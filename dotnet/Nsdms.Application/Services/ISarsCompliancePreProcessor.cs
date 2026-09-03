using System.IO;
using System.Threading;
using System.Threading.Tasks;

namespace Nsdms.Application.Services;

/// <summary>
/// Pre-ingestion compliance gatekeeper for SARS monthly Skills Development Levy files.
/// Validates files against structural, syntax, regulatory, and cryptographic requirements
/// prior to database staging or ledger commitment (Option A: Streaming In-Line Pre-Flight Gatekeeper).
/// </summary>
public interface ISarsCompliancePreProcessor
{
    /// <summary>
    /// Evaluates a raw file stream against SARS statutory and structural compliance rules.
    /// Computes the Digital Security Seal, validates SDL regex, verifies trailer control totals,
    /// and checks for duplicate batches. Guarantees ZERO database modifications.
    /// </summary>
    Task<SarsComplianceReport> ValidateStreamAsync(
        Stream stream,
        string fileName,
        CancellationToken cancellationToken = default);

    /// <summary>
    /// Evaluates raw file content string against compliance rules.
    /// </summary>
    Task<SarsComplianceReport> ValidateContentAsync(
        string content,
        string fileName,
        CancellationToken cancellationToken = default);
}
