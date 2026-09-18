-- =========================================================================================
-- MerSETA NSDMS Enterprise SQL Migration: Module 19 - Organisation Employee Roster
-- Description: Option B (The Living Employer Roster with 1-Click WSP Auto-Harvest)
-- Standards: Singular PascalCase, Covering FK Indexes, Audit Columns, Temporal Versioning
-- Script: V2026_16_Organisation_Employee_Roster.sql
-- =========================================================================================

SET QUOTED_IDENTIFIER ON;
SET ANSI_NULLS ON;

-- 1. Create Table: [dbo].[OrganisationEmployee]
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'OrganisationEmployee' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE [dbo].[OrganisationEmployee] (
        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_OrganisationEmployee] PRIMARY KEY CLUSTERED,
        [OrganisationId] INT NOT NULL,
        [PersonId] INT NOT NULL,
        [OrganisationSiteId] INT NULL,
        [EmployeeNumber] NVARCHAR(50) NULL,
        [JobTitle] NVARCHAR(150) NULL,
        [OfoCodeId] NVARCHAR(50) NULL,
        [EmploymentTypeCode] NVARCHAR(50) NULL CONSTRAINT [DF_OrganisationEmployee_EmploymentType] DEFAULT ('PERMANENT'),
        [EmploymentStatusCode] NVARCHAR(50) NULL CONSTRAINT [DF_OrganisationEmployee_EmploymentStatus] DEFAULT ('ACTIVE'),
        [OccupationalCategoryCode] NVARCHAR(50) NULL,
        [StartDate] DATETIME2 NULL,
        [EndDate] DATETIME2 NULL,
        [IsActive] BIT NOT NULL CONSTRAINT [DF_OrganisationEmployee_IsActive] DEFAULT (1),
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_OrganisationEmployee_CreatedAt] DEFAULT (SYSUTCDATETIME()),
        [CreatedBy] NVARCHAR(150) NULL CONSTRAINT [DF_OrganisationEmployee_CreatedBy] DEFAULT ('SYSTEM'),
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(150) NULL,

        CONSTRAINT [FK_OrganisationEmployee_Organisation] FOREIGN KEY ([OrganisationId]) 
            REFERENCES [dbo].[Organisation] ([Id]) ON DELETE CASCADE,
        CONSTRAINT [FK_OrganisationEmployee_Person] FOREIGN KEY ([PersonId]) 
            REFERENCES [dbo].[Person] ([Id]),
        CONSTRAINT [FK_OrganisationEmployee_OrganisationSite] FOREIGN KEY ([OrganisationSiteId]) 
            REFERENCES [dbo].[OrganisationSite] ([Id])
    );
END;

-- 2. FK to lookup.OfoCodeType if table exists
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = 'OfoCodeType' AND schema_id = SCHEMA_ID('lookup'))
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = 'FK_OrganisationEmployee_OfoCode' AND parent_object_id = OBJECT_ID('dbo.OrganisationEmployee'))
    BEGIN
        ALTER TABLE [dbo].[OrganisationEmployee] WITH CHECK 
        ADD CONSTRAINT [FK_OrganisationEmployee_OfoCode] FOREIGN KEY ([OfoCodeId]) 
        REFERENCES [lookup].[OfoCodeType] ([Code]);
    END;
END;

-- 3. High Performance Universal Indexes
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_OrganisationEmployee_OrganisationId' AND object_id = OBJECT_ID('dbo.OrganisationEmployee'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_OrganisationEmployee_OrganisationId]
    ON [dbo].[OrganisationEmployee] ([OrganisationId]);
END;

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_OrganisationEmployee_PersonId' AND object_id = OBJECT_ID('dbo.OrganisationEmployee'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_OrganisationEmployee_PersonId]
    ON [dbo].[OrganisationEmployee] ([PersonId]);
END;

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_OrganisationEmployee_OrganisationSiteId' AND object_id = OBJECT_ID('dbo.OrganisationEmployee'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_OrganisationEmployee_OrganisationSiteId]
    ON [dbo].[OrganisationEmployee] ([OrganisationSiteId]);
END;

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_OrganisationEmployee_OfoCodeId' AND object_id = OBJECT_ID('dbo.OrganisationEmployee'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_OrganisationEmployee_OfoCodeId]
    ON [dbo].[OrganisationEmployee] ([OfoCodeId]);
END;

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_OrganisationEmployee_IsActive' AND object_id = OBJECT_ID('dbo.OrganisationEmployee'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_OrganisationEmployee_IsActive]
    ON [dbo].[OrganisationEmployee] ([IsActive]);
END;

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_OrganisationEmployee_Org_Active' AND object_id = OBJECT_ID('dbo.OrganisationEmployee'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_OrganisationEmployee_Org_Active]
    ON [dbo].[OrganisationEmployee] ([OrganisationId], [IsActive])
    INCLUDE ([PersonId], [OrganisationSiteId], [EmployeeNumber], [JobTitle], [OfoCodeId], [OccupationalCategoryCode]);
END;

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_OrganisationEmployee_EmployeeNumber' AND object_id = OBJECT_ID('dbo.OrganisationEmployee'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_OrganisationEmployee_EmployeeNumber]
    ON [dbo].[OrganisationEmployee] ([OrganisationId], [EmployeeNumber]);
END;

-- 4. Temporal Table Versioning (System-Versioned History)
IF NOT EXISTS (SELECT 1 FROM sys.schemas WHERE name = 'history')
BEGIN
    EXEC('CREATE SCHEMA [history]');
END;

IF EXISTS (SELECT 1 FROM sys.tables WHERE name = 'OrganisationEmployee' AND schema_id = SCHEMA_ID('dbo') AND temporal_type = 0)
BEGIN
    BEGIN TRY
        IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.OrganisationEmployee') AND name = 'PeriodStart')
        BEGIN
            ALTER TABLE [dbo].[OrganisationEmployee] ADD
                [PeriodStart] DATETIME2 GENERATED ALWAYS AS ROW START HIDDEN NOT NULL CONSTRAINT [DF_OrganisationEmployee_PeriodStart] DEFAULT SYSUTCDATETIME(),
                [PeriodEnd] DATETIME2 GENERATED ALWAYS AS ROW END HIDDEN NOT NULL CONSTRAINT [DF_OrganisationEmployee_PeriodEnd] DEFAULT '9999-12-31 23:59:59.9999999',
                PERIOD FOR SYSTEM_TIME ([PeriodStart], [PeriodEnd]);
        END;

        IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'OrganisationEmployeeHistory' AND schema_id = SCHEMA_ID('history'))
        BEGIN
            ALTER TABLE [dbo].[OrganisationEmployee]
            SET (SYSTEM_VERSIONING = ON (HISTORY_TABLE = [history].[OrganisationEmployeeHistory]));
        END;
    END TRY
    BEGIN CATCH
        PRINT 'Temporal versioning configuration note: ' + ERROR_MESSAGE();
    END CATCH;
END;
