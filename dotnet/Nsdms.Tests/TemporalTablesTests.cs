using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Metadata;
using Nsdms.Domain.Common;
using Nsdms.Domain.Entities;
using Nsdms.Domain.Lookups;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class TemporalTablesTests
{
    private static NsdmsDbContext CreateContext()
    {
        Environment.SetEnvironmentVariable("ENABLE_EF_TEMPORAL_TABLES", "true");
        var options = new DbContextOptionsBuilder<NsdmsDbContext>()
            .UseSqlServer("Server=localhost;Database=temp_design_db;Integrated Security=true;TrustServerCertificate=true")
            .EnableServiceProviderCaching(false)
            .Options;
        return new NsdmsDbContext(options);
    }

    [Fact]
    public void Model_Configures_AllCoreDomainEntities_AsTemporalTables_In_HistorySchema()
    {
        using var db = CreateContext();
        var designModel = db.GetService<IDesignTimeModel>().Model;

        var coreEntities = new[]
        {
            // Organisation & Core Stakeholders
            typeof(Organisation),
            typeof(Person),
            typeof(OrganisationContact),
            typeof(OrganisationSite),
            typeof(Visit),
            typeof(SdfCompany),
            typeof(SdfAppointmentHistory),
            typeof(BankingDetails),
            typeof(BankingDetailsAudit),
            typeof(TrainingCommittee),
            typeof(TrainingCommitteeMember),

            // Mandatory Grants, Levies & WSP / ATR
            typeof(WspSubmission),
            typeof(WspEmploymentSummary),
            typeof(WspTrainingPlan),
            typeof(WspDispute),
            typeof(WspSkillsGap),
            typeof(WspStrategicSkillsGap),
            typeof(WspStrategicPriority),
            typeof(WspTrainingImpactSurvey),
            typeof(LevyFile),
            typeof(LevyFileLine),

            // Discretionary Grants & Financial Governance
            typeof(GrantFundingWindow),
            typeof(GrantApplication),
            typeof(GrantProjectBudget),
            typeof(GrantMoa),
            typeof(GrantMoaMilestone),
            typeof(GrantTranchePayment),
            typeof(MandatoryGrantDisbursement),
            typeof(InterSetaTransfer),
            // Skills Development Providers (SDP) & ETQA Assessors / Moderators
            typeof(TrainingProvider),
            typeof(TrainingProviderQualification),
            typeof(TrainingProviderUnitStandard),
            typeof(EtqaAssessor),
            typeof(AssessorModeratorScope),
            typeof(AssessorModeratorApplication),
            typeof(SdpScopeExtensionApplication),
            typeof(SdpExtensionOfScope),
            typeof(SdpReAccreditationApplication),
            typeof(AssessorExtensionOfScope),
            typeof(AqpPartner),
            typeof(AqpQualificationScope),

            // Learner Lifecycle, Assessments & Trade Tests
            typeof(CompanyLearner),
            typeof(CompanyLearnerTransfer),
            typeof(CompanyLearnerLostTime),
            typeof(CompanyLearnerTermination),
            typeof(LearnerAssessment),
            typeof(LearnerTradeTest),
            typeof(LearnerTradeTestApplication),
            typeof(TradeTestTask),
            typeof(ArplTradeTestInformation),
            typeof(ArplExperienceDetail),
            typeof(ArplTrainingDetail),
            typeof(NambDecisionHistory),
            typeof(SummativeAssessmentReport),
            typeof(SummativeAssessmentUnitStandard),
            typeof(EisaAssessmentEntry),
            typeof(StatementOfResults),
            typeof(AqpLearnerAssessment),

            // Workplace Approval & Monitoring Surveys
            typeof(WorkplaceApproval),
            typeof(WorkplaceApprovalMentor),
            typeof(WorkplaceApprovalToolList),
            typeof(WorkplaceMonitoringSiteVisit),
            typeof(WorkplaceMonitoringComplianceSurvey),
            typeof(WorkplaceMonitoringActionPlan),
            typeof(WorkplaceMonitoringMitigationPlan),
            typeof(WorkplaceMonitoringLearnerSurvey),

            // Review Committees, Curriculum & Non-SETA
            typeof(ReviewCommitteeMeeting),
            typeof(ReviewCommitteeMeetingAgenda),
            typeof(ReviewCommitteeMeetingMember),
            typeof(QualificationsCurriculumDevelopment),
            typeof(CurriculumWorkingGroupMember),
            typeof(SkillsRegistration),
            typeof(NonSetaCompany),
            typeof(NonSetaQualificationsCompletion),
            typeof(SarsLevyReconAudit),

            // Workflow Engine, Security & System Governance
            typeof(WorkflowDefinition),
            typeof(WorkflowState),
            typeof(WorkflowTransition),
            typeof(WorkflowInstance),
            typeof(WorkflowTask),
            typeof(WorkflowDelegation),
            typeof(FinancialApprovalThreshold),
            typeof(DocumentMetadata),
            typeof(DocumentRequirementRule),
            typeof(DocumentAttachment),
            typeof(SystemNotification)
        };

        foreach (var entityType in coreEntities)
        {
            var efEntityType = designModel.FindEntityType(entityType);
            Assert.NotNull(efEntityType);

            var isTemporal = efEntityType.IsTemporal();
            Assert.True(isTemporal, $"Entity {entityType.Name} should be configured as a temporal table.");

            var historySchema = efEntityType.GetHistoryTableSchema();
            Assert.Equal("history", historySchema);

            var tableName = efEntityType.GetTableName() ?? entityType.Name;
            var historyTableName = efEntityType.GetHistoryTableName();
            Assert.Equal($"{tableName}History", historyTableName);
        }
    }

    [Fact]
    public void Model_Excludes_AuditLog_And_Lookups_From_TemporalTables()
    {
        using var db = CreateContext();
        var designModel = db.GetService<IDesignTimeModel>().Model;

        // AuditLog, WorkflowHistory & TaskLease should NOT be temporal
        var excludedTypes = new[]
        {
            typeof(AuditLog),
            typeof(WorkflowHistory),
            typeof(WorkflowTaskLease)
        };

        foreach (var exType in excludedTypes)
        {
            var efType = designModel.FindEntityType(exType);
            Assert.NotNull(efType);
            Assert.False(efType.IsTemporal(), $"{exType.Name} should not be a temporal table.");
        }

        // Lookup tables in lookup schema should NOT be temporal
        var lookupTypes = new[]
        {
            typeof(GenderType),
            typeof(EquityType),
            typeof(ProvinceType),
            typeof(ChamberType),
            typeof(SectorType),
            typeof(OrganisationType),
            typeof(StatusType),
            typeof(TradeTestResultType),
            typeof(FundingType),
            typeof(SetaType),
            typeof(OfoCodeType)
        };

        foreach (var lookupType in lookupTypes)
        {
            var efLookup = designModel.FindEntityType(lookupType);
            Assert.NotNull(efLookup);
            Assert.False(efLookup.IsTemporal(), $"Lookup {lookupType.Name} should not be configured as a temporal table.");
        }
    }

    [Fact]
    public void SqlScripts_Include_HistorySchema_And_SystemVersioning_DDL()
    {
        var scriptPath = Path.Combine("..", "..", "..", "..", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_09_Enable_Temporal_Tables_History_Schema.sql");
        Assert.True(File.Exists(scriptPath), $"Expected DDL script to exist at {scriptPath}");

        var scriptContent = File.ReadAllText(scriptPath);
        Assert.Contains("CREATE SCHEMA [history]", scriptContent);
        Assert.Contains("SYSTEM_VERSIONING = ON", scriptContent);
        Assert.Contains("HISTORY_TABLE = [history].", scriptContent);
        Assert.Contains("PERIOD FOR SYSTEM_TIME", scriptContent);
        Assert.Contains("GENERATED ALWAYS AS ROW START", scriptContent);
        Assert.Contains("GENERATED ALWAYS AS ROW END", scriptContent);
    }
}
