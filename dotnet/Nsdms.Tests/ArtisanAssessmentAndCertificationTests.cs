using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class ArtisanAssessmentAndCertificationTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit) CreateContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        return (factory, db, audit);
    }

    private static async Task<(CompanyLearner learner, Person person, Organisation org)> SeedLearnerAsync(NsdmsDbContext db)
    {
        var person = new Person
        {
            FirstName = "Kagiso",
            LastName = "Modise",
            RsaIdNumber = "9604125829081",
            EmailAddress = "kagiso.modise@example.com"
        };
        db.People.Add(person);

        var org = new Organisation
        {
            CompanyName = "Precision Diesel Engineering Ltd",
            LevyNumber = "L123456789",
            OrganisationTypeCode = "LevyPayingEmployer"
        };
        db.Organisations.Add(org);
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

        return (learner, person, org);
    }

    [Fact]
    public async Task TradeTest_CompleteLifecycle_FromApplicationToCertificateIssuance()
    {
        var (factory, db, audit) = CreateContext();
        var (learner, person, org) = await SeedLearnerAsync(db);
        var tradeService = new TradeTestAndArplService(factory, audit);

        // 1. Create Application
        var app = await tradeService.CreateTradeTestApplicationAsync(
            learner.Id,
            "Diesel Mechanic (Artisan)",
            "Section26D",
            1,
            "653306",
            null,
            currentUsername: "EmployerCoordinator");

        Assert.NotNull(app);
        Assert.StartsWith("TT-", app.ApplicationNumber);
        Assert.Equal("Submitted", app.StatusCode);
        Assert.Equal(person.Id, app.PersonId);

        // 2. Allocate Assessment Centre & Schedule
        var scheduled = await tradeService.AllocateTradeTestCenterAndScheduleAsync(
            app.Id,
            1,
            "Ekurhuleni Artisan Assessment Centre",
            DateTime.UtcNow.AddDays(14),
            new TimeSpan(8, 30, 0),
            "Sipho Sithole",
            "ASSR-2026-091",
            "CentreManager");

        Assert.Equal("TradeCenterAllocated", scheduled.StatusCode);
        Assert.Equal("Ekurhuleni Artisan Assessment Centre", scheduled.AssessmentCenterName);

        // 3. Submit to NAMB and Record Approval Decision
        await tradeService.SubmitToNambForSerialAsync(app.Id, "CentreManager");
        var nambDecision = await tradeService.RecordNambDecisionAsync(
            app.Id,
            "Approved",
            "Precious Khumalo (NAMB Provincial Officer)",
            "NAMB-2026-DM-88219",
            "Candidate verified with NAMB database.",
            "AdminOfficer");

        Assert.Equal("Assessing", nambDecision.StatusCode);
        Assert.Equal("NAMB-2026-DM-88219", nambDecision.NambSerialNumber);

        // 4. Record Practical Task Results
        var tasks = new List<TradeTestTask>
        {
            new() { TaskNumber = 1, TaskTitle = "Hydraulic Circuit Diagnosis", TotalMarksAvailable = 50, MarksObtained = 44, PassPercentage = 70 },
            new() { TaskNumber = 2, TaskTitle = "Diesel Injector Timing & Bench Calibration", TotalMarksAvailable = 50, MarksObtained = 42, PassPercentage = 70 }
        };

        var scored = await tradeService.RecordTaskResultsAsync(app.Id, tasks, "Candidate passed all modules with distinction.", "Sipho Sithole");
        Assert.Equal("Competent", scored.CompetencyStatusCode);
        Assert.Equal("Competent", scored.StatusCode);

        // 5. Finalize Trade Test and Issue Certificate
        var certified = await tradeService.FinalizeTradeTestAndIssueCertificateAsync(
            app.Id,
            "Nomvula Dlamini",
            "MOD-2026-0044",
            "QualityAssuranceManager");

        Assert.Equal("Certified", certified.StatusCode);
        Assert.NotNull(certified.SerialCertificateNumber);
        Assert.StartsWith("CERT-", certified.SerialCertificateNumber);

        // Verify CompanyLearner updated to Completed
        var updatedLearner = await db.CompanyLearners.AsNoTracking().FirstOrDefaultAsync(l => l.Id == learner.Id);
        Assert.NotNull(updatedLearner);
        Assert.Equal("Completed", updatedLearner.LearnerStatusCode);

        // Verify Double-Write Audit Trail
        var auditLogs = await db.AuditLogs.Where(a => a.EntityName == "LearnerTradeTestApplication" && a.RecordId == app.Id).ToListAsync();
        Assert.NotEmpty(auditLogs);
        Assert.Contains(auditLogs, a => a.ActionName == "IssueArtisanTradeCertificate");
    }

    [Fact]
    public async Task Arpl_PortfolioSubmission_CalculatesRecommendationAndTracksExperience()
    {
        var (factory, db, audit) = CreateContext();
        var (learner, person, org) = await SeedLearnerAsync(db);
        var tradeService = new TradeTestAndArplService(factory, audit);

        // Create Section 28 (ARPL) Application
        var app = await tradeService.CreateTradeTestApplicationAsync(
            learner.Id,
            "Boilermaker (Section 28 ARPL)",
            "Section28",
            1,
            "651401",
            null,
            currentUsername: "LearnerCandidate");

        var exp = new List<ArplExperienceDetail>
        {
            new() { EmployerName = "Transnet Rail Engineering", JobTitle = "Assistant Boilermaker", StartDate = DateTime.UtcNow.AddYears(-5), EndDate = DateTime.UtcNow.AddYears(-2), DutiesDescription = "Structural steel fabrication and welding." },
            new() { EmployerName = "Heavy Mining Equipment SA", JobTitle = "Boiler Fabrication Specialist", StartDate = DateTime.UtcNow.AddYears(-2), DutiesDescription = "Pressure vessel repairs and cutting." }
        };

        var training = new List<ArplTrainingDetail>
        {
            new() { InstitutionName = "Sedibeng TVET College", CourseOrModuleTitle = "N2 Engineering Studies (Boilermaking)", CompletionDate = DateTime.UtcNow.AddYears(-3), CertificateObtained = "N2 National Certificate" }
        };

        var arpl = await tradeService.SubmitArplEvidenceAndChecklistAsync(
            app.Id,
            5,
            "Heavy Mining Equipment SA",
            "0119876543",
            exp,
            training,
            85.5m,
            "PortfolioAssessor");

        Assert.NotNull(arpl);
        Assert.Equal("ProceedToTradeTest", arpl.ArplRecommendation);
        Assert.True(arpl.PortfolioOfEvidenceVerified);
        Assert.Equal(85.5m, arpl.PortfolioScorePercentage);

        var updatedApp = await tradeService.GetApplicationByIdAsync(app.Id);
        Assert.NotNull(updatedApp);
        Assert.Equal(2, updatedApp.ExperienceDetails.Count);
        Assert.Single(updatedApp.TrainingDetails);
    }

    [Fact]
    public async Task SummativeAssessment_CompleteLifecycle_CreditTally_Moderation_Eisa_And_SorCryptographicIssuing()
    {
        var (factory, db, audit) = CreateContext();
        var (learner, person, org) = await SeedLearnerAsync(db);
        var assessmentService = new SummativeAssessmentAndModerationService(factory, audit);

        // 1. Create Report
        var report = await assessmentService.CreateSummativeAssessmentReportAsync(
            learner.Id,
            "National Certificate: Automotive Repair and Maintenance",
            "SAQA-65409",
            4,
            "Learnership",
            120,
            "ProviderCoordinator");

        Assert.NotNull(report);
        Assert.StartsWith("SOR-REP-", report.ReportNumber);
        Assert.Equal("Draft", report.StatusCode);

        // 2. Capture Unit Standard Credits (5 Unit Standards = 120 credits)
        var unitStandards = new List<SummativeAssessmentUnitStandard>
        {
            new() { UnitStandardCode = "US-119472", UnitStandardTitle = "Oral communications", NqfLevel = 3, Credits = 10, CompetencyStatusCode = "Competent" },
            new() { UnitStandardCode = "US-119457", UnitStandardTitle = "Written texts", NqfLevel = 3, Credits = 10, CompetencyStatusCode = "Competent" },
            new() { UnitStandardCode = "US-9844", UnitStandardTitle = "Mechanical fitting", NqfLevel = 4, Credits = 40, CompetencyStatusCode = "Competent" },
            new() { UnitStandardCode = "US-9845", UnitStandardTitle = "Pneumatics & hydraulics", NqfLevel = 4, Credits = 40, CompetencyStatusCode = "Competent" },
            new() { UnitStandardCode = "US-9846", UnitStandardTitle = "Workplace safety", NqfLevel = 4, Credits = 20, CompetencyStatusCode = "Competent" }
        };

        var assessed = await assessmentService.RecordUnitStandardCreditsAsync(
            report.Id,
            1,
            "ASSR-REG-2026-99",
            unitStandards,
            "AssessorUser");

        Assert.Equal("Assessed", assessed.StatusCode);
        Assert.Equal(120, assessed.TotalCreditsEarned);

        // 3. Internal Moderation
        var decisions = assessed.UnitStandardAssessments.ToDictionary(u => u.Id, u => ("Upheld", (string?)"Assessor judgement verified."));
        var moderated = await assessmentService.PerformInternalModerationAsync(
            report.Id,
            2,
            "MOD-REG-2026-88",
            decisions,
            "InternalModerator");

        Assert.Equal("InternalModerated", moderated.StatusCode);
        Assert.Equal(120, moderated.TotalCreditsEarned);

        // 4. ETQA External Moderation Approval
        var etqaApproved = await assessmentService.PerformEtqaExternalModerationAsync(
            report.Id,
            true,
            "ETQA verified and endorsed credits.",
            "EtqaManager");

        Assert.Equal("CreditsApproved", etqaApproved.StatusCode);

        // 5. Record EISA Exam Entry
        var eisa = await assessmentService.RecordEisaExamEntryAsync(
            report.Id,
            DateTime.UtcNow,
            "Johannesburg South Assessment Centre",
            "EISA-AUTO-2026-P1",
            92m,
            100m,
            null,
            "QctoModerator");

        Assert.Equal("Competent", eisa.CompetencyStatusCode);
        Assert.Equal(92m, eisa.PercentageScore);

        // 6. Issue Statement of Results (SOR) with Cryptographic Hash
        var sor = await assessmentService.IssueStatementOfResultsAsync(report.Id, "CertificationOfficer");

        Assert.NotNull(sor);
        Assert.StartsWith("SOR-", sor.SorSerialNumber);
        Assert.Equal(120, sor.TotalCreditsCertified);
        Assert.NotEmpty(sor.TamperProofHashSha256);
        Assert.Contains(sor.TamperProofHashSha256, sor.QrVerificationUrl);

        // 7. Verify SOR by Serial Number and Hash
        var verifiedBySerial = await assessmentService.VerifyStatementOfResultsAsync(sor.SorSerialNumber);
        Assert.NotNull(verifiedBySerial);
        Assert.Equal(sor.Id, verifiedBySerial.Id);

        var verifiedByHash = await assessmentService.VerifyStatementOfResultsAsync(sor.TamperProofHashSha256);
        Assert.NotNull(verifiedByHash);
        Assert.Equal(sor.Id, verifiedByHash.Id);

        // Verify Double-Write Audit Trail
        var auditLogs = await db.AuditLogs.Where(a => a.EntityName == "SummativeAssessmentReport" && a.RecordId == report.Id).ToListAsync();
        Assert.NotEmpty(auditLogs);
        Assert.Contains(auditLogs, a => a.ActionName == "IssueStatementOfResults");
    }
}
