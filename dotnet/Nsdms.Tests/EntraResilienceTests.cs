using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Security.Claims;
using System.Security.Cryptography;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Logging.Abstractions;
using Moq;
using Moq.Protected;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

/// <summary>
/// Unit and integration tests for Microsoft Entra ID Resilience,
/// Automated Outage Circuit-Breaker, and Disaster Recovery Emergency Backup Password.
/// </summary>
public class EntraResilienceTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, SystemConfigurationService config, EntraResilienceService service, Mock<IHttpClientFactory> httpMock) CreateTestContext(
        Dictionary<string, string?>? additionalConfig = null)
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);

        var configSettings = new Dictionary<string, string?>
        {
            { "Auth:EntraOfflineGracePeriodDays", "14" },
            { "Auth:EntraMaxBackupPasswordFailedAttempts", "5" },
            { "Auth:BackupPasswordMaxFailedAttempts", "5" },
            { "Auth:EntraBackupPasswordLockoutMinutes", "15" },
            { "Auth:BackupPasswordLockoutMinutes", "15" },
            { "Auth:EntraHealthProbeUrl", "https://login.microsoftonline.com/common/discovery/keys" }
        };

        if (additionalConfig != null)
        {
            foreach (var kv in additionalConfig)
            {
                configSettings[kv.Key] = kv.Value;
            }
        }

        var configuration = new ConfigurationBuilder().AddInMemoryCollection(configSettings).Build();
        var configService = new SystemConfigurationService(factory, configuration, audit);

        var httpFactoryMock = new Mock<IHttpClientFactory>();
        var handlerMock = new Mock<HttpMessageHandler>();
        handlerMock
            .Protected()
            .Setup<Task<HttpResponseMessage>>(
                "SendAsync",
                ItExpr.IsAny<HttpRequestMessage>(),
                ItExpr.IsAny<CancellationToken>())
            .ReturnsAsync(new HttpResponseMessage
            {
                StatusCode = HttpStatusCode.OK,
                Content = new StringContent("{\"keys\":[]}")
            });

        httpFactoryMock.Setup(f => f.CreateClient(It.IsAny<string>()))
            .Returns(() => new HttpClient(handlerMock.Object, disposeHandler: false));

        var logger = NullLogger<EntraResilienceService>.Instance;
        var service = new EntraResilienceService(factory, audit, configService, httpFactoryMock.Object, logger);

        return (factory, db, audit, configService, service, httpFactoryMock);
    }

    private static async Task<ApplicationUser> CreateTestEmployeeAsync(
        NsdmsDbContext db,
        string username = "employee@merseta.org.za",
        bool isEntraUser = true,
        bool entraEnabled = true,
        bool isLocalActive = true,
        DateTime? lastSync = null,
        bool explicitNullSync = false)
    {
        var user = new ApplicationUser
        {
            UserName = username,
            NormalizedUserName = username.ToUpperInvariant(),
            Email = username,
            NormalizedEmail = username.ToUpperInvariant(),
            SecurityStamp = Guid.NewGuid().ToString(),
            IsActive = isLocalActive,
            IsEntraUser = isEntraUser,
            EntraObjectId = Guid.NewGuid().ToString(),
            EntraUserPrincipalName = username,
            EntraAccountEnabled = entraEnabled,
            LastEntraSyncUtc = explicitNullSync ? null : (lastSync ?? DateTime.UtcNow)
        };

        db.Users.Add(user);
        await db.SaveChangesAsync();
        return user;
    }

    /// <summary>
    /// Generates an ASP.NET Identity V2 formatted password hash (PBKDF2 HMAC-SHA1 with header 0x00)
    /// to test automatic re-hashing migration to modern Identity V3 format.
    /// </summary>
    private static string GenerateV2PasswordHash(string password)
    {
        byte[] salt = new byte[16];
        using (var rng = RandomNumberGenerator.Create())
        {
            rng.GetBytes(salt);
        }

        byte[] subkey = Rfc2898DeriveBytes.Pbkdf2(password, salt, 1000, HashAlgorithmName.SHA1, 32);

        byte[] output = new byte[1 + 16 + 32];
        output[0] = 0x00; // Identity V2 header byte
        Buffer.BlockCopy(salt, 0, output, 1, 16);
        Buffer.BlockCopy(subkey, 0, output, 17, 32);
        return Convert.ToBase64String(output);
    }

    [Fact]
    public async Task SetBackupPasswordAsync_EnforcesComplexityRules()
    {
        var (factory, db, audit, config, service, _) = CreateTestContext();
        var user = await CreateTestEmployeeAsync(db);

        // Too short (< 8 chars)
        var (s1, err1) = await service.SetBackupPasswordAsync(user.Id, "Short1!", "admin");
        Assert.False(s1);
        Assert.Contains("at least 8 characters", err1);

        // Missing uppercase
        var (s2, err2) = await service.SetBackupPasswordAsync(user.Id, "nouppercase123!", "admin");
        Assert.False(s2);
        Assert.Contains("uppercase letter", err2);

        // Missing lowercase
        var (s3, err3) = await service.SetBackupPasswordAsync(user.Id, "NOLOWERCASE123!", "admin");
        Assert.False(s3);
        Assert.Contains("lowercase letter", err3);

        // Missing digit
        var (s4, err4) = await service.SetBackupPasswordAsync(user.Id, "NoDigitsHere!!", "admin");
        Assert.False(s4);
        Assert.Contains("digit", err4);

        // Missing special char
        var (s5, err5) = await service.SetBackupPasswordAsync(user.Id, "NoSpecialChar123", "admin");
        Assert.False(s5);
        Assert.Contains("special character", err5);

        // Valid complex password
        var (sValid, errValid) = await service.SetBackupPasswordAsync(user.Id, "Valid@P@ssw0rd2026", "admin");
        Assert.True(sValid);
        Assert.Null(errValid);

        // Verify updated entity
        var updated = await db.Users.AsNoTracking().FirstOrDefaultAsync(u => u.Id == user.Id);
        Assert.NotNull(updated);
        Assert.NotNull(updated!.BackupPasswordHash);
        Assert.NotNull(updated.BackupPasswordSetAt);
        Assert.False(updated.BackupPasswordMustChange);

        // Verify audit log
        var auditEntry = await db.AuditLogs.AsNoTracking().FirstOrDefaultAsync(a =>
            a.EntityName == "ApplicationUser" &&
            a.ActionName == "SetBackupPassword" &&
            a.RecordId == user.Id);
        Assert.NotNull(auditEntry);
    }

    [Fact]
    public async Task ValidateBackupCredentialsAsync_ActiveUser_CorrectPassword_Succeeds()
    {
        var (factory, db, audit, config, service, _) = CreateTestContext();
        var user = await CreateTestEmployeeAsync(db, "sipho@merseta.org.za");

        // Set backup password
        var (setSuccess, _) = await service.SetBackupPasswordAsync(user.Id, "Sipho#Emergency2026", "sipho");
        Assert.True(setSuccess);

        // Validate correct credentials
        var result = await service.ValidateBackupCredentialsAsync("sipho@merseta.org.za", "Sipho#Emergency2026", "10.0.0.1", "Mozilla/5.0");

        Assert.True(result.Succeeded);
        Assert.NotNull(result.User);
        Assert.Equal(user.Id, result.User!.Id);
        Assert.False(result.IsLockedOut);
        Assert.False(result.IsEntraDisabled);
        Assert.False(result.IsLocalInactive);
        Assert.False(result.IsGracePeriodExceeded);

        // Verify login timestamp and zero failed attempts
        var reloaded = await db.Users.AsNoTracking().FirstOrDefaultAsync(u => u.Id == user.Id);
        Assert.NotNull(reloaded!.LastBackupPasswordLoginUtc);
        Assert.Equal(0, reloaded.BackupPasswordFailedAttempts);

        // Verify audit log
        var auditEntry = await db.AuditLogs.AsNoTracking().FirstOrDefaultAsync(a =>
            a.EntityName == "ApplicationUser" &&
            a.ActionName == "EmergencyBackupPasswordLogin" &&
            a.RecordId == user.Id);
        Assert.NotNull(auditEntry);
    }

    [Fact]
    public async Task ValidateBackupCredentialsAsync_CRITICAL_INVARIANT_RejectsIfEntraAccountDisabled()
    {
        // Core Governance Invariant:
        // Staff must be able to log in even if Microsoft Entra ID is down,
        // AS LONG AS their account was NOT disabled in Entra prior to the outage.
        var (factory, db, audit, config, service, _) = CreateTestContext();
        var user = await CreateTestEmployeeAsync(db, "terminated.staff@merseta.org.za", entraEnabled: false);

        // Set valid backup password
        await service.SetBackupPasswordAsync(user.Id, "OldPassword#2026", "admin");

        // Attempt login with correct backup password
        var result = await service.ValidateBackupCredentialsAsync(
            "terminated.staff@merseta.org.za",
            "OldPassword#2026",
            "192.168.1.50",
            "Chrome");

        Assert.False(result.Succeeded);
        Assert.True(result.IsEntraDisabled);
        Assert.Contains("Microsoft Entra", result.ErrorMessage);
        Assert.Contains("disabled", result.ErrorMessage);

        // Verify rejected audit log
        var auditEntry = await db.AuditLogs.AsNoTracking().FirstOrDefaultAsync(a =>
            a.EntityName == "ApplicationUser" &&
            a.ActionName == "DeniedEmergencyLogin_EntraAccountDisabled" &&
            a.RecordId == user.Id);
        Assert.NotNull(auditEntry);
    }

    [Fact]
    public async Task ValidateBackupCredentialsAsync_RejectsIfLocallyInactive()
    {
        var (factory, db, audit, config, service, _) = CreateTestContext();
        var user = await CreateTestEmployeeAsync(db, "inactive@merseta.org.za", isLocalActive: false);

        await service.SetBackupPasswordAsync(user.Id, "SecureBackup#123", "admin");

        var result = await service.ValidateBackupCredentialsAsync("inactive@merseta.org.za", "SecureBackup#123");

        Assert.False(result.Succeeded);
        Assert.True(result.IsLocalInactive);
        Assert.Contains("deactivated", result.ErrorMessage);
    }

    [Fact]
    public async Task ValidateBackupCredentialsAsync_RejectsIfGracePeriodExceeded()
    {
        var (factory, db, audit, config, service, _) = CreateTestContext();

        // Last Entra sync was 25 days ago (grace period is 14 days)
        var staleSyncDate = DateTime.UtcNow.AddDays(-25);
        var user = await CreateTestEmployeeAsync(db, "stale@merseta.org.za", lastSync: staleSyncDate);

        await service.SetBackupPasswordAsync(user.Id, "StaleAccount#2026", "admin");

        var result = await service.ValidateBackupCredentialsAsync("stale@merseta.org.za", "StaleAccount#2026");

        Assert.False(result.Succeeded);
        Assert.True(result.IsGracePeriodExceeded);
        Assert.Contains("grace window", result.ErrorMessage);
    }

    [Fact]
    public async Task ValidateBackupCredentialsAsync_LocksOutAfterMaxFailedAttempts_AndCanBeReset()
    {
        var (factory, db, audit, config, service, _) = CreateTestContext();
        var user = await CreateTestEmployeeAsync(db, "lockout.test@merseta.org.za");

        await service.SetBackupPasswordAsync(user.Id, "CorrectPass#2026", "admin");

        // Attempt 1-4 with wrong password
        for (int i = 1; i <= 4; i++)
        {
            var res = await service.ValidateBackupCredentialsAsync("lockout.test@merseta.org.za", "WrongPassword!");
            Assert.False(res.Succeeded);
            Assert.False(res.IsLockedOut);
        }

        // Attempt 5 -> triggers lockout
        var res5 = await service.ValidateBackupCredentialsAsync("lockout.test@merseta.org.za", "WrongPassword!");
        Assert.False(res5.Succeeded);
        Assert.True(res5.IsLockedOut);
        Assert.NotNull(res5.LockoutEnd);

        // Attempt with CORRECT password while locked out is still rejected
        var resWhileLocked = await service.ValidateBackupCredentialsAsync("lockout.test@merseta.org.za", "CorrectPass#2026");
        Assert.False(resWhileLocked.Succeeded);
        Assert.True(resWhileLocked.IsLockedOut);

        // Reset lockout by administrator
        var resetSuccess = await service.ResetBackupPasswordLockoutAsync(user.Id, "secops_admin");
        Assert.True(resetSuccess);

        // Login with correct password now succeeds
        var resAfterReset = await service.ValidateBackupCredentialsAsync("lockout.test@merseta.org.za", "CorrectPass#2026");
        Assert.True(resAfterReset.Succeeded);
        Assert.False(resAfterReset.IsLockedOut);
    }

    [Fact]
    public async Task EmergencyOutageOverride_EnablesAndDisablesContingencyMode()
    {
        var (factory, db, audit, config, service, _) = CreateTestContext();

        // Initially inactive
        var initiallyActive = await service.IsEmergencyOutageModeActiveAsync();
        Assert.False(initiallyActive);

        // SecOps activates manual outage override
        await service.SetEmergencyOutageOverrideAsync(true, "Simulated undersea cable cut drill", "secops_lead");

        var activeAfterToggle = await service.IsEmergencyOutageModeActiveAsync();
        Assert.True(activeAfterToggle);

        var healthStatus = await service.CheckEntraServiceHealthAsync(forceProbe: true);
        Assert.Equal(EntraHealthStatus.ManualOverride, healthStatus);

        // Verify audit log for override activation
        var auditEntry = await db.AuditLogs.AsNoTracking().FirstOrDefaultAsync(a =>
            a.EntityName == "SystemConfiguration" &&
            a.ActionName == "ActivateEmergencyOutageOverride");
        Assert.NotNull(auditEntry);

        // Release manual override
        await service.SetEmergencyOutageOverrideAsync(false, "Drill finished, restored primary", "secops_lead");

        var activeAfterRelease = await service.IsEmergencyOutageModeActiveAsync();
        Assert.False(activeAfterRelease);
    }

    [Fact]
    public async Task SyncEntraAccountStatusAsync_UpdatesCachedStatusAndDoubleWritesAudit()
    {
        var (factory, db, audit, config, service, _) = CreateTestContext();
        var user = await CreateTestEmployeeAsync(db, "sync.test@merseta.org.za", entraEnabled: true);

        // Sync Entra disabled status
        var syncResult = await service.SyncEntraAccountStatusAsync(user.Id, false, "graph_sync_job");
        Assert.True(syncResult);

        var reloaded = await db.Users.AsNoTracking().FirstOrDefaultAsync(u => u.Id == user.Id);
        Assert.False(reloaded!.EntraAccountEnabled);
        Assert.NotNull(reloaded.LastEntraSyncUtc);

        var auditLog = await db.AuditLogs.AsNoTracking().FirstOrDefaultAsync(a =>
            a.EntityName == "ApplicationUser" &&
            a.ActionName == "SyncEntraAccountStatus" &&
            a.RecordId == user.Id);
        Assert.NotNull(auditLog);
    }

    [Fact]
    public async Task SyncAllEntraAccountsBatchAsync_SynchronizesInternalStaff()
    {
        var (factory, db, audit, config, service, _) = CreateTestContext();

        await CreateTestEmployeeAsync(db, "staff1@merseta.org.za");
        await CreateTestEmployeeAsync(db, "staff2@merseta.org.za");
        await CreateTestEmployeeAsync(db, "staff3@merseta.org.za");

        var processedCount = await service.SyncAllEntraAccountsBatchAsync("NIGHTLY_SYNC_AGENT");
        Assert.True(processedCount >= 3);

        var auditLog = await db.AuditLogs.AsNoTracking().FirstOrDefaultAsync(a =>
            a.EntityName == "ApplicationUser" &&
            a.ActionName == "BatchSyncEntraAccounts");
        Assert.NotNull(auditLog);
    }

    [Fact]
    public async Task GetUserEntraStatusAsync_CalculatesGracePeriodRemainingCorrectly()
    {
        var (factory, db, audit, config, service, _) = CreateTestContext();

        // 4 days ago
        var fourDaysAgo = DateTime.UtcNow.AddDays(-4);
        var user = await CreateTestEmployeeAsync(db, "grace.calc@merseta.org.za", lastSync: fourDaysAgo);
        await service.SetBackupPasswordAsync(user.Id, "GracePeriod#123", "admin");

        var status = await service.GetUserEntraStatusAsync(user.Id);

        Assert.NotNull(status);
        Assert.True(status!.HasBackupPassword);
        Assert.True(status.IsOfflineGraceValid);
        // 14 days total - 4 days = 10 days remaining
        Assert.True(status.GracePeriodDaysRemaining >= 9 && status.GracePeriodDaysRemaining <= 10);
    }

    [Fact]
    public async Task AdminCatalog_Includes_EntraResilience_Module()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var audit = new AuditService(factory);
        var inMemorySettings = new Dictionary<string, string?>
        {
            { "General:AppVersion", "2026.8.0" }
        };
        var config = new ConfigurationBuilder().AddInMemoryCollection(inMemorySettings).Build();
        var configService = new SystemConfigurationService(factory, config, audit);
        var flagService = new FeatureFlagService(factory, config, audit);
        var lookupService = new LookupService(factory, audit);
        var roleService = new RolePermissionService(factory, audit);

        var adminService = new AdminCatalogService(
            factory,
            configService,
            flagService,
            lookupService,
            roleService,
            audit);

        var index = await adminService.GetCatalogIndexAsync();

        var entraModule = index.AllItems.Find(m => m.Key == "MOD-ENTRA-RESILIENCE");
        Assert.NotNull(entraModule);
        Assert.Equal("/admin/entra-resilience", entraModule!.RouteUrl);
        Assert.Equal("Security & Access Control", entraModule.Category);
    }

    [Fact]
    public async Task ValidateBackupCredentialsAsync_NonExistentUsername_AntiTimingProtection_ReturnsGenericErrorWithoutLeakingAccountExistence()
    {
        var (factory, db, audit, config, service, _) = CreateTestContext();

        // Non-existent user query
        var result = await service.ValidateBackupCredentialsAsync(
            "nonexistent.user@merseta.org.za",
            "AnyPassword#2026",
            "127.0.0.1",
            "Mozilla/5.0");

        // Asserts generic error - does NOT reveal account existence
        Assert.False(result.Succeeded);
        Assert.Null(result.User);
        Assert.Equal("Invalid username/email or emergency backup password.", result.ErrorMessage);
        Assert.False(result.IsLockedOut);
        Assert.False(result.IsEntraDisabled);
        Assert.False(result.IsLocalInactive);
        Assert.False(result.IsGracePeriodExceeded);
        Assert.False(result.RequiresPasswordSetup);
    }

    [Theory]
    [InlineData("", "Valid#Password2026")]
    [InlineData("   ", "Valid#Password2026")]
    [InlineData("user@merseta.org.za", "")]
    [InlineData("user@merseta.org.za", "   ")]
    [InlineData("", "")]
    [InlineData("   ", "   ")]
    [InlineData(null, "Valid#Password2026")]
    [InlineData("user@merseta.org.za", null)]
    [InlineData(null, null)]
    public async Task ValidateBackupCredentialsAsync_EmptyOrWhitespaceInputs_ReturnsValidationError(string? username, string? password)
    {
        var (factory, db, audit, config, service, _) = CreateTestContext();

        var result = await service.ValidateBackupCredentialsAsync(username!, password!);

        Assert.False(result.Succeeded);
        Assert.Null(result.User);
        Assert.Equal("Email address and emergency backup password are required.", result.ErrorMessage);
        Assert.False(result.IsLockedOut);
        Assert.False(result.IsEntraDisabled);
        Assert.False(result.IsLocalInactive);
        Assert.False(result.IsGracePeriodExceeded);
        Assert.False(result.RequiresPasswordSetup);
    }

    [Fact]
    public async Task ValidateBackupCredentialsAsync_GracePeriod_BoundaryTesting_13Days23Hours_Valid_Vs_14Days1Hour_Expired()
    {
        var (factory, db, audit, config, service, _) = CreateTestContext();

        // 1. User with sync 13 days, 23 hours ago (within 14 days grace period)
        var validSyncDate = DateTime.UtcNow.AddDays(-13).AddHours(-23);
        var validUser = await CreateTestEmployeeAsync(db, "valid.grace@merseta.org.za", lastSync: validSyncDate);
        await service.SetBackupPasswordAsync(validUser.Id, "ValidGrace#2026", "admin");

        var validResult = await service.ValidateBackupCredentialsAsync("valid.grace@merseta.org.za", "ValidGrace#2026");
        Assert.True(validResult.Succeeded);
        Assert.False(validResult.IsGracePeriodExceeded);

        var validStatus = await service.GetUserEntraStatusAsync(validUser.Id);
        Assert.NotNull(validStatus);
        Assert.True(validStatus!.IsOfflineGraceValid);
        Assert.True(validStatus.GracePeriodDaysRemaining >= 1);

        // 2. User with sync 14 days, 1 hour ago (exceeds 14 days grace period)
        var expiredSyncDate = DateTime.UtcNow.AddDays(-14).AddHours(-1);
        var expiredUser = await CreateTestEmployeeAsync(db, "expired.grace@merseta.org.za", lastSync: expiredSyncDate);
        await service.SetBackupPasswordAsync(expiredUser.Id, "ExpiredGrace#2026", "admin");

        var expiredResult = await service.ValidateBackupCredentialsAsync("expired.grace@merseta.org.za", "ExpiredGrace#2026");
        Assert.False(expiredResult.Succeeded);
        Assert.True(expiredResult.IsGracePeriodExceeded);
        Assert.Contains("grace window (14 days) has expired", expiredResult.ErrorMessage);

        var expiredStatus = await service.GetUserEntraStatusAsync(expiredUser.Id);
        Assert.NotNull(expiredStatus);
        Assert.False(expiredStatus!.IsOfflineGraceValid);
        Assert.Equal(0, expiredStatus.GracePeriodDaysRemaining);
    }

    [Fact]
    public async Task ValidateBackupCredentialsAsync_GracePeriod_ExactBoundary_14DaysExactly_Valid_Vs_14DaysPlusOneSecond_Expired()
    {
        var (factory, db, audit, config, service, _) = CreateTestContext();

        // 1. User just within the 14-day threshold (14 days minus 10 seconds to ensure safety under execution)
        var justWithin14Days = DateTime.UtcNow - TimeSpan.FromDays(14) + TimeSpan.FromSeconds(10);
        var withinUser = await CreateTestEmployeeAsync(db, "within.exact@merseta.org.za", lastSync: justWithin14Days);
        await service.SetBackupPasswordAsync(withinUser.Id, "ExactBoundary#2026", "admin");

        var withinResult = await service.ValidateBackupCredentialsAsync("within.exact@merseta.org.za", "ExactBoundary#2026");
        Assert.True(withinResult.Succeeded);
        Assert.False(withinResult.IsGracePeriodExceeded);

        // 2. User just past the 14-day threshold (14 days plus 5 seconds)
        var justPast14Days = DateTime.UtcNow - TimeSpan.FromDays(14) - TimeSpan.FromSeconds(5);
        var pastUser = await CreateTestEmployeeAsync(db, "past.exact@merseta.org.za", lastSync: justPast14Days);
        await service.SetBackupPasswordAsync(pastUser.Id, "ExactBoundary#2026", "admin");

        var pastResult = await service.ValidateBackupCredentialsAsync("past.exact@merseta.org.za", "ExactBoundary#2026");
        Assert.False(pastResult.Succeeded);
        Assert.True(pastResult.IsGracePeriodExceeded);
        Assert.Contains("grace window", pastResult.ErrorMessage);
    }

    [Fact]
    public async Task GetUserEntraStatusAsync_NullLastEntraSyncUtc_CalculatesStatusGracefully()
    {
        var (factory, db, audit, config, service, _) = CreateTestContext();

        // Employee whose LastEntraSyncUtc has never been set (null)
        var user = await CreateTestEmployeeAsync(db, "nullsync@merseta.org.za", explicitNullSync: true);
        await service.SetBackupPasswordAsync(user.Id, "NullSync#Pass2026", "admin");

        // Verify GetUserEntraStatusAsync
        var status = await service.GetUserEntraStatusAsync(user.Id);
        Assert.NotNull(status);
        Assert.Null(status!.LastEntraSyncUtc);
        Assert.True(status.IsOfflineGraceValid);
        Assert.Equal(0, status.GracePeriodDaysRemaining);
        Assert.True(status.HasBackupPassword);

        // Verify GetAllEntraUsersStatusAsync
        var allStatuses = await service.GetAllEntraUsersStatusAsync("nullsync");
        var userInList = allStatuses.FirstOrDefault(u => u.UserId == user.Id);
        Assert.NotNull(userInList);
        Assert.Null(userInList!.LastEntraSyncUtc);
        Assert.True(userInList.IsOfflineGraceValid);
        Assert.Equal(0, userInList.GracePeriodDaysRemaining);

        // Verify ValidateBackupCredentialsAsync succeeds without grace period block
        var authResult = await service.ValidateBackupCredentialsAsync("nullsync@merseta.org.za", "NullSync#Pass2026");
        Assert.True(authResult.Succeeded);
        Assert.False(authResult.IsGracePeriodExceeded);
    }

    [Fact]
    public async Task ValidateBackupCredentialsAsync_DeprecatedV2Hash_UpgradesAndRehashesToV3Automatically()
    {
        var (factory, db, audit, config, service, _) = CreateTestContext();
        var user = await CreateTestEmployeeAsync(db, "legacy.hash@merseta.org.za");

        // Generate an Identity V2 format password hash (PBKDF2 HMAC-SHA1 with format byte 0x00)
        var password = "Legacy#UpgradePassword2026";
        var legacyV2Hash = GenerateV2PasswordHash(password);

        // Store deprecated V2 hash directly
        user.BackupPasswordHash = legacyV2Hash;
        user.BackupPasswordSetAt = DateTime.UtcNow.AddMonths(-6);
        await db.SaveChangesAsync();

        // Verify that PasswordHasher initially evaluates this as SuccessRehashNeeded
        var hasher = new PasswordHasher<ApplicationUser>();
        var preCheck = hasher.VerifyHashedPassword(user, legacyV2Hash, password);
        Assert.Equal(PasswordVerificationResult.SuccessRehashNeeded, preCheck);

        // Authenticate with backup credentials
        var result = await service.ValidateBackupCredentialsAsync("legacy.hash@merseta.org.za", password);
        Assert.True(result.Succeeded);
        Assert.NotNull(result.User);

        // Reload user from DB and verify hash was upgraded
        var reloaded = await db.Users.AsNoTracking().FirstOrDefaultAsync(u => u.Id == user.Id);
        Assert.NotNull(reloaded);
        Assert.NotNull(reloaded!.BackupPasswordHash);
        Assert.NotEqual(legacyV2Hash, reloaded.BackupPasswordHash);

        // Verify the upgraded hash is in modern V3 format (starts with 0x01)
        var upgradedBytes = Convert.FromBase64String(reloaded.BackupPasswordHash);
        Assert.Equal(0x01, upgradedBytes[0]);

        // Verify that subsequent evaluation is cleanly PasswordVerificationResult.Success without needing rehash
        var postCheck = hasher.VerifyHashedPassword(reloaded, reloaded.BackupPasswordHash, password);
        Assert.Equal(PasswordVerificationResult.Success, postCheck);
    }

    [Fact]
    public async Task ValidateBackupCredentialsAsync_MultiRoleAndPermissionClaimAssignment_AssignsAllClaimsCorrectly()
    {
        var (factory, db, audit, config, service, _) = CreateTestContext();
        var identityService = new IdentityService(factory, audit);
        var roleService = new RolePermissionService(factory, audit);

        // 1. Create Roles and assign permissions
        var cloRole = new ApplicationRole { Name = "CLO", NormalizedName = "CLO", Active = true, Description = "Client Liaison Officer" };
        var finRole = new ApplicationRole { Name = "FinanceManager", NormalizedName = "FINANCEMANAGER", Active = true, Description = "Finance Manager" };
        db.Roles.AddRange(cloRole, finRole);
        await db.SaveChangesAsync();

        db.RoleClaims.AddRange(
            new IdentityRoleClaim<int> { RoleId = cloRole.Id, ClaimType = RolePermissionService.PermissionClaimType, ClaimValue = "Workplace:View" },
            new IdentityRoleClaim<int> { RoleId = cloRole.Id, ClaimType = RolePermissionService.PermissionClaimType, ClaimValue = "Workplace:Verify" },
            new IdentityRoleClaim<int> { RoleId = finRole.Id, ClaimType = RolePermissionService.PermissionClaimType, ClaimValue = "Finance:Disburse" },
            new IdentityRoleClaim<int> { RoleId = finRole.Id, ClaimType = RolePermissionService.PermissionClaimType, ClaimValue = "Finance:Approve" }
        );
        await db.SaveChangesAsync();

        // 2. Create User and assign both roles
        var user = await CreateTestEmployeeAsync(db, "multirole.staff@merseta.org.za");
        db.UserRoles.AddRange(
            new IdentityUserRole<int> { UserId = user.Id, RoleId = cloRole.Id },
            new IdentityUserRole<int> { UserId = user.Id, RoleId = finRole.Id }
        );
        await db.SaveChangesAsync();

        // 3. Add a user-specific permission override
        await roleService.SetUserPermissionOverrideAsync(user.Id, "Grants:SpecialApproval", isGranted: true, "Acting delegation", "SecOpsAdmin");

        // 4. Enroll backup password
        var (setSuccess, _) = await service.SetBackupPasswordAsync(user.Id, "MultiRole#Emergency2026", "admin");
        Assert.True(setSuccess);

        // 5. Authenticate via backup credentials
        var authResult = await service.ValidateBackupCredentialsAsync("multirole.staff@merseta.org.za", "MultiRole#Emergency2026", "10.0.0.5", "Mozilla/5.0");
        Assert.True(authResult.Succeeded);
        Assert.NotNull(authResult.User);

        // 6. Build ClaimsPrincipal matching AuthEndpoints.cs logic
        var authUser = authResult.User!;
        var roles = await identityService.GetUserRolesAsync(authUser.Id);
        var userPermissions = await roleService.GetUserPermissionsAsync(authUser.Id);

        var claims = new List<Claim>
        {
            new(ClaimTypes.Name, authUser.UserName ?? authUser.Email ?? "User"),
            new(ClaimTypes.Email, authUser.Email ?? string.Empty),
            new(ClaimTypes.NameIdentifier, authUser.Id.ToString()),
            new("AuthMethod", "EmergencyBackupPassword")
        };

        foreach (var role in roles)
        {
            claims.Add(new Claim(ClaimTypes.Role, role));
        }

        foreach (var perm in userPermissions)
        {
            claims.Add(new Claim("Permission", perm));
        }

        var identity = new ClaimsIdentity(claims, "EmergencyAuthCookie");
        var principal = new ClaimsPrincipal(identity);

        // 7. Verify multi-role assignment
        Assert.Contains("CLO", roles);
        Assert.Contains("FinanceManager", roles);
        Assert.True(principal.IsInRole("CLO"));
        Assert.True(principal.IsInRole("FinanceManager"));

        // 8. Verify permission claim assignment from both roles and user-override
        Assert.Contains("Workplace:View", userPermissions);
        Assert.Contains("Workplace:Verify", userPermissions);
        Assert.Contains("Finance:Disburse", userPermissions);
        Assert.Contains("Finance:Approve", userPermissions);
        Assert.Contains("Grants:SpecialApproval", userPermissions);

        Assert.True(principal.HasClaim("Permission", "Workplace:View"));
        Assert.True(principal.HasClaim("Permission", "Workplace:Verify"));
        Assert.True(principal.HasClaim("Permission", "Finance:Disburse"));
        Assert.True(principal.HasClaim("Permission", "Finance:Approve"));
        Assert.True(principal.HasClaim("Permission", "Grants:SpecialApproval"));

        // 9. Verify emergency authentication method claim
        Assert.True(principal.HasClaim("AuthMethod", "EmergencyBackupPassword"));
    }

    [Fact]
    public async Task ValidateBackupCredentialsAsync_UnenrolledUser_ReturnsRequiresPasswordSetup()
    {
        var (factory, db, audit, config, service, _) = CreateTestContext();
        var user = await CreateTestEmployeeAsync(db, "unenrolled@merseta.org.za");

        var result = await service.ValidateBackupCredentialsAsync("unenrolled@merseta.org.za", "SomePass#123");
        Assert.False(result.Succeeded);
        Assert.True(result.RequiresPasswordSetup);
        Assert.Contains("No disaster recovery backup password has been configured", result.ErrorMessage);
    }

    [Fact]
    public async Task ResetBackupPasswordLockoutAsync_NonExistentUser_ReturnsFalse()
    {
        var (factory, db, audit, config, service, _) = CreateTestContext();
        var reset = await service.ResetBackupPasswordLockoutAsync(999999, "admin");
        Assert.False(reset);
    }

    [Fact]
    public async Task CheckEntraServiceHealthAsync_OutageDetected_WhenProbeReturnsHttpError()
    {
        var (factory, db, audit, config, _, _) = CreateTestContext();

        var httpFactoryMock = new Mock<IHttpClientFactory>();
        var handlerMock = new Mock<HttpMessageHandler>();
        handlerMock
            .Protected()
            .Setup<Task<HttpResponseMessage>>(
                "SendAsync",
                ItExpr.IsAny<HttpRequestMessage>(),
                ItExpr.IsAny<CancellationToken>())
            .ReturnsAsync(new HttpResponseMessage
            {
                StatusCode = HttpStatusCode.BadGateway
            });

        httpFactoryMock.Setup(f => f.CreateClient(It.IsAny<string>()))
            .Returns(() => new HttpClient(handlerMock.Object, disposeHandler: false));

        var service = new EntraResilienceService(factory, audit, config, httpFactoryMock.Object, NullLogger<EntraResilienceService>.Instance);

        var status = await service.CheckEntraServiceHealthAsync(forceProbe: true);
        Assert.Equal(EntraHealthStatus.OutageDetected, status);
    }

    [Fact]
    public async Task CheckEntraServiceHealthAsync_OutageDetected_WhenProbeThrowsException()
    {
        var (factory, db, audit, config, _, _) = CreateTestContext();

        var httpFactoryMock = new Mock<IHttpClientFactory>();
        var handlerMock = new Mock<HttpMessageHandler>();
        handlerMock
            .Protected()
            .Setup<Task<HttpResponseMessage>>(
                "SendAsync",
                ItExpr.IsAny<HttpRequestMessage>(),
                ItExpr.IsAny<CancellationToken>())
            .ThrowsAsync(new HttpRequestException("Connection refused"));

        httpFactoryMock.Setup(f => f.CreateClient(It.IsAny<string>()))
            .Returns(() => new HttpClient(handlerMock.Object, disposeHandler: false));

        var service = new EntraResilienceService(factory, audit, config, httpFactoryMock.Object, NullLogger<EntraResilienceService>.Instance);

        var status = await service.CheckEntraServiceHealthAsync(forceProbe: true);
        Assert.Equal(EntraHealthStatus.OutageDetected, status);
    }

    [Fact]
    public async Task GetAllEntraUsersStatusAsync_SearchFiltering_MatchesEmailUserNameAndPersonName()
    {
        var (factory, db, audit, config, service, _) = CreateTestContext();

        var person1 = new Person { FirstName = "Nandi", LastName = "Dlamini", RsaIdNumber = "9001010001081" };
        var person2 = new Person { FirstName = "Kagiso", LastName = "Mokoena", RsaIdNumber = "9102020002082" };
        db.People.AddRange(person1, person2);
        await db.SaveChangesAsync();

        var u1 = await CreateTestEmployeeAsync(db, "nandi.dlamini@merseta.org.za");
        u1.PersonId = person1.Id;
        var u2 = await CreateTestEmployeeAsync(db, "kagiso.mokoena@merseta.org.za");
        u2.PersonId = person2.Id;
        await db.SaveChangesAsync();

        var resultDlamini = await service.GetAllEntraUsersStatusAsync("Dlamini");
        Assert.Contains(resultDlamini, u => u.Email == "nandi.dlamini@merseta.org.za");
        Assert.DoesNotContain(resultDlamini, u => u.Email == "kagiso.mokoena@merseta.org.za");

        var resultKagiso = await service.GetAllEntraUsersStatusAsync("kagiso");
        Assert.Contains(resultKagiso, u => u.Email == "kagiso.mokoena@merseta.org.za");
        Assert.DoesNotContain(resultKagiso, u => u.Email == "nandi.dlamini@merseta.org.za");
    }

    [Fact]
    public async Task ValidateBackupCredentialsAsync_NonExistentUser_NeutralizesUsernameEnumerationWithDummyHash()
    {
        var (factory, db, audit, config, service, _) = CreateTestContext();

        // Target a completely non-existent username
        var stopwatch = System.Diagnostics.Stopwatch.StartNew();
        var result = await service.ValidateBackupCredentialsAsync("ghost.user@merseta.org.za", "SomePassword#2026");
        stopwatch.Stop();

        Assert.False(result.Succeeded);
        Assert.Null(result.User);
        Assert.Equal("Invalid username/email or emergency backup password.", result.ErrorMessage);
        Assert.False(result.IsLockedOut);
        Assert.False(result.IsEntraDisabled);
        Assert.False(result.IsLocalInactive);
        Assert.False(result.RequiresPasswordSetup);
    }

    [Fact]
    public async Task ValidateBackupCredentialsAsync_AccountWithoutBackupPassword_ReturnsRequiresSetupSafely()
    {
        var (factory, db, audit, config, service, _) = CreateTestContext();
        var user = await CreateTestEmployeeAsync(db, "no.backup@merseta.org.za");

        // Attempt authentication when BackupPasswordHash is null
        var result = await service.ValidateBackupCredentialsAsync("no.backup@merseta.org.za", "AttemptPassword#2026");

        Assert.False(result.Succeeded);
        Assert.True(result.RequiresPasswordSetup);
        Assert.Contains("No disaster recovery backup password has been configured", result.ErrorMessage);
    }

    [Fact]
    public async Task SetBackupPasswordAsync_HandlesNullOrWhitespaceSafelyWithoutException()
    {
        var (factory, db, audit, config, service, _) = CreateTestContext();
        var user = await CreateTestEmployeeAsync(db, "policy.test@merseta.org.za");

        // Test with empty string
        var (sEmpty, errEmpty) = await service.SetBackupPasswordAsync(user.Id, "", "admin");
        Assert.False(sEmpty);
        Assert.NotNull(errEmpty);
        Assert.Contains("at least 8 characters", errEmpty);

        // Test with whitespace string
        var (sSpace, errSpace) = await service.SetBackupPasswordAsync(user.Id, "        ", "admin");
        Assert.False(sSpace);
        Assert.NotNull(errSpace);
        Assert.Contains("at least 8 characters", errSpace);
    }
}
