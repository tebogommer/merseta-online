using Nsdms.Domain.Common;
using Nsdms.Domain.Lookups;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Stakeholder eligibility mapping for a Discretionary Grant funding window.
/// Specifies which organizational categories (e.g. Levy-paying, Public TVET, NGO) may apply.
/// </summary>
public class GrantWindowEligibility : BaseEntity
{
    public int FundingWindowId { get; set; }
    public GrantFundingWindow? FundingWindow { get; set; }

    public string StakeholderEligibilityTypeCode { get; set; } = string.Empty;
    public StakeholderEligibilityType? StakeholderEligibilityType { get; set; }
}

/// <summary>
/// Whitelisted skills development intervention permitted under a specific funding window.
/// Scopes whether learners, apprenticeships, bursaries, or non-pivotal projects are eligible.
/// </summary>
public class GrantWindowIntervention : BaseEntity
{
    public int FundingWindowId { get; set; }
    public GrantFundingWindow? FundingWindow { get; set; }

    public string InterventionTypeCode { get; set; } = string.Empty;
    public InterventionType? InterventionType { get; set; }

    public decimal? MaxBudgetCap { get; set; }
    public int? MaxLearnerCap { get; set; }
}

/// <summary>
/// Reusable Blueprint Template for rapid 1-click Discretionary Grant funding window creation.
/// Bundles default stakeholder eligibilities and whitelisted interventions.
/// </summary>
public class GrantWindowTemplate : BaseEntity
{
    public string TemplateCode { get; set; } = string.Empty;
    public string Name { get; set; } = string.Empty;
    public string? Description { get; set; }
    public bool IsPivotal { get; set; } = true;
    public string WindowClassification { get; set; } = "Pivotal"; // Pivotal, NonPivotal, Hybrid
    public bool RequireWspComplianceDefault { get; set; } = false;
    public int EstimatedDurationDays { get; set; } = 45;
    public bool IsActive { get; set; } = true;

    public ICollection<GrantWindowTemplateEligibility> DefaultEligibilities { get; set; } = new List<GrantWindowTemplateEligibility>();
    public ICollection<GrantWindowTemplateIntervention> DefaultInterventions { get; set; } = new List<GrantWindowTemplateIntervention>();
}

/// <summary>
/// Default stakeholder eligibility presets attached to a funding window template.
/// </summary>
public class GrantWindowTemplateEligibility : BaseEntity
{
    public int TemplateId { get; set; }
    public GrantWindowTemplate? Template { get; set; }

    public string StakeholderEligibilityTypeCode { get; set; } = string.Empty;
    public StakeholderEligibilityType? StakeholderEligibilityType { get; set; }
}

/// <summary>
/// Default whitelisted interventions attached to a funding window template.
/// </summary>
public class GrantWindowTemplateIntervention : BaseEntity
{
    public int TemplateId { get; set; }
    public GrantWindowTemplate? Template { get; set; }

    public string InterventionTypeCode { get; set; } = string.Empty;
    public InterventionType? InterventionType { get; set; }
}
