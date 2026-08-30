using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

public class NotificationServiceTests
{
    private (NotificationService notificationService, TestDbContextFactory factory) CreateServices()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var audit = new AuditService(factory);
        var service = new NotificationService(factory, audit, publisher: null);

        return (service, factory);
    }

    [Fact]
    public async Task SendNotificationAsync_ShouldPersistNotification_AndReturnDto()
    {
        var (service, factory) = CreateServices();

        var notification = await service.SendNotificationAsync(
            recipientUsername: "finance_officer@merseta.org.za",
            recipientRole: "FinanceManager",
            title: "Dual-Signoff Required",
            message: "Toyota SA banking details pending secondary executive signoff.",
            actionUrl: "/finance/banking-details/1",
            notificationType: "SignoffRequired",
            severity: "Warning",
            actor: "FirstSignoffUser");

        Assert.NotNull(notification);
        Assert.True(notification.Id > 0);
        Assert.Equal("Dual-Signoff Required", notification.Title);
        Assert.Equal("finance_officer@merseta.org.za", notification.RecipientUsername);
        Assert.Equal("FinanceManager", notification.RecipientRole);
        Assert.False(notification.IsRead);

        using var db = await factory.CreateDbContextAsync();
        var entity = await db.SystemNotifications.FindAsync(notification.Id);
        Assert.NotNull(entity);
        Assert.Equal("Dual-Signoff Required", entity.Title);
        Assert.Equal("SignoffRequired", entity.NotificationType);
    }

    [Fact]
    public async Task GetUserNotificationsAsync_ShouldFilterByUserRoleAndGlobal()
    {
        var (service, _) = CreateServices();

        // 1. Direct user notification
        await service.SendNotificationAsync("thabo_clo", null, "Task Assigned", "Review WSP submission", "/wsp/1", "WorkflowTask");

        // 2. Role notification
        await service.SendNotificationAsync(null, "FinanceManager", "Payment Batch Ready", "Review tranche EFT batch", "/finance/grants", "Approval");

        // 3. Global broadcast
        await service.SendNotificationAsync(null, null, "System Maintenance", "Scheduled maintenance tonight at 22:00", null, "SystemAlert");

        // 4. Other user notification
        await service.SendNotificationAsync("other_user", "Legal", "Legal Case", "Review dispute", null, "SystemAlert");

        // Query for Thabo (who has role FinanceManager)
        var userNotifs = await service.GetUserNotificationsAsync("thabo_clo", new List<string> { "FinanceManager" });

        Assert.Equal(3, userNotifs.Count);
        Assert.Contains(userNotifs, n => n.Title == "Task Assigned");
        Assert.Contains(userNotifs, n => n.Title == "Payment Batch Ready");
        Assert.Contains(userNotifs, n => n.Title == "System Maintenance");
        Assert.DoesNotContain(userNotifs, n => n.Title == "Legal Case");
    }

    [Fact]
    public async Task GetUnreadCountAsync_AndMarkAsRead_ShouldUpdateAccurately()
    {
        var (service, _) = CreateServices();

        var n1 = await service.SendNotificationAsync("clerk_jane", null, "Alert 1", "Message 1");
        var n2 = await service.SendNotificationAsync("clerk_jane", null, "Alert 2", "Message 2");

        var initialCount = await service.GetUnreadCountAsync("clerk_jane");
        Assert.Equal(2, initialCount);

        // Mark n1 as read
        var readSuccess = await service.MarkAsReadAsync(n1.Id, "clerk_jane");
        Assert.True(readSuccess);

        var countAfterOne = await service.GetUnreadCountAsync("clerk_jane");
        Assert.Equal(1, countAfterOne);

        // Mark all as read
        var updatedCount = await service.MarkAllAsReadAsync("clerk_jane");
        Assert.Equal(1, updatedCount);

        var finalCount = await service.GetUnreadCountAsync("clerk_jane");
        Assert.Equal(0, finalCount);
    }

    [Fact]
    public async Task DeleteNotificationAsync_ShouldRemoveFromDatabase()
    {
        var (service, factory) = CreateServices();

        var n = await service.SendNotificationAsync("test_user", null, "Temporary Alert", "Will be deleted");

        var deleted = await service.DeleteNotificationAsync(n.Id, "test_user");
        Assert.True(deleted);

        using var db = await factory.CreateDbContextAsync();
        var entity = await db.SystemNotifications.FindAsync(n.Id);
        Assert.Null(entity);
    }
}
