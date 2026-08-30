using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public interface IAqpPartnerService
{
    Task<List<AqpPartner>> GetAllPartnersAsync();
    Task<AqpPartner?> GetPartnerByIdAsync(int id);
    Task<AqpPartner> CreatePartnerAsync(string name, string code, string accreditationNum, string qaBody, int? contactPersonId, string? email, string? phone, string? address, string province, DateTime start, DateTime end, string actor);
    Task<AqpPartner?> UpdatePartnerAsync(int id, string name, string accreditationNum, string? email, string? phone, string status, string actor);

    Task<List<AqpQualificationScope>> GetScopesByPartnerIdAsync(int partnerId);
    Task<AqpQualificationScope> AddScopeAsync(int partnerId, string title, string? saqaId, int nqfLevel, string? curriculumCode, string model, string actor);
    Task<bool> RemoveScopeAsync(int scopeId, string actor);

    Task<List<AqpLearnerAssessment>> GetAssessmentsByPartnerIdAsync(int partnerId);
    Task<AqpLearnerAssessment> ScheduleAssessmentAsync(int partnerId, int? learnerId, int? personId, string examSession, DateTime date, string center, string actor);
    Task<AqpLearnerAssessment?> RecordAssessmentResultsAsync(int assessmentId, decimal theoryScore, decimal practicalScore, string resultStatus, string actor);
    Task<AqpLearnerAssessment?> EndorseAndCertifyAssessmentAsync(int assessmentId, string certNumber, string? comments, string actor);
}

public class AqpPartnerService : IAqpPartnerService
{
    private readonly INsdmsDbContextFactory _dbFactory;
    private readonly AuditService _auditService;

    public AqpPartnerService(INsdmsDbContextFactory dbFactory, AuditService auditService)
    {
        _dbFactory = dbFactory;
        _auditService = auditService;
    }

    public async Task<List<AqpPartner>> GetAllPartnersAsync()
    {
        await using var db = await _dbFactory.CreateDbContextAsync();
        return await db.AqpPartners
            .AsNoTracking()
            .Include(p => p.ContactPerson)
            .Include(p => p.Scopes)
            .OrderBy(p => p.AqpName)
            .ToListAsync();
    }

    public async Task<AqpPartner?> GetPartnerByIdAsync(int id)
    {
        await using var db = await _dbFactory.CreateDbContextAsync();
        return await db.AqpPartners
            .AsNoTracking()
            .Include(p => p.ContactPerson)
            .Include(p => p.Scopes.Where(s => s.IsActive))
            .Include(p => p.Assessments)
                .ThenInclude(a => a.Person)
            .Include(p => p.Assessments)
                .ThenInclude(a => a.CompanyLearner)
            .FirstOrDefaultAsync(p => p.Id == id);
    }

    public async Task<AqpPartner> CreatePartnerAsync(string name, string code, string accreditationNum, string qaBody, int? contactPersonId, string? email, string? phone, string? address, string province, DateTime start, DateTime end, string actor)
    {
        await using var db = await _dbFactory.CreateDbContextAsync();
        var partner = new AqpPartner
        {
            AqpName = name,
            AqpCode = code,
            AccreditationNumber = accreditationNum,
            QualityAssuranceBody = qaBody,
            ContactPersonId = contactPersonId,
            Email = email,
            PhoneNumber = phone,
            PhysicalAddress = address,
            ProvinceCode = province,
            AccreditationStartDate = start,
            AccreditationEndDate = end,
            StatusCode = "Active",
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = actor,
            ModifiedAt = DateTime.UtcNow,
            ModifiedBy = actor
        };

        db.AqpPartners.Add(partner);
        await db.SaveChangesAsync();

        _auditService.LogAction(db, "AqpPartner", partner.Id, "CreateAqpPartner", actor, null, partner);
        await db.SaveChangesAsync();

        return partner;
    }

    public async Task<AqpPartner?> UpdatePartnerAsync(int id, string name, string accreditationNum, string? email, string? phone, string status, string actor)
    {
        await using var db = await _dbFactory.CreateDbContextAsync();
        var partner = await db.AqpPartners.FindAsync(id);
        if (partner == null) return null;

        var oldVal = new { partner.AqpName, partner.AccreditationNumber, partner.StatusCode };
        partner.AqpName = name;
        partner.AccreditationNumber = accreditationNum;
        partner.Email = email;
        partner.PhoneNumber = phone;
        partner.StatusCode = status;
        partner.ModifiedAt = DateTime.UtcNow;
        partner.ModifiedBy = actor;

        _auditService.LogAction(db, "AqpPartner", partner.Id, "UpdateAqpPartner", actor, oldVal, partner);
        await db.SaveChangesAsync();

        return partner;
    }

    public async Task<List<AqpQualificationScope>> GetScopesByPartnerIdAsync(int partnerId)
    {
        await using var db = await _dbFactory.CreateDbContextAsync();
        return await db.AqpQualificationScopes
            .AsNoTracking()
            .Where(s => s.AqpPartnerId == partnerId && s.IsActive)
            .OrderBy(s => s.QualificationTitle)
            .ToListAsync();
    }

    public async Task<AqpQualificationScope> AddScopeAsync(int partnerId, string title, string? saqaId, int nqfLevel, string? curriculumCode, string model, string actor)
    {
        await using var db = await _dbFactory.CreateDbContextAsync();
        var scope = new AqpQualificationScope
        {
            AqpPartnerId = partnerId,
            QualificationTitle = title,
            SaqaQualificationId = saqaId,
            NqfLevel = nqfLevel,
            CurriculumCode = curriculumCode,
            AssessmentModel = model,
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = actor,
            ModifiedAt = DateTime.UtcNow,
            ModifiedBy = actor
        };

        db.AqpQualificationScopes.Add(scope);
        await db.SaveChangesAsync();

        _auditService.LogAction(db, "AqpQualificationScope", scope.Id, "AddAqpScope", actor, null, scope);
        await db.SaveChangesAsync();

        return scope;
    }

    public async Task<bool> RemoveScopeAsync(int scopeId, string actor)
    {
        await using var db = await _dbFactory.CreateDbContextAsync();
        var scope = await db.AqpQualificationScopes.FindAsync(scopeId);
        if (scope == null) return false;

        scope.IsActive = false;
        scope.ModifiedAt = DateTime.UtcNow;
        scope.ModifiedBy = actor;

        _auditService.LogAction(db, "AqpQualificationScope", scope.Id, "RemoveAqpScope", actor, new { IsActive = true }, new { IsActive = false });
        await db.SaveChangesAsync();
        return true;
    }

    public async Task<List<AqpLearnerAssessment>> GetAssessmentsByPartnerIdAsync(int partnerId)
    {
        await using var db = await _dbFactory.CreateDbContextAsync();
        return await db.AqpLearnerAssessments
            .AsNoTracking()
            .Include(a => a.Person)
            .Include(a => a.CompanyLearner)
            .Where(a => a.AqpPartnerId == partnerId)
            .OrderByDescending(a => a.AssessmentDate)
            .ToListAsync();
    }

    public async Task<AqpLearnerAssessment> ScheduleAssessmentAsync(int partnerId, int? learnerId, int? personId, string examSession, DateTime date, string center, string actor)
    {
        await using var db = await _dbFactory.CreateDbContextAsync();
        var assessmentNum = $"EISA-{DateTime.UtcNow.Year}-{Guid.NewGuid().ToString("N")[..6].ToUpper()}";
        var assessment = new AqpLearnerAssessment
        {
            AqpPartnerId = partnerId,
            CompanyLearnerId = learnerId,
            PersonId = personId,
            AssessmentNumber = assessmentNum,
            EisaExamSession = examSession,
            AssessmentDate = date,
            AssessmentCenter = center,
            ResultStatusCode = "Pending",
            ModerationStatusCode = "Pending",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = actor,
            ModifiedAt = DateTime.UtcNow,
            ModifiedBy = actor
        };

        db.AqpLearnerAssessments.Add(assessment);
        await db.SaveChangesAsync();

        _auditService.LogAction(db, "AqpLearnerAssessment", assessment.Id, "ScheduleEisaAssessment", actor, null, assessment);
        await db.SaveChangesAsync();

        return assessment;
    }

    public async Task<AqpLearnerAssessment?> RecordAssessmentResultsAsync(int assessmentId, decimal theoryScore, decimal practicalScore, string resultStatus, string actor)
    {
        await using var db = await _dbFactory.CreateDbContextAsync();
        var assessment = await db.AqpLearnerAssessments.FindAsync(assessmentId);
        if (assessment == null) return null;

        var oldVal = new { assessment.TheoryScorePercentage, assessment.PracticalScorePercentage, assessment.ResultStatusCode };
        assessment.TheoryScorePercentage = theoryScore;
        assessment.PracticalScorePercentage = practicalScore;
        assessment.FinalOverallPercentage = Math.Round((theoryScore + practicalScore) / 2.0m, 2);
        assessment.ResultStatusCode = resultStatus;
        assessment.ModifiedAt = DateTime.UtcNow;
        assessment.ModifiedBy = actor;

        _auditService.LogAction(db, "AqpLearnerAssessment", assessment.Id, "RecordEisaScores", actor, oldVal, assessment);
        await db.SaveChangesAsync();

        return assessment;
    }

    public async Task<AqpLearnerAssessment?> EndorseAndCertifyAssessmentAsync(int assessmentId, string certNumber, string? comments, string actor)
    {
        await using var db = await _dbFactory.CreateDbContextAsync();
        var assessment = await db.AqpLearnerAssessments.FindAsync(assessmentId);
        if (assessment == null) return null;

        var oldVal = new { assessment.ModerationStatusCode, assessment.CertificateNumber };
        assessment.ModerationStatusCode = "Endorsed";
        assessment.CertificateNumber = certNumber;
        assessment.CertificateIssuedDate = DateTime.UtcNow;
        assessment.ModeratorComments = comments;
        assessment.ModifiedAt = DateTime.UtcNow;
        assessment.ModifiedBy = actor;

        _auditService.LogAction(db, "AqpLearnerAssessment", assessment.Id, "EndorseAndCertifyEisa", actor, oldVal, assessment);
        await db.SaveChangesAsync();

        return assessment;
    }
}
