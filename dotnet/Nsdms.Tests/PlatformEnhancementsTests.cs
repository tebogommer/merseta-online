using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

public class PlatformEnhancementsTests
{
    private readonly INsdmsDbContextFactory _dbFactory;

    public PlatformEnhancementsTests()
    {
        _dbFactory = new TestDbContextFactory($"TestDb_Enhancements_{Guid.NewGuid()}");
    }

    private async Task SeedDataAsync()
    {
        await using var db = await _dbFactory.CreateDbContextAsync();

        // 1. Seed Organisation
        var org = new Organisation
        {
            Id = 1,
            CompanyName = "Apex Automotive Manufacturing",
            SdlNumber = "L123456789",
            OrganisationStatusCode = "ACTIVE",
            LevyCategoryCode = "LEVY_PAYING",
            IsActive = true
        };
        db.Organisations.Add(org);

        // 2. Seed Training Provider
        var sdp = new TrainingProvider
        {
            Id = 1,
            OrganisationId = 1,
            AccreditationNumber = "SDP-2026-001",
            ProviderStatusCode = "ACCREDITED",
            IsActive = true
        };
        db.TrainingProviders.Add(sdp);

        // 4. Seed Grant Application
        var app = new GrantApplication
        {
            Id = 1,
            OrganisationId = 1,
            ProjectTitle = "Automotive Toolmaking Apprenticeship Programme",
            ApplicationStatusCode = "Approved",
            RequestedAmount = 1800000m
        };
        db.GrantApplications.Add(app);

        await db.SaveChangesAsync();
    }

    [Fact]
    public async Task BrandAssetService_SetOrganisationBrand_UpdatesBrandAndWritesAuditLog()
    {
        await SeedDataAsync();
        var brandService = new BrandAssetService(_dbFactory);

        var success = await brandService.SetOrganisationBrandAsync(1, "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg==", "#865300", "AdminUser");
        Assert.True(success);

        var logoUri = await brandService.GetLogoDataUriAsync(1, "Organisation");
        Assert.NotNull(logoUri);
        Assert.StartsWith("data:image/png;base64,", logoUri);

        await using var db = await _dbFactory.CreateDbContextAsync();
        var org = await db.Organisations.FirstOrDefaultAsync(o => o.Id == 1);
        Assert.NotNull(org?.LogoDocumentId);
        Assert.Equal("#865300", org?.BrandColorHex);

        var audit = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "Organisation" && a.ActionName == "UPDATE_BRAND_ASSETS");
        Assert.NotNull(audit);
        Assert.Equal("AdminUser", audit?.Actor);
    }

    [Fact]
    public async Task BrandAssetService_SetProviderBrand_UpdatesProviderLogoAndWritesAuditLog()
    {
        await SeedDataAsync();
        var brandService = new BrandAssetService(_dbFactory);

        var success = await brandService.SetProviderBrandAsync(1, "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg==", "AdminUser");
        Assert.True(success);

        var logoUri = await brandService.GetLogoDataUriAsync(1, "TrainingProvider");
        Assert.NotNull(logoUri);

        await using var db = await _dbFactory.CreateDbContextAsync();
        var audit = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "TrainingProvider" && a.ActionName == "UPDATE_BRAND_LOGO");
        Assert.NotNull(audit);
    }

    [Fact]
    public async Task ReportExportService_GenerateGrantMoaAgreementPdf_ProducesValidPdf()
    {
        await SeedDataAsync();
        await using var db = await _dbFactory.CreateDbContextAsync();
        var moa = new GrantMoa
        {
            Id = 1,
            GrantApplicationId = 1,
            MoaNumber = "MOA-2026-001",
            TotalContractValue = 1800000m,
            ContractStartDate = new DateTime(2026, 4, 1),
            ContractEndDate = new DateTime(2027, 3, 31),
            MoaStatusCode = "Active"
        };
        moa.Milestones.Add(new GrantMoaMilestone
        {
            Id = 1,
            GrantMoaId = 1,
            MilestoneNumber = 1,
            MilestoneTitle = "Learner Registration & Contract Signing",
            TranchePercentage = 30,
            TrancheAmount = 540000m,
            TargetDueDate = new DateTime(2026, 5, 30)
        });
        db.GrantMoas.Add(moa);
        await db.SaveChangesAsync();

        var exportService = new ReportExportService(_dbFactory);
        var pdfBytes = await exportService.GenerateGrantMoaAgreementPdfAsync(1);

        Assert.NotNull(pdfBytes);
        Assert.True(pdfBytes.Length > 500);

        var header = System.Text.Encoding.ASCII.GetString(pdfBytes.Take(5).ToArray());
        Assert.Equal("%PDF-", header);
    }

    [Fact]
    public async Task ReportExportService_GenerateSarsLevyReconCsv_ProducesValidCsv()
    {
        await SeedDataAsync();
        await using var db = await _dbFactory.CreateDbContextAsync();
        db.LevyFiles.Add(new LevyFile
        {
            Id = 1,
            FileRef = "SARS-2026-04",
            FileName = "SARS_LEVY_202604.TXT",
            ImportDate = new DateTime(2026, 4, 1),
            TotalAmount = 5000000m,
            TotalRecords = 120,
            ImportStatusCode = "PROCESSED"
        });
        await db.SaveChangesAsync();

        var exportService = new ReportExportService(_dbFactory);
        var csvBytes = await exportService.GenerateSarsLevyReconCsvAsync("2026");

        Assert.NotNull(csvBytes);
        var csvContent = System.Text.Encoding.UTF8.GetString(csvBytes);
        Assert.Contains("LevyFileId,FileRef,FileName", csvContent);
        Assert.Contains("5000000.00", csvContent);
    }
}
