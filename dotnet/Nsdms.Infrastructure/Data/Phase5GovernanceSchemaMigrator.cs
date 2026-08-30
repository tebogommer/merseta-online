using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

public static class Phase5GovernanceSchemaMigrator
{
    public static async Task MigrateGovernanceSchemaAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        var ddl = @"
-- Clean Purge of SETMIS & QMR Non-Compliant Tables
IF EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_QmrProgrammeMetric_QmrQuarterlyReport_QmrQuarterlyReportId')
    ALTER TABLE [dbo].[QmrProgrammeMetric] DROP CONSTRAINT [FK_QmrProgrammeMetric_QmrQuarterlyReport_QmrQuarterlyReportId];

IF EXISTS (SELECT * FROM sys.tables WHERE name = 'QmrProgrammeMetric') DROP TABLE [dbo].[QmrProgrammeMetric];
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'QmrQuarterlyReport') DROP TABLE [dbo].[QmrQuarterlyReport];
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'SetmisSubmissionBatch') DROP TABLE [dbo].[SetmisSubmissionBatch];
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'DhetFlatFileExportLog') DROP TABLE [dbo].[DhetFlatFileExportLog];
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'setmis_submission_batch') DROP TABLE [dbo].[setmis_submission_batch];
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'qmr_quarterly_report') DROP TABLE [dbo].[qmr_quarterly_report];
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'qmr_programme_metric') DROP TABLE [dbo].[qmr_programme_metric];
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'dhet_flat_file_export_log') DROP TABLE [dbo].[dhet_flat_file_export_log];

-- Drop Legacy SETMIS Staging Tables if present
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'SetmisFile100Staging') DROP TABLE [dbo].[SetmisFile100Staging];
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'SetmisFile200Staging') DROP TABLE [dbo].[SetmisFile200Staging];
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'SetmisFile304Staging') DROP TABLE [dbo].[SetmisFile304Staging];
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'SetmisFile400Staging') DROP TABLE [dbo].[SetmisFile400Staging];
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'SetmisFile401Staging') DROP TABLE [dbo].[SetmisFile401Staging];
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'SetmisFile500Staging') DROP TABLE [dbo].[SetmisFile500Staging];
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'SetmisFile501Staging') DROP TABLE [dbo].[SetmisFile501Staging];
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'SetmisFile502Staging') DROP TABLE [dbo].[SetmisFile502Staging];
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'SetmisFile503Staging') DROP TABLE [dbo].[SetmisFile503Staging];
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'SetmisFile505Staging') DROP TABLE [dbo].[SetmisFile505Staging];
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'SetmisFile506Staging') DROP TABLE [dbo].[SetmisFile506Staging];

-- System Governance Provisions
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'SystemConfig')
BEGIN
    CREATE TABLE [dbo].[SystemConfig] (
        [Id] INT IDENTITY(1,1) PRIMARY KEY,
        [ConfigKey] NVARCHAR(150) NOT NULL,
        [ConfigValue] NVARCHAR(MAX) NULL,
        [ConfigCategory] NVARCHAR(50) NOT NULL DEFAULT 'General',
        [Description] NVARCHAR(500) NULL,
        [DataType] NVARCHAR(50) NOT NULL DEFAULT 'String',
        [IsEncrypted] BIT NOT NULL DEFAULT 0,
        [IsActive] BIT NOT NULL DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE UNIQUE INDEX [IX_SystemConfig_ConfigKey] ON [dbo].[SystemConfig]([ConfigKey]);
    CREATE INDEX [IX_SystemConfig_ConfigCategory] ON [dbo].[SystemConfig]([ConfigCategory]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'SystemFeatureFlag')
BEGIN
    CREATE TABLE [dbo].[SystemFeatureFlag] (
        [Id] INT IDENTITY(1,1) PRIMARY KEY,
        [FeatureKey] NVARCHAR(150) NOT NULL,
        [FeatureName] NVARCHAR(150) NOT NULL,
        [Description] NVARCHAR(500) NULL,
        [IsEnabled] BIT NOT NULL DEFAULT 0,
        [FeatureCategory] NVARCHAR(50) NOT NULL DEFAULT 'Integrations',
        [IsActive] BIT NOT NULL DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE UNIQUE INDEX [IX_SystemFeatureFlag_FeatureKey] ON [dbo].[SystemFeatureFlag]([FeatureKey]);
    CREATE INDEX [IX_SystemFeatureFlag_FeatureCategory] ON [dbo].[SystemFeatureFlag]([FeatureCategory]);
END

-- Workplace Approval Contact Person Invariant
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'WorkplaceApproval') AND NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'ContactPersonId')
BEGIN
    ALTER TABLE [dbo].[WorkplaceApproval] ADD [ContactPersonId] INT NULL;
    CREATE INDEX [IX_WorkplaceApproval_ContactPersonId] ON [dbo].[WorkplaceApproval]([ContactPersonId]);
END

-- BankingDetails Schema Alignment
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'BankingDetails')
BEGIN
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('BankingDetails') AND name = 'ApprovalStatusCode')
        ALTER TABLE [dbo].[BankingDetails] ADD [ApprovalStatusCode] NVARCHAR(50) NOT NULL DEFAULT 'PendingVerification';
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('BankingDetails') AND name = 'BankConfirmationDate')
        ALTER TABLE [dbo].[BankingDetails] ADD [BankConfirmationDate] DATETIME2 NULL;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('BankingDetails') AND name = 'BankConfirmationDocumentId')
        ALTER TABLE [dbo].[BankingDetails] ADD [BankConfirmationDocumentId] INT NULL;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('BankingDetails') AND name = 'BankConfirmationDocumentPath')
        ALTER TABLE [dbo].[BankingDetails] ADD [BankConfirmationDocumentPath] NVARCHAR(200) NULL;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('BankingDetails') AND name = 'FirstSignoffUserId')
        ALTER TABLE [dbo].[BankingDetails] ADD [FirstSignoffUserId] NVARCHAR(100) NULL;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('BankingDetails') AND name = 'FirstSignoffDate')
        ALTER TABLE [dbo].[BankingDetails] ADD [FirstSignoffDate] DATETIME2 NULL;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('BankingDetails') AND name = 'FirstSignoffNotes')
        ALTER TABLE [dbo].[BankingDetails] ADD [FirstSignoffNotes] NVARCHAR(1000) NULL;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('BankingDetails') AND name = 'SecondSignoffUserId')
        ALTER TABLE [dbo].[BankingDetails] ADD [SecondSignoffUserId] NVARCHAR(100) NULL;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('BankingDetails') AND name = 'SecondSignoffDate')
        ALTER TABLE [dbo].[BankingDetails] ADD [SecondSignoffDate] DATETIME2 NULL;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('BankingDetails') AND name = 'SecondSignoffNotes')
        ALTER TABLE [dbo].[BankingDetails] ADD [SecondSignoffNotes] NVARCHAR(1000) NULL;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('BankingDetails') AND name = 'IsErpActive')
        ALTER TABLE [dbo].[BankingDetails] ADD [IsErpActive] BIT NOT NULL DEFAULT 0;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('BankingDetails') AND name = 'ErpVendorId')
        ALTER TABLE [dbo].[BankingDetails] ADD [ErpVendorId] NVARCHAR(100) NULL;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('BankingDetails') AND name = 'ErpSyncDate')
        ALTER TABLE [dbo].[BankingDetails] ADD [ErpSyncDate] DATETIME2 NULL;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('BankingDetails') AND name = 'IsActive')
        ALTER TABLE [dbo].[BankingDetails] ADD [IsActive] BIT NOT NULL DEFAULT 1;
END

-- Review Committee & Meeting Governance Tables
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'ReviewCommitteeMeeting')
BEGIN
    CREATE TABLE [dbo].[ReviewCommitteeMeeting] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [Title] NVARCHAR(250) NOT NULL DEFAULT '',
        [MeetingTypeCode] NVARCHAR(50) NOT NULL DEFAULT 'EtqaReviewCommittee',
        [MeetingNumber] NVARCHAR(50) NOT NULL DEFAULT '',
        [FromDateTime] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [ToDateTime] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [Venue] NVARCHAR(250) NOT NULL DEFAULT '',
        [AdditionalInfo] NVARCHAR(MAX) NULL,
        [StatusCode] NVARCHAR(50) NOT NULL DEFAULT 'Scheduled',
        [ChairpersonUserId] NVARCHAR(100) NULL,
        [QuorumReached] BIT NOT NULL DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_ReviewCommitteeMeeting_MeetingNumber] ON [dbo].[ReviewCommitteeMeeting]([MeetingNumber]);
    CREATE INDEX [IX_ReviewCommitteeMeeting_StatusCode] ON [dbo].[ReviewCommitteeMeeting]([StatusCode]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'ReviewCommitteeMeetingAgenda')
BEGIN
    CREATE TABLE [dbo].[ReviewCommitteeMeetingAgenda] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [ReviewCommitteeMeetingId] INT NOT NULL,
        [ItemNumber] INT NOT NULL DEFAULT 1,
        [Title] NVARCHAR(250) NOT NULL DEFAULT '',
        [Description] NVARCHAR(MAX) NULL,
        [TargetEntityName] NVARCHAR(100) NULL,
        [TargetEntityId] INT NULL,
        [DecisionCode] NVARCHAR(50) NOT NULL DEFAULT 'Pending',
        [DecisionNotes] NVARCHAR(MAX) NULL,
        [VotedYesCount] INT NOT NULL DEFAULT 0,
        [VotedNoCount] INT NOT NULL DEFAULT 0,
        [AbstainCount] INT NOT NULL DEFAULT 0,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_ReviewCommitteeMeetingAgenda_ReviewCommitteeMeetingId] ON [dbo].[ReviewCommitteeMeetingAgenda]([ReviewCommitteeMeetingId]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'ReviewCommitteeMeetingMember')
BEGIN
    CREATE TABLE [dbo].[ReviewCommitteeMeetingMember] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [ReviewCommitteeMeetingId] INT NOT NULL,
        [PersonId] INT NOT NULL,
        [RoleInMeeting] NVARCHAR(50) NOT NULL DEFAULT 'VotingMember',
        [Attended] BIT NOT NULL DEFAULT 0,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_ReviewCommitteeMeetingMember_ReviewCommitteeMeetingId] ON [dbo].[ReviewCommitteeMeetingMember]([ReviewCommitteeMeetingId]);
    CREATE INDEX [IX_ReviewCommitteeMeetingMember_PersonId] ON [dbo].[ReviewCommitteeMeetingMember]([PersonId]);
END

-- LearnerTradeTestApplication Schema Alignment
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'LearnerTradeTestApplication')
BEGIN
    CREATE TABLE [dbo].[LearnerTradeTestApplication] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [CompanyLearnerId] INT NOT NULL,
        [PersonId] INT NOT NULL,
        [OrganisationId] INT NULL,
        [TrainingProviderId] INT NULL,
        [ApplicationNumber] NVARCHAR(50) NOT NULL DEFAULT '',
        [TradeTitle] NVARCHAR(150) NOT NULL DEFAULT '',
        [TradeOfoCode] NVARCHAR(50) NULL,
        [DesignatedTradeLevel] NVARCHAR(50) NULL,
        [ApplicationTypeCode] NVARCHAR(50) NOT NULL DEFAULT 'Section26D',
        [AttemptNumber] INT NOT NULL DEFAULT 1,
        [LearnerReadinessDate] DATETIME2 NULL,
        [AssessmentCenterName] NVARCHAR(150) NULL,
        [AssessmentDate] DATETIME2 NULL,
        [ScheduledStartTime] TIME NULL,
        [NambSerialNumber] NVARCHAR(50) NULL,
        [NambSubmissionDate] DATETIME2 NULL,
        [NambApprovalDate] DATETIME2 NULL,
        [NambDecisionStatusCode] NVARCHAR(50) NOT NULL DEFAULT 'Pending',
        [CompetencyStatusCode] NVARCHAR(50) NOT NULL DEFAULT 'Pending',
        [SerialCertificateNumber] NVARCHAR(50) NULL,
        [CertificateIssueDate] DATETIME2 NULL,
        [StatusCode] NVARCHAR(50) NOT NULL DEFAULT 'Draft',
        [AssessorName] NVARCHAR(150) NULL,
        [AssessorRegistrationNumber] NVARCHAR(50) NULL,
        [ModeratorName] NVARCHAR(150) NULL,
        [ModeratorRegistrationNumber] NVARCHAR(50) NULL,
        [Notes] NVARCHAR(MAX) NULL,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_LearnerTradeTestApplication_CompanyLearnerId] ON [dbo].[LearnerTradeTestApplication]([CompanyLearnerId]);
    CREATE INDEX [IX_LearnerTradeTestApplication_PersonId] ON [dbo].[LearnerTradeTestApplication]([PersonId]);
END

IF EXISTS (SELECT * FROM sys.tables WHERE name = 'LearnerTradeTestApplication')
BEGIN
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('LearnerTradeTestApplication') AND name = 'LearnerReadinessDate')
        ALTER TABLE [dbo].[LearnerTradeTestApplication] ADD [LearnerReadinessDate] DATETIME2 NULL;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('LearnerTradeTestApplication') AND name = 'NambSubmissionDate')
        ALTER TABLE [dbo].[LearnerTradeTestApplication] ADD [NambSubmissionDate] DATETIME2 NULL;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('LearnerTradeTestApplication') AND name = 'NambApprovalDate')
        ALTER TABLE [dbo].[LearnerTradeTestApplication] ADD [NambApprovalDate] DATETIME2 NULL;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('LearnerTradeTestApplication') AND name = 'NambDecisionStatusCode')
        ALTER TABLE [dbo].[LearnerTradeTestApplication] ADD [NambDecisionStatusCode] NVARCHAR(50) NOT NULL DEFAULT 'Pending';
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('LearnerTradeTestApplication') AND name = 'AssessmentCenterName')
        ALTER TABLE [dbo].[LearnerTradeTestApplication] ADD [AssessmentCenterName] NVARCHAR(150) NULL;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('LearnerTradeTestApplication') AND name = 'AssessmentDate')
        ALTER TABLE [dbo].[LearnerTradeTestApplication] ADD [AssessmentDate] DATETIME2 NULL;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('LearnerTradeTestApplication') AND name = 'ScheduledStartTime')
        ALTER TABLE [dbo].[LearnerTradeTestApplication] ADD [ScheduledStartTime] TIME NULL;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('LearnerTradeTestApplication') AND name = 'TradeOfoCode')
        ALTER TABLE [dbo].[LearnerTradeTestApplication] ADD [TradeOfoCode] NVARCHAR(50) NULL;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('LearnerTradeTestApplication') AND name = 'DesignatedTradeLevel')
        ALTER TABLE [dbo].[LearnerTradeTestApplication] ADD [DesignatedTradeLevel] NVARCHAR(50) NULL;
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'TradeTestTask')
BEGIN
    CREATE TABLE [dbo].[TradeTestTask] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [LearnerTradeTestApplicationId] INT NOT NULL,
        [TaskNumber] INT NOT NULL DEFAULT 1,
        [TaskTitle] NVARCHAR(200) NOT NULL DEFAULT '',
        [TaskDescription] NVARCHAR(500) NULL,
        [TotalMarksAvailable] DECIMAL(18,2) NOT NULL DEFAULT 100,
        [MarksObtained] DECIMAL(18,2) NOT NULL DEFAULT 0,
        [PassPercentage] DECIMAL(18,2) NOT NULL DEFAULT 70,
        [PercentageAchieved] DECIMAL(18,2) NOT NULL DEFAULT 0,
        [IsCompetent] BIT NOT NULL DEFAULT 0,
        [AssessorComments] NVARCHAR(1000) NULL,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_TradeTestTask_LearnerTradeTestApplicationId] ON [dbo].[TradeTestTask]([LearnerTradeTestApplicationId]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'ArplTradeTestInformation')
BEGIN
    CREATE TABLE [dbo].[ArplTradeTestInformation] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [LearnerTradeTestApplicationId] INT NOT NULL,
        [YearsOfExperienceInTrade] INT NOT NULL DEFAULT 0,
        [CurrentEmployerName] NVARCHAR(200) NULL,
        [EmployerContactPersonName] NVARCHAR(150) NULL,
        [EmployerContactPhone] NVARCHAR(50) NULL,
        [PortfolioOfEvidenceVerified] BIT NOT NULL DEFAULT 0,
        [PortfolioScorePercentage] DECIMAL(18,2) NOT NULL DEFAULT 0,
        [WorkplaceMentorName] NVARCHAR(150) NULL,
        [ToolkitChecklistVerified] BIT NOT NULL DEFAULT 0,
        [PortfolioAssessorUserId] NVARCHAR(100) NULL,
        [PortfolioAssessmentDate] DATETIME2 NULL,
        [ArplRecommendation] NVARCHAR(1000) NOT NULL DEFAULT 'ProceedToTradeTest',
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_ArplTradeTestInformation_LearnerTradeTestApplicationId] ON [dbo].[ArplTradeTestInformation]([LearnerTradeTestApplicationId]);
END

IF EXISTS (SELECT * FROM sys.tables WHERE name = 'ArplTradeTestInformation')
BEGIN
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('ArplTradeTestInformation') AND name = 'EmployerContactPersonName')
        ALTER TABLE [dbo].[ArplTradeTestInformation] ADD [EmployerContactPersonName] NVARCHAR(150) NULL;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('ArplTradeTestInformation') AND name = 'WorkplaceMentorName')
        ALTER TABLE [dbo].[ArplTradeTestInformation] ADD [WorkplaceMentorName] NVARCHAR(150) NULL;
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'ArplExperienceDetail')
BEGIN
    CREATE TABLE [dbo].[ArplExperienceDetail] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [LearnerTradeTestApplicationId] INT NOT NULL,
        [EmployerName] NVARCHAR(200) NOT NULL DEFAULT '',
        [JobTitle] NVARCHAR(150) NOT NULL DEFAULT '',
        [StartDate] DATETIME2 NOT NULL,
        [EndDate] DATETIME2 NULL,
        [DutiesDescription] NVARCHAR(MAX) NOT NULL DEFAULT '',
        [EvidenceDocumentName] NVARCHAR(250) NULL,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_ArplExperienceDetail_LearnerTradeTestApplicationId] ON [dbo].[ArplExperienceDetail]([LearnerTradeTestApplicationId]);
END

IF EXISTS (SELECT * FROM sys.tables WHERE name = 'ArplExperienceDetail')
BEGIN
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('ArplExperienceDetail') AND name = 'DutiesDescription')
        ALTER TABLE [dbo].[ArplExperienceDetail] ADD [DutiesDescription] NVARCHAR(MAX) NOT NULL DEFAULT '';
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('ArplExperienceDetail') AND name = 'EvidenceDocumentName')
        ALTER TABLE [dbo].[ArplExperienceDetail] ADD [EvidenceDocumentName] NVARCHAR(250) NULL;
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'ArplTrainingDetail')
BEGIN
    CREATE TABLE [dbo].[ArplTrainingDetail] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [LearnerTradeTestApplicationId] INT NOT NULL,
        [InstitutionName] NVARCHAR(200) NOT NULL DEFAULT '',
        [CourseOrModuleTitle] NVARCHAR(200) NOT NULL DEFAULT '',
        [CompletionDate] DATETIME2 NULL,
        [CertificateObtained] NVARCHAR(200) NULL,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_ArplTrainingDetail_LearnerTradeTestApplicationId] ON [dbo].[ArplTrainingDetail]([LearnerTradeTestApplicationId]);
END

IF EXISTS (SELECT * FROM sys.tables WHERE name = 'ArplTrainingDetail')
BEGIN
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('ArplTrainingDetail') AND name = 'CompletionDate')
        ALTER TABLE [dbo].[ArplTrainingDetail] ADD [CompletionDate] DATETIME2 NULL;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('ArplTrainingDetail') AND name = 'CertificateObtained')
        ALTER TABLE [dbo].[ArplTrainingDetail] ADD [CertificateObtained] NVARCHAR(200) NULL;
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'NambDecisionHistory')
BEGIN
    CREATE TABLE [dbo].[NambDecisionHistory] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [LearnerTradeTestApplicationId] INT NOT NULL,
        [NambOfficialUserId] NVARCHAR(100) NULL,
        [NambOfficerName] NVARCHAR(150) NOT NULL DEFAULT '',
        [DecisionStatusCode] NVARCHAR(50) NOT NULL DEFAULT '',
        [DecisionNotes] NVARCHAR(1000) NULL,
        [DecisionDate] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [NambBatchReference] NVARCHAR(100) NULL,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_NambDecisionHistory_LearnerTradeTestApplicationId] ON [dbo].[NambDecisionHistory]([LearnerTradeTestApplicationId]);
END

IF EXISTS (SELECT * FROM sys.tables WHERE name = 'NambDecisionHistory')
BEGIN
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('NambDecisionHistory') AND name = 'NambOfficialUserId')
        ALTER TABLE [dbo].[NambDecisionHistory] ADD [NambOfficialUserId] NVARCHAR(100) NULL;
END
";

        await context.Database.ExecuteSqlRawAsync(ddl);
        logger?.LogInformation("Phase 5 System Governance DDL verified and SETMIS/QMR tables purged.");
    }
}
