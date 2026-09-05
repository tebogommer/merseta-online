using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Workplace site inspection approval for hosting apprentice and learnership training.
/// </summary>
public class WorkplaceApproval : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the host Employer Organisation.
    /// </summary>
    public int OrganisationId { get; set; }

    /// <summary>
    /// Navigational reference to the Employer Organisation.
    /// </summary>
    public Organisation? Organisation { get; set; }

    /// <summary>
    /// Optional foreign key referencing the specific branch or plant site approved.
    /// </summary>
    public int? OrganisationSiteId { get; set; }

    /// <summary>
    /// Navigational reference to the approved OrganisationSite.
    /// </summary>
    public OrganisationSite? OrganisationSite { get; set; }

    /// <summary>
    /// SAQA Registered Qualification ID code approved for practical workplace training.
    /// </summary>
    public int? SaqaQualificationId { get; set; }

    /// <summary>
    /// Title of the registered qualification approved for on-site hosting.
    /// </summary>
    public string QualificationTitle { get; set; } = string.Empty;

    /// <summary>
    /// Official workplace approval certificate reference number (e.g. WPA-2026-001).
    /// </summary>
    public string ApprovalNumber { get; set; } = string.Empty;

    /// <summary>
    /// Current approval lifecycle status code (e.g. Pending, Approved, Rejected, Expired).
    /// </summary>
    public string ApprovalStatusCode { get; set; } = "Pending";

    /// <summary>
    /// Date when the physical on-site audit inspection occurred.
    /// </summary>
    public DateTime? InspectionDate { get; set; }

    /// <summary>
    /// Official decision date granting workplace approval.
    /// </summary>
    public DateTime? ApprovalDate { get; set; }

    /// <summary>
    /// Validity expiration date of the workplace approval certificate.
    /// </summary>
    public DateTime? ExpiryDate { get; set; }

    /// <summary>
    /// Mandatory Foreign key referencing the designated Employer Contact Person present during the workplace visit/approval.
    /// </summary>
    public int? ContactPersonId { get; set; }

    /// <summary>
    /// Navigational reference to the designated Employer Contact Person.
    /// </summary>
    public Person? ContactPerson { get; set; }

    /// <summary>
    /// Foreign key referencing the MerSETA officer / assessor who performed the inspection.
    /// </summary>
    public int? AssessorPersonId { get; set; }

    /// <summary>
    /// Navigational reference to the inspecting assessor Person.
    /// </summary>
    public Person? AssessorPerson { get; set; }

    /// <summary>
    /// Official auditor recommendations, tool adjustments, or compliance notes.
    /// </summary>
    public string? Recommendations { get; set; }

    /// <summary>
    /// Indicates whether the workplace approval is currently active.
    /// </summary>
    public bool IsActive { get; set; } = true;

    /// <summary>
    /// Designated Trade / Occupational Code (references TradeMentorRatioPolicy.TradeCode, e.g. WELD, ELEC, FITT).
    /// </summary>
    public string? TradeCode { get; set; }

    /// <summary>
    /// Explicit mentor ratio enforcement override for this workplace approval (null = inherit Org/Global, true = enforce, false = exempt).
    /// </summary>
    public bool? IsRatioEnforced { get; set; }

    /// <summary>
    /// Custom site-specific learner-to-mentor ratio override (e.g. 3 for 1:3), overriding the standard trade policy.
    /// </summary>
    public int? CustomTradeRatio { get; set; }

    /// <summary>
    /// Exemption justification or special dispensation notes for this workplace approval.
    /// </summary>
    public string? MentorRatioExemptionNotes { get; set; }

    /// <summary>
    /// Learning programme stream code (e.g. Apprenticeship, Learnership, InternshipNDiploma, OccupationalQual, SkillsProgramme, Candidacy).
    /// </summary>
    public string? LearningProgramTypeCode { get; set; } = "Apprenticeship";

    /// <summary>
    /// Indicates whether this learning programme and qualification stream mandates statutory workplace approval.
    /// </summary>
    public bool RequiresWorkplaceApproval { get; set; } = true;

    /// <summary>
    /// Indicates whether a physical on-site audit visit is required (true) or desktop verification suffices (false).
    /// </summary>
    public bool? IsSiteVisitRequired { get; set; } = true;

    /// <summary>
    /// Justification notes if a physical site visit is waived in favour of desktop audit.
    /// </summary>
    public string? SiteVisitJustification { get; set; }

    /// <summary>
    /// Statutory SLA deadline (20 South African business days from application submission).
    /// </summary>
    public DateTime? InspectionDueDate { get; set; }

    // Section 6.2: Workplace Verification Attributes (Role-Neutral)
    /// <summary>
    /// Recommendation reason category for workplace verification.
    /// </summary>
    public string? VerificationRecommendationReason { get; set; }

    /// <summary>
    /// Detailed justification for recommending workplace approval.
    /// </summary>
    public string? VerificationRecommendationExplanation { get; set; }

    /// <summary>
    /// Rejection reason category identified during workplace verification.
    /// </summary>
    public string? VerificationRejectionReason { get; set; }

    /// <summary>
    /// Detailed justification for rejecting or returning the application during verification.
    /// </summary>
    public string? VerificationRejectionExplanation { get; set; }

    /// <summary>
    /// Date when the formal workplace verification report was concluded.
    /// </summary>
    public DateTime? VerifiedDate { get; set; }

    /// <summary>
    /// Foreign key referencing the officer person who performed the verification.
    /// </summary>
    public int? VerifiedByPersonId { get; set; }

    /// <summary>
    /// Navigational reference to the verifying officer person.
    /// </summary>
    public Person? VerifiedByPerson { get; set; }

    // Section 6.3: Workplace Evaluation & Decision Attributes (Role-Neutral)
    /// <summary>
    /// Statutory approval decision reason code.
    /// </summary>
    public string? ApprovalReason { get; set; }

    /// <summary>
    /// Detailed rationale for granting final workplace approval.
    /// </summary>
    public string? ApprovalExplanation { get; set; }

    /// <summary>
    /// Statutory rejection decision reason code.
    /// </summary>
    public string? RejectionReason { get; set; }

    /// <summary>
    /// Detailed rationale for rejecting the workplace approval application.
    /// </summary>
    public string? RejectionExplanation { get; set; }

    /// <summary>
    /// Date when the final committee or management evaluation decision was rendered.
    /// </summary>
    public DateTime? DecisionDate { get; set; }

    /// <summary>
    /// Foreign key referencing the manager or committee chairperson who rendered the decision.
    /// </summary>
    public int? DecisionByPersonId { get; set; }

    /// <summary>
    /// Navigational reference to the decision maker person.
    /// </summary>
    public Person? DecisionByPerson { get; set; }

    // Non-merSETA Support
    /// <summary>
    /// Indicates whether the host employer is registered with another SETA under non-merSETA SDL.
    /// </summary>
    public bool IsNonMerSetaCompany { get; set; } = false;

    /// <summary>
    /// Title of the originating Home SETA if employer is registered outside merSETA.
    /// </summary>
    public string? HomeSetaName { get; set; }

    /// <summary>
    /// Statutory Inter-SETA agreement or MOU reference number.
    /// </summary>
    public string? HomeSetaAgreementRef { get; set; }

    /// <summary>
    /// Certified artisan mentors assigned to supervise learners at this site.
    /// </summary>
    public ICollection<WorkplaceApprovalMentor> Mentors { get; set; } = new List<WorkplaceApprovalMentor>();

    /// <summary>
    /// Mandatory physical equipment, tools, and safety apparatus compliance checklist.
    /// </summary>
    public ICollection<WorkplaceApprovalToolList> ToolItems { get; set; } = new List<WorkplaceApprovalToolList>();
}
