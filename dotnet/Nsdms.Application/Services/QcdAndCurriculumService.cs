using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class QcdAndCurriculumService : IQcdAndCurriculumService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly AuditService _audit;

    public QcdAndCurriculumService(INsdmsDbContextFactory contextFactory, AuditService audit)
    {
        _contextFactory = contextFactory;
        _audit = audit;
    }

    public async Task<QualificationsCurriculumDevelopment> CreateQcdApplicationAsync(
        string qualificationTitle,
        string ofoCode,
        int nqfLevel,
        int totalCreditsRequired,
        string developmentTypeCode = "NewDevelopment",
        string? purpose = null,
        string? demandJustification = null,
        int? organisationId = null,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var qcd = new QualificationsCurriculumDevelopment
        {
            ApplicationNumber = $"QCD-{DateTime.UtcNow.Year}-{Guid.NewGuid().ToString("N")[..6].ToUpper()}",
            QualificationTitle = qualificationTitle,
            OfoCode = ofoCode,
            NqfLevel = nqfLevel,
            TotalCreditsRequired = totalCreditsRequired,
            DevelopmentTypeCode = developmentTypeCode,
            PurposeOfQualification = purpose,
            IndustryDemandJustification = demandJustification,
            OrganisationId = organisationId,
            NationalDevelopmentPlanChecked = true,
            NewGrowthPlanChecked = true,
            IndustrialPolicyActionPlanChecked = true,
            DevelopmentQualityPartner = "merSETA DQP",
            AssessmentQualityPartner = "merSETA AQP",
            StatusCode = "Draft",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        db.QualificationsCurriculumDevelopments.Add(qcd);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "QualificationsCurriculumDevelopment", qcd.Id, "CreateQcdApplication", currentUsername, null, qcd);
        await db.SaveChangesAsync();

        return qcd;
    }

    public async Task<QualificationsCurriculumDevelopment> AddWorkingGroupMemberAsync(
        int qcdId,
        string memberName,
        string stakeholderRoleTitle,
        string organisationRepresented,
        string email,
        string phone,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var qcd = await db.QualificationsCurriculumDevelopments.FirstOrDefaultAsync(q => q.Id == qcdId);
        if (qcd == null)
        {
            throw new KeyNotFoundException($"QCD Application with ID {qcdId} not found.");
        }

        var member = new CurriculumWorkingGroupMember
        {
            QualificationsCurriculumDevelopmentId = qcdId,
            MemberName = memberName,
            StakeholderRoleTitle = stakeholderRoleTitle,
            OrganisationRepresented = organisationRepresented,
            EmailAddress = email,
            PhoneNumber = phone,
            IsConfirmedAttendee = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        db.CurriculumWorkingGroupMembers.Add(member);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "QualificationsCurriculumDevelopment", qcd.Id, "AddWorkingGroupMember", currentUsername, null, member);
        await db.SaveChangesAsync();

        return (await GetQcdByIdAsync(qcdId))!;
    }

    public async Task<QualificationsCurriculumDevelopment> AddSkillsRegistrationAsync(
        int qcdId,
        string skillsCode,
        string skillsTitle,
        int credits,
        int nqfLevel,
        string unitStandardsJson,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var qcd = await db.QualificationsCurriculumDevelopments.FirstOrDefaultAsync(q => q.Id == qcdId);
        if (qcd == null)
        {
            throw new KeyNotFoundException($"QCD Application with ID {qcdId} not found.");
        }

        var skills = new SkillsRegistration
        {
            QualificationsCurriculumDevelopmentId = qcdId,
            SkillsProgrammeCode = skillsCode,
            SkillsProgrammeTitle = skillsTitle,
            Credits = credits,
            NqfLevel = nqfLevel,
            UnitStandardsIncludedJson = unitStandardsJson,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        db.SkillsRegistrations.Add(skills);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "QualificationsCurriculumDevelopment", qcd.Id, "AddSkillsRegistration", currentUsername, null, skills);
        await db.SaveChangesAsync();

        return (await GetQcdByIdAsync(qcdId))!;
    }

    public async Task<QualificationsCurriculumDevelopment> ConveneWorkingGroupAndOpenPublicCommentAsync(
        int qcdId,
        DateTime publicCommentClosingDate,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var qcd = await db.QualificationsCurriculumDevelopments.FirstOrDefaultAsync(q => q.Id == qcdId);
        if (qcd == null)
        {
            throw new KeyNotFoundException($"QCD Application with ID {qcdId} not found.");
        }

        var before = new { qcd.StatusCode, qcd.WorkingGroupConvenedDate };
        qcd.WorkingGroupConvenedDate = DateTime.UtcNow;
        qcd.PublicCommentClosingDate = publicCommentClosingDate;
        qcd.StatusCode = "PublicCommentOpen";
        qcd.ModifiedAt = DateTime.UtcNow;
        qcd.ModifiedBy = currentUsername;

        _audit.LogAction(db, "QualificationsCurriculumDevelopment", qcd.Id, "ConveneWorkingGroupAndOpenPublicComment", currentUsername, before, qcd);
        await db.SaveChangesAsync();

        return qcd;
    }

    public async Task<QualificationsCurriculumDevelopment> SubmitToQctoForSaqaRegistrationAsync(
        int qcdId,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var qcd = await db.QualificationsCurriculumDevelopments.FirstOrDefaultAsync(q => q.Id == qcdId);
        if (qcd == null)
        {
            throw new KeyNotFoundException($"QCD Application with ID {qcdId} not found.");
        }

        var before = new { qcd.StatusCode, qcd.SaqaSubmissionDate };
        qcd.SaqaSubmissionDate = DateTime.UtcNow;
        qcd.StatusCode = "SubmittedToQcto";
        qcd.ModifiedAt = DateTime.UtcNow;
        qcd.ModifiedBy = currentUsername;

        _audit.LogAction(db, "QualificationsCurriculumDevelopment", qcd.Id, "SubmitToQctoForSaqaRegistration", currentUsername, before, qcd);
        await db.SaveChangesAsync();

        return qcd;
    }

    public async Task<QualificationsCurriculumDevelopment> RecordSaqaRegistrationApprovalAsync(
        int qcdId,
        string saqaRegistrationNumber,
        DateTime registrationDate,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var qcd = await db.QualificationsCurriculumDevelopments.FirstOrDefaultAsync(q => q.Id == qcdId);
        if (qcd == null)
        {
            throw new KeyNotFoundException($"QCD Application with ID {qcdId} not found.");
        }

        var before = new { qcd.StatusCode, qcd.SaqaRegistrationNumber };
        qcd.SaqaRegistrationNumber = saqaRegistrationNumber;
        qcd.SaqaRegistrationDate = registrationDate;
        qcd.StatusCode = "ApprovedBySaqa";
        qcd.ModifiedAt = DateTime.UtcNow;
        qcd.ModifiedBy = currentUsername;

        _audit.LogAction(db, "QualificationsCurriculumDevelopment", qcd.Id, "RecordSaqaRegistrationApproval", currentUsername, before, qcd);
        await db.SaveChangesAsync();

        return qcd;
    }

    public async Task<QualificationsCurriculumDevelopment?> GetQcdByIdAsync(int id)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.QualificationsCurriculumDevelopments
            .Include(q => q.Organisation)
            .Include(q => q.WorkingGroupMembers)
            .Include(q => q.SkillsRegistrations)
            .FirstOrDefaultAsync(q => q.Id == id);
    }

    public async Task<List<QualificationsCurriculumDevelopment>> GetQcdApplicationsAsync(string? statusCode = null, string? title = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.QualificationsCurriculumDevelopments
            .Include(q => q.Organisation)
            .AsQueryable();

        if (!string.IsNullOrWhiteSpace(statusCode))
        {
            query = query.Where(q => q.StatusCode == statusCode);
        }

        if (!string.IsNullOrWhiteSpace(title))
        {
            query = query.Where(q => q.QualificationTitle.Contains(title));
        }

        return await query.OrderByDescending(q => q.CreatedAt).ToListAsync();
    }
}
