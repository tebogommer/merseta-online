using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;
using System.Collections.Concurrent;
using System.Globalization;

namespace Nsdms.Application.Services;

/// <summary>
/// Universal calculation engine for statutory South African working days and institutional closures.
/// Evaluates weekend boundaries, South African public holidays (Act 36 of 1994 + Computus),
/// and active merSETA institutional closures where AffectsSla == true.
/// </summary>
public class WorkingDayCalculationEngine : IWorkingDayCalculationEngine
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly ISystemConfigurationService _configService;
    private readonly ILogger<WorkingDayCalculationEngine> _logger;

    // Thread-safe year-indexed memory cache for active closures
    private static readonly ConcurrentDictionary<int, List<NonWorkingDay>> _closuresCache = new();

    public WorkingDayCalculationEngine(
        INsdmsDbContextFactory contextFactory,
        ISystemConfigurationService configService,
        ILogger<WorkingDayCalculationEngine> logger)
    {
        _contextFactory = contextFactory;
        _configService = configService;
        _logger = logger;
    }

    /// <summary>
    /// Clears the thread-safe closure cache when holiday/closure records are modified.
    /// </summary>
    public static void InvalidateCache()
    {
        _closuresCache.Clear();
    }

    /// <summary>
    /// Synchronous static SLA calculation method providing universal backward compatibility
    /// for existing callers (WorkplaceApprovalService, LearnerLifecycleService, TrainingProviderService).
    /// </summary>
    public static DateTime AddBusinessDays(DateTime startDate, int businessDays)
    {
        var current = startDate.Date;
        while (businessDays > 0)
        {
            current = current.AddDays(1);
            if (!IsNonWorkingDate(current))
            {
                businessDays--;
            }
        }
        return current;
    }

    /// <summary>
    /// Checks whether a given calendar date is a weekend, statutory holiday, or cached institutional closure.
    /// </summary>
    public static bool IsNonWorkingDate(DateTime date)
    {
        // 1. Weekend Check
        if (date.DayOfWeek == DayOfWeek.Saturday || date.DayOfWeek == DayOfWeek.Sunday)
        {
            return true;
        }

        // 2. Statutory South African Public Holiday Check (Act 36 of 1994 + Computus)
        if (SouthAfricanPublicHolidays.IsPublicHoliday(date))
        {
            return true;
        }

        // 3. Cached merSETA Institutional Closures Check
        if (_closuresCache.TryGetValue(date.Year, out var closures))
        {
            if (closures.Any(c => c.IsActive && c.AffectsSla && c.StartDate.Date <= date.Date && date.Date <= c.EndDate.Date))
            {
                return true;
            }
        }

        return false;
    }

    public async Task<DateTime> AddBusinessDaysAsync(DateTime startDate, int businessDays, CancellationToken cancellationToken = default)
    {
        var observeClosures = await _configService.GetValueAsync("Calendar:ObserveInstitutionalClosuresInSla", true);
        var closures = observeClosures ? await GetActiveClosuresForSpanAsync(startDate.Year, startDate.Year + 2, cancellationToken) : new();

        var current = startDate.Date;
        while (businessDays > 0)
        {
            current = current.AddDays(1);
            var isWeekend = current.DayOfWeek == DayOfWeek.Saturday || current.DayOfWeek == DayOfWeek.Sunday;
            var isStatutoryHoliday = SouthAfricanPublicHolidays.IsPublicHoliday(current);
            var isClosure = observeClosures && closures.Any(c => c.AffectsSla && c.StartDate.Date <= current.Date && current.Date <= c.EndDate.Date);

            if (!isWeekend && !isStatutoryHoliday && !isClosure)
            {
                businessDays--;
            }
        }

        return current;
    }

    public async Task<bool> IsWorkingDayAsync(DateTime date, CancellationToken cancellationToken = default)
    {
        if (date.DayOfWeek == DayOfWeek.Saturday || date.DayOfWeek == DayOfWeek.Sunday)
        {
            return false;
        }

        if (SouthAfricanPublicHolidays.IsPublicHoliday(date))
        {
            return false;
        }

        var observeClosures = await _configService.GetValueAsync("Calendar:ObserveInstitutionalClosuresInSla", true);
        if (!observeClosures)
        {
            return true;
        }

        var closures = await GetActiveClosuresForSpanAsync(date.Year, date.Year, cancellationToken);
        return !closures.Any(c => c.AffectsSla && c.StartDate.Date <= date.Date && date.Date <= c.EndDate.Date);
    }

    public async Task<int> CountBusinessDaysAsync(DateTime startDate, DateTime endDate, CancellationToken cancellationToken = default)
    {
        if (startDate.Date > endDate.Date)
        {
            return 0;
        }

        var observeClosures = await _configService.GetValueAsync("Calendar:ObserveInstitutionalClosuresInSla", true);
        var closures = observeClosures ? await GetActiveClosuresForSpanAsync(startDate.Year, endDate.Year, cancellationToken) : new();

        int count = 0;
        for (var day = startDate.Date; day <= endDate.Date; day = day.AddDays(1))
        {
            var isWeekend = day.DayOfWeek == DayOfWeek.Saturday || day.DayOfWeek == DayOfWeek.Sunday;
            var isStatutoryHoliday = SouthAfricanPublicHolidays.IsPublicHoliday(day);
            var isClosure = observeClosures && closures.Any(c => c.AffectsSla && c.StartDate.Date <= day.Date && day.Date <= c.EndDate.Date);

            if (!isWeekend && !isStatutoryHoliday && !isClosure)
            {
                count++;
            }
        }

        return count;
    }

    public async Task<List<NonWorkingDayBreakdownDto>> GetNonWorkingDaysInRangeAsync(DateTime startDate, DateTime endDate, CancellationToken cancellationToken = default)
    {
        var result = new List<NonWorkingDayBreakdownDto>();
        var closures = await GetActiveClosuresForSpanAsync(startDate.Year, endDate.Year, cancellationToken);

        for (var day = startDate.Date; day <= endDate.Date; day = day.AddDays(1))
        {
            if (day.DayOfWeek == DayOfWeek.Saturday || day.DayOfWeek == DayOfWeek.Sunday)
            {
                result.Add(new NonWorkingDayBreakdownDto
                {
                    Date = day,
                    Name = day.DayOfWeek == DayOfWeek.Saturday ? "Saturday" : "Sunday",
                    TypeCode = "WEEKEND",
                    AffectsSla = true,
                    Reference = "Standard Weekend"
                });
                continue;
            }

            var closure = closures.FirstOrDefault(c => c.StartDate.Date <= day && day <= c.EndDate.Date);
            if (closure != null)
            {
                result.Add(new NonWorkingDayBreakdownDto
                {
                    Date = day,
                    Name = closure.Name,
                    TypeCode = closure.TypeCode,
                    AffectsSla = closure.AffectsSla,
                    Reference = closure.GazetteOrResolutionRef
                });
                continue;
            }

            if (SouthAfricanPublicHolidays.IsPublicHoliday(day))
            {
                result.Add(new NonWorkingDayBreakdownDto
                {
                    Date = day,
                    Name = "National Public Holiday",
                    TypeCode = NonWorkingDayType.NationalStatutory,
                    AffectsSla = true,
                    Reference = "Public Holidays Act 36 of 1994"
                });
            }
        }

        return result;
    }

    public async Task<SlaImpactSimulationResultDto> SimulateSlaAsync(DateTime startDate, int businessDays, CancellationToken cancellationToken = default)
    {
        var result = new SlaImpactSimulationResultDto
        {
            StartDate = startDate.Date,
            RequestedBusinessDays = businessDays
        };

        var observeClosures = await _configService.GetValueAsync("Calendar:ObserveInstitutionalClosuresInSla", true);
        var closures = observeClosures ? await GetActiveClosuresForSpanAsync(startDate.Year, startDate.Year + 2, cancellationToken) : new();

        var current = startDate.Date;
        var remaining = businessDays;
        int dayIndex = 0;

        while (remaining > 0 || dayIndex == 0)
        {
            if (dayIndex > 0)
            {
                var isWeekend = current.DayOfWeek == DayOfWeek.Saturday || current.DayOfWeek == DayOfWeek.Sunday;
                var isStatutoryHoliday = SouthAfricanPublicHolidays.IsPublicHoliday(current);
                var closure = observeClosures ? closures.FirstOrDefault(c => c.AffectsSla && c.StartDate.Date <= current && current <= c.EndDate.Date) : null;

                var trajectoryItem = new SlaDayTrajectoryDto
                {
                    DayIndex = dayIndex,
                    Date = current,
                    DayOfWeek = current.ToString("dddd", CultureInfo.InvariantCulture)
                };

                if (isWeekend)
                {
                    result.WeekendDaysCount++;
                    trajectoryItem.IsWorkingDay = false;
                    trajectoryItem.StatusBadge = "Weekend";
                    trajectoryItem.SkipReason = $"Standard weekend ({current.DayOfWeek})";
                }
                else if (closure != null)
                {
                    result.InstitutionalClosuresCount++;
                    trajectoryItem.IsWorkingDay = false;
                    trajectoryItem.StatusBadge = closure.TypeCode == NonWorkingDayType.InstitutionalShutdown ? "Shutdown" : "Closure";
                    trajectoryItem.SkipReason = $"{closure.Name} ({closure.GazetteOrResolutionRef ?? "merSETA Closure"})";
                }
                else if (isStatutoryHoliday)
                {
                    result.StatutoryHolidaysCount++;
                    trajectoryItem.IsWorkingDay = false;
                    trajectoryItem.StatusBadge = "Public holiday";
                    trajectoryItem.SkipReason = "Statutory public holiday (Act 36 of 1994)";
                }
                else
                {
                    remaining--;
                    trajectoryItem.IsWorkingDay = true;
                    trajectoryItem.StatusBadge = "Working day";
                    trajectoryItem.SkipReason = null;
                }

                trajectoryItem.RemainingBusinessDays = remaining;
                result.Trajectory.Add(trajectoryItem);

                if (remaining == 0)
                {
                    result.CalculatedDueDate = current;
                    break;
                }
            }

            current = current.AddDays(1);
            dayIndex++;

            if (dayIndex > 365) // Safety circuit breaker
            {
                break;
            }
        }

        result.TotalCalendarDaysSpanned = (result.CalculatedDueDate - result.StartDate).Days;
        return result;
    }

    private async Task<List<NonWorkingDay>> GetActiveClosuresForSpanAsync(int startYear, int endYear, CancellationToken cancellationToken)
    {
        var allClosures = new List<NonWorkingDay>();

        for (int y = startYear; y <= endYear; y++)
        {
            if (_closuresCache.TryGetValue(y, out var cached))
            {
                allClosures.AddRange(cached);
            }
            else
            {
                try
                {
                    using var db = await _contextFactory.CreateDbContextAsync();
                    var fromDb = await db.NonWorkingDays
                        .AsNoTracking()
                        .Where(n => n.CalendarYear == y && n.IsActive && n.StatusCode == NonWorkingDayStatus.Approved)
                        .ToListAsync(cancellationToken);

                    _closuresCache[y] = fromDb;
                    allClosures.AddRange(fromDb);
                }
                catch (Exception ex)
                {
                    _logger.LogWarning(ex, "Failed to load non-working days for year {Year}", y);
                }
            }
        }

        return allClosures;
    }
}
