using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class OrganisationServiceTests
{
    private static NsdmsDbContext CreateInMemoryDbContext()
    {
        var options = new DbContextOptionsBuilder<NsdmsDbContext>()
            .UseInMemoryDatabase(databaseName: Guid.NewGuid().ToString())
            .Options;

        return new NsdmsDbContext(options);
    }

    #region Organisation CRUD Tests

    [Fact]
    public async Task CreateAsync_CreatesOrganisationAndLogsAudit()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new OrganisationService(db, audit);

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
    public async Task GetByIdAsync_ExistingOrganisation_ReturnsWithRelatedEntities()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new OrganisationService(db, audit);

        var person = new Person { FirstName = "Sarah", LastName = "Connor", Email = "sarah@cyberdyne.co.za" };
        db.People.Add(person);
        await db.SaveChangesAsync();

        var org = new Organisation
        {
            CompanyName = "Cyberdyne Systems SA",
            SdlNumber = "L111222333",
            PrimaryContactPersonId = person.Id
        };
        await service.CreateAsync(org, "System");

        await service.AddContactAsync(org.Id, person.Id, "CEO", isPrimary: true, currentUsername: "System");

        // Act
        var result = await service.GetByIdAsync(org.Id);

        // Assert
        Assert.NotNull(result);
        Assert.Equal("Cyberdyne Systems SA", result.CompanyName);
        Assert.NotNull(result.PrimaryContactPerson);
        Assert.Equal("Sarah", result.PrimaryContactPerson.FirstName);
        Assert.Single(result.Contacts);
    }

    [Fact]
    public async Task GetByIdAsync_NonExistentOrganisation_ReturnsNull()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new OrganisationService(db, audit);

        // Act
        var result = await service.GetByIdAsync(9999);

        // Assert
        Assert.Null(result);
    }

    [Theory]
    [InlineData("Apex", 1)]
    [InlineData("L999111222", 1)]
    [InlineData("2021/999888/07", 1)]
    [InlineData("9000111222", 1)]
    [InlineData("UnknownCompany", 0)]
    public async Task GetAllAsync_WithSearchQuery_FiltersOrganisations(string search, int expectedCount)
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new OrganisationService(db, audit);

        await service.CreateAsync(new Organisation
        {
            CompanyName = "Apex Manufacturing Ltd",
            TradingName = "Apex Tools",
            SdlNumber = "L999111222",
            RegistrationNumber = "2021/999888/07",
            TaxNumber = "9000111222"
        });

        await service.CreateAsync(new Organisation
        {
            CompanyName = "Beta Automotive",
            TradingName = "Beta Motors",
            SdlNumber = "L888777666",
            RegistrationNumber = "2019/555444/07",
            TaxNumber = "8000333444"
        });

        // Act
        var results = await service.GetAllAsync(search);

        // Assert
        Assert.Equal(expectedCount, results.Count);
    }

    [Fact]
    public async Task UpdateAsync_UpdatesOrganisationAndLogsAudit()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new OrganisationService(db, audit);

        var org = await service.CreateAsync(new Organisation
        {
            CompanyName = "Original Name Pty Ltd",
            SdlNumber = "L123000111",
            BankName = "Standard Bank"
        }, "Creator");

        // Act
        var updateModel = new Organisation
        {
            Id = org.Id,
            CompanyName = "Updated Name Pty Ltd",
            TradingName = "Updated Trading",
            SdlNumber = org.SdlNumber,
            BankName = "First National Bank",
            BankAccountNumber = "62000000000"
        };

        var updated = await service.UpdateAsync(updateModel, "UpdaterUser");

        // Assert
        Assert.Equal("Updated Name Pty Ltd", updated.CompanyName);
        Assert.Equal("First National Bank", updated.BankName);
        Assert.Equal("UpdaterUser", updated.ModifiedBy);
        Assert.NotNull(updated.ModifiedAt);

        // Verify audit log
        var updateAudit = await db.AuditLogs
            .FirstOrDefaultAsync(a => a.EntityName == "Organisation" && a.RecordId == org.Id && a.ActionName == "Update");
        Assert.NotNull(updateAudit);
        Assert.Equal("UpdaterUser", updateAudit.Actor);
        Assert.Contains("Original Name Pty Ltd", updateAudit.MetadataJson);
        Assert.Contains("Updated Name Pty Ltd", updateAudit.MetadataJson);
    }

    [Fact]
    public async Task UpdateAsync_NonExistentOrganisation_ThrowsKeyNotFoundException()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new OrganisationService(db, audit);

        var org = new Organisation { Id = 888, CompanyName = "Ghost Org" };

        // Act & Assert
        await Assert.ThrowsAsync<KeyNotFoundException>(() => service.UpdateAsync(org, "Admin"));
    }

    [Fact]
    public async Task SaveAsync_NewOrganisation_CallsCreate()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new OrganisationService(db, audit);

        var org = new Organisation { Id = 0, CompanyName = "New Co", SdlNumber = "L000111222" };

        // Act
        var saved = await service.SaveAsync(org, "Admin");

        // Assert
        Assert.True(saved.Id > 0);
    }

    [Fact]
    public async Task SaveAsync_ExistingOrganisation_CallsUpdate()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new OrganisationService(db, audit);

        var org = await service.CreateAsync(new Organisation { CompanyName = "Existing Co", SdlNumber = "L000111222" });

        // Act
        org.CompanyName = "Modified Co";
        var saved = await service.SaveAsync(org, "Admin");

        // Assert
        Assert.Equal(org.Id, saved.Id);
        Assert.Equal("Modified Co", saved.CompanyName);
    }

    [Fact]
    public async Task DeleteAsync_ExistingOrganisation_RemovesAndLogsAudit()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new OrganisationService(db, audit);

        var org = await service.CreateAsync(new Organisation { CompanyName = "To Delete Co", SdlNumber = "L999000111" });

        // Act
        var deleted = await service.DeleteAsync(org.Id, "AdminDeleter");

        // Assert
        Assert.True(deleted);
        var inDb = await db.Organisations.FindAsync(org.Id);
        Assert.Null(inDb);

        var deleteAudit = await db.AuditLogs
            .FirstOrDefaultAsync(a => a.EntityName == "Organisation" && a.RecordId == org.Id && a.ActionName == "Delete");
        Assert.NotNull(deleteAudit);
        Assert.Equal("AdminDeleter", deleteAudit.Actor);
    }

    [Fact]
    public async Task DeleteAsync_NonExistentOrganisation_ReturnsFalse()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new OrganisationService(db, audit);

        // Act
        var deleted = await service.DeleteAsync(7777, "Admin");

        // Assert
        Assert.False(deleted);
    }

    #endregion

    #region Contacts Management Tests

    [Fact]
    public async Task ContactsManagement_AddsAndRemovesContactsWithAudit()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new OrganisationService(db, audit);

        var org = await service.CreateAsync(new Organisation { CompanyName = "Auto Tech Ltd", SdlNumber = "L999888777" });
        var person1 = new Person { FirstName = "Alice", LastName = "Smith" };
        var person2 = new Person { FirstName = "Bob", LastName = "Jones" };
        db.People.AddRange(person1, person2);
        await db.SaveChangesAsync();

        // Act - Add primary contact 1
        var contact1 = await service.AddContactAsync(org.Id, person1.Id, "SDF", isPrimary: true, currentUsername: "ContactAdmin");
        Assert.True(contact1.IsPrimary);
        Assert.Equal("ContactAdmin", contact1.CreatedBy);

        // Act - Add second contact as primary -> contact1 should no longer be primary
        var contact2 = await service.AddContactAsync(org.Id, person2.Id, "CEO", isPrimary: true, currentUsername: "ContactAdmin");
        Assert.True(contact2.IsPrimary);

        var contacts = await service.GetContactsAsync(org.Id);
        Assert.Equal(2, contacts.Count);
        var c1Reloaded = contacts.First(c => c.Id == contact1.Id);
        Assert.False(c1Reloaded.IsPrimary);

        // Act - Remove contact1
        var removed = await service.RemoveContactAsync(org.Id, contact1.Id, "ContactAdmin");
        Assert.True(removed);

        contacts = await service.GetContactsAsync(org.Id);
        Assert.Single(contacts);
        Assert.Equal(contact2.Id, contacts[0].Id);

        // Act - Remove contact2 via single-id overload
        var removed2 = await service.RemoveContactAsync(contact2.Id, "ContactAdmin");
        Assert.True(removed2);

        contacts = await service.GetContactsAsync(org.Id);
        Assert.Empty(contacts);

        // Assert - Verify audit log for AddContact and RemoveContact
        var addAudit = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "OrganisationContact" && a.ActionName == "AddContact");
        var removeAudit = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "OrganisationContact" && a.ActionName == "RemoveContact");
        Assert.NotNull(addAudit);
        Assert.NotNull(removeAudit);
    }

    [Fact]
    public async Task RemoveContactAsync_NonExistentContact_ReturnsFalse()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new OrganisationService(db, audit);

        // Act
        var result = await service.RemoveContactAsync(1, 9999, "Admin");
        var result2 = await service.RemoveContactAsync(9999, "Admin");

        // Assert
        Assert.False(result);
        Assert.False(result2);
    }

    #endregion

    #region Sites Management Tests

    [Fact]
    public async Task SitesManagement_AddsUpdatesAndRemovesSitesWithAudit()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new OrganisationService(db, audit);

        var org = await service.CreateAsync(new Organisation { CompanyName = "Mining Tools Corp", SdlNumber = "L555444333" });

        // Act - Add Head Office site
        var site1 = new OrganisationSite
        {
            OrganisationId = org.Id,
            SiteName = "Gauteng HQ",
            SiteCode = "HQ-01",
            PhysicalAddress = "123 Main Road, Sandton",
            ProvinceCode = "GP",
            IsHeadOffice = true
        };

        var addedSite = await service.AddSiteAsync(site1, "SiteAdmin");

        // Assert site 1
        Assert.True(addedSite.Id > 0);
        Assert.True(addedSite.IsHeadOffice);
        Assert.Equal("SiteAdmin", addedSite.CreatedBy);

        // Act - Update site 1
        addedSite.PhysicalAddress = "456 West Street, Sandton";
        var updatedSite = await service.UpdateSiteAsync(addedSite, "SiteAdmin");
        Assert.Equal("456 West Street, Sandton", updatedSite.PhysicalAddress);

        // Act - Add Branch site as Head Office -> site1 should no longer be head office
        var site2 = new OrganisationSite
        {
            OrganisationId = org.Id,
            SiteName = "Durban Branch",
            SiteCode = "KZN-01",
            IsHeadOffice = true
        };
        await service.AddSiteAsync(site2, "SiteAdmin");

        var sites = await service.GetSitesAsync(org.Id);
        Assert.Equal(2, sites.Count);
        var site1Reloaded = sites.First(s => s.Id == site1.Id);
        Assert.False(site1Reloaded.IsHeadOffice);

        // Act - Remove site 2
        var removed = await service.RemoveSiteAsync(site2.Id, "SiteAdmin");
        Assert.True(removed);

        // Verify audit logs
        var addSiteAudit = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "OrganisationSite" && a.ActionName == "AddSite");
        var updateSiteAudit = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "OrganisationSite" && a.ActionName == "UpdateSite");
        var removeSiteAudit = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "OrganisationSite" && a.ActionName == "RemoveSite");

        Assert.NotNull(addSiteAudit);
        Assert.NotNull(updateSiteAudit);
        Assert.NotNull(removeSiteAudit);
    }

    [Fact]
    public async Task UpdateSiteAsync_NonExistentSite_ThrowsKeyNotFoundException()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new OrganisationService(db, audit);

        var site = new OrganisationSite { Id = 9999, SiteName = "Missing Site" };

        // Act & Assert
        await Assert.ThrowsAsync<KeyNotFoundException>(() => service.UpdateSiteAsync(site, "Admin"));
    }

    [Fact]
    public async Task RemoveSiteAsync_NonExistentSite_ReturnsFalse()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new OrganisationService(db, audit);

        // Act
        var result = await service.RemoveSiteAsync(9999, "Admin");

        // Assert
        Assert.False(result);
    }

    #endregion
}
