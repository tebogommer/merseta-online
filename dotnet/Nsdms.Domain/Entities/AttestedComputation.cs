using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// A concept carrying a sanctioned way to compute a value per OKF v0.2 Section 10,
/// so a consumer can confirm the value was produced by executing blessed code rather than improvising.
/// </summary>
public class AttestedComputation : BaseEntity
{
    public int ConceptId { get; set; }

    /// <summary>
    /// Runtime identifier (e.g. 'tsql', 'postgres', 'bigquery', 'dbt', 'python'). Defaults to 'tsql'.
    /// </summary>
    public string Runtime { get; set; } = "tsql";

    /// <summary>
    /// Sanctioned inline T-SQL query template with parameterized holes (@ParamName).
    /// </summary>
    public string? ComputationSql { get; set; }

    /// <summary>
    /// Optional external script path under references/ when computation is kept as an external file.
    /// </summary>
    public string? ExternalScriptPath { get; set; }

    /// <summary>
    /// Resource URI for run instructions or execution skill (e.g., 'references/skills/run-tsql.md').
    /// </summary>
    public string ExecutorResource { get; set; } = "references/skills/run-tsql.md";

    /// <summary>
    /// Resource URI for deterministic attester code (e.g., 'references/attesters/tsql-equality.cs').
    /// </summary>
    public string AttesterResource { get; set; } = "references/attesters/tsql-equality.cs";

    /// <summary>
    /// JSON array declaring the receipt fields a run must return (e.g. ["execution_id", "executed_sql", "rows_affected", "result_digest"]).
    /// </summary>
    public string ReceiptSchemaJson { get; set; } = "[\"execution_id\", \"executed_sql\", \"rows_affected\", \"result_digest\"]";

    /// <summary>
    /// Indicates whether the computation is currently active.
    /// </summary>
    public bool IsActive { get; set; } = true;

    // Navigation properties
    public ConceptDocument Concept { get; set; } = null!;
    public ICollection<ComputationParameter> Parameters { get; set; } = new List<ComputationParameter>();
    public ICollection<ComputationExecutionAudit> Executions { get; set; } = new List<ComputationExecutionAudit>();
}
