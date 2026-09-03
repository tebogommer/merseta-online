using System.IO.Compression;
using System.Text;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging.Abstractions;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Xunit;

namespace Nsdms.Tests;

public class StatutoryExtractEngineTests
{
    private readonly TestDbContextFactory _factory;
    private readonly AuditService _auditService;
    private readonly ISetmisExtractService _setmisService;
    private readonly INlrdExtractService _nlrdService;
    private readonly IStatutorySchedulerService _schedulerService;

    public StatutoryExtractEngineTests()
    {
        var dbName = "StatutoryExtractTests_" + Guid.NewGuid();
        _factory = new TestDbContextFactory(dbName);
        _auditService = new AuditService(_factory);

        _setmisService = new SetmisExtractService(
            _factory,
            _auditService,
            NullLogger<SetmisExtractService>.Instance);

        _nlrdService = new NlrdExtractService(
            _factory,
            _auditService,
            NullLogger<NlrdExtractService>.Instance);

        var config = new ConfigurationBuilder().Build();
        var configService = new SystemConfigurationService(_factory, config, _auditService);
        _schedulerService = new StatutorySchedulerService(
            _factory,
            configService,
            _auditService,
            NullLogger<StatutorySchedulerService>.Instance);

        SeedSampleEntities();
    }

    private void SeedSampleEntities()
    {
        using var ctx = (Nsdms.Infrastructure.Data.NsdmsDbContext)_factory.CreateDbContext();

        var person = new Person
        {
            Id = 1,
            RsaIdNumber = "9001015009087",
            FirstName = "Sipho",
            LastName = "Nkosi",
            DateOfBirth = new DateTime(1990, 1, 1),
            GenderCode = "M",
            EquityCode = "BA",
            NationalityCode = "SA",
            Email = "sipho.nkosi@example.com",
            PhoneNumber = "0115551234",
            PostalAddress = "10 Commissioner St",
            PostalAddressPostalCode = "2001",
            PhysicalAddress = "10 Commissioner St",
            PhysicalAddressPostalCode = "2001",
            ProvinceCode = "GP",
            DisabilityCode = "N"
        };
        ctx.People.Add(person);

        var org = new Organisation
        {
            Id = 1,
            SdlNumber = "L123456789",
            CompanyName = "Apex Automotive Manufacturing",
            LegalName = "Apex Automotive Manufacturing Pty Ltd",
            RegistrationNumber = "2015/123456/07",
            TaxNumber = "9876543210",
            SicCode = "38100",
            ChamberCode = "AUTO",
            CompanySizeCode = "LARGE",
            OrganisationStatusCode = "ACTIVE",
            OrganisationTypeCode = "PTY_LTD",
            PhoneNumber = "0112223344",
            PhysicalAddress = "45 Industry Road, Rosslyn",
            PhysicalAddressPostalCode = "0200",
            PostalAddress = "PO Box 100, Rosslyn",
            PostalAddressPostalCode = "0200",
            PrimaryContactPersonId = 1
        };
        ctx.Organisations.Add(org);

        var provider = new TrainingProvider
        {
            Id = 1,
            ProviderCode = "PRV-0001",
            ProviderName = "Gauteng Artisan Technical Academy",
            OrganisationId = 1,
            AccreditationNumber = "ACC/2026/001",
            AccreditationStartDate = new DateTime(2025, 1, 1),
            AccreditationEndDate = new DateTime(2028, 12, 31),
            EtqaDecisionNumber = "ETQA-DEC-2025-01",
            EtqaId = "599",
            PrimaryContactPersonId = 1
        };
        ctx.TrainingProviders.Add(provider);

        var providerQual = new TrainingProviderQualification
        {
            Id = 1,
            TrainingProviderId = 1,
            SaqaQualificationId = 24418,
            QualificationTitle = "National Certificate: Mechanical Engineering",
            AccreditationStatusCode = "Accredited",
            ExpiryDate = new DateTime(2028, 12, 31)
        };
        ctx.TrainingProviderQualifications.Add(providerQual);

        var assessor = new EtqaAssessor
        {
            Id = 1,
            PersonId = 1,
            RegistrationNumber = "ASR-2026-0042",
            DesignationTypeId = "01",
            DesignationStructureStatusId = "01",
            StartDate = new DateTime(2025, 1, 1),
            EndDate = new DateTime(2028, 12, 31),
            EtqaId = "599"
        };
        ctx.EtqaAssessors.Add(assessor);

        var scope = new AssessorModeratorScope
        {
            Id = 1,
            EtqaAssessorId = 1,
            SaqaQualificationId = 24418,
            QualificationTitle = "National Certificate: Mechanical Engineering",
            RegistrationStatusCode = "Active",
            ExpiryDate = new DateTime(2028, 12, 31)
        };
        ctx.AssessorModeratorScopes.Add(scope);

        var learner = new CompanyLearner
        {
            Id = 1,
            PersonId = 1,
            OrganisationId = 1,
            TrainingProviderId = 1,
            LearnershipId = "18Q180026241203",
            SaqaQualificationId = 24418,
            EnrolmentStatusId = "01",
            CommencementDate = new DateTime(2026, 2, 1),
            CompletionDate = new DateTime(2027, 1, 31),
            CertificateNumber = "CERT-2026-001"
        };
        ctx.CompanyLearners.Add(learner);

        var assessment = new LearnerAssessment
        {
            Id = 1,
            CompanyLearnerId = 1,
            UnitStandardId = 119472,
            AssessmentDate = new DateTime(2026, 6, 1),
            EnrolmentStatusId = "02",
            AssessorRegistrationNumber = "ASR-2026-0042",
            CertificateNumber = "US-CERT-001"
        };
        ctx.LearnerAssessments.Add(assessment);

        var tradeTest = new LearnerTradeTest
        {
            Id = 1,
            CompanyLearnerId = 1,
            TradeCode = "651202",
            TradeTestDate = new DateTime(2026, 7, 15),
            TradeTestNumber = 1,
            TradeTestResultId = "01",
            AssessorRegistrationNumber = "ASR-2026-0042"
        };
        ctx.LearnerTradeTests.Add(tradeTest);

        var skillsReg = new SkillsRegistration
        {
            Id = 1,
            NonNqfIntervCode = "NON-NQF-001",
            NonNqfIntervName = "Advanced CNC Milling Automation",
            EtqaId = "17",
            SubfieldId = "06"
        };
        ctx.SkillsRegistrations.Add(skillsReg);

        ctx.SaveChanges();
    }

    [Fact]
    public void FixedFormatWriter_FormatString_PadsAndSanitizesCorrectly()
    {
        // Trailing padding with spaces
        var formatted = FixedFormatWriter.FormatString("Apex Auto", 15);
        Assert.Equal(15, formatted.Length);
        Assert.Equal("APEX AUTO      ", formatted);

        // Truncation when exceeding length
        var truncated = FixedFormatWriter.FormatString("1234567890ABCDEF", 10);
        Assert.Equal(10, truncated.Length);
        Assert.Equal("1234567890", truncated);

        // Sanitizing newlines and tabs
        var sanitized = FixedFormatWriter.FormatString("Line1\r\nLine2\tEnd", 20);
        Assert.Equal(20, sanitized.Length);
        Assert.DoesNotContain("\r", sanitized);
        Assert.DoesNotContain("\n", sanitized);
        Assert.DoesNotContain("\t", sanitized);
    }

    [Fact]
    public void FixedFormatWriter_FormatNlrdHeader_FormatsExactSAQAStructure()
    {
        var header = FixedFormatWriter.FormatNlrdHeader("Provider (21)", 42, 847, "599");

        Assert.Equal(847, header.Length);
        Assert.StartsWith("HEADER599", header);
        // Record count at pos 29, 9 digits zero-padded: "000000042"
        Assert.Contains("000000042", header);
    }

    [Theory]
    [InlineData("100", 799)]
    [InlineData("200", 796)]
    [InlineData("304", 328)]
    [InlineData("400", 845)]
    [InlineData("401", 187)]
    [InlineData("500", 258)]
    [InlineData("501", 411)]
    [InlineData("502", 273)]
    [InlineData("503", 407)]
    [InlineData("505", 206)]
    [InlineData("506", 185)]
    public async Task ExtractSetmisFileAsync_ProducesExactStatutoryRecordLengths(string fileCode, int expectedLength)
    {
        var result = await _setmisService.ExtractSetmisFileAsync(fileCode, new DateTime(2026, 9, 2));

        Assert.NotNull(result);
        Assert.Equal(fileCode, result.FileCode);
        Assert.Equal(expectedLength, result.RecordLength);
        Assert.StartsWith($"MERS_0006_{fileCode}_v001_20260902.dat", result.FileName);

        if (result.RecordCount > 0)
        {
            var lines = result.Content.Split(new[] { "\r\n" }, StringSplitOptions.RemoveEmptyEntries);
            Assert.NotEmpty(lines);
            foreach (var line in lines)
            {
                Assert.Equal(expectedLength, line.Length);
            }
        }
    }

    [Theory]
    [InlineData("21", 847)]
    [InlineData("24", 135)]
    [InlineData("25", 791)]
    [InlineData("26", 157)]
    [InlineData("27", 119)]
    [InlineData("28", 143)]
    [InlineData("29", 161)]
    [InlineData("30", 171)]
    public async Task ExtractNlrdFileAsync_ProducesExactStatutoryRecordLengthsAndHeader(string fileCode, int expectedLength)
    {
        var result = await _nlrdService.ExtractNlrdFileAsync(fileCode, new DateTime(2026, 9, 2));

        Assert.NotNull(result);
        Assert.Equal(fileCode, result.FileCode);
        Assert.Equal(expectedLength, result.RecordLength);
        Assert.Equal($"MERS{fileCode}260902.dat", result.FileName);

        var lines = result.Content.Split(new[] { "\r\n" }, StringSplitOptions.RemoveEmptyEntries);
        Assert.True(lines.Length >= 1, "Should have at least the SAQA HEADER record.");

        // First line must be SAQA HEADER of exact record length
        var headerLine = lines[0];
        Assert.Equal(expectedLength, headerLine.Length);
        Assert.StartsWith("HEADER599", headerLine);

        // Data lines must also be of exact record length
        for (int i = 1; i < lines.Length; i++)
        {
            Assert.Equal(expectedLength, lines[i].Length);
        }
    }

    [Fact]
    public async Task GenerateFullSetmisBatchAsync_GeneratesZipArchiveAndSecuritySeal()
    {
        var batch = await _setmisService.GenerateFullSetmisBatchAsync(2026, 1, "Automated test SETMIS run");

        Assert.NotNull(batch);
        Assert.Equal("SETMIS", batch.BatchType);
        Assert.Equal(2026, batch.SubmissionYear);
        Assert.Equal("Extracted", batch.StatusCode);
        Assert.NotEmpty(batch.DigitalSecuritySeal);
        Assert.Equal(64, batch.DigitalSecuritySeal.Length); // SHA-256 hex string length
        Assert.Equal(11, batch.Files.Count);

        // Download archive and verify zip contents
        var archiveResult = await _setmisService.DownloadSetmisBatchArchiveAsync(batch.Id);
        Assert.NotNull(archiveResult);
        Assert.NotEmpty(archiveResult.ZipBytes);

        using var zipStream = new MemoryStream(archiveResult.ZipBytes);
        using var zip = new ZipArchive(zipStream, ZipArchiveMode.Read);
        Assert.Equal(11, zip.Entries.Count);
    }

    [Fact]
    public async Task GenerateFullNlrdBatchAsync_GeneratesZipArchiveAndSecuritySeal()
    {
        var batch = await _nlrdService.GenerateFullNlrdBatchAsync(2026, "Automated test NLRD run");

        Assert.NotNull(batch);
        Assert.Equal("NLRD", batch.BatchType);
        Assert.Equal(2026, batch.SubmissionYear);
        Assert.Equal("Extracted", batch.StatusCode);
        Assert.NotEmpty(batch.DigitalSecuritySeal);
        Assert.Equal(64, batch.DigitalSecuritySeal.Length); // SHA-256 hex string length
        Assert.Equal(8, batch.Files.Count);

        // Download archive and verify zip contents
        var archiveResult = await _nlrdService.DownloadNlrdBatchArchiveAsync(batch.Id);
        Assert.NotNull(archiveResult);
        Assert.NotEmpty(archiveResult.ZipBytes);

        using var zipStream = new MemoryStream(archiveResult.ZipBytes);
        using var zip = new ZipArchive(zipStream, ZipArchiveMode.Read);
        Assert.Equal(8, zip.Entries.Count);
    }

    [Fact]
    public async Task StatutorySchedulerService_ExecutesAllThreeBackgroundJobsSuccessfully()
    {
        // 1. SARS Levy Recon Job
        var sarsResult = await _schedulerService.TriggerJobAsync("SARS_LEVY_RECON", "TestRunner");
        Assert.True(sarsResult.Success);
        Assert.Contains("Reconciled", sarsResult.Summary);

        // 2. WSP Deadline Monitor Job
        var wspResult = await _schedulerService.TriggerJobAsync("WSP_DEADLINE_MONITOR", "TestRunner");
        Assert.True(wspResult.Success);
        Assert.Contains("30 April", wspResult.Summary);

        // 3. SETMIS Monthly Delta Job
        var deltaResult = await _schedulerService.TriggerJobAsync("SETMIS_MONTHLY_DELTA", "TestRunner");
        Assert.True(deltaResult.Success);
        Assert.Contains("Delta scan complete", deltaResult.Summary);

        // Verify status reflects execution
        var statuses = await _schedulerService.GetJobStatusesAsync();
        Assert.Equal(3, statuses.Count);
        Assert.All(statuses, s => Assert.Equal("Success", s.LastStatus));
        Assert.All(statuses, s => Assert.NotNull(s.LastRunTime));
    }
}
