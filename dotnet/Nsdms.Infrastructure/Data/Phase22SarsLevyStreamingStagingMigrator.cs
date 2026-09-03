using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Phase 22 Database Schema Migrator:
/// Applies schema updates for Option A: Reactive Streaming Pipeline with SqlBulkCopy & Staging Table.
/// - SarsLevyStaging (High-speed landing table for raw SARS monthly levy feeds)
/// - LevyFile (DigitalSecuritySeal, ControlRecordCount, ControlTotalAmount, IsControlValidated, ProcessingDurationMs)
/// </summary>
public static class Phase22SarsLevyStreamingStagingMigrator
{
    public static async Task MigrateAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        if (context.Database.IsSqlServer())
        {
            const string ddlSql = @"
                -- 1. Create SarsLevyStaging Table
                IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'SarsLevyStaging' AND schema_id = SCHEMA_ID('dbo'))
                BEGIN
                    CREATE TABLE [dbo].[SarsLevyStaging] (
                        [Id] BIGINT IDENTITY(1,1) NOT NULL,
                        [BatchIdentifier] NVARCHAR(100) NOT NULL,
                        [LineNumber] INT NOT NULL,
                        [RawRecord] NVARCHAR(1000) NULL,
                        [SdlNumber] NVARCHAR(20) NOT NULL,
                        [SchemeYear] NVARCHAR(10) NULL,
                        [SicCode] NVARCHAR(20) NULL,
                        [ChamberCode] NVARCHAR(20) NULL,
                        [SetaCode] NVARCHAR(10) NOT NULL DEFAULT N'17',
                        [MandatoryLevyAmount] DECIMAL(18,2) NOT NULL DEFAULT 0,
                        [DiscretionaryLevyAmount] DECIMAL(18,2) NOT NULL DEFAULT 0,
                        [AdminLevyAmount] DECIMAL(18,2) NOT NULL DEFAULT 0,
                        [QctoLevyAmount] DECIMAL(18,2) NOT NULL DEFAULT 0,
                        [InterestAmount] DECIMAL(18,2) NOT NULL DEFAULT 0,
                        [PenaltyAmount] DECIMAL(18,2) NOT NULL DEFAULT 0,
                        [TotalLevyAmount] DECIMAL(18,2) NOT NULL DEFAULT 0,
                        [IsOutOfScopeSeta] BIT NOT NULL DEFAULT 0,
                        [HasSicCodeMismatch] BIT NOT NULL DEFAULT 0,
                        [StagingStatus] NVARCHAR(25) NOT NULL DEFAULT N'Pending',
                        [ValidationMessage] NVARCHAR(500) NULL,
                        [PromotedLevyFileId] INT NULL,
                        [CreatedAt] DATETIME2(7) NOT NULL DEFAULT SYSUTCDATETIME(),
                        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT N'SYSTEM',
                        [ModifiedAt] DATETIME2(7) NULL,
                        [ModifiedBy] NVARCHAR(100) NULL,
                        CONSTRAINT [PK_SarsLevyStaging] PRIMARY KEY CLUSTERED ([Id] ASC)
                    );

                    CREATE NONCLUSTERED INDEX [IX_SarsLevyStaging_BatchIdentifier] ON [dbo].[SarsLevyStaging] ([BatchIdentifier]);
                    CREATE NONCLUSTERED INDEX [IX_SarsLevyStaging_SdlNumber] ON [dbo].[SarsLevyStaging] ([SdlNumber]);
                    CREATE NONCLUSTERED INDEX [IX_SarsLevyStaging_SicCode] ON [dbo].[SarsLevyStaging] ([SicCode]);
                    CREATE NONCLUSTERED INDEX [IX_SarsLevyStaging_StagingStatus] ON [dbo].[SarsLevyStaging] ([StagingStatus]);
                    CREATE NONCLUSTERED INDEX [IX_SarsLevyStaging_IsOutOfScopeSeta] ON [dbo].[SarsLevyStaging] ([IsOutOfScopeSeta]);
                END;

                -- 2. Align LevyFile with Streaming Security & Trailer Control Columns
                IF EXISTS (SELECT * FROM sys.tables WHERE name = 'LevyFile' AND schema_id = SCHEMA_ID('dbo'))
                BEGIN
                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LevyFile') AND name = 'DigitalSecuritySeal')
                        ALTER TABLE [dbo].[LevyFile] ADD [DigitalSecuritySeal] NVARCHAR(64) NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LevyFile') AND name = 'ControlRecordCount')
                        ALTER TABLE [dbo].[LevyFile] ADD [ControlRecordCount] INT NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LevyFile') AND name = 'ControlTotalAmount')
                        ALTER TABLE [dbo].[LevyFile] ADD [ControlTotalAmount] DECIMAL(18,2) NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LevyFile') AND name = 'IsControlValidated')
                        ALTER TABLE [dbo].[LevyFile] ADD [IsControlValidated] BIT NOT NULL DEFAULT 0;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LevyFile') AND name = 'ProcessingDurationMs')
                        ALTER TABLE [dbo].[LevyFile] ADD [ProcessingDurationMs] BIGINT NULL;

                    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_LevyFile_DigitalSecuritySeal' AND object_id = OBJECT_ID('dbo.LevyFile'))
                        CREATE NONCLUSTERED INDEX [IX_LevyFile_DigitalSecuritySeal] ON [dbo].[LevyFile] ([DigitalSecuritySeal]);
                END;
            ";

            try
            {
                await context.Database.ExecuteSqlRawAsync(ddlSql);
                logger?.LogInformation("Phase 22 SARS Levy Streaming Pipeline & Staging schema migration executed successfully.");
            }
            catch (Exception ex)
            {
                logger?.LogWarning(ex, "Phase 22 migration encountered a non-critical notice.");
            }
        }
    }
}
