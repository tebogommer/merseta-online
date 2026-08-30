using System.ComponentModel.DataAnnotations.Schema;
using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

#region WSP Qualitative Survey & Strategic Skills Gap Entities

/// <summary>
/// Captures identified organizational skills gaps, root causes, and targeted interventions for WSP/ATR submissions.
/// </summary>
public class WspStrategicSkillsGap : BaseEntity
{
    public int WspId { get; set; }
    public WspSubmission? Wsp { get; set; }

    public string OccupationTitle { get; set; } = string.Empty;
    public string? OfoCode { get; set; }
    public string SkillGapDescription { get; set; } = string.Empty;
    public string? CauseOfGap { get; set; }
    public string? PlannedIntervention { get; set; }
    public string PriorityLevel { get; set; } = "High"; // High, Medium, Low
    public int TargetLearnerCount { get; set; } = 1;
    public decimal EstimatedBudget { get; set; } = 0;
    public bool IsActive { get; set; } = true;
}

/// <summary>
/// Qualitative training impact questionnaire evaluating the business effectiveness of previous year training.
/// </summary>
public class WspTrainingImpactSurvey : BaseEntity
{
    public int WspId { get; set; }
    public WspSubmission? Wsp { get; set; }

    public string SurveyCategory { get; set; } = string.Empty; // Productivity, Compliance, Innovation, Safety, Retention
    public string QuestionText { get; set; } = string.Empty;
    public int RatingScore { get; set; } = 5; // 1 to 5 scale
    public string? QualitativeImpactNotes { get; set; }
    public string? EvidenceDocumentUrl { get; set; }
}

/// <summary>
/// Strategic priorities and alignment with National Skills Development Plan (NSDP) goals in WSP submissions.
/// </summary>
public class WspStrategicPriority : BaseEntity
{
    public int WspId { get; set; }
    public WspSubmission? Wsp { get; set; }

    public string PriorityCode { get; set; } = string.Empty; // 4IR, GREEN_ECONOMY, LOCAL_MANUFACTURING, YOUTH_DEV
    public string StrategicObjective { get; set; } = string.Empty;
    public string AlignmentDescription { get; set; } = string.Empty;
    public decimal AllocatedBudget { get; set; } = 0;
    public bool IsAlignedWithNsdp { get; set; } = true;
}

#endregion

#region AQP (Assessment Quality Partner) Quality Partner & EISA Entities

/// <summary>
/// Assessment Quality Partner (AQP) accredited by QCTO to develop assessment instruments and manage EISA exams.
/// </summary>
public class AqpPartner : BaseEntity
{
    public string AqpName { get; set; } = string.Empty;
    public string AqpCode { get; set; } = string.Empty;
    public string AccreditationNumber { get; set; } = string.Empty;
    public string QualityAssuranceBody { get; set; } = "QCTO";
    
    public int? ContactPersonId { get; set; }
    public Person? ContactPerson { get; set; }

    public string? Email { get; set; }
    public string? PhoneNumber { get; set; }
    public string? PhysicalAddress { get; set; }
    public string? PostalCode { get; set; }
    public string ProvinceCode { get; set; } = "GP";

    public DateTime AccreditationStartDate { get; set; } = DateTime.UtcNow;
    public DateTime AccreditationEndDate { get; set; } = DateTime.UtcNow.AddYears(5);
    public string StatusCode { get; set; } = "Active"; // Active, Suspended, UnderReview, Expired
    public bool IsActive { get; set; } = true;

    public List<AqpQualificationScope> Scopes { get; set; } = new();
    public List<AqpLearnerAssessment> Assessments { get; set; } = new();
}

/// <summary>
/// Qualifications in scope for which the AQP is authorized to administer external summative assessments (EISA).
/// </summary>
public class AqpQualificationScope : BaseEntity
{
    public int AqpPartnerId { get; set; }
    public AqpPartner? AqpPartner { get; set; }

    public string QualificationTitle { get; set; } = string.Empty;
    public string? SaqaQualificationId { get; set; }
    public int NqfLevel { get; set; } = 4;
    public string? CurriculumCode { get; set; }
    public string AssessmentModel { get; set; } = "EISA"; // EISA, Practical, Portfolio, Integrated
    public bool IsActive { get; set; } = true;
}

/// <summary>
/// External Integrated Summative Assessment (EISA) record administered by an AQP for a registered learner.
/// </summary>
public class AqpLearnerAssessment : BaseEntity
{
    public int AqpPartnerId { get; set; }
    public AqpPartner? AqpPartner { get; set; }

    public int? CompanyLearnerId { get; set; }
    public CompanyLearner? CompanyLearner { get; set; }

    public int? PersonId { get; set; }
    public Person? Person { get; set; }

    public string AssessmentNumber { get; set; } = string.Empty;
    public string EisaExamSession { get; set; } = string.Empty; // e.g. 2026-OCT-EISA-01
    public DateTime AssessmentDate { get; set; } = DateTime.UtcNow;
    public string AssessmentCenter { get; set; } = string.Empty;
    
    public decimal? TheoryScorePercentage { get; set; }
    public decimal? PracticalScorePercentage { get; set; }
    public decimal FinalOverallPercentage { get; set; } = 0;
    
    public string ResultStatusCode { get; set; } = "Pending"; // Competent, NotYetCompetent, Absent, Deferred, Pending
    public string ModerationStatusCode { get; set; } = "Pending"; // Approved, Endorsed, FlaggedForRecheck, Pending
    public string? CertificateNumber { get; set; }
    public DateTime? CertificateIssuedDate { get; set; }
    public string? ModeratorComments { get; set; }
}

#endregion
