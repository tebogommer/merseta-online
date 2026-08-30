using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;
using System.Security.Cryptography;

namespace Nsdms.Application.Services;

public class StorageService : IStorageService
{
    private readonly INsdmsDbContextFactory _contextFactory;

    public StorageService(INsdmsDbContextFactory contextFactory)
    {
        _contextFactory = contextFactory;
    }

    public async Task<DocumentMetadata> UploadDocumentAsync(
        string entityName, 
        int entityId, 
        string docTypeCode, 
        string docTypeName, 
        string fileName, 
        Stream contentStream, 
        string contentType, 
        string userId, 
        string userName)
    {
        var context = await _contextFactory.CreateDbContextAsync();

        // Compute SHA-256 Checksum
        using var sha256 = SHA256.Create();
        var hashBytes = await sha256.ComputeHashAsync(contentStream);
        var hashString = BitConverter.ToString(hashBytes).Replace("-", "").ToLowerInvariant();

        var storageUri = $"vault://{entityName.ToLower()}/{entityId}/{Guid.NewGuid()}_{fileName}";

        var doc = new DocumentMetadata
        {
            TargetEntityName = entityName,
            TargetEntityId = entityId,
            DocumentTypeCode = docTypeCode,
            DocumentTypeName = docTypeName,
            FileName = fileName,
            StorageUri = storageUri,
            FileSizeBytes = contentStream.Length,
            ContentType = contentType,
            Sha256Hash = hashString,
            UploadedByUserId = userId,
            UploadedByUserName = userName,
            UploadDate = DateTime.UtcNow,
            IsVerified = false
        };

        context.DocumentMetadatas.Add(doc);

        context.AuditLogs.Add(new AuditLog
        {
            EntityName = "DocumentMetadata",
            RecordId = doc.Id,
            ActionName = "UPLOAD_DOCUMENT",
            Actor = userId,
            Timestamp = DateTime.UtcNow,
            MetadataJson = $"{{\"fileName\":\"{fileName}\",\"docType\":\"{docTypeCode}\",\"targetEntity\":\"{entityName}\",\"targetId\":{entityId}}}"
        });

        await context.SaveChangesAsync();
        return doc;
    }

    public async Task<List<DocumentMetadata>> GetDocumentsForEntityAsync(string entityName, int entityId)
    {
        var context = await _contextFactory.CreateDbContextAsync();
        return await context.DocumentMetadatas
            .Where(d => d.TargetEntityName == entityName && d.TargetEntityId == entityId)
            .OrderByDescending(d => d.UploadDate)
            .ToListAsync();
    }

    public async Task<DocumentMetadata?> GetDocumentByIdAsync(int documentId)
    {
        var context = await _contextFactory.CreateDbContextAsync();
        return await context.DocumentMetadatas.FirstOrDefaultAsync(d => d.Id == documentId);
    }

    public async Task<bool> VerifyDocumentAsync(int documentId, string verifierUserId, string? notes = null)
    {
        var context = await _contextFactory.CreateDbContextAsync();
        var doc = await context.DocumentMetadatas.FirstOrDefaultAsync(d => d.Id == documentId);
        if (doc == null) return false;

        doc.IsVerified = true;
        doc.VerificationNotes = notes;
        doc.ModifiedAt = DateTime.UtcNow;
        doc.ModifiedBy = verifierUserId;

        context.AuditLogs.Add(new AuditLog
        {
            EntityName = "DocumentMetadata",
            RecordId = doc.Id,
            ActionName = "VERIFY_DOCUMENT",
            Actor = verifierUserId,
            Timestamp = DateTime.UtcNow,
            MetadataJson = $"{{\"isVerified\":true,\"notes\":\"{notes}\"}}"
        });

        await context.SaveChangesAsync();
        return true;
    }

    public async Task<bool> DeleteDocumentAsync(int documentId)
    {
        var context = await _contextFactory.CreateDbContextAsync();
        var doc = await context.DocumentMetadatas.FirstOrDefaultAsync(d => d.Id == documentId);
        if (doc == null) return false;

        context.DocumentMetadatas.Remove(doc);
        await context.SaveChangesAsync();
        return true;
    }
}
