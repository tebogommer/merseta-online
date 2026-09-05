using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging.Abstractions;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests.Wizards;

public class WizardDraftServiceTests
{
    private class TestDbContextFactory : INsdmsDbContextFactory
    {
        private readonly DbContextOptions<NsdmsDbContext> _options;

        public TestDbContextFactory(DbContextOptions<NsdmsDbContext> options)
        {
            _options = options;
        }

        public Task<INsdmsDbContext> CreateDbContextAsync(CancellationToken cancellationToken = default)
        {
            return Task.FromResult<INsdmsDbContext>(new NsdmsDbContext(_options));
        }

        public INsdmsDbContext CreateDbContext()
        {
            return new NsdmsDbContext(_options);
        }
    }

    private static (TestDbContextFactory Factory, WizardDraftService Service) CreateService()
    {
        var options = new DbContextOptionsBuilder<NsdmsDbContext>()
            .UseInMemoryDatabase(Guid.NewGuid().ToString())
            .Options;

        var factory = new TestDbContextFactory(options);
        var service = new WizardDraftService(factory, NullLogger<WizardDraftService>.Instance);
        return (factory, service);
    }

    private record TestWizardModel
    {
        public string Title { get; init; } = string.Empty;
        public decimal Amount { get; init; }
        public int Headcount { get; init; }
        public List<string> Items { get; init; } = new();
    }

    [Fact]
    public async Task SaveDraftAsync_CreatesNewSession_WhenNoExistingDraftExists()
    {
        var (_, service) = CreateService();

        var model = new TestWizardModel
        {
            Title = "Engineering Apprenticeship 2026",
            Amount = 450000m,
            Headcount = 20,
            Items = new() { "Phase 1 Theory", "Phase 2 Workplace" }
        };

        var session = await service.SaveDraftAsync(
            candidateKey: "DgGrantApplication",
            wizardTitle: "Discretionary Grant Application",
            route: "/dg-grants/apply",
            userId: "SdfUser01",
            organisationId: 10,
            currentStepIndex: 2,
            totalStepCount: 5,
            model: model
        );

        Assert.NotNull(session);
        Assert.StartsWith("DRAFT-", session.DraftKey);
        Assert.Equal("DgGrantApplication", session.CandidateKey);
        Assert.Equal("SdfUser01", session.UserId);
        Assert.Equal(10, session.OrganisationId);
        Assert.Equal(2, session.CurrentStepIndex);
        Assert.Equal(5, session.TotalStepCount);
        Assert.Equal("Active", session.Status);
        Assert.True(session.IsActive);
        Assert.True(session.ExpiresAtUtc > DateTime.UtcNow.AddDays(25));
    }

    [Fact]
    public async Task SaveDraftAsync_UpdatesExistingSession_WhenDraftAlreadyActive()
    {
        var (_, service) = CreateService();

        var initialModel = new TestWizardModel { Title = "Initial Title", Amount = 100000m };
        var session1 = await service.SaveDraftAsync(
            candidateKey: "WorkplaceApproval",
            wizardTitle: "Workplace Approval",
            route: "/workplace-approvals/apply",
            userId: "Employer01",
            organisationId: 5,
            currentStepIndex: 1,
            totalStepCount: 5,
            model: initialModel
        );

        var updatedModel = new TestWizardModel { Title = "Updated Title", Amount = 250000m, Headcount = 8 };
        var session2 = await service.SaveDraftAsync(
            candidateKey: "WorkplaceApproval",
            wizardTitle: "Workplace Approval",
            route: "/workplace-approvals/apply",
            userId: "Employer01",
            organisationId: 5,
            currentStepIndex: 3,
            totalStepCount: 5,
            model: updatedModel,
            existingDraftKey: session1.DraftKey
        );

        Assert.Equal(session1.DraftKey, session2.DraftKey);
        Assert.Equal(3, session2.CurrentStepIndex);

        var loaded = await service.GetActiveDraftAsync("WorkplaceApproval", "Employer01", 5);
        Assert.NotNull(loaded);
        Assert.Equal(3, loaded.CurrentStepIndex);

        var deserialized = service.DeserializeDraftModel<TestWizardModel>(loaded);
        Assert.NotNull(deserialized);
        Assert.Equal("Updated Title", deserialized.Title);
        Assert.Equal(250000m, deserialized.Amount);
        Assert.Equal(8, deserialized.Headcount);
    }

    [Fact]
    public async Task GetActiveDraftAsync_ReturnsNull_WhenExpiredOrInactive()
    {
        var (factory, service) = CreateService();

        // Seed an expired session
        using (var db = await factory.CreateDbContextAsync())
        {
            db.WizardDraftSessions.Add(new WizardDraftSession
            {
                DraftKey = "DRAFT-EXPIRED-001",
                CandidateKey = "LearnerAgreement",
                WizardTitle = "Learner Agreement",
                Route = "/learners/register-agreement",
                UserId = "SdfUser99",
                OrganisationId = null,
                CurrentStepIndex = 2,
                CompletedStepCount = 2,
                TotalStepCount = 5,
                DraftModelJson = "{}",
                Status = "Active",
                IsActive = true,
                ExpiresAtUtc = DateTime.UtcNow.AddDays(-1), // Expired
                CreatedAt = DateTime.UtcNow.AddDays(-31)
            });
            await db.SaveChangesAsync();
        }

        var result = await service.GetActiveDraftAsync("LearnerAgreement", "SdfUser99");
        Assert.Null(result);
    }

    [Fact]
    public async Task MarkAsSubmittedAsync_DeactivatesSession()
    {
        var (_, service) = CreateService();

        var model = new TestWizardModel { Title = "Submitted Test" };
        var session = await service.SaveDraftAsync(
            candidateKey: "TradeTestApplication",
            wizardTitle: "Trade Test Application",
            route: "/trade-tests/apply",
            userId: "Candidate01",
            organisationId: null,
            currentStepIndex: 4,
            totalStepCount: 5,
            model: model
        );

        await service.MarkAsSubmittedAsync(session.DraftKey);

        var activeDraft = await service.GetActiveDraftAsync("TradeTestApplication", "Candidate01");
        Assert.Null(activeDraft);
    }

    [Fact]
    public async Task DiscardDraftAsync_DeactivatesSession()
    {
        var (_, service) = CreateService();

        var model = new TestWizardModel { Title = "To Be Discarded" };
        var session = await service.SaveDraftAsync(
            candidateKey: "AssessorReRegistration",
            wizardTitle: "Assessor Re-registration",
            route: "/etqa/assessors/1/re-register",
            userId: "Assessor_1",
            organisationId: null,
            currentStepIndex: 2,
            totalStepCount: 4,
            model: model
        );

        await service.DiscardDraftAsync(session.DraftKey);

        var activeDraft = await service.GetActiveDraftAsync("AssessorReRegistration", "Assessor_1");
        Assert.Null(activeDraft);
    }

    [Fact]
    public async Task GetActiveDraftsForUserAsync_ReturnsOnlyActiveDraftsForSpecifiedUserAndOrg()
    {
        var (_, service) = CreateService();

        await service.SaveDraftAsync(
            candidateKey: "DgGrantApplication",
            wizardTitle: "DG Grant",
            route: "/dg-grants/apply",
            userId: "MultiDraftUser",
            organisationId: 10,
            currentStepIndex: 1,
            totalStepCount: 5,
            model: new TestWizardModel { Title = "DG 1" }
        );

        await service.SaveDraftAsync(
            candidateKey: "WorkplaceApproval",
            wizardTitle: "Workplace Approval",
            route: "/workplace-approvals/apply",
            userId: "MultiDraftUser",
            organisationId: 10,
            currentStepIndex: 2,
            totalStepCount: 5,
            model: new TestWizardModel { Title = "WPA 1" }
        );

        await service.SaveDraftAsync(
            candidateKey: "LearnerAgreement",
            wizardTitle: "Learner Agreement",
            route: "/learners/register-agreement",
            userId: "AnotherUser",
            organisationId: 10,
            currentStepIndex: 1,
            totalStepCount: 5,
            model: new TestWizardModel { Title = "Other" }
        );

        var userDrafts = await service.GetActiveDraftsForUserAsync("MultiDraftUser", 10);
        Assert.Equal(2, userDrafts.Count);
        Assert.All(userDrafts, d => Assert.Equal("MultiDraftUser", d.UserId));
        Assert.All(userDrafts, d => Assert.Equal(10, d.OrganisationId));
    }

    [Fact]
    public async Task SaveDraftAsync_HandlesCandidate9_InterSetaTransfer()
    {
        var (_, service) = CreateService();

        var model = new
        {
            OrganisationId = (int?)15,
            SdlNumber = "L123987456",
            TransferType = "Outgoing",
            OtherSetaCode = "CHIETA",
            CurrentSicCode = "38100",
            TargetSicCode = "33400",
            TransferAmount = 250000m
        };

        var session = await service.SaveDraftAsync(
            candidateKey: "InterSetaTransfer",
            wizardTitle: "Section 32 Inter-SETA transfer application",
            route: "/inter-seta/transfer-request",
            userId: "SdfUser_Sec32",
            organisationId: 15,
            currentStepIndex: 2,
            totalStepCount: 4,
            model: model
        );

        Assert.NotNull(session);
        Assert.Equal("InterSetaTransfer", session.CandidateKey);
        Assert.Equal("Section 32 Inter-SETA transfer application", session.WizardTitle);
        Assert.Equal(2, session.CurrentStepIndex);
        Assert.Equal(4, session.TotalStepCount);

        var retrieved = await service.GetActiveDraftAsync("InterSetaTransfer", "SdfUser_Sec32", 15);
        Assert.NotNull(retrieved);
        Assert.Equal(session.DraftKey, retrieved.DraftKey);
    }
}
