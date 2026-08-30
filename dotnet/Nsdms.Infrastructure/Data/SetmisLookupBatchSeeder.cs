using System.Reflection;
using System.Text.Json;
using Microsoft.EntityFrameworkCore;
using Nsdms.Domain.Common;
using Nsdms.Domain.Lookups;

namespace Nsdms.Infrastructure.Data;

public class SetmisLookupRawItem
{
    public string code { get; set; } = string.Empty;
    public string name { get; set; } = string.Empty;
    public string description { get; set; } = string.Empty;
}

public static class SetmisLookupBatchSeeder
{
    private static readonly Lazy<Dictionary<string, List<SetmisLookupRawItem>>> _cachedData = new(LoadFromResource);

    public static Dictionary<string, List<SetmisLookupRawItem>> GetAllCategories() => _cachedData.Value;

    public static List<SetmisLookupRawItem> GetCategoryItems(string categoryKey)
    {
        return _cachedData.Value.TryGetValue(categoryKey, out var items) ? items : new();
    }

    private static Dictionary<string, List<SetmisLookupRawItem>> LoadFromResource()
    {
        var assembly = typeof(SetmisLookupBatchSeeder).Assembly;
        using var stream = assembly.GetManifestResourceStream("Nsdms.Infrastructure.Data.setmis_lookups.json");
        if (stream != null)
        {
            using var reader = new StreamReader(stream);
            var json = reader.ReadToEnd();
            var result = JsonSerializer.Deserialize<Dictionary<string, List<SetmisLookupRawItem>>>(json);
            if (result != null) return result;
        }

        // Fallback to direct file read if running in development or tests
        var fallbackPath = Path.Combine(AppContext.BaseDirectory, "Data", "setmis_lookups.json");
        if (File.Exists(fallbackPath))
        {
            var json = File.ReadAllText(fallbackPath);
            var result = JsonSerializer.Deserialize<Dictionary<string, List<SetmisLookupRawItem>>>(json);
            if (result != null) return result;
        }

        // Fallback relative to project folder
        var devPath = Path.Combine(Directory.GetCurrentDirectory(), "dotnet", "Nsdms.Infrastructure", "Data", "setmis_lookups.json");
        if (File.Exists(devPath))
        {
            var json = File.ReadAllText(devPath);
            var result = JsonSerializer.Deserialize<Dictionary<string, List<SetmisLookupRawItem>>>(json);
            if (result != null) return result;
        }

        return new();
    }
}
