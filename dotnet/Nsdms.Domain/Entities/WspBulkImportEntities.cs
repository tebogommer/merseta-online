using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Master batch record tracking bulk spreadsheet ingestion runs (Excel and CSV) for WSP / ATR submissions.
/// </summary>
public class WspBulkImportBatch : BaseEntity
{
    /// <summary>
    /// Unique cryptographic GUID identifier for external client and SignalR telemetry tracking.
    /// </summary>
    public Guid BatchGuid { get; set; } = Guid.NewGuid();

    /// <summary>
    /// Human-readable statutory batch reference (e.g. BATCH-WSP-2026-00042).
    /// </summary>
    public string BatchReference { get; set; } = string.Empty;

    /// <summary>
    /// Foreign key referencing the parent WspSubmission.
    /// </summary>
    public int WspSubmissionId { get; set; }

    /// <summary>
    /// Navigational reference to the parent WspSubmission.
    /// </summary>
    public WspSubmission? WspSubmission { get; set; }

    /// <summary>
    /// Foreign key referencing the submitting organisation.
    /// </summary>
    public int OrganisationId { get; set; }

    /// <summary>
    /// Navigational reference to the submitting organisation.
    /// </summary>
    public Organisation? Organisation { get; set; }

    /// <summary>
    /// Statutory scheme financial year (e.g. 2026).
    /// </summary>
    public int SchemeYear { get; set; }

    /// <summary>
    /// Ingestion section target: WSP (Planned) or ATR (Actuals).
    /// </summary>
    public string ReportType { get; set; } = "WSP";

    /// <summary>
    /// Original file name uploaded by user.
    /// </summary>
    public string OriginalFileName { get; set; } = string.Empty;

    /// <summary>
    /// File size in bytes.
    /// </summary>
    public long FileSizeBytes { get; set; }

    /// <summary>
    /// Cryptographic SHA-256 digital security seal calculated over raw input stream.
    /// </summary>
    public string ContentHashSha256 { get; set; } = string.Empty;

    /// <summary>
    /// Processing lifecycle status:
    /// 'Queued', 'Staging', 'Validating', 'Validated', 'ValidationFailed', 'PartiallyCommitted', 'Committed', 'RolledBack'
    /// </summary>
    public string BatchStatus { get; set; } = "Queued";

    /// <summary>
    /// Total data rows identified in the source file.
    /// </summary>
    public int TotalRowCount { get; set; }

    /// <summary>
    /// Count of records passing all statutory and taxonomy validations.
    /// </summary>
    public int ValidRowCount { get; set; }

    /// <summary>
    /// Count of records failing validation (exceptions).
    /// </summary>
    public int ErrorRowCount { get; set; }

    /// <summary>
    /// Count of verified records materialized into core domain tables.
    /// </summary>
    public int CommittedRowCount { get; set; }

    /// <summary>
    /// Total monetary investment cost parsed across all valid rows in ZAR.
    /// </summary>
    public decimal TotalEstimatedCostRollup { get; set; }

    /// <summary>
    /// Total beneficiary headcount parsed across all valid rows.
    /// </summary>
    public int TotalBeneficiariesRollup { get; set; }

    /// <summary>
    /// Whether user selected permissive partial staging (commit valid, queue errors).
    /// </summary>
    public bool AllowPartialCommit { get; set; }

    /// <summary>
    /// Diagnostic summary JSON storing categorized error tallies.
    /// </summary>
    public string? ErrorSummaryJson { get; set; }

    /// <summary>
    /// Processing duration in milliseconds.
    /// </summary>
    public int? ProcessingDurationMs { get; set; }

    /// <summary>
    /// Collection of staged candidate rows.
    /// </summary>
    public ICollection<WspBulkImportStaging> StagedRows { get; set; } = new List<WspBulkImportStaging>();
}

/// <summary>
/// Individual candidate line staged within a WspBulkImportBatch before core commitment.
/// </summary>
public class WspBulkImportStaging : BaseLongEntity
{
    /// <summary>
    /// Foreign key referencing the parent WspBulkImportBatch.
    /// </summary>
    public int BatchId { get; set; }

    /// <summary>
    /// Navigational reference to parent batch.
    /// </summary>
    public WspBulkImportBatch? Batch { get; set; }

    /// <summary>
    /// 1-based original line index in uploaded spreadsheet.
    /// </summary>
    public int RowIndex { get; set; }

    // Raw unconstrained string tokens captured directly from spreadsheet
    public string? RawIdType { get; set; }
    public string? RawIdNumber { get; set; }
    public string? RawFirstName { get; set; }
    public string? RawLastName { get; set; }
    public string? RawGenderCode { get; set; }
    public string? RawEquityCode { get; set; }
    public string? RawNationalityCode { get; set; }
    public string? RawOfoCode { get; set; }
    public string? RawSpecialisationCode { get; set; }
    public string? RawInterventionTypeCode { get; set; }
    public string? RawQualificationCode { get; set; }
    public string? RawSkillsProgramCode { get; set; }
    public string? RawSkillsSetCode { get; set; }
    public string? RawEmploymentTypeCode { get; set; }
    public string? RawProviderTypeCode { get; set; }
    public string? RawTrainingDeliveryMethodCode { get; set; }
    public string? RawMunicipalityCode { get; set; }
    public string? RawStartDate { get; set; }
    public string? RawEndDate { get; set; }
    public string? RawEstimatedCost { get; set; }
    public string? RawBeneficiaryCount { get; set; }

    // Resolved / validated relational and scalar fields
    public int? ResolvedOfoCodeId { get; set; }
    public int? ResolvedQualificationId { get; set; }
    public int? ResolvedInterventionTypeId { get; set; }
    public int? ResolvedNqfLevel { get; set; }
    public decimal? ParsedEstimatedCost { get; set; }
    public int? ParsedBeneficiaryCount { get; set; }
    public DateTime? ParsedStartDate { get; set; }
    public DateTime? ParsedEndDate { get; set; }

    /// <summary>
    /// Whether this specific row is 100% valid and ready for core table commitment.
    /// </summary>
    public bool IsValid { get; set; } = true;

    /// <summary>
    /// Whether this row has already been committed to WspTrainingPlan or WspEmploymentSummary.
    /// </summary>
    public bool IsCommitted { get; set; } = false;

    /// <summary>
    /// Standard error taxonomy code (e.g. INVALID_OFO, INVALID_RSA_ID, DUPLICATE_KEY).
    /// </summary>
    public string? ValidationErrorCode { get; set; }

    /// <summary>
    /// Explicit human-readable diagnostic error text detailing why validation failed.
    /// </summary>
    public string? ValidationErrorDetails { get; set; }
}
