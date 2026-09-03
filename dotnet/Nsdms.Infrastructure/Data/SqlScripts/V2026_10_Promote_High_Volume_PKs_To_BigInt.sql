-- =========================================================================================
-- NSDMS Migration: V2026_10_Promote_High_Volume_PKs_To_BigInt.sql
-- Description: Promotes high-volume transaction primary keys and audit RecordId to 64-bit BIGINT
--              to prevent identity overflow on SARS levy records, WSP plans, and audit trails.
-- =========================================================================================

SET NOCOUNT ON;

PRINT 'Starting migration: V2026_10_Promote_High_Volume_PKs_To_BigInt...';

-- 1. Promote AuditLog / audit_logs RecordId to BIGINT
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = 'AuditLog' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    IF EXISTS (
        SELECT 1 FROM sys.columns c
        JOIN sys.types t ON c.user_type_id = t.user_type_id
        WHERE c.object_id = OBJECT_ID('dbo.AuditLog')
          AND c.name = 'RecordId'
          AND t.name = 'int'
    )
    BEGIN
        PRINT 'Promoting dbo.AuditLog.RecordId from INT to BIGINT...';

        -- Drop composite index if exists
        IF EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_AuditLog_EntityName_RecordId' AND object_id = OBJECT_ID('dbo.AuditLog'))
        BEGIN
            DROP INDEX [IX_AuditLog_EntityName_RecordId] ON [dbo].[AuditLog];
        END

        ALTER TABLE [dbo].[AuditLog] ALTER COLUMN [RecordId] BIGINT NOT NULL;

        CREATE NONCLUSTERED INDEX [IX_AuditLog_EntityName_RecordId] 
        ON [dbo].[AuditLog] ([EntityName], [RecordId]);

        PRINT 'Successfully altered dbo.AuditLog.RecordId to BIGINT.';
    END
END

IF EXISTS (SELECT 1 FROM sys.tables WHERE name = 'audit_logs' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    IF EXISTS (
        SELECT 1 FROM sys.columns c
        JOIN sys.types t ON c.user_type_id = t.user_type_id
        WHERE c.object_id = OBJECT_ID('dbo.audit_logs')
          AND c.name = 'record_id'
          AND t.name = 'int'
    )
    BEGIN
        PRINT 'Promoting dbo.audit_logs.record_id from INT to BIGINT...';

        IF EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_audit_logs_entity' AND object_id = OBJECT_ID('dbo.audit_logs'))
        BEGIN
            DROP INDEX [IX_audit_logs_entity] ON [dbo].[audit_logs];
        END

        ALTER TABLE [dbo].[audit_logs] ALTER COLUMN [record_id] BIGINT NOT NULL;

        CREATE NONCLUSTERED INDEX [IX_audit_logs_entity] 
        ON [dbo].[audit_logs] ([entity_name], [record_id]);

        PRINT 'Successfully altered dbo.audit_logs.record_id to BIGINT.';
    END
END

PRINT 'Completed migration: V2026_10_Promote_High_Volume_PKs_To_BigInt.';
