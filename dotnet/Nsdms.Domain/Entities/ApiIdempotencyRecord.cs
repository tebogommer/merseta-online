using System;
using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Persisted record of idempotent API requests to eliminate double-submission hazards on network retry.
/// </summary>
public class ApiIdempotencyRecord : BaseLongEntity
{
    public string IdempotencyKey { get; set; } = string.Empty;
    public string ClientIdentifier { get; set; } = string.Empty;
    public string RequestPath { get; set; } = string.Empty;
    public int ResponseStatusCode { get; set; }
    public string ResponseBodyJson { get; set; } = string.Empty;
    public DateTime ExpiresAt { get; set; }
}
