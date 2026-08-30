using System.ComponentModel.DataAnnotations.Schema;
using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Qualifications Curriculum Development (QCD) Application for QCTO Occupational Qualifications and Realignment.
/// </summary>
public class QualificationsCurriculumDevelopment : BaseEntity
{
    public string ApplicationNumber { get; set; } = string.Empty;
    public string QualificationTitle { get; set; } = string.Empty;
    public string? SaqaQualificationId { get; set; }
    public string? OfoCode { get; set; }
    public int NqfLevel { get; set; } = 4;
    public int TotalCreditsRequired { get; set; } = 120;

    /// <summary>
    /// Development type: NewDevelopment, ReAlignment, CurriculumReview
    /// </summary>
    public string DevelopmentTypeCode { get; set; } = "NewDevelopment";

    // Strategic Policy Alignment Checklists
    public bool NationalDevelopmentPlanChecked { get; set; } = true;
    public string? NationalDevelopmentPlanEvidence { get; set; }
    public bool NewGrowthPlanChecked { get; set; } = true;
    public string? NewGrowthPlanEvidence { get; set; }
    public bool IndustrialPolicyActionPlanChecked { get; set; } = true;
    public string? IndustrialPolicyActionPlanEvidence { get; set; }
    public bool StrategicInfrastructureChecked { get; set; } = false;

    // Occupational Scope & Demand Details
    public string? PurposeOfQualification { get; set; }
    public string? TargetLearnerAudience { get; set; }
    public string? IndustryDemandJustification { get; set; }

    // Partner Roles
    public string DevelopmentQualityPartner { get; set; } = "merSETA DQP";
    public string AssessmentQualityPartner { get; set; } = "merSETA AQP";

    public int? OrganisationId { get; set; }
    public Organisation? Organisation { get; set; }

    public DateTime? WorkingGroupConvenedDate { get; set; }
    public DateTime? PublicCommentClosingDate { get; set; }
    public DateTime? SaqaSubmissionDate { get; set; }
    public string? SaqaRegistrationNumber { get; set; }
    public DateTime? SaqaRegistrationDate { get; set; }

    /// <summary>
    /// Status: Draft, WorkingGroupConvened, PublicCommentOpen, SubmittedToQcto, ApprovedBySaqa, Rejected
    /// </summary>
    public string StatusCode { get; set; } = "Draft";

    [NotMapped]
    public string Status { get => StatusCode; set => StatusCode = value; }

    // Collections
    public ICollection<CurriculumWorkingGroupMember> WorkingGroupMembers { get; set; } = new List<CurriculumWorkingGroupMember>();
    public ICollection<SkillsRegistration> SkillsRegistrations { get; set; } = new List<SkillsRegistration>();
}

/// <summary>
/// Expert stakeholder member participating in the QCTO Qualification Development Working Group.
/// </summary>
public class CurriculumWorkingGroupMember : BaseEntity
{
    public int QualificationsCurriculumDevelopmentId { get; set; }
    public QualificationsCurriculumDevelopment? QualificationsCurriculumDevelopment { get; set; }

    public int? PersonId { get; set; }
    public Person? Person { get; set; }

    public string MemberName { get; set; } = string.Empty;
    public string StakeholderRoleTitle { get; set; } = "IndustryExpert"; // LeadQDF, IndustryExpert, TradeUnionRep, AcademicSpecialist
    public string OrganisationRepresented { get; set; } = string.Empty;
    public string EmailAddress { get; set; } = string.Empty;
    public string PhoneNumber { get; set; } = string.Empty;
    public bool IsConfirmedAttendee { get; set; } = true;
}

/// <summary>
/// Skills Programme / Part-Qualification Curriculum Registration,
/// capturing all statutory fields required for SETMIS File 304 (Non NQF Intervention) reporting.
/// </summary>
public class SkillsRegistration : BaseEntity
{
    public int QualificationsCurriculumDevelopmentId { get; set; }
    public QualificationsCurriculumDevelopment? QualificationsCurriculumDevelopment { get; set; }

    /// <summary>
    /// Statutory Non-NQF Intervention Code (SETMIS File 304).
    /// </summary>
    public string NonNqfIntervCode { get; set; } = string.Empty;

    /// <summary>
    /// Statutory Non-NQF Intervention descriptive title.
    /// </summary>
    public string NonNqfIntervName { get; set; } = string.Empty;

    [NotMapped]
    public string SkillsProgrammeCode { get => NonNqfIntervCode; set => NonNqfIntervCode = value; }

    [NotMapped]
    public string SkillsProgrammeTitle { get => NonNqfIntervName; set => NonNqfIntervName = value; }

    /// <summary>
    /// SAQA NQF Subfield ID (references lookup.SubfieldType).
    /// </summary>
    public string SubfieldId { get; set; } = "06";

    /// <summary>
    /// Submitting ETQA ID (references lookup.SetaType, default 17 for merSETA).
    /// </summary>
    public string EtqaId { get; set; } = "17";

    /// <summary>
    /// Statutory Registration Status (references lookup.NonNqfInterventionStatusType: 01 Registered, 02 Approved, 03 Concluded).
    /// </summary>
    public string NonNqfIntervStatusId { get; set; } = "01";

    /// <summary>
    /// Statutory Learning Programme Type code (references lookup.LearningProgrammeType, default 03 Skills Programme).
    /// </summary>
    public string LearningProgrammeTypeId { get; set; } = "03";

    /// <summary>
    /// Registration validity start date (SETMIS File 304).
    /// </summary>
    public DateTime RegistrationStartDate { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Registration validity expiration date.
    /// </summary>
    public DateTime? RegistrationEndDate { get; set; }

    public int Credits { get; set; } = 30;
    public int NqfLevel { get; set; } = 3;
    public string UnitStandardsIncludedJson { get; set; } = "[]";
}

/// <summary>
/// Non-SETA External Employer / Organisation registered with other Quality Councils / SETAs.
/// </summary>
public class NonSetaCompany : BaseEntity
{
    public string CompanyName { get; set; } = string.Empty;
    public string? SdlNumber { get; set; }
    public string PrimarySetaCode { get; set; } = "W&RSETA"; // W&RSETA, CHIETA, TETA, SERVICES_SETA, MICT_SETA, QCTO
    public string? CompanyRegistrationNumber { get; set; }
    public string Email { get; set; } = string.Empty;
    public string PhoneNumber { get; set; } = string.Empty;
    public string? PhysicalAddress { get; set; }
    public bool IsActive { get; set; } = true;
}

/// <summary>
/// Cross-SETA Qualification &amp; TVET College Achievement Verification for merSETA articulation.
/// </summary>
public class NonSetaQualificationsCompletion : BaseEntity
{
    public int? CompanyLearnerId { get; set; }
    public CompanyLearner? CompanyLearner { get; set; }

    public int PersonId { get; set; }
    public Person? Person { get; set; }

    public int? NonSetaCompanyId { get; set; }
    public NonSetaCompany? NonSetaCompany { get; set; }

    public string OriginatingSetaCode { get; set; } = "CHIETA";
    public string QualificationTitle { get; set; } = string.Empty;
    public string? SaqaQualificationId { get; set; }
    public int NqfLevel { get; set; } = 4;
    public int TotalCreditsAchieved { get; set; } = 120;
    public DateTime AchievementDate { get; set; } = DateTime.UtcNow;
    public string ExternalCertificateNumber { get; set; } = string.Empty;

    public string VerificationStatusCode { get; set; } = "PendingVerification"; // PendingVerification, CreditsArticulated, EndorsedByMerSeta, Rejected
    public DateTime? EndorsementDate { get; set; }
    public string? EndorsedByUserId { get; set; }
    public string? EndorsementNotes { get; set; }

    [NotMapped]
    public string Status { get => VerificationStatusCode; set => VerificationStatusCode = value; }
}
