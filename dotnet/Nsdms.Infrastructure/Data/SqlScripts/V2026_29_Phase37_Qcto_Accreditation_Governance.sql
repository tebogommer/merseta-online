-- ==============================================================================================
-- V2026_29_Phase37_Qcto_Accreditation_Governance.sql
-- MerSETA NSDMS 2.0: QCTO & Trade Test Centre Statutory Accreditation Governance Remediation
-- Reference: SDA §26I, QCTO Policy on Accreditation of Assessment Centres & Skills Development Providers
-- ==============================================================================================

-- 1. TrainingProvider QCTO Accreditation Extensions
IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.TrainingProvider') AND name = 'QctoAccreditationNumber')
BEGIN
    ALTER TABLE dbo.TrainingProvider ADD QctoAccreditationNumber NVARCHAR(50) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.TrainingProvider') AND name = 'QctoAccreditationStartDate')
BEGIN
    ALTER TABLE dbo.TrainingProvider ADD QctoAccreditationStartDate DATETIME2 NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.TrainingProvider') AND name = 'QctoAccreditationEndDate')
BEGIN
    ALTER TABLE dbo.TrainingProvider ADD QctoAccreditationEndDate DATETIME2 NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.TrainingProvider') AND name = 'QctoCentreCode')
BEGIN
    ALTER TABLE dbo.TrainingProvider ADD QctoCentreCode NVARCHAR(50) NULL;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('dbo.TrainingProvider') AND name = 'QctoLetterAttachmentRef')
BEGIN
    ALTER TABLE dbo.TrainingProvider ADD QctoLetterAttachmentRef NVARCHAR(255) NULL;
END
GO

-- 2. Performance Indexes
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID('dbo.TrainingProvider') AND name = 'IX_TrainingProvider_QctoAccreditationNumber')
BEGIN
    CREATE INDEX IX_TrainingProvider_QctoAccreditationNumber ON dbo.TrainingProvider(QctoAccreditationNumber);
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE object_id = OBJECT_ID('dbo.TrainingProvider') AND name = 'IX_TrainingProvider_NambRegistrationNumber')
BEGIN
    CREATE INDEX IX_TrainingProvider_NambRegistrationNumber ON dbo.TrainingProvider(NambRegistrationNumber);
END
GO
