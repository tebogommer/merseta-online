using Nsdms.Application.Common;

namespace Nsdms.Infrastructure.Services;

/// <summary>
/// Default implementation of ITenantProvider with configurable tenancy state and administrative bypass.
/// </summary>
public class DefaultTenantProvider : ITenantProvider
{
    public int? CurrentOrganisationId { get; set; }
    public string? CurrentOrganisationName { get; set; }
    public string? CurrentOrganisationSdl { get; set; }
    public bool IsAdmin { get; set; } = true; // Default to admin / system bypass

    public event Action? OnTenantChanged;

    public DefaultTenantProvider(int? organisationId = null, bool isAdmin = true)
    {
        CurrentOrganisationId = organisationId;
        IsAdmin = isAdmin;
    }

    public void SetTenant(int? organisationId, string? organisationName, string? sdlNumber, bool isAdmin = false)
    {
        CurrentOrganisationId = organisationId;
        CurrentOrganisationName = organisationName;
        CurrentOrganisationSdl = sdlNumber;
        IsAdmin = isAdmin;
        OnTenantChanged?.Invoke();
    }

    public void ResetToGlobalAdmin()
    {
        CurrentOrganisationId = null;
        CurrentOrganisationName = null;
        CurrentOrganisationSdl = null;
        IsAdmin = true;
        OnTenantChanged?.Invoke();
    }
}
