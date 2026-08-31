using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;
using System.Security.Claims;

namespace Nsdms.Application.Services;

/// <summary>
/// Implements the universal Workflow &amp; BPM state machine engine with decoupled entity status synchronization and audit logging.
/// </summary>
public class WorkflowEngineService : IWorkflowEngineService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IRealtimeNotificationService? _notificationService;
    private readonly ICaslAbilityService? _caslService;

    public WorkflowEngineService(
        INsdmsDbContextFactory contextFactory, 
        IRealtimeNotificationService? notificationService = null,
        ICaslAbilityService? caslService = null)
    {
        _contextFactory = contextFactory;
        _notificationService = notificationService;
        _caslService = caslService;
    }

    public async Task<WorkflowInstance?> GetInstanceByEntityAsync(string processCode, int entityId)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        return await context.WorkflowInstances
            .Include(i => i.WorkflowDefinition)
            .Include(i => i.CurrentWorkflowState)
            .Include(i => i.Tasks)
            .Include(i => i.History)
            .FirstOrDefaultAsync(i => i.WorkflowDefinition!.Code == processCode && i.EntityId == entityId);
    }

    public async Task<WorkflowInstance?> GetInstanceByIdAsync(int instanceId)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        return await context.WorkflowInstances
            .Include(i => i.WorkflowDefinition)
            .Include(i => i.CurrentWorkflowState)
            .Include(i => i.Tasks)
            .Include(i => i.History)
            .FirstOrDefaultAsync(i => i.Id == instanceId);
    }

    public async Task<List<WorkflowState>> GetWorkflowDefinitionStatesAsync(string processCode)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        return await context.WorkflowStates
            .Include(s => s.WorkflowDefinition)
            .Where(s => s.WorkflowDefinition!.Code == processCode)
            .OrderBy(s => s.StepOrder)
            .ToListAsync();
    }

    public async Task<WorkflowInstance?> GetOrInitiateInstanceAsync(
        string processCode, 
        int entityId, 
        string entityTitle, 
        string entityRef, 
        string initiatorUserId, 
        string initiatorName)
    {
        var existing = await GetInstanceByEntityAsync(processCode, entityId);
        if (existing != null) return existing;

        var startResult = await StartWorkflowAsync(processCode, entityId, entityTitle, entityRef, initiatorUserId, initiatorName);
        return startResult.Instance;
    }

    public async Task<List<WorkflowTransition>> GetAvailableTransitionsAsync(int instanceId, ClaimsPrincipal? user)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var instance = await context.WorkflowInstances
            .Include(i => i.CurrentWorkflowState)
            .FirstOrDefaultAsync(i => i.Id == instanceId);

        if (instance == null || instance.IsCompleted)
        {
            return new List<WorkflowTransition>();
        }

        var transitions = await context.WorkflowTransitions
            .Include(t => t.ToState)
            .Where(t => t.WorkflowDefinitionId == instance.WorkflowDefinitionId && t.FromStateId == instance.CurrentWorkflowStateId)
            .ToListAsync();

        if (user == null || user.Identity?.IsAuthenticated != true || _caslService == null)
        {
            return transitions;
        }

        var username = user.Identity.Name;
        if (string.IsNullOrEmpty(username))
        {
            return transitions;
        }

        var userContext = await _caslService.GetUserContextByUsernameAsync(username);
        if (userContext.IsAdmin)
        {
            return transitions;
        }

        return transitions.Where(t => 
            string.IsNullOrEmpty(t.RequiredPermission) || 
            userContext.Permissions.Contains(t.RequiredPermission) || 
            userContext.Permissions.Contains($"{t.RequiredPermission.Split(':')[0]}:Manage")
        ).ToList();
    }

    public async Task<WorkflowActionResult> StartWorkflowAsync(
        string processCode, 
        int entityId, 
        string entityTitle, 
        string entityRef, 
        string initiatorUserId, 
        string initiatorName)
    {
        using var context = await _contextFactory.CreateDbContextAsync();

        var def = await context.WorkflowDefinitions
            .Include(d => d.States)
            .FirstOrDefaultAsync(d => d.Code == processCode && d.IsActive);

        if (def == null)
        {
            return new WorkflowActionResult(false, $"Workflow definition '{processCode}' not found or inactive.");
        }

        var initialState = def.States.FirstOrDefault(s => s.IsInitial) ?? def.States.OrderBy(s => s.StepOrder).FirstOrDefault();
        if (initialState == null)
        {
            return new WorkflowActionResult(false, $"Workflow definition '{processCode}' has no configured initial state.");
        }

        var instance = new WorkflowInstance
        {
            WorkflowDefinitionId = def.Id,
            EntityId = entityId,
            EntityTitle = entityTitle,
            EntityReferenceNumber = entityRef,
            CurrentWorkflowStateId = initialState.Id,
            InitiatorUserId = initiatorUserId,
            InitiatorName = initiatorName,
            InitiatedDate = DateTime.UtcNow,
            IsCompleted = initialState.IsTerminal
        };

        context.WorkflowInstances.Add(instance);
        await context.SaveChangesAsync();

        // Create initial task if initial state assigns to a group
        if (!string.IsNullOrEmpty(initialState.AllowedGroupRole))
        {
            var task = new WorkflowTask
            {
                WorkflowInstanceId = instance.Id,
                TaskTitle = $"{def.Name}: {entityTitle}",
                TaskDescription = $"Initial task for {entityRef} in state '{initialState.StateName}'.",
                AssignedGroupRole = initialState.AllowedGroupRole,
                TaskStatus = "Open",
                Priority = "Normal",
                DueDate = DateTime.UtcNow.AddDays(7),
                TargetRoute = GetTargetRoute(def.TargetEntityName, entityId)
            };
            context.WorkflowTasks.Add(task);
            await context.SaveChangesAsync();
        }

        // Double-write to AuditLog
        context.AuditLogs.Add(new AuditLog
        {
            EntityName = "WorkflowInstance",
            RecordId = instance.Id,
            ActionName = "START_WORKFLOW",
            Actor = initiatorUserId,
            Timestamp = DateTime.UtcNow,
            MetadataJson = $"{{\"processCode\":\"{processCode}\",\"entityId\":{entityId},\"initialState\":\"{initialState.StateName}\"}}"
        });
        await context.SaveChangesAsync();

        return new WorkflowActionResult(true, "Workflow started successfully.", instance, initialState.StateName);
    }

    public async Task<WorkflowActionResult> AdvanceWorkflowAsync(
        int instanceId, 
        int transitionId, 
        string actorUserId, 
        string actorName, 
        string actorRole, 
        string? comments = null)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var instance = await context.WorkflowInstances
            .Include(i => i.WorkflowDefinition)
            .Include(i => i.CurrentWorkflowState)
            .Include(i => i.Tasks)
            .FirstOrDefaultAsync(i => i.Id == instanceId);

        if (instance == null)
        {
            return new WorkflowActionResult(false, "Workflow instance not found.");
        }

        if (instance.IsCompleted)
        {
            return new WorkflowActionResult(false, "Workflow is already completed and in terminal state.");
        }

        var transition = await context.WorkflowTransitions
            .Include(t => t.ToState)
            .FirstOrDefaultAsync(t => t.Id == transitionId && t.WorkflowDefinitionId == instance.WorkflowDefinitionId);

        if (transition == null || transition.FromStateId != instance.CurrentWorkflowStateId)
        {
            return new WorkflowActionResult(false, "Invalid transition or state mismatch.");
        }

        if (transition.RequiresComments && string.IsNullOrWhiteSpace(comments))
        {
            return new WorkflowActionResult(false, "Comments/rationale are mandatory for this action.");
        }

        var fromStateId = instance.CurrentWorkflowStateId;
        var toState = transition.ToState!;

        // 1. Advance state
        instance.CurrentWorkflowStateId = toState.Id;
        instance.ModifiedAt = DateTime.UtcNow;
        instance.ModifiedBy = actorUserId;

        if (toState.IsTerminal)
        {
            instance.IsCompleted = true;
            instance.CompletedDate = DateTime.UtcNow;
        }

        // 2. Complete open tasks on this instance
        foreach (var task in instance.Tasks.Where(t => t.TaskStatus == "Open" || t.TaskStatus == "Claimed"))
        {
            task.TaskStatus = "Completed";
            task.CompletedDate = DateTime.UtcNow;
            task.ModifiedAt = DateTime.UtcNow;
            task.ModifiedBy = actorUserId;
        }

        // 3. Record history entry
        var history = new WorkflowHistory
        {
            WorkflowInstanceId = instance.Id,
            FromStateId = fromStateId,
            ToStateId = toState.Id,
            ActionName = transition.ActionName,
            ActorUserId = actorUserId,
            ActorName = actorName,
            ActorRole = actorRole,
            ActionDate = DateTime.UtcNow,
            Comments = comments
        };
        context.WorkflowHistories.Add(history);

        // 4. Create next task if not terminal
        WorkflowTask? nextTask = null;
        if (!toState.IsTerminal && !string.IsNullOrEmpty(toState.AllowedGroupRole))
        {
            nextTask = new WorkflowTask
            {
                WorkflowInstanceId = instance.Id,
                TaskTitle = $"{instance.WorkflowDefinition!.Name}: {instance.EntityTitle}",
                TaskDescription = $"Task transitioned to '{toState.StateName}' via '{transition.ActionName}' by {actorName}.",
                AssignedGroupRole = toState.AllowedGroupRole,
                TaskStatus = "Open",
                Priority = "Normal",
                DueDate = DateTime.UtcNow.AddDays(5),
                TargetRoute = GetTargetRoute(instance.WorkflowDefinition.TargetEntityName, instance.EntityId)
            };
            context.WorkflowTasks.Add(nextTask);
        }

        // 5. Create notification for initiator
        if (!string.IsNullOrEmpty(instance.InitiatorUserId) && instance.InitiatorUserId != actorUserId)
        {
            context.WorkflowNotifications.Add(new WorkflowNotification
            {
                WorkflowInstanceId = instance.Id,
                RecipientUserId = instance.InitiatorUserId,
                Title = $"Workflow Update: {instance.EntityTitle}",
                MessageHtml = $"Your item <strong>{instance.EntityReferenceNumber}</strong> was moved to <strong>{toState.StateName}</strong> by {actorName}.",
                TargetRoute = GetTargetRoute(instance.WorkflowDefinition!.TargetEntityName, instance.EntityId),
                CreatedDate = DateTime.UtcNow
            });
        }

        // 6. Push status code to parent entity if configured
        if (!string.IsNullOrEmpty(transition.NewEntityStatusCode))
        {
            await PushStatusToParentEntityAsync(context, instance.WorkflowDefinition!.TargetEntityName, instance.EntityId, transition.NewEntityStatusCode);
        }

        // 7. Double-write to AuditLog
        context.AuditLogs.Add(new AuditLog
        {
            EntityName = "WorkflowInstance",
            RecordId = instance.Id,
            ActionName = "ADVANCE_STATE",
            Actor = actorUserId,
            Timestamp = DateTime.UtcNow,
            MetadataJson = $"{{\"fromState\":\"{fromStateId}\",\"toState\":\"{toState.StateName}\",\"action\":\"{transition.ActionName}\",\"comments\":\"{comments}\"}}"
        });

        await context.SaveChangesAsync();

        if (_notificationService != null)
        {
            _ = _notificationService.NotifyWorkflowTransitionAsync(instance.WorkflowDefinition?.TargetEntityName ?? "Entity", instance.EntityId, fromStateId.ToString(), toState.StateName, actorName);
            if (nextTask != null)
            {
                _ = _notificationService.NotifyTaskAssignedAsync(nextTask.Id.ToString(), nextTask.TaskTitle, nextTask.AssignedGroupRole ?? "All", nextTask.Priority);
            }
        }

        return new WorkflowActionResult(true, $"Workflow successfully advanced to '{toState.StateName}'.", instance, toState.StateName);
    }

    public async Task<bool> SyncEntityStatusAsync(string processCode, int entityId, string statusCode, string actor = "SYSTEM")
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var def = await context.WorkflowDefinitions.FirstOrDefaultAsync(d => d.Code == processCode);
        if (def == null) return false;

        await PushStatusToParentEntityAsync(context, def.TargetEntityName, entityId, statusCode);

        context.AuditLogs.Add(new AuditLog
        {
            EntityName = def.TargetEntityName,
            RecordId = entityId,
            ActionName = "SYNC_ENTITY_STATUS",
            Actor = actor,
            Timestamp = DateTime.UtcNow,
            MetadataJson = $"{{\"processCode\":\"{processCode}\",\"newStatus\":\"{statusCode}\"}}"
        });

        await context.SaveChangesAsync();
        return true;
    }

    public async Task<List<WorkflowTask>> GetUserTasksAsync(string? userRole = null, string? userId = null)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var query = context.WorkflowTasks
            .Include(t => t.WorkflowInstance)
                .ThenInclude(i => i!.CurrentWorkflowState)
            .Include(t => t.WorkflowInstance)
                .ThenInclude(i => i!.WorkflowDefinition)
            .Where(t => t.TaskStatus == "Open" || t.TaskStatus == "Claimed");

        if (!string.IsNullOrEmpty(userRole) && !string.IsNullOrEmpty(userId))
        {
            query = query.Where(t => t.AssignedGroupRole == userRole || t.AssignedUserId == userId || t.AssignedGroupRole == null);
        }
        else if (!string.IsNullOrEmpty(userRole))
        {
            query = query.Where(t => t.AssignedGroupRole == userRole || t.AssignedGroupRole == null);
        }
        else if (!string.IsNullOrEmpty(userId))
        {
            query = query.Where(t => t.AssignedUserId == userId);
        }

        return await query.OrderByDescending(t => t.DueDate).ToListAsync();
    }

    public async Task<WorkflowTask?> ClaimTaskAsync(int taskId, string userId, string userName)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var task = await context.WorkflowTasks.FirstOrDefaultAsync(t => t.Id == taskId);
        if (task == null || task.TaskStatus != "Open") return null;

        task.AssignedUserId = userId;
        task.AssignedUserName = userName;
        task.TaskStatus = "Claimed";
        task.ClaimedDate = DateTime.UtcNow;
        task.ModifiedAt = DateTime.UtcNow;
        task.ModifiedBy = userId;

        context.AuditLogs.Add(new AuditLog
        {
            EntityName = "WorkflowTask",
            RecordId = task.Id,
            ActionName = "CLAIM_TASK",
            Actor = userId,
            Timestamp = DateTime.UtcNow,
            MetadataJson = $"{{\"taskId\":{task.Id},\"claimedBy\":\"{userName}\"}}"
        });

        await context.SaveChangesAsync();
        return task;
    }

    public async Task<List<WorkflowHistory>> GetWorkflowHistoryAsync(int instanceId)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        return await context.WorkflowHistories
            .Include(h => h.FromState)
            .Include(h => h.ToState)
            .Where(h => h.WorkflowInstanceId == instanceId)
            .OrderByDescending(h => h.ActionDate)
            .ToListAsync();
    }

    public async Task<List<WorkflowNotification>> GetUserNotificationsAsync(string userId, bool unreadOnly = false)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var query = context.WorkflowNotifications.Where(n => n.RecipientUserId == userId);
        if (unreadOnly)
        {
            query = query.Where(n => !n.IsRead);
        }
        return await query.OrderByDescending(n => n.CreatedDate).ToListAsync();
    }

    public async Task<bool> MarkNotificationReadAsync(int notificationId)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var notification = await context.WorkflowNotifications.FirstOrDefaultAsync(n => n.Id == notificationId);
        if (notification == null) return false;

        notification.IsRead = true;
        notification.ReadDate = DateTime.UtcNow;
        await context.SaveChangesAsync();
        return true;
    }

    public async Task<int> GetPendingTaskCountAsync(string? userRole = null, string? userId = null)
    {
        var tasks = await GetUserTasksAsync(userRole, userId);
        return tasks.Count;
    }

    public async Task<List<WorkflowDefinition>> GetAllDefinitionsAsync()
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        return await context.WorkflowDefinitions
            .Include(d => d.States)
            .Include(d => d.Transitions)
            .Include(d => d.Instances)
            .OrderBy(d => d.Name)
            .ToListAsync();
    }

    public async Task<WorkflowDefinition?> GetDefinitionByIdAsync(int id)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var def = await context.WorkflowDefinitions
            .Include(d => d.States)
            .Include(d => d.Transitions).ThenInclude(t => t.FromState)
            .Include(d => d.Transitions).ThenInclude(t => t.ToState)
            .Include(d => d.Instances).ThenInclude(i => i.CurrentWorkflowState)
            .FirstOrDefaultAsync(d => d.Id == id);

        if (def != null)
        {
            def.States = def.States.OrderBy(s => s.StepOrder).ToList();
        }

        return def;
    }

    public async Task<WorkflowDefinition> SaveDefinitionAsync(WorkflowDefinition definition, string actorUserId, string actorName)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var now = DateTime.UtcNow;

        if (definition.Id == 0)
        {
            definition.CreatedAt = now;
            definition.CreatedBy = actorUserId;
            context.WorkflowDefinitions.Add(definition);
            await context.SaveChangesAsync();

            context.AuditLogs.Add(new AuditLog
            {
                EntityName = "WorkflowDefinition",
                RecordId = definition.Id,
                ActionName = "CREATE_WORKFLOW_DEFINITION",
                Actor = actorUserId,
                Timestamp = now,
                MetadataJson = $"{{\"code\":\"{definition.Code}\",\"name\":\"{definition.Name}\",\"targetEntity\":\"{definition.TargetEntityName}\"}}"
            });
        }
        else
        {
            var existing = await context.WorkflowDefinitions.FirstOrDefaultAsync(d => d.Id == definition.Id);
            if (existing == null) throw new InvalidOperationException($"Workflow definition #{definition.Id} not found.");

            var beforeSnapshot = $"{{\"code\":\"{existing.Code}\",\"name\":\"{existing.Name}\",\"isActive\":{existing.IsActive.ToString().ToLowerInvariant()}}}";

            existing.Name = definition.Name;
            existing.TargetEntityName = definition.TargetEntityName;
            existing.KeyFieldName = definition.KeyFieldName;
            existing.IsActive = definition.IsActive;
            existing.ModifiedAt = now;
            existing.ModifiedBy = actorUserId;

            context.AuditLogs.Add(new AuditLog
            {
                EntityName = "WorkflowDefinition",
                RecordId = existing.Id,
                ActionName = "UPDATE_WORKFLOW_DEFINITION",
                Actor = actorUserId,
                Timestamp = now,
                MetadataJson = $"{{\"before\":{beforeSnapshot},\"after\":{{\"code\":\"{existing.Code}\",\"name\":\"{existing.Name}\",\"isActive\":{existing.IsActive.ToString().ToLowerInvariant()}}}}}"
            });

            await context.SaveChangesAsync();
            return existing;
        }

        await context.SaveChangesAsync();
        return definition;
    }

    public async Task<bool> DeleteDefinitionAsync(int id, string actorUserId, string actorName)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var existing = await context.WorkflowDefinitions
            .Include(d => d.Instances)
            .FirstOrDefaultAsync(d => d.Id == id);

        if (existing == null) return false;

        var now = DateTime.UtcNow;
        if (existing.Instances.Any())
        {
            // Deactivate instead of physical delete to preserve historical relational integrity
            existing.IsActive = false;
            existing.ModifiedAt = now;
            existing.ModifiedBy = actorUserId;

            context.AuditLogs.Add(new AuditLog
            {
                EntityName = "WorkflowDefinition",
                RecordId = existing.Id,
                ActionName = "DEACTIVATE_WORKFLOW_DEFINITION",
                Actor = actorUserId,
                Timestamp = now,
                MetadataJson = $"{{\"code\":\"{existing.Code}\",\"reason\":\"Has active/historic instances\"}}"
            });
        }
        else
        {
            context.WorkflowDefinitions.Remove(existing);
            context.AuditLogs.Add(new AuditLog
            {
                EntityName = "WorkflowDefinition",
                RecordId = id,
                ActionName = "DELETE_WORKFLOW_DEFINITION",
                Actor = actorUserId,
                Timestamp = now,
                MetadataJson = $"{{\"code\":\"{existing.Code}\",\"name\":\"{existing.Name}\"}}"
            });
        }

        await context.SaveChangesAsync();
        return true;
    }

    public async Task<WorkflowState> SaveStateAsync(WorkflowState state, string actorUserId, string actorName)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var now = DateTime.UtcNow;

        if (state.IsInitial)
        {
            var otherStates = await context.WorkflowStates
                .Where(s => s.WorkflowDefinitionId == state.WorkflowDefinitionId && s.Id != state.Id && s.IsInitial)
                .ToListAsync();
            foreach (var s in otherStates)
            {
                s.IsInitial = false;
                s.ModifiedAt = now;
                s.ModifiedBy = actorUserId;
            }
        }

        if (state.Id == 0)
        {
            state.CreatedAt = now;
            state.CreatedBy = actorUserId;
            context.WorkflowStates.Add(state);
            await context.SaveChangesAsync();

            context.AuditLogs.Add(new AuditLog
            {
                EntityName = "WorkflowState",
                RecordId = state.Id,
                ActionName = "CREATE_WORKFLOW_STATE",
                Actor = actorUserId,
                Timestamp = now,
                MetadataJson = $"{{\"workflowDefinitionId\":{state.WorkflowDefinitionId},\"stateCode\":\"{state.StateCode}\",\"stateName\":\"{state.StateName}\"}}"
            });
        }
        else
        {
            var existing = await context.WorkflowStates.FirstOrDefaultAsync(s => s.Id == state.Id);
            if (existing == null) throw new InvalidOperationException($"Workflow state #{state.Id} not found.");

            existing.StateName = state.StateName;
            existing.StateCode = state.StateCode;
            existing.StepOrder = state.StepOrder;
            existing.IsInitial = state.IsInitial;
            existing.IsTerminal = state.IsTerminal;
            existing.AllowedGroupRole = state.AllowedGroupRole;
            existing.ModifiedAt = now;
            existing.ModifiedBy = actorUserId;

            context.AuditLogs.Add(new AuditLog
            {
                EntityName = "WorkflowState",
                RecordId = existing.Id,
                ActionName = "UPDATE_WORKFLOW_STATE",
                Actor = actorUserId,
                Timestamp = now,
                MetadataJson = $"{{\"stateCode\":\"{existing.StateCode}\",\"stateName\":\"{existing.StateName}\",\"stepOrder\":{existing.StepOrder}}}"
            });

            await context.SaveChangesAsync();
            return existing;
        }

        await context.SaveChangesAsync();
        return state;
    }

    public async Task<bool> DeleteStateAsync(int stateId, string actorUserId, string actorName)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var hasActiveInstances = await context.WorkflowInstances.AnyAsync(i => i.CurrentWorkflowStateId == stateId);
        if (hasActiveInstances) return false;

        var state = await context.WorkflowStates.FirstOrDefaultAsync(s => s.Id == stateId);
        if (state == null) return false;

        // Remove dependent transitions
        var transitions = await context.WorkflowTransitions
            .Where(t => t.FromStateId == stateId || t.ToStateId == stateId)
            .ToListAsync();
        if (transitions.Any())
        {
            context.WorkflowTransitions.RemoveRange(transitions);
        }

        context.WorkflowStates.Remove(state);

        context.AuditLogs.Add(new AuditLog
        {
            EntityName = "WorkflowState",
            RecordId = stateId,
            ActionName = "DELETE_WORKFLOW_STATE",
            Actor = actorUserId,
            Timestamp = DateTime.UtcNow,
            MetadataJson = $"{{\"stateCode\":\"{state.StateCode}\",\"stateName\":\"{state.StateName}\"}}"
        });

        await context.SaveChangesAsync();
        return true;
    }

    public async Task<WorkflowTransition> SaveTransitionAsync(WorkflowTransition transition, string actorUserId, string actorName)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var now = DateTime.UtcNow;

        if (transition.Id == 0)
        {
            transition.CreatedAt = now;
            transition.CreatedBy = actorUserId;
            context.WorkflowTransitions.Add(transition);
            await context.SaveChangesAsync();

            context.AuditLogs.Add(new AuditLog
            {
                EntityName = "WorkflowTransition",
                RecordId = transition.Id,
                ActionName = "CREATE_WORKFLOW_TRANSITION",
                Actor = actorUserId,
                Timestamp = now,
                MetadataJson = $"{{\"fromStateId\":{transition.FromStateId},\"toStateId\":{transition.ToStateId},\"actionName\":\"{transition.ActionName}\"}}"
            });
        }
        else
        {
            var existing = await context.WorkflowTransitions.FirstOrDefaultAsync(t => t.Id == transition.Id);
            if (existing == null) throw new InvalidOperationException($"Workflow transition #{transition.Id} not found.");

            existing.FromStateId = transition.FromStateId;
            existing.ToStateId = transition.ToStateId;
            existing.ActionName = transition.ActionName;
            existing.ButtonColor = transition.ButtonColor;
            existing.ButtonIcon = transition.ButtonIcon;
            existing.RequiredPermission = transition.RequiredPermission;
            existing.RequiresComments = transition.RequiresComments;
            existing.NewEntityStatusCode = transition.NewEntityStatusCode;
            existing.ModifiedAt = now;
            existing.ModifiedBy = actorUserId;

            context.AuditLogs.Add(new AuditLog
            {
                EntityName = "WorkflowTransition",
                RecordId = existing.Id,
                ActionName = "UPDATE_WORKFLOW_TRANSITION",
                Actor = actorUserId,
                Timestamp = now,
                MetadataJson = $"{{\"actionName\":\"{existing.ActionName}\",\"fromStateId\":{existing.FromStateId},\"toStateId\":{existing.ToStateId}}}"
            });

            await context.SaveChangesAsync();
            return existing;
        }

        await context.SaveChangesAsync();
        return transition;
    }

    public async Task<bool> DeleteTransitionAsync(int transitionId, string actorUserId, string actorName)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var transition = await context.WorkflowTransitions.FirstOrDefaultAsync(t => t.Id == transitionId);
        if (transition == null) return false;

        context.WorkflowTransitions.Remove(transition);

        context.AuditLogs.Add(new AuditLog
        {
            EntityName = "WorkflowTransition",
            RecordId = transitionId,
            ActionName = "DELETE_WORKFLOW_TRANSITION",
            Actor = actorUserId,
            Timestamp = DateTime.UtcNow,
            MetadataJson = $"{{\"actionName\":\"{transition.ActionName}\"}}"
        });

        await context.SaveChangesAsync();
        return true;
    }

    public async Task<List<WorkflowInstance>> GetInstancesByDefinitionIdAsync(int definitionId)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        return await context.WorkflowInstances
            .Include(i => i.CurrentWorkflowState)
            .Include(i => i.Tasks)
            .Include(i => i.History)
            .Where(i => i.WorkflowDefinitionId == definitionId)
            .OrderByDescending(i => i.InitiatedDate)
            .ToListAsync();
    }

    public async Task<List<DocumentRequirementRule>> GetDocumentRequirementsAsync(string processCode)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        return await context.DocumentRequirementRules
            .Where(r => r.WorkflowProcessCode == processCode)
            .ToListAsync();
    }

    public async Task<DocumentRequirementRule> SaveDocumentRequirementAsync(DocumentRequirementRule rule, string actorUserId, string actorName)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var now = DateTime.UtcNow;

        if (rule.Id == 0)
        {
            rule.CreatedAt = now;
            rule.CreatedBy = actorUserId;
            context.DocumentRequirementRules.Add(rule);
            await context.SaveChangesAsync();

            context.AuditLogs.Add(new AuditLog
            {
                EntityName = "DocumentRequirementRule",
                RecordId = rule.Id,
                ActionName = "CREATE_DOCUMENT_REQUIREMENT_RULE",
                Actor = actorUserId,
                Timestamp = now,
                MetadataJson = $"{{\"processCode\":\"{rule.WorkflowProcessCode}\",\"type\":\"{rule.DocumentTypeCode}\"}}"
            });
        }
        else
        {
            var existing = await context.DocumentRequirementRules.FirstOrDefaultAsync(r => r.Id == rule.Id);
            if (existing == null) throw new InvalidOperationException($"Rule #{rule.Id} not found.");

            existing.DocumentTypeCode = rule.DocumentTypeCode;
            existing.DocumentTypeName = rule.DocumentTypeName;
            existing.Description = rule.Description;
            existing.IsMandatory = rule.IsMandatory;
            existing.RequiredAtStateId = rule.RequiredAtStateId;
            existing.ModifiedAt = now;
            existing.ModifiedBy = actorUserId;

            context.AuditLogs.Add(new AuditLog
            {
                EntityName = "DocumentRequirementRule",
                RecordId = existing.Id,
                ActionName = "UPDATE_DOCUMENT_REQUIREMENT_RULE",
                Actor = actorUserId,
                Timestamp = now,
                MetadataJson = $"{{\"processCode\":\"{existing.WorkflowProcessCode}\",\"type\":\"{existing.DocumentTypeCode}\"}}"
            });

            await context.SaveChangesAsync();
            return existing;
        }

        await context.SaveChangesAsync();
        return rule;
    }

    public async Task<bool> DeleteDocumentRequirementAsync(int ruleId, string actorUserId, string actorName)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var existing = await context.DocumentRequirementRules.FirstOrDefaultAsync(r => r.Id == ruleId);
        if (existing == null) return false;

        context.DocumentRequirementRules.Remove(existing);

        context.AuditLogs.Add(new AuditLog
        {
            EntityName = "DocumentRequirementRule",
            RecordId = ruleId,
            ActionName = "DELETE_DOCUMENT_REQUIREMENT_RULE",
            Actor = actorUserId,
            Timestamp = DateTime.UtcNow,
            MetadataJson = $"{{\"processCode\":\"{existing.WorkflowProcessCode}\",\"type\":\"{existing.DocumentTypeCode}\"}}"
        });

        await context.SaveChangesAsync();
        return true;
    }

    public async Task<WorkflowDefinition> CloneDefinitionAsync(int sourceDefinitionId, string newCode, string newName, string actorUserId, string actorName)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var source = await context.WorkflowDefinitions
            .Include(d => d.States)
            .Include(d => d.Transitions)
            .FirstOrDefaultAsync(d => d.Id == sourceDefinitionId);

        if (source == null) throw new InvalidOperationException($"Source workflow definition #{sourceDefinitionId} not found.");

        var now = DateTime.UtcNow;
        var newDef = new WorkflowDefinition
        {
            Code = newCode.ToUpperInvariant().Trim(),
            Name = newName.Trim(),
            TargetEntityName = source.TargetEntityName,
            KeyFieldName = source.KeyFieldName,
            IsActive = true,
            CreatedAt = now,
            CreatedBy = actorUserId
        };

        context.WorkflowDefinitions.Add(newDef);
        await context.SaveChangesAsync();

        var stateIdMap = new Dictionary<int, int>();
        foreach (var oldState in source.States.OrderBy(s => s.StepOrder))
        {
            var newState = new WorkflowState
            {
                WorkflowDefinitionId = newDef.Id,
                StateCode = oldState.StateCode,
                StateName = oldState.StateName,
                StepOrder = oldState.StepOrder,
                IsInitial = oldState.IsInitial,
                IsTerminal = oldState.IsTerminal,
                AllowedGroupRole = oldState.AllowedGroupRole,
                CreatedAt = now,
                CreatedBy = actorUserId
            };
            context.WorkflowStates.Add(newState);
            await context.SaveChangesAsync();
            stateIdMap[oldState.Id] = newState.Id;
        }

        foreach (var oldTrans in source.Transitions)
        {
            if (stateIdMap.TryGetValue(oldTrans.FromStateId, out var newFromId) &&
                stateIdMap.TryGetValue(oldTrans.ToStateId, out var newToId))
            {
                var newTrans = new WorkflowTransition
                {
                    WorkflowDefinitionId = newDef.Id,
                    FromStateId = newFromId,
                    ToStateId = newToId,
                    ActionName = oldTrans.ActionName,
                    ButtonColor = oldTrans.ButtonColor,
                    ButtonIcon = oldTrans.ButtonIcon,
                    RequiredPermission = oldTrans.RequiredPermission,
                    RequiresComments = oldTrans.RequiresComments,
                    NewEntityStatusCode = oldTrans.NewEntityStatusCode,
                    CreatedAt = now,
                    CreatedBy = actorUserId
                };
                context.WorkflowTransitions.Add(newTrans);
            }
        }

        context.AuditLogs.Add(new AuditLog
        {
            EntityName = "WorkflowDefinition",
            RecordId = newDef.Id,
            ActionName = "CLONE_WORKFLOW_DEFINITION",
            Actor = actorUserId,
            Timestamp = now,
            MetadataJson = $"{{\"sourceId\":{sourceDefinitionId},\"newCode\":\"{newDef.Code}\",\"newName\":\"{newDef.Name}\"}}"
        });

        await context.SaveChangesAsync();
        return newDef;
    }

    private static string GetTargetRoute(string entityName, int entityId)
    {
        return entityName switch
        {
            "Organisation" => $"/employers/{entityId}",
            "WspSubmission" => $"/wsp/{entityId}",
            "GrantApplication" => $"/grants/{entityId}",
            "TrainingProvider" => $"/sdp/{entityId}",
            "WorkplaceApproval" => $"/workplace-approvals/{entityId}",
            "CompanyLearner" => $"/learners/{entityId}",
            "GrantMoa" => $"/finance/grants/{entityId}",
            "LearnerTradeTest" => $"/tradetests/{entityId}",
            "InterSetaTransfer" => $"/inter-seta-transfers",
            "WorkplaceMonitoring" or "Visit" => $"/monitoring/{entityId}",
            "BankingDetails" => $"/finance/banking-details/{entityId}",
            "ExtensionOfScope" or "AssessorModeratorScope" => $"/etqa/scope-extensions/{entityId}",
            "NonSetaVerification" or "NonSetaCompanyHistory" => $"/non-seta/verifications/{entityId}",
            "ProjectImplementationPlan" => $"/grants/pip/{entityId}",
            "SummativeAssessmentReport" or "SummativeAssessment" => $"/assessments/summative/{entityId}",
            "ContractAddenda" => $"/contracts/variations/{entityId}",
            _ => "/"
        };
    }

    private static async Task PushStatusToParentEntityAsync(INsdmsDbContext context, string entityName, int entityId, string statusCode)
    {
        switch (entityName)
        {
            case "Organisation":
                var org = await context.Organisations.FirstOrDefaultAsync(o => o.Id == entityId);
                if (org != null) org.OrganisationStatusCode = statusCode;
                break;
            case "WspSubmission":
                var wsp = await context.WspSubmissions.FirstOrDefaultAsync(w => w.Id == entityId);
                if (wsp != null) wsp.WspApprovalStatusCode = statusCode;
                break;
            case "GrantApplication":
                var grant = await context.GrantApplications.FirstOrDefaultAsync(g => g.Id == entityId);
                if (grant != null) grant.ApplicationStatusCode = statusCode;
                break;
            case "GrantMoa":
                var moa = await context.GrantMoas.FirstOrDefaultAsync(m => m.Id == entityId);
                if (moa != null) moa.MoaStatusCode = statusCode;
                break;
            case "TrainingProvider":
                var provider = await context.TrainingProviders.FirstOrDefaultAsync(p => p.Id == entityId);
                if (provider != null) provider.ProviderStatusCode = statusCode;
                break;
            case "WorkplaceApproval":
                var wpa = await context.WorkplaceApprovals.FirstOrDefaultAsync(w => w.Id == entityId);
                if (wpa != null) wpa.ApprovalStatusCode = statusCode;
                break;
            case "CompanyLearner":
                var learner = await context.CompanyLearners.FirstOrDefaultAsync(l => l.Id == entityId);
                if (learner != null) learner.EnrolmentStatusCode = statusCode;
                break;
            case "LearnerTradeTest":
                var test = await context.LearnerTradeTests.FirstOrDefaultAsync(t => t.Id == entityId);
                if (test != null)
                {
                    test.ResultStatusCode = statusCode;
                }
                break;
            case "LearnerTradeTestApplication":
                var ttApp = await context.LearnerTradeTestApplications.FirstOrDefaultAsync(t => t.Id == entityId);
                if (ttApp != null)
                {
                    ttApp.StatusCode = statusCode;
                }
                break;
            case "InterSetaTransfer":
                var transfer = await context.InterSetaTransfers.FirstOrDefaultAsync(t => t.Id == entityId);
                if (transfer != null) transfer.TransferStatusCode = statusCode;
                break;
            case "WorkplaceMonitoring":
            case "WorkplaceMonitoringSiteVisit":
                var mon = await context.WorkplaceMonitoringSiteVisits.FirstOrDefaultAsync(m => m.Id == entityId);
                if (mon != null) mon.StatusCode = statusCode;
                break;
            case "BankingDetails":
                var bank = await context.BankingDetails.FirstOrDefaultAsync(b => b.Id == entityId);
                if (bank != null) bank.ApprovalStatusCode = statusCode;
                break;
            case "ExtensionOfScope":
            case "SdpScopeExtensionApplication":
                var eos = await context.SdpScopeExtensionApplications.FirstOrDefaultAsync(e => e.Id == entityId);
                if (eos != null) eos.StatusCode = statusCode;
                break;
            case "AssessorModeratorScope":
            case "AssessorModeratorApplication":
                var amApp = await context.AssessorModeratorApplications.FirstOrDefaultAsync(e => e.Id == entityId);
                if (amApp != null) amApp.StatusCode = statusCode;
                break;
            case "NonSetaVerification":
            case "NonSetaQualificationsCompletion":
                var nsv = await context.NonSetaQualificationsCompletions.FirstOrDefaultAsync(n => n.Id == entityId);
                if (nsv != null) nsv.VerificationStatusCode = statusCode;
                break;
            case "ProjectImplementationPlan":
                var pip = await context.ProjectImplementationPlans.FirstOrDefaultAsync(p => p.Id == entityId);
                if (pip != null) pip.StatusCode = statusCode;
                break;
            case "SummativeAssessmentReport":
                var sar = await context.SummativeAssessmentReports.FirstOrDefaultAsync(s => s.Id == entityId);
                if (sar != null) sar.StatusCode = statusCode;
                break;
            case "ContractAddenda":
                var add = await context.ContractAddendas.FirstOrDefaultAsync(a => a.Id == entityId);
                if (add != null) add.StatusCode = statusCode;
                break;
        }
    }
}
