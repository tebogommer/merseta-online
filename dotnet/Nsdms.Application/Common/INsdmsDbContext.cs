using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Metadata;
using Nsdms.Domain.Entities;
using Nsdms.Domain.Lookups;

namespace Nsdms.Application.Common;

public interface INsdmsDbContext : IDisposable, IAsyncDisposable
{
    IModel Model { get; }
    DatabaseFacade Database { get; }
    DbSet<Organisation> Organisations { get; }
    DbSet<Person> People { get; }
    DbSet<OrganisationContact> OrganisationContacts { get; }
    DbSet<OrganisationSite> OrganisationSites { get; }
    DbSet<ApplicationUser> Users { get; }
    DbSet<ApplicationRole> Roles { get; }
    DbSet<Microsoft.AspNetCore.Identity.IdentityUserRole<int>> UserRoles { get; }
    DbSet<Microsoft.AspNetCore.Identity.IdentityRoleClaim<int>> RoleClaims { get; }
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
    DbSet<WorkflowSignoffAttestation> WorkflowSignoffAttestations { get; }
    DbSet<WorkflowDelegation> WorkflowDelegations { get; }
    DbSet<WorkflowTaskLease> WorkflowTaskLeases { get; }
    DbSet<FinancialApprovalThreshold> FinancialApprovalThresholds { get; }

    // Document Management
    DbSet<DocumentMetadata> DocumentMetadatas { get; }
    DbSet<DocumentRequirementRule> DocumentRequirementRules { get; }

    // Financial Governance, Grant MOAs & Disbursements (Phase 4)
    DbSet<GrantMoa> GrantMoas { get; }
    DbSet<GrantMoaMilestone> GrantMoaMilestones { get; }
    DbSet<GrantTranchePayment> GrantTranchePayments { get; }
    DbSet<MandatoryGrantDisbursement> MandatoryGrantDisbursements { get; }
    DbSet<InterSetaTransfer> InterSetaTransfers { get; }


    // System Configuration & Feature Flags
    DbSet<SystemConfig> SystemConfigs { get; }
    DbSet<SystemFeatureFlag> SystemFeatureFlags { get; }

    // Document & File Management
    DbSet<DocumentAttachment> DocumentAttachments { get; }

    // Learner Lifecycle Transitions
    DbSet<CompanyLearnerTransfer> CompanyLearnerTransfers { get; }
    DbSet<CompanyLearnerLostTime> CompanyLearnerLostTimes { get; }
    DbSet<CompanyLearnerTermination> CompanyLearnerTerminations { get; }

    // Workplace Monitoring & Inspection Surveys (Cluster 1)
    DbSet<WorkplaceMonitoringSiteVisit> WorkplaceMonitoringSiteVisits { get; }
    DbSet<WorkplaceMonitoringComplianceSurvey> WorkplaceMonitoringComplianceSurveys { get; }
    DbSet<WorkplaceMonitoringActionPlan> WorkplaceMonitoringActionPlans { get; }
    DbSet<WorkplaceMonitoringMitigationPlan> WorkplaceMonitoringMitigationPlans { get; }
    DbSet<WorkplaceMonitoringLearnerSurvey> WorkplaceMonitoringLearnerSurveys { get; }

    // Governance, Review Committees & Accreditation Scope (Cluster 2)
    DbSet<ReviewCommitteeMeeting> ReviewCommitteeMeetings { get; }
    DbSet<ReviewCommitteeMeetingAgenda> ReviewCommitteeMeetingAgendas { get; }
    DbSet<ReviewCommitteeMeetingMember> ReviewCommitteeMeetingMembers { get; }
    DbSet<AssessorModeratorApplication> AssessorModeratorApplications { get; }
    DbSet<SdpScopeExtensionApplication> SdpScopeExtensionApplications { get; }

    // DG Project Implementation Plans & Payment Claims (Cluster 3)
    DbSet<ProjectImplementationPlan> ProjectImplementationPlans { get; }
    DbSet<PipLearnerAllocation> PipLearnerAllocations { get; }
    DbSet<GrantPaymentClaim> GrantPaymentClaims { get; }

    // Training Committees & WSP Disputes (Cluster 4)
    DbSet<TrainingCommittee> TrainingCommittees { get; }
    DbSet<TrainingCommitteeMember> TrainingCommitteeMembers { get; }
    DbSet<WspDispute> WspDisputes { get; }
    DbSet<WspSkillsGap> WspSkillsGaps { get; }


    // Trade Test Administration & ARPL (Area 13)
    DbSet<LearnerTradeTestApplication> LearnerTradeTestApplications { get; }
    DbSet<TradeTestTask> TradeTestTasks { get; }
    DbSet<ArplTradeTestInformation> ArplTradeTestInformations { get; }
    DbSet<ArplExperienceDetail> ArplExperienceDetails { get; }
    DbSet<ArplTrainingDetail> ArplTrainingDetails { get; }
    DbSet<NambDecisionHistory> NambDecisionHistories { get; }

    // Summative Assessment Reports & Moderation (Area 14)
    DbSet<SummativeAssessmentReport> SummativeAssessmentReports { get; }
    DbSet<SummativeAssessmentUnitStandard> SummativeAssessmentUnitStandards { get; }
    DbSet<EisaAssessmentEntry> EisaAssessmentEntries { get; }
    DbSet<StatementOfResults> StatementOfResults { get; }

    // Qualifications Curriculum Development & QDF (Area 15)
    DbSet<QualificationsCurriculumDevelopment> QualificationsCurriculumDevelopments { get; }
    DbSet<CurriculumWorkingGroupMember> CurriculumWorkingGroupMembers { get; }
    DbSet<SkillsRegistration> SkillsRegistrations { get; }

    // Non-SETA Qualifications & Provider Verification (Area 16)
    DbSet<NonSetaCompany> NonSetaCompanies { get; }
    DbSet<NonSetaQualificationsCompletion> NonSetaQualificationsCompletions { get; }

    // Advanced SARS Historical Levy Reconciliation (Area 17)
    DbSet<SarsLevyReconAudit> SarsLevyReconAudits { get; }

    // Auxiliary Enterprise Modules (Options A, B, C, D)
    DbSet<BankingDetails> BankingDetails { get; }
    DbSet<BankingDetailsAudit> BankingDetailsAudits { get; }
    DbSet<SdfCompany> SdfCompanies { get; }
    DbSet<SdfAppointmentHistory> SdfAppointmentHistories { get; }
    DbSet<ContractAddenda> ContractAddendas { get; }
    DbSet<ContractExtensionRequest> ContractExtensionRequests { get; }
    DbSet<ContractTerminationRequest> ContractTerminationRequests { get; }
    DbSet<SdpExtensionOfScope> SdpExtensionOfScopes { get; }
    DbSet<SdpReAccreditationApplication> SdpReAccreditationApplications { get; }
    DbSet<AssessorExtensionOfScope> AssessorExtensionOfScopes { get; }

    // Lookups in `lookup` schema
    DbSet<GenderType> GenderTypes { get; }
    DbSet<EquityType> EquityTypes { get; }
    DbSet<CitizenStatusType> CitizenStatusTypes { get; }
    DbSet<CountryType> CountryTypes { get; }
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

    // Expanded SETMIS Lookups
    DbSet<AlternateIdType> AlternateIdTypes { get; }
    DbSet<EconomicStatusType> EconomicStatusTypes { get; }
    DbSet<PopiActStatusType> PopiActStatusTypes { get; }
    DbSet<CommunicatingRatingType> CommunicatingRatingTypes { get; }
    DbSet<HearingRatingType> HearingRatingTypes { get; }
    DbSet<RememberingRatingType> RememberingRatingTypes { get; }
    DbSet<SeeingRatingType> SeeingRatingTypes { get; }
    DbSet<SelfCareRatingType> SelfCareRatingTypes { get; }
    DbSet<WalkingRatingType> WalkingRatingTypes { get; }
    DbSet<DesignationType> DesignationTypes { get; }
    DbSet<DesignationStructureStatusType> DesignationStructureStatusTypes { get; }
    DbSet<EnrolmentStatusReasonType> EnrolmentStatusReasonTypes { get; }
    DbSet<InternshipStatusType> InternshipStatusTypes { get; }
    DbSet<NonNqfInterventionStatusType> NonNqfInterventionStatusTypes { get; }
    DbSet<PartOfType> PartOfTypes { get; }
    DbSet<ProviderClassType> ProviderClassTypes { get; }
    DbSet<SubfieldType> SubfieldTypes { get; }
    DbSet<TradeTestResultType> TradeTestResultTypes { get; }
    DbSet<TradeTestResultReasonType> TradeTestResultReasonTypes { get; }
    DbSet<StatssaAreaCodeType> StatssaAreaCodeTypes { get; }
    DbSet<UrbanRuralType> UrbanRuralTypes { get; }
    DbSet<SetaType> SetaTypes { get; }
    DbSet<FundingType> FundingTypes { get; }

    DbSet<TEntity> Set<TEntity>() where TEntity : class;
    ValueTask<object?> FindAsync(Type entityType, params object?[]? keyValues);
    Microsoft.EntityFrameworkCore.ChangeTracking.EntityEntry Add(object entity);
    Microsoft.EntityFrameworkCore.ChangeTracking.EntityEntry<TEntity> Add<TEntity>(TEntity entity) where TEntity : class;

    Task<int> SaveChangesAsync(CancellationToken cancellationToken = default);
}
