using System;
using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Persistent database ledger for background jobs, providing durability
/// across application pool recycles and container restarts in compliance with
/// Enterprise Background Job Pipeline & Observability Standards.
/// </summary>
public class BackgroundJobJournal : BaseEntity
{
    public Guid JobGuid { get; set; }
    public string JobType { get; set; } = string.Empty;
    public string Description { get; set; } = string.Empty;
    public string Status { get; set; } = "Queued";
    public int ProgressPercentage { get; set; }
    public string? CurrentStep { get; set; }
    public string RequestedBy { get; set; } = "SYSTEM";
    public string? PayloadJson { get; set; }
    public string? ErrorMessage { get; set; }
    public string? ResultFileName { get; set; }
    public string? ResultContentType { get; set; }
    public DateTime? StartedAt { get; set; }
    public DateTime? CompletedAt { get; set; }
}
