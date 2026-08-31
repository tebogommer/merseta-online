using Nsdms.Application.Common;
using Nsdms.Domain.Entities;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace Nsdms.Application.Services;

public interface IAuditService
{
    Task LogActionAsync(string entityName, int recordId, string actionName, string actor, object? beforeState = null, object? afterState = null);
    Task LogAsync(string entityName, int recordId, string actionName, string actor, object? afterState = null);
    Task LogAsync(string entityName, int recordId, string actionName, string description, string actor, object? afterState = null);
    void LogAction(INsdmsDbContext db, string entityName, int recordId, string actionName, string actor, object? beforeState = null, object? afterState = null);
    Task LogActionAsync(INsdmsDbContext db, string entityName, int recordId, string actionName, string actor, object? beforeState = null, object? afterState = null);
}

public class AuditService : IAuditService
{
    private readonly INsdmsDbContextFactory _contextFactory;

    private static readonly JsonSerializerOptions JsonOpts = new()
    {
        ReferenceHandler = ReferenceHandler.IgnoreCycles,
        DefaultIgnoreCondition = JsonIgnoreCondition.WhenWritingNull,
        WriteIndented = false
    };

    public AuditService(INsdmsDbContextFactory contextFactory)
    {
        _contextFactory = contextFactory;
    }

    public async Task LogActionAsync(string entityName, int recordId, string actionName, string actor, object? beforeState = null, object? afterState = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        LogAction(db, entityName, recordId, actionName, actor, beforeState, afterState);
        await db.SaveChangesAsync();
    }

    public Task LogAsync(string entityName, int recordId, string actionName, string actor, object? afterState = null)
    {
        return LogActionAsync(entityName, recordId, actionName, actor, null, afterState);
    }

    public Task LogAsync(string entityName, int recordId, string actionName, string description, string actor, object? afterState = null)
    {
        return LogActionAsync(entityName, recordId, actionName, actor, null, new { description, data = afterState });
    }

    public void LogAction(INsdmsDbContext db, string entityName, int recordId, string actionName, string actor, object? beforeState = null, object? afterState = null)
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

    public Task LogActionAsync(INsdmsDbContext db, string entityName, int recordId, string actionName, string actor, object? beforeState = null, object? afterState = null)
    {
        LogAction(db, entityName, recordId, actionName, actor, beforeState, afterState);
        return Task.CompletedTask;
    }
}
