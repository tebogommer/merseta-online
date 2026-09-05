using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Phase 26 Database Migrator:
/// Applies schema updates for Non-Levy N-Number Sequence & Chamber / GP Vendor Class Derivation:
/// - seq_NonLevyOrganisationNumber sequence with dynamic high-water mark seeding.
/// - Organisation columns: HasMissingChamberMapping, GpVendorClass.
/// - Filtered unique index on Organisation.SdlNumber.
/// - Index on Organisation.HasMissingChamberMapping.
/// </summary>
public static class Phase26NonLevyAndChamberMigrator
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
                var scriptPath = Path.Combine(AppContext.BaseDirectory, "Data", "SqlScripts", "V2026_19_NonLevy_Number_And_Chamber_Derivation.sql");
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "dotnet", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_19_NonLevy_Number_And_Chamber_Derivation.sql");
                }
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "..", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_19_NonLevy_Number_And_Chamber_Derivation.sql");
                }

                if (File.Exists(scriptPath))
                {
                    var sql = await File.ReadAllTextAsync(scriptPath);
                    await SqlBatchRunner.ExecuteBatchesAsync(context, sql, logger);
                    logger?.LogInformation("Executed V2026_19_NonLevy_Number_And_Chamber_Derivation.sql successfully.");
                }
            }
            catch (Exception ex)
            {
                logger?.LogWarning(ex, "Failed to apply V2026_19_NonLevy_Number_And_Chamber_Derivation.sql migrator. Non-fatal in sandbox.");
            }
        }
    }
}
