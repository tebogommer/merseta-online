using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Domain.Lookups;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

public class SarsLevyElectronicIngestionTests
{
    private (TestDbContextFactory Factory, IAuditService Audit) CreateServices()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var audit = new AuditService(factory);
        return (factory, audit);
    }

    [Fact]
    public void SarsLevyParser_DelimitedContent_ParsesHeaderAndLineItemsCorrectly()
    {
        var (factory, audit) = CreateServices();
        var service = new LevyService(factory, audit);

        // Standard Case 1 format: SDL|Year|Mandatory|Discretionary|Admin|Qcto|Interest|Penalty|Total|SIC
        string rawContent = 
            "SDL_NO|SCHEME_YEAR|MANDATORY|DISCRETIONARY|ADMIN|QCTO|INTEREST|PENALTY|TOTAL_AMOUNT|SIC_CODE\r\n" +
            "L123456789|2026|20000.00|49500.00|10500.00|500.00|0.00|0.00|100000.00|35100\r\n" +
            "L987654321|2026|100000.00\r\n"; // 3-column auto-split format

        var lines = service.ParseSarsFileContent(rawContent);

        Assert.Equal(2, lines.Count);
        Assert.Equal("L123456789", lines[0].SdlNumber);
        Assert.Equal(20000.00m, lines[0].MandatoryLevyAmount);
        Assert.Equal(49500.00m, lines[0].DiscretionaryLevyAmount);
        Assert.Equal(10500.00m, lines[0].AdminLevyAmount);
        Assert.Equal(100000.00m, lines[0].TotalLevyAmount);

        Assert.Equal("L987654321", lines[1].SdlNumber);
        Assert.Equal(20000.00m, lines[1].MandatoryLevyAmount); // 20% of 100k
        Assert.Equal(49500.00m, lines[1].DiscretionaryLevyAmount); // 49.5% of 100k
    }

    [Fact]
    public async Task SarsLevyIngestion_FullBatchImport_PerformsBoundaryDetectionAndCreatesAuditLogs()
    {
        var (factory, audit) = CreateServices();

        // Seed registered organisations and non-merSETA SIC codes
        using (var db = (NsdmsDbContext)await factory.CreateDbContextAsync())
        {
            var merSetaOrg = new Organisation
            {
                LegalName = "Toyota SA Manufacturing",
                SdlNumber = "L100000001",
                SicCode = "35100",
                ChamberCode = "AUTO"
            };
            var nonMerSetaOrg = new Organisation
            {
                LegalName = "Mining Corp Africa",
                SdlNumber = "L200000002",
                SicCode = "11100",
                ChamberCode = "MINING"
            };
            db.Organisations.AddRange(merSetaOrg, nonMerSetaOrg);

            // Add non-merSETA SIC if not already present
            if (!await db.SicCodeTypes.AnyAsync(s => s.Code == "11100"))
            {
                var sicOutOfScope = new SicCodeType { Code = "11100", SetaCode = "16", ChamberCode = "MQA", Name = "Mining Operations" };
                db.SicCodeTypes.Add(sicOutOfScope);
            }

            await db.SaveChangesAsync();
        }

        var service = new LevyService(factory, audit);

        string rawFile = 
            "L100000001|2026|40000.00|99000.00|21000.00|1000.00|0.00|0.00|200000.00|35100\r\n" +
            "L200000002|2026|20000.00|49500.00|10500.00|500.00|0.00|0.00|100000.00|11100\r\n";

        var levyFile = await service.ImportLevyFileAsync("SARS_2026_M04.dat", rawFile, "FinanceOfficer");

        Assert.NotNull(levyFile);
        Assert.Equal("Imported", levyFile.ImportStatusCode);
        Assert.Equal(2, levyFile.TotalRecords);
        Assert.Equal(300000.00m, levyFile.TotalAmount);

        // Verify database writes
        using (var db = (NsdmsDbContext)await factory.CreateDbContextAsync())
        {
            var lines = await db.LevyFileLines.Where(l => l.LevyFileId == levyFile.Id).ToListAsync();
            Assert.Equal(2, lines.Count);

            var outOfScopeLine = lines.First(l => l.SdlNumber == "L200000002");
            Assert.True(outOfScopeLine.IsOutOfScopeSeta);

            var auditDiscrepancy = await db.SarsLevyReconAudits.FirstOrDefaultAsync(a => a.SdlNumber == "L200000002");
            Assert.NotNull(auditDiscrepancy);
            Assert.Equal("OutOfScopeSeta", auditDiscrepancy.DiscrepancyReasonCode);
            Assert.Equal("16", auditDiscrepancy.CounterpartSetaCode);

            var interSetaTransfer = await db.InterSetaTransfers.FirstOrDefaultAsync(t => t.OtherSetaCode == "16");
            Assert.NotNull(interSetaTransfer);
            Assert.Equal("Initiated", interSetaTransfer.TransferStatusCode);
        }
    }
}
