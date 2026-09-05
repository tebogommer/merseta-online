-- ==============================================================================
-- V2026_21_Wizard_Draft_Session_Persistence.sql
-- Wizard Draft Persistence & Resume Lifecycle Engine
-- 1. Creates WizardDraftSession table for storing serialised in-progress wizard drafts.
-- 2. Enforces non-temporal transient lifecycle for drafts with auto-expiry.
-- 3. Indexes CandidateKey, UserId, and OrganisationId for fast lookups.
-- ==============================================================================

SET ANSI_NULLS ON;
SET QUOTED_IDENTIFIER ON;
GO

IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = N'WizardDraftSession' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE [dbo].[WizardDraftSession] (
        [Id] INT IDENTITY(1,1) NOT NULL,
        [DraftKey] NVARCHAR(100) NOT NULL,
        [CandidateKey] NVARCHAR(50) NOT NULL,
        [WizardTitle] NVARCHAR(150) NOT NULL,
        [Route] NVARCHAR(250) NOT NULL,
        [UserId] NVARCHAR(100) NOT NULL,
        [OrganisationId] INT NULL,
        [CurrentStepIndex] INT NOT NULL CONSTRAINT [DF_WizardDraftSession_Step] DEFAULT 0,
        [CompletedStepCount] INT NOT NULL CONSTRAINT [DF_WizardDraftSession_Completed] DEFAULT 0,
        [TotalStepCount] INT NOT NULL CONSTRAINT [DF_WizardDraftSession_Total] DEFAULT 5,
        [DraftModelJson] NVARCHAR(MAX) NOT NULL,
        [Status] NVARCHAR(30) NOT NULL CONSTRAINT [DF_WizardDraftSession_Status] DEFAULT N'Active',
        [ExpiresAtUtc] DATETIME2 NOT NULL,
        [IsActive] BIT NOT NULL CONSTRAINT [DF_WizardDraftSession_IsActive] DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_WizardDraftSession_CreatedAt] DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NOT NULL CONSTRAINT [DF_WizardDraftSession_CreatedBy] DEFAULT N'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT [PK_WizardDraftSession] PRIMARY KEY CLUSTERED ([Id] ASC)
    );
    PRINT 'Created table dbo.WizardDraftSession.';
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'UQ_WizardDraftSession_DraftKey' AND object_id = OBJECT_ID(N'dbo.WizardDraftSession'))
BEGIN
    CREATE UNIQUE NONCLUSTERED INDEX [UQ_WizardDraftSession_DraftKey]
    ON [dbo].[WizardDraftSession] ([DraftKey]);
    PRINT 'Created unique index UQ_WizardDraftSession_DraftKey.';
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'IX_WizardDraftSession_Lookup' AND object_id = OBJECT_ID(N'dbo.WizardDraftSession'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_WizardDraftSession_Lookup]
    ON [dbo].[WizardDraftSession] ([UserId], [CandidateKey], [IsActive])
    INCLUDE ([DraftKey], [CurrentStepIndex], [ExpiresAtUtc], [ModifiedAt]);
    PRINT 'Created index IX_WizardDraftSession_Lookup.';
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'IX_WizardDraftSession_Organisation' AND object_id = OBJECT_ID(N'dbo.WizardDraftSession'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_WizardDraftSession_Organisation]
    ON [dbo].[WizardDraftSession] ([OrganisationId])
    WHERE [OrganisationId] IS NOT NULL;
    PRINT 'Created index IX_WizardDraftSession_Organisation.';
END
GO
