using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;

namespace Nsdms.Infrastructure.Services;

public class NotificationService : INotificationService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly ISignalRNotificationPublisher? _publisher;
    private readonly IAuditService _audit;

    public NotificationService(
        INsdmsDbContextFactory contextFactory, 
        IAuditService audit, 
        ISignalRNotificationPublisher? publisher = null)
    {
        _contextFactory = contextFactory;
        _audit = audit;
        _publisher = publisher;
    }

    public async Task<SystemNotificationDto> SendNotificationAsync(
        string? recipientUsername, 
        string? recipientRole, 
        string title, 
        string message, 
        string? actionUrl = null, 
        string notificationType = "SystemAlert", 
        string severity = "Info", 
        string actor = "SYSTEM",
        string? bodyHtml = null,
        string? senderDisplayName = null,
        int? broadcastMessageId = null,
        bool hasAttachment = false,
        string? attachmentFileName = null,
        string? attachmentStoragePath = null,
        long? attachmentSizeBytes = null,
        string? attachmentContentType = null,
        bool sendEmail = false)
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var notification = new SystemNotification
        {
            RecipientUsername = string.IsNullOrWhiteSpace(recipientUsername) ? null : recipientUsername.Trim(),
            RecipientRole = string.IsNullOrWhiteSpace(recipientRole) ? null : recipientRole.Trim(),
            Title = title.Trim(),
            Message = message.Trim(),
            BodyHtml = bodyHtml,
            SenderDisplayName = senderDisplayName,
            BroadcastMessageId = broadcastMessageId,
            ActionUrl = actionUrl,
            NotificationType = notificationType,
            Severity = severity,
            IsRead = false,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = actor,
            HasAttachment = hasAttachment,
            AttachmentFileName = attachmentFileName,
            AttachmentStoragePath = attachmentStoragePath,
            AttachmentSizeBytes = attachmentSizeBytes,
            AttachmentContentType = attachmentContentType
        };

        db.SystemNotifications.Add(notification);

        // If sendEmail requested and recipientUsername looks like an email or can be resolved
        if (sendEmail && !string.IsNullOrWhiteSpace(recipientUsername) && recipientUsername.Contains('@'))
        {
            var emailItem = new EmailOutboxItem
            {
                RecipientEmail = recipientUsername.Trim(),
                RecipientName = recipientUsername.Trim(),
                Subject = title.Trim(),
                BodyHtml = bodyHtml ?? message.Trim(),
                HasAttachment = hasAttachment,
                AttachmentFileName = attachmentFileName,
                AttachmentStoragePath = attachmentStoragePath,
                AttachmentContentType = attachmentContentType,
                Status = "Pending",
                SourceModule = notificationType,
                SourceReferenceId = notification.Id.ToString(),
                CreatedAt = DateTime.UtcNow,
                CreatedBy = actor
            };
            db.EmailOutboxItems.Add(emailItem);
        }

        await db.SaveChangesAsync();

        var dto = new SystemNotificationDto
        {
            Id = notification.Id,
            RecipientUsername = notification.RecipientUsername,
            RecipientRole = notification.RecipientRole,
            Title = notification.Title,
            Message = notification.Message,
            BodyHtml = notification.BodyHtml,
            SenderDisplayName = notification.SenderDisplayName,
            BroadcastMessageId = notification.BroadcastMessageId,
            ActionUrl = notification.ActionUrl,
            NotificationType = notification.NotificationType,
            Severity = notification.Severity,
            IsRead = notification.IsRead,
            CreatedAt = notification.CreatedAt,
            HasAttachment = notification.HasAttachment,
            AttachmentFileName = notification.AttachmentFileName,
            AttachmentStoragePath = notification.AttachmentStoragePath,
            AttachmentSizeBytes = notification.AttachmentSizeBytes,
            AttachmentContentType = notification.AttachmentContentType
        };

        // Real-time SignalR push dispatch
        if (_publisher != null)
        {
            try
            {
                await _publisher.PublishNotificationAsync(dto);
            }
            catch
            {
                // Graceful fallback if client circuit disconnected
            }
        }

        return dto;
    }

    public async Task<SystemNotificationDto?> GetNotificationByIdAsync(int id, string? username = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var entity = await db.SystemNotifications.AsNoTracking().FirstOrDefaultAsync(n => n.Id == id);
        if (entity == null) return null;

        return new SystemNotificationDto
        {
            Id = entity.Id,
            RecipientUsername = entity.RecipientUsername,
            RecipientRole = entity.RecipientRole,
            Title = entity.Title,
            Message = entity.Message,
            BodyHtml = entity.BodyHtml,
            SenderDisplayName = entity.SenderDisplayName,
            BroadcastMessageId = entity.BroadcastMessageId,
            ActionUrl = entity.ActionUrl,
            NotificationType = entity.NotificationType,
            Severity = entity.Severity,
            IsRead = entity.IsRead,
            ReadAt = entity.ReadAt,
            CreatedAt = entity.CreatedAt,
            HasAttachment = entity.HasAttachment,
            AttachmentFileName = entity.AttachmentFileName,
            AttachmentStoragePath = entity.AttachmentStoragePath,
            AttachmentSizeBytes = entity.AttachmentSizeBytes,
            AttachmentContentType = entity.AttachmentContentType
        };
    }

    public async Task<List<SystemNotificationDto>> GetUserNotificationsAsync(
        string username, 
        List<string>? userRoles = null, 
        bool unreadOnly = false, 
        int maxCount = 20)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var normalizedUser = username.Trim();
        var roles = userRoles?.Select(r => r.Trim()).ToList() ?? new List<string>();

        var query = db.SystemNotifications.AsNoTracking().AsQueryable();

        // Match user by username or assigned roles or global broadcast (both null)
        query = query.Where(n =>
            n.RecipientUsername == normalizedUser ||
            (n.RecipientRole != null && roles.Contains(n.RecipientRole)) ||
            (n.RecipientUsername == null && n.RecipientRole == null));

        if (unreadOnly)
        {
            query = query.Where(n => !n.IsRead);
        }

        var results = await query
            .OrderByDescending(n => n.CreatedAt)
            .Take(maxCount)
            .Select(n => new SystemNotificationDto
            {
                Id = n.Id,
                RecipientUsername = n.RecipientUsername,
                RecipientRole = n.RecipientRole,
                Title = n.Title,
                Message = n.Message,
                BodyHtml = n.BodyHtml,
                SenderDisplayName = n.SenderDisplayName,
                BroadcastMessageId = n.BroadcastMessageId,
                ActionUrl = n.ActionUrl,
                NotificationType = n.NotificationType,
                Severity = n.Severity,
                IsRead = n.IsRead,
                ReadAt = n.ReadAt,
                CreatedAt = n.CreatedAt,
                HasAttachment = n.HasAttachment,
                AttachmentFileName = n.AttachmentFileName,
                AttachmentStoragePath = n.AttachmentStoragePath,
                AttachmentSizeBytes = n.AttachmentSizeBytes,
                AttachmentContentType = n.AttachmentContentType
            })
            .ToListAsync();

        return results;
    }

    public async Task<int> GetUnreadCountAsync(string username, List<string>? userRoles = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var normalizedUser = username.Trim();
        var roles = userRoles?.Select(r => r.Trim()).ToList() ?? new List<string>();

        return await db.SystemNotifications
            .AsNoTracking()
            .Where(n => !n.IsRead && (
                n.RecipientUsername == normalizedUser ||
                (n.RecipientRole != null && roles.Contains(n.RecipientRole)) ||
                (n.RecipientUsername == null && n.RecipientRole == null)))
            .CountAsync();
    }

    public async Task<bool> MarkAsReadAsync(int notificationId, string username)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var item = await db.SystemNotifications.FindAsync(notificationId);
        if (item == null) return false;

        item.IsRead = true;
        item.ReadAt = DateTime.UtcNow;
        item.ModifiedAt = DateTime.UtcNow;
        item.ModifiedBy = username;

        await db.SaveChangesAsync();
        return true;
    }

    public async Task<int> MarkAllAsReadAsync(string username, List<string>? userRoles = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var normalizedUser = username.Trim();
        var roles = userRoles?.Select(r => r.Trim()).ToList() ?? new List<string>();

        var unreadItems = await db.SystemNotifications
            .Where(n => !n.IsRead && (
                n.RecipientUsername == normalizedUser ||
                (n.RecipientRole != null && roles.Contains(n.RecipientRole)) ||
                (n.RecipientUsername == null && n.RecipientRole == null)))
            .ToListAsync();

        if (!unreadItems.Any()) return 0;

        var now = DateTime.UtcNow;
        foreach (var item in unreadItems)
        {
            item.IsRead = true;
            item.ReadAt = now;
            item.ModifiedAt = now;
            item.ModifiedBy = username;
        }

        await db.SaveChangesAsync();
        return unreadItems.Count;
    }

    public async Task<bool> DeleteNotificationAsync(int notificationId, string username)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var item = await db.SystemNotifications.FindAsync(notificationId);
        if (item == null) return false;

        db.SystemNotifications.Remove(item);
        await db.SaveChangesAsync();
        return true;
    }
}
