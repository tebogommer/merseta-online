using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

public class WspEmploymentSummary : BaseEntity
{
    public int WspSubmissionId { get; set; }
    public WspSubmission? WspSubmission { get; set; }

    public string? OfoCode { get; set; }
    public string? OccupationalCategory { get; set; }

    public int MaleAfrican { get; set; }
    public int FemaleAfrican { get; set; }
    public int MaleColoured { get; set; }
    public int FemaleColoured { get; set; }
    public int MaleIndian { get; set; }
    public int FemaleIndian { get; set; }
    public int MaleWhite { get; set; }
    public int FemaleWhite { get; set; }
    public int DisabledCount { get; set; }
    public int TotalEmployees { get; set; }
}
