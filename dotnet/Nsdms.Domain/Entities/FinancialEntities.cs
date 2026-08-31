using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Memorandum of Agreement (MOA) for approved Discretionary Grants.
/// </summary>
public class GrantMoa : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the originating Discretionary Grant application.
    /// </summary>
    public int GrantApplicationId { get; set; }

    /// <summary>
    /// Navigational reference to the approved GrantApplication.
    /// </summary>
    public GrantApplication? GrantApplication { get; set; }

    /// <summary>
    /// Convenient alias for direct organization reference.
    /// </summary>
    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public Organisation? Organisation => GrantApplication?.Organisation;

    /// <summary>
    /// Unique MerSETA MOA legal contract reference number (e.g. MOA-2026-DG-001).
    /// </summary>
    public string MoaNumber { get; set; } = string.Empty;

    /// <summary>
    /// Contractual project commencement date.
    /// </summary>
    public DateTime ContractStartDate { get; set; }

    /// <summary>
    /// Contractual project completion and closeout deadline.
    /// </summary>
    public DateTime ContractEndDate { get; set; }

    /// <summary>
    /// Total committed monetary contract allocation in ZAR.
    /// </summary>
    public decimal TotalContractValue { get; set; }

    /// <summary>
    /// Date when the employer authorized signatory signed the contract.
    /// </summary>
    public DateTime? SignoffDateEmployer { get; set; }

    /// <summary>
    /// Date when the MerSETA CEO / delegated authority executed the agreement.
    /// </summary>
    public DateTime? SignoffDateSeta { get; set; }

    /// <summary>
    /// Digital storage URI of the executed bilateral MOA legal document.
    /// </summary>
    public string? SignoffDocumentUri { get; set; }

    /// <summary>
    /// Lifecycle contract state code (e.g. Draft, Pending Signature, Active, Terminated, Completed).
    /// </summary>
    public string MoaStatusCode { get; set; } = "Draft";

    /// <summary>
    /// Special conditions, covenants, or bespoke performance clauses attached to this agreement.
    /// </summary>
    public string? SpecialConditions { get; set; }

    /// <summary>
    /// Foreign key referencing the active MoaTemplate version applied to this agreement.
    /// </summary>
    public int? MoaTemplateId { get; set; }

    /// <summary>
    /// Navigational reference to the MoaTemplate.
    /// </summary>
    public MoaTemplate? MoaTemplate { get; set; }

    /// <summary>
    /// Phased deliverable milestones and tranche schedules.
    /// </summary>
    public ICollection<GrantMoaMilestone> Milestones { get; set; } = new List<GrantMoaMilestone>();

    /// <summary>
    /// Collection of cryptographically frozen execution snapshots for this MOA.
    /// </summary>
    public ICollection<MoaExecutionSnapshot> ExecutionSnapshots { get; set; } = new List<MoaExecutionSnapshot>();

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public string StatusCode { get => MoaStatusCode; set => MoaStatusCode = value; }

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public string ProjectTitle => GrantApplication?.ProjectTitle ?? MoaNumber;

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public decimal ContractValue => TotalContractValue;

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public DateTime StartDate => ContractStartDate;

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public DateTime EndDate => ContractEndDate;
}

/// <summary>
/// Delivery milestones linked to MOA tranches.
/// </summary>
public class GrantMoaMilestone : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the parent GrantMoa contract.
    /// </summary>
    public int GrantMoaId { get; set; }

    /// <summary>
    /// Navigational reference to the parent GrantMoa.
    /// </summary>
    public GrantMoa? GrantMoa { get; set; }

    /// <summary>
    /// Sequential milestone sequence index (e.g. 1, 2, 3, 4).
    /// </summary>
    public int MilestoneNumber { get; set; }

    /// <summary>
    /// Descriptive milestone title (e.g. Inception &amp; Learner Contracting, Midterm Progress, Final Assessment &amp; Closeout).
    /// </summary>
    public string MilestoneTitle { get; set; } = string.Empty;

    /// <summary>
    /// Detailed description of delivery expectations.
    /// </summary>
    public string? MilestoneDescription { get; set; }

    /// <summary>
    /// Mandatory physical or digital evidence deliverables required for tranche release.
    /// </summary>
    public string? DeliverableRequirement { get; set; }

    /// <summary>
    /// Deliverable payment allocation percentage (e.g. 30%, 40%, 30%).
    /// </summary>
    public decimal TranchePercentage { get; set; }

    /// <summary>
    /// Calculated tranche disbursement payout amount in ZAR.
    /// </summary>
    public decimal TrancheAmount { get; set; }

    /// <summary>
    /// Target due date for milestone deliverable submission.
    /// </summary>
    public DateTime TargetDueDate { get; set; }

    /// <summary>
    /// Milestone verification and disbursement state code (e.g. Pending, Submitted, Verified, Approved, Paid).
    /// </summary>
    public string MilestoneStatusCode { get; set; } = "Pending";

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public string MilestoneName { get => MilestoneTitle; set => MilestoneTitle = value; }

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public decimal PaymentPercentage { get => TranchePercentage; set => TranchePercentage = value; }

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public decimal PaymentAmount { get => TrancheAmount; set => TrancheAmount = value; }

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public DateTime TargetCompletionDate { get => TargetDueDate; set => TargetDueDate = value; }

    /// <summary>
    /// Date when the MerSETA monitoring officer verified milestone evidence.
    /// </summary>
    public DateTime? VerificationDate { get; set; }

    /// <summary>
    /// User identifier of the reviewing MerSETA verifier.
    /// </summary>
    public string? VerifiedByUserId { get; set; }

    /// <summary>
    /// Verifier audit assessment notes and inspection remarks.
    /// </summary>
    public string? VerificationComments { get; set; }

    /// <summary>
    /// Invoices and payment requisitions generated against this milestone.
    /// </summary>
    public ICollection<GrantTranchePayment> Payments { get; set; } = new List<GrantTranchePayment>();
}

/// <summary>
/// Tranche Invoices and Payment Requisitions.
/// </summary>
public class GrantTranchePayment : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the parent GrantMoaMilestone.
    /// </summary>
    public int GrantMoaMilestoneId { get; set; }

    /// <summary>
    /// Navigational reference to the parent GrantMoaMilestone.
    /// </summary>
    public GrantMoaMilestone? GrantMoaMilestone { get; set; }

    /// <summary>
    /// Foreign key referencing the originating GrantApplication.
    /// </summary>
    public int GrantApplicationId { get; set; }

    /// <summary>
    /// Navigational reference to the GrantApplication.
    /// </summary>
    public GrantApplication? GrantApplication { get; set; }

    /// <summary>
    /// Internal financial voucher reference tracking number.
    /// </summary>
    public string PaymentReferenceNumber { get; set; } = string.Empty;

    /// <summary>
    /// Employer or provider Tax Invoice reference number.
    /// </summary>
    public string InvoiceNumber { get; set; } = string.Empty;

    /// <summary>
    /// Official date on the submitted tax invoice.
    /// </summary>
    public DateTime InvoiceDate { get; set; }

    /// <summary>
    /// Gross claimed invoice monetary value in ZAR.
    /// </summary>
    public decimal ClaimedAmount { get; set; }

    /// <summary>
    /// Net verified amount approved for EFT release by MerSETA finance in ZAR.
    /// </summary>
    public decimal ApprovedPaymentAmount { get; set; }

    /// <summary>
    /// Payout workflow state code (e.g. Draft, Submitted, Finance Approved, Batch Scheduled, Paid, Rejected).
    /// </summary>
    public string PaymentStatusCode { get; set; } = "Draft";

    /// <summary>
    /// Date when the bank EFT transaction cleared.
    /// </summary>
    public DateTime? PaymentDate { get; set; }

    /// <summary>
    /// Bank disbursement batch grouping reference identifier.
    /// </summary>
    public string? BatchNumber { get; set; }

    /// <summary>
    /// Electronic bank payment reference appearing on statements.
    /// </summary>
    public string? BankReference { get; set; }

    /// <summary>
    /// User identifier of the finance manager who authorized payment.
    /// </summary>
    public string? FinanceApproverUserId { get; set; }

    /// <summary>
    /// Date and time of finance dual-authorization signoff.
    /// </summary>
    public DateTime? FinanceApprovalDate { get; set; }

    /// <summary>
    /// Financial audit notes, ledger allocations, or rejection justifications.
    /// </summary>
    public string? ApprovalComments { get; set; }
}

/// <summary>
/// Mandatory Grant 20% Rebate Payouts for compliant employers submitting WSP/ATR.
/// </summary>
public class MandatoryGrantDisbursement : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the compliant WspSubmission.
    /// </summary>
    public int WspSubmissionId { get; set; }

    /// <summary>
    /// Navigational reference to the approved WspSubmission.
    /// </summary>
    public WspSubmission? WspSubmission { get; set; }

    /// <summary>
    /// Foreign key referencing the receiving Employer Organisation.
    /// </summary>
    public int OrganisationId { get; set; }

    /// <summary>
    /// Navigational reference to the Organisation.
    /// </summary>
    public Organisation? Organisation { get; set; }

    /// <summary>
    /// Unique mandatory grant disbursement voucher reference number.
    /// </summary>
    public string DisbursementReference { get; set; } = string.Empty;

    /// <summary>
    /// Financial scheme year for the rebate calculation.
    /// </summary>
    public int FinYear { get; set; }

    /// <summary>
    /// Scheme month or quarterly levy distribution period (e.g. 2026-Q1).
    /// </summary>
    public string? LevyPeriod { get; set; }

    /// <summary>
    /// Total SARS levy received for this employer during the period in ZAR.
    /// </summary>
    public decimal LeviesReceivedAmount { get; set; }

    /// <summary>
    /// Statutory 20% Mandatory Grant rebate amount calculated in ZAR.
    /// </summary>
    public decimal CalculatedRebateAmount { get; set; }

    /// <summary>
    /// Payout processing state code (e.g. Calculated, Approved, Paid, On Hold).
    /// </summary>
    public string DisbursementStatusCode { get; set; } = "Calculated";

    /// <summary>
    /// Date when the rebate EFT payment cleared.
    /// </summary>
    public DateTime? PaymentDate { get; set; }

    /// <summary>
    /// Finance EFT disbursement batch identifier.
    /// </summary>
    public string? BatchNumber { get; set; }

    /// <summary>
    /// JSON snapshot of verified employer banking details at time of disbursement.
    /// </summary>
    public string? BankAccountSnapshot { get; set; }

    /// <summary>
    /// Governance remarks and reconciliation ledger comments.
    /// </summary>
    public string? Comments { get; set; }
}

/// <summary>
/// Inter-SETA Transfer of employer registration and levy funds between SETAs.
/// </summary>
public class InterSetaTransfer : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the migrating Employer Organisation.
    /// </summary>
    public int OrganisationId { get; set; }

    /// <summary>
    /// Navigational reference to the migrating Organisation.
    /// </summary>
    public Organisation? Organisation { get; set; }

    /// <summary>
    /// Direction of migration (Incoming to MerSETA or Outgoing to another SETA).
    /// </summary>
    public string TransferType { get; set; } = "Outgoing";

    /// <summary>
    /// Acronym/Code of counterpart SETA (e.g. CHIETA, EWSETA, TETA, SERVICES, W&amp;RSETA).
    /// </summary>
    public string OtherSetaCode { get; set; } = string.Empty;

    /// <summary>
    /// Full statutory title of counterpart SETA.
    /// </summary>
    public string OtherSetaName { get; set; } = string.Empty;

    /// <summary>
    /// Operational or legal justification for employer migration.
    /// </summary>
    public string TransferReason { get; set; } = string.Empty;

    /// <summary>
    /// Effective gazetted date of transfer.
    /// </summary>
    public DateTime EffectiveDate { get; set; }

    /// <summary>
    /// Transfer lifecycle status code (e.g. Initiated, Documents Verified, Approved by CEO, Transferred, Rejected).
    /// </summary>
    public string TransferStatusCode { get; set; } = "Initiated";

    /// <summary>
    /// Uncommitted accumulated levy funds transferred between SETAs in ZAR.
    /// </summary>
    public decimal TransferAmount { get; set; }

    /// <summary>
    /// Official SETA executive signoff reference.
    /// </summary>
    public string? SetaApprovalReference { get; set; }

    /// <summary>
    /// Department of Higher Education &amp; Training (DHET) gazetted approval number.
    /// </summary>
    public string? DhetReferenceNumber { get; set; }

    /// <summary>
    /// Legal comments and audit trail notes.
    /// </summary>
    public string? Comments { get; set; }
}
