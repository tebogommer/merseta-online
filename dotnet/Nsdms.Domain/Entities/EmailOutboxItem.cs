using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// High-volume persistent email outbox queue item managed by token-bucket rate limiter.
/// Supports decoupling of immediate in-app notifications from throttled outbound SMTP/Office 365 dispatch.
/// </summary>
public class EmailOutboxItem : BaseLongEntity
{
    public int? BroadcastMessageId { get; set; }
    public string RecipientEmail { get; set; } = string.Empty;
    public string? RecipientName { get; set; }
    public string Subject { get; set; } = string.Empty;
    public string BodyHtml { get; set; } = string.Empty;
    public bool HasAttachment { get; set; } = false;
    public string? AttachmentFileName { get; set; }
    public string? AttachmentStoragePath { get; set; }
    public string? AttachmentContentType { get; set; }
    public string Status { get; set; } = "Pending"; // "Pending", "InFlight", "Sent", "Throttled", "Failed"
    public int AttemptCount { get; set; } = 0;
    public int MaxAttempts { get; set; } = 5;
    public DateTime NextAttemptAt { get; set; } = DateTime.UtcNow;
    public DateTime? SentAt { get; set; }
    public string? LastError { get; set; }
    public string SourceModule { get; set; } = "Broadcast"; // "Broadcast", "Workflow", "Notification", "Sla"
    public string? SourceReferenceId { get; set; }
}
