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

    /// <summary>
    /// Retrieves all workflow definitions with states, transitions, and instances included.
    /// </summary>
    Task<List<WorkflowDefinition>> GetAllDefinitionsAsync();

    /// <summary>
    /// Retrieves a workflow definition by its primary key ID with all related states, transitions, and instances.
    /// </summary>
    Task<WorkflowDefinition?> GetDefinitionByIdAsync(int id);

    /// <summary>
    /// Creates or updates a workflow definition, recording an audit log entry.
    /// </summary>
    Task<WorkflowDefinition> SaveDefinitionAsync(WorkflowDefinition definition, string actorUserId, string actorName);

    /// <summary>
    /// Deactivates or removes a workflow definition.
    /// </summary>
    Task<bool> DeleteDefinitionAsync(int id, string actorUserId, string actorName);

    /// <summary>
    /// Creates or updates a lifecycle state on a workflow definition.
    /// </summary>
    Task<WorkflowState> SaveStateAsync(WorkflowState state, string actorUserId, string actorName);

    /// <summary>
    /// Deletes a lifecycle state if no active instances are currently in this state.
    /// </summary>
    Task<bool> DeleteStateAsync(int stateId, string actorUserId, string actorName);

    /// <summary>
    /// Creates or updates a state transition rule.
    /// </summary>
    Task<WorkflowTransition> SaveTransitionAsync(WorkflowTransition transition, string actorUserId, string actorName);

    /// <summary>
    /// Deletes a state transition rule.
    /// </summary>
    Task<bool> DeleteTransitionAsync(int transitionId, string actorUserId, string actorName);

    /// <summary>
    /// Retrieves all execution instances running a specific workflow definition.
    /// </summary>
    Task<List<WorkflowInstance>> GetInstancesByDefinitionIdAsync(int definitionId);

    /// <summary>
    /// Retrieves document requirement rules for a workflow process.
    /// </summary>
    Task<List<DocumentRequirementRule>> GetDocumentRequirementsAsync(string processCode);

    /// <summary>
    /// Saves or updates a document requirement rule.
    /// </summary>
    Task<DocumentRequirementRule> SaveDocumentRequirementAsync(DocumentRequirementRule rule, string actorUserId, string actorName);

    /// <summary>
    /// Deletes a document requirement rule.
    /// </summary>
    Task<bool> DeleteDocumentRequirementAsync(int ruleId, string actorUserId, string actorName);

    /// <summary>
    /// Clones an existing workflow blueprint with its states and transitions into a new blueprint.
    /// </summary>
    Task<WorkflowDefinition> CloneDefinitionAsync(int sourceDefinitionId, string newCode, string newName, string actorUserId, string actorName);
}
