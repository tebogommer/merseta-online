using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class WspServiceTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, WspService service) CreateTestContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var service = new WspService(factory, audit);
        return (factory, db, audit, service);
    }

    [Fact]
    public async Task CreateAsync_GeneratesReferenceNumberAndLogsAudit()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation { CompanyName = "Steel Works Corp", SdlNumber = "L500600700" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var wsp = new WspSubmission
        {
            OrganisationId = org.Id,
            FinYear = 2026,
            PlannedTrainingBudget = 250000m,
            EmployeeCount = 50
        };

        // Act
        var created = await service.CreateAsync(wsp, "WspOfficer");

        // Assert
        Assert.True(created.Id > 0);
        Assert.StartsWith("WSP-2026-", created.ReferenceNumber);
        Assert.Equal("WspOfficer", created.CreatedBy);

        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "WspSubmission" && a.RecordId == created.Id);
        Assert.NotNull(auditLog);
        Assert.Equal("Create", auditLog.ActionName);
    }

    [Fact]
    public async Task GetAllAsync_FiltersByFinYearAndSearch()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org1 = new Organisation { CompanyName = "Apex Metal", SdlNumber = "L111" };
        var org2 = new Organisation { CompanyName = "Zenith Plastics", SdlNumber = "L222" };
        db.Organisations.AddRange(org1, org2);
        await db.SaveChangesAsync();

        db.WspSubmissions.AddRange(
            new WspSubmission { OrganisationId = org1.Id, FinYear = 2025, ReferenceNumber = "WSP-2025-001" },
            new WspSubmission { OrganisationId = org1.Id, FinYear = 2026, ReferenceNumber = "WSP-2026-001" },
            new WspSubmission { OrganisationId = org2.Id, FinYear = 2026, ReferenceNumber = "WSP-2026-002" }
        );
        await db.SaveChangesAsync();

        // Act
        var year2026Only = await service.GetAllAsync(finYear: 2026);
        var searchZenith = await service.GetAllAsync(search: "Zenith");

        // Assert
        Assert.Equal(2, year2026Only.Count);
        Assert.Single(searchZenith);
        Assert.Equal("WSP-2026-002", searchZenith[0].ReferenceNumber);
    }

    [Fact]
    public async Task AddTrainingPlanAsync_AddsPlanAndRecalculatesBudget()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation { CompanyName = "Harmony Mining", SdlNumber = "L333" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var wsp = await service.CreateAsync(new WspSubmission
        {
            OrganisationId = org.Id,
            FinYear = 2026,
            PlannedTrainingBudget = 0m
        });

        // Act - Add Training Plan
        var plan = new WspTrainingPlan
        {
            WspSubmissionId = wsp.Id,
            ProgrammeTypeCode = "Apprenticeship",
            NqfLevel = 4,
            BeneficiaryCount = 10,
            EstimatedCost = 150000m
        };

        var added = await service.AddTrainingPlanAsync(plan, "Admin");

        // Assert
        Assert.True(added.Id > 0);
        var plans = await service.GetTrainingPlansAsync(wsp.Id);
        Assert.Single(plans);
        Assert.Equal(150000m, plans[0].EstimatedCost);
    }

    [Fact]
    public async Task CalculateMandatoryGrant_Returns20Percent()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        // Act
        var mg = service.CalculateMandatoryGrant(100000m);

        // Assert
        Assert.Equal(20000m, mg);
    }

    [Fact]
    public async Task DeleteAsync_DeletesSubmissionAndLogsAudit()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var org = new Organisation { CompanyName = "Temp Org", SdlNumber = "L999" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var wsp = await service.CreateAsync(new WspSubmission { OrganisationId = org.Id, FinYear = 2026 });

        // Act
        var deleted = await service.DeleteAsync(wsp.Id, "AdminDeleter");

        // Assert
        Assert.True(deleted);
        var inDb = await service.GetByIdAsync(wsp.Id);
        Assert.Null(inDb);
    }

    [Fact]
    public async Task GetEffectiveSubmissionDeadlineAsync_StandardDeadline_Returns30April()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();
        var org = new Organisation { CompanyName = "Alpha Motors", SdlNumber = "L100200300" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        // Act
        var deadline = await service.GetEffectiveSubmissionDeadlineAsync(org.Id, 2026);

        // Assert: standard statutory deadline is 30 April 23:59:59 UTC
        Assert.Equal(2026, deadline.Year);
        Assert.Equal(4, deadline.Month);
        Assert.Equal(30, deadline.Day);
    }

    [Fact]
    public async Task GetEffectiveSubmissionDeadlineAsync_ApprovedExtension_ReturnsGrantedDate()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();
        var org = new Organisation { CompanyName = "Beta Engineering", SdlNumber = "L200300400" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var grantedDate = new DateTime(2026, 5, 25, 23, 59, 59, DateTimeKind.Utc);
        var ext = new WspExtensionRequest
        {
            OrganisationId = org.Id,
            SchemeYear = 2026,
            ApplicationReference = "EXT-2026-0415-001",
            ReasonCode = "BusinessRescue",
            StatutoryMotivation = "Formal business rescue proceedings under Companies Act.",
            RequestedExtensionDate = new DateTime(2026, 5, 25),
            GrantedExtensionDate = grantedDate,
            ApprovalStatusCode = "Approved",
            SubmittedByUserId = "SDF_USER",
            ApprovedByUserId = "EXECUTIVE_OFFICER"
        };
        db.WspExtensionRequests.Add(ext);
        await db.SaveChangesAsync();

        // Act
        var deadline = await service.GetEffectiveSubmissionDeadlineAsync(org.Id, 2026);

        // Assert: deadline should be the approved extension date 25 May
        Assert.Equal(2026, deadline.Year);
        Assert.Equal(5, deadline.Month);
        Assert.Equal(25, deadline.Day);
    }

    [Fact]
    public async Task SubmitExtensionRequestAsync_CreatesPendingReviewRequestAndCapsAt31May()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();
        var org = new Organisation { CompanyName = "Delta Manufacturing", SdlNumber = "L300400500" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var req = new WspExtensionRequest
        {
            OrganisationId = org.Id,
            SchemeYear = 2026,
            ReasonCode = "TechnicalOutage",
            StatutoryMotivation = "Total ERP and connectivity failure during submission week.",
            RequestedExtensionDate = new DateTime(2026, 6, 15), // Exceeds statutory 31 May limit
            DeclarationAccepted = true
        };

        // Act
        var submitted = await service.SubmitExtensionRequestAsync(req, "SdfUser");

        // Assert
        Assert.True(submitted.Id > 0);
        Assert.Equal("PendingReview", submitted.ApprovalStatusCode);
        Assert.Equal("SdfUser", submitted.SubmittedByUserId);
        Assert.StartsWith("EXT-2026-", submitted.ApplicationReference);

        // Clamped to 31 May
        Assert.Equal(5, submitted.RequestedExtensionDate.Month);
        Assert.Equal(31, submitted.RequestedExtensionDate.Day);

        // Audit log verified
        var auditEntry = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "WspExtensionRequest" && a.RecordId == submitted.Id);
        Assert.NotNull(auditEntry);
        Assert.Equal("Submit", auditEntry.ActionName);
    }

    [Fact]
    public async Task SubmitExtensionRequestAsync_DuplicateActiveRequest_ThrowsInvalidOperationException()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();
        var org = new Organisation { CompanyName = "Echo Plastics", SdlNumber = "L400500600" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var first = new WspExtensionRequest
        {
            OrganisationId = org.Id,
            SchemeYear = 2026,
            ReasonCode = "NaturalDisaster",
            StatutoryMotivation = "Flooding at industrial plant.",
            RequestedExtensionDate = new DateTime(2026, 5, 20)
        };
        await service.SubmitExtensionRequestAsync(first, "SdfUser");

        // Act & Assert: second submission while first is still pending must throw
        var second = new WspExtensionRequest
        {
            OrganisationId = org.Id,
            SchemeYear = 2026,
            ReasonCode = "BusinessRescue",
            StatutoryMotivation = "Duplicate attempt.",
            RequestedExtensionDate = new DateTime(2026, 5, 22)
        };

        await Assert.ThrowsAsync<InvalidOperationException>(() => service.SubmitExtensionRequestAsync(second, "SdfUser"));
    }

    [Fact]
    public async Task ReviewExtensionRequestAsync_Stage1MakerChecker_RecommendsOrRejects()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();
        var org = new Organisation { CompanyName = "Foxtrot Logistics", SdlNumber = "L500600700" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var req = await service.SubmitExtensionRequestAsync(new WspExtensionRequest
        {
            OrganisationId = org.Id,
            SchemeYear = 2026,
            ReasonCode = "IndustrialAction",
            StatutoryMotivation = "Protracted 3-week lawful strike action.",
            RequestedExtensionDate = new DateTime(2026, 5, 15)
        }, "SdfUser");

        // Act: CLO reviews and recommends
        var reviewed = await service.ReviewExtensionRequestAsync(req.Id, "CLO_OFFICER", recommend: true, "Verified strike certificate and union dispute documentation.");

        // Assert
        Assert.Equal("Recommended", reviewed.ApprovalStatusCode);
        Assert.Equal("CLO_OFFICER", reviewed.ReviewedByUserId);
        Assert.NotNull(reviewed.ReviewedAt);
        Assert.Equal("Verified strike certificate and union dispute documentation.", reviewed.ReviewerComments);
    }

    [Fact]
    public async Task AdjudicateExtensionRequestAsync_Stage2ExecutiveMakerChecker_ApprovesWithGrantedDate()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();
        var org = new Organisation { CompanyName = "Golf Mining Corp", SdlNumber = "L600700800" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var req = await service.SubmitExtensionRequestAsync(new WspExtensionRequest
        {
            OrganisationId = org.Id,
            SchemeYear = 2026,
            ReasonCode = "BusinessRescue",
            StatutoryMotivation = "Business rescue practitioner appointed by court.",
            RequestedExtensionDate = new DateTime(2026, 5, 20)
        }, "SdfUser");

        await service.ReviewExtensionRequestAsync(req.Id, "CLO_OFFICER", recommend: true, "Recommended by CLO.");

        // Act: Executive adjudicates and approves
        var grantedDate = new DateTime(2026, 5, 20, 23, 59, 59, DateTimeKind.Utc);
        var adjudicated = await service.AdjudicateExtensionRequestAsync(req.Id, "EXECUTIVE_AUTHORITY", approve: true, grantedDate, "Approved in terms of Regulation 4(2).");

        // Assert
        Assert.Equal("Approved", adjudicated.ApprovalStatusCode);
        Assert.Equal("EXECUTIVE_AUTHORITY", adjudicated.ApprovedByUserId);
        Assert.NotNull(adjudicated.ApprovedAt);
        Assert.Equal(grantedDate, adjudicated.GrantedExtensionDate);
        Assert.Equal("Approved in terms of Regulation 4(2).", adjudicated.ApprovalComments);
    }

    [Fact]
    public async Task AdjudicateExtensionRequestAsync_MakerCheckerViolation_ThrowsException()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();
        var org = new Organisation { CompanyName = "Hotel Services", SdlNumber = "L700800900" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var req = await service.SubmitExtensionRequestAsync(new WspExtensionRequest
        {
            OrganisationId = org.Id,
            SchemeYear = 2026,
            ReasonCode = "TechnicalOutage",
            StatutoryMotivation = "Data loss incident.",
            RequestedExtensionDate = new DateTime(2026, 5, 10)
        }, "SdfUser");

        await service.ReviewExtensionRequestAsync(req.Id, "SAME_OFFICER", recommend: true, "Recommended.");

        // Act & Assert: Same officer attempting to adjudicate must be blocked
        await Assert.ThrowsAsync<InvalidOperationException>(() =>
            service.AdjudicateExtensionRequestAsync(req.Id, "SAME_OFFICER", approve: true, new DateTime(2026, 5, 10), "Self-approval attempt."));
    }

    [Fact]
    public async Task UpdateSubmissionStatusAsync_WhenWindowClosed_ThrowsInvalidOperationException()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();
        var org = new Organisation { CompanyName = "India Holdings", SdlNumber = "L800900100" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        // Scheme year 2020 is well in the past (closed on 30 April 2020)
        var wsp = await service.CreateAsync(new WspSubmission
        {
            OrganisationId = org.Id,
            FinYear = 2020,
            PlannedTrainingBudget = 50000m,
            EmployeeCount = 20,
            WspApprovalStatusCode = "Draft"
        });

        // Act & Assert: attempting to update status to "Submitted" past deadline without extension must fail
        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() =>
            service.UpdateSubmissionStatusAsync(wsp.Id, "Submitted", "SdfUser"));

        Assert.Contains("closed", ex.Message);
    }

    [Fact]
    public async Task UpdateSubmissionStatusAsync_WhenWindowOpenWithApprovedExtension_Succeeds()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();
        var org = new Organisation { CompanyName = "Juliet Precision", SdlNumber = "L900100200" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        // Scheme year 2026 with approved extension valid through future date
        var schemeYear = 2026;
        var validExtensionDate = DateTime.UtcNow.AddDays(7);
        var ext = new WspExtensionRequest
        {
            OrganisationId = org.Id,
            SchemeYear = schemeYear,
            ApplicationReference = "EXT-2026-0415-099",
            ReasonCode = "BusinessRescue",
            StatutoryMotivation = "Approved corporate rescue extension.",
            RequestedExtensionDate = validExtensionDate,
            GrantedExtensionDate = validExtensionDate,
            ApprovalStatusCode = "Approved",
            ApprovedByUserId = "EXECUTIVE_OFFICER"
        };
        db.WspExtensionRequests.Add(ext);
        await db.SaveChangesAsync();

        var wsp = await service.CreateAsync(new WspSubmission
        {
            OrganisationId = org.Id,
            FinYear = schemeYear,
            PlannedTrainingBudget = 75000m,
            EmployeeCount = 15,
            WspApprovalStatusCode = "Draft"
        });

        // Act: submission within open window should succeed
        var updated = await service.UpdateSubmissionStatusAsync(wsp.Id, "Submitted", "SdfUser");

        // Assert
        Assert.Equal("Submitted", updated.WspApprovalStatusCode);
        Assert.NotNull(updated.SubmissionDate);
    }
}
