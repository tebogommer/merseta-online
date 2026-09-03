using System;
using System.IO;
using System.Threading;
using System.Threading.Tasks;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

/// <summary>
/// Result summary returned upon completion of reactive streaming SARS levy file ingestion and promotion.
/// </summary>
public record LevyStreamingResult(
    int LevyFileId,
    string BatchIdentifier,
    string FileName,
    string DigitalSecuritySeal,
    int TotalRecords,
    decimal TotalAmount,
    bool IsControlValidated,
    int OutOfScopeCount,
    int SicMismatchCount,
    long ProcessingDurationMs,
    bool Success,
    string StatusMessage
);

/// <summary>
/// Stated control record totals extracted from a SARS/DHET file trailer or control line.
/// </summary>
public record SarsControlTotals(
    int ExpectedRecordCount,
    decimal ExpectedTotalAmount
);

/// <summary>
/// Intermediate representation of an individually parsed SARS text file line.
/// </summary>
public record ParsedSarsStreamLine(
    int LineNumber,
    string RawRecord,
    string SdlNumber,
    string SchemeYear,
    string? SicCode,
    decimal MandatoryLevyAmount,
    decimal DiscretionaryLevyAmount,
    decimal AdminLevyAmount,
    decimal QctoLevyAmount,
    decimal InterestAmount,
    decimal PenaltyAmount,
    decimal TotalLevyAmount,
    bool IsTrailer = false,
    SarsControlTotals? TrailerTotals = null
);

/// <summary>
/// Interface for Option A: Reactive Streaming Pipeline with SqlBulkCopy and Staging Table.
/// Guarantees constant O(1) memory usage, non-repudiation Digital Security Seal computation,
/// trailer control verification, and high-speed bulk promotion into the financial ledger.
/// </summary>
public interface ISarsLevyStreamingPipeline
{
    /// <summary>
    /// Processes a SARS monthly levy schedule stream end-to-end:
    /// Streams through staging table, computes Digital Security Seal (SHA-256), validates trailer controls,
    /// checks idempotency, evaluates Inter-SETA boundaries, and promotes into production LevyFile and LevyFileLine records.
    /// </summary>
    Task<LevyStreamingResult> ProcessSarsStreamAsync(
        Stream fileStream,
        string fileName,
        string currentUsername = "SYSTEM",
        CancellationToken cancellationToken = default);

    /// <summary>
    /// Evaluates if a file with the given Digital Security Seal (SHA-256 hash) has already been processed.
    /// </summary>
    Task<bool> IsDuplicateFileSealAsync(string digitalSecuritySeal);
}
