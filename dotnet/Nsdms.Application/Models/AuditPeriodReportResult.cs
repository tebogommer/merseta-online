using Nsdms.Domain.Entities;

namespace Nsdms.Application.Models;

/// <summary>
/// Encapsulates the results of a period-filtered audit inquiry with cryptographic tamper-evident seal.
/// </summary>
public class AuditPeriodReportResult
{
    public List<AuditLog> Logs { get; set; } = new();
    public int TotalCount { get; set; }
    public DateTime? FromDateUtc { get; set; }
    public DateTime? ToDateUtc { get; set; }
    public string ReportIntegrityHash { get; set; } = string.Empty;
    public DateTime GeneratedAtUtc { get; set; } = DateTime.UtcNow;
}
