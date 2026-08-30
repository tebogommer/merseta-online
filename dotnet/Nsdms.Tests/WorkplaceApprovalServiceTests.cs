using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class WorkplaceApprovalServiceTests
{
    private static NsdmsDbContext CreateInMemoryDbContext()
    {
        var options = new DbContextOptionsBuilder<NsdmsDbContext>()
            .UseInMemoryDatabase(databaseName: Guid.NewGuid().ToString())
            .Options;

        return new NsdmsDbContext(options);
    }

    [Fact]
    public async Task CreateAsync_ValidApproval_CreatesAndAudits()
    {
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new WorkplaceApprovalService(db, audit);

        var org = new Organisation { CompanyName = "Toyota SA", SdlNumber = "L700100200" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var wpa = new WorkplaceApproval
        {
            OrganisationId = org.Id,
            QualificationTitle = "Automotive Motor Mechanic",
            ApprovalStatusCode = "Pending"
        };

        var result = await service.CreateAsync(wpa, "TESTUSER");

        Assert.True(result.Id > 0);
        Assert.StartsWith("WPA-", result.ApprovalNumber);
        Assert.Equal("Pending", result.ApprovalStatusCode);

        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "WorkplaceApproval" && a.RecordId == result.Id);
        Assert.NotNull(auditLog);
        Assert.Equal("Create", auditLog.ActionName);
    }

    [Fact]
    public async Task AddMentorAsync_ValidMentor_AddsToApproval()
    {
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new WorkplaceApprovalService(db, audit);

        var org = new Organisation { CompanyName = "Toyota SA", SdlNumber = "L700100200" };
        var person = new Person { FirstName = "John", LastName = "Doe", RsaIdNumber = "8001015009087" };
        db.Organisations.Add(org);
        db.People.Add(person);
        await db.SaveChangesAsync();

        var wpa = await service.CreateAsync(new WorkplaceApproval
        {
            OrganisationId = org.Id,
            QualificationTitle = "Automotive Motor Mechanic"
        });

        var mentor = new WorkplaceApprovalMentor
        {
            WorkplaceApprovalId = wpa.Id,
            PersonId = person.Id,
            Designation = "Master Artisan",
            YearsExperience = 15,
            IsCertifiedArtisan = true
        };

        var result = await service.AddMentorAsync(mentor, "TESTUSER");

        Assert.True(result.Id > 0);
        Assert.Equal("Master Artisan", result.Designation);

        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "WorkplaceApprovalMentor" && a.RecordId == result.Id);
        Assert.NotNull(auditLog);
        Assert.Equal("AddMentor", auditLog.ActionName);
    }

    [Fact]
    public async Task AddToolItemAsync_ValidTool_AddsToApproval()
    {
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new WorkplaceApprovalService(db, audit);

        var org = new Organisation { CompanyName = "Sasol", SdlNumber = "L800200300" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var wpa = await service.CreateAsync(new WorkplaceApproval
        {
            OrganisationId = org.Id,
            QualificationTitle = "Boilermaker"
        });

        var tool = new WorkplaceApprovalToolList
        {
            WorkplaceApprovalId = wpa.Id,
            ToolName = "Hydraulic Guillotine",
            Category = "Mechanical",
            RequiredQuantity = 2,
            AvailableQuantity = 2
        };

        var result = await service.AddToolItemAsync(tool, "TESTUSER");

        Assert.True(result.Id > 0);
        Assert.True(result.IsCompliant);

        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "WorkplaceApprovalToolList" && a.RecordId == result.Id);
        Assert.NotNull(auditLog);
        Assert.Equal("AddToolItem", auditLog.ActionName);
    }

    [Fact]
    public async Task ApproveWorkplaceAsync_SetsStatusAndExpiry()
    {
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new WorkplaceApprovalService(db, audit);

        var org = new Organisation { CompanyName = "Toyota SA", SdlNumber = "L700100200" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var wpa = await service.CreateAsync(new WorkplaceApproval
        {
            OrganisationId = org.Id,
            QualificationTitle = "Automotive Motor Mechanic",
            ApprovalStatusCode = "Pending"
        });

        var approved = await service.ApproveWorkplaceAsync(wpa.Id, "Fully compliant", "TESTUSER");

        Assert.Equal("Approved", approved.ApprovalStatusCode);
        Assert.NotNull(approved.ApprovalDate);
        Assert.NotNull(approved.ExpiryDate);
        Assert.True(approved.ExpiryDate > DateTime.UtcNow.AddYears(2));
    }
}
