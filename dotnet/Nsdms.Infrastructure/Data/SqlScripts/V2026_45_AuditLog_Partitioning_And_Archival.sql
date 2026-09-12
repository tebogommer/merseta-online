-- ===================================================================================
-- Migration: V2026_45_AuditLog_Partitioning_And_Archival.sql
-- Description: SQL Server Partitioning & Tiered Archival Infrastructure for AuditLog.
-- Creates quarterly partition boundaries, archival table, and batch archival procedure.
-- ===================================================================================

PRINT '--- Beginning V2026_45 AuditLog Partitioning & Archival Migration ---';

-- 1. Create Partition Function if not exists
IF NOT EXISTS (SELECT 1 FROM sys.partition_functions WHERE name = 'PF_AuditLog_Timestamp')
BEGIN
    PRINT 'Creating Partition Function [PF_AuditLog_Timestamp]...';
    CREATE PARTITION FUNCTION PF_AuditLog_Timestamp (DATETIME2(7))
    AS RANGE RIGHT FOR VALUES (
        '2025-01-01T00:00:00.000',
        '2025-04-01T00:00:00.000',
        '2025-07-01T00:00:00.000',
        '2025-10-01T00:00:00.000',
        '2026-01-01T00:00:00.000',
        '2026-04-01T00:00:00.000',
        '2026-07-01T00:00:00.000',
        '2026-10-01T00:00:00.000',
        '2027-01-01T00:00:00.000'
    );
    PRINT 'Partition Function [PF_AuditLog_Timestamp] created successfully.';
END
ELSE
BEGIN
    PRINT 'Partition Function [PF_AuditLog_Timestamp] already exists.';
END;

-- 2. Create Partition Scheme if not exists
IF NOT EXISTS (SELECT 1 FROM sys.partition_schemes WHERE name = 'PS_AuditLog_Timestamp')
BEGIN
    PRINT 'Creating Partition Scheme [PS_AuditLog_Timestamp]...';
    CREATE PARTITION SCHEME PS_AuditLog_Timestamp
    AS PARTITION PF_AuditLog_Timestamp
    ALL TO ([PRIMARY]);
    PRINT 'Partition Scheme [PS_AuditLog_Timestamp] created successfully.';
END
ELSE
BEGIN
    PRINT 'Partition Scheme [PS_AuditLog_Timestamp] already exists.';
END;

-- 3. Create Tiered Archival Table dbo.audit_logs_archive
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'audit_logs_archive' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    PRINT 'Creating Tiered Archival Table [dbo].[audit_logs_archive]...';
    CREATE TABLE dbo.audit_logs_archive (
        id BIGINT NOT NULL,
        entity_name NVARCHAR(128) NOT NULL,
        record_id BIGINT NOT NULL,
        action_name NVARCHAR(128) NOT NULL,
        actor NVARCHAR(128) NOT NULL,
        metadata_json NVARCHAR(MAX) NULL,
        timestamp DATETIME2(7) NOT NULL,
        archived_at DATETIME2(7) NOT NULL CONSTRAINT DF_audit_logs_archive_archived_at DEFAULT SYSUTCDATETIME(),
        CONSTRAINT PK_audit_logs_archive PRIMARY KEY NONCLUSTERED (id, timestamp)
    );

    CREATE CLUSTERED INDEX CIX_audit_logs_archive_timestamp 
        ON dbo.audit_logs_archive (timestamp);

    CREATE NONCLUSTERED INDEX IX_audit_logs_archive_entity 
        ON dbo.audit_logs_archive (entity_name, record_id);

    CREATE NONCLUSTERED INDEX IX_audit_logs_archive_actor 
        ON dbo.audit_logs_archive (actor);

    PRINT 'Tiered Archival Table [dbo].[audit_logs_archive] created successfully.';
END
ELSE
BEGIN
    PRINT 'Table [dbo].[audit_logs_archive] already exists.';
END;

-- 4. Create Batch Archival Stored Procedure usp_ArchiveAuditLogs
IF EXISTS (SELECT 1 FROM sys.procedures WHERE name = 'usp_ArchiveAuditLogs' AND schema_id = SCHEMA_ID('dbo'))
    DROP PROCEDURE dbo.usp_ArchiveAuditLogs;
GO

CREATE PROCEDURE dbo.usp_ArchiveAuditLogs
    @CutoffDate DATETIME2(7),
    @BatchSize INT = 5000,
    @MaxRows INT = 50000,
    @ArchivedCount INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    SET @ArchivedCount = 0;
    
    DECLARE @BatchCount INT = 1;
    
    WHILE @BatchCount > 0 AND @ArchivedCount < @MaxRows
    BEGIN
        BEGIN TRANSACTION;
        
        -- Copy batch to archive
        INSERT INTO dbo.audit_logs_archive (id, entity_name, record_id, action_name, actor, metadata_json, timestamp, archived_at)
        SELECT TOP (@BatchSize) id, entity_name, record_id, action_name, actor, metadata_json, timestamp, SYSUTCDATETIME()
        FROM dbo.audit_logs WITH (READPAST)
        WHERE timestamp < @CutoffDate
          AND id NOT IN (SELECT id FROM dbo.audit_logs_archive)
        ORDER BY timestamp ASC;
        
        SET @BatchCount = @@ROWCOUNT;
        
        IF @BatchCount > 0
        BEGIN
            -- Delete the archived records from active audit_logs
            DELETE FROM dbo.audit_logs
            WHERE id IN (
                SELECT TOP (@BatchSize) id 
                FROM dbo.audit_logs_archive 
                WHERE timestamp < @CutoffDate
                ORDER BY archived_at DESC
            );
            
            SET @ArchivedCount = @ArchivedCount + @BatchCount;
        END;
        
        COMMIT TRANSACTION;
        
        IF @BatchCount < @BatchSize
            BREAK;
    END;
END;
GO

-- 5. Create Diagnostic Partition View
IF EXISTS (SELECT 1 FROM sys.views WHERE name = 'vw_AuditLogPartitionStats' AND schema_id = SCHEMA_ID('dbo'))
    DROP VIEW dbo.vw_AuditLogPartitionStats;
GO

CREATE VIEW dbo.vw_AuditLogPartitionStats AS
SELECT 
    t.name AS TableName,
    p.partition_number AS PartitionNumber,
    p.rows AS RowCounts,
    prv.value AS BoundaryValue,
    fg.name AS FileGroupName
FROM sys.partitions p
INNER JOIN sys.tables t ON p.object_id = t.object_id
INNER JOIN sys.indexes i ON p.object_id = i.object_id AND p.index_id = i.index_id
LEFT JOIN sys.partition_schemes ps ON i.data_space_id = ps.data_space_id
LEFT JOIN sys.partition_functions pf ON ps.function_id = pf.function_id
LEFT JOIN sys.partition_range_values prv ON pf.function_id = prv.function_id AND prv.boundary_id = p.partition_number
LEFT JOIN sys.destination_data_spaces dds ON ps.data_space_id = dds.partition_scheme_id AND dds.destination_id = p.partition_number
LEFT JOIN sys.filegroups fg ON dds.data_space_id = fg.data_space_id
WHERE t.name IN ('audit_logs', 'audit_logs_archive')
  AND i.type IN (0, 1);
GO

PRINT '--- V2026_45 AuditLog Partitioning & Archival Migration Completed ---';
