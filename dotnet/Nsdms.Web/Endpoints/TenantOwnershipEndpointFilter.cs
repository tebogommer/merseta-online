using System;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using Nsdms.Application.Common;

namespace Nsdms.Web.Endpoints;

/// <summary>
/// Endpoint filter preventing Broken Object-Level Authorization (BOLA / IDOR) across document streaming routes
/// by validating that the requesting tenant owns the target record, unless holding system administrative privileges.
/// </summary>
public class TenantOwnershipEndpointFilter : IEndpointFilter
{
    private readonly ITenantProvider _tenantProvider;
    private readonly INsdmsDbContextFactory _dbFactory;
    private readonly ILogger<TenantOwnershipEndpointFilter> _logger;

    public TenantOwnershipEndpointFilter(
        ITenantProvider tenantProvider,
        INsdmsDbContextFactory dbFactory,
        ILogger<TenantOwnershipEndpointFilter> logger)
    {
        _tenantProvider = tenantProvider;
        _dbFactory = dbFactory;
        _logger = logger;
    }

    public async ValueTask<object?> InvokeAsync(EndpointFilterInvocationContext context, EndpointFilterDelegate next)
    {
        // Administrative / SuperAdmin bypass
        if (_tenantProvider.IsAdmin)
        {
            return await next(context);
        }

        // Non-admin callers require an active authenticated organisation context
        if (!_tenantProvider.CurrentOrganisationId.HasValue)
        {
            _logger.LogWarning("Access to document endpoint blocked: caller has no active organisation tenancy.");
            return Results.Forbid();
        }

        int currentOrgId = _tenantProvider.CurrentOrganisationId.Value;
        var httpContext = context.HttpContext;
        var path = httpContext.Request.Path.Value ?? string.Empty;

        if (httpContext.Request.RouteValues.TryGetValue("id", out var idObj) &&
            int.TryParse(idObj?.ToString(), out int recordId))
        {
            using var db = await _dbFactory.CreateDbContextAsync();
            int? recordOrgId = null;

            if (path.Contains("/moa/", StringComparison.OrdinalIgnoreCase))
            {
                recordOrgId = await db.GrantMoas
                    .Where(m => m.Id == recordId)
                    .Select(m => (int?)m.GrantApplication!.OrganisationId)
                    .FirstOrDefaultAsync();
            }
            else if (path.Contains("/wsp/", StringComparison.OrdinalIgnoreCase))
            {
                recordOrgId = await db.WspSubmissions
                    .Where(w => w.Id == recordId)
                    .Select(w => (int?)w.OrganisationId)
                    .FirstOrDefaultAsync();
            }
            else if (path.Contains("/remittance/", StringComparison.OrdinalIgnoreCase))
            {
                recordOrgId = await db.MandatoryGrantDisbursements
                    .Where(d => d.Id == recordId)
                    .Select(d => (int?)d.OrganisationId)
                    .FirstOrDefaultAsync();
            }
            else if (path.Contains("/workplace-approval/", StringComparison.OrdinalIgnoreCase))
            {
                recordOrgId = await db.WorkplaceApprovals
                    .Where(w => w.Id == recordId)
                    .Select(w => (int?)w.OrganisationId)
                    .FirstOrDefaultAsync();
            }
            else if (path.Contains("/tradetest/", StringComparison.OrdinalIgnoreCase))
            {
                recordOrgId = await db.LearnerTradeTests
                    .Where(t => t.Id == recordId)
                    .Select(t => (int?)t.CompanyLearner!.OrganisationId)
                    .FirstOrDefaultAsync();
            }

            if (recordOrgId.HasValue && recordOrgId.Value != currentOrgId)
            {
                _logger.LogWarning("BOLA/IDOR attempt blocked: User from Org #{CurrentOrgId} attempted to access {Path} belonging to Org #{TargetOrgId}",
                    currentOrgId, path, recordOrgId.Value);
                return Results.Forbid();
            }
        }

        return await next(context);
    }
}
