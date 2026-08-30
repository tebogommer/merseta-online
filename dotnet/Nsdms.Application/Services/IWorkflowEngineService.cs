using Nsdms.Domain.Entities;
using System.Security.Claims;

namespace Nsdms.Application.Services;

/// <summary>
/// Execution outcome of a workflow state mutation or advancement action.
/// </summary>
/// <param name="Success">Whether the transition succeeded.</param>
/// <param name="Message">User-facing result message or validation error.</param>
/// <param name="Instance">The updated workflow instance.</param>
/// <param name="NewStateName">The resulting state name.</param>
public record WorkflowActionResult(
    bool Success, 
    string Message, 
    WorkflowInstance? Instance = null,
    string? NewStateName = null
);

/// <summary>
/// Universal Workflow &amp; BPM Orchestration Engine contract for managing state machines, gates, and task queues.
/// </summary>
public interface IWorkflowEngineService
{
    /// <summary>
    /// Retrieves the active workflow instance for a specific domain entity.
    /// </summary>
    Task<WorkflowInstance?> GetInstanceByEntityAsync(string processCode, int entityId);

    /// <summary>
    /// Retrieves a workflow instance by its primary key ID with states and definition loaded.
    /// </summary>
    Task<WorkflowInstance?> GetInstanceByIdAsync(int instanceId);

    /// <summary>
    /// Retrieves all sequential steps / gates for a workflow process definition ordered by StepOrder.
    /// </summary>
    Task<List<WorkflowState>> GetWorkflowDefinitionStatesAsync(string processCode);

    /// <summary>
    /// Retrieves or initializes a workflow instance for an entity if not already active.
    /// </summary>
    Task<WorkflowInstance?> GetOrInitiateInstanceAsync(string processCode, int entityId, string entityTitle, string entityRef, string initiatorUserId, string initiatorName);

    /// <summary>
    /// Evaluates authorized transitions available from the instance's current state.
    /// </summary>
    Task<List<WorkflowTransition>> GetAvailableTransitionsAsync(int instanceId, ClaimsPrincipal? user);

    /// <summary>
    /// Initializes and starts a new workflow execution instance for a target entity.
    /// </summary>
    Task<WorkflowActionResult> StartWorkflowAsync(string processCode, int entityId, string entityTitle, string entityRef, string initiatorUserId, string initiatorName);

    /// <summary>
    /// Advances the workflow instance across an authorized transition, validating guard rules and syncing entity status.
    /// </summary>
    Task<WorkflowActionResult> AdvanceWorkflowAsync(int instanceId, int transitionId, string actorUserId, string actorName, string actorRole, string? comments = null);

    /// <summary>
    /// Synchronizes the parent domain entity's high-level status code with double-write audit logging.
    /// </summary>
    Task<bool> SyncEntityStatusAsync(string processCode, int entityId, string statusCode, string actor = "SYSTEM");

    /// <summary>
    /// Retrieves open or claimed tasks filtered by role or user.
    /// </summary>
    Task<List<WorkflowTask>> GetUserTasksAsync(string? userRole = null, string? userId = null);

    /// <summary>
    /// Claims an unassigned open task for an individual user.
    /// </summary>
    Task<WorkflowTask?> ClaimTaskAsync(int taskId, string userId, string userName);

    /// <summary>
    /// Retrieves complete historical audit trail of state transitions and review comments.
    /// </summary>
    Task<List<WorkflowHistory>> GetWorkflowHistoryAsync(int instanceId);

    /// <summary>
    /// Retrieves workflow notifications for a user.
    /// </summary>
    Task<List<WorkflowNotification>> GetUserNotificationsAsync(string userId, bool unreadOnly = false);

    /// <summary>
    /// Marks a notification as read.
    /// </summary>
    Task<bool> MarkNotificationReadAsync(int notificationId);

    /// <summary>
    /// Returns the total count of pending actionable tasks for a role/user.
    /// </summary>
    Task<int> GetPendingTaskCountAsync(string? userRole = null, string? userId = null);
}
