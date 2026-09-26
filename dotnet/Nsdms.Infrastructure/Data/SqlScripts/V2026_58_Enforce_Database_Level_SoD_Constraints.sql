-- ====================================================================================================
-- Script: V2026_58_Enforce_Database_Level_SoD_Constraints.sql
-- Description: Enforces hard database-level Segregation of Duties (Maker-Checker) check constraints
--              on dbo.BankingDetails in accordance with PFMA Sec 38 and COBIT DSS06.03.
-- Database: NSDMS-NET
-- ====================================================================================================

IF EXISTS (SELECT 1 FROM sys.tables WHERE name = 'BankingDetails' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.check_constraints WHERE name = 'CK_BankingDetails_MakerChecker_SoD')
    BEGIN
        ALTER TABLE [dbo].[BankingDetails] WITH CHECK
        ADD CONSTRAINT [CK_BankingDetails_MakerChecker_SoD]
        CHECK (
            [SecondSignoffUserId] IS NULL 
            OR [CreatedBy] <> [SecondSignoffUserId]
        );

        ALTER TABLE [dbo].[BankingDetails] CHECK CONSTRAINT [CK_BankingDetails_MakerChecker_SoD];
        PRINT 'Check constraint CK_BankingDetails_MakerChecker_SoD created successfully.';
    END
    ELSE
    BEGIN
        PRINT 'Check constraint CK_BankingDetails_MakerChecker_SoD already exists.';
    END
END
GO
