using Microsoft.EntityFrameworkCore;
using Moq;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Common.Models;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class WspWindowGatingTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit) CreateTestContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        return (factory, db, audit);
    }

    /// <summary>
    /// Invariant 1: Standard 30 April deadline (Regulation 4(1))
    /// Regulation 4(1) of the Grant Regulations mandates statutory lodgement by 30 April 23:59:59 UTC.
    /// </summary>
    [Fact]
    public async Task GetWindowStatusAsync_StandardDeadline_Regulation4_1_EnforcesApril30StatutoryDeadline()
    {
        var (factory, db, audit) = CreateTestContext();
        var service = new WspService(factory, audit);

        var org = new Organisation { CompanyName = "Statutory Alpha Corp", SdlNumber = "L100000000" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        int schemeYear = 2026;
        var status = await service.GetWindowStatusAsync(org.Id, schemeYear);

        // Standard Opening date is 1 January of scheme year at 00:00:00 UTC
        Assert.Equal(new DateTime(schemeYear, 1, 1, 0, 0, 0, DateTimeKind.Utc), status.OpeningDate);

        // Regulation 4(1) statutory closing date is strictly 30 April 23:59:59 UTC
        Assert.Equal(4, status.ClosingDate.Month);
        Assert.Equal(30, status.ClosingDate.Day);
        Assert.Equal(new DateTime(schemeYear, 4, 30, 23, 59, 59, DateTimeKind.Utc), status.ClosingDate);

        // Effective deadline equals standard closing date when no approved extension exists
        Assert.Equal(status.ClosingDate, status.EffectiveDeadline);
        Assert.False(status.HasApprovedExtension);
        Assert.Null(status.GrantedExtensionDate);
        Assert.Null(status.ExtensionReferenceNumber);

        // Effective submission deadline matches closing date
        var effectiveDeadline = await service.GetEffectiveSubmissionDeadlineAsync(org.Id, schemeYear);
        Assert.Equal(status.ClosingDate, effectiveDeadline);
    }

    [Fact]
    public async Task GetWindowStatusAsync_PastStandardDeadline_WithoutExtension_ReturnsClosed()
    {
        var (factory, db, audit) = CreateTestContext();
        var service = new WspService(factory, audit);

        var org = new Organisation { CompanyName = "Beta Industries", SdlNumber = "L100000001" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        // Scheme year 2020 is well in the past
        var status = await service.GetWindowStatusAsync(org.Id, 2020);

        Assert.False(status.IsOpen);
        Assert.True(status.IsClosed);
        Assert.False(status.IsUpcoming);
        Assert.Equal(WspUrgencyTier.Closed, status.UrgencyTier);
        Assert.Equal("Window Closed", status.StatusBadgeText);
        Assert.Contains("Window Closed on", status.FormattedTimeRemaining);
        Assert.False(status.HasApprovedExtension);
        Assert.Equal(new DateTime(2020, 4, 30, 23, 59, 59, DateTimeKind.Utc), status.ClosingDate);
    }

    /// <summary>
    /// Invariant 2: Approved Regulation 4(2) extension override (up to 31 May)
    /// Regulation 4(2) permits an extension up to 31 May of the scheme year upon approved motivation.
    /// </summary>
    [Fact]
    public async Task GetWindowStatusAsync_Regulation4_2_ApprovedExtension_OverridesDeadlineUpToMay31()
    {
        var (factory, db, audit) = CreateTestContext();
        var service = new WspService(factory, audit);

        var org = new Organisation { CompanyName = "Epsilon Heavy Engineering", SdlNumber = "L100000005" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        int schemeYear = 2026;
        var grantedExtensionDate = new DateTime(schemeYear, 5, 31, 23, 59, 59, DateTimeKind.Utc);
        var ext = new WspExtensionRequest
        {
            OrganisationId = org.Id,
            SchemeYear = schemeYear,
            ApplicationReference = "EXT-2026-REG42-MAY31",
            ReasonCode = "TechnicalOutage",
            StatutoryMotivation = "Regulation 4(2) extension granted up to 31 May deadline.",
            RequestedExtensionDate = grantedExtensionDate,
            GrantedExtensionDate = grantedExtensionDate,
            ApprovalStatusCode = "Approved",
            ApprovedByUserId = "CEO_APPROVAL"
        };
        db.WspExtensionRequests.Add(ext);
        await db.SaveChangesAsync();

        var status = await service.GetWindowStatusAsync(org.Id, schemeYear);

        // Standard statutory closing date remains 30 April
        Assert.Equal(new DateTime(schemeYear, 4, 30, 23, 59, 59, DateTimeKind.Utc), status.ClosingDate);

        // Approved Regulation 4(2) extension overrides effective deadline up to 31 May
        Assert.True(status.HasApprovedExtension);
        Assert.Equal("EXT-2026-REG42-MAY31", status.ExtensionReferenceNumber);
        Assert.NotNull(status.GrantedExtensionDate);
        Assert.Equal(new DateTime(schemeYear, 5, 31, 23, 59, 59, DateTimeKind.Utc), status.EffectiveDeadline);
        Assert.True(status.EffectiveDeadline > status.ClosingDate);

        var effectiveDeadline = await service.GetEffectiveSubmissionDeadlineAsync(org.Id, schemeYear);
        Assert.Equal(new DateTime(schemeYear, 5, 31, 23, 59, 59, DateTimeKind.Utc), effectiveDeadline);
    }

    [Fact]
    public async Task GetWindowStatusAsync_ApprovedExtensionActive_ReturnsExtensionActive()
    {
        var (factory, db, audit) = CreateTestContext();
        var service = new WspService(factory, audit);

        var org = new Organisation { CompanyName = "Gamma Machining", SdlNumber = "L100000002" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var schemeYear = 2026;
        var validExtensionDate = DateTime.UtcNow.AddDays(10);
        var ext = new WspExtensionRequest
        {
            OrganisationId = org.Id,
            SchemeYear = schemeYear,
            ApplicationReference = "EXT-2026-0415-888",
            ReasonCode = "TechnicalOutage",
            StatutoryMotivation = "System migration outage extension approved.",
            RequestedExtensionDate = validExtensionDate,
            GrantedExtensionDate = validExtensionDate,
            ApprovalStatusCode = "Approved",
            ApprovedByUserId = "EXECUTIVE_OFFICER"
        };
        db.WspExtensionRequests.Add(ext);
        await db.SaveChangesAsync();

        var status = await service.GetWindowStatusAsync(org.Id, schemeYear);

        Assert.True(status.IsOpen);
        Assert.False(status.IsClosed);
        Assert.True(status.HasApprovedExtension);
        Assert.Equal("EXT-2026-0415-888", status.ExtensionReferenceNumber);
        Assert.Equal(validExtensionDate.Date, status.GrantedExtensionDate?.Date);
        Assert.Equal(WspUrgencyTier.ExtensionActive, status.UrgencyTier);
        Assert.Equal("Approved Extension Active", status.StatusBadgeText);
        Assert.Contains("remaining", status.FormattedTimeRemaining);
    }

    /// <summary>
    /// Invariant 3: Unapproved or pending extension does not override deadline
    /// Neither 'PendingReview', 'Rejected', nor 'Cancelled' extensions may extend the submission window.
    /// </summary>
    [Theory]
    [InlineData("PendingReview")]
    [InlineData("Rejected")]
    [InlineData("Cancelled")]
    public async Task GetWindowStatusAsync_UnapprovedOrPendingExtension_DoesNotOverrideDeadline(string unapprovedStatusCode)
    {
        var (factory, db, audit) = CreateTestContext();
        var service = new WspService(factory, audit);

        var org = new Organisation { CompanyName = $"Delta Motors {unapprovedStatusCode}", SdlNumber = $"L{Guid.NewGuid().ToString("N")[..8]}" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var schemeYear = 2020; // Expired scheme year
        var ext = new WspExtensionRequest
        {
            OrganisationId = org.Id,
            SchemeYear = schemeYear,
            ApplicationReference = $"EXT-2020-{unapprovedStatusCode}",
            ReasonCode = "BusinessRescue",
            StatutoryMotivation = "Extension requested but not approved.",
            RequestedExtensionDate = DateTime.UtcNow.AddDays(15),
            GrantedExtensionDate = DateTime.UtcNow.AddDays(15), // May be present in draft but unapproved
            ApprovalStatusCode = unapprovedStatusCode
        };
        db.WspExtensionRequests.Add(ext);
        await db.SaveChangesAsync();

        var status = await service.GetWindowStatusAsync(org.Id, schemeYear);

        Assert.False(status.IsOpen);
        Assert.True(status.IsClosed);
        Assert.False(status.HasApprovedExtension);
        Assert.Null(status.GrantedExtensionDate);
        Assert.Null(status.ExtensionReferenceNumber);
        Assert.Equal(WspUrgencyTier.Closed, status.UrgencyTier);
        Assert.Equal(new DateTime(schemeYear, 4, 30, 23, 59, 59, DateTimeKind.Utc), status.EffectiveDeadline);

        var isOpen = await service.IsSubmissionWindowOpenAsync(org.Id, schemeYear);
        Assert.False(isOpen);
    }

    /// <summary>
    /// Invariant 4: Urgency tier calculations
    /// Normal > 14 days, Warning <= 14 days, Critical <= 72 hours, Closed, ExtensionActive, Upcoming
    /// </summary>
    [Fact]
    public async Task GetWindowStatusAsync_WindowUpcoming_ReturnsUpcoming()
    {
        var (factory, db, audit) = CreateTestContext();
        var service = new WspService(factory, audit);

        // Scheme year far in the future
        var futureYear = DateTime.UtcNow.Year + 5;
        var status = await service.GetWindowStatusAsync(null, futureYear);

        Assert.False(status.IsOpen);
        Assert.False(status.IsClosed);
        Assert.True(status.IsUpcoming);
        Assert.Equal(WspUrgencyTier.Upcoming, status.UrgencyTier);
        Assert.Equal("Upcoming Window", status.StatusBadgeText);
    }

    [Fact]
    public async Task GetWindowStatusAsync_UrgencyTiers_WarningAndCritical()
    {
        var (factory, db, audit) = CreateTestContext();

        // 1. Critical tier test: deadline within 48 hours (<= 72 hours)
        var mockConfigCritical = new Mock<ISystemConfigurationService>();
        mockConfigCritical.Setup(c => c.GetValueAsync("Governance:CurrentSchemeYear", It.IsAny<string>()))
            .ReturnsAsync("2026");
        mockConfigCritical.Setup(c => c.GetValueAsync("Governance:WspWindowOpenDate", It.IsAny<string>()))
            .ReturnsAsync(DateTime.UtcNow.AddDays(-10).ToString("yyyy-MM-dd HH:mm:ss"));
        mockConfigCritical.Setup(c => c.GetValueAsync("Governance:WspAnnualSubmissionDeadline", It.IsAny<string>()))
            .ReturnsAsync(DateTime.UtcNow.AddHours(48).ToString("yyyy-MM-dd HH:mm:ss"));

        var criticalService = new WspService(factory, audit, mockConfigCritical.Object);
        var criticalStatus = await criticalService.GetWindowStatusAsync(null, 2026);

        Assert.True(criticalStatus.IsOpen);
        Assert.Equal(WspUrgencyTier.Critical, criticalStatus.UrgencyTier);
        Assert.Equal("Critical (<= 72 Hours)", criticalStatus.StatusBadgeText);

        // 2. Warning tier test: deadline in 10 days (<= 14 days and > 72h)
        var mockConfigWarning = new Mock<ISystemConfigurationService>();
        mockConfigWarning.Setup(c => c.GetValueAsync("Governance:CurrentSchemeYear", It.IsAny<string>()))
            .ReturnsAsync("2026");
        mockConfigWarning.Setup(c => c.GetValueAsync("Governance:WspWindowOpenDate", It.IsAny<string>()))
            .ReturnsAsync(DateTime.UtcNow.AddDays(-10).ToString("yyyy-MM-dd HH:mm:ss"));
        mockConfigWarning.Setup(c => c.GetValueAsync("Governance:WspAnnualSubmissionDeadline", It.IsAny<string>()))
            .ReturnsAsync(DateTime.UtcNow.AddDays(10).ToString("yyyy-MM-dd HH:mm:ss"));

        var warningService = new WspService(factory, audit, mockConfigWarning.Object);
        var warningStatus = await warningService.GetWindowStatusAsync(null, 2026);

        Assert.True(warningStatus.IsOpen);
        Assert.Equal(WspUrgencyTier.Warning, warningStatus.UrgencyTier);
        Assert.Equal("Warning (<= 14 Days)", warningStatus.StatusBadgeText);

        // 3. Normal tier test: deadline in 30 days (> 14 days)
        var mockConfigNormal = new Mock<ISystemConfigurationService>();
        mockConfigNormal.Setup(c => c.GetValueAsync("Governance:CurrentSchemeYear", It.IsAny<string>()))
            .ReturnsAsync("2026");
        mockConfigNormal.Setup(c => c.GetValueAsync("Governance:WspWindowOpenDate", It.IsAny<string>()))
            .ReturnsAsync(DateTime.UtcNow.AddDays(-10).ToString("yyyy-MM-dd HH:mm:ss"));
        mockConfigNormal.Setup(c => c.GetValueAsync("Governance:WspAnnualSubmissionDeadline", It.IsAny<string>()))
            .ReturnsAsync(DateTime.UtcNow.AddDays(30).ToString("yyyy-MM-dd HH:mm:ss"));

        var normalService = new WspService(factory, audit, mockConfigNormal.Object);
        var normalStatus = await normalService.GetWindowStatusAsync(null, 2026);

        Assert.True(normalStatus.IsOpen);
        Assert.Equal(WspUrgencyTier.Normal, normalStatus.UrgencyTier);
        Assert.Equal("Window Active", normalStatus.StatusBadgeText);
    }

    /// <summary>
    /// Invariant 5: Service-level submission rejection when window is closed
    /// Validates CreateAsync, UpdateAsync, and UpdateSubmissionStatusAsync throw InvalidOperationException.
    /// </summary>
    [Fact]
    public async Task CreateAsync_WhenDirectlySubmittingPastDeadline_ThrowsInvalidOperationException()
    {
        var (factory, db, audit) = CreateTestContext();
        var service = new WspService(factory, audit);

        var org = new Organisation { CompanyName = "Omega Fabricators", SdlNumber = "L100000004" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var wsp = new WspSubmission
        {
            OrganisationId = org.Id,
            FinYear = 2020, // Expired window
            PlannedTrainingBudget = 100000m,
            EmployeeCount = 25,
            WspApprovalStatusCode = "Submitted"
        };

        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() =>
            service.CreateAsync(wsp, "ApplicantUser"));

        Assert.Contains("closed", ex.Message, StringComparison.OrdinalIgnoreCase);
    }

    [Fact]
    public async Task CreateAsync_DraftAllowedWhenWindowClosed_SubmittingThrowsOnUpdate()
    {
        var (factory, db, audit) = CreateTestContext();
        var service = new WspService(factory, audit);

        var org = new Organisation { CompanyName = "Zeta Manufacturing", SdlNumber = "L100000006" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        // 1. Creating as Draft is permitted even after window is closed
        var draft = new WspSubmission
        {
            OrganisationId = org.Id,
            FinYear = 2020, // Expired window
            PlannedTrainingBudget = 50000m,
            EmployeeCount = 10,
            WspApprovalStatusCode = "Draft"
        };

        var created = await service.CreateAsync(draft, "ApplicantUser");
        Assert.NotNull(created);
        Assert.True(created.Id > 0);
        Assert.Equal("Draft", created.WspApprovalStatusCode);

        // 2. Transitioning to Submitted via UpdateAsync must throw InvalidOperationException
        created.WspApprovalStatusCode = "Submitted";
        var updateEx = await Assert.ThrowsAsync<InvalidOperationException>(() =>
            service.UpdateAsync(created, "ApplicantUser"));
        Assert.Contains("closed", updateEx.Message, StringComparison.OrdinalIgnoreCase);

        // 3. Transitioning to Submitted via UpdateSubmissionStatusAsync must also throw InvalidOperationException
        var statusEx = await Assert.ThrowsAsync<InvalidOperationException>(() =>
            service.UpdateSubmissionStatusAsync(created.Id, "Submitted", "ApplicantUser"));
        Assert.Contains("closed", statusEx.Message, StringComparison.OrdinalIgnoreCase);
    }

    [Fact]
    public async Task SubmissionGating_WhenApprovedExtensionActivePastStandardDeadline_AllowsSubmission()
    {
        var (factory, db, audit) = CreateTestContext();
        var service = new WspService(factory, audit);

        var org = new Organisation { CompanyName = "Eta Precision Works", SdlNumber = "L100000007" };
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        int schemeYear = 2026;
        var validExtensionDate = DateTime.UtcNow.AddDays(7);
        var ext = new WspExtensionRequest
        {
            OrganisationId = org.Id,
            SchemeYear = schemeYear,
            ApplicationReference = "EXT-2026-ACTIVE-SUBMIT",
            ReasonCode = "TechnicalOutage",
            StatutoryMotivation = "Valid active extension.",
            RequestedExtensionDate = validExtensionDate,
            GrantedExtensionDate = validExtensionDate,
            ApprovalStatusCode = "Approved",
            ApprovedByUserId = "EXECUTIVE_OFFICER"
        };
        db.WspExtensionRequests.Add(ext);
        await db.SaveChangesAsync();

        var wsp = new WspSubmission
        {
            OrganisationId = org.Id,
            FinYear = schemeYear,
            PlannedTrainingBudget = 75000m,
            EmployeeCount = 15,
            WspApprovalStatusCode = "Submitted"
        };

        // Allowed because approved extension is currently active
        var created = await service.CreateAsync(wsp, "ApplicantUser");
        Assert.NotNull(created);
        Assert.True(created.Id > 0);
        Assert.Equal("Submitted", created.WspApprovalStatusCode);
    }
}
