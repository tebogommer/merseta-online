namespace Nsdms.Application.Common;

/// <summary>
/// Provides tenancy context for multi-tenant data isolation and administrative bypass.
/// </summary>
public interface ITenantProvider
{
    /// <summary>
    /// The currently active organisation identifier for the logged-in user or session.
    /// Returns null if user is unauthenticated or has system-wide access.
    /// </summary>
    int? CurrentOrganisationId { get; }

    /// <summary>
    /// Descriptive name of the currently active organisation context.
    /// </summary>
    string? CurrentOrganisationName { get; }

    /// <summary>
    /// Statutory SDL reference of the currently active organisation context (e.g. L700100200).
    /// </summary>
    string? CurrentOrganisationSdl { get; }

    /// <summary>
    /// True if the current user possesses administrative or system privileges allowing global visibility.
    /// </summary>
    bool IsAdmin { get; }

    /// <summary>
    /// Notification event fired when the active organisation context changes.
    /// </summary>
    event Action? OnTenantChanged;

    /// <summary>
    /// Sets the active organisation context for the current user circuit/session.
    /// </summary>
    void SetTenant(int? organisationId, string? organisationName, string? sdlNumber, bool isAdmin = false);

    /// <summary>
    /// Resets the tenancy context to global administrator bypass.
    /// </summary>
    void ResetToGlobalAdmin();
}
