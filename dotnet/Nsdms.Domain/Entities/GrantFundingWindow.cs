using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

public class GrantFundingWindow : BaseEntity
{
    public int FinYear { get; set; }
    public string WindowName { get; set; } = string.Empty;
    public string? GrantTypeCode { get; set; }
    public DateTime OpeningDate { get; set; }
    public DateTime ClosingDate { get; set; }
    public decimal TotalAvailableBudget { get; set; }
    public bool IsActive { get; set; } = true;
}
