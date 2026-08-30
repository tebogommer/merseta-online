using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Tool, equipment, or health and safety checklist item inspected for workplace qualification approval.
/// </summary>
public class WorkplaceApprovalToolList : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the parent WorkplaceApproval.
    /// </summary>
    public int WorkplaceApprovalId { get; set; }

    /// <summary>
    /// Navigational reference to the parent WorkplaceApproval.
    /// </summary>
    public WorkplaceApproval? WorkplaceApproval { get; set; }

    /// <summary>
    /// Name / description of the required tool, machinery, or PPE safety item.
    /// </summary>
    public string ToolName { get; set; } = string.Empty;

    /// <summary>
    /// Tool category classification (e.g. Mechanical, Electrical, Welding, Safety, PPE).
    /// </summary>
    public string? Category { get; set; }

    /// <summary>
    /// Minimum required quantity specified in the trade training curriculum regulations.
    /// </summary>
    public int RequiredQuantity { get; set; } = 1;

    /// <summary>
    /// Actual operational quantity verified during the on-site physical inspection.
    /// </summary>
    public int AvailableQuantity { get; set; } = 0;

    /// <summary>
    /// Indicates whether the available equipment satisfies statutory curriculum quotas.
    /// </summary>
    public bool IsCompliant => AvailableQuantity >= RequiredQuantity;

    /// <summary>
    /// Auditor remarks on equipment condition, calibration, or servicing status.
    /// </summary>
    public string? Remarks { get; set; }
}
