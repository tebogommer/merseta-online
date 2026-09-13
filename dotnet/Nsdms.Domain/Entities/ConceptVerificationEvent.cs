using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Records a formal verification event against a concept document.
/// Multiple events capture independent sign-offs (e.g. human approval plus nightly reconciliation process).
/// </summary>
public class ConceptVerificationEvent : BaseEntity
{
    public int ConceptId { get; set; }

    /// <summary>
    /// Actor string identifying the verifying party (e.g. 'human:clo_042', 'process:nightly-recon').
    /// </summary>
    public string VerifiedByActor { get; set; } = string.Empty;

    /// <summary>
    /// Classification of the verifying actor ('human', 'process', 'agent').
    /// </summary>
    public string ActorType { get; set; } = "human";

    /// <summary>
    /// UTC timestamp when verification occurred.
    /// </summary>
    public DateTime VerifiedAt { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Governance notes, resolution numbers, or verification remarks.
    /// </summary>
    public string? Notes { get; set; }

    public ConceptDocument Concept { get; set; } = null!;
}
