using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class AuxiliaryEnterpriseServicesTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit) CreateContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        return (factory, db, audit);
    }

    [Fact]
    public async Task BankingDetails_DualSignoff_And_ErpActivation()
    {
        var (factory, db, audit) = CreateContext();
        var bankingService = new BankingDetailsService(factory, audit);

        var org = new Organisation { CompanyName = "Bell Equipment Engineering Ltd", SdlNumber = "L998877665" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        // 1. Submit Bank Details
        var details = await bankingService.SubmitBankingDetailsAsync(
            org.Id,
            null,
            "Standard Bank South Africa",
            "051001",
            "Richards Bay Commercial",
            "0123456789",
            "Bell Equipment Ltd",
            "Current",
            "/docs/bank_conf_bell.pdf",
            DateTime.UtcNow,
            "EmployerAdmin");

        Assert.NotNull(details);
        Assert.Equal("PendingVerification", details.ApprovalStatusCode);
        Assert.False(details.IsErpActive);

        // 2. First Signoff (Verification Officer)
        var firstApproved = await bankingService.FirstSignoffAsync(details.Id, true, "Bank confirmation letter and cancelled cheque confirmed.", "VerificationOfficer");
        Assert.Equal("FirstSignoffApproved", firstApproved.ApprovalStatusCode);
        Assert.NotNull(firstApproved.FirstSignoffDate);
        Assert.False(firstApproved.IsErpActive);

        // 3. Second Signoff (Finance Manager) -> Activates ERP
        var fullyApproved = await bankingService.SecondSignoffAndActivateErpAsync(details.Id, true, "Dual signoff complete. ERP vendor linked.", "FinanceManager");
        Assert.Equal("FullyApproved", fullyApproved.ApprovalStatusCode);
        Assert.True(fullyApproved.IsErpActive);
        Assert.NotNull(fullyApproved.ErpVendorId);
        Assert.NotNull(fullyApproved.ErpSyncDate);

        // Verify Double-Write Audit Trail
        var auditLogs = await db.AuditLogs.Where(a => a.EntityName == "BankingDetails" && a.RecordId == details.Id).ToListAsync();
        Assert.NotEmpty(auditLogs);
    }

    [Fact]
    public async Task SdfAppointment_Submission_Approval_And_Termination()
    {
        var (factory, db, audit) = CreateContext();
        var sdfService = new SdfAppointmentService(factory, audit);

        var org = new Organisation { CompanyName = "Toyota Manufacturing SA", SdlNumber = "L112233445" };
        var person = new Person { FirstName = "Nolwazi", LastName = "Dlamini", RsaIdNumber = "8805125829088", Email = "nolwazi.dlamini@toyota.co.za" };
        db.Organisations.Add(org);
        db.People.Add(person);
        await db.SaveChangesAsync();

        // 1. Submit SDF Appointment
        var appt = await sdfService.SubmitSdfAppointmentAsync(
            org.Id,
            person.Id,
            "Primary",
            DateTime.UtcNow,
            "/docs/sdf_appointment_toyota.pdf",
            true,
            true,
            "EmployerHR");

        Assert.NotNull(appt);
        Assert.Equal("PendingApproval", appt.SdfStatusCode);
        Assert.True(appt.AllowWspSubmission);

        // 2. Approve SDF Appointment
        var approved = await sdfService.ApproveSdfAppointmentAsync(appt.Id, "Appointment letter and company resolution verified.", "ClientRelationsOfficer");
        Assert.Equal("Approved", approved.SdfStatusCode);
        Assert.NotNull(approved.ApprovalDate);

        // 3. Terminate SDF Appointment
        var terminated = await sdfService.TerminateSdfAppointmentAsync(appt.Id, "SDF left the employment of Toyota SA.", "ClientRelationsOfficer");
        Assert.Equal("Terminated", terminated.SdfStatusCode);
        Assert.NotNull(terminated.AppointmentEndDate);

        // Check history trail
        var history = await db.SdfAppointmentHistories.Where(h => h.SdfCompanyId == appt.Id).ToListAsync();
        Assert.Equal(3, history.Count);
    }

    [Fact]
    public async Task ContractVariation_AddendaCreation_And_ExecutiveApproval()
    {
        var (factory, db, audit) = CreateContext();
        var variationService = new ContractVariationService(factory, audit);

        var moa = new GrantMoa
        {
            MoaNumber = "MOA-2026-DG-0044",
            TotalContractValue = 1000000m,
            ContractStartDate = new DateTime(2026, 4, 1),
            ContractEndDate = new DateTime(2027, 3, 31),
            MoaStatusCode = "SignedActive"
        };
        db.GrantMoas.Add(moa);
        await db.SaveChangesAsync();

        // 1. Create Addenda (Timeline Extension + Scope Budget Variance)
        var addenda = await variationService.CreateAddendaAsync(
            moa.Id,
            "TimelineExtension",
            1200000m,
            new DateTime(2027, 9, 30),
            "Additional 20 apprentices enrolled under merSETA renewable energy window.",
            "ProjectManager");

        Assert.NotNull(addenda);
        Assert.StartsWith("ADD-", addenda.AddendaNumber);
        Assert.Equal(200000m, addenda.ContractValueVariance);
        Assert.Equal("SubmittedForReview", addenda.StatusCode);

        // 2. Executive Approval
        var approved = await variationService.ApproveAddendaAsync(addenda.Id, "ChiefOperationsOfficer");
        Assert.Equal("ExecutiveApproved", approved.StatusCode);

        // Verify parent MOA values were updated
        using var assertDb = factory.CreateDbContext();
        var updatedMoa = await assertDb.GrantMoas.FindAsync(moa.Id);
        Assert.Equal(1200000m, updatedMoa!.TotalContractValue);
        Assert.Equal(new DateTime(2027, 9, 30), updatedMoa.ContractEndDate);
    }

    [Fact]
    public async Task SdpScopeExtension_And_ReAccreditation()
    {
        var (factory, db, audit) = CreateContext();
        var scopeService = new ExtensionOfScopeService(factory, audit);

        var provider = new TrainingProvider
        {
            ProviderName = "Coastal TVET College - Swinton Campus",
            AccreditationNumber = "ACC/2026/08/001"
        };
        db.TrainingProviders.Add(provider);
        await db.SaveChangesAsync();

        // 1. Submit Scope Extension
        var eos = await scopeService.SubmitSdpScopeExtensionAsync(
            provider.Id,
            "Occupational Certificate: Electrician (Mining Specialisation)",
            "SAQA-91761",
            4,
            360,
            "OccupationalCertificate",
            "CollegePrincipal");

        Assert.NotNull(eos);
        Assert.StartsWith("SDP-EOS-", eos.ApplicationNumber);
        Assert.Equal("Submitted", eos.StatusCode);

        // 2. Record Site Inspection Passed
        var inspected = await scopeService.RecordSiteInspectionAsync(eos.Id, true, "EtqaInspector");
        Assert.True(inspected.SiteInspectionPassed);
        Assert.Equal("CommitteeReview", inspected.StatusCode);

        // 3. Approve Scope Extension
        var approved = await scopeService.ApproveSdpScopeExtensionAsync(eos.Id, "ETQA-COM-2026-09-001", "EtqaManager");
        Assert.Equal("Approved", approved.StatusCode);
        Assert.Equal("ETQA-COM-2026-09-001", approved.CommitteeDecisionReference);

        // 4. Submit & Endorse 5-Year Re-Accreditation
        var reacc = await scopeService.SubmitReAccreditationAsync(
            provider.Id,
            DateTime.UtcNow.AddMonths(1),
            DateTime.UtcNow.AddYears(5),
            "CollegePrincipal");

        var endorsed = await scopeService.EndorseReAccreditationAsync(reacc.Id, "COUNCIL-DEC-2026-554", "EtqaManager");
        Assert.Equal("ReAccredited", endorsed.StatusCode);
        Assert.Equal("COUNCIL-DEC-2026-554", endorsed.CouncilDecisionNumber);
    }
}
