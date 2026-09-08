using Nsdms.Domain.Entities;

namespace Nsdms.Application.Common.Interfaces;

/// <summary>
/// Service contract for managing statutory public holidays, merSETA year-end shutdowns,
/// and ad-hoc institutional closures.
/// </summary>
public interface IHolidayClosureService
{
    /// <summary>
    /// Retrieves all holiday and closure records matching optional filter parameters.
    /// </summary>
    Task<List<NonWorkingDay>> GetAllAsync(int? year = null, string? typeCode = null, bool activeOnly = false, CancellationToken cancellationToken = default);

    /// <summary>
    /// Retrieves an individual holiday or closure record by its database primary key identifier.
    /// </summary>
    Task<NonWorkingDay?> GetByIdAsync(int id, CancellationToken cancellationToken = default);

    /// <summary>
    /// Creates a new holiday or closure entry, performing double-write audit logging.
    /// </summary>
    Task<NonWorkingDay> CreateAsync(NonWorkingDayDto dto, string currentUsername, CancellationToken cancellationToken = default);

    /// <summary>
    /// Updates an existing holiday or closure entry, capturing before-and-after snapshots in the audited change log.
    /// </summary>
    Task<NonWorkingDay> UpdateAsync(int id, NonWorkingDayDto dto, string currentUsername, CancellationToken cancellationToken = default);

    /// <summary>
    /// Deletes or deactivates a holiday or closure entry with affirmative audit trail capture.
    /// </summary>
    Task<bool> DeleteAsync(int id, string currentUsername, CancellationToken cancellationToken = default);

    /// <summary>
    /// Approves a draft closure or ad-hoc holiday, making it active for SLA engine calculations.
    /// </summary>
    Task<NonWorkingDay> ApproveAsync(int id, string currentUsername, CancellationToken cancellationToken = default);

    /// <summary>
    /// Automatically pre-populates all South African statutory holidays and the official merSETA annual shutdown for a specific year.
    /// </summary>
    Task<int> SeedDefaultHolidaysForYearAsync(int year, string currentUsername, CancellationToken cancellationToken = default);
}

/// <summary>
/// DTO for creating and updating NonWorkingDay records.
/// </summary>
public class NonWorkingDayDto
{
    public string Name { get; set; } = string.Empty;
    public string TypeCode { get; set; } = NonWorkingDayType.NationalStatutory;
    public DateTime StartDate { get; set; } = DateTime.UtcNow.Date;
    public DateTime EndDate { get; set; } = DateTime.UtcNow.Date;
    public int CalendarYear { get; set; } = DateTime.UtcNow.Year;
    public bool AffectsSla { get; set; } = true;
    public bool IsRecurringAnnually { get; set; } = false;
    public string? GazetteOrResolutionRef { get; set; }
    public string? Description { get; set; }
    public string StatusCode { get; set; } = NonWorkingDayStatus.Approved;
    public bool IsActive { get; set; } = true;
}
