-- ==============================================================================
-- NSDMS Migration: V2026_09_Strategic_Priorities_And_DG_Themes.sql
-- Description: Creates StrategicPriority and FundingWindowPriority tables, 
--              adds StrategicPriority FK columns to GrantApplication & GrantProjectBudget.
-- ==============================================================================

SET ANSI_NULLS ON;
SET QUOTED_IDENTIFIER ON;

-- 1. Create StrategicPriority Table
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[StrategicPriority]') AND type in (N'U'))
BEGIN
    CREATE TABLE [dbo].[StrategicPriority] (
        [Id] INT IDENTITY(1,1) NOT NULL,
        [Code] NVARCHAR(50) NOT NULL,
        [Name] NVARCHAR(200) NOT NULL,
        [Description] NVARCHAR(1000) NULL,
        [NsdpOutcomeCode] NVARCHAR(50) NOT NULL,
        [NsdpOutcomeDescription] NVARCHAR(500) NULL,
        [SipCategory] NVARCHAR(150) NULL,
        [TargetSector] NVARCHAR(100) NULL,
        [IsActive] BIT NOT NULL CONSTRAINT [DF_StrategicPriority_IsActive] DEFAULT (1),
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_StrategicPriority_CreatedAt] DEFAULT (SYSUTCDATETIME()),
        [CreatedBy] NVARCHAR(100) NOT NULL CONSTRAINT [DF_StrategicPriority_CreatedBy] DEFAULT ('SYSTEM'),
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT [PK_StrategicPriority] PRIMARY KEY CLUSTERED ([Id] ASC)
    );

    CREATE UNIQUE NONCLUSTERED INDEX [IX_StrategicPriority_Code] ON [dbo].[StrategicPriority]([Code]);
    CREATE NONCLUSTERED INDEX [IX_StrategicPriority_NsdpOutcomeCode] ON [dbo].[StrategicPriority]([NsdpOutcomeCode]);
    CREATE NONCLUSTERED INDEX [IX_StrategicPriority_IsActive] ON [dbo].[StrategicPriority]([IsActive]);
    PRINT 'Created [StrategicPriority] table with indexes.';
END;

-- 2. Create FundingWindowPriority Table
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[FundingWindowPriority]') AND type in (N'U'))
BEGIN
    CREATE TABLE [dbo].[FundingWindowPriority] (
        [Id] INT IDENTITY(1,1) NOT NULL,
        [FundingWindowId] INT NOT NULL,
        [StrategicPriorityId] INT NOT NULL,
        [AllocatedBudget] DECIMAL(18,2) NOT NULL,
        [TargetBeneficiaries] INT NOT NULL,
        [MinScoreThreshold] DECIMAL(5,2) NOT NULL CONSTRAINT [DF_FundingWindowPriority_MinScore] DEFAULT (65.00),
        [IsRingFenced] BIT NOT NULL CONSTRAINT [DF_FundingWindowPriority_IsRingFenced] DEFAULT (0),
        [IsActive] BIT NOT NULL CONSTRAINT [DF_FundingWindowPriority_IsActive] DEFAULT (1),
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_FundingWindowPriority_CreatedAt] DEFAULT (SYSUTCDATETIME()),
        [CreatedBy] NVARCHAR(100) NOT NULL CONSTRAINT [DF_FundingWindowPriority_CreatedBy] DEFAULT ('SYSTEM'),
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT [PK_FundingWindowPriority] PRIMARY KEY CLUSTERED ([Id] ASC),
        CONSTRAINT [FK_FundingWindowPriority_GrantFundingWindow] FOREIGN KEY ([FundingWindowId]) REFERENCES [dbo].[GrantFundingWindow]([Id]) ON DELETE CASCADE,
        CONSTRAINT [FK_FundingWindowPriority_StrategicPriority] FOREIGN KEY ([StrategicPriorityId]) REFERENCES [dbo].[StrategicPriority]([Id])
    );

    CREATE NONCLUSTERED INDEX [IX_FundingWindowPriority_FundingWindowId] ON [dbo].[FundingWindowPriority]([FundingWindowId]);
    CREATE NONCLUSTERED INDEX [IX_FundingWindowPriority_StrategicPriorityId] ON [dbo].[FundingWindowPriority]([StrategicPriorityId]);
    CREATE UNIQUE NONCLUSTERED INDEX [IX_FundingWindowPriority_Window_Priority] ON [dbo].[FundingWindowPriority]([FundingWindowId], [StrategicPriorityId]);
    CREATE NONCLUSTERED INDEX [IX_FundingWindowPriority_IsActive] ON [dbo].[FundingWindowPriority]([IsActive]);
    PRINT 'Created [FundingWindowPriority] table with indexes and constraints.';
END;

-- 3. Add StrategicPriority foreign keys to GrantApplication
IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[GrantApplication]') AND name = 'StrategicPriorityId')
BEGIN
    ALTER TABLE [dbo].[GrantApplication] ADD [StrategicPriorityId] INT NULL;
    ALTER TABLE [dbo].[GrantApplication] ADD CONSTRAINT [FK_GrantApplication_StrategicPriority] FOREIGN KEY ([StrategicPriorityId]) REFERENCES [dbo].[StrategicPriority]([Id]);
    CREATE NONCLUSTERED INDEX [IX_GrantApplication_StrategicPriorityId] ON [dbo].[GrantApplication]([StrategicPriorityId]);
    PRINT 'Added StrategicPriorityId to GrantApplication.';
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[GrantApplication]') AND name = 'FundingWindowPriorityId')
BEGIN
    ALTER TABLE [dbo].[GrantApplication] ADD [FundingWindowPriorityId] INT NULL;
    ALTER TABLE [dbo].[GrantApplication] ADD CONSTRAINT [FK_GrantApplication_FundingWindowPriority] FOREIGN KEY ([FundingWindowPriorityId]) REFERENCES [dbo].[FundingWindowPriority]([Id]);
    CREATE NONCLUSTERED INDEX [IX_GrantApplication_FundingWindowPriorityId] ON [dbo].[GrantApplication]([FundingWindowPriorityId]);
    PRINT 'Added FundingWindowPriorityId to GrantApplication.';
END;

-- 4. Add StrategicPriority foreign key to GrantProjectBudget
IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[GrantProjectBudget]') AND name = 'StrategicPriorityId')
BEGIN
    ALTER TABLE [dbo].[GrantProjectBudget] ADD [StrategicPriorityId] INT NULL;
    ALTER TABLE [dbo].[GrantProjectBudget] ADD CONSTRAINT [FK_GrantProjectBudget_StrategicPriority] FOREIGN KEY ([StrategicPriorityId]) REFERENCES [dbo].[StrategicPriority]([Id]);
    CREATE NONCLUSTERED INDEX [IX_GrantProjectBudget_StrategicPriorityId] ON [dbo].[GrantProjectBudget]([StrategicPriorityId]);
    PRINT 'Added StrategicPriorityId to GrantProjectBudget.';
END;
