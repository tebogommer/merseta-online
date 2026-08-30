using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

public class TrainingProvider : BaseEntity
{
    public int OrganisationId { get; set; }
    public Organisation? Organisation { get; set; }

    public string AccreditationNumber { get; set; } = string.Empty;
    public DateTime? AccreditationStartDate { get; set; }
    public DateTime? AccreditationEndDate { get; set; }
    public string? ProviderTypeCode { get; set; }
    public string? ProviderStatusCode { get; set; }
    public string? EtqaDecisionNumber { get; set; }
    public int? MaxLearnerCapacity { get; set; }

    public int? PrimaryContactPersonId { get; set; }
    public Person? PrimaryContactPerson { get; set; }

    public bool IsActive { get; set; } = true;

    public ICollection<TrainingProviderQualification> Qualifications { get; set; } = new List<TrainingProviderQualification>();
    public ICollection<TrainingProviderUnitStandard> UnitStandards { get; set; } = new List<TrainingProviderUnitStandard>();
}
