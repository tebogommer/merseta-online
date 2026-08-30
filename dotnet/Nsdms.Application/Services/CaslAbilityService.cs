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

        return new CaslUserContext
        {
            UserId = user.Id,
            Username = user.UserName ?? user.Email ?? "Unknown",
            Roles = roles,
            DefaultOrganisationId = user.DefaultOrganisationId,
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
                if (context.DefaultOrganisationId.Value != targetOrganisationId.Value)
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
        if (!context.DefaultOrganisationId.HasValue) return true;
        return context.DefaultOrganisationId.Value == targetOrganisationId;
    }
}
