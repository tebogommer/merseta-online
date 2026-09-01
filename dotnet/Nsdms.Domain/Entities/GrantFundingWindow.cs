using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Gazette-announced Discretionary Grant funding window opening and allocation cycle.
/// </summary>
public class GrantFundingWindow : BaseEntity
{
    /// <summary>
    /// Financial scheme year for this funding allocation window (e.g. 2026).
    /// </summary>
    public int FinYear { get; set; }

    /// <summary>
    /// Descriptive window name (e.g. 2026/27 Discretionary Grant Funding Window 1).
    /// </summary>
    public string WindowName { get; set; } = string.Empty;

    /// <summary>
    /// Grant funding type code (e.g. PIVOTAL, APPRENTICESHIP, SKILLS_PROGRAMME, BURSARY).
    /// </summary>
    public string? GrantTypeCode { get; set; }

    /// <summary>
    /// Official window opening date and time for employer application submissions.
    /// </summary>
    public DateTime OpeningDate { get; set; }

    /// <summary>
    /// Hard deadline closing date and time after which no new applications are accepted.
    /// </summary>
    public DateTime ClosingDate { get; set; }

    /// <summary>
    /// Total aggregate discretionary budget allocated to this funding window in ZAR.
    /// </summary>
    public decimal TotalAvailableBudget { get; set; }

    /// <summary>
    /// Indicates whether this funding window is active and accepting submissions.
    /// </summary>
    public bool IsActive { get; set; } = true;

    /// <summary>
    /// Optional gazette reference or policy notice circular reference.
    /// </summary>
    public string? Description { get; set; }

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public bool IsOpen => IsActive && OpeningDate <= DateTime.UtcNow && ClosingDate >= DateTime.UtcNow;

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public string StatusDisplay => !IsActive ? "Closed" : (DateTime.UtcNow < OpeningDate ? "Upcoming" : (DateTime.UtcNow > ClosingDate ? "Expired" : "Open"));

    /// <summary>
    /// Applications submitted against this funding window.
    /// </summary>
    public ICollection<GrantApplication> Applications { get; set; } = new List<GrantApplication>();

    /// <summary>
    /// Strategic priority allocations and sub-budget envelopes gazetted under this funding window.
    /// </summary>
    public ICollection<FundingWindowPriority> StrategicPriorities { get; set; } = new List<FundingWindowPriority>();
}
