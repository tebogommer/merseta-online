using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public interface IAnalyticsService
{
    Task<List<SspChamberMetricDto>> GetSspChamberMetricsAsync();
    Task<List<SspEquityMetricDto>> GetSspEquityMetricsAsync();
    Task<List<SspProvincialMetricDto>> GetSspProvincialMetricsAsync();
    Task<List<SspScarceSkillDto>> GetSspScarceSkillsAsync();
    Task<(decimal TotalCommitted, decimal TotalDisbursed, decimal TotalRebates)> GetFinancialOverviewAsync();
    Task<List<ChamberGrantFinancialSummaryDto>> GetChamberGrantFinancialSummaryAsync(string? schemeYear = null);
    Task<ExecutiveDashboardSummaryDto> GetExecutiveDashboardSummaryAsync();
}

public class ExecutiveDashboardSummaryDto
{
    public int EmployerCount { get; set; }
    public int LearnerCount { get; set; }
    public int ProviderCount { get; set; }
    public int GrantCount { get; set; }
    public List<DashboardActionItemDto> ActionItems { get; set; } = new();
}

public class DashboardActionItemDto
{
    public string EntityTitle { get; set; } = string.Empty;
    public string ReferenceNumber { get; set; } = string.Empty;
    public string ProcessName { get; set; } = string.Empty;
    public string CurrentStage { get; set; } = string.Empty;
    public int DaysRemaining { get; set; }
    public bool IsDueSoon { get; set; }
    public bool IsOverdue { get; set; }
    public string TargetUrl { get; set; } = string.Empty;
}
