using System.Diagnostics;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Nsdms.LoadTester.Data;
using Nsdms.LoadTester.Scenarios;
using Nsdms.LoadTester.Telemetry;

namespace Nsdms.LoadTester.Engine;

public record LoadTestConfig(
    int TotalUsers,
    int TotalLearners,
    int TotalDgApplications,
    int TotalMgSubmissions,
    int TotalSdps,
    int MaxConcurrency = 48
);

public class LoadTestOrchestrator
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly PerformanceMetricsCollector _metrics;
    private readonly WspSubmissionWorker _wspWorker;
    private readonly DgApplicationWorker _dgWorker;
    private readonly SdpProviderWorker _sdpWorker;
    private readonly LearnerLifecycleWorker _learnerWorker;

    public LoadTestOrchestrator(
        INsdmsDbContextFactory contextFactory,
        PerformanceMetricsCollector metrics)
    {
        _contextFactory = contextFactory;
        _metrics = metrics;
        _wspWorker = new WspSubmissionWorker(contextFactory, metrics);
        _dgWorker = new DgApplicationWorker(contextFactory, metrics);
        _sdpWorker = new SdpProviderWorker(contextFactory, metrics);
        _learnerWorker = new LearnerLifecycleWorker(contextFactory, metrics);
    }

    public async Task<OverallSummary> RunBenchmarkAsync(LoadTestConfig config, Action<string>? logger = null)
    {
        logger ??= Console.WriteLine;
        logger("====================================================================================");
        logger("🚀 merSETA NSDMS ENTERPRISE LOAD & PERFORMANCE TEST HARNESS");
        logger($"Simulated Users: {config.TotalUsers:N0} (SDFs, SDPs, merSETA Officers)");
        logger($"Workload Target: {config.TotalLearners:N0} Learners | {config.TotalDgApplications:N0} DGs | {config.TotalMgSubmissions:N0} MGs | {config.TotalSdps:N0} SDPs");
        logger($"Database Concurrency Degree: {config.MaxConcurrency} parallel workers");
        logger("====================================================================================");

        // Pre-flight: Seed or acquire base organisations and funding window
        logger("\n[Phase 0] Initialising pre-flight baseline entities...");
        var (orgIds, fundingWindowId) = await InitializeBaselineAsync(logger);
        logger($"[Phase 0] Baseline ready: {orgIds.Count} reference organisations, Funding Window #{fundingWindowId}.");

        _metrics.Start();
        var sw = Stopwatch.StartNew();

        // 1. SDP Provider Registrations
        logger($"\n[Phase 1] Executing {config.TotalSdps:N0} SDP Provider Registrations & Accreditations...");
        var sdpIds = await ExecuteBatchAsync(config.TotalSdps, config.MaxConcurrency, async (index) =>
        {
            var username = $"SDP_OFFICER_{index % 500:D4}";
            return await _sdpWorker.ExecuteProviderRegistrationAsync(index, username);
        }, "SDP_Registrations", logger);

        // 2. Mandatory Grant (WSP) Submissions
        logger($"\n[Phase 2] Executing {config.TotalMgSubmissions:N0} Mandatory Grant (WSP/ATR) Submissions...");
        await ExecuteBatchAsync(config.TotalMgSubmissions, config.MaxConcurrency, async (index) =>
        {
            var orgId = orgIds[index % orgIds.Count];
            var username = $"SDF_MG_{index % 2000:D4}";
            return await _wspWorker.ExecuteSubmissionAsync(index, orgId, username);
        }, "WSP_Submissions", logger);

        // 3. Discretionary Grant (DG) Applications
        logger($"\n[Phase 3] Executing {config.TotalDgApplications:N0} Discretionary Grant Applications & Budgets...");
        await ExecuteBatchAsync(config.TotalDgApplications, config.MaxConcurrency, async (index) =>
        {
            var orgId = orgIds[index % orgIds.Count];
            var username = $"SDF_DG_{index % 2500:D4}";
            return await _dgWorker.ExecuteApplicationAsync(index, orgId, fundingWindowId, username);
        }, "DG_Applications", logger);

        // 4. High-Volume Learner Lifecycle Enrolments & Transitions
        logger($"\n[Phase 4] Executing {config.TotalLearners:N0} Learner Enrolments & Status Transitions...");
        var trainingProviderId = sdpIds.Count > 0 ? sdpIds[0] : 1;
        await ExecuteBatchAsync(config.TotalLearners, config.MaxConcurrency, async (index) =>
        {
            var orgId = orgIds[index % orgIds.Count];
            var providerId = sdpIds.Count > 0 ? sdpIds[index % sdpIds.Count] : trainingProviderId;
            var username = $"LEARNER_OFFICER_{index % 1000:D4}";
            return await _learnerWorker.ExecuteEnrolmentAndTransitionAsync(index, orgId, providerId, username);
        }, "Learner_Mutations", logger);

        sw.Stop();
        _metrics.Stop();

        var summary = _metrics.GetOverallSummary();
        logger("\n====================================================================================");
        logger("🏁 BENCHMARK COMPLETED SUCCESSFULLY");
        logger($"Total Transactions Executed: {summary.TotalOperations:N0}");
        logger($"Total Elapsed Time: {summary.TotalDurationSeconds:F2} seconds");
        logger($"Overall Throughput: {summary.ThroughputOpsPerSec:F2} operations/second");
        logger($"Mean Latency: {summary.AvgLatencyMs:F2} ms | p50: {summary.P50LatencyMs:F2} ms | p95: {summary.P95LatencyMs:F2} ms | p99: {summary.P99LatencyMs:F2} ms");
        logger($"Success Rate: {summary.SucceededOperations:N0} / {summary.TotalOperations:N0} ({(summary.SucceededOperations * 100.0 / Math.Max(1, summary.TotalOperations)):F2}%)");
        logger("====================================================================================");

        return summary;
    }

    private async Task<List<int>> ExecuteBatchAsync(
        int totalItems,
        int maxConcurrency,
        Func<int, Task<int>> itemAction,
        string phaseName,
        Action<string> logger)
    {
        var semaphore = new SemaphoreSlim(maxConcurrency);
        var completed = 0;
        var results = new List<int>();
        var lockObj = new object();
        var progressStopwatch = Stopwatch.StartNew();

        var tasks = Enumerable.Range(1, totalItems).Select(async (index) =>
        {
            await semaphore.WaitAsync();
            try
            {
                var result = await itemAction(index);
                lock (lockObj)
                {
                    results.Add(result);
                    completed++;
                    if (completed % Math.Max(1, totalItems / 20) == 0 || completed == totalItems)
                    {
                        var rate = completed / Math.Max(0.01, progressStopwatch.Elapsed.TotalSeconds);
                        logger($"    [{phaseName}] Progress: {completed:N0} / {totalItems:N0} ({(completed * 100.0 / totalItems):F1}%) | Rate: {rate:F1} ops/sec");
                    }
                }
            }
            catch (Exception ex)
            {
                logger($"    [{phaseName}] ERROR on item #{index}: {ex.Message}");
            }
            finally
            {
                semaphore.Release();
            }
        });

        await Task.WhenAll(tasks);
        return results;
    }

    private async Task<(List<int> OrgIds, int FundingWindowId)> InitializeBaselineAsync(Action<string> logger)
    {
        await using var context = _contextFactory.CreateDbContext();

        // 1. Get existing or create base employer organisations
        var orgs = await context.Organisations.Where(o => o.OrganisationStatusCode == "ACTIVE").Take(100).ToListAsync();
        if (orgs.Count < 20)
        {
            for (int i = orgs.Count + 1; i <= 25; i++)
            {
                var (legal, trading, reg, chamber) = StatutorySyntheticDataGenerator.GenerateOrganisation(i);
                var newOrg = new Organisation
                {
                    CompanyName = legal,
                    TradingName = trading,
                    RegistrationNumber = reg,
                    SdlNumber = StatutorySyntheticDataGenerator.GenerateSdlNumber(i),
                    ChamberCode = chamber,
                    OrganisationStatusCode = "ACTIVE",
                    LevyCategoryCode = "LEVY_PAYING",
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = "BENCHMARK_SEED"
                };
                context.Organisations.Add(newOrg);
            }
            await context.SaveChangesAsync();
            orgs = await context.Organisations.Where(o => o.OrganisationStatusCode == "ACTIVE").Take(100).ToListAsync();
        }

        // 2. Get existing or create active Discretionary Grant Funding Window
        var window = await context.GrantFundingWindows.FirstOrDefaultAsync(w => w.IsActive);
        if (window == null)
        {
            window = new GrantFundingWindow
            {
                WindowName = "2026/2027 Discretionary Grant Strategic Window",
                FinYear = 2026,
                OpeningDate = DateTime.UtcNow.AddMonths(-1),
                ClosingDate = DateTime.UtcNow.AddMonths(2),
                TotalAvailableBudget = 150000000m,
                IsActive = true,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = "BENCHMARK_SEED"
            };
            context.GrantFundingWindows.Add(window);
            await context.SaveChangesAsync();
        }

        return (orgs.Select(o => o.Id).ToList(), window.Id);
    }
}
