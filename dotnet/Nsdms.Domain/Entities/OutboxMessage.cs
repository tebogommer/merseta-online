using System;
using System.ComponentModel.DataAnnotations.Schema;
using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Transactional outbox entity recording domain events within the same database transaction.
/// Dispatched reliably by hosted workers to guarantee at-least-once message delivery.
/// </summary>
[Table("OutboxMessage")]
public class OutboxMessage : BaseEntity
{
    /// <summary>
    /// Fully qualified or simple event type name (e.g. WspApprovedEvent).
    /// </summary>
    public string EventType { get; set; } = string.Empty;

    /// <summary>
    /// Serialized JSON payload of the domain event.
    /// </summary>
    public string PayloadJson { get; set; } = string.Empty;

    /// <summary>
    /// Timestamp when this outbox message was successfully dispatched to subscribers.
    /// </summary>
    public DateTime? ProcessedAt { get; set; }

    /// <summary>
    /// Error message captured if dispatch failed during worker execution.
    /// </summary>
    public string? ErrorMessage { get; set; }

    /// <summary>
    /// Number of delivery retry attempts.
    /// </summary>
    public int RetryCount { get; set; } = 0;

    /// <summary>
    /// True if the message has been processed successfully.
    /// </summary>
    public bool IsProcessed => ProcessedAt.HasValue;
}
