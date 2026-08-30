namespace Nsdms.Application.Services;

using System.Text.Json;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;

public class GridViewPreferenceDto
{
    public string GridKey { get; set; } = string.Empty;
    public int PageSize { get; set; } = 20;
    public string? SortColumn { get; set; }
    public bool SortDescending { get; set; }
    public List<string> HiddenColumns { get; set; } = new();
    public string Density { get; set; } = "dense"; // comfortable, dense
}

public interface IGridViewPreferenceService
{
    Task<GridViewPreferenceDto> GetPreferencesAsync(string gridKey, string username);
    Task SavePreferencesAsync(GridViewPreferenceDto preferences, string username);
    Task ResetPreferencesAsync(string gridKey, string username);
}

public class GridViewPreferenceService : IGridViewPreferenceService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private static readonly Dictionary<string, GridViewPreferenceDto> _memoryFallback = new();

    public GridViewPreferenceService(INsdmsDbContextFactory contextFactory)
    {
        _contextFactory = contextFactory;
    }

    public async Task<GridViewPreferenceDto> GetPreferencesAsync(string gridKey, string username)
    {
        var cacheKey = $"{username}:{gridKey}";
        if (_memoryFallback.TryGetValue(cacheKey, out var cached))
        {
            return cached;
        }

        try
        {
            using var db = _contextFactory.CreateDbContext();
            var setting = await db.SystemConfigs
                .FirstOrDefaultAsync(s => s.ConfigCategory == "GridPreferences" && s.ConfigKey == cacheKey);

            if (setting != null && !string.IsNullOrWhiteSpace(setting.ConfigValue))
            {
                var dto = JsonSerializer.Deserialize<GridViewPreferenceDto>(setting.ConfigValue);
                if (dto != null)
                {
                    _memoryFallback[cacheKey] = dto;
                    return dto;
                }
            }
        }
        catch
        {
            // Fallback gracefully
        }

        var defaultDto = new GridViewPreferenceDto
        {
            GridKey = gridKey,
            PageSize = 20,
            Density = "dense"
        };
        _memoryFallback[cacheKey] = defaultDto;
        return defaultDto;
    }

    public async Task SavePreferencesAsync(GridViewPreferenceDto preferences, string username)
    {
        var cacheKey = $"{username}:{preferences.GridKey}";
        _memoryFallback[cacheKey] = preferences;

        try
        {
            using var db = _contextFactory.CreateDbContext();
            var setting = await db.SystemConfigs
                .FirstOrDefaultAsync(s => s.ConfigCategory == "GridPreferences" && s.ConfigKey == cacheKey);

            var json = JsonSerializer.Serialize(preferences);
            if (setting != null)
            {
                setting.ConfigValue = json;
                setting.ModifiedAt = DateTime.UtcNow;
                setting.ModifiedBy = username;
            }
            else
            {
                db.SystemConfigs.Add(new SystemConfig
                {
                    ConfigCategory = "GridPreferences",
                    ConfigKey = cacheKey,
                    ConfigValue = json,
                    DataType = "JSON",
                    Description = $"User view preferences for grid {preferences.GridKey}",
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = username
                });
            }

            await db.SaveChangesAsync();
        }
        catch
        {
            // In-memory fallback retains state for current runtime
        }
    }

    public async Task ResetPreferencesAsync(string gridKey, string username)
    {
        var cacheKey = $"{username}:{gridKey}";
        _memoryFallback.Remove(cacheKey);

        try
        {
            using var db = _contextFactory.CreateDbContext();
            var setting = await db.SystemConfigs
                .FirstOrDefaultAsync(s => s.ConfigCategory == "GridPreferences" && s.ConfigKey == cacheKey);

            if (setting != null)
            {
                db.SystemConfigs.Remove(setting);
                await db.SaveChangesAsync();
            }
        }
        catch
        {
            // Graceful fallback
        }
    }
}

