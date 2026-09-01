namespace Nsdms.Domain.Entities;

/// <summary>
/// Executive Skills Intelligence DTO models for Sector Skills Plan (SSP) &amp; Business Intelligence.
/// </summary>
public class SspChamberMetricDto
{
    public string ChamberName { get; set; } = string.Empty;
    public int EmployerCount { get; set; }
    public int LearnerCount { get; set; }
    public decimal PlannedTrainingBudget { get; set; }
    public decimal DisbursedGrantsAmount { get; set; }
}

public class SspEquityMetricDto
{
    public string DemographicGroup { get; set; } = string.Empty;
    public int TotalLearners { get; set; }
    public double Percentage { get; set; }
}

public class SspProvincialMetricDto
{
    public string ProvinceCode { get; set; } = string.Empty;
    public string ProvinceName { get; set; } = string.Empty;
    public int EmployerCount { get; set; }
    public int ProviderCount { get; set; }
    public int ActiveLearnerCount { get; set; }
    public int CertifiedCount { get; set; }
}

public class SspScarceSkillDto
{
    public string OfoCode { get; set; } = string.Empty;
    public string OccupationTitle { get; set; } = string.Empty;
    public string ChamberName { get; set; } = string.Empty;
    public int ReportedNeedCount { get; set; }
    public int EnrolledTrainingCount { get; set; }
    public string InterventionsRequired { get; set; } = string.Empty;
}

/// <summary>
/// Chamber-partitioned statutory levy collection, Mandatory Grant rebates, Discretionary Grant envelopes, and budget burn-rate intelligence.
/// </summary>
public class ChamberGrantFinancialSummaryDto
{
    public string ChamberCode { get; set; } = string.Empty;
    public string ChamberName { get; set; } = string.Empty;
    public int EmployerCount { get; set; }
    public decimal TotalGrossLevyCollected { get; set; }
    public decimal MandatoryGrantRebateTarget { get; set; }
    public decimal MandatoryGrantRebatesPaid { get; set; }
    public decimal DiscretionaryGrantEnvelope { get; set; }
    public decimal DiscretionaryGrantCommitted { get; set; }
    public decimal DiscretionaryGrantDisbursed { get; set; }
    public decimal AdministrationExpensePortion { get; set; }
    public decimal QctoLevyPortion { get; set; }
    public decimal UnallocatedReserveBalance => Math.Max(0m, DiscretionaryGrantEnvelope - DiscretionaryGrantCommitted);
    public double GrantBurnRatePercentage => DiscretionaryGrantEnvelope > 0 ? (double)Math.Round((DiscretionaryGrantCommitted / DiscretionaryGrantEnvelope) * 100m, 1) : 0;
}
