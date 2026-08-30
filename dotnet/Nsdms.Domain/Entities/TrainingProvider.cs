using System.ComponentModel.DataAnnotations.Schema;
using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Accredited Skills Development Provider (SDP) offering registered occupational qualifications and skills programmes,
/// capturing all statutory fields required for SETMIS File 100 reporting.
/// </summary>
public class TrainingProvider : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the parent legal Organisation.
    /// </summary>
    public int OrganisationId { get; set; }

    /// <summary>
    /// Navigational reference to the parent Organisation.
    /// </summary>
    public Organisation? Organisation { get; set; }

    /// <summary>
    /// Statutory Provider Code as recorded on the National Learners' Records Database (NLRD) and SETMIS (SETMIS File 100).
    /// </summary>
    public string? ProviderCode { get; set; } = string.Empty;

    /// <summary>
    /// Submitting ETQA / SETA Identifier (references lookup.SetaType, default 17 for merSETA).
    /// </summary>
    public string? EtqaId { get; set; } = "17";

    /// <summary>
    /// Official ETQA accreditation certificate number.
    /// </summary>
    public string? AccreditationNumber { get; set; } = string.Empty;

    /// <summary>
    /// Start date of current ETQA accreditation cycle.
    /// </summary>
    public DateTime? AccreditationStartDate { get; set; }

    /// <summary>
    /// Expiration date of current ETQA accreditation cycle.
    /// </summary>
    public DateTime? AccreditationEndDate { get; set; }

    /// <summary>
    /// Provider institutional class code (references lookup.ProviderClassType: 01 Public TVET, 02 Private, 03 University, 04 NGO/CBO).
    /// </summary>
    public string? ProviderClassId { get; set; } = "02";

    /// <summary>
    /// Provider functional operational type code (references lookup.ProviderType: 01 Education, 02 Training, 03 Employer Provider).
    /// </summary>
    public string? ProviderTypeId { get; set; } = "02";

    /// <summary>
    /// Current accreditation standing code (references lookup.ProviderStatusType: 01 Accredited, 02 Provisional, 03 De-accredited).
    /// </summary>
    public string? ProviderStatusId { get; set; } = "01";

    /// <summary>
    /// Legacy Provider Type Code mapping.
    /// </summary>
    public string? ProviderTypeCode { get; set; }

    /// <summary>
    /// Legacy Provider Status Code mapping.
    /// </summary>
    public string? ProviderStatusCode { get; set; }

    [NotMapped]
    public string? StatusCode { get => ProviderStatusCode; set => ProviderStatusCode = value; }

    [NotMapped]
    public string LegalName { get => Organisation?.CompanyName ?? string.Empty; set { if (Organisation != null) Organisation.CompanyName = value; } }

    [NotMapped]
    public string ProviderName { get => Organisation?.CompanyName ?? string.Empty; set { if (Organisation != null) Organisation.CompanyName = value; } }

    /// <summary>
    /// MerSETA ETQA committee decision minute reference number (SETMIS File 100).
    /// </summary>
    public string? EtqaDecisionNumber { get; set; }

    /// <summary>
    /// SARS Income Tax reference number (SETMIS File 100).
    /// </summary>
    public string? SarsNumber { get; set; }

    /// <summary>
    /// Facsimile transmission contact number.
    /// </summary>
    public string? FaxNumber { get; set; }

    /// <summary>
    /// Corporate website URL.
    /// </summary>
    public string? WebsiteUrl { get; set; }

    /// <summary>
    /// Maximum concurrent learner enrolment capacity authorized for facilities.
    /// </summary>
    public int? MaxLearnerCapacity { get; set; }

    /// <summary>
    /// Optional foreign key referencing the SDP principal or training director.
    /// </summary>
    public int? PrimaryContactPersonId { get; set; }

    /// <summary>
    /// Navigational reference to the principal contact Person.
    /// </summary>
    public Person? PrimaryContactPerson { get; set; }

    /// <summary>
    /// Indicates whether the provider is active for new learner enrolments.
    /// </summary>
    public bool IsActive { get; set; } = true;

    /// <summary>
    /// Foreign key referencing the high-resolution brand logo DocumentMetadata.
    /// </summary>
    public int? LogoDocumentId { get; set; }

    /// <summary>
    /// Primary brand color in HEX format (e.g. #865300).
    /// </summary>
    public string? BrandColorHex { get; set; }

    /// <summary>
    /// Full SAQA qualifications accredited for delivery by this provider.
    /// </summary>
    public ICollection<TrainingProviderQualification> Qualifications { get; set; } = new List<TrainingProviderQualification>();

    /// <summary>
    /// Standalone SAQA unit standards accredited for delivery by this provider.
    /// </summary>
    public ICollection<TrainingProviderUnitStandard> UnitStandards { get; set; } = new List<TrainingProviderUnitStandard>();
}
