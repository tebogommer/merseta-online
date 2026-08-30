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
