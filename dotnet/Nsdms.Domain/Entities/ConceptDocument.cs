using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Derived trust tiers per OKF v0.2 Section 5.3.
/// </summary>
public enum TrustTier : byte
{
    Unverified = 0,
    MachineConfirmed = 1,
    HumanReviewed = 2
}

/// <summary>
/// Lifecycle status values per OKF v0.2 Section 5.4.
/// </summary>
public enum ConceptLifecycleStatus
{
    Draft,
    Stable,
    Deprecated
}

/// <summary>
/// Represents a single unit of knowledge within a bundle, mapped from an OKF Markdown file
/// with YAML frontmatter. Backed by SQL Server system-versioned temporal tables for historical version timelines.
/// </summary>
public class ConceptDocument : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the parent knowledge bundle.
    /// </summary>
    public int BundleId { get; set; }

    /// <summary>
    /// Relative path within the bundle with '.md' suffix removed (e.g. 'grants/mandatory-grant-rebate').
    /// </summary>
    public string ConceptId { get; set; } = string.Empty;

    /// <summary>
    /// REQUIRED per OKF §4.1: Short string identifying the kind of concept (e.g. 'Metric', 'BigQuery Table', 'Attested Computation').
    /// </summary>
    public string ConceptType { get; set; } = string.Empty;

    /// <summary>
    /// Human-readable display title.
    /// </summary>
    public string Title { get; set; } = string.Empty;

    /// <summary>
    /// Single sentence summarizing the concept.
    /// </summary>
    public string? SummaryDescription { get; set; }

    /// <summary>
    /// Canonical URI uniquely identifying the underlying physical or statutory asset.
    /// </summary>
    public string? ResourceUri { get; set; }

    /// <summary>
    /// Free-form Markdown body following the YAML frontmatter.
    /// </summary>
    public string BodyMarkdown { get; set; } = string.Empty;

    /// <summary>
    /// Lifecycle status per OKF §5.4 (Draft, Stable, Deprecated).
    /// </summary>
    public ConceptLifecycleStatus LifecycleStatus { get; set; } = ConceptLifecycleStatus.Stable;

    /// <summary>
    /// Absolute UTC instant when content becomes stale per OKF §5.5.
    /// </summary>
    public DateTime? StaleAfter { get; set; }

    /// <summary>
    /// Derived trust tier (0 = Unverified, 1 = Machine-Confirmed, 2 = Human-Reviewed) per OKF §5.3.
    /// </summary>
    public TrustTier DerivedTrustTier { get; set; } = TrustTier.Unverified;

    /// <summary>
    /// UTC timestamp when current content was generated.
    /// </summary>
    public DateTime GeneratedAt { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Actor string identifying who or what produced content (e.g., 'agent/gemini-2.5-pro', 'human:clo_042').
    /// </summary>
    public string GeneratedByActor { get; set; } = "SYSTEM";

    /// <summary>
    /// Indicates whether the record is active.
    /// </summary>
    public bool IsActive { get; set; } = true;

    // Navigation properties
    public KnowledgeBundle Bundle { get; set; } = null!;
    public ICollection<ConceptTag> Tags { get; set; } = new List<ConceptTag>();
    public ICollection<ConceptSource> Sources { get; set; } = new List<ConceptSource>();
    public ICollection<ConceptVerificationEvent> Verifications { get; set; } = new List<ConceptVerificationEvent>();
    public AttestedComputation? Computation { get; set; }
    public ICollection<ConceptCrossLink> OutgoingCrossLinks { get; set; } = new List<ConceptCrossLink>();
    public ICollection<ConceptCrossLink> IncomingCrossLinks { get; set; } = new List<ConceptCrossLink>();
}
