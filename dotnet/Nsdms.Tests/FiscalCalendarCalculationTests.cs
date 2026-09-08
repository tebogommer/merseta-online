using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging.Abstractions;
using Nsdms.Application.Common.Models;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class FiscalCalendarCalculationTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, SystemConfigurationService config, FiscalCalendarService service) CreateTestContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var conf = new ConfigurationBuilder().Build();
        var config = new SystemConfigurationService(factory, conf, audit);
        var service = new FiscalCalendarService(factory, audit, config, NullLogger<FiscalCalendarService>.Instance);
        return (factory, db, audit, config, service);
    }

    [Fact]
    public void ComputeYearBreakdown_StandardStatutoryYear_CalculatesAccurateCalendarAndWorkingDays()
    {
        // Arrange
        var (_, _, _, _, service) = CreateTestContext();

        var quarters = new List<QuarterDateRangeDto>
        {
            new() { QuarterCode = "Q1", QuarterNumber = 1, StartDate = new DateTime(2026, 4, 1), EndDate = new DateTime(2026, 6, 30) },
            new() { QuarterCode = "Q2", QuarterNumber = 2, StartDate = new DateTime(2026, 7, 1), EndDate = new DateTime(2026, 9, 30) },
            new() { QuarterCode = "Q3", QuarterNumber = 3, StartDate = new DateTime(2026, 10, 1), EndDate = new DateTime(2026, 12, 31) },
            new() { QuarterCode = "Q4", QuarterNumber = 4, StartDate = new DateTime(2027, 1, 1), EndDate = new DateTime(2027, 3, 31) }
        };

        // Act
        var result = service.ComputeYearBreakdown(
            "2026/2027",
            2026,
            2027,
            new DateTime(2026, 4, 1),
            new DateTime(2027, 3, 31),
            quarters);

        // Assert
        Assert.Equal("2026/2027", result.FinYearCode);
        Assert.Equal(365, result.TotalCalendarDays);
        Assert.Equal(4, result.Quarters.Count);

        // Check Q1 Breakdown
        var q1 = result.Quarters.First(q => q.QuarterCode == "Q1");
        Assert.Equal(91, q1.TotalCalendarDays); // 30 (Apr) + 31 (May) + 30 (Jun)
        Assert.Equal(3, q1.Months.Count);

        var apr = q1.Months.First(m => m.MonthName == "April");
        Assert.Equal(30, apr.CalendarDaysCount);
        Assert.Contains(apr.ObservedHolidays, h => h.Contains("Freedom Day"));

        var may = q1.Months.First(m => m.MonthName == "May");
        Assert.Equal(31, may.CalendarDaysCount);
        Assert.Contains(may.ObservedHolidays, h => h.Contains("Workers' Day"));

        var jun = q1.Months.First(m => m.MonthName == "June");
        Assert.Equal(30, jun.CalendarDaysCount);
        Assert.Contains(jun.ObservedHolidays, h => h.Contains("Youth Day"));

        // Working days must be strictly less than calendar days
        Assert.True(result.TotalWorkingDays < result.TotalCalendarDays);
        Assert.True(result.TotalWorkingDays > 240); // Standard SA working days around 248-251
        Assert.True(result.TotalPublicHolidays >= 10);
    }

    [Fact]
    public void ComputeYearBreakdown_LeapYear_AccuratelyIncludesFeb29()
    {
        // Arrange
        var (_, _, _, _, service) = CreateTestContext();

        // 2027/2028 has Leap Year in Feb 2028 (29 days)
        var quarters = new List<QuarterDateRangeDto>
        {
            new() { QuarterCode = "Q1", QuarterNumber = 1, StartDate = new DateTime(2027, 4, 1), EndDate = new DateTime(2027, 6, 30) },
            new() { QuarterCode = "Q2", QuarterNumber = 2, StartDate = new DateTime(2027, 7, 1), EndDate = new DateTime(2027, 9, 30) },
            new() { QuarterCode = "Q3", QuarterNumber = 3, StartDate = new DateTime(2027, 10, 1), EndDate = new DateTime(2027, 12, 31) },
            new() { QuarterCode = "Q4", QuarterNumber = 4, StartDate = new DateTime(2028, 1, 1), EndDate = new DateTime(2028, 3, 31) }
        };

        // Act
        var result = service.ComputeYearBreakdown(
            "2027/2028",
            2027,
            2028,
            new DateTime(2027, 4, 1),
            new DateTime(2028, 3, 31),
            quarters);

        // Assert
        Assert.Equal(366, result.TotalCalendarDays); // 366 days in leap year!

        var q4 = result.Quarters.First(q => q.QuarterCode == "Q4");
        Assert.Equal(91, q4.TotalCalendarDays); // 31 (Jan) + 29 (Feb) + 31 (Mar)

        var feb = q4.Months.First(m => m.MonthName == "February");
        Assert.Equal(29, feb.CalendarDaysCount);
    }

    [Fact]
    public void ValidateQuartersContiguity_ValidContiguousQuarters_ReturnsValid()
    {
        // Arrange
        var (_, _, _, _, service) = CreateTestContext();

        var quarters = new List<QuarterDateRangeDto>
        {
            new() { QuarterCode = "Q1", QuarterNumber = 1, StartDate = new DateTime(2026, 4, 1), EndDate = new DateTime(2026, 6, 30) },
            new() { QuarterCode = "Q2", QuarterNumber = 2, StartDate = new DateTime(2026, 7, 1), EndDate = new DateTime(2026, 9, 30) },
            new() { QuarterCode = "Q3", QuarterNumber = 3, StartDate = new DateTime(2026, 10, 1), EndDate = new DateTime(2026, 12, 31) },
            new() { QuarterCode = "Q4", QuarterNumber = 4, StartDate = new DateTime(2027, 1, 1), EndDate = new DateTime(2027, 3, 31) }
        };

        // Act
        var validation = service.ValidateQuartersContiguity(new DateTime(2026, 4, 1), new DateTime(2027, 3, 31), quarters);

        // Assert
        Assert.True(validation.IsValid);
        Assert.Empty(validation.Errors);
    }

    [Fact]
    public void ValidateQuartersContiguity_GapDetected_ReturnsError()
    {
        // Arrange
        var (_, _, _, _, service) = CreateTestContext();

        // Gap: Q1 ends 2026-06-30, but Q2 starts 2026-07-02 (2026-07-01 omitted)
        var quarters = new List<QuarterDateRangeDto>
        {
            new() { QuarterCode = "Q1", QuarterNumber = 1, StartDate = new DateTime(2026, 4, 1), EndDate = new DateTime(2026, 6, 30) },
            new() { QuarterCode = "Q2", QuarterNumber = 2, StartDate = new DateTime(2026, 7, 2), EndDate = new DateTime(2026, 9, 30) },
            new() { QuarterCode = "Q3", QuarterNumber = 3, StartDate = new DateTime(2026, 10, 1), EndDate = new DateTime(2026, 12, 31) },
            new() { QuarterCode = "Q4", QuarterNumber = 4, StartDate = new DateTime(2027, 1, 1), EndDate = new DateTime(2027, 3, 31) }
        };

        // Act
        var validation = service.ValidateQuartersContiguity(new DateTime(2026, 4, 1), new DateTime(2027, 3, 31), quarters);

        // Assert
        Assert.False(validation.IsValid);
        Assert.Contains(validation.Errors, e => e.Contains("Date gap detected between Quarter Q1 and Quarter Q2"));
    }

    [Fact]
    public void ValidateQuartersContiguity_OverlapDetected_ReturnsError()
    {
        // Arrange
        var (_, _, _, _, service) = CreateTestContext();

        // Overlap: Q1 ends 2026-06-30, but Q2 starts 2026-06-30
        var quarters = new List<QuarterDateRangeDto>
        {
            new() { QuarterCode = "Q1", QuarterNumber = 1, StartDate = new DateTime(2026, 4, 1), EndDate = new DateTime(2026, 6, 30) },
            new() { QuarterCode = "Q2", QuarterNumber = 2, StartDate = new DateTime(2026, 6, 30), EndDate = new DateTime(2026, 9, 30) },
            new() { QuarterCode = "Q3", QuarterNumber = 3, StartDate = new DateTime(2026, 10, 1), EndDate = new DateTime(2026, 12, 31) },
            new() { QuarterCode = "Q4", QuarterNumber = 4, StartDate = new DateTime(2027, 1, 1), EndDate = new DateTime(2027, 3, 31) }
        };

        // Act
        var validation = service.ValidateQuartersContiguity(new DateTime(2026, 4, 1), new DateTime(2027, 3, 31), quarters);

        // Assert
        Assert.False(validation.IsValid);
        Assert.Contains(validation.Errors, e => e.Contains("overlaps with Quarter Q2"));
    }

    [Fact]
    public void ValidateQuartersContiguity_YearBoundaryMismatch_ReturnsError()
    {
        // Arrange
        var (_, _, _, _, service) = CreateTestContext();

        // Q1 starts 2026-04-05, but year starts 2026-04-01
        var quarters = new List<QuarterDateRangeDto>
        {
            new() { QuarterCode = "Q1", QuarterNumber = 1, StartDate = new DateTime(2026, 4, 5), EndDate = new DateTime(2026, 6, 30) },
            new() { QuarterCode = "Q2", QuarterNumber = 2, StartDate = new DateTime(2026, 7, 1), EndDate = new DateTime(2026, 9, 30) },
            new() { QuarterCode = "Q3", QuarterNumber = 3, StartDate = new DateTime(2026, 10, 1), EndDate = new DateTime(2026, 12, 31) },
            new() { QuarterCode = "Q4", QuarterNumber = 4, StartDate = new DateTime(2027, 1, 1), EndDate = new DateTime(2027, 3, 31) }
        };

        // Act
        var validation = service.ValidateQuartersContiguity(new DateTime(2026, 4, 1), new DateTime(2027, 3, 31), quarters);

        // Assert
        Assert.False(validation.IsValid);
        Assert.Contains(validation.Errors, e => e.Contains("must match the financial year start date"));
    }

    [Fact]
    public void GenerateDefaultQuartersForYear_AppliesTemplateCorrectly()
    {
        // Arrange
        var (_, _, _, _, service) = CreateTestContext();
        var template = new FiscalTemplateConfigDto();

        // Act
        var quarters = service.GenerateDefaultQuartersForYear(2026, template);

        // Assert
        Assert.Equal(4, quarters.Count);
        Assert.Equal(new DateTime(2026, 4, 1), quarters[0].StartDate);
        Assert.Equal(new DateTime(2026, 6, 30), quarters[0].EndDate);
        Assert.Equal(new DateTime(2026, 7, 1), quarters[1].StartDate);
        Assert.Equal(new DateTime(2026, 9, 30), quarters[1].EndDate);
        Assert.Equal(new DateTime(2026, 10, 1), quarters[2].StartDate);
        Assert.Equal(new DateTime(2026, 12, 31), quarters[2].EndDate);
        Assert.Equal(new DateTime(2027, 1, 1), quarters[3].StartDate);
        Assert.Equal(new DateTime(2027, 3, 31), quarters[3].EndDate);
    }

    [Fact]
    public async Task CrudFinancialYear_FullLifecycleWithAuditLogs()
    {
        // Arrange
        var (_, db, _, _, service) = CreateTestContext();

        var createDto = new CreateFinancialYearDto
        {
            FinYearCode = "2029/2030",
            StartYear = 2029,
            EndYear = 2030,
            StartDate = new DateTime(2029, 4, 1),
            EndDate = new DateTime(2030, 3, 31),
            Description = "Future test fiscal year",
            StatusCode = "Upcoming",
            IsActive = true,
            Quarters = new List<QuarterDateRangeDto>
            {
                new() { QuarterCode = "Q1", QuarterNumber = 1, StartDate = new DateTime(2029, 4, 1), EndDate = new DateTime(2029, 6, 30) },
                new() { QuarterCode = "Q2", QuarterNumber = 2, StartDate = new DateTime(2029, 7, 1), EndDate = new DateTime(2029, 9, 30) },
                new() { QuarterCode = "Q3", QuarterNumber = 3, StartDate = new DateTime(2029, 10, 1), EndDate = new DateTime(2029, 12, 31) },
                new() { QuarterCode = "Q4", QuarterNumber = 4, StartDate = new DateTime(2030, 1, 1), EndDate = new DateTime(2030, 3, 31) }
            }
        };

        // Act 1: Create
        var created = await service.CreateFinancialYearAsync(createDto, "OfficerTest");
        Assert.True(created.Id > 0);
        Assert.Equal(4, created.Quarters.Count);

        var createAudit = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == nameof(FinancialYear) && a.RecordId == created.Id && a.ActionName == "CreateFinancialYear");
        Assert.NotNull(createAudit);

        // Act 2: Read
        var retrieved = await service.GetFinancialYearByIdAsync(created.Id);
        Assert.NotNull(retrieved);
        Assert.Equal("2029/2030", retrieved.FinYearCode);

        // Act 3: Update
        var updateDto = new UpdateFinancialYearDto
        {
            Id = created.Id,
            FinYearCode = "2029/2030",
            StartYear = 2029,
            EndYear = 2030,
            StartDate = new DateTime(2029, 4, 1),
            EndDate = new DateTime(2030, 3, 31),
            Description = "Updated description for 2029/2030",
            StatusCode = "Active",
            IsActive = true,
            IsClosed = false,
            Quarters = created.Quarters.Select(q => new QuarterDateRangeDto
            {
                Id = q.Id,
                QuarterCode = q.QuarterCode,
                QuarterNumber = q.QuarterNumber,
                StartDate = q.StartDate,
                EndDate = q.EndDate,
                Description = "Updated Quarter Note"
            }).ToList()
        };

        var updated = await service.UpdateFinancialYearAsync(updateDto, "OfficerUpdater");
        Assert.Equal("Active", updated.StatusCode);
        Assert.Equal("Updated description for 2029/2030", updated.Description);

        var updateAudit = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == nameof(FinancialYear) && a.RecordId == created.Id && a.ActionName == "UpdateFinancialYear");
        Assert.NotNull(updateAudit);

        // Act 4: Delete
        var deleted = await service.DeleteFinancialYearAsync(created.Id, "OfficerDelete");
        Assert.True(deleted);

        var deleteAudit = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == nameof(FinancialYear) && a.RecordId == created.Id && a.ActionName == "DeleteFinancialYear");
        Assert.NotNull(deleteAudit);

        var recheck = await service.GetFinancialYearByIdAsync(created.Id);
        Assert.Null(recheck);
    }

    [Fact]
    public async Task SubmitForReviewAsync_DraftYear_TransitionsToUnderReviewWithAuditAndSubmitterMetadata()
    {
        // Arrange
        var (_, db, _, _, service) = CreateTestContext();
        var template = await service.GetDefaultTemplateAsync();
        var quarters = service.GenerateDefaultQuartersForYear(2031, template);

        var created = await service.CreateFinancialYearAsync(new CreateFinancialYearDto
        {
            FinYearCode = "2031/2032",
            StartYear = 2031,
            EndYear = 2032,
            StartDate = new DateTime(2031, 4, 1),
            EndDate = new DateTime(2032, 3, 31),
            Description = "Initial Draft",
            Quarters = quarters
        }, "OfficerMaker");

        Assert.Equal(FinancialYearStatus.Draft, created.StatusCode);
        Assert.False(created.IsActive);

        // Act
        var submitted = await service.SubmitForReviewAsync(created.Id, "Ready for committee review", "OfficerMaker");

        // Assert
        Assert.Equal(FinancialYearStatus.UnderReview, submitted.StatusCode);
        Assert.Equal("OfficerMaker", submitted.SubmittedBy);
        Assert.NotNull(submitted.SubmittedAt);
        Assert.Equal("Ready for committee review", submitted.SubmissionNotes);

        var audit = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == nameof(FinancialYear) && a.RecordId == created.Id && a.ActionName == "SubmitFinancialYearForReview");
        Assert.NotNull(audit);
    }

    [Fact]
    public async Task ApproveFiscalYearAsync_SubmitterAttemptsToApprove_ThrowsSegregationOfDutiesException()
    {
        // Arrange
        var (_, _, _, _, service) = CreateTestContext();
        var template = await service.GetDefaultTemplateAsync();
        var quarters = service.GenerateDefaultQuartersForYear(2032, template);

        var created = await service.CreateFinancialYearAsync(new CreateFinancialYearDto
        {
            FinYearCode = "2032/2033",
            StartYear = 2032,
            EndYear = 2033,
            StartDate = new DateTime(2032, 4, 1),
            EndDate = new DateTime(2033, 3, 31),
            Quarters = quarters
        }, "OfficerMaker");

        await service.SubmitForReviewAsync(created.Id, "Review notes", "OfficerMaker");

        // Act & Assert (Submitter tries to self-approve)
        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() =>
            service.ApproveFiscalYearAsync(created.Id, "Self approve attempt", "OfficerMaker", new[] { "Admin", "SuperAdmin" }));

        Assert.Contains("Segregation of duties violation", ex.Message);
    }

    [Fact]
    public async Task ApproveFiscalYearAsync_IndependentCheckerApproves_ActivatesYearAndAutoDeactivatesPreviousActiveYear()
    {
        // Arrange
        var (_, db, _, _, service) = CreateTestContext();
        var template = await service.GetDefaultTemplateAsync();

        // 1. Existing Active Year
        var fy1Quarters = service.GenerateDefaultQuartersForYear(2026, template);
        var activeYear = await service.CreateFinancialYearAsync(new CreateFinancialYearDto
        {
            FinYearCode = "2026/2027-Active",
            StartYear = 2026,
            EndYear = 2027,
            StartDate = new DateTime(2026, 4, 1),
            EndDate = new DateTime(2027, 3, 31),
            StatusCode = FinancialYearStatus.Active,
            IsActive = true,
            Quarters = fy1Quarters
        }, "SysSeeded");

        // 2. Incoming Draft submitted by Maker
        var fy2Quarters = service.GenerateDefaultQuartersForYear(2027, template);
        var newYear = await service.CreateFinancialYearAsync(new CreateFinancialYearDto
        {
            FinYearCode = "2027/2028-Incoming",
            StartYear = 2027,
            EndYear = 2028,
            StartDate = new DateTime(2027, 4, 1),
            EndDate = new DateTime(2028, 3, 31),
            Quarters = fy2Quarters
        }, "OfficerMaker");

        await service.SubmitForReviewAsync(newYear.Id, "Submission for 2027/2028", "OfficerMaker");

        // Act: Independent Checker approves
        var approved = await service.ApproveFiscalYearAsync(
            newYear.Id,
            "Approved per Executive Committee Resolution 4.2",
            "CfoChecker",
            new[] { "Admin" });

        // Assert New Year is Active
        Assert.Equal(FinancialYearStatus.Active, approved.StatusCode);
        Assert.True(approved.IsActive);
        Assert.Equal("CfoChecker", approved.ReviewedBy);
        Assert.NotNull(approved.ReviewedAt);
        Assert.Equal("Approved per Executive Committee Resolution 4.2", approved.ReviewNotes);

        // Assert Predecessor Year was auto-deactivated
        var recheckActiveYear = await service.GetFinancialYearByIdAsync(activeYear.Id);
        Assert.NotNull(recheckActiveYear);
        Assert.False(recheckActiveYear.IsActive);
        Assert.Equal(FinancialYearStatus.Inactive, recheckActiveYear.StatusCode);

        // Verify Audit Logs
        var approveAudit = await db.AuditLogs.FirstOrDefaultAsync(a => a.RecordId == newYear.Id && a.ActionName == "ApproveFinancialYearActivation");
        Assert.NotNull(approveAudit);

        var supersededAudit = await db.AuditLogs.FirstOrDefaultAsync(a => a.RecordId == activeYear.Id && a.ActionName == "SupersededByNewActiveYear");
        Assert.NotNull(supersededAudit);
    }

    [Fact]
    public async Task RejectFiscalYearAsync_CheckerRejectsWithReason_ReturnsToDraftWithFeedbackNotes()
    {
        // Arrange
        var (_, db, _, _, service) = CreateTestContext();
        var template = await service.GetDefaultTemplateAsync();
        var quarters = service.GenerateDefaultQuartersForYear(2033, template);

        var created = await service.CreateFinancialYearAsync(new CreateFinancialYearDto
        {
            FinYearCode = "2033/2034",
            StartYear = 2033,
            EndYear = 2034,
            StartDate = new DateTime(2033, 4, 1),
            EndDate = new DateTime(2034, 3, 31),
            Quarters = quarters
        }, "OfficerMaker");

        await service.SubmitForReviewAsync(created.Id, "Submission notes", "OfficerMaker");

        // Act
        var rejected = await service.RejectFiscalYearAsync(
            created.Id,
            "Q2 dates require verification against DHET gazette notice",
            "CfoChecker",
            new[] { "SuperAdmin" });

        // Assert
        Assert.Equal(FinancialYearStatus.Draft, rejected.StatusCode);
        Assert.Equal("CfoChecker", rejected.ReviewedBy);
        Assert.NotNull(rejected.ReviewedAt);
        Assert.Equal("Q2 dates require verification against DHET gazette notice", rejected.ReviewNotes);

        var rejectAudit = await db.AuditLogs.FirstOrDefaultAsync(a => a.RecordId == created.Id && a.ActionName == "RejectFinancialYearSubmission");
        Assert.NotNull(rejectAudit);
    }

    [Fact]
    public async Task RequestAmendmentAsync_ActiveYear_IncrementsRevisionAndEntersAmendmentDraft()
    {
        // Arrange
        var (_, db, _, _, service) = CreateTestContext();
        var template = await service.GetDefaultTemplateAsync();
        var quarters = service.GenerateDefaultQuartersForYear(2034, template);

        var activeYear = await service.CreateFinancialYearAsync(new CreateFinancialYearDto
        {
            FinYearCode = "2034/2035",
            StartYear = 2034,
            EndYear = 2035,
            StartDate = new DateTime(2034, 4, 1),
            EndDate = new DateTime(2035, 3, 31),
            StatusCode = FinancialYearStatus.Active,
            IsActive = true,
            Quarters = quarters
        }, "SysAdmin");

        Assert.Equal(1, activeYear.RevisionNumber);

        // Act
        var amended = await service.RequestAmendmentAsync(
            activeYear.Id,
            "Gazetted shift in Q3 working day milestone",
            "FinanceDirector");

        // Assert
        Assert.Equal(FinancialYearStatus.AmendmentDraft, amended.StatusCode);
        Assert.Equal(2, amended.RevisionNumber);
        Assert.Equal("Gazetted shift in Q3 working day milestone", amended.AmendmentReason);

        var audit = await db.AuditLogs.FirstOrDefaultAsync(a => a.RecordId == activeYear.Id && a.ActionName == "RequestFinancialYearAmendment");
        Assert.NotNull(audit);
    }

    [Fact]
    public async Task UpdateFinancialYearAsync_ActiveOrUnderReviewYear_ThrowsEditLockedException()
    {
        // Arrange
        var (_, _, _, _, service) = CreateTestContext();
        var template = await service.GetDefaultTemplateAsync();
        var quarters = service.GenerateDefaultQuartersForYear(2035, template);

        var activeYear = await service.CreateFinancialYearAsync(new CreateFinancialYearDto
        {
            FinYearCode = "2035/2036",
            StartYear = 2035,
            EndYear = 2036,
            StartDate = new DateTime(2035, 4, 1),
            EndDate = new DateTime(2036, 3, 31),
            StatusCode = FinancialYearStatus.Active,
            IsActive = true,
            Quarters = quarters
        }, "SysAdmin");

        var updateDto = new UpdateFinancialYearDto
        {
            Id = activeYear.Id,
            FinYearCode = "2035/2036",
            StartYear = 2035,
            EndYear = 2036,
            StartDate = new DateTime(2035, 4, 1),
            EndDate = new DateTime(2036, 3, 31),
            Quarters = quarters
        };

        // Act & Assert: Attempting direct edit on active year must throw
        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() =>
            service.UpdateFinancialYearAsync(updateDto, "AnyUser"));

        Assert.Contains("Active financial years require an approved amendment", ex.Message);
    }
}

