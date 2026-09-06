using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public record BulkBatchImportResult(
    LearnerBulkBatch Batch,
    int TotalProcessed,
    int TotalSucceeded,
    int TotalFailed,
    int TotalStpApproved
);

/// <summary>
/// Service managing the automated high-speed bulk ingestion pipeline (The ATM Channel).
/// </summary>
public interface ILearnerBulkIngestionService
{
    /// <summary>
    /// Generates standard CSV template bytes for bulk learner enrolment.
    /// </summary>
    byte[] GenerateBulkTemplateCsv();

    /// <summary>
    /// Staged file upload and in-memory pre-flight ingestion.
    /// </summary>
    Task<LearnerBulkBatch> StageBatchAsync(
        int organisationId,
        string fileName,
        Stream fileStream,
        string currentUsername = "SYSTEM");

    /// <summary>
    /// Processes staged rows: executes pre-flight checks, runs STP engine, and commits valid rows.
    /// </summary>
    Task<BulkBatchImportResult> ProcessBatchAsync(int batchId, string currentUsername = "SYSTEM");

    /// <summary>
    /// Retrieves all bulk batches for an organisation or globally for administrators.
    /// </summary>
    Task<List<LearnerBulkBatch>> GetBatchesAsync(int? organisationId = null);

    /// <summary>
    /// Retrieves a single bulk batch with its line rows.
    /// </summary>
    Task<LearnerBulkBatch?> GetBatchByIdAsync(int batchId);

    /// <summary>
    /// Updates an invalid line row in-place and re-evaluates its validation status.
    /// </summary>
    Task<LearnerBulkBatchRow> UpdateBatchRowAsync(int rowId, LearnerBulkBatchRow updatedRow, string currentUsername = "SYSTEM");
}
