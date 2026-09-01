using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Domain.Lookups;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class SicCodeChamberSectorIntelligenceTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, OrganisationService orgService, LevyService levyService, AnalyticsService analyticsService) CreateContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var orgService = new OrganisationService(factory, audit);
        var levyService = new LevyService(factory, audit);
        var analyticsService = new AnalyticsService(factory);
        return (factory, db, audit, orgService, levyService, analyticsService);
    }

    [Fact]
    public async Task CreateAsync_AutoCascadesChamberFromSicCode_WhenNoManualOverride()
    {
        // Arrange
        var (_, db, _, orgService, _, _) = CreateContext();

        var org = new Organisation
        {
            CompanyName = "BMW SA Rosslyn Assembly",
            SdlNumber = "L100000001",
            SicCode = "38400", // Automotive OEM
            IsManualChamberOverride = false
        };

        // Act
        var created = await orgService.CreateAsync(org, "TestUser");

        // Assert - Chamber should be auto-derived as AUTO from lookup.SicCodeType
        Assert.Equal("AUTO", created.ChamberCode);
        Assert.False(created.IsManualChamberOverride);
    }

    [Fact]
    public async Task SetManualChamberOverrideAsync_PreservesCustomChamber_WithAuditJustification()
    {
        // Arrange
        var (_, db, _, orgService, _, _) = CreateContext();

        var org = new Organisation
        {
            CompanyName = "Multidisciplinary Engineering Works",
            SdlNumber = "L100000002",
            SicCode = "38200", // Metal fabrication
            IsManualChamberOverride = false
        };
        var created = await orgService.CreateAsync(org, "TestUser");
        Assert.Equal("METAL", created.ChamberCode);

        // Act - Executive Board approves special cross-chamber assignment to Plastics
        var updated = await orgService.SetManualChamberOverrideAsync(
            created.Id,
            "PLASTICS",
            "Executive Board Resolution 2026/04: Primary facility shifted to composite polymer moulding",
            "BoardSecretary"
        );

        // Assert
        Assert.Equal("PLASTICS", updated.ChamberCode);
        Assert.True(updated.IsManualChamberOverride);
        Assert.Equal("BoardSecretary", updated.ChamberOverrideApprovedBy);
        Assert.Contains("Executive Board Resolution", updated.ChamberOverrideReason);
        Assert.NotNull(updated.ChamberOverrideDate);
    }

    [Fact]
    public async Task ImportLevyFileAsync_DetectsNonMersetaSicCode_AndDraftsInterSetaTransfer()
    {
        // Arrange
        var (_, db, _, orgService, levyService, _) = CreateContext();

        var org = new Organisation
        {
            CompanyName = "Civil Construction Ltd",
            SdlNumber = "L100000003",
            SicCode = "50100", // CETA Construction (SETA 05)
            ChamberCode = "OTHER"
        };
        await orgService.CreateAsync(org, "Admin");

        // Ingest SARS file containing CETA construction SIC code 50100
        var csvContent = "SDL_NO,SCHEME_YEAR,SIC_CODE,AMOUNT\nL100000003,2026,50100,100000";

        // Act
        var file = await levyService.ImportLevyFileAsync("SARS_OUT_OF_SCOPE.csv", csvContent, "FinanceAdmin");

        // Assert - Out-of-scope line flagged and audit record created
        Assert.Single(file.LineItems);
        var line = file.LineItems.First();
        Assert.True(line.IsOutOfScopeSeta);
        Assert.Equal("05", line.SetaCode);

        var audit = await db.SarsLevyReconAudits.FirstOrDefaultAsync(a => a.SdlNumber == "L100000003");
        Assert.NotNull(audit);
        Assert.Equal("OutOfScopeSeta", audit.DiscrepancyReasonCode);
        Assert.Equal("05", audit.CounterpartSetaCode);
        Assert.Equal("50100", audit.ActualSarsSicCode);

        var transfer = await db.InterSetaTransfers.FirstOrDefaultAsync(t => t.OrganisationId == org.Id);
        Assert.NotNull(transfer);
        Assert.Equal("05", transfer.OtherSetaCode);
        Assert.Equal("Outgoing", transfer.TransferType);
        Assert.Equal("Initiated", transfer.TransferStatusCode);
    }

    [Fact]
    public async Task ImportLevyFileAsync_DetectsSicCodeMismatch_BetweenSarsAndOrganisationMaster()
    {
        // Arrange
        var (_, db, _, orgService, levyService, _) = CreateContext();

        var org = new Organisation
        {
            CompanyName = "Toyota Component Stamping",
            SdlNumber = "L100000004",
            SicCode = "38420", // Auto parts
            ChamberCode = "AUTO"
        };
        await orgService.CreateAsync(org, "Admin");

        // Ingest SARS file where employer declares Metal fabrication SIC code 38200 instead of Auto 38420
        var csvContent = "SDL_NO,SCHEME_YEAR,SIC_CODE,AMOUNT\nL100000004,2026,38200,80000";

        // Act
        var file = await levyService.ImportLevyFileAsync("SARS_SIC_MISMATCH.csv", csvContent, "FinanceAdmin");

        // Assert - Discrepancy flagged in audit ledger
        Assert.Single(file.LineItems);
        var line = file.LineItems.First();
        Assert.True(line.HasSicCodeMismatch);

        var audit = await db.SarsLevyReconAudits.FirstOrDefaultAsync(a => a.SdlNumber == "L100000004" && a.DiscrepancyReasonCode == "SicCodeMismatch");
        Assert.NotNull(audit);
        Assert.Equal("38420", audit.ExpectedSicCode);
        Assert.Equal("38200", audit.ActualSarsSicCode);
        Assert.Equal("AUTO", audit.ExpectedChamberCode);
        Assert.Equal("METAL", audit.ActualSarsChamberCode);
        Assert.Equal("DiscrepancyFlagged", audit.AuditStatusCode);
    }

    [Fact]
    public async Task GetChamberGrantFinancialSummaryAsync_CalculatesChamberEnvelopesAndBurnRates()
    {
        // Arrange
        var (_, db, _, orgService, levyService, analyticsService) = CreateContext();

        // 1. Create organisations in Auto and Metal chambers
        var autoOrg = new Organisation { CompanyName = "Nissan SA", SdlNumber = "L100000005", SicCode = "38400" };
        var metalOrg = new Organisation { CompanyName = "Scaw Metals", SdlNumber = "L100000006", SicCode = "38100" };
        await orgService.CreateAsync(autoOrg, "Admin");
        await orgService.CreateAsync(metalOrg, "Admin");

        // 2. Ingest levies for Auto (R200,000) and Metal (R100,000)
        var csv = "SDL_NO,SCHEME_YEAR,SIC_CODE,AMOUNT\nL100000005,2026,38400,200000\nL100000006,2026,38100,100000";
        await levyService.ImportLevyFileAsync("CHAMBER_BURNS.csv", csv, "Admin");

        // 3. Create DG MOA commitment for Auto (R50,000)
        var autoApp = new GrantApplication
        {
            ApplicationNumber = "DG-2026-AUTO-01",
            OrganisationId = autoOrg.Id,
            RequestedAmount = 50000m,
            ApprovedAmount = 50000m,
            ApplicationStatusCode = "Approved"
        };
        db.GrantApplications.Add(autoApp);
        await db.SaveChangesAsync();

        var autoMoa = new GrantMoa
        {
            MoaNumber = "MOA-2026-AUTO-01",
            GrantApplicationId = autoApp.Id,
            TotalContractValue = 50000m,
            MoaStatusCode = "Active"
        };
        db.GrantMoas.Add(autoMoa);
        await db.SaveChangesAsync();

        // Act
        var summaries = await analyticsService.GetChamberGrantFinancialSummaryAsync("2026");

        // Assert
        Assert.NotEmpty(summaries);
        var autoSummary = summaries.First(s => s.ChamberCode == "AUTO");
        Assert.Equal(200000m, autoSummary.TotalGrossLevyCollected);
        Assert.Equal(40000m, autoSummary.MandatoryGrantRebateTarget); // 20%
        Assert.Equal(99000m, autoSummary.DiscretionaryGrantEnvelope); // 49.5%
        Assert.Equal(50000m, autoSummary.DiscretionaryGrantCommitted);
        Assert.Equal(49000m, autoSummary.UnallocatedReserveBalance); // 99k - 50k
        Assert.True(autoSummary.GrantBurnRatePercentage > 50.0); // ~50.5%

        var metalSummary = summaries.First(s => s.ChamberCode == "METAL");
        Assert.Equal(100000m, metalSummary.TotalGrossLevyCollected);
        Assert.Equal(20000m, metalSummary.MandatoryGrantRebateTarget);
        Assert.Equal(49500m, metalSummary.DiscretionaryGrantEnvelope);
        Assert.Equal(0m, metalSummary.DiscretionaryGrantCommitted);
        Assert.Equal(49500m, metalSummary.UnallocatedReserveBalance);
        Assert.Equal(0.0, metalSummary.GrantBurnRatePercentage);
    }
}
