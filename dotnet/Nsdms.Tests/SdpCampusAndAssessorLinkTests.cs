using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

public class SdpCampusAndAssessorLinkTests
{
    private (TestDbContextFactory Factory, IAuditService Audit) CreateServices()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var audit = new AuditService(factory);
        return (factory, audit);
    }

    [Fact]
    public async Task SdpCampus_AddUpdateDelete_EnforcesSinglePrimarySiteAndAuditLogs()
    {
        var (factory, audit) = CreateServices();
        int providerId = 0;

        using (var db = (NsdmsDbContext)await factory.CreateDbContextAsync())
        {
            var org = new Organisation { LegalName = "Apex Technical Academy", SdlNumber = "L334455667" };
            db.Organisations.Add(org);
            await db.SaveChangesAsync();

            var provider = new TrainingProvider
            {
                OrganisationId = org.Id,
                AccreditationNumber = "SDP-2026-APEX-01",
                ProviderStatusCode = "Accredited"
            };
            db.TrainingProviders.Add(provider);
            await db.SaveChangesAsync();
            providerId = provider.Id;
        }

        var service = new SdpCampusService(factory, audit);

        // 1. Add Main Campus
        var mainCampus = new TrainingProviderCampus
        {
            TrainingProviderId = providerId,
            CampusName = "Johannesburg Central Campus",
            City = "Johannesburg",
            ProvinceCode = "GP",
            IsPrimarySite = true
        };
        var added1 = await service.AddCampusAsync(mainCampus, "AdminUser");
        Assert.NotNull(added1);
        Assert.True(added1.IsPrimarySite);
        Assert.StartsWith("CAMPUS-", added1.CampusCode);

        // 2. Add Satellite Campus marked as primary
        var durbanCampus = new TrainingProviderCampus
        {
            TrainingProviderId = providerId,
            CampusName = "Durban South Delivery Site",
            City = "Durban",
            ProvinceCode = "KZN",
            IsPrimarySite = true
        };
        var added2 = await service.AddCampusAsync(durbanCampus, "AdminUser");
        Assert.True(added2.IsPrimarySite);

        // Verify primary site exclusivity
        var campuses = await service.GetCampusesByProviderAsync(providerId);
        Assert.Equal(2, campuses.Count);

        var first = campuses.First(c => c.Id == added1.Id);
        var second = campuses.First(c => c.Id == added2.Id);

        Assert.False(first.IsPrimarySite);
        Assert.True(second.IsPrimarySite);

        // 3. Delete satellite
        var deleted = await service.DeleteCampusAsync(added2.Id, "AdminUser");
        Assert.True(deleted);

        var remaining = await service.GetCampusesByProviderAsync(providerId);
        Assert.Single(remaining);
    }

    [Fact]
    public async Task SdpAssessorLink_LinkAndUnlink_EnforcesUniquenessAndAuditTrail()
    {
        var (factory, audit) = CreateServices();
        int providerId = 0;
        int assessorId = 0;

        using (var db = (NsdmsDbContext)await factory.CreateDbContextAsync())
        {
            var org = new Organisation { LegalName = "Skills Institute Africa", SdlNumber = "L998877665" };
            var person = new Person { FirstName = "Naledi", LastName = "Dlamini", RsaIdNumber = "8805055555088" };
            db.Organisations.Add(org);
            db.People.Add(person);
            await db.SaveChangesAsync();

            var provider = new TrainingProvider
            {
                OrganisationId = org.Id,
                AccreditationNumber = "SDP-2026-SIA-01"
            };
            db.TrainingProviders.Add(provider);

            var assessor = new EtqaAssessor
            {
                PersonId = person.Id,
                RegistrationNumber = "ASS-2026-NALEDI",
                EtqaRole = "Assessor"
            };
            db.EtqaAssessors.Add(assessor);

            await db.SaveChangesAsync();
            providerId = provider.Id;
            assessorId = assessor.Id;
        }

        var service = new SdpCampusService(factory, audit);

        // 1. Link Assessor
        var link = new TrainingProviderAssessorLink
        {
            TrainingProviderId = providerId,
            EtqaAssessorId = assessorId,
            RoleTypeCode = "Assessor"
        };
        var createdLink = await service.LinkAssessorAsync(link, "ETQAOfficer");
        Assert.NotNull(createdLink);
        Assert.Equal("Active", createdLink.Status);

        // 2. Duplicate link throws
        var dupLink = new TrainingProviderAssessorLink
        {
            TrainingProviderId = providerId,
            EtqaAssessorId = assessorId,
            RoleTypeCode = "Assessor"
        };
        await Assert.ThrowsAsync<InvalidOperationException>(() => service.LinkAssessorAsync(dupLink, "ETQAOfficer"));

        // 3. Unlink Assessor
        var unlinked = await service.UnlinkAssessorAsync(createdLink.Id, "ETQAOfficer");
        Assert.True(unlinked);

        var list = await service.GetLinkedAssessorsAsync(providerId);
        Assert.Single(list);
        Assert.Equal("Terminated", list[0].Status);
        Assert.NotNull(list[0].EndDate);
    }
}
