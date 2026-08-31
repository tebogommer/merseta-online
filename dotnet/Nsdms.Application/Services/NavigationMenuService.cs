using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;
using Nsdms.Domain.Security;
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;
using System.Text.Json;
using System.Threading.Tasks;

namespace Nsdms.Application.Services;

public class NavigationMenuService : INavigationMenuService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;
    private readonly ICaslAbilityService _caslService;
    private readonly IAdminCatalogService _adminCatalogService;

    // Thread-safe in-memory cache for user navigation preferences
    private static readonly ConcurrentDictionary<string, UserNavPreferencesDto> _userPreferencesCache = new(StringComparer.OrdinalIgnoreCase);

    public NavigationMenuService(
        INsdmsDbContextFactory contextFactory,
        IAuditService audit,
        ICaslAbilityService caslService,
        IAdminCatalogService adminCatalogService)
    {
        _contextFactory = contextFactory;
        _audit = audit;
        _caslService = caslService;
        _adminCatalogService = adminCatalogService;
    }

    public List<string> GetPredefinedPersonas()
    {
        return new List<string>
        {
            "All Roles (Unified)",
            "Skills Development Facilitator (SDF)",
            "Finance & Disbursements Specialist",
            "Assessor & Quality Assurance Partner",
            "Skills Development Provider (SDP)",
            "SETA Client Liaison Officer (CLO)",
            "Executive & Governance",
            "System Administrator"
        };
    }

    public async Task<NavigationTreeResultDto> GetUserNavigationTreeAsync(
        string username,
        List<string> roles,
        HashSet<string> permissions,
        string? activePersona = null)
    {
        var preferences = await GetUserPreferencesAsync(username);
        var persona = string.IsNullOrWhiteSpace(activePersona) ? (preferences.ActivePersonaFilter ?? "All") : activePersona;

        var allCatalogItems = BuildMasterNavigationCatalog();
        var badgeCounts = await GetDynamicBadgeCountsAsync(username, roles);

        // Determine if user is super admin
        bool isSuperAdmin = roles.Any(r => r.Equals("SuperAdmin", StringComparison.OrdinalIgnoreCase) || 
                                           r.Equals("Admin", StringComparison.OrdinalIgnoreCase) || 
                                           username.Equals("Admin", StringComparison.OrdinalIgnoreCase));

        // Filter items by role & permissions (Union RBAC)
        var accessibleItems = allCatalogItems.Where(item =>
        {
            if (isSuperAdmin && (persona.StartsWith("All", StringComparison.OrdinalIgnoreCase) || persona.StartsWith("System", StringComparison.OrdinalIgnoreCase)))
            {
                return true;
            }

            // Check persona tag filter if specific persona is chosen
            if (!persona.StartsWith("All", StringComparison.OrdinalIgnoreCase))
            {
                var normalizedPersona = NormalizePersonaKey(persona);
                if (!item.PersonaTags.Any(t => t.Equals(normalizedPersona, StringComparison.OrdinalIgnoreCase) || t.Equals("All", StringComparison.OrdinalIgnoreCase)))
                {
                    return false;
                }
            }

            // If superadmin simulating a persona, allow items tagged for that persona
            if (isSuperAdmin)
            {
                return true;
            }

            // Check permissions & role requirements
            if (item.RequiredRoles.Any() && item.RequiredRoles.Any(r => roles.Any(ur => ur.Equals(r, StringComparison.OrdinalIgnoreCase))))
            {
                return true;
            }

            if (!string.IsNullOrEmpty(item.RequiredModule))
            {
                var action = item.RequiredAction ?? AppPermissions.ActionView;
                var claim = AppPermissions.Create(item.RequiredModule, action);
                var manageClaim = AppPermissions.Create(item.RequiredModule, AppPermissions.ActionManage);
                
                if (permissions.Contains(claim) || permissions.Contains(manageClaim))
                {
                    return true;
                }
            }

            return item.RequiredRoles.Count == 0 && string.IsNullOrEmpty(item.RequiredModule);
        }).ToList();

        // Apply badges & pinned state
        var pinnedSet = new HashSet<string>(preferences.PinnedItemIds, StringComparer.OrdinalIgnoreCase);
        foreach (var item in accessibleItems)
        {
            item.IsPinned = pinnedSet.Contains(item.Id);
            if (badgeCounts.TryGetValue(item.Id, out var count))
            {
                item.BadgeCount = count;
                item.BadgeColor = count > 5 ? "Error" : count > 0 ? "Warning" : "Primary";
            }
        }

        // Extract pinned items
        var pinnedItems = accessibleItems.Where(i => i.IsPinned).OrderBy(i => i.DisplayOrder).ToList();

        // Group into sections
        var groups = accessibleItems
            .GroupBy(i => i.Category)
            .Select(g => new NavGroupDto
            {
                GroupId = g.Key.ToLowerInvariant().Replace(" ", "-").Replace("&", "and"),
                GroupName = g.Key,
                Icon = GetGroupIcon(g.Key),
                DisplayOrder = GetGroupDisplayOrder(g.Key),
                IsExpanded = !preferences.CollapsedGroupIds.Contains(g.Key, StringComparer.OrdinalIgnoreCase),
                Items = g.OrderBy(i => i.DisplayOrder).ToList()
            })
            .OrderBy(g => g.DisplayOrder)
            .ToList();

        int totalBadgeCount = accessibleItems.Sum(i => i.BadgeCount);

        return new NavigationTreeResultDto
        {
            PinnedItems = pinnedItems,
            Groups = groups,
            ActivePersona = persona,
            AvailablePersonas = GetPredefinedPersonas(),
            TotalItemsCount = accessibleItems.Count,
            TotalPendingBadgeCount = totalBadgeCount
        };
    }

    public async Task<List<NavItemDto>> GetPinnedItemsAsync(string username)
    {
        var prefs = await GetUserPreferencesAsync(username);
        var master = BuildMasterNavigationCatalog();
        var pinnedSet = new HashSet<string>(prefs.PinnedItemIds, StringComparer.OrdinalIgnoreCase);
        
        return master.Where(i => pinnedSet.Contains(i.Id)).OrderBy(i => i.DisplayOrder).ToList();
    }

    public async Task<bool> PinItemAsync(string username, string itemId)
    {
        var prefs = await GetUserPreferencesAsync(username);
        if (!prefs.PinnedItemIds.Contains(itemId, StringComparer.OrdinalIgnoreCase))
        {
            prefs.PinnedItemIds.Add(itemId);
            await SaveUserPreferencesAsync(prefs);
        }
        return true;
    }

    public async Task<bool> UnpinItemAsync(string username, string itemId)
    {
        var prefs = await GetUserPreferencesAsync(username);
        if (prefs.PinnedItemIds.RemoveAll(id => id.Equals(itemId, StringComparison.OrdinalIgnoreCase)) > 0)
        {
            await SaveUserPreferencesAsync(prefs);
        }
        return true;
    }

    public async Task<bool> TogglePinItemAsync(string username, string itemId)
    {
        var prefs = await GetUserPreferencesAsync(username);
        if (prefs.PinnedItemIds.Contains(itemId, StringComparer.OrdinalIgnoreCase))
        {
            prefs.PinnedItemIds.RemoveAll(id => id.Equals(itemId, StringComparison.OrdinalIgnoreCase));
        }
        else
        {
            prefs.PinnedItemIds.Add(itemId);
        }
        await SaveUserPreferencesAsync(prefs);
        return true;
    }

    public Task<UserNavPreferencesDto> GetUserPreferencesAsync(string username)
    {
        var key = string.IsNullOrWhiteSpace(username) ? "DefaultUser" : username.Trim();
        if (_userPreferencesCache.TryGetValue(key, out var cached))
        {
            return Task.FromResult(cached);
        }

        // Default initial shortcuts
        var defaults = new UserNavPreferencesDto
        {
            Username = key,
            PinnedItemIds = new List<string> { "nav-tasks", "nav-employers", "nav-wsp", "nav-grants" },
            CollapsedGroupIds = new List<string>(),
            ActivePersonaFilter = "All"
        };

        _userPreferencesCache[key] = defaults;
        return Task.FromResult(defaults);
    }

    public async Task<bool> SaveUserPreferencesAsync(UserNavPreferencesDto preferences)
    {
        if (preferences == null || string.IsNullOrWhiteSpace(preferences.Username))
        {
            return false;
        }

        _userPreferencesCache[preferences.Username.Trim()] = preferences;

        // Perform double-write audit logging
        await _audit.LogAsync(
            "UserNavigationPreference",
            0,
            "UpdateNavigationPreferences",
            preferences.Username,
            JsonSerializer.Serialize(preferences));

        return true;
    }

    public async Task<List<OmnisearchResultItemDto>> SearchOmnisearchAsync(
        string query,
        string username,
        List<string> roles,
        HashSet<string> permissions,
        int maxResults = 25)
    {
        var results = new List<OmnisearchResultItemDto>();
        var cleanQuery = (query ?? string.Empty).Trim();

        // 1. Search in master navigation items
        var navItems = BuildMasterNavigationCatalog();
        foreach (var item in navItems)
        {
            int score = 0;
            if (string.IsNullOrWhiteSpace(cleanQuery))
            {
                score = 10;
            }
            else if (item.Title.Equals(cleanQuery, StringComparison.OrdinalIgnoreCase))
            {
                score = 100;
            }
            else if (item.Title.Contains(cleanQuery, StringComparison.OrdinalIgnoreCase))
            {
                score = 80;
            }
            else if (item.Description.Contains(cleanQuery, StringComparison.OrdinalIgnoreCase) || 
                     item.Category.Contains(cleanQuery, StringComparison.OrdinalIgnoreCase))
            {
                score = 50;
            }
            else if (item.Keywords.Any(k => k.Contains(cleanQuery, StringComparison.OrdinalIgnoreCase)))
            {
                score = 40;
            }

            if (score > 0)
            {
                results.Add(new OmnisearchResultItemDto
                {
                    Id = item.Id,
                    Title = item.Title,
                    Href = item.Href,
                    Icon = item.Icon,
                    Category = item.Category,
                    Description = item.Description,
                    ItemType = "Navigation",
                    BadgeText = item.BadgeCount > 0 ? item.BadgeCount.ToString() : null,
                    BadgeColor = item.BadgeColor,
                    Score = score
                });
            }
        }

        // 2. Add Quick Actions
        var quickActions = BuildQuickActions();
        foreach (var action in quickActions)
        {
            int score = 0;
            if (string.IsNullOrWhiteSpace(cleanQuery))
            {
                score = 5;
            }
            else if (action.Title.Contains(cleanQuery, StringComparison.OrdinalIgnoreCase) ||
                     action.Description.Contains(cleanQuery, StringComparison.OrdinalIgnoreCase))
            {
                score = 75;
            }

            if (score > 0)
            {
                action.Score = score;
                results.Add(action);
            }
        }

        // 3. Search Admin Catalog items if relevant
        try
        {
            var adminResults = await _adminCatalogService.SearchAsync(cleanQuery, 10);
            foreach (var a in adminResults)
            {
                results.Add(new OmnisearchResultItemDto
                {
                    Id = a.Key,
                    Title = a.Title,
                    Href = a.RouteUrl,
                    Icon = a.Icon,
                    Category = a.Category,
                    Description = a.Description,
                    ItemType = "AdminConfig",
                    BadgeText = a.CurrentValue,
                    BadgeColor = "Secondary",
                    Score = 60
                });
            }
        }
        catch
        {
            // Graceful fallback
        }

        return results.OrderByDescending(r => r.Score).Take(maxResults).ToList();
    }

    public async Task<Dictionary<string, int>> GetDynamicBadgeCountsAsync(string username, List<string> roles)
    {
        var counts = new Dictionary<string, int>(StringComparer.OrdinalIgnoreCase);

        try
        {
            using var db = await _contextFactory.CreateDbContextAsync();

            // Task Inbox Pending
            var taskCount = await db.WorkflowTasks.CountAsync(t => t.TaskStatus == "Open" || t.TaskStatus == "Claimed" || t.TaskStatus == "Pending");
            counts["nav-tasks"] = taskCount;

            // WSP Submissions Pending Review
            var wspCount = await db.WspSubmissions.CountAsync(w => w.WspApprovalStatusCode == "Submitted" || w.WspApprovalStatusCode == "UnderReview");
            counts["nav-wsp"] = wspCount;

            // Discretionary Grants Pending
            var grantCount = await db.GrantApplications.CountAsync(g => g.ApplicationStatusCode == "Submitted" || g.ApplicationStatusCode == "UnderReview");
            counts["nav-grants"] = grantCount;

            // Grant MOAs pending signoff / disbursement
            var moaCount = await db.GrantMoas.CountAsync(m => m.MoaStatusCode == "Approved" || m.MoaStatusCode == "Draft" || m.MoaStatusCode == "Pending Signature");
            counts["nav-finance-grants"] = moaCount;

            // Workplace Monitoring visits pending
            var monitoringCount = await db.WorkplaceMonitoringSiteVisits.CountAsync(v => v.StatusCode == "Draft" || v.StatusCode == "PendingApproval");
            counts["nav-monitoring"] = monitoringCount;
        }
        catch
        {
            // Fallback default sample counts for demo/mock modes
            counts["nav-tasks"] = 12;
            counts["nav-wsp"] = 3;
            counts["nav-grants"] = 5;
            counts["nav-finance-grants"] = 4;
            counts["nav-monitoring"] = 2;
        }

        return counts;
    }

    private string NormalizePersonaKey(string persona)
    {
        if (persona.Contains("SDF", StringComparison.OrdinalIgnoreCase)) return "SDF";
        if (persona.Contains("Finance", StringComparison.OrdinalIgnoreCase)) return "Finance";
        if (persona.Contains("Assessor", StringComparison.OrdinalIgnoreCase)) return "Assessor";
        if (persona.Contains("Provider", StringComparison.OrdinalIgnoreCase) || persona.Contains("SDP", StringComparison.OrdinalIgnoreCase)) return "SDP";
        if (persona.Contains("CLO", StringComparison.OrdinalIgnoreCase)) return "CLO";
        if (persona.Contains("Executive", StringComparison.OrdinalIgnoreCase)) return "Executive";
        if (persona.Contains("Admin", StringComparison.OrdinalIgnoreCase)) return "Admin";
        return "All";
    }

    private string GetGroupIcon(string category)
    {
        return category switch
        {
            "WORKFLOW ORCHESTRATION" => "AccountTree",
            "CORE REGISTRIES" => "FolderShared",
            "GRANTS & WSP" => "AccountBalanceWallet",
            "FINANCE & DISBURSEMENTS" => "ReceiptLong",
            "LEARNER LIFECYCLE" => "School",
            "QUALITY ASSURANCE" => "VerifiedUser",
            "SKILLS PLANNING & BI" => "Insights",
            "LEGAL & POLICY TEMPLATES" => "Gavel",
            "SYSTEM ADMINISTRATION" => "AdminPanelSettings",
            _ => "Folder"
        };
    }

    private int GetGroupDisplayOrder(string category)
    {
        return category switch
        {
            "WORKFLOW ORCHESTRATION" => 1,
            "CORE REGISTRIES" => 2,
            "GRANTS & WSP" => 3,
            "FINANCE & DISBURSEMENTS" => 4,
            "LEARNER LIFECYCLE" => 5,
            "QUALITY ASSURANCE" => 6,
            "SKILLS PLANNING & BI" => 7,
            "LEGAL & POLICY TEMPLATES" => 8,
            "SYSTEM ADMINISTRATION" => 9,
            _ => 99
        };
    }

    private List<OmnisearchResultItemDto> BuildQuickActions()
    {
        return new List<OmnisearchResultItemDto>
        {
            new() { Id = "act-new-org", Title = "Create Organisation / Employer", Href = "employers", Icon = "Business", Category = "Quick Actions", Description = "Register a new levy or non-levy paying organisation", ItemType = "QuickAction" },
            new() { Id = "act-new-wsp", Title = "Submit WSP / ATR Application", Href = "wsp", Icon = "Assignment", Category = "Quick Actions", Description = "Initiate a Workplace Skills Plan submission cycle", ItemType = "QuickAction" },
            new() { Id = "act-new-dg", Title = "Apply for Discretionary Grant (DG)", Href = "grants", Icon = "AccountBalanceWallet", Category = "Quick Actions", Description = "Submit a discretionary grant funding application window", ItemType = "QuickAction" },
            new() { Id = "act-new-learner", Title = "Register New Learner", Href = "learners", Icon = "School", Category = "Quick Actions", Description = "Enrol a learner with RSA ID validation and contract", ItemType = "QuickAction" },
            new() { Id = "act-schedule-visit", Title = "Schedule Employer Monitoring Visit", Href = "monitoring", Icon = "FactCheck", Category = "Quick Actions", Description = "Plan an on-site workplace verification with designated contact person", ItemType = "QuickAction" },
            new() { Id = "act-sars-recon", Title = "Run SARS Levy Reconciliation Audit", Href = "finance/levy-audits", Icon = "Calculate", Category = "Quick Actions", Description = "Audit SARS levy monthly file against DHET distribution", ItemType = "QuickAction" },
            new() { Id = "act-verify-doc", Title = "Verify Document Authenticity", Href = "verify", Icon = "VerifiedUser", Category = "Quick Actions", Description = "Verify cryptographic certificate or MoA hash", ItemType = "QuickAction" }
        };
    }

    private List<NavItemDto> BuildMasterNavigationCatalog()
    {
        return new List<NavItemDto>
        {
            // WORKFLOW ORCHESTRATION
            new()
            {
                Id = "nav-exec-dashboard",
                Title = "Executive Dashboard",
                Href = "",
                ExactMatch = true,
                Icon = "Dashboard",
                Category = "WORKFLOW ORCHESTRATION",
                Description = "High-level KPI overview, statutory milestones, and SETA health metrics",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Executive", "CLO" },
                PersonaTags = new() { "Admin", "Executive", "CLO", "All" },
                DisplayOrder = 1,
                Keywords = new() { "dashboard", "home", "executive", "kpi", "stats", "overview" }
            },
            new()
            {
                Id = "nav-tasks",
                Title = "Universal Task Inbox",
                Href = "tasks",
                Icon = "Inbox",
                Category = "WORKFLOW ORCHESTRATION",
                Description = "Pending workflow approvals, sign-offs, and operational task queue",
                PersonaTags = new() { "All", "Admin", "SDF", "Finance", "Assessor", "SDP", "CLO", "Executive" },
                DisplayOrder = 2,
                Keywords = new() { "tasks", "inbox", "approvals", "pending", "action", "workflow" }
            },
            new()
            {
                Id = "nav-workflow-studio",
                Title = "Workflow Studio & Blueprints",
                Href = "admin/workflows",
                Icon = "AccountTree",
                Category = "WORKFLOW ORCHESTRATION",
                Description = "Visual workflow designer, state machine transitions, and SLA configurations",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionManage,
                RequiredRoles = new() { "SuperAdmin", "Admin" },
                PersonaTags = new() { "Admin" },
                DisplayOrder = 3,
                Keywords = new() { "workflows", "blueprints", "states", "designer", "studio", "sla" }
            },

            // CORE REGISTRIES
            new()
            {
                Id = "nav-people",
                Title = "People & Demographics",
                Href = "people",
                Icon = "People",
                Category = "CORE REGISTRIES",
                Description = "Master registry of citizens, learners, assessors, and demographic profiles",
                RequiredModule = AppPermissions.ModulePeople,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "CLO", "SDF" },
                PersonaTags = new() { "Admin", "CLO", "SDF" },
                DisplayOrder = 10,
                Keywords = new() { "people", "citizens", "demographics", "rsa id", "contacts" }
            },
            new()
            {
                Id = "nav-employers",
                Title = "Employers / Organisations",
                Href = "employers",
                Icon = "Business",
                Category = "CORE REGISTRIES",
                Description = "Levy and non-levy organisations, chambers, SDL numbers, and contact persons",
                RequiredModule = AppPermissions.ModuleOrganisations,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "SDF", "CLO", "Finance" },
                PersonaTags = new() { "Admin", "SDF", "CLO", "Finance" },
                DisplayOrder = 11,
                Keywords = new() { "employers", "organisations", "companies", "sdl", "chamber", "levy" }
            },
            new()
            {
                Id = "nav-sdf",
                Title = "SDF Appointments",
                Href = "employers/sdf",
                Icon = "Badge",
                Category = "CORE REGISTRIES",
                Description = "Skills Development Facilitator registrations, appointment letters, and linkages",
                RequiredModule = AppPermissions.ModuleOrganisations,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "SDF", "CLO" },
                PersonaTags = new() { "Admin", "SDF", "CLO" },
                DisplayOrder = 12,
                Keywords = new() { "sdf", "facilitator", "appointments", "nominations", "skills development" }
            },
            new()
            {
                Id = "nav-sdp",
                Title = "Skills Development Providers",
                Href = "sdp",
                Icon = "School",
                Category = "CORE REGISTRIES",
                Description = "Accredited training providers, campuses, qualifications scope, and audits",
                RequiredModule = AppPermissions.ModuleOrganisations,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "SDP", "CLO", "Assessor" },
                PersonaTags = new() { "Admin", "SDP", "CLO", "Assessor" },
                DisplayOrder = 13,
                Keywords = new() { "sdp", "providers", "training", "colleges", "institutions", "accreditation" }
            },
            new()
            {
                Id = "nav-curriculum",
                Title = "Curriculum Development (QCD)",
                Href = "curriculum",
                Icon = "MenuBook",
                Category = "CORE REGISTRIES",
                Description = "QCTO curriculum scoping, occupational qualification blueprints, and modules",
                RequiredModule = AppPermissions.ModuleEtqa,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Assessor", "SDP" },
                PersonaTags = new() { "Admin", "Assessor", "SDP" },
                DisplayOrder = 14,
                Keywords = new() { "qcd", "curriculum", "qcto", "qualifications", "scoping", "modules" }
            },

            // GRANTS & WSP
            new()
            {
                Id = "nav-wsp",
                Title = "WSP & Mandatory Grants",
                Href = "wsp",
                Icon = "Assignment",
                Category = "GRANTS & WSP",
                Description = "Workplace Skills Plans, Annual Training Reports, and Mandatory Grant claims",
                RequiredModule = AppPermissions.ModuleWsp,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "SDF", "CLO", "Finance" },
                PersonaTags = new() { "Admin", "SDF", "CLO", "Finance" },
                DisplayOrder = 20,
                Keywords = new() { "wsp", "atr", "mandatory grant", "skills plan", "pivotal" }
            },
            new()
            {
                Id = "nav-wsp-committees",
                Title = "Training Committees",
                Href = "wsp/committees",
                Icon = "Groups",
                Category = "GRANTS & WSP",
                Description = "Consultative training committees, meeting minutes, and labour sign-offs",
                RequiredModule = AppPermissions.ModuleWsp,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "SDF", "CLO" },
                PersonaTags = new() { "Admin", "SDF", "CLO" },
                DisplayOrder = 21,
                Keywords = new() { "committees", "training committee", "labour", "union", "consultation" }
            },
            new()
            {
                Id = "nav-grants",
                Title = "Discretionary Grants (DG)",
                Href = "grants",
                Icon = "AccountBalanceWallet",
                Category = "GRANTS & WSP",
                Description = "Discretionary grant funding windows, applications, scoring, and MoA awards",
                RequiredModule = AppPermissions.ModuleGrants,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "SDF", "Finance", "CLO" },
                PersonaTags = new() { "Admin", "SDF", "Finance", "CLO" },
                DisplayOrder = 22,
                Keywords = new() { "dg", "discretionary grants", "funding", "allocations", "applications" }
            },
            new()
            {
                Id = "nav-grants-pip",
                Title = "Project Implementation (PIP)",
                Href = "grants/pip",
                Icon = "AssignmentTurnedIn",
                Category = "GRANTS & WSP",
                Description = "PIP milestones, learner enrollment targets, and tranche claim verification",
                RequiredModule = AppPermissions.ModuleGrants,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "SDF", "Finance", "CLO" },
                PersonaTags = new() { "Admin", "SDF", "Finance", "CLO" },
                DisplayOrder = 23,
                Keywords = new() { "pip", "project implementation", "milestones", "tranches", "targets" }
            },
            new()
            {
                Id = "nav-contracts-variations",
                Title = "Contract Addenda & Variations",
                Href = "contracts/variations",
                Icon = "Difference",
                Category = "GRANTS & WSP",
                Description = "Contract variation requests, time extensions, budget re-allocations, and legal addenda",
                RequiredModule = AppPermissions.ModuleGrants,
                RequiredAction = AppPermissions.ActionEdit,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Finance", "Legal" },
                PersonaTags = new() { "Admin", "Finance", "Legal" },
                DisplayOrder = 24,
                Keywords = new() { "variations", "addenda", "contract amendments", "extensions", "legal" }
            },

            // FINANCE & DISBURSEMENTS
            new()
            {
                Id = "nav-finance-grants",
                Title = "Grant MOAs & Tranches",
                Href = "finance/grants",
                Icon = "Description",
                Category = "FINANCE & DISBURSEMENTS",
                Description = "Memorandums of Agreement, tranche schedules, GP ERP posting batches",
                RequiredModule = AppPermissions.ModuleFinance,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Finance" },
                PersonaTags = new() { "Admin", "Finance" },
                DisplayOrder = 30,
                Keywords = new() { "moa", "tranches", "disbursements", "erp", "gp", "payments" }
            },
            new()
            {
                Id = "nav-finance-banking",
                Title = "Banking Details & Signoff",
                Href = "finance/banking-details",
                Icon = "AccountBalance",
                Category = "FINANCE & DISBURSEMENTS",
                Description = "Bank account verification (AVS), dual-control sign-off, and audit trail",
                RequiredModule = AppPermissions.ModuleFinance,
                RequiredAction = AppPermissions.ActionVerify,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Finance" },
                PersonaTags = new() { "Admin", "Finance" },
                DisplayOrder = 31,
                Keywords = new() { "banking", "avs", "bankserv", "account verification", "signoff" }
            },
            new()
            {
                Id = "nav-finance-rebates",
                Title = "Mandatory Grant Rebates",
                Href = "finance/levy-rebates",
                Icon = "Payments",
                Category = "FINANCE & DISBURSEMENTS",
                Description = "20% Mandatory grant payout calculations, remittance advice, and EFT batches",
                RequiredModule = AppPermissions.ModuleFinance,
                RequiredAction = AppPermissions.ActionDisburse,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Finance" },
                PersonaTags = new() { "Admin", "Finance" },
                DisplayOrder = 32,
                Keywords = new() { "rebates", "mandatory rebate", "remittance", "20%", "payouts" }
            },
            new()
            {
                Id = "nav-finance-audits",
                Title = "SARS Levy Audits & Clawbacks",
                Href = "finance/levy-audits",
                Icon = "Calculate",
                Category = "FINANCE & DISBURSEMENTS",
                Description = "SARS levy reconciliation audits, clawback letters, and interest calculations",
                RequiredModule = AppPermissions.ModuleFinance,
                RequiredAction = AppPermissions.ActionReconcile,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Finance" },
                PersonaTags = new() { "Admin", "Finance" },
                DisplayOrder = 33,
                Keywords = new() { "clawbacks", "sars audits", "reconciliation", "adjustments" }
            },
            new()
            {
                Id = "nav-levies",
                Title = "SARS Levy Files",
                Href = "levies",
                Icon = "ReceiptLong",
                Category = "FINANCE & DISBURSEMENTS",
                Description = "Monthly SARS electronic levy files, chamber distribution, and employer reconciliations",
                RequiredModule = AppPermissions.ModuleFinance,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Finance" },
                PersonaTags = new() { "Admin", "Finance" },
                DisplayOrder = 34,
                Keywords = new() { "levies", "sars files", "levy downloads", "monthly levies" }
            },
            new()
            {
                Id = "nav-inter-seta",
                Title = "Inter-SETA Transfers",
                Href = "inter-seta-transfers",
                Icon = "SwapHoriz",
                Category = "FINANCE & DISBURSEMENTS",
                Description = "Transfers between SETAs for SIC code moves, levy adjustments, and approvals",
                RequiredModule = AppPermissions.ModuleFinance,
                RequiredAction = AppPermissions.ActionTransfer,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Finance" },
                PersonaTags = new() { "Admin", "Finance" },
                DisplayOrder = 35,
                Keywords = new() { "inter-seta", "transfers", "sic codes", "seta transfer" }
            },

            // LEARNER LIFECYCLE
            new()
            {
                Id = "nav-learners",
                Title = "Learner Management",
                Href = "learners",
                Icon = "School",
                Category = "LEARNER LIFECYCLE",
                Description = "Learner registrations, agreements, NLRD submissions, and progress tracking",
                RequiredModule = AppPermissions.ModuleLearners,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "SDF", "SDP", "CLO" },
                PersonaTags = new() { "Admin", "SDF", "SDP", "CLO" },
                DisplayOrder = 40,
                Keywords = new() { "learners", "students", "agreements", "nlrd", "contracts", "artisan" }
            },
            new()
            {
                Id = "nav-tradetests",
                Title = "Trade Tests & ARPL",
                Href = "tradetests",
                Icon = "FactCheck",
                Category = "LEARNER LIFECYCLE",
                Description = "Artisan Recognition of Prior Learning (ARPL), trade test centers, and certifications",
                RequiredModule = AppPermissions.ModuleLearners,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Assessor", "SDP", "CLO" },
                PersonaTags = new() { "Admin", "Assessor", "SDP", "CLO" },
                DisplayOrder = 41,
                Keywords = new() { "trade tests", "arpl", "artisans", "red seal", "certification" }
            },
            new()
            {
                Id = "nav-assessments",
                Title = "Summative Assessments & SOR",
                Href = "assessments/summative",
                Icon = "AssignmentTurnedIn",
                Category = "LEARNER LIFECYCLE",
                Description = "Summative assessment reporting, Statement of Results (SOR), and moderation",
                RequiredModule = AppPermissions.ModuleLearners,
                RequiredAction = AppPermissions.ActionModerate,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Assessor", "SDP" },
                PersonaTags = new() { "Admin", "Assessor", "SDP" },
                DisplayOrder = 42,
                Keywords = new() { "assessments", "sor", "statement of results", "moderation", "grades" }
            },

            // QUALITY ASSURANCE
            new()
            {
                Id = "nav-monitoring",
                Title = "Workplace Monitoring & Audits",
                Href = "monitoring",
                Icon = "FactCheck",
                Category = "QUALITY ASSURANCE",
                Description = "On-site employer visits, monitoring reports, and remediation findings",
                RequiredModule = AppPermissions.ModuleWorkplace,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "CLO", "Assessor" },
                PersonaTags = new() { "Admin", "CLO", "Assessor" },
                DisplayOrder = 50,
                Keywords = new() { "monitoring", "site visits", "audits", "remediation", "workplace" }
            },
            new()
            {
                Id = "nav-etqa",
                Title = "ETQA & Assessors",
                Href = "etqa",
                Icon = "VerifiedUser",
                Category = "QUALITY ASSURANCE",
                Description = "Assessor and moderator registrations, provider accreditation certificates",
                RequiredModule = AppPermissions.ModuleEtqa,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Assessor", "CLO" },
                PersonaTags = new() { "Admin", "Assessor", "CLO" },
                DisplayOrder = 51,
                Keywords = new() { "etqa", "assessors", "moderators", "accreditation", "qa" }
            },
            new()
            {
                Id = "nav-etqa-aqp",
                Title = "Assessment Quality Partners (AQP)",
                Href = "etqa/aqp",
                Icon = "FactCheck",
                Category = "QUALITY ASSURANCE",
                Description = "AQP partner agreements, external integrated summative assessment (EISA) oversight",
                RequiredModule = AppPermissions.ModuleEtqa,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Assessor", "SDP" },
                PersonaTags = new() { "Admin", "Assessor", "SDP" },
                DisplayOrder = 52,
                Keywords = new() { "aqp", "eisa", "quality partners", "qcto assessment" }
            },
            new()
            {
                Id = "nav-etqa-scope",
                Title = "Accreditation Scope Extensions",
                Href = "etqa/scope-extensions",
                Icon = "FactCheck",
                Category = "QUALITY ASSURANCE",
                Description = "Provider program extension requests, unit standard additions, and evaluations",
                RequiredModule = AppPermissions.ModuleEtqa,
                RequiredAction = AppPermissions.ActionEdit,
                RequiredRoles = new() { "SuperAdmin", "Admin", "SDP", "Assessor" },
                PersonaTags = new() { "Admin", "SDP", "Assessor" },
                DisplayOrder = 53,
                Keywords = new() { "scope extension", "accreditation extension", "unit standards" }
            },
            new()
            {
                Id = "nav-non-seta",
                Title = "Non-SETA Articulations",
                Href = "non-seta/verifications",
                Icon = "DomainVerification",
                Category = "QUALITY ASSURANCE",
                Description = "Cross-SETA qualification verifications, external artisan endorsements",
                RequiredModule = AppPermissions.ModuleEtqa,
                RequiredAction = AppPermissions.ActionVerify,
                RequiredRoles = new() { "SuperAdmin", "Admin", "SDP", "CLO" },
                PersonaTags = new() { "Admin", "SDP", "CLO" },
                DisplayOrder = 54,
                Keywords = new() { "non-seta", "articulations", "cross-seta", "verifications" }
            },
            new()
            {
                Id = "nav-wpa",
                Title = "Workplace Approvals (WPA)",
                Href = "workplace-approvals",
                Icon = "Handyman",
                Category = "QUALITY ASSURANCE",
                Description = "Workplace approval applications, safety checks, and mentor certifications",
                RequiredModule = AppPermissions.ModuleWorkplace,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "SDF", "CLO" },
                PersonaTags = new() { "Admin", "SDF", "CLO" },
                DisplayOrder = 55,
                Keywords = new() { "wpa", "workplace approval", "mentor", "safety", "workplace" }
            },
            new()
            {
                Id = "nav-trade-mentor-ratios",
                Title = "Trade Mentor Ratios",
                Href = "reference-data/trade-mentor-ratios",
                Icon = "Engineering",
                Category = "QUALITY ASSURANCE",
                Description = "Statutory artisan mentor-to-apprentice ratios and multi-tiered exemption policy matrix",
                RequiredModule = AppPermissions.ModuleWorkplace,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "CLO" },
                PersonaTags = new() { "Admin", "CLO" },
                DisplayOrder = 56,
                Keywords = new() { "ratio", "mentor ratio", "artisan ratio", "capacity", "trade policy", "exemption" }
            },

            // SKILLS PLANNING & BI
            new()
            {
                Id = "nav-bi-reports",
                Title = "Executive Skills BI & SSP",
                Href = "reports/bi",
                Icon = "Insights",
                Category = "SKILLS PLANNING & BI",
                Description = "Sector Skills Plan (SSP) analytics, scarce skills heatmaps, and DHET reports",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Executive", "CLO" },
                PersonaTags = new() { "Admin", "Executive", "CLO" },
                DisplayOrder = 60,
                Keywords = new() { "bi", "analytics", "ssp", "sector skills plan", "scarce skills", "reports" }
            },

            // LEGAL & POLICY TEMPLATES
            new()
            {
                Id = "nav-moa-templates",
                Title = "MoA Template Studio",
                Href = "legal/moa-templates",
                Icon = "Gavel",
                Category = "LEGAL & POLICY TEMPLATES",
                Description = "Draft and publish dynamic legal MoA templates with parameterized placeholders",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionManage,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Legal", "Finance" },
                PersonaTags = new() { "Admin", "Legal", "Finance" },
                DisplayOrder = 70,
                Keywords = new() { "moa templates", "legal templates", "contracts", "studio" }
            },
            new()
            {
                Id = "nav-doc-templates",
                Title = "Enterprise Document Templates",
                Href = "admin/document-templates",
                Icon = "Description",
                Category = "LEGAL & POLICY TEMPLATES",
                Description = "Certificate templates, rejection notices, and statutory correspondence blueprints",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionManage,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Legal" },
                PersonaTags = new() { "Admin", "Legal" },
                DisplayOrder = 71,
                Keywords = new() { "document templates", "letters", "notices", "certificates" }
            },
            new()
            {
                Id = "nav-moa-clauses",
                Title = "Clause Library",
                Href = "legal/moa-clauses",
                Icon = "LibraryBooks",
                Category = "LEGAL & POLICY TEMPLATES",
                Description = "Standardized statutory clauses, dispute resolution rules, and breach terms",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Legal" },
                PersonaTags = new() { "Admin", "Legal" },
                DisplayOrder = 72,
                Keywords = new() { "clauses", "legal clauses", "terms", "conditions", "dispute" }
            },
            new()
            {
                Id = "nav-doc-snapshots",
                Title = "Document Security & Snapshots",
                Href = "admin/document-snapshots",
                Icon = "Security",
                Category = "LEGAL & POLICY TEMPLATES",
                Description = "Cryptographic document hashing, tamper-proofing logs, and digital signatures",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionVerify,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Legal" },
                PersonaTags = new() { "Admin", "Legal" },
                DisplayOrder = 73,
                Keywords = new() { "snapshots", "security", "hashes", "digital signatures", "tamper-proof" }
            },
            new()
            {
                Id = "nav-verify",
                Title = "Digital Verification Portal",
                Href = "verify",
                Icon = "VerifiedUser",
                Category = "LEGAL & POLICY TEMPLATES",
                Description = "Public and internal verification of issued certificates, letters, and MoAs",
                PersonaTags = new() { "All", "Admin", "SDF", "Finance", "Assessor", "SDP", "CLO" },
                DisplayOrder = 74,
                Keywords = new() { "verification", "verify portal", "qr code", "authenticity" }
            },

            // SYSTEM ADMINISTRATION
            new()
            {
                Id = "nav-admin",
                Title = "Administration & Control Hub",
                Href = "admin",
                ExactMatch = true,
                Icon = "AdminPanelSettings",
                Category = "SYSTEM ADMINISTRATION",
                Description = "Central control hub, system telemetry, and governance dashboard",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin" },
                PersonaTags = new() { "Admin" },
                DisplayOrder = 80,
                Keywords = new() { "admin", "control hub", "telemetry", "system" }
            },
            new()
            {
                Id = "nav-admin-settings",
                Title = "System Settings & Features",
                Href = "admin/settings",
                Icon = "Settings",
                Category = "SYSTEM ADMINISTRATION",
                Description = "System configuration parameters, external integration feature toggles",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionManage,
                RequiredRoles = new() { "SuperAdmin", "Admin" },
                PersonaTags = new() { "Admin" },
                DisplayOrder = 81,
                Keywords = new() { "settings", "configuration", "feature flags", "parameters", "integrations" }
            },
            new()
            {
                Id = "nav-admin-roles",
                Title = "Security Roles & Permissions",
                Href = "admin/roles",
                Icon = "Security",
                Category = "SYSTEM ADMINISTRATION",
                Description = "CASL claims authorization, role definitions, and user permission overrides",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionManage,
                RequiredRoles = new() { "SuperAdmin", "Admin" },
                PersonaTags = new() { "Admin" },
                DisplayOrder = 82,
                Keywords = new() { "roles", "permissions", "casl", "security", "users", "rbac" }
            },
            new()
            {
                Id = "nav-admin-lookups",
                Title = "Lookup Hub & Enums",
                Href = "admin/lookups",
                Icon = "Tune",
                Category = "SYSTEM ADMINISTRATION",
                Description = "SETMIS lookup tables, statutory enumerations, and reference data management",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionManage,
                RequiredRoles = new() { "SuperAdmin", "Admin" },
                PersonaTags = new() { "Admin" },
                DisplayOrder = 83,
                Keywords = new() { "lookups", "enums", "reference data", "setmis codes", "tables" }
            },
            new()
            {
                Id = "nav-admin-meetings",
                Title = "Committee & MANCO Meetings",
                Href = "governance/meetings",
                Icon = "MeetingRoom",
                Category = "SYSTEM ADMINISTRATION",
                Description = "Board, MANCO, and Grant Adjudication meeting resolutions and delegations",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Executive" },
                PersonaTags = new() { "Admin", "Executive" },
                DisplayOrder = 84,
                Keywords = new() { "meetings", "manco", "board", "governance", "resolutions", "delegations" }
            },
            new()
            {
                Id = "nav-admin-statutory",
                Title = "Statutory Submissions & Audits",
                Href = "compliance/statutory",
                Icon = "FactCheck",
                Category = "SYSTEM ADMINISTRATION",
                Description = "Quarterly DHET SETMIS submissions, NLRD batch uploads, and AGSA audit pack",
                RequiredModule = AppPermissions.ModuleCompliance,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Compliance" },
                PersonaTags = new() { "Admin", "Compliance" },
                DisplayOrder = 85,
                Keywords = new() { "statutory", "setmis submissions", "nlrd", "agsa", "audits", "dhet" }
            },
            new()
            {
                Id = "nav-admin-audit",
                Title = "Audit Trail",
                Href = "audit-logs",
                Icon = "History",
                Category = "SYSTEM ADMINISTRATION",
                Description = "System-wide double-write immutable audit log, before/after JSON diffs",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin" },
                PersonaTags = new() { "Admin" },
                DisplayOrder = 86,
                Keywords = new() { "audit", "logs", "forensics", "history", "double-write", "trail" }
            },
            new()
            {
                Id = "nav-admin-schema",
                Title = "Data Dictionary & Schema",
                Href = "developer/schema",
                Icon = "MenuBook",
                Category = "SYSTEM ADMINISTRATION",
                Description = "Interactive database schema documentation, temporal versioning tables",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Developer" },
                PersonaTags = new() { "Admin", "Developer" },
                DisplayOrder = 87,
                Keywords = new() { "data dictionary", "schema", "tables", "database", "temporal" }
            },
            new()
            {
                Id = "nav-admin-compliance",
                Title = "UI/UX Compliance HUD",
                Href = "developer/compliance-audit",
                Icon = "Verified",
                Category = "SYSTEM ADMINISTRATION",
                Description = "Automated compliance auditor for UI-STANDARD.md, WCAG 2.2 AA, and heuristics",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Developer" },
                PersonaTags = new() { "Admin", "Developer" },
                DisplayOrder = 88,
                Keywords = new() { "compliance hud", "ui standard", "wcag", "heuristics", "audit" }
            }
        };
    }
}
