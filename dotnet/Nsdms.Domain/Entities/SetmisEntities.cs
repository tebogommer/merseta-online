using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Log of SETMIS 500-505 extract batches generated and submitted to DHET.
/// </summary>
public class SetmisSubmissionBatch : BaseEntity
{
    public string BatchNumber { get; set; } = string.Empty;
    public string FileCode { get; set; } = "ALL"; // 500, 501, 502, 503, 504, 505, ALL
    public string SubmissionPeriod { get; set; } = string.Empty; // e.g. 2026-Q1, 2026-Q2
    public int TotalRecords { get; set; }
    public int ValidRecords { get; set; }
    public int ErrorRecords { get; set; }
    public string Status { get; set; } = "Generated"; // Generated, Validated, Submitted to DHET, Accepted by DHET, Rejected
    public string? GeneratedFileUri { get; set; }
    public string? GeneratedByUserId { get; set; }
    public DateTime GeneratedDate { get; set; } = DateTime.UtcNow;
    public string? DhetAcknowledgmentRef { get; set; }
    public string? ValidationSummaryJson { get; set; }
}

/// <summary>
/// Pre-submission validation diagnostics for DHET SETMIS flat files.
/// </summary>
public class SetmisValidationMessage
{
    public string FileCode { get; set; } = string.Empty;
    public int RecordId { get; set; }
    public string EntityIdentifier { get; set; } = string.Empty;
    public string Severity { get; set; } = "Error"; // Error, Warning, Info
    public string FieldName { get; set; } = string.Empty;
    public string RuleDescription { get; set; } = string.Empty;
    public string RemediationGuidance { get; set; } = string.Empty;
}

/// <summary>
/// Executive Skills Intelligence DTO models for SSP &amp; BI.
/// </summary>
public class SspChamberMetricDto
{
    public string ChamberName { get; set; } = string.Empty;
    public int EmployerCount { get; set; }
    public int LearnerCount { get; set; }
    public decimal PlannedTrainingBudget { get; set; }
    public decimal DisbursedGrantsAmount { get; set; }
}

public class SspEquityMetricDto
{
    public string DemographicGroup { get; set; } = string.Empty;
    public int TotalLearners { get; set; }
    public double Percentage { get; set; }
}

public class SspProvincialMetricDto
{
    public string ProvinceCode { get; set; } = string.Empty;
    public string ProvinceName { get; set; } = string.Empty;
    public int EmployerCount { get; set; }
    public int ProviderCount { get; set; }
    public int ActiveLearnerCount { get; set; }
    public int CertifiedCount { get; set; }
}

public class SspScarceSkillDto
{
    public string OfoCode { get; set; } = string.Empty;
    public string OccupationTitle { get; set; } = string.Empty;
    public string ChamberName { get; set; } = string.Empty;
    public int ReportedNeedCount { get; set; }
    public int EnrolledTrainingCount { get; set; }
    public string InterventionsRequired { get; set; } = string.Empty;
}
