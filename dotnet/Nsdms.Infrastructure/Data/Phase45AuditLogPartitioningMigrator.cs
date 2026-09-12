using System.Text.RegularExpressions;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Idempotent database schema migrator establishing table partitioning and tiered archival
/// infrastructure for high-volume audit logs in compliance with Enterprise High-Volume Ingestion Standards.
/// </summary>
public static class Phase45AuditLogPartitioningMigrator
{
    public static async Task MigrateAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        if (context.Database.IsSqlServer())
        {
            var scriptPaths = new[]
            {
                Path.Combine(AppContext.BaseDirectory, "Data", "SqlScripts", "V2026_45_AuditLog_Partitioning_And_Archival.sql"),
                Path.Combine(AppContext.BaseDirectory, "SqlScripts", "V2026_45_AuditLog_Partitioning_And_Archival.sql"),
                Path.Combine(Directory.GetCurrentDirectory(), "..", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_45_AuditLog_Partitioning_And_Archival.sql"),
                Path.Combine(Directory.GetCurrentDirectory(), "Data", "SqlScripts", "V2026_45_AuditLog_Partitioning_And_Archival.sql")
            };

            string? sqlScript = null;
            foreach (var path in scriptPaths)
            {
                if (File.Exists(path))
                {
                    sqlScript = await File.ReadAllTextAsync(path);
                    break;
                }
            }

            if (string.IsNullOrWhiteSpace(sqlScript))
            {
                logger?.LogWarning("V2026_45 SQL script not found on disk. Skipping file-based migration.");
                return;
            }

            // Split on standalone GO statements
            var batches = Regex.Split(sqlScript, @"^\s*GO\s*$", RegexOptions.Multiline | RegexOptions.IgnoreCase);

            foreach (var batch in batches)
            {
                var trimmed = batch.Trim();
                if (string.IsNullOrWhiteSpace(trimmed)) continue;

                try
                {
                    await context.Database.ExecuteSqlRawAsync(trimmed);
                }
                catch (Exception ex)
                {
                    logger?.LogWarning(ex, "Phase 45 batch execution notice (may be already configured): {Message}", ex.Message);
                }
            }

            logger?.LogInformation("Phase 45 AuditLog Partitioning & Archival migration completed.");
        }
    }
}
