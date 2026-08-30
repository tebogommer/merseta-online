using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

public class Visit : BaseEntity
{
    public int OrganisationId { get; set; }
    public Organisation? Organisation { get; set; }

    // Mandatory contact person for visits
    public int ContactPersonId { get; set; }
    public Person? ContactPerson { get; set; }

    public string? VisitTypeCode { get; set; }
    public string? StatusCode { get; set; }

    public DateTime VisitDate { get; set; }
    public string Title { get; set; } = string.Empty;
    public string? Purpose { get; set; }
    public string? OutcomeNotes { get; set; }
    public string? Location { get; set; }
}
