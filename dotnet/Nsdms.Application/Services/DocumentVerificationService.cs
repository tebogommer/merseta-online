using System.Security.Cryptography;
using System.Text;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;
using QRCoder;

namespace Nsdms.Application.Services;

public class DocumentVerificationService : IDocumentVerificationService
{
    private readonly INsdmsDbContextFactory _factory;
    private readonly AuditService _audit;
    private readonly ISystemConfigurationService _config;

    public DocumentVerificationService(
        INsdmsDbContextFactory factory,
        AuditService audit,
        ISystemConfigurationService config)
    {
        _factory = factory;
        _audit = audit;
        _config = config;
    }

    public async Task<DocumentSnapshot> CreateAndFreezeDocumentSnapshotAsync(DocumentSnapshotRequest request, string issuedBy)
    {
        using var db = await _factory.CreateDbContextAsync();

        // 1. Generate unique snapshot reference if not supplied
        string snapshotNumber = string.IsNullOrWhiteSpace(request.DocumentSnapshotNumber)
            ? $"DOC-{DateTime.UtcNow:yyyyMMdd}-{Guid.NewGuid().ToString("N")[..8].ToUpperInvariant()}"
            : request.DocumentSnapshotNumber;

        // 2. Compute SHA-256 digital fingerprint hash
        byte[] hashBytes = SHA256.HashData(Encoding.UTF8.GetBytes(request.AssembledContent));
        string sha256Hex = Convert.ToHexStringLower(hashBytes);

        // 3. Construct canonical verification URL
        string baseUrl = request.BaseVerificationUrl
            ?? await _config.GetValueAsync("System.BaseUrl", "https://nsdms.merseta.org.za");
        string verificationUri = $"{baseUrl.TrimEnd('/')}/verify/document/{sha256Hex}";

        // 4. Generate embedded QR code bytes and Base64 string
        byte[] qrBytes = GenerateVerificationQrCodeBytes(verificationUri);
        string qrBase64 = $"data:image/png;base64,{Convert.ToBase64String(qrBytes)}";

        // 5. Create immutable snapshot record
        var snapshot = new DocumentSnapshot
        {
            DocumentSnapshotNumber = snapshotNumber,
            DocumentTypeCode = request.DocumentTypeCode,
            DocumentTemplateId = request.DocumentTemplateId,
            TemplateVersionNumber = request.TemplateVersionNumber,
            RelatedEntityId = request.RelatedEntityId,
            RelatedEntityType = request.RelatedEntityType,
            RecipientName = request.RecipientName,
            RecipientIdentifier = request.RecipientIdentifier,
            RenderedContentHash = sha256Hex,
            RenderedContent = request.AssembledContent,
            VerificationQrBase64 = qrBase64,
            VerificationUri = verificationUri,
            IssuedAt = DateTime.UtcNow,
            IssuedBy = issuedBy,
            SignatoryName = request.SignatoryName,
            SignatoryTitle = request.SignatoryTitle,
            SignatorySignedAt = !string.IsNullOrWhiteSpace(request.SignatoryName) ? DateTime.UtcNow : null,
            VerificationScanCount = 0
        };

        db.DocumentSnapshots.Add(snapshot);
        await db.SaveChangesAsync();

        // 6. Double-write audit log
        await _audit.LogAsync(
            "DocumentSnapshot",
            snapshot.Id,
            "IssueDocumentSnapshot",
            issuedBy,
            snapshot);

        return snapshot;
    }

    public async Task<DocumentVerificationResult> VerifyDocumentSnapshotAsync(string hashOrSnapshotNumber)
    {
        if (string.IsNullOrWhiteSpace(hashOrSnapshotNumber))
        {
            return new DocumentVerificationResult { IsFound = false };
        }

        using var db = await _factory.CreateDbContextAsync();
        string cleanQuery = hashOrSnapshotNumber.Trim().ToLowerInvariant();

        var snapshot = await db.DocumentSnapshots
            .Include(s => s.DocumentTemplate)
            .FirstOrDefaultAsync(s => s.RenderedContentHash.ToLower() == cleanQuery 
                                   || s.DocumentSnapshotNumber.ToLower() == cleanQuery);

        if (snapshot == null)
        {
            return new DocumentVerificationResult
            {
                IsFound = false,
                IsAuthentic = false
            };
        }

        // Recompute SHA-256 hash to verify physical tamper-proof integrity
        byte[] expectedHashBytes = SHA256.HashData(Encoding.UTF8.GetBytes(snapshot.RenderedContent));
        string computedHash = Convert.ToHexStringLower(expectedHashBytes);
        bool isHashValid = string.Equals(snapshot.RenderedContentHash, computedHash, StringComparison.OrdinalIgnoreCase);

        return new DocumentVerificationResult
        {
            IsFound = true,
            IsAuthentic = isHashValid && !snapshot.IsRevoked,
            IsRevoked = snapshot.IsRevoked,
            RevocationReason = snapshot.RevocationReason,
            DocumentSnapshotNumber = snapshot.DocumentSnapshotNumber,
            DocumentTypeCode = snapshot.DocumentTypeCode,
            DocumentTitle = snapshot.DocumentTemplate?.TemplateTitle ?? FormatDocumentTypeName(snapshot.DocumentTypeCode),
            RecipientName = snapshot.RecipientName,
            RecipientIdentifier = snapshot.RecipientIdentifier,
            RenderedContentHash = snapshot.RenderedContentHash,
            IssuedAt = snapshot.IssuedAt,
            IssuedBy = snapshot.IssuedBy,
            SignatoryName = snapshot.SignatoryName,
            SignatoryTitle = snapshot.SignatoryTitle,
            VerificationScanCount = snapshot.VerificationScanCount,
            AssembledContent = snapshot.RenderedContent,
            VerificationQrBase64 = snapshot.VerificationQrBase64,
            VerificationUri = snapshot.VerificationUri
        };
    }

    public async Task RecordVerificationScanAsync(int snapshotId, string? clientIp = null, string? userAgent = null)
    {
        using var db = await _factory.CreateDbContextAsync();
        var snapshot = await db.DocumentSnapshots.FindAsync(snapshotId);
        if (snapshot == null) return;

        snapshot.VerificationScanCount++;
        snapshot.LastVerifiedAt = DateTime.UtcNow;
        await db.SaveChangesAsync();

        await _audit.LogAsync(
            "DocumentSnapshot",
            snapshot.Id,
            "PublicVerificationScan",
            "PublicVerificationPortal",
            new { ScanCount = snapshot.VerificationScanCount, Ip = clientIp, UserAgent = userAgent });
    }

    public byte[] GenerateVerificationQrCodeBytes(string payloadUrl, int pixelsPerModule = 10)
    {
        using var qrGenerator = new QRCodeGenerator();
        using var qrCodeData = qrGenerator.CreateQrCode(payloadUrl, QRCodeGenerator.ECCLevel.Q);
        var qrCode = new PngByteQRCode(qrCodeData);
        return qrCode.GetGraphic(pixelsPerModule);
    }

    public async Task<List<DocumentSnapshot>> GetRecentSnapshotsAsync(string? documentType = null, int limit = 50)
    {
        using var db = await _factory.CreateDbContextAsync();
        var query = db.DocumentSnapshots
            .Include(s => s.DocumentTemplate)
            .AsQueryable();

        if (!string.IsNullOrWhiteSpace(documentType))
        {
            query = query.Where(s => s.DocumentTypeCode == documentType);
        }

        return await query
            .OrderByDescending(s => s.IssuedAt)
            .Take(limit)
            .ToListAsync();
    }

    public async Task<DocumentSnapshot?> GetSnapshotByIdAsync(int id)
    {
        using var db = await _factory.CreateDbContextAsync();
        return await db.DocumentSnapshots
            .Include(s => s.DocumentTemplate)
            .FirstOrDefaultAsync(s => s.Id == id);
    }

    public async Task<bool> RevokeDocumentSnapshotAsync(int snapshotId, string reason, string revokedBy)
    {
        using var db = await _factory.CreateDbContextAsync();
        var snapshot = await db.DocumentSnapshots.FindAsync(snapshotId);
        if (snapshot == null) return false;

        snapshot.IsRevoked = true;
        snapshot.RevocationReason = reason;
        await db.SaveChangesAsync();

        await _audit.LogAsync(
            "DocumentSnapshot",
            snapshot.Id,
            "RevokeDocumentSnapshot",
            $"Document #{snapshot.DocumentSnapshotNumber} revoked. Reason: {reason}",
            revokedBy,
            snapshot);

        return true;
    }

    private static string FormatDocumentTypeName(string typeCode) => typeCode switch
    {
        "TradeTestCertificate" => "Artisan National Trade Test Certificate",
        "WspApprovalLetter" => "Mandatory Grant (WSP) Annual Approval Letter",
        "WspRejectionLetter" => "Mandatory Grant (WSP) Non-Approval Notice",
        "AccreditationCertificate" => "Skills Development Provider (SDP) Accreditation Certificate",
        "RemittanceAdvice" => "Mandatory Grant Levy Rebate Remittance Advice",
        "SarsClawbackNotice" => "SARS Statutory Levy Audit Demand Notice",
        "StatementOfResults" => "National Artisan Summative Statement of Results (SOR)",
        "GrantMoaContract" => "Discretionary Grant Memorandum of Agreement (MoA)",
        _ => typeCode
    };
}
