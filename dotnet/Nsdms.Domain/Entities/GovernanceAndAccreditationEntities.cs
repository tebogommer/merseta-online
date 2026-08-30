using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Executive &amp; ETQA Review Committee / MANCO Governance Meeting.
/// </summary>
public class ReviewCommitteeMeeting : BaseEntity
{
    public string Title { get; set; } = string.Empty;
    public string MeetingTypeCode { get; set; } = "EtqaReviewCommittee"; // EtqaReviewCommittee, Manco, DgAdjudicationCommittee, AuditAndRisk
    public string MeetingNumber { get; set; } = string.Empty;
    public DateTime FromDateTime { get; set; } = DateTime.UtcNow.AddDays(7);
    public DateTime ToDateTime { get; set; } = DateTime.UtcNow.AddDays(7).AddHours(4);
    public string Venue { get; set; } = "merSETA Head Office / Microsoft Teams";
    public string? AdditionalInfo { get; set; }
    public string StatusCode { get; set; } = "Scheduled"; // Scheduled, InProgress, Concluded, Adjourned, Cancelled
    public string? ChairpersonUserId { get; set; }
    public bool QuorumReached { get; set; } = true;

    // Collections
    public ICollection<ReviewCommitteeMeetingAgenda> Agendas { get; set; } = new List<ReviewCommitteeMeetingAgenda>();
    public ICollection<ReviewCommitteeMeetingMember> Members { get; set; } = new List<ReviewCommitteeMeetingMember>();
}

/// <summary>
/// Specific agenda item submitted for committee adjudication or voting resolution.
/// </summary>
public class ReviewCommitteeMeetingAgenda : BaseEntity
{
    public int ReviewCommitteeMeetingId { get; set; }
    public ReviewCommitteeMeeting? ReviewCommitteeMeeting { get; set; }

    public int ItemNumber { get; set; }
    public string Title { get; set; } = string.Empty;
    public string? Description { get; set; }
    public string? TargetEntityName { get; set; } // AssessorModeratorApplication, SdpScopeExtensionApplication, DgAllocation
    public int? TargetEntityId { get; set; }
    public string DecisionCode { get; set; } = "Pending"; // Pending, Approved, Rejected, Deferred
    public string? DecisionNotes { get; set; }
    public int VotedYesCount { get; set; } = 0;
    public int VotedNoCount { get; set; } = 0;
    public int AbstainCount { get; set; } = 0;
}

/// <summary>
/// Committee attendee / voting member.
/// </summary>
public class ReviewCommitteeMeetingMember : BaseEntity
{
    public int ReviewCommitteeMeetingId { get; set; }
    public ReviewCommitteeMeeting? ReviewCommitteeMeeting { get; set; }

    public int PersonId { get; set; }
    public Person? Person { get; set; }

    public string RoleInMeeting { get; set; } = "VotingMember"; // Chairperson, Secretariat, VotingMember, Observer
    public bool Attended { get; set; } = false;
}

/// <summary>
/// Assessor and Moderator Accreditation Application.
/// </summary>
public class AssessorModeratorApplication : BaseEntity
{
    public int PersonId { get; set; }
    public Person? Person { get; set; }

    public string ApplicationTypeCode { get; set; } = "Assessor"; // Assessor, Moderator, ReRegistration
    public string ApplicationNumber { get; set; } = string.Empty;
    public string StatusCode { get; set; } = "Draft"; // Draft, UnderEvaluation, RecommendedForCommittee, Approved, Rejected
    public string? CertificateNumber { get; set; }
    public DateTime? ExpiryDate { get; set; }
    public int? ReviewCommitteeMeetingAgendaId { get; set; }
    public ReviewCommitteeMeetingAgenda? ReviewCommitteeMeetingAgenda { get; set; }
}

/// <summary>
/// Skills Development Provider (SDP) Scope Extension Application.
/// </summary>
public class SdpScopeExtensionApplication : BaseEntity
{
    public int TrainingProviderId { get; set; }
    public TrainingProvider? TrainingProvider { get; set; }

    public string ApplicationNumber { get; set; } = string.Empty;
    public int? SaqaQualificationId { get; set; }
    public string? QualificationTitle { get; set; }

    public string StatusCode { get; set; } = "Draft"; // Draft, SiteAuditScheduled, CommitteeReview, Approved, Rejected
    public string? RecommendationNotes { get; set; }
    public int? ReviewCommitteeMeetingAgendaId { get; set; }
    public ReviewCommitteeMeetingAgenda? ReviewCommitteeMeetingAgenda { get; set; }
}
