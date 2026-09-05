using Microsoft.AspNetCore.SignalR;
using Microsoft.Extensions.Logging.Abstractions;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Services;
using Nsdms.Web.Hubs;
using Nsdms.Web.Services;
using System.Security.Claims;
using Xunit;

namespace Nsdms.Tests;

public class SignalRNotificationAndSlaTests
{
    [Fact]
    public async Task RealtimeNotificationService_TaskAssigned_InvokesEventAndSignalRClient()
    {
        var fakeClient = new FakeNotificationClient();
        var fakeHubContext = new FakeHubContext(fakeClient);
        var service = new RealtimeNotificationService(fakeHubContext, NullLogger<RealtimeNotificationService>.Instance);

        string? receivedId = null;
        string? receivedTitle = null;
        string? receivedRole = null;
        string? receivedPriority = null;

        service.TaskAssignedReceived += (id, title, role, priority) =>
        {
            receivedId = id;
            receivedTitle = title;
            receivedRole = role;
            receivedPriority = priority;
        };

        await service.NotifyTaskAssignedAsync("101", "Review WSP 2026", "CLO", "High");

        Assert.Equal("101", receivedId);
        Assert.Equal("Review WSP 2026", receivedTitle);
        Assert.Equal("CLO", receivedRole);
        Assert.Equal("High", receivedPriority);

        Assert.Contains(fakeClient.TaskNotifications, n => n.TaskId == "101" && n.TaskTitle == "Review WSP 2026");
        Assert.Contains(fakeClient.TaskAssignments, a => a.TaskId == "101" && a.Title == "Review WSP 2026" && a.AssignedRole == "CLO");
    }

    [Fact]
    public async Task RealtimeNotificationService_WorkflowTransition_InvokesEventAndSignalRClient()
    {
        var fakeClient = new FakeNotificationClient();
        var fakeHubContext = new FakeHubContext(fakeClient);
        var service = new RealtimeNotificationService(fakeHubContext, NullLogger<RealtimeNotificationService>.Instance);

        string? receivedEntityType = null;
        int receivedEntityId = 0;
        string? receivedFromState = null;
        string? receivedToState = null;
        string? receivedActor = null;

        service.WorkflowTransitionReceived += (entityType, entityId, fromState, toState, actor) =>
        {
            receivedEntityType = entityType;
            receivedEntityId = entityId;
            receivedFromState = fromState;
            receivedToState = toState;
            receivedActor = actor;
        };

        await service.NotifyWorkflowTransitionAsync("WspSubmission", 42, "Draft", "Submitted", "SDF_Officer");

        Assert.Equal("WspSubmission", receivedEntityType);
        Assert.Equal(42, receivedEntityId);
        Assert.Equal("Draft", receivedFromState);
        Assert.Equal("Submitted", receivedToState);
        Assert.Equal("SDF_Officer", receivedActor);

        Assert.Contains(fakeClient.Transitions, t => t.EntityType == "WspSubmission" && t.EntityId == 42 && t.ToState == "Submitted");
    }

    [Fact]
    public async Task RealtimeNotificationService_SlaWarning_InvokesEventAndSignalRClient()
    {
        var fakeClient = new FakeNotificationClient();
        var fakeHubContext = new FakeHubContext(fakeClient);
        var service = new RealtimeNotificationService(fakeHubContext, NullLogger<RealtimeNotificationService>.Instance);

        string? receivedTitle = null;
        int receivedHours = -999;

        service.SlaWarningReceived += (title, hours) =>
        {
            receivedTitle = title;
            receivedHours = hours;
        };

        await service.NotifySlaWarningAsync("Discretionary Grant Tranche Inspection", 6);

        Assert.Equal("Discretionary Grant Tranche Inspection", receivedTitle);
        Assert.Equal(6, receivedHours);

        Assert.Contains(fakeClient.SlaWarnings, w => w.TaskTitle == "Discretionary Grant Tranche Inspection" && w.HoursRemaining == 6);
    }

    [Fact]
    public async Task RealtimeNotificationService_UserNotification_InvokesEventAndSignalRClient()
    {
        var fakeClient = new FakeNotificationClient();
        var fakeHubContext = new FakeHubContext(fakeClient);
        var service = new RealtimeNotificationService(fakeHubContext, NullLogger<RealtimeNotificationService>.Instance);

        SystemNotificationDto? received = null;
        service.UserNotificationReceived += dto => received = dto;

        var sampleDto = new SystemNotificationDto
        {
            Id = 55,
            Title = "Banking Approval Required",
            Message = "Secondary signoff needed",
            NotificationType = "SignoffRequired",
            Severity = "Warning",
            RecipientUsername = "cfo@merseta.org.za"
        };

        await service.PublishNotificationAsync(sampleDto);

        Assert.NotNull(received);
        Assert.Equal(55, received.Id);
        Assert.Equal("Banking Approval Required", received.Title);
        Assert.Contains(fakeClient.UserNotifications, n => n.Id == 55);
    }

    [Fact]
    public async Task SlaMonitoringService_EvaluatesHealthy_Approaching_AndBreachedTasks()
    {
        var dbName = Guid.NewGuid().ToString();
        var factory = new TestDbContextFactory(dbName);
        var realtimeService = new FakeRealtimeNotificationService();
        var audit = new AuditService(factory);
        var persistent = new NotificationService(factory, audit);
        var slaService = new SlaMonitoringService(factory, NullLogger<SlaMonitoringService>.Instance, realtimeService, persistent);

        using (var db = await factory.CreateDbContextAsync())
        {
            var def = new WorkflowDefinition { Code = "WSP", Name = "WSP Submission", TargetEntityName = "WspSubmission" };
            db.WorkflowDefinitions.Add(def);
            await db.SaveChangesAsync();

            var state = new WorkflowState { WorkflowDefinitionId = def.Id, StateName = "PendingReview", StepOrder = 1 };
            db.WorkflowStates.Add(state);
            await db.SaveChangesAsync();

            var instance = new WorkflowInstance
            {
                WorkflowDefinitionId = def.Id,
                EntityId = 10,
                CurrentWorkflowStateId = state.Id,
                InitiatorUserId = "user1",
                EntityTitle = "WSP 2026",
                EntityReferenceNumber = "WSP-001"
            };
            db.WorkflowInstances.Add(instance);
            await db.SaveChangesAsync();

            // Task 1: Healthy (due in 5 days)
            db.WorkflowTasks.Add(new WorkflowTask
            {
                WorkflowInstanceId = instance.Id,
                TaskTitle = "Healthy Review Task",
                AssignedGroupRole = "CLO",
                TaskStatus = "Open",
                DueDate = DateTime.UtcNow.AddDays(5)
            });

            // Task 2: Approaching breach (due in 12 hours)
            db.WorkflowTasks.Add(new WorkflowTask
            {
                WorkflowInstanceId = instance.Id,
                TaskTitle = "Approaching Deadline Task",
                AssignedGroupRole = "CLO",
                TaskStatus = "Open",
                DueDate = DateTime.UtcNow.AddHours(12)
            });

            // Task 3: Breached (overdue by 4 hours)
            db.WorkflowTasks.Add(new WorkflowTask
            {
                WorkflowInstanceId = instance.Id,
                TaskTitle = "Breached Overdue Task",
                AssignedGroupRole = "Manager",
                TaskStatus = "Open",
                DueDate = DateTime.UtcNow.AddHours(-4)
            });

            // Task 4: Completed task (should be ignored)
            db.WorkflowTasks.Add(new WorkflowTask
            {
                WorkflowInstanceId = instance.Id,
                TaskTitle = "Completed Archive Task",
                AssignedGroupRole = "Manager",
                TaskStatus = "Completed",
                DueDate = DateTime.UtcNow.AddHours(-10)
            });

            await db.SaveChangesAsync();
        }

        var result = await slaService.CheckTaskSlasAsync();

        Assert.Equal(3, result.TotalTasksEvaluated);
        Assert.Equal(1, result.HealthyCount);
        Assert.Equal(1, result.ApproachingBreachCount);
        Assert.Equal(1, result.BreachedCount);
        Assert.Equal(2, result.Warnings.Count);

        var breachedWarning = result.Warnings.FirstOrDefault(w => w.TaskTitle == "Breached Overdue Task");
        Assert.NotNull(breachedWarning);
        Assert.True(breachedWarning.IsBreached);
        Assert.True(breachedWarning.HoursRemaining <= 0);

        var approachingWarning = result.Warnings.FirstOrDefault(w => w.TaskTitle == "Approaching Deadline Task");
        Assert.NotNull(approachingWarning);
        Assert.False(approachingWarning.IsBreached);
        Assert.True(approachingWarning.HoursRemaining > 0 && approachingWarning.HoursRemaining <= 24);

        // Verify Real-time alerts were dispatched
        Assert.Equal(2, realtimeService.SlaWarnings.Count);
        Assert.Contains(realtimeService.SlaWarnings, w => w.Title == "Breached Overdue Task");
        Assert.Contains(realtimeService.SlaWarnings, w => w.Title == "Approaching Deadline Task");
    }

    [Fact]
    public async Task WorkflowEngineService_DispatchesRealtimeNotifications_OnStartAdvanceAndClaim()
    {
        var dbName = Guid.NewGuid().ToString();
        var factory = new TestDbContextFactory(dbName);
        var realtimeService = new FakeRealtimeNotificationService();
        var engine = new WorkflowEngineService(factory, realtimeService, caslService: null);

        int instanceId;
        int taskId;
        int transitionId;

        using (var db = await factory.CreateDbContextAsync())
        {
            var def = new WorkflowDefinition
            {
                Code = "TEST_FLOW",
                Name = "Test Process",
                TargetEntityName = "TestEntity",
                IsActive = true
            };
            db.WorkflowDefinitions.Add(def);
            await db.SaveChangesAsync();

            var s1 = new WorkflowState { WorkflowDefinitionId = def.Id, StateName = "Initial", StepOrder = 1, IsInitial = true, AllowedGroupRole = "CLO" };
            var s2 = new WorkflowState { WorkflowDefinitionId = def.Id, StateName = "Approved", StepOrder = 2, IsTerminal = true };
            db.WorkflowStates.AddRange(s1, s2);
            await db.SaveChangesAsync();

            var trans = new WorkflowTransition
            {
                WorkflowDefinitionId = def.Id,
                FromStateId = s1.Id,
                ToStateId = s2.Id,
                ActionName = "Approve",
                NewEntityStatusCode = "APPROVED"
            };
            db.WorkflowTransitions.Add(trans);
            await db.SaveChangesAsync();

            transitionId = trans.Id;
        }

        // 1. Start Workflow -> should broadcast transition and task assigned
        var startResult = await engine.StartWorkflowAsync("TEST_FLOW", 100, "Record 100", "REF-100", "user_maker", "Maker User");
        Assert.True(startResult.Success);
        instanceId = startResult.Instance!.Id;

        Assert.Contains(realtimeService.Transitions, t => t.EntityType == "TestEntity" && t.EntityId == 100 && t.To == "Initial");
        Assert.Contains(realtimeService.TaskAssignments, a => a.Role == "CLO" && a.Priority == "Normal");

        using (var db = await factory.CreateDbContextAsync())
        {
            var createdTask = db.WorkflowTasks.FirstOrDefault(t => t.WorkflowInstanceId == instanceId);
            Assert.NotNull(createdTask);
            taskId = createdTask.Id;
        }

        // 2. Claim Task -> should broadcast task assignment update
        var claimResult = await engine.ClaimTaskAsync(taskId, "officer_1", "Officer One");
        Assert.NotNull(claimResult);
        Assert.Equal("Claimed", claimResult.TaskStatus);

        Assert.Contains(realtimeService.TaskAssignments, a => a.TaskId == taskId.ToString());

        // 3. Advance Workflow -> should broadcast transition
        var advanceResult = await engine.AdvanceWorkflowAsync(instanceId, transitionId, "officer_1", "Officer One", "CLO", "Approved all requirements.");
        Assert.True(advanceResult.Success);

        Assert.Contains(realtimeService.Transitions, t => t.EntityType == "TestEntity" && t.EntityId == 100 && t.To == "Approved");
    }

    // --- Pure C# Test Doubles for SignalR Testing ---

    private class FakeNotificationClient : INsdmsNotificationClient
    {
        public List<(string TaskId, string TaskTitle, string AssignedRole, string Priority)> TaskNotifications { get; } = new();
        public List<(string TaskId, string Title, string AssignedRole)> TaskAssignments { get; } = new();
        public List<(string EntityType, int EntityId, string FromState, string ToState, string Actor)> Transitions { get; } = new();
        public List<(string TaskTitle, int HoursRemaining)> SlaWarnings { get; } = new();
        public List<(string Message, string Severity)> SystemAlerts { get; } = new();
        public List<(string Message, string Severity)> BroadcastAlerts { get; } = new();
        public List<SystemNotificationDto> UserNotifications { get; } = new();
        public List<int> NotificationCounts { get; } = new();

        public Task ReceiveTaskNotification(string taskId, string taskTitle, string assignedRole, string priority)
        {
            TaskNotifications.Add((taskId, taskTitle, assignedRole, priority));
            return Task.CompletedTask;
        }

        public Task ReceiveTaskAssignment(string taskId, string title, string assignedRole)
        {
            TaskAssignments.Add((taskId, title, assignedRole));
            return Task.CompletedTask;
        }

        public Task ReceiveWorkflowTransition(string entityType, int entityId, string fromState, string toState, string actor)
        {
            Transitions.Add((entityType, entityId, fromState, toState, actor));
            return Task.CompletedTask;
        }

        public Task ReceiveSlaWarning(string taskTitle, int hoursRemaining)
        {
            SlaWarnings.Add((taskTitle, hoursRemaining));
            return Task.CompletedTask;
        }

        public Task ReceiveSystemAlert(string message, string severity)
        {
            SystemAlerts.Add((message, severity));
            return Task.CompletedTask;
        }

        public Task ReceiveBroadcastAlert(string message, string severity)
        {
            BroadcastAlerts.Add((message, severity));
            return Task.CompletedTask;
        }

        public Task ReceiveUserNotification(SystemNotificationDto notification)
        {
            UserNotifications.Add(notification);
            return Task.CompletedTask;
        }

        public Task ReceiveNotificationCount(int unreadCount)
        {
            NotificationCounts.Add(unreadCount);
            return Task.CompletedTask;
        }
    }

    private class FakeHubContext : IHubContext<NsdmsNotificationHub, INsdmsNotificationClient>
    {
        public IHubClients<INsdmsNotificationClient> Clients { get; }
        public IGroupManager Groups { get; }

        public FakeHubContext(INsdmsNotificationClient client)
        {
            Clients = new FakeHubClients(client);
            Groups = new FakeGroupManager();
        }
    }

    private class FakeHubClients : IHubClients<INsdmsNotificationClient>
    {
        private readonly INsdmsNotificationClient _client;

        public FakeHubClients(INsdmsNotificationClient client)
        {
            _client = client;
        }

        public INsdmsNotificationClient All => _client;
        public INsdmsNotificationClient AllExcept(IReadOnlyList<string> excludedConnectionIds) => _client;
        public INsdmsNotificationClient Client(string connectionId) => _client;
        public INsdmsNotificationClient Clients(IReadOnlyList<string> connectionIds) => _client;
        public INsdmsNotificationClient Group(string groupName) => _client;
        public INsdmsNotificationClient Groups(IReadOnlyList<string> groupNames) => _client;
        public INsdmsNotificationClient GroupExcept(string groupName, IReadOnlyList<string> excludedConnectionIds) => _client;
        public INsdmsNotificationClient User(string userId) => _client;
        public INsdmsNotificationClient Users(IReadOnlyList<string> userIds) => _client;
    }

    private class FakeGroupManager : IGroupManager
    {
        public Task AddToGroupAsync(string connectionId, string groupName, CancellationToken cancellationToken = default) => Task.CompletedTask;
        public Task RemoveFromGroupAsync(string connectionId, string groupName, CancellationToken cancellationToken = default) => Task.CompletedTask;
    }

#pragma warning disable CS0067
    private class FakeRealtimeNotificationService : IRealtimeNotificationService
    {
        public event Action<string, string, string, string>? TaskAssignedReceived;
        public event Action<string, int, string, string, string>? WorkflowTransitionReceived;
        public event Action<SystemNotificationDto>? UserNotificationReceived;
        public event Action<string, int>? SlaWarningReceived;
#pragma warning restore CS0067

        public List<(string TaskId, string Title, string Role, string Priority)> TaskAssignments { get; } = new();
        public List<(string EntityType, int EntityId, string From, string To, string Actor)> Transitions { get; } = new();
        public List<(string Title, int HoursRemaining)> SlaWarnings { get; } = new();
        public List<(string Message, string Severity)> Alerts { get; } = new();

        public Task NotifyTaskAssignedAsync(string taskId, string taskTitle, string assignedRole, string priority)
        {
            TaskAssignments.Add((taskId, taskTitle, assignedRole, priority));
            TaskAssignedReceived?.Invoke(taskId, taskTitle, assignedRole, priority);
            return Task.CompletedTask;
        }

        public Task NotifyWorkflowTransitionAsync(string entityType, int entityId, string fromState, string toState, string actor)
        {
            Transitions.Add((entityType, entityId, fromState, toState, actor));
            WorkflowTransitionReceived?.Invoke(entityType, entityId, fromState, toState, actor);
            return Task.CompletedTask;
        }

        public Task NotifySlaWarningAsync(string taskTitle, int hoursRemaining)
        {
            SlaWarnings.Add((taskTitle, hoursRemaining));
            SlaWarningReceived?.Invoke(taskTitle, hoursRemaining);
            return Task.CompletedTask;
        }

        public Task BroadcastAlertAsync(string message, string severity)
        {
            Alerts.Add((message, severity));
            return Task.CompletedTask;
        }
    }
}
