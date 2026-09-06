using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Disciplinary, suspension, and de-accreditation cases against an accredited Skills Development Provider (SDP)
/// (Ref: merSETA ETQA Disciplinary Regulations, Signed SDP Application Use Case Section 4.5 and Form ETQ-TP-015).
/// Enforces immediate system freeze on new learner enrolments upon provider suspension.
/// </summary>
public class SdpDisciplinaryCase : BaseEntity
{
    public int TrainingProviderId { get; set; }
    public TrainingProvider? TrainingProvider { get; set; }

    /// <summary>
    /// Statutory case reference number (e.g. SDP-DISC-2026-0001).
    /// </summary>
    public string CaseNumber { get; set; } = string.Empty;

    /// <summary>
    /// Statutory case typology: Suspension, DeAccreditation, VoluntarySurrender, NonComplianceNotice, ScopeRestriction.
    /// </summary>
    public string CaseType { get; set; } = "Suspension";

    /// <summary>
    /// Workflow status: UnderInvestigation, CommitteeReview, Suspended, Deregistered, Dismissed, Reinstated.
    /// </summary>
    public string Status { get; set; } = "UnderInvestigation";

    /// <summary>
    /// Source of referral/complaint: LearnerComplaint, EmployerComplaint, AuditFinding, Whistleblower, DhetDirective.
    /// </summary>
    public string? ComplaintSource { get; set; } = "AuditFinding";

    public string AllegationSummary { get; set; } = string.Empty;

    public string? InvestigationFindings { get; set; }

    /// <summary>
    /// Sanctions imposed: FormalWarning, TemporarySuspension, ScopeRestriction, FullDeregistration, None.
    /// </summary>
    public string? SanctionType { get; set; }

    public DateTime? SanctionStartDate { get; set; }
    public DateTime? SanctionEndDate { get; set; }

    /// <summary>
    /// ETQA Review Committee ratification minute/decision number.
    /// </summary>
    public string? ReviewCommitteeDecisionNumber { get; set; }

    public DateTime? ReviewCommitteeDate { get; set; }

    /// <summary>
    /// File reference for formal statutory Notice of Suspension / De-Accreditation (ETQ-TP-015).
    /// </summary>
    public string? NoticeDocumentRef { get; set; }

    public bool IsActive { get; set; } = true;
}
