using System.ComponentModel.DataAnnotations.Schema;
using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Represents a statutory initial registration application for ETQA Assessors and Moderators
/// per specification MerSeta\NSDMS\LMS\LR\01.
/// </summary>
public class AssessorRegistrationApplication : BaseEntity
{
    /// <summary>
    /// Statutory application tracking reference (e.g. APP-ASS-2026-00001 or APP-MOD-2026-00001).
    /// </summary>
    public string ApplicationNumber { get; set; } = string.Empty;

    /// <summary>
    /// Practitioner track (Assessor, Moderator, Assessor & Moderator).
    /// </summary>
    public string PractitionerType { get; set; } = "Assessor";

    /// <summary>
    /// Foreign key referencing the applicant's Person demographic record.
    /// </summary>
    public int PersonId { get; set; }

    /// <summary>
    /// Navigational reference to the demographic Person profile.
    /// </summary>
    public Person? Person { get; set; }

    #region School & Demographics (Spec Section 7.1)

    /// <summary>
    /// Last secondary/high school attended.
    /// </summary>
    public string? LastSchoolAttended { get; set; }

    /// <summary>
    /// Year completed at last school attended.
    /// </summary>
    public int? LastSchoolYear { get; set; }

    /// <summary>
    /// Employment standing (Employed, Unemployed).
    /// </summary>
    public string EmploymentStatusCode { get; set; } = "Employed";

    /// <summary>
    /// Disability classification type (if applicable).
    /// </summary>
    public string? DisabilityTypeCode { get; set; }

    /// <summary>
    /// Disability severity level.
    /// </summary>
    public string? DisabilitySeverityCode { get; set; }

    /// <summary>
    /// Urban or rural living location indicator.
    /// </summary>
    public string UrbanRuralArea { get; set; } = "Urban";

    /// <summary>
    /// Next of kin contact name.
    /// </summary>
    public string? NextOfKinName { get; set; }

    /// <summary>
    /// Next of kin telephone contact number.
    /// </summary>
    public string? NextOfKinContact { get; set; }

    /// <summary>
    /// Next of kin relationship to applicant (e.g. Spouse, Parent, Sibling).
    /// </summary>
    public string? NextOfKinRelationship { get; set; }

    /// <summary>
    /// Highest educational qualification title held by applicant.
    /// </summary>
    public string? HighestQualificationTitle { get; set; }

    /// <summary>
    /// Date highest qualification was obtained.
    /// </summary>
    public DateTime? HighestQualificationObtainedDate { get; set; }

    #endregion

    #region Application Lifecycle & Sign-Off (Spec Section 4.2.6)

    /// <summary>
    /// Application status (Draft, Application, VerificationPending, EvaluationPending, ReviewCommitteePending, Approved, Rejected, Withdrawn).
    /// </summary>
    public string ApplicationStatusCode { get; set; } = "Draft";
    [NotMapped]
    public string StageCode { get => ApplicationStatusCode; set => ApplicationStatusCode = value; }

    /// <summary>
    /// Indicates whether applicant completed legal acknowledgment and declaration sign-off.
    /// </summary>
    public bool IsDeclarationAcknowledged { get; set; }

    /// <summary>
    /// User ID of the practitioner or CLO representative who signed off the submission.
    /// </summary>
    public string? SignedOffByUserId { get; set; }

    /// <summary>
    /// Timestamp when application was locked and submitted for review.
    /// </summary>
    public DateTime? SignedOffAt { get; set; }
    [NotMapped]
    public DateTime? SubmissionDate { get => SignedOffAt; set => SignedOffAt = value; }

    #endregion

    #region Stage 1: Document Verification (Spec Section 4.2.4 & 7.2)

    /// <summary>
    /// Recommendation outcome from verification officer (Recommend, Reject).
    /// </summary>
    public string? VerificationRecommendation { get; set; }
    [NotMapped]
    public string? VerificationStatus { get => VerificationRecommendation; set => VerificationRecommendation = value; }

    /// <summary>
    /// Statutory recommendation reason code.
    /// </summary>
    public string? VerificationReason { get; set; }

    /// <summary>
    /// Detailed justification notes from verification officer.
    /// </summary>
    public string? VerificationExplanation { get; set; }

    /// <summary>
    /// User ID of verifying officer.
    /// </summary>
    public string? VerifiedByUserId { get; set; }

    /// <summary>
    /// Timestamp of verification completion.
    /// </summary>
    public DateTime? VerificationDate { get; set; }

    #endregion

    #region Stage 2: Application Evaluation (Spec Section 4.2.4)

    /// <summary>
    /// Recommendation outcome from evaluator (Recommend, Reject).
    /// </summary>
    public string? EvaluationRecommendation { get; set; }
    [NotMapped]
    public string? EvaluationStatus { get => EvaluationRecommendation; set => EvaluationRecommendation = value; }

    /// <summary>
    /// Evaluation statutory reason code.
    /// </summary>
    public string? EvaluationReason { get; set; }

    /// <summary>
    /// Detailed notes from evaluator.
    /// </summary>
    public string? EvaluationExplanation { get; set; }

    /// <summary>
    /// User ID of evaluating officer.
    /// </summary>
    public string? EvaluatedByUserId { get; set; }

    /// <summary>
    /// Timestamp of evaluation completion.
    /// </summary>
    public DateTime? EvaluationDate { get; set; }

    #endregion

    #region Stage 3: Review Committee (RC) Adjudication (Spec Section 4.2.4 & 7.3)

    /// <summary>
    /// Official Review Committee outcome (Approve, Reject).
    /// </summary>
    public string? ReviewCommitteeDecision { get; set; }

    /// <summary>
    /// Formal ETQA Review Committee Decision Number.
    /// </summary>
    public string? ReviewCommitteeDecisionNumber { get; set; }

    /// <summary>
    /// Date when the Review Committee meeting convened.
    /// </summary>
    public DateTime? ReviewCommitteeMeetingDate { get; set; }

    /// <summary>
    /// Review committee deliberations summary and minute notes.
    /// </summary>
    public string? ReviewCommitteeNotes { get; set; }

    /// <summary>
    /// Indicates whether committee decision marks a terminal rejection.
    /// </summary>
    public bool IsFinalRejection { get; set; }

    /// <summary>
    /// Rejection reason code from Review Committee.
    /// </summary>
    public string? RejectionReason { get; set; }

    /// <summary>
    /// Additional rejection reason comments.
    /// </summary>
    public string? RejectionComments { get; set; }

    #endregion

    #region Stage 4: Final Approval & Registration (Spec Section 4.2.4)

    /// <summary>
    /// User ID of Senior Manager / Executive granting final sign-off.
    /// </summary>
    public string? ApprovedByUserId { get; set; }

    /// <summary>
    /// Timestamp of final approval.
    /// </summary>
    public DateTime? ApprovalDate { get; set; }

    /// <summary>
    /// Final approval comments or directive.
    /// </summary>
    public string? ApprovalComments { get; set; }

    /// <summary>
    /// Immutable SHA-256 digital security seal certifying statutory registration approval.
    /// </summary>
    public string? DigitalSecuritySeal { get; set; }

    /// <summary>
    /// Foreign key referencing the active EtqaAssessor record created upon approval.
    /// </summary>
    public int? RegisteredAssessorId { get; set; }

    /// <summary>
    /// Navigational reference to the active EtqaAssessor record.
    /// </summary>
    public EtqaAssessor? RegisteredAssessor { get; set; }

    #endregion

    #region Withdrawal (Spec Section 4.2.9)

    /// <summary>
    /// Reason code if application is withdrawn by applicant.
    /// </summary>
    public string? WithdrawalReason { get; set; }

    /// <summary>
    /// Detailed notes supporting withdrawal.
    /// </summary>
    public string? WithdrawalComments { get; set; }

    /// <summary>
    /// Timestamp when withdrawal was recorded.
    /// </summary>
    public DateTime? WithdrawnAt { get; set; }

    /// <summary>
    /// User ID who initiated withdrawal.
    /// </summary>
    public string? WithdrawnByUserId { get; set; }

    #endregion

    /// <summary>
    /// Requested qualification and unit standard scopes.
    /// </summary>
    public ICollection<AssessorApplicationScope> Scopes { get; set; } = new List<AssessorApplicationScope>();

    [NotMapped]
    public ICollection<AssessorApplicationScope> ApplicationScopes { get => Scopes; set => Scopes = value; }

    /// <summary>
    /// Skills Development Provider and Employer affiliations requiring SLA agreements.
    /// </summary>
    public ICollection<AssessorApplicationProviderLink> ProviderAffiliations { get; set; } = new List<AssessorApplicationProviderLink>();

    /// <summary>
    /// Evidentiary supporting documents uploaded against application.
    /// </summary>
    public ICollection<AssessorApplicationDocument> Documents { get; set; } = new List<AssessorApplicationDocument>();
}

/// <summary>
/// Specific qualification scope requested in an initial registration application.
/// </summary>
public class AssessorApplicationScope : BaseEntity
{
    /// <summary>
    /// Foreign key referencing parent application.
    /// </summary>
    public int AssessorRegistrationApplicationId { get; set; }

    /// <summary>
    /// Navigational reference to parent application.
    /// </summary>
    public AssessorRegistrationApplication? Application { get; set; }

    /// <summary>
    /// SAQA Registered Qualification ID code.
    /// </summary>
    public int SaqaQualificationId { get; set; }

    /// <summary>
    /// Registered Qualification Title.
    /// </summary>
    public string QualificationTitle { get; set; } = string.Empty;

    /// <summary>
    /// Date when the applicant obtained this qualification (Spec Section 5: must be >= 3 years ago).
    /// </summary>
    public DateTime QualificationObtainedDate { get; set; }

    /// <summary>
    /// Indicates whether the qualification satisfies the statutory 3-year post-qualification experience rule.
    /// </summary>
    public bool IsThreeYearExperienceCompliant => (DateTime.UtcNow - QualificationObtainedDate).TotalDays >= (3 * 365.25);

    /// <summary>
    /// Constituent unit standards associated with this qualification scope.
    /// </summary>
    public ICollection<AssessorApplicationUnitStandard> UnitStandards { get; set; } = new List<AssessorApplicationUnitStandard>();
}

/// <summary>
/// Individual unit standard scope linked to a qualification or requested standalone.
/// </summary>
public class AssessorApplicationUnitStandard : BaseEntity
{
    /// <summary>
    /// Foreign key referencing parent application scope.
    /// </summary>
    public int AssessorApplicationScopeId { get; set; }

    /// <summary>
    /// Navigational reference to parent application scope.
    /// </summary>
    public AssessorApplicationScope? ApplicationScope { get; set; }

    /// <summary>
    /// SAQA Unit Standard Code (e.g. 115753).
    /// </summary>
    public string UnitStandardCode { get; set; } = string.Empty;

    /// <summary>
    /// SAQA Unit Standard Title.
    /// </summary>
    public string UnitStandardTitle { get; set; } = string.Empty;

    /// <summary>
    /// NQF Level of unit standard.
    /// </summary>
    public int NqfLevel { get; set; } = 4;

    /// <summary>
    /// Statutory credit value.
    /// </summary>
    public int Credits { get; set; } = 15;

    /// <summary>
    /// Indicates if unit standard was auto-populated from parent qualification (true = non-removable by user; false = manually added).
    /// </summary>
    public bool IsPopulatedFromQualification { get; set; } = true;
}

/// <summary>
/// Skills Development Provider (SDP) affiliation requested during registration with mandatory SLA verification.
/// </summary>
public class AssessorApplicationProviderLink : BaseEntity
{
    /// <summary>
    /// Foreign key referencing parent registration application.
    /// </summary>
    public int AssessorRegistrationApplicationId { get; set; }

    /// <summary>
    /// Navigational reference to parent application.
    /// </summary>
    public AssessorRegistrationApplication? Application { get; set; }

    /// <summary>
    /// Foreign key referencing the affiliated Training Provider / SDP.
    /// </summary>
    public int TrainingProviderId { get; set; }

    /// <summary>
    /// Navigational reference to Training Provider.
    /// </summary>
    public TrainingProvider? TrainingProvider { get; set; }

    /// <summary>
    /// File storage reference to uploaded Service Level Agreement (SLA) contract.
    /// </summary>
    public string? SlaDocumentRef { get; set; }
    [NotMapped]
    public string? SlaDocumentAttachmentReference { get => SlaDocumentRef; set => SlaDocumentRef = value; }

    /// <summary>
    /// Indicates if the SLA contract has been uploaded.
    /// </summary>
    public bool IsSlaUploaded => !string.IsNullOrWhiteSpace(SlaDocumentRef);

    /// <summary>
    /// Indicates if the SDP has officially confirmed and verified the practitioner affiliation.
    /// </summary>
    public bool IsVerifiedByProvider { get; set; }

    /// <summary>
    /// Timestamp when SDP verified affiliation.
    /// </summary>
    public DateTime? VerificationDate { get; set; }
}

/// <summary>
/// Evidentiary document attached to an assessor/moderator registration application.
/// </summary>
public class AssessorApplicationDocument : BaseEntity
{
    /// <summary>
    /// Foreign key referencing parent application.
    /// </summary>
    public int AssessorRegistrationApplicationId { get; set; }

    /// <summary>
    /// Navigational reference to parent application.
    /// </summary>
    public AssessorRegistrationApplication? Application { get; set; }

    /// <summary>
    /// Document category (ID, CV, QualificationCertificate, SlaAgreement, EmploymentProof).
    /// </summary>
    public string DocumentTypeCode { get; set; } = "ID";

    /// <summary>
    /// Display title or original file name.
    /// </summary>
    public string DocumentTitle { get; set; } = string.Empty;

    /// <summary>
    /// File system storage or blob reference path.
    /// </summary>
    public string FileStoragePath { get; set; } = string.Empty;

    /// <summary>
    /// Document version counter for version control.
    /// </summary>
    public int VersionNumber { get; set; } = 1;

    /// <summary>
    /// Verification status flag from ETQA document inspection.
    /// </summary>
    public bool IsVerified { get; set; }
}

/// <summary>
/// Registered unit standard constituent scope approved on an active EtqaAssessor profile.
/// </summary>
public class AssessorUnitStandardScope : BaseEntity
{
    /// <summary>
    /// Foreign key referencing parent AssessorModeratorScope.
    /// </summary>
    public int AssessorModeratorScopeId { get; set; }

    /// <summary>
    /// Navigational reference to parent qualification scope.
    /// </summary>
    public AssessorModeratorScope? AssessorScope { get; set; }

    /// <summary>
    /// SAQA Unit Standard Code.
    /// </summary>
    public string UnitStandardCode { get; set; } = string.Empty;

    /// <summary>
    /// SAQA Unit Standard Title.
    /// </summary>
    public string UnitStandardTitle { get; set; } = string.Empty;

    /// <summary>
    /// NQF Level.
    /// </summary>
    public int NqfLevel { get; set; } = 4;

    /// <summary>
    /// Credits value.
    /// </summary>
    public int Credits { get; set; } = 15;

    /// <summary>
    /// Indicates if unit standard was derived from approved qualification.
    /// </summary>
    public bool IsPopulatedFromQualification { get; set; } = true;
}

/// <summary>
/// Active multi-SDP affiliation for a registered ETQA practitioner.
/// </summary>
public class AssessorProviderLink : BaseEntity
{
    /// <summary>
    /// Foreign key referencing registered EtqaAssessor.
    /// </summary>
    public int EtqaAssessorId { get; set; }

    /// <summary>
    /// Navigational reference to registered practitioner.
    /// </summary>
    public EtqaAssessor? EtqaAssessor { get; set; }

    /// <summary>
    /// Foreign key referencing affiliated Training Provider / SDP.
    /// </summary>
    public int TrainingProviderId { get; set; }

    /// <summary>
    /// Navigational reference to Training Provider.
    /// </summary>
    public TrainingProvider? TrainingProvider { get; set; }

    /// <summary>
    /// File reference to signed SLA agreement.
    /// </summary>
    public string? SlaDocumentRef { get; set; }

    /// <summary>
    /// Indicates if affiliation agreement is currently in force.
    /// </summary>
    public bool IsActive { get; set; } = true;

    /// <summary>
    /// Timestamp when provider verified link.
    /// </summary>
    public DateTime? VerifiedDate { get; set; }
}

/// <summary>
/// Statutory disciplinary, investigation, suspension, and de-registration case
/// per specification MerSeta\NSDMS\LMS\LR\01 Use Case 4.2.7.
/// </summary>
public class AssessorDisciplinaryCase : BaseEntity
{
    /// <summary>
    /// Foreign key referencing registered practitioner.
    /// </summary>
    public int EtqaAssessorId { get; set; }

    /// <summary>
    /// Navigational reference to registered practitioner.
    /// </summary>
    public EtqaAssessor? EtqaAssessor { get; set; }

    /// <summary>
    /// Statutory case tracking number (e.g. DISC-2026-ASS-0042).
    /// </summary>
    public string CaseNumber { get; set; } = string.Empty;

    /// <summary>
    /// Case type (DeRegistration, Suspension, VoluntaryDeRegistration, Deceased).
    /// </summary>
    public string CaseType { get; set; } = "DeRegistration";

    /// <summary>
    /// Summary of complaint or misconduct report.
    /// </summary>
    public string ComplaintSummary { get; set; } = string.Empty;

    /// <summary>
    /// File reference to formal complaint letter.
    /// </summary>
    public string? ComplaintDocumentRef { get; set; }

    /// <summary>
    /// Date investigation initiated.
    /// </summary>
    public DateTime? InvestigationStartDate { get; set; }

    /// <summary>
    /// Date investigation concluded.
    /// </summary>
    public DateTime? InvestigationEndDate { get; set; }

    /// <summary>
    /// Summary of investigative findings and recommendations.
    /// </summary>
    public string? InvestigationReportSummary { get; set; }

    /// <summary>
    /// Official Review Committee Decision Number adjudicating the disciplinary outcome.
    /// </summary>
    public string? ReviewCommitteeDecisionNumber { get; set; }

    /// <summary>
    /// Date of Review Committee sitting.
    /// </summary>
    public DateTime? ReviewCommitteeDate { get; set; }

    /// <summary>
    /// Case outcome (DEREGISTERED, SUSPENDED, DISMISSED, DECEASED).
    /// </summary>
    public string OutcomeCode { get; set; } = "DEREGISTERED";

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public string? StatusCode { get => OutcomeCode; set => OutcomeCode = value ?? "DEREGISTERED"; }

    /// <summary>
    /// Case status (Open, UnderInvestigation, CommitteeReview, Resolved, Sanctioned, Closed).
    /// </summary>
    public string Status { get; set; } = "Open";

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public string CaseStatusCode { get => Status; set => Status = value; }

    /// <summary>
    /// If suspended, start date of disciplinary suspension period.
    /// </summary>
    public DateTime? SuspensionStartDate { get; set; }

    /// <summary>
    /// If suspended, end date of disciplinary suspension period.
    /// </summary>
    public DateTime? SuspensionEndDate { get; set; }

    /// <summary>
    /// Remedial development plan outlining corrective actions required prior to potential re-registration.
    /// </summary>
    public string? DevelopmentPlanDetails { get; set; }

    /// <summary>
    /// File reference to system-generated de-registration or suspension letter.
    /// </summary>
    public string? DecisionLetterDocumentRef { get; set; }

    /// <summary>
    /// Timestamp when case was concluded.
    /// </summary>
    public DateTime? ClosedAt { get; set; }

    /// <summary>
    /// User ID of officer concluding case.
    /// </summary>
    public string? ClosedByUserId { get; set; }
}
