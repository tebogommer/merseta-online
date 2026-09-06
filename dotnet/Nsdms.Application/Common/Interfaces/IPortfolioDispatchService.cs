using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Common.Interfaces;

#region DTOs
public record OrganisationPortfolioDto(
    int Id,
    int OrganisationId,
    string OrganisationName,
    string? SdlNumber,
    string? PhysicalAddress,
    string? TownName,
    string RelationshipOfficerUserId,
    string RelationshipOfficerName,
    string RelationshipOfficerEmail,
    string PortfolioRoleCode,
    string ManagingRegionCode,
    string RegionName,
    bool IsCrossRegionalAssignment,
    string AssignmentReason,
    DateTime EffectiveFrom,
    bool IsActive,
    int ActiveOpenTasksCount
);

public record SuccessorHandoffRequestDto(
    string FromOfficerUserId,
    string FromOfficerName,
    string ToOfficerUserId,
    string ToOfficerName,
    List<int> OrganisationIds,
    string Reason,
    bool ReassignOpenTasks = true
);

public record PortfolioHandoffResultDto(
    bool Success,
    int TransferredOrganisationsCount,
    int ReassignedTasksCount,
    string SecuritySealHash,
    List<string> ReassignedTaskTitles
);

public record CreateFieldDispatchDto(
    int OrganisationId,
    int? VisitId,
    int ContactPersonId,
    DateTime ScheduledDate,
    string ActivityTypeCode,
    string RequiredCapabilityCode,
    string DispatchedOfficerUserId,
    string DispatchedOfficerName,
    string Priority = "Normal",
    string? CoordinatorNotes = null
);

public record OfficerWorkloadDto(
    string OfficerUserId,
    string OfficerName,
    string Email,
    string StationedRegionCode,
    string EmploymentRole,
    List<string> CertifiedCapabilities,
    int ActivePortfolioAccountsCount,
    int PendingScheduledDispatchesCount,
    int OpenWorkflowTasksCount
);
#endregion

/// <summary>
/// Service contract for Option B: Unified Dynamic Portfolio & Capability Dispatch Engine.
/// Decouples static lookups, handles cross-regional portfolios, temporal demarcation, CLC dispatch, and audited successor handoffs.
/// </summary>
public interface IPortfolioDispatchService
{
    // 1. Territory Demarcation
    Task<List<TerritoryDemarcation>> GetAllTerritoryDemarcationsAsync(bool activeOnly = true);
    Task<TerritoryDemarcation?> ResolveTerritoryForTownAsync(string townName, DateTime? asOfDate = null);
    Task<TerritoryDemarcation> SaveTerritoryDemarcationAsync(TerritoryDemarcation demarcation, string currentUsername);

    // 2. Staff Capabilities
    Task<List<StaffCapability>> GetStaffCapabilitiesAsync(string? userId = null, string? capabilityCode = null, string? regionCode = null);
    Task<List<StaffCapability>> GetEligibleOfficersForCapabilityAsync(string capabilityCode, string? regionCode = null);
    Task<StaffCapability> GrantStaffCapabilityAsync(StaffCapability capability, string currentUsername);
    Task<bool> RevokeStaffCapabilityAsync(int id, string currentUsername);

    // 3. Organisation Portfolios
    Task<OrganisationPortfolio?> GetActivePortfolioAsync(int organisationId);
    Task<List<OrganisationPortfolio>> GetPortfolioHistoryAsync(int organisationId);
    Task<List<OrganisationPortfolioDto>> GetAllPortfoliosAsync(string? regionCode = null, bool? crossRegionalOnly = null, string? search = null);
    Task<OrganisationPortfolio> AssignOrganisationOfficerAsync(int organisationId, string officerUserId, string officerName, string officerEmail, string roleCode, string managingRegionCode, bool isCrossRegional, string reason, string currentUsername);

    // 4. Successor Handoff & Task Reassignment
    Task<PortfolioHandoffResultDto> ExecuteSuccessorHandoffAsync(SuccessorHandoffRequestDto request, string currentUsername);
    Task<List<PortfolioHandoffLog>> GetHandoffLogsAsync(int? organisationId = null, string? officerUserId = null);

    // 5. Coordinator Dispatch & Scheduling
    Task<List<FieldDispatchAssignment>> GetCoordinatorDispatchQueueAsync(string? status = null, string? regionCode = null, DateTime? date = null);
    Task<FieldDispatchAssignment> DispatchFieldVisitAsync(CreateFieldDispatchDto request, string currentUsername);
    Task<FieldDispatchAssignment> UpdateDispatchStatusAsync(int dispatchId, string status, string? notes, string currentUsername);
    Task<List<OfficerWorkloadDto>> GetOfficerWorkloadSummaryAsync(string? regionCode = null);
}
