using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Nsdms.Domain.Entities;

#region Module 1: Banking Details Verification & Dual-Signoff
[Table("BankingDetails")]
public class BankingDetails
{
    [Key]
    public int Id { get; set; }

    public int? OrganisationId { get; set; }
    public Organisation? Organisation { get; set; }

    public int? TrainingProviderId { get; set; }
    public TrainingProvider? TrainingProvider { get; set; }

    [Required, MaxLength(100)]
    public string BankName { get; set; } = string.Empty;

    [Required, MaxLength(50)]
    public string BranchCode { get; set; } = string.Empty;

    [MaxLength(100)]
    public string? BranchName { get; set; }

    [Required, MaxLength(50)]
    public string AccountNumber { get; set; } = string.Empty;

    [Required, MaxLength(50)]
    public string AccountHolderName { get; set; } = string.Empty;

    [Required, MaxLength(50)]
    public string AccountTypeCode { get; set; } = "Current"; // Current, Savings, Transmission

    [MaxLength(200)]
    public string? BankConfirmationDocumentPath { get; set; }

    public int? BankConfirmationDocumentId { get; set; }

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public int? BankConfirmationAttachmentId { get => BankConfirmationDocumentId; set => BankConfirmationDocumentId = value; }

    public DateTime? BankConfirmationDate { get; set; }

    [Required, MaxLength(50)]
    public string ApprovalStatusCode { get; set; } = "PendingVerification"; // PendingVerification, FirstSignoffApproved, FullyApproved, Rejected

    public string? FirstSignoffUserId { get; set; }
    public DateTime? FirstSignoffDate { get; set; }
    public string? FirstSignoffNotes { get; set; }

    public string? SecondSignoffUserId { get; set; }
    public DateTime? SecondSignoffDate { get; set; }
    public string? SecondSignoffNotes { get; set; }

    public bool IsErpActive { get; set; } = false;
    public string? ErpVendorId { get; set; }
    public DateTime? ErpSyncDate { get; set; }

    // Option C: Real-Time AVS & Anti-Fraud Shield
    public bool RequiresForensicApproval { get; set; } = false;
    public string? FraudRiskFlags { get; set; }
    public bool IsCoolingOffActive { get; set; } = false;
    public DateTime? CoolingOffExpiresAt { get; set; }
    public string? AvsVerificationReference { get; set; }
    public DateTime? AvsVerifiedAt { get; set; }
    public string? AvsStatusResponse { get; set; }

    public bool IsActive { get; set; } = true;
    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    public string CreatedBy { get; set; } = "SYSTEM";
    public DateTime? ModifiedAt { get; set; }
    public string? ModifiedBy { get; set; }
}

[Table("BankingDetailsAudit")]
public class BankingDetailsAudit
{
    [Key]
    public int Id { get; set; }

    public int BankingDetailsId { get; set; }
    public BankingDetails? BankingDetails { get; set; }

    [Required, MaxLength(100)]
    public string ActionType { get; set; } = string.Empty;

    public string? PreviousStateJson { get; set; }
    public string? NewStateJson { get; set; }

    [Required, MaxLength(100)]
    public string ChangedByUserId { get; set; } = "SYSTEM";

    public DateTime ChangedAt { get; set; } = DateTime.UtcNow;
    public string? IpAddress { get; set; }
}
#endregion

#region Module 2: SDF Registration & Access Appointments
[Table("SdfCompany")]
public class SdfCompany
{
    [Key]
    public int Id { get; set; }

    public int OrganisationId { get; set; }
    public Organisation? Organisation { get; set; }

    public int PersonId { get; set; }
    public Person? Person { get; set; }

    [Required, MaxLength(50)]
    public string SdfTypeCode { get; set; } = "Primary"; // Primary, Secondary, Labour

    [Required, MaxLength(50)]
    public string SdfStatusCode { get; set; } = "PendingApproval"; // PendingApproval, Approved, Terminated, Rejected

    [NotMapped]
    public string AppointmentStatusCode { get => SdfStatusCode; set => SdfStatusCode = value; }

    public DateTime AppointmentStartDate { get; set; } = DateTime.UtcNow;
    public DateTime? AppointmentEndDate { get; set; }

    [MaxLength(250)]
    public string? AppointmentLetterDocumentPath { get; set; }

    public bool SignedAppointmentLetterReceived { get; set; } = false;
    public bool SignedAcceptanceDeclarationReceived { get; set; } = false;

    public string? ApprovedByUserId { get; set; }
    public DateTime? ApprovalDate { get; set; }
    public string? ApprovalComments { get; set; }

    public bool AllowWspSubmission { get; set; } = true;
    public bool AllowDgApplication { get; set; } = true;
    public bool AllowTrancheClaims { get; set; } = false;

    public bool IsActive { get; set; } = true;
    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    public string CreatedBy { get; set; } = "SYSTEM";
    public DateTime? ModifiedAt { get; set; }
    public string? ModifiedBy { get; set; }
}

[Table("SdfAppointmentHistory")]
public class SdfAppointmentHistory
{
    [Key]
    public int Id { get; set; }

    public int SdfCompanyId { get; set; }
    public SdfCompany? SdfCompany { get; set; }

    [Required, MaxLength(50)]
    public string PreviousStatusCode { get; set; } = string.Empty;

    [Required, MaxLength(50)]
    public string NewStatusCode { get; set; } = string.Empty;

    public string? ChangeReason { get; set; }

    [Required, MaxLength(100)]
    public string ChangedByUserId { get; set; } = "SYSTEM";

    public DateTime ChangedAt { get; set; } = DateTime.UtcNow;
}
#endregion

#region Module 3: Active Contract Addenda & Project Extension Engine
[Table("ContractAddenda")]
public class ContractAddenda
{
    [Key]
    public int Id { get; set; }

    public int GrantMoaId { get; set; }
    public GrantMoa? GrantMoa { get; set; }

    [Required, MaxLength(50)]
    public string AddendaNumber { get; set; } = string.Empty; // ADD-2026-0001

    [Required, MaxLength(50)]
    public string VariationTypeCode { get; set; } = "TimelineExtension"; // TimelineExtension, BudgetReallocation, ScopeAdjustment, BeneficiaryReduction

    public decimal OriginalContractValue { get; set; }
    public decimal RevisedContractValue { get; set; }
    public decimal ContractValueVariance => RevisedContractValue - OriginalContractValue;

    public DateTime OriginalEndDate { get; set; }
    public DateTime RevisedEndDate { get; set; }

    [Required, MaxLength(1000)]
    public string MotivationReason { get; set; } = string.Empty;

    [Required, MaxLength(50)]
    public string StatusCode { get; set; } = "Draft"; // Draft, SubmittedForReview, LegalApproved, ExecutiveApproved, Rejected

    [NotMapped]
    public string AddendaTypeCode { get => VariationTypeCode; set => VariationTypeCode = value; }

    [NotMapped]
    public DateTime? RevisedContractEndDate { get => RevisedEndDate; set => RevisedEndDate = value ?? DateTime.MinValue; }

    [NotMapped]
    public string ApprovalStatusCode { get => StatusCode; set => StatusCode = value; }

    [NotMapped]
    public decimal AdjustmentAmount => ContractValueVariance;

    public string? LegalReviewerUserId { get; set; }
    public DateTime? LegalReviewDate { get; set; }
    public string? LegalReviewComments { get; set; }

    public string? ExecutiveApprovedByUserId { get; set; }
    public DateTime? ExecutiveApprovalDate { get; set; }

    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    public string CreatedBy { get; set; } = "SYSTEM";
    public DateTime? ModifiedAt { get; set; }
    public string? ModifiedBy { get; set; }
}

[Table("ContractExtensionRequest")]
public class ContractExtensionRequest
{
    [Key]
    public int Id { get; set; }

    public int GrantMoaId { get; set; }
    public GrantMoa? GrantMoa { get; set; }

    [Required, MaxLength(50)]
    public string RequestNumber { get; set; } = string.Empty;

    public int RequestedExtensionMonths { get; set; }
    public DateTime CurrentEndDate { get; set; }
    public DateTime ProposedNewEndDate { get; set; }

    [Required, MaxLength(1000)]
    public string ProjectProgressStatus { get; set; } = string.Empty;

    [Required, MaxLength(1000)]
    public string MitigationPlanSummary { get; set; } = string.Empty;

    [Required, MaxLength(50)]
    public string StatusCode { get; set; } = "Submitted"; // Submitted, RecommendedByProjectManager, ApprovedByExecutive, Rejected

    public string? ReviewedByUserId { get; set; }
    public DateTime? ReviewDate { get; set; }
    public string? ReviewerComments { get; set; }

    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    public string CreatedBy { get; set; } = "SYSTEM";
    public DateTime? ModifiedAt { get; set; }
    public string? ModifiedBy { get; set; }
}

[Table("ContractTerminationRequest")]
public class ContractTerminationRequest
{
    [Key]
    public int Id { get; set; }

    public int GrantMoaId { get; set; }
    public GrantMoa? GrantMoa { get; set; }

    [Required, MaxLength(50)]
    public string TerminationNumber { get; set; } = string.Empty;

    [Required, MaxLength(50)]
    public string TerminationReasonCode { get; set; } = "MutualAgreement"; // MutualAgreement, EmployerLiquidation, NonPerformance, FraudBreach

    public decimal TotalFundsDisbursedToDate { get; set; }
    public decimal TotalValueDeliverablesAchieved { get; set; }
    public decimal ClawbackAmountRecoverable { get; set; }

    [Required, MaxLength(1000)]
    public string DetailedMotivation { get; set; } = string.Empty;

    [Required, MaxLength(50)]
    public string StatusCode { get; set; } = "PendingLegalReview"; // PendingLegalReview, ApprovedForClawback, TerminatedSettled, Cancelled

    public string? SettledByUserId { get; set; }
    public DateTime? SettlementDate { get; set; }

    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    public string CreatedBy { get; set; } = "SYSTEM";
    public DateTime? ModifiedAt { get; set; }
    public string? ModifiedBy { get; set; }
}
#endregion

#region Module 4: SDP & Assessor Extension of Scope & Re-Accreditation
[Table("SdpExtensionOfScope")]
public class SdpExtensionOfScope
{
    [Key]
    public int Id { get; set; }

    public int TrainingProviderId { get; set; }
    public TrainingProvider? TrainingProvider { get; set; }

    [Required, MaxLength(50)]
    public string ApplicationNumber { get; set; } = string.Empty; // EOS-2026-0001

    [Required, MaxLength(250)]
    public string AdditionalQualificationTitle { get; set; } = string.Empty;

    [MaxLength(50)]
    public string? SaqaQualificationId { get; set; }

    public int NqfLevel { get; set; }
    public int Credits { get; set; }

    [Required, MaxLength(50)]
    public string ProgrammeTypeCode { get; set; } = "OccupationalCertificate"; // Learnership, SkillsProgramme, OccupationalCertificate, UnitStandard

    public bool SiteInspectionPassed { get; set; } = false;
    public DateTime? SiteInspectionDate { get; set; }
    public string? SiteEvaluatorUserId { get; set; }

    [NotMapped]
    public bool SiteInspectionRequired => !SiteInspectionPassed;

    [Required, MaxLength(50)]
    public string StatusCode { get; set; } = "Submitted"; // Submitted, UnderSiteEvaluation, CommitteeReview, Approved, Rejected

    public string? ApprovedByUserId { get; set; }
    public DateTime? ApprovalDate { get; set; }
    public string? CommitteeDecisionReference { get; set; }

    [NotMapped]
    public string? ReviewComments { get => CommitteeDecisionReference; set => CommitteeDecisionReference = value; }

    [NotMapped]
    public DateTime? ApprovedDate { get => ApprovalDate; set => ApprovalDate = value; }

    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    public string CreatedBy { get; set; } = "SYSTEM";
    public DateTime? ModifiedAt { get; set; }
    public string? ModifiedBy { get; set; }
}

[Table("SdpReAccreditationApplication")]
public class SdpReAccreditationApplication
{
    [Key]
    public int Id { get; set; }

    public int TrainingProviderId { get; set; }
    public TrainingProvider? TrainingProvider { get; set; }

    [Required, MaxLength(50)]
    public string ApplicationNumber { get; set; } = string.Empty; // REACC-2026-0001

    public DateTime CurrentAccreditationExpiryDate { get; set; }
    public DateTime ProposedAccreditationExpiryDate { get; set; }

    [NotMapped]
    public DateTime CurrentAccreditationEndDate { get => CurrentAccreditationExpiryDate; set => CurrentAccreditationExpiryDate = value; }

    [NotMapped]
    public int RequestedDurationYears => 5;

    [NotMapped]
    public decimal AuditScorePercentage => QmsComplianceAudited ? 100m : 0m;

    public bool QmsComplianceAudited { get; set; } = false;
    public bool FacilitatorAssessorRatiosCompliant { get; set; } = false;
    public bool OshSafetyCertificatesValid { get; set; } = false;

    [Required, MaxLength(50)]
    public string StatusCode { get; set; } = "UnderReview"; // UnderReview, AuditPassed, CouncilEndorsed, ReAccredited, ExpiredLapsed

    public string? CouncilDecisionNumber { get; set; }
    public DateTime? ReAccreditationDecisionDate { get; set; }

    public string? PreviousAccreditationNumber { get; set; }
    public DateTime? PreviousStartDate { get; set; }
    public DateTime? PreviousEndDate { get; set; }
    public DateTime? RequestedStartDate { get; set; }
    public DateTime? RequestedEndDate { get; set; }
    public string? CommitteeDecisionNumber { get; set; }
    public DateTime? CommitteeMeetingDate { get; set; }
    public string? RenewalAuditReportRef { get; set; }
    [NotMapped]
    public string Status { get => StatusCode; set => StatusCode = value; }

    [NotMapped]
    public DateTime ApplicationDate { get => CreatedAt; set => CreatedAt = value; }

    [NotMapped]
    public bool IsAuditPassed { get => QmsComplianceAudited; set => QmsComplianceAudited = value; }

    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    public string CreatedBy { get; set; } = "SYSTEM";
    public DateTime? ModifiedAt { get; set; }
    public string? ModifiedBy { get; set; }
}

public class SdpReAccreditation : SdpReAccreditationApplication
{
}

[Table("AssessorExtensionOfScope")]
public class AssessorExtensionOfScope
{
    [Key]
    public int Id { get; set; }

    public int AssessorPersonId { get; set; }
    public Person? AssessorPerson { get; set; }

    [Required, MaxLength(50)]
    public string ApplicationNumber { get; set; } = string.Empty; // AM-EOS-2026-0001

    [Required, MaxLength(50)]
    public string PractitionerTypeCode { get; set; } = "Assessor"; // Assessor, Moderator

    [Required, MaxLength(250)]
    public string RequestedQualificationTitle { get; set; } = string.Empty;

    [MaxLength(50)]
    public string? SaqaQualificationId { get; set; }

    public int NqfLevel { get; set; }

    public bool RelevantIndustryCvVerified { get; set; } = false;
    public bool SubjectMatterCertificateVerified { get; set; } = false;

    [Required, MaxLength(50)]
    public string StatusCode { get; set; } = "Submitted"; // Submitted, VettingPassed, ScopeApproved, Rejected

    public string? EndorsedByUserId { get; set; }
    public DateTime? EndorsementDate { get; set; }

    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    public string CreatedBy { get; set; } = "SYSTEM";
    public DateTime? ModifiedAt { get; set; }
    public string? ModifiedBy { get; set; }
}
#endregion


