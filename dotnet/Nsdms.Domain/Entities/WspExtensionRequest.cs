using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Statutory extension application for Workplace Skills Plan (WSP) & Mandatory Grant submission per SETA Grant Regulations.
/// </summary>
public class WspExtensionRequest : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the submitting Employer Organisation.
    /// </summary>
    public int OrganisationId { get; set; }

    /// <summary>
    /// Navigational reference to the submitting Organisation.
    /// </summary>
    public Organisation? Organisation { get; set; }

    /// <summary>
    /// Optional foreign key referencing an existing WSP submission record (null if requested prior to WSP initiation).
    /// </summary>
    public int? WspSubmissionId { get; set; }

    /// <summary>
    /// Navigational reference to the linked WSP submission.
    /// </summary>
    public WspSubmission? WspSubmission { get; set; }

    /// <summary>
    /// Statutory financial/scheme year for this extension request (e.g. 2026).
    /// </summary>
    public int SchemeYear { get; set; }

    /// <summary>
    /// Masked statutory business tracking reference number (e.g. EXT-2026-0415-001).
    /// </summary>
    public string ApplicationReference { get; set; } = string.Empty;

    /// <summary>
    /// Categorical grounds for extension (e.g. BusinessRescue, TechnicalOutage, NaturalDisaster, IndustrialAction, OwnershipRestructure, OtherExceptional).
    /// </summary>
    public string ReasonCode { get; set; } = "BusinessRescue";

    /// <summary>
    /// Plain-language description of the grounds for extension.
    /// </summary>
    public string? GroundsDescription { get; set; }

    /// <summary>
    /// Statutory motivation and justification detailed by the applicant SDF.
    /// </summary>
    public string StatutoryMotivation { get; set; } = string.Empty;

    /// <summary>
    /// Proposed extended submission date requested by the applicant (statutory max: 31 May).
    /// </summary>
    public DateTime RequestedExtensionDate { get; set; }

    /// <summary>
    /// Official extended submission date granted upon executive approval.
    /// </summary>
    public DateTime? GrantedExtensionDate { get; set; }

    /// <summary>
    /// Current approval lifecycle status code (PendingReview, Recommended, Approved, Rejected).
    /// </summary>
    public string ApprovalStatusCode { get; set; } = "PendingReview";

    /// <summary>
    /// Storage identifier or reference for attached supporting evidentiary documentation.
    /// </summary>
    public string? EvidenceDocumentId { get; set; }

    /// <summary>
    /// File name of the attached evidentiary document/affidavit.
    /// </summary>
    public string? EvidenceFileName { get; set; }

    /// <summary>
    /// Identity of the applicant user who submitted the request.
    /// </summary>
    public string SubmittedByUserId { get; set; } = "SYSTEM";

    /// <summary>
    /// UTC timestamp when the request was officially submitted.
    /// </summary>
    public DateTime SubmittedAt { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// User ID of the Client Liaison Officer (CLO) or Project Manager who reviewed and recommended/queried the request.
    /// </summary>
    public string? ReviewedByUserId { get; set; }

    /// <summary>
    /// UTC timestamp of the CLO/CRM review recommendation.
    /// </summary>
    public DateTime? ReviewedAt { get; set; }

    /// <summary>
    /// Evaluative notes and compliance observations from the reviewing officer.
    /// </summary>
    public string? ReviewerComments { get; set; }

    /// <summary>
    /// User ID of the merSETA Executive Officer (COO / CEO) who formally adjudicated the extension request.
    /// </summary>
    public string? ApprovedByUserId { get; set; }

    /// <summary>
    /// UTC timestamp of formal executive adjudication.
    /// </summary>
    public DateTime? ApprovedAt { get; set; }

    /// <summary>
    /// Adjudication rationale or formal rejection reason recorded by the executive authority.
    /// </summary>
    public string? ApprovalComments { get; set; }

    /// <summary>
    /// Confirms applicant acceptance of the statutory legal declaration under the Skills Development Act.
    /// </summary>
    public bool DeclarationAccepted { get; set; } = true;
}
