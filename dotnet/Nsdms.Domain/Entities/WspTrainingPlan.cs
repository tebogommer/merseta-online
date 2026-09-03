using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Planned learning and development interventions submitted in the annual Workplace Skills Plan.
/// </summary>
public class WspTrainingPlan : BaseLongEntity
{
    /// <summary>
    /// Foreign key referencing the parent WspSubmission.
    /// </summary>
    public int WspSubmissionId { get; set; }

    /// <summary>
    /// Navigational reference to the parent WspSubmission.
    /// </summary>
    public WspSubmission? WspSubmission { get; set; }

    /// <summary>
    /// Learning programme type code (e.g. Learnership, Apprenticeship, SkillsProgramme, ShortCourse).
    /// </summary>
    public string? ProgrammeTypeCode { get; set; }

    /// <summary>
    /// Targeted National Qualifications Framework (NQF) level descriptor.
    /// </summary>
    public int? NqfLevel { get; set; }

    /// <summary>
    /// Total number of planned employee / unemployed beneficiaries.
    /// </summary>
    public int BeneficiaryCount { get; set; }

    /// <summary>
    /// Total estimated investment cost budgeted for delivery in ZAR.
    /// </summary>
    public decimal EstimatedCost { get; set; }
}
