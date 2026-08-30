using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

public class TrainingProviderUnitStandard : BaseEntity
{
    public int TrainingProviderId { get; set; }
    public TrainingProvider? TrainingProvider { get; set; }

    public int UnitStandardId { get; set; }
    public string UnitStandardTitle { get; set; } = string.Empty;
    public int? NqfLevel { get; set; }
    public int Credits { get; set; }
}
