-- ====================================================================================================
-- MERSETA NSDMS ENTERPRISE DDL MIGRATION: PHASE 4
-- Script: V2026_14_Phase4_Etqa_ReRegistration.sql
-- Description: Creates tables for ETQA Assessor/Moderator 3-Year Re-registration & CPD Tracking.
-- Conventions: Singular PascalCase, INT IDENTITY PK, audit columns, indexed FKs.
-- ====================================================================================================

-- 1. AssessorReRegistrationApplication
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'AssessorReRegistrationApplication' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE dbo.AssessorReRegistrationApplication (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_AssessorReRegistrationApplication PRIMARY KEY CLUSTERED,
        EtqaAssessorId INT NOT NULL,
        ApplicationReferenceNumber NVARCHAR(50) NOT NULL CONSTRAINT UQ_AssessorReReg_Ref UNIQUE,
        ApplicationTypeCode NVARCHAR(50) NOT NULL CONSTRAINT DF_AssessorReReg_Type DEFAULT ('ReRegistration'),
        CurrentExpirationDate DATETIME2(7) NOT NULL,
        ProposedNewExpirationDate DATETIME2(7) NOT NULL,
        CpdPointsAccumulated INT NOT NULL CONSTRAINT DF_AssessorReReg_CpdPoints DEFAULT (0),
        CpdPortfolioSummary NVARCHAR(MAX) NULL,
        ScopeConfirmationJson NVARCHAR(MAX) NOT NULL CONSTRAINT DF_AssessorReReg_ScopeJson DEFAULT ('[]'),
        ReviewStatusCode NVARCHAR(50) NOT NULL CONSTRAINT DF_AssessorReReg_Status DEFAULT ('Draft'),
        CommitteeDecisionNumber NVARCHAR(50) NULL,
        AdjudicationDate DATETIME2(7) NULL,
        AdjudicatedByUserId NVARCHAR(100) NULL,
        AdjudicationNotes NVARCHAR(MAX) NULL,
        DigitalSecuritySeal NVARCHAR(64) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_AssessorReReg_CreatedAt DEFAULT (SYSUTCDATETIME()),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_AssessorReReg_CreatedBy DEFAULT ('SYSTEM'),
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_AssessorReReg_EtqaAssessor FOREIGN KEY (EtqaAssessorId) REFERENCES dbo.EtqaAssessor (id) ON DELETE CASCADE
    );

    CREATE NONCLUSTERED INDEX IX_AssessorReReg_AssessorId ON dbo.AssessorReRegistrationApplication (EtqaAssessorId);
    CREATE NONCLUSTERED INDEX IX_AssessorReReg_Status ON dbo.AssessorReRegistrationApplication (ReviewStatusCode);
    CREATE NONCLUSTERED INDEX IX_AssessorReReg_Ref ON dbo.AssessorReRegistrationApplication (ApplicationReferenceNumber);
END
GO

-- 2. AssessorCpdActivity
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'AssessorCpdActivity' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE dbo.AssessorCpdActivity (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_AssessorCpdActivity PRIMARY KEY CLUSTERED,
        AssessorReRegistrationApplicationId INT NOT NULL,
        ActivityDate DATETIME2(7) NOT NULL,
        ActivityTitle NVARCHAR(200) NOT NULL,
        ActivityCategory NVARCHAR(50) NOT NULL CONSTRAINT DF_AssessorCpd_Cat DEFAULT ('IndustryPractice'),
        PointsClaimed INT NOT NULL CONSTRAINT DF_AssessorCpd_PointsClaimed DEFAULT (0),
        PointsApproved INT NOT NULL CONSTRAINT DF_AssessorCpd_PointsApproved DEFAULT (0),
        EvidenceDocumentRef NVARCHAR(250) NULL,
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_AssessorCpd_CreatedAt DEFAULT (SYSUTCDATETIME()),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_AssessorCpd_CreatedBy DEFAULT ('SYSTEM'),
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_AssessorCpd_Application FOREIGN KEY (AssessorReRegistrationApplicationId) REFERENCES dbo.AssessorReRegistrationApplication (id) ON DELETE CASCADE
    );

    CREATE NONCLUSTERED INDEX IX_AssessorCpd_AppId ON dbo.AssessorCpdActivity (AssessorReRegistrationApplicationId);
    CREATE NONCLUSTERED INDEX IX_AssessorCpd_Category ON dbo.AssessorCpdActivity (ActivityCategory);
END
GO
