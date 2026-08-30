using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class PersonServiceTests
{
    private static NsdmsDbContext CreateInMemoryDbContext()
    {
        var options = new DbContextOptionsBuilder<NsdmsDbContext>()
            .UseInMemoryDatabase(databaseName: Guid.NewGuid().ToString())
            .Options;

        return new NsdmsDbContext(options);
    }

    #region Create Tests

    [Fact]
    public async Task CreateAsync_WithValidRsaId_AutoPopulatesDobGenderAndCitizenship()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new PersonService(db, audit);

        var person = new Person
        {
            FirstName = "Thabo",
            LastName = "Mokoena",
            RsaIdNumber = "8001015009087", // 1980-01-01, Male, SA Citizen
            Email = "thabo.mokoena@merseta.org.za",
            PhoneNumber = "0115551234"
        };

        // Act
        var created = await service.CreateAsync(person, "AdminUser");

        // Assert
        Assert.NotNull(created);
        Assert.True(created.Id > 0);
        Assert.Equal(new DateTime(1980, 1, 1), created.DateOfBirth);
        Assert.Equal("Male", created.Gender);
        Assert.Equal("M", created.GenderCode);
        Assert.True(created.IsSouthAfricanCitizen);
        Assert.Equal("AdminUser", created.CreatedBy);

        // Verify audit log double-write
        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "Person" && a.RecordId == created.Id);
        Assert.NotNull(auditLog);
        Assert.Equal("Create", auditLog.ActionName);
        Assert.Equal("AdminUser", auditLog.Actor);
        Assert.Contains("8001015009087", auditLog.MetadataJson);
    }

    [Fact]
    public async Task CreateAsync_WithFemalePermanentResidentRsaId_AutoPopulatesFemaleAndResident()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new PersonService(db, audit);

        var person = new Person
        {
            FirstName = "Nomvula",
            LastName = "Khumalo",
            RsaIdNumber = "0512150123184", // 2005-12-15, Female, Permanent Resident
            Email = "nomvula.k@merseta.org.za"
        };

        // Act
        var created = await service.CreateAsync(person, "Registrar");

        // Assert
        Assert.NotNull(created);
        Assert.Equal(new DateTime(2005, 12, 15), created.DateOfBirth);
        Assert.Equal("Female", created.Gender);
        Assert.Equal("F", created.GenderCode);
        Assert.False(created.IsSouthAfricanCitizen);
    }

    [Fact]
    public async Task CreateAsync_WithoutRsaId_CreatesSuccessfullyWithoutAutoPopulation()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new PersonService(db, audit);

        var person = new Person
        {
            FirstName = "John",
            LastName = "Doe",
            PassportNumber = "A12345678",
            DateOfBirth = new DateTime(1992, 4, 10),
            Gender = "Male",
            GenderCode = "M",
            IsSouthAfricanCitizen = false
        };

        // Act
        var created = await service.CreateAsync(person, "Admin");

        // Assert
        Assert.NotNull(created);
        Assert.True(created.Id > 0);
        Assert.Equal(new DateTime(1992, 4, 10), created.DateOfBirth);
        Assert.Equal("A12345678", created.PassportNumber);
    }

    #endregion

    #region Update Tests

    [Fact]
    public async Task UpdateAsync_WithValidChanges_UpdatesPersonAndLogsAuditWithBeforeState()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new PersonService(db, audit);

        var person = new Person
        {
            FirstName = "Nomvula",
            LastName = "Khumalo",
            RsaIdNumber = "0512150123184",
            Email = "nomvula@merseta.org.za"
        };
        await service.CreateAsync(person, "Creator");

        // Act
        var updateModel = new Person
        {
            Id = person.Id,
            FirstName = person.FirstName,
            LastName = person.LastName,
            RsaIdNumber = person.RsaIdNumber,
            PhoneNumber = "0821234567",
            Email = "nomvula.updated@merseta.org.za",
            PhysicalAddress = "456 West Street, Sandton"
        };
        var updated = await service.UpdateAsync(updateModel, "Updater");

        // Assert
        Assert.Equal("0821234567", updated.PhoneNumber);
        Assert.Equal("nomvula.updated@merseta.org.za", updated.Email);
        Assert.Equal("456 West Street, Sandton", updated.PhysicalAddress);
        Assert.Equal("Updater", updated.ModifiedBy);
        Assert.NotNull(updated.ModifiedAt);

        // Verify audit log for Update
        var updateAudit = await db.AuditLogs
            .Where(a => a.EntityName == "Person" && a.RecordId == person.Id && a.ActionName == "Update")
            .FirstOrDefaultAsync();

        Assert.NotNull(updateAudit);
        Assert.Equal("Updater", updateAudit.Actor);
        Assert.Contains("nomvula@merseta.org.za", updateAudit.MetadataJson);
        Assert.Contains("nomvula.updated@merseta.org.za", updateAudit.MetadataJson);
    }

    [Fact]
    public async Task UpdateAsync_NonExistentPerson_ThrowsKeyNotFoundException()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new PersonService(db, audit);

        var person = new Person { Id = 999, FirstName = "Missing", LastName = "Person" };

        // Act & Assert
        await Assert.ThrowsAsync<KeyNotFoundException>(() => service.UpdateAsync(person, "Admin"));
    }

    #endregion

    #region Delete Tests

    [Fact]
    public async Task DeleteAsync_ExistingPerson_RemovesPersonAndLogsAudit()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new PersonService(db, audit);

        var person = new Person
        {
            FirstName = "Temp",
            LastName = "User",
            Email = "temp@merseta.org.za"
        };
        await service.CreateAsync(person, "Admin");

        // Act
        var deleted = await service.DeleteAsync(person.Id, "DeleterUser");

        // Assert
        Assert.True(deleted);
        var inDb = await db.People.FindAsync(person.Id);
        Assert.Null(inDb);

        var deleteAudit = await db.AuditLogs
            .FirstOrDefaultAsync(a => a.EntityName == "Person" && a.RecordId == person.Id && a.ActionName == "Delete");
        Assert.NotNull(deleteAudit);
        Assert.Equal("DeleterUser", deleteAudit.Actor);
        Assert.Contains("Temp", deleteAudit.MetadataJson);
    }

    [Fact]
    public async Task DeleteAsync_NonExistentPerson_ReturnsFalse()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new PersonService(db, audit);

        // Act
        var deleted = await service.DeleteAsync(999, "Admin");

        // Assert
        Assert.False(deleted);
    }

    #endregion

    #region Query & Search Tests

    [Fact]
    public async Task GetByIdAsync_ExistingId_ReturnsPerson()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new PersonService(db, audit);

        var person = await service.CreateAsync(new Person
        {
            FirstName = "Lerato",
            LastName = "Molefe",
            Email = "lerato.m@merseta.org.za"
        });

        // Act
        var result = await service.GetByIdAsync(person.Id);

        // Assert
        Assert.NotNull(result);
        Assert.Equal("Lerato", result.FirstName);
        Assert.Equal("Molefe", result.LastName);
    }

    [Fact]
    public async Task GetByIdAsync_NonExistentId_ReturnsNull()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new PersonService(db, audit);

        // Act
        var result = await service.GetByIdAsync(999);

        // Assert
        Assert.Null(result);
    }

    [Theory]
    [InlineData("Dlamini", 1)]
    [InlineData("Sipho", 1)]
    [InlineData("8001015009087", 1)]
    [InlineData("gmail.com", 1)]
    [InlineData("0829990000", 1)]
    [InlineData("NonExistentQuery", 0)]
    public async Task GetAllAsync_WithSearchQuery_FiltersCorrectly(string query, int expectedCount)
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new PersonService(db, audit);

        await service.CreateAsync(new Person
        {
            FirstName = "Sipho",
            LastName = "Dlamini",
            RsaIdNumber = "8001015009087",
            Email = "sipho@merseta.org.za",
            PhoneNumber = "0829990000"
        });

        await service.CreateAsync(new Person
        {
            FirstName = "Lerato",
            LastName = "Nkosi",
            Email = "lerato@gmail.com",
            PhoneNumber = "0711112222"
        });

        // Act
        var results = await service.GetAllAsync(query);

        // Assert
        Assert.Equal(expectedCount, results.Count);
    }

    [Fact]
    public async Task GetAllAsync_NoSearchQuery_ReturnsAllSortedByLastNameThenFirstName()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new PersonService(db, audit);

        await service.CreateAsync(new Person { FirstName = "Bongi", LastName = "Zulu" });
        await service.CreateAsync(new Person { FirstName = "Alice", LastName = "Adams" });
        await service.CreateAsync(new Person { FirstName = "Charlie", LastName = "Adams" });

        // Act
        var results = await service.GetAllAsync();

        // Assert
        Assert.Equal(3, results.Count);
        Assert.Equal("Adams", results[0].LastName);
        Assert.Equal("Alice", results[0].FirstName);
        Assert.Equal("Adams", results[1].LastName);
        Assert.Equal("Charlie", results[1].FirstName);
        Assert.Equal("Zulu", results[2].LastName);
    }

    #endregion
}
