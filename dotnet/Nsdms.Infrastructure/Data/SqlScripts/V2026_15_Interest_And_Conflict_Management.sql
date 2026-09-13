-- =========================================================================================
-- MerSETA NSDMS Enterprise SQL Migration: Module 18 - Interest and Conflict Management
-- Standards: Singular PascalCase, snake_case/PascalCase columns, covering FK indexes, RCSI compliant
-- =========================================================================================

-- 1. OrganisationGovernanceMember
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'OrganisationGovernanceMember')
BEGIN
    CREATE TABLE [dbo].[OrganisationGovernanceMember] (
        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_OrganisationGovernanceMember] PRIMARY KEY CLUSTERED,
        [OrganisationId] INT NOT NULL,
        [PersonId] INT NOT NULL,
        [GovernanceRoleCode] NVARCHAR(50) NOT NULL,
        [ShareholdingPercentage] DECIMAL(5,2) NOT NULL CONSTRAINT [DF_OrganisationGovernanceMember_ShareholdingPercentage] DEFAULT (0.00),
        [HasVotingRights] BIT NOT NULL CONSTRAINT [DF_OrganisationGovernanceMember_HasVotingRights] DEFAULT (1),
        [AppointmentDate] DATETIME2 NULL,
        [ResignationDate] DATETIME2 NULL,
        [CipcRegistered] BIT NOT NULL CONSTRAINT [DF_OrganisationGovernanceMember_CipcRegistered] DEFAULT (1),
        [IdVerified] BIT NOT NULL CONSTRAINT [DF_OrganisationGovernanceMember_IdVerified] DEFAULT (1),
        [IsActive] BIT NOT NULL CONSTRAINT [DF_OrganisationGovernanceMember_IsActive] DEFAULT (1),
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_OrganisationGovernanceMember_CreatedAt] DEFAULT (SYSUTCDATETIME()),
        [CreatedBy] NVARCHAR(150) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(150) NULL,
        CONSTRAINT [FK_OrganisationGovernanceMember_Organisation] FOREIGN KEY ([OrganisationId]) REFERENCES [dbo].[Organisation] ([Id]) ON DELETE CASCADE,
        CONSTRAINT [FK_OrganisationGovernanceMember_Person] FOREIGN KEY ([PersonId]) REFERENCES [dbo].[Person] ([Id])
    );

    CREATE NONCLUSTERED INDEX [IX_OrganisationGovernanceMember_OrganisationId] ON [dbo].[OrganisationGovernanceMember] ([OrganisationId]);
    CREATE NONCLUSTERED INDEX [IX_OrganisationGovernanceMember_PersonId] ON [dbo].[OrganisationGovernanceMember] ([PersonId]);
    CREATE NONCLUSTERED INDEX [IX_OrganisationGovernanceMember_GovernanceRoleCode] ON [dbo].[OrganisationGovernanceMember] ([GovernanceRoleCode]);
    CREATE NONCLUSTERED INDEX [IX_OrganisationGovernanceMember_Org_Person_Role] ON [dbo].[OrganisationGovernanceMember] ([OrganisationId], [PersonId], [GovernanceRoleCode]);
END;

-- 2. InstitutionalAffiliation
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'InstitutionalAffiliation')
BEGIN
    CREATE TABLE [dbo].[InstitutionalAffiliation] (
        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_InstitutionalAffiliation] PRIMARY KEY CLUSTERED,
        [PersonId] INT NOT NULL,
        [AffiliationTypeCode] NVARCHAR(50) NOT NULL,
        [DepartmentOrCommittee] NVARCHAR(150) NOT NULL,
        [Designation] NVARCHAR(150) NOT NULL,
        [EmployeeNumber] NVARCHAR(50) NULL,
        [IsIndependentMember] BIT NOT NULL CONSTRAINT [DF_InstitutionalAffiliation_IsIndependentMember] DEFAULT (0),
        [TermStartDate] DATETIME2 NULL,
        [TermEndDate] DATETIME2 NULL,
        [IsActive] BIT NOT NULL CONSTRAINT [DF_InstitutionalAffiliation_IsActive] DEFAULT (1),
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_InstitutionalAffiliation_CreatedAt] DEFAULT (SYSUTCDATETIME()),
        [CreatedBy] NVARCHAR(150) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(150) NULL,
        CONSTRAINT [FK_InstitutionalAffiliation_Person] FOREIGN KEY ([PersonId]) REFERENCES [dbo].[Person] ([Id]) ON DELETE CASCADE
    );

    CREATE NONCLUSTERED INDEX [IX_InstitutionalAffiliation_PersonId] ON [dbo].[InstitutionalAffiliation] ([PersonId]);
    CREATE NONCLUSTERED INDEX [IX_InstitutionalAffiliation_AffiliationTypeCode] ON [dbo].[InstitutionalAffiliation] ([AffiliationTypeCode]);
    CREATE NONCLUSTERED INDEX [IX_InstitutionalAffiliation_IsActive] ON [dbo].[InstitutionalAffiliation] ([IsActive]);
END;

-- 3. InterestDeclaration
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'InterestDeclaration')
BEGIN
    CREATE TABLE [dbo].[InterestDeclaration] (
        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_InterestDeclaration] PRIMARY KEY CLUSTERED,
        [PersonId] INT NOT NULL,
        [FinancialYearId] INT NULL,
        [DeclarationPeriodYear] NVARCHAR(10) NOT NULL,
        [DeclarationTypeCode] NVARCHAR(50) NOT NULL,
        [MeetingOrProjectRef] NVARCHAR(150) NULL,
        [StatusCode] NVARCHAR(50) NOT NULL CONSTRAINT [DF_InterestDeclaration_StatusCode] DEFAULT ('SUBMITTED'),
        [HasConflictsToDeclare] BIT NOT NULL CONSTRAINT [DF_InterestDeclaration_HasConflictsToDeclare] DEFAULT (0),
        [GeneralDeclarationNotes] NVARCHAR(2000) NULL,
        [DigitalSignatureSeal] NVARCHAR(100) NULL,
        [CertifiedAt] DATETIME2 NULL,
        [CertifiedByUserId] NVARCHAR(150) NULL,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_InterestDeclaration_CreatedAt] DEFAULT (SYSUTCDATETIME()),
        [CreatedBy] NVARCHAR(150) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(150) NULL,
        CONSTRAINT [FK_InterestDeclaration_Person] FOREIGN KEY ([PersonId]) REFERENCES [dbo].[Person] ([Id]) ON DELETE CASCADE,
        CONSTRAINT [FK_InterestDeclaration_FinancialYear] FOREIGN KEY ([FinancialYearId]) REFERENCES [dbo].[FinancialYear] ([Id]) ON DELETE SET NULL
    );

    CREATE NONCLUSTERED INDEX [IX_InterestDeclaration_PersonId] ON [dbo].[InterestDeclaration] ([PersonId]);
    CREATE NONCLUSTERED INDEX [IX_InterestDeclaration_FinancialYearId] ON [dbo].[InterestDeclaration] ([FinancialYearId]);
    CREATE NONCLUSTERED INDEX [IX_InterestDeclaration_Period_Status] ON [dbo].[InterestDeclaration] ([DeclarationPeriodYear], [StatusCode]);
    CREATE NONCLUSTERED INDEX [IX_InterestDeclaration_DeclarationTypeCode] ON [dbo].[InterestDeclaration] ([DeclarationTypeCode]);
END;

-- 4. InterestDeclarationItem
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'InterestDeclarationItem')
BEGIN
    CREATE TABLE [dbo].[InterestDeclarationItem] (
        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_InterestDeclarationItem] PRIMARY KEY CLUSTERED,
        [InterestDeclarationId] INT NOT NULL,
        [OrganisationName] NVARCHAR(250) NOT NULL,
        [RegistrationOrSdlNumber] NVARCHAR(50) NULL,
        [NatureOfRelationship] NVARCHAR(50) NOT NULL,
        [InterestPercentage] DECIMAL(5,2) NULL,
        [AnnualRemunerationOrBenefit] DECIMAL(18,2) NULL,
        [IsApprovedExternalWork] BIT NOT NULL CONSTRAINT [DF_InterestDeclarationItem_IsApprovedExternalWork] DEFAULT (0),
        [ApprovalReference] NVARCHAR(100) NULL,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_InterestDeclarationItem_CreatedAt] DEFAULT (SYSUTCDATETIME()),
        [CreatedBy] NVARCHAR(150) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(150) NULL,
        CONSTRAINT [FK_InterestDeclarationItem_InterestDeclaration] FOREIGN KEY ([InterestDeclarationId]) REFERENCES [dbo].[InterestDeclaration] ([Id]) ON DELETE CASCADE
    );

    CREATE NONCLUSTERED INDEX [IX_InterestDeclarationItem_DeclarationId] ON [dbo].[InterestDeclarationItem] ([InterestDeclarationId]);
END;

-- 5. ConflictFlag
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'ConflictFlag')
BEGIN
    CREATE TABLE [dbo].[ConflictFlag] (
        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_ConflictFlag] PRIMARY KEY CLUSTERED,
        [TargetOrganisationId] INT NULL,
        [TargetGrantApplicationId] INT NULL,
        [TargetTrainingProviderId] INT NULL,
        [PersonId] INT NOT NULL,
        [SeverityCode] NVARCHAR(50) NOT NULL,
        [ConflictCategoryCode] NVARCHAR(50) NOT NULL,
        [Title] NVARCHAR(250) NOT NULL,
        [Description] NVARCHAR(2000) NOT NULL,
        [DetectedAt] DATETIME2 NOT NULL CONSTRAINT [DF_ConflictFlag_DetectedAt] DEFAULT (SYSUTCDATETIME()),
        [ResolutionStatusCode] NVARCHAR(50) NOT NULL CONSTRAINT [DF_ConflictFlag_ResolutionStatusCode] DEFAULT ('OPEN'),
        [ResolutionNotes] NVARCHAR(2000) NULL,
        [ClearedByUserId] NVARCHAR(150) NULL,
        [ClearedAt] DATETIME2 NULL,
        [ClearanceAuthorityRole] NVARCHAR(100) NULL,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_ConflictFlag_CreatedAt] DEFAULT (SYSUTCDATETIME()),
        [CreatedBy] NVARCHAR(150) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(150) NULL,
        CONSTRAINT [FK_ConflictFlag_Organisation] FOREIGN KEY ([TargetOrganisationId]) REFERENCES [dbo].[Organisation] ([Id]) ON DELETE SET NULL,
        CONSTRAINT [FK_ConflictFlag_GrantApplication] FOREIGN KEY ([TargetGrantApplicationId]) REFERENCES [dbo].[GrantApplication] ([Id]) ON DELETE SET NULL,
        CONSTRAINT [FK_ConflictFlag_TrainingProvider] FOREIGN KEY ([TargetTrainingProviderId]) REFERENCES [dbo].[TrainingProvider] ([Id]) ON DELETE SET NULL,
        CONSTRAINT [FK_ConflictFlag_Person] FOREIGN KEY ([PersonId]) REFERENCES [dbo].[Person] ([Id])
    );

    CREATE NONCLUSTERED INDEX [IX_ConflictFlag_TargetOrganisationId] ON [dbo].[ConflictFlag] ([TargetOrganisationId]);
    CREATE NONCLUSTERED INDEX [IX_ConflictFlag_TargetGrantApplicationId] ON [dbo].[ConflictFlag] ([TargetGrantApplicationId]);
    CREATE NONCLUSTERED INDEX [IX_ConflictFlag_TargetTrainingProviderId] ON [dbo].[ConflictFlag] ([TargetTrainingProviderId]);
    CREATE NONCLUSTERED INDEX [IX_ConflictFlag_PersonId] ON [dbo].[ConflictFlag] ([PersonId]);
    CREATE NONCLUSTERED INDEX [IX_ConflictFlag_SeverityCode] ON [dbo].[ConflictFlag] ([SeverityCode]);
    CREATE NONCLUSTERED INDEX [IX_ConflictFlag_Category_Status] ON [dbo].[ConflictFlag] ([ConflictCategoryCode], [ResolutionStatusCode]);
    CREATE NONCLUSTERED INDEX [IX_ConflictFlag_DetectedAt] ON [dbo].[ConflictFlag] ([DetectedAt]);
END;
