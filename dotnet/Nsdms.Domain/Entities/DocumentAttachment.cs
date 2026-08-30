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
}
