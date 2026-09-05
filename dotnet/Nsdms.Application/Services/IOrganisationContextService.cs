namespace Nsdms.Application.Services;

public class AffiliatedOrganisationDto
{
    public int OrganisationId { get; set; }
    public string OrganisationName { get; set; } = string.Empty;
    public string SdlNumber { get; set; } = string.Empty;
    public string? TradingName { get; set; }
    public string? ChamberCode { get; set; }
    public string? ChamberName { get; set; }
    public string? LegalStatus { get; set; }
    public List<string> Roles { get; set; } = new();
    public string RolesSummary => Roles.Count > 0 ? string.Join(", ", Roles) : "Affiliated Member";
    public bool IsPrimary { get; set; }
    public string? WspStatus { get; set; }
    public string? DgStatus { get; set; }
    public string? WpaStatus { get; set; }
    public int PendingTaskCount { get; set; }
}

public interface IOrganisationContextService
{
    /// <summary>
    /// Retrieves all organisations affiliated with the specified person/user.
    /// If isAdmin is true, returns top organisations and allows global registry selection.
    /// </summary>
    Task<List<AffiliatedOrganisationDto>> GetAffiliatedOrganisationsAsync(string? userEmail, string? personIdNumber = null, bool isAdmin = false);

    /// <summary>
    /// Gets a summarized DTO for a specific organisation.
    /// </summary>
    Task<AffiliatedOrganisationDto?> GetOrganisationSummaryAsync(int organisationId);

    /// <summary>
    /// Searches the full organisation registry (used by Admin/internal SETA roles).
    /// </summary>
    Task<List<AffiliatedOrganisationDto>> SearchAllOrganisationsAsync(string? searchTerm, int maxResults = 25);
}
