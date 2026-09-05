using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging.Abstractions;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

public class ErpOutboxQueueTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, FeatureFlagService flags, SystemConfigurationService config, ErpIntegrationService erp, ErpOutboxQueueService outbox) CreateContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var inMemory = new Dictionary<string, string?>
        {
            { "Features:Integrations.DynamicsGp", "false" },
            { "Integrations:DynamicsGp:SimulatedOffline", "false" }
        };
        var conf = new ConfigurationBuilder().AddInMemoryCollection(inMemory).Build();
        var configService = new SystemConfigurationService(factory, conf, audit);
        var flagService = new FeatureFlagService(factory, conf, audit);
        var erp = new ErpIntegrationService(factory, flagService, configService, audit);
        var logger = NullLogger<ErpOutboxQueueService>.Instance;
        var outbox = new ErpOutboxQueueService(factory, erp, flagService, configService, audit, logger);

        return (factory, db, audit, flagService, configService, erp, outbox);
    }

    [Fact]
    public async Task ErpOutbox_Enqueue_AllMessageTypes_PersistsPendingWithCorrelationIdAndPriority()
    {
        var (factory, db, audit, flags, config, erp, outbox) = CreateContext();

        var org = new Organisation { CompanyName = "Outbox Test Employer", SdlNumber = "L112233445" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        // 1. Enqueue VendorSync
        var vendorMsg = await outbox.EnqueueAsync("VendorSync", org.Id.ToString(), new { org.Id, org.SdlNumber }, org.Id, priority: 1, "FinanceAdmin");
        // 2. Enqueue BankingDetailsUpdate
        var bankMsg = await outbox.EnqueueAsync("BankingDetailsUpdate", org.Id.ToString(), new { org.Id, BankAccount = "12345678" }, org.Id, priority: 1, "FinanceAdmin");
        // 3. Enqueue DgTrancheDisbursement
        var dgMsg = await outbox.EnqueueAsync("DgTrancheDisbursement", "101", new { trancheId = 101 }, org.Id, priority: 2, "FinanceAdmin");
        // 4. Enqueue MgRebateDisbursement
        var mgMsg = await outbox.EnqueueAsync("MgRebateDisbursement", "201", new { rebateId = 201 }, org.Id, priority: 2, "FinanceAdmin");

        Assert.Equal("Pending", vendorMsg.QueueStatusCode);
        Assert.Equal(1, vendorMsg.ExecutionPriority);
        Assert.False(string.IsNullOrWhiteSpace(vendorMsg.MessageCorrelationId));

        Assert.Equal("Pending", bankMsg.QueueStatusCode);
        Assert.Equal(1, bankMsg.ExecutionPriority);

        Assert.Equal("Pending", dgMsg.QueueStatusCode);
        Assert.Equal(2, dgMsg.ExecutionPriority);

        Assert.Equal("Pending", mgMsg.QueueStatusCode);
        Assert.Equal(2, mgMsg.ExecutionPriority);

        using var verifyDb = (NsdmsDbContext)factory.CreateDbContext();
        var count = verifyDb.ErpOutboxMessages.Count();
        Assert.Equal(4, count);
    }

    [Fact]
    public async Task ErpOutbox_WhenGpIsOffline_PausesExecutionAndLeavesItemsPending()
    {
        var (factory, db, audit, flags, config, erp, outbox) = CreateContext();

        var org = new Organisation { CompanyName = "Offline Queue Employer", SdlNumber = "L556677889" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        // Enqueue item
        var msg = await outbox.EnqueueAsync("VendorSync", org.Id.ToString(), new { org.Id }, org.Id, priority: 1, "FinanceAdmin");

        // Simulate GP Outage
        await config.SetConfigAsync("Integrations.DynamicsGp.SimulatedOffline", "true", "Integrations", "Simulate GP Outage", "String", "AdminUser");

        var health = await outbox.CheckGpHealthAsync();
        Assert.False(health.IsAvailable);

        // Act - Attempt to process batch
        var result = await outbox.ProcessNextBatchAsync(10);

        // Assert - Queue is paused, 0 processed
        Assert.True(result.PausedDueToGpOutage);
        Assert.Equal(0, result.TotalProcessed);

        using var verifyDb = (NsdmsDbContext)factory.CreateDbContext();
        var persisted = await verifyDb.ErpOutboxMessages.FindAsync(msg.Id);
        Assert.NotNull(persisted);
        Assert.Equal("Pending", persisted.QueueStatusCode);
        Assert.Null(persisted.DeliveredAtUtc);
    }

    [Fact]
    public async Task ErpOutbox_WhenGpBecomesAvailable_ResumesAndDrainsQueueInFifoOrder()
    {
        var (factory, db, audit, flags, config, erp, outbox) = CreateContext();

        var org = new Organisation { CompanyName = "Resume Queue Employer", SdlNumber = "L998877665" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        // Enqueue items
        var msg1 = await outbox.EnqueueAsync("VendorSync", org.Id.ToString(), new { org.Id }, org.Id, priority: 1, "FinanceAdmin");
        var msg2 = await outbox.EnqueueAsync("BankingDetailsUpdate", org.Id.ToString(), new { org.Id }, org.Id, priority: 1, "FinanceAdmin");

        // Ensure GP is online
        await config.SetConfigAsync("Integrations.DynamicsGp.SimulatedOffline", "false", "Integrations", "GP Online", "String", "AdminUser");

        var health = await outbox.CheckGpHealthAsync();
        Assert.True(health.IsAvailable);

        // Act - Process batch
        var result = await outbox.ProcessNextBatchAsync(10);

        // Assert
        Assert.False(result.PausedDueToGpOutage);
        Assert.Equal(2, result.SuccessCount);

        using var verifyDb = (NsdmsDbContext)factory.CreateDbContext();
        var updated1 = await verifyDb.ErpOutboxMessages.FindAsync(msg1.Id);
        var updated2 = await verifyDb.ErpOutboxMessages.FindAsync(msg2.Id);

        Assert.NotNull(updated1);
        Assert.Equal("Delivered", updated1.QueueStatusCode);
        Assert.NotNull(updated1.DeliveredAtUtc);
        Assert.Equal($"VEN-SYNC-{org.Id}", updated1.TransactionReference);

        Assert.NotNull(updated2);
        Assert.Equal("Delivered", updated2.QueueStatusCode);
        Assert.NotNull(updated2.DeliveredAtUtc);
        Assert.Equal($"AVS-VERIFY-{org.Id}", updated2.TransactionReference);
    }

    [Fact]
    public async Task ErpOutbox_PriorityOrdering_ExecutesPriority1BeforePriority2()
    {
        var (factory, db, audit, flags, config, erp, outbox) = CreateContext();

        var org = new Organisation { CompanyName = "Priority Employer", SdlNumber = "L334455667" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        // Enqueue Priority 2 FIRST (Disbursement)
        var msgPayment = await outbox.EnqueueAsync("PaymentDisbursement", "VOUCHER-001", new { amount = 5000 }, org.Id, priority: 2, "FinanceAdmin");

        // Enqueue Priority 1 SECOND (VendorSync pre-requisite)
        var msgVendor = await outbox.EnqueueAsync("VendorSync", org.Id.ToString(), new { org.Id }, org.Id, priority: 1, "FinanceAdmin");

        // Process single item batch (batchSize = 1)
        var result = await outbox.ProcessNextBatchAsync(batchSize: 1);

        Assert.Equal(1, result.SuccessCount);

        using var verifyDb = (NsdmsDbContext)factory.CreateDbContext();
        var processedVendor = await verifyDb.ErpOutboxMessages.FindAsync(msgVendor.Id);
        var pendingPayment = await verifyDb.ErpOutboxMessages.FindAsync(msgPayment.Id);

        // Priority 1 (Vendor) was processed first even though Payment was enqueued earlier!
        Assert.Equal("Delivered", processedVendor?.QueueStatusCode);
        Assert.Equal("Pending", pendingPayment?.QueueStatusCode);
    }

    [Fact]
    public async Task ErpOutbox_GetQueueStats_ReturnsAccurateMetrics()
    {
        var (factory, db, audit, flags, config, erp, outbox) = CreateContext();

        var org = new Organisation { CompanyName = "Stats Employer", SdlNumber = "L887766554" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        await outbox.EnqueueAsync("VendorSync", org.Id.ToString(), new { org.Id }, org.Id, priority: 1, "FinanceAdmin");
        await outbox.EnqueueAsync("BankingDetailsUpdate", org.Id.ToString(), new { org.Id }, org.Id, priority: 1, "FinanceAdmin");

        var stats = await outbox.GetQueueStatsAsync();

        Assert.Equal(2, stats.PendingCount);
        Assert.Equal(2, stats.TotalCount);
        Assert.Equal(0, stats.DeliveredCount);
        Assert.True(stats.GpHealth.IsAvailable);
    }

    [Fact]
    public async Task ErpOutbox_RetryMessage_ResetsFailedMessageToPending()
    {
        var (factory, db, audit, flags, config, erp, outbox) = CreateContext();

        var org = new Organisation { CompanyName = "Retry Employer", SdlNumber = "L445566778" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var msg = await outbox.EnqueueAsync("VendorSync", org.Id.ToString(), new { org.Id }, org.Id, priority: 1, "FinanceAdmin");

        // Manually simulate DeadLetter state
        using (var updateDb = (NsdmsDbContext)factory.CreateDbContext())
        {
            var entity = await updateDb.ErpOutboxMessages.FindAsync(msg.Id);
            entity!.QueueStatusCode = "DeadLetter";
            entity.RetryCount = 5;
            entity.LastError = "Simulated permanent GP schema error";
            await updateDb.SaveChangesAsync();
        }

        // Act - Retry
        var retried = await outbox.RetryMessageAsync(msg.Id, "FinanceAdmin");
        Assert.True(retried);

        using (var verifyDb = (NsdmsDbContext)factory.CreateDbContext())
        {
            var verified = await verifyDb.ErpOutboxMessages.FindAsync(msg.Id);
            Assert.NotNull(verified);
            Assert.Equal("Pending", verified.QueueStatusCode);
            Assert.Equal(0, verified.RetryCount);
            Assert.Null(verified.LastError);
        }
    }
}
