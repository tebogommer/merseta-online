using Nsdms.Domain.Entities;

namespace Nsdms.Application.Common.Interfaces;

public interface IEnterpriseDocumentTemplateService
{
    // Clause Management
    Task<List<DocumentClause>> GetClausesAsync(string? category = null);
    Task<DocumentClause?> GetClauseByIdAsync(int id);
    Task<DocumentClause> CreateClauseAsync(DocumentClause clause, string actor);
    Task<DocumentClause> UpdateClauseAsync(DocumentClause clause, string actor);
    Task<bool> DeleteClauseAsync(int id, string actor);

    // Template Management & Versioning
    Task<List<DocumentTemplate>> GetTemplatesAsync(string? category = null, int? financialYear = null);
    Task<DocumentTemplate?> GetTemplateByIdAsync(int id);
    Task<DocumentTemplate> CreateTemplateAsync(DocumentTemplate template, string actor);
    Task<DocumentTemplate> UpdateTemplateAsync(DocumentTemplate template, string actor);
    Task<DocumentTemplate> ApproveTemplateAsync(int templateId, string actor);
    Task<DocumentTemplate> ApproveAndActivateTemplateAsync(int templateId, string actor);
    Task<DocumentTemplate> CreateNewVersionAsync(int sourceTemplateId, string newVersionNumber, string versionNotes, string actor);
    Task<List<DocumentTemplate>> GetTemplateVersionHistoryAsync(string templateCode);
    Task<int> GetDocumentIssuanceCountForTemplateAsync(int templateId);
    Task<bool> DeleteTemplateAsync(int id, string actor);

    // Section Composition
    Task<DocumentTemplate> AddSectionAsync(int templateId, int clauseId, string sectionNumber, string sectionTitle, int sequenceOrder, bool isMandatory, string actor);
    Task<DocumentTemplate> RemoveSectionAsync(int templateId, int sectionId, string actor);
    Task<DocumentTemplate> ReorderSectionsAsync(int templateId, List<int> orderedSectionIds, string actor);

    // Resolution & Assembly
    Task<DocumentTemplate?> ResolveTemplateAsync(string documentTypeCode, int financialYear, string targetEntityType = "All");
    Task<string> AssembleDocumentTextAsync(int templateId, Dictionary<string, string> tokens);
    Task<string> AssembleDocumentHtmlAsync(int templateId, Dictionary<string, string>? tokens = null);
    Task<string> AssembleTemplatePreviewAsync(int templateId, Dictionary<string, string>? sampleTokens = null);
    Task<byte[]> GenerateSimulatedPdfAsync(int templateId, Dictionary<string, string>? sampleTokens = null, bool includeWatermark = true);
    Dictionary<string, Dictionary<string, string>> GetDefaultScenarioTokenProfiles();
}
