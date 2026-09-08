using System.ComponentModel.DataAnnotations.Schema;
using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Advanced SARS Monthly SDL Levy Reconciliation Audit and Discrepancy Tracking.
/// </summary>
public class SarsLevyReconAudit : BaseEntity
{
    public string FinancialYear { get; set; } = string.Empty;
    public string SchemeYear { get; set; } = string.Empty;
    public string SdlNumber { get; set; } = string.Empty;

    public int? OrganisationId { get; set; }
    public Organisation? Organisation { get; set; }

    public decimal TotalSarsLeviesReceived { get; set; } = 0m;
    public decimal TotalCalculatedLeviesExpected { get; set; } = 0m;
    public decimal VarianceAmount { get; set; } = 0m;

    /// <summary>
    /// Discrepancy Category: ExactMatch, Underpayment, Overpayment, ChamberMisallocation, SicCodeMismatch, OutOfScopeSeta, SchemeYearMismatch
    /// </summary>
    public string DiscrepancyReasonCode { get; set; } = "ExactMatch";

    public string? ExpectedChamberCode { get; set; }
    public string? ActualSarsChamberCode { get; set; }
    public string? ExpectedSicCode { get; set; }
    public string? ActualSarsSicCode { get; set; }
    public string? CounterpartSetaCode { get; set; }

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

/// <summary>
/// Configurable Statutory Scheme Year Calculation Parameters & Return Processing Controls.
/// </summary>
public class SarsSchemeYearCalculation : BaseEntity
{
    public string SchemeYear { get; set; } = string.Empty;
    public decimal MandatoryPercentage { get; set; } = 20.0m;
    public decimal DiscretionaryPercentage { get; set; } = 49.5m;
    public decimal AdminPercentage { get; set; } = 10.5m;
    public decimal QctoPercentage { get; set; } = 0.5m;
    public decimal TotalPercentage { get; set; } = 80.5m;

    public bool AllowReturnsMandatory { get; set; } = true;
    public bool AllowInvoicesMandatory { get; set; } = true;
    public bool AllowReturnsDiscretionary { get; set; } = true;
    public bool AllowInvoicesDiscretionary { get; set; } = true;

    public string StatusCode { get; set; } = "Active";
    public string? Notes { get; set; }
}

/// <summary>
/// Statistical 12-Month Rolling Standard Deviation Anomaly & Inconsistent Contributor Analysis.
/// </summary>
public class SarsLevyDeviationDto
{
    public string SdlNumber { get; set; } = string.Empty;
    public string OrganisationName { get; set; } = string.Empty;
    public string ChamberCode { get; set; } = "METAL";
    public string SchemeYear { get; set; } = string.Empty;
    public decimal Month1 { get; set; }
    public decimal Month2 { get; set; }
    public decimal Month3 { get; set; }
    public decimal Month4 { get; set; }
    public decimal Month5 { get; set; }
    public decimal Month6 { get; set; }
    public decimal Month7 { get; set; }
    public decimal Month8 { get; set; }
    public decimal Month9 { get; set; }
    public decimal Month10 { get; set; }
    public decimal Month11 { get; set; }
    public decimal Month12 { get; set; }
    public decimal TotalLevy { get; set; }
    public decimal LatestLevy { get; set; }
    public decimal AverageMonthlyLevy { get; set; }
    public decimal StandardDeviation { get; set; }
    public decimal DeviationPercentage { get; set; }
    public bool IsInconsistentContributor => AverageMonthlyLevy >= 40000m && DeviationPercentage >= 20.0m;
    public string LevyStatus { get; set; } = "Consistent";
}

/// <summary>
/// Aggregate Breakdown of Statutory Levy Income by Chamber.
/// </summary>
public class ChamberLevyBreakdownDto
{
    public string ChamberCode { get; set; } = string.Empty;
    public string ChamberName { get; set; } = string.Empty;
    public int EmployerCount { get; set; }
    public decimal TotalGrossLevy { get; set; }
    public decimal MandatoryGrantPortion { get; set; }
    public decimal DiscretionaryGrantPortion { get; set; }
    public decimal AdminPortion { get; set; }
    public decimal QctoPortion { get; set; }
    public decimal InterestAndPenalties { get; set; }
}
