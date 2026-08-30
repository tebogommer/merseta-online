using Nsdms.Domain.Entities;
using System.Security.Claims;

namespace Nsdms.Application.Services;

public record WorkflowActionResult(
    bool Success, 
    string Message, 
    WorkflowInstance? Instance = null,
    string? NewStateName = null
);

public interface IWorkflowEngineService
{
    Task<WorkflowInstance?> GetInstanceByEntityAsync(string processCode, int entityId);
    Task<WorkflowInstance?> GetInstanceByIdAsync(int instanceId);
    Task<List<WorkflowTransition>> GetAvailableTransitionsAsync(int instanceId, ClaimsPrincipal? user);
    Task<WorkflowActionResult> StartWorkflowAsync(string processCode, int entityId, string entityTitle, string entityRef, string initiatorUserId, string initiatorName);
    Task<WorkflowActionResult> AdvanceWorkflowAsync(int instanceId, int transitionId, string actorUserId, string actorName, string actorRole, string? comments = null);
    Task<List<WorkflowTask>> GetUserTasksAsync(string? userRole = null, string? userId = null);
    Task<WorkflowTask?> ClaimTaskAsync(int taskId, string userId, string userName);
    Task<List<WorkflowHistory>> GetWorkflowHistoryAsync(int instanceId);
    Task<List<WorkflowNotification>> GetUserNotificationsAsync(string userId, bool unreadOnly = false);
    Task<bool> MarkNotificationReadAsync(int notificationId);
    Task<int> GetPendingTaskCountAsync(string? userRole = null, string? userId = null);
}
