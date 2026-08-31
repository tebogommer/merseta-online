using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class WorkplaceApprovalServiceTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, WorkplaceApprovalService service) CreateTestContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var config = new ConfigurationBuilder().AddInMemoryCollection(new Dictionary<string, string?>()).Build();
        var sysConfig = new SystemConfigurationService(factory, config, audit);
        var ratioEngine = new MentorRatioPolicyEngine(factory, audit, sysConfig);
        var service = new WorkplaceApprovalService(factory, audit, ratioEngine);
        return (factory, db, audit, service);
    }

    [Fact]
    public async Task CreateAsync_ValidApproval_CreatesAndAudits()
    {
        var (factory, db, audit, service) = CreateTestContext();

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
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation { CompanyName = "Toyota SA", SdlNumber = "L700100200" };
        var person = new Person { FirstName = "John", LastName = "Doe", RsaIdNumber = "8001015009087" };
        db.Organisations.Add(org);
        db.People.Add(person);
        await db.SaveChangesAsync();

        var wpa = await service.CreateAsync(new WorkplaceApproval
        {
            OrganisationId = org.Id,
            QualificationTitle = "Welder"
        });

        var mentor = new WorkplaceApprovalMentor
        {
            WorkplaceApprovalId = wpa.Id,
            PersonId = person.Id,
            Designation = "Master Welder",
            ArtisanTradeNumber = "CERT-9988",
            YearsExperience = 10,
            IsCertifiedArtisan = true
        };

        var result = await service.AddMentorAsync(mentor, "TESTUSER");

        Assert.True(result.Id > 0);
        Assert.Equal("Master Welder", result.Designation);

        var loaded = await service.GetByIdAsync(wpa.Id);
        Assert.NotNull(loaded);
        Assert.Single(loaded.Mentors);
        Assert.Equal("John", loaded.Mentors.First().Person?.FirstName);
    }

    [Fact]
    public async Task AddToolItemAsync_ValidTool_AddsToApproval()
    {
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation { CompanyName = "Toyota SA", SdlNumber = "L700100200" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var wpa = await service.CreateAsync(new WorkplaceApproval
        {
            OrganisationId = org.Id,
            QualificationTitle = "Toolmaker"
        });

        var tool = new WorkplaceApprovalToolList
        {
            WorkplaceApprovalId = wpa.Id,
            ToolName = "Hydraulic Press Machine",
            Category = "Mechanical",
            RequiredQuantity = 2,
            AvailableQuantity = 2,
            Remarks = "Excellent condition"
        };

        var result = await service.AddToolItemAsync(tool, "TESTUSER");

        Assert.True(result.Id > 0);
        Assert.Equal("Hydraulic Press Machine", result.ToolName);
        Assert.True(result.IsCompliant);

        var loaded = await service.GetByIdAsync(wpa.Id);
        Assert.NotNull(loaded);
        Assert.Single(loaded.ToolItems);
        Assert.Equal("Hydraulic Press Machine", loaded.ToolItems.First().ToolName);
    }

    [Fact]
    public async Task ApproveWorkplaceAsync_ValidTransition_UpdatesAndAudits()
    {
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation { CompanyName = "Toyota SA", SdlNumber = "L700100200" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var wpa = await service.CreateAsync(new WorkplaceApproval
        {
            OrganisationId = org.Id,
            QualificationTitle = "Diesel Mechanic",
            ApprovalStatusCode = "Pending"
        });

        var updated = await service.ApproveWorkplaceAsync(wpa.Id, "All safety criteria met", "MANAGER");

        Assert.Equal("Approved", updated.ApprovalStatusCode);
        Assert.NotNull(updated.ApprovalDate);

        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "WorkplaceApproval" && a.ActionName == "ApproveWorkplace");
        Assert.NotNull(auditLog);
        Assert.Equal("MANAGER", auditLog.Actor);
    }

    #region 360-Degree Relational Tests

    [Fact]
    public async Task GetPlacedLearnersAsync_ReturnsLearnersUnderApprovedScope()
    {
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation { CompanyName = "Nissan SA", SdlNumber = "L333444555" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var wpa = await service.CreateAsync(new WorkplaceApproval
        {
            OrganisationId = org.Id,
            QualificationTitle = "Automotive Electrician",
            ApprovalStatusCode = "Approved"
        });

        var person = new Person { FirstName = "Kgothatso", LastName = "Mokoena", RsaIdNumber = "0404045009087" };
        db.People.Add(person);
        await db.SaveChangesAsync();

        db.CompanyLearners.Add(new CompanyLearner
        {
            PersonId = person.Id,
            OrganisationId = org.Id,
            LearnerContractNumber = "LC-NISSAN-001",
            QualificationTitle = "Automotive Electrician",
            LearningProgrammeTypeCode = "01",
            EnrolmentStatusCode = "Active"
        });
        await db.SaveChangesAsync();

        var learners = await service.GetPlacedLearnersAsync(wpa.Id);

        Assert.Single(learners);
        Assert.Equal("Kgothatso Mokoena", learners[0].FullName);
        Assert.Equal("Automotive Electrician", learners[0].QualificationTitle);
    }

    [Fact]
    public async Task GetVerificationVisitsAsync_ReturnsSiteVisits()
    {
        var (factory, db, audit, service) = CreateTestContext();

        var contact = new Person { FirstName = "Mandla", LastName = "Zulu", Email = "mandla@vw.co.za" };
        db.People.Add(contact);
        await db.SaveChangesAsync();

        var org = new Organisation { CompanyName = "VW Kariega", SdlNumber = "L111222333", PrimaryContactPersonId = contact.Id };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var wpa = await service.CreateAsync(new WorkplaceApproval
        {
            OrganisationId = org.Id,
            QualificationTitle = "Fitter & Turner",
            ContactPersonId = contact.Id
        });

        db.Visits.Add(new Visit
        {
            OrganisationId = org.Id,
            ContactPersonId = contact.Id,
            VisitTypeCode = "WorkplaceApproval",
            VisitStatusCode = "Completed",
            VisitDate = DateTime.Today.AddDays(-14),
            Location = "VW Plant 2",
            Purpose = "Workplace Approval Pre-inspection",
            OutcomeNotes = "Tooling compliant and safety gear inspected"
        });
        await db.SaveChangesAsync();

        var visits = await service.GetVerificationVisitsAsync(wpa.Id);

        Assert.Single(visits);
        Assert.Equal("Mandla Zulu", visits[0].ContactPersonName);
        Assert.Equal("VW Plant 2", visits[0].Location);
    }

    #endregion
}
