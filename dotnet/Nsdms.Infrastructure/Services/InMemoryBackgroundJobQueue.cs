using System.Collections.Concurrent;
using System.Text.Json;
using System.Threading.Channels;
using Microsoft.Extensions.Logging;
using Nsdms.Application.Services;

namespace Nsdms.Infrastructure.Services;

public class InMemoryBackgroundJobQueue : IBackgroundJobQueue
{
    private readonly Channel<BackgroundJobTicket> _channel;
    private readonly ConcurrentDictionary<Guid, BackgroundJobTicket> _jobStore = new();
    private readonly ILogger<InMemoryBackgroundJobQueue> _logger;

    public ChannelReader<BackgroundJobTicket> Reader => _channel.Reader;

    public InMemoryBackgroundJobQueue(ILogger<InMemoryBackgroundJobQueue> logger, int capacity = 1000)
    {
        _logger = logger;
        var options = new BoundedChannelOptions(capacity)
        {
            FullMode = BoundedChannelFullMode.Wait,
            SingleReader = false,
            SingleWriter = false
        };
        _channel = Channel.CreateBounded<BackgroundJobTicket>(options);
    }

    public async ValueTask<BackgroundJobTicket> EnqueueAsync(BackgroundJobTicket ticket, CancellationToken cancellationToken = default)
    {
        _jobStore[ticket.JobId] = ticket;
        await _channel.Writer.WriteAsync(ticket, cancellationToken);
        _logger.LogInformation("Background Job #{JobId} [{JobType}] enqueued by '{User}'.", ticket.JobId, ticket.JobType, ticket.RequestedBy);
        return ticket;
    }

    public async ValueTask<BackgroundJobTicket> EnqueueDocumentGenerationAsync(
        string documentType,
        int recordId,
        string requestedBy,
        string? fileName = null,
        CancellationToken cancellationToken = default)
    {
        var payload = JsonSerializer.Serialize(new
        {
            DocumentType = documentType,
            RecordId = recordId
        });

        var ticket = new BackgroundJobTicket
        {
            JobId = Guid.NewGuid(),
            JobType = "DocumentGeneration",
            Description = $"Generate {documentType} for record #{recordId}",
            RequestedBy = requestedBy,
            PayloadJson = payload,
            ResultFileName = fileName ?? $"{documentType}_{recordId}.pdf",
            ResultContentType = "application/pdf",
            Status = BackgroundJobStatus.Queued,
            ProgressPercentage = 0,
            CurrentStep = "Enqueued in background processing queue"
        };

        return await EnqueueAsync(ticket, cancellationToken);
    }

    public async ValueTask<BackgroundJobTicket> EnqueueSarsLevyIngestionAsync(
        string fileName,
        byte[] fileData,
        string requestedBy,
        CancellationToken cancellationToken = default)
    {
        var payload = JsonSerializer.Serialize(new
        {
            FileName = fileName,
            FileSizeBytes = fileData.Length
        });

        var ticket = new BackgroundJobTicket
        {
            JobId = Guid.NewGuid(),
            JobType = "SarsLevyIngestion",
            Description = $"Process and reconcile SARS monthly levy schedule '{fileName}' ({fileData.Length / 1024.0:N1} KB)",
            RequestedBy = requestedBy,
            PayloadJson = payload,
            ResultFileName = fileName,
            ResultContentType = "text/plain",
            ResultData = fileData,
            Status = BackgroundJobStatus.Queued,
            ProgressPercentage = 0,
            CurrentStep = "Enqueued in reactive background processing pipeline"
        };

        return await EnqueueAsync(ticket, cancellationToken);
    }

    public BackgroundJobTicket? GetJob(Guid jobId)
    {
        _jobStore.TryGetValue(jobId, out var ticket);
        return ticket;
    }

    public IEnumerable<BackgroundJobTicket> GetRecentJobs(int limit = 50)
    {
        return _jobStore.Values
            .OrderByDescending(j => j.CreatedAt)
            .Take(limit)
            .ToList();
    }

    public IEnumerable<BackgroundJobTicket> GetUserJobs(string requestedBy, int limit = 20)
    {
        return _jobStore.Values
            .Where(j => string.Equals(j.RequestedBy, requestedBy, StringComparison.OrdinalIgnoreCase))
            .OrderByDescending(j => j.CreatedAt)
            .Take(limit)
            .ToList();
    }

    public void UpdateProgress(Guid jobId, int progressPercentage, string? currentStep = null)
    {
        if (_jobStore.TryGetValue(jobId, out var ticket))
        {
            ticket.Status = BackgroundJobStatus.Running;
            ticket.ProgressPercentage = Math.Clamp(progressPercentage, 0, 100);
            if (!string.IsNullOrEmpty(currentStep))
            {
                ticket.CurrentStep = currentStep;
            }
            if (!ticket.StartedAt.HasValue)
            {
                ticket.StartedAt = DateTime.UtcNow;
            }
        }
    }

    public void MarkCompleted(Guid jobId, byte[]? resultData, string? contentType, string? fileName, string? downloadUrl = null)
    {
        if (_jobStore.TryGetValue(jobId, out var ticket))
        {
            ticket.Status = BackgroundJobStatus.Completed;
            ticket.ProgressPercentage = 100;
            ticket.CompletedAt = DateTime.UtcNow;
            ticket.CurrentStep = "Completed successfully";
            ticket.ResultData = resultData;
            ticket.ResultContentType = contentType ?? "application/pdf";
            ticket.ResultFileName = fileName ?? ticket.ResultFileName;
            ticket.ResultDownloadUrl = downloadUrl ?? $"/api/jobs/{jobId}/download";
            _logger.LogInformation("Background Job #{JobId} [{JobType}] completed successfully.", jobId, ticket.JobType);
        }
    }

    public void MarkFailed(Guid jobId, string errorMessage)
    {
        if (_jobStore.TryGetValue(jobId, out var ticket))
        {
            ticket.Status = BackgroundJobStatus.Failed;
            ticket.CompletedAt = DateTime.UtcNow;
            ticket.ErrorMessage = errorMessage;
            ticket.CurrentStep = $"Failed: {errorMessage}";
            _logger.LogError("Background Job #{JobId} [{JobType}] failed: {Error}", jobId, ticket.JobType, errorMessage);
        }
    }
}
