using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public interface IIdentityService
{
    // User Queries
    Task<List<ApplicationUser>> GetAllUsersAsync(string? search = null, string? role = null);
    Task<ApplicationUser?> GetUserByIdAsync(int id);
    Task<ApplicationUser?> GetUserByUsernameAsync(string username);
    Task<ApplicationUser?> GetUserByEmailAsync(string email);
    Task<List<string>> GetUserRolesAsync(int userId);

    // User Management & Security
    Task<ApplicationUser> CreateUserAsync(ApplicationUser user, string password, string? role = null, string currentUsername = "SYSTEM");
    Task<ApplicationUser> UpdateUserAsync(ApplicationUser user, string currentUsername = "SYSTEM");
    Task<(bool Succeeded, ApplicationUser? User)> ValidateCredentialsAsync(string username, string password);
    Task<bool> ChangePasswordAsync(int userId, string currentPassword, string newPassword, string currentUsername = "SYSTEM");
    Task<bool> ResetPasswordAsync(int userId, string newPassword, string currentUsername = "SYSTEM");
    Task<bool> DeactivateUserAsync(int userId, string currentUsername = "SYSTEM");
    Task<bool> DeleteUserAsync(int userId, string currentUsername = "SYSTEM");

    // Role Management
    Task<bool> AssignRoleAsync(int userId, string roleName, string currentUsername = "SYSTEM");
    Task<bool> RemoveRoleAsync(int userId, string roleName, string currentUsername = "SYSTEM");

    // Person Linking
    Task<bool> LinkUserToPersonAsync(int userId, int personId, string currentUsername = "SYSTEM");
    Task<bool> UnlinkUserFromPersonAsync(int userId, string currentUsername = "SYSTEM");
}

public class IdentityService : IIdentityService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;
    private readonly PasswordHasher<ApplicationUser> _passwordHasher;

    public IdentityService(INsdmsDbContextFactory contextFactory, IAuditService audit)
    {
        _contextFactory = contextFactory;
        _audit = audit;
        _passwordHasher = new PasswordHasher<ApplicationUser>();
    }

    public async Task<List<ApplicationUser>> GetAllUsersAsync(string? search = null, string? role = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.Users
            .Include(u => u.Person)
            .Include(u => u.DefaultOrganisation)
            .AsQueryable();

        if (!string.IsNullOrWhiteSpace(role))
        {
            var targetRole = await db.Roles.FirstOrDefaultAsync(r => r.NormalizedName == role.ToUpperInvariant() || r.Name == role);
            if (targetRole != null)
            {
                var userIdsInRole = await db.UserRoles
                    .Where(ur => ur.RoleId == targetRole.Id)
                    .Select(ur => ur.UserId)
                    .ToListAsync();

                query = query.Where(u => userIdsInRole.Contains(u.Id));
            }
            else
            {
                return new List<ApplicationUser>();
            }
        }

        if (!string.IsNullOrWhiteSpace(search))
        {
            var s = search.Trim();
            query = query.Where(u =>
                (u.UserName != null && u.UserName.Contains(s)) ||
                (u.Email != null && u.Email.Contains(s)) ||
                (u.Person != null && (u.Person.FirstName.Contains(s) || u.Person.LastName.Contains(s) || u.Person.RsaIdNumber.Contains(s))));
        }

        return await query
            .OrderBy(u => u.UserName)
            .ToListAsync();
    }

    public async Task<ApplicationUser?> GetUserByIdAsync(int id)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.Users
            .Include(u => u.Person)
            .Include(u => u.DefaultOrganisation)
            .FirstOrDefaultAsync(u => u.Id == id);
    }

    public async Task<ApplicationUser?> GetUserByUsernameAsync(string username)
    {
        var normalized = username.Trim().ToUpperInvariant();
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.Users
            .Include(u => u.Person)
            .Include(u => u.DefaultOrganisation)
            .FirstOrDefaultAsync(u => u.NormalizedUserName == normalized || u.UserName == username.Trim());
    }

    public async Task<ApplicationUser?> GetUserByEmailAsync(string email)
    {
        var normalized = email.Trim().ToUpperInvariant();
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.Users
            .Include(u => u.Person)
            .Include(u => u.DefaultOrganisation)
            .FirstOrDefaultAsync(u => u.NormalizedEmail == normalized || u.Email == email.Trim());
    }

    public async Task<List<string>> GetUserRolesAsync(int userId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var roleIds = await db.UserRoles
            .Where(ur => ur.UserId == userId)
            .Select(ur => ur.RoleId)
            .ToListAsync();

        return await db.Roles
            .Where(r => roleIds.Contains(r.Id))
            .Select(r => r.Name!)
            .Where(name => name != null)
            .ToListAsync();
    }

    public async Task<ApplicationUser> CreateUserAsync(ApplicationUser user, string password, string? role = null, string currentUsername = "SYSTEM")
    {
        if (string.IsNullOrWhiteSpace(user.UserName))
        {
            throw new ArgumentException("Username is required.", nameof(user));
        }

        if (string.IsNullOrWhiteSpace(password))
        {
            throw new ArgumentException("Password is required.", nameof(password));
        }

        user.NormalizedUserName = user.UserName.ToUpperInvariant();
        if (!string.IsNullOrWhiteSpace(user.Email))
        {
            user.NormalizedEmail = user.Email.ToUpperInvariant();
        }

        user.PasswordHash = _passwordHasher.HashPassword(user, password);
        user.SecurityStamp = Guid.NewGuid().ToString();
        user.CreatedAt = DateTime.UtcNow;
        user.CreatedBy = currentUsername;

        using var db = await _contextFactory.CreateDbContextAsync();
        db.Users.Add(user);
        await db.SaveChangesAsync();

        if (!string.IsNullOrWhiteSpace(role))
        {
            var normalizedRoleName = role.Trim().ToUpperInvariant();
            var appRole = await db.Roles.FirstOrDefaultAsync(r => r.NormalizedName == normalizedRoleName || r.Name == role.Trim());
            if (appRole == null)
            {
                appRole = new ApplicationRole(role.Trim())
                {
                    NormalizedName = normalizedRoleName,
                    Active = true
                };
                db.Roles.Add(appRole);
                await db.SaveChangesAsync();
            }

            db.UserRoles.Add(new IdentityUserRole<int>
            {
                UserId = user.Id,
                RoleId = appRole.Id
            });
            await db.SaveChangesAsync();
        }

        var auditDetails = new
        {
            user.Id,
            user.UserName,
            user.Email,
            user.PersonId,
            Role = role,
            user.IsActive
        };

        _audit.LogAction(db, "ApplicationUser", user.Id, "CreateUser", currentUsername, null, auditDetails);
        await db.SaveChangesAsync();
        return user;
    }

    public async Task<ApplicationUser> UpdateUserAsync(ApplicationUser user, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.Users.FindAsync(user.Id);
        if (existing == null)
        {
            throw new KeyNotFoundException($"User with ID {user.Id} was not found.");
        }

        var beforeState = new
        {
            existing.UserName,
            existing.Email,
            existing.PhoneNumber,
            existing.PersonId,
            existing.DefaultOrganisationId,
            existing.IsActive
        };

        existing.UserName = user.UserName;
        existing.NormalizedUserName = user.UserName?.ToUpperInvariant();
        existing.Email = user.Email;
        existing.NormalizedEmail = user.Email?.ToUpperInvariant();
        existing.PhoneNumber = user.PhoneNumber;
        existing.DefaultOrganisationId = user.DefaultOrganisationId;
        existing.IsActive = user.IsActive;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        _audit.LogAction(db, "ApplicationUser", existing.Id, "UpdateUser", currentUsername, beforeState, existing);
        await db.SaveChangesAsync();
        return existing;
    }

    public async Task<(bool Succeeded, ApplicationUser? User)> ValidateCredentialsAsync(string username, string password)
    {
        var user = await GetUserByUsernameAsync(username);
        if (user == null || !user.IsActive)
        {
            return (false, null);
        }

        if (string.IsNullOrEmpty(user.PasswordHash))
        {
            return (false, null);
        }

        var result = _passwordHasher.VerifyHashedPassword(user, user.PasswordHash, password);
        if (result == PasswordVerificationResult.Success || result == PasswordVerificationResult.SuccessRehashNeeded)
        {
            return (true, user);
        }

        return (false, null);
    }

    public async Task<bool> ChangePasswordAsync(int userId, string currentPassword, string newPassword, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var user = await db.Users.FindAsync(userId);
        if (user == null) return false;

        var verifyResult = _passwordHasher.VerifyHashedPassword(user, user.PasswordHash ?? string.Empty, currentPassword);
        if (verifyResult == PasswordVerificationResult.Failed)
        {
            return false;
        }

        user.PasswordHash = _passwordHasher.HashPassword(user, newPassword);
        user.SecurityStamp = Guid.NewGuid().ToString();
        user.ModifiedAt = DateTime.UtcNow;
        user.ModifiedBy = currentUsername;

        _audit.LogAction(db, "ApplicationUser", userId, "ChangePassword", currentUsername, null, new { userId, Changed = true });
        await db.SaveChangesAsync();
        return true;
    }

    public async Task<bool> ResetPasswordAsync(int userId, string newPassword, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var user = await db.Users.FindAsync(userId);
        if (user == null) return false;

        user.PasswordHash = _passwordHasher.HashPassword(user, newPassword);
        user.SecurityStamp = Guid.NewGuid().ToString();
        user.ModifiedAt = DateTime.UtcNow;
        user.ModifiedBy = currentUsername;

        _audit.LogAction(db, "ApplicationUser", userId, "ResetPassword", currentUsername, null, new { userId, Reset = true });
        await db.SaveChangesAsync();
        return true;
    }

    public async Task<bool> DeactivateUserAsync(int userId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var user = await db.Users.FindAsync(userId);
        if (user == null) return false;

        var beforeState = new { user.Id, user.UserName, user.IsActive };
        user.IsActive = false;
        user.ModifiedAt = DateTime.UtcNow;
        user.ModifiedBy = currentUsername;

        _audit.LogAction(db, "ApplicationUser", userId, "DeactivateUser", currentUsername, beforeState, new { user.Id, user.IsActive });
        await db.SaveChangesAsync();
        return true;
    }

    public async Task<bool> DeleteUserAsync(int userId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var user = await db.Users.FindAsync(userId);
        if (user == null) return false;

        var beforeState = new { user.Id, user.UserName, user.Email, user.IsActive };

        var userRoles = await db.UserRoles.Where(ur => ur.UserId == userId).ToListAsync();
        if (userRoles.Any())
        {
            db.UserRoles.RemoveRange(userRoles);
        }

        db.Users.Remove(user);
        _audit.LogAction(db, "ApplicationUser", userId, "DeleteUser", currentUsername, beforeState, null);
        await db.SaveChangesAsync();
        return true;
    }

    public async Task<bool> AssignRoleAsync(int userId, string roleName, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var user = await db.Users.FindAsync(userId);
        if (user == null) return false;

        var normalizedRoleName = roleName.Trim().ToUpperInvariant();
        var role = await db.Roles.FirstOrDefaultAsync(r => r.NormalizedName == normalizedRoleName || r.Name == roleName.Trim());

        if (role == null)
        {
            role = new ApplicationRole(roleName.Trim())
            {
                NormalizedName = normalizedRoleName,
                Active = true
            };
            db.Roles.Add(role);
            await db.SaveChangesAsync();
        }

        var alreadyInRole = await db.UserRoles.AnyAsync(ur => ur.UserId == userId && ur.RoleId == role.Id);
        if (alreadyInRole) return true;

        db.UserRoles.Add(new IdentityUserRole<int>
        {
            UserId = userId,
            RoleId = role.Id
        });

        _audit.LogAction(db, "ApplicationUser", userId, "AssignRole", currentUsername, null, new { userId, Role = roleName });
        await db.SaveChangesAsync();
        return true;
    }

    public async Task<bool> RemoveRoleAsync(int userId, string roleName, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var normalizedRoleName = roleName.Trim().ToUpperInvariant();
        var role = await db.Roles.FirstOrDefaultAsync(r => r.NormalizedName == normalizedRoleName || r.Name == roleName.Trim());
        if (role == null) return false;

        var userRole = await db.UserRoles.FirstOrDefaultAsync(ur => ur.UserId == userId && ur.RoleId == role.Id);
        if (userRole == null) return false;

        db.UserRoles.Remove(userRole);
        _audit.LogAction(db, "ApplicationUser", userId, "RemoveRole", currentUsername, new { userId, Role = roleName }, null);
        await db.SaveChangesAsync();
        return true;
    }

    public async Task<bool> LinkUserToPersonAsync(int userId, int personId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var user = await db.Users.FindAsync(userId);
        if (user == null) return false;

        var personExists = await db.People.AnyAsync(p => p.Id == personId);
        if (!personExists)
        {
            throw new KeyNotFoundException($"Person with ID {personId} does not exist.");
        }

        var beforeState = new { user.Id, user.PersonId };
        user.PersonId = personId;
        user.ModifiedAt = DateTime.UtcNow;
        user.ModifiedBy = currentUsername;

        _audit.LogAction(db, "ApplicationUser", userId, "LinkPerson", currentUsername, beforeState, new { user.Id, PersonId = personId });
        await db.SaveChangesAsync();
        return true;
    }

    public async Task<bool> UnlinkUserFromPersonAsync(int userId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var user = await db.Users.FindAsync(userId);
        if (user == null) return false;

        var beforeState = new { user.Id, user.PersonId };
        user.PersonId = null;
        user.ModifiedAt = DateTime.UtcNow;
        user.ModifiedBy = currentUsername;

        _audit.LogAction(db, "ApplicationUser", userId, "UnlinkPerson", currentUsername, beforeState, new { user.Id, PersonId = (int?)null });
        await db.SaveChangesAsync();
        return true;
    }
}
