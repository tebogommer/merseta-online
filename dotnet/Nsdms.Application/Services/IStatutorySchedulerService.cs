namespace Nsdms.Application.Services;

/// <summary>
/// Status descriptor of an automated statutory scheduled background job.
/// </summary>
public class StatutoryJobStatusDto
{
    public string JobKey { get; set; } = string.Empty;
    public string JobName { get; set; } = string.Empty;
    public string Description { get; set; } = string.Empty;
    public string ScheduleDescription { get; set; } = string.Empty;
    public bool IsEnabled { get; set; }
    public DateTime? LastRunTime { get; set; }
    public DateTime? NextRunTime { get; set; }
    public string LastStatus { get; set; } = "Pending";
    public string? LastExecutionSummary { get; set; }
}

/// <summary>
/// Result of manual or automated execution of a statutory background job.
/// </summary>
public class StatutoryJobExecutionResult
{
    public string JobKey { get; set; } = string.Empty;
    public bool Success { get; set; }
    public DateTime ExecutionTime { get; set; } = DateTime.UtcNow;
    public string Summary { get; set; } = string.Empty;
    public string? ErrorMessage { get; set; }
}

/// <summary>
/// Service contract for managing, monitoring, and triggering automated statutory background jobs.
/// </summary>
public interface IStatutorySchedulerService
{
    /// <summary>
    /// Gets real-time execution status, last run timestamps, and schedule details for all statutory background jobs.
    /// </summary>
    Task<List<StatutoryJobStatusDto>> GetJobStatusesAsync(CancellationToken cancellationToken = default);

    /// <summary>
    /// Triggers an immediate execution of a specific statutory background job.
    /// </summary>
    Task<StatutoryJobExecutionResult> TriggerJobAsync(string jobKey, string? triggeredBy = null, CancellationToken cancellationToken = default);

    /// <summary>
    /// Executes the SARS Levy Reconciliation Job (ingestion, line matching, and 20% Mandatory Grant disbursement checks).
    /// </summary>
    Task<StatutoryJobExecutionResult> ExecuteSarsLevyReconciliationJobAsync(string? triggeredBy = null, CancellationToken cancellationToken = default);

    /// <summary>
    /// Executes the WSP / ATR Statutory Deadline Monitoring Job (evaluates 30 April cut-off, flags pending submissions, and sends alerts).
    /// </summary>
    Task<StatutoryJobExecutionResult> ExecuteWspDeadlineMonitorJobAsync(string? triggeredBy = null, CancellationToken cancellationToken = default);

    /// <summary>
    /// Executes the SETMIS Monthly Delta Extraction Job (identifies unsubmitted learner registrations, assessments, and provider accreditations).
    /// </summary>
    Task<StatutoryJobExecutionResult> ExecuteSetmisMonthlyDeltaJobAsync(string? triggeredBy = null, CancellationToken cancellationToken = default);
}
