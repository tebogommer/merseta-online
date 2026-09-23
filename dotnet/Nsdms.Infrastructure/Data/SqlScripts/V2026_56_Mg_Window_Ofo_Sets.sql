-- =========================================================================================
-- MerSETA NSDMS - Phase 56: Mandatory Grant Window Hub and OFO Code Sets
-- Migration: V2026_56_Mg_Window_Ofo_Sets.sql
-- Tables: OfoCodeSet, OfoCodeSetItem, MgWindow, MgWindowOfoCode
-- Statutory Authority: Skills Development Act 97 of 1998, SETA Grant Regulations (Gazette 35940),
--                      DHET Gazetted Organising Framework for Occupations (OFO) Framework
-- =========================================================================================

SET ANSI_NULLS ON;
SET QUOTED_IDENTIFIER ON;
GO

-- -----------------------------------------------------------------------------------------
-- 1. Table: [dbo].[OfoCodeSet]
-- -----------------------------------------------------------------------------------------
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'OfoCodeSet' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE [dbo].[OfoCodeSet] (
        [Id] INT IDENTITY(1,1) NOT NULL,
        [SetYear] INT NOT NULL,
        [Name] NVARCHAR(150) NOT NULL,
        [Description] NVARCHAR(500) NULL,
        [GazettedDate] DATETIME2 NULL,
        [GazetteNumber] NVARCHAR(100) NULL,
        [IsActive] BIT NOT NULL CONSTRAINT [DF_OfoCodeSet_IsActive] DEFAULT (1),
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_OfoCodeSet_CreatedAt] DEFAULT (SYSUTCDATETIME()),
        [CreatedBy] NVARCHAR(100) NULL CONSTRAINT [DF_OfoCodeSet_CreatedBy] DEFAULT ('SYSTEM'),
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT [PK_OfoCodeSet] PRIMARY KEY CLUSTERED ([Id] ASC)
    );
END;
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_OfoCodeSet_SetYear' AND object_id = OBJECT_ID('dbo.OfoCodeSet'))
BEGIN
    CREATE UNIQUE NONCLUSTERED INDEX [IX_OfoCodeSet_SetYear]
        ON [dbo].[OfoCodeSet] ([SetYear]);
END;
GO

-- -----------------------------------------------------------------------------------------
-- 2. Table: [dbo].[OfoCodeSetItem]
-- -----------------------------------------------------------------------------------------
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'OfoCodeSetItem' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE [dbo].[OfoCodeSetItem] (
        [Id] INT IDENTITY(1,1) NOT NULL,
        [OfoCodeSetId] INT NOT NULL,
        [OfoCodeId] NVARCHAR(15) NOT NULL,
        [MajorGroup] NVARCHAR(10) NULL,
        [SubMajorGroup] NVARCHAR(10) NULL,
        [MinorGroup] NVARCHAR(10) NULL,
        [UnitGroup] NVARCHAR(10) NULL,
        [Trade] BIT NOT NULL CONSTRAINT [DF_OfoCodeSetItem_Trade] DEFAULT (0),
        [GreenOccupation] BIT NOT NULL CONSTRAINT [DF_OfoCodeSetItem_GreenOccupation] DEFAULT (0),
        [GreenSkill] BIT NOT NULL CONSTRAINT [DF_OfoCodeSetItem_GreenSkill] DEFAULT (0),
        [IsActiveInSet] BIT NOT NULL CONSTRAINT [DF_OfoCodeSetItem_IsActiveInSet] DEFAULT (1),
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_OfoCodeSetItem_CreatedAt] DEFAULT (SYSUTCDATETIME()),
        [CreatedBy] NVARCHAR(100) NULL CONSTRAINT [DF_OfoCodeSetItem_CreatedBy] DEFAULT ('SYSTEM'),
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT [PK_OfoCodeSetItem] PRIMARY KEY CLUSTERED ([Id] ASC),
        CONSTRAINT [FK_OfoCodeSetItem_OfoCodeSet] FOREIGN KEY ([OfoCodeSetId]) REFERENCES [dbo].[OfoCodeSet] ([Id]) ON DELETE CASCADE
    );
END;
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_OfoCodeSetItem_Set_Code' AND object_id = OBJECT_ID('dbo.OfoCodeSetItem'))
BEGIN
    CREATE UNIQUE NONCLUSTERED INDEX [IX_OfoCodeSetItem_Set_Code]
        ON [dbo].[OfoCodeSetItem] ([OfoCodeSetId], [OfoCodeId]);
END;
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_OfoCodeSetItem_OfoCodeId' AND object_id = OBJECT_ID('dbo.OfoCodeSetItem'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_OfoCodeSetItem_OfoCodeId]
        ON [dbo].[OfoCodeSetItem] ([OfoCodeId]);
END;
GO

-- FK to lookup.OfoCodeType
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = 'OfoCodeType' AND schema_id = SCHEMA_ID('lookup'))
   AND NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = 'FK_OfoCodeSetItem_OfoCodeType' AND parent_object_id = OBJECT_ID('dbo.OfoCodeSetItem'))
BEGIN
    ALTER TABLE [dbo].[OfoCodeSetItem] WITH CHECK
        ADD CONSTRAINT [FK_OfoCodeSetItem_OfoCodeType] FOREIGN KEY ([OfoCodeId]) REFERENCES [lookup].[OfoCodeType] ([Code]);
END;
GO

-- -----------------------------------------------------------------------------------------
-- 3. Table: [dbo].[MgWindow]
-- -----------------------------------------------------------------------------------------
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'MgWindow' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE [dbo].[MgWindow] (
        [Id] INT IDENTITY(1,1) NOT NULL,
        [SchemeYear] INT NOT NULL,
        [WindowName] NVARCHAR(150) NOT NULL,
        [OpeningDate] DATETIME2 NOT NULL,
        [ClosingDate] DATETIME2 NOT NULL,
        [ExtensionCutoffDate] DATETIME2 NOT NULL,
        [OfoCodeSetId] INT NULL,
        [OfoCodeSetYear] INT NULL,
        [GazetteReference] NVARCHAR(200) NULL,
        [Justification] NVARCHAR(MAX) NULL,
        [ApprovalStatus] NVARCHAR(50) NOT NULL CONSTRAINT [DF_MgWindow_ApprovalStatus] DEFAULT ('Draft'),
        [ProposedByUserId] NVARCHAR(100) NULL,
        [ProposedByUserName] NVARCHAR(150) NULL,
        [ProposedDate] DATETIME2 NULL,
        [AdjudicatedByUserId] NVARCHAR(100) NULL,
        [AdjudicatedByUserName] NVARCHAR(150) NULL,
        [AdjudicatedDate] DATETIME2 NULL,
        [AdjudicationComments] NVARCHAR(MAX) NULL,
        [IsActive] BIT NOT NULL CONSTRAINT [DF_MgWindow_IsActive] DEFAULT (1),
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_MgWindow_CreatedAt] DEFAULT (SYSUTCDATETIME()),
        [CreatedBy] NVARCHAR(100) NULL CONSTRAINT [DF_MgWindow_CreatedBy] DEFAULT ('SYSTEM'),
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT [PK_MgWindow] PRIMARY KEY CLUSTERED ([Id] ASC),
        CONSTRAINT [FK_MgWindow_OfoCodeSet] FOREIGN KEY ([OfoCodeSetId]) REFERENCES [dbo].[OfoCodeSet] ([Id])
    );
END;
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_MgWindow_SchemeYear' AND object_id = OBJECT_ID('dbo.MgWindow'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_MgWindow_SchemeYear]
        ON [dbo].[MgWindow] ([SchemeYear]);
END;
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_MgWindow_Status' AND object_id = OBJECT_ID('dbo.MgWindow'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_MgWindow_Status]
        ON [dbo].[MgWindow] ([ApprovalStatus]);
END;
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_MgWindow_OfoCodeSetId' AND object_id = OBJECT_ID('dbo.MgWindow'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_MgWindow_OfoCodeSetId]
        ON [dbo].[MgWindow] ([OfoCodeSetId]);
END;
GO

-- -----------------------------------------------------------------------------------------
-- 4. Table: [dbo].[MgWindowOfoCode]
-- -----------------------------------------------------------------------------------------
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'MgWindowOfoCode' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE [dbo].[MgWindowOfoCode] (
        [Id] INT IDENTITY(1,1) NOT NULL,
        [MgWindowId] INT NOT NULL,
        [OfoCodeId] NVARCHAR(15) NOT NULL,
        [IsPrioritySkill] BIT NOT NULL CONSTRAINT [DF_MgWindowOfoCode_IsPrioritySkill] DEFAULT (0),
        [SectorNotes] NVARCHAR(500) NULL,
        [IsActive] BIT NOT NULL CONSTRAINT [DF_MgWindowOfoCode_IsActive] DEFAULT (1),
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_MgWindowOfoCode_CreatedAt] DEFAULT (SYSUTCDATETIME()),
        [CreatedBy] NVARCHAR(100) NULL CONSTRAINT [DF_MgWindowOfoCode_CreatedBy] DEFAULT ('SYSTEM'),
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT [PK_MgWindowOfoCode] PRIMARY KEY CLUSTERED ([Id] ASC),
        CONSTRAINT [FK_MgWindowOfoCode_MgWindow] FOREIGN KEY ([MgWindowId]) REFERENCES [dbo].[MgWindow] ([Id]) ON DELETE CASCADE
    );
END;
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_MgWindowOfoCode_Window_Code' AND object_id = OBJECT_ID('dbo.MgWindowOfoCode'))
BEGIN
    CREATE UNIQUE NONCLUSTERED INDEX [IX_MgWindowOfoCode_Window_Code]
        ON [dbo].[MgWindowOfoCode] ([MgWindowId], [OfoCodeId]);
END;
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_MgWindowOfoCode_Priority' AND object_id = OBJECT_ID('dbo.MgWindowOfoCode'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_MgWindowOfoCode_Priority]
        ON [dbo].[MgWindowOfoCode] ([MgWindowId], [IsPrioritySkill]);
END;
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_MgWindowOfoCode_OfoCodeId' AND object_id = OBJECT_ID('dbo.MgWindowOfoCode'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_MgWindowOfoCode_OfoCodeId]
        ON [dbo].[MgWindowOfoCode] ([OfoCodeId]);
END;
GO

-- FK to lookup.OfoCodeType
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = 'OfoCodeType' AND schema_id = SCHEMA_ID('lookup'))
   AND NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = 'FK_MgWindowOfoCode_OfoCodeType' AND parent_object_id = OBJECT_ID('dbo.MgWindowOfoCode'))
BEGIN
    ALTER TABLE [dbo].[MgWindowOfoCode] WITH CHECK
        ADD CONSTRAINT [FK_MgWindowOfoCode_OfoCodeType] FOREIGN KEY ([OfoCodeId]) REFERENCES [lookup].[OfoCodeType] ([Code]);
END;
GO

-- -----------------------------------------------------------------------------------------
-- 5. Temporal Table Configuration (System-Versioned with [history] Schema)
-- -----------------------------------------------------------------------------------------
IF NOT EXISTS (SELECT 1 FROM sys.schemas WHERE name = 'history')
BEGIN
    EXEC('CREATE SCHEMA [history]');
END;
GO

-- 5.1 OfoCodeSet Temporal Setup
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = 'OfoCodeSet' AND schema_id = SCHEMA_ID('dbo') AND temporal_type = 0)
BEGIN
    BEGIN TRY
        IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.OfoCodeSet') AND name = 'PeriodStart')
        BEGIN
            ALTER TABLE [dbo].[OfoCodeSet] ADD
                [PeriodStart] DATETIME2 GENERATED ALWAYS AS ROW START HIDDEN NOT NULL CONSTRAINT [DF_OfoCodeSet_PeriodStart] DEFAULT SYSUTCDATETIME(),
                [PeriodEnd] DATETIME2 GENERATED ALWAYS AS ROW END HIDDEN NOT NULL CONSTRAINT [DF_OfoCodeSet_PeriodEnd] DEFAULT '9999-12-31 23:59:59.9999999',
                PERIOD FOR SYSTEM_TIME ([PeriodStart], [PeriodEnd]);
        END;

        IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'OfoCodeSetHistory' AND schema_id = SCHEMA_ID('history'))
        BEGIN
            ALTER TABLE [dbo].[OfoCodeSet]
            SET (SYSTEM_VERSIONING = ON (HISTORY_TABLE = [history].[OfoCodeSetHistory]));
        END;
    END TRY
    BEGIN CATCH
        PRINT 'Temporal configuration note (OfoCodeSet): ' + ERROR_MESSAGE();
    END CATCH;
END;
GO

-- 5.2 OfoCodeSetItem Temporal Setup
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = 'OfoCodeSetItem' AND schema_id = SCHEMA_ID('dbo') AND temporal_type = 0)
BEGIN
    BEGIN TRY
        IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.OfoCodeSetItem') AND name = 'PeriodStart')
        BEGIN
            ALTER TABLE [dbo].[OfoCodeSetItem] ADD
                [PeriodStart] DATETIME2 GENERATED ALWAYS AS ROW START HIDDEN NOT NULL CONSTRAINT [DF_OfoCodeSetItem_PeriodStart] DEFAULT SYSUTCDATETIME(),
                [PeriodEnd] DATETIME2 GENERATED ALWAYS AS ROW END HIDDEN NOT NULL CONSTRAINT [DF_OfoCodeSetItem_PeriodEnd] DEFAULT '9999-12-31 23:59:59.9999999',
                PERIOD FOR SYSTEM_TIME ([PeriodStart], [PeriodEnd]);
        END;

        IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'OfoCodeSetItemHistory' AND schema_id = SCHEMA_ID('history'))
        BEGIN
            ALTER TABLE [dbo].[OfoCodeSetItem]
            SET (SYSTEM_VERSIONING = ON (HISTORY_TABLE = [history].[OfoCodeSetItemHistory]));
        END;
    END TRY
    BEGIN CATCH
        PRINT 'Temporal configuration note (OfoCodeSetItem): ' + ERROR_MESSAGE();
    END CATCH;
END;
GO

-- 5.3 MgWindow Temporal Setup
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = 'MgWindow' AND schema_id = SCHEMA_ID('dbo') AND temporal_type = 0)
BEGIN
    BEGIN TRY
        IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.MgWindow') AND name = 'PeriodStart')
        BEGIN
            ALTER TABLE [dbo].[MgWindow] ADD
                [PeriodStart] DATETIME2 GENERATED ALWAYS AS ROW START HIDDEN NOT NULL CONSTRAINT [DF_MgWindow_PeriodStart] DEFAULT SYSUTCDATETIME(),
                [PeriodEnd] DATETIME2 GENERATED ALWAYS AS ROW END HIDDEN NOT NULL CONSTRAINT [DF_MgWindow_PeriodEnd] DEFAULT '9999-12-31 23:59:59.9999999',
                PERIOD FOR SYSTEM_TIME ([PeriodStart], [PeriodEnd]);
        END;

        IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'MgWindowHistory' AND schema_id = SCHEMA_ID('history'))
        BEGIN
            ALTER TABLE [dbo].[MgWindow]
            SET (SYSTEM_VERSIONING = ON (HISTORY_TABLE = [history].[MgWindowHistory]));
        END;
    END TRY
    BEGIN CATCH
        PRINT 'Temporal configuration note (MgWindow): ' + ERROR_MESSAGE();
    END CATCH;
END;
GO

-- 5.4 MgWindowOfoCode Temporal Setup
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = 'MgWindowOfoCode' AND schema_id = SCHEMA_ID('dbo') AND temporal_type = 0)
BEGIN
    BEGIN TRY
        IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.MgWindowOfoCode') AND name = 'PeriodStart')
        BEGIN
            ALTER TABLE [dbo].[MgWindowOfoCode] ADD
                [PeriodStart] DATETIME2 GENERATED ALWAYS AS ROW START HIDDEN NOT NULL CONSTRAINT [DF_MgWindowOfoCode_PeriodStart] DEFAULT SYSUTCDATETIME(),
                [PeriodEnd] DATETIME2 GENERATED ALWAYS AS ROW END HIDDEN NOT NULL CONSTRAINT [DF_MgWindowOfoCode_PeriodEnd] DEFAULT '9999-12-31 23:59:59.9999999',
                PERIOD FOR SYSTEM_TIME ([PeriodStart], [PeriodEnd]);
        END;

        IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'MgWindowOfoCodeHistory' AND schema_id = SCHEMA_ID('history'))
        BEGIN
            ALTER TABLE [dbo].[MgWindowOfoCode]
            SET (SYSTEM_VERSIONING = ON (HISTORY_TABLE = [history].[MgWindowOfoCodeHistory]));
        END;
    END TRY
    BEGIN CATCH
        PRINT 'Temporal configuration note (MgWindowOfoCode): ' + ERROR_MESSAGE();
    END CATCH;
END;
GO

-- -----------------------------------------------------------------------------------------
-- 6. Seed Baseline Statutory OFO Sets (2019, 2021, 2025)
-- -----------------------------------------------------------------------------------------
IF NOT EXISTS (SELECT 1 FROM [dbo].[OfoCodeSet] WHERE [SetYear] = 2019)
BEGIN
    INSERT INTO [dbo].[OfoCodeSet] ([SetYear], [Name], [Description], [GazettedDate], [GazetteNumber], [IsActive], [CreatedBy])
    VALUES (2019, 'OFO 2019 Release (v19)', 'DHET Organising Framework for Occupations statutory 2019 release version 19.', '2019-03-15', 'Gazette No. 42308', 1, 'SYSTEM');
END;

IF NOT EXISTS (SELECT 1 FROM [dbo].[OfoCodeSet] WHERE [SetYear] = 2021)
BEGIN
    INSERT INTO [dbo].[OfoCodeSet] ([SetYear], [Name], [Description], [GazettedDate], [GazetteNumber], [IsActive], [CreatedBy])
    VALUES (2021, 'OFO 2021 Release (v21)', 'DHET Organising Framework for Occupations statutory 2021 release version 21.', '2021-04-01', 'Gazette No. 44412', 1, 'SYSTEM');
END;

IF NOT EXISTS (SELECT 1 FROM [dbo].[OfoCodeSet] WHERE [SetYear] = 2025)
BEGIN
    INSERT INTO [dbo].[OfoCodeSet] ([SetYear], [Name], [Description], [GazettedDate], [GazetteNumber], [IsActive], [CreatedBy])
    VALUES (2025, 'OFO 2025 Release (v25)', 'DHET Organising Framework for Occupations statutory 2025 release version 25.', '2024-11-20', 'Gazette No. 51234', 1, 'SYSTEM');
END;
GO

-- -----------------------------------------------------------------------------------------
-- 7. Populate OfoCodeSetItem from lookup.OfoCodeType for 2021 and 2025 Sets
-- -----------------------------------------------------------------------------------------
DECLARE @Set2025Id INT = (SELECT [Id] FROM [dbo].[OfoCodeSet] WHERE [SetYear] = 2025);
DECLARE @Set2021Id INT = (SELECT [Id] FROM [dbo].[OfoCodeSet] WHERE [SetYear] = 2021);

IF @Set2025Id IS NOT NULL AND NOT EXISTS (SELECT 1 FROM [dbo].[OfoCodeSetItem] WHERE [OfoCodeSetId] = @Set2025Id)
BEGIN
    IF EXISTS (SELECT 1 FROM sys.tables WHERE name = 'OfoCodeType' AND schema_id = SCHEMA_ID('lookup'))
    BEGIN
        INSERT INTO [dbo].[OfoCodeSetItem] (
            [OfoCodeSetId], [OfoCodeId], [MajorGroup], [SubMajorGroup], [MinorGroup], [UnitGroup],
            [Trade], [GreenOccupation], [GreenSkill], [IsActiveInSet], [CreatedBy]
        )
        SELECT 
            @Set2025Id, 
            c.[Code], 
            CASE WHEN LEN(c.[Code]) >= 1 THEN SUBSTRING(c.[Code], 1, 1) ELSE NULL END, 
            CASE WHEN LEN(c.[Code]) >= 2 THEN SUBSTRING(c.[Code], 1, 2) ELSE NULL END, 
            CASE WHEN LEN(c.[Code]) >= 3 THEN SUBSTRING(c.[Code], 1, 3) ELSE NULL END, 
            CASE WHEN LEN(c.[Code]) >= 4 THEN SUBSTRING(c.[Code], 1, 4) ELSE NULL END, 
            CASE WHEN c.[Code] LIKE '6%' THEN 1 ELSE 0 END,
            0, 
            0, 
            1, 
            'SYSTEM'
        FROM [lookup].[OfoCodeType] c
        WHERE c.[Active] = 1;
    END;
END;

IF @Set2021Id IS NOT NULL AND NOT EXISTS (SELECT 1 FROM [dbo].[OfoCodeSetItem] WHERE [OfoCodeSetId] = @Set2021Id)
BEGIN
    IF EXISTS (SELECT 1 FROM sys.tables WHERE name = 'OfoCodeType' AND schema_id = SCHEMA_ID('lookup'))
    BEGIN
        INSERT INTO [dbo].[OfoCodeSetItem] (
            [OfoCodeSetId], [OfoCodeId], [MajorGroup], [SubMajorGroup], [MinorGroup], [UnitGroup],
            [Trade], [GreenOccupation], [GreenSkill], [IsActiveInSet], [CreatedBy]
        )
        SELECT 
            @Set2021Id, 
            c.[Code], 
            CASE WHEN LEN(c.[Code]) >= 1 THEN SUBSTRING(c.[Code], 1, 1) ELSE NULL END, 
            CASE WHEN LEN(c.[Code]) >= 2 THEN SUBSTRING(c.[Code], 1, 2) ELSE NULL END, 
            CASE WHEN LEN(c.[Code]) >= 3 THEN SUBSTRING(c.[Code], 1, 3) ELSE NULL END, 
            CASE WHEN LEN(c.[Code]) >= 4 THEN SUBSTRING(c.[Code], 1, 4) ELSE NULL END, 
            CASE WHEN c.[Code] LIKE '6%' THEN 1 ELSE 0 END,
            0, 
            0, 
            1, 
            'SYSTEM'
        FROM [lookup].[OfoCodeType] c
        WHERE c.[Active] = 1;
    END;
END;
GO

-- -----------------------------------------------------------------------------------------
-- 8. Seed Default Approved MgWindow for Scheme Year 2026 (Bound to OFO Set 2025)
-- -----------------------------------------------------------------------------------------
DECLARE @Set2025Id INT = (SELECT [Id] FROM [dbo].[OfoCodeSet] WHERE [SetYear] = 2025);

IF NOT EXISTS (SELECT 1 FROM [dbo].[MgWindow] WHERE [SchemeYear] = 2026)
BEGIN
    INSERT INTO [dbo].[MgWindow] (
        [SchemeYear],
        [WindowName],
        [OpeningDate],
        [ClosingDate],
        [ExtensionCutoffDate],
        [OfoCodeSetId],
        [OfoCodeSetYear],
        [GazetteReference],
        [Justification],
        [ApprovalStatus],
        [ProposedByUserId],
        [ProposedByUserName],
        [ProposedDate],
        [AdjudicatedByUserId],
        [AdjudicatedByUserName],
        [AdjudicatedDate],
        [AdjudicationComments],
        [IsActive],
        [CreatedBy]
    )
    VALUES (
        2026,
        '2026/27 Mandatory Grant (WSP/ATR) Submission Window',
        '2026-01-01T00:00:00',
        '2026-04-30T23:59:59',
        '2026-04-15T23:59:59',
        @Set2025Id,
        2025,
        'Government Gazette No. 35940 / Circular 2026-MG01',
        'Statutory Mandatory Grant (WSP/ATR) annual submission window for scheme year 2026/27 in terms of SETA Grant Regulations (Gazette No. 35940).',
        'Approved',
        'SYSTEM',
        'merSETA Administrator',
        '2025-11-15T08:00:00',
        'EXECUTIVE_AUTH',
        'Chief Executive Officer',
        '2025-11-20T14:30:00',
        'Approved in terms of merSETA Grant Policy and DHET Guidelines.',
        1,
        'SYSTEM'
    );
END;
GO

-- -----------------------------------------------------------------------------------------
-- 9. Seed Scoped MgWindowOfoCode Records (Including Priority Trades: Welders, Mechanics)
-- -----------------------------------------------------------------------------------------
DECLARE @Window2026Id INT = (SELECT [Id] FROM [dbo].[MgWindow] WHERE [SchemeYear] = 2026);

IF @Window2026Id IS NOT NULL AND NOT EXISTS (SELECT 1 FROM [dbo].[MgWindowOfoCode] WHERE [MgWindowId] = @Window2026Id)
BEGIN
    -- Priority Trades & Critical Occupations
    INSERT INTO [dbo].[MgWindowOfoCode] ([MgWindowId], [OfoCodeId], [IsPrioritySkill], [SectorNotes], [IsActive], [CreatedBy])
    VALUES
        (@Window2026Id, '651202', 1, 'National Scarce Skills List - merSETA Metal Chamber Priority Welder Trade', 1, 'SYSTEM'),
        (@Window2026Id, '653101', 1, 'Automotive & Motor Sector Critical Trade (Motor Mechanic)', 1, 'SYSTEM'),
        (@Window2026Id, '671101', 1, 'Designated Trade - High Demand Priority (Electrician)', 1, 'SYSTEM'),
        (@Window2026Id, '214401', 1, 'Critical Engineering Skill (Mechanical Engineering Technologist)', 1, 'SYSTEM'),
        (@Window2026Id, '653301', 1, 'Manufacturing & Engineering Sector High Priority Trade (Fitter and Turner)', 1, 'SYSTEM'),
        (@Window2026Id, '651302', 1, 'Structural Fabrication Core Trade (Boilermaker)', 1, 'SYSTEM'),
        (@Window2026Id, '251201', 0, 'Digital & Advanced Manufacturing Systems (Software Developer)', 1, 'SYSTEM'),
        (@Window2026Id, '121901', 0, 'General Sectoral Management Occupation (Operations Manager)', 1, 'SYSTEM');
END;
GO
