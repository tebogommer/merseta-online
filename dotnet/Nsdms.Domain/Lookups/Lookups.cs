using Nsdms.Domain.Common;
using System.ComponentModel.DataAnnotations.Schema;

namespace Nsdms.Domain.Lookups;

// =========================================================================================
// SETMIS & Core Demographics Lookups
// =========================================================================================

/// <summary>
/// SETMIS statutory alternate identification types (Field: Alternate_Id_Type_Id - e.g. Passport, Birth Certificate, Work Permit).
/// </summary>
[Table("AlternateIdType", Schema = "lookup")]
public class AlternateIdType : BaseLookupType { }

/// <summary>
/// SETMIS South African citizenship and residency legal status classifications (Field: Citizen_Resident_Status_Code - SA, PR, D, O, U).
/// </summary>
[Table("CitizenStatusType", Schema = "lookup")]
public class CitizenStatusType : BaseLookupType { }

/// <summary>
/// Country classification reference codes (Field: Country_Code - ISO-3166 2-Letter Alpha codes covering 249 global jurisdictions).
/// </summary>
[Table("CountryType", Schema = "lookup")]
public class CountryType : BaseLookupType { }

/// <summary>
/// SETMIS regional nationality classifications (Field: Nationality_Code - 23 statutory nationality codes).
/// </summary>
[Table("NationalityType", Schema = "lookup")]
public class NationalityType : BaseLookupType { }

/// <summary>
/// Official South African home language classifications (Field: Home_Language_Code - 14 statutory codes including SASL).
/// </summary>
[Table("HomeLanguageType", Schema = "lookup")]
public class HomeLanguageType : BaseLookupType { }

/// <summary>
/// South African provincial geopolitical regions (Field: Province_Code - 11 statutory provincial and national codes).
/// </summary>
[Table("ProvinceType", Schema = "lookup")]
public class ProvinceType : BaseLookupType { }

/// <summary>
/// Statutory Employment Equity / BBBEE racial demographic classifications (Field: Equity_Code - BA, BC, BI, Wh, U).
/// </summary>
[Table("EquityType", Schema = "lookup")]
public class EquityType : BaseLookupType { }

/// <summary>
/// Gender classification reference lookups (Field: Gender_Code - F, M, U).
/// </summary>
[Table("GenderType", Schema = "lookup")]
public class GenderType : BaseLookupType { }

/// <summary>
/// Disability impairment classifications per Employment Equity &amp; SETMIS standards.
/// </summary>
[Table("DisabilityType", Schema = "lookup")]
public class DisabilityType : BaseLookupType { }

/// <summary>
/// Learner / employee economic employment status classifications (Field: Economic_Status_Id - Employed, Unemployed, etc.).
/// </summary>
[Table("EconomicStatusType", Schema = "lookup")]
public class EconomicStatusType : BaseLookupType { }

/// <summary>
/// Protection of Personal Information Act compliance consent status (Field: POPI_Act_Status_ID - Agree, Disagree, N/A).
/// </summary>
[Table("PopiActStatusType", Schema = "lookup")]
public class PopiActStatusType : BaseLookupType { }

// =========================================================================================
// SETMIS Washington Group Functioning Disability Rating Lookups
// =========================================================================================

/// <summary>
/// Washington Group Communication functional rating (Field: Communicating_Rating_Id - 1: No difficulty to 6: Cannot yet be determined).
/// </summary>
[Table("CommunicatingRatingType", Schema = "lookup")]
public class CommunicatingRatingType : BaseLookupType { }

/// <summary>
/// Washington Group Hearing functional rating (Field: Hearing_Rating_Id - 1: No difficulty to 6: Cannot yet be determined).
/// </summary>
[Table("HearingRatingType", Schema = "lookup")]
public class HearingRatingType : BaseLookupType { }

/// <summary>
/// Washington Group Memory and Cognitive functional rating (Field: Remembering_Rating_Id - 1: No difficulty to 6: Cannot yet be determined).
/// </summary>
[Table("RememberingRatingType", Schema = "lookup")]
public class RememberingRatingType : BaseLookupType { }

/// <summary>
/// Washington Group Vision functional rating (Field: Seeing_Rating_Id - 1: No difficulty to 6: Cannot yet be determined).
/// </summary>
[Table("SeeingRatingType", Schema = "lookup")]
public class SeeingRatingType : BaseLookupType { }

/// <summary>
/// Washington Group Self-Care functional rating (Field: Self_Care_Rating_Id - 1: No difficulty to 6: Cannot yet be determined).
/// </summary>
[Table("SelfCareRatingType", Schema = "lookup")]
public class SelfCareRatingType : BaseLookupType { }

/// <summary>
/// Washington Group Mobility and Walking functional rating (Field: Walking_Rating_Id - 1: No difficulty to 6: Cannot yet be determined).
/// </summary>
[Table("WalkingRatingType", Schema = "lookup")]
public class WalkingRatingType : BaseLookupType { }

// =========================================================================================
// SETMIS Learning, ETQA & Assessment Lookups
// =========================================================================================

/// <summary>
/// Assessor and Moderator statutory designation types (Field: Designation_Id - 1: Assessor, 0: Moderator).
/// </summary>
[Table("DesignationType", Schema = "lookup")]
public class DesignationType : BaseLookupType { }

/// <summary>
/// Assessor and Moderator ETQA registration structure status codes (Field: Designation_Structure_Status_Id - Registered, Deregistered, etc.).
/// </summary>
[Table("DesignationStructureStatusType", Schema = "lookup")]
public class DesignationStructureStatusType : BaseLookupType { }

/// <summary>
/// Learning intervention modality types (Field: Learning_Programme_Type_Id - Learnership, Apprenticeship, Skills Programme, Internship, Bursary, etc.).
/// </summary>
[Table("LearningProgrammeType", Schema = "lookup")]
public class LearningProgrammeType : BaseLookupType { }

/// <summary>
/// Learner enrolment intake and delivery modality types (Field: Enrolment_Type_Id - Contact, Distance, Mixed Mode, etc.).
/// </summary>
[Table("EnrolmentType", Schema = "lookup")]
public class EnrolmentType : BaseLookupType { }

/// <summary>
/// Learner agreement registration lifecycle status codes (Field: Enrolment_Status_Id - Enrolled, Achieved, Certificated, etc.).
/// </summary>
[Table("EnrolmentStatusType", Schema = "lookup")]
public class EnrolmentStatusType : BaseLookupType { }

/// <summary>
/// Reasons for learner enrolment status transitions (Field: Enrolment_Status_Reason_Id - Medical, Financial, Social, etc.).
/// </summary>
[Table("EnrolmentStatusReasonType", Schema = "lookup")]
public class EnrolmentStatusReasonType : BaseLookupType { }

/// <summary>
/// Work Integrated Learning (WIL) and Internship lifecycle status (Field: Internship_Status_Id - Active, Completed, Discontinued).
/// </summary>
[Table("InternshipStatusType", Schema = "lookup")]
public class InternshipStatusType : BaseLookupType { }

/// <summary>
/// Non-NQF skills intervention registration status codes (Field: Non_NQF_Interv_Status_Id - Active, Inactive, Provisional).
/// </summary>
[Table("NonNqfInterventionStatusType", Schema = "lookup")]
public class NonNqfInterventionStatusType : BaseLookupType { }

/// <summary>
/// Programme articulation hierarchy standing (Field: Part_Of_Id - Stand-alone, Part of Qualification, Part of Learnership, etc.).
/// </summary>
[Table("PartOfType", Schema = "lookup")]
public class PartOfType : BaseLookupType { }

/// <summary>
/// Skills Development Provider institutional classification (Field: Provider_Class_Id - Public, Private, NGO, Foreign, etc.).
/// </summary>
[Table("ProviderClassType", Schema = "lookup")]
public class ProviderClassType : BaseLookupType { }

/// <summary>
/// Skills Development Provider functional entity type (Field: Provider_Type_Id - Education, Training, Employer, NGO).
/// </summary>
[Table("ProviderType", Schema = "lookup")]
public class ProviderType : BaseLookupType { }

/// <summary>
/// Skills Development Provider ETQA accreditation standing codes (Field: Provider_Status_Id - Accredited, Registered, Closed, etc.).
/// </summary>
[Table("ProviderStatusType", Schema = "lookup")]
public class ProviderStatusType : BaseLookupType { }

/// <summary>
/// SAQA National Qualifications Framework subfield classifications (Field: Subfield_Id - 68 statutory subfields).
/// </summary>
[Table("SubfieldType", Schema = "lookup")]
public class SubfieldType : BaseLookupType { }

/// <summary>
/// Artisan practical trade test assessment competency outcomes (Field: Trade_Test_Result_Id - Competent, Not yet competent).
/// </summary>
[Table("TradeTestResultType", Schema = "lookup")]
public class TradeTestResultType : BaseLookupType { }

/// <summary>
/// Artisan trade test assessment specific outcome reasons (Field: Trade_Test_Result_Reason_Id).
/// </summary>
[Table("TradeTestResultReasonType", Schema = "lookup")]
public class TradeTestResultReasonType : BaseLookupType { }

/// <summary>
/// Portfolio of Evidence (PoE) digital artifact and evidence document types.
/// </summary>
[Table("LearnerEvidenceType", Schema = "lookup")]
public class LearnerEvidenceType : BaseLookupType { }

// =========================================================================================
// SETMIS Occupations, Industries & Spatial Lookups
// =========================================================================================

/// <summary>
/// DHET Organising Framework for Occupations occupational classification codes (Field: OFO_Code - 1,454 statutory codes).
/// </summary>
[Table("OfoCodeType", Schema = "lookup")]
public class OfoCodeType : BaseLookupType { }

/// <summary>
/// Standard Industrial Classification (SIC) 5-digit economic activity codes (Field: SIC_Code - 815 statutory codes).
/// </summary>
[Table("SicCodeType", Schema = "lookup")]
public class SicCodeType : BaseLookupType
{
    /// <summary>
    /// Associated merSETA Chamber Code (references lookup.ChamberType: AUTO, METAL, MOTOR, NEW_TYRE, PLASTICS, OTHER).
    /// </summary>
    public string? ChamberCode { get; set; }

    /// <summary>
    /// Designated statutory SETA Code (SETA 17 for merSETA, or other SETA code if out-of-scope).
    /// </summary>
    public string SetaCode { get; set; } = "17";
}

/// <summary>
/// Stats SA spatial geographic sub-place and municipal area codes (Field: STATSSA_Area_Code - 22,108 statutory codes).
/// </summary>
[Table("StatssaAreaCodeType", Schema = "lookup")]
public class StatssaAreaCodeType : BaseLookupType { }

/// <summary>
/// Spatial demographic urban versus rural intervention classifications (Field: Urban_Rural_ID - Urban, Rural, Unknown).
/// </summary>
[Table("UrbanRuralType", Schema = "lookup")]
public class UrbanRuralType : BaseLookupType { }

// =========================================================================================
// Organisations, Sector & System Governance Lookups
// =========================================================================================

/// <summary>
/// South African Sector Education and Training Authorities (Field: SETA_Id - 21 SETAs including MerSETA).
/// </summary>
[Table("SetaType", Schema = "lookup")]
public class SetaType : BaseLookupType { }

/// <summary>
/// Employer entity levy compliance categories (e.g. Levy Paying, Non-Levy Paying, Government/Exempt).
/// </summary>
[Table("CategoryType", Schema = "lookup")]
public class CategoryType : BaseLookupType { }

/// <summary>
/// Enterprise legal constitution types (e.g. Pty Ltd, Close Corporation, Public Entity, NGO/NPO).
/// </summary>
[Table("OrganisationType", Schema = "lookup")]
public class OrganisationType : BaseLookupType { }

/// <summary>
/// Enterprise headcount and turnover size bands (Small: 0-49, Medium: 50-149, Large: 150+).
/// </summary>
[Table("CompanySizeType", Schema = "lookup")]
public class CompanySizeType : BaseLookupType { }

/// <summary>
/// MerSETA industrial economic sector classifications (Auto, Metal, Motor Retail, Plastics, Tyre).
/// </summary>
[Table("SectorType", Schema = "lookup")]
public class SectorType : BaseLookupType { }

/// <summary>
/// MerSETA sub-sector chambers (Auto, Metal &amp; Engineering, Plastics, Motor Retail).
/// </summary>
[Table("ChamberType", Schema = "lookup")]
public class ChamberType : BaseLookupType { }

/// <summary>
/// General system and workflow operational status codes (Active, Inactive, Pending, Approved, Rejected, Suspended).
/// </summary>
[Table("StatusType", Schema = "lookup")]
public class StatusType : BaseLookupType { }

// =========================================================================================
// Grants, Funding & Visits Lookups
// =========================================================================================

/// <summary>
/// Learning intervention funding source classifications (Field: Funding_Id - SETA funded, Employer funded, Learner funded, etc.).
/// </summary>
[Table("FundingType", Schema = "lookup")]
public class FundingType : BaseLookupType { }

/// <summary>
/// MerSETA Discretionary and Mandatory grant allocation funding types.
/// </summary>
[Table("GrantTypeType", Schema = "lookup")]
public class GrantTypeType : BaseLookupType { }

/// <summary>
/// Targeted skills development intervention categories.
/// </summary>
[Table("InterventionType", Schema = "lookup")]
public class InterventionType : BaseLookupType { }

/// <summary>
/// Employer on-site inspection and monitoring visit activity types.
/// </summary>
[Table("VisitTypeType", Schema = "lookup")]
public class VisitTypeType : BaseLookupType { }

/// <summary>
/// On-site physical facility inspection approval status codes.
/// </summary>
[Table("SiteVisitApprovalStatusType", Schema = "lookup")]
public class SiteVisitApprovalStatusType : BaseLookupType { }

/// <summary>
/// Employer workplace approval hosting eligibility status codes (Field: Employer_Approval_Status_Id - Active, Inactive, Legacy).
/// </summary>
[Table("EmployerApprovalStatusType", Schema = "lookup")]
public class EmployerApprovalStatusType : BaseLookupType { }

// =========================================================================================
// NLRD Academic & Qualification Classification Lookups
// =========================================================================================

/// <summary>
/// NLRD Adult Basic Education and Training Band classifications (Field: ABET_BAND_ID - 1 to 5).
/// </summary>
[Table("AbetBandType", Schema = "lookup")]
public class AbetBandType : BaseLookupType { }

/// <summary>
/// SAQA National Qualifications Framework qualification formal types (Field: QUALIFICATION_TYPE_ID - National Certificate, Diploma, Occupational Certificate, etc.).
/// </summary>
[Table("QualificationTypeType", Schema = "lookup")]
public class QualificationTypeType : BaseLookupType { }

/// <summary>
/// Higher Education and Umalusi academic achievement classification distinctions (Field: HONOURS_CLASS_ID - Cum Laude, Honours, etc.).
/// </summary>
[Table("HonoursClassType", Schema = "lookup")]
public class HonoursClassType : BaseLookupType { }

