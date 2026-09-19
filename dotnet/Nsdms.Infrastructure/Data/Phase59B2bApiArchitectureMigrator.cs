using System;
using System.IO;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Phase 59: B2B API Architecture & Webhook Event Engine Migrator.
/// Provisions ApiClient, ApiWebhookSubscription, ApiWebhookDeliveryLog, and ApiIdempotencyRecord tables and indexes.
/// </summary>
public static class Phase59B2bApiArchitectureMigrator
{
    public static async Task MigrateAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        if (context.Database.IsSqlServer())
        {
            try
            {
                var scriptPath = Path.Combine(AppContext.BaseDirectory, "Data", "SqlScripts", "V2026_31_Phase59_B2b_Api_Architecture.sql");
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "dotnet", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_31_Phase59_B2b_Api_Architecture.sql");
                }
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "..", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_31_Phase59_B2b_Api_Architecture.sql");
                }

                if (File.Exists(scriptPath))
                {
                    var sql = await File.ReadAllTextAsync(scriptPath);
                    await SqlBatchRunner.ExecuteBatchesAsync(context, sql, logger);
                    logger?.LogInformation("Phase 59: Executed V2026_31_Phase59_B2b_Api_Architecture.sql successfully from file.");
                }
                else
                {
                    // Fallback embedded DDL
                    const string embeddedDdl = @"
                        IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'ApiClient' AND schema_id = SCHEMA_ID('dbo'))
                        BEGIN
                            CREATE TABLE [dbo].[ApiClient] (
                                [Id] INT IDENTITY(1,1) NOT NULL,
                                [OrganisationId] INT NOT NULL,
                                [ClientIdentifier] NVARCHAR(100) NOT NULL,
                                [ClientName] NVARCHAR(200) NOT NULL,
                                [HashedClientSecret] NVARCHAR(256) NOT NULL,
                                [AllowedScopes] NVARCHAR(1000) NOT NULL CONSTRAINT [DF_ApiClient_AllowedScopes] DEFAULT ('wsp:write,workforce:sync,claims:submit,learners:register,trade_tests:write,verify:read'),
                                [DpopPublicKeyJwk] NVARCHAR(4000) NULL,
                                [ClientCertificateThumbprint] NVARCHAR(100) NULL,
                                [Tier] NVARCHAR(50) NOT NULL CONSTRAINT [DF_ApiClient_Tier] DEFAULT ('Enterprise'),
                                [RateLimitPerMinute] INT NOT NULL CONSTRAINT [DF_ApiClient_RateLimitPerMinute] DEFAULT (120),
                                [IsActive] BIT NOT NULL CONSTRAINT [DF_ApiClient_IsActive] DEFAULT (1),
                                [LastUsedAt] DATETIME2 NULL,
                                [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_ApiClient_CreatedAt] DEFAULT (SYSUTCDATETIME()),
                                [CreatedBy] NVARCHAR(100) NULL CONSTRAINT [DF_ApiClient_CreatedBy] DEFAULT ('SYSTEM'),
                                [ModifiedAt] DATETIME2 NULL,
                                [ModifiedBy] NVARCHAR(100) NULL,
                                CONSTRAINT [PK_ApiClient] PRIMARY KEY CLUSTERED ([Id] ASC)
                            );
                        END
                        GO

                        IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_ApiClient_ClientIdentifier' AND object_id = OBJECT_ID('dbo.ApiClient'))
                        BEGIN
                            CREATE UNIQUE NONCLUSTERED INDEX [IX_ApiClient_ClientIdentifier] ON [dbo].[ApiClient] ([ClientIdentifier]);
                        END
                        GO

                        IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'ApiWebhookSubscription' AND schema_id = SCHEMA_ID('dbo'))
                        BEGIN
                            CREATE TABLE [dbo].[ApiWebhookSubscription] (
                                [Id] INT IDENTITY(1,1) NOT NULL,
                                [OrganisationId] INT NOT NULL,
                                [EventTopic] NVARCHAR(100) NOT NULL,
                                [TargetUrl] NVARCHAR(1000) NOT NULL,
                                [SecretKey] NVARCHAR(256) NOT NULL,
                                [IsActive] BIT NOT NULL CONSTRAINT [DF_ApiWebhookSubscription_IsActive] DEFAULT (1),
                                [LastTriggeredAt] DATETIME2 NULL,
                                [FailureCount] INT NOT NULL CONSTRAINT [DF_ApiWebhookSubscription_FailureCount] DEFAULT (0),
                                [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_ApiWebhookSubscription_CreatedAt] DEFAULT (SYSUTCDATETIME()),
                                [CreatedBy] NVARCHAR(100) NULL CONSTRAINT [DF_ApiWebhookSubscription_CreatedBy] DEFAULT ('SYSTEM'),
                                [ModifiedAt] DATETIME2 NULL,
                                [ModifiedBy] NVARCHAR(100) NULL,
                                CONSTRAINT [PK_ApiWebhookSubscription] PRIMARY KEY CLUSTERED ([Id] ASC)
                            );
                        END
                        GO

                        IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'ApiWebhookDeliveryLog' AND schema_id = SCHEMA_ID('dbo'))
                        BEGIN
                            CREATE TABLE [dbo].[ApiWebhookDeliveryLog] (
                                [Id] BIGINT IDENTITY(1,1) NOT NULL,
                                [SubscriptionId] INT NOT NULL,
                                [EventTopic] NVARCHAR(100) NOT NULL,
                                [PayloadJson] NVARCHAR(MAX) NOT NULL,
                                [AttemptNumber] INT NOT NULL CONSTRAINT [DF_ApiWebhookDeliveryLog_AttemptNumber] DEFAULT (1),
                                [HttpStatusCode] INT NULL,
                                [IsSuccess] BIT NOT NULL CONSTRAINT [DF_ApiWebhookDeliveryLog_IsSuccess] DEFAULT (0),
                                [ErrorMessage] NVARCHAR(4000) NULL,
                                [DeliveredAt] DATETIME2 NOT NULL CONSTRAINT [DF_ApiWebhookDeliveryLog_DeliveredAt] DEFAULT (SYSUTCDATETIME()),
                                [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_ApiWebhookDeliveryLog_CreatedAt] DEFAULT (SYSUTCDATETIME()),
                                [CreatedBy] NVARCHAR(100) NULL CONSTRAINT [DF_ApiWebhookDeliveryLog_CreatedBy] DEFAULT ('SYSTEM'),
                                [ModifiedAt] DATETIME2 NULL,
                                [ModifiedBy] NVARCHAR(100) NULL,
                                CONSTRAINT [PK_ApiWebhookDeliveryLog] PRIMARY KEY CLUSTERED ([Id] ASC)
                            );
                        END
                        GO

                        IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'ApiIdempotencyRecord' AND schema_id = SCHEMA_ID('dbo'))
                        BEGIN
                            CREATE TABLE [dbo].[ApiIdempotencyRecord] (
                                [Id] BIGINT IDENTITY(1,1) NOT NULL,
                                [IdempotencyKey] NVARCHAR(100) NOT NULL,
                                [ClientIdentifier] NVARCHAR(100) NOT NULL,
                                [RequestPath] NVARCHAR(500) NOT NULL,
                                [ResponseStatusCode] INT NOT NULL,
                                [ResponseBodyJson] NVARCHAR(MAX) NOT NULL,
                                [ExpiresAt] DATETIME2 NOT NULL,
                                [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_ApiIdempotencyRecord_CreatedAt] DEFAULT (SYSUTCDATETIME()),
                                [CreatedBy] NVARCHAR(100) NULL CONSTRAINT [DF_ApiIdempotencyRecord_CreatedBy] DEFAULT ('SYSTEM'),
                                [ModifiedAt] DATETIME2 NULL,
                                [ModifiedBy] NVARCHAR(100) NULL,
                                CONSTRAINT [PK_ApiIdempotencyRecord] PRIMARY KEY CLUSTERED ([Id] ASC)
                            );
                        END
                        GO

                        IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_ApiIdempotencyRecord_IdempotencyKey' AND object_id = OBJECT_ID('dbo.ApiIdempotencyRecord'))
                        BEGIN
                            CREATE UNIQUE NONCLUSTERED INDEX [IX_ApiIdempotencyRecord_IdempotencyKey] ON [dbo].[ApiIdempotencyRecord] ([IdempotencyKey]);
                        END
                        GO
                    ";
                    await SqlBatchRunner.ExecuteBatchesAsync(context, embeddedDdl, logger);
                    logger?.LogInformation("Phase 59: Executed embedded DDL fallback successfully.");
                }
            }
            catch (Exception ex)
            {
                logger?.LogError(ex, "Phase 59: Error migrating B2B API Architecture schema.");
                throw;
            }
        }
    }
}
