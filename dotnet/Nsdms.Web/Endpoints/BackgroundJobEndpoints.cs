using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Routing;
using Nsdms.Application.Services;

namespace Nsdms.Web.Endpoints;

public static class BackgroundJobEndpoints
{
    public static RouteGroupBuilder MapBackgroundJobEndpoints(this IEndpointRouteBuilder app)
    {
        var group = app.MapGroup("/api/jobs").RequireAuthorization();

        group.MapGet("/{id:guid}/status", (Guid id, IBackgroundJobQueue jobQueue, HttpContext httpContext) =>
        {
            var job = jobQueue.GetJob(id);
            if (job == null) return Results.NotFound(new { message = $"Job #{id} not found." });

            var currentUsername = httpContext.User.Identity?.Name;
            bool isAdmin = httpContext.User.IsInRole("Admin") || httpContext.User.IsInRole("SuperAdmin") || httpContext.User.IsInRole("SUPERADMIN") || httpContext.User.HasClaim("Permission", "System.Admin");
            if (!isAdmin && !string.Equals(job.RequestedBy, currentUsername, StringComparison.OrdinalIgnoreCase))
            {
                return Results.Forbid();
            }

            return Results.Ok(new
            {
                job.JobId,
                job.JobType,
                job.Description,
                Status = job.Status.ToString(),
                job.ProgressPercentage,
                job.CurrentStep,
                job.CreatedAt,
                job.StartedAt,
                job.CompletedAt,
                job.ResultDownloadUrl,
                job.ErrorMessage
            });
        });

        group.MapGet("/{id:guid}/download", (Guid id, IBackgroundJobQueue jobQueue, HttpContext httpContext) =>
        {
            var job = jobQueue.GetJob(id);
            if (job == null) return Results.NotFound(new { message = $"Job #{id} not found." });

            var currentUsername = httpContext.User.Identity?.Name;
            bool isAdmin = httpContext.User.IsInRole("Admin") || httpContext.User.IsInRole("SuperAdmin") || httpContext.User.IsInRole("SUPERADMIN") || httpContext.User.HasClaim("Permission", "System.Admin");
            if (!isAdmin && !string.Equals(job.RequestedBy, currentUsername, StringComparison.OrdinalIgnoreCase))
            {
                return Results.Forbid();
            }

            if (job.Status != BackgroundJobStatus.Completed)
                return Results.BadRequest(new { message = $"Job #{id} is not completed." });

            string contentType = job.ResultContentType ?? "application/pdf";
            string fileName = job.ResultFileName ?? $"JobResult_{id}.pdf";

            // Stream directly from disk cache to eliminate LOH memory retention
            if (!string.IsNullOrEmpty(job.ResultStoragePath) && System.IO.File.Exists(job.ResultStoragePath))
            {
                return Results.File(job.ResultStoragePath, contentType, fileName, enableRangeProcessing: true);
            }

            if (job.ResultData != null)
            {
                return Results.File(job.ResultData, contentType, fileName);
            }

            return Results.BadRequest(new { message = $"Job #{id} has no binary data available for download." });
        });

        return group;
    }
}
