using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public interface IStorageService
{
    Task<DocumentMetadata> UploadDocumentAsync(
        string entityName, 
        int entityId, 
        string docTypeCode, 
        string docTypeName, 
        string fileName, 
        Stream contentStream, 
        string contentType, 
        string userId, 
        string userName);

    Task<List<DocumentMetadata>> GetDocumentsForEntityAsync(string entityName, int entityId);
    Task<DocumentMetadata?> GetDocumentByIdAsync(int documentId);
    Task<bool> VerifyDocumentAsync(int documentId, string verifierUserId, string? notes = null);
    Task<bool> DeleteDocumentAsync(int documentId);
}
