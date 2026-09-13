using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// High-volume append-only audit trail of computation runs, returned receipts,
/// deterministic attestation verdicts, and cryptographic Digital Security Seals.
/// Inherits BaseLongEntity for BIGINT primary key scalability.
/// </summary>
public class ComputationExecutionAudit : BaseLongEntity
{
    public int ComputationId { get; set; }

    /// <summary>
    /// Actor string identifying who invoked computation (e.g. 'agent/gemini-2.5-pro', 'human:officer_01').
    /// </summary>
    public string InvokedByActor { get; set; } = string.Empty;

    /// <summary>
    /// JSON serialization of bound parameter dictionary.
    /// </summary>
    public string BoundParametersJson { get; set; } = "{}";

    /// <summary>
    /// SHA-256 cryptographic digest of the actual query string that executed in SQL Server.
    /// </summary>
    public string ExecutedSqlDigest { get; set; } = string.Empty;

    /// <summary>
    /// Complete JSON receipt payload returned by the execution runner.
    /// </summary>
    public string ReceiptPayloadJson { get; set; } = "{}";

    /// <summary>
    /// Attestation verdict ('Pass', 'Fail', 'Warning').
    /// </summary>
    public string AttestationVerdict { get; set; } = "Pass";

    /// <summary>
    /// Diagnostic explanation if attestation failed.
    /// </summary>
    public string? AttestationFailureReason { get; set; }

    /// <summary>
    /// 64-character SHA-256 Digital Security Seal generated upon successful attestation.
    /// </summary>
    public string VerificationReference { get; set; } = string.Empty;

    /// <summary>
    /// Query execution duration in milliseconds.
    /// </summary>
    public int ExecutionDurationMs { get; set; }

    /// <summary>
    /// UTC timestamp of execution.
    /// </summary>
    public DateTime ExecutedAt { get; set; } = DateTime.UtcNow;

    public AttestedComputation Computation { get; set; } = null!;
}
