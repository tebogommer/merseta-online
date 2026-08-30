using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Persistent system notification for real-time SignalR push and user inbox alerts.
/// </summary>
public class SystemNotification : BaseEntity
{
    public string? RecipientUsername { get; set; }
    public string? RecipientRole { get; set; }
    public string Title { get; set; } = string.Empty;
    public string Message { get; set; } = string.Empty;
    public string? ActionUrl { get; set; }
    public string NotificationType { get; set; } = "SystemAlert";
    public string Severity { get; set; } = "Info";
    public bool IsRead { get; set; } = false;
    public DateTime? ReadAt { get; set; }
}

public class SystemNotificationDto
{
    public int Id { get; set; }
    public string? RecipientUsername { get; set; }
    public string? RecipientRole { get; set; }
    public string Title { get; set; } = string.Empty;
    public string Message { get; set; } = string.Empty;
    public string? ActionUrl { get; set; }
    public string NotificationType { get; set; } = "SystemAlert";
    public string Severity { get; set; } = "Info";
    public bool IsRead { get; set; }
    public DateTime? ReadAt { get; set; }
    public DateTime CreatedAt { get; set; }
}
