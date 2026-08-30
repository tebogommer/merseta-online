using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Domain.Lookups;
using Xunit;

namespace Nsdms.Tests;

public class AnalyticsServiceTests
{
    [Fact]
    public async Task GetSspAnalytics_ShouldReturnAggregationsAndTotals()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var analyticsService = new AnalyticsService(factory);

        using (var ctx = factory.CreateDbContext())
        {
            var person = new Person { FirstName = "Themba", LastName = "Nkosi", EquityCode = "1" };
            ctx.People.Add(person);
            await ctx.SaveChangesAsync();

            var learner = new CompanyLearner { PersonId = person.Id, Person = person, QualificationTitle = "Fitter" };
            ctx.CompanyLearners.Add(learner);

            await ctx.SaveChangesAsync();
        }

        // Act
        var chambers = await analyticsService.GetSspChamberMetricsAsync();
        var equities = await analyticsService.GetSspEquityMetricsAsync();
        var provinces = await analyticsService.GetSspProvincialMetricsAsync();
        var scarce = await analyticsService.GetSspScarceSkillsAsync();
        var (committed, disbursed, rebates) = await analyticsService.GetFinancialOverviewAsync();

        // Assert
        Assert.NotEmpty(chambers);
        Assert.Equal(4, equities.Count);
        Assert.Equal(100.0, equities.First(e => e.DemographicGroup == "African").Percentage);
        Assert.NotEmpty(provinces);
        Assert.NotEmpty(scarce);
        Assert.True(scarce.Count >= 5);
    }
}
