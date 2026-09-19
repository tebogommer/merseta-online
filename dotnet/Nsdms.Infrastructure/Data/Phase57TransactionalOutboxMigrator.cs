using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Phase 57: Transactional Outbox Pattern Schema Migrator.
/// Provisions the OutboxMessage table and index for asynchronous domain event processing.
/// </summary>
public static class Phase57TransactionalOutboxMigrator
{
    public static async Task MigrateAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        if (context.Database.IsSqlServer())
        {
            const string ddlSql = @"
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
                END";

            try
            {
                await context.Database.ExecuteSqlRawAsync(ddlSql);
                logger?.LogInformation("Phase 57: Transactional Outbox migration completed successfully.");
            }
            catch (Exception ex)
            {
                logger?.LogError(ex, "Phase 57: Error applying OutboxMessage table migration.");
            }
        }
    }
}
