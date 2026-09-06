namespace Nsdms.Application.Models;

/// <summary>
/// Detailed diagnostic outcome of an individual rule evaluation.
/// </summary>
public class RuleFailureDetail
{
    public string RuleName { get; set; } = string.Empty;
    public string ErrorMessage { get; set; } = string.Empty;
    public string Severity { get; set; } = "Fatal";
    public string? Expression { get; set; }
}

/// <summary>
/// Consolidated result of evaluating a business rule workflow against an input payload.
/// </summary>
public class RuleWorkflowEvaluationResult
{
    public bool IsSuccess { get; set; }
    public string WorkflowName { get; set; } = string.Empty;
    public int TotalRulesEvaluated { get; set; }
    public int PassedCount { get; set; }
    public int FailedCount { get; set; }
    public List<RuleFailureDetail> Failures { get; set; } = new();
    public List<string> PassedRuleNames { get; set; } = new();
    public string SummaryMessage { get; set; } = string.Empty;
    public long ExecutionDurationMs { get; set; }
}

/// <summary>
/// Request payload for testing an individual rule expression in the interactive sandbox.
/// </summary>
public class RuleSandboxTestRequest
{
    public string Expression { get; set; } = string.Empty;
    public string SampleJsonPayload { get; set; } = "{}";
}

/// <summary>
/// Diagnostic response returned by the interactive rule testing sandbox.
/// </summary>
public class RuleSandboxTestResult
{
    public bool IsSuccess { get; set; }
    public bool ExpressionEvaluatedToTrue { get; set; }
    public string? ErrorMessage { get; set; }
    public string? DiagnosticDetails { get; set; }
    public long ExecutionDurationMs { get; set; }
}

/// <summary>
/// Flattened representation of a learner application passed into the STP rules engine.
/// </summary>
public class LearnerStpPayload
{
    public int PersonId { get; set; }
    public double Age { get; set; }
    public bool HasMinorGuardian { get; set; }
    public bool HasValidEmployer { get; set; }
    public bool IsEmployerActive { get; set; }
    public bool IsUnemployedBursary { get; set; }
    public bool HasSaqaQualification { get; set; }
    public bool IsQualificationActive { get; set; }
    public int WorkingDaysElapsed { get; set; }
    public bool IsContinuationBursary { get; set; }
    public bool HasPredecessorBursary { get; set; }
    public bool HasPassedTranscripts { get; set; }
}
