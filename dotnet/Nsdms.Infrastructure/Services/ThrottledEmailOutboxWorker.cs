using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;
using System.Collections.Concurrent;

namespace Nsdms.Infrastructure.Services;

/// <summary>
/// Hosted background worker executing the token-bucket rate limiter for Office 365 Exchange Online.
/// Enforces:
/// 1. Hard daily limit of 10,000 emails per UTC calendar day.
/// 2. Sliding window rate throttling of 30 emails per minute (maximum burst protection).
/// 3. Exponential backoff retry on SMTP throttling (421/451) errors.
/// </summary>
public class ThrottledEmailOutboxWorker : BackgroundService
{
    private readonly IServiceProvider _serviceProvider;
    private readonly ILogger<ThrottledEmailOutboxWorker> _logger;

    // Sliding window of sent email timestamps within the last 60 seconds
    private readonly ConcurrentQueue<DateTime> _slidingWindowTimestamps = new();
    private const int MaxEmailsPerMinute = 30;
    private const int DailyLimitCeiling = 10000;

    public ThrottledEmailOutboxWorker(IServiceProvider serviceProvider, ILogger<ThrottledEmailOutboxWorker> logger)
    {
        _serviceProvider = serviceProvider;
        _logger = logger;
    }

    protected override async Task ExecuteAsync(CancellationToken stoppingToken)
    {
        _logger.LogInformation("ThrottledEmailOutboxWorker started. Enforcing 10,000/day and {Rate}/min limits.", MaxEmailsPerMinute);

        while (!stoppingToken.IsCancellationRequested)
        {
            try
            {
                await ProcessOutboxBatchAsync(stoppingToken);
            }
            catch (OperationCanceledException) when (stoppingToken.IsCancellationRequested)
            {
                break;
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "Unexpected error in ThrottledEmailOutboxWorker processing loop.");
            }

            // Polling interval between checks
            await Task.Delay(2000, stoppingToken);
        }

        _logger.LogInformation("ThrottledEmailOutboxWorker stopped.");
    }

    private async Task ProcessOutboxBatchAsync(CancellationToken stoppingToken)
    {
        using var scope = _serviceProvider.CreateScope();
        var contextFactory = scope.ServiceProvider.GetRequiredService<INsdmsDbContextFactory>();
        var transport = scope.ServiceProvider.GetRequiredService<IEmailTransportService>();

        using var db = await contextFactory.CreateDbContextAsync(stoppingToken);

        // 1. Check daily quota tracker
        var today = DateTime.UtcNow.Date;
        var quotaTracker = await db.EmailDailyQuotaTrackers.FirstOrDefaultAsync(q => q.QuotaDate == today, stoppingToken);

        if (quotaTracker == null)
        {
            quotaTracker = new EmailDailyQuotaTracker
            {
                QuotaDate = today,
                SentCount = 0,
                ThrottledCount = 0,
                FailedCount = 0,
                DailyLimit = DailyLimitCeiling,
                IsLimitReached = false,
                CreatedAt = DateTime.UtcNow
            };
            db.EmailDailyQuotaTrackers.Add(quotaTracker);
            await db.SaveChangesAsync(stoppingToken);
        }

        if (quotaTracker.SentCount >= quotaTracker.DailyLimit)
        {
            if (!quotaTracker.IsLimitReached)
            {
                quotaTracker.IsLimitReached = true;
                await db.SaveChangesAsync(stoppingToken);
                _logger.LogWarning("Daily email quota of {Limit} reached for {Date}. Halting email dispatch until next UTC day.",
                    quotaTracker.DailyLimit, today.ToString("yyyy-MM-dd"));
            }
            return;
        }

        // 2. Clean up rolling 1-minute window
        var oneMinuteAgo = DateTime.UtcNow.AddMinutes(-1);
        while (_slidingWindowTimestamps.TryPeek(out var oldest) && oldest < oneMinuteAgo)
        {
            _slidingWindowTimestamps.TryDequeue(out _);
        }

        // Available quota in current minute
        var availableInMinute = MaxEmailsPerMinute - _slidingWindowTimestamps.Count;
        if (availableInMinute <= 0)
        {
            // Rate limit active for the current minute
            return;
        }

        var availableInDay = quotaTracker.DailyLimit - quotaTracker.SentCount;
        var batchSize = Math.Min(availableInMinute, Math.Min(availableInDay, 10));

        // 3. Fetch due pending or throttled items
        var now = DateTime.UtcNow;
        var itemsToProcess = await db.EmailOutboxItems
            .Where(e => (e.Status == "Pending" || e.Status == "Throttled") &&
                        e.NextAttemptAt <= now &&
                        e.AttemptCount < e.MaxAttempts)
            .OrderBy(e => e.CreatedAt)
            .Take(batchSize)
            .ToListAsync(stoppingToken);

        if (!itemsToProcess.Any()) return;

        // 4. Process each email through rate-limited transport
        foreach (var item in itemsToProcess)
        {
            if (stoppingToken.IsCancellationRequested) break;

            item.Status = "InFlight";
            item.AttemptCount++;
            await db.SaveChangesAsync(stoppingToken);

            var transportMessage = new EmailTransportMessage(
                item.RecipientEmail,
                item.RecipientName,
                item.Subject,
                item.BodyHtml,
                item.AttachmentStoragePath,
                item.AttachmentFileName,
                item.AttachmentContentType);

            var result = await transport.SendEmailAsync(transportMessage, stoppingToken);

            if (result.Success)
            {
                item.Status = "Sent";
                item.SentAt = DateTime.UtcNow;
                item.LastError = null;

                _slidingWindowTimestamps.Enqueue(DateTime.UtcNow);
                quotaTracker.SentCount++;
                quotaTracker.LastSentAt = DateTime.UtcNow;
            }
            else if (result.Throttled)
            {
                item.Status = "Throttled";
                item.LastError = $"[Office 365 Throttled]: {result.ErrorMessage}";
                // Exponential backoff: 2^attempt minutes
                item.NextAttemptAt = DateTime.UtcNow.AddMinutes(Math.Min(60, Math.Pow(2, item.AttemptCount)));
                quotaTracker.ThrottledCount++;

                _logger.LogWarning("Email #{Id} throttled by Office 365. Next attempt at {NextAttempt}.",
                    item.Id, item.NextAttemptAt);
            }
            else
            {
                item.LastError = result.ErrorMessage;
                if (item.AttemptCount >= item.MaxAttempts)
                {
                    item.Status = "Failed";
                    quotaTracker.FailedCount++;
                    _logger.LogError("Email #{Id} failed permanently after {Attempts} attempts. Error: {Error}",
                        item.Id, item.AttemptCount, result.ErrorMessage);
                }
                else
                {
                    item.Status = "Pending";
                    // Linear backoff: 30s * attempt
                    item.NextAttemptAt = DateTime.UtcNow.AddSeconds(30 * item.AttemptCount);
                }
            }

            await db.SaveChangesAsync(stoppingToken);
        }
    }
}
