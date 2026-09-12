using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Idempotent database schema migrator enforcing:
/// 1. Dual Authorisation Governance columns (ApprovalStatusCode, ProposedByUserId, ApprovedByUserId, etc.) on GrantFundingWindow.
/// 2. Compound unique index on GrantApplication (OrganisationId, FundingWindowId) to prevent duplicate submissions per window.
/// </summary>
public static class Phase46DgFundingWindowGovernanceMigrator
{
    public static async Task MigrateAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        if (context.Database.IsSqlServer())
        {
            const string ddlSql = @"
                -- 1. Dual Authorisation Governance Columns on GrantFundingWindow
                IF EXISTS (SELECT * FROM sys.tables WHERE name = 'GrantFundingWindow')
                BEGIN
                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantFundingWindow') AND name = 'ApprovalStatusCode')
                        ALTER TABLE [dbo].[GrantFundingWindow] ADD [ApprovalStatusCode] NVARCHAR(50) NOT NULL CONSTRAINT DF_GrantFundingWindow_ApprovalStatus DEFAULT 'Active';

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantFundingWindow') AND name = 'ProposedByUserId')
                        ALTER TABLE [dbo].[GrantFundingWindow] ADD [ProposedByUserId] NVARCHAR(100) NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantFundingWindow') AND name = 'ProposedDate')
                        ALTER TABLE [dbo].[GrantFundingWindow] ADD [ProposedDate] DATETIME2 NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantFundingWindow') AND name = 'ApprovedByUserId')
                        ALTER TABLE [dbo].[GrantFundingWindow] ADD [ApprovedByUserId] NVARCHAR(100) NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantFundingWindow') AND name = 'ApprovedDate')
                        ALTER TABLE [dbo].[GrantFundingWindow] ADD [ApprovedDate] DATETIME2 NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantFundingWindow') AND name = 'ApprovalJustification')
                        ALTER TABLE [dbo].[GrantFundingWindow] ADD [ApprovalJustification] NVARCHAR(1000) NULL;

                    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_GrantFundingWindow_ApprovalStatus' AND object_id = OBJECT_ID('GrantFundingWindow'))
                        CREATE INDEX [IX_GrantFundingWindow_ApprovalStatus] ON [dbo].[GrantFundingWindow] ([ApprovalStatusCode]);
                END;

                -- 2. Compound Unique Index on GrantApplication(OrganisationId, FundingWindowId)
                IF EXISTS (SELECT * FROM sys.tables WHERE name = 'GrantApplication')
                BEGIN
                    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_GrantApplication_Org_FundingWindow_Unique' AND object_id = OBJECT_ID('GrantApplication'))
                    BEGIN
                        CREATE UNIQUE INDEX [IX_GrantApplication_Org_FundingWindow_Unique] 
                        ON [dbo].[GrantApplication] ([OrganisationId], [FundingWindowId])
                        WHERE [FundingWindowId] IS NOT NULL;
                    END;
                END;
            ";

            await context.Database.ExecuteSqlRawAsync(ddlSql);
            logger?.LogInformation("Phase 46 Schema Migration: GrantFundingWindow Dual Authorisation governance and GrantApplication compound unique index verified.");
        }
    }
}
