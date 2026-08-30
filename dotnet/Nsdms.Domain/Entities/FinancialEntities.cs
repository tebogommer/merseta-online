using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Memorandum of Agreement (MOA) for approved Discretionary Grants.
/// </summary>
public class GrantMoa : BaseEntity
{
    public int GrantApplicationId { get; set; }
    public GrantApplication? GrantApplication { get; set; }

    public string MoaNumber { get; set; } = string.Empty;
    public DateTime ContractStartDate { get; set; }
    public DateTime ContractEndDate { get; set; }
    public decimal TotalContractValue { get; set; }
    public DateTime? SignoffDateEmployer { get; set; }
    public DateTime? SignoffDateSeta { get; set; }
    public string? SignoffDocumentUri { get; set; }
    public string MoaStatusCode { get; set; } = "Draft"; // Draft, Pending Signature, Active, Terminated, Completed
    public string? SpecialConditions { get; set; }

    public ICollection<GrantMoaMilestone> Milestones { get; set; } = new List<GrantMoaMilestone>();
}

/// <summary>
/// Delivery milestones linked to MOA tranches.
/// </summary>
public class GrantMoaMilestone : BaseEntity
{
    public int GrantMoaId { get; set; }
    public GrantMoa? GrantMoa { get; set; }

    public int MilestoneNumber { get; set; }
    public string MilestoneTitle { get; set; } = string.Empty;
    public string? MilestoneDescription { get; set; }
    public string? DeliverableRequirement { get; set; }
    public decimal TranchePercentage { get; set; }
    public decimal TrancheAmount { get; set; }
    public DateTime TargetDueDate { get; set; }
    public string MilestoneStatusCode { get; set; } = "Pending"; // Pending, Submitted, Verified, Approved, Paid
    public DateTime? VerificationDate { get; set; }
    public string? VerifiedByUserId { get; set; }
    public string? VerificationComments { get; set; }

    public ICollection<GrantTranchePayment> Payments { get; set; } = new List<GrantTranchePayment>();
}

/// <summary>
/// Tranche Invoices and Payment Requisitions.
/// </summary>
public class GrantTranchePayment : BaseEntity
{
    public int GrantMoaMilestoneId { get; set; }
    public GrantMoaMilestone? GrantMoaMilestone { get; set; }

    public int GrantApplicationId { get; set; }
    public GrantApplication? GrantApplication { get; set; }

    public string PaymentReferenceNumber { get; set; } = string.Empty;
    public string InvoiceNumber { get; set; } = string.Empty;
    public DateTime InvoiceDate { get; set; }
    public decimal ClaimedAmount { get; set; }
    public decimal ApprovedPaymentAmount { get; set; }
    public string PaymentStatusCode { get; set; } = "Draft"; // Draft, Submitted, Finance Approved, Batch Scheduled, Paid, Rejected
    public DateTime? PaymentDate { get; set; }
    public string? BatchNumber { get; set; }
    public string? BankReference { get; set; }
    public string? FinanceApproverUserId { get; set; }
    public DateTime? FinanceApprovalDate { get; set; }
    public string? ApprovalComments { get; set; }
}

/// <summary>
/// Mandatory Grant 20% Rebate Payouts for compliant employers submitting WSP/ATR.
/// </summary>
public class MandatoryGrantDisbursement : BaseEntity
{
    public int WspSubmissionId { get; set; }
    public WspSubmission? WspSubmission { get; set; }

    public int OrganisationId { get; set; }
    public Organisation? Organisation { get; set; }

    public string DisbursementReference { get; set; } = string.Empty;
    public int FinYear { get; set; }
    public string? LevyPeriod { get; set; }
    public decimal LeviesReceivedAmount { get; set; }
    public decimal CalculatedRebateAmount { get; set; } // 20% of Levies Received
    public string DisbursementStatusCode { get; set; } = "Calculated"; // Calculated, Approved, Paid, On Hold
    public DateTime? PaymentDate { get; set; }
    public string? BatchNumber { get; set; }
    public string? BankAccountSnapshot { get; set; }
    public string? Comments { get; set; }
}

/// <summary>
/// Inter-SETA Transfer of employer registration and levy funds between SETAs.
/// </summary>
public class InterSetaTransfer : BaseEntity
{
    public int OrganisationId { get; set; }
    public Organisation? Organisation { get; set; }

    public string TransferType { get; set; } = "Outgoing"; // Incoming, Outgoing
    public string OtherSetaCode { get; set; } = string.Empty; // e.g. CHIETA, EWSETA, TETA, SERVICES, W&RSETA
    public string OtherSetaName { get; set; } = string.Empty;
    public string TransferReason { get; set; } = string.Empty;
    public DateTime EffectiveDate { get; set; }
    public string TransferStatusCode { get; set; } = "Initiated"; // Initiated, Documents Verified, Approved by CEO, Transferred, Rejected
    public decimal TransferAmount { get; set; }
    public string? SetaApprovalReference { get; set; }
    public string? DhetReferenceNumber { get; set; }
    public string? Comments { get; set; }
}
