using System.Globalization;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
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

        // Check for existing pending proposal for this scheme year
        var pending = await db.MgWindowScheduleProposals
            .FirstOrDefaultAsync(p => p.SchemeYear == schemeYear && p.Status == "PendingReview");

        if (pending != null)
        {
            throw new InvalidOperationException($"A window schedule proposal (ID #{pending.Id}) is already pending Maker-Checker review for Scheme Year {schemeYear}. Please adjudicate or withdraw the existing proposal first.");
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

        // MAKER-CHECKER SEGREGATION OF DUTIES INVARIANT:
        // The Maker (proposer) cannot act as the Checker (approver/rejecter)
        if (!string.IsNullOrWhiteSpace(proposal.ProposedByUserId) &&
            string.Equals(proposal.ProposedByUserId, checkerUserId, StringComparison.OrdinalIgnoreCase))
        {
            throw new InvalidOperationException(
                "Maker-Checker Segregation of Duties violation: The officer who submitted this window schedule proposal cannot adjudicate their own proposal. An independent checker/approver is required.");
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

            // Commit to active SystemConfig
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
