using System.Security.Cryptography;
using System.Text;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

public class CoreStatutoryWorkflowTests
{
    private class TestDbContextFactory : INsdmsDbContextFactory
    {
        private readonly DbContextOptions<NsdmsDbContext> _options;

        public TestDbContextFactory(DbContextOptions<NsdmsDbContext> options)
        {
            _options = options;
        }

        public Task<INsdmsDbContext> CreateDbContextAsync(CancellationToken cancellationToken = default)
        {
            return Task.FromResult<INsdmsDbContext>(new NsdmsDbContext(_options, null));
        }

        public INsdmsDbContext CreateDbContext()
        {
            return new NsdmsDbContext(_options, null);
        }
    }

    private (TestDbContextFactory Factory, NsdmsDbContext Db, IAuditService Audit) CreateContext()
    {
        var dbName = "Test_CoreStatutoryWorkflows_" + Guid.NewGuid().ToString("N");
        var options = new DbContextOptionsBuilder<NsdmsDbContext>()
            .UseInMemoryDatabase(databaseName: dbName)
            .Options;

        var factory = new TestDbContextFactory(options);
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);

        return (factory, db, audit);
    }

    #region 1. WSP Multi-Party Quorum Signoff Tests (UI-04 & BE-05)

    [Fact]
    public async Task WspQuorumSignoff_SmallEmployer_QuorumMetWithSdfAndCeo()
    {
        var (factory, db, audit) = CreateContext();
        var signoffService = new WspSignoffService(factory, audit);

        var org = new Organisation { CompanyName = "Precision Engineering (Small)", SdlNumber = "L100000001" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var wsp = new WspSubmission
        {
            OrganisationId = org.Id,
            FinYear = 2026,
            ReferenceNumber = "WSP-2026-PE-001",
            EmployeeCount = 35, // Small employer (< 50)
            WspApprovalStatusCode = "Draft"
        };
        db.WspSubmissions.Add(wsp);
        await db.SaveChangesAsync();

        // Check initial quorum status
        var initialStatus = await signoffService.GetQuorumStatusAsync(wsp.Id);
        Assert.True(initialStatus.IsSmallEmployer);
        Assert.Equal(2, initialStatus.RequiredSignaturesCount);
        Assert.False(initialStatus.IsQuorumMet);

        // SDF signs off
        var sdfOtp = await signoffService.RequestSignoffOtpAsync(wsp.Id, "PrimarySdf", "sdf@precision.co.za", "SDF_User");
        Assert.NotNull(sdfOtp);

        await signoffService.RecordSignoffAttestationAsync(new WspSignoffRequest
        {
            WspSubmissionId = wsp.Id,
            SignerRoleCode = "PrimarySdf",
            SignerFullName = "Sarah Connor",
            SignerEmail = "sdf@precision.co.za",
            SignerRsaId = "8501015000085",
            OtpToken = sdfOtp
        }, "SDF_User");

        var midStatus = await signoffService.GetQuorumStatusAsync(wsp.Id);
        Assert.False(midStatus.IsQuorumMet);
        Assert.Equal(1, midStatus.CompletedSignaturesCount);

        // CEO signs off
        var ceoOtp = await signoffService.RequestSignoffOtpAsync(wsp.Id, "CeoManagingDirector", "ceo@precision.co.za", "CEO_User");
        await signoffService.RecordSignoffAttestationAsync(new WspSignoffRequest
        {
            WspSubmissionId = wsp.Id,
            SignerRoleCode = "CeoManagingDirector",
            SignerFullName = "John Connor",
            SignerEmail = "ceo@precision.co.za",
            SignerRsaId = "7501015000080",
            OtpToken = ceoOtp
        }, "CEO_User");

        var finalStatus = await signoffService.GetQuorumStatusAsync(wsp.Id);
        Assert.True(finalStatus.IsQuorumMet);
        Assert.Equal(2, finalStatus.CompletedSignaturesCount);
        Assert.NotNull(finalStatus.SignoffDigitalSecuritySeal);

        using var verifyDb = (NsdmsDbContext)factory.CreateDbContext();
        var refreshedWsp = await verifyDb.WspSubmissions.FindAsync(wsp.Id);
        Assert.Equal("SignedOff", refreshedWsp!.WspApprovalStatusCode);
        Assert.True(refreshedWsp.IsSignoffQuorumMet);
    }

    [Fact]
    public async Task WspQuorumSignoff_MediumLargeEmployer_RequiresTripartiteQuorum()
    {
        var (factory, db, audit) = CreateContext();
        var signoffService = new WspSignoffService(factory, audit);

        var org = new Organisation { CompanyName = "Apex Heavy Forge (Large)", SdlNumber = "L200000002" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var wsp = new WspSubmission
        {
            OrganisationId = org.Id,
            FinYear = 2026,
            ReferenceNumber = "WSP-2026-AHF-001",
            EmployeeCount = 150, // Large employer (>= 50)
            WspApprovalStatusCode = "Draft"
        };
        db.WspSubmissions.Add(wsp);
        await db.SaveChangesAsync();

        // Initial check: requires 3 signatures
        var status = await signoffService.GetQuorumStatusAsync(wsp.Id);
        Assert.False(status.IsSmallEmployer);
        Assert.Equal(3, status.RequiredSignaturesCount);

        // 1. SDF signs
        await signoffService.RecordSignoffAttestationAsync(new WspSignoffRequest
        {
            WspSubmissionId = wsp.Id,
            SignerRoleCode = "PrimarySdf",
            SignerFullName = "Alice Smith",
            SignerEmail = "alice@apex.co.za",
            SignerRsaId = "8801015000088"
        });

        // 2. CEO signs
        await signoffService.RecordSignoffAttestationAsync(new WspSignoffRequest
        {
            WspSubmissionId = wsp.Id,
            SignerRoleCode = "CeoManagingDirector",
            SignerFullName = "Bob Vance",
            SignerEmail = "bob@apex.co.za",
            SignerRsaId = "6801015000088"
        });

        // Quorum must STILL BE FALSE because Labour Union hasn't signed yet!
        var afterCeoStatus = await signoffService.GetQuorumStatusAsync(wsp.Id);
        Assert.False(afterCeoStatus.IsQuorumMet);
        Assert.Equal(2, afterCeoStatus.CompletedSignaturesCount);

        // 3. Labour Union signs
        await signoffService.RecordSignoffAttestationAsync(new WspSignoffRequest
        {
            WspSubmissionId = wsp.Id,
            SignerRoleCode = "LabourUnionRep",
            SignerFullName = "David Mthembu",
            SignerEmail = "david@numsa.org.za",
            SignerRsaId = "8001015000088",
            UnionName = "NUMSA"
        });

        var finalStatus = await signoffService.GetQuorumStatusAsync(wsp.Id);
        Assert.True(finalStatus.IsQuorumMet);
        Assert.Equal(3, finalStatus.CompletedSignaturesCount);
    }

    [Fact]
    public async Task WspQuorumSignoff_LabourDispute_TransitionsToDisputedAndBlocksAttestation()
    {
        var (factory, db, audit) = CreateContext();
        var signoffService = new WspSignoffService(factory, audit);

        var org = new Organisation { CompanyName = "SteelCorp Manufacturing", SdlNumber = "L300000003" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var wsp = new WspSubmission
        {
            OrganisationId = org.Id,
            FinYear = 2026,
            ReferenceNumber = "WSP-2026-STEEL-001",
            EmployeeCount = 200,
            WspApprovalStatusCode = "Draft"
        };
        db.WspSubmissions.Add(wsp);
        await db.SaveChangesAsync();

        // SDF signs first
        await signoffService.RecordSignoffAttestationAsync(new WspSignoffRequest
        {
            WspSubmissionId = wsp.Id,
            SignerRoleCode = "PrimarySdf",
            SignerFullName = "Jane Doe",
            SignerEmail = "jane@steel.co.za"
        });

        // Labour rep lodges dispute
        var dispute = await signoffService.LodgeSignoffDisputeAsync(new WspDisputeLodgementRequest
        {
            WspSubmissionId = wsp.Id,
            DisputeReasonCode = "ConsultationFailure",
            Description = "Union was not consulted on skills gap analysis or mandatory grant allocations.",
            UnionRepresentativeName = "Peter Khumalo",
            Constituency = "Solidarity"
        }, "UnionRep_User");

        Assert.NotNull(dispute);
        Assert.Equal("Logged", dispute.DisputeStatusCode);

        // Check WSP status
        using var verifyDb = (NsdmsDbContext)factory.CreateDbContext();
        var refreshedWsp = await verifyDb.WspSubmissions.FindAsync(wsp.Id);
        Assert.True(refreshedWsp!.DisputeLogged);
        Assert.Equal("Disputed", refreshedWsp.WspApprovalStatusCode);

        // Attempting another sign-off while disputed must throw InvalidOperationException
        await Assert.ThrowsAsync<InvalidOperationException>(async () =>
        {
            await signoffService.RecordSignoffAttestationAsync(new WspSignoffRequest
            {
                WspSubmissionId = wsp.Id,
                SignerRoleCode = "CeoManagingDirector",
                SignerFullName = "CEO Person",
                SignerEmail = "ceo@steel.co.za"
            });
        });
    }

    #endregion

    #region 2. Learner Lifecycle Amendments & OTP Signoff Tests (UI-07 & DB-04)

    [Fact]
    public async Task LearnerLifecycle_ChangeRequest_SubmitsAndReviewsSuccessfully()
    {
        var (factory, db, audit) = CreateContext();
        var lifecycle = new LearnerLifecycleService(factory, audit);

        var org = new Organisation { CompanyName = "Bell Equipment", SdlNumber = "L600000006" };
        db.Organisations.Add(org);

        var person = new Person { FirstName = "Bongani", LastName = "Nkosi", RsaIdNumber = "9501015000088" };
        db.Set<Person>().Add(person);
        await db.SaveChangesAsync();

        var learner = new CompanyLearner
        {
            PersonId = person.Id,
            OrganisationId = org.Id,
            LearnerContractNumber = "LRN-2026-0099",
            QualificationTitle = "Boilermaker",
            OfoCode = "651401",
            StatusCode = "Registered"
        };
        db.CompanyLearners.Add(learner);
        await db.SaveChangesAsync();

        // Submit change request
        var changeReq = await lifecycle.SubmitChangeRequestAsync(new LearnerChangeRequestInput
        {
            CompanyLearnerId = learner.Id,
            ChangeTypeCode = "QualificationAmendment",
            ProposedChangesJson = "{\"QualificationTitle\":\"Advanced Boilermaker\",\"OfoCode\":\"651402\"}",
            JustificationReason = "Learner upgraded to Level 4 specialized curriculum"
        }, "Employer_Admin");

        Assert.Equal("Pending", changeReq.ChangeStatusCode);
        Assert.Contains("Boilermaker", changeReq.CurrentValuesSnapshotJson);

        // Review and approve
        var reviewed = await lifecycle.ReviewChangeRequestAsync(changeReq.Id, true, "Approved by merSETA ETQA committee", "EtqaOfficer");
        Assert.Equal("Approved", reviewed.ChangeStatusCode);
        Assert.NotNull(reviewed.ApprovalDate);
    }

    [Fact]
    public async Task LearnerLifecycle_OtpSignoff_ExecutesTripartiteAgreementWithDigitalSeal()
    {
        var (factory, db, audit) = CreateContext();
        var lifecycle = new LearnerLifecycleService(factory, audit);

        var org = new Organisation { CompanyName = "Transnet Rail", SdlNumber = "L700000007" };
        db.Organisations.Add(org);

        var person = new Person
        {
            FirstName = "Sipho",
            LastName = "Zulu",
            RsaIdNumber = "9901015000088"
        };
        db.Set<Person>().Add(person);
        await db.SaveChangesAsync();

        var learner = new CompanyLearner
        {
            PersonId = person.Id,
            OrganisationId = org.Id,
            LearnerContractNumber = "LRN-2026-OTP-777",
            QualificationTitle = "Fitter",
            EnrolmentStatusCode = "PendingSignoff"
        };
        db.CompanyLearners.Add(learner);
        await db.SaveChangesAsync();

        // Verify with invalid contract number returns false
        var failResult = await lifecycle.VerifyAndExecuteSignoffOtpAsync(new LearnerOtpVerificationRequest
        {
            ContractReferenceOrRsaId = "INVALID-NUMBER",
            OtpToken = "123456",
            SignerName = "Sipho Zulu"
        });
        Assert.False(failResult.Success);

        // Verify with valid RSA ID returns true and generates SHA-256 seal
        var successResult = await lifecycle.VerifyAndExecuteSignoffOtpAsync(new LearnerOtpVerificationRequest
        {
            ContractReferenceOrRsaId = "9901015000088",
            OtpToken = "889900",
            SignerName = "Sipho Zulu",
            SignerRsaId = "9901015000088",
            SignerRole = "Learner"
        }, "Sipho_User");

        Assert.True(successResult.Success);
        Assert.NotNull(successResult.SecuritySeal);
        Assert.Equal(64, successResult.SecuritySeal.Length); // 64 hex characters (SHA-256)

        using var verifyDb = (NsdmsDbContext)factory.CreateDbContext();
        var refreshedLearner = await verifyDb.CompanyLearners.FindAsync(learner.Id);
        Assert.Equal("Registered", refreshedLearner!.EnrolmentStatusCode);
        Assert.NotEqual(default(DateTime), refreshedLearner.RegistrationDate);
    }

    #endregion

    #region 3. Discretionary Grant Claim Aggregate & ERP Batch Staging Tests (BE-03)

    [Fact]
    public async Task DgClaim_BudgetEnvelopeCap_BlocksOverclaiming()
    {
        var (factory, db, audit) = CreateContext();
        var claimService = new DiscretionaryGrantClaimService(factory, audit);

        var org = new Organisation { CompanyName = "Toyota SA Motors", SdlNumber = "L400000004" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var app = new GrantApplication
        {
            OrganisationId = org.Id,
            FundingWindowId = 1,
            RequestedAmount = 1000000m
        };
        db.GrantApplications.Add(app);
        await db.SaveChangesAsync();

        var moa = new GrantMoa
        {
            GrantApplicationId = app.Id,
            MoaNumber = "MOA-2026-TOYOTA-01",
            TotalContractValue = 500000.00m // R500,000 budget cap
        };
        db.GrantMoas.Add(moa);
        await db.SaveChangesAsync();

        var pip = new ProjectImplementationPlan
        {
            OrganisationId = org.Id,
            GrantApplicationId = app.Id,
            GrantMoaId = moa.Id,
            TotalAwardedAmount = 500000.00m
        };
        db.ProjectImplementationPlans.Add(pip);
        await db.SaveChangesAsync();

        // 1. Submit Tranche 1 (R 200,000) - Should succeed
        var claim1 = await claimService.SubmitTrancheClaimAsync(new SubmitDgClaimRequest
        {
            ProjectImplementationPlanId = pip.Id,
            TrancheNumber = 1,
            ClaimAmount = 200000.00m,
            DeliverableDescription = "Tranche 1 Advance: Signed bilateral MoA and 100% learner contracts."
        });
        Assert.Equal(200000.00m, claim1.ClaimAmount);
        Assert.False(claim1.RequiresCfoApproval); // Below R500k

        // 2. Submit Tranche 2 (R 350,000) - Should FAIL because 200k + 350k = 550k > 500k
        await Assert.ThrowsAsync<InvalidOperationException>(async () =>
        {
            await claimService.SubmitTrancheClaimAsync(new SubmitDgClaimRequest
            {
                ProjectImplementationPlanId = pip.Id,
                TrancheNumber = 2,
                ClaimAmount = 350000.00m,
                DeliverableDescription = "Tranche 2 Midterm"
            });
        });

        // 3. Submit valid Tranche 2 (R 300,000) - Should succeed (total 500k)
        var claim2 = await claimService.SubmitTrancheClaimAsync(new SubmitDgClaimRequest
        {
            ProjectImplementationPlanId = pip.Id,
            TrancheNumber = 2,
            ClaimAmount = 300000.00m,
            DeliverableDescription = "Tranche 2 Midterm: Logbooks verified."
        });
        Assert.Equal(300000.00m, claim2.ClaimAmount);

        // Check Financial Aggregate
        var aggregate = await claimService.GetFinancialAggregateForMoaAsync(moa.Id);
        Assert.Equal(500000.00m, aggregate.TotalMoaAllocation);
        Assert.Equal(500000.00m, aggregate.TotalClaimedAmount);
        Assert.Equal(0.00m, aggregate.RemainingEnvelopeBalance);
        Assert.True(aggregate.IsFullyClaimed);
        Assert.Equal(2, aggregate.Claims.Count);
    }

    [Fact]
    public async Task DgClaim_MultiTierApprovalAndErpBatchStaging_ProducesVoucherAndTsv()
    {
        var (factory, db, audit) = CreateContext();
        var claimService = new DiscretionaryGrantClaimService(factory, audit);

        var org = new Organisation { CompanyName = "Defy Appliances", SdlNumber = "L500000005" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var app = new GrantApplication { OrganisationId = org.Id, RequestedAmount = 1500000m };
        db.GrantApplications.Add(app);
        await db.SaveChangesAsync();

        var moa = new GrantMoa { GrantApplicationId = app.Id, MoaNumber = "MOA-2026-DEFY-01", TotalContractValue = 1200000m };
        db.GrantMoas.Add(moa);
        await db.SaveChangesAsync();

        var pip = new ProjectImplementationPlan
        {
            OrganisationId = org.Id,
            GrantApplicationId = app.Id,
            GrantMoaId = moa.Id,
            TotalAwardedAmount = 1200000m
        };
        db.ProjectImplementationPlans.Add(pip);
        await db.SaveChangesAsync();

        // Submit high-value claim: R 600,000 (Requires CFO approval)
        var claim = await claimService.SubmitTrancheClaimAsync(new SubmitDgClaimRequest
        {
            ProjectImplementationPlanId = pip.Id,
            TrancheNumber = 1,
            ClaimAmount = 600000.00m,
            DeliverableDescription = "Tranche 1 Advance"
        });
        Assert.True(claim.RequiresCfoApproval);

        // Tier 1: CLO Verification
        var cloVerified = await claimService.ProcessClaimApprovalAsync(new ApproveDgClaimRequest
        {
            ClaimId = claim.Id,
            ApprovalRole = "CLO",
            ApproverName = "Johnathan CLO",
            Comments = "Deliverables inspected on site"
        });
        Assert.Equal("CloVerified", cloVerified.StatusCode);

        // Tier 2: Finance Officer Review -> moves to PendingCfoApproval because > R500k
        var finReviewed = await claimService.ProcessClaimApprovalAsync(new ApproveDgClaimRequest
        {
            ClaimId = claim.Id,
            ApprovalRole = "FinanceOfficer",
            ApproverName = "Felicia Finance",
            Comments = "Tax clearance and banking details verified"
        });
        Assert.Equal("PendingCfoApproval", finReviewed.StatusCode);

        // Tier 3: CFO Final Executive Signoff -> issues serialized Payment Voucher
        var cfoApproved = await claimService.ProcessClaimApprovalAsync(new ApproveDgClaimRequest
        {
            ClaimId = claim.Id,
            ApprovalRole = "CFO",
            ApproverName = "Chief Financial Officer",
            Comments = "Approved per executive authorization"
        });
        Assert.Equal("CfoApproved", cfoApproved.StatusCode);
        Assert.NotNull(cfoApproved.PaymentVoucherNumber);
        Assert.StartsWith($"PV-{DateTime.UtcNow.Year}-DG-", cfoApproved.PaymentVoucherNumber);

        // Stage into ERP Payment Batch
        var batch = await claimService.StageApprovedClaimsToErpBatchAsync(new List<int> { claim.Id }, "FinanceAdmin");
        Assert.NotNull(batch.BatchNumber);
        Assert.Equal(1, batch.ItemCount);
        Assert.Equal(600000.00m, batch.TotalAmount);

        // Export TSV for Sage / GP
        var export = await claimService.ExportBatchForErpAsync(batch.Id, "FinanceAdmin");
        Assert.Equal("ExportedToErp", export.BatchHeader.BatchStatusCode);
        Assert.Contains("VoucherNumber\tVendorNumber\tOrganisationName", export.FormattedTsv);
        Assert.Contains("Defy Appliances", export.FormattedTsv);
        Assert.Contains("600000.00", export.FormattedTsv);

        // Claim status is now Paid
        using var verifyDb = (NsdmsDbContext)factory.CreateDbContext();
        var refreshedClaim = await verifyDb.GrantPaymentClaims.FindAsync(claim.Id);
        Assert.Equal("Paid", refreshedClaim!.StatusCode);
    }

    #endregion
}
