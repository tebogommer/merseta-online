using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Idempotent database schema migrator provisioning the MgWindowScheduleProposal table
/// for Maker-Checker segregation of duties governance over Mandatory Grant submission window schedules.
/// </summary>
public static class Phase43MgWindowMakerCheckerMigrator
{
    public static async Task MigrateAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        if (context.Database.IsSqlServer())
        {
            const string ddlSql = @"
                IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'MgWindowScheduleProposal')
                BEGIN
                    CREATE TABLE [dbo].[MgWindowScheduleProposal] (
                        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
                        [SchemeYear] INT NOT NULL,
                        [ProposedOpeningDate] DATETIME2 NOT NULL,
                        [ProposedClosingDate] DATETIME2 NOT NULL,
                        [ProposedExtensionCutoffDate] DATETIME2 NOT NULL,
                        [Justification] NVARCHAR(MAX) NOT NULL,
                        [GazetteOrResolutionRef] NVARCHAR(250) NULL,
                        [Status] NVARCHAR(50) NOT NULL DEFAULT 'PendingReview',
                        [ProposedByUserId] NVARCHAR(100) NOT NULL,
                        [ProposedByUserName] NVARCHAR(150) NOT NULL,
                        [ProposedAt] DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
                        [AdjudicatedByUserId] NVARCHAR(100) NULL,
                        [AdjudicatedByUserName] NVARCHAR(150) NULL,
                        [AdjudicatedAt] DATETIME2 NULL,
                        [AdjudicationComments] NVARCHAR(MAX) NULL,
                        [AppliedToSystemConfig] BIT NOT NULL DEFAULT 0,
                        [CreatedAt] DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
                        [CreatedBy] NVARCHAR(100) NULL,
                        [ModifiedAt] DATETIME2 NULL,
                        [ModifiedBy] NVARCHAR(100) NULL
                    );

                    CREATE INDEX [IX_MgWindowScheduleProposal_SchemeYear] ON [dbo].[MgWindowScheduleProposal] ([SchemeYear]);
                    CREATE INDEX [IX_MgWindowScheduleProposal_Status] ON [dbo].[MgWindowScheduleProposal] ([Status]);
                END;
            ";

            await context.Database.ExecuteSqlRawAsync(ddlSql);
            logger?.LogInformation("Phase 43 Schema Migration: MgWindowScheduleProposal table and indices verified.");
        }
    }
}
