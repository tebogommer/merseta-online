using Nsdms.Application.Common.Models;

namespace Nsdms.Application.Services;

public class EmployeeFilterQuery
{
    public int PageIndex { get; set; } = 0;
    public int PageSize { get; set; } = 10;
    public string? SearchTerm { get; set; }
    public string? OccupationalCategory { get; set; }
    public string? EmploymentStatus { get; set; }
    public int? OrganisationSiteId { get; set; }
    public bool? IsActiveOnly { get; set; } = true;
}

public class OrganisationEmployeeDto
{
    public int Id { get; set; }
    public int OrganisationId { get; set; }
    public int PersonId { get; set; }
    public string FullName { get; set; } = string.Empty;
    public string FirstName { get; set; } = string.Empty;
    public string LastName { get; set; } = string.Empty;
    public string RsaIdMasked { get; set; } = string.Empty;
    public string? PassportNumberMasked { get; set; }
    public string? EmployeeNumber { get; set; }
    public string? JobTitle { get; set; }
    public string? OfoCodeId { get; set; }
    public string? OfoDescription { get; set; }
    public string? EmploymentTypeCode { get; set; }
    public string? EmploymentStatusCode { get; set; }
    public string? OccupationalCategoryCode { get; set; }
    public string? SiteName { get; set; }
    public int? OrganisationSiteId { get; set; }
    public string? GenderCode { get; set; }
    public string? EquityCode { get; set; }
    public bool HasDisability { get; set; }
    public DateTime? DateOfBirth { get; set; }
    public DateTime? StartDate { get; set; }
    public DateTime? EndDate { get; set; }
    public bool IsActive { get; set; }
}

public class CreateOrganisationEmployeeCommand
{
    public int OrganisationId { get; set; }
    public int? ExistingPersonId { get; set; }

    // Demographics (used when creating a new Person)
    public string FirstName { get; set; } = string.Empty;
    public string LastName { get; set; } = string.Empty;
    public string? RsaIdNumber { get; set; }
    public string? PassportNumber { get; set; }
    public string? GenderCode { get; set; } = "M";
    public string? EquityCode { get; set; } = "BA";
    public string? DisabilityCode { get; set; }
    public DateTime? DateOfBirth { get; set; }

    // Position Details
    public int? OrganisationSiteId { get; set; }
    public string? EmployeeNumber { get; set; }
    public string? JobTitle { get; set; }
    public string? OfoCodeId { get; set; }
    public string? EmploymentTypeCode { get; set; } = "PERMANENT";
    public string? EmploymentStatusCode { get; set; } = "ACTIVE";
    public string? OccupationalCategoryCode { get; set; } = "MANAGERS";
    public DateTime? StartDate { get; set; } = DateTime.UtcNow.Date;
    public DateTime? EndDate { get; set; }
}

public class UpdateOrganisationEmployeeCommand
{
    public int Id { get; set; }
    public string? JobTitle { get; set; }
    public string? OfoCodeId { get; set; }
    public int? OrganisationSiteId { get; set; }
    public string? EmploymentTypeCode { get; set; }
    public string? EmploymentStatusCode { get; set; }
    public string? OccupationalCategoryCode { get; set; }
    public DateTime? StartDate { get; set; }
    public DateTime? EndDate { get; set; }
    public bool IsActive { get; set; } = true;
}

public class BulkEmployeeImportResultDto
{
    public bool Success { get; set; }
    public int TotalRows { get; set; }
    public int InsertedCount { get; set; }
    public int UpdatedCount { get; set; }
    public int SkippedCount { get; set; }
    public List<string> Errors { get; set; } = new();
    public string Message { get; set; } = string.Empty;
}

public class WspHarvestResultDto
{
    public bool Success { get; set; }
    public int TotalEmployeesHarvested { get; set; }
    public int CategoriesUpdatedCount { get; set; }
    public int WspSubmissionId { get; set; }
    public DateTime HarvestedAt { get; set; } = DateTime.UtcNow;
    public List<WspCategoryBreakdownDto> CategoryBreakdown { get; set; } = new();
    public string Message { get; set; } = string.Empty;
}

public class WspCategoryBreakdownDto
{
    public string CategoryCode { get; set; } = string.Empty;
    public string CategoryName { get; set; } = string.Empty;
    public int MaleAfrican { get; set; }
    public int FemaleAfrican { get; set; }
    public int MaleColoured { get; set; }
    public int FemaleColoured { get; set; }
    public int MaleIndian { get; set; }
    public int FemaleIndian { get; set; }
    public int MaleWhite { get; set; }
    public int FemaleWhite { get; set; }
    public int DisabledCount { get; set; }
    public int Total { get; set; }
}

public interface IOrganisationEmployeeService
{
    Task<PagedResult<OrganisationEmployeeDto>> GetPagedEmployeesAsync(int organisationId, EmployeeFilterQuery query, CancellationToken ct = default);
    Task<OrganisationEmployeeDto?> GetEmployeeByIdAsync(int id, CancellationToken ct = default);
    Task<OrganisationEmployeeDto> CreateEmployeeAsync(CreateOrganisationEmployeeCommand command, string actor = "Admin", CancellationToken ct = default);
    Task<OrganisationEmployeeDto> UpdateEmployeeAsync(UpdateOrganisationEmployeeCommand command, string actor = "Admin", CancellationToken ct = default);
    Task<bool> TerminateEmployeeAsync(int id, DateTime terminationDate, string? reason = null, string actor = "Admin", CancellationToken ct = default);
    Task<BulkEmployeeImportResultDto> ImportEmployeesAsync(int organisationId, Stream fileStream, string fileName, string actor = "Admin", CancellationToken ct = default);
    Task<byte[]> ExportEmployeesTemplateAsync(CancellationToken ct = default);
    Task<byte[]> ExportEmployeesAsync(int organisationId, CancellationToken ct = default);
    Task<WspHarvestResultDto> HarvestWspTablesAsync(int organisationId, int wspSubmissionId, string actor = "Admin", CancellationToken ct = default);
}
