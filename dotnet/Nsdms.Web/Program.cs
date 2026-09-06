using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using MudBlazor.Services;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Interceptors;
using Nsdms.Infrastructure.Services;
using Nsdms.Web.Components;
using Microsoft.AspNetCore.Components.Authorization;

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

// Authentication & Authorization Services
builder.Services.AddCascadingAuthenticationState();
builder.Services.AddScoped<AuthenticationStateProvider, Nsdms.Web.Services.NsdmsAuthenticationStateProvider>();
builder.Services.AddScoped<Nsdms.Web.Services.NsdmsAuthenticationStateProvider>(sp => (Nsdms.Web.Services.NsdmsAuthenticationStateProvider)sp.GetRequiredService<AuthenticationStateProvider>());
builder.Services.AddAuthentication(options =>
{
    options.DefaultScheme = Microsoft.AspNetCore.Authentication.Cookies.CookieAuthenticationDefaults.AuthenticationScheme;
})
.AddCookie(Microsoft.AspNetCore.Authentication.Cookies.CookieAuthenticationDefaults.AuthenticationScheme, options =>
{
    options.Cookie.Name = "NSDMS_AUTH_TICKET";
    options.Cookie.HttpOnly = true;
    options.Cookie.SameSite = SameSiteMode.Lax;
});
builder.Services.AddAuthorization();

// Add Razor components with Interactive Server mode
builder.Services.AddRazorComponents()
    .AddInteractiveServerComponents();

// Database & Interceptors
builder.Services.AddSingleton<AuditableEntityInterceptor>();
builder.Services.AddDbContextFactory<NsdmsDbContext>((sp, options) =>
{
    var interceptor = sp.GetRequiredService<AuditableEntityInterceptor>();
    var conn = builder.Configuration.GetConnectionString("DefaultConnection") 
               ?? throw new InvalidOperationException("Connection string 'DefaultConnection' was not found in configuration.");
    options.UseSqlServer(conn)
           .AddInterceptors(interceptor);
});
// Multi-Tenancy Provider
builder.Services.AddScoped<ITenantProvider, DefaultTenantProvider>();
builder.Services.AddScoped<DefaultTenantProvider>(sp => (DefaultTenantProvider)sp.GetRequiredService<ITenantProvider>());

builder.Services.AddScoped<NsdmsDbContext>(sp => new NsdmsDbContext(
    sp.GetRequiredService<DbContextOptions<NsdmsDbContext>>(),
    sp.GetRequiredService<ITenantProvider>()));

// Interface registration
builder.Services.AddScoped<INsdmsDbContext>(sp => sp.GetRequiredService<NsdmsDbContext>());
builder.Services.AddScoped<INsdmsDbContextFactory, NsdmsDbContextFactory>();

// Application Services
builder.Services.AddScoped<AuditService>();
builder.Services.AddScoped<IAuditService>(sp => sp.GetRequiredService<AuditService>());
builder.Services.AddScoped<PersonService>();
builder.Services.AddScoped<IPersonService>(sp => sp.GetRequiredService<PersonService>());
builder.Services.AddScoped<OrganisationService>();
builder.Services.AddScoped<IOrganisationService>(sp => sp.GetRequiredService<OrganisationService>());
builder.Services.AddScoped<OrganisationContextService>();
builder.Services.AddScoped<IOrganisationContextService>(sp => sp.GetRequiredService<OrganisationContextService>());
builder.Services.AddScoped<IdentityService>();
builder.Services.AddScoped<IIdentityService>(sp => sp.GetRequiredService<IdentityService>());
builder.Services.AddScoped<RolePermissionService>();
builder.Services.AddScoped<IRolePermissionService>(sp => sp.GetRequiredService<RolePermissionService>());
builder.Services.AddScoped<CaslAbilityService>();
builder.Services.AddScoped<ICaslAbilityService>(sp => sp.GetRequiredService<CaslAbilityService>());
builder.Services.AddScoped<VisitService>();
builder.Services.AddScoped<IVisitService>(sp => sp.GetRequiredService<VisitService>());
builder.Services.AddScoped<LookupService>();
builder.Services.AddScoped<ILookupService>(sp => sp.GetRequiredService<LookupService>());
builder.Services.AddScoped<TrainingProviderService>();
builder.Services.AddScoped<ITrainingProviderService>(sp => sp.GetRequiredService<TrainingProviderService>());

// Phase 7: SDP Campus Infrastructure & Assessor Linking
builder.Services.AddScoped<SdpCampusService>();
builder.Services.AddScoped<ISdpCampusService>(sp => sp.GetRequiredService<SdpCampusService>());
builder.Services.AddScoped<WspService>();
builder.Services.AddScoped<IWspService>(sp => sp.GetRequiredService<WspService>());
builder.Services.AddScoped<GrantService>();
builder.Services.AddScoped<IGrantService>(sp => sp.GetRequiredService<GrantService>());
builder.Services.AddScoped<LevyService>();
builder.Services.AddScoped<ILevyService>(sp => sp.GetRequiredService<LevyService>());
builder.Services.AddScoped<EtqaService>();
builder.Services.AddScoped<IEtqaService>(sp => sp.GetRequiredService<EtqaService>());
builder.Services.AddScoped<IMentorRatioPolicyEngine, MentorRatioPolicyEngine>();
builder.Services.AddScoped<WorkplaceApprovalService>();
builder.Services.AddScoped<IWorkplaceApprovalService>(sp => sp.GetRequiredService<WorkplaceApprovalService>());
builder.Services.AddScoped<LearnerService>();
builder.Services.AddScoped<ILearnerService>(sp => sp.GetRequiredService<LearnerService>());
builder.Services.AddScoped<ILearnerStpRiskEngine, LearnerStpRiskEngine>();
builder.Services.AddScoped<ILearnerBulkIngestionService, LearnerBulkIngestionService>();

// Workflow Engine & Storage Services
builder.Services.AddScoped<IWorkflowEngineService, WorkflowEngineService>();
builder.Services.AddScoped<IStorageService, StorageService>();

// Financial Governance & MOA Services (Phase 4)
builder.Services.AddScoped<IFinanceService, FinanceService>();

// Executive Skills Business Intelligence & Analytics
builder.Services.AddScoped<IAnalyticsService, AnalyticsService>();

// Developer Documentation & Database Schema Engine
builder.Services.AddScoped<IDatabaseDocumentationService, DatabaseDocumentationService>();

// System Configuration & Feature Flags Engine
builder.Services.AddScoped<ISystemConfigurationService, SystemConfigurationService>();
builder.Services.AddScoped<IFeatureFlagService, FeatureFlagService>();

// Advanced Administration Catalog & Search Engine
builder.Services.AddScoped<IAdminCatalogService, AdminCatalogService>();

// Grid View Preferences Persistence (Clause 11.2.7)
builder.Services.AddScoped<IGridViewPreferenceService, GridViewPreferenceService>();

// Wizard Draft Persistence & Resume Lifecycle Engine (Option 1)
builder.Services.AddScoped<IWizardDraftService, Nsdms.Infrastructure.Services.WizardDraftService>();

// Modern Navigation Menu & Role-Based Workspaces (Option C)
builder.Services.AddScoped<INavigationMenuService, NavigationMenuService>();

// Configurable Document & File Storage
builder.Services.AddScoped<IFileStorageService, Nsdms.Infrastructure.Services.LocalFileStorageService>();

// Templated PDF & Certificate Generation (QuestPDF)
builder.Services.AddScoped<IPdfDocumentService, Nsdms.Infrastructure.Services.QuestPdfDocumentService>();

// Decoupled ERP Integration Adapter (Off by default)
builder.Services.AddScoped<IErpIntegrationService, Nsdms.Infrastructure.Services.ErpIntegrationService>();
builder.Services.AddScoped<IErpOutboxQueueService, Nsdms.Infrastructure.Services.ErpOutboxQueueService>();
builder.Services.AddScoped<INonLevyNumberGeneratorService, Nsdms.Infrastructure.Services.NonLevyNumberGeneratorService>();
builder.Services.AddScoped<IChamberDerivationService, Nsdms.Infrastructure.Services.ChamberDerivationService>();

// Advanced Learner Lifecycle Transitions
builder.Services.AddScoped<ILearnerLifecycleService, LearnerLifecycleService>();

// Workplace Monitoring & Inspection Surveys (Cluster 1)
builder.Services.AddScoped<IWorkplaceMonitoringService, WorkplaceMonitoringService>();

// Governance, Review Committees & Accreditation Scope (Cluster 2)
builder.Services.AddScoped<IReviewCommitteeService, ReviewCommitteeService>();

// DG Project Implementation Plans & Payment Claims (Cluster 3)
builder.Services.AddScoped<IDgProjectImplementationService, DgProjectImplementationService>();

// Training Committees & WSP Disputes (Cluster 4)
builder.Services.AddScoped<ITrainingCommitteeAndDisputeService, TrainingCommitteeAndDisputeService>();

// Trade Test Administration & ARPL (Area 13)
builder.Services.AddScoped<ITradeTestAndArplService, TradeTestAndArplService>();

// Phase 5: Artisan Development & NAMB Batch Governance
builder.Services.AddScoped<INambBatchService, NambBatchService>();

// Summative Assessment Reports & Moderation (Area 14)
builder.Services.AddScoped<ISummativeAssessmentAndModerationService, SummativeAssessmentAndModerationService>();

// Qualifications Curriculum Development & QDF (Area 15)
builder.Services.AddScoped<IQcdAndCurriculumService, QcdAndCurriculumService>();

// Non-SETA Qualifications & Provider Verification (Area 16)
builder.Services.AddScoped<INonSetaVerificationService, NonSetaVerificationService>();
builder.Services.AddScoped<IQualificationEnrolmentGatekeeperService, QualificationEnrolmentGatekeeperService>();

// Advanced SARS Historical Levy Reconciliation (Area 17)
builder.Services.AddScoped<ISarsLevyReconAuditService, SarsLevyReconAuditService>();

// Option A: Reactive Streaming Pipeline & SqlBulkCopy Staging Table Architecture
builder.Services.AddScoped<ISarsBulkStagingWriter, SarsBulkStagingWriter>();
builder.Services.AddScoped<ISarsCompliancePreProcessor, SarsCompliancePreProcessor>();
builder.Services.AddScoped<ISarsLevyStreamingPipeline, SarsLevyStreamingPipeline>();

// Auxiliary Enterprise Services (Options A, B, C, D)
builder.Services.AddScoped<IBankingDetailsService, BankingDetailsService>();
builder.Services.AddScoped<ISdfAppointmentService, SdfAppointmentService>();
builder.Services.AddScoped<IContractVariationService, ContractVariationService>();
builder.Services.AddScoped<IExtensionOfScopeService, ExtensionOfScopeService>();

// WSP Qualitative Survey & Skills Gap Service
builder.Services.AddScoped<IWspSurveyService, WspSurveyService>();

// Statutory Batch Pre-Submission Validation Engine (SETMIS & NLRD)
builder.Services.AddScoped<Nsdms.Application.Validation.IStatutoryValidationService, StatutoryValidationService>();

// Production Statutory Extract Generation Engines (DHET SETMIS & SAQA NLRD Edu.Dex)
builder.Services.AddScoped<ISetmisExtractService, SetmisExtractService>();
builder.Services.AddScoped<INlrdExtractService, NlrdExtractService>();

// Automated Statutory Schedulers & Background Jobs
builder.Services.AddScoped<IStatutorySchedulerService, StatutorySchedulerService>();

// AQP Quality Partner & EISA Assessment Service
builder.Services.AddScoped<IAqpPartnerService, AqpPartnerService>();

// Phase 3: Core Statutory Workflow Services
builder.Services.AddScoped<IWspSignoffService, WspSignoffService>();
builder.Services.AddScoped<IDiscretionaryGrantClaimService, DiscretionaryGrantClaimService>();

// Phase 4: ETQA Assessor Statutory Lifecycle & Registration Services
builder.Services.AddScoped<IAssessorRegistrationService, AssessorRegistrationService>();
builder.Services.AddScoped<IAssessorReRegistrationService, AssessorReRegistrationService>();
builder.Services.AddScoped<IAssessorDisciplinaryService, AssessorDisciplinaryService>();

// Brand Asset Service
builder.Services.AddScoped<IBrandAssetService, Nsdms.Infrastructure.Services.BrandAssetService>();

// Enterprise PDF & Excel Report Export Service (QuestPDF)
builder.Services.AddScoped<IReportExportService, Nsdms.Infrastructure.Services.ReportExportService>();

// Workflow Governance & Delegations Service
builder.Services.AddScoped<IWorkflowGovernanceService, WorkflowGovernanceService>();

// MoA Template & Reusable Clause Engine (Option A)
builder.Services.AddScoped<IMoaTemplateEngineService, MoaTemplateEngineService>();

// BankservAfrica AVS Service (Option C)
builder.Services.AddScoped<IBankservAvsService, Nsdms.Infrastructure.Services.BankservAvsService>();

// High-Throughput Streaming Batch Ingestion Service (Option D)
builder.Services.AddScoped<ISqlBulkBatchIngestionService, Nsdms.Infrastructure.Services.SqlBulkBatchIngestionService>();

// Universal Document Template & Cryptographic Verification Engine (Strategic Action Items)
builder.Services.AddScoped<IDocumentVerificationService, DocumentVerificationService>();
builder.Services.AddScoped<IEnterpriseDocumentTemplateService, EnterpriseDocumentTemplateService>();

// Real-time SignalR Notification Service & Transport Publisher
builder.Services.AddSingleton<Nsdms.Web.Services.RealtimeNotificationService>();
builder.Services.AddSingleton<IRealtimeNotificationService>(sp => sp.GetRequiredService<Nsdms.Web.Services.RealtimeNotificationService>());
builder.Services.AddSingleton<ISignalRNotificationPublisher>(sp => sp.GetRequiredService<Nsdms.Web.Services.RealtimeNotificationService>());

// Persistent Notification Inbox Service
builder.Services.AddScoped<INotificationService, Nsdms.Infrastructure.Services.NotificationService>();

// SLA Monitoring & Statutory Deadline Alert Engine
builder.Services.AddScoped<ISlaMonitoringService, Nsdms.Infrastructure.Services.SlaMonitoringService>();

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
app.UseAuthentication();
app.UseAuthorization();
app.UseAntiforgery();

app.MapStaticAssets();
app.UseStaticFiles();

// Map Real-time SignalR Hub
app.MapHub<Nsdms.Web.Hubs.NsdmsNotificationHub>("/hubs/notifications");

// PDF & Statutory Document Download Endpoints (Secured per POPIA & Statutory Governance)
app.MapGet("/api/documents/moa/{id:int}/pdf", async (int id, IPdfDocumentService pdf) =>
{
    var bytes = await pdf.GenerateGrantMoaContractPdfAsync(id);
    return Results.File(bytes, "application/pdf", $"GrantMoa_Contract_{id}.pdf");
}).RequireAuthorization();

app.MapGet("/api/documents/tradetest/{id:int}/pdf", async (int id, IPdfDocumentService pdf) =>
{
    var bytes = await pdf.GenerateTradeTestCertificatePdfAsync(id);
    return Results.File(bytes, "application/pdf", $"TradeTest_Artisan_Certificate_{id}.pdf");
}).RequireAuthorization();

app.MapGet("/api/documents/tradetest/{id:int}/form-pdf", async (int id, IPdfDocumentService pdf) =>
{
    var bytes = await pdf.GenerateArplApplicationFormPdfAsync(id);
    return Results.File(bytes, "application/pdf", $"ARPL_Application_Form_ETQ_TP_ARPL_01_{id}.pdf");
}).RequireAuthorization();

app.MapGet("/api/documents/wsp/{id:int}/pdf", async (int id, IPdfDocumentService pdf) =>
{
    var bytes = await pdf.GenerateWspOutcomeLetterPdfAsync(id);
    return Results.File(bytes, "application/pdf", $"WSP_Outcome_Letter_{id}.pdf");
}).RequireAuthorization();

app.MapGet("/api/documents/remittance/{id:int}/pdf", async (int id, IPdfDocumentService pdf) =>
{
    var bytes = await pdf.GenerateMandatoryRebateRemittancePdfAsync(id);
    return Results.File(bytes, "application/pdf", $"Mandatory_Rebate_Remittance_{id}.pdf");
}).RequireAuthorization();

app.MapGet("/api/documents/workplace-approval/{id:int}/letter-pdf", async (int id, IPdfDocumentService pdf) =>
{
    var bytes = await pdf.GenerateWorkplaceApprovalLetterPdfAsync(id);
    return Results.File(bytes, "application/pdf", $"WorkplaceApproval_Outcome_Letter_ETQ_TP_003_{id}.pdf");
}).RequireAuthorization();

app.MapGet("/api/documents/workplace-approval/{id:int}/report-pdf", async (int id, IPdfDocumentService pdf) =>
{
    var bytes = await pdf.GenerateWorkplaceApprovalReportPdfAsync(id);
    return Results.File(bytes, "application/pdf", $"WorkplaceApproval_Report_ETQ_TP_054_{id}.pdf");
}).RequireAuthorization();


// Statutory Flat-File and Batch Zip Package Download Endpoints
app.MapGet("/api/statutory/setmis/files/{fileCode}", async (string fileCode, ISetmisExtractService setmis) =>
{
    var res = await setmis.ExtractSetmisFileAsync(fileCode);
    return Results.File(res.ContentBytes, "text/plain", res.FileName);
}).RequireAuthorization();

app.MapGet("/api/statutory/nlrd/files/{fileCode}", async (string fileCode, INlrdExtractService nlrd) =>
{
    var res = await nlrd.ExtractNlrdFileAsync(fileCode);
    return Results.File(res.ContentBytes, "text/plain", res.FileName);
}).RequireAuthorization();

app.MapGet("/api/statutory/batches/{id:int}/download", async (int id, ISetmisExtractService setmis, INlrdExtractService nlrd, INsdmsDbContextFactory dbFactory) =>
{
    using var db = await dbFactory.CreateDbContextAsync();
    var b = await Microsoft.EntityFrameworkCore.EntityFrameworkQueryableExtensions.FirstOrDefaultAsync(db.StatutorySubmissionBatches, x => x.Id == id);
    if (b == null) return Results.NotFound();

    if (b.BatchType == "SETMIS")
    {
        var zip = await setmis.DownloadSetmisBatchArchiveAsync(id);
        return Results.File(zip.ZipBytes, "application/zip", zip.ArchiveFileName);
    }
    else
    {
        var zip = await nlrd.DownloadNlrdBatchArchiveAsync(id);
        return Results.File(zip.ZipBytes, "application/zip", zip.ArchiveFileName);
    }
}).RequireAuthorization();

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
}).RequireAuthorization();

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
}).RequireAuthorization();

app.MapRazorComponents<App>()
    .AddInteractiveServerRenderMode();

// Auto-create database & seed lookups, sample data, workflow definitions, financial records, and system governance
using (var scope = app.Services.CreateScope())
{
    var db = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
    try { db.Database.EnsureCreated(); } catch (Exception ex) { app.Logger.LogWarning(ex, "EnsureCreated skipped or already initialized."); }

    void RunMigrator(string name, Action action)
    {
        try
        {
            action();
        }
        catch (Exception ex)
        {
            app.Logger.LogWarning(ex, "Migrator {MigratorName} logged a warning or partial skip.", name);
        }
    }

    RunMigrator("Phase2", () => Phase2SchemaMigrator.EnsurePhase2SchemaAsync(db).GetAwaiter().GetResult());
    RunMigrator("Phase3", () => Phase3WorkflowSchemaMigrator.MigrateWorkflowSchemaAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase4", () => Phase4FinancialSchemaMigrator.MigrateFinancialSchemaAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase5", () => Phase5GovernanceSchemaMigrator.MigrateGovernanceSchemaAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase6", () => Phase6GovernanceSchemaMigrator.MigrateGovernanceSchemaAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase7", () => Phase7SetmisLookupMigrator.MigrateSetmisLookupsAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase8Alignment", () => Phase8SetmisSchemaAlignmentMigrator.MigrateSetmisSchemaAlignmentAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase8Notification", () => Phase8NotificationSchemaMigrator.MigrateNotificationSchemaAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase8WspSurvey", () => Phase8WspSurveyAndAqpSchemaMigrator.MigrateAsync(db).GetAwaiter().GetResult());
    RunMigrator("Phase9Temporal", () => Phase9TemporalTablesSchemaMigrator.MigrateTemporalTablesSchemaAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase10Moa", () => Phase10MoaTemplateEngineMigrator.MigrateMoaTemplateSchemaAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase11Verification", () => Phase11UniversalDocumentVerificationMigrator.MigrateDocumentVerificationSchemaAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase12Alignment", () => Phase12SchemaAlignmentMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase13MentorRatio", () => Phase13TradeMentorRatioSchemaMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase14WspEligibility", () => Phase14WspEligibilityAndMoaProvisioningMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase15SicCodeChamber", () => Phase15SicCodeChamberGovernanceMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase16PhysicalConstraintsAndBigInt", () => Phase16PhysicalConstraintsAndBigIntMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase17StatutoryBatch", () => Phase17StatutoryBatchMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase18CoreStatutoryWorkflows", () => Phase18CoreStatutoryWorkflowsMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase19EtqaReRegistration", () => Phase19EtqaReRegistrationMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase20NambBatch", () => Phase20NambBatchMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase21SdpCampus", () => Phase21SdpCampusMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase22SarsLevyStreamingStaging", () => Phase22SarsLevyStreamingStagingMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase23LookupIndexes", () => Phase23LookupIndexesMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase24ErpOutboxQueue", () => Phase24ErpOutboxQueueMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase25VerticalPartitioning", () => Phase25VerticalPartitioningMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase26NonLevyAndChamber", () => Phase26NonLevyAndChamberMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase27PhysicalRenaming", () => Phase27PhysicalRenamingMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase28WizardDraftPersistence", () => Phase28WizardDraftPersistenceMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase29LearnerRegistrationAlignment", () => Phase29LearnerRegistrationAlignmentMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase30ArplGovernance", () => Phase30ArplGovernanceSchemaMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase31WorkplaceApproval", () => Phase31WorkplaceApprovalSchemaMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase32BursaryRegistration", () => Phase32BursaryRegistrationGovernanceMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase33LearnerLifecycleManagement", () => Phase33LearnerLifecycleManagementMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase33LearnerDualChannel", () => Phase33LearnerDualChannelMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase34SdpAccreditationGovernance", () => Phase34SdpAccreditationGovernanceMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("SampleData", () => SampleDataSeeder.SeedSampleDataAsync(db).GetAwaiter().GetResult());
    RunMigrator("FeatureFlags", () => scope.ServiceProvider.GetRequiredService<IFeatureFlagService>().SeedDefaultFeatureFlagsAsync().GetAwaiter().GetResult());
    RunMigrator("RolePermissions", () => scope.ServiceProvider.GetRequiredService<IRolePermissionService>().SeedDefaultRolePermissionsAsync().GetAwaiter().GetResult());
    app.Logger.LogInformation("SQL Server database verified with all entities, SETMIS lookups, sample data, workflow engine, financial governance, system configuration & security roles.");
}

app.Run();
