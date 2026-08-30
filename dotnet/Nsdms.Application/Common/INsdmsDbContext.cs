using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;
using Nsdms.Domain.Entities;
using Nsdms.Domain.Lookups;

namespace Nsdms.Application.Common;

public interface INsdmsDbContext : IDisposable, IAsyncDisposable
{
    IModel Model { get; }
    DbSet<Organisation> Organisations { get; }
    DbSet<Person> People { get; }
    DbSet<OrganisationContact> OrganisationContacts { get; }
    DbSet<OrganisationSite> OrganisationSites { get; }
    DbSet<ApplicationUser> Users { get; }
    DbSet<ApplicationRole> Roles { get; }
    DbSet<Microsoft.AspNetCore.Identity.IdentityUserRole<int>> UserRoles { get; }
    DbSet<Visit> Visits { get; }
    DbSet<WspSubmission> WspSubmissions { get; }
    DbSet<LevyFile> LevyFiles { get; }
    DbSet<LevyFileLine> LevyFileLines { get; }
    DbSet<GrantApplication> GrantApplications { get; }
    DbSet<EtqaAssessor> EtqaAssessors { get; }
    DbSet<TrainingProvider> TrainingProviders { get; }
    DbSet<TrainingProviderQualification> TrainingProviderQualifications { get; }
    DbSet<TrainingProviderUnitStandard> TrainingProviderUnitStandards { get; }
    DbSet<WspEmploymentSummary> WspEmploymentSummaries { get; }
    DbSet<WspTrainingPlan> WspTrainingPlans { get; }
    DbSet<GrantFundingWindow> GrantFundingWindows { get; }
    DbSet<GrantProjectBudget> GrantProjectBudgets { get; }
    DbSet<AssessorModeratorScope> AssessorModeratorScopes { get; }
    DbSet<LearnerAssessment> LearnerAssessments { get; }
    DbSet<WorkplaceApproval> WorkplaceApprovals { get; }
    DbSet<WorkplaceApprovalMentor> WorkplaceApprovalMentors { get; }
    DbSet<WorkplaceApprovalToolList> WorkplaceApprovalToolLists { get; }
    DbSet<CompanyLearner> CompanyLearners { get; }
    DbSet<LearnerTradeTest> LearnerTradeTests { get; }
    DbSet<AuditLog> AuditLogs { get; }

    // Workflow Engine & Task Matrix
    DbSet<WorkflowDefinition> WorkflowDefinitions { get; }
    DbSet<WorkflowState> WorkflowStates { get; }
    DbSet<WorkflowTransition> WorkflowTransitions { get; }
    DbSet<WorkflowInstance> WorkflowInstances { get; }
    DbSet<WorkflowTask> WorkflowTasks { get; }
    DbSet<WorkflowHistory> WorkflowHistories { get; }
    DbSet<WorkflowNotification> WorkflowNotifications { get; }

    // Document Management
    DbSet<DocumentMetadata> DocumentMetadatas { get; }
    DbSet<DocumentRequirementRule> DocumentRequirementRules { get; }

    // Financial Governance, Grant MOAs & Disbursements (Phase 4)
    DbSet<GrantMoa> GrantMoas { get; }
    DbSet<GrantMoaMilestone> GrantMoaMilestones { get; }
    DbSet<GrantTranchePayment> GrantTranchePayments { get; }
    DbSet<MandatoryGrantDisbursement> MandatoryGrantDisbursements { get; }
    DbSet<InterSetaTransfer> InterSetaTransfers { get; }

    // SETMIS & DHET Compliance Batches (Phase 5)
    DbSet<SetmisSubmissionBatch> SetmisSubmissionBatches { get; }

    // Lookups in `lookup` schema
    DbSet<GenderType> GenderTypes { get; }
    DbSet<EquityType> EquityTypes { get; }
    DbSet<CitizenStatusType> CitizenStatusTypes { get; }
    DbSet<NationalityType> NationalityTypes { get; }
    DbSet<HomeLanguageType> HomeLanguageTypes { get; }
    DbSet<ProvinceType> ProvinceTypes { get; }
    DbSet<DisabilityType> DisabilityTypes { get; }
    DbSet<CategoryType> CategoryTypes { get; }
    DbSet<OrganisationType> OrganisationTypes { get; }
    DbSet<CompanySizeType> CompanySizeTypes { get; }
    DbSet<SectorType> SectorTypes { get; }
    DbSet<ChamberType> ChamberTypes { get; }
    DbSet<SicCodeType> SicCodeTypes { get; }
    DbSet<StatusType> StatusTypes { get; }
    DbSet<LearningProgrammeType> LearningProgrammeTypes { get; }
    DbSet<EnrolmentType> EnrolmentTypes { get; }
    DbSet<EnrolmentStatusType> EnrolmentStatusTypes { get; }
    DbSet<ProviderType> ProviderTypes { get; }
    DbSet<ProviderStatusType> ProviderStatusTypes { get; }
    DbSet<LearnerEvidenceType> LearnerEvidenceTypes { get; }
    DbSet<GrantTypeType> GrantTypeTypes { get; }
    DbSet<InterventionType> InterventionTypes { get; }
    DbSet<OfoCodeType> OfoCodeTypes { get; }
    DbSet<VisitTypeType> VisitTypeTypes { get; }
    DbSet<SiteVisitApprovalStatusType> SiteVisitApprovalStatusTypes { get; }
    DbSet<EmployerApprovalStatusType> EmployerApprovalStatusTypes { get; }

    Task<int> SaveChangesAsync(CancellationToken cancellationToken = default);
}
