using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Comprehensive Workplace Monitoring and Quality Assurance Site Visit.
/// </summary>
public class WorkplaceMonitoringSiteVisit : BaseEntity
{
    public int OrganisationId { get; set; }
    public Organisation? Organisation { get; set; }

    /// <summary>
    /// Mandatory Contact Person at the employer site during the visit.
    /// </summary>
    public int ContactPersonId { get; set; }
    public Person? ContactPerson { get; set; }

    public DateTime MonitoringDate { get; set; } = DateTime.UtcNow;
    public string StatusCode { get; set; } = "Draft"; // Draft, PendingApproval, Approved, NonComplianceIdentified, Rejected

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public string MonitoringStatusCode { get => StatusCode; set => StatusCode = value; }

    public string? CloUserId { get; set; } // Client Liaison Officer
    public string? CrmUserId { get; set; } // Client Relationship Manager

    public bool NonCompliancesIdentified { get; set; } = false;
    public bool NonComplianceHoldingArea { get; set; } = false;
    public DateTime? NonComplianceSubmittedDate { get; set; }
    public DateTime? NonComplianceApprovalDate { get; set; }
    public string? NonComplianceNotes { get; set; }

    public DateTime? ApprovalDate { get; set; }
    public string? ApprovedByUserId { get; set; }
    public string? ApprovalComments { get; set; }
    public bool SignOffState { get; set; } = false;

    // Collections
    public ICollection<WorkplaceMonitoringComplianceSurvey> ComplianceSurveys { get; set; } = new List<WorkplaceMonitoringComplianceSurvey>();
    public ICollection<WorkplaceMonitoringActionPlan> ActionPlans { get; set; } = new List<WorkplaceMonitoringActionPlan>();
    public ICollection<WorkplaceMonitoringMitigationPlan> MitigationPlans { get; set; } = new List<WorkplaceMonitoringMitigationPlan>();
    public ICollection<WorkplaceMonitoringLearnerSurvey> LearnerSurveys { get; set; } = new List<WorkplaceMonitoringLearnerSurvey>();
}

/// <summary>
/// 10-Point Statutory Compliance Survey Questions answered during the visit.
/// </summary>
public class WorkplaceMonitoringComplianceSurvey : BaseEntity
{
    public int WorkplaceMonitoringSiteVisitId { get; set; }
    public WorkplaceMonitoringSiteVisit? WorkplaceMonitoringSiteVisit { get; set; }

    public int QuestionNumber { get; set; }
    public string Category { get; set; } = "General"; // Toolbox, Training, Mentor, PPE, Wages, Contracts, SDF

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public string SurveyCategory { get => Category; set => Category = value; }

    public string QuestionText { get; set; } = string.Empty;
    public string Answer { get; set; } = "Yes"; // Yes, No, NotApplicable
    public bool NonComplianceRisk { get; set; } = false;
    public string? Comments { get; set; }
}

/// <summary>
/// Corrective Action Plan for identified defects or non-compliances.
/// </summary>
public class WorkplaceMonitoringActionPlan : BaseEntity
{
    public int WorkplaceMonitoringSiteVisitId { get; set; }
    public WorkplaceMonitoringSiteVisit? WorkplaceMonitoringSiteVisit { get; set; }

    public string ValidationTypeCode { get; set; } = "DiscretionaryGrant"; // DiscretionaryGrant, LearnerInduction, LearnerPayments, Mentorship, HealthAndSafety
    public string Criteria { get; set; } = string.Empty;
    public bool IsAtRisk { get; set; } = false;
    public string ActionRequired { get; set; } = string.Empty;
    public DateTime TargetCompletionDate { get; set; } = DateTime.UtcNow.AddDays(30);
    public string ResponsiblePersonName { get; set; } = string.Empty;
    public string StatusCode { get; set; } = "Open"; // Open, UnderReview, Completed, Escalated

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public string ActionPlanStatusCode { get => StatusCode; set => StatusCode = value; }

    public DateTime? ResolutionDate { get; set; }
    public string? ResolutionNotes { get; set; }
}

/// <summary>
/// Specific risk mitigation plan for high-risk non-compliance findings.
/// </summary>
public class WorkplaceMonitoringMitigationPlan : BaseEntity
{
    public int WorkplaceMonitoringSiteVisitId { get; set; }
    public WorkplaceMonitoringSiteVisit? WorkplaceMonitoringSiteVisit { get; set; }

    public int? WorkplaceMonitoringActionPlanId { get; set; }
    public WorkplaceMonitoringActionPlan? WorkplaceMonitoringActionPlan { get; set; }

    public string IdentifiedRisk { get; set; } = string.Empty;
    public string MitigationSteps { get; set; } = string.Empty;
    public DateTime TargetResolutionDate { get; set; } = DateTime.UtcNow.AddDays(14);
    public string StatusCode { get; set; } = "Pending"; // Pending, Implemented, Closed

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public string MitigationStatusCode { get => StatusCode; set => StatusCode = value; }

    public string? ReviewerNotes { get; set; }
}

/// <summary>
/// Confidential Learner Interview Log conducted on-site.
/// </summary>
public class WorkplaceMonitoringLearnerSurvey : BaseEntity
{
    public int WorkplaceMonitoringSiteVisitId { get; set; }
    public WorkplaceMonitoringSiteVisit? WorkplaceMonitoringSiteVisit { get; set; }

    public int? CompanyLearnerId { get; set; }
    public CompanyLearner? CompanyLearner { get; set; }

    public string LearnerName { get; set; } = string.Empty;
    public bool ReceivedToolbox { get; set; } = true;
    public bool ExposedToFullCurriculum { get; set; } = true;
    public bool SatisfiedWithTraining { get; set; } = true;
    public bool HasQualifiedMentor { get; set; } = true;
    public bool ReceivesStipendWage { get; set; } = true;
    public bool HasRequiredPPE { get; set; } = true;
    public string? LearnerComments { get; set; }
}
