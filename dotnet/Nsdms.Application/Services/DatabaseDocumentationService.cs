using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;
using Nsdms.Application.Common;
using System.Text;

namespace Nsdms.Application.Services;

/// <summary>
/// Implements dynamic EF Core metadata introspection to generate in-app and exportable database schema documentation.
/// </summary>
public class DatabaseDocumentationService : IDatabaseDocumentationService
{
    private readonly INsdmsDbContextFactory _contextFactory;

    public DatabaseDocumentationService(INsdmsDbContextFactory contextFactory)
    {
        _contextFactory = contextFactory;
    }

    public async Task<List<TableDocumentationDto>> GetDatabaseSchemaDocumentationAsync()
    {
        var context = await _contextFactory.CreateDbContextAsync();
        var model = context.Model;
        var tables = new List<TableDocumentationDto>();

        foreach (var entityType in model.GetEntityTypes())
        {
            var tableName = SafeGetTableName(entityType);
            if (string.IsNullOrEmpty(tableName)) continue;

            var schemaName = SafeGetSchemaName(entityType);
            var tableDto = new TableDocumentationDto
            {
                TableName = tableName,
                SchemaName = schemaName,
                ClrTypeName = entityType.ClrType.Name,
                Description = SafeGetEntityComment(entityType) ?? GetEntityDefaultDescription(entityType.ClrType.Name)
            };

            // Primary Key
            var pk = entityType.FindPrimaryKey();
            if (pk != null)
            {
                tableDto.PrimaryKeyColumns = pk.Properties.Select(p => SafeGetColumnName(p)).ToList();
            }

            // Columns
            foreach (var prop in entityType.GetProperties())
            {
                var colName = SafeGetColumnName(prop);
                var isPk = tableDto.PrimaryKeyColumns.Contains(colName);
                var isFk = prop.IsForeignKey();
                var maxLength = prop.GetMaxLength();

                tableDto.Columns.Add(new ColumnDocumentationDto
                {
                    ColumnName = colName,
                    PropertyName = prop.Name,
                    StoreType = SafeGetColumnType(prop),
                    ClrTypeName = prop.ClrType.Name,
                    IsPrimaryKey = isPk,
                    IsForeignKey = isFk,
                    IsNullable = prop.IsNullable,
                    MaxLength = maxLength,
                    Description = SafeGetPropertyComment(prop) ?? GetPropertyDefaultDescription(prop.Name, isPk, isFk)
                });
            }

            // Foreign Keys
            foreach (var fk in entityType.GetForeignKeys())
            {
                var principalTable = SafeGetTableName(fk.PrincipalEntityType);
                tableDto.ForeignKeys.Add(new ForeignKeyDocumentationDto
                {
                    ConstraintName = SafeGetConstraintName(fk, tableName, principalTable),
                    PrincipalTable = principalTable,
                    PrincipalSchema = SafeGetSchemaName(fk.PrincipalEntityType),
                    ForeignProperties = fk.Properties.Select(p => SafeGetColumnName(p)).ToList(),
                    PrincipalProperties = fk.PrincipalKey.Properties.Select(p => SafeGetColumnName(p)).ToList(),
                    DeleteBehavior = fk.DeleteBehavior.ToString()
                });
            }

            // Indexes
            foreach (var idx in entityType.GetIndexes())
            {
                tableDto.Indexes.Add(new IndexDocumentationDto
                {
                    IndexName = SafeGetIndexName(idx, tableName),
                    ColumnNames = idx.Properties.Select(p => SafeGetColumnName(p)).ToList(),
                    IsUnique = idx.IsUnique
                });
            }

            tables.Add(tableDto);
        }

        return tables.OrderBy(t => t.SchemaName).ThenBy(t => t.TableName).ToList();
    }

    public async Task<TableDocumentationDto?> GetTableDocumentationAsync(string tableName, string? schema = null)
    {
        var all = await GetDatabaseSchemaDocumentationAsync();
        return all.FirstOrDefault(t =>
            string.Equals(t.TableName, tableName, StringComparison.OrdinalIgnoreCase) &&
            (schema == null || string.Equals(t.SchemaName, schema, StringComparison.OrdinalIgnoreCase)));
    }

    public async Task<string> ExportMarkdownDataDictionaryAsync()
    {
        var tables = await GetDatabaseSchemaDocumentationAsync();
        var sb = new StringBuilder();

        sb.AppendLine("# MerSETA NSDMS — Database Data Dictionary");
        sb.AppendLine();
        sb.AppendLine($"> **Generated:** {DateTime.UtcNow:yyyy-MM-dd HH:mm:ss} UTC | **Target Engine:** Microsoft SQL Server Express | **Total Tables:** {tables.Count}");
        sb.AppendLine();
        sb.AppendLine("---");
        sb.AppendLine();

        sb.AppendLine("## 📋 Schema Overview Table");
        sb.AppendLine();
        sb.AppendLine("| Schema | Table Name | CLR Entity | Columns | Primary Key | Description |");
        sb.AppendLine("| :--- | :--- | :--- | :--- | :--- | :--- |");
        foreach (var t in tables)
        {
            sb.AppendLine($"| `{t.SchemaName}` | [`{t.TableName}`](#{t.TableName.ToLower()}) | `{t.ClrTypeName}` | {t.ColumnCount} | `{string.Join(", ", t.PrimaryKeyColumns)}` | {t.Description} |");
        }
        sb.AppendLine();
        sb.AppendLine("---");
        sb.AppendLine();

        sb.AppendLine("## 🏛️ Table Details & Column Specifications");
        sb.AppendLine();

        foreach (var t in tables)
        {
            sb.AppendLine($"### <a id=\"{t.TableName.ToLower()}\"></a> `{t.SchemaName}.{t.TableName}`");
            sb.AppendLine();
            sb.AppendLine($"**Description:** {t.Description}  ");
            sb.AppendLine($"**CLR Model:** `Nsdms.Domain.Entities.{t.ClrTypeName}`  ");
            sb.AppendLine($"**Primary Key:** `{string.Join(", ", t.PrimaryKeyColumns)}`");
            sb.AppendLine();

            sb.AppendLine("#### Columns");
            sb.AppendLine();
            sb.AppendLine("| Column | SQL Store Type | Nullable | Key | Description |");
            sb.AppendLine("| :--- | :--- | :--- | :--- | :--- |");

            foreach (var col in t.Columns)
            {
                var keyBadge = col.IsPrimaryKey ? "🔑 **PK**" : col.IsForeignKey ? "🔗 **FK**" : "";
                var nullBadge = col.IsNullable ? "NULL" : "**NOT NULL**";
                sb.AppendLine($"| `{col.ColumnName}` | `{col.StoreType}` | {nullBadge} | {keyBadge} | {col.Description} |");
            }
            sb.AppendLine();

            if (t.ForeignKeys.Count > 0)
            {
                sb.AppendLine("#### Foreign Key Constraints");
                sb.AppendLine();
                sb.AppendLine("| Constraint Name | Foreign Columns | Principal Table | Delete Rule |");
                sb.AppendLine("| :--- | :--- | :--- | :--- |");
                foreach (var fk in t.ForeignKeys)
                {
                    sb.AppendLine($"| `{fk.ConstraintName}` | `{string.Join(", ", fk.ForeignProperties)}` | `{fk.PrincipalSchema}.{fk.PrincipalTable}` | `{fk.DeleteBehavior}` |");
                }
                sb.AppendLine();
            }

            if (t.Indexes.Count > 0)
            {
                sb.AppendLine("#### Performance Indexes");
                sb.AppendLine();
                sb.AppendLine("| Index Name | Columns | Unique |");
                sb.AppendLine("| :--- | :--- | :--- |");
                foreach (var idx in t.Indexes)
                {
                    sb.AppendLine($"| `{idx.IndexName}` | `{string.Join(", ", idx.ColumnNames)}` | {(idx.IsUnique ? "✅ Yes" : "No")} |");
                }
                sb.AppendLine();
            }

            sb.AppendLine("---");
            sb.AppendLine();
        }

        return sb.ToString();
    }

    private static string SafeGetTableName(IReadOnlyEntityType entityType)
    {
        try
        {
            return entityType.GetTableName() ?? entityType.ClrType.Name;
        }
        catch
        {
            return entityType.ClrType.Name;
        }
    }

    private static string SafeGetSchemaName(IReadOnlyEntityType entityType)
    {
        try
        {
            return entityType.GetSchema() ?? "dbo";
        }
        catch
        {
            return "dbo";
        }
    }

    private static string SafeGetColumnName(IReadOnlyProperty prop)
    {
        try
        {
            return prop.GetColumnName() ?? prop.Name;
        }
        catch
        {
            return prop.Name;
        }
    }

    private static string SafeGetColumnType(IReadOnlyProperty prop)
    {
        try
        {
            var explicitType = prop.FindAnnotation(RelationalAnnotationNames.ColumnType)?.Value as string;
            if (!string.IsNullOrWhiteSpace(explicitType)) return explicitType;

            var type = prop.GetColumnType();
            if (!string.IsNullOrWhiteSpace(type)) return type;
        }
        catch
        {
            // InMemory provider fallback
        }

        return GetFallbackStoreType(prop.ClrType, prop.GetMaxLength());
    }

    private static string SafeGetConstraintName(IReadOnlyForeignKey fk, string tableName, string principalTable)
    {
        try
        {
            return fk.GetConstraintName() ?? $"FK_{tableName}_{principalTable}";
        }
        catch
        {
            return $"FK_{tableName}_{principalTable}";
        }
    }

    private static string SafeGetIndexName(IReadOnlyIndex idx, string tableName)
    {
        try
        {
            return idx.GetDatabaseName() ?? $"IX_{tableName}_{string.Join("_", idx.Properties.Select(p => p.Name))}";
        }
        catch
        {
            return $"IX_{tableName}_{string.Join("_", idx.Properties.Select(p => p.Name))}";
        }
    }

    private static string? SafeGetEntityComment(IReadOnlyEntityType entityType)
    {
        try
        {
            return entityType.FindAnnotation(RelationalAnnotationNames.Comment)?.Value?.ToString();
        }
        catch
        {
            return null;
        }
    }

    private static string? SafeGetPropertyComment(IReadOnlyProperty prop)
    {
        try
        {
            return prop.FindAnnotation(RelationalAnnotationNames.Comment)?.Value?.ToString();
        }
        catch
        {
            return null;
        }
    }

    private static string GetEntityDefaultDescription(string entityName) => entityName switch
    {
        "Organisation" => "Registered employers, host workplaces, and legal enterprise entities under MerSETA jurisdiction.",
        "Person" => "Individual identity demographic profile (RSA ID, demographics, contact info).",
        "TrainingProvider" => "Accredited Skills Development Providers (SDP) offering registered occupational qualifications.",
        "WspSubmission" => "Workplace Skills Plan (WSP) & Annual Training Report (ATR) submissions for mandatory grants.",
        "GrantApplication" => "Discretionary Grant project funding applications submitted during open funding windows.",
        "GrantMoa" => "Memorandum of Agreement contractual legal commitments for approved discretionary grants.",
        "GrantMoaMilestone" => "Tranche delivery milestones (Inception, Midterm, Final, Closeout) for MOA contracts.",
        "GrantTranchePayment" => "Tranche tax invoice requisitions, finance dual-authorization, and EFT payout batches.",
        "MandatoryGrantDisbursement" => "Statutory 20% Skills Development Levy rebate calculation ledger for compliant employers.",
        "InterSetaTransfer" => "Chamber and SIC code employer migrations with counterpart SETAs and DHET approvals.",
        "EtqaAssessor" => "Registered ETQA assessors and moderators with registered unit standard / qualification scopes.",
        "WorkplaceApproval" => "Workplace site inspection approval records with mentor-to-learner ratio allocations.",
        "CompanyLearner" => "Registered apprentices, learnerships, and skills programme learners.",
        "LearnerTradeTest" => "Trade test serial certifications, ARPL evaluations, and competency awards.",
        "WorkflowInstance" => "Universal state machine execution instances for long-running approval lifecycles.",
        "WorkflowTask" => "Actionable review, inspection, and verification tasks assigned to SETA roles.",
        "DocumentMetadata" => "SHA-256 integrity hashed digital evidence files in the Document Vault.",
        "SetmisSubmissionBatch" => "Department of Higher Education & Training (DHET) flat-file extract submission batch logs.",
        "AuditLog" => "Immutable operational audit trail capturing user, action, timestamp, and JSON before/after snapshots.",
        _ => $"System entity for {entityName} data governance."
    };

    private static string GetPropertyDefaultDescription(string propName, bool isPk, bool isFk)
    {
        if (isPk) return "Auto-generated integer primary key identifier.";
        if (isFk) return $"Foreign key relational link to parent entity.";

        return propName switch
        {
            "CreatedAt" => "UTC timestamp when the record was initially created.",
            "CreatedBy" => "User identifier or service that created the record.",
            "ModifiedAt" => "UTC timestamp when the record was last updated.",
            "ModifiedBy" => "User identifier that last modified the record.",
            "MoaNumber" => "Unique legal MOA contract reference number.",
            "SdlNumber" => "SARS Skills Development Levy registration number (e.g. L123456789).",
            "RsaIdNumber" => "13-digit South African National Identity Number.",
            "TotalContractValue" => "Total committed monetary value in South African Rands (ZAR).",
            "TranchePercentage" => "Deliverable payment percentage (e.g. 30%, 20%).",
            "TrancheAmount" => "Calculated monetary amount for this delivery milestone.",
            "CalculatedRebateAmount" => "Calculated 20% statutory levy rebate amount.",
            "Status" or "StatusCode" or "MoaStatusCode" => "Current lifecycle state code in the workflow engine.",
            "BatchNumber" => "Financial or SETMIS batch grouping reference.",
            _ => $"Domain property for {propName}."
        };
    }

    private static string GetFallbackStoreType(Type clrType, int? maxLength)
    {
        var underlying = Nullable.GetUnderlyingType(clrType) ?? clrType;
        if (underlying == typeof(int)) return "int";
        if (underlying == typeof(long)) return "bigint";
        if (underlying == typeof(string)) return maxLength.HasValue ? $"nvarchar({maxLength.Value})" : "nvarchar(max)";
        if (underlying == typeof(decimal)) return "decimal(18,2)";
        if (underlying == typeof(DateTime)) return "datetime2";
        if (underlying == typeof(bool)) return "bit";
        if (underlying == typeof(Guid)) return "uniqueidentifier";
        return "nvarchar(max)";
    }
}
