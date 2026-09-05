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
    /// Physical residential street address line 1.
    /// </summary>
    public string? PhysicalAddress { get; set; }

    /// <summary>
    /// Physical residential address postal code.
    /// </summary>
    public string? PhysicalAddressPostalCode { get; set; }

    /// <summary>
    /// Postal delivery address.
    /// </summary>
    public string? PostalAddress { get; set; }

    /// <summary>
    /// Postal address delivery code.
    /// </summary>
    public string? PostalAddressPostalCode { get; set; }

    /// <summary>
    /// South African province code of primary residence (references lookup.ProvinceType: GP, KZN, WC, EC, FS, MP, NW, NC, LP).
    /// </summary>
    public string? ProvinceCode { get; set; }

    /// <summary>
    /// Statistics South Africa Spatial Area Code (references lookup.StatssaAreaCodeType).
    /// </summary>
    public string? StatssaAreaCode { get; set; }
}
