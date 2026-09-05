using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;

namespace Nsdms.Infrastructure.Services;

public class BackgroundSchedulerHostedService : BackgroundService
{
    private readonly IServiceProvider _serviceProvider;
    private readonly ILogger<BackgroundSchedulerHostedService> _logger;

    public BackgroundSchedulerHostedService(
        IServiceProvider serviceProvider,
        ILogger<BackgroundSchedulerHostedService> logger)
    {
        _serviceProvider = serviceProvider;
        _logger = logger;
    }

    protected override async Task ExecuteAsync(CancellationToken stoppingToken)
    {
        _logger.LogInformation("Background Scheduler Hosted Service initialized (Off-By-Default Check Active).");
        try
        {
            await Task.Delay(10000, stoppingToken);

            while (!stoppingToken.IsCancellationRequested)
            {
                try
                {
                    using var scope = _serviceProvider.CreateScope();
                    var featureFlags = scope.ServiceProvider.GetRequiredService<IFeatureFlagService>();
                    var isSchedulerEnabled = await featureFlags.IsFeatureEnabledAsync("Scheduler.BackgroundWorker", false);

                    if (isSchedulerEnabled)
                    {
                        _logger.LogInformation("Background Scheduler: Executing active scheduled maintenance cycle...");
                        var levyService = scope.ServiceProvider.GetRequiredService<LevyService>();
                        var configService = scope.ServiceProvider.GetRequiredService<ISystemConfigurationService>();
                        var statutoryScheduler = scope.ServiceProvider.GetService<IStatutorySchedulerService>();

                        if (statutoryScheduler != null)
                        {
                            // Trigger statutory checks
                            await statutoryScheduler.ExecuteWspDeadlineMonitorJobAsync("SYSTEM_SCHEDULER", stoppingToken);
                            await statutoryScheduler.ExecuteSetmisMonthlyDeltaJobAsync("SYSTEM_SCHEDULER", stoppingToken);
                        }

                        // Process Microsoft Dynamics GP Resilient Outbox Queue
                        var outboxService = scope.ServiceProvider.GetService<IErpOutboxQueueService>();
                        if (outboxService != null)
                        {
                            var outboxResult = await outboxService.ProcessNextBatchAsync(20, stoppingToken);
                            if (outboxResult.TotalProcessed > 0)
                            {
                                _logger.LogInformation("ERP Outbox Worker: Processed {Total} messages ({Success} delivered, {Failed} failed, Paused={Paused})",
                                    outboxResult.TotalProcessed, outboxResult.SuccessCount, outboxResult.FailedCount, outboxResult.PausedDueToGpOutage);
                            }
                        }

                        // Automated SLA Task check and maintenance logic
                        var slaMonitoringService = scope.ServiceProvider.GetService<ISlaMonitoringService>();
                        if (slaMonitoringService != null)
                        {
                            await slaMonitoringService.CheckTaskSlasAsync(stoppingToken);
                        }

                        var lastRun = DateTime.UtcNow;
                        await configService.SetConfigAsync("Scheduler.LastHeartbeat", lastRun.ToString("o"), "Scheduler", "Last recorded background scheduler execution timestamp", "String", "SYSTEM");
                    }
                    else
                    {
                        _logger.LogDebug("Background Scheduler: Feature 'Scheduler.BackgroundWorker' is DISABLED by policy. Skipping execution cycle.");
                    }
                }
                catch (OperationCanceledException) when (stoppingToken.IsCancellationRequested)
                {
                    break;
                }
                catch (Exception ex)
                {
                    _logger.LogError(ex, "Error occurred during background scheduler execution cycle.");
                }

                // Sleep for 60 seconds between cycles
                await Task.Delay(TimeSpan.FromSeconds(60), stoppingToken);
            }
        }
        catch (OperationCanceledException) when (stoppingToken.IsCancellationRequested)
        {
            // Clean host shutdown
        }
    }
}
