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
}
