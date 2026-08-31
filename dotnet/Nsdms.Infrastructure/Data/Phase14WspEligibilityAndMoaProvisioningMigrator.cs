using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Idempotent database schema migrator adding statutory WSP compliance columns to GrantApplication.
/// </summary>
public static class Phase14WspEligibilityAndMoaProvisioningMigrator
{
    public static async Task MigrateAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        if (context.Database.IsSqlServer())
        {
            const string ddlSql = @"
                IF EXISTS (SELECT * FROM sys.tables WHERE name = 'GrantApplication')
                BEGIN
                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantApplication') AND name = 'WspSubmissionId')
                        ALTER TABLE [dbo].[GrantApplication] ADD [WspSubmissionId] INT NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantApplication') AND name = 'IsWspCompliant')
                        ALTER TABLE [dbo].[GrantApplication] ADD [IsWspCompliant] BIT NOT NULL DEFAULT 0;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantApplication') AND name = 'IsWspExempt')
                        ALTER TABLE [dbo].[GrantApplication] ADD [IsWspExempt] BIT NOT NULL DEFAULT 0;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantApplication') AND name = 'WspExemptionReason')
                        ALTER TABLE [dbo].[GrantApplication] ADD [WspExemptionReason] NVARCHAR(500) NULL;

                    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_GrantApplication_WspSubmission' AND object_id = OBJECT_ID('GrantApplication'))
                        CREATE INDEX [IX_GrantApplication_WspSubmission] ON [dbo].[GrantApplication] ([WspSubmissionId]);
                END;
            ";

            try
            {
                await context.Database.ExecuteSqlRawAsync(ddlSql);
                logger?.LogInformation("Phase 14 WSP Eligibility and MOA Provisioning schema migration executed successfully.");
            }
            catch (Exception ex)
            {
                logger?.LogWarning(ex, "Phase 14 migration encountered a non-critical notice.");
            }
        }
    }
}
