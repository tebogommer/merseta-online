using Microsoft.EntityFrameworkCore;
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
}

public class CaslAbilityService : ICaslAbilityService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IRolePermissionService _rolePermissionService;

    public CaslAbilityService(INsdmsDbContextFactory contextFactory, IRolePermissionService rolePermissionService)
    {
        _contextFactory = contextFactory;
        _rolePermissionService = rolePermissionService;
    }

    public async Task<CaslUserContext> GetUserContextAsync(int userId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var user = await db.Users.FindAsync(userId);
        if (user == null)
        {
            return new CaslUserContext { UserId = userId };
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

        return new CaslUserContext
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
    }

    public async Task<CaslUserContext> GetUserContextByUsernameAsync(string username)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var normalized = username.Trim().ToUpperInvariant();
        var user = await db.Users.FirstOrDefaultAsync(u => u.NormalizedUserName == normalized || u.NormalizedEmail == normalized);
        if (user == null)
        {
            // Default fallback for development/demo admin
            if (username.Equals("Admin", StringComparison.OrdinalIgnoreCase) || username.Equals("SYSTEM", StringComparison.OrdinalIgnoreCase))
            {
                return new CaslUserContext
                {
                    UserId = 1,
                    Username = username,
                    Roles = new() { "SuperAdmin" },
                    IsAdmin = true,
                    Permissions = new(AppPermissions.GetAllPermissions().Select(p => p.ClaimValue), StringComparer.OrdinalIgnoreCase)
                };
            }

            return new CaslUserContext { Username = username };
        }

        return await GetUserContextAsync(user.Id);
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
