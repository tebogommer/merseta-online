using System.Collections.Concurrent;
using System.Text.Json;
using System.Threading.Channels;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Nsdms.Application.Common;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;

namespace Nsdms.Infrastructure.Services;

public class InMemoryBackgroundJobQueue : IBackgroundJobQueue
{
    private readonly Channel<BackgroundJobTicket> _highPriorityChannel;
    private readonly Channel<BackgroundJobTicket> _batchChannel;
    private readonly ConcurrentDictionary<Guid, BackgroundJobTicket> _jobStore = new();
    private readonly ILogger<InMemoryBackgroundJobQueue> _logger;
    private readonly IServiceScopeFactory? _scopeFactory;

    public ChannelReader<BackgroundJobTicket> Reader => _highPriorityChannel.Reader;
    public ChannelReader<BackgroundJobTicket> BatchReader => _batchChannel.Reader;

    public InMemoryBackgroundJobQueue(
        ILogger<InMemoryBackgroundJobQueue> logger,
        IServiceScopeFactory? scopeFactory = null,
        int capacity = 1000)
    {
        _logger = logger;
        _scopeFactory = scopeFactory;
        var options = new BoundedChannelOptions(capacity)
        {
            FullMode = BoundedChannelFullMode.Wait,
            SingleReader = false,
            SingleWriter = false
        };
        _highPriorityChannel = Channel.CreateBounded<BackgroundJobTicket>(options);
        _batchChannel = Channel.CreateBounded<BackgroundJobTicket>(options);
    }

    public async ValueTask<BackgroundJobTicket> EnqueueAsync(BackgroundJobTicket ticket, CancellationToken cancellationToken = default)
    {
        PruneOldJobs();
        _jobStore[ticket.JobId] = ticket;

        bool isBatch = string.Equals(ticket.JobType, "SarsLevyIngestion", StringComparison.OrdinalIgnoreCase) ||
                       ticket.JobType.Contains("Batch", StringComparison.OrdinalIgnoreCase) ||
                       ticket.JobType.Contains("Extract", StringComparison.OrdinalIgnoreCase);

        if (isBatch)
        {
            await _batchChannel.Writer.WriteAsync(ticket, cancellationToken);
            _logger.LogInformation("Background Job #{JobId} [{JobType}] enqueued in [BatchChannel] by '{User}'.", ticket.JobId, ticket.JobType, ticket.RequestedBy);
        }
        else
        {
            await _highPriorityChannel.Writer.WriteAsync(ticket, cancellationToken);
            _logger.LogInformation("Background Job #{JobId} [{JobType}] enqueued in [HighPriorityChannel] by '{User}'.", ticket.JobId, ticket.JobType, ticket.RequestedBy);
        }

        PersistTicketState(ticket, "Queued");
        return ticket;
    }

    private void PruneOldJobs()
    {
        if (_jobStore.Count <= 500) return;

        var cutoff = DateTime.UtcNow.AddHours(-2);
        var oldJobKeys = _jobStore
            .Where(kvp => kvp.Value.CompletedAt.HasValue && kvp.Value.CompletedAt.Value < cutoff)
            .Select(kvp => kvp.Key)
            .Take(100)
            .ToList();

        foreach (var key in oldJobKeys)
        {
            _jobStore.TryRemove(key, out _);
        }
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
            PersistTicketState(ticket, "Running");
        }
    }

    public void MarkCompleted(Guid jobId, byte[]? resultData, string? contentType, string? fileName, string? downloadUrl = null, string? storagePath = null)
    {
        if (_jobStore.TryGetValue(jobId, out var ticket))
        {
            ticket.Status = BackgroundJobStatus.Completed;
            ticket.ProgressPercentage = 100;
            ticket.CompletedAt = DateTime.UtcNow;
            ticket.CurrentStep = "Completed successfully";
            ticket.ResultStoragePath = storagePath;
            ticket.StorageUri = storagePath;
            ticket.ResultData = string.IsNullOrEmpty(storagePath) ? resultData : null;
            ticket.ResultContentType = contentType ?? "application/pdf";
            ticket.ResultFileName = fileName ?? ticket.ResultFileName;
            ticket.ResultDownloadUrl = downloadUrl ?? $"/api/jobs/{jobId}/download";
            _logger.LogInformation("Background Job #{JobId} [{JobType}] completed successfully.", jobId, ticket.JobType);
            PersistTicketState(ticket, "Completed");
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
            PersistTicketState(ticket, "Failed");
        }
    }

    private void PersistTicketState(BackgroundJobTicket ticket, string status)
    {
        if (_scopeFactory == null) return;

        _ = Task.Run(async () =>
        {
            try
            {
                using var scope = _scopeFactory.CreateScope();
                var db = scope.ServiceProvider.GetService<INsdmsDbContext>();
                if (db == null) return;

                var existing = await db.BackgroundJobJournals
                    .FirstOrDefaultAsync(j => j.JobGuid == ticket.JobId);

                if (existing == null)
                {
                    db.BackgroundJobJournals.Add(new BackgroundJobJournal
                    {
                        JobGuid = ticket.JobId,
                        JobType = ticket.JobType,
                        Description = ticket.Description,
                        Status = status,
                        ProgressPercentage = ticket.ProgressPercentage,
                        CurrentStep = ticket.CurrentStep,
                        RequestedBy = ticket.RequestedBy,
                        PayloadJson = ticket.PayloadJson,
                        ErrorMessage = ticket.ErrorMessage,
                        ResultFileName = ticket.ResultFileName,
                        ResultContentType = ticket.ResultContentType,
                        StartedAt = ticket.StartedAt,
                        CompletedAt = ticket.CompletedAt,
                        CreatedAt = ticket.CreatedAt
                    });
                }
                else
                {
                    existing.Status = status;
                    existing.ProgressPercentage = ticket.ProgressPercentage;
                    existing.CurrentStep = ticket.CurrentStep;
                    existing.ErrorMessage = ticket.ErrorMessage;
                    existing.StartedAt = ticket.StartedAt;
                    existing.CompletedAt = ticket.CompletedAt;
                    existing.ModifiedAt = DateTime.UtcNow;
                }

                await db.SaveChangesAsync();
            }
            catch (Exception ex)
            {
                _logger.LogDebug(ex, "BackgroundJobJournal persistence notice: {Message}", ex.Message);
            }
        });
    }
}
