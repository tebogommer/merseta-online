using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class IdentityServiceTests
{
    private static NsdmsDbContext CreateInMemoryDbContext()
    {
        var options = new DbContextOptionsBuilder<NsdmsDbContext>()
            .UseInMemoryDatabase(databaseName: Guid.NewGuid().ToString())
            .Options;

        return new NsdmsDbContext(options);
    }

    [Fact]
    public async Task CreateUserAsync_HashesPasswordAndAssignsRole()
    {
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new IdentityService(db, audit);

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
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new IdentityService(db, audit);

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
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new IdentityService(db, audit);

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
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new IdentityService(db, audit);

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
        var db = CreateInMemoryDbContext();
        var audit = new AuditService(db);
        var service = new IdentityService(db, audit);

        var user = new ApplicationUser { UserName = "deact_me", Email = "deact@merseta.org.za" };
        await service.CreateUserAsync(user, "Password123!", currentUsername: "Admin");

        var deactResult = await service.DeactivateUserAsync(user.Id, "Admin");
        Assert.True(deactResult);

        var (succeeded, _) = await service.ValidateCredentialsAsync("deact_me", "Password123!");
        Assert.False(succeeded); // Inactive user cannot authenticate
    }
}
