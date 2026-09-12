using System.Diagnostics;
using System.Text;
using Nsdms.Application.Services;

namespace Nsdms.Infrastructure.Services;

public interface IMetricsScraperService
{
    Task<string> GetPrometheusMetricsTextAsync(CancellationToken cancellationToken = default);
}

public class MetricsScraperService : IMetricsScraperService
{
    private readonly IBackgroundJobQueue _backgroundJobQueue;
    private readonly IAuditLogArchivalService _auditLogArchivalService;

    public MetricsScraperService(
        IBackgroundJobQueue backgroundJobQueue,
        IAuditLogArchivalService auditLogArchivalService)
    {
        _backgroundJobQueue = backgroundJobQueue;
        _auditLogArchivalService = auditLogArchivalService;
    }

    public async Task<string> GetPrometheusMetricsTextAsync(CancellationToken cancellationToken = default)
    {
        var sb = new StringBuilder();
        var process = Process.GetCurrentProcess();

        // 1. Process & Runtime Gauges
        sb.AppendLine("# HELP process_cpu_seconds_total Total user and system CPU time spent in seconds.");
        sb.AppendLine("# TYPE process_cpu_seconds_total counter");
        sb.AppendLine($"process_cpu_seconds_total {process.TotalProcessorTime.TotalSeconds:F3}");

        sb.AppendLine("# HELP process_working_set_bytes Process working set memory in bytes.");
        sb.AppendLine("# TYPE process_working_set_bytes gauge");
        sb.AppendLine($"process_working_set_bytes {process.WorkingSet64}");

        sb.AppendLine("# HELP process_private_memory_bytes Process private memory size in bytes.");
        sb.AppendLine("# TYPE process_private_memory_bytes gauge");
        sb.AppendLine($"process_private_memory_bytes {process.PrivateMemorySize64}");

        sb.AppendLine("# HELP dotnet_total_memory_bytes Best available approximation of the number of bytes allocated in managed memory.");
        sb.AppendLine("# TYPE dotnet_total_memory_bytes gauge");
        sb.AppendLine($"dotnet_total_memory_bytes {GC.GetTotalMemory(false)}");

        sb.AppendLine("# HELP dotnet_collection_count_total Number of garbage collections that have occurred.");
        sb.AppendLine("# TYPE dotnet_collection_count_total counter");
        sb.AppendLine($"dotnet_collection_count_total{{generation=\"0\"}} {GC.CollectionCount(0)}");
        sb.AppendLine($"dotnet_collection_count_total{{generation=\"1\"}} {GC.CollectionCount(1)}");
        sb.AppendLine($"dotnet_collection_count_total{{generation=\"2\"}} {GC.CollectionCount(2)}");

        sb.AppendLine("# HELP process_threads_count Current number of OS threads utilized by the process.");
        sb.AppendLine("# TYPE process_threads_count gauge");
        sb.AppendLine($"process_threads_count {process.Threads.Count}");

        // 2. Background Queue Metrics
        var recentJobs = _backgroundJobQueue.GetRecentJobs(100).ToList();
        var queued = recentJobs.Count(j => j.Status == BackgroundJobStatus.Queued);
        var running = recentJobs.Count(j => j.Status == BackgroundJobStatus.Running);
        var completed = recentJobs.Count(j => j.Status == BackgroundJobStatus.Completed);
        var failed = recentJobs.Count(j => j.Status == BackgroundJobStatus.Failed);

        sb.AppendLine("# HELP nsdms_background_jobs_queue_count Current background jobs partitioned by status.");
        sb.AppendLine("# TYPE nsdms_background_jobs_queue_count gauge");
        sb.AppendLine($"nsdms_background_jobs_queue_count{{status=\"queued\"}} {queued}");
        sb.AppendLine($"nsdms_background_jobs_queue_count{{status=\"running\"}} {running}");
        sb.AppendLine($"nsdms_background_jobs_queue_count{{status=\"completed\"}} {completed}");
        sb.AppendLine($"nsdms_background_jobs_queue_count{{status=\"failed\"}} {failed}");

        // 3. Database AuditLog Partition & Archival Metrics
        try
        {
            var auditMetrics = await _auditLogArchivalService.GetArchivalMetricsAsync(cancellationToken);
            sb.AppendLine("# HELP nsdms_audit_logs_active_total Total active rows in primary audit_logs table.");
            sb.AppendLine("# TYPE nsdms_audit_logs_active_total gauge");
            sb.AppendLine($"nsdms_audit_logs_active_total {auditMetrics.ActiveAuditLogCount}");

            sb.AppendLine("# HELP nsdms_audit_logs_archived_total Total archived rows in audit_logs_archive table.");
            sb.AppendLine("# TYPE nsdms_audit_logs_archived_total gauge");
            sb.AppendLine($"nsdms_audit_logs_archived_total {auditMetrics.ArchivedAuditLogCount}");

            sb.AppendLine("# HELP nsdms_audit_logs_partitions_count Number of active partitions on audit_logs.");
            sb.AppendLine("# TYPE nsdms_audit_logs_partitions_count gauge");
            sb.AppendLine($"nsdms_audit_logs_partitions_count {auditMetrics.Partitions.Count}");
        }
        catch
        {
            // Omit if DB is temporarily unreachable
        }

        return sb.ToString();
    }
}
