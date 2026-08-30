using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

public class RemainingClustersTests
{
    private static async Task<(TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, int orgId, int personId, int providerId)> SetupEnvironmentAsync()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);

        var org = new Organisation
        {
            CompanyName = "MerSETA Auto Assemblies Ltd",
            SdlNumber = "L998877665",
            RegistrationNumber = "2020/654321/07",
            SicCode = "35600"
        };
        db.Organisations.Add(org);

        var person = new Person
        {
            FirstName = "Kagiso",
            LastName = "Molefe",
            RsaIdNumber = "9201015009087",
            Email = "kagiso.molefe@merseta.org.za",
            GenderCode = "M"
        };
        db.People.Add(person);

        var provider = new TrainingProvider
        {
            Organisation = org,
            AccreditationNumber = "ACC-2026-9912",
            ProviderTypeCode = "PrivateProvider",
            ProviderStatusCode = "Accredited",
            AccreditationStartDate = DateTime.UtcNow.AddYears(-1),
            AccreditationEndDate = DateTime.UtcNow.AddYears(4)
        };
        db.TrainingProviders.Add(provider);

        await db.SaveChangesAsync();

        return (factory, db, audit, org.Id, person.Id, provider.Id);
    }

    [Fact]
    public async Task Cluster2_ReviewCommittee_LifecycleAndAdjudication_Succeeds()
    {
        var (factory, _, audit, _, personId, providerId) = await SetupEnvironmentAsync();
        var committeeService = new ReviewCommitteeService(factory, audit);

        // Schedule meeting
        var meeting = new ReviewCommitteeMeeting
        {
            Title = "ETQA Assessor & Scope Adjudication",
            MeetingTypeCode = "EtqaReviewCommittee",
            FromDateTime = DateTime.UtcNow.AddDays(7),
            ToDateTime = DateTime.UtcNow.AddDays(7).AddHours(3),
            Venue = "merSETA Head Office - Boardroom 1"
        };
        var scheduled = await committeeService.ScheduleMeetingAsync(meeting, "AdminUser");
        Assert.StartsWith("RCM-", scheduled.MeetingNumber);
        Assert.Equal("Scheduled", scheduled.StatusCode);

        // Add member
        var member = new ReviewCommitteeMeetingMember
        {
            ReviewCommitteeMeetingId = scheduled.Id,
            PersonId = personId,
            RoleInMeeting = "Chairperson"
        };
        await committeeService.AddMemberAsync(member, "AdminUser");

        // Add agenda
        var agenda = new ReviewCommitteeMeetingAgenda
        {
            ReviewCommitteeMeetingId = scheduled.Id,
            ItemNumber = 1,
            Title = "Assessor Registration for Kagiso Molefe",
            TargetEntityName = "AssessorModeratorApplication",
            DecisionCode = "Pending"
        };
        var addedAgenda = await committeeService.AddAgendaItemAsync(agenda, "AdminUser");

        // Record decision
        var votedAgenda = await committeeService.RecordAgendaDecisionAsync(
            addedAgenda.Id,
            "Approved",
            "Candidate meets all ETQA criteria.",
            7, 0, 0,
            "ChairpersonUser");
        Assert.Equal("Approved", votedAgenda.DecisionCode);
        Assert.Equal(7, votedAgenda.VotedYesCount);

        // Conclude meeting
        var concluded = await committeeService.ConcludeMeetingAsync(scheduled.Id, "AdminUser");
        Assert.Equal("Concluded", concluded.StatusCode);

        // Assessor application workflow
        var app = new AssessorModeratorApplication
        {
            PersonId = personId,
            ApplicationTypeCode = "Assessor"
        };
        var submittedApp = await committeeService.SubmitAssessorModeratorApplicationAsync(app, "AdminUser");
        Assert.Equal("RecommendedForCommittee", submittedApp.StatusCode);

        var approvedApp = await committeeService.AdjudicateAssessorModeratorApplicationAsync(submittedApp.Id, true, null, "ManagerUser");
        Assert.Equal("Approved", approvedApp.StatusCode);
        Assert.NotNull(approvedApp.CertificateNumber);
        Assert.NotNull(approvedApp.ExpiryDate);

        // SDP Scope extension
        var scopeApp = new SdpScopeExtensionApplication
        {
            TrainingProviderId = providerId
        };
        var submittedScope = await committeeService.SubmitSdpScopeExtensionAsync(scopeApp, "AdminUser");
        Assert.Equal("CommitteeReview", submittedScope.StatusCode);

        var approvedScope = await committeeService.AdjudicateSdpScopeExtensionAsync(submittedScope.Id, true, "Facilities verified compliant.", "ManagerUser");
        Assert.Equal("Approved", approvedScope.StatusCode);
    }

    [Fact]
    public async Task Cluster3_DgProjectImplementationPlan_AllocationsAndClaims_Succeeds()
    {
        var (factory, _, audit, orgId, _, _) = await SetupEnvironmentAsync();
        var inMemorySettings = new Dictionary<string, string?>
        {
            { "Features:Integrations.DynamicsGp", "false" }
        };
        var config = new ConfigurationBuilder().AddInMemoryCollection(inMemorySettings).Build();
        var configService = new SystemConfigurationService(factory, config, audit);
        var flagService = new FeatureFlagService(factory, config, audit);
        var erpService = new ErpIntegrationService(factory, flagService, configService, audit);
        var pipService = new DgProjectImplementationService(factory, audit, erpService);

        var pip = new ProjectImplementationPlan
        {
            OrganisationId = orgId,
            InterventionTypeCode = "Apprenticeship"
        };

        var allocations = new List<PipLearnerAllocation>
        {
            new()
            {
                LearnerCount = 10,
                UnitCost = 150000m,
                TotalAllowanceBudget = 900000m,
                TotalTuitionBudget = 600000m
            },
            new()
            {
                LearnerCount = 5,
                UnitCost = 120000m,
                TotalAllowanceBudget = 400000m,
                TotalTuitionBudget = 200000m
            }
        };

        var createdPip = await pipService.CreatePipAsync(pip, allocations, "AdminUser");
        Assert.Equal(2100000m, createdPip.TotalAwardedAmount);
        Assert.Equal(15, createdPip.TotalLearnersAwarded);
        Assert.StartsWith("PIP-", createdPip.PlanReferenceNumber);

        var signedPip = await pipService.SignOffContractsAsync(createdPip.Id, "AdminUser");
        Assert.Equal("ActiveContractsSigned", signedPip.StatusCode);
        Assert.NotNull(signedPip.ContractSignOffDate);

        // Submit and disburse claim
        var claim = new GrantPaymentClaim
        {
            ProjectImplementationPlanId = createdPip.Id,
            TrancheNumber = 1,
            ClaimAmount = 525000m,
            DeliverableDescription = "Tranche 1: Learner Contracting and Induction Verification"
        };
        var submittedClaim = await pipService.SubmitPaymentClaimAsync(claim, "AdminUser");
        Assert.Equal("PendingSubmission", submittedClaim.StatusCode);

        var disbursed = await pipService.ApproveAndDisburseClaimAsync(submittedClaim.Id, "FinanceManager");
        Assert.Equal("Paid", disbursed.StatusCode);
        Assert.NotNull(disbursed.ErpBatchNumber);
        Assert.StartsWith("ERP-DISB-", disbursed.ErpBatchNumber);
    }

    [Fact]
    public async Task Cluster4_TrainingCommitteeAndDispute_Lifecycle_Succeeds()
    {
        var (factory, _, audit, orgId, personId, _) = await SetupEnvironmentAsync();
        var service = new TrainingCommitteeAndDisputeService(factory, audit);

        // Register committee with quorum
        var committee = new TrainingCommittee
        {
            OrganisationId = orgId,
            FinancialYear = 2026
        };

        var members = new List<TrainingCommitteeMember>
        {
            new()
            {
                PersonId = personId,
                MemberRoleCode = "UnionRepresentative",
                Constituency = "NUMSA"
            },
            new()
            {
                PersonId = personId,
                MemberRoleCode = "EmployerRepresentative",
                Constituency = "Management"
            }
        };

        var registered = await service.RegisterCommitteeAsync(committee, members, "AdminUser");
        Assert.True(registered.ConstitutionalQuorumMet);
        Assert.Equal("Active", registered.StatusCode);

        // Non-compliant committee (missing labour)
        var badCommittee = new TrainingCommittee
        {
            OrganisationId = orgId,
            FinancialYear = 2025
        };
        var badMembers = new List<TrainingCommitteeMember>
        {
            new()
            {
                PersonId = personId,
                MemberRoleCode = "EmployerRepresentative",
                Constituency = "Management"
            }
        };
        var badRegistered = await service.RegisterCommitteeAsync(badCommittee, badMembers, "AdminUser");
        Assert.False(badRegistered.ConstitutionalQuorumMet);
        Assert.Equal("NonCompliant", badRegistered.StatusCode);

        // Dispute workflow
        var dispute = new WspDispute
        {
            OrganisationId = orgId,
            DisputeReasonCode = "UnionRefusalToSign",
            Description = "Union representatives raised concerns over training plan allocation."
        };
        var loggedDispute = await service.LogDisputeAsync(dispute, "AdminUser");
        Assert.Equal("Logged", loggedDispute.StatusCode);
        Assert.StartsWith("DSP-", loggedDispute.DisputeReferenceNumber);

        var resolved = await service.ResolveDisputeAsync(loggedDispute.Id, "Consensus reached in mediation meeting.", "AdminUser");
        Assert.Equal("Resolved", resolved.StatusCode);
        Assert.NotNull(resolved.ResolutionDate);

        // Skills gap
        var gap = new WspSkillsGap
        {
            OrganisationId = orgId,
            FinancialYear = 2026,
            OfoCode = "651302",
            OccupationTitle = "Boilermaker",
            HardToFillVacanciesCount = 8,
            SkillsGapReason = "High national demand and lack of certified trade test artisans"
        };
        var recordedGap = await service.RecordSkillsGapAsync(gap, "AdminUser");
        Assert.True(recordedGap.Id > 0);
    }
}