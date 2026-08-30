using Microsoft.EntityFrameworkCore;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class SampleDataSeederTests
{
    [Fact]
    public async Task SeedSampleDataAsync_PopulatesAllModulesCleanly()
    {
        var options = new DbContextOptionsBuilder<NsdmsDbContext>()
            .UseInMemoryDatabase(databaseName: Guid.NewGuid().ToString())
            .Options;

        var db = new NsdmsDbContext(options);

        // Run seeder
        await SampleDataSeeder.SeedSampleDataAsync(db);

        // Verify People
        var people = await db.People.ToListAsync();
        Assert.True(people.Count >= 7);

        // Verify Organisations
        var orgs = await db.Organisations.ToListAsync();
        Assert.True(orgs.Count >= 5);
        Assert.Contains(orgs, o => o.CompanyName.Contains("Toyota"));
        Assert.Contains(orgs, o => o.CompanyName.Contains("Sasol"));

        // Verify Providers
        var providers = await db.TrainingProviders.Include(p => p.Qualifications).ToListAsync();
        Assert.NotEmpty(providers);
        Assert.NotEmpty(providers.First().Qualifications);

        // Verify WSPs
        var wsps = await db.WspSubmissions.Include(w => w.EmploymentSummaries).Include(w => w.TrainingPlans).ToListAsync();
        Assert.NotEmpty(wsps);
        Assert.NotEmpty(wsps.First().EmploymentSummaries);

        // Verify Grants
        var windows = await db.GrantFundingWindows.ToListAsync();
        var grants = await db.GrantApplications.ToListAsync();
        var budgets = await db.GrantProjectBudgets.ToListAsync();
        Assert.NotEmpty(windows);
        Assert.NotEmpty(grants);
        Assert.NotEmpty(budgets);

        // Verify Levies
        var levyFiles = await db.LevyFiles.ToListAsync();
        var levyLines = await db.LevyFileLines.ToListAsync();
        Assert.NotEmpty(levyFiles);
        Assert.NotEmpty(levyLines);

        // Verify ETQA
        var assessors = await db.EtqaAssessors.ToListAsync();
        var scopes = await db.AssessorModeratorScopes.ToListAsync();
        Assert.NotEmpty(assessors);
        Assert.NotEmpty(scopes);

        // Verify Workplace Approvals
        var approvals = await db.WorkplaceApprovals.ToListAsync();
        var mentors = await db.WorkplaceApprovalMentors.ToListAsync();
        var toolItems = await db.WorkplaceApprovalToolLists.ToListAsync();
        Assert.NotEmpty(approvals);
        Assert.NotEmpty(mentors);
        Assert.NotEmpty(toolItems);

        // Verify Learners & Trade Tests
        var learners = await db.CompanyLearners.ToListAsync();
        var tradeTests = await db.LearnerTradeTests.ToListAsync();
        Assert.NotEmpty(learners);
        Assert.NotEmpty(tradeTests);
        Assert.Contains(tradeTests, t => t.ResultStatusCode == "Competent");

        // Verify Idempotency - running second time shouldn't duplicate
        var initialPeopleCount = people.Count;
        await SampleDataSeeder.SeedSampleDataAsync(db);
        var secondPeopleCount = await db.People.CountAsync();
        Assert.Equal(initialPeopleCount, secondPeopleCount);
    }
}
