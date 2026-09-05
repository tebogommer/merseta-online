using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Vertically partitioned satellite entity isolating special personal information under POPIA:
/// Washington Group Functioning disability difficulty ratings, assessment records, and support notes.
/// </summary>
public class PersonDisabilityRating : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the parent Person record.
    /// </summary>
    public int PersonId { get; set; }

    /// <summary>
    /// Navigational reference to the parent Person record.
    /// </summary>
    public Person? Person { get; set; }

    /// <summary>
    /// Legacy disability classification lookup code (references lookup.DisabilityType: 00 None, 01 Sight, 02 Hearing, etc.).
    /// </summary>
    public string? DisabilityCode { get; set; } = "00";

    #region Washington Group Functioning Difficulty Ratings (SETMIS File 400)
    /// <summary>
    /// Washington Group Seeing functional difficulty rating (references lookup.SeeingRatingType: 01 None to 06 Cannot determine).
    /// </summary>
    public string? SeeingRatingId { get; set; } = "01";

    /// <summary>
    /// Washington Group Hearing functional difficulty rating (references lookup.HearingRatingType: 01 None to 06 Cannot determine).
    /// </summary>
    public string? HearingRatingId { get; set; } = "01";

    /// <summary>
    /// Washington Group Mobility / Walking functional difficulty rating (references lookup.WalkingRatingType: 01 None to 06 Cannot determine).
    /// </summary>
    public string? WalkingRatingId { get; set; } = "01";

    /// <summary>
    /// Washington Group Memory / Cognitive functional difficulty rating (references lookup.RememberingRatingType: 01 None to 06 Cannot determine).
    /// </summary>
    public string? RememberingRatingId { get; set; } = "01";

    /// <summary>
    /// Washington Group Communication functional difficulty rating (references lookup.CommunicatingRatingType: 01 None to 06 Cannot determine).
    /// </summary>
    public string? CommunicatingRatingId { get; set; } = "01";

    /// <summary>
    /// Washington Group Self-Care functional difficulty rating (references lookup.SelfCareRatingType: 01 None to 06 Cannot determine).
    /// </summary>
    public string? SelfCareRatingId { get; set; } = "01";
    #endregion

    /// <summary>
    /// Workplace or learning environment special accommodation and support requirements.
    /// </summary>
    public string? DisabilitySupportNotes { get; set; }

    /// <summary>
    /// Indicates whether a formal medical practitioner or occupational therapist disability assessment was conducted.
    /// </summary>
    public bool IsDisabilityAssessed { get; set; } = false;

    /// <summary>
    /// Date when the formal disability assessment took place.
    /// </summary>
    public DateTime? AssessedDate { get; set; }

    /// <summary>
    /// Name or registration number of the assessing medical practitioner / occupational specialist.
    /// </summary>
    public string? AssessedBy { get; set; }
}
