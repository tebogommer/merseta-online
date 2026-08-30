using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Employment profile demographic breakdown by occupational category (SETMIS Form 500).
/// </summary>
public class WspEmploymentSummary : BaseEntity
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
    /// Organising Framework for Occupations (OFO) 6-digit classification code.
    /// </summary>
    public string? OfoCode { get; set; }

    /// <summary>
    /// Major occupational level title (e.g. Managers, Professionals, Technicians, Clerical, Artisans, Elementary).
    /// </summary>
    public string? OccupationalCategory { get; set; }

    /// <summary>
    /// Headcount of African male employees in this occupational level.
    /// </summary>
    public int MaleAfrican { get; set; }

    /// <summary>
    /// Headcount of African female employees in this occupational level.
    /// </summary>
    public int FemaleAfrican { get; set; }

    /// <summary>
    /// Headcount of Coloured male employees.
    /// </summary>
    public int MaleColoured { get; set; }

    /// <summary>
    /// Headcount of Coloured female employees.
    /// </summary>
    public int FemaleColoured { get; set; }

    /// <summary>
    /// Headcount of Indian male employees.
    /// </summary>
    public int MaleIndian { get; set; }

    /// <summary>
    /// Headcount of Indian female employees.
    /// </summary>
    public int FemaleIndian { get; set; }

    /// <summary>
    /// Headcount of White male employees.
    /// </summary>
    public int MaleWhite { get; set; }

    /// <summary>
    /// Headcount of White female employees.
    /// </summary>
    public int FemaleWhite { get; set; }

    /// <summary>
    /// Total headcount of employees declared with disabilities.
    /// </summary>
    public int DisabledCount { get; set; }

    /// <summary>
    /// Total headcount sum of all employees in this category.
    /// </summary>
    public int TotalEmployees { get; set; }
}
