using System.Data;
using Microsoft.Data.SqlClient;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// High-performance idempotent schema migration journal that tracks executed migrator phases in [dbo].[__CustomSchemaJournal].
/// Eliminates cold-start stalls and multi-instance DDL locking by skipping already executed phases in sub-millisecond time.
/// </summary>
public static class SchemaMigrationJournal
{
    private static readonly HashSet<string> _appliedMigrations = new(StringComparer.OrdinalIgnoreCase);
    private static bool _isInitialized = false;
    private static readonly object _lock = new();

    public static void Initialize(DbContext context, ILogger logger)
    {
        lock (_lock)
        {
            if (_isInitialized) return;

            try
            {
                var conn = context.Database.GetDbConnection();
                if (conn.State != ConnectionState.Open)
                {
                    conn.Open();
                }

                using var cmd = conn.CreateCommand();
                cmd.CommandText = @"
                    IF OBJECT_ID('dbo.__CustomSchemaJournal', 'U') IS NULL
                    BEGIN
                        CREATE TABLE dbo.__CustomSchemaJournal (
                            MigrationName NVARCHAR(128) NOT NULL CONSTRAINT PK___CustomSchemaJournal PRIMARY KEY,
                            AppliedAtUtc DATETIME2(7) NOT NULL CONSTRAINT DF___CustomSchemaJournal_AppliedAt DEFAULT SYSUTCDATETIME(),
                            ExecutionDurationMs BIGINT NOT NULL CONSTRAINT DF___CustomSchemaJournal_Duration DEFAULT 0
                        );
                    END;

                    SELECT MigrationName FROM dbo.__CustomSchemaJournal WITH (NOLOCK);";

                using var reader = cmd.ExecuteReader();
                while (reader.Read())
                {
                    _appliedMigrations.Add(reader.GetString(0));
                }

                _isInitialized = true;
                logger.LogInformation("SchemaMigrationJournal initialized. {Count} recorded migration phases loaded into memory.", _appliedMigrations.Count);
            }
            catch (Exception ex)
            {
                logger.LogWarning(ex, "Could not initialize SchemaMigrationJournal. Falling back to non-journaled execution.");
            }
        }
    }

    public static bool IsApplied(string migrationName)
    {
        lock (_lock)
        {
            return _appliedMigrations.Contains(migrationName);
        }
    }

    public static void RecordApplied(DbContext context, string migrationName, long elapsedMs, ILogger logger)
    {
        lock (_lock)
        {
            try
            {
                var conn = context.Database.GetDbConnection();
                if (conn.State != ConnectionState.Open)
                {
                    conn.Open();
                }

                using var cmd = conn.CreateCommand();
                cmd.CommandText = @"
                    IF NOT EXISTS (SELECT 1 FROM dbo.__CustomSchemaJournal WHERE MigrationName = @MigrationName)
                    BEGIN
                        INSERT INTO dbo.__CustomSchemaJournal (MigrationName, AppliedAtUtc, ExecutionDurationMs)
                        VALUES (@MigrationName, SYSUTCDATETIME(), @Duration);
                    END";

                var paramName = cmd.CreateParameter();
                paramName.ParameterName = "@MigrationName";
                paramName.Value = migrationName;
                cmd.Parameters.Add(paramName);

                var paramDuration = cmd.CreateParameter();
                paramDuration.ParameterName = "@Duration";
                paramDuration.Value = elapsedMs;
                cmd.Parameters.Add(paramDuration);

                cmd.ExecuteNonQuery();
                _appliedMigrations.Add(migrationName);
            }
            catch (Exception ex)
            {
                logger.LogWarning(ex, "Failed to record migration {MigrationName} in __CustomSchemaJournal.", migrationName);
            }
        }
    }
}
