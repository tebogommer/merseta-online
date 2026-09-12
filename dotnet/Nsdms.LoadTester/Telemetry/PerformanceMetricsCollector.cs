using System.Collections.Concurrent;
using System.Diagnostics;

namespace Nsdms.LoadTester.Telemetry;

public record OperationMetric(
    string Module,
    string Operation,
    double ElapsedMs,
    bool Succeeded,
    string? ErrorMessage = null
);

public class PerformanceMetricsCollector
{
    private readonly ConcurrentBag<OperationMetric> _metrics = new();
    private readonly Stopwatch _totalStopwatch = new();

    public void Start() => _totalStopwatch.Restart();
    public void Stop() => _totalStopwatch.Stop();

    public void Record(string module, string operation, double elapsedMs, bool succeeded, string? error = null)
    {
        _metrics.Add(new OperationMetric(module, operation, elapsedMs, succeeded, error));
    }

    public async Task<T> MeasureAsync<T>(string module, string operation, Func<Task<T>> action)
    {
        var sw = Stopwatch.StartNew();
        try
        {
            var result = await action();
            sw.Stop();
            Record(module, operation, sw.Elapsed.TotalMilliseconds, true);
            return result;
        }
        catch (Exception ex)
        {
            sw.Stop();
            Record(module, operation, sw.Elapsed.TotalMilliseconds, false, ex.Message);
            throw;
        }
    }

    public async Task MeasureAsync(string module, string operation, Func<Task> action)
    {
        var sw = Stopwatch.StartNew();
        try
        {
            await action();
            sw.Stop();
            Record(module, operation, sw.Elapsed.TotalMilliseconds, true);
        }
        catch (Exception ex)
        {
            sw.Stop();
            Record(module, operation, sw.Elapsed.TotalMilliseconds, false, ex.Message);
            throw;
        }
    }

    public ModuleSummary GetModuleSummary(string module)
    {
        var items = _metrics.Where(m => m.Module == module).ToList();
        return BuildSummary(module, items);
    }

    public OverallSummary GetOverallSummary()
    {
        var all = _metrics.ToList();
        var byModule = all.GroupBy(m => m.Module)
                          .Select(g => BuildSummary(g.Key, g.ToList()))
                          .ToList();

        var totalSeconds = Math.Max(0.001, _totalStopwatch.Elapsed.TotalSeconds);
        var succeeded = all.Count(m => m.Succeeded);
        var failed = all.Count(m => !m.Succeeded);
        var totalOps = all.Count;

        var latencies = all.Select(m => m.ElapsedMs).OrderBy(x => x).ToList();
        var p50 = GetPercentile(latencies, 50);
        var p90 = GetPercentile(latencies, 90);
        var p95 = GetPercentile(latencies, 95);
        var p99 = GetPercentile(latencies, 99);
        var min = latencies.Count > 0 ? latencies.First() : 0;
        var max = latencies.Count > 0 ? latencies.Last() : 0;
        var avg = latencies.Count > 0 ? latencies.Average() : 0;

        return new OverallSummary(
            TotalOperations: totalOps,
            SucceededOperations: succeeded,
            FailedOperations: failed,
            TotalDurationSeconds: totalSeconds,
            ThroughputOpsPerSec: totalOps / totalSeconds,
            MinLatencyMs: min,
            AvgLatencyMs: avg,
            P50LatencyMs: p50,
            P90LatencyMs: p90,
            P95LatencyMs: p95,
            P99LatencyMs: p99,
            MaxLatencyMs: max,
            ModuleSummaries: byModule
        );
    }

    private static ModuleSummary BuildSummary(string name, List<OperationMetric> items)
    {
        var count = items.Count;
        var succeeded = items.Count(i => i.Succeeded);
        var failed = items.Count(i => !i.Succeeded);
        var latencies = items.Select(i => i.ElapsedMs).OrderBy(x => x).ToList();

        return new ModuleSummary(
            Module: name,
            TotalCount: count,
            SucceededCount: succeeded,
            FailedCount: failed,
            AvgLatencyMs: latencies.Count > 0 ? latencies.Average() : 0,
            P50LatencyMs: GetPercentile(latencies, 50),
            P90LatencyMs: GetPercentile(latencies, 90),
            P95LatencyMs: GetPercentile(latencies, 95),
            P99LatencyMs: GetPercentile(latencies, 99),
            MaxLatencyMs: latencies.Count > 0 ? latencies.Last() : 0
        );
    }

    private static double GetPercentile(List<double> sorted, double percentile)
    {
        if (sorted.Count == 0) return 0;
        if (percentile <= 0) return sorted.First();
        if (percentile >= 100) return sorted.Last();

        var index = (percentile / 100.0) * (sorted.Count - 1);
        var lower = (int)Math.Floor(index);
        var upper = (int)Math.Ceiling(index);
        if (lower == upper) return sorted[lower];

        return sorted[lower] + (sorted[upper] - sorted[lower]) * (index - lower);
    }
}

public record ModuleSummary(
    string Module,
    int TotalCount,
    int SucceededCount,
    int FailedCount,
    double AvgLatencyMs,
    double P50LatencyMs,
    double P90LatencyMs,
    double P95LatencyMs,
    double P99LatencyMs,
    double MaxLatencyMs
);

public record OverallSummary(
    int TotalOperations,
    int SucceededOperations,
    int FailedOperations,
    double TotalDurationSeconds,
    double ThroughputOpsPerSec,
    double MinLatencyMs,
    double AvgLatencyMs,
    double P50LatencyMs,
    double P90LatencyMs,
    double P95LatencyMs,
    double P99LatencyMs,
    double MaxLatencyMs,
    List<ModuleSummary> ModuleSummaries
);
