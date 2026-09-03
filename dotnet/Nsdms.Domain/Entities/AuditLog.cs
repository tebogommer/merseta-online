using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Immutable operational audit trail recording entity mutations, actor identity, timestamps, and JSON snapshots.
/// </summary>
public class AuditLog : BaseLongEntity
{
    /// <summary>
    /// Name of the target domain entity modified (e.g. Organisation, CompanyLearner, GrantMoa).
    /// </summary>
    public string EntityName { get; set; } = string.Empty;

    /// <summary>
    /// Primary key identifier of the target record (64-bit integer supporting high-volume entities).
    /// </summary>
    public long RecordId { get; set; }

    /// <summary>
    /// Specific CRUD or workflow action executed (e.g. Created, Updated, Deleted, StatusChanged, Disbursed).
    /// </summary>
    public string ActionName { get; set; } = string.Empty;

    /// <summary>
    /// Username, email, or system process identity of the actor performing the action.
    /// </summary>
    public string Actor { get; set; } = string.Empty;

    /// <summary>
    /// Structured JSON payload capturing before/after state snapshots and contextual metadata.
    /// </summary>
    public string? MetadataJson { get; set; }

    /// <summary>
    /// Exact UTC timestamp when the audit event occurred.
    /// </summary>
    public DateTime Timestamp { get; set; } = DateTime.UtcNow;
}
