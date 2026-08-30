using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Represents a cryptographic digital signoff attestation captured during approval/rejection milestones for AGSA and King IV compliance.
/// </summary>
[Table("WorkflowSignoffAttestation")]
public class WorkflowSignoffAttestation
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public int Id { get; set; }

    public int? WorkflowTaskId { get; set; }

    [MaxLength(100)]
    public string EntityName { get; set; } = string.Empty;

    public int EntityId { get; set; }

    public int SignerUserId { get; set; }

    [MaxLength(150)]
    public string SignerName { get; set; } = string.Empty;

    [MaxLength(20)]
    public string SignerRsaId { get; set; } = string.Empty;

    [MaxLength(100)]
    public string SignoffRole { get; set; } = string.Empty;

    [MaxLength(50)]
    public string SignoffAction { get; set; } = "Approved";

    /// <summary>
    /// Hex-encoded SHA-256 hash of the payload / document snapshot at signoff time.
    /// </summary>
    [MaxLength(128)]
    public string DocumentSha256Checksum { get; set; } = string.Empty;

    [MaxLength(1000)]
    public string AttestationNotes { get; set; } = string.Empty;

    public DateTime SignedAt { get; set; } = DateTime.UtcNow;

    // Audit columns
    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    public string CreatedBy { get; set; } = "SYSTEM";
    public DateTime? ModifiedAt { get; set; }
    public string? ModifiedBy { get; set; }
}
