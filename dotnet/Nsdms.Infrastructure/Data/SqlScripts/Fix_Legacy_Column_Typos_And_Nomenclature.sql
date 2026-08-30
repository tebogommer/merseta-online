-- ====================================================================================
-- MerSETA NSDMS — Fix Legacy Column Typos & Disambiguate Nomenclature (Idempotent T-SQL)
-- Database Target: Microsoft SQL Server 2022 / SQL Server Express (NSDMS-NET)
-- Purpose: Normalizes legacy typos (e.g. dunding_id, active_aontracts_id, etc.)
-- ====================================================================================

SET NOCOUNT ON;
PRINT 'Beginning Idempotent Schema Normalization & Legacy Typo Fixes...';

-- Helper macro procedure for safe column renaming
DECLARE @SchemaName NVARCHAR(128) = N'dbo';
DECLARE @TableName NVARCHAR(128);
DECLARE @OldColumn NVARCHAR(128);
DECLARE @NewColumn NVARCHAR(128);

-- Table of known legacy column typo mappings
DECLARE @Renames TABLE (
    SchemaName NVARCHAR(128),
    TableName NVARCHAR(128),
    OldColumn NVARCHAR(128),
    NewColumn NVARCHAR(128)
);

INSERT INTO @Renames (SchemaName, TableName, OldColumn, NewColumn)
VALUES
    (N'dbo', N'company_learners', N'dunding_id', N'funding_id'),
    (N'dbo', N'CompanyLearner', N'dunding_id', N'funding_id'),
    (N'dbo', N'company_learners_details_change', N'dunding_id', N'funding_id'),
    (N'dbo', N'active_contract_extension_request', N'active_aontracts_id', N'active_contracts_id'),
    (N'dbo', N'active_contract_termination_request', N'active_aontracts_id', N'active_contracts_id'),
    (N'dbo', N'company_learners', N'stats_saarea_code_id', N'stats_sa_area_code_id'),
    (N'dbo', N'CompanyLearner', N'stats_saarea_code_id', N'stats_sa_area_code_id'),
    (N'dbo', N'address', N'stats_saarea_code_id', N'stats_sa_area_code_id'),
    (N'dbo', N'address_change', N'stats_saarea_code_id', N'stats_sa_area_code_id'),
    (N'dbo', N'address_history', N'stats_saarea_code_id', N'stats_sa_area_code_id'),
    (N'dbo', N'legacy_employer_wa2_trade', N'wa_emplyer_trade_status', N'wa_employer_trade_status'),
    (N'dbo', N'sites_sme_company_learners', N'reason_learner_not_avalaible', N'reason_learner_not_available'),
    (N'dbo', N'legacy_organisation_non_levy_paying', N'seta__status', N'seta_status'),
    (N'dbo', N'legacy_organisation_levy_paying', N'seta__status', N'seta_status'),
    (N'dbo', N'active_contracts', N'rejection_user', N'rejection_user_id');

DECLARE RenameCursor CURSOR LOCAL FAST_FORWARD FOR
SELECT SchemaName, TableName, OldColumn, NewColumn FROM @Renames;

OPEN RenameCursor;
FETCH NEXT FROM RenameCursor INTO @SchemaName, @TableName, @OldColumn, @NewColumn;

WHILE @@FETCH_STATUS = 0
BEGIN
    -- Check if table and old column exist, and new column does not yet exist
    IF EXISTS (
        SELECT 1 FROM sys.columns c
        JOIN sys.tables t ON c.object_id = t.object_id
        JOIN sys.schemas s ON t.schema_id = s.schema_id
        WHERE s.name = @SchemaName AND t.name = @TableName AND c.name = @OldColumn
    ) AND NOT EXISTS (
        SELECT 1 FROM sys.columns c
        JOIN sys.tables t ON c.object_id = t.object_id
        JOIN sys.schemas s ON t.schema_id = s.schema_id
        WHERE s.name = @SchemaName AND t.name = @TableName AND c.name = @NewColumn
    )
    BEGIN
        DECLARE @QualifiedOldName NVARCHAR(500) = QUOTENAME(@SchemaName) + N'.' + QUOTENAME(@TableName) + N'.' + QUOTENAME(@OldColumn);
        PRINT 'Renaming legacy typo column ' + @QualifiedOldName + ' -> ' + @NewColumn;
        EXEC sys.sp_rename @objname = @QualifiedOldName, @newname = @NewColumn, @objtype = 'COLUMN';
    END
    ELSE
    BEGIN
        PRINT 'Skipped rename for ' + @SchemaName + '.' + @TableName + '.' + @OldColumn + ' (already normalized or not present).';
    END

    FETCH NEXT FROM RenameCursor INTO @SchemaName, @TableName, @OldColumn, @NewColumn;
END;

CLOSE RenameCursor;
DEALLOCATE RenameCursor;

PRINT 'Schema Typo Normalization Completed Successfully.';
