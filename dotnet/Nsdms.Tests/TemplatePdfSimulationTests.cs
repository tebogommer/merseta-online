using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

public class TemplatePdfSimulationTests
{
    private readonly TestDbContextFactory _factory;
    private readonly AuditService _audit;
    private readonly SystemConfigurationService _config;
    private readonly FeatureFlagService _featureFlags;
    private readonly QuestPdfDocumentService _pdfService;
    private readonly EnterpriseDocumentTemplateService _docTemplateService;
    private readonly MoaTemplateEngineService _moaTemplateService;

    public TemplatePdfSimulationTests()
    {
        _factory = new TestDbContextFactory($"TemplatePdfSimulationDb_{Guid.NewGuid()}");
        var conf = new Microsoft.Extensions.Configuration.ConfigurationBuilder().Build();
        _audit = new AuditService(_factory);
        _config = new SystemConfigurationService(_factory, conf, _audit);
        _featureFlags = new FeatureFlagService(_factory, conf, _audit);
        _pdfService = new QuestPdfDocumentService(_factory, _featureFlags, _config);
        _docTemplateService = new EnterpriseDocumentTemplateService(_factory, _audit, _pdfService);
        _moaTemplateService = new MoaTemplateEngineService(_factory, _audit, _pdfService);
    }

    [Fact]
    public async Task UniversalDocumentTemplate_SimulationPdf_GeneratesValidPdfBytes()
    {
        // Arrange
        var clause1 = await _docTemplateService.CreateClauseAsync(new DocumentClause
        {
            ClauseCode = "DOC-SEC-01",
            ClauseTitle = "1.0 Purpose and Statutory Scope",
            Category = "MandatoryGrant",
            ClauseContent = "This document confirms approval for {{RecipientName}} (Identifier: {{RecipientIdentifier}}) for financial year {{FinancialYear}}.",
            IsMandatory = true,
            IsActive = true
        }, "Admin");

        var template = await _docTemplateService.CreateTemplateAsync(new DocumentTemplate
        {
            TemplateCode = "WSP-SIM-TEST-2026",
            TemplateTitle = "WSP Mandatory Grant Approval Notice",
            DocumentCategory = "MandatoryGrant",
            DocumentTypeCode = "WspApprovalLetter",
            FinancialYear = 2026,
            TargetEntityType = "Employer",
            VersionNumber = "1.0.0",
            ApprovalStatus = "Draft",
            IsActive = true,
            FooterDisclaimerText = "Official statutory notice issued in terms of the Skills Development Act."
        }, "Admin");

        await _docTemplateService.AddSectionAsync(template.Id, clause1.Id, "1.0", "Purpose and Scope", 10, true, "Admin");

        // Act
        var customTokens = new Dictionary<string, string>
        {
            ["RecipientName"] = "Apex Engineering Works (Pty) Ltd",
            ["RecipientIdentifier"] = "L998877665",
            ["FinancialYear"] = "2026"
        };

        var pdfBytes = await _docTemplateService.GenerateSimulatedPdfAsync(template.Id, customTokens, includeWatermark: true);

        // Assert
        Assert.NotNull(pdfBytes);
        Assert.True(pdfBytes.Length > 1000, "Generated PDF must contain valid binary content.");
        // Validate standard PDF magic bytes (%PDF)
        Assert.Equal(0x25, pdfBytes[0]); // %
        Assert.Equal(0x50, pdfBytes[1]); // P
        Assert.Equal(0x44, pdfBytes[2]); // D
        Assert.Equal(0x46, pdfBytes[3]); // F
    }

    [Fact]
    public async Task MoaTemplate_SimulationPdf_GeneratesValidPdfBytes()
    {
        // Arrange
        var moaClause = await _moaTemplateService.CreateClauseAsync(new MoaClause
        {
            ClauseCode = "MOA-CLAUSE-SIM",
            ClauseTitle = "Discretionary Grant Scope",
            Category = "DiscretionaryGrant",
            ClauseContent = "The Grantee {{EmployerName}} (SDL: {{SdlNumber}}) undertakes to deliver project {{ProjectTitle}} with value {{TotalContractValue}}.",
            IsMandatory = true,
            IsActive = true
        }, "LegalOfficer");

        var moaTemplate = await _moaTemplateService.CreateTemplateAsync(new MoaTemplate
        {
            TemplateCode = "DG-SIM-TEST-2026",
            TemplateTitle = "merSETA Discretionary Grant Agreement 2026/2027",
            FinancialYear = 2026,
            GrantTypeCode = "DiscretionaryGrant",
            LegalEntityType = "Employer",
            VersionNumber = "1.0.0",
            ApprovalStatus = "Draft",
            IsActive = true
        }, "LegalOfficer");

        await _moaTemplateService.AddSectionAsync(moaTemplate.Id, moaClause.Id, "1.0", "Project Scope", 10, true, "LegalOfficer");

        // Act
        var pdfBytes = await _moaTemplateService.GenerateSimulatedPdfAsync(moaTemplate.Id, null, includeWatermark: true);

        // Assert
        Assert.NotNull(pdfBytes);
        Assert.True(pdfBytes.Length > 1000, "Generated MoA simulation PDF must contain valid binary content.");
        // Validate standard PDF magic bytes (%PDF)
        Assert.Equal(0x25, pdfBytes[0]);
        Assert.Equal(0x50, pdfBytes[1]);
        Assert.Equal(0x44, pdfBytes[2]);
        Assert.Equal(0x46, pdfBytes[3]);
    }

    [Fact]
    public void ScenarioTokenProfiles_ProvideCompleteDefaultProfiles()
    {
        // Act
        var docProfiles = _docTemplateService.GetDefaultScenarioTokenProfiles();
        var moaProfiles = _moaTemplateService.GetDefaultScenarioTokenProfiles();

        // Assert
        Assert.Contains("LevyEmployer", docProfiles.Keys);
        Assert.Contains("NonLevySme", docProfiles.Keys);
        Assert.Contains("ArtisanCandidate", docProfiles.Keys);
        Assert.Contains("AccreditedSdp", docProfiles.Keys);

        Assert.Contains("LevyEmployer", moaProfiles.Keys);
        Assert.Contains("NonLevySme", moaProfiles.Keys);
        Assert.Contains("TvetCollege", moaProfiles.Keys);
        Assert.Contains("BursaryCandidacy", moaProfiles.Keys);

        Assert.Equal("Apex Engineering Works (Pty) Ltd", docProfiles["LevyEmployer"]["RecipientName"]);
        Assert.Equal("L998877665", moaProfiles["LevyEmployer"]["SdlNumber"]);
    }
}
