using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;
using Nsdms.Domain.Security;

namespace Nsdms.Application.Services;

public class RoleSummaryDto
{
    public int Id { get; set; }
    public string Name { get; set; } = string.Empty;
    public string? Description { get; set; }
    public bool Active { get; set; } = true;
    public int UserCount { get; set; }
    public int PermissionCount { get; set; }
}

public class RoleDetailDto
{
    public int Id { get; set; }
    public string Name { get; set; } = string.Empty;
    public string? Description { get; set; }
    public bool Active { get; set; } = true;
    public List<string> PermissionClaims { get; set; } = new();
    public List<string> Users { get; set; } = new();
}

public class UserPermissionOverrideDto
{
    public int UserId { get; set; }
    public string PermissionClaim { get; set; } = string.Empty;
    public bool IsGranted { get; set; }
    public string? Reason { get; set; }
}

public interface IRolePermissionService
{
    Task<List<RoleSummaryDto>> GetAllRolesAsync();
    Task<RoleDetailDto?> GetRoleByIdAsync(int id);
    Task<ApplicationRole> CreateRoleAsync(string roleName, string? description, List<string> permissionClaims, string currentUsername = "SYSTEM");
    Task<bool> UpdateRoleAsync(int id, string roleName, string? description, bool active, List<string> permissionClaims, string currentUsername = "SYSTEM");
    Task<bool> DeleteRoleAsync(int id, string currentUsername = "SYSTEM");
    Task<List<string>> GetRolePermissionsAsync(int roleId);
    Task<List<string>> GetUserPermissionsAsync(int userId);
    Task<List<UserPermissionOverrideDto>> GetUserPermissionOverridesAsync(int userId);
    Task<bool> SetUserPermissionOverrideAsync(int userId, string permissionClaim, bool isGranted, string? reason = null, string currentUsername = "SYSTEM");
    Task<bool> RemoveUserPermissionOverrideAsync(int userId, string permissionClaim, string currentUsername = "SYSTEM");
    Task<bool> UserHasPermissionAsync(int userId, string module, string action);
    Task SeedDefaultRolePermissionsAsync();
}

public class RolePermissionService : IRolePermissionService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;

    public const string PermissionClaimType = "Permission";

    public RolePermissionService(INsdmsDbContextFactory contextFactory, IAuditService audit)
    {
        _contextFactory = contextFactory;
        _audit = audit;
    }

    public async Task<List<RoleSummaryDto>> GetAllRolesAsync()
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var roles = await db.Roles.OrderBy(r => r.Name).ToListAsync();
        var userRoles = await db.UserRoles.ToListAsync();
        var roleClaims = await db.RoleClaims.Where(rc => rc.ClaimType == PermissionClaimType).ToListAsync();

        var list = new List<RoleSummaryDto>();
        foreach (var r in roles)
        {
            list.Add(new RoleSummaryDto
            {
                Id = r.Id,
                Name = r.Name ?? string.Empty,
                Description = r.Description,
                Active = r.Active,
                UserCount = userRoles.Count(ur => ur.RoleId == r.Id),
                PermissionCount = roleClaims.Count(rc => rc.RoleId == r.Id)
            });
        }

        return list;
    }

    public async Task<RoleDetailDto?> GetRoleByIdAsync(int id)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var role = await db.Roles.FindAsync(id);
        if (role == null) return null;

        var permissions = await db.RoleClaims
            .Where(rc => rc.RoleId == id && rc.ClaimType == PermissionClaimType && rc.ClaimValue != null)
            .Select(rc => rc.ClaimValue!)
            .ToListAsync();

        var userIds = await db.UserRoles
            .Where(ur => ur.RoleId == id)
            .Select(ur => ur.UserId)
            .ToListAsync();

        var usernames = await db.Users
            .Where(u => userIds.Contains(u.Id))
            .Select(u => u.UserName ?? u.Email ?? $"User #{u.Id}")
            .ToListAsync();

        return new RoleDetailDto
        {
            Id = role.Id,
            Name = role.Name ?? string.Empty,
            Description = role.Description,
            Active = role.Active,
            PermissionClaims = permissions,
            Users = usernames
        };
    }

    public async Task<ApplicationRole> CreateRoleAsync(string roleName, string? description, List<string> permissionClaims, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var normalized = roleName.Trim().ToUpperInvariant();
        var existing = await db.Roles.FirstOrDefaultAsync(r => r.NormalizedName == normalized);
        if (existing != null)
        {
            throw new InvalidOperationException($"Role '{roleName}' already exists.");
        }

        var role = new ApplicationRole(roleName.Trim())
        {
            NormalizedName = normalized,
            Description = description,
            Active = true
        };

        db.Roles.Add(role);
        await db.SaveChangesAsync();

        // Assign permission claims
        foreach (var claim in permissionClaims.Distinct())
        {
            db.RoleClaims.Add(new IdentityRoleClaim<int>
            {
                RoleId = role.Id,
                ClaimType = PermissionClaimType,
                ClaimValue = claim
            });
        }
        await db.SaveChangesAsync();

        _audit.LogAction(db, "ApplicationRole", role.Id, "CreateRole", currentUsername, null, new { role.Id, role.Name, Permissions = permissionClaims });
        await db.SaveChangesAsync();

        return role;
    }

    public async Task<bool> UpdateRoleAsync(int id, string roleName, string? description, bool active, List<string> permissionClaims, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var role = await db.Roles.FindAsync(id);
        if (role == null) return false;

        var beforeState = new { role.Id, role.Name, role.Description, role.Active };
        role.Name = roleName.Trim();
        role.NormalizedName = roleName.Trim().ToUpperInvariant();
        role.Description = description;
        role.Active = active;

        // Synchronize permission claims
        var existingClaims = await db.RoleClaims.Where(rc => rc.RoleId == id && rc.ClaimType == PermissionClaimType).ToListAsync();
        db.RoleClaims.RemoveRange(existingClaims);

        foreach (var claim in permissionClaims.Distinct())
        {
            db.RoleClaims.Add(new IdentityRoleClaim<int>
            {
                RoleId = id,
                ClaimType = PermissionClaimType,
                ClaimValue = claim
            });
        }

        _audit.LogAction(db, "ApplicationRole", id, "UpdateRole", currentUsername, beforeState, new { id, role.Name, role.Description, role.Active, Permissions = permissionClaims });
        await db.SaveChangesAsync();

        return true;
    }

    public async Task<bool> DeleteRoleAsync(int id, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var role = await db.Roles.FindAsync(id);
        if (role == null) return false;

        // Prevent deletion of fundamental SuperAdmin role
        if (role.NormalizedName == "SUPERADMIN" || role.NormalizedName == "ADMIN")
        {
            throw new InvalidOperationException("Core system administrative roles cannot be deleted.");
        }

        var beforeState = new { role.Id, role.Name };

        var userRoles = await db.UserRoles.Where(ur => ur.RoleId == id).ToListAsync();
        if (userRoles.Any()) db.UserRoles.RemoveRange(userRoles);

        var claims = await db.RoleClaims.Where(rc => rc.RoleId == id).ToListAsync();
        if (claims.Any()) db.RoleClaims.RemoveRange(claims);

        db.Roles.Remove(role);
        _audit.LogAction(db, "ApplicationRole", id, "DeleteRole", currentUsername, beforeState, null);
        await db.SaveChangesAsync();

        return true;
    }

    public async Task<List<string>> GetRolePermissionsAsync(int roleId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.RoleClaims
            .Where(rc => rc.RoleId == roleId && rc.ClaimType == PermissionClaimType && rc.ClaimValue != null)
            .Select(rc => rc.ClaimValue!)
            .ToListAsync();
    }

    public const string RevokedPermissionClaimType = "RevokedPermission";

    public async Task<List<string>> GetUserPermissionsAsync(int userId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var user = await db.Users.FindAsync(userId);
        if (user == null) return new List<string>();

        var roleIds = await db.UserRoles
            .Where(ur => ur.UserId == userId)
            .Select(ur => ur.RoleId)
            .ToListAsync();

        var roles = await db.Roles
            .Where(r => roleIds.Contains(r.Id) && r.Active)
            .Select(r => r.NormalizedName)
            .ToListAsync();

        // Base permissions from active assigned roles
        HashSet<string> effectivePermissions;
        if (roles.Contains("SUPERADMIN") || roles.Contains("ADMIN"))
        {
            effectivePermissions = new HashSet<string>(AppPermissions.GetAllPermissions().Select(p => p.ClaimValue), StringComparer.OrdinalIgnoreCase);
        }
        else
        {
            var roleClaims = await db.RoleClaims
                .Where(rc => roleIds.Contains(rc.RoleId) && rc.ClaimType == PermissionClaimType && rc.ClaimValue != null)
                .Select(rc => rc.ClaimValue!)
                .ToListAsync();

            effectivePermissions = new HashSet<string>(roleClaims, StringComparer.OrdinalIgnoreCase);
        }

        // Apply User-Specific Overrides (UserClaims)
        var userClaims = await db.UserClaims
            .Where(uc => uc.UserId == userId && uc.ClaimValue != null)
            .ToListAsync();

        // 1. Explicitly Granted User Overrides
        foreach (var grant in userClaims.Where(uc => uc.ClaimType == PermissionClaimType))
        {
            effectivePermissions.Add(grant.ClaimValue!);
        }

        // 2. Explicitly Revoked User Overrides
        foreach (var revocation in userClaims.Where(uc => uc.ClaimType == RevokedPermissionClaimType))
        {
            effectivePermissions.Remove(revocation.ClaimValue!);
        }

        return effectivePermissions.ToList();
    }

    public async Task<List<UserPermissionOverrideDto>> GetUserPermissionOverridesAsync(int userId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var userClaims = await db.UserClaims
            .Where(uc => uc.UserId == userId && (uc.ClaimType == PermissionClaimType || uc.ClaimType == RevokedPermissionClaimType) && uc.ClaimValue != null)
            .ToListAsync();

        return userClaims.Select(uc => new UserPermissionOverrideDto
        {
            UserId = userId,
            PermissionClaim = uc.ClaimValue!,
            IsGranted = uc.ClaimType == PermissionClaimType
        }).ToList();
    }

    public async Task<bool> SetUserPermissionOverrideAsync(int userId, string permissionClaim, bool isGranted, string? reason = null, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var user = await db.Users.FindAsync(userId);
        if (user == null) return false;

        // Remove any existing override for this permission claim
        var existing = await db.UserClaims
            .Where(uc => uc.UserId == userId && (uc.ClaimType == PermissionClaimType || uc.ClaimType == RevokedPermissionClaimType) && uc.ClaimValue == permissionClaim)
            .ToListAsync();

        if (existing.Any())
        {
            db.UserClaims.RemoveRange(existing);
        }

        var targetClaimType = isGranted ? PermissionClaimType : RevokedPermissionClaimType;
        db.UserClaims.Add(new IdentityUserClaim<int>
        {
            UserId = userId,
            ClaimType = targetClaimType,
            ClaimValue = permissionClaim
        });

        _audit.LogAction(db, "UserPermissionOverride", userId, "SetUserPermissionOverride", currentUsername, null, new { userId, permissionClaim, isGranted, reason });
        await db.SaveChangesAsync();
        return true;
    }

    public async Task<bool> RemoveUserPermissionOverrideAsync(int userId, string permissionClaim, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.UserClaims
            .Where(uc => uc.UserId == userId && (uc.ClaimType == PermissionClaimType || uc.ClaimType == RevokedPermissionClaimType) && uc.ClaimValue == permissionClaim)
            .ToListAsync();

        if (!existing.Any()) return false;

        db.UserClaims.RemoveRange(existing);
        _audit.LogAction(db, "UserPermissionOverride", userId, "RemoveUserPermissionOverride", currentUsername, null, new { userId, permissionClaim });
        await db.SaveChangesAsync();
        return true;
    }

    public async Task<bool> UserHasPermissionAsync(int userId, string module, string action)
    {
        var permissions = await GetUserPermissionsAsync(userId);
        var directClaim = AppPermissions.Create(module, action);
        var manageClaim = AppPermissions.Create(module, AppPermissions.ActionManage);

        return permissions.Contains(directClaim) || permissions.Contains(manageClaim);
    }

    public async Task SeedDefaultRolePermissionsAsync()
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        // 1. SuperAdmin (All permissions)
        await EnsureRoleWithPermissionsAsync(db, "SuperAdmin", "Enterprise Super Administrator with unrestricted access across all subsystems.",
            AppPermissions.GetAllPermissions().Select(p => p.ClaimValue).ToList());

        // 2. Admin (Full operational & management permissions)
        await EnsureRoleWithPermissionsAsync(db, "Admin", "System Administrator with full operational management capabilities.",
            AppPermissions.GetAllPermissions().Select(p => p.ClaimValue).ToList());

        // 3. FinanceManager (Finance, Levies, MOA Disbursal)
        await EnsureRoleWithPermissionsAsync(db, "FinanceManager", "Senior Finance and Grant Disbursements Governance Specialist.",
            new List<string>
            {
                AppPermissions.Create(AppPermissions.ModuleFinance, AppPermissions.ActionView),
                AppPermissions.Create(AppPermissions.ModuleFinance, AppPermissions.ActionCreate),
                AppPermissions.Create(AppPermissions.ModuleFinance, AppPermissions.ActionEdit),
                AppPermissions.Create(AppPermissions.ModuleFinance, AppPermissions.ActionApprove),
                AppPermissions.Create(AppPermissions.ModuleFinance, AppPermissions.ActionDisburse),
                AppPermissions.Create(AppPermissions.ModuleFinance, AppPermissions.ActionManage),
                AppPermissions.Create(AppPermissions.ModuleGrants, AppPermissions.ActionView),
                AppPermissions.Create(AppPermissions.ModuleGrants, AppPermissions.ActionDisburse),
                AppPermissions.Create(AppPermissions.ModuleWsp, AppPermissions.ActionView),
                AppPermissions.Create(AppPermissions.ModuleOrganisations, AppPermissions.ActionView),
                AppPermissions.Create(AppPermissions.ModuleCompliance, AppPermissions.ActionView)
            });

        // 4. CLO (Client Liaison Officer)
        await EnsureRoleWithPermissionsAsync(db, "CLO", "Client Liaison Officer conducting workplace monitoring, site audits & desktop reviews.",
            new List<string>
            {
                AppPermissions.Create(AppPermissions.ModuleWorkplace, AppPermissions.ActionView),
                AppPermissions.Create(AppPermissions.ModuleWorkplace, AppPermissions.ActionCreate),
                AppPermissions.Create(AppPermissions.ModuleWorkplace, AppPermissions.ActionEdit),
                AppPermissions.Create(AppPermissions.ModuleWorkplace, AppPermissions.ActionApprove),
                AppPermissions.Create(AppPermissions.ModuleEtqa, AppPermissions.ActionView),
                AppPermissions.Create(AppPermissions.ModuleEtqa, AppPermissions.ActionCreate),
                AppPermissions.Create(AppPermissions.ModuleEtqa, AppPermissions.ActionEdit),
                AppPermissions.Create(AppPermissions.ModuleWsp, AppPermissions.ActionView),
                AppPermissions.Create(AppPermissions.ModuleWsp, AppPermissions.ActionApprove),
                AppPermissions.Create(AppPermissions.ModuleGrants, AppPermissions.ActionView),
                AppPermissions.Create(AppPermissions.ModuleLearners, AppPermissions.ActionView),
                AppPermissions.Create(AppPermissions.ModuleOrganisations, AppPermissions.ActionView)
            });

        // 5. SDF (Skills Development Facilitator)
        await EnsureRoleWithPermissionsAsync(db, "SDF", "External Skills Development Facilitator submitting WSP/ATR and Grant Applications.",
            new List<string>
            {
                AppPermissions.Create(AppPermissions.ModuleWsp, AppPermissions.ActionView),
                AppPermissions.Create(AppPermissions.ModuleWsp, AppPermissions.ActionCreate),
                AppPermissions.Create(AppPermissions.ModuleWsp, AppPermissions.ActionEdit),
                AppPermissions.Create(AppPermissions.ModuleWsp, AppPermissions.ActionSubmit),
                AppPermissions.Create(AppPermissions.ModuleGrants, AppPermissions.ActionView),
                AppPermissions.Create(AppPermissions.ModuleGrants, AppPermissions.ActionCreate),
                AppPermissions.Create(AppPermissions.ModuleGrants, AppPermissions.ActionEdit),
                AppPermissions.Create(AppPermissions.ModuleGrants, AppPermissions.ActionSubmit),
                AppPermissions.Create(AppPermissions.ModuleLearners, AppPermissions.ActionView),
                AppPermissions.Create(AppPermissions.ModuleLearners, AppPermissions.ActionCreate),
                AppPermissions.Create(AppPermissions.ModuleWorkplace, AppPermissions.ActionView),
                AppPermissions.Create(AppPermissions.ModuleWorkplace, AppPermissions.ActionCreate),
                AppPermissions.Create(AppPermissions.ModuleOrganisations, AppPermissions.ActionView)
            });

        // 6. ReviewCommittee (Adjudication & Grant Approvals)
        await EnsureRoleWithPermissionsAsync(db, "ReviewCommittee", "Review & Adjudication Committee member approving grant allocations & scopes.",
            new List<string>
            {
                AppPermissions.Create(AppPermissions.ModuleGrants, AppPermissions.ActionView),
                AppPermissions.Create(AppPermissions.ModuleGrants, AppPermissions.ActionApprove),
                AppPermissions.Create(AppPermissions.ModuleGrants, AppPermissions.ActionReject),
                AppPermissions.Create(AppPermissions.ModuleEtqa, AppPermissions.ActionView),
                AppPermissions.Create(AppPermissions.ModuleEtqa, AppPermissions.ActionApprove),
                AppPermissions.Create(AppPermissions.ModuleWorkplace, AppPermissions.ActionView),
                AppPermissions.Create(AppPermissions.ModuleWorkplace, AppPermissions.ActionApprove),
                AppPermissions.Create(AppPermissions.ModuleCompliance, AppPermissions.ActionView)
            });

        await db.SaveChangesAsync();
    }

    private static async Task EnsureRoleWithPermissionsAsync(INsdmsDbContext db, string roleName, string description, List<string> permissions)
    {
        var normalized = roleName.Trim().ToUpperInvariant();
        var role = await db.Roles.FirstOrDefaultAsync(r => r.NormalizedName == normalized);
        if (role == null)
        {
            role = new ApplicationRole(roleName.Trim())
            {
                NormalizedName = normalized,
                Description = description,
                Active = true
            };
            db.Roles.Add(role);
            await db.SaveChangesAsync();
        }

        var existingClaims = await db.RoleClaims
            .Where(rc => rc.RoleId == role.Id && rc.ClaimType == PermissionClaimType)
            .Select(rc => rc.ClaimValue)
            .ToListAsync();

        foreach (var perm in permissions.Distinct())
        {
            if (!existingClaims.Contains(perm))
            {
                db.RoleClaims.Add(new IdentityRoleClaim<int>
                {
                    RoleId = role.Id,
                    ClaimType = PermissionClaimType,
                    ClaimValue = perm
                });
            }
        }
    }
}
