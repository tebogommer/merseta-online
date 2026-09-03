using Nsdms.Application.Common;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

/// <summary>
/// Service contract for generating SAQA NLRD Edu.Dex (Files 21 through 30) statutory flat-file extracts.
/// </summary>
public interface INlrdExtractService
{
    /// <summary>
    /// Gets metadata and estimated record counts for all 8 supported SAQA NLRD files.
    /// </summary>
    Task<List<StatutoryBatchFileSummary>> GetNlrdFileDescriptorsAsync(CancellationToken cancellationToken = default);

    /// <summary>
    /// Extracts a single SAQA NLRD file as a formatted, fixed-width ASCII string including the SAQA HEADER record.
    /// </summary>
    Task<StatutoryFileExtractResult> ExtractNlrdFileAsync(string fileCode, DateTime? extractionDate = null, CancellationToken cancellationToken = default);

    /// <summary>
    /// Executes a full SAQA NLRD batch extraction across all 8 files, computing SHA-256 seals and generating a zipped bundle.
    /// </summary>
    Task<StatutorySubmissionBatch> GenerateFullNlrdBatchAsync(int financialYear, string? comments = null, string? userId = null, CancellationToken cancellationToken = default);

    /// <summary>
    /// Generates a zipped archive payload for an existing NLRD batch.
    /// </summary>
    Task<StatutoryZipArchiveResult> DownloadNlrdBatchArchiveAsync(int batchId, CancellationToken cancellationToken = default);

    /// <summary>
    /// Lists all historical NLRD batches executed in the system.
    /// </summary>
    Task<List<StatutorySubmissionBatch>> GetNlrdBatchesAsync(CancellationToken cancellationToken = default);
}
