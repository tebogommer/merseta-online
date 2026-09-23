using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Master Mandatory Grant (WSP / ATR) statutory submission window aggregate root.
/// Governs scheme year submission windows, statutory dates, gazette citations, dual authorisation governance,
/// and scopes eligible occupational classifications via the OFO code manager.
/// </summary>
[Table("MgWindow")]
public class MgWindow : BaseEntity
{
    /// <summary>
    /// Statutory financial scheme year (e.g. 2026 for the 2026/2027 grant cycle).
    /// </summary>
    public int SchemeYear { get; set; }

    /// <summary>
    /// Official business name (e.g. "2026/27 Mandatory Grant (WSP/ATR) Submission Window").
    /// </summary>
    [MaxLength(150)]
    public string WindowName { get; set; } = string.Empty;

    /// <summary>
    /// UTC timestamp when employer submissions open on the NSDMS portal.
    /// </summary>
    public DateTime OpeningDate { get; set; }

    /// <summary>
    /// Statutory closing deadline under Regulation 4(1) of the SETA Grant Regulations (typically 30 April).
    /// </summary>
    public DateTime ClosingDate { get; set; }

    /// <summary>
    /// Statutory extension filing deadline under Regulation 4(2) (typically 15 April).
    /// </summary>
    public DateTime ExtensionCutoffDate { get; set; }

    /// <summary>
    /// Foreign key referencing the governing DHET Gazetted OFO Set release.
    /// </summary>
    public int? OfoCodeSetId { get; set; }

    /// <summary>
    /// Denormalized OFO set year for high-performance querying and historical integrity.
    /// </summary>
    public int? OfoCodeSetYear { get; set; }

    /// <summary>
    /// Statutory authority, Government Gazette publication reference, or departmental circular citation.
    /// </summary>
    [MaxLength(200)]
    public string? GazetteReference { get; set; }

    /// <summary>
    /// Detailed operational and statutory motivation recorded for PFMA audit compliance.
    /// </summary>
    public string? Justification { get; set; }

    /// <summary>
    /// Dual Authorisation governance review stage: 'Draft', 'PendingReview', 'Approved', 'Rejected'.
    /// </summary>
    [MaxLength(50)]
    public string ApprovalStatus { get; set; } = "Draft";

    /// <summary>
    /// User ID of the officer who initiated or proposed the window schedule.
    /// </summary>
    [MaxLength(100)]
    public string? ProposedByUserId { get; set; }

    /// <summary>
    /// Display name of the proposing officer.
    /// </summary>
    [MaxLength(150)]
    public string? ProposedByUserName { get; set; }

    /// <summary>
    /// Timestamp when the schedule proposal was submitted for review.
    /// </summary>
    public DateTime? ProposedDate { get; set; }

    /// <summary>
    /// User ID of the independent executive authority who reviewed and adjudicated the window.
    /// </summary>
    [MaxLength(100)]
    public string? AdjudicatedByUserId { get; set; }

    /// <summary>
    /// Display name of the adjudicating authority.
    /// </summary>
    [MaxLength(150)]
    public string? AdjudicatedByUserName { get; set; }

    /// <summary>
    /// Timestamp when the schedule was adjudicated.
    /// </summary>
    public DateTime? AdjudicatedDate { get; set; }

    /// <summary>
    /// Audit commentary and reason recorded by the adjudicating authority.
    /// </summary>
    public string? AdjudicationComments { get; set; }

    /// <summary>
    /// Indicates whether this submission window is currently active.
    /// </summary>
    public bool IsActive { get; set; } = true;

    // Navigation properties
    /// <summary>
    /// Governing DHET Gazetted OFO framework release.
    /// </summary>
    public OfoCodeSet? OfoCodeSet { get; set; }

    /// <summary>
    /// Occupational classifications scoped to this grant window cycle with sector priority designations.
    /// </summary>
    public ICollection<MgWindowOfoCode> ScopedOfoCodes { get; set; } = new List<MgWindowOfoCode>();
}
