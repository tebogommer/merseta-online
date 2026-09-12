using System.Data;
using Microsoft.Data.SqlClient;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using Nsdms.Application.Common;
using Nsdms.Application.Services;

namespace Nsdms.Infrastructure.Services;

public class AuditLogArchivalService : IAuditLogArchivalService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly ILogger<AuditLogArchivalService> _logger;

    public AuditLogArchivalService(
        INsdmsDbContextFactory contextFactory,
        ILogger<AuditLogArchivalService> logger)
    {
        _contextFactory = contextFactory;
        _logger = logger;
    }

    public async Task<AuditLogArchivalMetrics> GetArchivalMetricsAsync(CancellationToken cancellationToken = default)
    {
        await using var context = await _contextFactory.CreateDbContextAsync();
        var metrics = new AuditLogArchivalMetrics();

        try
        {
            metrics.ActiveAuditLogCount = await context.AuditLogs.LongCountAsync(cancellationToken);

            if (metrics.ActiveAuditLogCount > 0)
            {
                metrics.OldestActiveTimestamp = await context.AuditLogs
                    .OrderBy(a => a.Timestamp)
                    .Select(a => (DateTime?)a.Timestamp)
                    .FirstOrDefaultAsync(cancellationToken);

                metrics.NewestActiveTimestamp = await context.AuditLogs
                    .OrderByDescending(a => a.Timestamp)
                    .Select(a => (DateTime?)a.Timestamp)
                    .FirstOrDefaultAsync(cancellationToken);
            }

            // Query archive count if table exists
            var conn = context.Database.GetDbConnection();
            if (conn.State != ConnectionState.Open)
                await conn.OpenAsync(cancellationToken);

            using var cmdArchive = conn.CreateCommand();
            cmdArchive.CommandText = @"
                IF OBJECT_ID('dbo.audit_logs_archive', 'U') IS NOT NULL
                    SELECT COUNT_BIG(*) FROM dbo.audit_logs_archive WITH (NOLOCK);
                ELSE
                    SELECT CAST(0 AS BIGINT);";

            var archiveResult = await cmdArchive.ExecuteScalarAsync(cancellationToken);
            if (archiveResult != null && archiveResult != DBNull.Value)
            {
                metrics.ArchivedAuditLogCount = Convert.ToInt64(archiveResult);
            }

            // Query partition info if view exists
            using var cmdPart = conn.CreateCommand();
            cmdPart.CommandText = @"
                IF OBJECT_ID('dbo.vw_AuditLogPartitionStats', 'V') IS NOT NULL
                    SELECT TableName, PartitionNumber, RowCounts, BoundaryValue, FileGroupName 
                    FROM dbo.vw_AuditLogPartitionStats;
                ELSE
                    SELECT CAST('audit_logs' AS NVARCHAR(128)), 1, CAST(0 AS BIGINT), CAST(NULL AS NVARCHAR(128)), CAST('PRIMARY' AS NVARCHAR(128)) WHERE 1=0;";

            using var reader = await cmdPart.ExecuteReaderAsync(cancellationToken);
            while (await reader.ReadAsync(cancellationToken))
            {
                metrics.Partitions.Add(new AuditPartitionInfo
                {
                    TableName = reader.GetString(0),
                    PartitionNumber = reader.GetInt32(1),
                    RowCount = reader.GetInt64(2),
                    BoundaryValue = reader.IsDBNull(3) ? null : reader.GetValue(3)?.ToString(),
                    FileGroupName = reader.IsDBNull(4) ? null : reader.GetString(4)
                });
            }
        }
        catch (Exception ex)
        {
            _logger.LogWarning(ex, "Error gathering audit archival metrics.");
        }

        return metrics;
    }

    public async Task<int> ExecuteBatchArchivalAsync(
        DateTime cutoffDate,
        int batchSize = 5000,
        int maxRows = 50000,
        CancellationToken cancellationToken = default)
    {
        await using var context = await _contextFactory.CreateDbContextAsync();
        var conn = context.Database.GetDbConnection();
        if (conn.State != ConnectionState.Open)
            await conn.OpenAsync(cancellationToken);

        int totalArchived = 0;

        try
        {
            using var cmd = conn.CreateCommand();
            cmd.CommandText = "dbo.usp_ArchiveAuditLogs";
            cmd.CommandType = CommandType.StoredProcedure;

            var paramCutoff = new SqlParameter("@CutoffDate", SqlDbType.DateTime2) { Value = cutoffDate };
            var paramBatch = new SqlParameter("@BatchSize", SqlDbType.Int) { Value = batchSize };
            var paramMax = new SqlParameter("@MaxRows", SqlDbType.Int) { Value = maxRows };
            var paramOutput = new SqlParameter("@ArchivedCount", SqlDbType.Int) { Direction = ParameterDirection.Output };

            cmd.Parameters.Add(paramCutoff);
            cmd.Parameters.Add(paramBatch);
            cmd.Parameters.Add(paramMax);
            cmd.Parameters.Add(paramOutput);

            await cmd.ExecuteNonQueryAsync(cancellationToken);

            if (paramOutput.Value != null && paramOutput.Value != DBNull.Value)
            {
                totalArchived = (int)paramOutput.Value;
            }

            _logger.LogInformation("Audited Change Log Archival: Archived {Count} records older than {Cutoff:u}.", totalArchived, cutoffDate);
        }
        catch (Exception ex)
        {
            _logger.LogError(ex, "Failed to execute audit archival stored procedure.");
            throw;
        }

        return totalArchived;
    }

    public async Task<int> ArchiveOlderThanMonthsAsync(
        int retentionMonths = 24,
        int batchSize = 5000,
        int maxRows = 50000,
        CancellationToken cancellationToken = default)
    {
        var cutoff = DateTime.UtcNow.AddMonths(-Math.Abs(retentionMonths));
        return await ExecuteBatchArchivalAsync(cutoff, batchSize, maxRows, cancellationToken);
    }

    public async Task<IReadOnlyList<ArchivedAuditLogDto>> QueryArchivedLogsAsync(
        string? entityName,
        long? recordId,
        DateTime? fromUtc,
        DateTime? toUtc,
        int take = 50,
        CancellationToken cancellationToken = default)
    {
        await using var context = await _contextFactory.CreateDbContextAsync();
        var conn = context.Database.GetDbConnection();
        if (conn.State != ConnectionState.Open)
            await conn.OpenAsync(cancellationToken);

        var list = new List<ArchivedAuditLogDto>();

        try
        {
            using var cmd = conn.CreateCommand();
            var sql = @"
                SELECT TOP (@Take) id, entity_name, record_id, action_name, actor, metadata_json, timestamp, archived_at
                FROM dbo.audit_logs_archive WITH (NOLOCK)
                WHERE (1 = 1) ";

            cmd.Parameters.Add(new SqlParameter("@Take", SqlDbType.Int) { Value = take });

            if (!string.IsNullOrWhiteSpace(entityName))
            {
                sql += " AND entity_name = @EntityName ";
                cmd.Parameters.Add(new SqlParameter("@EntityName", SqlDbType.NVarChar, 128) { Value = entityName });
            }

            if (recordId.HasValue)
            {
                sql += " AND record_id = @RecordId ";
                cmd.Parameters.Add(new SqlParameter("@RecordId", SqlDbType.BigInt) { Value = recordId.Value });
            }

            if (fromUtc.HasValue)
            {
                sql += " AND timestamp >= @FromUtc ";
                cmd.Parameters.Add(new SqlParameter("@FromUtc", SqlDbType.DateTime2) { Value = fromUtc.Value });
            }

            if (toUtc.HasValue)
            {
                sql += " AND timestamp <= @ToUtc ";
                cmd.Parameters.Add(new SqlParameter("@ToUtc", SqlDbType.DateTime2) { Value = toUtc.Value });
            }

            sql += " ORDER BY timestamp DESC;";
            cmd.CommandText = sql;

            using var reader = await cmd.ExecuteReaderAsync(cancellationToken);
            while (await reader.ReadAsync(cancellationToken))
            {
                list.Add(new ArchivedAuditLogDto
                {
                    Id = reader.GetInt64(0),
                    EntityName = reader.GetString(1),
                    RecordId = reader.GetInt64(2),
                    ActionName = reader.GetString(3),
                    Actor = reader.GetString(4),
                    MetadataJson = reader.IsDBNull(5) ? null : reader.GetString(5),
                    Timestamp = reader.GetDateTime(6),
                    ArchivedAt = reader.GetDateTime(7)
                });
            }
        }
        catch (Exception ex)
        {
            _logger.LogWarning(ex, "Error querying archived audit logs.");
        }

        return list;
    }
}
