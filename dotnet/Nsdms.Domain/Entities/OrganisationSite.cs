using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

public class OrganisationSite : BaseEntity
{
    public int OrganisationId { get; set; }
    public Organisation? Organisation { get; set; }

    public string SiteName { get; set; } = string.Empty;
    public string? SiteCode { get; set; }
    public string? PhysicalAddress { get; set; }
    public string? PostalAddress { get; set; }
    public string? ProvinceCode { get; set; }
    public string? City { get; set; }
    public string? PostalCode { get; set; }

    public int? PrimaryContactPersonId { get; set; }
    public Person? PrimaryContactPerson { get; set; }

    public int? ContactPersonId
    {
        get => PrimaryContactPersonId;
        set => PrimaryContactPersonId = value;
    }
    public Person? ContactPerson
    {
        get => PrimaryContactPerson;
        set => PrimaryContactPerson = value;
    }

    public bool IsHeadOffice { get; set; } = false;
    public bool IsActive { get; set; } = true;
}
