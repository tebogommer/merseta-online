namespace Nsdms.Application.Services;

/// <summary>
/// Real-time SignalR notification dispatcher contract.
/// </summary>
public interface IRealtimeNotificationService
{
    /// <summary>
    /// Event fired when a new task is assigned.
    /// </summary>
    event Action<string, string, string, string>? TaskAssignedReceived;

    /// <summary>
    /// Event fired when a workflow state transition completes.
    /// </summary>
    event Action<string, int, string, string, string>? WorkflowTransitionReceived;

    /// <summary>
    /// Broadcasts an instant task assignment event to the assigned role group.
    /// </summary>
    Task NotifyTaskAssignedAsync(string taskId, string taskTitle, string assignedRole, string priority);

    /// <summary>
    /// Broadcasts a workflow state transition event across active connected clients.
    /// </summary>
    Task NotifyWorkflowTransitionAsync(string entityType, int entityId, string fromState, string toState, string actor);

    /// <summary>
    /// Broadcasts a high-priority system-wide alert.
    /// </summary>
    Task BroadcastAlertAsync(string message, string severity);
}
