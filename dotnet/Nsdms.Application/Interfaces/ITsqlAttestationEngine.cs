using Nsdms.Application.DTOs;

namespace Nsdms.Application.Interfaces;

/// <summary>
/// Execution runner and deterministic attester for OKF Attested Computations (Runtime: T-SQL).
/// Enforces:
/// 1. Staleness checking before execution (BR-OKF-002).
/// 2. Strict parameter binding (preventing arbitrary queries or agent hallucinations) (BR-OKF-003).
/// 3. Deterministic attestation: compares executed query digest against re-expanded template (BR-OKF-004).
/// 4. Cryptographic Digital Security Seal stamping and execution auditing.
/// </summary>
public interface ITsqlAttestationEngine
{
    Task<AttestationExecutionResultDto> ExecuteAndAttestAsync(
        int computationId, 
        Dictionary<string, object?> parameterValues, 
        string actor, 
        CancellationToken ct = default);

    Task<AttestationExecutionResultDto> DryRunAttestationAsync(
        int computationId, 
        Dictionary<string, object?> parameterValues, 
        CancellationToken ct = default);
}
