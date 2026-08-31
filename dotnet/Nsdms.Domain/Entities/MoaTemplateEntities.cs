using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Master legal template defining an MoA contract structure for a specific financial year and grant policy.
/// </summary>
public class MoaTemplate : BaseEntity
{
    /// <summary>
    /// Unique administrative template code identifier (e.g. DG-STD-2026, SP-TVET-2026).
    /// </summary>
    public string TemplateCode { get; set; } = string.Empty;

    /// <summary>
    /// Descriptive name of the MoA template.
    /// </summary>
    public string TemplateTitle { get; set; } = string.Empty;

    /// <summary>
    /// Financial year the template is effective for (e.g. 2026).
    /// </summary>
    public int FinancialYear { get; set; }

    /// <summary>
    /// Statutory Grant Type code (e.g. DiscretionaryGrant, SpecialProject, Bursary, Candidacy).
    /// </summary>
    public string GrantTypeCode { get; set; } = "DiscretionaryGrant";

    /// <summary>
    /// Target legal entity type filter (e.g. Employer, TVET, University, NGO, All).
    /// </summary>
    public string LegalEntityType { get; set; } = "All";

    /// <summary>
    /// Semantic version of the template policy (e.g. 1.0.0, 1.2.0, 2.0.0).
    /// </summary>
    public string VersionNumber { get; set; } = "1.0.0";

    /// <summary>
    /// Governance approval lifecycle status (Draft, UnderReview, Approved, Archived).
    /// </summary>
    public string ApprovalStatus { get; set; } = "Draft";

    /// <summary>
    /// Effective starting date for this template version.
    /// </summary>
    public DateTime EffectiveFrom { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Optional expiration / sunset date for this template version.
    /// </summary>
    public DateTime? EffectiveTo { get; set; }

    /// <summary>
    /// Whether this template is active for new contract issuances.
    /// </summary>
    public bool IsActive { get; set; } = true;

    /// <summary>
    /// Username of the governance/legal officer who approved this template.
    /// </summary>
    public string? ApprovedBy { get; set; }

    /// <summary>
    /// Timestamp when governance approval was executed.
    /// </summary>
    public DateTime? ApprovedAt { get; set; }

    /// <summary>
    /// Ordered collection of clause sections that make up this agreement template.
    /// </summary>
    public ICollection<MoaTemplateSection> Sections { get; set; } = new List<MoaTemplateSection>();

    /// <summary>
    /// Historical execution snapshots produced from this template.
    /// </summary>
    public ICollection<MoaExecutionSnapshot> ExecutionSnapshots { get; set; } = new List<MoaExecutionSnapshot>();

    /// <summary>
    /// Issued Grant MoA contracts linked to this template version.
    /// </summary>
    public ICollection<GrantMoa> GrantMoas { get; set; } = new List<GrantMoa>();
}

/// <summary>
/// Reusable atomic legal clause in the MerSETA clause library.
/// </summary>
public class MoaClause : BaseEntity
{
    /// <summary>
    /// Unique clause reference code (e.g. CLAUSE-PREAMBLE, CLAUSE-POPIA, CLAUSE-TRANCHES-01).
    /// </summary>
    public string ClauseCode { get; set; } = string.Empty;

    /// <summary>
    /// Human-readable title of the legal clause.
    /// </summary>
    public string ClauseTitle { get; set; } = string.Empty;

    /// <summary>
    /// Classification category (e.g. Statutory, Financial, Compliance, General, Annexure).
    /// </summary>
    public string Category { get; set; } = "General";

    /// <summary>
    /// Rich Markdown content of the clause containing dynamic tokens (e.g. {{OrganisationName}}, {{TotalContractValue}}).
    /// </summary>
    public string ClauseContent { get; set; } = string.Empty;

    /// <summary>
    /// Indicates if this clause is legally mandatory across all template variants.
    /// </summary>
    public bool IsMandatory { get; set; } = true;

    /// <summary>
    /// Indicates whether the clause is active in the clause library.
    /// </summary>
    public bool IsActive { get; set; } = true;

    /// <summary>
    /// Template section associations where this clause is utilized.
    /// </summary>
    public ICollection<MoaTemplateSection> TemplateSections { get; set; } = new List<MoaTemplateSection>();
}

/// <summary>
/// Ordered section mapping a reusable clause into a specific MoA template with custom numbering and conditions.
/// </summary>
public class MoaTemplateSection : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the parent MoaTemplate.
    /// </summary>
    public int MoaTemplateId { get; set; }

    /// <summary>
    /// Navigational reference to the parent MoaTemplate.
    /// </summary>
    public MoaTemplate? MoaTemplate { get; set; }

    /// <summary>
    /// Foreign key referencing the assigned MoaClause.
    /// </summary>
    public int MoaClauseId { get; set; }

    /// <summary>
    /// Navigational reference to the assigned MoaClause.
    /// </summary>
    public MoaClause? MoaClause { get; set; }

    /// <summary>
    /// Custom section number label in the generated contract (e.g. "1.0", "2.1", "Schedule A").
    /// </summary>
    public string SectionNumber { get; set; } = string.Empty;

    /// <summary>
    /// Section heading title override.
    /// </summary>
    public string SectionTitle { get; set; } = string.Empty;

    /// <summary>
    /// Sorting sequence order within the document layout (e.g. 10, 20, 30).
    /// </summary>
    public int SequenceOrder { get; set; }

    /// <summary>
    /// Whether this section is mandatory for this specific template.
    /// </summary>
    public bool IsMandatory { get; set; } = true;

    /// <summary>
    /// Optional conditional inclusion rule expression in JSON format.
    /// </summary>
    public string? ConditionRuleJson { get; set; }
}

/// <summary>
/// Cryptographically frozen snapshot of an issued MoA contract for legal non-repudiation and audit defense.
/// </summary>
public class MoaExecutionSnapshot : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the issued GrantMoa contract.
    /// </summary>
    public int GrantMoaId { get; set; }

    /// <summary>
    /// Navigational reference to the GrantMoa.
    /// </summary>
    public GrantMoa? GrantMoa { get; set; }

    /// <summary>
    /// Foreign key referencing the MoaTemplate version applied at the moment of issuance.
    /// </summary>
    public int MoaTemplateId { get; set; }

    /// <summary>
    /// Navigational reference to the applied MoaTemplate.
    /// </summary>
    public MoaTemplate? MoaTemplate { get; set; }

    /// <summary>
    /// Captured template version string (e.g. 1.2.0).
    /// </summary>
    public string TemplateVersionNumber { get; set; } = string.Empty;

    /// <summary>
    /// Cryptographic SHA-256 digital fingerprint hash of the assembled document content.
    /// </summary>
    public string RenderedContentHash { get; set; } = string.Empty;

    /// <summary>
    /// Fully assembled legal text in Markdown/Text format with all tokens interpolated.
    /// </summary>
    public string RenderedContent { get; set; } = string.Empty;

    /// <summary>
    /// Immutable digital storage URI of the generated signed PDF document.
    /// </summary>
    public string? PdfStorageUri { get; set; }

    /// <summary>
    /// Timestamp when this snapshot was frozen and issued.
    /// </summary>
    public DateTime FrozenAt { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Employer signatory name captured at signing time.
    /// </summary>
    public string? SignatoryEmployer { get; set; }

    /// <summary>
    /// MerSETA delegated signatory name captured at signing time.
    /// </summary>
    public string? SignatorySeta { get; set; }
}
