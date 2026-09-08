using Nsdms.Domain.Entities;

namespace Nsdms.Application.Common.Interfaces;

/// <summary>
/// Universal calculation engine for statutory South African working days, institutional closures,
/// and workflow SLA countdowns. Universally skips weekends, public holidays, and active merSETA closures.
/// </summary>
public interface IWorkingDayCalculationEngine
{
    /// <summary>
    /// Computes the target deadline date by adding the specified number of business days,
    /// rigorously skipping weekends, statutory public holidays, and active institutional closures.
    /// </summary>
    Task<DateTime> AddBusinessDaysAsync(DateTime startDate, int businessDays, CancellationToken cancellationToken = default);

    /// <summary>
    /// Determines whether a given calendar date is a recognized working day.
    /// </summary>
    Task<bool> IsWorkingDayAsync(DateTime date, CancellationToken cancellationToken = default);

    /// <summary>
    /// Computes the total number of business/working days between two dates (inclusive).
    /// </summary>
    Task<int> CountBusinessDaysAsync(DateTime startDate, DateTime endDate, CancellationToken cancellationToken = default);

    /// <summary>
    /// Returns all non-working days (statutory holidays and institutional closures) spanning the specified date range.
    /// </summary>
    Task<List<NonWorkingDayBreakdownDto>> GetNonWorkingDaysInRangeAsync(DateTime startDate, DateTime endDate, CancellationToken cancellationToken = default);

    /// <summary>
    /// Generates a full day-by-day SLA trajectory simulation demonstrating which days were skipped and why.
    /// </summary>
    Task<SlaImpactSimulationResultDto> SimulateSlaAsync(DateTime startDate, int businessDays, CancellationToken cancellationToken = default);
}

/// <summary>
/// Breakdown of an individual non-working calendar date.
/// </summary>
public class NonWorkingDayBreakdownDto
{
    public DateTime Date { get; set; }
    public string Name { get; set; } = string.Empty;
    public string TypeCode { get; set; } = string.Empty;
    public string TypeTitle => NonWorkingDayType.GetDisplayTitle(TypeCode);
    public bool AffectsSla { get; set; }
    public string? Reference { get; set; }
}

/// <summary>
/// Simulation result demonstrating day-by-day SLA progression.
/// </summary>
public class SlaImpactSimulationResultDto
{
    public DateTime StartDate { get; set; }
    public int RequestedBusinessDays { get; set; }
    public DateTime CalculatedDueDate { get; set; }
    public int TotalCalendarDaysSpanned { get; set; }
    public int WeekendDaysCount { get; set; }
    public int StatutoryHolidaysCount { get; set; }
    public int InstitutionalClosuresCount { get; set; }
    public List<SlaDayTrajectoryDto> Trajectory { get; set; } = new();
}

/// <summary>
/// Trajectory entry for each calendar day in an SLA timeline.
/// </summary>
public class SlaDayTrajectoryDto
{
    public int DayIndex { get; set; }
    public DateTime Date { get; set; }
    public string DayOfWeek { get; set; } = string.Empty;
    public bool IsWorkingDay { get; set; }
    public int RemainingBusinessDays { get; set; }
    public string StatusBadge { get; set; } = "Working day";
    public string? SkipReason { get; set; }
}
