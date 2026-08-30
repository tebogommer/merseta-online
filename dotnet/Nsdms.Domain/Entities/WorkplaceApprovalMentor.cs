using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Qualified artisan mentor assigned to supervise apprentices and learners at an approved workplace.
/// </summary>
public class WorkplaceApprovalMentor : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the parent WorkplaceApproval.
    /// </summary>
    public int WorkplaceApprovalId { get; set; }

    /// <summary>
    /// Navigational reference to the parent WorkplaceApproval.
    /// </summary>
    public WorkplaceApproval? WorkplaceApproval { get; set; }

    /// <summary>
    /// Foreign key referencing the mentor's demographic Person record.
    /// </summary>
    public int PersonId { get; set; }

    /// <summary>
    /// Navigational reference to the mentor Person record.
    /// </summary>
    public Person? Person { get; set; }

    /// <summary>
    /// Enterprise job title / role designation (e.g. Master Artisan, Lead Fitter, Foreman).
    /// </summary>
    public string Designation { get; set; } = string.Empty;

    /// <summary>
    /// Red Seal Artisan Trade Certificate serial number.
    /// </summary>
    public string? ArtisanTradeNumber { get; set; }

    /// <summary>
    /// Number of verified years of post-apprenticeship industry experience.
    /// </summary>
    public int YearsExperience { get; set; }

    /// <summary>
    /// Indicates whether the mentor is certified as a qualified Red Seal artisan.
    /// </summary>
    public bool IsCertifiedArtisan { get; set; } = true;

    /// <summary>
    /// Indicates whether the mentor is actively mentoring learners at this site.
    /// </summary>
    public bool IsActive { get; set; } = true;
}
