-- ====================================================================================================
-- Script: V2026_09_Enable_Temporal_Tables_History_Schema.sql
-- Description: Enables SQL Server System-Versioned Temporal Tables for all core domain tables,
--              routing all point-in-time historical revisions into the dedicated [history] schema.
-- Environment: SQL Server 2016+ / SQL Server Express (localhost)
-- Database: NSDMS-NET
-- ====================================================================================================

-- 1. Ensure 'history' schema exists
IF NOT EXISTS (SELECT 1 FROM sys.schemas WHERE name = 'history')
BEGIN
    EXEC('CREATE SCHEMA [history]');
    PRINT 'Schema [history] created successfully.';
END
ELSE
BEGIN
    PRINT 'Schema [history] already exists.';
END
GO

-- 2. Enable Temporal Tables across all core domain tables
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
        -- Check and add PeriodStart and PeriodEnd hidden datetime2 columns
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
            PRINT 'Added system-time period columns to [dbo].[' + @CurrentTable + '].';
        END

        -- Enable system versioning with history table in [history] schema
        DECLARE @historyTable NVARCHAR(128) = @CurrentTable + N'History';
        DECLARE @sqlTemporal NVARCHAR(MAX) = N'
            ALTER TABLE [dbo].' + QUOTENAME(@CurrentTable) + N' 
            SET (SYSTEM_VERSIONING = ON (HISTORY_TABLE = [history].' + QUOTENAME(@historyTable) + N'));';
        EXEC sp_executesql @sqlTemporal;
        PRINT 'Enabled SYSTEM_VERSIONING on [dbo].[' + @CurrentTable + '] -> [history].[' + @historyTable + '].';
    END TRY
    BEGIN CATCH
        PRINT 'Warning: Could not enable temporal table for ' + @CurrentTable + ': ' + ERROR_MESSAGE();
    END CATCH

    FETCH NEXT FROM table_cursor INTO @CurrentTable;
END

CLOSE table_cursor;
DEALLOCATE table_cursor;
GO

PRINT '====================================================================================';
PRINT 'SQL Server System-Versioned Temporal Tables successfully configured in history schema.';
PRINT '====================================================================================';
