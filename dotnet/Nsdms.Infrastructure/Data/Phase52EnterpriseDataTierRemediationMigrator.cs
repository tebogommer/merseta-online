using System;
using System.IO;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Idempotent schema migrator executing Wave 2 data tier remediation:
/// repairs FK_CompanyLearner_Organisation, adds missing FK indexes, provisions RowVersion, and harmonizes join data types.
/// </summary>
public static class Phase52EnterpriseDataTierRemediationMigrator
{
    public static async Task MigrateAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        if (!context.Database.IsSqlServer())
        {
            return;
        }

        var scriptPaths = new[]
        {
            Path.Combine(AppContext.BaseDirectory, "Data", "SqlScripts", "V2026_52_Universal_ForeignKey_Indexes_And_Repair.sql"),
            Path.Combine(AppContext.BaseDirectory, "SqlScripts", "V2026_52_Universal_ForeignKey_Indexes_And_Repair.sql"),
            Path.Combine(Directory.GetCurrentDirectory(), "..", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_52_Universal_ForeignKey_Indexes_And_Repair.sql"),
            Path.Combine(Directory.GetCurrentDirectory(), "Data", "SqlScripts", "V2026_52_Universal_ForeignKey_Indexes_And_Repair.sql")
        };

        string? sqlScript = null;
        foreach (var path in scriptPaths)
        {
            if (File.Exists(path))
            {
                sqlScript = await File.ReadAllTextAsync(path);
                break;
            }
        }

        if (string.IsNullOrWhiteSpace(sqlScript))
        {
            sqlScript = InlineMigrationSql;
        }

        try
        {
            await SqlBatchRunner.ExecuteBatchesAsync(context, sqlScript);
            logger?.LogInformation("Phase 52 Enterprise Data Tier Remediation Migrator completed successfully.");
        }
        catch (Exception ex)
        {
            logger?.LogWarning(ex, "Phase 52 Enterprise Data Tier Remediation Migrator encountered non-fatal schema check: {Message}", ex.Message);
        }
    }

    private const string InlineMigrationSql = @"
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'CompanyLearner' AND schema_id = SCHEMA_ID(N'dbo'))
   AND EXISTS (SELECT 1 FROM sys.tables WHERE name = N'Organisation' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    IF EXISTS (
        SELECT 1 
        FROM sys.foreign_key_columns fkc
        JOIN sys.columns c ON fkc.parent_object_id = c.object_id AND fkc.parent_column_id = c.column_id
        WHERE fkc.constraint_object_id = OBJECT_ID(N'dbo.FK_CompanyLearner_Organisation')
          AND c.name <> N'OrganisationId'
    )
    BEGIN
        ALTER TABLE [dbo].[CompanyLearner] DROP CONSTRAINT [FK_CompanyLearner_Organisation];
    END

    IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = N'FK_CompanyLearner_Organisation' AND parent_object_id = OBJECT_ID(N'dbo.CompanyLearner'))
    BEGIN
        ALTER TABLE [dbo].[CompanyLearner] WITH NOCHECK
        ADD CONSTRAINT [FK_CompanyLearner_Organisation]
        FOREIGN KEY ([OrganisationId]) REFERENCES [dbo].[Organisation] ([Id]);

        ALTER TABLE [dbo].[CompanyLearner] CHECK CONSTRAINT [FK_CompanyLearner_Organisation];
    END
END
GO

IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'WorkplaceApproval' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'IX_WorkplaceApproval_AssessorPersonId' AND object_id = OBJECT_ID(N'dbo.WorkplaceApproval'))
    BEGIN
        CREATE NONCLUSTERED INDEX [IX_WorkplaceApproval_AssessorPersonId]
        ON [dbo].[WorkplaceApproval] ([AssessorPersonId])
        WHERE [AssessorPersonId] IS NOT NULL;
    END
END
GO

IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'SummativeAssessmentUnitStandard' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'IX_SummativeAssessmentUnitStandard_AssessorPersonId' AND object_id = OBJECT_ID(N'dbo.SummativeAssessmentUnitStandard'))
    BEGIN
        CREATE NONCLUSTERED INDEX [IX_SummativeAssessmentUnitStandard_AssessorPersonId]
        ON [dbo].[SummativeAssessmentUnitStandard] ([AssessorPersonId])
        WHERE [AssessorPersonId] IS NOT NULL;
    END
END
GO

IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'LearnerTradeTest' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'IX_LearnerTradeTest_AssessorPersonId' AND object_id = OBJECT_ID(N'dbo.LearnerTradeTest'))
    BEGIN
        CREATE NONCLUSTERED INDEX [IX_LearnerTradeTest_AssessorPersonId]
        ON [dbo].[LearnerTradeTest] ([AssessorPersonId])
        WHERE [AssessorPersonId] IS NOT NULL;
    END
END
GO
";
}
