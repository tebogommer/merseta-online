using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class TrainingProviderServiceTests
{
    private static NsdmsDbContext CreateInMemoryDbContext()
    {
        var options = new DbContextOptionsBuilder<NsdmsDbContext>()
            .UseInMemoryDatabase(databaseName: Guid.NewGuid().ToString())
            .Options;

        return new NsdmsDbContext(options);
    }

    [Fact]
    public async Task CreateAsync_ValidProvider_CreatesAndLogsAudit()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new TrainingProviderService(db, audit);

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
    public async Task CreateAsync_MissingAccreditationNumber_ThrowsArgumentException()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new TrainingProviderService(db, audit);

        var provider = new TrainingProvider { AccreditationNumber = "" };

        // Act & Assert
        await Assert.ThrowsAsync<ArgumentException>(() => service.CreateAsync(provider));
    }

    [Fact]
    public async Task GetAllAsync_WithSearchQuery_FiltersCorrectly()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new TrainingProviderService(db, audit);

        var org1 = new Organisation { CompanyName = "Skills Center Alpha", SdlNumber = "L111" };
        var org2 = new Organisation { CompanyName = "Technical College Beta", SdlNumber = "L222" };
        db.Organisations.AddRange(org1, org2);
        await db.SaveChangesAsync();

        await service.CreateAsync(new TrainingProvider { OrganisationId = org1.Id, AccreditationNumber = "ACC-ALPHA-01" });
        await service.CreateAsync(new TrainingProvider { OrganisationId = org2.Id, AccreditationNumber = "ACC-BETA-02" });

        // Act
        var results = await service.GetAllAsync("ALPHA");

        // Assert
        Assert.Single(results);
        Assert.Equal("ACC-ALPHA-01", results[0].AccreditationNumber);
    }

    [Fact]
    public async Task GetByIdAsync_ReturnsWithQualificationsAndUnitStandards()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new TrainingProviderService(db, audit);

        var org = new Organisation { CompanyName = "Complete Academy", SdlNumber = "L999" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var provider = await service.CreateAsync(new TrainingProvider { OrganisationId = org.Id, AccreditationNumber = "ACC-COMPLETE" });

        await service.AddQualificationAsync(new TrainingProviderQualification
        {
            TrainingProviderId = provider.Id,
            SaqaQualificationId = 58761,
            QualificationTitle = "National Certificate: Automotive Repair",
            NqfLevel = 4
        });

        await service.AddUnitStandardAsync(new TrainingProviderUnitStandard
        {
            TrainingProviderId = provider.Id,
            UnitStandardId = 119457,
            UnitStandardTitle = "Interpret and use information from texts",
            Credits = 5
        });

        // Act
        var result = await service.GetByIdAsync(provider.Id);

        // Assert
        Assert.NotNull(result);
        Assert.Single(result.Qualifications);
        Assert.Single(result.UnitStandards);
        Assert.Equal(58761, result.Qualifications.First().SaqaQualificationId);
        Assert.Equal(119457, result.UnitStandards.First().UnitStandardId);
    }

    [Fact]
    public async Task UpdateAsync_UpdatesProviderAndLogsAudit()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new TrainingProviderService(db, audit);

        var org = new Organisation { CompanyName = "Update Org", SdlNumber = "L111" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var provider = await service.CreateAsync(new TrainingProvider { OrganisationId = org.Id, AccreditationNumber = "ACC-INITIAL", MaxLearnerCapacity = 50 });

        provider.AccreditationNumber = "ACC-UPDATED";
        provider.MaxLearnerCapacity = 150;

        // Act
        var updated = await service.UpdateAsync(provider, "UpdaterUser");

        // Assert
        Assert.Equal("ACC-UPDATED", updated.AccreditationNumber);
        Assert.Equal(150, updated.MaxLearnerCapacity);
        Assert.Equal("UpdaterUser", updated.ModifiedBy);

        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "TrainingProvider" && a.ActionName == "Update");
        Assert.NotNull(auditLog);
    }

    [Fact]
    public async Task DeleteAsync_ExistingProvider_RemovesAndLogsAudit()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new TrainingProviderService(db, audit);

        var org = new Organisation { CompanyName = "Delete Org", SdlNumber = "L222" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var provider = await service.CreateAsync(new TrainingProvider { OrganisationId = org.Id, AccreditationNumber = "ACC-TO-DELETE" });

        // Act
        var deleted = await service.DeleteAsync(provider.Id, "Admin");

        // Assert
        Assert.True(deleted);
        var inDb = await db.TrainingProviders.FindAsync(provider.Id);
        Assert.Null(inDb);

        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "TrainingProvider" && a.ActionName == "Delete");
        Assert.NotNull(auditLog);
    }

    [Fact]
    public async Task QualificationsAndUnitStandards_AddAndRemoveOperations()
    {
        // Arrange
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new TrainingProviderService(db, audit);

        var org = new Organisation { CompanyName = "Sub Test Org", SdlNumber = "L333" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var provider = await service.CreateAsync(new TrainingProvider { OrganisationId = org.Id, AccreditationNumber = "ACC-SUB-TEST" });

        // Act - Add Qual
        var qual = await service.AddQualificationAsync(new TrainingProviderQualification
        {
            TrainingProviderId = provider.Id,
            SaqaQualificationId = 49121,
            QualificationTitle = "FET Certificate: Computer Programming"
        });
        Assert.True(qual.Id > 0);

        // Act - Add Unit Standard
        var us = await service.AddUnitStandardAsync(new TrainingProviderUnitStandard
        {
            TrainingProviderId = provider.Id,
            UnitStandardId = 115358,
            UnitStandardTitle = "Write computer programs using design specifications",
            Credits = 10
        });
        Assert.True(us.Id > 0);

        // Act - Remove Qual
        var removedQual = await service.RemoveQualificationAsync(qual.Id);
        Assert.True(removedQual);

        // Act - Remove US
        var removedUs = await service.RemoveUnitStandardAsync(us.Id);
        Assert.True(removedUs);

        // Assert
        var checkQual = await db.TrainingProviderQualifications.FindAsync(qual.Id);
        var checkUs = await db.TrainingProviderUnitStandards.FindAsync(us.Id);
        Assert.Null(checkQual);
        Assert.Null(checkUs);
    }
}
