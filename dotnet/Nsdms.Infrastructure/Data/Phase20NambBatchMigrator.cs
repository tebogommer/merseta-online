using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Phase 20 Database Migrator:
/// Applies schema updates for Phase 5 Artisan Development & NAMB Batch Governance:
/// - National Artisan Moderation Body Batch Staging (NambSubmissionBatch)
/// - Trade Test Application linkage to NAMB batches
/// </summary>
public static class Phase20NambBatchMigrator
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
                var scriptPath = Path.Combine(AppContext.BaseDirectory, "Data", "SqlScripts", "V2026_15_Phase5_Namb_Batch.sql");
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "dotnet", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_15_Phase5_Namb_Batch.sql");
                }
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "..", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_15_Phase5_Namb_Batch.sql");
                }

                if (File.Exists(scriptPath))
                {
                    var sql = await File.ReadAllTextAsync(scriptPath);
                    await context.Database.ExecuteSqlRawAsync(sql);
                    logger?.LogInformation("Executed V2026_15_Phase5_Namb_Batch.sql successfully.");
                }
            }
            catch (Exception ex)
            {
                logger?.LogWarning(ex, "Failed to apply V2026_15_Phase5_Namb_Batch.sql migrator. Non-fatal in sandbox.");
            }
        }
    }
}
