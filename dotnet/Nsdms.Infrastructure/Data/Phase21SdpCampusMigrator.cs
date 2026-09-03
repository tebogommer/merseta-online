using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Phase 21 Database Migrator:
/// Applies schema updates for Phase 7 SDP Provider Campus Infrastructure & Assessor Linking Grid:
/// - TrainingProviderCampus
/// - TrainingProviderAssessorLink
/// </summary>
public static class Phase21SdpCampusMigrator
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
                var scriptPath = Path.Combine(AppContext.BaseDirectory, "Data", "SqlScripts", "V2026_16_Phase7_Sdp_Campus_Assessor.sql");
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "dotnet", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_16_Phase7_Sdp_Campus_Assessor.sql");
                }
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "..", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_16_Phase7_Sdp_Campus_Assessor.sql");
                }

                if (File.Exists(scriptPath))
                {
                    var sql = await File.ReadAllTextAsync(scriptPath);
                    await SqlBatchRunner.ExecuteBatchesAsync(context, sql, logger);
                    logger?.LogInformation("Executed V2026_16_Phase7_Sdp_Campus_Assessor.sql successfully.");
                }
            }
            catch (Exception ex)
            {
                logger?.LogWarning(ex, "Failed to apply V2026_16_Phase7_Sdp_Campus_Assessor.sql migrator. Non-fatal in sandbox.");
            }
        }
    }
}
