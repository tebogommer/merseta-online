-- ==============================================================================
-- Script: V2026_12_Add_Statutory_Batch_Tables.sql
-- Description: Creates persistent tables for DHET SETMIS and SAQA NLRD statutory
--              batch execution, individual extract files, audit trails, and security seals.
-- Standard: Singular PascalCase, Identity PK, Audit columns, Foreign Key Indexes.
-- ==============================================================================

SET NOCOUNT ON;

-- 1. Table: StatutorySubmissionBatch
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = N'StatutorySubmissionBatch')
BEGIN
    CREATE TABLE [dbo].[StatutorySubmissionBatch] (
        [Id] INT IDENTITY(1,1) NOT NULL,
        [BatchType] NVARCHAR(20) NOT NULL,              -- 'SETMIS' or 'NLRD'
        [BatchNumber] NVARCHAR(100) NOT NULL,           -- e.g. 'SETMIS-2026-Q1-001'
        [SubmissionYear] INT NOT NULL,                  -- 2026
        [SubmissionQuarter] INT NULL,                   -- 1, 2, 3, 4
        [ExtractionDate] DATETIME2(7) NOT NULL,
        [TotalRecords] INT NOT NULL DEFAULT(0),
        [FatalErrorsCount] INT NOT NULL DEFAULT(0),
        [WarningsCount] INT NOT NULL DEFAULT(0),
        [StatusCode] NVARCHAR(50) NOT NULL DEFAULT('Extracted'),
        [DigitalSecuritySeal] NVARCHAR(128) NULL,       -- SHA-256 Checksum
        [ArchiveFileName] NVARCHAR(255) NULL,           -- e.g. 'SETMIS_MERS_0006_20260902.zip'
        [ArchiveStorageUri] NVARCHAR(500) NULL,
        [Comments] NVARCHAR(MAX) NULL,
        [CreatedAt] DATETIME2(7) NOT NULL DEFAULT(SYSUTCDATETIME()),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2(7) NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT [PK_StatutorySubmissionBatch] PRIMARY KEY CLUSTERED ([Id] ASC)
    );

    CREATE UNIQUE NONCLUSTERED INDEX [IX_StatutorySubmissionBatch_BatchNumber]
        ON [dbo].[StatutorySubmissionBatch]([BatchNumber] ASC);

    CREATE NONCLUSTERED INDEX [IX_StatutorySubmissionBatch_BatchType]
        ON [dbo].[StatutorySubmissionBatch]([BatchType] ASC);

    CREATE NONCLUSTERED INDEX [IX_StatutorySubmissionBatch_SubmissionYear]
        ON [dbo].[StatutorySubmissionBatch]([SubmissionYear] ASC);

    CREATE NONCLUSTERED INDEX [IX_StatutorySubmissionBatch_StatusCode]
        ON [dbo].[StatutorySubmissionBatch]([StatusCode] ASC);

    CREATE NONCLUSTERED INDEX [IX_StatutorySubmissionBatch_ExtractionDate]
        ON [dbo].[StatutorySubmissionBatch]([ExtractionDate] ASC);

    PRINT 'Created table [dbo].[StatutorySubmissionBatch].';
END
ELSE
BEGIN
    PRINT 'Table [dbo].[StatutorySubmissionBatch] already exists.';
END
GO

-- 2. Table: StatutoryBatchFile
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = N'StatutoryBatchFile')
BEGIN
    CREATE TABLE [dbo].[StatutoryBatchFile] (
        [Id] INT IDENTITY(1,1) NOT NULL,
        [StatutorySubmissionBatchId] INT NOT NULL,
        [FileCode] NVARCHAR(10) NOT NULL,               -- '100', '200', '21', '25', etc.
        [FileTitle] NVARCHAR(150) NOT NULL,             -- 'Provider File (100)'
        [FileName] NVARCHAR(255) NOT NULL,              -- 'MERS_0006_100_v001_20260902.dat'
        [RecordCount] INT NOT NULL DEFAULT(0),
        [FileSizeBytes] BIGINT NOT NULL DEFAULT(0),
        [RecordLength] INT NOT NULL DEFAULT(0),
        [ChecksumSha256] NVARCHAR(128) NULL,
        [FileContent] NVARCHAR(MAX) NULL,
        [StatusCode] NVARCHAR(50) NOT NULL DEFAULT('Extracted'),
        [ValidationErrorsCount] INT NOT NULL DEFAULT(0),
        [CreatedAt] DATETIME2(7) NOT NULL DEFAULT(SYSUTCDATETIME()),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2(7) NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT [PK_StatutoryBatchFile] PRIMARY KEY CLUSTERED ([Id] ASC),
        CONSTRAINT [FK_StatutoryBatchFile_StatutorySubmissionBatch] FOREIGN KEY ([StatutorySubmissionBatchId])
            REFERENCES [dbo].[StatutorySubmissionBatch]([Id]) ON DELETE CASCADE
    );

    CREATE NONCLUSTERED INDEX [IX_StatutoryBatchFile_StatutorySubmissionBatchId]
        ON [dbo].[StatutoryBatchFile]([StatutorySubmissionBatchId] ASC);

    CREATE NONCLUSTERED INDEX [IX_StatutoryBatchFile_FileCode]
        ON [dbo].[StatutoryBatchFile]([FileCode] ASC);

    CREATE NONCLUSTERED INDEX [IX_StatutoryBatchFile_StatusCode]
        ON [dbo].[StatutoryBatchFile]([StatusCode] ASC);

    PRINT 'Created table [dbo].[StatutoryBatchFile].';
END
ELSE
BEGIN
    PRINT 'Table [dbo].[StatutoryBatchFile] already exists.';
END
GO
