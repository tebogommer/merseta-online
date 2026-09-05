using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;

namespace Nsdms.Infrastructure.Services;

/// <summary>
/// Evaluates active workflow task SLAs, dispatches real-time SignalR alerts and persistent notifications.
/// </summary>
public class SlaMonitoringService : ISlaMonitoringService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IRealtimeNotificationService? _realtimeNotification;
    private readonly INotificationService? _persistentNotification;
    private readonly ILogger<SlaMonitoringService> _logger;

    public SlaMonitoringService(
        INsdmsDbContextFactory contextFactory,
        ILogger<SlaMonitoringService> logger,
        IRealtimeNotificationService? realtimeNotification = null,
        INotificationService? persistentNotification = null)
    {
        _contextFactory = contextFactory;
        _logger = logger;
        _realtimeNotification = realtimeNotification;
        _persistentNotification = persistentNotification;
    }

    public async Task<SlaCheckResult> CheckTaskSlasAsync(CancellationToken cancellationToken = default)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var openTasks = await context.WorkflowTasks
            .Where(t => t.TaskStatus != "Completed")
            .ToListAsync(cancellationToken);

        var now = DateTime.UtcNow;
        var warnings = new List<SlaTaskWarningDto>();
        int healthyCount = 0;
        int approachingCount = 0;
        int breachedCount = 0;

        foreach (var task in openTasks)
        {
            var timeRemaining = task.DueDate - now;
            var hoursRemaining = (int)Math.Floor(timeRemaining.TotalHours);

            if (hoursRemaining <= 0)
            {
                breachedCount++;
                var warning = new SlaTaskWarningDto(
                    task.Id,
                    task.TaskTitle,
                    task.AssignedGroupRole,
                    task.AssignedUserId,
                    task.DueDate,
                    hoursRemaining,
                    IsBreached: true
                );
                warnings.Add(warning);

                if (_realtimeNotification != null)
                {
                    try
                    {
                        await _realtimeNotification.NotifySlaWarningAsync(task.TaskTitle, hoursRemaining);
                    }
                    catch (Exception ex)
                    {
                        _logger.LogWarning(ex, "Failed to dispatch real-time SLA breach alert for task #{TaskId}", task.Id);
                    }
                }

                if (_persistentNotification != null)
                {
                    try
                    {
                        await _persistentNotification.SendNotificationAsync(
                            recipientUsername: task.AssignedUserId,
                            recipientRole: task.AssignedGroupRole,
                            title: $"🚨 SLA Breach: {task.TaskTitle}",
                            message: $"Task #{task.Id} has exceeded its statutory deadline ({Math.Abs(hoursRemaining)} hours overdue). Immediate action required.",
                            actionUrl: !string.IsNullOrWhiteSpace(task.TargetRoute) ? task.TargetRoute : "/tasks",
                            notificationType: "WorkflowTask",
                            severity: "Error",
                            actor: "SLA_MONITOR"
                        );
                    }
                    catch (Exception ex)
                    {
                        _logger.LogWarning(ex, "Failed to persist SLA breach notification for task #{TaskId}", task.Id);
                    }
                }
            }
            else if (hoursRemaining <= 24)
            {
                approachingCount++;
                var warning = new SlaTaskWarningDto(
                    task.Id,
                    task.TaskTitle,
                    task.AssignedGroupRole,
                    task.AssignedUserId,
                    task.DueDate,
                    hoursRemaining,
                    IsBreached: false
                );
                warnings.Add(warning);

                if (_realtimeNotification != null)
                {
                    try
                    {
                        await _realtimeNotification.NotifySlaWarningAsync(task.TaskTitle, hoursRemaining);
                    }
                    catch (Exception ex)
                    {
                        _logger.LogWarning(ex, "Failed to dispatch real-time SLA warning alert for task #{TaskId}", task.Id);
                    }
                }

                if (_persistentNotification != null)
                {
                    try
                    {
                        await _persistentNotification.SendNotificationAsync(
                            recipientUsername: task.AssignedUserId,
                            recipientRole: task.AssignedGroupRole,
                            title: $"⚠️ SLA Warning: {task.TaskTitle}",
                            message: $"Task #{task.Id} has {hoursRemaining} hours remaining before statutory breach.",
                            actionUrl: !string.IsNullOrWhiteSpace(task.TargetRoute) ? task.TargetRoute : "/tasks",
                            notificationType: "WorkflowTask",
                            severity: "Warning",
                            actor: "SLA_MONITOR"
                        );
                    }
                    catch (Exception ex)
                    {
                        _logger.LogWarning(ex, "Failed to persist SLA warning notification for task #{TaskId}", task.Id);
                    }
                }
            }
            else
            {
                healthyCount++;
            }
        }

        _logger.LogInformation(
            "SLA Monitoring: Evaluated {Total} tasks ({Healthy} healthy, {Approaching} approaching deadline, {Breached} breached)",
            openTasks.Count, healthyCount, approachingCount, breachedCount);

        return new SlaCheckResult(
            TotalTasksEvaluated: openTasks.Count,
            HealthyCount: healthyCount,
            ApproachingBreachCount: approachingCount,
            BreachedCount: breachedCount,
            Warnings: warnings
        );
    }
}
