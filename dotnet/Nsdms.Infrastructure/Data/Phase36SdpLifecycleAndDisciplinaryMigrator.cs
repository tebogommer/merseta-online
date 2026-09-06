using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Phase 36 Database Migrator:
/// Applies Skills Development Provider (SDP) Lifecycle, Disciplinary, Site Inspection, Re-Accreditation, and SLA Affiliation:
/// - dbo.SdpDisciplinaryCase: Formal investigations, suspensions, and sanctions with automatic learner enrolment freezing.
/// - dbo.SdpSiteInspection: Form ETQ-TP-012 physical site inspection checklist and workshop tool ratio audit scoring.
/// - dbo.SdpReAccreditationApplication: Multi-cycle 5-year re-accreditation historical application registry.
/// - dbo.TrainingProviderAssessorLink: SLA bilateral document attachments and principal/practitioner attestations.
/// </summary>
public static class Phase36SdpLifecycleAndDisciplinaryMigrator
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
                var scriptPath = Path.Combine(AppContext.BaseDirectory, "Data", "SqlScripts", "V2026_28_Phase36_Sdp_Lifecycle_And_Disciplinary.sql");
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "dotnet", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_28_Phase36_Sdp_Lifecycle_And_Disciplinary.sql");
                }
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "..", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_28_Phase36_Sdp_Lifecycle_And_Disciplinary.sql");
                }

                if (File.Exists(scriptPath))
                {
                    var sql = await File.ReadAllTextAsync(scriptPath);
                    await SqlBatchRunner.ExecuteBatchesAsync(context, sql, logger);
                    logger?.LogInformation("Executed V2026_28_Phase36_Sdp_Lifecycle_And_Disciplinary.sql successfully.");
                }
            }
            catch (Exception ex)
            {
                logger?.LogWarning(ex, "Failed to apply V2026_28_Phase36_Sdp_Lifecycle_And_Disciplinary.sql migrator. Non-fatal in sandbox.");
            }
        }
    }
}
