using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

public class CompanyLearner : BaseEntity
{
    public int PersonId { get; set; }
    public Person? Person { get; set; }

    public int OrganisationId { get; set; }
    public Organisation? Organisation { get; set; }

    public int? TrainingProviderId { get; set; }
    public TrainingProvider? TrainingProvider { get; set; }

    public string LearnerContractNumber { get; set; } = string.Empty;
    public string QualificationTitle { get; set; } = string.Empty;
    public int? SaqaQualificationId { get; set; }
    public int? NqfLevel { get; set; }

    public string LearningProgrammeTypeCode { get; set; } = "Learnership"; // Learnership, Apprenticeship, SkillsProgramme, Internship, Bursary
    public string FundingTypeCode { get; set; } = "DiscretionaryGrant"; // MandatoryGrant, DiscretionaryGrant, SelfFunded
    public string StatusCode { get; set; } = "Registered"; // Registered, InProgress, Completed, Terminated, Transferred

    public DateTime RegistrationDate { get; set; } = DateTime.UtcNow;
    public DateTime? CommencementDate { get; set; }
    public DateTime? ExpectedCompletionDate { get; set; }
    public DateTime? CompletionDate { get; set; }

    public string? SetaRegion { get; set; }
    public string? ChamberCode { get; set; }
    public bool IsActive { get; set; } = true;

    public ICollection<LearnerTradeTest> TradeTests { get; set; } = new List<LearnerTradeTest>();
}
