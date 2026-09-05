using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Vertically partitioned satellite entity storing residential, postal, and telecommunications contact data for an individual.
/// </summary>
public class PersonContact : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the parent Person record.
    /// </summary>
    public int PersonId { get; set; }

    /// <summary>
    /// Navigational reference to the parent Person record.
    /// </summary>
    public Person? Person { get; set; }

    /// <summary>
    /// Primary email contact address.
    /// </summary>
    public string? Email { get; set; } = string.Empty;

    /// <summary>
    /// Primary telephone contact number.
    /// </summary>
    public string? PhoneNumber { get; set; } = string.Empty;

    /// <summary>
    /// Mobile / cellular phone number.
    /// </summary>
    public string? CellNumber { get; set; }

    /// <summary>
    /// Facsimile contact number.
    /// </summary>
    public string? FaxNumber { get; set; }

    /// <summary>
    /// Physical residential street address lines (Section 6.1 attributes 16, 17, 18).
    /// </summary>
    public string? PhysicalAddress { get; set; }
    public string? PhysicalAddressLine2 { get; set; }
    public string? PhysicalAddressLine3 { get; set; }

    /// <summary>
    /// Physical residential address postal code.
    /// </summary>
    public string? PhysicalAddressPostalCode { get; set; }

    /// <summary>
    /// Indicates whether postal address is identical to home residential address (Section 6.1 attribute 22).
    /// </summary>
    public bool IsPostalSameAsPhysical { get; set; } = false;

    /// <summary>
    /// Postal delivery address lines (Section 6.1 attributes 23, 24, 25).
    /// </summary>
    public string? PostalAddress { get; set; }
    public string? PostalAddressLine2 { get; set; }
    public string? PostalAddressLine3 { get; set; }

    /// <summary>
    /// Postal address delivery code.
    /// </summary>
    public string? PostalAddressPostalCode { get; set; }

    /// <summary>
    /// Next of kin demographic and contact details (Section 6.1 attributes 29, 30).
    /// </summary>
    public string? NextOfKinName { get; set; }
    public string? NextOfKinContactNumber { get; set; }

    /// <summary>
    /// Secondary email contact address (Section 6.1 attribute 32).
    /// </summary>
    public string? SecondaryEmail { get; set; }

    /// <summary>
    /// Urban / Rural area classification code (Section 6.1 attribute 20, references lookup.UrbanRuralType).
    /// </summary>
    public string? UrbanRuralId { get; set; } = "01";

    /// <summary>
    /// South African province code of primary residence (references lookup.ProvinceType: GP, KZN, WC, EC, FS, MP, NW, NC, LP).
    /// </summary>
    public string? ProvinceCode { get; set; }

    /// <summary>
    /// Statistics South Africa Spatial Area Code (references lookup.StatssaAreaCodeType).
    /// </summary>
    public string? StatssaAreaCode { get; set; }
}
