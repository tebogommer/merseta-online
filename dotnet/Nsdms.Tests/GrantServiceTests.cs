using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Domain.Lookups;
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

        var org1 = new Organisation { CompanyName = "Skills Org 1", SdlNumber = "L888999111" };
        var org2 = new Organisation { CompanyName = "Skills Org 2", SdlNumber = "L888999222" };
        db.Organisations.AddRange(org1, org2);
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
            OrganisationId = org1.Id,
            FundingWindowId = window.Id,
            ProjectTitle = "App 1",
            RequestedAmount = 1000000m,
            ApprovedAmount = 800000m,
            ApplicationStatusCode = "Approved"
        });

        await service.CreateApplicationAsync(new GrantApplication
        {
            OrganisationId = org2.Id,
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

    [Fact]
    public async Task CreateApplicationAsync_DuplicateApplicationSameWindow_ThrowsInvalidOperationException()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();
        var org = new Organisation { CompanyName = "Single App Employer", SdlNumber = "L999888777" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var window = await service.CreateFundingWindowAsync(new GrantFundingWindow
        {
            WindowName = "Unique Test Window",
            FinYear = 2026,
            TotalAvailableBudget = 10000000m,
            OpeningDate = DateTime.UtcNow.AddDays(-2),
            ClosingDate = DateTime.UtcNow.AddDays(30),
            IsActive = true,
            ApprovalStatusCode = "Active"
        });

        // First application succeeds
        var app1 = new GrantApplication
        {
            OrganisationId = org.Id,
            FundingWindowId = window.Id,
            ProjectTitle = "First Valid Application",
            RequestedAmount = 2500000m,
            ApplicationStatusCode = "Submitted"
        };
        await service.CreateApplicationAsync(app1, "OfficerA");

        // Second application by same employer in same window must fail
        var app2 = new GrantApplication
        {
            OrganisationId = org.Id,
            FundingWindowId = window.Id,
            ProjectTitle = "Duplicate Forbidden Application",
            RequestedAmount = 1500000m,
            ApplicationStatusCode = "Submitted"
        };

        // Act & Assert
        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() => service.CreateApplicationAsync(app2, "OfficerA"));
        Assert.Contains("only one application is permitted per funding window", ex.Message);
    }

    [Fact]
    public async Task DualAuthorisation_ProposeAndApprove_SucceedsForDistinctUsers()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();
        var window = await service.CreateFundingWindowAsync(new GrantFundingWindow
        {
            WindowName = "Gazetted DG 2026 Window",
            FinYear = 2026,
            TotalAvailableBudget = 25000000m,
            OpeningDate = DateTime.UtcNow.AddDays(-1),
            ClosingDate = DateTime.UtcNow.AddDays(45),
            IsActive = false,
            ApprovalStatusCode = "Draft"
        });

        // Act 1: Propose by Officer A
        var proposed = await service.ProposeFundingWindowAsync(window.Id, "OfficerA");
        Assert.Equal("PendingApproval", proposed.ApprovalStatusCode);
        Assert.Equal("OfficerA", proposed.ProposedByUserId);
        Assert.NotNull(proposed.ProposedDate);

        // Act 2: Approve & Activate by Executive B
        var approved = await service.ApproveAndActivateFundingWindowAsync(window.Id, "ExecutiveB", "Gazetted per MANCO Res 2026/01");
        Assert.Equal("Active", approved.ApprovalStatusCode);
        Assert.True(approved.IsActive);
        Assert.Equal("ExecutiveB", approved.ApprovedByUserId);
        Assert.NotNull(approved.ApprovedDate);
    }

    [Fact]
    public async Task DualAuthorisation_SelfApproval_ThrowsSegregationOfDutiesViolation()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();
        var window = await service.CreateFundingWindowAsync(new GrantFundingWindow
        {
            WindowName = "Self-Approval Test Window",
            FinYear = 2026,
            TotalAvailableBudget = 10000000m,
            OpeningDate = DateTime.UtcNow.AddDays(-1),
            ClosingDate = DateTime.UtcNow.AddDays(45),
            IsActive = false,
            ApprovalStatusCode = "Draft"
        });

        await service.ProposeFundingWindowAsync(window.Id, "OfficerA");

        // Act & Assert: Proposing officer OfficerA attempts to self-approve
        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() =>
            service.ApproveAndActivateFundingWindowAsync(window.Id, "OfficerA"));

        Assert.Contains("Dual Authorisation Governance Violation", ex.Message);
        Assert.Contains("cannot approve and activate their own DG funding window", ex.Message);
    }

    [Fact]
    public async Task RealTimeBudgetConsumption_OverSubscription_FlagsAlertAndComputesCapacity()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();
        var org1 = new Organisation { CompanyName = "Steel Corp", SdlNumber = "L111222333" };
        var org2 = new Organisation { CompanyName = "Motor Works", SdlNumber = "L444555666" };
        db.Organisations.AddRange(org1, org2);

        var priority = new StrategicPriority
        {
            Code = "SP-AERO-01",
            Name = "Aerospace Precision Engineering",
            NsdpOutcomeCode = "NSDP-OUTCOME-1"
        };
        db.StrategicPriorities.Add(priority);
        await db.SaveChangesAsync();

        var window = await service.CreateFundingWindowAsync(new GrantFundingWindow
        {
            WindowName = "Aerospace Window 2026",
            FinYear = 2026,
            TotalAvailableBudget = 10000000m,
            OpeningDate = DateTime.UtcNow.AddDays(-1),
            ClosingDate = DateTime.UtcNow.AddDays(60),
            IsActive = true,
            ApprovalStatusCode = "Active"
        });

        var windowPriority = await service.AddWindowPriorityAsync(new FundingWindowPriority
        {
            FundingWindowId = window.Id,
            StrategicPriorityId = priority.Id,
            AllocatedBudget = 3000000m, // R3,000,000 budget envelope
            TargetBeneficiaries = 50
        });

        // App 1 consumes R2,000,000 (R1,000,000 remaining)
        await service.CreateApplicationAsync(new GrantApplication
        {
            OrganisationId = org1.Id,
            FundingWindowId = window.Id,
            FundingWindowPriorityId = windowPriority.Id,
            ProjectTitle = "App 1 Precision Lathe Training",
            RequestedAmount = 2000000m,
            ApplicationStatusCode = "Submitted"
        });

        // Act: Evaluate an incoming application requesting R2,500,000 (would push total to R4.5m against R3.0m envelope)
        var consumption = await service.EvaluateBudgetConsumptionAsync(windowPriority.Id, 2500000m);

        // Assert
        Assert.True(consumption.IsOverSubscribed);
        Assert.Equal(2000000m, consumption.CurrentCommittedAmount);
        Assert.Equal(1000000m, consumption.RemainingBudget);
        Assert.Equal(1500000m, consumption.OverSubscriptionAmount); // 4.5M - 3.0M = 1.5M over-subscribed
        Assert.Contains("is over-subscribed by", consumption.AdvisoryMessage);
    }

    [Fact]
    public async Task CreateApplicationAsync_OutsideWindowDates_ThrowsInvalidOperationException()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();
        var org = new Organisation { CompanyName = "Late Submitter Ltd", SdlNumber = "L777888999" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        // Expired window
        var closedWindow = await service.CreateFundingWindowAsync(new GrantFundingWindow
        {
            WindowName = "Past Expired Window",
            FinYear = 2025,
            TotalAvailableBudget = 5000000m,
            OpeningDate = DateTime.UtcNow.AddMonths(-3),
            ClosingDate = DateTime.UtcNow.AddMonths(-1),
            IsActive = true,
            ApprovalStatusCode = "Active"
        });

        var app = new GrantApplication
        {
            OrganisationId = org.Id,
            FundingWindowId = closedWindow.Id,
            ProjectTitle = "Late Submission",
            RequestedAmount = 1000000m
        };

        // Act & Assert
        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() => service.CreateApplicationAsync(app, "Officer"));
        Assert.Contains("funding window 'Past Expired Window' is currently closed", ex.Message);
    }

    [Fact]
    public async Task InitializeWindowFromTemplateAsync_PopulatesTemplateEligibilitiesAndInterventions()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        db.StakeholderEligibilityTypes.Add(new StakeholderEligibilityType { Code = "LEVY_PAYING", Name = "Levy Paying Employers", Active = true });
        db.StakeholderEligibilityTypes.Add(new StakeholderEligibilityType { Code = "SMME_EXEMPT", Name = "Non-Levy Paying SMMEs", Active = true });
        db.InterventionTypes.Add(new InterventionType { Code = "APPRENTICE", Name = "Apprenticeship", IsPivotal = true, Active = true });
        await db.SaveChangesAsync();

        var template = new GrantWindowTemplate
        {
            TemplateCode = "TMPL_TEST_PIVOTAL",
            Name = "Unit Test PIVOTAL Template",
            Description = "Automated test template for DG window",
            IsPivotal = true,
            WindowClassification = "PIVOTAL",
            RequireWspComplianceDefault = true,
            EstimatedDurationDays = 30,
            IsActive = true
        };
        db.GrantWindowTemplates.Add(template);
        await db.SaveChangesAsync();

        db.GrantWindowTemplateEligibilities.Add(new GrantWindowTemplateEligibility
        {
            TemplateId = template.Id,
            StakeholderEligibilityTypeCode = "LEVY_PAYING"
        });
        db.GrantWindowTemplateEligibilities.Add(new GrantWindowTemplateEligibility
        {
            TemplateId = template.Id,
            StakeholderEligibilityTypeCode = "SMME_EXEMPT"
        });

        db.GrantWindowTemplateInterventions.Add(new GrantWindowTemplateIntervention
        {
            TemplateId = template.Id,
            InterventionTypeCode = "APPRENTICE"
        });
        await db.SaveChangesAsync();

        // Act
        var window = await service.InitializeWindowFromTemplateAsync(
            template.Id,
            2026,
            "2026 Gazetted PIVOTAL Window",
            DateTime.UtcNow,
            DateTime.UtcNow.AddDays(30),
            10000000m,
            "OfficerAdmin"
        );

        // Assert
        Assert.NotNull(window);
        Assert.Equal(template.Id, window.TemplateId);
        Assert.True(window.IsPivotal);
        Assert.True(window.RequireWspCompliance);
        Assert.Equal("Draft", window.ApprovalStatusCode);

        var eligibilities = await service.GetWindowEligibilitiesAsync(window.Id);
        Assert.Equal(2, eligibilities.Count);
        Assert.Contains(eligibilities, e => e.StakeholderEligibilityTypeCode == "LEVY_PAYING");
        Assert.Contains(eligibilities, e => e.StakeholderEligibilityTypeCode == "SMME_EXEMPT");

        var interventions = await service.GetWindowInterventionsAsync(window.Id);
        Assert.Single(interventions);
        Assert.Equal("APPRENTICE", interventions[0].InterventionTypeCode);
    }

    [Fact]
    public async Task AddInterventionToCatalogAsync_PersistsNewNonPivotalIntervention()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var newIntervention = new InterventionType
        {
            Code = "TOOL_ALLOW_2026",
            Name = "STEM Workshop Tooling Allowance",
            Description = "Workshop tools grant for TVET artisan workshops",
            IsPivotal = false,
            Category = "Workshop Equipment",
            DefaultUnitCost = 45000m,
            Active = true
        };

        // Act
        var created = await service.AddInterventionToCatalogAsync(newIntervention, "SystemAdmin");

        // Assert
        Assert.NotNull(created);
        Assert.Equal("TOOL_ALLOW_2026", created.Code);
        Assert.False(created.IsPivotal);

        var catalog = await service.GetInterventionCatalogAsync(isPivotal: false);
        Assert.Contains(catalog, i => i.Code == "TOOL_ALLOW_2026" && i.DefaultUnitCost == 45000m);
    }

    [Fact]
    public async Task CreateApplicationAsync_WithRequireWspComplianceTrue_ThrowsWhenWspNotApproved()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation
        {
            CompanyName = "Non-Compliant Employer",
            SdlNumber = "L111222333",
            LevyCategoryCode = "LEVY_PAYING"
        };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var window = await service.CreateFundingWindowAsync(new GrantFundingWindow
        {
            WindowName = "WSP Enforced DG Window",
            FinYear = 2026,
            IsActive = true,
            ApprovalStatusCode = "Active",
            OpeningDate = DateTime.UtcNow.AddDays(-1),
            ClosingDate = DateTime.UtcNow.AddMonths(1),
            TotalAvailableBudget = 5000000m,
            RequireWspCompliance = true // Enforce WSP
        });

        var app = new GrantApplication
        {
            OrganisationId = org.Id,
            FundingWindowId = window.Id,
            ProjectTitle = "Apprenticeship 2026",
            RequestedAmount = 500000m
        };

        // Act & Assert
        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() => service.CreateApplicationAsync(app, "Officer"));
        Assert.Contains("Mandatory Grant Compliance Requirement", ex.Message);
        Assert.Contains("approved Workplace Skills Plan", ex.Message);
    }

    [Fact]
    public async Task CreateApplicationAsync_WithRequireWspComplianceFalse_SucceedsEvenWithoutWsp()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation
        {
            CompanyName = "Early Window Employer",
            SdlNumber = "L444555666",
            LevyCategoryCode = "LEVY_PAYING"
        };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var window = await service.CreateFundingWindowAsync(new GrantFundingWindow
        {
            WindowName = "Early Independent DG Window",
            FinYear = 2026,
            IsActive = true,
            ApprovalStatusCode = "Active",
            OpeningDate = DateTime.UtcNow.AddDays(-1),
            ClosingDate = DateTime.UtcNow.AddMonths(1),
            TotalAvailableBudget = 5000000m,
            RequireWspCompliance = false // WSP is NOT enforced
        });

        var app = new GrantApplication
        {
            OrganisationId = org.Id,
            FundingWindowId = window.Id,
            ProjectTitle = "Early Window Project",
            RequestedAmount = 300000m
        };

        // Act
        var created = await service.CreateApplicationAsync(app, "Officer");

        // Assert
        Assert.NotNull(created);
        Assert.True(created.Id > 0);
        Assert.Equal("Submitted", created.ApplicationStatusCode);
    }

    [Fact]
    public async Task CreateApplicationAsync_WithIneligibleStakeholder_ThrowsInvalidOperationException()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        // Standard employer (not a TVET college)
        var org = new Organisation
        {
            CompanyName = "Standard Manufacturing Corp",
            SdlNumber = "L999888777",
            LevyCategoryCode = "LEVY_PAYING",
            NonEmployerEntityType = null
        };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var window = await service.CreateFundingWindowAsync(new GrantFundingWindow
        {
            WindowName = "TVET Colleges Only Window",
            FinYear = 2026,
            IsActive = true,
            ApprovalStatusCode = "Active",
            OpeningDate = DateTime.UtcNow.AddDays(-1),
            ClosingDate = DateTime.UtcNow.AddMonths(1),
            TotalAvailableBudget = 5000000m,
            RequireWspCompliance = false
        });

        // Configure window so only PUBLIC_TVET is eligible
        await service.SetWindowEligibilitiesAsync(window.Id, new[] { "PUBLIC_TVET" }, "AdminUser");

        var app = new GrantApplication
        {
            OrganisationId = org.Id,
            FundingWindowId = window.Id,
            ProjectTitle = "Employer Applying to TVET Window",
            RequestedAmount = 250000m
        };

        // Act & Assert
        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() => service.CreateApplicationAsync(app, "Officer"));
        Assert.Contains("Stakeholder Eligibility Violation", ex.Message);
        Assert.Contains("does not match the configured eligible stakeholder classifications", ex.Message);
    }

    [Fact]
    public async Task CreateApplicationAsync_WithCompositePivotalAndNonPivotalInterventions_CalculatesRollupAndPersistsMotivation()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation
        {
            CompanyName = "Precision Engineering Consortium",
            SdlNumber = "L102938475",
            LevyCategoryCode = "LEVY_PAYING"
        };
        db.Organisations.Add(org);
        db.InterventionTypes.AddRange(
            new InterventionType { Code = "APPRENTICESHIP", Name = "Apprenticeship", IsPivotal = true, Active = true },
            new InterventionType { Code = "INFRA_WORKSHOP", Name = "TVET Workshop Infrastructure & Equipment", IsPivotal = false, Active = true }
        );
        await db.SaveChangesAsync();

        var window = await service.CreateFundingWindowAsync(new GrantFundingWindow
        {
            WindowName = "Hybrid PIVOTAL & Strategic Infrastructure Window 2026",
            FinYear = 2026,
            WindowClassification = "Hybrid",
            IsActive = true,
            ApprovalStatusCode = "Active",
            OpeningDate = DateTime.UtcNow.AddDays(-1),
            ClosingDate = DateTime.UtcNow.AddMonths(2),
            TotalAvailableBudget = 20000000m,
            RequireWspCompliance = false
        });

        var app = new GrantApplication
        {
            OrganisationId = org.Id,
            FundingWindowId = window.Id,
            ProjectTitle = "National Tooling & Apprenticeship Modernisation Programme",
            GrantTypeCode = "HYBRID",
            ApplicationStatusCode = "Submitted",
            
            // Strategic Project Motivation Questions
            ProjectDescription = "Dual-intervention initiative combining accredited artisan apprenticeships with regional TVET tooling modernisation.",
            Purpose = "Upgrade tooling infrastructure and train high-absorption toolmakers.",
            Outcomes = "20 certified artisans and 2 upgraded training facilities.",
            Benefits = "Direct employment opportunities and enhanced local industrial tooling capacity.",
            PotentialRisks = "Supply chain delays on equipment; mitigated via pre-qualified vendors.",
            EstimatedOverallProjectCost = 2500000m,
            NumberOfBeneficiaries = 20,
            RequireProjectAdministrationCosts = true,
            TargetProvinces = "Gauteng, Eastern Cape",

            // Interventions: 1 PIVOTAL + 1 Non-PIVOTAL
            Interventions = new List<GrantApplicationIntervention>
            {
                new()
                {
                    IsPivotal = true,
                    InterventionTypeCode = "APPRENTICESHIP",
                    QualificationTitle = "National Certificate: Toolmaker",
                    SaqaId = "65432",
                    NqfLevel = "NQF Level 4",
                    OfoCode = "653302",
                    LearnerCountEmployed = 8,
                    LearnerCountUnemployed = 12,
                    UnitCost = 60000m,
                    TotalAmount = 1200000m
                },
                new()
                {
                    IsPivotal = false,
                    InterventionTypeCode = "INFRA_WORKSHOP",
                    DeliverableName = "Precision Tooling Simulation Benches",
                    TargetQuantity = 2,
                    EstimatedCost = 450000m,
                    TotalAmount = 450000m,
                    MilestoneNumber = 1,
                    ProjectedStartDate = DateTime.UtcNow.AddMonths(1),
                    ProjectedEndDate = DateTime.UtcNow.AddMonths(4),
                    Comments = "Installation and commissioning of 2 tooling simulation rigs"
                }
            }
        };

        // Act
        var created = await service.CreateApplicationAsync(app, "SdfUser");

        // Assert
        Assert.NotNull(created);
        Assert.True(created.Id > 0);
        Assert.True(created.HasPivotalInterventions);
        Assert.True(created.HasNonPivotalInterventions);
        Assert.Equal("National Tooling & Apprenticeship Modernisation Programme", created.ProjectTitle);
        Assert.Equal("Dual-intervention initiative combining accredited artisan apprenticeships with regional TVET tooling modernisation.", created.ProjectDescription);
        Assert.Equal(20, created.NumberOfBeneficiaries);
        Assert.True(created.RequireProjectAdministrationCosts);
        Assert.Equal("Gauteng, Eastern Cape", created.TargetProvinces);

        // Verify budget rollup: 1,200,000 + 450,000 = 1,650,000
        Assert.Equal(1650000m, created.RequestedAmount);

        // Verify persisted interventions in database
        var persistedInterventions = await service.GetApplicationInterventionsAsync(created.Id);
        Assert.Equal(2, persistedInterventions.Count);
        Assert.Contains(persistedInterventions, i => i.IsPivotal && i.QualificationTitle == "National Certificate: Toolmaker");
        Assert.Contains(persistedInterventions, i => !i.IsPivotal && i.DeliverableName == "Precision Tooling Simulation Benches");

        // Verify double-write audit trail
        var auditLogs = await db.AuditLogs.Where(a => a.EntityName == "GrantApplication" && a.RecordId == created.Id).ToListAsync();
        Assert.NotEmpty(auditLogs);
    }

    [Fact]
    public async Task AddApplicationInterventionAsync_ForPivotalAndNonPivotal_UpdatesApplicationFlagsAndTotals()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation { CompanyName = "Aero Engineering", SdlNumber = "L111222333" };
        db.Organisations.Add(org);
        db.InterventionTypes.AddRange(
            new InterventionType { Code = "LEARNERSHIP", Name = "Learnership", IsPivotal = true, Active = true },
            new InterventionType { Code = "INFRA_WORKSHOP", Name = "TVET Workshop Infrastructure & Equipment", IsPivotal = false, Active = true }
        );
        await db.SaveChangesAsync();

        var app = await service.CreateApplicationAsync(new GrantApplication
        {
            OrganisationId = org.Id,
            ProjectTitle = "Aerospace Manufacturing Development",
            GrantTypeCode = "HYBRID",
            RequestedAmount = 0m
        });

        // Initially no interventions
        Assert.False(app.HasPivotalInterventions);
        Assert.False(app.HasNonPivotalInterventions);

        // Act 1: Add PIVOTAL intervention
        var pivotal = new GrantApplicationIntervention
        {
            GrantApplicationId = app.Id,
            IsPivotal = true,
            InterventionTypeCode = "LEARNERSHIP",
            QualificationTitle = "Aeronautical Component Assembly",
            LearnerCountEmployed = 5,
            LearnerCountUnemployed = 5,
            UnitCost = 40000m
        };
        var addedPivotal = await service.AddApplicationInterventionAsync(app.Id, pivotal, "Officer");

        // Assert 1
        Assert.True(addedPivotal.Id > 0);
        Assert.Equal(400000m, addedPivotal.TotalAmount);

        var refreshedApp = await service.GetApplicationByIdAsync(app.Id);
        Assert.NotNull(refreshedApp);
        Assert.True(refreshedApp.HasPivotalInterventions);
        Assert.False(refreshedApp.HasNonPivotalInterventions);
        Assert.Equal(400000m, refreshedApp.RequestedAmount);

        // Act 2: Add Non-PIVOTAL deliverable
        var nonPivotal = new GrantApplicationIntervention
        {
            GrantApplicationId = app.Id,
            IsPivotal = false,
            InterventionTypeCode = "INFRA_WORKSHOP",
            DeliverableName = "Clean Room Assembly Pod",
            TargetQuantity = 1,
            EstimatedCost = 250000m
        };
        var addedNonPivotal = await service.AddApplicationInterventionAsync(app.Id, nonPivotal, "Officer");

        // Assert 2
        Assert.True(addedNonPivotal.Id > 0);
        Assert.Equal(250000m, addedNonPivotal.TotalAmount);

        refreshedApp = await service.GetApplicationByIdAsync(app.Id);
        Assert.NotNull(refreshedApp);
        Assert.True(refreshedApp.HasPivotalInterventions);
        Assert.True(refreshedApp.HasNonPivotalInterventions);
        Assert.Equal(650000m, refreshedApp.RequestedAmount); // 400k + 250k
    }

    [Fact]
    public async Task RemoveApplicationInterventionAsync_RemovesLineItemAndRecalculatesApplicationTotals()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation { CompanyName = "Marine Engineering", SdlNumber = "L555666777" };
        db.Organisations.Add(org);
        db.InterventionTypes.AddRange(
            new InterventionType { Code = "APPRENTICESHIP", Name = "Apprenticeship", IsPivotal = true, Active = true },
            new InterventionType { Code = "INFRA_WORKSHOP", Name = "TVET Workshop Infrastructure & Equipment", IsPivotal = false, Active = true }
        );
        await db.SaveChangesAsync();

        var app = await service.CreateApplicationAsync(new GrantApplication
        {
            OrganisationId = org.Id,
            ProjectTitle = "Shipbuilding Skills Project",
            GrantTypeCode = "HYBRID",
            Interventions = new List<GrantApplicationIntervention>
            {
                new()
                {
                    IsPivotal = true,
                    InterventionTypeCode = "APPRENTICESHIP",
                    QualificationTitle = "Shipbuilder",
                    LearnerCountEmployed = 10,
                    UnitCost = 50000m,
                    TotalAmount = 500000m
                },
                new()
                {
                    IsPivotal = false,
                    InterventionTypeCode = "INFRA_WORKSHOP",
                    DeliverableName = "Welding Bays Setup",
                    EstimatedCost = 200000m,
                    TotalAmount = 200000m
                }
            }
        });

        var interventions = await service.GetApplicationInterventionsAsync(app.Id);
        Assert.Equal(2, interventions.Count);
        var deliverable = interventions.First(i => !i.IsPivotal);

        // Act: Remove the non-pivotal deliverable
        var removed = await service.RemoveApplicationInterventionAsync(deliverable.Id, "Officer");

        // Assert
        Assert.True(removed);
        var remaining = await service.GetApplicationInterventionsAsync(app.Id);
        Assert.Single(remaining);
        Assert.True(remaining.First().IsPivotal);

        var refreshedApp = await service.GetApplicationByIdAsync(app.Id);
        Assert.NotNull(refreshedApp);
        Assert.True(refreshedApp.HasPivotalInterventions);
        Assert.False(refreshedApp.HasNonPivotalInterventions);
        Assert.Equal(500000m, refreshedApp.RequestedAmount);
    }
}

