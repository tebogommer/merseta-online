using System.Diagnostics;
using System.Text.Json;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;

namespace Nsdms.Infrastructure.Services;

public class ErpOutboxQueueService : IErpOutboxQueueService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IErpIntegrationService _erpService;
    private readonly IFeatureFlagService _featureFlags;
    private readonly ISystemConfigurationService _config;
    private readonly IAuditService _audit;
    private readonly ILogger<ErpOutboxQueueService> _logger;
    private readonly HttpClient _httpClient;

    public ErpOutboxQueueService(
        INsdmsDbContextFactory contextFactory,
        IErpIntegrationService erpService,
        IFeatureFlagService featureFlags,
        ISystemConfigurationService config,
        IAuditService audit,
        ILogger<ErpOutboxQueueService> logger,
        HttpClient? httpClient = null)
    {
        _contextFactory = contextFactory;
        _erpService = erpService;
        _featureFlags = featureFlags;
        _config = config;
        _audit = audit;
        _logger = logger;
        _httpClient = httpClient ?? new HttpClient { Timeout = TimeSpan.FromSeconds(3) };
    }

    public async Task<ErpOutboxMessage> EnqueueAsync(
        string messageType,
        string referenceKey,
        object payload,
        int? organisationId = null,
        int priority = 2,
        string username = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var message = new ErpOutboxMessage
        {
            MessageCorrelationId = Guid.NewGuid().ToString("N"),
            MessageType = messageType,
            ReferenceKey = referenceKey,
            OrganisationId = organisationId,
            PayloadJson = JsonSerializer.Serialize(payload),
            QueueStatusCode = "Pending",
            RetryCount = 0,
            MaxRetries = 5,
            NextAttemptAtUtc = DateTime.UtcNow,
            ExecutionPriority = priority,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = username
        };

        db.ErpOutboxMessages.Add(message);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "ErpOutboxMessage", message.Id, "EnqueueErpOutbox", username, null, new
        {
            message.MessageType,
            message.ReferenceKey,
            message.ExecutionPriority,
            message.MessageCorrelationId
        });
        await db.SaveChangesAsync();

        _logger.LogInformation("Enqueued ErpOutboxMessage {Id} for {MessageType} (Ref: {ReferenceKey})",
            message.Id, message.MessageType, message.ReferenceKey);

        return message;
    }

    public async Task<GpHealthStatus> CheckGpHealthAsync()
    {
        var isGpEnabled = await _featureFlags.IsFeatureEnabledAsync("Integrations.DynamicsGp", false);
        var simulatedOffline = await _config.GetValueAsync("Integrations.DynamicsGp.SimulatedOffline", "false");

        // Simulated Outage Toggle for Resilience Testing
        if (simulatedOffline.Equals("true", StringComparison.OrdinalIgnoreCase))
        {
            return new GpHealthStatus(
                IsAvailable: false,
                EndpointUrl: "MOCK://offline-simulated",
                ResponseTimeMs: 0,
                StatusMessage: "Dynamics GP Web Services paused (Disaster Recovery Simulation Active).",
                CheckedAtUtc: DateTime.UtcNow,
                IsSimulatedMode: true
            );
        }

        if (!isGpEnabled)
        {
            // Default decoupled simulation mode is healthy and available locally
            return new GpHealthStatus(
                IsAvailable: true,
                EndpointUrl: "https://erp.merseta.org.za/GP/v1/Transactions",
                ResponseTimeMs: 4,
                StatusMessage: "Dynamics GP ERP Ledger (Zero-Risk Simulation Mode Active).",
                CheckedAtUtc: DateTime.UtcNow,
                IsSimulatedMode: true
            );
        }

        // Live GP Web Services Health Probe
        var endpoint = await _config.GetValueAsync("Integrations.DynamicsGp.EndpointUrl", "https://erp.merseta.org.za/GP/v1/Transactions");
        var sw = Stopwatch.StartNew();

        try
        {
            using var cts = new CancellationTokenSource(TimeSpan.FromSeconds(3));
            var response = await _httpClient.GetAsync(endpoint, cts.Token);
            sw.Stop();

            // Web services endpoint responding (even 401/403/405 indicates host availability)
            bool isUp = (int)response.StatusCode < 500;
            return new GpHealthStatus(
                IsAvailable: isUp,
                EndpointUrl: endpoint,
                ResponseTimeMs: sw.ElapsedMilliseconds,
                StatusMessage: isUp ? $"Live GP Web Services connected in {sw.ElapsedMilliseconds}ms." : $"GP Web Services returned error code: {response.StatusCode}.",
                CheckedAtUtc: DateTime.UtcNow,
                IsSimulatedMode: false
            );
        }
        catch (Exception ex)
        {
            sw.Stop();
            _logger.LogWarning("GP Web Services health probe failed against {Endpoint}: {Error}", endpoint, ex.Message);
            return new GpHealthStatus(
                IsAvailable: false,
                EndpointUrl: endpoint,
                ResponseTimeMs: sw.ElapsedMilliseconds,
                StatusMessage: $"GP Web Services unavailable: {ex.Message}",
                CheckedAtUtc: DateTime.UtcNow,
                IsSimulatedMode: false
            );
        }
    }

    public async Task<ErpOutboxBatchProcessResult> ProcessNextBatchAsync(int batchSize = 20, CancellationToken ct = default)
    {
        var health = await CheckGpHealthAsync();
        if (!health.IsAvailable)
        {
            _logger.LogInformation("Dynamics GP is currently unavailable. Outbox queue execution paused automatically. Status: {Status}", health.StatusMessage);
            return new ErpOutboxBatchProcessResult(
                TotalProcessed: 0,
                SuccessCount: 0,
                FailedCount: 0,
                PausedDueToGpOutage: true,
                SummaryMessage: $"Queue paused. GP unavailable: {health.StatusMessage}"
            );
        }

        using var db = await _contextFactory.CreateDbContextAsync();
        var now = DateTime.UtcNow;

        var messages = await db.ErpOutboxMessages
            .Where(m => (m.QueueStatusCode == "Pending" || m.QueueStatusCode == "FailedRetryable")
                     && (m.NextAttemptAtUtc == null || m.NextAttemptAtUtc <= now))
            .OrderBy(m => m.ExecutionPriority)
            .ThenBy(m => m.CreatedAt)
            .Take(batchSize)
            .ToListAsync(ct);

        if (!messages.Any())
        {
            return new ErpOutboxBatchProcessResult(0, 0, 0, false, "Outbox queue is empty. No pending items.");
        }

        int successCount = 0;
        int failedCount = 0;
        bool pausedMidBatch = false;
        string lastStatus = "Completed batch.";

        foreach (var msg in messages)
        {
            if (ct.IsCancellationRequested) break;

            msg.QueueStatusCode = "Processing";
            msg.LastAttemptAtUtc = DateTime.UtcNow;
            msg.LockToken = Guid.NewGuid().ToString("N");
            msg.LockExpiresAtUtc = DateTime.UtcNow.AddMinutes(5);
            await db.SaveChangesAsync(ct);

            try
            {
                string trxRef = string.Empty;
                string batchNum = string.Empty;

                switch (msg.MessageType)
                {
                    case "VendorSync":
                        if (int.TryParse(msg.ReferenceKey, out int vendorOrgId))
                        {
                            var ok = await _erpService.SyncVendorDetailsAsync(vendorOrgId, msg.CreatedBy ?? "SYSTEM");
                            trxRef = $"VEN-SYNC-{vendorOrgId}";
                            batchNum = "GP-VENDOR-MASTER";
                        }
                        break;

                    case "BankingDetailsUpdate":
                        if (int.TryParse(msg.ReferenceKey, out int bankOrgId))
                        {
                            var ok = await _erpService.VerifyBankingDetailsAsync(bankOrgId, msg.CreatedBy ?? "SYSTEM");
                            trxRef = $"AVS-VERIFY-{bankOrgId}";
                            batchNum = "GP-BANK-UPDATE";
                        }
                        break;

                    case "DgTrancheDisbursement":
                        if (int.TryParse(msg.ReferenceKey, out int tranchePaymentId))
                        {
                            var res = await _erpService.PostTranchePaymentBatchAsync(tranchePaymentId, msg.CreatedBy ?? "SYSTEM");
                            trxRef = res.TransactionReference;
                            batchNum = res.BatchNumber;
                        }
                        break;

                    case "MgRebateDisbursement":
                        if (int.TryParse(msg.ReferenceKey, out int rebateDisbursementId))
                        {
                            var res = await _erpService.PostMandatoryRebateDisbursementAsync(rebateDisbursementId, msg.CreatedBy ?? "SYSTEM");
                            trxRef = res.TransactionReference;
                            batchNum = res.BatchNumber;
                        }
                        break;

                    case "PaymentDisbursement":
                        // Generic disbursement
                        var resGeneric = await _erpService.DisbursePaymentAsync(msg.ReferenceKey, 1000m, "Disbursement via Outbox Queue", msg.CreatedBy ?? "SYSTEM");
                        trxRef = resGeneric.TransactionReference;
                        batchNum = resGeneric.BatchNumber;
                        break;

                    default:
                        _logger.LogWarning("Unknown Outbox MessageType: {Type}", msg.MessageType);
                        break;
                }

                // Delivery Succeeded
                msg.QueueStatusCode = "Delivered";
                msg.DeliveredAtUtc = DateTime.UtcNow;
                msg.TransactionReference = trxRef;
                msg.GpBatchNumber = batchNum;
                msg.LastError = null;
                msg.LockToken = null;
                msg.LockExpiresAtUtc = null;
                msg.ModifiedAt = DateTime.UtcNow;
                msg.ModifiedBy = "ErpOutboxWorker";

                _audit.LogAction(db, "ErpOutboxMessage", msg.Id, "DeliverErpOutboxMessage", "ErpOutboxWorker", null, new
                {
                    msg.MessageType,
                    msg.ReferenceKey,
                    msg.TransactionReference,
                    msg.GpBatchNumber
                });

                await db.SaveChangesAsync(ct);
                successCount++;
            }
            catch (Exception ex)
            {
                failedCount++;
                msg.RetryCount++;
                msg.LastError = ex.Message;
                msg.LockToken = null;
                msg.LockExpiresAtUtc = null;
                msg.ModifiedAt = DateTime.UtcNow;
                msg.ModifiedBy = "ErpOutboxWorker";

                // Check for network/connection/outage failure
                bool isConnectionFailure = ex is HttpRequestException or TimeoutException or TaskCanceledException
                    || ex.Message.Contains("connection", StringComparison.OrdinalIgnoreCase)
                    || ex.Message.Contains("unavailable", StringComparison.OrdinalIgnoreCase);

                if (msg.RetryCount >= msg.MaxRetries && !isConnectionFailure)
                {
                    msg.QueueStatusCode = "DeadLetter";
                    _logger.LogError("ErpOutboxMessage {Id} reached max retries ({Max}). Escalated to DeadLetter.", msg.Id, msg.MaxRetries);
                }
                else
                {
                    msg.QueueStatusCode = "FailedRetryable";
                    // Exponential backoff: 15s, 30s, 60s, 120s, up to 300s
                    double backoffSeconds = Math.Min(300, Math.Pow(2, msg.RetryCount) * 15);
                    msg.NextAttemptAtUtc = DateTime.UtcNow.AddSeconds(backoffSeconds);
                }

                await db.SaveChangesAsync(ct);

                if (isConnectionFailure)
                {
                    _logger.LogWarning("Connection failure encountered on ErpOutboxMessage {Id}. Pausing queue execution for subsequent items.", msg.Id);
                    pausedMidBatch = true;
                    lastStatus = $"Batch paused due to GP connection failure on Message {msg.Id}: {ex.Message}";
                    break;
                }
            }
        }

        return new ErpOutboxBatchProcessResult(
            TotalProcessed: successCount + failedCount,
            SuccessCount: successCount,
            FailedCount: failedCount,
            PausedDueToGpOutage: pausedMidBatch,
            SummaryMessage: lastStatus
        );
    }

    public async Task<ErpOutboxQueueStats> GetQueueStatsAsync()
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var pending = await db.ErpOutboxMessages.CountAsync(m => m.QueueStatusCode == "Pending");
        var processing = await db.ErpOutboxMessages.CountAsync(m => m.QueueStatusCode == "Processing");
        var delivered = await db.ErpOutboxMessages.CountAsync(m => m.QueueStatusCode == "Delivered");
        var failed = await db.ErpOutboxMessages.CountAsync(m => m.QueueStatusCode == "FailedRetryable");
        var deadLetter = await db.ErpOutboxMessages.CountAsync(m => m.QueueStatusCode == "DeadLetter");
        var total = await db.ErpOutboxMessages.CountAsync();

        var lastDelivered = await db.ErpOutboxMessages
            .Where(m => m.QueueStatusCode == "Delivered" && m.DeliveredAtUtc != null)
            .OrderByDescending(m => m.DeliveredAtUtc)
            .Select(m => m.DeliveredAtUtc)
            .FirstOrDefaultAsync();

        var lastPaused = await db.ErpOutboxMessages
            .Where(m => m.QueueStatusCode == "FailedRetryable")
            .OrderByDescending(m => m.LastAttemptAtUtc)
            .Select(m => m.LastAttemptAtUtc)
            .FirstOrDefaultAsync();

        var gpHealth = await CheckGpHealthAsync();

        return new ErpOutboxQueueStats(
            PendingCount: pending,
            ProcessingCount: processing,
            DeliveredCount: delivered,
            FailedRetryableCount: failed,
            DeadLetterCount: deadLetter,
            TotalCount: total,
            LastDeliveredAtUtc: lastDelivered,
            LastPausedAtUtc: lastPaused,
            GpHealth: gpHealth
        );
    }

    public async Task<List<ErpOutboxMessage>> GetMessagesAsync(string? statusCode = null, int maxCount = 100)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.ErpOutboxMessages
            .Include(m => m.Organisation)
            .AsQueryable();

        if (!string.IsNullOrWhiteSpace(statusCode))
        {
            query = query.Where(m => m.QueueStatusCode == statusCode);
        }

        return await query
            .OrderByDescending(m => m.CreatedAt)
            .Take(maxCount)
            .ToListAsync();
    }

    public async Task<bool> RetryMessageAsync(int messageId, string username = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var msg = await db.ErpOutboxMessages.FindAsync(messageId);
        if (msg == null) return false;

        var before = new { msg.QueueStatusCode, msg.RetryCount };
        msg.QueueStatusCode = "Pending";
        msg.RetryCount = 0;
        msg.NextAttemptAtUtc = DateTime.UtcNow;
        msg.LastError = null;
        msg.LockToken = null;
        msg.LockExpiresAtUtc = null;
        msg.ModifiedAt = DateTime.UtcNow;
        msg.ModifiedBy = username;

        _audit.LogAction(db, "ErpOutboxMessage", msg.Id, "RetryErpOutboxMessage", username, before, msg);
        await db.SaveChangesAsync();

        _logger.LogInformation("ErpOutboxMessage {Id} manually reset to Pending by {User}", messageId, username);
        return true;
    }

    public async Task<ErpOutboxBatchProcessResult> ResumeQueueManuallyAsync(string username = "SYSTEM")
    {
        _logger.LogInformation("Manual queue resume initiated by {User}", username);
        return await ProcessNextBatchAsync(50);
    }
}
