namespace Nsdms.Application.Services;

/// <summary>
/// Service contract for generating dynamic and exportable database schema documentation from EF Core model metadata.
/// </summary>
public interface IDatabaseDocumentationService
{
    /// <summary>
    /// Introspects all configured EF Core entities and returns structured documentation for all database tables.
    /// </summary>
    /// <returns>List of table documentation metadata DTOs.</returns>
    Task<List<TableDocumentationDto>> GetDatabaseSchemaDocumentationAsync();

    /// <summary>
    /// Retrieves detailed documentation for a specific database table.
    /// </summary>
    /// <param name="tableName">The exact SQL table name.</param>
    /// <param name="schema">Optional database schema name (defaults to 'dbo').</param>
    /// <returns>Table metadata DTO or null if not found.</returns>
    Task<TableDocumentationDto?> GetTableDocumentationAsync(string tableName, string? schema = null);

    /// <summary>
    /// Exports the entire database schema into a GitHub-flavored Markdown data dictionary.
    /// </summary>
    /// <returns>Formatted Markdown document string.</returns>
    Task<string> ExportMarkdownDataDictionaryAsync();

    /// <summary>
    /// Generates an idempotent T-SQL script containing all sp_addextendedproperty / sp_updateextendedproperty commands.
    /// </summary>
    /// <returns>T-SQL migration script string.</returns>
    Task<string> GenerateSqlExtendedPropertiesScriptAsync();

    /// <summary>
    /// Synchronizes all table and column MS_Description extended properties directly to SQL Server Express.
    /// </summary>
    /// <returns>Total number of table and column extended properties updated.</returns>
    Task<int> SyncExtendedPropertiesToDatabaseAsync();
}

/// <summary>
/// Documentation metadata for a database table.
/// </summary>
public class TableDocumentationDto
{
    public string TableName { get; set; } = string.Empty;
    public string SchemaName { get; set; } = "dbo";
    public string ClrTypeName { get; set; } = string.Empty;
    public string Description { get; set; } = string.Empty;
    public int ColumnCount => Columns.Count;
    public List<string> PrimaryKeyColumns { get; set; } = new();
    public List<ColumnDocumentationDto> Columns { get; set; } = new();
    public List<ForeignKeyDocumentationDto> ForeignKeys { get; set; } = new();
    public List<IndexDocumentationDto> Indexes { get; set; } = new();
}

/// <summary>
/// Documentation metadata for a database column.
/// </summary>
public class ColumnDocumentationDto
{
    public string ColumnName { get; set; } = string.Empty;
    public string PropertyName { get; set; } = string.Empty;
    public string StoreType { get; set; } = string.Empty;
    public string ClrTypeName { get; set; } = string.Empty;
    public bool IsPrimaryKey { get; set; }
    public bool IsForeignKey { get; set; }
    public bool IsNullable { get; set; }
    public int? MaxLength { get; set; }
    public string? Description { get; set; }
}

/// <summary>
/// Documentation metadata for a foreign key relationship.
/// </summary>
public class ForeignKeyDocumentationDto
{
    public string ConstraintName { get; set; } = string.Empty;
    public string PrincipalTable { get; set; } = string.Empty;
    public string PrincipalSchema { get; set; } = "dbo";
    public List<string> ForeignProperties { get; set; } = new();
    public List<string> PrincipalProperties { get; set; } = new();
    public string DeleteBehavior { get; set; } = string.Empty;
}

/// <summary>
/// Documentation metadata for a table index.
/// </summary>
public class IndexDocumentationDto
{
    public string IndexName { get; set; } = string.Empty;
    public List<string> ColumnNames { get; set; } = new();
    public bool IsUnique { get; set; }
}
