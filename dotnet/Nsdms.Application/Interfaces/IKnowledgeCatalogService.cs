using Nsdms.Application.Common;
using Nsdms.Application.Common.Models;
using Nsdms.Application.DTOs;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Interfaces;

public interface IKnowledgeCatalogService
{
    Task<PagedResult<ConceptSummaryDto>> GetConceptsAsync(ConceptFilterDto filter, CancellationToken ct = default);
    Task<ConceptDetailDto?> GetConceptByIdAsync(int id, CancellationToken ct = default);
    Task<ConceptDetailDto?> GetConceptByConceptIdAsync(string conceptId, CancellationToken ct = default);
    Task<ConceptDetailDto> CreateConceptAsync(CreateConceptCommand command, string currentUser, CancellationToken ct = default);
    Task<ConceptDetailDto> UpdateConceptAsync(UpdateConceptCommand command, string currentUser, CancellationToken ct = default);
    Task<bool> DeleteConceptAsync(int id, string currentUser, CancellationToken ct = default);
    Task<bool> RecordVerificationAsync(int conceptId, RecordVerificationCommand command, string currentUser, CancellationToken ct = default);
    Task<List<KnowledgeBundle>> GetBundlesAsync(CancellationToken ct = default);
    Task<byte[]> ExportBundleArchiveAsync(int bundleId, CancellationToken ct = default);
    TrustTier DeriveTrustTier(IEnumerable<ConceptVerificationEvent> verifications);
}
