using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using System.Text;
using Xunit;

using Nsdms.Application.Common;

namespace Nsdms.Tests;

public class WorkflowEngineTests
{
    [Fact]
    public async Task StartWorkflow_ShouldCreateInstance_AndInitialTask()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        using (var seedContext = factory.CreateDbContext())
        {
            await WorkflowDefinitionSeeder.SeedWorkflowDefinitionsAsync(seedContext);
        }
        var workflowService = new WorkflowEngineService(factory);

        // Act
        var result = await workflowService.StartWorkflowAsync(
            "WSP",
            101,
            "Toyota SA - WSP 2026/27",
            "WSP-2026-9999",
            "sdf@toyota.co.za",
            "Toyota Primary SDF"
        );

        // Assert
        Assert.True(result.Success);
        Assert.NotNull(result.Instance);
        Assert.Equal("Draft WSP", result.NewStateName);

        var instance = await workflowService.GetInstanceByEntityAsync("WSP", 101);
        Assert.NotNull(instance);
        Assert.Equal("Draft WSP", instance.CurrentWorkflowState?.StateName);
        Assert.False(instance.IsCompleted);

        // Verify task was created for Primary SDF
        var tasks = await workflowService.GetUserTasksAsync(userRole: "Primary SDF");
        Assert.Contains(tasks, t => t.WorkflowInstanceId == instance.Id);
    }

    [Fact]
    public async Task AdvanceWorkflow_ShouldTransitionState_CreateHistory_AndCompletePreviousTask()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        using (var seedContext = factory.CreateDbContext())
        {
            await WorkflowDefinitionSeeder.SeedWorkflowDefinitionsAsync(seedContext);
        }
        var workflowService = new WorkflowEngineService(factory);

        var startResult = await workflowService.StartWorkflowAsync(
            "PROVIDER",
            501,
            "Atlas Skills Academy Accreditation",
            "SDP-2026-501",
            "admin@atlas.co.za",
            "Atlas Director"
        );
        var instanceId = startResult.Instance!.Id;

        var transitions = await workflowService.GetAvailableTransitionsAsync(instanceId, null);
        var submitTransition = transitions.First(t => t.ActionName == "Submit for Desktop Review");

        // Act
        var advanceResult = await workflowService.AdvanceWorkflowAsync(
            instanceId,
            submitTransition.Id,
            "clo@merseta.org.za",
            "MerSETA CLO",
            "Client Liaison Officer (CLO)",
            "Desk review documentation uploaded."
        );

        // Assert
        Assert.True(advanceResult.Success);
        Assert.Equal("Under Desktop Review", advanceResult.NewStateName);

        // Verify history double-write
        var history = await workflowService.GetWorkflowHistoryAsync(instanceId);
        Assert.NotEmpty(history);
        Assert.Equal("Submit for Desktop Review", history[0].ActionName);
        Assert.Equal("Desk review documentation uploaded.", history[0].Comments);

        // Verify next task assigned to CLO
        var cloTasks = await workflowService.GetUserTasksAsync(userRole: "Client Liaison Officer (CLO)");
        Assert.Contains(cloTasks, t => t.WorkflowInstanceId == instanceId);
    }

    [Fact]
    public async Task ClaimTask_ShouldAssignUser_AndMarkClaimed()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        using (var seedContext = factory.CreateDbContext())
        {
            await WorkflowDefinitionSeeder.SeedWorkflowDefinitionsAsync(seedContext);
        }
        var workflowService = new WorkflowEngineService(factory);

        var startResult = await workflowService.StartWorkflowAsync(
            "DG",
            301,
            "Robotics Apprenticeship Project",
            "DG-2026-301",
            "initiator@denel.co.za",
            "Denel Dynamics"
        );
        var instanceId = startResult.Instance!.Id;

        var tasks = await workflowService.GetUserTasksAsync();
        var targetTask = tasks.First(t => t.WorkflowInstanceId == instanceId);

        // Act
        var claimed = await workflowService.ClaimTaskAsync(targetTask.Id, "reviewer@merseta.org.za", "Thabo Mokoena");

        // Assert
        Assert.NotNull(claimed);
        Assert.Equal("Claimed", claimed.TaskStatus);
        Assert.Equal("reviewer@merseta.org.za", claimed.AssignedUserId);
        Assert.Equal("Thabo Mokoena", claimed.AssignedUserName);
        Assert.NotNull(claimed.ClaimedDate);
    }

    [Fact]
    public async Task StorageService_UploadAndVerifyDocument_ShouldCalculateSha256Checksum()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var storageService = new StorageService(factory);

        var fileContent = "MerSETA Quality Assurance Signed SLA Document Content 2026";
        var fileBytes = Encoding.UTF8.GetBytes(fileContent);
        using var stream = new MemoryStream(fileBytes);

        // Act
        var doc = await storageService.UploadDocumentAsync(
            "Organisation",
            1,
            "BANK_CONFIRM",
            "Proof of Banking Details",
            "fnb_bank_confirmation.pdf",
            stream,
            "application/pdf",
            "admin@merseta.org.za",
            "Administrator"
        );

        // Assert
        Assert.NotNull(doc);
        Assert.True(doc.Id > 0);
        Assert.NotNull(doc.Sha256Hash);
        Assert.Equal(64, doc.Sha256Hash.Length); // 64 hex characters
        Assert.False(doc.IsVerified);

        // Act: Verify Document
        var verifyResult = await storageService.VerifyDocumentAsync(doc.Id, "auditor@merseta.org.za", "Bank stamp confirmed authentic.");
        Assert.True(verifyResult);

        var updatedDoc = await storageService.GetDocumentByIdAsync(doc.Id);
        Assert.NotNull(updatedDoc);
        Assert.True(updatedDoc.IsVerified);
        Assert.Equal("Bank stamp confirmed authentic.", updatedDoc.VerificationNotes);
    }
}
