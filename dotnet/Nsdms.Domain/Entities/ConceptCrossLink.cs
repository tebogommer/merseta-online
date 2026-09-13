using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Directed graph edge between concepts parsed from internal Markdown links (/path/to/concept.md).
/// Allows visualization of concept dependency graphs and lineage networks.
/// </summary>
public class ConceptCrossLink : BaseEntity
{
    public int SourceConceptId { get; set; }

    /// <summary>
    /// Declared target path in the Markdown link (e.g. '/grants/mandatory-grant-rebate.md').
    /// </summary>
    public string TargetConceptPath { get; set; } = string.Empty;

    /// <summary>
    /// Resolved target concept ID in the database, or null if target does not yet exist.
    /// Consumers must tolerate broken links per OKF §6.1.
    /// </summary>
    public int? ResolvedTargetId { get; set; }

    /// <summary>
    /// Anchor link text in the markdown.
    /// </summary>
    public string? LinkText { get; set; }

    public ConceptDocument SourceConcept { get; set; } = null!;
    public ConceptDocument? ResolvedTarget { get; set; }
}
