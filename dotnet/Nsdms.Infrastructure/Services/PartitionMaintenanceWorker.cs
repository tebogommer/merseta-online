using System;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;
using Nsdms.Infrastructure.Data;

namespace Nsdms.Infrastructure.Services;

/// <summary>
/// Background hosted service that performs scheduled database maintenance,
/// executing tiered archival of partitioned high-volume audit logs via usp_ArchiveAuditLogs.
/// </summary>
public class PartitionMaintenanceWorker : BackgroundService
{
    private readonly IServiceProvider _serviceProvider;
    private readonly ILogger<PartitionMaintenanceWorker> _logger;

    public PartitionMaintenanceWorker(
        IServiceProvider serviceProvider,
        ILogger<PartitionMaintenanceWorker> logger)
    {
        _serviceProvider = serviceProvider;
        _logger = logger;
    }

    protected override async Task ExecuteAsync(CancellationToken stoppingToken)
    {
        _logger.LogInformation("Partition & Archival Maintenance Worker registered.");

        // Initial delay after startup to let schema migrators and web initialization settle
        try
        {
            await Task.Delay(TimeSpan.FromMinutes(2), stoppingToken);
        }
        catch (OperationCanceledException)
        {
            return;
        }

        while (!stoppingToken.IsCancellationRequested)
        {
            try
            {
                await RunMaintenancePassAsync(stoppingToken);
            }
            catch (OperationCanceledException) when (stoppingToken.IsCancellationRequested)
            {
                break;
            }
            catch (Exception ex)
            {
                _logger.LogWarning(ex, "Scheduled partition maintenance pass encountered a transient warning: {Message}", ex.Message);
            }

            // Runs once every 24 hours
            try
            {
                await Task.Delay(TimeSpan.FromHours(24), stoppingToken);
            }
            catch (OperationCanceledException)
            {
                break;
            }
        }

        _logger.LogInformation("Partition & Archival Maintenance Worker stopped.");
    }

    private async Task RunMaintenancePassAsync(CancellationToken ct)
    {
        using var scope = _serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetService<NsdmsDbContext>();
        if (context == null || !context.Database.IsSqlServer())
        {
            return;
        }

        _logger.LogInformation("Starting scheduled high-volume audit log archival and partition check...");

        // Keep 90 days in the active partition, archive older rows in batches of 5000
        var cutoff = DateTime.UtcNow.AddDays(-90);
        var sql = "EXEC [dbo].[usp_ArchiveAuditLogs] @CutoffDate = {0}, @BatchSize = {1};";

        try
        {
            var rowsArchived = await context.Database.ExecuteSqlRawAsync(sql, new object[] { cutoff, 5000 }, ct);
            _logger.LogInformation("Audit log archival maintenance completed. Rows affected/archived: {Rows}", rowsArchived);
        }
        catch (Exception ex)
        {
            _logger.LogDebug(ex, "usp_ArchiveAuditLogs execution deferred or procedure not provisioned on current instance: {Message}", ex.Message);
        }
    }
}
