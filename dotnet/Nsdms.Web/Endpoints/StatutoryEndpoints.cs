using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Routing;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Services;

namespace Nsdms.Web.Endpoints;

public static class StatutoryEndpoints
{
    public static RouteGroupBuilder MapStatutoryEndpoints(this IEndpointRouteBuilder app)
    {
        var group = app.MapGroup("/api/statutory")
            .RequireAuthorization(policy => policy.RequireRole("SuperAdmin", "StatutoryReportingOfficer", "Administrator"));

        group.MapGet("/setmis/files/{fileCode}", async (string fileCode, ISetmisExtractService setmis) =>
        {
            var res = await setmis.ExtractSetmisFileAsync(fileCode);
            return Results.File(res.ContentBytes, "text/plain", res.FileName);
        });

        group.MapGet("/nlrd/files/{fileCode}", async (string fileCode, INlrdExtractService nlrd) =>
        {
            var res = await nlrd.ExtractNlrdFileAsync(fileCode);
            return Results.File(res.ContentBytes, "text/plain", res.FileName);
        });

        group.MapGet("/batches/{id:int}/download", async (int id, ISetmisExtractService setmis, INlrdExtractService nlrd, INsdmsDbContextFactory dbFactory) =>
        {
            using var db = await dbFactory.CreateDbContextAsync();
            var b = await db.StatutorySubmissionBatches.FirstOrDefaultAsync(x => x.Id == id);
            if (b == null) return Results.NotFound();

            if (b.BatchType == "SETMIS")
            {
                var zip = await setmis.DownloadSetmisBatchArchiveAsync(id);
                return Results.File(zip.ZipBytes, "application/zip", zip.ArchiveFileName);
            }
            else
            {
                var zip = await nlrd.DownloadNlrdBatchArchiveAsync(id);
                return Results.File(zip.ZipBytes, "application/zip", zip.ArchiveFileName);
            }
        });

        return group;
    }
}
