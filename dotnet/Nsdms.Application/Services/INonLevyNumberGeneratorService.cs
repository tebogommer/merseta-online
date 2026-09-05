namespace Nsdms.Application.Services;

/// <summary>
/// Service contract for generating unique, sequential, and statutory-compliant N-Numbers
/// for non-levy paying entities (TVET colleges, universities, NGOs, CBOs, trade unions, exempt SMEs).
/// Implements a 4-tier zero-collision architecture:
/// 1. Dynamic high-water mark sequence seeding.
/// 2. Atomic SQL Server sequence draw.
/// 3. Self-healing collision probe loop.
/// 4. Physical unique index barrier.
/// </summary>
public interface INonLevyNumberGeneratorService
{
    /// <summary>
    /// Generates the next guaranteed-unique 10-character statutory N-Number (e.g. N000100001)
    /// conforming to DHET SETMIS File 200 and SAQA NLRD Edu.Dex specifications.
    /// </summary>
    Task<string> GenerateNextNonLevyNumberAsync(CancellationToken cancellationToken = default);

    /// <summary>
    /// Checks whether an SDL or N-number format represents a non-levy paying organisation.
    /// </summary>
    bool IsNonLevyNumber(string? sdlNumber);

    /// <summary>
    /// Validates whether a candidate N-number matches statutory format ^N\d{9}$.
    /// </summary>
    bool IsValidNNumberFormat(string? nNumber);
}
