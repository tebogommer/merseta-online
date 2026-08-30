using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Domain.Lookups;
using Xunit;

namespace Nsdms.Tests;

public class SetmisAndAnalyticsTests
{
    [Fact]
    public async Task GenerateSetmisFile_File500_ShouldReturnPipeDelimitedHeadersAndProviders()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var setmisService = new SetmisService(factory);

        using (var ctx = factory.CreateDbContext())
        {
            var org = new Organisation { CompanyName = "Eskom Academy", ProvinceCode = "GP" };
            ctx.Organisations.Add(org);
            await ctx.SaveChangesAsync();

            var provider = new TrainingProvider
            {
                OrganisationId = org.Id,
                AccreditationNumber = "ACC/2026/099",
                ProviderTypeCode = "Accredited",
                AccreditationStartDate = new DateTime(2026, 1, 1),
                AccreditationEndDate = new DateTime(2029, 12, 31),
                ProviderStatusCode = "Accredited"
            };
            ctx.TrainingProviders.Add(provider);
            await ctx.SaveChangesAsync();
        }

        // Act
        var content = await setmisService.GenerateSetmisFileContentAsync("500");

        // Assert
        Assert.Contains("PROVIDER_ID|LEGAL_NAME|TRADING_NAME|ACCREDITATION_NO", content);
        Assert.Contains("Eskom Academy", content);
    }

    [Fact]
    public async Task ValidateSetmisData_ShouldCatchInvalidRsaIdAndMissingAccreditation()
    {
        // Arrange
        var dbName = Guid.NewGuid().ToString();
        var factory = new TestDbContextFactory(dbName);
        var setmisService = new SetmisService(factory);

        using (var ctx = factory.CreateDbContext())
        {
            var org = new Organisation { CompanyName = "Test Firm", SdlNumber = "L123456789" };
            ctx.Organisations.Add(org);
            await ctx.SaveChangesAsync();

            var person = new Person { FirstName = "Invalid", LastName = "User", RsaIdNumber = "123" }; // < 13 chars
            ctx.People.Add(person);
            await ctx.SaveChangesAsync();

            var learner = new CompanyLearner
            {
                OrganisationId = org.Id,
                PersonId = person.Id,
                RegistrationDate = DateTime.Today,
                ExpectedCompletionDate = DateTime.Today.AddDays(-10), // Invalid date sequence
                QualificationTitle = "Welder"
            };
            ctx.CompanyLearners.Add(learner);

            var provider = new TrainingProvider
            {
                OrganisationId = org.Id,
                AccreditationNumber = "", // Empty accreditation
                AccreditationStartDate = DateTime.Today,
                AccreditationEndDate = DateTime.Today.AddYears(1)
            };
            ctx.TrainingProviders.Add(provider);

            await ctx.SaveChangesAsync();
        }

        // Act
        var errors = await setmisService.ValidateSetmisDataAsync();

        // Assert
        Assert.Contains(errors, e => e.FieldName == "RsaIdNumber" && e.Severity == "Error");
        Assert.Contains(errors, e => e.FieldName == "ExpectedCompletionDate" && e.Severity == "Error");
        Assert.Contains(errors, e => e.FieldName == "AccreditationNumber" && e.Severity == "Error");
    }

    [Fact]
    public async Task CreateSubmissionBatch_ShouldGenerateAndLogAudit()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var setmisService = new SetmisService(factory);

        // Act
        var batch = await setmisService.CreateSubmissionBatchAsync("2026-Q2", "ALL", "compliance@merseta.org.za");

        // Assert
        Assert.NotNull(batch);
        Assert.True(batch.Id > 0);
        Assert.Contains("SETMIS-2026-Q2", batch.BatchNumber);

        var batches = await setmisService.GetSubmissionBatchesAsync();
        Assert.Single(batches);
    }

    [Fact]
    public async Task UpdateBatchStatus_ShouldRecordDhetAcknowledgment()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var setmisService = new SetmisService(factory);
        var batch = await setmisService.CreateSubmissionBatchAsync("2026-Q1", "ALL", "compliance@merseta.org.za");

        // Act
        var updated = await setmisService.UpdateBatchStatusAsync(batch.Id, "Accepted by DHET", "DHET-ACK-20260830-101", "Compliance Officer");

        // Assert
        Assert.True(updated);
        var batches = await setmisService.GetSubmissionBatchesAsync();
        Assert.Equal("Accepted by DHET", batches[0].Status);
        Assert.Equal("DHET-ACK-20260830-101", batches[0].DhetAcknowledgmentRef);
    }

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
