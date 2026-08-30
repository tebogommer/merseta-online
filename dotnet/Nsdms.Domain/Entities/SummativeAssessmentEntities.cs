using System.ComponentModel.DataAnnotations.Schema;
using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Comprehensive Summative Assessment Report capturing unit standard credits, EISA exams, and moderation outcomes.
/// </summary>
public class SummativeAssessmentReport : BaseEntity
{
    public int CompanyLearnerId { get; set; }
    public CompanyLearner? CompanyLearner { get; set; }

    public int PersonId { get; set; }
    public Person? Person { get; set; }

    public int? OrganisationId { get; set; }
    public Organisation? Organisation { get; set; }

    public int? TrainingProviderId { get; set; }
    public TrainingProvider? TrainingProvider { get; set; }

    public string ReportNumber { get; set; } = string.Empty;
    public string QualificationTitle { get; set; } = string.Empty;
    public string? SaqaQualificationId { get; set; }
    public int NqfLevel { get; set; } = 4;
    public string InterventionTypeCode { get; set; } = "Learnership"; // Learnership, SkillsProgramme, UnitStandard, ShortCourse

    public DateTime AssessmentDate { get; set; } = DateTime.UtcNow;
    public DateTime? ModerationDate { get; set; }

    public int? AssessorPersonId { get; set; }
    public Person? AssessorPerson { get; set; }
    public string? AssessorRegistrationNumber { get; set; }

    public int? InternalModeratorPersonId { get; set; }
    public Person? InternalModeratorPerson { get; set; }
    public string? InternalModeratorRegistrationNumber { get; set; }

    public string? ExternalModeratorUserId { get; set; }
    public DateTime? ExternalModeratorApprovalDate { get; set; }
    public string? ExternalModeratorComments { get; set; }

    public int TotalCreditsEarned { get; set; } = 0;
    public int TotalCreditsRequired { get; set; } = 120;

    /// <summary>
    /// Workflow status: Draft, Assessed, InternalModerated, EtqaModerated, CreditsApproved, SorIssued, Rejected
    /// </summary>
    public string StatusCode { get; set; } = "Draft";

    [NotMapped]
    public string Status { get => StatusCode; set => StatusCode = value; }

    // Collections
    public ICollection<SummativeAssessmentUnitStandard> UnitStandardAssessments { get; set; } = new List<SummativeAssessmentUnitStandard>();
    public ICollection<EisaAssessmentEntry> EisaEntries { get; set; } = new List<EisaAssessmentEntry>();
    public ICollection<StatementOfResults> StatementOfResultsList { get; set; } = new List<StatementOfResults>();
}

/// <summary>
/// Unit Standard credit assessment and moderation outcome line item.
/// </summary>
public class SummativeAssessmentUnitStandard : BaseEntity
{
    public int SummativeAssessmentReportId { get; set; }
    public SummativeAssessmentReport? SummativeAssessmentReport { get; set; }

    public string UnitStandardCode { get; set; } = string.Empty;
    public string UnitStandardTitle { get; set; } = string.Empty;
    public int NqfLevel { get; set; } = 4;
    public int Credits { get; set; } = 10;

    public DateTime AssessmentDate { get; set; } = DateTime.UtcNow;
    public string CompetencyStatusCode { get; set; } = "Competent"; // Competent, NotYetCompetent
    public string? AssessorComments { get; set; }

    public bool IsModerated { get; set; } = false;
    public string ModerationOutcome { get; set; } = "Upheld"; // Upheld, Overturned
    public string? ModeratorComments { get; set; }
}

/// <summary>
/// External Integrated Summative Assessment (EISA) exam entry for QCTO occupational qualifications.
/// </summary>
public class EisaAssessmentEntry : BaseEntity
{
    public int SummativeAssessmentReportId { get; set; }
    public SummativeAssessmentReport? SummativeAssessmentReport { get; set; }

    public DateTime EisaAssessmentDate { get; set; } = DateTime.UtcNow;
    public string EisaCenterName { get; set; } = string.Empty;
    public string AssessmentPaperCode { get; set; } = string.Empty;

    public decimal ScoreAchieved { get; set; } = 0m;
    public decimal TotalScorePossible { get; set; } = 100m;
    public decimal PercentageScore { get; set; } = 0m;

    public string CompetencyStatusCode { get; set; } = "Competent"; // Competent, NotYetCompetent
    public string? QctoModerationReferenceNumber { get; set; }
    public DateTime? QctoSignOffDate { get; set; }
}

/// <summary>
/// Formal merSETA Statement of Results (SOR) document record with cryptographic tamper-proof hash.
/// </summary>
public class StatementOfResults : BaseEntity
{
    public int SummativeAssessmentReportId { get; set; }
    public SummativeAssessmentReport? SummativeAssessmentReport { get; set; }

    public int CompanyLearnerId { get; set; }
    public CompanyLearner? CompanyLearner { get; set; }

    public int PersonId { get; set; }
    public Person? Person { get; set; }

    public string SorSerialNumber { get; set; } = string.Empty;
    public DateTime DateIssued { get; set; } = DateTime.UtcNow;
    public int TotalCreditsCertified { get; set; }

    /// <summary>
    /// SHA-256 integrity hash for instant online verification.
    /// </summary>
    public string TamperProofHashSha256 { get; set; } = string.Empty;
    public string? QrVerificationUrl { get; set; }
    public string IssuedByUserId { get; set; } = "SYSTEM";
}
