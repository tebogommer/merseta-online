using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class WorkflowStudioAndBlueprintTests
{
    [Fact]
    public async Task GetAllDefinitions_ShouldReturnSeededBlueprints_WithStatesAndTransitions()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        using (var seedContext = factory.CreateDbContext())
        {
            await WorkflowDefinitionSeeder.SeedWorkflowDefinitionsAsync(seedContext);
        }
        var workflowService = new WorkflowEngineService(factory);

        // Act
        var blueprints = await workflowService.GetAllDefinitionsAsync();

        // Assert
        Assert.NotEmpty(blueprints);
        Assert.Contains(blueprints, b => b.Code == "PROVIDER");
        Assert.Contains(blueprints, b => b.Code == "WSP");
        Assert.Contains(blueprints, b => b.Code == "DG");

        var wsp = blueprints.First(b => b.Code == "WSP");
        Assert.True(wsp.States.Count >= 5);
        Assert.True(wsp.Transitions.Count >= 4);
    }

    [Fact]
    public async Task SaveDefinition_ShouldCreateAndAudit_NewBlueprint()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var workflowService = new WorkflowEngineService(factory);

        var newDef = new WorkflowDefinition
        {
            Code = "BURSARY_2026",
            Name = "National Bursary & Scholarship Application",
            TargetEntityName = "CompanyLearner",
            KeyFieldName = "Id",
            IsActive = true
        };

        // Act
        var saved = await workflowService.SaveDefinitionAsync(newDef, "admin@merseta.org.za", "System Administrator");

        // Assert
        Assert.True(saved.Id > 0);
        Assert.Equal("BURSARY_2026", saved.Code);

        // Verify audit log entry
        using var checkContext = factory.CreateDbContext();
        var audit = await checkContext.AuditLogs
            .FirstOrDefaultAsync(a => a.EntityName == "WorkflowDefinition" && a.RecordId == saved.Id && a.ActionName == "CREATE_WORKFLOW_DEFINITION");
        Assert.NotNull(audit);
        Assert.Equal("admin@merseta.org.za", audit.Actor);
    }

    [Fact]
    public async Task CloneDefinition_ShouldDeepCopy_StatesAndRemapTransitions()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        using (var seedContext = factory.CreateDbContext())
        {
            await WorkflowDefinitionSeeder.SeedWorkflowDefinitionsAsync(seedContext);
        }
        var workflowService = new WorkflowEngineService(factory);

        var sourceWsp = (await workflowService.GetAllDefinitionsAsync()).First(d => d.Code == "WSP");

        // Act
        var cloned = await workflowService.CloneDefinitionAsync(
            sourceWsp.Id,
            "WSP_2027",
            "Workplace Skills Plan 2027 Scheme",
            "admin@merseta.org.za",
            "System Administrator"
        );

        // Assert
        Assert.True(cloned.Id > 0);
        Assert.NotEqual(sourceWsp.Id, cloned.Id);
        Assert.Equal("WSP_2027", cloned.Code);

        var loadedCloned = await workflowService.GetDefinitionByIdAsync(cloned.Id);
        Assert.NotNull(loadedCloned);
        Assert.Equal(sourceWsp.States.Count, loadedCloned.States.Count);
        Assert.Equal(sourceWsp.Transitions.Count, loadedCloned.Transitions.Count);

        // Ensure newly cloned state IDs are referenced in cloned transitions (not source state IDs)
        var clonedStateIds = loadedCloned.States.Select(s => s.Id).ToHashSet();
        foreach (var trans in loadedCloned.Transitions)
        {
            Assert.Contains(trans.FromStateId, clonedStateIds);
            Assert.Contains(trans.ToStateId, clonedStateIds);
        }
    }

    [Fact]
    public async Task DeleteState_WithActiveInstances_ShouldBeBlocked()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        using (var seedContext = factory.CreateDbContext())
        {
            await WorkflowDefinitionSeeder.SeedWorkflowDefinitionsAsync(seedContext);
        }
        var workflowService = new WorkflowEngineService(factory);

        var startResult = await workflowService.StartWorkflowAsync(
            "WSP",
            999,
            "Active Test Instance",
            "REF-999",
            "tester@merseta.org.za",
            "Test Officer"
        );
        Assert.True(startResult.Success);
        Assert.NotNull(startResult.Instance);

        // Act - Try deleting the state where an instance is currently active
        var result = await workflowService.DeleteStateAsync(startResult.Instance.CurrentWorkflowStateId, "admin@merseta.org.za", "System Administrator");

        // Assert - Safeguard must block deletion
        Assert.False(result);
    }

    [Fact]
    public async Task DocumentRequirementRules_CrudOperations_ShouldSucceed()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var workflowService = new WorkflowEngineService(factory);

        var rule = new DocumentRequirementRule
        {
            WorkflowProcessCode = "PROVIDER",
            DocumentTypeCode = "ETQA_ACCRED_PROOF",
            DocumentTypeName = "Proof of Primary ETQA Accreditation",
            Description = "Certified copy of valid accreditation certificate",
            IsMandatory = true,
            RequiredAtStateId = 2
        };

        // Act - Save
        var savedRule = await workflowService.SaveDocumentRequirementAsync(rule, "admin@merseta.org.za", "System Administrator");
        Assert.True(savedRule.Id > 0);

        // Act - Query
        var rules = await workflowService.GetDocumentRequirementsAsync("PROVIDER");
        Assert.Single(rules);
        Assert.Equal("ETQA_ACCRED_PROOF", rules[0].DocumentTypeCode);

        // Act - Delete
        var deleted = await workflowService.DeleteDocumentRequirementAsync(savedRule.Id, "admin@merseta.org.za", "System Administrator");
        Assert.True(deleted);

        var remaining = await workflowService.GetDocumentRequirementsAsync("PROVIDER");
        Assert.Empty(remaining);
    }
}
