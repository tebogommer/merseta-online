using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public interface ITrainingProviderService
{
    Task<List<TrainingProvider>> GetAllAsync(string? search = null, string? providerType = null, string? status = null);
    Task<TrainingProvider?> GetByIdAsync(int id);
    Task<TrainingProvider?> GetByOrganisationIdAsync(int organisationId);
    Task<TrainingProvider> CreateAsync(TrainingProvider provider, string currentUsername = "SYSTEM");
    Task<TrainingProvider> UpdateAsync(TrainingProvider provider, string currentUsername = "SYSTEM");
    Task<TrainingProvider> SaveAsync(TrainingProvider provider, string currentUsername = "SYSTEM");
    Task<bool> DeleteAsync(int id, string currentUsername = "SYSTEM");

    Task<TrainingProviderQualification> AddQualificationAsync(TrainingProviderQualification qualification, string currentUsername = "SYSTEM");
    Task<TrainingProviderQualification> AddQualificationAsync(int trainingProviderId, TrainingProviderQualification qualification, string currentUsername = "SYSTEM");
    Task<List<TrainingProviderQualification>> GetQualificationsAsync(int trainingProviderId);
    Task<bool> RemoveQualificationAsync(int qualificationId, string currentUsername = "SYSTEM");

    Task<TrainingProviderUnitStandard> AddUnitStandardAsync(TrainingProviderUnitStandard unitStandard, string currentUsername = "SYSTEM");
    Task<TrainingProviderUnitStandard> AddUnitStandardAsync(int trainingProviderId, TrainingProviderUnitStandard unitStandard, string currentUsername = "SYSTEM");
    Task<List<TrainingProviderUnitStandard>> GetUnitStandardsAsync(int trainingProviderId);
    Task<bool> RemoveUnitStandardAsync(int unitStandardId, string currentUsername = "SYSTEM");
}

public class TrainingProviderService : ITrainingProviderService
{
    private readonly INsdmsDbContext _db;
    private readonly IAuditService _audit;

    public TrainingProviderService(INsdmsDbContext db, IAuditService audit)
    {
        _db = db;
        _audit = audit;
    }

    public async Task<List<TrainingProvider>> GetAllAsync(string? search = null, string? providerType = null, string? status = null)
    {
        var query = _db.TrainingProviders
            .Include(tp => tp.Organisation)
            .Include(tp => tp.PrimaryContactPerson)
            .Include(tp => tp.Qualifications)
            .Include(tp => tp.UnitStandards)
            .AsQueryable();

        if (!string.IsNullOrWhiteSpace(search))
        {
            var s = search.Trim();
            query = query.Where(tp =>
                tp.AccreditationNumber.Contains(s) ||
                (tp.Organisation != null && tp.Organisation.CompanyName.Contains(s)) ||
                (tp.EtqaDecisionNumber != null && tp.EtqaDecisionNumber.Contains(s)));
        }

        if (!string.IsNullOrWhiteSpace(providerType))
        {
            query = query.Where(tp => tp.ProviderTypeCode == providerType);
        }

        if (!string.IsNullOrWhiteSpace(status))
        {
            query = query.Where(tp => tp.ProviderStatusCode == status);
        }

        return await query
            .OrderBy(tp => tp.AccreditationNumber)
            .ToListAsync();
    }

    public async Task<TrainingProvider?> GetByIdAsync(int id)
    {
        return await _db.TrainingProviders
            .Include(tp => tp.Organisation)
            .Include(tp => tp.PrimaryContactPerson)
            .Include(tp => tp.Qualifications)
            .Include(tp => tp.UnitStandards)
            .FirstOrDefaultAsync(tp => tp.Id == id);
    }

    public async Task<TrainingProvider?> GetByOrganisationIdAsync(int organisationId)
    {
        return await _db.TrainingProviders
            .Include(tp => tp.Organisation)
            .Include(tp => tp.PrimaryContactPerson)
            .Include(tp => tp.Qualifications)
            .Include(tp => tp.UnitStandards)
            .FirstOrDefaultAsync(tp => tp.OrganisationId == organisationId);
    }

    public async Task<TrainingProvider> CreateAsync(TrainingProvider provider, string currentUsername = "SYSTEM")
    {
        if (string.IsNullOrWhiteSpace(provider.AccreditationNumber))
        {
            throw new ArgumentException("Accreditation number is required.");
        }

        provider.CreatedAt = DateTime.UtcNow;
        provider.CreatedBy = currentUsername;

        _db.TrainingProviders.Add(provider);
        await _db.SaveChangesAsync();

        await _audit.LogActionAsync("TrainingProvider", provider.Id, "Create", currentUsername, null, provider);

        return provider;
    }

    public async Task<TrainingProvider> UpdateAsync(TrainingProvider provider, string currentUsername = "SYSTEM")
    {
        var existing = await _db.TrainingProviders.FindAsync(provider.Id);
        if (existing == null)
        {
            throw new KeyNotFoundException($"TrainingProvider with ID {provider.Id} was not found.");
        }

        var beforeState = new
        {
            existing.AccreditationNumber,
            existing.AccreditationStartDate,
            existing.AccreditationEndDate,
            existing.ProviderTypeCode,
            existing.ProviderStatusCode,
            existing.EtqaDecisionNumber,
            existing.MaxLearnerCapacity,
            existing.PrimaryContactPersonId,
            existing.IsActive
        };

        existing.AccreditationNumber = provider.AccreditationNumber;
        existing.AccreditationStartDate = provider.AccreditationStartDate;
        existing.AccreditationEndDate = provider.AccreditationEndDate;
        existing.ProviderTypeCode = provider.ProviderTypeCode;
        existing.ProviderStatusCode = provider.ProviderStatusCode;
        existing.EtqaDecisionNumber = provider.EtqaDecisionNumber;
        existing.MaxLearnerCapacity = provider.MaxLearnerCapacity;
        existing.PrimaryContactPersonId = provider.PrimaryContactPersonId;
        existing.IsActive = provider.IsActive;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        await _db.SaveChangesAsync();

        await _audit.LogActionAsync("TrainingProvider", existing.Id, "Update", currentUsername, beforeState, existing);

        return existing;
    }

    public async Task<TrainingProvider> SaveAsync(TrainingProvider provider, string currentUsername = "SYSTEM")
    {
        if (provider.Id == 0)
        {
            return await CreateAsync(provider, currentUsername);
        }

        return await UpdateAsync(provider, currentUsername);
    }

    public async Task<bool> DeleteAsync(int id, string currentUsername = "SYSTEM")
    {
        var provider = await _db.TrainingProviders.FindAsync(id);
        if (provider == null)
        {
            return false;
        }

        var beforeState = new
        {
            provider.Id,
            provider.OrganisationId,
            provider.AccreditationNumber,
            provider.ProviderStatusCode,
            provider.IsActive
        };

        _db.TrainingProviders.Remove(provider);
        await _db.SaveChangesAsync();

        await _audit.LogActionAsync("TrainingProvider", id, "Delete", currentUsername, beforeState, null);

        return true;
    }

    public async Task<TrainingProviderQualification> AddQualificationAsync(TrainingProviderQualification qualification, string currentUsername = "SYSTEM")
    {
        if (qualification.TrainingProviderId <= 0)
        {
            throw new ArgumentException("A valid TrainingProviderId must be specified.");
        }

        qualification.CreatedAt = DateTime.UtcNow;
        qualification.CreatedBy = currentUsername;

        _db.TrainingProviderQualifications.Add(qualification);
        await _db.SaveChangesAsync();

        await _audit.LogActionAsync("TrainingProviderQualification", qualification.Id, "AddQualification", currentUsername, null, qualification);

        return qualification;
    }

    public async Task<TrainingProviderQualification> AddQualificationAsync(int trainingProviderId, TrainingProviderQualification qualification, string currentUsername = "SYSTEM")
    {
        qualification.TrainingProviderId = trainingProviderId;
        return await AddQualificationAsync(qualification, currentUsername);
    }

    public async Task<List<TrainingProviderQualification>> GetQualificationsAsync(int trainingProviderId)
    {
        return await _db.TrainingProviderQualifications
            .Where(q => q.TrainingProviderId == trainingProviderId)
            .OrderBy(q => q.QualificationTitle)
            .ToListAsync();
    }

    public async Task<bool> RemoveQualificationAsync(int qualificationId, string currentUsername = "SYSTEM")
    {
        var qualification = await _db.TrainingProviderQualifications.FindAsync(qualificationId);
        if (qualification == null)
        {
            return false;
        }

        var beforeState = new
        {
            qualification.Id,
            qualification.TrainingProviderId,
            qualification.SaqaQualificationId,
            qualification.QualificationTitle
        };

        _db.TrainingProviderQualifications.Remove(qualification);
        await _db.SaveChangesAsync();

        await _audit.LogActionAsync("TrainingProviderQualification", qualificationId, "RemoveQualification", currentUsername, beforeState, null);

        return true;
    }

    public async Task<TrainingProviderUnitStandard> AddUnitStandardAsync(TrainingProviderUnitStandard unitStandard, string currentUsername = "SYSTEM")
    {
        if (unitStandard.TrainingProviderId <= 0)
        {
            throw new ArgumentException("A valid TrainingProviderId must be specified.");
        }

        unitStandard.CreatedAt = DateTime.UtcNow;
        unitStandard.CreatedBy = currentUsername;

        _db.TrainingProviderUnitStandards.Add(unitStandard);
        await _db.SaveChangesAsync();

        await _audit.LogActionAsync("TrainingProviderUnitStandard", unitStandard.Id, "AddUnitStandard", currentUsername, null, unitStandard);

        return unitStandard;
    }

    public async Task<TrainingProviderUnitStandard> AddUnitStandardAsync(int trainingProviderId, TrainingProviderUnitStandard unitStandard, string currentUsername = "SYSTEM")
    {
        unitStandard.TrainingProviderId = trainingProviderId;
        return await AddUnitStandardAsync(unitStandard, currentUsername);
    }

    public async Task<List<TrainingProviderUnitStandard>> GetUnitStandardsAsync(int trainingProviderId)
    {
        return await _db.TrainingProviderUnitStandards
            .Where(u => u.TrainingProviderId == trainingProviderId)
            .OrderBy(u => u.UnitStandardTitle)
            .ToListAsync();
    }

    public async Task<bool> RemoveUnitStandardAsync(int unitStandardId, string currentUsername = "SYSTEM")
    {
        var unitStandard = await _db.TrainingProviderUnitStandards.FindAsync(unitStandardId);
        if (unitStandard == null)
        {
            return false;
        }

        var beforeState = new
        {
            unitStandard.Id,
            unitStandard.TrainingProviderId,
            unitStandard.UnitStandardId,
            unitStandard.UnitStandardTitle
        };

        _db.TrainingProviderUnitStandards.Remove(unitStandard);
        await _db.SaveChangesAsync();

        await _audit.LogActionAsync("TrainingProviderUnitStandard", unitStandardId, "RemoveUnitStandard", currentUsername, beforeState, null);

        return true;
    }
}
