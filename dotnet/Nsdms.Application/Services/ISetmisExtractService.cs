using Nsdms.Application.Common;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

/// <summary>
/// Service contract for generating DHET SETMIS (Files 100 through 506) statutory flat-file extracts.
/// </summary>
public interface ISetmisExtractService
{
    /// <summary>
    /// Gets metadata and estimated record counts for all 11 supported DHET SETMIS files.
    /// </summary>
    Task<List<StatutoryBatchFileSummary>> GetSetmisFileDescriptorsAsync(CancellationToken cancellationToken = default);

    /// <summary>
    /// Extracts a single SETMIS file as a formatted, fixed-width ASCII string.
    /// </summary>
    Task<StatutoryFileExtractResult> ExtractSetmisFileAsync(string fileCode, DateTime? extractionDate = null, CancellationToken cancellationToken = default);

    /// <summary>
    /// Executes a full SETMIS batch extraction across all 11 files, computing SHA-256 seals and generating a zipped bundle.
    /// </summary>
    Task<StatutorySubmissionBatch> GenerateFullSetmisBatchAsync(int financialYear, int? quarter = null, string? comments = null, string? userId = null, CancellationToken cancellationToken = default);

    /// <summary>
    /// Generates a zipped archive payload for an existing SETMIS batch.
    /// </summary>
    Task<StatutoryZipArchiveResult> DownloadSetmisBatchArchiveAsync(int batchId, CancellationToken cancellationToken = default);

    /// <summary>
    /// Lists all historical SETMIS batches executed in the system.
    /// </summary>
    Task<List<StatutorySubmissionBatch>> GetSetmisBatchesAsync(CancellationToken cancellationToken = default);
}
