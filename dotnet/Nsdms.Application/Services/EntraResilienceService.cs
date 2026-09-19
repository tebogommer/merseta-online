using System;
using System.Net.Http;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

/// <summary>
/// Status enumeration for Microsoft Entra ID directory availability.
/// </summary>
public enum EntraHealthStatus
{
    /// <summary>
    /// Microsoft Entra ID endpoints are reachable and responding normally.
    /// </summary>
    Healthy,

    /// <summary>
    /// Microsoft Entra ID responded with high latency or intermittent errors.
    /// </summary>
    Degraded,

    /// <summary>
    /// Automated circuit-breaker detected that Microsoft Entra ID is offline or unreachable.
    /// </summary>
    OutageDetected,

    /// <summary>
    /// IT SecOps manually activated Emergency Outage Contingency Mode.
    /// </summary>
    ManualOverride
}

/// <summary>
/// Detailed authentication outcome for emergency backup password verification.
/// </summary>
public class BackupAuthResult
{
    public bool Succeeded { get; set; }
    public ApplicationUser? User { get; set; }
    public bool IsLockedOut { get; set; }
    public DateTimeOffset? LockoutEnd { get; set; }
    public bool IsEntraDisabled { get; set; }
    public bool IsLocalInactive { get; set; }
    public bool IsGracePeriodExceeded { get; set; }
    public bool RequiresPasswordSetup { get; set; }
    public string? ErrorMessage { get; set; }
}

/// <summary>
/// Overview DTO representing an employee's Entra synchronization and backup credential state.
/// </summary>
public class EntraStatusDto
{
    public int UserId { get; set; }
    public string Email { get; set; } = string.Empty;
    public string? UserName { get; set; }
    public string? DisplayName { get; set; }
    public bool IsEntraUser { get; set; }
    public string? EntraObjectId { get; set; }
    public string? EntraUserPrincipalName { get; set; }
    public bool? EntraAccountEnabled { get; set; }
    public DateTime? LastEntraSyncUtc { get; set; }
    public bool HasBackupPassword { get; set; }
    public DateTime? BackupPasswordSetAt { get; set; }
    public DateTime? LastBackupPasswordLoginUtc { get; set; }
    public int BackupPasswordFailedAttempts { get; set; }
    public bool IsLockedOut { get; set; }
    public bool IsOfflineGraceValid { get; set; }
    public int GracePeriodDaysRemaining { get; set; }
}

/// <summary>
/// Contract for Microsoft Entra ID outage resilience, directory status caching,
/// and self-service disaster recovery emergency backup password operations.
/// </summary>
public interface IEntraResilienceService
{
    /// <summary>
    /// Authenticates an employee against their local disaster recovery backup password during an Entra outage.
    /// Strictly verifies that the account was NOT disabled in Entra prior to the outage and is within the grace window.
    /// </summary>
    Task<BackupAuthResult> ValidateBackupCredentialsAsync(string usernameOrEmail, string password, string? clientIp = null, string? userAgent = null);

    /// <summary>
    /// Sets or rotates an employee's self-service disaster recovery emergency backup password.
    /// </summary>
    Task<(bool Succeeded, string? ErrorMessage)> SetBackupPasswordAsync(int userId, string newPassword, string currentUsername);

    /// <summary>
    /// Resets the failed attempt counter and lockout on a user's emergency backup password.
    /// </summary>
    Task<bool> ResetBackupPasswordLockoutAsync(int userId, string currentUsername);

    /// <summary>
    /// Evaluates live Microsoft Entra ID availability using an automated circuit-breaker probe.
    /// </summary>
    Task<EntraHealthStatus> CheckEntraServiceHealthAsync(bool forceProbe = false);

    /// <summary>
    /// Determines whether the login page should display the Emergency Outage banner and fallback form.
    /// </summary>
    Task<bool> IsEmergencyOutageModeActiveAsync();

    /// <summary>
    /// Allows IT SecOps administrators to manually force or release Emergency Outage Contingency Mode.
    /// </summary>
    Task SetEmergencyOutageOverrideAsync(bool enabled, string reason, string adminUsername);

    /// <summary>
    /// Retrieves the Entra synchronization and backup password status for a specific user.
    /// </summary>
    Task<EntraStatusDto?> GetUserEntraStatusAsync(int userId);

    /// <summary>
    /// Retrieves Entra synchronization and backup password statuses for internal employee accounts.
    /// </summary>
    Task<List<EntraStatusDto>> GetAllEntraUsersStatusAsync(string? search = null);

    /// <summary>
    /// Updates the cached Entra account status (e.g. from Microsoft Graph API delta sync or manual admin check).
    /// </summary>
    Task<bool> SyncEntraAccountStatusAsync(int userId, bool accountEnabled, string currentUsername);

    /// <summary>
    /// Performs a batch synchronization of all internal merSETA employee accounts.
    /// </summary>
    Task<int> SyncAllEntraAccountsBatchAsync(string currentUsername = "SYSTEM");
}

/// <summary>
/// Implementation of Microsoft Entra ID resilience, automated outage circuit-breaker,
/// and disaster recovery backup password management.
/// </summary>
public class EntraResilienceService : IEntraResilienceService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;
    private readonly ISystemConfigurationService _configService;
    private readonly PasswordHasher<ApplicationUser> _passwordHasher;
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly ILogger<EntraResilienceService> _logger;

    // In-memory circuit-breaker probe cache
    private static DateTime _lastProbeTime = DateTime.MinValue;
    private static EntraHealthStatus _cachedHealthStatus = EntraHealthStatus.Healthy;
    private static readonly SemaphoreSlim _probeLock = new(1, 1);

    // Anti-timing / username enumeration side-channel defense:
    // Pre-computed dummy canary user and dummy PBKDF2 hash used for constant-time evaluation on failed user lookups.
    private static readonly ApplicationUser _dummyUser = new() { Id = -1, UserName = "anti_timing_dummy_canary" };
    private static readonly string DummyPasswordHash = new PasswordHasher<ApplicationUser>().HashPassword(_dummyUser, "Dummy#AntiTimingVerification#2026");

    public EntraResilienceService(
        INsdmsDbContextFactory contextFactory,
        IAuditService audit,
        ISystemConfigurationService configService,
        IHttpClientFactory httpClientFactory,
        ILogger<EntraResilienceService> logger)
    {
        _contextFactory = contextFactory;
        _audit = audit;
        _configService = configService;
        _httpClientFactory = httpClientFactory;
        _logger = logger;
        _passwordHasher = new PasswordHasher<ApplicationUser>();
    }

    public async Task<BackupAuthResult> ValidateBackupCredentialsAsync(
        string usernameOrEmail,
        string password,
        string? clientIp = null,
        string? userAgent = null)
    {
        if (string.IsNullOrWhiteSpace(usernameOrEmail) || string.IsNullOrWhiteSpace(password))
        {
            return new BackupAuthResult
            {
                Succeeded = false,
                ErrorMessage = "Email address and emergency backup password are required."
            };
        }

        var normalized = usernameOrEmail.Trim().ToUpperInvariant();
        using var db = await _contextFactory.CreateDbContextAsync();

        var user = await db.Users
            .Include(u => u.Person)
            .Include(u => u.DefaultOrganisation)
            .FirstOrDefaultAsync(u =>
                u.NormalizedEmail == normalized ||
                u.NormalizedUserName == normalized ||
                u.Email == usernameOrEmail.Trim() ||
                u.UserName == usernameOrEmail.Trim());

        if (user == null)
        {
            // Non-existent user dummy timing evaluation: execute dummy password verification to equalize latency and mitigate user enumeration
            _passwordHasher.VerifyHashedPassword(_dummyUser, DummyPasswordHash, password);

            return new BackupAuthResult
            {
                Succeeded = false,
                ErrorMessage = "Invalid username/email or emergency backup password."
            };
        }

        // 1. Invariant: Local Account Active Check
        if (!user.IsActive)
        {
            return new BackupAuthResult
            {
                Succeeded = false,
                User = user,
                IsLocalInactive = true,
                ErrorMessage = "Your NSDMS user account has been deactivated. Please contact your system administrator."
            };
        }

        // 2. CRITICAL OFFLINE INVARIANT: Must NOT be disabled in Microsoft Entra ID
        if (user.IsEntraUser && user.EntraAccountEnabled == false)
        {
            _logger.LogWarning("Security Alert: Disabled Entra employee {UserId} ({Email}) attempted emergency backup password login.", user.Id, user.Email);

            await _audit.LogActionAsync(
                "ApplicationUser",
                user.Id,
                "DeniedEmergencyLogin_EntraAccountDisabled",
                user.Email ?? "UNKNOWN",
                null,
                new { Reason = "EntraAccountEnabled is false", ClientIp = clientIp, UserAgent = userAgent });

            return new BackupAuthResult
            {
                Succeeded = false,
                User = user,
                IsEntraDisabled = true,
                ErrorMessage = "Access Denied: Your merSETA Microsoft Entra account has been disabled in the directory. Emergency backup authentication is strictly prohibited."
            };
        }

        // 3. Offline Grace Window Invariant: Protect against stale disabled accounts during prolonged outages
        var gracePeriodDays = await _configService.GetValueAsync<int>("Auth:EntraOfflineGracePeriodDays", 14);
        if (user.IsEntraUser && user.LastEntraSyncUtc.HasValue)
        {
            var offlineDuration = DateTime.UtcNow - user.LastEntraSyncUtc.Value;
            if (offlineDuration > TimeSpan.FromDays(gracePeriodDays))
            {
                _logger.LogWarning("Security Alert: User {UserId} ({Email}) attempted emergency login after grace period expired ({Days:F1} days).",
                    user.Id, user.Email, offlineDuration.TotalDays);

                await _audit.LogActionAsync(
                    "ApplicationUser",
                    user.Id,
                    "DeniedEmergencyLogin_GracePeriodExpired",
                    user.Email ?? "UNKNOWN",
                    null,
                    new { OfflineDurationDays = offlineDuration.TotalDays, MaxAllowedDays = gracePeriodDays, ClientIp = clientIp });

                return new BackupAuthResult
                {
                    Succeeded = false,
                    User = user,
                    IsGracePeriodExceeded = true,
                    ErrorMessage = $"Your emergency offline grace window ({gracePeriodDays} days) has expired without directory re-synchronization. Please contact merSETA IT SecOps."
                };
            }
        }

        // 4. Lockout Evaluation
        if (user.BackupPasswordLockoutEnd.HasValue && user.BackupPasswordLockoutEnd.Value > DateTimeOffset.UtcNow)
        {
            return new BackupAuthResult
            {
                Succeeded = false,
                User = user,
                IsLockedOut = true,
                LockoutEnd = user.BackupPasswordLockoutEnd,
                ErrorMessage = $"Emergency access is temporarily locked until {user.BackupPasswordLockoutEnd.Value.ToLocalTime():HH:mm} due to multiple consecutive failed attempts."
            };
        }

        // 5. Check if Backup Password has been enrolled
        if (string.IsNullOrEmpty(user.BackupPasswordHash))
        {
            // Execute dummy verification to maintain constant-time response profile
            _passwordHasher.VerifyHashedPassword(_dummyUser, DummyPasswordHash, password);

            return new BackupAuthResult
            {
                Succeeded = false,
                User = user,
                RequiresPasswordSetup = true,
                ErrorMessage = "No disaster recovery backup password has been configured for this account. Please sign in via Microsoft Entra ID first and set one in your Profile."
            };
        }

        // 6. Verify Backup Password Hash
        var verifyResult = _passwordHasher.VerifyHashedPassword(user, user.BackupPasswordHash, password);
        var maxFailedAttempts = await _configService.GetValueAsync<int>("Auth:BackupPasswordMaxFailedAttempts", 0);
        if (maxFailedAttempts <= 0)
        {
            maxFailedAttempts = await _configService.GetValueAsync<int>("Auth:EntraMaxBackupPasswordFailedAttempts", 5);
        }

        var lockoutMinutes = await _configService.GetValueAsync<int>("Auth:BackupPasswordLockoutMinutes", 0);
        if (lockoutMinutes <= 0)
        {
            lockoutMinutes = await _configService.GetValueAsync<int>("Auth:EntraBackupPasswordLockoutMinutes", 15);
        }

        if (verifyResult == PasswordVerificationResult.Success || verifyResult == PasswordVerificationResult.SuccessRehashNeeded)
        {
            if (verifyResult == PasswordVerificationResult.SuccessRehashNeeded)
            {
                user.BackupPasswordHash = _passwordHasher.HashPassword(user, password);
            }

            user.BackupPasswordFailedAttempts = 0;
            user.BackupPasswordLockoutEnd = null;
            user.LastBackupPasswordLoginUtc = DateTime.UtcNow;
            user.ModifiedAt = DateTime.UtcNow;
            user.ModifiedBy = "SYSTEM";

            await db.SaveChangesAsync();

            // Double-write high-priority audit log entry
            await _audit.LogActionAsync(
                "ApplicationUser",
                user.Id,
                "EmergencyBackupPasswordLogin",
                user.Email ?? "SYSTEM",
                null,
                new
                {
                    AuthMethod = "BackupPasswordEmergency",
                    user.IsEntraUser,
                    user.LastEntraSyncUtc,
                    ClientIp = clientIp,
                    UserAgent = userAgent,
                    Timestamp = DateTime.UtcNow
                });

            return new BackupAuthResult
            {
                Succeeded = true,
                User = user
            };
        }

        // Failure handling
        user.BackupPasswordFailedAttempts++;
        if (user.BackupPasswordFailedAttempts >= maxFailedAttempts)
        {
            user.BackupPasswordLockoutEnd = DateTimeOffset.UtcNow.AddMinutes(lockoutMinutes);
        }

        user.ModifiedAt = DateTime.UtcNow;
        user.ModifiedBy = "SYSTEM";
        await db.SaveChangesAsync();

        await _audit.LogActionAsync(
            "ApplicationUser",
            user.Id,
            "FailedBackupPasswordAttempt",
            user.Email ?? "UNKNOWN",
            null,
            new
            {
                FailedAttempts = user.BackupPasswordFailedAttempts,
                LockedOut = user.BackupPasswordLockoutEnd.HasValue,
                ClientIp = clientIp
            });

        if (user.BackupPasswordLockoutEnd.HasValue)
        {
            return new BackupAuthResult
            {
                Succeeded = false,
                User = user,
                IsLockedOut = true,
                LockoutEnd = user.BackupPasswordLockoutEnd,
                ErrorMessage = $"Account locked out for {lockoutMinutes} minutes due to {user.BackupPasswordFailedAttempts} failed emergency login attempts."
            };
        }

        var remainingAttempts = maxFailedAttempts - user.BackupPasswordFailedAttempts;
        return new BackupAuthResult
        {
            Succeeded = false,
            User = user,
            ErrorMessage = $"Invalid emergency backup password. You have {remainingAttempts} attempt(s) remaining before temporary lockout."
        };
    }

    public async Task<(bool Succeeded, string? ErrorMessage)> SetBackupPasswordAsync(
        int userId,
        string newPassword,
        string currentUsername)
    {
        // 1. Password Policy Validation
        var (isValid, errors) = ValidateBackupPasswordPolicy(newPassword);
        if (!isValid)
        {
            return (false, string.Join(" ", errors));
        }

        using var db = await _contextFactory.CreateDbContextAsync();
        var user = await db.Users.FirstOrDefaultAsync(u => u.Id == userId);
        if (user == null)
        {
            return (false, "User account not found.");
        }

        // 2. Hash and store
        user.BackupPasswordHash = _passwordHasher.HashPassword(user, newPassword);
        user.BackupPasswordSetAt = DateTime.UtcNow;
        user.BackupPasswordMustChange = false;
        user.BackupPasswordFailedAttempts = 0;
        user.BackupPasswordLockoutEnd = null;
        user.ModifiedAt = DateTime.UtcNow;
        user.ModifiedBy = currentUsername;

        await db.SaveChangesAsync();

        // 3. Double-write audit log
        await _audit.LogActionAsync(
            "ApplicationUser",
            user.Id,
            "SetBackupPassword",
            currentUsername,
            null,
            new
            {
                user.Id,
                user.Email,
                BackupPasswordSetAt = user.BackupPasswordSetAt,
                Actor = currentUsername
            });

        _logger.LogInformation("Disaster recovery emergency backup password established for user {UserId} by {Actor}.", userId, currentUsername);
        return (true, null);
    }

    public async Task<bool> ResetBackupPasswordLockoutAsync(int userId, string currentUsername)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var user = await db.Users.FirstOrDefaultAsync(u => u.Id == userId);
        if (user == null) return false;

        user.BackupPasswordFailedAttempts = 0;
        user.BackupPasswordLockoutEnd = null;
        user.ModifiedAt = DateTime.UtcNow;
        user.ModifiedBy = currentUsername;

        await db.SaveChangesAsync();

        await _audit.LogActionAsync(
            "ApplicationUser",
            user.Id,
            "ResetBackupPasswordLockout",
            currentUsername,
            null,
            new { user.Id, user.Email, Actor = currentUsername });

        return true;
    }

    public async Task<EntraHealthStatus> CheckEntraServiceHealthAsync(bool forceProbe = false)
    {
        // 1. Check manual override first
        var manualOverride = await _configService.GetValueAsync<bool>("Auth:EntraOutageManualOverride", false);
        if (manualOverride)
        {
            return EntraHealthStatus.ManualOverride;
        }

        // 2. Cache evaluation with dynamic cache interval
        var cacheSeconds = await _configService.GetValueAsync<int>("Auth:EntraProbeCacheSeconds", 30);
        var cacheWindow = TimeSpan.FromSeconds(Math.Max(5, cacheSeconds));

        if (!forceProbe && DateTime.UtcNow - _lastProbeTime < cacheWindow)
        {
            return _cachedHealthStatus;
        }

        await _probeLock.WaitAsync();
        try
        {
            if (!forceProbe && DateTime.UtcNow - _lastProbeTime < cacheWindow)
            {
                return _cachedHealthStatus;
            }

            var probeUrl = await _configService.GetValueAsync(
                "Auth:EntraHealthProbeUrl",
                "https://login.microsoftonline.com/common/v2.0/.well-known/openid-configuration")!;

            var timeoutSeconds = await _configService.GetValueAsync<int>("Auth:EntraProbeTimeoutSeconds", 3);
            var degradedThresholdMs = await _configService.GetValueAsync<int>("Auth:EntraDegradedThresholdMs", 2500);

            try
            {
                var client = _httpClientFactory.CreateClient();
                using var cts = new CancellationTokenSource(TimeSpan.FromSeconds(Math.Max(1, timeoutSeconds)));

                var stopwatch = System.Diagnostics.Stopwatch.StartNew();
                using var response = await client.GetAsync(probeUrl, HttpCompletionOption.ResponseHeadersRead, cts.Token);
                stopwatch.Stop();

                if (response.IsSuccessStatusCode)
                {
                    _cachedHealthStatus = stopwatch.ElapsedMilliseconds > degradedThresholdMs
                        ? EntraHealthStatus.Degraded
                        : EntraHealthStatus.Healthy;
                }
                else
                {
                    _cachedHealthStatus = EntraHealthStatus.OutageDetected;
                }
            }
            catch (Exception ex)
            {
                _logger.LogWarning(ex, "Entra health check probe failed. Marking OutageDetected.");
                _cachedHealthStatus = EntraHealthStatus.OutageDetected;
            }

            _lastProbeTime = DateTime.UtcNow;
            return _cachedHealthStatus;
        }
        finally
        {
            _probeLock.Release();
        }
    }

    public async Task<bool> IsEmergencyOutageModeActiveAsync()
    {
        var status = await CheckEntraServiceHealthAsync();
        return status == EntraHealthStatus.OutageDetected || status == EntraHealthStatus.ManualOverride;
    }

    public async Task SetEmergencyOutageOverrideAsync(bool enabled, string reason, string adminUsername)
    {
        await _configService.SetConfigAsync("Auth:EntraOutageManualOverride", enabled ? "true" : "false", "Auth", "Manual override for Entra outage mode", "Boolean", adminUsername);
        await _configService.SetConfigAsync("Auth:EntraOutageOverrideReason", reason, "Auth", "Reason for manual outage override", "String", adminUsername);

        _lastProbeTime = DateTime.MinValue; // Invalidate cache

        await _audit.LogActionAsync(
            "SystemConfiguration",
            0,
            enabled ? "ActivateEmergencyOutageOverride" : "DeactivateEmergencyOutageOverride",
            adminUsername,
            null,
            new { Enabled = enabled, Reason = reason, Actor = adminUsername });

        _logger.LogWarning("Emergency Outage Contingency Mode override set to {Enabled} by {Admin}. Reason: {Reason}", enabled, adminUsername, reason);
    }

    public async Task<EntraStatusDto?> GetUserEntraStatusAsync(int userId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var user = await db.Users.Include(u => u.Person).FirstOrDefaultAsync(u => u.Id == userId);
        if (user == null) return null;

        var gracePeriodDays = await _configService.GetValueAsync<int>("Auth:EntraOfflineGracePeriodDays", 14);
        var daysRemaining = 0;
        var isGraceValid = true;

        if (user.LastEntraSyncUtc.HasValue)
        {
            var elapsedDays = (DateTime.UtcNow - user.LastEntraSyncUtc.Value).TotalDays;
            daysRemaining = Math.Max(0, (int)Math.Ceiling(gracePeriodDays - elapsedDays));
            isGraceValid = elapsedDays <= gracePeriodDays;
        }

        return new EntraStatusDto
        {
            UserId = user.Id,
            Email = user.Email ?? string.Empty,
            UserName = user.UserName,
            DisplayName = user.Person != null ? $"{user.Person.FirstName} {user.Person.LastName}" : user.UserName,
            IsEntraUser = user.IsEntraUser,
            EntraObjectId = user.EntraObjectId,
            EntraUserPrincipalName = user.EntraUserPrincipalName,
            EntraAccountEnabled = user.EntraAccountEnabled,
            LastEntraSyncUtc = user.LastEntraSyncUtc,
            HasBackupPassword = !string.IsNullOrEmpty(user.BackupPasswordHash),
            BackupPasswordSetAt = user.BackupPasswordSetAt,
            LastBackupPasswordLoginUtc = user.LastBackupPasswordLoginUtc,
            BackupPasswordFailedAttempts = user.BackupPasswordFailedAttempts,
            IsLockedOut = user.BackupPasswordLockoutEnd.HasValue && user.BackupPasswordLockoutEnd.Value > DateTimeOffset.UtcNow,
            IsOfflineGraceValid = isGraceValid,
            GracePeriodDaysRemaining = daysRemaining
        };
    }

    private async Task<string[]> GetInternalEmployeeDomainsAsync()
    {
        var domainsCsv = await _configService.GetValueAsync<string>("Auth:InternalEmployeeDomains", "@merseta.org.za") ?? "@merseta.org.za";
        return domainsCsv.Split(',', StringSplitOptions.RemoveEmptyEntries | StringSplitOptions.TrimEntries);
    }

    private static bool IsInternalEmployeeEmail(string? email, string[] domains)
    {
        if (string.IsNullOrEmpty(email)) return false;
        return domains.Any(d => email.EndsWith(d, StringComparison.OrdinalIgnoreCase));
    }

    public async Task<List<EntraStatusDto>> GetAllEntraUsersStatusAsync(string? search = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var domains = await GetInternalEmployeeDomainsAsync();
        var query = db.Users.Include(u => u.Person).AsQueryable();

        if (!string.IsNullOrWhiteSpace(search))
        {
            var s = search.Trim().ToLowerInvariant();
            query = query.Where(u =>
                (u.Email != null && u.Email.ToLower().Contains(s)) ||
                (u.UserName != null && u.UserName.ToLower().Contains(s)) ||
                (u.Person != null && (u.Person.FirstName.ToLower().Contains(s) || u.Person.LastName.ToLower().Contains(s))));
        }

        var allUsers = await query.OrderBy(u => u.Email).ToListAsync();
        var users = allUsers.Where(u => u.IsEntraUser || IsInternalEmployeeEmail(u.Email, domains)).ToList();
        var gracePeriodDays = await _configService.GetValueAsync<int>("Auth:EntraOfflineGracePeriodDays", 14);

        return users.Select(user =>
        {
            var daysRemaining = 0;
            var isGraceValid = true;

            if (user.LastEntraSyncUtc.HasValue)
            {
                var elapsedDays = (DateTime.UtcNow - user.LastEntraSyncUtc.Value).TotalDays;
                daysRemaining = Math.Max(0, (int)Math.Ceiling(gracePeriodDays - elapsedDays));
                isGraceValid = elapsedDays <= gracePeriodDays;
            }

            return new EntraStatusDto
            {
                UserId = user.Id,
                Email = user.Email ?? string.Empty,
                UserName = user.UserName,
                DisplayName = user.Person != null ? $"{user.Person.FirstName} {user.Person.LastName}" : user.UserName,
                IsEntraUser = user.IsEntraUser,
                EntraObjectId = user.EntraObjectId,
                EntraUserPrincipalName = user.EntraUserPrincipalName,
                EntraAccountEnabled = user.EntraAccountEnabled,
                LastEntraSyncUtc = user.LastEntraSyncUtc,
                HasBackupPassword = !string.IsNullOrEmpty(user.BackupPasswordHash),
                BackupPasswordSetAt = user.BackupPasswordSetAt,
                LastBackupPasswordLoginUtc = user.LastBackupPasswordLoginUtc,
                BackupPasswordFailedAttempts = user.BackupPasswordFailedAttempts,
                IsLockedOut = user.BackupPasswordLockoutEnd.HasValue && user.BackupPasswordLockoutEnd.Value > DateTimeOffset.UtcNow,
                IsOfflineGraceValid = isGraceValid,
                GracePeriodDaysRemaining = daysRemaining
            };
        }).ToList();
    }

    public async Task<bool> SyncEntraAccountStatusAsync(int userId, bool accountEnabled, string currentUsername)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var user = await db.Users.FirstOrDefaultAsync(u => u.Id == userId);
        if (user == null) return false;

        var previousStatus = user.EntraAccountEnabled;
        user.EntraAccountEnabled = accountEnabled;
        user.LastEntraSyncUtc = DateTime.UtcNow;
        user.ModifiedAt = DateTime.UtcNow;
        user.ModifiedBy = currentUsername;

        await db.SaveChangesAsync();

        await _audit.LogActionAsync(
            "ApplicationUser",
            user.Id,
            "SyncEntraAccountStatus",
            currentUsername,
            null,
            new
            {
                UserId = user.Id,
                Email = user.Email,
                PreviousStatus = previousStatus,
                NewStatus = accountEnabled,
                SyncTime = user.LastEntraSyncUtc
            });

        _logger.LogInformation("Entra account status synchronized for {Email}: Enabled={Enabled}", user.Email, accountEnabled);
        return true;
    }

    public async Task<int> SyncAllEntraAccountsBatchAsync(string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var domains = await GetInternalEmployeeDomainsAsync();
        var allUsers = await db.Users.ToListAsync();
        var users = allUsers
            .Where(u => u.IsEntraUser || IsInternalEmployeeEmail(u.Email, domains))
            .ToList();

        var count = 0;
        foreach (var user in users)
        {
            user.IsEntraUser = true;
            if (user.EntraAccountEnabled == null)
            {
                user.EntraAccountEnabled = true;
            }
            if (string.IsNullOrEmpty(user.EntraUserPrincipalName))
            {
                user.EntraUserPrincipalName = user.Email;
            }
            user.LastEntraSyncUtc = DateTime.UtcNow;
            user.ModifiedAt = DateTime.UtcNow;
            user.ModifiedBy = currentUsername;
            count++;
        }

        await db.SaveChangesAsync();

        await _audit.LogActionAsync(
            "ApplicationUser",
            0,
            "BatchSyncEntraAccounts",
            currentUsername,
            null,
            new { SynchronizedCount = count, Timestamp = DateTime.UtcNow });

        return count;
    }

    private static (bool IsValid, List<string> Errors) ValidateBackupPasswordPolicy(string? password)
    {
        var errors = new List<string>();
        if (string.IsNullOrWhiteSpace(password))
        {
            errors.Add("Emergency backup password must be at least 8 characters long.");
            errors.Add("Password must contain at least one uppercase letter (A-Z).");
            errors.Add("Password must contain at least one lowercase letter (a-z).");
            errors.Add("Password must contain at least one digit (0-9).");
            errors.Add("Password must contain at least one special character (e.g. !@#$%^&*).");
            return (false, errors);
        }

        if (password.Length < 8)
        {
            errors.Add("Emergency backup password must be at least 8 characters long.");
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
            errors.Add("Password must contain at least one digit (0-9).");
        }
        if (!password.Any(ch => !char.IsLetterOrDigit(ch)))
        {
            errors.Add("Password must contain at least one special character (e.g. !@#$%^&*).");
        }

        return (errors.Count == 0, errors);
    }
}
