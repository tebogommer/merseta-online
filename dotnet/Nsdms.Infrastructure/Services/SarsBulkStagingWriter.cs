using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.Data.SqlClient;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using Nsdms.Application.Common;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;

namespace Nsdms.Infrastructure.Services;

/// <summary>
/// High-speed bulk staging writer for raw monthly SARS Skills Development Levy files.
/// Implements Option A by streaming records through SqlBulkCopy when backed by Microsoft SQL Server,
/// with automatic resilient fallback to chunked EF Core AddRange when operating in test or in-memory modes.
/// </summary>
public class SarsBulkStagingWriter : ISarsBulkStagingWriter
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly ILogger<SarsBulkStagingWriter>? _logger;

    public SarsBulkStagingWriter(INsdmsDbContextFactory contextFactory, ILogger<SarsBulkStagingWriter>? logger = null)
    {
        _contextFactory = contextFactory;
        _logger = logger;
    }

    public async Task<int> BulkWriteStagingAsync(IEnumerable<SarsLevyStaging> records, CancellationToken cancellationToken = default)
    {
        var recordList = records as IList<SarsLevyStaging> ?? records.ToList();
        if (recordList.Count == 0)
        {
            return 0;
        }

        using var context = (NsdmsDbContext)await _contextFactory.CreateDbContextAsync();

        if (context.Database.IsSqlServer())
        {
            try
            {
                return await WriteViaSqlBulkCopyAsync(context, recordList, cancellationToken);
            }
            catch (Exception ex)
            {
                _logger?.LogWarning(ex, "SqlBulkCopy encountered an issue. Falling back to EF Core chunked insertion.");
                return await WriteViaEfCoreFallbackAsync(context, recordList, cancellationToken);
            }
        }
        else
        {
            return await WriteViaEfCoreFallbackAsync(context, recordList, cancellationToken);
        }
    }

    private async Task<int> WriteViaSqlBulkCopyAsync(NsdmsDbContext context, IList<SarsLevyStaging> records, CancellationToken cancellationToken)
    {
        var connection = (SqlConnection)context.Database.GetDbConnection();
        if (connection.State != ConnectionState.Open)
        {
            await connection.OpenAsync(cancellationToken);
        }

        using var table = BuildDataTable(records);
        using var bulkCopy = new SqlBulkCopy(connection, SqlBulkCopyOptions.CheckConstraints | SqlBulkCopyOptions.FireTriggers, null)
        {
            DestinationTableName = "dbo.SarsLevyStaging",
            BatchSize = Math.Min(5000, records.Count),
            BulkCopyTimeout = 120
        };

        bulkCopy.ColumnMappings.Add("BatchIdentifier", "BatchIdentifier");
        bulkCopy.ColumnMappings.Add("LineNumber", "LineNumber");
        bulkCopy.ColumnMappings.Add("RawRecord", "RawRecord");
        bulkCopy.ColumnMappings.Add("SdlNumber", "SdlNumber");
        bulkCopy.ColumnMappings.Add("SchemeYear", "SchemeYear");
        bulkCopy.ColumnMappings.Add("SicCode", "SicCode");
        bulkCopy.ColumnMappings.Add("ChamberCode", "ChamberCode");
        bulkCopy.ColumnMappings.Add("SetaCode", "SetaCode");
        bulkCopy.ColumnMappings.Add("MandatoryLevyAmount", "MandatoryLevyAmount");
        bulkCopy.ColumnMappings.Add("DiscretionaryLevyAmount", "DiscretionaryLevyAmount");
        bulkCopy.ColumnMappings.Add("AdminLevyAmount", "AdminLevyAmount");
        bulkCopy.ColumnMappings.Add("QctoLevyAmount", "QctoLevyAmount");
        bulkCopy.ColumnMappings.Add("InterestAmount", "InterestAmount");
        bulkCopy.ColumnMappings.Add("PenaltyAmount", "PenaltyAmount");
        bulkCopy.ColumnMappings.Add("TotalLevyAmount", "TotalLevyAmount");
        bulkCopy.ColumnMappings.Add("IsOutOfScopeSeta", "IsOutOfScopeSeta");
        bulkCopy.ColumnMappings.Add("HasSicCodeMismatch", "HasSicCodeMismatch");
        bulkCopy.ColumnMappings.Add("StagingStatus", "StagingStatus");
        bulkCopy.ColumnMappings.Add("ValidationMessage", "ValidationMessage");
        bulkCopy.ColumnMappings.Add("CreatedAt", "CreatedAt");
        bulkCopy.ColumnMappings.Add("CreatedBy", "CreatedBy");

        await bulkCopy.WriteToServerAsync(table, cancellationToken);
        return records.Count;
    }

    private async Task<int> WriteViaEfCoreFallbackAsync(NsdmsDbContext context, IList<SarsLevyStaging> records, CancellationToken cancellationToken)
    {
        const int chunkSize = 2000;
        for (int i = 0; i < records.Count; i += chunkSize)
        {
            var chunk = records.Skip(i).Take(chunkSize).ToList();
            await context.SarsLevyStagings.AddRangeAsync(chunk, cancellationToken);
            await context.SaveChangesAsync(cancellationToken);
            context.ChangeTracker.Clear();
        }
        return records.Count;
    }

    private DataTable BuildDataTable(IList<SarsLevyStaging> records)
    {
        var table = new DataTable("SarsLevyStaging");
        table.Columns.Add("BatchIdentifier", typeof(string));
        table.Columns.Add("LineNumber", typeof(int));
        table.Columns.Add("RawRecord", typeof(string));
        table.Columns.Add("SdlNumber", typeof(string));
        table.Columns.Add("SchemeYear", typeof(string));
        table.Columns.Add("SicCode", typeof(string));
        table.Columns.Add("ChamberCode", typeof(string));
        table.Columns.Add("SetaCode", typeof(string));
        table.Columns.Add("MandatoryLevyAmount", typeof(decimal));
        table.Columns.Add("DiscretionaryLevyAmount", typeof(decimal));
        table.Columns.Add("AdminLevyAmount", typeof(decimal));
        table.Columns.Add("QctoLevyAmount", typeof(decimal));
        table.Columns.Add("InterestAmount", typeof(decimal));
        table.Columns.Add("PenaltyAmount", typeof(decimal));
        table.Columns.Add("TotalLevyAmount", typeof(decimal));
        table.Columns.Add("IsOutOfScopeSeta", typeof(bool));
        table.Columns.Add("HasSicCodeMismatch", typeof(bool));
        table.Columns.Add("StagingStatus", typeof(string));
        table.Columns.Add("ValidationMessage", typeof(string));
        table.Columns.Add("CreatedAt", typeof(DateTime));
        table.Columns.Add("CreatedBy", typeof(string));

        foreach (var r in records)
        {
            table.Rows.Add(
                r.BatchIdentifier,
                r.LineNumber,
                (object?)r.RawRecord ?? DBNull.Value,
                r.SdlNumber,
                (object?)r.SchemeYear ?? DBNull.Value,
                (object?)r.SicCode ?? DBNull.Value,
                (object?)r.ChamberCode ?? DBNull.Value,
                r.SetaCode,
                r.MandatoryLevyAmount,
                r.DiscretionaryLevyAmount,
                r.AdminLevyAmount,
                r.QctoLevyAmount,
                r.InterestAmount,
                r.PenaltyAmount,
                r.TotalLevyAmount,
                r.IsOutOfScopeSeta,
                r.HasSicCodeMismatch,
                r.StagingStatus,
                (object?)r.ValidationMessage ?? DBNull.Value,
                r.CreatedAt,
                r.CreatedBy
            );
        }

        return table;
    }
}
