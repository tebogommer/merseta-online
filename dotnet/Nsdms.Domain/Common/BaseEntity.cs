namespace Nsdms.Domain.Common;

/// <summary>
/// Defines audit tracking metadata contract for all persistent domain entities.
/// </summary>
public interface IAuditableEntity
{
    /// <summary>
    /// UTC timestamp when the record was initially created.
    /// </summary>
    DateTime CreatedAt { get; set; }

    /// <summary>
    /// Username or system process that created the record.
    /// </summary>
    string? CreatedBy { get; set; }

    /// <summary>
    /// UTC timestamp when the record was last updated.
    /// </summary>
    DateTime? ModifiedAt { get; set; }

    /// <summary>
    /// Username or system process that last updated the record.
    /// </summary>
    string? ModifiedBy { get; set; }
}

/// <summary>
/// Abstract base entity class providing auto-generated primary key of specified type and audit metadata.
/// </summary>
public abstract class BaseEntity<TKey> : IAuditableEntity
{
    /// <summary>
    /// Auto-generated primary key identifier.
    /// </summary>
    public TKey Id { get; set; } = default!;

    /// <summary>
    /// UTC timestamp when the record was initially created.
    /// </summary>
    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Username or system process that created the record.
    /// </summary>
    public string? CreatedBy { get; set; } = "SYSTEM";

    /// <summary>
    /// UTC timestamp when the record was last modified.
    /// </summary>
    public DateTime? ModifiedAt { get; set; }

    /// <summary>
    /// Username or system process that last modified the record.
    /// </summary>
    public string? ModifiedBy { get; set; }
}

/// <summary>
/// Default abstract base entity class providing 32-bit auto-generated integer primary key.
/// </summary>
public abstract class BaseEntity : BaseEntity<int>
{
}

/// <summary>
/// Abstract base entity class providing 64-bit auto-generated BIGINT primary key for high-volume entities.
/// </summary>
public abstract class BaseLongEntity : BaseEntity<long>
{
}

/// <summary>
/// Abstract base type for reference/lookup lookup tables with unique code identifier and metadata.
/// </summary>
public abstract class BaseLookupType : IAuditableEntity
{
    /// <summary>
    /// Unique alphanumeric code identifier acting as primary key.
    /// </summary>
    public string Code { get; set; } = string.Empty;

    /// <summary>
    /// Display name / title of the lookup option.
    /// </summary>
    public string Name { get; set; } = string.Empty;

    /// <summary>
    /// Detailed description and statutory context of the lookup code.
    /// </summary>
    public string? Description { get; set; }

    /// <summary>
    /// Indicates whether the lookup value is active and selectable in UI workflows.
    /// </summary>
    public bool Active { get; set; } = true;

    /// <summary>
    /// UTC timestamp when the lookup record was created.
    /// </summary>
    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Username or system process that created the lookup record.
    /// </summary>
    public string? CreatedBy { get; set; } = "SYSTEM";

    /// <summary>
    /// UTC timestamp when the lookup record was last modified.
    /// </summary>
    public DateTime? ModifiedAt { get; set; }

    /// <summary>
    /// Username or system process that last modified the lookup record.
    /// </summary>
    public string? ModifiedBy { get; set; }
}
