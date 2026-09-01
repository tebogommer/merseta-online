using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Statutory Strategic Priorities, Key Focus Areas, and Sector Skills Plan (SSP) Themes
/// mapped to National Skills Development Plan (NSDP III) Outcomes and Strategic Infrastructure Projects (SIPs).
/// </summary>
public class StrategicPriority : BaseEntity
{
    /// <summary>
    /// Unique strategic priority identification code (e.g. SP-GREEN-01, SP-4IR-02, SP-ARTISAN-03).
    /// </summary>
    public string Code { get; set; } = string.Empty;

    /// <summary>
    /// Official title of the strategic theme (e.g. Green Economy, EV & Battery Technologies).
    /// </summary>
    public string Name { get; set; } = string.Empty;

    /// <summary>
    /// Detailed description and statutory objective of this focus area.
    /// </summary>
    public string? Description { get; set; }

    /// <summary>
    /// National Skills Development Plan (NSDP III) Outcome reference (e.g. Outcome 1, Outcome 2, Outcome 5).
    /// </summary>
    public string NsdpOutcomeCode { get; set; } = "NSDP-OUTCOME-1";

    /// <summary>
    /// Full description of the NSDP Outcome goal.
    /// </summary>
    public string NsdpOutcomeDescription { get; set; } = "Identify and increase production of occupations in high demand";

    /// <summary>
    /// Relevant Strategic Infrastructure Project (SIP) category (e.g. SIP 8: Green Energy, SIP 2: Freight).
    /// </summary>
    public string? SipCategory { get; set; }

    /// <summary>
    /// Target sector classification (e.g. Automotive, Metal & Engineering, Plastics, All Sectors).
    /// </summary>
    public string? TargetSector { get; set; } = "All Sectors";

    /// <summary>
    /// Indicates whether this strategic theme is active for allocation in new funding windows.
    /// </summary>
    public bool IsActive { get; set; } = true;

    /// <summary>
    /// Funding window allocations associated with this strategic priority.
    /// </summary>
    public ICollection<FundingWindowPriority> WindowPriorities { get; set; } = new List<FundingWindowPriority>();

    /// <summary>
    /// Grant applications addressing this strategic priority.
    /// </summary>
    public ICollection<GrantApplication> GrantApplications { get; set; } = new List<GrantApplication>();
}
