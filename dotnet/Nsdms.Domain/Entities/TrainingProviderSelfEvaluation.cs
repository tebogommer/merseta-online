using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Two-stage QMS Self-Evaluation audit checklist item completed by the SDP and verified on-site by the Regional Quality Assuror
/// (Signed SDP Application Use Case 21022023 Table 7 Step J, L, M & Table 24 Step J, K, L).
/// </summary>
public class TrainingProviderSelfEvaluation : BaseEntity
{
    public int TrainingProviderId { get; set; }
    public TrainingProvider? TrainingProvider { get; set; }

    /// <summary>
    /// Statutory Criteria Code (e.g. QMS-01, QMS-02, OHS-01, STAFF-01).
    /// </summary>
    public string CriteriaCode { get; set; } = string.Empty;

    /// <summary>
    /// Governance category (e.g. Policy & Governance, Curriculum & Delivery, Assessment & Moderation, Facilities & Safety, Financial Viability).
    /// </summary>
    public string CriteriaCategory { get; set; } = "Quality Management System";

    /// <summary>
    /// Evaluation checklist requirement description.
    /// </summary>
    public string CriteriaDescription { get; set; } = string.Empty;

    /// <summary>
    /// Applicant self-evaluation rating: Yes (Compliant) or No (Non-Compliant).
    /// </summary>
    public bool IsCompliant { get; set; } = true;

    /// <summary>
    /// Documentary evidence reference ID or filing reference.
    /// </summary>
    public string? DocumentReferenceNumber { get; set; }

    /// <summary>
    /// SDP Applicant compliance comments, explanation, or procedure manual cross-references.
    /// </summary>
    public string? ApplicantComments { get; set; }

    /// <summary>
    /// Quality Assuror on-site audit verification verdict: true = Verified Compliant, false = Gaps Identified, null = Pending Inspection.
    /// </summary>
    public bool? AssessorVerified { get; set; }

    /// <summary>
    /// Quality Assuror forensic site findings, observations, or gap remediation notes.
    /// </summary>
    public string? AssessorFindings { get; set; }
}
