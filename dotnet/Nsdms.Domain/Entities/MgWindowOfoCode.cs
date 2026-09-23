using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Nsdms.Domain.Common;
using Nsdms.Domain.Lookups;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Child entity scoping an individual OFO occupational code to a specific Mandatory Grant submission window.
/// Flags sector priority skills, scarce trades, and chamber-specific commentary for grant scoring.
/// </summary>
[Table("MgWindowOfoCode")]
public class MgWindowOfoCode : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the parent Mandatory Grant submission window.
    /// </summary>
    public int MgWindowId { get; set; }

    /// <summary>
    /// Statutory OFO occupational code referencing lookup.OfoCodeType.Code (e.g. "651202", "653101").
    /// </summary>
    [MaxLength(50)]
    public string OfoCodeId { get; set; } = string.Empty;

    /// <summary>
    /// Flags whether this occupation is a national or sectoral priority skill for merSETA.
    /// </summary>
    public bool IsPrioritySkill { get; set; } = false;

    /// <summary>
    /// Chamber priorities, specialisation notes, or critical skill commentary.
    /// </summary>
    [MaxLength(500)]
    public string? SectorNotes { get; set; }

    /// <summary>
    /// Indicates whether this occupational code is active within the scoped window.
    /// </summary>
    public bool IsActive { get; set; } = true;

    // Navigation properties
    /// <summary>
    /// Parent Mandatory Grant window.
    /// </summary>
    public MgWindow? MgWindow { get; set; }

    /// <summary>
    /// Statutory OFO code master lookup record.
    /// </summary>
    public OfoCodeType? OfoCode { get; set; }
}
