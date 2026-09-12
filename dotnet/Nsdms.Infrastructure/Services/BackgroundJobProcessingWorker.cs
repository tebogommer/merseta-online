using System.Text.Json;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;

namespace Nsdms.Infrastructure.Services;

public class BackgroundJobProcessingWorker : BackgroundService
{
    private readonly IBackgroundJobQueue _jobQueue;
    private readonly IServiceProvider _serviceProvider;
    private readonly ILogger<BackgroundJobProcessingWorker> _logger;

    public BackgroundJobProcessingWorker(
        IBackgroundJobQueue jobQueue,
        IServiceProvider serviceProvider,
        ILogger<BackgroundJobProcessingWorker> logger)
    {
        _jobQueue = jobQueue;
        _serviceProvider = serviceProvider;
        _logger = logger;
    }

    protected override async Task ExecuteAsync(CancellationToken stoppingToken)
    {
        _logger.LogInformation("Background Job Processing Worker started. Ready to process decoupled CPU tasks.");

        try
        {
            await foreach (var ticket in _jobQueue.Reader.ReadAllAsync(stoppingToken))
            {
                try
                {
                    await ProcessJobAsync(ticket, stoppingToken);
                }
                catch (OperationCanceledException) when (stoppingToken.IsCancellationRequested)
                {
                    break;
                }
                catch (Exception ex)
                {
                    _logger.LogError(ex, "Unhandled error processing background job #{JobId} [{JobType}]", ticket.JobId, ticket.JobType);
                    _jobQueue.MarkFailed(ticket.JobId, ex.Message);
                }
            }
        }
        catch (OperationCanceledException) when (stoppingToken.IsCancellationRequested)
        {
            // Graceful shutdown
        }

        _logger.LogInformation("Background Job Processing Worker shutting down.");
    }

    private async Task ProcessJobAsync(BackgroundJobTicket ticket, CancellationToken ct)
    {
        _logger.LogInformation("Processing background job #{JobId} [{JobType}] requested by '{User}'", 
            ticket.JobId, ticket.JobType, ticket.RequestedBy);

        _jobQueue.UpdateProgress(ticket.JobId, 10, "Worker assigned. Initializing processing environment...");

        using var scope = _serviceProvider.CreateScope();

        if (string.Equals(ticket.JobType, "DocumentGeneration", StringComparison.OrdinalIgnoreCase))
        {
            await ProcessDocumentGenerationJobAsync(ticket, scope.ServiceProvider, ct);
        }
        else if (string.Equals(ticket.JobType, "SarsLevyIngestion", StringComparison.OrdinalIgnoreCase))
        {
            await ProcessSarsLevyIngestionJobAsync(ticket, scope.ServiceProvider, ct);
        }
        else
        {
            // Generic task execution
            _jobQueue.UpdateProgress(ticket.JobId, 50, "Executing background task...");
            await Task.Delay(100, ct); // Brief simulation yield
            _jobQueue.MarkCompleted(ticket.JobId, null, null, null, null);
        }

        // Notify user via SignalR if available
        try
        {
            var notificationPublisher = scope.ServiceProvider.GetService<ISignalRNotificationPublisher>();
            if (notificationPublisher != null && ticket.Status == BackgroundJobStatus.Completed)
            {
                bool isSars = string.Equals(ticket.JobType, "SarsLevyIngestion", StringComparison.OrdinalIgnoreCase);
                await notificationPublisher.PublishNotificationAsync(new SystemNotificationDto
                {
                    RecipientUsername = ticket.RequestedBy,
                    Title = isSars ? "SARS Levy Ingestion Completed" : "Background Task Completed",
                    Message = isSars
                        ? $"SARS Levy File '{ticket.ResultFileName}' processed and reconciled successfully. Ledger records updated."
                        : ticket.Description,
                    NotificationType = isSars ? "Finance" : "Task",
                    Severity = "Success",
                    ActionUrl = ticket.ResultDownloadUrl,
                    CreatedAt = DateTime.UtcNow
                });
            }
        }
        catch (Exception ex)
        {
            _logger.LogWarning(ex, "Failed to send SignalR notification for completed background job #{JobId}", ticket.JobId);
        }
    }

    private async Task ProcessSarsLevyIngestionJobAsync(BackgroundJobTicket ticket, IServiceProvider sp, CancellationToken ct)
    {
        var streamingPipeline = sp.GetRequiredService<ISarsLevyStreamingPipeline>();

        if (ticket.ResultData == null || ticket.ResultData.Length == 0)
        {
            throw new InvalidOperationException("No binary file data was provided for background SARS levy ingestion.");
        }

        string fileName = ticket.ResultFileName ?? "SARS_UPLOAD.dat";
        _logger.LogInformation("Starting background SARS levy streaming ingestion for '{FileName}' ({Bytes:N0} bytes)", fileName, ticket.ResultData.Length);

        _jobQueue.UpdateProgress(ticket.JobId, 20, "Executing streaming pre-flight compliance & cryptographic seal validation...");

        using var stream = new MemoryStream(ticket.ResultData);

        _jobQueue.UpdateProgress(ticket.JobId, 50, "Streaming file chunks through staging table & executing set-based ledger promotion...");

        var streamResult = await streamingPipeline.ProcessSarsStreamAsync(stream, fileName, ticket.RequestedBy, ct);

        _jobQueue.UpdateProgress(ticket.JobId, 90, "Finalizing financial ledger records & double-write audit trail...");

        var resultSummary = JsonSerializer.Serialize(new
        {
            streamResult.LevyFileId,
            streamResult.BatchIdentifier,
            streamResult.FileName,
            streamResult.DigitalSecuritySeal,
            streamResult.TotalRecords,
            streamResult.TotalAmount,
            streamResult.OutOfScopeCount,
            streamResult.SicMismatchCount,
            streamResult.ProcessingDurationMs
        });

        _jobQueue.MarkCompleted(
            ticket.JobId,
            System.Text.Encoding.UTF8.GetBytes(resultSummary),
            "application/json",
            fileName,
            $"/levies/{streamResult.LevyFileId}");

        _logger.LogInformation("Background SARS levy streaming ingestion completed successfully. Batch #{BatchId} -> LevyFile #{LevyFileId} ({Records:N0} records, {Amount:C}) in {Duration}ms",
            streamResult.BatchIdentifier, streamResult.LevyFileId, streamResult.TotalRecords, streamResult.TotalAmount, streamResult.ProcessingDurationMs);
    }

    private async Task ProcessDocumentGenerationJobAsync(BackgroundJobTicket ticket, IServiceProvider sp, CancellationToken ct)
    {
        var pdfService = sp.GetRequiredService<IPdfDocumentService>();

        string docType = "Unknown";
        int recordId = 0;

        if (!string.IsNullOrEmpty(ticket.PayloadJson))
        {
            using var doc = JsonDocument.Parse(ticket.PayloadJson);
            if (doc.RootElement.TryGetProperty("DocumentType", out var dtElem))
            {
                docType = dtElem.GetString() ?? docType;
            }
            if (doc.RootElement.TryGetProperty("RecordId", out var idElem))
            {
                recordId = idElem.GetInt32();
            }
        }

        _jobQueue.UpdateProgress(ticket.JobId, 30, $"Compiling QuestPDF layout for {docType} #{recordId}...");

        byte[] pdfBytes;
        string fileName = ticket.ResultFileName ?? $"{docType}_{recordId}.pdf";

        switch (docType.ToLowerInvariant())
        {
            case "moacontract":
            case "grantmoa":
                pdfBytes = await pdfService.GenerateGrantMoaContractPdfAsync(recordId);
                fileName = $"GrantMoa_Contract_{recordId}.pdf";
                break;

            case "tradetestcertificate":
                pdfBytes = await pdfService.GenerateTradeTestCertificatePdfAsync(recordId);
                fileName = $"TradeTest_Artisan_Certificate_{recordId}.pdf";
                break;

            case "arplform":
                pdfBytes = await pdfService.GenerateArplApplicationFormPdfAsync(recordId);
                fileName = $"ARPL_Application_Form_{recordId}.pdf";
                break;

            case "wspoutcomeletter":
            case "wspapprovalletter":
                pdfBytes = await pdfService.GenerateWspOutcomeLetterPdfAsync(recordId);
                fileName = $"WSP_Outcome_Letter_{recordId}.pdf";
                break;

            case "mandatoryrebateremittance":
            case "remittance":
                pdfBytes = await pdfService.GenerateMandatoryRebateRemittancePdfAsync(recordId);
                fileName = $"Mandatory_Rebate_Remittance_{recordId}.pdf";
                break;

            case "workplaceapprovalletter":
                pdfBytes = await pdfService.GenerateWorkplaceApprovalLetterPdfAsync(recordId);
                fileName = $"WorkplaceApproval_Outcome_Letter_{recordId}.pdf";
                break;

            case "workplaceapprovalreport":
                pdfBytes = await pdfService.GenerateWorkplaceApprovalReportPdfAsync(recordId);
                fileName = $"WorkplaceApproval_Report_{recordId}.pdf";
                break;

            case "summativeresults":
                pdfBytes = await pdfService.GenerateSummativeAssessmentResultsFormPdfAsync(recordId);
                fileName = $"Summative_Results_{recordId}.pdf";
                break;

            case "moderationreport":
                pdfBytes = await pdfService.GenerateModerationValidationReportPdfAsync(recordId);
                fileName = $"Moderation_Report_Batch_{recordId}.pdf";
                break;

            case "learnercertificate":
                pdfBytes = await pdfService.GenerateLearnerQualificationCertificatePdfAsync(recordId);
                fileName = $"MerSETA_Certificate_{recordId}.pdf";
                break;

            default:
                throw new NotSupportedException($"Document type '{docType}' is not supported for background compilation.");
        }

        _jobQueue.UpdateProgress(ticket.JobId, 90, "Finalizing binary streaming buffer...");
        _jobQueue.MarkCompleted(ticket.JobId, pdfBytes, "application/pdf", fileName, $"/api/jobs/{ticket.JobId}/download");
    }
}
