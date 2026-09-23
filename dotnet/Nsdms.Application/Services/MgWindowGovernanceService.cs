using System.Globalization;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Common.Models;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class MgWindowScheduleDto
{
    public DateTime OpeningDate { get; set; }
    public DateTime ClosingDate { get; set; }
    public DateTime ExtensionCutoffDate { get; set; }
    public int SchemeYear { get; set; }
    public bool IsWindowOpen { get; set; }
    public string StatusBadgeText { get; set; } = string.Empty;
}

public interface IMgWindowGovernanceService
{
    // Backwards compatibility with proposals & current schedule
    Task<MgWindowScheduleDto> GetCurrentScheduleAsync();
    Task<MgWindowScheduleProposal?> GetActivePendingProposalAsync(int? schemeYear = null);
    Task<List<MgWindowScheduleProposal>> GetProposalHistoryAsync(int? schemeYear = null);
    Task<MgWindowScheduleProposal> SubmitScheduleProposalAsync(
        int schemeYear,
        DateTime openingDate,
        DateTime closingDate,
        DateTime extensionCutoffDate,
        string justification,
        string? gazetteOrResolutionRef,
        string makerUserId,
        string makerUserName);
    Task<MgWindowScheduleProposal> AdjudicateProposalAsync(
        int proposalId,
        bool approve,
        string? comments,
        string checkerUserId,
        string checkerUserName,
        IList<string>? checkerRoles);
    Task<MgWindowScheduleProposal> WithdrawProposalAsync(int proposalId, string userId);

    // Option A: Master Mandatory Grant Windows
    Task<List<MgWindowListDto>> GetWindowsAsync(int? schemeYear = null, string? status = null, string? search = null);
    Task<MgWindowDetailDto?> GetWindowDetailAsync(int windowId);
    Task<MgWindow> CreateDraftWindowAsync(CreateMgWindowDto dto, string userId, string userName);
    Task<MgWindow> UpdateDraftWindowAsync(int windowId, UpdateMgWindowDto dto, string userId, string userName);
    Task<MgWindow> SubmitForReviewAsync(int windowId, string userId, string userName);
    Task<MgWindow> AdjudicateWindowAsync(int windowId, bool approve, string? comments, string checkerUserId, string checkerUserName);
    Task<MgWindow> WithdrawWindowProposalAsync(int windowId, string userId);

    // Option A: Scoped OFO Codes Child Operations
    Task<PagedResult<MgWindowOfoCodeDto>> GetScopedOfoCodesAsync(int windowId, OfoFilterQuery query);
    Task<int> BulkSyncFromSetAsync(int windowId, string userId);
    Task<MgWindowOfoCodeDto> AddScopedOfoCodeAsync(int windowId, AddScopedOfoCodeDto dto, string userId);
    Task<bool> TogglePrioritySkillAsync(int windowId, int windowOfoCodeId, bool isPrioritySkill, string userId);
    Task<bool> RemoveScopedOfoCodeAsync(int windowId, int windowOfoCodeId, string userId);
}

public class MgWindowGovernanceService : IMgWindowGovernanceService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;
    private readonly ISystemConfigurationService _configService;

    public MgWindowGovernanceService(
        INsdmsDbContextFactory contextFactory,
        IAuditService audit,
        ISystemConfigurationService configService)
    {
        _contextFactory = contextFactory;
        _audit = audit;
        _configService = configService;
    }

    public async Task<MgWindowScheduleDto> GetCurrentScheduleAsync()
    {
        var now = DateTime.UtcNow;
        var schemeYearStr = await _configService.GetValueAsync("Governance:CurrentSchemeYear", now.Year.ToString());
        int.TryParse(schemeYearStr, out int schemeYear);
        if (schemeYear <= 0) schemeYear = now.Year;

        var openDateStr = await _configService.GetValueAsync("Governance:WspWindowOpenDate", $"{schemeYear}-01-01 00:00");
        var closeDateStr = await _configService.GetValueAsync("Governance:WspAnnualSubmissionDeadline", $"{schemeYear}-04-30 23:59");
        var extCutoffStr = await _configService.GetValueAsync("Governance:WspExtensionRequestDeadline", $"{schemeYear}-04-15");

        DateTime openingDate = ParseDateOrTimestamp(openDateStr, schemeYear, 1, 1, 0, 0);
        DateTime closingDate = ParseDateOrTimestamp(closeDateStr, schemeYear, 4, 30, 23, 59);
        DateTime extCutoff = ParseDateOrTimestamp(extCutoffStr, schemeYear, 4, 15, 23, 59);

        bool isOpen = now >= openingDate && now <= closingDate;
        string badge = !isOpen
            ? (now < openingDate ? "Upcoming" : "Closed")
            : "Active & Open";

        return new MgWindowScheduleDto
        {
            OpeningDate = openingDate,
            ClosingDate = closingDate,
            ExtensionCutoffDate = extCutoff,
            SchemeYear = schemeYear,
            IsWindowOpen = isOpen,
            StatusBadgeText = badge
        };
    }

    public async Task<MgWindowScheduleProposal?> GetActivePendingProposalAsync(int? schemeYear = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.MgWindowScheduleProposals.AsNoTracking()
            .Where(p => p.Status == "PendingReview");

        if (schemeYear.HasValue)
        {
            query = query.Where(p => p.SchemeYear == schemeYear.Value);
        }

        return await query.OrderByDescending(p => p.ProposedAt).FirstOrDefaultAsync();
    }

    public async Task<List<MgWindowScheduleProposal>> GetProposalHistoryAsync(int? schemeYear = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.MgWindowScheduleProposals.AsNoTracking();

        if (schemeYear.HasValue)
        {
            query = query.Where(p => p.SchemeYear == schemeYear.Value);
        }

        return await query.OrderByDescending(p => p.ProposedAt).Take(50).ToListAsync();
    }

    public async Task<MgWindowScheduleProposal> SubmitScheduleProposalAsync(
        int schemeYear,
        DateTime openingDate,
        DateTime closingDate,
        DateTime extensionCutoffDate,
        string justification,
        string? gazetteOrResolutionRef,
        string makerUserId,
        string makerUserName)
    {
        if (string.IsNullOrWhiteSpace(justification))
        {
            throw new ArgumentException("A statutory justification, circular reference, or rationale is required.", nameof(justification));
        }

        if (openingDate >= closingDate)
        {
            throw new ArgumentException("Window opening date and time must precede the standard closing deadline.", nameof(openingDate));
        }

        if (extensionCutoffDate > closingDate)
        {
            throw new ArgumentException("Extension filing cutoff date cannot be later than the standard closing deadline.", nameof(extensionCutoffDate));
        }

        using var db = await _contextFactory.CreateDbContextAsync();

        var pending = await db.MgWindowScheduleProposals
            .FirstOrDefaultAsync(p => p.SchemeYear == schemeYear && p.Status == "PendingReview");

        if (pending != null)
        {
            throw new InvalidOperationException($"A window schedule proposal (ID #{pending.Id}) is already pending independent review for Scheme Year {schemeYear}. Please adjudicate or withdraw the existing proposal first.");
        }

        var proposal = new MgWindowScheduleProposal
        {
            SchemeYear = schemeYear,
            ProposedOpeningDate = openingDate,
            ProposedClosingDate = closingDate,
            ProposedExtensionCutoffDate = extensionCutoffDate,
            Justification = justification.Trim(),
            GazetteOrResolutionRef = gazetteOrResolutionRef?.Trim(),
            Status = "PendingReview",
            ProposedByUserId = makerUserId,
            ProposedByUserName = string.IsNullOrWhiteSpace(makerUserName) ? makerUserId : makerUserName,
            ProposedAt = DateTime.UtcNow,
            AppliedToSystemConfig = false,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = makerUserId
        };

        db.MgWindowScheduleProposals.Add(proposal);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "MgWindowScheduleProposal", proposal.Id, "SubmitProposal", makerUserId, null, proposal);
        await db.SaveChangesAsync();

        return proposal;
    }

    public async Task<MgWindowScheduleProposal> AdjudicateProposalAsync(
        int proposalId,
        bool approve,
        string? comments,
        string checkerUserId,
        string checkerUserName,
        IList<string>? checkerRoles)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var proposal = await db.MgWindowScheduleProposals.FindAsync(proposalId);
        if (proposal == null)
        {
            throw new KeyNotFoundException($"MgWindowScheduleProposal with ID {proposalId} was not found.");
        }

        if (proposal.Status != "PendingReview")
        {
            throw new InvalidOperationException($"Proposal #{proposalId} is in status '{proposal.Status}' and cannot be adjudicated.");
        }

        if (!string.IsNullOrWhiteSpace(proposal.ProposedByUserId) &&
            string.Equals(proposal.ProposedByUserId, checkerUserId, StringComparison.OrdinalIgnoreCase))
        {
            throw new InvalidOperationException(
                "Segregation of Duties violation: The officer who submitted this window schedule proposal cannot adjudicate their own proposal. An independent reviewer or authorising official is required.");
        }

        var beforeState = new
        {
            proposal.Status,
            proposal.AdjudicatedByUserId,
            proposal.AdjudicatedByUserName,
            proposal.AdjudicatedAt,
            proposal.AdjudicationComments,
            proposal.AppliedToSystemConfig
        };

        proposal.AdjudicatedByUserId = checkerUserId;
        proposal.AdjudicatedByUserName = string.IsNullOrWhiteSpace(checkerUserName) ? checkerUserId : checkerUserName;
        proposal.AdjudicatedAt = DateTime.UtcNow;
        proposal.AdjudicationComments = comments;
        proposal.ModifiedAt = DateTime.UtcNow;
        proposal.ModifiedBy = checkerUserId;

        if (approve)
        {
            proposal.Status = "Approved";
            proposal.AppliedToSystemConfig = true;

            await _configService.SetValueAsync("Governance:WspWindowOpenDate", proposal.ProposedOpeningDate.ToString("yyyy-MM-dd HH:mm"), checkerUserId);
            await _configService.SetValueAsync("Governance:WspAnnualSubmissionDeadline", proposal.ProposedClosingDate.ToString("yyyy-MM-dd HH:mm"), checkerUserId);
            await _configService.SetValueAsync("Governance:WspExtensionRequestDeadline", proposal.ProposedExtensionCutoffDate.ToString("yyyy-MM-dd"), checkerUserId);
            await _configService.SetValueAsync("Governance:CurrentSchemeYear", proposal.SchemeYear.ToString(), checkerUserId);

            _audit.LogAction(db, "MgWindowScheduleProposal", proposal.Id, "ApproveSchedule", checkerUserId, beforeState, proposal);
        }
        else
        {
            if (string.IsNullOrWhiteSpace(comments))
            {
                throw new ArgumentException("Rejection comments are mandatory when rejecting a schedule proposal.", nameof(comments));
            }

            proposal.Status = "Rejected";
            proposal.AppliedToSystemConfig = false;

            _audit.LogAction(db, "MgWindowScheduleProposal", proposal.Id, "RejectSchedule", checkerUserId, beforeState, proposal);
        }

        await db.SaveChangesAsync();
        return proposal;
    }

    public async Task<MgWindowScheduleProposal> WithdrawProposalAsync(int proposalId, string userId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var proposal = await db.MgWindowScheduleProposals.FindAsync(proposalId);
        if (proposal == null)
        {
            throw new KeyNotFoundException($"MgWindowScheduleProposal with ID {proposalId} was not found.");
        }

        if (proposal.Status != "PendingReview")
        {
            throw new InvalidOperationException($"Proposal #{proposalId} is in status '{proposal.Status}' and cannot be withdrawn.");
        }

        var beforeState = new { proposal.Status };
        proposal.Status = "Withdrawn";
        proposal.ModifiedAt = DateTime.UtcNow;
        proposal.ModifiedBy = userId;

        _audit.LogAction(db, "MgWindowScheduleProposal", proposal.Id, "WithdrawProposal", userId, beforeState, proposal);
        await db.SaveChangesAsync();

        return proposal;
    }

    // =========================================================================================
    // OPTION A: MASTER MANDATORY GRANT WINDOWS & GOVERNANCE OPERATIONS
    // =========================================================================================

    public async Task<List<MgWindowListDto>> GetWindowsAsync(int? schemeYear = null, string? status = null, string? search = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.MgWindows.AsNoTracking().Include(w => w.OfoCodeSet).AsQueryable();

        if (schemeYear.HasValue)
        {
            query = query.Where(w => w.SchemeYear == schemeYear.Value);
        }

        if (!string.IsNullOrWhiteSpace(status))
        {
            query = query.Where(w => w.ApprovalStatus == status);
        }

        if (!string.IsNullOrWhiteSpace(search))
        {
            var s = search.Trim();
            query = query.Where(w =>
                w.WindowName.Contains(s) ||
                (w.GazetteReference != null && w.GazetteReference.Contains(s)) ||
                w.SchemeYear.ToString().Contains(s));
        }

        var windows = await query.OrderByDescending(w => w.SchemeYear).ThenByDescending(w => w.CreatedAt).ToListAsync();

        var result = new List<MgWindowListDto>();
        foreach (var w in windows)
        {
            int scopedCount = await db.MgWindowOfoCodes.CountAsync(o => o.MgWindowId == w.Id && o.IsActive);
            int priorityCount = await db.MgWindowOfoCodes.CountAsync(o => o.MgWindowId == w.Id && o.IsActive && o.IsPrioritySkill);

            result.Add(new MgWindowListDto
            {
                Id = w.Id,
                SchemeYear = w.SchemeYear,
                WindowName = w.WindowName,
                OpeningDate = w.OpeningDate,
                ClosingDate = w.ClosingDate,
                ExtensionCutoffDate = w.ExtensionCutoffDate,
                OfoCodeSetId = w.OfoCodeSetId,
                OfoCodeSetYear = w.OfoCodeSetYear,
                OfoCodeSetName = w.OfoCodeSet?.Name ?? (w.OfoCodeSetYear.HasValue ? $"{w.OfoCodeSetYear} OFO Set" : "Unspecified"),
                GazetteReference = w.GazetteReference,
                ApprovalStatus = w.ApprovalStatus,
                ProposedByUserName = w.ProposedByUserName,
                ProposedDate = w.ProposedDate,
                AdjudicatedByUserName = w.AdjudicatedByUserName,
                AdjudicatedDate = w.AdjudicatedDate,
                IsActive = w.IsActive,
                ScopedOccupationsCount = scopedCount,
                PrioritySkillsCount = priorityCount
            });
        }

        return result;
    }

    public async Task<MgWindowDetailDto?> GetWindowDetailAsync(int windowId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var w = await db.MgWindows.AsNoTracking()
            .Include(x => x.OfoCodeSet)
            .FirstOrDefaultAsync(x => x.Id == windowId);

        if (w == null) return null;

        int scopedCount = await db.MgWindowOfoCodes.CountAsync(o => o.MgWindowId == w.Id && o.IsActive);
        int priorityCount = await db.MgWindowOfoCodes.CountAsync(o => o.MgWindowId == w.Id && o.IsActive && o.IsPrioritySkill);

        return new MgWindowDetailDto
        {
            Id = w.Id,
            SchemeYear = w.SchemeYear,
            WindowName = w.WindowName,
            OpeningDate = w.OpeningDate,
            ClosingDate = w.ClosingDate,
            ExtensionCutoffDate = w.ExtensionCutoffDate,
            OfoCodeSetId = w.OfoCodeSetId,
            OfoCodeSetYear = w.OfoCodeSetYear,
            OfoCodeSetName = w.OfoCodeSet?.Name ?? (w.OfoCodeSetYear.HasValue ? $"{w.OfoCodeSetYear} OFO Set" : "Unspecified"),
            GazetteReference = w.GazetteReference,
            Justification = w.Justification,
            ApprovalStatus = w.ApprovalStatus,
            ProposedByUserId = w.ProposedByUserId,
            ProposedByUserName = w.ProposedByUserName,
            ProposedDate = w.ProposedDate,
            AdjudicatedByUserId = w.AdjudicatedByUserId,
            AdjudicatedByUserName = w.AdjudicatedByUserName,
            AdjudicatedDate = w.AdjudicatedDate,
            AdjudicationComments = w.AdjudicationComments,
            IsActive = w.IsActive,
            ScopedOccupationsCount = scopedCount,
            PrioritySkillsCount = priorityCount
        };
    }

    public async Task<MgWindow> CreateDraftWindowAsync(CreateMgWindowDto dto, string userId, string userName)
    {
        // INVARIANT 4: Statutory Date Validation
        if (dto.OpeningDate >= dto.ClosingDate)
        {
            throw new ArgumentException("Window opening date and time must precede the standard closing deadline.", nameof(dto.OpeningDate));
        }

        if (dto.ExtensionCutoffDate > dto.ClosingDate)
        {
            throw new ArgumentException("Extension filing cutoff date cannot be later than the standard closing deadline.", nameof(dto.ExtensionCutoffDate));
        }

        if (string.IsNullOrWhiteSpace(dto.Justification))
        {
            throw new ArgumentException("A statutory justification, circular reference, or operational rationale is required.", nameof(dto.Justification));
        }

        using var db = await _contextFactory.CreateDbContextAsync();

        var set = await db.OfoCodeSets.FindAsync(dto.OfoCodeSetId);
        if (set == null)
        {
            throw new KeyNotFoundException($"DHET Gazetted OFO Code Set #{dto.OfoCodeSetId} was not found.");
        }

        var window = new MgWindow
        {
            SchemeYear = dto.SchemeYear,
            WindowName = dto.WindowName.Trim(),
            OpeningDate = dto.OpeningDate,
            ClosingDate = dto.ClosingDate,
            ExtensionCutoffDate = dto.ExtensionCutoffDate,
            OfoCodeSetId = dto.OfoCodeSetId,
            OfoCodeSetYear = set.SetYear,
            GazetteReference = dto.GazetteReference?.Trim(),
            Justification = dto.Justification.Trim(),
            ApprovalStatus = "Draft",
            ProposedByUserId = userId,
            ProposedByUserName = string.IsNullOrWhiteSpace(userName) ? userId : userName,
            ProposedDate = DateTime.UtcNow,
            IsActive = false,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = userId
        };

        db.MgWindows.Add(window);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "MgWindow", window.Id, "CreateDraft", userId, null, window);
        await db.SaveChangesAsync();

        return window;
    }

    public async Task<MgWindow> UpdateDraftWindowAsync(int windowId, UpdateMgWindowDto dto, string userId, string userName)
    {
        // INVARIANT 4: Statutory Date Validation
        if (dto.OpeningDate >= dto.ClosingDate)
        {
            throw new ArgumentException("Window opening date and time must precede the standard closing deadline.", nameof(dto.OpeningDate));
        }

        if (dto.ExtensionCutoffDate > dto.ClosingDate)
        {
            throw new ArgumentException("Extension filing cutoff date cannot be later than the standard closing deadline.", nameof(dto.ExtensionCutoffDate));
        }

        if (string.IsNullOrWhiteSpace(dto.Justification))
        {
            throw new ArgumentException("A statutory justification is required.", nameof(dto.Justification));
        }

        using var db = await _contextFactory.CreateDbContextAsync();
        var window = await db.MgWindows.FindAsync(windowId);
        if (window == null)
        {
            throw new KeyNotFoundException($"Mandatory Grant Window #{windowId} was not found.");
        }

        if (window.ApprovalStatus != "Draft" && window.ApprovalStatus != "Rejected")
        {
            throw new InvalidOperationException($"Window #{windowId} is in stage '{window.ApprovalStatus}' and cannot be directly modified. Only Draft or Rejected windows can be modified.");
        }

        var set = await db.OfoCodeSets.FindAsync(dto.OfoCodeSetId);
        if (set == null)
        {
            throw new KeyNotFoundException($"DHET Gazetted OFO Code Set #{dto.OfoCodeSetId} was not found.");
        }

        var beforeState = new
        {
            window.WindowName,
            window.OpeningDate,
            window.ClosingDate,
            window.ExtensionCutoffDate,
            window.OfoCodeSetId,
            window.OfoCodeSetYear,
            window.GazetteReference,
            window.Justification
        };

        window.WindowName = dto.WindowName.Trim();
        window.OpeningDate = dto.OpeningDate;
        window.ClosingDate = dto.ClosingDate;
        window.ExtensionCutoffDate = dto.ExtensionCutoffDate;
        window.OfoCodeSetId = dto.OfoCodeSetId;
        window.OfoCodeSetYear = set.SetYear;
        window.GazetteReference = dto.GazetteReference?.Trim();
        window.Justification = dto.Justification.Trim();
        window.ModifiedAt = DateTime.UtcNow;
        window.ModifiedBy = userId;

        _audit.LogAction(db, "MgWindow", window.Id, "UpdateDraft", userId, beforeState, window);
        await db.SaveChangesAsync();

        return window;
    }

    public async Task<MgWindow> SubmitForReviewAsync(int windowId, string userId, string userName)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var window = await db.MgWindows.FindAsync(windowId);
        if (window == null)
        {
            throw new KeyNotFoundException($"Mandatory Grant Window #{windowId} was not found.");
        }

        if (window.ApprovalStatus != "Draft" && window.ApprovalStatus != "Rejected")
        {
            throw new InvalidOperationException($"Window #{windowId} is in stage '{window.ApprovalStatus}' and cannot be submitted for review.");
        }

        var beforeState = new { window.ApprovalStatus, window.ProposedByUserId, window.ProposedByUserName, window.ProposedDate };

        window.ApprovalStatus = "PendingReview";
        window.ProposedByUserId = userId;
        window.ProposedByUserName = string.IsNullOrWhiteSpace(userName) ? userId : userName;
        window.ProposedDate = DateTime.UtcNow;
        window.ModifiedAt = DateTime.UtcNow;
        window.ModifiedBy = userId;

        _audit.LogAction(db, "MgWindow", window.Id, "SubmitForReview", userId, beforeState, window);
        await db.SaveChangesAsync();

        return window;
    }

    public async Task<MgWindow> AdjudicateWindowAsync(int windowId, bool approve, string? comments, string checkerUserId, string checkerUserName)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var window = await db.MgWindows.FindAsync(windowId);
        if (window == null)
        {
            throw new KeyNotFoundException($"Mandatory Grant Window #{windowId} was not found.");
        }

        if (window.ApprovalStatus != "PendingReview")
        {
            throw new InvalidOperationException($"Window #{windowId} is in stage '{window.ApprovalStatus}' and cannot be adjudicated. Only windows pending independent review can be adjudicated.");
        }

        // INVARIANT 3: SEGREGATION OF DUTIES INVARIANT (PFMA Dual Authorisation)
        if (!string.IsNullOrWhiteSpace(window.ProposedByUserId) &&
            string.Equals(window.ProposedByUserId, checkerUserId, StringComparison.OrdinalIgnoreCase))
        {
            throw new InvalidOperationException(
                "Segregation of Duties Violation: The officer who proposed this Mandatory Grant window cannot approve it. An independent Reviewer or Executive official is required.");
        }

        var beforeState = new
        {
            window.ApprovalStatus,
            window.IsActive,
            window.AdjudicatedByUserId,
            window.AdjudicatedByUserName,
            window.AdjudicatedDate,
            window.AdjudicationComments
        };

        window.AdjudicatedByUserId = checkerUserId;
        window.AdjudicatedByUserName = string.IsNullOrWhiteSpace(checkerUserName) ? checkerUserId : checkerUserName;
        window.AdjudicatedDate = DateTime.UtcNow;
        window.AdjudicationComments = comments?.Trim();
        window.ModifiedAt = DateTime.UtcNow;
        window.ModifiedBy = checkerUserId;

        if (approve)
        {
            window.ApprovalStatus = "Approved";
            window.IsActive = true;

            // INVARIANT 5: Single Live Window & SystemConfig Synchronization
            // Archive any prior active window for this scheme year
            var previousActive = await db.MgWindows
                .Where(w => w.SchemeYear == window.SchemeYear && w.Id != window.Id && w.IsActive)
                .ToListAsync();

            foreach (var prev in previousActive)
            {
                prev.IsActive = false;
                prev.ApprovalStatus = "Archived";
                prev.ModifiedAt = DateTime.UtcNow;
                prev.ModifiedBy = checkerUserId;
            }

            // Sync active dates to SystemConfig
            await _configService.SetValueAsync("Governance:WspWindowOpenDate", window.OpeningDate.ToString("yyyy-MM-dd HH:mm"), checkerUserId);
            await _configService.SetValueAsync("Governance:WspAnnualSubmissionDeadline", window.ClosingDate.ToString("yyyy-MM-dd HH:mm"), checkerUserId);
            await _configService.SetValueAsync("Governance:WspExtensionRequestDeadline", window.ExtensionCutoffDate.ToString("yyyy-MM-dd"), checkerUserId);
            await _configService.SetValueAsync("Governance:CurrentSchemeYear", window.SchemeYear.ToString(), checkerUserId);

            _audit.LogAction(db, "MgWindow", window.Id, "ApproveSchedule", checkerUserId, beforeState, window);
        }
        else
        {
            if (string.IsNullOrWhiteSpace(comments))
            {
                throw new ArgumentException("Rejection comments are mandatory when rejecting a schedule proposal.", nameof(comments));
            }

            window.ApprovalStatus = "Rejected";
            window.IsActive = false;

            _audit.LogAction(db, "MgWindow", window.Id, "RejectSchedule", checkerUserId, beforeState, window);
        }

        await db.SaveChangesAsync();
        return window;
    }

    public async Task<MgWindow> WithdrawWindowProposalAsync(int windowId, string userId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var window = await db.MgWindows.FindAsync(windowId);
        if (window == null)
        {
            throw new KeyNotFoundException($"Mandatory Grant Window #{windowId} was not found.");
        }

        if (window.ApprovalStatus != "PendingReview")
        {
            throw new InvalidOperationException($"Window #{windowId} is in stage '{window.ApprovalStatus}' and cannot be withdrawn.");
        }

        var beforeState = new { window.ApprovalStatus };
        window.ApprovalStatus = "Draft";
        window.ModifiedAt = DateTime.UtcNow;
        window.ModifiedBy = userId;

        _audit.LogAction(db, "MgWindow", window.Id, "WithdrawProposal", userId, beforeState, window);
        await db.SaveChangesAsync();

        return window;
    }

    // =========================================================================================
    // OPTION A: CHILDGRID OFO OCCUPATIONAL SCOPE OPERATIONS
    // =========================================================================================

    public async Task<PagedResult<MgWindowOfoCodeDto>> GetScopedOfoCodesAsync(int windowId, OfoFilterQuery query)
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var q = db.MgWindowOfoCodes.AsNoTracking()
            .Where(o => o.MgWindowId == windowId && o.IsActive);

        if (query.PriorityOnly == true)
        {
            q = q.Where(o => o.IsPrioritySkill);
        }

        // Join with lookup.OfoCodeType and OfoCodeSetItem to project full details
        var window = await db.MgWindows.AsNoTracking().FirstOrDefaultAsync(w => w.Id == windowId);
        int setId = window?.OfoCodeSetId ?? 0;

        var fullQuery = from scoped in q
                        join ofo in db.OfoCodeTypes.AsNoTracking() on scoped.OfoCodeId equals ofo.Code into ofoJoin
                        from ofo in ofoJoin.DefaultIfEmpty()
                        join item in db.OfoCodeSetItems.AsNoTracking().Where(i => i.OfoCodeSetId == setId) on scoped.OfoCodeId equals item.OfoCodeId into itemJoin
                        from item in itemJoin.DefaultIfEmpty()
                        select new { scoped, ofo, item };

        if (!string.IsNullOrWhiteSpace(query.SearchTerm))
        {
            var s = query.SearchTerm.Trim();
            fullQuery = fullQuery.Where(x =>
                x.scoped.OfoCodeId.Contains(s) ||
                (x.ofo != null && x.ofo.Name.Contains(s)) ||
                (x.scoped.SectorNotes != null && x.scoped.SectorNotes.Contains(s)));
        }

        if (!string.IsNullOrWhiteSpace(query.MajorGroup))
        {
            fullQuery = fullQuery.Where(x => x.item != null && x.item.MajorGroup == query.MajorGroup);
        }

        if (query.TradeOnly == true)
        {
            fullQuery = fullQuery.Where(x => x.item != null && x.item.Trade);
        }

        int totalCount = await fullQuery.CountAsync();
        int pageIndex = Math.Max(0, query.PageNumber - 1);
        int pageSize = query.PageSize > 0 ? query.PageSize : 20;

        var items = await fullQuery
            .OrderBy(x => x.scoped.OfoCodeId)
            .Skip(pageIndex * pageSize)
            .Take(pageSize)
            .Select(x => new MgWindowOfoCodeDto
            {
                Id = x.scoped.Id,
                MgWindowId = x.scoped.MgWindowId,
                OfoCode = x.scoped.OfoCodeId,
                OccupationTitle = x.ofo != null ? x.ofo.Name : x.scoped.OfoCodeId,
                MajorGroup = x.item != null ? x.item.MajorGroup : (x.scoped.OfoCodeId.Length > 0 ? x.scoped.OfoCodeId.Substring(0, 1) : null),
                SubMajorGroup = x.item != null ? x.item.SubMajorGroup : (x.scoped.OfoCodeId.Length > 1 ? x.scoped.OfoCodeId.Substring(0, 2) : null),
                UnitGroup = x.item != null ? x.item.UnitGroup : (x.scoped.OfoCodeId.Length > 3 ? x.scoped.OfoCodeId.Substring(0, 4) : null),
                Trade = x.item != null && x.item.Trade,
                GreenOccupation = x.item != null && x.item.GreenOccupation,
                GreenSkill = x.item != null && x.item.GreenSkill,
                IsPrioritySkill = x.scoped.IsPrioritySkill,
                SectorNotes = x.scoped.SectorNotes,
                IsActiveInScope = x.scoped.IsActive,
                IsActiveInRegistry = x.ofo != null && x.ofo.Active
            })
            .ToListAsync();

        return new PagedResult<MgWindowOfoCodeDto>(items, totalCount, pageIndex, pageSize);
    }

    public async Task<int> BulkSyncFromSetAsync(int windowId, string userId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var window = await db.MgWindows.FindAsync(windowId);
        if (window == null)
        {
            throw new KeyNotFoundException($"Mandatory Grant Window #{windowId} was not found.");
        }

        if (!window.OfoCodeSetId.HasValue)
        {
            throw new InvalidOperationException($"Window #{windowId} does not have an associated OFO Code Set configured.");
        }

        int setId = window.OfoCodeSetId.Value;

        // INVARIANT 1: Strictly query codes that are members of the set AND active in lookup.OfoCodeType
        var validCodesQuery = from item in db.OfoCodeSetItems.AsNoTracking()
                              join ofo in db.OfoCodeTypes.AsNoTracking() on item.OfoCodeId equals ofo.Code
                              where item.OfoCodeSetId == setId && item.IsActiveInSet && ofo.Active
                              select item.OfoCodeId;

        var validCodeList = await validCodesQuery.Distinct().ToListAsync();

        // Query currently scoped codes
        var existingScoped = await db.MgWindowOfoCodes
            .Where(o => o.MgWindowId == windowId)
            .ToListAsync();

        var existingMap = existingScoped.ToDictionary(o => o.OfoCodeId, StringComparer.OrdinalIgnoreCase);

        int addedCount = 0;
        int reactivatedCount = 0;

        foreach (var code in validCodeList)
        {
            if (existingMap.TryGetValue(code, out var existing))
            {
                if (!existing.IsActive)
                {
                    existing.IsActive = true;
                    existing.ModifiedAt = DateTime.UtcNow;
                    existing.ModifiedBy = userId;
                    reactivatedCount++;
                }
            }
            else
            {
                db.MgWindowOfoCodes.Add(new MgWindowOfoCode
                {
                    MgWindowId = windowId,
                    OfoCodeId = code,
                    IsPrioritySkill = false,
                    IsActive = true,
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = userId
                });
                addedCount++;
            }
        }

        await db.SaveChangesAsync();

        _audit.LogAction(db, "MgWindow", windowId, "BulkSyncOfoCodes", userId, null, new
        {
            OfoCodeSetId = setId,
            AddedCount = addedCount,
            ReactivatedCount = reactivatedCount,
            TotalActiveInSet = validCodeList.Count
        });
        await db.SaveChangesAsync();

        return addedCount + reactivatedCount;
    }

    public async Task<MgWindowOfoCodeDto> AddScopedOfoCodeAsync(int windowId, AddScopedOfoCodeDto dto, string userId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var window = await db.MgWindows.FindAsync(windowId);
        if (window == null)
        {
            throw new KeyNotFoundException($"Mandatory Grant Window #{windowId} was not found.");
        }

        var code = dto.OfoCode.Trim();

        // INVARIANT 1: Active Code Enforcement
        var ofo = await db.OfoCodeTypes.AsNoTracking().FirstOrDefaultAsync(o => o.Code == code);
        if (ofo == null)
        {
            throw new KeyNotFoundException($"OFO Code '{code}' does not exist in the national registry.");
        }

        if (!ofo.Active)
        {
            throw new InvalidOperationException($"OFO Code '{code}' is marked INACTIVE/DEPRECATED in the national registry and cannot be scoped to a statutory submission window.");
        }

        // INVARIANT 2: Set Membership Enforcement
        if (window.OfoCodeSetId.HasValue)
        {
            bool inSet = await db.OfoCodeSetItems.AnyAsync(i =>
                i.OfoCodeSetId == window.OfoCodeSetId.Value &&
                i.OfoCodeId == code &&
                i.IsActiveInSet);

            if (!inSet)
            {
                throw new InvalidOperationException(
                    $"OFO Code '{code}' is not a gazetted member of the {window.OfoCodeSetYear} OFO Set (Set ID #{window.OfoCodeSetId}). It cannot be scoped to this window.");
            }
        }

        // Check if already scoped
        var existing = await db.MgWindowOfoCodes
            .FirstOrDefaultAsync(o => o.MgWindowId == windowId && o.OfoCodeId == code);

        if (existing != null)
        {
            if (existing.IsActive)
            {
                throw new InvalidOperationException($"OFO Code '{code}' ({ofo.Name}) is already scoped to this submission window.");
            }

            existing.IsActive = true;
            existing.IsPrioritySkill = dto.IsPrioritySkill;
            existing.SectorNotes = dto.SectorNotes?.Trim();
            existing.ModifiedAt = DateTime.UtcNow;
            existing.ModifiedBy = userId;

            _audit.LogAction(db, "MgWindowOfoCode", existing.Id, "ReactivateScopedOfoCode", userId, null, existing);
            await db.SaveChangesAsync();

            return new MgWindowOfoCodeDto
            {
                Id = existing.Id,
                MgWindowId = existing.MgWindowId,
                OfoCode = existing.OfoCodeId,
                OccupationTitle = ofo.Name,
                IsPrioritySkill = existing.IsPrioritySkill,
                SectorNotes = existing.SectorNotes,
                IsActiveInScope = true,
                IsActiveInRegistry = ofo.Active
            };
        }

        var newScoped = new MgWindowOfoCode
        {
            MgWindowId = windowId,
            OfoCodeId = code,
            IsPrioritySkill = dto.IsPrioritySkill,
            SectorNotes = dto.SectorNotes?.Trim(),
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = userId
        };

        db.MgWindowOfoCodes.Add(newScoped);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "MgWindowOfoCode", newScoped.Id, "AddScopedOfoCode", userId, null, newScoped);
        await db.SaveChangesAsync();

        return new MgWindowOfoCodeDto
        {
            Id = newScoped.Id,
            MgWindowId = newScoped.MgWindowId,
            OfoCode = newScoped.OfoCodeId,
            OccupationTitle = ofo.Name,
            IsPrioritySkill = newScoped.IsPrioritySkill,
            SectorNotes = newScoped.SectorNotes,
            IsActiveInScope = true,
            IsActiveInRegistry = ofo.Active
        };
    }

    public async Task<bool> TogglePrioritySkillAsync(int windowId, int windowOfoCodeId, bool isPrioritySkill, string userId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var item = await db.MgWindowOfoCodes
            .FirstOrDefaultAsync(o => o.Id == windowOfoCodeId && o.MgWindowId == windowId);

        if (item == null) return false;

        var before = new { item.IsPrioritySkill };
        item.IsPrioritySkill = isPrioritySkill;
        item.ModifiedAt = DateTime.UtcNow;
        item.ModifiedBy = userId;

        _audit.LogAction(db, "MgWindowOfoCode", item.Id, "TogglePrioritySkill", userId, before, item);
        await db.SaveChangesAsync();

        return true;
    }

    public async Task<bool> RemoveScopedOfoCodeAsync(int windowId, int windowOfoCodeId, string userId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var item = await db.MgWindowOfoCodes
            .FirstOrDefaultAsync(o => o.Id == windowOfoCodeId && o.MgWindowId == windowId);

        if (item == null) return false;

        var before = new { item.IsActive };
        item.IsActive = false;
        item.ModifiedAt = DateTime.UtcNow;
        item.ModifiedBy = userId;

        _audit.LogAction(db, "MgWindowOfoCode", item.Id, "RemoveScopedOfoCode", userId, before, item);
        await db.SaveChangesAsync();

        return true;
    }

    private static DateTime ParseDateOrTimestamp(string configValue, int schemeYear, int fallbackMonth, int fallbackDay, int fallbackHour, int fallbackMinute)
    {
        if (DateTime.TryParse(configValue, CultureInfo.InvariantCulture, DateTimeStyles.AssumeUniversal | DateTimeStyles.AdjustToUniversal, out var parsedFull))
        {
            return parsedFull;
        }

        if (configValue.Contains('-') && !configValue.Contains(':'))
        {
            var parts = configValue.Split('-');
            if (parts.Length == 2 && int.TryParse(parts[0], out var m) && int.TryParse(parts[1], out var d))
            {
                return new DateTime(schemeYear, m, d, fallbackHour, fallbackMinute, 0, DateTimeKind.Utc);
            }
        }

        return new DateTime(schemeYear, fallbackMonth, fallbackDay, fallbackHour, fallbackMinute, 0, DateTimeKind.Utc);
    }
}
