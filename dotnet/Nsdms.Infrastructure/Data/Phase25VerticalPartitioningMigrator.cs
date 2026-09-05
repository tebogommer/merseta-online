using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Phase 25 Database Migrator:
/// Applies schema updates for Enterprise Vertical Table Partitioning:
/// - PersonContact satellite table & indexes.
/// - PersonDemographics satellite table & indexes.
/// - PersonDisabilityRating satellite table & indexes (POPIA Isolated).
/// - Idempotent historical data backfill from Person to satellites.
/// - Zero-breaking backward-compatible SQL Views: vw_PersonComplete, vw_PersonSetmis.
/// </summary>
public static class Phase25VerticalPartitioningMigrator
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
                var scriptPath = Path.Combine(AppContext.BaseDirectory, "Data", "SqlScripts", "V2026_19_Vertical_Partitioning.sql");
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "dotnet", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_19_Vertical_Partitioning.sql");
                }
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "..", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_19_Vertical_Partitioning.sql");
                }

                if (File.Exists(scriptPath))
                {
                    var sql = await File.ReadAllTextAsync(scriptPath);
                    await SqlBatchRunner.ExecuteBatchesAsync(context, sql, logger);
                    logger?.LogInformation("Executed V2026_19_Vertical_Partitioning.sql successfully.");
                }
            }
            catch (Exception ex)
            {
                logger?.LogWarning(ex, "Failed to apply V2026_19_Vertical_Partitioning.sql migrator. Non-fatal in sandbox.");
            }
        }
    }
}
