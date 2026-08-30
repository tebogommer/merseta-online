using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class WspSurveyAndAqpPartnerTests
{
    private readonly INsdmsDbContextFactory _factory;
    private readonly AuditService _auditService;

    public WspSurveyAndAqpPartnerTests()
    {
        var dbName = $"Nsdms_SurveyAqpTests_{Guid.NewGuid()}";
        _factory = new TestDbContextFactory(dbName);
        _auditService = new AuditService(_factory);

        // Seed test Organisation, WSP, Person, and CompanyLearner
        using var db = (NsdmsDbContext)_factory.CreateDbContext();
        var org = new Organisation
        {
            Id = 1,
            CompanyName = "Engineering Innovations Ltd",
            LevyNumber = "L123456789",
            SicCode = "SIC-2026",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "TEST"
        };
        db.Organisations.Add(org);

        var wsp = new WspSubmission
        {
            Id = 10,
            OrganisationId = 1,
            FinYear = 2026,
            ReferenceNumber = "WSP-2026-ENG-01",
            StatusCode = "DRAFT",
            PlannedTrainingBudget = 500000m,
            EmployeeCount = 120,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "TEST"
        };
        db.WspSubmissions.Add(wsp);

        var person = new Person
        {
            Id = 50,
            FirstName = "Kagiso",
            LastName = "Modise",
            RsaIdNumber = "9201015800085",
            EmailAddress = "kagiso.modise@example.com",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "TEST"
        };
        db.People.Add(person);

        var learner = new CompanyLearner
        {
            Id = 100,
            PersonId = 50,
            OrganisationId = 1,
            LearnerContractNumber = "CON-2026-001",
            QualificationTitle = "Occupational Certificate: Mechanical Fitter",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "TEST"
        };
        db.CompanyLearners.Add(learner);

        db.SaveChanges();
    }

    [Fact]
    public async Task AddSkillsGap_ShouldPersist_AndWriteAuditLog()
    {
        var sut = new WspSurveyService(_factory, _auditService);

        var gap = await sut.AddSkillsGapAsync(
            wspId: 10,
            occupationTitle: "CNC Precision Machinist",
            ofoCode: "671202",
            description: "Scarcity of multi-axis CNC programmers",
            cause: "Introduction of robotic milling cells",
            intervention: "5-axis CAM Apprenticeship Programme",
            priority: "High",
            targetLearners: 8,
            estimatedBudget: 240000m,
            actor: "WspOfficer"
        );

        Assert.NotNull(gap);
        Assert.True(gap.Id > 0);
        Assert.Equal("CNC Precision Machinist", gap.OccupationTitle);
        Assert.Equal(8, gap.TargetLearnerCount);
        Assert.Equal(240000m, gap.EstimatedBudget);

        var gaps = await sut.GetSkillsGapsAsync(10);
        Assert.Single(gaps);

        await using var db = await _factory.CreateDbContextAsync();
        var audit = await db.AuditLogs
            .FirstOrDefaultAsync(a => a.EntityName == "WspStrategicSkillsGap" && a.RecordId == gap.Id && a.ActionName == "CreateSkillsGap");

        Assert.NotNull(audit);
        Assert.Equal("WspOfficer", audit.Actor);
    }

    [Fact]
    public async Task InitializeStandardImpactQuestions_ShouldPopulateSurveys()
    {
        var sut = new WspSurveyService(_factory, _auditService);

        var success = await sut.InitializeStandardImpactQuestionsAsync(10, "SkillsPlanner");
        Assert.True(success);

        var surveys = await sut.GetTrainingImpactSurveysAsync(10);
        Assert.Equal(5, surveys.Count);
        Assert.Contains(surveys, s => s.SurveyCategory == "Productivity");
        Assert.Contains(surveys, s => s.SurveyCategory == "Innovation & 4IR");

        // Updating a score
        var prodSurvey = surveys.First(s => s.SurveyCategory == "Productivity");
        var updated = await sut.SaveTrainingImpactSurveyAsync(10, prodSurvey.SurveyCategory, prodSurvey.QuestionText, 5, "Productivity increased by 18%", null, "SkillsPlanner");
        Assert.Equal(5, updated.RatingScore);
        Assert.Equal("Productivity increased by 18%", updated.QualitativeImpactNotes);
    }

    [Fact]
    public async Task AddStrategicPriority_ShouldPersist_AndSupportDelete()
    {
        var sut = new WspSurveyService(_factory, _auditService);

        var priority = await sut.AddStrategicPriorityAsync(
            wspId: 10,
            priorityCode: "4IR_DIGITAL",
            objective: "Digitise maintenance operations with IoT telemetry",
            alignment: "NSDP 2030 Priority 4 - Digital Transformation",
            budget: 150000m,
            alignedNsdp: true,
            actor: "Manager"
        );

        Assert.NotNull(priority);
        Assert.True(priority.IsAlignedWithNsdp);

        var list = await sut.GetStrategicPrioritiesAsync(10);
        Assert.Single(list);

        var deleted = await sut.DeleteStrategicPriorityAsync(priority.Id, "Manager");
        Assert.True(deleted);

        var remaining = await sut.GetStrategicPrioritiesAsync(10);
        Assert.Empty(remaining);
    }

    [Fact]
    public async Task AqpPartner_FullLifecycle_ShouldCreate_AddScope_ScheduleAndCertify()
    {
        var sut = new AqpPartnerService(_factory, _auditService);

        // 1. Create Partner
        var partner = await sut.CreatePartnerAsync(
            name: "Automotive Assessment Quality Partner",
            code: "AQP-AUTO-01",
            accreditationNum: "QCTO-AQP-2026-9999",
            qaBody: "QCTO",
            contactPersonId: 50,
            email: "aqp@auto.co.za",
            phone: "+27 11 555 0199",
            address: "12 Motor Way, Rosslyn",
            province: "GP",
            start: DateTime.Today,
            end: DateTime.Today.AddYears(5),
            actor: "EtqaAdmin"
        );

        Assert.NotNull(partner);
        Assert.True(partner.Id > 0);
        Assert.Equal("Active", partner.StatusCode);

        // 2. Add Scope
        var scope = await sut.AddScopeAsync(
            partnerId: partner.Id,
            title: "Occupational Certificate: Automotive Electrician",
            saqaId: "SAQA-101452",
            nqfLevel: 4,
            curriculumCode: "672101",
            model: "EISA",
            actor: "EtqaAdmin"
        );

        Assert.NotNull(scope);
        var scopes = await sut.GetScopesByPartnerIdAsync(partner.Id);
        Assert.Single(scopes);

        // 3. Schedule EISA Assessment
        var assessment = await sut.ScheduleAssessmentAsync(
            partnerId: partner.Id,
            learnerId: 100,
            personId: 50,
            examSession: "2026-OCT-EISA-AUTO",
            date: DateTime.Today.AddDays(7),
            center: "Rosslyn Auto Testing Center",
            actor: "ExamOfficer"
        );

        Assert.NotNull(assessment);
        Assert.StartsWith("EISA-", assessment.AssessmentNumber);
        Assert.Equal("Pending", assessment.ResultStatusCode);

        // 4. Record Scores
        var scored = await sut.RecordAssessmentResultsAsync(
            assessmentId: assessment.Id,
            theoryScore: 84.0m,
            practicalScore: 88.0m,
            resultStatus: "Competent",
            actor: "Assessor"
        );

        Assert.NotNull(scored);
        Assert.Equal(86.0m, scored.FinalOverallPercentage);
        Assert.Equal("Competent", scored.ResultStatusCode);

        // 5. Endorse and Issue Certificate
        var certified = await sut.EndorseAndCertifyAssessmentAsync(
            assessmentId: assessment.Id,
            certNumber: "QCTO-CERT-2026-AUTO-778",
            comments: "All rubric competencies achieved with distinction",
            actor: "QctoModerator"
        );

        Assert.NotNull(certified);
        Assert.Equal("Endorsed", certified.ModerationStatusCode);
        Assert.Equal("QCTO-CERT-2026-AUTO-778", certified.CertificateNumber);

        // 6. Verify Double-Write Audit Trail
        await using var db = await _factory.CreateDbContextAsync();
        var audits = await db.AuditLogs
            .Where(a => a.EntityName == "AqpLearnerAssessment" && a.RecordId == assessment.Id)
            .ToListAsync();

        Assert.Contains(audits, a => a.ActionName == "ScheduleEisaAssessment");
        Assert.Contains(audits, a => a.ActionName == "RecordEisaScores");
        Assert.Contains(audits, a => a.ActionName == "EndorseAndCertifyEisa");
    }
}

