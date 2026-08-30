using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class WspServiceTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, WspService service) CreateTestContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var service = new WspService(factory, audit);
        return (factory, db, audit, service);
    }

    [Fact]
    public async Task CreateAsync_GeneratesReferenceNumberAndLogsAudit()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation { CompanyName = "Steel Works Corp", SdlNumber = "L500600700" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var wsp = new WspSubmission
        {
            OrganisationId = org.Id,
            FinYear = 2026,
            PlannedTrainingBudget = 250000m,
            EmployeeCount = 50
        };

        // Act
        var created = await service.CreateAsync(wsp, "WspOfficer");

        // Assert
        Assert.True(created.Id > 0);
        Assert.StartsWith("WSP-2026-", created.ReferenceNumber);
        Assert.Equal("WspOfficer", created.CreatedBy);

        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "WspSubmission" && a.RecordId == created.Id);
        Assert.NotNull(auditLog);
        Assert.Equal("Create", auditLog.ActionName);
    }

    [Fact]
    public async Task GetAllAsync_FiltersByFinYearAndSearch()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org1 = new Organisation { CompanyName = "Apex Metal", SdlNumber = "L111" };
        var org2 = new Organisation { CompanyName = "Zenith Plastics", SdlNumber = "L222" };
        db.Organisations.AddRange(org1, org2);
        await db.SaveChangesAsync();

        db.WspSubmissions.AddRange(
            new WspSubmission { OrganisationId = org1.Id, FinYear = 2025, ReferenceNumber = "WSP-2025-001" },
            new WspSubmission { OrganisationId = org1.Id, FinYear = 2026, ReferenceNumber = "WSP-2026-001" },
            new WspSubmission { OrganisationId = org2.Id, FinYear = 2026, ReferenceNumber = "WSP-2026-002" }
        );
        await db.SaveChangesAsync();

        // Act
        var year2026Only = await service.GetAllAsync(finYear: 2026);
        var searchZenith = await service.GetAllAsync(search: "Zenith");

        // Assert
        Assert.Equal(2, year2026Only.Count);
        Assert.Single(searchZenith);
        Assert.Equal("WSP-2026-002", searchZenith[0].ReferenceNumber);
    }

    [Fact]
    public async Task AddTrainingPlanAsync_AddsPlanAndRecalculatesBudget()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation { CompanyName = "Harmony Mining", SdlNumber = "L333" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var wsp = await service.CreateAsync(new WspSubmission
        {
            OrganisationId = org.Id,
            FinYear = 2026,
            PlannedTrainingBudget = 0m
        });

        // Act - Add Training Plan
        var plan = new WspTrainingPlan
        {
            WspSubmissionId = wsp.Id,
            ProgrammeTypeCode = "Apprenticeship",
            NqfLevel = 4,
            BeneficiaryCount = 10,
            EstimatedCost = 150000m
        };

        var added = await service.AddTrainingPlanAsync(plan, "Admin");

        // Assert
        Assert.True(added.Id > 0);
        var plans = await service.GetTrainingPlansAsync(wsp.Id);
        Assert.Single(plans);
        Assert.Equal(150000m, plans[0].EstimatedCost);
    }

    [Fact]
    public async Task CalculateMandatoryGrant_Returns20Percent()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        // Act
        var mg = service.CalculateMandatoryGrant(100000m);

        // Assert
        Assert.Equal(20000m, mg);
    }

    [Fact]
    public async Task DeleteAsync_DeletesSubmissionAndLogsAudit()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation { CompanyName = "Temp Org", SdlNumber = "L999" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var wsp = await service.CreateAsync(new WspSubmission { OrganisationId = org.Id, FinYear = 2026 });

        // Act
        var deleted = await service.DeleteAsync(wsp.Id, "AdminDeleter");

        // Assert
        Assert.True(deleted);
        var inDb = await service.GetByIdAsync(wsp.Id);
        Assert.Null(inDb);
    }
}
