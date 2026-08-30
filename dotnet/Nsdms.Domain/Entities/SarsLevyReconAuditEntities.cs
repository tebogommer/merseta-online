using System.ComponentModel.DataAnnotations.Schema;
using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Advanced SARS Monthly SDL Levy Reconciliation Audit and Discrepancy Tracking.
/// </summary>
public class SarsLevyReconAudit : BaseEntity
{
    public string FinancialYear { get; set; } = "2026";
    public string SchemeYear { get; set; } = "2026";
    public string SdlNumber { get; set; } = string.Empty;

    public int? OrganisationId { get; set; }
    public Organisation? Organisation { get; set; }

    public decimal TotalSarsLeviesReceived { get; set; } = 0m;
    public decimal TotalCalculatedLeviesExpected { get; set; } = 0m;
    public decimal VarianceAmount { get; set; } = 0m;

    /// <summary>
    /// Discrepancy Category: ExactMatch, Underpayment, Overpayment, ChamberMisallocation, SchemeYearMismatch
    /// </summary>
    public string DiscrepancyReasonCode { get; set; } = "ExactMatch";

    public bool ClawbackActionRequired { get; set; } = false;
    public decimal ClawbackAmount { get; set; } = 0m;
    public DateTime? ClawbackIssuedDate { get; set; }
    public DateTime? ClawbackSettledDate { get; set; }

    /// <summary>
    /// Status: Reconciled, DiscrepancyFlagged, ClawbackIssued, Resolved
    /// </summary>
    public string AuditStatusCode { get; set; } = "Reconciled";

    public string? AuditNotes { get; set; }
    public string AuditorUserId { get; set; } = "SYSTEM";
    public DateTime ReconciliationDate { get; set; } = DateTime.UtcNow;

    [NotMapped]
    public string Status { get => AuditStatusCode; set => AuditStatusCode = value; }
}
