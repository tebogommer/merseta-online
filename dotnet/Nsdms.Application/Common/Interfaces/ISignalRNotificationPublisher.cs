using Nsdms.Domain.Entities;

namespace Nsdms.Application.Common.Interfaces;

public interface ISignalRNotificationPublisher
{
    Task PublishNotificationAsync(SystemNotificationDto notification);
    Task BroadcastTaskEventAsync(string taskId, string taskTitle, string assignedRole, string priority);
    Task BroadcastWorkflowTransitionAsync(string entityType, int entityId, string fromState, string toState, string actor);
}
