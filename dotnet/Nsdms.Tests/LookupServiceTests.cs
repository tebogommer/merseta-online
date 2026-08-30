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

    [Fact]
    public async Task SaveLookupItem_SoftDeactivation_PreservesRecordWithInactiveStatus()
    {
        // Arrange
        var contextFactory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var audit = new AuditService(contextFactory);
        var service = new LookupService(contextFactory, audit);

        var newCode = new LookupItemDto
        {
            Code = "TEST_CODE",
            Name = "Test Lookup Entry",
            Description = "Test for UI Standard T3",
            Active = true
        };

        // Act 1: Create
        await service.SaveLookupItemAsync("ProvinceType", newCode, "AdminUser");
        var items1 = await service.GetLookupItemsAsync("ProvinceType", "TEST_CODE");
        
        // Assert 1: Active
        Assert.Single(items1);
        Assert.True(items1[0].Active);

        // Act 2: Soft Deactivate (Clause 6.2)
        newCode.Active = false;
        await service.SaveLookupItemAsync("ProvinceType", newCode, "AdminUser");
        var items2 = await service.GetLookupItemsAsync("ProvinceType", "TEST_CODE");

        // Assert 2: Record preserved in database with Active = false
        Assert.Single(items2);
        Assert.False(items2[0].Active);
        Assert.Equal("TEST_CODE", items2[0].Code);
    }
}

