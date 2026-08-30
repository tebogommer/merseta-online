using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

public class LearnerAssessment : BaseEntity
{
    public int EtqaAssessorId { get; set; }
    public EtqaAssessor? EtqaAssessor { get; set; }

    public int PersonId { get; set; }
    public Person? Person { get; set; }

    public int OrganisationId { get; set; }
    public Organisation? Organisation { get; set; }

    public string QualificationTitle { get; set; } = string.Empty;
    public DateTime AssessmentDate { get; set; }
    public string? CompetencyStatusCode { get; set; }

    public int? ModeratorPersonId { get; set; }
    public Person? ModeratorPerson { get; set; }
    public DateTime? ModerationDate { get; set; }
}
