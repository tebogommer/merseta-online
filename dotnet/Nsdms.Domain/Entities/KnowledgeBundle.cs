using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Top-level Knowledge Bundle container representing a self-contained, hierarchical collection
/// of knowledge documents, synchronized with Git repositories per the OKF v0.2 specification.
/// </summary>
public class KnowledgeBundle : BaseEntity
{
    /// <summary>
    /// Unique alphanumeric identifier for the bundle (e.g., 'merseta-statutory-grants').
    /// </summary>
    public string BundleCode { get; set; } = string.Empty;

    /// <summary>
    /// Human-readable display title.
    /// </summary>
    public string DisplayName { get; set; } = string.Empty;

    /// <summary>
    /// Summary description of the bundle scope and contents.
    /// </summary>
    public string? Description { get; set; }

    /// <summary>
    /// Remote Git repository URL when synced via GitOps.
    /// </summary>
    public string? GitRepositoryUrl { get; set; }

    /// <summary>
    /// Local or relative filesystem path holding the Markdown file hierarchy.
    /// </summary>
    public string FileSystemPath { get; set; } = string.Empty;

    /// <summary>
    /// Target Open Knowledge Format specification version (defaults to '0.2').
    /// </summary>
    public string OkfVersion { get; set; } = "0.2";

    /// <summary>
    /// Indicates whether the knowledge bundle is currently active.
    /// </summary>
    public bool IsActive { get; set; } = true;

    // Navigation properties
    public ICollection<ConceptDocument> Concepts { get; set; } = new List<ConceptDocument>();
}
