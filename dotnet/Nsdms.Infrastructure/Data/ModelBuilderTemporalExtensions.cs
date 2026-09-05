using Microsoft.EntityFrameworkCore;
using Nsdms.Domain.Common;
using Nsdms.Domain.Lookups;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Configures SQL Server System-Versioned Temporal Tables with the 'history' schema for all persistent domain entities.
/// </summary>
public static class ModelBuilderTemporalExtensions
{
    /// <summary>
    /// Entities explicitly excluded from temporal versioning (e.g. append-only audit log tables, transient lease locks, Identity claims/tokens).
    /// </summary>
    public static readonly HashSet<string> ExcludedEntityTypes = new(StringComparer.OrdinalIgnoreCase)
    {
        "AuditLog",
        "WorkflowHistory",
        "WorkflowTaskLease",
        "SystemFeatureFlag",
        "SystemConfig",
        "WizardDraftSession",
        "AppUserRole",
        "AppUserClaim",
        "AppUserLogin",
        "AppRoleClaim",
        "AppUserToken",
        "IdentityUserRole`1",
        "IdentityUserClaim`1",
        "IdentityUserLogin`1",
        "IdentityRoleClaim`1",
        "IdentityUserToken`1"
    };

    /// <summary>
    /// Applies SQL Server System-Versioned Temporal Table configurations to all domain entities mapped in EF Core,
    /// storing all historical row revisions in the 'history' schema.
    /// </summary>
    public static ModelBuilder ApplyTemporalTables(this ModelBuilder modelBuilder)
    {
        // Only enable EF Core temporal mapping if explicitly requested in environment (e.g. after full DDL migration)
        if (!string.Equals(Environment.GetEnvironmentVariable("ENABLE_EF_TEMPORAL_TABLES"), "true", StringComparison.OrdinalIgnoreCase))
        {
            return modelBuilder;
        }

        foreach (var entityType in modelBuilder.Model.GetEntityTypes())
        {
            var clrType = entityType.ClrType;
            if (clrType == null) continue;

            // Skip static lookups
            if (typeof(BaseLookupType).IsAssignableFrom(clrType)) continue;

            // Skip explicitly excluded entities
            if (ExcludedEntityTypes.Contains(clrType.Name)) continue;

            // Skip owned types or types without primary key
            if (entityType.IsOwned() || entityType.FindPrimaryKey() == null) continue;

            var tableName = entityType.GetTableName() ?? clrType.Name;
            var schema = entityType.GetSchema();

            // Skip lookup schema
            if (string.Equals(schema, "lookup", StringComparison.OrdinalIgnoreCase)) continue;

            var historyTableName = $"{tableName}History";

            // Set relational temporal table metadata directly on the entity type
            entityType.SetIsTemporal(true);
            entityType.SetHistoryTableName(historyTableName);
            entityType.SetHistoryTableSchema("history");
        }

        return modelBuilder;
    }
}
