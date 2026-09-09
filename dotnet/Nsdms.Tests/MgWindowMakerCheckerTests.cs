using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class MgWindowMakerCheckerTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, SystemConfigurationService configService, MgWindowGovernanceService govService) CreateTestContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var conf = new ConfigurationBuilder().Build();
        var configService = new SystemConfigurationService(factory, conf, audit);
        var govService = new MgWindowGovernanceService(factory, audit, configService);
        return (factory, db, audit, configService, govService);
    }

    [Fact]
    public async Task SubmitScheduleProposal_CreatesPendingReviewProposal_AndLogsAudit()
    {
        // Arrange
        var (factory, db, audit, configService, govService) = CreateTestContext();
        var openDate = new DateTime(2026, 2, 1, 8, 0, 0, DateTimeKind.Utc);
        var closeDate = new DateTime(2026, 4, 30, 23, 59, 0, DateTimeKind.Utc);
        var extCutoff = new DateTime(2026, 4, 15, 23, 59, 0, DateTimeKind.Utc);

        // Act
        var proposal = await govService.SubmitScheduleProposalAsync(
            2026,
            openDate,
            closeDate,
            extCutoff,
            "Annual Statutory Schedule per Regulation 4",
            "Gazette No. 49872",
            "Officer_Maker",
            "Grant Officer Maker");

        // Assert
        Assert.NotNull(proposal);
        Assert.True(proposal.Id > 0);
        Assert.Equal("PendingReview", proposal.Status);
        Assert.Equal("Officer_Maker", proposal.ProposedByUserId);
        Assert.False(proposal.AppliedToSystemConfig);

        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "MgWindowScheduleProposal" && a.RecordId == proposal.Id);
        Assert.NotNull(auditLog);
        Assert.Equal("SubmitProposal", auditLog.ActionName);
    }

    [Fact]
    public async Task SubmitScheduleProposal_ThrowsIfDuplicatePendingProposalExists()
    {
        // Arrange
        var (factory, db, audit, configService, govService) = CreateTestContext();
        var openDate = new DateTime(2026, 2, 1, 8, 0, 0, DateTimeKind.Utc);
        var closeDate = new DateTime(2026, 4, 30, 23, 59, 0, DateTimeKind.Utc);
        var extCutoff = new DateTime(2026, 4, 15, 23, 59, 0, DateTimeKind.Utc);

        await govService.SubmitScheduleProposalAsync(
            2026, openDate, closeDate, extCutoff,
            "First Proposal", null, "Officer_Maker", "Grant Officer Maker");

        // Act & Assert
        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() =>
            govService.SubmitScheduleProposalAsync(
                2026, openDate, closeDate, extCutoff,
                "Duplicate Proposal", null, "Another_Maker", "Another Officer"));

        Assert.Contains("already pending Maker-Checker review", ex.Message);
    }

    [Fact]
    public async Task AdjudicateProposal_ThrowsSegregationOfDutiesViolation_WhenMakerAttemptsSelfApproval()
    {
        // Arrange
        var (factory, db, audit, configService, govService) = CreateTestContext();
        var openDate = new DateTime(2026, 2, 1, 8, 0, 0, DateTimeKind.Utc);
        var closeDate = new DateTime(2026, 4, 30, 23, 59, 0, DateTimeKind.Utc);
        var extCutoff = new DateTime(2026, 4, 15, 23, 59, 0, DateTimeKind.Utc);

        var proposal = await govService.SubmitScheduleProposalAsync(
            2026, openDate, closeDate, extCutoff,
            "Statutory Submission", null, "Officer_Maker", "Grant Officer Maker");

        // Act & Assert: Proposer tries to approve their own proposal
        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() =>
            govService.AdjudicateProposalAsync(
                proposal.Id,
                true,
                "Self approval attempt",
                "Officer_Maker", // Same user as ProposedByUserId!
                "Grant Officer Maker",
                new List<string> { "Admin" }));

        Assert.Contains("Maker-Checker Segregation of Duties violation", ex.Message);
    }

    [Fact]
    public async Task AdjudicateProposal_ApprovesAndAppliesToSystemConfig_WhenCheckerIsIndependent()
    {
        // Arrange
        var (factory, db, audit, configService, govService) = CreateTestContext();
        var openDate = new DateTime(2026, 2, 1, 8, 0, 0, DateTimeKind.Utc);
        var closeDate = new DateTime(2026, 4, 30, 23, 59, 0, DateTimeKind.Utc);
        var extCutoff = new DateTime(2026, 4, 15, 23, 59, 0, DateTimeKind.Utc);

        var proposal = await govService.SubmitScheduleProposalAsync(
            2026, openDate, closeDate, extCutoff,
            "Statutory Submission", "Notice 102", "Officer_Maker", "Grant Officer Maker");

        // Act: Independent checker approves
        var approved = await govService.AdjudicateProposalAsync(
            proposal.Id,
            true,
            "Approved per Executive Committee review.",
            "Executive_Checker",
            "COO / Executive Checker",
            new List<string> { "ExecutiveAuthority", "Admin" });

        // Assert
        Assert.Equal("Approved", approved.Status);
        Assert.Equal("Executive_Checker", approved.AdjudicatedByUserId);
        Assert.True(approved.AppliedToSystemConfig);

        // Verify SystemConfig was updated with the approved schedule
        var configOpen = await configService.GetValueAsync("Governance:WspWindowOpenDate", "");
        var configClose = await configService.GetValueAsync("Governance:WspAnnualSubmissionDeadline", "");
        var configExt = await configService.GetValueAsync("Governance:WspExtensionRequestDeadline", "");

        Assert.Equal(openDate.ToString("yyyy-MM-dd HH:mm"), configOpen);
        Assert.Equal(closeDate.ToString("yyyy-MM-dd HH:mm"), configClose);
        Assert.Equal(extCutoff.ToString("yyyy-MM-dd"), configExt);

        // Verify Audit Log
        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "MgWindowScheduleProposal" && a.ActionName == "ApproveSchedule" && a.RecordId == proposal.Id);
        Assert.NotNull(auditLog);
        Assert.Equal("Executive_Checker", auditLog.Actor);
    }

    [Fact]
    public async Task AdjudicateProposal_RejectionRequiresComments_AndDoesNotUpdateSystemConfig()
    {
        // Arrange
        var (factory, db, audit, configService, govService) = CreateTestContext();
        var openDate = new DateTime(2026, 2, 1, 8, 0, 0, DateTimeKind.Utc);
        var closeDate = new DateTime(2026, 4, 30, 23, 59, 0, DateTimeKind.Utc);
        var extCutoff = new DateTime(2026, 4, 15, 23, 59, 0, DateTimeKind.Utc);

        var proposal = await govService.SubmitScheduleProposalAsync(
            2026, openDate, closeDate, extCutoff,
            "Proposed schedule", null, "Officer_Maker", "Grant Officer Maker");

        // Act: Checker rejects without comments -> throws ArgumentException
        await Assert.ThrowsAsync<ArgumentException>(() =>
            govService.AdjudicateProposalAsync(
                proposal.Id,
                false,
                "", // empty comments
                "Executive_Checker",
                "Executive Checker",
                new List<string> { "Admin" }));

        // Act: Checker rejects with valid reason
        var rejected = await govService.AdjudicateProposalAsync(
            proposal.Id,
            false,
            "Rejected: Closing date must align with gazetted shutdown calendar.",
            "Executive_Checker",
            "Executive Checker",
            new List<string> { "Admin" });

        // Assert
        Assert.Equal("Rejected", rejected.Status);
        Assert.False(rejected.AppliedToSystemConfig);
        Assert.Contains("gazetted shutdown calendar", rejected.AdjudicationComments);
    }

    [Fact]
    public async Task WithdrawProposal_SetsStatusToWithdrawn_AndLogsAudit()
    {
        // Arrange
        var (factory, db, audit, configService, govService) = CreateTestContext();
        var openDate = new DateTime(2026, 2, 1, 8, 0, 0, DateTimeKind.Utc);
        var closeDate = new DateTime(2026, 4, 30, 23, 59, 0, DateTimeKind.Utc);
        var extCutoff = new DateTime(2026, 4, 15, 23, 59, 0, DateTimeKind.Utc);

        var proposal = await govService.SubmitScheduleProposalAsync(
            2026, openDate, closeDate, extCutoff,
            "Draft proposal", null, "Officer_Maker", "Grant Officer Maker");

        // Act
        var withdrawn = await govService.WithdrawProposalAsync(proposal.Id, "Officer_Maker");

        // Assert
        Assert.Equal("Withdrawn", withdrawn.Status);

        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "MgWindowScheduleProposal" && a.ActionName == "WithdrawProposal" && a.RecordId == proposal.Id);
        Assert.NotNull(auditLog);
    }
}
