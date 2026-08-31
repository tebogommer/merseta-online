using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public enum MentorRatioEnforcementState
{
    StrictlyEnforced,
    TradeAdvisoryOnly,
    WorkplaceExempt,
    OrgExempt,
    GlobalDisabled,
    CustomOverride
}

public enum MentorRatioComplianceStatus
{
    Compliant,
    NearCapacity,
    OverCapacity,
    Exempt
}

public record MentorCapacityBreakdownDto(
    int MentorId,
    int PersonId,
    string MentorName,
    string? RsaIdNumber,
    string Designation,
    string? ArtisanTradeNumber,
    int YearsExperience,
    bool IsCertifiedArtisan,
    bool IsActive,
    bool IsRatioExempt,
    bool IsRatioEnforced,
    int AllocatedCapacity,
    string CapacitySource
);

public class MentorRatioEvaluationResult
{
    public int WorkplaceApprovalId { get; set; }
    public string WorkplaceApprovalNumber { get; set; } = string.Empty;
    public int OrganisationId { get; set; }
    public string OrganisationName { get; set; } = string.Empty;
    public string TradeCode { get; set; } = "GENERIC";
    public string TradeTitle { get; set; } = "General Engineering Trade";
    public int StandardTradeRatio { get; set; } = 4;
    public int TotalActiveMentors { get; set; }
    public int TotalPlacedLearners { get; set; }
    public int EffectiveTotalCapacity { get; set; }
    public int AvailableSlots { get; set; }
    public double UtilizationPercentage { get; set; }
    public bool IsEnforcementActive { get; set; } = true;
    public MentorRatioEnforcementState EnforcementState { get; set; } = MentorRatioEnforcementState.StrictlyEnforced;
    public MentorRatioComplianceStatus Status { get; set; } = MentorRatioComplianceStatus.Compliant;
    public string StatusMessage { get; set; } = string.Empty;
    public string? ExemptionReason { get; set; }
    public List<string> Warnings { get; set; } = new();
    public List<MentorCapacityBreakdownDto> MentorBreakdowns { get; set; } = new();
}

public interface IMentorRatioPolicyEngine
{
    Task<MentorRatioEvaluationResult> EvaluateWorkplaceApprovalCapacityAsync(int workplaceApprovalId);
    Task<MentorRatioEvaluationResult> EvaluatePlacementFeasibilityAsync(int workplaceApprovalId, int additionalLearners = 1);
    
    Task<List<TradeMentorRatioPolicy>> GetAllTradePoliciesAsync(bool activeOnly = false);
    Task<TradeMentorRatioPolicy?> GetTradePolicyByIdAsync(int id);
    Task<TradeMentorRatioPolicy?> GetTradePolicyByCodeAsync(string tradeCode);
    Task<TradeMentorRatioPolicy> SaveTradePolicyAsync(TradeMentorRatioPolicy policy, string currentUsername = "SYSTEM");
    Task<bool> DeleteTradePolicyAsync(int id, string currentUsername = "SYSTEM");

    Task<bool> GetGlobalRatioEnforcementAsync();
    Task<bool> SetGlobalRatioEnforcementAsync(bool isEnabled, string currentUsername = "SYSTEM");
    
    Task SetOrganisationRatioOverrideAsync(int organisationId, bool? isEnforced, string? exemptionReason, int? customCap, string currentUsername = "SYSTEM");
    Task SetWorkplaceRatioOverrideAsync(int workplaceApprovalId, bool? isEnforced, int? customRatio, string? tradeCode, string? exemptionNotes, string currentUsername = "SYSTEM");
    Task SetMentorCapacityOverrideAsync(int mentorId, int? maxCapacity, bool isExempt, bool isEnforced, string? notes, string currentUsername = "SYSTEM");
}
