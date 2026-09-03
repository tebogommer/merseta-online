-- ====================================================================================================
-- MERSETA NSDMS ENTERPRISE DDL MIGRATION: PHASE 7
-- Script: V2026_16_Phase7_Sdp_Campus_Assessor.sql
-- Description: Creates TrainingProviderCampus and TrainingProviderAssessorLink tables.
-- Conventions: Singular PascalCase, INT IDENTITY PK, audit columns, indexed FKs.
-- ====================================================================================================

-- 1. TrainingProviderCampus
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'TrainingProviderCampus' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE dbo.TrainingProviderCampus (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_TrainingProviderCampus PRIMARY KEY CLUSTERED,
        TrainingProviderId INT NOT NULL,
        CampusName NVARCHAR(150) NOT NULL,
        CampusCode NVARCHAR(50) NOT NULL,
        PhysicalAddressLine1 NVARCHAR(200) NULL,
        PhysicalAddressLine2 NVARCHAR(200) NULL,
        City NVARCHAR(100) NULL,
        ProvinceCode NVARCHAR(20) NULL,
        PostalCode NVARCHAR(20) NULL,
        ContactPersonName NVARCHAR(150) NULL,
        ContactEmail NVARCHAR(150) NULL,
        ContactPhone NVARCHAR(50) NULL,
        IsPrimarySite BIT NOT NULL CONSTRAINT DF_SdpCampus_IsPrimary DEFAULT (0),
        Status NVARCHAR(50) NOT NULL CONSTRAINT DF_SdpCampus_Status DEFAULT ('Active'),
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_SdpCampus_CreatedAt DEFAULT (SYSUTCDATETIME()),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_SdpCampus_CreatedBy DEFAULT ('SYSTEM'),
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_TrainingProviderCampus_Provider FOREIGN KEY (TrainingProviderId) REFERENCES dbo.TrainingProvider (id) ON DELETE CASCADE
    );

    CREATE NONCLUSTERED INDEX IX_TrainingProviderCampus_ProviderId ON dbo.TrainingProviderCampus (TrainingProviderId);
    CREATE NONCLUSTERED INDEX IX_TrainingProviderCampus_Code ON dbo.TrainingProviderCampus (CampusCode);
END
GO

-- 2. TrainingProviderAssessorLink
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'TrainingProviderAssessorLink' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE dbo.TrainingProviderAssessorLink (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_TrainingProviderAssessorLink PRIMARY KEY CLUSTERED,
        TrainingProviderId INT NOT NULL,
        TrainingProviderCampusId INT NULL,
        EtqaAssessorId INT NOT NULL,
        RoleTypeCode NVARCHAR(50) NOT NULL CONSTRAINT DF_SdpAssessor_Role DEFAULT ('Assessor'),
        StartDate DATETIME2(7) NOT NULL CONSTRAINT DF_SdpAssessor_StartDate DEFAULT (SYSUTCDATETIME()),
        EndDate DATETIME2(7) NULL,
        Status NVARCHAR(50) NOT NULL CONSTRAINT DF_SdpAssessor_Status DEFAULT ('Active'),
        VerificationNotes NVARCHAR(MAX) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_SdpAssessor_CreatedAt DEFAULT (SYSUTCDATETIME()),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_SdpAssessor_CreatedBy DEFAULT ('SYSTEM'),
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_TrainingProviderAssessorLink_Provider FOREIGN KEY (TrainingProviderId) REFERENCES dbo.TrainingProvider (id) ON DELETE CASCADE,
        CONSTRAINT FK_TrainingProviderAssessorLink_Campus FOREIGN KEY (TrainingProviderCampusId) REFERENCES dbo.TrainingProviderCampus (id) ON DELETE NO ACTION,
        CONSTRAINT FK_TrainingProviderAssessorLink_Assessor FOREIGN KEY (EtqaAssessorId) REFERENCES dbo.EtqaAssessor (id) ON DELETE CASCADE
    );

    CREATE NONCLUSTERED INDEX IX_TrainingProviderAssessorLink_ProviderId ON dbo.TrainingProviderAssessorLink (TrainingProviderId);
    CREATE NONCLUSTERED INDEX IX_TrainingProviderAssessorLink_CampusId ON dbo.TrainingProviderAssessorLink (TrainingProviderCampusId);
    CREATE NONCLUSTERED INDEX IX_TrainingProviderAssessorLink_AssessorId ON dbo.TrainingProviderAssessorLink (EtqaAssessorId);
END
GO
