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
    /// Statutory accreditation intake stream: PrimaryAccreditation, ProgrammeApproval, QctoSkillsDevelopmentProvider, QctoTradeTestCentre, NonMerSetaScope
    /// (Signed SDP Application Use Case Table 7, 8, 9 & Table 24).
    /// </summary>
    public string AccreditationStream { get; set; } = "PrimaryAccreditation";

    /// <summary>
    /// Primary ETQA / SETA name for Programme Approval stream (e.g. CHIETA, Services SETA, QCTO).
    /// </summary>
    public string? PrimaryEtqaName { get; set; }

    /// <summary>
    /// Accreditation certificate number awarded by the Primary ETQA (Table 8 / Table 24 Col 2).
    /// </summary>
    public string? PrimaryAccreditationNumber { get; set; }

    /// <summary>
    /// Primary ETQA accreditation validity cycle start date.
    /// </summary>
    public DateTime? PrimaryAccreditationStartDate { get; set; }

    /// <summary>
    /// Primary ETQA accreditation validity cycle end date.
    /// </summary>
    public DateTime? PrimaryAccreditationEndDate { get; set; }

    /// <summary>
    /// Official NAMB Trade Test Centre registration number (Table 9 / Table 24 Col 4).
    /// </summary>
    public string? NambRegistrationNumber { get; set; }

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public string? PrimaryEtqaAccreditationNumber { get => PrimaryAccreditationNumber; set => PrimaryAccreditationNumber = value; }

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public string? NambTtcRegistrationNumber { get => NambRegistrationNumber; set => NambRegistrationNumber = value; }

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public DateTime? NambTtcExpiryDate { get => NambRegistrationEndDate; set => NambRegistrationEndDate = value; }

    /// <summary>
    /// NAMB Trade Test Centre accreditation start date.
    /// </summary>
    public DateTime? NambRegistrationStartDate { get; set; }

    /// <summary>
    /// NAMB Trade Test Centre accreditation end date.
    /// </summary>
    public DateTime? NambRegistrationEndDate { get; set; }

    /// <summary>
    /// MerSETA ETQA Review Committee decision minute reference number (Table 7 Step N, Table 15 Attribute 19).
    /// </summary>
    public string? EtqaCommitteeDecisionNumber { get; set; }

    /// <summary>
    /// Date when the ETQA Review Committee ratified the accreditation decision.
    /// </summary>
    public DateTime? EtqaCommitteeMeetingDate { get; set; }

    /// <summary>
    /// Indicates whether a re-accreditation application is currently underway.
    /// Statutory Invariant (BR4): Operational status must NOT revert to Pending Approval during renewal.
    /// </summary>
    public bool ReAccreditationUnderway { get; set; } = false;

    /// <summary>
    /// Timestamp when re-accreditation application was initiated.
    /// </summary>
    public DateTime? ReAccreditationEffectiveDate { get; set; }

    /// <summary>
    /// 5-Working-Day SLA Inspection Due Date for QA initial contact and scheduling (Table 14 BR3).
    /// </summary>
    public DateTime? InspectionDueDate { get; set; }

    /// <summary>
    /// SHA-256 cryptographic Digital Security Seal stamped on official ETQA Accreditation Certificate.
    /// </summary>
    public string? DigitalSecuritySeal { get; set; }

    /// <summary>
    /// Full SAQA qualifications accredited for delivery by this provider.
    /// </summary>
    public ICollection<TrainingProviderQualification> Qualifications { get; set; } = new List<TrainingProviderQualification>();

    /// <summary>
    /// Standalone SAQA unit standards accredited for delivery by this provider.
    /// </summary>
    public ICollection<TrainingProviderUnitStandard> UnitStandards { get; set; } = new List<TrainingProviderUnitStandard>();

    /// <summary>
    /// Physical delivery sites and satellite training locations.
    /// </summary>
    public ICollection<TrainingProviderCampus> Campuses { get; set; } = new List<TrainingProviderCampus>();

    /// <summary>
    /// Statutory alias for Delivery Sites.
    /// </summary>
    [NotMapped]
    public ICollection<TrainingProviderCampus> DeliverySites { get => Campuses; set => Campuses = value; }

    /// <summary>
    /// Registered assessors and moderators linked to this provider.
    /// </summary>
    public ICollection<TrainingProviderAssessorLink> AssessorLinks { get; set; } = new List<TrainingProviderAssessorLink>();

    /// <summary>
    /// Two-stage QMS Self-Evaluation audit checklist records.
    /// </summary>
    public ICollection<TrainingProviderSelfEvaluation> SelfEvaluations { get; set; } = new List<TrainingProviderSelfEvaluation>();

    /// <summary>
    /// Verified multi-contact quorum records including banking confirmation authorization.
    /// </summary>
    public ICollection<TrainingProviderContact> Contacts { get; set; } = new List<TrainingProviderContact>();

    /// <summary>
    /// Disciplinary, suspension, and compliance sanction records.
    /// </summary>
    public ICollection<SdpDisciplinaryCase> DisciplinaryCases { get; set; } = new List<SdpDisciplinaryCase>();

    /// <summary>
    /// Form ETQ-TP-012 physical site inspection and tool ratio audit records.
    /// </summary>
    public ICollection<SdpSiteInspection> SiteInspections { get; set; } = new List<SdpSiteInspection>();

    /// <summary>
    /// Multi-cycle 5-year re-accreditation historical application records.
    /// </summary>
    public ICollection<SdpReAccreditationApplication> ReAccreditationApplications { get; set; } = new List<SdpReAccreditationApplication>();
}
