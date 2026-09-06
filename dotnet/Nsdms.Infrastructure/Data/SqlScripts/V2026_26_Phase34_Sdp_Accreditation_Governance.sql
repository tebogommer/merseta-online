-- ==============================================================================================
-- V2026_26_Phase34_Sdp_Accreditation_Governance.sql
-- MerSETA NSDMS 2.0: Skills Development Provider (SDP) Accreditation Statutory Governance Alignment
-- Reference: Signed Specification (21 Feb 2023) - SDP Application Use Case (MerSeta\NSDMS\LMS\LR\01)
-- ==============================================================================================

-- 1. TrainingProviderCampus extensions (GPS Coordinates, Local Municipality)
IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.TrainingProviderCampus') AND name = 'GpsCoordinates')
BEGIN
    ALTER TABLE dbo.TrainingProviderCampus ADD GpsCoordinates NVARCHAR(50) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.TrainingProviderCampus') AND name = 'Latitude')
BEGIN
    ALTER TABLE dbo.TrainingProviderCampus ADD Latitude DECIMAL(9,6) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.TrainingProviderCampus') AND name = 'Longitude')
BEGIN
    ALTER TABLE dbo.TrainingProviderCampus ADD Longitude DECIMAL(9,6) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.TrainingProviderCampus') AND name = 'LocalMunicipality')
BEGIN
    ALTER TABLE dbo.TrainingProviderCampus ADD LocalMunicipality NVARCHAR(100) NULL;
END
GO

-- 2. TrainingProvider statutory accreditation extensions
IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.TrainingProvider') AND name = 'AccreditationStream')
BEGIN
    ALTER TABLE dbo.TrainingProvider ADD AccreditationStream NVARCHAR(50) NOT NULL CONSTRAINT DF_TrainingProvider_AccreditationStream DEFAULT 'PrimaryAccreditation';
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.TrainingProvider') AND name = 'PrimaryEtqaName')
BEGIN
    ALTER TABLE dbo.TrainingProvider ADD PrimaryEtqaName NVARCHAR(100) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.TrainingProvider') AND name = 'PrimaryAccreditationNumber')
BEGIN
    ALTER TABLE dbo.TrainingProvider ADD PrimaryAccreditationNumber NVARCHAR(50) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.TrainingProvider') AND name = 'PrimaryAccreditationStartDate')
BEGIN
    ALTER TABLE dbo.TrainingProvider ADD PrimaryAccreditationStartDate DATETIME2 NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.TrainingProvider') AND name = 'PrimaryAccreditationEndDate')
BEGIN
    ALTER TABLE dbo.TrainingProvider ADD PrimaryAccreditationEndDate DATETIME2 NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.TrainingProvider') AND name = 'NambRegistrationNumber')
BEGIN
    ALTER TABLE dbo.TrainingProvider ADD NambRegistrationNumber NVARCHAR(50) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.TrainingProvider') AND name = 'NambRegistrationStartDate')
BEGIN
    ALTER TABLE dbo.TrainingProvider ADD NambRegistrationStartDate DATETIME2 NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.TrainingProvider') AND name = 'NambRegistrationEndDate')
BEGIN
    ALTER TABLE dbo.TrainingProvider ADD NambRegistrationEndDate DATETIME2 NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.TrainingProvider') AND name = 'EtqaCommitteeDecisionNumber')
BEGIN
    ALTER TABLE dbo.TrainingProvider ADD EtqaCommitteeDecisionNumber NVARCHAR(50) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.TrainingProvider') AND name = 'EtqaCommitteeMeetingDate')
BEGIN
    ALTER TABLE dbo.TrainingProvider ADD EtqaCommitteeMeetingDate DATETIME2 NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.TrainingProvider') AND name = 'ReAccreditationUnderway')
BEGIN
    ALTER TABLE dbo.TrainingProvider ADD ReAccreditationUnderway BIT NOT NULL CONSTRAINT DF_TrainingProvider_ReAccreditationUnderway DEFAULT 0;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.TrainingProvider') AND name = 'ReAccreditationEffectiveDate')
BEGIN
    ALTER TABLE dbo.TrainingProvider ADD ReAccreditationEffectiveDate DATETIME2 NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.TrainingProvider') AND name = 'InspectionDueDate')
BEGIN
    ALTER TABLE dbo.TrainingProvider ADD InspectionDueDate DATETIME2 NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.TrainingProvider') AND name = 'DigitalSecuritySeal')
BEGIN
    ALTER TABLE dbo.TrainingProvider ADD DigitalSecuritySeal NVARCHAR(64) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_TrainingProvider_AccreditationStream' AND object_id = OBJECT_ID('dbo.TrainingProvider'))
BEGIN
    CREATE INDEX IX_TrainingProvider_AccreditationStream ON dbo.TrainingProvider(AccreditationStream);
END
GO

-- 3. TrainingProviderSelfEvaluation Table (Two-Stage QMS Self-Evaluation Audit)
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'TrainingProviderSelfEvaluation')
BEGIN
    CREATE TABLE dbo.TrainingProviderSelfEvaluation (
        Id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_TrainingProviderSelfEvaluation PRIMARY KEY CLUSTERED,
        TrainingProviderId INT NOT NULL CONSTRAINT FK_TrainingProviderSelfEvaluation_TrainingProvider REFERENCES dbo.TrainingProvider(Id) ON DELETE CASCADE,
        CriteriaCode NVARCHAR(50) NOT NULL,
        CriteriaCategory NVARCHAR(100) NOT NULL,
        CriteriaDescription NVARCHAR(500) NOT NULL,
        IsCompliant BIT NOT NULL CONSTRAINT DF_TrainingProviderSelfEvaluation_IsCompliant DEFAULT 1,
        DocumentReferenceNumber NVARCHAR(150) NULL,
        ApplicantComments NVARCHAR(1000) NULL,
        AssessorVerified BIT NULL,
        AssessorFindings NVARCHAR(1000) NULL,
        CreatedAt DATETIME2 NOT NULL CONSTRAINT DF_TrainingProviderSelfEvaluation_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_TrainingProviderSelfEvaluation_CreatedBy DEFAULT 'SYSTEM',
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL
    );

    CREATE INDEX IX_TrainingProviderSelfEvaluation_TrainingProviderId ON dbo.TrainingProviderSelfEvaluation(TrainingProviderId);
    CREATE INDEX IX_TrainingProviderSelfEvaluation_CriteriaCode ON dbo.TrainingProviderSelfEvaluation(CriteriaCode);
END
GO

-- 4. TrainingProviderContact Table (Multi-Contact Quorum & Banking Confirmation)
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'TrainingProviderContact')
BEGIN
    CREATE TABLE dbo.TrainingProviderContact (
        Id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_TrainingProviderContact PRIMARY KEY CLUSTERED,
        TrainingProviderId INT NOT NULL CONSTRAINT FK_TrainingProviderContact_TrainingProvider REFERENCES dbo.TrainingProvider(Id) ON DELETE CASCADE,
        PersonId INT NULL CONSTRAINT FK_TrainingProviderContact_Person REFERENCES dbo.Person(Id) ON DELETE SET NULL,
        ContactDesignation NVARCHAR(50) NOT NULL,
        Title NVARCHAR(20) NULL,
        FirstName NVARCHAR(100) NOT NULL,
        LastName NVARCHAR(100) NOT NULL,
        IdOrPassportNumber NVARCHAR(30) NULL,
        Email NVARCHAR(150) NOT NULL,
        CellNumber NVARCHAR(30) NOT NULL,
        IsBankingConfirmationAuthorized BIT NOT NULL CONSTRAINT DF_TrainingProviderContact_Banking DEFAULT 0,
        IsActive BIT NOT NULL CONSTRAINT DF_TrainingProviderContact_IsActive DEFAULT 1,
        CreatedAt DATETIME2 NOT NULL CONSTRAINT DF_TrainingProviderContact_CreatedAt DEFAULT SYSUTCDATETIME(),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_TrainingProviderContact_CreatedBy DEFAULT 'SYSTEM',
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL
    );

    CREATE INDEX IX_TrainingProviderContact_TrainingProviderId ON dbo.TrainingProviderContact(TrainingProviderId);
    CREATE INDEX IX_TrainingProviderContact_PersonId ON dbo.TrainingProviderContact(PersonId);
    CREATE INDEX IX_TrainingProviderContact_Email ON dbo.TrainingProviderContact(Email);
END
GO
