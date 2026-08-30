using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

public class OrganisationContact : BaseEntity
{
    public int OrganisationId { get; set; }
    public Organisation? Organisation { get; set; }

    public int PersonId { get; set; }
    public Person? Person { get; set; }

    public string? ContactTypeCode { get; set; } = "General"; // e.g. Primary, SDF, Secondary, Financial, HR
    public string ContactType 
    { 
        get => ContactTypeCode ?? "General"; 
        set => ContactTypeCode = value; 
    }
    public string? Designation { get; set; }
    public bool IsPrimary { get; set; } = false;
    public bool IsActive { get; set; } = true;
}
