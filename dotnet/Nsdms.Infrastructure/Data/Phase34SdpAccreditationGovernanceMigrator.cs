using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Phase 34 Database Migrator:
/// Applies Skills Development Provider (SDP) Statutory Accreditation Governance Alignment
/// (Ref: Signed SDP Application Use Case 21022023 MerSeta\NSDMS\LMS\LR\01):
/// - dbo.TrainingProviderCampus GPS coordinates (GpsCoordinates, Latitude, Longitude) and LocalMunicipality.
/// - dbo.TrainingProvider 5 accreditation streams, Primary ETQA, NAMB TTC, Committee, ReAccreditation, SLA, and Digital Security Seal.
/// - dbo.TrainingProviderSelfEvaluation two-stage interactive QMS audit checklist table.
/// - dbo.TrainingProviderContact multi-contact quorum (minimum 2 contacts) and banking details confirmation authority table.
/// </summary>
public static class Phase34SdpAccreditationGovernanceMigrator
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
                var scriptPath = Path.Combine(AppContext.BaseDirectory, "Data", "SqlScripts", "V2026_26_Phase34_Sdp_Accreditation_Governance.sql");
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "dotnet", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_26_Phase34_Sdp_Accreditation_Governance.sql");
                }
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "..", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_26_Phase34_Sdp_Accreditation_Governance.sql");
                }

                if (File.Exists(scriptPath))
                {
                    var sql = await File.ReadAllTextAsync(scriptPath);
                    await SqlBatchRunner.ExecuteBatchesAsync(context, sql, logger);
                    logger?.LogInformation("Executed V2026_26_Phase34_Sdp_Accreditation_Governance.sql successfully.");
                }
            }
            catch (Exception ex)
            {
                logger?.LogWarning(ex, "Failed to apply V2026_26_Phase34_Sdp_Accreditation_Governance.sql migrator. Non-fatal in sandbox.");
            }
        }
    }
}
