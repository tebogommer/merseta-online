using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class GrantServiceTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, GrantService service) CreateTestContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var service = new GrantService(factory, audit);
        return (factory, db, audit, service);
    }

    [Fact]
    public async Task CreateFundingWindowAsync_CreatesWindowAndLogsAudit()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var window = new GrantFundingWindow
        {
            FinYear = 2026,
            WindowName = "Discretionary Grant Window 1 - 2026/27",
            GrantTypeCode = "Discretionary",
            OpeningDate = DateTime.UtcNow.AddDays(-1),
            ClosingDate = DateTime.UtcNow.AddMonths(2),
            TotalAvailableBudget = 15000000m,
            IsActive = true
        };

        // Act
        var created = await service.CreateFundingWindowAsync(window, "GrantManager");

        // Assert
        Assert.True(created.Id > 0);
        Assert.Equal("GrantManager", created.CreatedBy);

        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "GrantFundingWindow" && a.RecordId == created.Id);
        Assert.NotNull(auditLog);
        Assert.Equal("CreateFundingWindow", auditLog.ActionName);
    }

    [Fact]
    public async Task CreateApplicationAsync_GeneratesApplicationNumberAndLogsAudit()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation { CompanyName = "Auto Parts Manufacturer", SdlNumber = "L333444555" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var app = new GrantApplication
        {
            OrganisationId = org.Id,
            ProjectTitle = "Apprenticeship 2026",
            GrantTypeCode = "Discretionary",
            StatusCode = "Submitted"
        };

        // Act
        var created = await service.CreateApplicationAsync(app, "EmployerUser");

        // Assert
        Assert.True(created.Id > 0);
        Assert.StartsWith("DG-", created.ApplicationNumber);
        Assert.Equal("EmployerUser", created.CreatedBy);

        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "GrantApplication" && a.RecordId == created.Id);
        Assert.NotNull(auditLog);
    }

    [Fact]
    public async Task AddBudgetItemAsync_CalculatesTotalCostAndUpdatesApplicationRequestedAmount()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation { CompanyName = "Toolmaker Org", SdlNumber = "L444" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var app = await service.CreateApplicationAsync(new GrantApplication
        {
            OrganisationId = org.Id,
            ProjectTitle = "Toolmaker Training Programme",
            RequestedAmount = 0m
        });

        var budgetItem = new GrantProjectBudget
        {
            GrantApplicationId = app.Id,
            ExpenseCategory = "Stipends",
            Description = "Learner monthly stipends",
            UnitCost = 4500m,
            Quantity = 10,
            TotalCost = 0m // should auto-calculate to 45,000
        };

        // Act
        var added = await service.AddBudgetItemAsync(budgetItem, "FinancialOfficer");

        // Assert
        Assert.Equal(45000m, added.TotalCost);

        var updatedApp = await db.GrantApplications.FindAsync(app.Id);
        Assert.NotNull(updatedApp);
        Assert.Equal(45000m, updatedApp.RequestedAmount);

        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "GrantProjectBudget");
        Assert.NotNull(auditLog);
    }

    [Fact]
    public async Task RemoveBudgetItemAsync_UpdatesApplicationRequestedAmount()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation { CompanyName = "Tuition Org", SdlNumber = "L555" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var app = await service.CreateApplicationAsync(new GrantApplication { OrganisationId = org.Id, ProjectTitle = "Project 1" });
        var item = await service.AddBudgetItemAsync(new GrantProjectBudget
        {
            GrantApplicationId = app.Id,
            ExpenseCategory = "Tuition",
            UnitCost = 20000m,
            Quantity = 1
        });

        // Act
        var removed = await service.RemoveBudgetItemAsync(item.Id, "Admin");

        // Assert
        Assert.True(removed);
        var updatedApp = await db.GrantApplications.FindAsync(app.Id);
        Assert.NotNull(updatedApp);
        Assert.Equal(0m, updatedApp.RequestedAmount);
    }

    [Fact]
    public async Task GetAllApplicationsAsync_FiltersCorrectly()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation { CompanyName = "Filter Org", SdlNumber = "L666" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var window1 = await service.CreateFundingWindowAsync(new GrantFundingWindow
        {
            WindowName = "Window A",
            FinYear = 2026,
            OpeningDate = DateTime.UtcNow.AddDays(-1),
            ClosingDate = DateTime.UtcNow.AddMonths(1),
            IsActive = true
        });

        var window2 = await service.CreateFundingWindowAsync(new GrantFundingWindow
        {
            WindowName = "Window B",
            FinYear = 2026,
            OpeningDate = DateTime.UtcNow.AddDays(-1),
            ClosingDate = DateTime.UtcNow.AddMonths(1),
            IsActive = true
        });

        await service.CreateApplicationAsync(new GrantApplication { OrganisationId = org.Id, FundingWindowId = window1.Id, ProjectTitle = "Project Alpha" });
        await service.CreateApplicationAsync(new GrantApplication { OrganisationId = org.Id, FundingWindowId = window2.Id, ProjectTitle = "Project Beta" });

        // Act
        var window1Apps = await service.GetAllApplicationsAsync(fundingWindowId: window1.Id);
        var searchApps = await service.GetAllApplicationsAsync(search: "Beta");

        // Assert
        Assert.Single(window1Apps);
        Assert.Equal("Project Alpha", window1Apps[0].ProjectTitle);

        Assert.Single(searchApps);
        Assert.Equal("Project Beta", searchApps[0].ProjectTitle);
    }

    [Fact]
    public async Task EvaluateWspEligibilityAsync_ReturnsCompliant_WhenApprovedWspExists()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation
        {
            CompanyName = "Precision Engineering Works",
            SdlNumber = "L112233445",
            LevyCategoryCode = "LEVY_PAYING"
        };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var approvedWsp = new WspSubmission
        {
            OrganisationId = org.Id,
            FinYear = 2026,
            ReferenceNumber = "WSP-2026-00123",
            WspApprovalStatusCode = "Approved",
            PlannedTrainingBudget = 250000m
        };
        db.WspSubmissions.Add(approvedWsp);
        await db.SaveChangesAsync();

        // Act
        var result = await service.EvaluateWspEligibilityAsync(org.Id, 2026);

        // Assert
        Assert.True(result.IsEligible);
        Assert.False(result.IsExempt);
        Assert.Equal("Approved", result.Status);
        Assert.Equal(approvedWsp.Id, result.WspSubmissionId);
        Assert.Equal("WSP-2026-00123", result.WspReferenceNumber);
    }

    [Fact]
    public async Task EvaluateWspEligibilityAsync_ReturnsMissing_WhenNoWspExists()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation
        {
            CompanyName = "Non-Compliant Auto Workshop",
            SdlNumber = "L998877665",
            LevyCategoryCode = "LEVY_PAYING"
        };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        // Act
        var result = await service.EvaluateWspEligibilityAsync(org.Id, 2026);

        // Assert
        Assert.False(result.IsEligible);
        Assert.False(result.IsExempt);
        Assert.Equal("Missing", result.Status);
        Assert.Null(result.WspSubmissionId);
    }

    [Fact]
    public async Task EvaluateWspEligibilityAsync_ReturnsExempt_ForNonLevyOrPublicEntity()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation
        {
            CompanyName = "Ekurhuleni East TVET College",
            SdlNumber = "N/A",
            OrganisationTypeCode = "TVET",
            LevyCategoryCode = "NON_LEVY_PAYING"
        };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        // Act
        var result = await service.EvaluateWspEligibilityAsync(org.Id, 2026);

        // Assert
        Assert.True(result.IsEligible);
        Assert.True(result.IsExempt);
        Assert.Equal("Exempt", result.Status);
        Assert.Contains("exempt", result.Reason, StringComparison.OrdinalIgnoreCase);
    }

    [Fact]
    public async Task GenerateGrantMoaFromApplicationAsync_CreatesMoaWithFourTrancheMilestones()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation { CompanyName = "Mega Motor Assembly", SdlNumber = "L777888999" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var app = new GrantApplication
        {
            OrganisationId = org.Id,
            ProjectTitle = "Electric Vehicle Technician Apprenticeship",
            ApplicationNumber = "DG-2026-0042",
            RequestedAmount = 1000000m,
            ApprovedAmount = 1000000m,
            ApplicationStatusCode = "Approved"
        };
        db.GrantApplications.Add(app);
        await db.SaveChangesAsync();

        // Act
        var moa = await service.GenerateGrantMoaFromApplicationAsync(app.Id, "LegalManager");

        // Assert
        Assert.NotNull(moa);
        Assert.True(moa.Id > 0);
        Assert.Equal(app.Id, moa.GrantApplicationId);
        Assert.StartsWith("MOA-", moa.MoaNumber);
        Assert.Equal(1000000m, moa.TotalContractValue);
        Assert.Equal(4, moa.Milestones.Count);

        // Tranche checks: 30%, 30%, 30%, 10%
        var sortedMilestones = moa.Milestones.OrderBy(m => m.MilestoneNumber).ToList();
        Assert.Equal(30m, sortedMilestones[0].TranchePercentage);
        Assert.Equal(300000m, sortedMilestones[0].TrancheAmount);
        Assert.Equal(30m, sortedMilestones[1].TranchePercentage);
        Assert.Equal(300000m, sortedMilestones[1].TrancheAmount);
        Assert.Equal(30m, sortedMilestones[2].TranchePercentage);
        Assert.Equal(300000m, sortedMilestones[2].TrancheAmount);
        Assert.Equal(10m, sortedMilestones[3].TranchePercentage);
        Assert.Equal(100000m, sortedMilestones[3].TrancheAmount);

        // Double-write audit log check
        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "GrantMoa" && a.RecordId == moa.Id);
        Assert.NotNull(auditLog);
        Assert.Equal("GenerateFromApplication", auditLog.ActionName);
    }

    [Fact]
    public async Task UpdateFundingWindowAsync_UpdatesFieldsAndLogsAudit()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var window = await service.CreateFundingWindowAsync(new GrantFundingWindow
        {
            WindowName = "Initial Window Name",
            FinYear = 2026,
            TotalAvailableBudget = 10000000m,
            OpeningDate = DateTime.UtcNow,
            ClosingDate = DateTime.UtcNow.AddMonths(1)
        });

        // Act
        window.WindowName = "Updated Window Name 2026/27";
        window.TotalAvailableBudget = 12500000m;
        var updated = await service.UpdateFundingWindowAsync(window, "AdminUser");

        // Assert
        Assert.Equal("Updated Window Name 2026/27", updated.WindowName);
        Assert.Equal(12500000m, updated.TotalAvailableBudget);

        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "GrantFundingWindow" && a.ActionName == "UpdateFundingWindow");
        Assert.NotNull(auditLog);
    }

    [Fact]
    public async Task GetFundingWindowSummaryAsync_CalculatesMetricsCorrectly()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation { CompanyName = "Skills Org", SdlNumber = "L888999111" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var window = await service.CreateFundingWindowAsync(new GrantFundingWindow
        {
            WindowName = "Apprenticeship Window 2026",
            FinYear = 2026,
            TotalAvailableBudget = 5000000m,
            OpeningDate = DateTime.UtcNow.AddDays(-5),
            ClosingDate = DateTime.UtcNow.AddDays(25),
            IsActive = true
        });

        await service.CreateApplicationAsync(new GrantApplication
        {
            OrganisationId = org.Id,
            FundingWindowId = window.Id,
            ProjectTitle = "App 1",
            RequestedAmount = 1000000m,
            ApprovedAmount = 800000m,
            ApplicationStatusCode = "Approved"
        });

        await service.CreateApplicationAsync(new GrantApplication
        {
            OrganisationId = org.Id,
            FundingWindowId = window.Id,
            ProjectTitle = "App 2",
            RequestedAmount = 1500000m,
            ApprovedAmount = null,
            ApplicationStatusCode = "Submitted"
        });

        // Act
        var summary = await service.GetFundingWindowSummaryAsync(window.Id);

        // Assert
        Assert.NotNull(summary);
        Assert.Equal(window.Id, summary.WindowId);
        Assert.Equal(2, summary.TotalApplications);
        Assert.Equal(2500000m, summary.TotalRequestedAmount);
        Assert.Equal(800000m, summary.TotalApprovedAmount);
        Assert.Equal(4200000m, summary.RemainingBudget);
        Assert.True(summary.IsOpen);
    }

    [Fact]
    public async Task DeleteFundingWindowAsync_DeletesWindowWhenNoApplications()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var window = await service.CreateFundingWindowAsync(new GrantFundingWindow
        {
            WindowName = "Unused Window",
            FinYear = 2026,
            TotalAvailableBudget = 1000000m,
            OpeningDate = DateTime.UtcNow,
            ClosingDate = DateTime.UtcNow.AddMonths(1)
        });

        // Act
        var deleted = await service.DeleteFundingWindowAsync(window.Id, "AdminUser");

        // Assert
        Assert.True(deleted);
        var found = await db.GrantFundingWindows.FindAsync(window.Id);
        Assert.Null(found);
    }

    [Fact]
    public async Task GetStrategicPrioritiesAsync_ReturnsActivePriorities()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var sp1 = new StrategicPriority
        {
            Code = "SP-GREEN-01",
            Name = "Green Economy & EV Tech",
            NsdpOutcomeCode = "NSDP-OUTCOME-1",
            IsActive = true
        };
        var sp2 = new StrategicPriority
        {
            Code = "SP-INACTIVE-02",
            Name = "Decommissioned Theme",
            NsdpOutcomeCode = "NSDP-OUTCOME-2",
            IsActive = false
        };

        db.StrategicPriorities.AddRange(sp1, sp2);
        await db.SaveChangesAsync();

        // Act
        var activeList = await service.GetStrategicPrioritiesAsync(activeOnly: true);
        var allList = await service.GetStrategicPrioritiesAsync(activeOnly: false);

        // Assert
        Assert.Single(activeList);
        Assert.Equal("SP-GREEN-01", activeList[0].Code);
        Assert.Equal(2, allList.Count);
    }

    [Fact]
    public async Task AddWindowPriorityAsync_EnforcesWindowBudgetEnvelope()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var window = await service.CreateFundingWindowAsync(new GrantFundingWindow
        {
            WindowName = "Window with Capped Envelope",
            FinYear = 2026,
            TotalAvailableBudget = 10000000m,
            OpeningDate = DateTime.UtcNow,
            ClosingDate = DateTime.UtcNow.AddMonths(2),
            IsActive = true
        });

        var sp = await service.SaveStrategicPriorityAsync(new StrategicPriority
        {
            Code = "SP-4IR-02",
            Name = "Robotics & Automation",
            NsdpOutcomeCode = "NSDP-OUTCOME-2",
            IsActive = true
        });

        // Act & Assert 1: Valid sub-budget allocation succeeds
        var fwp = await service.AddWindowPriorityAsync(new FundingWindowPriority
        {
            FundingWindowId = window.Id,
            StrategicPriorityId = sp.Id,
            AllocatedBudget = 6000000m,
            TargetBeneficiaries = 100,
            MinScoreThreshold = 65.00m,
            IsRingFenced = true,
            IsActive = true
        });

        Assert.True(fwp.Id > 0);
        Assert.Equal(6000000m, fwp.AllocatedBudget);

        // Act & Assert 2: Over-allocation throws InvalidOperationException
        var sp2 = await service.SaveStrategicPriorityAsync(new StrategicPriority
        {
            Code = "SP-ARTISAN-03",
            Name = "Artisan Acceleration",
            NsdpOutcomeCode = "NSDP-OUTCOME-1",
            IsActive = true
        });

        await Assert.ThrowsAsync<InvalidOperationException>(() => service.AddWindowPriorityAsync(new FundingWindowPriority
        {
            FundingWindowId = window.Id,
            StrategicPriorityId = sp2.Id,
            AllocatedBudget = 5000001m, // exceeds remaining 4,000,000 envelope!
            TargetBeneficiaries = 50,
            IsActive = true
        }));
    }

    [Fact]
    public async Task GetStrategicReportDashboardAsync_Calculates3TierMetrics()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var spGreen = await service.SaveStrategicPriorityAsync(new StrategicPriority
        {
            Code = "SP-GREEN-01",
            Name = "Green Economy & EV Tech",
            NsdpOutcomeCode = "NSDP-OUTCOME-1",
            NsdpOutcomeDescription = "Outcome 1: High Demand Skills",
            SipCategory = "SIP 8: Green Energy",
            IsActive = true
        });

        var window = await service.CreateFundingWindowAsync(new GrantFundingWindow
        {
            WindowName = "Strategic DG Window 2026",
            FinYear = 2026,
            TotalAvailableBudget = 20000000m,
            OpeningDate = DateTime.UtcNow.AddDays(-10),
            ClosingDate = DateTime.UtcNow.AddDays(30),
            IsActive = true
        });

        var fwp = await service.AddWindowPriorityAsync(new FundingWindowPriority
        {
            FundingWindowId = window.Id,
            StrategicPriorityId = spGreen.Id,
            AllocatedBudget = 12000000m,
            TargetBeneficiaries = 150,
            MinScoreThreshold = 70.00m,
            IsRingFenced = true,
            IsActive = true
        });

        var org = new Organisation
        {
            CompanyName = "Solar Mobility SA",
            SdlNumber = "L999888777",
            ProvinceCode = "GP"
        };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var app = new GrantApplication
        {
            OrganisationId = org.Id,
            FundingWindowId = window.Id,
            StrategicPriorityId = spGreen.Id,
            FundingWindowPriorityId = fwp.Id,
            ProjectTitle = "EV Battery Cell Manufacturing Apprenticeship",
            RequestedAmount = 6000000m,
            ApprovedAmount = 6000000m,
            StatusCode = "Approved",
            ApplicationStatusCode = "Approved"
        };
        await service.CreateApplicationAsync(app);

        // Act
        var report = await service.GetStrategicReportDashboardAsync(finYear: 2026, windowId: window.Id);

        // Assert
        Assert.NotNull(report);
        Assert.Equal(12000000m, report.TotalAllocatedBudget);
        Assert.Equal(6000000m, report.TotalApprovedAmount);
        Assert.Equal(50.0m, report.BudgetAbsorptionRate); // 6M / 12M = 50%
        Assert.Equal(150, report.TotalLearnersTarget);

        // Operational Tier
        var opTheme = report.OperationalThemes.FirstOrDefault(t => t.PriorityCode == "SP-GREEN-01");
        Assert.NotNull(opTheme);
        Assert.Equal(12000000m, opTheme.AllocatedBudget);
        Assert.Equal(6000000m, opTheme.ApprovedAmount);
        Assert.Equal(6000000m, opTheme.RemainingBudget);
        Assert.Equal(50.0m, opTheme.BurnRatePercent);
        Assert.Equal(1, opTheme.ApprovedCount);
        Assert.True(opTheme.IsRingFenced);

        // Tactical Tier
        var gp = report.TacticalProvinces.FirstOrDefault(p => p.ProvinceName == "Gauteng");
        Assert.NotNull(gp);
        Assert.Equal(1, gp.ApplicationCount);
        Assert.Equal(6000000m, gp.TotalApproved);

        // Strategic Tier
        var outcome = report.StrategicNsdpOutcomes.FirstOrDefault(o => o.OutcomeCode == "NSDP-OUTCOME-1");
        Assert.NotNull(outcome);
        Assert.Equal(6000000m, outcome.TotalBudgetCommitted);
        Assert.Equal(1800000m, outcome.TotalDisbursed); // 30% first tranche
    }
}
