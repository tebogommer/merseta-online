using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using MudBlazor.Services;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Interceptors;
using Nsdms.Web.Components;

var builder = WebApplication.CreateBuilder(args);

// Add MudBlazor services
builder.Services.AddMudServices();

// Response Compression for High Performance
builder.Services.AddResponseCompression(options =>
{
    options.EnableForHttps = true;
});

// Password Hasher for Identity
builder.Services.AddScoped<PasswordHasher<ApplicationUser>>();

// Add Razor components with Interactive Server mode
builder.Services.AddRazorComponents()
    .AddInteractiveServerComponents();

// Database & Interceptors
builder.Services.AddSingleton<AuditableEntityInterceptor>();
builder.Services.AddDbContextFactory<NsdmsDbContext>((sp, options) =>
{
    var interceptor = sp.GetRequiredService<AuditableEntityInterceptor>();
    var conn = builder.Configuration.GetConnectionString("DefaultConnection");
    options.UseSqlServer(conn)
           .AddInterceptors(interceptor);
});
builder.Services.AddScoped<NsdmsDbContext>(sp => sp.GetRequiredService<IDbContextFactory<NsdmsDbContext>>().CreateDbContext());

// Interface registration
builder.Services.AddScoped<INsdmsDbContext>(sp => sp.GetRequiredService<NsdmsDbContext>());
builder.Services.AddSingleton<INsdmsDbContextFactory, NsdmsDbContextFactory>();

// Application Services
builder.Services.AddScoped<IAuditService, AuditService>();
builder.Services.AddScoped<AuditService>(sp => (AuditService)sp.GetRequiredService<IAuditService>());

builder.Services.AddScoped<IPersonService, PersonService>();
builder.Services.AddScoped<PersonService>(sp => (PersonService)sp.GetRequiredService<IPersonService>());

builder.Services.AddScoped<IOrganisationService, OrganisationService>();
builder.Services.AddScoped<OrganisationService>(sp => (OrganisationService)sp.GetRequiredService<IOrganisationService>());

builder.Services.AddScoped<IIdentityService, IdentityService>();
builder.Services.AddScoped<IdentityService>(sp => (IdentityService)sp.GetRequiredService<IIdentityService>());

builder.Services.AddScoped<IRolePermissionService, RolePermissionService>();
builder.Services.AddScoped<RolePermissionService>(sp => (RolePermissionService)sp.GetRequiredService<IRolePermissionService>());

builder.Services.AddScoped<ICaslAbilityService, CaslAbilityService>();
builder.Services.AddScoped<CaslAbilityService>(sp => (CaslAbilityService)sp.GetRequiredService<ICaslAbilityService>());

builder.Services.AddScoped<IVisitService, VisitService>();
builder.Services.AddScoped<VisitService>(sp => (VisitService)sp.GetRequiredService<IVisitService>());

builder.Services.AddScoped<ILookupService, LookupService>();
builder.Services.AddScoped<LookupService>(sp => (LookupService)sp.GetRequiredService<ILookupService>());

builder.Services.AddScoped<ITrainingProviderService, TrainingProviderService>();
builder.Services.AddScoped<TrainingProviderService>(sp => (TrainingProviderService)sp.GetRequiredService<ITrainingProviderService>());

builder.Services.AddScoped<IWspService, WspService>();
builder.Services.AddScoped<WspService>(sp => (WspService)sp.GetRequiredService<IWspService>());

builder.Services.AddScoped<IGrantService, GrantService>();
builder.Services.AddScoped<GrantService>(sp => (GrantService)sp.GetRequiredService<IGrantService>());

builder.Services.AddScoped<ILevyService, LevyService>();
builder.Services.AddScoped<LevyService>(sp => (LevyService)sp.GetRequiredService<ILevyService>());

builder.Services.AddScoped<IEtqaService, EtqaService>();
builder.Services.AddScoped<EtqaService>(sp => (EtqaService)sp.GetRequiredService<IEtqaService>());

builder.Services.AddScoped<IMentorRatioPolicyEngine, MentorRatioPolicyEngine>();
builder.Services.AddScoped<MentorRatioPolicyEngine>(sp => (MentorRatioPolicyEngine)sp.GetRequiredService<IMentorRatioPolicyEngine>());

builder.Services.AddScoped<IWorkplaceApprovalService, WorkplaceApprovalService>();
builder.Services.AddScoped<WorkplaceApprovalService>(sp => (WorkplaceApprovalService)sp.GetRequiredService<IWorkplaceApprovalService>());

builder.Services.AddScoped<ILearnerService, LearnerService>();
builder.Services.AddScoped<LearnerService>(sp => (LearnerService)sp.GetRequiredService<ILearnerService>());

// Workflow Engine & Storage Services
builder.Services.AddScoped<IWorkflowEngineService, WorkflowEngineService>();
builder.Services.AddScoped<WorkflowEngineService>(sp => (WorkflowEngineService)sp.GetRequiredService<IWorkflowEngineService>());

builder.Services.AddScoped<IStorageService, StorageService>();
builder.Services.AddScoped<StorageService>(sp => (StorageService)sp.GetRequiredService<IStorageService>());

// Financial Governance & MOA Services (Phase 4)
builder.Services.AddScoped<IFinanceService, FinanceService>();
builder.Services.AddScoped<FinanceService>(sp => (FinanceService)sp.GetRequiredService<IFinanceService>());

// Executive Skills Business Intelligence & Analytics
builder.Services.AddScoped<IAnalyticsService, AnalyticsService>();
builder.Services.AddScoped<AnalyticsService>(sp => (AnalyticsService)sp.GetRequiredService<IAnalyticsService>());

// Developer Documentation & Database Schema Engine
builder.Services.AddScoped<IDatabaseDocumentationService, DatabaseDocumentationService>();
builder.Services.AddScoped<DatabaseDocumentationService>(sp => (DatabaseDocumentationService)sp.GetRequiredService<IDatabaseDocumentationService>());

// System Configuration & Feature Flags Engine
builder.Services.AddScoped<ISystemConfigurationService, SystemConfigurationService>();
builder.Services.AddScoped<SystemConfigurationService>(sp => (SystemConfigurationService)sp.GetRequiredService<ISystemConfigurationService>());

builder.Services.AddScoped<IFeatureFlagService, FeatureFlagService>();
builder.Services.AddScoped<FeatureFlagService>(sp => (FeatureFlagService)sp.GetRequiredService<IFeatureFlagService>());

// Advanced Administration Catalog & Search Engine
builder.Services.AddScoped<IAdminCatalogService, AdminCatalogService>();
builder.Services.AddScoped<AdminCatalogService>(sp => (AdminCatalogService)sp.GetRequiredService<IAdminCatalogService>());

// Grid View Preferences Persistence (Clause 11.2.7)
builder.Services.AddScoped<IGridViewPreferenceService, GridViewPreferenceService>();
builder.Services.AddScoped<GridViewPreferenceService>(sp => (GridViewPreferenceService)sp.GetRequiredService<IGridViewPreferenceService>());

// Modern Navigation Menu & Role-Based Workspaces (Option C)
builder.Services.AddScoped<INavigationMenuService, NavigationMenuService>();
builder.Services.AddScoped<NavigationMenuService>(sp => (NavigationMenuService)sp.GetRequiredService<INavigationMenuService>());

// Configurable Document & File Storage
builder.Services.AddScoped<IFileStorageService, Nsdms.Infrastructure.Services.LocalFileStorageService>();

// Templated PDF & Certificate Generation (QuestPDF)
builder.Services.AddScoped<IPdfDocumentService, Nsdms.Infrastructure.Services.QuestPdfDocumentService>();

// Decoupled ERP Integration Adapter (Off by default)
builder.Services.AddScoped<IErpIntegrationService, Nsdms.Infrastructure.Services.ErpIntegrationService>();

// Advanced Learner Lifecycle Transitions
builder.Services.AddScoped<ILearnerLifecycleService, LearnerLifecycleService>();
builder.Services.AddScoped<LearnerLifecycleService>(sp => (LearnerLifecycleService)sp.GetRequiredService<ILearnerLifecycleService>());

// Workplace Monitoring & Inspection Surveys (Cluster 1)
builder.Services.AddScoped<IWorkplaceMonitoringService, WorkplaceMonitoringService>();
builder.Services.AddScoped<WorkplaceMonitoringService>(sp => (WorkplaceMonitoringService)sp.GetRequiredService<IWorkplaceMonitoringService>());

// Governance, Review Committees & Accreditation Scope (Cluster 2)
builder.Services.AddScoped<IReviewCommitteeService, ReviewCommitteeService>();
builder.Services.AddScoped<ReviewCommitteeService>(sp => (ReviewCommitteeService)sp.GetRequiredService<IReviewCommitteeService>());

// DG Project Implementation Plans & Payment Claims (Cluster 3)
builder.Services.AddScoped<IDgProjectImplementationService, DgProjectImplementationService>();
builder.Services.AddScoped<DgProjectImplementationService>(sp => (DgProjectImplementationService)sp.GetRequiredService<IDgProjectImplementationService>());

// Training Committees & WSP Disputes (Cluster 4)
builder.Services.AddScoped<ITrainingCommitteeAndDisputeService, TrainingCommitteeAndDisputeService>();
builder.Services.AddScoped<TrainingCommitteeAndDisputeService>(sp => (TrainingCommitteeAndDisputeService)sp.GetRequiredService<ITrainingCommitteeAndDisputeService>());

// Trade Test Administration & ARPL (Area 13)
builder.Services.AddScoped<ITradeTestAndArplService, TradeTestAndArplService>();
builder.Services.AddScoped<TradeTestAndArplService>(sp => (TradeTestAndArplService)sp.GetRequiredService<ITradeTestAndArplService>());

// Summative Assessment Reports & Moderation (Area 14)
builder.Services.AddScoped<ISummativeAssessmentAndModerationService, SummativeAssessmentAndModerationService>();
builder.Services.AddScoped<SummativeAssessmentAndModerationService>(sp => (SummativeAssessmentAndModerationService)sp.GetRequiredService<ISummativeAssessmentAndModerationService>());

// Qualifications Curriculum Development & QDF (Area 15)
builder.Services.AddScoped<IQcdAndCurriculumService, QcdAndCurriculumService>();
builder.Services.AddScoped<QcdAndCurriculumService>(sp => (QcdAndCurriculumService)sp.GetRequiredService<IQcdAndCurriculumService>());

// Non-SETA Qualifications & Provider Verification (Area 16)
builder.Services.AddScoped<INonSetaVerificationService, NonSetaVerificationService>();
builder.Services.AddScoped<NonSetaVerificationService>(sp => (NonSetaVerificationService)sp.GetRequiredService<INonSetaVerificationService>());

// Advanced SARS Historical Levy Reconciliation (Area 17)
builder.Services.AddScoped<ISarsLevyReconAuditService, SarsLevyReconAuditService>();
builder.Services.AddScoped<SarsLevyReconAuditService>(sp => (SarsLevyReconAuditService)sp.GetRequiredService<ISarsLevyReconAuditService>());

// Auxiliary Enterprise Services (Options A, B, C, D)
builder.Services.AddScoped<IBankingDetailsService, BankingDetailsService>();
builder.Services.AddScoped<BankingDetailsService>(sp => (BankingDetailsService)sp.GetRequiredService<IBankingDetailsService>());

builder.Services.AddScoped<ISdfAppointmentService, SdfAppointmentService>();
builder.Services.AddScoped<SdfAppointmentService>(sp => (SdfAppointmentService)sp.GetRequiredService<ISdfAppointmentService>());

builder.Services.AddScoped<IContractVariationService, ContractVariationService>();
builder.Services.AddScoped<ContractVariationService>(sp => (ContractVariationService)sp.GetRequiredService<IContractVariationService>());

builder.Services.AddScoped<IExtensionOfScopeService, ExtensionOfScopeService>();
builder.Services.AddScoped<ExtensionOfScopeService>(sp => (ExtensionOfScopeService)sp.GetRequiredService<IExtensionOfScopeService>());

// WSP Qualitative Survey & Skills Gap Service
builder.Services.AddScoped<IWspSurveyService, WspSurveyService>();
builder.Services.AddScoped<WspSurveyService>(sp => (WspSurveyService)sp.GetRequiredService<IWspSurveyService>());

// Statutory Batch Pre-Submission Validation Engine (SETMIS & NLRD)
builder.Services.AddScoped<Nsdms.Application.Validation.IStatutoryValidationService, StatutoryValidationService>();
builder.Services.AddScoped<StatutoryValidationService>(sp => (StatutoryValidationService)sp.GetRequiredService<Nsdms.Application.Validation.IStatutoryValidationService>());

// AQP Quality Partner & EISA Assessment Service
builder.Services.AddScoped<IAqpPartnerService, AqpPartnerService>();
builder.Services.AddScoped<AqpPartnerService>(sp => (AqpPartnerService)sp.GetRequiredService<IAqpPartnerService>());

// Brand Asset Service
builder.Services.AddScoped<IBrandAssetService, Nsdms.Infrastructure.Services.BrandAssetService>();
builder.Services.AddScoped<Nsdms.Infrastructure.Services.BrandAssetService>(sp => (Nsdms.Infrastructure.Services.BrandAssetService)sp.GetRequiredService<IBrandAssetService>());

// Enterprise PDF & Excel Report Export Service (QuestPDF)
builder.Services.AddScoped<IReportExportService, Nsdms.Infrastructure.Services.ReportExportService>();
builder.Services.AddScoped<Nsdms.Infrastructure.Services.ReportExportService>(sp => (Nsdms.Infrastructure.Services.ReportExportService)sp.GetRequiredService<IReportExportService>());

// Workflow Governance & Delegations Service
builder.Services.AddScoped<IWorkflowGovernanceService, WorkflowGovernanceService>();
builder.Services.AddScoped<WorkflowGovernanceService>(sp => (WorkflowGovernanceService)sp.GetRequiredService<IWorkflowGovernanceService>());

// MoA Template & Reusable Clause Engine (Option A)
builder.Services.AddScoped<IMoaTemplateEngineService, MoaTemplateEngineService>();
builder.Services.AddScoped<MoaTemplateEngineService>(sp => (MoaTemplateEngineService)sp.GetRequiredService<IMoaTemplateEngineService>());

// BankservAfrica AVS Service (Option C)
builder.Services.AddScoped<IBankservAvsService, Nsdms.Infrastructure.Services.BankservAvsService>();
builder.Services.AddScoped<Nsdms.Infrastructure.Services.BankservAvsService>(sp => (Nsdms.Infrastructure.Services.BankservAvsService)sp.GetRequiredService<IBankservAvsService>());

// High-Throughput Streaming Batch Ingestion Service (Option D)
builder.Services.AddScoped<ISqlBulkBatchIngestionService, Nsdms.Infrastructure.Services.SqlBulkBatchIngestionService>();
builder.Services.AddScoped<Nsdms.Infrastructure.Services.SqlBulkBatchIngestionService>(sp => (Nsdms.Infrastructure.Services.SqlBulkBatchIngestionService)sp.GetRequiredService<ISqlBulkBatchIngestionService>());

// Universal Document Template & Cryptographic Verification Engine (Strategic Action Items)
builder.Services.AddScoped<IDocumentVerificationService, DocumentVerificationService>();
builder.Services.AddScoped<DocumentVerificationService>(sp => (DocumentVerificationService)sp.GetRequiredService<IDocumentVerificationService>());

builder.Services.AddScoped<IEnterpriseDocumentTemplateService, EnterpriseDocumentTemplateService>();
builder.Services.AddScoped<EnterpriseDocumentTemplateService>(sp => (EnterpriseDocumentTemplateService)sp.GetRequiredService<IEnterpriseDocumentTemplateService>());

// Real-time SignalR Notification Service & Transport Publisher
builder.Services.AddSingleton<Nsdms.Web.Services.RealtimeNotificationService>();
builder.Services.AddSingleton<IRealtimeNotificationService>(sp => sp.GetRequiredService<Nsdms.Web.Services.RealtimeNotificationService>());
builder.Services.AddSingleton<ISignalRNotificationPublisher>(sp => sp.GetRequiredService<Nsdms.Web.Services.RealtimeNotificationService>());

// Persistent Notification Inbox Service
builder.Services.AddScoped<INotificationService, Nsdms.Infrastructure.Services.NotificationService>();
builder.Services.AddScoped<Nsdms.Infrastructure.Services.NotificationService>(sp => (Nsdms.Infrastructure.Services.NotificationService)sp.GetRequiredService<INotificationService>());

// Background Scheduler Hosted Service (Off by default)
builder.Services.AddHostedService<Nsdms.Infrastructure.Services.BackgroundSchedulerHostedService>();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error", createScopeForErrors: true);
    app.UseHsts();
}

app.UseResponseCompression();
app.UseHttpsRedirection();
app.UseAntiforgery();

app.MapStaticAssets();

// Map Real-time SignalR Hub
app.MapHub<Nsdms.Web.Hubs.NsdmsNotificationHub>("/hubs/notifications");

// PDF & Statutory Document Download Endpoints
app.MapGet("/api/documents/moa/{id:int}/pdf", async (int id, IPdfDocumentService pdf) =>
{
    var bytes = await pdf.GenerateGrantMoaContractPdfAsync(id);
    return Results.File(bytes, "application/pdf", $"GrantMoa_Contract_{id}.pdf");
});

app.MapGet("/api/documents/tradetest/{id:int}/pdf", async (int id, IPdfDocumentService pdf) =>
{
    var bytes = await pdf.GenerateTradeTestCertificatePdfAsync(id);
    return Results.File(bytes, "application/pdf", $"TradeTest_Artisan_Certificate_{id}.pdf");
});

app.MapGet("/api/documents/wsp/{id:int}/pdf", async (int id, IPdfDocumentService pdf) =>
{
    var bytes = await pdf.GenerateWspOutcomeLetterPdfAsync(id);
    return Results.File(bytes, "application/pdf", $"WSP_Outcome_Letter_{id}.pdf");
});

app.MapGet("/api/documents/remittance/{id:int}/pdf", async (int id, IPdfDocumentService pdf) =>
{
    var bytes = await pdf.GenerateMandatoryRebateRemittancePdfAsync(id);
    return Results.File(bytes, "application/pdf", $"Mandatory_Rebate_Remittance_{id}.pdf");
});

app.MapGet("/api/documents/templates/{id:int}/simulation-pdf", async (int id, IEnterpriseDocumentTemplateService templateService, string? scenario, HttpContext context) =>
{
    var profiles = templateService.GetDefaultScenarioTokenProfiles();
    var selectedScenario = !string.IsNullOrEmpty(scenario) && profiles.ContainsKey(scenario) ? scenario : "LevyEmployer";
    var tokens = new Dictionary<string, string>(profiles[selectedScenario]);

    foreach (var query in context.Request.Query)
    {
        if (query.Key != "scenario" && query.Key != "t" && !string.IsNullOrWhiteSpace(query.Value))
        {
            tokens[query.Key] = query.Value.ToString();
        }
    }

    var bytes = await templateService.GenerateSimulatedPdfAsync(id, tokens, includeWatermark: true);
    return Results.File(bytes, "application/pdf", $"Template_Simulation_{id}_{selectedScenario}.pdf");
});

app.MapGet("/api/documents/moa-templates/{id:int}/simulation-pdf", async (int id, IMoaTemplateEngineService moaService, string? scenario, HttpContext context) =>
{
    var profiles = moaService.GetDefaultScenarioTokenProfiles();
    var selectedScenario = !string.IsNullOrEmpty(scenario) && profiles.ContainsKey(scenario) ? scenario : "LevyEmployer";
    var tokens = new Dictionary<string, string>(profiles[selectedScenario]);

    foreach (var query in context.Request.Query)
    {
        if (query.Key != "scenario" && query.Key != "t" && !string.IsNullOrWhiteSpace(query.Value))
        {
            tokens[query.Key] = query.Value.ToString();
        }
    }

    var bytes = await moaService.GenerateSimulatedPdfAsync(id, tokens, includeWatermark: true);
    return Results.File(bytes, "application/pdf", $"MoaTemplate_Simulation_{id}_{selectedScenario}.pdf");
});

app.MapRazorComponents<App>()
    .AddInteractiveServerRenderMode();

// Auto-create database & seed lookups, sample data, workflow definitions, financial records, and system governance
using (var scope = app.Services.CreateScope())
{
    try
    {
        var db = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        db.Database.EnsureCreated();
        Phase2SchemaMigrator.EnsurePhase2SchemaAsync(db).GetAwaiter().GetResult();
        Phase3WorkflowSchemaMigrator.MigrateWorkflowSchemaAsync(app.Services).GetAwaiter().GetResult();
        Phase4FinancialSchemaMigrator.MigrateFinancialSchemaAsync(app.Services).GetAwaiter().GetResult();
        Phase5GovernanceSchemaMigrator.MigrateGovernanceSchemaAsync(app.Services).GetAwaiter().GetResult();
        Phase6GovernanceSchemaMigrator.MigrateGovernanceSchemaAsync(app.Services).GetAwaiter().GetResult();
        Phase7SetmisLookupMigrator.MigrateSetmisLookupsAsync(app.Services).GetAwaiter().GetResult();
        Phase8SetmisSchemaAlignmentMigrator.MigrateSetmisSchemaAlignmentAsync(app.Services).GetAwaiter().GetResult();
        Phase8NotificationSchemaMigrator.MigrateNotificationSchemaAsync(app.Services).GetAwaiter().GetResult();
        Phase8WspSurveyAndAqpSchemaMigrator.MigrateAsync(db).GetAwaiter().GetResult();
        Phase9TemporalTablesSchemaMigrator.MigrateTemporalTablesSchemaAsync(app.Services).GetAwaiter().GetResult();
        Phase10MoaTemplateEngineMigrator.MigrateMoaTemplateSchemaAsync(app.Services).GetAwaiter().GetResult();
        Phase11UniversalDocumentVerificationMigrator.MigrateDocumentVerificationSchemaAsync(app.Services).GetAwaiter().GetResult();
        Phase12SchemaAlignmentMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult();
        Phase13TradeMentorRatioSchemaMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult();
        Phase14WspEligibilityAndMoaProvisioningMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult();
        SampleDataSeeder.SeedSampleDataAsync(db).GetAwaiter().GetResult();
        var featureFlags = scope.ServiceProvider.GetRequiredService<IFeatureFlagService>();
        featureFlags.SeedDefaultFeatureFlagsAsync().GetAwaiter().GetResult();
        var roleService = scope.ServiceProvider.GetRequiredService<IRolePermissionService>();
        roleService.SeedDefaultRolePermissionsAsync().GetAwaiter().GetResult();
        app.Logger.LogInformation("SQL Server database verified with all entities, SETMIS lookups, sample data, workflow engine, financial governance, system configuration & security roles.");
    }
    catch (Exception ex)
    {
        app.Logger.LogWarning(ex, "Could not automatically initialize SQL Server tables on startup.");
    }
}

app.Run();
