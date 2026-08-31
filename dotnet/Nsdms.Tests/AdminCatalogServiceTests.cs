using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Domain.Security;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class AdminCatalogServiceTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AdminCatalogService adminService, SystemConfigurationService configService, FeatureFlagService flagService) CreateContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var inMemorySettings = new Dictionary<string, string?>
        {
            { "General:AppVersion", "2026.8.0" }
        };
        var config = new ConfigurationBuilder().AddInMemoryCollection(inMemorySettings).Build();

        var configService = new SystemConfigurationService(factory, config, audit);
        var flagService = new FeatureFlagService(factory, config, audit);
        var lookupService = new LookupService(factory, audit);
        var roleService = new RolePermissionService(factory, audit);

        var adminService = new AdminCatalogService(
            factory,
            configService,
            flagService,
            lookupService,
            roleService,
            audit
        );

        // Seed initial test data
        db.SystemConfigs.AddRange(
            new SystemConfig
            {
                ConfigKey = "Finance.MandatoryGrantRebatePercent",
                ConfigValue = "20.0",
                ConfigCategory = "Finance",
                Description = "Rebate percentage allocated for approved Mandatory Grant WSP submissions.",
                DataType = "Number",
                IsActive = true,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = "Admin"
            },
            new SystemConfig
            {
                ConfigKey = "Wsp.SubmissionDeadlineMonth",
                ConfigValue = "4",
                ConfigCategory = "Compliance",
                Description = "Statutory annual Workplace Skills Plan submission month deadline.",
                DataType = "Number",
                IsActive = true,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = "Admin"
            }
        );

        db.SystemFeatureFlags.AddRange(
            new SystemFeatureFlag
            {
                FeatureKey = "Integrations.DynamicsGp",
                FeatureName = "Dynamics GP ERP Integration",
                Category = "Integrations",
                Description = "Live automated disbursement vouchers posted into Microsoft Dynamics GP.",
                IsEnabled = false,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = "Admin"
            },
            new SystemFeatureFlag
            {
                FeatureKey = "Integrations.LiveSarsFtp",
                FeatureName = "Live SARS FTP File Ingestion",
                Category = "Integrations",
                Description = "Live automated extraction of monthly SARS levy files via secure SFTP.",
                IsEnabled = false,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = "Admin"
            }
        );

        db.Roles.Add(new ApplicationRole
        {
            Name = "SuperAdmin",
            NormalizedName = "SUPERADMIN",
            Description = "Unrestricted enterprise root administrator with full CASL ability.",
            Active = true
        });

        db.SaveChanges();

        return (factory, db, adminService, configService, flagService);
    }

    [Fact]
    public async Task GetCatalogIndexAsync_ReturnsCompleteCatalogAndTelemetry()
    {
        var (factory, db, adminService, configService, flagService) = CreateContext();

        // Act
        var result = await adminService.GetCatalogIndexAsync();

        // Assert
        Assert.NotNull(result);
        Assert.NotNull(result.Telemetry);
        Assert.True(result.Telemetry.IsDatabaseConnected);
        Assert.NotEmpty(result.AllItems);
        Assert.NotEmpty(result.QuickAccessModules);
        Assert.NotEmpty(result.CategoryGroups);

        // Check primary modules exist
        Assert.Contains(result.AllItems, i => i.Key == "MOD-SYSTEM-SETTINGS");
        Assert.Contains(result.AllItems, i => i.Key == "MOD-SECURITY-ROLES");
        Assert.Contains(result.AllItems, i => i.Key == "MOD-LOOKUPS-HUB");
        Assert.Contains(result.AllItems, i => i.Key == "MOD-AUDIT-TRAIL");
    }

    [Fact]
    public async Task SearchAsync_WithQuery_FiltersCorrectly()
    {
        var (factory, db, adminService, configService, flagService) = CreateContext();

        // Act - Search for 'Mandatory'
        var results = await adminService.SearchAsync("Mandatory");

        // Assert
        Assert.NotEmpty(results);
        Assert.Contains(results, i => i.Key.Contains("Mandatory") || i.Title.Contains("Mandatory") || i.Description.Contains("Mandatory"));
    }

    [Fact]
    public async Task SearchAsync_WithFeatureFlagQuery_ReturnsFlags()
    {
        var (factory, db, adminService, configService, flagService) = CreateContext();

        // Act - Search for 'Dynamics'
        var results = await adminService.SearchAsync("Dynamics");

        // Assert
        Assert.NotEmpty(results);
        Assert.Contains(results, i => i.Key == "Integrations.DynamicsGp" || i.Title.Contains("Dynamics"));
    }

    [Fact]
    public async Task SearchAsync_WithLookupQuery_ReturnsLookupTables()
    {
        var (factory, db, adminService, configService, flagService) = CreateContext();

        // Act - Search for 'Gender'
        var results = await adminService.SearchAsync("Gender");

        // Assert
        Assert.NotEmpty(results);
        Assert.Contains(results, i => i.Key == "LOOKUP-GenderType" || i.Title.Contains("Gender"));
    }

    [Fact]
    public async Task UpdateConfigValueInlineAsync_UpdatesValueAndAudits()
    {
        var (factory, db, adminService, configService, flagService) = CreateContext();

        // Act
        var success = await adminService.UpdateConfigValueInlineAsync(
            "Finance.MandatoryGrantRebatePercent",
            "25.0",
            "TestAdmin"
        );

        // Assert
        Assert.True(success);
        var val = await configService.GetValueAsync<decimal>("Finance.MandatoryGrantRebatePercent", 0m);
        Assert.Equal(25.0m, val);
    }

    [Fact]
    public async Task ToggleFeatureFlagInlineAsync_TogglesFlagSuccessfully()
    {
        var (factory, db, adminService, configService, flagService) = CreateContext();

        // Act
        var success = await adminService.ToggleFeatureFlagInlineAsync(
            "Integrations.DynamicsGp",
            true,
            "TestAdmin"
        );

        // Assert
        Assert.True(success);
        var isEnabled = await flagService.IsFeatureEnabledAsync("Integrations.DynamicsGp");
        Assert.True(isEnabled);
    }
}
