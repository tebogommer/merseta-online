namespace Nsdms.Application.Common.Models;

/// <summary>
/// Result model for automated statutory clawback netting against grant disbursements.
/// </summary>
public class ClawbackNettingResult
{
    public int OrganisationId { get; set; }
    public decimal GrossClaimAmount { get; set; }
    public decimal TotalOutstandingClawbacks { get; set; }
    public decimal TotalNettedAmount { get; set; }
    public decimal NetPayableAmount { get; set; }
    public decimal RemainingClawbackBalance { get; set; }
    public int SettledAuditRecordsCount { get; set; }
    public string SummaryMessage { get; set; } = string.Empty;
    public List<int> SettledReconAuditIds { get; set; } = new();
}
