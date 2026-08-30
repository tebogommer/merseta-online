using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

public class WspSubmission : BaseEntity
{
    public int OrganisationId { get; set; }
    public Organisation? Organisation { get; set; }

    public int FinYear { get; set; }
    public string ReferenceNumber { get; set; } = string.Empty;
    public string? StatusCode { get; set; }
    public DateTime? SubmissionDate { get; set; }
    public decimal PlannedTrainingBudget { get; set; }
    public int EmployeeCount { get; set; }

    public ICollection<WspEmploymentSummary> EmploymentSummaries { get; set; } = new List<WspEmploymentSummary>();
    public ICollection<WspTrainingPlan> TrainingPlans { get; set; } = new List<WspTrainingPlan>();
}

public class LevyFile : BaseEntity
{
    public string FileName { get; set; } = string.Empty;
    public string FileRef { get; set; } = string.Empty;
    public DateTime ImportDate { get; set; } = DateTime.UtcNow;
    public int TotalRecords { get; set; }
    public decimal TotalAmount { get; set; }
    public string? StatusCode { get; set; }

    public ICollection<LevyFileLine> LineItems { get; set; } = new List<LevyFileLine>();
}

public class LevyFileLine : BaseEntity
{
    public int LevyFileId { get; set; }
    public LevyFile? LevyFile { get; set; }

    public string SdlNumber { get; set; } = string.Empty;
    public string SchemeYear { get; set; } = string.Empty;
    public decimal MandatoryLevyAmount { get; set; }
    public decimal DiscretionaryLevyAmount { get; set; }
    public decimal AdminLevyAmount { get; set; }
    public decimal QctoLevyAmount { get; set; }
    public decimal InterestAmount { get; set; }
    public decimal PenaltyAmount { get; set; }
    public decimal TotalLevyAmount { get; set; }
    public bool IsReconciled { get; set; } = false;
}

public class GrantApplication : BaseEntity
{
    public int OrganisationId { get; set; }
    public Organisation? Organisation { get; set; }

    public int? FundingWindowId { get; set; }
    public GrantFundingWindow? FundingWindow { get; set; }

    public string ApplicationNumber { get; set; } = string.Empty;
    public string? GrantTypeCode { get; set; }
    public string? StatusCode { get; set; }
    public decimal RequestedAmount { get; set; }
    public decimal? ApprovedAmount { get; set; }
    public DateTime ApplicationDate { get; set; } = DateTime.UtcNow;
    public string ProjectTitle { get; set; } = string.Empty;

    public ICollection<GrantProjectBudget> ProjectBudgets { get; set; } = new List<GrantProjectBudget>();
}

public class EtqaAssessor : BaseEntity
{
    public int PersonId { get; set; }
    public Person? Person { get; set; }

    public string RegistrationNumber { get; set; } = string.Empty;
    public string EtqaRole { get; set; } = "Assessor"; // Assessor, Moderator
    public string? StatusCode { get; set; }
    public DateTime StartDate { get; set; }
    public DateTime EndDate { get; set; }
    public bool IsActive { get; set; } = true;

    public ICollection<AssessorModeratorScope> Scopes { get; set; } = new List<AssessorModeratorScope>();
    public ICollection<LearnerAssessment> Assessments { get; set; } = new List<LearnerAssessment>();
}
