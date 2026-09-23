namespace Nsdms.Application.Common.Models;

/// <summary>
/// Urgency tier for Mandatory Grant (WSP/ATR) statutory lodgement window countdown and alert states.
/// </summary>
public enum WspUrgencyTier
{
    Upcoming,
    Normal,
    Warning,           // <= 14 days
    Critical,          // <= 72 hours
    ExtensionActive,   // standard window expired but approved extension active
    Closed             // expired, no valid extension
}

/// <summary>
/// Authoritative statutory window status evaluated across global or employer-specific scopes.
/// Option B: The Smart Responsive Ticker.
/// </summary>
public class WspWindowStatusDto
{
    public int SchemeYear { get; set; }
    public DateTime OpeningDate { get; set; }
    public DateTime ClosingDate { get; set; }
    public DateTime EffectiveDeadline { get; set; }
    public bool HasApprovedExtension { get; set; }
    public DateTime? GrantedExtensionDate { get; set; }
    public string? ExtensionReferenceNumber { get; set; }
    public bool IsOpen { get; set; }
    public bool IsUpcoming { get; set; }
    public bool IsClosed { get; set; }
    public TimeSpan TimeRemaining { get; set; }
    public WspUrgencyTier UrgencyTier { get; set; }
    public string StatusBadgeText { get; set; } = string.Empty;
    public string FormattedTimeRemaining { get; set; } = string.Empty;
    public int? OrganisationId { get; set; }

    // Compatibility aliases and supplementary metadata
    public string? OrganisationName { get; set; }
    public DateTime OpeningDateUtc { get => OpeningDate; set => OpeningDate = value; }
    public DateTime StandardClosingDateUtc { get => ClosingDate; set => ClosingDate = value; }
    public DateTime EffectiveDeadlineUtc { get => EffectiveDeadline; set => EffectiveDeadline = value; }
    public DateTime? GrantedExtensionDateUtc { get => GrantedExtensionDate; set => GrantedExtensionDate = value; }
    public string? ExtensionApplicationReference { get => ExtensionReferenceNumber; set => ExtensionReferenceNumber = value; }
    public string DisplayTitle { get => StatusBadgeText; set => StatusBadgeText = value; }
    public string DisplayMessage { get; set; } = string.Empty;
}
