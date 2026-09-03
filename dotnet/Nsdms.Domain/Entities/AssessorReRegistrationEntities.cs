using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Represents a statutory 3-year re-registration or scope extension application for ETQA registered Assessors and Moderators.
/// </summary>
public class AssessorReRegistrationApplication : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the parent EtqaAssessor record.
    /// </summary>
    public int EtqaAssessorId { get; set; }

    /// <summary>
    /// Navigational reference to the registered Assessor/Moderator.
    /// </summary>
    public EtqaAssessor? EtqaAssessor { get; set; }

    /// <summary>
    /// Statutory application tracking reference (e.g. REG-2026-ASS-0042).
    /// </summary>
    public string ApplicationReferenceNumber { get; set; } = string.Empty;

    /// <summary>
    /// Application type code (e.g. ReRegistration, ScopeExtension, InitialRegistration).
    /// </summary>
    public string ApplicationTypeCode { get; set; } = "ReRegistration";

    /// <summary>
    /// Practitioner expiration date prior to renewal.
    /// </summary>
    public DateTime CurrentExpirationDate { get; set; }

    /// <summary>
    /// Proposed 3-year extended expiration date upon approval.
    /// </summary>
    public DateTime ProposedNewExpirationDate { get; set; }

    /// <summary>
    /// Cumulative CPD points accumulated for this renewal cycle (statutory target >= 30 points).
    /// </summary>
    public int CpdPointsAccumulated { get; set; }

    /// <summary>
    /// Detailed narrative summary of continuous professional development and workplace practice.
    /// </summary>
    public string? CpdPortfolioSummary { get; set; }

    /// <summary>
    /// JSON serialized snapshot of confirmed renewal scopes and requested new unit standards.
    /// </summary>
    public string ScopeConfirmationJson { get; set; } = "[]";

    /// <summary>
    /// Current adjudication review status (Draft, Submitted, CommitteeReview, Approved, Rejected, AdditionalInfoRequired).
    /// </summary>
    public string ReviewStatusCode { get; set; } = "Draft";

    /// <summary>
    /// Official ETQA Committee Decision Number approving the 3-year re-registration.
    /// </summary>
    public string? CommitteeDecisionNumber { get; set; }

    /// <summary>
    /// Timestamp when the ETQA committee completed adjudication.
    /// </summary>
    public DateTime? AdjudicationDate { get; set; }

    /// <summary>
    /// User ID of the ETQA manager or committee secretary who adjudicated the application.
    /// </summary>
    public string? AdjudicatedByUserId { get; set; }

    /// <summary>
    /// Adjudication committee feedback or justification notes.
    /// </summary>
    public string? AdjudicationNotes { get; set; }

    /// <summary>
    /// Immutable SHA-256 digital security seal certifying renewal approval and certificate issuance.
    /// </summary>
    public string? DigitalSecuritySeal { get; set; }

    /// <summary>
    /// Collection of logged CPD activities supporting this renewal application.
    /// </summary>
    public ICollection<AssessorCpdActivity> CpdActivities { get; set; } = new List<AssessorCpdActivity>();
}

/// <summary>
/// Individual Continuous Professional Development (CPD) activity logged in support of practitioner renewal.
/// </summary>
public class AssessorCpdActivity : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the parent renewal application.
    /// </summary>
    public int AssessorReRegistrationApplicationId { get; set; }

    /// <summary>
    /// Navigational reference to the parent renewal application.
    /// </summary>
    public AssessorReRegistrationApplication? Application { get; set; }

    /// <summary>
    /// Date when the CPD activity was undertaken.
    /// </summary>
    public DateTime ActivityDate { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Title or description of the training, moderation workshop, or industry practice.
    /// </summary>
    public string ActivityTitle { get; set; } = string.Empty;

    /// <summary>
    /// Activity category code (IndustryPractice, SetaWorkshop, PeerModeration, CourseAttendance, Mentorship).
    /// </summary>
    public string ActivityCategory { get; set; } = "IndustryPractice";

    /// <summary>
    /// Number of CPD points claimed by the applicant.
    /// </summary>
    public int PointsClaimed { get; set; }

    /// <summary>
    /// Number of CPD points officially accredited by the ETQA evaluator.
    /// </summary>
    public int PointsApproved { get; set; }

    /// <summary>
    /// Supporting certificate or document file reference.
    /// </summary>
    public string? EvidenceDocumentRef { get; set; }
}
