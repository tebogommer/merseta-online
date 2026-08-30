using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Operational branch facility, plant, or training site belonging to an Employer Organisation,
/// supporting multi-site mapping and GPS geolocation for SETMIS File 200 reporting.
/// </summary>
public class OrganisationSite : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the parent Employer Organisation.
    /// </summary>
    public int OrganisationId { get; set; }

    /// <summary>
    /// Navigational reference to the parent Organisation.
    /// </summary>
    public Organisation? Organisation { get; set; }

    /// <summary>
    /// Commercial or operational name of the facility site.
    /// </summary>
    public string SiteName { get; set; } = string.Empty;

    /// <summary>
    /// SETMIS Statutory Site Number (e.g. 001, 002) for File 200 reporting.
    /// </summary>
    public string SiteNumber { get; set; } = "001";

    /// <summary>
    /// Internal branch or site reference code.
    /// </summary>
    public string? SiteCode { get; set; }

    /// <summary>
    /// Physical street address of the facility.
    /// </summary>
    public string? PhysicalAddress { get; set; }

    /// <summary>
    /// Postal delivery address of the branch site.
    /// </summary>
    public string? PostalAddress { get; set; }

    /// <summary>
    /// South African province code where the facility operates (references lookup.ProvinceType).
    /// </summary>
    public string? ProvinceCode { get; set; }

    /// <summary>
    /// Country code of site location (references lookup.CountryType, default ZA).
    /// </summary>
    public string CountryCode { get; set; } = "ZA";

    /// <summary>
    /// Municipality or city location of the site.
    /// </summary>
    public string? City { get; set; }

    /// <summary>
    /// Postal code of the site facility.
    /// </summary>
    public string? PostalCode { get; set; }

    #region Geolocation & Spatial Metadata (SETMIS File 200)
    /// <summary>
    /// Decimal GPS Latitude coordinate (e.g. -26.204100).
    /// </summary>
    public decimal? Latitude { get; set; }

    /// <summary>
    /// Decimal GPS Longitude coordinate (e.g. 28.047300).
    /// </summary>
    public decimal? Longitude { get; set; }

    /// <summary>
    /// Statistics South Africa Spatial Area Code (references lookup.StatssaAreaCodeType).
    /// </summary>
    public string? StatssaAreaCode { get; set; }
    #endregion

    #region Communication Details
    /// <summary>
    /// Direct site telephone number.
    /// </summary>
    public string? PhoneNumber { get; set; }

    /// <summary>
    /// Direct site fax number.
    /// </summary>
    public string? FaxNumber { get; set; }

    /// <summary>
    /// Direct site general email address.
    /// </summary>
    public string? Email { get; set; }
    #endregion

    /// <summary>
    /// Foreign key referencing the designated site manager or contact person.
    /// </summary>
    public int? PrimaryContactPersonId { get; set; }

    /// <summary>
    /// Navigational reference to the site contact person.
    /// </summary>
    public Person? PrimaryContactPerson { get; set; }

    /// <summary>
    /// Alias property referencing PrimaryContactPersonId.
    /// </summary>
    public int? ContactPersonId
    {
        get => PrimaryContactPersonId;
        set => PrimaryContactPersonId = value;
    }

    /// <summary>
    /// Alias navigation referencing PrimaryContactPerson.
    /// </summary>
    public Person? ContactPerson
    {
        get => PrimaryContactPerson;
        set => PrimaryContactPerson = value;
    }

    /// <summary>
    /// Indicates whether this facility is the corporate head office.
    /// </summary>
    public bool IsHeadOffice { get; set; } = false;

    /// <summary>
    /// Indicates whether this site is actively operating.
    /// </summary>
    public bool IsActive { get; set; } = true;
}
