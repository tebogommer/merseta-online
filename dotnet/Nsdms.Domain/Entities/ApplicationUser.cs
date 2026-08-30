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
