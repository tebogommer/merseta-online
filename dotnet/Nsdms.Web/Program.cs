using System.Diagnostics;
using System.Security.Claims;
using System.Threading.RateLimiting;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.DataProtection;
using Microsoft.AspNetCore.Components.Authorization;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.HttpOverrides;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.RateLimiting;
using Microsoft.EntityFrameworkCore;
using MudBlazor.Services;
using Nsdms.Application;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Services;
using Nsdms.Web.Components;
using Nsdms.Web.Endpoints;
using Nsdms.Web.Hubs;
using Nsdms.Web.Services;

var builder = WebApplication.CreateBuilder(args);

// Ensure static web assets development manifest is loaded even when running compiled DLL directly
builder.WebHost.UseStaticWebAssets();

// Add MudBlazor services
builder.Services.AddMudServices();
builder.Services.AddMemoryCache();

// Response Compression for High Performance
builder.Services.AddResponseCompression(options =>
{
    options.EnableForHttps = true;
});

// Reverse Proxy & Forwarded Headers Configuration (Eliminates global rate-limiter proxy lockouts)
builder.Services.Configure<ForwardedHeadersOptions>(options =>
{
    options.ForwardedHeaders = ForwardedHeaders.XForwardedFor | ForwardedHeaders.XForwardedProto;
    options.KnownNetworks.Clear();
    options.KnownProxies.Clear();
});

// Resilient Host Options - Prevent transient background service errors from terminating the web host
builder.Services.Configure<HostOptions>(options =>
{
    options.BackgroundServiceExceptionBehavior = BackgroundServiceExceptionBehavior.Ignore;
});

// Password Hasher, HttpClient & HttpContext Accessor
builder.Services.AddScoped<PasswordHasher<ApplicationUser>>();
builder.Services.AddHttpContextAccessor();
builder.Services.AddHttpClient();

// Microsoft Entra ID Resilience & Backup Password Service
builder.Services.AddScoped<IEntraResilienceService, EntraResilienceService>();

// Data Protection with persistent key storage (ensures persistent auth cookies survive server & dev process recycles)
var keysFolder = Path.Combine(builder.Environment.ContentRootPath, "App_Data", "DataProtectionKeys");
Directory.CreateDirectory(keysFolder);
builder.Services.AddDataProtection()
    .PersistKeysToFileSystem(new DirectoryInfo(keysFolder))
    .SetApplicationName("merSETA-NSDMS");

// Authentication & Authorization Services
builder.Services.AddCascadingAuthenticationState();
builder.Services.AddScoped<AuthenticationStateProvider, NsdmsAuthenticationStateProvider>();
builder.Services.AddScoped<NsdmsAuthenticationStateProvider>(sp => (NsdmsAuthenticationStateProvider)sp.GetRequiredService<AuthenticationStateProvider>());
builder.Services.AddAuthentication(options =>
{
    options.DefaultScheme = CookieAuthenticationDefaults.AuthenticationScheme;
    options.DefaultAuthenticateScheme = CookieAuthenticationDefaults.AuthenticationScheme;
    options.DefaultChallengeScheme = CookieAuthenticationDefaults.AuthenticationScheme;
    options.DefaultSignInScheme = CookieAuthenticationDefaults.AuthenticationScheme;
})
.AddCookie(CookieAuthenticationDefaults.AuthenticationScheme, options =>
{
    options.Cookie.Name = "NSDMS_AUTH_TICKET";
    options.Cookie.HttpOnly = true;
    options.Cookie.SameSite = SameSiteMode.Lax;
    options.Cookie.SecurePolicy = builder.Environment.IsDevelopment() ? CookieSecurePolicy.SameAsRequest : CookieSecurePolicy.Always;
    options.LoginPath = "/login";
    options.LogoutPath = "/api/auth/logout";
    options.AccessDeniedPath = "/login";
    options.ExpireTimeSpan = TimeSpan.FromDays(14); // 14-day persistent session window
    options.SlidingExpiration = true;

    // Validate account active status, real-time Entra directory revocation, and offline grace limits on persistent cookies
    options.Events = new CookieAuthenticationEvents
    {
        OnValidatePrincipal = async context =>
        {
            var userIdClaim = context.Principal?.FindFirst(ClaimTypes.NameIdentifier)?.Value;
            if (int.TryParse(userIdClaim, out var userId))
            {
                var identityService = context.HttpContext.RequestServices.GetRequiredService<IIdentityService>();
                var user = await identityService.GetUserByIdAsync(userId);
                if (user == null || !user.IsActive || (user.IsEntraUser && user.EntraAccountEnabled == false))
                {
                    var logger = context.HttpContext.RequestServices.GetService<ILoggerFactory>()?.CreateLogger("AuthCookieValidation");
                    logger?.LogWarning("Security Alert: Session revoked via OnValidatePrincipal for user {UserId} ({Email}). IsActive={IsActive}, EntraAccountEnabled={EntraEnabled}",
                        userId, user?.Email ?? "Unknown", user?.IsActive, user?.EntraAccountEnabled);

                    context.RejectPrincipal();
                    await context.HttpContext.SignOutAsync(CookieAuthenticationDefaults.AuthenticationScheme);
                }
                else if (!string.IsNullOrEmpty(user.SecurityStamp) && context.Principal?.FindFirst("SecurityStamp")?.Value != user.SecurityStamp)
                {
                    var logger = context.HttpContext.RequestServices.GetService<ILoggerFactory>()?.CreateLogger("AuthCookieValidation");
                    logger?.LogWarning("Security Alert: SecurityStamp mismatch for user {UserId} ({Email}). Revoking invalidated session.",
                        userId, user.Email);

                    context.RejectPrincipal();
                    await context.HttpContext.SignOutAsync(CookieAuthenticationDefaults.AuthenticationScheme);
                }
                else if (user.IsEntraUser && context.Principal?.FindFirst("AuthMethod")?.Value == "EmergencyBackupPassword" && user.LastEntraSyncUtc.HasValue)
                {
                    var config = context.HttpContext.RequestServices.GetService<ISystemConfigurationService>();
                    var gracePeriodDays = config != null ? await config.GetValueAsync<int>("Auth:EntraOfflineGracePeriodDays", 14) : 14;
                    if ((DateTime.UtcNow - user.LastEntraSyncUtc.Value) > TimeSpan.FromDays(gracePeriodDays))
                    {
                        var logger = context.HttpContext.RequestServices.GetService<ILoggerFactory>()?.CreateLogger("AuthCookieValidation");
                        logger?.LogWarning("Security Alert: Emergency offline grace period ({Days}d) exceeded for user {UserId} ({Email}). Revoking session.",
                            gracePeriodDays, userId, user.Email);

                        context.RejectPrincipal();
                        await context.HttpContext.SignOutAsync(CookieAuthenticationDefaults.AuthenticationScheme);
                    }
                }
            }
        }
    };
});
builder.Services.AddAuthorization();

// Rate Limiting Services
builder.Services.AddRateLimiter(options =>
{
    options.RejectionStatusCode = StatusCodes.Status429TooManyRequests;
    options.AddPolicy("auth-limiter", httpContext =>
        RateLimitPartition.GetFixedWindowLimiter(
            httpContext.Connection.RemoteIpAddress?.ToString() ?? "unknown",
            _ => new FixedWindowRateLimiterOptions
            {
                PermitLimit = 60,
                Window = TimeSpan.FromMinutes(1),
                QueueProcessingOrder = QueueProcessingOrder.OldestFirst,
                QueueLimit = 0
            }));
});

// Add Razor components with Interactive Server mode and tuned circuit resilience
builder.Services.AddRazorComponents()
    .AddInteractiveServerComponents(options =>
    {
        options.DisconnectedCircuitRetentionPeriod = TimeSpan.FromMinutes(15);
        options.DisconnectedCircuitMaxRetained = 2000;
        options.DetailedErrors = builder.Environment.IsDevelopment();
    })
    .AddHubOptions(options =>
    {
        options.MaximumReceiveMessageSize = 10 * 1024 * 1024; // 10 MB limit for spreadsheet imports
    });

// Configure SignalR Hub options globally for all hubs
builder.Services.Configure<Microsoft.AspNetCore.SignalR.HubOptions>(options =>
{
    options.MaximumReceiveMessageSize = 10 * 1024 * 1024; // 10 MB limit
});

// Multi-Tenancy Provider with Blazor Server Interactive Circuit Fallback
builder.Services.AddScoped<ITenantProvider>(sp =>
{
    var httpContext = sp.GetService<IHttpContextAccessor>()?.HttpContext;
    var user = httpContext?.User;

    // In Blazor Server interactive circuits, HttpContext is null; resolve user from AuthenticationStateProvider
    if (user?.Identity?.IsAuthenticated != true)
    {
        var authProvider = sp.GetService<AuthenticationStateProvider>();
        if (authProvider != null)
        {
            var authStateTask = authProvider.GetAuthenticationStateAsync();
            if (authStateTask.IsCompletedSuccessfully)
            {
                user = authStateTask.Result.User;
            }
            else
            {
                user = authStateTask.GetAwaiter().GetResult().User;
            }
        }
    }

    bool isAdmin = user?.Identity?.IsAuthenticated == true &&
                   (user.IsInRole("Admin") || user.IsInRole("SuperAdmin") || user.IsInRole("SUPERADMIN") || user.HasClaim("Permission", "System.Admin"));
    int? orgId = null;
    if (int.TryParse(user?.FindFirst("OrganisationId")?.Value, out int parsedOrgId) && parsedOrgId > 0)
    {
        orgId = parsedOrgId;
    }
    return new DefaultTenantProvider(orgId, isAdmin);
});
builder.Services.AddScoped<DefaultTenantProvider>(sp => (DefaultTenantProvider)sp.GetRequiredService<ITenantProvider>());

builder.Services.AddScoped<NsdmsDbContext>(sp => new NsdmsDbContext(
    sp.GetRequiredService<DbContextOptions<NsdmsDbContext>>(),
    sp.GetRequiredService<ITenantProvider>()));

// ASP.NET Core Identity Core registration with standard policies
builder.Services.AddIdentityCore<ApplicationUser>(options =>
{
    // Password policy
    options.Password.RequireDigit = true;
    options.Password.RequireLowercase = true;
    options.Password.RequireUppercase = true;
    options.Password.RequireNonAlphanumeric = false;
    options.Password.RequiredLength = 8;
    options.Password.RequiredUniqueChars = 1;

    // Lockout policy
    options.Lockout.DefaultLockoutTimeSpan = TimeSpan.FromMinutes(15);
    options.Lockout.MaxFailedAccessAttempts = 5;
    options.Lockout.AllowedForNewUsers = true;

    // User options
    options.User.RequireUniqueEmail = false;
    options.SignIn.RequireConfirmedAccount = false;
})
.AddRoles<ApplicationRole>()
.AddRoleManager<RoleManager<ApplicationRole>>()
.AddUserManager<UserManager<ApplicationUser>>()
.AddSignInManager<SignInManager<ApplicationUser>>()
.AddEntityFrameworkStores<NsdmsDbContext>()
.AddDefaultTokenProviders();

builder.Services.PostConfigure<AuthenticationOptions>(options =>
{
    options.DefaultScheme = CookieAuthenticationDefaults.AuthenticationScheme;
    options.DefaultAuthenticateScheme = CookieAuthenticationDefaults.AuthenticationScheme;
    options.DefaultChallengeScheme = CookieAuthenticationDefaults.AuthenticationScheme;
    options.DefaultSignInScheme = CookieAuthenticationDefaults.AuthenticationScheme;
});

// Real-time SignalR Notification Service & Transport Publisher
builder.Services.AddSingleton<RealtimeNotificationService>();
builder.Services.AddSingleton<IRealtimeNotificationService>(sp => sp.GetRequiredService<RealtimeNotificationService>());
builder.Services.AddSingleton<ISignalRNotificationPublisher>(sp => sp.GetRequiredService<RealtimeNotificationService>());

// Client-Side Draft State Hydration
builder.Services.AddScoped<IFormDraftService, FormDraftService>();

// Modular Clean Architecture Layer Registrations
builder.Services.AddApplicationServices();
builder.Services.AddInfrastructureServices(builder.Configuration);

var app = builder.Build();

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error", createScopeForErrors: true);
    app.UseHsts();
}

app.UseResponseCompression();
app.UseForwardedHeaders();
app.UseHttpsRedirection();

app.Use(async (context, next) =>
{
    context.Response.Headers.Append("X-Frame-Options", "SAMEORIGIN");
    context.Response.Headers.Append("X-Content-Type-Options", "nosniff");
    context.Response.Headers.Append("Referrer-Policy", "strict-origin-when-cross-origin");
    context.Response.Headers.Append("X-XSS-Protection", "1; mode=block");
    context.Response.Headers.Append("Content-Security-Policy",
        "default-src 'self'; " +
        "script-src 'self' 'unsafe-inline' 'unsafe-eval'; " +
        "style-src 'self' 'unsafe-inline' https://fonts.googleapis.com; " +
        "font-src 'self' https://fonts.gstatic.com data:; " +
        "img-src 'self' data: https:; " +
        "connect-src 'self' ws: wss:; " +
        "frame-ancestors 'self'; " +
        "object-src 'none'; " +
        "base-uri 'self';");
    context.Response.Headers.Append("Permissions-Policy", "camera=(), microphone=(), geolocation=(), payment=()");
    await next();
});

app.UseRateLimiter();
app.UseAuthentication();
app.UseAuthorization();
app.UseAntiforgery();

app.MapStaticAssets();
app.UseStaticFiles();

// Map Real-time SignalR Hub
app.MapHub<NsdmsNotificationHub>("/hubs/notifications");

// Map Modular Route Groups (Minimal APIs)
app.MapAuthEndpoints();
app.MapDocumentEndpoints();
app.MapStatutoryEndpoints();
app.MapBackgroundJobEndpoints();
app.MapMetricsEndpoints();
app.MapB2bApiEndpoints();

app.MapRazorComponents<App>()
    .AddInteractiveServerRenderMode();

// Auto-create database & seed lookups, sample data, workflow definitions, financial records, and system governance
using (var scope = app.Services.CreateScope())
{
    var db = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
    try { db.Database.EnsureCreated(); } catch (Exception ex) { app.Logger.LogWarning(ex, "EnsureCreated skipped or already initialized."); }

    // Initialize Schema Migration Journal to skip already executed DDL in sub-millisecond time
    SchemaMigrationJournal.Initialize(db, app.Logger);

    void RunMigrator(string name, Action action)
    {
        if (SchemaMigrationJournal.IsApplied(name))
        {
            return;
        }

        var sw = Stopwatch.StartNew();
        try
        {
            action();
            sw.Stop();
            SchemaMigrationJournal.RecordApplied(db, name, sw.ElapsedMilliseconds, app.Logger);
            app.Logger.LogInformation("Migrator {MigratorName} completed in {ElapsedMs}ms.", name, sw.ElapsedMilliseconds);
        }
        catch (Exception ex)
        {
            sw.Stop();
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
    RunMigrator("Phase21AssessorModeratorLifecycle", () => Phase21AssessorModeratorLifecycleMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
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
    RunMigrator("Phase35AssessmentAndModerationGovernance", () => Phase35AssessmentAndModerationGovernanceMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase36SdpLifecycleAndDisciplinary", () => Phase36SdpLifecycleAndDisciplinaryMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase37QctoAccreditationGovernance", () => Phase37QctoAccreditationGovernanceMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase36PortfolioDispatch", () => Phase11PortfolioDispatchMigrator.MigratePortfolioDispatchSchemaAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase37ZoningAndCaseload", () => Phase12ZoningAndCaseloadMigrator.MigrateZoningAndCaseloadSchemaAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase38EnterpriseSchemaFix", () => Phase38EnterpriseSchemaFixMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase12BusinessRuleEngine", () => Phase12BusinessRuleEngineMigrator.MigrateBusinessRuleSchemaAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase39FiscalCalendar", () => Phase13FiscalCalendarMigrator.MigrateFiscalCalendarSchemaAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase40HolidayAndClosure", () => Phase14HolidayAndClosureMigrator.MigrateHolidayAndClosureSchemaAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase41WspExtensionRequest", () => Phase15WspExtensionRequestMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase42DocumentVerification", () => Phase42DocumentVerificationAndRejectionReasonsMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase43MgWindowMakerChecker", () => Phase43MgWindowMakerCheckerMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase44EnterprisePerformanceAndIndexing", () => Phase44EnterprisePerformanceAndIndexingMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase45AuditLogPartitioning", () => Phase45AuditLogPartitioningMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase46DgFundingWindowGovernance", () => Phase46DgFundingWindowGovernanceMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase47DgWindowConfigurationAndBlueprint", () => Phase47DgWindowConfigurationAndBlueprintMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase48SarsLevySetBasedPromotion", () => Phase48SarsLevySetBasedPromotionMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase49GrantApplicationCompositeStructure", () => Phase49GrantApplicationCompositeStructureMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase50DocumentTemplateVersioningAndHtmlStudio", () => Phase50DocumentTemplateVersioningAndHtmlStudioMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase51OpenKnowledgeFormat", () => Phase51OpenKnowledgeFormatMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase52InterestAndConflictManagement", () => Phase52InterestAndConflictManagementMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase53BroadcastMessagingAndEmailThrottling", () => Phase53BroadcastMessagingAndEmailThrottlingMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase54HoldingHierarchy", () => Phase54HoldingHierarchyMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase55WspBulkIngestion", () => Phase55WspBulkIngestionMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase56DatabaseEngineOptimization", () => Phase56DatabaseEngineOptimizationMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase53OrganisationEmployeeRosterMigrator", () => new Phase53OrganisationEmployeeRosterMigrator(app.Services).MigrateAsync().GetAwaiter().GetResult());
    RunMigrator("Phase52EnterpriseDataTierRemediation", () => Phase52EnterpriseDataTierRemediationMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase57TransactionalOutbox", () => Phase57TransactionalOutboxMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase58EntraResilience", () => Phase58EntraResilienceSchemaMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase59B2bApiArchitecture", () => Phase59B2bApiArchitectureMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("Phase56MgWindowOfoSet", () => Phase56MgWindowOfoSetMigrator.MigrateAsync(app.Services).GetAwaiter().GetResult());
    RunMigrator("SampleData", () => SampleDataSeeder.SeedSampleDataAsync(db).GetAwaiter().GetResult());
    RunMigrator("FeatureFlags", () => scope.ServiceProvider.GetRequiredService<IFeatureFlagService>().SeedDefaultFeatureFlagsAsync().GetAwaiter().GetResult());
    RunMigrator("SystemConfigs", () => scope.ServiceProvider.GetRequiredService<ISystemConfigurationService>().SeedDefaultConfigsAsync().GetAwaiter().GetResult());
    RunMigrator("RolePermissions", () => scope.ServiceProvider.GetRequiredService<IRolePermissionService>().SeedDefaultRolePermissionsAsync().GetAwaiter().GetResult());
    RunMigrator("DefaultUsers", () => scope.ServiceProvider.GetRequiredService<IIdentityService>().SeedDefaultUsersAsync().GetAwaiter().GetResult());
    app.Logger.LogInformation("SQL Server database verified with all entities, SETMIS lookups, sample data, workflow engine, financial governance, system configuration & security roles.");
}

app.Run();

public record AsyncDocumentRequest(string DocumentType, int RecordId, string? FileName = null);
