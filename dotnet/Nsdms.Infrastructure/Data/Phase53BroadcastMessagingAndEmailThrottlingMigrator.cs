using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Idempotent database schema migrator for Broadcast Messaging, Outbox Email Queue, and Office 365 Rate Limiting.
/// Provisions tables: BroadcastMessage, EmailOutboxItem, EmailDailyQuotaTracker, and upgrades SystemNotification.
/// </summary>
public static class Phase53BroadcastMessagingAndEmailThrottlingMigrator
{
    public static async Task MigrateAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        if (context.Database.IsSqlServer())
        {
            var sqlFile = Path.Combine(AppContext.BaseDirectory, "Data", "SqlScripts", "V2026_16_Broadcast_Messaging_And_Email_Throttling.sql");
            if (!File.Exists(sqlFile))
            {
                sqlFile = Path.Combine(AppContext.BaseDirectory, "..", "..", "..", "..", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_16_Broadcast_Messaging_And_Email_Throttling.sql");
            }

            if (File.Exists(sqlFile))
            {
                var sql = await File.ReadAllTextAsync(sqlFile);
                await context.Database.ExecuteSqlRawAsync(sql);
                logger?.LogInformation("Phase 53 Broadcast Messaging & Email Throttling DDL executed successfully from {Path}.", sqlFile);
            }
            else
            {
                const string inlineSql = @"
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

IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'BroadcastMessage' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE [dbo].[BroadcastMessage] (
        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_BroadcastMessage] PRIMARY KEY CLUSTERED,
        [Subject] NVARCHAR(300) NOT NULL,
        [BodyHtml] NVARCHAR(MAX) NOT NULL,
        [TargetType] NVARCHAR(50) NOT NULL CONSTRAINT [DF_BroadcastMessage_TargetType] DEFAULT 'Role',
        [TargetFilterValue] NVARCHAR(150) NULL,
        [TargetFilterDisplay] NVARCHAR(250) NOT NULL,
        [RecipientCount] INT NOT NULL CONSTRAINT [DF_BroadcastMessage_RecipientCount] DEFAULT 0,
        [HasAttachment] BIT NOT NULL CONSTRAINT [DF_BroadcastMessage_HasAttachment] DEFAULT 0,
        [AttachmentFileName] NVARCHAR(255) NULL,
        [AttachmentContentType] NVARCHAR(100) NULL,
        [AttachmentStoragePath] NVARCHAR(1000) NULL,
        [AttachmentSizeBytes] BIGINT NULL,
        [Status] NVARCHAR(30) NOT NULL CONSTRAINT [DF_BroadcastMessage_Status] DEFAULT 'Dispatched',
        [DispatchedAt] DATETIME2 NULL,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_BroadcastMessage_CreatedAt] DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(150) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(150) NULL
    );
END

IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'EmailOutboxItem' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE [dbo].[EmailOutboxItem] (
        [Id] BIGINT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_EmailOutboxItem] PRIMARY KEY CLUSTERED,
        [BroadcastMessageId] INT NULL,
        [RecipientEmail] NVARCHAR(255) NOT NULL,
        [RecipientName] NVARCHAR(255) NULL,
        [RecipientUsername] NVARCHAR(150) NULL,
        [Subject] NVARCHAR(300) NOT NULL,
        [BodyHtml] NVARCHAR(MAX) NOT NULL,
        [IsHtml] BIT NOT NULL CONSTRAINT [DF_EmailOutboxItem_IsHtml] DEFAULT 1,
        [SourceModule] NVARCHAR(50) NOT NULL CONSTRAINT [DF_EmailOutboxItem_SourceModule] DEFAULT 'Broadcast',
        [ReferenceEntityId] INT NULL,
        [ReferenceEntityType] NVARCHAR(100) NULL,
        [ActionUrl] NVARCHAR(500) NULL,
        [HasAttachment] BIT NOT NULL CONSTRAINT [DF_EmailOutboxItem_HasAttachment] DEFAULT 0,
        [AttachmentFileName] NVARCHAR(255) NULL,
        [AttachmentStoragePath] NVARCHAR(1000) NULL,
        [AttachmentContentType] NVARCHAR(100) NULL,
        [Status] NVARCHAR(30) NOT NULL CONSTRAINT [DF_EmailOutboxItem_Status] DEFAULT 'Pending',
        [AttemptCount] INT NOT NULL CONSTRAINT [DF_EmailOutboxItem_AttemptCount] DEFAULT 0,
        [MaxAttempts] INT NOT NULL CONSTRAINT [DF_EmailOutboxItem_MaxAttempts] DEFAULT 5,
        [LastError] NVARCHAR(2000) NULL,
        [NextAttemptAt] DATETIME2 NULL,
        [SentAt] DATETIME2 NULL,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_EmailOutboxItem_CreatedAt] DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(150) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(150) NULL
    );
END

IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'EmailDailyQuotaTracker' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE [dbo].[EmailDailyQuotaTracker] (
        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_EmailDailyQuotaTracker] PRIMARY KEY CLUSTERED,
        [QuotaDate] DATE NOT NULL,
        [SentCount] INT NOT NULL CONSTRAINT [DF_EmailDailyQuotaTracker_SentCount] DEFAULT 0,
        [ThrottledCount] INT NOT NULL CONSTRAINT [DF_EmailDailyQuotaTracker_ThrottledCount] DEFAULT 0,
        [FailedCount] INT NOT NULL CONSTRAINT [DF_EmailDailyQuotaTracker_FailedCount] DEFAULT 0,
        [DailyLimit] INT NOT NULL CONSTRAINT [DF_EmailDailyQuotaTracker_DailyLimit] DEFAULT 10000,
        [IsLimitReached] BIT NOT NULL CONSTRAINT [DF_EmailDailyQuotaTracker_IsLimitReached] DEFAULT 0,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_EmailDailyQuotaTracker_CreatedAt] DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(150) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(150) NULL
    );
    CREATE UNIQUE NONCLUSTERED INDEX [IX_EmailDailyQuotaTracker_QuotaDate] ON [dbo].[EmailDailyQuotaTracker]([QuotaDate]);
END
";
                await context.Database.ExecuteSqlRawAsync(inlineSql);
                logger?.LogInformation("Phase 53 Broadcast Messaging & Email Throttling inline DDL applied successfully.");
            }
        }
    }
}
