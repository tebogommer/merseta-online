using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public interface IAnalyticsService
{
    Task<List<SspChamberMetricDto>> GetSspChamberMetricsAsync();
    Task<List<SspEquityMetricDto>> GetSspEquityMetricsAsync();
    Task<List<SspProvincialMetricDto>> GetSspProvincialMetricsAsync();
    Task<List<SspScarceSkillDto>> GetSspScarceSkillsAsync();
    Task<(decimal TotalCommitted, decimal TotalDisbursed, decimal TotalRebates)> GetFinancialOverviewAsync();
}
