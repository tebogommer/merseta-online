using Nsdms.Application.Common.Models;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

/// <summary>
/// Service contract for managing DHET Gazetted Organising Framework for Occupations (OFO) releases.
/// </summary>
public interface IOfoCodeSetService
{
    /// <summary>
    /// Retrieves all registered statutory OFO framework releases (e.g. 2019, 2021, 2025).
    /// </summary>
    Task<List<OfoCodeSetDto>> GetActiveSetsAsync();

    /// <summary>
    /// Retrieves full details of an OFO set including occupational breakdown.
    /// </summary>
    Task<OfoCodeSetDto?> GetSetByIdAsync(int setId);

    /// <summary>
    /// Searches active occupational codes within a designated OFO set for autocomplete/picker dialogs.
    /// Strictly filters to codes where lookup.OfoCodeType.Active == true and membership in the set.
    /// </summary>
    Task<List<OfoCodeLookupDto>> SearchActiveCodesInSetAsync(int setId, string query, int maxResults = 50);

    /// <summary>
    /// Creates a new statutory OFO framework release record.
    /// </summary>
    Task<OfoCodeSet> CreateSetAsync(CreateOfoCodeSetDto dto, string userId);
}
