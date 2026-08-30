using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class LevyServiceTests
{
    private static NsdmsDbContext CreateInMemoryDbContext()
    {
        var options = new DbContextOptionsBuilder<NsdmsDbContext>()
            .UseInMemoryDatabase(databaseName: Guid.NewGuid().ToString())
            .Options;

        return new NsdmsDbContext(options);
    }

    [Fact]
    public async Task ParseSarsFileAsync_CsvContent_CalculatesDistributionsCorrectly()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new LevyService(db, audit);

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
        Assert.Equal(2000.00m, line1.MandatoryLevyAmount);     // 20% of 10000
        Assert.Equal(4950.00m, line1.DiscretionaryLevyAmount); // 49.5% of 10000
        Assert.Equal(1050.00m, line1.AdminLevyAmount);         // 10.5% of 10000
        Assert.Equal(50.00m, line1.QctoLevyAmount);            // 0.5% of 10000
        Assert.Equal(10000.00m, line1.TotalLevyAmount);
        Assert.False(line1.IsReconciled);

        var line2 = lines.First(l => l.SdlNumber == "L987654321");
        Assert.Equal(4000.00m, line2.MandatoryLevyAmount);     // 20% of 20000
        Assert.Equal(9900.00m, line2.DiscretionaryLevyAmount); // 49.5% of 20000
        Assert.Equal(2100.00m, line2.AdminLevyAmount);         // 10.5% of 20000
        Assert.Equal(100.00m, line2.QctoLevyAmount);           // 0.5% of 20000
        Assert.Equal(20000.00m, line2.TotalLevyAmount);

        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "LevyFile");
        Assert.NotNull(auditLog);
        Assert.Equal("ParseSarsFile", auditLog.ActionName);
    }

    [Fact]
    public async Task ParseSarsFileAsync_PipeDelimited_WithDetailedAmounts_ParsesExactAmounts()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new LevyService(db, audit);

        var fileContent = @"REF_NO|SCHEME_YEAR|MANDATORY|DISCRETIONARY|ADMIN|QCTO|INTEREST|PENALTY|TOTAL
L100200300|2026|2000.00|4950.00|1050.00|50.00|100.00|50.00|8200.00";

        // Act
        var result = await service.ParseSarsFileAsync(fileContent, "SARS_PIPE.txt", "LevyOfficer");

        // Assert
        Assert.Equal(1, result.TotalRecords);
        var lines = await service.GetLineItemsAsync(result.Id);
        Assert.Single(lines);

        var item = lines[0];
        Assert.Equal("L100200300", item.SdlNumber);
        Assert.Equal(2000.00m, item.MandatoryLevyAmount);
        Assert.Equal(4950.00m, item.DiscretionaryLevyAmount);
        Assert.Equal(1050.00m, item.AdminLevyAmount);
        Assert.Equal(50.00m, item.QctoLevyAmount);
        Assert.Equal(100.00m, item.InterestAmount);
        Assert.Equal(50.00m, item.PenaltyAmount);
        Assert.Equal(8200.00m, item.TotalLevyAmount);
    }

    [Fact]
    public async Task ReconcileEmployerLeviesAsync_MarksLinesAsReconciledAndLogsAudit()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new LevyService(db, audit);

        var fileContent = @"SDL_NO,SCHEME_YEAR,AMOUNT
L555666777,2026,50000.00
L555666777,2026,25000.00
L999999999,2026,10000.00";

        var file = await service.ParseSarsFileAsync(fileContent, "RECON_TEST.csv");

        // Act
        var reconciledCount = await service.ReconcileEmployerLeviesAsync("L555666777", "2026", "ReconOfficer");

        // Assert
        Assert.Equal(2, reconciledCount);

        var lines = await service.GetLineItemsAsync(file.Id);
        var reconciledLines = lines.Where(l => l.SdlNumber == "L555666777").ToList();
        var otherLines = lines.Where(l => l.SdlNumber == "L999999999").ToList();

        Assert.All(reconciledLines, l => Assert.True(l.IsReconciled));
        Assert.All(otherLines, l => Assert.False(l.IsReconciled));

        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.ActionName == "ReconcileEmployerLevies");
        Assert.NotNull(auditLog);
        Assert.Equal("ReconOfficer", auditLog.Actor);
    }
}
