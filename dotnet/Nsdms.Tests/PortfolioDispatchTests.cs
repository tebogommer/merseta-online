using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

/// <summary>
/// Unit and integration tests for Option B: Unified Dynamic Portfolio & Capability Dispatch Engine.
/// Verifies:
/// 1. Cross-Regional direct employer allocation regardless of geographical base.
/// 2. Temporal territory demarcation resolution supporting municipal boundary shifts.
/// 3. Predecessor-to-successor handoff with atomic open task reassignment and SHA-256 digital security seal.
/// 4. Preservation of historical provenance (past visits/audits retain predecessor attribution).
/// 5. Capability-based role-neutral dispatching by CLC coordinators.
/// </summary>
public class PortfolioDispatchTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, PortfolioDispatchService service) CreateContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var service = new PortfolioDispatchService(factory, audit);
        return (factory, db, audit, service);
    }

    [Fact]
    public async Task AssignOrganisationOfficer_CrossRegionalDirectAllocation_Succeeds()
    {
        var (factory, db, audit, service) = CreateContext();

        var org = new Organisation
        {
            CompanyName = "Toyota SA Motors - Durban Prospecton Plant",
            SdlNumber = "L102938475",
            ProvinceCode = "KZN",
            PhysicalAddress = "Prospecton Road, Durban",
            IsActive = true
        };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        // Assign Gauteng-based Key Account Manager directly to KZN organisation
        var portfolio = await service.AssignOrganisationOfficerAsync(
            organisationId: org.Id,
            officerUserId: "OFFICER_GAU_01",
            officerName: "Bongani Sithole",
            officerEmail: "bongani.sithole@merseta.org.za",
            roleCode: "KEY_ACCOUNT_MANAGER",
            managingRegionCode: "GAUTENG_SOUTH",
            isCrossRegional: true,
            reason: "National OEM Strategic Account Portfolio",
            currentUsername: "CRM_MANAGER"
        );

        Assert.NotNull(portfolio);
        Assert.Equal(org.Id, portfolio.OrganisationId);
        Assert.True(portfolio.IsCrossRegionalAssignment);
        Assert.Equal("KEY_ACCOUNT_MANAGER", portfolio.PortfolioRoleCode);
        Assert.Equal("GAUTENG_SOUTH", portfolio.ManagingRegionCode);
        Assert.True(portfolio.IsActive);

        // Verify active query
        var active = await service.GetActivePortfolioAsync(org.Id);
        Assert.NotNull(active);
        Assert.Equal("OFFICER_GAU_01", active.RelationshipOfficerUserId);
    }

    [Fact]
    public async Task ResolveTerritoryForTown_TemporalDemarcation_ReturnsCorrectJurisdictionForDate()
    {
        var (factory, db, audit, service) = CreateContext();

        // Historical mapping: Midrand mapped to GAUTENG_NORTH until 2024-12-31
        var demarcationHistoric = new TerritoryDemarcation
        {
            TownName = "Midrand",
            RegionCode = "GAUTENG_NORTH",
            RegionName = "Gauteng North Regional Office",
            ProvinceCode = "GP",
            EffectiveFrom = new DateTime(2020, 1, 1, 0, 0, 0, DateTimeKind.Utc),
            EffectiveTo = new DateTime(2024, 12, 31, 23, 59, 59, DateTimeKind.Utc),
            BoundaryGazetteReference = "Gazette 41002/2019",
            IsActive = true
        };

        // Modern mapping: Midrand mapped to GAUTENG_SOUTH from 2025-01-01
        var demarcationModern = new TerritoryDemarcation
        {
            TownName = "Midrand",
            RegionCode = "GAUTENG_SOUTH",
            RegionName = "Gauteng South Regional Office",
            ProvinceCode = "GP",
            EffectiveFrom = new DateTime(2025, 1, 1, 0, 0, 0, DateTimeKind.Utc),
            EffectiveTo = null,
            BoundaryGazetteReference = "Gazette 49800/2024",
            IsActive = true
        };

        db.TerritoryDemarcations.AddRange(demarcationHistoric, demarcationModern);
        await db.SaveChangesAsync();

        // As of 2023 -> Should resolve GAUTENG_NORTH
        var resolvedPast = await service.ResolveTerritoryForTownAsync("Midrand", new DateTime(2023, 6, 15));
        Assert.NotNull(resolvedPast);
        Assert.Equal("GAUTENG_NORTH", resolvedPast.RegionCode);

        // As of 2026 -> Should resolve GAUTENG_SOUTH
        var resolvedPresent = await service.ResolveTerritoryForTownAsync("Midrand", new DateTime(2026, 3, 1));
        Assert.NotNull(resolvedPresent);
        Assert.Equal("GAUTENG_SOUTH", resolvedPresent.RegionCode);
    }

    [Fact]
    public async Task SuccessorHandoff_ReassignsOpenTasks_PreservesHistoricalProvenance_AndGeneratesSecuritySeal()
    {
        var (factory, db, audit, service) = CreateContext();

        var org = new Organisation
        {
            CompanyName = "Bell Equipment Richards Bay",
            SdlNumber = "L556677889",
            ProvinceCode = "KZN",
            IsActive = true
        };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        // Initial portfolio assignment to predecessor
        await service.AssignOrganisationOfficerAsync(
            organisationId: org.Id,
            officerUserId: "CLO_PREDECESSOR",
            officerName: "Predecessor Officer",
            officerEmail: "predecessor@merseta.org.za",
            roleCode: "PRIMARY_CLO",
            managingRegionCode: "KZN",
            isCrossRegional: false,
            reason: "Territory Assignment",
            currentUsername: "CRM_KZN"
        );

        // Seed an open workflow task assigned to predecessor
        var wfInstance = new WorkflowInstance
        {
            WorkflowDefinitionId = 1,
            EntityId = org.Id,
            EntityTitle = org.CompanyName,
            EntityReferenceNumber = "WPA-2026-001",
            InitiatorUserId = "SDF_USER"
        };
        db.WorkflowInstances.Add(wfInstance);
        await db.SaveChangesAsync();

        var task = new WorkflowTask
        {
            WorkflowInstanceId = wfInstance.Id,
            WorkflowInstance = wfInstance,
            TaskTitle = "Verify Workshop Ratios",
            TaskDescription = "Inspect artisan mentor ratios",
            AssignedUserId = "CLO_PREDECESSOR",
            TaskStatus = "Open"
        };
        db.WorkflowTasks.Add(task);

        // Seed a historic completed visit by predecessor to verify provenance preservation
        var completedVisit = new Visit
        {
            OrganisationId = org.Id,
            ContactPersonId = 99,
            Title = "Workplace Inspection",
            VisitDate = DateTime.UtcNow.AddMonths(-3),
            VisitTypeCode = "WORKPLACE_APPROVAL",
            VisitStatusCode = "Completed",
            CreatedBy = "CLO_PREDECESSOR"
        };
        db.Visits.Add(completedVisit);
        await db.SaveChangesAsync();

        // Execute Successor Handoff
        var handoffRequest = new SuccessorHandoffRequestDto(
            FromOfficerUserId: "CLO_PREDECESSOR",
            FromOfficerName: "Predecessor Officer",
            ToOfficerUserId: "CLO_SUCCESSOR",
            ToOfficerName: "Successor Officer",
            OrganisationIds: new List<int> { org.Id },
            Reason: "Predecessor transferred to National Office",
            ReassignOpenTasks: true
        );

        var result = await service.ExecuteSuccessorHandoffAsync(handoffRequest, "CRM_KZN");

        Assert.True(result.Success);
        Assert.Equal(1, result.TransferredOrganisationsCount);
        Assert.Equal(1, result.ReassignedTasksCount);
        Assert.False(string.IsNullOrWhiteSpace(result.SecuritySealHash));

        // 1. Verify Active Portfolio updated to successor
        var currentPortfolio = await service.GetActivePortfolioAsync(org.Id);
        Assert.NotNull(currentPortfolio);
        Assert.Equal("CLO_SUCCESSOR", currentPortfolio.RelationshipOfficerUserId);
        Assert.Equal("Successor Officer", currentPortfolio.RelationshipOfficerName);

        // 2. Verify Open Task reassigned to successor
        var updatedTask = await db.WorkflowTasks.AsNoTracking().FirstOrDefaultAsync(t => t.Id == task.Id);
        Assert.NotNull(updatedTask);
        Assert.Equal("CLO_SUCCESSOR", updatedTask.AssignedUserId);

        // 3. Verify Historical Completed Visit provenance is untouched
        var historicalVisit = await db.Visits.AsNoTracking().FirstOrDefaultAsync(v => v.Id == completedVisit.Id);
        Assert.NotNull(historicalVisit);
        Assert.Equal("CLO_PREDECESSOR", historicalVisit.CreatedBy); // Predecessor credit strictly preserved

        // 4. Verify Immutable Handoff Log written
        var logs = await service.GetHandoffLogsAsync(org.Id);
        Assert.NotEmpty(logs);
        var log = logs.First();
        Assert.Equal("CLO_PREDECESSOR", log.FromOfficerUserId);
        Assert.Equal("CLO_SUCCESSOR", log.ToOfficerUserId);
        Assert.Equal(1, log.ReassignedTasksCount);
        Assert.Equal(result.SecuritySealHash, log.SecuritySealHash);
    }

    [Fact]
    public async Task CoordinatorDispatch_RoleNeutralCapabilityCheck_PreventsUnqualifiedStaff()
    {
        var (factory, db, audit, service) = CreateContext();

        var org = new Organisation { CompanyName = "Defy Appliances Ladysmith", SdlNumber = "L112233445" };
        var contact = new Person { FirstName = "Mandla", LastName = "Dlamini", EmailAddress = "mandla@defy.co.za" };
        db.Organisations.Add(org);
        db.People.Add(contact);

        // Staff member holding only CAP_GRANT_VERIFICATION (not CAP_WORKPLACE_AUDIT)
        var staffCap = new StaffCapability
        {
            UserId = "OFFICER_QA_FINANCE",
            StaffName = "Zanele Khumalo",
            Email = "zanele@merseta.org.za",
            CapabilityCode = "CAP_GRANT_VERIFICATION",
            CapabilityName = "Grant Claim Verification",
            StationedRegionCode = "KZN",
            EmploymentRole = "Finance Specialist",
            IsActive = true
        };
        db.StaffCapabilities.Add(staffCap);
        await db.SaveChangesAsync();

        // Attempting to dispatch Zanele for a Workplace Approval audit requiring CAP_WORKPLACE_AUDIT must fail
        var dispatchReq = new CreateFieldDispatchDto(
            OrganisationId: org.Id,
            VisitId: null,
            ContactPersonId: contact.Id,
            ScheduledDate: DateTime.UtcNow.AddDays(3),
            ActivityTypeCode: "WORKPLACE_APPROVAL",
            RequiredCapabilityCode: "CAP_WORKPLACE_AUDIT", // MISSING CAPABILITY!
            DispatchedOfficerUserId: "OFFICER_QA_FINANCE",
            DispatchedOfficerName: "Zanele Khumalo",
            Priority: "Normal",
            CoordinatorNotes: "Inspect artisan workshop tools"
        );

        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() =>
            service.DispatchFieldVisitAsync(dispatchReq, "CLC_COORDINATOR")
        );

        Assert.Contains("lacks certified capability", ex.Message, StringComparison.OrdinalIgnoreCase);

        // Now grant CAP_WORKPLACE_AUDIT to Zanele (even though her job title is Finance Specialist)
        var newCap = new StaffCapability
        {
            UserId = "OFFICER_QA_FINANCE",
            StaffName = "Zanele Khumalo",
            Email = "zanele@merseta.org.za",
            CapabilityCode = "CAP_WORKPLACE_AUDIT",
            CapabilityName = "Workplace Site Audit & Tool Inspection",
            StationedRegionCode = "KZN",
            EmploymentRole = "Finance Specialist",
            IsActive = true
        };
        await service.GrantStaffCapabilityAsync(newCap, "ADMIN");

        // Now dispatch should succeed polymorphically (anti-rigidity demonstrated)
        var dispatch = await service.DispatchFieldVisitAsync(dispatchReq, "CLC_COORDINATOR");
        Assert.NotNull(dispatch);
        Assert.Equal("Dispatched", dispatch.DispatchStatus);
        Assert.Equal("OFFICER_QA_FINANCE", dispatch.DispatchedOfficerUserId);
        Assert.Equal(contact.Id, dispatch.ContactPersonId);
    }
}
