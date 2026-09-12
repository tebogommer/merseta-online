namespace Nsdms.Application.Services;

public class AuditLogArchivalMetrics
{
    public long ActiveAuditLogCount { get; set; }
    public long ArchivedAuditLogCount { get; set; }
    public DateTime? OldestActiveTimestamp { get; set; }
    public DateTime? NewestActiveTimestamp { get; set; }
    public List<AuditPartitionInfo> Partitions { get; set; } = new();
}

public class AuditPartitionInfo
{
    public string TableName { get; set; } = string.Empty;
    public int PartitionNumber { get; set; }
    public long RowCount { get; set; }
    public string? BoundaryValue { get; set; }
    public string? FileGroupName { get; set; }
}

public class ArchivedAuditLogDto
{
    public long Id { get; set; }
    public string EntityName { get; set; } = string.Empty;
    public long RecordId { get; set; }
    public string ActionName { get; set; } = string.Empty;
    public string Actor { get; set; } = string.Empty;
    public string? MetadataJson { get; set; }
    public DateTime Timestamp { get; set; }
    public DateTime ArchivedAt { get; set; }
}

public interface IAuditLogArchivalService
{
    Task<AuditLogArchivalMetrics> GetArchivalMetricsAsync(CancellationToken cancellationToken = default);
    Task<int> ExecuteBatchArchivalAsync(DateTime cutoffDate, int batchSize = 5000, int maxRows = 50000, CancellationToken cancellationToken = default);
    Task<int> ArchiveOlderThanMonthsAsync(int retentionMonths = 24, int batchSize = 5000, int maxRows = 50000, CancellationToken cancellationToken = default);
    Task<IReadOnlyList<ArchivedAuditLogDto>> QueryArchivedLogsAsync(string? entityName, long? recordId, DateTime? fromUtc, DateTime? toUtc, int take = 50, CancellationToken cancellationToken = default);
}
