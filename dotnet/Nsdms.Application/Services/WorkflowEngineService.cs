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

    public WorkflowEngineService(INsdmsDbContextFactory contextFactory, IRealtimeNotificationService? notificationService = null)
    {
        _contextFactory = contextFactory;
        _notificationService = notificationService;
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

        return transitions;
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
            "LearnerTradeTest" => $"/trade-tests",
            "InterSetaTransfer" => $"/inter-seta-transfers",
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
                if (test != null) test.ResultStatusCode = statusCode;
                break;
            case "InterSetaTransfer":
                var transfer = await context.InterSetaTransfers.FirstOrDefaultAsync(t => t.Id == entityId);
                if (transfer != null) transfer.TransferStatusCode = statusCode;
                break;
        }
    }
}
