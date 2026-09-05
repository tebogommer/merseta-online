-- ==============================================================================
-- V2026_19_Vertical_Partitioning.sql
-- Phase 3 Enterprise Vertical Partitioning: Person Core -> Satellite Tables
-- 1. Creates PersonContact (residential, postal, telecommunications)
-- 2. Creates PersonDemographics (demographic, equity, language, POPIA metadata)
-- 3. Creates PersonDisabilityRating (POPIA special personal data isolation)
-- 4. Idempotently backfills existing Person records into satellites
-- 5. Creates Zero-Breaking SQL Views: vw_PersonComplete, vw_PersonSetmis
-- ==============================================================================

-- 1. Satellite: PersonContact
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = N'PersonContact' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE [dbo].[PersonContact] (
        [Id] INT IDENTITY(1,1) NOT NULL,
        [PersonId] INT NOT NULL,
        [Email] NVARCHAR(150) NULL,
        [PhoneNumber] NVARCHAR(30) NULL,
        [CellNumber] NVARCHAR(30) NULL,
        [FaxNumber] NVARCHAR(30) NULL,
        [PhysicalAddress] NVARCHAR(500) NULL,
        [PhysicalAddressPostalCode] NVARCHAR(20) NULL,
        [PostalAddress] NVARCHAR(500) NULL,
        [PostalAddressPostalCode] NVARCHAR(20) NULL,
        [ProvinceCode] NVARCHAR(15) NULL,
        [StatssaAreaCode] NVARCHAR(50) NULL,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT DF_PersonContact_CreatedAt DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT PK_PersonContact PRIMARY KEY CLUSTERED ([Id] ASC),
        CONSTRAINT FK_PersonContact_Person FOREIGN KEY ([PersonId]) REFERENCES [dbo].[Person] ([Id]) ON DELETE CASCADE
    );
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'IX_PersonContact_PersonId' AND object_id = OBJECT_ID(N'dbo.PersonContact'))
BEGIN
    CREATE UNIQUE NONCLUSTERED INDEX [IX_PersonContact_PersonId] ON [dbo].[PersonContact] ([PersonId]);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'IX_PersonContact_Email' AND object_id = OBJECT_ID(N'dbo.PersonContact'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_PersonContact_Email] ON [dbo].[PersonContact] ([Email]);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'IX_PersonContact_ProvinceCode' AND object_id = OBJECT_ID(N'dbo.PersonContact'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_PersonContact_ProvinceCode] ON [dbo].[PersonContact] ([ProvinceCode]);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'IX_PersonContact_StatssaAreaCode' AND object_id = OBJECT_ID(N'dbo.PersonContact'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_PersonContact_StatssaAreaCode] ON [dbo].[PersonContact] ([StatssaAreaCode]);
END
GO

-- 2. Satellite: PersonDemographics
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = N'PersonDemographics' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE [dbo].[PersonDemographics] (
        [Id] INT IDENTITY(1,1) NOT NULL,
        [PersonId] INT NOT NULL,
        [EquityCode] NVARCHAR(15) NULL,
        [DisabilityCode] NVARCHAR(15) NULL,
        [NationalityCode] NVARCHAR(15) NULL,
        [HomeLanguageCode] NVARCHAR(15) NULL,
        [CitizenStatusCode] NVARCHAR(15) NULL,
        [PopiActStatusId] NVARCHAR(10) NULL CONSTRAINT DF_PersonDemographics_PopiStatus DEFAULT '01',
        [PopiActConsentDate] DATETIME2 NULL,
        [LastSchoolEmisNumber] NVARCHAR(50) NULL,
        [LastSchoolYear] NVARCHAR(10) NULL,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT DF_PersonDemographics_CreatedAt DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT PK_PersonDemographics PRIMARY KEY CLUSTERED ([Id] ASC),
        CONSTRAINT FK_PersonDemographics_Person FOREIGN KEY ([PersonId]) REFERENCES [dbo].[Person] ([Id]) ON DELETE CASCADE
    );
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'IX_PersonDemographics_PersonId' AND object_id = OBJECT_ID(N'dbo.PersonDemographics'))
BEGIN
    CREATE UNIQUE NONCLUSTERED INDEX [IX_PersonDemographics_PersonId] ON [dbo].[PersonDemographics] ([PersonId]);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'IX_PersonDemographics_EquityCode' AND object_id = OBJECT_ID(N'dbo.PersonDemographics'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_PersonDemographics_EquityCode] ON [dbo].[PersonDemographics] ([EquityCode]);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'IX_PersonDemographics_NationalityCode' AND object_id = OBJECT_ID(N'dbo.PersonDemographics'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_PersonDemographics_NationalityCode] ON [dbo].[PersonDemographics] ([NationalityCode]);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'IX_PersonDemographics_PopiActStatusId' AND object_id = OBJECT_ID(N'dbo.PersonDemographics'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_PersonDemographics_PopiActStatusId] ON [dbo].[PersonDemographics] ([PopiActStatusId]);
END
GO

-- 3. Satellite: PersonDisabilityRating (POPIA Special Personal Data)
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = N'PersonDisabilityRating' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    CREATE TABLE [dbo].[PersonDisabilityRating] (
        [Id] INT IDENTITY(1,1) NOT NULL,
        [PersonId] INT NOT NULL,
        [DisabilityCode] NVARCHAR(15) NULL CONSTRAINT DF_PersonDisabilityRating_DisabilityCode DEFAULT '00',
        [SeeingRatingId] NVARCHAR(10) NULL CONSTRAINT DF_PersonDisabilityRating_Seeing DEFAULT '01',
        [HearingRatingId] NVARCHAR(10) NULL CONSTRAINT DF_PersonDisabilityRating_Hearing DEFAULT '01',
        [WalkingRatingId] NVARCHAR(10) NULL CONSTRAINT DF_PersonDisabilityRating_Walking DEFAULT '01',
        [RememberingRatingId] NVARCHAR(10) NULL CONSTRAINT DF_PersonDisabilityRating_Remembering DEFAULT '01',
        [CommunicatingRatingId] NVARCHAR(10) NULL CONSTRAINT DF_PersonDisabilityRating_Communicating DEFAULT '01',
        [SelfCareRatingId] NVARCHAR(10) NULL CONSTRAINT DF_PersonDisabilityRating_SelfCare DEFAULT '01',
        [DisabilitySupportNotes] NVARCHAR(1000) NULL,
        [IsDisabilityAssessed] BIT NOT NULL CONSTRAINT DF_PersonDisabilityRating_Assessed DEFAULT 0,
        [AssessedDate] DATETIME2 NULL,
        [AssessedBy] NVARCHAR(150) NULL,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT DF_PersonDisabilityRating_CreatedAt DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT PK_PersonDisabilityRating PRIMARY KEY CLUSTERED ([Id] ASC),
        CONSTRAINT FK_PersonDisabilityRating_Person FOREIGN KEY ([PersonId]) REFERENCES [dbo].[Person] ([Id]) ON DELETE CASCADE
    );
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'IX_PersonDisabilityRating_PersonId' AND object_id = OBJECT_ID(N'dbo.PersonDisabilityRating'))
BEGIN
    CREATE UNIQUE NONCLUSTERED INDEX [IX_PersonDisabilityRating_PersonId] ON [dbo].[PersonDisabilityRating] ([PersonId]);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'IX_PersonDisabilityRating_DisabilityCode' AND object_id = OBJECT_ID(N'dbo.PersonDisabilityRating'))
BEGIN
    CREATE NONCLUSTERED INDEX [IX_PersonDisabilityRating_DisabilityCode] ON [dbo].[PersonDisabilityRating] ([DisabilityCode]);
END
GO

-- 4. Idempotent Data Backfill from Person to Satellite Tables
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'Person' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    -- Backfill PersonContact
    INSERT INTO [dbo].[PersonContact] (
        [PersonId], [Email], [PhoneNumber], [CellNumber], [FaxNumber],
        [PhysicalAddress], [PhysicalAddressPostalCode], [PostalAddress], [PostalAddressPostalCode],
        [ProvinceCode], [StatssaAreaCode], [CreatedAt], [CreatedBy]
    )
    SELECT 
        p.[Id], p.[Email], p.[PhoneNumber], p.[CellNumber], p.[FaxNumber],
        p.[PhysicalAddress], p.[PhysicalAddressPostalCode], p.[PostalAddress], p.[PostalAddressPostalCode],
        p.[ProvinceCode], p.[StatssaAreaCode], ISNULL(p.[CreatedAt], SYSUTCDATETIME()), ISNULL(p.[CreatedBy], 'MIGRATION')
    FROM [dbo].[Person] p
    WHERE NOT EXISTS (SELECT 1 FROM [dbo].[PersonContact] c WHERE c.[PersonId] = p.[Id]);

    -- Backfill PersonDemographics
    INSERT INTO [dbo].[PersonDemographics] (
        [PersonId], [EquityCode], [DisabilityCode], [NationalityCode], [HomeLanguageCode],
        [CitizenStatusCode], [PopiActStatusId], [PopiActConsentDate], [LastSchoolEmisNumber], [LastSchoolYear],
        [CreatedAt], [CreatedBy]
    )
    SELECT 
        p.[Id], p.[EquityCode], p.[DisabilityCode], p.[NationalityCode], p.[HomeLanguageCode],
        p.[CitizenStatusCode], ISNULL(p.[PopiActStatusId], '01'), p.[PopiActConsentDate], p.[LastSchoolEmisNumber], p.[LastSchoolYear],
        ISNULL(p.[CreatedAt], SYSUTCDATETIME()), ISNULL(p.[CreatedBy], 'MIGRATION')
    FROM [dbo].[Person] p
    WHERE NOT EXISTS (SELECT 1 FROM [dbo].[PersonDemographics] d WHERE d.[PersonId] = p.[Id]);

    -- Backfill PersonDisabilityRating
    INSERT INTO [dbo].[PersonDisabilityRating] (
        [PersonId], [DisabilityCode], [SeeingRatingId], [HearingRatingId], [WalkingRatingId],
        [RememberingRatingId], [CommunicatingRatingId], [SelfCareRatingId], [DisabilitySupportNotes],
        [IsDisabilityAssessed], [CreatedAt], [CreatedBy]
    )
    SELECT 
        p.[Id], ISNULL(p.[DisabilityCode], '00'), ISNULL(p.[SeeingRatingId], '01'), ISNULL(p.[HearingRatingId], '01'),
        ISNULL(p.[WalkingRatingId], '01'), ISNULL(p.[RememberingRatingId], '01'), ISNULL(p.[CommunicatingRatingId], '01'),
        ISNULL(p.[SelfCareRatingId], '01'), NULL, 0,
        ISNULL(p.[CreatedAt], SYSUTCDATETIME()), ISNULL(p.[CreatedBy], 'MIGRATION')
    FROM [dbo].[Person] p
    WHERE NOT EXISTS (SELECT 1 FROM [dbo].[PersonDisabilityRating] r WHERE r.[PersonId] = p.[Id]);
END
GO

-- 5. Zero-Breaking Backward-Compatible SQL Views

-- View 1: vw_PersonComplete (Full denormalized composite representation)
IF OBJECT_ID(N'dbo.vw_PersonComplete', 'V') IS NOT NULL
    DROP VIEW [dbo].[vw_PersonComplete];
GO

CREATE VIEW [dbo].[vw_PersonComplete]
AS
SELECT 
    p.[Id],
    p.[Title],
    p.[FirstName],
    p.[MiddleName],
    p.[LastName],
    p.[RsaIdNumber],
    p.[PassportNumber],
    p.[AlternateIdTypeId],
    p.[DateOfBirth],
    p.[Gender],
    p.[GenderCode],
    p.[IsSouthAfricanCitizen],
    p.[IsActive],
    p.[PreviousLastName],
    p.[PreviousAlternateId],
    p.[PreviousAlternateIdTypeId],
    p.[PreviousProviderCode],
    p.[PreviousProviderEtqaId],
    p.[CreatedAt],
    p.[CreatedBy],
    p.[ModifiedAt],
    p.[ModifiedBy],
    -- Contact Details (from PersonContact satellite)
    c.[Email] AS [ContactEmail],
    c.[PhoneNumber] AS [ContactPhoneNumber],
    c.[CellNumber] AS [ContactCellNumber],
    c.[FaxNumber] AS [ContactFaxNumber],
    c.[PhysicalAddress],
    c.[PhysicalAddressPostalCode],
    c.[PostalAddress],
    c.[PostalAddressPostalCode],
    c.[ProvinceCode],
    c.[StatssaAreaCode],
    -- Demographics & Statutory Metadata (from PersonDemographics satellite)
    d.[EquityCode],
    d.[NationalityCode],
    d.[HomeLanguageCode],
    d.[CitizenStatusCode],
    d.[PopiActStatusId],
    d.[PopiActConsentDate],
    d.[LastSchoolEmisNumber],
    d.[LastSchoolYear],
    -- Disability Functioning (from PersonDisabilityRating satellite)
    r.[DisabilityCode],
    r.[SeeingRatingId],
    r.[HearingRatingId],
    r.[WalkingRatingId],
    r.[RememberingRatingId],
    r.[CommunicatingRatingId],
    r.[SelfCareRatingId],
    r.[DisabilitySupportNotes],
    r.[IsDisabilityAssessed],
    r.[AssessedDate],
    r.[AssessedBy]
FROM [dbo].[Person] p
LEFT JOIN [dbo].[PersonContact] c ON p.[Id] = c.[PersonId]
LEFT JOIN [dbo].[PersonDemographics] d ON p.[Id] = d.[PersonId]
LEFT JOIN [dbo].[PersonDisabilityRating] r ON p.[Id] = r.[PersonId];
GO

-- View 2: vw_PersonSetmis (Optimized projection specifically for SETMIS File 400 Person Demographics flat file generation)
IF OBJECT_ID(N'dbo.vw_PersonSetmis', 'V') IS NOT NULL
    DROP VIEW [dbo].[vw_PersonSetmis];
GO

CREATE VIEW [dbo].[vw_PersonSetmis]
AS
SELECT 
    p.[Id] AS [PersonId],
    p.[AlternateIdTypeId] AS [NationalIdType],
    ISNULL(p.[RsaIdNumber], p.[PassportNumber]) AS [NationalId],
    p.[AlternateIdTypeId],
    p.[PassportNumber] AS [AlternateId],
    p.[FirstName],
    p.[MiddleName],
    p.[LastName],
    p.[PreviousLastName],
    p.[PreviousAlternateId],
    p.[PreviousAlternateIdTypeId],
    p.[DateOfBirth],
    p.[GenderCode],
    d.[CitizenStatusCode],
    d.[NationalityCode],
    d.[HomeLanguageCode],
    d.[EquityCode],
    r.[DisabilityCode],
    c.[ProvinceCode],
    c.[StatssaAreaCode],
    d.[PopiActStatusId],
    d.[PopiActConsentDate],
    d.[LastSchoolEmisNumber],
    d.[LastSchoolYear],
    p.[PreviousProviderCode],
    p.[PreviousProviderEtqaId],
    r.[SeeingRatingId],
    r.[HearingRatingId],
    r.[WalkingRatingId],
    r.[RememberingRatingId],
    r.[CommunicatingRatingId],
    r.[SelfCareRatingId]
FROM [dbo].[Person] p
LEFT JOIN [dbo].[PersonContact] c ON p.[Id] = c.[PersonId]
LEFT JOIN [dbo].[PersonDemographics] d ON p.[Id] = d.[PersonId]
LEFT JOIN [dbo].[PersonDisabilityRating] r ON p.[Id] = r.[PersonId];
GO
