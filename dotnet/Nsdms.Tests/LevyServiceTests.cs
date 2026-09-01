using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Domain.Lookups;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class LevyServiceTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, LevyService service) CreateTestContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var service = new LevyService(factory, audit);
        return (factory, db, audit, service);
    }

    [Fact]
    public async Task ParseSarsFileAsync_CsvContent_CalculatesDistributionsCorrectly()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        // Header + 2 lines with total amount
        // Mandatory = 20%, Discretionary = 49.5%, Admin = 10.5%, QCTO = 0.5%
        var fileContent = @"SDL_NO,SCHEME_YEAR,AMOUNT
L123456789,2026,10000.00
L987654321,2026,20000.00";

        // Act
        var result = await service.ParseSarsFileAsync(fileContent, "SARS_JULY_2026.csv", "LevyOfficer");

        // Assert
        Assert.True(result.Id > 0);
        Assert.Equal("SARS_JULY_2026.csv", result.FileName);
        Assert.Equal(2, result.TotalRecords);
        Assert.Equal(30000.00m, result.TotalAmount);

        var lines = await service.GetLineItemsAsync(result.Id);
        Assert.Equal(2, lines.Count);

        var line1 = lines.First(l => l.SdlNumber == "L123456789");
        Assert.Equal(2000.00m, line1.MandatoryLevyAmount);
        Assert.Equal(4950.00m, line1.DiscretionaryLevyAmount);
        Assert.Equal(1050.00m, line1.AdminLevyAmount);

        var line2 = lines.First(l => l.SdlNumber == "L987654321");
        Assert.Equal(4000.00m, line2.MandatoryLevyAmount);
        Assert.Equal(9900.00m, line2.DiscretionaryLevyAmount);
        Assert.Equal(2100.00m, line2.AdminLevyAmount);
    }

    [Fact]
    public async Task GetAllAsync_ReturnsAllImportedLevyFiles()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var csv1 = "SDL_NO,SCHEME_YEAR,AMOUNT\nL1,2026,1000";
        var csv2 = "SDL_NO,SCHEME_YEAR,AMOUNT\nL2,2026,2000";

        await service.ParseSarsFileAsync(csv1, "FILE_1.csv");
        await service.ParseSarsFileAsync(csv2, "FILE_2.csv");

        // Act
        var list = await service.GetAllAsync();

        // Assert
        Assert.Equal(2, list.Count);
    }

    [Fact]
    public async Task ReconcileLevyFileAsync_MarksLineItemsReconciledAndUpdatesStatus()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        db.Organisations.AddRange(
            new Organisation { CompanyName = "Org 1", SdlNumber = "L123456789" },
            new Organisation { CompanyName = "Org 2", SdlNumber = "L987654321" }
        );
        await db.SaveChangesAsync();

        var csv = "SDL_NO,SCHEME_YEAR,AMOUNT\nL123456789,2026,5000\nL987654321,2026,7500";
        var file = await service.ParseSarsFileAsync(csv, "SARS_AUG_2026.csv");

        // Act
        var result = await service.ReconcileLevyFileAsync(file.Id, "FinanceManager");

        // Assert
        Assert.Equal(2, result.ReconciledCount);
        var inDb = await service.GetByIdAsync(file.Id);
        Assert.NotNull(inDb);
        Assert.Equal("FullyReconciled", inDb.StatusCode);

        var lines = await service.GetLineItemsAsync(file.Id);
        Assert.All(lines, l => Assert.True(l.IsReconciled));
    }

    [Fact]
    public async Task DeleteAsync_DeletesFileAndCascadesLineItems()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var csv = "SDL_NO,SCHEME_YEAR,AMOUNT\nL111,2026,1000";
        var file = await service.ParseSarsFileAsync(csv, "FILE_TO_DELETE.csv");

        // Act
        var deleted = await service.DeleteAsync(file.Id, "Deleter");

        // Assert
        Assert.True(deleted);
        var inDb = await service.GetByIdAsync(file.Id);
        Assert.Null(inDb);

        var lines = await service.GetLineItemsAsync(file.Id);
        Assert.Empty(lines);
    }

    [Fact]
    public async Task GetLevyDeviationReportAsync_CalculatesStandardDeviationAndFlagsInconsistentContributors()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        db.Organisations.Add(new Organisation
        {
            CompanyName = "Toyota Manufacturing SA",
            SdlNumber = "L100000001",
            ChamberCode = "AUTO"
        });
        await db.SaveChangesAsync();

        // Month 1: 50,000 | Month 2: 120,000 | Month 3: 40,000 (Average = 70,000, high variance)
        var csv1 = "SDL_NO,SCHEME_YEAR,AMOUNT\nL100000001,2026,50000";
        var csv2 = "SDL_NO,SCHEME_YEAR,AMOUNT\nL100000001,2026,120000";
        var csv3 = "SDL_NO,SCHEME_YEAR,AMOUNT\nL100000001,2026,40000";

        await service.ParseSarsFileAsync(csv1, "M1.csv");
        await service.ParseSarsFileAsync(csv2, "M2.csv");
        await service.ParseSarsFileAsync(csv3, "M3.csv");

        // Act
        var deviations = await service.GetLevyDeviationReportAsync("2026", "AUTO");

        // Assert
        Assert.Single(deviations);
        var reportItem = deviations[0];
        Assert.Equal("L100000001", reportItem.SdlNumber);
        Assert.Equal("Toyota Manufacturing SA", reportItem.OrganisationName);
        Assert.Equal("AUTO", reportItem.ChamberCode);
        Assert.Equal(210000m, reportItem.TotalLevy);
        Assert.Equal(70000m, reportItem.AverageMonthlyLevy);
        Assert.True(reportItem.DeviationPercentage >= 20.0m);
        Assert.True(reportItem.IsInconsistentContributor);
        Assert.Equal("Inconsistent", reportItem.LevyStatus);
    }

    [Fact]
    public async Task GetChamberLevyBreakdownAsync_AggregatesChambersCorrectly()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        db.Organisations.AddRange(
            new Organisation { CompanyName = "Metal Co", SdlNumber = "L200000001", ChamberCode = "METAL" },
            new Organisation { CompanyName = "Plastics Co", SdlNumber = "L300000001", ChamberCode = "PLASTICS" }
        );
        await db.SaveChangesAsync();

        var csv = "SDL_NO,SCHEME_YEAR,AMOUNT\nL200000001,2026,100000\nL300000001,2026,50000";
        await service.ParseSarsFileAsync(csv, "CHAMBER_BATCH.csv");

        // Act
        var breakdowns = await service.GetChamberLevyBreakdownAsync("2026");

        // Assert
        Assert.NotEmpty(breakdowns);
        var metal = breakdowns.First(b => b.ChamberCode == "METAL");
        Assert.Equal(1, metal.EmployerCount);
        Assert.Equal(100000m, metal.TotalGrossLevy);
        Assert.Equal(20000m, metal.MandatoryGrantPortion);
        Assert.Equal(49500m, metal.DiscretionaryGrantPortion);
        Assert.Equal(10500m, metal.AdminPortion);
        Assert.Equal(500m, metal.QctoPortion);

        var plastics = breakdowns.First(b => b.ChamberCode == "PLASTICS");
        Assert.Equal(1, plastics.EmployerCount);
        Assert.Equal(50000m, plastics.TotalGrossLevy);
        Assert.Equal(10000m, plastics.MandatoryGrantPortion);
        Assert.Equal(24750m, plastics.DiscretionaryGrantPortion);
    }

    [Fact]
    public async Task SchemeYearCalculation_SaveAndRetrieve_PersistsConfiguration()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var config = new SarsSchemeYearCalculation
        {
            SchemeYear = "2027",
            MandatoryPercentage = 20.0m,
            DiscretionaryPercentage = 49.5m,
            AdminPercentage = 10.5m,
            QctoPercentage = 0.5m,
            AllowReturnsMandatory = true,
            AllowInvoicesMandatory = false,
            StatusCode = "Active",
            Notes = "2027 Gazetted Skills Scheme Year Rates"
        };

        // Act
        var saved = await service.SaveSchemeYearCalculationAsync(config, "FinanceAdmin");
        var list = await service.GetSchemeYearCalculationsAsync();

        // Assert
        Assert.True(saved.Id > 0);
        var inList = list.FirstOrDefault(c => c.SchemeYear == "2027");
        Assert.NotNull(inList);
        Assert.Equal(80.5m, inList.TotalPercentage);
        Assert.False(inList.AllowInvoicesMandatory);
    }

    [Fact]
    public async Task GetChamberLevyBreakdownAsync_DynamicallyAdaptsToCustomChambersWithoutCodeChanges()
    {
        // Arrange - Setup a brand new 7th custom chamber added at runtime via admin lookups
        var (factory, db, audit, service) = CreateTestContext();

        db.ChamberTypes.Add(
            new ChamberType { Code = "AEROSPACE", Name = "Aerospace & Defence Manufacturing Chamber", Active = true }
        );

        db.Organisations.AddRange(
            new Organisation { CompanyName = "Bridgestone SA", SdlNumber = "L400000001", ChamberCode = "NEW_TYRE" },
            new Organisation { CompanyName = "Denel Aviation", SdlNumber = "L500000001", ChamberCode = "AEROSPACE" }
        );
        await db.SaveChangesAsync();

        var csv = "SDL_NO,SCHEME_YEAR,AMOUNT\nL400000001,2026,80000\nL500000001,2026,60000";
        await service.ParseSarsFileAsync(csv, "DYNAMIC_CHAMBERS.csv");

        // Act
        var breakdowns = await service.GetChamberLevyBreakdownAsync("2026");

        // Assert - Confirms that all 6 default chambers PLUS the new 7th Aerospace chamber are dynamically aggregated
        Assert.True(breakdowns.Count >= 7);
        var tyreChamber = breakdowns.FirstOrDefault(b => b.ChamberCode == "NEW_TYRE");
        Assert.NotNull(tyreChamber);
        Assert.Equal(80000m, tyreChamber.TotalGrossLevy);
        Assert.Equal(1, tyreChamber.EmployerCount);

        var aerospaceChamber = breakdowns.FirstOrDefault(b => b.ChamberCode == "AEROSPACE");
        Assert.NotNull(aerospaceChamber);
        Assert.Equal("Aerospace & Defence Manufacturing Chamber", aerospaceChamber.ChamberName);
        Assert.Equal(60000m, aerospaceChamber.TotalGrossLevy);
        Assert.Equal(1, aerospaceChamber.EmployerCount);
    }
}
