using Nsdms.Application.Common.Models;

namespace Nsdms.Application.Common.Interfaces;

public record TargetOrganisationOptionDto(int Id, string LegalName, string? SdlNumber);

public record TargetUserOptionDto(int Id, string Username, string FullName, string Email, string? Role);

public record CreateBroadcastRequest(
    string Subject,
    string BodyHtml,
    string TargetType,
    string? TargetFilterValue,
    string? TargetFilterDisplay,
    List<int>? SelectedUserIds = null,
    string? AttachmentFileName = null,
    string? AttachmentContentType = null,
    byte[]? AttachmentBytes = null,
    long? AttachmentSizeBytes = null);

public record BroadcastMessageDto(
    int Id,
    string Subject,
    string TargetType,
    string? TargetFilterDisplay,
    int RecipientCount,
    bool HasAttachment,
    string? AttachmentFileName,
    string Status,
    DateTime? DispatchedAt,
    string DispatchedBy,
    DateTime CreatedAt);

public record BroadcastMessageDetailDto(
    int Id,
    string Subject,
    string BodyHtml,
    string TargetType,
    string? TargetFilterDisplay,
    int RecipientCount,
    int SentEmailsCount,
    int PendingEmailsCount,
    int ThrottledEmailsCount,
    int FailedEmailsCount,
    bool HasAttachment,
    string? AttachmentFileName,
    string? AttachmentStoragePath,
    long? AttachmentSizeBytes,
    string? AttachmentContentType,
    string Status,
    DateTime CreatedAt,
    string CreatedBy,
    List<EmailOutboxItemDto> OutboxItems);

public record EmailDailyQuotaStatusDto(
    int SentCount,
    int DailyLimit,
    int ThrottledCount,
    int FailedCount,
    double UtilizationPercentage,
    bool IsLimitReached,
    bool IsQueuePaused,
    int PendingQueueCount,
    int RatePerMinuteLimit,
    int CurrentWindowUsage,
    int ThrottledQueueCount = 0,
    int FailedQueueCount = 0)
{
    public int RemainingQuota => Math.Max(0, DailyLimit - SentCount);
    public int ConfiguredMaxPerMinute => RatePerMinuteLimit;
}

public record EmailOutboxItemDto(
    long Id,
    int? BroadcastMessageId,
    string RecipientEmail,
    string? RecipientName,
    string? RecipientUsername,
    string Subject,
    string Status,
    int AttemptCount,
    DateTime? NextAttemptAt,
    DateTime? SentAt,
    string? LastError,
    string SourceModule,
    bool HasAttachment,
    string? AttachmentFileName,
    DateTime CreatedAt);

public record EmailOutboxRequest(
    string RecipientEmail,
    string? RecipientName,
    string Subject,
    string BodyHtml,
    string SourceModule = "Workflow",
    string? SourceReferenceId = null,
    string? AttachmentStoragePath = null,
    string? AttachmentFileName = null,
    string? AttachmentContentType = null);

public interface IEmailOutboxService
{
    Task<List<string>> GetAvailableTargetRolesAsync(CancellationToken ct = default);

    Task<int> EstimateRecipientCountAsync(string targetType, string? filterValue, CancellationToken ct = default);

    Task<List<TargetOrganisationOptionDto>> SearchOrganisationsAsync(string query, int maxResults = 15, CancellationToken ct = default);

    Task<List<TargetUserOptionDto>> SearchUsersAsync(string query, int maxResults = 15, CancellationToken ct = default);

    Task<BroadcastMessageDto> CreateAndDispatchBroadcastAsync(CreateBroadcastRequest request, string username, CancellationToken ct = default);

    Task<PagedResult<BroadcastMessageDto>> GetBroadcastsPagedAsync(PaginationQuery query, string? status = null, string? search = null, CancellationToken ct = default);

    Task<BroadcastMessageDetailDto?> GetBroadcastByIdAsync(int id, CancellationToken ct = default);

    Task<EmailDailyQuotaStatusDto> GetDailyQuotaStatusAsync(CancellationToken ct = default);

    Task<PagedResult<EmailOutboxItemDto>> GetOutboxQueuePagedAsync(PaginationQuery query, string? status = null, string? search = null, CancellationToken ct = default);

    Task PauseOutboxQueueAsync(string username, CancellationToken ct = default);

    Task ResumeOutboxQueueAsync(string username, CancellationToken ct = default);

    Task RetryOutboxItemAsync(long id, string username, CancellationToken ct = default);

    Task<int> RetryAllFailedOutboxItemsAsync(string username, CancellationToken ct = default);

    Task<byte[]?> GetAttachmentBytesAsync(string storagePath, CancellationToken ct = default);

    Task EnqueueEmailAsync(EmailOutboxRequest request, CancellationToken ct = default);
}
