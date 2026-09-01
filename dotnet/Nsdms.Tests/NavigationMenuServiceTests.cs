using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Domain.Security;
using Nsdms.Infrastructure.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Xunit;

namespace Nsdms.Tests;

public class NavigationMenuServiceTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, NavigationMenuService navService) CreateService()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var roleService = new RolePermissionService(factory, audit);
        var caslService = new CaslAbilityService(factory, roleService);

        var inMemorySettings = new Dictionary<string, string?>
        {
            { "General:AppVersion", "2026.8.0" }
        };
        var config = new ConfigurationBuilder().AddInMemoryCollection(inMemorySettings).Build();
        var configService = new SystemConfigurationService(factory, config, audit);
        var flagService = new FeatureFlagService(factory, config, audit);
        var lookupService = new LookupService(factory, audit);

        var adminService = new AdminCatalogService(
            factory,
            configService,
            flagService,
            lookupService,
            roleService,
            audit
        );

        var navService = new NavigationMenuService(factory, audit, caslService, adminService);

        return (factory, db, navService);
    }

    [Fact]
    public async Task GetUserNavigationTreeAsync_SuperAdmin_ReturnsAllGroupsAndItems()
    {
        var (_, _, navService) = CreateService();

        var roles = new List<string> { "SuperAdmin" };
        var perms = new HashSet<string>(AppPermissions.GetAllPermissions().Select(p => p.ClaimValue));

        var result = await navService.GetUserNavigationTreeAsync("Admin", roles, perms, "All Roles (Unified)");

        Assert.NotNull(result);
        Assert.True(result.TotalItemsCount >= 25, $"Expected >= 25 items, got {result.TotalItemsCount}");
        Assert.Equal(7, result.Groups.Count);
        Assert.Contains(result.Groups, g => g.GroupName == "Overview & tasks");
        Assert.Contains(result.Groups, g => g.GroupName == "Registries & stakeholders");
        Assert.Contains(result.Groups, g => g.GroupName == "Grants, levies & finance");
        Assert.Contains(result.Groups, g => g.GroupName == "Learner & artisan development");
        Assert.Contains(result.Groups, g => g.GroupName == "Quality assurance & ETQA");
        Assert.Contains(result.Groups, g => g.GroupName == "Legal, compliance & BI");
        Assert.Contains(result.Groups, g => g.GroupName == "System administration");
    }

    [Fact]
    public async Task GetUserNavigationTreeAsync_SdfRole_ReturnsOnlySdfRelevantItems()
    {
        var (_, _, navService) = CreateService();

        var roles = new List<string> { "SDF" };
        var perms = new HashSet<string>(StringComparer.OrdinalIgnoreCase)
        {
            "Wsp:View", "Wsp:Create", "Wsp:Edit", "Wsp:Submit",
            "Grants:View", "Grants:Create", "Grants:Submit",
            "Organisations:View",
            "Learners:View"
        };

        var result = await navService.GetUserNavigationTreeAsync("SDF_User", roles, perms, "Skills Development Facilitator (SDF)");

        Assert.NotNull(result);
        var allItems = result.Groups.SelectMany(g => g.Items).ToList();

        // Should contain SDF modules
        Assert.Contains(allItems, i => i.Id == "nav-wsp");
        Assert.Contains(allItems, i => i.Id == "nav-grants");
        Assert.Contains(allItems, i => i.Id == "nav-employers");

        // Should NOT contain System Admin modules
        Assert.DoesNotContain(allItems, i => i.Id == "nav-admin-settings");
        Assert.DoesNotContain(allItems, i => i.Id == "nav-admin-roles");
        Assert.DoesNotContain(allItems, i => i.Id == "nav-admin-lookups");
    }

    [Fact]
    public async Task GetUserNavigationTreeAsync_LegalRole_ReturnsLegalAndMoAModules()
    {
        var (_, _, navService) = CreateService();

        var roles = new List<string> { "Legal" };
        var perms = new HashSet<string>(StringComparer.OrdinalIgnoreCase)
        {
            "System:View", "System:Manage", "System:Verify", "Grants:Edit"
        };

        var result = await navService.GetUserNavigationTreeAsync("Legal_User", roles, perms, "Legal & Contracting Specialist");

        Assert.NotNull(result);
        var allItems = result.Groups.SelectMany(g => g.Items).ToList();

        Assert.Contains(allItems, i => i.Id == "nav-moa-templates");
        Assert.Contains(allItems, i => i.Id == "nav-doc-templates");
        Assert.Contains(allItems, i => i.Id == "nav-moa-clauses");
        Assert.Contains(allItems, i => i.Id == "nav-contracts-variations");
    }

    [Fact]
    public async Task GetUserNavigationTreeAsync_ComplianceRole_ReturnsComplianceAndAudits()
    {
        var (_, _, navService) = CreateService();

        var roles = new List<string> { "Compliance" };
        var perms = new HashSet<string>(StringComparer.OrdinalIgnoreCase)
        {
            "Compliance:View", "Workplace:View", "Etqa:View"
        };

        var result = await navService.GetUserNavigationTreeAsync("Compliance_User", roles, perms, "Statutory Compliance Auditor");

        Assert.NotNull(result);
        var allItems = result.Groups.SelectMany(g => g.Items).ToList();

        Assert.Contains(allItems, i => i.Id == "nav-admin-statutory");
        Assert.Contains(allItems, i => i.Id == "nav-monitoring");
        Assert.Contains(allItems, i => i.Id == "nav-etqa");
    }

    [Fact]
    public async Task GetUserNavigationTreeAsync_MultiRoleUser_ReturnsUnionOfPermissionsWithoutDuplicates()
    {
        var (_, _, navService) = CreateService();

        // User with both SDF and Assessor roles
        var roles = new List<string> { "SDF", "Assessor" };
        var perms = new HashSet<string>(StringComparer.OrdinalIgnoreCase)
        {
            "Wsp:View", "Organisations:View", "Etqa:View", "Learners:View", "Learners:Moderate"
        };

        var result = await navService.GetUserNavigationTreeAsync("MultiRoleUser", roles, perms, "All Roles (Unified)");

        Assert.NotNull(result);
        var allItems = result.Groups.SelectMany(g => g.Items).ToList();

        // Should have items from both SDF (WSP, Employers) and Assessor (ETQA, TradeTests, Summative)
        Assert.Contains(allItems, i => i.Id == "nav-wsp");
        Assert.Contains(allItems, i => i.Id == "nav-employers");
        Assert.Contains(allItems, i => i.Id == "nav-etqa");

        // Deduplication invariant: No duplicate item IDs
        var distinctCount = allItems.Select(i => i.Id).Distinct().Count();
        Assert.Equal(allItems.Count, distinctCount);
    }

    [Fact]
    public async Task TogglePinItemAsync_PinsAndUnpinsItemCorrectly()
    {
        var (_, _, navService) = CreateService();
        var username = "TestUser_Pin";

        // Default preferences
        var initialPrefs = await navService.GetUserPreferencesAsync(username);
        Assert.NotNull(initialPrefs);

        // Pin a new item
        await navService.TogglePinItemAsync(username, "nav-bi-reports");
        var pinnedAfterAdd = await navService.GetPinnedItemsAsync(username);
        Assert.Contains(pinnedAfterAdd, p => p.Id == "nav-bi-reports");

        // Toggle again to unpin
        await navService.TogglePinItemAsync(username, "nav-bi-reports");
        var pinnedAfterRemove = await navService.GetPinnedItemsAsync(username);
        Assert.DoesNotContain(pinnedAfterRemove, p => p.Id == "nav-bi-reports");
    }

    [Fact]
    public async Task SaveUserPreferencesAsync_PersistsPreferencesToDatabase()
    {
        var (_, db, navService) = CreateService();
        var username = "Persist_User";

        var prefs = new UserNavPreferencesDto
        {
            Username = username,
            PinnedItemIds = new List<string> { "nav-tasks", "nav-wsp" },
            CollapsedGroupIds = new List<string> { "System administration" },
            ActivePersonaFilter = "Finance & Disbursements Specialist"
        };

        var saved = await navService.SaveUserPreferencesAsync(prefs);
        Assert.True(saved);

        var configKey = $"{username}:NavPreferences";
        var dbConfig = await db.SystemConfigs
            .FirstOrDefaultAsync(s => s.ConfigCategory == "NavPreferences" && s.ConfigKey == configKey);

        Assert.NotNull(dbConfig);
        Assert.Contains("nav-wsp", dbConfig.ConfigValue);
    }

    [Fact]
    public async Task SearchOmnisearchAsync_MatchesTitlesCategoriesAndQuickActions()
    {
        var (_, _, navService) = CreateService();

        var roles = new List<string> { "SuperAdmin" };
        var perms = new HashSet<string>(AppPermissions.GetAllPermissions().Select(p => p.ClaimValue));

        // 1. Search for "WSP"
        var wspResults = await navService.SearchOmnisearchAsync("WSP", "Admin", roles, perms, 10);
        Assert.NotEmpty(wspResults);
        Assert.Contains(wspResults, r => r.Title.Contains("WSP", StringComparison.OrdinalIgnoreCase));

        // 2. Search for "Create" (should find quick actions)
        var actionResults = await navService.SearchOmnisearchAsync("Create", "Admin", roles, perms, 10);
        Assert.NotEmpty(actionResults);
        Assert.Contains(actionResults, r => r.ItemType == "QuickAction");

        // 3. Search for "Grant"
        var grantResults = await navService.SearchOmnisearchAsync("Grant", "Admin", roles, perms, 10);
        Assert.NotEmpty(grantResults);
        Assert.Contains(grantResults, r => r.Title.Contains("Grant", StringComparison.OrdinalIgnoreCase));
    }

    [Fact]
    public async Task GetDynamicBadgeCountsAsync_ReturnsValidCounts()
    {
        var (_, db, navService) = CreateService();

        // Seed some pending tasks and WSPs
        db.WspSubmissions.Add(new WspSubmission
        {
            FinYear = 2026,
            ReferenceNumber = "WSP-TEST-001",
            WspApprovalStatusCode = "Submitted",
            OrganisationId = 1,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "Admin"
        });
        await db.SaveChangesAsync();

        var badgeCounts = await navService.GetDynamicBadgeCountsAsync("Admin", new() { "SuperAdmin" });

        Assert.NotNull(badgeCounts);
        Assert.True(badgeCounts.ContainsKey("nav-tasks"));
        Assert.True(badgeCounts.ContainsKey("nav-wsp"));
        Assert.True(badgeCounts.ContainsKey("nav-grants"));
        Assert.True(badgeCounts["nav-wsp"] >= 1);
    }
}
