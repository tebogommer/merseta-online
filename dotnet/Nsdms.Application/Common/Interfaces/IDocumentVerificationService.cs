using Nsdms.Domain.Entities;

namespace Nsdms.Application.Common.Interfaces;

public class DocumentSnapshotRequest
{
    public string DocumentTypeCode { get; set; } = string.Empty;
    public string RelatedEntityType { get; set; } = string.Empty;
    public int RelatedEntityId { get; set; }
    public string RecipientName { get; set; } = string.Empty;
    public string RecipientIdentifier { get; set; } = string.Empty;
    public string DocumentSnapshotNumber { get; set; } = string.Empty;
    public string AssembledContent { get; set; } = string.Empty;
    public int? DocumentTemplateId { get; set; }
    public string TemplateVersionNumber { get; set; } = "1.0.0";
    public string? SignatoryName { get; set; }
    public string? SignatoryTitle { get; set; }
    public string? BaseVerificationUrl { get; set; }
}

public class DocumentVerificationResult
{
    public bool IsFound { get; set; }
    public bool IsAuthentic { get; set; }
    public bool IsRevoked { get; set; }
    public string? RevocationReason { get; set; }
    public string DocumentSnapshotNumber { get; set; } = string.Empty;
    public string DocumentTypeCode { get; set; } = string.Empty;
    public string DocumentTitle { get; set; } = string.Empty;
    public string RecipientName { get; set; } = string.Empty;
    public string RecipientIdentifier { get; set; } = string.Empty;
    public string RenderedContentHash { get; set; } = string.Empty;
    public DateTime IssuedAt { get; set; }
    public string IssuedBy { get; set; } = string.Empty;
    public string? SignatoryName { get; set; }
    public string? SignatoryTitle { get; set; }
    public int VerificationScanCount { get; set; }
    public string AssembledContent { get; set; } = string.Empty;
    public string? VerificationQrBase64 { get; set; }
    public string VerificationUri { get; set; } = string.Empty;
}

public interface IDocumentVerificationService
{
    /// <summary>
    /// Freezes an issued statutory document, generates cryptographic SHA-256 fingerprint and QR code, stores the snapshot, and writes to audit logs.
    /// </summary>
    Task<DocumentSnapshot> CreateAndFreezeDocumentSnapshotAsync(DocumentSnapshotRequest request, string issuedBy);

    /// <summary>
    /// Verifies the cryptographic authenticity and provenance of a document by its SHA-256 hash or snapshot number.
    /// </summary>
    Task<DocumentVerificationResult> VerifyDocumentSnapshotAsync(string hashOrSnapshotNumber);

    /// <summary>
    /// Records a public/auditor scan verification lookup against a document snapshot.
    /// </summary>
    Task RecordVerificationScanAsync(int snapshotId, string? clientIp = null, string? userAgent = null);

    /// <summary>
    /// Generates a standard PNG byte array of a QR code embedding the verification URL.
    /// </summary>
    byte[] GenerateVerificationQrCodeBytes(string payloadUrl, int pixelsPerModule = 10);

    /// <summary>
    /// Retrieves recent issued document snapshots for compliance oversight.
    /// </summary>
    Task<List<DocumentSnapshot>> GetRecentSnapshotsAsync(string? documentType = null, int limit = 50);

    /// <summary>
    /// Retrieves a snapshot by ID.
    /// </summary>
    Task<DocumentSnapshot?> GetSnapshotByIdAsync(int id);

    /// <summary>
    /// Revokes an issued document (e.g. fraudulent certificate or superseded notice).
    /// </summary>
    Task<bool> RevokeDocumentSnapshotAsync(int snapshotId, string reason, string revokedBy);
}
