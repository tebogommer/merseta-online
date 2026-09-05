using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public record GpHealthStatus(
    bool IsAvailable,
    string EndpointUrl,
    long ResponseTimeMs,
    string StatusMessage,
    DateTime CheckedAtUtc,
    bool IsSimulatedMode
);

public record ErpOutboxBatchProcessResult(
    int TotalProcessed,
    int SuccessCount,
    int FailedCount,
    bool PausedDueToGpOutage,
    string SummaryMessage
);

public record ErpOutboxQueueStats(
    int PendingCount,
    int ProcessingCount,
    int DeliveredCount,
    int FailedRetryableCount,
    int DeadLetterCount,
    int TotalCount,
    DateTime? LastDeliveredAtUtc,
    DateTime? LastPausedAtUtc,
    GpHealthStatus GpHealth
);

/// <summary>
/// Universal Transactional Outbox pattern contract for Microsoft Dynamics GP Web Services.
/// Enqueues, pauses on GP downtime, and sequentially resumes execution upon recovery.
/// </summary>
public interface IErpOutboxQueueService
{
    /// <summary>
    /// Enqueue an ERP web service operation into durable storage.
    /// </summary>
    Task<ErpOutboxMessage> EnqueueAsync(
        string messageType,
        string referenceKey,
        object payload,
        int? organisationId = null,
        int priority = 2,
        string username = "SYSTEM");

    /// <summary>
    /// Probe the Microsoft Dynamics GP endpoint to determine connectivity status.
    /// </summary>
    Task<GpHealthStatus> CheckGpHealthAsync();

    /// <summary>
    /// Drains the next eligible batch of outbox messages in priority & FIFO order if GP is online.
    /// Automatically pauses processing and applies exponential backoff if GP is offline.
    /// </summary>
    Task<ErpOutboxBatchProcessResult> ProcessNextBatchAsync(int batchSize = 20, CancellationToken ct = default);

    /// <summary>
    /// Retrieves current operational metrics for the outbox queue.
    /// </summary>
    Task<ErpOutboxQueueStats> GetQueueStatsAsync();

    /// <summary>
    /// Retrieves recent messages for monitoring and administrative review.
    /// </summary>
    Task<List<ErpOutboxMessage>> GetMessagesAsync(string? statusCode = null, int maxCount = 100);

    /// <summary>
    /// Manually resets a DeadLetter or Failed message back to Pending for immediate retry.
    /// </summary>
    Task<bool> RetryMessageAsync(int messageId, string username = "SYSTEM");

    /// <summary>
    /// Manually triggers GP health probe and forces an outbox queue drain cycle.
    /// </summary>
    Task<ErpOutboxBatchProcessResult> ResumeQueueManuallyAsync(string username = "SYSTEM");
}
