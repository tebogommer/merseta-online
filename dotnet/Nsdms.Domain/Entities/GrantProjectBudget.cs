using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Detailed cost item and financial budget breakdown submitted in a Discretionary Grant application.
/// </summary>
public class GrantProjectBudget : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the parent GrantApplication.
    /// </summary>
    public int GrantApplicationId { get; set; }

    /// <summary>
    /// Navigational reference to the parent GrantApplication.
    /// </summary>
    public GrantApplication? GrantApplication { get; set; }

    /// <summary>
    /// Optional foreign key referencing the specific StrategicPriority theme this line item finances.
    /// </summary>
    public int? StrategicPriorityId { get; set; }

    /// <summary>
    /// Navigational reference to the StrategicPriority theme.
    /// </summary>
    public StrategicPriority? StrategicPriority { get; set; }

    /// <summary>
    /// Expense classification category (e.g. TUITION, STIPEND, PPE_SAFETY, LEARNER_ALLOWANCE, ASSESSMENTS).
    /// </summary>
    public string ExpenseCategory { get; set; } = string.Empty;

    /// <summary>
    /// Detailed specification and cost justification.
    /// </summary>
    public string? Description { get; set; }

    /// <summary>
    /// Unit cost per beneficiary / deliverable unit in ZAR.
    /// </summary>
    public decimal UnitCost { get; set; }

    /// <summary>
    /// Total number of beneficiary or item units budgeted.
    /// </summary>
    public int Quantity { get; set; }

    /// <summary>
    /// Total aggregate line item cost (UnitCost * Quantity) in ZAR.
    /// </summary>
    public decimal TotalCost { get; set; }
}
