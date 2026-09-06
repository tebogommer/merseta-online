using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Phase 32 Database Migrator:
/// Applies Bursary Application Registration & Continuation Schema (Ref: MerSeta\NSDMS\LMS\LR\01):
/// - dbo.CompanyLearner nullable OrganisationId for unemployed applicants.
/// - dbo.CompanyLearner Bursary Application attributes (New vs Continuation, PreviousCompanyLearnerId, YearOfStudy, AcademicYear).
/// - dbo.CompanyLearner Educational Institution & Employment Status attributes.
/// - lookup.BursaryFundingType table with 7 statutory funding categories.
/// </summary>
public static class Phase32BursaryRegistrationGovernanceMigrator
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
                var scriptPath = Path.Combine(AppContext.BaseDirectory, "Data", "SqlScripts", "V2026_24_Phase32_Bursary_Registration_Governance.sql");
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "dotnet", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_24_Phase32_Bursary_Registration_Governance.sql");
                }
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "..", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_24_Phase32_Bursary_Registration_Governance.sql");
                }

                if (File.Exists(scriptPath))
                {
                    var sql = await File.ReadAllTextAsync(scriptPath);
                    await SqlBatchRunner.ExecuteBatchesAsync(context, sql, logger);
                    logger?.LogInformation("Executed V2026_24_Phase32_Bursary_Registration_Governance.sql successfully.");
                }
            }
            catch (Exception ex)
            {
                logger?.LogWarning(ex, "Failed to apply V2026_24_Phase32_Bursary_Registration_Governance.sql migrator. Non-fatal in sandbox.");
            }
        }
    }
}
