using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Polymorphic document attachment linked to any entity record.
/// </summary>
public class DocumentAttachment : BaseEntity
{
    /// <summary>
    /// Name of the target entity type (e.g. Organisation, CompanyLearner, GrantMoa, WorkplaceApproval).
    /// </summary>
    public string TargetEntityName { get; set; } = string.Empty;

    /// <summary>
    /// Primary key identifier of the associated target entity.
    /// </summary>
    public int TargetEntityId { get; set; }
    
    /// <summary>
    /// Stored sanitized file name.
    /// </summary>
    public string FileName { get; set; } = string.Empty;

    /// <summary>
    /// Original file name as uploaded by the user.
    /// </summary>
    public string OriginalFileName { get; set; } = string.Empty;

    /// <summary>
    /// MIME content type (e.g. application/pdf, image/png).
    /// </summary>
    public string ContentType { get; set; } = "application/octet-stream";

    /// <summary>
    /// File size in bytes.
    /// </summary>
    public long FileSizeBytes { get; set; }
    
    /// <summary>
    /// Storage provider engine (e.g. Local, AzureBlob, Database).
    /// </summary>
    public string StorageProvider { get; set; } = "Local";

    /// <summary>
    /// Relative or absolute storage path URI.
    /// </summary>
    public string StoragePath { get; set; } = string.Empty;

    /// <summary>
    /// Cryptographic SHA-256 integrity hash for document tampering verification.
    /// </summary>
    public string? FileHashSha256 { get; set; }
    
    /// <summary>
    /// Document categorization code (e.g. ID_DOCUMENT, QUALIFICATION_CERT, SITE_PHOTO, BANK_CONFIRMATION, SIGNED_MOA).
    /// </summary>
    public string? DocumentCategoryCode { get; set; }

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public string? CategoryCode { get => DocumentCategoryCode; set => DocumentCategoryCode = value; }

    /// <summary>
    /// Indicates whether the document has been archived or soft-deleted.
    /// </summary>
    public bool IsArchived { get; set; } = false;

    /// <summary>
    /// Verification and compliance status: "Pending", "Compliant" (OK), "NonCompliant" (Not OK / Rejected).
    /// </summary>
    public string VerificationStatusCode { get; set; } = "Pending";

    /// <summary>
    /// Full name and role of the officer who performed the verification check.
    /// </summary>
    public string? VerifiedBy { get; set; }

    /// <summary>
    /// UTC timestamp when the document was verified or rejected.
    /// </summary>
    public DateTime? VerifiedAt { get; set; }

    /// <summary>
    /// Date when the document was certified or originally issued (e.g. 3-month statutory validity window for RSA IDs).
    /// </summary>
    public DateTime? DocumentCertificationDate { get; set; }

    /// <summary>
    /// Optional expiration date for the document (e.g. Tax Clearance PIN or accreditation expiry).
    /// </summary>
    public DateTime? DocumentExpiryDate { get; set; }

    /// <summary>
    /// Officer evaluation notes or compliance remarks.
    /// </summary>
    public string? VerificationNotes { get; set; }

    /// <summary>
    /// Consolidated human-readable summary of rejection reasons.
    /// </summary>
    public string? RejectionReason { get; set; }

    /// <summary>
    /// Serialized JSON array of selected DocumentRejectionReasonType codes (e.g. ["ID_EXPIRED_CERT", "ID_BLURRY"]).
    /// </summary>
    public string? RejectionReasonCodesJson { get; set; }

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public bool IsCompliant => VerificationStatusCode == "Compliant";

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public bool IsNonCompliant => VerificationStatusCode == "NonCompliant";

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public bool IsPendingReview => string.IsNullOrWhiteSpace(VerificationStatusCode) || VerificationStatusCode == "Pending";
}
