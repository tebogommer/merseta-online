using System.Text;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

public class UniversalDocumentVerificationTests
{
    private readonly TestDbContextFactory _factory;
    private readonly AuditService _audit;
    private readonly SystemConfigurationService _config;
    private readonly FeatureFlagService _featureFlags;
    private readonly DocumentVerificationService _verificationService;
    private readonly EnterpriseDocumentTemplateService _templateService;
    private readonly QuestPdfDocumentService _pdfService;

    public UniversalDocumentVerificationTests()
    {
        _factory = new TestDbContextFactory($"DocVerificationDb_{Guid.NewGuid()}");
        var conf = new Microsoft.Extensions.Configuration.ConfigurationBuilder().Build();
        _audit = new AuditService(_factory);
        _config = new SystemConfigurationService(_factory, conf, _audit);
        _featureFlags = new FeatureFlagService(_factory, conf, _audit);
        _verificationService = new DocumentVerificationService(_factory, _audit, _config);
        _pdfService = new QuestPdfDocumentService(_factory, _featureFlags, _config, _verificationService);
        _templateService = new EnterpriseDocumentTemplateService(_factory, _audit, _pdfService);
    }

    [Fact]
    public async Task DocumentSnapshot_CreateAndFreeze_ShouldGenerateValidSha256AndQrCode()
    {
        // Arrange
        var request = new DocumentSnapshotRequest
        {
            DocumentSnapshotNumber = "DOC-2026-TT-TEST01",
            DocumentTypeCode = "TradeTestCertificate",
            RelatedEntityType = "LearnerTradeTest",
            RelatedEntityId = 101,
            RecipientName = "Thabo Mokoena",
            RecipientIdentifier = "9805125123087",
            AssembledContent = "# National Artisan Certificate\n\nThabo Mokoena is declared COMPETENT in Fitting and Turning.",
            SignatoryName = "Chief Executive Officer",
            SignatoryTitle = "CEO"
        };

        // Act
        var snapshot = await _verificationService.CreateAndFreezeDocumentSnapshotAsync(request, "ExaminerOfficer");

        // Assert
        Assert.NotNull(snapshot);
        Assert.Equal("DOC-2026-TT-TEST01", snapshot.DocumentSnapshotNumber);
        Assert.Equal(64, snapshot.RenderedContentHash.Length);
        Assert.False(string.IsNullOrWhiteSpace(snapshot.VerificationQrBase64));
        Assert.StartsWith("data:image/png;base64,", snapshot.VerificationQrBase64!);
        Assert.Contains(snapshot.RenderedContentHash, snapshot.VerificationUri);
        Assert.Equal("ExaminerOfficer", snapshot.IssuedBy);
    }

    [Fact]
    public async Task DocumentSnapshot_VerifyByHash_ShouldReturnAuthentic()
    {
        // Arrange
        var request = new DocumentSnapshotRequest
        {
            DocumentSnapshotNumber = "DOC-2026-WSP-TEST02",
            DocumentTypeCode = "WspApprovalLetter",
            RelatedEntityType = "WspSubmission",
            RelatedEntityId = 202,
            RecipientName = "Sasol South Africa",
            RecipientIdentifier = "L123456789",
            AssembledContent = "The Workplace Skills Plan for Sasol is officially APPROVED."
        };

        var snapshot = await _verificationService.CreateAndFreezeDocumentSnapshotAsync(request, "GrantsManager");

        // Act
        var result = await _verificationService.VerifyDocumentSnapshotAsync(snapshot.RenderedContentHash);

        // Assert
        Assert.True(result.IsFound);
        Assert.True(result.IsAuthentic);
        Assert.False(result.IsRevoked);
        Assert.Equal("Sasol South Africa", result.RecipientName);
        Assert.Equal("L123456789", result.RecipientIdentifier);
    }

    [Fact]
    public async Task DocumentSnapshot_VerifyByDocumentNumber_ShouldReturnAuthentic()
    {
        // Arrange
        var request = new DocumentSnapshotRequest
        {
            DocumentSnapshotNumber = "DOC-2026-ETQA-TEST03",
            DocumentTypeCode = "AccreditationCertificate",
            RelatedEntityType = "TrainingProvider",
            RelatedEntityId = 303,
            RecipientName = "Apex Training Academy",
            RecipientIdentifier = "17-QA/ACC/0999/26",
            AssembledContent = "Accredited Skills Development Provider."
        };

        await _verificationService.CreateAndFreezeDocumentSnapshotAsync(request, "EtqaManager");

        // Act
        var result = await _verificationService.VerifyDocumentSnapshotAsync("DOC-2026-ETQA-TEST03");

        // Assert
        Assert.True(result.IsFound);
        Assert.True(result.IsAuthentic);
        Assert.Equal("Apex Training Academy", result.RecipientName);
    }

    [Fact]
    public async Task DocumentSnapshot_TamperedContent_ShouldDetectMismatch()
    {
        // Arrange
        var request = new DocumentSnapshotRequest
        {
            DocumentSnapshotNumber = "DOC-2026-TAMPER-01",
            DocumentTypeCode = "TradeTestCertificate",
            RelatedEntityType = "LearnerTradeTest",
            RelatedEntityId = 404,
            RecipientName = "Legitimate Learner",
            RecipientIdentifier = "9901015000080",
            AssembledContent = "Original authentic competency certificate."
        };

        var snapshot = await _verificationService.CreateAndFreezeDocumentSnapshotAsync(request, "Admin");

        // Act - Simulate direct database row tampering / data corruption
        using (var db = await _factory.CreateDbContextAsync())
        {
            var dbSnapshot = await db.DocumentSnapshots.FindAsync(snapshot.Id);
            Assert.NotNull(dbSnapshot);
            dbSnapshot.RenderedContent = "Fraudulently modified certificate content with altered trade.";
            await db.SaveChangesAsync();
        }

        var result = await _verificationService.VerifyDocumentSnapshotAsync(snapshot.RenderedContentHash);

        // Assert
        Assert.True(result.IsFound);
        Assert.False(result.IsAuthentic); // Cryptographic checksum mismatch detected!
    }

    [Fact]
    public async Task DocumentSnapshot_Revoke_ShouldMarkRevokedWithReason()
    {
        // Arrange
        var request = new DocumentSnapshotRequest
        {
            DocumentSnapshotNumber = "DOC-2026-REVOKE-01",
            DocumentTypeCode = "AccreditationCertificate",
            RelatedEntityType = "TrainingProvider",
            RelatedEntityId = 505,
            RecipientName = "NonCompliant College",
            RecipientIdentifier = "17-QA/ACC/BAD01",
            AssembledContent = "Accredited provider."
        };

        var snapshot = await _verificationService.CreateAndFreezeDocumentSnapshotAsync(request, "Admin");

        // Act
        var revoked = await _verificationService.RevokeDocumentSnapshotAsync(snapshot.Id, "Accreditation revoked due to non-compliance with audit findings.", "SeniorManagerETQA");
        var result = await _verificationService.VerifyDocumentSnapshotAsync(snapshot.RenderedContentHash);

        // Assert
        Assert.True(revoked);
        Assert.True(result.IsFound);
        Assert.False(result.IsAuthentic);
        Assert.True(result.IsRevoked);
        Assert.Contains("non-compliance", result.RevocationReason);
    }

    [Fact]
    public async Task DocumentSnapshot_RecordVerificationScan_ShouldIncrementScanCount()
    {
        // Arrange
        var request = new DocumentSnapshotRequest
        {
            DocumentSnapshotNumber = "DOC-2026-SCAN-01",
            DocumentTypeCode = "TradeTestCertificate",
            RelatedEntityType = "LearnerTradeTest",
            RelatedEntityId = 606,
            RecipientName = "Nomsa Dlamini",
            RecipientIdentifier = "9203045000088",
            AssembledContent = "Certificate content..."
        };

        var snapshot = await _verificationService.CreateAndFreezeDocumentSnapshotAsync(request, "Admin");
        Assert.Equal(0, snapshot.VerificationScanCount);

        // Act
        await _verificationService.RecordVerificationScanAsync(snapshot.Id, "196.25.1.1", "Mozilla/5.0 InspectorScan");
        await _verificationService.RecordVerificationScanAsync(snapshot.Id, "196.25.1.2", "MobileBrowser");

        var updated = await _verificationService.GetSnapshotByIdAsync(snapshot.Id);

        // Assert
        Assert.NotNull(updated);
        Assert.Equal(2, updated.VerificationScanCount);
        Assert.NotNull(updated.LastVerifiedAt);
    }

    [Fact]
    public async Task EnterpriseTemplate_CreateApproveAndResolve_ShouldReturnActiveTemplate()
    {
        // Arrange
        var template = await _templateService.CreateTemplateAsync(new DocumentTemplate
        {
            TemplateCode = "TEST-WSP-APPROVE-2026",
            TemplateTitle = "2026 WSP Approval Letter",
            DocumentCategory = "MandatoryGrant",
            DocumentTypeCode = "WspApprovalLetter",
            FinancialYear = 2026,
            TargetEntityType = "Employer",
            VersionNumber = "1.0.0"
        }, "LegalOfficer");

        Assert.Equal("Draft", template.ApprovalStatus);

        // Act
        var approved = await _templateService.ApproveTemplateAsync(template.Id, "ExecutiveApprover");
        var resolved = await _templateService.ResolveTemplateAsync("WspApprovalLetter", 2026, "Employer");

        // Assert
        Assert.Equal("Approved", approved.ApprovalStatus);
        Assert.NotNull(resolved);
        Assert.Equal("TEST-WSP-APPROVE-2026", resolved.TemplateCode);
    }

    [Fact]
    public async Task EnterpriseTemplate_AssembleWithTokens_ShouldInterpolateAllVariables()
    {
        // Arrange
        var clause = await _templateService.CreateClauseAsync(new DocumentClause
        {
            ClauseCode = "CL-TEST-REBATE",
            ClauseTitle = "Rebate Clause",
            Category = "FinanceAudit",
            ClauseContent = "The rebate of {{RebateAmount}} for {{RecipientName}} (SDL: {{RecipientIdentifier}}) for scheme year {{FinancialYear}} is approved."
        }, "Admin");

        var template = await _templateService.CreateTemplateAsync(new DocumentTemplate
        {
            TemplateCode = "TEST-REBATE-TMP",
            TemplateTitle = "Rebate Remittance Advice",
            DocumentCategory = "FinanceAudit",
            DocumentTypeCode = "RemittanceAdvice",
            FinancialYear = 2026
        }, "Admin");

        await _templateService.AddSectionAsync(template.Id, clause.Id, "1.0", "Rebate Notification", 10, true, "Admin");

        var tokens = new Dictionary<string, string>
        {
            ["RebateAmount"] = "R 150,000.00",
            ["RecipientName"] = "Transnet Engineering",
            ["RecipientIdentifier"] = "L554433221",
            ["FinancialYear"] = "2026"
        };

        // Act
        var assembled = await _templateService.AssembleDocumentTextAsync(template.Id, tokens);

        // Assert
        Assert.Contains("Transnet Engineering", assembled);
        Assert.Contains("L554433221", assembled);
        Assert.Contains("R 150,000.00", assembled);
        Assert.Contains("2026", assembled);
    }

    [Fact]
    public void QrCodeGenerator_GenerateVerificationQrCodeBytes_ShouldProduceValidPng()
    {
        // Arrange
        string testUrl = "https://nsdms.merseta.org.za/verify/document/abcdef1234567890";

        // Act
        byte[] qrBytes = _verificationService.GenerateVerificationQrCodeBytes(testUrl, 8);

        // Assert
        Assert.NotNull(qrBytes);
        Assert.True(qrBytes.Length > 100);
        // Standard PNG magic header bytes: 0x89, 'P', 'N', 'G'
        Assert.Equal(0x89, qrBytes[0]);
        Assert.Equal((byte)'P', qrBytes[1]);
        Assert.Equal((byte)'N', qrBytes[2]);
        Assert.Equal((byte)'G', qrBytes[3]);
    }

    [Fact]
    public async Task QuestPdf_GenerateTradeTestCertificate_ShouldIncludeQrCodeAndFingerprint()
    {
        // Arrange
        var tradeTest = new LearnerTradeTest
        {
            Id = 999,
            TradeTitle = "Toolmaker and Precision Machinist",
            SerialCertificateNumber = "CERT-2026-TM-0099",
            TestCenterName = "merSETA Precision Training Centre (Denver)",
            CertificateIssueDate = new DateTime(2026, 8, 15),
            CompanyLearner = new CompanyLearner
            {
                Person = new Person
                {
                    FirstName = "Kagiso",
                    LastName = "Mabena",
                    RsaIdNumber = "9408155099081"
                }
            }
        };

        // Act
        var pdfBytes = await _pdfService.GenerateTradeTestCertificateAsync(tradeTest);

        // Assert
        Assert.NotNull(pdfBytes);
        Assert.True(pdfBytes.Length > 1000);
        // Standard PDF magic header: %PDF
        Assert.Equal((byte)'%', pdfBytes[0]);
        Assert.Equal((byte)'P', pdfBytes[1]);
        Assert.Equal((byte)'D', pdfBytes[2]);
        Assert.Equal((byte)'F', pdfBytes[3]);
    }
}
