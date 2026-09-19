using System;
using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// High-volume audit record of outbound webhook delivery attempts, HTTP responses, and error traces.
/// </summary>
public class ApiWebhookDeliveryLog : BaseLongEntity
{
    public int SubscriptionId { get; set; }
    public ApiWebhookSubscription? Subscription { get; set; }

    public string EventTopic { get; set; } = string.Empty;
    public string PayloadJson { get; set; } = string.Empty;
    public int AttemptNumber { get; set; } = 1;
    public int? HttpStatusCode { get; set; }
    public bool IsSuccess { get; set; } = false;
    public string? ErrorMessage { get; set; }
    public DateTime DeliveredAt { get; set; } = DateTime.UtcNow;
}
