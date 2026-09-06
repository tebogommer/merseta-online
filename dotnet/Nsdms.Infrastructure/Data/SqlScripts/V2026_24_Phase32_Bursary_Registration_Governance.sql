-- ====================================================================================================
-- MERSETA NSDMS ENTERPRISE DDL MIGRATION: PHASE 32 (BURSARY REGISTRATION & CONTINUATION GOVERNANCE)
-- Script: V2026_24_Phase32_Bursary_Registration_Governance.sql
-- Description: Adds tables, columns & constraints for Bursary Registration Application (Ref: MerSeta\NSDMS\LMS\LR\01):
--              1. Alter CompanyLearner.OrganisationId to INT NULL (unemployed bursary applicants)
--              2. Add Bursary Application attributes (New vs Continuation, PreviousCompanyLearnerId, YearOfStudy, AcademicYear)
--              3. Add Educational Institution & Employment Status attributes (InstitutionName, InstitutionTypeCode, EmploymentStatusCode)
--              4. Create lookup.BursaryFundingType table with 7 statutory funding categories
-- Conventions: Singular PascalCase, INT IDENTITY PK / Code PK for lookup, audit columns, indexed FKs.
-- ====================================================================================================

-- 1. Ensure lookup schema exists
IF NOT EXISTS (SELECT 1 FROM sys.schemas WHERE name = 'lookup')
BEGIN
    EXEC('CREATE SCHEMA lookup');
END
GO

-- 2. Make CompanyLearner.OrganisationId NULLABLE for Unemployed Bursaries
IF EXISTS (
    SELECT 1 FROM sys.columns c 
    JOIN sys.tables t ON c.object_id = t.object_id 
    WHERE t.name = 'CompanyLearner' AND c.name = 'OrganisationId' AND c.is_nullable = 0
)
BEGIN
    ALTER TABLE dbo.CompanyLearner ALTER COLUMN OrganisationId INT NULL;
END
GO

-- 3. Extend CompanyLearner with Bursary Statutory Attributes
IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearner') AND name = 'BursaryApplicationTypeCode')
BEGIN
    ALTER TABLE dbo.CompanyLearner ADD BursaryApplicationTypeCode NVARCHAR(50) NULL CONSTRAINT DF_CompanyLearner_BursaryAppType DEFAULT ('New');
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearner') AND name = 'IsContinuation')
BEGIN
    ALTER TABLE dbo.CompanyLearner ADD IsContinuation BIT NOT NULL CONSTRAINT DF_CompanyLearner_IsContinuation DEFAULT (0);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearner') AND name = 'PreviousCompanyLearnerId')
BEGIN
    ALTER TABLE dbo.CompanyLearner ADD PreviousCompanyLearnerId INT NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearner') AND name = 'AcademicYear')
BEGIN
    ALTER TABLE dbo.CompanyLearner ADD AcademicYear INT NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearner') AND name = 'YearOfStudy')
BEGIN
    ALTER TABLE dbo.CompanyLearner ADD YearOfStudy INT NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearner') AND name = 'BursaryFundingTypeCode')
BEGIN
    ALTER TABLE dbo.CompanyLearner ADD BursaryFundingTypeCode NVARCHAR(50) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearner') AND name = 'InstitutionName')
BEGIN
    ALTER TABLE dbo.CompanyLearner ADD InstitutionName NVARCHAR(250) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearner') AND name = 'InstitutionTypeCode')
BEGIN
    ALTER TABLE dbo.CompanyLearner ADD InstitutionTypeCode NVARCHAR(50) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearner') AND name = 'EmploymentStatusCode')
BEGIN
    ALTER TABLE dbo.CompanyLearner ADD EmploymentStatusCode NVARCHAR(50) NULL CONSTRAINT DF_CompanyLearner_EmploymentStatus DEFAULT ('Unemployed');
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearner') AND name = 'ContinuationAcademicResultsPassed')
BEGIN
    ALTER TABLE dbo.CompanyLearner ADD ContinuationAcademicResultsPassed BIT NULL;
END
GO

-- 4. Indexes for Bursary Columns
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_CompanyLearner_PreviousCompanyLearnerId' AND object_id = OBJECT_ID('dbo.CompanyLearner'))
BEGIN
    CREATE NONCLUSTERED INDEX IX_CompanyLearner_PreviousCompanyLearnerId ON dbo.CompanyLearner (PreviousCompanyLearnerId);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_CompanyLearner_BursaryApplicationTypeCode' AND object_id = OBJECT_ID('dbo.CompanyLearner'))
BEGIN
    CREATE NONCLUSTERED INDEX IX_CompanyLearner_BursaryApplicationTypeCode ON dbo.CompanyLearner (BursaryApplicationTypeCode);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_CompanyLearner_AcademicYear' AND object_id = OBJECT_ID('dbo.CompanyLearner'))
BEGIN
    CREATE NONCLUSTERED INDEX IX_CompanyLearner_AcademicYear ON dbo.CompanyLearner (AcademicYear);
END
GO

-- Foreign Key: PreviousCompanyLearner self-referencing hierarchy
IF NOT EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = 'FK_CompanyLearner_PreviousCompanyLearner' AND parent_object_id = OBJECT_ID('dbo.CompanyLearner'))
BEGIN
    ALTER TABLE dbo.CompanyLearner WITH NOCHECK
    ADD CONSTRAINT FK_CompanyLearner_PreviousCompanyLearner
    FOREIGN KEY (PreviousCompanyLearnerId) REFERENCES dbo.CompanyLearner (Id);
END
GO

-- 5. Create lookup.BursaryFundingType Table
IF NOT EXISTS (SELECT 1 FROM sys.tables t JOIN sys.schemas s ON t.schema_id = s.schema_id WHERE t.name = 'BursaryFundingType' AND s.name = 'lookup')
BEGIN
    CREATE TABLE lookup.BursaryFundingType (
        Code NVARCHAR(50) NOT NULL PRIMARY KEY,
        Name NVARCHAR(150) NOT NULL,
        Description NVARCHAR(500) NULL,
        Active BIT NOT NULL DEFAULT 1,
        CreatedAt DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        CreatedBy NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL
    );

    CREATE NONCLUSTERED INDEX IX_BursaryFundingType_Name ON lookup.BursaryFundingType (Name);
    CREATE NONCLUSTERED INDEX IX_BursaryFundingType_Active ON lookup.BursaryFundingType (Active);
END
GO

-- 6. Seed the 7 Statutory Bursary Funding Categories
MERGE lookup.BursaryFundingType AS target
USING (VALUES
    (N'01', N'merSETA funded', N'Direct merSETA discretionary bursary allocation envelope', 1),
    (N'02', N'Non-merSETA funded', N'External donor or non-merSETA sponsored bursary funding', 1),
    (N'03', N'Employer funded', N'Host or sponsor employer funded tertiary bursary', 1),
    (N'04', N'Learner funded', N'Self-funded bursary or private study financial model', 1),
    (N'05', N'Other SETA funded', N'Cross-SETA funded national skills priority bursary allocation', 1),
    (N'06', N'NSF funded', N'National Skills Fund (NSF) statutory bursary allocation', 1),
    (N'07', N'Industry funded', N'Organised industrial chamber or industry association bursary scheme', 1)
) AS source (Code, Name, Description, Active)
ON target.Code = source.Code
WHEN MATCHED THEN
    UPDATE SET 
        target.Name = source.Name,
        target.Description = source.Description,
        target.Active = source.Active,
        target.ModifiedAt = GETUTCDATE(),
        target.ModifiedBy = 'SYSTEM'
WHEN NOT MATCHED THEN
    INSERT (Code, Name, Description, Active, CreatedAt, CreatedBy)
    VALUES (source.Code, source.Name, source.Description, source.Active, GETUTCDATE(), 'SYSTEM');
GO
