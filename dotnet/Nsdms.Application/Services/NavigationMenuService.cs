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

    // Thread-safe static lazy cache for master navigation catalog definition
    private static readonly Lazy<List<NavItemDto>> _masterNavigationCatalog = new(InitializeMasterNavigationCatalog);

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

    public List<PersonaProfileDto> GetPersonaProfiles()
    {
        return new List<PersonaProfileDto>
        {
            // === Group 1: Workplace Approval Personas ===
            new()
            {
                PersonaKey = "WorkplaceMentor",
                PersonaTitle = "Workplace Artisan Mentor (Sipho Khumalo)",
                ShortLabel = "Sipho Khumalo (Mentor)",
                CompactLabel = "Sipho K. (Mentor)",
                PersonName = "Sipho Khumalo",
                Email = "sipho.khumalo@toyota.co.za",
                Initials = "SK",
                StatutoryRole = "Workplace Artisan Mentor & Workshop Lead",
                RoleBadge = "ARTISAN MENTOR",
                OrganisationName = "Toyota South Africa Motors (Pty) Ltd",
                Subtitle = "Master Artisan (ART-1999-88741) • Supervising Motor Mechanic Apprentices",
                Category = "Workplace Approval",
                Icon = "Build",
                ThemeColor = "Warning",
                Roles = new() { "Mentor", "ArtisanMentor", "CLO" },
                IsWorkplaceApprovalPersona = true,
                WorkplaceReference = "Toyota Prospection Plant • WPA-2025-TOYOTA-PROS"
            },
            new()
            {
                PersonaKey = "CLOInspector",
                PersonaTitle = "SETA Client Liaison Officer (Thabo Molefe)",
                ShortLabel = "Thabo Molefe (CLO)",
                CompactLabel = "Thabo M. (CLO)",
                PersonName = "Thabo Molefe",
                Email = "thabo.molefe@merseta.org.za",
                Initials = "TM",
                StatutoryRole = "SETA Client Liaison Officer (CLO) & Site Inspector",
                RoleBadge = "CLO INSPECTOR",
                OrganisationName = "merSETA Regional Operations",
                Subtitle = "Conducts physical workplace audits, tool checklists & site verification",
                Category = "Workplace Approval",
                Icon = "FactCheck",
                ThemeColor = "Info",
                Roles = new() { "CLO", "Inspector" },
                IsWorkplaceApprovalPersona = true,
                WorkplaceReference = "Regional Inspectorate • Workplace Approval Site Audits"
            },
            new()
            {
                PersonaKey = "EmployerContactSDF",
                PersonaTitle = "Employer Contact Person & SDF (Nalini Moodley)",
                ShortLabel = "Nalini Moodley (SDF)",
                CompactLabel = "Nalini M. (SDF)",
                PersonName = "Nalini Moodley",
                Email = "nalini.moodley@toyota.co.za",
                Initials = "NM",
                StatutoryRole = "Designated Employer Contact Person & Primary SDF",
                RoleBadge = "SDF • EMPLOYER",
                OrganisationName = "Toyota South Africa Motors (Pty) Ltd",
                Subtitle = "Mandatory contact person for workplace visits & WSP submissions",
                Category = "Workplace Approval",
                Icon = "Work",
                ThemeColor = "Primary",
                Roles = new() { "SDF", "EmployerContact" },
                IsWorkplaceApprovalPersona = true,
                WorkplaceReference = "Host Employer Lead • Contact Person for Site Inspections"
            },
            new()
            {
                PersonaKey = "WorkplaceAssessor",
                PersonaTitle = "Workplace Trade Assessor (Bongani Mabaso)",
                ShortLabel = "Bongani Mabaso (Assessor)",
                CompactLabel = "Bongani M. (Assessor)",
                PersonName = "Bongani Mabaso",
                Email = "bongani.mabaso@assessors.org.za",
                Initials = "BM",
                StatutoryRole = "Registered Trade Assessor & ETQA Evaluator",
                RoleBadge = "ETQA ASSESSOR",
                OrganisationName = "merSETA ETQA Accreditation Partner",
                Subtitle = "Inspects workshop trade tooling & conducts ARPL trade test assessments",
                Category = "Workplace Approval",
                Icon = "VerifiedUser",
                ThemeColor = "Success",
                Roles = new() { "Assessor", "ETQA" },
                IsWorkplaceApprovalPersona = true,
                WorkplaceReference = "Inspecting Assessor on WPA-2025-TOYOTA-PROS"
            },
            new()
            {
                PersonaKey = "ReviewCommittee",
                PersonaTitle = "Approval Review Committee (Dr. Johan van der Merwe)",
                ShortLabel = "Dr. Johan v.d. Merwe (Chair)",
                CompactLabel = "Dr. Johan (Chair)",
                PersonName = "Dr. Johan van der Merwe",
                Email = "johan.vdm@merietasamples.co.za",
                Initials = "JM",
                StatutoryRole = "Review & Adjudication Committee Chair",
                RoleBadge = "COMMITTEE CHAIR",
                OrganisationName = "merSETA Executive Directorate",
                Subtitle = "Adjudicates workplace approvals, site accreditations & mentor ratio exemptions",
                Category = "Workplace Approval",
                Icon = "Gavel",
                ThemeColor = "Secondary",
                Roles = new() { "ReviewCommittee", "Executive" },
                IsWorkplaceApprovalPersona = true,
                WorkplaceReference = "Executive Governance • Workplace Approval Ratification"
            },

            // === Group 2: Enterprise & Statutory Personas ===
            new()
            {
                PersonaKey = "UnifiedAdmin",
                PersonaTitle = "All Roles (Unified) • System Administrator",
                ShortLabel = "System Administrator",
                CompactLabel = "SysAdmin",
                PersonName = "System Administrator",
                Email = "sysadmin@merseta.org.za",
                Initials = "SA",
                StatutoryRole = "Enterprise Super Administrator",
                RoleBadge = "SUPERADMIN",
                OrganisationName = "merSETA ICT & Executive Directorate",
                Subtitle = "Full administrative access across all 7 statutory pillars and workflow engine",
                Category = "Enterprise Governance",
                Icon = "AdminPanelSettings",
                ThemeColor = "Dark",
                Roles = new() { "SuperAdmin", "Admin", "FinanceManager", "CLO", "SDF", "SDP", "Assessor", "Executive", "Legal", "Compliance", "Mentor" },
                IsWorkplaceApprovalPersona = false,
                WorkplaceReference = "Global System Authority"
            },
            new()
            {
                PersonaKey = "FinanceSpecialist",
                PersonaTitle = "Finance & Disbursements (Fatima Adams)",
                ShortLabel = "Fatima Adams (Finance)",
                CompactLabel = "Fatima A. (Finance)",
                PersonName = "Fatima Adams",
                Email = "fatima.adams@finance.merseta.org.za",
                Initials = "FA",
                StatutoryRole = "Senior Finance & Grant Disbursements Officer",
                RoleBadge = "FINANCE SPECIALIST",
                OrganisationName = "merSETA Finance Directorate",
                Subtitle = "DG tranche claims, MoA payment vouchers, and SARS levy allocations",
                Category = "Enterprise Governance",
                Icon = "AccountBalance",
                ThemeColor = "Success",
                Roles = new() { "FinanceManager" },
                IsWorkplaceApprovalPersona = false
            },
            new()
            {
                PersonaKey = "SkillsProvider",
                PersonaTitle = "Skills Development Provider (Prof. K. Naidoo)",
                ShortLabel = "Prof. K. Naidoo (SDP)",
                CompactLabel = "Prof. Naidoo (SDP)",
                PersonName = "Prof. K. Naidoo",
                Email = "k.naidoo@vut-accredited.ac.za",
                Initials = "KN",
                StatutoryRole = "Accredited SDP Site Director",
                RoleBadge = "SDP PRINCIPAL",
                OrganisationName = "Vaal Skills & Technology Centre",
                Subtitle = "Accredited occupational qualifications, learner enrolments & delivery site management",
                Category = "Enterprise Governance",
                Icon = "School",
                ThemeColor = "Info",
                Roles = new() { "SDP" },
                IsWorkplaceApprovalPersona = false
            },
            new()
            {
                PersonaKey = "LegalSpecialist",
                PersonaTitle = "Legal & Contracting (Adv. Nomvula Dlamini)",
                ShortLabel = "Adv. Nomvula Dlamini (Legal)",
                CompactLabel = "Adv. Dlamini (Legal)",
                PersonName = "Adv. Nomvula Dlamini",
                Email = "legal@merseta.org.za",
                Initials = "ND",
                StatutoryRole = "Legal & MoA Contracting Specialist",
                RoleBadge = "LEGAL COUNSEL",
                OrganisationName = "merSETA Legal Directorate",
                Subtitle = "MoA legal contracting, addenda, dispute mediation & compliance governance",
                Category = "Enterprise Governance",
                Icon = "Policy",
                ThemeColor = "Warning",
                Roles = new() { "Legal" },
                IsWorkplaceApprovalPersona = false
            },
            new()
            {
                PersonaKey = "StatutoryAuditor",
                PersonaTitle = "Statutory Compliance Auditor (Zanele Sithole)",
                ShortLabel = "Zanele Sithole (Auditor)",
                CompactLabel = "Zanele S. (Audit)",
                PersonName = "Zanele Sithole",
                Email = "compliance@merseta.org.za",
                Initials = "ZS",
                StatutoryRole = "Statutory Compliance & SETMIS Auditor",
                RoleBadge = "COMPLIANCE AUDITOR",
                OrganisationName = "merSETA Internal Audit & Compliance",
                Subtitle = "SETMIS 11-file pre-flight checks, SAQA NLRD extracts & audit logs",
                Category = "Enterprise Governance",
                Icon = "Security",
                ThemeColor = "Primary",
                Roles = new() { "Compliance" },
                IsWorkplaceApprovalPersona = false
            },
            new()
            {
                PersonaKey = "StandardUser",
                PersonaTitle = "Standard Applicant / User",
                ShortLabel = "Standard User (Applicant)",
                CompactLabel = "User (Applicant)",
                PersonName = "Stakeholder Applicant",
                Email = "user.applicant@merseta.org.za",
                Initials = "UA",
                StatutoryRole = "Self-Registered Stakeholder Applicant",
                RoleBadge = "USER / APPLICANT",
                OrganisationName = "Unassigned Organisation",
                Subtitle = "Applies for statutory stakeholder roles (SDF, SDP Contact, Organisation Contact, Assessor, Moderator)",
                Category = "Stakeholder Registration",
                Icon = "Person",
                ThemeColor = "Secondary",
                Roles = new() { "User" },
                IsWorkplaceApprovalPersona = false
            }
        };
    }

    public PersonaProfileDto? GetPersonaProfile(string personaKeyOrTitle)
    {
        if (string.IsNullOrWhiteSpace(personaKeyOrTitle)) return null;
        var profiles = GetPersonaProfiles();
        return profiles.FirstOrDefault(p => 
            p.PersonaKey.Equals(personaKeyOrTitle, StringComparison.OrdinalIgnoreCase) ||
            p.PersonaTitle.Equals(personaKeyOrTitle, StringComparison.OrdinalIgnoreCase) ||
            p.ShortLabel.Equals(personaKeyOrTitle, StringComparison.OrdinalIgnoreCase) ||
            p.PersonName.Equals(personaKeyOrTitle, StringComparison.OrdinalIgnoreCase) ||
            p.PersonaTitle.Contains(personaKeyOrTitle, StringComparison.OrdinalIgnoreCase) ||
            personaKeyOrTitle.Contains(p.PersonName, StringComparison.OrdinalIgnoreCase));
    }

    public List<string> GetPredefinedPersonas()
    {
        return GetPersonaProfiles().Select(p => p.PersonaTitle).ToList();
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

        var pinnedSet = new HashSet<string>(preferences.PinnedItemIds, StringComparer.OrdinalIgnoreCase);

        // Filter items by role & permissions (Union RBAC) and project user-scoped instances
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
        }).Select(item =>
        {
            int badgeCount = 0;
            string? badgeColor = null;
            if (badgeCounts.TryGetValue(item.Id, out var count))
            {
                badgeCount = count;
                badgeColor = count > 5 ? "Error" : count > 0 ? "Warning" : "Primary";
            }

            return new NavItemDto
            {
                Id = item.Id,
                Title = item.Title,
                Href = item.Href,
                Icon = item.Icon,
                Category = item.Category,
                Description = item.Description,
                RequiredModule = item.RequiredModule,
                RequiredAction = item.RequiredAction,
                RequiredRoles = item.RequiredRoles,
                BadgeCount = badgeCount,
                BadgeColor = badgeColor,
                IsPinned = pinnedSet.Contains(item.Id),
                DisplayOrder = item.DisplayOrder,
                Keywords = item.Keywords,
                PersonaTags = item.PersonaTags,
                ExactMatch = item.ExactMatch
            };
        }).ToList();

        // Extract pinned items
        var pinnedItems = accessibleItems.Where(i => i.IsPinned).OrderBy(i => i.DisplayOrder).ToList();

        // Group into the 7 Statutory Domain Pillars
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
        
        return master
            .Where(i => pinnedSet.Contains(i.Id))
            .OrderBy(i => i.DisplayOrder)
            .Select(i => new NavItemDto
            {
                Id = i.Id,
                Title = i.Title,
                Href = i.Href,
                Icon = i.Icon,
                Category = i.Category,
                Description = i.Description,
                RequiredModule = i.RequiredModule,
                RequiredAction = i.RequiredAction,
                RequiredRoles = i.RequiredRoles,
                BadgeCount = i.BadgeCount,
                BadgeColor = i.BadgeColor,
                IsPinned = true,
                DisplayOrder = i.DisplayOrder,
                Keywords = i.Keywords,
                PersonaTags = i.PersonaTags,
                ExactMatch = i.ExactMatch
            })
            .ToList();
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

    public async Task<UserNavPreferencesDto> GetUserPreferencesAsync(string username)
    {
        var key = string.IsNullOrWhiteSpace(username) ? "DefaultUser" : username.Trim();
        if (_userPreferencesCache.TryGetValue(key, out var cached))
        {
            return cached;
        }

        // Attempt to load from database SystemConfig
        try
        {
            using var db = await _contextFactory.CreateDbContextAsync();
            var configKey = $"{key}:NavPreferences";
            var setting = await db.SystemConfigs
                .FirstOrDefaultAsync(s => s.ConfigCategory == "NavPreferences" && s.ConfigKey == configKey);

            if (setting != null && !string.IsNullOrWhiteSpace(setting.ConfigValue))
            {
                var dto = JsonSerializer.Deserialize<UserNavPreferencesDto>(setting.ConfigValue);
                if (dto != null)
                {
                    _userPreferencesCache[key] = dto;
                    return dto;
                }
            }
        }
        catch
        {
            // Fallback gracefully to default
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
        return defaults;
    }

    public async Task<bool> SaveUserPreferencesAsync(UserNavPreferencesDto preferences)
    {
        if (preferences == null || string.IsNullOrWhiteSpace(preferences.Username))
        {
            return false;
        }

        var key = preferences.Username.Trim();
        _userPreferencesCache[key] = preferences;

        try
        {
            using var db = await _contextFactory.CreateDbContextAsync();
            var configKey = $"{key}:NavPreferences";
            var setting = await db.SystemConfigs
                .FirstOrDefaultAsync(s => s.ConfigCategory == "NavPreferences" && s.ConfigKey == configKey);

            var json = JsonSerializer.Serialize(preferences);
            if (setting != null)
            {
                setting.ConfigValue = json;
                setting.ModifiedAt = DateTime.UtcNow;
                setting.ModifiedBy = key;
            }
            else
            {
                db.SystemConfigs.Add(new SystemConfig
                {
                    ConfigCategory = "NavPreferences",
                    ConfigKey = configKey,
                    ConfigValue = json,
                    DataType = "JSON",
                    Description = $"User navigation preferences and pinned shortcuts for {key}",
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = key
                });
            }

            await db.SaveChangesAsync();
        }
        catch
        {
            // In-memory fallback retains state for current runtime circuit
        }

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
            counts["nav-tasks"] = 0;
            counts["nav-wsp"] = 0;
            counts["nav-grants"] = 0;
            counts["nav-finance-grants"] = 0;
            counts["nav-monitoring"] = 0;
        }

        return counts;
    }

    private string NormalizePersonaKey(string persona)
    {
        if (persona.Contains("Mentor", StringComparison.OrdinalIgnoreCase) || persona.Contains("Artisan", StringComparison.OrdinalIgnoreCase)) return "Mentor";
        if (persona.Contains("SDF", StringComparison.OrdinalIgnoreCase)) return "SDF";
        if (persona.Contains("Provider", StringComparison.OrdinalIgnoreCase) || persona.Contains("SDP", StringComparison.OrdinalIgnoreCase)) return "SDP";
        if (persona.Contains("Assessor", StringComparison.OrdinalIgnoreCase) || persona.Contains("QA", StringComparison.OrdinalIgnoreCase)) return "Assessor";
        if (persona.Contains("Finance", StringComparison.OrdinalIgnoreCase)) return "Finance";
        if (persona.Contains("CLO", StringComparison.OrdinalIgnoreCase) || persona.Contains("Liaison", StringComparison.OrdinalIgnoreCase)) return "CLO";
        if (persona.Contains("Legal", StringComparison.OrdinalIgnoreCase)) return "Legal";
        if (persona.Contains("Compliance", StringComparison.OrdinalIgnoreCase) || persona.Contains("Auditor", StringComparison.OrdinalIgnoreCase)) return "Compliance";
        if (persona.Contains("Executive", StringComparison.OrdinalIgnoreCase) || persona.Contains("Committee", StringComparison.OrdinalIgnoreCase)) return "Executive";
        if (persona.Contains("Admin", StringComparison.OrdinalIgnoreCase) || persona.Contains("System", StringComparison.OrdinalIgnoreCase)) return "Admin";
        return "All";
    }

    private string GetGroupIcon(string category)
    {
        return category switch
        {
            "Overview & tasks" => "Dashboard",
            "Registries & stakeholders" => "FolderShared",
            "Grants, levies & finance" => "AccountBalanceWallet",
            "Learner & artisan development" => "School",
            "Quality assurance & ETQA" => "VerifiedUser",
            "Legal, compliance & BI" => "Gavel",
            "System administration" => "AdminPanelSettings",
            _ => "Folder"
        };
    }

    private int GetGroupDisplayOrder(string category)
    {
        return category switch
        {
            "Overview & tasks" => 1,
            "Registries & stakeholders" => 2,
            "Grants, levies & finance" => 3,
            "Learner & artisan development" => 4,
            "Quality assurance & ETQA" => 5,
            "Legal, compliance & BI" => 6,
            "System administration" => 7,
            _ => 99
        };
    }

    private List<OmnisearchResultItemDto> BuildQuickActions()
    {
        return new List<OmnisearchResultItemDto>
        {
            new() { Id = "act-new-org", Title = "Create Organisation / Employer", Href = "employers/create", Icon = "Business", Category = "Quick Actions", Description = "Register a new levy or non-levy paying organisation", ItemType = "QuickAction" },
            new() { Id = "act-new-wsp", Title = "Submit WSP / ATR Application", Href = "wsp/create", Icon = "Assignment", Category = "Quick Actions", Description = "Initiate a Workplace Skills Plan submission cycle", ItemType = "QuickAction" },
            new() { Id = "act-wsp-extension", Title = "Request WSP / ATR Deadline Extension", Href = "wsp/extension-request", Icon = "EventBusy", Category = "Quick Actions", Description = "Submit a statutory motivation for extending the 30 April WSP deadline", ItemType = "QuickAction" },
            new() { Id = "act-new-dg", Title = "Apply for Discretionary Grant (DG)", Href = "grants/create", Icon = "AccountBalanceWallet", Category = "Quick Actions", Description = "Submit a discretionary grant funding application window", ItemType = "QuickAction" },
            new() { Id = "act-new-learner", Title = "Register New Learner / Apprentice", Href = "learners/create", Icon = "School", Category = "Quick Actions", Description = "Enrol a learner with RSA ID validation and contract", ItemType = "QuickAction" },
            new() { Id = "act-learner-signoff", Title = "Learner OTP Sign-off Portal", Href = "signoff/learner", Icon = "Draw", Category = "Quick Actions", Description = "Electronic tripartite agreement execution via digital OTP token", ItemType = "QuickAction" },
            new() { Id = "act-new-tradetest", Title = "Apply for Artisan Trade Test (26D)", Href = "tradetests/create", Icon = "FactCheck", Category = "Quick Actions", Description = "Contracted apprentice summative assessment booking", ItemType = "QuickAction" },
            new() { Id = "act-new-arpl", Title = "Apply for Section 28 ARPL Evaluation", Href = "tradetests/create?type=arpl", Icon = "Handyman", Category = "Quick Actions", Description = "Artisan Recognition of Prior Learning portfolio submission", ItemType = "QuickAction" },
            new() { Id = "act-new-claim", Title = "Submit DG Tranche Claim & Voucher", Href = "finance/claims/create", Icon = "Payments", Category = "Quick Actions", Description = "Submit milestone deliverable invoice and generate payment voucher", ItemType = "QuickAction" },
            new() { Id = "act-new-wpa", Title = "Apply for Workplace Approval", Href = "workplace-approvals/create", Icon = "DomainAdd", Category = "Quick Actions", Description = "Register training workshop site and artisan mentor ratios", ItemType = "QuickAction" },
            new() { Id = "act-new-assessor-mod", Title = "Register as Assessor / Moderator", Href = "etqa/create", Icon = "VerifiedUser", Category = "Quick Actions", Description = "Submit new ETQA practitioner accreditation application", ItemType = "QuickAction" },
            new() { Id = "act-new-sdp", Title = "Apply for Training Provider (SDP) Accreditation", Href = "sdp/create", Icon = "AccountBalance", Category = "Quick Actions", Description = "Submit primary or secondary training provider accreditation", ItemType = "QuickAction" },
            new() { Id = "act-courseware", Title = "Request Training Courseware", Href = "curriculum/courseware", Icon = "MenuBook", Category = "Quick Actions", Description = "Request official MerSETA learning modules and curriculum materials", ItemType = "QuickAction" },
            new() { Id = "act-schedule-visit", Title = "Schedule Employer Monitoring Visit", Href = "monitoring", Icon = "FactCheck", Category = "Quick Actions", Description = "Plan an on-site workplace verification with designated contact person", ItemType = "QuickAction" },
            new() { Id = "act-sars-recon", Title = "Run SARS Levy Reconciliation Audit", Href = "finance/levy-audits", Icon = "Calculate", Category = "Quick Actions", Description = "Audit SARS levy monthly file against DHET distribution", ItemType = "QuickAction" },
            new() { Id = "act-inter-seta", Title = "Initiate Inter-SETA Transfer", Href = "inter-seta/transfer-request", Icon = "SwapHoriz", Category = "Quick Actions", Description = "Transfer organisation across SETAs due to business scope shift", ItemType = "QuickAction" },
            new() { Id = "act-banking-details", Title = "Submit / Verify Banking Details", Href = "finance/banking-details", Icon = "AccountBalance", Category = "Quick Actions", Description = "Upload verified bank confirmation letter for GP vendor sync", ItemType = "QuickAction" },
            new() { Id = "act-verify-doc", Title = "Verify Document Authenticity", Href = "verify", Icon = "VerifiedUser", Category = "Quick Actions", Description = "Verify cryptographic certificate or MoA digital security seal", ItemType = "QuickAction" },
            new() { Id = "act-field-dispatch", Title = "Dispatch Field Verification (CLC)", Href = "coordination/dispatch", Icon = "AltRoute", Category = "Quick Actions", Description = "Schedule and dispatch certified officer for workplace or monitoring audit", ItemType = "QuickAction" },
            new() { Id = "act-portfolio-handoff", Title = "Initiate Portfolio Handoff", Href = "coordination/portfolios", Icon = "FolderShared", Category = "Quick Actions", Description = "Reassign organisation portfolio accounts and open tasks to successor officer", ItemType = "QuickAction" }
        };
    }

    private static IReadOnlyList<NavItemDto> BuildMasterNavigationCatalog()
    {
        return _masterNavigationCatalog.Value;
    }

    private static List<NavItemDto> InitializeMasterNavigationCatalog()
    {
        return new List<NavItemDto>
        {
            // 1. Overview & tasks
            new()
            {
                Id = "nav-home-applicant",
                Title = "Applicant portal",
                Href = "",
                ExactMatch = true,
                Icon = "Home",
                Category = "Overview & tasks",
                Description = "Stakeholder registration hub and statutory application status",
                RequiredRoles = new() { "SuperAdmin", "Admin", "User" },
                PersonaTags = new() { "User", "All" },
                DisplayOrder = 0,
                Keywords = new() { "home", "portal", "applicant", "stakeholder", "apply" }
            },
            new()
            {
                Id = "nav-tasks",
                Title = "Task inbox",
                Href = "tasks",
                ExactMatch = true,
                Icon = "Inbox",
                Category = "Overview & tasks",
                Description = "Pending workflow approvals, sign-offs, and operational task queue",
                PersonaTags = new() { "All", "Admin", "SDF", "Finance", "Assessor", "SDP", "CLO", "Legal", "Compliance", "Executive" },
                DisplayOrder = 1,
                Keywords = new() { "tasks", "inbox", "approvals", "pending", "action", "workflow", "universal task inbox" }
            },
            new()
            {
                Id = "nav-exec-dashboard",
                Title = "Executive dashboard",
                Href = "dashboard",
                ExactMatch = true,
                Icon = "Dashboard",
                Category = "Overview & tasks",
                Description = "High-level KPI overview, statutory milestones, and SETA health metrics",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Executive", "CLO" },
                PersonaTags = new() { "Admin", "Executive", "CLO" },
                DisplayOrder = 2,
                Keywords = new() { "dashboard", "home", "executive", "kpi", "stats", "overview", "operations portal" }
            },
            new()
            {
                Id = "nav-workflow-studio",
                Title = "Workflow studio",
                Href = "admin/workflows",
                Icon = "AccountTree",
                Category = "Overview & tasks",
                Description = "Visual approval process designer, state transitions, and SLA configurations",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionManage,
                RequiredRoles = new() { "SuperAdmin", "Admin" },
                PersonaTags = new() { "Admin" },
                DisplayOrder = 3,
                Keywords = new() { "workflows", "approval process", "lifecycles", "states", "designer", "studio", "sla" }
            },
            new()
            {
                Id = "nav-coordinator-dispatch",
                Title = "Field dispatch hub (CLC)",
                Href = "coordination/dispatch",
                Icon = "AltRoute",
                Category = "Overview & tasks",
                Description = "Client Liaison Coordinator (CLC) field dispatch, officer workload balancing, and capability scheduling",
                RequiredModule = AppPermissions.ModuleOrganisations,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "CLO", "Executive" },
                PersonaTags = new() { "Admin", "CLO", "Executive", "All" },
                DisplayOrder = 4,
                Keywords = new() { "dispatch", "clc", "coordinator", "field visit", "scheduling", "workload", "capability", "officer" }
            },

            // 2. Registries & stakeholders
            new()
            {
                Id = "nav-employers",
                Title = "Employers & orgs",
                Href = "employers",
                Icon = "Business",
                Category = "Registries & stakeholders",
                Description = "Levy and non-levy organisations, chambers, SDL numbers, and contact persons",
                RequiredModule = AppPermissions.ModuleOrganisations,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "SDF", "CLO", "Finance" },
                PersonaTags = new() { "Admin", "SDF", "CLO", "Finance" },
                DisplayOrder = 1,
                Keywords = new() { "employers", "organisations", "companies", "sdl", "chamber", "levy" }
            },
            new()
            {
                Id = "nav-portfolios",
                Title = "Officer portfolios & handoffs",
                Href = "coordination/portfolios",
                Icon = "FolderShared",
                Category = "Registries & stakeholders",
                Description = "Cross-regional employer account assignments, temporal territory demarcation, and successor handoff audit trail",
                RequiredModule = AppPermissions.ModuleOrganisations,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "CLO", "Executive" },
                PersonaTags = new() { "Admin", "CLO", "Executive", "All" },
                DisplayOrder = 2,
                Keywords = new() { "portfolios", "handoff", "relationship officer", "successor", "territory", "demarcation", "cross-regional" }
            },
            new()
            {
                Id = "nav-zones",
                Title = "Sub-regional zones",
                Href = "coordination/zones",
                Icon = "Map",
                Category = "Registries & stakeholders",
                Description = "Sub-regional territory zones, town cluster assignments, and default primary relationship officer governance",
                RequiredModule = AppPermissions.ModuleOrganisations,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "CLO", "Executive" },
                PersonaTags = new() { "Admin", "CLO", "Executive", "All" },
                DisplayOrder = 3,
                Keywords = new() { "zones", "sub-regional", "towns", "demarcation", "clusters", "territory", "clo" }
            },
            new()
            {
                Id = "nav-sdf",
                Title = "SDF appointments",
                Href = "employers/sdf",
                Icon = "Badge",
                Category = "Registries & stakeholders",
                Description = "Skills Development Facilitator registrations, appointment letters, and linkages",
                RequiredModule = AppPermissions.ModuleOrganisations,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "SDF", "CLO" },
                PersonaTags = new() { "Admin", "SDF", "CLO" },
                DisplayOrder = 2,
                Keywords = new() { "sdf", "facilitator", "appointments", "nominations", "skills development" }
            },
            new()
            {
                Id = "nav-sdp",
                Title = "SDP providers",
                Href = "sdp",
                Icon = "School",
                Category = "Registries & stakeholders",
                Description = "Accredited training providers, delivery sites, qualifications scope, and audits",
                RequiredModule = AppPermissions.ModuleOrganisations,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "SDP", "CLO", "Assessor" },
                PersonaTags = new() { "Admin", "SDP", "CLO", "Assessor" },
                DisplayOrder = 3,
                Keywords = new() { "sdp", "providers", "training", "colleges", "institutions", "accreditation" }
            },
            new()
            {
                Id = "nav-sdp-wizard",
                Title = "Apply for SDP accreditation wizard",
                Href = "providers/apply-accreditation",
                Icon = "AccountBalance",
                Category = "Registries & stakeholders",
                Description = "Multi-step Skills Development Provider primary accreditation and scope registration wizard",
                RequiredModule = AppPermissions.ModuleOrganisations,
                RequiredAction = AppPermissions.ActionEdit,
                RequiredRoles = new() { "SuperAdmin", "Admin", "SDP", "CLO" },
                PersonaTags = new() { "Admin", "SDP", "CLO" },
                DisplayOrder = 4,
                Keywords = new() { "sdp wizard", "accreditation wizard", "apply sdp", "training provider accreditation" }
            },
            new()
            {
                Id = "nav-people",
                Title = "People & demographics",
                Href = "people",
                Icon = "People",
                Category = "Registries & stakeholders",
                Description = "Master registry of citizens, learners, assessors, and demographic profiles",
                RequiredModule = AppPermissions.ModulePeople,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "CLO", "SDF" },
                PersonaTags = new() { "Admin", "CLO", "SDF" },
                DisplayOrder = 5,
                Keywords = new() { "people", "citizens", "demographics", "rsa id", "contacts", "setmis" }
            },
            new()
            {
                Id = "nav-curriculum",
                Title = "Curriculum (QCD)",
                Href = "curriculum",
                Icon = "MenuBook",
                Category = "Registries & stakeholders",
                Description = "QCTO curriculum scoping, occupational qualification blueprints, and modules",
                RequiredModule = AppPermissions.ModuleEtqa,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Assessor", "SDP" },
                PersonaTags = new() { "Admin", "Assessor", "SDP" },
                DisplayOrder = 5,
                Keywords = new() { "qcd", "curriculum", "qcto", "qualifications", "scoping", "modules" }
            },
            new()
            {
                Id = "nav-user-apply-sdf",
                Title = "Apply to be an SDF",
                Href = "employers/sdf/create",
                Icon = "Work",
                Category = "Registries & stakeholders",
                Description = "Appoint or register as a Skills Development Facilitator for an employer",
                RequiredRoles = new() { "SuperAdmin", "Admin", "User", "SDF" },
                PersonaTags = new() { "User", "SDF", "Admin" },
                DisplayOrder = 6,
                Keywords = new() { "apply sdf", "register sdf", "facilitator appointment" }
            },
            new()
            {
                Id = "nav-user-apply-sdp",
                Title = "Apply to be SDP contact",
                Href = "providers/apply-accreditation",
                Icon = "School",
                Category = "Registries & stakeholders",
                Description = "Register as a Skills Development Provider contact or apply for SDP accreditation",
                RequiredRoles = new() { "SuperAdmin", "Admin", "User", "SDP" },
                PersonaTags = new() { "User", "SDP", "Admin" },
                DisplayOrder = 7,
                Keywords = new() { "apply sdp", "training provider", "sdp contact", "accreditation" }
            },
            new()
            {
                Id = "nav-user-apply-contact",
                Title = "Apply to be organisation contact",
                Href = "employers/sdf/create?type=contact",
                Icon = "Business",
                Category = "Registries & stakeholders",
                Description = "Register as an official contact person linked to an employer organisation",
                RequiredRoles = new() { "SuperAdmin", "Admin", "User" },
                PersonaTags = new() { "User", "Admin" },
                DisplayOrder = 8,
                Keywords = new() { "organisation contact", "employer contact", "register contact" }
            },

            // 3. Grants, levies & finance
            new()
            {
                Id = "nav-wsp",
                Title = "WSP & mandatory grants",
                Href = "wsp",
                Icon = "Assignment",
                Category = "Grants, levies & finance",
                Description = "Workplace Skills Plans, Annual Training Reports, and Mandatory Grant (MG) claims",
                RequiredModule = AppPermissions.ModuleWsp,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "SDF", "CLO", "Finance" },
                PersonaTags = new() { "Admin", "SDF", "CLO", "Finance" },
                DisplayOrder = 1,
                Keywords = new() { "wsp", "atr", "mandatory grant", "skills plan", "pivotal", "mg", "workplace skills plans" }
            },
            new()
            {
                Id = "nav-wsp-wizard",
                Title = "Submit WSP/ATR wizard",
                Href = "wsp/submit",
                Icon = "AssignmentTurnedIn",
                Category = "Grants, levies & finance",
                Description = "Multi-step Workplace Skills Plan, Annual Training Report, and Mandatory Grant submission wizard",
                RequiredModule = AppPermissions.ModuleWsp,
                RequiredAction = AppPermissions.ActionEdit,
                RequiredRoles = new() { "SuperAdmin", "Admin", "SDF", "CLO" },
                PersonaTags = new() { "Admin", "SDF", "CLO" },
                DisplayOrder = 2,
                Keywords = new() { "wsp wizard", "atr wizard", "submit wsp", "annual submission", "mandatory grant wizard" }
            },
            new()
            {
                Id = "nav-wsp-committees",
                Title = "Training committees",
                Href = "wsp/committees",
                Icon = "Groups",
                Category = "Grants, levies & finance",
                Description = "Consultative training committees, meeting minutes, and labour sign-offs",
                RequiredModule = AppPermissions.ModuleWsp,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "SDF", "CLO" },
                PersonaTags = new() { "Admin", "SDF", "CLO" },
                DisplayOrder = 3,
                Keywords = new() { "committees", "training committee", "labour", "union", "consultation" }
            },
            new()
            {
                Id = "nav-wsp-extension",
                Title = "Deadline extensions",
                Href = "wsp/extension-request",
                Icon = "EventBusy",
                Category = "Grants, levies & finance",
                Description = "Statutory 30 April WSP/ATR deadline extension requests and motivations",
                RequiredModule = AppPermissions.ModuleWsp,
                RequiredAction = AppPermissions.ActionEdit,
                RequiredRoles = new() { "SuperAdmin", "Admin", "SDF", "CLO" },
                PersonaTags = new() { "Admin", "SDF", "CLO" },
                DisplayOrder = 4,
                Keywords = new() { "extension", "deadline extension", "wsp extension", "late filing", "postponement" }
            },
            new()
            {
                Id = "nav-grants",
                Title = "Discretionary grants",
                Href = "grants",
                Icon = "AccountBalanceWallet",
                Category = "Grants, levies & finance",
                Description = "Discretionary grant applications, scoring matrix, and project approvals",
                RequiredModule = AppPermissions.ModuleGrants,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "SDF", "Finance", "CLO" },
                PersonaTags = new() { "Admin", "SDF", "Finance", "CLO" },
                DisplayOrder = 3,
                Keywords = new() { "dg", "discretionary grants", "funding", "allocations", "applications" }
            },
            new()
            {
                Id = "nav-grants-windows",
                Title = "Funding windows",
                Href = "grants/windows",
                Icon = "EventNote",
                Category = "Grants, levies & finance",
                Description = "Gazetted Discretionary Grant funding allocation cycles, opening and closing deadlines",
                RequiredModule = AppPermissions.ModuleGrants,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Finance", "CLO" },
                PersonaTags = new() { "Admin", "Finance", "CLO" },
                DisplayOrder = 4,
                Keywords = new() { "windows", "funding windows", "gazette", "opening", "deadlines", "budget allocation", "dg funding windows" }
            },
            new()
            {
                Id = "nav-grants-pip",
                Title = "Project implementation",
                Href = "grants/pip",
                Icon = "AssignmentTurnedIn",
                Category = "Grants, levies & finance",
                Description = "PIP milestones, learner enrollment targets, and tranche claim verification",
                RequiredModule = AppPermissions.ModuleGrants,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "SDF", "Finance", "CLO" },
                PersonaTags = new() { "Admin", "SDF", "Finance", "CLO" },
                DisplayOrder = 5,
                Keywords = new() { "pip", "project implementation", "milestones", "tranches", "targets", "dg pip" }
            },
            new()
            {
                Id = "nav-contracts-variations",
                Title = "Contract variations",
                Href = "contracts/variations",
                Icon = "Difference",
                Category = "Grants, levies & finance",
                Description = "Contract variation requests, time extensions, budget re-allocations, and legal addenda",
                RequiredModule = AppPermissions.ModuleGrants,
                RequiredAction = AppPermissions.ActionEdit,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Finance", "Legal" },
                PersonaTags = new() { "Admin", "Finance", "Legal" },
                DisplayOrder = 6,
                Keywords = new() { "variations", "addenda", "contract amendments", "extensions", "legal" }
            },
            new()
            {
                Id = "nav-finance-grants",
                Title = "DG MOAs & tranches",
                Href = "finance/grants",
                Icon = "Description",
                Category = "Grants, levies & finance",
                Description = "Memorandums of Agreement, tranche schedules, GP ERP posting batches",
                RequiredModule = AppPermissions.ModuleFinance,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Finance" },
                PersonaTags = new() { "Admin", "Finance" },
                DisplayOrder = 7,
                Keywords = new() { "moa", "tranches", "disbursements", "erp", "gp", "payments", "dg" }
            },
            new()
            {
                Id = "nav-dg-claims",
                Title = "DG claims & tranche invoicing",
                Href = "finance/dg-claims",
                Icon = "ReceiptLong",
                Category = "Grants, levies & finance",
                Description = "Discretionary Grant tranche claims, multi-tier financial approval, and ERP payment batches",
                RequiredModule = AppPermissions.ModuleFinance,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Finance", "CLO", "Executive" },
                PersonaTags = new() { "Admin", "Finance", "CLO", "Executive" },
                DisplayOrder = 8,
                Keywords = new() { "claims", "invoices", "tranches", "financial approval", "cfo", "vouchers", "erp", "sage", "gp" }
            },
            new()
            {
                Id = "nav-dg-claim-wizard",
                Title = "Submit tranche claim wizard",
                Href = "finance/claims/create",
                Icon = "Payments",
                Category = "Grants, levies & finance",
                Description = "Multi-step Discretionary Grant tranche milestone claim and serialized payment voucher wizard",
                RequiredModule = AppPermissions.ModuleFinance,
                RequiredAction = AppPermissions.ActionCreate,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Finance", "SDF" },
                PersonaTags = new() { "All", "Admin", "Finance", "SDF" },
                DisplayOrder = 9,
                Keywords = new() { "claim wizard", "tranche claim", "payment voucher", "financial approval", "invoice" }
            },
            new()
            {
                Id = "nav-erp-outbox",
                Title = "Dynamics GP integration outbox",
                Href = "finance/erp-outbox",
                Icon = "SendAndArchive",
                Category = "Grants, levies & finance",
                Description = "Resilient ERP outbox queue with automated pause on GP downtime and FIFO resumption",
                RequiredModule = AppPermissions.ModuleFinance,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Finance" },
                PersonaTags = new() { "Admin", "Finance" },
                DisplayOrder = 10,
                Keywords = new() { "outbox", "gp", "dynamics", "queue", "erp", "webservices", "offline", "resume" }
            },
            new()
            {
                Id = "nav-finance-banking",
                Title = "Banking details",
                Href = "finance/banking-details",
                Icon = "AccountBalance",
                Category = "Grants, levies & finance",
                Description = "Bank account verification (AVS), dual-control sign-off, and audit trail",
                RequiredModule = AppPermissions.ModuleFinance,
                RequiredAction = AppPermissions.ActionVerify,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Finance" },
                PersonaTags = new() { "Admin", "Finance" },
                DisplayOrder = 8,
                Keywords = new() { "banking", "avs", "bankserv", "account verification", "signoff" }
            },
            new()
            {
                Id = "nav-finance-rebates",
                Title = "Mandatory rebates",
                Href = "finance/levy-rebates",
                Icon = "Payments",
                Category = "Grants, levies & finance",
                Description = "20% Mandatory grant payout calculations, remittance advice, and EFT batches",
                RequiredModule = AppPermissions.ModuleFinance,
                RequiredAction = AppPermissions.ActionDisburse,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Finance" },
                PersonaTags = new() { "Admin", "Finance" },
                DisplayOrder = 9,
                Keywords = new() { "rebates", "mandatory rebate", "remittance", "20%", "payouts", "mg" }
            },
            new()
            {
                Id = "nav-finance-audits",
                Title = "SARS levy audits",
                Href = "finance/levy-audits",
                Icon = "Calculate",
                Category = "Grants, levies & finance",
                Description = "SARS levy reconciliation audits, clawback letters, and interest calculations",
                RequiredModule = AppPermissions.ModuleFinance,
                RequiredAction = AppPermissions.ActionReconcile,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Finance" },
                PersonaTags = new() { "Admin", "Finance" },
                DisplayOrder = 10,
                Keywords = new() { "clawbacks", "sars audits", "reconciliation", "adjustments" }
            },
            new()
            {
                Id = "nav-levies",
                Title = "SARS levy files",
                Href = "levies",
                Icon = "ReceiptLong",
                Category = "Grants, levies & finance",
                Description = "Monthly SARS electronic levy files, chamber distribution, and employer reconciliations",
                RequiredModule = AppPermissions.ModuleFinance,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Finance" },
                PersonaTags = new() { "Admin", "Finance" },
                DisplayOrder = 11,
                Keywords = new() { "levies", "sars files", "levy downloads", "monthly levies" }
            },
            new()
            {
                Id = "nav-levy-deviations",
                Title = "Levy deviations",
                Href = "levies/deviations",
                Icon = "QueryStats",
                Category = "Grants, levies & finance",
                Description = "12-month rolling standard deviation anomalies and chamber revenue intelligence",
                RequiredModule = AppPermissions.ModuleFinance,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Finance" },
                PersonaTags = new() { "Admin", "Finance" },
                DisplayOrder = 12,
                Keywords = new() { "deviations", "chambers", "anomalies", "standard deviation", "inconsistent" }
            },
            new()
            {
                Id = "nav-levy-schemes",
                Title = "Scheme year rates",
                Href = "levies/scheme-years",
                Icon = "Tune",
                Category = "Grants, levies & finance",
                Description = "Statutory percentage splits and return processing controls by scheme year",
                RequiredModule = AppPermissions.ModuleFinance,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Finance" },
                PersonaTags = new() { "Admin", "Finance" },
                DisplayOrder = 13,
                Keywords = new() { "scheme year", "statutory rates", "mandatory %", "discretionary %", "admin %" }
            },
            new()
            {
                Id = "nav-inter-seta",
                Title = "Inter-SETA transfers",
                Href = "inter-seta-transfers",
                Icon = "SwapHoriz",
                Category = "Grants, levies & finance",
                Description = "Transfers between SETAs for SIC code moves, levy adjustments, and approvals",
                RequiredModule = AppPermissions.ModuleFinance,
                RequiredAction = AppPermissions.ActionTransfer,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Finance" },
                PersonaTags = new() { "Admin", "Finance" },
                DisplayOrder = 14,
                Keywords = new() { "inter-seta", "transfers", "sic codes", "seta transfer" }
            },
            new()
            {
                Id = "nav-interseta-wizard",
                Title = "Section 32 transfer wizard",
                Href = "inter-seta/transfer-request",
                Icon = "CompareArrows",
                Category = "Grants, levies & finance",
                Description = "Multi-step Section 32 employer chamber reclassification and accumulated levy funds migration wizard",
                RequiredModule = AppPermissions.ModuleFinance,
                RequiredAction = AppPermissions.ActionTransfer,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Finance" },
                PersonaTags = new() { "Admin", "Finance" },
                DisplayOrder = 15,
                Keywords = new() { "inter-seta wizard", "section 32 wizard", "transfer request", "sic reclassification" }
            },

            // 4. Learner & artisan development
            new()
            {
                Id = "nav-learners",
                Title = "Learner agreements",
                Href = "learners",
                Icon = "School",
                Category = "Learner & artisan development",
                Description = "Learner registrations, agreements, NLRD submissions, and progress tracking",
                RequiredModule = AppPermissions.ModuleLearners,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "SDF", "SDP", "CLO", "Mentor" },
                PersonaTags = new() { "Admin", "SDF", "SDP", "CLO", "Mentor" },
                DisplayOrder = 1,
                Keywords = new() { "learners", "students", "agreements", "nlrd", "contracts", "artisan" }
            },
            new()
            {
                Id = "nav-learner-signoff",
                Title = "OTP digital sign-off",
                Href = "signoff/learner",
                Icon = "Draw",
                Category = "Learner & artisan development",
                Description = "Paperless digital execution of tripartite learnership agreements via SMS/Email OTP",
                RequiredModule = AppPermissions.ModuleLearners,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "SDF", "SDP", "CLO" },
                PersonaTags = new() { "All", "Admin", "SDF", "SDP", "CLO" },
                DisplayOrder = 2,
                Keywords = new() { "otp", "sign-off", "digital signature", "paperless", "tripartite", "token" }
            },
            new()
            {
                Id = "nav-tradetests",
                Title = "Trade tests & ARPL",
                Href = "tradetests",
                Icon = "FactCheck",
                Category = "Learner & artisan development",
                Description = "Artisan Recognition of Prior Learning (ARPL), trade test centers, and certifications",
                RequiredModule = AppPermissions.ModuleLearners,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Assessor", "SDP", "CLO" },
                PersonaTags = new() { "Admin", "Assessor", "SDP", "CLO" },
                DisplayOrder = 2,
                Keywords = new() { "trade tests", "arpl", "artisans", "red seal", "certification" }
            },
            new()
            {
                Id = "nav-tradetest-wizard",
                Title = "Trade test application wizard",
                Href = "trade-tests/apply",
                Icon = "Construction",
                Category = "Learner & artisan development",
                Description = "Multi-step artisan trade test and ARPL Section 28 assessment booking wizard",
                RequiredModule = AppPermissions.ModuleLearners,
                RequiredAction = AppPermissions.ActionCreate,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Assessor", "SDP", "SDF" },
                PersonaTags = new() { "All", "Admin", "Assessor", "SDP", "SDF" },
                DisplayOrder = 3,
                Keywords = new() { "trade test wizard", "arpl wizard", "apply trade test", "red seal", "exam" }
            },
            new()
            {
                Id = "nav-tradetest-cla-queue",
                Title = "CLA trade test review queue",
                Href = "tradetests/cla-queue",
                Icon = "AssignmentTurnedIn",
                Category = "Learner & artisan development",
                Description = "Regional Client Liaison Officer review queue for recommending or resubmitting artisan trade test applications",
                RequiredModule = AppPermissions.ModuleLearners,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "CLO" },
                PersonaTags = new() { "All", "Admin", "CLO" },
                DisplayOrder = 4,
                Keywords = new() { "cla review", "trade test recommendations", "artisan intake" }
            },
            new()
            {
                Id = "nav-tradetest-qa-queue",
                Title = "QA trade test approval queue",
                Href = "tradetests/qa-queue",
                Icon = "Verified",
                Category = "Learner & artisan development",
                Description = "Regional Quality Assurance adjudication queue for stamping signed application forms and serial allocation",
                RequiredModule = AppPermissions.ModuleLearners,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Assessor" },
                PersonaTags = new() { "All", "Admin", "Assessor" },
                DisplayOrder = 5,
                Keywords = new() { "qa approval", "trade test serials", "artisan stamping", "red seal" }
            },
            new()
            {
                Id = "nav-namb-queue",
                Title = "NAMB staging & serials",
                Href = "artisans/namb-queue",
                Icon = "WorkspacePremium",
                Category = "Learner & artisan development",
                Description = "National Artisan Moderation Body batch staging, candidate moderation, and serial issuance",
                RequiredModule = AppPermissions.ModuleLearners,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Assessor", "CLO" },
                PersonaTags = new() { "Admin", "Assessor", "CLO" },
                DisplayOrder = 3,
                Keywords = new() { "namb", "artisan serials", "moderation batch", "red seal" }
            },
            new()
            {
                Id = "nav-assessments",
                Title = "Summative assessments",
                Href = "assessments/summative",
                Icon = "AssignmentTurnedIn",
                Category = "Learner & artisan development",
                Description = "Summative assessment reporting, Statement of Results (SOR), and moderation",
                RequiredModule = AppPermissions.ModuleLearners,
                RequiredAction = AppPermissions.ActionModerate,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Assessor", "SDP" },
                PersonaTags = new() { "Admin", "Assessor", "SDP" },
                DisplayOrder = 3,
                Keywords = new() { "assessments", "sor", "statement of results", "moderation", "grades" }
            },
            new()
            {
                Id = "nav-assessment-capture",
                Title = "Capture assessments",
                Href = "assessments/capture",
                Icon = "AddChart",
                Category = "Learner & artisan development",
                Description = "Capture summative assessment results, evaluate credits, and commit to holding room",
                RequiredModule = AppPermissions.ModuleLearners,
                RequiredAction = AppPermissions.ActionCreate,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Assessor", "SDP" },
                PersonaTags = new() { "Admin", "Assessor", "SDP" },
                DisplayOrder = 4,
                Keywords = new() { "capture assessment", "intake", "etq-fm-005", "results capture" }
            },
            new()
            {
                Id = "nav-assessment-batching",
                Title = "Assessment holding & batching",
                Href = "assessments/batching",
                Icon = "Layers",
                Category = "Learner & artisan development",
                Description = "Holding room inventory, sample size selection, and external moderation batching",
                RequiredModule = AppPermissions.ModuleLearners,
                RequiredAction = AppPermissions.ActionModerate,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Assessor", "SDP" },
                PersonaTags = new() { "Admin", "Assessor", "SDP" },
                DisplayOrder = 5,
                Keywords = new() { "holding room", "batching", "moderation batch", "sample size" }
            },
            new()
            {
                Id = "nav-qa-workbench",
                Title = "QA moderation workbench",
                Href = "assessments/qa-workbench",
                Icon = "FactCheck",
                Category = "Quality assurance & ETQA",
                Description = "External QA site visit scheduling, stratified audit sample, and ETQ-TP-043 validation",
                RequiredModule = AppPermissions.ModuleEtqa,
                RequiredAction = AppPermissions.ActionModerate,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Assessor", "CLO", "Compliance" },
                PersonaTags = new() { "Admin", "Assessor", "CLO", "Compliance" },
                DisplayOrder = 6,
                Keywords = new() { "qa workbench", "external moderation", "etq-tp-043", "vacs", "remedial" }
            },
            new()
            {
                Id = "nav-cert-printing",
                Title = "Certificate printing hub",
                Href = "assessments/printing-hub",
                Icon = "Print",
                Category = "Quality assurance & ETQA",
                Description = "Batch qualification certificate printing, transmittal letters, and distribution tracking",
                RequiredModule = AppPermissions.ModuleEtqa,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Assessor", "CLO" },
                PersonaTags = new() { "Admin", "Assessor", "CLO" },
                DisplayOrder = 7,
                Keywords = new() { "certificates", "printing hub", "distribution letter", "etq-lt-012" }
            },
            new()
            {
                Id = "nav-wpa",
                Title = "Workplace approvals",
                Href = "workplace-approvals",
                Icon = "Handyman",
                Category = "Learner & artisan development",
                Description = "Workplace approval applications, safety checks, and mentor certifications",
                RequiredModule = AppPermissions.ModuleWorkplace,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "SDF", "CLO", "Mentor", "Assessor" },
                PersonaTags = new() { "Admin", "SDF", "CLO", "Mentor", "Assessor" },
                DisplayOrder = 4,
                Keywords = new() { "wpa", "workplace approval", "mentor", "safety", "workplace" }
            },
            new()
            {
                Id = "nav-trade-mentor-ratios",
                Title = "Trade mentor ratios",
                Href = "reference-data/trade-mentor-ratios",
                Icon = "Engineering",
                Category = "Learner & artisan development",
                Description = "Statutory artisan mentor-to-apprentice ratios and multi-tiered exemption policy matrix",
                RequiredModule = AppPermissions.ModuleWorkplace,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "CLO", "Mentor" },
                PersonaTags = new() { "Admin", "CLO", "Mentor" },
                DisplayOrder = 5,
                Keywords = new() { "ratio", "mentor ratio", "artisan ratio", "capacity", "trade policy", "exemption" }
            },

            // 5. Quality assurance & ETQA
            new()
            {
                Id = "nav-monitoring",
                Title = "Workplace monitoring",
                Href = "monitoring",
                Icon = "FactCheck",
                Category = "Quality assurance & ETQA",
                Description = "On-site employer visits, monitoring reports, and remediation findings",
                RequiredModule = AppPermissions.ModuleWorkplace,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "CLO", "Assessor", "Compliance" },
                PersonaTags = new() { "Admin", "CLO", "Assessor", "Compliance" },
                DisplayOrder = 1,
                Keywords = new() { "monitoring", "site visits", "audits", "remediation", "workplace" }
            },
            new()
            {
                Id = "nav-etqa",
                Title = "ETQA assessors",
                Href = "etqa",
                Icon = "VerifiedUser",
                Category = "Quality assurance & ETQA",
                Description = "Assessor and moderator registrations, provider accreditation certificates",
                RequiredModule = AppPermissions.ModuleEtqa,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Assessor", "CLO", "Compliance" },
                PersonaTags = new() { "Admin", "Assessor", "CLO", "Compliance" },
                DisplayOrder = 2,
                Keywords = new() { "etqa", "assessors", "moderators", "accreditation", "qa" }
            },
            new()
            {
                Id = "nav-etqa-applications",
                Title = "Assessor applications",
                Href = "etqa/applications",
                Icon = "Assignment",
                Category = "Quality assurance & ETQA",
                Description = "Assessor and moderator initial registration applications and 4-stage maker-checker review queue",
                RequiredModule = AppPermissions.ModuleEtqa,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Assessor", "CLO", "Compliance" },
                PersonaTags = new() { "Admin", "Assessor", "CLO", "Compliance" },
                DisplayOrder = 3,
                Keywords = new() { "applications", "etqa queue", "assessor review", "evaluations", "committee" }
            },
            new()
            {
                Id = "nav-etqa-register",
                Title = "Register practitioner",
                Href = "etqa/register",
                Icon = "PersonAdd",
                Category = "Quality assurance & ETQA",
                Description = "Apply for initial assessor or moderator statutory registration",
                RequiredModule = AppPermissions.ModuleEtqa,
                RequiredAction = AppPermissions.ActionCreate,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Assessor", "SDP", "CLO", "User" },
                PersonaTags = new() { "Admin", "Assessor", "SDP", "User" },
                DisplayOrder = 4,
                Keywords = new() { "register assessor", "new moderator", "etqa application" }
            },
            new()
            {
                Id = "nav-user-apply-assessor",
                Title = "Apply to be an assessor",
                Href = "etqa/register?type=Assessor",
                Icon = "FactCheck",
                Category = "Quality assurance & ETQA",
                Description = "Apply for initial statutory registration as an accredited constituent assessor",
                RequiredRoles = new() { "SuperAdmin", "Admin", "User", "Assessor" },
                PersonaTags = new() { "User", "Assessor", "Admin" },
                DisplayOrder = 4,
                Keywords = new() { "apply assessor", "register assessor", "etqa application" }
            },
            new()
            {
                Id = "nav-user-apply-moderator",
                Title = "Apply to be a moderator",
                Href = "etqa/register?type=Moderator",
                Icon = "VerifiedUser",
                Category = "Quality assurance & ETQA",
                Description = "Apply for statutory registration as an accredited constituent moderator",
                RequiredRoles = new() { "SuperAdmin", "Admin", "User", "Assessor" },
                PersonaTags = new() { "User", "Assessor", "Admin" },
                DisplayOrder = 5,
                Keywords = new() { "apply moderator", "register moderator", "etqa moderation" }
            },
            new()
            {
                Id = "nav-etqa-aqp",
                Title = "AQP partners",
                Href = "etqa/aqp",
                Icon = "FactCheck",
                Category = "Quality assurance & ETQA",
                Description = "AQP partner agreements, external integrated summative assessment (EISA) oversight",
                RequiredModule = AppPermissions.ModuleEtqa,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Assessor", "SDP" },
                PersonaTags = new() { "Admin", "Assessor", "SDP" },
                DisplayOrder = 3,
                Keywords = new() { "aqp", "eisa", "quality partners", "qcto assessment" }
            },
            new()
            {
                Id = "nav-etqa-scope",
                Title = "Scope extensions",
                Href = "etqa/scope-extensions",
                Icon = "FactCheck",
                Category = "Quality assurance & ETQA",
                Description = "Provider program extension requests, unit standard additions, and evaluations",
                RequiredModule = AppPermissions.ModuleEtqa,
                RequiredAction = AppPermissions.ActionEdit,
                RequiredRoles = new() { "SuperAdmin", "Admin", "SDP", "Assessor" },
                PersonaTags = new() { "Admin", "SDP", "Assessor" },
                DisplayOrder = 4,
                Keywords = new() { "scope extension", "accreditation extension", "unit standards" }
            },
            new()
            {
                Id = "nav-non-seta",
                Title = "Non-SETA articulations",
                Href = "non-seta/verifications",
                Icon = "DomainVerification",
                Category = "Quality assurance & ETQA",
                Description = "Cross-SETA qualification verifications, external artisan endorsements",
                RequiredModule = AppPermissions.ModuleEtqa,
                RequiredAction = AppPermissions.ActionVerify,
                RequiredRoles = new() { "SuperAdmin", "Admin", "SDP", "CLO" },
                PersonaTags = new() { "Admin", "SDP", "CLO" },
                DisplayOrder = 5,
                Keywords = new() { "non-seta", "articulations", "cross-seta", "verifications" }
            },
            new()
            {
                Id = "nav-courseware",
                Title = "Courseware distribution",
                Href = "curriculum/courseware",
                Icon = "MenuBook",
                Category = "Quality assurance & ETQA",
                Description = "Request and download official MerSETA-developed curriculum modules and learning material",
                RequiredModule = AppPermissions.ModuleEtqa,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "SDP", "Assessor" },
                PersonaTags = new() { "Admin", "SDP", "Assessor" },
                DisplayOrder = 6,
                Keywords = new() { "courseware", "learning materials", "modules", "curriculum", "books", "guides" }
            },

            // 6. Legal, compliance & BI
            new()
            {
                Id = "nav-moa-templates",
                Title = "MoA templates",
                Href = "legal/moa-templates",
                Icon = "Gavel",
                Category = "Legal, compliance & BI",
                Description = "Draft and publish dynamic legal MoA templates with parameterized placeholders",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionManage,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Legal", "Finance" },
                PersonaTags = new() { "Admin", "Legal", "Finance" },
                DisplayOrder = 1,
                Keywords = new() { "moa templates", "legal templates", "contracts", "studio" }
            },
            new()
            {
                Id = "nav-doc-templates",
                Title = "Document templates",
                Href = "admin/document-templates",
                Icon = "Description",
                Category = "Legal, compliance & BI",
                Description = "Certificate templates, rejection notices, and statutory correspondence blueprints",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionManage,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Legal" },
                PersonaTags = new() { "Admin", "Legal" },
                DisplayOrder = 2,
                Keywords = new() { "document templates", "letters", "notices", "certificates" }
            },
            new()
            {
                Id = "nav-moa-clauses",
                Title = "Clause library",
                Href = "legal/moa-clauses",
                Icon = "LibraryBooks",
                Category = "Legal, compliance & BI",
                Description = "Standardized statutory clauses, dispute resolution rules, and breach terms",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Legal" },
                PersonaTags = new() { "Admin", "Legal" },
                DisplayOrder = 3,
                Keywords = new() { "clauses", "legal clauses", "terms", "conditions", "dispute" }
            },
            new()
            {
                Id = "nav-doc-snapshots",
                Title = "Document snapshots",
                Href = "admin/document-snapshots",
                Icon = "Security",
                Category = "Legal, compliance & BI",
                Description = "Cryptographic document hashing, tamper-proofing logs, and digital signatures",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionVerify,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Legal", "Compliance" },
                PersonaTags = new() { "Admin", "Legal", "Compliance" },
                DisplayOrder = 4,
                Keywords = new() { "snapshots", "security", "hashes", "digital signatures", "tamper-proof", "document security" }
            },
            new()
            {
                Id = "nav-verify",
                Title = "Digital verification",
                Href = "verify",
                Icon = "VerifiedUser",
                Category = "Legal, compliance & BI",
                Description = "Public and internal verification of issued certificates, letters, and MoAs",
                PersonaTags = new() { "All", "Admin", "SDF", "Finance", "Assessor", "SDP", "CLO", "Legal", "Compliance" },
                DisplayOrder = 5,
                Keywords = new() { "verification", "verify portal", "qr code", "authenticity" }
            },
            new()
            {
                Id = "nav-bi-reports",
                Title = "Executive skills BI",
                Href = "reports/bi",
                Icon = "Insights",
                Category = "Legal, compliance & BI",
                Description = "Sector Skills Plan (SSP) analytics, scarce skills heatmaps, and DHET reports",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Executive", "CLO" },
                PersonaTags = new() { "Admin", "Executive", "CLO" },
                DisplayOrder = 6,
                Keywords = new() { "bi", "analytics", "ssp", "sector skills plan", "scarce skills", "reports" }
            },
            new()
            {
                Id = "nav-admin-statutory",
                Title = "Statutory submissions",
                Href = "compliance/statutory",
                Icon = "FactCheck",
                Category = "Legal, compliance & BI",
                Description = "Quarterly DHET SETMIS submissions, NLRD batch uploads, and AGSA audit pack",
                RequiredModule = AppPermissions.ModuleCompliance,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Compliance" },
                PersonaTags = new() { "Admin", "Compliance" },
                DisplayOrder = 7,
                Keywords = new() { "statutory", "setmis submissions", "nlrd", "agsa", "audits", "dhet" }
            },

            // 7. System administration
            new()
            {
                Id = "nav-admin",
                Title = "Administration hub",
                Href = "admin",
                ExactMatch = true,
                Icon = "AdminPanelSettings",
                Category = "System administration",
                Description = "Central control hub, system telemetry, and governance dashboard",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin" },
                PersonaTags = new() { "Admin" },
                DisplayOrder = 1,
                Keywords = new() { "admin", "control hub", "telemetry", "system", "administration and control hub" }
            },
            new()
            {
                Id = "nav-admin-settings",
                Title = "System settings",
                Href = "admin/settings",
                Icon = "Settings",
                Category = "System administration",
                Description = "System configuration parameters, external integration feature toggles",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionManage,
                RequiredRoles = new() { "SuperAdmin", "Admin" },
                PersonaTags = new() { "Admin" },
                DisplayOrder = 2,
                Keywords = new() { "settings", "configuration", "feature flags", "parameters", "integrations" }
            },
            new()
            {
                Id = "nav-admin-financial-years",
                Title = "Financial years & quarters",
                Href = "admin/financial-years",
                Icon = "CalendarMonth",
                Category = "System administration",
                Description = "Statutory financial scheme years, custom quarter date boundaries, and working day projections",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionManage,
                RequiredRoles = new() { "SuperAdmin", "Admin" },
                PersonaTags = new() { "Admin", "Finance" },
                DisplayOrder = 3,
                Keywords = new() { "financial year", "fiscal", "quarters", "working days", "calendar", "fin year" }
            },
            new()
            {
                Id = "nav-admin-non-working-days",
                Title = "Holidays & institutional closures",
                Href = "admin/non-working-days",
                Icon = "EventBusy",
                Category = "System administration",
                Description = "National statutory holidays, merSETA year-end shutdowns, and ad-hoc institutional closure periods affecting workflow SLAs",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionManage,
                RequiredRoles = new() { "SuperAdmin", "Admin" },
                PersonaTags = new() { "Admin", "Finance", "CLO", "Compliance" },
                DisplayOrder = 4,
                Keywords = new() { "holidays", "institutional closures", "shutdown", "sla", "non-working days", "public holidays", "annual shutdown" }
            },
            new()
            {
                Id = "nav-admin-roles",
                Title = "Roles & permissions",
                Href = "admin/roles",
                Icon = "Security",
                Category = "System administration",
                Description = "CASL claims authorization, role definitions, and user permission overrides",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionManage,
                RequiredRoles = new() { "SuperAdmin", "Admin" },
                PersonaTags = new() { "Admin" },
                DisplayOrder = 3,
                Keywords = new() { "roles", "permissions", "casl", "security", "users", "rbac" }
            },
            new()
            {
                Id = "nav-admin-users",
                Title = "Identity & user accounts",
                Href = "admin/users",
                Icon = "ManageAccounts",
                Category = "System administration",
                Description = "ASP.NET Core Identity user accounts, demographic linking, credential security, and lockout controls",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionManage,
                RequiredRoles = new() { "SuperAdmin", "Admin" },
                PersonaTags = new() { "Admin" },
                DisplayOrder = 4,
                Keywords = new() { "users", "accounts", "identity", "passwords", "lockout", "logins" }
            },
            new()
            {
                Id = "nav-admin-lookups",
                Title = "Lookup tables",
                Href = "admin/lookups",
                Icon = "Tune",
                Category = "System administration",
                Description = "SETMIS lookup tables, statutory enumerations, and reference data management",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionManage,
                RequiredRoles = new() { "SuperAdmin", "Admin" },
                PersonaTags = new() { "Admin" },
                DisplayOrder = 4,
                Keywords = new() { "lookups", "enums", "reference data", "setmis codes", "tables" }
            },
            new()
            {
                Id = "nav-admin-rules",
                Title = "Business rules",
                Href = "system-admin/rules",
                Icon = "AccountTree",
                Category = "System administration",
                Description = "Dynamic business rule engine, statutory gatekeeper workflows, and interactive test sandbox",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionManage,
                RequiredRoles = new() { "SuperAdmin", "Admin" },
                PersonaTags = new() { "Admin" },
                DisplayOrder = 5,
                Keywords = new() { "rules", "business rules", "rule engine", "stp", "gatekeepers", "financial approval", "sandbox", "validation" }
            },
            new()
            {
                Id = "nav-admin-meetings",
                Title = "Committee meetings",
                Href = "governance/meetings",
                Icon = "MeetingRoom",
                Category = "System administration",
                Description = "Board, MANCO, and Grant Adjudication meeting resolutions and delegations",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Executive" },
                PersonaTags = new() { "Admin", "Executive" },
                DisplayOrder = 5,
                Keywords = new() { "meetings", "manco", "board", "governance", "resolutions", "delegations" }
            },
            new()
            {
                Id = "nav-admin-audit",
                Title = "Audited change log",
                Href = "audit-logs",
                Icon = "History",
                Category = "System administration",
                Description = "System-wide immutable audited change log, before/after JSON diffs",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin" },
                PersonaTags = new() { "Admin" },
                DisplayOrder = 6,
                Keywords = new() { "audit", "logs", "forensics", "history", "audited change log", "trail" }
            },
            new()
            {
                Id = "nav-admin-schema",
                Title = "Database schema",
                Href = "developer/schema",
                Icon = "MenuBook",
                Category = "System administration",
                Description = "Interactive database schema documentation and historical version timeline",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Developer" },
                PersonaTags = new() { "Admin", "Developer" },
                DisplayOrder = 7,
                Keywords = new() { "data dictionary", "schema", "tables", "database", "history", "versioning" }
            },
            new()
            {
                Id = "nav-admin-compliance",
                Title = "UI compliance HUD",
                Href = "developer/compliance-audit",
                Icon = "Verified",
                Category = "System administration",
                Description = "Automated compliance auditor for UI-STANDARD.md, WCAG 2.2 AA, and heuristics",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Developer" },
                PersonaTags = new() { "Admin", "Developer" },
                DisplayOrder = 8,
                Keywords = new() { "compliance hud", "ui standard", "wcag", "heuristics", "audit" }
            },
            new()
            {
                Id = "nav-wizard-matrix",
                Title = "Wizard candidate matrix",
                Href = "wizards",
                Icon = "AccountTree",
                Category = "System administration",
                Description = "Enterprise multi-step wizard registry, candidate matrix, and compliance hub (DESIGN.md §10)",
                RequiredModule = AppPermissions.ModuleSystem,
                RequiredAction = AppPermissions.ActionView,
                RequiredRoles = new() { "SuperAdmin", "Admin", "Executive", "Developer" },
                PersonaTags = new() { "All", "Admin", "Executive", "Developer", "SDF", "CLO" },
                DisplayOrder = 9,
                Keywords = new() { "wizard matrix", "multi-step", "wizards", "candidate matrix", "wizard shell", "stepper" }
            }
        };
    }
}
