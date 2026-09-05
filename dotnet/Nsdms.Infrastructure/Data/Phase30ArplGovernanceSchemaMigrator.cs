using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Phase 30 Database Migrator:
/// Applies ARPL & Trade Test Schema Alignment per the signed 2023 Use Case Specification
/// (ARPL Registration Application Use Case 27012023.NMok.signed.pdf):
/// - dbo.PersonContact address lines 2-3, postal toggle, Next of Kin, secondary email.
/// - dbo.Person MaidenSurname.
/// - dbo.LearnerTradeTestApplication statutory ARPL extensions (CLA/QA 2-tier approval, toolkits flag, 10% sampling, etc.).
/// - dbo.TradeTestTask attempt partitioning & 50% credit retention columns.
/// - dbo.LearnerTradeTestWithdrawal table & non-repudiation withdrawal audit.
/// - dbo.ArplDocumentChecklist table & statutory document matrix (Toolkit 7 vs Non-Toolkit 6).
/// - dbo.CertificateDistributionEvent table for Red Seal dispatch tracking.
/// </summary>
public static class Phase30ArplGovernanceSchemaMigrator
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
                var scriptPath = Path.Combine(AppContext.BaseDirectory, "Data", "SqlScripts", "V2026_22_Arpl_Signed_Spec_Alignment.sql");
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "dotnet", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_22_Arpl_Signed_Spec_Alignment.sql");
                }
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "..", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_22_Arpl_Signed_Spec_Alignment.sql");
                }

                if (File.Exists(scriptPath))
                {
                    var sql = await File.ReadAllTextAsync(scriptPath);
                    await SqlBatchRunner.ExecuteBatchesAsync(context, sql, logger);
                    logger?.LogInformation("Executed V2026_22_Arpl_Signed_Spec_Alignment.sql successfully.");
                }
            }
            catch (Exception ex)
            {
                logger?.LogWarning(ex, "Failed to apply V2026_22_Arpl_Signed_Spec_Alignment.sql migrator. Non-fatal in sandbox.");
            }
        }
    }
}
