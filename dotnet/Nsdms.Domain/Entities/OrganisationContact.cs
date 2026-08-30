using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Liaison contact person link associating an individual Person with an Employer Organisation.
/// </summary>
public class OrganisationContact : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the parent Employer Organisation.
    /// </summary>
    public int OrganisationId { get; set; }

    /// <summary>
    /// Navigational reference to the parent Organisation.
    /// </summary>
    public Organisation? Organisation { get; set; }

    /// <summary>
    /// Foreign key referencing the individual Person demographic profile.
    /// </summary>
    public int PersonId { get; set; }

    /// <summary>
    /// Navigational reference to the individual Person.
    /// </summary>
    public Person? Person { get; set; }

    /// <summary>
    /// Contact role classification code (e.g. Primary, SDF, Secondary, Financial, HR, TrainingManager).
    /// </summary>
    public string? ContactTypeCode { get; set; } = "General";

    /// <summary>
    /// Alias property exposing ContactTypeCode.
    /// </summary>
    public string ContactType 
    { 
        get => ContactTypeCode ?? "General"; 
        set => ContactTypeCode = value; 
    }

    /// <summary>
    /// Job title or professional designation within the enterprise.
    /// </summary>
    public string? Designation { get; set; }

    /// <summary>
    /// Indicates whether this individual is the primary designated contact for official notices.
    /// </summary>
    public bool IsPrimary { get; set; } = false;

    /// <summary>
    /// Indicates whether this contact link is currently active.
    /// </summary>
    public bool IsActive { get; set; } = true;
}
