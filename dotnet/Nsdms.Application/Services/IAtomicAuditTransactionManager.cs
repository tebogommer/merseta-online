using Nsdms.Application.Common;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

/// <summary>
/// Specification describing an audited domain mutation to be committed atomically within a single database transaction.
/// </summary>
public class AuditEntrySpec
{
    /// <summary>
    /// Target domain entity name (e.g. Organisation, Person, WspSubmission, GrantMoa, BankingDetails).
    /// </summary>
    public string EntityName { get; set; } = string.Empty;

    /// <summary>
    /// Database primary key of the modified record. If 0 on initial creation, the manager automatically
    /// extracts the auto-generated identity ID from the persisted entity before committing the audit entry.
    /// </summary>
    public long RecordId { get; set; }

    /// <summary>
    /// Specific business or workflow action performed (e.g. Create, Update, Delete, Submit, Approve).
    /// </summary>
    public string ActionName { get; set; } = string.Empty;

    /// <summary>
    /// Authenticated actor identity. Anonymous mutations are strictly prohibited under DPSA Information Security Directive.
    /// </summary>
    public string Actor { get; set; } = string.Empty;

    /// <summary>
    /// Previous state snapshot before mutation.
    /// </summary>
    public object? BeforeState { get; set; }

    /// <summary>
    /// Resulting state snapshot after mutation.
    /// </summary>
    public object? AfterState { get; set; }

    /// <summary>
    /// Optional cryptographic digital security seal. If null, the manager automatically computes an SHA-256 seal.
    /// </summary>
    public string? DigitalSecuritySeal { get; set; }

    /// <summary>
    /// Contextual metadata dictionary.
    /// </summary>
    public Dictionary<string, object?> AdditionalMetadata { get; set; } = new();
}

/// <summary>
/// Enterprise transaction manager enforcing ISO 9001:2015 Clause 7.5 and DPSA Information Security Directive compliance.
/// Guarantees atomic double-write persistence: business entity mutations and audit log records commit together,
/// or both roll back completely with zero partial commits.
/// </summary>
public interface IAtomicAuditTransactionManager
{
    /// <summary>
    /// Executes a business mutation within an execution strategy and database transaction,
    /// committing the entity changes and audit log atomically. If any error occurs, the transaction rolls back.
    /// </summary>
    Task<TResult> ExecuteAtomicAsync<TResult>(
        Func<INsdmsDbContext, Task<(TResult Result, AuditEntrySpec AuditSpec)>> operation,
        CancellationToken cancellationToken = default);

    /// <summary>
    /// Executes a business mutation producing multiple audit entries atomically within the same transaction.
    /// </summary>
    Task<TResult> ExecuteAtomicBatchAsync<TResult>(
        Func<INsdmsDbContext, Task<(TResult Result, IEnumerable<AuditEntrySpec> AuditSpecs)>> operation,
        CancellationToken cancellationToken = default);

    /// <summary>
    /// Executes an action without a returned payload atomically with its audit record.
    /// </summary>
    Task ExecuteAtomicAsync(
        Func<INsdmsDbContext, Task<AuditEntrySpec>> operation,
        CancellationToken cancellationToken = default);
}
