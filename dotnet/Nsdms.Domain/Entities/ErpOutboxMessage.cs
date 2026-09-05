using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Transactional Outbox message entity for Microsoft Dynamics GP and ERP Web Services integration.
/// Ensures resilient, decoupled asynchronous execution with automatic pause on GP outage and resumption upon recovery.
/// </summary>
public class ErpOutboxMessage : BaseEntity
{
    /// <summary>
    /// Unique correlation ID for tracking and idempotency across ERP boundaries.
    /// </summary>
    public string MessageCorrelationId { get; set; } = Guid.NewGuid().ToString("N");

    /// <summary>
    /// Type of GP operation: VendorSync, BankingDetailsUpdate, DgTrancheDisbursement, MgRebateDisbursement, PaymentDisbursement.
    /// </summary>
    public string MessageType { get; set; } = string.Empty;

    /// <summary>
    /// Business reference key (e.g. SDL number, MoA number, Voucher number, or statutory reference).
    /// </summary>
    public string ReferenceKey { get; set; } = string.Empty;

    /// <summary>
    /// Associated Organisation ID if applicable.
    /// </summary>
    public int? OrganisationId { get; set; }
    public Organisation? Organisation { get; set; }

    /// <summary>
    /// Serialized JSON payload containing parameters for the GP Web Service invocation.
    /// </summary>
    public string PayloadJson { get; set; } = "{}";

    /// <summary>
    /// Queue status: Pending, Processing, Delivered, FailedRetryable, DeadLetter, Suspended.
    /// </summary>
    public string QueueStatusCode { get; set; } = "Pending";

    /// <summary>
    /// Current count of retry attempts.
    /// </summary>
    public int RetryCount { get; set; } = 0;

    /// <summary>
    /// Maximum retry attempts before escalating to DeadLetter.
    /// </summary>
    public int MaxRetries { get; set; } = 5;

    /// <summary>
    /// UTC timestamp when the message is eligible for next delivery attempt.
    /// </summary>
    public DateTime? NextAttemptAtUtc { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// UTC timestamp of the last attempted invocation.
    /// </summary>
    public DateTime? LastAttemptAtUtc { get; set; }

    /// <summary>
    /// UTC timestamp when the GP transaction was confirmed delivered.
    /// </summary>
    public DateTime? DeliveredAtUtc { get; set; }

    /// <summary>
    /// Diagnostic error message or stack trace if the last attempt failed.
    /// </summary>
    public string? LastError { get; set; }

    /// <summary>
    /// Transaction reference returned by Dynamics GP (e.g. TRX-XXXXXX or voucher reference).
    /// </summary>
    public string? TransactionReference { get; set; }

    /// <summary>
    /// GP Payment Batch reference assigned to this transaction (e.g. GP-LIVE-YYYYMMDD-XXXX).
    /// </summary>
    public string? GpBatchNumber { get; set; }

    /// <summary>
    /// Concurrency lock token to ensure safe single-worker processing.
    /// </summary>
    public string? LockToken { get; set; }

    /// <summary>
    /// UTC expiration time for worker lock token.
    /// </summary>
    public DateTime? LockExpiresAtUtc { get; set; }

    /// <summary>
    /// Execution priority order: 1 = Pre-requisite (VendorSync, BankingDetails), 2 = Standard (Disbursements).
    /// </summary>
    public int ExecutionPriority { get; set; } = 2;
}
