using System.Security.Cryptography;
using System.Text;
using System.Text.Json;
using System.Text.Json.Serialization;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Models;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public interface IAuditService
{
    Task LogActionAsync(string entityName, long recordId, string actionName, string actor, object? beforeState = null, object? afterState = null);
    Task LogAsync(string entityName, long recordId, string actionName, string actor, object? afterState = null);
    Task LogAsync(string entityName, long recordId, string actionName, string description, string actor, object? afterState = null);
    void LogAction(INsdmsDbContext db, string entityName, long recordId, string actionName, string actor, object? beforeState = null, object? afterState = null);
    Task LogActionAsync(INsdmsDbContext db, string entityName, long recordId, string actionName, string actor, object? beforeState = null, object? afterState = null);
    Task<List<AuditLog>> GetRecentLogsAsync(int count = 500);
    Task<List<AuditLog>> GetLogsForEntityAsync(string entityName, long recordId, CancellationToken cancellationToken = default);
    Task<AuditPeriodReportResult> GetAuditReportByPeriodAsync(AuditPeriodFilterRequest request, CancellationToken cancellationToken = default);
    Task<byte[]> ExportAuditReportCsvAsync(AuditPeriodFilterRequest request, string exportedBy, CancellationToken cancellationToken = default);
    Task<byte[]> GenerateItgcAuditDossierPdfAsync(AuditPeriodFilterRequest request, string exportedBy, CancellationToken cancellationToken = default);
}

public class AuditService : IAuditService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IServiceProvider? _serviceProvider;

    private static readonly JsonSerializerOptions JsonOpts = new()
    {
        ReferenceHandler = ReferenceHandler.IgnoreCycles,
        DefaultIgnoreCondition = JsonIgnoreCondition.WhenWritingNull,
        WriteIndented = false
    };

    public AuditService(INsdmsDbContextFactory contextFactory, IServiceProvider? serviceProvider = null)
    {
        _contextFactory = contextFactory;
        _serviceProvider = serviceProvider;
    }


    public async Task LogActionAsync(string entityName, long recordId, string actionName, string actor, object? beforeState = null, object? afterState = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        LogAction(db, entityName, recordId, actionName, actor, beforeState, afterState);
        await db.SaveChangesAsync();
    }

    public Task LogAsync(string entityName, long recordId, string actionName, string actor, object? afterState = null)
    {
        return LogActionAsync(entityName, recordId, actionName, actor, null, afterState);
    }

    public Task LogAsync(string entityName, long recordId, string actionName, string description, string actor, object? afterState = null)
    {
        return LogActionAsync(entityName, recordId, actionName, actor, null, new { description, data = afterState });
    }

    public void LogAction(INsdmsDbContext db, string entityName, long recordId, string actionName, string actor, object? beforeState = null, object? afterState = null)
    {
        var metadata = new
        {
            before = beforeState,
            after = afterState
        };

        var log = new AuditLog
        {
            EntityName = entityName,
            RecordId = recordId,
            ActionName = actionName,
            Actor = actor,
            MetadataJson = SerializeSanitizedMetadata(metadata),
            Timestamp = DateTime.UtcNow
        };

        db.AuditLogs.Add(log);
    }

    /// <summary>
    /// Serializes metadata objects while automatically redacting and masking sensitive POPIA PII properties.
    /// </summary>
    public static string SerializeSanitizedMetadata(object metadata)
    {
        try
        {
            var rawJson = JsonSerializer.Serialize(metadata, JsonOpts);
            using var doc = JsonDocument.Parse(rawJson);
            using var stream = new MemoryStream();
            using (var writer = new Utf8JsonWriter(stream))
            {
                SanitizeJsonElement(doc.RootElement, writer);
            }
            return System.Text.Encoding.UTF8.GetString(stream.ToArray());
        }
        catch
        {
            // Fallback to standard serializer if custom parsing fails
            return JsonSerializer.Serialize(metadata, JsonOpts);
        }
    }

    private static void SanitizeJsonElement(JsonElement element, Utf8JsonWriter writer, string? propertyName = null)
    {
        switch (element.ValueKind)
        {
            case JsonValueKind.Object:
                writer.WriteStartObject();
                foreach (var prop in element.EnumerateObject())
                {
                    writer.WritePropertyName(prop.Name);
                    SanitizeJsonElement(prop.Value, writer, prop.Name);
                }
                writer.WriteEndObject();
                break;

            case JsonValueKind.Array:
                writer.WriteStartArray();
                foreach (var item in element.EnumerateArray())
                {
                    SanitizeJsonElement(item, writer, propertyName);
                }
                writer.WriteEndArray();
                break;

            case JsonValueKind.String:
                var strVal = element.GetString();
                if (!string.IsNullOrWhiteSpace(propertyName) && Nsdms.Application.Common.Utilities.PopiaMaskingUtility.IsSensitivePropertyName(propertyName))
                {
                    writer.WriteStringValue(Nsdms.Application.Common.Utilities.PopiaMaskingUtility.MaskSensitiveValue(propertyName, strVal));
                }
                else
                {
                    writer.WriteStringValue(strVal);
                }
                break;

            default:
                element.WriteTo(writer);
                break;
        }
    }

    public Task LogActionAsync(INsdmsDbContext db, string entityName, long recordId, string actionName, string actor, object? beforeState = null, object? afterState = null)
    {
        LogAction(db, entityName, recordId, actionName, actor, beforeState, afterState);
        return Task.CompletedTask;
    }

    public async Task<List<AuditLog>> GetRecentLogsAsync(int count = 500)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.AuditLogs
            .AsNoTracking()
            .OrderByDescending(a => a.Timestamp)
            .Take(count)
            .ToListAsync();
    }

    public async Task<List<AuditLog>> GetLogsForEntityAsync(string entityName, long recordId, CancellationToken cancellationToken = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync(cancellationToken);
        return await db.AuditLogs
            .AsNoTracking()
            .Where(a => a.EntityName == entityName && a.RecordId == recordId)
            .OrderByDescending(a => a.Timestamp)
            .ToListAsync(cancellationToken);
    }

    public async Task<AuditPeriodReportResult> GetAuditReportByPeriodAsync(AuditPeriodFilterRequest request, CancellationToken cancellationToken = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync(cancellationToken);
        var query = BuildFilteredAuditQuery(db, request);

        var totalCount = await query.CountAsync(cancellationToken);

        var page = request.PageNumber > 0 ? request.PageNumber : 1;
        var size = request.PageSize > 0 ? request.PageSize : 50;

        var logs = await query
            .Skip((page - 1) * size)
            .Take(size)
            .ToListAsync(cancellationToken);

        return new AuditPeriodReportResult
        {
            Logs = logs,
            TotalCount = totalCount,
            FromDateUtc = request.FromDateUtc,
            ToDateUtc = request.ToDateUtc,
            GeneratedAtUtc = DateTime.UtcNow,
            ReportIntegrityHash = ComputeReportIntegrityHash(logs)
        };
    }

    public async Task<byte[]> ExportAuditReportCsvAsync(AuditPeriodFilterRequest request, string exportedBy, CancellationToken cancellationToken = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync(cancellationToken);
        var query = BuildFilteredAuditQuery(db, request);

        List<AuditLog> logs;
        if (request.PageNumber > 1)
        {
            var size = request.PageSize > 0 ? request.PageSize : 50;
            logs = await query.Skip((request.PageNumber - 1) * size).Take(size).ToListAsync(cancellationToken);
        }
        else if (request.PageSize > 0 && request.PageSize != 50)
        {
            logs = await query.Take(request.PageSize).ToListAsync(cancellationToken);
        }
        else
        {
            logs = await query.ToListAsync(cancellationToken);
        }

        var exportTime = DateTime.UtcNow;
        var hash = ComputeReportIntegrityHash(logs);

        var sb = new StringBuilder();
        // DLP Watermark Header
        sb.AppendLine($"# merSETA CONFIDENTIAL - Exported by {exportedBy} on {exportTime:O} - ISO 27001 / AGSA Verified - SHA256: {hash}");
        // CSV Header Columns
        sb.AppendLine("Id, TimestampUtc, EntityName, RecordId, ActionName, Actor, DigitalSecuritySeal, MetadataJson");

        foreach (var log in logs)
        {
            var seal = ExtractOrComputeDigitalSecuritySeal(log);
            sb.AppendLine(System.Globalization.CultureInfo.InvariantCulture,
                $"{log.Id},{log.Timestamp:O},{EscapeCsv(log.EntityName)},{log.RecordId},{EscapeCsv(log.ActionName)},{EscapeCsv(log.Actor)},{seal},{EscapeCsv(log.MetadataJson ?? string.Empty)}");
        }

        // The CSV export must log an EXPORT_AUDIT_REPORT event to AuditLog
        await LogActionAsync(
            "AuditLog",
            0,
            "EXPORT_AUDIT_REPORT",
            exportedBy,
            null,
            new
            {
                Action = "EXPORT_AUDIT_REPORT",
                ExportedBy = exportedBy,
                ExportedAtUtc = exportTime,
                Filter = new
                {
                    request.FromDateUtc,
                    request.ToDateUtc,
                    request.EntityName,
                    request.ActionName,
                    request.Actor,
                    request.SearchTerm
                },
                TotalRecordsExported = logs.Count,
                ReportIntegrityHash = hash
            });

        return Encoding.UTF8.GetBytes(sb.ToString());
    }

    public async Task<byte[]> GenerateItgcAuditDossierPdfAsync(AuditPeriodFilterRequest request, string exportedBy, CancellationToken cancellationToken = default)
    {
        if (_serviceProvider != null)
        {
            var exportService = _serviceProvider.GetService(typeof(IReportExportService)) as IReportExportService;
            if (exportService != null)
            {
                return await exportService.GenerateItgcAuditDossierPdfAsync(request, exportedBy, cancellationToken);
            }
        }

        throw new InvalidOperationException("IReportExportService is not available in the current service provider context.");
    }

    private static IQueryable<AuditLog> BuildFilteredAuditQuery(INsdmsDbContext db, AuditPeriodFilterRequest request)
    {
        var query = db.AuditLogs.AsNoTracking().AsQueryable();

        if (request.FromDateUtc.HasValue)
        {
            query = query.Where(a => a.Timestamp >= request.FromDateUtc.Value);
        }

        if (request.ToDateUtc.HasValue)
        {
            query = query.Where(a => a.Timestamp <= request.ToDateUtc.Value);
        }

        if (!string.IsNullOrWhiteSpace(request.EntityName))
        {
            var entity = request.EntityName.Trim();
            query = query.Where(a => a.EntityName == entity);
        }

        if (!string.IsNullOrWhiteSpace(request.ActionName))
        {
            var action = request.ActionName.Trim();
            query = query.Where(a => a.ActionName == action);
        }

        if (!string.IsNullOrWhiteSpace(request.Actor))
        {
            var actor = request.Actor.Trim();
            query = query.Where(a => a.Actor == actor);
        }

        if (!string.IsNullOrWhiteSpace(request.SearchTerm))
        {
            var term = request.SearchTerm.Trim();
            query = query.Where(a =>
                a.EntityName.Contains(term) ||
                a.ActionName.Contains(term) ||
                a.Actor.Contains(term) ||
                (a.MetadataJson != null && a.MetadataJson.Contains(term)));
        }

        return query.OrderByDescending(a => a.Timestamp);
    }

    public static string ComputeReportIntegrityHash(IEnumerable<AuditLog> logs)
    {
        var sb = new StringBuilder();
        foreach (var l in logs)
        {
            sb.Append($"{l.Id}|{l.Timestamp:O}|{l.EntityName}|{l.RecordId}|{l.ActionName}|{l.Actor};");
        }
        var hashBytes = SHA256.HashData(Encoding.UTF8.GetBytes(sb.ToString()));
        return Convert.ToHexString(hashBytes).ToLowerInvariant();
    }

    public static string ExtractOrComputeDigitalSecuritySeal(AuditLog log)
    {
        if (!string.IsNullOrWhiteSpace(log.MetadataJson))
        {
            try
            {
                using var doc = JsonDocument.Parse(log.MetadataJson);
                if (doc.RootElement.TryGetProperty("digitalSecuritySeal", out var sealProp))
                {
                    var recordedSeal = sealProp.GetString();
                    if (!string.IsNullOrEmpty(recordedSeal))
                    {
                        return recordedSeal;
                    }
                }
            }
            catch
            {
                // Fallback to compute
            }
        }

        return ComputeDigitalSecuritySeal(log.EntityName, log.RecordId, log.ActionName, log.Actor, log.Timestamp);
    }

    public static string ComputeDigitalSecuritySeal(string entityName, long recordId, string actionName, string actor, DateTime timestamp)
    {
        var rawPayload = $"{entityName}|{recordId}|{actionName}|{actor}|{timestamp:O}";
        var hashBytes = SHA256.HashData(Encoding.UTF8.GetBytes(rawPayload));
        return Convert.ToHexString(hashBytes).ToLowerInvariant();
    }

    private static string EscapeCsv(string? value)
    {
        if (string.IsNullOrEmpty(value)) return string.Empty;
        if (value.Contains(',') || value.Contains('"') || value.Contains('\n') || value.Contains('\r'))
        {
            return $"\"{value.Replace("\"", "\"\"")}\"";
        }
        return value;
    }
}
