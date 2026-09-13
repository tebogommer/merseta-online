using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging.Abstractions;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Common.Models;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Services;
using System.Text;
using Xunit;

namespace Nsdms.Tests;

public class BroadcastAndEmailOutboxTests
{
    private (EmailOutboxService service, TestDbContextFactory factory) CreateServices()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var audit = new AuditService(factory);
        var logger = NullLogger<EmailOutboxService>.Instance;
        var service = new EmailOutboxService(factory, audit, logger, publisher: null);

        return (service, factory);
    }

    private async Task SeedUsersAndRolesAsync(TestDbContextFactory factory)
    {
        using var db = (NsdmsDbContext)await factory.CreateDbContextAsync();

        // 1. Roles
        var sdfRole = new ApplicationRole { Id = 1, Name = "SDF", NormalizedName = "SDF" };
        var adminRole = new ApplicationRole { Id = 2, Name = "Admin", NormalizedName = "ADMIN" };
        var cloRole = new ApplicationRole { Id = 3, Name = "CLO", NormalizedName = "CLO" };

        db.Roles.AddRange(sdfRole, adminRole, cloRole);

        // 2. Organisation
        var org = new Organisation
        {
            Id = 101,
            CompanyName = "Toyota South Africa Motors",
            SdlNumber = "L123456789",
            IsActive = true
        };
        db.Organisations.Add(org);

        // 3. People
        var p1 = new Person { Id = 1, FirstName = "Nalini", LastName = "Moodley", EmailAddress = "nalini@toyota.co.za" };
        var p2 = new Person { Id = 2, FirstName = "Sipho", LastName = "Khumalo", EmailAddress = "sipho@toyota.co.za" };
        var p3 = new Person { Id = 3, FirstName = "Thabo", LastName = "Molefe", EmailAddress = "thabo@merseta.org.za" };
        db.People.AddRange(p1, p2, p3);

        // 4. Users
        var u1 = new ApplicationUser
        {
            Id = 1,
            UserName = "nalini.moodley@toyota.co.za",
            NormalizedUserName = "NALINI.MOODLEY@TOYOTA.CO.ZA",
            Email = "nalini@toyota.co.za",
            NormalizedEmail = "NALINI@TOYOTA.CO.ZA",
            PersonId = 1,
            DefaultOrganisationId = 101,
            IsActive = true
        };
        var u2 = new ApplicationUser
        {
            Id = 2,
            UserName = "sipho.khumalo@toyota.co.za",
            NormalizedUserName = "SIPHO.KHUMALO@TOYOTA.CO.ZA",
            Email = "sipho@toyota.co.za",
            NormalizedEmail = "SIPHO@TOYOTA.CO.ZA",
            PersonId = 2,
            DefaultOrganisationId = 101,
            IsActive = true
        };
        var u3 = new ApplicationUser
        {
            Id = 3,
            UserName = "thabo.molefe@merseta.org.za",
            NormalizedUserName = "THABO.MOLEFE@MERSETA.ORG.ZA",
            Email = "thabo@merseta.org.za",
            NormalizedEmail = "THABO@MERSETA.ORG.ZA",
            PersonId = 3,
            DefaultOrganisationId = null,
            IsActive = true
        };
        db.Users.AddRange(u1, u2, u3);

        // 5. User Roles (U1: SDF, U2: SDF, U3: CLO)
        db.UserRoles.AddRange(
            new IdentityUserRole<int> { UserId = 1, RoleId = 1 },
            new IdentityUserRole<int> { UserId = 2, RoleId = 1 },
            new IdentityUserRole<int> { UserId = 3, RoleId = 3 }
        );

        await db.SaveChangesAsync();
    }

    [Fact]
    public async Task TargetRecipientEstimation_ShouldReturnCorrectCounts()
    {
        var (service, factory) = CreateServices();
        await SeedUsersAndRolesAsync(factory);

        // Estimation by Role SDF -> 2
        var sdfCount = await service.EstimateRecipientCountAsync("Role", "SDF");
        Assert.Equal(2, sdfCount);

        // Estimation by Organisation 101 -> 2
        var orgCount = await service.EstimateRecipientCountAsync("Organisation", "101");
        Assert.Equal(2, orgCount);

        // Estimation by SpecificUser -> 1
        var userCount = await service.EstimateRecipientCountAsync("SpecificUser", "thabo.molefe@merseta.org.za");
        Assert.Equal(1, userCount);

        // Estimation for All -> 3
        var allCount = await service.EstimateRecipientCountAsync("BroadcastAll", null);
        Assert.Equal(3, allCount);
    }

    [Fact]
    public async Task CreateAndDispatchBroadcast_ByRole_ShouldCreateInAppNotificationsAndOutboxEmails()
    {
        var (service, factory) = CreateServices();
        await SeedUsersAndRolesAsync(factory);

        var request = new CreateBroadcastRequest(
            Subject: "Urgent WSP Circular 2026",
            BodyHtml: "<p>Please ensure all WSP submissions are finalised by 30 April.</p>",
            TargetType: "Role",
            TargetFilterValue: "SDF",
            TargetFilterDisplay: "Role: Skills Development Facilitators (SDF)");

        var broadcast = await service.CreateAndDispatchBroadcastAsync(request, "admin@merseta.org.za");

        Assert.NotNull(broadcast);
        Assert.True(broadcast.Id > 0);
        Assert.Equal(2, broadcast.RecipientCount);
        Assert.Equal("Urgent WSP Circular 2026", broadcast.Subject);
        Assert.Equal("Dispatched", broadcast.Status);

        using var db = await factory.CreateDbContextAsync();

        // Verify In-App Notifications
        var inAppNotifs = await db.SystemNotifications
            .Where(n => n.BroadcastMessageId == broadcast.Id)
            .ToListAsync();
        Assert.Equal(2, inAppNotifs.Count);
        Assert.All(inAppNotifs, n =>
        {
            Assert.Equal("Urgent WSP Circular 2026", n.Title);
            Assert.Contains("30 April", n.BodyHtml);
            Assert.False(n.IsRead);
            Assert.Equal("BroadcastCircular", n.NotificationType);
        });

        // Verify Rate-Limited Email Outbox Items
        var outboxItems = await db.EmailOutboxItems
            .Where(e => e.BroadcastMessageId == broadcast.Id)
            .ToListAsync();
        Assert.Equal(2, outboxItems.Count);
        Assert.All(outboxItems, e =>
        {
            Assert.Equal("Pending", e.Status);
            Assert.Equal(0, e.AttemptCount);
            Assert.Equal("Urgent WSP Circular 2026", e.Subject);
            Assert.Equal("Broadcast", e.SourceModule);
        });
        Assert.Contains(outboxItems, e => e.RecipientEmail == "nalini@toyota.co.za");
        Assert.Contains(outboxItems, e => e.RecipientEmail == "sipho@toyota.co.za");
    }

    [Fact]
    public async Task CreateAndDispatchBroadcast_WithPhysicalBinaryAttachment_ShouldStoreAndLinkCorrectly()
    {
        var (service, factory) = CreateServices();
        await SeedUsersAndRolesAsync(factory);

        var fakePdfBytes = Encoding.UTF8.GetBytes("%PDF-1.4 Fake signed CEO circular document for testing");

        var request = new CreateBroadcastRequest(
            Subject: "Executive Notice with CEO Signed Letter",
            BodyHtml: "<p>Attached is the official signed communique from the Chief Executive Officer.</p>",
            TargetType: "SpecificUser",
            TargetFilterValue: "thabo.molefe@merseta.org.za",
            TargetFilterDisplay: "User: Thabo Molefe",
            AttachmentFileName: "CEO_Circular_2026.pdf",
            AttachmentContentType: "application/pdf",
            AttachmentBytes: fakePdfBytes,
            AttachmentSizeBytes: fakePdfBytes.Length);

        var broadcast = await service.CreateAndDispatchBroadcastAsync(request, "ceo@merseta.org.za");

        Assert.True(broadcast.HasAttachment);
        Assert.Equal("CEO_Circular_2026.pdf", broadcast.AttachmentFileName);

        using var db = await factory.CreateDbContextAsync();
        var entity = await db.BroadcastMessages.FindAsync(broadcast.Id);
        Assert.NotNull(entity);
        Assert.NotNull(entity.AttachmentStoragePath);
        Assert.True(File.Exists(entity.AttachmentStoragePath));

        // Verify attachment can be retrieved via service
        var retrievedBytes = await service.GetAttachmentBytesAsync(entity.AttachmentStoragePath);
        Assert.NotNull(retrievedBytes);
        Assert.Equal(fakePdfBytes.Length, retrievedBytes.Length);

        // Verify outbox item has attachment path stamped
        var outboxItem = await db.EmailOutboxItems.FirstOrDefaultAsync(e => e.BroadcastMessageId == broadcast.Id);
        Assert.NotNull(outboxItem);
        Assert.True(outboxItem.HasAttachment);
        Assert.Equal("CEO_Circular_2026.pdf", outboxItem.AttachmentFileName);
        Assert.Equal(entity.AttachmentStoragePath, outboxItem.AttachmentStoragePath);

        // Clean up created file
        try
        {
            if (File.Exists(entity.AttachmentStoragePath)) File.Delete(entity.AttachmentStoragePath);
        }
        catch { }
    }

    [Fact]
    public async Task DailyQuotaStatus_ShouldAccuratelyCalculateCeilingAndRemaining()
    {
        var (service, factory) = CreateServices();

        var initialQuota = await service.GetDailyQuotaStatusAsync();
        Assert.Equal(0, initialQuota.SentCount);
        Assert.Equal(10000, initialQuota.DailyLimit);
        Assert.Equal(10000, initialQuota.RemainingQuota);
        Assert.Equal(0, initialQuota.UtilizationPercentage);
        Assert.False(initialQuota.IsLimitReached);

        // Simulate sending 2500 emails
        using var db = await factory.CreateDbContextAsync();
        var tracker = await db.EmailDailyQuotaTrackers.FirstAsync();
        tracker.SentCount = 2500;
        await db.SaveChangesAsync();

        var updatedQuota = await service.GetDailyQuotaStatusAsync();
        Assert.Equal(2500, updatedQuota.SentCount);
        Assert.Equal(7500, updatedQuota.RemainingQuota);
        Assert.Equal(25.0, updatedQuota.UtilizationPercentage);
        Assert.False(updatedQuota.IsLimitReached);
    }

    [Fact]
    public async Task OutboxQueueManagement_PauseResumeAndRetry_ShouldWorkAsExpected()
    {
        var (service, factory) = CreateServices();

        // 1. Pause & Resume Queue
        await service.PauseOutboxQueueAsync("admin_user");
        var statusPaused = await service.GetDailyQuotaStatusAsync();
        Assert.True(statusPaused.IsQueuePaused);

        await service.ResumeOutboxQueueAsync("admin_user");
        var statusResumed = await service.GetDailyQuotaStatusAsync();
        Assert.False(statusResumed.IsQueuePaused);

        // 2. Retry specific failed item
        using var db = await factory.CreateDbContextAsync();
        var failedItem = new EmailOutboxItem
        {
            RecipientEmail = "failed@example.com",
            Subject = "Test Failed",
            BodyHtml = "Message",
            Status = "Failed",
            AttemptCount = 5,
            LastError = "Connection refused",
            SourceModule = "Workflow"
        };
        db.EmailOutboxItems.Add(failedItem);
        await db.SaveChangesAsync();

        await service.RetryOutboxItemAsync(failedItem.Id, "admin_user");

        using (var verifyDb = await factory.CreateDbContextAsync())
        {
            var refreshed = await verifyDb.EmailOutboxItems.FindAsync(failedItem.Id);
            Assert.NotNull(refreshed);
            Assert.Equal("Pending", refreshed.Status);
            Assert.Equal(0, refreshed.AttemptCount);
            Assert.Null(refreshed.LastError);
        }

        // 3. Retry all failed items
        var throttled1 = new EmailOutboxItem { RecipientEmail = "t1@ex.com", Subject = "T1", BodyHtml = "T", Status = "Throttled", AttemptCount = 3 };
        var failed2 = new EmailOutboxItem { RecipientEmail = "f2@ex.com", Subject = "F2", BodyHtml = "F", Status = "Failed", AttemptCount = 5 };
        
        using (var db2 = await factory.CreateDbContextAsync())
        {
            db2.EmailOutboxItems.AddRange(throttled1, failed2);
            await db2.SaveChangesAsync();
        }

        var retryCount = await service.RetryAllFailedOutboxItemsAsync("admin_user");
        Assert.Equal(2, retryCount);

        using (var verifyDb2 = await factory.CreateDbContextAsync())
        {
            var itemT1 = await verifyDb2.EmailOutboxItems.FindAsync(throttled1.Id);
            var itemF2 = await verifyDb2.EmailOutboxItems.FindAsync(failed2.Id);
            Assert.Equal("Pending", itemT1?.Status);
            Assert.Equal("Pending", itemF2?.Status);
        }
    }

    [Fact]
    public async Task SearchOrganisationsAndUsers_ShouldFilterBySearchText()
    {
        var (service, factory) = CreateServices();
        await SeedUsersAndRolesAsync(factory);

        var orgs = await service.SearchOrganisationsAsync("toyota", 10);
        Assert.Single(orgs);
        Assert.Equal("Toyota South Africa Motors", orgs[0].LegalName);
        Assert.Equal("L123456789", orgs[0].SdlNumber);

        var users = await service.SearchUsersAsync("nalini", 10);
        Assert.Single(users);
        Assert.Equal("nalini.moodley@toyota.co.za", users[0].Username);
        Assert.Equal("Nalini Moodley", users[0].FullName);
    }
}
