using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

public class Organisation : BaseEntity
{
    public string SdlNumber { get; set; } = string.Empty; // SARS SDL Levy Number
    public string CompanyName { get; set; } = string.Empty;
    public string? TradingName { get; set; }
    public string? RegistrationNumber { get; set; }
    public string? TaxNumber { get; set; }
    
    public string? CategoryCode { get; set; }
    public string? StatusCode { get; set; }
    public string? ProvinceCode { get; set; }
    public string? SectorCode { get; set; }
    public string? ChamberCode { get; set; }
    public string? SicCode { get; set; }
    public string? CompanySizeCode { get; set; }
    public string? OrganisationTypeCode { get; set; }

    public string? BankName { get; set; }
    public string? BankBranchCode { get; set; }
    public string? BankAccountNumber { get; set; }
    public string? BankAccountType { get; set; }
    public bool BankingDetailsVerified { get; set; } = false;

    public int? PrimaryContactPersonId { get; set; }
    public Person? PrimaryContactPerson { get; set; }

    public bool IsActive { get; set; } = true;
    
    // Navigation Collections
    public ICollection<OrganisationContact> Contacts { get; set; } = new List<OrganisationContact>();
    public ICollection<OrganisationSite> Sites { get; set; } = new List<OrganisationSite>();
    public ICollection<Visit> Visits { get; set; } = new List<Visit>();
    public ICollection<WspSubmission> WspSubmissions { get; set; } = new List<WspSubmission>();
    public ICollection<GrantApplication> GrantApplications { get; set; } = new List<GrantApplication>();
    public ICollection<TrainingProvider> TrainingProviders { get; set; } = new List<TrainingProvider>();
}
