using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Models;
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

    // 360-Degree SDP Relational Queries
    Task<List<ProviderLearnerDto>> GetEnrolledLearnersAsync(int trainingProviderId);
    Task<List<ProviderAssessorModeratorDto>> GetAssessorsAndModeratorsAsync(int trainingProviderId);
    Task<List<ProviderEmployerDto>> GetParticipatingEmployersAsync(int trainingProviderId);
    Task<List<ProviderAuditVisitDto>> GetAuditVisitsAsync(int trainingProviderId);
}

public class TrainingProviderService : ITrainingProviderService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;

    public TrainingProviderService(INsdmsDbContextFactory contextFactory, IAuditService audit)
    {
        _contextFactory = contextFactory;
        _audit = audit;
    }

    public async Task<List<TrainingProvider>> GetAllAsync(string? search = null, string? providerType = null, string? status = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.TrainingProviders
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
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.TrainingProviders
            .Include(tp => tp.Organisation)
            .Include(tp => tp.PrimaryContactPerson)
            .Include(tp => tp.Qualifications)
            .Include(tp => tp.UnitStandards)
            .FirstOrDefaultAsync(tp => tp.Id == id);
    }

    public async Task<TrainingProvider?> GetByOrganisationIdAsync(int organisationId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.TrainingProviders
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

        using var db = await _contextFactory.CreateDbContextAsync();
        provider.CreatedAt = DateTime.UtcNow;
        provider.CreatedBy = currentUsername;

        db.TrainingProviders.Add(provider);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "TrainingProvider", provider.Id, "Create", currentUsername, null, provider);
        await db.SaveChangesAsync();

        return provider;
    }

    public async Task<TrainingProvider> UpdateAsync(TrainingProvider provider, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.TrainingProviders.FindAsync(provider.Id);
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

        _audit.LogAction(db, "TrainingProvider", existing.Id, "Update", currentUsername, beforeState, existing);
        await db.SaveChangesAsync();

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
        using var db = await _contextFactory.CreateDbContextAsync();
        var provider = await db.TrainingProviders.FindAsync(id);
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

        db.TrainingProviders.Remove(provider);
        _audit.LogAction(db, "TrainingProvider", id, "Delete", currentUsername, beforeState, null);
        await db.SaveChangesAsync();

        return true;
    }

    public async Task<TrainingProviderQualification> AddQualificationAsync(TrainingProviderQualification qualification, string currentUsername = "SYSTEM")
    {
        if (qualification.TrainingProviderId <= 0)
        {
            throw new ArgumentException("A valid TrainingProviderId must be specified.");
        }

        using var db = await _contextFactory.CreateDbContextAsync();
        qualification.CreatedAt = DateTime.UtcNow;
        qualification.CreatedBy = currentUsername;

        db.TrainingProviderQualifications.Add(qualification);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "TrainingProviderQualification", qualification.Id, "AddQualification", currentUsername, null, qualification);
        await db.SaveChangesAsync();

        return qualification;
    }

    public async Task<TrainingProviderQualification> AddQualificationAsync(int trainingProviderId, TrainingProviderQualification qualification, string currentUsername = "SYSTEM")
    {
        qualification.TrainingProviderId = trainingProviderId;
        return await AddQualificationAsync(qualification, currentUsername);
    }

    public async Task<List<TrainingProviderQualification>> GetQualificationsAsync(int trainingProviderId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.TrainingProviderQualifications
            .Where(q => q.TrainingProviderId == trainingProviderId)
            .OrderBy(q => q.QualificationTitle)
            .ToListAsync();
    }

    public async Task<bool> RemoveQualificationAsync(int qualificationId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var qualification = await db.TrainingProviderQualifications.FindAsync(qualificationId);
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

        db.TrainingProviderQualifications.Remove(qualification);
        _audit.LogAction(db, "TrainingProviderQualification", qualificationId, "RemoveQualification", currentUsername, beforeState, null);
        await db.SaveChangesAsync();

        return true;
    }

    public async Task<TrainingProviderUnitStandard> AddUnitStandardAsync(TrainingProviderUnitStandard unitStandard, string currentUsername = "SYSTEM")
    {
        if (unitStandard.TrainingProviderId <= 0)
        {
            throw new ArgumentException("A valid TrainingProviderId must be specified.");
        }

        using var db = await _contextFactory.CreateDbContextAsync();
        unitStandard.CreatedAt = DateTime.UtcNow;
        unitStandard.CreatedBy = currentUsername;

        db.TrainingProviderUnitStandards.Add(unitStandard);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "TrainingProviderUnitStandard", unitStandard.Id, "AddUnitStandard", currentUsername, null, unitStandard);
        await db.SaveChangesAsync();

        return unitStandard;
    }

    public async Task<TrainingProviderUnitStandard> AddUnitStandardAsync(int trainingProviderId, TrainingProviderUnitStandard unitStandard, string currentUsername = "SYSTEM")
    {
        unitStandard.TrainingProviderId = trainingProviderId;
        return await AddUnitStandardAsync(unitStandard, currentUsername);
    }

    public async Task<List<TrainingProviderUnitStandard>> GetUnitStandardsAsync(int trainingProviderId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.TrainingProviderUnitStandards
            .Where(u => u.TrainingProviderId == trainingProviderId)
            .OrderBy(u => u.UnitStandardTitle)
            .ToListAsync();
    }

    public async Task<bool> RemoveUnitStandardAsync(int unitStandardId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var unitStandard = await db.TrainingProviderUnitStandards.FindAsync(unitStandardId);
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

        db.TrainingProviderUnitStandards.Remove(unitStandard);
        _audit.LogAction(db, "TrainingProviderUnitStandard", unitStandardId, "RemoveUnitStandard", currentUsername, beforeState, null);
        await db.SaveChangesAsync();

        return true;
    }

    // 360-Degree SDP Relational Queries Implementation
    public async Task<List<ProviderLearnerDto>> GetEnrolledLearnersAsync(int trainingProviderId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var learners = await db.CompanyLearners
            .Include(l => l.Person)
            .Include(l => l.Organisation)
            .Where(l => l.TrainingProviderId == trainingProviderId)
            .OrderByDescending(l => l.Id)
            .ToListAsync();

        return learners.Select(l => new ProviderLearnerDto(
            l.Id,
            l.LearnerContractNumber,
            l.Person != null ? $"{l.Person.FirstName} {l.Person.LastName}".Trim() : "Unknown Learner",
            l.Person?.RsaIdNumber,
            l.QualificationTitle,
            l.LearningProgrammeTypeCode,
            GetProgrammeTypeName(l.LearningProgrammeTypeCode),
            l.Organisation?.CompanyName,
            l.OrganisationId > 0 ? l.OrganisationId : null,
            l.RegistrationDate,
            l.EnrolmentStatusCode ?? "Registered"
        )).ToList();
    }

    public async Task<List<ProviderAssessorModeratorDto>> GetAssessorsAndModeratorsAsync(int trainingProviderId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var provider = await db.TrainingProviders.FindAsync(trainingProviderId);
        if (provider == null) return new List<ProviderAssessorModeratorDto>();

        var assessors = await db.EtqaAssessors
            .Include(a => a.Person)
            .Include(a => a.Scopes)
            .Where(a => a.IsActive)
            .ToListAsync();

        return assessors.Select(a => new ProviderAssessorModeratorDto(
            a.Id,
            a.Person != null ? $"{a.Person.FirstName} {a.Person.LastName}".Trim() : "Assessor #" + a.Id,
            !string.IsNullOrWhiteSpace(a.RegistrationNumber) ? a.RegistrationNumber : "REG-" + a.Id,
            a.EtqaRole ?? "Assessor",
            string.Join(", ", a.Scopes.Select(s => s.QualificationTitle)),
            a.EndDate,
            a.RegistrationStatusCode ?? "Active"
        )).ToList();
    }

    public async Task<List<ProviderEmployerDto>> GetParticipatingEmployersAsync(int trainingProviderId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var learners = await db.CompanyLearners
            .Include(l => l.Organisation!)
                .ThenInclude(o => o.PrimaryContactPerson)
            .Include(l => l.OrganisationSite)
            .Where(l => l.TrainingProviderId == trainingProviderId && l.Organisation != null)
            .ToListAsync();

        var learnerEmployers = learners.GroupBy(l => l.OrganisationId);

        var list = new List<ProviderEmployerDto>();
        foreach (var group in learnerEmployers)
        {
            var first = group.First();
            var org = first.Organisation;
            if (org == null) continue;

            var contact = org.PrimaryContactPerson;
            list.Add(new ProviderEmployerDto(
                org.Id,
                org.CompanyName,
                org.SdlNumber,
                first.OrganisationSite?.SiteName ?? "Main Office / Plant",
                group.Count(),
                contact != null ? $"{contact.FirstName} {contact.LastName}".Trim() : null,
                contact?.Email,
                contact?.PhoneNumber ?? contact?.CellNumber
            ));
        }
        return list;
    }

    public async Task<List<ProviderAuditVisitDto>> GetAuditVisitsAsync(int trainingProviderId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var provider = await db.TrainingProviders
            .Include(tp => tp.Organisation)
            .FirstOrDefaultAsync(tp => tp.Id == trainingProviderId);

        if (provider == null) return new List<ProviderAuditVisitDto>();

        var visits = await db.Visits
            .Include(v => v.ContactPerson)
            .Where(v => v.OrganisationId == provider.OrganisationId)
            .OrderByDescending(v => v.VisitDate)
            .ToListAsync();

        return visits.Select(v => new ProviderAuditVisitDto(
            v.Id,
            "VST-" + v.Id,
            v.VisitTypeCode ?? "ETQA Audit",
            v.VisitDate,
            v.ContactPerson != null ? $"{v.ContactPerson.FirstName} {v.ContactPerson.LastName}".Trim() : "ETQA Quality Officer",
            v.VisitStatusCode ?? "Completed",
            v.Purpose ?? v.Title
        )).ToList();
    }

    private static string GetProgrammeTypeName(string? code) => code switch
    {
        "01" => "Apprenticeship",
        "02" => "Learnership",
        "03" => "Skills Programme",
        "04" => "Internship",
        "05" => "Bursary",
        "06" => "Candidacy",
        "07" => "ARPL",
        _ => code ?? "Learnership"
    };
}
