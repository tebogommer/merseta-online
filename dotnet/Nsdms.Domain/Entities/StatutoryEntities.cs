using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Represents a statutory submission batch run for DHET SETMIS or SAQA NLRD.
/// </summary>
public class StatutorySubmissionBatch : BaseEntity
{
    /// <summary>
    /// Type of statutory submission ("SETMIS" or "NLRD").
    /// </summary>
    public string BatchType { get; set; } = "SETMIS";

    /// <summary>
    /// Unique business reference for the statutory batch (e.g. SETMIS-2026-Q1-001).
    /// </summary>
    public string BatchNumber { get; set; } = string.Empty;

    /// <summary>
    /// Scheme / Financial submission year (e.g. 2026).
    /// </summary>
    public int SubmissionYear { get; set; }

    /// <summary>
    /// Quarterly submission cycle (1, 2, 3, 4, or null for annual/ad-hoc).
    /// </summary>
    public int? SubmissionQuarter { get; set; }

    /// <summary>
    /// Timestamp when data extraction was initiated.
    /// </summary>
    public DateTime ExtractionDate { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Total records across all generated files in this batch.
    /// </summary>
    public int TotalRecords { get; set; }

    /// <summary>
    /// Number of fatal blocking validation errors detected before extraction.
    /// </summary>
    public int FatalErrorsCount { get; set; }

    /// <summary>
    /// Number of non-blocking warning anomalies detected.
    /// </summary>
    public int WarningsCount { get; set; }

    /// <summary>
    /// Batch status code ("Draft", "Validated", "Extracted", "Submitted", "Accepted", "Rejected").
    /// </summary>
    public string StatusCode { get; set; } = "Extracted";

    /// <summary>
    /// SHA-256 digital security seal / checksum of the combined extract package.
    /// </summary>
    public string? DigitalSecuritySeal { get; set; }

    /// <summary>
    /// Name of the generated zip archive package (e.g. SETMIS_MERS_0006_20260902.zip).
    /// </summary>
    public string? ArchiveFileName { get; set; }

    /// <summary>
    /// File storage URI or relative path to the archived package.
    /// </summary>
    public string? ArchiveStorageUri { get; set; }

    /// <summary>
    /// Data steward notes, audit remarks, or DHET transmission acknowledgements.
    /// </summary>
    public string? Comments { get; set; }

    /// <summary>
    /// Collection of individual extract files generated within this batch.
    /// </summary>
    public ICollection<StatutoryBatchFile> Files { get; set; } = new List<StatutoryBatchFile>();
}

/// <summary>
/// Individual fixed-width data file generated within a statutory batch.
/// </summary>
public class StatutoryBatchFile : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the parent StatutorySubmissionBatch.
    /// </summary>
    public int StatutorySubmissionBatchId { get; set; }

    /// <summary>
    /// Navigation property to parent StatutorySubmissionBatch.
    /// </summary>
    public StatutorySubmissionBatch? Batch { get; set; }

    /// <summary>
    /// Statutory file code (e.g. "100", "200", "304", "400", "401", "500", "501", "502", "503", "505", "506" for SETMIS,
    /// or "21", "24", "25", "26", "27", "28", "29", "30" for NLRD).
    /// </summary>
    public string FileCode { get; set; } = string.Empty;

    /// <summary>
    /// Statutory file title / description (e.g. "Provider File 100", "Learner Enrolment File 500").
    /// </summary>
    public string FileTitle { get; set; } = string.Empty;

    /// <summary>
    /// Generated file name (e.g. MERS_0006_100_v001_20260902.dat or MERS21260902.dat).
    /// </summary>
    public string FileName { get; set; } = string.Empty;

    /// <summary>
    /// Number of lines / records extracted in this file.
    /// </summary>
    public int RecordCount { get; set; }

    /// <summary>
    /// File size in bytes.
    /// </summary>
    public long FileSizeBytes { get; set; }

    /// <summary>
    /// Fixed-width record length in characters (per specification).
    /// </summary>
    public int RecordLength { get; set; }

    /// <summary>
    /// SHA-256 digital security seal of this individual file.
    /// </summary>
    public string? ChecksumSha256 { get; set; }

    /// <summary>
    /// Extracted fixed-width text content or storage path reference.
    /// </summary>
    public string? FileContent { get; set; }

    /// <summary>
    /// File status ("Extracted", "Empty", "Failed").
    /// </summary>
    public string StatusCode { get; set; } = "Extracted";

    /// <summary>
    /// Number of validation discrepancies recorded for this file.
    /// </summary>
    public int ValidationErrorsCount { get; set; }
}
