using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;
using System.Text.Json;

namespace Nsdms.Application.Services;

/// <summary>
/// Application service for administrative management of statutory public holidays,
/// merSETA annual shutdowns, and ad-hoc institutional closures.
/// </summary>
public class HolidayClosureService : IHolidayClosureService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;
    private readonly ILogger<HolidayClosureService> _logger;

    public HolidayClosureService(
        INsdmsDbContextFactory contextFactory,
        IAuditService audit,
        ILogger<HolidayClosureService> logger)
    {
        _contextFactory = contextFactory;
        _audit = audit;
        _logger = logger;
    }

    public async Task<List<NonWorkingDay>> GetAllAsync(
        int? year = null,
        string? typeCode = null,
        bool activeOnly = false,
        CancellationToken cancellationToken = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.NonWorkingDays.AsNoTracking().AsQueryable();

        if (year.HasValue)
        {
            query = query.Where(n => n.CalendarYear == year.Value);
        }

        if (!string.IsNullOrWhiteSpace(typeCode))
        {
            query = query.Where(n => n.TypeCode == typeCode);
        }

        if (activeOnly)
        {
            query = query.Where(n => n.IsActive);
        }

        return await query
            .OrderBy(n => n.StartDate)
            .ThenBy(n => n.Name)
            .ToListAsync(cancellationToken);
    }

    public async Task<NonWorkingDay?> GetByIdAsync(int id, CancellationToken cancellationToken = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.NonWorkingDays.FirstOrDefaultAsync(n => n.Id == id, cancellationToken);
    }

    public async Task<NonWorkingDay> CreateAsync(NonWorkingDayDto dto, string currentUsername, CancellationToken cancellationToken = default)
    {
        if (dto.EndDate.Date < dto.StartDate.Date)
        {
            throw new InvalidOperationException("Closure end date cannot be earlier than start date.");
        }

        using var db = await _contextFactory.CreateDbContextAsync();

        var entity = new NonWorkingDay
        {
            Name = dto.Name.Trim(),
            TypeCode = dto.TypeCode,
            StartDate = dto.StartDate.Date,
            EndDate = dto.EndDate.Date,
            CalendarYear = dto.CalendarYear > 0 ? dto.CalendarYear : dto.StartDate.Year,
            AffectsSla = dto.AffectsSla,
            IsRecurringAnnually = dto.IsRecurringAnnually,
            GazetteOrResolutionRef = dto.GazetteOrResolutionRef?.Trim(),
            Description = dto.Description?.Trim(),
            StatusCode = dto.StatusCode,
            IsActive = dto.IsActive,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        db.NonWorkingDays.Add(entity);
        await db.SaveChangesAsync(cancellationToken);

        // Double-write audit log
        await _audit.LogActionAsync(
            "NonWorkingDay",
            entity.Id,
            "CreateNonWorkingDay",
            currentUsername,
            null,
            new
            {
                entity.Id,
                entity.Name,
                entity.TypeCode,
                entity.StartDate,
                entity.EndDate,
                entity.CalendarYear,
                entity.AffectsSla,
                entity.StatusCode
            });

        WorkingDayCalculationEngine.InvalidateCache();
        _logger.LogInformation("Created non-working day record {Id} ({Name}) by {User}", entity.Id, entity.Name, currentUsername);
        return entity;
    }

    public async Task<NonWorkingDay> UpdateAsync(int id, NonWorkingDayDto dto, string currentUsername, CancellationToken cancellationToken = default)
    {
        if (dto.EndDate.Date < dto.StartDate.Date)
        {
            throw new InvalidOperationException("Closure end date cannot be earlier than start date.");
        }

        using var db = await _contextFactory.CreateDbContextAsync();
        var entity = await db.NonWorkingDays.FirstOrDefaultAsync(n => n.Id == id, cancellationToken);
        if (entity == null)
        {
            throw new InvalidOperationException($"Non-working day record with ID {id} not found.");
        }

        var beforeSnapshot = new
        {
            entity.Id,
            entity.Name,
            entity.TypeCode,
            entity.StartDate,
            entity.EndDate,
            entity.CalendarYear,
            entity.AffectsSla,
            entity.StatusCode,
            entity.IsActive
        };

        entity.Name = dto.Name.Trim();
        entity.TypeCode = dto.TypeCode;
        entity.StartDate = dto.StartDate.Date;
        entity.EndDate = dto.EndDate.Date;
        entity.CalendarYear = dto.CalendarYear > 0 ? dto.CalendarYear : dto.StartDate.Year;
        entity.AffectsSla = dto.AffectsSla;
        entity.IsRecurringAnnually = dto.IsRecurringAnnually;
        entity.GazetteOrResolutionRef = dto.GazetteOrResolutionRef?.Trim();
        entity.Description = dto.Description?.Trim();
        entity.StatusCode = dto.StatusCode;
        entity.IsActive = dto.IsActive;
        entity.ModifiedAt = DateTime.UtcNow;
        entity.ModifiedBy = currentUsername;

        await db.SaveChangesAsync(cancellationToken);

        var afterSnapshot = new
        {
            entity.Id,
            entity.Name,
            entity.TypeCode,
            entity.StartDate,
            entity.EndDate,
            entity.CalendarYear,
            entity.AffectsSla,
            entity.StatusCode,
            entity.IsActive
        };

        // Double-write audit log
        await _audit.LogActionAsync(
            "NonWorkingDay",
            entity.Id,
            "UpdateNonWorkingDay",
            currentUsername,
            beforeSnapshot,
            afterSnapshot);

        WorkingDayCalculationEngine.InvalidateCache();
        _logger.LogInformation("Updated non-working day record {Id} ({Name}) by {User}", entity.Id, entity.Name, currentUsername);
        return entity;
    }

    public async Task<bool> DeleteAsync(int id, string currentUsername, CancellationToken cancellationToken = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var entity = await db.NonWorkingDays.FirstOrDefaultAsync(n => n.Id == id, cancellationToken);
        if (entity == null)
        {
            return false;
        }

        db.NonWorkingDays.Remove(entity);
        await db.SaveChangesAsync(cancellationToken);

        // Double-write audit log
        await _audit.LogActionAsync(
            "NonWorkingDay",
            id,
            "DeleteNonWorkingDay",
            currentUsername,
            new { entity.Id, entity.Name, entity.TypeCode, entity.StartDate, entity.EndDate },
            null);

        WorkingDayCalculationEngine.InvalidateCache();
        _logger.LogInformation("Deleted non-working day record {Id} ({Name}) by {User}", id, entity.Name, currentUsername);
        return true;
    }

    public async Task<NonWorkingDay> ApproveAsync(int id, string currentUsername, CancellationToken cancellationToken = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var entity = await db.NonWorkingDays.FirstOrDefaultAsync(n => n.Id == id, cancellationToken);
        if (entity == null)
        {
            throw new InvalidOperationException($"Non-working day record with ID {id} not found.");
        }

        var beforeState = new { entity.Id, entity.Name, Status = entity.StatusCode };

        entity.StatusCode = NonWorkingDayStatus.Approved;
        entity.IsActive = true;
        entity.ModifiedAt = DateTime.UtcNow;
        entity.ModifiedBy = currentUsername;

        await db.SaveChangesAsync(cancellationToken);

        // Double-write audit log
        await _audit.LogActionAsync(
            "NonWorkingDay",
            id,
            "ApproveNonWorkingDay",
            currentUsername,
            beforeState,
            new { entity.Id, entity.Name, Status = "Approved" });

        WorkingDayCalculationEngine.InvalidateCache();
        _logger.LogInformation("Approved non-working day record {Id} ({Name}) by {User}", id, entity.Name, currentUsername);
        return entity;
    }

    public async Task<int> SeedDefaultHolidaysForYearAsync(int year, string currentUsername, CancellationToken cancellationToken = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var existingHolidays = await db.NonWorkingDays
            .Where(n => n.CalendarYear == year)
            .Select(n => n.Name)
            .ToListAsync(cancellationToken);

        var existingSet = new HashSet<string>(existingHolidays, StringComparer.OrdinalIgnoreCase);
        var toAdd = new List<NonWorkingDay>();

        void AddIfMissing(string name, string typeCode, DateTime start, DateTime end, bool affectsSla, bool recurring, string gazette, string description)
        {
            if (!existingSet.Contains(name))
            {
                toAdd.Add(new NonWorkingDay
                {
                    Name = name,
                    TypeCode = typeCode,
                    StartDate = start.Date,
                    EndDate = end.Date,
                    CalendarYear = year,
                    AffectsSla = affectsSla,
                    IsRecurringAnnually = recurring,
                    GazetteOrResolutionRef = gazette,
                    Description = description,
                    StatusCode = NonWorkingDayStatus.Approved,
                    IsActive = true,
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = currentUsername
                });
            }
        }

        // 1. Fixed National Statutory Holidays
        AddIfMissing("New Year's Day", NonWorkingDayType.NationalStatutory, new DateTime(year, 1, 1), new DateTime(year, 1, 1), true, true, "Act 36 of 1994", "Statutory public holiday observed nationwide in South Africa");
        AddIfMissing("Human Rights Day", NonWorkingDayType.NationalStatutory, new DateTime(year, 3, 21), new DateTime(year, 3, 21), true, true, "Act 36 of 1994", "Statutory public holiday observed nationwide in South Africa");
        AddIfMissing("Freedom Day", NonWorkingDayType.NationalStatutory, new DateTime(year, 4, 27), new DateTime(year, 4, 27), true, true, "Act 36 of 1994", "Statutory public holiday observed nationwide in South Africa");
        AddIfMissing("Workers' Day", NonWorkingDayType.NationalStatutory, new DateTime(year, 5, 1), new DateTime(year, 5, 1), true, true, "Act 36 of 1994", "Statutory public holiday observed nationwide in South Africa");
        AddIfMissing("Youth Day", NonWorkingDayType.NationalStatutory, new DateTime(year, 6, 16), new DateTime(year, 6, 16), true, true, "Act 36 of 1994", "Statutory public holiday observed nationwide in South Africa");
        AddIfMissing("National Women's Day", NonWorkingDayType.NationalStatutory, new DateTime(year, 8, 9), new DateTime(year, 8, 9), true, true, "Act 36 of 1994", "Statutory public holiday observed nationwide in South Africa");
        AddIfMissing("Heritage Day", NonWorkingDayType.NationalStatutory, new DateTime(year, 9, 24), new DateTime(year, 9, 24), true, true, "Act 36 of 1994", "Statutory public holiday observed nationwide in South Africa");
        AddIfMissing("Day of Reconciliation", NonWorkingDayType.NationalStatutory, new DateTime(year, 12, 16), new DateTime(year, 12, 16), true, true, "Act 36 of 1994", "Statutory public holiday observed nationwide in South Africa");
        AddIfMissing("Christmas Day", NonWorkingDayType.NationalStatutory, new DateTime(year, 12, 25), new DateTime(year, 12, 25), true, true, "Act 36 of 1994", "Statutory public holiday observed nationwide in South Africa");
        AddIfMissing("Day of Goodwill", NonWorkingDayType.NationalStatutory, new DateTime(year, 12, 26), new DateTime(year, 12, 26), true, true, "Act 36 of 1994", "Statutory public holiday observed nationwide in South Africa");

        // Sunday rollover checks for fixed holidays
        DateTime[] fixedDates = {
            new(year, 1, 1), new(year, 3, 21), new(year, 4, 27), new(year, 5, 1),
            new(year, 6, 16), new(year, 8, 9), new(year, 9, 24), new(year, 12, 16),
            new(year, 12, 25), new(year, 12, 26)
        };

        foreach (var d in fixedDates)
        {
            if (d.DayOfWeek == DayOfWeek.Sunday)
            {
                var rollover = d.AddDays(1);
                var rolloverName = $"Public Holiday Observed ({d:dd MMMM})";
                AddIfMissing(rolloverName, NonWorkingDayType.NationalStatutory, rollover, rollover, true, false, "Act 36 of 1994 Sec 2(1)", "Section 2(1) Sunday public holiday rollover observed on Monday");
            }
        }

        // Easter Holidays (Computus)
        var easter = GetEasterSunday(year);
        var goodFriday = easter.AddDays(-2);
        var familyDay = easter.AddDays(1);
        AddIfMissing("Good Friday", NonWorkingDayType.NationalStatutory, goodFriday, goodFriday, true, false, "Act 36 of 1994", "Statutory public holiday observed nationwide in South Africa (Computus)");
        AddIfMissing("Family Day", NonWorkingDayType.NationalStatutory, familyDay, familyDay, true, false, "Act 36 of 1994", "Statutory public holiday observed nationwide in South Africa (Easter Monday)");

        // 2. Official merSETA Annual Year-End Institutional Shutdown
        var shutdownStart = new DateTime(year, 12, 24);
        var shutdownEnd = new DateTime(year + 1, 1, 3);
        var shutdownTitle = $"merSETA Annual Year-End Office Shutdown {year}/{year + 1}";
        AddIfMissing(shutdownTitle, NonWorkingDayType.InstitutionalShutdown, shutdownStart, shutdownEnd, true, true, $"merSETA Circular {year}-12", "Annual institutional closure of all merSETA regional operations and head office. Universal workflow SLA countdowns pause throughout this closure period.");

        if (toAdd.Count > 0)
        {
            db.NonWorkingDays.AddRange(toAdd);
            await db.SaveChangesAsync(cancellationToken);

            await _audit.LogActionAsync(
                "NonWorkingDay",
                0,
                "SeedDefaultHolidays",
                currentUsername,
                null,
                new { Year = year, AddedCount = toAdd.Count });

            WorkingDayCalculationEngine.InvalidateCache();
        }

        return toAdd.Count;
    }

    private static DateTime GetEasterSunday(int year)
    {
        int a = year % 19;
        int b = year / 100;
        int c = year % 100;
        int d = b / 4;
        int e = b % 4;
        int f = (b + 8) / 25;
        int g = (b - f + 1) / 3;
        int h = (19 * a + b - d - g + 15) % 30;
        int i = c / 4;
        int k = c % 4;
        int l = (32 + 2 * e + 2 * i - h - k) % 7;
        int m = (a + 11 * h + 22 * l) / 451;
        int month = (h + l - 7 * m + 114) / 31;
        int day = ((h + l - 7 * m + 114) % 31) + 1;
        return new DateTime(year, month, day);
    }
}
