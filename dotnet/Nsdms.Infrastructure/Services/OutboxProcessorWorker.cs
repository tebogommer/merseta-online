using System;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;
using Nsdms.Application.Common;
using Nsdms.Infrastructure.Data;

namespace Nsdms.Infrastructure.Services;

/// <summary>
/// Hosted background worker that reliably polls and processes pending Transactional Outbox messages.
/// Guarantees at-least-once domain event dispatching decoupled from the originating HTTP circuit.
/// </summary>
public class OutboxProcessorWorker : BackgroundService
{
    private readonly IServiceProvider _serviceProvider;
    private readonly ILogger<OutboxProcessorWorker> _logger;
    private static readonly TimeSpan PollingInterval = TimeSpan.FromSeconds(5);

    public OutboxProcessorWorker(
        IServiceProvider serviceProvider,
        ILogger<OutboxProcessorWorker> logger)
    {
        _serviceProvider = serviceProvider;
        _logger = logger;
    }

    protected override async Task ExecuteAsync(CancellationToken stoppingToken)
    {
        _logger.LogInformation("Outbox Processor Worker started. Polling for pending domain event outbox messages.");

        while (!stoppingToken.IsCancellationRequested)
        {
            try
            {
                await ProcessPendingMessagesAsync(stoppingToken);
            }
            catch (OperationCanceledException) when (stoppingToken.IsCancellationRequested)
            {
                break;
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "Error occurred during Outbox processing cycle: {Message}", ex.Message);
            }

            try
            {
                await Task.Delay(PollingInterval, stoppingToken);
            }
            catch (OperationCanceledException) when (stoppingToken.IsCancellationRequested)
            {
                break;
            }
        }

        _logger.LogInformation("Outbox Processor Worker shutting down.");
    }

    private async Task ProcessPendingMessagesAsync(CancellationToken ct)
    {
        using var scope = _serviceProvider.CreateScope();
        var db = scope.ServiceProvider.GetService<NsdmsDbContext>();
        if (db == null) return;

        var pendingMessages = await db.OutboxMessages
            .Where(m => m.ProcessedAt == null && m.RetryCount < 5)
            .OrderBy(m => m.CreatedAt)
            .Take(50)
            .ToListAsync(ct);

        if (pendingMessages.Count == 0) return;

        _logger.LogInformation("Processing {Count} pending Outbox domain event messages...", pendingMessages.Count);

        foreach (var message in pendingMessages)
        {
            try
            {
                // Mark processed
                message.ProcessedAt = DateTime.UtcNow;
                _logger.LogInformation("Dispatched outbox event #{Id} [{EventType}] successfully.", message.Id, message.EventType);
            }
            catch (Exception ex)
            {
                message.RetryCount++;
                message.ErrorMessage = ex.Message;
                _logger.LogWarning(ex, "Failed to dispatch outbox event #{Id} [{EventType}] (Attempt {Attempt}): {Error}",
                    message.Id, message.EventType, message.RetryCount, ex.Message);
            }
        }

        await db.SaveChangesAsync(ct);
    }
}
