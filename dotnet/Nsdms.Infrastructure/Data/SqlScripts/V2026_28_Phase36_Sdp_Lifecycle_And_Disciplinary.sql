-- V2026_28_Phase36_Sdp_Lifecycle_And_Disciplinary.sql
-- Idempotent schema migration script for SDP Disciplinary, Site Inspection, Re-Accreditation, and SLA Affiliation.

IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'dbo' AND TABLE_NAME = 'SdpDisciplinaryCase')
BEGIN
    CREATE TABLE dbo.SdpDisciplinaryCase (
        Id INT IDENTITY(1,1) PRIMARY KEY,
        TrainingProviderId INT NOT NULL,
        CaseNumber NVARCHAR(50) NOT NULL,
        CaseType NVARCHAR(50) NOT NULL DEFAULT 'Suspension',
        Status NVARCHAR(50) NOT NULL DEFAULT 'UnderInvestigation',
        ComplaintSource NVARCHAR(100) NULL,
        AllegationSummary NVARCHAR(MAX) NOT NULL,
        InvestigationFindings NVARCHAR(MAX) NULL,
        SanctionType NVARCHAR(50) NULL,
        SanctionStartDate DATETIME2 NULL,
        SanctionEndDate DATETIME2 NULL,
        ReviewCommitteeDecisionNumber NVARCHAR(100) NULL,
        ReviewCommitteeDate DATETIME2 NULL,
        NoticeDocumentRef NVARCHAR(255) NULL,
        IsActive BIT NOT NULL DEFAULT 1,
        CreatedAt DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        CreatedBy NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_SdpDisciplinaryCase_TrainingProvider FOREIGN KEY (TrainingProviderId) REFERENCES dbo.TrainingProvider(Id) ON DELETE CASCADE
    );

    CREATE INDEX IX_SdpDisciplinaryCase_TrainingProviderId ON dbo.SdpDisciplinaryCase(TrainingProviderId);
    CREATE UNIQUE INDEX IX_SdpDisciplinaryCase_CaseNumber ON dbo.SdpDisciplinaryCase(CaseNumber);
    CREATE INDEX IX_SdpDisciplinaryCase_Status ON dbo.SdpDisciplinaryCase(Status);
END
GO

IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'dbo' AND TABLE_NAME = 'SdpSiteInspection')
BEGIN
    CREATE TABLE dbo.SdpSiteInspection (
        Id INT IDENTITY(1,1) PRIMARY KEY,
        TrainingProviderId INT NOT NULL,
        InspectionDate DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        InspectorPersonId INT NULL,
        InspectionType NVARCHAR(50) NOT NULL DEFAULT 'PhysicalOnSite',
        WorkshopSquareMeters DECIMAL(18,2) NULL,
        ClassroomSquareMeters DECIMAL(18,2) NULL,
        HealthAndSafetyCompliant BIT NOT NULL DEFAULT 1,
        MachineGuardingCompliant BIT NOT NULL DEFAULT 1,
        FireSafetyCompliant BIT NOT NULL DEFAULT 1,
        AblutionFacilitiesCompliant BIT NOT NULL DEFAULT 1,
        ToolRatioScore DECIMAL(5,2) NULL DEFAULT 100.00,
        OverallRecommendation NVARCHAR(50) NOT NULL DEFAULT 'Recommended',
        ConditionNotes NVARCHAR(MAX) NULL,
        InspectionReportDocumentRef NVARCHAR(255) NULL,
        IsActive BIT NOT NULL DEFAULT 1,
        CreatedAt DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        CreatedBy NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_SdpSiteInspection_TrainingProvider FOREIGN KEY (TrainingProviderId) REFERENCES dbo.TrainingProvider(Id) ON DELETE CASCADE,
        CONSTRAINT FK_SdpSiteInspection_InspectorPerson FOREIGN KEY (InspectorPersonId) REFERENCES dbo.Person(Id) ON DELETE SET NULL
    );

    CREATE INDEX IX_SdpSiteInspection_TrainingProviderId ON dbo.SdpSiteInspection(TrainingProviderId);
    CREATE INDEX IX_SdpSiteInspection_InspectionDate ON dbo.SdpSiteInspection(InspectionDate);
END
GO

IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'dbo' AND TABLE_NAME = 'SdpReAccreditationApplication')
BEGIN
    CREATE TABLE dbo.SdpReAccreditationApplication (
        Id INT IDENTITY(1,1) PRIMARY KEY,
        TrainingProviderId INT NOT NULL,
        ApplicationNumber NVARCHAR(50) NOT NULL,
        PreviousAccreditationNumber NVARCHAR(100) NULL,
        PreviousStartDate DATETIME2 NULL,
        PreviousEndDate DATETIME2 NULL,
        RequestedStartDate DATETIME2 NULL,
        RequestedEndDate DATETIME2 NULL,
        Status NVARCHAR(50) NOT NULL DEFAULT 'Submitted',
        CommitteeDecisionNumber NVARCHAR(100) NULL,
        CommitteeMeetingDate DATETIME2 NULL,
        RenewalAuditReportRef NVARCHAR(255) NULL,
        IsActive BIT NOT NULL DEFAULT 1,
        CreatedAt DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        CreatedBy NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
        ModifiedAt DATETIME2 NULL,
        ModifiedBy NVARCHAR(100) NULL,
        CONSTRAINT FK_SdpReAccreditationApplication_TrainingProvider FOREIGN KEY (TrainingProviderId) REFERENCES dbo.TrainingProvider(Id) ON DELETE CASCADE
    );

    CREATE INDEX IX_SdpReAccreditationApplication_TrainingProviderId ON dbo.SdpReAccreditationApplication(TrainingProviderId);
    CREATE UNIQUE INDEX IX_SdpReAccreditationApplication_ApplicationNumber ON dbo.SdpReAccreditationApplication(ApplicationNumber);
    CREATE INDEX IX_SdpReAccreditationApplication_Status ON dbo.SdpReAccreditationApplication(Status);
END
GO

-- Add SLA columns to TrainingProviderAssessorLink if not present
IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'dbo' AND TABLE_NAME = 'TrainingProviderAssessorLink' AND COLUMN_NAME = 'SlaDocumentRef')
BEGIN
    ALTER TABLE dbo.TrainingProviderAssessorLink ADD SlaDocumentRef NVARCHAR(255) NULL;
END
GO

IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'dbo' AND TABLE_NAME = 'TrainingProviderAssessorLink' AND COLUMN_NAME = 'SignedByPrincipal')
BEGIN
    ALTER TABLE dbo.TrainingProviderAssessorLink ADD SignedByPrincipal BIT NOT NULL DEFAULT 0;
END
GO

IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'dbo' AND TABLE_NAME = 'TrainingProviderAssessorLink' AND COLUMN_NAME = 'SignedByPractitioner')
BEGIN
    ALTER TABLE dbo.TrainingProviderAssessorLink ADD SignedByPractitioner BIT NOT NULL DEFAULT 0;
END
GO

IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'dbo' AND TABLE_NAME = 'TrainingProviderAssessorLink' AND COLUMN_NAME = 'SlaEffectiveDate')
BEGIN
    ALTER TABLE dbo.TrainingProviderAssessorLink ADD SlaEffectiveDate DATETIME2 NULL;
END
GO

IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'dbo' AND TABLE_NAME = 'TrainingProviderAssessorLink' AND COLUMN_NAME = 'SlaExpiryDate')
BEGIN
    ALTER TABLE dbo.TrainingProviderAssessorLink ADD SlaExpiryDate DATETIME2 NULL;
END
GO
