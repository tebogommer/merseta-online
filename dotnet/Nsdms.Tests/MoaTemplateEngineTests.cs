using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

public class MoaTemplateEngineTests
{
    private readonly TestDbContextFactory _factory;
    private readonly AuditService _audit;
    private readonly SystemConfigurationService _config;
    private readonly FeatureFlagService _featureFlags;
    private readonly QuestPdfDocumentService _pdfService;
    private readonly MoaTemplateEngineService _service;

    public MoaTemplateEngineTests()
    {
        _factory = new TestDbContextFactory($"MoaTemplateEngineDb_{Guid.NewGuid()}");
        var conf = new Microsoft.Extensions.Configuration.ConfigurationBuilder().Build();
        _audit = new AuditService(_factory);
        _config = new SystemConfigurationService(_factory, conf, _audit);
        _featureFlags = new FeatureFlagService(_factory, conf, _audit);
        _pdfService = new QuestPdfDocumentService(_factory, _featureFlags, _config);
        _service = new MoaTemplateEngineService(_factory, _audit, _pdfService);
    }

    [Fact]
    public async Task ClauseLibrary_CreateAndRetrieve_ShouldSucceed()
    {
        // Arrange
        var clause = new MoaClause
        {
            ClauseCode = "CLAUSE-TEST-POPIA",
            ClauseTitle = "POPIA Privacy Warranty",
            Category = "Compliance",
            ClauseContent = "The Grantee {{OrganisationName}} warrants compliance with POPIA for Grant {{GrantYear}}.",
            IsMandatory = true,
            IsActive = true
        };

        // Act
        var created = await _service.CreateClauseAsync(clause, "LegalOfficer");
        var retrieved = await _service.GetClauseByIdAsync(created.Id);
        var list = await _service.GetClausesAsync("Compliance");

        // Assert
        Assert.NotNull(retrieved);
        Assert.Equal("CLAUSE-TEST-POPIA", retrieved.ClauseCode);
        Assert.Contains(list, c => c.ClauseCode == "CLAUSE-TEST-POPIA");
    }

    [Fact]
    public async Task ClauseLibrary_DeleteReferencedClause_ShouldSoftDeactivate()
    {
        // Arrange
        var clause = await _service.CreateClauseAsync(new MoaClause
        {
            ClauseCode = "CLAUSE-REFERENCED",
            ClauseTitle = "Referenced Clause",
            Category = "General",
            ClauseContent = "Content...",
            IsActive = true
        }, "Admin");

        var template = await _service.CreateTemplateAsync(new MoaTemplate
        {
            TemplateCode = "DG-TEST-REF",
            TemplateTitle = "Test Template",
            FinancialYear = 2026,
            GrantTypeCode = "DiscretionaryGrant",
            VersionNumber = "1.0.0"
        }, "Admin");

        await _service.AddSectionAsync(template.Id, clause.Id, "1.0", "Sec 1", 10, true, "Admin");

        // Act
        var result = await _service.DeleteClauseAsync(clause.Id, "Admin");
        var updated = await _service.GetClauseByIdAsync(clause.Id);

        // Assert
        Assert.True(result);
        Assert.NotNull(updated);
        Assert.False(updated.IsActive); // Soft deactivated because it's referenced in a template
    }

    [Fact]
    public async Task Template_CreateAndApprove_ShouldUpdateLifecycleStatus()
    {
        // Arrange
        var template = await _service.CreateTemplateAsync(new MoaTemplate
        {
            TemplateCode = "DG-POLICY-2026",
            TemplateTitle = "2026/27 DG Standard Policy",
            FinancialYear = 2026,
            GrantTypeCode = "DiscretionaryGrant",
            LegalEntityType = "All",
            VersionNumber = "1.0.0",
            ApprovalStatus = "Draft"
        }, "LegalOfficer");

        Assert.Equal("Draft", template.ApprovalStatus);

        // Act
        var approved = await _service.ApproveTemplateAsync(template.Id, "LegalExecutive");

        // Assert
        Assert.Equal("Approved", approved.ApprovalStatus);
        Assert.Equal("LegalExecutive", approved.ApprovedBy);
        Assert.NotNull(approved.ApprovedAt);
        Assert.True(approved.IsActive);
    }

    [Fact]
    public async Task Template_AddAndReorderSections_ShouldMaintainSequence()
    {
        // Arrange
        var template = await _service.CreateTemplateAsync(new MoaTemplate
        {
            TemplateCode = "DG-ORDER-TEST",
            TemplateTitle = "Ordering Test Template",
            FinancialYear = 2026
        }, "Admin");

        var clauseA = await _service.CreateClauseAsync(new MoaClause { ClauseCode = "C-A", ClauseTitle = "Clause A", ClauseContent = "A" }, "Admin");
        var clauseB = await _service.CreateClauseAsync(new MoaClause { ClauseCode = "C-B", ClauseTitle = "Clause B", ClauseContent = "B" }, "Admin");
        var clauseC = await _service.CreateClauseAsync(new MoaClause { ClauseCode = "C-C", ClauseTitle = "Clause C", ClauseContent = "C" }, "Admin");

        await _service.AddSectionAsync(template.Id, clauseA.Id, "1.0", "Sec A", 10, true, "Admin");
        await _service.AddSectionAsync(template.Id, clauseB.Id, "2.0", "Sec B", 20, true, "Admin");
        var t = await _service.AddSectionAsync(template.Id, clauseC.Id, "3.0", "Sec C", 30, true, "Admin");

        Assert.Equal(3, t.Sections.Count);

        // Act - Reorder so C is first, then A, then B
        var sectionCId = t.Sections.First(s => s.MoaClauseId == clauseC.Id).Id;
        var sectionAId = t.Sections.First(s => s.MoaClauseId == clauseA.Id).Id;
        var sectionBId = t.Sections.First(s => s.MoaClauseId == clauseB.Id).Id;

        var reordered = await _service.ReorderSectionsAsync(template.Id, new List<int> { sectionCId, sectionAId, sectionBId }, "Admin");

        // Assert
        var orderedSections = reordered.Sections.OrderBy(s => s.SequenceOrder).ToList();
        Assert.Equal("C-C", orderedSections[0].MoaClause?.ClauseCode);
        Assert.Equal("C-A", orderedSections[1].MoaClause?.ClauseCode);
        Assert.Equal("C-B", orderedSections[2].MoaClause?.ClauseCode);
    }

    [Fact]
    public async Task DynamicResolution_ShouldMatchExactFinancialYearAndGrantType()
    {
        // Arrange
        var t2025 = await _service.CreateTemplateAsync(new MoaTemplate
        {
            TemplateCode = "DG-2025",
            TemplateTitle = "2025 Standard",
            FinancialYear = 2025,
            GrantTypeCode = "DiscretionaryGrant",
            VersionNumber = "1.0.0"
        }, "Admin");
        await _service.ApproveTemplateAsync(t2025.Id, "Admin");

        var t2026 = await _service.CreateTemplateAsync(new MoaTemplate
        {
            TemplateCode = "DG-2026",
            TemplateTitle = "2026 Standard",
            FinancialYear = 2026,
            GrantTypeCode = "DiscretionaryGrant",
            VersionNumber = "1.0.0"
        }, "Admin");
        await _service.ApproveTemplateAsync(t2026.Id, "Admin");

        // Act
        var resolved2025 = await _service.ResolveTemplateAsync(2025, "DiscretionaryGrant");
        var resolved2026 = await _service.ResolveTemplateAsync(2026, "DiscretionaryGrant");

        // Assert
        Assert.NotNull(resolved2025);
        Assert.Equal("DG-2025", resolved2025.TemplateCode);
        Assert.NotNull(resolved2026);
        Assert.Equal("DG-2026", resolved2026.TemplateCode);
    }

    [Fact]
    public async Task TokenAssembly_ShouldInterpolateAllVariablesAndMilestoneTable()
    {
        // Arrange
        using (var db = await _factory.CreateDbContextAsync())
        {
            var org = new Organisation { CompanyName = "Apex Engineering Works", TradingName = "Apex", SdlNumber = "L998877665" };
            db.Organisations.Add(org);
            await db.SaveChangesAsync();

            var app = new GrantApplication
            {
                OrganisationId = org.Id,
                ApplicationDate = new DateTime(2026, 4, 1),
                ProjectTitle = "Apprenticeship 2026",
                GrantTypeCode = "DiscretionaryGrant"
            };
            db.GrantApplications.Add(app);
            await db.SaveChangesAsync();

            var moa = new GrantMoa
            {
                GrantApplicationId = app.Id,
                MoaNumber = "MOA-2026-DG-9999",
                ContractStartDate = new DateTime(2026, 4, 1),
                ContractEndDate = new DateTime(2027, 3, 31),
                TotalContractValue = 500000.00m,
                MoaStatusCode = "Draft",
                Milestones = new List<GrantMoaMilestone>
                {
                    new GrantMoaMilestone { MilestoneNumber = 1, MilestoneTitle = "Inception Tranche", TranchePercentage = 50, TrancheAmount = 250000, MilestoneStatusCode = "Pending", TargetDueDate = new DateTime(2026, 6, 30) },
                    new GrantMoaMilestone { MilestoneNumber = 2, MilestoneTitle = "Final Closeout", TranchePercentage = 50, TrancheAmount = 250000, MilestoneStatusCode = "Pending", TargetDueDate = new DateTime(2027, 3, 31) }
                }
            };
            db.GrantMoas.Add(moa);
            await db.SaveChangesAsync();

            var clause = new MoaClause
            {
                ClauseCode = "CLAUSE-INTERPOLATION",
                ClauseTitle = "Interpolation Test",
                Category = "General",
                ClauseContent = "Agreement for {{OrganisationName}} (SDL: {{LevyNumber}}), Contract {{MoaNumber}}, Value: R {{TotalContractValue}}.\n\n{{TrancheScheduleTable}}"
            };
            db.MoaClauses.Add(clause);
            await db.SaveChangesAsync();

            var template = new MoaTemplate
            {
                TemplateCode = "DG-INTERP",
                TemplateTitle = "Interp Template",
                FinancialYear = 2026,
                GrantTypeCode = "DiscretionaryGrant",
                ApprovalStatus = "Approved",
                IsActive = true
            };
            db.MoaTemplates.Add(template);
            await db.SaveChangesAsync();

            db.MoaTemplateSections.Add(new MoaTemplateSection
            {
                MoaTemplateId = template.Id,
                MoaClauseId = clause.Id,
                SectionNumber = "1.0",
                SectionTitle = "Section 1",
                SequenceOrder = 10
            });
            await db.SaveChangesAsync();

            // Act
            var assembled = await _service.AssembleMoaMarkdownAsync(moa.Id, template.Id);

            // Assert
            Assert.Contains("Apex Engineering Works", assembled);
            Assert.Contains("L998877665", assembled);
            Assert.Contains("MOA-2026-DG-9999", assembled);
            Assert.Contains("500,000.00", assembled);
            Assert.Contains("Inception Tranche", assembled);
            Assert.Contains("R 250,000.00", assembled);
        }
    }

    [Fact]
    public async Task SnapshotFreezing_ShouldComputeValidSha256ChecksumAndVerifyIntegrity()
    {
        // Arrange
        int moaId;
        using (var db = await _factory.CreateDbContextAsync())
        {
            var org = new Organisation { CompanyName = "Zenith Technology", SdlNumber = "L112233445" };
            db.Organisations.Add(org);
            await db.SaveChangesAsync();

            var app = new GrantApplication
            {
                OrganisationId = org.Id,
                ApplicationDate = new DateTime(2026, 4, 1),
                ProjectTitle = "Robotics & Automation Training",
                GrantTypeCode = "DiscretionaryGrant"
            };
            db.GrantApplications.Add(app);
            await db.SaveChangesAsync();

            var moa = new GrantMoa
            {
                GrantApplicationId = app.Id,
                MoaNumber = "MOA-2026-DG-HASH-01",
                ContractStartDate = new DateTime(2026, 4, 1),
                ContractEndDate = new DateTime(2027, 3, 31),
                TotalContractValue = 1000000.00m,
                MoaStatusCode = "Draft"
            };
            db.GrantMoas.Add(moa);
            await db.SaveChangesAsync();
            moaId = moa.Id;

            var clause = new MoaClause
            {
                ClauseCode = "CLAUSE-HASH",
                ClauseTitle = "Hash Clause",
                ClauseContent = "The legal agreement between merSETA and {{OrganisationName}}."
            };
            db.MoaClauses.Add(clause);
            await db.SaveChangesAsync();

            var template = new MoaTemplate
            {
                TemplateCode = "DG-HASH-TMP",
                TemplateTitle = "Hash Template",
                FinancialYear = 2026,
                GrantTypeCode = "DiscretionaryGrant",
                ApprovalStatus = "Approved",
                VersionNumber = "2.1.0",
                IsActive = true
            };
            db.MoaTemplates.Add(template);
            await db.SaveChangesAsync();

            db.MoaTemplateSections.Add(new MoaTemplateSection
            {
                MoaTemplateId = template.Id,
                MoaClauseId = clause.Id,
                SectionNumber = "1.0",
                SectionTitle = "Preamble",
                SequenceOrder = 10
            });
            await db.SaveChangesAsync();
        }

        // Act
        var snapshot = await _service.FreezeAndIssueMoaSnapshotAsync(moaId, "ChiefLegalCounsel");
        var isValid = await _service.VerifySnapshotIntegrityAsync(snapshot.Id);

        // Assert
        Assert.NotNull(snapshot);
        Assert.Equal(64, snapshot.RenderedContentHash.Length); // 64 hex chars for SHA-256
        Assert.Equal("2.1.0", snapshot.TemplateVersionNumber);
        Assert.True(isValid);
    }
}
