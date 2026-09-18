using Nsdms.Application.Common.Models;

namespace Nsdms.Application.Common.Interfaces;

public class CorporateHierarchyNodeDto
{
    public int OrganisationId { get; set; }
    public string CompanyName { get; set; } = string.Empty;
    public string? TradingName { get; set; }
    public string SdlNumber { get; set; } = string.Empty;
    public string? MainSdlNumber { get; set; }
    public int? ParentOrganisationId { get; set; }
    public string HoldingRelationshipType { get; set; } = "WHOLLY_OWNED_SUBSIDIARY";
    public decimal? OwnershipPercentage { get; set; } = 100.00m;
    public string? LevyCategoryCode { get; set; }
    public string? CompanySizeCode { get; set; }
    public string? ProvinceCode { get; set; }
    public bool IsActive { get; set; } = true;
    public decimal ComplianceScore { get; set; }
    public string ComplianceGrade { get; set; } = "Compliant";
    public string ComplianceBadgeColor { get; set; } = "Success";
    public int DirectSubsidiariesCount => Children.Count;
    public int TotalDescendantsCount => Children.Count + Children.Sum(c => c.TotalDescendantsCount);
    public bool IsCurrentOrganisation { get; set; }
    public List<CorporateHierarchyNodeDto> Children { get; set; } = new();
}

public class CorporateGroupRollupSummaryDto
{
    public int RootOrganisationId { get; set; }
    public string RootCompanyName { get; set; } = string.Empty;
    public string RootSdlNumber { get; set; } = string.Empty;
    public int TotalEntitiesCount { get; set; }
    public int TotalActiveLearnersCount { get; set; }
    public decimal TotalReconciledLevyAmount { get; set; }
    public decimal AverageComplianceScore { get; set; }
    public string GroupComplianceGrade { get; set; } = "Compliant";
    public string GroupComplianceBadgeColor { get; set; } = "Success";
    public List<CorporateHierarchyNodeDto> FlattenedEntities { get; set; } = new();
}

public interface IOrganisationHierarchyService
{
    Task<CorporateHierarchyNodeDto?> GetCorporateTreeAsync(int organisationId, CancellationToken cancellationToken = default);
    Task<CorporateGroupRollupSummaryDto> GetCorporateGroupRollupAsync(int organisationId, CancellationToken cancellationToken = default);
    Task<bool> LinkParentOrganisationAsync(int childOrgId, int? parentOrgId, string relationshipType, decimal? ownershipPct, string currentUsername = "Admin");
    Task<bool> UnlinkParentOrganisationAsync(int childOrgId, string currentUsername = "Admin");
    Task<List<Nsdms.Application.Services.OrganisationLookupDto>> GetPotentialParentOrganisationsAsync(int currentOrgId, string? search = null, CancellationToken cancellationToken = default);
}