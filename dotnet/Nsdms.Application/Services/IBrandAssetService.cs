namespace Nsdms.Application.Services;

public interface IBrandAssetService
{
    Task<string?> GetLogoDataUriAsync(int targetEntityId, string targetEntityType);
    Task<bool> SetOrganisationBrandAsync(int organisationId, string? logoBase64, string? brandColorHex, string actor);
    Task<bool> SetProviderBrandAsync(int providerId, string? logoBase64, string actor);
}
