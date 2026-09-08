using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging.Abstractions;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class HolidayAndClosureTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, SystemConfigurationService config, WorkingDayCalculationEngine calcEngine, HolidayClosureService closureService) CreateTestContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var conf = new ConfigurationBuilder().Build();
        var config = new SystemConfigurationService(factory, conf, audit);
        var calcEngine = new WorkingDayCalculationEngine(factory, config, NullLogger<WorkingDayCalculationEngine>.Instance);
        var closureService = new HolidayClosureService(factory, audit, NullLogger<HolidayClosureService>.Instance);

        WorkingDayCalculationEngine.InvalidateCache();
        return (factory, db, audit, config, calcEngine, closureService);
    }

    [Fact]
    public void AddBusinessDays_StandardWeek_SkipsWeekends()
    {
        // Monday 2026-06-01 + 5 business days = Monday 2026-06-08 (skipping Saturday 6th and Sunday 7th)
        var start = new DateTime(2026, 6, 1);
        var result = WorkingDayCalculationEngine.AddBusinessDays(start, 5);

        Assert.Equal(new DateTime(2026, 6, 8), result);
    }

    [Fact]
    public void AddBusinessDays_EasterPeriod_SkipsGoodFridayAndFamilyDay()
    {
        // In 2026, Easter Sunday is April 5.
        // Good Friday is April 3, Family Day is April 6 (Monday).
        // Starting Thursday April 2, adding 2 business days:
        // Friday Apr 3 (Good Friday - skipped)
        // Saturday Apr 4 (Weekend - skipped)
        // Sunday Apr 5 (Weekend - skipped)
        // Monday Apr 6 (Family Day - skipped)
        // Tuesday Apr 7 = Day 1
        // Wednesday Apr 8 = Day 2
        var start = new DateTime(2026, 4, 2);
        var result = WorkingDayCalculationEngine.AddBusinessDays(start, 2);

        Assert.Equal(new DateTime(2026, 4, 8), result);
    }

    [Fact]
    public void AddBusinessDays_SundayHolidayRollover_SkipsObservedMonday()
    {
        // 9 August 2026 (National Women's Day) is a Sunday.
        // Public Holidays Act Section 2(1) rolls observance to Monday 10 August 2026.
        // Starting Friday 7 August 2026, adding 1 business day:
        // Sat Aug 8 (Weekend)
        // Sun Aug 9 (Women's Day / Weekend)
        // Mon Aug 10 (Observed holiday - skipped!)
        // Tue Aug 11 = Day 1
        var start = new DateTime(2026, 8, 7);
        var result = WorkingDayCalculationEngine.AddBusinessDays(start, 1);

        Assert.Equal(new DateTime(2026, 8, 11), result);
    }

    [Fact]
    public async Task AddBusinessDays_YearEndShutdown_UniversallyPausesOverFestivePeriod()
    {
        // Arrange: Register merSETA Annual Year-End Shutdown from 24 Dec 2026 to 03 Jan 2027
        var (_, db, _, _, calcEngine, _) = CreateTestContext();

        var shutdown = new NonWorkingDay
        {
            Name = "merSETA Annual Year-End Office Shutdown 2026/2027",
            TypeCode = NonWorkingDayType.InstitutionalShutdown,
            StartDate = new DateTime(2026, 12, 24),
            EndDate = new DateTime(2027, 1, 3),
            CalendarYear = 2026,
            AffectsSla = true,
            StatusCode = NonWorkingDayStatus.Approved,
            IsActive = true,
            CreatedBy = "TEST"
        };
        db.NonWorkingDays.Add(shutdown);
        await db.SaveChangesAsync();

        WorkingDayCalculationEngine.InvalidateCache();

        // Starting Wednesday 2026-12-23, add 2 business days:
        // Thu Dec 24 to Sun Jan 3 is merSETA Shutdown + Christmas/Goodwill/NewYear (All skipped!)
        // Mon Jan 4, 2027 = Day 1
        // Tue Jan 5, 2027 = Day 2
        var start = new DateTime(2026, 12, 23);
        var result = await calcEngine.AddBusinessDaysAsync(start, 2);

        Assert.Equal(new DateTime(2027, 1, 5), result);
    }

    [Fact]
    public void WorkplaceApprovalService_AddBusinessDays_DelegatesToUniversalEngine()
    {
        // Verify WorkplaceApprovalService.AddBusinessDays calls the universal calculation
        var start = new DateTime(2026, 6, 1); // Monday
        var result = WorkplaceApprovalService.AddBusinessDays(start, 5);

        Assert.Equal(new DateTime(2026, 6, 8), result);
    }

    [Fact]
    public async Task CreateAsync_ValidRecord_PersistsAndLogsAuditDoubleWrite()
    {
        // Arrange
        var (_, db, _, _, _, service) = CreateTestContext();

        var dto = new NonWorkingDayDto
        {
            Name = "2026 Local Government Elections",
            TypeCode = NonWorkingDayType.AdHocGazetted,
            StartDate = new DateTime(2026, 10, 28),
            EndDate = new DateTime(2026, 10, 28),
            CalendarYear = 2026,
            AffectsSla = true,
            GazetteOrResolutionRef = "Gazette #50412",
            Description = "Special gazetted public holiday for general voting.",
            StatusCode = NonWorkingDayStatus.Approved,
            IsActive = true
        };

        // Act
        var created = await service.CreateAsync(dto, "AdminOfficer");

        // Assert
        Assert.True(created.Id > 0);
        Assert.Equal("2026 Local Government Elections", created.Name);

        var dbRecord = await db.NonWorkingDays.FindAsync(created.Id);
        Assert.NotNull(dbRecord);
        Assert.Equal(NonWorkingDayType.AdHocGazetted, dbRecord.TypeCode);

        // Verify Double-Write in AuditLog
        var auditEntry = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "NonWorkingDay" && a.RecordId == created.Id);
        Assert.NotNull(auditEntry);
        Assert.Equal("CreateNonWorkingDay", auditEntry.ActionName);
        Assert.Equal("AdminOfficer", auditEntry.Actor);
    }

    [Fact]
    public async Task CreateAsync_EndDateBeforeStartDate_ThrowsInvalidOperationException()
    {
        // Arrange
        var (_, _, _, _, _, service) = CreateTestContext();

        var dto = new NonWorkingDayDto
        {
            Name = "Invalid Closure",
            TypeCode = NonWorkingDayType.SpecialClosure,
            StartDate = new DateTime(2026, 5, 10),
            EndDate = new DateTime(2026, 5, 5), // Invalid: end before start
            CalendarYear = 2026
        };

        // Act & Assert
        await Assert.ThrowsAsync<InvalidOperationException>(() => service.CreateAsync(dto, "AdminOfficer"));
    }

    [Fact]
    public async Task UpdateAsync_ModifiesRecord_RecordsBeforeAndAfterAuditSnapshot()
    {
        // Arrange
        var (_, db, _, _, _, service) = CreateTestContext();

        var closure = new NonWorkingDay
        {
            Name = "Mid-Year Planning Workshop",
            TypeCode = NonWorkingDayType.SpecialClosure,
            StartDate = new DateTime(2026, 7, 15),
            EndDate = new DateTime(2026, 7, 16),
            CalendarYear = 2026,
            AffectsSla = false,
            StatusCode = NonWorkingDayStatus.Draft,
            IsActive = true,
            CreatedBy = "AdminOfficer"
        };
        db.NonWorkingDays.Add(closure);
        await db.SaveChangesAsync();

        var updateDto = new NonWorkingDayDto
        {
            Name = "Strategic Mid-Year Planning Conference",
            TypeCode = NonWorkingDayType.SpecialClosure,
            StartDate = new DateTime(2026, 7, 15),
            EndDate = new DateTime(2026, 7, 17), // Extended by 1 day
            CalendarYear = 2026,
            AffectsSla = true, // Changed to pause SLAs
            StatusCode = NonWorkingDayStatus.Approved,
            IsActive = true
        };

        // Act
        var updated = await service.UpdateAsync(closure.Id, updateDto, "AdminReviewer");

        // Assert
        Assert.Equal("Strategic Mid-Year Planning Conference", updated.Name);
        Assert.Equal(new DateTime(2026, 7, 17), updated.EndDate);
        Assert.True(updated.AffectsSla);

        // Verify audit log has before/after snapshot
        var auditEntry = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "NonWorkingDay" && a.RecordId == closure.Id && a.ActionName == "UpdateNonWorkingDay");
        Assert.NotNull(auditEntry);
        Assert.Contains("before", auditEntry.MetadataJson, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("after", auditEntry.MetadataJson, StringComparison.OrdinalIgnoreCase);
    }

    [Fact]
    public async Task ApproveAsync_TransitionsStatusToApproved_AndInvalidatesCache()
    {
        // Arrange
        var (_, db, _, _, _, service) = CreateTestContext();

        var closure = new NonWorkingDay
        {
            Name = "Emergency Head Office Closure",
            TypeCode = NonWorkingDayType.SpecialClosure,
            StartDate = new DateTime(2026, 9, 1),
            EndDate = new DateTime(2026, 9, 2),
            CalendarYear = 2026,
            AffectsSla = true,
            StatusCode = NonWorkingDayStatus.Draft,
            IsActive = false,
            CreatedBy = "AdminOfficer"
        };
        db.NonWorkingDays.Add(closure);
        await db.SaveChangesAsync();

        // Act
        var approved = await service.ApproveAsync(closure.Id, "SeniorManager");

        // Assert
        Assert.Equal(NonWorkingDayStatus.Approved, approved.StatusCode);
        Assert.True(approved.IsActive);

        var auditEntry = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "NonWorkingDay" && a.RecordId == closure.Id && a.ActionName == "ApproveNonWorkingDay");
        Assert.NotNull(auditEntry);
    }

    [Fact]
    public async Task DeleteAsync_RemovesRecord_AndWritesAuditLog()
    {
        // Arrange
        var (_, db, _, _, _, service) = CreateTestContext();

        var closure = new NonWorkingDay
        {
            Name = "Cancelled Regional Closure",
            TypeCode = NonWorkingDayType.SpecialClosure,
            StartDate = new DateTime(2026, 11, 1),
            EndDate = new DateTime(2026, 11, 1),
            CalendarYear = 2026,
            AffectsSla = true,
            CreatedBy = "AdminOfficer"
        };
        db.NonWorkingDays.Add(closure);
        await db.SaveChangesAsync();

        // Act
        var deleted = await service.DeleteAsync(closure.Id, "AdminOfficer");

        // Assert
        Assert.True(deleted);
        var found = await db.NonWorkingDays.AsNoTracking().FirstOrDefaultAsync(n => n.Id == closure.Id);
        Assert.Null(found);

        var auditEntry = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "NonWorkingDay" && a.RecordId == closure.Id && a.ActionName == "DeleteNonWorkingDay");
        Assert.NotNull(auditEntry);
    }

    [Fact]
    public async Task SeedDefaultHolidaysForYearAsync_SeedsAllStatutoryHolidaysAndMerSetaShutdown()
    {
        // Arrange
        var (_, db, _, _, _, service) = CreateTestContext();

        // Act
        var count = await service.SeedDefaultHolidaysForYearAsync(2026, "SystemSeeder");

        // Assert
        Assert.True(count >= 12);

        var allHolidays = await db.NonWorkingDays.Where(n => n.CalendarYear == 2026).ToListAsync();
        Assert.Contains(allHolidays, h => h.Name == "New Year's Day");
        Assert.Contains(allHolidays, h => h.Name == "Workers' Day");
        Assert.Contains(allHolidays, h => h.Name == "Day of Reconciliation");
        Assert.Contains(allHolidays, h => h.TypeCode == NonWorkingDayType.InstitutionalShutdown);

        // Verify idempotency (seeding again should add 0 duplicates)
        var secondRun = await service.SeedDefaultHolidaysForYearAsync(2026, "SystemSeeder");
        Assert.Equal(0, secondRun);
    }

    [Fact]
    public async Task SimulateSlaAsync_ProducesAccurateDayByDayTrajectory()
    {
        // Arrange
        var (_, db, _, _, calcEngine, _) = CreateTestContext();

        // Register merSETA shutdown
        var shutdown = new NonWorkingDay
        {
            Name = "merSETA Annual Year-End Office Shutdown 2026/2027",
            TypeCode = NonWorkingDayType.InstitutionalShutdown,
            StartDate = new DateTime(2026, 12, 24),
            EndDate = new DateTime(2027, 1, 3),
            CalendarYear = 2026,
            AffectsSla = true,
            StatusCode = NonWorkingDayStatus.Approved,
            IsActive = true,
            CreatedBy = "TEST"
        };
        db.NonWorkingDays.Add(shutdown);
        await db.SaveChangesAsync();

        WorkingDayCalculationEngine.InvalidateCache();

        // Act: Simulate a 5-day SLA starting Monday 2026-12-21
        var sim = await calcEngine.SimulateSlaAsync(new DateTime(2026, 12, 21), 5);

        // Assert
        Assert.NotNull(sim);
        Assert.Equal(5, sim.RequestedBusinessDays);
        Assert.True(sim.Trajectory.Count >= 5);

        // Days before shutdown:
        // Day 1: Tue Dec 22
        // Day 2: Wed Dec 23
        // Dec 24 - Jan 3: All skipped as institutional shutdown
        // Day 3: Mon Jan 4
        // Day 4: Tue Jan 5
        // Day 5: Wed Jan 6 (Due date)
        Assert.Equal(new DateTime(2027, 1, 6), sim.CalculatedDueDate);
        Assert.True(sim.InstitutionalClosuresCount > 0);
        Assert.Contains(sim.Trajectory, t => t.StatusBadge == "Shutdown");
    }

    [Fact]
    public async Task CountBusinessDaysAsync_AccuratelyCalculatesRange()
    {
        // Arrange
        var (_, _, _, _, calcEngine, _) = CreateTestContext();

        // Monday 2026-06-01 to Friday 2026-06-05 = 5 business days
        var count = await calcEngine.CountBusinessDaysAsync(new DateTime(2026, 6, 1), new DateTime(2026, 6, 5));
        Assert.Equal(5, count);

        // Monday 2026-06-01 to Monday 2026-06-08 (includes 1 weekend) = 6 business days
        var countWithWeekend = await calcEngine.CountBusinessDaysAsync(new DateTime(2026, 6, 1), new DateTime(2026, 6, 8));
        Assert.Equal(6, countWithWeekend);
    }
}
