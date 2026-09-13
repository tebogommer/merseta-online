using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Services;
using QuestPDF.Fluent;
using QuestPDF.Helpers;
using QuestPDF.Infrastructure;
using Xunit;

namespace Nsdms.Tests;

public class DocumentTemplateVersioningAndStudioTests
{
    private readonly TestDbContextFactory _factory;
    private readonly AuditService _audit;
    private readonly SystemConfigurationService _config;
    private readonly FeatureFlagService _featureFlags;
    private readonly DocumentVerificationService _verificationService;
    private readonly EnterpriseDocumentTemplateService _templateService;
    private readonly QuestPdfDocumentService _pdfService;
    private readonly DocumentPlaceholderRegistry _placeholderRegistry;

    public DocumentTemplateVersioningAndStudioTests()
    {
        _factory = new TestDbContextFactory($"DocTemplateStudioDb_{Guid.NewGuid()}");
        var conf = new Microsoft.Extensions.Configuration.ConfigurationBuilder().Build();
        _audit = new AuditService(_factory);
        _config = new SystemConfigurationService(_factory, conf, _audit);
        _featureFlags = new FeatureFlagService(_factory, conf, _audit);
        _verificationService = new DocumentVerificationService(_factory, _audit, _config);
        _pdfService = new QuestPdfDocumentService(_factory, _featureFlags, _config, _verificationService);
        _placeholderRegistry = new DocumentPlaceholderRegistry();
        _templateService = new EnterpriseDocumentTemplateService(_factory, _audit, _pdfService, _placeholderRegistry);
    }

    [Fact]
    public async Task SingleActiveVersionInvariant_ApproveAndActivate_SupersedesPredecessorVersions()
    {
        // 1. Arrange: Create and activate version 1.0.0
        var v1 = new DocumentTemplate
        {
            TemplateCode = "WSP-APPROVAL-TEST",
            TemplateTitle = "WSP Approval Letter",
            DocumentCategory = "MandatoryGrant",
            DocumentTypeCode = "WspApprovalLetter",
            FinancialYear = 2026,
            VersionNumber = "1.0.0",
            TemplateBodyHtml = "<h2>WSP Approval v1.0</h2><p>Dear {{Employer.Name}}</p>",
            IsActive = true,
            ApprovalStatus = "Approved"
        };
        using (var db = await _factory.CreateDbContextAsync())
        {
            db.DocumentTemplates.Add(v1);
            await db.SaveChangesAsync();
        }

        // 2. Act: Branch new draft revision 1.1.0
        var v11 = await _templateService.CreateNewVersionAsync(
            v1.Id, 
            "1.1.0", 
            "Updated statutory wording for 2026", 
            "ComplianceOfficer");

        // Verify intermediate state: v1.0.0 is still active, v1.1.0 is draft and inactive
        Assert.False(v11.IsActive);
        Assert.Equal("Draft", v11.ApprovalStatus);
        Assert.Equal(v1.Id, v11.ParentTemplateId);

        using (var db = await _factory.CreateDbContextAsync())
        {
            var currentV1 = await db.DocumentTemplates.FindAsync(v1.Id);
            Assert.NotNull(currentV1);
            Assert.True(currentV1!.IsActive);
            Assert.Equal("Approved", currentV1.ApprovalStatus);
        }

        // 3. Act: Approve and activate v1.1.0
        var activatedV11 = await _templateService.ApproveAndActivateTemplateAsync(v11.Id, "LegalManager");

        // 4. Assert: v1.1.0 is now Active/Approved; v1.0.0 was atomically superseded
        Assert.True(activatedV11.IsActive);
        Assert.Equal("Approved", activatedV11.ApprovalStatus);

        using (var db = await _factory.CreateDbContextAsync())
        {
            var oldV1 = await db.DocumentTemplates.FindAsync(v1.Id);
            Assert.NotNull(oldV1);
            Assert.False(oldV1!.IsActive, "Predecessor template must not be active");
            Assert.Equal("Superseded", oldV1.ApprovalStatus);
            Assert.NotNull(oldV1.EffectiveTo);

            // Double check that ONLY one template with this code is active
            var activeCount = await _templateService.GetTemplatesAsync();
            var activeInFamily = activeCount.Where(t => t.TemplateCode == "WSP-APPROVAL-TEST" && t.IsActive).ToList();
            Assert.Single(activeInFamily);
            Assert.Equal("1.1.0", activeInFamily[0].VersionNumber);
        }
    }

    [Fact]
    public async Task CreateNewVersion_ClonesMetadataAndHtmlBody_InDraftState()
    {
        // Arrange
        var source = new DocumentTemplate
        {
            TemplateCode = "TRADE-CERT-TEST",
            TemplateTitle = "Artisan Trade Test Certificate",
            DocumentCategory = "TradeTest",
            DocumentTypeCode = "TradeTestCertificate",
            FinancialYear = 2026,
            VersionNumber = "1.0.0",
            TemplateBodyHtml = "<h2>Certificate</h2><p>Learner: {{Learner.FullName}}</p>",
            FooterDisclaimerText = "Statutory notice",
            IsActive = true,
            ApprovalStatus = "Approved"
        };
        using (var db = await _factory.CreateDbContextAsync())
        {
            db.DocumentTemplates.Add(source);
            await db.SaveChangesAsync();
        }

        // Act
        var revision = await _templateService.CreateNewVersionAsync(
            source.Id,
            "1.2.0",
            "Added NAMB logo and updated trade terminology",
            "AdminUser");

        // Assert
        Assert.NotNull(revision);
        Assert.Equal("TRADE-CERT-TEST", revision.TemplateCode);
        Assert.Equal("1.2.0", revision.VersionNumber);
        Assert.Equal("Draft", revision.ApprovalStatus);
        Assert.False(revision.IsActive);
        Assert.Equal(source.Id, revision.ParentTemplateId);
        Assert.Equal(source.TemplateBodyHtml, revision.TemplateBodyHtml);
        Assert.Equal(source.FooterDisclaimerText, revision.FooterDisclaimerText);
        Assert.Equal("Added NAMB logo and updated trade terminology", revision.VersionNotes);
    }

    [Fact]
    public async Task CreateNewVersion_DuplicateVersionNumber_ThrowsException()
    {
        // Arrange
        var source = new DocumentTemplate
        {
            TemplateCode = "ETQA-ACCRED-TEST",
            TemplateTitle = "SDP Accreditation",
            DocumentCategory = "EtqaAccreditation",
            DocumentTypeCode = "AccreditationCertificate",
            FinancialYear = 2026,
            VersionNumber = "1.0.0",
            IsActive = true,
            ApprovalStatus = "Approved"
        };
        using (var db = await _factory.CreateDbContextAsync())
        {
            db.DocumentTemplates.Add(source);
            await db.SaveChangesAsync();
        }

        // Act & Assert: Trying to create revision with existing version "1.0.0" must fail
        await Assert.ThrowsAsync<InvalidOperationException>(() =>
            _templateService.CreateNewVersionAsync(source.Id, "1.0.0", "Invalid duplicate version", "Admin"));
    }

    [Fact]
    public void DocumentPlaceholderRegistry_ExtractAndValidate_IdentifiesValidAndInvalidTokens()
    {
        // Valid content
        string validTemplate = "<h2>Notice</h2><p>Dear {{Employer.Name}}, regarding SDL {{Employer.SdlNumber}} for scheme year {{Wsp.SchemeYear}}.</p>";
        var validResult = _placeholderRegistry.ValidateTemplate(validTemplate);
        Assert.True(validResult.IsValid);
        Assert.Empty(validResult.UnknownTokens);
        Assert.Contains("Employer.Name", validResult.UsedTokens);
        Assert.Contains("Employer.SdlNumber", validResult.UsedTokens);
        Assert.Contains("Wsp.SchemeYear", validResult.UsedTokens);

        // Invalid content with unknown token
        string invalidTemplate = "<p>Hello {{Employer.Name}}, your code is {{TotallyFakePlaceholderKey}}.</p>";
        var invalidResult = _placeholderRegistry.ValidateTemplate(invalidTemplate);
        Assert.False(invalidResult.IsValid);
        Assert.Single(invalidResult.UnknownTokens);
        Assert.Equal("TotallyFakePlaceholderKey", invalidResult.UnknownTokens[0]);
    }

    [Fact]
    public void DocumentPlaceholderRegistry_Interpolate_ReplacesTokensAccurately()
    {
        string template = "Employer: {{Employer.Name}} (SDL: {{Employer.SdlNumber}}). Grant: {{Wsp.RebateAmount}}.";
        var tokens = new Dictionary<string, string>
        {
            ["Employer.Name"] = "Transnet Engineering",
            ["Employer.SdlNumber"] = "L123456789",
            ["Wsp.RebateAmount"] = "R 500,000.00"
        };

        string output = _placeholderRegistry.Interpolate(template, tokens);

        Assert.Contains("Transnet Engineering", output);
        Assert.Contains("L123456789", output);
        Assert.Contains("R 500,000.00", output);
        Assert.DoesNotContain("{{Employer.Name}}", output);
    }

    [Fact]
    public void HtmlToQuestPdfRenderer_RendersHtmlBlocksWithoutException()
    {
        // Arrange
        string html = @"
            <h2>STATUTORY ACCREDITATION CERTIFICATE</h2>
            <p>This certifies that <strong>Gauteng Training Institute</strong> has been granted full accreditation.</p>
            <ul>
                <li>Fitting &amp; Turning (NQF 4)</li>
                <li>Welding (NQF 3)</li>
            </ul>
            <table border=""1"">
                <tr><th>Metric</th><th>Status</th></tr>
                <tr><td>Site Audit</td><td>Passed</td></tr>
            </table>
            <div style=""background-color: #fbe9e7; border-left: 4px solid #d32f2f; padding: 10px;"">
                Notice: Periodic surveillance audits apply.
            </div>
            <hr />
        ";

        // Act: Generate QuestPDF document with HtmlToQuestPdfRenderer
        var document = Document.Create(container =>
        {
            container.Page(page =>
            {
                page.Size(PageSizes.A4);
                page.Margin(2, Unit.Centimetre);
                page.Content().Column(col =>
                {
                    HtmlToQuestPdfRenderer.RenderHtmlToColumn(col, html);
                });
            });
        });

        byte[] pdfBytes = document.GeneratePdf();

        // Assert
        Assert.NotNull(pdfBytes);
        Assert.True(pdfBytes.Length > 1000, "Generated PDF must contain valid byte stream");
        // PDF header magic bytes %PDF
        Assert.Equal(0x25, pdfBytes[0]); // %
        Assert.Equal(0x50, pdfBytes[1]); // P
        Assert.Equal(0x44, pdfBytes[2]); // D
        Assert.Equal(0x46, pdfBytes[3]); // F
    }

    [Fact]
    public async Task DocumentSnapshot_PreservesIssuanceTraceability_AcrossTemplateRevisions()
    {
        // 1. Arrange: Create template v1.0.0 and generate a snapshot with it
        var v1 = new DocumentTemplate
        {
            TemplateCode = "DG-MOA-TEST",
            TemplateTitle = "Discretionary Grant MoA",
            DocumentCategory = "DiscretionaryGrant",
            DocumentTypeCode = "DiscretionaryGrantMoa",
            FinancialYear = 2026,
            VersionNumber = "1.0.0",
            TemplateBodyHtml = "<h2>MoA Agreement v1.0</h2><p>Recipient: {{Employer.Name}}</p>",
            IsActive = true,
            ApprovalStatus = "Approved"
        };
        using (var db = await _factory.CreateDbContextAsync())
        {
            db.DocumentTemplates.Add(v1);
            await db.SaveChangesAsync();
        }

        // Issue document snapshot using v1.0.0
        var snapshotRequest = new DocumentSnapshotRequest
        {
            DocumentSnapshotNumber = "DOC-2026-DG-0001",
            DocumentTypeCode = "DiscretionaryGrantMoa",
            DocumentTemplateId = v1.Id,
            TemplateVersionNumber = v1.VersionNumber,
            RelatedEntityType = "GrantMoa",
            RelatedEntityId = 55,
            RecipientName = "Toyota SA Motors",
            RecipientIdentifier = "L778899001",
            AssembledContent = "<h2>MoA Agreement v1.0</h2><p>Recipient: Toyota SA Motors</p>",
            SignatoryName = "Executive Authority",
            SignatoryTitle = "Chief Executive Officer"
        };
        var issuedSnapshot = await _verificationService.CreateAndFreezeDocumentSnapshotAsync(snapshotRequest, "GrantOfficer");
        Assert.NotNull(issuedSnapshot);
        Assert.Equal(v1.Id, issuedSnapshot.DocumentTemplateId);
        Assert.Equal("1.0.0", issuedSnapshot.TemplateVersionNumber);

        // 2. Act: Branch and activate version 1.1.0 with changed text
        var v11 = await _templateService.CreateNewVersionAsync(v1.Id, "1.1.0", "New legal clauses", "LegalOfficer");
        v11.TemplateBodyHtml = "<h2>MoA Agreement v1.1 Revised</h2><p>Recipient: {{Employer.Name}}</p>";
        await _templateService.UpdateTemplateAsync(v11, "LegalOfficer");
        await _templateService.ApproveAndActivateTemplateAsync(v11.Id, "ExecutiveAuthority");

        // 3. Assert: Historical snapshot still references v1 and preserves original text and version
        using (var db = await _factory.CreateDbContextAsync())
        {
            var verifiedSnapshot = await db.DocumentSnapshots
                .Include(s => s.DocumentTemplate)
                .FirstOrDefaultAsync(s => s.Id == issuedSnapshot.Id);

            Assert.NotNull(verifiedSnapshot);
            Assert.Equal("1.0.0", verifiedSnapshot!.TemplateVersionNumber);
            Assert.Equal(v1.Id, verifiedSnapshot.DocumentTemplateId);
            Assert.Contains("MoA Agreement v1.0", verifiedSnapshot.RenderedContent);
            Assert.DoesNotContain("MoA Agreement v1.1 Revised", verifiedSnapshot.RenderedContent);
        }
    }
}
