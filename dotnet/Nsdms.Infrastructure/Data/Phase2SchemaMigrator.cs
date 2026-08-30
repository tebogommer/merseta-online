using Microsoft.EntityFrameworkCore;
using Nsdms.Infrastructure.Data;

namespace Nsdms.Infrastructure.Data;

public static class Phase2SchemaMigrator
{
    public static async Task EnsurePhase2SchemaAsync(NsdmsDbContext db)
    {
        var sql = @"
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'TrainingProvider')
BEGIN
    CREATE TABLE [dbo].[TrainingProvider] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [OrganisationId] INT NOT NULL,
        [AccreditationNumber] NVARCHAR(50) NOT NULL DEFAULT '',
        [AccreditationStartDate] DATETIME2 NULL,
        [AccreditationEndDate] DATETIME2 NULL,
        [ProviderTypeCode] NVARCHAR(50) NULL,
        [ProviderStatusCode] NVARCHAR(50) NULL,
        [EtqaDecisionNumber] NVARCHAR(50) NULL,
        [MaxLearnerCapacity] INT NULL,
        [PrimaryContactPersonId] INT NULL,
        [IsActive] BIT NOT NULL DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_TrainingProvider_OrganisationId] ON [dbo].[TrainingProvider]([OrganisationId]);
    CREATE INDEX [IX_TrainingProvider_AccreditationNumber] ON [dbo].[TrainingProvider]([AccreditationNumber]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'TrainingProviderQualification')
BEGIN
    CREATE TABLE [dbo].[TrainingProviderQualification] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [TrainingProviderId] INT NOT NULL,
        [SaqaQualificationId] INT NOT NULL DEFAULT 0,
        [QualificationTitle] NVARCHAR(250) NOT NULL DEFAULT '',
        [NqfLevel] INT NULL,
        [AccreditationStatusCode] NVARCHAR(50) NULL,
        [ExpiryDate] DATETIME2 NULL,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_TrainingProviderQualification_TrainingProviderId] ON [dbo].[TrainingProviderQualification]([TrainingProviderId]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'TrainingProviderUnitStandard')
BEGIN
    CREATE TABLE [dbo].[TrainingProviderUnitStandard] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [TrainingProviderId] INT NOT NULL,
        [UnitStandardId] INT NOT NULL DEFAULT 0,
        [UnitStandardTitle] NVARCHAR(250) NOT NULL DEFAULT '',
        [NqfLevel] INT NULL,
        [Credits] INT NOT NULL DEFAULT 0,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_TrainingProviderUnitStandard_TrainingProviderId] ON [dbo].[TrainingProviderUnitStandard]([TrainingProviderId]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'WspEmploymentSummary')
BEGIN
    CREATE TABLE [dbo].[WspEmploymentSummary] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [WspSubmissionId] INT NOT NULL,
        [OfoCode] NVARCHAR(50) NULL,
        [OccupationalCategory] NVARCHAR(150) NULL,
        [MaleAfrican] INT NOT NULL DEFAULT 0,
        [FemaleAfrican] INT NOT NULL DEFAULT 0,
        [MaleColoured] INT NOT NULL DEFAULT 0,
        [FemaleColoured] INT NOT NULL DEFAULT 0,
        [MaleIndian] INT NOT NULL DEFAULT 0,
        [FemaleIndian] INT NOT NULL DEFAULT 0,
        [MaleWhite] INT NOT NULL DEFAULT 0,
        [FemaleWhite] INT NOT NULL DEFAULT 0,
        [DisabledCount] INT NOT NULL DEFAULT 0,
        [TotalEmployees] INT NOT NULL DEFAULT 0,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_WspEmploymentSummary_WspSubmissionId] ON [dbo].[WspEmploymentSummary]([WspSubmissionId]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'WspTrainingPlan')
BEGIN
    CREATE TABLE [dbo].[WspTrainingPlan] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [WspSubmissionId] INT NOT NULL,
        [ProgrammeTypeCode] NVARCHAR(50) NULL,
        [NqfLevel] INT NULL,
        [BeneficiaryCount] INT NOT NULL DEFAULT 0,
        [EstimatedCost] DECIMAL(18,2) NOT NULL DEFAULT 0,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_WspTrainingPlan_WspSubmissionId] ON [dbo].[WspTrainingPlan]([WspSubmissionId]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'GrantFundingWindow')
BEGIN
    CREATE TABLE [dbo].[GrantFundingWindow] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [FinYear] INT NOT NULL DEFAULT 2026,
        [WindowName] NVARCHAR(200) NOT NULL DEFAULT '',
        [GrantTypeCode] NVARCHAR(50) NULL,
        [OpeningDate] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [ClosingDate] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [TotalAvailableBudget] DECIMAL(18,2) NOT NULL DEFAULT 0,
        [IsActive] BIT NOT NULL DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'GrantProjectBudget')
BEGIN
    CREATE TABLE [dbo].[GrantProjectBudget] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [GrantApplicationId] INT NOT NULL,
        [ExpenseCategory] NVARCHAR(100) NOT NULL DEFAULT '',
        [Description] NVARCHAR(500) NULL,
        [UnitCost] DECIMAL(18,2) NOT NULL DEFAULT 0,
        [Quantity] INT NOT NULL DEFAULT 1,
        [TotalCost] DECIMAL(18,2) NOT NULL DEFAULT 0,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_GrantProjectBudget_GrantApplicationId] ON [dbo].[GrantProjectBudget]([GrantApplicationId]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'AssessorModeratorScope')
BEGIN
    CREATE TABLE [dbo].[AssessorModeratorScope] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [EtqaAssessorId] INT NOT NULL,
        [SaqaQualificationId] INT NOT NULL DEFAULT 0,
        [QualificationTitle] NVARCHAR(250) NOT NULL DEFAULT '',
        [RegistrationStatusCode] NVARCHAR(50) NULL,
        [ExpiryDate] DATETIME2 NULL,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_AssessorModeratorScope_EtqaAssessorId] ON [dbo].[AssessorModeratorScope]([EtqaAssessorId]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'LearnerAssessment')
BEGIN
    CREATE TABLE [dbo].[LearnerAssessment] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [EtqaAssessorId] INT NOT NULL,
        [PersonId] INT NOT NULL,
        [OrganisationId] INT NOT NULL,
        [QualificationTitle] NVARCHAR(250) NOT NULL DEFAULT '',
        [AssessmentDate] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CompetencyStatusCode] NVARCHAR(50) NOT NULL DEFAULT 'Competent',
        [ModeratorPersonId] INT NULL,
        [ModerationDate] DATETIME2 NULL,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_LearnerAssessment_PersonId] ON [dbo].[LearnerAssessment]([PersonId]);
    CREATE INDEX [IX_LearnerAssessment_EtqaAssessorId] ON [dbo].[LearnerAssessment]([EtqaAssessorId]);
    CREATE INDEX [IX_LearnerAssessment_OrganisationId] ON [dbo].[LearnerAssessment]([OrganisationId]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'WorkplaceApproval')
BEGIN
    CREATE TABLE [dbo].[WorkplaceApproval] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [OrganisationId] INT NOT NULL,
        [OrganisationSiteId] INT NULL,
        [SaqaQualificationId] INT NULL,
        [QualificationTitle] NVARCHAR(250) NOT NULL DEFAULT '',
        [ApprovalNumber] NVARCHAR(50) NOT NULL DEFAULT '',
        [ApprovalStatusCode] NVARCHAR(50) NOT NULL DEFAULT 'Pending',
        [InspectionDate] DATETIME2 NULL,
        [ApprovalDate] DATETIME2 NULL,
        [ExpiryDate] DATETIME2 NULL,
        [AssessorPersonId] INT NULL,
        [Recommendations] NVARCHAR(MAX) NULL,
        [IsActive] BIT NOT NULL DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_WorkplaceApproval_OrganisationId] ON [dbo].[WorkplaceApproval]([OrganisationId]);
    CREATE INDEX [IX_WorkplaceApproval_ApprovalNumber] ON [dbo].[WorkplaceApproval]([ApprovalNumber]);
    CREATE INDEX [IX_WorkplaceApproval_ApprovalStatusCode] ON [dbo].[WorkplaceApproval]([ApprovalStatusCode]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'WorkplaceApprovalMentor')
BEGIN
    CREATE TABLE [dbo].[WorkplaceApprovalMentor] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [WorkplaceApprovalId] INT NOT NULL,
        [PersonId] INT NOT NULL,
        [Designation] NVARCHAR(100) NOT NULL DEFAULT '',
        [ArtisanTradeNumber] NVARCHAR(50) NULL,
        [YearsExperience] INT NOT NULL DEFAULT 0,
        [IsCertifiedArtisan] BIT NOT NULL DEFAULT 1,
        [IsActive] BIT NOT NULL DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_WorkplaceApprovalMentor_WorkplaceApprovalId] ON [dbo].[WorkplaceApprovalMentor]([WorkplaceApprovalId]);
    CREATE INDEX [IX_WorkplaceApprovalMentor_PersonId] ON [dbo].[WorkplaceApprovalMentor]([PersonId]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'WorkplaceApprovalToolList')
BEGIN
    CREATE TABLE [dbo].[WorkplaceApprovalToolList] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [WorkplaceApprovalId] INT NOT NULL,
        [ToolName] NVARCHAR(150) NOT NULL DEFAULT '',
        [Category] NVARCHAR(50) NULL,
        [RequiredQuantity] INT NOT NULL DEFAULT 1,
        [AvailableQuantity] INT NOT NULL DEFAULT 0,
        [Remarks] NVARCHAR(250) NULL,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_WorkplaceApprovalToolList_WorkplaceApprovalId] ON [dbo].[WorkplaceApprovalToolList]([WorkplaceApprovalId]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'CompanyLearner')
BEGIN
    CREATE TABLE [dbo].[CompanyLearner] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [PersonId] INT NOT NULL,
        [OrganisationId] INT NOT NULL,
        [TrainingProviderId] INT NULL,
        [LearnerContractNumber] NVARCHAR(50) NOT NULL DEFAULT '',
        [QualificationTitle] NVARCHAR(250) NOT NULL DEFAULT '',
        [SaqaQualificationId] INT NULL,
        [NqfLevel] INT NULL,
        [LearningProgrammeTypeCode] NVARCHAR(50) NOT NULL DEFAULT 'Learnership',
        [FundingTypeCode] NVARCHAR(50) NOT NULL DEFAULT 'DiscretionaryGrant',
        [StatusCode] NVARCHAR(50) NOT NULL DEFAULT 'Registered',
        [RegistrationDate] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CommencementDate] DATETIME2 NULL,
        [ExpectedCompletionDate] DATETIME2 NULL,
        [CompletionDate] DATETIME2 NULL,
        [SetaRegion] NVARCHAR(100) NULL,
        [ChamberCode] NVARCHAR(50) NULL,
        [IsActive] BIT NOT NULL DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_CompanyLearner_PersonId] ON [dbo].[CompanyLearner]([PersonId]);
    CREATE INDEX [IX_CompanyLearner_OrganisationId] ON [dbo].[CompanyLearner]([OrganisationId]);
    CREATE INDEX [IX_CompanyLearner_TrainingProviderId] ON [dbo].[CompanyLearner]([TrainingProviderId]);
    CREATE INDEX [IX_CompanyLearner_LearnerContractNumber] ON [dbo].[CompanyLearner]([LearnerContractNumber]);
    CREATE INDEX [IX_CompanyLearner_StatusCode] ON [dbo].[CompanyLearner]([StatusCode]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'LearnerTradeTest')
BEGIN
    CREATE TABLE [dbo].[LearnerTradeTest] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [CompanyLearnerId] INT NOT NULL,
        [TestCenterName] NVARCHAR(150) NOT NULL DEFAULT '',
        [TradeTitle] NVARCHAR(150) NOT NULL DEFAULT '',
        [AttemptNumber] INT NOT NULL DEFAULT 1,
        [TradeTestDate] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [ResultStatusCode] NVARCHAR(50) NOT NULL DEFAULT 'Scheduled',
        [AssessorPersonId] INT NULL,
        [ModeratorPersonId] INT NULL,
        [SerialCertificateNumber] NVARCHAR(50) NULL,
        [CertificateIssueDate] DATETIME2 NULL,
        [Remarks] NVARCHAR(MAX) NULL,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_LearnerTradeTest_CompanyLearnerId] ON [dbo].[LearnerTradeTest]([CompanyLearnerId]);
    CREATE INDEX [IX_LearnerTradeTest_TradeTestDate] ON [dbo].[LearnerTradeTest]([TradeTestDate]);
    CREATE INDEX [IX_LearnerTradeTest_ResultStatusCode] ON [dbo].[LearnerTradeTest]([ResultStatusCode]);
END

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[LevyFileLine]') AND name = 'QctoLevyAmount')
BEGIN
    ALTER TABLE [dbo].[LevyFileLine] ADD [QctoLevyAmount] DECIMAL(18,2) NOT NULL DEFAULT 0;
END

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[LevyFileLine]') AND name = 'TotalLevyAmount')
BEGIN
    ALTER TABLE [dbo].[LevyFileLine] ADD [TotalLevyAmount] DECIMAL(18,2) NOT NULL DEFAULT 0;
END

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[GrantApplication]') AND name = 'FundingWindowId')
BEGIN
    ALTER TABLE [dbo].[GrantApplication] ADD [FundingWindowId] INT NULL;
END
";

        await db.Database.ExecuteSqlRawAsync(sql);
    }
}
