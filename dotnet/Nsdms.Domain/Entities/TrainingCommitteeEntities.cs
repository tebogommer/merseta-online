using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Employer Workplace Training Committee constituted per statutory merSETA guidelines.
/// </summary>
public class TrainingCommittee : BaseEntity
{
    public int OrganisationId { get; set; }
    public Organisation? Organisation { get; set; }

    public int FinancialYear { get; set; } = DateTime.UtcNow.Year;
    public string CommitteeStatusCode { get; set; } = "Active"; // Active, Dissolved, NonCompliant

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public string StatusCode { get => CommitteeStatusCode; set => CommitteeStatusCode = value; }

    public bool ConstitutionalQuorumMet { get; set; } = true;
    public DateTime? LastMeetingDate { get; set; }

    // Collections
    public ICollection<TrainingCommitteeMember> Members { get; set; } = new List<TrainingCommitteeMember>();
}

/// <summary>
/// Individual Union and Employer representatives on a Training Committee.
/// </summary>
public class TrainingCommitteeMember : BaseEntity
{
    public int TrainingCommitteeId { get; set; }
    public TrainingCommittee? TrainingCommittee { get; set; }

    public int PersonId { get; set; }
    public Person? Person { get; set; }

    public string MemberRoleCode { get; set; } = "UnionRepresentative"; // UnionRepresentative, EmployerRepresentative, Chairperson, SdfSecretary
    public string Constituency { get; set; } = "NUMSA"; // Trade Union or Employer division
    public bool IsActive { get; set; } = true;
}

/// <summary>
/// Dispute logged regarding Workplace Skills Plan (WSP) approval or committee sign-off.
/// </summary>
public class WspDispute : BaseEntity
{
    public int OrganisationId { get; set; }
    public Organisation? Organisation { get; set; }

    public int? WspSubmissionId { get; set; }
    public WspSubmission? WspSubmission { get; set; }

    public string DisputeReferenceNumber { get; set; } = string.Empty;
    public string DisputeReasonCode { get; set; } = "UnionRefusalToSign"; // UnionRefusalToSign, ConsultationFailure, SkillsPlanOmission, FinancialDisagreement
    public string Description { get; set; } = string.Empty;
    public string DisputeStatusCode { get; set; } = "Logged"; // Logged, InvestigationInProgress, MediationScheduled, Resolved, EscalatedToDhet

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public string StatusCode { get => DisputeStatusCode; set => DisputeStatusCode = value; }

    public DateTime? ResolutionDate { get; set; }
    public string? ResolutionNotes { get; set; }
}

/// <summary>
/// Critical and Scarce Skills Gap identified during WSP compilation.
/// </summary>
public class WspSkillsGap : BaseEntity
{
    public int OrganisationId { get; set; }
    public Organisation? Organisation { get; set; }

    public int FinancialYear { get; set; } = DateTime.UtcNow.Year;
    public string OfoCode { get; set; } = "651202"; // OFO Occupational Code
    public string OccupationTitle { get; set; } = "Welder";
    public int HardToFillVacanciesCount { get; set; } = 0;
    public string SkillsGapReason { get; set; } = "Lack of relevant experience and trade test certification";
}
