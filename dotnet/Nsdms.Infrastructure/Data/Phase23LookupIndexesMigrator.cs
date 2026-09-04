using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Phase 23 Database Migrator (UNIMPL-001, UNIMPL-002, UNIMPL-003 / OPT-003, OPT-004):
/// Applies composite non-clustered indexes on (Code, Name) with covering INCLUDE columns for high-volume lookup tables:
/// - lookup.SicCodeType (IX_SicCodeType_Code_Name)
/// - lookup.OfoCodeType (IX_OfoCodeType_Code_Name)
/// - lookup.StatssaAreaCodeType / lookup.StatssaAreaType (IX_StatssaAreaCodeType_Code_Name)
/// </summary>
public static class Phase23LookupIndexesMigrator
{
    private const string EmbeddedDdlSql = @"
        IF NOT EXISTS (SELECT * FROM sys.schemas WHERE name = 'lookup')
        BEGIN
            EXEC('CREATE SCHEMA [lookup]');
        END
        GO

        IF EXISTS (SELECT * FROM sys.tables WHERE name = 'SicCodeType' AND schema_id = SCHEMA_ID('lookup'))
        BEGIN
            IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_SicCodeType_Code_Name' AND object_id = OBJECT_ID('lookup.SicCodeType'))
            BEGIN
                CREATE NONCLUSTERED INDEX [IX_SicCodeType_Code_Name]
                ON [lookup].[SicCodeType] ([Code], [Name])
                INCLUDE ([Active], [Description], [ChamberCode], [SetaCode]);
            END
        END
        GO

        IF EXISTS (SELECT * FROM sys.tables WHERE name = 'OfoCodeType' AND schema_id = SCHEMA_ID('lookup'))
        BEGIN
            IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_OfoCodeType_Code_Name' AND object_id = OBJECT_ID('lookup.OfoCodeType'))
            BEGIN
                CREATE NONCLUSTERED INDEX [IX_OfoCodeType_Code_Name]
                ON [lookup].[OfoCodeType] ([Code], [Name])
                INCLUDE ([Active], [Description]);
            END
        END
        GO

        IF EXISTS (SELECT * FROM sys.tables WHERE name = 'StatssaAreaCodeType' AND schema_id = SCHEMA_ID('lookup'))
        BEGIN
            IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_StatssaAreaCodeType_Code_Name' AND object_id = OBJECT_ID('lookup.StatssaAreaCodeType'))
            BEGIN
                CREATE NONCLUSTERED INDEX [IX_StatssaAreaCodeType_Code_Name]
                ON [lookup].[StatssaAreaCodeType] ([Code], [Name])
                INCLUDE ([Active], [Description]);
            END
        END
        GO

        IF EXISTS (SELECT * FROM sys.tables WHERE name = 'StatssaAreaType' AND schema_id = SCHEMA_ID('lookup'))
        BEGIN
            IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_StatssaAreaType_Code_Name' AND object_id = OBJECT_ID('lookup.StatssaAreaType'))
            BEGIN
                CREATE NONCLUSTERED INDEX [IX_StatssaAreaType_Code_Name]
                ON [lookup].[StatssaAreaType] ([Code], [Name])
                INCLUDE ([Active], [Description]);
            END
        END
        GO
    ";

    public static async Task MigrateAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        if (context.Database.IsSqlServer())
        {
            try
            {
                var scriptPath = Path.Combine(AppContext.BaseDirectory, "Data", "SqlScripts", "V2026_17_Lookup_Search_Composite_Indexes.sql");
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "dotnet", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_17_Lookup_Search_Composite_Indexes.sql");
                }
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "..", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_17_Lookup_Search_Composite_Indexes.sql");
                }

                string sql;
                if (File.Exists(scriptPath))
                {
                    sql = await File.ReadAllTextAsync(scriptPath);
                }
                else
                {
                    sql = EmbeddedDdlSql;
                }

                await SqlBatchRunner.ExecuteBatchesAsync(context, sql, logger);
                logger?.LogInformation("Executed Phase 23 Lookup Search Composite Indexes migration successfully.");
            }
            catch (Exception ex)
            {
                logger?.LogWarning(ex, "Failed to apply Phase 23 Lookup Search Composite Indexes migrator. Non-fatal in sandbox.");
            }
        }
    }
}
