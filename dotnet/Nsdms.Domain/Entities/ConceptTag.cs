using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Cross-cutting categorization tag associated with a concept document.
/// </summary>
public class ConceptTag : BaseEntity
{
    public int ConceptId { get; set; }
    public string TagName { get; set; } = string.Empty;

    public ConceptDocument Concept { get; set; } = null!;
}
