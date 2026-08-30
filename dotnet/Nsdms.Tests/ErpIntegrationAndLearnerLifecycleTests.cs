using Microsoft.Extensions.Configuration;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

public class ErpIntegrationAndLearnerLifecycleTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, FeatureFlagService flags, SystemConfigurationService config) CreateContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var inMemory = new Dictionary<string, string?> { { "Features:Integrations.DynamicsGp", "false" } };
        var conf = new ConfigurationBuilder().AddInMemoryCollection(inMemory).Build();
        var configService = new SystemConfigurationService(factory, conf, audit);
        var flagService = new FeatureFlagService(factory, conf, audit);
        return (factory, db, audit, flagService, configService);
    }

    [Fact]
    public async Task ErpIntegrationService_WhenDisabled_DisbursesInSimulatedModeWithoutFailing()
    {
        var (factory, db, audit, flags, config) = CreateContext();
        var erp = new ErpIntegrationService(factory, flags, config, audit);

        var org = new Organisation { CompanyName = "Grantee Ltd", SdlNumber = "L999888777" };
        var grantApp = new GrantApplication { Organisation = org, ProjectTitle = "Green Skills DG", ApplicationNumber = "DG-2026-99", GrantTypeCode = "PIVOTAL", StatusCode = "Approved" };
        var moa = new GrantMoa { GrantApplication = grantApp, MoaNumber = "MOA-2026-99", TotalContractValue = 100000m, MoaStatusCode = "Active" };
        var milestone = new GrantMoaMilestone { GrantMoa = moa, MilestoneNumber = 1, MilestoneTitle = "Inception", TranchePercentage = 50, TrancheAmount = 50000m, MilestoneStatusCode = "Approved" };

        var payment = new GrantTranchePayment
        {
            GrantApplication = grantApp,
            GrantMoaMilestone = milestone,
            PaymentReferenceNumber = "PAY-2026-TEST",
            InvoiceNumber = "INV-001",
            ClaimedAmount = 50000m,
            ApprovedPaymentAmount = 50000m,
            PaymentStatusCode = "Approved"
        };
        db.GrantTranchePayments.Add(payment);
        await db.SaveChangesAsync();

        var result = await erp.PostTranchePaymentBatchAsync(payment.Id, "FinanceOfficer");

        Assert.True(result.Success);
        Assert.True(result.IsSimulated);
        Assert.StartsWith("MOCK-ERP-", result.BatchNumber);

        using var verifyDb = (NsdmsDbContext)factory.CreateDbContext();
        var updatedPayment = await verifyDb.GrantTranchePayments.FindAsync(payment.Id);
        Assert.NotNull(updatedPayment);
        Assert.Equal("Disbursed", updatedPayment.PaymentStatusCode);
    }

    [Fact]
    public async Task LearnerLifecycle_Transfer_TransfersToNewEmployerOnApproval()
    {
        var (factory, db, audit, flags, config) = CreateContext();
        var lifecycle = new LearnerLifecycleService(factory, audit);

        var org1 = new Organisation { CompanyName = "Old Employer", SdlNumber = "L100000001" };
        var org2 = new Organisation { CompanyName = "New Employer", SdlNumber = "L200000002" };
        db.Organisations.AddRange(org1, org2);
        await db.SaveChangesAsync();

        var learner = new CompanyLearner
        {
            OrganisationId = org1.Id,
            QualificationTitle = "Welder",
            StatusCode = "Registered"
        };
        db.CompanyLearners.Add(learner);
        await db.SaveChangesAsync();

        // Request Transfer
        var transfer = new CompanyLearnerTransfer
        {
            CompanyLearnerId = learner.Id,
            ToOrganisationId = org2.Id,
            TransferReasonCode = "CompanyDownsized"
        };
        var requested = await lifecycle.RequestTransferAsync(transfer, "SDF_User");
        Assert.Equal("Pending", requested.StatusCode);

        // Approve Transfer
        var approved = await lifecycle.ApproveTransferAsync(requested.Id, "Transfer approved due to retrenchment", "Manager");
        Assert.Equal("Approved", approved.StatusCode);

        using var verifyDb = (NsdmsDbContext)factory.CreateDbContext();
        var refreshedLearner = await verifyDb.CompanyLearners.FindAsync(learner.Id);
        Assert.NotNull(refreshedLearner);
        Assert.Equal(org2.Id, refreshedLearner.OrganisationId);
    }

    [Fact]
    public async Task LearnerLifecycle_LostTime_RecalculatesEndDateAndApproves()
    {
        var (factory, db, audit, flags, config) = CreateContext();
        var lifecycle = new LearnerLifecycleService(factory, audit);

        var learner = new CompanyLearner
        {
            QualificationTitle = "Fitter and Turner",
            RegistrationDate = new DateTime(2026, 1, 1),
            StatusCode = "InProgress"
        };
        db.CompanyLearners.Add(learner);
        await db.SaveChangesAsync();

        var lostTime = new CompanyLearnerLostTime
        {
            CompanyLearnerId = learner.Id,
            LostTimeReasonCode = "MedicalLeave",
            StartDate = new DateTime(2026, 5, 1),
            EndDate = new DateTime(2026, 6, 1) // 31 days
        };

        var recorded = await lifecycle.RecordLostTimeAsync(lostTime, "MedicalOfficer");
        Assert.Equal(31, recorded.DaysLost);
        Assert.Equal("Pending", recorded.StatusCode);

        var approved = await lifecycle.ApproveLostTimeAsync(recorded.Id, "Medical certificates verified", "Manager");
        Assert.Equal("Approved", approved.StatusCode);
    }

    [Fact]
    public async Task LearnerLifecycle_Termination_SetsLearnerStatusToTerminated()
    {
        var (factory, db, audit, flags, config) = CreateContext();
        var lifecycle = new LearnerLifecycleService(factory, audit);

        var learner = new CompanyLearner
        {
            QualificationTitle = "Electrician",
            StatusCode = "InProgress"
        };
        db.CompanyLearners.Add(learner);
        await db.SaveChangesAsync();

        var termination = new CompanyLearnerTermination
        {
            CompanyLearnerId = learner.Id,
            TerminationReasonCode = "Absconded",
            EffectiveDate = DateTime.UtcNow,
            SettlementNotes = "Learner failed to report for 30 consecutive shifts"
        };

        var requested = await lifecycle.RequestTerminationAsync(termination, "HR_Officer");
        Assert.Equal("Pending", requested.StatusCode);

        var approved = await lifecycle.ApproveTerminationAsync(requested.Id, "Contract cancelled per SETA guidelines", "Manager");
        Assert.Equal("Approved", approved.StatusCode);

        using var verifyDb = (NsdmsDbContext)factory.CreateDbContext();
        var refreshedLearner = await verifyDb.CompanyLearners.FindAsync(learner.Id);
        Assert.NotNull(refreshedLearner);
        Assert.Equal("Terminated", refreshedLearner.StatusCode);
    }
}
