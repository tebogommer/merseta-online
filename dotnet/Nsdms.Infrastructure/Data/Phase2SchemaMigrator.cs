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

IF EXISTS (SELECT * FROM sys.tables WHERE name = 'TrainingProvider')
BEGIN
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('TrainingProvider') AND name = 'BrandColorHex')
        ALTER TABLE [dbo].[TrainingProvider] ADD [BrandColorHex] NVARCHAR(50) NULL;
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('TrainingProvider') AND name = 'LogoDocumentId')
        ALTER TABLE [dbo].[TrainingProvider] ADD [LogoDocumentId] INT NULL;
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
        [FundingId] INT NULL,
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

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[CompanyLearner]') AND name = 'FundingId')
BEGIN
    ALTER TABLE [dbo].[CompanyLearner] ADD [FundingId] INT NULL;
    CREATE INDEX [IX_CompanyLearner_FundingId] ON [dbo].[CompanyLearner]([FundingId]);
END

-- Align Organisation columns
IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[Organisation]') AND name = 'OrganisationStatusCode')
BEGIN
    ALTER TABLE [dbo].[Organisation] ADD [OrganisationStatusCode] NVARCHAR(50) NULL DEFAULT 'ACTIVE';
    EXEC sp_executesql N'UPDATE [dbo].[Organisation] SET [OrganisationStatusCode] = ISNULL([StatusCode], ''ACTIVE'') WHERE [OrganisationStatusCode] IS NULL';
END

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[Organisation]') AND name = 'LevyCategoryCode')
BEGIN
    ALTER TABLE [dbo].[Organisation] ADD [LevyCategoryCode] NVARCHAR(50) NULL DEFAULT 'LEVY_PAYING';
    EXEC sp_executesql N'UPDATE [dbo].[Organisation] SET [LevyCategoryCode] = ISNULL([CategoryCode], ''LEVY_PAYING'') WHERE [LevyCategoryCode] IS NULL';
END

-- Align Person columns
IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[Person]') AND name = 'CitizenStatusCode')
BEGIN
    ALTER TABLE [dbo].[Person] ADD [CitizenStatusCode] NVARCHAR(50) NULL DEFAULT 'RSA_CITIZEN';
    EXEC sp_executesql N'UPDATE [dbo].[Person] SET [CitizenStatusCode] = ISNULL([CitizenStatus], ''RSA_CITIZEN'') WHERE [CitizenStatusCode] IS NULL';
END

-- Align TrainingProvider columns
IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[TrainingProvider]') AND name = 'ProviderStatusCode')
BEGIN
    ALTER TABLE [dbo].[TrainingProvider] ADD [ProviderStatusCode] NVARCHAR(50) NULL DEFAULT 'ACCREDITED';
END

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[Organisation]') AND name = 'LogoDocumentId')
BEGIN
    ALTER TABLE [dbo].[Organisation] ADD [LogoDocumentId] INT NULL;
    ALTER TABLE [dbo].[Organisation] ADD [BrandColorHex] NVARCHAR(20) NULL;
    CREATE INDEX [IX_Organisation_LogoDocumentId] ON [dbo].[Organisation]([LogoDocumentId]);
END

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[TrainingProvider]') AND name = 'LogoDocumentId')
BEGIN
    ALTER TABLE [dbo].[TrainingProvider] ADD [LogoDocumentId] INT NULL;
    CREATE INDEX [IX_TrainingProvider_LogoDocumentId] ON [dbo].[TrainingProvider]([LogoDocumentId]);
END

-- Align WspSubmission columns
IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WspSubmission]') AND name = 'WspApprovalStatusCode')
BEGIN
    ALTER TABLE [dbo].[WspSubmission] ADD [WspApprovalStatusCode] NVARCHAR(50) NULL DEFAULT 'DRAFT';
END

-- Align CompanyLearner columns
IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[CompanyLearner]') AND name = 'EnrolmentStatusCode')
BEGIN
    ALTER TABLE [dbo].[CompanyLearner] ADD [EnrolmentStatusCode] NVARCHAR(50) NULL DEFAULT 'REGISTERED';
END

-- Align Visit columns
IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[Visit]') AND name = 'VisitStatusCode')
BEGIN
    ALTER TABLE [dbo].[Visit] ADD [VisitStatusCode] NVARCHAR(50) NULL DEFAULT 'Scheduled';
    EXEC sp_executesql N'UPDATE [dbo].[Visit] SET [VisitStatusCode] = ISNULL([StatusCode], ''Scheduled'') WHERE [VisitStatusCode] IS NULL';
END

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[Visit]') AND name = 'VisitTypeCode')
BEGIN
    ALTER TABLE [dbo].[Visit] ADD [VisitTypeCode] NVARCHAR(50) NULL DEFAULT 'WORKPLACE_APPROVAL';
    EXEC sp_executesql N'UPDATE [dbo].[Visit] SET [VisitTypeCode] = ISNULL([VisitType], ''WORKPLACE_APPROVAL'') WHERE [VisitTypeCode] IS NULL';
END

-- Align EtqaAssessor columns
IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[EtqaAssessor]') AND name = 'RegistrationStatusCode')
BEGIN
    ALTER TABLE [dbo].[EtqaAssessor] ADD [RegistrationStatusCode] NVARCHAR(50) NULL DEFAULT 'Active';
    EXEC sp_executesql N'UPDATE [dbo].[EtqaAssessor] SET [RegistrationStatusCode] = ISNULL([StatusCode], ''Active'') WHERE [RegistrationStatusCode] IS NULL';
END

-- Align LevyFile columns
IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[LevyFile]') AND name = 'ImportStatusCode')
BEGIN
    ALTER TABLE [dbo].[LevyFile] ADD [ImportStatusCode] NVARCHAR(50) NULL DEFAULT 'Processed';
    EXEC sp_executesql N'UPDATE [dbo].[LevyFile] SET [ImportStatusCode] = ISNULL([StatusCode], ''Processed'') WHERE [ImportStatusCode] IS NULL';
END

-- ASP.NET Core Identity & Security Roles (PascalCase tables)
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'AppUser')
BEGIN
    CREATE TABLE [dbo].[AppUser] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [UserName] NVARCHAR(100) NOT NULL,
        [NormalizedUserName] NVARCHAR(100) NOT NULL,
        [Email] NVARCHAR(150) NULL,
        [NormalizedEmail] NVARCHAR(150) NULL,
        [EmailConfirmed] BIT NOT NULL DEFAULT 0,
        [PasswordHash] NVARCHAR(MAX) NULL,
        [SecurityStamp] NVARCHAR(MAX) NULL,
        [ConcurrencyStamp] NVARCHAR(MAX) NULL,
        [PhoneNumber] NVARCHAR(50) NULL,
        [PhoneNumberConfirmed] BIT NOT NULL DEFAULT 0,
        [TwoFactorEnabled] BIT NOT NULL DEFAULT 0,
        [LockoutEnd] DATETIMEOFFSET NULL,
        [LockoutEnabled] BIT NOT NULL DEFAULT 1,
        [AccessFailedCount] INT NOT NULL DEFAULT 0,
        [PersonId] INT NULL,
        [DefaultOrganisationId] INT NULL,
        [IsActive] BIT NOT NULL DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_AppUser_PersonId] ON [dbo].[AppUser]([PersonId]);
    CREATE INDEX [IX_AppUser_DefaultOrganisationId] ON [dbo].[AppUser]([DefaultOrganisationId]);
    CREATE INDEX [IX_AppUser_NormalizedUserName] ON [dbo].[AppUser]([NormalizedUserName]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'AppRole')
BEGIN
    CREATE TABLE [dbo].[AppRole] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [Name] NVARCHAR(100) NOT NULL,
        [NormalizedName] NVARCHAR(100) NOT NULL,
        [ConcurrencyStamp] NVARCHAR(MAX) NULL,
        [Description] NVARCHAR(250) NULL,
        [Active] BIT NOT NULL DEFAULT 1
    );
    CREATE INDEX [IX_AppRole_NormalizedName] ON [dbo].[AppRole]([NormalizedName]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'AppUserRole')
BEGIN
    CREATE TABLE [dbo].[AppUserRole] (
        [UserId] INT NOT NULL,
        [RoleId] INT NOT NULL,
        PRIMARY KEY ([UserId], [RoleId])
    );
    CREATE INDEX [IX_AppUserRole_RoleId] ON [dbo].[AppUserRole]([RoleId]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'AppRoleClaim')
BEGIN
    CREATE TABLE [dbo].[AppRoleClaim] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [RoleId] INT NOT NULL,
        [ClaimType] NVARCHAR(MAX) NULL,
        [ClaimValue] NVARCHAR(MAX) NULL
    );
    CREATE INDEX [IX_AppRoleClaim_RoleId] ON [dbo].[AppRoleClaim]([RoleId]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'AppUserClaim')
BEGIN
    CREATE TABLE [dbo].[AppUserClaim] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [UserId] INT NOT NULL,
        [ClaimType] NVARCHAR(MAX) NULL,
        [ClaimValue] NVARCHAR(MAX) NULL
    );
    CREATE INDEX [IX_AppUserClaim_UserId] ON [dbo].[AppUserClaim]([UserId]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'AppUserLogin')
BEGIN
    CREATE TABLE [dbo].[AppUserLogin] (
        [LoginProvider] NVARCHAR(128) NOT NULL,
        [ProviderKey] NVARCHAR(128) NOT NULL,
        [ProviderDisplayName] NVARCHAR(MAX) NULL,
        [UserId] INT NOT NULL,
        PRIMARY KEY ([LoginProvider], [ProviderKey])
    );
    CREATE INDEX [IX_AppUserLogin_UserId] ON [dbo].[AppUserLogin]([UserId]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'AppUserToken')
BEGIN
    CREATE TABLE [dbo].[AppUserToken] (
        [UserId] INT NOT NULL,
        [LoginProvider] NVARCHAR(128) NOT NULL,
        [Name] NVARCHAR(128) NOT NULL,
        [Value] NVARCHAR(MAX) NULL,
        PRIMARY KEY ([UserId], [LoginProvider], [Name])
    );
END

-- System Configuration & Feature Flags
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

-- SDF Company & Appointment History Schema Sync
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'SdfCompany')
BEGIN
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('SdfCompany') AND name = 'SdfStatusCode')
        ALTER TABLE [dbo].[SdfCompany] ADD [SdfStatusCode] NVARCHAR(50) NOT NULL CONSTRAINT [DF_SdfCompany_SdfStatusCode] DEFAULT 'PendingApproval';

    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('SdfCompany') AND name = 'AppointmentStartDate')
        ALTER TABLE [dbo].[SdfCompany] ADD [AppointmentStartDate] DATETIME2 NOT NULL CONSTRAINT [DF_SdfCompany_AppointmentStartDate] DEFAULT SYSUTCDATETIME();

    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('SdfCompany') AND name = 'AppointmentEndDate')
        ALTER TABLE [dbo].[SdfCompany] ADD [AppointmentEndDate] DATETIME2 NULL;

    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('SdfCompany') AND name = 'SignedAppointmentLetterReceived')
        ALTER TABLE [dbo].[SdfCompany] ADD [SignedAppointmentLetterReceived] BIT NOT NULL CONSTRAINT [DF_SdfCompany_SignedAppointmentLetterReceived] DEFAULT 0;

    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('SdfCompany') AND name = 'SignedAcceptanceDeclarationReceived')
        ALTER TABLE [dbo].[SdfCompany] ADD [SignedAcceptanceDeclarationReceived] BIT NOT NULL CONSTRAINT [DF_SdfCompany_SignedAcceptanceDeclarationReceived] DEFAULT 0;

    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('SdfCompany') AND name = 'AllowDgApplication')
        ALTER TABLE [dbo].[SdfCompany] ADD [AllowDgApplication] BIT NOT NULL CONSTRAINT [DF_SdfCompany_AllowDgApplication] DEFAULT 1;

    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('SdfCompany') AND name = 'AllowTrancheClaims')
        ALTER TABLE [dbo].[SdfCompany] ADD [AllowTrancheClaims] BIT NOT NULL CONSTRAINT [DF_SdfCompany_AllowTrancheClaims] DEFAULT 0;

    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('SdfCompany') AND name = 'IsActive')
        ALTER TABLE [dbo].[SdfCompany] ADD [IsActive] BIT NOT NULL CONSTRAINT [DF_SdfCompany_IsActive] DEFAULT 1;
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'SdfAppointmentHistory')
BEGIN
    CREATE TABLE [dbo].[SdfAppointmentHistory] (
        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_SdfAppointmentHistory] PRIMARY KEY CLUSTERED,
        [SdfCompanyId] INT NOT NULL,
        [PreviousStatusCode] NVARCHAR(50) NOT NULL,
        [NewStatusCode] NVARCHAR(50) NOT NULL,
        [ChangeReason] NVARCHAR(MAX) NULL,
        [ChangedByUserId] NVARCHAR(100) NOT NULL CONSTRAINT [DF_SdfAppointmentHistory_ChangedBy] DEFAULT 'SYSTEM',
        [ChangedAt] DATETIME2 NOT NULL CONSTRAINT [DF_SdfAppointmentHistory_ChangedAt] DEFAULT SYSUTCDATETIME(),
        CONSTRAINT [FK_SdfAppointmentHistory_SdfCompany_SdfCompanyId] FOREIGN KEY ([SdfCompanyId]) REFERENCES [dbo].[SdfCompany] ([Id]) ON DELETE CASCADE
    );
    CREATE NONCLUSTERED INDEX [IX_SdfAppointmentHistory_SdfCompanyId] ON [dbo].[SdfAppointmentHistory]([SdfCompanyId]);
END
";

        await db.Database.ExecuteSqlRawAsync(sql);
    }
}
