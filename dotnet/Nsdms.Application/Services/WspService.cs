using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public interface IWspService
{
    Task<List<WspSubmission>> GetAllAsync(int? finYear = null, string? search = null, int? organisationId = null);
    Task<List<WspSubmission>> GetAllSubmissionsAsync(int? organisationId = null, int? finYear = null);
    Task<WspSubmission?> GetByIdAsync(int id);
    Task<WspSubmission?> GetSubmissionByIdAsync(int id);
    Task<WspSubmission> CreateAsync(WspSubmission submission, string currentUsername = "SYSTEM");
    Task<WspSubmission> CreateSubmissionAsync(WspSubmission submission, string currentUsername = "SYSTEM");
    Task<WspSubmission> UpdateAsync(WspSubmission submission, string currentUsername = "SYSTEM");
    Task<WspSubmission> UpdateSubmissionAsync(WspSubmission submission, string currentUsername = "SYSTEM");
    Task<WspSubmission> SaveAsync(WspSubmission submission, string currentUsername = "SYSTEM");
    Task<WspSubmission> UpdateSubmissionStatusAsync(int id, string statusCode, string currentUsername = "SYSTEM");
    Task<bool> DeleteAsync(int id, string currentUsername = "SYSTEM");
    Task<bool> DeleteSubmissionAsync(int id, string currentUsername = "SYSTEM");

    Task<WspEmploymentSummary> AddEmploymentSummaryAsync(WspEmploymentSummary summary, string currentUsername = "SYSTEM");
    Task<WspEmploymentSummary> AddEmploymentSummaryAsync(int submissionId, WspEmploymentSummary summary, string currentUsername = "SYSTEM");
    Task<List<WspEmploymentSummary>> GetEmploymentSummariesAsync(int submissionId);
    Task<int> RecalculateEmploymentTotalsAsync(int submissionId);
    Task<bool> RemoveEmploymentSummaryAsync(int summaryId, string currentUsername = "SYSTEM");

    Task<WspTrainingPlan> AddTrainingPlanAsync(WspTrainingPlan plan, string currentUsername = "SYSTEM");
    Task<WspTrainingPlan> AddTrainingPlanAsync(int submissionId, WspTrainingPlan plan, string currentUsername = "SYSTEM");
    Task<List<WspTrainingPlan>> GetTrainingPlansAsync(int submissionId);
    Task<decimal> RecalculateTrainingPlanBudgetAsync(int submissionId);
    Task<bool> RemoveTrainingPlanAsync(long planId, string currentUsername = "SYSTEM");

    Task<decimal> CalculateMandatoryGrantClaimAsync(int wspSubmissionId);
    decimal CalculateMandatoryGrant(decimal totalLevyPaid);
    bool ValidateMandatoryGrantEligibility(WspSubmission submission);

    // Statutory Window & Extension Requests
    Task<bool> IsSubmissionWindowOpenAsync(int organisationId, int schemeYear);
    Task<DateTime> GetEffectiveSubmissionDeadlineAsync(int organisationId, int schemeYear);
    Task<List<WspExtensionRequest>> GetExtensionRequestsAsync(int? organisationId = null, int? schemeYear = null, string? statusCode = null);
    Task<WspExtensionRequest?> GetExtensionRequestByIdAsync(int id);
    Task<WspExtensionRequest> SubmitExtensionRequestAsync(WspExtensionRequest request, string currentUsername = "SYSTEM");
    Task<WspExtensionRequest> ReviewExtensionRequestAsync(int id, string reviewerUserId, bool recommend, string comments);
    Task<WspExtensionRequest> AdjudicateExtensionRequestAsync(int id, string approverUserId, bool approve, DateTime? grantedDate, string comments);
}

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

    public async Task<List<WspSubmission>> GetAllAsync(int? finYear = null, string? search = null, int? organisationId = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.WspSubmissions
            .Include(w => w.Organisation)
            .Include(w => w.EmploymentSummaries)
            .Include(w => w.TrainingPlans)
            .AsQueryable();

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
        submission.CreatedAt = DateTime.UtcNow;
        submission.CreatedBy = currentUsername;

        db.WspSubmissions.Add(submission);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "WspSubmission", submission.Id, "Create", currentUsername, null, submission);
        await db.SaveChangesAsync();

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

        return plan;
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
    public async Task<DateTime> GetEffectiveSubmissionDeadlineAsync(int organisationId, int schemeYear)
    {
        // 1. Resolve standard submission deadline for the scheme year (default April 30 23:59:59 SAST/UTC)
        var deadlineConfig = "04-30";
        if (_configService != null)
        {
            deadlineConfig = await _configService.GetValueAsync("Governance:WspAnnualSubmissionDeadline", "04-30") ?? "04-30";
        }

        int month = 4;
        int day = 30;
        if (deadlineConfig.Contains('-'))
        {
            var parts = deadlineConfig.Split('-');
            if (parts.Length == 2 && int.TryParse(parts[0], out var m) && int.TryParse(parts[1], out var d))
            {
                month = m;
                day = d;
            }
            else if (parts.Length == 3 && int.TryParse(parts[1], out var m3) && int.TryParse(parts[2], out var d3))
            {
                month = m3;
                day = d3;
            }
        }

        var baseDeadline = new DateTime(schemeYear, month, day, 23, 59, 59, DateTimeKind.Utc);

        // 2. Check if an approved extension request exists for this organisation and scheme year
        using var db = await _contextFactory.CreateDbContextAsync();
        var approvedExtension = await db.WspExtensionRequests
            .Where(r => r.OrganisationId == organisationId && r.SchemeYear == schemeYear && r.ApprovalStatusCode == "Approved" && r.GrantedExtensionDate.HasValue)
            .OrderByDescending(r => r.GrantedExtensionDate)
            .FirstOrDefaultAsync();

        if (approvedExtension?.GrantedExtensionDate != null)
        {
            var extDate = approvedExtension.GrantedExtensionDate.Value;
            var extensionDeadline = new DateTime(extDate.Year, extDate.Month, extDate.Day, 23, 59, 59, DateTimeKind.Utc);
            if (extensionDeadline > baseDeadline)
            {
                return extensionDeadline;
            }
        }

        return baseDeadline;
    }

    public async Task<bool> IsSubmissionWindowOpenAsync(int organisationId, int schemeYear)
    {
        var openConfig = "01-01";
        if (_configService != null)
        {
            openConfig = await _configService.GetValueAsync("Governance:WspWindowOpenDate", "01-01") ?? "01-01";
        }

        int openMonth = 1;
        int openDay = 1;
        if (openConfig.Contains('-'))
        {
            var parts = openConfig.Split('-');
            if (parts.Length == 2 && int.TryParse(parts[0], out var m) && int.TryParse(parts[1], out var d))
            {
                openMonth = m;
                openDay = d;
            }
        }

        var windowOpen = new DateTime(schemeYear, openMonth, openDay, 0, 0, 0, DateTimeKind.Utc);
        var now = DateTime.UtcNow;

        if (now < windowOpen)
        {
            return false;
        }

        var effectiveDeadline = await GetEffectiveSubmissionDeadlineAsync(organisationId, schemeYear);
        return now <= effectiveDeadline;
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

        if (!string.IsNullOrEmpty(existing.ReviewedByUserId) && string.Equals(existing.ReviewedByUserId, approverUserId, StringComparison.OrdinalIgnoreCase))
        {
            throw new InvalidOperationException("Maker-Checker segregation of duties violation: The reviewer cannot adjudicate the final approval.");
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
