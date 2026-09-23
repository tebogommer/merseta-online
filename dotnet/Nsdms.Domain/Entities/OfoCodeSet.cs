using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Authoritative DHET Organising Framework for Occupations (OFO) statutory framework release (e.g. 2019, 2021, 2025).
/// Serves as the statutory code set catalog for Mandatory Grant submission cycles and SETMIS reporting.
/// </summary>
[Table("OfoCodeSet")]
public class OfoCodeSet : BaseEntity
{
    /// <summary>
    /// Statutory OFO framework release year (e.g. 2019, 2021, 2025).
    /// </summary>
    public int SetYear { get; set; }

    /// <summary>
    /// Official release title (e.g. "OFO 2025 Release (v25)").
    /// </summary>
    [MaxLength(150)]
    public string Name { get; set; } = string.Empty;

    /// <summary>
    /// Detailed scope, legislative authority, and version commentary.
    /// </summary>
    [MaxLength(500)]
    public string? Description { get; set; }

    /// <summary>
    /// Date the OFO framework version was published in the Government Gazette.
    /// </summary>
    public DateTime? GazettedDate { get; set; }

    /// <summary>
    /// Government Gazette publication reference number (e.g. "Gazette No. 51234").
    /// </summary>
    [MaxLength(100)]
    public string? GazetteNumber { get; set; }

    /// <summary>
    /// Indicates whether this OFO code set is active and available for grant cycle scoping.
    /// </summary>
    public bool IsActive { get; set; } = true;

    /// <summary>
    /// Occupational codes cataloged within this statutory release.
    /// </summary>
    public ICollection<OfoCodeSetItem> Items { get; set; } = new List<OfoCodeSetItem>();
}
