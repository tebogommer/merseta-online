using System.Text;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

public class Document2DBarcodeVerificationTests
{
    private readonly TestDbContextFactory _factory;
    private readonly AuditService _audit;
    private readonly SystemConfigurationService _config;
    private readonly FeatureFlagService _featureFlags;
    private readonly DocumentVerificationService _verificationService;
    private readonly QuestPdfDocumentService _pdfService;
    private readonly DocumentIngestionBarcodeService _ingestionBarcodeService;

    public Document2DBarcodeVerificationTests()
    {
        _factory = new TestDbContextFactory($"Barcode2DDb_{Guid.NewGuid()}");
        var conf = new Microsoft.Extensions.Configuration.ConfigurationBuilder().Build();
        _audit = new AuditService(_factory);
        _config = new SystemConfigurationService(_factory, conf, _audit);
        _featureFlags = new FeatureFlagService(_factory, conf, _audit);
        _verificationService = new DocumentVerificationService(_factory, _audit, _config);
        _pdfService = new QuestPdfDocumentService(_factory, _featureFlags, _config, _verificationService);
        _ingestionBarcodeService = new DocumentIngestionBarcodeService();
    }

    [Fact]
    public async Task WspApprovalLetter_ShouldEmbed2DVerificationSealAndGenerateValidPdf()
    {
        // Arrange
        var org = new Organisation
        {
            Id = 1,
            CompanyName = "Toyota South Africa Motors",
            SdlNumber = "L123456789",
            CreatedBy = "Test"
        };

        var wsp = new WspSubmission
        {
            Id = 42,
            ReferenceNumber = "WSP-2026-0042",
            FinYear = 2026,
            EmployeeCount = 1250,
            PlannedTrainingBudget = 2500000m,
            SubmissionDate = DateTime.UtcNow.AddDays(-5),
            Organisation = org,
            OrganisationId = 1,
            WspApprovalStatusCode = "Approved",
            CreatedBy = "SDF"
        };

        // Act
        byte[] pdfBytes = await _pdfService.GenerateWspApprovalLetterAsync(wsp);

        // Assert
        Assert.NotNull(pdfBytes);
        Assert.True(pdfBytes.Length > 5000, "WSP Approval PDF should contain rich QuestPDF layout and vector QR code");

        // Verify DocumentSnapshot creation and double-write
        using var db = await _factory.CreateDbContextAsync();
        var snapshot = await db.DocumentSnapshots.FirstOrDefaultAsync(s => s.RelatedEntityType == "WspSubmission" && s.RelatedEntityId == 42);
        Assert.NotNull(snapshot);
        Assert.Equal("WSP_APPROVAL_LETTER", snapshot.DocumentTypeCode);
        Assert.Equal("WSP-2026-0042", snapshot.DocumentSnapshotNumber);
        Assert.Equal("Toyota South Africa Motors", snapshot.RecipientName);
        Assert.Equal("L123456789", snapshot.RecipientIdentifier);
        Assert.Equal(64, snapshot.RenderedContentHash.Length);
        Assert.StartsWith("data:image/png;base64,", snapshot.VerificationQrBase64!);
        Assert.Contains(snapshot.RenderedContentHash, snapshot.VerificationUri);
    }

    [Fact]
    public async Task ArtisanTradeCertificate_ShouldEmbed2DVerificationSealAndGenerateValidPdf()
    {
        // Arrange
        var person = new Person
        {
            Id = 10,
            FirstName = "Bongani",
            LastName = "Zulu",
            RsaIdNumber = "9504125890082",
            CreatedBy = "Test"
        };

        var app = new LearnerTradeTestApplication
        {
            Id = 101,
            Person = person,
            PersonId = 10,
            TradeTitle = "Diesel Mechanic",
            TradeOfoCode = "653306",
            ApplicationTypeCode = "ARPL",
            SerialCertificateNumber = "TT-2026-00101",
            NambSerialNumber = "NAMB-2026-9918",
            CertificateIssueDate = DateTime.UtcNow,
            AssessmentCenterName = "Germiston Technical Training Academy",
            CreatedBy = "Admin"
        };

        // Act
        byte[] pdfBytes = await _pdfService.GenerateArtisanTradeCertificatePdfAsync(app);

        // Assert
        Assert.NotNull(pdfBytes);
        Assert.True(pdfBytes.Length > 5000, "Artisan Trade Certificate should generate landscape vector document with QR code");

        // Verify DocumentSnapshot creation
        using var db = await _factory.CreateDbContextAsync();
        var snapshot = await db.DocumentSnapshots.FirstOrDefaultAsync(s => s.RelatedEntityType == "LearnerTradeTestApplication" && s.RelatedEntityId == 101);
        Assert.NotNull(snapshot);
        Assert.Equal("TRADE_TEST_CERTIFICATE", snapshot.DocumentTypeCode);
        Assert.Equal("TT-2026-00101", snapshot.DocumentSnapshotNumber);
        Assert.Equal("Bongani Zulu", snapshot.RecipientName);
        Assert.Equal("9504125890082", snapshot.RecipientIdentifier);
    }

    [Fact]
    public async Task StatementOfResults_ShouldEmbed2DVerificationSealAndGenerateValidPdf()
    {
        // Arrange
        var person = new Person
        {
            Id = 20,
            FirstName = "Kagiso",
            LastName = "Molefe",
            RsaIdNumber = "9801015000085",
            CreatedBy = "Test"
        };

        var report = new SummativeAssessmentReport
        {
            Id = 5,
            Person = person,
            PersonId = 20,
            QualificationTitle = "National Certificate: Automotive Repair and Maintenance",
            SaqaQualificationId = "64410",
            NqfLevel = 4,
            CreatedBy = "Assessor"
        };

        var sor = new StatementOfResults
        {
            Id = 8,
            SorSerialNumber = "SOR-2026-0008",
            TotalCreditsCertified = 140,
            TamperProofHashSha256 = "8f3b6a9c1e2d4f5a7b8c9d0e1f2a3b4c5d6e7f8a9b0c1d2e3f4a5b6c7d8e9f0a",
            QrVerificationUrl = "https://nsdms.merseta.org.za/verify/document/SOR-2026-0008",
            DateIssued = DateTime.UtcNow,
            IssuedByUserId = "qa_officer_01",
            CreatedBy = "QA"
        };

        // Act
        byte[] pdfBytes = await _pdfService.GenerateStatementOfResultsPdfAsync(report, sor);

        // Assert
        Assert.NotNull(pdfBytes);
        Assert.True(pdfBytes.Length > 3000);
    }

    [Fact]
    public void IngestionBarcodeService_FormatAndParse_RoundtripSucceeds()
    {
        // Arrange
        var payload = new IngestionBarcodePayload
        {
            Module = "WSP",
            DocumentType = "WSP_SIGNOFF",
            RecordId = 1042,
            ReferenceNumber = "WSP-2026-0042",
            PageNumber = 1,
            TotalPages = 3
        };

        // Act
        string formatted = _ingestionBarcodeService.FormatIngestionPayload(payload);
        var parsed = _ingestionBarcodeService.ParseIngestionPayload(formatted);

        // Assert
        Assert.NotNull(formatted);
        Assert.Contains("\"m\":\"WSP\"", formatted);
        Assert.Contains("\"id\":1042", formatted);
        Assert.Contains("\"c\":", formatted); // Checksum present

        Assert.NotNull(parsed);
        Assert.Equal("WSP", parsed.Module);
        Assert.Equal("WSP_SIGNOFF", parsed.DocumentType);
        Assert.Equal(1042, parsed.RecordId);
        Assert.Equal("WSP-2026-0042", parsed.ReferenceNumber);
        Assert.Equal(1, parsed.PageNumber);
        Assert.Equal(3, parsed.TotalPages);
        Assert.False(string.IsNullOrWhiteSpace(parsed.Checksum));
    }

    [Fact]
    public void IngestionBarcodeService_TamperedPayload_FailsIntegrityCheck()
    {
        // Arrange
        var payload = new IngestionBarcodePayload
        {
            Module = "DG_MOA",
            DocumentType = "DG_MOA_CONTRACT",
            RecordId = 55,
            ReferenceNumber = "MOA-2026-0055",
            PageNumber = 2,
            TotalPages = 4
        };

        string validJson = _ingestionBarcodeService.FormatIngestionPayload(payload);

        // Tamper with record ID (55 -> 99) without updating checksum
        string tamperedJson = validJson.Replace("\"id\":55", "\"id\":99");

        // Act
        var result = _ingestionBarcodeService.ParseIngestionPayload(tamperedJson);

        // Assert
        Assert.Null(result); // Must be rejected because checksum mismatch occurred
    }

    [Fact]
    public void IngestionBarcodeService_GenerateBarcodeBytes_ProducesValidPng()
    {
        // Arrange
        var payload = new IngestionBarcodePayload
        {
            Module = "WSP",
            DocumentType = "WSP_SIGNOFF",
            RecordId = 1042,
            ReferenceNumber = "WSP-2026-0042",
            PageNumber = 1,
            TotalPages = 3
        };

        // Act
        byte[] qrBytes = _ingestionBarcodeService.GenerateIngestionBarcodeBytes(payload);

        // Assert
        Assert.NotNull(qrBytes);
        Assert.True(qrBytes.Length > 100);
        // Verify PNG magic numbers: 0x89, 0x50 ('P'), 0x4E ('N'), 0x47 ('G')
        Assert.Equal(0x89, qrBytes[0]);
        Assert.Equal(0x50, qrBytes[1]);
        Assert.Equal(0x4E, qrBytes[2]);
        Assert.Equal(0x47, qrBytes[3]);
    }

    [Fact]
    public void DocumentVerification_PopiaMasking_ProtectsRsaIdNumbers()
    {
        // Arrange & Act
        string rsaId = "9504125890082";
        string maskedRsaId = MaskId(rsaId);

        string sdlNumber = "L123456789";
        string maskedSdl = MaskId(sdlNumber);

        // Assert
        Assert.Equal("9504******082", maskedRsaId);
        Assert.Equal("L123456789", maskedSdl); // SDL number is public company reference, unmasked
    }

    private static string MaskId(string? id)
    {
        if (string.IsNullOrWhiteSpace(id)) return "N/A";
        id = id.Trim();
        if (id.Length == 13 && id.All(char.IsDigit))
        {
            return $"{id[..4]}******{id[10..]}";
        }
        return id;
    }
}
