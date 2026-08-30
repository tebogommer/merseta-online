using Nsdms.Domain.Entities;

namespace Nsdms.Application.Common.Interfaces;

public interface INotificationService
{
    Task<SystemNotificationDto> SendNotificationAsync(
        string? recipientUsername, 
        string? recipientRole, 
        string title, 
        string message, 
        string? actionUrl = null, 
        string notificationType = "SystemAlert", 
        string severity = "Info", 
        string actor = "SYSTEM");

    Task<List<SystemNotificationDto>> GetUserNotificationsAsync(
        string username, 
        List<string>? userRoles = null, 
        bool unreadOnly = false, 
        int maxCount = 20);

    Task<int> GetUnreadCountAsync(string username, List<string>? userRoles = null);

    Task<bool> MarkAsReadAsync(int notificationId, string username);

    Task<int> MarkAllAsReadAsync(string username, List<string>? userRoles = null);

    Task<bool> DeleteNotificationAsync(int notificationId, string username);
}
