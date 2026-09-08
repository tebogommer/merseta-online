using System.Globalization;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Common.Models;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

/// <summary>
/// Production service for managing statutory financial years, custom quarters,
/// and dynamically computing constituent months and working day projections.
/// </summary>
public class FiscalCalendarService : IFiscalCalendarService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _auditService;
    private readonly ISystemConfigurationService _configService;
    private readonly ILogger<FiscalCalendarService> _logger;

    public FiscalCalendarService(
        INsdmsDbContextFactory contextFactory,
        IAuditService auditService,
        ISystemConfigurationService configService,
        ILogger<FiscalCalendarService> logger)
    {
        _contextFactory = contextFactory;
        _auditService = auditService;
        _configService = configService;
        _logger = logger;
    }

    public async Task<List<FinancialYear>> GetAllFinancialYearsAsync(bool activeOnly = false, CancellationToken cancellationToken = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync(cancellationToken);
        var query = db.FinancialYears
            .Include(f => f.Quarters)
            .AsNoTracking();

        if (activeOnly)
        {
            query = query.Where(f => f.IsActive);
        }

        return await query
            .OrderByDescending(f => f.StartYear)
            .ToListAsync(cancellationToken);
    }

    public async Task<FinancialYear?> GetFinancialYearByIdAsync(int id, CancellationToken cancellationToken = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync(cancellationToken);
        return await db.FinancialYears
            .Include(f => f.Quarters.OrderBy(q => q.QuarterNumber))
            .FirstOrDefaultAsync(f => f.Id == id, cancellationToken);
    }

    public async Task<FinancialYear?> GetFinancialYearByCodeAsync(string finYearCode, CancellationToken cancellationToken = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync(cancellationToken);
        return await db.FinancialYears
            .Include(f => f.Quarters.OrderBy(q => q.QuarterNumber))
            .FirstOrDefaultAsync(f => f.FinYearCode == finYearCode, cancellationToken);
    }

    public async Task<FinancialYear> CreateFinancialYearAsync(CreateFinancialYearDto dto, string userId, CancellationToken cancellationToken = default)
    {
        var validation = ValidateQuartersContiguity(dto.StartDate, dto.EndDate, dto.Quarters);
        if (!validation.IsValid)
        {
            throw new InvalidOperationException($"Fiscal calendar validation failed: {string.Join("; ", validation.Errors)}");
        }

        using var db = await _contextFactory.CreateDbContextAsync(cancellationToken);

        // Verify uniqueness of FinYearCode
        var existing = await db.FinancialYears.AnyAsync(f => f.FinYearCode == dto.FinYearCode, cancellationToken);
        if (existing)
        {
            throw new InvalidOperationException($"Financial year code '{dto.FinYearCode}' already exists.");
        }

        var entity = new FinancialYear
        {
            FinYearCode = dto.FinYearCode.Trim(),
            StartYear = dto.StartYear,
            EndYear = dto.EndYear,
            StartDate = dto.StartDate.Date,
            EndDate = dto.EndDate.Date,
            Description = dto.Description?.Trim(),
            StatusCode = string.IsNullOrWhiteSpace(dto.StatusCode) ? FinancialYearStatus.Draft : dto.StatusCode.Trim(),
            IsActive = dto.StatusCode == FinancialYearStatus.Active,
            IsClosed = false,
            CreatedBy = userId,
            CreatedAt = DateTime.UtcNow
        };

        foreach (var q in dto.Quarters.OrderBy(q => q.QuarterNumber))
        {
            entity.Quarters.Add(new FinancialQuarter
            {
                QuarterCode = q.QuarterCode.Trim().ToUpperInvariant(),
                QuarterNumber = q.QuarterNumber,
                StartDate = q.StartDate.Date,
                EndDate = q.EndDate.Date,
                Description = q.Description?.Trim(),
                IsLocked = q.IsLocked,
                IsClosed = q.IsClosed,
                CreatedBy = userId,
                CreatedAt = DateTime.UtcNow
            });
        }

        db.FinancialYears.Add(entity);
        await db.SaveChangesAsync(cancellationToken);

        // Audited Change Log
        _auditService.LogAction(
            db,
            nameof(FinancialYear),
            entity.Id,
            "CreateFinancialYear",
            userId,
            null,
            new
            {
                entity.FinYearCode,
                entity.StartYear,
                entity.EndYear,
                entity.StartDate,
                entity.EndDate,
                entity.StatusCode,
                QuartersCount = entity.Quarters.Count
            });
        await db.SaveChangesAsync(cancellationToken);

        _logger.LogInformation("Created new financial year '{FinYearCode}' (ID: {Id}, Status: {Status}) with {Count} quarters by user {UserId}.",
            entity.FinYearCode, entity.Id, entity.StatusCode, entity.Quarters.Count, userId);

        return entity;
    }

    public async Task<FinancialYear> UpdateFinancialYearAsync(UpdateFinancialYearDto dto, string userId, CancellationToken cancellationToken = default)
    {
        var validation = ValidateQuartersContiguity(dto.StartDate, dto.EndDate, dto.Quarters);
        if (!validation.IsValid)
        {
            throw new InvalidOperationException($"Fiscal calendar validation failed: {string.Join("; ", validation.Errors)}");
        }

        using var db = await _contextFactory.CreateDbContextAsync(cancellationToken);
        var entity = await db.FinancialYears
            .Include(f => f.Quarters)
            .FirstOrDefaultAsync(f => f.Id == dto.Id, cancellationToken);

        if (entity == null)
        {
            throw new KeyNotFoundException($"Financial year with ID {dto.Id} was not found.");
        }

        if (entity.StatusCode == FinancialYearStatus.Active || entity.StatusCode == FinancialYearStatus.UnderReview)
        {
            throw new InvalidOperationException($"Financial year '{entity.FinYearCode}' cannot be directly edited while in status '{entity.StatusCode}'. Active financial years require an approved amendment.");
        }

        var beforeState = new
        {
            entity.FinYearCode,
            entity.StartDate,
            entity.EndDate,
            entity.StatusCode,
            entity.IsActive,
            entity.IsClosed,
            Quarters = entity.Quarters.Select(q => new { q.QuarterCode, q.StartDate, q.EndDate, q.IsLocked }).ToList()
        };

        // Update master properties
        entity.FinYearCode = dto.FinYearCode.Trim();
        entity.StartYear = dto.StartYear;
        entity.EndYear = dto.EndYear;
        entity.StartDate = dto.StartDate.Date;
        entity.EndDate = dto.EndDate.Date;
        entity.Description = dto.Description?.Trim();
        entity.StatusCode = dto.StatusCode.Trim();
        entity.IsActive = dto.IsActive;
        entity.IsClosed = dto.IsClosed;
        entity.ModifiedBy = userId;
        entity.ModifiedAt = DateTime.UtcNow;

        // Update or replace quarters
        var incomingCodes = dto.Quarters.Select(q => q.QuarterCode.ToUpperInvariant()).ToHashSet();
        
        // Remove quarters not in incoming list
        var toRemove = entity.Quarters.Where(q => !incomingCodes.Contains(q.QuarterCode.ToUpperInvariant())).ToList();
        foreach (var r in toRemove)
        {
            db.FinancialQuarters.Remove(r);
        }

        // Update existing or add new
        foreach (var qDto in dto.Quarters.OrderBy(q => q.QuarterNumber))
        {
            var existingQ = entity.Quarters.FirstOrDefault(q => q.QuarterCode.Equals(qDto.QuarterCode, StringComparison.OrdinalIgnoreCase));
            if (existingQ != null)
            {
                existingQ.QuarterNumber = qDto.QuarterNumber;
                existingQ.StartDate = qDto.StartDate.Date;
                existingQ.EndDate = qDto.EndDate.Date;
                existingQ.Description = qDto.Description?.Trim();
                existingQ.IsLocked = qDto.IsLocked;
                existingQ.IsClosed = qDto.IsClosed;
                existingQ.ModifiedBy = userId;
                existingQ.ModifiedAt = DateTime.UtcNow;
            }
            else
            {
                entity.Quarters.Add(new FinancialQuarter
                {
                    FinancialYearId = entity.Id,
                    QuarterCode = qDto.QuarterCode.Trim().ToUpperInvariant(),
                    QuarterNumber = qDto.QuarterNumber,
                    StartDate = qDto.StartDate.Date,
                    EndDate = qDto.EndDate.Date,
                    Description = qDto.Description?.Trim(),
                    IsLocked = qDto.IsLocked,
                    IsClosed = qDto.IsClosed,
                    CreatedBy = userId,
                    CreatedAt = DateTime.UtcNow
                });
            }
        }

        var afterState = new
        {
            entity.FinYearCode,
            entity.StartDate,
            entity.EndDate,
            entity.StatusCode,
            entity.IsActive,
            entity.IsClosed,
            Quarters = entity.Quarters.Select(q => new { q.QuarterCode, q.StartDate, q.EndDate, q.IsLocked }).ToList()
        };

        // Audited Change Log
        _auditService.LogAction(
            db,
            nameof(FinancialYear),
            entity.Id,
            "UpdateFinancialYear",
            userId,
            beforeState,
            afterState);

        await db.SaveChangesAsync(cancellationToken);

        _logger.LogInformation("Updated financial year '{FinYearCode}' (ID: {Id}) by user {UserId}.", entity.FinYearCode, entity.Id, userId);
        return entity;
    }

    public async Task<bool> DeleteFinancialYearAsync(int id, string userId, CancellationToken cancellationToken = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync(cancellationToken);
        var entity = await db.FinancialYears
            .Include(f => f.Quarters)
            .FirstOrDefaultAsync(f => f.Id == id, cancellationToken);

        if (entity == null)
        {
            return false;
        }

        var snapshot = new
        {
            entity.Id,
            entity.FinYearCode,
            entity.StartDate,
            entity.EndDate,
            QuartersCount = entity.Quarters.Count
        };

        db.FinancialYears.Remove(entity);
        _auditService.LogAction(
            db,
            nameof(FinancialYear),
            id,
            "DeleteFinancialYear",
            userId,
            snapshot,
            null);
        await db.SaveChangesAsync(cancellationToken);

        _logger.LogInformation("Deleted financial year '{FinYearCode}' (ID: {Id}) by user {UserId}.", entity.FinYearCode, id, userId);
        return true;
    }

    public FiscalYearBreakdownDto ComputeYearBreakdown(
        string finYearCode,
        int startYear,
        int endYear,
        DateTime startDate,
        DateTime endDate,
        List<QuarterDateRangeDto> quarters)
    {
        var yearBreakdown = new FiscalYearBreakdownDto
        {
            FinYearCode = finYearCode,
            StartYear = startYear,
            EndYear = endYear,
            StartDate = startDate.Date,
            EndDate = endDate.Date
        };

        foreach (var q in quarters.OrderBy(q => q.QuarterNumber))
        {
            var qBreakdown = new FiscalQuarterBreakdownDto
            {
                QuarterCode = q.QuarterCode,
                QuarterNumber = q.QuarterNumber,
                StartDate = q.StartDate.Date,
                EndDate = q.EndDate.Date
            };

            // Slice quarter date range into calendar months
            var currentCursor = q.StartDate.Date;
            var quarterEnd = q.EndDate.Date;

            while (currentCursor <= quarterEnd)
            {
                var year = currentCursor.Year;
                var month = currentCursor.Month;

                // Month boundary
                var monthStart = new DateTime(year, month, 1);
                var daysInMonth = DateTime.DaysInMonth(year, month);
                var monthEnd = new DateTime(year, month, daysInMonth);

                // Effective slice within this quarter
                var sliceStart = currentCursor > monthStart ? currentCursor : monthStart;
                var sliceEnd = quarterEnd < monthEnd ? quarterEnd : monthEnd;

                var calendarDays = (int)(sliceEnd - sliceStart).TotalDays + 1;
                var workingDays = 0;
                var publicHolidaysCount = 0;
                var holidaysObserved = new List<string>();

                // Get South African statutory public holidays for this year
                var saHolidays = SouthAfricanPublicHolidays.GetPublicHolidays(year);

                for (var day = sliceStart; day <= sliceEnd; day = day.AddDays(1))
                {
                    var isWeekend = day.DayOfWeek == DayOfWeek.Saturday || day.DayOfWeek == DayOfWeek.Sunday;
                    var isHoliday = saHolidays.Contains(day.Date);
                    var isClosure = !isWeekend && !isHoliday && WorkingDayCalculationEngine.IsNonWorkingDate(day);

                    if (isHoliday)
                    {
                        publicHolidaysCount++;
                        var holidayName = GetHolidayName(day);
                        if (!holidaysObserved.Contains(holidayName))
                        {
                            holidaysObserved.Add(holidayName);
                        }
                    }
                    else if (isClosure)
                    {
                        publicHolidaysCount++;
                        var closureName = "merSETA Institutional Closure";
                        if (!holidaysObserved.Contains(closureName))
                        {
                            holidaysObserved.Add(closureName);
                        }
                    }

                    if (!isWeekend && !isHoliday && !isClosure)
                    {
                        workingDays++;
                    }
                }

                var monthBreakdown = new FiscalMonthBreakdownDto
                {
                    MonthNumber = month,
                    MonthName = CultureInfo.InvariantCulture.DateTimeFormat.GetMonthName(month),
                    CalendarYear = year,
                    StartDate = sliceStart,
                    EndDate = sliceEnd,
                    CalendarDaysCount = calendarDays,
                    WorkingDaysCount = workingDays,
                    PublicHolidaysCount = publicHolidaysCount,
                    ObservedHolidays = holidaysObserved
                };

                qBreakdown.Months.Add(monthBreakdown);
                qBreakdown.TotalCalendarDays += calendarDays;
                qBreakdown.TotalWorkingDays += workingDays;
                qBreakdown.TotalPublicHolidays += publicHolidaysCount;

                // Advance cursor to next month
                currentCursor = monthEnd.AddDays(1);
            }

            yearBreakdown.Quarters.Add(qBreakdown);
            yearBreakdown.TotalCalendarDays += qBreakdown.TotalCalendarDays;
            yearBreakdown.TotalWorkingDays += qBreakdown.TotalWorkingDays;
            yearBreakdown.TotalPublicHolidays += qBreakdown.TotalPublicHolidays;
        }

        return yearBreakdown;
    }

    public FiscalValidationResult ValidateQuartersContiguity(DateTime yearStart, DateTime yearEnd, List<QuarterDateRangeDto> quarters)
    {
        var result = new FiscalValidationResult();

        if (yearStart >= yearEnd)
        {
            result.Errors.Add($"Financial year start date ({yearStart:yyyy-MM-dd}) must be earlier than end date ({yearEnd:yyyy-MM-dd}).");
            return result;
        }

        if (quarters == null || quarters.Count == 0)
        {
            result.Errors.Add("At least one quarter must be defined for the financial year.");
            return result;
        }

        var sorted = quarters.OrderBy(q => q.QuarterNumber).ToList();

        // 1. Check that Quarter 1 starts on Year Start Date
        var firstQ = sorted[0];
        if (firstQ.StartDate.Date != yearStart.Date)
        {
            result.Errors.Add($"First quarter ({firstQ.QuarterCode}) start date ({firstQ.StartDate:yyyy-MM-dd}) must match the financial year start date ({yearStart:yyyy-MM-dd}).");
        }

        // 2. Check that Last Quarter ends on Year End Date
        var lastQ = sorted[^1];
        if (lastQ.EndDate.Date != yearEnd.Date)
        {
            result.Errors.Add($"Last quarter ({lastQ.QuarterCode}) end date ({lastQ.EndDate:yyyy-MM-dd}) must match the financial year end date ({yearEnd:yyyy-MM-dd}).");
        }

        // 3. Check individual quarter date validity and contiguity across sequential quarters
        for (var i = 0; i < sorted.Count; i++)
        {
            var current = sorted[i];

            if (current.StartDate > current.EndDate)
            {
                result.Errors.Add($"Quarter {current.QuarterCode} start date ({current.StartDate:yyyy-MM-dd}) cannot be after its end date ({current.EndDate:yyyy-MM-dd}).");
            }

            if (i < sorted.Count - 1)
            {
                var next = sorted[i + 1];
                var expectedNextStart = current.EndDate.Date.AddDays(1);

                if (current.EndDate.Date >= next.StartDate.Date)
                {
                    result.Errors.Add($"Quarter {current.QuarterCode} overlaps with Quarter {next.QuarterCode}: {current.QuarterCode} ends on {current.EndDate:yyyy-MM-dd} while {next.QuarterCode} starts on {next.StartDate:yyyy-MM-dd}.");
                }
                else if (next.StartDate.Date != expectedNextStart)
                {
                    result.Errors.Add($"Date gap detected between Quarter {current.QuarterCode} and Quarter {next.QuarterCode}: Expected {next.QuarterCode} to start on {expectedNextStart:yyyy-MM-dd}, but starts on {next.StartDate:yyyy-MM-dd}.");
                }
            }
        }

        return result;
    }

    public async Task<FiscalTemplateConfigDto> GetDefaultTemplateAsync(CancellationToken cancellationToken = default)
    {
        return new FiscalTemplateConfigDto
        {
            DefaultStartMonthDay = await _configService.GetValueAsync("Fiscal:DefaultStartMonthDay", "04-01"),
            DefaultEndMonthDay = await _configService.GetValueAsync("Fiscal:DefaultEndMonthDay", "03-31"),
            Q1StartMonthDay = await _configService.GetValueAsync("Fiscal:DefaultQ1Start", "04-01"),
            Q1EndMonthDay = await _configService.GetValueAsync("Fiscal:DefaultQ1End", "06-30"),
            Q2StartMonthDay = await _configService.GetValueAsync("Fiscal:DefaultQ2Start", "07-01"),
            Q2EndMonthDay = await _configService.GetValueAsync("Fiscal:DefaultQ2End", "09-30"),
            Q3StartMonthDay = await _configService.GetValueAsync("Fiscal:DefaultQ3Start", "10-01"),
            Q3EndMonthDay = await _configService.GetValueAsync("Fiscal:DefaultQ3End", "12-31"),
            Q4StartMonthDay = await _configService.GetValueAsync("Fiscal:DefaultQ4Start", "01-01"),
            Q4EndMonthDay = await _configService.GetValueAsync("Fiscal:DefaultQ4End", "03-31")
        };
    }

    public async Task<bool> UpdateDefaultTemplateAsync(FiscalTemplateConfigDto dto, string userId, CancellationToken cancellationToken = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync(cancellationToken);

        await _configService.SetConfigAsync("Fiscal:DefaultStartMonthDay", dto.DefaultStartMonthDay, "Fiscal Calendar", "Default statutory financial scheme year start date (MM-DD)", "String", userId);
        await _configService.SetConfigAsync("Fiscal:DefaultEndMonthDay", dto.DefaultEndMonthDay, "Fiscal Calendar", "Default statutory financial scheme year end date (MM-DD)", "String", userId);
        await _configService.SetConfigAsync("Fiscal:DefaultQ1Start", dto.Q1StartMonthDay, "Fiscal Calendar", "Default statutory Quarter 1 start date (MM-DD)", "String", userId);
        await _configService.SetConfigAsync("Fiscal:DefaultQ1End", dto.Q1EndMonthDay, "Fiscal Calendar", "Default statutory Quarter 1 end date (MM-DD)", "String", userId);
        await _configService.SetConfigAsync("Fiscal:DefaultQ2Start", dto.Q2StartMonthDay, "Fiscal Calendar", "Default statutory Quarter 2 start date (MM-DD)", "String", userId);
        await _configService.SetConfigAsync("Fiscal:DefaultQ2End", dto.Q2EndMonthDay, "Fiscal Calendar", "Default statutory Quarter 2 end date (MM-DD)", "String", userId);
        await _configService.SetConfigAsync("Fiscal:DefaultQ3Start", dto.Q3StartMonthDay, "Fiscal Calendar", "Default statutory Quarter 3 start date (MM-DD)", "String", userId);
        await _configService.SetConfigAsync("Fiscal:DefaultQ3End", dto.Q3EndMonthDay, "Fiscal Calendar", "Default statutory Quarter 3 end date (MM-DD)", "String", userId);
        await _configService.SetConfigAsync("Fiscal:DefaultQ4Start", dto.Q4StartMonthDay, "Fiscal Calendar", "Default statutory Quarter 4 start date (MM-DD)", "String", userId);
        await _configService.SetConfigAsync("Fiscal:DefaultQ4End", dto.Q4EndMonthDay, "Fiscal Calendar", "Default statutory Quarter 4 end date (MM-DD)", "String", userId);

        _auditService.LogAction(
            db,
            "SystemConfig",
            0,
            "UpdateFiscalTemplate",
            userId,
            null,
            dto);
        await db.SaveChangesAsync(cancellationToken);

        _logger.LogInformation("Updated statutory fiscal calendar default template by user {UserId}.", userId);
        return true;
    }

    public List<QuarterDateRangeDto> GenerateDefaultQuartersForYear(int startYear, FiscalTemplateConfigDto template)
    {
        DateTime ParseDate(string mmdd, int yr)
        {
            var parts = mmdd.Split('-');
            var m = int.Parse(parts[0], CultureInfo.InvariantCulture);
            var d = int.Parse(parts[1], CultureInfo.InvariantCulture);
            var daysInM = DateTime.DaysInMonth(yr, m);
            if (d > daysInM) d = daysInM; // Handle leap-year Feb or 30-day adjustments
            return new DateTime(yr, m, d);
        }

        // Determine if Q4 is in next calendar year
        var q1Start = ParseDate(template.Q1StartMonthDay, startYear);
        var q1End = ParseDate(template.Q1EndMonthDay, startYear);
        var q2Start = ParseDate(template.Q2StartMonthDay, startYear);
        var q2End = ParseDate(template.Q2EndMonthDay, startYear);
        var q3Start = ParseDate(template.Q3StartMonthDay, startYear);
        var q3End = ParseDate(template.Q3EndMonthDay, startYear);

        // Q4 year resolution
        var q4StartParts = template.Q4StartMonthDay.Split('-');
        var q4StartMonth = int.Parse(q4StartParts[0], CultureInfo.InvariantCulture);
        var q4Year = q4StartMonth < q1Start.Month ? startYear + 1 : startYear;

        var q4Start = ParseDate(template.Q4StartMonthDay, q4Year);
        var q4End = ParseDate(template.Q4EndMonthDay, q4Year);

        return new List<QuarterDateRangeDto>
        {
            new() { QuarterCode = "Q1", QuarterNumber = 1, StartDate = q1Start, EndDate = q1End, Description = "Quarter 1 (Statutory Intake & WSP Submissions)" },
            new() { QuarterCode = "Q2", QuarterNumber = 2, StartDate = q2Start, EndDate = q2End, Description = "Quarter 2 (Mid-Year Review & DG Tranche 1)" },
            new() { QuarterCode = "Q3", QuarterNumber = 3, StartDate = q3Start, EndDate = q3End, Description = "Quarter 3 (Statutory SETMIS & Milestone Audits)" },
            new() { QuarterCode = "Q4", QuarterNumber = 4, StartDate = q4Start, EndDate = q4End, Description = "Quarter 4 (Financial Year-End Reconciliations)" }
        };
    }

    private static string GetHolidayName(DateTime date)
    {
        return (date.Month, date.Day) switch
        {
            (1, 1) => "New Year's Day",
            (1, 2) when date.DayOfWeek == DayOfWeek.Monday => "New Year's Day (Observed)",
            (3, 21) => "Human Rights Day",
            (3, 22) when date.DayOfWeek == DayOfWeek.Monday => "Human Rights Day (Observed)",
            (4, 27) => "Freedom Day",
            (4, 28) when date.DayOfWeek == DayOfWeek.Monday => "Freedom Day (Observed)",
            (5, 1) => "Workers' Day",
            (5, 2) when date.DayOfWeek == DayOfWeek.Monday => "Workers' Day (Observed)",
            (6, 16) => "Youth Day",
            (6, 17) when date.DayOfWeek == DayOfWeek.Monday => "Youth Day (Observed)",
            (8, 9) => "National Women's Day",
            (8, 10) when date.DayOfWeek == DayOfWeek.Monday => "National Women's Day (Observed)",
            (9, 24) => "Heritage Day",
            (9, 25) when date.DayOfWeek == DayOfWeek.Monday => "Heritage Day (Observed)",
            (12, 16) => "Day of Reconciliation",
            (12, 17) when date.DayOfWeek == DayOfWeek.Monday => "Day of Reconciliation (Observed)",
            (12, 25) => "Christmas Day",
            (12, 26) => "Day of Goodwill",
            (12, 27) when date.DayOfWeek == DayOfWeek.Tuesday => "Christmas Day (Observed)",
            _ => "Public Holiday"
        };
    }

    public async Task<FinancialYear> SubmitForReviewAsync(int id, string? notes, string userId, CancellationToken cancellationToken = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync(cancellationToken);
        var entity = await db.FinancialYears
            .Include(f => f.Quarters)
            .FirstOrDefaultAsync(f => f.Id == id, cancellationToken);

        if (entity == null)
        {
            throw new KeyNotFoundException($"Financial year with ID {id} was not found.");
        }

        if (entity.StatusCode != FinancialYearStatus.Draft && entity.StatusCode != FinancialYearStatus.AmendmentDraft)
        {
            throw new InvalidOperationException($"Financial year '{entity.FinYearCode}' cannot be submitted for review from status '{entity.StatusCode}'. Only Draft or Amendment Draft records may be submitted.");
        }

        // Validate contiguity before submission
        var quartersDto = entity.Quarters.Select(q => new QuarterDateRangeDto
        {
            QuarterCode = q.QuarterCode,
            QuarterNumber = q.QuarterNumber,
            StartDate = q.StartDate,
            EndDate = q.EndDate,
            Description = q.Description
        }).ToList();

        var validation = ValidateQuartersContiguity(entity.StartDate, entity.EndDate, quartersDto);
        if (!validation.IsValid)
        {
            throw new InvalidOperationException($"Cannot submit financial year for review due to contiguity issues: {string.Join("; ", validation.Errors)}");
        }

        var beforeState = new { entity.StatusCode, entity.SubmittedBy, entity.SubmittedAt };

        entity.StatusCode = FinancialYearStatus.UnderReview;
        entity.SubmittedBy = userId;
        entity.SubmittedAt = DateTime.UtcNow;
        entity.SubmissionNotes = notes?.Trim();
        entity.ModifiedBy = userId;
        entity.ModifiedAt = DateTime.UtcNow;

        _auditService.LogAction(
            db,
            nameof(FinancialYear),
            entity.Id,
            "SubmitFinancialYearForReview",
            userId,
            beforeState,
            new { entity.StatusCode, entity.SubmittedBy, entity.SubmittedAt, entity.SubmissionNotes });

        await db.SaveChangesAsync(cancellationToken);
        _logger.LogInformation("Financial year '{FinYearCode}' (ID: {Id}) submitted for review by user {UserId}.", entity.FinYearCode, entity.Id, userId);
        return entity;
    }

    public async Task<FinancialYear> ApproveFiscalYearAsync(int id, string? reviewNotes, string userId, IEnumerable<string> userRoles, CancellationToken cancellationToken = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync(cancellationToken);
        var entity = await db.FinancialYears
            .Include(f => f.Quarters)
            .FirstOrDefaultAsync(f => f.Id == id, cancellationToken);

        if (entity == null)
        {
            throw new KeyNotFoundException($"Financial year with ID {id} was not found.");
        }

        if (entity.StatusCode != FinancialYearStatus.UnderReview)
        {
            throw new InvalidOperationException($"Financial year '{entity.FinYearCode}' cannot be approved from status '{entity.StatusCode}'. It must be in 'Under Review'.");
        }

        // 1. Resolve Dynamic Governance Rules
        var allowAnyAdmin = await _configService.GetValueAsync<bool>("Fiscal:AllowAnyAdminReviewer", true);
        var enforceMakerChecker = await _configService.GetValueAsync<bool>("Fiscal:EnforceMakerCheckerSegregation", true);
        var requiredRolesStr = await _configService.GetValueAsync<string>("Fiscal:RequiredReviewRole", "Admin,SuperAdmin");
        var allowMultipleActive = await _configService.GetValueAsync<bool>("Fiscal:AllowMultipleActiveYears", false);

        // 2. Enforce Segregation of Duties (Maker-Checker Invariant)
        if (enforceMakerChecker && !string.IsNullOrWhiteSpace(entity.SubmittedBy) && string.Equals(entity.SubmittedBy, userId, StringComparison.OrdinalIgnoreCase))
        {
            throw new InvalidOperationException("Segregation of duties violation: The submitter cannot approve their own financial year submission. An independent reviewer is required.");
        }

        // 3. Enforce Role-Based Adjudication Authority
        var roleList = userRoles?.ToList() ?? new List<string>();
        var allowedRoles = requiredRolesStr.Split(',', StringSplitOptions.TrimEntries | StringSplitOptions.RemoveEmptyEntries);
        bool hasSpecificRole = roleList.Any(r => allowedRoles.Contains(r, StringComparer.OrdinalIgnoreCase));
        bool hasAdminFallback = allowAnyAdmin && roleList.Any(r => r.Contains("Admin", StringComparison.OrdinalIgnoreCase));

        if (!hasSpecificRole && !hasAdminFallback)
        {
            throw new UnauthorizedAccessException("You are not authorized to review or approve financial scheme years.");
        }

        // 4. Validate Contiguity Invariant
        var quartersDto = entity.Quarters.Select(q => new QuarterDateRangeDto
        {
            QuarterCode = q.QuarterCode,
            QuarterNumber = q.QuarterNumber,
            StartDate = q.StartDate,
            EndDate = q.EndDate,
            Description = q.Description
        }).ToList();

        var validation = ValidateQuartersContiguity(entity.StartDate, entity.EndDate, quartersDto);
        if (!validation.IsValid)
        {
            throw new InvalidOperationException($"Cannot approve financial year due to contiguity issues: {string.Join("; ", validation.Errors)}");
        }

        // 5. Auto-deactivate predecessor active years if single active year enforced
        if (!allowMultipleActive)
        {
            var otherActiveYears = await db.FinancialYears
                .Where(f => f.Id != id && f.IsActive)
                .ToListAsync(cancellationToken);

            foreach (var other in otherActiveYears)
            {
                other.IsActive = false;
                other.StatusCode = FinancialYearStatus.Inactive;
                other.IsClosed = true;
                other.ModifiedBy = userId;
                other.ModifiedAt = DateTime.UtcNow;

                _auditService.LogAction(
                    db,
                    nameof(FinancialYear),
                    other.Id,
                    "SupersededByNewActiveYear",
                    userId,
                    new { other.FinYearCode, OldStatus = FinancialYearStatus.Active },
                    new { other.FinYearCode, NewStatus = FinancialYearStatus.Inactive, ActivatedYearId = id });
            }
        }

        var beforeState = new { entity.StatusCode, entity.IsActive, entity.ReviewedBy, entity.ReviewedAt };

        entity.StatusCode = FinancialYearStatus.Active;
        entity.IsActive = true;
        entity.IsClosed = false;
        entity.ReviewedBy = userId;
        entity.ReviewedAt = DateTime.UtcNow;
        entity.ReviewNotes = reviewNotes?.Trim();
        entity.ModifiedBy = userId;
        entity.ModifiedAt = DateTime.UtcNow;

        _auditService.LogAction(
            db,
            nameof(FinancialYear),
            entity.Id,
            "ApproveFinancialYearActivation",
            userId,
            beforeState,
            new { entity.StatusCode, entity.IsActive, entity.ReviewedBy, entity.ReviewedAt, entity.ReviewNotes });

        await db.SaveChangesAsync(cancellationToken);
        _logger.LogInformation("Financial year '{FinYearCode}' (ID: {Id}) formally approved and activated by user {UserId}.", entity.FinYearCode, entity.Id, userId);
        return entity;
    }

    public async Task<FinancialYear> RejectFiscalYearAsync(int id, string rejectionReason, string userId, IEnumerable<string> userRoles, CancellationToken cancellationToken = default)
    {
        if (string.IsNullOrWhiteSpace(rejectionReason))
        {
            throw new ArgumentException("A reason is mandatory when returning a financial year submission.", nameof(rejectionReason));
        }

        using var db = await _contextFactory.CreateDbContextAsync(cancellationToken);
        var entity = await db.FinancialYears
            .Include(f => f.Quarters)
            .FirstOrDefaultAsync(f => f.Id == id, cancellationToken);

        if (entity == null)
        {
            throw new KeyNotFoundException($"Financial year with ID {id} was not found.");
        }

        if (entity.StatusCode != FinancialYearStatus.UnderReview)
        {
            throw new InvalidOperationException($"Financial year '{entity.FinYearCode}' cannot be rejected from status '{entity.StatusCode}'. It must be in 'Under Review'.");
        }

        var beforeState = new { entity.StatusCode, entity.ReviewedBy, entity.ReviewedAt };

        entity.StatusCode = FinancialYearStatus.Draft;
        entity.ReviewedBy = userId;
        entity.ReviewedAt = DateTime.UtcNow;
        entity.ReviewNotes = rejectionReason.Trim();
        entity.ModifiedBy = userId;
        entity.ModifiedAt = DateTime.UtcNow;

        _auditService.LogAction(
            db,
            nameof(FinancialYear),
            entity.Id,
            "RejectFinancialYearSubmission",
            userId,
            beforeState,
            new { entity.StatusCode, entity.ReviewedBy, entity.ReviewedAt, entity.ReviewNotes });

        await db.SaveChangesAsync(cancellationToken);
        _logger.LogInformation("Financial year '{FinYearCode}' (ID: {Id}) rejected by user {UserId}. Returned to Draft.", entity.FinYearCode, entity.Id, userId);
        return entity;
    }

    public async Task<FinancialYear> RequestAmendmentAsync(int id, string amendmentReason, string userId, CancellationToken cancellationToken = default)
    {
        if (string.IsNullOrWhiteSpace(amendmentReason))
        {
            throw new ArgumentException("An amendment justification reason is mandatory.", nameof(amendmentReason));
        }

        using var db = await _contextFactory.CreateDbContextAsync(cancellationToken);
        var entity = await db.FinancialYears
            .Include(f => f.Quarters)
            .FirstOrDefaultAsync(f => f.Id == id, cancellationToken);

        if (entity == null)
        {
            throw new KeyNotFoundException($"Financial year with ID {id} was not found.");
        }

        if (entity.StatusCode != FinancialYearStatus.Active)
        {
            throw new InvalidOperationException($"Only active financial scheme years can be placed under amendment. Current status is '{entity.StatusCode}'.");
        }

        var beforeState = new { entity.StatusCode, entity.RevisionNumber, entity.AmendmentReason };

        entity.RevisionNumber += 1;
        entity.StatusCode = FinancialYearStatus.AmendmentDraft;
        entity.AmendmentReason = amendmentReason.Trim();
        entity.ModifiedBy = userId;
        entity.ModifiedAt = DateTime.UtcNow;

        _auditService.LogAction(
            db,
            nameof(FinancialYear),
            entity.Id,
            "RequestFinancialYearAmendment",
            userId,
            beforeState,
            new { entity.StatusCode, entity.RevisionNumber, entity.AmendmentReason });

        await db.SaveChangesAsync(cancellationToken);
        _logger.LogInformation("Financial year '{FinYearCode}' (ID: {Id}) transitioned to Amendment Draft (Rev {Rev}) by user {UserId}.",
            entity.FinYearCode, entity.Id, entity.RevisionNumber, userId);
        return entity;
    }

    public async Task<FinancialYear> DeactivateFiscalYearAsync(int id, string userId, CancellationToken cancellationToken = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync(cancellationToken);
        var entity = await db.FinancialYears
            .Include(f => f.Quarters)
            .FirstOrDefaultAsync(f => f.Id == id, cancellationToken);

        if (entity == null)
        {
            throw new KeyNotFoundException($"Financial year with ID {id} was not found.");
        }

        var beforeState = new { entity.StatusCode, entity.IsActive, entity.IsClosed };

        entity.StatusCode = FinancialYearStatus.Inactive;
        entity.IsActive = false;
        entity.IsClosed = true;
        entity.ModifiedBy = userId;
        entity.ModifiedAt = DateTime.UtcNow;

        _auditService.LogAction(
            db,
            nameof(FinancialYear),
            entity.Id,
            "DeactivateFinancialYear",
            userId,
            beforeState,
            new { entity.StatusCode, entity.IsActive, entity.IsClosed });

        await db.SaveChangesAsync(cancellationToken);
        _logger.LogInformation("Financial year '{FinYearCode}' (ID: {Id}) deactivated and closed by user {UserId}.", entity.FinYearCode, entity.Id, userId);
        return entity;
    }
}
