using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Phase 27 Database Migrator:
/// Applies Phase 4 Physical Renaming & SETMIS Compatibility Layer:
/// - Cuts over dbo.CompanyLearner to canonical dbo.LearnerEnrolment.
/// - Establishes backward-compatible view dbo.CompanyLearner forwarding to dbo.LearnerEnrolment.
/// - Creates statutory compatibility view dbo.vw_SetmisCompanyLearner for DHET SETMIS exports.
/// - Tunes and activates SQL Server System-Versioned Temporal Tables on PersonContact,
///   PersonDemographics, and PersonDisabilityRating into the dedicated [history] schema.
/// </summary>
public static class Phase27PhysicalRenamingMigrator
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
                var scriptPath = Path.Combine(AppContext.BaseDirectory, "Data", "SqlScripts", "V2026_20_Physical_Renaming_Setmis_Layer.sql");
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "dotnet", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_20_Physical_Renaming_Setmis_Layer.sql");
                }
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "..", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_20_Physical_Renaming_Setmis_Layer.sql");
                }

                if (File.Exists(scriptPath))
                {
                    var sql = await File.ReadAllTextAsync(scriptPath);
                    await SqlBatchRunner.ExecuteBatchesAsync(context, sql, logger);
                    logger?.LogInformation("Executed V2026_20_Physical_Renaming_Setmis_Layer.sql successfully.");
                }
            }
            catch (Exception ex)
            {
                logger?.LogWarning(ex, "Failed to apply V2026_20_Physical_Renaming_Setmis_Layer.sql migrator. Non-fatal in sandbox.");
            }
        }
    }
}
