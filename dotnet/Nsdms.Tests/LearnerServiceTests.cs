using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class LearnerServiceTests
{
    private static NsdmsDbContext CreateInMemoryDbContext()
    {
        var options = new DbContextOptionsBuilder<NsdmsDbContext>()
            .UseInMemoryDatabase(databaseName: Guid.NewGuid().ToString())
            .Options;

        return new NsdmsDbContext(options);
    }

    [Fact]
    public async Task RegisterLearnerAsync_ValidLearner_CreatesContractAndAudits()
    {
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new LearnerService(db, audit);

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
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new LearnerService(db, audit);

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
            TradeTitle = "Automotive Motor Mechanic",
            TestCenterName = "MerSETA Trade Test Centre",
            AttemptNumber = 1
        };

        var result = await service.ScheduleTradeTestAsync(tradeTest, "TESTUSER");

        Assert.True(result.Id > 0);
        Assert.Equal("Scheduled", result.ResultStatusCode);

        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "LearnerTradeTest" && a.RecordId == result.Id);
        Assert.NotNull(auditLog);
        Assert.Equal("ScheduleTradeTest", auditLog.ActionName);
    }

    [Fact]
    public async Task RecordTradeTestResultAsync_Competent_GeneratesCertificateAndCompletesLearner()
    {
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new LearnerService(db, audit);

        var person = new Person { FirstName = "Fatima", LastName = "Adams", RsaIdNumber = "0102145896081" };
        var org = new Organisation { CompanyName = "Toyota SA", SdlNumber = "L700100200" };
        db.People.Add(person);
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var learner = await service.RegisterLearnerAsync(new CompanyLearner
        {
            PersonId = person.Id,
            OrganisationId = org.Id,
            QualificationTitle = "National Certificate: Mechatronics",
            StatusCode = "InProgress"
        });

        var tradeTest = await service.ScheduleTradeTestAsync(new LearnerTradeTest
        {
            CompanyLearnerId = learner.Id,
            TradeTitle = "Mechatronics",
            TestCenterName = "MerSETA Trade Test Centre"
        });

        var outcome = await service.RecordTradeTestResultAsync(tradeTest.Id, "Competent", null, "Excellent practical performance", "TESTUSER");

        Assert.Equal("Competent", outcome.ResultStatusCode);
        Assert.NotNull(outcome.SerialCertificateNumber);
        Assert.StartsWith("CERT-", outcome.SerialCertificateNumber);
        Assert.NotNull(outcome.CertificateIssueDate);

        var updatedLearner = await service.GetLearnerByIdAsync(learner.Id);
        Assert.NotNull(updatedLearner);
        Assert.Equal("Completed", updatedLearner.StatusCode);
        Assert.NotNull(updatedLearner.CompletionDate);
    }
}
