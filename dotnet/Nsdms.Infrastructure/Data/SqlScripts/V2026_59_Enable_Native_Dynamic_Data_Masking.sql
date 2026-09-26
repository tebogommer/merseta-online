-- ====================================================================================================
-- Script: V2026_59_Enable_Native_Dynamic_Data_Masking.sql
-- Description: Applies SQL Server Dynamic Data Masking (DDM) to sensitive financial and demographic
--              identifiers in compliance with POPIA Section 19 (AC-13).
-- Database: NSDMS-NET
-- ====================================================================================================

IF EXISTS (
    SELECT 1 FROM sys.columns c
    JOIN sys.tables t ON c.object_id = t.object_id
    WHERE t.name = 'BankingDetails' AND c.name = 'AccountNumber' AND c.is_masked = 0
)
BEGIN
    ALTER TABLE [dbo].[BankingDetails]
    ALTER COLUMN [AccountNumber] ADD MASKED WITH (FUNCTION = 'partial(0, "******", 4)');
    PRINT 'Dynamic Data Masking applied to dbo.BankingDetails.AccountNumber';
END
GO

IF EXISTS (
    SELECT 1 FROM sys.columns c
    JOIN sys.tables t ON c.object_id = t.object_id
    WHERE t.name = 'Person' AND c.name = 'IdentificationNumber' AND c.is_masked = 0
)
BEGIN
    ALTER TABLE [dbo].[Person]
    ALTER COLUMN [IdentificationNumber] ADD MASKED WITH (FUNCTION = 'partial(6, "*****", 2)');
    PRINT 'Dynamic Data Masking applied to dbo.Person.IdentificationNumber';
END
GO
