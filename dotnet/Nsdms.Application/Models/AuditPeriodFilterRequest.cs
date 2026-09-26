namespace Nsdms.Application.Models;

/// <summary>
/// Parameters for filtering audit records across reporting periods and entity criteria (ITGC-18 / AGSA requirement).
/// </summary>
public class AuditPeriodFilterRequest
{
    public DateTime? FromDateUtc { get; set; }
    public DateTime? ToDateUtc { get; set; }
    public string? EntityName { get; set; }
    public string? ActionName { get; set; }
    public string? Actor { get; set; }
    public string? SearchTerm { get; set; }
    public int PageNumber { get; set; } = 1;
    public int PageSize { get; set; } = 50;
}
