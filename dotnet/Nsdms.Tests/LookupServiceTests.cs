using Nsdms.Application.Services;
using Xunit;

namespace Nsdms.Tests;

public class LookupServiceTests
{
    [Fact]
    public async Task GetAllLookupMetadata_ReturnsRegisteredLookups()
    {
        // Arrange
        var service = new LookupService(null!, null!);

        // Act
        var lookups = await service.GetAllLookupMetadataAsync();

        // Assert
        Assert.NotNull(lookups);
        Assert.True(lookups.Count >= 20);
        Assert.Contains(lookups, x => x.TableName == "ProvinceType");
        Assert.Contains(lookups, x => x.TableName == "GenderType");
        Assert.Contains(lookups, x => x.TableName == "SectorType");
    }

    [Fact]
    public async Task GetAllLookupMetadata_WithCategoryFilter_FiltersCorrectly()
    {
        // Arrange
        var service = new LookupService(null!, null!);

        // Act
        var demographicLookups = await service.GetAllLookupMetadataAsync(category: "Core & Demographics");

        // Assert
        Assert.NotEmpty(demographicLookups);
        Assert.All(demographicLookups, x => Assert.Equal("Core & Demographics", x.Category));
    }

    [Fact]
    public async Task GetAllLookupMetadata_WithSearch_ReturnsMatchingLookups()
    {
        // Arrange
        var service = new LookupService(null!, null!);

        // Act
        var results = await service.GetAllLookupMetadataAsync(search: "Province");

        // Assert
        Assert.Single(results);
        Assert.Equal("ProvinceType", results[0].TableName);
    }
}
