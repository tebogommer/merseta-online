using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Nsdms.Application.Common;
using Nsdms.Domain.Common;
using Nsdms.Domain.Entities;
using Nsdms.Domain.Lookups;
using Nsdms.Infrastructure.Services;

namespace Nsdms.Infrastructure.Data;

public class NsdmsDbContext : IdentityDbContext<ApplicationUser, ApplicationRole, int>, INsdmsDbContext
{
    private readonly ITenantProvider _tenantProvider;

    [ActivatorUtilitiesConstructor]
    public NsdmsDbContext(DbContextOptions<NsdmsDbContext> options) : base(options)
    {
        _tenantProvider = new DefaultTenantProvider(null, isAdmin: true);
    }

    public NsdmsDbContext(DbContextOptions<NsdmsDbContext> options, ITenantProvider? tenantProvider) : base(options)
    {
        _tenantProvider = tenantProvider ?? new DefaultTenantProvider(null, isAdmin: true);
    }

    public DbSet<Organisation> Organisations => Set<Organisation>();
    public DbSet<Person> People => Set<Person>();
    public DbSet<OrganisationContact> OrganisationContacts => Set<OrganisationContact>();
    public DbSet<OrganisationSite> OrganisationSites => Set<OrganisationSite>();
    public DbSet<Visit> Visits => Set<Visit>();
    public DbSet<WspSubmission> WspSubmissions => Set<WspSubmission>();
    public DbSet<LevyFile> LevyFiles => Set<LevyFile>();
    public DbSet<LevyFileLine> LevyFileLines => Set<LevyFileLine>();
    public DbSet<SarsLevyStaging> SarsLevyStagings => Set<SarsLevyStaging>();
    public DbSet<GrantApplication> GrantApplications => Set<GrantApplication>();
    public DbSet<EtqaAssessor> EtqaAssessors => Set<EtqaAssessor>();
    public DbSet<TrainingProvider> TrainingProviders => Set<TrainingProvider>();
    public DbSet<TrainingProviderQualification> TrainingProviderQualifications => Set<TrainingProviderQualification>();
    public DbSet<TrainingProviderUnitStandard> TrainingProviderUnitStandards => Set<TrainingProviderUnitStandard>();
    public DbSet<WspEmploymentSummary> WspEmploymentSummaries => Set<WspEmploymentSummary>();
    public DbSet<WspTrainingPlan> WspTrainingPlans => Set<WspTrainingPlan>();
    public DbSet<GrantFundingWindow> GrantFundingWindows => Set<GrantFundingWindow>();
    public DbSet<GrantProjectBudget> GrantProjectBudgets => Set<GrantProjectBudget>();
    public DbSet<StrategicPriority> StrategicPriorities => Set<StrategicPriority>();
    public DbSet<FundingWindowPriority> FundingWindowPriorities => Set<FundingWindowPriority>();
    public DbSet<AssessorModeratorScope> AssessorModeratorScopes => Set<AssessorModeratorScope>();
    public DbSet<LearnerAssessment> LearnerAssessments => Set<LearnerAssessment>();
    public DbSet<WorkplaceApproval> WorkplaceApprovals => Set<WorkplaceApproval>();
    public DbSet<WorkplaceApprovalMentor> WorkplaceApprovalMentors => Set<WorkplaceApprovalMentor>();
    public DbSet<WorkplaceApprovalToolList> WorkplaceApprovalToolLists => Set<WorkplaceApprovalToolList>();
    public DbSet<TradeMentorRatioPolicy> TradeMentorRatioPolicies => Set<TradeMentorRatioPolicy>();
    public DbSet<CompanyLearner> CompanyLearners => Set<CompanyLearner>();
    public DbSet<LearnerTradeTest> LearnerTradeTests => Set<LearnerTradeTest>();
    public DbSet<AuditLog> AuditLogs => Set<AuditLog>();

    // Workflow Engine & Task Matrix
    public DbSet<WorkflowDefinition> WorkflowDefinitions => Set<WorkflowDefinition>();
    public DbSet<WorkflowState> WorkflowStates => Set<WorkflowState>();
    public DbSet<WorkflowTransition> WorkflowTransitions => Set<WorkflowTransition>();
    public DbSet<WorkflowInstance> WorkflowInstances => Set<WorkflowInstance>();
    public DbSet<WorkflowTask> WorkflowTasks => Set<WorkflowTask>();
    public DbSet<WorkflowHistory> WorkflowHistories => Set<WorkflowHistory>();
    public DbSet<WorkflowNotification> WorkflowNotifications => Set<WorkflowNotification>();
    public DbSet<WorkflowSignoffAttestation> WorkflowSignoffAttestations => Set<WorkflowSignoffAttestation>();
    public DbSet<WorkflowDelegation> WorkflowDelegations => Set<WorkflowDelegation>();
    public DbSet<WorkflowTaskLease> WorkflowTaskLeases => Set<WorkflowTaskLease>();
    public DbSet<FinancialApprovalThreshold> FinancialApprovalThresholds => Set<FinancialApprovalThreshold>();

    // Document Management
    public DbSet<DocumentMetadata> DocumentMetadatas => Set<DocumentMetadata>();
    public DbSet<DocumentRequirementRule> DocumentRequirementRules => Set<DocumentRequirementRule>();

    // Financial Governance, Grant MOAs & Disbursements (Phase 4)
    public DbSet<GrantMoa> GrantMoas => Set<GrantMoa>();
    public DbSet<GrantMoaMilestone> GrantMoaMilestones => Set<GrantMoaMilestone>();
    public DbSet<GrantTranchePayment> GrantTranchePayments => Set<GrantTranchePayment>();
    public DbSet<MandatoryGrantDisbursement> MandatoryGrantDisbursements => Set<MandatoryGrantDisbursement>();
    public DbSet<InterSetaTransfer> InterSetaTransfers => Set<InterSetaTransfer>();

    // MoA Template & Reusable Clause Engine (Option A)
    public DbSet<MoaTemplate> MoaTemplates => Set<MoaTemplate>();
    public DbSet<MoaClause> MoaClauses => Set<MoaClause>();
    public DbSet<MoaTemplateSection> MoaTemplateSections => Set<MoaTemplateSection>();
    public DbSet<MoaExecutionSnapshot> MoaExecutionSnapshots => Set<MoaExecutionSnapshot>();

    // Universal Document & Verification Engine (Strategic Action Items)
    public DbSet<DocumentTemplate> DocumentTemplates => Set<DocumentTemplate>();
    public DbSet<DocumentClause> DocumentClauses => Set<DocumentClause>();
    public DbSet<DocumentTemplateSection> DocumentTemplateSections => Set<DocumentTemplateSection>();
    public DbSet<DocumentSnapshot> DocumentSnapshots => Set<DocumentSnapshot>();


    // System Configuration & Feature Flags
    public DbSet<SystemConfig> SystemConfigs => Set<SystemConfig>();
    public DbSet<SystemFeatureFlag> SystemFeatureFlags => Set<SystemFeatureFlag>();
    public DbSet<SystemNotification> SystemNotifications => Set<SystemNotification>();

    // Document & File Management
    public DbSet<DocumentAttachment> DocumentAttachments => Set<DocumentAttachment>();

    // Learner Lifecycle Transitions
    public DbSet<CompanyLearnerTransfer> CompanyLearnerTransfers => Set<CompanyLearnerTransfer>();
    public DbSet<CompanyLearnerLostTime> CompanyLearnerLostTimes => Set<CompanyLearnerLostTime>();
    public DbSet<CompanyLearnerTermination> CompanyLearnerTerminations => Set<CompanyLearnerTermination>();

    // Workplace Monitoring & Inspection Surveys (Cluster 1)
    public DbSet<WorkplaceMonitoringSiteVisit> WorkplaceMonitoringSiteVisits => Set<WorkplaceMonitoringSiteVisit>();
    public DbSet<WorkplaceMonitoringComplianceSurvey> WorkplaceMonitoringComplianceSurveys => Set<WorkplaceMonitoringComplianceSurvey>();
    public DbSet<WorkplaceMonitoringActionPlan> WorkplaceMonitoringActionPlans => Set<WorkplaceMonitoringActionPlan>();
    public DbSet<WorkplaceMonitoringMitigationPlan> WorkplaceMonitoringMitigationPlans => Set<WorkplaceMonitoringMitigationPlan>();
    public DbSet<WorkplaceMonitoringLearnerSurvey> WorkplaceMonitoringLearnerSurveys => Set<WorkplaceMonitoringLearnerSurvey>();

    // Governance, Review Committees & Accreditation Scope (Cluster 2)
    public DbSet<ReviewCommitteeMeeting> ReviewCommitteeMeetings => Set<ReviewCommitteeMeeting>();
    public DbSet<ReviewCommitteeMeetingAgenda> ReviewCommitteeMeetingAgendas => Set<ReviewCommitteeMeetingAgenda>();
    public DbSet<ReviewCommitteeMeetingMember> ReviewCommitteeMeetingMembers => Set<ReviewCommitteeMeetingMember>();
    public DbSet<AssessorModeratorApplication> AssessorModeratorApplications => Set<AssessorModeratorApplication>();
    public DbSet<SdpScopeExtensionApplication> SdpScopeExtensionApplications => Set<SdpScopeExtensionApplication>();

    // DG Project Implementation Plans & Payment Claims (Cluster 3)
    public DbSet<ProjectImplementationPlan> ProjectImplementationPlans => Set<ProjectImplementationPlan>();
    public DbSet<PipLearnerAllocation> PipLearnerAllocations => Set<PipLearnerAllocation>();
    public DbSet<GrantPaymentClaim> GrantPaymentClaims => Set<GrantPaymentClaim>();

    // Training Committees & WSP Disputes (Cluster 4)
    public DbSet<TrainingCommittee> TrainingCommittees => Set<TrainingCommittee>();
    public DbSet<TrainingCommitteeMember> TrainingCommitteeMembers => Set<TrainingCommitteeMember>();
    public DbSet<WspDispute> WspDisputes => Set<WspDispute>();
    public DbSet<WspSkillsGap> WspSkillsGaps => Set<WspSkillsGap>();
    public DbSet<WspStrategicSkillsGap> WspStrategicSkillsGaps => Set<WspStrategicSkillsGap>();
    public DbSet<WspTrainingImpactSurvey> WspTrainingImpactSurveys => Set<WspTrainingImpactSurvey>();
    public DbSet<WspStrategicPriority> WspStrategicPriorities => Set<WspStrategicPriority>();

    // AQP Assessment Quality Partner Management
    public DbSet<AqpPartner> AqpPartners => Set<AqpPartner>();
    public DbSet<AqpQualificationScope> AqpQualificationScopes => Set<AqpQualificationScope>();
    public DbSet<AqpLearnerAssessment> AqpLearnerAssessments => Set<AqpLearnerAssessment>();


    // Trade Test Administration & ARPL (Area 13)
    public DbSet<LearnerTradeTestApplication> LearnerTradeTestApplications => Set<LearnerTradeTestApplication>();
    public DbSet<TradeTestTask> TradeTestTasks => Set<TradeTestTask>();
    public DbSet<ArplTradeTestInformation> ArplTradeTestInformations => Set<ArplTradeTestInformation>();
    public DbSet<ArplExperienceDetail> ArplExperienceDetails => Set<ArplExperienceDetail>();
    public DbSet<ArplTrainingDetail> ArplTrainingDetails => Set<ArplTrainingDetail>();
    public DbSet<NambDecisionHistory> NambDecisionHistories => Set<NambDecisionHistory>();

    // Summative Assessment Reports & Moderation (Area 14)
    public DbSet<SummativeAssessmentReport> SummativeAssessmentReports => Set<SummativeAssessmentReport>();
    public DbSet<SummativeAssessmentUnitStandard> SummativeAssessmentUnitStandards => Set<SummativeAssessmentUnitStandard>();
    public DbSet<EisaAssessmentEntry> EisaAssessmentEntries => Set<EisaAssessmentEntry>();
    public DbSet<StatementOfResults> StatementOfResults => Set<StatementOfResults>();

    // Qualifications Curriculum Development & QDF (Area 15)
    public DbSet<QualificationsCurriculumDevelopment> QualificationsCurriculumDevelopments => Set<QualificationsCurriculumDevelopment>();
    public DbSet<CurriculumWorkingGroupMember> CurriculumWorkingGroupMembers => Set<CurriculumWorkingGroupMember>();
    public DbSet<SkillsRegistration> SkillsRegistrations => Set<SkillsRegistration>();

    // Non-SETA Qualifications & Provider Verification (Area 16)
    public DbSet<NonSetaCompany> NonSetaCompanies => Set<NonSetaCompany>();
    public DbSet<NonSetaQualificationsCompletion> NonSetaQualificationsCompletions => Set<NonSetaQualificationsCompletion>();

    // Advanced SARS Historical Levy Reconciliation (Area 17)
    public DbSet<SarsLevyReconAudit> SarsLevyReconAudits => Set<SarsLevyReconAudit>();
    public DbSet<SarsSchemeYearCalculation> SarsSchemeYearCalculations => Set<SarsSchemeYearCalculation>();

    // Auxiliary Enterprise Modules (Options A, B, C, D)
    public DbSet<BankingDetails> BankingDetails => Set<BankingDetails>();
    public DbSet<BankingDetailsAudit> BankingDetailsAudits => Set<BankingDetailsAudit>();
    public DbSet<SdfCompany> SdfCompanies => Set<SdfCompany>();
    public DbSet<SdfAppointmentHistory> SdfAppointmentHistories => Set<SdfAppointmentHistory>();
    public DbSet<ContractAddenda> ContractAddendas => Set<ContractAddenda>();
    public DbSet<ContractExtensionRequest> ContractExtensionRequests => Set<ContractExtensionRequest>();
    public DbSet<ContractTerminationRequest> ContractTerminationRequests => Set<ContractTerminationRequest>();
    public DbSet<SdpExtensionOfScope> SdpExtensionOfScopes => Set<SdpExtensionOfScope>();
    public DbSet<SdpReAccreditationApplication> SdpReAccreditationApplications => Set<SdpReAccreditationApplication>();
    public DbSet<AssessorExtensionOfScope> AssessorExtensionOfScopes => Set<AssessorExtensionOfScope>();
    public DbSet<StatutorySubmissionBatch> StatutorySubmissionBatches => Set<StatutorySubmissionBatch>();
    public DbSet<StatutoryBatchFile> StatutoryBatchFiles => Set<StatutoryBatchFile>();

    // Phase 3: Core Statutory Workflows
    public DbSet<WspSignoffAttestation> WspSignoffAttestations => Set<WspSignoffAttestation>();
    public DbSet<CompanyLearnerChangeRequest> CompanyLearnerChangeRequests => Set<CompanyLearnerChangeRequest>();
    public DbSet<ErpPaymentBatchHeader> ErpPaymentBatchHeaders => Set<ErpPaymentBatchHeader>();
    public DbSet<ErpPaymentBatchEntry> ErpPaymentBatchEntries => Set<ErpPaymentBatchEntry>();

    // Phase 4: ETQA Assessor 3-Year Re-registration & CPD
    public DbSet<AssessorReRegistrationApplication> AssessorReRegistrationApplications => Set<AssessorReRegistrationApplication>();
    public DbSet<AssessorCpdActivity> AssessorCpdActivities => Set<AssessorCpdActivity>();

    // Phase 5: Artisan Development & NAMB Batch Governance
    public DbSet<NambSubmissionBatch> NambSubmissionBatches => Set<NambSubmissionBatch>();

    // Phase 7: SDP Campus Infrastructure & Assessor Linking
    public DbSet<TrainingProviderCampus> TrainingProviderCampuses => Set<TrainingProviderCampus>();
    public DbSet<TrainingProviderAssessorLink> TrainingProviderAssessorLinks => Set<TrainingProviderAssessorLink>();

    // Lookups in `lookup` schema
    public DbSet<GenderType> GenderTypes => Set<GenderType>();
    public DbSet<EquityType> EquityTypes => Set<EquityType>();
    public DbSet<CitizenStatusType> CitizenStatusTypes => Set<CitizenStatusType>();
    public DbSet<CountryType> CountryTypes => Set<CountryType>();
    public DbSet<NationalityType> NationalityTypes => Set<NationalityType>();
    public DbSet<HomeLanguageType> HomeLanguageTypes => Set<HomeLanguageType>();
    public DbSet<ProvinceType> ProvinceTypes => Set<ProvinceType>();
    public DbSet<DisabilityType> DisabilityTypes => Set<DisabilityType>();
    public DbSet<CategoryType> CategoryTypes => Set<CategoryType>();
    public DbSet<OrganisationType> OrganisationTypes => Set<OrganisationType>();
    public DbSet<CompanySizeType> CompanySizeTypes => Set<CompanySizeType>();
    public DbSet<SectorType> SectorTypes => Set<SectorType>();
    public DbSet<ChamberType> ChamberTypes => Set<ChamberType>();
    public DbSet<SicCodeType> SicCodeTypes => Set<SicCodeType>();
    public DbSet<StatusType> StatusTypes => Set<StatusType>();
    public DbSet<LearningProgrammeType> LearningProgrammeTypes => Set<LearningProgrammeType>();
    public DbSet<EnrolmentType> EnrolmentTypes => Set<EnrolmentType>();
    public DbSet<EnrolmentStatusType> EnrolmentStatusTypes => Set<EnrolmentStatusType>();
    public DbSet<ProviderType> ProviderTypes => Set<ProviderType>();
    public DbSet<ProviderStatusType> ProviderStatusTypes => Set<ProviderStatusType>();
    public DbSet<LearnerEvidenceType> LearnerEvidenceTypes => Set<LearnerEvidenceType>();
    public DbSet<GrantTypeType> GrantTypeTypes => Set<GrantTypeType>();
    public DbSet<InterventionType> InterventionTypes => Set<InterventionType>();
    public DbSet<OfoCodeType> OfoCodeTypes => Set<OfoCodeType>();
    public DbSet<VisitTypeType> VisitTypeTypes => Set<VisitTypeType>();
    public DbSet<SiteVisitApprovalStatusType> SiteVisitApprovalStatusTypes => Set<SiteVisitApprovalStatusType>();
    public DbSet<EmployerApprovalStatusType> EmployerApprovalStatusTypes => Set<EmployerApprovalStatusType>();

    // Expanded SETMIS Lookups
    public DbSet<AlternateIdType> AlternateIdTypes => Set<AlternateIdType>();
    public DbSet<EconomicStatusType> EconomicStatusTypes => Set<EconomicStatusType>();
    public DbSet<PopiActStatusType> PopiActStatusTypes => Set<PopiActStatusType>();
    public DbSet<CommunicatingRatingType> CommunicatingRatingTypes => Set<CommunicatingRatingType>();
    public DbSet<HearingRatingType> HearingRatingTypes => Set<HearingRatingType>();
    public DbSet<RememberingRatingType> RememberingRatingTypes => Set<RememberingRatingType>();
    public DbSet<SeeingRatingType> SeeingRatingTypes => Set<SeeingRatingType>();
    public DbSet<SelfCareRatingType> SelfCareRatingTypes => Set<SelfCareRatingType>();
    public DbSet<WalkingRatingType> WalkingRatingTypes => Set<WalkingRatingType>();
    public DbSet<DesignationType> DesignationTypes => Set<DesignationType>();
    public DbSet<DesignationStructureStatusType> DesignationStructureStatusTypes => Set<DesignationStructureStatusType>();
    public DbSet<EnrolmentStatusReasonType> EnrolmentStatusReasonTypes => Set<EnrolmentStatusReasonType>();
    public DbSet<InternshipStatusType> InternshipStatusTypes => Set<InternshipStatusType>();
    public DbSet<NonNqfInterventionStatusType> NonNqfInterventionStatusTypes => Set<NonNqfInterventionStatusType>();
    public DbSet<PartOfType> PartOfTypes => Set<PartOfType>();
    public DbSet<ProviderClassType> ProviderClassTypes => Set<ProviderClassType>();
    public DbSet<SubfieldType> SubfieldTypes => Set<SubfieldType>();
    public DbSet<TradeTestResultType> TradeTestResultTypes => Set<TradeTestResultType>();
    public DbSet<TradeTestResultReasonType> TradeTestResultReasonTypes => Set<TradeTestResultReasonType>();
    public DbSet<StatssaAreaCodeType> StatssaAreaCodeTypes => Set<StatssaAreaCodeType>();
    public DbSet<UrbanRuralType> UrbanRuralTypes => Set<UrbanRuralType>();
    public DbSet<SetaType> SetaTypes => Set<SetaType>();
    public DbSet<FundingType> FundingTypes => Set<FundingType>();

    // NLRD Lookups
    public DbSet<AbetBandType> AbetBandTypes => Set<AbetBandType>();
    public DbSet<QualificationTypeType> QualificationTypeTypes => Set<QualificationTypeType>();
    public DbSet<HonoursClassType> HonoursClassTypes => Set<HonoursClassType>();

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        base.OnModelCreating(modelBuilder);

        // Identity table mapping
        modelBuilder.Entity<ApplicationUser>(entity =>
        {
            entity.ToTable("AppUser");
            entity.Property(u => u.UserName).HasMaxLength(100).IsRequired();
            entity.Property(u => u.NormalizedUserName).HasMaxLength(100).IsRequired();
            entity.Property(u => u.Email).HasMaxLength(150);
            entity.Property(u => u.NormalizedEmail).HasMaxLength(150);
            entity.Property(u => u.PhoneNumber).HasMaxLength(50);
            entity.Property(u => u.IsActive).HasDefaultValue(true);

            entity.HasOne(u => u.Person)
                  .WithOne()
                  .HasForeignKey<ApplicationUser>(u => u.PersonId)
                  .OnDelete(DeleteBehavior.SetNull);

            entity.HasOne(u => u.DefaultOrganisation)
                  .WithMany()
                  .HasForeignKey(u => u.DefaultOrganisationId)
                  .OnDelete(DeleteBehavior.SetNull);

            entity.HasIndex(u => u.PersonId).IsUnique();
            entity.HasIndex(u => u.DefaultOrganisationId);
        });

        modelBuilder.Entity<ApplicationRole>(entity =>
        {
            entity.ToTable("AppRole");
            entity.Property(r => r.Name).HasMaxLength(100).IsRequired();
            entity.Property(r => r.NormalizedName).HasMaxLength(100).IsRequired();
            entity.Property(r => r.Description).HasMaxLength(250);
            entity.Property(r => r.Active).HasDefaultValue(true);
        });

        modelBuilder.Entity<IdentityUserRole<int>>(entity =>
        {
            entity.ToTable("AppUserRole");
        });

        modelBuilder.Entity<IdentityUserClaim<int>>(entity =>
        {
            entity.ToTable("AppUserClaim");
        });

        modelBuilder.Entity<IdentityUserLogin<int>>(entity =>
        {
            entity.ToTable("AppUserLogin");
        });

        modelBuilder.Entity<IdentityRoleClaim<int>>(entity =>
        {
            entity.ToTable("AppRoleClaim");
        });

        modelBuilder.Entity<IdentityUserToken<int>>(entity =>
        {
            entity.ToTable("AppUserToken");
        });

        // Person table & indexes
        modelBuilder.Entity<Person>(entity =>
        {
            entity.ToTable("Person");
            entity.Property(p => p.FirstName).HasMaxLength(100).IsRequired();
            entity.Property(p => p.LastName).HasMaxLength(100).IsRequired();
            entity.Property(p => p.MiddleName).HasMaxLength(100);
            entity.Property(p => p.Title).HasMaxLength(20);
            entity.Property(p => p.RsaIdNumber).HasMaxLength(20);
            entity.Property(p => p.PassportNumber).HasMaxLength(50);
            entity.Property(p => p.AlternateIdTypeId).HasMaxLength(10);
            entity.Property(p => p.Email).HasMaxLength(150);
            entity.Property(p => p.PhoneNumber).HasMaxLength(30);
            entity.Property(p => p.CellNumber).HasMaxLength(30);
            entity.Property(p => p.FaxNumber).HasMaxLength(30);
            entity.Property(p => p.GenderCode).HasMaxLength(15);
            entity.Property(p => p.EquityCode).HasMaxLength(15);
            entity.Property(p => p.DisabilityCode).HasMaxLength(15);
            entity.Property(p => p.NationalityCode).HasMaxLength(15);
            entity.Property(p => p.HomeLanguageCode).HasMaxLength(15);
            entity.Property(p => p.ProvinceCode).HasMaxLength(15);
            entity.Property(p => p.PhysicalAddress).HasMaxLength(500);
            entity.Property(p => p.PostalAddress).HasMaxLength(500);
            entity.Property(p => p.SeeingRatingId).HasMaxLength(10);
            entity.Property(p => p.HearingRatingId).HasMaxLength(10);
            entity.Property(p => p.WalkingRatingId).HasMaxLength(10);
            entity.Property(p => p.RememberingRatingId).HasMaxLength(10);
            entity.Property(p => p.CommunicatingRatingId).HasMaxLength(10);
            entity.Property(p => p.SelfCareRatingId).HasMaxLength(10);
            entity.Property(p => p.LastSchoolEmisNumber).HasMaxLength(50);
            entity.Property(p => p.LastSchoolYear).HasMaxLength(10);
            entity.Property(p => p.StatssaAreaCode).HasMaxLength(50);
            entity.Property(p => p.PopiActStatusId).HasMaxLength(10);
            entity.Property(p => p.PreviousLastName).HasMaxLength(100);
            entity.Property(p => p.PreviousAlternateId).HasMaxLength(50);
            entity.Property(p => p.PreviousAlternateIdTypeId).HasMaxLength(10);
            entity.Property(p => p.PreviousProviderCode).HasMaxLength(50);
            entity.Property(p => p.PreviousProviderEtqaId).HasMaxLength(10);

            entity.HasIndex(p => p.RsaIdNumber);
            entity.HasIndex(p => p.PassportNumber);
            entity.HasIndex(p => p.AlternateIdTypeId);
            entity.HasIndex(p => p.Email);
            entity.HasIndex(p => p.LastName);
            entity.HasIndex(p => p.GenderCode);
            entity.HasIndex(p => p.EquityCode);
            entity.HasIndex(p => p.NationalityCode);
            entity.HasIndex(p => p.ProvinceCode);
            entity.HasIndex(p => p.StatssaAreaCode);
            entity.HasIndex(p => p.PopiActStatusId);
            entity.HasIndex(p => p.IsActive);
        });

        // Organisation table & indexes
        modelBuilder.Entity<Organisation>(entity =>
        {
            entity.ToTable("Organisation");
            entity.Property(o => o.CompanyName).HasMaxLength(200).IsRequired();
            entity.Property(o => o.TradingName).HasMaxLength(200);
            entity.Property(o => o.SdlNumber).HasMaxLength(20).IsRequired();
            entity.Property(o => o.MainSdlNumber).HasMaxLength(20);
            entity.Property(o => o.SetaId).HasMaxLength(10).HasDefaultValue("17");
            entity.Property(o => o.RegistrationNumber).HasMaxLength(50);
            entity.Property(o => o.TaxNumber).HasMaxLength(50);
            entity.Property(o => o.LevyCategoryCode).HasMaxLength(15);
            entity.Property(o => o.OrganisationStatusCode).HasMaxLength(15);
            entity.Property(o => o.ProvinceCode).HasMaxLength(15);
            entity.Property(o => o.CountryCode).HasMaxLength(10).HasDefaultValue("ZA");
            entity.Property(o => o.SectorCode).HasMaxLength(15);
            entity.Property(o => o.ChamberCode).HasMaxLength(15);
            entity.Property(o => o.SicCode).HasMaxLength(15);
            entity.Property(o => o.CompanySizeCode).HasMaxLength(15);
            entity.Property(o => o.OrganisationTypeCode).HasMaxLength(15);
            entity.Property(o => o.PhoneNumber).HasMaxLength(50);
            entity.Property(o => o.FaxNumber).HasMaxLength(50);
            entity.Property(o => o.WebsiteUrl).HasMaxLength(200);
            entity.Property(o => o.PhysicalAddress).HasMaxLength(500);
            entity.Property(o => o.PostalAddress).HasMaxLength(500);
            entity.Property(o => o.BankName).HasMaxLength(100);
            entity.Property(o => o.BankBranchCode).HasMaxLength(20);
            entity.Property(o => o.BankAccountNumber).HasMaxLength(50);
            entity.Property(o => o.BankAccountType).HasMaxLength(50);

            entity.HasOne(o => o.PrimaryContactPerson)
                  .WithMany()
                  .HasForeignKey(o => o.PrimaryContactPersonId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(o => o.SdlNumber).IsUnique();
            entity.HasIndex(o => o.MainSdlNumber);
            entity.HasIndex(o => o.SetaId);
            entity.HasIndex(o => o.CompanyName);
            entity.HasIndex(o => o.LevyCategoryCode);
            entity.HasIndex(o => o.OrganisationStatusCode);
            entity.HasIndex(o => o.ProvinceCode);
            entity.HasIndex(o => o.SectorCode);
            entity.HasIndex(o => o.ChamberCode);
            entity.HasIndex(o => o.SicCode);
            entity.HasIndex(o => o.CompanySizeCode);
            entity.Property(o => o.MentorRatioExemptionReason).HasMaxLength(500);
            entity.Property(o => o.ChamberOverrideReason).HasMaxLength(500);
            entity.Property(o => o.ChamberOverrideApprovedBy).HasMaxLength(100);
            entity.HasIndex(o => o.IsMentorRatioEnforced);
            entity.HasIndex(o => o.IsManualChamberOverride);
            entity.HasIndex(o => o.IsActive);
        });

        // OrganisationContact table & indexes
        modelBuilder.Entity<OrganisationContact>(entity =>
        {
            entity.ToTable("OrganisationContact");
            entity.Property(c => c.ContactTypeCode).HasMaxLength(15);

            entity.HasOne(c => c.Organisation)
                  .WithMany(o => o.Contacts)
                  .HasForeignKey(c => c.OrganisationId)
                  .OnDelete(DeleteBehavior.Cascade);

            entity.HasOne(c => c.Person)
                  .WithMany()
                  .HasForeignKey(c => c.PersonId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(c => c.OrganisationId);
            entity.HasIndex(c => c.PersonId);
            entity.HasIndex(c => c.ContactTypeCode);
            entity.HasIndex(c => c.IsActive);
        });

        // OrganisationSite table & indexes
        modelBuilder.Entity<OrganisationSite>(entity =>
        {
            entity.ToTable("OrganisationSite");
            entity.Property(s => s.SiteName).HasMaxLength(200).IsRequired();
            entity.Property(s => s.SiteNumber).HasMaxLength(20).HasDefaultValue("001");
            entity.Property(s => s.PhysicalAddress).HasMaxLength(500);
            entity.Property(s => s.PostalAddress).HasMaxLength(500);
            entity.Property(s => s.ProvinceCode).HasMaxLength(15);
            entity.Property(s => s.CountryCode).HasMaxLength(10).HasDefaultValue("ZA");
            entity.Property(s => s.Latitude).HasPrecision(10, 6);
            entity.Property(s => s.Longitude).HasPrecision(10, 6);
            entity.Property(s => s.StatssaAreaCode).HasMaxLength(50);
            entity.Property(s => s.PhoneNumber).HasMaxLength(50);
            entity.Property(s => s.FaxNumber).HasMaxLength(50);
            entity.Property(s => s.Email).HasMaxLength(150);

            entity.HasOne(s => s.Organisation)
                  .WithMany(o => o.Sites)
                  .HasForeignKey(s => s.OrganisationId)
                  .OnDelete(DeleteBehavior.Cascade);

            entity.HasOne(s => s.PrimaryContactPerson)
                  .WithMany()
                  .HasForeignKey(s => s.PrimaryContactPersonId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(s => s.OrganisationId);
            entity.HasIndex(s => s.SiteNumber);
            entity.HasIndex(s => s.StatssaAreaCode);
            entity.HasIndex(s => s.PrimaryContactPersonId);
            entity.HasIndex(s => s.ProvinceCode);
            entity.HasIndex(s => s.IsActive);
        });

        // Visit relationship & mandatory contact person constraint
        modelBuilder.Entity<Visit>(entity =>
        {
            entity.ToTable("Visit");
            entity.Property(v => v.Title).HasMaxLength(200).IsRequired();
            entity.Property(v => v.VisitTypeCode).HasMaxLength(15);
            entity.Property(v => v.VisitStatusCode).HasMaxLength(15);
            entity.Property(v => v.Location).HasMaxLength(300);

            entity.HasOne(v => v.Organisation)
                  .WithMany(o => o.Visits)
                  .HasForeignKey(v => v.OrganisationId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(v => v.ContactPerson)
                  .WithMany()
                  .HasForeignKey(v => v.ContactPersonId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(v => v.OrganisationId);
            entity.HasIndex(v => v.ContactPersonId);
            entity.HasIndex(v => v.VisitDate);
            entity.HasIndex(v => v.VisitStatusCode);
        });

        // WspSubmission
        modelBuilder.Entity<WspSubmission>(entity =>
        {
            entity.ToTable("WspSubmission");
            entity.Property(w => w.ReferenceNumber).HasMaxLength(50);
            entity.Property(w => w.WspApprovalStatusCode).HasMaxLength(15);
            entity.Property(w => w.PlannedTrainingBudget).HasPrecision(18, 2);

            entity.HasOne(w => w.Organisation)
                  .WithMany(o => o.WspSubmissions)
                  .HasForeignKey(w => w.OrganisationId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(w => w.OrganisationId);
            entity.HasIndex(w => w.FinYear);
            entity.HasIndex(w => w.ReferenceNumber);
            entity.HasIndex(w => w.WspApprovalStatusCode);

            entity.HasQueryFilter(w => _tenantProvider.IsAdmin || _tenantProvider.CurrentOrganisationId == null || w.OrganisationId == _tenantProvider.CurrentOrganisationId);
        });

        // LevyFile & LevyFileLine & SarsLevyStaging
        modelBuilder.Entity<LevyFile>(entity =>
        {
            entity.ToTable("LevyFile");
            entity.Property(l => l.FileName).HasMaxLength(255).IsRequired();
            entity.Property(l => l.FileRef).HasMaxLength(100);
            entity.Property(l => l.ImportStatusCode).HasMaxLength(25);
            entity.Property(l => l.DigitalSecuritySeal).HasMaxLength(64);
            entity.Property(l => l.TotalAmount).HasPrecision(18, 2);
            entity.Property(l => l.ControlTotalAmount).HasPrecision(18, 2);

            entity.HasIndex(l => l.FileRef);
            entity.HasIndex(l => l.ImportDate);
            entity.HasIndex(l => l.ImportStatusCode);
            entity.HasIndex(l => l.DigitalSecuritySeal);
        });

        modelBuilder.Entity<LevyFileLine>(entity =>
        {
            entity.ToTable("LevyFileLine");
            entity.Property(l => l.SdlNumber).HasMaxLength(20).IsRequired();
            entity.Property(l => l.SchemeYear).HasMaxLength(10);
            entity.Property(l => l.MandatoryLevyAmount).HasPrecision(18, 2);
            entity.Property(l => l.DiscretionaryLevyAmount).HasPrecision(18, 2);
            entity.Property(l => l.AdminLevyAmount).HasPrecision(18, 2);
            entity.Property(l => l.QctoLevyAmount).HasPrecision(18, 2);
            entity.Property(l => l.InterestAmount).HasPrecision(18, 2);
            entity.Property(l => l.PenaltyAmount).HasPrecision(18, 2);
            entity.Property(l => l.TotalLevyAmount).HasPrecision(18, 2);
            entity.Property(l => l.SicCode).HasMaxLength(20);
            entity.Property(l => l.ChamberCode).HasMaxLength(20);
            entity.Property(l => l.SetaCode).HasMaxLength(10).HasDefaultValue("17");

            entity.HasOne(l => l.LevyFile)
                  .WithMany(f => f.LineItems)
                  .HasForeignKey(l => l.LevyFileId)
                  .OnDelete(DeleteBehavior.Cascade);

            entity.HasIndex(l => l.LevyFileId);
            entity.HasIndex(l => l.SdlNumber);
            entity.HasIndex(l => l.SicCode);
            entity.HasIndex(l => l.ChamberCode);
            entity.HasIndex(l => l.SetaCode);
            entity.HasIndex(l => l.IsOutOfScopeSeta);
            entity.HasIndex(l => l.HasSicCodeMismatch);
        });

        modelBuilder.Entity<SarsLevyStaging>(entity =>
        {
            entity.ToTable("SarsLevyStaging");
            entity.Property(s => s.BatchIdentifier).HasMaxLength(100).IsRequired();
            entity.Property(s => s.SdlNumber).HasMaxLength(20).IsRequired();
            entity.Property(s => s.SchemeYear).HasMaxLength(10);
            entity.Property(s => s.SicCode).HasMaxLength(20);
            entity.Property(s => s.ChamberCode).HasMaxLength(20);
            entity.Property(s => s.SetaCode).HasMaxLength(10).HasDefaultValue("17");
            entity.Property(s => s.RawRecord).HasMaxLength(1000);
            entity.Property(s => s.StagingStatus).HasMaxLength(25).HasDefaultValue("Pending");
            entity.Property(s => s.ValidationMessage).HasMaxLength(500);

            entity.Property(s => s.MandatoryLevyAmount).HasPrecision(18, 2);
            entity.Property(s => s.DiscretionaryLevyAmount).HasPrecision(18, 2);
            entity.Property(s => s.AdminLevyAmount).HasPrecision(18, 2);
            entity.Property(s => s.QctoLevyAmount).HasPrecision(18, 2);
            entity.Property(s => s.InterestAmount).HasPrecision(18, 2);
            entity.Property(s => s.PenaltyAmount).HasPrecision(18, 2);
            entity.Property(s => s.TotalLevyAmount).HasPrecision(18, 2);

            entity.HasIndex(s => s.BatchIdentifier);
            entity.HasIndex(s => s.SdlNumber);
            entity.HasIndex(s => s.SicCode);
            entity.HasIndex(s => s.StagingStatus);
            entity.HasIndex(s => s.IsOutOfScopeSeta);
        });

        // GrantApplication
        modelBuilder.Entity<GrantApplication>(entity =>
        {
            entity.ToTable("GrantApplication");
            entity.Property(g => g.ApplicationNumber).HasMaxLength(50).IsRequired();
            entity.Property(g => g.GrantTypeCode).HasMaxLength(15);
            entity.Property(g => g.ApplicationStatusCode).HasMaxLength(15);
            entity.Property(g => g.ProjectTitle).HasMaxLength(300);
            entity.Property(g => g.RequestedAmount).HasPrecision(18, 2);
            entity.Property(g => g.ApprovedAmount).HasPrecision(18, 2);

            entity.Property(g => g.WspExemptionReason).HasMaxLength(500);

            entity.HasOne(g => g.Organisation)
                  .WithMany(o => o.GrantApplications)
                  .HasForeignKey(g => g.OrganisationId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(g => g.WspSubmission)
                  .WithMany()
                  .HasForeignKey(g => g.WspSubmissionId)
                  .OnDelete(DeleteBehavior.SetNull);

            entity.HasOne(g => g.FundingWindow)
                  .WithMany(w => w.Applications)
                  .HasForeignKey(g => g.FundingWindowId)
                  .OnDelete(DeleteBehavior.SetNull);

            entity.HasOne(g => g.StrategicPriority)
                  .WithMany(s => s.GrantApplications)
                  .HasForeignKey(g => g.StrategicPriorityId)
                  .OnDelete(DeleteBehavior.SetNull);

            entity.HasOne(g => g.FundingWindowPriority)
                  .WithMany()
                  .HasForeignKey(g => g.FundingWindowPriorityId)
                  .OnDelete(DeleteBehavior.SetNull);

            entity.HasIndex(g => g.OrganisationId);
            entity.HasIndex(g => g.FundingWindowId);
            entity.HasIndex(g => g.StrategicPriorityId);
            entity.HasIndex(g => g.FundingWindowPriorityId);
            entity.HasIndex(g => g.WspSubmissionId);
            entity.HasIndex(g => g.ApplicationNumber);
            entity.HasIndex(g => g.ApplicationStatusCode);

            entity.HasQueryFilter(g => _tenantProvider.IsAdmin || _tenantProvider.CurrentOrganisationId == null || g.OrganisationId == _tenantProvider.CurrentOrganisationId);
        });

        // EtqaAssessor
        modelBuilder.Entity<EtqaAssessor>(entity =>
        {
            entity.ToTable("EtqaAssessor");
            entity.Property(a => a.RegistrationNumber).HasMaxLength(50).IsRequired();
            entity.Property(a => a.DesignationTypeId).HasMaxLength(10).HasDefaultValue("01");
            entity.Property(a => a.DesignationStructureStatusId).HasMaxLength(10).HasDefaultValue("01");
            entity.Property(a => a.EtqaId).HasMaxLength(10).HasDefaultValue("17");
            entity.Property(a => a.EtqeDecisionNumber).HasMaxLength(50);
            entity.Property(a => a.EtqaRole).HasMaxLength(50);
            entity.Property(a => a.RegistrationStatusCode).HasMaxLength(15);

            entity.HasOne(a => a.Person)
                  .WithMany()
                  .HasForeignKey(a => a.PersonId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(a => a.TrainingProvider)
                  .WithMany()
                  .HasForeignKey(a => a.TrainingProviderId)
                  .OnDelete(DeleteBehavior.SetNull);

            entity.HasIndex(a => a.PersonId);
            entity.HasIndex(a => a.TrainingProviderId);
            entity.HasIndex(a => a.RegistrationNumber);
            entity.HasIndex(a => a.DesignationTypeId);
            entity.HasIndex(a => a.DesignationStructureStatusId);
            entity.HasIndex(a => a.EtqaId);
            entity.HasIndex(a => a.RegistrationStatusCode);
        });

        // TrainingProvider
        modelBuilder.Entity<TrainingProvider>(entity =>
        {
            entity.ToTable("TrainingProvider", t => t.HasCheckConstraint("CK_TrainingProvider_AccreditationDates", "[AccreditationEndDate] IS NULL OR [AccreditationStartDate] IS NULL OR [AccreditationEndDate] >= [AccreditationStartDate]"));
            entity.Property(tp => tp.AccreditationNumber).HasMaxLength(50).IsRequired();
            entity.Property(tp => tp.ProviderCode).HasMaxLength(50);
            entity.Property(tp => tp.EtqaId).HasMaxLength(10).HasDefaultValue("17");
            entity.Property(tp => tp.ProviderClassId).HasMaxLength(10).HasDefaultValue("02");
            entity.Property(tp => tp.ProviderTypeId).HasMaxLength(10).HasDefaultValue("02");
            entity.Property(tp => tp.ProviderStatusId).HasMaxLength(10).HasDefaultValue("01");
            entity.Property(tp => tp.ProviderTypeCode).HasMaxLength(15);
            entity.Property(tp => tp.ProviderStatusCode).HasMaxLength(15);
            entity.Property(tp => tp.EtqaDecisionNumber).HasMaxLength(50);
            entity.Property(tp => tp.SarsNumber).HasMaxLength(50);
            entity.Property(tp => tp.FaxNumber).HasMaxLength(50);
            entity.Property(tp => tp.WebsiteUrl).HasMaxLength(200);

            entity.Ignore(tp => tp.StatusCode);
            entity.Ignore(tp => tp.LegalName);
            entity.Ignore(tp => tp.ProviderName);

            entity.HasOne(tp => tp.Organisation)
                  .WithMany(o => o.TrainingProviders)
                  .HasForeignKey(tp => tp.OrganisationId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(tp => tp.PrimaryContactPerson)
                  .WithMany()
                  .HasForeignKey(tp => tp.PrimaryContactPersonId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(tp => tp.OrganisationId);
            entity.HasIndex(tp => tp.PrimaryContactPersonId);
            entity.HasIndex(tp => tp.ProviderCode);
            entity.HasIndex(tp => tp.AccreditationNumber);
            entity.HasIndex(tp => tp.ProviderClassId);
            entity.HasIndex(tp => tp.ProviderTypeId);
            entity.HasIndex(tp => tp.ProviderStatusId);
            entity.HasIndex(tp => tp.EtqaId);
            entity.HasIndex(tp => tp.IsActive);
        });

        // TrainingProviderQualification
        modelBuilder.Entity<TrainingProviderQualification>(entity =>
        {
            entity.ToTable("TrainingProviderQualification");
            entity.Property(q => q.QualificationTitle).HasMaxLength(200).IsRequired();
            entity.Property(q => q.AccreditationStatusCode).HasMaxLength(15);

            entity.HasOne(q => q.TrainingProvider)
                  .WithMany(tp => tp.Qualifications)
                  .HasForeignKey(q => q.TrainingProviderId)
                  .OnDelete(DeleteBehavior.Cascade);

            entity.HasIndex(q => q.TrainingProviderId);
            entity.HasIndex(q => q.SaqaQualificationId);
            entity.HasIndex(q => q.AccreditationStatusCode);
        });

        // TrainingProviderUnitStandard
        modelBuilder.Entity<TrainingProviderUnitStandard>(entity =>
        {
            entity.ToTable("TrainingProviderUnitStandard");
            entity.Property(u => u.UnitStandardTitle).HasMaxLength(200).IsRequired();

            entity.HasOne(u => u.TrainingProvider)
                  .WithMany(tp => tp.UnitStandards)
                  .HasForeignKey(u => u.TrainingProviderId)
                  .OnDelete(DeleteBehavior.Cascade);

            entity.HasIndex(u => u.TrainingProviderId);
            entity.HasIndex(u => u.UnitStandardId);
        });

        // WspEmploymentSummary
        modelBuilder.Entity<WspEmploymentSummary>(entity =>
        {
            entity.ToTable("WspEmploymentSummary");
            entity.Property(e => e.OfoCode).HasMaxLength(20);
            entity.Property(e => e.OccupationalCategory).HasMaxLength(100);

            entity.HasOne(e => e.WspSubmission)
                  .WithMany(w => w.EmploymentSummaries)
                  .HasForeignKey(e => e.WspSubmissionId)
                  .OnDelete(DeleteBehavior.Cascade);

            entity.HasIndex(e => e.WspSubmissionId);
            entity.HasIndex(e => e.OfoCode);
        });

        // WspTrainingPlan
        modelBuilder.Entity<WspTrainingPlan>(entity =>
        {
            entity.ToTable("WspTrainingPlan");
            entity.Property(p => p.ProgrammeTypeCode).HasMaxLength(15);
            entity.Property(p => p.EstimatedCost).HasPrecision(18, 2);

            entity.HasOne(p => p.WspSubmission)
                  .WithMany(w => w.TrainingPlans)
                  .HasForeignKey(p => p.WspSubmissionId)
                  .OnDelete(DeleteBehavior.Cascade);

            entity.HasIndex(p => p.WspSubmissionId);
            entity.HasIndex(p => p.ProgrammeTypeCode);
        });

        // GrantFundingWindow
        modelBuilder.Entity<GrantFundingWindow>(entity =>
        {
            entity.ToTable("GrantFundingWindow", t => t.HasCheckConstraint("CK_GrantFundingWindow_Dates", "[ClosingDate] >= [OpeningDate]"));
            entity.Property(w => w.WindowName).HasMaxLength(200).IsRequired();
            entity.Property(w => w.GrantTypeCode).HasMaxLength(15);
            entity.Property(w => w.TotalAvailableBudget).HasPrecision(18, 2);

            entity.HasIndex(w => w.FinYear);
            entity.HasIndex(w => w.GrantTypeCode);
            entity.HasIndex(w => w.IsActive);
        });

        // GrantProjectBudget
        modelBuilder.Entity<GrantProjectBudget>(entity =>
        {
            entity.ToTable("GrantProjectBudget");
            entity.Property(b => b.ExpenseCategory).HasMaxLength(100).IsRequired();
            entity.Property(b => b.Description).HasMaxLength(500);
            entity.Property(b => b.UnitCost).HasPrecision(18, 2);
            entity.Property(b => b.TotalCost).HasPrecision(18, 2);

            entity.HasOne(b => b.GrantApplication)
                  .WithMany(g => g.ProjectBudgets)
                  .HasForeignKey(b => b.GrantApplicationId)
                  .OnDelete(DeleteBehavior.Cascade);

            entity.HasOne(b => b.StrategicPriority)
                  .WithMany()
                  .HasForeignKey(b => b.StrategicPriorityId)
                  .OnDelete(DeleteBehavior.SetNull);

            entity.HasIndex(b => b.GrantApplicationId);
            entity.HasIndex(b => b.StrategicPriorityId);
            entity.HasIndex(b => b.ExpenseCategory);
        });

        // StrategicPriority
        modelBuilder.Entity<StrategicPriority>(entity =>
        {
            entity.ToTable("StrategicPriority");
            entity.Property(s => s.Code).HasMaxLength(50).IsRequired();
            entity.Property(s => s.Name).HasMaxLength(200).IsRequired();
            entity.Property(s => s.Description).HasMaxLength(1000);
            entity.Property(s => s.NsdpOutcomeCode).HasMaxLength(50).IsRequired();
            entity.Property(s => s.NsdpOutcomeDescription).HasMaxLength(500);
            entity.Property(s => s.SipCategory).HasMaxLength(150);
            entity.Property(s => s.TargetSector).HasMaxLength(100);

            entity.HasIndex(s => s.Code).IsUnique();
            entity.HasIndex(s => s.NsdpOutcomeCode);
            entity.HasIndex(s => s.IsActive);
        });

        // FundingWindowPriority
        modelBuilder.Entity<FundingWindowPriority>(entity =>
        {
            entity.ToTable("FundingWindowPriority");
            entity.Property(p => p.AllocatedBudget).HasPrecision(18, 2);
            entity.Property(p => p.MinScoreThreshold).HasPrecision(5, 2);

            entity.HasOne(p => p.FundingWindow)
                  .WithMany(w => w.StrategicPriorities)
                  .HasForeignKey(p => p.FundingWindowId)
                  .OnDelete(DeleteBehavior.Cascade);

            entity.HasOne(p => p.StrategicPriority)
                  .WithMany(s => s.WindowPriorities)
                  .HasForeignKey(p => p.StrategicPriorityId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(p => p.FundingWindowId);
            entity.HasIndex(p => p.StrategicPriorityId);
            entity.HasIndex(p => new { p.FundingWindowId, p.StrategicPriorityId }).IsUnique();
            entity.HasIndex(p => p.IsActive);
        });

        // AssessorModeratorScope
        modelBuilder.Entity<AssessorModeratorScope>(entity =>
        {
            entity.ToTable("AssessorModeratorScope");
            entity.Property(s => s.QualificationTitle).HasMaxLength(200).IsRequired();
            entity.Property(s => s.RegistrationStatusCode).HasMaxLength(15);

            entity.HasOne(s => s.EtqaAssessor)
                  .WithMany(a => a.Scopes)
                  .HasForeignKey(s => s.EtqaAssessorId)
                  .OnDelete(DeleteBehavior.Cascade);

            entity.HasIndex(s => s.EtqaAssessorId);
            entity.HasIndex(s => s.SaqaQualificationId);
            entity.HasIndex(s => s.RegistrationStatusCode);
        });

        // LearnerAssessment
        modelBuilder.Entity<LearnerAssessment>(entity =>
        {
            entity.ToTable("LearnerAssessment", t => t.HasCheckConstraint("CK_LearnerAssessment_Dates", "[ModerationDate] IS NULL OR [AssessmentDate] IS NULL OR [ModerationDate] >= [AssessmentDate]"));
            entity.Property(a => a.QualificationTitle).HasMaxLength(200).IsRequired();
            entity.Property(a => a.UnitStandardTitle).HasMaxLength(250);
            entity.Property(a => a.PartOfId).HasMaxLength(10).HasDefaultValue("01");
            entity.Property(a => a.EnrolmentTypeId).HasMaxLength(10).HasDefaultValue("01");
            entity.Property(a => a.EnrolmentStatusId).HasMaxLength(10).HasDefaultValue("02");
            entity.Property(a => a.EnrolmentStatusReasonId).HasMaxLength(10);
            entity.Property(a => a.AssessorRegistrationNumber).HasMaxLength(50);
            entity.Property(a => a.AssessorEtqaId).HasMaxLength(10).HasDefaultValue("17");
            entity.Property(a => a.CertificateNumber).HasMaxLength(100);
            entity.Property(a => a.CumulativeSpend).HasColumnType("decimal(18,2)");
            entity.Property(a => a.OfoCode).HasMaxLength(20);
            entity.Property(a => a.FundingId).HasMaxLength(10).HasDefaultValue("01");
            entity.Property(a => a.UrbanRuralId).HasMaxLength(10).HasDefaultValue("01");
            entity.Property(a => a.EconomicStatusId).HasMaxLength(10).HasDefaultValue("01");
            entity.Property(a => a.NonNqfInterventionCode).HasMaxLength(50);
            entity.Property(a => a.CompetencyStatusCode).HasMaxLength(15);

            entity.HasOne(a => a.CompanyLearner)
                  .WithMany(cl => cl.Assessments)
                  .HasForeignKey(a => a.CompanyLearnerId)
                  .OnDelete(DeleteBehavior.Cascade);

            entity.HasOne(a => a.EtqaAssessor)
                  .WithMany(ea => ea.Assessments)
                  .HasForeignKey(a => a.EtqaAssessorId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(a => a.Person)
                  .WithMany()
                  .HasForeignKey(a => a.PersonId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(a => a.Organisation)
                  .WithMany()
                  .HasForeignKey(a => a.OrganisationId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(a => a.TrainingProvider)
                  .WithMany()
                  .HasForeignKey(a => a.TrainingProviderId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(a => a.ModeratorPerson)
                  .WithMany()
                  .HasForeignKey(a => a.ModeratorPersonId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(a => a.CompanyLearnerId);
            entity.HasIndex(a => a.UnitStandardId);
            entity.HasIndex(a => a.EtqaAssessorId);
            entity.HasIndex(a => a.PersonId);
            entity.HasIndex(a => a.OrganisationId);
            entity.HasIndex(a => a.TrainingProviderId);
            entity.HasIndex(a => a.ModeratorPersonId);
            entity.HasIndex(a => a.PartOfId);
            entity.HasIndex(a => a.EnrolmentTypeId);
            entity.HasIndex(a => a.EnrolmentStatusId);
            entity.HasIndex(a => a.CompetencyStatusCode);
            entity.HasIndex(a => a.AssessmentDate);
        });

        // AuditLog
        modelBuilder.Entity<AuditLog>(entity =>
        {
            entity.ToTable("AuditLog");
            entity.Property(a => a.EntityName).HasMaxLength(100).IsRequired();
            entity.Property(a => a.ActionName).HasMaxLength(50).IsRequired();
            entity.Property(a => a.Actor).HasMaxLength(100);

            entity.HasIndex(a => new { a.EntityName, a.RecordId });
            entity.HasIndex(a => a.Timestamp);
        });

        // WorkplaceApproval
        modelBuilder.Entity<WorkplaceApproval>(entity =>
        {
            entity.ToTable("WorkplaceApproval");
            entity.Property(w => w.QualificationTitle).HasMaxLength(250).IsRequired();
            entity.Property(w => w.ApprovalNumber).HasMaxLength(50);
            entity.Property(w => w.ApprovalStatusCode).HasMaxLength(50);

            entity.HasOne(w => w.Organisation)
                  .WithMany()
                  .HasForeignKey(w => w.OrganisationId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(w => w.OrganisationSite)
                  .WithMany()
                  .HasForeignKey(w => w.OrganisationSiteId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(w => w.ContactPerson)
                  .WithMany()
                  .HasForeignKey(w => w.ContactPersonId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(w => w.AssessorPerson)
                  .WithMany()
                  .HasForeignKey(w => w.AssessorPersonId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasMany(w => w.Mentors)
                  .WithOne(m => m.WorkplaceApproval)
                  .HasForeignKey(m => m.WorkplaceApprovalId)
                  .OnDelete(DeleteBehavior.Cascade);

            entity.HasMany(w => w.ToolItems)
                  .WithOne(t => t.WorkplaceApproval)
                  .HasForeignKey(t => t.WorkplaceApprovalId)
                  .OnDelete(DeleteBehavior.Cascade);

            entity.Property(w => w.TradeCode).HasMaxLength(50);
            entity.Property(w => w.MentorRatioExemptionNotes).HasMaxLength(500);

            entity.HasIndex(w => w.OrganisationId);
            entity.HasIndex(w => w.OrganisationSiteId);
            entity.HasIndex(w => w.ContactPersonId);
            entity.HasIndex(w => w.ApprovalNumber);
            entity.HasIndex(w => w.ApprovalStatusCode);
            entity.HasIndex(w => w.TradeCode);
            entity.HasIndex(w => w.IsRatioEnforced);

            entity.HasQueryFilter(w => _tenantProvider.IsAdmin || _tenantProvider.CurrentOrganisationId == null || w.OrganisationId == _tenantProvider.CurrentOrganisationId);
        });

        // WorkplaceApprovalMentor
        modelBuilder.Entity<WorkplaceApprovalMentor>(entity =>
        {
            entity.ToTable("WorkplaceApprovalMentor");
            entity.Property(m => m.Designation).HasMaxLength(100);
            entity.Property(m => m.ArtisanTradeNumber).HasMaxLength(50);
            entity.Property(m => m.Notes).HasMaxLength(500);

            entity.HasOne(m => m.Person)
                  .WithMany()
                  .HasForeignKey(m => m.PersonId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(m => m.WorkplaceApprovalId);
            entity.HasIndex(m => m.PersonId);
            entity.HasIndex(m => m.IsActive);
            entity.HasIndex(m => m.IsRatioExempt);
            entity.HasIndex(m => m.IsRatioEnforced);
        });

        // TradeMentorRatioPolicy
        modelBuilder.Entity<TradeMentorRatioPolicy>(entity =>
        {
            entity.ToTable("TradeMentorRatioPolicy");
            entity.Property(p => p.TradeCode).HasMaxLength(50).IsRequired();
            entity.Property(p => p.TradeTitle).HasMaxLength(200).IsRequired();
            entity.Property(p => p.TradeOfoCode).HasMaxLength(50);
            entity.Property(p => p.Notes).HasMaxLength(500);

            entity.HasIndex(p => p.TradeCode).IsUnique();
            entity.HasIndex(p => p.TradeOfoCode);
            entity.HasIndex(p => p.SaqaQualificationId);
            entity.HasIndex(p => p.IsActive);
        });

        // WorkplaceApprovalToolList
        modelBuilder.Entity<WorkplaceApprovalToolList>(entity =>
        {
            entity.ToTable("WorkplaceApprovalToolList");
            entity.Property(t => t.ToolName).HasMaxLength(150).IsRequired();
            entity.Property(t => t.Category).HasMaxLength(50);
            entity.Property(t => t.Remarks).HasMaxLength(250);

            entity.HasIndex(t => t.WorkplaceApprovalId);
        });

        // CompanyLearner
        modelBuilder.Entity<CompanyLearner>(entity =>
        {
            entity.ToTable("CompanyLearner", t => t.HasCheckConstraint("CK_CompanyLearner_Dates", "[CompletionDate] IS NULL OR [CommencementDate] IS NULL OR [CompletionDate] >= [CommencementDate]"));
            entity.Property(l => l.LearnerContractNumber).HasMaxLength(50);
            entity.Property(l => l.QualificationTitle).HasMaxLength(250).IsRequired();
            entity.Property(l => l.LearningProgrammeTypeCode).HasMaxLength(50);
            entity.Property(l => l.LearnershipId).HasMaxLength(50);
            entity.Property(l => l.NonNqfInterventionCode).HasMaxLength(50);
            entity.Property(l => l.PartOfId).HasMaxLength(10).HasDefaultValue("01");
            entity.Property(l => l.EnrolmentTypeId).HasMaxLength(10).HasDefaultValue("01");
            entity.Property(l => l.EnrolmentStatusId).HasMaxLength(10).HasDefaultValue("01");
            entity.Property(l => l.EnrolmentStatusReasonId).HasMaxLength(10);
            entity.Property(l => l.AssessorRegistrationNumber).HasMaxLength(50);
            entity.Property(l => l.AssessorEtqaId).HasMaxLength(10).HasDefaultValue("17");
            entity.Property(l => l.PracticalProviderCode).HasMaxLength(50);
            entity.Property(l => l.PracticalProviderEtqaId).HasMaxLength(10).HasDefaultValue("17");
            entity.Property(l => l.OfoCode).HasMaxLength(20);
            entity.Property(l => l.EconomicStatusId).HasMaxLength(10).HasDefaultValue("01");
            entity.Property(l => l.UrbanRuralId).HasMaxLength(10).HasDefaultValue("01");
            entity.Property(l => l.CumulativeSpend).HasColumnType("decimal(18,2)");
            entity.Property(l => l.CertificateNumber).HasMaxLength(100);
            entity.Property(l => l.PriorQualificationId).HasMaxLength(50);
            entity.Property(l => l.InternshipStatusId).HasMaxLength(10);
            entity.Property(l => l.FundingTypeCode).HasMaxLength(50);
            entity.Property(l => l.FundingId).HasMaxLength(10).HasDefaultValue("01");
            entity.Property(l => l.EnrolmentStatusCode).HasMaxLength(50);

            entity.HasOne(l => l.Person)
                  .WithMany()
                  .HasForeignKey(l => l.PersonId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(l => l.Organisation)
                  .WithMany()
                  .HasForeignKey(l => l.OrganisationId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(l => l.OrganisationSite)
                  .WithMany()
                  .HasForeignKey(l => l.OrganisationSiteId)
                  .OnDelete(DeleteBehavior.SetNull);

            entity.HasOne(l => l.TrainingProvider)
                  .WithMany()
                  .HasForeignKey(l => l.TrainingProviderId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasMany(l => l.TradeTests)
                  .WithOne(t => t.CompanyLearner)
                  .HasForeignKey(t => t.CompanyLearnerId)
                  .OnDelete(DeleteBehavior.Cascade);

            entity.HasIndex(l => l.PersonId);
            entity.HasIndex(l => l.OrganisationId);
            entity.HasIndex(l => l.OrganisationSiteId);
            entity.HasIndex(l => l.TrainingProviderId);
            entity.HasIndex(l => l.LearnershipId);
            entity.HasIndex(l => l.NonNqfInterventionCode);
            entity.HasIndex(l => l.PartOfId);
            entity.HasIndex(l => l.EnrolmentTypeId);
            entity.HasIndex(l => l.EnrolmentStatusId);
            entity.HasIndex(l => l.OfoCode);
            entity.HasIndex(l => l.EconomicStatusId);
            entity.HasIndex(l => l.UrbanRuralId);
            entity.HasIndex(l => l.FundingId);
            entity.HasIndex(l => l.LearnerContractNumber);
            entity.HasIndex(l => l.EnrolmentStatusCode);
            entity.HasIndex(l => l.RegistrationDate);
        });

        // LearnerTradeTest
        modelBuilder.Entity<LearnerTradeTest>(entity =>
        {
            entity.ToTable("LearnerTradeTest");
            entity.Property(t => t.TestCenterName).HasMaxLength(150).IsRequired();
            entity.Property(t => t.TradeTestCentreCode).HasMaxLength(50);
            entity.Property(t => t.TradeTestCentreEtqaId).HasMaxLength(10).HasDefaultValue("17");
            entity.Property(t => t.TradeTitle).HasMaxLength(150).IsRequired();
            entity.Property(t => t.TradeCode).HasMaxLength(50);
            entity.Property(t => t.QualificationId).HasMaxLength(50);
            entity.Property(t => t.TradeTestResultId).HasMaxLength(10).HasDefaultValue("01");
            entity.Property(t => t.TradeTestResultReasonId).HasMaxLength(10).HasDefaultValue("01");
            entity.Property(t => t.ResultStatusCode).HasMaxLength(50);
            entity.Property(t => t.AssessorRegistrationNumber).HasMaxLength(50);
            entity.Property(t => t.AssessorEtqaId).HasMaxLength(10).HasDefaultValue("17");
            entity.Property(t => t.ModeratorRegistrationNumber).HasMaxLength(50);
            entity.Property(t => t.ModeratorEtqaId).HasMaxLength(10).HasDefaultValue("17");
            entity.Property(t => t.TrainingProviderEtqaId).HasMaxLength(10).HasDefaultValue("17");
            entity.Property(t => t.SerialCertificateNumber).HasMaxLength(50);

            entity.HasOne(t => t.TrainingProvider)
                  .WithMany()
                  .HasForeignKey(t => t.TrainingProviderId)
                  .OnDelete(DeleteBehavior.SetNull);

            entity.HasOne(t => t.AssessorPerson)
                  .WithMany()
                  .HasForeignKey(t => t.AssessorPersonId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(t => t.ModeratorPerson)
                  .WithMany()
                  .HasForeignKey(t => t.ModeratorPersonId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(t => t.CompanyLearnerId);
            entity.HasIndex(t => t.TrainingProviderId);
            entity.HasIndex(t => t.TradeTestCentreCode);
            entity.HasIndex(t => t.TradeCode);
            entity.HasIndex(t => t.QualificationId);
            entity.HasIndex(t => t.TradeTestResultId);
            entity.HasIndex(t => t.TradeTestDate);
            entity.HasIndex(t => t.ResultStatusCode);
            entity.HasIndex(t => t.SerialCertificateNumber);
        });

        // WorkflowDefinition
        modelBuilder.Entity<WorkflowDefinition>(entity =>
        {
            entity.ToTable("WorkflowDefinition");
            entity.Property(d => d.Code).HasMaxLength(50).IsRequired();
            entity.Property(d => d.Name).HasMaxLength(150).IsRequired();
            entity.Property(d => d.TargetEntityName).HasMaxLength(100).IsRequired();
            entity.Property(d => d.KeyFieldName).HasMaxLength(50).IsRequired();

            entity.HasIndex(d => d.Code).IsUnique();
            entity.HasIndex(d => d.TargetEntityName);
        });

        // WorkflowState
        modelBuilder.Entity<WorkflowState>(entity =>
        {
            entity.ToTable("WorkflowState");
            entity.Property(s => s.StateName).HasMaxLength(100).IsRequired();
            entity.Property(s => s.StateCode).HasMaxLength(50).IsRequired();
            entity.Property(s => s.AllowedGroupRole).HasMaxLength(100);

            entity.HasOne(s => s.WorkflowDefinition)
                  .WithMany(d => d.States)
                  .HasForeignKey(s => s.WorkflowDefinitionId)
                  .OnDelete(DeleteBehavior.Cascade);

            entity.HasIndex(s => s.WorkflowDefinitionId);
            entity.HasIndex(s => s.StateCode);
        });

        // WorkflowTransition
        modelBuilder.Entity<WorkflowTransition>(entity =>
        {
            entity.ToTable("WorkflowTransition");
            entity.Property(t => t.ActionName).HasMaxLength(100).IsRequired();
            entity.Property(t => t.ButtonColor).HasMaxLength(50);
            entity.Property(t => t.ButtonIcon).HasMaxLength(50);
            entity.Property(t => t.RequiredPermission).HasMaxLength(100);
            entity.Property(t => t.NewEntityStatusCode).HasMaxLength(50);

            entity.HasOne(t => t.WorkflowDefinition)
                  .WithMany(d => d.Transitions)
                  .HasForeignKey(t => t.WorkflowDefinitionId)
                  .OnDelete(DeleteBehavior.Cascade);

            entity.HasOne(t => t.FromState)
                  .WithMany()
                  .HasForeignKey(t => t.FromStateId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(t => t.ToState)
                  .WithMany()
                  .HasForeignKey(t => t.ToStateId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(t => t.WorkflowDefinitionId);
            entity.HasIndex(t => t.FromStateId);
            entity.HasIndex(t => t.ToStateId);
        });

        // WorkflowInstance
        modelBuilder.Entity<WorkflowInstance>(entity =>
        {
            entity.ToTable("WorkflowInstance");
            entity.Property(i => i.EntityTitle).HasMaxLength(250);
            entity.Property(i => i.EntityReferenceNumber).HasMaxLength(100);
            entity.Property(i => i.InitiatorUserId).HasMaxLength(100);
            entity.Property(i => i.InitiatorName).HasMaxLength(150);

            entity.HasOne(i => i.WorkflowDefinition)
                  .WithMany(d => d.Instances)
                  .HasForeignKey(i => i.WorkflowDefinitionId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(i => i.CurrentWorkflowState)
                  .WithMany()
                  .HasForeignKey(i => i.CurrentWorkflowStateId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(i => i.WorkflowDefinitionId);
            entity.HasIndex(i => new { i.WorkflowDefinitionId, i.EntityId });
            entity.HasIndex(i => i.CurrentWorkflowStateId);
            entity.HasIndex(i => i.IsCompleted);
        });

        // WorkflowTask
        modelBuilder.Entity<WorkflowTask>(entity =>
        {
            entity.ToTable("WorkflowTask");
            entity.Property(t => t.TaskTitle).HasMaxLength(250).IsRequired();
            entity.Property(t => t.TaskDescription).HasMaxLength(500);
            entity.Property(t => t.AssignedGroupRole).HasMaxLength(100);
            entity.Property(t => t.AssignedUserId).HasMaxLength(100);
            entity.Property(t => t.AssignedUserName).HasMaxLength(150);
            entity.Property(t => t.TaskStatus).HasMaxLength(50).IsRequired();
            entity.Property(t => t.Priority).HasMaxLength(20);
            entity.Property(t => t.TargetRoute).HasMaxLength(250);

            entity.HasOne(t => t.WorkflowInstance)
                  .WithMany(i => i.Tasks)
                  .HasForeignKey(t => t.WorkflowInstanceId)
                  .OnDelete(DeleteBehavior.Cascade);

            entity.HasIndex(t => t.WorkflowInstanceId);
            entity.HasIndex(t => t.AssignedGroupRole);
            entity.HasIndex(t => t.AssignedUserId);
            entity.HasIndex(t => t.TaskStatus);
            entity.HasIndex(t => t.DueDate);
        });

        // WorkflowHistory
        modelBuilder.Entity<WorkflowHistory>(entity =>
        {
            entity.ToTable("WorkflowHistory");
            entity.Property(h => h.ActionName).HasMaxLength(100).IsRequired();
            entity.Property(h => h.ActorUserId).HasMaxLength(100);
            entity.Property(h => h.ActorName).HasMaxLength(150);
            entity.Property(h => h.ActorRole).HasMaxLength(100);
            entity.Property(h => h.Comments).HasMaxLength(1000);

            entity.HasOne(h => h.WorkflowInstance)
                  .WithMany(i => i.History)
                  .HasForeignKey(h => h.WorkflowInstanceId)
                  .OnDelete(DeleteBehavior.Cascade);

            entity.HasOne(h => h.FromState)
                  .WithMany()
                  .HasForeignKey(h => h.FromStateId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(h => h.ToState)
                  .WithMany()
                  .HasForeignKey(h => h.ToStateId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(h => h.WorkflowInstanceId);
            entity.HasIndex(h => h.ActionDate);
        });

        // WorkflowNotification
        modelBuilder.Entity<WorkflowNotification>(entity =>
        {
            entity.ToTable("WorkflowNotification");
            entity.Property(n => n.RecipientUserId).HasMaxLength(100).IsRequired();
            entity.Property(n => n.Title).HasMaxLength(200).IsRequired();
            entity.Property(n => n.TargetRoute).HasMaxLength(250);

            entity.HasOne(n => n.WorkflowInstance)
                  .WithMany(i => i.Notifications)
                  .HasForeignKey(n => n.WorkflowInstanceId)
                  .OnDelete(DeleteBehavior.Cascade);

            entity.HasIndex(n => n.RecipientUserId);
            entity.HasIndex(n => n.IsRead);
            entity.HasIndex(n => n.CreatedDate);
        });

        // DocumentMetadata
        modelBuilder.Entity<DocumentMetadata>(entity =>
        {
            entity.ToTable("DocumentMetadata");
            entity.Property(d => d.TargetEntityName).HasMaxLength(100).IsRequired();
            entity.Property(d => d.DocumentTypeCode).HasMaxLength(50).IsRequired();
            entity.Property(d => d.DocumentTypeName).HasMaxLength(150);
            entity.Property(d => d.FileName).HasMaxLength(255).IsRequired();
            entity.Property(d => d.StorageUri).HasMaxLength(500).IsRequired();
            entity.Property(d => d.ContentType).HasMaxLength(100);
            entity.Property(d => d.Sha256Hash).HasMaxLength(100);
            entity.Property(d => d.UploadedByUserId).HasMaxLength(100);
            entity.Property(d => d.UploadedByUserName).HasMaxLength(150);

            entity.HasIndex(d => new { d.TargetEntityName, d.TargetEntityId });
            entity.HasIndex(d => d.DocumentTypeCode);
            entity.HasIndex(d => d.UploadDate);
        });

        // DocumentRequirementRule
        modelBuilder.Entity<DocumentRequirementRule>(entity =>
        {
            entity.ToTable("DocumentRequirementRule");
            entity.Property(r => r.WorkflowProcessCode).HasMaxLength(50).IsRequired();
            entity.Property(r => r.DocumentTypeCode).HasMaxLength(50).IsRequired();
            entity.Property(r => r.DocumentTypeName).HasMaxLength(150).IsRequired();
            entity.Property(r => r.Description).HasMaxLength(300);

            entity.HasIndex(r => r.WorkflowProcessCode);
            entity.HasIndex(r => r.DocumentTypeCode);
        });

        // Phase 4: Financial Governance Configurations
        modelBuilder.Entity<GrantMoa>(entity =>
        {
            entity.ToTable("GrantMoa", t => t.HasCheckConstraint("CK_GrantMoa_Dates", "[ContractEndDate] >= [ContractStartDate]"));
            entity.Property(m => m.MoaNumber).HasMaxLength(100).IsRequired();
            entity.Property(m => m.MoaStatusCode).HasMaxLength(50).IsRequired();
            entity.Property(m => m.TotalContractValue).HasColumnType("decimal(18,2)").IsRequired();
            entity.Property(m => m.SignoffDocumentUri).HasMaxLength(500);
            entity.Property(m => m.SpecialConditions).HasMaxLength(2000);

            entity.HasOne(m => m.GrantApplication)
                .WithMany()
                .HasForeignKey(m => m.GrantApplicationId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(m => m.MoaTemplate)
                .WithMany(t => t.GrantMoas)
                .HasForeignKey(m => m.MoaTemplateId)
                .OnDelete(DeleteBehavior.SetNull);

            entity.HasIndex(m => m.GrantApplicationId);
            entity.HasIndex(m => m.MoaTemplateId);
            entity.HasIndex(m => m.MoaNumber).IsUnique();
            entity.HasIndex(m => m.MoaStatusCode);
        });

        // MoA Template & Reusable Clause Engine Configurations (Option A)
        modelBuilder.Entity<MoaTemplate>(entity =>
        {
            entity.ToTable("MoaTemplate");
            entity.Property(t => t.TemplateCode).HasMaxLength(100).IsRequired();
            entity.Property(t => t.TemplateTitle).HasMaxLength(200).IsRequired();
            entity.Property(t => t.GrantTypeCode).HasMaxLength(50).IsRequired();
            entity.Property(t => t.LegalEntityType).HasMaxLength(50).IsRequired();
            entity.Property(t => t.VersionNumber).HasMaxLength(20).IsRequired();
            entity.Property(t => t.ApprovalStatus).HasMaxLength(50).IsRequired();
            entity.Property(t => t.ApprovedBy).HasMaxLength(100);

            entity.HasIndex(t => t.TemplateCode).IsUnique();
            entity.HasIndex(t => new { t.FinancialYear, t.GrantTypeCode, t.IsActive });
            entity.HasIndex(t => t.ApprovalStatus);
        });

        modelBuilder.Entity<MoaClause>(entity =>
        {
            entity.ToTable("MoaClause");
            entity.Property(c => c.ClauseCode).HasMaxLength(100).IsRequired();
            entity.Property(c => c.ClauseTitle).HasMaxLength(200).IsRequired();
            entity.Property(c => c.Category).HasMaxLength(50).IsRequired();
            entity.Property(c => c.ClauseContent).HasColumnType("nvarchar(max)").IsRequired();

            entity.HasIndex(c => c.ClauseCode).IsUnique();
            entity.HasIndex(c => c.Category);
            entity.HasIndex(c => c.IsActive);
        });

        modelBuilder.Entity<MoaTemplateSection>(entity =>
        {
            entity.ToTable("MoaTemplateSection");
            entity.Property(s => s.SectionNumber).HasMaxLength(50).IsRequired();
            entity.Property(s => s.SectionTitle).HasMaxLength(200).IsRequired();
            entity.Property(s => s.ConditionRuleJson).HasMaxLength(1000);

            entity.HasOne(s => s.MoaTemplate)
                .WithMany(t => t.Sections)
                .HasForeignKey(s => s.MoaTemplateId)
                .OnDelete(DeleteBehavior.Cascade);

            entity.HasOne(s => s.MoaClause)
                .WithMany(c => c.TemplateSections)
                .HasForeignKey(s => s.MoaClauseId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(s => s.MoaTemplateId);
            entity.HasIndex(s => s.MoaClauseId);
            entity.HasIndex(s => new { s.MoaTemplateId, s.SequenceOrder });
        });

        modelBuilder.Entity<MoaExecutionSnapshot>(entity =>
        {
            entity.ToTable("MoaExecutionSnapshot");
            entity.Property(s => s.TemplateVersionNumber).HasMaxLength(20).IsRequired();
            entity.Property(s => s.RenderedContentHash).HasMaxLength(100).IsRequired();
            entity.Property(s => s.RenderedContent).HasColumnType("nvarchar(max)").IsRequired();
            entity.Property(s => s.PdfStorageUri).HasMaxLength(500);
            entity.Property(s => s.SignatoryEmployer).HasMaxLength(150);
            entity.Property(s => s.SignatorySeta).HasMaxLength(150);

            entity.HasOne(s => s.GrantMoa)
                .WithMany(m => m.ExecutionSnapshots)
                .HasForeignKey(s => s.GrantMoaId)
                .OnDelete(DeleteBehavior.Cascade);

            entity.HasOne(s => s.MoaTemplate)
                .WithMany(t => t.ExecutionSnapshots)
                .HasForeignKey(s => s.MoaTemplateId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(s => s.GrantMoaId);
            entity.HasIndex(s => s.MoaTemplateId);
            entity.HasIndex(s => s.RenderedContentHash);
            entity.HasIndex(s => s.FrozenAt);
        });

        modelBuilder.Entity<DocumentTemplate>(entity =>
        {
            entity.ToTable("DocumentTemplate");
            entity.Property(t => t.TemplateCode).HasMaxLength(50).IsRequired();
            entity.Property(t => t.TemplateTitle).HasMaxLength(200).IsRequired();
            entity.Property(t => t.DocumentCategory).HasMaxLength(50).IsRequired();
            entity.Property(t => t.DocumentTypeCode).HasMaxLength(50).IsRequired();
            entity.Property(t => t.TargetEntityType).HasMaxLength(50).IsRequired();
            entity.Property(t => t.VersionNumber).HasMaxLength(20).IsRequired();
            entity.Property(t => t.ApprovalStatus).HasMaxLength(30).IsRequired();
            entity.Property(t => t.HeaderBannerUrl).HasMaxLength(500);
            entity.Property(t => t.FooterDisclaimerText).HasMaxLength(500);
            entity.Property(t => t.ApprovedBy).HasMaxLength(100);

            entity.HasIndex(t => t.TemplateCode).IsUnique();
            entity.HasIndex(t => new { t.DocumentCategory, t.DocumentTypeCode, t.FinancialYear, t.IsActive });
            entity.HasIndex(t => t.ApprovalStatus);
        });

        modelBuilder.Entity<DocumentClause>(entity =>
        {
            entity.ToTable("DocumentClause");
            entity.Property(c => c.ClauseCode).HasMaxLength(50).IsRequired();
            entity.Property(c => c.ClauseTitle).HasMaxLength(200).IsRequired();
            entity.Property(c => c.Category).HasMaxLength(50).IsRequired();
            entity.Property(c => c.ClauseContent).HasColumnType("nvarchar(max)").IsRequired();

            entity.HasIndex(c => c.ClauseCode).IsUnique();
            entity.HasIndex(c => c.Category);
            entity.HasIndex(c => c.IsActive);
        });

        modelBuilder.Entity<DocumentTemplateSection>(entity =>
        {
            entity.ToTable("DocumentTemplateSection");
            entity.Property(s => s.SectionNumber).HasMaxLength(30).IsRequired();
            entity.Property(s => s.SectionTitle).HasMaxLength(200).IsRequired();
            entity.Property(s => s.ConditionRuleJson).HasColumnType("nvarchar(max)");

            entity.HasOne(s => s.DocumentTemplate)
                .WithMany(t => t.Sections)
                .HasForeignKey(s => s.DocumentTemplateId)
                .OnDelete(DeleteBehavior.Cascade);

            entity.HasOne(s => s.DocumentClause)
                .WithMany(c => c.TemplateSections)
                .HasForeignKey(s => s.DocumentClauseId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(s => s.DocumentTemplateId);
            entity.HasIndex(s => s.DocumentClauseId);
            entity.HasIndex(s => new { s.DocumentTemplateId, s.SequenceOrder });
        });

        modelBuilder.Entity<DocumentSnapshot>(entity =>
        {
            entity.ToTable("DocumentSnapshot");
            entity.Property(s => s.DocumentSnapshotNumber).HasMaxLength(50).IsRequired();
            entity.Property(s => s.DocumentTypeCode).HasMaxLength(50).IsRequired();
            entity.Property(s => s.TemplateVersionNumber).HasMaxLength(20).IsRequired();
            entity.Property(s => s.RelatedEntityType).HasMaxLength(50).IsRequired();
            entity.Property(s => s.RecipientName).HasMaxLength(200).IsRequired();
            entity.Property(s => s.RecipientIdentifier).HasMaxLength(50).IsRequired();
            entity.Property(s => s.RenderedContentHash).HasMaxLength(100).IsRequired();
            entity.Property(s => s.RenderedContent).HasColumnType("nvarchar(max)").IsRequired();
            entity.Property(s => s.VerificationQrBase64).HasColumnType("nvarchar(max)");
            entity.Property(s => s.VerificationUri).HasMaxLength(500).IsRequired();
            entity.Property(s => s.PdfStorageUri).HasMaxLength(500);
            entity.Property(s => s.IssuedBy).HasMaxLength(100).IsRequired();
            entity.Property(s => s.SignatoryName).HasMaxLength(150);
            entity.Property(s => s.SignatoryTitle).HasMaxLength(150);
            entity.Property(s => s.RevocationReason).HasMaxLength(500);

            entity.HasOne(s => s.DocumentTemplate)
                .WithMany(t => t.Snapshots)
                .HasForeignKey(s => s.DocumentTemplateId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(s => s.DocumentSnapshotNumber).IsUnique();
            entity.HasIndex(s => s.RenderedContentHash);
            entity.HasIndex(s => new { s.RelatedEntityType, s.RelatedEntityId });
            entity.HasIndex(s => s.DocumentTypeCode);
            entity.HasIndex(s => s.IssuedAt);
            entity.HasIndex(s => s.RecipientIdentifier);
        });

        modelBuilder.Entity<GrantMoaMilestone>(entity =>
        {
            entity.ToTable("GrantMoaMilestone");
            entity.Property(m => m.MilestoneTitle).HasMaxLength(200).IsRequired();
            entity.Property(m => m.MilestoneStatusCode).HasMaxLength(50).IsRequired();
            entity.Property(m => m.TranchePercentage).HasColumnType("decimal(5,2)").IsRequired();
            entity.Property(m => m.TrancheAmount).HasColumnType("decimal(18,2)").IsRequired();
            entity.Property(m => m.DeliverableRequirement).HasMaxLength(1000);
            entity.Property(m => m.VerificationComments).HasMaxLength(1000);

            entity.HasOne(m => m.GrantMoa)
                .WithMany(m => m.Milestones)
                .HasForeignKey(m => m.GrantMoaId)
                .OnDelete(DeleteBehavior.Cascade);

            entity.HasIndex(m => m.GrantMoaId);
            entity.HasIndex(m => m.MilestoneStatusCode);
        });

        modelBuilder.Entity<GrantTranchePayment>(entity =>
        {
            entity.ToTable("GrantTranchePayment");
            entity.Property(p => p.PaymentReferenceNumber).HasMaxLength(100).IsRequired();
            entity.Property(p => p.InvoiceNumber).HasMaxLength(100).IsRequired();
            entity.Property(p => p.ClaimedAmount).HasColumnType("decimal(18,2)").IsRequired();
            entity.Property(p => p.ApprovedPaymentAmount).HasColumnType("decimal(18,2)").IsRequired();
            entity.Property(p => p.PaymentStatusCode).HasMaxLength(50).IsRequired();
            entity.Property(p => p.BatchNumber).HasMaxLength(100);
            entity.Property(p => p.BankReference).HasMaxLength(100);
            entity.Property(p => p.ApprovalComments).HasMaxLength(1000);

            entity.HasOne(p => p.GrantMoaMilestone)
                .WithMany(m => m.Payments)
                .HasForeignKey(p => p.GrantMoaMilestoneId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(p => p.GrantApplication)
                .WithMany()
                .HasForeignKey(p => p.GrantApplicationId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(p => p.GrantMoaMilestoneId);
            entity.HasIndex(p => p.GrantApplicationId);
            entity.HasIndex(p => p.PaymentReferenceNumber).IsUnique();
            entity.HasIndex(p => p.PaymentStatusCode);
        });

        modelBuilder.Entity<MandatoryGrantDisbursement>(entity =>
        {
            entity.ToTable("MandatoryGrantDisbursement");
            entity.Property(d => d.DisbursementReference).HasMaxLength(100).IsRequired();
            entity.Property(d => d.LeviesReceivedAmount).HasColumnType("decimal(18,2)").IsRequired();
            entity.Property(d => d.CalculatedRebateAmount).HasColumnType("decimal(18,2)").IsRequired();
            entity.Property(d => d.DisbursementStatusCode).HasMaxLength(50).IsRequired();
            entity.Property(d => d.BatchNumber).HasMaxLength(100);
            entity.Property(d => d.BankAccountSnapshot).HasMaxLength(200);
            entity.Property(d => d.Comments).HasMaxLength(1000);

            entity.HasOne(d => d.WspSubmission)
                .WithMany()
                .HasForeignKey(d => d.WspSubmissionId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(d => d.Organisation)
                .WithMany()
                .HasForeignKey(d => d.OrganisationId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(d => d.WspSubmissionId);
            entity.HasIndex(d => d.OrganisationId);
            entity.HasIndex(d => d.DisbursementReference).IsUnique();
            entity.HasIndex(d => d.DisbursementStatusCode);

            entity.HasQueryFilter(d => _tenantProvider.IsAdmin || _tenantProvider.CurrentOrganisationId == null || d.OrganisationId == _tenantProvider.CurrentOrganisationId);
        });

        modelBuilder.Entity<InterSetaTransfer>(entity =>
        {
            entity.ToTable("InterSetaTransfer");
            entity.Property(t => t.TransferType).HasMaxLength(50).IsRequired();
            entity.Property(t => t.OtherSetaCode).HasMaxLength(50).IsRequired();
            entity.Property(t => t.OtherSetaName).HasMaxLength(150).IsRequired();
            entity.Property(t => t.TransferReason).HasMaxLength(500).IsRequired();
            entity.Property(t => t.TransferStatusCode).HasMaxLength(50).IsRequired();
            entity.Property(t => t.TransferAmount).HasColumnType("decimal(18,2)").IsRequired();
            entity.Property(t => t.SetaApprovalReference).HasMaxLength(100);
            entity.Property(t => t.DhetReferenceNumber).HasMaxLength(100);
            entity.Property(t => t.Comments).HasMaxLength(1000);

            entity.HasOne(t => t.Organisation)
                .WithMany()
                .HasForeignKey(t => t.OrganisationId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(t => t.OrganisationId);
            entity.HasIndex(t => t.TransferStatusCode);
            entity.HasIndex(t => t.OtherSetaCode);
        });


        // System Configuration & Feature Flags
        modelBuilder.Entity<SystemConfig>(entity =>
        {
            entity.ToTable("SystemConfig");
            entity.Property(c => c.ConfigKey).HasMaxLength(150).IsRequired();
            entity.Property(c => c.ConfigCategory).HasMaxLength(50).IsRequired();
            entity.Property(c => c.DataType).HasMaxLength(30).IsRequired();
            entity.Property(c => c.Description).HasMaxLength(500);

            entity.HasIndex(c => c.ConfigKey).IsUnique();
            entity.HasIndex(c => c.ConfigCategory);
            entity.HasIndex(c => c.IsActive);
        });

        modelBuilder.Entity<SystemFeatureFlag>(entity =>
        {
            entity.ToTable("SystemFeatureFlag");
            entity.Property(f => f.FeatureKey).HasMaxLength(150).IsRequired();
            entity.Property(f => f.FeatureName).HasMaxLength(150).IsRequired();
            entity.Property(f => f.FeatureCategory).HasMaxLength(50).IsRequired();
            entity.Property(f => f.Description).HasMaxLength(500);

            entity.HasIndex(f => f.FeatureKey).IsUnique();
            entity.HasIndex(f => f.FeatureCategory);
            entity.HasIndex(f => f.IsEnabled);
        });

        modelBuilder.Entity<SystemNotification>(entity =>
        {
            entity.ToTable("SystemNotification");
            entity.Property(n => n.Title).HasMaxLength(200).IsRequired();
            entity.Property(n => n.Message).HasMaxLength(1000).IsRequired();
            entity.Property(n => n.RecipientUsername).HasMaxLength(150);
            entity.Property(n => n.RecipientRole).HasMaxLength(100);
            entity.Property(n => n.ActionUrl).HasMaxLength(300);
            entity.Property(n => n.NotificationType).HasMaxLength(50).IsRequired();
            entity.Property(n => n.Severity).HasMaxLength(30).IsRequired();

            entity.HasIndex(n => n.RecipientUsername);
            entity.HasIndex(n => n.RecipientRole);
            entity.HasIndex(n => n.IsRead);
            entity.HasIndex(n => n.CreatedAt);
        });

        // Document & File Attachments
        modelBuilder.Entity<DocumentAttachment>(entity =>
        {
            entity.ToTable("DocumentAttachment");
            entity.Property(d => d.TargetEntityName).HasMaxLength(100).IsRequired();
            entity.Property(d => d.FileName).HasMaxLength(255).IsRequired();
            entity.Property(d => d.OriginalFileName).HasMaxLength(255).IsRequired();
            entity.Property(d => d.ContentType).HasMaxLength(100).IsRequired();
            entity.Property(d => d.StorageProvider).HasMaxLength(50).IsRequired();
            entity.Property(d => d.StoragePath).HasMaxLength(500).IsRequired();
            entity.Property(d => d.FileHashSha256).HasMaxLength(100);
            entity.Property(d => d.DocumentCategoryCode).HasMaxLength(50);

            entity.HasIndex(d => new { d.TargetEntityName, d.TargetEntityId });
            entity.HasIndex(d => d.DocumentCategoryCode);
            entity.HasIndex(d => d.IsArchived);
        });

        // Learner Lifecycle Transitions
        modelBuilder.Entity<CompanyLearnerTransfer>(entity =>
        {
            entity.ToTable("CompanyLearnerTransfer");
            entity.Property(t => t.TransferReasonCode).HasMaxLength(50).IsRequired();
            entity.Property(t => t.TransferStatusCode).HasMaxLength(50).IsRequired();
            entity.Property(t => t.ApprovalComments).HasMaxLength(1000);
            entity.Property(t => t.ApprovedByUserId).HasMaxLength(100);

            entity.HasOne(t => t.CompanyLearner)
                .WithMany()
                .HasForeignKey(t => t.CompanyLearnerId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(t => t.FromOrganisation)
                .WithMany()
                .HasForeignKey(t => t.FromOrganisationId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(t => t.ToOrganisation)
                .WithMany()
                .HasForeignKey(t => t.ToOrganisationId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(t => t.CompanyLearnerId);
            entity.HasIndex(t => t.FromOrganisationId);
            entity.HasIndex(t => t.ToOrganisationId);
            entity.HasIndex(t => t.TransferStatusCode);
        });

        modelBuilder.Entity<CompanyLearnerLostTime>(entity =>
        {
            entity.ToTable("CompanyLearnerLostTime");
            entity.Property(l => l.LostTimeReasonCode).HasMaxLength(50).IsRequired();
            entity.Property(l => l.LostTimeStatusCode).HasMaxLength(50).IsRequired();
            entity.Property(l => l.ApprovalComments).HasMaxLength(1000);
            entity.Property(l => l.ApprovedByUserId).HasMaxLength(100);

            entity.HasOne(l => l.CompanyLearner)
                .WithMany()
                .HasForeignKey(l => l.CompanyLearnerId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(l => l.CompanyLearnerId);
            entity.HasIndex(l => l.LostTimeStatusCode);
        });

        modelBuilder.Entity<CompanyLearnerTermination>(entity =>
        {
            entity.ToTable("CompanyLearnerTermination");
            entity.Property(t => t.TerminationReasonCode).HasMaxLength(50).IsRequired();
            entity.Property(t => t.TerminationStatusCode).HasMaxLength(50).IsRequired();
            entity.Property(t => t.UnionRepresentativeName).HasMaxLength(150);
            entity.Property(t => t.SettlementNotes).HasMaxLength(2000);
            entity.Property(t => t.ApprovalComments).HasMaxLength(1000);
            entity.Property(t => t.ApprovedByUserId).HasMaxLength(100);

            entity.HasOne(t => t.CompanyLearner)
                .WithMany()
                .HasForeignKey(t => t.CompanyLearnerId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(t => t.CompanyLearnerId);
            entity.HasIndex(t => t.TerminationStatusCode);
        });

        // Workplace Monitoring & Quality Assurance Site Visits (Cluster 1)
        modelBuilder.Entity<WorkplaceMonitoringSiteVisit>(entity =>
        {
            entity.ToTable("WorkplaceMonitoringSiteVisit");
            entity.Property(v => v.MonitoringStatusCode).HasMaxLength(50).IsRequired();
            entity.Property(v => v.CloUserId).HasMaxLength(100);
            entity.Property(v => v.CrmUserId).HasMaxLength(100);
            entity.Property(v => v.ApprovedByUserId).HasMaxLength(100);
            entity.Property(v => v.ApprovalComments).HasMaxLength(2000);
            entity.Property(v => v.NonComplianceNotes).HasMaxLength(2000);

            entity.HasOne(v => v.Organisation)
                .WithMany()
                .HasForeignKey(v => v.OrganisationId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(v => v.ContactPerson)
                .WithMany()
                .HasForeignKey(v => v.ContactPersonId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(v => v.OrganisationId);
            entity.HasIndex(v => v.ContactPersonId);
            entity.HasIndex(v => v.MonitoringDate);
            entity.HasIndex(v => v.MonitoringStatusCode);
            entity.HasIndex(v => v.NonCompliancesIdentified);
        });

        modelBuilder.Entity<WorkplaceMonitoringComplianceSurvey>(entity =>
        {
            entity.ToTable("WorkplaceMonitoringComplianceSurvey");
            entity.Property(s => s.SurveyCategory).HasMaxLength(50).IsRequired();
            entity.Property(s => s.QuestionText).HasMaxLength(500).IsRequired();
            entity.Property(s => s.Answer).HasMaxLength(20).IsRequired();
            entity.Property(s => s.Comments).HasMaxLength(1000);

            entity.HasOne(s => s.WorkplaceMonitoringSiteVisit)
                .WithMany(v => v.ComplianceSurveys)
                .HasForeignKey(s => s.WorkplaceMonitoringSiteVisitId)
                .OnDelete(DeleteBehavior.Cascade);

            entity.HasIndex(s => s.WorkplaceMonitoringSiteVisitId);
            entity.HasIndex(s => s.SurveyCategory);
            entity.HasIndex(s => s.NonComplianceRisk);
        });

        modelBuilder.Entity<WorkplaceMonitoringActionPlan>(entity =>
        {
            entity.ToTable("WorkplaceMonitoringActionPlan");
            entity.Property(a => a.ValidationTypeCode).HasMaxLength(50).IsRequired();
            entity.Property(a => a.Criteria).HasMaxLength(500).IsRequired();
            entity.Property(a => a.ActionRequired).HasMaxLength(2000).IsRequired();
            entity.Property(a => a.ResponsiblePersonName).HasMaxLength(150).IsRequired();
            entity.Property(a => a.StatusCode).HasMaxLength(50).IsRequired();
            entity.Property(a => a.ResolutionNotes).HasMaxLength(2000);

            entity.HasOne(a => a.WorkplaceMonitoringSiteVisit)
                .WithMany(v => v.ActionPlans)
                .HasForeignKey(a => a.WorkplaceMonitoringSiteVisitId)
                .OnDelete(DeleteBehavior.Cascade);

            entity.HasIndex(a => a.WorkplaceMonitoringSiteVisitId);
            entity.HasIndex(a => a.ValidationTypeCode);
            entity.HasIndex(a => a.StatusCode);
            entity.HasIndex(a => a.IsAtRisk);
        });

        modelBuilder.Entity<WorkplaceMonitoringMitigationPlan>(entity =>
        {
            entity.ToTable("WorkplaceMonitoringMitigationPlan");
            entity.Property(m => m.IdentifiedRisk).HasMaxLength(500).IsRequired();
            entity.Property(m => m.MitigationSteps).HasMaxLength(2000).IsRequired();
            entity.Property(m => m.StatusCode).HasMaxLength(50).IsRequired();
            entity.Property(m => m.ReviewerNotes).HasMaxLength(2000);

            entity.HasOne(m => m.WorkplaceMonitoringSiteVisit)
                .WithMany(v => v.MitigationPlans)
                .HasForeignKey(m => m.WorkplaceMonitoringSiteVisitId)
                .OnDelete(DeleteBehavior.Cascade);

            entity.HasOne(m => m.WorkplaceMonitoringActionPlan)
                .WithMany()
                .HasForeignKey(m => m.WorkplaceMonitoringActionPlanId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(m => m.WorkplaceMonitoringSiteVisitId);
            entity.HasIndex(m => m.WorkplaceMonitoringActionPlanId);
            entity.HasIndex(m => m.StatusCode);
        });

        modelBuilder.Entity<WorkplaceMonitoringLearnerSurvey>(entity =>
        {
            entity.ToTable("WorkplaceMonitoringLearnerSurvey");
            entity.Property(l => l.LearnerName).HasMaxLength(150).IsRequired();
            entity.Property(l => l.LearnerComments).HasMaxLength(2000);

            entity.HasOne(l => l.WorkplaceMonitoringSiteVisit)
                .WithMany(v => v.LearnerSurveys)
                .HasForeignKey(l => l.WorkplaceMonitoringSiteVisitId)
                .OnDelete(DeleteBehavior.Cascade);

            entity.HasOne(l => l.CompanyLearner)
                .WithMany()
                .HasForeignKey(l => l.CompanyLearnerId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(l => l.WorkplaceMonitoringSiteVisitId);
            entity.HasIndex(l => l.CompanyLearnerId);
        });

        // Governance, Review Committees & Accreditation Scope (Cluster 2)
        modelBuilder.Entity<ReviewCommitteeMeeting>(entity =>
        {
            entity.ToTable("ReviewCommitteeMeeting");
            entity.Property(m => m.Title).HasMaxLength(300).IsRequired();
            entity.Property(m => m.MeetingTypeCode).HasMaxLength(50).IsRequired();
            entity.Property(m => m.MeetingNumber).HasMaxLength(50).IsRequired();
            entity.Property(m => m.Venue).HasMaxLength(250).IsRequired();
            entity.Property(m => m.StatusCode).HasMaxLength(50).IsRequired();
            entity.Property(m => m.ChairpersonUserId).HasMaxLength(100);
            entity.Property(m => m.AdditionalInfo).HasMaxLength(2000);

            entity.HasIndex(m => m.MeetingTypeCode);
            entity.HasIndex(m => m.FromDateTime);
            entity.HasIndex(m => m.StatusCode);
        });

        modelBuilder.Entity<ReviewCommitteeMeetingAgenda>(entity =>
        {
            entity.ToTable("ReviewCommitteeMeetingAgenda");
            entity.Property(a => a.Title).HasMaxLength(300).IsRequired();
            entity.Property(a => a.DecisionCode).HasMaxLength(50).IsRequired();
            entity.Property(a => a.TargetEntityName).HasMaxLength(100);
            entity.Property(a => a.Description).HasMaxLength(2000);
            entity.Property(a => a.DecisionNotes).HasMaxLength(2000);

            entity.HasOne(a => a.ReviewCommitteeMeeting)
                .WithMany(m => m.Agendas)
                .HasForeignKey(a => a.ReviewCommitteeMeetingId)
                .OnDelete(DeleteBehavior.Cascade);

            entity.HasIndex(a => a.ReviewCommitteeMeetingId);
            entity.HasIndex(a => a.DecisionCode);
        });

        modelBuilder.Entity<ReviewCommitteeMeetingMember>(entity =>
        {
            entity.ToTable("ReviewCommitteeMeetingMember");
            entity.Property(m => m.RoleInMeeting).HasMaxLength(50).IsRequired();

            entity.HasOne(m => m.ReviewCommitteeMeeting)
                .WithMany(m => m.Members)
                .HasForeignKey(m => m.ReviewCommitteeMeetingId)
                .OnDelete(DeleteBehavior.Cascade);

            entity.HasOne(m => m.Person)
                .WithMany()
                .HasForeignKey(m => m.PersonId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(m => m.ReviewCommitteeMeetingId);
            entity.HasIndex(m => m.PersonId);
        });

        modelBuilder.Entity<AssessorModeratorApplication>(entity =>
        {
            entity.ToTable("AssessorModeratorApplication");
            entity.Property(a => a.ApplicationTypeCode).HasMaxLength(50).IsRequired();
            entity.Property(a => a.ApplicationNumber).HasMaxLength(50).IsRequired();
            entity.Property(a => a.StatusCode).HasMaxLength(50).IsRequired();
            entity.Property(a => a.CertificateNumber).HasMaxLength(50);

            entity.HasOne(a => a.Person)
                .WithMany()
                .HasForeignKey(a => a.PersonId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(a => a.ReviewCommitteeMeetingAgenda)
                .WithMany()
                .HasForeignKey(a => a.ReviewCommitteeMeetingAgendaId)
                .OnDelete(DeleteBehavior.SetNull);

            entity.HasIndex(a => a.PersonId);
            entity.HasIndex(a => a.ApplicationNumber);
            entity.HasIndex(a => a.StatusCode);
        });

        modelBuilder.Entity<SdpScopeExtensionApplication>(entity =>
        {
            entity.ToTable("SdpScopeExtensionApplication");
            entity.Property(s => s.ApplicationNumber).HasMaxLength(50).IsRequired();
            entity.Property(s => s.StatusCode).HasMaxLength(50).IsRequired();
            entity.Property(s => s.QualificationTitle).HasMaxLength(250);
            entity.Property(s => s.RecommendationNotes).HasMaxLength(2000);

            entity.HasOne(s => s.TrainingProvider)
                .WithMany()
                .HasForeignKey(s => s.TrainingProviderId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(s => s.ReviewCommitteeMeetingAgenda)
                .WithMany()
                .HasForeignKey(s => s.ReviewCommitteeMeetingAgendaId)
                .OnDelete(DeleteBehavior.SetNull);

            entity.HasIndex(s => s.TrainingProviderId);
            entity.HasIndex(s => s.StatusCode);
        });

        // DG Project Implementation Plans & Payment Claims (Cluster 3)
        modelBuilder.Entity<ProjectImplementationPlan>(entity =>
        {
            entity.ToTable("ProjectImplementationPlan");
            entity.Property(p => p.PlanReferenceNumber).HasMaxLength(50).IsRequired();
            entity.Property(p => p.InterventionTypeCode).HasMaxLength(50).IsRequired();
            entity.Property(p => p.StatusCode).HasMaxLength(50).IsRequired();
            entity.Property(p => p.TotalAwardedAmount).HasPrecision(18, 2);
            entity.Property(p => p.RecoverableAmount).HasPrecision(18, 2);

            entity.HasOne(p => p.Organisation)
                .WithMany()
                .HasForeignKey(p => p.OrganisationId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(p => p.FundingWindow)
                .WithMany()
                .HasForeignKey(p => p.FundingWindowId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(p => p.GrantApplication)
                .WithMany()
                .HasForeignKey(p => p.GrantApplicationId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(p => p.OrganisationId);
            entity.HasIndex(p => p.PlanReferenceNumber);
            entity.HasIndex(p => p.StatusCode);
        });

        modelBuilder.Entity<PipLearnerAllocation>(entity =>
        {
            entity.ToTable("PipLearnerAllocation");
            entity.Property(l => l.QualificationTitle).HasMaxLength(250);
            entity.Property(l => l.UnitCost).HasPrecision(18, 2);
            entity.Property(l => l.TotalAllowanceBudget).HasPrecision(18, 2);
            entity.Property(l => l.TotalTuitionBudget).HasPrecision(18, 2);

            entity.HasOne(l => l.ProjectImplementationPlan)
                .WithMany(p => p.Allocations)
                .HasForeignKey(l => l.ProjectImplementationPlanId)
                .OnDelete(DeleteBehavior.Cascade);

            entity.HasIndex(l => l.ProjectImplementationPlanId);
        });

        modelBuilder.Entity<GrantPaymentClaim>(entity =>
        {
            entity.ToTable("GrantPaymentClaim");
            entity.Property(c => c.ClaimNumber).HasMaxLength(50).IsRequired();
            entity.Property(c => c.StatusCode).HasMaxLength(50).IsRequired();
            entity.Property(c => c.DeliverableDescription).HasMaxLength(1000).IsRequired();
            entity.Property(c => c.ApprovedByUserId).HasMaxLength(100);
            entity.Property(c => c.ErpBatchNumber).HasMaxLength(100);
            entity.Property(c => c.ClaimAmount).HasPrecision(18, 2);

            entity.HasOne(c => c.ProjectImplementationPlan)
                .WithMany(p => p.Claims)
                .HasForeignKey(c => c.ProjectImplementationPlanId)
                .OnDelete(DeleteBehavior.Cascade);

            entity.HasIndex(c => c.ProjectImplementationPlanId);
            entity.HasIndex(c => c.ClaimNumber);
            entity.HasIndex(c => c.StatusCode);
        });

        // Training Committees & WSP Disputes (Cluster 4)
        modelBuilder.Entity<TrainingCommittee>(entity =>
        {
            entity.ToTable("TrainingCommittee");
            entity.Property(t => t.CommitteeStatusCode).HasMaxLength(50).IsRequired();

            entity.HasOne(t => t.Organisation)
                .WithMany()
                .HasForeignKey(t => t.OrganisationId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(t => t.OrganisationId);
            entity.HasIndex(t => t.FinancialYear);
            entity.HasIndex(t => t.CommitteeStatusCode);
        });

        modelBuilder.Entity<TrainingCommitteeMember>(entity =>
        {
            entity.ToTable("TrainingCommitteeMember");
            entity.Property(m => m.MemberRoleCode).HasMaxLength(50).IsRequired();
            entity.Property(m => m.Constituency).HasMaxLength(100).IsRequired();

            entity.HasOne(m => m.TrainingCommittee)
                .WithMany(t => t.Members)
                .HasForeignKey(m => m.TrainingCommitteeId)
                .OnDelete(DeleteBehavior.Cascade);

            entity.HasOne(m => m.Person)
                .WithMany()
                .HasForeignKey(m => m.PersonId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(m => m.TrainingCommitteeId);
            entity.HasIndex(m => m.PersonId);
        });

        modelBuilder.Entity<WspDispute>(entity =>
        {
            entity.ToTable("WspDispute");
            entity.Property(d => d.DisputeReferenceNumber).HasMaxLength(50).IsRequired();
            entity.Property(d => d.DisputeReasonCode).HasMaxLength(50).IsRequired();
            entity.Property(d => d.DisputeStatusCode).HasMaxLength(50).IsRequired();
            entity.Property(d => d.Description).HasMaxLength(2000).IsRequired();
            entity.Property(d => d.ResolutionNotes).HasMaxLength(2000);

            entity.HasOne(d => d.Organisation)
                .WithMany()
                .HasForeignKey(d => d.OrganisationId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(d => d.WspSubmission)
                .WithMany()
                .HasForeignKey(d => d.WspSubmissionId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(d => d.OrganisationId);
            entity.HasIndex(d => d.DisputeReferenceNumber);
            entity.HasIndex(d => d.DisputeStatusCode);
        });

        modelBuilder.Entity<WspSkillsGap>(entity =>
        {
            entity.ToTable("WspSkillsGap");
            entity.Property(s => s.OfoCode).HasMaxLength(50).IsRequired();
            entity.Property(s => s.OccupationTitle).HasMaxLength(200).IsRequired();
            entity.Property(s => s.SkillsGapReason).HasMaxLength(1000).IsRequired();

            entity.HasOne(s => s.Organisation)
                .WithMany()
                .HasForeignKey(s => s.OrganisationId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(s => s.OrganisationId);
            entity.HasIndex(s => s.FinancialYear);
            entity.HasIndex(s => s.OfoCode);
        });


        // Trade Test Administration & ARPL (Area 13)
        modelBuilder.Entity<LearnerTradeTestApplication>(entity =>
        {
            entity.ToTable("LearnerTradeTestApplication");
            entity.Property(t => t.ApplicationNumber).HasMaxLength(50).IsRequired();
            entity.Property(t => t.TradeTitle).HasMaxLength(200).IsRequired();
            entity.Property(t => t.ApplicationTypeCode).HasMaxLength(50).IsRequired();
            entity.Property(t => t.StatusCode).HasMaxLength(50).IsRequired();
            entity.Property(t => t.CompetencyStatusCode).HasMaxLength(50);
            entity.Property(t => t.NambSerialNumber).HasMaxLength(100);
            entity.Property(t => t.SerialCertificateNumber).HasMaxLength(100);

            entity.HasOne(t => t.CompanyLearner)
                .WithMany()
                .HasForeignKey(t => t.CompanyLearnerId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(t => t.Person)
                .WithMany()
                .HasForeignKey(t => t.PersonId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(t => t.Organisation)
                .WithMany()
                .HasForeignKey(t => t.OrganisationId)
                .OnDelete(DeleteBehavior.SetNull);

            entity.HasOne(t => t.TrainingProvider)
                .WithMany()
                .HasForeignKey(t => t.TrainingProviderId)
                .OnDelete(DeleteBehavior.SetNull);

            entity.HasIndex(t => t.CompanyLearnerId);
            entity.HasIndex(t => t.PersonId);
            entity.HasIndex(t => t.ApplicationNumber).IsUnique();
            entity.HasIndex(t => t.StatusCode);
            entity.HasIndex(t => t.NambSerialNumber);
            entity.HasIndex(t => t.SerialCertificateNumber);
        });

        modelBuilder.Entity<TradeTestTask>(entity =>
        {
            entity.ToTable("TradeTestTask");
            entity.Property(k => k.TaskTitle).HasMaxLength(200).IsRequired();
            entity.Property(k => k.TotalMarksAvailable).HasPrecision(18, 2);
            entity.Property(k => k.MarksObtained).HasPrecision(18, 2);
            entity.Property(k => k.PassPercentage).HasPrecision(18, 2);
            entity.Property(k => k.PercentageAchieved).HasPrecision(18, 2);

            entity.HasOne(k => k.LearnerTradeTestApplication)
                .WithMany(t => t.Tasks)
                .HasForeignKey(k => k.LearnerTradeTestApplicationId)
                .OnDelete(DeleteBehavior.Cascade);

            entity.HasIndex(k => k.LearnerTradeTestApplicationId);
            entity.HasIndex(k => k.TaskNumber);
        });

        modelBuilder.Entity<ArplTradeTestInformation>(entity =>
        {
            entity.ToTable("ArplTradeTestInformation");
            entity.Property(a => a.PortfolioScorePercentage).HasPrecision(18, 2);
            entity.Property(a => a.ArplRecommendation).HasMaxLength(100).IsRequired();

            entity.HasOne(a => a.LearnerTradeTestApplication)
                .WithOne()
                .HasForeignKey<ArplTradeTestInformation>(a => a.LearnerTradeTestApplicationId)
                .OnDelete(DeleteBehavior.Cascade);

            entity.HasIndex(a => a.LearnerTradeTestApplicationId).IsUnique();
        });

        modelBuilder.Entity<ArplExperienceDetail>(entity =>
        {
            entity.ToTable("ArplExperienceDetail");
            entity.Property(e => e.EmployerName).HasMaxLength(200).IsRequired();
            entity.Property(e => e.JobTitle).HasMaxLength(150).IsRequired();

            entity.HasOne(e => e.LearnerTradeTestApplication)
                .WithMany(t => t.ExperienceDetails)
                .HasForeignKey(e => e.LearnerTradeTestApplicationId)
                .OnDelete(DeleteBehavior.Cascade);

            entity.HasIndex(e => e.LearnerTradeTestApplicationId);
        });

        modelBuilder.Entity<ArplTrainingDetail>(entity =>
        {
            entity.ToTable("ArplTrainingDetail");
            entity.Property(r => r.InstitutionName).HasMaxLength(200).IsRequired();
            entity.Property(r => r.CourseOrModuleTitle).HasMaxLength(200).IsRequired();

            entity.HasOne(r => r.LearnerTradeTestApplication)
                .WithMany(t => t.TrainingDetails)
                .HasForeignKey(r => r.LearnerTradeTestApplicationId)
                .OnDelete(DeleteBehavior.Cascade);

            entity.HasIndex(r => r.LearnerTradeTestApplicationId);
        });

        modelBuilder.Entity<NambDecisionHistory>(entity =>
        {
            entity.ToTable("NambDecisionHistory");
            entity.Property(n => n.NambOfficerName).HasMaxLength(150).IsRequired();
            entity.Property(n => n.DecisionStatusCode).HasMaxLength(50).IsRequired();

            entity.HasOne(n => n.LearnerTradeTestApplication)
                .WithMany(t => t.NambHistories)
                .HasForeignKey(n => n.LearnerTradeTestApplicationId)
                .OnDelete(DeleteBehavior.Cascade);

            entity.HasIndex(n => n.LearnerTradeTestApplicationId);
            entity.HasIndex(n => n.DecisionStatusCode);
        });

        // Summative Assessment Reports & Moderation (Area 14)
        modelBuilder.Entity<SummativeAssessmentReport>(entity =>
        {
            entity.ToTable("SummativeAssessmentReport");
            entity.Property(s => s.ReportNumber).HasMaxLength(50).IsRequired();
            entity.Property(s => s.QualificationTitle).HasMaxLength(250).IsRequired();
            entity.Property(s => s.InterventionTypeCode).HasMaxLength(50).IsRequired();
            entity.Property(s => s.StatusCode).HasMaxLength(50).IsRequired();

            entity.HasOne(s => s.CompanyLearner)
                .WithMany()
                .HasForeignKey(s => s.CompanyLearnerId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(s => s.Person)
                .WithMany()
                .HasForeignKey(s => s.PersonId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(s => s.Organisation)
                .WithMany()
                .HasForeignKey(s => s.OrganisationId)
                .OnDelete(DeleteBehavior.SetNull);

            entity.HasOne(s => s.TrainingProvider)
                .WithMany()
                .HasForeignKey(s => s.TrainingProviderId)
                .OnDelete(DeleteBehavior.SetNull);

            entity.HasOne(s => s.AssessorPerson)
                .WithMany()
                .HasForeignKey(s => s.AssessorPersonId)
                .OnDelete(DeleteBehavior.SetNull);

            entity.HasOne(s => s.InternalModeratorPerson)
                .WithMany()
                .HasForeignKey(s => s.InternalModeratorPersonId)
                .OnDelete(DeleteBehavior.SetNull);

            entity.HasIndex(s => s.CompanyLearnerId);
            entity.HasIndex(s => s.PersonId);
            entity.HasIndex(s => s.ReportNumber).IsUnique();
            entity.HasIndex(s => s.StatusCode);
        });

        modelBuilder.Entity<SummativeAssessmentUnitStandard>(entity =>
        {
            entity.ToTable("SummativeAssessmentUnitStandard");
            entity.Property(u => u.UnitStandardCode).HasMaxLength(50).IsRequired();
            entity.Property(u => u.UnitStandardTitle).HasMaxLength(250).IsRequired();
            entity.Property(u => u.CompetencyStatusCode).HasMaxLength(50).IsRequired();
            entity.Property(u => u.ModerationOutcome).HasMaxLength(50).IsRequired();

            entity.HasOne(u => u.SummativeAssessmentReport)
                .WithMany(r => r.UnitStandardAssessments)
                .HasForeignKey(u => u.SummativeAssessmentReportId)
                .OnDelete(DeleteBehavior.Cascade);

            entity.HasIndex(u => u.SummativeAssessmentReportId);
            entity.HasIndex(u => u.UnitStandardCode);
        });

        modelBuilder.Entity<EisaAssessmentEntry>(entity =>
        {
            entity.ToTable("EisaAssessmentEntry");
            entity.Property(e => e.EisaCenterName).HasMaxLength(200).IsRequired();
            entity.Property(e => e.AssessmentPaperCode).HasMaxLength(50).IsRequired();
            entity.Property(e => e.CompetencyStatusCode).HasMaxLength(50).IsRequired();
            entity.Property(e => e.ScoreAchieved).HasPrecision(18, 2);
            entity.Property(e => e.TotalScorePossible).HasPrecision(18, 2);
            entity.Property(e => e.PercentageScore).HasPrecision(18, 2);

            entity.HasOne(e => e.SummativeAssessmentReport)
                .WithMany(r => r.EisaEntries)
                .HasForeignKey(e => e.SummativeAssessmentReportId)
                .OnDelete(DeleteBehavior.Cascade);

            entity.HasIndex(e => e.SummativeAssessmentReportId);
            entity.HasIndex(e => e.AssessmentPaperCode);
        });

        modelBuilder.Entity<StatementOfResults>(entity =>
        {
            entity.ToTable("StatementOfResults");
            entity.Property(r => r.SorSerialNumber).HasMaxLength(100).IsRequired();
            entity.Property(r => r.TamperProofHashSha256).HasMaxLength(100).IsRequired();
            entity.Property(r => r.IssuedByUserId).HasMaxLength(100).IsRequired();

            entity.HasOne(r => r.SummativeAssessmentReport)
                .WithMany(s => s.StatementOfResultsList)
                .HasForeignKey(r => r.SummativeAssessmentReportId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(r => r.CompanyLearner)
                .WithMany()
                .HasForeignKey(r => r.CompanyLearnerId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(r => r.Person)
                .WithMany()
                .HasForeignKey(r => r.PersonId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(r => r.SummativeAssessmentReportId);
            entity.HasIndex(r => r.CompanyLearnerId);
            entity.HasIndex(r => r.SorSerialNumber).IsUnique();
            entity.HasIndex(r => r.TamperProofHashSha256);
        });

        // Qualifications Curriculum Development & QDF (Area 15)
        modelBuilder.Entity<QualificationsCurriculumDevelopment>(entity =>
        {
            entity.ToTable("QualificationsCurriculumDevelopment");
            entity.Property(q => q.ApplicationNumber).HasMaxLength(50).IsRequired();
            entity.Property(q => q.QualificationTitle).HasMaxLength(250).IsRequired();
            entity.Property(q => q.DevelopmentTypeCode).HasMaxLength(50).IsRequired();
            entity.Property(q => q.StatusCode).HasMaxLength(50).IsRequired();
            entity.Property(q => q.SaqaRegistrationNumber).HasMaxLength(100);

            entity.HasOne(q => q.Organisation)
                .WithMany()
                .HasForeignKey(q => q.OrganisationId)
                .OnDelete(DeleteBehavior.SetNull);

            entity.HasIndex(q => q.ApplicationNumber).IsUnique();
            entity.HasIndex(q => q.StatusCode);
            entity.HasIndex(q => q.OfoCode);
        });

        modelBuilder.Entity<CurriculumWorkingGroupMember>(entity =>
        {
            entity.ToTable("CurriculumWorkingGroupMember");
            entity.Property(m => m.MemberName).HasMaxLength(150).IsRequired();
            entity.Property(m => m.StakeholderRoleTitle).HasMaxLength(100).IsRequired();
            entity.Property(m => m.OrganisationRepresented).HasMaxLength(200);

            entity.HasOne(m => m.QualificationsCurriculumDevelopment)
                .WithMany(q => q.WorkingGroupMembers)
                .HasForeignKey(m => m.QualificationsCurriculumDevelopmentId)
                .OnDelete(DeleteBehavior.Cascade);

            entity.HasIndex(m => m.QualificationsCurriculumDevelopmentId);
        });

        modelBuilder.Entity<SkillsRegistration>(entity =>
        {
            entity.ToTable("SkillsRegistration");
            entity.Property(s => s.NonNqfIntervCode).HasMaxLength(50).IsRequired();
            entity.Property(s => s.NonNqfIntervName).HasMaxLength(200).IsRequired();
            entity.Property(s => s.SubfieldId).HasMaxLength(10);
            entity.Property(s => s.EtqaId).HasMaxLength(10);
            entity.Property(s => s.NonNqfIntervStatusId).HasMaxLength(10);
            entity.Property(s => s.LearningProgrammeTypeId).HasMaxLength(10);

            entity.Ignore(s => s.SkillsProgrammeCode);
            entity.Ignore(s => s.SkillsProgrammeTitle);

            entity.HasOne(s => s.QualificationsCurriculumDevelopment)
                .WithMany(q => q.SkillsRegistrations)
                .HasForeignKey(s => s.QualificationsCurriculumDevelopmentId)
                .OnDelete(DeleteBehavior.Cascade);

            entity.HasIndex(s => s.QualificationsCurriculumDevelopmentId);
            entity.HasIndex(s => s.NonNqfIntervCode);
        });

        // Non-SETA Qualifications & Provider Verification (Area 16)
        modelBuilder.Entity<NonSetaCompany>(entity =>
        {
            entity.ToTable("NonSetaCompany");
            entity.Property(c => c.CompanyName).HasMaxLength(200).IsRequired();
            entity.Property(c => c.PrimarySetaCode).HasMaxLength(50).IsRequired();
            entity.Property(c => c.SdlNumber).HasMaxLength(50);

            entity.HasIndex(c => c.CompanyName);
            entity.HasIndex(c => c.PrimarySetaCode);
            entity.HasIndex(c => c.SdlNumber);
        });

        modelBuilder.Entity<NonSetaQualificationsCompletion>(entity =>
        {
            entity.ToTable("NonSetaQualificationsCompletion");
            entity.Property(n => n.QualificationTitle).HasMaxLength(250).IsRequired();
            entity.Property(n => n.OriginatingSetaCode).HasMaxLength(50).IsRequired();
            entity.Property(n => n.ExternalCertificateNumber).HasMaxLength(100).IsRequired();
            entity.Property(n => n.VerificationStatusCode).HasMaxLength(50).IsRequired();

            entity.HasOne(n => n.Person)
                .WithMany()
                .HasForeignKey(n => n.PersonId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(n => n.NonSetaCompany)
                .WithMany()
                .HasForeignKey(n => n.NonSetaCompanyId)
                .OnDelete(DeleteBehavior.SetNull);

            entity.HasIndex(n => n.PersonId);
            entity.HasIndex(n => n.VerificationStatusCode);
            entity.HasIndex(n => n.ExternalCertificateNumber);
        });

        // Advanced SARS Historical Levy Reconciliation (Area 17)
        modelBuilder.Entity<SarsLevyReconAudit>(entity =>
        {
            entity.ToTable("SarsLevyReconAudit");
            entity.Property(r => r.FinancialYear).HasMaxLength(10).IsRequired();
            entity.Property(r => r.SdlNumber).HasMaxLength(50).IsRequired();
            entity.Property(r => r.DiscrepancyReasonCode).HasMaxLength(50).IsRequired();
            entity.Property(r => r.AuditStatusCode).HasMaxLength(50).IsRequired();
            entity.Property(r => r.TotalSarsLeviesReceived).HasPrecision(18, 2);
            entity.Property(r => r.TotalCalculatedLeviesExpected).HasPrecision(18, 2);
            entity.Property(r => r.VarianceAmount).HasPrecision(18, 2);
            entity.Property(r => r.ClawbackAmount).HasPrecision(18, 2);

            entity.HasOne(r => r.Organisation)
                .WithMany()
                .HasForeignKey(r => r.OrganisationId)
                .OnDelete(DeleteBehavior.SetNull);

            entity.HasIndex(r => r.FinancialYear);
            entity.HasIndex(r => r.SdlNumber);
            entity.HasIndex(r => r.AuditStatusCode);
            entity.HasIndex(r => r.DiscrepancyReasonCode);
        });

        modelBuilder.Entity<SarsSchemeYearCalculation>(entity =>
        {
            entity.ToTable("SarsSchemeYearCalculation");
            entity.Property(s => s.SchemeYear).HasMaxLength(10).IsRequired();
            entity.Property(s => s.MandatoryPercentage).HasPrecision(18, 2);
            entity.Property(s => s.DiscretionaryPercentage).HasPrecision(18, 2);
            entity.Property(s => s.AdminPercentage).HasPrecision(18, 2);
            entity.Property(s => s.QctoPercentage).HasPrecision(18, 2);
            entity.Property(s => s.TotalPercentage).HasPrecision(18, 2);
            entity.Property(s => s.StatusCode).HasMaxLength(50).IsRequired();
            entity.HasIndex(s => s.SchemeYear);
            entity.HasIndex(s => s.StatusCode);
        });

        // Auxiliary Enterprise Entities (Options A, B, C, D)
        modelBuilder.Entity<BankingDetails>(entity =>
        {
            entity.ToTable("BankingDetails");
            entity.HasKey(b => b.Id);
            entity.Property(b => b.BankName).HasMaxLength(100).IsRequired();
            entity.Property(b => b.BranchCode).HasMaxLength(50).IsRequired();
            entity.Property(b => b.AccountNumber).HasMaxLength(50).IsRequired();
            entity.Property(b => b.ApprovalStatusCode).HasMaxLength(50).IsRequired();
            entity.HasIndex(b => b.OrganisationId);
            entity.HasIndex(b => b.TrainingProviderId);
            entity.HasIndex(b => b.ApprovalStatusCode);
        });

        modelBuilder.Entity<BankingDetailsAudit>(entity =>
        {
            entity.ToTable("BankingDetailsAudit");
            entity.HasKey(a => a.Id);
            entity.HasIndex(a => a.BankingDetailsId);
            entity.HasIndex(a => a.ChangedAt);
        });

        modelBuilder.Entity<SdfCompany>(entity =>
        {
            entity.ToTable("SdfCompany");
            entity.HasKey(s => s.Id);
            entity.Property(s => s.SdfTypeCode).HasMaxLength(50).IsRequired();
            entity.Property(s => s.SdfStatusCode).HasMaxLength(50).IsRequired();
            entity.Ignore(s => s.AppointmentStatusCode);
            entity.HasIndex(s => s.OrganisationId);
            entity.HasIndex(s => s.PersonId);
            entity.HasIndex(s => s.SdfStatusCode);
        });

        modelBuilder.Entity<SdfAppointmentHistory>(entity =>
        {
            entity.ToTable("SdfAppointmentHistory");
            entity.HasKey(h => h.Id);
            entity.Property(h => h.PreviousStatusCode).HasMaxLength(50).IsRequired();
            entity.Property(h => h.NewStatusCode).HasMaxLength(50).IsRequired();
            entity.Property(h => h.ChangedByUserId).HasMaxLength(100).IsRequired();
            entity.HasOne(h => h.SdfCompany)
                .WithMany()
                .HasForeignKey(h => h.SdfCompanyId)
                .OnDelete(DeleteBehavior.Cascade);
            entity.HasIndex(h => h.SdfCompanyId);
        });

        modelBuilder.Entity<ContractAddenda>(entity =>
        {
            entity.ToTable("ContractAddenda");
            entity.HasKey(c => c.Id);
            entity.Property(c => c.AddendaNumber).HasMaxLength(50).IsRequired();
            entity.HasIndex(c => c.GrantMoaId);
            entity.HasIndex(c => c.StatusCode);
            entity.HasIndex(c => c.AddendaNumber).IsUnique();
        });

        modelBuilder.Entity<ContractExtensionRequest>(entity =>
        {
            entity.ToTable("ContractExtensionRequest");
            entity.HasKey(c => c.Id);
            entity.Property(c => c.RequestNumber).HasMaxLength(50).IsRequired();
            entity.HasIndex(c => c.GrantMoaId);
            entity.HasIndex(c => c.StatusCode);
        });

        modelBuilder.Entity<ContractTerminationRequest>(entity =>
        {
            entity.ToTable("ContractTerminationRequest");
            entity.HasKey(c => c.Id);
            entity.Property(c => c.TerminationNumber).HasMaxLength(50).IsRequired();
            entity.HasIndex(c => c.GrantMoaId);
            entity.HasIndex(c => c.StatusCode);
        });

        modelBuilder.Entity<SdpExtensionOfScope>(entity =>
        {
            entity.ToTable("SdpExtensionOfScope");
            entity.HasKey(s => s.Id);
            entity.Property(s => s.ApplicationNumber).HasMaxLength(50).IsRequired();
            entity.HasIndex(s => s.TrainingProviderId);
            entity.HasIndex(s => s.StatusCode);
            entity.HasIndex(s => s.ApplicationNumber).IsUnique();
        });

        modelBuilder.Entity<SdpReAccreditationApplication>(entity =>
        {
            entity.ToTable("SdpReAccreditationApplication");
            entity.HasKey(s => s.Id);
            entity.Property(s => s.ApplicationNumber).HasMaxLength(50).IsRequired();
            entity.HasIndex(s => s.TrainingProviderId);
            entity.HasIndex(s => s.StatusCode);
        });

        modelBuilder.Entity<AssessorExtensionOfScope>(entity =>
        {
            entity.ToTable("AssessorExtensionOfScope");
            entity.HasKey(a => a.Id);
            entity.Property(a => a.ApplicationNumber).HasMaxLength(50).IsRequired();
            entity.HasIndex(a => a.AssessorPersonId);
            entity.HasIndex(a => a.StatusCode);
        });

        modelBuilder.Entity<StatutorySubmissionBatch>(entity =>
        {
            entity.ToTable("StatutorySubmissionBatch");
            entity.HasKey(b => b.Id);
            entity.Property(b => b.BatchType).HasMaxLength(20).IsRequired();
            entity.Property(b => b.BatchNumber).HasMaxLength(100).IsRequired();
            entity.Property(b => b.StatusCode).HasMaxLength(50).IsRequired();
            entity.Property(b => b.DigitalSecuritySeal).HasMaxLength(128);
            entity.Property(b => b.ArchiveFileName).HasMaxLength(255);
            entity.Property(b => b.ArchiveStorageUri).HasMaxLength(500);

            entity.HasIndex(b => b.BatchNumber).IsUnique();
            entity.HasIndex(b => b.BatchType);
            entity.HasIndex(b => b.SubmissionYear);
            entity.HasIndex(b => b.StatusCode);
            entity.HasIndex(b => b.ExtractionDate);

            entity.HasMany(b => b.Files)
                .WithOne(f => f.Batch)
                .HasForeignKey(f => f.StatutorySubmissionBatchId)
                .OnDelete(DeleteBehavior.Cascade);
        });

        modelBuilder.Entity<StatutoryBatchFile>(entity =>
        {
            entity.ToTable("StatutoryBatchFile");
            entity.HasKey(f => f.Id);
            entity.Property(f => f.FileCode).HasMaxLength(10).IsRequired();
            entity.Property(f => f.FileTitle).HasMaxLength(150).IsRequired();
            entity.Property(f => f.FileName).HasMaxLength(255).IsRequired();
            entity.Property(f => f.StatusCode).HasMaxLength(50).IsRequired();
            entity.Property(f => f.ChecksumSha256).HasMaxLength(128);

            entity.HasIndex(f => f.StatutorySubmissionBatchId);
            entity.HasIndex(f => f.FileCode);
            entity.HasIndex(f => f.StatusCode);
        });


        // Configure BaseLookupType with Code (varchar 50) as Key in `lookup` schema
        ConfigureLookup<GenderType>(modelBuilder, "GenderType");
        ConfigureLookup<EquityType>(modelBuilder, "EquityType");
        ConfigureLookup<CitizenStatusType>(modelBuilder, "CitizenStatusType");
        ConfigureLookup<CountryType>(modelBuilder, "CountryType");
        ConfigureLookup<NationalityType>(modelBuilder, "NationalityType");
        ConfigureLookup<HomeLanguageType>(modelBuilder, "HomeLanguageType");
        ConfigureLookup<ProvinceType>(modelBuilder, "ProvinceType");
        ConfigureLookup<DisabilityType>(modelBuilder, "DisabilityType");
        ConfigureLookup<CategoryType>(modelBuilder, "CategoryType");
        ConfigureLookup<OrganisationType>(modelBuilder, "OrganisationType");
        ConfigureLookup<CompanySizeType>(modelBuilder, "CompanySizeType");
        ConfigureLookup<SectorType>(modelBuilder, "SectorType");
        ConfigureLookup<ChamberType>(modelBuilder, "ChamberType");
        ConfigureLookup<SicCodeType>(modelBuilder, "SicCodeType");
        modelBuilder.Entity<SicCodeType>(entity =>
        {
            entity.Property(s => s.ChamberCode).HasMaxLength(20);
            entity.Property(s => s.SetaCode).HasMaxLength(10).HasDefaultValue("17");
            entity.HasIndex(s => s.ChamberCode);
            entity.HasIndex(s => s.SetaCode);
        });
        ConfigureLookup<StatusType>(modelBuilder, "StatusType");
        ConfigureLookup<LearningProgrammeType>(modelBuilder, "LearningProgrammeType");
        ConfigureLookup<EnrolmentType>(modelBuilder, "EnrolmentType");
        ConfigureLookup<EnrolmentStatusType>(modelBuilder, "EnrolmentStatusType");
        ConfigureLookup<ProviderType>(modelBuilder, "ProviderType");
        ConfigureLookup<ProviderStatusType>(modelBuilder, "ProviderStatusType");
        ConfigureLookup<LearnerEvidenceType>(modelBuilder, "LearnerEvidenceType");
        ConfigureLookup<GrantTypeType>(modelBuilder, "GrantTypeType");
        ConfigureLookup<InterventionType>(modelBuilder, "InterventionType");
        ConfigureLookup<OfoCodeType>(modelBuilder, "OfoCodeType");
        ConfigureLookup<VisitTypeType>(modelBuilder, "VisitTypeType");
        ConfigureLookup<SiteVisitApprovalStatusType>(modelBuilder, "SiteVisitApprovalStatusType");
        ConfigureLookup<EmployerApprovalStatusType>(modelBuilder, "EmployerApprovalStatusType");

        // SETMIS Expanded Lookups
        ConfigureLookup<AlternateIdType>(modelBuilder, "AlternateIdType");
        ConfigureLookup<EconomicStatusType>(modelBuilder, "EconomicStatusType");
        ConfigureLookup<PopiActStatusType>(modelBuilder, "PopiActStatusType");
        ConfigureLookup<CommunicatingRatingType>(modelBuilder, "CommunicatingRatingType");
        ConfigureLookup<HearingRatingType>(modelBuilder, "HearingRatingType");
        ConfigureLookup<RememberingRatingType>(modelBuilder, "RememberingRatingType");
        ConfigureLookup<SeeingRatingType>(modelBuilder, "SeeingRatingType");
        ConfigureLookup<SelfCareRatingType>(modelBuilder, "SelfCareRatingType");
        ConfigureLookup<WalkingRatingType>(modelBuilder, "WalkingRatingType");
        ConfigureLookup<DesignationType>(modelBuilder, "DesignationType");
        ConfigureLookup<DesignationStructureStatusType>(modelBuilder, "DesignationStructureStatusType");
        ConfigureLookup<EnrolmentStatusReasonType>(modelBuilder, "EnrolmentStatusReasonType");
        ConfigureLookup<InternshipStatusType>(modelBuilder, "InternshipStatusType");
        ConfigureLookup<NonNqfInterventionStatusType>(modelBuilder, "NonNqfInterventionStatusType");
        ConfigureLookup<PartOfType>(modelBuilder, "PartOfType");
        ConfigureLookup<ProviderClassType>(modelBuilder, "ProviderClassType");
        ConfigureLookup<SubfieldType>(modelBuilder, "SubfieldType");
        ConfigureLookup<TradeTestResultType>(modelBuilder, "TradeTestResultType");
        ConfigureLookup<TradeTestResultReasonType>(modelBuilder, "TradeTestResultReasonType");
        ConfigureLookup<StatssaAreaCodeType>(modelBuilder, "StatssaAreaCodeType");
        ConfigureLookup<UrbanRuralType>(modelBuilder, "UrbanRuralType");
        ConfigureLookup<SetaType>(modelBuilder, "SetaType");
        ConfigureLookup<FundingType>(modelBuilder, "FundingType");
        ConfigureLookup<AbetBandType>(modelBuilder, "AbetBandType");
        ConfigureLookup<QualificationTypeType>(modelBuilder, "QualificationTypeType");
        ConfigureLookup<HonoursClassType>(modelBuilder, "HonoursClassType");

        // WSP Qualitative Survey & Strategic Gaps
        modelBuilder.Entity<WspStrategicSkillsGap>(entity =>
        {
            entity.ToTable("WspStrategicSkillsGap");
            entity.Property(w => w.OccupationTitle).HasMaxLength(150).IsRequired();
            entity.Property(w => w.PriorityLevel).HasMaxLength(50).IsRequired();
            entity.HasIndex(w => w.WspId);
            entity.HasIndex(w => w.IsActive);
        });

        modelBuilder.Entity<WspTrainingImpactSurvey>(entity =>
        {
            entity.ToTable("WspTrainingImpactSurvey");
            entity.Property(w => w.SurveyCategory).HasMaxLength(100).IsRequired();
            entity.HasIndex(w => w.WspId);
        });

        modelBuilder.Entity<WspStrategicPriority>(entity =>
        {
            entity.ToTable("WspStrategicPriority");
            entity.Property(w => w.PriorityCode).HasMaxLength(100).IsRequired();
            entity.HasIndex(w => w.WspId);
        });

        // AQP Quality Partners & Assessments
        modelBuilder.Entity<AqpPartner>(entity =>
        {
            entity.ToTable("AqpPartner");
            entity.Property(a => a.AqpName).HasMaxLength(200).IsRequired();
            entity.Property(a => a.AqpCode).HasMaxLength(50).IsRequired();
            entity.Property(a => a.AccreditationNumber).HasMaxLength(100).IsRequired();
            entity.HasIndex(a => a.AqpCode);
            entity.HasIndex(a => a.IsActive);
        });

        modelBuilder.Entity<AqpQualificationScope>(entity =>
        {
            entity.ToTable("AqpQualificationScope");
            entity.Property(a => a.QualificationTitle).HasMaxLength(250).IsRequired();
            entity.HasIndex(a => a.AqpPartnerId);
        });

        modelBuilder.Entity<AqpLearnerAssessment>(entity =>
        {
            entity.ToTable("AqpLearnerAssessment");
            entity.Property(a => a.AssessmentNumber).HasMaxLength(100).IsRequired();
            entity.Property(a => a.EisaExamSession).HasMaxLength(100).IsRequired();
            entity.HasIndex(a => a.AqpPartnerId);
            entity.HasIndex(a => a.CompanyLearnerId);
            entity.HasIndex(a => a.PersonId);
        });

        modelBuilder.Entity<WspSignoffAttestation>(entity =>
        {
            entity.ToTable("WspSignoffAttestation");
            entity.Property(w => w.SignerRoleCode).HasMaxLength(50).IsRequired();
            entity.Property(w => w.SignerFullName).HasMaxLength(250).IsRequired();
            entity.Property(w => w.SignerEmail).HasMaxLength(200).IsRequired();
            entity.Property(w => w.DigitalSecuritySeal).HasMaxLength(128).IsRequired();
            entity.HasIndex(w => w.WspSubmissionId);
            entity.HasIndex(w => w.SignerRoleCode);
            entity.HasIndex(w => w.AttestationStatusCode);
        });

        modelBuilder.Entity<CompanyLearnerChangeRequest>(entity =>
        {
            entity.ToTable("CompanyLearnerChangeRequest");
            entity.Property(c => c.ChangeTypeCode).HasMaxLength(50).IsRequired();
            entity.Property(c => c.ChangeStatusCode).HasMaxLength(50).IsRequired();
            entity.HasIndex(c => c.CompanyLearnerId);
            entity.HasIndex(c => c.ChangeStatusCode);
        });

        modelBuilder.Entity<ErpPaymentBatchHeader>(entity =>
        {
            entity.ToTable("ErpPaymentBatchHeader");
            entity.Property(e => e.BatchNumber).HasMaxLength(100).IsRequired();
            entity.Property(e => e.BatchTypeCode).HasMaxLength(50).IsRequired();
            entity.Property(e => e.TotalAmount).HasPrecision(18, 2);
            entity.HasIndex(e => e.BatchNumber).IsUnique();
            entity.HasIndex(e => e.BatchStatusCode);
        });

        modelBuilder.Entity<ErpPaymentBatchEntry>(entity =>
        {
            entity.ToTable("ErpPaymentBatchEntry");
            entity.Property(e => e.PaymentVoucherNumber).HasMaxLength(100).IsRequired();
            entity.Property(e => e.PaymentAmount).HasPrecision(18, 2);
            entity.HasIndex(e => e.ErpPaymentBatchHeaderId);
            entity.HasIndex(e => e.GrantPaymentClaimId);
            entity.HasIndex(e => e.OrganisationId);
            entity.HasIndex(e => e.PaymentVoucherNumber);
        });

        // AssessorReRegistrationApplication
        modelBuilder.Entity<AssessorReRegistrationApplication>(entity =>
        {
            entity.ToTable("AssessorReRegistrationApplication");
            entity.Property(a => a.ApplicationReferenceNumber).HasMaxLength(50).IsRequired();
            entity.Property(a => a.ApplicationTypeCode).HasMaxLength(50).IsRequired();
            entity.Property(a => a.ReviewStatusCode).HasMaxLength(50).IsRequired();
            entity.Property(a => a.CommitteeDecisionNumber).HasMaxLength(50);
            entity.Property(a => a.DigitalSecuritySeal).HasMaxLength(64);

            entity.HasOne(a => a.EtqaAssessor)
                  .WithMany(ass => ass.ReRegistrationApplications)
                  .HasForeignKey(a => a.EtqaAssessorId)
                  .OnDelete(DeleteBehavior.Cascade);

            entity.HasIndex(a => a.EtqaAssessorId);
            entity.HasIndex(a => a.ReviewStatusCode);
            entity.HasIndex(a => a.ApplicationReferenceNumber).IsUnique();
        });

        // AssessorCpdActivity
        modelBuilder.Entity<AssessorCpdActivity>(entity =>
        {
            entity.ToTable("AssessorCpdActivity");
            entity.Property(c => c.ActivityTitle).HasMaxLength(200).IsRequired();
            entity.Property(c => c.ActivityCategory).HasMaxLength(50).IsRequired();
            entity.Property(c => c.EvidenceDocumentRef).HasMaxLength(250);

            entity.HasOne(c => c.Application)
                  .WithMany(a => a.CpdActivities)
                  .HasForeignKey(c => c.AssessorReRegistrationApplicationId)
                  .OnDelete(DeleteBehavior.Cascade);

            entity.HasIndex(c => c.AssessorReRegistrationApplicationId);
            entity.HasIndex(c => c.ActivityCategory);
        });

        // NambSubmissionBatch
        modelBuilder.Entity<NambSubmissionBatch>(entity =>
        {
            entity.ToTable("NambSubmissionBatch");
            entity.Property(b => b.BatchReferenceNumber).HasMaxLength(50).IsRequired();
            entity.Property(b => b.BatchDescription).HasMaxLength(250).IsRequired();
            entity.Property(b => b.Status).HasMaxLength(50).IsRequired();
            entity.Property(b => b.DigitalSecuritySeal).HasMaxLength(64);

            entity.HasIndex(b => b.BatchReferenceNumber).IsUnique();
            entity.HasIndex(b => b.Status);
        });

        // Phase 7: TrainingProviderCampus & TrainingProviderAssessorLink
        modelBuilder.Entity<TrainingProviderCampus>(entity =>
        {
            entity.ToTable("TrainingProviderCampus");
            entity.Property(c => c.CampusName).HasMaxLength(150).IsRequired();
            entity.Property(c => c.CampusCode).HasMaxLength(50).IsRequired();
            entity.Property(c => c.Status).HasMaxLength(50).IsRequired();

            entity.HasOne(c => c.TrainingProvider)
                  .WithMany(p => p.Campuses)
                  .HasForeignKey(c => c.TrainingProviderId)
                  .OnDelete(DeleteBehavior.Cascade);

            entity.HasIndex(c => c.TrainingProviderId);
            entity.HasIndex(c => c.CampusCode);
        });

        modelBuilder.Entity<TrainingProviderAssessorLink>(entity =>
        {
            entity.ToTable("TrainingProviderAssessorLink");
            entity.Property(l => l.RoleTypeCode).HasMaxLength(50).IsRequired();
            entity.Property(l => l.Status).HasMaxLength(50).IsRequired();

            entity.HasOne(l => l.TrainingProvider)
                  .WithMany(p => p.AssessorLinks)
                  .HasForeignKey(l => l.TrainingProviderId)
                  .OnDelete(DeleteBehavior.Cascade);

            entity.HasOne(l => l.TrainingProviderCampus)
                  .WithMany(c => c.LinkedAssessors)
                  .HasForeignKey(l => l.TrainingProviderCampusId)
                  .OnDelete(DeleteBehavior.NoAction);

            entity.HasOne(l => l.EtqaAssessor)
                  .WithMany()
                  .HasForeignKey(l => l.EtqaAssessorId)
                  .OnDelete(DeleteBehavior.Cascade);

            entity.HasIndex(l => l.TrainingProviderId);
            entity.HasIndex(l => l.TrainingProviderCampusId);
            entity.HasIndex(l => l.EtqaAssessorId);
        });

        // Seed all lookups
        LookupBatchSeeder.SeedAllLookups(modelBuilder);

        // Reflect XML documentation and statutory comments onto all tables and columns
        modelBuilder.ApplyXmlDocumentation();

        // Apply SQL Server System-Versioned Temporal Tables with history schema for all domain entities
        modelBuilder.ApplyTemporalTables();
    }

    private static void ConfigureLookup<T>(ModelBuilder modelBuilder, string tableName) where T : BaseLookupType
    {
        modelBuilder.Entity<T>(entity =>
        {
            entity.ToTable(tableName, schema: "lookup");
            entity.HasKey(x => x.Code);
            entity.Property(x => x.Code).HasMaxLength(50).IsRequired();
            entity.Property(x => x.Name).HasMaxLength(250).IsRequired();
            entity.Property(x => x.Description).HasMaxLength(500);
            entity.HasIndex(x => x.Name);
            entity.HasIndex(x => x.Active);
        });
    }
}
