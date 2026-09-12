using System;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging.Abstractions;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

public class SarsLevyAsyncBackgroundJobTests
{
    private static ServiceProvider CreateTestServiceProvider(string dbName)
    {
        var services = new ServiceCollection();
        var factory = new TestDbContextFactory(dbName);

        services.AddSingleton<INsdmsDbContextFactory>(factory);
        services.AddScoped<IAuditService, AuditService>();
        services.AddScoped<ISarsBulkStagingWriter, SarsBulkStagingWriter>();
        services.AddScoped<ILevyService, LevyService>();
        services.AddScoped<ISarsCompliancePreProcessor, SarsCompliancePreProcessor>();
        services.AddScoped<ISarsLevyStreamingPipeline, SarsLevyStreamingPipeline>();
        services.AddSingleton<IBackgroundJobQueue, InMemoryBackgroundJobQueue>(sp =>
            new InMemoryBackgroundJobQueue(NullLogger<InMemoryBackgroundJobQueue>.Instance));
        services.AddSingleton<IPdfDocumentService, Nsdms.Infrastructure.Services.QuestPdfDocumentService>();
        services.AddSingleton<ISignalRNotificationPublisher, NullSignalRNotificationPublisher>();

        return services.BuildServiceProvider();
    }

    [Fact]
    public async Task EnqueueSarsLevyIngestionAsync_CreatesTicketAndWritesToChannel()
    {
        var sp = CreateTestServiceProvider(Guid.NewGuid().ToString());
        var queue = sp.GetRequiredService<IBackgroundJobQueue>();

        var rawContent = "L102938475|2026|150000.00\r\nTRAILER|1|150000.00";
        var bytes = Encoding.UTF8.GetBytes(rawContent);

        var ticket = await queue.EnqueueSarsLevyIngestionAsync("SARS_SCHEDULE_2026.dat", bytes, "FinanceOfficer");

        Assert.NotNull(ticket);
        Assert.Equal("SarsLevyIngestion", ticket.JobType);
        Assert.Equal(BackgroundJobStatus.Queued, ticket.Status);
        Assert.Equal(0, ticket.ProgressPercentage);
        Assert.Equal("FinanceOfficer", ticket.RequestedBy);
        Assert.Equal("SARS_SCHEDULE_2026.dat", ticket.ResultFileName);
        Assert.Equal(bytes.Length, ticket.ResultData?.Length);

        // Read from channel
        var readSuccess = queue.Reader.TryRead(out var readTicket);
        Assert.True(readSuccess);
        Assert.Equal(ticket.JobId, readTicket?.JobId);
    }

    [Fact]
    public async Task BackgroundJobProcessingWorker_ValidSarsFile_ExecutesPipelineAndMarksCompleted()
    {
        var dbName = Guid.NewGuid().ToString();
        var sp = CreateTestServiceProvider(dbName);
        var queue = sp.GetRequiredService<IBackgroundJobQueue>();
        var factory = sp.GetRequiredService<INsdmsDbContextFactory>();

        // Seed Organisation
        using (var db = await factory.CreateDbContextAsync())
        {
            db.Organisations.Add(new Organisation
            {
                LegalName = "Toyota Prospecton Plant",
                SdlNumber = "L102938475",
                SicCode = "35100",
                ChamberCode = "AUTO"
            });
            await db.SaveChangesAsync();
        }

        var validContent =
            "L102938475|2026|20000.00|49500.00|10500.00|500.00|0.00|0.00|100000.00|35100\r\n" +
            "L203948576|2026|10000.00|24750.00|5250.00|250.00|0.00|0.00|50000.00|35100\r\n" +
            "TRAILER|2|150000.00";
        var bytes = Encoding.UTF8.GetBytes(validContent);

        var ticket = await queue.EnqueueSarsLevyIngestionAsync("SARS_PROD_2026_M06.dat", bytes, "TestOfficer");

        var worker = new BackgroundJobProcessingWorker(queue, sp, NullLogger<BackgroundJobProcessingWorker>.Instance);

        using var cts = new CancellationTokenSource(TimeSpan.FromSeconds(5));
        var workerTask = worker.StartAsync(cts.Token);

        // Wait until job is completed
        var maxWait = DateTime.UtcNow.AddSeconds(5);
        while (ticket.Status != BackgroundJobStatus.Completed && ticket.Status != BackgroundJobStatus.Failed && DateTime.UtcNow < maxWait)
        {
            await Task.Delay(50);
        }

        await worker.StopAsync(CancellationToken.None);

        Assert.Equal(BackgroundJobStatus.Completed, ticket.Status);
        Assert.Equal(100, ticket.ProgressPercentage);
        Assert.NotNull(ticket.ResultDownloadUrl);
        Assert.StartsWith("/levies/", ticket.ResultDownloadUrl);

        // Verify database records
        using (var db = await factory.CreateDbContextAsync())
        {
            var levyFiles = await db.LevyFiles.ToListAsync();
            Assert.Single(levyFiles);
            Assert.Equal("SARS_PROD_2026_M06.dat", levyFiles[0].FileName);
            Assert.Equal(2, levyFiles[0].TotalRecords);
            Assert.Equal(150000.00m, levyFiles[0].TotalAmount);

            var lines = await db.LevyFileLines.Where(l => l.LevyFileId == levyFiles[0].Id).ToListAsync();
            Assert.Equal(2, lines.Count);
        }
    }

    [Fact]
    public async Task BackgroundJobProcessingWorker_InvalidSarsFile_FailsGracefullyWithAuditReport()
    {
        var dbName = Guid.NewGuid().ToString();
        var sp = CreateTestServiceProvider(dbName);
        var queue = sp.GetRequiredService<IBackgroundJobQueue>();
        var factory = sp.GetRequiredService<INsdmsDbContextFactory>();

        // Corrupt file: invalid SDL format
        var corruptContent =
            "BAD_SDL_NUM|2026|100000.00\r\n" +
            "TRAILER|1|100000.00";
        var bytes = Encoding.UTF8.GetBytes(corruptContent);

        var ticket = await queue.EnqueueSarsLevyIngestionAsync("SARS_CORRUPT.dat", bytes, "TestOfficer");

        var worker = new BackgroundJobProcessingWorker(queue, sp, NullLogger<BackgroundJobProcessingWorker>.Instance);

        using var cts = new CancellationTokenSource(TimeSpan.FromSeconds(5));
        var workerTask = worker.StartAsync(cts.Token);

        var maxWait = DateTime.UtcNow.AddSeconds(5);
        while (ticket.Status != BackgroundJobStatus.Completed && ticket.Status != BackgroundJobStatus.Failed && DateTime.UtcNow < maxWait)
        {
            await Task.Delay(50);
        }

        await worker.StopAsync(CancellationToken.None);

        Assert.Equal(BackgroundJobStatus.Failed, ticket.Status);
        Assert.NotNull(ticket.ErrorMessage);
        Assert.Contains("InvalidSdlFormat", ticket.ErrorMessage);

        // Verify database is completely untouched
        using (var db = await factory.CreateDbContextAsync())
        {
            Assert.Empty(await db.LevyFiles.ToListAsync());
            Assert.Empty(await db.LevyFileLines.ToListAsync());
            Assert.Empty(await db.SarsLevyStagings.ToListAsync());
        }
    }

    private class NullSignalRNotificationPublisher : ISignalRNotificationPublisher
    {
        public Task PublishNotificationAsync(SystemNotificationDto notification) => Task.CompletedTask;
        public Task BroadcastTaskEventAsync(string taskId, string taskTitle, string assignedRole, string priority) => Task.CompletedTask;
        public Task BroadcastWorkflowTransitionAsync(string entityType, int entityId, string fromState, string toState, string actor) => Task.CompletedTask;
        public Task BroadcastSlaWarningAsync(string taskTitle, int hoursRemaining) => Task.CompletedTask;
        public Task BroadcastAlertAsync(string message, string severity) => Task.CompletedTask;
    }
}
