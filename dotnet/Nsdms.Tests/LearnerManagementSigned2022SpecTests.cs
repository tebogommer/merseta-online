using System.Text.Json;
using Microsoft.Extensions.Configuration;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

public class LearnerManagementSigned2022SpecTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, LearnerLifecycleService lifecycle, LearnerService learnerService, IPdfDocumentService pdf) CreateContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var inMemory = new Dictionary<string, string?>
        {
            { "Features:Integrations.DynamicsGp", "false" },
            { "WorkplaceApproval.EnforceMentorRatios", "true" }
        };
        var conf = new ConfigurationBuilder().AddInMemoryCollection(inMemory).Build();
        var configService = new SystemConfigurationService(factory, conf, audit);
        var flagService = new FeatureFlagService(factory, conf, audit);
        var mentorRatioEngine = new MentorRatioPolicyEngine(factory, audit, configService);
        var lifecycle = new LearnerLifecycleService(factory, audit, mentorRatioEngine);
        var learnerService = new LearnerService(factory, audit);
        var pdf = new QuestPdfDocumentService(factory, flagService, configService);

        return (factory, db, audit, lifecycle, learnerService, pdf);
    }

    [Fact]
    public async Task Spec4_1_RequestExtension_CapturesJustificationAndUpdatesInstateStatus()
    {
        var (factory, db, audit, lifecycle, learnerService, pdf) = CreateContext();

        var person = new Person { FirstName = "Sipho", LastName = "Dlamini", RsaIdNumber = "9201015009087" };
        var org = new Organisation { CompanyName = "Precision Manufacturing", SdlNumber = "L100000001" };
        db.People.Add(person);
        db.Organisations.Add(org);

        var learner = new CompanyLearner
        {
            Person = person,
            Organisation = org,
            QualificationTitle = "Toolmaker",
            LearnerContractNumber = "LRN-EXT-001",
            EnrolmentStatusCode = "Registered",
            InstateStatusCode = "Active",
            ExpectedCompletionDate = DateTime.UtcNow.AddMonths(12)
        };
        db.CompanyLearners.Add(learner);
        await db.SaveChangesAsync();

        var extensionReq = new LearnerExtensionRequest
        {
            CompanyLearnerId = learner.Id,
            ExtensionTypeCode = "ContractAddendum",
            ExtensionReasonCode = "MedicalLeave",
            RequestedExpiryDate = DateTime.UtcNow.AddMonths(18),
            JustificationComments = "Hospitalisation required 6 months bed rest",
            AddendumDocumentId = 101
        };

        var extension = await lifecycle.RequestExtensionAsync(extensionReq, "EmployerSDF");

        Assert.NotNull(extension);
        Assert.Equal("Pending", extension.ExtensionStatusCode);
        Assert.Equal("ContractAddendum", extension.ExtensionTypeCode);
        Assert.Equal("MedicalLeave", extension.ExtensionReasonCode);

        // Verify learner in-state status
        using var verifyDb = (NsdmsDbContext)factory.CreateDbContext();
        var refreshedLearner = await verifyDb.CompanyLearners.FindAsync(learner.Id);
        Assert.NotNull(refreshedLearner);
        Assert.Equal("Extension Requested", refreshedLearner.InstateStatusCode);

        // Approve Extension
        var approvedExpiry = DateTime.UtcNow.AddMonths(18);
        var approved = await lifecycle.ApproveExtensionAsync(extension.Id, approvedExpiry, "Extension approved with medical certificate", "MerSetaOfficer");
        Assert.Equal("Approved", approved.ExtensionStatusCode);

        using var verifyDb2 = (NsdmsDbContext)factory.CreateDbContext();
        var completedLearner = await verifyDb2.CompanyLearners.FindAsync(learner.Id);
        Assert.NotNull(completedLearner);
        Assert.Equal("Active", completedLearner.InstateStatusCode);
        Assert.Equal(approvedExpiry.Date, completedLearner.ExpectedCompletionDate?.Date);

        // Verify Addendum PDF generation
        var pdfBytes = await pdf.GenerateLearnerAddendumPdfAsync(extension.Id);
        Assert.NotNull(pdfBytes);
        Assert.True(pdfBytes.Length > 0);
    }

    [Fact]
    public async Task Spec4_2_WithdrawLearnerApplication_SetsInstateStatusToWithdrawal()
    {
        var (factory, db, audit, lifecycle, learnerService, pdf) = CreateContext();

        var person = new Person { FirstName = "Bongani", LastName = "Khoza", RsaIdNumber = "9301015009087" };
        db.People.Add(person);

        var learner = new CompanyLearner
        {
            Person = person,
            LearnerContractNumber = "LRN-WTH-001",
            EnrolmentStatusCode = "Application",
            InstateStatusCode = "Active"
        };
        db.CompanyLearners.Add(learner);
        await db.SaveChangesAsync();

        var withdrawn = await learnerService.WithdrawLearnerApplicationAsync(learner.Id, "CandidateRequest", "Candidate relocated abroad", "SDF_User");
        Assert.NotNull(withdrawn);

        using var verifyDb = (NsdmsDbContext)factory.CreateDbContext();
        var refreshed = await verifyDb.CompanyLearners.FindAsync(learner.Id);
        Assert.NotNull(refreshed);
        Assert.Equal("Withdrawal", refreshed.InstateStatusCode);
        Assert.Equal("Withdrawn", refreshed.EnrolmentStatusCode);
    }

    [Fact]
    public async Task Spec4_7_Transfer_WhenTargetEmployerNotWorkplaceApproved_ThrowsStatutoryException()
    {
        var (factory, db, audit, lifecycle, learnerService, pdf) = CreateContext();

        var person = new Person { FirstName = "Themba", LastName = "Sithole", RsaIdNumber = "9101015009087" };
        var fromOrg = new Organisation { CompanyName = "Releasing Employer Ltd", SdlNumber = "L111111111" };
        var toOrg = new Organisation { CompanyName = "Receiving Employer Ltd", SdlNumber = "L222222222" };
        db.People.Add(person);
        db.Organisations.AddRange(fromOrg, toOrg);
        await db.SaveChangesAsync();

        var learner = new CompanyLearner
        {
            Person = person,
            LearnerContractNumber = "LRN-TRF-001",
            OrganisationId = fromOrg.Id,
            EnrolmentStatusCode = "Registered",
            InstateStatusCode = "Active"
        };
        db.CompanyLearners.Add(learner);
        await db.SaveChangesAsync();

        var req = new LearnerTransferRequest
        {
            CompanyLearnerId = learner.Id,
            ToOrganisationId = toOrg.Id,
            TransferReasonCode = "TakenOnByHostEmployer"
        };

        // Must throw exact statutory message
        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() => lifecycle.RequestTransferAsync(req, "FutureSDF"));
        Assert.Equal("The selected option is not workplace approved please contact MerSETA", ex.Message);
    }

    [Fact]
    public async Task Spec4_7_Transfer_WhenWorkplaceApproved_ProceedsAndGeneratesLpmFm005Pdf()
    {
        var (factory, db, audit, lifecycle, learnerService, pdf) = CreateContext();

        var person = new Person { FirstName = "Dumisani", LastName = "Nkosi", RsaIdNumber = "9501015009087" };
        var fromOrg = new Organisation { CompanyName = "Old Auto Factory", SdlNumber = "L333333333" };
        var toOrg = new Organisation { CompanyName = "New Auto Assembly", SdlNumber = "L444444444" };
        db.People.Add(person);
        db.Organisations.AddRange(fromOrg, toOrg);

        var wpa = new WorkplaceApproval
        {
            Organisation = toOrg,
            ApprovalNumber = "WPA-2026-AUTO-01",
            ApprovalStatusCode = "Approved",
            QualificationTitle = "Automotive Mechanic",
            ApprovalDate = DateTime.UtcNow.AddMonths(-6),
            ExpiryDate = DateTime.UtcNow.AddYears(2)
        };
        db.WorkplaceApprovals.Add(wpa);

        var mentorPerson = new Person { FirstName = "Master", LastName = "Artisan", RsaIdNumber = "7501015009087" };
        db.People.Add(mentorPerson);

        var mentor = new WorkplaceApprovalMentor
        {
            WorkplaceApproval = wpa,
            Person = mentorPerson,
            MaxLearnerCapacity = 10,
            IsActive = true
        };
        db.WorkplaceApprovalMentors.Add(mentor);

        var learner = new CompanyLearner
        {
            Person = person,
            LearnerContractNumber = "LRN-TRF-002",
            Organisation = fromOrg,
            QualificationTitle = "Automotive Mechanic",
            EnrolmentStatusCode = "Registered",
            InstateStatusCode = "Active"
        };
        db.CompanyLearners.Add(learner);
        await db.SaveChangesAsync();

        var req = new LearnerTransferRequest
        {
            CompanyLearnerId = learner.Id,
            ToOrganisationId = toOrg.Id,
            TransferReasonCode = "ChangedEmployer",
            IsCurrentEmployerAgreed = false, // Releasing employer dissents
            IsFutureEmployerAgreed = true,
            IsLearnerAgreed = true
        };

        var transfer = await lifecycle.RequestTransferAsync(req, "FutureSDF");
        Assert.NotNull(transfer);
        Assert.Equal("Pending", transfer.TransferStatusCode);
        Assert.True(transfer.DisagreementPromptedTermination); // Releasing employer dissented

        // Verify Instate Status
        using var verifyDb = (NsdmsDbContext)factory.CreateDbContext();
        var refreshedLearner = await verifyDb.CompanyLearners.FindAsync(learner.Id);
        Assert.NotNull(refreshedLearner);
        Assert.Equal("Transfer Application", refreshedLearner.InstateStatusCode);

        // Approve Transfer
        var approved = await lifecycle.ApproveTransferAsync(transfer.Id, "Transfer approved post dispute conciliation", "MerSetaAdmin");
        Assert.Equal("Approved", approved.TransferStatusCode);

        using var verifyDb2 = (NsdmsDbContext)factory.CreateDbContext();
        var transferredLearner = await verifyDb2.CompanyLearners.FindAsync(learner.Id);
        Assert.NotNull(transferredLearner);
        Assert.Equal(toOrg.Id, transferredLearner.OrganisationId);
        Assert.Equal("Transferred", transferredLearner.InstateStatusCode);
        Assert.Equal("Registered", transferredLearner.EnrolmentStatusCode);

        // Verify LPM-FM-005 form generation
        var pdfBytes = await pdf.GenerateLpmFm005TransferFormPdfAsync(transfer.Id);
        Assert.NotNull(pdfBytes);
        Assert.True(pdfBytes.Length > 0);
    }

    [Fact]
    public async Task Spec4_5_OneSidedTermination_Calculates14DaySlaAndAdjudicatesThroughEtqaCommittee()
    {
        var (factory, db, audit, lifecycle, learnerService, pdf) = CreateContext();

        var person = new Person { FirstName = "Nomsa", LastName = "Zulu", RsaIdNumber = "9401015009087" };
        var employer = new Organisation { CompanyName = "Precision Tooling", SdlNumber = "L555555555" };
        db.People.Add(person);
        db.Organisations.Add(employer);

        var learner = new CompanyLearner
        {
            Person = person,
            LearnerContractNumber = "LRN-TERM-001",
            Organisation = employer,
            EnrolmentStatusCode = "Registered",
            InstateStatusCode = "Active"
        };
        db.CompanyLearners.Add(learner);
        await db.SaveChangesAsync();

        // 1. Request One-Sided Termination (AwolDismissal)
        var req = new LearnerTerminationRequest
        {
            CompanyLearnerId = learner.Id,
            TerminationTypeCode = "OneSided",
            TerminationReasonCode = "AwolDismissal",
            DisputeLogged = true,
            UnionRepresentativeName = "NUMSA Shop Steward",
            SettlementNotes = "Learner deserted post disciplinary inquiry"
        };

        var term = await lifecycle.RequestTerminationAsync(req, "HR_Officer");
        Assert.NotNull(term);
        Assert.Equal("OneSided", term.TerminationTypeCode);
        Assert.Equal("InInvestigation", term.TerminationStatusCode);
        Assert.NotNull(term.InvestigationDueDate);
        Assert.True(term.InvestigationDueDate.Value > DateTime.UtcNow); // 14 business days ahead

        // 2. Complete Checklist 036
        var checklistJson = JsonSerializer.Serialize(new
        {
            SignedAgreementVerified = true,
            ProofOfAbsenceAttached = true,
            DisciplinaryMinutesVerified = true,
            LearnerTraced = false
        });
        var investigated = await lifecycle.CompleteChecklist036Async(term.Id, checklistJson, "InvestigatingOfficer_John");
        Assert.True(investigated.Checklist036Completed);

        // 3. Record Investigation Outcome (ARPL recommended)
        var outcome = await lifecycle.RecordInvestigationOutcomeAsync(term.Id, "Candidate passed N2 theory; ARPL recommended to host employer", isArplRecommended: true, isTransferRecommended: false, currentUsername: "InvestigatingOfficer_John");
        Assert.True(outcome.IsArplRecommended);

        // 4. Submit to ETQA Review Committee
        var queued = await lifecycle.SubmitTerminationToCommitteeAsync(term.Id, 202609, "InvestigatingOfficer_John");
        Assert.Equal("CommitteeAgenda", queued.TerminationStatusCode);

        // 5. Adjudicate - Rejection / RequirementsNotMet
        var rejected = await lifecycle.AdjudicateCommitteeDecisionAsync(term.Id, "RequirementsNotMet", "Proof of trace attempts insufficient", "CommitteeChair");
        Assert.Equal("RequirementsNotMet", rejected.TerminationStatusCode);

        using var verifyDb = (NsdmsDbContext)factory.CreateDbContext();
        var learnerRefreshed = await verifyDb.CompanyLearners.FindAsync(learner.Id);
        Assert.NotNull(learnerRefreshed);
        Assert.Equal("Requirements Not Met", learnerRefreshed.InstateStatusCode);
        Assert.Equal("Registered", learnerRefreshed.EnrolmentStatusCode); // Agreement not cancelled!

        // Generate Checklist 036 PDF and Decision Letter PDF
        var checklistPdf = await pdf.GenerateChecklist036InvestigationPdfAsync(term.Id);
        Assert.NotNull(checklistPdf);
        Assert.True(checklistPdf.Length > 0);

        var decisionPdf = await pdf.GenerateTerminationDecisionLetterPdfAsync(term.Id);
        Assert.NotNull(decisionPdf);
        Assert.True(decisionPdf.Length > 0);
    }

    [Fact]
    public async Task Spec4_6_MutualTermination_ApprovesAndGeneratesLpmTp010Letter()
    {
        var (factory, db, audit, lifecycle, learnerService, pdf) = CreateContext();

        var person = new Person { FirstName = "Kagiso", LastName = "Mokoena", RsaIdNumber = "9601015009087" };
        var employer = new Organisation { CompanyName = "Steel Fabricators", SdlNumber = "L666666666" };
        db.People.Add(person);
        db.Organisations.Add(employer);

        var learner = new CompanyLearner
        {
            Person = person,
            LearnerContractNumber = "LRN-MUT-001",
            Organisation = employer,
            EnrolmentStatusCode = "Registered",
            InstateStatusCode = "Active"
        };
        db.CompanyLearners.Add(learner);
        await db.SaveChangesAsync();

        // 1. Request Mutual Termination
        var req = new LearnerTerminationRequest
        {
            CompanyLearnerId = learner.Id,
            TerminationTypeCode = "Mutual",
            TerminationReasonCode = "ResignationAgreement",
            SettlementNotes = "Learner offered permanent overseas scholarship by agreement"
        };

        var term = await lifecycle.RequestTerminationAsync(req, "SDF_User");
        Assert.NotNull(term);
        Assert.Equal("Mutual", term.TerminationTypeCode);
        Assert.Equal("Pending", term.TerminationStatusCode);

        // 2. Approve Mutual Termination
        var approved = await lifecycle.ApproveTerminationAsync(term.Id, "Mutual cancellation approved per LPM-TP-010 terms", "CLO_Officer");
        Assert.Equal("Approved", approved.TerminationStatusCode);

        using var verifyDb = (NsdmsDbContext)factory.CreateDbContext();
        var learnerRefreshed = await verifyDb.CompanyLearners.FindAsync(learner.Id);
        Assert.NotNull(learnerRefreshed);
        Assert.Equal("Terminated", learnerRefreshed.InstateStatusCode);
        Assert.Equal("Terminated", learnerRefreshed.EnrolmentStatusCode);

        // 3. Generate LPM-TP-010 Letter
        var lpmTp010Pdf = await pdf.GenerateLpmTp010MutualTerminationLetterPdfAsync(term.Id);
        Assert.NotNull(lpmTp010Pdf);
        Assert.True(lpmTp010Pdf.Length > 0);
    }
}
