using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class SystemConfigurationTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, IConfiguration config) CreateContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var inMemorySettings = new Dictionary<string, string?>
        {
            { "General:AppVersion", "2.0.0" },
            { "Features:Integrations.DynamicsGp", "false" }
        };
        var config = new ConfigurationBuilder().AddInMemoryCollection(inMemorySettings).Build();
        return (factory, db, audit, config);
    }

    [Fact]
    public async Task FeatureFlags_ExternalIntegrations_MustDefaultToDisabledFalse()
    {
        var (factory, db, audit, config) = CreateContext();
        var flagService = new FeatureFlagService(factory, config, audit);

        await flagService.SeedDefaultFeatureFlagsAsync();

        var isGpEnabled = await flagService.IsFeatureEnabledAsync("Integrations.DynamicsGp");
        var isSageEnabled = await flagService.IsFeatureEnabledAsync("Integrations.SageErp");
        var isSarsEnabled = await flagService.IsFeatureEnabledAsync("Integrations.LiveSarsFtp");
        var isSmsEnabled = await flagService.IsFeatureEnabledAsync("Integrations.SmsOtp");
        var isSchedulerEnabled = await flagService.IsFeatureEnabledAsync("Scheduler.BackgroundWorker");

        Assert.False(isGpEnabled);
        Assert.False(isSageEnabled);
        Assert.False(isSarsEnabled);
        Assert.False(isSmsEnabled);
        Assert.False(isSchedulerEnabled);
    }

    [Fact]
    public async Task FeatureFlags_ToggleFlag_UpdatesAndPersistsAudit()
    {
        var (factory, db, audit, config) = CreateContext();
        var flagService = new FeatureFlagService(factory, config, audit);

        var flag = await flagService.SetFeatureFlagAsync("Integrations.DynamicsGp", true, "Dynamics GP", "Integrations", "Test override", "AdminUser");
        Assert.True(flag.IsEnabled);

        var check = await flagService.IsFeatureEnabledAsync("Integrations.DynamicsGp");
        Assert.True(check);

        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "SystemFeatureFlag" && (a.ActionName == "CreateFeatureFlag" || a.ActionName == "UpdateFeatureFlag"));
        Assert.NotNull(auditLog);
    }

    [Fact]
    public async Task SystemConfig_DatabaseOverridesAppSettings_AndCachesCleanly()
    {
        var (factory, db, audit, config) = CreateContext();
        var configService = new SystemConfigurationService(factory, config, audit);

        // Appsettings fallback
        var version = await configService.GetValueAsync("General:AppVersion");
        Assert.Equal("2.0.0", version);

        // Set DB override
        await configService.SetConfigAsync("General:AppVersion", "2.1.0-Override", "General", "Overridden in DB", "String", "AdminUser");

        var overridden = await configService.GetValueAsync("General:AppVersion");
        Assert.Equal("2.1.0-Override", overridden);
    }

    [Fact]
    public async Task SystemConfig_SeedDefaultConfigs_SeedsAllMerSetaParametersCleanly()
    {
        var (factory, db, audit, config) = CreateContext();
        var configService = new SystemConfigurationService(factory, config, audit);

        await configService.SeedDefaultConfigsAsync();

        var configs = await configService.GetAllConfigsAsync();
        Assert.NotEmpty(configs);
        Assert.True(configs.Count >= 20);

        // Verify Finance thresholds
        var mgRebate = await configService.GetValueAsync<decimal>("Finance:MandatoryGrantRebatePercentage", 0m);
        Assert.Equal(0.20m, mgRebate);

        var dgAdminCap = await configService.GetValueAsync<decimal>("Finance:DiscretionaryGrantAdminCostCapPercentage", 0m);
        Assert.Equal(0.075m, dgAdminCap);

        var tranche1 = await configService.GetValueAsync<decimal>("Finance:Tranche1Percentage", 0m);
        Assert.Equal(0.30m, tranche1);

        // Verify Storage paths
        var storagePath = await configService.GetValueAsync("Storage:LocalRootPath");
        Assert.NotNull(storagePath);
        Assert.Contains("nsdms_storage", storagePath);

        // Verify Governance deadlines
        var wspDeadline = await configService.GetValueAsync("Governance:WspAnnualSubmissionDeadline");
        Assert.Equal("04-30", wspDeadline);

        var committeeQuorum = await configService.GetValueAsync<int>("Governance:ReviewCommitteeQuorumMinimumAttendees", 0);
        Assert.Equal(5, committeeQuorum);
    }
}
