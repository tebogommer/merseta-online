using System;
using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Represents an external partner webhook registration for asynchronous statutory event notifications.
/// </summary>
public class ApiWebhookSubscription : BaseEntity
{
    public int OrganisationId { get; set; }
    public Organisation? Organisation { get; set; }

    public string EventTopic { get; set; } = string.Empty;
    public string TargetUrl { get; set; } = string.Empty;
    
    /// <summary>
    /// Shared secret used to compute HMAC-SHA256 signatures for payload verification.
    /// </summary>
    public string SecretKey { get; set; } = string.Empty;

    public bool IsActive { get; set; } = true;
    public DateTime? LastTriggeredAt { get; set; }
    public int FailureCount { get; set; } = 0;
}
