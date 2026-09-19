using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Phase 58: Microsoft Entra ID Resilient Authentication & Backup Password Schema Migrator.
/// Provisions Entra ID federation columns, index coverage, and self-service disaster recovery
/// backup password properties on dbo.AppUser.
/// </summary>
public static class Phase58EntraResilienceSchemaMigrator
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
                var scriptPath = Path.Combine(AppContext.BaseDirectory, "Data", "SqlScripts", "V2026_30_Phase58_Entra_Resilience.sql");
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "dotnet", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_30_Phase58_Entra_Resilience.sql");
                }
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "..", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_30_Phase58_Entra_Resilience.sql");
                }

                if (File.Exists(scriptPath))
                {
                    var sql = await File.ReadAllTextAsync(scriptPath);
                    await SqlBatchRunner.ExecuteBatchesAsync(context, sql, logger);
                    logger?.LogInformation("Phase 58: Executed V2026_30_Phase58_Entra_Resilience.sql successfully from file.");
                }
                else
                {
                    // Fallback embedded DDL
                    const string embeddedDdl = @"
                        IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[AppUser]') AND name = 'IsEntraUser')
                        BEGIN
                            ALTER TABLE [dbo].[AppUser] ADD [IsEntraUser] BIT NOT NULL CONSTRAINT [DF_AppUser_IsEntraUser] DEFAULT (0);
                        END
                        GO

                        IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[AppUser]') AND name = 'EntraObjectId')
                        BEGIN
                            ALTER TABLE [dbo].[AppUser] ADD [EntraObjectId] NVARCHAR(100) NULL;
                        END
                        GO

                        IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[AppUser]') AND name = 'EntraUserPrincipalName')
                        BEGIN
                            ALTER TABLE [dbo].[AppUser] ADD [EntraUserPrincipalName] NVARCHAR(150) NULL;
                        END
                        GO

                        IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[AppUser]') AND name = 'EntraAccountEnabled')
                        BEGIN
                            ALTER TABLE [dbo].[AppUser] ADD [EntraAccountEnabled] BIT NULL CONSTRAINT [DF_AppUser_EntraAccountEnabled] DEFAULT (1);
                        END
                        GO

                        IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[AppUser]') AND name = 'LastEntraSyncUtc')
                        BEGIN
                            ALTER TABLE [dbo].[AppUser] ADD [LastEntraSyncUtc] DATETIME2(7) NULL;
                        END
                        GO

                        IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[AppUser]') AND name = 'BackupPasswordHash')
                        BEGIN
                            ALTER TABLE [dbo].[AppUser] ADD [BackupPasswordHash] NVARCHAR(MAX) NULL;
                        END
                        GO

                        IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[AppUser]') AND name = 'BackupPasswordSetAt')
                        BEGIN
                            ALTER TABLE [dbo].[AppUser] ADD [BackupPasswordSetAt] DATETIME2(7) NULL;
                        END
                        GO

                        IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[AppUser]') AND name = 'BackupPasswordMustChange')
                        BEGIN
                            ALTER TABLE [dbo].[AppUser] ADD [BackupPasswordMustChange] BIT NOT NULL CONSTRAINT [DF_AppUser_BackupPasswordMustChange] DEFAULT (0);
                        END
                        GO

                        IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[AppUser]') AND name = 'LastBackupPasswordLoginUtc')
                        BEGIN
                            ALTER TABLE [dbo].[AppUser] ADD [LastBackupPasswordLoginUtc] DATETIME2(7) NULL;
                        END
                        GO

                        IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[AppUser]') AND name = 'BackupPasswordFailedAttempts')
                        BEGIN
                            ALTER TABLE [dbo].[AppUser] ADD [BackupPasswordFailedAttempts] INT NOT NULL CONSTRAINT [DF_AppUser_BackupPasswordFailedAttempts] DEFAULT (0);
                        END
                        GO

                        IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[AppUser]') AND name = 'BackupPasswordLockoutEnd')
                        BEGIN
                            ALTER TABLE [dbo].[AppUser] ADD [BackupPasswordLockoutEnd] DATETIMEOFFSET NULL;
                        END
                        GO

                        IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_AppUser_EntraObjectId' AND object_id = OBJECT_ID(N'[dbo].[AppUser]'))
                        BEGIN
                            CREATE NONCLUSTERED INDEX [IX_AppUser_EntraObjectId] ON [dbo].[AppUser]([EntraObjectId]) WHERE [EntraObjectId] IS NOT NULL;
                        END
                        GO

                        IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_AppUser_EntraUserPrincipalName' AND object_id = OBJECT_ID(N'[dbo].[AppUser]'))
                        BEGIN
                            CREATE NONCLUSTERED INDEX [IX_AppUser_EntraUserPrincipalName] ON [dbo].[AppUser]([EntraUserPrincipalName]) WHERE [EntraUserPrincipalName] IS NOT NULL;
                        END
                        GO

                        UPDATE [dbo].[AppUser]
                        SET [IsEntraUser] = 1,
                            [EntraAccountEnabled] = 1,
                            [EntraUserPrincipalName] = [Email],
                            [LastEntraSyncUtc] = SYSUTCDATETIME()
                        WHERE [Email] LIKE '%@merseta.org.za' AND [IsEntraUser] = 0;
                        GO";

                    await SqlBatchRunner.ExecuteBatchesAsync(context, embeddedDdl, logger);
                    logger?.LogInformation("Phase 58: Executed embedded DDL fallback successfully.");
                }

                // Backfill default merSETA employees with backup passwords and Entra federation
                try
                {
                    var identityService = scope.ServiceProvider.GetService<Nsdms.Application.Services.IIdentityService>();
                    if (identityService != null)
                    {
                        await identityService.SeedDefaultUsersAsync();
                        logger?.LogInformation("Phase 58: Ensured default merSETA employees have Entra federation and backup credentials.");
                    }
                }
                catch (Exception ex)
                {
                    logger?.LogWarning(ex, "Phase 58: Warning during default employee backup password seeding.");
                }
            }
            catch (Exception ex)
            {
                logger?.LogError(ex, "Phase 58: Error during Entra resilience schema migration.");
                throw;
            }
        }
    }
}
