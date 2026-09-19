using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Routing;
using Nsdms.Application.Services;
using Nsdms.Infrastructure.Services;

namespace Nsdms.Web.Endpoints;

public static class MetricsEndpoints
{
    public static RouteGroupBuilder MapMetricsEndpoints(this IEndpointRouteBuilder app)
    {
        var group = app.MapGroup(string.Empty).RequireAuthorization();

        group.MapGet("/metrics", async (IMetricsScraperService metricsService, CancellationToken ct) =>
        {
            var text = await metricsService.GetPrometheusMetricsTextAsync(ct);
            return Results.Content(text, "text/plain; version=0.0.4; charset=utf-8");
        });

        group.MapGet("/api/admin/audit-logs/archival-metrics", async (IAuditLogArchivalService archivalService, CancellationToken ct) =>
        {
            var metrics = await archivalService.GetArchivalMetricsAsync(ct);
            return Results.Ok(metrics);
        });

        group.MapPost("/api/documents/async-generate", async (AsyncDocumentRequest req, IBackgroundJobQueue jobQueue, HttpContext httpContext) =>
        {
            var user = httpContext.User.Identity?.Name ?? "ANONYMOUS";
            var ticket = await jobQueue.EnqueueDocumentGenerationAsync(req.DocumentType, req.RecordId, user, req.FileName);
            return Results.Accepted($"/api/jobs/{ticket.JobId}/status", new
            {
                ticket.JobId,
                ticket.Description,
                Status = ticket.Status.ToString(),
                StatusUrl = $"/api/jobs/{ticket.JobId}/status"
            });
        });

        return group;
    }
}
