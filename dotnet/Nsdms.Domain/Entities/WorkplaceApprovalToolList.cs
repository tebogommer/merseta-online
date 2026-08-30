using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

public class WorkplaceApprovalToolList : BaseEntity
{
    public int WorkplaceApprovalId { get; set; }
    public WorkplaceApproval? WorkplaceApproval { get; set; }

    public string ToolName { get; set; } = string.Empty;
    public string? Category { get; set; } // Mechanical, Electrical, Welding, Safety, PPE
    public int RequiredQuantity { get; set; } = 1;
    public int AvailableQuantity { get; set; } = 0;
    public bool IsCompliant => AvailableQuantity >= RequiredQuantity;
    public string? Remarks { get; set; }
}
