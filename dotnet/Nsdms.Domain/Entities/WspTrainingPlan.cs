using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

public class WspTrainingPlan : BaseEntity
{
    public int WspSubmissionId { get; set; }
    public WspSubmission? WspSubmission { get; set; }

    public string? ProgrammeTypeCode { get; set; }
    public int? NqfLevel { get; set; }
    public int BeneficiaryCount { get; set; }
    public decimal EstimatedCost { get; set; }
}
