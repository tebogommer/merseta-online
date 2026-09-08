using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Idempotent database schema migrator and seeder for the Hierarchical Relational Fiscal Calendar & Quarters engine (Option A).
/// </summary>
public static class Phase13FiscalCalendarMigrator
{
    public static async Task MigrateFiscalCalendarSchemaAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        if (!context.Database.IsSqlServer())
        {
            return;
        }

        try
        {
            // Resolve script from output directory or relative path
            var baseDir = AppContext.BaseDirectory;
            var scriptPath = Path.Combine(baseDir, "Data", "SqlScripts", "V2026_13_Add_Fiscal_Calendar_Tables.sql");
            
            if (!File.Exists(scriptPath))
            {
                scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "Data", "SqlScripts", "V2026_13_Add_Fiscal_Calendar_Tables.sql");
            }
            if (!File.Exists(scriptPath))
            {
                scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "..", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_13_Add_Fiscal_Calendar_Tables.sql");
            }

            string sql;
            if (File.Exists(scriptPath))
            {
                sql = await File.ReadAllTextAsync(scriptPath);
            }
            else
            {
                // Fallback embedded DDL
                sql = @"
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'FinancialYear')
BEGIN
    CREATE TABLE [dbo].[FinancialYear] (
        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_FinancialYear] PRIMARY KEY CLUSTERED,
        [FinYearCode] NVARCHAR(50) NOT NULL,
        [StartYear] INT NOT NULL,
        [EndYear] INT NOT NULL,
        [StartDate] DATETIME2 NOT NULL,
        [EndDate] DATETIME2 NOT NULL,
        [Description] NVARCHAR(500) NULL,
        [StatusCode] NVARCHAR(50) NOT NULL CONSTRAINT [DF_FinancialYear_StatusCode] DEFAULT 'Active',
        [IsActive] BIT NOT NULL CONSTRAINT [DF_FinancialYear_IsActive] DEFAULT 1,
        [IsClosed] BIT NOT NULL CONSTRAINT [DF_FinancialYear_IsClosed] DEFAULT 0,
        [SubmittedBy] NVARCHAR(100) NULL,
        [SubmittedAt] DATETIME2 NULL,
        [SubmissionNotes] NVARCHAR(500) NULL,
        [ReviewedBy] NVARCHAR(100) NULL,
        [ReviewedAt] DATETIME2 NULL,
        [ReviewNotes] NVARCHAR(500) NULL,
        [RevisionNumber] INT NOT NULL CONSTRAINT [DF_FinancialYear_RevisionNumber] DEFAULT 1,
        [AmendmentReason] NVARCHAR(500) NULL,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_FinancialYear_CreatedAt] DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );

    CREATE UNIQUE INDEX [IX_FinancialYear_FinYearCode] ON [dbo].[FinancialYear]([FinYearCode]);
    CREATE INDEX [IX_FinancialYear_Dates] ON [dbo].[FinancialYear]([StartDate], [EndDate]);
    CREATE INDEX [IX_FinancialYear_Status] ON [dbo].[FinancialYear]([StatusCode], [IsActive]);
END
ELSE
BEGIN
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID(N'[dbo].[FinancialYear]') AND name = 'SubmittedBy')
    BEGIN
        ALTER TABLE [dbo].[FinancialYear] ADD [SubmittedBy] NVARCHAR(100) NULL;
        ALTER TABLE [dbo].[FinancialYear] ADD [SubmittedAt] DATETIME2 NULL;
        ALTER TABLE [dbo].[FinancialYear] ADD [SubmissionNotes] NVARCHAR(500) NULL;
        ALTER TABLE [dbo].[FinancialYear] ADD [ReviewedBy] NVARCHAR(100) NULL;
        ALTER TABLE [dbo].[FinancialYear] ADD [ReviewedAt] DATETIME2 NULL;
        ALTER TABLE [dbo].[FinancialYear] ADD [ReviewNotes] NVARCHAR(500) NULL;
        ALTER TABLE [dbo].[FinancialYear] ADD [RevisionNumber] INT NOT NULL CONSTRAINT [DF_FinancialYear_RevisionNumber] DEFAULT 1;
        ALTER TABLE [dbo].[FinancialYear] ADD [AmendmentReason] NVARCHAR(500) NULL;
    END;
END;

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'FinancialQuarter')
BEGIN
    CREATE TABLE [dbo].[FinancialQuarter] (
        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_FinancialQuarter] PRIMARY KEY CLUSTERED,
        [FinancialYearId] INT NOT NULL,
        [QuarterCode] NVARCHAR(10) NOT NULL,
        [QuarterNumber] INT NOT NULL,
        [StartDate] DATETIME2 NOT NULL,
        [EndDate] DATETIME2 NOT NULL,
        [Description] NVARCHAR(250) NULL,
        [IsLocked] BIT NOT NULL CONSTRAINT [DF_FinancialQuarter_IsLocked] DEFAULT 0,
        [IsClosed] BIT NOT NULL CONSTRAINT [DF_FinancialQuarter_IsClosed] DEFAULT 0,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_FinancialQuarter_CreatedAt] DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT [FK_FinancialQuarter_FinancialYear_FinancialYearId] 
            FOREIGN KEY ([FinancialYearId]) REFERENCES [dbo].[FinancialYear]([Id]) ON DELETE CASCADE
    );

    CREATE UNIQUE INDEX [IX_FinancialQuarter_Year_Quarter] ON [dbo].[FinancialQuarter]([FinancialYearId], [QuarterCode]);
    CREATE INDEX [IX_FinancialQuarter_Dates] ON [dbo].[FinancialQuarter]([StartDate], [EndDate]);
    CREATE INDEX [IX_FinancialQuarter_Year_Number] ON [dbo].[FinancialQuarter]([FinancialYearId], [QuarterNumber]);
END;

IF NOT EXISTS (SELECT 1 FROM [dbo].[FinancialYear] WHERE [FinYearCode] = '2026/2027')
BEGIN
    INSERT INTO [dbo].[FinancialYear] ([FinYearCode], [StartYear], [EndYear], [StartDate], [EndDate], [Description], [StatusCode], [IsActive], [IsClosed], [CreatedBy])
    VALUES ('2026/2027', 2026, 2027, '2026-04-01', '2027-03-31', 'Official statutory merSETA 2026/2027 financial scheme year.', 'Active', 1, 0, 'SYSTEM_SEEDED');

    DECLARE @fy2026Id INT = SCOPE_IDENTITY();

    INSERT INTO [dbo].[FinancialQuarter] ([FinancialYearId], [QuarterCode], [QuarterNumber], [StartDate], [EndDate], [Description], [CreatedBy])
    VALUES 
        (@fy2026Id, 'Q1', 1, '2026-04-01', '2026-06-30', 'Quarter 1: April to June 2026', 'SYSTEM_SEEDED'),
        (@fy2026Id, 'Q2', 2, '2026-07-01', '2026-09-30', 'Quarter 2: July to September 2026', 'SYSTEM_SEEDED'),
        (@fy2026Id, 'Q3', 3, '2026-10-01', '2026-12-31', 'Quarter 3: October to December 2026', 'SYSTEM_SEEDED'),
        (@fy2026Id, 'Q4', 4, '2027-01-01', '2027-03-31', 'Quarter 4: January to March 2027', 'SYSTEM_SEEDED');
END;

IF NOT EXISTS (SELECT 1 FROM [dbo].[FinancialYear] WHERE [FinYearCode] = '2025/2026')
BEGIN
    INSERT INTO [dbo].[FinancialYear] ([FinYearCode], [StartYear], [EndYear], [StartDate], [EndDate], [Description], [StatusCode], [IsActive], [IsClosed], [CreatedBy])
    VALUES ('2025/2026', 2025, 2026, '2025-04-01', '2026-03-31', 'Concluded merSETA 2025/2026 financial scheme year.', 'Audited', 0, 1, 'SYSTEM_SEEDED');

    DECLARE @fy2025Id INT = SCOPE_IDENTITY();

    INSERT INTO [dbo].[FinancialQuarter] ([FinancialYearId], [QuarterCode], [QuarterNumber], [StartDate], [EndDate], [Description], [IsLocked], [IsClosed], [CreatedBy])
    VALUES 
        (@fy2025Id, 'Q1', 1, '2025-04-01', '2025-06-30', 'Quarter 1: April to June 2025', 1, 1, 'SYSTEM_SEEDED'),
        (@fy2025Id, 'Q2', 2, '2025-07-01', '2025-09-30', 'Quarter 2: July to September 2025', 1, 1, 'SYSTEM_SEEDED'),
        (@fy2025Id, 'Q3', 3, '2025-10-01', '2025-12-31', 'Quarter 3: October to December 2025', 1, 1, 'SYSTEM_SEEDED'),
        (@fy2025Id, 'Q4', 4, '2026-01-01', '2026-03-31', 'Quarter 4: January to March 2026', 1, 1, 'SYSTEM_SEEDED');
END;

IF NOT EXISTS (SELECT 1 FROM [dbo].[FinancialYear] WHERE [FinYearCode] = '2027/2028')
BEGIN
    INSERT INTO [dbo].[FinancialYear] ([FinYearCode], [StartYear], [EndYear], [StartDate], [EndDate], [Description], [StatusCode], [IsActive], [IsClosed], [CreatedBy])
    VALUES ('2027/2028', 2027, 2028, '2027-04-01', '2028-03-31', 'Upcoming merSETA 2027/2028 financial scheme year (Leap year Feb 2028).', 'Upcoming', 1, 0, 'SYSTEM_SEEDED');

    DECLARE @fy2027Id INT = SCOPE_IDENTITY();

    INSERT INTO [dbo].[FinancialQuarter] ([FinancialYearId], [QuarterCode], [QuarterNumber], [StartDate], [EndDate], [Description], [CreatedBy])
    VALUES 
        (@fy2027Id, 'Q1', 1, '2027-04-01', '2027-06-30', 'Quarter 1: April to June 2027', 'SYSTEM_SEEDED'),
        (@fy2027Id, 'Q2', 2, '2027-07-01', '2027-09-30', 'Quarter 2: July to September 2027', 'SYSTEM_SEEDED'),
        (@fy2027Id, 'Q3', 3, '2027-10-01', '2027-12-31', 'Quarter 3: October to December 2027', 'SYSTEM_SEEDED'),
        (@fy2027Id, 'Q4', 4, '2028-01-01', '2028-03-31', 'Quarter 4: January to March 2028 (29 days in Feb 2028)', 'SYSTEM_SEEDED');
END;
";
            }

            await context.Database.ExecuteSqlRawAsync(sql);
            logger?.LogInformation("Phase13FiscalCalendarMigrator: FinancialYear & FinancialQuarter tables successfully verified/seeded.");
        }
        catch (Exception ex)
        {
            logger?.LogError(ex, "Phase13FiscalCalendarMigrator: Failed to execute fiscal calendar schema migration.");
            throw;
        }
    }
}
