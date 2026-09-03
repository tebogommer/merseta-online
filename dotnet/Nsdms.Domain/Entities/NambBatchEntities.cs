using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Represents a formal National Artisan Moderation Body (NAMB) moderation and serial allocation submission batch.
/// </summary>
public class NambSubmissionBatch : BaseEntity
{
    /// <summary>
    /// Unique statutory batch reference (e.g. NAMB-2026-B001).
    /// </summary>
    public string BatchReferenceNumber { get; set; } = string.Empty;

    /// <summary>
    /// Submission cycle or intake description.
    /// </summary>
    public string BatchDescription { get; set; } = string.Empty;

    /// <summary>
    /// Date when the batch was submitted to NAMB.
    /// </summary>
    public DateTime SubmissionDate { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Date when NAMB concluded adjudication and serial assignment.
    /// </summary>
    public DateTime? AdjudicationDate { get; set; }

    /// <summary>
    /// Batch processing status (Draft, SubmittedToNamb, Approved, PartiallyApproved, Rejected).
    /// </summary>
    public string Status { get; set; } = "Draft";

    /// <summary>
    /// Total number of trade test candidates included in the batch.
    /// </summary>
    public int TotalCandidates { get; set; }

    /// <summary>
    /// Total candidates approved with NAMB serial allocation.
    /// </summary>
    public int ApprovedCandidates { get; set; }

    /// <summary>
    /// Total candidates queried or rejected by NAMB moderation.
    /// </summary>
    public int RejectedCandidates { get; set; }

    /// <summary>
    /// Official NAMB moderator or committee notes.
    /// </summary>
    public string? NambModeratorNotes { get; set; }

    /// <summary>
    /// Cryptographic SHA-256 seal anchoring candidate records and allocated serials.
    /// </summary>
    public string? DigitalSecuritySeal { get; set; }

    /// <summary>
    /// Collection of candidate trade test applications included in this batch.
    /// </summary>
    public ICollection<LearnerTradeTestApplication> Applications { get; set; } = new List<LearnerTradeTestApplication>();
}
