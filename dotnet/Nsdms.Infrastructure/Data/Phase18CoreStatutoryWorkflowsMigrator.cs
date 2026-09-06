using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Phase 18 Database Migrator:
/// Applies schema updates for Phase 3 Core Statutory Workflows:
/// - WSP Multi-Party Quorum Signoff Attestation (WspSignoffAttestation)
/// - Learner Contract Lifecycle Amendment Requests (CompanyLearnerChangeRequest)
/// - Grant Payment Claim Multi-Tier Financial Approvals & ERP Payment Batch Staging (ErpPaymentBatchHeader/Entry)
/// </summary>
public static class Phase18CoreStatutoryWorkflowsMigrator
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
                var scriptPath = Path.Combine(AppContext.BaseDirectory, "Data", "SqlScripts", "V2026_13_Phase3_Core_Statutory_Workflows.sql");
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "dotnet", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_13_Phase3_Core_Statutory_Workflows.sql");
                }
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "..", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_13_Phase3_Core_Statutory_Workflows.sql");
                }

                if (File.Exists(scriptPath))
                {
                    var sql = await File.ReadAllTextAsync(scriptPath);
                    await SqlBatchRunner.ExecuteBatchesAsync(context, sql, logger);
                    logger?.LogInformation("Executed V2026_13_Phase3_Core_Statutory_Workflows.sql successfully.");
                }
            }
            catch (Exception ex)
            {
                logger?.LogWarning(ex, "Failed to apply V2026_13_Phase3_Core_Statutory_Workflows.sql migrator. Non-fatal in sandbox.");
            }
        }
    }
}
