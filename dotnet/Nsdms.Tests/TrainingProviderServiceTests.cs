using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class TrainingProviderServiceTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, TrainingProviderService service) CreateTestContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var service = new TrainingProviderService(factory, audit);
        return (factory, db, audit, service);
    }

    [Fact]
    public async Task CreateAsync_ValidProvider_CreatesAndLogsAudit()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation { CompanyName = "Tech Academy", SdlNumber = "L100200300" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var provider = new TrainingProvider
        {
            OrganisationId = org.Id,
            AccreditationNumber = "ACC-2026-001",
            AccreditationStartDate = new DateTime(2026, 1, 1),
            AccreditationEndDate = new DateTime(2029, 1, 1),
            ProviderTypeCode = "Private",
            ProviderStatusCode = "Accredited",
            MaxLearnerCapacity = 100
        };

        // Act
        var created = await service.CreateAsync(provider, "AdminAuthor");

        // Assert
        Assert.True(created.Id > 0);
        Assert.Equal("AdminAuthor", created.CreatedBy);

        var inDb = await db.TrainingProviders.FindAsync(created.Id);
        Assert.NotNull(inDb);
        Assert.Equal("ACC-2026-001", inDb.AccreditationNumber);

        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "TrainingProvider" && a.RecordId == created.Id);
        Assert.NotNull(auditLog);
        Assert.Equal("Create", auditLog.ActionName);
        Assert.Equal("AdminAuthor", auditLog.Actor);
    }

    [Fact]
    public async Task GetAllAsync_ReturnsAllProvidersWithOrganisation()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org1 = new Organisation { CompanyName = "Provider One", SdlNumber = "L111000111" };
        var org2 = new Organisation { CompanyName = "Provider Two", SdlNumber = "L222000222" };
        db.Organisations.AddRange(org1, org2);
        await db.SaveChangesAsync();

        db.TrainingProviders.AddRange(
            new TrainingProvider { OrganisationId = org1.Id, AccreditationNumber = "ACC-01" },
            new TrainingProvider { OrganisationId = org2.Id, AccreditationNumber = "ACC-02" }
        );
        await db.SaveChangesAsync();

        // Act
        var list = await service.GetAllAsync();

        // Assert
        Assert.Equal(2, list.Count);
        Assert.Contains(list, p => p.Organisation?.CompanyName == "Provider One");
    }

    [Fact]
    public async Task GetByIdAsync_ReturnsProviderWithQualificationsAndUnitStandards()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation { CompanyName = "Mega Training", SdlNumber = "L333000333" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var provider = new TrainingProvider { OrganisationId = org.Id, AccreditationNumber = "ACC-MEGA" };
        db.TrainingProviders.Add(provider);
        await db.SaveChangesAsync();

        db.TrainingProviderQualifications.Add(new TrainingProviderQualification { TrainingProviderId = provider.Id, QualificationTitle = "Welding NQF 4", SaqaQualificationId = 1234 });
        db.TrainingProviderUnitStandards.Add(new TrainingProviderUnitStandard { TrainingProviderId = provider.Id, UnitStandardTitle = "Shielded Metal Arc Welding", UnitStandardId = 5678 });
        await db.SaveChangesAsync();

        // Act
        var result = await service.GetByIdAsync(provider.Id);

        // Assert
        Assert.NotNull(result);
        Assert.Single(result.Qualifications);
        Assert.Single(result.UnitStandards);
    }

    [Fact]
    public async Task AddQualificationAsync_AddsQualificationAndLogsAudit()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation { CompanyName = "Skills Hub", SdlNumber = "L444000444" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var provider = new TrainingProvider { OrganisationId = org.Id, AccreditationNumber = "ACC-HUB" };
        db.TrainingProviders.Add(provider);
        await db.SaveChangesAsync();

        var qual = new TrainingProviderQualification
        {
            TrainingProviderId = provider.Id,
            QualificationTitle = "Boilermaking NQF 3",
            SaqaQualificationId = 58785,
            NqfLevel = 3
        };

        // Act
        var added = await service.AddQualificationAsync(qual, "ProgManager");

        // Assert
        Assert.True(added.Id > 0);
        Assert.Equal("ProgManager", added.CreatedBy);

        var qualifications = await service.GetQualificationsAsync(provider.Id);
        Assert.Single(qualifications);
        Assert.Equal("Boilermaking NQF 3", qualifications[0].QualificationTitle);
    }

    [Fact]
    public async Task DeleteAsync_DeletesProviderAndCascades()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation { CompanyName = "To Close", SdlNumber = "L666000666" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var provider = new TrainingProvider { OrganisationId = org.Id, AccreditationNumber = "ACC-CLOSE" };
        db.TrainingProviders.Add(provider);
        await db.SaveChangesAsync();

        // Act
        var deleted = await service.DeleteAsync(provider.Id, "Deleter");

        // Assert
        Assert.True(deleted);
        var inDb = await service.GetByIdAsync(provider.Id);
        Assert.Null(inDb);
    }

    #region 360-Degree SDP Relational Tests

    [Fact]
    public async Task GetEnrolledLearnersAsync_ReturnsAllLearnersUnderProvider()
    {
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation { CompanyName = "Ford SA", SdlNumber = "L123456789" };
        var sdpOrg = new Organisation { CompanyName = "Tshwane South TVET", SdlNumber = "L987654321" };
        db.Organisations.AddRange(org, sdpOrg);
        await db.SaveChangesAsync();

        var provider = new TrainingProvider { OrganisationId = sdpOrg.Id, AccreditationNumber = "ACC-TSHWANE" };
        db.TrainingProviders.Add(provider);
        await db.SaveChangesAsync();

        var person1 = new Person { FirstName = "Sipho", LastName = "Mthembu", RsaIdNumber = "0202025009087" };
        var person2 = new Person { FirstName = "Zanele", LastName = "Dube", RsaIdNumber = "0303035009088" };
        db.People.AddRange(person1, person2);
        await db.SaveChangesAsync();

        db.CompanyLearners.AddRange(
            new CompanyLearner { PersonId = person1.Id, OrganisationId = org.Id, TrainingProviderId = provider.Id, QualificationTitle = "Diesel Mechanic" },
            new CompanyLearner { PersonId = person2.Id, OrganisationId = org.Id, TrainingProviderId = provider.Id, QualificationTitle = "Mechatronics Technician" }
        );
        await db.SaveChangesAsync();

        var learners = await service.GetEnrolledLearnersAsync(provider.Id);

        Assert.Equal(2, learners.Count);
        Assert.Contains(learners, l => l.LearnerFullName == "Sipho Mthembu" && l.QualificationTitle == "Diesel Mechanic");
        Assert.Contains(learners, l => l.LearnerFullName == "Zanele Dube" && l.QualificationTitle == "Mechatronics Technician");
    }

    [Fact]
    public async Task GetParticipatingEmployersAsync_ReturnsEmployersPlacingLearners()
    {
        var (factory, db, audit, service) = CreateTestContext();

        var employer = new Organisation { CompanyName = "BMW Rosslyn", SdlNumber = "L555000555" };
        var sdpOrg = new Organisation { CompanyName = "Ekurhuleni East TVET", SdlNumber = "L777000777" };
        db.Organisations.AddRange(employer, sdpOrg);
        await db.SaveChangesAsync();

        var provider = new TrainingProvider { OrganisationId = sdpOrg.Id, AccreditationNumber = "ACC-EKURHULENI" };
        db.TrainingProviders.Add(provider);
        await db.SaveChangesAsync();

        var person = new Person { FirstName = "Thabo", LastName = "Molefe", RsaIdNumber = "9901015009088" };
        db.People.Add(person);
        await db.SaveChangesAsync();

        db.CompanyLearners.Add(new CompanyLearner
        {
            PersonId = person.Id,
            OrganisationId = employer.Id,
            TrainingProviderId = provider.Id,
            QualificationTitle = "Automotive Body Repair"
        });
        await db.SaveChangesAsync();

        var employers = await service.GetParticipatingEmployersAsync(provider.Id);

        Assert.Single(employers);
        Assert.Equal("BMW Rosslyn", employers[0].CompanyName);
        Assert.Equal(1, employers[0].PlacedLearnersCount);
    }

    #endregion
}
