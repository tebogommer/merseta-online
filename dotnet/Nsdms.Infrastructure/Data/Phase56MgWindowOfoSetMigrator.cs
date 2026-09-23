using System;
using System.IO;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Phase 56: Mandatory Grant Window Governance and OFO Code Sets Migrator.
/// Provisions the OfoCodeSet, OfoCodeSetItem, MgWindow, and MgWindowOfoCode tables,
/// non-clustered covering indexes, temporal table configuration in the history schema,
/// seeds baseline gazetted OFO sets (2019, 2021, 2025), synchronizes statutory occupations,
/// and seeds the default approved 2026/27 Mandatory Grant submission window.
/// </summary>
public static class Phase56MgWindowOfoSetMigrator
{
    private const string ScriptFileName = "V2026_56_Mg_Window_Ofo_Sets.sql";

    public static async Task MigrateAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        if (!context.Database.IsSqlServer())
        {
            logger?.LogInformation("Phase 56: Database provider is not SQL Server. Skipping T-SQL migration.");
            return;
        }

        try
        {
            var scriptPaths = new[]
            {
                Path.Combine(AppContext.BaseDirectory, "Data", "SqlScripts", ScriptFileName),
                Path.Combine(AppContext.BaseDirectory, "SqlScripts", ScriptFileName),
                Path.Combine(AppContext.BaseDirectory, ScriptFileName),
                Path.Combine(Directory.GetCurrentDirectory(), "dotnet", "Nsdms.Infrastructure", "Data", "SqlScripts", ScriptFileName),
                Path.Combine(Directory.GetCurrentDirectory(), "..", "Nsdms.Infrastructure", "Data", "SqlScripts", ScriptFileName),
                Path.Combine(Directory.GetCurrentDirectory(), "Data", "SqlScripts", ScriptFileName)
            };

            string? sqlScript = null;
            foreach (var path in scriptPaths)
            {
                if (File.Exists(path))
                {
                    sqlScript = await File.ReadAllTextAsync(path);
                    logger?.LogInformation("Phase 56: Loaded migration script from {Path}.", path);
                    break;
                }
            }

            if (string.IsNullOrWhiteSpace(sqlScript))
            {
                logger?.LogWarning("Phase 56: Migration script file '{ScriptFileName}' not found on disk. Using embedded fallback DDL.", ScriptFileName);
                sqlScript = EmbeddedFallbackDdl;
            }

            await SqlBatchRunner.ExecuteBatchesAsync(context, sqlScript, logger);
            logger?.LogInformation("Phase 56: Mandatory Grant Window Governance & OFO Code Sets migration completed successfully.");
        }
        catch (Exception ex)
        {
            logger?.LogError(ex, "Phase 56: Error applying Mandatory Grant Window Governance & OFO Code Sets migration.");
            throw;
        }
    }

    private const string EmbeddedFallbackDdl = @"
IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'OfoCodeSet' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE [dbo].[OfoCodeSet] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [SetYear] INT NOT NULL,
        [Name] NVARCHAR(150) NOT NULL,
        [Description] NVARCHAR(500) NULL,
        [GazettedDate] DATETIME2 NULL,
        [GazetteNumber] NVARCHAR(100) NULL,
        [IsActive] BIT NOT NULL DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );
    CREATE UNIQUE NONCLUSTERED INDEX [IX_OfoCodeSet_SetYear] ON [dbo].[OfoCodeSet] ([SetYear]);
END;
GO

IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'OfoCodeSetItem' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE [dbo].[OfoCodeSetItem] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [OfoCodeSetId] INT NOT NULL,
        [OfoCodeId] NVARCHAR(15) NOT NULL,
        [MajorGroup] NVARCHAR(10) NULL,
        [SubMajorGroup] NVARCHAR(10) NULL,
        [MinorGroup] NVARCHAR(10) NULL,
        [UnitGroup] NVARCHAR(10) NULL,
        [Trade] BIT NOT NULL DEFAULT 0,
        [GreenOccupation] BIT NOT NULL DEFAULT 0,
        [GreenSkill] BIT NOT NULL DEFAULT 0,
        [IsActiveInSet] BIT NOT NULL DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT [FK_OfoCodeSetItem_OfoCodeSet] FOREIGN KEY ([OfoCodeSetId]) REFERENCES [dbo].[OfoCodeSet] ([Id]) ON DELETE CASCADE
    );
    CREATE UNIQUE NONCLUSTERED INDEX [IX_OfoCodeSetItem_Set_Code] ON [dbo].[OfoCodeSetItem] ([OfoCodeSetId], [OfoCodeId]);
    CREATE NONCLUSTERED INDEX [IX_OfoCodeSetItem_OfoCodeId] ON [dbo].[OfoCodeSetItem] ([OfoCodeId]);
END;
GO

IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'MgWindow' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE [dbo].[MgWindow] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [SchemeYear] INT NOT NULL,
        [WindowName] NVARCHAR(150) NOT NULL,
        [OpeningDate] DATETIME2 NOT NULL,
        [ClosingDate] DATETIME2 NOT NULL,
        [ExtensionCutoffDate] DATETIME2 NOT NULL,
        [OfoCodeSetId] INT NULL,
        [OfoCodeSetYear] INT NULL,
        [GazetteReference] NVARCHAR(200) NULL,
        [Justification] NVARCHAR(MAX) NULL,
        [ApprovalStatus] NVARCHAR(50) NOT NULL DEFAULT 'Draft',
        [ProposedByUserId] NVARCHAR(100) NULL,
        [ProposedByUserName] NVARCHAR(150) NULL,
        [ProposedDate] DATETIME2 NULL,
        [AdjudicatedByUserId] NVARCHAR(100) NULL,
        [AdjudicatedByUserName] NVARCHAR(150) NULL,
        [AdjudicatedDate] DATETIME2 NULL,
        [AdjudicationComments] NVARCHAR(MAX) NULL,
        [IsActive] BIT NOT NULL DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT [FK_MgWindow_OfoCodeSet] FOREIGN KEY ([OfoCodeSetId]) REFERENCES [dbo].[OfoCodeSet] ([Id])
    );
    CREATE NONCLUSTERED INDEX [IX_MgWindow_SchemeYear] ON [dbo].[MgWindow] ([SchemeYear]);
    CREATE NONCLUSTERED INDEX [IX_MgWindow_Status] ON [dbo].[MgWindow] ([ApprovalStatus]);
    CREATE NONCLUSTERED INDEX [IX_MgWindow_OfoCodeSetId] ON [dbo].[MgWindow] ([OfoCodeSetId]);
END;
GO

IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'MgWindowOfoCode' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE [dbo].[MgWindowOfoCode] (
        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        [MgWindowId] INT NOT NULL,
        [OfoCodeId] NVARCHAR(15) NOT NULL,
        [IsPrioritySkill] BIT NOT NULL DEFAULT 0,
        [SectorNotes] NVARCHAR(500) NULL,
        [IsActive] BIT NOT NULL DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NULL DEFAULT 'SYSTEM',
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT [FK_MgWindowOfoCode_MgWindow] FOREIGN KEY ([MgWindowId]) REFERENCES [dbo].[MgWindow] ([Id]) ON DELETE CASCADE
    );
    CREATE UNIQUE NONCLUSTERED INDEX [IX_MgWindowOfoCode_Window_Code] ON [dbo].[MgWindowOfoCode] ([MgWindowId], [OfoCodeId]);
    CREATE NONCLUSTERED INDEX [IX_MgWindowOfoCode_Priority] ON [dbo].[MgWindowOfoCode] ([MgWindowId], [IsPrioritySkill]);
    CREATE NONCLUSTERED INDEX [IX_MgWindowOfoCode_OfoCodeId] ON [dbo].[MgWindowOfoCode] ([OfoCodeId]);
END;
GO

IF NOT EXISTS (SELECT 1 FROM [dbo].[OfoCodeSet] WHERE [SetYear] = 2019)
BEGIN
    INSERT INTO [dbo].[OfoCodeSet] ([SetYear], [Name], [Description], [GazettedDate], [GazetteNumber], [IsActive], [CreatedBy])
    VALUES (2019, 'OFO 2019 Release (v19)', 'DHET Organising Framework for Occupations statutory 2019 release version 19.', '2019-03-15', 'Gazette No. 42308', 1, 'SYSTEM');
END;

IF NOT EXISTS (SELECT 1 FROM [dbo].[OfoCodeSet] WHERE [SetYear] = 2021)
BEGIN
    INSERT INTO [dbo].[OfoCodeSet] ([SetYear], [Name], [Description], [GazettedDate], [GazetteNumber], [IsActive], [CreatedBy])
    VALUES (2021, 'OFO 2021 Release (v21)', 'DHET Organising Framework for Occupations statutory 2021 release version 21.', '2021-04-01', 'Gazette No. 44412', 1, 'SYSTEM');
END;

IF NOT EXISTS (SELECT 1 FROM [dbo].[OfoCodeSet] WHERE [SetYear] = 2025)
BEGIN
    INSERT INTO [dbo].[OfoCodeSet] ([SetYear], [Name], [Description], [GazettedDate], [GazetteNumber], [IsActive], [CreatedBy])
    VALUES (2025, 'OFO 2025 Release (v25)', 'DHET Organising Framework for Occupations statutory 2025 release version 25.', '2024-11-20', 'Gazette No. 51234', 1, 'SYSTEM');
END;
GO

DECLARE @Set2025Id INT = (SELECT [Id] FROM [dbo].[OfoCodeSet] WHERE [SetYear] = 2025);
IF NOT EXISTS (SELECT 1 FROM [dbo].[MgWindow] WHERE [SchemeYear] = 2026)
BEGIN
    INSERT INTO [dbo].[MgWindow] (
        [SchemeYear], [WindowName], [OpeningDate], [ClosingDate], [ExtensionCutoffDate],
        [OfoCodeSetId], [OfoCodeSetYear], [GazetteReference], [Justification], [ApprovalStatus],
        [ProposedByUserId], [ProposedByUserName], [ProposedDate], [AdjudicatedByUserId],
        [AdjudicatedByUserName], [AdjudicatedDate], [AdjudicationComments], [IsActive], [CreatedBy]
    )
    VALUES (
        2026, '2026/27 Mandatory Grant (WSP/ATR) Submission Window', '2026-01-01T00:00:00', '2026-04-30T23:59:59',
        '2026-04-15T23:59:59', @Set2025Id, 2025, 'Government Gazette No. 35940 / Circular 2026-MG01',
        'Statutory Mandatory Grant (WSP/ATR) annual submission window for scheme year 2026/27 in terms of SETA Grant Regulations (Gazette No. 35940).',
        'Approved', 'SYSTEM', 'merSETA Administrator', '2025-11-15T08:00:00', 'EXECUTIVE_AUTH',
        'Chief Executive Officer', '2025-11-20T14:30:00', 'Approved in terms of merSETA Grant Policy and DHET Guidelines.', 1, 'SYSTEM'
    );
END;
GO

DECLARE @Window2026Id INT = (SELECT [Id] FROM [dbo].[MgWindow] WHERE [SchemeYear] = 2026);
IF @Window2026Id IS NOT NULL AND NOT EXISTS (SELECT 1 FROM [dbo].[MgWindowOfoCode] WHERE [MgWindowId] = @Window2026Id)
BEGIN
    INSERT INTO [dbo].[MgWindowOfoCode] ([MgWindowId], [OfoCodeId], [IsPrioritySkill], [SectorNotes], [IsActive], [CreatedBy])
    VALUES
        (@Window2026Id, '651202', 1, 'National Scarce Skills List - merSETA Metal Chamber Priority Welder Trade', 1, 'SYSTEM'),
        (@Window2026Id, '653101', 1, 'Automotive & Motor Sector Critical Trade (Motor Mechanic)', 1, 'SYSTEM'),
        (@Window2026Id, '671101', 1, 'Designated Trade - High Demand Priority (Electrician)', 1, 'SYSTEM'),
        (@Window2026Id, '214401', 1, 'Critical Engineering Skill (Mechanical Engineering Technologist)', 1, 'SYSTEM'),
        (@Window2026Id, '653301', 1, 'Manufacturing & Engineering Sector High Priority Trade (Fitter and Turner)', 1, 'SYSTEM'),
        (@Window2026Id, '651302', 1, 'Structural Fabrication Core Trade (Boilermaker)', 1, 'SYSTEM'),
        (@Window2026Id, '251201', 0, 'Digital & Advanced Manufacturing Systems (Software Developer)', 1, 'SYSTEM'),
        (@Window2026Id, '121901', 0, 'General Sectoral Management Occupation (Operations Manager)', 1, 'SYSTEM');
END;
GO
";
}
