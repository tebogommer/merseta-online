using System.Globalization;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Common.Models;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class WspService : IWspService
{
    public const decimal MandatoryGrantPercentage = 0.20m; // 20% of total SDL levy

    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;
    private readonly ISystemConfigurationService? _configService;

    public WspService(INsdmsDbContextFactory contextFactory, IAuditService audit, ISystemConfigurationService? configService = null)
    {
        _contextFactory = contextFactory;
        _audit = audit;
        _configService = configService;
    }

    public async Task<PagedResult<WspSubmission>> GetPagedSubmissionsAsync(PaginationQuery query, CancellationToken cancellationToken = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync(cancellationToken);
        var baseQuery = db.WspSubmissions
            .Include(w => w.Organisation)
            .AsNoTracking();

        if (query.FilterParams.TryGetValue("finYear", out var finYearStr) && int.TryParse(finYearStr, out var finYear))
        {
            baseQuery = baseQuery.Where(w => w.FinYear == finYear);
        }

        if (query.FilterParams.TryGetValue("organisationId", out var orgIdStr) && int.TryParse(orgIdStr, out var orgId))
        {
            baseQuery = baseQuery.Where(w => w.OrganisationId == orgId);
        }

        if (query.FilterParams.TryGetValue("status", out var statusVal) && !string.IsNullOrWhiteSpace(statusVal) && !statusVal.Equals("All", StringComparison.OrdinalIgnoreCase))
        {
            baseQuery = baseQuery.Where(w => w.WspApprovalStatusCode != null && w.WspApprovalStatusCode == statusVal);
        }

        if (!string.IsNullOrWhiteSpace(query.SearchText))
        {
            var s = query.SearchText.Trim();
            baseQuery = baseQuery.Where(w =>
                (w.ReferenceNumber != null && w.ReferenceNumber.Contains(s)) ||
                w.FinYear.ToString().Contains(s) ||
                (w.Organisation != null && w.Organisation.CompanyName != null && w.Organisation.CompanyName.Contains(s)) ||
                (w.Organisation != null && w.Organisation.SdlNumber != null && w.Organisation.SdlNumber.Contains(s)));
        }

        var totalCount = await baseQuery.CountAsync(cancellationToken);

        var pagedQuery = baseQuery.OrderByDescending(w => w.FinYear).ThenByDescending(w => w.Id);

        var items = await pagedQuery
            .Skip(query.PageIndex * query.PageSize)
            .Take(query.PageSize)
            .ToListAsync(cancellationToken);

        return new PagedResult<WspSubmission>(items, totalCount, query.PageIndex, query.PageSize);
    }

    public async Task<List<WspSubmission>> GetAllAsync(int? finYear = null, string? search = null, int? organisationId = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.WspSubmissions
            .Include(w => w.Organisation)
            .AsNoTracking();

        if (finYear.HasValue)
        {
            query = query.Where(w => w.FinYear == finYear.Value);
        }

        if (organisationId.HasValue)
        {
            query = query.Where(w => w.OrganisationId == organisationId.Value);
        }

        if (!string.IsNullOrWhiteSpace(search))
        {
            var s = search.Trim();
            query = query.Where(w =>
                w.ReferenceNumber.Contains(s) ||
                (w.Organisation != null && w.Organisation.CompanyName.Contains(s)));
        }

        return await query
            .OrderByDescending(w => w.FinYear)
            .ThenBy(w => w.ReferenceNumber)
            .ToListAsync();
    }

    public async Task<List<WspSubmission>> GetAllSubmissionsAsync(int? organisationId = null, int? finYear = null)
    {
        return await GetAllAsync(finYear, null, organisationId);
    }

    public async Task<WspSubmission?> GetByIdAsync(int id)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.WspSubmissions
            .Include(w => w.Organisation)
            .Include(w => w.EmploymentSummaries)
            .Include(w => w.TrainingPlans)
            .FirstOrDefaultAsync(w => w.Id == id);
    }

    public async Task<WspSubmission?> GetSubmissionByIdAsync(int id)
    {
        return await GetByIdAsync(id);
    }

    public async Task<WspSubmission> CreateAsync(WspSubmission submission, string currentUsername = "SYSTEM")
    {
        if (submission.FinYear <= 0)
        {
            submission.FinYear = DateTime.UtcNow.Year;
        }

        if (string.IsNullOrWhiteSpace(submission.ReferenceNumber))
        {
            submission.ReferenceNumber = $"WSP-{submission.FinYear}-{submission.OrganisationId}-{Guid.NewGuid().ToString("N")[..6].ToUpper()}";
        }

        if (string.IsNullOrWhiteSpace(submission.WspApprovalStatusCode))
        {
            submission.WspApprovalStatusCode = "Draft";
        }

        if (string.Equals(submission.WspApprovalStatusCode, "Submitted", StringComparison.OrdinalIgnoreCase))
        {
            var isOpen = await IsSubmissionWindowOpenAsync(submission.OrganisationId, submission.FinYear);
            if (!isOpen)
            {
                var deadline = await GetEffectiveSubmissionDeadlineAsync(submission.OrganisationId, submission.FinYear);
                throw new InvalidOperationException($"Mandatory Grant (WSP/ATR) submission window for scheme year {submission.FinYear} closed on {deadline:yyyy-MM-dd}. Submissions cannot be accepted after deadline without an approved extension.");
            }
        }

        using var db = await _contextFactory.CreateDbContextAsync();
        var strategy = db.Database.CreateExecutionStrategy();

        await strategy.ExecuteAsync(async () =>
        {
            Microsoft.EntityFrameworkCore.Storage.IDbContextTransaction? tx = null;
            if (db.Database.IsRelational())
            {
                tx = await db.Database.BeginTransactionAsync();
            }

            try
            {
                submission.CreatedAt = DateTime.UtcNow;
                submission.CreatedBy = currentUsername;

                db.WspSubmissions.Add(submission);
                await db.SaveChangesAsync();

                _audit.LogAction(db, "WspSubmission", submission.Id, "Create", currentUsername, null, submission);
                await db.SaveChangesAsync();

                if (tx != null) await tx.CommitAsync();
            }
            catch
            {
                if (tx != null) await tx.RollbackAsync();
                throw;
            }
            finally
            {
                tx?.Dispose();
            }
        });

        return submission;
    }

    public async Task<WspSubmission> CreateSubmissionAsync(WspSubmission submission, string currentUsername = "SYSTEM")
    {
        return await CreateAsync(submission, currentUsername);
    }

    public async Task<WspSubmission> UpdateAsync(WspSubmission submission, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.WspSubmissions.FindAsync(submission.Id);
        if (existing == null)
        {
            throw new KeyNotFoundException($"WspSubmission with ID {submission.Id} was not found.");
        }

        // Hydrate client RowVersion concurrency token so EF Core actively checks for concurrency conflicts
        if (submission.RowVersion != null && submission.RowVersion.Length > 0)
        {
            db.Entry(existing).Property(e => e.RowVersion).OriginalValue = submission.RowVersion;
        }

        if ((string.Equals(submission.WspApprovalStatusCode, "Submitted", StringComparison.OrdinalIgnoreCase) ||
             string.Equals(submission.StatusCode, "Submitted", StringComparison.OrdinalIgnoreCase)) &&
            !string.Equals(existing.WspApprovalStatusCode, "Submitted", StringComparison.OrdinalIgnoreCase) &&
            !string.Equals(existing.StatusCode, "Submitted", StringComparison.OrdinalIgnoreCase))
        {
            var isOpen = await IsSubmissionWindowOpenAsync(existing.OrganisationId, existing.FinYear);
            if (!isOpen)
            {
                var deadline = await GetEffectiveSubmissionDeadlineAsync(existing.OrganisationId, existing.FinYear);
                throw new InvalidOperationException($"Mandatory Grant (WSP/ATR) submission window for scheme year {existing.FinYear} closed on {deadline:yyyy-MM-dd}. Submissions cannot be accepted after deadline without an approved extension.");
            }
        }

        var beforeState = new
        {
            existing.FinYear,
            existing.ReferenceNumber,
            existing.WspApprovalStatusCode,
            existing.PlannedTrainingBudget,
            existing.EmployeeCount,
            existing.SubmissionDate
        };

        existing.FinYear = submission.FinYear;
        existing.ReferenceNumber = submission.ReferenceNumber;
        existing.WspApprovalStatusCode = submission.WspApprovalStatusCode;
        existing.PlannedTrainingBudget = submission.PlannedTrainingBudget;
        existing.EmployeeCount = submission.EmployeeCount;
        existing.SubmissionDate = submission.SubmissionDate;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        _audit.LogAction(db, "WspSubmission", existing.Id, "Update", currentUsername, beforeState, existing);
        await db.SaveChangesAsync();

        return existing;
    }

    public async Task<WspSubmission> UpdateSubmissionAsync(WspSubmission submission, string currentUsername = "SYSTEM")
    {
        return await UpdateAsync(submission, currentUsername);
    }

    public async Task<WspSubmission> SaveAsync(WspSubmission submission, string currentUsername = "SYSTEM")
    {
        if (submission.Id == 0)
        {
            return await CreateAsync(submission, currentUsername);
        }

        return await UpdateAsync(submission, currentUsername);
    }

    public async Task<WspSubmission> UpdateSubmissionStatusAsync(int id, string statusCode, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.WspSubmissions.FindAsync(id);
        if (existing == null)
        {
            throw new KeyNotFoundException($"WspSubmission with ID {id} was not found.");
        }

        if (statusCode.Equals("Submitted", StringComparison.OrdinalIgnoreCase))
        {
            var isOpen = await IsSubmissionWindowOpenAsync(existing.OrganisationId, existing.FinYear);
            if (!isOpen)
            {
                var deadline = await GetEffectiveSubmissionDeadlineAsync(existing.OrganisationId, existing.FinYear);
                throw new InvalidOperationException($"Mandatory Grant (WSP/ATR) submission window for scheme year {existing.FinYear} closed on {deadline:yyyy-MM-dd}. Submissions cannot be accepted after deadline without an approved extension.");
            }
        }

        var beforeState = new { existing.WspApprovalStatusCode, existing.SubmissionDate };

        existing.WspApprovalStatusCode = statusCode;
        if (statusCode.Equals("Submitted", StringComparison.OrdinalIgnoreCase) && !existing.SubmissionDate.HasValue)
        {
            existing.SubmissionDate = DateTime.UtcNow;
        }

        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        _audit.LogAction(db, "WspSubmission", existing.Id, "UpdateStatus", currentUsername, beforeState, existing);
        await db.SaveChangesAsync();

        return existing;
    }

    public async Task<bool> DeleteAsync(int id, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var submission = await db.WspSubmissions.FindAsync(id);
        if (submission == null)
        {
            return false;
        }

        var beforeState = new
        {
            submission.Id,
            submission.OrganisationId,
            submission.FinYear,
            submission.ReferenceNumber,
            submission.WspApprovalStatusCode
        };

        db.WspSubmissions.Remove(submission);
        _audit.LogAction(db, "WspSubmission", id, "Delete", currentUsername, beforeState, null);
        await db.SaveChangesAsync();

        return true;
    }

    public async Task<bool> DeleteSubmissionAsync(int id, string currentUsername = "SYSTEM")
    {
        return await DeleteAsync(id, currentUsername);
    }

    public async Task<WspEmploymentSummary> AddEmploymentSummaryAsync(WspEmploymentSummary summary, string currentUsername = "SYSTEM")
    {
        if (summary.WspSubmissionId <= 0)
        {
            throw new ArgumentException("A valid WspSubmissionId must be specified.");
        }

        summary.TotalEmployees = summary.MaleAfrican + summary.FemaleAfrican +
                                 summary.MaleColoured + summary.FemaleColoured +
                                 summary.MaleIndian + summary.FemaleIndian +
                                 summary.MaleWhite + summary.FemaleWhite;

        using var db = await _contextFactory.CreateDbContextAsync();
        summary.CreatedAt = DateTime.UtcNow;
        summary.CreatedBy = currentUsername;

        db.WspEmploymentSummaries.Add(summary);
        await db.SaveChangesAsync();

        var summaries = await db.WspEmploymentSummaries
            .Where(e => e.WspSubmissionId == summary.WspSubmissionId)
            .ToListAsync();
        var totalEmployees = summaries.Sum(s => s.TotalEmployees);
        var sub = await db.WspSubmissions.FindAsync(summary.WspSubmissionId);
        if (sub != null)
        {
            sub.EmployeeCount = totalEmployees;
            sub.ModifiedAt = DateTime.UtcNow;
        }

        _audit.LogAction(db, "WspEmploymentSummary", summary.Id, "AddEmploymentSummary", currentUsername, null, summary);
        await db.SaveChangesAsync();

        return summary;
    }

    public async Task<WspEmploymentSummary> AddEmploymentSummaryAsync(int submissionId, WspEmploymentSummary summary, string currentUsername = "SYSTEM")
    {
        summary.WspSubmissionId = submissionId;
        return await AddEmploymentSummaryAsync(summary, currentUsername);
    }

    public async Task<List<WspEmploymentSummary>> GetEmploymentSummariesAsync(int submissionId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.WspEmploymentSummaries
            .Where(e => e.WspSubmissionId == submissionId)
            .OrderBy(e => e.OfoCode)
            .ToListAsync();
    }

    public async Task<int> RecalculateEmploymentTotalsAsync(int submissionId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var submission = await db.WspSubmissions.FindAsync(submissionId);
        if (submission == null)
        {
            throw new KeyNotFoundException($"WspSubmission with ID {submissionId} was not found.");
        }

        var summaries = await db.WspEmploymentSummaries
            .Where(e => e.WspSubmissionId == submissionId)
            .ToListAsync();

        var totalEmployees = summaries.Sum(s => s.TotalEmployees);
        submission.EmployeeCount = totalEmployees;
        submission.ModifiedAt = DateTime.UtcNow;

        await db.SaveChangesAsync();

        return totalEmployees;
    }

    public async Task<bool> RemoveEmploymentSummaryAsync(int summaryId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var summary = await db.WspEmploymentSummaries.FindAsync(summaryId);
        if (summary == null)
        {
            return false;
        }

        var submissionId = summary.WspSubmissionId;
        var beforeState = new { summary.Id, summary.WspSubmissionId, summary.TotalEmployees };

        db.WspEmploymentSummaries.Remove(summary);
        await db.SaveChangesAsync();

        var summaries = await db.WspEmploymentSummaries
            .Where(e => e.WspSubmissionId == submissionId)
            .ToListAsync();
        var totalEmployees = summaries.Sum(s => s.TotalEmployees);
        var sub = await db.WspSubmissions.FindAsync(submissionId);
        if (sub != null)
        {
            sub.EmployeeCount = totalEmployees;
            sub.ModifiedAt = DateTime.UtcNow;
        }

        _audit.LogAction(db, "WspEmploymentSummary", summaryId, "RemoveEmploymentSummary", currentUsername, beforeState, null);
        await db.SaveChangesAsync();

        return true;
    }

    public async Task<WspTrainingPlan> AddTrainingPlanAsync(WspTrainingPlan plan, string currentUsername = "SYSTEM")
    {
        if (plan.WspSubmissionId <= 0)
        {
            throw new ArgumentException("A valid WspSubmissionId must be specified.");
        }

        using var db = await _contextFactory.CreateDbContextAsync();
        var strategy = db.Database.CreateExecutionStrategy();

        return await strategy.ExecuteAsync(async () =>
        {
            Microsoft.EntityFrameworkCore.Storage.IDbContextTransaction? tx = null;
            if (db.Database.IsRelational())
            {
                tx = await db.Database.BeginTransactionAsync();
            }

            try
            {
                plan.CreatedAt = DateTime.UtcNow;
                plan.CreatedBy = currentUsername;

                db.WspTrainingPlans.Add(plan);
                await db.SaveChangesAsync();

                var plans = await db.WspTrainingPlans
                    .Where(p => p.WspSubmissionId == plan.WspSubmissionId)
                    .ToListAsync();
                var totalBudget = plans.Sum(p => p.EstimatedCost);
                var sub = await db.WspSubmissions.FindAsync(plan.WspSubmissionId);
                if (sub != null)
                {
                    sub.PlannedTrainingBudget = totalBudget;
                    sub.ModifiedAt = DateTime.UtcNow;
                }

                _audit.LogAction(db, "WspTrainingPlan", plan.Id, "AddTrainingPlan", currentUsername, null, plan);
                await db.SaveChangesAsync();

                if (tx != null) await tx.CommitAsync();

                return plan;
            }
            catch
            {
                if (tx != null) await tx.RollbackAsync();
                throw;
            }
            finally
            {
                tx?.Dispose();
            }
        });
    }

    public async Task<WspTrainingPlan> AddTrainingPlanAsync(int submissionId, WspTrainingPlan plan, string currentUsername = "SYSTEM")
    {
        plan.WspSubmissionId = submissionId;
        return await AddTrainingPlanAsync(plan, currentUsername);
    }

    public async Task<List<WspTrainingPlan>> GetTrainingPlansAsync(int submissionId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.WspTrainingPlans
            .Where(p => p.WspSubmissionId == submissionId)
            .OrderBy(p => p.ProgrammeTypeCode)
            .ToListAsync();
    }

    public async Task<decimal> RecalculateTrainingPlanBudgetAsync(int submissionId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var submission = await db.WspSubmissions.FindAsync(submissionId);
        if (submission == null)
        {
            throw new KeyNotFoundException($"WspSubmission with ID {submissionId} was not found.");
        }

        var plans = await db.WspTrainingPlans
            .Where(p => p.WspSubmissionId == submissionId)
            .ToListAsync();

        var totalBudget = plans.Sum(p => p.EstimatedCost);
        submission.PlannedTrainingBudget = totalBudget;
        submission.ModifiedAt = DateTime.UtcNow;

        await db.SaveChangesAsync();

        return totalBudget;
    }

    public async Task<bool> RemoveTrainingPlanAsync(long planId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var plan = await db.WspTrainingPlans.FindAsync(planId);
        if (plan == null)
        {
            return false;
        }

        var submissionId = plan.WspSubmissionId;
        var beforeState = new { plan.Id, plan.WspSubmissionId, plan.EstimatedCost };

        db.WspTrainingPlans.Remove(plan);
        await db.SaveChangesAsync();

        var plans = await db.WspTrainingPlans
            .Where(p => p.WspSubmissionId == submissionId)
            .ToListAsync();
        var totalBudget = plans.Sum(p => p.EstimatedCost);
        var sub = await db.WspSubmissions.FindAsync(submissionId);
        if (sub != null)
        {
            sub.PlannedTrainingBudget = totalBudget;
            sub.ModifiedAt = DateTime.UtcNow;
        }

        _audit.LogAction(db, "WspTrainingPlan", planId, "RemoveTrainingPlan", currentUsername, beforeState, null);
        await db.SaveChangesAsync();

        return true;
    }

    public async Task<decimal> CalculateMandatoryGrantClaimAsync(int wspSubmissionId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var submission = await db.WspSubmissions
            .Include(w => w.Organisation)
            .FirstOrDefaultAsync(w => w.Id == wspSubmissionId);

        if (submission == null)
        {
            throw new KeyNotFoundException($"WspSubmission with ID {wspSubmissionId} was not found.");
        }

        var sdlNumber = submission.Organisation?.SdlNumber;
        if (string.IsNullOrWhiteSpace(sdlNumber))
        {
            var org = await db.Organisations.FindAsync(submission.OrganisationId);
            sdlNumber = org?.SdlNumber;
        }

        if (string.IsNullOrWhiteSpace(sdlNumber))
        {
            return 0m;
        }

        var levyLines = await db.LevyFileLines
            .Where(l => l.SdlNumber == sdlNumber)
            .ToListAsync();

        if (!levyLines.Any())
        {
            return 0m;
        }

        var mandatorySum = levyLines.Sum(l => l.MandatoryLevyAmount > 0 ? l.MandatoryLevyAmount : Math.Round(l.TotalLevyAmount * MandatoryGrantPercentage, 2));

        return mandatorySum;
    }

    public decimal CalculateMandatoryGrant(decimal totalLevyPaid)
    {
        if (totalLevyPaid <= 0)
        {
            return 0m;
        }

        return Math.Round(totalLevyPaid * MandatoryGrantPercentage, 2);
    }

    public bool ValidateMandatoryGrantEligibility(WspSubmission submission)
    {
        if (submission == null)
        {
            return false;
        }

        var isValidStatus = string.Equals(submission.WspApprovalStatusCode, "Submitted", StringComparison.OrdinalIgnoreCase) ||
                            string.Equals(submission.WspApprovalStatusCode, "Approved", StringComparison.OrdinalIgnoreCase) ||
                            string.Equals(submission.WspApprovalStatusCode, "APPROVED", StringComparison.OrdinalIgnoreCase) ||
                            string.Equals(submission.WspApprovalStatusCode, "SUBMITTED", StringComparison.OrdinalIgnoreCase);

        return isValidStatus && submission.EmployeeCount > 0 && submission.PlannedTrainingBudget > 0;
    }

    // Statutory Window & Extension Requests
    public async Task<WspWindowStatusDto> GetWindowStatusAsync(int? organisationId = null, int? schemeYear = null, CancellationToken ct = default)
    {
        // 1. Resolve schemeYear: default to DateTime.UtcNow.Year or Governance:CurrentSchemeYear
        int resolvedSchemeYear = schemeYear.GetValueOrDefault();
        if (resolvedSchemeYear <= 0)
        {
            if (_configService != null)
            {
                var configuredYearStr = await _configService.GetValueAsync("Governance:CurrentSchemeYear", string.Empty);
                if (int.TryParse(configuredYearStr, out var parsedYear) && parsedYear > 0)
                {
                    resolvedSchemeYear = parsedYear;
                }
            }
        }
        if (resolvedSchemeYear <= 0)
        {
            resolvedSchemeYear = DateTime.UtcNow.Year;
        }

        // 2. Resolve OpeningDate from Governance:WspWindowOpenDate (fallback: Jan 1 00:00:00 UTC)
        var openConfig = "01-01";
        if (_configService != null)
        {
            openConfig = await _configService.GetValueAsync("Governance:WspWindowOpenDate", "01-01") ?? "01-01";
        }
        DateTime openingDate = ParseWindowDate(openConfig, resolvedSchemeYear, fallbackMonth: 1, fallbackDay: 1, fallbackHour: 0, fallbackMinute: 0, fallbackSecond: 0);

        // 3. Resolve ClosingDate from Governance:WspAnnualSubmissionDeadline (fallback: Apr 30 23:59:59 UTC)
        var closeConfig = "04-30";
        if (_configService != null)
        {
            closeConfig = await _configService.GetValueAsync("Governance:WspAnnualSubmissionDeadline", "04-30") ?? "04-30";
        }
        DateTime closingDate = ParseWindowDate(closeConfig, resolvedSchemeYear, fallbackMonth: 4, fallbackDay: 30, fallbackHour: 23, fallbackMinute: 59, fallbackSecond: 59);

        // 4. Resolve Organisation-specific approved extension
        bool hasApprovedExtension = false;
        DateTime? grantedExtensionDate = null;
        string? extensionReferenceNumber = null;
        string? organisationName = null;
        DateTime effectiveDeadline = closingDate;

        if (organisationId.HasValue && organisationId.Value > 0)
        {
            using var db = await _contextFactory.CreateDbContextAsync(ct);
            organisationName = await db.Organisations
                .AsNoTracking()
                .Where(o => o.Id == organisationId.Value)
                .Select(o => o.CompanyName)
                .FirstOrDefaultAsync(ct);

            var approvedExtension = await db.WspExtensionRequests
                .AsNoTracking()
                .Where(r => r.OrganisationId == organisationId.Value
                            && r.SchemeYear == resolvedSchemeYear
                            && r.ApprovalStatusCode == "Approved"
                            && r.GrantedExtensionDate.HasValue)
                .OrderByDescending(r => r.GrantedExtensionDate)
                .FirstOrDefaultAsync(ct);

            if (approvedExtension?.GrantedExtensionDate != null)
            {
                var rawGrantedDate = approvedExtension.GrantedExtensionDate.Value;
                var extensionDeadline = new DateTime(rawGrantedDate.Year, rawGrantedDate.Month, rawGrantedDate.Day, 23, 59, 59, DateTimeKind.Utc);
                if (extensionDeadline > closingDate)
                {
                    hasApprovedExtension = true;
                    grantedExtensionDate = extensionDeadline;
                    extensionReferenceNumber = approvedExtension.ApplicationReference;
                    effectiveDeadline = extensionDeadline;
                }
            }
        }

        // 5. Calculate window state and urgency tier
        var now = DateTime.UtcNow;
        bool isOpen = false;
        bool isUpcoming = false;
        bool isClosed = false;
        TimeSpan timeRemaining = TimeSpan.Zero;
        WspUrgencyTier urgencyTier;

        if (now < openingDate)
        {
            isUpcoming = true;
            isOpen = false;
            isClosed = false;
            timeRemaining = openingDate - now;
            urgencyTier = WspUrgencyTier.Upcoming;
        }
        else if (now > effectiveDeadline)
        {
            isUpcoming = false;
            isOpen = false;
            isClosed = true;
            timeRemaining = TimeSpan.Zero;
            urgencyTier = WspUrgencyTier.Closed;
        }
        else
        {
            isOpen = true;
            isUpcoming = false;
            isClosed = false;
            timeRemaining = effectiveDeadline - now;

            if (now > closingDate && hasApprovedExtension)
            {
                urgencyTier = WspUrgencyTier.ExtensionActive;
            }
            else if (timeRemaining <= TimeSpan.FromHours(72))
            {
                urgencyTier = WspUrgencyTier.Critical;
            }
            else if (timeRemaining <= TimeSpan.FromDays(14))
            {
                urgencyTier = WspUrgencyTier.Warning;
            }
            else
            {
                urgencyTier = WspUrgencyTier.Normal;
            }
        }

        // 6. Format TimeRemaining and StatusBadgeText
        string statusBadgeText;
        string formattedTimeRemaining;

        switch (urgencyTier)
        {
            case WspUrgencyTier.Upcoming:
                statusBadgeText = "Upcoming Window";
                formattedTimeRemaining = timeRemaining.TotalDays >= 1
                    ? $"{timeRemaining.Days}d {timeRemaining.Hours:D2}h {timeRemaining.Minutes:D2}m remaining"
                    : $"{timeRemaining.Hours:D2}h {timeRemaining.Minutes:D2}m remaining";
                break;

            case WspUrgencyTier.Closed:
                statusBadgeText = "Window Closed";
                formattedTimeRemaining = $"Window Closed on {effectiveDeadline:dd MMMM yyyy}";
                break;

            case WspUrgencyTier.ExtensionActive:
                statusBadgeText = "Approved Extension Active";
                formattedTimeRemaining = timeRemaining.TotalDays >= 1
                    ? $"{timeRemaining.Days}d {timeRemaining.Hours:D2}h {timeRemaining.Minutes:D2}m remaining"
                    : $"{timeRemaining.Hours:D2}h {timeRemaining.Minutes:D2}m remaining";
                break;

            case WspUrgencyTier.Critical:
                statusBadgeText = "Critical (<= 72 Hours)";
                formattedTimeRemaining = timeRemaining.TotalDays >= 1
                    ? $"{timeRemaining.Days}d {timeRemaining.Hours:D2}h {timeRemaining.Minutes:D2}m remaining"
                    : $"{timeRemaining.Hours:D2}h {timeRemaining.Minutes:D2}m remaining";
                break;

            case WspUrgencyTier.Warning:
                statusBadgeText = "Warning (<= 14 Days)";
                formattedTimeRemaining = $"{timeRemaining.Days}d {timeRemaining.Hours:D2}h {timeRemaining.Minutes:D2}m remaining";
                break;

            case WspUrgencyTier.Normal:
            default:
                statusBadgeText = "Window Active";
                formattedTimeRemaining = $"{timeRemaining.Days}d {timeRemaining.Hours:D2}h {timeRemaining.Minutes:D2}m remaining";
                break;
        }

        string displayMessage = urgencyTier switch
        {
            WspUrgencyTier.Upcoming => $"The Mandatory Grant (WSP/ATR) submission window for Scheme Year {resolvedSchemeYear} opens on {openingDate:dd MMMM yyyy HH:mm} UTC.",
            WspUrgencyTier.Closed => $"The Mandatory Grant submission window for Scheme Year {resolvedSchemeYear} closed on {effectiveDeadline:dd MMMM yyyy HH:mm} UTC.",
            WspUrgencyTier.ExtensionActive => $"Approved Regulation 4(2) extension is active until {effectiveDeadline:dd MMMM yyyy HH:mm} UTC (Ref: {extensionReferenceNumber}).",
            WspUrgencyTier.Critical => $"Statutory deadline is imminent. Submission closes in {formattedTimeRemaining} on {effectiveDeadline:dd MMMM yyyy HH:mm} UTC.",
            WspUrgencyTier.Warning => $"Submission window is closing soon. {formattedTimeRemaining} remaining before deadline on {effectiveDeadline:dd MMMM yyyy HH:mm} UTC.",
            _ => $"Mandatory Grant submission window is open until {effectiveDeadline:dd MMMM yyyy HH:mm} UTC."
        };

        return new WspWindowStatusDto
        {
            SchemeYear = resolvedSchemeYear,
            OpeningDate = openingDate,
            ClosingDate = closingDate,
            EffectiveDeadline = effectiveDeadline,
            HasApprovedExtension = hasApprovedExtension,
            GrantedExtensionDate = grantedExtensionDate,
            ExtensionReferenceNumber = extensionReferenceNumber,
            IsOpen = isOpen,
            IsUpcoming = isUpcoming,
            IsClosed = isClosed,
            TimeRemaining = timeRemaining,
            UrgencyTier = urgencyTier,
            StatusBadgeText = statusBadgeText,
            FormattedTimeRemaining = formattedTimeRemaining,
            OrganisationId = organisationId,
            OrganisationName = organisationName,
            DisplayMessage = displayMessage
        };
    }

    private static DateTime ParseWindowDate(string configValue, int schemeYear, int fallbackMonth, int fallbackDay, int fallbackHour, int fallbackMinute, int fallbackSecond)
    {
        if (string.IsNullOrWhiteSpace(configValue))
        {
            return new DateTime(schemeYear, fallbackMonth, fallbackDay, fallbackHour, fallbackMinute, fallbackSecond, DateTimeKind.Utc);
        }

        var trimmed = configValue.Trim();

        // Month-Day format: "MM-dd" (e.g. "01-01" or "04-30")
        if (trimmed.Length <= 5 && trimmed.Contains('-') && !trimmed.Contains(':'))
        {
            var parts = trimmed.Split('-');
            if (parts.Length == 2 && int.TryParse(parts[0], out var m) && int.TryParse(parts[1], out var d))
            {
                return new DateTime(schemeYear, m, d, fallbackHour, fallbackMinute, fallbackSecond, DateTimeKind.Utc);
            }
        }

        if (DateTime.TryParse(trimmed, CultureInfo.InvariantCulture, DateTimeStyles.AssumeUniversal | DateTimeStyles.AdjustToUniversal, out var parsed))
        {
            int year = parsed.Year > 1900 ? parsed.Year : schemeYear;
            int hour = parsed.Hour;
            int minute = parsed.Minute;
            int second = parsed.Second;

            // If time was 00:00:00 and fallback was end-of-day 23:59:59 (e.g. "2026-04-30" date-only)
            if (fallbackHour == 23 && hour == 0 && minute == 0 && second == 0 && !trimmed.Contains(':'))
            {
                hour = fallbackHour;
                minute = fallbackMinute;
                second = fallbackSecond;
            }

            return new DateTime(year, parsed.Month, parsed.Day, hour, minute, second, DateTimeKind.Utc);
        }

        if (trimmed.Contains('-'))
        {
            var parts = trimmed.Split('-');
            if (parts.Length == 3 && int.TryParse(parts[0], out var y) && int.TryParse(parts[1], out var m) && int.TryParse(parts[2], out var d))
            {
                return new DateTime(y, m, d, fallbackHour, fallbackMinute, fallbackSecond, DateTimeKind.Utc);
            }
        }

        return new DateTime(schemeYear, fallbackMonth, fallbackDay, fallbackHour, fallbackMinute, fallbackSecond, DateTimeKind.Utc);
    }

    public async Task<DateTime> GetEffectiveSubmissionDeadlineAsync(int organisationId, int schemeYear)
    {
        var status = await GetWindowStatusAsync(organisationId, schemeYear);
        return status.EffectiveDeadline;
    }

    public async Task<bool> IsSubmissionWindowOpenAsync(int organisationId, int schemeYear)
    {
        var status = await GetWindowStatusAsync(organisationId, schemeYear);
        return status.IsOpen;
    }

    public async Task<List<WspExtensionRequest>> GetExtensionRequestsAsync(int? organisationId = null, int? schemeYear = null, string? statusCode = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.WspExtensionRequests
            .Include(r => r.Organisation)
            .Include(r => r.WspSubmission)
            .AsQueryable();

        if (organisationId.HasValue && organisationId.Value > 0)
        {
            query = query.Where(r => r.OrganisationId == organisationId.Value);
        }

        if (schemeYear.HasValue && schemeYear.Value > 0)
        {
            query = query.Where(r => r.SchemeYear == schemeYear.Value);
        }

        if (!string.IsNullOrWhiteSpace(statusCode) && !statusCode.Equals("All", StringComparison.OrdinalIgnoreCase))
        {
            query = query.Where(r => r.ApprovalStatusCode == statusCode);
        }

        return await query.OrderByDescending(r => r.CreatedAt).ToListAsync();
    }

    public async Task<WspExtensionRequest?> GetExtensionRequestByIdAsync(int id)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.WspExtensionRequests
            .Include(r => r.Organisation)
            .Include(r => r.WspSubmission)
            .FirstOrDefaultAsync(r => r.Id == id);
    }

    public async Task<WspExtensionRequest> SubmitExtensionRequestAsync(WspExtensionRequest request, string currentUsername = "SYSTEM")
    {
        if (request == null) throw new ArgumentNullException(nameof(request));
        if (request.OrganisationId <= 0) throw new ArgumentException("A valid Organisation is required.", nameof(request));
        if (request.SchemeYear <= 0) request.SchemeYear = DateTime.UtcNow.Year;
        if (string.IsNullOrWhiteSpace(request.StatutoryMotivation)) throw new ArgumentException("Detailed statutory motivation is required.", nameof(request));
        if (request.RequestedExtensionDate == default) throw new ArgumentException("Requested extension date is required.", nameof(request));

        var maxAllowedDate = new DateTime(request.SchemeYear, 5, 31, 23, 59, 59, DateTimeKind.Utc);
        if (request.RequestedExtensionDate > maxAllowedDate)
        {
            request.RequestedExtensionDate = maxAllowedDate;
        }

        using var db = await _contextFactory.CreateDbContextAsync();

        var existingActive = await db.WspExtensionRequests
            .AnyAsync(r => r.OrganisationId == request.OrganisationId
                        && r.SchemeYear == request.SchemeYear
                        && (r.ApprovalStatusCode == "PendingReview" || r.ApprovalStatusCode == "Recommended"));

        if (existingActive)
        {
            throw new InvalidOperationException($"An active extension request is already under review for organisation ID {request.OrganisationId} in scheme year {request.SchemeYear}.");
        }

        if (string.IsNullOrWhiteSpace(request.ApplicationReference))
        {
            request.ApplicationReference = $"EXT-{request.SchemeYear}-{DateTime.UtcNow:MMdd}-{request.OrganisationId:D3}";
        }

        request.ApprovalStatusCode = "PendingReview";
        request.SubmittedByUserId = currentUsername;
        request.CreatedAt = DateTime.UtcNow;
        request.CreatedBy = currentUsername;

        db.WspExtensionRequests.Add(request);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "WspExtensionRequest", request.Id, "Submit", currentUsername, null, request);
        await db.SaveChangesAsync();

        return request;
    }

    public async Task<WspExtensionRequest> ReviewExtensionRequestAsync(int id, string reviewerUserId, bool recommend, string comments)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.WspExtensionRequests.FindAsync(id);
        if (existing == null)
        {
            throw new KeyNotFoundException($"WspExtensionRequest with ID {id} was not found.");
        }

        if (existing.ApprovalStatusCode != "PendingReview")
        {
            throw new InvalidOperationException($"Cannot review extension request in status '{existing.ApprovalStatusCode}'. It must be in 'PendingReview' status.");
        }

        var beforeState = new
        {
            existing.ApprovalStatusCode,
            existing.ReviewedByUserId,
            existing.ReviewedAt,
            existing.ReviewerComments
        };

        existing.ReviewedByUserId = reviewerUserId;
        existing.ReviewedAt = DateTime.UtcNow;
        existing.ReviewerComments = comments;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = reviewerUserId;

        if (recommend)
        {
            existing.ApprovalStatusCode = "Recommended";
        }
        else
        {
            existing.ApprovalStatusCode = "Rejected";
            existing.ApprovalComments = comments;
            existing.ApprovedAt = DateTime.UtcNow;
        }

        _audit.LogAction(db, "WspExtensionRequest", existing.Id, "Review", reviewerUserId, beforeState, existing);
        await db.SaveChangesAsync();

        return existing;
    }

    public async Task<WspExtensionRequest> AdjudicateExtensionRequestAsync(int id, string approverUserId, bool approve, DateTime? grantedDate, string comments)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.WspExtensionRequests.FindAsync(id);
        if (existing == null)
        {
            throw new KeyNotFoundException($"WspExtensionRequest with ID {id} was not found.");
        }

        if (string.IsNullOrEmpty(existing.ReviewedByUserId))
        {
            throw new InvalidOperationException("Two-tier Maker-Checker violation: Extension request must be reviewed and recommended by a Client Liaison Officer prior to executive adjudication.");
        }

        if (string.Equals(existing.ReviewedByUserId, approverUserId, StringComparison.OrdinalIgnoreCase))
        {
            throw new InvalidOperationException("Segregation of duties violation: The reviewer cannot adjudicate the final approval.");
        }

        if (!string.IsNullOrEmpty(existing.CreatedBy) && string.Equals(existing.CreatedBy, approverUserId, StringComparison.OrdinalIgnoreCase))
        {
            throw new InvalidOperationException("Segregation of duties violation: The applicant/creator cannot adjudicate their own extension request.");
        }

        var beforeState = new
        {
            existing.ApprovalStatusCode,
            existing.ApprovedByUserId,
            existing.ApprovedAt,
            existing.GrantedExtensionDate,
            existing.ApprovalComments
        };

        existing.ApprovedByUserId = approverUserId;
        existing.ApprovedAt = DateTime.UtcNow;
        existing.ApprovalComments = comments;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = approverUserId;

        if (approve)
        {
            var finalGrantedDate = grantedDate ?? existing.RequestedExtensionDate;
            var maxAllowedDate = new DateTime(existing.SchemeYear, 5, 31, 23, 59, 59, DateTimeKind.Utc);
            if (finalGrantedDate > maxAllowedDate)
            {
                finalGrantedDate = maxAllowedDate;
            }

            existing.ApprovalStatusCode = "Approved";
            existing.GrantedExtensionDate = finalGrantedDate;
        }
        else
        {
            existing.ApprovalStatusCode = "Rejected";
        }

        _audit.LogAction(db, "WspExtensionRequest", existing.Id, "Adjudicate", approverUserId, beforeState, existing);
        await db.SaveChangesAsync();

        return existing;
    }
}
