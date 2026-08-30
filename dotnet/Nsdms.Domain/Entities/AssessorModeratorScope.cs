using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

public class AssessorModeratorScope : BaseEntity
{
    public int EtqaAssessorId { get; set; }
    public EtqaAssessor? EtqaAssessor { get; set; }

    public int SaqaQualificationId { get; set; }
    public string QualificationTitle { get; set; } = string.Empty;
    public string? RegistrationStatusCode { get; set; }
    public DateTime? ExpiryDate { get; set; }
}
