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

        // Gatekeeping assertion: Unverified milestone approval must be blocked
        await Assert.ThrowsAsync<InvalidOperationException>(() => 
            financeService.ApproveTranchePaymentAsync(submitted.Id, "cfo@merseta.org.za", "BATCH-2026-01", "Should fail unverified"));

        // Act 2: Verify milestone first
        await financeService.VerifyMilestoneAsync(milestone.Id, "clo@merseta.org.za", "Deliverables confirmed.");

        // Act 3: Approve
        var approved = await financeService.ApproveTranchePaymentAsync(submitted.Id, "cfo@merseta.org.za", "BATCH-2026-01", "Approved for EFT");
        Assert.True(approved);

        // Act 4: Payout
        var paid = await financeService.ProcessTranchePayoutAsync(submitted.Id, "finance@merseta.org.za", "MERSETA-EFT-991");
        Assert.True(paid);

        var updatedMilestone = await financeService.GetMilestoneByIdAsync(milestone.Id);
        Assert.Equal("Paid", updatedMilestone!.MilestoneStatusCode);
    }

    [Fact]
    public async Task SubmitTranchePayment_BudgetEnvelopeHeadroom_BlocksOverclaiming()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var financeService = new FinanceService(factory);

        var moa = new GrantMoa
        {
            MoaNumber = "MOA-2026-CAP-001",
            ContractStartDate = new DateTime(2026, 4, 1),
            ContractEndDate = new DateTime(2027, 3, 31),
            TotalContractValue = 200000.00m // R200k budget cap
        };
        var created = await financeService.CreateGrantMoaAsync(moa, "admin@merseta.org.za");
        var milestone = created.Milestones.First();

        // Act 1: Submit claim within headroom (R 150,000)
        var payment1 = new GrantTranchePayment
        {
            GrantMoaMilestoneId = milestone.Id,
            InvoiceNumber = "INV-2026-CAP-1",
            InvoiceDate = DateTime.Today,
            ClaimedAmount = 150000.00m
        };
        var submitted1 = await financeService.SubmitTranchePaymentAsync(payment1, "sdf@employer.co.za");
        Assert.NotNull(submitted1);
        Assert.Equal(150000.00m, submitted1.ClaimedAmount);

        // Act 2: Submit claim exceeding remaining headroom (R 60,000 where remaining is 50,000)
        var payment2 = new GrantTranchePayment
        {
            GrantMoaMilestoneId = milestone.Id,
            InvoiceNumber = "INV-2026-CAP-2",
            InvoiceDate = DateTime.Today,
            ClaimedAmount = 60000.00m
        };

        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() =>
            financeService.SubmitTranchePaymentAsync(payment2, "sdf@employer.co.za"));
        Assert.Contains("exceeds remaining MoA budget envelope", ex.Message);
    }

    [Fact]
    public async Task ApproveTranchePayment_HighValueClaim_RequiresCfoDualSignoffAndGeneratesVoucher()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var financeService = new FinanceService(factory);

        var moa = new GrantMoa
        {
            MoaNumber = "MOA-2026-DOFA-001",
            ContractStartDate = new DateTime(2026, 4, 1),
            ContractEndDate = new DateTime(2027, 3, 31),
            TotalContractValue = 2000000.00m
        };
        var created = await financeService.CreateGrantMoaAsync(moa, "admin@merseta.org.za");
        var milestone = created.Milestones.First();

        // 1. CLO milestone verification
        var cloVerified = await financeService.CloVerifyMilestoneAsync(milestone.Id, "clo@merseta.org.za", "Site inspection completed");
        Assert.True(cloVerified);

        // 2. Submit high-value claim >= R500,000 (R 600,000)
        var payment = new GrantTranchePayment
        {
            GrantMoaMilestoneId = milestone.Id,
            InvoiceNumber = "INV-2026-HV-01",
            InvoiceDate = DateTime.Today,
            ClaimedAmount = 600000.00m
        };
        var submitted = await financeService.SubmitTranchePaymentAsync(payment, "sdf@employer.co.za");
        Assert.Equal("Submitted", submitted.PaymentStatusCode);

        // 3. Finance Manager approves -> should transition to PendingCfoApproval (DOFA threshold)
        var finApproved = await financeService.ApproveTranchePaymentAsync(submitted.Id, "finmanager@merseta.org.za", "BATCH-2026-DOFA");
        Assert.True(finApproved);

        using (var ctx = (Nsdms.Infrastructure.Data.NsdmsDbContext)factory.CreateDbContext())
        {
            var p = await ctx.GrantTranchePayments.FindAsync(submitted.Id);
            Assert.Equal("PendingCfoApproval", p!.PaymentStatusCode);
        }

        // Payout while PendingCfoApproval must be blocked
        await Assert.ThrowsAsync<InvalidOperationException>(() =>
            financeService.ProcessTranchePayoutAsync(submitted.Id, "disbursements@merseta.org.za", "EFT-FAIL"));

        // 4. CFO Executive Approval
        var cfoApproved = await financeService.CfoApproveTranchePaymentAsync(submitted.Id, "cfo@merseta.org.za", "CFO executive authorization confirmed");
        Assert.True(cfoApproved);

        using (var ctx = (Nsdms.Infrastructure.Data.NsdmsDbContext)factory.CreateDbContext())
        {
            var p = await ctx.GrantTranchePayments.FindAsync(submitted.Id);
            Assert.Equal("CfoApproved", p!.PaymentStatusCode);
            Assert.StartsWith($"PV-{DateTime.UtcNow.Year}-DG-", p.PaymentReferenceNumber);
        }

        // 5. Process Payout succeeds
        var paid = await financeService.ProcessTranchePayoutAsync(submitted.Id, "disbursements@merseta.org.za", "EFT-DOFA-SUCCESS");
        Assert.True(paid);

        using (var ctx = (Nsdms.Infrastructure.Data.NsdmsDbContext)factory.CreateDbContext())
        {
            var p = await ctx.GrantTranchePayments.FindAsync(submitted.Id);
            Assert.Equal("Paid", p!.PaymentStatusCode);
        }
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
    public async Task CalculateMandatoryGrantRebates_ShouldCalculateFromReconciledLevyFileLines_WhenAvailable()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var financeService = new FinanceService(factory);

        using (var ctx = factory.CreateDbContext())
        {
            var org = new Organisation { CompanyName = "Toyota SA", SdlNumber = "L999888777" };
            ctx.Organisations.Add(org);
            await ctx.SaveChangesAsync();

            var wsp = new WspSubmission
            {
                OrganisationId = org.Id,
                FinYear = 2026,
                ReferenceNumber = "WSP-2026-TOYOTA",
                PlannedTrainingBudget = 2000000m,
                StatusCode = "Approved"
            };
            ctx.WspSubmissions.Add(wsp);

            // Add actual SARS LevyFileLines for this employer
            var levyFile = new LevyFile
            {
                FileName = "SARS_2026_04.txt",
                FileRef = "SARS-2026-04-001",
                ImportStatusCode = "PROCESSED"
            };
            ctx.LevyFiles.Add(levyFile);
            await ctx.SaveChangesAsync();

            var line1 = new LevyFileLine
            {
                LevyFileId = levyFile.Id,
                SdlNumber = "L999888777",
                SchemeYear = "2026",
                TotalLevyAmount = 600000m,
                MandatoryLevyAmount = 120000m, // 20%
                DiscretionaryLevyAmount = 297000m,
                AdminLevyAmount = 63000m,
                QctoLevyAmount = 3000m
            };
            var line2 = new LevyFileLine
            {
                LevyFileId = levyFile.Id,
                SdlNumber = "L999888777",
                SchemeYear = "2026",
                TotalLevyAmount = 400000m,
                MandatoryLevyAmount = 80000m, // 20%
                DiscretionaryLevyAmount = 198000m,
                AdminLevyAmount = 42000m,
                QctoLevyAmount = 2000m
            };
            ctx.LevyFileLines.AddRange(line1, line2);
            await ctx.SaveChangesAsync();
        }

        // Act
        var count = await financeService.CalculateMandatoryGrantRebatesAsync(2026, "finance@merseta.org.za");

        // Assert
        Assert.Equal(1, count);
        var disbursements = await financeService.GetMandatoryDisbursementsAsync(2026);
        Assert.Single(disbursements);
        // Rebate should be 120,000 + 80,000 = 200,000 from SARS levies (not from planned budget 2M)
        Assert.Equal(200000m, disbursements[0].CalculatedRebateAmount);
        Assert.Equal(1000000m, disbursements[0].LeviesReceivedAmount);
        Assert.Contains("reconciled SARS levy line(s)", disbursements[0].Comments);
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

    #region 360-Degree Grant MoA Relational Tests

    [Fact]
    public async Task GetGrantMoaBeneficiariesAsync_ReturnsEnrolledLearnersUnderProject()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var financeService = new FinanceService(factory);

        int moaId;
        using (var ctx = factory.CreateDbContext())
        {
            var org = new Organisation { CompanyName = "Bell Equipment", SdlNumber = "L100200300" };
            ctx.Organisations.Add(org);
            await ctx.SaveChangesAsync();

            var app = new GrantApplication
            {
                OrganisationId = org.Id,
                ProjectTitle = "Heavy Equipment Apprenticeships",
                ApplicationNumber = "DG-2026-BELL",
                StatusCode = "Approved"
            };
            ctx.GrantApplications.Add(app);
            await ctx.SaveChangesAsync();

            var moa = new GrantMoa
            {
                GrantApplicationId = app.Id,
                MoaNumber = "MOA-2026-BELL-001",
                TotalContractValue = 2500000m,
                ContractStartDate = DateTime.Today,
                ContractEndDate = DateTime.Today.AddYears(3)
            };
            ctx.GrantMoas.Add(moa);
            await ctx.SaveChangesAsync();
            moaId = moa.Id;

            var person = new Person { FirstName = "Bongani", LastName = "Nkosi", RsaIdNumber = "0101015009087" };
            ctx.People.Add(person);
            await ctx.SaveChangesAsync();

            ctx.CompanyLearners.Add(new CompanyLearner
            {
                PersonId = person.Id,
                OrganisationId = org.Id,
                LearnerContractNumber = "LC-BELL-001",
                QualificationTitle = "Earthmoving Equipment Mechanic",
                LearningProgrammeTypeCode = "01",
                EnrolmentStatusCode = "Registered"
            });
            await ctx.SaveChangesAsync();
        }

        var beneficiaries = await financeService.GetGrantMoaBeneficiariesAsync(moaId);

        Assert.Single(beneficiaries);
        Assert.Equal("Bongani Nkosi", beneficiaries[0].FullName);
        Assert.Equal("Earthmoving Equipment Mechanic", beneficiaries[0].QualificationTitle);
        Assert.Equal("Apprenticeship", beneficiaries[0].ProgrammeTypeName);
    }

    [Fact]
    public async Task GetGrantMoaEmployersAsync_ReturnsParticipatingWorkplaces()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var financeService = new FinanceService(factory);

        int moaId;
        using (var ctx = factory.CreateDbContext())
        {
            var contact = new Person { FirstName = "Nalini", LastName = "Govender", Email = "nalini@toyota.co.za" };
            ctx.People.Add(contact);
            await ctx.SaveChangesAsync();

            var org = new Organisation
            {
                CompanyName = "Toyota SA Motors",
                SdlNumber = "L999888777",
                ChamberCode = "Automotive",
                PrimaryContactPersonId = contact.Id
            };
            ctx.Organisations.Add(org);
            await ctx.SaveChangesAsync();

            var app = new GrantApplication
            {
                OrganisationId = org.Id,
                ProjectTitle = "Auto Electrician Upskilling",
                ApplicationNumber = "DG-2026-TSAM",
                StatusCode = "Approved"
            };
            ctx.GrantApplications.Add(app);
            await ctx.SaveChangesAsync();

            var moa = new GrantMoa
            {
                GrantApplicationId = app.Id,
                MoaNumber = "MOA-2026-TSAM-001",
                TotalContractValue = 3000000m,
                ContractStartDate = DateTime.Today,
                ContractEndDate = DateTime.Today.AddYears(2)
            };
            ctx.GrantMoas.Add(moa);
            await ctx.SaveChangesAsync();
            moaId = moa.Id;
        }

        var employers = await financeService.GetGrantMoaEmployersAsync(moaId);

        Assert.Single(employers);
        Assert.Equal("Toyota SA Motors", employers[0].CompanyName);
        Assert.Equal("Nalini Govender", employers[0].ContactPersonName);
        Assert.Equal("nalini@toyota.co.za", employers[0].ContactEmail);
    }

    #endregion
}
