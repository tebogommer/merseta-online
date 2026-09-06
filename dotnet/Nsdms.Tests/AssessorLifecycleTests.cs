using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

/// <summary>
/// Comprehensive Unit & Integration Tests for Assessor and Moderator Registration
/// based on the signed 2023 use case specification (Ref: MerSeta\NSDMS\LMS\LR\01):
/// - 3-Year post-qualification experience validation and rejection.
/// - Unit standard cascading & constituent unit standards protection against removal.
/// - Standalone unit standard addition & removal.
/// - 4-Stage Maker-Checker workflow (Verification, Evaluation, Committee Decision, Final Approval).
/// - Review Committee rejection (Resubmission vs Terminal).
/// - Multi-SDP provider affiliation & SLA linking.
/// - Disciplinary sanctioning (Suspension, De-registration, DHA Deceased status).
/// - QuestPDF Statutory documents (Annexure 10.1 Certificate Letter, Annexure 10.3 Statement of Scope, Disciplinary Letter).
/// </summary>
public class AssessorLifecycleTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, AssessorRegistrationService regService, AssessorDisciplinaryService discService, QuestPdfDocumentService pdfService) CreateTestContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var regService = new AssessorRegistrationService(factory, audit);
        var discService = new AssessorDisciplinaryService(factory, audit);

        var inMemory = new Dictionary<string, string?>
        {
            { "System.BaseUrl", "https://nsdms.merseta.org.za" }
        };
        var conf = new ConfigurationBuilder().AddInMemoryCollection(inMemory).Build();
        var configService = new SystemConfigurationService(factory, conf, audit);
        var featureFlags = new FeatureFlagService(factory, conf, audit);
        var pdfService = new QuestPdfDocumentService(factory, featureFlags, configService, null);

        return (factory, db, audit, regService, discService, pdfService);
    }

    [Fact]
    public async Task PostQualification_LessThanThreeYears_ThrowsStatutoryValidationException()
    {
        var (_, db, _, regService, _, _) = CreateTestContext();

        var person = new Person
        {
            FirstName = "Kagiso",
            LastName = "Mahlangu",
            RsaIdNumber = "9001015800084",
            EmailAddress = "kagiso.m@example.com"
        };
        db.People.Add(person);
        await db.SaveChangesAsync();

        var app = await regService.CreateDraftApplicationAsync(new CreateAssessorApplicationRequest
        {
            PersonId = person.Id,
            PractitionerType = "Assessor"
        });

        // 1 year post-qualification (< 3 statutory years required)
        var addScopeRequest = new AddQualificationScopeRequest
        {
            SaqaQualificationId = 58781,
            QualificationTitle = "National Certificate: Mechanical Engineering",
            QualificationObtainedDate = DateTime.UtcNow.AddYears(-1)
        };

        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() =>
            regService.AddQualificationScopeAsync(app.Id, addScopeRequest));

        Assert.Contains("3 years", ex.Message, StringComparison.OrdinalIgnoreCase);
    }

    [Fact]
    public async Task PostQualification_GreaterThanThreeYears_SucceedsAndCascadesConstituentStandards()
    {
        var (_, db, _, regService, _, _) = CreateTestContext();

        var person = new Person
        {
            FirstName = "Sipho",
            LastName = "Dlamini",
            RsaIdNumber = "8505055800083",
            EmailAddress = "sipho.d@example.com"
        };
        db.People.Add(person);
        await db.SaveChangesAsync();

        var app = await regService.CreateDraftApplicationAsync(new CreateAssessorApplicationRequest
        {
            PersonId = person.Id,
            PractitionerType = "Assessor"
        });

        // 4 years post-qualification (> 3 statutory years)
        var addScopeRequest = new AddQualificationScopeRequest
        {
            SaqaQualificationId = 58781,
            QualificationTitle = "National Certificate: Fitting and Turning",
            QualificationObtainedDate = DateTime.UtcNow.AddYears(-4),
            UnitStandards = new List<ConstituentUnitStandardDto>
            {
                new() { UnitStandardCode = "US-119744", UnitStandardTitle = "Select and use appropriate hand tools", NqfLevel = 2, Credits = 6 },
                new() { UnitStandardCode = "US-12476", UnitStandardTitle = "Turn workpieces using basic lathe techniques", NqfLevel = 3, Credits = 12 }
            }
        };

        var scope = await regService.AddQualificationScopeAsync(app.Id, addScopeRequest);

        Assert.NotNull(scope);
        Assert.Equal(58781, scope.SaqaQualificationId);
        Assert.Equal(2, scope.UnitStandards.Count);
        Assert.All(scope.UnitStandards, us => Assert.True(us.IsPopulatedFromQualification));
    }

    [Fact]
    public async Task ConstituentUnitStandard_CannotBeRemoved_ThrowsStatutoryProtectionException()
    {
        var (_, db, _, regService, _, _) = CreateTestContext();

        var person = new Person
        {
            FirstName = "Precious",
            LastName = "Nkosi",
            RsaIdNumber = "8808085800081",
            EmailAddress = "precious.n@example.com"
        };
        db.People.Add(person);
        await db.SaveChangesAsync();

        var app = await regService.CreateDraftApplicationAsync(new CreateAssessorApplicationRequest
        {
            PersonId = person.Id,
            PractitionerType = "Assessor"
        });

        var scope = await regService.AddQualificationScopeAsync(app.Id, new AddQualificationScopeRequest
        {
            SaqaQualificationId = 65432,
            QualificationTitle = "Welding Application and Practice",
            QualificationObtainedDate = DateTime.UtcNow.AddYears(-5),
            UnitStandards = new List<ConstituentUnitStandardDto>
            {
                new() { UnitStandardCode = "US-WEL-01", UnitStandardTitle = "Shielded Metal Arc Welding", NqfLevel = 3, Credits = 10 }
            }
        });

        var constituentUs = scope.UnitStandards.First();

        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() =>
            regService.RemoveUnitStandardAsync(constituentUs.Id));

        Assert.Contains("Constituent unit standards", ex.Message, StringComparison.OrdinalIgnoreCase);
    }

    [Fact]
    public async Task StandaloneUnitStandard_CanBeAddedAndRemovedSuccessfully()
    {
        var (_, db, _, regService, _, _) = CreateTestContext();

        var person = new Person
        {
            FirstName = "Bongani",
            LastName = "Zulu",
            RsaIdNumber = "8303035800089",
            EmailAddress = "bongani.z@example.com"
        };
        db.People.Add(person);
        await db.SaveChangesAsync();

        var app = await regService.CreateDraftApplicationAsync(new CreateAssessorApplicationRequest
        {
            PersonId = person.Id,
            PractitionerType = "Assessor"
        });

        var scope = await regService.AddQualificationScopeAsync(app.Id, new AddQualificationScopeRequest
        {
            SaqaQualificationId = 77112,
            QualificationTitle = "Electrical Engineering Systems",
            QualificationObtainedDate = DateTime.UtcNow.AddYears(-6)
        });

        var standalone = await regService.AddStandaloneUnitStandardAsync(scope.Id, new ConstituentUnitStandardDto
        {
            UnitStandardCode = "US-ELE-99",
            UnitStandardTitle = "Fault finding in 3-phase systems",
            NqfLevel = 4,
            Credits = 8
        });

        Assert.NotNull(standalone);
        Assert.False(standalone.IsPopulatedFromQualification);

        var removed = await regService.RemoveUnitStandardAsync(standalone.Id);
        Assert.True(removed);
    }

    [Fact]
    public async Task FourStageMakerChecker_FullWorkflow_RegistersAssessorAndIssuesSerial()
    {
        var (_, db, _, regService, _, _) = CreateTestContext();

        var person = new Person
        {
            FirstName = "Nomvula",
            LastName = "Mthembu",
            RsaIdNumber = "8204045800087",
            EmailAddress = "nomvula.m@example.com"
        };
        db.People.Add(person);
        await db.SaveChangesAsync();

        // 1. Create Draft
        var app = await regService.CreateDraftApplicationAsync(new CreateAssessorApplicationRequest
        {
            PersonId = person.Id,
            PractitionerType = "Assessor",
            EmploymentStatusCode = "Employed",
            UrbanRuralArea = "Urban"
        });
        Assert.Equal("Draft", app.StageCode);

        // Add Scope
        await regService.AddQualificationScopeAsync(app.Id, new AddQualificationScopeRequest
        {
            SaqaQualificationId = 99881,
            QualificationTitle = "Automotive Body Repair",
            QualificationObtainedDate = DateTime.UtcNow.AddYears(-5)
        });

        // 2. Submit
        app = await regService.SignOffAndSubmitAsync(app.Id, "NomvulaM");
        Assert.Equal("VerificationPending", app.StageCode);
        Assert.NotNull(app.SubmissionDate);

        // 3. Stage 1: Document Verification
        app = await regService.VerifyDocumentAsync(new VerificationStepRequest
        {
            ApplicationId = app.Id,
            Recommendation = "Recommend",
            Explanation = "All certified certificates, ID copies, and proof of experience verified."
        }, "VerificationOfficer1");
        Assert.Equal("EvaluationPending", app.StageCode);
        Assert.Equal("Recommended", app.VerificationStatus);

        // 4. Stage 2: Application Evaluation
        app = await regService.EvaluateApplicationAsync(new EvaluationStepRequest
        {
            ApplicationId = app.Id,
            Recommendation = "Recommend",
            Explanation = "Candidate meets all SAQA and merSETA ETQA criteria for automotive body repair assessor registration."
        }, "EvaluationOfficer2");
        Assert.Equal("ReviewCommitteePending", app.StageCode);
        Assert.Equal("Recommended", app.EvaluationStatus);

        // 5. Stage 3: ETQA Review Committee Ratification
        app = await regService.RecordCommitteeDecisionAsync(new ReviewCommitteeDecisionRequest
        {
            ApplicationId = app.Id,
            Decision = "Approve",
            CommitteeDecisionNumber = "ETQA-COM-2026-042",
            MeetingDate = DateTime.UtcNow.Date,
            Notes = "Unanimous committee approval."
        }, "CommitteeSecretary");
        Assert.Equal("Approved", app.ReviewCommitteeDecision);
        Assert.Equal("ETQA-COM-2026-042", app.ReviewCommitteeDecisionNumber);

        // 6. Stage 4: Senior QA Final Approval
        app = await regService.FinalApproveAsync(new FinalApprovalRequest
        {
            ApplicationId = app.Id,
            ApprovalComments = "Final executive stamp granted. Certificate authorized."
        }, "SeniorQaManager");
        Assert.Equal("Approved", app.StageCode);
        Assert.NotNull(app.RegisteredAssessorId);

        // Verify Created EtqaAssessor Record
        var assessor = await db.EtqaAssessors.FindAsync(app.RegisteredAssessorId.Value);
        Assert.NotNull(assessor);
        Assert.Equal("Registered", assessor.RegistrationStatusCode);
        Assert.StartsWith("ASS-", assessor.AssessorRegistrationNumber);
        Assert.Equal("ETQA-COM-2026-042", assessor.EtqaDecisionNumber);
        Assert.NotNull(assessor.CertificateStartDate);
        Assert.NotNull(assessor.CertificateEndDate);

        // Verify 3-Year statutory cycle
        var validityDays = (assessor.CertificateEndDate!.Value - assessor.CertificateStartDate!.Value).TotalDays;
        Assert.InRange(validityDays, 365 * 3, 366 * 3 + 2);
    }

    [Fact]
    public async Task ReviewCommittee_Rejection_SupportsResubmissionAndTerminalRejection()
    {
        var (_, db, _, regService, _, _) = CreateTestContext();

        var person1 = new Person { FirstName = "Johan", LastName = "Botha", RsaIdNumber = "7901015009088" };
        var person2 = new Person { FirstName = "Andries", LastName = "Nel", RsaIdNumber = "8102025009089" };
        db.People.AddRange(person1, person2);
        await db.SaveChangesAsync();

        // Case A: Reject for Resubmission (IsFinalRejection = false)
        var app1 = await regService.CreateDraftApplicationAsync(new CreateAssessorApplicationRequest { PersonId = person1.Id });
        await regService.AddQualificationScopeAsync(app1.Id, new AddQualificationScopeRequest { SaqaQualificationId = 1111, QualificationTitle = "Diesel", QualificationObtainedDate = DateTime.UtcNow.AddYears(-4) });
        await regService.SignOffAndSubmitAsync(app1.Id);
        await regService.VerifyDocumentAsync(new VerificationStepRequest { ApplicationId = app1.Id, Recommendation = "Recommend" });
        await regService.EvaluateApplicationAsync(new EvaluationStepRequest { ApplicationId = app1.Id, Recommendation = "Recommend" });

        var resubmitted = await regService.RecordCommitteeDecisionAsync(new ReviewCommitteeDecisionRequest
        {
            ApplicationId = app1.Id,
            Decision = "Reject",
            IsFinalRejection = false,
            RejectionReason = "Incomplete Workplace Logbook",
            RejectionComments = "Candidate must upload 6 additional months of workshop supervisor logbooks."
        });
        Assert.Equal("RejectedForResubmission", resubmitted.StageCode);

        // Case B: Final Terminal Rejection (IsFinalRejection = true)
        var app2 = await regService.CreateDraftApplicationAsync(new CreateAssessorApplicationRequest { PersonId = person2.Id });
        await regService.AddQualificationScopeAsync(app2.Id, new AddQualificationScopeRequest { SaqaQualificationId = 2222, QualificationTitle = "Boilermaking", QualificationObtainedDate = DateTime.UtcNow.AddYears(-5) });
        await regService.SignOffAndSubmitAsync(app2.Id);
        await regService.VerifyDocumentAsync(new VerificationStepRequest { ApplicationId = app2.Id, Recommendation = "Recommend" });
        await regService.EvaluateApplicationAsync(new EvaluationStepRequest { ApplicationId = app2.Id, Recommendation = "Recommend" });

        var finalRejected = await regService.RecordCommitteeDecisionAsync(new ReviewCommitteeDecisionRequest
        {
            ApplicationId = app2.Id,
            Decision = "Reject",
            IsFinalRejection = true,
            RejectionReason = "Fraudulent Certification",
            RejectionComments = "Forged trade certificate detected upon verification."
        });
        Assert.Equal("RejectedApplication", finalRejected.StageCode);
    }

    [Fact]
    public async Task MultiSdpAffiliation_LinksProviderWithSlaAndRemoves()
    {
        var (_, db, _, regService, _, _) = CreateTestContext();

        var person = new Person { FirstName = "Fatima", LastName = "Patel", RsaIdNumber = "8606065800082" };
        var sdp = new TrainingProvider { ProviderName = "Gauteng Technical Academy", AccreditationNumber = "SDP-GTA-2024-01" };
        db.People.Add(person);
        db.TrainingProviders.Add(sdp);
        await db.SaveChangesAsync();

        var app = await regService.CreateDraftApplicationAsync(new CreateAssessorApplicationRequest { PersonId = person.Id });

        var link = await regService.LinkProviderAffiliationAsync(app.Id, sdp.Id, "DOC-SLA-GTA-2026.pdf", "FatimaP");
        Assert.NotNull(link);
        Assert.Equal(sdp.Id, link.TrainingProviderId);
        Assert.Equal("DOC-SLA-GTA-2026.pdf", link.SlaDocumentAttachmentReference);

        var removed = await regService.RemoveProviderAffiliationAsync(link.Id, "FatimaP");
        Assert.True(removed);
    }

    [Fact]
    public async Task DisciplinarySanction_Suspension_BlocksAssessmentAbility()
    {
        var (_, db, _, _, discService, _) = CreateTestContext();

        var person = new Person { FirstName = "Hendrik", LastName = "Venter", RsaIdNumber = "7707075009085" };
        db.People.Add(person);
        await db.SaveChangesAsync();

        var assessor = new EtqaAssessor
        {
            PersonId = person.Id,
            AssessorRegistrationNumber = "ASS-2026-99001",
            RegistrationStatusCode = "Registered",
            AssessmentAbilitySuspended = false,
            CertificateStartDate = DateTime.UtcNow.AddMonths(-6),
            CertificateEndDate = DateTime.UtcNow.AddYears(2)
        };
        db.EtqaAssessors.Add(assessor);
        await db.SaveChangesAsync();

        // 1. Initial assessment status
        var initialCanAssess = await discService.CanConductAssessmentAsync(assessor.Id);
        Assert.True(initialCanAssess);

        // 2. Initiate Disciplinary Case
        var caseRecord = await discService.InitiateCaseAsync(new InitiateDisciplinaryCaseRequest
        {
            EtqaAssessorId = assessor.Id,
            CaseType = "Suspension",
            ComplaintSummary = "Serious assessment irregularity reported during artisan moderation.",
            ComplaintDocumentRef = "DOC-CMPL-001.pdf"
        }, "EtqaInvestigator");
        Assert.Equal("UnderInvestigation", caseRecord.CaseStatusCode);

        // 3. Record Investigation Report
        caseRecord = await discService.RecordInvestigationReportAsync(caseRecord.Id, "Independent moderator corroborated grading irregularities.", "EtqaInvestigator");
        Assert.Equal("InvestigationCompleted", caseRecord.CaseStatusCode);

        // 4. Record Committee Sanction (SUSPENDED)
        caseRecord = await discService.RecordOutcomeAsync(new RecordDisciplinaryOutcomeRequest
        {
            CaseId = caseRecord.Id,
            OutcomeCode = "SUSPENDED",
            ReviewCommitteeDecisionNumber = "ETQA-DISC-2026-088",
            SuspensionStartDate = DateTime.UtcNow.Date,
            SuspensionEndDate = DateTime.UtcNow.AddMonths(6).Date,
            DevelopmentPlanDetails = "Assessor must re-take Unit Standard 115753 moderation course.",
            DecisionLetterDocumentRef = "DEC-SUSP-088.pdf"
        }, "CommitteeChair");

        Assert.Equal("Sanctioned", caseRecord.CaseStatusCode);
        Assert.Equal("SUSPENDED", caseRecord.OutcomeCode);

        // Verify Practitioner record updated
        var refreshed = await db.EtqaAssessors.AsNoTracking().FirstOrDefaultAsync(a => a.Id == assessor.Id);
        Assert.NotNull(refreshed);
        Assert.Equal("Suspended", refreshed.RegistrationStatusCode);
        Assert.True(refreshed.AssessmentAbilitySuspended);

        // Verify CanConductAssessment returns false
        var canAssessNow = await discService.CanConductAssessmentAsync(assessor.Id);
        Assert.False(canAssessNow);
    }

    [Fact]
    public async Task DisciplinarySanction_DhaDeceased_DeRegistersAndStampsDeceased()
    {
        var (factory, db, _, _, discService, _) = CreateTestContext();

        var person = new Person { FirstName = "Late", LastName = "Practitioner", RsaIdNumber = "6001015009081" };
        db.People.Add(person);
        await db.SaveChangesAsync();

        var assessor = new EtqaAssessor
        {
            PersonId = person.Id,
            AssessorRegistrationNumber = "ASS-2026-99002",
            RegistrationStatusCode = "Registered",
            AssessmentAbilitySuspended = false
        };
        db.EtqaAssessors.Add(assessor);
        await db.SaveChangesAsync();

        var caseRecord = await discService.RecordDeceasedAsync(assessor.Id, "DHA-DEATH-CERT-778899.pdf", "DhaIntegrationService");

        Assert.NotNull(caseRecord);
        Assert.Equal("Closed", caseRecord.CaseStatusCode);
        Assert.Equal("DECEASED", caseRecord.OutcomeCode);

        var refreshed = await db.EtqaAssessors.AsNoTracking().FirstOrDefaultAsync(a => a.Id == assessor.Id);
        Assert.NotNull(refreshed);
        Assert.Equal("De-Registered", refreshed.RegistrationStatusCode);
        Assert.Equal("Deceased", refreshed.DeRegistrationReason);
        Assert.True(refreshed.AssessmentAbilitySuspended);

        var canAssess = await discService.CanConductAssessmentAsync(assessor.Id);
        Assert.False(canAssess);
    }

    [Fact]
    public async Task StatutoryQuestPdfGeneration_Annexure10_1_10_3_and_DisciplinaryLetter()
    {
        var (_, db, _, regService, discService, pdfService) = CreateTestContext();

        var person = new Person
        {
            FirstName = "Ntombi",
            LastName = "Khumalo",
            RsaIdNumber = "8909095800086",
            EmailAddress = "ntombi.k@example.com",
            PhysicalAddress = "45 Commissioner Street, Johannesburg"
        };
        var sdp = new TrainingProvider
        {
            ProviderName = "Ekurhuleni Artisan Centre",
            AccreditationNumber = "SDP-EKUR-0042"
        };
        db.People.Add(person);
        db.TrainingProviders.Add(sdp);
        await db.SaveChangesAsync();

        var app = await regService.CreateDraftApplicationAsync(new CreateAssessorApplicationRequest
        {
            PersonId = person.Id,
            PractitionerType = "Assessor"
        });

        await regService.AddQualificationScopeAsync(app.Id, new AddQualificationScopeRequest
        {
            SaqaQualificationId = 64749,
            QualificationTitle = "National Certificate: Boilermaking",
            QualificationObtainedDate = DateTime.UtcNow.AddYears(-5),
            UnitStandards = new List<ConstituentUnitStandardDto>
            {
                new() { UnitStandardCode = "US-BOIL-01", UnitStandardTitle = "Fabricate steel components", NqfLevel = 3, Credits = 12 }
            }
        });

        await regService.LinkProviderAffiliationAsync(app.Id, sdp.Id, "SLA-EKUR-2026.pdf");
        await regService.SignOffAndSubmitAsync(app.Id);
        await regService.VerifyDocumentAsync(new VerificationStepRequest { ApplicationId = app.Id, Recommendation = "Recommend" });
        await regService.EvaluateApplicationAsync(new EvaluationStepRequest { ApplicationId = app.Id, Recommendation = "Recommend" });
        await regService.RecordCommitteeDecisionAsync(new ReviewCommitteeDecisionRequest
        {
            ApplicationId = app.Id,
            Decision = "Approve",
            CommitteeDecisionNumber = "COM-DEC-2026-789"
        });
        app = await regService.FinalApproveAsync(new FinalApprovalRequest { ApplicationId = app.Id });

        // 1. Generate Annexure 10.1: Certificate Letter PDF
        var certLetterPdf = await pdfService.GenerateAssessorCertificateLetterPdfAsync(app.Id);
        Assert.NotNull(certLetterPdf);
        Assert.True(certLetterPdf.Length > 1000, "Certificate letter PDF should contain valid generated content.");

        // 2. Generate Annexure 10.3: Statement of Scope PDF
        var statementOfScopePdf = await pdfService.GenerateAssessorStatementOfScopePdfAsync(app.Id);
        Assert.NotNull(statementOfScopePdf);
        Assert.True(statementOfScopePdf.Length > 1000, "Statement of Scope PDF should contain valid generated content.");

        // 3. Generate Disciplinary Notification Letter PDF
        var caseRecord = await discService.InitiateCaseAsync(new InitiateDisciplinaryCaseRequest
        {
            EtqaAssessorId = app.RegisteredAssessorId!.Value,
            CaseType = "Suspension",
            ComplaintSummary = "Test irregularity notification"
        });
        await discService.RecordInvestigationReportAsync(caseRecord.Id, "Report summary completed");
        await discService.RecordOutcomeAsync(new RecordDisciplinaryOutcomeRequest
        {
            CaseId = caseRecord.Id,
            OutcomeCode = "SUSPENDED",
            ReviewCommitteeDecisionNumber = "DISC-COM-99"
        });

        var disciplinaryLetterPdf = await pdfService.GenerateAssessorDisciplinaryLetterPdfAsync(caseRecord.Id);
        Assert.NotNull(disciplinaryLetterPdf);
        Assert.True(disciplinaryLetterPdf.Length > 1000, "Disciplinary notification letter PDF should contain valid generated content.");
    }
}
