-- ====================================================================================================
-- MERSETA NSDMS ENTERPRISE DDL MIGRATION: PHASE 5 (LEARNER REGISTRATION ALIGNMENT)
-- Script: V2026_21_Phase5_Learner_Registration_Alignment.sql
-- Description: Adds tables & columns for Learner Application Registration Use Case alignment:
--              1. PersonGuardian (legal parent/guardian for minor learners < 18)
--              2. LearnerRegisteredUnitStandard (multi-unit standard registration for skills programmes)
--              3. CompanyLearner statutory extension columns (Signature dates, 30-day tracking, Candidacy, Withdrawal)
--              4. Organisation non-employer delivery partner & external SETA levy columns
-- Conventions: Singular PascalCase, INT IDENTITY PK, audit columns, indexed FKs.
-- ====================================================================================================

-- 1. Extend CompanyLearner
IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearner') AND name = 'LearnerSignatureDate')
BEGIN
    ALTER TABLE dbo.CompanyLearner ADD LearnerSignatureDate DATETIME2(7) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearner') AND name = 'SubmissionDate')
BEGIN
    ALTER TABLE dbo.CompanyLearner ADD SubmissionDate DATETIME2(7) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearner') AND name = 'SignatoryRoleTitle')
BEGIN
    ALTER TABLE dbo.CompanyLearner ADD SignatoryRoleTitle NVARCHAR(100) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearner') AND name = 'SignatoryPersonId')
BEGIN
    ALTER TABLE dbo.CompanyLearner ADD SignatoryPersonId INT NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearner') AND name = 'ProfessionalRegistrationNumber')
BEGIN
    ALTER TABLE dbo.CompanyLearner ADD ProfessionalRegistrationNumber NVARCHAR(100) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearner') AND name = 'WithdrawalReasonCode')
BEGIN
    ALTER TABLE dbo.CompanyLearner ADD WithdrawalReasonCode NVARCHAR(50) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearner') AND name = 'WithdrawalComments')
BEGIN
    ALTER TABLE dbo.CompanyLearner ADD WithdrawalComments NVARCHAR(MAX) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearner') AND name = 'IsNonEmployerEntity')
BEGIN
    ALTER TABLE dbo.CompanyLearner ADD IsNonEmployerEntity BIT NOT NULL CONSTRAINT DF_CompanyLearner_IsNonEmployer DEFAULT (0);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearner') AND name = 'ExternalSetaId')
BEGIN
    ALTER TABLE dbo.CompanyLearner ADD ExternalSetaId NVARCHAR(10) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.CompanyLearner') AND name = 'HasPendingModifications')
BEGIN
    ALTER TABLE dbo.CompanyLearner ADD HasPendingModifications BIT NOT NULL CONSTRAINT DF_CompanyLearner_HasPendingMod DEFAULT (0);
END
GO

-- Indexes for CompanyLearner extensions
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_CompanyLearner_LearnerSignatureDate' AND object_id = OBJECT_ID('dbo.CompanyLearner'))
BEGIN
    CREATE NONCLUSTERED INDEX IX_CompanyLearner_LearnerSignatureDate ON dbo.CompanyLearner (LearnerSignatureDate);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_CompanyLearner_SubmissionDate' AND object_id = OBJECT_ID('dbo.CompanyLearner'))
BEGIN
    CREATE NONCLUSTERED INDEX IX_CompanyLearner_SubmissionDate ON dbo.CompanyLearner (SubmissionDate);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_CompanyLearner_WithdrawalReason' AND object_id = OBJECT_ID('dbo.CompanyLearner'))
BEGIN
    CREATE NONCLUSTERED INDEX IX_CompanyLearner_WithdrawalReason ON dbo.CompanyLearner (WithdrawalReasonCode);
END
GO

-- 2. Extend Organisation
IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.Organisation') AND name = 'IsNonEmployerEntity')
BEGIN
    ALTER TABLE dbo.Organisation ADD IsNonEmployerEntity BIT NOT NULL CONSTRAINT DF_Organisation_IsNonEmployer DEFAULT (0);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.Organisation') AND name = 'NonEmployerEntityType')
BEGIN
    ALTER TABLE dbo.Organisation ADD NonEmployerEntityType NVARCHAR(50) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.Organisation') AND name = 'ExternalSetaId')
BEGIN
    ALTER TABLE dbo.Organisation ADD ExternalSetaId NVARCHAR(10) NULL;
END
GO

-- 3. PersonGuardian
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'PersonGuardian' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE dbo.PersonGuardian (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_PersonGuardian PRIMARY KEY CLUSTERED,
        PersonId INT NOT NULL,
        GuardianFullName NVARCHAR(100) NOT NULL,
        GuardianIdNumber NVARCHAR(50) NOT NULL,
        RelationshipTypeId NVARCHAR(50) NOT NULL CONSTRAINT DF_PersonGuardian_Relationship DEFAULT ('Parent'),
        ContactNumber NVARCHAR(20) NOT NULL,
        EmailAddress NVARCHAR(100) NULL,
        PhysicalAddress NVARCHAR(250) NULL,
        PostalCode NVARCHAR(10) NULL,
        CeasedAtAge18 BIT NOT NULL CONSTRAINT DF_PersonGuardian_Ceased DEFAULT (0),
        SignatureDate DATETIME2(7) NULL,
        SignatureSeal NVARCHAR(100) NULL,
        IsActive BIT NOT NULL CONSTRAINT DF_PersonGuardian_IsActive DEFAULT (1),
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_PersonGuardian_CreatedAt DEFAULT (SYSUTCDATETIME()),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_PersonGuardian_CreatedBy DEFAULT ('SYSTEM'),
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_PersonGuardian_Person FOREIGN KEY (PersonId) REFERENCES dbo.Person (id) ON DELETE CASCADE
    );

    CREATE NONCLUSTERED INDEX IX_PersonGuardian_PersonId ON dbo.PersonGuardian (PersonId);
    CREATE NONCLUSTERED INDEX IX_PersonGuardian_GuardianIdNumber ON dbo.PersonGuardian (GuardianIdNumber);
END
GO

-- 4. LearnerRegisteredUnitStandard
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'LearnerRegisteredUnitStandard' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE dbo.LearnerRegisteredUnitStandard (
        id INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_LearnerRegisteredUnitStandard PRIMARY KEY CLUSTERED,
        CompanyLearnerId INT NOT NULL,
        UnitStandardId INT NOT NULL,
        UnitStandardTitle NVARCHAR(250) NOT NULL,
        NqfLevel INT NULL,
        Credits INT NULL,
        IsCore BIT NOT NULL CONSTRAINT DF_LearnerRegUnitStandard_IsCore DEFAULT (1),
        StatusCode NVARCHAR(50) NOT NULL CONSTRAINT DF_LearnerRegUnitStandard_Status DEFAULT ('Enrolled'),
        CreatedAt DATETIME2(7) NOT NULL CONSTRAINT DF_LearnerRegUnitStandard_CreatedAt DEFAULT (SYSUTCDATETIME()),
        CreatedBy NVARCHAR(100) NOT NULL CONSTRAINT DF_LearnerRegUnitStandard_CreatedBy DEFAULT ('SYSTEM'),
        ModifiedAt DATETIME2(7) NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_LearnerRegUnitStandard_CompanyLearner FOREIGN KEY (CompanyLearnerId) REFERENCES dbo.CompanyLearner (id) ON DELETE CASCADE
    );

    CREATE NONCLUSTERED INDEX IX_LearnerRegUnitStandard_LearnerId ON dbo.LearnerRegisteredUnitStandard (CompanyLearnerId);
    CREATE NONCLUSTERED INDEX IX_LearnerRegUnitStandard_UnitStandardId ON dbo.LearnerRegisteredUnitStandard (UnitStandardId);
END
GO