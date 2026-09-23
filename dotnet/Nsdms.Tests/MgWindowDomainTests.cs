using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Nsdms.Application.Common.Models;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Domain.Lookups;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class MgWindowDomainTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, SystemConfigurationService configService, MgWindowGovernanceService govService, OfoCodeSetService ofoSetService) CreateTestContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var conf = new ConfigurationBuilder().Build();
        var configService = new SystemConfigurationService(factory, conf, audit);
        var govService = new MgWindowGovernanceService(factory, audit, configService);
        var ofoSetService = new OfoCodeSetService(factory, audit);
        return (factory, db, audit, configService, govService, ofoSetService);
    }

    private static async Task SeedBaselineOfoData(NsdmsDbContext db)
    {
        // Add national lookup OFO codes (some active, one inactive)
        db.OfoCodeTypes.AddRange(
            new OfoCodeType { Code = "651202", Name = "Welder", Active = true },
            new OfoCodeType { Code = "653101", Name = "Automotive Motor Mechanic", Active = true },
            new OfoCodeType { Code = "214401", Name = "Mechanical Engineer", Active = true },
            new OfoCodeType { Code = "999999", Name = "Deprecated Legacy Occupation", Active = false }
        );

        // Add 2025 OFO Set
        var set2025 = new OfoCodeSet
        {
            Id = 1,
            SetYear = 2025,
            Name = "DHET OFO 2025 Gazetted Set",
            GazettedDate = new DateTime(2024, 11, 20, 0, 0, 0, DateTimeKind.Utc),
            GazetteNumber = "Gazette No. 51234",
            IsActive = true
        };

        // Add 2021 OFO Set
        var set2021 = new OfoCodeSet
        {
            Id = 2,
            SetYear = 2021,
            Name = "DHET OFO 2021 Gazetted Set",
            GazettedDate = new DateTime(2021, 4, 1, 0, 0, 0, DateTimeKind.Utc),
            GazetteNumber = "Gazette No. 44412",
            IsActive = true
        };

        db.OfoCodeSets.AddRange(set2025, set2021);

        // Populate items into 2025 set: 651202, 653101, and deprecated 999999
        db.OfoCodeSetItems.AddRange(
            new OfoCodeSetItem { OfoCodeSetId = 1, OfoCodeId = "651202", MajorGroup = "6", Trade = true, IsActiveInSet = true },
            new OfoCodeSetItem { OfoCodeSetId = 1, OfoCodeId = "653101", MajorGroup = "6", Trade = true, IsActiveInSet = true },
            new OfoCodeSetItem { OfoCodeSetId = 1, OfoCodeId = "999999", MajorGroup = "9", Trade = false, IsActiveInSet = true }, // inactive in lookup
            // 214401 belongs only to 2021 set!
            new OfoCodeSetItem { OfoCodeSetId = 2, OfoCodeId = "214401", MajorGroup = "2", Trade = false, IsActiveInSet = true }
        );

        await db.SaveChangesAsync();
    }

    [Fact]
    public async Task CreateDraftWindow_InvalidDateSequence_ThrowsArgumentException()
    {
        // Arrange
        var (factory, db, audit, configService, govService, ofoSetService) = CreateTestContext();
        await SeedBaselineOfoData(db);

        var dtoInvalidOpenClose = new CreateMgWindowDto
        {
            SchemeYear = 2026,
            WindowName = "2026 Submission Cycle",
            OpeningDate = new DateTime(2026, 5, 1, 0, 0, 0, DateTimeKind.Utc),
            ClosingDate = new DateTime(2026, 4, 30, 23, 59, 0, DateTimeKind.Utc), // Closing precedes opening!
            ExtensionCutoffDate = new DateTime(2026, 4, 15, 0, 0, 0, DateTimeKind.Utc),
            OfoCodeSetId = 1,
            Justification = "Annual Mandatory Grant cycle"
        };

        // Act & Assert
        var ex = await Assert.ThrowsAsync<ArgumentException>(() =>
            govService.CreateDraftWindowAsync(dtoInvalidOpenClose, "Officer1", "Officer One"));
        Assert.Contains("Opening date and time must precede", ex.Message, StringComparison.OrdinalIgnoreCase);

        var dtoInvalidExtensionCutoff = new CreateMgWindowDto
        {
            SchemeYear = 2026,
            WindowName = "2026 Submission Cycle",
            OpeningDate = new DateTime(2026, 1, 1, 0, 0, 0, DateTimeKind.Utc),
            ClosingDate = new DateTime(2026, 4, 30, 23, 59, 0, DateTimeKind.Utc),
            ExtensionCutoffDate = new DateTime(2026, 5, 15, 0, 0, 0, DateTimeKind.Utc), // Cutoff exceeds closing deadline!
            OfoCodeSetId = 1,
            Justification = "Annual Mandatory Grant cycle"
        };

        var ex2 = await Assert.ThrowsAsync<ArgumentException>(() =>
            govService.CreateDraftWindowAsync(dtoInvalidExtensionCutoff, "Officer1", "Officer One"));
        Assert.Contains("Extension filing cutoff date cannot be later", ex2.Message, StringComparison.OrdinalIgnoreCase);
    }

    [Fact]
    public async Task AddScopedOfoCode_InactiveOfoCode_ThrowsInvalidOperationException()
    {
        // Arrange (INVARIANT 1: Active Code Enforcement)
        var (factory, db, audit, configService, govService, ofoSetService) = CreateTestContext();
        await SeedBaselineOfoData(db);

        var window = await govService.CreateDraftWindowAsync(new CreateMgWindowDto
        {
            SchemeYear = 2026,
            WindowName = "2026 Mandatory Grant Window",
            OpeningDate = new DateTime(2026, 1, 1, 0, 0, 0, DateTimeKind.Utc),
            ClosingDate = new DateTime(2026, 4, 30, 23, 59, 0, DateTimeKind.Utc),
            ExtensionCutoffDate = new DateTime(2026, 4, 15, 0, 0, 0, DateTimeKind.Utc),
            OfoCodeSetId = 1, // 2025 Set
            Justification = "Statutory cycle"
        }, "Proposer1", "Proposer One");

        // Act & Assert: Try scoping code '999999' which is Active = false in lookup.OfoCodeType
        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() =>
            govService.AddScopedOfoCodeAsync(window.Id, new AddScopedOfoCodeDto
            {
                OfoCode = "999999",
                IsPrioritySkill = false
            }, "Officer1"));

        Assert.Contains("INACTIVE/DEPRECATED", ex.Message, StringComparison.OrdinalIgnoreCase);
    }

    [Fact]
    public async Task AddScopedOfoCode_CodeNotInSelectedSet_ThrowsInvalidOperationException()
    {
        // Arrange (INVARIANT 2: Set Membership Enforcement)
        var (factory, db, audit, configService, govService, ofoSetService) = CreateTestContext();
        await SeedBaselineOfoData(db);

        var window = await govService.CreateDraftWindowAsync(new CreateMgWindowDto
        {
            SchemeYear = 2026,
            WindowName = "2026 Mandatory Grant Window",
            OpeningDate = new DateTime(2026, 1, 1, 0, 0, 0, DateTimeKind.Utc),
            ClosingDate = new DateTime(2026, 4, 30, 23, 59, 0, DateTimeKind.Utc),
            ExtensionCutoffDate = new DateTime(2026, 4, 15, 0, 0, 0, DateTimeKind.Utc),
            OfoCodeSetId = 1, // 2025 Set
            Justification = "Statutory cycle"
        }, "Proposer1", "Proposer One");

        // Code '214401' is active, but only belongs to Set 2 (2021 Set), NOT Set 1 (2025 Set)!
        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() =>
            govService.AddScopedOfoCodeAsync(window.Id, new AddScopedOfoCodeDto
            {
                OfoCode = "214401",
                IsPrioritySkill = false
            }, "Officer1"));

        Assert.Contains("not a gazetted member of the 2025 OFO Set", ex.Message, StringComparison.OrdinalIgnoreCase);
    }

    [Fact]
    public async Task AdjudicateWindow_ProposerAttemptsApproval_ThrowsInvalidOperationException()
    {
        // Arrange (INVARIANT 3: PFMA Dual Authorisation / Segregation of Duties)
        var (factory, db, audit, configService, govService, ofoSetService) = CreateTestContext();
        await SeedBaselineOfoData(db);

        var window = await govService.CreateDraftWindowAsync(new CreateMgWindowDto
        {
            SchemeYear = 2026,
            WindowName = "2026 Mandatory Grant Window",
            OpeningDate = new DateTime(2026, 1, 1, 0, 0, 0, DateTimeKind.Utc),
            ClosingDate = new DateTime(2026, 4, 30, 23, 59, 0, DateTimeKind.Utc),
            ExtensionCutoffDate = new DateTime(2026, 4, 15, 0, 0, 0, DateTimeKind.Utc),
            OfoCodeSetId = 1,
            Justification = "Statutory cycle"
        }, "Officer_Proposer", "Officer Proposer");

        // Submit for review
        await govService.SubmitForReviewAsync(window.Id, "Officer_Proposer", "Officer Proposer");

        // Act & Assert: Proposer attempts self-approval
        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() =>
            govService.AdjudicateWindowAsync(window.Id, approve: true, "Self approval attempt", "Officer_Proposer", "Officer Proposer"));

        Assert.Contains("Segregation of Duties Violation", ex.Message, StringComparison.OrdinalIgnoreCase);
    }

    [Fact]
    public async Task BulkSyncFromSet_CopiesAllActiveCodes_Idempotently()
    {
        // Arrange (INVARIANT 1 & 2: Synchronize active codes from set)
        var (factory, db, audit, configService, govService, ofoSetService) = CreateTestContext();
        await SeedBaselineOfoData(db);

        var window = await govService.CreateDraftWindowAsync(new CreateMgWindowDto
        {
            SchemeYear = 2026,
            WindowName = "2026 Mandatory Grant Window",
            OpeningDate = new DateTime(2026, 1, 1, 0, 0, 0, DateTimeKind.Utc),
            ClosingDate = new DateTime(2026, 4, 30, 23, 59, 0, DateTimeKind.Utc),
            ExtensionCutoffDate = new DateTime(2026, 4, 15, 0, 0, 0, DateTimeKind.Utc),
            OfoCodeSetId = 1, // 2025 Set has 651202, 653101 (active) and 999999 (inactive)
            Justification = "Statutory cycle"
        }, "Proposer1", "Proposer One");

        // Act 1: First sync
        int syncedFirst = await govService.BulkSyncFromSetAsync(window.Id, "Officer1");

        // Assert 1: Only the 2 active codes (651202, 653101) should be synced, 999999 skipped because Active == false
        Assert.Equal(2, syncedFirst);

        var scoped = await db.MgWindowOfoCodes.Where(o => o.MgWindowId == window.Id && o.IsActive).ToListAsync();
        Assert.Equal(2, scoped.Count);
        Assert.Contains(scoped, s => s.OfoCodeId == "651202");
        Assert.Contains(scoped, s => s.OfoCodeId == "653101");
        Assert.DoesNotContain(scoped, s => s.OfoCodeId == "999999");

        // Act 2: Second sync (Idempotency test)
        int syncedSecond = await govService.BulkSyncFromSetAsync(window.Id, "Officer1");

        // Assert 2: No duplicates created
        Assert.Equal(0, syncedSecond);
        var scopedAfterSecond = await db.MgWindowOfoCodes.Where(o => o.MgWindowId == window.Id && o.IsActive).ToListAsync();
        Assert.Equal(2, scopedAfterSecond.Count);
    }

    [Fact]
    public async Task TogglePrioritySkill_WritesAuditLog_AndUpdatesState()
    {
        // Arrange
        var (factory, db, audit, configService, govService, ofoSetService) = CreateTestContext();
        await SeedBaselineOfoData(db);

        var window = await govService.CreateDraftWindowAsync(new CreateMgWindowDto
        {
            SchemeYear = 2026,
            WindowName = "2026 Mandatory Grant Window",
            OpeningDate = new DateTime(2026, 1, 1, 0, 0, 0, DateTimeKind.Utc),
            ClosingDate = new DateTime(2026, 4, 30, 23, 59, 0, DateTimeKind.Utc),
            ExtensionCutoffDate = new DateTime(2026, 4, 15, 0, 0, 0, DateTimeKind.Utc),
            OfoCodeSetId = 1,
            Justification = "Statutory cycle"
        }, "Proposer1", "Proposer One");

        var scopedItem = await govService.AddScopedOfoCodeAsync(window.Id, new AddScopedOfoCodeDto
        {
            OfoCode = "651202", // Welder
            IsPrioritySkill = false
        }, "Proposer1");

        Assert.False(scopedItem.IsPrioritySkill);

        // Act: Toggle to true
        bool success = await govService.TogglePrioritySkillAsync(window.Id, scopedItem.Id, isPrioritySkill: true, "SectorOfficer");

        // Assert
        Assert.True(success);
        var updated = await db.MgWindowOfoCodes.FindAsync(scopedItem.Id);
        Assert.NotNull(updated);
        Assert.True(updated.IsPrioritySkill);

        // Verify double-write in audit_logs
        var auditEntry = await db.AuditLogs
            .FirstOrDefaultAsync(a => a.EntityName == "MgWindowOfoCode" && a.RecordId == scopedItem.Id && a.ActionName == "TogglePrioritySkill");
        Assert.NotNull(auditEntry);
        Assert.Equal("SectorOfficer", auditEntry.Actor);
    }

    [Fact]
    public async Task ApproveWindow_SynchronizesSystemConfig_AndArchivesPriorWindow()
    {
        // Arrange (INVARIANT 5: Single Live Window & SystemConfig Sync)
        var (factory, db, audit, configService, govService, ofoSetService) = CreateTestContext();
        await SeedBaselineOfoData(db);

        // Prior active window
        var priorWindow = new MgWindow
        {
            SchemeYear = 2026,
            WindowName = "2026 Prior Window",
            OpeningDate = new DateTime(2026, 1, 1, 0, 0, 0, DateTimeKind.Utc),
            ClosingDate = new DateTime(2026, 4, 30, 23, 59, 0, DateTimeKind.Utc),
            ExtensionCutoffDate = new DateTime(2026, 4, 15, 0, 0, 0, DateTimeKind.Utc),
            ApprovalStatus = "Approved",
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "PriorAdmin"
        };
        db.MgWindows.Add(priorWindow);
        await db.SaveChangesAsync();

        // New window proposal
        var newWindow = await govService.CreateDraftWindowAsync(new CreateMgWindowDto
        {
            SchemeYear = 2026,
            WindowName = "2026 New Gazetted Window",
            OpeningDate = new DateTime(2026, 2, 1, 8, 0, 0, DateTimeKind.Utc),
            ClosingDate = new DateTime(2026, 5, 15, 23, 59, 0, DateTimeKind.Utc),
            ExtensionCutoffDate = new DateTime(2026, 4, 30, 0, 0, 0, DateTimeKind.Utc),
            OfoCodeSetId = 1,
            GazetteReference = "Gazette No. 51234",
            Justification = "Gazetted extension under Regulation 4(2)"
        }, "Maker_User", "Maker User");

        await govService.SubmitForReviewAsync(newWindow.Id, "Maker_User", "Maker User");

        // Act: Independent checker approves
        var approved = await govService.AdjudicateWindowAsync(
            newWindow.Id,
            approve: true,
            comments: "Approved in accordance with ministerial gazette.",
            checkerUserId: "Checker_Executive",
            checkerUserName: "Executive Officer");

        // Assert
        Assert.Equal("Approved", approved.ApprovalStatus);
        Assert.True(approved.IsActive);

        // Prior window must be archived
        using var verifyDb = (NsdmsDbContext)factory.CreateDbContext();
        var reloadedPrior = await verifyDb.MgWindows.AsNoTracking().FirstOrDefaultAsync(w => w.Id == priorWindow.Id);
        Assert.NotNull(reloadedPrior);
        Assert.Equal("Archived", reloadedPrior.ApprovalStatus);
        Assert.False(reloadedPrior.IsActive);

        // SystemConfig parameters must reflect approved window dates
        var syncedOpenDate = await configService.GetValueAsync("Governance:WspWindowOpenDate", "");
        var syncedDeadline = await configService.GetValueAsync("Governance:WspAnnualSubmissionDeadline", "");
        var syncedExtCutoff = await configService.GetValueAsync("Governance:WspExtensionRequestDeadline", "");
        var syncedYear = await configService.GetValueAsync("Governance:CurrentSchemeYear", "");

        Assert.Equal("2026-02-01 08:00", syncedOpenDate);
        Assert.Equal("2026-05-15 23:59", syncedDeadline);
        Assert.Equal("2026-04-30", syncedExtCutoff);
        Assert.Equal("2026", syncedYear);
    }
}
