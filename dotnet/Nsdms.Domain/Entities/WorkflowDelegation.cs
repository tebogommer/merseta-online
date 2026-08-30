using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Represents a time-bounded role and module delegation from one user to another (e.g., leave, acting capacity).
/// </summary>
[Table("WorkflowDelegation")]
public class WorkflowDelegation
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public int Id { get; set; }

    public int DelegatorUserId { get; set; }
    public string DelegatorUserName { get; set; } = string.Empty;

    public int DelegateeUserId { get; set; }
    public string DelegateeUserName { get; set; } = string.Empty;

    public DateTime StartDate { get; set; }
    public DateTime EndDate { get; set; }

    /// <summary>
    /// JSON array of allowed module codes e.g. ["Grants", "Wsp", "Etqa"] or ["*"] for full authority.
    /// </summary>
    public string AllowedModulesJson { get; set; } = "[\"*\"]";

    [MaxLength(500)]
    public string Reason { get; set; } = string.Empty;

    public bool IsActive { get; set; } = true;

    // Audit columns
    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    public string CreatedBy { get; set; } = "SYSTEM";
    public DateTime? ModifiedAt { get; set; }
    public string? ModifiedBy { get; set; }
}
