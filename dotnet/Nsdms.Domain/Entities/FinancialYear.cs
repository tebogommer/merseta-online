using System.ComponentModel.DataAnnotations.Schema;
using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Master statutory financial scheme year definition (e.g. 2026/2027).
/// Supports customizable start and end dates with dynamic quarterly projections.
/// </summary>
[Table("FinancialYear")]
public class FinancialYear : BaseEntity
{
    /// <summary>
    /// Formatted statutory code representing the financial year (e.g. "2026/2027" or "2026").
    /// </summary>
    public string FinYearCode { get; set; } = string.Empty;

    /// <summary>
    /// Calendar start year (e.g. 2026).
    /// </summary>
    public int StartYear { get; set; }

    /// <summary>
    /// Calendar end year (e.g. 2027).
    /// </summary>
    public int EndYear { get; set; }

    /// <summary>
    /// Official statutory effective starting date of the financial year.
    /// </summary>
    public DateTime StartDate { get; set; }

    /// <summary>
    /// Official statutory closing date of the financial year.
    /// </summary>
    public DateTime EndDate { get; set; }

    /// <summary>
    /// Optional administrative description or gazette notice reference.
    /// </summary>
    public string? Description { get; set; }

    /// <summary>
    /// Operational workflow status code (e.g. "Draft", "Under Review", "Active", "Inactive", "Amendment Draft").
    /// </summary>
    public string StatusCode { get; set; } = FinancialYearStatus.Draft;

    /// <summary>
    /// Indicates whether this financial year is currently active for allocations and submissions.
    /// </summary>
    public bool IsActive { get; set; } = false;

    /// <summary>
    /// Indicates whether this financial year has been finalized and closed for further transaction postings.
    /// </summary>
    public bool IsClosed { get; set; } = false;

    /// <summary>
    /// User identifier of the officer who prepared and submitted the financial year for review (Maker).
    /// </summary>
    public string? SubmittedBy { get; set; }

    /// <summary>
    /// Timestamp when the financial year was formally submitted for review.
    /// </summary>
    public DateTime? SubmittedAt { get; set; }

    /// <summary>
    /// Submitter notes or justification submitted with the calendar schedule.
    /// </summary>
    public string? SubmissionNotes { get; set; }

    /// <summary>
    /// User identifier of the authority who adjudicated the submission (Checker).
    /// </summary>
    public string? ReviewedBy { get; set; }

    /// <summary>
    /// Timestamp when the submission was adjudicated (approved or rejected).
    /// </summary>
    public DateTime? ReviewedAt { get; set; }

    /// <summary>
    /// Reviewer feedback or reason for rejection.
    /// </summary>
    public string? ReviewNotes { get; set; }

    /// <summary>
    /// Sequential revision number incremented upon each approved amendment.
    /// </summary>
    public int RevisionNumber { get; set; } = 1;

    /// <summary>
    /// Justification captured when requesting an amendment against an active financial year.
    /// </summary>
    public string? AmendmentReason { get; set; }

    /// <summary>
    /// Constituent statutory quarters (typically 4 quarters: Q1 to Q4).
    /// </summary>
    public ICollection<FinancialQuarter> Quarters { get; set; } = new List<FinancialQuarter>();
}

/// <summary>
/// Constituent statutory quarter belonging to a specific FinancialYear.
/// Supports arbitrary, customizable date boundaries.
/// </summary>
[Table("FinancialQuarter")]
public class FinancialQuarter : BaseEntity
{
    /// <summary>
    /// Relational foreign key referencing the parent FinancialYear.
    /// </summary>
    public int FinancialYearId { get; set; }

    /// <summary>
    /// Navigational reference to the parent FinancialYear.
    /// </summary>
    public FinancialYear? FinancialYear { get; set; }

    /// <summary>
    /// Statutory quarter code (e.g. "Q1", "Q2", "Q3", "Q4").
    /// </summary>
    public string QuarterCode { get; set; } = string.Empty;

    /// <summary>
    /// Sequential quarter ordinal (1, 2, 3, or 4).
    /// </summary>
    public int QuarterNumber { get; set; } = 1;

    /// <summary>
    /// Effective starting date of this statutory quarter.
    /// </summary>
    public DateTime StartDate { get; set; }

    /// <summary>
    /// Closing date of this statutory quarter.
    /// </summary>
    public DateTime EndDate { get; set; }

    /// <summary>
    /// Optional administrative description or milestone notes.
    /// </summary>
    public string? Description { get; set; }

    /// <summary>
    /// Indicates whether transactions and metrics for this quarter are locked against modifications.
    /// </summary>
    public bool IsLocked { get; set; } = false;

    /// <summary>
    /// Indicates whether this quarter is finalized.
    /// </summary>
    public bool IsClosed { get; set; } = false;
}

/// <summary>
/// Canonical workflow lifecycle status definitions for FinancialSchemeYear records.
/// Enforces non-presumptive nomenclature per statutory governance standards.
/// </summary>
public static class FinancialYearStatus
{
    /// <summary>
    /// Initial draft state. Fully editable with live contiguity feedback.
    /// </summary>
    public const string Draft = "Draft";

    /// <summary>
    /// Formally submitted by Maker. Locked against arbitrary edits while awaiting adjudication.
    /// Non-presumptive terminology replaces "Pending Approval".
    /// </summary>
    public const string UnderReview = "Under Review";

    /// <summary>
    /// Formally approved by Checker authority. Active statutory operational scheme year, strictly locked.
    /// </summary>
    public const string Active = "Active";

    /// <summary>
    /// Concluded or historical financial year, finalized and archived.
    /// </summary>
    public const string Inactive = "Inactive";

    /// <summary>
    /// Revision created via an authorized "Request Amendment" action on an active year.
    /// </summary>
    public const string AmendmentDraft = "Amendment Draft";

    /// <summary>
    /// All permitted statuses in canonical lifecycle order.
    /// </summary>
    public static readonly IReadOnlyList<string> All = new[]
    {
        Draft,
        UnderReview,
        Active,
        Inactive,
        AmendmentDraft
    };
}
