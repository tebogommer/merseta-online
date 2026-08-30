using Microsoft.EntityFrameworkCore;

namespace Nsdms.Infrastructure.Data;

public static class Phase8WspSurveyAndAqpSchemaMigrator
{
    public static async Task MigrateAsync(NsdmsDbContext db)
    {
        const string sql = @"
-- 1. WspStrategicSkillsGap
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'WspStrategicSkillsGap')
BEGIN
    CREATE TABLE [dbo].[WspStrategicSkillsGap] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [WspId] INT NOT NULL,
        [OccupationTitle] NVARCHAR(150) NOT NULL DEFAULT '',
        [OfoCode] NVARCHAR(50) NULL,
        [SkillGapDescription] NVARCHAR(MAX) NOT NULL DEFAULT '',
        [CauseOfGap] NVARCHAR(MAX) NULL,
        [PlannedIntervention] NVARCHAR(MAX) NULL,
        [PriorityLevel] NVARCHAR(50) NOT NULL DEFAULT 'High',
        [TargetLearnerCount] INT NOT NULL DEFAULT 1,
        [EstimatedBudget] DECIMAL(18,2) NOT NULL DEFAULT 0,
        [IsActive] BIT NOT NULL DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_WspStrategicSkillsGap_WspId] ON [dbo].[WspStrategicSkillsGap]([WspId]);
    CREATE INDEX [IX_WspStrategicSkillsGap_IsActive] ON [dbo].[WspStrategicSkillsGap]([IsActive]);
END;

-- 2. WspTrainingImpactSurvey
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'WspTrainingImpactSurvey')
BEGIN
    CREATE TABLE [dbo].[WspTrainingImpactSurvey] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [WspId] INT NOT NULL,
        [SurveyCategory] NVARCHAR(100) NOT NULL DEFAULT '',
        [QuestionText] NVARCHAR(MAX) NOT NULL DEFAULT '',
        [RatingScore] INT NOT NULL DEFAULT 5,
        [QualitativeImpactNotes] NVARCHAR(MAX) NULL,
        [EvidenceDocumentUrl] NVARCHAR(500) NULL,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_WspTrainingImpactSurvey_WspId] ON [dbo].[WspTrainingImpactSurvey]([WspId]);
END;

-- 3. WspStrategicPriority
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'WspStrategicPriority')
BEGIN
    CREATE TABLE [dbo].[WspStrategicPriority] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [WspId] INT NOT NULL,
        [PriorityCode] NVARCHAR(100) NOT NULL DEFAULT '',
        [StrategicObjective] NVARCHAR(MAX) NOT NULL DEFAULT '',
        [AlignmentDescription] NVARCHAR(MAX) NOT NULL DEFAULT '',
        [AllocatedBudget] DECIMAL(18,2) NOT NULL DEFAULT 0,
        [IsAlignedWithNsdp] BIT NOT NULL DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_WspStrategicPriority_WspId] ON [dbo].[WspStrategicPriority]([WspId]);
END;

-- 4. AqpPartner
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'AqpPartner')
BEGIN
    CREATE TABLE [dbo].[AqpPartner] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [AqpName] NVARCHAR(200) NOT NULL DEFAULT '',
        [AqpCode] NVARCHAR(50) NOT NULL DEFAULT '',
        [AccreditationNumber] NVARCHAR(100) NOT NULL DEFAULT '',
        [QualityAssuranceBody] NVARCHAR(100) NOT NULL DEFAULT 'QCTO',
        [ContactPersonId] INT NULL,
        [Email] NVARCHAR(150) NULL,
        [PhoneNumber] NVARCHAR(50) NULL,
        [PhysicalAddress] NVARCHAR(500) NULL,
        [PostalCode] NVARCHAR(20) NULL,
        [ProvinceCode] NVARCHAR(10) NOT NULL DEFAULT 'GP',
        [AccreditationStartDate] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [AccreditationEndDate] DATETIME2 NOT NULL DEFAULT DATEADD(year, 5, GETUTCDATE()),
        [StatusCode] NVARCHAR(50) NOT NULL DEFAULT 'Active',
        [IsActive] BIT NOT NULL DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_AqpPartner_AqpCode] ON [dbo].[AqpPartner]([AqpCode]);
    CREATE INDEX [IX_AqpPartner_IsActive] ON [dbo].[AqpPartner]([IsActive]);
END;

-- 5. AqpQualificationScope
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'AqpQualificationScope')
BEGIN
    CREATE TABLE [dbo].[AqpQualificationScope] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [AqpPartnerId] INT NOT NULL,
        [QualificationTitle] NVARCHAR(250) NOT NULL DEFAULT '',
        [SaqaQualificationId] NVARCHAR(50) NULL,
        [NqfLevel] INT NOT NULL DEFAULT 4,
        [CurriculumCode] NVARCHAR(50) NULL,
        [RegistrationStatus] NVARCHAR(50) NOT NULL DEFAULT 'Active',
        [ApprovedExamCentersCount] INT NOT NULL DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_AqpQualificationScope_AqpPartnerId] ON [dbo].[AqpQualificationScope]([AqpPartnerId]);
END;

-- 6. AqpLearnerAssessment
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'AqpLearnerAssessment')
BEGIN
    CREATE TABLE [dbo].[AqpLearnerAssessment] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [AqpPartnerId] INT NOT NULL,
        [CompanyLearnerId] INT NULL,
        [PersonId] INT NULL,
        [AssessmentNumber] NVARCHAR(100) NOT NULL DEFAULT '',
        [EisaExamSession] NVARCHAR(100) NOT NULL DEFAULT '',
        [AssessmentDate] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [AssessmentCenter] NVARCHAR(200) NOT NULL DEFAULT '',
        [TheoryScorePercentage] DECIMAL(5,2) NULL,
        [PracticalScorePercentage] DECIMAL(5,2) NULL,
        [FinalOverallPercentage] DECIMAL(5,2) NOT NULL DEFAULT 0,
        [ResultStatusCode] NVARCHAR(50) NOT NULL DEFAULT 'Pending',
        [ModerationStatusCode] NVARCHAR(50) NOT NULL DEFAULT 'Pending',
        [CertificateNumber] NVARCHAR(100) NULL,
        [CertificateIssuedDate] DATETIME2 NULL,
        [ModeratorComments] NVARCHAR(MAX) NULL,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
END;

-- 7. QualificationsCurriculumDevelopment missing columns
IF OBJECT_ID(N'dbo.QualificationsCurriculumDevelopment', N'U') IS NOT NULL
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.QualificationsCurriculumDevelopment') AND name = N'NationalDevelopmentPlanEvidence')
        ALTER TABLE dbo.QualificationsCurriculumDevelopment ADD [NationalDevelopmentPlanEvidence] NVARCHAR(MAX) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.QualificationsCurriculumDevelopment') AND name = N'NewGrowthPlanEvidence')
        ALTER TABLE dbo.QualificationsCurriculumDevelopment ADD [NewGrowthPlanEvidence] NVARCHAR(MAX) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.QualificationsCurriculumDevelopment') AND name = N'IndustrialPolicyActionPlanEvidence')
        ALTER TABLE dbo.QualificationsCurriculumDevelopment ADD [IndustrialPolicyActionPlanEvidence] NVARCHAR(MAX) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.QualificationsCurriculumDevelopment') AND name = N'TargetLearnerAudience')
        ALTER TABLE dbo.QualificationsCurriculumDevelopment ADD [TargetLearnerAudience] NVARCHAR(MAX) NULL;
END;

-- 8. CurriculumWorkingGroupMember
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'CurriculumWorkingGroupMember')
BEGIN
    CREATE TABLE [dbo].[CurriculumWorkingGroupMember] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [QualificationsCurriculumDevelopmentId] INT NOT NULL CONSTRAINT FK_CWGM_QCD REFERENCES dbo.QualificationsCurriculumDevelopment(id) ON DELETE CASCADE,
        [PersonId] INT NULL CONSTRAINT FK_CWGM_Person REFERENCES dbo.Person(id),
        [MemberName] NVARCHAR(150) NOT NULL DEFAULT '',
        [StakeholderRoleTitle] NVARCHAR(100) NOT NULL DEFAULT 'IndustryExpert',
        [OrganisationRepresented] NVARCHAR(200) NOT NULL DEFAULT '',
        [EmailAddress] NVARCHAR(150) NOT NULL DEFAULT '',
        [PhoneNumber] NVARCHAR(50) NOT NULL DEFAULT '',
        [IsConfirmedAttendee] BIT NOT NULL DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_CurriculumWorkingGroupMember_QCDId] ON [dbo].[CurriculumWorkingGroupMember]([QualificationsCurriculumDevelopmentId]);
END;

-- 9. SkillsRegistration
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'SkillsRegistration')
BEGIN
    CREATE TABLE [dbo].[SkillsRegistration] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [QualificationsCurriculumDevelopmentId] INT NOT NULL CONSTRAINT FK_SkillsReg_QCD REFERENCES dbo.QualificationsCurriculumDevelopment(id) ON DELETE CASCADE,
        [NonNqfIntervCode] NVARCHAR(50) NOT NULL DEFAULT '',
        [NonNqfIntervName] NVARCHAR(200) NOT NULL DEFAULT '',
        [SubfieldId] NVARCHAR(10) NOT NULL DEFAULT '06',
        [EtqaId] NVARCHAR(10) NOT NULL DEFAULT '17',
        [NonNqfIntervStatusId] NVARCHAR(10) NOT NULL DEFAULT '01',
        [LearningProgrammeTypeId] NVARCHAR(10) NOT NULL DEFAULT '03',
        [RegistrationStartDate] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [RegistrationEndDate] DATETIME2 NULL,
        [Credits] INT NOT NULL DEFAULT 30,
        [NqfLevel] INT NOT NULL DEFAULT 3,
        [UnitStandardsIncludedJson] NVARCHAR(MAX) NOT NULL DEFAULT '[]',
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_SkillsRegistration_QCDId] ON [dbo].[SkillsRegistration]([QualificationsCurriculumDevelopmentId]);
    CREATE INDEX [IX_SkillsRegistration_Code] ON [dbo].[SkillsRegistration]([NonNqfIntervCode]);
END;

-- 10. NonSetaCompany
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'NonSetaCompany')
BEGIN
    CREATE TABLE [dbo].[NonSetaCompany] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [CompanyName] NVARCHAR(200) NOT NULL DEFAULT '',
        [SdlNumber] NVARCHAR(50) NULL,
        [PrimarySetaCode] NVARCHAR(50) NOT NULL DEFAULT 'W&RSETA',
        [CompanyRegistrationNumber] NVARCHAR(50) NULL,
        [Email] NVARCHAR(150) NOT NULL DEFAULT '',
        [PhoneNumber] NVARCHAR(50) NOT NULL DEFAULT '',
        [PhysicalAddress] NVARCHAR(500) NULL,
        [IsActive] BIT NOT NULL DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE INDEX [IX_NonSetaCompany_Seta] ON [dbo].[NonSetaCompany]([PrimarySetaCode]);
END;

-- 11. WorkplaceMonitoringSiteVisit Columns
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = 'WorkplaceMonitoringSiteVisit')
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceMonitoringSiteVisit]') AND name = 'ApprovalComments')
        ALTER TABLE [dbo].[WorkplaceMonitoringSiteVisit] ADD [ApprovalComments] NVARCHAR(MAX) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceMonitoringSiteVisit]') AND name = 'ApprovalDate')
        ALTER TABLE [dbo].[WorkplaceMonitoringSiteVisit] ADD [ApprovalDate] DATETIME2 NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceMonitoringSiteVisit]') AND name = 'ApprovedByUserId')
        ALTER TABLE [dbo].[WorkplaceMonitoringSiteVisit] ADD [ApprovedByUserId] NVARCHAR(100) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceMonitoringSiteVisit]') AND name = 'CloUserId')
        ALTER TABLE [dbo].[WorkplaceMonitoringSiteVisit] ADD [CloUserId] NVARCHAR(100) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceMonitoringSiteVisit]') AND name = 'CrmUserId')
        ALTER TABLE [dbo].[WorkplaceMonitoringSiteVisit] ADD [CrmUserId] NVARCHAR(100) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceMonitoringSiteVisit]') AND name = 'NonComplianceApprovalDate')
        ALTER TABLE [dbo].[WorkplaceMonitoringSiteVisit] ADD [NonComplianceApprovalDate] DATETIME2 NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceMonitoringSiteVisit]') AND name = 'NonComplianceHoldingArea')
        ALTER TABLE [dbo].[WorkplaceMonitoringSiteVisit] ADD [NonComplianceHoldingArea] BIT NOT NULL DEFAULT 0;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceMonitoringSiteVisit]') AND name = 'NonComplianceNotes')
        ALTER TABLE [dbo].[WorkplaceMonitoringSiteVisit] ADD [NonComplianceNotes] NVARCHAR(MAX) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceMonitoringSiteVisit]') AND name = 'NonComplianceSubmittedDate')
        ALTER TABLE [dbo].[WorkplaceMonitoringSiteVisit] ADD [NonComplianceSubmittedDate] DATETIME2 NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceMonitoringSiteVisit]') AND name = 'NonCompliancesIdentified')
        ALTER TABLE [dbo].[WorkplaceMonitoringSiteVisit] ADD [NonCompliancesIdentified] BIT NOT NULL DEFAULT 0;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceMonitoringSiteVisit]') AND name = 'SignOffState')
        ALTER TABLE [dbo].[WorkplaceMonitoringSiteVisit] ADD [SignOffState] BIT NOT NULL DEFAULT 0;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceMonitoringSiteVisit]') AND name = 'StatusCode')
        ALTER TABLE [dbo].[WorkplaceMonitoringSiteVisit] ADD [StatusCode] NVARCHAR(50) NOT NULL DEFAULT 'Draft';
END;

-- 12. SdpExtensionOfScope Columns
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = 'SdpExtensionOfScope')
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[SdpExtensionOfScope]') AND name = 'AdditionalQualificationTitle')
        ALTER TABLE [dbo].[SdpExtensionOfScope] ADD [AdditionalQualificationTitle] NVARCHAR(250) NOT NULL DEFAULT '';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[SdpExtensionOfScope]') AND name = 'ApprovalDate')
        ALTER TABLE [dbo].[SdpExtensionOfScope] ADD [ApprovalDate] DATETIME2 NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[SdpExtensionOfScope]') AND name = 'ApprovedByUserId')
        ALTER TABLE [dbo].[SdpExtensionOfScope] ADD [ApprovedByUserId] NVARCHAR(100) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[SdpExtensionOfScope]') AND name = 'CommitteeDecisionReference')
        ALTER TABLE [dbo].[SdpExtensionOfScope] ADD [CommitteeDecisionReference] NVARCHAR(100) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[SdpExtensionOfScope]') AND name = 'SiteEvaluatorUserId')
        ALTER TABLE [dbo].[SdpExtensionOfScope] ADD [SiteEvaluatorUserId] NVARCHAR(100) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[SdpExtensionOfScope]') AND name = 'SiteInspectionDate')
        ALTER TABLE [dbo].[SdpExtensionOfScope] ADD [SiteInspectionDate] DATETIME2 NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[SdpExtensionOfScope]') AND name = 'SiteInspectionPassed')
        ALTER TABLE [dbo].[SdpExtensionOfScope] ADD [SiteInspectionPassed] BIT NOT NULL DEFAULT 0;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[SdpExtensionOfScope]') AND name = 'StatusCode')
        ALTER TABLE [dbo].[SdpExtensionOfScope] ADD [StatusCode] NVARCHAR(50) NOT NULL DEFAULT 'Submitted';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[SdpExtensionOfScope]') AND name = 'ApplicationNumber')
        ALTER TABLE [dbo].[SdpExtensionOfScope] ADD [ApplicationNumber] NVARCHAR(50) NOT NULL DEFAULT '';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[SdpExtensionOfScope]') AND name = 'SaqaQualificationId')
        ALTER TABLE [dbo].[SdpExtensionOfScope] ADD [SaqaQualificationId] NVARCHAR(50) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[SdpExtensionOfScope]') AND name = 'NqfLevel')
        ALTER TABLE [dbo].[SdpExtensionOfScope] ADD [NqfLevel] INT NOT NULL DEFAULT 0;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[SdpExtensionOfScope]') AND name = 'Credits')
        ALTER TABLE [dbo].[SdpExtensionOfScope] ADD [Credits] INT NOT NULL DEFAULT 0;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[SdpExtensionOfScope]') AND name = 'ProgrammeTypeCode')
        ALTER TABLE [dbo].[SdpExtensionOfScope] ADD [ProgrammeTypeCode] NVARCHAR(50) NOT NULL DEFAULT 'OccupationalCertificate';
END;

-- 13. AssessorExtensionOfScope Columns
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = 'AssessorExtensionOfScope')
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[AssessorExtensionOfScope]') AND name = 'AdditionalQualificationTitle')
        ALTER TABLE [dbo].[AssessorExtensionOfScope] ADD [AdditionalQualificationTitle] NVARCHAR(250) NOT NULL DEFAULT '';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[AssessorExtensionOfScope]') AND name = 'ApprovalDate')
        ALTER TABLE [dbo].[AssessorExtensionOfScope] ADD [ApprovalDate] DATETIME2 NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[AssessorExtensionOfScope]') AND name = 'ApprovedByUserId')
        ALTER TABLE [dbo].[AssessorExtensionOfScope] ADD [ApprovedByUserId] NVARCHAR(100) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[AssessorExtensionOfScope]') AND name = 'ReviewNotes')
        ALTER TABLE [dbo].[AssessorExtensionOfScope] ADD [ReviewNotes] NVARCHAR(MAX) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[AssessorExtensionOfScope]') AND name = 'ReviewDate')
        ALTER TABLE [dbo].[AssessorExtensionOfScope] ADD [ReviewDate] DATETIME2 NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[AssessorExtensionOfScope]') AND name = 'ReviewedByUserId')
        ALTER TABLE [dbo].[AssessorExtensionOfScope] ADD [ReviewedByUserId] NVARCHAR(100) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[AssessorExtensionOfScope]') AND name = 'StatusCode')
        ALTER TABLE [dbo].[AssessorExtensionOfScope] ADD [StatusCode] NVARCHAR(50) NOT NULL DEFAULT 'Submitted';
END;
";
        await db.Database.ExecuteSqlRawAsync(sql);
    }
}
