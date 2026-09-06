using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Phase 21 Database Migrator:
/// Ensures statutory initial Assessor & Moderator Registration Application schema alignment
/// per specification MerSeta\NSDMS\LMS\LR\01:
/// - dbo.AssessorRegistrationApplication
/// - dbo.AssessorApplicationScope
/// - dbo.AssessorApplicationUnitStandard
/// - dbo.AssessorApplicationProviderLink
/// - dbo.AssessorApplicationDocument
/// - dbo.AssessorUnitStandardScope
/// - dbo.AssessorProviderLink
/// - dbo.AssessorDisciplinaryCase
/// </summary>
public static class Phase21AssessorModeratorLifecycleMigrator
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
                var scriptPath = Path.Combine(AppContext.BaseDirectory, "Data", "SqlScripts", "V2026_21_Assessor_Moderator_Full_Lifecycle.sql");
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "dotnet", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_21_Assessor_Moderator_Full_Lifecycle.sql");
                }
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "..", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_21_Assessor_Moderator_Full_Lifecycle.sql");
                }
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(AppContext.BaseDirectory, "SqlScripts", "V2026_21_Assessor_Moderator_Full_Lifecycle.sql");
                }

                if (File.Exists(scriptPath))
                {
                    var sql = await File.ReadAllTextAsync(scriptPath);
                    await SqlBatchRunner.ExecuteBatchesAsync(context, sql, logger);
                    logger?.LogInformation("Executed V2026_21_Assessor_Moderator_Full_Lifecycle.sql successfully.");
                }
                else
                {
                    logger?.LogWarning("Script V2026_21_Assessor_Moderator_Full_Lifecycle.sql was not found at expected paths.");
                }
            }
            catch (Exception ex)
            {
                logger?.LogWarning(ex, "Failed to apply V2026_21_Assessor_Moderator_Full_Lifecycle.sql migrator.");
            }
        }
    }
}
