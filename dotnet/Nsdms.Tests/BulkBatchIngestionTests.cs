using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

public class BulkBatchIngestionTests
{
    private readonly TestDbContextFactory _factory;
    private readonly SqlBulkBatchIngestionService _batchService;

    public BulkBatchIngestionTests()
    {
        _factory = new TestDbContextFactory($"BulkIngestDb_{Guid.NewGuid()}");
        _batchService = new SqlBulkBatchIngestionService(_factory);
    }

    [Fact]
    public async Task BulkIngestLevyLines_ShouldProcessThousandsOfRecordsWithMetrics()
    {
        // Arrange
        int fileId;
        using (var db = await _factory.CreateDbContextAsync())
        {
            var file = new LevyFile
            {
                FileName = "SARS_SDL_202604_BULK_TEST.txt",
                FileRef = "BATCH-2026-BULK-01",
                ImportStatusCode = "Processing"
            };
            db.LevyFiles.Add(file);
            await db.SaveChangesAsync();
            fileId = file.Id;
        }

        // Generate 6,000 synthetic records (spans multiple 5,000 chunk boundaries)
        var records = new List<LevyFileLine>();
        for (int i = 1; i <= 6000; i++)
        {
            records.Add(new LevyFileLine
            {
                SdlNumber = $"L{100000000 + i}",
                SchemeYear = "2026",
                MandatoryLevyAmount = 200.00m,
                DiscretionaryLevyAmount = 495.00m,
                AdminLevyAmount = 105.00m,
                QctoLevyAmount = 5.00m,
                InterestAmount = 0m,
                PenaltyAmount = 0m,
                TotalLevyAmount = 800.00m
            });
        }

        // Act
        var result = await _batchService.BulkIngestLevyLinesAsync(fileId, records, "SarsDataAdmin");

        // Assert
        Assert.Equal(6000, result.TotalRecordsProcessed);
        Assert.Equal(6000, result.SuccessCount);
        Assert.Equal(0, result.ErrorCount);
        Assert.Equal(4800000.00m, result.TotalAmountSum); // 6000 * 800.00
        Assert.True(result.ProcessingTime.TotalSeconds < 10); // High throughput

        // Verify count in database
        using (var db = await _factory.CreateDbContextAsync())
        {
            int count = db.LevyFileLines.Count(l => l.LevyFileId == fileId);
            Assert.Equal(6000, count);
        }
    }
}
