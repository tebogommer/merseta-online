using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Idempotent database schema migrator adding statutory WSP compliance columns to GrantApplication.
/// </summary>
public static class Phase14WspEligibilityAndMoaProvisioningMigrator
{
    public static async Task MigrateAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        if (context.Database.IsSqlServer())
        {
            const string ddlSql = @"
                IF EXISTS (SELECT * FROM sys.tables WHERE name = 'GrantApplication')
                BEGIN
                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantApplication') AND name = 'WspSubmissionId')
                        ALTER TABLE [dbo].[GrantApplication] ADD [WspSubmissionId] INT NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantApplication') AND name = 'IsWspCompliant')
                        ALTER TABLE [dbo].[GrantApplication] ADD [IsWspCompliant] BIT NOT NULL DEFAULT 0;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantApplication') AND name = 'IsWspExempt')
                        ALTER TABLE [dbo].[GrantApplication] ADD [IsWspExempt] BIT NOT NULL DEFAULT 0;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantApplication') AND name = 'WspExemptionReason')
                        ALTER TABLE [dbo].[GrantApplication] ADD [WspExemptionReason] NVARCHAR(500) NULL;

                    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_GrantApplication_WspSubmission' AND object_id = OBJECT_ID('GrantApplication'))
                        CREATE INDEX [IX_GrantApplication_WspSubmission] ON [dbo].[GrantApplication] ([WspSubmissionId]);
                END;

                IF EXISTS (SELECT * FROM sys.tables WHERE name = 'GrantFundingWindow')
                BEGIN
                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('GrantFundingWindow') AND name = 'Description')
                        ALTER TABLE [dbo].[GrantFundingWindow] ADD [Description] NVARCHAR(MAX) NULL;
                END;

                IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'ProjectImplementationPlan')
                BEGIN
                    CREATE TABLE [dbo].[ProjectImplementationPlan] (
                        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
                        [OrganisationId] INT NOT NULL,
                        [FundingWindowId] INT NULL,
                        [GrantApplicationId] INT NULL,
                        [PlanReferenceNumber] NVARCHAR(50) NOT NULL,
                        [InterventionTypeCode] NVARCHAR(50) NOT NULL DEFAULT 'Learnership',
                        [TotalAwardedAmount] DECIMAL(18,2) NOT NULL DEFAULT 0.00,
                        [RecoverableAmount] DECIMAL(18,2) NOT NULL DEFAULT 0.00,
                        [TotalLearnersAwarded] INT NOT NULL DEFAULT 0,
                        [LearnersWithDisabilityCount] INT NOT NULL DEFAULT 0,
                        [StatusCode] NVARCHAR(50) NOT NULL DEFAULT 'Draft',
                        [ContractSignOffDate] DATETIME2 NULL,
                        [CreatedAt] DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
                        [CreatedBy] NVARCHAR(100) NULL,
                        [ModifiedAt] DATETIME2 NULL,
                        [ModifiedBy] NVARCHAR(100) NULL
                    );
                    CREATE INDEX [IX_ProjectImplementationPlan_Org] ON [dbo].[ProjectImplementationPlan] ([OrganisationId]);
                    CREATE INDEX [IX_ProjectImplementationPlan_Window] ON [dbo].[ProjectImplementationPlan] ([FundingWindowId]);
                    CREATE INDEX [IX_ProjectImplementationPlan_App] ON [dbo].[ProjectImplementationPlan] ([GrantApplicationId]);
                    CREATE INDEX [IX_ProjectImplementationPlan_Ref] ON [dbo].[ProjectImplementationPlan] ([PlanReferenceNumber]);
                    CREATE INDEX [IX_ProjectImplementationPlan_Status] ON [dbo].[ProjectImplementationPlan] ([StatusCode]);
                END;

                IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'PipLearnerAllocation')
                BEGIN
                    CREATE TABLE [dbo].[PipLearnerAllocation] (
                        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
                        [ProjectImplementationPlanId] INT NOT NULL,
                        [SaqaQualificationId] INT NULL,
                        [QualificationTitle] NVARCHAR(250) NULL,
                        [LearnerCount] INT NOT NULL DEFAULT 0,
                        [UnitCost] DECIMAL(18,2) NOT NULL DEFAULT 0.00,
                        [TotalAllowanceBudget] DECIMAL(18,2) NOT NULL DEFAULT 0.00,
                        [TotalTuitionBudget] DECIMAL(18,2) NOT NULL DEFAULT 0.00,
                        [CreatedAt] DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
                        [CreatedBy] NVARCHAR(100) NULL,
                        [ModifiedAt] DATETIME2 NULL,
                        [ModifiedBy] NVARCHAR(100) NULL
                    );
                    CREATE INDEX [IX_PipLearnerAllocation_Plan] ON [dbo].[PipLearnerAllocation] ([ProjectImplementationPlanId]);
                END;

                IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'GrantPaymentClaim')
                BEGIN
                    CREATE TABLE [dbo].[GrantPaymentClaim] (
                        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
                        [ProjectImplementationPlanId] INT NOT NULL,
                        [ClaimNumber] NVARCHAR(50) NOT NULL,
                        [TrancheNumber] INT NOT NULL DEFAULT 1,
                        [ClaimAmount] DECIMAL(18,2) NOT NULL DEFAULT 0.00,
                        [DeliverableDescription] NVARCHAR(1000) NOT NULL DEFAULT '',
                        [StatusCode] NVARCHAR(50) NOT NULL DEFAULT 'PendingSubmission',
                        [ApprovalDate] DATETIME2 NULL,
                        [ApprovedByUserId] NVARCHAR(100) NULL,
                        [ErpBatchNumber] NVARCHAR(100) NULL,
                        [CreatedAt] DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
                        [CreatedBy] NVARCHAR(100) NULL,
                        [ModifiedAt] DATETIME2 NULL,
                        [ModifiedBy] NVARCHAR(100) NULL
                    );
                    CREATE INDEX [IX_GrantPaymentClaim_Plan] ON [dbo].[GrantPaymentClaim] ([ProjectImplementationPlanId]);
                    CREATE INDEX [IX_GrantPaymentClaim_ClaimNumber] ON [dbo].[GrantPaymentClaim] ([ClaimNumber]);
                    CREATE INDEX [IX_GrantPaymentClaim_Status] ON [dbo].[GrantPaymentClaim] ([StatusCode]);
                END;

                -- Contract Variations & Addenda Schema Alignment
                IF EXISTS (SELECT * FROM sys.tables WHERE name = 'ContractAddenda')
                BEGIN
                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('ContractAddenda') AND name = 'VariationTypeCode')
                        ALTER TABLE [dbo].[ContractAddenda] ADD [VariationTypeCode] NVARCHAR(50) NOT NULL DEFAULT 'TimelineExtension';

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('ContractAddenda') AND name = 'LegalReviewerUserId')
                        ALTER TABLE [dbo].[ContractAddenda] ADD [LegalReviewerUserId] NVARCHAR(100) NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('ContractAddenda') AND name = 'LegalReviewDate')
                        ALTER TABLE [dbo].[ContractAddenda] ADD [LegalReviewDate] DATETIME2 NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('ContractAddenda') AND name = 'LegalReviewComments')
                        ALTER TABLE [dbo].[ContractAddenda] ADD [LegalReviewComments] NVARCHAR(MAX) NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('ContractAddenda') AND name = 'ExecutiveApprovedByUserId')
                        ALTER TABLE [dbo].[ContractAddenda] ADD [ExecutiveApprovedByUserId] NVARCHAR(100) NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('ContractAddenda') AND name = 'ExecutiveApprovalDate')
                        ALTER TABLE [dbo].[ContractAddenda] ADD [ExecutiveApprovalDate] DATETIME2 NULL;
                END
                ELSE
                BEGIN
                    CREATE TABLE [dbo].[ContractAddenda] (
                        [Id] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
                        [GrantMoaId] INT NOT NULL,
                        [AddendaNumber] NVARCHAR(50) NOT NULL,
                        [VariationTypeCode] NVARCHAR(50) NOT NULL DEFAULT 'TimelineExtension',
                        [OriginalContractValue] DECIMAL(18,2) NOT NULL DEFAULT 0.00,
                        [RevisedContractValue] DECIMAL(18,2) NOT NULL DEFAULT 0.00,
                        [OriginalEndDate] DATETIME2 NOT NULL,
                        [RevisedEndDate] DATETIME2 NOT NULL,
                        [MotivationReason] NVARCHAR(1000) NOT NULL DEFAULT '',
                        [StatusCode] NVARCHAR(50) NOT NULL DEFAULT 'Draft',
                        [LegalReviewerUserId] NVARCHAR(100) NULL,
                        [LegalReviewDate] DATETIME2 NULL,
                        [LegalReviewComments] NVARCHAR(MAX) NULL,
                        [ExecutiveApprovedByUserId] NVARCHAR(100) NULL,
                        [ExecutiveApprovalDate] DATETIME2 NULL,
                        [CreatedAt] DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
                        [CreatedBy] NVARCHAR(100) NOT NULL DEFAULT 'SYSTEM',
                        [ModifiedAt] DATETIME2 NULL,
                        [ModifiedBy] NVARCHAR(100) NULL
                    );
                    CREATE UNIQUE INDEX [UX_ContractAddenda_Number] ON [dbo].[ContractAddenda] ([AddendaNumber]);
                    CREATE INDEX [IX_ContractAddenda_Moa] ON [dbo].[ContractAddenda] ([GrantMoaId]);
                    CREATE INDEX [IX_ContractAddenda_Status] ON [dbo].[ContractAddenda] ([StatusCode]);
                END;

                IF EXISTS (SELECT * FROM sys.tables WHERE name = 'ContractExtensionRequest')
                BEGIN
                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('ContractExtensionRequest') AND name = 'ReviewedByUserId')
                        ALTER TABLE [dbo].[ContractExtensionRequest] ADD [ReviewedByUserId] NVARCHAR(100) NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('ContractExtensionRequest') AND name = 'ReviewDate')
                        ALTER TABLE [dbo].[ContractExtensionRequest] ADD [ReviewDate] DATETIME2 NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('ContractExtensionRequest') AND name = 'ReviewerComments')
                        ALTER TABLE [dbo].[ContractExtensionRequest] ADD [ReviewerComments] NVARCHAR(MAX) NULL;
                END;

                IF EXISTS (SELECT * FROM sys.tables WHERE name = 'ContractTerminationRequest')
                BEGIN
                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('ContractTerminationRequest') AND name = 'TerminationReasonCode')
                        ALTER TABLE [dbo].[ContractTerminationRequest] ADD [TerminationReasonCode] NVARCHAR(50) NOT NULL DEFAULT 'MutualAgreement';

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('ContractTerminationRequest') AND name = 'TotalFundsDisbursedToDate')
                        ALTER TABLE [dbo].[ContractTerminationRequest] ADD [TotalFundsDisbursedToDate] DECIMAL(18,2) NOT NULL DEFAULT 0.00;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('ContractTerminationRequest') AND name = 'TotalValueDeliverablesAchieved')
                        ALTER TABLE [dbo].[ContractTerminationRequest] ADD [TotalValueDeliverablesAchieved] DECIMAL(18,2) NOT NULL DEFAULT 0.00;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('ContractTerminationRequest') AND name = 'ClawbackAmountRecoverable')
                        ALTER TABLE [dbo].[ContractTerminationRequest] ADD [ClawbackAmountRecoverable] DECIMAL(18,2) NOT NULL DEFAULT 0.00;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('ContractTerminationRequest') AND name = 'DetailedMotivation')
                        ALTER TABLE [dbo].[ContractTerminationRequest] ADD [DetailedMotivation] NVARCHAR(1000) NOT NULL DEFAULT '';

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('ContractTerminationRequest') AND name = 'SettledByUserId')
                        ALTER TABLE [dbo].[ContractTerminationRequest] ADD [SettledByUserId] NVARCHAR(100) NULL;

                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('ContractTerminationRequest') AND name = 'SettlementDate')
                        ALTER TABLE [dbo].[ContractTerminationRequest] ADD [SettlementDate] DATETIME2 NULL;
                END;
            ";

            try
            {
                await context.Database.ExecuteSqlRawAsync(ddlSql);
                logger?.LogInformation("Phase 14 WSP Eligibility and MOA Provisioning schema migration executed successfully.");
            }
            catch (Exception ex)
            {
                logger?.LogWarning(ex, "Phase 14 migration encountered a non-critical notice.");
            }
        }
    }
}
