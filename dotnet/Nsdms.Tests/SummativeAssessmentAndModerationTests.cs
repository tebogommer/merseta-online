using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class SummativeAssessmentAndModerationTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit) CreateContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        return (factory, db, audit);
    }

    [Fact]
    public void StatutoryCertificateNumberGenerator_RsaId_GeneratesCorrect12DigitFormat()
    {
        // RSA ID: 960412 5829 081 -> Middle 4 digits at index 5: "2582"
        string rsaId = "9604125829081";
        string certNumber = StatutoryCertificateNumberGenerator.Generate(rsaId, null, 1);

        Assert.Equal(12, certNumber.Length);
        Assert.StartsWith("172582", certNumber);
        Assert.True(long.TryParse(certNumber, out _), "Certificate number must be numeric.");
    }

    [Fact]
    public void StatutoryCertificateNumberGenerator_ForeignNational_UsesDobMonthYear()
    {
        // Foreign national with DOB 1998-07-24 -> Month: 07, Year: 98 -> "0798"
        var dob = new DateTime(1998, 7, 24);
        string certNumber = StatutoryCertificateNumberGenerator.Generate(null, dob, 42);

        Assert.Equal(12, certNumber.Length);
        Assert.StartsWith("170798", certNumber);
        Assert.True(long.TryParse(certNumber, out _), "Certificate number must be numeric.");
    }

    [Fact]
    public void StatutoryCertificateNumberGenerator_Fallback_UsesStandardPadding()
    {
        string certNumber = StatutoryCertificateNumberGenerator.Generate(null, null, 99);

        Assert.Equal(12, certNumber.Length);
        Assert.StartsWith("179999", certNumber);
    }

    [Fact]
    public async Task SummativeAssessment_AssessorAndModeratorSamePerson_ThrowsSegregationOfDutiesViolation()
    {
        var (factory, db, audit) = CreateContext();
        var service = new SummativeAssessmentAndModerationService(factory, audit);

        var person = new Person { FirstName = "Thabo", LastName = "Mokoena", RsaIdNumber = "9001015000080" };
        var assessor = new Person { FirstName = "David", LastName = "Smith" };
        var org = new Organisation { CompanyName = "Auto Precision", LevyNumber = "L999888777" };
        var sdp = new TrainingProvider { ProviderName = "Technical Skills Academy", AccreditationNumber = "ACC-001" };

        db.People.AddRange(person, assessor);
        db.Organisations.Add(org);
        db.TrainingProviders.Add(sdp);
        await db.SaveChangesAsync();

        var learner = new CompanyLearner 
        { 
            PersonId = person.Id, 
            OrganisationId = org.Id, 
            TrainingProviderId = sdp.Id,
            LearnerStatusCode = "Registered" 
        };
        db.CompanyLearners.Add(learner);
        await db.SaveChangesAsync();

        // Create Report
        var report = await service.CreateSummativeAssessmentReportAsync(
            companyLearnerId: learner.Id,
            qualificationTitle: "National Certificate: Automotive Repair",
            saqaQualId: "64289",
            nqfLevel: 4,
            interventionTypeCode: "Learnership",
            assessmentStageCode: "Completion",
            isFundedEmployer: true,
            totalCreditsRequired: 120,
            currentUsername: "SDP_USER"
        );

        // Record Unit Standards with assessor
        var unitStandards = new List<SummativeAssessmentUnitStandard>
        {
            new() { UnitStandardCode = "119457", UnitStandardTitle = "Communication", Credits = 10, CompetencyStatusCode = "Competent" }
        };

        await service.RecordUnitStandardCreditsAsync(
            report.Id,
            assessorPersonId: assessor.Id,
            assessorRegNumber: "ASSESS-001",
            unitStandards,
            currentUsername: "SDP_USER"
        );

        // Attempting to internally moderate by the SAME assessor person ID must throw Segregation of Duties Violation
        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() =>
            service.PerformInternalModerationAsync(
                report.Id,
                moderatorPersonId: assessor.Id, // VIOLATION!
                moderatorRegNumber: "MOD-001",
                moderationDecisions: new Dictionary<int, (string Outcome, string? Comments)>(),
                currentUsername: "SDP_USER"
            )
        );

        Assert.Contains("Segregation of Duties Violation", ex.Message, StringComparison.OrdinalIgnoreCase);
    }

    [Fact]
    public async Task SummativeAssessment_FundedEmployerProgressReport_Under50PercentCredits_ThrowsViolation()
    {
        var (factory, db, audit) = CreateContext();
        var service = new SummativeAssessmentAndModerationService(factory, audit);

        var person = new Person { FirstName = "Nandi", LastName = "Dlamini", RsaIdNumber = "9505050000085" };
        var assessor = new Person { FirstName = "Assessor", LastName = "One" };
        var fundedOrg = new Organisation { CompanyName = "Funded Manufacturing Co", LevyNumber = "L112233445" };
        var sdp = new TrainingProvider { ProviderName = "Industrial Academy", AccreditationNumber = "ACC-002" };

        db.People.AddRange(person, assessor);
        db.Organisations.Add(fundedOrg);
        db.TrainingProviders.Add(sdp);
        await db.SaveChangesAsync();

        var learner = new CompanyLearner 
        { 
            PersonId = person.Id, 
            OrganisationId = fundedOrg.Id, 
            TrainingProviderId = sdp.Id,
            LearnerStatusCode = "Registered" 
        };
        db.CompanyLearners.Add(learner);
        await db.SaveChangesAsync();

        // Create Progress Report (requires min 50% = 60 of 120 credits)
        var report = await service.CreateSummativeAssessmentReportAsync(
            companyLearnerId: learner.Id,
            qualificationTitle: "FET Certificate: Mechanical Engineering",
            saqaQualId: "64290",
            nqfLevel: 4,
            interventionTypeCode: "Learnership",
            assessmentStageCode: "Progress", // PROGRESS REPORT
            isFundedEmployer: true,
            totalCreditsRequired: 120,
            currentUsername: "SDP_USER"
        );

        // Supply only 30 credits (which is < 60 credits)
        var unitStandards = new List<SummativeAssessmentUnitStandard>
        {
            new() { UnitStandardCode = "US-01", UnitStandardTitle = "Module 1", Credits = 30, CompetencyStatusCode = "Competent", IsMandatory = true }
        };

        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() =>
            service.RecordUnitStandardCreditsAsync(
                report.Id,
                assessorPersonId: assessor.Id,
                assessorRegNumber: "ASSESS-002",
                unitStandards,
                currentUsername: "SDP_USER"
            )
        );

        Assert.Contains("minimum 50% credits", ex.Message, StringComparison.OrdinalIgnoreCase);
    }

    [Fact]
    public async Task ExternalModeration_RejectionWithVacsTaxonomy_TransitionsToReferredForRemediation()
    {
        var (factory, db, audit) = CreateContext();
        var service = new SummativeAssessmentAndModerationService(factory, audit);

        var person = new Person { FirstName = "Learner", LastName = "Test", RsaIdNumber = "9901015000088" };
        var org = new Organisation { CompanyName = "Org 1", LevyNumber = "L0001" };
        var sdp = new TrainingProvider { ProviderName = "Apex Training", AccreditationNumber = "ACC-003" };
        db.People.Add(person);
        db.Organisations.Add(org);
        db.TrainingProviders.Add(sdp);
        await db.SaveChangesAsync();

        var learner = new CompanyLearner { PersonId = person.Id, OrganisationId = org.Id, TrainingProviderId = sdp.Id, LearnerStatusCode = "Registered" };
        db.CompanyLearners.Add(learner);
        await db.SaveChangesAsync();

        var report = await service.CreateSummativeAssessmentReportAsync(
            companyLearnerId: learner.Id,
            qualificationTitle: "National Certificate: Welding Application",
            saqaQualId: "55100",
            nqfLevel: 4,
            interventionTypeCode: "Learnership",
            assessmentStageCode: "Completion",
            isFundedEmployer: false,
            totalCreditsRequired: 120,
            currentUsername: "SDP_USER"
        );

        var batch = await service.CreateAssessmentBatchAsync(
            providerId: sdp.Id,
            qualificationTitle: "National Certificate: Welding Application",
            saqaId: "55100",
            stageCode: "Completion",
            samplePercentage: 30,
            reportIds: new List<int> { report.Id },
            internalReportDocRef: "DOC-IMR-001",
            currentUsername: "SDP_USER"
        );

        Assert.Equal("InExternalModerationPool", batch.StatusCode);

        // QA rejects batch with VACS reason
        var outcome = await service.RecordExternalModerationOutcomeAsync(
            batch.Id,
            isUpheld: false,
            primaryRejectionReason: "VACS_EvidenceNotSufficient",
            vacsViolation: "Sufficient",
            remarks: "Portfolio of evidence lacks direct observation checklists for unit standard 116235.",
            remedialAction: "Resubmit PoE within 30 working days",
            checklistItems: null,
            currentUsername: "QA_OFFICER_01"
        );

        Assert.Equal("RejectedRemedialRequired", outcome.StatusCode);
        Assert.Equal("Rejected", outcome.BatchLearners.First().LearnerOutcomeStatus);
    }

    [Fact]
    public async Task ExternalModeration_Upheld_GeneratesStatutoryCertificateWithModerationDateAsIssueDate()
    {
        var (factory, db, audit) = CreateContext();
        var service = new SummativeAssessmentAndModerationService(factory, audit);

        var person = new Person { FirstName = "Sipho", LastName = "Nkosi", RsaIdNumber = "9201015000088" };
        var assessor = new Person { FirstName = "Assessor", LastName = "A" };
        var moderator = new Person { FirstName = "Moderator", LastName = "M" };
        var org = new Organisation { CompanyName = "Steel Works", LevyNumber = "N0001" };
        var sdp = new TrainingProvider { ProviderName = "National Welding Institute", AccreditationNumber = "ACC-004" };

        db.People.AddRange(person, assessor, moderator);
        db.Organisations.Add(org);
        db.TrainingProviders.Add(sdp);
        await db.SaveChangesAsync();

        var learner = new CompanyLearner 
        { 
            PersonId = person.Id, 
            OrganisationId = org.Id, 
            TrainingProviderId = sdp.Id,
            LearnerStatusCode = "Registered" 
        };
        db.CompanyLearners.Add(learner);
        await db.SaveChangesAsync();

        var report = await service.CreateSummativeAssessmentReportAsync(
            companyLearnerId: learner.Id,
            qualificationTitle: "National Certificate: Fabrication",
            saqaQualId: "77123",
            nqfLevel: 4,
            interventionTypeCode: "Learnership",
            assessmentStageCode: "Completion",
            isFundedEmployer: false,
            totalCreditsRequired: 120,
            currentUsername: "SDP_USER"
        );

        // Record credits for 120
        var unitStandards = new List<SummativeAssessmentUnitStandard>
        {
            new() { UnitStandardCode = "US-CORE-1", UnitStandardTitle = "Core Fabrication", Credits = 120, CompetencyStatusCode = "Competent", IsMandatory = true }
        };
        await service.RecordUnitStandardCreditsAsync(report.Id, assessor.Id, "ASS-10", unitStandards, "SDP_USER");

        // Record Internal Moderation
        var usList = await db.SummativeAssessmentUnitStandards.Where(u => u.SummativeAssessmentReportId == report.Id).ToListAsync();
        var decisions = new Dictionary<int, (string Outcome, string? Comments)>
        {
            { usList.First().Id, ("Upheld", "Valid and competent") }
        };
        await service.PerformInternalModerationAsync(report.Id, moderator.Id, "MOD-10", decisions, "SDP_USER");

        var batch = await service.CreateAssessmentBatchAsync(
            providerId: sdp.Id,
            qualificationTitle: "National Certificate: Fabrication",
            saqaId: "77123",
            stageCode: "Completion",
            samplePercentage: 50,
            reportIds: new List<int> { report.Id },
            internalReportDocRef: "DOC-IMR-002",
            currentUsername: "SDP_USER"
        );

        var outcome = await service.RecordExternalModerationOutcomeAsync(
            batch.Id,
            isUpheld: true,
            primaryRejectionReason: null,
            vacsViolation: null,
            remarks: "Exemplary assessment instruments. Full alignment with SAQA NQF exit level outcomes.",
            remedialAction: null,
            checklistItems: null,
            currentUsername: "QA_OFFICER_CHAIR"
        );

        Assert.Equal("Upheld", outcome.StatusCode);

        // Verify Certificate was generated in database
        var cert = await db.LearnerCertificates.FirstOrDefaultAsync(c => c.SummativeAssessmentReportId == report.Id);
        Assert.NotNull(cert);
        Assert.Equal(DateTime.UtcNow.Date, cert.IssueDate.Date);
        Assert.StartsWith("171500", cert.CertificateNumber); // Middle 4 digits of 9201015000088 (characters at indices 5..8: "1500")
    }
}
