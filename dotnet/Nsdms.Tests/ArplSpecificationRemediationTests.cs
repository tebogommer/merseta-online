using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common.Utilities;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

/// <summary>
/// Verifies compliance with the signed 2023 ARPL and Artisan Trade Test specification:
/// "ARPL Registration Application Use Case 27012023.NMok.signed.pdf"
/// (Version 1.1 signed Jan 30/31, 2023 by Naphtaly Mokgotsane, Acting COO, and Thabo Mokwena, QA).
/// </summary>
public class ArplSpecificationRemediationTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit) CreateContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        return (factory, db, audit);
    }

    private static async Task<(CompanyLearner learner, Person person, Organisation org, TrainingProvider ttc)> SeedLearnerAndTtcAsync(NsdmsDbContext db)
    {
        var person = new Person
        {
            FirstName = "Siphamandla",
            LastName = "Nkosi",
            MaidenSurname = "Zulu",
            RsaIdNumber = "9207185123088",
            EmailAddress = "sipho.nkosi@example.com"
        };
        db.People.Add(person);

        var org = new Organisation
        {
            CompanyName = "Pretoria Metal and Engineering Works",
            LevyNumber = "L987654321",
            OrganisationTypeCode = "LevyPayingEmployer"
        };
        db.Organisations.Add(org);

        var ttc = new TrainingProvider
        {
            ProviderName = "Gauteng Central Accredited Trade Test Centre",
            AccreditationNumber = "TTC-GP-2026-001",
            ProviderTypeCode = "AccreditedTradeTestCentre",
            IsActive = true
        };
        db.TrainingProviders.Add(ttc);
        await db.SaveChangesAsync();

        var learner = new CompanyLearner
        {
            PersonId = person.Id,
            OrganisationId = org.Id,
            LearnerStatusCode = "Registered",
            RegistrationDate = DateTime.UtcNow
        };
        db.CompanyLearners.Add(learner);
        await db.SaveChangesAsync();

        return (learner, person, org, ttc);
    }

    [Fact]
    public void ArplTradeValidator_DesignatedTrades_Identifies17TradesAccurately()
    {
        // 17 statutory designated trades per Section 5.0 (Category 7)
        var designatedTrades = new[]
        {
            "Diesel Mechanic",
            "Motor Mechanic",
            "Boilermaker",
            "Welder",
            "Fitter",
            "Fitter and Turner",
            "Electrician",
            "Heavy Equipment Mechanic",
            "Instrument Mechanic",
            "Lift Mechanic",
            "Shipbuilder",
            "Panel Beater",
            "Vehicle Painter",
            "Bricklayer",
            "Plumber",
            "Carpenter",
            "Sheet fed-Lithograph"
        };

        foreach (var trade in designatedTrades)
        {
            Assert.True(ArplTradeValidator.RequiresToolkit(trade), $"Trade '{trade}' must be recognized as a designated toolkit trade.");
        }

        // Non-toolkit trades must evaluate to false
        Assert.False(ArplTradeValidator.RequiresToolkit("Hairdresser"));
        Assert.False(ArplTradeValidator.RequiresToolkit("Goldsmith"));
        Assert.False(ArplTradeValidator.RequiresToolkit("Toolmaker"));
    }

    [Theory]
    [InlineData(ArplQualifyingCategory.Category1_Min3Years_N2, 36, true, true)]
    [InlineData(ArplQualifyingCategory.Category1_Min3Years_N2, 24, true, false)]
    [InlineData(ArplQualifyingCategory.Category4_Min18Months_NcvLevel4, 18, true, true)]
    [InlineData(ArplQualifyingCategory.Category4_Min18Months_NcvLevel4, 12, true, false)]
    [InlineData(ArplQualifyingCategory.Category6_Min4Years_Grade9, 48, true, true)]
    [InlineData(ArplQualifyingCategory.Category6_Min4Years_Grade9, 36, true, false)]
    public void ArplTradeValidator_QualifyingCategories_EvaluatesComplianceAccurately(
        ArplQualifyingCategory category,
        int monthsExp,
        bool hasCert,
        bool expectedPass)
    {
        var (passed, message) = ArplTradeValidator.ValidateQualifyingCategory(
            category,
            "Diesel Mechanic",
            monthsExp,
            hasN2Certificate: hasCert,
            hasNcvLevel4: hasCert,
            hasGrade9OrStandard7: hasCert);

        Assert.Equal(expectedPass, passed);
    }

    [Fact]
    public void ArplTradeValidator_CreditRetention_Calculates50PercentRuleAnd18MonthExpiry()
    {
        var tasks = new List<TradeTestTask>
        {
            new() { TaskNumber = 1, TaskTitle = "Component Alignment", TotalMarksAvailable = 50, MarksObtained = 40, PassPercentage = 70 }, // Passed (80%)
            new() { TaskNumber = 2, TaskTitle = "Hydraulic Test", TotalMarksAvailable = 50, MarksObtained = 20, PassPercentage = 70 }       // Failed (40%)
        };

        // 1 out of 2 passed = 50% passed
        var (qualifies, passRate, expiryDate) = ArplTradeValidator.EvaluateTaskCreditRetention(tasks, DateTime.UtcNow);
        Assert.True(qualifies, "Candidate passing >= 50% of tasks must be eligible for credit retention.");
        Assert.Equal(50m, passRate);
        
        // Expiry date must be approximately 18 months in the future
        var monthsDiff = ((expiryDate.Year - DateTime.UtcNow.Year) * 12) + expiryDate.Month - DateTime.UtcNow.Month;
        Assert.True(monthsDiff >= 17 && monthsDiff <= 19, $"Expiry must be ~18 months, got {monthsDiff} months.");
    }

    [Fact]
    public async Task TradeTestService_AttemptLimit_BlocksExceedingThreeAttempts()
    {
        var (factory, db, audit) = CreateContext();
        var (learner, person, org, ttc) = await SeedLearnerAndTtcAsync(db);
        var service = new TradeTestAndArplService(factory, audit);

        // Attempt 1
        var app1 = await service.CreateTradeTestApplicationAsync(learner.Id, "Welder", "Section28", 1, preferredTrainingCenterId: ttc.Id);
        Assert.NotNull(app1);

        // Attempt 2
        var app2 = await service.CreateTradeTestApplicationAsync(learner.Id, "Welder", "Section28", 2, preferredTrainingCenterId: ttc.Id);
        Assert.NotNull(app2);

        // Attempt 3
        var app3 = await service.CreateTradeTestApplicationAsync(learner.Id, "Welder", "Section28", 3, preferredTrainingCenterId: ttc.Id);
        Assert.NotNull(app3);

        // Attempt 4 must throw InvalidOperationException
        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() =>
            service.CreateTradeTestApplicationAsync(learner.Id, "Welder", "Section28", 4, preferredTrainingCenterId: ttc.Id));

        Assert.Contains("3 trade test attempt", ex.Message, StringComparison.OrdinalIgnoreCase);
    }

    [Fact]
    public async Task TradeTestService_TwoTierWorkflow_ClaRecommendation_To_QaApproval_GeneratesSerialAndAudits()
    {
        var (factory, db, audit) = CreateContext();
        var (learner, person, org, ttc) = await SeedLearnerAndTtcAsync(db);
        var service = new TradeTestAndArplService(factory, audit);

        // 1. Create Application
        var app = await service.CreateTradeTestApplicationAsync(learner.Id, "Fitter and Turner", "Section28", 1, preferredTrainingCenterId: ttc.Id, currentUsername: "ApplicantUser");
        Assert.Equal("Submitted", app.StatusCode);
        Assert.True(app.RequiresToolkit);

        // 2. CLA Recommendation
        var recommended = await service.SubmitClaRecommendationAsync(
            app.Id,
            recommend: true,
            rejectionReason: null,
            comments: "Applicant credentials and service letters verified in Pretoria region.",
            currentUsername: "RegionalClaOfficer");

        Assert.Equal("RecommendedApplication", recommended.StatusCode);
        Assert.Equal("Recommended", recommended.ClaRecommendationStatus);
        Assert.NotNull(recommended.ClaRecommendationDate);

        // 3. QA Approval & Stamped Document Attachment
        var approved = await service.SubmitQaApprovalAsync(
            app.Id,
            approve: true,
            stampedDocumentAttachmentId: 105,
            isFinalRejection: false,
            rejectionReason: null,
            comments: "QA audit verified. Official serial granted.",
            currentUsername: "RegionalQaManager");

        Assert.Equal("Registered", approved.StatusCode);
        Assert.Equal("Approved", approved.QaApprovalStatus);
        Assert.NotNull(approved.QaApprovalDate);
        Assert.NotNull(approved.TradeTestSerialNumber);
        Assert.StartsWith("TT-SER-", approved.TradeTestSerialNumber);
        Assert.Equal(105, approved.QaSignedApplicationDocumentAttachmentId);

        // Verify Double-Write Audit Trail
        var audits = await db.AuditLogs
            .Where(a => a.EntityName == "LearnerTradeTestApplication" && a.RecordId == app.Id)
            .ToListAsync();

        Assert.Contains(audits, a => a.ActionName == "SubmitClaRecommendation");
        Assert.Contains(audits, a => a.ActionName == "SubmitQaApproval");
    }

    [Fact]
    public async Task TradeTestService_QaRejection_AllowsResubmissionOrFinalRejection()
    {
        var (factory, db, audit) = CreateContext();
        var (learner, person, org, ttc) = await SeedLearnerAndTtcAsync(db);
        var service = new TradeTestAndArplService(factory, audit);

        var app = await service.CreateTradeTestApplicationAsync(learner.Id, "Electrician", "Section28", 1, preferredTrainingCenterId: ttc.Id);
        await service.SubmitClaRecommendationAsync(app.Id, recommend: true, rejectionReason: null, comments: "All verified", currentUsername: "ClaUser");

        // Non-final rejection: returns for resubmission
        var resubmittable = await service.SubmitQaApprovalAsync(
            app.Id,
            approve: false,
            stampedDocumentAttachmentId: null,
            isFinalRejection: false,
            rejectionReason: "Employer letter lacks official stamp.",
            comments: "Please re-upload certified employer service letter.",
            currentUsername: "QaUser");

        Assert.Equal("RejectedForResubmission", resubmittable.StatusCode);
        Assert.False(resubmittable.IsFinalRejection);

        // Final rejection: permanently rejected
        var finalRejected = await service.SubmitQaApprovalAsync(
            app.Id,
            approve: false,
            stampedDocumentAttachmentId: null,
            isFinalRejection: true,
            rejectionReason: "Candidate identity verification failed.",
            comments: "Permanent disqualification due to fraudulent documents.",
            currentUsername: "QaUser");

        Assert.Equal("RejectedApplication", finalRejected.StatusCode);
        Assert.True(finalRejected.IsFinalRejection);
    }

    [Fact]
    public async Task TradeTestService_RecordTaskResults_PreservesHistoryAndApplies50PercentCreditRetention()
    {
        var (factory, db, audit) = CreateContext();
        var (learner, person, org, ttc) = await SeedLearnerAndTtcAsync(db);
        var service = new TradeTestAndArplService(factory, audit);

        var app = await service.CreateTradeTestApplicationAsync(learner.Id, "Diesel Mechanic", "Section28", 1, preferredTrainingCenterId: ttc.Id);
        await service.SubmitClaRecommendationAsync(app.Id, true, null, "Ok", "Cla");
        await service.SubmitQaApprovalAsync(app.Id, true, 10, false, null, "Ok", "Qa");

        // Attempt 1: 2 tasks - 1 passed (45/50), 1 failed (15/50)
        var attempt1Tasks = new List<TradeTestTask>
        {
            new() { TaskNumber = 1, TaskTitle = "Engine Teardown and Inspection", TotalMarksAvailable = 50, MarksObtained = 45, PassPercentage = 70 },
            new() { TaskNumber = 2, TaskTitle = "Fuel Injection Timing Calibration", TotalMarksAvailable = 50, MarksObtained = 15, PassPercentage = 70 }
        };

        var scored1 = await service.RecordTaskResultsAsync(app.Id, attempt1Tasks, "Candidate passed Module 1 only.", "AssessorDave");
        Assert.Equal("NotYetCompetent", scored1.CompetencyStatusCode);

        // Verify tasks in database
        var dbTasks1 = await db.TradeTestTasks.Where(t => t.LearnerTradeTestApplicationId == app.Id).ToListAsync();
        Assert.Equal(2, dbTasks1.Count);
        var passedTask = dbTasks1.First(t => t.TaskNumber == 1);
        Assert.True(passedTask.IsRetainedCredit, "Task 1 passed so it must be marked as retained credit.");
        Assert.NotNull(passedTask.CreditRetentionExpiryDate);

        // Attempt 2: Re-test on Module 2
        var attempt2Tasks = new List<TradeTestTask>
        {
            new() { TaskNumber = 2, TaskTitle = "Fuel Injection Timing Calibration (Re-test)", TotalMarksAvailable = 50, MarksObtained = 42, PassPercentage = 70 }
        };

        var scored2 = await service.RecordTaskResultsAsync(app.Id, attempt2Tasks, "Candidate competent on re-test.", "AssessorDave");
        
        // Both Attempt 1 tasks AND Attempt 2 tasks must be preserved (never deleted!)
        var allDbTasks = await db.TradeTestTasks.Where(t => t.LearnerTradeTestApplicationId == app.Id).ToListAsync();
        Assert.Equal(3, allDbTasks.Count);
    }

    [Fact]
    public async Task TradeTestService_Withdrawal_CreatesWithdrawalRecordAndAudit()
    {
        var (factory, db, audit) = CreateContext();
        var (learner, person, org, ttc) = await SeedLearnerAndTtcAsync(db);
        var service = new TradeTestAndArplService(factory, audit);

        var app = await service.CreateTradeTestApplicationAsync(learner.Id, "Boilermaker", "Section28", 1, preferredTrainingCenterId: ttc.Id);

        var withdrawn = await service.WithdrawTradeTestApplicationAsync(
            app.Id,
            "PersonalReasons",
            "Candidate relocated to another province and requested withdrawal.",
            "OfficerSmith");

        Assert.Equal("Withdrawn", withdrawn.StatusCode);
        Assert.True(withdrawn.IsWithdrawn);
        Assert.Equal("PersonalReasons", withdrawn.WithdrawalReasonCode);

        // Verify withdrawal child record in database
        var withdrawals = await db.LearnerTradeTestWithdrawals.Where(w => w.LearnerTradeTestApplicationId == app.Id).ToListAsync();
        Assert.Single(withdrawals);
        Assert.Equal("PersonalReasons", withdrawals[0].WithdrawalReasonCode);
        Assert.Equal("OfficerSmith", withdrawals[0].WithdrawnByUserId);

        // Verify Audit Log
        var audits = await db.AuditLogs.Where(a => a.EntityName == "LearnerTradeTestApplication" && a.RecordId == app.Id).ToListAsync();
        Assert.Contains(audits, a => a.ActionName == "WithdrawTradeTestApplication");
    }

    [Fact]
    public async Task TradeTestService_DocumentChecklist_SavesAndVerifiesChecklist()
    {
        var (factory, db, audit) = CreateContext();
        var (learner, person, org, ttc) = await SeedLearnerAndTtcAsync(db);
        var service = new TradeTestAndArplService(factory, audit);

        var app = await service.CreateTradeTestApplicationAsync(learner.Id, "Diesel Mechanic", "Section28", 1, preferredTrainingCenterId: ttc.Id);

        // 7 mandatory documents for toolkit trade
        var docs = new List<(string DocTypeCode, int? AttachmentId)>
        {
            ("StatementOfResults", 101),
            ("ArplApplicationForm", 102),
            ("CertifiedIdPassport", 103),
            ("ProofOfLegalStatus", 104),
            ("NambEvidencePackConfirmation", 105),
            ("EmployerServiceLetter", 106),
            ("PoeChecklist", 107)
        };

        var saved = await service.SaveDocumentChecklistAsync(app.Id, docs, "ClaOfficer");
        Assert.Equal(7, saved.Count);

        var dbDocs = await db.ArplDocumentChecklists.Where(d => d.LearnerTradeTestApplicationId == app.Id).ToListAsync();
        Assert.Equal(7, dbDocs.Count);
    }

    [Fact]
    public async Task TradeTestService_CertificateDistribution_LogsWaybillAndRecipient()
    {
        var (factory, db, audit) = CreateContext();
        var (learner, person, org, ttc) = await SeedLearnerAndTtcAsync(db);
        var service = new TradeTestAndArplService(factory, audit);

        var app = await service.CreateTradeTestApplicationAsync(learner.Id, "Welder", "Section28", 1, preferredTrainingCenterId: ttc.Id);

        var distEvent = await service.LogCertificateDistributionAsync(
            app.Id,
            "RegisteredCourier",
            "RAM-WAYBILL-2026-99182",
            "Siphamandla Nkosi",
            "9207185123088",
            "DispatchOfficerLinda");

        Assert.NotNull(distEvent);
        Assert.Equal("RAM-WAYBILL-2026-99182", distEvent.ConsignmentOrTrackingNumber);
        Assert.Equal("RegisteredCourier", distEvent.DistributionMethodCode);
        Assert.Equal("DispatchOfficerLinda", distEvent.DispatchedByUserId);

        var events = await db.CertificateDistributionEvents.Where(e => e.LearnerTradeTestApplicationId == app.Id).ToListAsync();
        Assert.Single(events);

        var reloadedApp = await db.LearnerTradeTestApplications.FindAsync(app.Id);
        Assert.NotNull(reloadedApp?.CertificateDistributedAt);
    }

    [Fact]
    public async Task OptionA_WorkplaceExperiencePortfolio_CalculatesDurationAndAssociatesUnregisteredEmployer()
    {
        var (factory, db, audit) = CreateContext();
        var (learner, _, _, ttc) = await SeedLearnerAndTtcAsync(db);
        var service = new TradeTestAndArplService(factory, audit);

        var app = await service.CreateTradeTestApplicationAsync(learner.Id, "Welder", "Section28", 1, preferredTrainingCenterId: ttc.Id);

        // Add 2 workplace experience records
        var exp1 = new ArplExperienceDetail
        {
            LearnerTradeTestApplicationId = app.Id,
            EmployerName = "Apex Fabricators (Pty) Ltd",
            CompanyRegistrationNumber = "2018/123456/07",
            ContactPersonName = "John Foreman",
            EmployerAddress = "12 Industrial Road, Germiston",
            StartDate = new DateTime(2020, 1, 1),
            EndDate = new DateTime(2022, 12, 31)
        };
        var exp2 = new ArplExperienceDetail
        {
            LearnerTradeTestApplicationId = app.Id,
            EmployerName = "Non-registered Workshop (Informal)",
            ContactPersonName = "Local Artisan Joe",
            EmployerAddress = "Stand 45, Soweto",
            StartDate = new DateTime(2023, 1, 1),
            EndDate = new DateTime(2024, 6, 30)
        };

        db.ArplExperienceDetails.AddRange(exp1, exp2);
        await db.SaveChangesAsync();

        var experiences = await db.ArplExperienceDetails.Where(e => e.LearnerTradeTestApplicationId == app.Id).ToListAsync();
        Assert.Equal(2, experiences.Count);

        // Verify total experience is approx 4.5 years (>= 3 years statutory requirement for Category 7)
        var totalDays = experiences.Sum(e => ((e.EndDate ?? DateTime.UtcNow) - e.StartDate).TotalDays);
        var totalYears = totalDays / 365.25;
        Assert.True(totalYears >= 3.0);
    }

    [Fact]
    public async Task OptionB_TwoTierRegionalWorkflow_ClaRecommend_QaApproveAndStampSerial_EnforcesStatutoryGates()
    {
        var (factory, db, audit) = CreateContext();
        var (learner, _, _, ttc) = await SeedLearnerAndTtcAsync(db);
        var service = new TradeTestAndArplService(factory, audit);

        var app = await service.CreateTradeTestApplicationAsync(learner.Id, "Welder", "Section28", 1, preferredTrainingCenterId: ttc.Id);

        // Tier 1: CLA Recommendation Gate
        var recommended = await service.SubmitClaRecommendationAsync(app.Id, recommend: true, currentUsername: "ClaReviewOfficer");
        Assert.Equal("RecommendedApplication", recommended.StatusCode);
        Assert.Equal("Recommended", recommended.ClaRecommendationStatus);

        // Tier 2: QA Approval & Stamping Gate
        var approved = await service.SubmitQaApprovalAsync(app.Id, approve: true, stampedDocumentAttachmentId: 8899, isFinalRejection: false, rejectionReason: null, comments: "Certificate and stamps verified", currentUsername: "RegionalQaOfficer");
        Assert.Equal("Registered", approved.StatusCode);
        Assert.NotNull(approved.TradeTestSerialNumber);
        Assert.StartsWith($"TT-SER-{DateTime.UtcNow.Year}-", approved.TradeTestSerialNumber);
        Assert.Equal(8899, approved.QaSignedApplicationDocumentAttachmentId);

        // QA Rejection Gate: Remediation (isFinalRejection = false)
        var app2 = await service.CreateTradeTestApplicationAsync(learner.Id, "Welder", "Section28", 1, preferredTrainingCenterId: ttc.Id);
        var remediableReject = await service.SubmitQaApprovalAsync(app2.Id, approve: false, stampedDocumentAttachmentId: null, isFinalRejection: false, rejectionReason: "Missing Employer Service Letter", comments: "Please upload service letter", currentUsername: "RegionalQaOfficer");
        Assert.Equal("RejectedForResubmission", remediableReject.StatusCode);
        Assert.False(remediableReject.IsFinalRejection);

        // QA Rejection Gate: Terminal (isFinalRejection = true)
        var app3 = await service.CreateTradeTestApplicationAsync(learner.Id, "Welder", "Section28", 1, preferredTrainingCenterId: ttc.Id);
        var terminalReject = await service.SubmitQaApprovalAsync(app3.Id, approve: false, stampedDocumentAttachmentId: null, isFinalRejection: true, rejectionReason: "Fraudulent Documentation", comments: "Forged certificate", currentUsername: "RegionalQaOfficer");
        Assert.Equal("RejectedApplication", terminalReject.StatusCode);
        Assert.True(terminalReject.IsFinalRejection);
    }
}
