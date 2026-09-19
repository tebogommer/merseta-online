using Microsoft.Extensions.DependencyInjection;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Application.Validation;

namespace Nsdms.Application;

public static class DependencyInjection
{
    public static IServiceCollection AddApplicationServices(this IServiceCollection services)
    {
        // Core Identity & Security Services
        services.AddScoped<AuditService>();
        services.AddScoped<IAuditService>(sp => sp.GetRequiredService<AuditService>());
        services.AddScoped<PersonService>();
        services.AddScoped<IPersonService>(sp => sp.GetRequiredService<PersonService>());
        services.AddScoped<OrganisationService>();
        services.AddScoped<IOrganisationService>(sp => sp.GetRequiredService<OrganisationService>());
        services.AddScoped<OrganisationComplianceEngine>();
        services.AddScoped<IOrganisationComplianceEngine>(sp => sp.GetRequiredService<OrganisationComplianceEngine>());
        services.AddScoped<OrganisationHierarchyService>();
        services.AddScoped<IOrganisationHierarchyService>(sp => sp.GetRequiredService<OrganisationHierarchyService>());
        services.AddScoped<OrganisationContextService>();
        services.AddScoped<IOrganisationContextService>(sp => sp.GetRequiredService<OrganisationContextService>());
        services.AddScoped<IdentityService>();
        services.AddScoped<IIdentityService>(sp => sp.GetRequiredService<IdentityService>());
        services.AddScoped<RolePermissionService>();
        services.AddScoped<IRolePermissionService>(sp => sp.GetRequiredService<RolePermissionService>());
        services.AddScoped<CaslAbilityService>();
        services.AddScoped<ICaslAbilityService>(sp => sp.GetRequiredService<CaslAbilityService>());
        services.AddScoped<VisitService>();
        services.AddScoped<IVisitService>(sp => sp.GetRequiredService<VisitService>());
        services.AddScoped<LookupService>();
        services.AddScoped<ILookupService>(sp => sp.GetRequiredService<LookupService>());

        // Skills Development Providers & Delivery Sites
        services.AddScoped<TrainingProviderService>();
        services.AddScoped<ITrainingProviderService>(sp => sp.GetRequiredService<TrainingProviderService>());
        services.AddScoped<SdpSiteService>();
        services.AddScoped<ISdpSiteService>(sp => sp.GetRequiredService<SdpSiteService>());
        services.AddScoped<SdpCampusService>();
        services.AddScoped<ISdpCampusService>(sp => sp.GetRequiredService<SdpSiteService>());
        services.AddScoped<SdpDisciplinaryService>();
        services.AddScoped<ISdpDisciplinaryService>(sp => sp.GetRequiredService<SdpDisciplinaryService>());

        // Mandatory Grants (WSP/ATR) & Discretionary Grants
        services.AddScoped<WspService>();
        services.AddScoped<IWspService>(sp => sp.GetRequiredService<WspService>());
        services.AddScoped<IMgWindowGovernanceService, MgWindowGovernanceService>();
        services.AddScoped<GrantService>();
        services.AddScoped<IGrantService>(sp => sp.GetRequiredService<GrantService>());
        services.AddScoped<LevyService>();
        services.AddScoped<ILevyService>(sp => sp.GetRequiredService<LevyService>());
        services.AddScoped<IWspSignoffService, WspSignoffService>();
        services.AddScoped<IDiscretionaryGrantClaimService, DiscretionaryGrantClaimService>();
        services.AddScoped<IWspSurveyService, WspSurveyService>();

        // ETQA, Qualifications & Workplace Approvals
        services.AddScoped<EtqaService>();
        services.AddScoped<IEtqaService>(sp => sp.GetRequiredService<EtqaService>());
        services.AddScoped<IMentorRatioPolicyEngine, MentorRatioPolicyEngine>();
        services.AddScoped<WorkplaceApprovalService>();
        services.AddScoped<IWorkplaceApprovalService>(sp => sp.GetRequiredService<WorkplaceApprovalService>());
        services.AddScoped<LearnerService>();
        services.AddScoped<ILearnerService>(sp => sp.GetRequiredService<LearnerService>());
        services.AddScoped<IBusinessRuleEngineService, BusinessRuleEngineService>();
        services.AddScoped<ILearnerStpRiskEngine, LearnerStpRiskEngine>();
        services.AddScoped<ILearnerBulkIngestionService, LearnerBulkIngestionService>();
        services.AddScoped<ILearnerLifecycleService, LearnerLifecycleService>();
        services.AddScoped<IWorkplaceMonitoringService, WorkplaceMonitoringService>();
        services.AddScoped<IReviewCommitteeService, ReviewCommitteeService>();
        services.AddScoped<IDgProjectImplementationService, DgProjectImplementationService>();
        services.AddScoped<ITrainingCommitteeAndDisputeService, TrainingCommitteeAndDisputeService>();
        services.AddScoped<ITradeTestAndArplService, TradeTestAndArplService>();
        services.AddScoped<INambBatchService, NambBatchService>();
        services.AddScoped<ISummativeAssessmentAndModerationService, SummativeAssessmentAndModerationService>();
        services.AddScoped<IQcdAndCurriculumService, QcdAndCurriculumService>();
        services.AddScoped<INonSetaVerificationService, NonSetaVerificationService>();
        services.AddScoped<IQualificationEnrolmentGatekeeperService, QualificationEnrolmentGatekeeperService>();
        services.AddScoped<IAqpPartnerService, AqpPartnerService>();
        services.AddScoped<IAssessorRegistrationService, AssessorRegistrationService>();
        services.AddScoped<IAssessorReRegistrationService, AssessorReRegistrationService>();
        services.AddScoped<IAssessorDisciplinaryService, AssessorDisciplinaryService>();

        // Workflow Engine & Governance
        services.AddScoped<IWorkflowEngineService, WorkflowEngineService>();
        services.AddScoped<IWorkflowGovernanceService, WorkflowGovernanceService>();
        services.AddScoped<IStorageService, StorageService>();

        // Financial Governance & Fiscal Calendar
        services.AddScoped<IFinanceService, FinanceService>();
        services.AddScoped<IFiscalCalendarService, FiscalCalendarService>();
        services.AddScoped<IWorkingDayCalculationEngine, WorkingDayCalculationEngine>();
        services.AddScoped<IHolidayClosureService, HolidayClosureService>();
        services.AddScoped<IBankingDetailsService, BankingDetailsService>();
        services.AddScoped<ISdfAppointmentService, SdfAppointmentService>();
        services.AddScoped<IContractVariationService, ContractVariationService>();
        services.AddScoped<IExtensionOfScopeService, ExtensionOfScopeService>();

        // Business Intelligence & Administration Catalog
        services.AddScoped<IAnalyticsService, AnalyticsService>();
        services.AddScoped<IDatabaseDocumentationService, DatabaseDocumentationService>();
        services.AddScoped<ISystemConfigurationService, SystemConfigurationService>();
        services.AddScoped<IFeatureFlagService, FeatureFlagService>();
        services.AddScoped<IAdminCatalogService, AdminCatalogService>();
        services.AddScoped<IGridViewPreferenceService, GridViewPreferenceService>();
        services.AddScoped<INavigationMenuService, NavigationMenuService>();

        // Open Knowledge Format & Conflict Management
        services.AddSingleton<IOkfFrontmatterParser, OkfFrontmatterParser>();
        services.AddScoped<IKnowledgeCatalogService, KnowledgeCatalogService>();
        services.AddScoped<IConflictManagementService, ConflictManagementService>();

        // Document Verification & Templates
        services.AddScoped<IDocumentVerificationService, DocumentVerificationService>();
        services.AddScoped<IDocumentPlaceholderRegistry, DocumentPlaceholderRegistry>();
        services.AddScoped<IEnterpriseDocumentTemplateService, EnterpriseDocumentTemplateService>();
        services.AddScoped<IMoaTemplateEngineService, MoaTemplateEngineService>();

        // Statutory Extracts & Dispatch
        services.AddScoped<IStatutoryValidationService, StatutoryValidationService>();
        services.AddScoped<ISetmisExtractService, SetmisExtractService>();
        services.AddScoped<INlrdExtractService, NlrdExtractService>();
        services.AddScoped<IStatutorySchedulerService, StatutorySchedulerService>();
        services.AddScoped<IPortfolioDispatchService, PortfolioDispatchService>();
        services.AddScoped<IZoneAndCaseloadService, ZoneAndCaseloadService>();

        return services;
    }
}
