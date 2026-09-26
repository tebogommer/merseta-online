using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Caching.Memory;
using Nsdms.Application.Common;
using Nsdms.Domain.Security;

namespace Nsdms.Application.Services;

public class CaslUserContext
{
    public int UserId { get; set; }
    public string Username { get; set; } = string.Empty;
    public List<string> Roles { get; set; } = new();
    public int? DefaultOrganisationId { get; set; }
    public List<int> AssociatedOrganisationIds { get; set; } = new();
    public List<int> AssociatedTrainingProviderIds { get; set; } = new();
    public bool IsAdmin { get; set; } = false;
    public HashSet<string> Permissions { get; set; } = new(StringComparer.OrdinalIgnoreCase);
}

public interface ICaslAbilityService
{
    Task<CaslUserContext> GetUserContextAsync(int userId);
    Task<CaslUserContext> GetUserContextByUsernameAsync(string username);
    bool Can(CaslUserContext context, string action, string subject, int? targetOrganisationId = null);
    bool CanViewOrManage(CaslUserContext context, string subject, int? targetOrganisationId = null);
    bool IsOrganisationAccessible(CaslUserContext context, int targetOrganisationId);
    bool CanSubmitLearnerAgreement(CaslUserContext context, int? targetOrganisationId = null, int? targetTrainingProviderId = null);
    void InvalidateUserContext(string username);
    void InvalidateUserContext(int userId);
}

public class CaslAbilityService : ICaslAbilityService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IRolePermissionService _rolePermissionService;
    private readonly IMemoryCache? _cache;
    private static readonly TimeSpan SlidingCacheExpiration = TimeSpan.FromMinutes(10);
    private static readonly TimeSpan AbsoluteCacheExpiration = TimeSpan.FromHours(1);

    public CaslAbilityService(
        INsdmsDbContextFactory contextFactory,
        IRolePermissionService rolePermissionService,
        IMemoryCache? cache = null)
    {
        _contextFactory = contextFactory;
        _rolePermissionService = rolePermissionService;
        _cache = cache;
    }

    public async Task<CaslUserContext> GetUserContextAsync(int userId)
    {
        var cacheKey = $"casl_user_ctx_id_{userId}";
        if (_cache != null && _cache.TryGetValue(cacheKey, out CaslUserContext? cached) && cached != null)
        {
            return cached;
        }

        using var db = await _contextFactory.CreateDbContextAsync();
        var user = await db.Users.FindAsync(userId);
        if (user == null)
        {
            var notFoundCtx = new CaslUserContext { UserId = userId };
            SetCache(cacheKey, notFoundCtx);
            return notFoundCtx;
        }

        var roleIds = await db.UserRoles
            .Where(ur => ur.UserId == userId)
            .Select(ur => ur.RoleId)
            .ToListAsync();

        var roles = await db.Roles
            .Where(r => roleIds.Contains(r.Id) && r.Active)
            .Select(r => r.Name ?? "")
            .ToListAsync();

        var perms = await _rolePermissionService.GetUserPermissionsAsync(userId);
        var isAdmin = roles.Any(r => r.Equals("SuperAdmin", StringComparison.OrdinalIgnoreCase) ||
                                     r.Equals("Admin", StringComparison.OrdinalIgnoreCase));

        var associatedOrgIds = new List<int>();
        var associatedSdpIds = new List<int>();

        if (user.DefaultOrganisationId.HasValue && user.DefaultOrganisationId.Value > 0)
        {
            associatedOrgIds.Add(user.DefaultOrganisationId.Value);
        }

        if (user.PersonId.HasValue && user.PersonId.Value > 0)
        {
            var orgContacts = await db.OrganisationContacts
                .Where(oc => oc.PersonId == user.PersonId.Value && oc.IsActive)
                .Select(oc => oc.OrganisationId)
                .ToListAsync();
            associatedOrgIds.AddRange(orgContacts);

            var sdpContacts = await db.TrainingProviderContacts
                .Where(tc => tc.PersonId == user.PersonId.Value && tc.IsActive)
                .Select(tc => tc.TrainingProviderId)
                .ToListAsync();
            associatedSdpIds.AddRange(sdpContacts);
        }

        if (!string.IsNullOrWhiteSpace(user.Email))
        {
            var sdpByEmail = await db.TrainingProviderContacts
                .Where(tc => tc.Email == user.Email && tc.IsActive)
                .Select(tc => tc.TrainingProviderId)
                .ToListAsync();
            associatedSdpIds.AddRange(sdpByEmail);
        }

        var context = new CaslUserContext
        {
            UserId = user.Id,
            Username = user.UserName ?? user.Email ?? "Unknown",
            Roles = roles,
            DefaultOrganisationId = user.DefaultOrganisationId,
            AssociatedOrganisationIds = associatedOrgIds.Distinct().ToList(),
            AssociatedTrainingProviderIds = associatedSdpIds.Distinct().ToList(),
            IsAdmin = isAdmin,
            Permissions = new HashSet<string>(perms, StringComparer.OrdinalIgnoreCase)
        };

        SetCache(cacheKey, context);
        if (!string.IsNullOrWhiteSpace(context.Username))
        {
            SetCache($"casl_user_ctx_name_{context.Username.Trim().ToUpperInvariant()}", context);
        }

        return context;
    }

    public async Task<CaslUserContext> GetUserContextByUsernameAsync(string username)
    {
        if (string.IsNullOrWhiteSpace(username))
        {
            return new CaslUserContext();
        }

        var normalized = username.Trim().ToUpperInvariant();
        var cacheKey = $"casl_user_ctx_name_{normalized}";

        if (_cache != null && _cache.TryGetValue(cacheKey, out CaslUserContext? cached) && cached != null)
        {
            return cached;
        }

        // Default fallback for development/demo admin
        if (normalized == "ADMIN" || normalized == "SYSTEM")
        {
            var adminCtx = new CaslUserContext
            {
                UserId = 1,
                Username = username,
                Roles = new() { "SuperAdmin" },
                IsAdmin = true,
                Permissions = new(AppPermissions.GetAllPermissions().Select(p => p.ClaimValue), StringComparer.OrdinalIgnoreCase)
            };
            SetCache(cacheKey, adminCtx);
            return adminCtx;
        }

        using var db = await _contextFactory.CreateDbContextAsync();
        var user = await db.Users.FirstOrDefaultAsync(u => u.NormalizedUserName == normalized || u.NormalizedEmail == normalized);
        if (user == null)
        {
            var notFoundCtx = new CaslUserContext { Username = username };
            SetCache(cacheKey, notFoundCtx);
            return notFoundCtx;
        }

        var context = await GetUserContextAsync(user.Id);
        SetCache(cacheKey, context);
        return context;
    }

    private void SetCache(string key, CaslUserContext context)
    {
        if (_cache == null) return;
        var options = new MemoryCacheEntryOptions
        {
            SlidingExpiration = SlidingCacheExpiration,
            AbsoluteExpirationRelativeToNow = AbsoluteCacheExpiration,
            Size = 1
        };
        _cache.Set(key, context, options);
    }

    public void InvalidateUserContext(string username)
    {
        if (string.IsNullOrWhiteSpace(username) || _cache == null) return;
        var normalized = username.Trim().ToUpperInvariant();
        var nameKey = $"casl_user_ctx_name_{normalized}";
        if (_cache.TryGetValue(nameKey, out CaslUserContext? ctx) && ctx != null && ctx.UserId > 0)
        {
            _cache.Remove($"casl_user_ctx_id_{ctx.UserId}");
        }
        _cache.Remove(nameKey);
    }

    public void InvalidateUserContext(int userId)
    {
        if (_cache == null) return;
        var idKey = $"casl_user_ctx_id_{userId}";
        if (_cache.TryGetValue(idKey, out CaslUserContext? ctx) && ctx != null && !string.IsNullOrWhiteSpace(ctx.Username))
        {
            _cache.Remove($"casl_user_ctx_name_{ctx.Username.Trim().ToUpperInvariant()}");
        }
        _cache.Remove(idKey);
    }

    public bool Can(CaslUserContext context, string action, string subject, int? targetOrganisationId = null)
    {
        if (context.IsAdmin)
        {
            return true;
        }

        // Organisation / Tenant scoping enforcement
        if (targetOrganisationId.HasValue && targetOrganisationId.Value > 0)
        {
            if (context.DefaultOrganisationId.HasValue && context.DefaultOrganisationId.Value > 0)
            {
                if (context.DefaultOrganisationId.Value != targetOrganisationId.Value &&
                    !context.AssociatedOrganisationIds.Contains(targetOrganisationId.Value))
                {
                    return false;
                }
            }
        }

        // CASL Rule: Direct permission match or Subject:Manage wildcard
        var directKey = $"{subject}:{action}";
        var manageKey = $"{subject}:Manage";

        return context.Permissions.Contains(directKey) || context.Permissions.Contains(manageKey);
    }

    public bool CanViewOrManage(CaslUserContext context, string subject, int? targetOrganisationId = null)
    {
        return Can(context, AppPermissions.ActionView, subject, targetOrganisationId) ||
               Can(context, AppPermissions.ActionManage, subject, targetOrganisationId);
    }

    public bool IsOrganisationAccessible(CaslUserContext context, int targetOrganisationId)
    {
        if (context.IsAdmin) return true;
        if (!context.DefaultOrganisationId.HasValue && context.AssociatedOrganisationIds.Count == 0) return true;
        return (context.DefaultOrganisationId.HasValue && context.DefaultOrganisationId.Value == targetOrganisationId) ||
               context.AssociatedOrganisationIds.Contains(targetOrganisationId);
    }

    public bool CanSubmitLearnerAgreement(CaslUserContext context, int? targetOrganisationId = null, int? targetTrainingProviderId = null)
    {
        if (context.IsAdmin) return true;

        bool hasSubmitPermission = context.Permissions.Contains("Learners:Submit") ||
                                   context.Permissions.Contains("Learners:Create") ||
                                   context.Permissions.Contains("Learners:Manage");

        if (!hasSubmitPermission) return false;

        // If no specific employer or provider is targeted, basic permission check passed
        if ((!targetOrganisationId.HasValue || targetOrganisationId.Value <= 0) &&
            (!targetTrainingProviderId.HasValue || targetTrainingProviderId.Value <= 0))
        {
            return true;
        }

        // Target was specified: user must be associated with the Employer OR the Training Provider
        bool isEmployerContact = targetOrganisationId.HasValue && targetOrganisationId.Value > 0 &&
            ((context.DefaultOrganisationId.HasValue && context.DefaultOrganisationId.Value == targetOrganisationId.Value) ||
             context.AssociatedOrganisationIds.Contains(targetOrganisationId.Value));

        bool isSdpContact = targetTrainingProviderId.HasValue && targetTrainingProviderId.Value > 0 &&
            context.AssociatedTrainingProviderIds.Contains(targetTrainingProviderId.Value);

        return isEmployerContact || isSdpContact;
    }
}
