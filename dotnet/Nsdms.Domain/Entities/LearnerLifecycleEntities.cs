using System.ComponentModel.DataAnnotations.Schema;
using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Tracks transfer of an active learner contract from one employer to another,
/// or from one accredited SDP to another, per signed specification Section 4.7.
/// </summary>
public class CompanyLearnerTransfer : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the active CompanyLearner record.
    /// </summary>
    public int CompanyLearnerId { get; set; }
    [NotMapped]
    public int EnrolmentId { get => CompanyLearnerId; set => CompanyLearnerId = value; }

    /// <summary>
    /// Navigational reference to the transferred learner agreement.
    /// </summary>
    public CompanyLearner? CompanyLearner { get; set; }

    /// <summary>
    /// Transfer scope classification: "EmployerToEmployer", "SdpToSdp".
    /// </summary>
    public string TransferScopeCode { get; set; } = "EmployerToEmployer";

    /// <summary>
    /// Optional foreign key referencing the releasing employer Organisation (null for SDP-to-SDP transfers).
    /// </summary>
    public int? FromOrganisationId { get; set; }

    /// <summary>
    /// Navigational reference to the releasing Organisation.
    /// </summary>
    public Organisation? FromOrganisation { get; set; }

    /// <summary>
    /// Optional foreign key referencing the receiving employer Organisation (null for SDP-to-SDP transfers).
    /// </summary>
    public int? ToOrganisationId { get; set; }

    /// <summary>
    /// Navigational reference to the receiving Organisation.
    /// </summary>
    public Organisation? ToOrganisation { get; set; }

    /// <summary>
    /// Optional foreign key referencing the releasing accredited Training Provider.
    /// </summary>
    public int? FromTrainingProviderId { get; set; }

    /// <summary>
    /// Navigational reference to the releasing Training Provider.
    /// </summary>
    public TrainingProvider? FromTrainingProvider { get; set; }

    /// <summary>
    /// Optional foreign key referencing the receiving accredited Training Provider.
    /// </summary>
    public int? ToTrainingProviderId { get; set; }

    /// <summary>
    /// Navigational reference to the receiving Training Provider.
    /// </summary>
    public TrainingProvider? ToTrainingProvider { get; set; }

    /// <summary>
    /// Foreign key referencing the verified Workplace Approval record for the target employer.
    /// </summary>
    public int? TargetWorkplaceApprovalId { get; set; }

    /// <summary>
    /// Navigational reference to the target workplace approval.
    /// </summary>
    public WorkplaceApproval? TargetWorkplaceApproval { get; set; }

    /// <summary>
    /// Who initiated the transfer: "CurrentEmployer", "FutureEmployer", "SDP", "Learner", "MerSetaRepresentative".
    /// </summary>
    public string InitiatedByTypeCode { get; set; } = "CurrentEmployer";

    /// <summary>
    /// Transfer rationale code per Section 4.7:
    /// ChangedLocation, TakenOnByHostEmployer, ChangedEmployer, ChangedProvider, Other.
    /// </summary>
    public string TransferReasonCode { get; set; } = "ChangedEmployer";

    /// <summary>
    /// Date when the transfer request was formally initiated.
    /// </summary>
    public DateTime TransferDate { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Effective operational start date at the new employer / provider.
    /// </summary>
    public DateTime EffectiveDate { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Current transfer workflow state code (Pending, ReviewCommitteeAgenda, Approved, Rejected, Cancelled).
    /// </summary>
    public string TransferStatusCode { get; set; } = "Pending";

    [NotMapped]
    public string StatusCode { get => TransferStatusCode; set => TransferStatusCode = value; }

    /// <summary>
    /// Consent tracking: Current releasing employer agreement status.
    /// </summary>
    public bool? IsCurrentEmployerAgreed { get; set; }

    public DateTime? CurrentEmployerSignoffDate { get; set; }

    public string? CurrentEmployerSignoffUserId { get; set; }

    /// <summary>
    /// Consent tracking: Future receiving employer agreement status.
    /// </summary>
    public bool? IsFutureEmployerAgreed { get; set; }

    public DateTime? FutureEmployerSignoffDate { get; set; }

    public string? FutureEmployerSignoffUserId { get; set; }

    /// <summary>
    /// Consent tracking: Learner agreement status.
    /// </summary>
    public bool? IsLearnerAgreed { get; set; }

    public DateTime? LearnerSignoffDate { get; set; }

    /// <summary>
    /// Indicates whether refusal of consent by the current employer prompted a formal termination process.
    /// </summary>
    public bool DisagreementPromptedTermination { get; set; } = false;

    /// <summary>
    /// Optional foreign key referencing the ETQA Review Committee agenda item for committee ratification.
    /// </summary>
    public int? ReviewCommitteeMeetingId { get; set; }

    /// <summary>
    /// Optional foreign key referencing the generated LPM-FM-005 Transfer Application Form document.
    /// </summary>
    public int? TransferFormDocumentId { get; set; }
    
    /// <summary>
    /// Reviewer or approval justification notes.
    /// </summary>
    public string? ApprovalComments { get; set; }

    /// <summary>
    /// User identifier of the approving MerSETA official.
    /// </summary>
    public string? ApprovedByUserId { get; set; }

    /// <summary>
    /// Timestamp when the transfer was formally approved.
    /// </summary>
    public DateTime? ApprovalDate { get; set; }
}

/// <summary>
/// Tracks suspended or lost training time and recalculates revised contract end dates.
/// </summary>
public class CompanyLearnerLostTime : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the affected CompanyLearner record.
    /// </summary>
    public int CompanyLearnerId { get; set; }
    [NotMapped]
    public int EnrolmentId { get => CompanyLearnerId; set => CompanyLearnerId = value; }

    /// <summary>
    /// Navigational reference to the affected learner agreement.
    /// </summary>
    public CompanyLearner? CompanyLearner { get; set; }

    /// <summary>
    /// Categorized reason code for training interruption (e.g. MaternityLeave, MedicalLeave, Suspension, WorkplaceShutdown).
    /// </summary>
    public string LostTimeReasonCode { get; set; } = "MedicalLeave";

    /// <summary>
    /// Date when the training interruption commenced.
    /// </summary>
    public DateTime StartDate { get; set; }

    /// <summary>
    /// Date when the training interruption ended.
    /// </summary>
    public DateTime EndDate { get; set; }

    /// <summary>
    /// Total cumulative days lost during this interruption period.
    /// </summary>
    public int DaysLost { get; set; }
    
    /// <summary>
    /// Original contractual completion date prior to lost time extension.
    /// </summary>
    public DateTime OriginalContractEndDate { get; set; }

    /// <summary>
    /// Revised contractual completion date extended by the lost days.
    /// </summary>
    public DateTime RevisedContractEndDate { get; set; }
    
    /// <summary>
    /// Current approval status code for the time extension request (Pending, Approved, Rejected).
    /// </summary>
    public string LostTimeStatusCode { get; set; } = "Pending";

    [NotMapped]
    public string StatusCode { get => LostTimeStatusCode; set => LostTimeStatusCode = value; }

    /// <summary>
    /// Verification and approval justification comments.
    /// </summary>
    public string? ApprovalComments { get; set; }

    /// <summary>
    /// User identifier of the approving MerSETA official.
    /// </summary>
    public string? ApprovedByUserId { get; set; }

    /// <summary>
    /// Timestamp when the lost time extension was approved.
    /// </summary>
    public DateTime? ApprovalDate { get; set; }
}

/// <summary>
/// Manages formal bilateral or unilateral cancellation of learner contracts per signed specification Sections 4.4, 4.5, 4.6.
/// </summary>
public class CompanyLearnerTermination : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the terminated CompanyLearner record.
    /// </summary>
    public int CompanyLearnerId { get; set; }
    [NotMapped]
    public int EnrolmentId { get => CompanyLearnerId; set => CompanyLearnerId = value; }

    /// <summary>
    /// Navigational reference to the terminated learner agreement.
    /// </summary>
    public CompanyLearner? CompanyLearner { get; set; }

    /// <summary>
    /// Termination classification category: "Mutual" (Section 4.6) or "OneSided" (Section 4.5).
    /// </summary>
    public string TerminationTypeCode { get; set; } = "Mutual";

    /// <summary>
    /// Statutory contract cancellation reason code:
    /// Mutual: ResignationAgreement, Retrenchment.
    /// One-Sided: ResignationUnilateral, Deceased, AwolDismissal, Other.
    /// </summary>
    public string TerminationReasonCode { get; set; } = "ResignationAgreement";

    /// <summary>
    /// Official date on which the learner agreement cancellation was requested / effective.
    /// </summary>
    public DateTime EffectiveDate { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Indicates whether a formal labour dispute or CCMA conciliation is lodged.
    /// </summary>
    public bool DisputeLogged { get; set; } = false;

    /// <summary>
    /// Name of the trade union representative or legal counsel involved.
    /// </summary>
    public string? UnionRepresentativeName { get; set; }

    /// <summary>
    /// Detailed settlement notes, mutual release terms, or disciplinary minutes.
    /// </summary>
    public string? SettlementNotes { get; set; }
    
    /// <summary>
    /// Current cancellation approval status code (Pending, InInvestigation, CommitteeAgenda, Approved, Rejected, RequirementsNotMet).
    /// </summary>
    public string TerminationStatusCode { get; set; } = "Pending";

    [NotMapped]
    public string StatusCode { get => TerminationStatusCode; set => TerminationStatusCode = value; }

    #region Statutory Investigation & Checklist 036 (One-Sided Terminations - Section 4.5)
    /// <summary>
    /// Indicates whether statutory Checklist (036) has been completed and verified.
    /// </summary>
    public bool Checklist036Completed { get; set; } = false;

    /// <summary>
    /// Structured JSON payload capturing Checklist 036 criteria responses.
    /// </summary>
    public string? Checklist036DataJson { get; set; }

    /// <summary>
    /// User identifier of the investigating officer who completed Checklist 036.
    /// </summary>
    public string? Checklist036CompletedByUserId { get; set; }

    /// <summary>
    /// Timestamp when Checklist 036 was completed.
    /// </summary>
    public DateTime? Checklist036CompletedDate { get; set; }

    /// <summary>
    /// User identifier of the investigating MerSETA official.
    /// </summary>
    public string? InvestigationConductedByUserId { get; set; }

    /// <summary>
    /// Date when the formal unilateral termination investigation commenced.
    /// </summary>
    public DateTime? InvestigationStartDate { get; set; }

    /// <summary>
    /// Mandatory SLA deadline for investigation completion (14 working days from initiation per Section 5).
    /// </summary>
    public DateTime? InvestigationDueDate { get; set; }

    /// <summary>
    /// Date when the investigation was formally completed and signed off.
    /// </summary>
    public DateTime? InvestigationCompletedDate { get; set; }

    /// <summary>
    /// Detailed investigation findings and summary report.
    /// </summary>
    public string? InvestigationOutcomeSummary { get; set; }

    /// <summary>
    /// Indicates whether an official recommendation was dispatched to the employer to write ARPL.
    /// </summary>
    public bool IsArplRecommended { get; set; } = false;

    /// <summary>
    /// Indicates whether an official recommendation was dispatched to the employer to transfer the learner.
    /// </summary>
    public bool IsTransferRecommended { get; set; } = false;
    #endregion

    #region ETQA Review Committee Adjudication (Sections 4.5 & 4.6)
    /// <summary>
    /// Optional foreign key referencing the ETQA Review Committee meeting agenda.
    /// </summary>
    public int? ReviewCommitteeMeetingId { get; set; }

    /// <summary>
    /// User identifier of the official recommending the pack to the ETQA Review Committee.
    /// </summary>
    public string? RecommendedToCommitteeByUserId { get; set; }

    /// <summary>
    /// Timestamp when the document pack was submitted to the ETQA Review Committee.
    /// </summary>
    public DateTime? RecommendationToCommitteeDate { get; set; }

    /// <summary>
    /// Official ETQA Review Committee decision code: "Approved", "RequirementsNotMet", "Deferred".
    /// </summary>
    public string? CommitteeDecisionCode { get; set; }

    /// <summary>
    /// Date when the ETQA Review Committee rendered its decision.
    /// </summary>
    public DateTime? CommitteeDecisionDate { get; set; }

    /// <summary>
    /// Detailed committee minutes or rationale for the decision.
    /// </summary>
    public string? CommitteeDecisionNotes { get; set; }

    /// <summary>
    /// Document identifier for the generated Confirmation of Termination Decision Letter.
    /// </summary>
    public int? DecisionLetterDocumentId { get; set; }

    /// <summary>
    /// Document identifier for the generated "Requirements Not Met" Rejection Letter.
    /// </summary>
    public int? RejectionLetterDocumentId { get; set; }
    #endregion

    /// <summary>
    /// Adjudication and approval decision notes.
    /// </summary>
    public string? ApprovalComments { get; set; }

    /// <summary>
    /// User identifier of the authorized MerSETA official approving the cancellation.
    /// </summary>
    public string? ApprovedByUserId { get; set; }

    /// <summary>
    /// Timestamp when the termination was formally approved.
    /// </summary>
    public DateTime? ApprovalDate { get; set; }
}

/// <summary>
/// Represents a statutory request for extension of an unregistered learner application
/// or an active learner contract per signed specification Section 4.1.
/// </summary>
public class CompanyLearnerExtension : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the CompanyLearner agreement record.
    /// </summary>
    public int CompanyLearnerId { get; set; }
    [NotMapped]
    public int EnrolmentId { get => CompanyLearnerId; set => CompanyLearnerId = value; }

    /// <summary>
    /// Navigational reference to the learner agreement.
    /// </summary>
    public CompanyLearner? CompanyLearner { get; set; }

    /// <summary>
    /// Extension category classification: "PreRegistration", "ContractAddendum".
    /// </summary>
    public string ExtensionTypeCode { get; set; } = "PreRegistration";

    [NotMapped]
    public DateTime RequestDate => CreatedAt;

    [NotMapped]
    public string ExtensionCategory { get => ExtensionTypeCode; set => ExtensionTypeCode = value; }

    /// <summary>
    /// Reason code for the extension request (e.g. PendingDocumentation, SdpAccreditationPending, WorkplaceReadiness, MedicalInterruption, Other).
    /// </summary>
    public string ExtensionReasonCode { get; set; } = "PendingDocumentation";

    /// <summary>
    /// Detailed justification text captured from the user per Section 4.1 Step 5.
    /// </summary>
    public string JustificationComments { get; set; } = string.Empty;

    /// <summary>
    /// Original submission deadline or contract completion date.
    /// </summary>
    public DateTime? OriginalExpiryDate { get; set; }

    /// <summary>
    /// Requested extended expiry date.
    /// </summary>
    public DateTime RequestedExpiryDate { get; set; } = DateTime.UtcNow.AddMonths(3);

    /// <summary>
    /// Approved extended expiry date after merSETA adjudication.
    /// </summary>
    public DateTime? ApprovedExpiryDate { get; set; }

    /// <summary>
    /// Optional foreign key referencing the uploaded Addendum of Agreement document.
    /// </summary>
    public int? AddendumDocumentId { get; set; }

    /// <summary>
    /// Current approval status (Pending, Approved, Rejected).
    /// </summary>
    public string ExtensionStatusCode { get; set; } = "Pending";

    [NotMapped]
    public string StatusCode { get => ExtensionStatusCode; set => ExtensionStatusCode = value; }

    [NotMapped]
    public int? AddendumAgreementDocumentId { get => AddendumDocumentId; set => AddendumDocumentId = value; }

    /// <summary>
    /// User identifier of the approving MerSETA official.
    /// </summary>
    public string? ApprovedByUserId { get; set; }

    /// <summary>
    /// Timestamp when the extension was formally approved.
    /// </summary>
    public DateTime? ApprovalDate { get; set; }

    /// <summary>
    /// Reviewer or approval justification notes.
    /// </summary>
    public string? ApprovalComments { get; set; }
}
