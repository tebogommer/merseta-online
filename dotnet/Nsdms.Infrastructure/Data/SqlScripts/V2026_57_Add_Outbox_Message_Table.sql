-- ============================================================================
-- V2026_57_Add_Outbox_Message_Table.sql
-- Transactional Outbox Pattern Schema Migration
-- ============================================================================

IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'OutboxMessage' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE [dbo].[OutboxMessage] (
        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_OutboxMessage] PRIMARY KEY CLUSTERED,
        [EventType] NVARCHAR(250) NOT NULL,
        [PayloadJson] NVARCHAR(MAX) NOT NULL,
        [ProcessedAt] DATETIME2(7) NULL,
        [ErrorMessage] NVARCHAR(MAX) NULL,
        [RetryCount] INT NOT NULL CONSTRAINT [DF_OutboxMessage_RetryCount] DEFAULT (0),
        [CreatedAt] DATETIME2(7) NOT NULL CONSTRAINT [DF_OutboxMessage_CreatedAt] DEFAULT (SYSUTCDATETIME()),
        [CreatedBy] NVARCHAR(100) NOT NULL CONSTRAINT [DF_OutboxMessage_CreatedBy] DEFAULT ('SYSTEM'),
        [ModifiedAt] DATETIME2(7) NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );

    CREATE NONCLUSTERED INDEX [IX_OutboxMessage_ProcessedAt]
        ON [dbo].[OutboxMessage] ([ProcessedAt])
        INCLUDE ([Id], [EventType], [RetryCount], [CreatedAt]);

    PRINT 'Created table [dbo].[OutboxMessage].';
END
ELSE
BEGIN
    PRINT 'Table [dbo].[OutboxMessage] already exists.';
END
GO
