using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class CorporateHierarchyServiceTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, OrganisationHierarchyService service) CreateTestService()
    {
        var dbName = Guid.NewGuid().ToString();
        var factory = new TestDbContextFactory(dbName);
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var complianceEngine = new OrganisationComplianceEngine(factory, audit);
        var service = new OrganisationHierarchyService(factory, audit, complianceEngine);
        return (factory, db, service);
    }

    [Fact]
    public async Task GetCorporateTreeAsync_ThreeTierHierarchy_BuildsTreeWithCorrectChildren()
    {
        var (factory, db, service) = CreateTestService();

        // 1. Holding Company (Root)
        var holding = new Organisation
        {
            CompanyName = "Sasol Group Limited",
            SdlNumber = "L100000001",
            MainSdlNumber = "L100000001",
            IsActive = true,
            HoldingRelationshipType = "HOLDING_COMPANY",
            OwnershipPercentage = 100m
        };
        db.Organisations.Add(holding);
        await db.SaveChangesAsync();

        // 2. Subsidiary Level 1
        var sub1 = new Organisation
        {
            CompanyName = "Sasol Mining Pty Ltd",
            SdlNumber = "L100000002",
            MainSdlNumber = holding.SdlNumber,
            ParentOrganisationId = holding.Id,
            HoldingRelationshipType = "WHOLLY_OWNED_SUBSIDIARY",
            OwnershipPercentage = 100m,
            IsActive = true
        };
        db.Organisations.Add(sub1);
        await db.SaveChangesAsync();

        // 3. Subsidiary Level 2
        var sub2 = new Organisation
        {
            CompanyName = "Sasol Coal Supply Services",
            SdlNumber = "L100000003",
            MainSdlNumber = holding.SdlNumber,
            ParentOrganisationId = sub1.Id,
            HoldingRelationshipType = "MAJORITY_SUBSIDIARY",
            OwnershipPercentage = 75m,
            IsActive = true
        };
        db.Organisations.Add(sub2);
        await db.SaveChangesAsync();

        // Query tree from mid-level subsidiary perspective
        var tree = await service.GetCorporateTreeAsync(sub1.Id);

        Assert.NotNull(tree);
        Assert.Equal(holding.Id, tree.OrganisationId);
        Assert.Equal("Sasol Group Limited", tree.CompanyName);
        Assert.False(tree.IsCurrentOrganisation);
        Assert.Single(tree.Children);

        var childNode = tree.Children[0];
        Assert.Equal(sub1.Id, childNode.OrganisationId);
        Assert.True(childNode.IsCurrentOrganisation);
        Assert.Single(childNode.Children);

        var grandchildNode = childNode.Children[0];
        Assert.Equal(sub2.Id, grandchildNode.OrganisationId);
        Assert.Equal(75m, grandchildNode.OwnershipPercentage);
        Assert.False(grandchildNode.IsCurrentOrganisation);
    }

    [Fact]
    public async Task LinkParentOrganisationAsync_SelfLinking_ThrowsInvalidOperationException()
    {
        var (factory, db, service) = CreateTestService();

        var org = new Organisation
        {
            CompanyName = "Bidvest Limited",
            SdlNumber = "L200000001",
            IsActive = true
        };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() =>
            service.LinkParentOrganisationAsync(org.Id, org.Id, "HOLDING_COMPANY", 100m, "Tester")
        );

        Assert.Contains("own holding parent", ex.Message, StringComparison.OrdinalIgnoreCase);
    }

    [Fact]
    public async Task LinkParentOrganisationAsync_CircularReference_ThrowsInvalidOperationException()
    {
        var (factory, db, service) = CreateTestService();

        // Parent -> Child
        var parent = new Organisation
        {
            CompanyName = "Imperial Holdings Ltd",
            SdlNumber = "L300000001",
            IsActive = true
        };
        db.Organisations.Add(parent);
        await db.SaveChangesAsync();

        var child = new Organisation
        {
            CompanyName = "Imperial Logistics Pty Ltd",
            SdlNumber = "L300000002",
            ParentOrganisationId = parent.Id,
            IsActive = true
        };
        db.Organisations.Add(child);
        await db.SaveChangesAsync();

        // Attempt to make Child the parent of Parent (Parent -> Child -> Parent cycle)
        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() =>
            service.LinkParentOrganisationAsync(parent.Id, child.Id, "WHOLLY_OWNED_SUBSIDIARY", 100m, "Tester")
        );

        Assert.Contains("Circular corporate hierarchy detected", ex.Message, StringComparison.OrdinalIgnoreCase);
    }

    [Fact]
    public async Task GetCorporateGroupRollupAsync_AggregatesGroupMetricsAcrossSubsidiaries()
    {
        var (factory, db, service) = CreateTestService();

        var parent = new Organisation
        {
            CompanyName = "Transnet SOC Limited",
            SdlNumber = "L400000001",
            MainSdlNumber = "L400000001",
            IsActive = true
        };
        db.Organisations.Add(parent);
        await db.SaveChangesAsync();

        var sub = new Organisation
        {
            CompanyName = "Transnet Engineering",
            SdlNumber = "L400000002",
            MainSdlNumber = parent.SdlNumber,
            ParentOrganisationId = parent.Id,
            IsActive = true
        };
        db.Organisations.Add(sub);
        await db.SaveChangesAsync();

        // Add learners for both entities
        db.CompanyLearners.AddRange(
            new CompanyLearner { OrganisationId = parent.Id, PersonId = 1, StatusCode = "Registered" },
            new CompanyLearner { OrganisationId = sub.Id, PersonId = 2, StatusCode = "Registered" },
            new CompanyLearner { OrganisationId = sub.Id, PersonId = 3, StatusCode = "Registered" }
        );

        // Add levies for both entities
        db.LevyFileLines.AddRange(
            new LevyFileLine { SdlNumber = parent.SdlNumber, MandatoryLevyAmount = 50000m, DiscretionaryLevyAmount = 100000m },
            new LevyFileLine { SdlNumber = sub.SdlNumber, MandatoryLevyAmount = 25000m, DiscretionaryLevyAmount = 50000m }
        );
        await db.SaveChangesAsync();

        var rollup = await service.GetCorporateGroupRollupAsync(sub.Id);

        Assert.NotNull(rollup);
        Assert.Equal(parent.Id, rollup.RootOrganisationId);
        Assert.Equal("Transnet SOC Limited", rollup.RootCompanyName);
        Assert.Equal(2, rollup.TotalEntitiesCount);
        Assert.Equal(3, rollup.TotalActiveLearnersCount);
        Assert.Equal(225000m, rollup.TotalReconciledLevyAmount);
        Assert.Equal(2, rollup.FlattenedEntities.Count);
    }

    [Fact]
    public async Task UnlinkParentOrganisationAsync_ClearsParentAndMainSdl_PreservesEntity()
    {
        var (factory, db, service) = CreateTestService();

        var parent = new Organisation
        {
            CompanyName = "Naspers Limited",
            SdlNumber = "L500000001",
            IsActive = true
        };
        db.Organisations.Add(parent);
        await db.SaveChangesAsync();

        var sub = new Organisation
        {
            CompanyName = "Media24",
            SdlNumber = "L500000002",
            MainSdlNumber = parent.SdlNumber,
            ParentOrganisationId = parent.Id,
            HoldingRelationshipType = "MAJORITY_SUBSIDIARY",
            OwnershipPercentage = 85m,
            IsActive = true
        };
        db.Organisations.Add(sub);
        await db.SaveChangesAsync();

        var unlinked = await service.UnlinkParentOrganisationAsync(sub.Id, "Admin");
        Assert.True(unlinked);

        using var verifyDb = (NsdmsDbContext)factory.CreateDbContext();
        var updatedSub = await verifyDb.Organisations.AsNoTracking().FirstAsync(o => o.Id == sub.Id);
        Assert.Null(updatedSub.ParentOrganisationId);
        Assert.Null(updatedSub.MainSdlNumber);
        Assert.Null(updatedSub.HoldingRelationshipType);
    }
}
