using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Mandatory Grant Workplace Skills Plan (WSP) and Annual Training Report (ATR) submissions.
/// </summary>
public class WspSubmission : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the submitting Employer Organisation.
    /// </summary>
    public int OrganisationId { get; set; }

    /// <summary>
    /// Navigational reference to the submitting Organisation.
    /// </summary>
    public Organisation? Organisation { get; set; }

    /// <summary>
    /// Statutory financial/scheme year for this submission (e.g. 2026).
    /// </summary>
    public int FinYear { get; set; }

    /// <summary>
    /// Unique statutory WSP submission reference tracking number.
    /// </summary>
    public string ReferenceNumber { get; set; } = string.Empty;

    /// <summary>
    /// Current workflow review and approval status code (e.g. Draft, Submitted, Approved, Rejected).
    /// </summary>
    public string? WspApprovalStatusCode { get; set; }

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public string? StatusCode { get => WspApprovalStatusCode; set => WspApprovalStatusCode = value; }

    /// <summary>
    /// Official date and time when the submission was locked and signed off.
    /// </summary>
    public DateTime? SubmissionDate { get; set; }

    /// <summary>
    /// Total aggregate training budget planned for the upcoming financial year in ZAR.
    /// </summary>
    public decimal PlannedTrainingBudget { get; set; }

    /// <summary>
    /// Total headcount of employees declared in the organisation profile.
    /// </summary>
    public int EmployeeCount { get; set; }

    /// <summary>
    /// Occupational level employment demographics breakdown (Form 500).
    /// </summary>
    public ICollection<WspEmploymentSummary> EmploymentSummaries { get; set; } = new List<WspEmploymentSummary>();

    /// <summary>
    /// Planned learning interventions and beneficiary targets.
    /// </summary>
    public ICollection<WspTrainingPlan> TrainingPlans { get; set; } = new List<WspTrainingPlan>();
}

/// <summary>
/// Monthly SARS Skills Development Levy file import batches.
/// </summary>
public class LevyFile : BaseEntity
{
    /// <summary>
    /// Original file name uploaded from SARS levy distribution feed.
    /// </summary>
    public string FileName { get; set; } = string.Empty;

    /// <summary>
    /// Internal unique batch reference identifier.
    /// </summary>
    public string FileRef { get; set; } = string.Empty;

    /// <summary>
    /// Timestamp when the levy file was ingested into the system.
    /// </summary>
    public DateTime ImportDate { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Total count of line items contained in the levy file.
    /// </summary>
    public int TotalRecords { get; set; }

    /// <summary>
    /// Aggregate monetary value of all levy allocations in this file in ZAR.
    /// </summary>
    public decimal TotalAmount { get; set; }

    /// <summary>
    /// Processing status code (e.g. Uploaded, Processed, Reconciled, Error).
    /// </summary>
    public string? ImportStatusCode { get; set; }

    /// <summary>
    /// Individual employer levy transactions in this file.
    /// </summary>
    public ICollection<LevyFileLine> LineItems { get; set; } = new List<LevyFileLine>();

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public ICollection<LevyFileLine> Lines { get => LineItems; set => LineItems = value; }

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public string LevyMonth => ImportDate.ToString("yyyy-MM");

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public string SchemeYear => ImportDate.Year.ToString();

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public decimal TotalLevyAmount => TotalAmount;

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public decimal TotalDiscretionaryAmount => TotalAmount * 0.495m;

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public decimal TotalMandatoryAmount => TotalAmount * 0.20m;

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public decimal TotalAdminAmount => TotalAmount * 0.105m;

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public decimal TotalQctoAmount => TotalAmount * 0.005m;

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public string StatusCode { get => ImportStatusCode ?? "PROCESSED"; set => ImportStatusCode = value; }

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public string FileStatusCode => ImportStatusCode ?? "Processed";
}

/// <summary>
/// Individual employer monthly SARS levy transaction breakdown.
/// </summary>
public class LevyFileLine : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the parent LevyFile batch.
    /// </summary>
    public int LevyFileId { get; set; }

    /// <summary>
    /// Navigational reference to the parent LevyFile.
    /// </summary>
    public LevyFile? LevyFile { get; set; }

    /// <summary>
    /// SARS Skills Development Levy number (e.g. L123456789).
    /// </summary>
    public string SdlNumber { get; set; } = string.Empty;

    /// <summary>
    /// Scheme year or payment month reference (e.g. 2026-04).
    /// </summary>
    public string SchemeYear { get; set; } = string.Empty;

    /// <summary>
    /// 20% Mandatory Grant portion reserved for compliant employer rebates in ZAR.
    /// </summary>
    public decimal MandatoryLevyAmount { get; set; }

    /// <summary>
    /// 49.5% Discretionary Grant portion allocated to sector skills funding in ZAR.
    /// </summary>
    public decimal DiscretionaryLevyAmount { get; set; }

    /// <summary>
    /// 10.5% MerSETA administration levy portion in ZAR.
    /// </summary>
    public decimal AdminLevyAmount { get; set; }

    /// <summary>
    /// 0.5% Quality Council for Trades and Occupations (QCTO) levy portion in ZAR.
    /// </summary>
    public decimal QctoLevyAmount { get; set; }

    /// <summary>
    /// SARS penalty interest charged on late levy payments in ZAR.
    /// </summary>
    public decimal InterestAmount { get; set; }

    /// <summary>
    /// Statutory penalty fees charged on late levy submissions in ZAR.
    /// </summary>
    public decimal PenaltyAmount { get; set; }

    /// <summary>
    /// Gross total levy amount received for this employer in ZAR.
    /// </summary>
    public decimal TotalLevyAmount { get; set; }

    /// <summary>
    /// Indicates whether this levy line has been matched and reconciled to an employer ledger.
    /// </summary>
    public bool IsReconciled { get; set; } = false;
}

/// <summary>
/// Discretionary Grant funding applications submitted by employers for skills development projects.
/// </summary>
public class GrantApplication : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the applying Employer Organisation.
    /// </summary>
    public int OrganisationId { get; set; }

    /// <summary>
    /// Navigational reference to the applying Organisation.
    /// </summary>
    public Organisation? Organisation { get; set; }

    /// <summary>
    /// Optional foreign key referencing the open GrantFundingWindow.
    /// </summary>
    public int? FundingWindowId { get; set; }

    /// <summary>
    /// Navigational reference to the open funding window.
    /// </summary>
    public GrantFundingWindow? FundingWindow { get; set; }

    /// <summary>
    /// Unique grant application reference tracking number (e.g. DG-2026-0001).
    /// </summary>
    public string ApplicationNumber { get; set; } = string.Empty;

    /// <summary>
    /// Discretionary grant funding type code (e.g. PIVOTAL, NON_PIVOTAL, BURSARY, APPRENTICESHIP).
    /// </summary>
    public string? GrantTypeCode { get; set; }

    /// <summary>
    /// Current workflow review and adjudication status code.
    /// </summary>
    public string? ApplicationStatusCode { get; set; }

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public string? StatusCode { get => ApplicationStatusCode; set => ApplicationStatusCode = value; }

    /// <summary>
    /// Total grant funding amount requested by the applicant in ZAR.
    /// </summary>
    public decimal RequestedAmount { get; set; }

    /// <summary>
    /// Final grant funding amount approved by the MerSETA adjudication committee in ZAR.
    /// </summary>
    public decimal? ApprovedAmount { get; set; }

    /// <summary>
    /// Date when the grant application was officially submitted.
    /// </summary>
    public DateTime ApplicationDate { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Descriptive title of the skills development project.
    /// </summary>
    public string ProjectTitle { get; set; } = string.Empty;

    /// <summary>
    /// Cost line items and budget breakdown for the grant project.
    /// </summary>
    public ICollection<GrantProjectBudget> ProjectBudgets { get; set; } = new List<GrantProjectBudget>();
}

/// <summary>
/// Registered ETQA Assessors and Moderators with approved qualification scopes,
/// capturing all statutory fields required for SETMIS File 401 (Person Designation) reporting.
/// </summary>
public class EtqaAssessor : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the assessor's demographic Person record.
    /// </summary>
    public int PersonId { get; set; }

    /// <summary>
    /// Navigational reference to the Person record.
    /// </summary>
    public Person? Person { get; set; }

    /// <summary>
    /// MerSETA ETQA assessor or moderator registration number (SETMIS File 401).
    /// </summary>
    public string RegistrationNumber { get; set; } = string.Empty;

    /// <summary>
    /// Practitioner designation type code (references lookup.DesignationType: 01 Assessor, 02 Moderator).
    /// </summary>
    public string DesignationTypeId { get; set; } = "01";

    /// <summary>
    /// Practitioner registration standing code (references lookup.DesignationStructureStatusType: 01 Registered, 02 Deregistered, 03 Suspended).
    /// </summary>
    public string DesignationStructureStatusId { get; set; } = "01";

    /// <summary>
    /// Submitting ETQA ID (references lookup.SetaType, default 17 for merSETA).
    /// </summary>
    public string EtqaId { get; set; } = "17";

    /// <summary>
    /// ETQA Committee decision number approving registration scope (SETMIS File 401).
    /// </summary>
    public string? EtqeDecisionNumber { get; set; }

    /// <summary>
    /// Optional foreign key referencing primary affiliated Skills Development Provider.
    /// </summary>
    public int? TrainingProviderId { get; set; }

    /// <summary>
    /// Navigational reference to primary affiliated Training Provider.
    /// </summary>
    public TrainingProvider? TrainingProvider { get; set; }

    /// <summary>
    /// ETQA role classification (Assessor, Moderator, Both).
    /// </summary>
    public string EtqaRole { get; set; } = "Assessor";

    /// <summary>
    /// Current registration lifecycle status code (e.g. Active, PendingRenewal, Suspended, Expired).
    /// </summary>
    public string? RegistrationStatusCode { get; set; }

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public string? StatusCode { get => RegistrationStatusCode; set => RegistrationStatusCode = value; }

    /// <summary>
    /// Registration validity start date (SETMIS File 401).
    /// </summary>
    public DateTime StartDate { get; set; }

    /// <summary>
    /// Registration validity expiration date (SETMIS File 401).
    /// </summary>
    public DateTime EndDate { get; set; }

    /// <summary>
    /// Indicates whether the assessor registration is currently active.
    /// </summary>
    public bool IsActive { get; set; } = true;

    /// <summary>
    /// Registered unit standard and qualification assessment scopes.
    /// </summary>
    public ICollection<AssessorModeratorScope> Scopes { get; set; } = new List<AssessorModeratorScope>();

    /// <summary>
    /// Learner assessment evaluations conducted by this assessor.
    /// </summary>
    public ICollection<LearnerAssessment> Assessments { get; set; } = new List<LearnerAssessment>();
}
