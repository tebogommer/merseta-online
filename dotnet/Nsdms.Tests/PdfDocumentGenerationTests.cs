using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Nsdms.Application.Common;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

public class PdfDocumentGenerationTests
{
    private (QuestPdfDocumentService pdfService, TestDbContextFactory factory) CreateServices()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var conf = new ConfigurationBuilder().AddInMemoryCollection().Build();
        var audit = new AuditService(factory);
        var config = new SystemConfigurationService(factory, conf, audit);
        var flags = new FeatureFlagService(factory, conf, audit);
        var pdfService = new QuestPdfDocumentService(factory, flags, config);

        return (pdfService, factory);
    }

    [Fact]
    public async Task GenerateGrantMoaContractPdfAsync_ShouldGenerateValidPdfBytes()
    {
        var (pdfService, factory) = CreateServices();

        using var db = await factory.CreateDbContextAsync();
        var org = new Organisation
        {
            CompanyName = "Toyota SA Motors (Pty) Ltd",
            RegistrationNumber = "1961/001767/07",
            SdlNumber = "L100200300",
            ChamberCode = "AUTO"
        };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var grantApp = new GrantApplication
        {
            OrganisationId = org.Id,
            ProjectTitle = "Apprentice Automotive Mechatronics 2026",
            ApplicationNumber = "DG-2026-APP-0042",
            RequestedAmount = 2500000.00m,
            ApprovedAmount = 2500000.00m,
            ApplicationStatusCode = "Approved"
        };
        db.GrantApplications.Add(grantApp);
        await db.SaveChangesAsync();

        var moa = new GrantMoa
        {
            GrantApplicationId = grantApp.Id,
            MoaNumber = "MOA-2026-DG-00042",
            TotalContractValue = 2500000.00m,
            MoaStatusCode = "Active",
            ContractStartDate = new DateTime(2026, 4, 1),
            ContractEndDate = new DateTime(2027, 3, 31)
        };
        db.GrantMoas.Add(moa);
        await db.SaveChangesAsync();

        moa.Milestones.Add(new GrantMoaMilestone
        {
            GrantMoaId = moa.Id,
            MilestoneNumber = 1,
            MilestoneTitle = "Learner Registration & Contract Execution",
            TranchePercentage = 25.00m,
            MilestoneStatusCode = "Paid"
        });
        moa.Milestones.Add(new GrantMoaMilestone
        {
            GrantMoaId = moa.Id,
            MilestoneNumber = 2,
            MilestoneTitle = "Mid-Term Workplace Progress & Logbook Verification",
            TranchePercentage = 50.00m,
            MilestoneStatusCode = "Submitted"
        });
        await db.SaveChangesAsync();

        var pdfBytes = await pdfService.GenerateGrantMoaContractPdfAsync(moa.Id);

        Assert.NotNull(pdfBytes);
        Assert.True(pdfBytes.Length > 1000, "Generated PDF must contain valid document stream bytes");
        // PDF header magic bytes: %PDF
        Assert.Equal((byte)'%', pdfBytes[0]);
        Assert.Equal((byte)'P', pdfBytes[1]);
        Assert.Equal((byte)'D', pdfBytes[2]);
        Assert.Equal((byte)'F', pdfBytes[3]);
    }

    [Fact]
    public async Task GenerateTradeTestCertificatePdfAsync_ShouldGenerateValidArtisanCertificate()
    {
        var (pdfService, factory) = CreateServices();

        using var db = await factory.CreateDbContextAsync();
        var person = new Person
        {
            FirstName = "Sipho",
            LastName = "Ndlovu",
            RsaIdNumber = "9408155820084"
        };
        db.People.Add(person);
        await db.SaveChangesAsync();

        var org = new Organisation
        {
            CompanyName = "Toyota SA Manufacturing",
            SdlNumber = "L100200300"
        };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var learner = new CompanyLearner
        {
            PersonId = person.Id,
            OrganisationId = org.Id,
            LearnerContractNumber = "CON-2026-00088",
            RegistrationDate = new DateTime(2024, 1, 15)
        };
        db.CompanyLearners.Add(learner);
        await db.SaveChangesAsync();

        var app = new LearnerTradeTestApplication
        {
            CompanyLearnerId = learner.Id,
            ApplicationNumber = "TTA-2026-0001",
            TradeTitle = "Automotive Motor Mechanic",
            TradeOfoCode = "653101",
            StatusCode = "Certified",
            CompetencyStatusCode = "Competent",
            AssessmentCenterName = "Toyota Technical Training Academy",
            SerialCertificateNumber = "CERT-NAMB-2026-0099",
            DesignatedTradeLevel = "Level 4"
        };
        db.LearnerTradeTestApplications.Add(app);
        await db.SaveChangesAsync();

        var pdfBytes = await pdfService.GenerateTradeTestCertificatePdfAsync(app.Id);

        Assert.NotNull(pdfBytes);
        Assert.True(pdfBytes.Length > 1000);
        Assert.Equal((byte)'%', pdfBytes[0]);
        Assert.Equal((byte)'P', pdfBytes[1]);
        Assert.Equal((byte)'D', pdfBytes[2]);
        Assert.Equal((byte)'F', pdfBytes[3]);
    }

    [Fact]
    public async Task GenerateWspOutcomeLetterPdfAsync_ShouldGenerateFormalNotificationLetter()
    {
        var (pdfService, factory) = CreateServices();

        using var db = await factory.CreateDbContextAsync();
        var org = new Organisation
        {
            CompanyName = "Sasol Synfuels (Pty) Ltd",
            SdlNumber = "L800200300",
            RegistrationNumber = "1979/003200/07"
        };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var wsp = new WspSubmission
        {
            OrganisationId = org.Id,
            FinYear = 2026,
            ReferenceNumber = "WSP-2026-00045",
            EmployeeCount = 850,
            PlannedTrainingBudget = 3200000.00m,
            WspApprovalStatusCode = "Approved"
        };
        db.WspSubmissions.Add(wsp);
        await db.SaveChangesAsync();

        var pdfBytes = await pdfService.GenerateWspOutcomeLetterPdfAsync(wsp.Id);

        Assert.NotNull(pdfBytes);
        Assert.True(pdfBytes.Length > 1000);
        Assert.Equal((byte)'%', pdfBytes[0]);
        Assert.Equal((byte)'P', pdfBytes[1]);
        Assert.Equal((byte)'D', pdfBytes[2]);
        Assert.Equal((byte)'F', pdfBytes[3]);
    }

    [Fact]
    public async Task GenerateMandatoryRebateRemittancePdfAsync_ShouldGeneratePaymentVoucher()
    {
        var (pdfService, factory) = CreateServices();

        using var db = await factory.CreateDbContextAsync();
        var org = new Organisation
        {
            CompanyName = "Bell Equipment Co SA",
            SdlNumber = "L450200300"
        };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var wsp = new WspSubmission
        {
            OrganisationId = org.Id,
            FinYear = 2026,
            ReferenceNumber = "WSP-2026-00012"
        };
        db.WspSubmissions.Add(wsp);
        await db.SaveChangesAsync();

        var disb = new MandatoryGrantDisbursement
        {
            OrganisationId = org.Id,
            WspSubmissionId = wsp.Id,
            FinYear = 2026,
            DisbursementReference = "MGD-2026-Q1-00012",
            LeviesReceivedAmount = 1000000.00m,
            CalculatedRebateAmount = 200000.00m,
            DisbursementStatusCode = "Paid",
            PaymentDate = DateTime.UtcNow,
            BatchNumber = "BATCH-2026-04",
            BankAccountSnapshot = "Nedbank Corporate | Acc: ************8820"
        };
        db.MandatoryGrantDisbursements.Add(disb);
        await db.SaveChangesAsync();

        var pdfBytes = await pdfService.GenerateMandatoryRebateRemittancePdfAsync(disb.Id);

        Assert.NotNull(pdfBytes);
        Assert.True(pdfBytes.Length > 1000);
        Assert.Equal((byte)'%', pdfBytes[0]);
        Assert.Equal((byte)'P', pdfBytes[1]);
        Assert.Equal((byte)'D', pdfBytes[2]);
        Assert.Equal((byte)'F', pdfBytes[3]);
    }
}
