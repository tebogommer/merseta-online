using System;
using System.Collections.Generic;

namespace Nsdms.Application.Services;

/// <summary>
/// Individual compliance issue detected during pre-ingestion stream validation.
/// </summary>
public record SarsComplianceIssue(
    int LineNumber,
    string? SdlNumber,
    string IssueCode,
    string Description,
    string Severity = "Error",
    string? RawSnippet = null
);

/// <summary>
/// Comprehensive pre-flight compliance report generated before any staging or database writes.
/// </summary>
public class SarsComplianceReport
{
    public string FileName { get; set; } = string.Empty;
    public string DigitalSecuritySeal { get; set; } = string.Empty;
    public int TotalLinesRead { get; set; }
    public int ValidDataRowCount { get; set; }
    public decimal TotalLevyAmount { get; set; }
    public bool HasTrailer { get; set; }
    public bool IsTrailerReconciled { get; set; }
    public int? TrailerDeclaredCount { get; set; }
    public decimal? TrailerDeclaredAmount { get; set; }

    public List<SarsComplianceIssue> Errors { get; set; } = new();
    public List<SarsComplianceIssue> Warnings { get; set; } = new();

    public bool IsCompliant => Errors.Count == 0 && ValidDataRowCount > 0;
    public int ErrorCount => Errors.Count;
    public int WarningCount => Warnings.Count;

    public string SummaryMessage => IsCompliant
        ? $"File is 100% compliant ({ValidDataRowCount:N0} records totaling R{TotalLevyAmount:N2}). Ready for secure staging."
        : $"File rejected with {Errors.Count} compliance violation(s). Ingestion blocked to preserve ledger integrity.";
}

/// <summary>
/// Exception thrown when a SARS levy file fails pre-ingestion compliance checks.
/// </summary>
public class SarsComplianceException : InvalidOperationException
{
    public SarsComplianceReport Report { get; }

    public SarsComplianceException(SarsComplianceReport report)
        : base(report.Errors.Count > 0
            ? $"{report.SummaryMessage} Violations: {string.Join("; ", report.Errors.Select(e => $"{e.IssueCode}: {e.Description}"))}"
            : report.SummaryMessage)
    {
        Report = report;
    }
}
