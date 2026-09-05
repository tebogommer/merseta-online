-- ==============================================================================
-- V2026_18_Erp_Outbox_Queue.sql
-- Transactional Outbox Pattern for Microsoft Dynamics GP Web Services Integration
-- Supports Resilient Asynchronous Execution, Auto-Pause on Outage & FIFO Resume
-- ==============================================================================

IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = N'ErpOutboxMessage' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE [dbo].[ErpOutboxMessage] (
        [Id] INT IDENTITY(1,1) NOT NULL,
        [MessageCorrelationId] NVARCHAR(64) NOT NULL,
        [MessageType] NVARCHAR(50) NOT NULL,
        [ReferenceKey] NVARCHAR(100) NOT NULL,
        [OrganisationId] INT NULL,
        [PayloadJson] NVARCHAR(MAX) NOT NULL CONSTRAINT DF_ErpOutboxMessage_Payload DEFAULT '{}',
        [QueueStatusCode] NVARCHAR(50) NOT NULL CONSTRAINT DF_ErpOutboxMessage_Status DEFAULT 'Pending',
        [RetryCount] INT NOT NULL CONSTRAINT DF_ErpOutboxMessage_Retry DEFAULT 0,
        [MaxRetries] INT NOT NULL CONSTRAINT DF_ErpOutboxMessage_MaxRetries DEFAULT 5,
        [NextAttemptAtUtc] DATETIME2 NOT NULL CONSTRAINT DF_ErpOutboxMessage_NextAttempt DEFAULT SYSUTCDATETIME(),
        [LastAttemptAtUtc] DATETIME2 NULL,
        [DeliveredAtUtc] DATETIME2 NULL,
        [LastError] NVARCHAR(MAX) NULL,
        [TransactionReference] NVARCHAR(100) NULL,
        [GpBatchNumber] NVARCHAR(100) NULL,
        [LockToken] NVARCHAR(100) NULL,
        [LockExpiresAtUtc] DATETIME2 NULL,
        [ExecutionPriority] INT NOT NULL CONSTRAINT DF_ErpOutboxMessage_Priority DEFAULT 2,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT DF_ErpOutboxMessage_CreatedAt DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT PK_ErpOutboxMessage PRIMARY KEY CLUSTERED ([Id] ASC)
    );
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'IX_ErpOutboxMessage_Status_NextAttempt' AND object_id = OBJECT_ID(N'dbo.ErpOutboxMessage'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_ErpOutboxMessage_Status_NextAttempt]
    ON [dbo].[ErpOutboxMessage] ([QueueStatusCode], [NextAttemptAtUtc], [ExecutionPriority]);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'IX_ErpOutboxMessage_CorrelationId' AND object_id = OBJECT_ID(N'dbo.ErpOutboxMessage'))
BEGIN
    CREATE UNIQUE NONCLUSTERED INDEX [IX_ErpOutboxMessage_CorrelationId]
    ON [dbo].[ErpOutboxMessage] ([MessageCorrelationId]);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'IX_ErpOutboxMessage_Type_Ref' AND object_id = OBJECT_ID(N'dbo.ErpOutboxMessage'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_ErpOutboxMessage_Type_Ref]
    ON [dbo].[ErpOutboxMessage] ([MessageType], [ReferenceKey]);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'IX_ErpOutboxMessage_OrganisationId' AND object_id = OBJECT_ID(N'dbo.ErpOutboxMessage'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_ErpOutboxMessage_OrganisationId]
    ON [dbo].[ErpOutboxMessage] ([OrganisationId]);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = N'FK_ErpOutboxMessage_Organisation')
   AND EXISTS (SELECT 1 FROM sys.tables WHERE name = N'Organisation' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    ALTER TABLE [dbo].[ErpOutboxMessage]
    ADD CONSTRAINT FK_ErpOutboxMessage_Organisation
    FOREIGN KEY ([OrganisationId]) REFERENCES [dbo].[Organisation] ([Id])
    ON DELETE SET NULL;
END
GO
