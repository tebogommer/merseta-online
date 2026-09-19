-- =====================================================================================
-- Migration: V2026_52_Universal_ForeignKey_Indexes_And_Repair.sql
-- Purpose: Wave 2 Remediation: Repair broken FKs, add missing FK indexes, map RowVersion,
--          and harmonize QualificationId data types across relational joins.
-- Date: 2026-09-19
-- =====================================================================================

-- 1. Repair FK_CompanyLearner_Organisation
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'CompanyLearner' AND schema_id = SCHEMA_ID(N'dbo'))
   AND EXISTS (SELECT 1 FROM sys.tables WHERE name = N'Organisation' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    -- If FK exists referencing wrong column, drop it
    IF EXISTS (
        SELECT 1 
        FROM sys.foreign_key_columns fkc
        JOIN sys.columns c ON fkc.parent_object_id = c.object_id AND fkc.parent_column_id = c.column_id
        WHERE fkc.constraint_object_id = OBJECT_ID(N'dbo.FK_CompanyLearner_Organisation')
          AND c.name <> N'OrganisationId'
    )
    BEGIN
        ALTER TABLE [dbo].[CompanyLearner] DROP CONSTRAINT [FK_CompanyLearner_Organisation];
        PRINT 'Dropped invalid FK_CompanyLearner_Organisation constraint.';
    END

    IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = N'FK_CompanyLearner_Organisation' AND parent_object_id = OBJECT_ID(N'dbo.CompanyLearner'))
    BEGIN
        ALTER TABLE [dbo].[CompanyLearner] WITH NOCHECK
        ADD CONSTRAINT [FK_CompanyLearner_Organisation]
        FOREIGN KEY ([OrganisationId]) REFERENCES [dbo].[Organisation] ([Id]);

        ALTER TABLE [dbo].[CompanyLearner] CHECK CONSTRAINT [FK_CompanyLearner_Organisation];
        PRINT 'Added valid constraint [FK_CompanyLearner_Organisation] referencing OrganisationId.';
    END
END
GO

-- 2. Add Missing Non-Clustered Indexes on Foreign Key Columns
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'WorkplaceApproval' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'IX_WorkplaceApproval_AssessorPersonId' AND object_id = OBJECT_ID(N'dbo.WorkplaceApproval'))
    BEGIN
        CREATE NONCLUSTERED INDEX [IX_WorkplaceApproval_AssessorPersonId]
        ON [dbo].[WorkplaceApproval] ([AssessorPersonId])
        WHERE [AssessorPersonId] IS NOT NULL;
        PRINT 'Created index IX_WorkplaceApproval_AssessorPersonId.';
    END
END
GO

IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'SummativeAssessmentUnitStandard' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'IX_SummativeAssessmentUnitStandard_AssessorPersonId' AND object_id = OBJECT_ID(N'dbo.SummativeAssessmentUnitStandard'))
    BEGIN
        CREATE NONCLUSTERED INDEX [IX_SummativeAssessmentUnitStandard_AssessorPersonId]
        ON [dbo].[SummativeAssessmentUnitStandard] ([AssessorPersonId])
        WHERE [AssessorPersonId] IS NOT NULL;
        PRINT 'Created index IX_SummativeAssessmentUnitStandard_AssessorPersonId.';
    END
END
GO

IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'LearnerTradeTest' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'IX_LearnerTradeTest_AssessorPersonId' AND object_id = OBJECT_ID(N'dbo.LearnerTradeTest'))
    BEGIN
        CREATE NONCLUSTERED INDEX [IX_LearnerTradeTest_AssessorPersonId]
        ON [dbo].[LearnerTradeTest] ([AssessorPersonId])
        WHERE [AssessorPersonId] IS NOT NULL;
        PRINT 'Created index IX_LearnerTradeTest_AssessorPersonId.';
    END
END
GO

-- 3. Add RowVersion to Aggregate Roots
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'WspSubmission' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.WspSubmission') AND name = N'RowVersion')
    BEGIN
        ALTER TABLE [dbo].[WspSubmission] ADD [RowVersion] ROWVERSION NOT NULL;
        PRINT 'Added RowVersion to dbo.WspSubmission.';
    END
END
GO

IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'GrantApplication' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.GrantApplication') AND name = N'RowVersion')
    BEGIN
        ALTER TABLE [dbo].[GrantApplication] ADD [RowVersion] ROWVERSION NOT NULL;
        PRINT 'Added RowVersion to dbo.GrantApplication.';
    END
END
GO

IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'Organisation' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.Organisation') AND name = N'RowVersion')
    BEGIN
        ALTER TABLE [dbo].[Organisation] ADD [RowVersion] ROWVERSION NOT NULL;
        PRINT 'Added RowVersion to dbo.Organisation.';
    END
END
GO

IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'CompanyLearner' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.CompanyLearner') AND name = N'RowVersion')
    BEGIN
        ALTER TABLE [dbo].[CompanyLearner] ADD [RowVersion] ROWVERSION NOT NULL;
        PRINT 'Added RowVersion to dbo.CompanyLearner.';
    END
END
GO

-- 4. Harmonize QualificationId on LearnerTradeTest to INT NULL
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'LearnerTradeTest' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    IF EXISTS (
        SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS
        WHERE TABLE_SCHEMA = 'dbo' AND TABLE_NAME = 'LearnerTradeTest'
          AND COLUMN_NAME = 'QualificationId' AND DATA_TYPE IN ('nvarchar', 'varchar')
    )
    BEGIN
        -- Drop index on QualificationId if it exists
        IF EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'IX_LearnerTradeTest_QualificationId' AND object_id = OBJECT_ID(N'dbo.LearnerTradeTest'))
        BEGIN
            DROP INDEX [IX_LearnerTradeTest_QualificationId] ON [dbo].[LearnerTradeTest];
        END

        ALTER TABLE [dbo].[LearnerTradeTest] ALTER COLUMN [QualificationId] INT NULL;
        PRINT 'Harmonized LearnerTradeTest.QualificationId column to INT NULL.';

        CREATE NONCLUSTERED INDEX [IX_LearnerTradeTest_QualificationId]
        ON [dbo].[LearnerTradeTest] ([QualificationId])
        WHERE [QualificationId] IS NOT NULL;
        PRINT 'Recreated index IX_LearnerTradeTest_QualificationId as INT.';
    END
END
GO
