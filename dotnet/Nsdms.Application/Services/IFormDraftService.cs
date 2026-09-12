namespace Nsdms.Application.Services;

public class FormDraftDto
{
    public string FormKey { get; set; } = string.Empty;
    public string? RecordId { get; set; }
    public string UserId { get; set; } = string.Empty;
    public string FormTitle { get; set; } = string.Empty;
    public string DataJson { get; set; } = string.Empty;
    public DateTime SavedAt { get; set; } = DateTime.UtcNow;
    public DateTime ExpiresAt { get; set; } = DateTime.UtcNow.AddDays(7);

    public bool IsExpired => DateTime.UtcNow > ExpiresAt;
}

public interface IFormDraftService
{
    Task SaveDraftAsync(string formKey, string? recordId, string formTitle, object data, int ttlHours = 168);
    Task<FormDraftDto?> GetDraftAsync(string formKey, string? recordId = null);
    Task<T?> GetDraftDataAsync<T>(string formKey, string? recordId = null);
    Task RemoveDraftAsync(string formKey, string? recordId = null);
    Task<IReadOnlyList<FormDraftDto>> ListDraftsAsync();
    Task ClearExpiredDraftsAsync();
}
