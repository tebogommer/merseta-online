using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

/// <summary>
/// Service contract for managing accredited Skills Development Provider (SDP) physical delivery sites
/// (Statutory nomenclature: Delivery Site / Site per merSETA ETQA guidelines).
/// </summary>
public interface ISdpSiteService
{
    Task<List<TrainingProviderCampus>> GetSitesByProviderAsync(int providerId);
    Task<TrainingProviderCampus?> GetSiteByIdAsync(int siteId);
    Task<TrainingProviderCampus> AddSiteAsync(TrainingProviderCampus site, string currentUsername = "SYSTEM");
    Task<TrainingProviderCampus> UpdateSiteAsync(TrainingProviderCampus site, string currentUsername = "SYSTEM");
    Task<bool> DeleteSiteAsync(int siteId, string currentUsername = "SYSTEM");

    Task<List<TrainingProviderAssessorLink>> GetLinkedAssessorsAsync(int providerId);
    Task<TrainingProviderAssessorLink> LinkAssessorAsync(TrainingProviderAssessorLink link, string currentUsername = "SYSTEM");
    Task<bool> UnlinkAssessorAsync(int linkId, string currentUsername = "SYSTEM");
}

public interface ISdpCampusService : ISdpSiteService 
{
    Task<List<TrainingProviderCampus>> GetCampusesByProviderAsync(int providerId);
    Task<TrainingProviderCampus> AddCampusAsync(TrainingProviderCampus campus, string currentUsername = "SYSTEM");
    Task<bool> DeleteCampusAsync(int campusId, string currentUsername = "SYSTEM");
}

public class SdpSiteService : ISdpSiteService, ISdpCampusService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;

    public SdpSiteService(INsdmsDbContextFactory contextFactory, IAuditService audit)
    {
        _contextFactory = contextFactory;
        _audit = audit;
    }

    public async Task<List<TrainingProviderCampus>> GetSitesByProviderAsync(int providerId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.TrainingProviderCampuses
            .Where(c => c.TrainingProviderId == providerId)
            .OrderByDescending(c => c.IsPrimarySite)
            .ThenBy(c => c.CampusName)
            .ToListAsync();
    }

    public Task<List<TrainingProviderCampus>> GetCampusesByProviderAsync(int providerId) => GetSitesByProviderAsync(providerId);

    public async Task<TrainingProviderCampus?> GetSiteByIdAsync(int siteId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.TrainingProviderCampuses.FindAsync(siteId);
    }

    public async Task<TrainingProviderCampus> AddSiteAsync(TrainingProviderCampus site, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        if (string.IsNullOrWhiteSpace(site.CampusCode))
        {
            var count = await db.TrainingProviderCampuses.CountAsync(c => c.TrainingProviderId == site.TrainingProviderId);
            site.CampusCode = $"CAMPUS-{site.TrainingProviderId}-{(count + 1):D2}";
        }

        site.CreatedBy = currentUsername;
        site.CreatedAt = DateTime.UtcNow;

        if (site.IsPrimarySite)
        {
            var existingPrimary = await db.TrainingProviderCampuses
                .Where(c => c.TrainingProviderId == site.TrainingProviderId && c.IsPrimarySite)
                .ToListAsync();
            foreach (var ep in existingPrimary)
            {
                ep.IsPrimarySite = false;
            }
        }

        db.TrainingProviderCampuses.Add(site);
        await db.SaveChangesAsync();

        await _audit.LogAsync("TrainingProviderCampus", site.Id, "AddDeliverySite", currentUsername, new
        {
            site.CampusName,
            site.CampusCode,
            site.City,
            site.IsPrimarySite
        });

        return site;
    }

    public Task<TrainingProviderCampus> AddCampusAsync(TrainingProviderCampus campus, string currentUsername = "SYSTEM") => AddSiteAsync(campus, currentUsername);

    public async Task<TrainingProviderCampus> UpdateSiteAsync(TrainingProviderCampus site, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.TrainingProviderCampuses.FindAsync(site.Id);
        if (existing == null)
            throw new KeyNotFoundException($"Delivery site {site.Id} not found.");

        existing.CampusName = site.CampusName;
        existing.PhysicalAddressLine1 = site.PhysicalAddressLine1;
        existing.PhysicalAddressLine2 = site.PhysicalAddressLine2;
        existing.City = site.City;
        existing.ProvinceCode = site.ProvinceCode;
        existing.PostalCode = site.PostalCode;
        existing.ContactPersonName = site.ContactPersonName;
        existing.ContactEmail = site.ContactEmail;
        existing.ContactPhone = site.ContactPhone;
        existing.Status = site.Status;
        existing.GpsCoordinates = site.GpsCoordinates;
        existing.Latitude = site.Latitude;
        existing.Longitude = site.Longitude;
        existing.LocalMunicipality = site.LocalMunicipality;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        if (site.IsPrimarySite && !existing.IsPrimarySite)
        {
            var otherPrimaries = await db.TrainingProviderCampuses
                .Where(c => c.TrainingProviderId == existing.TrainingProviderId && c.Id != existing.Id && c.IsPrimarySite)
                .ToListAsync();
            foreach (var op in otherPrimaries)
            {
                op.IsPrimarySite = false;
            }
            existing.IsPrimarySite = true;
        }

        await db.SaveChangesAsync();

        await _audit.LogAsync("TrainingProviderCampus", existing.Id, "UpdateDeliverySite", currentUsername, new
        {
            existing.CampusName,
            existing.Status,
            existing.IsPrimarySite
        });

        return existing;
    }

    public Task<TrainingProviderCampus> UpdateCampusAsync(TrainingProviderCampus campus, string currentUsername = "SYSTEM") => UpdateSiteAsync(campus, currentUsername);

    public async Task<bool> DeleteSiteAsync(int siteId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var site = await db.TrainingProviderCampuses.FindAsync(siteId);
        if (site == null) return false;

        db.TrainingProviderCampuses.Remove(site);
        await db.SaveChangesAsync();

        await _audit.LogAsync("TrainingProviderCampus", siteId, "DeleteDeliverySite", currentUsername, new
        {
            site.CampusName,
            site.CampusCode
        });

        return true;
    }

    public Task<bool> DeleteCampusAsync(int campusId, string currentUsername = "SYSTEM") => DeleteSiteAsync(campusId, currentUsername);

    public async Task<List<TrainingProviderAssessorLink>> GetLinkedAssessorsAsync(int providerId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.TrainingProviderAssessorLinks
            .Include(l => l.EtqaAssessor)
                .ThenInclude(a => a != null ? a.Person : null)
            .Include(l => l.TrainingProviderCampus)
            .Where(l => l.TrainingProviderId == providerId)
            .OrderBy(l => l.Status)
            .ThenByDescending(l => l.StartDate)
            .ToListAsync();
    }

    public async Task<TrainingProviderAssessorLink> LinkAssessorAsync(TrainingProviderAssessorLink link, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var exists = await db.TrainingProviderAssessorLinks
            .AnyAsync(l => l.TrainingProviderId == link.TrainingProviderId &&
                           l.EtqaAssessorId == link.EtqaAssessorId &&
                           l.RoleTypeCode == link.RoleTypeCode &&
                           l.Status == "Active");

        if (exists)
            throw new InvalidOperationException("This practitioner is already actively linked in this capacity to this provider.");

        link.CreatedBy = currentUsername;
        link.CreatedAt = DateTime.UtcNow;
        link.Status = "Active";

        db.TrainingProviderAssessorLinks.Add(link);
        await db.SaveChangesAsync();

        await _audit.LogAsync("TrainingProviderAssessorLink", link.Id, "LinkAssessor", currentUsername, new
        {
            link.TrainingProviderId,
            link.EtqaAssessorId,
            link.RoleTypeCode
        });

        return link;
    }

    public async Task<bool> UnlinkAssessorAsync(int linkId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var link = await db.TrainingProviderAssessorLinks.FindAsync(linkId);
        if (link == null) return false;

        link.Status = "Terminated";
        link.EndDate = DateTime.UtcNow;
        link.ModifiedAt = DateTime.UtcNow;
        link.ModifiedBy = currentUsername;

        await db.SaveChangesAsync();

        await _audit.LogAsync("TrainingProviderAssessorLink", linkId, "UnlinkAssessor", currentUsername, new
        {
            link.TrainingProviderId,
            link.EtqaAssessorId,
            link.RoleTypeCode
        });

        return true;
    }
}
