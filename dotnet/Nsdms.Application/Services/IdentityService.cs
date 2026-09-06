using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class AuthResultDto
{
    public bool Succeeded { get; set; }
    public ApplicationUser? User { get; set; }
    public bool IsLockedOut { get; set; }
    public DateTimeOffset? LockoutEnd { get; set; }
    public bool IsNotActive { get; set; }
    public bool IsEmailUnconfirmed { get; set; }
    public string? ErrorMessage { get; set; }
}

public interface IIdentityService
{
    // User Queries
    Task<List<ApplicationUser>> GetAllUsersAsync(string? search = null, string? role = null);
    Task<ApplicationUser?> GetUserByIdAsync(int id);
    Task<ApplicationUser?> GetUserByUsernameAsync(string username);
    Task<ApplicationUser?> GetUserByEmailAsync(string email);
    Task<List<string>> GetUserRolesAsync(int userId);

    // Email Confirmation & Account Activation
    Task<string> GenerateEmailConfirmationTokenAsync(int userId);
    Task<(bool Succeeded, string? ErrorMessage)> ConfirmEmailAsync(int userId, string token);

    // User Management & Security
    Task<ApplicationUser> CreateUserAsync(ApplicationUser user, string password, string? role = null, string currentUsername = "SYSTEM", bool? emailConfirmed = null);
    Task<ApplicationUser> UpdateUserAsync(ApplicationUser user, string currentUsername = "SYSTEM");
    Task<(bool Succeeded, ApplicationUser? User)> ValidateCredentialsAsync(string username, string password);
    Task<AuthResultDto> ValidateCredentialsExtendedAsync(string usernameOrEmail, string password);
    (bool IsValid, List<string> Errors) ValidatePasswordPolicy(string password);
    Task<bool> ChangePasswordAsync(int userId, string currentPassword, string newPassword, string currentUsername = "SYSTEM");
    Task<bool> ResetPasswordAsync(int userId, string newPassword, string currentUsername = "SYSTEM");
    Task<bool> UnlockUserAsync(int userId, string currentUsername = "SYSTEM");
    Task<bool> ActivateUserAsync(int userId, string currentUsername = "SYSTEM");
    Task<bool> DeactivateUserAsync(int userId, string currentUsername = "SYSTEM");
    Task<bool> DeleteUserAsync(int userId, string currentUsername = "SYSTEM");

    // Role Management
    Task<bool> AssignRoleAsync(int userId, string roleName, string currentUsername = "SYSTEM");
    Task<bool> RemoveRoleAsync(int userId, string roleName, string currentUsername = "SYSTEM");

    // Person Linking
    Task<bool> LinkUserToPersonAsync(int userId, int personId, string currentUsername = "SYSTEM");
    Task<bool> UnlinkUserFromPersonAsync(int userId, string currentUsername = "SYSTEM");

    // Seeding
    Task SeedDefaultUsersAsync();
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

    public (bool IsValid, List<string> Errors) ValidatePasswordPolicy(string password)
    {
        var errors = new List<string>();
        if (string.IsNullOrWhiteSpace(password) || password.Length < 8)
        {
            errors.Add("Password must be at least 8 characters long.");
        }
        if (!password.Any(char.IsUpper))
        {
            errors.Add("Password must contain at least one uppercase letter (A-Z).");
        }
        if (!password.Any(char.IsLower))
        {
            errors.Add("Password must contain at least one lowercase letter (a-z).");
        }
        if (!password.Any(char.IsDigit))
        {
            errors.Add("Password must contain at least one number (0-9).");
        }
        return (errors.Count == 0, errors);
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

    public async Task<ApplicationUser> CreateUserAsync(ApplicationUser user, string password, string? role = null, string currentUsername = "SYSTEM", bool? emailConfirmed = null)
    {
        if (string.IsNullOrWhiteSpace(user.UserName))
        {
            throw new ArgumentException("Username is required.", nameof(user));
        }

        if (string.IsNullOrWhiteSpace(password))
        {
            throw new ArgumentException("Password is required.", nameof(password));
        }

        var (isValid, errors) = ValidatePasswordPolicy(password);
        if (!isValid)
        {
            throw new ArgumentException($"Password does not meet complexity requirements: {string.Join(" ", errors)}", nameof(password));
        }

        user.NormalizedUserName = user.UserName.ToUpperInvariant();
        if (!string.IsNullOrWhiteSpace(user.Email))
        {
            user.NormalizedEmail = user.Email.ToUpperInvariant();
        }

        user.PasswordHash = _passwordHasher.HashPassword(user, password);
        user.SecurityStamp = Guid.NewGuid().ToString();
        user.LockoutEnabled = true;
        user.CreatedAt = DateTime.UtcNow;
        user.CreatedBy = currentUsername;

        if (emailConfirmed.HasValue)
        {
            user.EmailConfirmed = emailConfirmed.Value;
        }
        else if (currentUsername != "SELF_SERVICE" && (string.IsNullOrEmpty(user.Email) || !string.Equals(currentUsername, user.Email, StringComparison.OrdinalIgnoreCase)))
        {
            // Internal, system, and administrative users are pre-confirmed
            user.EmailConfirmed = true;
        }

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
        var result = await ValidateCredentialsExtendedAsync(username, password);
        return (result.Succeeded, result.Succeeded ? result.User : null);
    }

    public async Task<AuthResultDto> ValidateCredentialsExtendedAsync(string usernameOrEmail, string password)
    {
        if (string.IsNullOrWhiteSpace(usernameOrEmail) || string.IsNullOrWhiteSpace(password))
        {
            return new AuthResultDto { Succeeded = false, ErrorMessage = "Username and password are required." };
        }

        var normalized = usernameOrEmail.Trim().ToUpperInvariant();
        using var db = await _contextFactory.CreateDbContextAsync();
        var user = await db.Users
            .Include(u => u.Person)
            .Include(u => u.DefaultOrganisation)
            .FirstOrDefaultAsync(u => u.NormalizedUserName == normalized || u.NormalizedEmail == normalized || u.UserName == usernameOrEmail.Trim() || u.Email == usernameOrEmail.Trim());

        if (user == null)
        {
            return new AuthResultDto { Succeeded = false, ErrorMessage = "Invalid username/email or password." };
        }

        if (!user.IsActive)
        {
            return new AuthResultDto
            {
                Succeeded = false,
                User = user,
                IsNotActive = true,
                ErrorMessage = "Account is inactive. Please contact your system administrator."
            };
        }

        if (!user.EmailConfirmed)
        {
            return new AuthResultDto
            {
                Succeeded = false,
                User = user,
                IsEmailUnconfirmed = true,
                ErrorMessage = "Your email address has not been confirmed. Please check your inbox and confirm your email to activate your account."
            };
        }

        // Evaluate Lockout Policy
        if (user.LockoutEnabled && user.LockoutEnd.HasValue && user.LockoutEnd.Value > DateTimeOffset.UtcNow)
        {
            return new AuthResultDto
            {
                Succeeded = false,
                User = user,
                IsLockedOut = true,
                LockoutEnd = user.LockoutEnd,
                ErrorMessage = $"Account is temporarily locked out until {user.LockoutEnd.Value.ToLocalTime():HH:mm} due to multiple failed login attempts."
            };
        }

        if (string.IsNullOrEmpty(user.PasswordHash))
        {
            return new AuthResultDto { Succeeded = false, User = user, ErrorMessage = "No password configured for this account." };
        }

        var verifyResult = _passwordHasher.VerifyHashedPassword(user, user.PasswordHash, password);
        if (verifyResult == PasswordVerificationResult.Success || verifyResult == PasswordVerificationResult.SuccessRehashNeeded)
        {
            if (verifyResult == PasswordVerificationResult.SuccessRehashNeeded)
            {
                user.PasswordHash = _passwordHasher.HashPassword(user, password);
            }

            // Reset failed counter and clear lockout
            if (user.AccessFailedCount > 0 || user.LockoutEnd.HasValue)
            {
                user.AccessFailedCount = 0;
                user.LockoutEnd = null;
            }
            user.ModifiedAt = DateTime.UtcNow;
            await db.SaveChangesAsync();

            return new AuthResultDto
            {
                Succeeded = true,
                User = user
            };
        }

        // Handle Failed Attempt
        if (user.LockoutEnabled)
        {
            user.AccessFailedCount++;
            if (user.AccessFailedCount >= 5)
            {
                user.LockoutEnd = DateTimeOffset.UtcNow.AddMinutes(15);
                _audit.LogAction(db, "ApplicationUser", user.Id, "AccountLockout", "SYSTEM", null, new { user.Id, user.AccessFailedCount, user.LockoutEnd });
            }
            await db.SaveChangesAsync();

            if (user.LockoutEnd.HasValue && user.LockoutEnd.Value > DateTimeOffset.UtcNow)
            {
                return new AuthResultDto
                {
                    Succeeded = false,
                    User = user,
                    IsLockedOut = true,
                    LockoutEnd = user.LockoutEnd,
                    ErrorMessage = "Account has been temporarily locked out for 15 minutes due to 5 consecutive failed login attempts."
                };
            }
        }

        return new AuthResultDto
        {
            Succeeded = false,
            User = user,
            ErrorMessage = "Invalid username/email or password."
        };
    }

    public async Task<bool> ChangePasswordAsync(int userId, string currentPassword, string newPassword, string currentUsername = "SYSTEM")
    {
        var (isValid, errors) = ValidatePasswordPolicy(newPassword);
        if (!isValid)
        {
            throw new ArgumentException($"Password does not meet complexity requirements: {string.Join(" ", errors)}", nameof(newPassword));
        }

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
        var (isValid, errors) = ValidatePasswordPolicy(newPassword);
        if (!isValid)
        {
            throw new ArgumentException($"Password does not meet complexity requirements: {string.Join(" ", errors)}", nameof(newPassword));
        }

        using var db = await _contextFactory.CreateDbContextAsync();
        var user = await db.Users.FindAsync(userId);
        if (user == null) return false;

        user.PasswordHash = _passwordHasher.HashPassword(user, newPassword);
        user.SecurityStamp = Guid.NewGuid().ToString();
        user.AccessFailedCount = 0;
        user.LockoutEnd = null;
        user.ModifiedAt = DateTime.UtcNow;
        user.ModifiedBy = currentUsername;

        _audit.LogAction(db, "ApplicationUser", userId, "ResetPassword", currentUsername, null, new { userId, Reset = true });
        await db.SaveChangesAsync();
        return true;
    }

    public async Task<bool> UnlockUserAsync(int userId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var user = await db.Users.FindAsync(userId);
        if (user == null) return false;

        var beforeState = new { user.Id, user.LockoutEnd, user.AccessFailedCount };
        user.LockoutEnd = null;
        user.AccessFailedCount = 0;
        user.ModifiedAt = DateTime.UtcNow;
        user.ModifiedBy = currentUsername;

        _audit.LogAction(db, "ApplicationUser", userId, "UnlockUser", currentUsername, beforeState, new { user.Id, Unlocked = true });
        await db.SaveChangesAsync();
        return true;
    }

    public async Task<bool> ActivateUserAsync(int userId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var user = await db.Users.FindAsync(userId);
        if (user == null) return false;

        var beforeState = new { user.Id, user.IsActive };
        user.IsActive = true;
        user.ModifiedAt = DateTime.UtcNow;
        user.ModifiedBy = currentUsername;

        _audit.LogAction(db, "ApplicationUser", userId, "ActivateUser", currentUsername, beforeState, new { user.Id, user.IsActive });
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

    public async Task<string> GenerateEmailConfirmationTokenAsync(int userId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var user = await db.Users.FindAsync(userId);
        if (user == null)
        {
            throw new KeyNotFoundException($"User with ID {userId} was not found.");
        }

        if (string.IsNullOrEmpty(user.SecurityStamp))
        {
            user.SecurityStamp = Guid.NewGuid().ToString("N");
            await db.SaveChangesAsync();
        }

        var raw = $"{user.Id}:{user.SecurityStamp}:{user.Email?.ToUpperInvariant()}";
        var hashBytes = System.Security.Cryptography.SHA256.HashData(System.Text.Encoding.UTF8.GetBytes(raw));
        return Convert.ToHexString(hashBytes).ToLowerInvariant();
    }

    public async Task<(bool Succeeded, string? ErrorMessage)> ConfirmEmailAsync(int userId, string token)
    {
        if (string.IsNullOrWhiteSpace(token))
        {
            return (false, "Confirmation token is required.");
        }

        using var db = await _contextFactory.CreateDbContextAsync();
        var user = await db.Users.FindAsync(userId);
        if (user == null)
        {
            return (false, "User account was not found.");
        }

        if (user.EmailConfirmed)
        {
            return (true, null); // Already confirmed
        }

        var expectedToken = Convert.ToHexString(System.Security.Cryptography.SHA256.HashData(
            System.Text.Encoding.UTF8.GetBytes($"{user.Id}:{user.SecurityStamp}:{user.Email?.ToUpperInvariant()}"))).ToLowerInvariant();

        if (!string.Equals(expectedToken, token.Trim(), StringComparison.OrdinalIgnoreCase))
        {
            return (false, "Invalid or expired confirmation token. Please request a new activation link.");
        }

        var beforeState = new { user.Id, user.EmailConfirmed, user.IsActive };
        user.EmailConfirmed = true;
        user.IsActive = true;
        user.SecurityStamp = Guid.NewGuid().ToString("N");
        user.ModifiedAt = DateTime.UtcNow;
        user.ModifiedBy = user.UserName ?? "SYSTEM";

        _audit.LogAction(db, "ApplicationUser", user.Id, "ConfirmEmail", user.UserName ?? "SYSTEM", beforeState, new { user.Id, user.EmailConfirmed, user.IsActive });
        await db.SaveChangesAsync();

        return (true, null);
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

    public async Task SeedDefaultUsersAsync()
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var defaultAccounts = new[]
        {
            new { UserName = "sysadmin@merseta.org.za", Email = "sysadmin@merseta.org.za", Role = "SuperAdmin", SecondRole = "Admin" },
            new { UserName = "clo.officer@merseta.org.za", Email = "clo.officer@merseta.org.za", Role = "CLO", SecondRole = (string?)null },
            new { UserName = "sdf.employer@toyota.co.za", Email = "sdf.employer@toyota.co.za", Role = "SDF", SecondRole = (string?)null },
            new { UserName = "finance.officer@merseta.org.za", Email = "finance.officer@merseta.org.za", Role = "FinanceManager", SecondRole = (string?)null },
            new { UserName = "review.committee@merseta.org.za", Email = "review.committee@merseta.org.za", Role = "ReviewCommittee", SecondRole = (string?)null }
        };

        foreach (var acc in defaultAccounts)
        {
            var normalizedEmail = acc.Email.ToUpperInvariant();
            var existing = await db.Users.FirstOrDefaultAsync(u => u.NormalizedEmail == normalizedEmail || u.NormalizedUserName == normalizedEmail);
            if (existing == null)
            {
                var user = new ApplicationUser
                {
                    UserName = acc.UserName,
                    NormalizedUserName = acc.UserName.ToUpperInvariant(),
                    Email = acc.Email,
                    NormalizedEmail = normalizedEmail,
                    EmailConfirmed = true,
                    IsActive = true,
                    LockoutEnabled = true,
                    SecurityStamp = Guid.NewGuid().ToString(),
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = "SYSTEM"
                };
                user.PasswordHash = _passwordHasher.HashPassword(user, "MerSETA@2026!");
                db.Users.Add(user);
                await db.SaveChangesAsync();

                await EnsureUserInRoleAsync(db, user.Id, acc.Role);
                if (acc.SecondRole != null)
                {
                    await EnsureUserInRoleAsync(db, user.Id, acc.SecondRole);
                }
            }
        }
    }

    private static async Task EnsureUserInRoleAsync(INsdmsDbContext db, int userId, string roleName)
    {
        var normalizedRole = roleName.ToUpperInvariant();
        var role = await db.Roles.FirstOrDefaultAsync(r => r.NormalizedName == normalizedRole || r.Name == roleName);
        if (role == null)
        {
            role = new ApplicationRole(roleName)
            {
                NormalizedName = normalizedRole,
                Active = true
            };
            db.Roles.Add(role);
            await db.SaveChangesAsync();
        }

        var inRole = await db.UserRoles.AnyAsync(ur => ur.UserId == userId && ur.RoleId == role.Id);
        if (!inRole)
        {
            db.UserRoles.Add(new IdentityUserRole<int>
            {
                UserId = userId,
                RoleId = role.Id
            });
            await db.SaveChangesAsync();
        }
    }
}
