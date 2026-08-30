using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

public class WorkplaceApprovalMentor : BaseEntity
{
    public int WorkplaceApprovalId { get; set; }
    public WorkplaceApproval? WorkplaceApproval { get; set; }

    public int PersonId { get; set; }
    public Person? Person { get; set; }

    public string Designation { get; set; } = string.Empty;
    public string? ArtisanTradeNumber { get; set; }
    public int YearsExperience { get; set; }
    public bool IsCertifiedArtisan { get; set; } = true;
    public bool IsActive { get; set; } = true;
}
