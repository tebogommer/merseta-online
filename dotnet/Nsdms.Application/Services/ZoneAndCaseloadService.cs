using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.Json;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

/// <summary>
/// Service implementation for Phase 1: Spatial Zoning, Auto-Intake & Foundational Caseload Heatmap (The Smart Backbone).
/// </summary>
public class ZoneAndCaseloadService : IZoneAndCaseloadService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;
    private readonly IPdfDocumentService _pdfService;

    public ZoneAndCaseloadService(
        INsdmsDbContextFactory contextFactory,
        IAuditService audit,
        IPdfDocumentService pdfService)
    {
        _contextFactory = contextFactory;
        _audit = audit;
        _pdfService = pdfService;
    }

    #region 1. Zone Management

    public async Task<List<TerritoryZoneDto>> GetAllZonesAsync(string? regionCode = null, string? search = null)
    {
        using var context = _contextFactory.CreateDbContext();
        var query = context.TerritoryZones
            .Include(z => z.Demarcations)
            .Where(z => z.IsActive)
            .AsQueryable();

        if (!string.IsNullOrWhiteSpace(regionCode))
        {
            query = query.Where(z => z.RegionCode == regionCode);
        }

        if (!string.IsNullOrWhiteSpace(search))
        {
            var clean = search.Trim().ToLower();
            query = query.Where(z => z.ZoneName.ToLower().Contains(clean) ||
                                     z.ZoneCode.ToLower().Contains(clean) ||
                                     (z.DefaultOfficerName != null && z.DefaultOfficerName.ToLower().Contains(clean)));
        }

        var zones = await query.OrderBy(z => z.RegionCode).ThenBy(z => z.ZoneName).ToListAsync();

        // Get count of employers in each region
        var portfoliosByRegion = await context.OrganisationPortfolios
            .Where(p => p.IsActive)
            .GroupBy(p => p.ManagingRegionCode)
            .Select(g => new { RegionCode = g.Key, Count = g.Count() })
            .ToDictionaryAsync(g => g.RegionCode, g => g.Count);

        var dtos = new List<TerritoryZoneDto>();
        foreach (var z in zones)
        {
            portfoliosByRegion.TryGetValue(z.RegionCode, out var regionalCount);
            // Distribute or estimate employers per zone based on town proportion
            var townCount = z.Demarcations?.Count ?? 0;
            var estimatedEmployers = townCount > 0 ? Math.Max(townCount * 8, regionalCount / Math.Max(1, zones.Count(x => x.RegionCode == z.RegionCode))) : 0;

            dtos.Add(new TerritoryZoneDto(
                z.Id,
                z.ZoneCode,
                z.ZoneName,
                z.RegionCode,
                z.RegionName,
                z.ProvinceCode,
                z.DefaultOfficerUserId,
                z.DefaultOfficerName,
                z.DefaultOfficerEmail,
                z.Description,
                z.IsActive,
                townCount,
                estimatedEmployers
            ));
        }

        return dtos;
    }

    public async Task<TerritoryZone?> GetZoneByIdAsync(int zoneId)
    {
        using var context = _contextFactory.CreateDbContext();
        return await context.TerritoryZones
            .Include(z => z.Demarcations)
            .FirstOrDefaultAsync(z => z.Id == zoneId);
    }

    public async Task<TerritoryZone> SaveZoneAsync(TerritoryZone zone, string currentUsername)
    {
        using var context = _contextFactory.CreateDbContext();

        if (zone.Id == 0)
        {
            zone.CreatedBy = currentUsername;
            zone.CreatedAt = DateTime.UtcNow;
            context.TerritoryZones.Add(zone);
        }
        else
        {
            var existing = await context.TerritoryZones.FindAsync(zone.Id)
                ?? throw new KeyNotFoundException($"TerritoryZone #{zone.Id} not found.");

            existing.ZoneCode = zone.ZoneCode;
            existing.ZoneName = zone.ZoneName;
            existing.RegionCode = zone.RegionCode;
            existing.RegionName = zone.RegionName;
            existing.ProvinceCode = zone.ProvinceCode;
            existing.DefaultOfficerUserId = zone.DefaultOfficerUserId;
            existing.DefaultOfficerName = zone.DefaultOfficerName;
            existing.DefaultOfficerEmail = zone.DefaultOfficerEmail;
            existing.Description = zone.Description;
            existing.IsActive = zone.IsActive;
            existing.ModifiedBy = currentUsername;
            existing.ModifiedAt = DateTime.UtcNow;
            zone = existing;
        }

        await context.SaveChangesAsync();

        await _audit.LogActionAsync(
            "TerritoryZone",
            zone.Id,
            zone.Id == 0 ? "CREATE_ZONE" : "UPDATE_ZONE",
            currentUsername,
            $"Saved Zone {zone.ZoneCode} - {zone.ZoneName} for Region {zone.RegionCode}. Officer: {zone.DefaultOfficerName}");

        return zone;
    }

    public async Task<bool> DeleteZoneAsync(int zoneId, string currentUsername)
    {
        using var context = _contextFactory.CreateDbContext();
        var zone = await context.TerritoryZones.Include(z => z.Demarcations).FirstOrDefaultAsync(z => z.Id == zoneId);
        if (zone == null) return false;

        zone.IsActive = false;
        zone.ModifiedBy = currentUsername;
        zone.ModifiedAt = DateTime.UtcNow;

        foreach (var d in zone.Demarcations)
        {
            d.ZoneId = null;
            d.ZoneCode = null;
        }

        await context.SaveChangesAsync();

        await _audit.LogActionAsync(
            "TerritoryZone",
            zone.Id,
            "DEACTIVATE_ZONE",
            currentUsername,
            $"Deactivated Zone {zone.ZoneCode} and unlinked {zone.Demarcations.Count} towns.");

        return true;
    }

    public async Task AssignTownsToZoneAsync(int zoneId, List<int> demarcationIds, string currentUsername)
    {
        using var context = _contextFactory.CreateDbContext();
        var zone = await context.TerritoryZones.FindAsync(zoneId)
            ?? throw new KeyNotFoundException($"TerritoryZone #{zoneId} not found.");

        var demarcations = await context.TerritoryDemarcations
            .Where(d => demarcationIds.Contains(d.Id))
            .ToListAsync();

        foreach (var d in demarcations)
        {
            d.ZoneId = zone.Id;
            d.ZoneCode = zone.ZoneCode;
            d.ModifiedBy = currentUsername;
            d.ModifiedAt = DateTime.UtcNow;
        }

        await context.SaveChangesAsync();

        await _audit.LogActionAsync(
            "TerritoryZone",
            zone.Id,
            "ASSIGN_TOWNS_TO_ZONE",
            currentUsername,
            $"Assigned {demarcations.Count} towns to Zone {zone.ZoneCode} ({zone.ZoneName}).");
    }

    public async Task<List<TerritoryDemarcation>> GetDemarcationsForZoneAsync(int zoneId)
    {
        using var context = _contextFactory.CreateDbContext();
        return await context.TerritoryDemarcations
            .Where(d => d.ZoneId == zoneId)
            .OrderBy(d => d.TownName)
            .ToListAsync();
    }

    public async Task<List<TerritoryDemarcation>> GetUnzonedDemarcationsAsync(string? regionCode = null)
    {
        using var context = _contextFactory.CreateDbContext();
        var query = context.TerritoryDemarcations
            .Where(d => d.ZoneId == null && d.IsActive);

        if (!string.IsNullOrWhiteSpace(regionCode))
        {
            query = query.Where(d => d.RegionCode == regionCode);
        }

        return await query.OrderBy(d => d.TownName).ToListAsync();
    }

    #endregion

    #region 2. Event-Driven Auto-Demarcation Hook

    public async Task<OrganisationDemarcationResultDto> AutoDemarcateOrganisationAsync(
        int organisationId, 
        bool forceReassignment, 
        string currentUsername)
    {
        using var context = _contextFactory.CreateDbContext();

        var org = await context.Organisations
            .FirstOrDefaultAsync(o => o.Id == organisationId)
            ?? throw new KeyNotFoundException($"Organisation #{organisationId} not found.");

        var activePortfolio = await context.OrganisationPortfolios
            .Where(p => p.OrganisationId == organisationId && p.IsActive)
            .OrderByDescending(p => p.EffectiveFrom)
            .FirstOrDefaultAsync();

        // Rule: If strategic cross-regional account and not forced, preserve existing relationship officer
        if (!forceReassignment && activePortfolio != null && activePortfolio.IsCrossRegionalAssignment)
        {
            return new OrganisationDemarcationResultDto(
                org.Id,
                org.LegalName,
                org.PostalAddressPostalCode,
                activePortfolio.ManagingRegionCode,
                GetRegionDisplayName(activePortfolio.ManagingRegionCode),
                null,
                "Cross-Regional Strategic Account",
                activePortfolio.RelationshipOfficerUserId,
                activePortfolio.RelationshipOfficerName,
                true,
                "ExemptedStrategicAccount",
                "Organisation holds active cross-regional strategic portfolio assignment. Territorial override skipped."
            );
        }

        // Resolve town from PostalAddressPostalCode or PhysicalAddress
        var allDemarcations = await context.TerritoryDemarcations
            .Include(d => d.Zone)
            .Where(d => d.IsActive && (d.EffectiveTo == null || d.EffectiveTo > DateTime.UtcNow))
            .ToListAsync();

        TerritoryDemarcation? matchedDemarcation = null;
        var physical = org.PhysicalAddress?.ToLower() ?? "";
        var postalCode = org.PostalAddressPostalCode?.ToLower() ?? "";

        // Exact town name match on postal/physical
        matchedDemarcation = allDemarcations.FirstOrDefault(d => 
            !string.IsNullOrWhiteSpace(postalCode) && d.TownName.ToLower() == postalCode);

        if (matchedDemarcation == null && !string.IsNullOrWhiteSpace(physical))
        {
            matchedDemarcation = allDemarcations
                .OrderByDescending(d => d.TownName.Length)
                .FirstOrDefault(d => physical.Contains(d.TownName.ToLower()));
        }

        if (matchedDemarcation == null)
        {
            // Default fallback if town cannot be resolved
            var defaultRegion = org.ProvinceCode == "KZN" ? "KZN" : (org.ProvinceCode == "WC" ? "WESTERN_CAPE" : "GAUTENG_SOUTH");
            return new OrganisationDemarcationResultDto(
                org.Id,
                org.LegalName,
                null,
                defaultRegion,
                GetRegionDisplayName(defaultRegion),
                null,
                null,
                activePortfolio?.RelationshipOfficerUserId,
                activePortfolio?.RelationshipOfficerName,
                false,
                "TownNotFound",
                $"Could not automatically resolve town from address '{org.PhysicalAddress}'. Assigned region fallback: {defaultRegion}."
            );
        }

        // Officer resolution: Zonal default officer or regional default
        var targetRegion = matchedDemarcation.RegionCode;
        var targetZone = matchedDemarcation.Zone;
        var officerUserId = targetZone?.DefaultOfficerUserId;
        var officerName = targetZone?.DefaultOfficerName;
        var officerEmail = targetZone?.DefaultOfficerEmail;

        if (string.IsNullOrWhiteSpace(officerUserId))
        {
            // Fallback to first active CLO registered in that region
            var regionalOfficer = await context.StaffCapabilities
                .Where(c => (c.StationedRegionCode == targetRegion || c.StationedRegionCode == "NATIONAL") && c.IsActive)
                .FirstOrDefaultAsync();

            officerUserId = regionalOfficer?.UserId ?? "CLO_DEFAULT";
            officerName = regionalOfficer?.StaffName ?? "Regional CLO";
            officerEmail = regionalOfficer?.Email ?? "clo@merseta.org.za";
        }

        // If existing portfolio already matches, preserve
        if (activePortfolio != null && 
            activePortfolio.RelationshipOfficerUserId == officerUserId && 
            activePortfolio.ManagingRegionCode == targetRegion &&
            !forceReassignment)
        {
            return new OrganisationDemarcationResultDto(
                org.Id,
                org.LegalName,
                matchedDemarcation.TownName,
                targetRegion,
                matchedDemarcation.RegionName,
                targetZone?.ZoneCode,
                targetZone?.ZoneName,
                officerUserId,
                officerName,
                false,
                "PreservedExisting",
                $"Organisation already correctly allocated to {officerName} for {matchedDemarcation.TownName}."
            );
        }

        // Close old portfolio if active
        if (activePortfolio != null)
        {
            activePortfolio.IsActive = false;
            activePortfolio.EffectiveTo = DateTime.UtcNow;
            activePortfolio.ModifiedBy = currentUsername;
            activePortfolio.ModifiedAt = DateTime.UtcNow;
        }

        // Create new auto-assigned portfolio
        var newPortfolio = new OrganisationPortfolio
        {
            OrganisationId = org.Id,
            RelationshipOfficerUserId = officerUserId,
            RelationshipOfficerName = officerName,
            RelationshipOfficerEmail = officerEmail ?? $"{officerUserId.ToLower()}@merseta.org.za",
            PortfolioRoleCode = "PRIMARY_CLO",
            ManagingRegionCode = targetRegion,
            IsCrossRegionalAssignment = false,
            AssignmentReason = $"Auto-Demarcation: Matched Town '{matchedDemarcation.TownName}', Zone '{targetZone?.ZoneName ?? "Default"}'",
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
            "AUTO_DEMARCATE",
            currentUsername,
            $"Auto-demarcated Org #{org.Id} ({org.LegalName}) to Zone {targetZone?.ZoneCode} -> Officer {officerName}.");

        return new OrganisationDemarcationResultDto(
            org.Id,
            org.LegalName,
            matchedDemarcation.TownName,
            targetRegion,
            matchedDemarcation.RegionName,
            targetZone?.ZoneCode,
            targetZone?.ZoneName,
            officerUserId,
            officerName,
            false,
            "AutoAssigned",
            $"Successfully auto-assigned to {officerName} based on territorial demarcation for {matchedDemarcation.TownName}."
        );
    }

    #endregion

    #region 3. Caseload Heatmap Aggregation Engine

    public async Task<List<OfficerCaseloadMetricDto>> GetOfficerCaseloadHeatmapAsync(string? regionCode = null)
    {
        using var context = _contextFactory.CreateDbContext();

        // 1. Fetch unique officers from StaffCapabilities
        var capQuery = context.StaffCapabilities.Where(c => c.IsActive).AsQueryable();
        if (!string.IsNullOrWhiteSpace(regionCode))
        {
            capQuery = capQuery.Where(c => c.StationedRegionCode == regionCode || c.StationedRegionCode == "NATIONAL");
        }

        var allStaff = await capQuery.ToListAsync();
        var uniqueOfficers = allStaff
            .GroupBy(s => s.UserId)
            .Select(g => g.First())
            .OrderBy(s => s.StationedRegionCode)
            .ThenBy(s => s.StaffName)
            .ToList();

        var officerUserIds = uniqueOfficers.Select(o => o.UserId).ToList();

        // 2. Aggregate active organisations per officer
        var orgCounts = await context.OrganisationPortfolios
            .Where(p => p.IsActive && officerUserIds.Contains(p.RelationshipOfficerUserId))
            .GroupBy(p => p.RelationshipOfficerUserId)
            .Select(g => new { OfficerUserId = g.Key, Count = g.Count() })
            .ToDictionaryAsync(g => g.OfficerUserId, g => g.Count);

        // 3. Aggregate open workflow tasks per officer
        var openTasks = await context.WorkflowTasks
            .Where(t => officerUserIds.Contains(t.AssignedUserId!) && (t.TaskStatus == "Open" || t.TaskStatus == "Claimed"))
            .ToListAsync();

        var taskCounts = openTasks
            .GroupBy(t => t.AssignedUserId!)
            .ToDictionary(g => g.Key, g => g.Count());

        var overdueCounts = openTasks
            .Where(t => t.DueDate < DateTime.UtcNow)
            .GroupBy(t => t.AssignedUserId!)
            .ToDictionary(g => g.Key, g => g.Count());

        // 4. Aggregate visits scheduled this month
        var startOfMonth = new DateTime(DateTime.UtcNow.Year, DateTime.UtcNow.Month, 1);
        var endOfMonth = startOfMonth.AddMonths(1);

        var visitCounts = await context.FieldDispatchAssignments
            .Where(d => officerUserIds.Contains(d.DispatchedOfficerUserId) &&
                        (d.DispatchStatus == "Scheduled" || d.DispatchStatus == "Dispatched" || d.DispatchStatus == "Accepted") &&
                        d.ScheduledDate >= startOfMonth && d.ScheduledDate < endOfMonth)
            .GroupBy(d => d.DispatchedOfficerUserId)
            .Select(g => new { OfficerUserId = g.Key, Count = g.Count() })
            .ToDictionaryAsync(g => g.OfficerUserId, g => g.Count);

        // 5. Build DTOs with threshold calculations
        var heatmap = new List<OfficerCaseloadMetricDto>();

        foreach (var officer in uniqueOfficers)
        {
            orgCounts.TryGetValue(officer.UserId, out var assignedOrgs);
            taskCounts.TryGetValue(officer.UserId, out var tasksCount);
            overdueCounts.TryGetValue(officer.UserId, out var overdueCount);
            visitCounts.TryGetValue(officer.UserId, out var visitsCount);

            // Nominal target capacity: 80 active organisations
            var capacityPercentage = Math.Min(100, (assignedOrgs * 100) / 80);

            // PFMA SLA Capacity Tiers:
            // Critical: > 90 employers OR > 3 overdue tasks
            // Elevated: 60 - 90 employers OR 1 - 3 overdue tasks
            // Normal: < 60 employers AND 0 overdue tasks
            string capacityStatus;
            if (assignedOrgs >= 90 || overdueCount > 3)
            {
                capacityStatus = "Critical";
            }
            else if (assignedOrgs >= 60 || overdueCount > 0)
            {
                capacityStatus = "Elevated";
            }
            else
            {
                capacityStatus = "Normal";
            }

            heatmap.Add(new OfficerCaseloadMetricDto(
                officer.UserId,
                officer.StaffName,
                officer.Email,
                officer.StationedRegionCode,
                GetRegionDisplayName(officer.StationedRegionCode),
                officer.EmploymentRole,
                assignedOrgs,
                tasksCount,
                visitsCount,
                overdueCount,
                capacityPercentage,
                capacityStatus
            ));
        }

        return heatmap.OrderByDescending(h => h.OverdueSlaCount)
                      .ThenByDescending(h => h.AssignedOrganisationsCount)
                      .ToList();
    }

    #endregion

    #region 4. Stakeholder Communication

    public async Task<byte[]> GenerateLetterOfIntroductionPdfAsync(int organisationId, string currentUsername)
    {
        using var context = _contextFactory.CreateDbContext();

        var activePortfolio = await context.OrganisationPortfolios
            .Where(p => p.OrganisationId == organisationId && p.IsActive)
            .OrderByDescending(p => p.EffectiveFrom)
            .FirstOrDefaultAsync()
            ?? throw new InvalidOperationException($"Organisation #{organisationId} has no active assigned Relationship Officer.");

        var pdfBytes = await _pdfService.GenerateLetterOfIntroductionPdfAsync(organisationId, activePortfolio.Id);

        await _audit.LogActionAsync(
            "OrganisationPortfolio",
            activePortfolio.Id,
            "DOWNLOAD_INTRO_LETTER",
            currentUsername,
            $"Downloaded ETQ-TP-012 Letter of Introduction for Org #{organisationId} (Officer: {activePortfolio.RelationshipOfficerName}).");

        return pdfBytes;
    }

    #endregion

    #region Private Helpers

    private static string GetRegionDisplayName(string code) => code switch
    {
        "GAUTENG_SOUTH" => "Gauteng South Regional Office (Johannesburg)",
        "GAUTENG_NORTH" => "Gauteng North Regional Office (Pretoria)",
        "KZN" => "KwaZulu-Natal Regional Office (Durban)",
        "WESTERN_CAPE" => "Western Cape Regional Office (Cape Town)",
        "EASTERN_CAPE" => "Eastern Cape Regional Office (Gqeberha)",
        "FREE_STATE_NC" => "Free State & Northern Cape Regional Office (Bloemfontein)",
        "MPUMALANGA_LIMPOPO" => "Mpumalanga & Limpopo Regional Office (Witbank)",
        "NATIONAL" => "Head Office / National OEM Strategic Division",
        _ => code
    };

    #endregion
}
