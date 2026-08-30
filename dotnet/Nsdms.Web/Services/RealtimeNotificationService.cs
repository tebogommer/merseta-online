using Microsoft.AspNetCore.SignalR;
using Microsoft.Extensions.Logging;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Web.Hubs;

namespace Nsdms.Web.Services;

public class RealtimeNotificationService : IRealtimeNotificationService, ISignalRNotificationPublisher
{
    private readonly IHubContext<NsdmsNotificationHub, INsdmsNotificationClient> _hubContext;
    private readonly ILogger<RealtimeNotificationService> _logger;

    public static event Action<string, string, string, string>? GlobalTaskAssigned;
    public static event Action<string, int, string, string, string>? GlobalWorkflowTransition;
    public static event Action<SystemNotificationDto>? GlobalUserNotification;

    public event Action<string, string, string, string>? TaskAssignedReceived
    {
        add => GlobalTaskAssigned += value;
        remove => GlobalTaskAssigned -= value;
    }

    public event Action<string, int, string, string, string>? WorkflowTransitionReceived
    {
        add => GlobalWorkflowTransition += value;
        remove => GlobalWorkflowTransition -= value;
    }

    public event Action<SystemNotificationDto>? UserNotificationReceived
    {
        add => GlobalUserNotification += value;
        remove => GlobalUserNotification -= value;
    }

    public RealtimeNotificationService(
        IHubContext<NsdmsNotificationHub, INsdmsNotificationClient> hubContext,
        ILogger<RealtimeNotificationService> logger)
    {
        _hubContext = hubContext;
        _logger = logger;
    }

    public async Task PublishNotificationAsync(SystemNotificationDto notification)
    {
        try
        {
            GlobalUserNotification?.Invoke(notification);

            if (!string.IsNullOrWhiteSpace(notification.RecipientUsername))
            {
                var group = $"user_{notification.RecipientUsername.Trim().ToLowerInvariant()}";
                await _hubContext.Clients.Group(group).ReceiveUserNotification(notification);
            }
            else if (!string.IsNullOrWhiteSpace(notification.RecipientRole))
            {
                var group = $"role_{notification.RecipientRole.Trim().ToLowerInvariant()}";
                await _hubContext.Clients.Group(group).ReceiveUserNotification(notification);
            }
            else
            {
                await _hubContext.Clients.All.ReceiveUserNotification(notification);
            }

            _logger.LogInformation("SignalR: Published notification '{Title}' ({Type})", notification.Title, notification.NotificationType);
        }
        catch (Exception ex)
        {
            _logger.LogWarning(ex, "Failed to broadcast SignalR user notification.");
        }
    }

    public async Task BroadcastTaskEventAsync(string taskId, string taskTitle, string assignedRole, string priority)
    {
        await NotifyTaskAssignedAsync(taskId, taskTitle, assignedRole, priority);
    }

    public async Task BroadcastWorkflowTransitionAsync(string entityType, int entityId, string fromState, string toState, string actor)
    {
        await NotifyWorkflowTransitionAsync(entityType, entityId, fromState, toState, actor);
    }

    public async Task NotifyTaskAssignedAsync(string taskId, string taskTitle, string assignedRole, string priority)
    {
        try
        {
            GlobalTaskAssigned?.Invoke(taskId, taskTitle, assignedRole, priority);

            if (!string.IsNullOrEmpty(assignedRole))
            {
                var group = $"role_{assignedRole.Trim().ToLowerInvariant()}";
                await _hubContext.Clients.Group(group).ReceiveTaskNotification(taskId, taskTitle, assignedRole, priority);
            }
            await _hubContext.Clients.All.ReceiveTaskNotification(taskId, taskTitle, assignedRole, priority);
            _logger.LogInformation("SignalR: Dispatched task assignment notification for '{Title}' to role '{Role}'", taskTitle, assignedRole);
        }
        catch (Exception ex)
        {
            _logger.LogWarning(ex, "Failed to broadcast SignalR task assignment notification.");
        }
    }

    public async Task NotifyWorkflowTransitionAsync(string entityType, int entityId, string fromState, string toState, string actor)
    {
        try
        {
            GlobalWorkflowTransition?.Invoke(entityType, entityId, fromState, toState, actor);

            await _hubContext.Clients.All.ReceiveWorkflowTransition(entityType, entityId, fromState, toState, actor);
            _logger.LogInformation("SignalR: Dispatched workflow transition notification for {EntityType} #{EntityId} ({From} -> {To})", entityType, entityId, fromState, toState);
        }
        catch (Exception ex)
        {
            _logger.LogWarning(ex, "Failed to broadcast SignalR workflow transition notification.");
        }
    }

    public async Task BroadcastAlertAsync(string message, string severity)
    {
        try
        {
            await _hubContext.Clients.All.ReceiveSystemAlert(message, severity);
            _logger.LogInformation("SignalR: Dispatched system-wide alert: {Message}", message);
        }
        catch (Exception ex)
        {
            _logger.LogWarning(ex, "Failed to broadcast SignalR system alert.");
        }
    }
}
