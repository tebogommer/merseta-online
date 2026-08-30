using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

public class AuditLog : BaseEntity
{
    public string EntityName { get; set; } = string.Empty;
    public int RecordId { get; set; }
    public string ActionName { get; set; } = string.Empty;
    public string Actor { get; set; } = string.Empty;
    public string? MetadataJson { get; set; }
    public DateTime Timestamp { get; set; } = DateTime.UtcNow;
}
