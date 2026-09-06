using System.Diagnostics;
using System.Dynamic;
using System.Text.Json;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Models;
using Nsdms.Domain.Entities;
using RulesEngine.Models;

namespace Nsdms.Application.Services;

/// <summary>
/// Implements dynamic business rule compilation, caching, and execution using RulesEngine.
/// Integrates with database-stored rule definitions and provides audit logging.
/// </summary>
public class BusinessRuleEngineService : IBusinessRuleEngineService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;
    private RulesEngine.RulesEngine? _cachedEngine;
    private readonly SemaphoreSlim _semaphore = new(1, 1);

    public BusinessRuleEngineService(INsdmsDbContextFactory contextFactory, IAuditService audit)
    {
        _contextFactory = contextFactory;
        _audit = audit;
    }

    public void InvalidateCache()
    {
        _cachedEngine = null;
    }

    private async Task<RulesEngine.RulesEngine> GetOrCreateEngineAsync()
    {
        if (_cachedEngine != null)
        {
            return _cachedEngine;
        }

        await _semaphore.WaitAsync();
        try
        {
            if (_cachedEngine != null)
            {
                return _cachedEngine;
            }

            using var db = await _contextFactory.CreateDbContextAsync();
            var workflows = await db.BusinessRuleWorkflows
                .Include(w => w.Rules)
                .Where(w => w.IsActive)
                .AsNoTracking()
                .ToListAsync();

            var reWorkflows = new List<Workflow>();

            foreach (var w in workflows)
            {
                var activeRules = w.Rules
                    .Where(r => r.Enabled && r.IsActive)
                    .OrderBy(r => r.OrderIndex)
                    .Select(r => new Rule
                    {
                        RuleName = r.RuleName,
                        Expression = r.Expression,
                        RuleExpressionType = RuleExpressionType.LambdaExpression,
                        ErrorMessage = r.ErrorMessage,
                        SuccessEvent = r.SuccessMessage
                    })
                    .ToList();

                if (activeRules.Count > 0)
                {
                    reWorkflows.Add(new Workflow
                    {
                        WorkflowName = w.WorkflowName,
                        Rules = activeRules
                    });
                }
            }

            // Fallback default workflows if database is not yet migrated or seeded
            EnsureDefaultWorkflows(reWorkflows);

            _cachedEngine = new RulesEngine.RulesEngine(reWorkflows.ToArray());
            return _cachedEngine;
        }
        finally
        {
            _semaphore.Release();
        }
    }

    public async Task<RuleWorkflowEvaluationResult> EvaluateWorkflowAsync<T>(string workflowName, T inputPayload)
    {
        var sw = Stopwatch.StartNew();
        var engine = await GetOrCreateEngineAsync();

        var resultList = await engine.ExecuteAllRulesAsync(workflowName, inputPayload);
        sw.Stop();

        var evaluation = new RuleWorkflowEvaluationResult
        {
            WorkflowName = workflowName,
            TotalRulesEvaluated = resultList.Count,
            ExecutionDurationMs = sw.ElapsedMilliseconds
        };

        foreach (var res in resultList)
        {
            if (res.IsSuccess)
            {
                evaluation.PassedCount++;
                evaluation.PassedRuleNames.Add(res.Rule.RuleName);
            }
            else
            {
                evaluation.FailedCount++;
                evaluation.Failures.Add(new RuleFailureDetail
                {
                    RuleName = res.Rule.RuleName,
                    ErrorMessage = !string.IsNullOrWhiteSpace(res.Rule.ErrorMessage) 
                        ? res.Rule.ErrorMessage 
                        : (res.ExceptionMessage ?? "Rule criteria not satisfied."),
                    Severity = "Fatal",
                    Expression = res.Rule.Expression
                });
            }
        }

        evaluation.IsSuccess = evaluation.FailedCount == 0;
        evaluation.SummaryMessage = evaluation.IsSuccess
            ? $"All {evaluation.PassedCount} business rules in '{workflowName}' passed successfully."
            : $"{evaluation.FailedCount} of {evaluation.TotalRulesEvaluated} business rules in '{workflowName}' failed.";

        return evaluation;
    }

    public async Task<RuleSandboxTestResult> TestRuleExpressionAsync(RuleSandboxTestRequest request)
    {
        var sw = Stopwatch.StartNew();
        try
        {
            // Build dynamic JSON input object
            object inputObj;
            if (string.IsNullOrWhiteSpace(request.SampleJsonPayload) || request.SampleJsonPayload.Trim() == "{}")
            {
                inputObj = new ExpandoObject();
            }
            else
            {
                using var doc = JsonDocument.Parse(request.SampleJsonPayload);
                inputObj = ConvertJsonElementToDynamic(doc.RootElement);
            }

            var testWorkflow = new Workflow
            {
                WorkflowName = "SandboxTestWorkflow",
                Rules = new List<Rule>
                {
                    new()
                    {
                        RuleName = "SandboxRule",
                        Expression = request.Expression,
                        RuleExpressionType = RuleExpressionType.LambdaExpression,
                        ErrorMessage = "Expression evaluated to false."
                    }
                }
            };

            var sandboxEngine = new RulesEngine.RulesEngine(new[] { testWorkflow });
            var results = await sandboxEngine.ExecuteAllRulesAsync("SandboxTestWorkflow", inputObj);
            sw.Stop();

            var firstRes = results.FirstOrDefault();
            bool isSuccess = firstRes?.IsSuccess ?? false;

            return new RuleSandboxTestResult
            {
                IsSuccess = true,
                ExpressionEvaluatedToTrue = isSuccess,
                ErrorMessage = isSuccess ? null : (firstRes?.ExceptionMessage ?? firstRes?.Rule.ErrorMessage ?? "Expression evaluated to false."),
                DiagnosticDetails = isSuccess ? "Expression successfully evaluated to true against the supplied JSON payload." : $"Expression failed or evaluated to false. Result: {firstRes?.Rule.ErrorMessage}",
                ExecutionDurationMs = sw.ElapsedMilliseconds
            };
        }
        catch (Exception ex)
        {
            sw.Stop();
            return new RuleSandboxTestResult
            {
                IsSuccess = false,
                ExpressionEvaluatedToTrue = false,
                ErrorMessage = $"Syntax or execution error: {ex.Message}",
                DiagnosticDetails = ex.ToString(),
                ExecutionDurationMs = sw.ElapsedMilliseconds
            };
        }
    }

    public async Task<List<BusinessRuleWorkflow>> GetAllWorkflowsAsync(string? category = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.BusinessRuleWorkflows
            .Include(w => w.Rules.Where(r => r.IsActive).OrderBy(r => r.OrderIndex))
            .Where(w => w.IsActive);

        if (!string.IsNullOrWhiteSpace(category) && category != "All")
        {
            query = query.Where(w => w.Category == category);
        }

        return await query.OrderBy(w => w.Category).ThenBy(w => w.Title).ToListAsync();
    }

    public async Task<BusinessRuleWorkflow?> GetWorkflowByIdAsync(int id)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.BusinessRuleWorkflows
            .Include(w => w.Rules.Where(r => r.IsActive).OrderBy(r => r.OrderIndex))
            .FirstOrDefaultAsync(w => w.Id == id && w.IsActive);
    }

    public async Task<BusinessRuleWorkflow> CreateWorkflowAsync(BusinessRuleWorkflow workflow, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        workflow.CreatedAt = DateTime.UtcNow;
        workflow.CreatedBy = currentUsername;

        db.BusinessRuleWorkflows.Add(workflow);
        await db.SaveChangesAsync();

        await _audit.LogAsync("BusinessRuleWorkflow", workflow.Id, "CreateWorkflow", currentUsername, new
        {
            workflow.WorkflowName,
            workflow.Title,
            workflow.Category
        });

        InvalidateCache();
        return workflow;
    }

    public async Task<BusinessRuleWorkflow> UpdateWorkflowAsync(BusinessRuleWorkflow workflow, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.BusinessRuleWorkflows.FirstOrDefaultAsync(w => w.Id == workflow.Id);
        if (existing == null)
            throw new KeyNotFoundException($"BusinessRuleWorkflow with ID {workflow.Id} not found.");

        existing.Title = workflow.Title;
        existing.Description = workflow.Description;
        existing.Category = workflow.Category;
        existing.IsActive = workflow.IsActive;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        await db.SaveChangesAsync();

        await _audit.LogAsync("BusinessRuleWorkflow", existing.Id, "UpdateWorkflow", currentUsername, new
        {
            existing.WorkflowName,
            existing.Title,
            existing.IsActive
        });

        InvalidateCache();
        return existing;
    }

    public async Task<BusinessRule> UpsertRuleAsync(BusinessRule rule, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        if (rule.Id == 0)
        {
            rule.CreatedAt = DateTime.UtcNow;
            rule.CreatedBy = currentUsername;
            db.BusinessRules.Add(rule);
            await db.SaveChangesAsync();

            await _audit.LogAsync("BusinessRule", rule.Id, "CreateRule", currentUsername, new
            {
                rule.BusinessRuleWorkflowId,
                rule.RuleName,
                rule.Expression
            });
        }
        else
        {
            var existing = await db.BusinessRules.FirstOrDefaultAsync(r => r.Id == rule.Id);
            if (existing == null)
                throw new KeyNotFoundException($"BusinessRule with ID {rule.Id} not found.");

            existing.RuleName = rule.RuleName;
            existing.Expression = rule.Expression;
            existing.ErrorMessage = rule.ErrorMessage;
            existing.SuccessMessage = rule.SuccessMessage;
            existing.Severity = rule.Severity;
            existing.OrderIndex = rule.OrderIndex;
            existing.Enabled = rule.Enabled;
            existing.ModifiedAt = DateTime.UtcNow;
            existing.ModifiedBy = currentUsername;

            await db.SaveChangesAsync();

            await _audit.LogAsync("BusinessRule", existing.Id, "UpdateRule", currentUsername, new
            {
                existing.RuleName,
                existing.Expression,
                existing.Enabled
            });

            rule = existing;
        }

        InvalidateCache();
        return rule;
    }

    public async Task<bool> DeleteRuleAsync(int ruleId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var rule = await db.BusinessRules.FirstOrDefaultAsync(r => r.Id == ruleId);
        if (rule == null) return false;

        rule.IsActive = false;
        rule.ModifiedAt = DateTime.UtcNow;
        rule.ModifiedBy = currentUsername;

        await db.SaveChangesAsync();

        await _audit.LogAsync("BusinessRule", rule.Id, "DeleteRule", currentUsername, new
        {
            rule.RuleName,
            rule.BusinessRuleWorkflowId
        });

        InvalidateCache();
        return true;
    }

    private static void EnsureDefaultWorkflows(List<Workflow> reWorkflows)
    {
        if (!reWorkflows.Any(w => w.WorkflowName == "LearnerStpEvaluation"))
        {
            reWorkflows.Add(new Workflow
            {
                WorkflowName = "LearnerStpEvaluation",
                Rules = new List<Rule>
                {
                    new()
                    {
                        RuleName = "EmployerStandingGate",
                        Expression = "HasValidEmployer || IsUnemployedBursary",
                        ErrorMessage = "Employer is mandatory and must be in good standing for this programme type.",
                        RuleExpressionType = RuleExpressionType.LambdaExpression
                    },
                    new()
                    {
                        RuleName = "ActiveQualificationGate",
                        Expression = "!HasSaqaQualification || IsQualificationActive",
                        ErrorMessage = "Enrolled qualification is inactive or past the statutory last date for enrolment.",
                        RuleExpressionType = RuleExpressionType.LambdaExpression
                    },
                    new()
                    {
                        RuleName = "SubmissionWindowSlaGate",
                        Expression = "WorkingDaysElapsed <= 30",
                        ErrorMessage = "Agreement execution date exceeds 30-working-day statutory SLA. Requires officer review.",
                        RuleExpressionType = RuleExpressionType.LambdaExpression
                    },
                    new()
                    {
                        RuleName = "MinorGuardianProtectionGate",
                        Expression = "Age >= 18 || HasMinorGuardian",
                        ErrorMessage = "Minor applicant (< 18) without an active verified co-signatory guardian.",
                        RuleExpressionType = RuleExpressionType.LambdaExpression
                    },
                    new()
                    {
                        RuleName = "BursaryContinuationIntegrityGate",
                        Expression = "!IsContinuationBursary || (HasPredecessorBursary && HasPassedTranscripts)",
                        ErrorMessage = "Continuation bursary requires linked predecessor bursary and passed academic transcripts.",
                        RuleExpressionType = RuleExpressionType.LambdaExpression
                    }
                }
            });
        }
    }

    private static object ConvertJsonElementToDynamic(JsonElement element)
    {
        switch (element.ValueKind)
        {
            case JsonValueKind.Object:
                var expando = new ExpandoObject();
                var dict = (IDictionary<string, object?>)expando;
                foreach (var prop in element.EnumerateObject())
                {
                    dict[prop.Name] = ConvertJsonElementToDynamic(prop.Value);
                }
                return expando;

            case JsonValueKind.Array:
                var list = new List<object?>();
                foreach (var item in element.EnumerateArray())
                {
                    list.Add(ConvertJsonElementToDynamic(item));
                }
                return list;

            case JsonValueKind.String:
                return element.GetString() ?? string.Empty;

            case JsonValueKind.Number:
                if (element.TryGetInt64(out long l)) return l;
                if (element.TryGetDouble(out double d)) return d;
                return element.GetDecimal();

            case JsonValueKind.True:
                return true;

            case JsonValueKind.False:
                return false;

            default:
                return null!;
        }
    }
}
