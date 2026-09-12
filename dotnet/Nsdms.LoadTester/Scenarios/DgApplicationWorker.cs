using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Nsdms.LoadTester.Telemetry;

namespace Nsdms.LoadTester.Scenarios;

public class DgApplicationWorker
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly PerformanceMetricsCollector _metrics;

    public DgApplicationWorker(INsdmsDbContextFactory contextFactory, PerformanceMetricsCollector metrics)
    {
        _contextFactory = contextFactory;
        _metrics = metrics;
    }

    public async Task<int> ExecuteApplicationAsync(int appIndex, int organisationId, int? fundingWindowId, string username = "SDF_DG_USER")
    {
        return await _metrics.MeasureAsync("DiscretionaryGrants", "DG_SubmitAndAdjudicate", async () =>
        {
            await using var context = _contextFactory.CreateDbContext();
            var appNo = $"DG-2026-SIM-{appIndex:D6}";

            var app = new GrantApplication
            {
                OrganisationId = organisationId,
                FundingWindowId = fundingWindowId,
                ApplicationNumber = appNo,
                GrantTypeCode = (appIndex % 2 == 0) ? "PIVOTAL" : "NON_PIVOTAL",
                ApplicationStatusCode = "Submitted",
                RequestedAmount = 350000m + (appIndex % 100 * 1000m),
                ApprovedAmount = 0m,
                ProjectTitle = $"Skills Transformation Project #{appIndex}",
                CreatedAt = DateTime.UtcNow,
                CreatedBy = username
            };

            context.GrantApplications.Add(app);
            await context.SaveChangesAsync();

            // Add Project Budget line items
            var budget = new GrantProjectBudget
            {
                GrantApplicationId = app.Id,
                ExpenseCategory = "TUITION",
                Description = "Learner Tuition & Toolkits",
                Quantity = 10,
                UnitCost = 35000m,
                TotalCost = 350000m,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = username
            };
            context.GrantProjectBudgets.Add(budget);

            // Double-write Audit Log
            var audit = new AuditLog
            {
                EntityName = nameof(GrantApplication),
                RecordId = app.Id,
                ActionName = "SubmitDiscretionaryGrantApplication",
                Actor = username,
                Timestamp = DateTime.UtcNow,
                MetadataJson = $"{{\"ApplicationNumber\":\"{appNo}\",\"RequestedAmount\":{app.RequestedAmount}}}"
            };
            context.AuditLogs.Add(audit);

            await context.SaveChangesAsync();
            return app.Id;
        });
    }
}
