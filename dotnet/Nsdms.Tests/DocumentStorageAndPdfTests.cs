using System.Text;
using Microsoft.Extensions.Configuration;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

public class DocumentStorageAndPdfTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, SystemConfigurationService config, FeatureFlagService flags) CreateContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var inMemory = new Dictionary<string, string?> { { "Storage.UploadDirectory", "test_uploads" } };
        var conf = new ConfigurationBuilder().AddInMemoryCollection(inMemory).Build();
        var configService = new SystemConfigurationService(factory, conf, audit);
        var flagService = new FeatureFlagService(factory, conf, audit);
        return (factory, db, audit, configService, flagService);
    }

    [Fact]
    public async Task LocalFileStorageService_SaveRetrieveDelete_SucceedsWithHash()
    {
        var (factory, db, audit, config, flags) = CreateContext();
        var storage = new LocalFileStorageService(factory, config, audit);

        var content = "SAMPLE CERTIFICATE ATTACHMENT CONTENT";
        var bytes = Encoding.UTF8.GetBytes(content);
        using var stream = new MemoryStream(bytes);

        var saved = await storage.SaveFileAsync("CompanyLearner", 101, "learner_id.pdf", "application/pdf", stream, "ID_DOCUMENT", "Tester");
        Assert.True(saved.Id > 0);
        Assert.NotNull(saved.FileHashSha256);
        Assert.Equal("CompanyLearner", saved.TargetEntityName);
        Assert.Equal(101, saved.TargetEntityId);

        var list = await storage.GetAttachmentsAsync("CompanyLearner", 101);
        Assert.Single(list);

        var retrieved = await storage.GetFileAsync(saved.Id);
        Assert.NotNull(retrieved);
        using var reader = new StreamReader(retrieved.Value.ContentStream);
        var readContent = await reader.ReadToEndAsync();
        Assert.Equal(content, readContent);

        var deleted = await storage.DeleteAttachmentAsync(saved.Id, "Tester");
        Assert.True(deleted);
    }

    [Fact]
    public async Task QuestPdfDocumentService_GeneratesValidPdfBytes_ForTradeTestCertificate()
    {
        var (factory, db, audit, config, flags) = CreateContext();
        var pdfService = new QuestPdfDocumentService(flags, config);

        var tradeTest = new LearnerTradeTest
        {
            TradeTitle = "Automotive Motor Mechanic",
            SerialCertificateNumber = "CERT-2026-TEST01",
            CertificateIssueDate = DateTime.UtcNow,
            TestCenterName = "merSETA Trade Test Centre",
            CompanyLearner = new CompanyLearner
            {
                Person = new Person { FirstName = "Bongani", LastName = "Nkosi", RsaIdNumber = "9506155009087" }
            }
        };

        var pdfBytes = await pdfService.GenerateTradeTestCertificateAsync(tradeTest);
        Assert.NotNull(pdfBytes);
        Assert.True(pdfBytes.Length > 1000);
        Assert.Equal("%PDF", Encoding.ASCII.GetString(pdfBytes[0..4]));
    }

    [Fact]
    public async Task QuestPdfDocumentService_GeneratesValidPdfBytes_ForGrantMoa()
    {
        var (factory, db, audit, config, flags) = CreateContext();
        var pdfService = new QuestPdfDocumentService(flags, config);

        var moa = new GrantMoa
        {
            MoaNumber = "MOA-2026-TEST-99",
            ContractStartDate = new DateTime(2026, 4, 1),
            ContractEndDate = new DateTime(2027, 3, 31),
            TotalContractValue = 500000m,
            Milestones = new List<GrantMoaMilestone>
            {
                new() { MilestoneNumber = 1, MilestoneTitle = "Tranche 1: Induction", TranchePercentage = 30, TrancheAmount = 150000, TargetDueDate = DateTime.UtcNow },
                new() { MilestoneNumber = 2, MilestoneTitle = "Tranche 2: Practical", TranchePercentage = 30, TrancheAmount = 150000, TargetDueDate = DateTime.UtcNow }
            }
        };

        var pdfBytes = await pdfService.GenerateGrantMoaDocumentAsync(moa);
        Assert.NotNull(pdfBytes);
        Assert.True(pdfBytes.Length > 1000);
        Assert.Equal("%PDF", Encoding.ASCII.GetString(pdfBytes[0..4]));
    }

    [Fact]
    public async Task QuestPdfDocumentService_GeneratesValidPdfBytes_ForArtisanTradeCertificateApp()
    {
        var (factory, db, audit, config, flags) = CreateContext();
        var pdfService = new QuestPdfDocumentService(flags, config);

        var app = new LearnerTradeTestApplication
        {
            TradeTitle = "Diesel Mechanic (Artisan)",
            ApplicationTypeCode = "Section26D",
            TradeOfoCode = "653306",
            SerialCertificateNumber = "CERT-2026-DM-0099",
            NambSerialNumber = "NAMB-2026-8819",
            CertificateIssueDate = DateTime.UtcNow,
            AssessmentCenterName = "Ekurhuleni Artisan Centre",
            Person = new Person { FirstName = "Kagiso", LastName = "Modise", RsaIdNumber = "9604125829081" }
        };

        var pdfBytes = await pdfService.GenerateArtisanTradeCertificatePdfAsync(app);
        Assert.NotNull(pdfBytes);
        Assert.True(pdfBytes.Length > 1000);
        Assert.Equal("%PDF", Encoding.ASCII.GetString(pdfBytes[0..4]));
    }

    [Fact]
    public async Task QuestPdfDocumentService_GeneratesValidPdfBytes_ForStatementOfResults()
    {
        var (factory, db, audit, config, flags) = CreateContext();
        var pdfService = new QuestPdfDocumentService(flags, config);

        var report = new SummativeAssessmentReport
        {
            QualificationTitle = "National Certificate: Automotive Repair and Maintenance",
            SaqaQualificationId = "SAQA-65409",
            NqfLevel = 4,
            Person = new Person { FirstName = "Bongani", LastName = "Nkosi", RsaIdNumber = "9208155829082" },
            UnitStandardAssessments = new List<SummativeAssessmentUnitStandard>
            {
                new() { UnitStandardCode = "US-119472", UnitStandardTitle = "Oral communications", NqfLevel = 3, Credits = 10, CompetencyStatusCode = "Competent" },
                new() { UnitStandardCode = "US-9844", UnitStandardTitle = "Mechanical fitting", NqfLevel = 4, Credits = 40, CompetencyStatusCode = "Competent" }
            }
        };

        var sor = new StatementOfResults
        {
            SorSerialNumber = "SOR-2026-00084",
            TotalCreditsCertified = 50,
            TamperProofHashSha256 = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855",
            DateIssued = DateTime.UtcNow,
            IssuedByUserId = "CertificationOfficer"
        };

        var pdfBytes = await pdfService.GenerateStatementOfResultsPdfAsync(report, sor);
        Assert.NotNull(pdfBytes);
        Assert.True(pdfBytes.Length > 1000);
        Assert.Equal("%PDF", Encoding.ASCII.GetString(pdfBytes[0..4]));
    }

    [Fact]
    public async Task QuestPdfDocumentService_GeneratesValidPdfBytes_ForSarsClawbackNotice()
    {
        var (factory, db, audit, config, flags) = CreateContext();
        var pdfService = new QuestPdfDocumentService(flags, config);

        var auditRecord = new SarsLevyReconAudit
        {
            FinancialYear = "2026",
            SdlNumber = "L123456789",
            TotalSarsLeviesReceived = 550000m,
            TotalCalculatedLeviesExpected = 500000m,
            VarianceAmount = 50000m,
            ClawbackAmount = 50000m,
            DiscrepancyReasonCode = "Overpayment",
            Organisation = new Organisation { CompanyName = "Precision Diesel Engineering Ltd", SdlNumber = "L123456789" }
        };

        var pdfBytes = await pdfService.GenerateSarsClawbackNoticePdfAsync(auditRecord);
        Assert.NotNull(pdfBytes);
        Assert.True(pdfBytes.Length > 1000);
        Assert.Equal("%PDF", Encoding.ASCII.GetString(pdfBytes[0..4]));
    }

    [Fact]
    public async Task QuestPdfDocumentService_GeneratesValidPdfBytes_ForQcdScopingDocument()
    {
        var (factory, db, audit, config, flags) = CreateContext();
        var pdfService = new QuestPdfDocumentService(flags, config);

        var qcd = new QualificationsCurriculumDevelopment
        {
            ApplicationNumber = "QCD-2026-0001",
            QualificationTitle = "Occupational Certificate: Electric Vehicle High-Voltage Technician",
            OfoCode = "653306",
            NqfLevel = 5,
            TotalCreditsRequired = 180,
            DevelopmentQualityPartner = "merSETA DQP",
            AssessmentQualityPartner = "merSETA AQP",
            NationalDevelopmentPlanChecked = true,
            NewGrowthPlanChecked = true,
            IndustrialPolicyActionPlanChecked = true,
            PurposeOfQualification = "Equip specialist diagnostic technicians with EV safety competencies.",
            WorkingGroupMembers = new List<CurriculumWorkingGroupMember>
            {
                new() { MemberName = "Dr. Dumisani Sithole", StakeholderRoleTitle = "IndustryExpert", OrganisationRepresented = "AIDC" }
            }
        };

        var pdfBytes = await pdfService.GenerateQcdScopingDocumentPdfAsync(qcd);
        Assert.NotNull(pdfBytes);
        Assert.True(pdfBytes.Length > 1000);
        Assert.Equal("%PDF", Encoding.ASCII.GetString(pdfBytes[0..4]));
    }
}
