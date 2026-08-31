using Nsdms.Domain.Entities;

namespace Nsdms.Application.Common.Interfaces;

/// <summary>
/// Service contract for the Relational Block-Based MoA Template &amp; Clause Engine (Option A).
/// </summary>
public interface IMoaTemplateEngineService
{
    #region Clause Library Operations

    /// <summary>
    /// Retrieves all reusable MoA clauses with optional category and status filtering.
    /// </summary>
    Task<List<MoaClause>> GetClausesAsync(string? category = null, bool activeOnly = false);

    /// <summary>
    /// Retrieves a specific clause by ID with its template associations.
    /// </summary>
    Task<MoaClause?> GetClauseByIdAsync(int id);

    /// <summary>
    /// Creates a new reusable clause in the library.
    /// </summary>
    Task<MoaClause> CreateClauseAsync(MoaClause clause, string currentUsername);

    /// <summary>
    /// Updates an existing reusable clause and audits the modification.
    /// </summary>
    Task<MoaClause> UpdateClauseAsync(MoaClause clause, string currentUsername);

    /// <summary>
    /// Deactivates or removes a clause if not referenced in active templates.
    /// </summary>
    Task<bool> DeleteClauseAsync(int id, string currentUsername);

    #endregion

    #region Template Lifecycle & Composition Operations

    /// <summary>
    /// Retrieves all master MoA templates with optional financial year and grant type filtering.
    /// </summary>
    Task<List<MoaTemplate>> GetTemplatesAsync(int? financialYear = null, string? grantTypeCode = null);

    /// <summary>
    /// Retrieves a complete MoA template by ID with its ordered sections and clauses.
    /// </summary>
    Task<MoaTemplate?> GetTemplateByIdAsync(int id);

    /// <summary>
    /// Creates a new master MoA template draft.
    /// </summary>
    Task<MoaTemplate> CreateTemplateAsync(MoaTemplate template, string currentUsername);

    /// <summary>
    /// Updates template metadata and policy configuration.
    /// </summary>
    Task<MoaTemplate> UpdateTemplateAsync(MoaTemplate template, string currentUsername);

    /// <summary>
    /// Formally approves and activates a template for production contract generation.
    /// </summary>
    Task<MoaTemplate> ApproveTemplateAsync(int templateId, string currentUsername);

    /// <summary>
    /// Adds a clause section to a template.
    /// </summary>
    Task<MoaTemplate> AddSectionAsync(int templateId, int clauseId, string sectionNumber, string sectionTitle, int sequenceOrder, bool isMandatory, string currentUsername);

    /// <summary>
    /// Removes a clause section from a template.
    /// </summary>
    Task<MoaTemplate> RemoveSectionAsync(int sectionId, string currentUsername);

    /// <summary>
    /// Reorders the sequence of sections within a template.
    /// </summary>
    Task<MoaTemplate> ReorderSectionsAsync(int templateId, List<int> sectionIdsInOrder, string currentUsername);

    #endregion

    #region Dynamic Resolution & Assembly Engine

    /// <summary>
    /// Dynamically resolves the best matching approved MoA template based on financial year and grant criteria.
    /// </summary>
    Task<MoaTemplate?> ResolveTemplateAsync(int financialYear, string grantTypeCode, string? entityType = null);

    /// <summary>
    /// Assembles the complete MoA contract text in Markdown by stitching sections and interpolating grant tokens.
    /// </summary>
    Task<string> AssembleMoaMarkdownAsync(int grantMoaId, int? templateId = null);

    /// <summary>
    /// Generates a live preview of a template using real or sample grant data for verification in the Admin UI.
    /// </summary>
    Task<string> AssembleTemplatePreviewAsync(int templateId, int? sampleGrantMoaId = null);

    /// <summary>
    /// Generates a real-time simulated PDF byte stream for an MoA template using sample scenario tokens.
    /// </summary>
    Task<byte[]> GenerateSimulatedPdfAsync(int templateId, Dictionary<string, string>? sampleTokens = null, bool includeWatermark = true);

    /// <summary>
    /// Retrieves predefined scenario token profiles for MoA template testing.
    /// </summary>
    Dictionary<string, Dictionary<string, string>> GetDefaultScenarioTokenProfiles();

    #endregion

    #region Cryptographic Freezing & Snapshot Non-Repudiation

    /// <summary>
    /// Compiles the contract, computes its SHA-256 digital fingerprint, and creates an immutable snapshot.
    /// </summary>
    Task<MoaExecutionSnapshot> FreezeAndIssueMoaSnapshotAsync(int grantMoaId, string currentUsername);

    /// <summary>
    /// Retrieves the active/frozen legal snapshot for an issued Grant MoA contract.
    /// </summary>
    Task<MoaExecutionSnapshot?> GetLatestMoaSnapshotAsync(int grantMoaId);

    /// <summary>
    /// Retrieves all historical execution snapshots for an MoA.
    /// </summary>
    Task<List<MoaExecutionSnapshot>> GetMoaSnapshotsAsync(int grantMoaId);

    /// <summary>
    /// Verifies the cryptographic integrity of a frozen snapshot against its SHA-256 checksum.
    /// </summary>
    Task<bool> VerifySnapshotIntegrityAsync(int snapshotId);

    #endregion
}
