using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Represents a distributed concurrency lock lease on a workflow task to prevent simultaneous claims and race conditions.
/// </summary>
[Table("WorkflowTaskLease")]
public class WorkflowTaskLease
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public int Id { get; set; }

    public int WorkflowTaskId { get; set; }

    public int ClaimedByUserId { get; set; }

    [MaxLength(150)]
    public string ClaimedByUserName { get; set; } = string.Empty;

    public DateTime LeaseStartTime { get; set; } = DateTime.UtcNow;
    public DateTime LeaseExpiryTime { get; set; }
    public DateTime LastHeartbeatTime { get; set; } = DateTime.UtcNow;

    public bool IsReleased { get; set; } = false;

    // Audit columns
    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    public string CreatedBy { get; set; } = "SYSTEM";
    public DateTime? ModifiedAt { get; set; }
    public string? ModifiedBy { get; set; }
}
