-- =========================================================================================
-- V2026_19_NonLevy_Number_And_Chamber_Derivation.sql
-- N-Number Generation Sequence & Chamber / GP Vendor Class Derivation Governance
-- =========================================================================================

SET ANSI_NULLS ON;
SET QUOTED_IDENTIFIER ON;
GO

-- 1. Dynamic High-Water Mark Seeding for Non-Levy Organisation Sequence
DECLARE @MaxExistingNumber INT;

SELECT @MaxExistingNumber = MAX(TRY_CAST(SUBSTRING(SdlNumber, 2, 9) AS INT))
FROM [dbo].[Organisation]
WHERE SdlNumber LIKE 'N[0-9]%';

SET @MaxExistingNumber = ISNULL(@MaxExistingNumber, 100000);
DECLARE @NextStartNumber INT = @MaxExistingNumber + 1;

IF NOT EXISTS (SELECT 1 FROM sys.sequences WHERE name = 'seq_NonLevyOrganisationNumber')
BEGIN
    DECLARE @CreateSeqSql NVARCHAR(MAX) = N'
        CREATE SEQUENCE [dbo].[seq_NonLevyOrganisationNumber]
        AS INT
        START WITH ' + CAST(@NextStartNumber AS NVARCHAR(20)) + N'
        INCREMENT BY 1
        MINVALUE 100000
        MAXVALUE 999999999
        NO CYCLE
        CACHE 10;';
    EXEC sp_executesql @CreateSeqSql;
END
ELSE
BEGIN
    DECLARE @CurrentSeqVal INT;
    SELECT @CurrentSeqVal = CAST(current_value AS INT) 
    FROM sys.sequences 
    WHERE name = 'seq_NonLevyOrganisationNumber';

    IF @CurrentSeqVal <= @MaxExistingNumber
    BEGIN
        DECLARE @AlterSeqSql NVARCHAR(MAX) = N'
            ALTER SEQUENCE [dbo].[seq_NonLevyOrganisationNumber] 
            RESTART WITH ' + CAST(@NextStartNumber AS NVARCHAR(20)) + N';';
        EXEC sp_executesql @AlterSeqSql;
    END
END
GO

-- 2. Add HasMissingChamberMapping and GpVendorClass to Organisation if not exists
IF NOT EXISTS (
    SELECT 1 FROM sys.columns 
    WHERE object_id = OBJECT_ID(N'[dbo].[Organisation]') 
    AND name = 'HasMissingChamberMapping'
)
BEGIN
    ALTER TABLE [dbo].[Organisation]
    ADD [HasMissingChamberMapping] BIT NOT NULL CONSTRAINT DF_Organisation_HasMissingChamberMapping DEFAULT (0);
END
GO

IF NOT EXISTS (
    SELECT 1 FROM sys.columns 
    WHERE object_id = OBJECT_ID(N'[dbo].[Organisation]') 
    AND name = 'GpVendorClass'
)
BEGIN
    ALTER TABLE [dbo].[Organisation]
    ADD [GpVendorClass] NVARCHAR(50) NULL;
END
GO

-- 3. Filtered Unique Index on SdlNumber to enforce uniqueness
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'UQ_Organisation_SdlNumber')
BEGIN
    CREATE UNIQUE NONCLUSTERED INDEX [UQ_Organisation_SdlNumber]
    ON [dbo].[Organisation] ([SdlNumber])
    WHERE [SdlNumber] IS NOT NULL AND [SdlNumber] <> '';
END
GO

-- 4. Index on HasMissingChamberMapping for rapid governance queries
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_Organisation_HasMissingChamberMapping')
BEGIN
    CREATE NONCLUSTERED INDEX [IX_Organisation_HasMissingChamberMapping]
    ON [dbo].[Organisation] ([HasMissingChamberMapping])
    INCLUDE ([ChamberCode], [GpVendorClass], [SicCode]);
END
GO
