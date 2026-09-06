using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Common.Interfaces;

#region DTOs

public record TerritoryZoneDto(
    int Id,
    string ZoneCode,
    string ZoneName,
    string RegionCode,
    string RegionName,
    string ProvinceCode,
    string? DefaultOfficerUserId,
    string? DefaultOfficerName,
    string? DefaultOfficerEmail,
    string? Description,
    bool IsActive,
    int TownCount,
    int ActiveEmployersCount
);

public record OfficerCaseloadMetricDto(
    string OfficerUserId,
    string OfficerName,
    string OfficerEmail,
    string RegionCode,
    string RegionName,
    string PrimaryRole,
    int AssignedOrganisationsCount,
    int OpenWorkflowTasksCount,
    int PendingVisitsThisMonthCount,
    int OverdueSlaCount,
    int CapacityLoadPercentage,
    string CapacityStatus // "Normal", "Elevated", "Critical"
);

public record OrganisationDemarcationResultDto(
    int OrganisationId,
    string OrganisationName,
    string? ResolvedTown,
    string? ResolvedRegionCode,
    string? ResolvedRegionName,
    string? ResolvedZoneCode,
    string? ResolvedZoneName,
    string? AssignedOfficerUserId,
    string? AssignedOfficerName,
    bool IsCrossRegionalExemption,
    string ActionTaken, // "AutoAssigned", "PreservedExisting", "ExemptedStrategicAccount", "TownNotFound"
    string Message
);

#endregion

/// <summary>
/// Service contract for Phase 1: Spatial Zoning, Auto-Intake & Foundational Caseload Heatmap (The Smart Backbone).
/// Manages sub-regional zones, auto-demarcation on address changes, caseload metrics, and statutory introduction notices.
/// </summary>
public interface IZoneAndCaseloadService
{
    // Zone Management
    Task<List<TerritoryZoneDto>> GetAllZonesAsync(string? regionCode = null, string? search = null);
    Task<TerritoryZone?> GetZoneByIdAsync(int zoneId);
    Task<TerritoryZone> SaveZoneAsync(TerritoryZone zone, string currentUsername);
    Task<bool> DeleteZoneAsync(int zoneId, string currentUsername);
    Task AssignTownsToZoneAsync(int zoneId, List<int> demarcationIds, string currentUsername);
    Task<List<TerritoryDemarcation>> GetDemarcationsForZoneAsync(int zoneId);
    Task<List<TerritoryDemarcation>> GetUnzonedDemarcationsAsync(string? regionCode = null);

    // Auto-Demarcation Hook
    Task<OrganisationDemarcationResultDto> AutoDemarcateOrganisationAsync(int organisationId, bool forceReassignment, string currentUsername);

    // Caseload Heatmap Aggregation
    Task<List<OfficerCaseloadMetricDto>> GetOfficerCaseloadHeatmapAsync(string? regionCode = null);

    // Stakeholder Communications
    Task<byte[]> GenerateLetterOfIntroductionPdfAsync(int organisationId, string currentUsername);
}
