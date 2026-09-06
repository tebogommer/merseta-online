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

    public int? AssessmentBatchId { get; set; }
    public AssessmentBatch? AssessmentBatch { get; set; }

    public string ReportNumber { get; set; } = string.Empty;
    public string QualificationTitle { get; set; } = string.Empty;
    public string? SaqaQualificationId { get; set; }
    public int NqfLevel { get; set; } = 4;
    public string InterventionTypeCode { get; set; } = "Learnership"; // Learnership, SkillsProgramme, UnitStandard, ShortCourse

    /// <summary>
    /// Assessment Stage: Progress (Partial) or Completion (Full Qualification).
    /// </summary>
    public string AssessmentStageCode { get; set; } = "Completion"; // Progress, Completion

    /// <summary>
    /// Whether the learner's employer is MerSETA funded (levy paying or grant funded).
    /// </summary>
    public bool IsFundedEmployer { get; set; } = true;

    /// <summary>
    /// Formal early exit reason code: Resigned, Deceased, MedicalIllness, or null if normal progression.
    /// </summary>
    public string? EarlyExitReasonCode { get; set; }

    /// <summary>
    /// Statutory credit compliance flag indicating whether Core and Fundamental requirements are satisfied.
    /// </summary>
    public bool CreditComplianceMet { get; set; } = false;

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
    /// Workflow status: DraftHoldingRoom, Assessed, Batched, InExternalModerationPool, 
    /// InternalModerated, EtqaModerated, CreditsApproved, SorIssued, RejectedRemedialRequired
    /// </summary>
    public string StatusCode { get; set; } = "DraftHoldingRoom";

    [NotMapped]
    public string Status { get => StatusCode; set => StatusCode = value; }

    // Collections
    public ICollection<SummativeAssessmentUnitStandard> UnitStandardAssessments { get; set; } = new List<SummativeAssessmentUnitStandard>();
    public ICollection<EisaAssessmentEntry> EisaEntries { get; set; } = new List<EisaAssessmentEntry>();
    public ICollection<StatementOfResults> StatementOfResultsList { get; set; } = new List<StatementOfResults>();
    public ICollection<AssessmentBatchLearner> BatchLearnerLinks { get; set; } = new List<AssessmentBatchLearner>();
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

    /// <summary>
    /// Core, Fundamental, Elective
    /// </summary>
    public string UnitStandardTypeCode { get; set; } = "Core";
    public bool IsMandatory { get; set; } = true;
    public bool IsNonMandatoryElective { get; set; } = false;

    public int? AssessorPersonId { get; set; }
    public Person? AssessorPerson { get; set; }

    public int? InternalModeratorPersonId { get; set; }
    public Person? InternalModeratorPerson { get; set; }

    public DateTime AssessmentDate { get; set; } = DateTime.UtcNow;
    public decimal ScoreAchieved { get; set; } = 0m;
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
/// Aggregates individual summative assessment reports into a QA external moderation batch
/// per Use Case 4.2.1 (Holding Room & Batching) and 4.2.2 (External Moderation).
/// </summary>
public class AssessmentBatch : BaseEntity
{
    public string BatchNumber { get; set; } = string.Empty; // e.g. MOD-BATCH-2026-00042
    public int TrainingProviderId { get; set; }
    public TrainingProvider? TrainingProvider { get; set; }

    public string QualificationTitle { get; set; } = string.Empty;
    public string? SaqaQualificationId { get; set; }

    /// <summary>
    /// Progress (Partial credits) or Completion (Full Programme).
    /// </summary>
    public string AssessmentStageCode { get; set; } = "Completion";

    /// <summary>
    /// Statutory Moderation Sample Size Percentage: 10, 30, 50, 100.
    /// </summary>
    public int SamplePercentage { get; set; } = 10;
    public int TotalLearnersCount { get; set; } = 0;
    public int SampledLearnersCount { get; set; } = 0;

    /// <summary>
    /// Uploaded Internal Moderation Report reference (Annexure 3).
    /// </summary>
    public string? InternalModerationReportDocumentRef { get; set; }
    public DateTime? LastInternalModerationDate { get; set; }

    /// <summary>
    /// Workflow status: DraftHoldingRoom, Batched, InExternalModerationPool, 
    /// SiteVisitScheduled, Upheld, RejectedRemedialRequired, Finalised.
    /// </summary>
    public string StatusCode { get; set; } = "Batched";

    // QA Scheduling
    public DateTime? ScheduledSiteVisitDate { get; set; }
    public bool IsSiteVisitRequired { get; set; } = true;
    public string? SiteVisitSchedulingComments { get; set; }
    public string? AssignedQaUserId { get; set; }
    public int? ContactPersonId { get; set; }

    // Navigation collections
    public ICollection<AssessmentBatchLearner> BatchLearners { get; set; } = new List<AssessmentBatchLearner>();
    public ICollection<ModerationChecklistEtqTp043> ModerationChecklists { get; set; } = new List<ModerationChecklistEtqTp043>();
    public ICollection<SummativeAssessmentReport> Reports { get; set; } = new List<SummativeAssessmentReport>();
}

/// <summary>
/// Junction linking SummativeAssessmentReport to an AssessmentBatch with statutory sampling indicator.
/// </summary>
public class AssessmentBatchLearner : BaseEntity
{
    public int AssessmentBatchId { get; set; }
    public AssessmentBatch? AssessmentBatch { get; set; }

    public int SummativeAssessmentReportId { get; set; }
    public SummativeAssessmentReport? SummativeAssessmentReport { get; set; }

    public bool IsSelectedInSample { get; set; } = false;
    public string LearnerOutcomeStatus { get; set; } = "Pending"; // Pending, Upheld, Rejected
    public string? RejectionReasonCodes { get; set; }
}

/// <summary>
/// Official ETQ-TP-043 Moderation or Validation Report of Summative Assessments.
/// </summary>
public class ModerationChecklistEtqTp043 : BaseEntity
{
    public int AssessmentBatchId { get; set; }
    public AssessmentBatch? AssessmentBatch { get; set; }

    public string ValidationBatchNumber { get; set; } = string.Empty;
    public string QualityAssurorUserId { get; set; } = string.Empty;
    public DateTime DateOfModeration { get; set; } = DateTime.UtcNow;

    public string StageOfModerationCode { get; set; } = "Completion"; // Progress, Completion
    public string ValidationDecisionCode { get; set; } = "Upheld"; // Upheld, Rejected

    // VACS and Rejection Taxonomy
    public string? PrimaryRejectionReasonCode { get; set; } // AssessmentGuidesNonCompliant, ResultsIncorrectlyCaptured, VacsNonCompliant
    public string? VacsPrincipleViolatedCode { get; set; } // NotValid, NotAuthentic, NotCurrent, NotSufficient
    public string? RejectionRemarks { get; set; }
    public string? RemedialActionRequired { get; set; }

    public string? ReportDocumentReference { get; set; }
    public string TamperProofHashSha256 { get; set; } = string.Empty;

    public ICollection<ModerationChecklistItem> ChecklistItems { get; set; } = new List<ModerationChecklistItem>();
}

/// <summary>
/// Individual line-item compliance check for ETQ-TP-043 Sections 1 & 2.
/// </summary>
public class ModerationChecklistItem : BaseEntity
{
    public int ModerationChecklistEtqTp043Id { get; set; }
    public ModerationChecklistEtqTp043? ModerationChecklist { get; set; }

    public int SectionNumber { get; set; } = 1; // 1: External Moderation Compliance, 2: Verification of Internal Moderation
    public string CriteriaTitle { get; set; } = string.Empty;
    public string EvidenceRequirements { get; set; } = string.Empty;
    public bool IsCompliant { get; set; } = true;
    public string? Comments { get; set; }
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
    /// FullAchievement vs PartialAchievement.
    /// </summary>
    public string AchievementTypeCode { get; set; } = "FullAchievement";
    public string? EarlyExitReasonCode { get; set; }

    /// <summary>
    /// SHA-256 integrity hash for instant online verification.
    /// </summary>
    public string TamperProofHashSha256 { get; set; } = string.Empty;
    public string? QrVerificationUrl { get; set; }
    public string IssuedByUserId { get; set; } = "SYSTEM";
}

/// <summary>
/// Batch of qualification certificates sent to printer with consolidated release/distribution letters.
/// </summary>
public class CertificatePrintingBatch : BaseEntity
{
    public string PrintingBatchNumber { get; set; } = string.Empty; // e.g. CPB-2026-00045
    public DateTime BatchGeneratedDate { get; set; } = DateTime.UtcNow;
    public int TotalCertificatesCount { get; set; } = 0;
    public string? ConsolidatedPdfDocumentRef { get; set; }
    public string? ConsolidatedDistributionLettersPdfRef { get; set; }
    public string StatusCode { get; set; } = "QueuedForPrinting"; // QueuedForPrinting, Printed, Distributed

    public ICollection<LearnerCertificate> Certificates { get; set; } = new List<LearnerCertificate>();
    public ICollection<DistributionLetter> DistributionLetters { get; set; } = new List<DistributionLetter>();
}

/// <summary>
/// Statutory qualification certificate issued upon full completion.
/// </summary>
public class LearnerCertificate : BaseEntity
{
    public int CompanyLearnerId { get; set; }
    public CompanyLearner? CompanyLearner { get; set; }

    public int PersonId { get; set; }
    public Person? Person { get; set; }

    public int? CertificatePrintingBatchId { get; set; }
    public CertificatePrintingBatch? CertificatePrintingBatch { get; set; }

    public int SummativeAssessmentReportId { get; set; }
    public SummativeAssessmentReport? SummativeAssessmentReport { get; set; }

    /// <summary>
    /// Statutory Number Formula: '17' + Middle 4 digits of ID/DOB + 6 random/sequential numbers.
    /// </summary>
    public string CertificateNumber { get; set; } = string.Empty;
    public string QualificationTitle { get; set; } = string.Empty;
    public string? SaqaQualificationId { get; set; }
    public int NqfLevel { get; set; } = 4;
    public DateTime IssueDate { get; set; } = DateTime.UtcNow; // Must match moderation approval date

    public string TamperProofHashSha256 { get; set; } = string.Empty;
    public bool IsReprintOrReplacement { get; set; } = false;
    public string? ReplacementReason { get; set; }

    public ICollection<ScannedCertificateAttachment> ScannedAttachments { get; set; } = new List<ScannedCertificateAttachment>();
    public ICollection<AssessmentCertificateDistributionEvent> DistributionEvents { get; set; } = new List<AssessmentCertificateDistributionEvent>();
}

/// <summary>
/// Release / Distribution letter generated per batch and training provider accreditation number.
/// </summary>
public class DistributionLetter : BaseEntity
{
    public int CertificatePrintingBatchId { get; set; }
    public CertificatePrintingBatch? CertificatePrintingBatch { get; set; }

    public int TrainingProviderId { get; set; }
    public TrainingProvider? TrainingProvider { get; set; }
    public string ProviderAccreditationNumber { get; set; } = string.Empty;

    public string LetterReferenceNumber { get; set; } = string.Empty;
    public string? DocumentReferenceUrl { get; set; }
    public DateTime GeneratedDate { get; set; } = DateTime.UtcNow;
}

/// <summary>
/// Non-destructive physical scanned certificate repository attached to the learner's record.
/// </summary>
public class ScannedCertificateAttachment : BaseEntity
{
    public int LearnerCertificateId { get; set; }
    public LearnerCertificate? LearnerCertificate { get; set; }

    public int PersonId { get; set; }
    public Person? Person { get; set; }

    public string DocumentStorageKey { get; set; } = string.Empty;
    public string FileName { get; set; } = string.Empty;
    public long FileSizeBytes { get; set; }
    public string ScannedByUserId { get; set; } = string.Empty;
    public DateTime ScannedAt { get; set; } = DateTime.UtcNow;
    [NotMapped]
    public DateTime UploadedAt => ScannedAt;
    public string? OcrExtractedIdNumber { get; set; }
    public string? OcrExtractedCertificateNumber { get; set; }
    public bool IsVerifiedMatch { get; set; } = false;
}

/// <summary>
/// Dispatch and distribution event for summative qualification certificates.
/// </summary>
public class AssessmentCertificateDistributionEvent : BaseEntity
{
    public int LearnerCertificateId { get; set; }
    public LearnerCertificate? LearnerCertificate { get; set; }

    public string DistributionMethodCode { get; set; } = "RegisteredMail"; // Collection, Courier, RegisteredMail
    public string? WaybillOrTrackingNumber { get; set; }
    public DateTime DispatchedDate { get; set; } = DateTime.UtcNow;
    public string? RecipientName { get; set; }
    public string? RecipientIdNumber { get; set; }
    public DateTime? ReceivedDate { get; set; }
    public string? DispatchNotes { get; set; }
}
