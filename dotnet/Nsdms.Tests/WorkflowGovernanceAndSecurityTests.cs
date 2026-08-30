using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging.Abstractions;
using Nsdms.Application.Common;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class WorkflowGovernanceAndSecurityTests
{

    [Fact]
    public async Task ValidateMakerChecker_ShouldBlockSelfApproval_WhenActorIsCreator()
    {
        // Arrange
        var dbFactory = new TestDbContextFactory("MakerChecker_BlockSelf");
        await using (var db = dbFactory.CreateDbContext())
        {
            db.WorkflowTasks.Add(new WorkflowTask
            {
                Id = 101,
                WorkflowInstanceId = 1,
                TaskTitle = "Review WSP Submission",
                TaskStatus = "Open",
                CreatedBy = "42" // User 42 created this task
            });
            await db.SaveChangesAsync();
        }

        var service = new WorkflowGovernanceService(dbFactory, NullLogger<WorkflowGovernanceService>.Instance);

        // Act - User 42 attempts to self-approve
        var result = await service.ValidateMakerCheckerAsync(taskId: 101, actorUserId: 42, isSuperAdminOverride: false);

        // Assert
        Assert.False(result.IsAllowed);
        Assert.True(result.IsSelfAction);
        Assert.Contains("Segregation of Duties Violation", result.Message);
    }

    [Fact]
    public async Task ValidateMakerChecker_ShouldAllowApproval_WhenActorIsDifferentOfficer()
    {
        // Arrange
        var dbFactory = new TestDbContextFactory("MakerChecker_DifferentActor");
        await using (var db = dbFactory.CreateDbContext())
        {
            db.WorkflowTasks.Add(new WorkflowTask
            {
                Id = 102,
                WorkflowInstanceId = 1,
                TaskTitle = "Approve Grant Tranche",
                TaskStatus = "Open",
                CreatedBy = "42"
            });
            await db.SaveChangesAsync();
        }

        var service = new WorkflowGovernanceService(dbFactory, NullLogger<WorkflowGovernanceService>.Instance);

        // Act - User 99 (different officer) approves
        var result = await service.ValidateMakerCheckerAsync(taskId: 102, actorUserId: 99, isSuperAdminOverride: false);

        // Assert
        Assert.True(result.IsAllowed);
        Assert.False(result.IsSelfAction);
        Assert.Contains("Maker-Checker verified", result.Message);
    }

    [Fact]
    public async Task ValidateMakerChecker_ShouldAllowSuperAdminOverride_WithMandatoryAuditJustification()
    {
        // Arrange
        var dbFactory = new TestDbContextFactory("MakerChecker_SuperAdminOverride");
        await using (var db = dbFactory.CreateDbContext())
        {
            db.WorkflowTasks.Add(new WorkflowTask
            {
                Id = 103,
                WorkflowInstanceId = 1,
                TaskTitle = "Emergency Batch Disbursement",
                TaskStatus = "Open",
                CreatedBy = "1" // SuperAdmin created
            });
            await db.SaveChangesAsync();
        }

        var service = new WorkflowGovernanceService(dbFactory, NullLogger<WorkflowGovernanceService>.Instance);

        // Act 1 - SuperAdmin tries to override WITHOUT reason -> should block
        var failResult = await service.ValidateMakerCheckerAsync(taskId: 103, actorUserId: 1, isSuperAdminOverride: true, overrideReason: null);
        Assert.False(failResult.IsAllowed);

        // Act 2 - SuperAdmin provides statutory justification -> should allow and double write to audit log
        var successResult = await service.ValidateMakerCheckerAsync(
            taskId: 103,
            actorUserId: 1,
            isSuperAdminOverride: true,
            overrideReason: "Emergency batch approval authorized by CFO under PFMA emergency provisions");

        Assert.True(successResult.IsAllowed);
        Assert.True(successResult.IsSuperAdminOverride);

        // Assert - Audit log entry created
        await using (var db = dbFactory.CreateDbContext())
        {
            var audit = await db.AuditLogs.FirstOrDefaultAsync(a => a.RecordId == 103 && a.ActionName == "SUPERADMIN_MAKER_CHECKER_OVERRIDE");
            Assert.NotNull(audit);
            Assert.Contains("PFMA Segregation of Duties overridden", audit.MetadataJson);
        }
    }

    [Fact]
    public async Task TaskLease_ShouldPreventConcurrentClaims_AndAllowRenewalAndRelease()
    {
        // Arrange
        var dbFactory = new TestDbContextFactory("TaskLease_Concurrency");
        var service = new WorkflowGovernanceService(dbFactory, NullLogger<WorkflowGovernanceService>.Instance);

        // Act 1 - User A acquires lease on Task 201
        var leaseA = await service.AcquireTaskLeaseAsync(taskId: 201, userId: 10, userName: "Officer Alice", leaseDurationMinutes: 30);
        Assert.True(leaseA.IsSuccess);
        Assert.NotNull(leaseA.Lease);

        // Act 2 - User B attempts to claim Task 201 while locked by Alice -> Should fail
        var leaseB = await service.AcquireTaskLeaseAsync(taskId: 201, userId: 20, userName: "Officer Bob", leaseDurationMinutes: 30);
        Assert.False(leaseB.IsSuccess);
        Assert.True(leaseB.IsAlreadyClaimedByOther);
        Assert.Contains("currently locked by Officer Alice", leaseB.Message);

        // Act 3 - Alice renews her lease -> Should succeed
        var renewResult = await service.RenewTaskLeaseAsync(taskId: 201, userId: 10, extensionMinutes: 45);
        Assert.True(renewResult.IsSuccess);

        // Act 4 - Alice releases lease
        var releaseResult = await service.ReleaseTaskLeaseAsync(taskId: 201, userId: 10);
        Assert.True(releaseResult);

        // Act 5 - Bob can now acquire the lease
        var leaseBSecondAttempt = await service.AcquireTaskLeaseAsync(taskId: 201, userId: 20, userName: "Officer Bob", leaseDurationMinutes: 30);
        Assert.True(leaseBSecondAttempt.IsSuccess);
    }

    [Fact]
    public async Task CryptographicSignoffAttestation_ShouldPersistSha256Checksum_AndAuditTrail()
    {
        // Arrange
        var dbFactory = new TestDbContextFactory("Attestation_Sha256");
        var service = new WorkflowGovernanceService(dbFactory, NullLogger<WorkflowGovernanceService>.Instance);

        string samplePayload = "{\"grantId\":1042,\"contractAmount\":500000.00,\"beneficiary\":\"AutoTech Academy\"}";

        // Act
        var attestation = await service.RecordSignoffAttestationAsync(
            taskId: 301,
            entityName: "GrantMoa",
            entityId: 1042,
            signerUserId: 5,
            signerName: "Dr. N. Sithole",
            signerRsaId: "8504125890082",
            signoffRole: "FinanceExecutive",
            signoffAction: "Approved",
            payloadJson: samplePayload,
            notes: "Final tranche approval verified against physical site monitoring report.");

        // Assert
        Assert.NotNull(attestation);
        Assert.False(string.IsNullOrWhiteSpace(attestation.DocumentSha256Checksum));

        // Verify SHA-256 calculation
        var expectedHash = service.ComputePayloadSha256(samplePayload);
        Assert.Equal(expectedHash, attestation.DocumentSha256Checksum);

        // Verify entity attestations retrieval
        var list = await service.GetAttestationsForEntityAsync("GrantMoa", 1042);
        Assert.Single(list);
        Assert.Equal("Dr. N. Sithole", list[0].SignerName);
    }

    [Fact]
    public async Task TimeBoundedDelegations_ShouldEnforceModuleRestrictions_AndRevocation()
    {
        // Arrange
        var dbFactory = new TestDbContextFactory("Delegations_ModuleScoping");
        var service = new WorkflowGovernanceService(dbFactory, NullLogger<WorkflowGovernanceService>.Instance);

        // Act 1 - Create delegation for Grants and Wsp only
        var delegation = await service.CreateDelegationAsync(
            delegatorUserId: 100,
            delegatorUserName: "manager.ndlovu@merseta.org.za",
            delegateeUserId: 200,
            delegateeUserName: "acting.khoza@merseta.org.za",
            startDate: DateTime.UtcNow.AddDays(-1),
            endDate: DateTime.UtcNow.AddDays(7),
            allowedModules: new List<string> { "Grants", "Wsp" },
            reason: "Annual leave delegation approved by General Manager",
            createdBy: "HR Administrator");

        Assert.NotNull(delegation);
        Assert.True(delegation.IsActive);

        // Act 2 - Check module permissions
        bool canDoGrants = await service.IsUserDelegatedForModuleAsync(delegateeUserId: 200, delegatorUserId: 100, moduleCode: "Grants");
        bool canDoWsp = await service.IsUserDelegatedForModuleAsync(delegateeUserId: 200, delegatorUserId: 100, moduleCode: "Wsp");
        bool canDoFinance = await service.IsUserDelegatedForModuleAsync(delegateeUserId: 200, delegatorUserId: 100, moduleCode: "Finance");

        Assert.True(canDoGrants);
        Assert.True(canDoWsp);
        Assert.False(canDoFinance); // Finance was not delegated

        // Act 3 - Revoke delegation
        var revoked = await service.RevokeDelegationAsync(delegation.Id, "HR Administrator");
        Assert.True(revoked);

        bool canDoGrantsAfterRevocation = await service.IsUserDelegatedForModuleAsync(delegateeUserId: 200, delegatorUserId: 100, moduleCode: "Grants");
        Assert.False(canDoGrantsAfterRevocation);
    }

    [Fact]
    public async Task FinancialApprovalThresholds_ShouldEnforceTierLimits_AndBoardEscalation()
    {
        // Arrange
        var dbFactory = new TestDbContextFactory("Financial_Thresholds");
        await using (var db = dbFactory.CreateDbContext())
        {
            db.FinancialApprovalThresholds.AddRange(
                new FinancialApprovalThreshold
                {
                    RoleName = "CLO",
                    ModuleCode = "Grants",
                    ApprovalLevelName = "Level 1 - Officer",
                    MaxApprovalAmount = 250000m,
                    RequiresBoardApproval = false,
                    IsActive = true
                },
                new FinancialApprovalThreshold
                {
                    RoleName = "FinanceManager",
                    ModuleCode = "Grants",
                    ApprovalLevelName = "Level 2 - Manager",
                    MaxApprovalAmount = 1000000m,
                    RequiresBoardApproval = false,
                    IsActive = true
                },
                new FinancialApprovalThreshold
                {
                    RoleName = "ExecutiveCommittee",
                    ModuleCode = "Grants",
                    ApprovalLevelName = "Level 3 - Board",
                    MaxApprovalAmount = 10000000m,
                    RequiresBoardApproval = true,
                    IsActive = true
                }
            );
            await db.SaveChangesAsync();
        }

        var service = new WorkflowGovernanceService(dbFactory, NullLogger<WorkflowGovernanceService>.Instance);

        // Act 1 - CLO approves R150,000 (Within limit)
        var cloPass = await service.ValidateFinancialApprovalLimitAsync("CLO", "Grants", 150000m);
        Assert.True(cloPass.IsAllowed);

        // Act 2 - CLO approves R400,000 (Exceeds limit)
        var cloFail = await service.ValidateFinancialApprovalLimitAsync("CLO", "Grants", 400000m);
        Assert.False(cloFail.IsAllowed);
        Assert.Contains("exceeds your Level 1 - Officer approval limit", cloFail.Message);

        // Act 3 - Finance Manager approves R850,000 (Within limit)
        var mgrPass = await service.ValidateFinancialApprovalLimitAsync("FinanceManager", "Grants", 850000m);
        Assert.True(mgrPass.IsAllowed);

        // Act 4 - Executive Committee approves R6,000,000 (Board escalation flagged)
        var boardCheck = await service.ValidateFinancialApprovalLimitAsync("ExecutiveCommittee", "Grants", 6000000m);
        Assert.True(boardCheck.IsAllowed);
        Assert.True(boardCheck.RequiresBoardApproval);

        // Act 5 - SuperAdmin wildcard
        var superAdminCheck = await service.ValidateFinancialApprovalLimitAsync("SuperAdmin", "Grants", 50000000m);
        Assert.True(superAdminCheck.IsAllowed);
    }
}
