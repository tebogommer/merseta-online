using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
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
}
