using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Phase 29 Database Migrator:
/// Applies Learner Application Registration Alignment Schema:
/// - dbo.PersonGuardian table & indexes.
/// - dbo.LearnerRegisteredUnitStandard table & indexes.
/// - dbo.CompanyLearner statutory extensions (LearnerSignatureDate, SubmissionDate, SignatoryRoleTitle, etc.)
/// - dbo.Organisation non-employer partner & external SETA levy columns.
/// </summary>
public static class Phase29LearnerRegistrationAlignmentMigrator
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
                var scriptPath = Path.Combine(AppContext.BaseDirectory, "Data", "SqlScripts", "V2026_21_Phase5_Learner_Registration_Alignment.sql");
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "dotnet", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_21_Phase5_Learner_Registration_Alignment.sql");
                }
                if (!File.Exists(scriptPath))
                {
                    scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "..", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_21_Phase5_Learner_Registration_Alignment.sql");
                }

                if (File.Exists(scriptPath))
                {
                    var sql = await File.ReadAllTextAsync(scriptPath);
                    await SqlBatchRunner.ExecuteBatchesAsync(context, sql, logger);
                    logger?.LogInformation("Executed V2026_21_Phase5_Learner_Registration_Alignment.sql successfully.");
                }
            }
            catch (Exception ex)
            {
                logger?.LogWarning(ex, "Failed to apply V2026_21_Phase5_Learner_Registration_Alignment.sql migrator. Non-fatal in sandbox.");
            }
        }
    }
}
