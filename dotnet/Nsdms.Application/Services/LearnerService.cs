using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Models;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public interface ILearnerService
{
    // Company Learners
    Task<List<CompanyLearner>> GetAllLearnersAsync(string? search = null, string? status = null, int? organisationId = null, string? programmeType = null);
    Task<PagedResult<LearnerListDto>> GetPagedAsync(PaginationQuery query, CancellationToken cancellationToken = default);
    Task<CompanyLearner?> GetLearnerByIdAsync(int id);
    Task<CompanyLearner> RegisterLearnerAsync(CompanyLearner learner, string currentUsername = "SYSTEM");
    Task<CompanyLearner> UpdateLearnerAsync(CompanyLearner learner, string currentUsername = "SYSTEM");
    Task<CompanyLearner> SaveLearnerAsync(CompanyLearner learner, string currentUsername = "SYSTEM");
    Task<bool> DeleteLearnerAsync(int id, string currentUsername = "SYSTEM");

    // Trade Testing & Progression
    Task<LearnerTradeTest> ScheduleTradeTestAsync(LearnerTradeTest tradeTest, string currentUsername = "SYSTEM");
    Task<LearnerTradeTest> RecordTradeTestResultAsync(int tradeTestId, string resultStatusCode, string? certificateNumber, string? remarks, string currentUsername = "SYSTEM");
    Task<LearnerTradeTest?> GetTradeTestByIdAsync(int id);
    Task<List<LearnerTradeTest>> GetAllTradeTestsAsync(string? search = null, string? resultStatus = null);
    Task<List<LearnerTradeTest>> GetTradeTestsForLearnerAsync(int companyLearnerId);
    Task<bool> DeleteTradeTestAsync(int id, string currentUsername = "SYSTEM");

    // 360-Degree Learner Relational Queries
    Task<List<LearnerEnrolmentDto>> GetLearnerEnrolmentsAsync(int personId);
    Task<List<LearnerEmployerLinkDto>> GetLearnerEmployersAsync(int personId);
    Task<List<LearnerSdpLinkDto>> GetLearnerProvidersAsync(int personId);
    Task<List<LearnerAssessmentDto>> GetLearnerAssessmentsAsync(int personId);
    Task<List<LearnerStipendDto>> GetLearnerStipendsAsync(int personId);
}

public class LearnerService : ILearnerService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;

    public LearnerService(INsdmsDbContextFactory contextFactory, IAuditService audit)
    {
        _contextFactory = contextFactory;
        _audit = audit;
    }

    public async Task<PagedResult<LearnerListDto>> GetPagedAsync(PaginationQuery query, CancellationToken cancellationToken = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var baseQuery = db.CompanyLearners.AsNoTracking();

        if (query.FilterParams.TryGetValue("status", out var statusVal) && !string.IsNullOrWhiteSpace(statusVal) && statusVal != "All")
        {
            baseQuery = baseQuery.Where(l => l.EnrolmentStatusCode == statusVal || l.EnrolmentStatusId == statusVal);
        }

        if (query.FilterParams.TryGetValue("programmeType", out var progVal) && !string.IsNullOrWhiteSpace(progVal) && progVal != "All")
        {
            baseQuery = baseQuery.Where(l => l.LearningProgrammeTypeCode == progVal);
        }

        if (!string.IsNullOrWhiteSpace(query.SearchText))
        {
            var s = query.SearchText.Trim();
            baseQuery = baseQuery.Where(l =>
                (l.LearnerContractNumber != null && l.LearnerContractNumber.Contains(s)) ||
                (l.QualificationTitle != null && l.QualificationTitle.Contains(s)) ||
                (l.Person != null && (l.Person.FirstName.Contains(s) || l.Person.LastName.Contains(s) || l.Person.RsaIdNumber.Contains(s))) ||
                (l.Organisation != null && (l.Organisation.CompanyName.Contains(s) || l.Organisation.SdlNumber.Contains(s))));
        }

        var totalCount = await baseQuery.CountAsync(cancellationToken);

        var pagedEntities = await baseQuery
            .Include(l => l.Person)
            .Include(l => l.Organisation)
            .OrderByDescending(l => l.Id)
            .Skip(query.PageIndex * query.PageSize)
            .Take(query.PageSize)
            .ToListAsync(cancellationToken);

        var items = pagedEntities.Select(l => new LearnerListDto(
            l.Id,
            l.LearnerContractNumber ?? "LRN-" + l.Id,
            l.Person != null ? $"{l.Person.FirstName} {l.Person.LastName}".Trim() : "Unknown",
            l.Person?.RsaIdNumber,
            l.Organisation?.CompanyName ?? "Unlinked",
            l.Organisation?.SdlNumber,
            l.QualificationTitle ?? "Skills Programme",
            l.LearningProgrammeTypeCode,
            l.NqfLevel,
            l.RegistrationDate,
            l.EnrolmentStatusCode ?? "Registered"
        )).ToList();

        return new PagedResult<LearnerListDto>(items, totalCount, query.PageIndex, query.PageSize);
    }

    public async Task<List<CompanyLearner>> GetAllLearnersAsync(string? search = null, string? status = null, int? organisationId = null, string? programmeType = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.CompanyLearners
            .Include(l => l.Person)
            .Include(l => l.Organisation)
            .Include(l => l.OrganisationSite)
            .Include(l => l.TrainingProvider)
            .Include(l => l.TradeTests)
            .AsQueryable();

        if (organisationId.HasValue && organisationId.Value > 0)
        {
            query = query.Where(l => l.OrganisationId == organisationId.Value);
        }

        if (!string.IsNullOrWhiteSpace(status))
        {
            query = query.Where(l => l.EnrolmentStatusCode == status || l.EnrolmentStatusId == status);
        }

        if (!string.IsNullOrWhiteSpace(programmeType))
        {
            query = query.Where(l => l.LearningProgrammeTypeCode == programmeType);
        }

        if (!string.IsNullOrWhiteSpace(search))
        {
            var s = search.Trim();
            query = query.Where(l =>
                (l.LearnerContractNumber != null && l.LearnerContractNumber.Contains(s)) ||
                (l.LearnershipId != null && l.LearnershipId.Contains(s)) ||
                (l.OfoCode != null && l.OfoCode.Contains(s)) ||
                (l.QualificationTitle != null && l.QualificationTitle.Contains(s)) ||
                (l.Person != null && (l.Person.FirstName.Contains(s) || l.Person.LastName.Contains(s) || l.Person.RsaIdNumber.Contains(s))) ||
                (l.Organisation != null && l.Organisation.CompanyName.Contains(s)));
        }

        return await query
            .OrderByDescending(l => l.Id)
            .ToListAsync();
    }

    public async Task<CompanyLearner?> GetLearnerByIdAsync(int id)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.CompanyLearners
            .Include(l => l.Person)
            .Include(l => l.Organisation)
            .Include(l => l.OrganisationSite)
            .Include(l => l.TrainingProvider)
            .Include(l => l.TradeTests).ThenInclude(t => t.AssessorPerson)
            .Include(l => l.TradeTests).ThenInclude(t => t.ModeratorPerson)
            .FirstOrDefaultAsync(l => l.Id == id);
    }

    public async Task<CompanyLearner> RegisterLearnerAsync(CompanyLearner learner, string currentUsername = "SYSTEM")
    {
        if (learner.PersonId <= 0)
        {
            throw new ArgumentException("A valid PersonId (learner) is required.");
        }

        if (learner.OrganisationId <= 0)
        {
            throw new ArgumentException("A valid OrganisationId (employer) is required.");
        }

        if (string.IsNullOrWhiteSpace(learner.QualificationTitle))
        {
            throw new ArgumentException("Qualification title is required.");
        }

        if (string.IsNullOrWhiteSpace(learner.LearnerContractNumber))
        {
            var prefix = (learner.LearningProgrammeTypeCode ?? "").StartsWith("App", StringComparison.OrdinalIgnoreCase) || learner.LearningProgrammeTypeCode == "01" ? "APP" : "LRN";
            learner.LearnerContractNumber = $"{prefix}-{DateTime.UtcNow.Year}-{learner.OrganisationId}-{Guid.NewGuid().ToString("N")[..4].ToUpper()}";
        }

        if (string.IsNullOrWhiteSpace(learner.EnrolmentStatusCode))
        {
            learner.EnrolmentStatusCode = "Registered";
        }

        if (string.IsNullOrWhiteSpace(learner.EnrolmentStatusId))
        {
            learner.EnrolmentStatusId = "01";
        }

        if (learner.EnrolmentStatusDate == null)
        {
            learner.EnrolmentStatusDate = DateTime.UtcNow;
        }

        using var db = await _contextFactory.CreateDbContextAsync();
        learner.CreatedAt = DateTime.UtcNow;
        learner.CreatedBy = currentUsername;

        db.CompanyLearners.Add(learner);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "CompanyLearner", learner.Id, "RegisterLearner", currentUsername, null, learner);
        await db.SaveChangesAsync();
        return learner;
    }

    public async Task<CompanyLearner> UpdateLearnerAsync(CompanyLearner learner, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.CompanyLearners.FindAsync(learner.Id);
        if (existing == null) throw new KeyNotFoundException($"CompanyLearner with ID {learner.Id} not found.");

        var beforeState = new
        {
            existing.LearnerContractNumber,
            existing.LearnershipId,
            existing.NonNqfInterventionCode,
            existing.PartOfId,
            existing.EnrolmentTypeId,
            existing.EnrolmentStatusId,
            existing.EnrolmentStatusDate,
            existing.EnrolmentStatusReasonId,
            existing.AssessorRegistrationNumber,
            existing.AssessorEtqaId,
            existing.PracticalProviderCode,
            existing.PracticalProviderEtqaId,
            existing.OfoCode,
            existing.EconomicStatusId,
            existing.UrbanRuralId,
            existing.CumulativeSpend,
            existing.CertificateNumber,
            existing.PriorQualificationId,
            existing.PriorQualificationAchievementDate,
            existing.InternshipStatusId,
            existing.FundingId,
            existing.OrganisationSiteId,
            existing.EnrolmentStatusCode,
            existing.QualificationTitle,
            existing.CompletionDate
        };

        existing.QualificationTitle = learner.QualificationTitle;
        existing.SaqaQualificationId = learner.SaqaQualificationId;
        existing.NqfLevel = learner.NqfLevel;
        existing.LearningProgrammeTypeCode = learner.LearningProgrammeTypeCode;
        existing.LearnershipId = learner.LearnershipId;
        existing.NonNqfInterventionCode = learner.NonNqfInterventionCode;
        existing.PartOfId = learner.PartOfId;
        existing.EnrolmentTypeId = learner.EnrolmentTypeId;
        existing.EnrolmentStatusId = learner.EnrolmentStatusId;
        existing.EnrolmentStatusDate = learner.EnrolmentStatusDate;
        existing.EnrolmentStatusReasonId = learner.EnrolmentStatusReasonId;
        existing.AssessorRegistrationNumber = learner.AssessorRegistrationNumber;
        existing.AssessorEtqaId = learner.AssessorEtqaId;
        existing.PracticalProviderCode = learner.PracticalProviderCode;
        existing.PracticalProviderEtqaId = learner.PracticalProviderEtqaId;
        existing.OfoCode = learner.OfoCode;
        existing.EconomicStatusId = learner.EconomicStatusId;
        existing.UrbanRuralId = learner.UrbanRuralId;
        existing.CumulativeSpend = learner.CumulativeSpend;
        existing.CertificateNumber = learner.CertificateNumber;
        existing.PriorQualificationId = learner.PriorQualificationId;
        existing.PriorQualificationAchievementDate = learner.PriorQualificationAchievementDate;
        existing.InternshipStatusId = learner.InternshipStatusId;
        existing.FundingTypeCode = learner.FundingTypeCode;
        existing.FundingId = learner.FundingId;
        existing.OrganisationSiteId = learner.OrganisationSiteId;
        existing.TrainingProviderId = learner.TrainingProviderId;
        existing.CommencementDate = learner.CommencementDate;
        existing.ExpectedCompletionDate = learner.ExpectedCompletionDate;
        existing.CompletionDate = learner.CompletionDate;
        existing.EnrolmentStatusCode = learner.EnrolmentStatusCode;
        existing.SetaRegion = learner.SetaRegion;
        existing.ChamberCode = learner.ChamberCode;
        existing.IsActive = learner.IsActive;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        _audit.LogAction(db, "CompanyLearner", existing.Id, "UpdateLearner", currentUsername, beforeState, existing);
        await db.SaveChangesAsync();
        return existing;
    }

    public async Task<CompanyLearner> SaveLearnerAsync(CompanyLearner learner, string currentUsername = "SYSTEM")
    {
        if (learner.Id == 0) return await RegisterLearnerAsync(learner, currentUsername);
        return await UpdateLearnerAsync(learner, currentUsername);
    }

    public async Task<bool> DeleteLearnerAsync(int id, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var learner = await db.CompanyLearners.Include(l => l.TradeTests).FirstOrDefaultAsync(l => l.Id == id);
        if (learner == null) return false;

        var beforeState = new { learner.Id, learner.LearnerContractNumber, learner.PersonId, learner.OrganisationId };
        db.CompanyLearners.Remove(learner);
        _audit.LogAction(db, "CompanyLearner", id, "DeleteLearner", currentUsername, beforeState, null);
        await db.SaveChangesAsync();

        return true;
    }

    public async Task<LearnerTradeTest> ScheduleTradeTestAsync(LearnerTradeTest tradeTest, string currentUsername = "SYSTEM")
    {
        if (tradeTest.CompanyLearnerId <= 0)
        {
            throw new ArgumentException("A valid CompanyLearnerId is required.");
        }

        if (string.IsNullOrWhiteSpace(tradeTest.TradeTitle))
        {
            throw new ArgumentException("Trade Title is required for Trade Test.");
        }

        if (string.IsNullOrWhiteSpace(tradeTest.TestCenterName))
        {
            tradeTest.TestCenterName = "MerSETA Accredited Trade Test Center";
        }

        if (string.IsNullOrWhiteSpace(tradeTest.ResultStatusCode))
        {
            tradeTest.ResultStatusCode = "Scheduled";
        }

        if (string.IsNullOrWhiteSpace(tradeTest.TradeTestResultId))
        {
            tradeTest.TradeTestResultId = "01";
        }

        if (string.IsNullOrWhiteSpace(tradeTest.TradeTestResultReasonId))
        {
            tradeTest.TradeTestResultReasonId = "01";
        }

        using var db = await _contextFactory.CreateDbContextAsync();
        tradeTest.CreatedAt = DateTime.UtcNow;
        tradeTest.CreatedBy = currentUsername;

        db.LearnerTradeTests.Add(tradeTest);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "LearnerTradeTest", tradeTest.Id, "ScheduleTradeTest", currentUsername, null, tradeTest);
        await db.SaveChangesAsync();
        return tradeTest;
    }

    public async Task<LearnerTradeTest> RecordTradeTestResultAsync(int tradeTestId, string resultStatusCode, string? certificateNumber, string? remarks, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var tradeTest = await db.LearnerTradeTests.Include(t => t.CompanyLearner).FirstOrDefaultAsync(t => t.Id == tradeTestId);
        if (tradeTest == null) throw new KeyNotFoundException($"LearnerTradeTest with ID {tradeTestId} not found.");

        var beforeState = new { tradeTest.ResultStatusCode, tradeTest.TradeTestResultId, tradeTest.SerialCertificateNumber };

        tradeTest.ResultStatusCode = resultStatusCode;
        tradeTest.Remarks = remarks;

        if (resultStatusCode.Equals("Competent", StringComparison.OrdinalIgnoreCase) || resultStatusCode == "01")
        {
            tradeTest.TradeTestResultId = "01";
            tradeTest.CertificateIssueDate = DateTime.UtcNow;
            if (string.IsNullOrWhiteSpace(certificateNumber))
            {
                tradeTest.SerialCertificateNumber = $"CERT-{DateTime.UtcNow.Year}-{tradeTest.CompanyLearnerId}-{Guid.NewGuid().ToString("N")[..5].ToUpper()}";
            }
            else
            {
                tradeTest.SerialCertificateNumber = certificateNumber;
            }

            if (tradeTest.CompanyLearner != null)
            {
                tradeTest.CompanyLearner.EnrolmentStatusCode = "Completed";
                tradeTest.CompanyLearner.EnrolmentStatusId = "02";
                tradeTest.CompanyLearner.EnrolmentStatusDate = DateTime.UtcNow;
                tradeTest.CompanyLearner.CompletionDate = DateTime.UtcNow;
            }
        }
        else
        {
            tradeTest.TradeTestResultId = "02";
        }

        tradeTest.ModifiedAt = DateTime.UtcNow;
        tradeTest.ModifiedBy = currentUsername;

        _audit.LogAction(db, "LearnerTradeTest", tradeTest.Id, "RecordTradeTestResult", currentUsername, beforeState, tradeTest);
        await db.SaveChangesAsync();
        return tradeTest;
    }

    public async Task<LearnerTradeTest?> GetTradeTestByIdAsync(int id)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.LearnerTradeTests
            .Include(t => t.CompanyLearner!).ThenInclude(l => l.Person)
            .Include(t => t.CompanyLearner!).ThenInclude(l => l.Organisation)
            .Include(t => t.TrainingProvider)
            .Include(t => t.AssessorPerson)
            .Include(t => t.ModeratorPerson)
            .FirstOrDefaultAsync(t => t.Id == id);
    }

    public async Task<List<LearnerTradeTest>> GetAllTradeTestsAsync(string? search = null, string? resultStatus = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.LearnerTradeTests
            .Include(t => t.CompanyLearner!).ThenInclude(l => l.Person)
            .Include(t => t.CompanyLearner!).ThenInclude(l => l.Organisation)
            .Include(t => t.TrainingProvider)
            .Include(t => t.AssessorPerson)
            .Include(t => t.ModeratorPerson)
            .AsQueryable();

        if (!string.IsNullOrWhiteSpace(resultStatus))
        {
            query = query.Where(t => t.ResultStatusCode == resultStatus || t.TradeTestResultId == resultStatus);
        }

        if (!string.IsNullOrWhiteSpace(search))
        {
            var s = search.Trim();
            query = query.Where(t =>
                t.TradeTitle.Contains(s) ||
                t.TestCenterName.Contains(s) ||
                (t.TradeTestCentreCode != null && t.TradeTestCentreCode.Contains(s)) ||
                (t.TradeCode != null && t.TradeCode.Contains(s)) ||
                (t.SerialCertificateNumber != null && t.SerialCertificateNumber.Contains(s)) ||
                (t.CompanyLearner != null && t.CompanyLearner.Person != null && (t.CompanyLearner.Person.FirstName.Contains(s) || t.CompanyLearner.Person.LastName.Contains(s))));
        }

        return await query
            .OrderByDescending(t => t.TradeTestDate)
            .ToListAsync();
    }

    public async Task<List<LearnerTradeTest>> GetTradeTestsForLearnerAsync(int companyLearnerId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.LearnerTradeTests
            .Where(t => t.CompanyLearnerId == companyLearnerId)
            .Include(t => t.TrainingProvider)
            .Include(t => t.AssessorPerson)
            .Include(t => t.ModeratorPerson)
            .OrderByDescending(t => t.TradeTestDate)
            .ToListAsync();
    }

    public async Task<bool> DeleteTradeTestAsync(int id, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var tradeTest = await db.LearnerTradeTests.FindAsync(id);
        if (tradeTest == null) return false;

        db.LearnerTradeTests.Remove(tradeTest);
        _audit.LogAction(db, "LearnerTradeTest", id, "DeleteTradeTest", currentUsername, null, null);
        await db.SaveChangesAsync();
        return true;
    }

    // 360-Degree Learner Relational Queries Implementation
    public async Task<List<LearnerEnrolmentDto>> GetLearnerEnrolmentsAsync(int personId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var enrolments = await db.CompanyLearners
            .Include(l => l.Organisation)
            .Include(l => l.TrainingProvider)
            .Where(l => l.PersonId == personId)
            .OrderByDescending(l => l.Id)
            .ToListAsync();

        return enrolments.Select(l => new LearnerEnrolmentDto(
            l.Id,
            l.LearnerContractNumber,
            l.LearningProgrammeTypeCode,
            GetProgrammeTypeName(l.LearningProgrammeTypeCode),
            l.QualificationTitle,
            l.NqfLevel,
            l.SaqaQualificationId?.ToString(),
            l.OfoCode,
            l.EnrolmentStatusId,
            l.EnrolmentStatusCode ?? "Registered",
            l.RegistrationDate,
            l.CommencementDate,
            l.CompletionDate,
            l.Organisation?.CompanyName,
            l.OrganisationId > 0 ? l.OrganisationId : null,
            l.TrainingProvider?.ProviderName,
            l.TrainingProviderId
        )).ToList();
    }

    public async Task<List<LearnerEmployerLinkDto>> GetLearnerEmployersAsync(int personId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var agreements = await db.CompanyLearners
            .Include(l => l.Organisation!)
                .ThenInclude(o => o.PrimaryContactPerson)
            .Include(l => l.OrganisationSite!)
                .ThenInclude(s => s.PrimaryContactPerson)
            .Where(l => l.PersonId == personId && l.Organisation != null)
            .OrderByDescending(l => l.Id)
            .ToListAsync();

        var list = new List<LearnerEmployerLinkDto>();
        foreach (var a in agreements)
        {
            if (a.Organisation == null) continue;
            var contact = a.OrganisationSite?.PrimaryContactPerson ?? a.Organisation.PrimaryContactPerson;
            list.Add(new LearnerEmployerLinkDto(
                a.Organisation.Id,
                a.Organisation.CompanyName,
                a.Organisation.SdlNumber,
                a.OrganisationSite?.SiteName ?? "Head Office / Plant",
                a.OrganisationSite?.PhysicalAddress ?? a.Organisation.PhysicalAddress,
                contact != null ? $"{contact.FirstName} {contact.LastName}".Trim() : null,
                contact?.Email,
                contact?.PhoneNumber ?? contact?.CellNumber,
                "Host & Sponsoring Employer",
                a.CommencementDate ?? a.RegistrationDate
            ));
        }
        return list;
    }

    public async Task<List<LearnerSdpLinkDto>> GetLearnerProvidersAsync(int personId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var agreements = await db.CompanyLearners
            .Include(l => l.TrainingProvider!)
                .ThenInclude(p => p.PrimaryContactPerson)
            .Where(l => l.PersonId == personId && l.TrainingProvider != null)
            .OrderByDescending(l => l.Id)
            .ToListAsync();

        var list = new List<LearnerSdpLinkDto>();
        foreach (var a in agreements)
        {
            if (a.TrainingProvider == null) continue;
            var contact = a.TrainingProvider.PrimaryContactPerson;
            list.Add(new LearnerSdpLinkDto(
                a.TrainingProvider.Id,
                a.TrainingProvider.ProviderName,
                a.TrainingProvider.AccreditationNumber,
                contact != null ? $"{contact.FirstName} {contact.LastName}".Trim() : null,
                contact?.Email,
                contact?.PhoneNumber ?? contact?.CellNumber,
                a.AssessorRegistrationNumber != null ? $"Assessor Reg #{a.AssessorRegistrationNumber}" : "Unassigned",
                a.AssessorRegistrationNumber,
                a.EnrolmentTypeId == "01" ? "Contact Delivery" : "Workplace Based"
            ));
        }
        return list;
    }

    public async Task<List<LearnerAssessmentDto>> GetLearnerAssessmentsAsync(int personId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var assessments = await db.LearnerAssessments
            .Include(a => a.EtqaAssessor!)
                .ThenInclude(ea => ea.Person)
            .Where(a => a.PersonId == personId)
            .OrderByDescending(a => a.AssessmentDate)
            .ToListAsync();

        return assessments.Select(a => new LearnerAssessmentDto(
            a.Id,
            a.CompanyLearnerId ?? 0,
            a.UnitStandardId?.ToString() ?? "US-" + a.Id,
            a.UnitStandardTitle ?? a.QualificationTitle,
            a.AssessmentDate,
            a.EnrolmentTypeId,
            a.EnrolmentStatusId == "02" ? "Competent" : "NotYetCompetent",
            a.EtqaAssessor?.Person != null ? $"{a.EtqaAssessor.Person.FirstName} {a.EtqaAssessor.Person.LastName}".Trim() : null,
            a.AssessorRegistrationNumber,
            a.CertificateNumber
        )).ToList();
    }

    public async Task<List<LearnerStipendDto>> GetLearnerStipendsAsync(int personId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var agreements = await db.CompanyLearners
            .Where(l => l.PersonId == personId)
            .OrderByDescending(l => l.Id)
            .ToListAsync();

        return agreements.Select(a => new LearnerStipendDto(
            a.Id,
            a.FundingTypeCode ?? "DiscretionaryGrant",
            a.CumulativeSpend,
            a.QualificationTitle + " Stipend Pool",
            "MOA-" + a.OrganisationId + "-DG",
            a.FundingTypeCode == "DiscretionaryGrant" ? 4500.00m : 0.00m,
            a.IsActive ? "Active Disbursement" : "Completed"
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
