using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Xunit;

namespace Nsdms.Tests;

public class TransactionManagementAndConcurrencyTests
{
    [Fact]
    public async Task CreateGrantMoa_EnsuresAuditLogRecordIdMatchesPersistedEntityId()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var financeService = new FinanceService(factory);

        var grantApp = new GrantApplication
        {
            ApplicationNumber = "DG-2026-TRX-TEST",
            ProjectTitle = "CNC Robotics Integration",
            RequestedAmount = 500000m,
            ApprovedAmount = 500000m,
            StatusCode = "Approved"
        };

        using (var ctx = factory.CreateDbContext())
        {
            ctx.GrantApplications.Add(grantApp);
            await ctx.SaveChangesAsync();
        }

        var moa = new GrantMoa
        {
            GrantApplicationId = grantApp.Id,
            MoaNumber = "MOA-2026-TRX-001",
            ContractStartDate = new DateTime(2026, 4, 1),
            ContractEndDate = new DateTime(2027, 3, 31),
            TotalContractValue = 500000m,
            MoaStatusCode = "Draft"
        };

        // Act
        var created = await financeService.CreateGrantMoaAsync(moa, "officer@merseta.org.za");

        // Assert
        Assert.NotNull(created);
        Assert.True(created.Id > 0, "Persisted Moa must receive a positive auto-generated ID.");

        using (var ctx = factory.CreateDbContext())
        {
            var audit = await ctx.AuditLogs
                .FirstOrDefaultAsync(a => a.EntityName == "GrantMoa" && a.ActionName == "CREATE_GRANT_MOA");

            Assert.NotNull(audit);
            Assert.Equal(created.Id, audit.RecordId);
            Assert.NotEqual(0L, audit.RecordId);
        }
    }

    [Fact]
    public async Task UploadDocument_EnsuresAuditLogRecordIdMatchesPersistedDocId()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var storageService = new StorageService(factory);

        using var stream = new MemoryStream(new byte[] { 1, 2, 3, 4 });

        // Act
        var doc = await storageService.UploadDocumentAsync(
            "GrantMoa",
            101,
            "PROOF_OF_DELIVERY",
            "Proof of Delivery",
            "test_evidence.pdf",
            stream,
            "application/pdf",
            "SYSTEM",
            "System Admin");

        // Assert
        Assert.NotNull(doc);
        Assert.True(doc.Id > 0, "Persisted DocumentMetadata must receive a positive auto-generated ID.");

        using (var ctx = factory.CreateDbContext())
        {
            var audit = await ctx.AuditLogs
                .FirstOrDefaultAsync(a => a.EntityName == "DocumentMetadata" && a.ActionName == "UPLOAD_DOCUMENT");

            Assert.NotNull(audit);
            Assert.Equal(doc.Id, audit.RecordId);
            Assert.NotEqual(0L, audit.RecordId);
        }
    }

    [Fact]
    public async Task WspService_CreateAndAddTrainingPlan_ExecuteAtomicallyWithAudit()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var auditService = new AuditService(factory);
        var wspService = new WspService(factory, auditService);

        var submission = new WspSubmission
        {
            FinYear = 2026,
            OrganisationId = 1,
            PlannedTrainingBudget = 0m,
            EmployeeCount = 100,
            WspApprovalStatusCode = "Draft"
        };

        // Act
        var createdSub = await wspService.CreateAsync(submission, "sdf@employer.co.za");

        var plan = new WspTrainingPlan
        {
            WspSubmissionId = createdSub.Id,
            BeneficiaryCount = 10,
            EstimatedCost = 45000m,
            ProgrammeTypeCode = "Learnership"
        };

        var createdPlan = await wspService.AddTrainingPlanAsync(plan, "sdf@employer.co.za");

        // Assert
        Assert.NotNull(createdSub);
        Assert.True(createdSub.Id > 0);
        Assert.NotNull(createdPlan);
        Assert.True(createdPlan.Id > 0);

        using (var ctx = factory.CreateDbContext())
        {
            var updatedSub = await ctx.WspSubmissions.FindAsync(createdSub.Id);
            Assert.NotNull(updatedSub);
            Assert.Equal(45000m, updatedSub.PlannedTrainingBudget);

            var audits = await ctx.AuditLogs
                .Where(a => (a.EntityName == "WspSubmission" && a.RecordId == createdSub.Id) ||
                            (a.EntityName == "WspTrainingPlan" && a.RecordId == createdPlan.Id))
                .ToListAsync();

            Assert.True(audits.Count >= 2, "Both WspSubmission create and WspTrainingPlan addition must have audit records.");
        }
    }

    [Fact]
    public async Task DiscretionaryGrantClaim_ExceedingAvailableBudget_ThrowsInvalidOperationException()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var auditService = new AuditService(factory);
        var claimService = new DiscretionaryGrantClaimService(factory, auditService);

        var pip = new ProjectImplementationPlan
        {
            TotalAwardedAmount = 200000m,
            StatusCode = "Approved"
        };

        using (var ctx = factory.CreateDbContext())
        {
            ctx.ProjectImplementationPlans.Add(pip);
            await ctx.SaveChangesAsync();
        }

        var request = new SubmitDgClaimRequest
        {
            ProjectImplementationPlanId = pip.Id,
            TrancheNumber = 1,
            ClaimAmount = 250000m, // Exceeds 200k budget
            DeliverableDescription = "Induction & Registration Phase"
        };

        // Act & Assert
        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() =>
            claimService.SubmitTrancheClaimAsync(request, "clo@merseta.org.za"));

        Assert.Contains("exceeds available budget envelope", ex.Message);
    }

    [Fact]
    public async Task WspService_ConcurrentUpdate_WithStaleRowVersion_ThrowsDbUpdateConcurrencyException()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var auditService = new AuditService(factory);
        var wspService = new WspService(factory, auditService);

        var submission = new WspSubmission
        {
            FinYear = 2026,
            OrganisationId = 1,
            PlannedTrainingBudget = 50000m,
            EmployeeCount = 100,
            WspApprovalStatusCode = "Draft",
            RowVersion = new byte[] { 1, 0, 0, 0 }
        };

        var created = await wspService.CreateAsync(submission, "sdf@employer.co.za");

        // Simulate another user updating the record in the database, advancing its RowVersion
        using (var ctx = factory.CreateDbContext())
        {
            var dbRecord = await ctx.WspSubmissions.FindAsync(created.Id);
            Assert.NotNull(dbRecord);
            dbRecord.EmployeeCount = 120;
            dbRecord.RowVersion = new byte[] { 2, 0, 0, 0 }; // Store has advanced
            await ctx.SaveChangesAsync();
        }

        // Stale client submission still carrying original RowVersion { 1, 0, 0, 0 }
        var staleSubmission = new WspSubmission
        {
            Id = created.Id,
            FinYear = created.FinYear,
            OrganisationId = created.OrganisationId,
            PlannedTrainingBudget = 75000m,
            EmployeeCount = 150,
            WspApprovalStatusCode = "Draft",
            RowVersion = new byte[] { 1, 0, 0, 0 } // Stale token
        };

        // Act & Assert: EF Core must detect the conflict and throw DbUpdateConcurrencyException
        await Assert.ThrowsAsync<DbUpdateConcurrencyException>(() =>
            wspService.UpdateAsync(staleSubmission, "sdf@employer.co.za"));
    }

    [Fact]
    public async Task ApproveAndActivateTemplate_ExecutesAtomically_SupersedesPeersAndAudits()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var auditService = new AuditService(factory);
        var templateService = new EnterpriseDocumentTemplateService(factory, auditService, null!);

        var v1 = new DocumentTemplate
        {
            TemplateCode = "WSP-APPROVAL-LETTER",
            TemplateTitle = "WSP Approval Letter v1.0",
            DocumentCategory = "MandatoryGrants",
            FinancialYear = 2026,
            VersionNumber = "1.0",
            ApprovalStatus = "Approved",
            IsActive = true
        };

        var v2 = new DocumentTemplate
        {
            TemplateCode = "WSP-APPROVAL-LETTER",
            TemplateTitle = "WSP Approval Letter v2.0",
            DocumentCategory = "MandatoryGrants",
            FinancialYear = 2026,
            VersionNumber = "2.0",
            ApprovalStatus = "Draft",
            IsActive = false
        };

        using (var ctx = factory.CreateDbContext())
        {
            ctx.DocumentTemplates.AddRange(v1, v2);
            await ctx.SaveChangesAsync();
        }

        // Act
        var activated = await templateService.ApproveAndActivateTemplateAsync(v2.Id, "governance@merseta.org.za");

        // Assert
        Assert.NotNull(activated);
        Assert.True(activated.IsActive);
        Assert.Equal("Approved", activated.ApprovalStatus);

        using (var ctx = factory.CreateDbContext())
        {
            var oldVersion = await ctx.DocumentTemplates.FindAsync(v1.Id);
            Assert.NotNull(oldVersion);
            Assert.False(oldVersion.IsActive);
            Assert.Equal("Superseded", oldVersion.ApprovalStatus);

            var audit = await ctx.AuditLogs.FirstOrDefaultAsync(a =>
                a.EntityName == "DocumentTemplate" && a.RecordId == v2.Id && a.ActionName == "ApproveAndActivateTemplate");
            Assert.NotNull(audit);
            Assert.Contains("\"SupersededCount\":1", audit.MetadataJson);
        }
    }

    [Fact]
    public async Task RecordExternalModerationOutcome_ExecutesAtomically_UpdatesBatchAndRecordsAudit()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var auditService = new AuditService(factory);
        var moderationService = new SummativeAssessmentAndModerationService(factory, auditService);

        var batch = new AssessmentBatch
        {
            BatchNumber = "BATCH-MOD-2026-001",
            AssessmentStageCode = "Progress",
            StatusCode = "PendingExternalModeration"
        };

        using (var ctx = factory.CreateDbContext())
        {
            ctx.AssessmentBatches.Add(batch);
            await ctx.SaveChangesAsync();
        }

        // Act
        var outcome = await moderationService.RecordExternalModerationOutcomeAsync(
            batch.Id,
            isUpheld: true,
            primaryRejectionReason: null,
            vacsViolation: null,
            remarks: "All portfolios comply with VACS principles.",
            remedialAction: null,
            checklistItems: null,
            currentUsername: "mod@merseta.org.za");

        // Assert
        Assert.NotNull(outcome);
        Assert.Equal("Upheld", outcome.StatusCode);

        using (var ctx = factory.CreateDbContext())
        {
            var updatedBatch = await ctx.AssessmentBatches.FindAsync(batch.Id);
            Assert.NotNull(updatedBatch);
            Assert.Equal("Upheld", updatedBatch.StatusCode);

            var checklist = await ctx.ModerationChecklists.FirstOrDefaultAsync(c => c.AssessmentBatchId == batch.Id);
            Assert.NotNull(checklist);
            Assert.Equal("Upheld", checklist.ValidationDecisionCode);

            var audit = await ctx.AuditLogs.FirstOrDefaultAsync(a =>
                a.EntityName == "AssessmentBatch" && a.RecordId == batch.Id && a.ActionName == "RecordExternalModerationOutcome");
            Assert.NotNull(audit);
        }
    }
}


