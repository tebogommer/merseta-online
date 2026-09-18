using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;
using System.Text.Json;

namespace Nsdms.Application.Services;

public class OrganisationHierarchyService : IOrganisationHierarchyService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;
    private readonly IOrganisationComplianceEngine _complianceEngine;

    public OrganisationHierarchyService(
        INsdmsDbContextFactory contextFactory,
        IAuditService audit,
        IOrganisationComplianceEngine complianceEngine)
    {
        _contextFactory = contextFactory;
        _audit = audit;
        _complianceEngine = complianceEngine;
    }

    public async Task<CorporateHierarchyNodeDto?> GetCorporateTreeAsync(int organisationId, CancellationToken cancellationToken = default)
    {
        if (organisationId <= 0) return null;

        await using var context = await _contextFactory.CreateDbContextAsync(cancellationToken);

        // 1. Traverse upwards to find the Ultimate Holding Parent
        int rootId = organisationId;
        var visitedUp = new HashSet<int> { organisationId };

        while (true)
        {
            var parentRef = await context.Organisations
                .AsNoTracking()
                .Where(o => o.Id == rootId)
                .Select(o => new { o.ParentOrganisationId })
                .FirstOrDefaultAsync(cancellationToken);

            if (parentRef?.ParentOrganisationId != null && !visitedUp.Contains(parentRef.ParentOrganisationId.Value))
            {
                rootId = parentRef.ParentOrganisationId.Value;
                visitedUp.Add(rootId);
            }
            else
            {
                break;
            }
        }

        // 2. Fetch all organisations in the system that could belong to this family tree
        // To build the tree efficiently, fetch the root and its reachable descendants
        var allDescendantIds = new HashSet<int> { rootId };
        var queue = new Queue<int>();
        queue.Enqueue(rootId);

        while (queue.Count > 0)
        {
            var currentId = queue.Dequeue();
            var childIds = await context.Organisations
                .AsNoTracking()
                .Where(o => o.ParentOrganisationId == currentId)
                .Select(o => o.Id)
                .ToListAsync(cancellationToken);

            foreach (var childId in childIds)
            {
                if (allDescendantIds.Add(childId))
                {
                    queue.Enqueue(childId);
                }
            }
        }

        var familyOrgs = await context.Organisations
            .AsNoTracking()
            .Where(o => allDescendantIds.Contains(o.Id))
            .ToListAsync(cancellationToken);

        var rootOrg = familyOrgs.FirstOrDefault(o => o.Id == rootId);
        if (rootOrg == null) return null;

        // 3. Build tree recursively
        return await BuildNodeRecursiveAsync(rootOrg, familyOrgs, organisationId, cancellationToken);
    }

    public async Task<CorporateGroupRollupSummaryDto> GetCorporateGroupRollupAsync(int organisationId, CancellationToken cancellationToken = default)
    {
        var tree = await GetCorporateTreeAsync(organisationId, cancellationToken);
        if (tree == null)
        {
            return new CorporateGroupRollupSummaryDto();
        }

        var flattened = new List<CorporateHierarchyNodeDto>();
        FlattenNodes(tree, flattened);

        await using var context = await _contextFactory.CreateDbContextAsync(cancellationToken);

        var orgIds = flattened.Select(f => f.OrganisationId).ToList();
        var sdlNumbers = flattened.Select(f => f.SdlNumber).Where(s => !string.IsNullOrWhiteSpace(s)).ToList();

        // 1. Total Active Learners across group
        int totalLearners = await context.CompanyLearners
            .AsNoTracking()
            .CountAsync(l => l.OrganisationId.HasValue && orgIds.Contains(l.OrganisationId.Value), cancellationToken);

        // 2. Total Reconciled Levies across group
        decimal totalLevies = 0m;
        if (sdlNumbers.Count > 0)
        {
            totalLevies = await context.LevyFileLines
                .AsNoTracking()
                .Where(l => sdlNumbers.Contains(l.SdlNumber))
                .SumAsync(l => l.MandatoryLevyAmount + l.DiscretionaryLevyAmount, cancellationToken);
        }

        // 3. Group Average Compliance Score
        decimal avgCompliance = flattened.Count > 0 
            ? Math.Round(flattened.Average(f => f.ComplianceScore), 1) 
            : 0m;

        string grade = avgCompliance switch
        {
            >= 85m => "Gold Standard Group (Full Compliance)",
            >= 65m => "Good Standing Group (Minor Remediation)",
            >= 50m => "Restricted Group (Action Required)",
            _ => "High Risk Corporate Group"
        };

        string badgeColor = avgCompliance switch
        {
            >= 85m => "Success",
            >= 65m => "Info",
            >= 50m => "Warning",
            _ => "Error"
        };

        return new CorporateGroupRollupSummaryDto
        {
            RootOrganisationId = tree.OrganisationId,
            RootCompanyName = tree.CompanyName,
            RootSdlNumber = tree.SdlNumber,
            TotalEntitiesCount = flattened.Count,
            TotalActiveLearnersCount = totalLearners,
            TotalReconciledLevyAmount = totalLevies,
            AverageComplianceScore = avgCompliance,
            GroupComplianceGrade = grade,
            GroupComplianceBadgeColor = badgeColor,
            FlattenedEntities = flattened
        };
    }

    public async Task<bool> LinkParentOrganisationAsync(int childOrgId, int? parentOrgId, string relationshipType, decimal? ownershipPct, string currentUsername = "Admin")
    {
        if (childOrgId <= 0) return false;

        await using var context = await _contextFactory.CreateDbContextAsync();

        var child = await context.Organisations.FirstOrDefaultAsync(o => o.Id == childOrgId);
        if (child == null) return false;

        if (parentOrgId.HasValue)
        {
            if (parentOrgId.Value == childOrgId)
            {
                throw new InvalidOperationException("An organisation cannot be set as its own holding parent.");
            }

            var parent = await context.Organisations.FirstOrDefaultAsync(o => o.Id == parentOrgId.Value);
            if (parent == null)
            {
                throw new InvalidOperationException("The selected parent organisation record does not exist.");
            }

            // Circular reference validation: Walk up the parent's ancestor chain
            int checkId = parentOrgId.Value;
            var visited = new HashSet<int> { checkId };

            while (true)
            {
                var ancestor = await context.Organisations
                    .AsNoTracking()
                    .Where(o => o.Id == checkId)
                    .Select(o => new { o.ParentOrganisationId })
                    .FirstOrDefaultAsync();

                if (ancestor?.ParentOrganisationId != null)
                {
                    if (ancestor.ParentOrganisationId.Value == childOrgId)
                    {
                        throw new InvalidOperationException("Circular corporate hierarchy detected: The target parent organisation is already a subsidiary of this entity.");
                    }

                    if (!visited.Add(ancestor.ParentOrganisationId.Value))
                    {
                        break; // Loop in existing graph detected, avoid infinite loop
                    }
                    checkId = ancestor.ParentOrganisationId.Value;
                }
                else
                {
                    break;
                }
            }

            // Capture before snapshot
            var beforeJson = JsonSerializer.Serialize(new
            {
                child.Id,
                child.CompanyName,
                child.ParentOrganisationId,
                child.MainSdlNumber,
                child.HoldingRelationshipType,
                child.OwnershipPercentage
            });

            child.ParentOrganisationId = parentOrgId.Value;
            child.MainSdlNumber = parent.SdlNumber;
            child.HoldingRelationshipType = string.IsNullOrWhiteSpace(relationshipType) ? "WHOLLY_OWNED_SUBSIDIARY" : relationshipType;
            child.OwnershipPercentage = ownershipPct ?? 100.00m;
            child.ModifiedBy = currentUsername;
            child.ModifiedAt = DateTime.UtcNow;

            await context.SaveChangesAsync();

            // Capture after snapshot
            var afterJson = JsonSerializer.Serialize(new
            {
                child.Id,
                child.CompanyName,
                child.ParentOrganisationId,
                child.MainSdlNumber,
                child.HoldingRelationshipType,
                child.OwnershipPercentage
            });

            await _audit.LogActionAsync(
                entityName: "Organisation",
                recordId: child.Id,
                actionName: "LinkParentHoldingCompany",
                actor: currentUsername,
                beforeState: new { Before = beforeJson, ParentId = parentOrgId.Value, ParentName = parent.CompanyName },
                afterState: new { After = afterJson, ParentId = parentOrgId.Value, ParentName = parent.CompanyName }
            );

            return true;
        }
        else
        {
            return await UnlinkParentOrganisationAsync(childOrgId, currentUsername);
        }
    }

    public async Task<bool> UnlinkParentOrganisationAsync(int childOrgId, string currentUsername = "Admin")
    {
        if (childOrgId <= 0) return false;

        await using var context = await _contextFactory.CreateDbContextAsync();

        var child = await context.Organisations.FirstOrDefaultAsync(o => o.Id == childOrgId);
        if (child == null) return false;

        var beforeJson = JsonSerializer.Serialize(new
        {
            child.Id,
            child.CompanyName,
            child.ParentOrganisationId,
            child.MainSdlNumber,
            child.HoldingRelationshipType,
            child.OwnershipPercentage
        });

        child.ParentOrganisationId = null;
        child.MainSdlNumber = null;
        child.HoldingRelationshipType = null;
        child.OwnershipPercentage = null;
        child.ModifiedBy = currentUsername;
        child.ModifiedAt = DateTime.UtcNow;

        await context.SaveChangesAsync();

        var afterJson = JsonSerializer.Serialize(new
        {
            child.Id,
            child.CompanyName,
            child.ParentOrganisationId,
            child.MainSdlNumber,
            child.HoldingRelationshipType,
            child.OwnershipPercentage
        });

        await _audit.LogActionAsync(
            entityName: "Organisation",
            recordId: child.Id,
            actionName: "UnlinkParentHoldingCompany",
            actor: currentUsername,
            beforeState: new { Before = beforeJson },
            afterState: new { After = afterJson }
        );

        return true;
    }

    public async Task<List<OrganisationLookupDto>> GetPotentialParentOrganisationsAsync(int currentOrgId, string? search = null, CancellationToken cancellationToken = default)
    {
        await using var context = await _contextFactory.CreateDbContextAsync(cancellationToken);

        // Get all descendant IDs of currentOrgId to exclude them from parent candidates
        var excludedIds = new HashSet<int> { currentOrgId };
        var queue = new Queue<int>();
        queue.Enqueue(currentOrgId);

        while (queue.Count > 0)
        {
            var cur = queue.Dequeue();
            var childIds = await context.Organisations
                .AsNoTracking()
                .Where(o => o.ParentOrganisationId == cur)
                .Select(o => o.Id)
                .ToListAsync(cancellationToken);

            foreach (var cid in childIds)
            {
                if (excludedIds.Add(cid))
                {
                    queue.Enqueue(cid);
                }
            }
        }

        var query = context.Organisations
            .AsNoTracking()
            .Where(o => !excludedIds.Contains(o.Id) && o.IsActive);

        if (!string.IsNullOrWhiteSpace(search))
        {
            query = query.Where(o => o.CompanyName.Contains(search) || o.SdlNumber.Contains(search));
        }

        return await query
            .OrderBy(o => o.CompanyName)
            .Take(25)
            .Select(o => new OrganisationLookupDto(
                o.Id,
                o.CompanyName,
                o.SdlNumber,
                o.RegistrationNumber))
            .ToListAsync(cancellationToken);
    }

    private async Task<CorporateHierarchyNodeDto> BuildNodeRecursiveAsync(
        Organisation current,
        List<Organisation> allFamilyOrgs,
        int activeOrgId,
        CancellationToken ct)
    {
        var compliance = await _complianceEngine.EvaluateOrganisationComplianceAsync(current.Id, ct);

        var node = new CorporateHierarchyNodeDto
        {
            OrganisationId = current.Id,
            CompanyName = current.CompanyName,
            TradingName = current.TradingName,
            SdlNumber = current.SdlNumber,
            MainSdlNumber = current.MainSdlNumber,
            ParentOrganisationId = current.ParentOrganisationId,
            HoldingRelationshipType = current.ParentOrganisationId == null ? "HOLDING_COMPANY" : (current.HoldingRelationshipType ?? "SUBSIDIARY"),
            OwnershipPercentage = current.ParentOrganisationId == null ? 100m : current.OwnershipPercentage,
            LevyCategoryCode = current.LevyCategoryCode,
            CompanySizeCode = current.CompanySizeCode,
            ProvinceCode = current.ProvinceCode,
            IsActive = current.IsActive,
            ComplianceScore = compliance.OverallScore,
            ComplianceGrade = compliance.OverallGrade,
            ComplianceBadgeColor = compliance.OverallBadgeColor,
            IsCurrentOrganisation = (current.Id == activeOrgId)
        };

        var directSubsidiaries = allFamilyOrgs
            .Where(o => o.ParentOrganisationId == current.Id)
            .OrderBy(o => o.CompanyName)
            .ToList();

        foreach (var sub in directSubsidiaries)
        {
            var childNode = await BuildNodeRecursiveAsync(sub, allFamilyOrgs, activeOrgId, ct);
            node.Children.Add(childNode);
        }

        return node;
    }

    private void FlattenNodes(CorporateHierarchyNodeDto node, List<CorporateHierarchyNodeDto> result)
    {
        result.Add(node);
        foreach (var child in node.Children)
        {
            FlattenNodes(child, result);
        }
    }
}