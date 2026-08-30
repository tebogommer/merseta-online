using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using MudBlazor.Services;
using Nsdms.Application.Common;
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

// SETMIS Compliance & Business Intelligence (Phase 5)
builder.Services.AddScoped<ISetmisService, SetmisService>();
builder.Services.AddScoped<SetmisService>(sp => (SetmisService)sp.GetRequiredService<ISetmisService>());

builder.Services.AddScoped<IAnalyticsService, AnalyticsService>();
builder.Services.AddScoped<AnalyticsService>(sp => (AnalyticsService)sp.GetRequiredService<IAnalyticsService>());

// Developer Documentation & Database Schema Engine
builder.Services.AddScoped<IDatabaseDocumentationService, DatabaseDocumentationService>();
builder.Services.AddScoped<DatabaseDocumentationService>(sp => (DatabaseDocumentationService)sp.GetRequiredService<IDatabaseDocumentationService>());

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

app.MapRazorComponents<App>()
    .AddInteractiveServerRenderMode();

// Auto-create database & seed lookups, sample data, workflow definitions, financial records, and SETMIS compliance
using (var scope = app.Services.CreateScope())
{
    try
    {
        var db = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        db.Database.EnsureCreated();
        Phase2SchemaMigrator.EnsurePhase2SchemaAsync(db).GetAwaiter().GetResult();
        SampleDataSeeder.SeedSampleDataAsync(db).GetAwaiter().GetResult();
        Phase3WorkflowSchemaMigrator.MigrateWorkflowSchemaAsync(app.Services).GetAwaiter().GetResult();
        Phase4FinancialSchemaMigrator.MigrateFinancialSchemaAsync(app.Services).GetAwaiter().GetResult();
        Phase5SetmisSchemaMigrator.MigrateSetmisSchemaAsync(app.Services).GetAwaiter().GetResult();
        app.Logger.LogInformation("SQL Server database verified with all entities, lookups, sample data, workflow engine, financial governance & SETMIS compliance.");
    }
    catch (Exception ex)
    {
        app.Logger.LogWarning(ex, "Could not automatically initialize SQL Server tables on startup.");
    }
}

app.Run();
