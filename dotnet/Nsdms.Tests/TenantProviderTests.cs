using Microsoft.EntityFrameworkCore;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

public class TenantProviderTests
{
    private static DbContextOptions<NsdmsDbContext> CreateOptions(string dbName)
    {
        return new DbContextOptionsBuilder<NsdmsDbContext>()
            .UseInMemoryDatabase(databaseName: dbName)
            .Options;
    }

    [Fact]
    public async Task QueryFilter_ShouldFilterWspSubmissions_ForNonAdminTenant()
    {
        var dbName = Guid.NewGuid().ToString();
        var options = CreateOptions(dbName);

        // Seed data using admin context
        using (var seedDb = new NsdmsDbContext(options, new DefaultTenantProvider(null, isAdmin: true)))
        {
            seedDb.WspSubmissions.AddRange(
                new WspSubmission { Id = 1, OrganisationId = 1, ReferenceNumber = "WSP-001", FinYear = 2026 },
                new WspSubmission { Id = 2, OrganisationId = 2, ReferenceNumber = "WSP-002", FinYear = 2026 },
                new WspSubmission { Id = 3, OrganisationId = 1, ReferenceNumber = "WSP-003", FinYear = 2025 }
            );
            await seedDb.SaveChangesAsync();
        }

        // Query as Organisation 1 tenant (non-admin)
        var tenant1 = new DefaultTenantProvider(organisationId: 1, isAdmin: false);
        using (var tenantDb = new NsdmsDbContext(options, tenant1))
        {
            var results = await tenantDb.WspSubmissions.ToListAsync();
            Assert.Equal(2, results.Count);
            Assert.All(results, r => Assert.Equal(1, r.OrganisationId));
        }

        // Query as Organisation 2 tenant (non-admin)
        var tenant2 = new DefaultTenantProvider(organisationId: 2, isAdmin: false);
        using (var tenantDb = new NsdmsDbContext(options, tenant2))
        {
            var results = await tenantDb.WspSubmissions.ToListAsync();
            Assert.Single(results);
            Assert.Equal(2, results[0].OrganisationId);
        }

        // Query as Admin (global visibility)
        var adminTenant = new DefaultTenantProvider(null, isAdmin: true);
        using (var adminDb = new NsdmsDbContext(options, adminTenant))
        {
            var results = await adminDb.WspSubmissions.ToListAsync();
            Assert.Equal(3, results.Count);
        }

        // Verify IgnoreQueryFilters bypasses tenant filter
        using (var tenantDb = new NsdmsDbContext(options, tenant1))
        {
            var results = await tenantDb.WspSubmissions.IgnoreQueryFilters().ToListAsync();
            Assert.Equal(3, results.Count);
        }
    }

    [Fact]
    public async Task QueryFilter_ShouldFilterGrantApplications_ForNonAdminTenant()
    {
        var dbName = Guid.NewGuid().ToString();
        var options = CreateOptions(dbName);

        using (var seedDb = new NsdmsDbContext(options, new DefaultTenantProvider(null, isAdmin: true)))
        {
            seedDb.GrantApplications.AddRange(
                new GrantApplication { Id = 1, OrganisationId = 10, ApplicationNumber = "DG-001", ProjectTitle = "Project A" },
                new GrantApplication { Id = 2, OrganisationId = 20, ApplicationNumber = "DG-002", ProjectTitle = "Project B" }
            );
            await seedDb.SaveChangesAsync();
        }

        var tenant10 = new DefaultTenantProvider(organisationId: 10, isAdmin: false);
        using (var tenantDb = new NsdmsDbContext(options, tenant10))
        {
            var results = await tenantDb.GrantApplications.ToListAsync();
            Assert.Single(results);
            Assert.Equal("DG-001", results[0].ApplicationNumber);
        }
    }

    [Fact]
    public async Task QueryFilter_ShouldFilterWorkplaceApprovals_ForNonAdminTenant()
    {
        var dbName = Guid.NewGuid().ToString();
        var options = CreateOptions(dbName);

        using (var seedDb = new NsdmsDbContext(options, new DefaultTenantProvider(null, isAdmin: true)))
        {
            seedDb.WorkplaceApprovals.AddRange(
                new WorkplaceApproval { Id = 1, OrganisationId = 100, QualificationTitle = "Fitter and Turner" },
                new WorkplaceApproval { Id = 2, OrganisationId = 200, QualificationTitle = "Boilermaker" }
            );
            await seedDb.SaveChangesAsync();
        }

        var tenant100 = new DefaultTenantProvider(organisationId: 100, isAdmin: false);
        using (var tenantDb = new NsdmsDbContext(options, tenant100))
        {
            var results = await tenantDb.WorkplaceApprovals.ToListAsync();
            Assert.Single(results);
            Assert.Equal("Fitter and Turner", results[0].QualificationTitle);
        }
    }
}
