-- ==============================================================================
-- V2026_20_Physical_Renaming_Setmis_Layer.sql
-- Phase 4: Physical Renaming & SETMIS Compatibility Layer
-- 1. Safely cuts over dbo.CompanyLearner to dbo.LearnerEnrolment via sp_rename.
-- 2. Preserves 100% backward compatibility via dbo.CompanyLearner SQL View.
-- 3. Updates dbo.vw_SetmisCompanyLearner statutory projection view.
-- 4. Tunes and enables SQL Server System-Versioned Temporal Tables for satellite tables
--    (PersonContact, PersonDemographics, PersonDisabilityRating) in the [history] schema.
-- ==============================================================================

-- 1. Ensure 'history' schema exists
IF NOT EXISTS (SELECT 1 FROM sys.schemas WHERE name = N'history')
BEGIN
    EXEC('CREATE SCHEMA [history]');
END
GO

-- 2. Physical Table Cutover: dbo.CompanyLearner -> dbo.LearnerEnrolment
IF EXISTS (SELECT 1 FROM sys.tables WHERE name = N'CompanyLearner' AND schema_id = SCHEMA_ID(N'dbo'))
   AND NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = N'LearnerEnrolment' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
    PRINT 'Renaming dbo.CompanyLearner to dbo.LearnerEnrolment...';
    EXEC sp_rename 'dbo.CompanyLearner', 'LearnerEnrolment';
    PRINT 'Physical table renamed to dbo.LearnerEnrolment successfully.';
END
GO

-- If PK constraint is still named PK_CompanyLearner, rename it to PK_LearnerEnrolment
IF EXISTS (SELECT 1 FROM sys.key_constraints WHERE name = N'PK_CompanyLearner' AND parent_object_id = OBJECT_ID(N'dbo.LearnerEnrolment'))
BEGIN
    EXEC sp_rename N'dbo.LearnerEnrolment.PK_CompanyLearner', N'PK_LearnerEnrolment', N'OBJECT';
    PRINT 'Renamed primary key constraint to PK_LearnerEnrolment.';
END
GO

-- 3. Create or Refresh dbo.CompanyLearner Backward-Compatibility View
-- If dbo.CompanyLearner does not exist as a base table, ensure it exists as a View.
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

-- 4. Statutory SETMIS Compatibility View: dbo.vw_SetmisCompanyLearner
IF EXISTS (SELECT 1 FROM sys.views WHERE name = N'vw_SetmisCompanyLearner' AND schema_id = SCHEMA_ID(N'dbo'))
BEGIN
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
        le.SetaRegion,
        le.ChamberCode,
        le.IsActive,
        le.CreatedAt,
        le.CreatedBy,
        le.ModifiedAt,
        le.ModifiedBy
    FROM dbo.LearnerEnrolment le;
    ');
    PRINT 'Updated statutory compatibility view dbo.vw_SetmisCompanyLearner.';
END
ELSE
BEGIN
    EXEC('
    CREATE VIEW dbo.vw_SetmisCompanyLearner AS
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
        le.SetaRegion,
        le.ChamberCode,
        le.IsActive,
        le.CreatedAt,
        le.CreatedBy,
        le.ModifiedAt,
        le.ModifiedBy
    FROM dbo.LearnerEnrolment le;
    ');
    PRINT 'Created statutory compatibility view dbo.vw_SetmisCompanyLearner.';
END
GO

-- 5. System-Versioned Temporal Tables Tuning for Satellite Sub-Tables
-- Configures PersonContact, PersonDemographics, and PersonDisabilityRating to use history schema
-- This optimizes storage and prevents full-row history bloat in PersonHistory.
DECLARE @Satellites TABLE (TableName NVARCHAR(128));
INSERT INTO @Satellites (TableName)
VALUES (N'PersonContact'), (N'PersonDemographics'), (N'PersonDisabilityRating');

DECLARE @CurrentSat NVARCHAR(128);
DECLARE sat_cursor CURSOR LOCAL FAST_FORWARD FOR SELECT TableName FROM @Satellites;
OPEN sat_cursor;
FETCH NEXT FROM sat_cursor INTO @CurrentSat;

WHILE @@FETCH_STATUS = 0
BEGIN
    BEGIN TRY
        IF EXISTS (SELECT 1 FROM sys.tables WHERE name = @CurrentSat AND schema_id = SCHEMA_ID(N'dbo') AND temporal_type <> 2)
        BEGIN
            -- Add Period columns if missing
            IF NOT EXISTS (
                SELECT 1 FROM sys.columns 
                WHERE object_id = OBJECT_ID(N'[dbo].[' + @CurrentSat + N']') 
                  AND name = N'PeriodStart'
            )
            BEGIN
                DECLARE @sqlAddCols NVARCHAR(MAX) = N'
                    ALTER TABLE [dbo].[' + @CurrentSat + N'] ADD 
                        [PeriodStart] DATETIME2 GENERATED ALWAYS AS ROW START HIDDEN NOT NULL CONSTRAINT [DF_' + @CurrentSat + N'_PeriodStart] DEFAULT SYSUTCDATETIME(),
                        [PeriodEnd] DATETIME2 GENERATED ALWAYS AS ROW END HIDDEN NOT NULL CONSTRAINT [DF_' + @CurrentSat + N'_PeriodEnd] DEFAULT ''9999-12-31 23:59:59.9999999'',
                        PERIOD FOR SYSTEM_TIME ([PeriodStart], [PeriodEnd]);';
                EXEC sp_executesql @sqlAddCols;
                PRINT 'Added PeriodStart and PeriodEnd to dbo.' + @CurrentSat;
            END

            -- Enable system versioning with dedicated history table in history schema
            DECLARE @histName NVARCHAR(128) = @CurrentSat + N'History';
            DECLARE @sqlEnableSys NVARCHAR(MAX) = N'
                ALTER TABLE [dbo].[' + @CurrentSat + N'] 
                SET (SYSTEM_VERSIONING = ON (HISTORY_TABLE = [history].[' + @histName + N']));';
            EXEC sp_executesql @sqlEnableSys;
            PRINT 'Enabled SYSTEM_VERSIONING on dbo.' + @CurrentSat + ' -> [history].[' + @histName + '].';
        END
    END TRY
    BEGIN CATCH
        PRINT 'Warning on ' + @CurrentSat + ' temporal config: ' + ERROR_MESSAGE();
    END CATCH

    FETCH NEXT FROM sat_cursor INTO @CurrentSat;
END

CLOSE sat_cursor;
DEALLOCATE sat_cursor;
GO
