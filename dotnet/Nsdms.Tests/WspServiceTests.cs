using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class WspServiceTests
{
    private static NsdmsDbContext CreateInMemoryDbContext()
    {
        var options = new DbContextOptionsBuilder<NsdmsDbContext>()
            .UseInMemoryDatabase(databaseName: Guid.NewGuid().ToString())
            .Options;

        return new NsdmsDbContext(options);
    }

    [Fact]
    public async Task CreateAsync_GeneratesReferenceNumberAndLogsAudit()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new WspService(db, audit);

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
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new WspService(db, audit);

        var org1 = new Organisation { CompanyName = "Johannesburg Mining", SdlNumber = "L111" };
        var org2 = new Organisation { CompanyName = "Durban Auto", SdlNumber = "L222" };
        db.Organisations.AddRange(org1, org2);
        await db.SaveChangesAsync();

        await service.CreateAsync(new WspSubmission { OrganisationId = org1.Id, FinYear = 2025, ReferenceNumber = "WSP-2025-001" });
        await service.CreateAsync(new WspSubmission { OrganisationId = org2.Id, FinYear = 2026, ReferenceNumber = "WSP-2026-002" });

        // Act
        var results2026 = await service.GetAllAsync(finYear: 2026);
        var searchResults = await service.GetAllAsync(search: "Johannesburg");

        // Assert
        Assert.Single(results2026);
        Assert.Equal("WSP-2026-002", results2026[0].ReferenceNumber);

        Assert.Single(searchResults);
        Assert.Equal("WSP-2025-001", searchResults[0].ReferenceNumber);
    }

    [Fact]
    public async Task AddEmploymentSummaryAsync_CalculatesTotalAndUpdatesSubmission()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new WspService(db, audit);

        var wsp = await service.CreateAsync(new WspSubmission { OrganisationId = 1, FinYear = 2026, EmployeeCount = 0 });

        var summary = new WspEmploymentSummary
        {
            WspSubmissionId = wsp.Id,
            OccupationalCategory = "Technicians and Trades Workers",
            MaleAfrican = 10,
            FemaleAfrican = 8,
            MaleColoured = 4,
            FemaleColoured = 3,
            MaleIndian = 2,
            FemaleIndian = 1,
            MaleWhite = 5,
            FemaleWhite = 2,
            DisabledCount = 1
        };

        // Act
        var added = await service.AddEmploymentSummaryAsync(summary, "DataOfficer");

        // Assert
        Assert.Equal(35, added.TotalEmployees); // 10+8+4+3+2+1+5+2 = 35

        var updatedWsp = await db.WspSubmissions.FindAsync(wsp.Id);
        Assert.NotNull(updatedWsp);
        Assert.Equal(35, updatedWsp.EmployeeCount);

        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "WspEmploymentSummary");
        Assert.NotNull(auditLog);
    }

    [Fact]
    public async Task AddTrainingPlanAsync_UpdatesPlannedBudget()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new WspService(db, audit);

        var wsp = await service.CreateAsync(new WspSubmission { OrganisationId = 1, FinYear = 2026, PlannedTrainingBudget = 0m });

        var plan = new WspTrainingPlan
        {
            WspSubmissionId = wsp.Id,
            ProgrammeTypeCode = "Learnership",
            NqfLevel = 4,
            BeneficiaryCount = 10,
            EstimatedCost = 85000m
        };

        // Act
        var added = await service.AddTrainingPlanAsync(plan, "Planner");

        // Assert
        Assert.True(added.Id > 0);

        var updatedWsp = await db.WspSubmissions.FindAsync(wsp.Id);
        Assert.NotNull(updatedWsp);
        Assert.Equal(85000m, updatedWsp.PlannedTrainingBudget);
    }

    [Fact]
    public async Task CalculateMandatoryGrantClaimAsync_WithLevyLines_CalculatesCorrectMandatory20Percent()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new WspService(db, audit);

        var org = new Organisation { CompanyName = "National Logistics Pty Ltd", SdlNumber = "L789123456" };
        db.Organisations.Add(org);

        var levyFile = new LevyFile { FileName = "SARS_2026.csv", StatusCode = "Imported" };
        db.LevyFiles.Add(levyFile);
        await db.SaveChangesAsync();

        // Add SARS levy file lines totaling R100,000 SDL -> Mandatory (20%) is R20,000
        db.LevyFileLines.Add(new LevyFileLine
        {
            LevyFileId = levyFile.Id,
            SdlNumber = "L789123456",
            SchemeYear = "2026",
            TotalLevyAmount = 100000m,
            MandatoryLevyAmount = 20000m,
            DiscretionaryLevyAmount = 49500m,
            AdminLevyAmount = 10500m,
            QctoLevyAmount = 500m
        });
        await db.SaveChangesAsync();

        var wsp = await service.CreateAsync(new WspSubmission
        {
            OrganisationId = org.Id,
            FinYear = 2026,
            PlannedTrainingBudget = 50000m
        });

        // Act
        var claim = await service.CalculateMandatoryGrantClaimAsync(wsp.Id);

        // Assert
        Assert.Equal(20000m, claim);
    }

    [Fact]
    public async Task DeleteAsync_RemovesWspSubmission()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new WspService(db, audit);

        var wsp = await service.CreateAsync(new WspSubmission { OrganisationId = 1, FinYear = 2026 });

        // Act
        var deleted = await service.DeleteAsync(wsp.Id);

        // Assert
        Assert.True(deleted);
        var inDb = await db.WspSubmissions.FindAsync(wsp.Id);
        Assert.Null(inDb);
    }
}
