using System.ComponentModel.DataAnnotations.Schema;
using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Represents a learner registration agreement linked to an employer organisation, training provider,
/// and MerSETA learning programme, capturing all statutory fields for SETMIS Files 500, 501, 502, and 506.
/// </summary>
public class CompanyLearner : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the demographic Person record (names, RSA ID, contact details).
    /// </summary>
    public int PersonId { get; set; }

    /// <summary>
    /// Navigational reference to the learner's demographic Person profile.
    /// </summary>
    public Person? Person { get; set; }

    /// <summary>
    /// Optional foreign key referencing the host or sponsoring Employer Organisation (optional for unemployed bursaries).
    /// </summary>
    public int? OrganisationId { get; set; }

    /// <summary>
    /// Navigational reference to the sponsoring Employer Organisation.
    /// </summary>
    public Organisation? Organisation { get; set; }

    /// <summary>
    /// Optional foreign key referencing the specific host branch / plant facility.
    /// </summary>
    public int? OrganisationSiteId { get; set; }

    /// <summary>
    /// Navigational reference to the specific host workplace facility.
    /// </summary>
    public OrganisationSite? OrganisationSite { get; set; }

    /// <summary>
    /// Optional foreign key referencing the accredited Skills Development Training Provider (SDP).
    /// </summary>
    public int? TrainingProviderId { get; set; }

    /// <summary>
    /// Navigational reference to the accredited Training Provider.
    /// </summary>
    public TrainingProvider? TrainingProvider { get; set; }

    /// <summary>
    /// Unique MerSETA-generated learner agreement / contract registration number.
    /// </summary>
    public string? LearnerContractNumber { get; set; } = string.Empty;

    /// <summary>
    /// Title of the registered SAQA / QCTO qualification or learning programme.
    /// </summary>
    public string? QualificationTitle { get; set; } = string.Empty;

    /// <summary>
    /// SAQA Registered Qualification ID code (SETMIS File 501, 505, 506).
    /// </summary>
    public int? SaqaQualificationId { get; set; }

    /// <summary>
    /// National Qualifications Framework (NQF) level descriptor (e.g. 2, 3, 4, 5).
    /// </summary>
    public int? NqfLevel { get; set; }

    /// <summary>
    /// Classification code of the learning programme (references lookup.LearningProgrammeType: 01 Apprenticeship, 02 Learnership, 03 Skills Programme, 04 Internship, 05 Bursary).
    /// </summary>
    public string? LearningProgrammeTypeCode { get; set; } = "02";

    /// <summary>
    /// Registered SAQA Learnership ID Code (e.g. 18Q180026241203) for SETMIS File 500 and 501.
    /// </summary>
    public string? LearnershipId { get; set; }

    /// <summary>
    /// Registered Non-NQF Skills Programme or Course Code for SETMIS File 502 (references SkillsRegistration.CourseCode).
    /// </summary>
    public string? NonNqfInterventionCode { get; set; }

    /// <summary>
    /// Programme hierarchy and articulation type code (references lookup.PartOfType: 01 Stand-alone, 02 Part of Qualification, 03 Part of Learnership).
    /// </summary>
    public string? PartOfId { get; set; } = "01";

    /// <summary>
    /// Learner delivery mode classification code (references lookup.EnrolmentType: 01 Contact, 02 Distance, 03 Mixed Mode, 04 Workplace Based).
    /// </summary>
    public string? EnrolmentTypeId { get; set; } = "01";

    /// <summary>
    /// Current statutory enrolment lifecycle status code (references lookup.EnrolmentStatusType: 01 Enrolled, 02 Achieved, 03 Certificated, 04 Terminated, 05 Transferred).
    /// </summary>
    public string? EnrolmentStatusId { get; set; } = "01";

    [NotMapped]
    public string? EnrolmentStatus { get => EnrolmentStatusId; set => EnrolmentStatusId = value; }

    [NotMapped]
    public string? Status { get => EnrolmentStatusId; set => EnrolmentStatusId = value; }

    /// <summary>
    /// Date when current enrolment status milestone became effective (SETMIS Files 500, 501, 502).
    /// </summary>
    public DateTime? EnrolmentStatusDate { get; set; }

    /// <summary>
    /// Statutory reason code for contract termination or status change (references lookup.EnrolmentStatusReasonType).
    /// </summary>
    public string? EnrolmentStatusReasonId { get; set; }

    /// <summary>
    /// Registered ETQA Assessor registration number assigned to this learner agreement (SETMIS Files 500, 501, 502).
    /// </summary>
    public string? AssessorRegistrationNumber { get; set; }

    /// <summary>
    /// Submitting ETQA ID of the registered Assessor (references lookup.SetaType, default 17 for merSETA).
    /// </summary>
    public string? AssessorEtqaId { get; set; } = "17";

    /// <summary>
    /// Secondary practical training provider code for decentralized apprenticeship workplace programmes (SETMIS File 501).
    /// </summary>
    public string? PracticalProviderCode { get; set; }

    /// <summary>
    /// Secondary practical training provider ETQA ID (default 17).
    /// </summary>
    public string? PracticalProviderEtqaId { get; set; } = "17";

    /// <summary>
    /// DHET Organising Framework for Occupations statutory code (references lookup.OfoCodeType, e.g. 264202 Editor, 651202 Welder).
    /// </summary>
    public string? OfoCode { get; set; }

    /// <summary>
    /// Learner economic employment standing code (references lookup.EconomicStatusType: 01 Employed 18.1, 02 Unemployed 18.2, 03 Student).
    /// </summary>
    public string? EconomicStatusId { get; set; } = "01";

    /// <summary>
    /// Geographic intervention area classification (references lookup.UrbanRuralType: 01 Urban, 02 Rural, 98 Unknown).
    /// </summary>
    public string? UrbanRuralId { get; set; } = "01";

    /// <summary>
    /// Cumulative Discretionary Grant / Levy stipend funding disbursed to date in ZAR.
    /// </summary>
    public decimal CumulativeSpend { get; set; } = 0.00m;

    /// <summary>
    /// SAQA or merSETA National Certificate Serial Number awarded upon successful achievement.
    /// </summary>
    public string? CertificateNumber { get; set; }

    #region Internship Placement Fields (SETMIS File 506)
    /// <summary>
    /// Prior completed TVET / Higher Education qualification ID achieved before entering internship.
    /// </summary>
    public string? PriorQualificationId { get; set; }

    /// <summary>
    /// Date when prior qualification was achieved.
    /// </summary>
    public DateTime? PriorQualificationAchievementDate { get; set; }

    /// <summary>
    /// Work Integrated Learning / Internship milestone standing (references lookup.InternshipStatusType: 01 In Progress, 02 Completed, 03 Withdrawn).
    /// </summary>
    public string? InternshipStatusId { get; set; }
    #endregion

    /// <summary>
    /// Funding vehicle classification code (e.g. MandatoryGrant, DiscretionaryGrant, SelfFunded).
    /// </summary>
    public string? FundingTypeCode { get; set; } = "DiscretionaryGrant";

    /// <summary>
    /// Funding source lookup identifier (references lookup.FundingType: 01 SETA Funded, 02 Employer Funded, 03 NSF).
    /// </summary>
    public string? FundingId { get; set; } = "01";

    /// <summary>
    /// Legacy enrolment status code mapping.
    /// </summary>
    public string? EnrolmentStatusCode { get; set; } = "Registered";

    [NotMapped]
    public string? StatusCode { get => EnrolmentStatusCode; set => EnrolmentStatusCode = value ?? "Registered"; }

    [NotMapped]
    public string? LearnerStatusCode { get => EnrolmentStatusCode; set => EnrolmentStatusCode = value ?? "Registered"; }

    /// <summary>
    /// Operational in-state status code per signed Learner Management specification Section 5
    /// (e.g. Active, TransferApplication, Transferred, TerminationPending, Terminated, Withdrawal, RequirementsNotMet).
    /// Preserves statutory EnrolmentStatusCode = "Registered" / "Enrolled".
    /// </summary>
    public string InstateStatusCode { get; set; } = "Active";

    /// <summary>
    /// Timestamp when the operational in-state status was last transitioned.
    /// </summary>
    public DateTime InstateStatusDate { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Official date when the learner contract was officially registered with MerSETA.
    /// </summary>
    public DateTime RegistrationDate { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Commencement start date of training in the workplace / provider.
    /// </summary>
    public DateTime? CommencementDate { get; set; }

    /// <summary>
    /// Projected completion date based on registered programme curriculum duration.
    /// </summary>
    public DateTime? ExpectedCompletionDate { get; set; }

    /// <summary>
    /// Actual completion or certification signoff date.
    /// </summary>
    public DateTime? CompletionDate { get; set; }

    /// <summary>
    /// MerSETA regional office responsible for managing this learner agreement.
    /// </summary>
    public string? SetaRegion { get; set; }

    /// <summary>
    /// MerSETA industrial chamber code (e.g. Auto, Metal, Plastic, Motor, New Tyre).
    /// </summary>
    public string? ChamberCode { get; set; }

    /// <summary>
    /// Indicates whether the learner record is active in reporting and SETMIS submissions.
    /// </summary>
    public bool IsActive { get; set; } = true;

    #region Statutory Registration & Sign-Off Attributes (Use Case Specification)
    /// <summary>
    /// Date when the learner physically or electronically signed the agreement.
    /// Used to enforce the statutory 30-working-day submission deadline.
    /// </summary>
    public DateTime? LearnerSignatureDate { get; set; }

    /// <summary>
    /// System auto-generated date and time when the application was formally submitted to merSETA.
    /// </summary>
    public DateTime? SubmissionDate { get; set; }

    /// <summary>
    /// Appointed role title of the individual executing the submission sign-off.
    /// </summary>
    public string? SignatoryRoleTitle { get; set; }

    /// <summary>
    /// Optional foreign key referencing the Person who signed off the application.
    /// </summary>
    public int? SignatoryPersonId { get; set; }

    /// <summary>
    /// Professional council candidate registration number (e.g. ECSA Candidate Engineer) for Candidacy programmes.
    /// </summary>
    public string? ProfessionalRegistrationNumber { get; set; }

    /// <summary>
    /// Reason code if the learner agreement application is withdrawn prior to approval.
    /// </summary>
    public string? WithdrawalReasonCode { get; set; }

    /// <summary>
    /// Detailed comments or justification recorded upon application withdrawal.
    /// </summary>
    public string? WithdrawalComments { get; set; }

    [NotMapped]
    public string? ApprovalWorkflowStatus { get => EnrolmentStatusCode; set => EnrolmentStatusCode = value ?? "Registered"; }

    [NotMapped]
    public string? ReviewNotes { get => WithdrawalComments; set => WithdrawalComments = value; }

    /// <summary>
    /// Indicates whether the contracting partner is a Non-Employer Entity (TVET, CET, HEI, NGO).
    /// </summary>
    public bool IsNonEmployerEntity { get; set; } = false;

    /// <summary>
    /// External SETA identifier for non-employer entities paying levies to other SETAs.
    /// </summary>
    public string? ExternalSetaId { get; set; }

    /// <summary>
    /// Flags whether modifications have been made to this application prior to approval.
    /// </summary>
    public bool HasPendingModifications { get; set; } = false;

    #region Bursary Registration & Continuation Attributes (Signed 2022 Use Case)
    /// <summary>
    /// Classification of the bursary submission: "New" for initial qualification, "Continuation" for next academic year.
    /// </summary>
    public string? BursaryApplicationTypeCode { get; set; } = "New";

    /// <summary>
    /// Indicates whether this record is an academic continuation of an existing active bursary.
    /// </summary>
    public bool IsContinuation { get; set; } = false;

    /// <summary>
    /// Foreign key referencing the preceding active CompanyLearner bursary record when IsContinuation is true.
    /// </summary>
    public int? PreviousCompanyLearnerId { get; set; }

    /// <summary>
    /// Navigational reference to the previous academic year bursary record.
    /// </summary>
    public CompanyLearner? PreviousCompanyLearner { get; set; }

    /// <summary>
    /// Current academic funding year for the bursary (e.g. 2026).
    /// </summary>
    public int? AcademicYear { get; set; }

    /// <summary>
    /// Current academic year level of study (e.g. 1 = 1st Year, 2 = 2nd Year, 3 = 3rd Year, 4 = 4th Year, 5 = Honours, 6 = Masters).
    /// </summary>
    public int? YearOfStudy { get; set; }

    /// <summary>
    /// Statutory Bursary funding category (references lookup.BursaryFundingType):
    /// 01 merSETA funded, 02 Non-merSETA funded, 03 Employer funded, 04 Learner funded, 05 Other SETA funded, 06 NSF funded, 07 Industry funded.
    /// </summary>
    public string? BursaryFundingTypeCode { get; set; }

    /// <summary>
    /// Higher Education Institution (HEI) or TVET College name where the learner is registered.
    /// </summary>
    public string? InstitutionName { get; set; }

    /// <summary>
    /// Classification of the educational institution: "PublicUniversity", "TvetCollege", "PrivateHEI".
    /// </summary>
    public string? InstitutionTypeCode { get; set; }

    /// <summary>
    /// Employment standing of the bursary applicant: "Employed", "Unemployed".
    /// Unemployed bursars do not require host employer details per Section 5 of the Bursary Use Case.
    /// </summary>
    public string? EmploymentStatusCode { get; set; } = "Unemployed";

    /// <summary>
    /// Verification flag indicating whether prior year academic results were submitted and verified for continuation.
    /// </summary>
    public bool? ContinuationAcademicResultsPassed { get; set; }
    #endregion

    #region Dual-Channel Registration (Manual Teller vs Automated STP Engine)
    /// <summary>
    /// Registration intake channel: "ManualWizard" (The Teller) or "AutomatedBulk" (The ATM).
    /// </summary>
    public string RegistrationChannel { get; set; } = "ManualWizard";

    /// <summary>
    /// Optional foreign key referencing the bulk ingestion batch if registered via automated fast-track.
    /// </summary>
    public int? IngestionBatchId { get; set; }

    /// <summary>
    /// Indicates whether this registration was straight-through processed by the automated rule engine.
    /// </summary>
    public bool StpApproved { get; set; } = false;

    /// <summary>
    /// Reason or evaluation notes from the Straight-Through Processing rule engine.
    /// </summary>
    public string? StpDecisionReason { get; set; }
    #endregion
    #endregion

    /// <summary>
    /// Trade tests and competency assessments undertaken by this learner.
    /// </summary>
    public ICollection<LearnerTradeTest> TradeTests { get; set; } = new List<LearnerTradeTest>();

    /// <summary>
    /// Formative and summative unit standard assessments linked to this learner agreement.
    /// </summary>
    public ICollection<LearnerAssessment> Assessments { get; set; } = new List<LearnerAssessment>();

    /// <summary>
    /// Registered unit standards or skills set components linked to this agreement.
    /// </summary>
    public ICollection<LearnerRegisteredUnitStandard> RegisteredUnitStandards { get; set; } = new List<LearnerRegisteredUnitStandard>();

    /// <summary>
    /// Pre-registration and contract extension requests linked to this agreement.
    /// </summary>
    public ICollection<CompanyLearnerExtension> Extensions { get; set; } = new List<CompanyLearnerExtension>();

    /// <summary>
    /// Inter-employer and inter-SDP transfers linked to this agreement.
    /// </summary>
    public ICollection<CompanyLearnerTransfer> Transfers { get; set; } = new List<CompanyLearnerTransfer>();

    /// <summary>
    /// Certified lost training times linked to this agreement.
    /// </summary>
    public ICollection<CompanyLearnerLostTime> LostTimes { get; set; } = new List<CompanyLearnerLostTime>();

    /// <summary>
    /// Cancellation and termination requests linked to this agreement.
    /// </summary>
    public ICollection<CompanyLearnerTermination> Terminations { get; set; } = new List<CompanyLearnerTermination>();
}
