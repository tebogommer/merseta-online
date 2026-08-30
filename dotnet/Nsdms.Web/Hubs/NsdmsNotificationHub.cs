using Microsoft.AspNetCore.SignalR;

namespace Nsdms.Web.Hubs;

public interface INsdmsNotificationClient
{
    Task ReceiveTaskNotification(string taskId, string taskTitle, string assignedRole, string priority);
    Task ReceiveWorkflowTransition(string entityType, int entityId, string fromState, string toState, string actor);
    Task ReceiveSystemAlert(string message, string severity);
}

public class NsdmsNotificationHub : Hub<INsdmsNotificationClient>
{
    public async Task JoinRoleGroup(string roleName)
    {
        if (!string.IsNullOrWhiteSpace(roleName))
        {
            await Groups.AddToGroupAsync(Context.ConnectionId, roleName);
        }
    }

    public async Task LeaveRoleGroup(string roleName)
    {
        if (!string.IsNullOrWhiteSpace(roleName))
        {
            await Groups.RemoveFromGroupAsync(Context.ConnectionId, roleName);
        }
    }
}
