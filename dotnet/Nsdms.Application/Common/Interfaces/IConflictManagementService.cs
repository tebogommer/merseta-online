using Nsdms.Domain.Entities;

namespace Nsdms.Application.Common.Interfaces;

/// <summary>
/// Service contract for Module 18: Interest and Conflict of Interest (COI) Management.
/// Enforces corporate governance disclosures, institutional insider tracking,
/// multi-entity grant syndicate detection, and configurable statutory clearance under PFMA and King IV.
/// </summary>
public interface IConflictManagementService
{
    #region Organisation Governance Members
    Task<List<OrganisationGovernanceMember>> GetGovernanceMembersAsync(int organisationId);
    Task<OrganisationGovernanceMember?> GetGovernanceMemberByIdAsync(int id);
    Task<OrganisationGovernanceMember> AddGovernanceMemberAsync(OrganisationGovernanceMember member, string currentUsername);
    Task<OrganisationGovernanceMember> UpdateGovernanceMemberAsync(OrganisationGovernanceMember member, string currentUsername);
    Task<bool> DeleteGovernanceMemberAsync(int memberId, string currentUsername);
    Task<GovernanceValidationResult> ValidateOrganisationGovernanceComplianceAsync(int organisationId, bool isAccreditationContext = false);
    #endregion

    #region Institutional Insiders (Employees & Accounting Authority)
    Task<List<InstitutionalAffiliation>> GetInstitutionalAffiliationsAsync(bool activeOnly = true);
    Task<InstitutionalAffiliation?> GetInstitutionalAffiliationByIdAsync(int id);
    Task<InstitutionalAffiliation> AddInstitutionalAffiliationAsync(InstitutionalAffiliation affiliation, string currentUsername);
    Task<InstitutionalAffiliation> UpdateInstitutionalAffiliationAsync(InstitutionalAffiliation affiliation, string currentUsername);
    Task<bool> DeactivateInstitutionalAffiliationAsync(int id, string currentUsername);
    #endregion

    #region Declarations of Interest (e-DOI)
    Task<List<InterestDeclaration>> GetDeclarationsAsync(string? schemeYear = null, int? personId = null);
    Task<InterestDeclaration?> GetDeclarationByIdAsync(int id);
    Task<InterestDeclaration> SubmitDeclarationAsync(InterestDeclaration declaration, List<InterestDeclarationItem> items, string currentUsername);
    Task<InterestDeclaration> CertifyDeclarationAsync(int id, string currentUsername);
    #endregion

    #region Conflict Evaluation & Detection Engine
    Task<List<ConflictFlag>> EvaluateOrganisationConflictsAsync(int organisationId, string currentUsername = "SYSTEM");
    Task<List<ConflictFlag>> EvaluateGrantApplicationConflictsAsync(int grantApplicationId, string currentUsername = "SYSTEM");
    Task<List<ConflictFlag>> EvaluateAccreditationConflictsAsync(int trainingProviderId, string currentUsername = "SYSTEM");
    #endregion

    #region Conflict Flags & Configurable Clearance
    Task<List<ConflictFlag>> GetConflictFlagsAsync(string? severity = null, string? status = null, int? organisationId = null);
    Task<ConflictFlag?> GetConflictFlagByIdAsync(int id);
    Task<ConflictFlag> ResolveConflictFlagAsync(int flagId, string resolutionStatus, string resolutionNotes, string clearanceRole, string currentUsername);
    Task<List<string>> GetAuthorizedClearanceRolesAsync();
    #endregion

    #region Dashboard & Statutory Reports
    Task<ConflictDashboardMetricsDto> GetDashboardMetricsAsync();
    Task<List<AgsConflictReportItemDto>> GenerateAgsConflictReportAsync(string schemeYear);
    Task<List<MultiEntityGrantExposureDto>> GenerateMultiEntityGrantExposureReportAsync(string schemeYear);
    #endregion
}

public class GovernanceValidationResult
{
    public bool IsCompliant { get; set; } = true;
    public List<string> Violations { get; set; } = new List<string>();
    public int ActiveDirectorCount { get; set; } = 0;
    public int ActiveExecutiveDirectorCount { get; set; } = 0;
    public int ActiveNonExecutiveDirectorCount { get; set; } = 0;
    public int ActiveShareholderCount { get; set; } = 0;
    public int CorporateShareholderCount { get; set; } = 0;
    public decimal TotalShareholdingPercentageDeclared { get; set; } = 0.00m;
    public decimal MinimumThresholdPercentEnforced { get; set; } = 5.00m;
    public bool IsAccreditationContext { get; set; } = false;
}

public class ConflictDashboardMetricsDto
{
    public int TotalActiveFlags { get; set; }
    public int RedCriticalCount { get; set; }
    public int AmberElevatedCount { get; set; }
    public int YellowAdvisoryCount { get; set; }
    public int ClearedFlagsCount { get; set; }
    public int MultiEntitySyndicateCount { get; set; }
    public int OutstandingDeclarationsCount { get; set; }
    public decimal ConfiguredShareholdingThresholdPercent { get; set; }
    public List<string> ConfiguredClearanceRoles { get; set; } = new List<string>();
    public List<TopSharedDirectorDto> TopSharedDirectors { get; set; } = new List<TopSharedDirectorDto>();
    public List<ChamberConflictCountDto> ChamberDistribution { get; set; } = new List<ChamberConflictCountDto>();
    public List<ConflictFlag> RecentFlags { get; set; } = new List<ConflictFlag>();
}

public class TopSharedDirectorDto
{
    public int PersonId { get; set; }
    public string FullName { get; set; } = string.Empty;
    public string RsaIdNumber { get; set; } = string.Empty;
    public int LinkedOrganisationCount { get; set; }
    public int ActiveGrantApplicationCount { get; set; }
    public decimal TotalRequestedGrantAmount { get; set; }
    public List<string> OrganisationNames { get; set; } = new List<string>();
}

public class ChamberConflictCountDto
{
    public string ChamberCode { get; set; } = string.Empty;
    public string ChamberName { get; set; } = string.Empty;
    public int ConflictCount { get; set; }
}

public class AgsConflictReportItemDto
{
    public int ConflictFlagId { get; set; }
    public string ReferenceNumber { get; set; } = string.Empty;
    public string SeverityCode { get; set; } = string.Empty;
    public string ConflictCategoryCode { get; set; } = string.Empty;
    public string PersonFullName { get; set; } = string.Empty;
    public string PersonRsaId { get; set; } = string.Empty;
    public string InstitutionalAffiliation { get; set; } = string.Empty;
    public string OrganisationName { get; set; } = string.Empty;
    public string OrganisationSdl { get; set; } = string.Empty;
    public string GovernanceRole { get; set; } = string.Empty;
    public decimal ShareholdingPercentage { get; set; }
    public string ResolutionStatus { get; set; } = string.Empty;
    public string? ClearanceRole { get; set; }
    public string? ClearedBy { get; set; }
    public DateTime? ClearedDate { get; set; }
}

public class MultiEntityGrantExposureDto
{
    public int PersonId { get; set; }
    public string FullName { get; set; } = string.Empty;
    public string RsaIdNumber { get; set; } = string.Empty;
    public int TotalLinkedEntities { get; set; }
    public int TotalGrantApplications { get; set; }
    public decimal TotalRequestedAmount { get; set; }
    public decimal TotalApprovedAmount { get; set; }
    public List<LinkedEntityGrantItemDto> Entities { get; set; } = new List<LinkedEntityGrantItemDto>();
}

public class LinkedEntityGrantItemDto
{
    public int OrganisationId { get; set; }
    public string CompanyName { get; set; } = string.Empty;
    public string SdlNumber { get; set; } = string.Empty;
    public string RoleInEntity { get; set; } = string.Empty;
    public decimal ShareholdingPercentage { get; set; }
    public string ApplicationNumber { get; set; } = string.Empty;
    public string GrantStatusCode { get; set; } = string.Empty;
    public decimal RequestedAmount { get; set; }
}
