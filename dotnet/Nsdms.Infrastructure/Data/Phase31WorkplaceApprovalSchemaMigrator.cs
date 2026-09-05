using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Phase 31 Database Migrator:
/// Applies Workplace Approval Schema Alignment per the signed 2022 Use Case Specification
/// (Workplace Approval Application Use Case_19122022.NMok.signed.pdf):
/// - LearningProgramTypeCode, RequiresWorkplaceApproval, IsSiteVisitRequired, SiteVisitJustification, InspectionDueDate (20 business day SLA).
/// - Role-neutral verification attributes (VerificationRecommendationReason, Explanation, RejectionReason, Explanation, VerifiedDate, VerifiedByPersonId).
/// - Role-neutral evaluation & decision attributes (ApprovalReason, Explanation, RejectionReason, Explanation, DecisionDate, DecisionByPersonId).
/// - Non-merSETA host employer support (IsNonMerSetaCompany, HomeSetaName, HomeSetaAgreementRef).
/// - Performance indexes on VerifiedByPersonId, DecisionByPersonId, InspectionDueDate.
/// </summary>
public static class Phase31WorkplaceApprovalSchemaMigrator
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
                var scriptPath = Path.Combine(AppContext.BaseDirectory, "Data", "SqlScripts", "V2026_23_WorkplaceApproval_Spec_Alignment.sql");
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "dotnet", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_23_WorkplaceApproval_Spec_Alignment.sql");
                }
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "..", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_23_WorkplaceApproval_Spec_Alignment.sql");
                }

                if (File.Exists(scriptPath))
                {
                    var sql = await File.ReadAllTextAsync(scriptPath);
                    await SqlBatchRunner.ExecuteBatchesAsync(context, sql, logger);
                    logger?.LogInformation("Executed V2026_23_WorkplaceApproval_Spec_Alignment.sql successfully.");
                }
            }
            catch (Exception ex)
            {
                logger?.LogWarning(ex, "Failed to apply V2026_23_WorkplaceApproval_Spec_Alignment.sql migrator. Non-fatal in sandbox.");
            }
        }
    }
}
