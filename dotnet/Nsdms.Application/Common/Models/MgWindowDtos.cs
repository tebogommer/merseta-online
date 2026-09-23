using System.ComponentModel.DataAnnotations;

namespace Nsdms.Application.Common.Models;

/// <summary>
/// Lightweight DTO for rendering Mandatory Grant windows in the A1 Master List.
/// </summary>
public class MgWindowListDto
{
    public int Id { get; set; }
    public string ReferenceNumber => $"MG-WIN-{SchemeYear}";
    public int SchemeYear { get; set; }
    public string WindowName { get; set; } = string.Empty;
    public DateTime OpeningDate { get; set; }
    public DateTime ClosingDate { get; set; }
    public DateTime ExtensionCutoffDate { get; set; }
    public int? OfoCodeSetId { get; set; }
    public int? OfoCodeSetYear { get; set; }
    public string OfoCodeSetName { get; set; } = string.Empty;
    public string OfoCodeSetVersion => OfoCodeSetYear.HasValue ? $"v{OfoCodeSetYear.Value % 100}" : string.Empty;
    public string? GazetteReference { get; set; }
    public string ApprovalStatus { get; set; } = "Draft";
    public string? ProposedByUserName { get; set; }
    public DateTime? ProposedDate { get; set; }
    public string? AdjudicatedByUserName { get; set; }
    public DateTime? AdjudicatedDate { get; set; }
    public bool IsActive { get; set; }
    public int ScopedOccupationsCount { get; set; }
    public int PrioritySkillsCount { get; set; }
    public bool IsCurrentlyOpen => DateTime.UtcNow >= OpeningDate && DateTime.UtcNow <= ClosingDate;
}

/// <summary>
/// Detailed DTO for rendering the A3 Master-Detail Hub.
/// </summary>
public class MgWindowDetailDto
{
    public int Id { get; set; }
    public string ReferenceNumber => $"MG-WIN-{SchemeYear}";
    public int SchemeYear { get; set; }
    public string WindowName { get; set; } = string.Empty;
    public DateTime OpeningDate { get; set; }
    public DateTime ClosingDate { get; set; }
    public DateTime ExtensionCutoffDate { get; set; }
    public int? OfoCodeSetId { get; set; }
    public int? OfoCodeSetYear { get; set; }
    public string OfoCodeSetName { get; set; } = string.Empty;
    public string OfoCodeSetVersion => OfoCodeSetYear.HasValue ? $"v{OfoCodeSetYear.Value % 100}" : string.Empty;
    public string? GazetteReference { get; set; }
    public string? Justification { get; set; }
    public string ApprovalStatus { get; set; } = "Draft";
    public string? ProposedByUserId { get; set; }
    public string? ProposedByUserName { get; set; }
    public DateTime? ProposedDate { get; set; }
    public string? AdjudicatedByUserId { get; set; }
    public string? AdjudicatedByUserName { get; set; }
    public DateTime? AdjudicatedDate { get; set; }
    public string? AdjudicationComments { get; set; }
    public bool IsActive { get; set; }
    public int ScopedOccupationsCount { get; set; }
    public int PrioritySkillsCount { get; set; }
    public bool IsCurrentlyOpen => DateTime.UtcNow >= OpeningDate && DateTime.UtcNow <= ClosingDate;
}

/// <summary>
/// Input DTO for proposing a new Mandatory Grant window.
/// </summary>
public class CreateMgWindowDto
{
    [Required]
    [Range(2020, 2040)]
    public int SchemeYear { get; set; }

    [Required]
    [MaxLength(150)]
    public string WindowName { get; set; } = string.Empty;

    [Required]
    public DateTime OpeningDate { get; set; }

    [Required]
    public DateTime ClosingDate { get; set; }

    [Required]
    public DateTime ExtensionCutoffDate { get; set; }

    [Required]
    public int OfoCodeSetId { get; set; }

    [MaxLength(200)]
    public string? GazetteReference { get; set; }

    [Required]
    public string Justification { get; set; } = string.Empty;
}

/// <summary>
/// Input DTO for updating draft window parameters.
/// </summary>
public class UpdateMgWindowDto
{
    [Required]
    [MaxLength(150)]
    public string WindowName { get; set; } = string.Empty;

    [Required]
    public DateTime OpeningDate { get; set; }

    [Required]
    public DateTime ClosingDate { get; set; }

    [Required]
    public DateTime ExtensionCutoffDate { get; set; }

    [Required]
    public int OfoCodeSetId { get; set; }

    [MaxLength(200)]
    public string? GazetteReference { get; set; }

    [Required]
    public string Justification { get; set; } = string.Empty;
}

/// <summary>
/// DTO representing an individual occupational code scoped to a grant window.
/// </summary>
public class MgWindowOfoCodeDto
{
    public int Id { get; set; }
    public int MgWindowId { get; set; }
    public string OfoCode { get; set; } = string.Empty;
    public string OccupationTitle { get; set; } = string.Empty;
    public string? MajorGroup { get; set; }
    public string? SubMajorGroup { get; set; }
    public string? UnitGroup { get; set; }
    public bool Trade { get; set; }
    public bool GreenOccupation { get; set; }
    public bool GreenSkill { get; set; }
    public bool IsPrioritySkill { get; set; }
    public string? SectorNotes { get; set; }
    public bool IsActiveInScope { get; set; }
    public bool IsActiveInRegistry { get; set; }
}

/// <summary>
/// Input DTO for manually scoping an active occupational code to a window.
/// </summary>
public class AddScopedOfoCodeDto
{
    [Required]
    [MaxLength(50)]
    public string OfoCode { get; set; } = string.Empty;

    public bool IsPrioritySkill { get; set; }

    [MaxLength(500)]
    public string? SectorNotes { get; set; }
}

/// <summary>
/// DTO representing a statutory DHET OFO Framework Release.
/// </summary>
public class OfoCodeSetDto
{
    public int Id { get; set; }
    public int SetYear { get; set; }
    public string Name { get; set; } = string.Empty;
    public string? Description { get; set; }
    public DateTime? GazettedDate { get; set; }
    public string? GazetteNumber { get; set; }
    public bool IsActive { get; set; }
    public int TotalOccupationsCount { get; set; }
    public int TradeCount { get; set; }
}

/// <summary>
/// DTO for creating a new gazetted OFO release.
/// </summary>
public class CreateOfoCodeSetDto
{
    [Required]
    [Range(2015, 2040)]
    public int SetYear { get; set; }

    [Required]
    [MaxLength(150)]
    public string Name { get; set; } = string.Empty;

    [MaxLength(500)]
    public string? Description { get; set; }

    public DateTime? GazettedDate { get; set; }

    [MaxLength(100)]
    public string? GazetteNumber { get; set; }
}

/// <summary>
/// Fast lookup item for autocomplete selection in the Add OFO Code dialog.
/// </summary>
public class OfoCodeLookupDto
{
    public string Code { get; set; } = string.Empty;
    public string Name { get; set; } = string.Empty;
    public string? MajorGroup { get; set; }
    public bool Trade { get; set; }
    public bool Active { get; set; }
    public string DisplayText => $"{Code} - {Name}" + (Trade ? " [Trade]" : string.Empty);
}

/// <summary>
/// Filter query parameters for the Scoped OFO child table.
/// </summary>
public class OfoFilterQuery
{
    public string? SearchTerm { get; set; }
    public string? MajorGroup { get; set; }
    public bool? PriorityOnly { get; set; }
    public bool? TradeOnly { get; set; }
    public int PageNumber { get; set; } = 1;
    public int PageSize { get; set; } = 20;
}
