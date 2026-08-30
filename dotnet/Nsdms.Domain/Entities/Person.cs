using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

public class Person : BaseEntity
{
    public string? Title { get; set; }
    public string FirstName { get; set; } = string.Empty;
    public string? MiddleName { get; set; }
    public string LastName { get; set; } = string.Empty;
    public string RsaIdNumber { get; set; } = string.Empty;
    public string? PassportNumber { get; set; }
    public DateTime? DateOfBirth { get; set; }
    public string? Gender { get; set; }
    public string? GenderCode { get; set; }
    public bool? IsSouthAfricanCitizen { get; set; }
    public string? CitizenStatusCode { get; set; }
    public string? EquityCode { get; set; }
    public string? DisabilityCode { get; set; }
    public string? NationalityCode { get; set; }
    public string? HomeLanguageCode { get; set; }
    public string? ProvinceCode { get; set; }
    public string Email { get; set; } = string.Empty;
    public string PhoneNumber { get; set; } = string.Empty;
    public string? CellNumber { get; set; }
    public string? PhysicalAddress { get; set; }
    public string? PostalAddress { get; set; }
    public bool IsActive { get; set; } = true;

    public string FullName => $"{FirstName} {LastName}".Trim();
}
