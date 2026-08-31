using System.Diagnostics;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;

namespace Nsdms.Infrastructure.Services;

/// <summary>
/// Production high-throughput streaming batch ingestion service.
/// Optimized for large monthly SARS SDL levy files (50,000+ lines) and SETMIS staging extracts.
/// </summary>
public class SqlBulkBatchIngestionService : ISqlBulkBatchIngestionService
{
    private readonly INsdmsDbContextFactory _factory;
    private const int ChunkSize = 5000;

    public SqlBulkBatchIngestionService(INsdmsDbContextFactory factory)
    {
        _factory = factory;
    }

    public async Task<BatchIngestionResult> BulkIngestLevyLinesAsync(int levyFileId, IEnumerable<LevyFileLine> lines, string currentUsername)
    {
        var sw = Stopwatch.StartNew();
        int totalProcessed = 0;
        decimal totalAmount = 0m;

        using var db = await _factory.CreateDbContextAsync();
        if (db is DbContext efDb)
        {
            efDb.ChangeTracker.AutoDetectChangesEnabled = false;
        }

        var lineList = lines.ToList();
        var chunks = lineList.Chunk(ChunkSize);

        foreach (var chunk in chunks)
        {
            foreach (var line in chunk)
            {
                line.LevyFileId = levyFileId;
                line.CreatedAt = DateTime.UtcNow;
                line.CreatedBy = currentUsername;
                totalAmount += line.TotalLevyAmount;
            }

            await db.LevyFileLines.AddRangeAsync(chunk);
            await db.SaveChangesAsync();
            totalProcessed += chunk.Length;
        }

        if (db is DbContext efDbEnd)
        {
            efDbEnd.ChangeTracker.AutoDetectChangesEnabled = true;
        }
        sw.Stop();

        return new BatchIngestionResult
        {
            TotalRecordsProcessed = totalProcessed,
            SuccessCount = totalProcessed,
            ErrorCount = 0,
            ProcessingTime = sw.Elapsed,
            TotalAmountSum = totalAmount,
            BatchExecutionSummary = $"Successfully ingested {totalProcessed:N0} levy records in {sw.ElapsedMilliseconds}ms (Chunk Size: {ChunkSize:N0}). Total Amount: R {totalAmount:N2}."
        };
    }
}
