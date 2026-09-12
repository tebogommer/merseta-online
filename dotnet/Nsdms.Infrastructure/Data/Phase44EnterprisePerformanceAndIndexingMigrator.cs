using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Idempotent database schema migrator provisioning foreign key covering indexes,
/// ensuring zero table scan locks on relational updates and deletes under enterprise concurrency.
/// </summary>
public static class Phase44EnterprisePerformanceAndIndexingMigrator
{
    public static async Task MigrateAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        if (context.Database.IsSqlServer())
        {
            var scriptPaths = new[]
            {
                Path.Combine(AppContext.BaseDirectory, "Data", "SqlScripts", "V2026_44_Enterprise_Performance_And_Foreign_Key_Indexes.sql"),
                Path.Combine(AppContext.BaseDirectory, "SqlScripts", "V2026_44_Enterprise_Performance_And_Foreign_Key_Indexes.sql"),
                Path.Combine(Directory.GetCurrentDirectory(), "..", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_44_Enterprise_Performance_And_Foreign_Key_Indexes.sql"),
                Path.Combine(Directory.GetCurrentDirectory(), "Data", "SqlScripts", "V2026_44_Enterprise_Performance_And_Foreign_Key_Indexes.sql")
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
                await context.Database.ExecuteSqlRawAsync(sqlScript);
                logger?.LogInformation("Phase 44 Enterprise Performance & Foreign Key Covering Indexes migrated successfully.");
            }
            catch (Exception ex)
            {
                logger?.LogWarning(ex, "Phase 44 migration encountered an error executing SQL script (indexes may already exist).");
            }
        }
    }

    private const string InlineMigrationSql = @"
        IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_AqpLearnerAssessment_PersonId' AND object_id = OBJECT_ID('[dbo].[AqpLearnerAssessment]'))
        BEGIN
            CREATE NONCLUSTERED INDEX [IX_AqpLearnerAssessment_PersonId] ON [dbo].[AqpLearnerAssessment] ([PersonId]);
        END

        IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_CurriculumWorkingGroupMember_PersonId' AND object_id = OBJECT_ID('[dbo].[CurriculumWorkingGroupMember]'))
        BEGIN
            CREATE NONCLUSTERED INDEX [IX_CurriculumWorkingGroupMember_PersonId] ON [dbo].[CurriculumWorkingGroupMember] ([PersonId]);
        END

        IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_DocumentSnapshot_DocumentTemplateId' AND object_id = OBJECT_ID('[dbo].[DocumentSnapshot]'))
        BEGIN
            CREATE NONCLUSTERED INDEX [IX_DocumentSnapshot_DocumentTemplateId] ON [dbo].[DocumentSnapshot] ([DocumentTemplateId]);
        END

        IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_LearnerTradeTestApplication_OrganisationId' AND object_id = OBJECT_ID('[dbo].[LearnerTradeTestApplication]'))
        BEGIN
            CREATE NONCLUSTERED INDEX [IX_LearnerTradeTestApplication_OrganisationId] ON [dbo].[LearnerTradeTestApplication] ([OrganisationId]);
        END

        IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_LearnerTradeTestApplication_PersonId' AND object_id = OBJECT_ID('[dbo].[LearnerTradeTestApplication]'))
        BEGIN
            CREATE NONCLUSTERED INDEX [IX_LearnerTradeTestApplication_PersonId] ON [dbo].[LearnerTradeTestApplication] ([PersonId]);
        END

        IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_NonSetaQualificationsCompletion_CompanyLearnerId' AND object_id = OBJECT_ID('[dbo].[NonSetaQualificationsCompletion]'))
        BEGIN
            CREATE NONCLUSTERED INDEX [IX_NonSetaQualificationsCompletion_CompanyLearnerId] ON [dbo].[NonSetaQualificationsCompletion] ([CompanyLearnerId]);
        END

        IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_QualificationsCurriculumDevelopment_OrganisationId' AND object_id = OBJECT_ID('[dbo].[QualificationsCurriculumDevelopment]'))
        BEGIN
            CREATE NONCLUSTERED INDEX [IX_QualificationsCurriculumDevelopment_OrganisationId] ON [dbo].[QualificationsCurriculumDevelopment] ([OrganisationId]);
        END

        IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_SarsLevyReconAudit_OrganisationId' AND object_id = OBJECT_ID('[dbo].[SarsLevyReconAudit]'))
        BEGIN
            CREATE NONCLUSTERED INDEX [IX_SarsLevyReconAudit_OrganisationId] ON [dbo].[SarsLevyReconAudit] ([OrganisationId]);
        END

        IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_SdpSiteInspection_InspectorPersonId' AND object_id = OBJECT_ID('[dbo].[SdpSiteInspection]'))
        BEGIN
            CREATE NONCLUSTERED INDEX [IX_SdpSiteInspection_InspectorPersonId] ON [dbo].[SdpSiteInspection] ([InspectorPersonId]);
        END

        IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_StatementOfResults_PersonId' AND object_id = OBJECT_ID('[dbo].[StatementOfResults]'))
        BEGIN
            CREATE NONCLUSTERED INDEX [IX_StatementOfResults_PersonId] ON [dbo].[StatementOfResults] ([PersonId]);
        END

        IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_StatementOfResults_SummativeAssessmentReportId' AND object_id = OBJECT_ID('[dbo].[StatementOfResults]'))
        BEGIN
            CREATE NONCLUSTERED INDEX [IX_StatementOfResults_SummativeAssessmentReportId] ON [dbo].[StatementOfResults] ([SummativeAssessmentReportId]);
        END

        IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_SummativeAssessmentReport_AssessorPersonId' AND object_id = OBJECT_ID('[dbo].[SummativeAssessmentReport]'))
        BEGIN
            CREATE NONCLUSTERED INDEX [IX_SummativeAssessmentReport_AssessorPersonId] ON [dbo].[SummativeAssessmentReport] ([AssessorPersonId]);
        END

        IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_SummativeAssessmentReport_InternalModeratorPersonId' AND object_id = OBJECT_ID('[dbo].[SummativeAssessmentReport]'))
        BEGIN
            CREATE NONCLUSTERED INDEX [IX_SummativeAssessmentReport_InternalModeratorPersonId] ON [dbo].[SummativeAssessmentReport] ([InternalModeratorPersonId]);
        END

        IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_SummativeAssessmentReport_OrganisationId' AND object_id = OBJECT_ID('[dbo].[SummativeAssessmentReport]'))
        BEGIN
            CREATE NONCLUSTERED INDEX [IX_SummativeAssessmentReport_OrganisationId] ON [dbo].[SummativeAssessmentReport] ([OrganisationId]);
        END

        IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_SummativeAssessmentReport_PersonId' AND object_id = OBJECT_ID('[dbo].[SummativeAssessmentReport]'))
        BEGIN
            CREATE NONCLUSTERED INDEX [IX_SummativeAssessmentReport_PersonId] ON [dbo].[SummativeAssessmentReport] ([PersonId]);
        END

        IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_WorkflowHistory_FromStateId' AND object_id = OBJECT_ID('[dbo].[WorkflowHistory]'))
        BEGIN
            CREATE NONCLUSTERED INDEX [IX_WorkflowHistory_FromStateId] ON [dbo].[WorkflowHistory] ([FromStateId]);
        END

        IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_WorkflowHistory_ToStateId' AND object_id = OBJECT_ID('[dbo].[WorkflowHistory]'))
        BEGIN
            CREATE NONCLUSTERED INDEX [IX_WorkflowHistory_ToStateId] ON [dbo].[WorkflowHistory] ([ToStateId]);
        END

        IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_WorkflowNotification_WorkflowInstanceId' AND object_id = OBJECT_ID('[dbo].[WorkflowNotification]'))
        BEGIN
            CREATE NONCLUSTERED INDEX [IX_WorkflowNotification_WorkflowInstanceId] ON [dbo].[WorkflowNotification] ([WorkflowInstanceId]);
        END
    ";
}
