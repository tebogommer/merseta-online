using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Tracks individual Unit Standards or Skills Sets registered against a CompanyLearner
/// for Skills Programme and Unit Standard enrolments (SETMIS File 503).
/// </summary>
public class LearnerRegisteredUnitStandard : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the parent CompanyLearner record.
    /// </summary>
    public int CompanyLearnerId { get; set; }

    /// <summary>
    /// Navigational reference to the parent CompanyLearner record.
    /// </summary>
    public CompanyLearner? CompanyLearner { get; set; }

    /// <summary>
    /// SAQA Unit Standard ID number (e.g. 116937 or 9964).
    /// </summary>
    public int UnitStandardId { get; set; }

    /// <summary>
    /// Official SAQA descriptive title of the unit standard.
    /// </summary>
    public string UnitStandardTitle { get; set; } = string.Empty;

    /// <summary>
    /// NDF level of the unit standard (e.g. 2, 3, 4, 5).
    /// </summary>
    public int? NqfLevel { get; set; }

    /// <summary>
    /// Number of credits attached to this unit standard.
    /// </summary>
    public int? Credits { get; set; }

    /// <summary>
    /// Indicates whether this unit standard is Core, Fundamental, or Elective.
    /// </summary>
    public bool IsCore { get; set; } = true;

    /// <summary>
    /// Current enrolment / competency status code (e.g. Enrolled, Competent, NotYetCompetent).
    /// </summary>
    public string StatusCode { get; set; } = "Enrolled";
}
