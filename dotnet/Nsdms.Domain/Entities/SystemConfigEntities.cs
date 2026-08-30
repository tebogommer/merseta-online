using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Runtime system configuration key-value overrides.
/// </summary>
public class SystemConfig : BaseEntity
{
    /// <summary>
    /// Unique configuration hierarchical key (e.g. General:AppVersion, Storage:LocalPath, Integrations:DynamicsGp:Endpoint).
    /// </summary>
    public string ConfigKey { get; set; } = string.Empty;

    /// <summary>
    /// Current configured runtime value.
    /// </summary>
    public string? ConfigValue { get; set; }

    /// <summary>
    /// Configuration category grouping (e.g. General, Storage, Integrations, Finance, Scheduler, Security).
    /// </summary>
    public string ConfigCategory { get; set; } = "General";

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public string Category { get => ConfigCategory; set => ConfigCategory = value; }

    /// <summary>
    /// Descriptive explanation and operational purpose of this configuration setting.
    /// </summary>
    public string? Description { get; set; }

    /// <summary>
    /// Data type encoding (e.g. String, Boolean, Integer, Decimal, Json).
    /// </summary>
    public string DataType { get; set; } = "String";

    /// <summary>
    /// Indicates whether the stored configuration value is encrypted with AES-256.
    /// </summary>
    public bool IsEncrypted { get; set; } = false;

    /// <summary>
    /// Indicates whether this configuration override is active.
    /// </summary>
    public bool IsActive { get; set; } = true;
}

/// <summary>
/// Granular runtime feature flags for enabling/disabling modules and integrations.
/// All external third-party integrations default to IsEnabled = false.
/// </summary>
public class SystemFeatureFlag : BaseEntity
{
    /// <summary>
    /// Unique feature flag key identifier (e.g. Integrations.DynamicsGp, Features.TradeTestOnlineBooking).
    /// </summary>
    public string FeatureKey { get; set; } = string.Empty;

    /// <summary>
    /// Human-readable display name of the feature flag.
    /// </summary>
    public string FeatureName { get; set; } = string.Empty;

    /// <summary>
    /// Business explanation and functional scope of the feature toggle.
    /// </summary>
    public string? Description { get; set; }

    /// <summary>
    /// State toggle (true = active, false = disabled/simulated fallback).
    /// </summary>
    public bool IsEnabled { get; set; } = false;

    /// <summary>
    /// Feature flag module grouping category (e.g. Integrations, Storage, Scheduler, Workflow, Compliance).
    /// </summary>
    public string FeatureCategory { get; set; } = "Integrations";

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public string Category { get => FeatureCategory; set => FeatureCategory = value; }

    /// <summary>
    /// Indicates whether the feature flag definition is active.
    /// </summary>
    public bool IsActive { get; set; } = true;
}
