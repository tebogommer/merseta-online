using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Nsdms.Domain.Common;
using Nsdms.Domain.Lookups;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Individual occupational classification record scoped to a statutory OFO framework release.
/// Links DHET hierarchical groups (Major, Sub-Major, Minor, Unit) and trade designations to lookup.OfoCodeType.
/// </summary>
[Table("OfoCodeSetItem")]
public class OfoCodeSetItem : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the parent statutory OFO framework release.
    /// </summary>
    public int OfoCodeSetId { get; set; }

    /// <summary>
    /// Statutory OFO occupational code referencing lookup.OfoCodeType.Code (e.g. "651202", "653101").
    /// </summary>
    [MaxLength(50)]
    public string OfoCodeId { get; set; } = string.Empty;

    /// <summary>
    /// 1-digit DHET Major Group classification (e.g. "1" Managers, "2" Professionals, "6" Craft and Related Trades).
    /// </summary>
    [MaxLength(10)]
    public string? MajorGroup { get; set; }

    /// <summary>
    /// 2-digit DHET Sub-Major Group classification (e.g. "65" Metal, Machinery and Related Trades).
    /// </summary>
    [MaxLength(10)]
    public string? SubMajorGroup { get; set; }

    /// <summary>
    /// 3-digit DHET Minor Group classification (e.g. "651" Sheet and Structural Metal Workers).
    /// </summary>
    [MaxLength(10)]
    public string? MinorGroup { get; set; }

    /// <summary>
    /// 4-digit DHET Unit Group classification (e.g. "6512" Welders and Flamecutters).
    /// </summary>
    [MaxLength(10)]
    public string? UnitGroup { get; set; }

    /// <summary>
    /// Indicates whether this occupation is officially designated as a statutory trade under the Skills Development Act.
    /// </summary>
    public bool Trade { get; set; } = false;

    /// <summary>
    /// Indicates whether this occupation is designated as a green economy occupation.
    /// </summary>
    public bool GreenOccupation { get; set; } = false;

    /// <summary>
    /// Indicates whether this occupation involves designated green skills.
    /// </summary>
    public bool GreenSkill { get; set; } = false;

    /// <summary>
    /// Indicates whether this occupational code is active and valid within this specific set version.
    /// </summary>
    public bool IsActiveInSet { get; set; } = true;

    // Navigation properties
    /// <summary>
    /// Parent statutory OFO framework release.
    /// </summary>
    public OfoCodeSet? OfoCodeSet { get; set; }

    /// <summary>
    /// Referenced statutory OFO code lookup master record.
    /// </summary>
    public OfoCodeType? OfoCode { get; set; }
}
