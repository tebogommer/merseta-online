using Nsdms.Domain.Entities;

namespace Nsdms.Application.Common.Interfaces;

public interface ISystemConfigurationService
{
    Task<string?> GetValueAsync(string key, string? defaultValue = null);
    Task<T> GetValueAsync<T>(string key, T defaultValue);
    Task<List<SystemConfig>> GetAllConfigsAsync(string? category = null);
    Task<SystemConfig> SetConfigAsync(string key, string value, string category = "General", string? description = null, string dataType = "String", string currentUsername = "SYSTEM");
    Task<bool> DeleteConfigAsync(string key, string currentUsername = "SYSTEM");
    Task SeedDefaultConfigsAsync();
}

public interface IFeatureFlagService
{
    Task<bool> IsFeatureEnabledAsync(string featureKey, bool defaultIfMissing = false);
    Task<List<SystemFeatureFlag>> GetAllFeatureFlagsAsync(string? category = null);
    Task<SystemFeatureFlag> SetFeatureFlagAsync(string featureKey, bool isEnabled, string? featureName = null, string category = "Integrations", string? description = null, string currentUsername = "SYSTEM");
    Task SeedDefaultFeatureFlagsAsync();
}
