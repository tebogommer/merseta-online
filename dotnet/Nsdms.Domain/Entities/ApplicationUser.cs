using Microsoft.AspNetCore.Identity;
using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

public class ApplicationUser : IdentityUser<int>, IAuditableEntity
{
    public int? PersonId { get; set; }
    public Person? Person { get; set; }

    public int? DefaultOrganisationId { get; set; }
    public Organisation? DefaultOrganisation { get; set; }

    public bool IsActive { get; set; } = true;

    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    public string? CreatedBy { get; set; } = "SYSTEM";
    public DateTime? ModifiedAt { get; set; }
    public string? ModifiedBy { get; set; }
}
