using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

public class TrainingProviderQualification : BaseEntity
{
    public int TrainingProviderId { get; set; }
    public TrainingProvider? TrainingProvider { get; set; }

    public int SaqaQualificationId { get; set; }
    public string QualificationTitle { get; set; } = string.Empty;
    public int? NqfLevel { get; set; }
    public string? AccreditationStatusCode { get; set; }
    public DateTime? ExpiryDate { get; set; }
}
