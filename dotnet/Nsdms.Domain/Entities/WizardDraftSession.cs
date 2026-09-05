using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Transient wizard draft state session. Stores progressive inputs and active step indices
/// to enable seamless resume lifecycle across all 9 statutory enterprise wizards.
/// </summary>
public class WizardDraftSession : BaseEntity
{
    /// <summary>
    /// Unique deterministic draft session key, e.g. "DRAFT-DG-APP-USR101-ORG42" or a GUID.
    /// </summary>
    public string DraftKey { get; set; } = string.Empty;

    /// <summary>
    /// Canonical identifier of the wizard flow (e.g. "DgGrantApplication", "WorkplaceApproval", "AssessorReRegistration").
    /// </summary>
    public string CandidateKey { get; set; } = string.Empty;

    /// <summary>
    /// Human-readable title of the wizard.
    /// </summary>
    public string WizardTitle { get; set; } = string.Empty;

    /// <summary>
    /// Primary entry URL route for the wizard.
    /// </summary>
    public string Route { get; set; } = string.Empty;

    /// <summary>
    /// User identifier owning this in-progress draft.
    /// </summary>
    public string UserId { get; set; } = string.Empty;

    /// <summary>
    /// Optional foreign key referencing the employer/SDP organisation context.
    /// </summary>
    public int? OrganisationId { get; set; }

    /// <summary>
    /// Navigational reference to the linked organisation.
    /// </summary>
    public Organisation? Organisation { get; set; }

    /// <summary>
    /// Active 0-based step index when the draft was saved.
    /// </summary>
    public int CurrentStepIndex { get; set; } = 0;

    /// <summary>
    /// Count of validated and completed steps.
    /// </summary>
    public int CompletedStepCount { get; set; } = 0;

    /// <summary>
    /// Total number of steps in this wizard.
    /// </summary>
    public int TotalStepCount { get; set; } = 5;

    /// <summary>
    /// Serialized JSON snapshot of the wizard's state/model.
    /// </summary>
    public string DraftModelJson { get; set; } = "{}";

    /// <summary>
    /// Lifecycle status of the draft session ("Active", "Submitted", "Discarded", "Expired").
    /// </summary>
    public string Status { get; set; } = "Active";

    /// <summary>
    /// Expiration timestamp in UTC after which the draft is purged (default: 30 days).
    /// </summary>
    public DateTime ExpiresAtUtc { get; set; } = DateTime.UtcNow.AddDays(30);

    /// <summary>
    /// Indicates whether the draft is currently active and eligible for resume.
    /// </summary>
    public bool IsActive { get; set; } = true;
}
