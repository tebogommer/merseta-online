using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Common.Models;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using System.Collections.Concurrent;
using System.Text.Json;

namespace Nsdms.Infrastructure.Services;

/// <summary>
/// Service managing targeted broadcast campaigns, outbox queuing, and Office 365 daily rate limiting.
/// </summary>
public class EmailOutboxService : IEmailOutboxService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;
    private readonly ISignalRNotificationPublisher? _publisher;
    private readonly ILogger<EmailOutboxService> _logger;

    // Static flag for outbox pause/resume across threads
    private static volatile bool _isQueuePaused = false;

    // Default statutory roles
    private static readonly List<string> _standardRoles = new()
    {
        "SDF",
        "Admin",
        "SuperAdmin",
        "ReviewCommittee",
        "Assessor",
        "Moderator",
        "Finance",
        "CLO",
        "Inspector",
        "ETQA",
        "Executive",
        "User"
    };

    public EmailOutboxService(
        INsdmsDbContextFactory contextFactory,
        IAuditService audit,
        ILogger<EmailOutboxService> logger,
        ISignalRNotificationPublisher? publisher = null)
    {
        _contextFactory = contextFactory;
        _audit = audit;
        _logger = logger;
        _publisher = publisher;
    }

    public Task<List<string>> GetAvailableTargetRolesAsync(CancellationToken ct = default)
    {
        return Task.FromResult(new List<string>(_standardRoles));
    }

    public async Task<int> EstimateRecipientCountAsync(string targetType, string? filterValue, CancellationToken ct = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync(ct);

        switch (targetType)
        {
            case "Role":
                if (string.IsNullOrWhiteSpace(filterValue)) return 0;
                var role = await db.Roles.FirstOrDefaultAsync(r => r.Name == filterValue, ct);
                if (role == null) return 0;
                return await db.UserRoles.CountAsync(ur => ur.RoleId == role.Id, ct);

            case "Organisation":
                if (string.IsNullOrWhiteSpace(filterValue) || !int.TryParse(filterValue, out var orgId)) return 0;
                return await db.Users.CountAsync(u => u.DefaultOrganisationId == orgId && u.IsActive, ct);

            case "SpecificUser":
                if (string.IsNullOrWhiteSpace(filterValue)) return 0;
                var normalized = filterValue.ToUpperInvariant();
                var exists = await db.Users.AnyAsync(u => (u.NormalizedUserName == normalized || u.NormalizedEmail == normalized) && u.IsActive, ct);
                return exists ? 1 : 0;

            case "BroadcastAll":
                return await db.Users.CountAsync(u => u.IsActive, ct);

            default:
                return 0;
        }
    }

    public async Task<List<TargetOrganisationOptionDto>> SearchOrganisationsAsync(string query, int maxResults = 15, CancellationToken ct = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync(ct);
        var q = query.Trim().ToLowerInvariant();

        return await db.Organisations
            .AsNoTracking()
            .Where(o => o.CompanyName.ToLower().Contains(q) || (o.SdlNumber != null && o.SdlNumber.ToLower().Contains(q)))
            .OrderBy(o => o.CompanyName)
            .Take(maxResults)
            .Select(o => new TargetOrganisationOptionDto(o.Id, o.CompanyName, o.SdlNumber))
            .ToListAsync(ct);
    }

    public async Task<List<TargetUserOptionDto>> SearchUsersAsync(string query, int maxResults = 15, CancellationToken ct = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync(ct);
        var q = query.Trim().ToLowerInvariant();

        return await db.Users
            .AsNoTracking()
            .Include(u => u.Person)
            .Where(u => u.IsActive && (
                u.UserName.ToLower().Contains(q) ||
                (u.Email != null && u.Email.ToLower().Contains(q)) ||
                (u.Person != null && ((u.Person.FirstName + " " + u.Person.LastName).ToLower().Contains(q)))))
            .OrderBy(u => u.UserName)
            .Take(maxResults)
            .Select(u => new TargetUserOptionDto(
                u.Id,
                u.UserName,
                u.Person != null ? $"{u.Person.FirstName} {u.Person.LastName}" : u.UserName,
                u.Email ?? u.UserName,
                null))
            .ToListAsync(ct);
    }

    public async Task<BroadcastMessageDto> CreateAndDispatchBroadcastAsync(CreateBroadcastRequest request, string username, CancellationToken ct = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync(ct);

        // 1. Persist physical binary attachment to storage disk if provided
        string? attachmentPath = null;
        if (request.AttachmentBytes != null && request.AttachmentBytes.Length > 0 && !string.IsNullOrWhiteSpace(request.AttachmentFileName))
        {
            var storageDir = Path.Combine(AppContext.BaseDirectory, "storage", "broadcasts");
            if (!Directory.Exists(storageDir))
            {
                Directory.CreateDirectory(storageDir);
            }

            var safeName = $"{Guid.NewGuid():N}_{Path.GetFileName(request.AttachmentFileName)}";
            attachmentPath = Path.Combine(storageDir, safeName);
            await File.WriteAllBytesAsync(attachmentPath, request.AttachmentBytes, ct);
        }

        // 2. Resolve target recipient user accounts
        List<ApplicationUser> recipients = new();

        switch (request.TargetType)
        {
            case "Role":
                if (!string.IsNullOrWhiteSpace(request.TargetFilterValue))
                {
                    var role = await db.Roles.FirstOrDefaultAsync(r => r.Name == request.TargetFilterValue, ct);
                    if (role != null)
                    {
                        var userIds = await db.UserRoles.Where(ur => ur.RoleId == role.Id).Select(ur => ur.UserId).ToListAsync(ct);
                        recipients = await db.Users.Where(u => userIds.Contains(u.Id) && u.IsActive).Include(u => u.Person).ToListAsync(ct);
                    }
                }
                break;

            case "Organisation":
                if (int.TryParse(request.TargetFilterValue, out var orgId))
                {
                    recipients = await db.Users.Where(u => u.DefaultOrganisationId == orgId && u.IsActive).Include(u => u.Person).ToListAsync(ct);
                }
                break;

            case "SpecificUser":
                if (!string.IsNullOrWhiteSpace(request.TargetFilterValue))
                {
                    var normalized = request.TargetFilterValue.ToUpperInvariant();
                    var user = await db.Users.Include(u => u.Person).FirstOrDefaultAsync(u => (u.NormalizedUserName == normalized || u.NormalizedEmail == normalized) && u.IsActive, ct);
                    if (user != null) recipients.Add(user);
                }
                break;

            case "BroadcastAll":
                recipients = await db.Users.Where(u => u.IsActive).Include(u => u.Person).ToListAsync(ct);
                break;
        }

        // 3. Create Campaign Entity
        var broadcast = new BroadcastMessage
        {
            Subject = request.Subject.Trim(),
            BodyHtml = request.BodyHtml.Trim(),
            TargetType = request.TargetType,
            TargetFilterValue = request.TargetFilterValue,
            TargetFilterDisplay = request.TargetFilterDisplay,
            RecipientCount = recipients.Count,
            HasAttachment = attachmentPath != null,
            AttachmentFileName = request.AttachmentFileName,
            AttachmentStoragePath = attachmentPath,
            AttachmentSizeBytes = request.AttachmentSizeBytes ?? request.AttachmentBytes?.Length,
            AttachmentContentType = request.AttachmentContentType ?? "application/pdf",
            Status = "Dispatched",
            DispatchedAt = DateTime.UtcNow,
            DispatchedBy = username,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = username
        };

        db.BroadcastMessages.Add(broadcast);
        await db.SaveChangesAsync(ct);

        // 4. Batch insert immediate in-app SystemNotification & EmailOutboxItem rows
        var now = DateTime.UtcNow;
        var notificationsToPublish = new List<SystemNotificationDto>();

        foreach (var recipient in recipients)
        {
            var email = recipient.Email ?? recipient.UserName;
            var displayName = recipient.Person != null ? $"{recipient.Person.FirstName} {recipient.Person.LastName}" : recipient.UserName;

            var inAppNotif = new SystemNotification
            {
                RecipientUsername = recipient.UserName,
                RecipientRole = request.TargetType == "Role" ? request.TargetFilterValue : null,
                Title = request.Subject.Trim(),
                Message = $"Official broadcast: {request.Subject.Trim()}",
                BodyHtml = request.BodyHtml.Trim(),
                SenderDisplayName = username,
                BroadcastMessageId = broadcast.Id,
                ActionUrl = $"/notifications",
                NotificationType = "BroadcastCircular",
                Severity = "Info",
                IsRead = false,
                CreatedAt = now,
                CreatedBy = username,
                HasAttachment = attachmentPath != null,
                AttachmentFileName = request.AttachmentFileName,
                AttachmentStoragePath = attachmentPath,
                AttachmentSizeBytes = request.AttachmentSizeBytes ?? request.AttachmentBytes?.Length,
                AttachmentContentType = request.AttachmentContentType
            };
            db.SystemNotifications.Add(inAppNotif);

            var outboxItem = new EmailOutboxItem
            {
                BroadcastMessageId = broadcast.Id,
                RecipientEmail = email,
                RecipientName = displayName,
                Subject = request.Subject.Trim(),
                BodyHtml = request.BodyHtml.Trim(),
                HasAttachment = attachmentPath != null,
                AttachmentFileName = request.AttachmentFileName,
                AttachmentStoragePath = attachmentPath,
                AttachmentContentType = request.AttachmentContentType,
                Status = "Pending",
                SourceModule = "Broadcast",
                SourceReferenceId = broadcast.Id.ToString(),
                AttemptCount = 0,
                MaxAttempts = 5,
                NextAttemptAt = now,
                CreatedAt = now,
                CreatedBy = username
            };
            db.EmailOutboxItems.Add(outboxItem);
        }

        await db.SaveChangesAsync(ct);

        // Double write audit
        await _audit.LogActionAsync(
            "BroadcastMessage",
            broadcast.Id,
            "BroadcastCampaignDispatched",
            username,
            null,
            new { broadcast.Id, broadcast.Subject, broadcast.RecipientCount, broadcast.TargetType });

        return new BroadcastMessageDto(
            broadcast.Id,
            broadcast.Subject,
            broadcast.TargetType,
            broadcast.TargetFilterDisplay,
            broadcast.RecipientCount,
            broadcast.HasAttachment,
            broadcast.AttachmentFileName,
            broadcast.Status,
            broadcast.DispatchedAt,
            broadcast.DispatchedBy,
            broadcast.CreatedAt);
    }

    public async Task<PagedResult<BroadcastMessageDto>> GetBroadcastsPagedAsync(PaginationQuery query, string? status = null, string? search = null, CancellationToken ct = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync(ct);
        var q = db.BroadcastMessages.AsNoTracking().AsQueryable();

        if (!string.IsNullOrWhiteSpace(status) && status != "All")
        {
            q = q.Where(b => b.Status == status);
        }

        if (!string.IsNullOrWhiteSpace(search))
        {
            var s = search.Trim().ToLowerInvariant();
            q = q.Where(b => b.Subject.ToLower().Contains(s) || (b.TargetFilterDisplay != null && b.TargetFilterDisplay.ToLower().Contains(s)));
        }

        var total = await q.CountAsync(ct);

        var items = await q
            .OrderByDescending(b => b.DispatchedAt)
            .Skip(query.PageIndex * query.PageSize)
            .Take(query.PageSize)
            .Select(b => new BroadcastMessageDto(
                b.Id,
                b.Subject,
                b.TargetType,
                b.TargetFilterDisplay,
                b.RecipientCount,
                b.HasAttachment,
                b.AttachmentFileName,
                b.Status,
                b.DispatchedAt,
                b.DispatchedBy,
                b.CreatedAt))
            .ToListAsync(ct);

        return new PagedResult<BroadcastMessageDto>(items, total, query.PageIndex, query.PageSize);
    }

    public async Task<BroadcastMessageDetailDto?> GetBroadcastByIdAsync(int id, CancellationToken ct = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync(ct);
        var b = await db.BroadcastMessages.AsNoTracking().FirstOrDefaultAsync(x => x.Id == id, ct);
        if (b == null) return null;

        var outboxItems = await db.EmailOutboxItems
            .AsNoTracking()
            .Where(e => e.BroadcastMessageId == id)
            .OrderByDescending(e => e.CreatedAt)
            .Take(250)
            .Select(e => new EmailOutboxItemDto(
                e.Id,
                e.BroadcastMessageId,
                e.RecipientEmail,
                e.RecipientName,
                e.RecipientName,
                e.Subject,
                e.Status,
                e.AttemptCount,
                e.NextAttemptAt,
                e.SentAt,
                e.LastError,
                e.SourceModule,
                e.HasAttachment,
                e.AttachmentFileName,
                e.CreatedAt))
            .ToListAsync(ct);

        var sentCount = await db.EmailOutboxItems.CountAsync(e => e.BroadcastMessageId == id && e.Status == "Sent", ct);
        var pendingCount = await db.EmailOutboxItems.CountAsync(e => e.BroadcastMessageId == id && (e.Status == "Pending" || e.Status == "InFlight"), ct);
        var throttledCount = await db.EmailOutboxItems.CountAsync(e => e.BroadcastMessageId == id && e.Status == "Throttled", ct);
        var failedCount = await db.EmailOutboxItems.CountAsync(e => e.BroadcastMessageId == id && e.Status == "Failed", ct);

        return new BroadcastMessageDetailDto(
            b.Id,
            b.Subject,
            b.BodyHtml,
            b.TargetType,
            b.TargetFilterDisplay,
            b.RecipientCount,
            sentCount,
            pendingCount,
            throttledCount,
            failedCount,
            b.HasAttachment,
            b.AttachmentFileName,
            b.AttachmentStoragePath,
            b.AttachmentSizeBytes,
            b.AttachmentContentType,
            b.Status,
            b.CreatedAt,
            b.CreatedBy ?? b.DispatchedBy,
            outboxItems);
    }

    public async Task<EmailDailyQuotaStatusDto> GetDailyQuotaStatusAsync(CancellationToken ct = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync(ct);
        var today = DateTime.UtcNow.Date;

        var tracker = await db.EmailDailyQuotaTrackers.FirstOrDefaultAsync(q => q.QuotaDate == today, ct);
        if (tracker == null)
        {
            tracker = new EmailDailyQuotaTracker
            {
                QuotaDate = today,
                SentCount = 0,
                ThrottledCount = 0,
                FailedCount = 0,
                DailyLimit = 10000,
                IsLimitReached = false,
                CreatedAt = DateTime.UtcNow
            };
            db.EmailDailyQuotaTrackers.Add(tracker);
            await db.SaveChangesAsync(ct);
        }

        var pendingCount = await db.EmailOutboxItems.CountAsync(e => e.Status == "Pending" || e.Status == "Throttled" || e.Status == "InFlight", ct);
        var throttledQueueCount = await db.EmailOutboxItems.CountAsync(e => e.Status == "Throttled", ct);
        var failedQueueCount = await db.EmailOutboxItems.CountAsync(e => e.Status == "Failed", ct);
        var util = tracker.DailyLimit > 0 ? (tracker.SentCount / (double)tracker.DailyLimit) * 100.0 : 0.0;

        // Current rolling window usage (last 1 minute)
        var oneMinuteAgo = DateTime.UtcNow.AddMinutes(-1);
        var currentWindowUsage = await db.EmailOutboxItems.CountAsync(e => e.SentAt != null && e.SentAt >= oneMinuteAgo, ct);

        return new EmailDailyQuotaStatusDto(
            tracker.SentCount,
            tracker.DailyLimit,
            tracker.ThrottledCount,
            tracker.FailedCount,
            Math.Round(util, 2),
            tracker.IsLimitReached,
            _isQueuePaused,
            pendingCount,
            30, // 30 emails/min rate limit
            currentWindowUsage,
            throttledQueueCount,
            failedQueueCount);
    }

    public async Task<PagedResult<EmailOutboxItemDto>> GetOutboxQueuePagedAsync(PaginationQuery query, string? status = null, string? search = null, CancellationToken ct = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync(ct);
        var q = db.EmailOutboxItems.AsNoTracking().AsQueryable();

        if (!string.IsNullOrWhiteSpace(status) && status != "All")
        {
            q = q.Where(e => e.Status == status);
        }

        if (!string.IsNullOrWhiteSpace(search))
        {
            var s = search.Trim().ToLowerInvariant();
            q = q.Where(e => e.RecipientEmail.ToLower().Contains(s) || e.Subject.ToLower().Contains(s));
        }

        var total = await q.CountAsync(ct);

        var items = await q
            .OrderByDescending(e => e.CreatedAt)
            .Skip(query.PageIndex * query.PageSize)
            .Take(query.PageSize)
            .Select(e => new EmailOutboxItemDto(
                e.Id,
                e.BroadcastMessageId,
                e.RecipientEmail,
                e.RecipientName,
                e.RecipientName,
                e.Subject,
                e.Status,
                e.AttemptCount,
                e.NextAttemptAt,
                e.SentAt,
                e.LastError,
                e.SourceModule,
                e.HasAttachment,
                e.AttachmentFileName,
                e.CreatedAt))
            .ToListAsync(ct);

        return new PagedResult<EmailOutboxItemDto>(items, total, query.PageIndex, query.PageSize);
    }

    public Task PauseOutboxQueueAsync(string username, CancellationToken ct = default)
    {
        _isQueuePaused = true;
        _logger.LogWarning("Email outbox queue paused by user {Username}", username);
        return Task.CompletedTask;
    }

    public Task ResumeOutboxQueueAsync(string username, CancellationToken ct = default)
    {
        _isQueuePaused = false;
        _logger.LogInformation("Email outbox queue resumed by user {Username}", username);
        return Task.CompletedTask;
    }

    public async Task RetryOutboxItemAsync(long id, string username, CancellationToken ct = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync(ct);
        var item = await db.EmailOutboxItems.FindAsync(new object[] { id }, ct);
        if (item != null)
        {
            item.Status = "Pending";
            item.AttemptCount = 0;
            item.NextAttemptAt = DateTime.UtcNow;
            item.LastError = null;
            item.ModifiedAt = DateTime.UtcNow;
            item.ModifiedBy = username;
            await db.SaveChangesAsync(ct);
        }
    }

    public async Task<int> RetryAllFailedOutboxItemsAsync(string username, CancellationToken ct = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync(ct);
        var failedItems = await db.EmailOutboxItems
            .Where(e => e.Status == "Failed" || e.Status == "Throttled")
            .ToListAsync(ct);

        if (!failedItems.Any()) return 0;

        var now = DateTime.UtcNow;
        foreach (var item in failedItems)
        {
            item.Status = "Pending";
            item.AttemptCount = 0;
            item.NextAttemptAt = now;
            item.LastError = null;
            item.ModifiedAt = now;
            item.ModifiedBy = username;
        }

        await db.SaveChangesAsync(ct);
        return failedItems.Count;
    }

    public async Task<byte[]?> GetAttachmentBytesAsync(string storagePath, CancellationToken ct = default)
    {
        if (string.IsNullOrWhiteSpace(storagePath) || !File.Exists(storagePath)) return null;
        return await File.ReadAllBytesAsync(storagePath, ct);
    }

    public async Task EnqueueEmailAsync(EmailOutboxRequest request, CancellationToken ct = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync(ct);

        var item = new EmailOutboxItem
        {
            RecipientEmail = request.RecipientEmail.Trim(),
            RecipientName = request.RecipientName,
            Subject = request.Subject.Trim(),
            BodyHtml = request.BodyHtml,
            SourceModule = request.SourceModule,
            SourceReferenceId = request.SourceReferenceId,
            HasAttachment = !string.IsNullOrWhiteSpace(request.AttachmentStoragePath),
            AttachmentFileName = request.AttachmentFileName,
            AttachmentStoragePath = request.AttachmentStoragePath,
            AttachmentContentType = request.AttachmentContentType,
            Status = "Pending",
            AttemptCount = 0,
            MaxAttempts = 5,
            NextAttemptAt = DateTime.UtcNow,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "SYSTEM"
        };

        db.EmailOutboxItems.Add(item);
        await db.SaveChangesAsync(ct);
    }
}
