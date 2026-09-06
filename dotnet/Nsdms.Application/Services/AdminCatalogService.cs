using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.Json;

namespace Nsdms.Application.Services;

public static class AdminCategoryConstants
{
    public const string All = "All";
    public const string SecurityAndAccess = "Security & Access Control";
    public const string SystemConfiguration = "System Settings & Parameters";
    public const string FeatureFlags = "Feature Flags & Integrations";
    public const string ReferenceLookups = "Reference Data & SETMIS Enums";
    public const string DocumentsAndTemplates = "Document & Legal Templates";
    public const string DelegationsAndGovernance = "Delegations & Approvals";
    public const string ComplianceAndStatutory = "Statutory & Compliance";
    public const string AuditAndForensics = "Audit Trail & Forensics";
    public const string DiagnosticsAndDeveloper = "Diagnostics & Data Dictionary";
}

public class AdminSearchItemDto
{
    public string Key { get; set; } = string.Empty;
    public string Title { get; set; } = string.Empty;
    public string Category { get; set; } = string.Empty;
    public string Description { get; set; } = string.Empty;
    public string Icon { get; set; } = string.Empty;
    public string RouteUrl { get; set; } = string.Empty;
    public string ItemType { get; set; } = "Module"; // Module, ConfigParameter, FeatureFlag, LookupTable, Role, DocumentTemplate, Diagnostic
    public string? CurrentValue { get; set; }
    public string? ValueType { get; set; } // String, Number, Boolean, Enum, Count, Badge
    public bool IsEditableInline { get; set; }
    public List<string> Tags { get; set; } = new();
    public string StatusBadgeColor { get; set; } = "Primary";
    public int DisplayOrder { get; set; }
}

public class AdminCategoryGroupDto
{
    public string CategoryName { get; set; } = string.Empty;
    public string Description { get; set; } = string.Empty;
    public string Icon { get; set; } = string.Empty;
    public int ItemCount { get; set; }
    public List<AdminSearchItemDto> Items { get; set; } = new();
}

public class AdminTelemetryDto
{
    public int TotalAdminItemsCount { get; set; }
    public int TotalConfigsCount { get; set; }
    public int TotalFlagsCount { get; set; }
    public int EnabledFlagsCount { get; set; }
    public int TotalLookupsCount { get; set; }
    public int TotalRolesCount { get; set; }
    public int TotalAuditLogsCount { get; set; }
    public int TotalTemplatesCount { get; set; }
    public bool IsDatabaseConnected { get; set; } = true;
    public bool IsTemporalVersioningActive { get; set; } = true;
    public string EnvironmentName { get; set; } = Environment.GetEnvironmentVariable("ASPNETCORE_ENVIRONMENT") ?? "Production";
    public DateTime ServerTimeUtc { get; set; } = DateTime.UtcNow;
}

public class AdminCatalogIndexDto
{
    public AdminTelemetryDto Telemetry { get; set; } = new();
    public List<AdminSearchItemDto> AllItems { get; set; } = new();
    public List<AdminSearchItemDto> QuickAccessModules { get; set; } = new();
    public List<AdminCategoryGroupDto> CategoryGroups { get; set; } = new();
}

public interface IAdminCatalogService
{
    Task<AdminCatalogIndexDto> GetCatalogIndexAsync(string? search = null, string? category = null);
    Task<List<AdminSearchItemDto>> SearchAsync(string query, int maxResults = 50);
    Task<AdminTelemetryDto> GetTelemetryAsync();
    Task<bool> UpdateConfigValueInlineAsync(string key, string newValue, string currentUsername = "Admin");
    Task<bool> ToggleFeatureFlagInlineAsync(string featureKey, bool isEnabled, string currentUsername = "Admin");
}

public class AdminCatalogService : IAdminCatalogService
{
    private static readonly List<AdminSearchItemDto> _primaryAdminModules = BuildPrimaryAdminModules();

    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly ISystemConfigurationService _configService;
    private readonly IFeatureFlagService _featureFlagService;
    private readonly ILookupService _lookupService;
    private readonly IRolePermissionService _roleService;
    private readonly IAuditService _audit;
    private readonly ILogger<AdminCatalogService>? _logger;

    public AdminCatalogService(
        INsdmsDbContextFactory contextFactory,
        ISystemConfigurationService configService,
        IFeatureFlagService featureFlagService,
        ILookupService lookupService,
        IRolePermissionService roleService,
        IAuditService audit,
        ILogger<AdminCatalogService>? logger = null)
    {
        _contextFactory = contextFactory;
        _configService = configService;
        _featureFlagService = featureFlagService;
        _lookupService = lookupService;
        _roleService = roleService;
        _audit = audit;
        _logger = logger;
    }

    public async Task<AdminCatalogIndexDto> GetCatalogIndexAsync(string? search = null, string? category = null)
    {
        var telemetry = await GetTelemetryAsync();
        var allItems = await BuildFullCatalogItemsAsync();

        if (!string.IsNullOrWhiteSpace(search))
        {
            var query = search.Trim();
            allItems = allItems.Where(i =>
                i.Title.Contains(query, StringComparison.OrdinalIgnoreCase) ||
                i.Description.Contains(query, StringComparison.OrdinalIgnoreCase) ||
                i.Key.Contains(query, StringComparison.OrdinalIgnoreCase) ||
                (i.CurrentValue?.Contains(query, StringComparison.OrdinalIgnoreCase) ?? false) ||
                i.Tags.Any(t => t.Contains(query, StringComparison.OrdinalIgnoreCase))
            ).ToList();
        }

        if (!string.IsNullOrWhiteSpace(category) && !category.Equals(AdminCategoryConstants.All, StringComparison.OrdinalIgnoreCase))
        {
            allItems = allItems.Where(i => i.Category.Equals(category, StringComparison.OrdinalIgnoreCase)).ToList();
        }

        var quickAccessModules = allItems
            .Where(i => i.ItemType == "Module")
            .OrderBy(i => i.DisplayOrder)
            .Take(8)
            .ToList();

        var groups = allItems
            .GroupBy(i => i.Category)
            .Select(g => new AdminCategoryGroupDto
            {
                CategoryName = g.Key,
                Description = GetCategoryDescription(g.Key),
                Icon = GetCategoryIcon(g.Key),
                ItemCount = g.Count(),
                Items = g.OrderBy(i => i.DisplayOrder).ThenBy(i => i.Title).ToList()
            })
            .OrderBy(g => GetCategoryOrder(g.CategoryName))
            .ToList();

        return new AdminCatalogIndexDto
        {
            Telemetry = telemetry,
            AllItems = allItems,
            QuickAccessModules = quickAccessModules,
            CategoryGroups = groups
        };
    }

    public async Task<List<AdminSearchItemDto>> SearchAsync(string query, int maxResults = 50)
    {
        if (string.IsNullOrWhiteSpace(query))
        {
            var catalog = await BuildFullCatalogItemsAsync();
            return catalog.OrderBy(i => i.DisplayOrder).Take(maxResults).ToList();
        }

        var all = await BuildFullCatalogItemsAsync();
        var q = query.Trim();

        return all.Where(i =>
            i.Title.Contains(q, StringComparison.OrdinalIgnoreCase) ||
            i.Description.Contains(q, StringComparison.OrdinalIgnoreCase) ||
            i.Key.Contains(q, StringComparison.OrdinalIgnoreCase) ||
            (i.CurrentValue?.Contains(q, StringComparison.OrdinalIgnoreCase) ?? false) ||
            i.Tags.Any(t => t.Contains(q, StringComparison.OrdinalIgnoreCase))
        )
        .OrderByDescending(i => i.Title.StartsWith(q, StringComparison.OrdinalIgnoreCase))
        .ThenByDescending(i => i.Key.StartsWith(q, StringComparison.OrdinalIgnoreCase))
        .ThenBy(i => i.DisplayOrder)
        .Take(maxResults)
        .ToList();
    }

    public async Task<AdminTelemetryDto> GetTelemetryAsync()
    {
        var telemetry = new AdminTelemetryDto
        {
            ServerTimeUtc = DateTime.UtcNow,
            IsDatabaseConnected = true,
            IsTemporalVersioningActive = true,
            EnvironmentName = Environment.GetEnvironmentVariable("ASPNETCORE_ENVIRONMENT") ?? "Production"
        };

        try
        {
            await using var db = await _contextFactory.CreateDbContextAsync();
            telemetry.TotalAuditLogsCount = await db.AuditLogs.CountAsync();
            telemetry.TotalConfigsCount = await db.SystemConfigs.CountAsync(c => c.IsActive);
            telemetry.TotalFlagsCount = await db.SystemFeatureFlags.CountAsync();
            telemetry.EnabledFlagsCount = await db.SystemFeatureFlags.CountAsync(f => f.IsEnabled);
            telemetry.TotalRolesCount = await db.Roles.CountAsync(r => r.Active);
            telemetry.TotalTemplatesCount = await db.DocumentTemplates.CountAsync();

            var lookups = await _lookupService.GetAllLookupMetadataAsync();
            telemetry.TotalLookupsCount = lookups.Count;

            telemetry.TotalAdminItemsCount = telemetry.TotalConfigsCount +
                                             telemetry.TotalFlagsCount +
                                             telemetry.TotalLookupsCount +
                                             telemetry.TotalRolesCount +
                                             telemetry.TotalTemplatesCount +
                                             _primaryAdminModules.Count;
        }
        catch (Exception ex)
        {
            _logger?.LogWarning(ex, "Failed to retrieve telemetry stats from database.");
            telemetry.IsDatabaseConnected = false;
        }

        return telemetry;
    }

    public async Task<bool> UpdateConfigValueInlineAsync(string key, string newValue, string currentUsername = "Admin")
    {
        try
        {
            await using var db = await _contextFactory.CreateDbContextAsync();
            var existing = await db.SystemConfigs.FirstOrDefaultAsync(c => c.ConfigKey == key);
            if (existing == null)
            {
                await _configService.SetConfigAsync(key, newValue, "General", null, "String", currentUsername);
            }
            else
            {
                await _configService.SetConfigAsync(key, newValue, existing.ConfigCategory, existing.Description, existing.DataType, currentUsername);
            }
            return true;
        }
        catch (Exception ex)
        {
            _logger?.LogError(ex, "Failed to update config value inline for key {ConfigKey}.", key);
            return false;
        }
    }

    public async Task<bool> ToggleFeatureFlagInlineAsync(string featureKey, bool isEnabled, string currentUsername = "Admin")
    {
        try
        {
            await _featureFlagService.SetFeatureFlagAsync(featureKey, isEnabled, currentUsername: currentUsername);
            return true;
        }
        catch (Exception ex)
        {
            _logger?.LogError(ex, "Failed to toggle feature flag inline for key {FeatureKey}.", featureKey);
            return false;
        }
    }

    private async Task<List<AdminSearchItemDto>> BuildFullCatalogItemsAsync()
    {
        var items = new List<AdminSearchItemDto>();

        // 1. PRIMARY ADMIN MODULES
        AddPrimaryAdminModules(items);

        // 2. SYSTEM CONFIGURATION PARAMETERS
        try
        {
            var configs = await _configService.GetAllConfigsAsync();
            foreach (var cfg in configs)
            {
                items.Add(new AdminSearchItemDto
                {
                    Key = cfg.ConfigKey,
                    Title = FormatConfigTitle(cfg.ConfigKey),
                    Category = AdminCategoryConstants.SystemConfiguration,
                    Description = cfg.Description ?? $"Dynamic runtime configuration parameter for {cfg.ConfigCategory}.",
                    Icon = GetConfigIcon(cfg.ConfigCategory),
                    RouteUrl = "/admin/settings",
                    ItemType = "ConfigParameter",
                    CurrentValue = cfg.ConfigValue,
                    ValueType = cfg.DataType ?? "String",
                    IsEditableInline = true,
                    Tags = new List<string> { "config", "setting", cfg.ConfigCategory.ToLowerInvariant(), cfg.ConfigKey.ToLowerInvariant() },
                    StatusBadgeColor = "Success",
                    DisplayOrder = 20
                });
            }
        }
        catch (Exception ex)
        {
            _logger?.LogWarning(ex, "Failed to load system configs into admin catalog.");
        }

        // 3. FEATURE FLAGS & INTEGRATIONS
        try
        {
            var flags = await _featureFlagService.GetAllFeatureFlagsAsync();
            foreach (var flg in flags)
            {
                items.Add(new AdminSearchItemDto
                {
                    Key = flg.FeatureKey,
                    Title = flg.FeatureName,
                    Category = AdminCategoryConstants.FeatureFlags,
                    Description = flg.Description ?? $"Feature toggle governing {flg.FeatureKey} behavior and live execution.",
                    Icon = flg.IsEnabled ? "Icons.Material.Filled.ToggleOn" : "Icons.Material.Filled.ToggleOff",
                    RouteUrl = "/admin/settings",
                    ItemType = "FeatureFlag",
                    CurrentValue = flg.IsEnabled ? "Enabled" : "Disabled (Simulation)",
                    ValueType = "Boolean",
                    IsEditableInline = true,
                    Tags = new List<string> { "flag", "feature", "integration", flg.Category.ToLowerInvariant(), flg.FeatureKey.ToLowerInvariant() },
                    StatusBadgeColor = flg.IsEnabled ? "Success" : "Warning",
                    DisplayOrder = 30
                });
            }
        }
        catch (Exception ex)
        {
            _logger?.LogWarning(ex, "Failed to load feature flags into admin catalog.");
        }

        // 4. REFERENCE DATA & SETMIS LOOKUP TABLES
        try
        {
            var lookups = await _lookupService.GetAllLookupMetadataAsync();
            foreach (var lkp in lookups)
            {
                items.Add(new AdminSearchItemDto
                {
                    Key = $"LOOKUP-{lkp.TableName}",
                    Title = $"{lkp.DisplayName} ({lkp.TableName})",
                    Category = AdminCategoryConstants.ReferenceLookups,
                    Description = lkp.Description,
                    Icon = "Icons.Material.Filled.Tune",
                    RouteUrl = "/admin/lookups",
                    ItemType = "LookupTable",
                    CurrentValue = $"{lkp.ItemCount} items",
                    ValueType = "Count",
                    IsEditableInline = false,
                    Tags = new List<string> { "lookup", "setmis", "enum", "reference", lkp.TableName.ToLowerInvariant(), lkp.Category.ToLowerInvariant() },
                    StatusBadgeColor = "Info",
                    DisplayOrder = 40
                });
            }
        }
        catch (Exception ex)
        {
            _logger?.LogWarning(ex, "Failed to load lookup tables into admin catalog.");
        }

        // 5. SECURITY ROLES & ACCESS CONTROL
        try
        {
            var roles = await _roleService.GetAllRolesAsync();
            foreach (var r in roles)
            {
                items.Add(new AdminSearchItemDto
                {
                    Key = $"ROLE-{r.Id}",
                    Title = $"Security Role: {r.Name}",
                    Category = AdminCategoryConstants.SecurityAndAccess,
                    Description = r.Description ?? $"Claims-based RBAC security role with {r.PermissionCount} granted claims and {r.UserCount} assigned users.",
                    Icon = "Icons.Material.Filled.Security",
                    RouteUrl = $"/admin/roles/{r.Id}",
                    ItemType = "Role",
                    CurrentValue = $"{r.UserCount} Users • {r.PermissionCount} Claims",
                    ValueType = "Badge",
                    IsEditableInline = false,
                    Tags = new List<string> { "role", "security", "rbac", "casl", "permission", r.Name.ToLowerInvariant() },
                    StatusBadgeColor = r.Active ? "Success" : "Error",
                    DisplayOrder = 15
                });
            }
        }
        catch (Exception ex)
        {
            _logger?.LogWarning(ex, "Failed to load roles into admin catalog.");
        }

        return items;
    }

    private void AddPrimaryAdminModules(List<AdminSearchItemDto> items)
    {
        items.AddRange(_primaryAdminModules);
    }

    private static List<AdminSearchItemDto> BuildPrimaryAdminModules()
    {
        var items = new List<AdminSearchItemDto>();
        items.Add(new AdminSearchItemDto
        {
            Key = "MOD-SECURITY-ROLES",
            Title = "Security Roles & Permissions Matrix",
            Category = AdminCategoryConstants.SecurityAndAccess,
            Description = "Configure Claims-Based RBAC roles, CASL ability matrices, and module-level CRUD permissions.",
            Icon = "Icons.Material.Filled.Security",
            RouteUrl = "/admin/roles",
            ItemType = "Module",
            CurrentValue = "Active RBAC",
            ValueType = "Badge",
            IsEditableInline = false,
            Tags = new List<string> { "security", "roles", "permissions", "casl", "rbac", "users" },
            StatusBadgeColor = "Primary",
            DisplayOrder = 1
        });

        items.Add(new AdminSearchItemDto
        {
            Key = "MOD-PEOPLE-ACCOUNTS",
            Title = "People & Identity Accounts",
            Category = AdminCategoryConstants.SecurityAndAccess,
            Description = "Manage demographics, RSA ID verification, citizen identities, and login user associations.",
            Icon = "Icons.Material.Filled.People",
            RouteUrl = "/people",
            ItemType = "Module",
            CurrentValue = "Demographics Hub",
            ValueType = "Badge",
            IsEditableInline = false,
            Tags = new List<string> { "people", "users", "accounts", "identity", "rsa id", "demographics" },
            StatusBadgeColor = "Primary",
            DisplayOrder = 2
        });

        items.Add(new AdminSearchItemDto
        {
            Key = "MOD-IDENTITY-USERS",
            Title = "Identity & User Accounts",
            Category = AdminCategoryConstants.SecurityAndAccess,
            Description = "Manage ASP.NET Core Identity login credentials, account lockouts, passwords, and roles.",
            Icon = "Icons.Material.Filled.ManageAccounts",
            RouteUrl = "/admin/users",
            ItemType = "Module",
            CurrentValue = "Identity Accounts",
            ValueType = "Badge",
            IsEditableInline = false,
            Tags = new List<string> { "users", "accounts", "identity", "passwords", "lockout", "logins" },
            StatusBadgeColor = "Success",
            DisplayOrder = 3
        });

        items.Add(new AdminSearchItemDto
        {
            Key = "MOD-SYSTEM-SETTINGS",
            Title = "System Configuration & Overrides",
            Category = AdminCategoryConstants.SystemConfiguration,
            Description = "Dynamic enterprise configuration engine with live database overrides and audited change logging.",
            Icon = "Icons.Material.Filled.Settings",
            RouteUrl = "/admin/settings",
            ItemType = "Module",
            CurrentValue = "Dynamic Engine",
            ValueType = "Badge",
            IsEditableInline = false,
            Tags = new List<string> { "settings", "configuration", "parameters", "finance", "storage", "thresholds" },
            StatusBadgeColor = "Primary",
            DisplayOrder = 3
        });

        items.Add(new AdminSearchItemDto
        {
            Key = "MOD-FEATURE-FLAGS",
            Title = "Feature Flags & Integrations Engine",
            Category = AdminCategoryConstants.FeatureFlags,
            Description = "Zero-risk runtime switches for ERP (Dynamics GP/Sage), DHET/SARS SFTP, SMS OTP, and storage.",
            Icon = "Icons.Material.Filled.ToggleOn",
            RouteUrl = "/admin/settings",
            ItemType = "Module",
            CurrentValue = "Decoupled Adapters",
            ValueType = "Badge",
            IsEditableInline = false,
            Tags = new List<string> { "flags", "integrations", "erp", "sars", "dhet", "sftp", "otp", "azure" },
            StatusBadgeColor = "Primary",
            DisplayOrder = 4
        });

        items.Add(new AdminSearchItemDto
        {
            Key = "MOD-LOOKUPS-HUB",
            Title = "Reference Data & SETMIS Enums Hub",
            Category = AdminCategoryConstants.ReferenceLookups,
            Description = "Statutory reference tables, OFO codes (1454), SIC codes (815), and SETMIS lookup enums.",
            Icon = "Icons.Material.Filled.Tune",
            RouteUrl = "/admin/lookups",
            ItemType = "Module",
            CurrentValue = "30+ Tables",
            ValueType = "Badge",
            IsEditableInline = false,
            Tags = new List<string> { "lookups", "setmis", "enums", "ofo", "sic", "provinces", "equity", "demographics" },
            StatusBadgeColor = "Primary",
            DisplayOrder = 5
        });

        items.Add(new AdminSearchItemDto
        {
            Key = "MOD-DOC-TEMPLATES",
            Title = "Enterprise Document Template Studio",
            Category = AdminCategoryConstants.DocumentsAndTemplates,
            Description = "Universal template composition engine with dynamic token replacement and PDF generation.",
            Icon = "Icons.Material.Filled.Description",
            RouteUrl = "/admin/document-templates",
            ItemType = "Module",
            CurrentValue = "Template Studio",
            ValueType = "Badge",
            IsEditableInline = false,
            Tags = new List<string> { "templates", "documents", "pdf", "tokens", "clauses", "contracts" },
            StatusBadgeColor = "Primary",
            DisplayOrder = 6
        });

        items.Add(new AdminSearchItemDto
        {
            Key = "MOD-MOA-TEMPLATES",
            Title = "MoA Legal Template Studio",
            Category = AdminCategoryConstants.DocumentsAndTemplates,
            Description = "Discretionary Grant Memorandum of Agreement template builder with variable binding.",
            Icon = "Icons.Material.Filled.Gavel",
            RouteUrl = "/legal/moa-templates",
            ItemType = "Module",
            CurrentValue = "Legal Studio",
            ValueType = "Badge",
            IsEditableInline = false,
            Tags = new List<string> { "moa", "legal", "grant", "memorandum", "clauses", "contracts" },
            StatusBadgeColor = "Primary",
            DisplayOrder = 7
        });

        items.Add(new AdminSearchItemDto
        {
            Key = "MOD-MOA-CLAUSES",
            Title = "Reusable Legal Clause Library",
            Category = AdminCategoryConstants.DocumentsAndTemplates,
            Description = "Standardized statutory clauses, breach penalties, tranche disbursement terms, and dispute provisions.",
            Icon = "Icons.Material.Filled.LibraryBooks",
            RouteUrl = "/legal/moa-clauses",
            ItemType = "Module",
            CurrentValue = "Clause Library",
            ValueType = "Badge",
            IsEditableInline = false,
            Tags = new List<string> { "clauses", "legal", "library", "dispute", "penalties", "tranches" },
            StatusBadgeColor = "Primary",
            DisplayOrder = 8
        });

        items.Add(new AdminSearchItemDto
        {
            Key = "MOD-DOC-SNAPSHOTS",
            Title = "Document Security & Digital Seals",
            Category = AdminCategoryConstants.DocumentsAndTemplates,
            Description = "Digital security seal repository and tamper-evident document integrity tracking.",
            Icon = "Icons.Material.Filled.Shield",
            RouteUrl = "/admin/document-snapshots",
            ItemType = "Module",
            CurrentValue = "Security Seals",
            ValueType = "Badge",
            IsEditableInline = false,
            Tags = new List<string> { "security", "seal", "verification", "snapshots", "tamper", "reference" },
            StatusBadgeColor = "Primary",
            DisplayOrder = 9
        });

        items.Add(new AdminSearchItemDto
        {
            Key = "MOD-DOC-VERIFY",
            Title = "Digital Document Verification Portal",
            Category = AdminCategoryConstants.DocumentsAndTemplates,
            Description = "Public and internal verification endpoint for validating official NSDMS certificates and MOAs.",
            Icon = "Icons.Material.Filled.VerifiedUser",
            RouteUrl = "/verify",
            ItemType = "Module",
            CurrentValue = "Public Validator",
            ValueType = "Badge",
            IsEditableInline = false,
            Tags = new List<string> { "verify", "certificates", "validation", "public", "qr code", "authenticity" },
            StatusBadgeColor = "Primary",
            DisplayOrder = 10
        });

        items.Add(new AdminSearchItemDto
        {
            Key = "MOD-COMMITTEE-MEETINGS",
            Title = "Committee & MANCO Meetings",
            Category = AdminCategoryConstants.DelegationsAndGovernance,
            Description = "Review committee agendas, charter minutes, quorum tracking, and executive approval resolutions.",
            Icon = "Icons.Material.Filled.MeetingRoom",
            RouteUrl = "/governance/meetings",
            ItemType = "Module",
            CurrentValue = "Executive MANCO",
            ValueType = "Badge",
            IsEditableInline = false,
            Tags = new List<string> { "manco", "committees", "meetings", "resolutions", "charter", "quorum" },
            StatusBadgeColor = "Primary",
            DisplayOrder = 11
        });

        items.Add(new AdminSearchItemDto
        {
            Key = "MOD-DELEGATIONS",
            Title = "Delegation of Authority Register",
            Category = AdminCategoryConstants.DelegationsAndGovernance,
            Description = "Delegated financial and operational approval authorities and acting capacity appointments.",
            Icon = "Icons.Material.Filled.AssignmentTurnedIn",
            RouteUrl = "/governance/delegations",
            ItemType = "Module",
            CurrentValue = "DoA Register",
            ValueType = "Badge",
            IsEditableInline = false,
            Tags = new List<string> { "delegations", "doa", "authority", "acting", "approvals" },
            StatusBadgeColor = "Primary",
            DisplayOrder = 12
        });

        items.Add(new AdminSearchItemDto
        {
            Key = "MOD-THRESHOLDS",
            Title = "Financial Approval Thresholds",
            Category = AdminCategoryConstants.DelegationsAndGovernance,
            Description = "Tiered approval monetary limits for CEO, COO, CFO, Senior Managers, and Review Committees.",
            Icon = "Icons.Material.Filled.AccountBalance",
            RouteUrl = "/governance/thresholds",
            ItemType = "Module",
            CurrentValue = "Tiered Limits",
            ValueType = "Badge",
            IsEditableInline = false,
            Tags = new List<string> { "thresholds", "financial limits", "tier", "spending", "authorisation" },
            StatusBadgeColor = "Primary",
            DisplayOrder = 13
        });

        items.Add(new AdminSearchItemDto
        {
            Key = "MOD-STATUTORY-COMPLIANCE",
            Title = "Statutory Submissions & SETMIS Validation",
            Category = AdminCategoryConstants.ComplianceAndStatutory,
            Description = "Pre-submission SETMIS and NLRD data file validation, validation rules engine, and batch logs.",
            Icon = "Icons.Material.Filled.FactCheck",
            RouteUrl = "/compliance/statutory",
            ItemType = "Module",
            CurrentValue = "SETMIS & NLRD",
            ValueType = "Badge",
            IsEditableInline = false,
            Tags = new List<string> { "statutory", "setmis", "nlrd", "submissions", "batches", "validation" },
            StatusBadgeColor = "Primary",
            DisplayOrder = 14
        });

        items.Add(new AdminSearchItemDto
        {
            Key = "MOD-LEVY-AUDITS",
            Title = "SARS Levy Audits & Historical Recon",
            Category = AdminCategoryConstants.ComplianceAndStatutory,
            Description = "Multi-year SARS levy data reconciliation, clawback detection, and variance audit trail.",
            Icon = "Icons.Material.Filled.Calculate",
            RouteUrl = "/finance/levy-audits",
            ItemType = "Module",
            CurrentValue = "SARS Recon",
            ValueType = "Badge",
            IsEditableInline = false,
            Tags = new List<string> { "sars", "levy", "audits", "clawback", "reconciliation", "variance" },
            StatusBadgeColor = "Primary",
            DisplayOrder = 15
        });

        items.Add(new AdminSearchItemDto
        {
            Key = "MOD-AUDIT-TRAIL",
            Title = "Immutable Audited Change Log",
            Category = AdminCategoryConstants.AuditAndForensics,
            Description = "Enterprise audited change log capturing entity mutations, actor activity, and before/after JSON diffs.",
            Icon = "Icons.Material.Filled.History",
            RouteUrl = "/audit-logs",
            ItemType = "Module",
            CurrentValue = "Audited Change Log",
            ValueType = "Badge",
            IsEditableInline = false,
            Tags = new List<string> { "audit", "forensics", "logs", "mutations", "history", "compliance" },
            StatusBadgeColor = "Primary",
            DisplayOrder = 16
        });

        items.Add(new AdminSearchItemDto
        {
            Key = "MOD-DATA-DICTIONARY",
            Title = "Database Data Dictionary & Extended Properties",
            Category = AdminCategoryConstants.DiagnosticsAndDeveloper,
            Description = "Interactive schema explorer reading SQL Server extended properties, historical version timelines, and foreign keys.",
            Icon = "Icons.Material.Filled.MenuBook",
            RouteUrl = "/developer/schema",
            ItemType = "Module",
            CurrentValue = "Schema Explorer",
            ValueType = "Badge",
            IsEditableInline = false,
            Tags = new List<string> { "schema", "dictionary", "database", "sql", "tables", "developer" },
            StatusBadgeColor = "Primary",
            DisplayOrder = 17
        });

        items.Add(new AdminSearchItemDto
        {
            Key = "MOD-UI-COMPLIANCE",
            Title = "UI/UX & Standards Compliance HUD",
            Category = AdminCategoryConstants.DiagnosticsAndDeveloper,
            Description = "Live compliance audit evaluating WCAG 2.2 AA, NN/g heuristics, and Master-Detail standards.",
            Icon = "Icons.Material.Filled.Verified",
            RouteUrl = "/developer/compliance-audit",
            ItemType = "Module",
            CurrentValue = "WCAG 2.2 AA",
            ValueType = "Badge",
            IsEditableInline = false,
            Tags = new List<string> { "wcag", "compliance", "ui", "ux", "accessibility", "audit", "hud" },
            StatusBadgeColor = "Primary",
            DisplayOrder = 18
        });
        return items;
    }

    private static string FormatConfigTitle(string key)
    {
        var parts = key.Split('.');
        if (parts.Length > 1)
        {
            var cleanName = System.Text.RegularExpressions.Regex.Replace(parts[1], "(\\B[A-Z])", " $1");
            return $"{parts[0]}: {cleanName}";
        }
        return System.Text.RegularExpressions.Regex.Replace(key, "(\\B[A-Z])", " $1");
    }

    private static string GetConfigIcon(string category)
    {
        return category.ToLowerInvariant() switch
        {
            "finance" => "Icons.Material.Filled.AccountBalance",
            "storage" => "Icons.Material.Filled.Storage",
            "governance" => "Icons.Material.Filled.Gavel",
            "compliance" => "Icons.Material.Filled.FactCheck",
            "security" => "Icons.Material.Filled.Security",
            "integrations" => "Icons.Material.Filled.ToggleOn",
            _ => "Icons.Material.Filled.Settings"
        };
    }

    private static string GetCategoryDescription(string category)
    {
        return category switch
        {
            AdminCategoryConstants.SecurityAndAccess => "Manage identity accounts, RBAC security roles, and CASL permission matrices.",
            AdminCategoryConstants.SystemConfiguration => "Dynamic parameters governing business calculations, deadlines, and quotas.",
            AdminCategoryConstants.FeatureFlags => "Runtime feature switches and external ERP/SARS/DHET integration adapters.",
            AdminCategoryConstants.ReferenceLookups => "Statutory reference tables, OFO codes, SIC codes, and SETMIS standard enums.",
            AdminCategoryConstants.DocumentsAndTemplates => "Document templates, MoA clause libraries, and digital security seal verification.",
            AdminCategoryConstants.DelegationsAndGovernance => "Review committee schedules, delegated approval limits, and charter resolutions.",
            AdminCategoryConstants.ComplianceAndStatutory => "SETMIS/NLRD batch pre-submission validations and SARS levy audits.",
            AdminCategoryConstants.AuditAndForensics => "Immutable audited change log and historical version timeline.",
            AdminCategoryConstants.DiagnosticsAndDeveloper => "Schema data dictionary, extended properties, and WCAG accessibility HUD.",
            _ => "Administrative configuration items."
        };
    }

    private static string GetCategoryIcon(string category)
    {
        return category switch
        {
            AdminCategoryConstants.SecurityAndAccess => "Icons.Material.Filled.Security",
            AdminCategoryConstants.SystemConfiguration => "Icons.Material.Filled.Settings",
            AdminCategoryConstants.FeatureFlags => "Icons.Material.Filled.ToggleOn",
            AdminCategoryConstants.ReferenceLookups => "Icons.Material.Filled.Tune",
            AdminCategoryConstants.DocumentsAndTemplates => "Icons.Material.Filled.Description",
            AdminCategoryConstants.DelegationsAndGovernance => "Icons.Material.Filled.MeetingRoom",
            AdminCategoryConstants.ComplianceAndStatutory => "Icons.Material.Filled.FactCheck",
            AdminCategoryConstants.AuditAndForensics => "Icons.Material.Filled.History",
            AdminCategoryConstants.DiagnosticsAndDeveloper => "Icons.Material.Filled.Code",
            _ => "Icons.Material.Filled.Folder"
        };
    }

    private static int GetCategoryOrder(string category)
    {
        return category switch
        {
            AdminCategoryConstants.SecurityAndAccess => 1,
            AdminCategoryConstants.SystemConfiguration => 2,
            AdminCategoryConstants.FeatureFlags => 3,
            AdminCategoryConstants.ReferenceLookups => 4,
            AdminCategoryConstants.DocumentsAndTemplates => 5,
            AdminCategoryConstants.DelegationsAndGovernance => 6,
            AdminCategoryConstants.ComplianceAndStatutory => 7,
            AdminCategoryConstants.AuditAndForensics => 8,
            AdminCategoryConstants.DiagnosticsAndDeveloper => 9,
            _ => 99
        };
    }
}
