using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Phase 16 Database Migrator:
/// 1. Promotes high-volume transaction primary keys and audit target RecordId to 64-bit BIGINT.
/// 2. Applies physical FOREIGN KEY constraints across core enterprise tables.
/// </summary>
public static class Phase16PhysicalConstraintsAndBigIntMigrator
{
    public static async Task MigrateAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        if (context.Database.IsSqlServer())
        {
            try
            {
                // 1. Run BIGINT promotion script
                var scriptPath10 = Path.Combine(AppContext.BaseDirectory, "Data", "SqlScripts", "V2026_10_Promote_High_Volume_PKs_To_BigInt.sql");
                if (!File.Exists(scriptPath10))
                {
                    scriptPath10 = Path.Combine(Directory.GetCurrentDirectory(), "dotnet", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_10_Promote_High_Volume_PKs_To_BigInt.sql");
                }
                if (!File.Exists(scriptPath10))
                {
                    scriptPath10 = Path.Combine(Directory.GetCurrentDirectory(), "..", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_10_Promote_High_Volume_PKs_To_BigInt.sql");
                }

                if (File.Exists(scriptPath10))
                {
                    var sql10 = await File.ReadAllTextAsync(scriptPath10);
                    await context.Database.ExecuteSqlRawAsync(sql10);
                    logger?.LogInformation("Executed V2026_10_Promote_High_Volume_PKs_To_BigInt.sql successfully.");
                }

                // 2. Run Physical Foreign Key constraints script
                var scriptPath11 = Path.Combine(AppContext.BaseDirectory, "Data", "SqlScripts", "V2026_11_Add_Physical_Foreign_Key_Constraints.sql");
                if (!File.Exists(scriptPath11))
                {
                    scriptPath11 = Path.Combine(Directory.GetCurrentDirectory(), "dotnet", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_11_Add_Physical_Foreign_Key_Constraints.sql");
                }
                if (!File.Exists(scriptPath11))
                {
                    scriptPath11 = Path.Combine(Directory.GetCurrentDirectory(), "..", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_11_Add_Physical_Foreign_Key_Constraints.sql");
                }

                if (File.Exists(scriptPath11))
                {
                    var sql11 = await File.ReadAllTextAsync(scriptPath11);
                    await context.Database.ExecuteSqlRawAsync(sql11);
                    logger?.LogInformation("Executed V2026_11_Add_Physical_Foreign_Key_Constraints.sql successfully.");
                }
            }
            catch (Exception ex)
            {
                logger?.LogWarning(ex, "Phase 16 migration logged a warning or partial skip.");
            }
        }
    }
}
