using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Idempotent database schema migrator for TradeMentorRatioPolicy and multi-tier cascading mentor ratio enforcement.
/// </summary>
public static class Phase13TradeMentorRatioSchemaMigrator
{
    public static async Task MigrateAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        if (context.Database.IsSqlServer())
        {
            const string ddlSql = @"
                -- 1. Create TradeMentorRatioPolicy Table
                IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'TradeMentorRatioPolicy')
                BEGIN
                    CREATE TABLE [dbo].[TradeMentorRatioPolicy] (
                        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
                        [TradeCode] NVARCHAR(50) NOT NULL,
                        [TradeTitle] NVARCHAR(200) NOT NULL,
                        [TradeOfoCode] NVARCHAR(50) NULL,
                        [SaqaQualificationId] INT NULL,
                        [StandardRatio] INT NOT NULL DEFAULT 4,
                        [MaxAllowedRatio] INT NOT NULL DEFAULT 6,
                        [MinExperienceYearsRequired] INT NOT NULL DEFAULT 3,
                        [EnforceStrictly] BIT NOT NULL DEFAULT 1,
                        [IsActive] BIT NOT NULL DEFAULT 1,
                        [Notes] NVARCHAR(500) NULL,
                        [CreatedAt] DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
                        [CreatedBy] NVARCHAR(100) NULL,
                        [ModifiedAt] DATETIME2 NULL,
                        [ModifiedBy] NVARCHAR(100) NULL
                    );

                    CREATE UNIQUE INDEX [IX_TradeMentorRatioPolicy_TradeCode] ON [dbo].[TradeMentorRatioPolicy] ([TradeCode]);
                    CREATE INDEX [IX_TradeMentorRatioPolicy_Ofo] ON [dbo].[TradeMentorRatioPolicy] ([TradeOfoCode]);
                    CREATE INDEX [IX_TradeMentorRatioPolicy_Saqa] ON [dbo].[TradeMentorRatioPolicy] ([SaqaQualificationId]);
                    CREATE INDEX [IX_TradeMentorRatioPolicy_IsActive] ON [dbo].[TradeMentorRatioPolicy] ([IsActive]);
                END;

                -- 2. Alter Organisation Table
                IF EXISTS (SELECT * FROM sys.tables WHERE name = 'Organisation')
                BEGIN
                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Organisation') AND name = 'IsMentorRatioEnforced')
                        ALTER TABLE [dbo].[Organisation] ADD [IsMentorRatioEnforced] BIT NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Organisation') AND name = 'MentorRatioExemptionReason')
                        ALTER TABLE [dbo].[Organisation] ADD [MentorRatioExemptionReason] NVARCHAR(500) NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Organisation') AND name = 'CustomMentorRatioCap')
                        ALTER TABLE [dbo].[Organisation] ADD [CustomMentorRatioCap] INT NULL;
                END;

                -- 3. Alter WorkplaceApproval Table
                IF EXISTS (SELECT * FROM sys.tables WHERE name = 'WorkplaceApproval')
                BEGIN
                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('WorkplaceApproval') AND name = 'TradeCode')
                        ALTER TABLE [dbo].[WorkplaceApproval] ADD [TradeCode] NVARCHAR(50) NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('WorkplaceApproval') AND name = 'IsRatioEnforced')
                        ALTER TABLE [dbo].[WorkplaceApproval] ADD [IsRatioEnforced] BIT NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('WorkplaceApproval') AND name = 'CustomTradeRatio')
                        ALTER TABLE [dbo].[WorkplaceApproval] ADD [CustomTradeRatio] INT NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('WorkplaceApproval') AND name = 'MentorRatioExemptionNotes')
                        ALTER TABLE [dbo].[WorkplaceApproval] ADD [MentorRatioExemptionNotes] NVARCHAR(500) NULL;
                END;

                -- 4. Alter WorkplaceApprovalMentor Table
                IF EXISTS (SELECT * FROM sys.tables WHERE name = 'WorkplaceApprovalMentor')
                BEGIN
                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('WorkplaceApprovalMentor') AND name = 'MaxLearnerCapacity')
                        ALTER TABLE [dbo].[WorkplaceApprovalMentor] ADD [MaxLearnerCapacity] INT NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('WorkplaceApprovalMentor') AND name = 'IsRatioExempt')
                        ALTER TABLE [dbo].[WorkplaceApprovalMentor] ADD [IsRatioExempt] BIT NOT NULL DEFAULT 0;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('WorkplaceApprovalMentor') AND name = 'IsRatioEnforced')
                        ALTER TABLE [dbo].[WorkplaceApprovalMentor] ADD [IsRatioEnforced] BIT NOT NULL DEFAULT 1;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('WorkplaceApprovalMentor') AND name = 'Notes')
                        ALTER TABLE [dbo].[WorkplaceApprovalMentor] ADD [Notes] NVARCHAR(500) NULL;
                END;
            ";

            await context.Database.ExecuteSqlRawAsync(ddlSql);
            logger?.LogInformation("Phase 13 Schema Migration: TradeMentorRatioPolicy table and multi-tier mentor ratio columns verified.");
        }
    }
}
