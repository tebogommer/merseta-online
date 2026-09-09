using System.ComponentModel.DataAnnotations.Schema;
using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Mandatory Grant (MG / WSP / ATR) Window Schedule Proposal governed by Maker-Checker Segregation of Duties.
/// A Maker prepares and submits a schedule change proposal, and an independent Checker reviews and adjudicates.
/// </summary>
[Table("MgWindowScheduleProposal")]
public class MgWindowScheduleProposal : BaseEntity
{
    /// <summary>
    /// Financial scheme year (e.g. 2026).
    /// </summary>
    public int SchemeYear { get; set; }

    /// <summary>
    /// Proposed opening date and time when the submission portal opens for employers.
    /// </summary>
    public DateTime ProposedOpeningDate { get; set; }

    /// <summary>
    /// Proposed statutory closing deadline date and time under Regulation 4(1).
    /// </summary>
    public DateTime ProposedClosingDate { get; set; }

    /// <summary>
    /// Proposed deadline for filing statutory extension requests under Regulation 4(2).
    /// </summary>
    public DateTime ProposedExtensionCutoffDate { get; set; }

    /// <summary>
    /// Mandatory administrative justification or rationale for this schedule proposal.
    /// </summary>
    public string Justification { get; set; } = string.Empty;

    /// <summary>
    /// Statutory authority, resolution reference, Government Gazette, or CEO circular reference.
    /// </summary>
    public string? GazetteOrResolutionRef { get; set; }

    /// <summary>
    /// Workflow status: "PendingReview", "Approved", "Rejected", "Withdrawn".
    /// </summary>
    public string Status { get; set; } = "PendingReview";

    /// <summary>
    /// User ID of the Maker who prepared and submitted this proposal.
    /// </summary>
    public string ProposedByUserId { get; set; } = string.Empty;

    /// <summary>
    /// Display name of the Maker.
    /// </summary>
    public string ProposedByUserName { get; set; } = string.Empty;

    /// <summary>
    /// Timestamp when this proposal was submitted for Maker-Checker review.
    /// </summary>
    public DateTime ProposedAt { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// User ID of the Checker / Approver who adjudicated this proposal.
    /// Must be distinct from ProposedByUserId per Segregation of Duties.
    /// </summary>
    public string? AdjudicatedByUserId { get; set; }

    /// <summary>
    /// Display name of the Checker / Approver.
    /// </summary>
    public string? AdjudicatedByUserName { get; set; }

    /// <summary>
    /// Timestamp when the Checker adjudicated the proposal.
    /// </summary>
    public DateTime? AdjudicatedAt { get; set; }

    /// <summary>
    /// Checker decision comments or rejection reasons.
    /// </summary>
    public string? AdjudicationComments { get; set; }

    /// <summary>
    /// Whether this proposal was successfully activated and written to SystemConfig.
    /// </summary>
    public bool AppliedToSystemConfig { get; set; }
}
