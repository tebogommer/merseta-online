using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class OrganisationServiceTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, OrganisationService service) CreateTestContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var service = new OrganisationService(factory, audit);
        return (factory, db, audit, service);
    }

    #region Organisation CRUD Tests

    [Fact]
    public async Task CreateAsync_CreatesOrganisationAndLogsAudit()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation
        {
            CompanyName = "Engineering Innovations Pty Ltd",
            TradingName = "Eng Innovations",
            SdlNumber = "L123456789",
            RegistrationNumber = "2020/123456/07",
            TaxNumber = "9876543210",
            CategoryCode = "Levy-Paying",
            StatusCode = "Active",
            ProvinceCode = "GP",
            SectorCode = "Metal",
            ChamberCode = "Chamber1"
        };

        // Act
        var created = await service.CreateAsync(org, "AdminAuthor");

        // Assert
        Assert.True(created.Id > 0);
        Assert.Equal("AdminAuthor", created.CreatedBy);

        var inDb = await db.Organisations.FindAsync(created.Id);
        Assert.NotNull(inDb);
        Assert.Equal("Engineering Innovations Pty Ltd", inDb.CompanyName);

        // Verify audit log
        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "Organisation" && a.RecordId == created.Id);
        Assert.NotNull(auditLog);
        Assert.Equal("Create", auditLog.ActionName);
        Assert.Equal("AdminAuthor", auditLog.Actor);
        Assert.Contains("Engineering Innovations Pty Ltd", auditLog.MetadataJson);
    }

    [Fact]
    public async Task GetAllAsync_ReturnsAllOrganisationsWithPrimaryContact()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var contact = new Person
        {
            FirstName = "John",
            LastName = "Doe",
            RsaIdNumber = "8001015009087",
            Email = "john.doe@test.com"
        };
        db.People.Add(contact);
        await db.SaveChangesAsync();

        db.Organisations.AddRange(
            new Organisation { CompanyName = "Org Alpha", SdlNumber = "L111111111", PrimaryContactPersonId = contact.Id },
            new Organisation { CompanyName = "Org Beta", SdlNumber = "L222222222" }
        );
        await db.SaveChangesAsync();

        // Act
        var list = await service.GetAllAsync();

        // Assert
        Assert.Equal(2, list.Count);
        var alpha = list.FirstOrDefault(o => o.CompanyName == "Org Alpha");
        Assert.NotNull(alpha);
        Assert.NotNull(alpha.PrimaryContactPerson);
        Assert.Equal("John", alpha.PrimaryContactPerson.FirstName);
    }

    [Fact]
    public async Task GetAllAsync_WithSearch_FiltersByCompanyNameOrSdl()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        db.Organisations.AddRange(
            new Organisation { CompanyName = "Steel Dynamics Ltd", SdlNumber = "L123456789" },
            new Organisation { CompanyName = "Auto Care Motors", SdlNumber = "L987654321" },
            new Organisation { CompanyName = "Polymer Plastic Works", SdlNumber = "L555555555" }
        );
        await db.SaveChangesAsync();

        // Act
        var searchByName = await service.GetAllAsync("Auto Care");
        var searchBySdl = await service.GetAllAsync("555555");

        // Assert
        Assert.Single(searchByName);
        Assert.Equal("Auto Care Motors", searchByName[0].CompanyName);

        Assert.Single(searchBySdl);
        Assert.Equal("Polymer Plastic Works", searchBySdl[0].CompanyName);
    }

    [Fact]
    public async Task GetByIdAsync_ReturnsOrganisationWithAllRelatedEntities()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var contact = new Person { FirstName = "Sarah", LastName = "Connor", RsaIdNumber = "8505050001088", Email = "sarah@test.com" };
        db.People.Add(contact);
        await db.SaveChangesAsync();

        var org = new Organisation { CompanyName = "Cyberdyne Systems", SdlNumber = "L999888777", PrimaryContactPersonId = contact.Id };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        db.Visits.Add(new Visit { OrganisationId = org.Id, ContactPersonId = contact.Id, Title = "Annual Visit", VisitDate = DateTime.Today });
        db.WspSubmissions.Add(new WspSubmission { OrganisationId = org.Id, FinYear = 2026, ReferenceNumber = "WSP-2026-001" });
        db.GrantApplications.Add(new GrantApplication { OrganisationId = org.Id, ProjectTitle = "Skills Dev", ApplicationNumber = "GA-001" });
        db.OrganisationContacts.Add(new OrganisationContact { OrganisationId = org.Id, PersonId = contact.Id, ContactType = "SDF" });
        db.OrganisationSites.Add(new OrganisationSite { OrganisationId = org.Id, SiteName = "Factory 1" });
        await db.SaveChangesAsync();

        // Act
        var result = await service.GetByIdAsync(org.Id);

        // Assert
        Assert.NotNull(result);
        Assert.Equal("Cyberdyne Systems", result.CompanyName);
        Assert.Single(result.Visits);
        Assert.Single(result.WspSubmissions);
        Assert.Single(result.GrantApplications);
        Assert.Single(result.Contacts);
        Assert.Single(result.Sites);
    }

    [Fact]
    public async Task UpdateAsync_UpdatesFieldsAndLogsAudit()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation
        {
            CompanyName = "Original Name",
            TradingName = "Original Trading",
            SdlNumber = "L100000001",
            StatusCode = "Pending"
        };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        // Act
        org.CompanyName = "Updated Name Pty Ltd";
        org.TradingName = "Updated Trading";
        org.StatusCode = "Active";
        var updated = await service.UpdateAsync(org, "EditorUser");

        // Assert
        Assert.Equal("Updated Name Pty Ltd", updated.CompanyName);
        Assert.Equal("Active", updated.StatusCode);
        Assert.Equal("EditorUser", updated.ModifiedBy);
        Assert.NotNull(updated.ModifiedAt);

        // Verify audit log
        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "Organisation" && a.RecordId == org.Id && a.ActionName == "Update");
        Assert.NotNull(auditLog);
        Assert.Equal("EditorUser", auditLog.Actor);
    }

    [Fact]
    public async Task DeleteAsync_DeletesOrganisationAndLogsAudit()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation { CompanyName = "To Delete", SdlNumber = "L999999999" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        // Act
        var result = await service.DeleteAsync(org.Id, "DeleterUser");

        // Assert
        Assert.True(result);
        var inDb = await service.GetByIdAsync(org.Id);
        Assert.Null(inDb);

        // Verify audit log
        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "Organisation" && a.RecordId == org.Id && a.ActionName == "Delete");
        Assert.NotNull(auditLog);
        Assert.Equal("DeleterUser", auditLog.Actor);
    }

    #endregion

    #region Contacts Management Tests

    [Fact]
    public async Task AddContactAsync_EntityOverload_AddsContactAndLogsAudit()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var person = new Person { FirstName = "Alice", LastName = "Smith", RsaIdNumber = "9001010001081", Email = "alice@test.com" };
        var org = new Organisation { CompanyName = "Apex Ltd", SdlNumber = "L100000002" };
        db.People.Add(person);
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var contact = new OrganisationContact
        {
            OrganisationId = org.Id,
            PersonId = person.Id,
            ContactType = "SDF",
            IsPrimary = true
        };

        // Act
        var added = await service.AddContactAsync(contact, "ContactManager");

        // Assert
        Assert.True(added.Id > 0);
        Assert.Equal("ContactManager", added.CreatedBy);

        var contacts = await service.GetContactsAsync(org.Id);
        Assert.Single(contacts);
        Assert.Equal("Alice", contacts[0].Person?.FirstName);
    }

    [Fact]
    public async Task AddContactAsync_ParamOverload_AddsContactCorrectly()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var person = new Person { FirstName = "Bob", LastName = "Jones", RsaIdNumber = "9102020002082", Email = "bob@test.com" };
        var org = new Organisation { CompanyName = "Beta Corp", SdlNumber = "L100000003" };
        db.People.Add(person);
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        // Act
        var added = await service.AddContactAsync(org.Id, person.Id, "Finance", true, "ParamAuthor");

        // Assert
        Assert.NotNull(added);
        Assert.Equal("Finance", added.ContactType);
        Assert.True(added.IsPrimary);
        Assert.Equal("ParamAuthor", added.CreatedBy);
    }

    [Fact]
    public async Task AddContactAsync_WhenIsPrimaryTrue_UnsetsPreviousPrimaryContact()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var person1 = new Person { FirstName = "Person1", LastName = "Last1", RsaIdNumber = "8001010001081", Email = "p1@test.com" };
        var person2 = new Person { FirstName = "Person2", LastName = "Last2", RsaIdNumber = "8001010002082", Email = "p2@test.com" };
        var org = new Organisation { CompanyName = "Primary Org", SdlNumber = "L100000004" };
        db.People.AddRange(person1, person2);
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        // Add first contact as Primary
        await service.AddContactAsync(org.Id, person1.Id, "SDF", true, "Admin");

        // Act: Add second contact as Primary
        await service.AddContactAsync(org.Id, person2.Id, "CEO", true, "Admin");

        // Assert
        var contacts = await service.GetContactsAsync(org.Id);
        Assert.Equal(2, contacts.Count);
        var c1 = contacts.First(c => c.PersonId == person1.Id);
        var c2 = contacts.First(c => c.PersonId == person2.Id);

        Assert.False(c1.IsPrimary);
        Assert.True(c2.IsPrimary);
    }

    [Fact]
    public async Task RemoveContactAsync_ByContactId_RemovesContactAndLogsAudit()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var person = new Person { FirstName = "Charlie", LastName = "Brown", RsaIdNumber = "9203030003083", Email = "charlie@test.com" };
        var org = new Organisation { CompanyName = "Gamma Works", SdlNumber = "L100000005" };
        db.People.Add(person);
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var contact = await service.AddContactAsync(org.Id, person.Id, "HR", false, "Admin");

        // Act
        var result = await service.RemoveContactAsync(contact.Id, "Deleter");

        // Assert
        Assert.True(result);
        var contacts = await service.GetContactsAsync(org.Id);
        Assert.Empty(contacts);

        // Verify audit log
        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "OrganisationContact" && a.ActionName == "RemoveContact");
        Assert.NotNull(auditLog);
        Assert.Equal("Deleter", auditLog.Actor);
    }

    [Fact]
    public async Task RemoveContactAsync_ByOrgAndContactId_RemovesContactSuccessfully()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var person = new Person { FirstName = "David", LastName = "Miller", RsaIdNumber = "9304040004084", Email = "david@test.com" };
        var org = new Organisation { CompanyName = "Delta Fabrications", SdlNumber = "L100000006" };
        db.People.Add(person);
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var contact = await service.AddContactAsync(org.Id, person.Id, "Legal", false, "Admin");

        // Act
        var result = await service.RemoveContactAsync(org.Id, contact.Id, "OrgAdmin");

        // Assert
        Assert.True(result);
        var contacts = await service.GetContactsAsync(org.Id);
        Assert.Empty(contacts);
    }

    #endregion

    #region Sites Management Tests

    [Fact]
    public async Task AddSiteAsync_AddsSiteAndLogsAudit()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation { CompanyName = "Epsilon Logistics", SdlNumber = "L100000007" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var site = new OrganisationSite
        {
            OrganisationId = org.Id,
            SiteName = "Centurion Distribution Hub",
            PhysicalAddress = "12 Industrial Road",
            City = "Centurion",
            ProvinceCode = "GP",
            PostalCode = "0157",
            IsHeadOffice = true
        };

        // Act
        var added = await service.AddSiteAsync(site, "SiteManager");

        // Assert
        Assert.True(added.Id > 0);
        Assert.Equal("SiteManager", added.CreatedBy);

        var sites = await service.GetSitesAsync(org.Id);
        Assert.Single(sites);
        Assert.Equal("Centurion Distribution Hub", sites[0].SiteName);
        Assert.True(sites[0].IsHeadOffice);

        // Verify audit log
        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "OrganisationSite" && a.ActionName == "AddSite");
        Assert.NotNull(auditLog);
        Assert.Equal("SiteManager", auditLog.Actor);
    }

    [Fact]
    public async Task AddSiteAsync_WhenIsHeadOfficeTrue_UnsetsPreviousHeadOffice()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation { CompanyName = "Zeta Auto", SdlNumber = "L100000008" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var site1 = new OrganisationSite { OrganisationId = org.Id, SiteName = "Site 1", IsHeadOffice = true };
        await service.AddSiteAsync(site1, "Admin");

        // Act: Add second site as Head Office
        var site2 = new OrganisationSite { OrganisationId = org.Id, SiteName = "Site 2", IsHeadOffice = true };
        await service.AddSiteAsync(site2, "Admin");

        // Assert
        var sites = await service.GetSitesAsync(org.Id);
        Assert.Equal(2, sites.Count);
        var s1 = sites.First(s => s.SiteName == "Site 1");
        var s2 = sites.First(s => s.SiteName == "Site 2");

        Assert.False(s1.IsHeadOffice);
        Assert.True(s2.IsHeadOffice);
    }

    [Fact]
    public async Task UpdateSiteAsync_UpdatesSiteDetailsAndLogsAudit()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation { CompanyName = "Eta Engineering", SdlNumber = "L100000009" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var site = await service.AddSiteAsync(new OrganisationSite
        {
            OrganisationId = org.Id,
            SiteName = "Old Site Name",
            City = "Durban",
            ProvinceCode = "KZN"
        }, "Admin");

        // Act
        site.SiteName = "Durban Marine Facility";
        site.PhysicalAddress = "Port of Durban Pier 2";
        var updated = await service.UpdateSiteAsync(site, "SiteEditor");

        // Assert
        Assert.Equal("Durban Marine Facility", updated.SiteName);
        Assert.Equal("SiteEditor", updated.ModifiedBy);
        Assert.NotNull(updated.ModifiedAt);

        // Verify audit log
        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "OrganisationSite" && a.ActionName == "UpdateSite");
        Assert.NotNull(auditLog);
        Assert.Equal("SiteEditor", auditLog.Actor);
    }

    [Fact]
    public async Task RemoveSiteAsync_RemovesSiteAndLogsAudit()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation { CompanyName = "Theta Mining", SdlNumber = "L100000010" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var site = await service.AddSiteAsync(new OrganisationSite
        {
            OrganisationId = org.Id,
            SiteName = "Rustenburg Shaft 4",
            City = "Rustenburg",
            ProvinceCode = "NW"
        }, "Admin");

        // Act
        var result = await service.RemoveSiteAsync(site.Id, "DeleterUser");

        // Assert
        Assert.True(result);
        var sites = await service.GetSitesAsync(org.Id);
        Assert.Empty(sites);

        // Verify audit log
        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "OrganisationSite" && a.ActionName == "RemoveSite");
        Assert.NotNull(auditLog);
        Assert.Equal("DeleterUser", auditLog.Actor);
    }

    #endregion

    #region 360-Degree Relational Tab Tests

    [Fact]
    public async Task GetLinkedLearnersAsync_ReturnsAllEnrolledLearners()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation { CompanyName = "Toyota SA Motors", SdlNumber = "L100000099" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var person = new Person { FirstName = "Thabo", LastName = "Mokoena", RsaIdNumber = "9901015009087" };
        db.People.Add(person);
        await db.SaveChangesAsync();

        var learner = new CompanyLearner
        {
            OrganisationId = org.Id,
            PersonId = person.Id,
            LearnerContractNumber = "CON-2026-001",
            LearningProgrammeTypeCode = "01",
            QualificationTitle = "Automotive Electrician",
            NqfLevel = 4,
            EnrolmentStatusId = "01"
        };
        db.CompanyLearners.Add(learner);
        await db.SaveChangesAsync();

        // Act
        var result = await service.GetLinkedLearnersAsync(org.Id);

        // Assert
        Assert.Single(result);
        Assert.Equal("Thabo Mokoena", result[0].LearnerFullName);
        Assert.Equal("9901015009087", result[0].RsaIdNumber);
        Assert.Equal("Apprenticeship", result[0].LearningProgrammeTypeName);
        Assert.Equal("Automotive Electrician", result[0].QualificationTitle);
    }

    [Fact]
    public async Task GetGrantMoasAndApplicationsAsync_ReturnsGrantsAndDisbursements()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation { CompanyName = "BMW Rosslyn", SdlNumber = "L200000099" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var app = new GrantApplication
        {
            OrganisationId = org.Id,
            ApplicationNumber = "DG-2026-001",
            ProjectTitle = "Apprentice Skills Initiative",
            RequestedAmount = 500000m,
            ApprovedAmount = 450000m,
            ApplicationStatusCode = "Approved"
        };
        db.GrantApplications.Add(app);
        await db.SaveChangesAsync();

        var moa = new GrantMoa
        {
            GrantApplicationId = app.Id,
            MoaNumber = "MOA-2026-001",
            TotalContractValue = 450000m,
            MoaStatusCode = "Active",
            ContractStartDate = DateTime.UtcNow
        };
        db.GrantMoas.Add(moa);
        await db.SaveChangesAsync();

        // Act
        var result = await service.GetGrantMoasAndApplicationsAsync(org.Id);

        // Assert
        Assert.Single(result);
        Assert.Equal("MOA-2026-001", result[0].ApplicationOrMoaNumber);
        Assert.Equal(450000m, result[0].ApprovedAmount);
        Assert.Equal("Active", result[0].StatusCode);
    }

    [Fact]
    public async Task GetWorkplaceApprovalsAsync_ReturnsApprovalsWithCounts()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation { CompanyName = "Ford Motor Co", SdlNumber = "L300000099" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var wpa = new WorkplaceApproval
        {
            OrganisationId = org.Id,
            ApprovalNumber = "WPA-2026-001",
            QualificationTitle = "Fitter and Turner",
            ApprovalStatusCode = "Approved",
            InspectionDate = DateTime.UtcNow
        };
        db.WorkplaceApprovals.Add(wpa);
        await db.SaveChangesAsync();

        // Act
        var result = await service.GetWorkplaceApprovalsAsync(org.Id);

        // Assert
        Assert.Single(result);
        Assert.Equal("WPA-2026-001", result[0].ApprovalNumber);
        Assert.Equal("Fitter and Turner", result[0].QualificationTitle);
        Assert.Equal("Approved", result[0].ApprovalStatusCode);
    }

    [Fact]
    public async Task TrainingCommittee_AddAndRemoveMember_WorksWithAudit()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation { CompanyName = "Scaw Metals", SdlNumber = "L400000099" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var person = new Person { FirstName = "Sipho", LastName = "Ndlovu", RsaIdNumber = "8501015009087" };
        db.People.Add(person);
        await db.SaveChangesAsync();

        // Act - Add
        var member = await service.AddTrainingCommitteeMemberAsync(org.Id, person.Id, "UnionRepresentative", "NUMSA", "AdminUser");

        // Assert - Add
        Assert.NotNull(member);
        Assert.Equal("Sipho Ndlovu", member.PersonFullName);
        Assert.Equal("NUMSA", member.Constituency);

        var list = await service.GetTrainingCommitteeMembersAsync(org.Id);
        Assert.Single(list);

        // Act - Remove
        var removed = await service.RemoveTrainingCommitteeMemberAsync(member.MemberId, "AdminUser");
        Assert.True(removed);

        var listAfter = await service.GetTrainingCommitteeMembersAsync(org.Id);
        Assert.Empty(listAfter);
    }

    #endregion
}
