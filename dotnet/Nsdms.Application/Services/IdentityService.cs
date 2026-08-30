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
    private readonly INsdmsDbContext _db;
    private readonly IAuditService _audit;
    private readonly PasswordHasher<ApplicationUser> _passwordHasher;

    public IdentityService(INsdmsDbContext db, IAuditService audit)
    {
        _db = db;
        _audit = audit;
        _passwordHasher = new PasswordHasher<ApplicationUser>();
    }

    public async Task<List<ApplicationUser>> GetAllUsersAsync(string? search = null, string? role = null)
    {
        var query = _db.Users
            .Include(u => u.Person)
            .Include(u => u.DefaultOrganisation)
            .AsQueryable();

        if (!string.IsNullOrWhiteSpace(role))
        {
            var targetRole = await _db.Roles.FirstOrDefaultAsync(r => r.NormalizedName == role.ToUpperInvariant() || r.Name == role);
            if (targetRole != null)
            {
                var userIdsInRole = await _db.UserRoles
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
        return await _db.Users
            .Include(u => u.Person)
            .Include(u => u.DefaultOrganisation)
            .FirstOrDefaultAsync(u => u.Id == id);
    }

    public async Task<ApplicationUser?> GetUserByUsernameAsync(string username)
    {
        var normalized = username.Trim().ToUpperInvariant();
        return await _db.Users
            .Include(u => u.Person)
            .Include(u => u.DefaultOrganisation)
            .FirstOrDefaultAsync(u => u.NormalizedUserName == normalized || u.UserName == username.Trim());
    }

    public async Task<ApplicationUser?> GetUserByEmailAsync(string email)
    {
        var normalized = email.Trim().ToUpperInvariant();
        return await _db.Users
            .Include(u => u.Person)
            .Include(u => u.DefaultOrganisation)
            .FirstOrDefaultAsync(u => u.NormalizedEmail == normalized || u.Email == email.Trim());
    }

    public async Task<List<string>> GetUserRolesAsync(int userId)
    {
        var roleIds = await _db.UserRoles
            .Where(ur => ur.UserId == userId)
            .Select(ur => ur.RoleId)
            .ToListAsync();

        return await _db.Roles
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

        _db.Users.Add(user);
        await _db.SaveChangesAsync();

        if (!string.IsNullOrWhiteSpace(role))
        {
            var normalizedRoleName = role.Trim().ToUpperInvariant();
            var appRole = await _db.Roles.FirstOrDefaultAsync(r => r.NormalizedName == normalizedRoleName || r.Name == role.Trim());
            if (appRole == null)
            {
                appRole = new ApplicationRole(role.Trim())
                {
                    NormalizedName = normalizedRoleName,
                    Active = true
                };
                _db.Roles.Add(appRole);
                await _db.SaveChangesAsync();
            }

            _db.UserRoles.Add(new IdentityUserRole<int>
            {
                UserId = user.Id,
                RoleId = appRole.Id
            });
            await _db.SaveChangesAsync();
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

        await _audit.LogActionAsync("ApplicationUser", user.Id, "CreateUser", currentUsername, null, auditDetails);
        return user;
    }

    public async Task<ApplicationUser> UpdateUserAsync(ApplicationUser user, string currentUsername = "SYSTEM")
    {
        var existing = await _db.Users.FindAsync(user.Id);
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

        await _db.SaveChangesAsync();

        await _audit.LogActionAsync("ApplicationUser", existing.Id, "UpdateUser", currentUsername, beforeState, existing);
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
        var user = await _db.Users.FindAsync(userId);
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

        await _db.SaveChangesAsync();
        await _audit.LogActionAsync("ApplicationUser", userId, "ChangePassword", currentUsername, null, new { userId, Changed = true });
        return true;
    }

    public async Task<bool> ResetPasswordAsync(int userId, string newPassword, string currentUsername = "SYSTEM")
    {
        var user = await _db.Users.FindAsync(userId);
        if (user == null) return false;

        user.PasswordHash = _passwordHasher.HashPassword(user, newPassword);
        user.SecurityStamp = Guid.NewGuid().ToString();
        user.ModifiedAt = DateTime.UtcNow;
        user.ModifiedBy = currentUsername;

        await _db.SaveChangesAsync();
        await _audit.LogActionAsync("ApplicationUser", userId, "ResetPassword", currentUsername, null, new { userId, Reset = true });
        return true;
    }

    public async Task<bool> DeactivateUserAsync(int userId, string currentUsername = "SYSTEM")
    {
        var user = await _db.Users.FindAsync(userId);
        if (user == null) return false;

        var beforeState = new { user.Id, user.UserName, user.IsActive };
        user.IsActive = false;
        user.ModifiedAt = DateTime.UtcNow;
        user.ModifiedBy = currentUsername;

        await _db.SaveChangesAsync();
        await _audit.LogActionAsync("ApplicationUser", userId, "DeactivateUser", currentUsername, beforeState, new { user.Id, user.IsActive });
        return true;
    }

    public async Task<bool> DeleteUserAsync(int userId, string currentUsername = "SYSTEM")
    {
        var user = await _db.Users.FindAsync(userId);
        if (user == null) return false;

        var beforeState = new { user.Id, user.UserName, user.Email, user.IsActive };

        var userRoles = await _db.UserRoles.Where(ur => ur.UserId == userId).ToListAsync();
        if (userRoles.Any())
        {
            _db.UserRoles.RemoveRange(userRoles);
        }

        _db.Users.Remove(user);
        await _db.SaveChangesAsync();

        await _audit.LogActionAsync("ApplicationUser", userId, "DeleteUser", currentUsername, beforeState, null);
        return true;
    }

    public async Task<bool> AssignRoleAsync(int userId, string roleName, string currentUsername = "SYSTEM")
    {
        var user = await _db.Users.FindAsync(userId);
        if (user == null) return false;

        var normalizedRoleName = roleName.Trim().ToUpperInvariant();
        var role = await _db.Roles.FirstOrDefaultAsync(r => r.NormalizedName == normalizedRoleName || r.Name == roleName.Trim());

        if (role == null)
        {
            role = new ApplicationRole(roleName.Trim())
            {
                NormalizedName = normalizedRoleName,
                Active = true
            };
            _db.Roles.Add(role);
            await _db.SaveChangesAsync();
        }

        var alreadyInRole = await _db.UserRoles.AnyAsync(ur => ur.UserId == userId && ur.RoleId == role.Id);
        if (alreadyInRole) return true;

        _db.UserRoles.Add(new IdentityUserRole<int>
        {
            UserId = userId,
            RoleId = role.Id
        });

        await _db.SaveChangesAsync();
        await _audit.LogActionAsync("ApplicationUser", userId, "AssignRole", currentUsername, null, new { userId, Role = roleName });
        return true;
    }

    public async Task<bool> RemoveRoleAsync(int userId, string roleName, string currentUsername = "SYSTEM")
    {
        var normalizedRoleName = roleName.Trim().ToUpperInvariant();
        var role = await _db.Roles.FirstOrDefaultAsync(r => r.NormalizedName == normalizedRoleName || r.Name == roleName.Trim());
        if (role == null) return false;

        var userRole = await _db.UserRoles.FirstOrDefaultAsync(ur => ur.UserId == userId && ur.RoleId == role.Id);
        if (userRole == null) return false;

        _db.UserRoles.Remove(userRole);
        await _db.SaveChangesAsync();

        await _audit.LogActionAsync("ApplicationUser", userId, "RemoveRole", currentUsername, new { userId, Role = roleName }, null);
        return true;
    }

    public async Task<bool> LinkUserToPersonAsync(int userId, int personId, string currentUsername = "SYSTEM")
    {
        var user = await _db.Users.FindAsync(userId);
        if (user == null) return false;

        var personExists = await _db.People.AnyAsync(p => p.Id == personId);
        if (!personExists)
        {
            throw new KeyNotFoundException($"Person with ID {personId} does not exist.");
        }

        var beforeState = new { user.Id, user.PersonId };
        user.PersonId = personId;
        user.ModifiedAt = DateTime.UtcNow;
        user.ModifiedBy = currentUsername;

        await _db.SaveChangesAsync();
        await _audit.LogActionAsync("ApplicationUser", userId, "LinkPerson", currentUsername, beforeState, new { user.Id, PersonId = personId });
        return true;
    }

    public async Task<bool> UnlinkUserFromPersonAsync(int userId, string currentUsername = "SYSTEM")
    {
        var user = await _db.Users.FindAsync(userId);
        if (user == null) return false;

        var beforeState = new { user.Id, user.PersonId };
        user.PersonId = null;
        user.ModifiedAt = DateTime.UtcNow;
        user.ModifiedBy = currentUsername;

        await _db.SaveChangesAsync();
        await _audit.LogActionAsync("ApplicationUser", userId, "UnlinkPerson", currentUsername, beforeState, new { user.Id, PersonId = (int?)null });
        return true;
    }
}
