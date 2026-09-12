using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Nsdms.LoadTester.Telemetry;

namespace Nsdms.LoadTester.Scenarios;

public class WspSubmissionWorker
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly PerformanceMetricsCollector _metrics;

    public WspSubmissionWorker(INsdmsDbContextFactory contextFactory, PerformanceMetricsCollector metrics)
    {
        _contextFactory = contextFactory;
        _metrics = metrics;
    }

    public async Task<int> ExecuteSubmissionAsync(int submissionIndex, int organisationId, string username = "SDF_USER")
    {
        return await _metrics.MeasureAsync("MandatoryGrants", "Wsp_CompleteSubmission", async () =>
        {
            await using var context = _contextFactory.CreateDbContext();
            var schemeYear = 2026;
            var refNo = $"WSP-{schemeYear}-SIM-{submissionIndex:D6}";

            var wsp = new WspSubmission
            {
                OrganisationId = organisationId,
                FinYear = schemeYear,
                ReferenceNumber = refNo,
                WspApprovalStatusCode = "Submitted",
                SubmissionDate = DateTime.UtcNow,
                PlannedTrainingBudget = 150000m + (submissionIndex * 10m),
                EmployeeCount = 120 + (submissionIndex % 80),
                IsSignoffQuorumMet = true,
                RequiredSignoffCount = 2,
                CompletedSignoffCount = 2,
                SignoffDigitalSecuritySeal = Guid.NewGuid().ToString("N").ToUpper(),
                CreatedAt = DateTime.UtcNow,
                CreatedBy = username
            };

            context.WspSubmissions.Add(wsp);
            await context.SaveChangesAsync();

            // Add Form 500 demographic summary
            var summary = new WspEmploymentSummary
            {
                WspSubmissionId = wsp.Id,
                OccupationalCategory = "Artisans",
                OfoCode = "641201",
                MaleAfrican = 40,
                FemaleAfrican = 30,
                MaleColoured = 10,
                FemaleColoured = 8,
                MaleIndian = 5,
                FemaleIndian = 4,
                MaleWhite = 15,
                FemaleWhite = 8,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = username
            };
            context.WspEmploymentSummaries.Add(summary);

            // Add Training Plan
            var plan = new WspTrainingPlan
            {
                WspSubmissionId = wsp.Id,
                ProgrammeTypeCode = "02", // Learnership
                NqfLevel = 4,
                BeneficiaryCount = 25,
                EstimatedCost = 85000m,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = username
            };
            context.WspTrainingPlans.Add(plan);

            // Double-write Audit Log
            var audit = new AuditLog
            {
                EntityName = nameof(WspSubmission),
                RecordId = wsp.Id,
                ActionName = "SubmitMandatoryGrantWsp",
                Actor = username,
                Timestamp = DateTime.UtcNow,
                MetadataJson = $"{{\"ReferenceNumber\":\"{refNo}\",\"Status\":\"Submitted\",\"EmployeeCount\":{wsp.EmployeeCount}}}"
            };
            context.AuditLogs.Add(audit);

            await context.SaveChangesAsync();
            return wsp.Id;
        });
    }
}
