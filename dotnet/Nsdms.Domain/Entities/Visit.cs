using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// On-site monitoring, workplace inspection, and employer liaison visits scheduled and executed by MerSETA staff.
/// </summary>
public class Visit : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the host or inspected Employer Organisation.
    /// </summary>
    public int OrganisationId { get; set; }

    /// <summary>
    /// Navigational reference to the visited Organisation.
    /// </summary>
    public Organisation? Organisation { get; set; }

    /// <summary>
    /// Mandatory foreign key referencing the employer's designated Contact Person attending the visit.
    /// </summary>
    public int ContactPersonId { get; set; }

    /// <summary>
    /// Navigational reference to the designated Contact Person.
    /// </summary>
    public Person? ContactPerson { get; set; }

    /// <summary>
    /// Classification code of the visit activity (e.g. WORKPLACE_APPROVAL, MONITORING, VERIFICATION, SDF_LIAISON).
    /// </summary>
    public string? VisitTypeCode { get; set; }

    /// <summary>
    /// Current execution status code of the visit (e.g. Scheduled, InProgress, Completed, Cancelled).
    /// </summary>
    public string? VisitStatusCode { get; set; }

    /// <summary>
    /// Scheduled date and time of the visit activity.
    /// </summary>
    public DateTime VisitDate { get; set; }

    /// <summary>
    /// Concise summary title or subject of the visit.
    /// </summary>
    public string Title { get; set; } = string.Empty;

    /// <summary>
    /// Detailed statutory or operational purpose for conducting the visit.
    /// </summary>
    public string? Purpose { get; set; }

    /// <summary>
    /// Official findings, remediation recommendations, and outcome notes recorded by the visiting officer.
    /// </summary>
    public string? OutcomeNotes { get; set; }

    /// <summary>
    /// Physical location, facility branch, or site address where the visit was held.
    /// </summary>
    public string? Location { get; set; }
}
