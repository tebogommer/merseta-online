using Microsoft.AspNetCore.Identity;

namespace Nsdms.Domain.Entities;

public class ApplicationRole : IdentityRole<int>
{
    public string? Description { get; set; }
    public bool Active { get; set; } = true;

    public ApplicationRole() : base()
    {
    }

    public ApplicationRole(string roleName) : base(roleName)
    {
    }
}
