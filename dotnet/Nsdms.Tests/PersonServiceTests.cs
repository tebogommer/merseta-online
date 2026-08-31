using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class PersonServiceTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, PersonService service) CreateTestContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var service = new PersonService(factory, audit);
        return (factory, db, audit, service);
    }

    #region Create Tests

    [Fact]
    public async Task CreateAsync_WithValidRsaId_AutoPopulatesDobGenderAndCitizenship()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

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
        Assert.Equal("M", created.GenderCode);
        Assert.Equal("SA_CIT", created.CitizenStatusCode);
        Assert.Equal("AdminUser", created.CreatedBy);

        // Verify in DB
        var inDb = await db.People.FindAsync(created.Id);
        Assert.NotNull(inDb);
        Assert.Equal("Thabo", inDb.FirstName);
        Assert.Equal("Mokoena", inDb.LastName);

        // Verify audit log
        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "Person" && a.RecordId == created.Id);
        Assert.NotNull(auditLog);
        Assert.Equal("Create", auditLog.ActionName);
        Assert.Equal("AdminUser", auditLog.Actor);
    }

    [Fact]
    public async Task CreateAsync_FemaleCitizen_AutoPopulatesCorrectly()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var person = new Person
        {
            FirstName = "Nomvula",
            LastName = "Khumalo",
            RsaIdNumber = "9005200123081", // 1990-05-20, Female, SA Citizen
            Email = "nomvula@merseta.org.za"
        };

        // Act
        var created = await service.CreateAsync(person);

        // Assert
        Assert.Equal(new DateTime(1990, 5, 20), created.DateOfBirth);
        Assert.Equal("F", created.GenderCode);
        Assert.Equal("SA_CIT", created.CitizenStatusCode);
    }

    [Fact]
    public async Task CreateAsync_PermanentResident_AutoPopulatesPermanentResidentStatus()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var person = new Person
        {
            FirstName = "Jean",
            LastName = "Dupont",
            RsaIdNumber = "0512150123184", // 2005-12-15, Female, Permanent Resident (C=1)
            Email = "jean@merseta.org.za"
        };

        // Act
        var created = await service.CreateAsync(person);

        // Assert
        Assert.Equal("PERM_RES", created.CitizenStatusCode);
    }

    [Fact]
    public async Task CreateAsync_WithoutRsaId_UsesProvidedDobAndGender()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var person = new Person
        {
            FirstName = "Carlos",
            LastName = "Silva",
            PassportNumber = "A12345678",
            NationalityCode = "BRA",
            DateOfBirth = new DateTime(1988, 7, 20),
            GenderCode = "M",
            CitizenStatusCode = "FOREIGN",
            Email = "carlos@merseta.org.za"
        };

        // Act
        var created = await service.CreateAsync(person);

        // Assert
        Assert.Equal(new DateTime(1988, 7, 20), created.DateOfBirth);
        Assert.Equal("M", created.GenderCode);
        Assert.Equal("FOREIGN", created.CitizenStatusCode);
    }

    [Fact]
    public async Task CreateAsync_DuplicateRsaId_ThrowsInvalidOperationException()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var existing = new Person
        {
            FirstName = "Existing",
            LastName = "Person",
            RsaIdNumber = "8001015009087",
            Email = "existing@merseta.org.za"
        };
        db.People.Add(existing);
        await db.SaveChangesAsync();

        var duplicate = new Person
        {
            FirstName = "Duplicate",
            LastName = "Person",
            RsaIdNumber = "8001015009087",
            Email = "duplicate@merseta.org.za"
        };

        // Act & Assert
        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() => service.CreateAsync(duplicate));
        Assert.Contains("already exists", ex.Message);
    }

    [Fact]
    public async Task CreateAsync_InvalidRsaIdLuhn_ThrowsArgumentException()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var person = new Person
        {
            FirstName = "Bad",
            LastName = "Luhn",
            RsaIdNumber = "8001015009088", // Invalid check digit (should be 7)
            Email = "bad@merseta.org.za"
        };

        // Act & Assert
        var ex = await Assert.ThrowsAsync<ArgumentException>(() => service.CreateAsync(person));
        Assert.Contains("Invalid RSA ID number", ex.Message);
    }

    #endregion

    #region Read & Search Tests

    [Fact]
    public async Task GetAllAsync_ReturnsAllActivePeople()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        db.People.AddRange(
            new Person { FirstName = "Person1", LastName = "Last1", Email = "p1@test.com", IsActive = true },
            new Person { FirstName = "Person2", LastName = "Last2", Email = "p2@test.com", IsActive = true }
        );
        await db.SaveChangesAsync();

        // Act
        var list = await service.GetAllAsync();

        // Assert
        Assert.Equal(2, list.Count);
    }

    [Fact]
    public async Task GetAllAsync_WithSearch_FiltersByFirstNameLastNameRsaIdOrEmail()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        db.People.AddRange(
            new Person { FirstName = "Sipho", LastName = "Ndlovu", RsaIdNumber = "9001015009087", Email = "sipho@test.com" },
            new Person { FirstName = "Fatima", LastName = "Patel", RsaIdNumber = "8502020001088", Email = "fatima@test.com" },
            new Person { FirstName = "Pieter", LastName = "Van Der Merwe", RsaIdNumber = "7803035009089", Email = "pieter@test.com" }
        );
        await db.SaveChangesAsync();

        // Act
        var searchByName = await service.GetAllAsync("Fatima");
        var searchByRsaId = await service.GetAllAsync("780303");
        var searchByEmail = await service.GetAllAsync("sipho@test.com");

        // Assert
        Assert.Single(searchByName);
        Assert.Equal("Fatima", searchByName[0].FirstName);

        Assert.Single(searchByRsaId);
        Assert.Equal("Pieter", searchByRsaId[0].FirstName);

        Assert.Single(searchByEmail);
        Assert.Equal("Sipho", searchByEmail[0].FirstName);
    }

    [Fact]
    public async Task GetByIdAsync_ReturnsPerson()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var person = new Person { FirstName = "Kagiso", LastName = "Rabada", Email = "kagiso@cricket.co.za" };
        db.People.Add(person);
        await db.SaveChangesAsync();

        // Act
        var result = await service.GetByIdAsync(person.Id);

        // Assert
        Assert.NotNull(result);
        Assert.Equal("Kagiso", result.FirstName);
    }

    [Fact]
    public async Task GetByRsaIdAsync_ReturnsMatchingPerson()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var person = new Person { FirstName = "Caster", LastName = "Semenya", RsaIdNumber = "9101070001087", Email = "caster@athletics.co.za" };
        db.People.Add(person);
        await db.SaveChangesAsync();

        // Act
        var result = await service.GetByRsaIdAsync("9101070001087");

        // Assert
        Assert.NotNull(result);
        Assert.Equal("Caster", result.FirstName);
    }

    #endregion

    #region Update Tests

    [Fact]
    public async Task UpdateAsync_UpdatesFieldsAndLogsAudit()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var person = new Person
        {
            FirstName = "OldFirst",
            LastName = "OldLast",
            RsaIdNumber = "8001015009087",
            Email = "old@test.com",
            PhoneNumber = "0110000000"
        };
        db.People.Add(person);
        await db.SaveChangesAsync();

        // Act
        person.FirstName = "NewFirst";
        person.LastName = "NewLast";
        person.Email = "new@test.com";
        person.PhoneNumber = "0119999999";
        var updated = await service.UpdateAsync(person, "EditorUser");

        // Assert
        Assert.Equal("NewFirst", updated.FirstName);
        Assert.Equal("NewLast", updated.LastName);
        Assert.Equal("new@test.com", updated.Email);
        Assert.Equal("EditorUser", updated.ModifiedBy);
        Assert.NotNull(updated.ModifiedAt);

        // Verify audit log
        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "Person" && a.RecordId == person.Id && a.ActionName == "Update");
        Assert.NotNull(auditLog);
        Assert.Equal("EditorUser", auditLog.Actor);
    }

    #endregion

    #region Delete Tests

    [Fact]
    public async Task DeleteAsync_SetsIsActiveFalseAndLogsAudit()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var person = new Person { FirstName = "ToSoftDelete", LastName = "Person", Email = "delete@test.com", IsActive = true };
        db.People.Add(person);
        await db.SaveChangesAsync();

        // Act
        var result = await service.DeleteAsync(person.Id, "DeleterUser");

        // Assert
        Assert.True(result);
        var inDb = await service.GetByIdAsync(person.Id);
        Assert.NotNull(inDb);
        Assert.False(inDb.IsActive); // Soft delete
        Assert.Equal("DeleterUser", inDb.ModifiedBy);

        // Verify audit log
        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "Person" && a.RecordId == person.Id && a.ActionName == "Delete");
        Assert.NotNull(auditLog);
        Assert.Equal("DeleterUser", auditLog.Actor);
    }

    #endregion

    #region 360-Degree Relational Query Tests

    [Fact]
    public async Task GetPersonLearnersAsync_ReturnsAssociatedLearnerAgreements()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var person = new Person { FirstName = "Learner", LastName = "Candidate", Email = "learner@test.com" };
        var org = new Organisation { CompanyName = "Engineering Works Ltd", SdlNumber = "L123456789" };
        db.People.Add(person);
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var cl = new CompanyLearner
        {
            PersonId = person.Id,
            OrganisationId = org.Id,
            LearnerContractNumber = "CON-2026-001",
            QualificationTitle = "Automotive Repairer NQF 4",
            LearningProgrammeTypeCode = "01",
            EnrolmentStatusCode = "Active",
            RegistrationDate = DateTime.UtcNow
        };
        db.CompanyLearners.Add(cl);
        await db.SaveChangesAsync();

        // Act
        var result = await service.GetPersonLearnersAsync(person.Id);

        // Assert
        Assert.Single(result);
        Assert.Equal("CON-2026-001", result[0].LearnerContractNumber);
        Assert.Equal("Engineering Works Ltd", result[0].EmployerName);
        Assert.Equal("Apprenticeship", result[0].ProgrammeTypeName);
    }

    [Fact]
    public async Task GetPersonEmployersAsync_ReturnsPrimaryContactAndCommitteeRoles()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var person = new Person { FirstName = "Executive", LastName = "Director", Email = "director@corp.co.za" };
        db.People.Add(person);
        await db.SaveChangesAsync();

        var org = new Organisation { CompanyName = "Auto Parts SA", SdlNumber = "L987654321", PrimaryContactPersonId = person.Id };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        // Act
        var result = await service.GetPersonEmployersAsync(person.Id);

        // Assert
        Assert.Single(result);
        Assert.Equal("Auto Parts SA", result[0].CompanyName);
        Assert.Contains("Primary Contact", result[0].Designation);
    }

    [Fact]
    public async Task GetPersonEtqaPractitionersAsync_ReturnsPractitionerWithScopesCount()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var person = new Person { FirstName = "Assessor", LastName = "Pro", Email = "assessor@test.com" };
        db.People.Add(person);
        await db.SaveChangesAsync();

        var assessor = new EtqaAssessor
        {
            PersonId = person.Id,
            RegistrationNumber = "ASS-2026-99",
            EtqaRole = "Assessor",
            RegistrationStatusCode = "Registered",
            StartDate = DateTime.UtcNow.AddYears(-1),
            EndDate = DateTime.UtcNow.AddYears(2),
            Scopes = new List<AssessorModeratorScope>
            {
                new() { SaqaQualificationId = 58781, QualificationTitle = "National Certificate: Mechanical Engineering" }
            }
        };
        db.EtqaAssessors.Add(assessor);
        await db.SaveChangesAsync();

        // Act
        var result = await service.GetPersonEtqaPractitionersAsync(person.Id);

        // Assert
        Assert.Single(result);
        Assert.Equal("ASS-2026-99", result[0].RegistrationNumber);
        Assert.Equal(1, result[0].ScopesCount);
    }

    #endregion
}
