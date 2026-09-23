using System.Security.Cryptography;
using System.Text;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Storage;
using Nsdms.Application.Common;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;

namespace Nsdms.Infrastructure.Services;

/// <summary>
/// Implements atomic database transactions and ISO 9001:2015 / DPSA Directive compliant audit logging.
/// Eliminates partial commits by coordinating entity mutations and AuditLog writes within a single transactional unit
/// backed by EF Core's CreateExecutionStrategy for robust SQL Server retry resilience.
/// </summary>
public class AtomicAuditTransactionManager : IAtomicAuditTransactionManager
{
    private readonly INsdmsDbContextFactory _contextFactory;

    public AtomicAuditTransactionManager(INsdmsDbContextFactory contextFactory)
    {
        _contextFactory = contextFactory;
    }

    public async Task<TResult> ExecuteAtomicAsync<TResult>(
        Func<INsdmsDbContext, Task<(TResult Result, AuditEntrySpec AuditSpec)>> operation,
        CancellationToken cancellationToken = default)
    {
        return await ExecuteAtomicBatchAsync<TResult>(async db =>
        {
            var (result, auditSpec) = await operation(db);
            return (result, (IEnumerable<AuditEntrySpec>)new[] { auditSpec });
        }, cancellationToken);
    }

    public async Task ExecuteAtomicAsync(
        Func<INsdmsDbContext, Task<AuditEntrySpec>> operation,
        CancellationToken cancellationToken = default)
    {
        await ExecuteAtomicBatchAsync<bool>(async db =>
        {
            var auditSpec = await operation(db);
            return (true, (IEnumerable<AuditEntrySpec>)new[] { auditSpec });
        }, cancellationToken);
    }

    public async Task<TResult> ExecuteAtomicBatchAsync<TResult>(
        Func<INsdmsDbContext, Task<(TResult Result, IEnumerable<AuditEntrySpec> AuditSpecs)>> operation,
        CancellationToken cancellationToken = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync(cancellationToken);
        var strategy = db.Database.CreateExecutionStrategy();

        return await strategy.ExecuteAsync(async () =>
        {
            IDbContextTransaction? tx = null;
            if (db.Database.IsRelational())
            {
                tx = await db.Database.BeginTransactionAsync(cancellationToken);
            }

            try
            {
                // Step 1: Execute domain operation inside the active transaction
                var (result, auditSpecs) = await operation(db);
                var specList = auditSpecs.ToList();

                // Step 2: Pre-validate audit specifications (DPSA actor check & non-null fields) before flushing
                foreach (var spec in specList)
                {
                    if (string.IsNullOrWhiteSpace(spec.EntityName))
                    {
                        throw new InvalidOperationException("Audit logging specification must specify a valid EntityName.");
                    }

                    if (string.IsNullOrWhiteSpace(spec.ActionName))
                    {
                        throw new InvalidOperationException("Audit logging specification must specify a valid ActionName.");
                    }

                    // DPSA Directive Invariant: Zero anonymous actors permitted on statutory/financial transactions
                    if (string.IsNullOrWhiteSpace(spec.Actor) ||
                        spec.Actor.Equals("ANONYMOUS", StringComparison.OrdinalIgnoreCase) ||
                        spec.Actor.Equals("UNKNOWN", StringComparison.OrdinalIgnoreCase))
                    {
                        throw new InvalidOperationException($"DPSA Information Security Directive breach: Anonymous mutations are strictly prohibited. Actor must be verified for entity '{spec.EntityName}'.");
                    }
                }

                // Step 3: Flush domain mutations to generate auto-increment identity IDs inside transaction
                await db.SaveChangesAsync(cancellationToken);

                // Step 4: Format and append corresponding AuditLog entries with confirmed entity IDs
                var now = DateTime.UtcNow;
                foreach (var spec in specList)
                {
                    // Extract RecordId if not explicitly passed on the spec
                    var recordId = spec.RecordId;
                    if (recordId <= 0 && result != null)
                    {
                        var idProp = result.GetType().GetProperty("Id");
                        if (idProp != null)
                        {
                            var val = idProp.GetValue(result);
                            if (val is int intVal && intVal > 0) recordId = intVal;
                            else if (val is long longVal && longVal > 0) recordId = longVal;
                        }
                    }

                    // ISO 9001 Clause 7.5 Invariant: Unique persistent identification
                    if (recordId <= 0)
                    {
                        throw new InvalidOperationException($"Data Integrity breach: RecordId for entity '{spec.EntityName}' must be a non-zero positive integer to guarantee ISO 9001 Clause 7.5 traceability.");
                    }

                    // Cryptographic seal generation
                    var seal = spec.DigitalSecuritySeal ?? ComputeDigitalSecuritySeal(spec.EntityName, recordId, spec.ActionName, spec.Actor, now);

                    var metadataDict = new Dictionary<string, object?>
                    {
                        ["before"] = spec.BeforeState,
                        ["after"] = spec.AfterState,
                        ["digitalSecuritySeal"] = seal,
                        ["iso9001Compliance"] = true,
                        ["dpsaDirectiveCompliance"] = true
                    };

                    if (spec.AdditionalMetadata != null)
                    {
                        foreach (var kvp in spec.AdditionalMetadata)
                        {
                            metadataDict[kvp.Key] = kvp.Value;
                        }
                    }

                    var sanitizedJson = AuditService.SerializeSanitizedMetadata(metadataDict);

                    var audit = new AuditLog
                    {
                        EntityName = spec.EntityName,
                        RecordId = recordId,
                        ActionName = spec.ActionName,
                        Actor = spec.Actor,
                        MetadataJson = sanitizedJson,
                        Timestamp = now
                    };

                    db.AuditLogs.Add(audit);
                }

                // Step 5: Persist audit records inside the exact same uncommitted transaction
                await db.SaveChangesAsync(cancellationToken);

                // Step 6: Atomically commit both business data and audit records
                if (tx != null)
                {
                    await tx.CommitAsync(cancellationToken);
                }

                return result;
            }
            catch
            {
                // Zero partial commits: Any failure causes complete rollback of business data and audit logs
                if (tx != null)
                {
                    await tx.RollbackAsync(cancellationToken);
                }
                else if (db is DbContext dbContext)
                {
                    dbContext.ChangeTracker.Clear();
                }
                throw;
            }
            finally
            {
                tx?.Dispose();
            }
        });
    }

    /// <summary>
    /// Computes an immutable SHA-256 digital verification seal over an audit transaction.
    /// </summary>
    public static string ComputeDigitalSecuritySeal(string entityName, long recordId, string actionName, string actor, DateTime timestamp)
    {
        var rawPayload = $"{entityName}|{recordId}|{actionName}|{actor}|{timestamp:O}";
        var hashBytes = SHA256.HashData(Encoding.UTF8.GetBytes(rawPayload));
        return Convert.ToHexString(hashBytes).ToLowerInvariant();
    }
}
