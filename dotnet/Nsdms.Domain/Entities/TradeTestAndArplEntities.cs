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
    /// Preferred Trade Test Centre (TTC) for the examination.
    /// </summary>
    public int? PreferredTradeTestCenterId { get; set; }
    public TrainingProvider? PreferredTradeTestCenter { get; set; }

    [NotMapped]
    public string? PreferredTradeCenterCode { get => PreferredTradeTestCenter?.AccreditationNumber ?? PreferredTradeTestCenterId?.ToString(); set { } }

    /// <summary>
    /// Relational Link to SAQA Qualification / Listed Trade.
    /// </summary>
    public int? QualificationId { get; set; }

    /// <summary>
    /// Trade specialisation under the designated trade.
    /// </summary>
    public string? Specialisation { get; set; }

    /// <summary>
    /// Statutory employment status: Employed vs Unemployed (Section 4.2.3 of Signed Use Case).
    /// </summary>
    public string EmploymentStatus { get; set; } = "Employed"; // Employed, Unemployed

    /// <summary>
    /// Name of employer if non-registered or private provider context (Section 4.2.3).
    /// </summary>
    public string? UnregisteredEmployerName { get; set; }

    /// <summary>
    /// Indicates whether the candidate attempted a trade test previously.
    /// </summary>
    public bool HasAttemptedTradeTestPreviously { get; set; } = false;
    public int? PreviousTrainingCenterId { get; set; }
    public string? PreviousAssessmentCenterName { get; set; }
    public DateTime? PreviousAttemptDate { get; set; }
    public int? PreviousAttemptsCount { get; set; }

    /// <summary>
    /// Formal statutory learner submission timestamp.
    /// </summary>
    public DateTime? LearnerSubmissionDate { get; set; }

    /// <summary>
    /// Indicates whether the designated trade requires an ARPL toolkit assessment (Section 5).
    /// </summary>
    public bool RequiresToolkit { get; set; } = false;

    /// <summary>
    /// Statutory ARPL Qualifying Category (Categories 1-8 per Section 5).
    /// </summary>
    public ArplQualifyingCategory? QualifyingCategory { get; set; }

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
    /// Date when the TTC 5-day advance notice was issued.
    /// </summary>
    public DateTime? NoticeDispatchedDate { get; set; }

    /// <summary>
    /// Statutory 5-day post-assessment results upload deadline (Section 5).
    /// </summary>
    public DateTime? ResultsUploadDeadlineDate { get; set; }

    #region Tier 1: Regional CLA Recommendation Flow (Section 4.2.4)
    public string? ClaUserId { get; set; }
    [NotMapped]
    public string? ClaRecommendedBy { get => ClaUserId; set => ClaUserId = value; }
    public DateTime? ClaRecommendationDate { get; set; }
    public string? ClaRecommendationStatus { get; set; } // Recommended, Rejected
    public string? ClaRejectionReason { get; set; }
    #endregion

    #region Tier 2: Regional QA Approval & Stamping Flow (Section 4.2.4)
    public string? QaUserId { get; set; }
    public DateTime? QaApprovalDate { get; set; }
    public string? QaApprovalStatus { get; set; } // Approved, Rejected
    public bool IsFinalRejection { get; set; } = false;
    public string? QaRejectionReason { get; set; }
    public int? QaSignedApplicationDocumentAttachmentId { get; set; }
    public DocumentAttachment? QaSignedApplicationDocumentAttachment { get; set; }
    /// <summary>
    /// Official Trade Test Serial Number generated upon QA Approval (committed to record).
    /// </summary>
    public string? TradeTestSerialNumber { get; set; }
    #endregion

    #region NAMB Evidence Pack & Scanned Certificate Ingestion (Section 4.2.6)
    public int? NambPackDocumentAttachmentId { get; set; }
    public DateTime? NambPackVerifiedAt { get; set; }
    public string? NambPackVerifiedByUserId { get; set; }
    public int? ScannedCertificateDocumentAttachmentId { get; set; }
    public DocumentAttachment? ScannedCertificateDocumentAttachment { get; set; }
    #endregion

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
    /// Quality Assurance 10% achievement audit sampling flag (DFD Step 4.0).
    /// </summary>
    public bool IsSelectedForQaAuditSample { get; set; } = false;
    public string QaAuditSampleStatus { get; set; } = "None"; // None, Selected, AuditedPassed, AuditedFailed

    /// <summary>
    /// Final Serial Number for the Trade Certificate.
    /// </summary>
    public string? SerialCertificateNumber { get; set; }
    public DateTime? CertificateIssueDate { get; set; }

    /// <summary>
    /// Application withdrawal fields.
    /// </summary>
    public string? WithdrawalReasonCode { get; set; }
    public string? WithdrawalNotes { get; set; }
    public DateTime? WithdrawnAt { get; set; }
    public string? WithdrawnBy { get; set; }
    public bool IsWithdrawn { get; set; } = false;
    [NotMapped]
    public DateTime? WithdrawalDate { get => WithdrawnAt; set => WithdrawnAt = value; }
    [NotMapped]
    public string? WithdrawalReason { get => WithdrawalNotes; set => WithdrawalNotes = value; }
    public DateTime? CertificateDistributedAt { get; set; }

    /// <summary>
    /// Workflow status: Draft, SavedDraft, Submitted, RejectedForResubmission, Resubmitted, Recommended, Registered, TradeCenterAllocated, Assessing, Competent, Certified, Rejected, Withdrawn
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
    public ICollection<LearnerTradeTestWithdrawal> Withdrawals { get; set; } = new List<LearnerTradeTestWithdrawal>();
    public ICollection<ArplDocumentChecklist> DocumentChecklists { get; set; } = new List<ArplDocumentChecklist>();
    public ICollection<CertificateDistributionEvent> CertificateDistributions { get; set; } = new List<CertificateDistributionEvent>();
    [NotMapped]
    public ICollection<CertificateDistributionEvent> DistributionEvents => CertificateDistributions;
}

/// <summary>
/// Statutory ARPL Qualifying Criteria Categories per Section 5 of Use Case.
/// </summary>
public enum ArplQualifyingCategory
{
    Category1_Min3Years_N2 = 1,
    Category2_Min3Years_NqfLevel3 = 2,
    Category3_Min3Years_TechnicalGrade12 = 3,
    Category4_Min18Months_NcvLevel4 = 4,
    Category5_Min18Months_N6OrNationalDiploma = 5,
    Category6_Min4Years_Grade9 = 6,
    Category7_Min3Years_ToolkitAssessment = 7,
    Category8_Min2Years_TradeLearnershipNqf2To4 = 8
}

/// <summary>
/// Practical Task and Scoring Item evaluated during the Trade Test.
/// </summary>
public class TradeTestTask : BaseEntity
{
    public int LearnerTradeTestApplicationId { get; set; }
    public LearnerTradeTestApplication? LearnerTradeTestApplication { get; set; }

    public int AttemptNumber { get; set; } = 1;
    public int TaskNumber { get; set; }
    public string? TaskCode { get; set; }
    public string TaskTitle { get; set; } = string.Empty;
    public string? TaskDescription { get; set; }
    public decimal TotalMarksAvailable { get; set; } = 100m;
    public decimal MarksObtained { get; set; } = 0m;
    public decimal PassPercentage { get; set; } = 70m;
    public decimal PercentageAchieved { get; set; } = 0m;
    public bool IsCompetent { get; set; } = false;

    /// <summary>
    /// Statutory 50% task credit retention (valid for max 3 attempts or 18 months).
    /// </summary>
    public bool IsRetainedCredit { get; set; } = false;
    public DateTime? CreditRetentionExpiryDate { get; set; }

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
    public string? CompanyRegistrationNumber { get; set; }
    public string? ContactPersonName { get; set; }
    public string? ContactPhoneNumber { get; set; }
    public string? EmployerAddress { get; set; }
    public string JobTitle { get; set; } = string.Empty;
    public DateTime StartDate { get; set; }
    public DateTime? EndDate { get; set; }
    public decimal YearsOfExperience { get; set; } = 0m;
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

/// <summary>
/// Non-repudiation audit record for withdrawn ARPL and Trade Test applications (Section 4.2.8).
/// </summary>
public class LearnerTradeTestWithdrawal : BaseEntity
{
    public int LearnerTradeTestApplicationId { get; set; }
    public LearnerTradeTestApplication? LearnerTradeTestApplication { get; set; }

    public string WithdrawalReasonCode { get; set; } = "CandidateRequested";
    public string WithdrawalJustification { get; set; } = string.Empty;
    [NotMapped]
    public string Justification { get => WithdrawalJustification; set => WithdrawalJustification = value; }
    public string WithdrawnByUserId { get; set; } = "SYSTEM";
    public DateTime WithdrawalDate { get; set; } = DateTime.UtcNow;
    [NotMapped]
    public DateTime WithdrawnAt { get => WithdrawalDate; set => WithdrawalDate = value; }
}

/// <summary>
/// Statutory ARPL document verification checklist gate (Section 4.2.7 & Section 5).
/// Supports both Toolkit Trades (7 documents) and Non-Toolkit Trades (6 documents).
/// </summary>
public class ArplDocumentChecklist : BaseEntity
{
    public int LearnerTradeTestApplicationId { get; set; }
    public LearnerTradeTestApplication? LearnerTradeTestApplication { get; set; }

    /// <summary>
    /// Statutory Document Type Codes:
    /// - STATEMENT_OF_RESULTS (Statement of results - Toolkit)
    /// - ARPL_APPLICATION_FORM (ARPL trade test application form - Both)
    /// - CERTIFIED_ID_PASSPORT (Certified copy of ID or passport - Both)
    /// - LEGAL_STATUS_PROOF (Documentary proof showing legal stay in SA - Both foreign)
    /// - NAMB_EVIDENCE_PACK (Confirmation of NAMB evidence packs submitted - Toolkit)
    /// - SERVICE_LETTER (Workplace service letters - Both)
    /// - POE_CHECKLIST (Portfolio of Evidence Checklist - Toolkit)
    /// - CERTIFIED_HIGHEST_QUAL (Certified copy of highest qualification - Non-toolkit)
    /// - PRE_ASSESSMENT_REPORT (Pre-assessment evaluation report from TTC - Non-toolkit)
    /// </summary>
    public string DocumentTypeCode { get; set; } = string.Empty;
    public string DocumentTitle { get; set; } = string.Empty;

    public int? DocumentAttachmentId { get; set; }
    [NotMapped]
    public int? AttachmentId { get => DocumentAttachmentId; set => DocumentAttachmentId = value; }
    public DocumentAttachment? DocumentAttachment { get; set; }

    public bool IsUploaded { get; set; } = false;
    public DateTime? UploadedAt { get; set; }
    public string? UploadedByUserId { get; set; }

    public bool IsVerified { get; set; } = false;
    public string? VerifiedByUserId { get; set; }
    public DateTime? VerifiedDate { get; set; }
    [NotMapped]
    public DateTime? VerifiedAt { get => VerifiedDate; set => VerifiedDate = value; }
    public string? RejectionReason { get; set; }
    [NotMapped]
    public string? VerificationComments { get => RejectionReason; set => RejectionReason = value; }

    public static string GetDocumentTitle(string code) => code switch
    {
        "STATEMENT_OF_RESULTS" => "Statement of Results",
        "ARPL_APPLICATION_FORM" => "ARPL Trade Test Application Form",
        "CERTIFIED_ID_PASSPORT" => "Certified Copy of ID / Passport",
        "LEGAL_STATUS_PROOF" => "Documentary Proof of Legal Stay in South Africa",
        "NAMB_EVIDENCE_PACK" => "Confirmation of NAMB Evidence Packs Submitted",
        "SERVICE_LETTER" => "Workplace Service Letter(s)",
        "POE_CHECKLIST" => "Portfolio of Evidence (POE) Checklist",
        "CERTIFIED_HIGHEST_QUAL" => "Certified Copy of Highest Qualification",
        "PRE_ASSESSMENT_REPORT" => "Pre-Assessment Evaluation Report from TTC",
        _ => code
    };
}

/// <summary>
/// Certificate Distribution Event tracking for National Red Seal certificates (Section 4.2.6).
/// </summary>
public class CertificateDistributionEvent : BaseEntity
{
    public int LearnerTradeTestApplicationId { get; set; }
    public LearnerTradeTestApplication? LearnerTradeTestApplication { get; set; }

    public string DistributionMethodCode { get; set; } = "RegisteredMail"; // Collection, Courier, RegisteredMail
    public string? ConsignmentOrTrackingNumber { get; set; }
    [NotMapped]
    public string? TrackingOrWaybillNumber { get => ConsignmentOrTrackingNumber; set => ConsignmentOrTrackingNumber = value; }
    public DateTime DispatchedDate { get; set; } = DateTime.UtcNow;
    [NotMapped]
    public DateTime DispatchedAt { get => DispatchedDate; set => DispatchedDate = value; }
    public string? RecipientName { get; set; }
    public string? RecipientIdNumber { get; set; }
    public DateTime? ReceivedDate { get; set; }
    public string? DispatchedByUserId { get; set; }
    public string? Notes { get; set; }
}
