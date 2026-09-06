using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Phase 38 Database Migrator:
/// Applies enterprise schema defect remediation:
/// - WorkplaceApproval role-neutral columns and indexes.
/// - LearnerTradeTestApplication and LearnerTradeTest PreviousTrainingCenterId.
/// - LearnerRegisteredUnitStandard table.
/// - SummativeAssessmentUnitStandard table.
/// - EisaAssessmentEntry table.
/// - LearnerCertificate table.
/// </summary>
public static class Phase38EnterpriseSchemaFixMigrator
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
                var scriptPath = Path.Combine(AppContext.BaseDirectory, "Data", "SqlScripts", "V2026_32_Fix_Enterprise_Schema_Defects.sql");
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "dotnet", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_32_Fix_Enterprise_Schema_Defects.sql");
                }
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "..", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_32_Fix_Enterprise_Schema_Defects.sql");
                }

                if (File.Exists(scriptPath))
                {
                    var sql = await File.ReadAllTextAsync(scriptPath);
                    await SqlBatchRunner.ExecuteBatchesAsync(context, sql, logger);
                    logger?.LogInformation("Executed V2026_32_Fix_Enterprise_Schema_Defects.sql successfully.");
                }
            }
            catch (Exception ex)
            {
                logger?.LogWarning(ex, "Failed to apply V2026_32_Fix_Enterprise_Schema_Defects.sql migrator. Non-fatal in sandbox.");
            }
        }
    }
}
