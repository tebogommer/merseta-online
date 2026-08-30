using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;

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
        string actor = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var notification = new SystemNotification
        {
            RecipientUsername = string.IsNullOrWhiteSpace(recipientUsername) ? null : recipientUsername.Trim(),
            RecipientRole = string.IsNullOrWhiteSpace(recipientRole) ? null : recipientRole.Trim(),
            Title = title.Trim(),
            Message = message.Trim(),
            ActionUrl = actionUrl,
            NotificationType = notificationType,
            Severity = severity,
            IsRead = false,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = actor
        };

        db.SystemNotifications.Add(notification);
        await db.SaveChangesAsync();

        var dto = new SystemNotificationDto
        {
            Id = notification.Id,
            RecipientUsername = notification.RecipientUsername,
            RecipientRole = notification.RecipientRole,
            Title = notification.Title,
            Message = notification.Message,
            ActionUrl = notification.ActionUrl,
            NotificationType = notification.NotificationType,
            Severity = notification.Severity,
            IsRead = notification.IsRead,
            CreatedAt = notification.CreatedAt
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
                ActionUrl = n.ActionUrl,
                NotificationType = n.NotificationType,
                Severity = n.Severity,
                IsRead = n.IsRead,
                ReadAt = n.ReadAt,
                CreatedAt = n.CreatedAt
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
