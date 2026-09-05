namespace Nsdms.Application.Services;

/// <summary>
/// Result of evaluating workflow task SLAs against statutory completion windows.
/// </summary>
public record SlaCheckResult(
    int TotalTasksEvaluated,
    int HealthyCount,
    int ApproachingBreachCount,
    int BreachedCount,
    List<SlaTaskWarningDto> Warnings
);

/// <summary>
/// Detail of an individual task nearing or past SLA breach.
/// </summary>
public record SlaTaskWarningDto(
    int TaskId,
    string TaskTitle,
    string? AssignedGroupRole,
    string? AssignedUserId,
    DateTime DueDate,
    int HoursRemaining,
    bool IsBreached
);

/// <summary>
/// Service contract for evaluating, broadcasting, and logging task SLA warnings.
/// </summary>
public interface ISlaMonitoringService
{
    /// <summary>
    /// Evaluates open workflow tasks, broadcasts real-time SLA alerts for items nearing or past deadline,
    /// and logs statutory notifications.
    /// </summary>
    Task<SlaCheckResult> CheckTaskSlasAsync(CancellationToken cancellationToken = default);
}
