using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

/// <summary>
/// Service contract for the Universal Bulk Ingestion Engine (Option C: Modern Hybrid).
/// Supports high-speed spreadsheet ingestion (.xlsx and .csv), real-time validation,
/// inline correction, delta workbook generation, and commit gating.
/// </summary>
public interface IWspBulkIngestionService
{
    /// <summary>
    /// Ingests a raw spreadsheet stream into the unconstrained staging buffer, calculates SHA-256 seal,
    /// and immediately executes pre-flight set-based validation.
    /// </summary>
    Task<WspBulkImportBatch> StageAndValidateBatchAsync(
        int wspSubmissionId, 
        string fileName, 
        Stream fileStream, 
        string currentUsername, 
        bool allowPartial = false);

    /// <summary>
    /// Re-evaluates validation rules on a staged batch after inline edits.
    /// </summary>
    Task<WspBulkImportBatch> RevalidateBatchAsync(int batchId, string currentUsername);

    /// <summary>
    /// Commits valid staged records into core WspTrainingPlan entities.
    /// If discardExceptions is true, any remaining invalid rows are permanently pruned.
    /// </summary>
    Task<WspBulkImportBatch> CommitBatchAsync(int batchId, string currentUsername, bool discardExceptions = false);

    /// <summary>
    /// Generates an OpenXML (.xlsx) delta correction workbook containing ONLY the failed exception rows,
    /// with cell comments and statutory lookup reference sheets.
    /// </summary>
    Task<byte[]> GenerateDeltaCorrectionExcelAsync(int batchId);

    /// <summary>
    /// Generates an official 2026 pre-formatted Excel template (.xlsx) with embedded metadata and dropdowns.
    /// </summary>
    Task<byte[]> GenerateOfficialTemplateExcelAsync(int schemeYear = 2026);

    /// <summary>
    /// Retrieves all staged and historical batch uploads for a given WSP submission.
    /// </summary>
    Task<List<WspBulkImportBatch>> GetBatchesBySubmissionAsync(int wspSubmissionId);

    /// <summary>
    /// Retrieves a batch record by primary key ID.
    /// </summary>
    Task<WspBulkImportBatch?> GetBatchByIdAsync(int batchId);

    /// <summary>
    /// Retrieves all uncommitted or failed exception rows for a batch.
    /// </summary>
    Task<List<WspBulkImportStaging>> GetBatchExceptionsAsync(int batchId);

    /// <summary>
    /// Updates a specific staged row with inline quick-fix corrections.
    /// </summary>
    Task UpdateStagedRowAsync(long stagingRowId, string? ofoCode, string? rsaIdNumber, string? programmeType, decimal? cost, string currentUsername);

    /// <summary>
    /// Merges an uploaded corrected delta workbook back into an existing staged batch.
    /// </summary>
    Task<WspBulkImportBatch> MergeDeltaWorkbookAsync(int batchId, Stream deltaStream, string currentUsername);
}
