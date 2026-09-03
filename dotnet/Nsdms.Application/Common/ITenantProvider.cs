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
    /// True if the current user possesses administrative or system privileges allowing global visibility.
    /// </summary>
    bool IsAdmin { get; }
}
