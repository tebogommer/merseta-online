using System.Security.Cryptography;
using System.Text.Json;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Domain.Lookups;

namespace Nsdms.Infrastructure.Services;

public class LocalFileStorageService : IFileStorageService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly ISystemConfigurationService _configService;
    private readonly IAuditService _audit;

    public LocalFileStorageService(INsdmsDbContextFactory contextFactory, ISystemConfigurationService configService, IAuditService audit)
    {
        _contextFactory = contextFactory;
        _configService = configService;
        _audit = audit;
    }

    public async Task<DocumentAttachment> SaveFileAsync(
        string targetEntityName,
        int targetEntityId,
        string fileName,
        string contentType,
        Stream fileStream,
        string? categoryCode = null,
        string currentUsername = "SYSTEM")
    {
        var baseDir = await _configService.GetValueAsync("Storage:LocalRootPath")
                      ?? await _configService.GetValueAsync("Storage.UploadDirectory", "uploads");
        var targetDir = Path.IsPathRooted(baseDir)
            ? Path.Combine(baseDir, targetEntityName.ToLowerInvariant())
            : Path.Combine(AppContext.BaseDirectory, baseDir, targetEntityName.ToLowerInvariant());
        Directory.CreateDirectory(targetDir);

        var safeFileName = Path.GetFileName(fileName);
        var uniqueFileName = $"{Guid.NewGuid():N}_{safeFileName}";
        var fullPath = Path.Combine(targetDir, uniqueFileName);
        var relativePath = Path.IsPathRooted(baseDir)
            ? fullPath
            : Path.Combine(baseDir, targetEntityName.ToLowerInvariant(), uniqueFileName);

        string hash;
        long totalBytes = 0;

        using (var sha256 = SHA256.Create())
        using (var outputStream = new FileStream(fullPath, FileMode.Create, FileAccess.Write, FileShare.None))
        {
            byte[] buffer = new byte[81920];
            int read;
            while ((read = await fileStream.ReadAsync(buffer, 0, buffer.Length)) > 0)
            {
                await outputStream.WriteAsync(buffer, 0, read);
                sha256.TransformBlock(buffer, 0, read, null, 0);
                totalBytes += read;
            }
            sha256.TransformFinalBlock(Array.Empty<byte>(), 0, 0);
            hash = Convert.ToHexString(sha256.Hash ?? Array.Empty<byte>());
        }

        var attachment = new DocumentAttachment
        {
            TargetEntityName = targetEntityName,
            TargetEntityId = targetEntityId,
            FileName = safeFileName,
            OriginalFileName = safeFileName,
            ContentType = string.IsNullOrWhiteSpace(contentType) ? "application/octet-stream" : contentType,
            FileSizeBytes = totalBytes,
            StorageProvider = "Local",
            StoragePath = relativePath,
            FileHashSha256 = hash,
            DocumentCategoryCode = categoryCode ?? "GENERAL",
            IsArchived = false,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        using var db = await _contextFactory.CreateDbContextAsync();
        db.DocumentAttachments.Add(attachment);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "DocumentAttachment", attachment.Id, "UploadAttachment", currentUsername, null, attachment);
        await db.SaveChangesAsync();

        return attachment;
    }

    public async Task<(Stream ContentStream, string ContentType, string FileName)?> GetFileAsync(int documentAttachmentId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var attachment = await db.DocumentAttachments.FindAsync(documentAttachmentId);
        if (attachment == null || attachment.IsArchived) return null;

        var fullPath = Path.IsPathRooted(attachment.StoragePath)
            ? attachment.StoragePath
            : Path.Combine(AppContext.BaseDirectory, attachment.StoragePath);
        if (!File.Exists(fullPath)) return null;

        var stream = new FileStream(fullPath, FileMode.Open, FileAccess.Read, FileShare.Read);
        return (stream, attachment.ContentType, attachment.FileName);
    }

    public async Task<List<DocumentAttachment>> GetAttachmentsAsync(string targetEntityName, int targetEntityId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.DocumentAttachments
            .Where(d => d.TargetEntityName == targetEntityName && d.TargetEntityId == targetEntityId && !d.IsArchived)
            .OrderByDescending(d => d.CreatedAt)
            .ToListAsync();
    }

    public async Task<bool> DeleteAttachmentAsync(int documentAttachmentId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var attachment = await db.DocumentAttachments.FindAsync(documentAttachmentId);
        if (attachment == null) return false;

        var before = new { attachment.Id, attachment.FileName, attachment.IsArchived };
        attachment.IsArchived = true;
        attachment.ModifiedAt = DateTime.UtcNow;
        attachment.ModifiedBy = currentUsername;

        _audit.LogAction(db, "DocumentAttachment", attachment.Id, "DeleteAttachment", currentUsername, before, attachment);
        await db.SaveChangesAsync();

        return true;
    }

    public async Task<DocumentAttachment?> VerifyAttachmentAsync(
        int documentAttachmentId,
        bool isCompliant,
        List<string>? rejectionReasonCodes = null,
        string? customNotes = null,
        DateTime? certificationDate = null,
        DateTime? expiryDate = null,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var attachment = await db.DocumentAttachments.FindAsync(documentAttachmentId);
        if (attachment == null || attachment.IsArchived) return null;

        var beforeSnapshot = new
        {
            attachment.Id,
            attachment.FileName,
            attachment.VerificationStatusCode,
            attachment.VerifiedBy,
            attachment.VerifiedAt,
            attachment.RejectionReason,
            attachment.DocumentCertificationDate
        };

        attachment.VerificationStatusCode = isCompliant ? "Compliant" : "NonCompliant";
        attachment.VerifiedBy = currentUsername;
        attachment.VerifiedAt = DateTime.UtcNow;
        attachment.DocumentCertificationDate = certificationDate;
        attachment.DocumentExpiryDate = expiryDate;
        attachment.VerificationNotes = customNotes;

        if (isCompliant)
        {
            attachment.RejectionReason = null;
            attachment.RejectionReasonCodesJson = null;
        }
        else
        {
            var codes = rejectionReasonCodes ?? new List<string>();
            attachment.RejectionReasonCodesJson = JsonSerializer.Serialize(codes);

            var reasonNames = await db.DocumentRejectionReasonTypes
                .Where(r => codes.Contains(r.Code))
                .OrderBy(r => r.DisplayOrder)
                .Select(r => r.Name)
                .ToListAsync();

            var summaryLines = new List<string>();
            if (reasonNames.Any())
            {
                summaryLines.AddRange(reasonNames);
            }
            if (!string.IsNullOrWhiteSpace(customNotes))
            {
                summaryLines.Add($"Officer Remarks: {customNotes.Trim()}");
            }
            attachment.RejectionReason = summaryLines.Any() ? string.Join(" • ", summaryLines) : "Document non-compliant.";
        }

        attachment.ModifiedAt = DateTime.UtcNow;
        attachment.ModifiedBy = currentUsername;

        _audit.LogAction(db, "DocumentAttachment", attachment.Id, isCompliant ? "VerifyAttachmentApproved" : "VerifyAttachmentRejected", currentUsername, beforeSnapshot, attachment);
        await db.SaveChangesAsync();

        return attachment;
    }

    public async Task<List<DocumentRejectionReasonType>> GetRejectionReasonsAsync(string? categoryCode = null, bool activeOnly = true)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.DocumentRejectionReasonTypes.AsNoTracking();

        if (activeOnly)
        {
            query = query.Where(r => r.Active);
        }

        if (!string.IsNullOrWhiteSpace(categoryCode))
        {
            var cat = categoryCode.Trim().ToUpperInvariant();
            query = query.Where(r => r.DocumentCategoryCode == cat || r.DocumentCategoryCode == "ALL");
        }

        return await query
            .OrderBy(r => r.DisplayOrder)
            .ThenBy(r => r.Name)
            .ToListAsync();
    }

    public async Task<List<DocumentRejectionReasonType>> GetAllRejectionReasonsAsync(string? search = null, string? categoryCode = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.DocumentRejectionReasonTypes.AsNoTracking();

        if (!string.IsNullOrWhiteSpace(categoryCode) && categoryCode != "ALL_CATEGORIES")
        {
            query = query.Where(r => r.DocumentCategoryCode == categoryCode);
        }

        if (!string.IsNullOrWhiteSpace(search))
        {
            var term = search.Trim().ToLower();
            query = query.Where(r => r.Code.ToLower().Contains(term) || r.Name.ToLower().Contains(term) || (r.Description != null && r.Description.ToLower().Contains(term)));
        }

        return await query
            .OrderBy(r => r.DocumentCategoryCode)
            .ThenBy(r => r.DisplayOrder)
            .ThenBy(r => r.Name)
            .ToListAsync();
    }

    public async Task<DocumentRejectionReasonType> SaveRejectionReasonAsync(DocumentRejectionReasonType reason, string currentUsername = "Admin")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.DocumentRejectionReasonTypes.FindAsync(reason.Code);
        if (existing == null)
        {
            reason.CreatedAt = DateTime.UtcNow;
            reason.CreatedBy = currentUsername;
            db.DocumentRejectionReasonTypes.Add(reason);
            _audit.LogAction(db, "DocumentRejectionReasonType", 0, "CreateRejectionReason", currentUsername, null, reason);
        }
        else
        {
            var before = new { existing.Name, existing.Description, existing.DocumentCategoryCode, existing.Active, existing.DisplayOrder };
            existing.Name = reason.Name;
            existing.Description = reason.Description;
            existing.DocumentCategoryCode = reason.DocumentCategoryCode;
            existing.Active = reason.Active;
            existing.DisplayOrder = reason.DisplayOrder;
            existing.ModifiedAt = DateTime.UtcNow;
            existing.ModifiedBy = currentUsername;
            _audit.LogAction(db, "DocumentRejectionReasonType", 0, "UpdateRejectionReason", currentUsername, before, existing);
        }

        await db.SaveChangesAsync();
        return existing ?? reason;
    }

    public async Task<bool> DeleteRejectionReasonAsync(string code, string currentUsername = "Admin")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.DocumentRejectionReasonTypes.FindAsync(code);
        if (existing == null) return false;

        var before = new { existing.Code, existing.Name, existing.Active };
        existing.Active = false;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        _audit.LogAction(db, "DocumentRejectionReasonType", 0, "DeactivateRejectionReason", currentUsername, before, existing);
        await db.SaveChangesAsync();
        return true;
    }
}
