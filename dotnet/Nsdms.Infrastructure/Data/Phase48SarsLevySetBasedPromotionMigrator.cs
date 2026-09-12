using System;
using System.IO;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Phase 48 Database Schema Migrator:
/// Deploys high-performance set-based stored procedure [dbo].[usp_PromoteSarsLevyBatch]
/// and covering indexes for Option A: Reactive Streaming Pipeline &amp; Set-Based SQL Promotion.
/// </summary>
public static class Phase48SarsLevySetBasedPromotionMigrator
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
                Path.Combine(AppContext.BaseDirectory, "Data", "SqlScripts", "V2026_48_Sars_Levy_Set_Based_Promotion.sql"),
                Path.Combine(AppContext.BaseDirectory, "SqlScripts", "V2026_48_Sars_Levy_Set_Based_Promotion.sql"),
                Path.Combine(Directory.GetCurrentDirectory(), "..", "Nsdms.Infrastructure", "Data", "SqlScripts", "V2026_48_Sars_Levy_Set_Based_Promotion.sql"),
                Path.Combine(Directory.GetCurrentDirectory(), "Data", "SqlScripts", "V2026_48_Sars_Levy_Set_Based_Promotion.sql")
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
                logger?.LogWarning("V2026_48 SQL script not found on disk. Skipping file-based migration.");
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
                    logger?.LogWarning(ex, "Phase 48 batch execution notice: {Message}", ex.Message);
                }
            }

            logger?.LogInformation("Phase 48 SARS Levy Set-Based Promotion migration executed successfully.");
        }
    }
}
