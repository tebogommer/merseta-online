using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public interface ISdpCampusService
{
    Task<List<TrainingProviderCampus>> GetCampusesByProviderAsync(int providerId);
    Task<TrainingProviderCampus> AddCampusAsync(TrainingProviderCampus campus, string currentUsername = "SYSTEM");
    Task<TrainingProviderCampus> UpdateCampusAsync(TrainingProviderCampus campus, string currentUsername = "SYSTEM");
    Task<bool> DeleteCampusAsync(int campusId, string currentUsername = "SYSTEM");

    Task<List<TrainingProviderAssessorLink>> GetLinkedAssessorsAsync(int providerId);
    Task<TrainingProviderAssessorLink> LinkAssessorAsync(TrainingProviderAssessorLink link, string currentUsername = "SYSTEM");
    Task<bool> UnlinkAssessorAsync(int linkId, string currentUsername = "SYSTEM");
}

public class SdpCampusService : ISdpCampusService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;

    public SdpCampusService(INsdmsDbContextFactory contextFactory, IAuditService audit)
    {
        _contextFactory = contextFactory;
        _audit = audit;
    }

    public async Task<List<TrainingProviderCampus>> GetCampusesByProviderAsync(int providerId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.TrainingProviderCampuses
            .Where(c => c.TrainingProviderId == providerId)
            .OrderByDescending(c => c.IsPrimarySite)
            .ThenBy(c => c.CampusName)
            .ToListAsync();
    }

    public async Task<TrainingProviderCampus> AddCampusAsync(TrainingProviderCampus campus, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        if (string.IsNullOrWhiteSpace(campus.CampusCode))
        {
            var count = await db.TrainingProviderCampuses.CountAsync(c => c.TrainingProviderId == campus.TrainingProviderId);
            campus.CampusCode = $"CAMPUS-{campus.TrainingProviderId}-{(count + 1):D2}";
        }

        campus.CreatedBy = currentUsername;
        campus.CreatedAt = DateTime.UtcNow;

        if (campus.IsPrimarySite)
        {
            var existingPrimary = await db.TrainingProviderCampuses
                .Where(c => c.TrainingProviderId == campus.TrainingProviderId && c.IsPrimarySite)
                .ToListAsync();
            foreach (var ep in existingPrimary)
            {
                ep.IsPrimarySite = false;
            }
        }

        db.TrainingProviderCampuses.Add(campus);
        await db.SaveChangesAsync();

        await _audit.LogAsync("TrainingProviderCampus", campus.Id, "AddCampus", currentUsername, new
        {
            campus.CampusName,
            campus.CampusCode,
            campus.City,
            campus.IsPrimarySite
        });

        return campus;
    }

    public async Task<TrainingProviderCampus> UpdateCampusAsync(TrainingProviderCampus campus, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.TrainingProviderCampuses.FindAsync(campus.Id);
        if (existing == null)
            throw new KeyNotFoundException($"Campus {campus.Id} not found.");

        existing.CampusName = campus.CampusName;
        existing.PhysicalAddressLine1 = campus.PhysicalAddressLine1;
        existing.PhysicalAddressLine2 = campus.PhysicalAddressLine2;
        existing.City = campus.City;
        existing.ProvinceCode = campus.ProvinceCode;
        existing.PostalCode = campus.PostalCode;
        existing.ContactPersonName = campus.ContactPersonName;
        existing.ContactEmail = campus.ContactEmail;
        existing.ContactPhone = campus.ContactPhone;
        existing.Status = campus.Status;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        if (campus.IsPrimarySite && !existing.IsPrimarySite)
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

        await _audit.LogAsync("TrainingProviderCampus", existing.Id, "UpdateCampus", currentUsername, new
        {
            existing.CampusName,
            existing.Status,
            existing.IsPrimarySite
        });

        return existing;
    }

    public async Task<bool> DeleteCampusAsync(int campusId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var campus = await db.TrainingProviderCampuses.FindAsync(campusId);
        if (campus == null) return false;

        db.TrainingProviderCampuses.Remove(campus);
        await db.SaveChangesAsync();

        await _audit.LogAsync("TrainingProviderCampus", campusId, "DeleteCampus", currentUsername, new
        {
            campus.CampusName,
            campus.CampusCode
        });

        return true;
    }

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
