using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Domain.Lookups;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

/// <summary>
/// Unit and model architecture tests verifying composite covering index configurations
/// for UNIMPL-001 (SicCodeType), UNIMPL-002 (OfoCodeType), and UNIMPL-003 (StatssaAreaCodeType).
/// </summary>
public class LookupIndexOptimizationTests
{
    [Fact]
    public void ModelBuilder_ConfiguresCompositeIndexes_ForHighVolumeLookups()
    {
        // Arrange
        var options = new DbContextOptionsBuilder<NsdmsDbContext>()
            .UseInMemoryDatabase(databaseName: "IndexTestDb_" + Guid.NewGuid().ToString())
            .Options;

        using var db = new NsdmsDbContext(options);
        var model = db.Model;

        // 1. Verify SicCodeType (UNIMPL-001)
        var sicEntity = model.FindEntityType(typeof(SicCodeType));
        Assert.NotNull(sicEntity);
        var sicCompositeIndex = sicEntity.GetIndexes()
            .FirstOrDefault(idx => idx.Properties.Count == 2 &&
                                   idx.Properties[0].Name == "Code" &&
                                   idx.Properties[1].Name == "Name");
        Assert.NotNull(sicCompositeIndex);
        Assert.Equal("IX_SicCodeType_Code_Name", sicCompositeIndex.GetDatabaseName());

        // 2. Verify OfoCodeType (UNIMPL-002)
        var ofoEntity = model.FindEntityType(typeof(OfoCodeType));
        Assert.NotNull(ofoEntity);
        var ofoCompositeIndex = ofoEntity.GetIndexes()
            .FirstOrDefault(idx => idx.Properties.Count == 2 &&
                                   idx.Properties[0].Name == "Code" &&
                                   idx.Properties[1].Name == "Name");
        Assert.NotNull(ofoCompositeIndex);
        Assert.Equal("IX_OfoCodeType_Code_Name", ofoCompositeIndex.GetDatabaseName());

        // 3. Verify StatssaAreaCodeType (UNIMPL-003)
        var statssaEntity = model.FindEntityType(typeof(StatssaAreaCodeType));
        Assert.NotNull(statssaEntity);
        var statssaCompositeIndex = statssaEntity.GetIndexes()
            .FirstOrDefault(idx => idx.Properties.Count == 2 &&
                                   idx.Properties[0].Name == "Code" &&
                                   idx.Properties[1].Name == "Name");
        Assert.NotNull(statssaCompositeIndex);
        Assert.Equal("IX_StatssaAreaCodeType_Code_Name", statssaCompositeIndex.GetDatabaseName());
    }

    [Fact]
    public async Task LookupService_GetSicCodes_WithSearchFilter_ExecutesSuccessfully()
    {
        // Arrange
        var contextFactory = new TestDbContextFactory(Guid.NewGuid().ToString());
        using (var db = (NsdmsDbContext)contextFactory.CreateDbContext())
        {
            db.SicCodeTypes.AddRange(
                new SicCodeType { Code = "SIC-TEST-01", Name = "Manufacture of Custom Test Vehicles", Active = true },
                new SicCodeType { Code = "SIC-TEST-02", Name = "Manufacture of Custom Metal Products", Active = true }
            );
            await db.SaveChangesAsync();
        }

        var audit = new AuditService(contextFactory);
        var service = new LookupService(contextFactory, audit);

        // Act
        var results = await service.GetSicCodesAsync("SIC-TEST-01");

        // Assert
        Assert.Single(results);
        Assert.Equal("SIC-TEST-01", results[0].Code);
        Assert.Contains("Test Vehicles", results[0].Name);
    }

    [Fact]
    public async Task LookupService_GetOfoCodes_WithSearchFilter_ExecutesSuccessfully()
    {
        // Arrange
        var contextFactory = new TestDbContextFactory(Guid.NewGuid().ToString());
        using (var db = (NsdmsDbContext)contextFactory.CreateDbContext())
        {
            db.OfoCodeTypes.AddRange(
                new OfoCodeType { Code = "2021-121904", Name = "Skills Development Facilitator", Active = true },
                new OfoCodeType { Code = "2021-651202", Name = "Welder (Artisan)", Active = true }
            );
            await db.SaveChangesAsync();
        }

        var audit = new AuditService(contextFactory);
        var service = new LookupService(contextFactory, audit);

        // Act
        var results = await service.GetOfoCodesAsync("Welder");

        // Assert
        Assert.Single(results);
        Assert.Equal("2021-651202", results[0].Code);
        Assert.Contains("Welder", results[0].Name);
    }

    [Fact]
    public async Task LookupService_GetStatssaAreaCodes_WithSearchFilter_ExecutesSuccessfully()
    {
        // Arrange
        var contextFactory = new TestDbContextFactory(Guid.NewGuid().ToString());
        using (var db = (NsdmsDbContext)contextFactory.CreateDbContext())
        {
            db.StatssaAreaCodeTypes.AddRange(
                new StatssaAreaCodeType { Code = "GT001", Name = "City of Johannesburg", Active = true },
                new StatssaAreaCodeType { Code = "WC001", Name = "City of Cape Town", Active = true }
            );
            await db.SaveChangesAsync();
        }

        var audit = new AuditService(contextFactory);
        var service = new LookupService(contextFactory, audit);

        // Act
        var results = await service.GetStatssaAreaCodesAsync("Johannesburg");

        // Assert
        Assert.Single(results);
        Assert.Equal("GT001", results[0].Code);
        Assert.Contains("Johannesburg", results[0].Name);
    }
}
