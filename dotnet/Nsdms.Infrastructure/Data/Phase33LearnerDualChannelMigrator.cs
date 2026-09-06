using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Phase 33 Database Migrator:
/// Applies Learner Registration Dual-Channel Architecture & Straight-Through Processing (STP) schema.
/// </summary>
public static class Phase33LearnerDualChannelMigrator
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
                var scriptPath = Path.Combine(AppContext.BaseDirectory, "Data", "SqlScripts", "V2026_25_Phase33_Learner_Dual_Channel_STP.sql");
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "dotnet", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_25_Phase33_Learner_Dual_Channel_STP.sql");
                }
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "..", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_25_Phase33_Learner_Dual_Channel_STP.sql");
                }

                if (File.Exists(scriptPath))
                {
                    var sql = await File.ReadAllTextAsync(scriptPath);
                    await SqlBatchRunner.ExecuteBatchesAsync(context, sql, logger);
                    logger?.LogInformation("Executed V2026_25_Phase33_Learner_Dual_Channel_STP.sql successfully.");
                }
            }
            catch (Exception ex)
            {
                logger?.LogWarning(ex, "Failed to apply V2026_25_Phase33_Learner_Dual_Channel_STP.sql during startup migration.");
            }
        }
    }
}
