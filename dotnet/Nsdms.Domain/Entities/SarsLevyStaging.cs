using System;
using Nsdms.Domain.Common;
using Nsdms.Domain.Entities;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Staging entity for high-speed bulk ingestion of raw monthly SARS Skills Development Levy transactions.
/// Serves as the landing table for streaming SqlBulkCopy before promotion into the production financial ledger.
/// </summary>
public class SarsLevyStaging : BaseLongEntity
{
    /// <summary>
    /// Unique ingestion batch tracking identifier (e.g. SARS-STAGING-20260903-ABC123).
    /// </summary>
    public string BatchIdentifier { get; set; } = string.Empty;

    /// <summary>
    /// Sequential line number in the source SARS text file (1-indexed).
    /// </summary>
    public int LineNumber { get; set; }

    /// <summary>
    /// Raw unparsed line content retained for forensic audit and diagnostic inspection.
    /// </summary>
    public string? RawRecord { get; set; }

    /// <summary>
    /// Employer SARS Skills Development Levy registration reference (e.g. L123456789).
    /// </summary>
    public string SdlNumber { get; set; } = string.Empty;

    /// <summary>
    /// Levy scheme year or accounting period (e.g. 2026).
    /// </summary>
    public string SchemeYear { get; set; } = string.Empty;

    /// <summary>
    /// Declared 5-digit Standard Industrial Classification (SIC) code reported in the SARS schedule.
    /// </summary>
    public string? SicCode { get; set; }

    /// <summary>
    /// Resolved merSETA Chamber Code (AUTO, METAL, MOTOR, NEW_TYRE, PLASTICS, OTHER).
    /// </summary>
    public string? ChamberCode { get; set; }

    /// <summary>
    /// Statutory SETA code (SETA 17 for merSETA, or other SETA code for out-of-scope records).
    /// </summary>
    public string SetaCode { get; set; } = "17";

    /// <summary>
    /// 20% Mandatory Grant portion in ZAR.
    /// </summary>
    public decimal MandatoryLevyAmount { get; set; }

    /// <summary>
    /// 49.5% Discretionary Grant portion in ZAR.
    /// </summary>
    public decimal DiscretionaryLevyAmount { get; set; }

    /// <summary>
    /// 10.5% merSETA administration levy portion in ZAR.
    /// </summary>
    public decimal AdminLevyAmount { get; set; }

    /// <summary>
    /// 0.5% Quality Council for Trades and Occupations (QCTO) levy portion in ZAR.
    /// </summary>
    public decimal QctoLevyAmount { get; set; }

    /// <summary>
    /// Statutory interest on late payments in ZAR.
    /// </summary>
    public decimal InterestAmount { get; set; }

    /// <summary>
    /// Statutory penalty fee on late payments in ZAR.
    /// </summary>
    public decimal PenaltyAmount { get; set; }

    /// <summary>
    /// Total gross levy amount in ZAR.
    /// </summary>
    public decimal TotalLevyAmount { get; set; }

    /// <summary>
    /// Indicates whether this transaction belongs to a non-merSETA industry requiring Inter-SETA transfer.
    /// </summary>
    public bool IsOutOfScopeSeta { get; set; } = false;

    /// <summary>
    /// Indicates whether the declared SARS SIC code differs from the employer's master verified record.
    /// </summary>
    public bool HasSicCodeMismatch { get; set; } = false;

    /// <summary>
    /// Staging lifecycle status: Pending, Validated, Promoted, Rejected.
    /// </summary>
    public string StagingStatus { get; set; } = "Pending";

    /// <summary>
    /// Optional diagnostic message or reason for rejection.
    /// </summary>
    public string? ValidationMessage { get; set; }

    /// <summary>
    /// Foreign key referencing the promoted LevyFile once batch promotion completes.
    /// </summary>
    public int? PromotedLevyFileId { get; set; }
}
