using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Phase 19 Database Migrator:
/// Applies schema updates for Phase 4 ETQA 3-Year Re-registration & CPD Tracking:
/// - Assessor/Moderator 3-Year Re-registration Application (AssessorReRegistrationApplication)
/// - Continuous Professional Development Activities (AssessorCpdActivity)
/// </summary>
public static class Phase19EtqaReRegistrationMigrator
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
                var scriptPath = Path.Combine(AppContext.BaseDirectory, "Data", "SqlScripts", "V2026_14_Phase4_Etqa_ReRegistration.sql");
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "dotnet", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_14_Phase4_Etqa_ReRegistration.sql");
                }
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "..", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_14_Phase4_Etqa_ReRegistration.sql");
                }

                if (File.Exists(scriptPath))
                {
                    var sql = await File.ReadAllTextAsync(scriptPath);
                    await SqlBatchRunner.ExecuteBatchesAsync(context, sql, logger);
                    logger?.LogInformation("Executed V2026_14_Phase4_Etqa_ReRegistration.sql successfully.");
                }
            }
            catch (Exception ex)
            {
                logger?.LogWarning(ex, "Failed to apply V2026_14_Phase4_Etqa_ReRegistration.sql migrator. Non-fatal in sandbox.");
            }
        }
    }
}
