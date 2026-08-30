using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Common;
using Nsdms.Domain.Entities;
using Nsdms.Domain.Lookups;

namespace Nsdms.Infrastructure.Data;

public class NsdmsDbContext : IdentityDbContext<ApplicationUser, ApplicationRole, int>, INsdmsDbContext
{
    public NsdmsDbContext(DbContextOptions<NsdmsDbContext> options) : base(options) { }

    public DbSet<Organisation> Organisations => Set<Organisation>();
    public DbSet<Person> People => Set<Person>();
    public DbSet<OrganisationContact> OrganisationContacts => Set<OrganisationContact>();
    public DbSet<OrganisationSite> OrganisationSites => Set<OrganisationSite>();
    public DbSet<Visit> Visits => Set<Visit>();
    public DbSet<WspSubmission> WspSubmissions => Set<WspSubmission>();
    public DbSet<LevyFile> LevyFiles => Set<LevyFile>();
    public DbSet<LevyFileLine> LevyFileLines => Set<LevyFileLine>();
    public DbSet<GrantApplication> GrantApplications => Set<GrantApplication>();
    public DbSet<EtqaAssessor> EtqaAssessors => Set<EtqaAssessor>();
    public DbSet<TrainingProvider> TrainingProviders => Set<TrainingProvider>();
    public DbSet<TrainingProviderQualification> TrainingProviderQualifications => Set<TrainingProviderQualification>();
    public DbSet<TrainingProviderUnitStandard> TrainingProviderUnitStandards => Set<TrainingProviderUnitStandard>();
    public DbSet<WspEmploymentSummary> WspEmploymentSummaries => Set<WspEmploymentSummary>();
    public DbSet<WspTrainingPlan> WspTrainingPlans => Set<WspTrainingPlan>();
    public DbSet<GrantFundingWindow> GrantFundingWindows => Set<GrantFundingWindow>();
    public DbSet<GrantProjectBudget> GrantProjectBudgets => Set<GrantProjectBudget>();
    public DbSet<AssessorModeratorScope> AssessorModeratorScopes => Set<AssessorModeratorScope>();
    public DbSet<LearnerAssessment> LearnerAssessments => Set<LearnerAssessment>();
    public DbSet<WorkplaceApproval> WorkplaceApprovals => Set<WorkplaceApproval>();
    public DbSet<WorkplaceApprovalMentor> WorkplaceApprovalMentors => Set<WorkplaceApprovalMentor>();
    public DbSet<WorkplaceApprovalToolList> WorkplaceApprovalToolLists => Set<WorkplaceApprovalToolList>();
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

    // Document Management
    public DbSet<DocumentMetadata> DocumentMetadatas => Set<DocumentMetadata>();
    public DbSet<DocumentRequirementRule> DocumentRequirementRules => Set<DocumentRequirementRule>();

    // Financial Governance, Grant MOAs & Disbursements (Phase 4)
    public DbSet<GrantMoa> GrantMoas => Set<GrantMoa>();
    public DbSet<GrantMoaMilestone> GrantMoaMilestones => Set<GrantMoaMilestone>();
    public DbSet<GrantTranchePayment> GrantTranchePayments => Set<GrantTranchePayment>();
    public DbSet<MandatoryGrantDisbursement> MandatoryGrantDisbursements => Set<MandatoryGrantDisbursement>();
    public DbSet<InterSetaTransfer> InterSetaTransfers => Set<InterSetaTransfer>();

    // SETMIS & DHET Compliance Batches (Phase 5)
    public DbSet<SetmisSubmissionBatch> SetmisSubmissionBatches => Set<SetmisSubmissionBatch>();

    // Lookups in `lookup` schema
    public DbSet<GenderType> GenderTypes => Set<GenderType>();
    public DbSet<EquityType> EquityTypes => Set<EquityType>();
    public DbSet<CitizenStatusType> CitizenStatusTypes => Set<CitizenStatusType>();
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

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        base.OnModelCreating(modelBuilder);

        // Identity table mapping & indexes
        modelBuilder.Entity<ApplicationUser>(entity =>
        {
            entity.ToTable("app_user");
            entity.HasOne(u => u.Person)
                  .WithMany()
                  .HasForeignKey(u => u.PersonId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(u => u.DefaultOrganisation)
                  .WithMany()
                  .HasForeignKey(u => u.DefaultOrganisationId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(u => u.PersonId);
            entity.HasIndex(u => u.DefaultOrganisationId);
            entity.HasIndex(u => u.IsActive);
        });

        modelBuilder.Entity<ApplicationRole>(entity =>
        {
            entity.ToTable("app_role");
            entity.HasIndex(r => r.Active);
        });

        modelBuilder.Entity<IdentityUserRole<int>>(entity =>
        {
            entity.ToTable("app_user_role");
        });

        modelBuilder.Entity<IdentityUserClaim<int>>(entity =>
        {
            entity.ToTable("app_user_claim");
        });

        modelBuilder.Entity<IdentityUserLogin<int>>(entity =>
        {
            entity.ToTable("app_user_login");
        });

        modelBuilder.Entity<IdentityRoleClaim<int>>(entity =>
        {
            entity.ToTable("app_role_claim");
        });

        modelBuilder.Entity<IdentityUserToken<int>>(entity =>
        {
            entity.ToTable("app_user_token");
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
            entity.Property(p => p.Email).HasMaxLength(150);
            entity.Property(p => p.PhoneNumber).HasMaxLength(30);
            entity.Property(p => p.CellNumber).HasMaxLength(30);
            entity.Property(p => p.GenderCode).HasMaxLength(15);
            entity.Property(p => p.EquityCode).HasMaxLength(15);
            entity.Property(p => p.DisabilityCode).HasMaxLength(15);
            entity.Property(p => p.NationalityCode).HasMaxLength(15);
            entity.Property(p => p.HomeLanguageCode).HasMaxLength(15);
            entity.Property(p => p.ProvinceCode).HasMaxLength(15);
            entity.Property(p => p.PhysicalAddress).HasMaxLength(500);
            entity.Property(p => p.PostalAddress).HasMaxLength(500);

            entity.HasIndex(p => p.RsaIdNumber);
            entity.HasIndex(p => p.PassportNumber);
            entity.HasIndex(p => p.Email);
            entity.HasIndex(p => p.LastName);
            entity.HasIndex(p => p.GenderCode);
            entity.HasIndex(p => p.EquityCode);
            entity.HasIndex(p => p.DisabilityCode);
            entity.HasIndex(p => p.NationalityCode);
            entity.HasIndex(p => p.ProvinceCode);
            entity.HasIndex(p => p.IsActive);
        });

        // Organisation table & indexes
        modelBuilder.Entity<Organisation>(entity =>
        {
            entity.ToTable("Organisation");
            entity.Property(o => o.CompanyName).HasMaxLength(200).IsRequired();
            entity.Property(o => o.TradingName).HasMaxLength(200);
            entity.Property(o => o.SdlNumber).HasMaxLength(20).IsRequired();
            entity.Property(o => o.RegistrationNumber).HasMaxLength(50);
            entity.Property(o => o.TaxNumber).HasMaxLength(50);
            entity.Property(o => o.CategoryCode).HasMaxLength(15);
            entity.Property(o => o.StatusCode).HasMaxLength(15);
            entity.Property(o => o.ProvinceCode).HasMaxLength(15);
            entity.Property(o => o.SectorCode).HasMaxLength(15);
            entity.Property(o => o.ChamberCode).HasMaxLength(15);
            entity.Property(o => o.SicCode).HasMaxLength(15);
            entity.Property(o => o.CompanySizeCode).HasMaxLength(15);
            entity.Property(o => o.OrganisationTypeCode).HasMaxLength(15);
            entity.Property(o => o.BankName).HasMaxLength(100);
            entity.Property(o => o.BankBranchCode).HasMaxLength(20);
            entity.Property(o => o.BankAccountNumber).HasMaxLength(50);
            entity.Property(o => o.BankAccountType).HasMaxLength(50);

            entity.HasOne(o => o.PrimaryContactPerson)
                  .WithMany()
                  .HasForeignKey(o => o.PrimaryContactPersonId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(o => o.SdlNumber).IsUnique();
            entity.HasIndex(o => o.CompanyName);
            entity.HasIndex(o => o.CategoryCode);
            entity.HasIndex(o => o.StatusCode);
            entity.HasIndex(o => o.ProvinceCode);
            entity.HasIndex(o => o.SectorCode);
            entity.HasIndex(o => o.ChamberCode);
            entity.HasIndex(o => o.SicCode);
            entity.HasIndex(o => o.CompanySizeCode);
            entity.HasIndex(o => o.OrganisationTypeCode);
            entity.HasIndex(o => o.PrimaryContactPersonId);
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
            entity.Property(s => s.PhysicalAddress).HasMaxLength(500);
            entity.Property(s => s.ProvinceCode).HasMaxLength(15);

            entity.HasOne(s => s.Organisation)
                  .WithMany(o => o.Sites)
                  .HasForeignKey(s => s.OrganisationId)
                  .OnDelete(DeleteBehavior.Cascade);

            entity.HasOne(s => s.PrimaryContactPerson)
                  .WithMany()
                  .HasForeignKey(s => s.PrimaryContactPersonId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(s => s.OrganisationId);
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
            entity.Property(v => v.StatusCode).HasMaxLength(15);
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
        });

        // WspSubmission
        modelBuilder.Entity<WspSubmission>(entity =>
        {
            entity.ToTable("WspSubmission");
            entity.Property(w => w.ReferenceNumber).HasMaxLength(50);
            entity.Property(w => w.StatusCode).HasMaxLength(15);
            entity.Property(w => w.PlannedTrainingBudget).HasPrecision(18, 2);

            entity.HasOne(w => w.Organisation)
                  .WithMany(o => o.WspSubmissions)
                  .HasForeignKey(w => w.OrganisationId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(w => w.OrganisationId);
            entity.HasIndex(w => w.FinYear);
            entity.HasIndex(w => w.ReferenceNumber);
        });

        // LevyFile & LevyFileLine
        modelBuilder.Entity<LevyFile>(entity =>
        {
            entity.ToTable("LevyFile");
            entity.Property(l => l.FileName).HasMaxLength(255).IsRequired();
            entity.Property(l => l.FileRef).HasMaxLength(100);
            entity.Property(l => l.StatusCode).HasMaxLength(15);
            entity.Property(l => l.TotalAmount).HasPrecision(18, 2);

            entity.HasIndex(l => l.FileRef);
            entity.HasIndex(l => l.ImportDate);
        });

        modelBuilder.Entity<LevyFileLine>(entity =>
        {
            entity.ToTable("LevyFileLine");
            entity.Property(l => l.SdlNumber).HasMaxLength(20).IsRequired();
            entity.Property(l => l.SchemeYear).HasMaxLength(10);
            entity.Property(l => l.MandatoryLevyAmount).HasPrecision(18, 2);
            entity.Property(l => l.DiscretionaryLevyAmount).HasPrecision(18, 2);
            entity.Property(l => l.AdminLevyAmount).HasPrecision(18, 2);
            entity.Property(l => l.InterestAmount).HasPrecision(18, 2);
            entity.Property(l => l.PenaltyAmount).HasPrecision(18, 2);

            entity.HasOne(l => l.LevyFile)
                  .WithMany(f => f.LineItems)
                  .HasForeignKey(l => l.LevyFileId)
                  .OnDelete(DeleteBehavior.Cascade);

            entity.HasIndex(l => l.LevyFileId);
            entity.HasIndex(l => l.SdlNumber);
        });

        // GrantApplication
        modelBuilder.Entity<GrantApplication>(entity =>
        {
            entity.ToTable("GrantApplication");
            entity.Property(g => g.ApplicationNumber).HasMaxLength(50).IsRequired();
            entity.Property(g => g.GrantTypeCode).HasMaxLength(15);
            entity.Property(g => g.StatusCode).HasMaxLength(15);
            entity.Property(g => g.ProjectTitle).HasMaxLength(300);
            entity.Property(g => g.RequestedAmount).HasPrecision(18, 2);
            entity.Property(g => g.ApprovedAmount).HasPrecision(18, 2);

            entity.HasOne(g => g.Organisation)
                  .WithMany(o => o.GrantApplications)
                  .HasForeignKey(g => g.OrganisationId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(g => g.OrganisationId);
            entity.HasIndex(g => g.ApplicationNumber);
        });

        // EtqaAssessor
        modelBuilder.Entity<EtqaAssessor>(entity =>
        {
            entity.ToTable("EtqaAssessor");
            entity.Property(a => a.RegistrationNumber).HasMaxLength(50).IsRequired();
            entity.Property(a => a.EtqaRole).HasMaxLength(50);
            entity.Property(a => a.StatusCode).HasMaxLength(15);

            entity.HasOne(a => a.Person)
                  .WithMany()
                  .HasForeignKey(a => a.PersonId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(a => a.PersonId);
            entity.HasIndex(a => a.RegistrationNumber);
        });

        // TrainingProvider
        modelBuilder.Entity<TrainingProvider>(entity =>
        {
            entity.ToTable("TrainingProvider");
            entity.Property(tp => tp.AccreditationNumber).HasMaxLength(50).IsRequired();
            entity.Property(tp => tp.ProviderTypeCode).HasMaxLength(15);
            entity.Property(tp => tp.ProviderStatusCode).HasMaxLength(15);
            entity.Property(tp => tp.EtqaDecisionNumber).HasMaxLength(50);

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
            entity.HasIndex(tp => tp.AccreditationNumber);
            entity.HasIndex(tp => tp.ProviderTypeCode);
            entity.HasIndex(tp => tp.ProviderStatusCode);
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
            entity.ToTable("GrantFundingWindow");
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

            entity.HasIndex(b => b.GrantApplicationId);
            entity.HasIndex(b => b.ExpenseCategory);
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
            entity.ToTable("LearnerAssessment");
            entity.Property(a => a.QualificationTitle).HasMaxLength(200).IsRequired();
            entity.Property(a => a.CompetencyStatusCode).HasMaxLength(15);

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

            entity.HasOne(a => a.ModeratorPerson)
                  .WithMany()
                  .HasForeignKey(a => a.ModeratorPersonId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(a => a.EtqaAssessorId);
            entity.HasIndex(a => a.PersonId);
            entity.HasIndex(a => a.OrganisationId);
            entity.HasIndex(a => a.ModeratorPersonId);
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

            entity.HasIndex(w => w.OrganisationId);
            entity.HasIndex(w => w.ApprovalNumber);
            entity.HasIndex(w => w.ApprovalStatusCode);
        });

        // WorkplaceApprovalMentor
        modelBuilder.Entity<WorkplaceApprovalMentor>(entity =>
        {
            entity.ToTable("WorkplaceApprovalMentor");
            entity.Property(m => m.Designation).HasMaxLength(100);
            entity.Property(m => m.ArtisanTradeNumber).HasMaxLength(50);

            entity.HasOne(m => m.Person)
                  .WithMany()
                  .HasForeignKey(m => m.PersonId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(m => m.WorkplaceApprovalId);
            entity.HasIndex(m => m.PersonId);
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
            entity.ToTable("CompanyLearner");
            entity.Property(l => l.LearnerContractNumber).HasMaxLength(50);
            entity.Property(l => l.QualificationTitle).HasMaxLength(250).IsRequired();
            entity.Property(l => l.LearningProgrammeTypeCode).HasMaxLength(50);
            entity.Property(l => l.FundingTypeCode).HasMaxLength(50);
            entity.Property(l => l.StatusCode).HasMaxLength(50);

            entity.HasOne(l => l.Person)
                  .WithMany()
                  .HasForeignKey(l => l.PersonId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(l => l.Organisation)
                  .WithMany()
                  .HasForeignKey(l => l.OrganisationId)
                  .OnDelete(DeleteBehavior.Restrict);

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
            entity.HasIndex(l => l.TrainingProviderId);
            entity.HasIndex(l => l.LearnerContractNumber);
            entity.HasIndex(l => l.StatusCode);
            entity.HasIndex(l => l.RegistrationDate);
        });

        // LearnerTradeTest
        modelBuilder.Entity<LearnerTradeTest>(entity =>
        {
            entity.ToTable("LearnerTradeTest");
            entity.Property(t => t.TestCenterName).HasMaxLength(150).IsRequired();
            entity.Property(t => t.TradeTitle).HasMaxLength(150).IsRequired();
            entity.Property(t => t.ResultStatusCode).HasMaxLength(50);
            entity.Property(t => t.SerialCertificateNumber).HasMaxLength(50);

            entity.HasOne(t => t.AssessorPerson)
                  .WithMany()
                  .HasForeignKey(t => t.AssessorPersonId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasOne(t => t.ModeratorPerson)
                  .WithMany()
                  .HasForeignKey(t => t.ModeratorPersonId)
                  .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(t => t.CompanyLearnerId);
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
            entity.ToTable("grant_moa");
            entity.Property(m => m.MoaNumber).HasMaxLength(100).IsRequired();
            entity.Property(m => m.MoaStatusCode).HasMaxLength(50).IsRequired();
            entity.Property(m => m.TotalContractValue).HasColumnType("decimal(18,2)").IsRequired();
            entity.Property(m => m.SignoffDocumentUri).HasMaxLength(500);
            entity.Property(m => m.SpecialConditions).HasMaxLength(2000);

            entity.HasOne(m => m.GrantApplication)
                .WithMany()
                .HasForeignKey(m => m.GrantApplicationId)
                .OnDelete(DeleteBehavior.Restrict);

            entity.HasIndex(m => m.GrantApplicationId);
            entity.HasIndex(m => m.MoaNumber).IsUnique();
            entity.HasIndex(m => m.MoaStatusCode);
        });

        modelBuilder.Entity<GrantMoaMilestone>(entity =>
        {
            entity.ToTable("grant_moa_milestone");
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
            entity.ToTable("grant_tranche_payment");
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
            entity.ToTable("mandatory_grant_disbursement");
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
        });

        modelBuilder.Entity<InterSetaTransfer>(entity =>
        {
            entity.ToTable("inter_seta_transfer");
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

        // Phase 5: SETMIS Submission Batch Configuration
        modelBuilder.Entity<SetmisSubmissionBatch>(entity =>
        {
            entity.ToTable("setmis_submission_batch");
            entity.Property(b => b.BatchNumber).HasMaxLength(100).IsRequired();
            entity.Property(b => b.FileCode).HasMaxLength(50).IsRequired();
            entity.Property(b => b.SubmissionPeriod).HasMaxLength(50).IsRequired();
            entity.Property(b => b.Status).HasMaxLength(50).IsRequired();
            entity.Property(b => b.GeneratedFileUri).HasMaxLength(500);
            entity.Property(b => b.DhetAcknowledgmentRef).HasMaxLength(100);

            entity.HasIndex(b => b.BatchNumber).IsUnique();
            entity.HasIndex(b => b.SubmissionPeriod);
            entity.HasIndex(b => b.Status);
        });

        // Configure BaseLookupType with Code (varchar 15) as Key in `lookup` schema
        ConfigureLookup<GenderType>(modelBuilder, "GenderType");
        ConfigureLookup<EquityType>(modelBuilder, "EquityType");
        ConfigureLookup<CitizenStatusType>(modelBuilder, "CitizenStatusType");
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

        // Seed all lookups
        LookupBatchSeeder.SeedAllLookups(modelBuilder);
    }

    private static void ConfigureLookup<T>(ModelBuilder modelBuilder, string tableName) where T : BaseLookupType
    {
        modelBuilder.Entity<T>(entity =>
        {
            entity.ToTable(tableName, schema: "lookup");
            entity.HasKey(x => x.Code);
            entity.Property(x => x.Code).HasMaxLength(15).IsRequired();
            entity.Property(x => x.Name).HasMaxLength(100).IsRequired();
            entity.HasIndex(x => x.Name);
            entity.HasIndex(x => x.Active);
        });
    }
}
