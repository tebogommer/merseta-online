using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Tracks transfer of an active learner contract from one employer to another.
/// </summary>
public class CompanyLearnerTransfer : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the active CompanyLearner record.
    /// </summary>
    public int CompanyLearnerId { get; set; }

    /// <summary>
    /// Navigational reference to the transferred learner agreement.
    /// </summary>
    public CompanyLearner? CompanyLearner { get; set; }

    /// <summary>
    /// Foreign key referencing the releasing employer Organisation.
    /// </summary>
    public int FromOrganisationId { get; set; }

    /// <summary>
    /// Navigational reference to the releasing Organisation.
    /// </summary>
    public Organisation? FromOrganisation { get; set; }

    /// <summary>
    /// Foreign key referencing the receiving employer Organisation.
    /// </summary>
    public int ToOrganisationId { get; set; }

    /// <summary>
    /// Navigational reference to the receiving Organisation.
    /// </summary>
    public Organisation? ToOrganisation { get; set; }

    /// <summary>
    /// Transfer rationale code (e.g. CompanyDownsized, MutualAgreement, Relocation, DisputeResolution).
    /// </summary>
    public string TransferReasonCode { get; set; } = "MutualAgreement";

    /// <summary>
    /// Date when the transfer request was formally initiated.
    /// </summary>
    public DateTime TransferDate { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Effective operational start date at the new employer.
    /// </summary>
    public DateTime EffectiveDate { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Current transfer workflow state code (Pending, Approved, Rejected, Completed).
    /// </summary>
    public string TransferStatusCode { get; set; } = "Pending";

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public string StatusCode { get => TransferStatusCode; set => TransferStatusCode = value; }
    
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

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
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
/// Manages formal bilateral or unilateral cancellation of learner contracts.
/// </summary>
public class CompanyLearnerTermination : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the terminated CompanyLearner record.
    /// </summary>
    public int CompanyLearnerId { get; set; }

    /// <summary>
    /// Navigational reference to the terminated learner agreement.
    /// </summary>
    public CompanyLearner? CompanyLearner { get; set; }

    /// <summary>
    /// Statutory contract cancellation reason code (e.g. Deceased, Absconded, DismissedForMisconduct, MutualCancellation, MedicalIncapacity).
    /// </summary>
    public string TerminationReasonCode { get; set; } = "MutualCancellation";

    /// <summary>
    /// Official date on which the learner agreement was terminated.
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
    /// Current cancellation approval status code (Pending, Approved, Rejected).
    /// </summary>
    public string TerminationStatusCode { get; set; } = "Pending";

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public string StatusCode { get => TerminationStatusCode; set => TerminationStatusCode = value; }

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
