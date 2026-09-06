using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Idempotent database schema migrator and seeder for the Business Rule Engine (Phase 12).
/// Creates BusinessRuleWorkflow and BusinessRule tables, creates indexes, and seeds statutory default rules.
/// </summary>
public static class Phase12BusinessRuleEngineMigrator
{
    public static async Task MigrateBusinessRuleSchemaAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        var ddl = @"
-- 1. Create BusinessRuleWorkflow Table
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'BusinessRuleWorkflow')
BEGIN
    CREATE TABLE [dbo].[BusinessRuleWorkflow] (
        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_BusinessRuleWorkflow] PRIMARY KEY CLUSTERED,
        [WorkflowName] NVARCHAR(100) NOT NULL,
        [Title] NVARCHAR(200) NOT NULL,
        [Description] NVARCHAR(1000) NULL,
        [Category] NVARCHAR(50) NOT NULL CONSTRAINT [DF_BusinessRuleWorkflow_Category] DEFAULT 'General',
        [IsActive] BIT NOT NULL CONSTRAINT [DF_BusinessRuleWorkflow_IsActive] DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_BusinessRuleWorkflow_CreatedAt] DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL
    );

    CREATE UNIQUE INDEX [IX_BusinessRuleWorkflow_WorkflowName] ON [dbo].[BusinessRuleWorkflow]([WorkflowName]);
    CREATE INDEX [IX_BusinessRuleWorkflow_Category] ON [dbo].[BusinessRuleWorkflow]([Category]);
    CREATE INDEX [IX_BusinessRuleWorkflow_IsActive] ON [dbo].[BusinessRuleWorkflow]([IsActive]);
END;

-- 2. Create BusinessRule Table
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'BusinessRule')
BEGIN
    CREATE TABLE [dbo].[BusinessRule] (
        [Id] INT IDENTITY(1,1) NOT NULL CONSTRAINT [PK_BusinessRule] PRIMARY KEY CLUSTERED,
        [BusinessRuleWorkflowId] INT NOT NULL,
        [RuleName] NVARCHAR(100) NOT NULL,
        [Expression] NVARCHAR(2000) NOT NULL,
        [RuleExpressionType] NVARCHAR(50) NOT NULL CONSTRAINT [DF_BusinessRule_Type] DEFAULT 'LambdaExpression',
        [ErrorMessage] NVARCHAR(500) NOT NULL,
        [SuccessMessage] NVARCHAR(500) NULL,
        [Severity] NVARCHAR(30) NOT NULL CONSTRAINT [DF_BusinessRule_Severity] DEFAULT 'Fatal',
        [OrderIndex] INT NOT NULL CONSTRAINT [DF_BusinessRule_OrderIndex] DEFAULT 1,
        [Enabled] BIT NOT NULL CONSTRAINT [DF_BusinessRule_Enabled] DEFAULT 1,
        [IsActive] BIT NOT NULL CONSTRAINT [DF_BusinessRule_IsActive] DEFAULT 1,
        [CreatedAt] DATETIME2 NOT NULL CONSTRAINT [DF_BusinessRule_CreatedAt] DEFAULT SYSUTCDATETIME(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT [FK_BusinessRule_Workflow] FOREIGN KEY ([BusinessRuleWorkflowId])
            REFERENCES [dbo].[BusinessRuleWorkflow]([Id]) ON DELETE CASCADE
    );

    CREATE INDEX [IX_BusinessRule_WorkflowId] ON [dbo].[BusinessRule]([BusinessRuleWorkflowId]);
    CREATE INDEX [IX_BusinessRule_RuleName] ON [dbo].[BusinessRule]([RuleName]);
    CREATE INDEX [IX_BusinessRule_Enabled] ON [dbo].[BusinessRule]([Enabled]);
    CREATE INDEX [IX_BusinessRule_IsActive] ON [dbo].[BusinessRule]([IsActive]);
END;
";

        await context.Database.ExecuteSqlRawAsync(ddl);

        // Seed default workflows and rules if empty
        if (!await context.BusinessRuleWorkflows.AnyAsync())
        {
            logger?.LogInformation("Seeding default statutory Business Rule Workflows and Rules...");

            var seedSql = @"
BEGIN TRANSACTION;

-- Insert Workflows
INSERT INTO [dbo].[BusinessRuleWorkflow] ([WorkflowName], [Title], [Description], [Category], [IsActive], [CreatedAt], [CreatedBy])
VALUES 
('LearnerStpEvaluation', 'Learner STP Straight-Through Processing Gates', 'Automated statutory risk screening gates for bulk and online learner agreement submissions.', 'LearnerRegistration', 1, SYSUTCDATETIME(), 'SYSTEM'),
('FinancialClaimApproval', 'Financial Approval & Executive Threshold Gates', 'Financial thresholds and dual maker-checker authorization limits for grant claims.', 'Finance', 1, SYSUTCDATETIME(), 'SYSTEM'),
('ArplTradeEligibility', 'Artisan RPL Qualification Category Verification', 'Statutory Section 28 ARPL category criteria and 17 designated toolkit trade checks.', 'TradeAssessment', 1, SYSUTCDATETIME(), 'SYSTEM'),
('ETQAPractitionerVerification', 'ETQA Assessor Industry Experience & Validity', 'ETQA statutory assessor & moderator 3-year post-qualification practice and validity cycles.', 'QualityAssurance', 1, SYSUTCDATETIME(), 'SYSTEM');

-- Seed LearnerStpEvaluation Rules
DECLARE @StpId INT = (SELECT [Id] FROM [dbo].[BusinessRuleWorkflow] WHERE [WorkflowName] = 'LearnerStpEvaluation');

INSERT INTO [dbo].[BusinessRule] ([BusinessRuleWorkflowId], [RuleName], [Expression], [RuleExpressionType], [ErrorMessage], [SuccessMessage], [Severity], [OrderIndex], [Enabled], [IsActive], [CreatedAt], [CreatedBy])
VALUES
(@StpId, 'EmployerStandingGate', 'HasValidEmployer || IsUnemployedBursary', 'LambdaExpression', 'Employer organisation is mandatory and must be active in the levy registry.', 'Verified employer in good standing.', 'Fatal', 1, 1, 1, SYSUTCDATETIME(), 'SYSTEM'),
(@StpId, 'ActiveQualificationGate', '!HasSaqaQualification || IsQualificationActive', 'LambdaExpression', 'Enrolled qualification is inactive or past the statutory last date for enrolment.', 'Active SAQA qualification confirmed.', 'Fatal', 2, 1, 1, SYSUTCDATETIME(), 'SYSTEM'),
(@StpId, 'SubmissionWindowSlaGate', 'WorkingDaysElapsed <= 30', 'LambdaExpression', 'Agreement execution date exceeds 30-working-day statutory SLA. Requires human officer condonation.', 'Submission timing compliant within 30 days.', 'Fatal', 3, 1, 1, SYSUTCDATETIME(), 'SYSTEM'),
(@StpId, 'MinorGuardianProtectionGate', 'Age >= 18 || HasMinorGuardian', 'LambdaExpression', 'Minor applicant (< 18) without an active verified co-signatory guardian.', 'Adult applicant or verified co-signatory guardian.', 'Fatal', 4, 1, 1, SYSUTCDATETIME(), 'SYSTEM'),
(@StpId, 'BursaryContinuationIntegrityGate', '!IsContinuationBursary || (HasPredecessorBursary && HasPassedTranscripts)', 'LambdaExpression', 'Continuation bursary requires linked predecessor bursary and passed academic transcripts.', 'Bursary continuation academic progression confirmed.', 'Fatal', 5, 1, 1, SYSUTCDATETIME(), 'SYSTEM');

-- Seed FinancialClaimApproval Rules
DECLARE @FinApprovalId INT = (SELECT [Id] FROM [dbo].[BusinessRuleWorkflow] WHERE [WorkflowName] = 'FinancialClaimApproval');

INSERT INTO [dbo].[BusinessRule] ([BusinessRuleWorkflowId], [RuleName], [Expression], [RuleExpressionType], [ErrorMessage], [SuccessMessage], [Severity], [OrderIndex], [Enabled], [IsActive], [CreatedAt], [CreatedBy])
VALUES
(@FinApprovalId, 'CfoThresholdGate', 'ClaimAmount < 500000.00 || RequiresCfoApproval', 'LambdaExpression', 'Discretionary Grant claims of R500,000.00 or more strictly mandate CFO executive sign-off.', 'Financial claim threshold satisfied.', 'Fatal', 1, 1, 1, SYSUTCDATETIME(), 'SYSTEM');

-- Seed ArplTradeEligibility Rules
DECLARE @ArplId INT = (SELECT [Id] FROM [dbo].[BusinessRuleWorkflow] WHERE [WorkflowName] = 'ArplTradeEligibility');

INSERT INTO [dbo].[BusinessRule] ([BusinessRuleWorkflowId], [RuleName], [Expression], [RuleExpressionType], [ErrorMessage], [SuccessMessage], [Severity], [OrderIndex], [Enabled], [IsActive], [CreatedAt], [CreatedBy])
VALUES
(@ArplId, 'ExperienceThresholdGate', 'ExperienceMonths >= RequiredExperienceMonths', 'LambdaExpression', 'Candidate work experience does not meet the statutory minimum months for the chosen category.', 'Experience threshold satisfied.', 'Fatal', 1, 1, 1, SYSUTCDATETIME(), 'SYSTEM'),
(@ArplId, 'ToolkitTradeRestrictionGate', '!IsCategory7 || IsDesignatedToolkitTrade', 'LambdaExpression', 'Category 7 admissions are strictly restricted to the 17 statutory designated toolkit trades.', 'Toolkit trade scope verified.', 'Fatal', 2, 1, 1, SYSUTCDATETIME(), 'SYSTEM');

-- Seed ETQAPractitionerVerification Rules
DECLARE @EtqaId INT = (SELECT [Id] FROM [dbo].[BusinessRuleWorkflow] WHERE [WorkflowName] = 'ETQAPractitionerVerification');

INSERT INTO [dbo].[BusinessRule] ([BusinessRuleWorkflowId], [RuleName], [Expression], [RuleExpressionType], [ErrorMessage], [SuccessMessage], [Severity], [OrderIndex], [Enabled], [IsActive], [CreatedAt], [CreatedBy])
VALUES
(@EtqaId, 'MinimumThreeYearsPracticeGate', 'YearsSinceQualification >= 3.0', 'LambdaExpression', 'Practitioners must have at least 3 years of post-qualification industry experience before applying.', 'Experience requirement met.', 'Fatal', 1, 1, 1, SYSUTCDATETIME(), 'SYSTEM');

COMMIT TRANSACTION;
";
            await context.Database.ExecuteSqlRawAsync(seedSql);
            logger?.LogInformation("Successfully seeded default Business Rule Workflows and Rules.");
        }
    }
}
