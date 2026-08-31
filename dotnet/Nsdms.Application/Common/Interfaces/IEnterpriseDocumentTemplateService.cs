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

    // Template Management
    Task<List<DocumentTemplate>> GetTemplatesAsync(string? category = null, int? financialYear = null);
    Task<DocumentTemplate?> GetTemplateByIdAsync(int id);
    Task<DocumentTemplate> CreateTemplateAsync(DocumentTemplate template, string actor);
    Task<DocumentTemplate> UpdateTemplateAsync(DocumentTemplate template, string actor);
    Task<DocumentTemplate> ApproveTemplateAsync(int templateId, string actor);
    Task<bool> DeleteTemplateAsync(int id, string actor);

    // Section Composition
    Task<DocumentTemplate> AddSectionAsync(int templateId, int clauseId, string sectionNumber, string sectionTitle, int sequenceOrder, bool isMandatory, string actor);
    Task<DocumentTemplate> RemoveSectionAsync(int templateId, int sectionId, string actor);
    Task<DocumentTemplate> ReorderSectionsAsync(int templateId, List<int> orderedSectionIds, string actor);

    // Resolution & Assembly
    Task<DocumentTemplate?> ResolveTemplateAsync(string documentTypeCode, int financialYear, string targetEntityType = "All");
    Task<string> AssembleDocumentTextAsync(int templateId, Dictionary<string, string> tokens);
    Task<string> AssembleTemplatePreviewAsync(int templateId, Dictionary<string, string>? sampleTokens = null);
    Task<byte[]> GenerateSimulatedPdfAsync(int templateId, Dictionary<string, string>? sampleTokens = null, bool includeWatermark = true);
    Dictionary<string, Dictionary<string, string>> GetDefaultScenarioTokenProfiles();
}
