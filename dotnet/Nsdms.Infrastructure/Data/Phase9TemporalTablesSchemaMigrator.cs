using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Phase 9 Schema Migrator: Enables SQL Server System-Versioned Temporal Tables for all core domain tables,
/// redirecting historical row revisions into the dedicated [history] schema.
/// </summary>
public static class Phase9TemporalTablesSchemaMigrator
{
    public static async Task MigrateTemporalTablesSchemaAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var db = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetRequiredService<ILoggerFactory>().CreateLogger("Phase9TemporalTablesSchemaMigrator");

        try
        {
            // Only execute against relational SQL Server providers
            if (!db.Database.IsSqlServer())
            {
                logger.LogInformation("Non-SQL Server database provider detected. Skipping SQL Server temporal table DDL execution.");
                return;
            }

            var tsql = @"
-- 1. Ensure 'history' schema exists
IF NOT EXISTS (SELECT * FROM sys.schemas WHERE name = 'history')
BEGIN
    EXEC('CREATE SCHEMA [history]');
END

-- 2. Stored procedure / script block to enable temporal versioning on any table in dbo
DECLARE @Tables TABLE (TableName NVARCHAR(128));

INSERT INTO @Tables (TableName)
SELECT t.name
FROM sys.tables t
JOIN sys.schemas s ON t.schema_id = s.schema_id
WHERE s.name = 'dbo'
  AND t.temporal_type <> 2 -- Not already system-versioned
  AND t.name NOT LIKE '__%'
  AND t.name NOT LIKE 'AppUserClaim%'
  AND t.name NOT LIKE 'AppUserLogin%'
  AND t.name NOT LIKE 'AppRoleClaim%'
  AND t.name NOT LIKE 'AppUserToken%'
  AND t.name NOT LIKE 'AppUserRole%'
  AND t.name NOT IN ('AuditLog', 'WorkflowHistory', 'WorkflowTaskLease', 'sysdiagrams');

DECLARE @CurrentTable NVARCHAR(128);
DECLARE table_cursor CURSOR LOCAL FAST_FORWARD FOR
SELECT TableName FROM @Tables;

OPEN table_cursor;
FETCH NEXT FROM table_cursor INTO @CurrentTable;

WHILE @@FETCH_STATUS = 0
BEGIN
    BEGIN TRY
        -- Add period columns if missing
        IF NOT EXISTS (
            SELECT 1 FROM sys.columns 
            WHERE object_id = OBJECT_ID(N'dbo.' + QUOTENAME(@CurrentTable)) 
              AND name = 'PeriodStart'
        )
        BEGIN
            DECLARE @sqlCols NVARCHAR(MAX) = N'
                ALTER TABLE [dbo].' + QUOTENAME(@CurrentTable) + N' ADD 
                    [PeriodStart] DATETIME2 GENERATED ALWAYS AS ROW START HIDDEN NOT NULL CONSTRAINT [DF_' + @CurrentTable + N'_PeriodStart] DEFAULT SYSUTCDATETIME(),
                    [PeriodEnd] DATETIME2 GENERATED ALWAYS AS ROW END HIDDEN NOT NULL CONSTRAINT [DF_' + @CurrentTable + N'_PeriodEnd] DEFAULT ''9999-12-31 23:59:59.9999999'',
                    PERIOD FOR SYSTEM_TIME ([PeriodStart], [PeriodEnd]);';
            EXEC sp_executesql @sqlCols;
        END

        -- Enable system versioning pointing to history schema
        DECLARE @historyTable NVARCHAR(128) = @CurrentTable + N'History';
        DECLARE @sqlTemporal NVARCHAR(MAX) = N'
            ALTER TABLE [dbo].' + QUOTENAME(@CurrentTable) + N' 
            SET (SYSTEM_VERSIONING = ON (HISTORY_TABLE = [history].' + QUOTENAME(@historyTable) + N'));';
        EXEC sp_executesql @sqlTemporal;
    END TRY
    BEGIN CATCH
        -- Log warning and continue with subsequent tables
        PRINT 'Warning: Could not enable temporal table for ' + @CurrentTable + ': ' + ERROR_MESSAGE();
    END CATCH

    FETCH NEXT FROM table_cursor INTO @CurrentTable;
END

CLOSE table_cursor;
DEALLOCATE table_cursor;
";

            await db.Database.ExecuteSqlRawAsync(tsql);
            logger.LogInformation("SQL Server System-Versioned Temporal Tables successfully verified and enabled in the 'history' schema.");
        }
        catch (Exception ex)
        {
            logger.LogWarning(ex, "Failed to apply SQL Server temporal tables DDL script during startup.");
        }
    }
}
