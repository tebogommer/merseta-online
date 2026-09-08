using Nsdms.Application.Common.Models;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Common.Interfaces;

/// <summary>
/// Service contract for managing statutory financial years, quarters,
/// and computing constituent months and working day projections.
/// </summary>
public interface IFiscalCalendarService
{
    /// <summary>
    /// Retrieves all registered financial years with quarters.
    /// </summary>
    Task<List<FinancialYear>> GetAllFinancialYearsAsync(bool activeOnly = false, CancellationToken cancellationToken = default);

    /// <summary>
    /// Retrieves a single financial year by primary key ID, including constituent quarters.
    /// </summary>
    Task<FinancialYear?> GetFinancialYearByIdAsync(int id, CancellationToken cancellationToken = default);

    /// <summary>
    /// Retrieves a financial year by statutory code (e.g. "2026/2027").
    /// </summary>
    Task<FinancialYear?> GetFinancialYearByCodeAsync(string finYearCode, CancellationToken cancellationToken = default);

    /// <summary>
    /// Creates a new financial year with quarters and records an audited change log.
    /// </summary>
    Task<FinancialYear> CreateFinancialYearAsync(CreateFinancialYearDto dto, string userId, CancellationToken cancellationToken = default);

    /// <summary>
    /// Updates an existing financial year and its quarters, recording an audited change log.
    /// </summary>
    Task<FinancialYear> UpdateFinancialYearAsync(UpdateFinancialYearDto dto, string userId, CancellationToken cancellationToken = default);

    /// <summary>
    /// Deletes a financial year with confirmation, recording an audited change log.
    /// </summary>
    Task<bool> DeleteFinancialYearAsync(int id, string userId, CancellationToken cancellationToken = default);

    /// <summary>
    /// Dynamically slices the financial year and quarters into constituent calendar months,
    /// calculating calendar days and statutory working days using South African public holiday rules.
    /// </summary>
    FiscalYearBreakdownDto ComputeYearBreakdown(string finYearCode, int startYear, int endYear, DateTime startDate, DateTime endDate, List<QuarterDateRangeDto> quarters);

    /// <summary>
    /// Validates that quarters are strictly contiguous: zero gaps, zero overlaps, and exact alignment with year start/end.
    /// </summary>
    FiscalValidationResult ValidateQuartersContiguity(DateTime yearStart, DateTime yearEnd, List<QuarterDateRangeDto> quarters);

    /// <summary>
    /// Resolves the default statutory template configuration from SystemConfig.
    /// </summary>
    Task<FiscalTemplateConfigDto> GetDefaultTemplateAsync(CancellationToken cancellationToken = default);

    /// <summary>
    /// Updates the default statutory template configuration in SystemConfig with audit logging.
    /// </summary>
    Task<bool> UpdateDefaultTemplateAsync(FiscalTemplateConfigDto dto, string userId, CancellationToken cancellationToken = default);

    /// <summary>
    /// Generates standard quarter date ranges for a given start year based on the template.
    /// </summary>
    List<QuarterDateRangeDto> GenerateDefaultQuartersForYear(int startYear, FiscalTemplateConfigDto template);

    /// <summary>
    /// Submits a draft or amendment draft financial year for review by an authorized reviewer (Maker action).
    /// </summary>
    Task<FinancialYear> SubmitForReviewAsync(int id, string? notes, string userId, CancellationToken cancellationToken = default);

    /// <summary>
    /// Formally approves and activates a financial year submitted for review (Checker action),
    /// enforcing segregation of duties and configurable role authorization.
    /// </summary>
    Task<FinancialYear> ApproveFiscalYearAsync(int id, string? reviewNotes, string userId, IEnumerable<string> userRoles, CancellationToken cancellationToken = default);

    /// <summary>
    /// Rejects a financial year submitted for review, returning it to Draft with feedback comments (Checker action).
    /// </summary>
    Task<FinancialYear> RejectFiscalYearAsync(int id, string rejectionReason, string userId, IEnumerable<string> userRoles, CancellationToken cancellationToken = default);

    /// <summary>
    /// Requests an amendment against an active financial year, creating a revision in 'Amendment Draft' state.
    /// </summary>
    Task<FinancialYear> RequestAmendmentAsync(int id, string amendmentReason, string userId, CancellationToken cancellationToken = default);

    /// <summary>
    /// Deactivates an active financial year, moving its status to 'Inactive' and closing it for operational postings.
    /// </summary>
    Task<FinancialYear> DeactivateFiscalYearAsync(int id, string userId, CancellationToken cancellationToken = default);
}
