using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

/// <summary>
/// Abstraction for writing micro-batches of raw SARS levy records into the SarsLevyStaging table.
/// Implementations utilize SqlBulkCopy on SQL Server for high-throughput batch writes, with in-memory fallback.
/// </summary>
public interface ISarsBulkStagingWriter
{
    /// <summary>
    /// Writes a batch of SarsLevyStaging rows into the staging table.
    /// </summary>
    Task<int> BulkWriteStagingAsync(IEnumerable<SarsLevyStaging> records, CancellationToken cancellationToken = default);
}
