using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Models;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class BusinessRuleEngineTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, SystemConfigurationService sysConfig, BusinessRuleEngineService ruleEngine) CreateTestContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var config = new ConfigurationBuilder().AddInMemoryCollection(new Dictionary<string, string?>()).Build();
        var sysConfig = new SystemConfigurationService(factory, config, audit);
        var ruleEngine = new BusinessRuleEngineService(factory, audit);

        return (factory, db, audit, sysConfig, ruleEngine);
    }

    [Fact]
    public async Task EvaluateWorkflow_LearnerStp_CompliantAdult_PassesAllRules()
    {
        var (_, _, _, _, ruleEngine) = CreateTestContext();

        var payload = new LearnerStpPayload
        {
            PersonId = 101,
            Age = 25.0,
            HasMinorGuardian = true,
            HasValidEmployer = true,
            IsEmployerActive = true,
            IsUnemployedBursary = false,
            HasSaqaQualification = true,
            IsQualificationActive = true,
            WorkingDaysElapsed = 15,
            IsContinuationBursary = false,
            HasPredecessorBursary = false,
            HasPassedTranscripts = false
        };

        var result = await ruleEngine.EvaluateWorkflowAsync("LearnerStpEvaluation", payload);

        Assert.True(result.IsSuccess);
        Assert.Equal(5, result.TotalRulesEvaluated);
        Assert.Equal(5, result.PassedCount);
        Assert.Empty(result.Failures);
    }

    [Fact]
    public async Task EvaluateWorkflow_LearnerStp_MinorWithoutGuardian_FailsProtectionGate()
    {
        var (_, _, _, _, ruleEngine) = CreateTestContext();

        var payload = new LearnerStpPayload
        {
            PersonId = 102,
            Age = 16.5,
            HasMinorGuardian = false,
            HasValidEmployer = true,
            IsEmployerActive = true,
            IsUnemployedBursary = false,
            HasSaqaQualification = true,
            IsQualificationActive = true,
            WorkingDaysElapsed = 10,
            IsContinuationBursary = false
        };

        var result = await ruleEngine.EvaluateWorkflowAsync("LearnerStpEvaluation", payload);

        Assert.False(result.IsSuccess);
        Assert.Contains(result.Failures, f => f.RuleName == "MinorGuardianProtectionGate");
    }

    [Fact]
    public async Task EvaluateWorkflow_LearnerStp_OverdueSubmission_FailsSlaGate()
    {
        var (_, _, _, _, ruleEngine) = CreateTestContext();

        var payload = new LearnerStpPayload
        {
            PersonId = 103,
            Age = 22.0,
            HasMinorGuardian = true,
            HasValidEmployer = true,
            IsEmployerActive = true,
            IsUnemployedBursary = false,
            HasSaqaQualification = true,
            IsQualificationActive = true,
            WorkingDaysElapsed = 45, // Exceeds 30-day statutory limit
            IsContinuationBursary = false
        };

        var result = await ruleEngine.EvaluateWorkflowAsync("LearnerStpEvaluation", payload);

        Assert.False(result.IsSuccess);
        Assert.Contains(result.Failures, f => f.RuleName == "SubmissionWindowSlaGate");
    }

    [Fact]
    public async Task EvaluateWorkflow_LearnerStp_ContinuationMissingTranscripts_FailsContinuationGate()
    {
        var (_, _, _, _, ruleEngine) = CreateTestContext();

        var payload = new LearnerStpPayload
        {
            PersonId = 104,
            Age = 21.0,
            HasMinorGuardian = true,
            HasValidEmployer = true,
            IsEmployerActive = true,
            IsUnemployedBursary = true,
            HasSaqaQualification = true,
            IsQualificationActive = true,
            WorkingDaysElapsed = 12,
            IsContinuationBursary = true,
            HasPredecessorBursary = true,
            HasPassedTranscripts = false // Failed prior transcripts
        };

        var result = await ruleEngine.EvaluateWorkflowAsync("LearnerStpEvaluation", payload);

        Assert.False(result.IsSuccess);
        Assert.Contains(result.Failures, f => f.RuleName == "BursaryContinuationIntegrityGate");
    }

    [Fact]
    public async Task TestRuleExpression_InteractiveSandbox_ValidExpression_EvaluatesSuccessfully()
    {
        var (_, _, _, _, ruleEngine) = CreateTestContext();

        var request = new RuleSandboxTestRequest
        {
            Expression = "Age >= 18 || HasGuardian",
            SampleJsonPayload = "{\"Age\": 21.5, \"HasGuardian\": false}"
        };

        var result = await ruleEngine.TestRuleExpressionAsync(request);

        Assert.True(result.IsSuccess);
        Assert.True(result.ExpressionEvaluatedToTrue);
        Assert.Null(result.ErrorMessage);
    }

    [Fact]
    public async Task TestRuleExpression_InteractiveSandbox_FailingExpression_EvaluatesToFalse()
    {
        var (_, _, _, _, ruleEngine) = CreateTestContext();

        var request = new RuleSandboxTestRequest
        {
            Expression = "ClaimAmount < 500000.00",
            SampleJsonPayload = "{\"ClaimAmount\": 750000.00}"
        };

        var result = await ruleEngine.TestRuleExpressionAsync(request);

        Assert.True(result.IsSuccess);
        Assert.False(result.ExpressionEvaluatedToTrue);
        Assert.NotNull(result.ErrorMessage);
    }

    [Fact]
    public async Task DiscretionaryGrantClaim_DynamicThreshold_ResolvesViaSystemConfig()
    {
        var (factory, db, audit, sysConfig, _) = CreateTestContext();

        // Configure custom CFO threshold in system configuration
        await sysConfig.SetConfigAsync("FinancialRules.DualApprovalCfoThreshold", "300000.00", "Finance");

        // Seed GrantMoa and PIP
        var moa = new GrantMoa
        {
            MoaNumber = "MOA-TEST-01",
            TotalContractValue = 1000000m,
            MoaStatusCode = "Active",
            ContractStartDate = DateTime.UtcNow.AddMonths(-1),
            ContractEndDate = DateTime.UtcNow.AddYears(1)
        };
        db.GrantMoas.Add(moa);
        await db.SaveChangesAsync();

        var pip = new ProjectImplementationPlan
        {
            GrantMoaId = moa.Id,
            TotalAwardedAmount = 1000000m,
            StatusCode = "Approved"
        };
        db.ProjectImplementationPlans.Add(pip);
        await db.SaveChangesAsync();

        var claimService = new DiscretionaryGrantClaimService(factory, audit, sysConfig);

        // Submit claim of R350,000 (exceeds R300,000 configured threshold)
        var claim = await claimService.SubmitTrancheClaimAsync(new SubmitDgClaimRequest
        {
            ProjectImplementationPlanId = pip.Id,
            TrancheNumber = 1,
            ClaimAmount = 350000m,
            DeliverableDescription = "Phase 1 deliverable"
        });

        Assert.True(claim.RequiresCfoApproval);
    }

    [Fact]
    public async Task AssessorRegistration_DynamicExperienceThreshold_EnforcesCustomThreshold()
    {
        var (factory, db, audit, sysConfig, _) = CreateTestContext();

        // Configure 5.0 years experience requirement in system configuration
        await sysConfig.SetConfigAsync("StatutorySlas.AssessorMinExperienceYears", "5.0", "Statutory");

        var app = new AssessorRegistrationApplication
        {
            ApplicationNumber = "APP-TEST-01",
            PractitionerType = "Assessor",
            ApplicationStatusCode = "Draft"
        };
        db.AssessorRegistrationApplications.Add(app);
        await db.SaveChangesAsync();

        var assessorService = new AssessorRegistrationService(factory, audit, sysConfig);

        // 4 years experience: fails 5.0 years requirement
        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() =>
            assessorService.AddQualificationScopeAsync(app.Id, new AddQualificationScopeRequest
            {
                SaqaQualificationId = 58761,
                QualificationTitle = "National Certificate: Engineering Fabrication",
                QualificationObtainedDate = DateTime.UtcNow.AddYears(-4)
            }));

        Assert.Contains("5 years", ex.Message);
    }
}
