using Nsdms.Application.Models;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

/// <summary>
/// Service interface for evaluating dynamic business rules, managing workflows,
/// and executing rule sandbox tests.
/// </summary>
public interface IBusinessRuleEngineService
{
    /// <summary>
    /// Evaluates all active rules in the specified workflow against a typed input payload.
    /// </summary>
    Task<RuleWorkflowEvaluationResult> EvaluateWorkflowAsync<T>(string workflowName, T inputPayload);

    /// <summary>
    /// Executes an interactive rule test against a sample JSON payload in the testing sandbox.
    /// </summary>
    Task<RuleSandboxTestResult> TestRuleExpressionAsync(RuleSandboxTestRequest request);

    /// <summary>
    /// Retrieves all registered business rule workflows with their constituent rules.
    /// </summary>
    Task<List<BusinessRuleWorkflow>> GetAllWorkflowsAsync(string? category = null);

    /// <summary>
    /// Retrieves a single business rule workflow by identifier including all child rules.
    /// </summary>
    Task<BusinessRuleWorkflow?> GetWorkflowByIdAsync(int id);

    /// <summary>
    /// Creates a new business rule workflow grouping.
    /// </summary>
    Task<BusinessRuleWorkflow> CreateWorkflowAsync(BusinessRuleWorkflow workflow, string currentUsername = "SYSTEM");

    /// <summary>
    /// Updates workflow metadata.
    /// </summary>
    Task<BusinessRuleWorkflow> UpdateWorkflowAsync(BusinessRuleWorkflow workflow, string currentUsername = "SYSTEM");

    /// <summary>
    /// Creates or updates a constituent business rule within a workflow.
    /// </summary>
    Task<BusinessRule> UpsertRuleAsync(BusinessRule rule, string currentUsername = "SYSTEM");

    /// <summary>
    /// Deactivates / removes a rule by identifier.
    /// </summary>
    Task<bool> DeleteRuleAsync(int ruleId, string currentUsername = "SYSTEM");

    /// <summary>
    /// Clears the in-memory compiled rule engine cache to force reload from the database.
    /// </summary>
    void InvalidateCache();
}
