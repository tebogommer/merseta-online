-- =========================================================================================
-- Migration Script: V2026_14_Add_Holiday_And_Institutional_Closure_Tables.sql
-- Description: Creates the NonWorkingDay table and pre-seeds statutory South African public
--              holidays and official merSETA annual year-end shutdowns (Option A).
-- =========================================================================================

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'NonWorkingDay')
BEGIN
    CREATE TABLE [dbo].[NonWorkingDay] (
        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_NonWorkingDay] PRIMARY KEY CLUSTERED,
        [Name] NVARCHAR(150) NOT NULL,
        [TypeCode] NVARCHAR(50) NOT NULL,
        [StartDate] DATETIME2 NOT NULL,
        [EndDate] DATETIME2 NOT NULL,
        [CalendarYear] INT NOT NULL,
        [AffectsSla] BIT NOT NULL CONSTRAINT [DF_NonWorkingDay_AffectsSla] DEFAULT 1,
        [IsRecurringAnnually] BIT NOT NULL CONSTRAINT [DF_NonWorkingDay_IsRecurring] DEFAULT 0,
        [GazetteOrResolutionRef] NVARCHAR(200) NULL,
        [Description] NVARCHAR(500) NULL,
        [StatusCode] NVARCHAR(50) NOT NULL CONSTRAINT [DF_NonWorkingDay_StatusCode] DEFAULT 'Approved',
        [IsActive] BIT NOT NULL CONSTRAINT [DF_NonWorkingDay_IsActive] DEFAULT 1,
        [IsClosed] BIT NOT NULL CONSTRAINT [DF_NonWorkingDay_IsClosed] DEFAULT 0,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_NonWorkingDay_CreatedAt] DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );

    CREATE INDEX [IX_NonWorkingDay_Name] ON [dbo].[NonWorkingDay]([Name]);
    CREATE INDEX [IX_NonWorkingDay_TypeCode] ON [dbo].[NonWorkingDay]([TypeCode]);
    CREATE INDEX [IX_NonWorkingDay_CalendarYear] ON [dbo].[NonWorkingDay]([CalendarYear]);
    CREATE INDEX [IX_NonWorkingDay_Dates] ON [dbo].[NonWorkingDay]([StartDate], [EndDate]);
    CREATE INDEX [IX_NonWorkingDay_Status] ON [dbo].[NonWorkingDay]([StatusCode], [IsActive]);
    CREATE INDEX [IX_NonWorkingDay_AffectsSla] ON [dbo].[NonWorkingDay]([AffectsSla]);
END;

-- Pre-seed 2026 South African Statutory Holidays (Public Holidays Act 36 of 1994)
IF NOT EXISTS (SELECT * FROM [dbo].[NonWorkingDay] WHERE [Name] = 'New Year''s Day' AND [CalendarYear] = 2026)
BEGIN
    INSERT INTO [dbo].[NonWorkingDay] ([Name], [TypeCode], [StartDate], [EndDate], [CalendarYear], [AffectsSla], [IsRecurringAnnually], [GazetteOrResolutionRef], [Description], [StatusCode], [IsActive], [CreatedBy])
    VALUES ('New Year''s Day', 'NAT_STATUTORY', '2026-01-01', '2026-01-01', 2026, 1, 1, 'Act 36 of 1994', 'Statutory public holiday observed nationwide in South Africa', 'Approved', 1, 'SYSTEM');
END;

IF NOT EXISTS (SELECT * FROM [dbo].[NonWorkingDay] WHERE [Name] = 'Human Rights Day' AND [CalendarYear] = 2026)
BEGIN
    INSERT INTO [dbo].[NonWorkingDay] ([Name], [TypeCode], [StartDate], [EndDate], [CalendarYear], [AffectsSla], [IsRecurringAnnually], [GazetteOrResolutionRef], [Description], [StatusCode], [IsActive], [CreatedBy])
    VALUES ('Human Rights Day', 'NAT_STATUTORY', '2026-03-21', '2026-03-21', 2026, 1, 1, 'Act 36 of 1994', 'Statutory public holiday observed nationwide in South Africa', 'Approved', 1, 'SYSTEM');
END;

IF NOT EXISTS (SELECT * FROM [dbo].[NonWorkingDay] WHERE [Name] = 'Good Friday' AND [CalendarYear] = 2026)
BEGIN
    INSERT INTO [dbo].[NonWorkingDay] ([Name], [TypeCode], [StartDate], [EndDate], [CalendarYear], [AffectsSla], [IsRecurringAnnually], [GazetteOrResolutionRef], [Description], [StatusCode], [IsActive], [CreatedBy])
    VALUES ('Good Friday', 'NAT_STATUTORY', '2026-04-03', '2026-04-03', 2026, 1, 0, 'Act 36 of 1994', 'Statutory public holiday observed nationwide in South Africa (Computus calculation)', 'Approved', 1, 'SYSTEM');
END;

IF NOT EXISTS (SELECT * FROM [dbo].[NonWorkingDay] WHERE [Name] = 'Family Day' AND [CalendarYear] = 2026)
BEGIN
    INSERT INTO [dbo].[NonWorkingDay] ([Name], [TypeCode], [StartDate], [EndDate], [CalendarYear], [AffectsSla], [IsRecurringAnnually], [GazetteOrResolutionRef], [Description], [StatusCode], [IsActive], [CreatedBy])
    VALUES ('Family Day', 'NAT_STATUTORY', '2026-04-06', '2026-04-06', 2026, 1, 0, 'Act 36 of 1994', 'Statutory public holiday observed nationwide in South Africa (Easter Monday)', 'Approved', 1, 'SYSTEM');
END;

IF NOT EXISTS (SELECT * FROM [dbo].[NonWorkingDay] WHERE [Name] = 'Freedom Day' AND [CalendarYear] = 2026)
BEGIN
    INSERT INTO [dbo].[NonWorkingDay] ([Name], [TypeCode], [StartDate], [EndDate], [CalendarYear], [AffectsSla], [IsRecurringAnnually], [GazetteOrResolutionRef], [Description], [StatusCode], [IsActive], [CreatedBy])
    VALUES ('Freedom Day', 'NAT_STATUTORY', '2026-04-27', '2026-04-27', 2026, 1, 1, 'Act 36 of 1994', 'Statutory public holiday observed nationwide in South Africa', 'Approved', 1, 'SYSTEM');
END;

IF NOT EXISTS (SELECT * FROM [dbo].[NonWorkingDay] WHERE [Name] = 'Workers'' Day' AND [CalendarYear] = 2026)
BEGIN
    INSERT INTO [dbo].[NonWorkingDay] ([Name], [TypeCode], [StartDate], [EndDate], [CalendarYear], [AffectsSla], [IsRecurringAnnually], [GazetteOrResolutionRef], [Description], [StatusCode], [IsActive], [CreatedBy])
    VALUES ('Workers'' Day', 'NAT_STATUTORY', '2026-05-01', '2026-05-01', 2026, 1, 1, 'Act 36 of 1994', 'Statutory public holiday observed nationwide in South Africa', 'Approved', 1, 'SYSTEM');
END;

IF NOT EXISTS (SELECT * FROM [dbo].[NonWorkingDay] WHERE [Name] = 'Youth Day' AND [CalendarYear] = 2026)
BEGIN
    INSERT INTO [dbo].[NonWorkingDay] ([Name], [TypeCode], [StartDate], [EndDate], [CalendarYear], [AffectsSla], [IsRecurringAnnually], [GazetteOrResolutionRef], [Description], [StatusCode], [IsActive], [CreatedBy])
    VALUES ('Youth Day', 'NAT_STATUTORY', '2026-06-16', '2026-06-16', 2026, 1, 1, 'Act 36 of 1994', 'Statutory public holiday observed nationwide in South Africa', 'Approved', 1, 'SYSTEM');
END;

IF NOT EXISTS (SELECT * FROM [dbo].[NonWorkingDay] WHERE [Name] = 'National Women''s Day' AND [CalendarYear] = 2026)
BEGIN
    INSERT INTO [dbo].[NonWorkingDay] ([Name], [TypeCode], [StartDate], [EndDate], [CalendarYear], [AffectsSla], [IsRecurringAnnually], [GazetteOrResolutionRef], [Description], [StatusCode], [IsActive], [CreatedBy])
    VALUES ('National Women''s Day', 'NAT_STATUTORY', '2026-08-09', '2026-08-09', 2026, 1, 1, 'Act 36 of 1994', 'Statutory public holiday observed nationwide in South Africa', 'Approved', 1, 'SYSTEM');
END;

IF NOT EXISTS (SELECT * FROM [dbo].[NonWorkingDay] WHERE [Name] = 'Public Holiday Observed (Women''s Day)' AND [CalendarYear] = 2026)
BEGIN
    INSERT INTO [dbo].[NonWorkingDay] ([Name], [TypeCode], [StartDate], [EndDate], [CalendarYear], [AffectsSla], [IsRecurringAnnually], [GazetteOrResolutionRef], [Description], [StatusCode], [IsActive], [CreatedBy])
    VALUES ('Public Holiday Observed (Women''s Day)', 'NAT_STATUTORY', '2026-08-10', '2026-08-10', 2026, 1, 0, 'Act 36 of 1994 Sec 2(1)', 'Section 2(1) Sunday holiday rollover observed on Monday', 'Approved', 1, 'SYSTEM');
END;

IF NOT EXISTS (SELECT * FROM [dbo].[NonWorkingDay] WHERE [Name] = 'Heritage Day' AND [CalendarYear] = 2026)
BEGIN
    INSERT INTO [dbo].[NonWorkingDay] ([Name], [TypeCode], [StartDate], [EndDate], [CalendarYear], [AffectsSla], [IsRecurringAnnually], [GazetteOrResolutionRef], [Description], [StatusCode], [IsActive], [CreatedBy])
    VALUES ('Heritage Day', 'NAT_STATUTORY', '2026-09-24', '2026-09-24', 2026, 1, 1, 'Act 36 of 1994', 'Statutory public holiday observed nationwide in South Africa', 'Approved', 1, 'SYSTEM');
END;

IF NOT EXISTS (SELECT * FROM [dbo].[NonWorkingDay] WHERE [Name] = 'Day of Reconciliation' AND [CalendarYear] = 2026)
BEGIN
    INSERT INTO [dbo].[NonWorkingDay] ([Name], [TypeCode], [StartDate], [EndDate], [CalendarYear], [AffectsSla], [IsRecurringAnnually], [GazetteOrResolutionRef], [Description], [StatusCode], [IsActive], [CreatedBy])
    VALUES ('Day of Reconciliation', 'NAT_STATUTORY', '2026-12-16', '2026-12-16', 2026, 1, 1, 'Act 36 of 1994', 'Statutory public holiday observed nationwide in South Africa', 'Approved', 1, 'SYSTEM');
END;

IF NOT EXISTS (SELECT * FROM [dbo].[NonWorkingDay] WHERE [Name] = 'Christmas Day' AND [CalendarYear] = 2026)
BEGIN
    INSERT INTO [dbo].[NonWorkingDay] ([Name], [TypeCode], [StartDate], [EndDate], [CalendarYear], [AffectsSla], [IsRecurringAnnually], [GazetteOrResolutionRef], [Description], [StatusCode], [IsActive], [CreatedBy])
    VALUES ('Christmas Day', 'NAT_STATUTORY', '2026-12-25', '2026-12-25', 2026, 1, 1, 'Act 36 of 1994', 'Statutory public holiday observed nationwide in South Africa', 'Approved', 1, 'SYSTEM');
END;

IF NOT EXISTS (SELECT * FROM [dbo].[NonWorkingDay] WHERE [Name] = 'Day of Goodwill' AND [CalendarYear] = 2026)
BEGIN
    INSERT INTO [dbo].[NonWorkingDay] ([Name], [TypeCode], [StartDate], [EndDate], [CalendarYear], [AffectsSla], [IsRecurringAnnually], [GazetteOrResolutionRef], [Description], [StatusCode], [IsActive], [CreatedBy])
    VALUES ('Day of Goodwill', 'NAT_STATUTORY', '2026-12-26', '2026-12-26', 2026, 1, 1, 'Act 36 of 1994', 'Statutory public holiday observed nationwide in South Africa', 'Approved', 1, 'SYSTEM');
END;

-- Pre-seed merSETA Annual Year-End Office Shutdown 2026/2027 (Multi-Day Institutional Closure Span)
IF NOT EXISTS (SELECT * FROM [dbo].[NonWorkingDay] WHERE [TypeCode] = 'INST_SHUTDOWN' AND [CalendarYear] = 2026)
BEGIN
    INSERT INTO [dbo].[NonWorkingDay] ([Name], [TypeCode], [StartDate], [EndDate], [CalendarYear], [AffectsSla], [IsRecurringAnnually], [GazetteOrResolutionRef], [Description], [StatusCode], [IsActive], [CreatedBy])
    VALUES (
        'merSETA Annual Year-End Office Shutdown 2026/2027',
        'INST_SHUTDOWN',
        '2026-12-24',
        '2027-01-03',
        2026,
        1,
        1,
        'merSETA Circular 2026-12',
        'Annual institutional closure of all merSETA regional operations and head office. Universal workflow SLA countdowns pause throughout this closure period.',
        'Approved',
        1,
        'SYSTEM'
    );
END;

-- Pre-seed merSETA Annual Year-End Office Shutdown 2027/2028 (Multi-Day Institutional Closure Span)
IF NOT EXISTS (SELECT * FROM [dbo].[NonWorkingDay] WHERE [TypeCode] = 'INST_SHUTDOWN' AND [CalendarYear] = 2027)
BEGIN
    INSERT INTO [dbo].[NonWorkingDay] ([Name], [TypeCode], [StartDate], [EndDate], [CalendarYear], [AffectsSla], [IsRecurringAnnually], [GazetteOrResolutionRef], [Description], [StatusCode], [IsActive], [CreatedBy])
    VALUES (
        'merSETA Annual Year-End Office Shutdown 2027/2028',
        'INST_SHUTDOWN',
        '2027-12-24',
        '2028-01-03',
        2027,
        1,
        1,
        'merSETA Circular 2027-12',
        'Annual institutional closure of all merSETA regional operations and head office. Universal workflow SLA countdowns pause throughout this closure period.',
        'Approved',
        1,
        'SYSTEM'
    );
END;
