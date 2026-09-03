using System;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Domain.Lookups;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

public class SarsLevyStreamingPipelineTests
{
    private static (TestDbContextFactory, IAuditService, ISarsBulkStagingWriter, ILevyService, ISarsLevyStreamingPipeline) CreateServices()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var audit = new AuditService(factory);
        var writer = new SarsBulkStagingWriter(factory);
        var levyService = new LevyService(factory, audit);
        var preProcessor = new SarsCompliancePreProcessor(factory, levyService);
        var pipeline = new SarsLevyStreamingPipeline(factory, writer, levyService, audit, preProcessor);
        return (factory, audit, writer, levyService, pipeline);
    }

    [Fact]
    public async Task StreamingPipeline_DelimitedStream_ProcessesAndPromotesRecordsWithExactHareNiemeyerSplit()
    {
        var (factory, _, _, _, pipeline) = CreateServices();

        // Seed Organisation
        using (var db = (NsdmsDbContext)await factory.CreateDbContextAsync())
        {
            db.Organisations.Add(new Organisation
            {
                LegalName = "BMW Rosslyn Manufacturing",
                SdlNumber = "L123456789",
                SicCode = "35100",
                ChamberCode = "AUTO"
            });
            await db.SaveChangesAsync();
        }

        string rawContent =
            "SDL_NO|SCHEME_YEAR|MANDATORY|DISCRETIONARY|ADMIN|QCTO|INTEREST|PENALTY|TOTAL_AMOUNT|SIC_CODE\r\n" +
            "L123456789|2026|20000.00|49500.00|10500.00|500.00|0.00|0.00|100000.00|35100\r\n" +
            "L999999999|2026|50000.00\r\n"; // 3-column format triggering Hare-Niemeyer statutory split

        using var stream = new MemoryStream(Encoding.UTF8.GetBytes(rawContent));
        var result = await pipeline.ProcessSarsStreamAsync(stream, "SARS_2026_M05_STREAM.dat", "FinanceOfficer");

        Assert.NotNull(result);
        Assert.True(result.Success);
        Assert.Equal(2, result.TotalRecords);
        Assert.Equal(150000.00m, result.TotalAmount);
        Assert.False(string.IsNullOrWhiteSpace(result.DigitalSecuritySeal));
        Assert.Equal(64, result.DigitalSecuritySeal.Length);

        // Verify Database Records
        using (var db = (NsdmsDbContext)await factory.CreateDbContextAsync())
        {
            // Verify LevyFile
            var levyFile = await db.LevyFiles.Include(f => f.LineItems).FirstOrDefaultAsync(f => f.Id == result.LevyFileId);
            Assert.NotNull(levyFile);
            Assert.Equal("Imported", levyFile.ImportStatusCode);
            Assert.Equal(result.DigitalSecuritySeal, levyFile.DigitalSecuritySeal);
            Assert.Equal(2, levyFile.LineItems.Count);

            // Verify Staging Table Records
            var stagingRows = await db.SarsLevyStagings.Where(s => s.BatchIdentifier == result.BatchIdentifier).ToListAsync();
            Assert.Equal(2, stagingRows.Count);
            Assert.All(stagingRows, s => Assert.Equal("Promoted", s.StagingStatus));
            Assert.All(stagingRows, s => Assert.Equal(levyFile.Id, s.PromotedLevyFileId));

            // Verify Hare-Niemeyer 4-way split on second line item (50,000 ZAR)
            var splitLine = levyFile.LineItems.First(l => l.SdlNumber == "L999999999");
            Assert.Equal(10000.00m, splitLine.MandatoryLevyAmount);     // 20%
            Assert.Equal(24750.00m, splitLine.DiscretionaryLevyAmount); // 49.5%
            Assert.Equal(5250.00m, splitLine.AdminLevyAmount);          // 10.5%
            Assert.Equal(250.00m, splitLine.QctoLevyAmount);            // 0.5%
            Assert.Equal(40250.00m, splitLine.MandatoryLevyAmount + splitLine.DiscretionaryLevyAmount + splitLine.AdminLevyAmount + splitLine.QctoLevyAmount); // Exactly 80.5%
        }
    }

    [Fact]
    public async Task StreamingPipeline_TrailerControlTotal_MatchesAndMarksControlValidated()
    {
        var (factory, _, _, _, pipeline) = CreateServices();

        string rawContent =
            "SDL_NO|SCHEME_YEAR|MANDATORY|DISCRETIONARY|ADMIN|QCTO|INTEREST|PENALTY|TOTAL_AMOUNT|SIC_CODE\r\n" +
            "L100000001|2026|20000.00|49500.00|10500.00|500.00|0.00|0.00|100000.00|35100\r\n" +
            "L100000002|2026|40000.00|99000.00|21000.00|1000.00|0.00|0.00|200000.00|35100\r\n" +
            "TRAILER|2|300000.00\r\n";

        using var stream = new MemoryStream(Encoding.UTF8.GetBytes(rawContent));
        var result = await pipeline.ProcessSarsStreamAsync(stream, "SARS_2026_TRAILER_TEST.dat", "FinanceOfficer");

        Assert.True(result.IsControlValidated);

        using (var db = (NsdmsDbContext)await factory.CreateDbContextAsync())
        {
            var file = await db.LevyFiles.FindAsync(result.LevyFileId);
            Assert.NotNull(file);
            Assert.True(file.IsControlValidated);
            Assert.Equal(2, file.ControlRecordCount);
            Assert.Equal(300000.00m, file.ControlTotalAmount);
        }
    }

    [Fact]
    public async Task StreamingPipeline_TrailerControlCountMismatch_ThrowsExceptionAndAborts()
    {
        var (factory, _, _, _, pipeline) = CreateServices();

        // Trailer claims 5 records, but file only contains 2 (e.g. truncated file)
        string rawContent =
            "L100000001|2026|20000.00|49500.00|10500.00|500.00|0.00|0.00|100000.00|35100\r\n" +
            "L100000002|2026|40000.00|99000.00|21000.00|1000.00|0.00|0.00|200000.00|35100\r\n" +
            "TRAILER|5|300000.00\r\n";

        using var stream = new MemoryStream(Encoding.UTF8.GetBytes(rawContent));
        var ex = await Assert.ThrowsAnyAsync<InvalidOperationException>(() =>
            pipeline.ProcessSarsStreamAsync(stream, "SARS_TRUNCATED.dat", "FinanceOfficer"));

        Assert.Contains("control count mismatch", ex.Message);

        using (var db = (NsdmsDbContext)await factory.CreateDbContextAsync())
        {
            Assert.Empty(await db.LevyFiles.ToListAsync());
        }
    }

    [Fact]
    public async Task StreamingPipeline_TrailerControlAmountMismatch_ThrowsExceptionAndAborts()
    {
        var (factory, _, _, _, pipeline) = CreateServices();

        // Trailer claims R500,000, but file only contains R300,000
        string rawContent =
            "L100000001|2026|20000.00|49500.00|10500.00|500.00|0.00|0.00|100000.00|35100\r\n" +
            "L100000002|2026|40000.00|99000.00|21000.00|1000.00|0.00|0.00|200000.00|35100\r\n" +
            "TRAILER|2|500000.00\r\n";

        using var stream = new MemoryStream(Encoding.UTF8.GetBytes(rawContent));
        var ex = await Assert.ThrowsAnyAsync<InvalidOperationException>(() =>
            pipeline.ProcessSarsStreamAsync(stream, "SARS_AMOUNT_MISMATCH.dat", "FinanceOfficer"));

        Assert.Contains("monetary total mismatch", ex.Message);
    }

    [Fact]
    public async Task StreamingPipeline_DuplicateDigitalSecuritySeal_BlocksReImport()
    {
        var (factory, _, _, _, pipeline) = CreateServices();

        string rawContent =
            "L100000001|2026|20000.00|49500.00|10500.00|500.00|0.00|0.00|100000.00|35100\r\n";

        // First import succeeds
        using (var stream1 = new MemoryStream(Encoding.UTF8.GetBytes(rawContent)))
        {
            var res1 = await pipeline.ProcessSarsStreamAsync(stream1, "SARS_MONTH_01.dat", "FinanceOfficer");
            Assert.True(res1.Success);
        }

        // Second import of the exact same physical stream content must fail
        using (var stream2 = new MemoryStream(Encoding.UTF8.GetBytes(rawContent)))
        {
            var ex = await Assert.ThrowsAnyAsync<InvalidOperationException>(() =>
                pipeline.ProcessSarsStreamAsync(stream2, "SARS_MONTH_01_RENAME.dat", "FinanceOfficer"));

            Assert.Contains("Duplicate", ex.Message);
        }
    }

    [Fact]
    public async Task StreamingPipeline_InterSetaBoundaryAndSicMismatch_IdentifiesDiscrepancies()
    {
        var (factory, _, _, _, pipeline) = CreateServices();

        using (var db = (NsdmsDbContext)await factory.CreateDbContextAsync())
        {
            db.Organisations.Add(new Organisation
            {
                LegalName = "Nissan South Africa",
                SdlNumber = "L100000001",
                SicCode = "35100", // Master verified: AUTO
                ChamberCode = "AUTO"
            });

            db.SicCodeTypes.Add(new SicCodeType
            {
                Code = "11100",
                SetaCode = "16", // MQA (Mining)
                ChamberCode = "MQA",
                Name = "Coal Mining Operations"
            });

            db.SicCodeTypes.Add(new SicCodeType
            {
                Code = "35200",
                SetaCode = "17", // merSETA METAL
                ChamberCode = "METAL",
                Name = "Metal Fabrication"
            });

            await db.SaveChangesAsync();
        }

        // Line 1: Out-of-scope SETA (SIC 11100 -> SETA 16)
        // Line 2: SIC mismatch (Organisation verified 35100, but file declared 35200)
        string rawContent =
            "L999999999|2026|10000.00|24750.00|5250.00|250.00|0.00|0.00|50000.00|11100\r\n" +
            "L100000001|2026|20000.00|49500.00|10500.00|500.00|0.00|0.00|100000.00|35200\r\n";

        using var stream = new MemoryStream(Encoding.UTF8.GetBytes(rawContent));
        var result = await pipeline.ProcessSarsStreamAsync(stream, "SARS_BOUNDARY_TEST.dat", "FinanceOfficer");

        Assert.Equal(1, result.OutOfScopeCount);
        Assert.Equal(1, result.SicMismatchCount);

        using (var db = (NsdmsDbContext)await factory.CreateDbContextAsync())
        {
            var auditOutOfScope = await db.SarsLevyReconAudits.FirstOrDefaultAsync(a => a.DiscrepancyReasonCode == "OutOfScopeSeta");
            Assert.NotNull(auditOutOfScope);
            Assert.Equal("16", auditOutOfScope.CounterpartSetaCode);

            var auditSicMismatch = await db.SarsLevyReconAudits.FirstOrDefaultAsync(a => a.DiscrepancyReasonCode == "SicCodeMismatch");
            Assert.NotNull(auditSicMismatch);
            Assert.Equal("35100", auditSicMismatch.ExpectedSicCode);
            Assert.Equal("35200", auditSicMismatch.ActualSarsSicCode);
        }
    }

    [Fact]
    public async Task StreamingPipeline_LargeBatchChunking_HandlesMultipleThousandsWithoutParameterLimits()
    {
        var (factory, _, _, _, pipeline) = CreateServices();

        // Generate 2,500 records (exceeding SQL Server 2,100 parameter limit)
        var sb = new StringBuilder();
        for (int i = 1; i <= 2500; i++)
        {
            sb.AppendLine($"L{i:D9}|2026|1000.00");
        }

        using var stream = new MemoryStream(Encoding.UTF8.GetBytes(sb.ToString()));
        var result = await pipeline.ProcessSarsStreamAsync(stream, "SARS_LARGE_2500_BATCH.dat", "BatchWorker");

        Assert.True(result.Success);
        Assert.Equal(2500, result.TotalRecords);
        Assert.Equal(2500 * 1000.00m, result.TotalAmount);

        using (var db = (NsdmsDbContext)await factory.CreateDbContextAsync())
        {
            var count = await db.LevyFileLines.CountAsync(l => l.LevyFileId == result.LevyFileId);
            Assert.Equal(2500, count);

            var stagingCount = await db.SarsLevyStagings.CountAsync(s => s.BatchIdentifier == result.BatchIdentifier && s.StagingStatus == "Promoted");
            Assert.Equal(2500, stagingCount);
        }
    }
}
