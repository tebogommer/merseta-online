-- ====================================================================================================
-- MERSETA NSDMS ENTERPRISE DDL MIGRATION
-- Script: V2026_21_Assessor_Moderator_Full_Lifecycle.sql
-- Description: Creates AssessorRegistrationApplication, AssessorApplicationScope,
--              AssessorApplicationUnitStandard, AssessorApplicationProviderLink,
--              AssessorApplicationDocument, AssessorUnitStandardScope,
--              AssessorProviderLink, and AssessorDisciplinaryCase tables.
-- Reference: MerSeta\NSDMS\LMS\LR\01 - Assessor/Moderator Registration Application Use Case
-- ====================================================================================================

-- 1. AssessorRegistrationApplication
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'AssessorRegistrationApplication' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE dbo.AssessorRegistrationApplication (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_AssessorRegistrationApplication PRIMARY KEY CLUSTERED,
        ApplicationNumber NVARCHAR(50) NOT NULL,
        PractitionerType NVARCHAR(50) NOT NULL,
        PersonId INT NOT NULL,
        LastSchoolAttended NVARCHAR(150) NULL,
        LastSchoolYear INT NULL,
        EmploymentStatusCode NVARCHAR(30) NOT NULL CONSTRAINT DF_AssessorApp_EmploymentStatus DEFAULT ('Employed'),
        DisabilityTypeCode NVARCHAR(50) NULL,
        DisabilitySeverityCode NVARCHAR(50) NULL,
        UrbanRuralArea NVARCHAR(20) NOT NULL CONSTRAINT DF_AssessorApp_UrbanRural DEFAULT ('Urban'),
        NextOfKinName NVARCHAR(150) NULL,
        NextOfKinContact NVARCHAR(50) NULL,
        NextOfKinRelationship NVARCHAR(50) NULL,
        HighestQualificationTitle NVARCHAR(250) NULL,
        HighestQualificationObtainedDate DATETIME2(7) NULL,
        ApplicationStatusCode NVARCHAR(40) NOT NULL CONSTRAINT DF_AssessorApp_Status DEFAULT ('Draft'),
        IsDeclarationAcknowledged BIT NOT NULL CONSTRAINT DF_AssessorApp_IsAck DEFAULT (0),
        SignedOffByUserId NVARCHAR(100) NULL,
        SignedOffAt DATETIME2(7) NULL,
        VerificationRecommendation NVARCHAR(50) NULL,
        VerificationReason NVARCHAR(100) NULL,
        VerificationExplanation NVARCHAR(MAX) NULL,
        VerifiedByUserId NVARCHAR(100) NULL,
        VerificationDate DATETIME2(7) NULL,
        EvaluationRecommendation NVARCHAR(50) NULL,
        EvaluationReason NVARCHAR(100) NULL,
        EvaluationExplanation NVARCHAR(MAX) NULL,
        EvaluatedByUserId NVARCHAR(100) NULL,
        EvaluationDate DATETIME2(7) NULL,
        ReviewCommitteeDecision NVARCHAR(50) NULL,
        ReviewCommitteeDecisionNumber NVARCHAR(100) NULL,
        ReviewCommitteeMeetingDate DATETIME2(7) NULL,
        ReviewCommitteeNotes NVARCHAR(MAX) NULL,
        IsFinalRejection BIT NOT NULL CONSTRAINT DF_AssessorApp_FinalRejection DEFAULT (0),
        RejectionReason NVARCHAR(100) NULL,
        RejectionComments NVARCHAR(MAX) NULL,
        ApprovedByUserId NVARCHAR(100) NULL,
        ApprovalDate DATETIME2(7) NULL,
        ApprovalComments NVARCHAR(MAX) NULL,
        DigitalSecuritySeal NVARCHAR(64) NULL,
        RegisteredAssessorId INT NULL,
        WithdrawalReason NVARCHAR(100) NULL,
        WithdrawalComments NVARCHAR(MAX) NULL,
        WithdrawnAt DATETIME2(7) NULL,
        WithdrawnByUserId NVARCHAR(100) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_AssessorApp_CreatedAt DEFAULT (SYSUTCDATETIME()),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_AssessorApp_CreatedBy DEFAULT ('SYSTEM'),
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_AssessorRegistrationApplication_Person FOREIGN KEY (PersonId) REFERENCES dbo.Person (id) ON DELETE NO ACTION,
        CONSTRAINT FK_AssessorRegistrationApplication_Assessor FOREIGN KEY (RegisteredAssessorId) REFERENCES dbo.EtqaAssessor (id) ON DELETE SET NULL
    );

    CREATE UNIQUE NONCLUSTERED INDEX UQ_AssessorRegistrationApplication_Number ON dbo.AssessorRegistrationApplication (ApplicationNumber);
    CREATE NONCLUSTERED INDEX IX_AssessorRegistrationApplication_PersonId ON dbo.AssessorRegistrationApplication (PersonId);
    CREATE NONCLUSTERED INDEX IX_AssessorRegistrationApplication_Status ON dbo.AssessorRegistrationApplication (ApplicationStatusCode);
    CREATE NONCLUSTERED INDEX IX_AssessorRegistrationApplication_Type ON dbo.AssessorRegistrationApplication (PractitionerType);
    CREATE NONCLUSTERED INDEX IX_AssessorRegistrationApplication_AssessorId ON dbo.AssessorRegistrationApplication (RegisteredAssessorId);
END
GO

-- 2. AssessorApplicationScope
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'AssessorApplicationScope' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE dbo.AssessorApplicationScope (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_AssessorApplicationScope PRIMARY KEY CLUSTERED,
        AssessorRegistrationApplicationId INT NOT NULL,
        SaqaQualificationId INT NOT NULL,
        QualificationTitle NVARCHAR(250) NOT NULL,
        QualificationObtainedDate DATETIME2(7) NOT NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_AssessorAppScope_CreatedAt DEFAULT (SYSUTCDATETIME()),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_AssessorAppScope_CreatedBy DEFAULT ('SYSTEM'),
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_AssessorApplicationScope_App FOREIGN KEY (AssessorRegistrationApplicationId) REFERENCES dbo.AssessorRegistrationApplication (id) ON DELETE CASCADE
    );

    CREATE NONCLUSTERED INDEX IX_AssessorApplicationScope_AppId ON dbo.AssessorApplicationScope (AssessorRegistrationApplicationId);
    CREATE NONCLUSTERED INDEX IX_AssessorApplicationScope_SaqaId ON dbo.AssessorApplicationScope (SaqaQualificationId);
END
GO

-- 3. AssessorApplicationUnitStandard
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'AssessorApplicationUnitStandard' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE dbo.AssessorApplicationUnitStandard (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_AssessorApplicationUnitStandard PRIMARY KEY CLUSTERED,
        AssessorApplicationScopeId INT NOT NULL,
        UnitStandardCode NVARCHAR(20) NOT NULL,
        UnitStandardTitle NVARCHAR(300) NOT NULL,
        NqfLevel INT NOT NULL CONSTRAINT DF_AssessorAppUS_Nqf DEFAULT (4),
        Credits INT NOT NULL CONSTRAINT DF_AssessorAppUS_Credits DEFAULT (15),
        IsPopulatedFromQualification BIT NOT NULL CONSTRAINT DF_AssessorAppUS_IsPopulated DEFAULT (1),
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_AssessorAppUS_CreatedAt DEFAULT (SYSUTCDATETIME()),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_AssessorAppUS_CreatedBy DEFAULT ('SYSTEM'),
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_AssessorApplicationUnitStandard_Scope FOREIGN KEY (AssessorApplicationScopeId) REFERENCES dbo.AssessorApplicationScope (id) ON DELETE CASCADE
    );

    CREATE NONCLUSTERED INDEX IX_AssessorApplicationUnitStandard_ScopeId ON dbo.AssessorApplicationUnitStandard (AssessorApplicationScopeId);
    CREATE NONCLUSTERED INDEX IX_AssessorApplicationUnitStandard_Code ON dbo.AssessorApplicationUnitStandard (UnitStandardCode);
END
GO

-- 4. AssessorApplicationProviderLink
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'AssessorApplicationProviderLink' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE dbo.AssessorApplicationProviderLink (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_AssessorApplicationProviderLink PRIMARY KEY CLUSTERED,
        AssessorRegistrationApplicationId INT NOT NULL,
        TrainingProviderId INT NOT NULL,
        SlaDocumentRef NVARCHAR(500) NULL,
        IsVerifiedByProvider BIT NOT NULL CONSTRAINT DF_AssessorAppProvider_IsVerified DEFAULT (0),
        VerificationDate DATETIME2(7) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_AssessorAppProvider_CreatedAt DEFAULT (SYSUTCDATETIME()),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_AssessorAppProvider_CreatedBy DEFAULT ('SYSTEM'),
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_AssessorApplicationProviderLink_App FOREIGN KEY (AssessorRegistrationApplicationId) REFERENCES dbo.AssessorRegistrationApplication (id) ON DELETE CASCADE,
        CONSTRAINT FK_AssessorApplicationProviderLink_Provider FOREIGN KEY (TrainingProviderId) REFERENCES dbo.TrainingProvider (id) ON DELETE NO ACTION
    );

    CREATE NONCLUSTERED INDEX IX_AssessorApplicationProviderLink_AppId ON dbo.AssessorApplicationProviderLink (AssessorRegistrationApplicationId);
    CREATE NONCLUSTERED INDEX IX_AssessorApplicationProviderLink_ProviderId ON dbo.AssessorApplicationProviderLink (TrainingProviderId);
END
GO

-- 5. AssessorApplicationDocument
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'AssessorApplicationDocument' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE dbo.AssessorApplicationDocument (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_AssessorApplicationDocument PRIMARY KEY CLUSTERED,
        AssessorRegistrationApplicationId INT NOT NULL,
        DocumentTypeCode NVARCHAR(50) NOT NULL,
        DocumentTitle NVARCHAR(250) NOT NULL,
        FileStoragePath NVARCHAR(500) NOT NULL,
        VersionNumber INT NOT NULL CONSTRAINT DF_AssessorAppDoc_Version DEFAULT (1),
        IsVerified BIT NOT NULL CONSTRAINT DF_AssessorAppDoc_IsVerified DEFAULT (0),
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_AssessorAppDoc_CreatedAt DEFAULT (SYSUTCDATETIME()),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_AssessorAppDoc_CreatedBy DEFAULT ('SYSTEM'),
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_AssessorApplicationDocument_App FOREIGN KEY (AssessorRegistrationApplicationId) REFERENCES dbo.AssessorRegistrationApplication (id) ON DELETE CASCADE
    );

    CREATE NONCLUSTERED INDEX IX_AssessorApplicationDocument_AppId ON dbo.AssessorApplicationDocument (AssessorRegistrationApplicationId);
    CREATE NONCLUSTERED INDEX IX_AssessorApplicationDocument_Type ON dbo.AssessorApplicationDocument (DocumentTypeCode);
END
GO

-- 6. AssessorUnitStandardScope
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'AssessorUnitStandardScope' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE dbo.AssessorUnitStandardScope (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_AssessorUnitStandardScope PRIMARY KEY CLUSTERED,
        AssessorModeratorScopeId INT NOT NULL,
        UnitStandardCode NVARCHAR(20) NOT NULL,
        UnitStandardTitle NVARCHAR(300) NOT NULL,
        NqfLevel INT NOT NULL CONSTRAINT DF_AssessorUSScope_Nqf DEFAULT (4),
        Credits INT NOT NULL CONSTRAINT DF_AssessorUSScope_Credits DEFAULT (15),
        IsPopulatedFromQualification BIT NOT NULL CONSTRAINT DF_AssessorUSScope_IsPopulated DEFAULT (1),
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_AssessorUSScope_CreatedAt DEFAULT (SYSUTCDATETIME()),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_AssessorUSScope_CreatedBy DEFAULT ('SYSTEM'),
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_AssessorUnitStandardScope_Scope FOREIGN KEY (AssessorModeratorScopeId) REFERENCES dbo.AssessorModeratorScope (id) ON DELETE CASCADE
    );

    CREATE NONCLUSTERED INDEX IX_AssessorUnitStandardScope_ScopeId ON dbo.AssessorUnitStandardScope (AssessorModeratorScopeId);
    CREATE NONCLUSTERED INDEX IX_AssessorUnitStandardScope_Code ON dbo.AssessorUnitStandardScope (UnitStandardCode);
END
GO

-- 7. AssessorProviderLink
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'AssessorProviderLink' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE dbo.AssessorProviderLink (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_AssessorProviderLink PRIMARY KEY CLUSTERED,
        EtqaAssessorId INT NOT NULL,
        TrainingProviderId INT NOT NULL,
        SlaDocumentRef NVARCHAR(500) NULL,
        IsActive BIT NOT NULL CONSTRAINT DF_AssessorProviderLink_IsActive DEFAULT (1),
        VerifiedDate DATETIME2(7) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_AssessorProviderLink_CreatedAt DEFAULT (SYSUTCDATETIME()),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_AssessorProviderLink_CreatedBy DEFAULT ('SYSTEM'),
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_AssessorProviderLink_Assessor FOREIGN KEY (EtqaAssessorId) REFERENCES dbo.EtqaAssessor (id) ON DELETE CASCADE,
        CONSTRAINT FK_AssessorProviderLink_Provider FOREIGN KEY (TrainingProviderId) REFERENCES dbo.TrainingProvider (id) ON DELETE NO ACTION
    );

    CREATE NONCLUSTERED INDEX IX_AssessorProviderLink_AssessorId ON dbo.AssessorProviderLink (EtqaAssessorId);
    CREATE NONCLUSTERED INDEX IX_AssessorProviderLink_ProviderId ON dbo.AssessorProviderLink (TrainingProviderId);
END
GO

-- 8. AssessorDisciplinaryCase
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'AssessorDisciplinaryCase' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE dbo.AssessorDisciplinaryCase (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_AssessorDisciplinaryCase PRIMARY KEY CLUSTERED,
        EtqaAssessorId INT NOT NULL,
        CaseNumber NVARCHAR(50) NOT NULL,
        CaseType NVARCHAR(50) NOT NULL,
        ComplaintSummary NVARCHAR(2000) NOT NULL,
        ComplaintDocumentRef NVARCHAR(500) NULL,
        InvestigationStartDate DATETIME2(7) NULL,
        InvestigationEndDate DATETIME2(7) NULL,
        InvestigationReportSummary NVARCHAR(MAX) NULL,
        ReviewCommitteeDecisionNumber NVARCHAR(100) NULL,
        ReviewCommitteeDate DATETIME2(7) NULL,
        OutcomeCode NVARCHAR(30) NOT NULL CONSTRAINT DF_AssessorDisciplinary_Outcome DEFAULT ('DEREGISTERED'),
        SuspensionStartDate DATETIME2(7) NULL,
        SuspensionEndDate DATETIME2(7) NULL,
        DevelopmentPlanDetails NVARCHAR(MAX) NULL,
        DecisionLetterDocumentRef NVARCHAR(500) NULL,
        Status NVARCHAR(30) NOT NULL CONSTRAINT DF_AssessorDisciplinary_Status DEFAULT ('Open'),
        ClosedAt DATETIME2(7) NULL,
        ClosedByUserId NVARCHAR(100) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_AssessorDisciplinary_CreatedAt DEFAULT (SYSUTCDATETIME()),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_AssessorDisciplinary_CreatedBy DEFAULT ('SYSTEM'),
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_AssessorDisciplinaryCase_Assessor FOREIGN KEY (EtqaAssessorId) REFERENCES dbo.EtqaAssessor (id) ON DELETE CASCADE
    );

    CREATE UNIQUE NONCLUSTERED INDEX UQ_AssessorDisciplinaryCase_Number ON dbo.AssessorDisciplinaryCase (CaseNumber);
    CREATE NONCLUSTERED INDEX IX_AssessorDisciplinaryCase_AssessorId ON dbo.AssessorDisciplinaryCase (EtqaAssessorId);
    CREATE NONCLUSTERED INDEX IX_AssessorDisciplinaryCase_Type ON dbo.AssessorDisciplinaryCase (CaseType);
    CREATE NONCLUSTERED INDEX IX_AssessorDisciplinaryCase_Status ON dbo.AssessorDisciplinaryCase (Status);
END
GO

-- 9. Add DeRegistrationReason to EtqaAssessor if missing
IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.EtqaAssessor') AND name = 'DeRegistrationReason')
BEGIN
    ALTER TABLE dbo.EtqaAssessor ADD DeRegistrationReason NVARCHAR(250) NULL;
END
GO

