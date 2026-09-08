using System.ComponentModel.DataAnnotations.Schema;
using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Master definition for national statutory public holidays, merSETA annual year-end shutdowns,
/// and ad-hoc institutional closures. Governs universal workflow SLA business day calculations.
/// </summary>
[Table("NonWorkingDay")]
public class NonWorkingDay : BaseEntity
{
    /// <summary>
    /// Official title or holiday designation (e.g. "Day of Goodwill", "merSETA Annual Year-End Shutdown").
    /// </summary>
    public string Name { get; set; } = string.Empty;

    /// <summary>
    /// Closure typology code (e.g. "NAT_STATUTORY", "INST_SHUTDOWN", "ADHOC_GAZETTED", "SPEC_CLOSURE").
    /// </summary>
    public string TypeCode { get; set; } = NonWorkingDayType.NationalStatutory;

    /// <summary>
    /// Calendar start date of the closure (inclusive).
    /// </summary>
    public DateTime StartDate { get; set; }

    /// <summary>
    /// Calendar end date of the closure (inclusive). Same as StartDate for single-day holidays.
    /// </summary>
    public DateTime EndDate { get; set; }

    /// <summary>
    /// Calendar year to which this holiday or closure record applies.
    /// </summary>
    public int CalendarYear { get; set; }

    /// <summary>
    /// When true, workflow SLA countdowns (e.g. 20-day Workplace Approval, 14-day Dispute SLA) skip this period.
    /// </summary>
    public bool AffectsSla { get; set; } = true;

    /// <summary>
    /// Indicates whether this holiday recurs annually on the same calendar month/day.
    /// </summary>
    public bool IsRecurringAnnually { get; set; } = false;

    /// <summary>
    /// Official statutory gazette number, board resolution reference, or executive circular (e.g. "Act 36 of 1994", "merSETA Circular 2026-12").
    /// </summary>
    public string? GazetteOrResolutionRef { get; set; }

    /// <summary>
    /// Administrative description, operational notes, or justification for institutional closure.
    /// </summary>
    public string? Description { get; set; }

    /// <summary>
    /// Operational status code ("Draft", "Approved", "Inactive").
    /// </summary>
    public string StatusCode { get; set; } = NonWorkingDayStatus.Approved;

    /// <summary>
    /// Indicates whether this non-working day is active and observed by the system.
    /// </summary>
    public bool IsActive { get; set; } = true;

    /// <summary>
    /// Indicates whether this historical closure is locked and archived.
    /// </summary>
    public bool IsClosed { get; set; } = false;

    /// <summary>
    /// Total number of calendar days spanned by this record.
    /// </summary>
    [NotMapped]
    public int TotalDays => Math.Max(1, (EndDate.Date - StartDate.Date).Days + 1);

    /// <summary>
    /// Indicates whether this record spans multiple calendar days (e.g. year-end shutdown).
    /// </summary>
    [NotMapped]
    public bool IsDateSpan => EndDate.Date > StartDate.Date;
}

/// <summary>
/// Statutory typology constants for non-working days and institutional closures.
/// </summary>
public static class NonWorkingDayType
{
    /// <summary>
    /// National South African Statutory Public Holiday (Public Holidays Act 36 of 1994).
    /// </summary>
    public const string NationalStatutory = "NAT_STATUTORY";

    /// <summary>
    /// merSETA Annual Year-End Office Shutdown (typically 24 December to early January).
    /// </summary>
    public const string InstitutionalShutdown = "INST_SHUTDOWN";

    /// <summary>
    /// Ad-Hoc Gazetted National Holiday (e.g. General Elections, Presidential proclamations).
    /// </summary>
    public const string AdHocGazetted = "ADHOC_GAZETTED";

    /// <summary>
    /// Special Administrative Closure (e.g. regional office closure, facility maintenance, emergency).
    /// </summary>
    public const string SpecialClosure = "SPEC_CLOSURE";

    public static string GetDisplayTitle(string code) => code switch
    {
        NationalStatutory => "National statutory holiday",
        InstitutionalShutdown => "merSETA annual year-end shutdown",
        AdHocGazetted => "Ad-hoc gazetted holiday",
        SpecialClosure => "Special administrative closure",
        _ => code
    };
}

/// <summary>
/// Workflow status codes for holiday and institutional closure records.
/// </summary>
public static class NonWorkingDayStatus
{
    public const string Draft = "Draft";
    public const string Approved = "Approved";
    public const string Inactive = "Inactive";
}
