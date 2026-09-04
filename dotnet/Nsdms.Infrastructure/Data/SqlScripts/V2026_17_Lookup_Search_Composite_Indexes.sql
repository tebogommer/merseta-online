-- ===========================================================================
-- MerSETA NSDMS — Phase 23 / OPT-003 & OPT-004 Lookup Search Composite Indexes
-- Script: V2026_17_Lookup_Search_Composite_Indexes.sql
-- Description: Creates optimized composite non-clustered indexes on (Code, Name)
--              with covering INCLUDE columns for high-volume lookup tables:
--              1. lookup.SicCodeType (815 rows - SIC 5-digit economic codes)
--              2. lookup.OfoCodeType (1,454 rows - DHET OFO occupations)
--              3. lookup.StatssaAreaCodeType / lookup.StatssaAreaType (22,108 rows - Stats SA spatial areas)
-- Target Engine: Microsoft SQL Server Express (localhost / NSDMS-NET)
-- ===========================================================================

-- 1. Ensure 'lookup' schema exists
IF NOT EXISTS (SELECT * FROM sys.schemas WHERE name = 'lookup')
BEGIN
    EXEC('CREATE SCHEMA [lookup]');
END
GO

-- 2. Composite Covering Index on lookup.SicCodeType (Code, Name)
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'SicCodeType' AND schema_id = SCHEMA_ID('lookup'))
BEGIN
    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_SicCodeType_Code_Name' AND object_id = OBJECT_ID('lookup.SicCodeType'))
    BEGIN
        CREATE NONCLUSTERED INDEX [IX_SicCodeType_Code_Name]
        ON [lookup].[SicCodeType] ([Code], [Name])
        INCLUDE ([Active], [Description], [ChamberCode], [SetaCode]);
    END
END
GO

-- 3. Composite Covering Index on lookup.OfoCodeType (Code, Name)
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'OfoCodeType' AND schema_id = SCHEMA_ID('lookup'))
BEGIN
    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_OfoCodeType_Code_Name' AND object_id = OBJECT_ID('lookup.OfoCodeType'))
    BEGIN
        CREATE NONCLUSTERED INDEX [IX_OfoCodeType_Code_Name]
        ON [lookup].[OfoCodeType] ([Code], [Name])
        INCLUDE ([Active], [Description]);
    END
END
GO

-- 4. Composite Covering Index on lookup.StatssaAreaCodeType (Code, Name)
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'StatssaAreaCodeType' AND schema_id = SCHEMA_ID('lookup'))
BEGIN
    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_StatssaAreaCodeType_Code_Name' AND object_id = OBJECT_ID('lookup.StatssaAreaCodeType'))
    BEGIN
        CREATE NONCLUSTERED INDEX [IX_StatssaAreaCodeType_Code_Name]
        ON [lookup].[StatssaAreaCodeType] ([Code], [Name])
        INCLUDE ([Active], [Description]);
    END
END
GO

-- 5. Fallback check for alternative table name lookup.StatssaAreaType (if mapped or aliased)
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'StatssaAreaType' AND schema_id = SCHEMA_ID('lookup'))
BEGIN
    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_StatssaAreaType_Code_Name' AND object_id = OBJECT_ID('lookup.StatssaAreaType'))
    BEGIN
        CREATE NONCLUSTERED INDEX [IX_StatssaAreaType_Code_Name]
        ON [lookup].[StatssaAreaType] ([Code], [Name])
        INCLUDE ([Active], [Description]);
    END
END
GO
