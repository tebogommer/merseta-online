using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Nsdms.Domain.Entities;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Phase 53 Migrator: Provisions the living employer employee roster schema and seeds representative
/// workforce records for Toyota SA across major OFO occupational categories.
/// Supports Option B: The Living Employer Roster with 1-Click WSP Auto-Harvest.
/// </summary>
public class Phase53OrganisationEmployeeRosterMigrator
{
    private readonly IServiceProvider? _serviceProvider;
    private readonly NsdmsDbContext? _context;
    private readonly ILogger? _logger;

    public Phase53OrganisationEmployeeRosterMigrator(IServiceProvider? serviceProvider = null, NsdmsDbContext? context = null)
    {
        _serviceProvider = serviceProvider;
        _context = context;
        _logger = serviceProvider?.GetService<ILogger<Phase53OrganisationEmployeeRosterMigrator>>();
    }

    public static async Task MigrateAsync(IServiceProvider serviceProvider, CancellationToken ct = default)
    {
        var migrator = new Phase53OrganisationEmployeeRosterMigrator(serviceProvider);
        await migrator.MigrateAsync(ct);
    }

    public async Task MigrateAsync(CancellationToken ct = default)
    {
        AsyncServiceScope? scope = null;
        NsdmsDbContext db;

        if (_context != null)
        {
            db = _context;
        }
        else if (_serviceProvider != null)
        {
            scope = _serviceProvider.CreateAsyncScope();
            db = scope.Value.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        }
        else
        {
            throw new InvalidOperationException("No DbContext or IServiceProvider provided to Phase53OrganisationEmployeeRosterMigrator.");
        }

        try
        {
            // 1. Execute SQL DDL migration script for SQL Server
            if (db.Database.IsSqlServer())
            {
                await ExecuteDdlScriptAsync(db, ct);
            }

            // 2. Seed representative sample workforce for Toyota SA
            await SeedToyotaSampleEmployeesAsync(db, ct);
        }
        finally
        {
            if (scope.HasValue)
            {
                await scope.Value.DisposeAsync();
            }
        }
    }

    private async Task ExecuteDdlScriptAsync(NsdmsDbContext db, CancellationToken ct)
    {
        var scriptName = "V2026_16_Organisation_Employee_Roster.sql";
        var candidatePaths = new[]
        {
            Path.Combine(AppContext.BaseDirectory, "Data", "SqlScripts", scriptName),
            Path.Combine(AppContext.BaseDirectory, "SqlScripts", scriptName),
            Path.Combine(AppContext.BaseDirectory, "..", "..", "..", "..", "Nsdms.Infrastructure", "Data", "SqlScripts", scriptName),
            Path.Combine(Directory.GetCurrentDirectory(), "dotnet", "Nsdms.Infrastructure", "Data", "SqlScripts", scriptName),
            Path.Combine(Directory.GetCurrentDirectory(), "..", "Nsdms.Infrastructure", "Data", "SqlScripts", scriptName)
        };

        var sqlFile = candidatePaths.FirstOrDefault(File.Exists);
        if (sqlFile != null)
        {
            var sql = await File.ReadAllTextAsync(sqlFile, ct);
            await db.Database.ExecuteSqlRawAsync(sql, ct);
            _logger?.LogInformation("[SCHEMA MIGRATOR] Phase 53 OrganisationEmployee roster DDL executed from {Path}", sqlFile);
        }
        else
        {
            // Fallback inline idempotent DDL
            const string inlineSql = @"
                SET QUOTED_IDENTIFIER ON;
                SET ANSI_NULLS ON;

                IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'OrganisationEmployee' AND schema_id = SCHEMA_ID('dbo'))
                BEGIN
                    CREATE TABLE [dbo].[OrganisationEmployee] (
                        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_OrganisationEmployee] PRIMARY KEY CLUSTERED,
                        [OrganisationId] INT NOT NULL,
                        [PersonId] INT NOT NULL,
                        [OrganisationSiteId] INT NULL,
                        [EmployeeNumber] NVARCHAR(50) NULL,
                        [JobTitle] NVARCHAR(150) NULL,
                        [OfoCodeId] NVARCHAR(50) NULL,
                        [EmploymentTypeCode] NVARCHAR(50) NULL CONSTRAINT [DF_OrganisationEmployee_EmploymentType] DEFAULT ('PERMANENT'),
                        [EmploymentStatusCode] NVARCHAR(50) NULL CONSTRAINT [DF_OrganisationEmployee_EmploymentStatus] DEFAULT ('ACTIVE'),
                        [OccupationalCategoryCode] NVARCHAR(50) NULL,
                        [StartDate] DATETIME2 NULL,
                        [EndDate] DATETIME2 NULL,
                        [IsActive] BIT NOT NULL CONSTRAINT [DF_OrganisationEmployee_IsActive] DEFAULT (1),
                        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_OrganisationEmployee_CreatedAt] DEFAULT (SYSUTCDATETIME()),
                        [CreatedBy] NVARCHAR(150) NULL CONSTRAINT [DF_OrganisationEmployee_CreatedBy] DEFAULT ('SYSTEM'),
                        [ModifiedAt] DATETIME2 NULL,
                        [ModifiedBy] NVARCHAR(150) NULL,

                        CONSTRAINT [FK_OrganisationEmployee_Organisation] FOREIGN KEY ([OrganisationId]) REFERENCES [dbo].[Organisation] ([Id]) ON DELETE CASCADE,
                        CONSTRAINT [FK_OrganisationEmployee_Person] FOREIGN KEY ([PersonId]) REFERENCES [dbo].[Person] ([Id]),
                        CONSTRAINT [FK_OrganisationEmployee_OrganisationSite] FOREIGN KEY ([OrganisationSiteId]) REFERENCES [dbo].[OrganisationSite] ([Id])
                    );
                END;

                IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_OrganisationEmployee_OrganisationId' AND object_id = OBJECT_ID('dbo.OrganisationEmployee'))
                    CREATE NONCLUSTERED INDEX [IX_OrganisationEmployee_OrganisationId] ON [dbo].[OrganisationEmployee] ([OrganisationId]);

                IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_OrganisationEmployee_PersonId' AND object_id = OBJECT_ID('dbo.OrganisationEmployee'))
                    CREATE NONCLUSTERED INDEX [IX_OrganisationEmployee_PersonId] ON [dbo].[OrganisationEmployee] ([PersonId]);

                IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_OrganisationEmployee_OrganisationSiteId' AND object_id = OBJECT_ID('dbo.OrganisationEmployee'))
                    CREATE NONCLUSTERED INDEX [IX_OrganisationEmployee_OrganisationSiteId] ON [dbo].[OrganisationEmployee] ([OrganisationSiteId]);

                IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_OrganisationEmployee_OfoCodeId' AND object_id = OBJECT_ID('dbo.OrganisationEmployee'))
                    CREATE NONCLUSTERED INDEX [IX_OrganisationEmployee_OfoCodeId] ON [dbo].[OrganisationEmployee] ([OfoCodeId]);

                IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_OrganisationEmployee_IsActive' AND object_id = OBJECT_ID('dbo.OrganisationEmployee'))
                    CREATE NONCLUSTERED INDEX [IX_OrganisationEmployee_IsActive] ON [dbo].[OrganisationEmployee] ([IsActive]);

                IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_OrganisationEmployee_Org_Active' AND object_id = OBJECT_ID('dbo.OrganisationEmployee'))
                    CREATE NONCLUSTERED INDEX [IX_OrganisationEmployee_Org_Active] ON [dbo].[OrganisationEmployee] ([OrganisationId], [IsActive]);
            ";

            await db.Database.ExecuteSqlRawAsync(inlineSql, ct);
            _logger?.LogInformation("[SCHEMA MIGRATOR] Phase 53 OrganisationEmployee roster inline DDL executed successfully.");
        }
    }

    private async Task SeedToyotaSampleEmployeesAsync(NsdmsDbContext db, CancellationToken ct)
    {
        // 1. Locate Toyota SA organisation (OrganisationId = 1 or by SDL number)
        var toyota = await db.Organisations
            .Include(o => o.Sites)
            .FirstOrDefaultAsync(o => o.Id == 1 || o.SdlNumber == "L700100200" || o.TradingName.Contains("Toyota"), ct);

        if (toyota == null)
        {
            _logger?.LogInformation("[SAMPLE SEEDER] Toyota SA organisation not found. Skipping sample employee roster seeding.");
            return;
        }

        // 2. Check if roster already has employees for Toyota SA
        var existingCount = await db.OrganisationEmployees.CountAsync(e => e.OrganisationId == toyota.Id, ct);
        if (existingCount > 0)
        {
            _logger?.LogInformation("[SAMPLE SEEDER] OrganisationEmployee roster already seeded for Toyota SA ({Count} records).", existingCount);
            return;
        }

        // 3. Retrieve valid Person records
        var samplePeople = await db.People.OrderBy(p => p.Id).Take(10).ToListAsync(ct);
        if (samplePeople.Count == 0)
        {
            _logger?.LogWarning("[SAMPLE SEEDER] No Person records available to attach to OrganisationEmployee roster.");
            return;
        }

        var defaultSiteId = toyota.Sites.FirstOrDefault()?.Id;

        // 4. Build sample roster spanning major OFO occupational categories
        var sampleEmployees = new List<OrganisationEmployee>();

        // Employee 1: Managers (OFO 121901 - Corporate General Manager / Plant Operations Manager)
        if (samplePeople.Count > 0)
        {
            sampleEmployees.Add(new OrganisationEmployee
            {
                OrganisationId = toyota.Id,
                PersonId = samplePeople[0].Id,
                OrganisationSiteId = defaultSiteId,
                EmployeeNumber = "TSA-MGR-001",
                JobTitle = "Plant Operations Manager",
                OfoCodeId = "121901",
                EmploymentTypeCode = "PERMANENT",
                EmploymentStatusCode = "ACTIVE",
                OccupationalCategoryCode = "MANAGERS",
                StartDate = new DateTime(2018, 3, 1),
                IsActive = true,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = "SEEDER"
            });
        }

        // Employee 2: Professionals (OFO 214401 - Mechanical Engineer)
        if (samplePeople.Count > 1)
        {
            sampleEmployees.Add(new OrganisationEmployee
            {
                OrganisationId = toyota.Id,
                PersonId = samplePeople[1].Id,
                OrganisationSiteId = defaultSiteId,
                EmployeeNumber = "TSA-ENG-042",
                JobTitle = "Automotive Systems Mechanical Engineer",
                OfoCodeId = "214401",
                EmploymentTypeCode = "PERMANENT",
                EmploymentStatusCode = "ACTIVE",
                OccupationalCategoryCode = "PROFESSIONALS",
                StartDate = new DateTime(2020, 6, 15),
                IsActive = true,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = "SEEDER"
            });
        }

        // Employee 3: Technicians (OFO 311501 - Mechanical Engineering Technologist)
        if (samplePeople.Count > 2)
        {
            sampleEmployees.Add(new OrganisationEmployee
            {
                OrganisationId = toyota.Id,
                PersonId = samplePeople[2].Id,
                OrganisationSiteId = defaultSiteId,
                EmployeeNumber = "TSA-TECH-109",
                JobTitle = "Quality Assurance Robotics Technologist",
                OfoCodeId = "311501",
                EmploymentTypeCode = "PERMANENT",
                EmploymentStatusCode = "ACTIVE",
                OccupationalCategoryCode = "TECHNICIANS",
                StartDate = new DateTime(2021, 1, 10),
                IsActive = true,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = "SEEDER"
            });
        }

        // Employee 4: Skilled Craft (OFO 653101 - Automotive Motor Mechanic)
        if (samplePeople.Count > 3)
        {
            sampleEmployees.Add(new OrganisationEmployee
            {
                OrganisationId = toyota.Id,
                PersonId = samplePeople[3].Id,
                OrganisationSiteId = defaultSiteId,
                EmployeeNumber = "TSA-ART-255",
                JobTitle = "Senior Automotive Motor Mechanic",
                OfoCodeId = "653101",
                EmploymentTypeCode = "PERMANENT",
                EmploymentStatusCode = "ACTIVE",
                OccupationalCategoryCode = "SKILLED_CRAFT",
                StartDate = new DateTime(2022, 4, 1),
                IsActive = true,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = "SEEDER"
            });
        }

        // Employee 5: Technicians (OFO 311501 - Production Assembly Technician)
        if (samplePeople.Count > 4)
        {
            sampleEmployees.Add(new OrganisationEmployee
            {
                OrganisationId = toyota.Id,
                PersonId = samplePeople[4].Id,
                OrganisationSiteId = defaultSiteId,
                EmployeeNumber = "TSA-OPS-310",
                JobTitle = "Chassis Assembly Technician",
                OfoCodeId = "311501",
                EmploymentTypeCode = "PERMANENT",
                EmploymentStatusCode = "ACTIVE",
                OccupationalCategoryCode = "TECHNICIANS",
                StartDate = new DateTime(2023, 8, 1),
                IsActive = true,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = "SEEDER"
            });
        }

        // Employee 6: Clerical / Administration
        if (samplePeople.Count > 5)
        {
            sampleEmployees.Add(new OrganisationEmployee
            {
                OrganisationId = toyota.Id,
                PersonId = samplePeople[5].Id,
                OrganisationSiteId = defaultSiteId,
                EmployeeNumber = "TSA-ADM-401",
                JobTitle = "Logistics Planning Coordinator",
                OfoCodeId = "132407",
                EmploymentTypeCode = "PERMANENT",
                EmploymentStatusCode = "ACTIVE",
                OccupationalCategoryCode = "CLERICAL",
                StartDate = new DateTime(2023, 10, 1),
                IsActive = true,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = "SEEDER"
            });
        }

        await db.OrganisationEmployees.AddRangeAsync(sampleEmployees, ct);
        await db.SaveChangesAsync(ct);
        _logger?.LogInformation("[SAMPLE SEEDER] Successfully seeded {Count} OrganisationEmployee records for Toyota SA.", sampleEmployees.Count);
    }
}
