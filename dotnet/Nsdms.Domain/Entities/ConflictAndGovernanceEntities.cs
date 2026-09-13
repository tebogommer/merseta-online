using System.ComponentModel.DataAnnotations.Schema;
using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Natural person serving as a legal director, partner, trustee, board member, or shareholder
/// within a participating employer, training provider, or grant applicant organisation.
/// </summary>
public class OrganisationGovernanceMember : BaseEntity
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
    /// Foreign key referencing the natural Person (nullable if corporate entity shareholder).
    /// </summary>
    public int? PersonId { get; set; }

    /// <summary>
    /// Navigational reference to the Person.
    /// </summary>
    public Person? Person { get; set; }

    /// <summary>
    /// Foreign key referencing a corporate institutional entity shareholder (if registered in merSETA system).
    /// </summary>
    public int? ShareholderOrganisationId { get; set; }

    /// <summary>
    /// Navigational reference to corporate institutional shareholder.
    /// </summary>
    public Organisation? ShareholderOrganisation { get; set; }

    /// <summary>
    /// Member type classification: NATURAL_PERSON or CORPORATE_ENTITY.
    /// </summary>
    public string MemberType { get; set; } = "NATURAL_PERSON";

    /// <summary>
    /// Registered legal name if corporate institutional shareholder (e.g. ABC Holdings (Pty) Ltd).
    /// </summary>
    public string? CorporateEntityName { get; set; }

    /// <summary>
    /// CIPC registration number if corporate institutional shareholder.
    /// </summary>
    public string? CorporateRegistrationNumber { get; set; }

    /// <summary>
    /// Director classification: EXECUTIVE or NON_EXECUTIVE.
    /// </summary>
    public string? DirectorCategory { get; set; }

    /// <summary>
    /// Evaluated display name for UI rendering across persons and corporate entities.
    /// </summary>
    [NotMapped]
    public string DisplayName => MemberType == "CORPORATE_ENTITY"
        ? (CorporateEntityName ?? ShareholderOrganisation?.CompanyName ?? "Corporate Shareholder")
        : (Person?.FullName ?? "N/A");

    /// <summary>
    /// Evaluated statutory identifier (RSA ID / Passport for persons; CIPC Registration number for entities).
    /// </summary>
    [NotMapped]
    public string DisplayIdentifier => MemberType == "CORPORATE_ENTITY"
        ? (CorporateRegistrationNumber ?? ShareholderOrganisation?.RegistrationNumber ?? "—")
        : (Person?.RsaIdNumber ?? Person?.PassportNumber ?? "—");

    /// <summary>
    /// Legal governance capacity code (e.g. DIRECTOR, MANAGING_DIRECTOR, PARTNER, TRUSTEE, SHAREHOLDER, BOARD_MEMBER, ACCOUNTING_OFFICER).
    /// </summary>
    public string GovernanceRoleCode { get; set; } = "DIRECTOR";

    /// <summary>
    /// Equity / beneficial shareholding percentage (0.00% to 100.00%).
    /// </summary>
    [Column(TypeName = "decimal(5, 2)")]
    public decimal ShareholdingPercentage { get; set; } = 0.00m;

    /// <summary>
    /// Indicates whether this member exercises corporate voting rights.
    /// </summary>
    public bool HasVotingRights { get; set; } = true;

    /// <summary>
    /// Official date of appointment according to CIPC / constitution.
    /// </summary>
    public DateTime? AppointmentDate { get; set; }

    /// <summary>
    /// Date of formal resignation or cessation of interest if no longer active.
    /// </summary>
    public DateTime? ResignationDate { get; set; }

    /// <summary>
    /// Indicates whether this directorship or shareholding is verified against CIPC Beneficial Ownership records.
    /// </summary>
    public bool CipcRegistered { get; set; } = true;

    /// <summary>
    /// Indicates whether identity verification (RSA ID / Passport) has passed.
    /// </summary>
    public bool IdVerified { get; set; } = true;

    /// <summary>
    /// Operational active flag.
    /// </summary>
    public bool IsActive { get; set; } = true;
}

/// <summary>
/// Internal institutional appointment linking a Person to merSETA as an employee,
/// executive, Accounting Authority (Board) member, or independent committee specialist.
/// </summary>
public class InstitutionalAffiliation : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the natural Person.
    /// </summary>
    public int PersonId { get; set; }

    /// <summary>
    /// Navigational reference to the Person.
    /// </summary>
    public Person? Person { get; set; }

    /// <summary>
    /// Official institutional affiliation type code:
    /// ACCOUNTING_AUTHORITY_MEMBER, INDEPENDENT_COMMITTEE_MEMBER, EXECUTIVE_MANAGEMENT,
    /// PERMANENT_EMPLOYEE, ADJUDICATION_OFFICER, CONTRACTOR.
    /// </summary>
    public string AffiliationTypeCode { get; set; } = "PERMANENT_EMPLOYEE";

    /// <summary>
    /// Specific statutory committee or business department:
    /// e.g. Accounting Authority Board, Audit &amp; Risk Committee, Finance MANCO,
    /// DG Adjudication Committee, Grants &amp; Operations, Quality Assurance &amp; ETQA.
    /// </summary>
    public string DepartmentOrCommittee { get; set; } = string.Empty;

    /// <summary>
    /// Official job title or committee appointment designation:
    /// e.g. Board Chairperson, Independent Specialist Member, Senior Grant Evaluator, Chief Financial Officer.
    /// </summary>
    public string Designation { get; set; } = string.Empty;

    /// <summary>
    /// Internal merSETA payroll / staff employee reference number if applicable.
    /// </summary>
    public string? EmployeeNumber { get; set; }

    /// <summary>
    /// Indicates whether the member is an independent specialist (e.g. Independent Audit &amp; Risk Committee Member).
    /// </summary>
    public bool IsIndependentMember { get; set; } = false;

    /// <summary>
    /// Official gazetted / contractual term commencement date.
    /// </summary>
    public DateTime? TermStartDate { get; set; }

    /// <summary>
    /// Expiry or conclusion date of the term of office.
    /// </summary>
    public DateTime? TermEndDate { get; set; }

    /// <summary>
    /// Indicates whether the affiliation is currently active.
    /// </summary>
    public bool IsActive { get; set; } = true;
}

/// <summary>
/// Official statutory Declaration of Interest (e-DOI) filed by an insider, committee member, or applicant director
/// in terms of Section 50/51 of the Public Finance Management Act (PFMA) and King IV.
/// </summary>
public class InterestDeclaration : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the declaring Person.
    /// </summary>
    public int PersonId { get; set; }

    /// <summary>
    /// Navigational reference to the Person.
    /// </summary>
    public Person? Person { get; set; }

    /// <summary>
    /// Optional foreign key referencing the applicable Financial / Scheme Year.
    /// </summary>
    public int? FinancialYearId { get; set; }

    /// <summary>
    /// Navigational reference to the Financial Year.
    /// </summary>
    public FinancialYear? FinancialYear { get; set; }

    /// <summary>
    /// Scheme or financial calendar year of compliance (e.g. 2026).
    /// </summary>
    public string DeclarationPeriodYear { get; set; } = "2026";

    /// <summary>
    /// Statutory declaration type code:
    /// ANNUAL_COMPLIANCE, MEETING_RECUSAL, TENDER_EVALUATION, GRANT_ADJUDICATION.
    /// </summary>
    public string DeclarationTypeCode { get; set; } = "ANNUAL_COMPLIANCE";

    /// <summary>
    /// Optional contextual reference (e.g. Review Committee Meeting Agenda number or Tender reference).
    /// </summary>
    public string? MeetingOrProjectRef { get; set; }

    /// <summary>
    /// Declaration status code: DRAFT, SUBMITTED, CERTIFIED, FLAGGED_CONFLICT, UNDER_REVIEW.
    /// </summary>
    public string StatusCode { get; set; } = "SUBMITTED";

    /// <summary>
    /// Indicates whether the individual has private commercial interests, shareholdings, or directorships to declare.
    /// </summary>
    public bool HasConflictsToDeclare { get; set; } = false;

    /// <summary>
    /// Narrative disclosure notes or explanatory statement.
    /// </summary>
    public string? GeneralDeclarationNotes { get; set; }

    /// <summary>
    /// Truncated cryptographic SHA-256 digital security seal prefix computed over the attestation payload.
    /// </summary>
    public string? DigitalSignatureSeal { get; set; }

    /// <summary>
    /// Timestamp when the electronic declaration was formally certified.
    /// </summary>
    public DateTime? CertifiedAt { get; set; }

    /// <summary>
    /// Username / Actor who certified or witnessed the declaration.
    /// </summary>
    public string? CertifiedByUserId { get; set; }

    /// <summary>
    /// Detailed commercial interests, corporate directorships, or shareholdings declared under this submission.
    /// </summary>
    public ICollection<InterestDeclarationItem> Items { get; set; } = new List<InterestDeclarationItem>();
}

/// <summary>
/// Individual commercial interest, directorship, shareholding, or partnership item
/// declared within an official statutory Declaration of Interest.
/// </summary>
public class InterestDeclarationItem : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the parent InterestDeclaration.
    /// </summary>
    public int InterestDeclarationId { get; set; }

    /// <summary>
    /// Navigational reference to the parent InterestDeclaration.
    /// </summary>
    public InterestDeclaration? InterestDeclaration { get; set; }

    /// <summary>
    /// Legal registered name of the external corporate entity or business enterprise.
    /// </summary>
    public string OrganisationName { get; set; } = string.Empty;

    /// <summary>
    /// Enterprise CIPC registration number or SARS SDL number if known.
    /// </summary>
    public string? RegistrationOrSdlNumber { get; set; }

    /// <summary>
    /// Nature of relationship or interest:
    /// DIRECTOR, SHAREHOLDER, PARTNER, CONSULTANT, FAMILY_MEMBER, OTHER_FINANCIAL_INTEREST.
    /// </summary>
    public string NatureOfRelationship { get; set; } = "DIRECTOR";

    /// <summary>
    /// Ownership or beneficial equity shareholding percentage if applicable.
    /// </summary>
    [Column(TypeName = "decimal(5, 2)")]
    public decimal? InterestPercentage { get; set; }

    /// <summary>
    /// Estimated annual remuneration, dividend, or financial benefit derived.
    /// </summary>
    [Column(TypeName = "decimal(18, 2)")]
    public decimal? AnnualRemunerationOrBenefit { get; set; }

    /// <summary>
    /// Indicates whether formal approval for Remunerative Work Outside the Public Service (RWOPS) has been granted.
    /// </summary>
    public bool IsApprovedExternalWork { get; set; } = false;

    /// <summary>
    /// Executive or CEO approval reference number for external remunerative work.
    /// </summary>
    public string? ApprovalReference { get; set; }
}

/// <summary>
/// Automated conflict of interest detection flag raised by the rules engine
/// during grant allocation, committee adjudication, accreditation intake, or annual audit.
/// </summary>
public class ConflictFlag : BaseEntity
{
    /// <summary>
    /// Optional foreign key referencing the implicated legal Organisation.
    /// </summary>
    public int? TargetOrganisationId { get; set; }

    /// <summary>
    /// Navigational reference to the implicated Organisation.
    /// </summary>
    public Organisation? TargetOrganisation { get; set; }

    /// <summary>
    /// Optional foreign key referencing the specific Grant Application implicated.
    /// </summary>
    public int? TargetGrantApplicationId { get; set; }

    /// <summary>
    /// Navigational reference to the Grant Application.
    /// </summary>
    public GrantApplication? TargetGrantApplication { get; set; }

    /// <summary>
    /// Optional foreign key referencing the Skills Development Provider implicated if in accreditation context.
    /// </summary>
    public int? TargetTrainingProviderId { get; set; }

    /// <summary>
    /// Navigational reference to the Training Provider.
    /// </summary>
    public TrainingProvider? TargetTrainingProvider { get; set; }

    /// <summary>
    /// Foreign key referencing the natural Person whose dual affiliations create the conflict.
    /// </summary>
    public int PersonId { get; set; }

    /// <summary>
    /// Navigational reference to the Person.
    /// </summary>
    public Person? Person { get; set; }

    /// <summary>
    /// Severity classification code:
    /// RED_CRITICAL (Hard statutory block: insider affiliation, direct benefit, PFMA Section 50 breach),
    /// AMBER_ELEVATED (Multi-organisation grant access, committee recusal required, unverified beneficial owner),
    /// YELLOW_ADVISORY (Overdue annual declaration, expired information).
    /// </summary>
    public string SeverityCode { get; set; } = "AMBER_ELEVATED";

    /// <summary>
    /// Conflict category code:
    /// INSIDER_AFFILIATION, MULTI_ORGANISATION_GRANT_SYNDICATE, ACCREDITATION_GOVERNANCE_BREACH,
    /// COMMITTEE_RECUSAL_REQUIRED, OVERDUE_DECLARATION.
    /// </summary>
    public string ConflictCategoryCode { get; set; } = "MULTI_ORGANISATION_GRANT_SYNDICATE";

    /// <summary>
    /// Short descriptive title of the conflict incident.
    /// </summary>
    public string Title { get; set; } = string.Empty;

    /// <summary>
    /// Detailed factual narrative and forensic rationale for the conflict flag.
    /// </summary>
    public string Description { get; set; } = string.Empty;

    /// <summary>
    /// Date and time when the conflict was detected by the rules engine.
    /// </summary>
    public DateTime DetectedAt { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Current investigation and resolution status:
    /// OPEN, UNDER_INVESTIGATION, CLEARED_WITH_JUSTIFICATION, UPHELD_DISQUALIFIED.
    /// </summary>
    public string ResolutionStatusCode { get; set; } = "OPEN";

    /// <summary>
    /// Detailed justification notes recorded upon resolution or clearance.
    /// </summary>
    public string? ResolutionNotes { get; set; }

    /// <summary>
    /// Username / Actor who cleared or adjudicated the conflict flag.
    /// </summary>
    public string? ClearedByUserId { get; set; }

    /// <summary>
    /// Date and time of clearance or final adjudication.
    /// </summary>
    public DateTime? ClearedAt { get; set; }

    /// <summary>
    /// Statutory authority role under which clearance was executed
    /// (e.g. RiskAndComplianceManager, InternalAudit, Ceo, AccountingAuthority).
    /// </summary>
    public string? ClearanceAuthorityRole { get; set; }
}
