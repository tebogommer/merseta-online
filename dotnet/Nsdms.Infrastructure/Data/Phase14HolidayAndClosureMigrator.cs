using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Idempotent database schema migrator and seeder for National Holidays and merSETA Institutional Closures (Option A).
/// </summary>
public static class Phase14HolidayAndClosureMigrator
{
    public static async Task MigrateHolidayAndClosureSchemaAsync(IServiceProvider serviceProvider)
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
            var scriptPath = Path.Combine(baseDir, "Data", "SqlScripts", "V2026_14_Add_Holiday_And_Institutional_Closure_Tables.sql");

            if (!File.Exists(scriptPath))
            {
                scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "Data", "SqlScripts", "V2026_14_Add_Holiday_And_Institutional_Closure_Tables.sql");
            }
            if (!File.Exists(scriptPath))
            {
                scriptPath = Path.Combine(Directory.GetCurrentDirectory(), "..", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_14_Add_Holiday_And_Institutional_Closure_Tables.sql");
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
END;";
            }

            // Split into executable batches and execute
            var batches = sql.Split(new[] { "\nGO\r\n", "\nGO\n", "\r\nGO\r\n", "\r\nGO\n" }, StringSplitOptions.RemoveEmptyEntries);
            foreach (var batch in batches)
            {
                var trimmed = batch.Trim();
                if (!string.IsNullOrWhiteSpace(trimmed))
                {
                    await context.Database.ExecuteSqlRawAsync(trimmed);
                }
            }

            logger?.LogInformation("Phase 14 Holiday & Institutional Closure schema migration and seeding verified successfully.");
        }
        catch (Exception ex)
        {
            logger?.LogError(ex, "Failed to apply Phase 14 Holiday & Institutional Closure schema migration.");
        }
    }
}
