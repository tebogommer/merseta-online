using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class LearnerServiceTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, LearnerService service) CreateTestContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var service = new LearnerService(factory, audit);
        return (factory, db, audit, service);
    }

    [Fact]
    public async Task RegisterLearnerAsync_ValidLearner_CreatesContractAndAudits()
    {
        var (factory, db, audit, service) = CreateTestContext();

        var person = new Person { FirstName = "Lunga", LastName = "Dlamini", RsaIdNumber = "0309190123084" };
        var org = new Organisation { CompanyName = "Toyota SA", SdlNumber = "L700100200" };
        db.People.Add(person);
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var learner = new CompanyLearner
        {
            PersonId = person.Id,
            OrganisationId = org.Id,
            QualificationTitle = "Automotive Motor Mechanic",
            LearningProgrammeTypeCode = "Apprenticeship",
            FundingTypeCode = "DiscretionaryGrant"
        };

        var result = await service.RegisterLearnerAsync(learner, "TESTUSER");

        Assert.True(result.Id > 0);
        Assert.StartsWith("APP-", result.LearnerContractNumber);
        Assert.Equal("Registered", result.StatusCode);

        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "CompanyLearner" && a.RecordId == result.Id);
        Assert.NotNull(auditLog);
        Assert.Equal("RegisterLearner", auditLog.ActionName);
    }

    [Fact]
    public async Task ScheduleTradeTestAsync_ValidTest_SchedulesAndAudits()
    {
        var (factory, db, audit, service) = CreateTestContext();

        var person = new Person { FirstName = "Lunga", LastName = "Dlamini", RsaIdNumber = "0309190123084" };
        var org = new Organisation { CompanyName = "Toyota SA", SdlNumber = "L700100200" };
        db.People.Add(person);
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var learner = await service.RegisterLearnerAsync(new CompanyLearner
        {
            PersonId = person.Id,
            OrganisationId = org.Id,
            QualificationTitle = "Automotive Motor Mechanic"
        });

        var tradeTest = new LearnerTradeTest
        {
            CompanyLearnerId = learner.Id,
            TestCenterName = "INDLELA Olifantsfontein",
            TradeTitle = "Motor Mechanic",
            TradeTestDate = DateTime.Today.AddDays(14),
            AttemptNumber = 1
        };

        var result = await service.ScheduleTradeTestAsync(tradeTest, "TESTUSER");

        Assert.True(result.Id > 0);
        Assert.Equal("Scheduled", result.ResultStatusCode);

        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "LearnerTradeTest" && a.ActionName == "ScheduleTradeTest");
        Assert.NotNull(auditLog);
    }

    [Fact]
    public async Task RecordTradeTestResultAsync_Competent_UpdatesAndAudits()
    {
        var (factory, db, audit, service) = CreateTestContext();

        var person = new Person { FirstName = "Lunga", LastName = "Dlamini", RsaIdNumber = "0309190123084" };
        var org = new Organisation { CompanyName = "Toyota SA", SdlNumber = "L700100200" };
        db.People.Add(person);
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var learner = await service.RegisterLearnerAsync(new CompanyLearner
        {
            PersonId = person.Id,
            OrganisationId = org.Id,
            QualificationTitle = "Automotive Motor Mechanic"
        });

        var tradeTest = await service.ScheduleTradeTestAsync(new LearnerTradeTest
        {
            CompanyLearnerId = learner.Id,
            TestCenterName = "INDLELA",
            TradeTitle = "Motor Mechanic"
        });

        var result = await service.RecordTradeTestResultAsync(tradeTest.Id, "Competent", "CERT-2026-9999", "Passed practical section", "TESTUSER");

        Assert.Equal("Competent", result.ResultStatusCode);
        Assert.Equal("CERT-2026-9999", result.SerialCertificateNumber);
        Assert.NotNull(result.CertificateIssueDate);
    }

    #region 360-Degree Learner Relational Tests

    [Fact]
    public async Task GetLearnerEnrolmentsAsync_ReturnsAllAgreements()
    {
        var (factory, db, audit, service) = CreateTestContext();

        var person = new Person { FirstName = "Nandi", LastName = "Zulu", RsaIdNumber = "0102030405086" };
        var org = new Organisation { CompanyName = "Bell Equipment", SdlNumber = "L800100200" };
        db.People.Add(person);
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        db.CompanyLearners.AddRange(
            new CompanyLearner { PersonId = person.Id, OrganisationId = org.Id, QualificationTitle = "Welder", LearningProgrammeTypeCode = "01" },
            new CompanyLearner { PersonId = person.Id, OrganisationId = org.Id, QualificationTitle = "Safety Representative", LearningProgrammeTypeCode = "03" }
        );
        await db.SaveChangesAsync();

        var result = await service.GetLearnerEnrolmentsAsync(person.Id);

        Assert.Equal(2, result.Count);
        Assert.Contains(result, r => r.QualificationTitle == "Welder" && r.LearningProgrammeTypeName == "Apprenticeship");
        Assert.Contains(result, r => r.QualificationTitle == "Safety Representative" && r.LearningProgrammeTypeName == "Skills Programme");
    }

    [Fact]
    public async Task GetLearnerEmployersAsync_ReturnsHostAndSponsoringWorkplaces()
    {
        var (factory, db, audit, service) = CreateTestContext();

        var person = new Person { FirstName = "Bongani", LastName = "Khumalo", RsaIdNumber = "9805055009088" };
        var org = new Organisation { CompanyName = "Sasol Synfuels", SdlNumber = "L900100200", PhysicalAddress = "Secunda Complex" };
        db.People.Add(person);
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        db.CompanyLearners.Add(new CompanyLearner
        {
            PersonId = person.Id,
            OrganisationId = org.Id,
            QualificationTitle = "Chemical Plant Operator"
        });
        await db.SaveChangesAsync();

        var result = await service.GetLearnerEmployersAsync(person.Id);

        Assert.Single(result);
        Assert.Equal("Sasol Synfuels", result[0].CompanyName);
        Assert.Equal("L900100200", result[0].SdlNumber);
        Assert.Equal("Host & Sponsoring Employer", result[0].RelationshipRole);
    }

    [Fact]
    public async Task GetLearnerAssessmentsAsync_ReturnsUnitStandardEvaluations()
    {
        var (factory, db, audit, service) = CreateTestContext();

        var person = new Person { FirstName = "Kagiso", LastName = "Rabada", RsaIdNumber = "9505055009088" };
        var org = new Organisation { CompanyName = "VW SA", SdlNumber = "L950100200" };
        db.People.Add(person);
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var assessment = new LearnerAssessment
        {
            PersonId = person.Id,
            OrganisationId = org.Id,
            UnitStandardId = 119472,
            UnitStandardTitle = "Accommodate audience and purpose in oral communication",
            AssessmentDate = DateTime.Today,
            EnrolmentStatusId = "02"
        };
        db.LearnerAssessments.Add(assessment);
        await db.SaveChangesAsync();

        var result = await service.GetLearnerAssessmentsAsync(person.Id);

        Assert.Single(result);
        Assert.Equal("119472", result[0].UnitStandardCode);
        Assert.Equal("Competent", result[0].ResultStatusCode);
    }

    #endregion
}
