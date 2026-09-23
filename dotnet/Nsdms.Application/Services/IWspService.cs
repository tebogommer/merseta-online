using Nsdms.Application.Common;
using Nsdms.Application.Common.Models;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public interface IWspService
{
    Task<PagedResult<WspSubmission>> GetPagedSubmissionsAsync(PaginationQuery query, CancellationToken cancellationToken = default);
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
    Task<WspWindowStatusDto> GetWindowStatusAsync(int? organisationId = null, int? schemeYear = null, CancellationToken ct = default);
    Task<bool> IsSubmissionWindowOpenAsync(int organisationId, int schemeYear);
    Task<DateTime> GetEffectiveSubmissionDeadlineAsync(int organisationId, int schemeYear);
    Task<List<WspExtensionRequest>> GetExtensionRequestsAsync(int? organisationId = null, int? schemeYear = null, string? statusCode = null);
    Task<WspExtensionRequest?> GetExtensionRequestByIdAsync(int id);
    Task<WspExtensionRequest> SubmitExtensionRequestAsync(WspExtensionRequest request, string currentUsername = "SYSTEM");
    Task<WspExtensionRequest> ReviewExtensionRequestAsync(int id, string reviewerUserId, bool recommend, string comments);
    Task<WspExtensionRequest> AdjudicateExtensionRequestAsync(int id, string approverUserId, bool approve, DateTime? grantedDate, string comments);
}
