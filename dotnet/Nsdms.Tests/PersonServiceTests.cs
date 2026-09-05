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

    #region Phase 3 Vertical Partitioning Tests

    [Fact]
    public async Task CreateAsync_AutomaticallySynchronizesAllThreeSatellites()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();
        var person = new Person
        {
            FirstName = "Kagiso",
            LastName = "Dlamini",
            RsaIdNumber = "8001015009087", // Proven valid RSA ID (1980-01-01, Male, SA Citizen)
            Email = "kagiso.dlamini@autoworks.co.za",
            PhoneNumber = "0112345678",
            CellNumber = "0823456789",
            PhysicalAddress = "42 Artisan Road, Rosslyn",
            PhysicalAddressPostalCode = "0200",
            PostalAddress = "PO Box 1234, Rosslyn",
            PostalAddressPostalCode = "0200",
            ProvinceCode = "GP",
            StatssaAreaCode = "TSH-01",
            EquityCode = "BA",
            NationalityCode = "SA",
            HomeLanguageCode = "ZUL",
            DisabilityCode = "01",
            SeeingRatingId = "03",
            HearingRatingId = "01",
            WalkingRatingId = "01",
            RememberingRatingId = "01",
            CommunicatingRatingId = "01",
            SelfCareRatingId = "01",
            PopiActStatusId = "01",
            PopiActConsentDate = new DateTime(2026, 1, 15, 0, 0, 0, DateTimeKind.Utc),
            LastSchoolEmisNumber = "EMIS-998877",
            LastSchoolYear = "2003"
        };

        // Act
        var created = await service.CreateAsync(person, "OfficerTest");

        // Assert - Satellites attached to returned entity
        Assert.NotNull(created.Contact);
        Assert.Equal("kagiso.dlamini@autoworks.co.za", created.Contact.Email);
        Assert.Equal("42 Artisan Road, Rosslyn", created.Contact.PhysicalAddress);
        Assert.Equal("GP", created.Contact.ProvinceCode);

        Assert.NotNull(created.Demographics);
        Assert.Equal("BA", created.Demographics.EquityCode);
        Assert.Equal("ZUL", created.Demographics.HomeLanguageCode);
        Assert.Equal("EMIS-998877", created.Demographics.LastSchoolEmisNumber);

        Assert.NotNull(created.DisabilityRating);
        Assert.Equal("01", created.DisabilityRating.DisabilityCode);
        Assert.Equal("03", created.DisabilityRating.SeeingRatingId);

        // Assert - Satellites persisted in database
        var contactInDb = await db.PersonContacts.FirstOrDefaultAsync(c => c.PersonId == created.Id);
        Assert.NotNull(contactInDb);
        Assert.Equal("kagiso.dlamini@autoworks.co.za", contactInDb.Email);
        Assert.Equal("0823456789", contactInDb.CellNumber);

        var demoInDb = await db.PersonDemographics.FirstOrDefaultAsync(d => d.PersonId == created.Id);
        Assert.NotNull(demoInDb);
        Assert.Equal("BA", demoInDb.EquityCode);
        Assert.Equal("01", demoInDb.PopiActStatusId);

        var disabInDb = await db.PersonDisabilityRatings.FirstOrDefaultAsync(r => r.PersonId == created.Id);
        Assert.NotNull(disabInDb);
        Assert.Equal("03", disabInDb.SeeingRatingId);
        Assert.Equal("01", disabInDb.HearingRatingId);
    }

    [Fact]
    public async Task GetByIdAsync_EagerlyLoadsAllSatellites()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();
        var person = new Person
        {
            FirstName = "Nombulelo",
            LastName = "Zulu",
            RsaIdNumber = "9005200123081", // Proven valid RSA ID (1990-05-20, Female, SA Citizen)
            Email = "nombulelo.zulu@precisiontool.co.za",
            PhoneNumber = "0314445555",
            ProvinceCode = "KZN",
            EquityCode = "BA",
            DisabilityCode = "00"
        };
        var created = await service.CreateAsync(person, "HrOfficer");

        // Act
        var loaded = await service.GetByIdAsync(created.Id);

        // Assert
        Assert.NotNull(loaded);
        Assert.NotNull(loaded.Contact);
        Assert.Equal("nombulelo.zulu@precisiontool.co.za", loaded.Contact.Email);
        Assert.Equal("KZN", loaded.Contact.ProvinceCode);

        Assert.NotNull(loaded.Demographics);
        Assert.Equal("BA", loaded.Demographics.EquityCode);

        Assert.NotNull(loaded.DisabilityRating);
        Assert.Equal("00", loaded.DisabilityRating.DisabilityCode);
        Assert.Equal("01", loaded.DisabilityRating.SeeingRatingId);
    }

    [Fact]
    public async Task UpdateContactAsync_UpdatesSatelliteAndKeepsParentPersonSynchronized()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();
        var person = new Person
        {
            FirstName = "David",
            LastName = "Botha",
            RsaIdNumber = "0512150123184", // Proven valid RSA ID (2005-12-15, Female, PR)
            Email = "david.botha@oldmail.co.za",
            PhoneNumber = "0123334444",
            ProvinceCode = "GP"
        };
        var created = await service.CreateAsync(person, "Admin");

        // Act - Mutate contact via satellite service
        var updatedContact = new PersonContact
        {
            Email = "david.botha@newdomain.co.za",
            PhoneNumber = "0129998888",
            CellNumber = "0711112222",
            PhysicalAddress = "78 Steelworks Boulevard, Vanderbijlpark",
            PhysicalAddressPostalCode = "1900",
            PostalAddress = "Private Bag X01, Vanderbijlpark",
            PostalAddressPostalCode = "1900",
            ProvinceCode = "GP",
            StatssaAreaCode = "SED-01"
        };
        var result = await service.UpdateContactAsync(created.Id, updatedContact, "FieldOfficer");

        // Assert
        Assert.NotNull(result);
        Assert.Equal("david.botha@newdomain.co.za", result.Email);
        Assert.Equal("0711112222", result.CellNumber);

        // Verify parent Person was synchronized for backward compatibility
        var parentInDb = await db.People.FindAsync(created.Id);
        Assert.NotNull(parentInDb);
        Assert.Equal("david.botha@newdomain.co.za", parentInDb.Email);
        Assert.Equal("0711112222", parentInDb.CellNumber);
        Assert.Equal("78 Steelworks Boulevard, Vanderbijlpark", parentInDb.PhysicalAddress);

        // Verify audit log
        var auditEntry = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "PersonContact" && a.RecordId == result.Id);
        Assert.NotNull(auditEntry);
        Assert.Equal("Update", auditEntry.ActionName);
        Assert.Equal("FieldOfficer", auditEntry.Actor);
    }

    [Fact]
    public async Task UpdateDisabilityRatingAsync_IsolatesPopiaSpecialDataAndLogsAudit()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();
        var person = new Person
        {
            FirstName = "Lerato",
            LastName = "Maseko",
            RsaIdNumber = "0211156543186", // Proven valid RSA ID (2002-11-15, Male, PR)
            Email = "lerato.maseko@auto.co.za",
            ProvinceCode = "MP"
        };
        var created = await service.CreateAsync(person, "Clinician");

        // Act - Record formal occupational therapist assessment with special accommodations
        var assessedRating = new PersonDisabilityRating
        {
            DisabilityCode = "02", // Hearing
            SeeingRatingId = "01",
            HearingRatingId = "03", // Some difficulty
            WalkingRatingId = "01",
            RememberingRatingId = "01",
            CommunicatingRatingId = "02",
            SelfCareRatingId = "01",
            DisabilitySupportNotes = "Candidate requires sound amplification assistive headset in workshop classroom sessions.",
            IsDisabilityAssessed = true,
            AssessedDate = new DateTime(2026, 3, 1, 0, 0, 0, DateTimeKind.Utc),
            AssessedBy = "Dr. S. Naidoo (OT Reg #778899)"
        };
        var ratingResult = await service.UpdateDisabilityRatingAsync(created.Id, assessedRating, "DrNaidoo");

        // Assert
        Assert.NotNull(ratingResult);
        Assert.True(ratingResult.IsDisabilityAssessed);
        Assert.Equal("03", ratingResult.HearingRatingId);
        Assert.Equal("Dr. S. Naidoo (OT Reg #778899)", ratingResult.AssessedBy);
        Assert.Contains("sound amplification assistive headset", ratingResult.DisabilitySupportNotes);

        // Verify satellite stored in database
        var ratingInDb = await db.PersonDisabilityRatings.FirstOrDefaultAsync(r => r.PersonId == created.Id);
        Assert.NotNull(ratingInDb);
        Assert.True(ratingInDb.IsDisabilityAssessed);
        Assert.Equal("03", ratingInDb.HearingRatingId);

        // Verify audit log captured the clinician mutation
        var auditEntry = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "PersonDisabilityRating" && a.RecordId == ratingResult.Id);
        Assert.NotNull(auditEntry);
        Assert.Equal("Update", auditEntry.ActionName);
        Assert.Equal("DrNaidoo", auditEntry.Actor);
    }

    [Fact]
    public async Task PersonDeletion_CascadesToSatellites()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();
        var person = new Person
        {
            FirstName = "Temporary",
            LastName = "Record",
            RsaIdNumber = "9602295001089", // Proven valid RSA ID (1996-02-29, Male, SA Citizen)
            Email = "temp.record@test.org.za"
        };
        var created = await service.CreateAsync(person, "Tester");

        // Act - Load person with satellite navigations attached and remove
        var personInDb = await db.People
            .Include(p => p.Contact)
            .Include(p => p.Demographics)
            .Include(p => p.DisabilityRating)
            .FirstOrDefaultAsync(p => p.Id == created.Id);

        Assert.NotNull(personInDb);
        db.People.Remove(personInDb);
        await db.SaveChangesAsync();

        // Assert - Satellites removed by cascade
        var contactAfter = await db.PersonContacts.FirstOrDefaultAsync(c => c.PersonId == created.Id);
        var demoAfter = await db.PersonDemographics.FirstOrDefaultAsync(d => d.PersonId == created.Id);
        var ratingAfter = await db.PersonDisabilityRatings.FirstOrDefaultAsync(r => r.PersonId == created.Id);

        Assert.Null(contactAfter);
        Assert.Null(demoAfter);
        Assert.Null(ratingAfter);
    }

    [Fact]
    public async Task SoftDeleteAsync_SetsIsActiveFalseAndLeavesSatellitesAudited()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();
        var person = new Person
        {
            FirstName = "Soft",
            LastName = "DeleteTest",
            RsaIdNumber = "8001015009087",
            Email = "soft.delete@test.org.za"
        };
        var created = await service.CreateAsync(person, "OfficerDeleter");

        // Act
        var deleted = await service.DeleteAsync(created.Id, "OfficerDeleter");

        // Assert
        Assert.True(deleted);
        var personInDb = await db.People.FindAsync(created.Id);
        Assert.NotNull(personInDb);
        Assert.False(personInDb.IsActive);

        // Satellites remain intact with historical audit trail
        var contact = await service.GetContactAsync(created.Id);
        Assert.NotNull(contact);
        Assert.Equal("soft.delete@test.org.za", contact.Email);
    }

    #endregion
}
