using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

/// <summary>
/// Implementation of Option B: Unified Dynamic Portfolio & Capability Dispatch Engine.
/// Provides enterprise account management, cross-regional allocations, capability-based dispatching, and audited successor handoffs.
/// </summary>
public class PortfolioDispatchService : IPortfolioDispatchService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;

    public PortfolioDispatchService(INsdmsDbContextFactory contextFactory, IAuditService audit)
    {
        _contextFactory = contextFactory;
        _audit = audit;
    }

    #region 1. Territory Demarcation Operations

    public async Task<List<TerritoryDemarcation>> GetAllTerritoryDemarcationsAsync(bool activeOnly = true)
    {
        using var context = _contextFactory.CreateDbContext();
        var query = context.TerritoryDemarcations.AsQueryable();

        if (activeOnly)
        {
            query = query.Where(t => t.IsActive && (t.EffectiveTo == null || t.EffectiveTo > DateTime.UtcNow));
        }

        return await query.OrderBy(t => t.RegionName).ThenBy(t => t.TownName).ToListAsync();
    }

    public async Task<TerritoryDemarcation?> ResolveTerritoryForTownAsync(string townName, DateTime? asOfDate = null)
    {
        if (string.IsNullOrWhiteSpace(townName)) return null;

        using var context = _contextFactory.CreateDbContext();
        var date = asOfDate ?? DateTime.UtcNow;
        var cleanTown = townName.Trim();

        return await context.TerritoryDemarcations
            .Include(t => t.Zone)
            .Where(t => t.TownName.ToLower() == cleanTown.ToLower() &&
                        t.EffectiveFrom <= date &&
                        (t.EffectiveTo == null || t.EffectiveTo > date))
            .OrderByDescending(t => t.EffectiveFrom)
            .FirstOrDefaultAsync();
    }

    public async Task<TerritoryDemarcation> SaveTerritoryDemarcationAsync(TerritoryDemarcation demarcation, string currentUsername)
    {
        using var context = _contextFactory.CreateDbContext();

        if (demarcation.Id == 0)
        {
            demarcation.CreatedBy = currentUsername;
            demarcation.CreatedAt = DateTime.UtcNow;
            context.TerritoryDemarcations.Add(demarcation);
        }
        else
        {
            var existing = await context.TerritoryDemarcations.FindAsync(demarcation.Id)
                ?? throw new KeyNotFoundException($"TerritoryDemarcation with ID {demarcation.Id} not found.");

            existing.TownName = demarcation.TownName;
            existing.RegionCode = demarcation.RegionCode;
            existing.RegionName = demarcation.RegionName;
            existing.ProvinceCode = demarcation.ProvinceCode;
            existing.StatssaAreaCode = demarcation.StatssaAreaCode;
            existing.EffectiveFrom = demarcation.EffectiveFrom;
            existing.EffectiveTo = demarcation.EffectiveTo;
            existing.BoundaryGazetteReference = demarcation.BoundaryGazetteReference;
            existing.IsActive = demarcation.IsActive;
            existing.ModifiedBy = currentUsername;
            existing.ModifiedAt = DateTime.UtcNow;
        }

        await context.SaveChangesAsync();

        await _audit.LogActionAsync(
            "TerritoryDemarcation",
            demarcation.Id,
            demarcation.Id == 0 ? "CREATE" : "UPDATE",
            currentUsername,
            JsonSerializer.Serialize(demarcation));

        return demarcation;
    }

    #endregion

    #region 2. Staff Capability Operations

    public async Task<List<StaffCapability>> GetStaffCapabilitiesAsync(string? userId = null, string? capabilityCode = null, string? regionCode = null)
    {
        using var context = _contextFactory.CreateDbContext();
        var query = context.StaffCapabilities.AsQueryable();

        if (!string.IsNullOrWhiteSpace(userId))
            query = query.Where(c => c.UserId == userId);

        if (!string.IsNullOrWhiteSpace(capabilityCode))
            query = query.Where(c => c.CapabilityCode == capabilityCode);

        if (!string.IsNullOrWhiteSpace(regionCode))
            query = query.Where(c => c.StationedRegionCode == regionCode || c.StationedRegionCode == "NATIONAL");

        return await query.OrderBy(c => c.StaffName).ThenBy(c => c.CapabilityName).ToListAsync();
    }

    public async Task<List<StaffCapability>> GetEligibleOfficersForCapabilityAsync(string capabilityCode, string? regionCode = null)
    {
        using var context = _contextFactory.CreateDbContext();
        var query = context.StaffCapabilities
            .Where(c => c.CapabilityCode == capabilityCode &&
                        c.IsActive &&
                        (c.ExpiryDate == null || c.ExpiryDate > DateTime.UtcNow));

        if (!string.IsNullOrWhiteSpace(regionCode))
        {
            query = query.Where(c => c.StationedRegionCode == regionCode || c.StationedRegionCode == "NATIONAL");
        }

        return await query.OrderBy(c => c.StaffName).ToListAsync();
    }

    public async Task<StaffCapability> GrantStaffCapabilityAsync(StaffCapability capability, string currentUsername)
    {
        using var context = _contextFactory.CreateDbContext();

        capability.CreatedBy = currentUsername;
        capability.CreatedAt = DateTime.UtcNow;
        capability.IsActive = true;

        context.StaffCapabilities.Add(capability);
        await context.SaveChangesAsync();

        await _audit.LogActionAsync(
            "StaffCapability",
            capability.Id,
            "GRANT",
            currentUsername,
            JsonSerializer.Serialize(capability));

        return capability;
    }

    public async Task<bool> RevokeStaffCapabilityAsync(int id, string currentUsername)
    {
        using var context = _contextFactory.CreateDbContext();
        var capability = await context.StaffCapabilities.FindAsync(id);
        if (capability == null) return false;

        capability.IsActive = false;
        capability.ModifiedBy = currentUsername;
        capability.ModifiedAt = DateTime.UtcNow;

        await context.SaveChangesAsync();

        await _audit.LogActionAsync(
            "StaffCapability",
            capability.Id,
            "REVOKE",
            currentUsername,
            $"Revoked capability {capability.CapabilityCode} for {capability.StaffName}");

        return true;
    }

    #endregion

    #region 3. Organisation Portfolio Operations

    public async Task<OrganisationPortfolio?> GetActivePortfolioAsync(int organisationId)
    {
        using var context = _contextFactory.CreateDbContext();
        return await context.OrganisationPortfolios
            .Include(p => p.Organisation)
            .Where(p => p.OrganisationId == organisationId && p.IsActive && (p.EffectiveTo == null || p.EffectiveTo > DateTime.UtcNow))
            .OrderByDescending(p => p.EffectiveFrom)
            .FirstOrDefaultAsync();
    }

    public async Task<List<OrganisationPortfolio>> GetPortfolioHistoryAsync(int organisationId)
    {
        using var context = _contextFactory.CreateDbContext();
        return await context.OrganisationPortfolios
            .Where(p => p.OrganisationId == organisationId)
            .OrderByDescending(p => p.EffectiveFrom)
            .ToListAsync();
    }

    public async Task<List<OrganisationPortfolioDto>> GetAllPortfoliosAsync(string? regionCode = null, bool? crossRegionalOnly = null, string? search = null)
    {
        using var context = _contextFactory.CreateDbContext();

        var query = from p in context.OrganisationPortfolios
                    join o in context.Organisations on p.OrganisationId equals o.Id
                    where p.IsActive && (p.EffectiveTo == null || p.EffectiveTo > DateTime.UtcNow)
                    select new { Portfolio = p, Organisation = o };

        if (!string.IsNullOrWhiteSpace(regionCode))
            query = query.Where(x => x.Portfolio.ManagingRegionCode == regionCode);

        if (crossRegionalOnly.HasValue && crossRegionalOnly.Value)
            query = query.Where(x => x.Portfolio.IsCrossRegionalAssignment);

        if (!string.IsNullOrWhiteSpace(search))
        {
            var clean = search.Trim().ToLower();
            query = query.Where(x => x.Organisation.LegalName.ToLower().Contains(clean) ||
                                     x.Organisation.TradingName.ToLower().Contains(clean) ||
                                     (x.Organisation.SdlNumber != null && x.Organisation.SdlNumber.ToLower().Contains(clean)) ||
                                     x.Portfolio.RelationshipOfficerName.ToLower().Contains(clean));
        }

        var results = await query.OrderBy(x => x.Organisation.LegalName).ToListAsync();

        // Get count of open tasks per officer
        var officerIds = results.Select(r => r.Portfolio.RelationshipOfficerUserId).Distinct().ToList();
        var openTasksCounts = await context.WorkflowTasks
            .Where(t => officerIds.Contains(t.AssignedUserId!) && (t.TaskStatus == "Open" || t.TaskStatus == "Claimed"))
            .GroupBy(t => t.AssignedUserId!)
            .Select(g => new { OfficerId = g.Key, Count = g.Count() })
            .ToDictionaryAsync(g => g.OfficerId, g => g.Count);

        var dtoList = new List<OrganisationPortfolioDto>();
        foreach (var item in results)
        {
            openTasksCounts.TryGetValue(item.Portfolio.RelationshipOfficerUserId, out var taskCount);

            dtoList.Add(new OrganisationPortfolioDto(
                item.Portfolio.Id,
                item.Organisation.Id,
                item.Organisation.TradingName ?? item.Organisation.LegalName,
                item.Organisation.SdlNumber,
                item.Organisation.PhysicalAddress,
                item.Organisation.PostalAddressPostalCode,
                item.Portfolio.RelationshipOfficerUserId,
                item.Portfolio.RelationshipOfficerName,
                item.Portfolio.RelationshipOfficerEmail,
                item.Portfolio.PortfolioRoleCode,
                item.Portfolio.ManagingRegionCode,
                GetRegionDisplayName(item.Portfolio.ManagingRegionCode),
                item.Portfolio.IsCrossRegionalAssignment,
                item.Portfolio.AssignmentReason,
                item.Portfolio.EffectiveFrom,
                item.Portfolio.IsActive,
                taskCount
            ));
        }

        return dtoList;
    }

    public async Task<OrganisationPortfolio> AssignOrganisationOfficerAsync(
        int organisationId,
        string officerUserId,
        string officerName,
        string officerEmail,
        string roleCode,
        string managingRegionCode,
        bool isCrossRegional,
        string reason,
        string currentUsername)
    {
        using var context = _contextFactory.CreateDbContext();

        // Close any currently active portfolios for this organisation
        var existingActives = await context.OrganisationPortfolios
            .Where(p => p.OrganisationId == organisationId && p.IsActive)
            .ToListAsync();

        foreach (var old in existingActives)
        {
            old.IsActive = false;
            old.EffectiveTo = DateTime.UtcNow;
            old.ModifiedBy = currentUsername;
            old.ModifiedAt = DateTime.UtcNow;
        }

        var newPortfolio = new OrganisationPortfolio
        {
            OrganisationId = organisationId,
            RelationshipOfficerUserId = officerUserId,
            RelationshipOfficerName = officerName,
            RelationshipOfficerEmail = officerEmail,
            PortfolioRoleCode = roleCode,
            ManagingRegionCode = managingRegionCode,
            IsCrossRegionalAssignment = isCrossRegional,
            AssignmentReason = reason,
            EffectiveFrom = DateTime.UtcNow,
            AssignedByUserId = currentUsername,
            CreatedBy = currentUsername,
            CreatedAt = DateTime.UtcNow,
            IsActive = true
        };

        context.OrganisationPortfolios.Add(newPortfolio);
        await context.SaveChangesAsync();

        await _audit.LogActionAsync(
            "OrganisationPortfolio",
            newPortfolio.Id,
            "ASSIGN_OFFICER",
            currentUsername,
            $"Assigned {officerName} to Organisation ID {organisationId}. Reason: {reason}. CrossRegional: {isCrossRegional}");

        return newPortfolio;
    }

    #endregion

    #region 4. Successor Handoff & Task Reassignment Engine

    public async Task<PortfolioHandoffResultDto> ExecuteSuccessorHandoffAsync(SuccessorHandoffRequestDto request, string currentUsername)
    {
        if (request.OrganisationIds == null || request.OrganisationIds.Count == 0)
        {
            throw new ArgumentException("At least one organisation must be selected for portfolio handoff.");
        }

        using var context = _contextFactory.CreateDbContext();

        // 1. Find all active portfolios for the given organisations matching the predecessor officer
        var activePortfolios = await context.OrganisationPortfolios
            .Where(p => request.OrganisationIds.Contains(p.OrganisationId) &&
                        p.RelationshipOfficerUserId == request.FromOfficerUserId &&
                        p.IsActive)
            .ToListAsync();

        var successorOfficer = await context.StaffCapabilities
            .Where(c => c.UserId == request.ToOfficerUserId)
            .FirstOrDefaultAsync();

        var successorEmail = successorOfficer?.Email ?? $"{request.ToOfficerUserId.ToLower()}@merseta.org.za";

        var newPortfolios = new List<OrganisationPortfolio>();
        var handoffLogs = new List<PortfolioHandoffLog>();
        var reassignedTaskTitles = new List<string>();

        // 2. Query open tasks associated with predecessor officer for these organisations
        var openTasks = new List<WorkflowTask>();
        if (request.ReassignOpenTasks)
        {
            openTasks = await context.WorkflowTasks
                .Include(t => t.WorkflowInstance)
                .Where(t => t.AssignedUserId == request.FromOfficerUserId &&
                            (t.TaskStatus == "Open" || t.TaskStatus == "Claimed"))
                .ToListAsync();
        }

        var timestamp = DateTime.UtcNow;
        var securitySeed = $"{request.FromOfficerUserId}:{request.ToOfficerUserId}:{string.Join(",", request.OrganisationIds)}:{timestamp:O}";
        var securitySealHash = ComputeSha256(securitySeed);

        // 3. Process each organization portfolio
        foreach (var oldPortfolio in activePortfolios)
        {
            oldPortfolio.IsActive = false;
            oldPortfolio.EffectiveTo = timestamp;
            oldPortfolio.ModifiedBy = currentUsername;
            oldPortfolio.ModifiedAt = timestamp;

            var newPortfolio = new OrganisationPortfolio
            {
                OrganisationId = oldPortfolio.OrganisationId,
                RelationshipOfficerUserId = request.ToOfficerUserId,
                RelationshipOfficerName = request.ToOfficerName,
                RelationshipOfficerEmail = successorEmail,
                PortfolioRoleCode = oldPortfolio.PortfolioRoleCode,
                ManagingRegionCode = oldPortfolio.ManagingRegionCode,
                IsCrossRegionalAssignment = oldPortfolio.IsCrossRegionalAssignment,
                AssignmentReason = $"Successor Handoff from {request.FromOfficerName}: {request.Reason}",
                EffectiveFrom = timestamp,
                AssignedByUserId = currentUsername,
                CreatedBy = currentUsername,
                CreatedAt = timestamp,
                IsActive = true
            };
            newPortfolios.Add(newPortfolio);

            // Find tasks relevant to this organisation instance
            var relevantTasks = openTasks
                .Where(t => t.WorkflowInstance != null && t.WorkflowInstance.EntityId == oldPortfolio.OrganisationId)
                .ToList();

            var taskIds = relevantTasks.Select(t => t.Id).ToList();
            foreach (var t in relevantTasks)
            {
                t.AssignedUserId = request.ToOfficerUserId;
                t.AssignedUserName = request.ToOfficerName;
                t.ModifiedBy = currentUsername;
                t.ModifiedAt = timestamp;
                reassignedTaskTitles.Add($"{t.TaskTitle} (Task #{t.Id})");
            }

            var log = new PortfolioHandoffLog
            {
                FromOfficerUserId = request.FromOfficerUserId,
                FromOfficerName = request.FromOfficerName,
                ToOfficerUserId = request.ToOfficerUserId,
                ToOfficerName = request.ToOfficerName,
                OrganisationId = oldPortfolio.OrganisationId,
                AuthorizedByUserId = currentUsername,
                AuthorizedByName = currentUsername,
                HandoffReason = request.Reason,
                ReassignedTasksCount = taskIds.Count,
                ReassignedTaskIdsJson = JsonSerializer.Serialize(taskIds),
                SecuritySealHash = securitySealHash,
                CreatedBy = currentUsername,
                CreatedAt = timestamp
            };
            handoffLogs.Add(log);
        }

        context.OrganisationPortfolios.AddRange(newPortfolios);
        context.PortfolioHandoffLogs.AddRange(handoffLogs);
        await context.SaveChangesAsync();

        // 4. Double-write to audit_logs
        await _audit.LogActionAsync(
            "PortfolioHandoff",
            0,
            "SUCCESSOR_HANDOFF",
            currentUsername,
            $"Transferred {newPortfolios.Count} organisations and {reassignedTaskTitles.Count} open tasks from {request.FromOfficerName} to {request.ToOfficerName}. Seal: {securitySealHash}");

        return new PortfolioHandoffResultDto(
            true,
            newPortfolios.Count,
            reassignedTaskTitles.Count,
            securitySealHash,
            reassignedTaskTitles
        );
    }

    public async Task<List<PortfolioHandoffLog>> GetHandoffLogsAsync(int? organisationId = null, string? officerUserId = null)
    {
        using var context = _contextFactory.CreateDbContext();
        var query = context.PortfolioHandoffLogs.Include(h => h.Organisation).AsQueryable();

        if (organisationId.HasValue)
            query = query.Where(h => h.OrganisationId == organisationId.Value);

        if (!string.IsNullOrWhiteSpace(officerUserId))
            query = query.Where(h => h.FromOfficerUserId == officerUserId || h.ToOfficerUserId == officerUserId);

        return await query.OrderByDescending(h => h.CreatedAt).ToListAsync();
    }

    #endregion

    #region 5. Coordinator Dispatch & Scheduling Operations

    public async Task<List<FieldDispatchAssignment>> GetCoordinatorDispatchQueueAsync(string? status = null, string? regionCode = null, DateTime? date = null)
    {
        using var context = _contextFactory.CreateDbContext();
        var query = context.FieldDispatchAssignments
            .Include(d => d.Organisation)
            .Include(d => d.ContactPerson)
            .Include(d => d.Visit)
            .AsQueryable();

        if (!string.IsNullOrWhiteSpace(status))
            query = query.Where(d => d.DispatchStatus == status);

        if (date.HasValue)
        {
            var target = date.Value.Date;
            query = query.Where(d => d.ScheduledDate.Date == target);
        }

        return await query.OrderByDescending(d => d.ScheduledDate).ToListAsync();
    }

    public async Task<FieldDispatchAssignment> DispatchFieldVisitAsync(CreateFieldDispatchDto request, string currentUsername)
    {
        if (request.ContactPersonId <= 0)
        {
            throw new ArgumentException("Enforce Employer Contact Person selection: ContactPersonId is required for any scheduled visit activity.");
        }

        using var context = _contextFactory.CreateDbContext();

        // Check if officer has the required capability
        var hasCapability = await context.StaffCapabilities
            .AnyAsync(c => c.UserId == request.DispatchedOfficerUserId &&
                           c.CapabilityCode == request.RequiredCapabilityCode &&
                           c.IsActive);

        if (!hasCapability)
        {
            throw new InvalidOperationException($"Officer {request.DispatchedOfficerName} ({request.DispatchedOfficerUserId}) lacks certified capability credential '{request.RequiredCapabilityCode}'.");
        }

        var assignment = new FieldDispatchAssignment
        {
            OrganisationId = request.OrganisationId,
            VisitId = request.VisitId,
            ContactPersonId = request.ContactPersonId,
            ScheduledDate = request.ScheduledDate,
            ActivityTypeCode = request.ActivityTypeCode,
            RequiredCapabilityCode = request.RequiredCapabilityCode,
            DispatchedOfficerUserId = request.DispatchedOfficerUserId,
            DispatchedOfficerName = request.DispatchedOfficerName,
            ScheduledByCoordinatorUserId = currentUsername,
            ScheduledByCoordinatorName = currentUsername,
            DispatchStatus = "Dispatched",
            Priority = request.Priority,
            CoordinatorNotes = request.CoordinatorNotes,
            CreatedBy = currentUsername,
            CreatedAt = DateTime.UtcNow
        };

        context.FieldDispatchAssignments.Add(assignment);
        await context.SaveChangesAsync();

        await _audit.LogActionAsync(
            "FieldDispatchAssignment",
            assignment.Id,
            "DISPATCH_VISIT",
            currentUsername,
            $"Coordinator {currentUsername} dispatched {request.ActivityTypeCode} to {request.DispatchedOfficerName} for Organisation ID {request.OrganisationId} on {request.ScheduledDate:yyyy-MM-dd}");

        return assignment;
    }

    public async Task<FieldDispatchAssignment> UpdateDispatchStatusAsync(int dispatchId, string status, string? notes, string currentUsername)
    {
        using var context = _contextFactory.CreateDbContext();
        var assignment = await context.FieldDispatchAssignments.FindAsync(dispatchId)
            ?? throw new KeyNotFoundException($"FieldDispatchAssignment #{dispatchId} not found.");

        assignment.DispatchStatus = status;
        if (!string.IsNullOrWhiteSpace(notes))
        {
            assignment.OfficerAcceptanceNotes = notes;
        }

        if (status == "Completed")
        {
            assignment.CompletedDate = DateTime.UtcNow;
        }

        assignment.ModifiedBy = currentUsername;
        assignment.ModifiedAt = DateTime.UtcNow;

        await context.SaveChangesAsync();

        await _audit.LogActionAsync(
            "FieldDispatchAssignment",
            assignment.Id,
            "STATUS_CHANGE",
            currentUsername,
            $"Dispatch #{dispatchId} status transitioned to '{status}'. Notes: {notes}");

        return assignment;
    }

    public async Task<List<OfficerWorkloadDto>> GetOfficerWorkloadSummaryAsync(string? regionCode = null)
    {
        using var context = _contextFactory.CreateDbContext();

        var officersQuery = context.StaffCapabilities.AsQueryable();
        if (!string.IsNullOrWhiteSpace(regionCode))
        {
            officersQuery = officersQuery.Where(c => c.StationedRegionCode == regionCode || c.StationedRegionCode == "NATIONAL");
        }

        var capabilities = await officersQuery.Where(c => c.IsActive).ToListAsync();
        var officersGrouped = capabilities.GroupBy(c => c.UserId).ToList();

        var officerUserIds = officersGrouped.Select(g => g.Key).ToList();

        // 1. Portfolios count
        var portfolioCounts = await context.OrganisationPortfolios
            .Where(p => officerUserIds.Contains(p.RelationshipOfficerUserId) && p.IsActive)
            .GroupBy(p => p.RelationshipOfficerUserId)
            .Select(g => new { UserId = g.Key, Count = g.Count() })
            .ToDictionaryAsync(x => x.UserId, x => x.Count);

        // 2. Pending dispatches count
        var dispatchCounts = await context.FieldDispatchAssignments
            .Where(d => officerUserIds.Contains(d.DispatchedOfficerUserId) && (d.DispatchStatus == "Dispatched" || d.DispatchStatus == "ConfirmedByOfficer"))
            .GroupBy(d => d.DispatchedOfficerUserId)
            .Select(g => new { UserId = g.Key, Count = g.Count() })
            .ToDictionaryAsync(x => x.UserId, x => x.Count);

        // 3. Open workflow tasks count
        var taskCounts = await context.WorkflowTasks
            .Where(t => officerUserIds.Contains(t.AssignedUserId!) && (t.TaskStatus == "Open" || t.TaskStatus == "Claimed"))
            .GroupBy(t => t.AssignedUserId!)
            .Select(g => new { UserId = g.Key, Count = g.Count() })
            .ToDictionaryAsync(x => x.UserId, x => x.Count);

        var list = new List<OfficerWorkloadDto>();
        foreach (var group in officersGrouped)
        {
            var first = group.First();
            portfolioCounts.TryGetValue(group.Key, out var pCount);
            dispatchCounts.TryGetValue(group.Key, out var dCount);
            taskCounts.TryGetValue(group.Key, out var tCount);

            list.Add(new OfficerWorkloadDto(
                group.Key,
                first.StaffName,
                first.Email,
                first.StationedRegionCode,
                first.EmploymentRole,
                group.Select(c => c.CapabilityCode).Distinct().ToList(),
                pCount,
                dCount,
                tCount
            ));
        }

        return list.OrderByDescending(o => o.ActivePortfolioAccountsCount + o.PendingScheduledDispatchesCount).ToList();
    }

    #endregion

    #region Helpers

    private static string GetRegionDisplayName(string code) => code switch
    {
        "GAUTENG_SOUTH" => "Gauteng South Regional Office",
        "GAUTENG_NORTH" => "Gauteng North Regional Office",
        "WESTERN_CAPE" => "Western Cape Regional Office",
        "KZN" => "KwaZulu-Natal Regional Office",
        "EASTERN_CAPE" => "Eastern Cape Regional Office",
        "FREE_STATE_NC" => "Free State & Northern Cape Office",
        "MPUMALANGA_LIMPOPO" => "Mpumalanga & Limpopo Office",
        "NATIONAL" => "National Head Office / Shared Services",
        _ => code
    };

    private static string ComputeSha256(string raw)
    {
        var bytes = SHA256.HashData(Encoding.UTF8.GetBytes(raw));
        return Convert.ToHexString(bytes).ToLowerInvariant();
    }

    #endregion
}
