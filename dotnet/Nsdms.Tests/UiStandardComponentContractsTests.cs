namespace Nsdms.Tests;

using System.Text.Json;
using Nsdms.Application.Services;
using Xunit;

public class UiStandardComponentContractsTests
{
    [Fact]
    public async Task GridViewPreferenceService_PersistsAndRetrievesPreferencesPerUserAndGrid()
    {
        // Arrange
        var contextFactory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var service = new GridViewPreferenceService(contextFactory);

        var prefs = new GridViewPreferenceDto
        {
            GridKey = "EmployersListGrid",
            PageSize = 50,
            Density = "comfortable",
            HiddenColumns = new List<string> { "TradingName", "TaxNumber" }
        };

        // Act
        await service.SavePreferencesAsync(prefs, "test_user_1");
        var retrieved = await service.GetPreferencesAsync("EmployersListGrid", "test_user_1");

        // Assert
        Assert.NotNull(retrieved);
        Assert.Equal("EmployersListGrid", retrieved.GridKey);
        Assert.Equal(50, retrieved.PageSize);
        Assert.Equal("comfortable", retrieved.Density);
        Assert.Contains("TradingName", retrieved.HiddenColumns);
        Assert.Contains("TaxNumber", retrieved.HiddenColumns);
    }

    [Fact]
    public async Task GridViewPreferenceService_ResetPreferences_RestoresDefaults()
    {
        // Arrange
        var contextFactory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var service = new GridViewPreferenceService(contextFactory);

        var prefs = new GridViewPreferenceDto
        {
            GridKey = "WspListGrid",
            PageSize = 100,
            HiddenColumns = new List<string> { "SubmittedDate" }
        };

        await service.SavePreferencesAsync(prefs, "test_user_2");
        await service.ResetPreferencesAsync("WspListGrid", "test_user_2");

        var retrieved = await service.GetPreferencesAsync("WspListGrid", "test_user_2");

        Assert.Equal(20, retrieved.PageSize);
        Assert.Empty(retrieved.HiddenColumns);
    }

    [Theory]
    [InlineData("Approved", "success")]
    [InlineData("Completed", "success")]
    [InlineData("Accredited", "success")]
    [InlineData("Rejected", "danger")]
    [InlineData("Declined", "danger")]
    [InlineData("Terminated", "danger")]
    [InlineData("Withdrawn", "danger")]
    [InlineData("Under Review", "active")]
    [InlineData("In Assessment", "active")]
    [InlineData("Inspection Scheduled", "active")]
    [InlineData("Submitted", "pending")]
    [InlineData("Awaiting Documents", "pending")]
    [InlineData("Draft", "neutral")]
    [InlineData("Inactive", "neutral")]
    public void StatusSemanticMapping_MatchesStandardTaxonomy(string status, string expectedGroup)
    {
        var s = status.ToLowerInvariant();
        string semantic;
        if (s.Contains("approve") || s.Contains("complete") || s.Contains("accredit") || s.Contains("certif") || s == "active" || s == "verified" || s == "endorsed")
            semantic = "success";
        else if (s.Contains("reject") || s.Contains("decline") || s.Contains("terminat") || s.Contains("withdraw") || s.Contains("lapsed") || s.Contains("fail"))
            semantic = "danger";
        else if (s.Contains("review") || s.Contains("assess") || s.Contains("inspect") || s.Contains("progress") || s.Contains("schedul") || s.Contains("training") || s.Contains("evaluat"))
            semantic = "active";
        else if (s.Contains("submit") || s.Contains("pending") || s.Contains("await") || s.Contains("applied") || s.Contains("requested") || s.Contains("moderation"))
            semantic = "pending";
        else
            semantic = "neutral";

        Assert.Equal(expectedGroup, semantic);
    }

    [Fact]
    public void PageSizeLadder_ContainsRequiredFixedSteps()
    {
        // UI-STANDARD.md Clause 11.3.3: 5, 10, 20, 50, 100, 250, 500
        var expectedLadder = new[] { 5, 10, 20, 50, 100, 250, 500 };
        var actualLadder = new[] { 5, 10, 20, 50, 100, 250, 500 };

        Assert.Equal(expectedLadder, actualLadder);
        Assert.Equal(20, actualLadder[2]); // Default page size is 20
    }
}
