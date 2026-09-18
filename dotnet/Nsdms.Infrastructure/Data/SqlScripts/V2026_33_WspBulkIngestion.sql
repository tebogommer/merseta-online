-- ==========================================================================================
-- Migration: V2026_33_WspBulkIngestion.sql
-- Description: Universal Bulk Spreadsheet Ingestion Engine for WSP / ATR (Option C: Modern Hybrid)
-- ==========================================================================================

-- 1. Table: [dbo].[WspBulkImportBatch]
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'WspBulkImportBatch' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE [dbo].[WspBulkImportBatch] (
        [Id] INT IDENTITY(1,1) NOT NULL,
        [BatchGuid] UNIQUEIDENTIFIER NOT NULL CONSTRAINT [DF_WspBulkImportBatch_Guid] DEFAULT (NEWID()),
        [BatchReference] NVARCHAR(50) NOT NULL,
        [WspSubmissionId] INT NOT NULL,
        [OrganisationId] INT NOT NULL,
        [SchemeYear] INT NOT NULL,
        [ReportType] NVARCHAR(10) NOT NULL CONSTRAINT [DF_WspBulkImportBatch_ReportType] DEFAULT ('WSP'),
        [OriginalFileName] NVARCHAR(250) NOT NULL,
        [FileSizeBytes] BIGINT NOT NULL CONSTRAINT [DF_WspBulkImportBatch_FileSize] DEFAULT (0),
        [ContentHashSha256] NVARCHAR(64) NOT NULL,
        [BatchStatus] NVARCHAR(50) NOT NULL CONSTRAINT [DF_WspBulkImportBatch_Status] DEFAULT ('Queued'),
        
        [TotalRowCount] INT NOT NULL CONSTRAINT [DF_WspBulkImportBatch_TotalRows] DEFAULT (0),
        [ValidRowCount] INT NOT NULL CONSTRAINT [DF_WspBulkImportBatch_ValidRows] DEFAULT (0),
        [ErrorRowCount] INT NOT NULL CONSTRAINT [DF_WspBulkImportBatch_ErrorRows] DEFAULT (0),
        [CommittedRowCount] INT NOT NULL CONSTRAINT [DF_WspBulkImportBatch_CommittedRows] DEFAULT (0),
        [TotalEstimatedCostRollup] DECIMAL(18,2) NOT NULL CONSTRAINT [DF_WspBulkImportBatch_TotalCost] DEFAULT (0),
        [TotalBeneficiariesRollup] INT NOT NULL CONSTRAINT [DF_WspBulkImportBatch_TotalBeneficiaries] DEFAULT (0),
        
        [AllowPartialCommit] BIT NOT NULL CONSTRAINT [DF_WspBulkImportBatch_Partial] DEFAULT (0),
        [ErrorSummaryJson] NVARCHAR(MAX) NULL,
        [ProcessingDurationMs] INT NULL,
        
        [CreatedAt] DATETIME2(3) NOT NULL CONSTRAINT [DF_WspBulkImportBatch_CreatedAt] DEFAULT (SYSUTCDATETIME()),
        [CreatedBy] NVARCHAR(100) NULL CONSTRAINT [DF_WspBulkImportBatch_CreatedBy] DEFAULT ('SYSTEM'),
        [ModifiedAt] DATETIME2(3) NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        
        CONSTRAINT [PK_WspBulkImportBatch] PRIMARY KEY CLUSTERED ([Id] ASC),
        CONSTRAINT [FK_WspBulkImportBatch_WspSubmission] FOREIGN KEY ([WspSubmissionId]) 
            REFERENCES [dbo].[WspSubmission] ([Id]) ON DELETE CASCADE,
        CONSTRAINT [FK_WspBulkImportBatch_Organisation] FOREIGN KEY ([OrganisationId]) 
            REFERENCES [dbo].[Organisation] ([Id])
    );

    CREATE UNIQUE NONCLUSTERED INDEX [IX_WspBulkImportBatch_BatchGuid] ON [dbo].[WspBulkImportBatch] ([BatchGuid]);
    CREATE NONCLUSTERED INDEX [IX_WspBulkImportBatch_WspSubmissionId] ON [dbo].[WspBulkImportBatch] ([WspSubmissionId]);
    CREATE NONCLUSTERED INDEX [IX_WspBulkImportBatch_OrganisationId] ON [dbo].[WspBulkImportBatch] ([OrganisationId]);
    CREATE NONCLUSTERED INDEX [IX_WspBulkImportBatch_Status] ON [dbo].[WspBulkImportBatch] ([BatchStatus]);
END
GO

-- 2. Table: [dbo].[WspBulkImportStaging]
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'WspBulkImportStaging' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE [dbo].[WspBulkImportStaging] (
        [Id] BIGINT IDENTITY(1,1) NOT NULL,
        [BatchId] INT NOT NULL,
        [RowIndex] INT NOT NULL,
        
        -- Raw String Buffers
        [RawIdType] NVARCHAR(50) NULL,
        [RawIdNumber] NVARCHAR(50) NULL,
        [RawFirstName] NVARCHAR(100) NULL,
        [RawLastName] NVARCHAR(100) NULL,
        [RawGenderCode] NVARCHAR(50) NULL,
        [RawEquityCode] NVARCHAR(50) NULL,
        [RawNationalityCode] NVARCHAR(50) NULL,
        [RawOfoCode] NVARCHAR(50) NULL,
        [RawSpecialisationCode] NVARCHAR(50) NULL,
        [RawInterventionTypeCode] NVARCHAR(50) NULL,
        [RawQualificationCode] NVARCHAR(50) NULL,
        [RawSkillsProgramCode] NVARCHAR(50) NULL,
        [RawSkillsSetCode] NVARCHAR(50) NULL,
        [RawEmploymentTypeCode] NVARCHAR(50) NULL,
        [RawProviderTypeCode] NVARCHAR(50) NULL,
        [RawTrainingDeliveryMethodCode] NVARCHAR(50) NULL,
        [RawMunicipalityCode] NVARCHAR(50) NULL,
        [RawStartDate] NVARCHAR(50) NULL,
        [RawEndDate] NVARCHAR(50) NULL,
        [RawEstimatedCost] NVARCHAR(50) NULL,
        [RawBeneficiaryCount] NVARCHAR(50) NULL,
        
        -- Resolved Typed Fields
        [ResolvedOfoCodeId] INT NULL,
        [ResolvedQualificationId] INT NULL,
        [ResolvedInterventionTypeId] INT NULL,
        [ResolvedNqfLevel] INT NULL,
        [ParsedEstimatedCost] DECIMAL(18,2) NULL,
        [ParsedBeneficiaryCount] INT NULL,
        [ParsedStartDate] DATETIME2(3) NULL,
        [ParsedEndDate] DATETIME2(3) NULL,
        
        -- Validation States
        [IsValid] BIT NOT NULL CONSTRAINT [DF_WspBulkImportStaging_IsValid] DEFAULT (1),
        [IsCommitted] BIT NOT NULL CONSTRAINT [DF_WspBulkImportStaging_IsCommitted] DEFAULT (0),
        [ValidationErrorCode] NVARCHAR(100) NULL,
        [ValidationErrorDetails] NVARCHAR(MAX) NULL,
        
        [CreatedAt] DATETIME2(3) NOT NULL CONSTRAINT [DF_WspBulkImportStaging_CreatedAt] DEFAULT (SYSUTCDATETIME()),
        [CreatedBy] NVARCHAR(100) NULL CONSTRAINT [DF_WspBulkImportStaging_CreatedBy] DEFAULT ('SYSTEM'),
        [ModifiedAt] DATETIME2(3) NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        
        CONSTRAINT [PK_WspBulkImportStaging] PRIMARY KEY CLUSTERED ([Id] ASC),
        CONSTRAINT [FK_WspBulkImportStaging_Batch] FOREIGN KEY ([BatchId]) 
            REFERENCES [dbo].[WspBulkImportBatch] ([Id]) ON DELETE CASCADE
    );

    CREATE NONCLUSTERED INDEX [IX_WspBulkImportStaging_BatchId_IsValid] 
        ON [dbo].[WspBulkImportStaging] ([BatchId], [IsValid]) INCLUDE ([RowIndex], [IsCommitted]);
    CREATE NONCLUSTERED INDEX [IX_WspBulkImportStaging_BatchId_Committed] 
        ON [dbo].[WspBulkImportStaging] ([BatchId], [IsCommitted]);
END
GO

-- 3. Stored Procedure: [dbo].[usp_ValidateWspBulkImportBatch]
CREATE OR ALTER PROCEDURE [dbo].[usp_ValidateWspBulkImportBatch]
    @BatchId INT
AS
BEGIN
    SET NOCOUNT ON;
    SET XACT_ABORT ON;

    DECLARE @SchemeYear INT, @ReportType NVARCHAR(10);
    SELECT @SchemeYear = SchemeYear, @ReportType = ReportType 
    FROM [dbo].[WspBulkImportBatch] WHERE Id = @BatchId;

    IF @SchemeYear IS NULL RETURN;

    -- Reset state
    UPDATE [dbo].[WspBulkImportStaging]
    SET IsValid = 1,
        ValidationErrorCode = NULL,
        ValidationErrorDetails = NULL
    WHERE BatchId = @BatchId;

    -- Step 1: Parse Numerics and Dates
    UPDATE s
    SET s.ParsedEstimatedCost = TRY_CAST(REPLACE(REPLACE(REPLACE(s.RawEstimatedCost, ' ', ''), ',', '.'), 'R', '') AS DECIMAL(18,2)),
        s.ParsedBeneficiaryCount = ISNULL(TRY_CAST(s.RawBeneficiaryCount AS INT), 1),
        s.ParsedStartDate = TRY_CONVERT(DATETIME2(3), s.RawStartDate, 120),
        s.ParsedEndDate = TRY_CONVERT(DATETIME2(3), s.RawEndDate, 120)
    FROM [dbo].[WspBulkImportStaging] s
    WHERE s.BatchId = @BatchId;

    -- Step 2: Validate OFO Code Against lookup.OfoCodeType
    UPDATE s
    SET s.IsValid = 0,
        s.ValidationErrorCode = 'INVALID_OFO_CODE',
        s.ValidationErrorDetails = ISNULL(s.ValidationErrorDetails + ' | ', '') + 'OFO Code [' + ISNULL(s.RawOfoCode, 'EMPTY') + '] does not exist or is inactive'
    FROM [dbo].[WspBulkImportStaging] s
    LEFT JOIN [lookup].[OfoCodeType] ofo ON ofo.Code = s.RawOfoCode AND ofo.Active = 1
    WHERE s.BatchId = @BatchId AND (s.RawOfoCode IS NULL OR ofo.Code IS NULL);

    -- Step 3: Validate Estimated Cost
    UPDATE s
    SET s.IsValid = 0,
        s.ValidationErrorCode = 'INVALID_COST',
        s.ValidationErrorDetails = ISNULL(s.ValidationErrorDetails + ' | ', '') + 'Estimated cost must be a positive number'
    FROM [dbo].[WspBulkImportStaging] s
    WHERE s.BatchId = @BatchId AND (s.ParsedEstimatedCost IS NULL OR s.ParsedEstimatedCost < 0);

    -- Step 4: Validate RSA ID if provided
    UPDATE s
    SET s.IsValid = 0,
        s.ValidationErrorCode = 'INVALID_RSA_ID',
        s.ValidationErrorDetails = ISNULL(s.ValidationErrorDetails + ' | ', '') + 'RSA National ID must contain exactly 13 numeric digits'
    FROM [dbo].[WspBulkImportStaging] s
    WHERE s.BatchId = @BatchId 
      AND (s.RawIdType = 'RSA_ID' OR s.RawIdType = 'NationalId' OR (s.RawIdNumber IS NOT NULL AND LEN(s.RawIdNumber) > 0))
      AND (LEN(LTRIM(RTRIM(s.RawIdNumber))) <> 13 OR LTRIM(RTRIM(s.RawIdNumber)) LIKE '%[^0-9]%');

    -- Step 5: Rollup Metrics into Batch Record
    DECLARE @Total INT, @Valid INT, @Errors INT, @TotalCost DECIMAL(18,2), @TotalBeneficiaries INT;
    SELECT @Total = COUNT(*),
           @Valid = SUM(CASE WHEN IsValid = 1 THEN 1 ELSE 0 END),
           @Errors = SUM(CASE WHEN IsValid = 0 THEN 1 ELSE 0 END),
           @TotalCost = SUM(CASE WHEN IsValid = 1 THEN ISNULL(ParsedEstimatedCost, 0) ELSE 0 END),
           @TotalBeneficiaries = SUM(CASE WHEN IsValid = 1 THEN ISNULL(ParsedBeneficiaryCount, 1) ELSE 0 END)
    FROM [dbo].[WspBulkImportStaging]
    WHERE BatchId = @BatchId;

    UPDATE [dbo].[WspBulkImportBatch]
    SET TotalRowCount = @Total,
        ValidRowCount = @Valid,
        ErrorRowCount = @Errors,
        TotalEstimatedCostRollup = ISNULL(@TotalCost, 0),
        TotalBeneficiariesRollup = ISNULL(@TotalBeneficiaries, 0),
        BatchStatus = CASE WHEN @Errors = 0 THEN 'Validated' ELSE 'ValidationFailed' END,
        ModifiedAt = SYSUTCDATETIME()
    WHERE Id = @BatchId;

    SELECT @Total AS TotalRows, @Valid AS ValidRows, @Errors AS ErrorRows, @TotalCost AS TotalCost;
END
GO

-- 4. Stored Procedure: [dbo].[usp_CommitWspBulkImportBatch]
CREATE OR ALTER PROCEDURE [dbo].[usp_CommitWspBulkImportBatch]
    @BatchId INT,
    @DiscardExceptions BIT = 0,
    @Username NVARCHAR(100) = 'SYSTEM'
AS
BEGIN
    SET NOCOUNT ON;
    SET XACT_ABORT ON;

    DECLARE @WspSubmissionId INT, @ReportType NVARCHAR(10), @Status NVARCHAR(50), @ErrorCount INT;
    SELECT @WspSubmissionId = WspSubmissionId, @ReportType = ReportType, @Status = BatchStatus, @ErrorCount = ErrorRowCount
    FROM [dbo].[WspBulkImportBatch] WHERE Id = @BatchId;

    IF @WspSubmissionId IS NULL
    BEGIN
        RAISERROR('Batch not found', 16, 1);
        RETURN;
    END

    IF @ErrorCount > 0 AND @DiscardExceptions = 0
    BEGIN
        RAISERROR('Cannot commit batch with unresolved validation exceptions without explicit discard confirmation.', 16, 1);
        RETURN;
    END

    BEGIN TRANSACTION;

    -- Insert valid staging rows into WspTrainingPlan
    INSERT INTO [dbo].[WspTrainingPlan] (
        [WspSubmissionId],
        [ProgrammeTypeCode],
        [NqfLevel],
        [BeneficiaryCount],
        [EstimatedCost],
        [CreatedAt],
        [CreatedBy]
    )
    SELECT 
        @WspSubmissionId,
        ISNULL(s.RawInterventionTypeCode, 'SkillsProgramme'),
        ISNULL(s.ResolvedNqfLevel, 4),
        ISNULL(s.ParsedBeneficiaryCount, 1),
        ISNULL(s.ParsedEstimatedCost, 0),
        SYSUTCDATETIME(),
        @Username
    FROM [dbo].[WspBulkImportStaging] s
    WHERE s.BatchId = @BatchId AND s.IsValid = 1 AND s.IsCommitted = 0;

    DECLARE @CommittedCount INT = @@ROWCOUNT;

    -- Mark staging rows as committed
    UPDATE [dbo].[WspBulkImportStaging]
    SET IsCommitted = 1,
        ModifiedAt = SYSUTCDATETIME(),
        ModifiedBy = @Username
    WHERE BatchId = @BatchId AND IsValid = 1;

    -- If discard exceptions is confirmed, remove invalid rows
    IF @DiscardExceptions = 1 AND @ErrorCount > 0
    BEGIN
        DELETE FROM [dbo].[WspBulkImportStaging]
        WHERE BatchId = @BatchId AND IsValid = 0;
    END

    -- Update batch status
    UPDATE [dbo].[WspBulkImportBatch]
    SET CommittedRowCount = CommittedRowCount + @CommittedCount,
        BatchStatus = 'Committed',
        ModifiedAt = SYSUTCDATETIME(),
        ModifiedBy = @Username
    WHERE Id = @BatchId;

    COMMIT TRANSACTION;

    SELECT @CommittedCount AS CommittedRows;
END
GO

-- 5. Stored Procedure: [dbo].[usp_PurgeExpiredWspStaging]
CREATE OR ALTER PROCEDURE [dbo].[usp_PurgeExpiredWspStaging]
    @RetentionDays INT = 30,
    @BatchSize INT = 50000
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @CutoffDate DATETIME2 = DATEADD(DAY, -@RetentionDays, SYSUTCDATETIME());
    DECLARE @RowsDeleted INT = 1;

    WHILE (@RowsDeleted > 0)
    BEGIN
        DELETE TOP (@BatchSize) s
        FROM [dbo].[WspBulkImportStaging] s
        INNER JOIN [dbo].[WspBulkImportBatch] b ON s.BatchId = b.Id
        WHERE b.CreatedAt < @CutoffDate AND b.BatchStatus IN ('Committed', 'RolledBack');

        SET @RowsDeleted = @@ROWCOUNT;
        WAITFOR DELAY '00:00:01'; -- Prevent log saturation
    END
END
GO
