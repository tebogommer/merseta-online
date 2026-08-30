using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

public class GrantProjectBudget : BaseEntity
{
    public int GrantApplicationId { get; set; }
    public GrantApplication? GrantApplication { get; set; }

    public string ExpenseCategory { get; set; } = string.Empty;
    public string? Description { get; set; }
    public decimal UnitCost { get; set; }
    public int Quantity { get; set; }
    public decimal TotalCost { get; set; }
}
