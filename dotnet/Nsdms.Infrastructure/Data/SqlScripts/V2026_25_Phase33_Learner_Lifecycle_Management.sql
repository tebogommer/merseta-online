-- ====================================================================================================
-- MERSETA NSDMS ENTERPRISE DDL MIGRATION: PHASE 33 (LEARNER MANAGEMENT STATUTORY LIFECYCLE ALIGNMENT)
-- Script: V2026_25_Phase33_Learner_Lifecycle_Management.sql
-- Description: Conformance with signed use case 'Management of a Learner Use Case' (Ref: MerSeta\NSDMS\LMS\ASM\12 – Manage Learner – Use Case\13):
--              1. Two-Tiered Instate Status Alignment on dbo.LearnerEnrolment (and refreshed dbo.CompanyLearner view)
--              2. Create / Extend dbo.CompanyLearnerTransfer for SDP transfers, Bilateral Consent, and Workplace Approval
--              3. Create / Extend dbo.CompanyLearnerLostTime for training interruptions
--              4. Create / Extend dbo.CompanyLearnerTermination for Mutual vs One-Sided, 14-day SLA, Checklist 036, and ETQA Adjudication
--              5. Create dbo.CompanyLearnerExtension for Pre-registration and contract extensions
-- Conventions: Singular PascalCase, INT IDENTITY PK, audit columns, indexed FKs.
-- ====================================================================================================

-- 1. Extend dbo.LearnerEnrolment with Two-Tiered Instate Status Attributes
IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerEnrolment') AND name = 'InstateStatusCode')
BEGIN
    ALTER TABLE dbo.LearnerEnrolment ADD InstateStatusCode NVARCHAR(50) NOT NULL CONSTRAINT DF_LearnerEnrolment_InstateStatus DEFAULT ('Active');
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerEnrolment') AND name = 'InstateStatusDate')
BEGIN
    ALTER TABLE dbo.LearnerEnrolment ADD InstateStatusDate DATETIME2(7) NOT NULL CONSTRAINT DF_LearnerEnrolment_InstateStatusDate DEFAULT (SYSUTCDATETIME());
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID('dbo.LearnerEnrolment') AND name = 'IX_LearnerEnrolment_InstateStatusCode')
BEGIN
    CREATE NONCLUSTERED INDEX IX_LearnerEnrolment_InstateStatusCode ON dbo.LearnerEnrolment (InstateStatusCode);
END
GO

-- Ensure registration alignment columns exist on dbo.LearnerEnrolment
IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerEnrolment') AND name = 'LearnerSignatureDate')
BEGIN
    ALTER TABLE dbo.LearnerEnrolment ADD LearnerSignatureDate DATETIME2(7) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerEnrolment') AND name = 'SubmissionDate')
BEGIN
    ALTER TABLE dbo.LearnerEnrolment ADD SubmissionDate DATETIME2(7) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerEnrolment') AND name = 'SignatoryRoleTitle')
BEGIN
    ALTER TABLE dbo.LearnerEnrolment ADD SignatoryRoleTitle NVARCHAR(100) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerEnrolment') AND name = 'SignatoryPersonId')
BEGIN
    ALTER TABLE dbo.LearnerEnrolment ADD SignatoryPersonId INT NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerEnrolment') AND name = 'ProfessionalRegistrationNumber')
BEGIN
    ALTER TABLE dbo.LearnerEnrolment ADD ProfessionalRegistrationNumber NVARCHAR(100) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerEnrolment') AND name = 'WithdrawalReasonCode')
BEGIN
    ALTER TABLE dbo.LearnerEnrolment ADD WithdrawalReasonCode NVARCHAR(100) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerEnrolment') AND name = 'WithdrawalComments')
BEGIN
    ALTER TABLE dbo.LearnerEnrolment ADD WithdrawalComments NVARCHAR(MAX) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerEnrolment') AND name = 'IsNonEmployerEntity')
BEGIN
    ALTER TABLE dbo.LearnerEnrolment ADD IsNonEmployerEntity BIT NOT NULL CONSTRAINT DF_LearnerEnrolment_NonEmp DEFAULT (0);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerEnrolment') AND name = 'ExternalSetaId')
BEGIN
    ALTER TABLE dbo.LearnerEnrolment ADD ExternalSetaId NVARCHAR(50) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerEnrolment') AND name = 'HasPendingModifications')
BEGIN
    ALTER TABLE dbo.LearnerEnrolment ADD HasPendingModifications BIT NOT NULL CONSTRAINT DF_LearnerEnrolment_PendingMod DEFAULT (0);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerEnrolment') AND name = 'BursaryApplicationTypeCode')
BEGIN
    ALTER TABLE dbo.LearnerEnrolment ADD BursaryApplicationTypeCode NVARCHAR(50) NULL CONSTRAINT DF_LearnerEnrolment_BursaryApp DEFAULT ('New');
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerEnrolment') AND name = 'IsContinuation')
BEGIN
    ALTER TABLE dbo.LearnerEnrolment ADD IsContinuation BIT NOT NULL CONSTRAINT DF_LearnerEnrolment_IsCont DEFAULT (0);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerEnrolment') AND name = 'PreviousCompanyLearnerId')
BEGIN
    ALTER TABLE dbo.LearnerEnrolment ADD PreviousCompanyLearnerId INT NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerEnrolment') AND name = 'AcademicYear')
BEGIN
    ALTER TABLE dbo.LearnerEnrolment ADD AcademicYear INT NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerEnrolment') AND name = 'YearOfStudy')
BEGIN
    ALTER TABLE dbo.LearnerEnrolment ADD YearOfStudy INT NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerEnrolment') AND name = 'BursaryFundingTypeCode')
BEGIN
    ALTER TABLE dbo.LearnerEnrolment ADD BursaryFundingTypeCode NVARCHAR(50) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerEnrolment') AND name = 'InstitutionName')
BEGIN
    ALTER TABLE dbo.LearnerEnrolment ADD InstitutionName NVARCHAR(250) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerEnrolment') AND name = 'InstitutionTypeCode')
BEGIN
    ALTER TABLE dbo.LearnerEnrolment ADD InstitutionTypeCode NVARCHAR(50) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerEnrolment') AND name = 'EmploymentStatusCode')
BEGIN
    ALTER TABLE dbo.LearnerEnrolment ADD EmploymentStatusCode NVARCHAR(50) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerEnrolment') AND name = 'ContinuationAcademicResultsPassed')
BEGIN
    ALTER TABLE dbo.LearnerEnrolment ADD ContinuationAcademicResultsPassed BIT NULL;
END
GO

-- Refresh the CompanyLearner VIEW to propagate all new columns
IF EXISTS (SELECT 1 FROM sys.views WHERE name = 'CompanyLearner')
BEGIN
    EXEC sp_refreshview 'dbo.CompanyLearner';
END
GO

-- 2. Create or Update dbo.CompanyLearnerTransfer
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'CompanyLearnerTransfer')
BEGIN
    CREATE TABLE dbo.CompanyLearnerTransfer (
        Id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_CompanyLearnerTransfer PRIMARY KEY CLUSTERED,
        CompanyLearnerId INT NOT NULL,
        FromOrganisationId INT NULL,
        ToOrganisationId INT NULL,
        TransferReasonCode NVARCHAR(50) NOT NULL CONSTRAINT DF_Transfer_Reason DEFAULT ('MutualAgreement'),
        TransferDate DATETIME2(7) NOT NULL CONSTRAINT DF_Transfer_Date DEFAULT (SYSUTCDATETIME()),
        EffectiveDate DATETIME2(7) NOT NULL CONSTRAINT DF_Transfer_EffDate DEFAULT (SYSUTCDATETIME()),
        TransferStatusCode NVARCHAR(50) NOT NULL CONSTRAINT DF_Transfer_Status DEFAULT ('Pending'),
        ApprovalComments NVARCHAR(1000) NULL,
        ApprovedByUserId NVARCHAR(100) NULL,
        ApprovalDate DATETIME2(7) NULL,
        TransferScopeCode NVARCHAR(50) NOT NULL CONSTRAINT DF_Transfer_Scope DEFAULT ('EmployerToEmployer'),
        FromTrainingProviderId INT NULL,
        ToTrainingProviderId INT NULL,
        TargetWorkplaceApprovalId INT NULL,
        InitiatedByTypeCode NVARCHAR(50) NOT NULL CONSTRAINT DF_Transfer_InitiatedBy DEFAULT ('CurrentEmployer'),
        IsCurrentEmployerAgreed BIT NULL,
        CurrentEmployerSignoffDate DATETIME2(7) NULL,
        CurrentEmployerSignoffUserId NVARCHAR(100) NULL,
        IsFutureEmployerAgreed BIT NULL,
        FutureEmployerSignoffDate DATETIME2(7) NULL,
        FutureEmployerSignoffUserId NVARCHAR(100) NULL,
        IsLearnerAgreed BIT NULL,
        LearnerSignoffDate DATETIME2(7) NULL,
        DisagreementPromptedTermination BIT NOT NULL CONSTRAINT DF_Transfer_Disagreement DEFAULT (0),
        ReviewCommitteeMeetingId INT NULL,
        TransferFormDocumentId INT NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_Transfer_CreatedAt DEFAULT (SYSUTCDATETIME()),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_Transfer_CreatedBy DEFAULT ('SYSTEM'),
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL,
        RowVersion ROWVERSION NOT NULL,
        CONSTRAINT FK_CompanyLearnerTransfer_LearnerEnrolment FOREIGN KEY (CompanyLearnerId) REFERENCES dbo.LearnerEnrolment (Id) ON DELETE CASCADE
    );
END
ELSE
BEGIN
    -- Alter existing table if columns are missing
    IF EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTransfer') AND name = 'FromOrganisationId' AND is_nullable = 0)
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTransfer ALTER COLUMN FromOrganisationId INT NULL;
    END

    IF EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTransfer') AND name = 'ToOrganisationId' AND is_nullable = 0)
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTransfer ALTER COLUMN ToOrganisationId INT NULL;
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTransfer') AND name = 'TransferScopeCode')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTransfer ADD TransferScopeCode NVARCHAR(50) NOT NULL CONSTRAINT DF_Transfer_Scope DEFAULT ('EmployerToEmployer');
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTransfer') AND name = 'FromTrainingProviderId')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTransfer ADD FromTrainingProviderId INT NULL;
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTransfer') AND name = 'ToTrainingProviderId')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTransfer ADD ToTrainingProviderId INT NULL;
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTransfer') AND name = 'TargetWorkplaceApprovalId')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTransfer ADD TargetWorkplaceApprovalId INT NULL;
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTransfer') AND name = 'InitiatedByTypeCode')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTransfer ADD InitiatedByTypeCode NVARCHAR(50) NOT NULL CONSTRAINT DF_Transfer_InitiatedBy DEFAULT ('CurrentEmployer');
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTransfer') AND name = 'IsCurrentEmployerAgreed')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTransfer ADD IsCurrentEmployerAgreed BIT NULL;
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTransfer') AND name = 'CurrentEmployerSignoffDate')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTransfer ADD CurrentEmployerSignoffDate DATETIME2(7) NULL;
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTransfer') AND name = 'CurrentEmployerSignoffUserId')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTransfer ADD CurrentEmployerSignoffUserId NVARCHAR(100) NULL;
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTransfer') AND name = 'IsFutureEmployerAgreed')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTransfer ADD IsFutureEmployerAgreed BIT NULL;
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTransfer') AND name = 'FutureEmployerSignoffDate')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTransfer ADD FutureEmployerSignoffDate DATETIME2(7) NULL;
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTransfer') AND name = 'FutureEmployerSignoffUserId')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTransfer ADD FutureEmployerSignoffUserId NVARCHAR(100) NULL;
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTransfer') AND name = 'IsLearnerAgreed')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTransfer ADD IsLearnerAgreed BIT NULL;
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTransfer') AND name = 'LearnerSignoffDate')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTransfer ADD LearnerSignoffDate DATETIME2(7) NULL;
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTransfer') AND name = 'DisagreementPromptedTermination')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTransfer ADD DisagreementPromptedTermination BIT NOT NULL CONSTRAINT DF_Transfer_Disagreement DEFAULT (0);
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTransfer') AND name = 'ReviewCommitteeMeetingId')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTransfer ADD ReviewCommitteeMeetingId INT NULL;
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTransfer') AND name = 'TransferFormDocumentId')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTransfer ADD TransferFormDocumentId INT NULL;
    END
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTransfer') AND name = 'IX_CompanyLearnerTransfer_Learner')
BEGIN
    CREATE NONCLUSTERED INDEX IX_CompanyLearnerTransfer_Learner ON dbo.CompanyLearnerTransfer (CompanyLearnerId);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTransfer') AND name = 'IX_CompanyLearnerTransfer_ToProvider')
BEGIN
    CREATE NONCLUSTERED INDEX IX_CompanyLearnerTransfer_ToProvider ON dbo.CompanyLearnerTransfer (ToTrainingProviderId);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTransfer') AND name = 'IX_CompanyLearnerTransfer_TargetWpa')
BEGIN
    CREATE NONCLUSTERED INDEX IX_CompanyLearnerTransfer_TargetWpa ON dbo.CompanyLearnerTransfer (TargetWorkplaceApprovalId);
END
GO

-- 3. Create or Update dbo.CompanyLearnerLostTime
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'CompanyLearnerLostTime')
BEGIN
    CREATE TABLE dbo.CompanyLearnerLostTime (
        Id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_CompanyLearnerLostTime PRIMARY KEY CLUSTERED,
        CompanyLearnerId INT NOT NULL,
        LostTimeReasonCode NVARCHAR(50) NOT NULL CONSTRAINT DF_LostTime_Reason DEFAULT ('MedicalLeave'),
        StartDate DATETIME2(7) NOT NULL,
        EndDate DATETIME2(7) NOT NULL,
        DaysLost INT NOT NULL,
        OriginalContractEndDate DATETIME2(7) NOT NULL,
        RevisedContractEndDate DATETIME2(7) NOT NULL,
        LostTimeStatusCode NVARCHAR(50) NOT NULL CONSTRAINT DF_LostTime_Status DEFAULT ('Pending'),
        ApprovalComments NVARCHAR(1000) NULL,
        ApprovedByUserId NVARCHAR(100) NULL,
        ApprovalDate DATETIME2(7) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_LostTime_CreatedAt DEFAULT (SYSUTCDATETIME()),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_LostTime_CreatedBy DEFAULT ('SYSTEM'),
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL,
        RowVersion ROWVERSION NOT NULL,
        CONSTRAINT FK_CompanyLearnerLostTime_LearnerEnrolment FOREIGN KEY (CompanyLearnerId) REFERENCES dbo.LearnerEnrolment (Id) ON DELETE CASCADE
    );

    CREATE NONCLUSTERED INDEX IX_CompanyLearnerLostTime_Learner ON dbo.CompanyLearnerLostTime (CompanyLearnerId);
    CREATE NONCLUSTERED INDEX IX_CompanyLearnerLostTime_Status ON dbo.CompanyLearnerLostTime (LostTimeStatusCode);
END
GO

-- 4. Create or Update dbo.CompanyLearnerTermination
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'CompanyLearnerTermination')
BEGIN
    CREATE TABLE dbo.CompanyLearnerTermination (
        Id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_CompanyLearnerTermination PRIMARY KEY CLUSTERED,
        CompanyLearnerId INT NOT NULL,
        TerminationReasonCode NVARCHAR(50) NOT NULL CONSTRAINT DF_Termination_Reason DEFAULT ('MutualCancellation'),
        EffectiveDate DATETIME2(7) NOT NULL CONSTRAINT DF_Termination_EffDate DEFAULT (SYSUTCDATETIME()),
        DisputeLogged BIT NOT NULL CONSTRAINT DF_Termination_Dispute DEFAULT (0),
        UnionRepresentativeName NVARCHAR(150) NULL,
        SettlementNotes NVARCHAR(2000) NULL,
        TerminationStatusCode NVARCHAR(50) NOT NULL CONSTRAINT DF_Termination_Status DEFAULT ('Pending'),
        ApprovalComments NVARCHAR(1000) NULL,
        ApprovedByUserId NVARCHAR(100) NULL,
        ApprovalDate DATETIME2(7) NULL,
        TerminationTypeCode NVARCHAR(50) NOT NULL CONSTRAINT DF_Termination_Type DEFAULT ('Mutual'),
        Checklist036Completed BIT NOT NULL CONSTRAINT DF_Termination_Checklist036 DEFAULT (0),
        Checklist036DataJson NVARCHAR(MAX) NULL,
        Checklist036CompletedByUserId NVARCHAR(100) NULL,
        Checklist036CompletedDate DATETIME2(7) NULL,
        InvestigationConductedByUserId NVARCHAR(100) NULL,
        InvestigationStartDate DATETIME2(7) NULL,
        InvestigationDueDate DATETIME2(7) NULL,
        InvestigationCompletedDate DATETIME2(7) NULL,
        InvestigationOutcomeSummary NVARCHAR(MAX) NULL,
        IsArplRecommended BIT NOT NULL CONSTRAINT DF_Termination_Arpl DEFAULT (0),
        IsTransferRecommended BIT NOT NULL CONSTRAINT DF_Termination_Transfer DEFAULT (0),
        ReviewCommitteeMeetingId INT NULL,
        RecommendedToCommitteeByUserId NVARCHAR(100) NULL,
        RecommendationToCommitteeDate DATETIME2(7) NULL,
        CommitteeDecisionCode NVARCHAR(50) NULL,
        CommitteeDecisionDate DATETIME2(7) NULL,
        CommitteeDecisionNotes NVARCHAR(MAX) NULL,
        DecisionLetterDocumentId INT NULL,
        RejectionLetterDocumentId INT NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_Termination_CreatedAt DEFAULT (SYSUTCDATETIME()),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_Termination_CreatedBy DEFAULT ('SYSTEM'),
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL,
        RowVersion ROWVERSION NOT NULL,
        CONSTRAINT FK_CompanyLearnerTermination_LearnerEnrolment FOREIGN KEY (CompanyLearnerId) REFERENCES dbo.LearnerEnrolment (Id) ON DELETE CASCADE
    );
END
ELSE
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTermination') AND name = 'TerminationTypeCode')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTermination ADD TerminationTypeCode NVARCHAR(50) NOT NULL CONSTRAINT DF_Termination_Type DEFAULT ('Mutual');
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTermination') AND name = 'Checklist036Completed')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTermination ADD Checklist036Completed BIT NOT NULL CONSTRAINT DF_Termination_Checklist036 DEFAULT (0);
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTermination') AND name = 'Checklist036DataJson')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTermination ADD Checklist036DataJson NVARCHAR(MAX) NULL;
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTermination') AND name = 'Checklist036CompletedByUserId')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTermination ADD Checklist036CompletedByUserId NVARCHAR(100) NULL;
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTermination') AND name = 'Checklist036CompletedDate')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTermination ADD Checklist036CompletedDate DATETIME2(7) NULL;
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTermination') AND name = 'InvestigationConductedByUserId')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTermination ADD InvestigationConductedByUserId NVARCHAR(100) NULL;
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTermination') AND name = 'InvestigationStartDate')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTermination ADD InvestigationStartDate DATETIME2(7) NULL;
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTermination') AND name = 'InvestigationDueDate')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTermination ADD InvestigationDueDate DATETIME2(7) NULL;
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTermination') AND name = 'InvestigationCompletedDate')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTermination ADD InvestigationCompletedDate DATETIME2(7) NULL;
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTermination') AND name = 'InvestigationOutcomeSummary')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTermination ADD InvestigationOutcomeSummary NVARCHAR(MAX) NULL;
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTermination') AND name = 'IsArplRecommended')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTermination ADD IsArplRecommended BIT NOT NULL CONSTRAINT DF_Termination_Arpl DEFAULT (0);
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTermination') AND name = 'IsTransferRecommended')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTermination ADD IsTransferRecommended BIT NOT NULL CONSTRAINT DF_Termination_Transfer DEFAULT (0);
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTermination') AND name = 'ReviewCommitteeMeetingId')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTermination ADD ReviewCommitteeMeetingId INT NULL;
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTermination') AND name = 'RecommendedToCommitteeByUserId')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTermination ADD RecommendedToCommitteeByUserId NVARCHAR(100) NULL;
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTermination') AND name = 'RecommendationToCommitteeDate')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTermination ADD RecommendationToCommitteeDate DATETIME2(7) NULL;
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTermination') AND name = 'CommitteeDecisionCode')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTermination ADD CommitteeDecisionCode NVARCHAR(50) NULL;
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTermination') AND name = 'CommitteeDecisionDate')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTermination ADD CommitteeDecisionDate DATETIME2(7) NULL;
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTermination') AND name = 'CommitteeDecisionNotes')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTermination ADD CommitteeDecisionNotes NVARCHAR(MAX) NULL;
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTermination') AND name = 'DecisionLetterDocumentId')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTermination ADD DecisionLetterDocumentId INT NULL;
    END

    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTermination') AND name = 'RejectionLetterDocumentId')
    BEGIN
        ALTER TABLE dbo.CompanyLearnerTermination ADD RejectionLetterDocumentId INT NULL;
    END
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTermination') AND name = 'IX_CompanyLearnerTermination_Learner')
BEGIN
    CREATE NONCLUSTERED INDEX IX_CompanyLearnerTermination_Learner ON dbo.CompanyLearnerTermination (CompanyLearnerId);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID('dbo.CompanyLearnerTermination') AND name = 'IX_CompanyLearnerTermination_TypeCode')
BEGIN
    CREATE NONCLUSTERED INDEX IX_CompanyLearnerTermination_TypeCode ON dbo.CompanyLearnerTermination (TerminationTypeCode);
END
GO

-- 5. Create dbo.CompanyLearnerExtension Table
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'CompanyLearnerExtension')
BEGIN
    CREATE TABLE dbo.CompanyLearnerExtension (
        Id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_CompanyLearnerExtension PRIMARY KEY CLUSTERED,
        CompanyLearnerId INT NOT NULL,
        ExtensionTypeCode NVARCHAR(50) NOT NULL CONSTRAINT DF_Extension_Type DEFAULT ('PreRegistration'),
        ExtensionReasonCode NVARCHAR(50) NOT NULL CONSTRAINT DF_Extension_Reason DEFAULT ('PendingDocumentation'),
        JustificationComments NVARCHAR(MAX) NOT NULL,
        OriginalExpiryDate DATETIME2(7) NULL,
        RequestedExpiryDate DATETIME2(7) NOT NULL,
        ApprovedExpiryDate DATETIME2(7) NULL,
        AddendumDocumentId INT NULL,
        ExtensionStatusCode NVARCHAR(50) NOT NULL CONSTRAINT DF_Extension_Status DEFAULT ('Pending'),
        ApprovedByUserId NVARCHAR(100) NULL,
        ApprovalDate DATETIME2(7) NULL,
        ApprovalComments NVARCHAR(MAX) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_Extension_CreatedAt DEFAULT (SYSUTCDATETIME()),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_Extension_CreatedBy DEFAULT ('SYSTEM'),
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL,
        RowVersion ROWVERSION NOT NULL,
        CONSTRAINT FK_CompanyLearnerExtension_LearnerEnrolment FOREIGN KEY (CompanyLearnerId) REFERENCES dbo.LearnerEnrolment (Id) ON DELETE CASCADE
    );

    CREATE NONCLUSTERED INDEX IX_CompanyLearnerExtension_LearnerId ON dbo.CompanyLearnerExtension (CompanyLearnerId);
    CREATE NONCLUSTERED INDEX IX_CompanyLearnerExtension_Status ON dbo.CompanyLearnerExtension (ExtensionStatusCode);
    CREATE NONCLUSTERED INDEX IX_CompanyLearnerExtension_Type ON dbo.CompanyLearnerExtension (ExtensionTypeCode);
END
GO
