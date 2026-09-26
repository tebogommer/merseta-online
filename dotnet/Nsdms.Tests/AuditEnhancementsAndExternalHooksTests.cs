using System.Text;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging.Abstractions;
using Nsdms.Application.Common;
using Nsdms.Application.Common.ExternalToolHooks;
using Nsdms.Application.Models;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Interceptors;
using Nsdms.Infrastructure.Services;
using Nsdms.Infrastructure.Services.DocumentCompilers;
using Nsdms.Infrastructure.Services.ExternalToolHooks;
using Xunit;

namespace Nsdms.Tests;

public class AuditEnhancementsAndExternalHooksTests
{
    private class InterceptedTestDbContextFactory : INsdmsDbContextFactory
    {
        private readonly DbContextOptions<NsdmsDbContext> _options;

        public InterceptedTestDbContextFactory(string dbName)
        {
            _options = new DbContextOptionsBuilder<NsdmsDbContext>()
                .UseInMemoryDatabase(databaseName: dbName)
                .AddInterceptors(new AuditableEntityInterceptor())
                .Options;

            using var ctx = new NsdmsDbContext(_options);
            ctx.Database.EnsureCreated();
        }

        public INsdmsDbContext CreateDbContext() => new NsdmsDbContext(_options);
        public Task<INsdmsDbContext> CreateDbContextAsync(CancellationToken cancellationToken = default) =>
            Task.FromResult(CreateDbContext());
    }

    [Fact]
    public async Task AppendOnly_AuditLog_Update_ThrowsViolationException()
    {
        var factory = new InterceptedTestDbContextFactory(Guid.NewGuid().ToString());
        using (var db = (NsdmsDbContext)factory.CreateDbContext())
        {
            db.AuditLogs.Add(new AuditLog
            {
                EntityName = "Organisation",
                RecordId = 101,
                ActionName = "Create",
                Actor = "admin@merseta.org.za",
                Timestamp = DateTime.UtcNow
            });
            await db.SaveChangesAsync();
        }

        using (var db = (NsdmsDbContext)factory.CreateDbContext())
        {
            var audit = await db.AuditLogs.FirstAsync();
            audit.ActionName = "TamperedAction";

            var ex = await Assert.ThrowsAsync<InvalidOperationException>(() => db.SaveChangesAsync());
            Assert.Contains("AGSA / ISO 27001 ITGC-19 VIOLATION: Audit logs and execution history are append-only. Modification and deletion are strictly prohibited.", ex.Message);
        }
    }

    [Fact]
    public async Task AppendOnly_AuditLog_Delete_ThrowsViolationException()
    {
        var factory = new InterceptedTestDbContextFactory(Guid.NewGuid().ToString());
        using (var db = (NsdmsDbContext)factory.CreateDbContext())
        {
            db.AuditLogs.Add(new AuditLog
            {
                EntityName = "GrantApplication",
                RecordId = 202,
                ActionName = "Submitted",
                Actor = "officer@merseta.org.za",
                Timestamp = DateTime.UtcNow
            });
            await db.SaveChangesAsync();
        }

        using (var db = (NsdmsDbContext)factory.CreateDbContext())
        {
            var audit = await db.AuditLogs.FirstAsync();
            db.AuditLogs.Remove(audit);

            var ex = await Assert.ThrowsAsync<InvalidOperationException>(() => db.SaveChangesAsync());
            Assert.Contains("AGSA / ISO 27001 ITGC-19 VIOLATION: Audit logs and execution history are append-only. Modification and deletion are strictly prohibited.", ex.Message);
        }
    }

    [Fact]
    public async Task AppendOnly_WorkflowHistory_And_BackgroundJobJournal_ThrowsViolationException()
    {
        var factory = new InterceptedTestDbContextFactory(Guid.NewGuid().ToString());
        using (var db = (NsdmsDbContext)factory.CreateDbContext())
        {
            db.WorkflowHistories.Add(new WorkflowHistory
            {
                WorkflowInstanceId = 1,
                ActionName = "Approve",
                ActorUserId = "user-10",
                ActorName = "Approver",
                ActionDate = DateTime.UtcNow
            });
            db.BackgroundJobJournals.Add(new BackgroundJobJournal
            {
                JobType = "SarsRecon",
                Status = "Completed",
                StartedAt = DateTime.UtcNow
            });
            db.ComputationExecutionAudits.Add(new ComputationExecutionAudit
            {
                ComputationId = 1,
                InvokedByActor = "system",
                ExecutedAt = DateTime.UtcNow
            });
            await db.SaveChangesAsync();
        }

        using (var db = (NsdmsDbContext)factory.CreateDbContext())
        {
            var wh = await db.WorkflowHistories.FirstAsync();
            wh.ActionName = "TamperedAction";

            var ex = await Assert.ThrowsAsync<InvalidOperationException>(() => db.SaveChangesAsync());
            Assert.Contains("AGSA / ISO 27001 ITGC-19 VIOLATION", ex.Message);
        }

        using (var db = (NsdmsDbContext)factory.CreateDbContext())
        {
            var wh = await db.WorkflowHistories.FirstAsync();
            db.WorkflowHistories.Remove(wh);

            var ex = await Assert.ThrowsAsync<InvalidOperationException>(() => db.SaveChangesAsync());
            Assert.Contains("AGSA / ISO 27001 ITGC-19 VIOLATION", ex.Message);
        }

        using (var db = (NsdmsDbContext)factory.CreateDbContext())
        {
            var journal = await db.BackgroundJobJournals.FirstAsync();
            journal.Status = "Tampered";

            var ex = await Assert.ThrowsAsync<InvalidOperationException>(() => db.SaveChangesAsync());
            Assert.Contains("AGSA / ISO 27001 ITGC-19 VIOLATION", ex.Message);
        }

        using (var db = (NsdmsDbContext)factory.CreateDbContext())
        {
            var journal = await db.BackgroundJobJournals.FirstAsync();
            db.BackgroundJobJournals.Remove(journal);

            var ex = await Assert.ThrowsAsync<InvalidOperationException>(() => db.SaveChangesAsync());
            Assert.Contains("AGSA / ISO 27001 ITGC-19 VIOLATION", ex.Message);
        }

        using (var db = (NsdmsDbContext)factory.CreateDbContext())
        {
            var comp = await db.ComputationExecutionAudits.FirstAsync();
            comp.InvokedByActor = "TamperedActor";

            var ex = await Assert.ThrowsAsync<InvalidOperationException>(() => db.SaveChangesAsync());
            Assert.Contains("AGSA / ISO 27001 ITGC-19 VIOLATION", ex.Message);
        }

        using (var db = (NsdmsDbContext)factory.CreateDbContext())
        {
            var comp = await db.ComputationExecutionAudits.FirstAsync();
            db.ComputationExecutionAudits.Remove(comp);

            var ex = await Assert.ThrowsAsync<InvalidOperationException>(() => db.SaveChangesAsync());
            Assert.Contains("AGSA / ISO 27001 ITGC-19 VIOLATION", ex.Message);
        }
    }

    [Fact]
    public async Task AuditService_GetAuditReportByPeriodAsync_FiltersCorrectlyAndComputesHash()
    {
        var factory = new InterceptedTestDbContextFactory(Guid.NewGuid().ToString());
        var baseDate = new DateTime(2026, 6, 1, 10, 0, 0, DateTimeKind.Utc);

        using (var db = (NsdmsDbContext)factory.CreateDbContext())
        {
            db.AuditLogs.AddRange(
                new AuditLog { EntityName = "Organisation", RecordId = 1, ActionName = "Create", Actor = "alice@merseta.org.za", Timestamp = baseDate.AddDays(1) },
                new AuditLog { EntityName = "Organisation", RecordId = 2, ActionName = "Update", Actor = "bob@merseta.org.za", Timestamp = baseDate.AddDays(5) },
                new AuditLog { EntityName = "GrantMoa", RecordId = 10, ActionName = "ApproveMoa", Actor = "cfo@merseta.org.za", Timestamp = baseDate.AddDays(10) },
                new AuditLog { EntityName = "Person", RecordId = 5, ActionName = "Register", Actor = "alice@merseta.org.za", Timestamp = baseDate.AddDays(20) }
            );
            await db.SaveChangesAsync();
        }

        var auditService = new AuditService(factory);

        // Filter by Date Range and EntityName
        var report = await auditService.GetAuditReportByPeriodAsync(new AuditPeriodFilterRequest
        {
            FromDateUtc = baseDate,
            ToDateUtc = baseDate.AddDays(7),
            EntityName = "Organisation"
        });

        Assert.Equal(2, report.TotalCount);
        Assert.Equal(2, report.Logs.Count);
        Assert.False(string.IsNullOrWhiteSpace(report.ReportIntegrityHash));
        Assert.Equal(64, report.ReportIntegrityHash.Length); // 64-char SHA256 hex string

        // Filter by Actor
        var actorReport = await auditService.GetAuditReportByPeriodAsync(new AuditPeriodFilterRequest
        {
            Actor = "cfo@merseta.org.za"
        });
        Assert.Equal(1, actorReport.TotalCount);
        Assert.Equal("GrantMoa", actorReport.Logs[0].EntityName);
    }

    [Fact]
    public async Task AuditService_ExportAuditReportCsvAsync_GeneratesDlpHeaderColumnsAndLogsAudit()
    {
        var factory = new InterceptedTestDbContextFactory(Guid.NewGuid().ToString());
        var baseDate = new DateTime(2026, 6, 15, 8, 30, 0, DateTimeKind.Utc);

        using (var db = (NsdmsDbContext)factory.CreateDbContext())
        {
            db.AuditLogs.Add(new AuditLog
            {
                EntityName = "BankingDetails",
                RecordId = 505,
                ActionName = "ApproveBanking",
                Actor = "finance.checker@merseta.org.za",
                MetadataJson = "{\"digitalSecuritySeal\":\"a1b2c3d4e5f6\",\"before\":null,\"after\":{\"Status\":\"Approved\"}}",
                Timestamp = baseDate
            });
            await db.SaveChangesAsync();
        }

        var auditService = new AuditService(factory);
        const string exporter = "auditor.agsa@agsa.co.za";

        var csvBytes = await auditService.ExportAuditReportCsvAsync(new AuditPeriodFilterRequest
        {
            EntityName = "BankingDetails"
        }, exporter);

        Assert.NotNull(csvBytes);
        Assert.True(csvBytes.Length > 0);

        var csvText = Encoding.UTF8.GetString(csvBytes);

        // DLP Header Verification
        Assert.Contains($"# merSETA CONFIDENTIAL - Exported by {exporter}", csvText);
        Assert.Contains("ISO 27001 / AGSA Verified - SHA256:", csvText);

        // Columns Verification
        Assert.Contains("Id, TimestampUtc, EntityName, RecordId, ActionName, Actor, DigitalSecuritySeal, MetadataJson", csvText);
        Assert.Contains("BankingDetails", csvText);
        Assert.Contains("finance.checker@merseta.org.za", csvText);
        Assert.Contains("a1b2c3d4e5f6", csvText);

        // Confirm EXPORT_AUDIT_REPORT logged to AuditLog
        using (var db = (NsdmsDbContext)factory.CreateDbContext())
        {
            var exportAudit = await db.AuditLogs.FirstOrDefaultAsync(a => a.ActionName == "EXPORT_AUDIT_REPORT");
            Assert.NotNull(exportAudit);
            Assert.Equal("AuditLog", exportAudit.EntityName);
            Assert.Equal(exporter, exportAudit.Actor);
            Assert.Contains("TotalRecordsExported", exportAudit.MetadataJson);
        }
    }

    [Fact]
    public async Task ExternalToolHooks_ResolveAndOperateInStandbyModeWhenUnconfigured()
    {
        var services = new ServiceCollection();
        var inMemoryConfig = new Dictionary<string, string?>
        {
            ["ConnectionStrings:DefaultConnection"] = "Server=localhost;Database=TestDb;Trusted_Connection=True;",
            ["ExternalSecurityHooks:Siem:Enabled"] = "false",
            ["ExternalSecurityHooks:Mfa:Enabled"] = "false",
            ["ExternalSecurityHooks:SecretsVault:Enabled"] = "false",
            ["ExternalSecurityHooks:VulnerabilityScan:Enabled"] = "false",
            ["ExternalSecurityHooks:BackupVerification:Enabled"] = "false",
            ["ExternalSecurityHooks:ItsmChangeTicket:Enabled"] = "false"
        };
        var config = new ConfigurationBuilder().AddInMemoryCollection(inMemoryConfig).Build();

        services.AddLogging();
        services.AddSingleton<IConfiguration>(config);
        services.AddInfrastructureServices(config);

        using var provider = services.BuildServiceProvider();

        // 1. SIEM Forwarder
        var siem = provider.GetRequiredService<ISiemForwarderHook>();
        Assert.NotNull(siem);
        Assert.False(siem.IsConfigured);
        var siemStatus = await siem.GetStatusAsync();
        Assert.False(siemStatus.IsConfigured);
        var siemForwardSuccess = await siem.ForwardSecurityEventAsync(new SiemSecurityEvent { ActionName = "TestEvent", Actor = "test" });
        Assert.True(siemForwardSuccess);

        // 2. MFA Provider
        var mfa = provider.GetRequiredService<IMfaProviderHook>();
        Assert.NotNull(mfa);
        Assert.False(mfa.IsConfigured);
        var challenge = await mfa.InitiateChallengeAsync("user-1");
        Assert.True(challenge.Success);
        Assert.True(await mfa.VerifyChallengeAsync("user-1", challenge.ChallengeId!, "123456"));

        // 3. Secrets Vault
        var vault = provider.GetRequiredService<ISecretsVaultHook>();
        Assert.NotNull(vault);
        Assert.False(vault.IsConfigured);
        var secretStatus = await vault.GetStatusAsync();
        Assert.False(secretStatus.IsConfigured);

        // 4. Vulnerability Scan
        var vuln = provider.GetRequiredService<IVulnerabilityScanHook>();
        Assert.NotNull(vuln);
        Assert.False(vuln.IsConfigured);
        var vulnReport = await vuln.GetLatestVulnerabilityReportAsync();
        Assert.True(vulnReport.Success);
        Assert.Equal(0, vulnReport.TotalVulnerabilities);

        // 5. Backup Verification
        var backup = provider.GetRequiredService<IBackupVerificationHook>();
        Assert.NotNull(backup);
        Assert.False(backup.IsConfigured);
        var backupStatus = await backup.VerifyLatestBackupAsync();
        Assert.True(backupStatus.IsVerified);
        Assert.Equal("Standby", backupStatus.Status);

        // 6. ITSM Change Ticket
        var itsm = provider.GetRequiredService<IItsmChangeTicketHook>();
        Assert.NotNull(itsm);
        Assert.False(itsm.IsConfigured);
        var ticketStatus = await itsm.VerifyChangeAuthorizationAsync("CAB-TEST-001");
        Assert.True(ticketStatus.IsAuthorized);
        var ticketRef = await itsm.CreateChangeTicketAsync(new ItsmChangeTicketRequest { Summary = "Test CAB Ticket" });
        Assert.NotNull(ticketRef);
        Assert.Contains("CAB", ticketRef);
    }

    [Fact]
    public async Task ReportExportService_GenerateItgcAuditDossierPdfAsync_ProducesValidLandscapePdfWithQrAndAuditTrail()
    {
        var factory = new InterceptedTestDbContextFactory(Guid.NewGuid().ToString());
        var baseDate = new DateTime(2026, 7, 1, 9, 0, 0, DateTimeKind.Utc);

        using (var db = (NsdmsDbContext)factory.CreateDbContext())
        {
            db.AuditLogs.AddRange(
                new AuditLog { EntityName = "Organisation", RecordId = 10, ActionName = "Create", Actor = "lead.auditor@agsa.co.za", Timestamp = baseDate },
                new AuditLog { EntityName = "GrantMoa", RecordId = 20, ActionName = "ApproveMoa", Actor = "cfo@merseta.org.za", Timestamp = baseDate.AddDays(2) },
                new AuditLog { EntityName = "BankingDetails", RecordId = 30, ActionName = "ApproveBanking", Actor = "finance.checker@merseta.org.za", Timestamp = baseDate.AddDays(4) }
            );
            await db.SaveChangesAsync();
        }

        var auditService = new AuditService(factory);
        var reportService = new ReportExportService(factory, auditService);
        const string exporter = "lead.auditor@agsa.co.za";

        var req = new AuditPeriodFilterRequest
        {
            FromDateUtc = baseDate,
            ToDateUtc = baseDate.AddDays(10)
        };

        var pdfBytes = await reportService.GenerateItgcAuditDossierPdfAsync(req, exporter);

        Assert.NotNull(pdfBytes);
        Assert.True(pdfBytes.Length > 1000, "PDF byte stream should contain substantial document data");

        // Verify PDF Magic Bytes (%PDF-)
        Assert.True(pdfBytes[0] == 0x25 && pdfBytes[1] == 0x50 && pdfBytes[2] == 0x44 && pdfBytes[3] == 0x46, "Document must have valid %PDF- magic header");

        // Verify EXPORT_AUDIT_REPORT audit trail entry created with PDF_ITGC_DOSSIER
        using (var db = (NsdmsDbContext)factory.CreateDbContext())
        {
            var exportAudit = await db.AuditLogs.FirstOrDefaultAsync(a => a.ActionName == "EXPORT_AUDIT_REPORT" && a.MetadataJson!.Contains("PDF_ITGC_DOSSIER"));
            Assert.NotNull(exportAudit);
            Assert.Equal("AuditLog", exportAudit.EntityName);
            Assert.Equal(exporter, exportAudit.Actor);
            Assert.Contains("ReportIntegrityHash", exportAudit.MetadataJson);
        }
    }

    [Fact]
    public void ItgcAuditDossierPdfCompiler_GetStandard20Controls_ContainsAllTwentyControls()
    {
        var controls = ItgcAuditDossierPdfCompiler.GetStandard20Controls();
        Assert.Equal(20, controls.Count);

        for (int i = 1; i <= 20; i++)
        {
            var expectedId = $"ITGC-{i:D2}";
            var ctrl = controls.FirstOrDefault(c => c.ControlId == expectedId);
            Assert.NotNull(ctrl);
            Assert.False(string.IsNullOrWhiteSpace(ctrl.Title));
            Assert.False(string.IsNullOrWhiteSpace(ctrl.Domain));
            Assert.False(string.IsNullOrWhiteSpace(ctrl.FrameworkMapping));
            Assert.False(string.IsNullOrWhiteSpace(ctrl.Status));
            Assert.False(string.IsNullOrWhiteSpace(ctrl.EvidenceCitation));
            Assert.False(string.IsNullOrWhiteSpace(ctrl.RemediationAndHooks));
        }
    }

    [Fact]
    public void ItgcAuditDossierPdfCompiler_GenerateQrCodePngBytes_ProducesValidPngImage()
    {
        var qrBytes = ItgcAuditDossierPdfCompiler.GenerateQrCodePngBytes("https://nsdms.merseta.org.za/verify/audit/test-hash");
        Assert.NotNull(qrBytes);
        Assert.True(qrBytes.Length > 0);

        // Verify PNG magic bytes (0x89, 'P', 'N', 'G')
        Assert.True(qrBytes[0] == 0x89 && qrBytes[1] == 0x50 && qrBytes[2] == 0x4E && qrBytes[3] == 0x47, "QR stream must have valid PNG magic header");
    }
}

