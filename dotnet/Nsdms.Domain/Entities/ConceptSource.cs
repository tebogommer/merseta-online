using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Records an authoritative source or external material a concept derives from,
/// along with objective credibility signals per OKF v0.2 Section 5.1.
/// </summary>
public class ConceptSource : BaseEntity
{
    public int ConceptId { get; set; }

    /// <summary>
    /// Stable key used to attribute claims in Markdown footnotes: [^source_id_alias].
    /// </summary>
    public string SourceIdAlias { get; set; } = string.Empty;

    /// <summary>
    /// Concrete artifact URI or population/scope descriptor (e.g. 'https://www.gov.za/...').
    /// </summary>
    public string ResourceUri { get; set; } = string.Empty;

    /// <summary>
    /// Human-readable label for the source.
    /// </summary>
    public string? Title { get; set; }

    /// <summary>
    /// Who or what produced the source in actor convention (e.g., 'government:dhet', 'team:finance').
    /// </summary>
    public string? AuthorActor { get; set; }

    /// <summary>
    /// How often the resource was exercised over the usage window. Coarse adoption/liveness signal.
    /// </summary>
    public long? UsageCount { get; set; }

    /// <summary>
    /// When the source itself was last modified (recency signal).
    /// </summary>
    public DateTime? LastModifiedAt { get; set; }

    /// <summary>
    /// Beginning of the usage window period framing usage count.
    /// </summary>
    public DateTime? UsageWindowStart { get; set; }

    /// <summary>
    /// End of the usage window period framing usage count.
    /// </summary>
    public DateTime? UsageWindowEnd { get; set; }

    public ConceptDocument Concept { get; set; } = null!;
}
