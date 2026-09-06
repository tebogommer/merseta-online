-- =========================================================================================
-- Phase 33: Learner Registration Dual-Channel Architecture & Straight-Through Processing (STP)
-- Supports high-speed automated bulk intake (The ATM) alongside manual wizards (The Teller)
-- =========================================================================================

-- 1. Extend CompanyLearner with Dual-Channel metadata
IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('CompanyLearner') AND name = 'RegistrationChannel')
BEGIN
    ALTER TABLE CompanyLearner ADD RegistrationChannel NVARCHAR(50) NOT NULL DEFAULT 'ManualWizard';
END;

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('CompanyLearner') AND name = 'IngestionBatchId')
BEGIN
    ALTER TABLE CompanyLearner ADD IngestionBatchId INT NULL;
END;

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('CompanyLearner') AND name = 'StpApproved')
BEGIN
    ALTER TABLE CompanyLearner ADD StpApproved BIT NOT NULL DEFAULT 0;
END;

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('CompanyLearner') AND name = 'StpDecisionReason')
BEGIN
    ALTER TABLE CompanyLearner ADD StpDecisionReason NVARCHAR(500) NULL;
END;

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID('CompanyLearner') AND name = 'IX_CompanyLearner_RegistrationChannel')
BEGIN
    CREATE NONCLUSTERED INDEX IX_CompanyLearner_RegistrationChannel ON CompanyLearner(RegistrationChannel);
END;

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID('CompanyLearner') AND name = 'IX_CompanyLearner_IngestionBatchId')
BEGIN
    CREATE NONCLUSTERED INDEX IX_CompanyLearner_IngestionBatchId ON CompanyLearner(IngestionBatchId);
END;

-- 2. Create LearnerBulkBatch table
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'LearnerBulkBatch')
BEGIN
    CREATE TABLE LearnerBulkBatch (
        Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        BatchReference NVARCHAR(50) NOT NULL,
        OrganisationId INT NOT NULL,
        OriginalFileName NVARCHAR(250) NOT NULL,
        TotalRows INT NOT NULL DEFAULT 0,
        SuccessCount INT NOT NULL DEFAULT 0,
        ErrorCount INT NOT NULL DEFAULT 0,
        StpCount INT NOT NULL DEFAULT 0,
        Status NVARCHAR(50) NOT NULL DEFAULT 'Staged',
        DigitalSecuritySeal NVARCHAR(100) NULL,
        CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL,
        IsActive BIT NOT NULL DEFAULT 1,
        CONSTRAINT FK_LearnerBulkBatch_Organisation FOREIGN KEY (OrganisationId) REFERENCES Organisation(Id)
    );

    CREATE UNIQUE NONCLUSTERED INDEX IX_LearnerBulkBatch_BatchReference ON LearnerBulkBatch(BatchReference);
    CREATE NONCLUSTERED INDEX IX_LearnerBulkBatch_OrganisationId ON LearnerBulkBatch(OrganisationId);
    CREATE NONCLUSTERED INDEX IX_LearnerBulkBatch_Status ON LearnerBulkBatch(Status);
END;

-- 3. Create LearnerBulkBatchRow table
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'LearnerBulkBatchRow')
BEGIN
    CREATE TABLE LearnerBulkBatchRow (
        Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        LearnerBulkBatchId INT NOT NULL,
        RowIndex INT NOT NULL,
        FirstName NVARCHAR(100) NOT NULL,
        MiddleName NVARCHAR(100) NULL,
        LastName NVARCHAR(100) NOT NULL,
        RsaIdNumber NVARCHAR(50) NOT NULL,
        PassportNumber NVARCHAR(50) NULL,
        DateOfBirth DATETIME2 NOT NULL,
        GenderCode NVARCHAR(10) NOT NULL,
        EquityCode NVARCHAR(10) NOT NULL,
        EmailAddress NVARCHAR(150) NULL,
        PhoneNumber NVARCHAR(50) NULL,
        LearningProgrammeTypeCode NVARCHAR(50) NOT NULL DEFAULT '01',
        SaqaQualificationId NVARCHAR(50) NULL,
        QualificationTitle NVARCHAR(250) NULL,
        TradeCode NVARCHAR(50) NULL,
        LearnerSignatureDate DATETIME2 NOT NULL,
        CommencementDate DATETIME2 NULL,
        IsValid BIT NOT NULL DEFAULT 1,
        ValidationErrors NVARCHAR(MAX) NULL,
        IsStpEligible BIT NOT NULL DEFAULT 0,
        StpDecisionNotes NVARCHAR(500) NULL,
        Status NVARCHAR(50) NOT NULL DEFAULT 'Pending',
        CompanyLearnerId INT NULL,
        CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL,
        IsActive BIT NOT NULL DEFAULT 1,
        CONSTRAINT FK_LearnerBulkBatchRow_LearnerBulkBatch FOREIGN KEY (LearnerBulkBatchId) REFERENCES LearnerBulkBatch(Id) ON DELETE CASCADE,
        CONSTRAINT FK_LearnerBulkBatchRow_CompanyLearner FOREIGN KEY (CompanyLearnerId) REFERENCES CompanyLearner(Id) ON DELETE SET NULL
    );

    CREATE NONCLUSTERED INDEX IX_LearnerBulkBatchRow_LearnerBulkBatchId ON LearnerBulkBatchRow(LearnerBulkBatchId);
    CREATE NONCLUSTERED INDEX IX_LearnerBulkBatchRow_RsaIdNumber ON LearnerBulkBatchRow(RsaIdNumber);
    CREATE NONCLUSTERED INDEX IX_LearnerBulkBatchRow_Status ON LearnerBulkBatchRow(Status);
    CREATE NONCLUSTERED INDEX IX_LearnerBulkBatchRow_CompanyLearnerId ON LearnerBulkBatchRow(CompanyLearnerId);
END;
