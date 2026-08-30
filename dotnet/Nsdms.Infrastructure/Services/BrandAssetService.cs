using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;

namespace Nsdms.Infrastructure.Services;

public class BrandAssetService : IBrandAssetService
{
    private readonly INsdmsDbContextFactory _dbFactory;

    public BrandAssetService(INsdmsDbContextFactory dbFactory)
    {
        _dbFactory = dbFactory;
    }

    public async Task<string?> GetLogoDataUriAsync(int entityId, string entityType)
    {
        await using var db = await _dbFactory.CreateDbContextAsync();

        if (entityType.Equals("Organisation", StringComparison.OrdinalIgnoreCase))
        {
            var org = await db.Organisations.FirstOrDefaultAsync(o => o.Id == entityId);
            if (org?.LogoDocumentId != null)
            {
                var doc = await db.DocumentMetadatas.FirstOrDefaultAsync(d => d.Id == org.LogoDocumentId);
                return doc?.StorageUri;
            }
        }
        else if (entityType.Equals("TrainingProvider", StringComparison.OrdinalIgnoreCase))
        {
            var provider = await db.TrainingProviders.Include(p => p.Organisation).FirstOrDefaultAsync(p => p.Id == entityId);
            if (provider?.Organisation?.LogoDocumentId != null)
            {
                var doc = await db.DocumentMetadatas.FirstOrDefaultAsync(d => d.Id == provider.Organisation.LogoDocumentId);
                return doc?.StorageUri;
            }
        }

        return null;
    }

    public async Task<bool> SetOrganisationBrandAsync(int organisationId, string? logoBase64, string? brandColorHex, string actor)
    {
        await using var db = await _dbFactory.CreateDbContextAsync();
        var org = await db.Organisations.FirstOrDefaultAsync(o => o.Id == organisationId);
        if (org == null) return false;

        if (!string.IsNullOrEmpty(logoBase64))
        {
            var doc = new DocumentMetadata
            {
                DocumentTypeName = $"{org.CompanyName} Corporate Logo",
                DocumentTypeCode = "LOGO",
                FileName = $"{org.SdlNumber}_logo.png",
                ContentType = "image/png",
                FileSizeBytes = logoBase64.Length,
                StorageUri = logoBase64.StartsWith("data:") ? logoBase64 : $"data:image/png;base64,{logoBase64}",
                TargetEntityName = "Organisation",
                TargetEntityId = organisationId,
                CreatedBy = actor,
                CreatedAt = DateTime.UtcNow
            };
            db.DocumentMetadatas.Add(doc);
            await db.SaveChangesAsync();

            org.LogoDocumentId = doc.Id;
        }

        if (!string.IsNullOrEmpty(brandColorHex))
        {
            org.BrandColorHex = brandColorHex;
        }

        org.ModifiedAt = DateTime.UtcNow;
        org.ModifiedBy = actor;

        // Double-write to audit log
        db.AuditLogs.Add(new AuditLog
        {
            EntityName = "Organisation",
            RecordId = organisationId,
            ActionName = "UPDATE_BRAND_ASSETS",
            Actor = actor,
            Timestamp = DateTime.UtcNow,
            MetadataJson = $"{{\"logoUpdated\":{(!string.IsNullOrEmpty(logoBase64)).ToString().ToLower()},\"brandColor\":\"{brandColorHex}\"}}"
        });

        await db.SaveChangesAsync();
        return true;
    }

    public async Task<bool> SetProviderBrandAsync(int providerId, string? logoBase64, string actor)
    {
        await using var db = await _dbFactory.CreateDbContextAsync();
        var provider = await db.TrainingProviders.Include(p => p.Organisation).FirstOrDefaultAsync(p => p.Id == providerId);
        if (provider == null) return false;

        int? logoDocId = null;
        if (!string.IsNullOrEmpty(logoBase64))
        {
            var doc = new DocumentMetadata
            {
                DocumentTypeName = $"{provider.Organisation?.CompanyName ?? "SDP"} Brand Logo",
                DocumentTypeCode = "LOGO",
                FileName = $"{provider.AccreditationNumber}_logo.png",
                ContentType = "image/png",
                FileSizeBytes = logoBase64.Length,
                StorageUri = logoBase64.StartsWith("data:") ? logoBase64 : $"data:image/png;base64,{logoBase64}",
                TargetEntityName = "TrainingProvider",
                TargetEntityId = providerId,
                CreatedBy = actor,
                CreatedAt = DateTime.UtcNow
            };
            db.DocumentMetadatas.Add(doc);
            await db.SaveChangesAsync();

            if (provider.Organisation != null)
            {
                provider.Organisation.LogoDocumentId = doc.Id;
            }
            logoDocId = doc.Id;
        }

        provider.ModifiedAt = DateTime.UtcNow;
        provider.ModifiedBy = actor;

        // Double-write to audit log
        db.AuditLogs.Add(new AuditLog
        {
            EntityName = "TrainingProvider",
            RecordId = providerId,
            ActionName = "UPDATE_BRAND_LOGO",
            Actor = actor,
            Timestamp = DateTime.UtcNow,
            MetadataJson = $"{{\"providerId\":{providerId},\"logoDocumentId\":{logoDocId}}}"
        });

        await db.SaveChangesAsync();
        return true;
    }
}
