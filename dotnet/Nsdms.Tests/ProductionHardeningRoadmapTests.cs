using System.Text.Json;
using Microsoft.Extensions.Logging.Abstractions;
using Moq;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Diagnostics;
using Nsdms.Application.Services;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

public class ProductionHardeningRoadmapTests
{
    [Fact]
    public async Task BackgroundJobQueue_EnqueueAndProcessTicket_TransitionsCorrectly()
    {
        // Arrange
        var logger = NullLogger<InMemoryBackgroundJobQueue>.Instance;
        var queue = new InMemoryBackgroundJobQueue(logger, capacity: 50);

        // Act 1: Enqueue document job
        var ticket = await queue.EnqueueDocumentGenerationAsync(
            "WspOutcomeLetter",
            recordId: 42,
            requestedBy: "sdf@toyota.co.za",
            fileName: "WSP_Outcome_Letter_42.pdf");

        // Assert 1: Initial state
        Assert.NotNull(ticket);
        Assert.Equal(BackgroundJobStatus.Queued, ticket.Status);
        Assert.Equal(0, ticket.ProgressPercentage);
        Assert.Equal("sdf@toyota.co.za", ticket.RequestedBy);

        // Act 2: Progress update
        queue.UpdateProgress(ticket.JobId, 45, "Compiling PDF layout");
        var updated = queue.GetJob(ticket.JobId);
        Assert.NotNull(updated);
        Assert.Equal(BackgroundJobStatus.Running, updated.Status);
        Assert.Equal(45, updated.ProgressPercentage);
        Assert.Equal("Compiling PDF layout", updated.CurrentStep);

        // Act 3: Complete job
        byte[] mockBytes = new byte[] { 0x25, 0x50, 0x44, 0x46 }; // %PDF
        queue.MarkCompleted(ticket.JobId, mockBytes, "application/pdf", "WSP_Outcome_Letter_42.pdf");

        var completed = queue.GetJob(ticket.JobId);
        Assert.NotNull(completed);
        Assert.Equal(BackgroundJobStatus.Completed, completed.Status);
        Assert.Equal(100, completed.ProgressPercentage);
        Assert.NotNull(completed.CompletedAt);
        Assert.NotNull(completed.ResultData);
        Assert.Equal(mockBytes.Length, completed.ResultData.Length);
        Assert.Equal($"/api/jobs/{ticket.JobId}/download", completed.ResultDownloadUrl);

        // Assert 4: Recent & User jobs queries
        var userJobs = queue.GetUserJobs("sdf@toyota.co.za");
        Assert.Single(userJobs);
    }

    [Fact]
    public async Task BackgroundJobQueue_FailureHandling_RecordsErrorGracefully()
    {
        // Arrange
        var logger = NullLogger<InMemoryBackgroundJobQueue>.Instance;
        var queue = new InMemoryBackgroundJobQueue(logger);

        var ticket = await queue.EnqueueAsync(new BackgroundJobTicket
        {
            JobType = "BatchExport",
            Description = "SETMIS Monthly Delta Export",
            RequestedBy = "system_officer"
        });

        // Act
        queue.MarkFailed(ticket.JobId, "Transient network timeout reaching SFTP server.");

        // Assert
        var failed = queue.GetJob(ticket.JobId);
        Assert.NotNull(failed);
        Assert.Equal(BackgroundJobStatus.Failed, failed.Status);
        Assert.Contains("Transient network timeout", failed.ErrorMessage);
        Assert.NotNull(failed.CompletedAt);
    }

    [Fact]
    public void FormDraftDto_ExpirationLogic_EvaluatesCorrectly()
    {
        // Arrange
        var activeDraft = new FormDraftDto
        {
            FormKey = "wsp_submission",
            RecordId = "101",
            UserId = "user_sdf",
            FormTitle = "WSP Submission 2026",
            DataJson = "{\"step\": 2, \"levyAmount\": 50000}",
            SavedAt = DateTime.UtcNow,
            ExpiresAt = DateTime.UtcNow.AddHours(24)
        };

        var expiredDraft = new FormDraftDto
        {
            FormKey = "dg_application",
            RecordId = "202",
            UserId = "user_sdf",
            FormTitle = "DG Application 2026",
            DataJson = "{\"grantType\": \"PIVOTAL\"}",
            SavedAt = DateTime.UtcNow.AddDays(-10),
            ExpiresAt = DateTime.UtcNow.AddDays(-3)
        };

        // Assert
        Assert.False(activeDraft.IsExpired);
        Assert.True(expiredDraft.IsExpired);
    }

    [Fact]
    public void NsdmsDiagnostics_TelemetryMeters_RecordWithoutExceptions()
    {
        // Act & Assert (Should execute cleanly without throwing)
        NsdmsDiagnostics.RecordStatutorySubmission("WSP", "Submitted", "SDF");
        NsdmsDiagnostics.RecordStatutorySubmission("DG", "Approved", "Approver");
        NsdmsDiagnostics.RecordDoubleWrite("Organisation", "Updated");
        NsdmsDiagnostics.RecordDocumentGeneration("GrantMoaContract", true, 0.42);

        using var activity = NsdmsDiagnostics.StartActivity("StatutoryBatchIngestion");
        Assert.NotNull(NsdmsDiagnostics.Meter);
        Assert.NotNull(NsdmsDiagnostics.Source);
    }

    [Fact]
    public async Task MetricsScraperService_GeneratesPrometheusFormatCorrectly()
    {
        // Arrange
        var jobQueue = new InMemoryBackgroundJobQueue(NullLogger<InMemoryBackgroundJobQueue>.Instance);
        var archivalServiceMock = new Mock<IAuditLogArchivalService>();
        archivalServiceMock.Setup(s => s.GetArchivalMetricsAsync(It.IsAny<CancellationToken>()))
            .ReturnsAsync(new AuditLogArchivalMetrics
            {
                ActiveAuditLogCount = 42461,
                ArchivedAuditLogCount = 12000,
                Partitions = new List<AuditPartitionInfo>
                {
                    new() { TableName = "audit_logs", PartitionNumber = 1, RowCount = 42461, BoundaryValue = "2026-01-01" }
                }
            });

        var scraper = new MetricsScraperService(jobQueue, archivalServiceMock.Object);

        // Act
        var prometheusText = await scraper.GetPrometheusMetricsTextAsync();

        // Assert
        Assert.NotEmpty(prometheusText);
        Assert.Contains("process_cpu_seconds_total", prometheusText);
        Assert.Contains("process_working_set_bytes", prometheusText);
        Assert.Contains("nsdms_background_jobs_queue_count", prometheusText);
        Assert.Contains("nsdms_audit_logs_active_total 42461", prometheusText);
        Assert.Contains("nsdms_audit_logs_archived_total 12000", prometheusText);
    }
}
