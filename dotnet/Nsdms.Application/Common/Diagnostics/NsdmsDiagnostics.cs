using System.Diagnostics;
using System.Diagnostics.Metrics;

namespace Nsdms.Application.Common.Diagnostics;

/// <summary>
/// Enterprise APM & OpenTelemetry instrumentation for merSETA NSDMS.
/// Exposes standardized .NET 10 Meters, Counters, Histograms, and ActivitySource spans.
/// </summary>
public static class NsdmsDiagnostics
{
    public const string MeterName = "MerSETA.Nsdms";
    public const string MeterVersion = "1.0.0";
    public const string SourceName = "MerSETA.Nsdms";

    public static readonly Meter Meter = new(MeterName, MeterVersion);
    public static readonly ActivitySource Source = new(SourceName, MeterVersion);

    // Statutory Submissions Counter
    public static readonly Counter<long> StatutorySubmissions = Meter.CreateCounter<long>(
        "nsdms_statutory_submissions_total",
        unit: "{submissions}",
        description: "Total count of statutory submissions processed across modules (WSP, DG, ARPL, SDP).");

    // Audited Double-Write Counter
    public static readonly Counter<long> DoubleWrites = Meter.CreateCounter<long>(
        "nsdms_audit_double_writes_total",
        unit: "{writes}",
        description: "Total count of verified double-write audit log entries persisted.");

    // PDF / Document Generation Counter
    public static readonly Counter<long> DocumentGenerations = Meter.CreateCounter<long>(
        "nsdms_document_generations_total",
        unit: "{documents}",
        description: "Total count of statutory PDF outcome letters, certificates, and contracts generated.");

    // Transaction & Operation Latency Histogram
    public static readonly Histogram<double> TransactionDurationSeconds = Meter.CreateHistogram<double>(
        "nsdms_transaction_duration_seconds",
        unit: "s",
        description: "Duration of business rule executions and database transactions in seconds.");

    // PDF Rendering Duration Histogram
    public static readonly Histogram<double> DocumentRenderDurationSeconds = Meter.CreateHistogram<double>(
        "nsdms_document_render_duration_seconds",
        unit: "s",
        description: "Duration of QuestPDF statutory document generation in seconds.");

    // Active Background Jobs UpDownCounter
    public static readonly UpDownCounter<int> ActiveBackgroundJobs = Meter.CreateUpDownCounter<int>(
        "nsdms_background_jobs_active",
        unit: "{jobs}",
        description: "Current number of in-flight background processing worker tasks.");

    // Form Draft Recovery Counter
    public static readonly Counter<long> DraftRestores = Meter.CreateCounter<long>(
        "nsdms_form_draft_restores_total",
        unit: "{restores}",
        description: "Total number of unsaved form drafts recovered by users from local storage.");

    // Circuit Reconnection Counter
    public static readonly Counter<long> CircuitReconnects = Meter.CreateCounter<long>(
        "nsdms_circuit_reconnects_total",
        unit: "{reconnects}",
        description: "Total number of Blazor SignalR circuit reconnections.");

    // Convenience Recording Helpers
    public static void RecordStatutorySubmission(string module, string status, string role = "SDF")
    {
        StatutorySubmissions.Add(1,
            new KeyValuePair<string, object?>("module", module),
            new KeyValuePair<string, object?>("status", status),
            new KeyValuePair<string, object?>("role", role));
    }

    public static void RecordDoubleWrite(string entityName, string actionName)
    {
        DoubleWrites.Add(1,
            new KeyValuePair<string, object?>("entity", entityName),
            new KeyValuePair<string, object?>("action", actionName));
    }

    public static void RecordDocumentGeneration(string documentType, bool success, double durationSeconds)
    {
        DocumentGenerations.Add(1,
            new KeyValuePair<string, object?>("doc_type", documentType),
            new KeyValuePair<string, object?>("status", success ? "success" : "failed"));

        DocumentRenderDurationSeconds.Record(durationSeconds,
            new KeyValuePair<string, object?>("doc_type", documentType));
    }

    public static Activity? StartActivity(string operationName, ActivityKind kind = ActivityKind.Internal)
    {
        return Source.StartActivity(operationName, kind);
    }
}
