using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Gazetted Strategic Priority sub-budget envelope and beneficiary quota allocated to a Discretionary Grant Funding Window.
/// </summary>
public class FundingWindowPriority : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the parent GrantFundingWindow.
    /// </summary>
    public int FundingWindowId { get; set; }

    /// <summary>
    /// Navigational reference to the funding window.
    /// </summary>
    public GrantFundingWindow? FundingWindow { get; set; }

    /// <summary>
    /// Foreign key referencing the StrategicPriority theme.
    /// </summary>
    public int StrategicPriorityId { get; set; }

    /// <summary>
    /// Navigational reference to the StrategicPriority theme.
    /// </summary>
    public StrategicPriority? StrategicPriority { get; set; }

    /// <summary>
    /// Monetary sub-budget allocation envelope dedicated to this theme in ZAR.
    /// </summary>
    public decimal AllocatedBudget { get; set; }

    /// <summary>
    /// Target number of learners/beneficiaries planned for this strategic priority.
    /// </summary>
    public int TargetBeneficiaries { get; set; }

    /// <summary>
    /// Minimum technical evaluation score threshold required for approval under this theme (e.g. 65.00%).
    /// </summary>
    public decimal MinScoreThreshold { get; set; } = 65.00m;

    /// <summary>
    /// If true, funds under this priority cannot be vired or reallocated to other themes without MANCO approval.
    /// </summary>
    public bool IsRingFenced { get; set; } = false;

    /// <summary>
    /// Indicates whether this theme allocation is active within the funding window.
    /// </summary>
    public bool IsActive { get; set; } = true;
}
