-- ==============================================================================
-- Script: V2026_27_CompanyLearner_To_LearnerEnrolment.sql
-- Description: Phase 4 / Option B Physical Cutover from dbo.CompanyLearner to dbo.LearnerEnrolment
-- Standards: Clean Architecture, DHET SETMIS Canonical Spec, SQL Server 2022 System-Versioned Temporal Tables
-- ==============================================================================

SET NOCOUNT ON;
PRINT '==============================================================================';
PRINT 'Starting Option B Migration: dbo.CompanyLearner -> dbo.LearnerEnrolment';
PRINT '==============================================================================';

-- 1. Ensure 'history' schema exists for temporal tables
IF NOT EXISTS (SELECT 1 FROM sys.schemas WHERE name = N'history')
BEGIN
    PRINT 'Creating [history] schema...';
    EXEC('CREATE SCHEMA [history]');
END
GO

-- 2. Temporal Table System Versioning Toggle (Safe sp_rename)
-- If dbo.CompanyLearner is currently system-versioned, we must turn SYSTEM_VERSIONING OFF before renaming
IF EXISTS (
    SELECT 1 FROM sys.tables 
    WHERE name = N'CompanyLearner' 
      AND schema_id = SCHEMA_ID(N'dbo') 
      AND temporal_type = 2
)
BEGIN
    PRINT 'Disabling SYSTEM_VERSIONING on dbo.CompanyLearner prior to rename...';
    ALTER TABLE dbo.CompanyLearner SET (SYSTEM_VERSIONING = OFF);
    PRINT 'SYSTEM_VERSIONING disabled successfully.';
END
GO

-- 3. Physical Table Rename: dbo.CompanyLearner -> dbo.LearnerEnrolment
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'CompanyLearner' AND schema_id = SCHEMA_ID(N'dbo'))
   AND NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = N'LearnerEnrolment' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    PRINT 'Executing sp_rename: dbo.CompanyLearner -> LearnerEnrolment...';
    EXEC sp_rename 'dbo.CompanyLearner', 'LearnerEnrolment';
    PRINT 'Physical table renamed to dbo.LearnerEnrolment successfully.';
END
ELSE
BEGIN
    PRINT 'dbo.LearnerEnrolment already exists as a base table; skipping physical table rename.';
END
GO

-- 4. Rename History Table if exists in history schema
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'CompanyLearnerHistory' AND schema_id = SCHEMA_ID(N'history'))
   AND NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = N'LearnerEnrolmentHistory' AND schema_id = SCHEMA_ID(N'history'))
BEGIN
    PRINT 'Executing sp_rename: history.CompanyLearnerHistory -> LearnerEnrolmentHistory...';
    EXEC sp_rename 'history.CompanyLearnerHistory', 'LearnerEnrolmentHistory';
    PRINT 'History table renamed to history.LearnerEnrolmentHistory successfully.';
END
GO

-- 5. Rename Primary Key Constraint
IF EXISTS (
    SELECT 1 FROM sys.key_constraints 
    WHERE name = N'PK_CompanyLearner' 
      AND parent_object_id = OBJECT_ID(N'dbo.LearnerEnrolment')
)
BEGIN
    PRINT 'Renaming PK_CompanyLearner -> PK_LearnerEnrolment...';
    EXEC sp_rename N'dbo.LearnerEnrolment.PK_CompanyLearner', N'PK_LearnerEnrolment', N'OBJECT';
    PRINT 'PK constraint renamed successfully.';
END
GO

-- 6. Rename Check Constraint
IF EXISTS (
    SELECT 1 FROM sys.check_constraints 
    WHERE name = N'CK_CompanyLearner_Dates' 
      AND parent_object_id = OBJECT_ID(N'dbo.LearnerEnrolment')
)
BEGIN
    PRINT 'Renaming CK_CompanyLearner_Dates -> CK_LearnerEnrolment_Dates...';
    EXEC sp_rename N'dbo.LearnerEnrolment.CK_CompanyLearner_Dates', N'CK_LearnerEnrolment_Dates', N'OBJECT';
    PRINT 'Check constraint renamed successfully.';
END
GO

-- 7. Re-enable or Configure Temporal System Versioning on dbo.LearnerEnrolment
IF EXISTS (
    SELECT 1 FROM sys.tables 
    WHERE name = N'LearnerEnrolment' 
      AND schema_id = SCHEMA_ID(N'dbo') 
      AND temporal_type = 0
)
BEGIN
    PRINT 'Configuring system-versioned temporal table on dbo.LearnerEnrolment...';

    -- Ensure ValidFrom and ValidTo row start/end columns exist
    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID(N'dbo.LearnerEnrolment') AND name = N'ValidFrom')
    BEGIN
        ALTER TABLE dbo.LearnerEnrolment ADD
            ValidFrom DATETIME2 GENERATED ALWAYS AS ROW START HIDDEN CONSTRAINT DF_LearnerEnrolment_ValidFrom DEFAULT SYSUTCDATETIME(),
            ValidTo DATETIME2 GENERATED ALWAYS AS ROW END HIDDEN CONSTRAINT DF_LearnerEnrolment_ValidTo DEFAULT '9999-12-31 23:59:59.9999999',
            PERIOD FOR SYSTEM_TIME (ValidFrom, ValidTo);
        PRINT 'Temporal period columns added to dbo.LearnerEnrolment.';
    END

    -- Enable system versioning pointing to history.LearnerEnrolmentHistory
    BEGIN TRY
        ALTER TABLE dbo.LearnerEnrolment SET (SYSTEM_VERSIONING = ON (HISTORY_TABLE = history.LearnerEnrolmentHistory));
        PRINT 'SYSTEM_VERSIONING enabled on dbo.LearnerEnrolment.';
    END TRY
    BEGIN CATCH
        PRINT 'Warning: Could not enable system versioning immediately: ' + ERROR_MESSAGE();
    END CATCH
END
GO

-- 8. Backward-Compatibility View: dbo.CompanyLearner
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = N'CompanyLearner' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    IF EXISTS (SELECT 1 FROM sys.views WHERE name = N'CompanyLearner' AND schema_id = SCHEMA_ID(N'dbo'))
    BEGIN
        EXEC('
        ALTER VIEW dbo.CompanyLearner AS
        SELECT * FROM dbo.LearnerEnrolment;
        ');
        PRINT 'Updated backward-compatible view dbo.CompanyLearner.';
    END
    ELSE
    BEGIN
        EXEC('
        CREATE VIEW dbo.CompanyLearner AS
        SELECT * FROM dbo.LearnerEnrolment;
        ');
        PRINT 'Created backward-compatible view dbo.CompanyLearner.';
    END
END
GO

-- 9. Refresh SETMIS Statutory View: dbo.vw_SetmisCompanyLearner
IF EXISTS (SELECT 1 FROM sys.views WHERE name = N'vw_SetmisCompanyLearner' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    PRINT 'Refreshing statutory view dbo.vw_SetmisCompanyLearner...';
    EXEC('
    ALTER VIEW dbo.vw_SetmisCompanyLearner AS
    SELECT 
        le.Id,
        le.PersonId,
        le.OrganisationId AS CompanyId,
        le.OrganisationSiteId,
        le.TrainingProviderId,
        le.LearnerContractNumber,
        le.QualificationTitle,
        le.SaqaQualificationId,
        le.NqfLevel,
        le.LearningProgrammeTypeCode,
        le.LearnershipId,
        le.NonNqfInterventionCode,
        le.PartOfId,
        le.EnrolmentTypeId,
        le.EnrolmentStatusId,
        le.EnrolmentStatusDate,
        le.EnrolmentStatusReasonId,
        le.AssessorRegistrationNumber,
        le.AssessorEtqaId,
        le.PracticalProviderCode,
        le.PracticalProviderEtqaId,
        le.OfoCode,
        le.EconomicStatusId,
        le.UrbanRuralId,
        le.CumulativeSpend,
        le.CertificateNumber,
        le.PriorQualificationId,
        le.PriorQualificationAchievementDate,
        le.InternshipStatusId,
        le.FundingTypeCode,
        le.FundingId,
        le.EnrolmentStatusCode,
        le.RegistrationDate,
        le.CommencementDate,
        le.ExpectedCompletionDate,
        le.CompletionDate,
        le.ApplicationDate,
        le.ContractRegistrationDate,
        le.IsActive,
        le.CreatedBy,
        le.CreatedAt,
        le.ModifiedBy,
        le.ModifiedAt
    FROM dbo.LearnerEnrolment le;
    ');
    PRINT 'Statutory view dbo.vw_SetmisCompanyLearner refreshed successfully.';
END
GO

PRINT '==============================================================================';
PRINT 'Option B Migration Completed Successfully.';
PRINT 'Physical Table: dbo.LearnerEnrolment';
PRINT 'Temporal History: history.LearnerEnrolmentHistory';
PRINT 'Compatibility View: dbo.CompanyLearner';
PRINT 'SETMIS View: dbo.vw_SetmisCompanyLearner';
PRINT '==============================================================================';
GO
