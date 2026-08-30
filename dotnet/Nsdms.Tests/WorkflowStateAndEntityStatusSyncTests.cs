using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class WorkflowStateAndEntityStatusSyncTests
{
    [Fact]
    public async Task GetWorkflowDefinitionStates_ShouldReturnOrderedStatesForProviderProcess()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        using (var seedContext = factory.CreateDbContext())
        {
            await WorkflowDefinitionSeeder.SeedWorkflowDefinitionsAsync(seedContext);
        }
        var workflowService = new WorkflowEngineService(factory);

        // Act
        var states = await workflowService.GetWorkflowDefinitionStatesAsync("PROVIDER");

        // Assert
        Assert.NotEmpty(states);
        Assert.True(states.Count >= 5);
        Assert.Equal(1, states.First().StepOrder);
        Assert.True(states.Last().IsTerminal);
    }

    [Fact]
    public async Task StartWorkflow_ShouldCreateInstanceAndInitialTask()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        using (var seedContext = factory.CreateDbContext())
        {
            await WorkflowDefinitionSeeder.SeedWorkflowDefinitionsAsync(seedContext);
        }
        var workflowService = new WorkflowEngineService(factory);

        using (var ctx = await factory.CreateDbContextAsync())
        {
            var org = new Organisation { CompanyName = "Test Accreditation Provider", SdlNumber = "L111111111" };
            ctx.Organisations.Add(org);
            await ctx.SaveChangesAsync();

            var provider = new TrainingProvider
            {
                OrganisationId = org.Id,
                AccreditationNumber = "SDP-SYNC-001",
                ProviderStatusCode = "DRAFT"
            };
            ctx.TrainingProviders.Add(provider);
            await ctx.SaveChangesAsync();

            // Act
            var result = await workflowService.StartWorkflowAsync("PROVIDER", provider.Id, "Test Accreditation Provider", provider.AccreditationNumber, "user-1", "John Doe");

            // Assert
            Assert.True(result.Success);
            Assert.NotNull(result.Instance);

            var instance = await ctx.WorkflowInstances
                .Include(i => i.CurrentWorkflowState)
                .Include(i => i.Tasks)
                .FirstOrDefaultAsync(i => i.Id == result.Instance.Id);

            Assert.NotNull(instance);
            Assert.Equal("DRAFT", instance.CurrentWorkflowState?.StateCode);
        }
    }

    [Fact]
    public async Task AdvanceWorkflow_ShouldTransitionState_CompleteTasks_AndSyncEntityStatus()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        using (var seedContext = factory.CreateDbContext())
        {
            await WorkflowDefinitionSeeder.SeedWorkflowDefinitionsAsync(seedContext);
        }
        var workflowService = new WorkflowEngineService(factory);

        int providerId = 0;
        int instanceId = 0;
        int transitionId = 0;

        using (var ctx = await factory.CreateDbContextAsync())
        {
            var org = new Organisation { CompanyName = "Advanced Training Academy", SdlNumber = "L222222222" };
            ctx.Organisations.Add(org);
            await ctx.SaveChangesAsync();

            var provider = new TrainingProvider
            {
                OrganisationId = org.Id,
                AccreditationNumber = "SDP-ADV-001",
                ProviderStatusCode = "DRAFT"
            };
            ctx.TrainingProviders.Add(provider);
            await ctx.SaveChangesAsync();
            providerId = provider.Id;

            var startResult = await workflowService.StartWorkflowAsync("PROVIDER", provider.Id, "Advanced Training Academy", provider.AccreditationNumber, "user-1", "John Doe");
            Assert.True(startResult.Success);
            instanceId = startResult.Instance!.Id;

            var transition = await ctx.WorkflowTransitions
                .FirstOrDefaultAsync(t => t.WorkflowDefinition!.Code == "PROVIDER" && t.ActionName == "Submit for Desktop Review");
            Assert.NotNull(transition);
            transitionId = transition.Id;
        }

        // Act - Advance across transition
        var advanceResult = await workflowService.AdvanceWorkflowAsync(
            instanceId, 
            transitionId, 
            "reviewer-1", 
            "CLO Reviewer", 
            "Client Liaison Officer (CLO)", 
            "All preliminary desktop documentation submitted.");

        // Assert
        Assert.True(advanceResult.Success);

        using (var ctx = await factory.CreateDbContextAsync())
        {
            // Verify WorkflowInstance State
            var instance = await ctx.WorkflowInstances
                .Include(i => i.CurrentWorkflowState)
                .Include(i => i.History)
                .FirstOrDefaultAsync(i => i.Id == instanceId);

            Assert.NotNull(instance);
            Assert.Equal("DESK_REVIEW", instance.CurrentWorkflowState?.StateCode);
            Assert.NotEmpty(instance.History);
            Assert.Equal("Submit for Desktop Review", instance.History.First().ActionName);

            // Verify Entity Status Synchronized to UNDER_REVIEW
            var updatedProvider = await ctx.TrainingProviders.FirstOrDefaultAsync(p => p.Id == providerId);
            Assert.NotNull(updatedProvider);
            Assert.Equal("UNDER_REVIEW", updatedProvider.ProviderStatusCode);

            // Verify Audit Log Double-Write
            var audit = await ctx.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "WorkflowInstance" && a.RecordId == instanceId && a.ActionName == "ADVANCE_STATE");
            Assert.NotNull(audit);
            Assert.Contains("Under Desktop Review", audit.MetadataJson);
        }
    }

    [Fact]
    public async Task SyncEntityStatusAsync_ShouldUpdateEntityDirectlyAndLogAudit()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        using (var seedContext = factory.CreateDbContext())
        {
            await WorkflowDefinitionSeeder.SeedWorkflowDefinitionsAsync(seedContext);
        }
        var workflowService = new WorkflowEngineService(factory);

        int wspId = 0;
        using (var ctx = await factory.CreateDbContextAsync())
        {
            var wsp = new WspSubmission
            {
                OrganisationId = 1,
                FinYear = 2026,
                ReferenceNumber = "WSP-SYNC-2026",
                WspApprovalStatusCode = "DRAFT"
            };
            ctx.WspSubmissions.Add(wsp);
            await ctx.SaveChangesAsync();
            wspId = wsp.Id;
        }

        // Act
        var synced = await workflowService.SyncEntityStatusAsync("WSP", wspId, "APPROVED", "Committee Chair");

        // Assert
        Assert.True(synced);
        using (var ctx = await factory.CreateDbContextAsync())
        {
            var updatedWsp = await ctx.WspSubmissions.FirstOrDefaultAsync(w => w.Id == wspId);
            Assert.NotNull(updatedWsp);
            Assert.Equal("APPROVED", updatedWsp.WspApprovalStatusCode);

            var audit = await ctx.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "WspSubmission" && a.RecordId == wspId && a.ActionName == "SYNC_ENTITY_STATUS");
            Assert.NotNull(audit);
            Assert.Contains("APPROVED", audit.MetadataJson);
        }
    }
}
