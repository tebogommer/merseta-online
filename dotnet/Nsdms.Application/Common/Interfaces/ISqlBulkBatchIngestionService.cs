using Nsdms.Domain.Entities;

namespace Nsdms.Application.Common.Interfaces;

/// <summary>
/// Execution summary metrics for high-throughput batch ingestion operations.
/// </summary>
public class BatchIngestionResult
{
    public int TotalRecordsProcessed { get; set; }
    public int SuccessCount { get; set; }
    public int ErrorCount { get; set; }
    public TimeSpan ProcessingTime { get; set; }
    public decimal TotalAmountSum { get; set; }
    public string BatchExecutionSummary { get; set; } = string.Empty;
}

/// <summary>
/// High-throughput streaming batch ingestion service for 50,000+ row SARS SDL files and SETMIS staging extracts.
/// Bypasses EF Core ChangeTracker entity overhead to achieve sub-second database ingestion with minimal memory footprint.
/// </summary>
public interface ISqlBulkBatchIngestionService
{
    /// <summary>
    /// Ingests a collection of LevyFileLines directly into the database using chunked batch persistence.
    /// </summary>
    Task<BatchIngestionResult> BulkIngestLevyLinesAsync(int levyFileId, IEnumerable<LevyFileLine> lines, string currentUsername);
}
