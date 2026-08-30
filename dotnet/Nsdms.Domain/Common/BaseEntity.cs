namespace Nsdms.Domain.Common;

public interface IAuditableEntity
{
    DateTime CreatedAt { get; set; }
    string? CreatedBy { get; set; }
    DateTime? ModifiedAt { get; set; }
    string? ModifiedBy { get; set; }
}

public abstract class BaseEntity : IAuditableEntity
{
    public int Id { get; set; }
    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    public string? CreatedBy { get; set; } = "SYSTEM";
    public DateTime? ModifiedAt { get; set; }
    public string? ModifiedBy { get; set; }
}

public abstract class BaseLookupType : IAuditableEntity
{
    // Code as Primary Key (varchar 15) for readability
    public string Code { get; set; } = string.Empty;
    public string Name { get; set; } = string.Empty;
    public string? Description { get; set; }
    public bool Active { get; set; } = true;

    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    public string? CreatedBy { get; set; } = "SYSTEM";
    public DateTime? ModifiedAt { get; set; }
    public string? ModifiedBy { get; set; }
}
