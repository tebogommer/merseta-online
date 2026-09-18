using Nsdms.Domain.Entities;

namespace Nsdms.Application.Common.Interfaces;

public enum StatutoryPillarType
{
    WspAtrCompliance = 1,
    SarsLevyContributions = 2,
    TrainingCommitteeParity = 3,
    GovernanceAndOwnership = 4,
    WorkplaceApprovalAndMentors = 5,
    ConflictOfInterestStanding = 6,
    BankingDetailsSecurity = 7
}

public enum CompliancePillarStatus
{
    Compliant = 1,
    PartiallyCompliant = 2,
    NonCompliant = 3,
    NotApplicable = 4
}

public class CompliancePillarResult
{
    public StatutoryPillarType PillarType { get; set; }
    public string PillarName { get; set; } = string.Empty;
    public string StatutoryCode { get; set; } = string.Empty;
    public decimal Weight { get; set; }
    public CompliancePillarStatus Status { get; set; } = CompliancePillarStatus.NonCompliant;
    public decimal Score { get; set; }
    public decimal WeightedScore => Math.Round(Score * (Weight / 100m), 2);
    public string Summary { get; set; } = string.Empty;
    public List<string> Details { get; set; } = new();
    public string? ActionRequired { get; set; }
    public int TargetTabIndex { get; set; }
    public string BadgeIcon { get; set; } = string.Empty;
}

public class OrganisationComplianceRadarReport
{
    public int OrganisationId { get; set; }
    public string OrganisationName { get; set; } = string.Empty;
    public string SdlNumber { get; set; } = string.Empty;
    public DateTime EvaluationDate { get; set; } = DateTime.UtcNow;
    public decimal OverallScore { get; set; }
    public string OverallGrade { get; set; } = string.Empty;
    public string OverallBadgeColor { get; set; } = "Default";
    public List<CompliancePillarResult> Pillars { get; set; } = new();
    public List<string> CriticalActionItems { get; set; } = new();
    public bool IsEligibleForDgGrants { get; set; }
    public bool IsEligibleForLearnerRegistrations { get; set; }
    public int CompliantPillarsCount => Pillars.Count(p => p.Status == CompliancePillarStatus.Compliant || p.Status == CompliancePillarStatus.NotApplicable);
    public int ActionRequiredCount => Pillars.Count(p => p.Status == CompliancePillarStatus.NonCompliant || p.Status == CompliancePillarStatus.PartiallyCompliant);
}

public class TrainingCommitteeParityReport
{
    public int OrganisationId { get; set; }
    public bool IsMandatory { get; set; }
    public string CompanySize { get; set; } = "UNKNOWN";
    public int EmployeeHeadcount { get; set; }
    public int TotalMembers { get; set; }
    public int ManagementMembersCount { get; set; }
    public int LabourMembersCount { get; set; }
    public decimal ManagementPercentage => TotalMembers > 0 ? Math.Round((decimal)ManagementMembersCount / TotalMembers * 100m, 1) : 0m;
    public decimal LabourPercentage => TotalMembers > 0 ? Math.Round((decimal)LabourMembersCount / TotalMembers * 100m, 1) : 0m;
    public decimal ParityRatio => (ManagementMembersCount > 0 && LabourMembersCount > 0)
        ? Math.Round((decimal)Math.Min(ManagementMembersCount, LabourMembersCount) / Math.Max(ManagementMembersCount, LabourMembersCount), 2)
        : 0m;
    public bool IsQuorumMet { get; set; }
    public bool IsCompliant { get; set; }
    public string StatusText { get; set; } = string.Empty;
    public string StatusSeverity { get; set; } = "Warning";
    public string Recommendation { get; set; } = string.Empty;
}

public interface IOrganisationComplianceEngine
{
    Task<OrganisationComplianceRadarReport> EvaluateOrganisationComplianceAsync(int organisationId, CancellationToken cancellationToken = default);
    Task<TrainingCommitteeParityReport> EvaluateTrainingCommitteeParityAsync(int organisationId, CancellationToken cancellationToken = default);
}
