namespace Nsdms.Application.Validation;

/// <summary>
/// Service contract for executing comprehensive statutory batch pre-submission audits
/// against DHET SETMIS and SAQA NLRD specification rules.
/// </summary>
public interface IStatutoryValidationService
{
    /// <summary>
    /// Audits all candidate records across DHET SETMIS Files (100 to 505) and returns a consolidated compliance report.
    /// </summary>
    Task<StatutoryValidationReport> ValidateSetmisSubmissionBatchAsync(CancellationToken cancellationToken = default);

    /// <summary>
    /// Audits all candidate records across SAQA NLRD Files (21 to 30) simulating Edu.Dex statutory rules.
    /// </summary>
    Task<StatutoryValidationReport> ValidateNlrdSubmissionBatchAsync(CancellationToken cancellationToken = default);
}
