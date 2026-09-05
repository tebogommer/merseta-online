using System.ComponentModel.DataAnnotations.Schema;
using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Registered employer, host workplace, or skills development entity under MerSETA jurisdiction,
/// fully normalized with statutory columns required for SETMIS File 100 and File 200 reporting.
/// </summary>
public class Organisation : BaseEntity
{
    /// <summary>
    /// SARS Skills Development Levy registration number (e.g. L123456789).
    /// </summary>
    public string SdlNumber { get; set; } = string.Empty;

    /// <summary>
    /// Parent or holding company Main SDL number for enterprise site networks (SETMIS File 200).
    /// </summary>
    public string? MainSdlNumber { get; set; }

    /// <summary>
    /// Originating SETA classification code (references lookup.SetaType, default 17 for merSETA).
    /// </summary>
    public string SetaId { get; set; } = "17";

    /// <summary>
    /// Registered legal corporate name of the enterprise.
    /// </summary>
    public string CompanyName { get; set; } = string.Empty;

    /// <summary>
    /// Trading name if operating under a different commercial title (T/A).
    /// </summary>
    public string? TradingName { get; set; }

    /// <summary>
    /// CIPC Company or Close Corporation registration number (e.g. 2015/123456/07).
    /// </summary>
    public string? RegistrationNumber { get; set; }

    /// <summary>
    /// SARS Income Tax reference number.
    /// </summary>
    public string? TaxNumber { get; set; }
    
    /// <summary>
    /// Organisation SARS levy compliance category code (e.g. LEVY_PAYING, NON_LEVY_PAYING, EXEMPT).
    /// </summary>
    public string? LevyCategoryCode { get; set; }

    /// <summary>
    /// Current operational registration status code (references lookup.StatusType: ACTIVE, INACTIVE, SUSPENDED).
    /// </summary>
    public string? OrganisationStatusCode { get; set; } = "ACTIVE";

    /// <summary>
    /// Indicates whether this entity is a non-employer delivery partner (TVET, CET, HEI, NGO, CBO, Public Entity).
    /// </summary>
    public bool IsNonEmployerEntity { get; set; } = false;

    /// <summary>
    /// Non-employer delivery partner classification (e.g. TVET, CET, HEI, NGO, NPO, CBO, PublicEntity, GovtDept, EmployerAssoc, OrganisedLabour).
    /// </summary>
    public string? NonEmployerEntityType { get; set; }

    /// <summary>
    /// External SETA classification for entities paying levies to other SETAs (e.g. 10 for ETDPSETA, 23 for Services SETA).
    /// </summary>
    public string? ExternalSetaId { get; set; }

    [NotMapped]
    public string? StatusCode { get => OrganisationStatusCode; set => OrganisationStatusCode = value; }

    [NotMapped]
    public string LegalName { get => CompanyName; set => CompanyName = value; }

    [NotMapped]
    public string LevyNumber { get => SdlNumber; set => SdlNumber = value; }

    [NotMapped]
    public string? CategoryCode { get => LevyCategoryCode; set => LevyCategoryCode = value; }

    /// <summary>
    /// Head office geographic province lookup code (references lookup.ProvinceType: GP, KZN, WC, EC, FS, MP, NW, NC, LP).
    /// </summary>
    public string? ProvinceCode { get; set; }

    /// <summary>
    /// Sovereign country lookup code (references lookup.CountryType, default ZA).
    /// </summary>
    public string CountryCode { get; set; } = "ZA";

    /// <summary>
    /// SETA industrial sector classification code (references lookup.SectorType).
    /// </summary>
    public string? SectorCode { get; set; }

    /// <summary>
    /// MerSETA chamber allocation code (references lookup.ChamberType: AUTO, METAL, PLASTICS, MOTOR, NEW_TYRE, OTHER).
    /// </summary>
    public string? ChamberCode { get; set; }

    /// <summary>
    /// Indicates whether this organisation lacks a valid merSETA Chamber or GP Vendor Class mapping,
    /// blocking downstream Discretionary Grant submissions, WSP submissions, MoAs, and ERP payment batches.
    /// </summary>
    public bool HasMissingChamberMapping { get; set; } = false;

    /// <summary>
    /// Resolved Microsoft Dynamics GP Vendor Class code (AUTO, METAL, MOTOR, NEW TYRE, PLASTICS, SETA).
    /// </summary>
    public string? GpVendorClass { get; set; }

    /// <summary>
    /// Standard Industrial Classification (SIC) 5-digit economic activity code (references lookup.SicCodeType).
    /// </summary>
    public string? SicCode { get; set; }

    /// <summary>
    /// Indicates whether the organisation's chamber assignment was manually overridden instead of auto-derived from the SIC code.
    /// </summary>
    public bool IsManualChamberOverride { get; set; } = false;

    /// <summary>
    /// Governance justification and Board/SSP reference for the manual chamber assignment override.
    /// </summary>
    public string? ChamberOverrideReason { get; set; }

    /// <summary>
    /// Date when the chamber override was approved and recorded.
    /// </summary>
    public DateTime? ChamberOverrideDate { get; set; }

    /// <summary>
    /// Executive or administrator username who authorized the chamber override.
    /// </summary>
    public string? ChamberOverrideApprovedBy { get; set; }

    /// <summary>
    /// Enterprise size classification code (references lookup.CompanySizeType: MICRO, SMALL, MEDIUM, LARGE).
    /// </summary>
    public string? CompanySizeCode { get; set; }

    /// <summary>
    /// Legal organisation constitution type code (references lookup.OrganisationType: PTY_LTD, CC, PUBLIC_ENTITY, NGO_NPO).
    /// </summary>
    public string? OrganisationTypeCode { get; set; }

    #region Contact & Communication Details
    /// <summary>
    /// Primary telephone switchboard number.
    /// </summary>
    public string? PhoneNumber { get; set; }

    [NotMapped]
    public string? Phone { get => PhoneNumber; set => PhoneNumber = value; }

    /// <summary>
    /// Facsimile transmission number.
    /// </summary>
    public string? FaxNumber { get; set; }

    /// <summary>
    /// Corporate website URL.
    /// </summary>
    public string? WebsiteUrl { get; set; }

    /// <summary>
    /// Primary physical street address line 1.
    /// </summary>
    public string? PhysicalAddress { get; set; }

    /// <summary>
    /// Physical address postal delivery code.
    /// </summary>
    public string? PhysicalAddressPostalCode { get; set; }

    /// <summary>
    /// Postal delivery address.
    /// </summary>
    public string? PostalAddress { get; set; }

    /// <summary>
    /// Postal delivery code.
    /// </summary>
    public string? PostalAddressPostalCode { get; set; }
    #endregion

    #region Banking & Grant Rebates
    /// <summary>
    /// Commercial bank name for grant disbursement rebates.
    /// </summary>
    public string? BankName { get; set; }

    /// <summary>
    /// Universal bank branch clearance code.
    /// </summary>
    public string? BankBranchCode { get; set; }

    /// <summary>
    /// Bank account number for EFT transfers.
    /// </summary>
    public string? BankAccountNumber { get; set; }

    /// <summary>
    /// Bank account type (e.g. CHEQUE, CURRENT, SAVINGS).
    /// </summary>
    public string? BankAccountType { get; set; }

    /// <summary>
    /// Indicates whether banking details and proof of banking have been verified by MerSETA finance.
    /// </summary>
    public bool BankingDetailsVerified { get; set; } = false;
    #endregion

    /// <summary>
    /// Foreign key referencing the primary contact person.
    /// </summary>
    public int? PrimaryContactPersonId { get; set; }

    /// <summary>
    /// Navigational reference to the primary contact person.
    /// </summary>
    public Person? PrimaryContactPerson { get; set; }

    /// <summary>
    /// Foreign key referencing the organisation high-DPI brand logo in DocumentMetadata vault.
    /// </summary>
    public int? LogoDocumentId { get; set; }

    /// <summary>
    /// Primary corporate brand color hex code (e.g. #865300).
    /// </summary>
    public string? BrandColorHex { get; set; }

    /// <summary>
    /// Indicates whether the organisation is currently active.
    /// </summary>
    public bool IsActive { get; set; } = true;

    /// <summary>
    /// Explicit mentor ratio enforcement override for this organisation (null = inherit Global, true = enforce, false = exempt).
    /// </summary>
    public bool? IsMentorRatioEnforced { get; set; }

    /// <summary>
    /// Statutory or executive justification when the organisation is granted an exemption from mentor ratios (e.g. State-Owned Enterprise Training Academy).
    /// </summary>
    public string? MentorRatioExemptionReason { get; set; }

    /// <summary>
    /// Optional enterprise-wide uniform mentor capacity cap override (e.g. 5 learners per mentor across all site workshops).
    /// </summary>
    public int? CustomMentorRatioCap { get; set; }
    
    /// <summary>
    /// Registered contact persons and Skills Development Facilitators (SDFs).
    /// </summary>
    public ICollection<OrganisationContact> Contacts { get; set; } = new List<OrganisationContact>();

    /// <summary>
    /// Secondary branch sites and operational premises.
    /// </summary>
    public ICollection<OrganisationSite> Sites { get; set; } = new List<OrganisationSite>();

    /// <summary>
    /// On-site monitoring, verification, and employer visit activities.
    /// </summary>
    public ICollection<Visit> Visits { get; set; } = new List<Visit>();

    /// <summary>
    /// Workplace Skills Plans (WSP) and Annual Training Reports (ATR).
    /// </summary>
    public ICollection<WspSubmission> WspSubmissions { get; set; } = new List<WspSubmission>();

    /// <summary>
    /// Discretionary grant funding applications submitted by this employer.
    /// </summary>
    public ICollection<GrantApplication> GrantApplications { get; set; } = new List<GrantApplication>();

    /// <summary>
    /// Training provider accreditations associated with this organisation.
    /// </summary>
    public ICollection<TrainingProvider> TrainingProviders { get; set; } = new List<TrainingProvider>();
}
