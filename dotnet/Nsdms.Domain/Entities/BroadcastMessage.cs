using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Persistent broadcast campaign representing an official circular, executive communication, or mass notification.
/// </summary>
public class BroadcastMessage : BaseEntity
{
    public string Subject { get; set; } = string.Empty;
    public string BodyHtml { get; set; } = string.Empty;
    public string TargetType { get; set; } = "Role"; // "Role", "Organisation", "SpecificUser", "BroadcastAll"
    public string? TargetFilterValue { get; set; } // Role code, OrganisationId string, or specific username
    public string? TargetFilterDisplay { get; set; } // User-facing description e.g. "SDF", "Toyota South Africa Motors", etc.
    public int RecipientCount { get; set; } = 0;
    public bool HasAttachment { get; set; } = false;
    public string? AttachmentFileName { get; set; }
    public string? AttachmentStoragePath { get; set; }
    public long? AttachmentSizeBytes { get; set; }
    public string? AttachmentContentType { get; set; }
    public string Status { get; set; } = "Dispatched"; // "Draft", "Dispatched", "Cancelled"
    public DateTime DispatchedAt { get; set; } = DateTime.UtcNow;
    public string DispatchedBy { get; set; } = "SYSTEM";
}
