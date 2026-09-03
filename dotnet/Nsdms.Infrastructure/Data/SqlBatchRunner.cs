using System.Text.RegularExpressions;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Executes multi-batch T-SQL scripts safely across ADO.NET and EF Core
/// by splitting commands on SSMS "GO" batch separators.
/// </summary>
public static class SqlBatchRunner
{
    public static async Task ExecuteBatchesAsync(NsdmsDbContext context, string sql, ILogger? logger = null)
    {
        var batches = Regex.Split(sql, @"^\s*GO\s*$", RegexOptions.Multiline | RegexOptions.IgnoreCase);
        foreach (var batch in batches)
        {
            var trimmed = batch.Trim();
            if (!string.IsNullOrWhiteSpace(trimmed))
            {
                try
                {
                    await context.Database.ExecuteSqlRawAsync(trimmed);
                }
                catch (Exception ex)
                {
                    logger?.LogWarning(ex, "Batch execution warning: {Snippet}", trimmed.Length > 80 ? trimmed[..80] : trimmed);
                }
            }
        }
    }
}
