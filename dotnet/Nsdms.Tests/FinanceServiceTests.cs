using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Xunit;

namespace Nsdms.Tests;

public class FinanceServiceTests
{
    [Fact]
    public async Task CreateGrantMoa_ShouldAutoGenerateFourMilestones_WithCorrectPercentagesAndAmounts()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var financeService = new FinanceService(factory);

        var grantApp = new GrantApplication
        {
            ApplicationNumber = "DG-2026-TEST",
            ProjectTitle = "CNC Milling Apprenticeship",
            RequestedAmount = 1000000m,
            ApprovedAmount = 1000000m,
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
            MoaNumber = "MOA-2026-CNC-001",
            ContractStartDate = new DateTime(2026, 4, 1),
            ContractEndDate = new DateTime(2027, 3, 31),
            TotalContractValue = 1000000m,
            MoaStatusCode = "Draft"
        };

        // Act
        var created = await financeService.CreateGrantMoaAsync(moa, "admin@merseta.org.za");

        // Assert
        Assert.NotNull(created);
        Assert.Equal(4, created.Milestones.Count);
        Assert.Equal(300000m, created.Milestones.First(m => m.MilestoneNumber == 1).TrancheAmount);
        Assert.Equal(300000m, created.Milestones.First(m => m.MilestoneNumber == 2).TrancheAmount);
        Assert.Equal(200000m, created.Milestones.First(m => m.MilestoneNumber == 3).TrancheAmount);
        Assert.Equal(200000m, created.Milestones.First(m => m.MilestoneNumber == 4).TrancheAmount);
        Assert.Equal(1000000m, created.Milestones.Sum(m => m.TrancheAmount));
    }

    [Fact]
    public async Task VerifyMilestone_ShouldUpdateStatusAndLogAudit()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var financeService = new FinanceService(factory);

        var moa = new GrantMoa
        {
            MoaNumber = "MOA-2026-VERIFY-001",
            ContractStartDate = new DateTime(2026, 4, 1),
            ContractEndDate = new DateTime(2027, 3, 31),
            TotalContractValue = 500000m
        };
        var created = await financeService.CreateGrantMoaAsync(moa, "admin@merseta.org.za");
        var milestone = created.Milestones.First();

        // Act
        var result = await financeService.VerifyMilestoneAsync(milestone.Id, "clo@merseta.org.za", "Contracts verified against attendance registers.");

        // Assert
        Assert.True(result);
        var updated = await financeService.GetMilestoneByIdAsync(milestone.Id);
        Assert.NotNull(updated);
        Assert.Equal("Verified", updated.MilestoneStatusCode);
        Assert.Equal("Contracts verified against attendance registers.", updated.VerificationComments);
    }

    [Fact]
    public async Task SubmitAndApproveTranchePayment_ShouldUpdateStatusAndBatch()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var financeService = new FinanceService(factory);

        var moa = new GrantMoa
        {
            MoaNumber = "MOA-2026-PAY-001",
            ContractStartDate = new DateTime(2026, 4, 1),
            ContractEndDate = new DateTime(2027, 3, 31),
            TotalContractValue = 500000m
        };
        var created = await financeService.CreateGrantMoaAsync(moa, "admin@merseta.org.za");
        var milestone = created.Milestones.First();

        var payment = new GrantTranchePayment
        {
            GrantMoaMilestoneId = milestone.Id,
            InvoiceNumber = "INV-2026-001",
            InvoiceDate = DateTime.Today,
            ClaimedAmount = milestone.TrancheAmount,
            ApprovedPaymentAmount = milestone.TrancheAmount
        };

        // Act 1: Submit
        var submitted = await financeService.SubmitTranchePaymentAsync(payment, "sdf@employer.co.za");
        Assert.Equal("Submitted", submitted.PaymentStatusCode);

        // Act 2: Approve
        var approved = await financeService.ApproveTranchePaymentAsync(submitted.Id, "cfo@merseta.org.za", "BATCH-2026-01", "Approved for EFT");
        Assert.True(approved);

        // Act 3: Payout
        var paid = await financeService.ProcessTranchePayoutAsync(submitted.Id, "finance@merseta.org.za", "MERSETA-EFT-991");
        Assert.True(paid);

        var updatedMilestone = await financeService.GetMilestoneByIdAsync(milestone.Id);
        Assert.Equal("Paid", updatedMilestone!.MilestoneStatusCode);
    }

    [Fact]
    public async Task CalculateMandatoryGrantRebates_ShouldCreate20PercentDisbursement()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var financeService = new FinanceService(factory);

        using (var ctx = factory.CreateDbContext())
        {
            var org = new Organisation { CompanyName = "Bell Equipment", SdlNumber = "L111222333" };
            ctx.Organisations.Add(org);
            await ctx.SaveChangesAsync();

            var wsp = new WspSubmission
            {
                OrganisationId = org.Id,
                FinYear = 2026,
                ReferenceNumber = "WSP-2026-BELL",
                PlannedTrainingBudget = 500000m,
                StatusCode = "Approved"
            };
            ctx.WspSubmissions.Add(wsp);
            await ctx.SaveChangesAsync();
        }

        // Act
        var count = await financeService.CalculateMandatoryGrantRebatesAsync(2026, "finance@merseta.org.za");

        // Assert
        Assert.Equal(1, count);
        var disbursements = await financeService.GetMandatoryDisbursementsAsync(2026);
        Assert.Single(disbursements);
        Assert.Equal(100000m, disbursements[0].CalculatedRebateAmount); // 20% of 500,000
    }

    [Fact]
    public async Task SaveAndApproveInterSetaTransfer_ShouldUpdateStatus()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var financeService = new FinanceService(factory);

        int orgId;
        using (var ctx = factory.CreateDbContext())
        {
            var org = new Organisation { CompanyName = "Sasol Synfuels", SdlNumber = "L800200300" };
            ctx.Organisations.Add(org);
            await ctx.SaveChangesAsync();
            orgId = org.Id;
        }

        var transfer = new InterSetaTransfer
        {
            OrganisationId = orgId,
            TransferType = "Incoming",
            OtherSetaCode = "CHIETA",
            OtherSetaName = "Chemical Industries SETA",
            TransferReason = "SIC Code migration",
            TransferAmount = 750000m,
            EffectiveDate = DateTime.Today,
            TransferStatusCode = "Initiated"
        };

        // Act 1: Save
        var saved = await financeService.SaveInterSetaTransferAsync(transfer, "admin@merseta.org.za");
        Assert.True(saved.Id > 0);

        // Act 2: Approve
        var approved = await financeService.ApproveInterSetaTransferAsync(saved.Id, "ceo@merseta.org.za", "DHET-SETMIS-TRF-001");
        Assert.True(approved);

        var updated = await financeService.GetInterSetaTransferByIdAsync(saved.Id);
        Assert.Equal("Approved by CEO", updated!.TransferStatusCode);
        Assert.Equal("DHET-SETMIS-TRF-001", updated.DhetReferenceNumber);
    }
}
