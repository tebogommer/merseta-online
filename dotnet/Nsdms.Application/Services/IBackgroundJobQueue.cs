using System.Threading.Channels;

namespace Nsdms.Application.Services;

public enum BackgroundJobStatus
{
    Queued = 0,
    Running = 1,
    Completed = 2,
    Failed = 3,
    Canceled = 4
}

public class BackgroundJobTicket
{
    public Guid JobId { get; set; } = Guid.NewGuid();
    public string JobType { get; set; } = string.Empty;
    public string Description { get; set; } = string.Empty;
    public BackgroundJobStatus Status { get; set; } = BackgroundJobStatus.Queued;
    public int ProgressPercentage { get; set; } = 0;
    public string? CurrentStep { get; set; }
    public string RequestedBy { get; set; } = "SYSTEM";
    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    public DateTime? StartedAt { get; set; }
    public DateTime? CompletedAt { get; set; }
    public string? PayloadJson { get; set; }
    public string? ErrorMessage { get; set; }
    
    // Result payload
    public string? ResultDownloadUrl { get; set; }
    public string? ResultFileName { get; set; }
    public string? ResultContentType { get; set; }
    public byte[]? ResultData { get; set; }
}

public interface IBackgroundJobQueue
{
    ChannelReader<BackgroundJobTicket> Reader { get; }

    ValueTask<BackgroundJobTicket> EnqueueAsync(BackgroundJobTicket ticket, CancellationToken cancellationToken = default);
    
    ValueTask<BackgroundJobTicket> EnqueueDocumentGenerationAsync(
        string documentType,
        int recordId,
        string requestedBy,
        string? fileName = null,
        CancellationToken cancellationToken = default);

    ValueTask<BackgroundJobTicket> EnqueueSarsLevyIngestionAsync(
        string fileName,
        byte[] fileData,
        string requestedBy,
        CancellationToken cancellationToken = default);

    BackgroundJobTicket? GetJob(Guid jobId);
    
    IEnumerable<BackgroundJobTicket> GetRecentJobs(int limit = 50);
    
    IEnumerable<BackgroundJobTicket> GetUserJobs(string requestedBy, int limit = 20);
    
    void UpdateProgress(Guid jobId, int progressPercentage, string? currentStep = null);
    
    void MarkCompleted(Guid jobId, byte[]? resultData, string? contentType, string? fileName, string? downloadUrl = null);
    
    void MarkFailed(Guid jobId, string errorMessage);
}
