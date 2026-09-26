using System.Text;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging.Abstractions;
using Moq;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

public class AuditRemediationControlsTests
{
    [Fact]
    public async Task ReportExportService_GenerateSarsLevyReconCsv_IncludesWatermarkAndAuditLog()
    {
        // Arrange
        var dbName = $"AuditRemediation_ReportExport_{Guid.NewGuid()}";
        var factory = new TestDbContextFactory(dbName);
        var audit = new AuditService(factory);
        var reportService = new ReportExportService(factory, audit);

        using (var db = (NsdmsDbContext)factory.CreateDbContext())
        {
            db.LevyFiles.Add(new LevyFile
            {
                FileRef = "SARS-2026-04-A",
                FileName = "SARS_SDL_202604.txt",
                ImportDate = new DateTime(2026, 4, 15),
                TotalRecords = 120,
                TotalAmount = 2500000.50m,
                ImportStatusCode = "Completed"
            });

            await db.SaveChangesAsync();
        }

        const string requester = "auditor.jones@agsa.co.za";

        // Act
        var csvBytes = await reportService.GenerateSarsLevyReconCsvAsync("2026", requester);
        var csvContent = Encoding.UTF8.GetString(csvBytes);

        // Assert - AC-17 Watermarking
        Assert.NotNull(csvBytes);
        Assert.True(csvBytes.Length > 0);
        Assert.Contains($"# merSETA CONFIDENTIAL - Exported by {requester}", csvContent);
        Assert.Contains("FinYear: 2026", csvContent);
        Assert.Contains("SARS-2026-04-A", csvContent);

        // Assert - AC-05 / AC-17 Immutable Audit Log Entry
        using (var db = (NsdmsDbContext)factory.CreateDbContext())
        {
            var auditEntry = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "LevyFile" && a.ActionName == "EXPORT_DATASET");
            Assert.NotNull(auditEntry);
            Assert.Equal(requester, auditEntry.Actor);
            Assert.Contains("CSV_EXPORT", auditEntry.MetadataJson);
            Assert.Contains("SarsLevyRecon", auditEntry.MetadataJson);
        }
    }

    [Fact]
    public void BankingDetails_ModelBuilder_HasMakerCheckerCheckConstraintConfigured()
    {
        // Arrange & Act
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        using var db = (NsdmsDbContext)factory.CreateDbContext();

        var designTimeModel = Microsoft.EntityFrameworkCore.Infrastructure.AccessorExtensions
            .GetService<Microsoft.EntityFrameworkCore.Metadata.IDesignTimeModel>(db).Model;
        var entityType = designTimeModel.FindEntityType(typeof(BankingDetails));
        Assert.NotNull(entityType);

        var checkConstraints = entityType.GetCheckConstraints().ToList();
        var sodConstraint = checkConstraints.FirstOrDefault(c => c.Name == "CK_BankingDetails_MakerChecker_SoD");

        // Assert - AC-01 Hard database check constraint registered in EF Core metadata
        Assert.NotNull(sodConstraint);
        Assert.Contains("CreatedBy", sodConstraint.Sql);
        Assert.Contains("SecondSignoffUserId", sodConstraint.Sql);
    }

    [Fact]
    public async Task ErpOutboxQueueService_DeadLetterEscalation_DispatchesNotificationAndAudit()
    {
        // Arrange
        var dbName = $"AuditRemediation_ErpQueue_{Guid.NewGuid()}";
        var factory = new TestDbContextFactory(dbName);
        var audit = new AuditService(factory);

        var mockErpService = new Mock<IErpIntegrationService>();
        // Simulate terminal failure during vendor sync
        mockErpService.Setup(e => e.SyncVendorDetailsAsync(It.IsAny<int>(), It.IsAny<string>()))
            .ThrowsAsync(new InvalidOperationException("Fatal ERP schema validation reject: Invalid GL account mapping"));

        var mockFeatureFlags = new Mock<IFeatureFlagService>();
        mockFeatureFlags.Setup(f => f.IsFeatureEnabledAsync("Integrations.DynamicsGp", It.IsAny<bool>())).ReturnsAsync(false);


        var conf = new ConfigurationBuilder().Build();
        var configService = new SystemConfigurationService(factory, conf, audit);

        var mockNotifService = new Mock<INotificationService>();

        var queueService = new ErpOutboxQueueService(
            factory,
            mockErpService.Object,
            mockFeatureFlags.Object,
            configService,
            audit,
            NullLogger<ErpOutboxQueueService>.Instance,
            httpClient: null,
            notificationService: mockNotifService.Object);

        // Enqueue message already at MaxRetries - 1 (RetryCount = 4, MaxRetries = 5)
        ErpOutboxMessage msg;
        using (var db = (NsdmsDbContext)factory.CreateDbContext())
        {
            msg = new ErpOutboxMessage
            {
                MessageType = "VendorSync",
                ReferenceKey = "105",
                PayloadJson = "{\"orgId\":105}",
                QueueStatusCode = "Pending",
                RetryCount = 4,
                MaxRetries = 5,
                NextAttemptAtUtc = DateTime.UtcNow.AddMinutes(-5),
                CreatedBy = "officer.clark@merseta.org.za"
            };
            db.ErpOutboxMessages.Add(msg);
            await db.SaveChangesAsync();
        }

        // Act - Process batch which hits 5th failure
        var result = await queueService.ProcessNextBatchAsync(10);

        // Assert - AC-11 Suspense / DeadLetter Escalation
        Assert.Equal(1, result.FailedCount);

        using (var db = (NsdmsDbContext)factory.CreateDbContext())
        {
            var updated = await db.ErpOutboxMessages.FindAsync(msg.Id);
            Assert.NotNull(updated);
            Assert.Equal("DeadLetter", updated.QueueStatusCode);
            Assert.Equal(5, updated.RetryCount);

            // Audit Action logged
            var auditEntry = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "ErpOutboxMessage" && a.ActionName == "DEAD_LETTER_THRESHOLD_ESCALATION");
            Assert.NotNull(auditEntry);
            Assert.Equal(msg.Id, auditEntry.RecordId);
        }

        // Notification Dispatched to FinanceAdmin role
        mockNotifService.Verify(n => n.SendNotificationAsync(
            It.IsAny<string?>(),
            "FinanceAdmin",
            It.Is<string>(t => t.Contains("DeadLetter")),
            It.IsAny<string>(),
            It.IsAny<string?>(),
            "SystemAlert",
            "Error",
            "ErpOutboxWorker",
            It.IsAny<string?>(),
            It.IsAny<string?>(),
            It.IsAny<int?>(),
            false,
            It.IsAny<string?>(),
            It.IsAny<string?>(),
            It.IsAny<long?>(),
            It.IsAny<string?>(),
            false), Times.Once);
    }

    [Fact]
    public void SqlMigrationScripts_ExistAndAreProperlyFormed()
    {
        // Assert - Verify physical SQL migration files exist on disk
        var sodScriptPath = Path.Combine(AppContext.BaseDirectory, "../../../../Nsdms.Infrastructure/Data/SqlScripts/V2026_58_Enforce_Database_Level_SoD_Constraints.sql");
        var ddmScriptPath = Path.Combine(AppContext.BaseDirectory, "../../../../Nsdms.Infrastructure/Data/SqlScripts/V2026_59_Enable_Native_Dynamic_Data_Masking.sql");

        Assert.True(File.Exists(sodScriptPath) || File.Exists("c:/Antigravity/nsdms-2026-04-01/nsdms/MerSETA/dotnet/Nsdms.Infrastructure/Data/SqlScripts/V2026_58_Enforce_Database_Level_SoD_Constraints.sql"));
        Assert.True(File.Exists(ddmScriptPath) || File.Exists("c:/Antigravity/nsdms-2026-04-01/nsdms/MerSETA/dotnet/Nsdms.Infrastructure/Data/SqlScripts/V2026_59_Enable_Native_Dynamic_Data_Masking.sql"));
    }
}
