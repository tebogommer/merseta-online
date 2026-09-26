using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Events;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Interceptors;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Services;
using Nsdms.Infrastructure.Services.DocumentCompilers;
using Nsdms.Application.Common.ExternalToolHooks;
using Nsdms.Infrastructure.Services.ExternalToolHooks;

namespace Nsdms.Infrastructure;

public static class DependencyInjection
{
    public static IServiceCollection AddInfrastructureServices(this IServiceCollection services, IConfiguration configuration)
    {
        // Database & Interceptors
        services.AddSingleton<AuditableEntityInterceptor>();
        services.AddDbContextFactory<NsdmsDbContext>((sp, options) =>
        {
            var interceptor = sp.GetRequiredService<AuditableEntityInterceptor>();
            var conn = configuration.GetConnectionString("DefaultConnection")
                       ?? throw new InvalidOperationException("Connection string 'DefaultConnection' was not found in configuration.");
            options.UseSqlServer(conn, sqlOptions =>
            {
                sqlOptions.EnableRetryOnFailure(
                    maxRetryCount: 5,
                    maxRetryDelay: TimeSpan.FromSeconds(30),
                    errorNumbersToAdd: new[] { 1205 });
            })
            .AddInterceptors(interceptor);
        });

        // DbContext Interfaces
        services.AddScoped<INsdmsDbContext>(sp => sp.GetRequiredService<NsdmsDbContext>());
        services.AddScoped<INsdmsDbContextFactory, NsdmsDbContextFactory>();

        // Storage & Document Generation
        services.AddScoped<IFileStorageService, LocalFileStorageService>();
        services.AddScoped<IPdfDocumentService, QuestPdfDocumentService>();
        services.AddScoped<IBrandAssetService, BrandAssetService>();
        services.AddScoped<IReportExportService, ReportExportService>();
        services.AddScoped<IDocumentIngestionBarcodeService, DocumentIngestionBarcodeService>();

        // ERP & Banking Integrations
        services.AddScoped<IErpIntegrationService, ErpIntegrationService>();
        services.AddScoped<IErpOutboxQueueService, ErpOutboxQueueService>();
        services.AddScoped<INonLevyNumberGeneratorService, NonLevyNumberGeneratorService>();
        services.AddScoped<IChamberDerivationService, ChamberDerivationService>();
        services.AddScoped<IBankservAvsService, BankservAvsService>();

        // Bulk Data Ingestion & Streaming
        services.AddScoped<ISarsBulkStagingWriter, SarsBulkStagingWriter>();
        services.AddScoped<ISarsCompliancePreProcessor, SarsCompliancePreProcessor>();
        services.AddScoped<ISarsLevyStreamingPipeline, SarsLevyStreamingPipeline>();
        services.AddScoped<ISarsLevyReconAuditService, SarsLevyReconAuditService>();
        services.AddScoped<ISqlBulkBatchIngestionService, SqlBulkBatchIngestionService>();
        services.AddScoped<IWspBulkIngestionService, WspBulkIngestionService>();
        services.AddScoped<IOrganisationEmployeeService, OrganisationEmployeeService>();

        // Notifications, Events & SLA Monitoring
        services.AddScoped<IDomainEventPublisher, DomainEventPublisher>();
        services.AddScoped<IDigitalSignatureSealService, DigitalSignatureSealService>();
        services.AddScoped<INotificationService, NotificationService>();
        services.AddScoped<ISlaMonitoringService, SlaMonitoringService>();

        // Background Job Queue & Workers
        services.AddSingleton<IBackgroundJobQueue, InMemoryBackgroundJobQueue>();
        services.AddHostedService<BackgroundJobProcessingWorker>();
        services.AddHostedService<BackgroundSchedulerHostedService>();
        services.AddHostedService<PartitionMaintenanceWorker>();

        // Audit Archival & APM Prometheus Metrics
        services.AddScoped<IAuditLogArchivalService, AuditLogArchivalService>();
        services.AddScoped<IMetricsScraperService, MetricsScraperService>();
        services.AddScoped<IAtomicAuditTransactionManager, AtomicAuditTransactionManager>();
        services.AddScoped<IIsoDpsaAuditComplianceService, IsoDpsaAuditComplianceService>();

        // Outbox Email Messaging
        services.AddScoped<IEmailTransportService, EmailTransportService>();
        services.AddScoped<IEmailOutboxService, EmailOutboxService>();
        services.AddHostedService<ThrottledEmailOutboxWorker>();

        // Document Compilers (Strategy Pattern)
        services.AddScoped<IDocumentCompiler<GrantMoa>, GrantMoaDocumentCompiler>();
        services.AddScoped<IDocumentCompiler<LearnerTradeTest>, TradeTestCertificateCompiler>();
        services.AddScoped<IDocumentCompiler<WspSubmission>, WspOutcomeLetterCompiler>();

        // Transactional Outbox Background Worker
        services.AddHostedService<OutboxProcessorWorker>();

        // Attestation & Wizard Drafts
        services.AddScoped<ITsqlAttestationEngine, TsqlAttestationEngine>();
        services.AddScoped<IWizardDraftService, WizardDraftService>();

        // B2B API Architecture & Webhook Event Engine
        services.AddScoped<IApiSecurityService, ApiSecurityService>();
        services.AddScoped<IWebhookDispatcherService, WebhookDispatcherService>();

        // External Security Tool Hook Points (Ground Rule 4 / ITGC Compliance)
        services.AddScoped<ISiemForwarderHook, SiemForwarderHook>();
        services.AddScoped<IMfaProviderHook, MfaProviderHook>();
        services.AddScoped<ISecretsVaultHook, SecretsVaultHook>();
        services.AddScoped<IVulnerabilityScanHook, VulnerabilityScanHook>();
        services.AddScoped<IBackupVerificationHook, BackupVerificationHook>();
        services.AddScoped<IItsmChangeTicketHook, ItsmChangeTicketHook>();

        return services;
    }
}
