using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Represents a cohesive grouping of statutory or operational business rules
/// evaluated as a unified decision pipeline (e.g. LearnerStpEvaluation, FinancialClaimApproval).
/// </summary>
[Table("BusinessRuleWorkflow")]
public class BusinessRuleWorkflow : BaseEntity
{
    /// <summary>
    /// Unique programmatic identifier for the workflow (e.g. LearnerStpEvaluation, ArplTradeEligibility).
    /// </summary>
    [Required]
    [MaxLength(100)]
    public string WorkflowName { get; set; } = string.Empty;

    /// <summary>
    /// Human-readable title describing the purpose of the business rule workflow.
    /// </summary>
    [Required]
    [MaxLength(200)]
    public string Title { get; set; } = string.Empty;

    /// <summary>
    /// Detailed statutory citation or operational rationale for this rule workflow.
    /// </summary>
    [MaxLength(1000)]
    public string? Description { get; set; }

    /// <summary>
    /// Functional domain grouping category (e.g. LearnerRegistration, Finance, TradeAssessment, QualityAssurance).
    /// </summary>
    [Required]
    [MaxLength(50)]
    public string Category { get; set; } = "General";

    /// <summary>
    /// Indicates whether the entire workflow is enabled for live evaluation.
    /// </summary>
    public bool IsActive { get; set; } = true;

    /// <summary>
    /// Collection of constituent rules evaluated as part of this workflow.
    /// </summary>
    public virtual ICollection<BusinessRule> Rules { get; set; } = new List<BusinessRule>();
}

/// <summary>
/// Represents an individual business rule containing a dynamic C# lambda expression,
/// error diagnostics, and evaluation order.
/// </summary>
[Table("BusinessRule")]
public class BusinessRule : BaseEntity
{
    /// <summary>
    /// Foreign key reference to the parent workflow grouping.
    /// </summary>
    public int BusinessRuleWorkflowId { get; set; }

    /// <summary>
    /// Navigation property to the parent workflow grouping.
    /// </summary>
    [ForeignKey(nameof(BusinessRuleWorkflowId))]
    public virtual BusinessRuleWorkflow? Workflow { get; set; }

    /// <summary>
    /// Unique rule name identifier within the workflow (e.g. MinimumAgeGate, ActiveEmployerGate).
    /// </summary>
    [Required]
    [MaxLength(100)]
    public string RuleName { get; set; } = string.Empty;

    /// <summary>
    /// Dynamic C# lambda expression evaluated by the rule engine (e.g. &quot;Age &gt;= 18&quot;, &quot;ClaimAmount &lt;= 500000&quot;).
    /// </summary>
    [Required]
    [MaxLength(2000)]
    public string Expression { get; set; } = string.Empty;

    /// <summary>
    /// Expression syntax type (defaults to "LambdaExpression").
    /// </summary>
    [Required]
    [MaxLength(50)]
    public string RuleExpressionType { get; set; } = "LambdaExpression";

    /// <summary>
    /// Human-readable error message emitted when the rule expression evaluates to false.
    /// </summary>
    [Required]
    [MaxLength(500)]
    public string ErrorMessage { get; set; } = string.Empty;

    /// <summary>
    /// Optional confirmation or compliance note emitted when the rule expression evaluates to true.
    /// </summary>
    [MaxLength(500)]
    public string? SuccessMessage { get; set; }

    /// <summary>
    /// Diagnostic failure severity: Fatal (blocks transaction), Warning (requires review), Advisory.
    /// </summary>
    [Required]
    [MaxLength(30)]
    public string Severity { get; set; } = "Fatal";

    /// <summary>
    /// Evaluation sequence order index within the workflow pipeline.
    /// </summary>
    public int OrderIndex { get; set; } = 1;

    /// <summary>
    /// Toggle determining whether this specific rule is active within the workflow.
    /// </summary>
    public bool Enabled { get; set; } = true;

    /// <summary>
    /// Soft-delete and active lifecycle flag.
    /// </summary>
    public bool IsActive { get; set; } = true;
}
