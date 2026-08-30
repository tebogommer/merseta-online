using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class WorkplaceMonitoringTests
{
    private static async Task<(TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, WorkplaceMonitoringService service, int orgId, int contactId)> SetupTestEnvironmentAsync()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var service = new WorkplaceMonitoringService(factory, audit);

        var org = new Organisation
        {
            CompanyName = "Apex Precision Engineering Pty Ltd",
            SdlNumber = "L102938475",
            RegistrationNumber = "2018/123456/07",
            SicCode = "35600"
        };
        db.Organisations.Add(org);

        var person = new Person
        {
            FirstName = "Sipho",
            LastName = "Dlamini",
            RsaIdNumber = "8502155123088",
            Email = "sipho.dlamini@apexeng.co.za",
            GenderCode = "M"
        };
        db.People.Add(person);
        await db.SaveChangesAsync();

        return (factory, db, audit, service, org.Id, person.Id);
    }

    [Fact]
    public async Task CreateSiteVisit_WithoutContactPerson_ThrowsArgumentException()
    {
        var (_, _, _, service, orgId, _) = await SetupTestEnvironmentAsync();

        var visit = new WorkplaceMonitoringSiteVisit
        {
            OrganisationId = orgId,
            ContactPersonId = 0, // Invalid - violates employer visit contact rule
            MonitoringDate = DateTime.UtcNow
        };

        var ex = await Assert.ThrowsAsync<ArgumentException>(() => service.CreateSiteVisitAsync(visit, "AssessorUser"));
        Assert.Contains("Contact Person", ex.Message);
    }

    [Fact]
    public async Task CreateSiteVisit_Valid_AutoGenerates10PointComplianceChecklist_AndAudits()
    {
        var (factory, _, _, service, orgId, contactId) = await SetupTestEnvironmentAsync();

        var visit = new WorkplaceMonitoringSiteVisit
        {
            OrganisationId = orgId,
            ContactPersonId = contactId,
            MonitoringDate = DateTime.UtcNow,
            CloUserId = "CLO-Gauteng-01"
        };

        var created = await service.CreateSiteVisitAsync(visit, "AssessorUser");
        Assert.True(created.Id > 0);
        Assert.Equal("Draft", created.StatusCode);
        Assert.Equal(10, created.ComplianceSurveys.Count);

        using var verifyDb = (NsdmsDbContext)factory.CreateDbContext();
        var auditLog = await verifyDb.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "WorkplaceMonitoringSiteVisit" && a.RecordId == created.Id && a.ActionName == "CreateSiteVisit");
        Assert.NotNull(auditLog);
        Assert.Equal("AssessorUser", auditLog.Actor);
    }

    [Fact]
    public async Task SubmitAndApproveSiteVisit_TransitionsStatus_AndRecordsAudit()
    {
        var (factory, _, _, service, orgId, contactId) = await SetupTestEnvironmentAsync();

        var visit = new WorkplaceMonitoringSiteVisit
        {
            OrganisationId = orgId,
            ContactPersonId = contactId,
            MonitoringDate = DateTime.UtcNow
        };
        var created = await service.CreateSiteVisitAsync(visit, "AssessorUser");

        var submitted = await service.SubmitForApprovalAsync(created.Id, "AssessorUser");
        Assert.Equal("PendingApproval", submitted.StatusCode);

        var approved = await service.ApproveSiteVisitAsync(created.Id, "Fully compliant and verified", "ManagerUser");
        Assert.Equal("Approved", approved.StatusCode);
        Assert.True(approved.SignOffState);
        Assert.NotNull(approved.ApprovalDate);
        Assert.Equal("ManagerUser", approved.ApprovedByUserId);

        using var verifyDb = (NsdmsDbContext)factory.CreateDbContext();
        var approveAudit = await verifyDb.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "WorkplaceMonitoringSiteVisit" && a.RecordId == created.Id && a.ActionName == "ApproveSiteVisit");
        Assert.NotNull(approveAudit);
        Assert.Equal("ManagerUser", approveAudit.Actor);
    }

    [Fact]
    public async Task FlagNonCompliance_MovesToHoldingArea_AndUpdatesStatus()
    {
        var (factory, _, _, service, orgId, contactId) = await SetupTestEnvironmentAsync();

        var visit = new WorkplaceMonitoringSiteVisit
        {
            OrganisationId = orgId,
            ContactPersonId = contactId,
            MonitoringDate = DateTime.UtcNow
        };
        var created = await service.CreateSiteVisitAsync(visit, "AssessorUser");

        var flagged = await service.FlagNonComplianceAsync(created.Id, "Safety equipment lacking and logbooks missing", "AssessorUser");
        Assert.Equal("NonComplianceIdentified", flagged.StatusCode);
        Assert.True(flagged.NonCompliancesIdentified);
        Assert.True(flagged.NonComplianceHoldingArea);
        Assert.NotNull(flagged.NonComplianceSubmittedDate);

        using var verifyDb = (NsdmsDbContext)factory.CreateDbContext();
        var audit = await verifyDb.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "WorkplaceMonitoringSiteVisit" && a.RecordId == created.Id && a.ActionName == "FlagNonCompliance");
        Assert.NotNull(audit);
        Assert.Equal("AssessorUser", audit.Actor);
    }

    [Fact]
    public async Task SaveComplianceSurveys_WithDefect_AutomaticallyFlagsNonCompliance()
    {
        var (factory, _, _, service, orgId, contactId) = await SetupTestEnvironmentAsync();

        var visit = new WorkplaceMonitoringSiteVisit
        {
            OrganisationId = orgId,
            ContactPersonId = contactId,
            MonitoringDate = DateTime.UtcNow
        };
        var created = await service.CreateSiteVisitAsync(visit, "AssessorUser");

        var surveys = created.ComplianceSurveys.ToList();
        surveys[3].Answer = "No"; // Question 4: PPE not supplied
        surveys[3].Comments = "Learners purchasing own welding helmets";

        var savedSurveys = await service.SaveComplianceSurveysAsync(created.Id, surveys, "AssessorUser");
        Assert.True(savedSurveys[3].NonComplianceRisk);

        var reloaded = await service.GetSiteVisitByIdAsync(created.Id);
        Assert.NotNull(reloaded);
        Assert.True(reloaded.NonCompliancesIdentified);
    }

    [Fact]
    public async Task ActionPlansAndMitigationPlans_Lifecycle_AddAndResolve()
    {
        var (factory, _, _, service, orgId, contactId) = await SetupTestEnvironmentAsync();

        var visit = new WorkplaceMonitoringSiteVisit
        {
            OrganisationId = orgId,
            ContactPersonId = contactId,
            MonitoringDate = DateTime.UtcNow
        };
        var created = await service.CreateSiteVisitAsync(visit, "AssessorUser");

        var actionPlan = new WorkplaceMonitoringActionPlan
        {
            WorkplaceMonitoringSiteVisitId = created.Id,
            ValidationTypeCode = "HealthAndSafety",
            Criteria = "PPE Provision",
            ActionRequired = "Supply full SABS approved welding gear",
            ResponsiblePersonName = "Sipho Dlamini",
            TargetCompletionDate = DateTime.UtcNow.AddDays(14)
        };
        var addedAction = await service.AddActionPlanAsync(actionPlan, "AssessorUser");
        Assert.Equal("Open", addedAction.StatusCode);

        var resolvedAction = await service.ResolveActionPlanAsync(addedAction.Id, "Invoices and photo evidence verified", "AssessorUser");
        Assert.Equal("Completed", resolvedAction.StatusCode);
        Assert.NotNull(resolvedAction.ResolutionDate);

        var mitigation = new WorkplaceMonitoringMitigationPlan
        {
            WorkplaceMonitoringSiteVisitId = created.Id,
            WorkplaceMonitoringActionPlanId = addedAction.Id,
            IdentifiedRisk = "Safety non-compliance penalty",
            MitigationSteps = "Interim PPE rental while stock arrives",
            TargetResolutionDate = DateTime.UtcNow.AddDays(7)
        };
        var addedMitigation = await service.AddMitigationPlanAsync(mitigation, "AssessorUser");
        Assert.Equal("Pending", addedMitigation.StatusCode);

        var learnerSurvey = new WorkplaceMonitoringLearnerSurvey
        {
            WorkplaceMonitoringSiteVisitId = created.Id,
            LearnerName = "Thabo Mokoena",
            ReceivedToolbox = true,
            ExposedToFullCurriculum = true,
            SatisfiedWithTraining = true,
            HasQualifiedMentor = true,
            ReceivesStipendWage = true,
            HasRequiredPPE = true,
            LearnerComments = "Great mentorship experience."
        };
        var addedSurvey = await service.AddLearnerSurveyAsync(learnerSurvey, "AssessorUser");
        Assert.True(addedSurvey.Id > 0);

        var fullVisit = await service.GetSiteVisitByIdAsync(created.Id);
        Assert.NotNull(fullVisit);
        Assert.Single(fullVisit.ActionPlans);
        Assert.Single(fullVisit.MitigationPlans);
        Assert.Single(fullVisit.LearnerSurveys);
    }
}