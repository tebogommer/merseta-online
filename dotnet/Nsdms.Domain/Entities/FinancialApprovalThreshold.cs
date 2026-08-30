using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Represents a tiered financial delegation limit (DoA) enforcing role-based threshold caps on grants and disbursements.
/// </summary>
[Table("FinancialApprovalThreshold")]
public class FinancialApprovalThreshold
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public int Id { get; set; }

    [MaxLength(100)]
    public string RoleName { get; set; } = string.Empty;

    [MaxLength(50)]
    public string ModuleCode { get; set; } = "Grants"; // Grants, MandatoryGrant, InterSeta, etc.

    [MaxLength(100)]
    public string ApprovalLevelName { get; set; } = "Level 1"; // Tier 1, Tier 2, Executive, Board

    [Column(TypeName = "decimal(18,2)")]
    public decimal MaxApprovalAmount { get; set; }

    public bool RequiresBoardApproval { get; set; } = false;

    [MaxLength(500)]
    public string Description { get; set; } = string.Empty;

    public bool IsActive { get; set; } = true;

    // Audit columns
    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    public string CreatedBy { get; set; } = "SYSTEM";
    public DateTime? ModifiedAt { get; set; }
    public string? ModifiedBy { get; set; }
}
