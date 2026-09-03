using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// ERP Payment Batch Header for staging mandatory/discretionary grant disbursements to Dynamics GP / Sage.
/// </summary>
public class ErpPaymentBatchHeader : BaseEntity
{
    public string BatchNumber { get; set; } = string.Empty;
    public string BatchTypeCode { get; set; } = "DG_TRANCHE"; // DG_TRANCHE, MG_REBATE
    public decimal TotalAmount { get; set; } = 0m;
    public int ItemCount { get; set; } = 0;
    public string BatchStatusCode { get; set; } = "Draft"; // Draft, ExportedToErp, Posted, Paid, Cancelled

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public string StatusCode { get => BatchStatusCode; set => BatchStatusCode = value; }

    public string? ExportFileName { get; set; }
    public DateTime? ExportedDate { get; set; }
    public string? ExportedByUserId { get; set; }
    public string? ApprovalComments { get; set; }

    public ICollection<ErpPaymentBatchEntry> Entries { get; set; } = new List<ErpPaymentBatchEntry>();
}

/// <summary>
/// Individual line item voucher within an ERP payment batch.
/// </summary>
public class ErpPaymentBatchEntry : BaseEntity
{
    public int ErpPaymentBatchHeaderId { get; set; }
    public ErpPaymentBatchHeader? ErpPaymentBatchHeader { get; set; }

    public int? GrantPaymentClaimId { get; set; }
    public GrantPaymentClaim? GrantPaymentClaim { get; set; }

    public int? MandatoryGrantDisbursementId { get; set; }

    public int OrganisationId { get; set; }
    public Organisation? Organisation { get; set; }

    public string PaymentVoucherNumber { get; set; } = string.Empty;
    public string VendorNumber { get; set; } = string.Empty;
    public string BankAccountNumber { get; set; } = string.Empty;
    public string BankBranchCode { get; set; } = string.Empty;
    public decimal PaymentAmount { get; set; } = 0m;
    public string PaymentDescription { get; set; } = string.Empty;
    public string EntryStatusCode { get; set; } = "Pending"; // Pending, IncludedInBatch, EftProcessed, Rejected
    public string? EftReferenceNumber { get; set; }
}
