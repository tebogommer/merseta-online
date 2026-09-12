using System.Text.Json;
using Microsoft.AspNetCore.Components.Authorization;
using Microsoft.JSInterop;
using Nsdms.Application.Services;

namespace Nsdms.Web.Services;

public class FormDraftService : IFormDraftService
{
    private readonly IJSRuntime _jsRuntime;
    private readonly AuthenticationStateProvider _authStateProvider;

    public FormDraftService(IJSRuntime jsRuntime, AuthenticationStateProvider authStateProvider)
    {
        _jsRuntime = jsRuntime;
        _authStateProvider = authStateProvider;
    }

    private string BuildStorageKey(string formKey, string? recordId)
    {
        return string.IsNullOrEmpty(recordId) ? formKey : $"{formKey}_{recordId}";
    }

    private async Task<string> GetCurrentUserIdAsync()
    {
        var auth = await _authStateProvider.GetAuthenticationStateAsync();
        return auth.User?.Identity?.Name ?? "ANONYMOUS";
    }

    public async Task SaveDraftAsync(string formKey, string? recordId, string formTitle, object data, int ttlHours = 168)
    {
        try
        {
            var userId = await GetCurrentUserIdAsync();
            var storageKey = BuildStorageKey(formKey, recordId);
            var now = DateTime.UtcNow;

            var draft = new FormDraftDto
            {
                FormKey = formKey,
                RecordId = recordId,
                UserId = userId,
                FormTitle = formTitle,
                DataJson = JsonSerializer.Serialize(data),
                SavedAt = now,
                ExpiresAt = now.AddHours(ttlHours)
            };

            var json = JsonSerializer.Serialize(draft);
            await _jsRuntime.InvokeVoidAsync("NsdmsDrafts.saveDraft", storageKey, json);
        }
        catch (InvalidOperationException)
        {
            // Prerendering or JS not available yet
        }
        catch (JSDisconnectedException)
        {
            // Circuit disconnected
        }
    }

    public async Task<FormDraftDto?> GetDraftAsync(string formKey, string? recordId = null)
    {
        try
        {
            var storageKey = BuildStorageKey(formKey, recordId);
            var json = await _jsRuntime.InvokeAsync<string?>("NsdmsDrafts.getDraft", storageKey);

            if (string.IsNullOrWhiteSpace(json))
                return null;

            var draft = JsonSerializer.Deserialize<FormDraftDto>(json);
            if (draft != null && draft.IsExpired)
            {
                await RemoveDraftAsync(formKey, recordId);
                return null;
            }

            return draft;
        }
        catch (InvalidOperationException)
        {
            return null;
        }
        catch (JSDisconnectedException)
        {
            return null;
        }
    }

    public async Task<T?> GetDraftDataAsync<T>(string formKey, string? recordId = null)
    {
        var draft = await GetDraftAsync(formKey, recordId);
        if (draft == null || string.IsNullOrWhiteSpace(draft.DataJson))
            return default;

        try
        {
            return JsonSerializer.Deserialize<T>(draft.DataJson);
        }
        catch
        {
            return default;
        }
    }

    public async Task RemoveDraftAsync(string formKey, string? recordId = null)
    {
        try
        {
            var storageKey = BuildStorageKey(formKey, recordId);
            await _jsRuntime.InvokeVoidAsync("NsdmsDrafts.removeDraft", storageKey);
        }
        catch (InvalidOperationException)
        {
        }
        catch (JSDisconnectedException)
        {
        }
    }

    public async Task<IReadOnlyList<FormDraftDto>> ListDraftsAsync()
    {
        try
        {
            var drafts = await _jsRuntime.InvokeAsync<List<FormDraftDto>>("NsdmsDrafts.listDrafts");
            return drafts?.Where(d => !d.IsExpired).ToList() ?? new List<FormDraftDto>();
        }
        catch (InvalidOperationException)
        {
            return Array.Empty<FormDraftDto>();
        }
        catch (JSDisconnectedException)
        {
            return Array.Empty<FormDraftDto>();
        }
    }

    public async Task ClearExpiredDraftsAsync()
    {
        try
        {
            await _jsRuntime.InvokeVoidAsync("NsdmsDrafts.clearExpiredDrafts");
        }
        catch (InvalidOperationException)
        {
        }
        catch (JSDisconnectedException)
        {
        }
    }
}
