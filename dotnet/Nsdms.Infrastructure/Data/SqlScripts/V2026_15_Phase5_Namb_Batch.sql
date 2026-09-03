-- ====================================================================================================
-- MERSETA NSDMS ENTERPRISE DDL MIGRATION: PHASE 5
-- Script: V2026_15_Phase5_Namb_Batch.sql
-- Description: Creates NambSubmissionBatch and links LearnerTradeTestApplication.
-- Conventions: Singular PascalCase, INT IDENTITY PK, audit columns, indexed FKs.
-- ====================================================================================================

-- 1. NambSubmissionBatch
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'NambSubmissionBatch' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE dbo.NambSubmissionBatch (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_NambSubmissionBatch PRIMARY KEY CLUSTERED,
        BatchReferenceNumber NVARCHAR(50) NOT NULL CONSTRAINT UQ_NambBatch_Ref UNIQUE,
        BatchDescription NVARCHAR(250) NOT NULL CONSTRAINT DF_NambBatch_Desc DEFAULT (''),
        SubmissionDate DATETIME2(7) NOT NULL CONSTRAINT DF_NambBatch_SubDate DEFAULT (SYSUTCDATETIME()),
        AdjudicationDate DATETIME2(7) NULL,
        Status NVARCHAR(50) NOT NULL CONSTRAINT DF_NambBatch_Status DEFAULT ('Draft'),
        TotalCandidates INT NOT NULL CONSTRAINT DF_NambBatch_Total DEFAULT (0),
        ApprovedCandidates INT NOT NULL CONSTRAINT DF_NambBatch_Approved DEFAULT (0),
        RejectedCandidates INT NOT NULL CONSTRAINT DF_NambBatch_Rejected DEFAULT (0),
        NambModeratorNotes NVARCHAR(MAX) NULL,
        DigitalSecuritySeal NVARCHAR(64) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_NambBatch_CreatedAt DEFAULT (SYSUTCDATETIME()),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_NambBatch_CreatedBy DEFAULT ('SYSTEM'),
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL
    );

    CREATE NONCLUSTERED INDEX IX_NambBatch_Status ON dbo.NambSubmissionBatch (Status);
    CREATE NONCLUSTERED INDEX IX_NambBatch_Ref ON dbo.NambSubmissionBatch (BatchReferenceNumber);
END
GO

-- 2. Link LearnerTradeTestApplication to NambSubmissionBatch
IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.LearnerTradeTestApplication') AND name = 'NambSubmissionBatchId')
BEGIN
    ALTER TABLE dbo.LearnerTradeTestApplication ADD NambSubmissionBatchId INT NULL;
    ALTER TABLE dbo.LearnerTradeTestApplication ADD CONSTRAINT FK_LearnerTradeTest_NambBatch FOREIGN KEY (NambSubmissionBatchId) REFERENCES dbo.NambSubmissionBatch (id) ON DELETE SET NULL;
    CREATE NONCLUSTERED INDEX IX_LearnerTradeTest_NambBatchId ON dbo.LearnerTradeTestApplication (NambSubmissionBatchId);
END
GO
