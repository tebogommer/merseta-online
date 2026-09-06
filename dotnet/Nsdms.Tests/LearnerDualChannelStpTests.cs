using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using System.Text;
using Xunit;

namespace Nsdms.Tests;

public class LearnerDualChannelStpTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, LearnerService learnerService, LearnerStpRiskEngine stpEngine, LearnerBulkIngestionService bulkService) CreateTestHarness()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var learnerService = new LearnerService(factory, audit);
        var gatekeeper = new QualificationEnrolmentGatekeeperService();
        var stpEngine = new LearnerStpRiskEngine(factory, gatekeeper);
        var bulkService = new LearnerBulkIngestionService(factory, audit, learnerService, stpEngine);

        return (factory, db, audit, learnerService, stpEngine, bulkService);
    }

    [Fact]
    public async Task StpRiskEngine_CompliantLearner_ApprovesStraightThrough()
    {
        var (_, db, _, _, engine, _) = CreateTestHarness();

        var org = new Organisation
        {
            CompanyName = "Toyota South Africa Motors",
            SdlNumber = "L123456789",
            LevyCategoryCode = "LEVY_PAYING",
            OrganisationStatusCode = "ACTIVE"
        };
        db.Organisations.Add(org);

        var person = new Person
        {
            FirstName = "Thabo",
            LastName = "Khumalo",
            RsaIdNumber = "9501015800085",
            DateOfBirth = new DateTime(1995, 1, 1)
        };
        db.People.Add(person);
        await db.SaveChangesAsync();

        var learner = new CompanyLearner
        {
            PersonId = person.Id,
            OrganisationId = org.Id,
            LearningProgrammeTypeCode = "01",
            SaqaQualificationId = 65789,
            QualificationTitle = "National Certificate: Automotive Repair",
            LearnerSignatureDate = DateTime.UtcNow.AddDays(-5),
            SubmissionDate = DateTime.UtcNow
        };

        var result = await engine.EvaluateRegistrationRiskAsync(learner);

        Assert.True(result.IsStpEligible);
        Assert.Empty(result.RiskFactors);
        Assert.Contains("Automated Straight-Through Processing", result.DecisionReason);
    }

    [Fact]
    public async Task StpRiskEngine_OverdueSubmission_DeclinesStpAndQueuesForOfficer()
    {
        var (_, db, _, _, engine, _) = CreateTestHarness();

        var org = new Organisation
        {
            CompanyName = "Ford Motor Company SA",
            SdlNumber = "L987654321",
            LevyCategoryCode = "LEVY_PAYING",
            OrganisationStatusCode = "ACTIVE"
        };
        db.Organisations.Add(org);

        var person = new Person
        {
            FirstName = "Lerato",
            LastName = "Molefe",
            RsaIdNumber = "9802025800082",
            DateOfBirth = new DateTime(1998, 2, 2)
        };
        db.People.Add(person);
        await db.SaveChangesAsync();

        // 45 working days elapsed (> 30-day statutory SLA)
        var learner = new CompanyLearner
        {
            PersonId = person.Id,
            OrganisationId = org.Id,
            LearningProgrammeTypeCode = "01",
            SaqaQualificationId = 65789,
            LearnerSignatureDate = DateTime.UtcNow.AddDays(-70),
            SubmissionDate = DateTime.UtcNow
        };

        var result = await engine.EvaluateRegistrationRiskAsync(learner);

        Assert.False(result.IsStpEligible);
        Assert.NotEmpty(result.RiskFactors);
        Assert.Contains(result.RiskFactors, r => r.Contains("overdue"));
    }

    [Fact]
    public async Task BulkIngestionService_StagesAndProcessesBatch_WithStpApproval()
    {
        var (_, db, _, _, _, bulkService) = CreateTestHarness();

        var org = new Organisation
        {
            CompanyName = "BMW South Africa",
            SdlNumber = "L555666777",
            LevyCategoryCode = "LEVY_PAYING",
            OrganisationStatusCode = "ACTIVE"
        };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var date1 = DateTime.UtcNow.AddDays(-5).ToString("yyyy-MM-dd");
        var date2 = DateTime.UtcNow.AddDays(10).ToString("yyyy-MM-dd");
        var csv = $"FirstName,MiddleName,LastName,RsaIdNumber,PassportNumber,GenderCode,EquityCode,EmailAddress,PhoneNumber,LearningProgrammeTypeCode,SaqaQualificationId,QualificationTitle,TradeCode,LearnerSignatureDate,CommencementDate\nKagiso,,Mokwena,8001015009087,,M,BA,kagiso.m@example.co.za,0821234567,01,65789,Autotronics,AUTO-01,{date1},{date2}";

        using var stream = new MemoryStream(Encoding.UTF8.GetBytes(csv));
        var batch = await bulkService.StageBatchAsync(org.Id, "bmw_intake_2026.csv", stream, "TestOfficer");

        Assert.NotNull(batch);
        Assert.Equal(1, batch.TotalRows);
        Assert.NotEmpty(batch.DigitalSecuritySeal!);

        var processResult = await bulkService.ProcessBatchAsync(batch.Id, "TestOfficer");

        Assert.Equal(1, processResult.TotalSucceeded);
        Assert.Equal(1, processResult.TotalStpApproved);
        Assert.Equal(0, processResult.TotalFailed);

        var registeredLearner = await db.CompanyLearners.FirstOrDefaultAsync(l => l.IngestionBatchId == batch.Id);
        Assert.NotNull(registeredLearner);
        Assert.Equal("AutomatedBulk", registeredLearner.RegistrationChannel);
        Assert.True(registeredLearner.StpApproved);
        Assert.Equal("Registered", registeredLearner.EnrolmentStatusCode);
    }
}
