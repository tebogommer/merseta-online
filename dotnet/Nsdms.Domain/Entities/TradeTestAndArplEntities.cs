using System.ComponentModel.DataAnnotations.Schema;
using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Artisan Trade Test Application and Assessment Record (Section 26D / Section 28 ARPL / Apprenticeships).
/// </summary>
public class LearnerTradeTestApplication : BaseEntity
{
    public int CompanyLearnerId { get; set; }
    public CompanyLearner? CompanyLearner { get; set; }

    public int PersonId { get; set; }
    public Person? Person { get; set; }

    public int? OrganisationId { get; set; }
    public Organisation? Organisation { get; set; }

    public int? TrainingProviderId { get; set; }
    public TrainingProvider? TrainingProvider { get; set; }

    public string ApplicationNumber { get; set; } = string.Empty;
    public string TradeTitle { get; set; } = string.Empty;
    public string? TradeOfoCode { get; set; }
    public string? DesignatedTradeLevel { get; set; }

    /// <summary>
    /// Section26D (Standard Apprenticeship), Section28 (ARPL - Recognition of Prior Learning), Section26F
    /// </summary>
    public string ApplicationTypeCode { get; set; } = "Section26D";

    public int AttemptNumber { get; set; } = 1;

    public DateTime? LearnerReadinessDate { get; set; }
    public string? AssessmentCenterName { get; set; }
    public DateTime? AssessmentDate { get; set; }
    public TimeSpan? ScheduledStartTime { get; set; }

    /// <summary>
    /// Foreign key referencing the parent NAMB submission batch.
    /// </summary>
    public int? NambSubmissionBatchId { get; set; }
    public NambSubmissionBatch? NambSubmissionBatch { get; set; }

    /// <summary>
    /// Serial number allocated by the National Artisan Moderation Body (NAMB).
    /// </summary>
    public string? NambSerialNumber { get; set; }
    public DateTime? NambSubmissionDate { get; set; }
    public DateTime? NambApprovalDate { get; set; }
    public string NambDecisionStatusCode { get; set; } = "Pending"; // Pending, Approved, Rejected

    /// <summary>
    /// Assessment outcome: Competent, NotYetCompetent, Absent, Deferred
    /// </summary>
    public string CompetencyStatusCode { get; set; } = "Pending";

    /// <summary>
    /// Final Serial Number for the Trade Certificate.
    /// </summary>
    public string? SerialCertificateNumber { get; set; }
    public DateTime? CertificateIssueDate { get; set; }

    /// <summary>
    /// Workflow status: Draft, Submitted, TradeCenterAllocated, AwaitingNambApproval, Assessing, Competent, Certified, Rejected
    /// </summary>
    public string StatusCode { get; set; } = "Draft";

    [NotMapped]
    public string Status { get => StatusCode; set => StatusCode = value; }

    public string? AssessorName { get; set; }
    public string? AssessorRegistrationNumber { get; set; }
    public string? ModeratorName { get; set; }
    public string? ModeratorRegistrationNumber { get; set; }
    public string? Notes { get; set; }

    // Collections
    public ICollection<TradeTestTask> Tasks { get; set; } = new List<TradeTestTask>();
    public ICollection<ArplExperienceDetail> ExperienceDetails { get; set; } = new List<ArplExperienceDetail>();
    public ICollection<ArplTrainingDetail> TrainingDetails { get; set; } = new List<ArplTrainingDetail>();
    public ICollection<NambDecisionHistory> NambHistories { get; set; } = new List<NambDecisionHistory>();
}

/// <summary>
/// Practical Task and Scoring Item evaluated during the Trade Test.
/// </summary>
public class TradeTestTask : BaseEntity
{
    public int LearnerTradeTestApplicationId { get; set; }
    public LearnerTradeTestApplication? LearnerTradeTestApplication { get; set; }

    public int TaskNumber { get; set; }
    public string TaskTitle { get; set; } = string.Empty;
    public string? TaskDescription { get; set; }
    public decimal TotalMarksAvailable { get; set; } = 100m;
    public decimal MarksObtained { get; set; } = 0m;
    public decimal PassPercentage { get; set; } = 70m;
    public decimal PercentageAchieved { get; set; } = 0m;
    public bool IsCompetent { get; set; } = false;
    public string? AssessorComments { get; set; }
}

/// <summary>
/// Artisan Recognition of Prior Learning (ARPL) Portfolio Assessment record.
/// </summary>
public class ArplTradeTestInformation : BaseEntity
{
    public int LearnerTradeTestApplicationId { get; set; }
    public LearnerTradeTestApplication? LearnerTradeTestApplication { get; set; }

    public int YearsOfExperienceInTrade { get; set; }
    public string? CurrentEmployerName { get; set; }
    public string? EmployerContactPersonName { get; set; }
    public string? EmployerContactPhone { get; set; }

    public bool PortfolioOfEvidenceVerified { get; set; } = false;
    public decimal PortfolioScorePercentage { get; set; } = 0m;

    public string? WorkplaceMentorName { get; set; }
    public string? PortfolioAssessorUserId { get; set; }
    public DateTime? PortfolioAssessmentDate { get; set; }

    public bool ToolkitChecklistVerified { get; set; } = false;
    public string ArplRecommendation { get; set; } = "ProceedToTradeTest"; // ProceedToTradeTest, RequiresBridgingTraining, Rejected
}

/// <summary>
/// Historical work experience item claimed under ARPL.
/// </summary>
public class ArplExperienceDetail : BaseEntity
{
    public int LearnerTradeTestApplicationId { get; set; }
    public LearnerTradeTestApplication? LearnerTradeTestApplication { get; set; }

    public string EmployerName { get; set; } = string.Empty;
    public string JobTitle { get; set; } = string.Empty;
    public DateTime StartDate { get; set; }
    public DateTime? EndDate { get; set; }
    public string DutiesDescription { get; set; } = string.Empty;
    public string? EvidenceDocumentName { get; set; }
}

/// <summary>
/// Prior formal or non-formal training modules completed by an ARPL candidate.
/// </summary>
public class ArplTrainingDetail : BaseEntity
{
    public int LearnerTradeTestApplicationId { get; set; }
    public LearnerTradeTestApplication? LearnerTradeTestApplication { get; set; }

    public string InstitutionName { get; set; } = string.Empty;
    public string CourseOrModuleTitle { get; set; } = string.Empty;
    public DateTime? CompletionDate { get; set; }
    public string? CertificateObtained { get; set; }
}

/// <summary>
/// National Artisan Moderation Body (NAMB) adjudication trace and serial assignment log.
/// </summary>
public class NambDecisionHistory : BaseEntity
{
    public int LearnerTradeTestApplicationId { get; set; }
    public LearnerTradeTestApplication? LearnerTradeTestApplication { get; set; }

    public string? NambOfficialUserId { get; set; }
    public string NambOfficerName { get; set; } = string.Empty;
    public string DecisionStatusCode { get; set; } = "Approved"; // Approved, QueryRaised, Rejected
    public string? DecisionNotes { get; set; }
    public DateTime DecisionDate { get; set; } = DateTime.UtcNow;
    public string? NambBatchReference { get; set; }
}
