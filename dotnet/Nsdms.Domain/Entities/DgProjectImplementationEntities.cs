using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Discretionary Grant Project Implementation Plan (PIP) and contracting governance.
/// </summary>
public class ProjectImplementationPlan : BaseEntity
{
    public int OrganisationId { get; set; }
    public Organisation? Organisation { get; set; }

    public int? FundingWindowId { get; set; }
    public GrantFundingWindow? FundingWindow { get; set; }

    public int? GrantApplicationId { get; set; }
    public GrantApplication? GrantApplication { get; set; }

    public string PlanReferenceNumber { get; set; } = string.Empty;
    public string InterventionTypeCode { get; set; } = "Learnership"; // Learnership, Apprenticeship, SkillsProgramme, Bursary, Internship
    public decimal TotalAwardedAmount { get; set; } = 0m;
    public decimal RecoverableAmount { get; set; } = 0m;
    public int TotalLearnersAwarded { get; set; } = 0;
    public int LearnersWithDisabilityCount { get; set; } = 0;
    public string StatusCode { get; set; } = "Draft"; // Draft, UnderReview, ActiveContractsSigned, Completed, Terminated
    public DateTime? ContractSignOffDate { get; set; }

    // Collections
    public ICollection<PipLearnerAllocation> Allocations { get; set; } = new List<PipLearnerAllocation>();
    public ICollection<GrantPaymentClaim> Claims { get; set; } = new List<GrantPaymentClaim>();
}

/// <summary>
/// Breakdown of awarded learner interventions and allowance budgets in a PIP.
/// </summary>
public class PipLearnerAllocation : BaseEntity
{
    public int ProjectImplementationPlanId { get; set; }
    public ProjectImplementationPlan? ProjectImplementationPlan { get; set; }

    public int? SaqaQualificationId { get; set; }
    public string? QualificationTitle { get; set; }

    public int LearnerCount { get; set; } = 0;
    public decimal UnitCost { get; set; } = 0m;
    public decimal TotalAllowanceBudget { get; set; } = 0m;
    public decimal TotalTuitionBudget { get; set; } = 0m;
}

/// <summary>
/// Milestone-based Payment Claim against a Project Implementation Plan.
/// </summary>
public class GrantPaymentClaim : BaseEntity
{
    public int ProjectImplementationPlanId { get; set; }
    public ProjectImplementationPlan? ProjectImplementationPlan { get; set; }

    public string ClaimNumber { get; set; } = string.Empty;
    public int TrancheNumber { get; set; } = 1; // 1, 2, 3, 4
    public decimal ClaimAmount { get; set; } = 0m;
    public string DeliverableDescription { get; set; } = string.Empty;
    public string StatusCode { get; set; } = "PendingSubmission"; // PendingSubmission, AssessorVerified, ApprovedForPayment, Paid, Rejected
    public DateTime? ApprovalDate { get; set; }
    public string? ApprovedByUserId { get; set; }
    public string? ErpBatchNumber { get; set; }
}
