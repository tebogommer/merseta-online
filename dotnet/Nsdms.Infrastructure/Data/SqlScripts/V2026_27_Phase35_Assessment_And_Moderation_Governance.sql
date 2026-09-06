-- ==============================================================================================
-- V2026_27_Phase35_Assessment_And_Moderation_Governance.sql
-- MerSETA NSDMS 2.0: Summative Assessment, Moderation, Batching & Certification Statutory Governance Alignment
-- Reference: Signed Specification (18 Nov 2022) - Assessments and Moderation Use Case (MerSeta\NSDMS\LMS\ASM\12)
-- ==============================================================================================

-- 1. AssessmentBatch table
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'AssessmentBatch')
BEGIN
    CREATE TABLE dbo.AssessmentBatch (
        Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        BatchNumber NVARCHAR(50) NOT NULL,
        TrainingProviderId INT NOT NULL,
        QualificationTitle NVARCHAR(250) NOT NULL,
        SaqaQualificationId NVARCHAR(50) NULL,
        AssessmentStageCode NVARCHAR(50) NOT NULL DEFAULT 'Completion',
        SamplePercentage INT NOT NULL DEFAULT 10,
        TotalLearnersCount INT NOT NULL DEFAULT 0,
        SampledLearnersCount INT NOT NULL DEFAULT 0,
        InternalModerationReportDocumentRef NVARCHAR(500) NULL,
        LastInternalModerationDate DATETIME2 NULL,
        StatusCode NVARCHAR(50) NOT NULL DEFAULT 'Batched',
        ScheduledSiteVisitDate DATETIME2 NULL,
        IsSiteVisitRequired BIT NOT NULL DEFAULT 1,
        SiteVisitSchedulingComments NVARCHAR(1000) NULL,
        AssignedQaUserId NVARCHAR(100) NULL,
        ContactPersonId INT NULL,
        CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NULL,
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_AssessmentBatch_TrainingProvider FOREIGN KEY (TrainingProviderId) REFERENCES dbo.TrainingProvider(Id)
    );

    CREATE UNIQUE INDEX IX_AssessmentBatch_BatchNumber ON dbo.AssessmentBatch(BatchNumber);
    CREATE INDEX IX_AssessmentBatch_TrainingProviderId ON dbo.AssessmentBatch(TrainingProviderId);
    CREATE INDEX IX_AssessmentBatch_StatusCode ON dbo.AssessmentBatch(StatusCode);
END
GO

-- 2. SummativeAssessmentReport extensions
IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.SummativeAssessmentReport') AND name = 'AssessmentBatchId')
BEGIN
    ALTER TABLE dbo.SummativeAssessmentReport ADD AssessmentBatchId INT NULL;
    ALTER TABLE dbo.SummativeAssessmentReport ADD CONSTRAINT FK_SummativeAssessmentReport_AssessmentBatch FOREIGN KEY (AssessmentBatchId) REFERENCES dbo.AssessmentBatch(Id);
    CREATE INDEX IX_SummativeAssessmentReport_AssessmentBatchId ON dbo.SummativeAssessmentReport(AssessmentBatchId);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.SummativeAssessmentReport') AND name = 'AssessmentStageCode')
BEGIN
    ALTER TABLE dbo.SummativeAssessmentReport ADD AssessmentStageCode NVARCHAR(50) NOT NULL CONSTRAINT DF_SummativeAssessmentReport_Stage DEFAULT 'Completion';
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.SummativeAssessmentReport') AND name = 'IsFundedEmployer')
BEGIN
    ALTER TABLE dbo.SummativeAssessmentReport ADD IsFundedEmployer BIT NOT NULL CONSTRAINT DF_SummativeAssessmentReport_Funded DEFAULT 1;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.SummativeAssessmentReport') AND name = 'EarlyExitReasonCode')
BEGIN
    ALTER TABLE dbo.SummativeAssessmentReport ADD EarlyExitReasonCode NVARCHAR(50) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.SummativeAssessmentReport') AND name = 'CreditComplianceMet')
BEGIN
    ALTER TABLE dbo.SummativeAssessmentReport ADD CreditComplianceMet BIT NOT NULL CONSTRAINT DF_SummativeAssessmentReport_CreditCompliance DEFAULT 0;
END
GO

-- 3. SummativeAssessmentUnitStandard extensions
IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.SummativeAssessmentUnitStandard') AND name = 'UnitStandardTypeCode')
BEGIN
    ALTER TABLE dbo.SummativeAssessmentUnitStandard ADD UnitStandardTypeCode NVARCHAR(50) NOT NULL CONSTRAINT DF_SummativeAssessmentUS_Type DEFAULT 'Core';
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.SummativeAssessmentUnitStandard') AND name = 'IsMandatory')
BEGIN
    ALTER TABLE dbo.SummativeAssessmentUnitStandard ADD IsMandatory BIT NOT NULL CONSTRAINT DF_SummativeAssessmentUS_Mandatory DEFAULT 1;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.SummativeAssessmentUnitStandard') AND name = 'IsNonMandatoryElective')
BEGIN
    ALTER TABLE dbo.SummativeAssessmentUnitStandard ADD IsNonMandatoryElective BIT NOT NULL CONSTRAINT DF_SummativeAssessmentUS_NonMandatory DEFAULT 0;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.SummativeAssessmentUnitStandard') AND name = 'AssessorPersonId')
BEGIN
    ALTER TABLE dbo.SummativeAssessmentUnitStandard ADD AssessorPersonId INT NULL;
    ALTER TABLE dbo.SummativeAssessmentUnitStandard ADD CONSTRAINT FK_SummativeAssessmentUS_Assessor FOREIGN KEY (AssessorPersonId) REFERENCES dbo.Person(Id);
    CREATE INDEX IX_SummativeAssessmentUS_AssessorPersonId ON dbo.SummativeAssessmentUnitStandard(AssessorPersonId);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.SummativeAssessmentUnitStandard') AND name = 'InternalModeratorPersonId')
BEGIN
    ALTER TABLE dbo.SummativeAssessmentUnitStandard ADD InternalModeratorPersonId INT NULL;
    ALTER TABLE dbo.SummativeAssessmentUnitStandard ADD CONSTRAINT FK_SummativeAssessmentUS_Moderator FOREIGN KEY (InternalModeratorPersonId) REFERENCES dbo.Person(Id);
    CREATE INDEX IX_SummativeAssessmentUS_InternalModeratorPersonId ON dbo.SummativeAssessmentUnitStandard(InternalModeratorPersonId);
END
GO

-- 4. StatementOfResults extensions
IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.StatementOfResults') AND name = 'AchievementTypeCode')
BEGIN
    ALTER TABLE dbo.StatementOfResults ADD AchievementTypeCode NVARCHAR(50) NOT NULL CONSTRAINT DF_StatementOfResults_Achievement DEFAULT 'FullAchievement';
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.StatementOfResults') AND name = 'EarlyExitReasonCode')
BEGIN
    ALTER TABLE dbo.StatementOfResults ADD EarlyExitReasonCode NVARCHAR(50) NULL;
END
GO

-- 5. AssessmentBatchLearner table
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'AssessmentBatchLearner')
BEGIN
    CREATE TABLE dbo.AssessmentBatchLearner (
        Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        AssessmentBatchId INT NOT NULL,
        SummativeAssessmentReportId INT NOT NULL,
        IsSelectedInSample BIT NOT NULL DEFAULT 0,
        LearnerOutcomeStatus NVARCHAR(50) NOT NULL DEFAULT 'Pending',
        RejectionReasonCodes NVARCHAR(500) NULL,
        CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NULL,
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_AssessmentBatchLearner_Batch FOREIGN KEY (AssessmentBatchId) REFERENCES dbo.AssessmentBatch(Id) ON DELETE CASCADE,
        CONSTRAINT FK_AssessmentBatchLearner_Report FOREIGN KEY (SummativeAssessmentReportId) REFERENCES dbo.SummativeAssessmentReport(Id)
    );

    CREATE INDEX IX_AssessmentBatchLearner_BatchId ON dbo.AssessmentBatchLearner(AssessmentBatchId);
    CREATE INDEX IX_AssessmentBatchLearner_ReportId ON dbo.AssessmentBatchLearner(SummativeAssessmentReportId);
    CREATE INDEX IX_AssessmentBatchLearner_Sample ON dbo.AssessmentBatchLearner(IsSelectedInSample);
END
GO

-- 6. ModerationChecklistEtqTp043 table
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'ModerationChecklistEtqTp043')
BEGIN
    CREATE TABLE dbo.ModerationChecklistEtqTp043 (
        Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        AssessmentBatchId INT NOT NULL,
        ValidationBatchNumber NVARCHAR(50) NOT NULL,
        QualityAssurorUserId NVARCHAR(100) NOT NULL,
        DateOfModeration DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        StageOfModerationCode NVARCHAR(50) NOT NULL DEFAULT 'Completion',
        ValidationDecisionCode NVARCHAR(50) NOT NULL DEFAULT 'Upheld',
        PrimaryRejectionReasonCode NVARCHAR(100) NULL,
        VacsPrincipleViolatedCode NVARCHAR(50) NULL,
        RejectionRemarks NVARCHAR(MAX) NULL,
        RemedialActionRequired NVARCHAR(MAX) NULL,
        ReportDocumentReference NVARCHAR(500) NULL,
        TamperProofHashSha256 NVARCHAR(100) NOT NULL DEFAULT '',
        CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NULL,
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_ModerationChecklist_Batch FOREIGN KEY (AssessmentBatchId) REFERENCES dbo.AssessmentBatch(Id) ON DELETE CASCADE
    );

    CREATE INDEX IX_ModerationChecklist_BatchId ON dbo.ModerationChecklistEtqTp043(AssessmentBatchId);
    CREATE INDEX IX_ModerationChecklist_Decision ON dbo.ModerationChecklistEtqTp043(ValidationDecisionCode);
END
GO

-- 7. ModerationChecklistItem table
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'ModerationChecklistItem')
BEGIN
    CREATE TABLE dbo.ModerationChecklistItem (
        Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        ModerationChecklistEtqTp043Id INT NOT NULL,
        SectionNumber INT NOT NULL DEFAULT 1,
        CriteriaTitle NVARCHAR(250) NOT NULL,
        EvidenceRequirements NVARCHAR(500) NOT NULL,
        IsCompliant BIT NOT NULL DEFAULT 1,
        Comments NVARCHAR(1000) NULL,
        CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NULL,
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_ModerationChecklistItem_Checklist FOREIGN KEY (ModerationChecklistEtqTp043Id) REFERENCES dbo.ModerationChecklistEtqTp043(Id) ON DELETE CASCADE
    );

    CREATE INDEX IX_ModerationChecklistItem_ChecklistId ON dbo.ModerationChecklistItem(ModerationChecklistEtqTp043Id);
END
GO

-- 8. CertificatePrintingBatch table
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'CertificatePrintingBatch')
BEGIN
    CREATE TABLE dbo.CertificatePrintingBatch (
        Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        PrintingBatchNumber NVARCHAR(50) NOT NULL,
        BatchGeneratedDate DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        TotalCertificatesCount INT NOT NULL DEFAULT 0,
        ConsolidatedPdfDocumentRef NVARCHAR(500) NULL,
        ConsolidatedDistributionLettersPdfRef NVARCHAR(500) NULL,
        StatusCode NVARCHAR(50) NOT NULL DEFAULT 'QueuedForPrinting',
        CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NULL,
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL
    );

    CREATE UNIQUE INDEX IX_CertificatePrintingBatch_BatchNumber ON dbo.CertificatePrintingBatch(PrintingBatchNumber);
    CREATE INDEX IX_CertificatePrintingBatch_StatusCode ON dbo.CertificatePrintingBatch(StatusCode);
END
GO

-- 9. LearnerCertificate table
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
        CONSTRAINT FK_LearnerCertificate_CompanyLearner FOREIGN KEY (CompanyLearnerId) REFERENCES dbo.CompanyLearner(Id),
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

-- 10. DistributionLetter table
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'DistributionLetter')
BEGIN
    CREATE TABLE dbo.DistributionLetter (
        Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        CertificatePrintingBatchId INT NOT NULL,
        TrainingProviderId INT NOT NULL,
        ProviderAccreditationNumber NVARCHAR(50) NOT NULL,
        LetterReferenceNumber NVARCHAR(50) NOT NULL,
        DocumentReferenceUrl NVARCHAR(500) NULL,
        GeneratedDate DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NULL,
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_DistributionLetter_Batch FOREIGN KEY (CertificatePrintingBatchId) REFERENCES dbo.CertificatePrintingBatch(Id) ON DELETE CASCADE,
        CONSTRAINT FK_DistributionLetter_Provider FOREIGN KEY (TrainingProviderId) REFERENCES dbo.TrainingProvider(Id)
    );

    CREATE UNIQUE INDEX IX_DistributionLetter_LetterRefNumber ON dbo.DistributionLetter(LetterReferenceNumber);
    CREATE INDEX IX_DistributionLetter_BatchId ON dbo.DistributionLetter(CertificatePrintingBatchId);
    CREATE INDEX IX_DistributionLetter_ProviderId ON dbo.DistributionLetter(TrainingProviderId);
END
GO

-- 11. ScannedCertificateAttachment table
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'ScannedCertificateAttachment')
BEGIN
    CREATE TABLE dbo.ScannedCertificateAttachment (
        Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        LearnerCertificateId INT NOT NULL,
        PersonId INT NOT NULL,
        DocumentStorageKey NVARCHAR(500) NOT NULL,
        FileName NVARCHAR(255) NOT NULL,
        FileSizeBytes BIGINT NOT NULL DEFAULT 0,
        ScannedByUserId NVARCHAR(100) NOT NULL,
        ScannedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        OcrExtractedIdNumber NVARCHAR(50) NULL,
        OcrExtractedCertificateNumber NVARCHAR(50) NULL,
        IsVerifiedMatch BIT NOT NULL DEFAULT 0,
        CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NULL,
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_ScannedCert_Certificate FOREIGN KEY (LearnerCertificateId) REFERENCES dbo.LearnerCertificate(Id) ON DELETE CASCADE,
        CONSTRAINT FK_ScannedCert_Person FOREIGN KEY (PersonId) REFERENCES dbo.Person(Id)
    );

    CREATE INDEX IX_ScannedCert_CertificateId ON dbo.ScannedCertificateAttachment(LearnerCertificateId);
    CREATE INDEX IX_ScannedCert_PersonId ON dbo.ScannedCertificateAttachment(PersonId);
END
GO

-- 12. AssessmentCertificateDistributionEvent table
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'AssessmentCertificateDistributionEvent')
BEGIN
    CREATE TABLE dbo.AssessmentCertificateDistributionEvent (
        Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        LearnerCertificateId INT NOT NULL,
        DistributionMethodCode NVARCHAR(50) NOT NULL DEFAULT 'RegisteredMail',
        WaybillOrTrackingNumber NVARCHAR(100) NULL,
        DispatchedDate DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        RecipientName NVARCHAR(150) NULL,
        RecipientIdNumber NVARCHAR(50) NULL,
        ReceivedDate DATETIME2 NULL,
        DispatchNotes NVARCHAR(1000) NULL,
        CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NULL,
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_AssessmentCertDist_Certificate FOREIGN KEY (LearnerCertificateId) REFERENCES dbo.LearnerCertificate(Id) ON DELETE CASCADE
    );

    CREATE INDEX IX_AssessmentCertDist_CertificateId ON dbo.AssessmentCertificateDistributionEvent(LearnerCertificateId);
    CREATE INDEX IX_AssessmentCertDist_Method ON dbo.AssessmentCertificateDistributionEvent(DistributionMethodCode);
END
GO

-- 13. Sequence for Statutory Certificate Numbering
IF NOT EXISTS (SELECT 1 FROM sys.sequences WHERE name = 'Seq_StatutoryCertificateNumber')
BEGIN
    CREATE SEQUENCE dbo.Seq_StatutoryCertificateNumber
        AS INT
        START WITH 1
        INCREMENT BY 1
        MINVALUE 1
        MAXVALUE 999999
        CYCLE;
END
GO

-- 14. Composite & High-Frequency Performance Indexes
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_SummativeAssessmentReport_Batch_Status' AND object_id = OBJECT_ID('dbo.SummativeAssessmentReport'))
BEGIN
    CREATE INDEX IX_SummativeAssessmentReport_Batch_Status ON dbo.SummativeAssessmentReport(AssessmentBatchId, StatusCode);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_AssessmentBatchLearner_Batch_Report' AND object_id = OBJECT_ID('dbo.AssessmentBatchLearner'))
BEGIN
    CREATE UNIQUE INDEX IX_AssessmentBatchLearner_Batch_Report ON dbo.AssessmentBatchLearner(AssessmentBatchId, SummativeAssessmentReportId);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_LearnerCertificate_SummativeAssessmentReportId' AND object_id = OBJECT_ID('dbo.LearnerCertificate'))
BEGIN
    CREATE INDEX IX_LearnerCertificate_SummativeAssessmentReportId ON dbo.LearnerCertificate(SummativeAssessmentReportId);
END
GO
