using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public interface ISetmisService
{
    Task<string> GenerateSetmisFileContentAsync(string fileCode);
    Task<List<SetmisValidationMessage>> ValidateSetmisDataAsync();
    Task<List<SetmisSubmissionBatch>> GetSubmissionBatchesAsync();
    Task<SetmisSubmissionBatch> CreateSubmissionBatchAsync(string period, string fileCode, string userId);
    Task<bool> UpdateBatchStatusAsync(int batchId, string status, string? ackRef, string userId);
}
