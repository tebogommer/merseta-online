using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class QcdAndNonSetaAndLevyReconTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit) CreateContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        return (factory, db, audit);
    }

    [Fact]
    public async Task Qcd_CompleteLifecycle_FromScopingToSaqaApproval()
    {
        var (factory, db, audit) = CreateContext();
        var qcdService = new QcdAndCurriculumService(factory, audit);

        // 1. Create Application
        var app = await qcdService.CreateQcdApplicationAsync(
            "Occupational Certificate: Electric Vehicle High-Voltage Technician",
            "653306",
            5,
            180,
            "NewDevelopment",
            "To qualify specialist diagnostic technicians for hybrid and electric vehicle battery powertrains.",
            "Crucial transition for the South African automotive manufacturing sector (APDP2).",
            null,
            "QdfCurriculumLead");

        Assert.NotNull(app);
        Assert.StartsWith("QCD-", app.ApplicationNumber);
        Assert.Equal("Draft", app.StatusCode);
        Assert.True(app.NationalDevelopmentPlanChecked);
        Assert.True(app.NewGrowthPlanChecked);

        // 2. Add Working Group Members
        var withMember = await qcdService.AddWorkingGroupMemberAsync(
            app.Id,
            "Prof. Willem Van Der Merwe",
            "AcademicSpecialist",
            "Tshwane University of Technology",
            "w.merwe@tut.ac.za",
            "0123825000",
            "QdfCurriculumLead");

        Assert.Single(withMember.WorkingGroupMembers);

        // 3. Add Skills Programme Part-Qualification
        var withSkills = await qcdService.AddSkillsRegistrationAsync(
            app.Id,
            "SP-EV-BATTERY-01",
            "High-Voltage Inverter & DC-DC Converter Fault Diagnostics",
            40,
            5,
            "[\"US-9844\", \"US-9845\"]",
            "QdfCurriculumLead");

        Assert.Single(withSkills.SkillsRegistrations);

        // 4. Convene Working Group and Open Public Comment
        var publicComment = await qcdService.ConveneWorkingGroupAndOpenPublicCommentAsync(
            app.Id,
            DateTime.UtcNow.AddDays(30),
            "QdfCurriculumLead");

        Assert.Equal("PublicCommentOpen", publicComment.StatusCode);
        Assert.NotNull(publicComment.PublicCommentClosingDate);

        // 5. Submit to QCTO
        var submitted = await qcdService.SubmitToQctoForSaqaRegistrationAsync(app.Id, "QdfCurriculumLead");
        Assert.Equal("SubmittedToQcto", submitted.StatusCode);
        Assert.NotNull(submitted.SaqaSubmissionDate);

        // 6. Record SAQA Approval
        var approved = await qcdService.RecordSaqaRegistrationApprovalAsync(
            app.Id,
            "SAQA-QUAL-119842",
            DateTime.UtcNow,
            "ExecutiveManager");

        Assert.Equal("ApprovedBySaqa", approved.StatusCode);
        Assert.Equal("SAQA-QUAL-119842", approved.SaqaRegistrationNumber);

        // Verify Double-Write Audit Trail
        var auditLogs = await db.AuditLogs.Where(a => a.EntityName == "QualificationsCurriculumDevelopment" && a.RecordId == app.Id).ToListAsync();
        Assert.NotEmpty(auditLogs);
        Assert.Contains(auditLogs, a => a.ActionName == "RecordSaqaRegistrationApproval");
    }

    [Fact]
    public async Task NonSeta_CompanyRegistration_And_QualificationVerificationEndorsement()
    {
        var (factory, db, audit) = CreateContext();
        var nonSetaService = new NonSetaVerificationService(factory, audit);

        // Seed Person
        var person = new Person
        {
            FirstName = "Bongani",
            LastName = "Nkosi",
            RsaIdNumber = "9208155829082",
            Email = "bongani.nkosi@example.com"
        };
        db.People.Add(person);
        await db.SaveChangesAsync();

        // 1. Register Non-SETA Institution
        var nonSetaCo = await nonSetaService.RegisterNonSetaCompanyAsync(
            "Sasol Chemical Skills Academy",
            "CHIETA",
            "L554433221",
            "2002/098765/07",
            "skills@sasol.com",
            "0117894561",
            "Secunda Industrial Park",
            "CoordinatorUser");

        Assert.NotNull(nonSetaCo);
        Assert.Equal("CHIETA", nonSetaCo.PrimarySetaCode);

        // 2. Submit External Qualification for Verification
        var verif = await nonSetaService.SubmitNonSetaQualificationForVerificationAsync(
            person.Id,
            "CHIETA",
            "National Certificate: Chemical Manufacturing & Polymer Processing",
            "SAQA-58801",
            4,
            140,
            "CHIETA-CERT-2024-88412",
            nonSetaCo.Id,
            null,
            "CandidateApplicant");

        Assert.NotNull(verif);
        Assert.Equal("PendingVerification", verif.VerificationStatusCode);

        // 3. Endorse for merSETA Articulation
        var endorsed = await nonSetaService.EndorseNonSetaQualificationAsync(
            verif.Id,
            true,
            "Verified against NLRD database and CHIETA certificate repository.",
            "EtqaManager");

        Assert.Equal("EndorsedByMerSeta", endorsed.VerificationStatusCode);
        Assert.NotNull(endorsed.EndorsementDate);
        Assert.Equal("EtqaManager", endorsed.EndorsedByUserId);

        // Verify Double-Write Audit Trail
        var auditLogs = await db.AuditLogs.Where(a => a.EntityName == "NonSetaQualificationsCompletion" && a.RecordId == verif.Id).ToListAsync();
        Assert.NotEmpty(auditLogs);
        Assert.Contains(auditLogs, a => a.ActionName == "EndorseNonSetaQualification");
    }

    [Fact]
    public async Task SarsLevyRecon_VarianceDetection_ClawbackIssuing_And_Settlement()
    {
        var (factory, db, audit) = CreateContext();
        var reconService = new SarsLevyReconAuditService(factory, audit);

        // 1. Exact Match Audit
        var exact = await reconService.PerformSarsLevyAuditAsync(
            "2026",
            "L111111111",
            100000m,
            100000m,
            null,
            "Zero variance verified.",
            "FinanceAuditor");

        Assert.Equal("Reconciled", exact.AuditStatusCode);
        Assert.Equal("ExactMatch", exact.DiscrepancyReasonCode);
        Assert.False(exact.ClawbackActionRequired);

        // 2. Overpayment Discrepancy (Triggers Clawback Requirement)
        var overpayment = await reconService.PerformSarsLevyAuditAsync(
            "2026",
            "L222222222",
            150000m,
            100000m,
            null,
            "Overpayment of R50,000 detected from revised SARS schedule.",
            "FinanceAuditor");

        Assert.Equal("DiscrepancyFlagged", overpayment.AuditStatusCode);
        Assert.Equal("Overpayment", overpayment.DiscrepancyReasonCode);
        Assert.True(overpayment.ClawbackActionRequired);
        Assert.Equal(50000m, overpayment.ClawbackAmount);

        // 3. Issue Clawback Notice
        var clawbackIssued = await reconService.IssueClawbackNoticeAsync(
            overpayment.Id,
            50000m,
            "FinanceAuditor");

        Assert.Equal("ClawbackIssued", clawbackIssued.AuditStatusCode);
        Assert.NotNull(clawbackIssued.ClawbackIssuedDate);

        // 4. Settle Clawback
        var settled = await reconService.SettleClawbackAsync(
            overpayment.Id,
            "FinanceAuditor");

        Assert.Equal("Resolved", settled.AuditStatusCode);
        Assert.NotNull(settled.ClawbackSettledDate);

        // Verify Double-Write Audit Trail
        var auditLogs = await db.AuditLogs.Where(a => a.EntityName == "SarsLevyReconAudit" && a.RecordId == overpayment.Id).ToListAsync();
        Assert.NotEmpty(auditLogs);
        Assert.Contains(auditLogs, a => a.ActionName == "SettleClawback");
    }
}
