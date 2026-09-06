using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Phase 35 Database Migrator:
/// Applies Summative Assessment, Moderation, Batching & Certification Statutory Governance Alignment
/// (Ref: Signed Specification 18 Nov 2022 MerSeta\NSDMS\LMS\ASM\12):
/// - dbo.AssessmentBatch (Holding Room & QA Pool batching engine).
/// - dbo.AssessmentBatchLearner (Stratified sampling and outcome mapping).
/// - dbo.ModerationChecklistEtqTp043 & dbo.ModerationChecklistItem (Interactive ETQ-TP-043 statutory audit).
/// - dbo.CertificatePrintingBatch (Multi-certificate merged print batches).
/// - dbo.LearnerCertificate (Statutory numbering: 17 + middle 4 + 6 digits).
/// - dbo.DistributionLetter (Accreditation-grouped release letters).
/// - dbo.ScannedCertificateAttachment (Non-destructive physical certificate archive).
/// - dbo.AssessmentCertificateDistributionEvent (Waybill/Courier consignment tracking).
/// - Extensions to SummativeAssessmentReport, SummativeAssessmentUnitStandard, and StatementOfResults.
/// </summary>
public static class Phase35AssessmentAndModerationGovernanceMigrator
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
                // 1. Ensure Assessor/Moderator registration lifecycle tables exist
                var scriptPathLifecycle = Path.Combine(AppContext.BaseDirectory, "Data", "SqlScripts", "V2026_21_Assessor_Moderator_Full_Lifecycle.sql");
                if (!File.Exists(scriptPathLifecycle))
                {
                    scriptPathLifecycle = Path.Combine(Directory.GetCurrentDirectory(), "dotnet", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_21_Assessor_Moderator_Full_Lifecycle.sql");
                }
                if (!File.Exists(scriptPathLifecycle))
                {
                    scriptPathLifecycle = Path.Combine(Directory.GetCurrentDirectory(), "..", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_21_Assessor_Moderator_Full_Lifecycle.sql");
                }
                if (File.Exists(scriptPathLifecycle))
                {
                    var sqlLifecycle = await File.ReadAllTextAsync(scriptPathLifecycle);
                    await SqlBatchRunner.ExecuteBatchesAsync(context, sqlLifecycle, logger);
                    logger?.LogInformation("Executed V2026_21_Assessor_Moderator_Full_Lifecycle.sql successfully.");
                }

                // 2. Summative assessment & moderation governance
                var scriptPath = Path.Combine(AppContext.BaseDirectory, "Data", "SqlScripts", "V2026_27_Phase35_Assessment_And_Moderation_Governance.sql");
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "dotnet", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_27_Phase35_Assessment_And_Moderation_Governance.sql");
                }
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "..", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_27_Phase35_Assessment_And_Moderation_Governance.sql");
                }

                if (File.Exists(scriptPath))
                {
                    var sql = await File.ReadAllTextAsync(scriptPath);
                    await SqlBatchRunner.ExecuteBatchesAsync(context, sql, logger);
                    logger?.LogInformation("Executed V2026_27_Phase35_Assessment_And_Moderation_Governance.sql successfully.");
                }
            }
            catch (Exception ex)
            {
                logger?.LogWarning(ex, "Failed to apply V2026_27_Phase35_Assessment_And_Moderation_Governance.sql migrator. Non-fatal in sandbox.");
            }
        }
    }
}
