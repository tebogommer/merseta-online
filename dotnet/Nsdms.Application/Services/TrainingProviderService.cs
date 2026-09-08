using System.Security.Cryptography;
using System.Text;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
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

    // Phase 34: SDP Statutory Governance Standards
    Task<List<TrainingProviderSelfEvaluation>> GetSelfEvaluationsAsync(int trainingProviderId);
    Task<List<TrainingProviderSelfEvaluation>> SeedDefaultSelfEvaluationsAsync(int trainingProviderId, string currentUsername = "SYSTEM");
    Task<TrainingProviderSelfEvaluation> SaveSelfEvaluationAsync(TrainingProviderSelfEvaluation item, string currentUsername = "SYSTEM");
    Task SaveSelfEvaluationsAsync(int trainingProviderId, List<TrainingProviderSelfEvaluation> items, string currentUsername = "SYSTEM");
    Task<bool> SubmitSelfEvaluationAsync(int trainingProviderId, List<TrainingProviderSelfEvaluation> items, string currentUsername = "SYSTEM");
    Task<bool> SubmitSelfEvaluationAsync(int trainingProviderId, string currentUsername = "SYSTEM");

    Task<List<TrainingProviderContact>> GetContactsAsync(int trainingProviderId);
    Task<TrainingProviderContact> AddOrUpdateContactAsync(TrainingProviderContact contact, string currentUsername = "SYSTEM");
    Task<TrainingProviderContact> SaveContactAsync(int trainingProviderId, TrainingProviderContact contact, string currentUsername = "SYSTEM");
    Task<bool> RemoveContactAsync(int contactId, string currentUsername = "SYSTEM");
    Task<bool> DeleteContactAsync(int contactId, string currentUsername = "SYSTEM");
    Task<(bool IsValid, string Message)> ValidateContactQuorumAsync(int trainingProviderId);

    Task<TrainingProvider> InitiateReAccreditationAsync(int trainingProviderId, string currentUsername = "SYSTEM");
    DateTime Calculate5WorkingDaysDueDate(DateTime startDate);
    string GenerateAccreditationSecuritySeal(TrainingProvider provider);

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
    private readonly ISystemConfigurationService? _configService;

    public TrainingProviderService(
        INsdmsDbContextFactory contextFactory,
        IAuditService audit,
        ISystemConfigurationService? configService = null)
    {
        _contextFactory = contextFactory;
        _audit = audit;
        _configService = configService;
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

        if (string.IsNullOrWhiteSpace(provider.AccreditationStream))
        {
            provider.AccreditationStream = "PrimaryAccreditation";
        }

        ValidateAccreditationStream(provider);

        if (!provider.InspectionDueDate.HasValue)
        {
            provider.InspectionDueDate = Calculate5WorkingDaysDueDate(DateTime.UtcNow);
        }

        if (string.IsNullOrWhiteSpace(provider.DigitalSecuritySeal))
        {
            provider.DigitalSecuritySeal = GenerateAccreditationSecuritySeal(provider);
        }

        db.TrainingProviders.Add(provider);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "TrainingProvider", provider.Id, "Create", currentUsername, null, provider);
        await db.SaveChangesAsync();

        return provider;
    }

    public async Task<TrainingProvider> UpdateAsync(TrainingProvider provider, string currentUsername = "SYSTEM")
    {
        ValidateAccreditationStream(provider);

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
            existing.AccreditationStream,
            existing.PrimaryEtqaName,
            existing.ReAccreditationUnderway,
            existing.InspectionDueDate,
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
        existing.AccreditationStream = provider.AccreditationStream ?? existing.AccreditationStream;
        existing.PrimaryEtqaName = provider.PrimaryEtqaName;
        existing.PrimaryAccreditationNumber = provider.PrimaryAccreditationNumber;
        existing.PrimaryAccreditationStartDate = provider.PrimaryAccreditationStartDate;
        existing.PrimaryAccreditationEndDate = provider.PrimaryAccreditationEndDate;
        existing.NambRegistrationNumber = provider.NambRegistrationNumber;
        existing.NambRegistrationStartDate = provider.NambRegistrationStartDate;
        existing.NambRegistrationEndDate = provider.NambRegistrationEndDate;
        existing.QctoAccreditationNumber = provider.QctoAccreditationNumber;
        existing.QctoAccreditationStartDate = provider.QctoAccreditationStartDate;
        existing.QctoAccreditationEndDate = provider.QctoAccreditationEndDate;
        existing.QctoCentreCode = provider.QctoCentreCode;
        existing.QctoLetterAttachmentRef = provider.QctoLetterAttachmentRef;
        existing.EtqaCommitteeDecisionNumber = provider.EtqaCommitteeDecisionNumber;
        existing.EtqaCommitteeMeetingDate = provider.EtqaCommitteeMeetingDate;
        existing.ReAccreditationUnderway = provider.ReAccreditationUnderway;
        existing.ReAccreditationEffectiveDate = provider.ReAccreditationEffectiveDate;
        existing.InspectionDueDate = provider.InspectionDueDate;
        existing.DigitalSecuritySeal = provider.DigitalSecuritySeal ?? GenerateAccreditationSecuritySeal(existing);
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

    // Phase 34: SDP Statutory Governance Standards
    public async Task<List<TrainingProviderSelfEvaluation>> GetSelfEvaluationsAsync(int trainingProviderId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.TrainingProviderSelfEvaluations
            .Where(e => e.TrainingProviderId == trainingProviderId)
            .OrderBy(e => e.CriteriaCode)
            .ToListAsync();
    }

    public async Task<List<TrainingProviderSelfEvaluation>> SeedDefaultSelfEvaluationsAsync(int trainingProviderId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.TrainingProviderSelfEvaluations
            .Where(e => e.TrainingProviderId == trainingProviderId)
            .ToListAsync();

        if (existing.Count > 0)
        {
            return existing;
        }

        var defaultCriteria = new List<TrainingProviderSelfEvaluation>
        {
            new() { TrainingProviderId = trainingProviderId, CriteriaCode = "QMS-01", CriteriaCategory = "Policy & Governance", CriteriaDescription = "Institutional governance structure, legal standing with CIPC/Trust, and statutory tax compliance with SARS.", IsCompliant = true, CreatedBy = currentUsername },
            new() { TrainingProviderId = trainingProviderId, CriteriaCode = "QMS-02", CriteriaCategory = "Policy & Governance", CriteriaDescription = "Formally adopted Learner Admissions, Registration, and Recognition of Prior Learning (RPL) policies.", IsCompliant = true, CreatedBy = currentUsername },
            new() { TrainingProviderId = trainingProviderId, CriteriaCode = "QMS-03", CriteriaCategory = "Assessment & Moderation", CriteriaDescription = "Documented internal assessment, moderation, reassessment, and learner appeals procedures.", IsCompliant = true, CreatedBy = currentUsername },
            new() { TrainingProviderId = trainingProviderId, CriteriaCode = "QMS-04", CriteriaCategory = "Curriculum & Delivery", CriteriaDescription = "Approved occupational curriculum delivery strategy, learning materials, and structured artisan training plans.", IsCompliant = true, CreatedBy = currentUsername },
            new() { TrainingProviderId = trainingProviderId, CriteriaCode = "QMS-05", CriteriaCategory = "Academic Staffing", CriteriaDescription = "Accredited and ETQA-registered assessors, internal moderators, and technical facilitators with statutory SLA agreements.", IsCompliant = true, CreatedBy = currentUsername },
            new() { TrainingProviderId = trainingProviderId, CriteriaCode = "QMS-06", CriteriaCategory = "Facilities & Safety", CriteriaDescription = "Physical training campus infrastructure with valid Occupational Health & Safety (OHS) clearance certificate.", IsCompliant = true, CreatedBy = currentUsername },
            new() { TrainingProviderId = trainingProviderId, CriteriaCode = "QMS-07", CriteriaCategory = "Tools & Equipment", CriteriaDescription = "Designated trade toolkit, machinery inventory, and practical workshop equipment matching curriculum specs.", IsCompliant = true, CreatedBy = currentUsername },
            new() { TrainingProviderId = trainingProviderId, CriteriaCode = "QMS-08", CriteriaCategory = "Learner Support", CriteriaDescription = "Learner guidance, code of conduct, grievance mechanisms, and special learning needs accommodation.", IsCompliant = true, CreatedBy = currentUsername },
            new() { TrainingProviderId = trainingProviderId, CriteriaCode = "QMS-09", CriteriaCategory = "Records & MIS", CriteriaDescription = "Management Information System (MIS) capable of data extraction for SETMIS File 100/200 and SAQA NLRD.", IsCompliant = true, CreatedBy = currentUsername },
            new() { TrainingProviderId = trainingProviderId, CriteriaCode = "QMS-10", CriteriaCategory = "Financial Viability", CriteriaDescription = "Financial sustainability, valid public liability insurance, and formal tripartite agreement governance.", IsCompliant = true, CreatedBy = currentUsername }
        };

        db.TrainingProviderSelfEvaluations.AddRange(defaultCriteria);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "TrainingProviderSelfEvaluation", trainingProviderId, "SeedDefaultCriteria", currentUsername, null, new { Count = defaultCriteria.Count });
        await db.SaveChangesAsync();

        return defaultCriteria;
    }

    public async Task<TrainingProviderSelfEvaluation> SaveSelfEvaluationAsync(TrainingProviderSelfEvaluation item, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        if (item.Id == 0)
        {
            item.CreatedAt = DateTime.UtcNow;
            item.CreatedBy = currentUsername;
            db.TrainingProviderSelfEvaluations.Add(item);
        }
        else
        {
            var existing = await db.TrainingProviderSelfEvaluations.FindAsync(item.Id);
            if (existing == null) throw new KeyNotFoundException($"SelfEvaluation {item.Id} not found.");

            existing.IsCompliant = item.IsCompliant;
            existing.DocumentReferenceNumber = item.DocumentReferenceNumber;
            existing.ApplicantComments = item.ApplicantComments;
            existing.AssessorVerified = item.AssessorVerified;
            existing.AssessorFindings = item.AssessorFindings;
            existing.ModifiedAt = DateTime.UtcNow;
            existing.ModifiedBy = currentUsername;
            item = existing;
        }

        await db.SaveChangesAsync();
        _audit.LogAction(db, "TrainingProviderSelfEvaluation", item.Id, "Save", currentUsername, null, item);
        await db.SaveChangesAsync();

        return item;
    }

    public async Task<bool> SubmitSelfEvaluationAsync(int trainingProviderId, List<TrainingProviderSelfEvaluation> items, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var provider = await db.TrainingProviders.FindAsync(trainingProviderId);
        if (provider == null) throw new KeyNotFoundException($"TrainingProvider {trainingProviderId} not found.");

        foreach (var item in items)
        {
            if (item.Id == 0)
            {
                item.TrainingProviderId = trainingProviderId;
                item.CreatedAt = DateTime.UtcNow;
                item.CreatedBy = currentUsername;
                db.TrainingProviderSelfEvaluations.Add(item);
            }
            else
            {
                var existing = await db.TrainingProviderSelfEvaluations.FindAsync(item.Id);
                if (existing != null)
                {
                    existing.IsCompliant = item.IsCompliant;
                    existing.DocumentReferenceNumber = item.DocumentReferenceNumber;
                    existing.ApplicantComments = item.ApplicantComments;
                    existing.AssessorVerified = item.AssessorVerified;
                    existing.AssessorFindings = item.AssessorFindings;
                    existing.ModifiedAt = DateTime.UtcNow;
                    existing.ModifiedBy = currentUsername;
                }
            }
        }

        // Advance workflow status
        provider.ProviderStatusCode = "QMS_SUBMITTED";
        provider.ModifiedAt = DateTime.UtcNow;
        provider.ModifiedBy = currentUsername;

        _audit.LogAction(db, "TrainingProvider", trainingProviderId, "SubmitSelfEvaluation", currentUsername, null, new { ItemCount = items.Count });
        await db.SaveChangesAsync();

        return true;
    }

    public async Task SaveSelfEvaluationsAsync(int trainingProviderId, List<TrainingProviderSelfEvaluation> items, string currentUsername = "SYSTEM")
    {
        foreach (var item in items)
        {
            item.TrainingProviderId = trainingProviderId;
            await SaveSelfEvaluationAsync(item, currentUsername);
        }
    }

    public async Task<bool> SubmitSelfEvaluationAsync(int trainingProviderId, string currentUsername = "SYSTEM")
    {
        var items = await GetSelfEvaluationsAsync(trainingProviderId);
        return await SubmitSelfEvaluationAsync(trainingProviderId, items, currentUsername);
    }

    public async Task<List<TrainingProviderContact>> GetContactsAsync(int trainingProviderId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.TrainingProviderContacts
            .Include(c => c.Person)
            .Where(c => c.TrainingProviderId == trainingProviderId && c.IsActive)
            .OrderBy(c => c.ContactDesignation)
            .ToListAsync();
    }

    public async Task<TrainingProviderContact> AddOrUpdateContactAsync(TrainingProviderContact contact, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        if (contact.Id == 0)
        {
            contact.CreatedAt = DateTime.UtcNow;
            contact.CreatedBy = currentUsername;
            db.TrainingProviderContacts.Add(contact);
        }
        else
        {
            var existing = await db.TrainingProviderContacts.FindAsync(contact.Id);
            if (existing == null) throw new KeyNotFoundException($"Contact {contact.Id} not found.");

            existing.ContactDesignation = contact.ContactDesignation;
            existing.Title = contact.Title;
            existing.FirstName = contact.FirstName;
            existing.LastName = contact.LastName;
            existing.IdOrPassportNumber = contact.IdOrPassportNumber;
            existing.Email = contact.Email;
            existing.CellNumber = contact.CellNumber;
            existing.IsBankingConfirmationAuthorized = contact.IsBankingConfirmationAuthorized;
            existing.IsActive = contact.IsActive;
            existing.ModifiedAt = DateTime.UtcNow;
            existing.ModifiedBy = currentUsername;
            contact = existing;
        }

        await db.SaveChangesAsync();
        _audit.LogAction(db, "TrainingProviderContact", contact.Id, "Save", currentUsername, null, contact);
        await db.SaveChangesAsync();

        return contact;
    }

    public async Task<bool> RemoveContactAsync(int contactId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var contact = await db.TrainingProviderContacts.FindAsync(contactId);
        if (contact == null) return false;

        contact.IsActive = false;
        contact.ModifiedAt = DateTime.UtcNow;
        contact.ModifiedBy = currentUsername;

        _audit.LogAction(db, "TrainingProviderContact", contact.Id, "Deactivate", currentUsername, null, new { contact.Id });
        await db.SaveChangesAsync();

        return true;
    }

    public async Task<TrainingProviderContact> SaveContactAsync(int trainingProviderId, TrainingProviderContact contact, string currentUsername = "SYSTEM")
    {
        contact.TrainingProviderId = trainingProviderId;
        return await AddOrUpdateContactAsync(contact, currentUsername);
    }

    public async Task<bool> DeleteContactAsync(int contactId, string currentUsername = "SYSTEM")
    {
        return await RemoveContactAsync(contactId, currentUsername);
    }

    public async Task<(bool IsValid, string Message)> ValidateContactQuorumAsync(int trainingProviderId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var contacts = await db.TrainingProviderContacts
            .Where(c => c.TrainingProviderId == trainingProviderId && c.IsActive)
            .ToListAsync();

        if (contacts.Count < 2)
        {
            return (false, $"Contact Quorum Failed: Exactly {contacts.Count} active contact person(s) registered. A minimum of two (2) verified contact persons is statutorily required.");
        }

        var hasBankingConfirmation = contacts.Any(c => c.IsBankingConfirmationAuthorized);
        if (!hasBankingConfirmation)
        {
            return (false, "Banking Confirmation Invariant Failed: At least one designated contact person other than the primary SDF must be authorized to confirm banking details.");
        }

        return (true, "Contact quorum and banking confirmation authority verified successfully.");
    }

    public async Task<TrainingProvider> InitiateReAccreditationAsync(int trainingProviderId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var provider = await db.TrainingProviders.FindAsync(trainingProviderId);
        if (provider == null) throw new KeyNotFoundException($"TrainingProvider {trainingProviderId} not found.");

        // Enforce 6-Month Invariant (Table 14 BR4)
        if (provider.AccreditationEndDate.HasValue)
        {
            var sixMonthsPrior = provider.AccreditationEndDate.Value.AddMonths(-6);
            if (DateTime.UtcNow < sixMonthsPrior)
            {
                throw new InvalidOperationException($"Statutory Invariant: Re-accreditation application is strictly permitted within 6 months prior to expiry. Current accreditation expires on {provider.AccreditationEndDate.Value:dd MMMM yyyy}; earliest re-accreditation date is {sixMonthsPrior:dd MMMM yyyy}.");
            }
        }

        var beforeState = new { provider.ReAccreditationUnderway, provider.ProviderStatusCode };

        // Statutory Non-Disruption Invariant (BR4):
        // Provider status MUST NOT revert to Pending Approval; SDP remains Accredited and able to transact learner enrolments!
        provider.ReAccreditationUnderway = true;
        provider.ReAccreditationEffectiveDate = DateTime.UtcNow;
        provider.ModifiedAt = DateTime.UtcNow;
        provider.ModifiedBy = currentUsername;

        _audit.LogAction(db, "TrainingProvider", provider.Id, "InitiateReAccreditation", currentUsername, beforeState, provider);
        await db.SaveChangesAsync();

        return provider;
    }

    public DateTime Calculate5WorkingDaysDueDate(DateTime startDate)
    {
        var slaDays = _configService?.GetValueAsync<int>("TrainingProvider:SiteInspectionSlaBusinessDays", 5).GetAwaiter().GetResult() ?? 5;
        return WorkplaceApprovalService.AddBusinessDays(startDate, slaDays);
    }

    public string GenerateAccreditationSecuritySeal(TrainingProvider provider)
    {
        var raw = $"{provider.AccreditationNumber}:{provider.OrganisationId}:{provider.AccreditationStream}:{provider.QctoAccreditationNumber}:{provider.AccreditationStartDate:yyyy-MM-dd}:{provider.AccreditationEndDate:yyyy-MM-dd}:{provider.EtqaDecisionNumber}:{provider.ProviderCode}";
        using var sha = SHA256.Create();
        var bytes = sha.ComputeHash(Encoding.UTF8.GetBytes(raw));
        return Convert.ToHexString(bytes).ToLowerInvariant();
    }

    private static void ValidateAccreditationStream(TrainingProvider provider)
    {
        if (provider.AccreditationStream == AccreditationStreamType.QctoSkillsDevelopmentProvider)
        {
            if (string.IsNullOrWhiteSpace(provider.QctoAccreditationNumber))
            {
                throw new InvalidOperationException("QCTO Accreditation Number is mandatory for QCTO Skills Development Providers.");
            }
            if (provider.QctoAccreditationStartDate.HasValue && provider.QctoAccreditationEndDate.HasValue &&
                provider.QctoAccreditationStartDate.Value > provider.QctoAccreditationEndDate.Value)
            {
                throw new InvalidOperationException("QCTO Accreditation End Date cannot precede Start Date.");
            }
        }
        else if (provider.AccreditationStream == AccreditationStreamType.QctoTradeTestCentre)
        {
            if (string.IsNullOrWhiteSpace(provider.NambRegistrationNumber))
            {
                throw new InvalidOperationException("NAMB Trade Test Centre Registration Number is mandatory for QCTO Trade Test Centres.");
            }
            if (provider.NambRegistrationStartDate.HasValue && provider.NambRegistrationEndDate.HasValue &&
                provider.NambRegistrationStartDate.Value > provider.NambRegistrationEndDate.Value)
            {
                throw new InvalidOperationException("NAMB Trade Test Centre Registration End Date cannot precede Start Date.");
            }
        }
        else if (provider.AccreditationStream == AccreditationStreamType.ProgrammeApproval)
        {
            if (string.IsNullOrWhiteSpace(provider.PrimaryEtqaName))
            {
                throw new InvalidOperationException("Primary ETQA / SETA Name is mandatory for Programme Approval stream.");
            }
            if (string.IsNullOrWhiteSpace(provider.PrimaryAccreditationNumber))
            {
                throw new InvalidOperationException("Primary SETA Accreditation Number is mandatory for Programme Approval stream.");
            }
        }
    }
}
