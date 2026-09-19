using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Phase 56: Enterprise SQL Server Concurrency & Database Engine Optimization Migrator.
/// Enforces AUTO_CLOSE OFF, READ_COMMITTED_SNAPSHOT (RCSI) ON, and ALLOW_SNAPSHOT_ISOLATION ON
/// to eliminate reader/writer lock blocking and prevent Deadlock 1205 contention.
/// </summary>
public static class Phase56DatabaseEngineOptimizationMigrator
{
    public static async Task MigrateAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        if (!context.Database.IsSqlServer())
        {
            logger?.LogInformation("Database is not SQL Server. Skipping Phase 56 engine optimizations.");
            return;
        }

        try
        {
            var dbName = context.Database.GetDbConnection().Database;
            if (string.IsNullOrWhiteSpace(dbName))
            {
                logger?.LogWarning("Phase 56: Could not determine current database name. Skipping ALTER DATABASE.");
                return;
            }

            logger?.LogInformation("Phase 56: Verifying SQL Server engine isolation and concurrency options for '{DbName}'...", dbName);

            // Execute outside user transactions
            const string verifySql = @"
                SELECT 
                    is_read_committed_snapshot_on AS IsRcsi,
                    snapshot_isolation_state AS SnapshotState,
                    is_auto_close_on AS IsAutoClose
                FROM sys.databases 
                WHERE name = DB_NAME();";

            using var cmd = context.Database.GetDbConnection().CreateCommand();
            cmd.CommandText = verifySql;

            if (context.Database.GetDbConnection().State != System.Data.ConnectionState.Open)
            {
                await context.Database.OpenConnectionAsync();
            }

            bool isRcsi = false;
            byte snapshotState = 0;
            bool isAutoClose = false;

            using (var reader = await cmd.ExecuteReaderAsync())
            {
                if (await reader.ReadAsync())
                {
                    isRcsi = reader.GetBoolean(0);
                    snapshotState = reader.GetByte(1);
                    isAutoClose = reader.GetBoolean(2);
                }
            }

            if (isAutoClose)
            {
                logger?.LogInformation("Phase 56: Disabling AUTO_CLOSE on database '{DbName}'...", dbName);
                await context.Database.ExecuteSqlRawAsync("ALTER DATABASE CURRENT SET AUTO_CLOSE OFF WITH NO_WAIT;");
                logger?.LogInformation("Phase 56: AUTO_CLOSE successfully disabled.");
            }

            if (!isRcsi)
            {
                logger?.LogInformation("Phase 56: Enabling READ_COMMITTED_SNAPSHOT (RCSI) on database '{DbName}'...", dbName);
                await context.Database.ExecuteSqlRawAsync("ALTER DATABASE CURRENT SET READ_COMMITTED_SNAPSHOT ON WITH ROLLBACK IMMEDIATE;");
                logger?.LogInformation("Phase 56: READ_COMMITTED_SNAPSHOT successfully enabled.");
            }

            if (snapshotState == 0)
            {
                logger?.LogInformation("Phase 56: Enabling ALLOW_SNAPSHOT_ISOLATION on database '{DbName}'...", dbName);
                await context.Database.ExecuteSqlRawAsync("ALTER DATABASE CURRENT SET ALLOW_SNAPSHOT_ISOLATION ON;");
                logger?.LogInformation("Phase 56: ALLOW_SNAPSHOT_ISOLATION successfully enabled.");
            }

            logger?.LogInformation("Phase 56: SQL Server engine optimization verified: AUTO_CLOSE=OFF, RCSI=ON, SNAPSHOT_ISOLATION=ON.");
        }
        catch (Exception ex)
        {
            // Log as warning rather than breaking startup in restricted environments
            logger?.LogWarning(ex, "Phase 56: Unable to apply SQL Server engine settings (insufficient ALTER DATABASE permissions or locked database).");
        }
    }
}
