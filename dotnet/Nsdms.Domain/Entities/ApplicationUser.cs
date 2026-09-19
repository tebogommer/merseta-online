using Microsoft.AspNetCore.Identity;
using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Application authentication user account integrated with ASP.NET Core Identity.
/// </summary>
public class ApplicationUser : IdentityUser<int>, IAuditableEntity
{
    /// <summary>
    /// Foreign key referencing the demographic profile in the Person registry.
    /// </summary>
    public int? PersonId { get; set; }

    /// <summary>
    /// Navigational reference to the associated Person profile.
    /// </summary>
    public Person? Person { get; set; }

    /// <summary>
    /// Foreign key referencing the primary employer organisation context for external users.
    /// </summary>
    public int? DefaultOrganisationId { get; set; }

    /// <summary>
    /// Navigational reference to the default employer Organisation.
    /// </summary>
    public Organisation? DefaultOrganisation { get; set; }

    /// <summary>
    /// Indicates whether the login account is enabled and active.
    /// </summary>
    public bool IsActive { get; set; } = true;

    // --- Microsoft Entra ID Federation & Resilience Cache ---

    /// <summary>
    /// Indicates whether this user account is federated / mapped to Microsoft Entra ID (primarily merSETA internal staff).
    /// </summary>
    public bool IsEntraUser { get; set; } = false;

    /// <summary>
    /// Microsoft Entra Object ID (GUID format) identifying the account in the cloud directory.
    /// </summary>
    public string? EntraObjectId { get; set; }

    /// <summary>
    /// User Principal Name (UPN) in Microsoft Entra (e.g. employee@merseta.org.za).
    /// </summary>
    public string? EntraUserPrincipalName { get; set; }

    /// <summary>
    /// Cached account status from Microsoft Entra (true = enabled in cloud directory, false = disabled/revoked).
    /// </summary>
    public bool? EntraAccountEnabled { get; set; } = true;

    /// <summary>
    /// UTC timestamp when accountEnabled status and directory claims were last confirmed with Entra.
    /// </summary>
    public DateTime? LastEntraSyncUtc { get; set; }

    // --- Self-Service Disaster Recovery / Emergency Backup Password ---

    /// <summary>
    /// Securely hashed disaster recovery / emergency fallback password for Entra outage contingency.
    /// </summary>
    public string? BackupPasswordHash { get; set; }

    /// <summary>
    /// UTC timestamp when the disaster recovery backup password was established or last rotated.
    /// </summary>
    public DateTime? BackupPasswordSetAt { get; set; }

    /// <summary>
    /// Indicates whether the user is required to rotate or configure their emergency backup password.
    /// </summary>
    public bool BackupPasswordMustChange { get; set; } = false;

    /// <summary>
    /// UTC timestamp when the user last authenticated using their emergency backup password.
    /// </summary>
    public DateTime? LastBackupPasswordLoginUtc { get; set; }

    /// <summary>
    /// Consecutive failed login attempts specifically against the emergency backup password.
    /// </summary>
    public int BackupPasswordFailedAttempts { get; set; } = 0;

    /// <summary>
    /// Temporary lockout timestamp specifically for emergency backup password attempts.
    /// </summary>
    public DateTimeOffset? BackupPasswordLockoutEnd { get; set; }

    /// <summary>
    /// UTC timestamp when the user account was created.
    /// </summary>
    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// User identifier or service that created the account.
    /// </summary>
    public string? CreatedBy { get; set; } = "SYSTEM";

    /// <summary>
    /// UTC timestamp when the user account was last modified.
    /// </summary>
    public DateTime? ModifiedAt { get; set; }

    /// <summary>
    /// User identifier that last modified the account.
    /// </summary>
    public string? ModifiedBy { get; set; }
}
