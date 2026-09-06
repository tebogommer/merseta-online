-- ==============================================================================================
-- V2026_32_Fix_Enterprise_Schema_Defects.sql
-- MerSETA NSDMS 2.0: Synchronize missing tables and columns for:
-- 1. WorkplaceApproval role-neutral columns and indexes
-- 2. LearnerTradeTestApplication & LearnerTradeTest PreviousTrainingCenterId
-- 3. LearnerRegisteredUnitStandard table
-- 4. SummativeAssessmentUnitStandard table
-- 5. EisaAssessmentEntry table
-- 6. LearnerCertificate table
-- ==============================================================================================

-- 1. Add missing columns to WorkplaceApproval
IF OBJECT_ID(N'[dbo].[WorkplaceApproval]', N'U') IS NOT NULL
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'LearningProgramTypeCode')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [LearningProgramTypeCode] NVARCHAR(100) NULL DEFAULT 'Apprenticeship';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'RequiresWorkplaceApproval')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [RequiresWorkplaceApproval] BIT NOT NULL DEFAULT 1;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'IsSiteVisitRequired')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [IsSiteVisitRequired] BIT NULL DEFAULT 1;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'SiteVisitJustification')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [SiteVisitJustification] NVARCHAR(MAX) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'InspectionDueDate')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [InspectionDueDate] DATETIME2 NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'VerificationRecommendationReason')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [VerificationRecommendationReason] NVARCHAR(100) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'VerificationRecommendationExplanation')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [VerificationRecommendationExplanation] NVARCHAR(MAX) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'VerificationRejectionReason')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [VerificationRejectionReason] NVARCHAR(100) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'VerificationRejectionExplanation')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [VerificationRejectionExplanation] NVARCHAR(MAX) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'VerifiedDate')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [VerifiedDate] DATETIME2 NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'VerifiedByPersonId')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [VerifiedByPersonId] INT NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'ApprovalReason')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [ApprovalReason] NVARCHAR(100) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'ApprovalExplanation')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [ApprovalExplanation] NVARCHAR(MAX) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'RejectionReason')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [RejectionReason] NVARCHAR(100) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'RejectionExplanation')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [RejectionExplanation] NVARCHAR(MAX) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'DecisionDate')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [DecisionDate] DATETIME2 NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'DecisionByPersonId')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [DecisionByPersonId] INT NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'IsNonMerSetaCompany')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [IsNonMerSetaCompany] BIT NOT NULL DEFAULT 0;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'HomeSetaName')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [HomeSetaName] NVARCHAR(150) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = 'HomeSetaAgreementRef')
        ALTER TABLE [dbo].[WorkplaceApproval] ADD [HomeSetaAgreementRef] NVARCHAR(100) NULL;
END
GO

-- 2. Add missing columns to WorkplaceApprovalMentor
IF OBJECT_ID(N'[dbo].[WorkplaceApprovalMentor]', N'U') IS NOT NULL
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApprovalMentor]') AND name = 'ApprovalStatusCode')
        ALTER TABLE [dbo].[WorkplaceApprovalMentor] ADD [ApprovalStatusCode] NVARCHAR(50) NOT NULL DEFAULT 'Approved';

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApprovalMentor]') AND name = 'RejectionReason')
        ALTER TABLE [dbo].[WorkplaceApprovalMentor] ADD [RejectionReason] NVARCHAR(MAX) NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApprovalMentor]') AND name = 'VerifiedDate')
        ALTER TABLE [dbo].[WorkplaceApprovalMentor] ADD [VerifiedDate] DATETIME2 NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApprovalMentor]') AND name = 'VerifiedByPersonId')
        ALTER TABLE [dbo].[WorkplaceApprovalMentor] ADD [VerifiedByPersonId] INT NULL;
END
GO

-- Indexes for WorkplaceApproval
IF OBJECT_ID(N'[dbo].[WorkplaceApproval]', N'U') IS NOT NULL
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = N'IX_WorkplaceApproval_VerifiedByPersonId')
        CREATE NONCLUSTERED INDEX [IX_WorkplaceApproval_VerifiedByPersonId] ON [dbo].[WorkplaceApproval] ([VerifiedByPersonId]) WHERE [VerifiedByPersonId] IS NOT NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = N'IX_WorkplaceApproval_DecisionByPersonId')
        CREATE NONCLUSTERED INDEX [IX_WorkplaceApproval_DecisionByPersonId] ON [dbo].[WorkplaceApproval] ([DecisionByPersonId]) WHERE [DecisionByPersonId] IS NOT NULL;

    IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID(N'[dbo].[WorkplaceApproval]') AND name = N'IX_WorkplaceApproval_InspectionDueDate')
        CREATE NONCLUSTERED INDEX [IX_WorkplaceApproval_InspectionDueDate] ON [dbo].[WorkplaceApproval] ([InspectionDueDate]) WHERE [InspectionDueDate] IS NOT NULL;
END
GO

-- 3. Add PreviousTrainingCenterId to LearnerTradeTestApplication and LearnerTradeTest
IF OBJECT_ID(N'[dbo].[LearnerTradeTestApplication]', N'U') IS NOT NULL
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[LearnerTradeTestApplication]') AND name = 'PreviousTrainingCenterId')
        ALTER TABLE [dbo].[LearnerTradeTestApplication] ADD [PreviousTrainingCenterId] INT NULL;
END
GO

IF OBJECT_ID(N'[dbo].[LearnerTradeTest]', N'U') IS NOT NULL
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[LearnerTradeTest]') AND name = 'PreviousTrainingCenterId')
        ALTER TABLE [dbo].[LearnerTradeTest] ADD [PreviousTrainingCenterId] INT NULL;
END
GO

-- 4. Create LearnerRegisteredUnitStandard table
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'LearnerRegisteredUnitStandard')
BEGIN
    CREATE TABLE dbo.LearnerRegisteredUnitStandard (
        Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        CompanyLearnerId INT NOT NULL,
        UnitStandardId INT NOT NULL,
        UnitStandardTitle NVARCHAR(250) NOT NULL,
        NqfLevel INT NULL,
        Credits INT NULL,
        IsCore BIT NOT NULL DEFAULT 1,
        StatusCode NVARCHAR(50) NOT NULL DEFAULT 'Enrolled',
        CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NULL,
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_LearnerRegisteredUnitStandard_CompanyLearner FOREIGN KEY (CompanyLearnerId) REFERENCES dbo.LearnerEnrolment(Id) ON DELETE CASCADE
    );

    CREATE INDEX IX_LearnerRegisteredUnitStandard_CompanyLearnerId ON dbo.LearnerRegisteredUnitStandard(CompanyLearnerId);
    CREATE INDEX IX_LearnerRegisteredUnitStandard_UnitStandardId ON dbo.LearnerRegisteredUnitStandard(UnitStandardId);
    CREATE INDEX IX_LearnerRegisteredUnitStandard_StatusCode ON dbo.LearnerRegisteredUnitStandard(StatusCode);
END
GO

-- 5. Create SummativeAssessmentUnitStandard table
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'SummativeAssessmentUnitStandard')
BEGIN
    CREATE TABLE dbo.SummativeAssessmentUnitStandard (
        Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        SummativeAssessmentReportId INT NOT NULL,
        UnitStandardCode NVARCHAR(50) NOT NULL,
        UnitStandardTitle NVARCHAR(250) NOT NULL,
        NqfLevel INT NOT NULL DEFAULT 4,
        Credits INT NOT NULL DEFAULT 10,
        UnitStandardTypeCode NVARCHAR(50) NOT NULL DEFAULT 'Core',
        IsMandatory BIT NOT NULL DEFAULT 1,
        IsNonMandatoryElective BIT NOT NULL DEFAULT 0,
        AssessorPersonId INT NULL,
        InternalModeratorPersonId INT NULL,
        AssessmentDate DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        ScoreAchieved DECIMAL(18,2) NOT NULL DEFAULT 0,
        CompetencyStatusCode NVARCHAR(50) NOT NULL DEFAULT 'Competent',
        AssessorComments NVARCHAR(MAX) NULL,
        IsModerated BIT NOT NULL DEFAULT 0,
        ModerationOutcome NVARCHAR(50) NOT NULL DEFAULT 'Upheld',
        ModeratorComments NVARCHAR(MAX) NULL,
        CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NULL,
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_SummativeAssessmentUS_Report FOREIGN KEY (SummativeAssessmentReportId) REFERENCES dbo.SummativeAssessmentReport(Id) ON DELETE CASCADE,
        CONSTRAINT FK_SummativeAssessmentUS_Assessor FOREIGN KEY (AssessorPersonId) REFERENCES dbo.Person(Id),
        CONSTRAINT FK_SummativeAssessmentUS_Moderator FOREIGN KEY (InternalModeratorPersonId) REFERENCES dbo.Person(Id)
    );

    CREATE INDEX IX_SummativeAssessmentUS_ReportId ON dbo.SummativeAssessmentUnitStandard(SummativeAssessmentReportId);
    CREATE INDEX IX_SummativeAssessmentUS_AssessorPersonId ON dbo.SummativeAssessmentUnitStandard(AssessorPersonId);
    CREATE INDEX IX_SummativeAssessmentUS_InternalModeratorPersonId ON dbo.SummativeAssessmentUnitStandard(InternalModeratorPersonId);
END
GO

-- 6. Create EisaAssessmentEntry table
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'EisaAssessmentEntry')
BEGIN
    CREATE TABLE dbo.EisaAssessmentEntry (
        Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        SummativeAssessmentReportId INT NOT NULL,
        EisaAssessmentDate DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        EisaCenterName NVARCHAR(250) NOT NULL DEFAULT '',
        AssessmentPaperCode NVARCHAR(100) NOT NULL DEFAULT '',
        ScoreAchieved DECIMAL(18,2) NOT NULL DEFAULT 0,
        TotalScorePossible DECIMAL(18,2) NOT NULL DEFAULT 100,
        PercentageScore DECIMAL(18,2) NOT NULL DEFAULT 0,
        CompetencyStatusCode NVARCHAR(50) NOT NULL DEFAULT 'Competent',
        QctoModerationReferenceNumber NVARCHAR(100) NULL,
        QctoSignOffDate DATETIME2 NULL,
        CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NULL,
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_EisaAssessmentEntry_Report FOREIGN KEY (SummativeAssessmentReportId) REFERENCES dbo.SummativeAssessmentReport(Id) ON DELETE CASCADE
    );

    CREATE INDEX IX_EisaAssessmentEntry_ReportId ON dbo.EisaAssessmentEntry(SummativeAssessmentReportId);
END
GO

-- 7. Create LearnerCertificate table
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'LearnerCertificate')
BEGIN
    CREATE TABLE dbo.LearnerCertificate (
        Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        CompanyLearnerId INT NOT NULL,
        PersonId INT NOT NULL,
        CertificatePrintingBatchId INT NULL,
        SummativeAssessmentReportId INT NOT NULL,
        CertificateNumber NVARCHAR(50) NOT NULL,
        QualificationTitle NVARCHAR(250) NOT NULL,
        SaqaQualificationId NVARCHAR(50) NULL,
        NqfLevel INT NOT NULL DEFAULT 4,
        IssueDate DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        TamperProofHashSha256 NVARCHAR(100) NOT NULL DEFAULT '',
        IsReprintOrReplacement BIT NOT NULL DEFAULT 0,
        ReplacementReason NVARCHAR(500) NULL,
        CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NULL,
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_LearnerCertificate_CompanyLearner FOREIGN KEY (CompanyLearnerId) REFERENCES dbo.LearnerEnrolment(Id),
        CONSTRAINT FK_LearnerCertificate_Person FOREIGN KEY (PersonId) REFERENCES dbo.Person(Id),
        CONSTRAINT FK_LearnerCertificate_Batch FOREIGN KEY (CertificatePrintingBatchId) REFERENCES dbo.CertificatePrintingBatch(Id) ON DELETE SET NULL,
        CONSTRAINT FK_LearnerCertificate_Report FOREIGN KEY (SummativeAssessmentReportId) REFERENCES dbo.SummativeAssessmentReport(Id)
    );

    CREATE UNIQUE INDEX IX_LearnerCertificate_CertificateNumber ON dbo.LearnerCertificate(CertificateNumber);
    CREATE INDEX IX_LearnerCertificate_CompanyLearnerId ON dbo.LearnerCertificate(CompanyLearnerId);
    CREATE INDEX IX_LearnerCertificate_PersonId ON dbo.LearnerCertificate(PersonId);
    CREATE INDEX IX_LearnerCertificate_PrintingBatchId ON dbo.LearnerCertificate(CertificatePrintingBatchId);
END
GO
