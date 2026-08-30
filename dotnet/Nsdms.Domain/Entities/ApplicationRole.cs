using Microsoft.AspNetCore.Identity;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Application security role for CASL/RBAC authorization.
/// </summary>
public class ApplicationRole : IdentityRole<int>
{
    /// <summary>
    /// Descriptive summary of the responsibilities and permissions associated with this role.
    /// </summary>
    public string? Description { get; set; }

    /// <summary>
    /// Indicates whether the role is active in the system.
    /// </summary>
    public bool Active { get; set; } = true;

    public ApplicationRole() : base()
    {
    }

    public ApplicationRole(string roleName) : base(roleName)
    {
    }
}
