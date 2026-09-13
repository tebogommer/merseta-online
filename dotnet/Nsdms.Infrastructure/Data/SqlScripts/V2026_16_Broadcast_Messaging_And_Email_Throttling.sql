-- =========================================================================================
-- MerSETA NSDMS Enterprise SQL Migration: Broadcast Messaging, Outbox Email Queue & Rate Limiting
-- Script: V2026_16_Broadcast_Messaging_And_Email_Throttling.sql
-- =========================================================================================

SET ANSI_NULLS ON;
SET QUOTED_IDENTIFIER ON;

-- 1. Upgrade SystemNotification Table with Rich Content and Physical Binary Attachment Metadata
IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[SystemNotification]') AND name = 'BodyHtml')
    ALTER TABLE [dbo].[SystemNotification] ADD [BodyHtml] NVARCHAR(MAX) NULL;

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[SystemNotification]') AND name = 'SenderDisplayName')
    ALTER TABLE [dbo].[SystemNotification] ADD [SenderDisplayName] NVARCHAR(150) NULL;

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[SystemNotification]') AND name = 'BroadcastMessageId')
    ALTER TABLE [dbo].[SystemNotification] ADD [BroadcastMessageId] INT NULL;

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[SystemNotification]') AND name = 'HasAttachment')
    ALTER TABLE [dbo].[SystemNotification] ADD [HasAttachment] BIT NOT NULL CONSTRAINT [DF_SystemNotification_HasAttachment] DEFAULT 0;

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[SystemNotification]') AND name = 'AttachmentFileName')
    ALTER TABLE [dbo].[SystemNotification] ADD [AttachmentFileName] NVARCHAR(255) NULL;

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[SystemNotification]') AND name = 'AttachmentStoragePath')
    ALTER TABLE [dbo].[SystemNotification] ADD [AttachmentStoragePath] NVARCHAR(1000) NULL;

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[SystemNotification]') AND name = 'AttachmentSizeBytes')
    ALTER TABLE [dbo].[SystemNotification] ADD [AttachmentSizeBytes] BIGINT NULL;

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[SystemNotification]') AND name = 'AttachmentContentType')
    ALTER TABLE [dbo].[SystemNotification] ADD [AttachmentContentType] NVARCHAR(100) NULL;

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID(N'[dbo].[SystemNotification]') AND name = 'IX_SystemNotification_BroadcastMessageId')
    CREATE NONCLUSTERED INDEX [IX_SystemNotification_BroadcastMessageId] ON [dbo].[SystemNotification] ([BroadcastMessageId]) WHERE [BroadcastMessageId] IS NOT NULL;

-- 2. BroadcastMessage Table
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'BroadcastMessage' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE [dbo].[BroadcastMessage]
    (
        [Id] INT IDENTITY(1,1) NOT NULL,
        [Subject] NVARCHAR(250) NOT NULL,
        [BodyHtml] NVARCHAR(MAX) NOT NULL,
        [TargetType] NVARCHAR(50) NOT NULL,
        [TargetFilterValue] NVARCHAR(150) NULL,
        [TargetFilterDisplay] NVARCHAR(250) NULL,
        [RecipientCount] INT NOT NULL DEFAULT 0,
        [HasAttachment] BIT NOT NULL DEFAULT 0,
        [AttachmentFileName] NVARCHAR(255) NULL,
        [AttachmentStoragePath] NVARCHAR(1000) NULL,
        [AttachmentSizeBytes] BIGINT NULL,
        [AttachmentContentType] NVARCHAR(100) NULL,
        [Status] NVARCHAR(50) NOT NULL DEFAULT 'Dispatched',
        [DispatchedAt] DATETIME2(7) NOT NULL DEFAULT SYSUTCDATETIME(),
        [DispatchedBy] NVARCHAR(150) NOT NULL DEFAULT 'SYSTEM',
        [CreatedAt] DATETIME2(7) NOT NULL DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(150) NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2(7) NULL,
        [ModifiedBy] NVARCHAR(150) NULL,
        CONSTRAINT [PK_BroadcastMessage] PRIMARY KEY CLUSTERED ([Id] ASC)
    );

    CREATE NONCLUSTERED INDEX [IX_BroadcastMessage_TargetType] ON [dbo].[BroadcastMessage] ([TargetType]);
    CREATE NONCLUSTERED INDEX [IX_BroadcastMessage_Status] ON [dbo].[BroadcastMessage] ([Status]);
    CREATE NONCLUSTERED INDEX [IX_BroadcastMessage_DispatchedAt] ON [dbo].[BroadcastMessage] ([DispatchedAt] DESC);
END;

-- 3. EmailOutboxItem Table (High-Volume BIGINT Identity)
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'EmailOutboxItem' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE [dbo].[EmailOutboxItem]
    (
        [Id] BIGINT IDENTITY(1,1) NOT NULL,
        [BroadcastMessageId] INT NULL,
        [RecipientEmail] NVARCHAR(255) NOT NULL,
        [RecipientName] NVARCHAR(200) NULL,
        [Subject] NVARCHAR(250) NOT NULL,
        [BodyHtml] NVARCHAR(MAX) NOT NULL,
        [HasAttachment] BIT NOT NULL DEFAULT 0,
        [AttachmentFileName] NVARCHAR(255) NULL,
        [AttachmentStoragePath] NVARCHAR(1000) NULL,
        [AttachmentContentType] NVARCHAR(100) NULL,
        [Status] NVARCHAR(50) NOT NULL DEFAULT 'Pending',
        [AttemptCount] INT NOT NULL DEFAULT 0,
        [MaxAttempts] INT NOT NULL DEFAULT 5,
        [NextAttemptAt] DATETIME2(7) NOT NULL DEFAULT SYSUTCDATETIME(),
        [SentAt] DATETIME2(7) NULL,
        [LastError] NVARCHAR(2000) NULL,
        [SourceModule] NVARCHAR(100) NOT NULL DEFAULT 'Broadcast',
        [SourceReferenceId] NVARCHAR(100) NULL,
        [CreatedAt] DATETIME2(7) NOT NULL DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(150) NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2(7) NULL,
        [ModifiedBy] NVARCHAR(150) NULL,
        CONSTRAINT [PK_EmailOutboxItem] PRIMARY KEY CLUSTERED ([Id] ASC),
        CONSTRAINT [FK_EmailOutboxItem_BroadcastMessage] FOREIGN KEY ([BroadcastMessageId]) REFERENCES [dbo].[BroadcastMessage] ([Id]) ON DELETE SET NULL
    );

    CREATE NONCLUSTERED INDEX [IX_EmailOutboxItem_Status_NextAttemptAt] ON [dbo].[EmailOutboxItem] ([Status], [NextAttemptAt]) INCLUDE ([AttemptCount], [MaxAttempts]);
    CREATE NONCLUSTERED INDEX [IX_EmailOutboxItem_BroadcastMessageId] ON [dbo].[EmailOutboxItem] ([BroadcastMessageId]);
    CREATE NONCLUSTERED INDEX [IX_EmailOutboxItem_RecipientEmail] ON [dbo].[EmailOutboxItem] ([RecipientEmail]);
    CREATE NONCLUSTERED INDEX [IX_EmailOutboxItem_CreatedAt] ON [dbo].[EmailOutboxItem] ([CreatedAt] DESC);
END;

-- 4. EmailDailyQuotaTracker Table (Office 365 10,000/day Limit Engine)
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'EmailDailyQuotaTracker' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE [dbo].[EmailDailyQuotaTracker]
    (
        [Id] INT IDENTITY(1,1) NOT NULL,
        [QuotaDate] DATE NOT NULL,
        [SentCount] INT NOT NULL DEFAULT 0,
        [ThrottledCount] INT NOT NULL DEFAULT 0,
        [FailedCount] INT NOT NULL DEFAULT 0,
        [DailyLimit] INT NOT NULL DEFAULT 10000,
        [IsLimitReached] BIT NOT NULL DEFAULT 0,
        [LastSentAt] DATETIME2(7) NULL,
        [CreatedAt] DATETIME2(7) NOT NULL DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(150) NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2(7) NULL,
        [ModifiedBy] NVARCHAR(150) NULL,
        CONSTRAINT [PK_EmailDailyQuotaTracker] PRIMARY KEY CLUSTERED ([Id] ASC)
    );

    CREATE UNIQUE NONCLUSTERED INDEX [UQ_EmailDailyQuotaTracker_QuotaDate] ON [dbo].[EmailDailyQuotaTracker] ([QuotaDate]);
END;
