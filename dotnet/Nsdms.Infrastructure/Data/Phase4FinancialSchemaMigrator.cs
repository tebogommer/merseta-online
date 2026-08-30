using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Nsdms.Domain.Entities;

namespace Nsdms.Infrastructure.Data;

public static class Phase4FinancialSchemaMigrator
{
    public static async Task MigrateFinancialSchemaAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        var ddl = @"
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'grant_moa')
BEGIN
    CREATE TABLE [dbo].[grant_moa] (
        [Id] INT IDENTITY(1,1) PRIMARY KEY,
        [GrantApplicationId] INT NOT NULL,
        [MoaNumber] NVARCHAR(100) NOT NULL,
        [ContractStartDate] DATETIME2 NOT NULL,
        [ContractEndDate] DATETIME2 NOT NULL,
        [TotalContractValue] DECIMAL(18,2) NOT NULL,
        [SignoffDateEmployer] DATETIME2 NULL,
        [SignoffDateSeta] DATETIME2 NULL,
        [SignoffDocumentUri] NVARCHAR(500) NULL,
        [MoaStatusCode] NVARCHAR(50) NOT NULL DEFAULT 'Draft',
        [SpecialConditions] NVARCHAR(2000) NULL,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT [FK_grant_moa_GrantApplication] FOREIGN KEY ([GrantApplicationId]) REFERENCES [dbo].[GrantApplication]([Id])
    );
    CREATE UNIQUE INDEX [IX_grant_moa_MoaNumber] ON [dbo].[grant_moa]([MoaNumber]);
    CREATE INDEX [IX_grant_moa_GrantApplicationId] ON [dbo].[grant_moa]([GrantApplicationId]);
    CREATE INDEX [IX_grant_moa_MoaStatusCode] ON [dbo].[grant_moa]([MoaStatusCode]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'grant_moa_milestone')
BEGIN
    CREATE TABLE [dbo].[grant_moa_milestone] (
        [Id] INT IDENTITY(1,1) PRIMARY KEY,
        [GrantMoaId] INT NOT NULL,
        [MilestoneNumber] INT NOT NULL,
        [MilestoneTitle] NVARCHAR(200) NOT NULL,
        [MilestoneDescription] NVARCHAR(MAX) NULL,
        [DeliverableRequirement] NVARCHAR(1000) NULL,
        [TranchePercentage] DECIMAL(5,2) NOT NULL,
        [TrancheAmount] DECIMAL(18,2) NOT NULL,
        [TargetDueDate] DATETIME2 NOT NULL,
        [MilestoneStatusCode] NVARCHAR(50) NOT NULL DEFAULT 'Pending',
        [VerificationDate] DATETIME2 NULL,
        [VerifiedByUserId] NVARCHAR(100) NULL,
        [VerificationComments] NVARCHAR(1000) NULL,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT [FK_grant_moa_milestone_GrantMoa] FOREIGN KEY ([GrantMoaId]) REFERENCES [dbo].[grant_moa]([Id]) ON DELETE CASCADE
    );
    CREATE INDEX [IX_grant_moa_milestone_GrantMoaId] ON [dbo].[grant_moa_milestone]([GrantMoaId]);
    CREATE INDEX [IX_grant_moa_milestone_MilestoneStatusCode] ON [dbo].[grant_moa_milestone]([MilestoneStatusCode]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'grant_tranche_payment')
BEGIN
    CREATE TABLE [dbo].[grant_tranche_payment] (
        [Id] INT IDENTITY(1,1) PRIMARY KEY,
        [GrantMoaMilestoneId] INT NOT NULL,
        [GrantApplicationId] INT NOT NULL,
        [PaymentReferenceNumber] NVARCHAR(100) NOT NULL,
        [InvoiceNumber] NVARCHAR(100) NOT NULL,
        [InvoiceDate] DATETIME2 NOT NULL,
        [ClaimedAmount] DECIMAL(18,2) NOT NULL,
        [ApprovedPaymentAmount] DECIMAL(18,2) NOT NULL,
        [PaymentStatusCode] NVARCHAR(50) NOT NULL DEFAULT 'Draft',
        [PaymentDate] DATETIME2 NULL,
        [BatchNumber] NVARCHAR(100) NULL,
        [BankReference] NVARCHAR(100) NULL,
        [FinanceApproverUserId] NVARCHAR(100) NULL,
        [FinanceApprovalDate] DATETIME2 NULL,
        [ApprovalComments] NVARCHAR(1000) NULL,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT [FK_grant_tranche_payment_Milestone] FOREIGN KEY ([GrantMoaMilestoneId]) REFERENCES [dbo].[grant_moa_milestone]([Id]),
        CONSTRAINT [FK_grant_tranche_payment_GrantApp] FOREIGN KEY ([GrantApplicationId]) REFERENCES [dbo].[GrantApplication]([Id])
    );
    CREATE UNIQUE INDEX [IX_grant_tranche_payment_PaymentReference] ON [dbo].[grant_tranche_payment]([PaymentReferenceNumber]);
    CREATE INDEX [IX_grant_tranche_payment_MilestoneId] ON [dbo].[grant_tranche_payment]([GrantMoaMilestoneId]);
    CREATE INDEX [IX_grant_tranche_payment_GrantAppId] ON [dbo].[grant_tranche_payment]([GrantApplicationId]);
    CREATE INDEX [IX_grant_tranche_payment_PaymentStatus] ON [dbo].[grant_tranche_payment]([PaymentStatusCode]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'mandatory_grant_disbursement')
BEGIN
    CREATE TABLE [dbo].[mandatory_grant_disbursement] (
        [Id] INT IDENTITY(1,1) PRIMARY KEY,
        [WspSubmissionId] INT NOT NULL,
        [OrganisationId] INT NOT NULL,
        [DisbursementReference] NVARCHAR(100) NOT NULL,
        [FinYear] INT NOT NULL,
        [LevyPeriod] NVARCHAR(50) NULL,
        [LeviesReceivedAmount] DECIMAL(18,2) NOT NULL,
        [CalculatedRebateAmount] DECIMAL(18,2) NOT NULL,
        [DisbursementStatusCode] NVARCHAR(50) NOT NULL DEFAULT 'Calculated',
        [PaymentDate] DATETIME2 NULL,
        [BatchNumber] NVARCHAR(100) NULL,
        [BankAccountSnapshot] NVARCHAR(200) NULL,
        [Comments] NVARCHAR(1000) NULL,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT [FK_mandatory_grant_disbursement_Wsp] FOREIGN KEY ([WspSubmissionId]) REFERENCES [dbo].[WspSubmission]([Id]),
        CONSTRAINT [FK_mandatory_grant_disbursement_Org] FOREIGN KEY ([OrganisationId]) REFERENCES [dbo].[Organisation]([Id])
    );
    CREATE UNIQUE INDEX [IX_mandatory_grant_disb_Ref] ON [dbo].[mandatory_grant_disbursement]([DisbursementReference]);
    CREATE INDEX [IX_mandatory_grant_disb_WspId] ON [dbo].[mandatory_grant_disbursement]([WspSubmissionId]);
    CREATE INDEX [IX_mandatory_grant_disb_OrgId] ON [dbo].[mandatory_grant_disbursement]([OrganisationId]);
    CREATE INDEX [IX_mandatory_grant_disb_Status] ON [dbo].[mandatory_grant_disbursement]([DisbursementStatusCode]);
END

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'inter_seta_transfer')
BEGIN
    CREATE TABLE [dbo].[inter_seta_transfer] (
        [Id] INT IDENTITY(1,1) PRIMARY KEY,
        [OrganisationId] INT NOT NULL,
        [TransferType] NVARCHAR(50) NOT NULL,
        [OtherSetaCode] NVARCHAR(50) NOT NULL,
        [OtherSetaName] NVARCHAR(150) NOT NULL,
        [TransferReason] NVARCHAR(500) NOT NULL,
        [EffectiveDate] DATETIME2 NOT NULL,
        [TransferStatusCode] NVARCHAR(50) NOT NULL DEFAULT 'Initiated',
        [TransferAmount] DECIMAL(18,2) NOT NULL,
        [SetaApprovalReference] NVARCHAR(100) NULL,
        [DhetReferenceNumber] NVARCHAR(100) NULL,
        [Comments] NVARCHAR(1000) NULL,
        [CreatedAt] DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
        [CreatedBy] NVARCHAR(100) NULL,
        [ModifiedAt] DATETIME2 NULL,
        [ModifiedBy] NVARCHAR(100) NULL,
        CONSTRAINT [FK_inter_seta_transfer_Org] FOREIGN KEY ([OrganisationId]) REFERENCES [dbo].[Organisation]([Id])
    );
    CREATE INDEX [IX_inter_seta_transfer_OrgId] ON [dbo].[inter_seta_transfer]([OrganisationId]);
    CREATE INDEX [IX_inter_seta_transfer_Status] ON [dbo].[inter_seta_transfer]([TransferStatusCode]);
    CREATE INDEX [IX_inter_seta_transfer_OtherSeta] ON [dbo].[inter_seta_transfer]([OtherSetaCode]);
END
";

        await context.Database.ExecuteSqlRawAsync(ddl);
        logger?.LogInformation("Phase 4 Financial Governance & MOA DDL verified and executed successfully.");

        // Seed Sample MOAs, Milestones, Payments, and Inter-SETA transfers
        await SeedFinancialSampleDataAsync(context);
    }

    private static async Task SeedFinancialSampleDataAsync(NsdmsDbContext context)
    {
        if (await context.GrantMoas.AnyAsync())
        {
            return;
        }

        var grants = await context.GrantApplications.Include(g => g.Organisation).ToListAsync();
        var wsps = await context.WspSubmissions.Include(w => w.Organisation).ToListAsync();
        var orgs = await context.Organisations.ToListAsync();

        if (grants.Count > 0)
        {
            var g1 = grants[0];
            var moa1 = new GrantMoa
            {
                GrantApplicationId = g1.Id,
                MoaNumber = $"MOA-2026-{g1.ApplicationNumber}",
                ContractStartDate = new DateTime(2026, 4, 1),
                ContractEndDate = new DateTime(2027, 3, 31),
                TotalContractValue = g1.ApprovedAmount ?? g1.RequestedAmount,
                SignoffDateEmployer = new DateTime(2026, 4, 10),
                SignoffDateSeta = new DateTime(2026, 4, 15),
                MoaStatusCode = "Active",
                SpecialConditions = "Learner progress reports required quarterly; 100% attendance register submission prior to milestone 2 payment.",
                CreatedBy = "System Administrator"
            };
            context.GrantMoas.Add(moa1);
            await context.SaveChangesAsync();

            // Milestones for MOA 1
            var m1 = new GrantMoaMilestone
            {
                GrantMoaId = moa1.Id,
                MilestoneNumber = 1,
                MilestoneTitle = "Contracting & Inception",
                MilestoneDescription = "Execution of tripartite contracts and learner selection verification.",
                DeliverableRequirement = "Signed MOA and certified learner identity documents.",
                TranchePercentage = 30.0m,
                TrancheAmount = moa1.TotalContractValue * 0.30m,
                TargetDueDate = new DateTime(2026, 5, 31),
                MilestoneStatusCode = "Paid",
                VerificationDate = new DateTime(2026, 5, 15),
                VerifiedByUserId = "clo@merseta.org.za",
                VerificationComments = "All contracts verified against workplace approval mentor ratios."
            };

            var m2 = new GrantMoaMilestone
            {
                GrantMoaId = moa1.Id,
                MilestoneNumber = 2,
                MilestoneTitle = "Midterm Delivery & Practical Logbooks",
                MilestoneDescription = "50% theoretical coursework completed and practical logbooks stamped.",
                DeliverableRequirement = "Midterm assessment reports and accredited provider attendance records.",
                TranchePercentage = 30.0m,
                TrancheAmount = moa1.TotalContractValue * 0.30m,
                TargetDueDate = new DateTime(2026, 9, 30),
                MilestoneStatusCode = "Verified",
                VerificationDate = new DateTime(2026, 9, 10),
                VerifiedByUserId = "clo@merseta.org.za",
                VerificationComments = "Site inspection confirmed active workshop participation."
            };

            var m3 = new GrantMoaMilestone
            {
                GrantMoaId = moa1.Id,
                MilestoneNumber = 3,
                MilestoneTitle = "Final Assessment & Exit Moderation",
                MilestoneDescription = "Summative assessments completed and external moderation registered.",
                DeliverableRequirement = "ETQA moderator statement of results and final attendance registers.",
                TranchePercentage = 20.0m,
                TrancheAmount = moa1.TotalContractValue * 0.20m,
                TargetDueDate = new DateTime(2026, 12, 15),
                MilestoneStatusCode = "Pending"
            };

            var m4 = new GrantMoaMilestone
            {
                GrantMoaId = moa1.Id,
                MilestoneNumber = 4,
                MilestoneTitle = "Trade Test & Certification Closeout",
                MilestoneDescription = "Trade testing completed and closeout audit signed.",
                DeliverableRequirement = "Serial Trade Test certificates and closeout financial expenditure report.",
                TranchePercentage = 20.0m,
                TrancheAmount = moa1.TotalContractValue * 0.20m,
                TargetDueDate = new DateTime(2027, 3, 31),
                MilestoneStatusCode = "Pending"
            };

            context.GrantMoaMilestones.AddRange(m1, m2, m3, m4);
            await context.SaveChangesAsync();

            // Tranche Payment for M1
            var pay1 = new GrantTranchePayment
            {
                GrantMoaMilestoneId = m1.Id,
                GrantApplicationId = g1.Id,
                PaymentReferenceNumber = $"PAY-2026-{m1.Id:D4}-01",
                InvoiceNumber = "INV-TOYOTA-DG-001",
                InvoiceDate = new DateTime(2026, 5, 20),
                ClaimedAmount = m1.TrancheAmount,
                ApprovedPaymentAmount = m1.TrancheAmount,
                PaymentStatusCode = "Paid",
                PaymentDate = new DateTime(2026, 5, 28),
                BatchNumber = "FIN-BATCH-2026-0528",
                BankReference = "MERSETA-DG-L700100200",
                FinanceApproverUserId = "cfo@merseta.org.za",
                FinanceApprovalDate = new DateTime(2026, 5, 25),
                ApprovalComments = "Approved per signed MOA milestone 1 verification."
            };

            // Tranche Payment for M2 (Submitted for approval)
            var pay2 = new GrantTranchePayment
            {
                GrantMoaMilestoneId = m2.Id,
                GrantApplicationId = g1.Id,
                PaymentReferenceNumber = $"PAY-2026-{m2.Id:D4}-02",
                InvoiceNumber = "INV-TOYOTA-DG-002",
                InvoiceDate = new DateTime(2026, 9, 12),
                ClaimedAmount = m2.TrancheAmount,
                ApprovedPaymentAmount = m2.TrancheAmount,
                PaymentStatusCode = "Finance Approved",
                BatchNumber = "FIN-BATCH-2026-0915",
                FinanceApproverUserId = "cfo@merseta.org.za",
                FinanceApprovalDate = new DateTime(2026, 9, 14),
                ApprovalComments = "Verification notes validated. Scheduled for batch EFT release."
            };

            context.GrantTranchePayments.AddRange(pay1, pay2);
            await context.SaveChangesAsync();
        }

        // 2. Seed Mandatory Grant 20% Rebate Payouts
        if (wsps.Count > 0)
        {
            var w1 = wsps[0];
            var disb1 = new MandatoryGrantDisbursement
            {
                WspSubmissionId = w1.Id,
                OrganisationId = w1.OrganisationId,
                DisbursementReference = $"MG-2026-{w1.FinYear}-{w1.Id:D4}",
                FinYear = w1.FinYear,
                LevyPeriod = "2026/04 - 2026/09",
                LeviesReceivedAmount = w1.PlannedTrainingBudget * 5.0m, // Gross levies
                CalculatedRebateAmount = w1.PlannedTrainingBudget * 0.20m, // 20% statutory rebate
                DisbursementStatusCode = "Approved",
                PaymentDate = new DateTime(2026, 10, 15),
                BatchNumber = "MG-BATCH-2026-Q2",
                BankAccountSnapshot = "Standard Bank - Acc ****5432 - Branch 051001",
                Comments = "Approved upon verified WSP/ATR submission compliance.",
                CreatedBy = "Finance Team"
            };
            context.MandatoryGrantDisbursements.Add(disb1);
            await context.SaveChangesAsync();
        }

        // 3. Seed Inter-SETA Transfers
        if (orgs.Count > 1)
        {
            var ist1 = new InterSetaTransfer
            {
                OrganisationId = orgs[1].Id,
                TransferType = "Incoming",
                OtherSetaCode = "CHIETA",
                OtherSetaName = "Chemical Industries Education and Training Authority",
                TransferReason = "Employer reclassified into Plastics Chamber SIC 33200 per SARS review.",
                EffectiveDate = new DateTime(2026, 4, 1),
                TransferStatusCode = "Approved by CEO",
                TransferAmount = 1450000.00m,
                SetaApprovalReference = "CHIETA/TRF/2026/089",
                DhetReferenceNumber = "DHET-SETMIS-TRF-2026-441",
                Comments = "Accumulated unspent discretionary levies transferred to MerSETA.",
                CreatedBy = "Admin"
            };

            var ist2 = new InterSetaTransfer
            {
                OrganisationId = orgs[0].Id,
                TransferType = "Outgoing",
                OtherSetaCode = "TETA",
                OtherSetaName = "Transport Education Training Authority",
                TransferReason = "Automotive logistics division split into standalone freight entity.",
                EffectiveDate = new DateTime(2026, 6, 1),
                TransferStatusCode = "Initiated",
                TransferAmount = 620000.00m,
                SetaApprovalReference = "TETA/PENDING/2026/112",
                Comments = "Awaiting DHET Ministerial signoff.",
                CreatedBy = "Admin"
            };

            context.InterSetaTransfers.AddRange(ist1, ist2);
            await context.SaveChangesAsync();
        }
    }
}
