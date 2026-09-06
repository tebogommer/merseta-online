using System.ComponentModel.DataAnnotations.Schema;
using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Formative and summative learning programme unit standard assessment evaluations,
/// capturing all statutory fields required for SETMIS File 503 (Unit Standard Enrolment) reporting.
/// </summary>
public class LearnerAssessment : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the parent CompanyLearner agreement.
    /// </summary>
    public int? CompanyLearnerId { get; set; }
    [NotMapped]
    public int? EnrolmentId { get => CompanyLearnerId; set => CompanyLearnerId = value; }
    [NotMapped]
    public int? LearnerEnrolmentId { get => CompanyLearnerId; set => CompanyLearnerId = value; }

    /// <summary>
    /// Navigational reference to the candidate CompanyLearner agreement.
    /// </summary>
    public CompanyLearner? CompanyLearner { get; set; }

    /// <summary>
    /// Foreign key referencing the learner Person evaluated.
    /// </summary>
    public int PersonId { get; set; }

    /// <summary>
    /// Navigational reference to the learner Person.
    /// </summary>
    public Person? Person { get; set; }

    /// <summary>
    /// Foreign key referencing the conducting EtqaAssessor.
    /// </summary>
    public int? EtqaAssessorId { get; set; }

    /// <summary>
    /// Navigational reference to the conducting EtqaAssessor.
    /// </summary>
    public EtqaAssessor? EtqaAssessor { get; set; }

    /// <summary>
    /// Foreign key referencing the host Employer Organisation or provider.
    /// </summary>
    public int OrganisationId { get; set; }

    /// <summary>
    /// Navigational reference to the host Organisation.
    /// </summary>
    public Organisation? Organisation { get; set; }

    /// <summary>
    /// Foreign key referencing the accredited Skills Development Training Provider (Assessment Centre).
    /// </summary>
    public int? TrainingProviderId { get; set; }

    /// <summary>
    /// Navigational reference to the accredited Training Provider.
    /// </summary>
    public TrainingProvider? TrainingProvider { get; set; }

    /// <summary>
    /// SAQA Unit Standard ID (e.g. 119472, 243272) for SETMIS File 503.
    /// </summary>
    public int? UnitStandardId { get; set; }

    /// <summary>
    /// SAQA Unit Standard title.
    /// </summary>
    public string? UnitStandardTitle { get; set; }

    /// <summary>
    /// Registered qualification title assessed.
    /// </summary>
    public string QualificationTitle { get; set; } = string.Empty;

    /// <summary>
    /// Date when the assessment evaluation occurred.
    /// </summary>
    public DateTime AssessmentDate { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Programme hierarchy and articulation classification code (references lookup.PartOfType: 01 Stand-alone, 02 Part of Qual, 03 Part of Learnership).
    /// </summary>
    public string PartOfId { get; set; } = "01";

    /// <summary>
    /// Learner delivery mode classification code (references lookup.EnrolmentType: 01 Contact, 02 Distance, 03 Mixed Mode, 04 Workplace).
    /// </summary>
    public string EnrolmentTypeId { get; set; } = "01";

    /// <summary>
    /// Statutory enrolment assessment status code (references lookup.EnrolmentStatusType: 01 Enrolled, 02 Achieved, 03 Certificated, 04 Terminated).
    /// </summary>
    public string EnrolmentStatusId { get; set; } = "02";

    /// <summary>
    /// Statutory reason code for status changes (references lookup.EnrolmentStatusReasonType).
    /// </summary>
    public string? EnrolmentStatusReasonId { get; set; }

    /// <summary>
    /// Registered ETQA Assessor number conducting evaluation (SETMIS File 503).
    /// </summary>
    public string? AssessorRegistrationNumber { get; set; }

    /// <summary>
    /// Submitting ETQA ID of the Assessor (references lookup.SetaType, default 17 for merSETA).
    /// </summary>
    public string AssessorEtqaId { get; set; } = "17";

    /// <summary>
    /// Statement of Results or Certificate serial number.
    /// </summary>
    public string? CertificateNumber { get; set; }

    /// <summary>
    /// Cumulative funding disbursed for this unit standard intervention.
    /// </summary>
    public decimal CumulativeSpend { get; set; } = 0.00m;

    /// <summary>
    /// DHET Organising Framework for Occupations statutory code (references lookup.OfoCodeType).
    /// </summary>
    public string? OfoCode { get; set; }

    /// <summary>
    /// Funding vehicle identifier (references lookup.FundingType: 01 SETA Funded, 02 Employer, 03 NSF).
    /// </summary>
    public string FundingId { get; set; } = "01";

    /// <summary>
    /// Geographic intervention area classification (references lookup.UrbanRuralType: 01 Urban, 02 Rural).
    /// </summary>
    public string UrbanRuralId { get; set; } = "01";

    /// <summary>
    /// Learner economic employment standing code (references lookup.EconomicStatusType).
    /// </summary>
    public string EconomicStatusId { get; set; } = "01";

    /// <summary>
    /// Non-NQF Skills Programme / Course Code if applicable.
    /// </summary>
    public string? NonNqfInterventionCode { get; set; }

    /// <summary>
    /// Competency outcome code (e.g. COMPETENT, NOT_YET_COMPETENT).
    /// </summary>
    public string? CompetencyStatusCode { get; set; } = "COMPETENT";

    /// <summary>
    /// Optional foreign key referencing the internal/external moderator Person.
    /// </summary>
    public int? ModeratorPersonId { get; set; }

    /// <summary>
    /// Navigational reference to the moderator Person.
    /// </summary>
    public Person? ModeratorPerson { get; set; }

    /// <summary>
    /// Date when assessment moderation review was finalized.
    /// </summary>
    public DateTime? ModerationDate { get; set; }
}
