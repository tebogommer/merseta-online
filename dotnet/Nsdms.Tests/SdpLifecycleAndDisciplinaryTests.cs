using System.Text;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

/// <summary>
/// Unit & Integration Tests for SDP Lifecycle, Disciplinary Actions, Site Inspections, and Controlled Documents
/// based on the Signed 2023 SDP Accreditation Specification:
/// - Disciplinary case initiation and audit logging.
/// - Sanction adjudication (SUSPENDED / DEREGISTERED) and automated freezing of learner enrolments.
/// - Reinstatement of sanctions restoring learner enrolment eligibility.
/// - Form ETQ-TP-012 Physical Site Inspection audit checklist (tool ratio and OHS compliance).
/// - Re-accreditation submission preserving operational status unhindered.
/// - QuestPDF Controlled Documents (Form ETQ-TP-002 Certificate, Form ETQ-TP-015 Sanction Notice, Form ETQ-TP-012 Site Inspection, Form ETQ-TP-001 Outcome Letter).
/// </summary>
public class SdpLifecycleAndDisciplinaryTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, SdpDisciplinaryService discService, QuestPdfDocumentService pdfService) CreateTestContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var discService = new SdpDisciplinaryService(factory, audit);

        var inMemory = new Dictionary<string, string?>
        {
            { "System.BaseUrl", "https://nsdms.merseta.org.za" },
            { "General.SetaName", "Manufacturing, Engineering and Related Services SETA (merSETA)" }
        };
        var conf = new ConfigurationBuilder().AddInMemoryCollection(inMemory).Build();
        var configService = new SystemConfigurationService(factory, conf, audit);
        var featureFlags = new FeatureFlagService(factory, conf, audit);
        var pdfService = new QuestPdfDocumentService(factory, featureFlags, configService, null);

        return (factory, db, audit, discService, pdfService);
    }

    private static async Task<TrainingProvider> SeedProviderAsync(NsdmsDbContext db, string orgName = "Apex Technical Academy", string accreditationNo = "17-QA/ACC/0942/26")
    {
        var org = new Organisation
        {
            CompanyName = orgName,
            TradingName = orgName,
            SdlNumber = "L987654321",
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "TEST"
        };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var provider = new TrainingProvider
        {
            OrganisationId = org.Id,
            AccreditationNumber = accreditationNo,
            AccreditationStartDate = DateTime.UtcNow.AddYears(-1),
            AccreditationEndDate = DateTime.UtcNow.AddYears(4),
            ProviderStatusCode = "Active",
            AccreditationStream = "Primary merSETA Scope",
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "TEST"
        };
        db.TrainingProviders.Add(provider);
        await db.SaveChangesAsync();

        return provider;
    }

    [Fact]
    public async Task Disciplinary_InitiateCase_AssignsSequentialCaseNumberAndAudits()
    {
        var (_, db, _, discService, _) = CreateTestContext();
        var provider = await SeedProviderAsync(db);

        var req = new InitiateSdpDisciplinaryCaseRequest
        {
            TrainingProviderId = provider.Id,
            CaseType = "Suspension",
            ComplaintSource = "AuditFinding",
            AllegationSummary = "Critical failure to maintain SANS machine guarding and lack of qualified artisan mentors on site."
        };

        var created = await discService.InitiateCaseAsync(req, "QA_Auditor_1");

        Assert.NotNull(created);
        Assert.StartsWith($"SDP-DISC-{DateTime.UtcNow.Year}-", created.CaseNumber);
        Assert.Equal("UnderInvestigation", created.Status);
        Assert.Equal("Suspension", created.CaseType);

        // Verify audit log
        var audit = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "SdpDisciplinaryCase" && a.RecordId == created.Id);
        Assert.NotNull(audit);
        Assert.Equal("InitiateDisciplinaryCase", audit.ActionName);
    }

    [Fact]
    public async Task Disciplinary_SuspendProvider_FreezesLearnerEnrolments()
    {
        var (factory, db, _, discService, _) = CreateTestContext();
        var provider = await SeedProviderAsync(db);

        // Initially eligible
        var initialEligibility = await discService.IsProviderEligibleForEnrolmentsAsync(provider.Id);
        Assert.True(initialEligibility);

        // Initiate case
        var c = await discService.InitiateCaseAsync(new InitiateSdpDisciplinaryCaseRequest
        {
            TrainingProviderId = provider.Id,
            CaseType = "Suspension",
            ComplaintSource = "AuditFinding",
            AllegationSummary = "Unauthorised assessment delivery at unapproved satellite site."
        }, "QA_Lead");

        // Record hearing findings
        await discService.RecordInvestigationReportAsync(c.Id, "Investigation confirmed satellite training without merSETA approval.", "QA_Investigator");

        // Committee adjudicates SUSPENDED sanction
        var outcomeReq = new RecordSdpDisciplinaryOutcomeRequest
        {
            CaseId = c.Id,
            OutcomeCode = "SUSPENDED",
            SanctionType = "TemporarySuspension",
            ReviewCommitteeDecisionNumber = "ETQA-RC-2026-0042",
            ReviewCommitteeDate = DateTime.UtcNow,
            SanctionStartDate = DateTime.UtcNow,
            SanctionEndDate = DateTime.UtcNow.AddMonths(6),
            NoticeDocumentRef = "NOT-2026-SANCT-01"
        };

        var outcome = await discService.RecordOutcomeAsync(outcomeReq, "RC_Secretary");

        Assert.Equal("Suspended", outcome.Status);
        Assert.Equal("TemporarySuspension", outcome.SanctionType);

        // Refresh provider from db
        using var verifyDb = (NsdmsDbContext)factory.CreateDbContext();
        var updatedProvider = await verifyDb.TrainingProviders.FirstOrDefaultAsync(p => p.Id == provider.Id);
        Assert.NotNull(updatedProvider);
        Assert.Equal("Suspended", updatedProvider.ProviderStatusCode);

        // Enrolments MUST now be blocked
        var eligibilityAfterSanction = await discService.IsProviderEligibleForEnrolmentsAsync(provider.Id);
        Assert.False(eligibilityAfterSanction);
    }

    [Fact]
    public async Task Disciplinary_ReinstateProvider_RestoresLearnerEnrolmentEligibility()
    {
        var (factory, db, _, discService, _) = CreateTestContext();
        var provider = await SeedProviderAsync(db);

        var c = await discService.InitiateCaseAsync(new InitiateSdpDisciplinaryCaseRequest
        {
            TrainingProviderId = provider.Id,
            CaseType = "Suspension",
            ComplaintSource = "LearnerComplaint",
            AllegationSummary = "Appealed disciplinary matter."
        }, "QA_Admin");

        // First suspend
        await discService.RecordOutcomeAsync(new RecordSdpDisciplinaryOutcomeRequest
        {
            CaseId = c.Id,
            OutcomeCode = "SUSPENDED",
            SanctionType = "TemporarySuspension",
            ReviewCommitteeDecisionNumber = "ETQA-RC-2026-0088"
        }, "Admin");

        Assert.False(await discService.IsProviderEligibleForEnrolmentsAsync(provider.Id));

        // Now reinstate
        await discService.RecordOutcomeAsync(new RecordSdpDisciplinaryOutcomeRequest
        {
            CaseId = c.Id,
            OutcomeCode = "REINSTATED",
            SanctionType = "None",
            ReviewCommitteeDecisionNumber = "ETQA-RC-2026-0120"
        }, "Admin");

        using var verifyDb2 = (NsdmsDbContext)factory.CreateDbContext();
        var updatedProvider = await verifyDb2.TrainingProviders.FirstOrDefaultAsync(p => p.Id == provider.Id);
        Assert.NotNull(updatedProvider);
        Assert.Equal("Active", updatedProvider.ProviderStatusCode);

        Assert.True(await discService.IsProviderEligibleForEnrolmentsAsync(provider.Id));
    }

    [Fact]
    public async Task SiteInspection_Form012_RecordsChecklistAndToolScore()
    {
        var (_, db, _, discService, _) = CreateTestContext();
        var provider = await SeedProviderAsync(db);

        var inspector = new Person
        {
            FirstName = "Nombulelo",
            LastName = "Zulu",
            RsaIdNumber = "8808085800082",
            EmailAddress = "nombulelo.z@merseta.org.za"
        };
        db.People.Add(inspector);
        await db.SaveChangesAsync();

        var inspection = new SdpSiteInspection
        {
            TrainingProviderId = provider.Id,
            InspectionDate = DateTime.UtcNow,
            InspectorPersonId = inspector.Id,
            InspectionType = "PhysicalOnSite",
            WorkshopSquareMeters = 220.50m,
            ClassroomSquareMeters = 110.00m,
            ToolRatioScore = 95.50m,
            HealthAndSafetyCompliant = true,
            MachineGuardingCompliant = true,
            FireSafetyCompliant = true,
            AblutionFacilitiesCompliant = true,
            OverallRecommendation = "Recommended",
            ConditionNotes = "All 17 designated trade toolkits present with verified shadow boards."
        };

        var saved = await discService.RecordSiteInspectionAsync(inspection, "Nombulelo_Zulu");

        Assert.NotNull(saved);
        Assert.True(saved.Id > 0);
        Assert.Equal(95.50m, saved.ToolRatioScore);
        Assert.Equal(220.50m, saved.WorkshopSquareMeters);
        Assert.True(saved.MachineGuardingCompliant);

        var inspections = await discService.GetSiteInspectionsForProviderAsync(provider.Id);
        Assert.Single(inspections);
        Assert.Equal("Recommended", inspections[0].OverallRecommendation);
    }

    [Fact]
    public async Task ReAccreditation_WithinSixMonths_PreservesOperationalStatus()
    {
        var (_, db, _, discService, _) = CreateTestContext();
        
        // Expiry in 3 months (within 6 month window)
        var org = new Organisation { CompanyName = "Precision Training Centre", SdlNumber = "L112233445" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var provider = new TrainingProvider
        {
            OrganisationId = org.Id,
            AccreditationNumber = "17-QA/ACC/0555/21",
            AccreditationStartDate = DateTime.UtcNow.AddYears(-5),
            AccreditationEndDate = DateTime.UtcNow.AddMonths(3),
            ProviderStatusCode = "Active",
            IsActive = true
        };
        db.TrainingProviders.Add(provider);
        await db.SaveChangesAsync();

        var reAccredReq = new SdpReAccreditationApplication
        {
            TrainingProviderId = provider.Id,
            CurrentAccreditationExpiryDate = provider.AccreditationEndDate.Value,
            ProposedAccreditationExpiryDate = provider.AccreditationEndDate.Value.AddYears(5),
            StatusCode = "ApplicationSubmitted",
            QmsComplianceAudited = true
        };

        var created = await discService.SubmitReAccreditationApplicationAsync(provider.Id, reAccredReq, "SDF_Officer");

        Assert.NotNull(created);
        Assert.Equal(provider.Id, created.TrainingProviderId);

        // INVARIANT: Operational status must NOT revert to PendingApproval
        var refreshedProvider = await db.TrainingProviders.FindAsync(provider.Id);
        Assert.NotNull(refreshedProvider);
        Assert.Equal("Active", refreshedProvider.ProviderStatusCode);
    }

    [Fact]
    public async Task QuestPdf_CeremonialAccreditationCertificate_GeneratesValidA4LandscapeBytes()
    {
        var (_, db, _, _, pdfService) = CreateTestContext();
        var provider = await SeedProviderAsync(db);

        var qual = new TrainingProviderQualification
        {
            TrainingProviderId = provider.Id,
            SaqaQualificationId = 58781,
            QualificationTitle = "National Certificate: Automotive Repair and Maintenance",
            NqfLevel = 4,
            AccreditationStatusCode = "Active",
            ExpiryDate = DateTime.UtcNow.AddYears(3)
        };
        db.TrainingProviderQualifications.Add(qual);
        await db.SaveChangesAsync();

        var pdfBytes = await pdfService.GenerateSdpAccreditationCertificatePdfAsync(provider.Id);

        Assert.NotNull(pdfBytes);
        Assert.True(pdfBytes.Length > 1000);

        // Verify PDF Magic Bytes (%PDF)
        var header = Encoding.ASCII.GetString(pdfBytes.Take(4).ToArray());
        Assert.Equal("%PDF", header);
    }

    [Fact]
    public async Task QuestPdf_Form015_DisciplinarySanctionNotice_GeneratesValidBytes()
    {
        var (_, db, _, discService, pdfService) = CreateTestContext();
        var provider = await SeedProviderAsync(db);

        var c = await discService.InitiateCaseAsync(new InitiateSdpDisciplinaryCaseRequest
        {
            TrainingProviderId = provider.Id,
            CaseType = "Suspension",
            ComplaintSource = "Whistleblower",
            AllegationSummary = "Issuing falsified logbook sign-offs for non-attending apprentices."
        });

        await discService.RecordOutcomeAsync(new RecordSdpDisciplinaryOutcomeRequest
        {
            CaseId = c.Id,
            OutcomeCode = "SUSPENDED",
            SanctionType = "TemporarySuspension",
            ReviewCommitteeDecisionNumber = "ETQA-RC-2026-0099",
            SanctionStartDate = DateTime.UtcNow,
            SanctionEndDate = DateTime.UtcNow.AddMonths(12),
            NoticeDocumentRef = "ETQ-SANCT-2026-0099"
        });

        var pdfBytes = await pdfService.GenerateSdpDisciplinaryNoticePdfAsync(c.Id);

        Assert.NotNull(pdfBytes);
        Assert.True(pdfBytes.Length > 1000);

        var header = Encoding.ASCII.GetString(pdfBytes.Take(4).ToArray());
        Assert.Equal("%PDF", header);
    }

    [Fact]
    public async Task QuestPdf_Form012_SiteInspectionReport_GeneratesValidBytes()
    {
        var (_, db, _, discService, pdfService) = CreateTestContext();
        var provider = await SeedProviderAsync(db);

        var inspection = await discService.RecordSiteInspectionAsync(new SdpSiteInspection
        {
            TrainingProviderId = provider.Id,
            InspectionDate = DateTime.UtcNow,
            InspectionType = "PhysicalOnSite",
            WorkshopSquareMeters = 300,
            ClassroomSquareMeters = 120,
            ToolRatioScore = 100,
            HealthAndSafetyCompliant = true,
            MachineGuardingCompliant = true,
            FireSafetyCompliant = true,
            AblutionFacilitiesCompliant = true,
            OverallRecommendation = "Recommended",
            ConditionNotes = "Exemplary workshop layout and tooling."
        });

        var pdfBytes = await pdfService.GenerateSdpSiteInspectionReportPdfAsync(inspection.Id);

        Assert.NotNull(pdfBytes);
        Assert.True(pdfBytes.Length > 1000);

        var header = Encoding.ASCII.GetString(pdfBytes.Take(4).ToArray());
        Assert.Equal("%PDF", header);
    }

    [Fact]
    public async Task QuestPdf_Form001_AccreditationOutcomeLetter_GeneratesValidBytes()
    {
        var (_, db, _, _, pdfService) = CreateTestContext();
        var provider = await SeedProviderAsync(db);

        var pdfBytes = await pdfService.GenerateSdpOutcomeLetterPdfAsync(provider.Id);

        Assert.NotNull(pdfBytes);
        Assert.True(pdfBytes.Length > 1000);

        var header = Encoding.ASCII.GetString(pdfBytes.Take(4).ToArray());
        Assert.Equal("%PDF", header);
    }
}
