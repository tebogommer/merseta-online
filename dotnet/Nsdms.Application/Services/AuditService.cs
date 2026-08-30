using Nsdms.Application.Common;
using Nsdms.Domain.Entities;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace Nsdms.Application.Services;

public interface IAuditService
{
    Task LogActionAsync(string entityName, int recordId, string actionName, string actor, object? beforeState = null, object? afterState = null);
    Task LogAsync(string entityName, int recordId, string actionName, string actor, object? afterState = null);
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
            MetadataJson = JsonSerializer.Serialize(metadata, JsonOpts),
            Timestamp = DateTime.UtcNow
        };

        db.AuditLogs.Add(log);
    }

    public Task LogActionAsync(INsdmsDbContext db, string entityName, int recordId, string actionName, string actor, object? beforeState = null, object? afterState = null)
    {
        LogAction(db, entityName, recordId, actionName, actor, beforeState, afterState);
        return Task.CompletedTask;
    }
}
