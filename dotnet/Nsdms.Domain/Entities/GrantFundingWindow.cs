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

    /// <summary>
    /// Dual Authorisation approval lifecycle status (Draft, PendingApproval, Active, Closed, Rejected).
    /// </summary>
    public string ApprovalStatusCode { get; set; } = "Active";

    /// <summary>
    /// User ID of the officer who proposed the funding window and allocation schedule.
    /// </summary>
    public string? ProposedByUserId { get; set; }

    /// <summary>
    /// Timestamp when the funding window proposal was lodged.
    /// </summary>
    public DateTime? ProposedDate { get; set; }

    /// <summary>
    /// User ID of the independent authority who approved and activated the funding window.
    /// Segregation of duties invariant: ApprovedByUserId != ProposedByUserId.
    /// </summary>
    public string? ApprovedByUserId { get; set; }

    /// <summary>
    /// Timestamp when the funding window was formally approved.
    /// </summary>
    public DateTime? ApprovedDate { get; set; }

    /// <summary>
    /// Gazette citation, MANCO resolution, or approval comments.
    /// </summary>
    public string? ApprovalJustification { get; set; }

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public bool IsOpen => IsActive && ApprovalStatusCode == "Active" && OpeningDate <= DateTime.UtcNow && ClosingDate >= DateTime.UtcNow;

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public string StatusDisplay => !IsActive || ApprovalStatusCode == "Closed" 
        ? "Closed" 
        : (ApprovalStatusCode == "Draft" 
            ? "Draft" 
            : (ApprovalStatusCode == "PendingApproval" 
                ? "Pending Approval" 
                : (ApprovalStatusCode == "Rejected" 
                    ? "Rejected" 
                    : (DateTime.UtcNow < OpeningDate 
                        ? "Upcoming" 
                        : (DateTime.UtcNow > ClosingDate ? "Expired" : "Open")))));

    /// <summary>
    /// Applications submitted against this funding window.
    /// </summary>
    public ICollection<GrantApplication> Applications { get; set; } = new List<GrantApplication>();

    /// <summary>
    /// Strategic priority allocations and sub-budget envelopes gazetted under this funding window.
    /// </summary>
    public ICollection<FundingWindowPriority> StrategicPriorities { get; set; } = new List<FundingWindowPriority>();

    /// <summary>
    /// Indicates whether this funding window is PIVOTAL (accredited qualification/unit-standard bearing) or Non-PIVOTAL (project/equipment/capacity).
    /// </summary>
    public bool IsPivotal { get; set; } = true;

    /// <summary>
    /// Window strategic classification (Pivotal, NonPivotal, Hybrid).
    /// </summary>
    public string WindowClassification { get; set; } = "Pivotal";

    /// <summary>
    /// Enforce prior-year Mandatory Grant (WSP/ATR) submission compliance as an eligibility pre-condition.
    /// Toggleable: some DG windows open before or independently of the WSP window.
    /// </summary>
    public bool RequireWspCompliance { get; set; } = false;

    /// <summary>
    /// Optional Blueprint Template from which this funding window was initialized.
    /// </summary>
    public int? TemplateId { get; set; }
    public GrantWindowTemplate? Template { get; set; }

    /// <summary>
    /// Explicit whitelist of stakeholder organization categories eligible to apply for this window.
    /// </summary>
    public ICollection<GrantWindowEligibility> EligibleStakeholders { get; set; } = new List<GrantWindowEligibility>();

    /// <summary>
    /// Whitelist of skills development and project interventions permitted under this window.
    /// </summary>
    public ICollection<GrantWindowIntervention> AllowedInterventions { get; set; } = new List<GrantWindowIntervention>();
}
