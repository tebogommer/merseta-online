using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class IdentityServiceTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, IdentityService service) CreateTestContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var service = new IdentityService(factory, audit);
        return (factory, db, audit, service);
    }

    [Fact]
    public async Task CreateUserAsync_HashesPasswordAndAssignsRole()
    {
        var (factory, db, audit, service) = CreateTestContext();

        var user = new ApplicationUser
        {
            UserName = "admin@merseta.org.za",
            Email = "admin@merseta.org.za"
        };

        var created = await service.CreateUserAsync(user, "SuperSecretP@ss1", "Administrator", "SystemAdmin");

        Assert.True(created.Id > 0);
        Assert.NotNull(created.PasswordHash);
        Assert.NotEqual("SuperSecretP@ss1", created.PasswordHash);

        var roles = await service.GetUserRolesAsync(created.Id);
        Assert.Contains("Administrator", roles);

        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "ApplicationUser" && a.ActionName == "CreateUser");
        Assert.NotNull(auditLog);
    }

    [Fact]
    public async Task ValidateCredentialsAsync_ValidAndInvalidPasswords()
    {
        var (factory, db, audit, service) = CreateTestContext();

        var user = new ApplicationUser
        {
            UserName = "officer1",
            Email = "officer1@merseta.org.za"
        };

        await service.CreateUserAsync(user, "CorrectPassword123!", "Officer", "System");

        var (successTrue, validUser) = await service.ValidateCredentialsAsync("officer1", "CorrectPassword123!");
        Assert.True(successTrue);
        Assert.NotNull(validUser);

        var (successFalse, invalidUser) = await service.ValidateCredentialsAsync("officer1", "WrongPassword");
        Assert.False(successFalse);
        Assert.Null(invalidUser);
    }

    [Fact]
    public async Task LinkUserToPersonAsync_AndUnlink_UpdatesAndLogsAudit()
    {
        var (factory, db, audit, service) = CreateTestContext();

        var person = new Person { FirstName = "Mandla", LastName = "Zulu", Email = "mandla@merseta.org.za" };
        db.People.Add(person);
        await db.SaveChangesAsync();

        var user = new ApplicationUser { UserName = "mzulu", Email = "mandla@merseta.org.za" };
        await service.CreateUserAsync(user, "Password123!", currentUsername: "Admin");

        // Link
        var linkResult = await service.LinkUserToPersonAsync(user.Id, person.Id, "Admin");
        Assert.True(linkResult);

        var userReloaded = await service.GetUserByIdAsync(user.Id);
        Assert.Equal(person.Id, userReloaded?.PersonId);
        Assert.NotNull(userReloaded?.Person);
        Assert.Equal("Mandla", userReloaded?.Person?.FirstName);

        // Unlink
        var unlinkResult = await service.UnlinkUserFromPersonAsync(user.Id, "Admin");
        Assert.True(unlinkResult);

        userReloaded = await service.GetUserByIdAsync(user.Id);
        Assert.Null(userReloaded?.PersonId);
    }

    [Fact]
    public async Task ChangePasswordAsync_ValidatesCurrentPasswordBeforeChanging()
    {
        var (factory, db, audit, service) = CreateTestContext();

        var user = new ApplicationUser { UserName = "testpwd", Email = "testpwd@merseta.org.za" };
        await service.CreateUserAsync(user, "InitialPass123!", currentUsername: "Admin");

        // Try wrong current password
        var failedChange = await service.ChangePasswordAsync(user.Id, "WrongPass", "NewPass123!", "testpwd");
        Assert.False(failedChange);

        // Right current password
        var successChange = await service.ChangePasswordAsync(user.Id, "InitialPass123!", "NewPass123!", "testpwd");
        Assert.True(successChange);

        // Check new credentials work
        var (succeeded, _) = await service.ValidateCredentialsAsync("testpwd", "NewPass123!");
        Assert.True(succeeded);
    }

    [Fact]
    public async Task DeactivateUserAsync_SetsInactiveAndLogsAudit()
    {
        var (factory, db, audit, service) = CreateTestContext();

        var user = new ApplicationUser { UserName = "deact_me", Email = "deact@merseta.org.za" };
        await service.CreateUserAsync(user, "Password123!", currentUsername: "Admin");

        var deactResult = await service.DeactivateUserAsync(user.Id, "Admin");
        Assert.True(deactResult);

        var (succeeded, _) = await service.ValidateCredentialsAsync("deact_me", "Password123!");
        Assert.False(succeeded); // Inactive user cannot authenticate
    }

    [Fact]
    public void ValidatePasswordPolicy_EnforcesComplexity()
    {
        var (_, _, _, service) = CreateTestContext();

        // Valid
        var (valid, errors) = service.ValidatePasswordPolicy("ValidP@ssword1");
        Assert.True(valid);
        Assert.Empty(errors);

        // Too short
        var (tooShort, shortErrors) = service.ValidatePasswordPolicy("Sh0rt!");
        Assert.False(tooShort);
        Assert.Contains(shortErrors, e => e.Contains("at least 8 characters"));

        // Missing uppercase
        var (noUpper, upperErrors) = service.ValidatePasswordPolicy("lowercase123!");
        Assert.False(noUpper);
        Assert.Contains(upperErrors, e => e.Contains("uppercase"));

        // Missing lowercase
        var (noLower, lowerErrors) = service.ValidatePasswordPolicy("UPPERCASE123!");
        Assert.False(noLower);
        Assert.Contains(lowerErrors, e => e.Contains("lowercase"));

        // Missing digit
        var (noDigit, digitErrors) = service.ValidatePasswordPolicy("NoDigitsHere!");
        Assert.False(noDigit);
        Assert.Contains(digitErrors, e => e.Contains("number"));
    }

    [Fact]
    public async Task ValidateCredentialsExtendedAsync_EnforcesLockoutPolicy()
    {
        var (_, db, _, service) = CreateTestContext();

        var user = new ApplicationUser
        {
            UserName = "lockout_user",
            Email = "lockout@merseta.org.za"
        };
        await service.CreateUserAsync(user, "StrongPass123!", currentUsername: "Admin");

        // 4 failed attempts should not lock out
        for (int i = 0; i < 4; i++)
        {
            var res = await service.ValidateCredentialsExtendedAsync("lockout_user", "WrongPassword");
            Assert.False(res.Succeeded);
            Assert.False(res.IsLockedOut);
        }

        // 5th failed attempt triggers lockout
        var lockedRes = await service.ValidateCredentialsExtendedAsync("lockout_user", "WrongPassword");
        Assert.False(lockedRes.Succeeded);
        Assert.True(lockedRes.IsLockedOut);
        Assert.NotNull(lockedRes.LockoutEnd);
        Assert.True(lockedRes.LockoutEnd.Value > DateTimeOffset.UtcNow);

        // Even with correct password, login is blocked while locked out
        var blockedRes = await service.ValidateCredentialsExtendedAsync("lockout_user", "StrongPass123!");
        Assert.False(blockedRes.Succeeded);
        Assert.True(blockedRes.IsLockedOut);

        // Admin unlocks the user
        var unlocked = await service.UnlockUserAsync(user.Id, "Admin");
        Assert.True(unlocked);

        // Now login succeeds with correct password
        var successRes = await service.ValidateCredentialsExtendedAsync("lockout_user", "StrongPass123!");
        Assert.True(successRes.Succeeded);
        Assert.False(successRes.IsLockedOut);
    }

    [Fact]
    public async Task ValidateCredentialsExtendedAsync_SupportsEmailOrUsername()
    {
        var (_, _, _, service) = CreateTestContext();

        var user = new ApplicationUser
        {
            UserName = "johndoe",
            Email = "john.doe@merseta.org.za"
        };
        await service.CreateUserAsync(user, "SecureP@ssword2026", currentUsername: "Admin");

        // Authenticate with username
        var byUsername = await service.ValidateCredentialsExtendedAsync("johndoe", "SecureP@ssword2026");
        Assert.True(byUsername.Succeeded);
        Assert.NotNull(byUsername.User);
        Assert.Equal("johndoe", byUsername.User.UserName);

        // Authenticate with email
        var byEmail = await service.ValidateCredentialsExtendedAsync("john.doe@merseta.org.za", "SecureP@ssword2026");
        Assert.True(byEmail.Succeeded);
        Assert.NotNull(byEmail.User);
        Assert.Equal("johndoe", byEmail.User.UserName);
    }

    [Fact]
    public async Task SeedDefaultUsersAsync_CreatesStandardStatutoryAccountsIdempotently()
    {
        var (_, db, _, service) = CreateTestContext();

        // Run seeder first time
        await service.SeedDefaultUsersAsync();

        var sysAdmin = await service.GetUserByEmailAsync("sysadmin@merseta.org.za");
        Assert.NotNull(sysAdmin);
        var sysRoles = await service.GetUserRolesAsync(sysAdmin.Id);
        Assert.Contains("SuperAdmin", sysRoles);
        Assert.Contains("Admin", sysRoles);

        var clo = await service.GetUserByEmailAsync("clo.officer@merseta.org.za");
        Assert.NotNull(clo);
        var cloRoles = await service.GetUserRolesAsync(clo.Id);
        Assert.Contains("CLO", cloRoles);

        var sdf = await service.GetUserByEmailAsync("sdf.employer@toyota.co.za");
        Assert.NotNull(sdf);
        var sdfRoles = await service.GetUserRolesAsync(sdf.Id);
        Assert.Contains("SDF", sdfRoles);

        // Seeder run second time must be idempotent
        await service.SeedDefaultUsersAsync();

        var totalUsers = await service.GetAllUsersAsync();
        Assert.Equal(5, totalUsers.Count);
    }

    [Fact]
    public async Task SelfServiceRegistration_CreatesUnconfirmedUser_BlocksLoginUntilConfirmed()
    {
        var (_, _, _, service) = CreateTestContext();

        var applicant = new ApplicationUser
        {
            UserName = "applicant@domain.co.za",
            Email = "applicant@domain.co.za"
        };

        // Self-service creation with emailConfirmed: false
        await service.CreateUserAsync(applicant, "P@ssword2026!", "User", "SELF_SERVICE", emailConfirmed: false);

        // Attempting to log in before confirmation must fail with IsEmailUnconfirmed = true
        var unconfirmedAuth = await service.ValidateCredentialsExtendedAsync("applicant@domain.co.za", "P@ssword2026!");
        Assert.False(unconfirmedAuth.Succeeded);
        Assert.True(unconfirmedAuth.IsEmailUnconfirmed);
        Assert.Contains("email address has not been confirmed", unconfirmedAuth.ErrorMessage, StringComparison.OrdinalIgnoreCase);

        // Generate token
        var token = await service.GenerateEmailConfirmationTokenAsync(applicant.Id);
        Assert.False(string.IsNullOrWhiteSpace(token));

        // Invalid token attempt fails
        var (invalidTokenSucceeded, invalidMsg) = await service.ConfirmEmailAsync(applicant.Id, "wrong-token-abc");
        Assert.False(invalidTokenSucceeded);
        Assert.NotNull(invalidMsg);

        // Confirm with valid token
        var (confirmSucceeded, _) = await service.ConfirmEmailAsync(applicant.Id, token);
        Assert.True(confirmSucceeded);

        // User now authenticates successfully
        var confirmedAuth = await service.ValidateCredentialsExtendedAsync("applicant@domain.co.za", "P@ssword2026!");
        Assert.True(confirmedAuth.Succeeded);
        Assert.NotNull(confirmedAuth.User);
        Assert.True(confirmedAuth.User.EmailConfirmed);

        // User has only standard "User" role
        var roles = await service.GetUserRolesAsync(applicant.Id);
        Assert.Single(roles);
        Assert.Equal("User", roles[0]);
    }
}
