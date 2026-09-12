-- ============================================================================
-- MerSETA National Skills Development Management System (NSDMS)
-- Script: V2026_48_Sars_Levy_Set_Based_Promotion.sql
-- Description: High-performance set-based stored procedure and covering indexes
--              for Option A: Reactive Streaming Pipeline & Set-Based SQL Promotion.
-- ============================================================================

-- 1. Ensure Covering Indexes on SarsLevyStaging and LevyFileLine
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_SarsLevyStaging_BatchIdentifier_LineNumber' AND object_id = OBJECT_ID('dbo.SarsLevyStaging'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_SarsLevyStaging_BatchIdentifier_LineNumber]
    ON [dbo].[SarsLevyStaging] ([BatchIdentifier], [LineNumber])
    INCLUDE ([SdlNumber], [SicCode], [TotalLevyAmount], [MandatoryLevyAmount], [DiscretionaryLevyAmount], [AdminLevyAmount], [QctoLevyAmount]);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_LevyFileLine_LevyFileId_SdlNumber' AND object_id = OBJECT_ID('dbo.LevyFileLine'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_LevyFileLine_LevyFileId_SdlNumber]
    ON [dbo].[LevyFileLine] ([LevyFileId], [SdlNumber])
    INCLUDE ([TotalLevyAmount], [IsReconciled], [ChamberCode], [SetaCode]);
END
GO

-- 2. Create or Alter usp_PromoteSarsLevyBatch
CREATE OR ALTER PROCEDURE [dbo].[usp_PromoteSarsLevyBatch]
    @BatchIdentifier NVARCHAR(100),
    @LevyFileId INT,
    @CurrentUsername NVARCHAR(100) = N'SYSTEM',
    @OutOfScopeCount INT = 0 OUTPUT,
    @SicMismatchCount INT = 0 OUTPUT,
    @TotalPromotedCount INT = 0 OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    SET XACT_ABORT ON;

    BEGIN TRANSACTION;

    -- Step 1: Bulk resolve ChamberCode and SetaCode in SarsLevyStaging from SicCodeType
    UPDATE s
    SET s.ChamberCode = sic.ChamberCode,
        s.SetaCode = COALESCE(sic.SetaCode, N'17'),
        s.ModifiedAt = SYSUTCDATETIME(),
        s.ModifiedBy = @CurrentUsername
    FROM [dbo].[SarsLevyStaging] s
    INNER JOIN [dbo].[SicCodeType] sic ON s.SicCode = sic.Code
    WHERE s.BatchIdentifier = @BatchIdentifier;

    -- Step 2: Fallback ChamberCode from verified Organisation profile if still null
    UPDATE s
    SET s.ChamberCode = o.ChamberCode,
        s.ModifiedAt = SYSUTCDATETIME(),
        s.ModifiedBy = @CurrentUsername
    FROM [dbo].[SarsLevyStaging] s
    INNER JOIN [dbo].[Organisation] o ON s.SdlNumber = o.SdlNumber
    WHERE s.BatchIdentifier = @BatchIdentifier 
      AND s.ChamberCode IS NULL 
      AND o.ChamberCode IS NOT NULL;

    -- Step 3: Flag Out-of-Scope SETA (SetaCode != '17')
    UPDATE s
    SET s.IsOutOfScopeSeta = 1,
        s.ModifiedAt = SYSUTCDATETIME(),
        s.ModifiedBy = @CurrentUsername
    FROM [dbo].[SarsLevyStaging] s
    WHERE s.BatchIdentifier = @BatchIdentifier 
      AND s.SetaCode <> N'17';

    -- Step 4: Flag SIC Code Mismatches against verified Organisation master profile
    UPDATE s
    SET s.HasSicCodeMismatch = 1,
        s.ModifiedAt = SYSUTCDATETIME(),
        s.ModifiedBy = @CurrentUsername
    FROM [dbo].[SarsLevyStaging] s
    INNER JOIN [dbo].[Organisation] o ON s.SdlNumber = o.SdlNumber
    WHERE s.BatchIdentifier = @BatchIdentifier
      AND s.SicCode IS NOT NULL
      AND o.SicCode IS NOT NULL
      AND s.SicCode <> o.SicCode;

    -- Step 5: Set-based insert into SarsLevyReconAudit for Out-of-Scope SETA transactions
    INSERT INTO [dbo].[SarsLevyReconAudit] (
        [FinancialYear], [SchemeYear], [SdlNumber], [OrganisationId],
        [TotalSarsLeviesReceived], [TotalCalculatedLeviesExpected], [VarianceAmount],
        [DiscrepancyReasonCode], [CounterpartSetaCode], [ActualSarsSicCode],
        [ActualSarsChamberCode], [ExpectedSicCode], [ExpectedChamberCode],
        [AuditStatusCode], [AuditNotes], [AuditorUserId], [ReconciliationDate],
        [CreatedAt], [CreatedBy]
    )
    SELECT
        CASE WHEN LEN(s.SchemeYear) >= 4 THEN SUBSTRING(s.SchemeYear, 1, 4) ELSE CAST(YEAR(SYSUTCDATETIME()) AS NVARCHAR(10)) END,
        COALESCE(s.SchemeYear, CAST(YEAR(SYSUTCDATETIME()) AS NVARCHAR(10))),
        s.SdlNumber,
        o.Id,
        s.TotalLevyAmount,
        0,
        s.TotalLevyAmount,
        N'OutOfScopeSeta',
        s.SetaCode,
        s.SicCode,
        s.ChamberCode,
        o.SicCode,
        o.ChamberCode,
        N'DiscrepancyFlagged',
        CONCAT(N'Streaming SARS levy reported non-merSETA SIC Code ''', s.SicCode, N''' belonging to SETA ''', s.SetaCode, N'''. Out-of-scope Inter-SETA transfer required.'),
        @CurrentUsername,
        SYSUTCDATETIME(),
        SYSUTCDATETIME(),
        @CurrentUsername
    FROM [dbo].[SarsLevyStaging] s
    LEFT JOIN [dbo].[Organisation] o ON s.SdlNumber = o.SdlNumber
    WHERE s.BatchIdentifier = @BatchIdentifier AND s.IsOutOfScopeSeta = 1;

    -- Step 6: Set-based insert into InterSetaTransfer for Out-of-Scope SETAs (only if organisation exists and no pending transfer exists)
    INSERT INTO [dbo].[InterSetaTransfer] (
        [OrganisationId], [TransferType], [OtherSetaCode], [OtherSetaName],
        [TransferReason], [EffectiveDate], [TransferStatusCode], [TransferAmount],
        [CreatedAt], [CreatedBy]
    )
    SELECT DISTINCT
        o.Id,
        N'Outgoing',
        s.SetaCode,
        COALESCE(sic.Description, CONCAT(N'SETA ', s.SetaCode)),
        CONCAT(N'Streaming boundary detection: SARS reported non-merSETA SIC Code ', s.SicCode, N' (SETA ', s.SetaCode, N')'),
        SYSUTCDATETIME(),
        N'Initiated',
        s.TotalLevyAmount,
        SYSUTCDATETIME(),
        @CurrentUsername
    FROM [dbo].[SarsLevyStaging] s
    INNER JOIN [dbo].[Organisation] o ON s.SdlNumber = o.SdlNumber
    LEFT JOIN [dbo].[SicCodeType] sic ON s.SicCode = sic.Code
    WHERE s.BatchIdentifier = @BatchIdentifier 
      AND s.IsOutOfScopeSeta = 1
      AND NOT EXISTS (
          SELECT 1 FROM [dbo].[InterSetaTransfer] t 
          WHERE t.OrganisationId = o.Id AND t.TransferStatusCode = N'Initiated'
      );

    -- Step 7: Set-based insert into SarsLevyReconAudit for SIC Code Mismatches
    INSERT INTO [dbo].[SarsLevyReconAudit] (
        [FinancialYear], [SchemeYear], [SdlNumber], [OrganisationId],
        [TotalSarsLeviesReceived], [TotalCalculatedLeviesExpected], [VarianceAmount],
        [DiscrepancyReasonCode], [ExpectedSicCode], [ActualSarsSicCode],
        [ExpectedChamberCode], [ActualSarsChamberCode],
        [AuditStatusCode], [AuditNotes], [AuditorUserId], [ReconciliationDate],
        [CreatedAt], [CreatedBy]
    )
    SELECT
        CASE WHEN LEN(s.SchemeYear) >= 4 THEN SUBSTRING(s.SchemeYear, 1, 4) ELSE CAST(YEAR(SYSUTCDATETIME()) AS NVARCHAR(10)) END,
        COALESCE(s.SchemeYear, CAST(YEAR(SYSUTCDATETIME()) AS NVARCHAR(10))),
        s.SdlNumber,
        o.Id,
        s.TotalLevyAmount,
        s.TotalLevyAmount,
        0,
        N'SicCodeMismatch',
        o.SicCode,
        s.SicCode,
        o.ChamberCode,
        s.ChamberCode,
        N'DiscrepancyFlagged',
        CONCAT(N'Streaming SARS declared SIC Code ''', s.SicCode, N''' differs from verified master record ''', o.SicCode, N'''.'),
        @CurrentUsername,
        SYSUTCDATETIME(),
        SYSUTCDATETIME(),
        @CurrentUsername
    FROM [dbo].[SarsLevyStaging] s
    INNER JOIN [dbo].[Organisation] o ON s.SdlNumber = o.SdlNumber
    WHERE s.BatchIdentifier = @BatchIdentifier AND s.HasSicCodeMismatch = 1;

    -- Step 8: Ultra-High-Speed Bulk Insert into LevyFileLine
    INSERT INTO [dbo].[LevyFileLine] (
        [LevyFileId], [SdlNumber], [SchemeYear], [SicCode], [ChamberCode], [SetaCode],
        [MandatoryLevyAmount], [DiscretionaryLevyAmount], [AdminLevyAmount], [QctoLevyAmount],
        [InterestAmount], [PenaltyAmount], [TotalLevyAmount], [IsOutOfScopeSeta],
        [HasSicCodeMismatch], [IsReconciled], [CreatedAt], [CreatedBy]
    )
    SELECT
        @LevyFileId,
        s.SdlNumber,
        COALESCE(s.SchemeYear, CAST(YEAR(SYSUTCDATETIME()) AS NVARCHAR(10))),
        s.SicCode,
        s.ChamberCode,
        s.SetaCode,
        s.MandatoryLevyAmount,
        s.DiscretionaryLevyAmount,
        s.AdminLevyAmount,
        s.QctoLevyAmount,
        s.InterestAmount,
        s.PenaltyAmount,
        s.TotalLevyAmount,
        s.IsOutOfScopeSeta,
        s.HasSicCodeMismatch,
        0,
        SYSUTCDATETIME(),
        @CurrentUsername
    FROM [dbo].[SarsLevyStaging] s
    WHERE s.BatchIdentifier = @BatchIdentifier
    ORDER BY s.LineNumber;

    -- Step 9: Mark Staging rows as 'Promoted'
    UPDATE [dbo].[SarsLevyStaging]
    SET [StagingStatus] = N'Promoted',
        [PromotedLevyFileId] = @LevyFileId,
        [ModifiedAt] = SYSUTCDATETIME(),
        [ModifiedBy] = @CurrentUsername
    WHERE [BatchIdentifier] = @BatchIdentifier;

    -- Step 10: Compute output counts
    SELECT 
        @TotalPromotedCount = COUNT(*),
        @OutOfScopeCount = ISNULL(SUM(CASE WHEN IsOutOfScopeSeta = 1 THEN 1 ELSE 0 END), 0),
        @SicMismatchCount = ISNULL(SUM(CASE WHEN HasSicCodeMismatch = 1 THEN 1 ELSE 0 END), 0)
    FROM [dbo].[SarsLevyStaging]
    WHERE [BatchIdentifier] = @BatchIdentifier;

    COMMIT TRANSACTION;
END
GO
