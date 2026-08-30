namespace Nsdms.Application.Common.Models;

public class PagedResult<T>
{
    public List<T> Items { get; set; } = new();
    public int TotalCount { get; set; }
    public int PageIndex { get; set; }
    public int PageSize { get; set; }

    public int TotalPages => PageSize > 0 ? (int)Math.Ceiling((double)TotalCount / PageSize) : 0;
    public bool HasPreviousPage => PageIndex > 0;
    public bool HasNextPage => PageIndex + 1 < TotalPages;

    public PagedResult() { }

    public PagedResult(List<T> items, int totalCount, int pageIndex, int pageSize)
    {
        Items = items;
        TotalCount = totalCount;
        PageIndex = pageIndex;
        PageSize = pageSize;
    }
}

public class PaginationQuery
{
    public string? SearchText { get; set; }
    public int PageIndex { get; set; } = 0;
    public int PageSize { get; set; } = 20;
    public string? SortBy { get; set; }
    public bool SortDescending { get; set; } = false;
    public Dictionary<string, string> FilterParams { get; set; } = new(StringComparer.OrdinalIgnoreCase);
}

public record OrganisationListDto(
    int Id,
    string SdlNumber,
    string CompanyName,
    string? TradingName,
    string? RegistrationNumber,
    string? TaxNumber,
    string? ChamberCode,
    string? SectorCode,
    bool IsActive,
    string? PrimaryContactName,
    string? PrimaryContactEmail
);

public record PersonListDto(
    int Id,
    string? Title,
    string FirstName,
    string LastName,
    string? MiddleName,
    string? RsaIdNumber,
    string? PassportNumber,
    string? Gender,
    DateTime? DateOfBirth,
    string? EmailAddress,
    string? CellPhoneNumber,
    string? ProvinceCode,
    bool IsActive
);

public record LearnerListDto(
    int Id,
    string LearnerContractNumber,
    string LearnerFullName,
    string? LearnerRsaId,
    string EmployerName,
    string? EmployerSdl,
    string QualificationTitle,
    string? LearningProgrammeTypeCode,
    int? NqfLevel,
    DateTime RegistrationDate,
    string? EnrolmentStatusCode
);
