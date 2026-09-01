using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Idempotent database schema migrator adding statutory SIC Code & Chamber governance columns to Organisation and lookup.SicCodeType.
/// </summary>
public static class Phase15SicCodeChamberGovernanceMigrator
{
    public static async Task MigrateAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        if (context.Database.IsSqlServer())
        {
            const string ddlSql = @"
                IF EXISTS (SELECT * FROM sys.tables WHERE name = 'Organisation' AND schema_id = SCHEMA_ID('dbo'))
                BEGIN
                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('dbo.Organisation') AND name = 'IsManualChamberOverride')
                        ALTER TABLE [dbo].[Organisation] ADD [IsManualChamberOverride] BIT NOT NULL DEFAULT 0;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('dbo.Organisation') AND name = 'ChamberOverrideReason')
                        ALTER TABLE [dbo].[Organisation] ADD [ChamberOverrideReason] NVARCHAR(500) NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('dbo.Organisation') AND name = 'ChamberOverrideDate')
                        ALTER TABLE [dbo].[Organisation] ADD [ChamberOverrideDate] DATETIME2(7) NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('dbo.Organisation') AND name = 'ChamberOverrideApprovedBy')
                        ALTER TABLE [dbo].[Organisation] ADD [ChamberOverrideApprovedBy] NVARCHAR(100) NULL;

                    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_Organisation_IsManualChamberOverride' AND object_id = OBJECT_ID('dbo.Organisation'))
                        CREATE NONCLUSTERED INDEX [IX_Organisation_IsManualChamberOverride] ON [dbo].[Organisation] ([IsManualChamberOverride]);
                END;

                IF EXISTS (SELECT * FROM sys.tables WHERE name = 'SicCodeType' AND schema_id = SCHEMA_ID('lookup'))
                BEGIN
                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('lookup.SicCodeType') AND name = 'ChamberCode')
                        ALTER TABLE [lookup].[SicCodeType] ADD [ChamberCode] NVARCHAR(20) NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('lookup.SicCodeType') AND name = 'SetaCode')
                        ALTER TABLE [lookup].[SicCodeType] ADD [SetaCode] NVARCHAR(10) NOT NULL DEFAULT N'17';

                    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_SicCodeType_ChamberCode' AND object_id = OBJECT_ID('lookup.SicCodeType'))
                        CREATE NONCLUSTERED INDEX [IX_SicCodeType_ChamberCode] ON [lookup].[SicCodeType] ([ChamberCode]);

                    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_SicCodeType_SetaCode' AND object_id = OBJECT_ID('lookup.SicCodeType'))
                        CREATE NONCLUSTERED INDEX [IX_SicCodeType_SetaCode] ON [lookup].[SicCodeType] ([SetaCode]);
                END;

                IF EXISTS (SELECT * FROM sys.tables WHERE name = 'LevyFileLine' AND schema_id = SCHEMA_ID('dbo'))
                BEGIN
                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LevyFileLine') AND name = 'SicCode')
                        ALTER TABLE [dbo].[LevyFileLine] ADD [SicCode] NVARCHAR(20) NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LevyFileLine') AND name = 'ChamberCode')
                        ALTER TABLE [dbo].[LevyFileLine] ADD [ChamberCode] NVARCHAR(20) NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LevyFileLine') AND name = 'SetaCode')
                        ALTER TABLE [dbo].[LevyFileLine] ADD [SetaCode] NVARCHAR(10) NOT NULL DEFAULT N'17';

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LevyFileLine') AND name = 'IsOutOfScopeSeta')
                        ALTER TABLE [dbo].[LevyFileLine] ADD [IsOutOfScopeSeta] BIT NOT NULL DEFAULT 0;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LevyFileLine') AND name = 'HasSicCodeMismatch')
                        ALTER TABLE [dbo].[LevyFileLine] ADD [HasSicCodeMismatch] BIT NOT NULL DEFAULT 0;
                END;

                IF EXISTS (SELECT * FROM sys.tables WHERE name = 'SarsLevyReconAudit' AND schema_id = SCHEMA_ID('dbo'))
                BEGIN
                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('dbo.SarsLevyReconAudit') AND name = 'ExpectedChamberCode')
                        ALTER TABLE [dbo].[SarsLevyReconAudit] ADD [ExpectedChamberCode] NVARCHAR(20) NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('dbo.SarsLevyReconAudit') AND name = 'ActualSarsChamberCode')
                        ALTER TABLE [dbo].[SarsLevyReconAudit] ADD [ActualSarsChamberCode] NVARCHAR(20) NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('dbo.SarsLevyReconAudit') AND name = 'ExpectedSicCode')
                        ALTER TABLE [dbo].[SarsLevyReconAudit] ADD [ExpectedSicCode] NVARCHAR(20) NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('dbo.SarsLevyReconAudit') AND name = 'ActualSarsSicCode')
                        ALTER TABLE [dbo].[SarsLevyReconAudit] ADD [ActualSarsSicCode] NVARCHAR(20) NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('dbo.SarsLevyReconAudit') AND name = 'CounterpartSetaCode')
                        ALTER TABLE [dbo].[SarsLevyReconAudit] ADD [CounterpartSetaCode] NVARCHAR(10) NULL;
                END;
            ";

            try
            {
                await context.Database.ExecuteSqlRawAsync(ddlSql);
                logger?.LogInformation("Phase 15 SIC Code & Chamber Governance schema migration executed successfully.");
            }
            catch (Exception ex)
            {
                logger?.LogWarning(ex, "Phase 15 migration encountered a non-critical notice.");
            }
        }
    }
}
