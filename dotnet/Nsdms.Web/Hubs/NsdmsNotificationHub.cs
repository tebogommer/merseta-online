using Microsoft.AspNetCore.SignalR;
using Nsdms.Domain.Entities;

namespace Nsdms.Web.Hubs;

public interface INsdmsNotificationClient
{
    Task ReceiveTaskNotification(string taskId, string taskTitle, string assignedRole, string priority);
    Task ReceiveWorkflowTransition(string entityType, int entityId, string fromState, string toState, string actor);
    Task ReceiveSystemAlert(string message, string severity);
    Task ReceiveUserNotification(SystemNotificationDto notification);
    Task ReceiveNotificationCount(int unreadCount);
}

public class NsdmsNotificationHub : Hub<INsdmsNotificationClient>
{
    public async Task JoinUserGroup(string username)
    {
        if (!string.IsNullOrWhiteSpace(username))
        {
            await Groups.AddToGroupAsync(Context.ConnectionId, $"user_{username.Trim().ToLowerInvariant()}");
        }
    }

    public async Task JoinRoleGroup(string roleName)
    {
        if (!string.IsNullOrWhiteSpace(roleName))
        {
            await Groups.AddToGroupAsync(Context.ConnectionId, $"role_{roleName.Trim().ToLowerInvariant()}");
        }
    }

    public async Task LeaveRoleGroup(string roleName)
    {
        if (!string.IsNullOrWhiteSpace(roleName))
        {
            await Groups.RemoveFromGroupAsync(Context.ConnectionId, $"role_{roleName.Trim().ToLowerInvariant()}");
        }
    }
}

