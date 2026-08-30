using System.Security.Cryptography;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;

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
        var baseDir = await _configService.GetValueAsync("Storage.UploadDirectory", "uploads");
        var targetDir = Path.Combine(AppContext.BaseDirectory, baseDir, targetEntityName.ToLowerInvariant());
        Directory.CreateDirectory(targetDir);

        var safeFileName = Path.GetFileName(fileName);
        var uniqueFileName = $"{Guid.NewGuid():N}_{safeFileName}";
        var fullPath = Path.Combine(targetDir, uniqueFileName);
        var relativePath = Path.Combine(baseDir, targetEntityName.ToLowerInvariant(), uniqueFileName);

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

        var fullPath = Path.Combine(AppContext.BaseDirectory, attachment.StoragePath);
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
}
