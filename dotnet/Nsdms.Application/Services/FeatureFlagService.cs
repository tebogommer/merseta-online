using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class FeatureFlagService : IFeatureFlagService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IConfiguration _configuration;
    private readonly AuditService _audit;

    public FeatureFlagService(INsdmsDbContextFactory contextFactory, IConfiguration configuration, AuditService audit)
    {
        _contextFactory = contextFactory;
        _configuration = configuration;
        _audit = audit;
    }

    public async Task<bool> IsFeatureEnabledAsync(string featureKey, bool defaultIfMissing = false)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var flag = await db.SystemFeatureFlags.FirstOrDefaultAsync(f => f.FeatureKey == featureKey && f.IsActive);
        if (flag != null)
        {
            return flag.IsEnabled;
        }

        var appSettingsVal = _configuration.GetValue<bool?>($"Features:{featureKey}");
        if (appSettingsVal.HasValue)
        {
            return appSettingsVal.Value;
        }

        return defaultIfMissing;
    }

    public async Task<List<SystemFeatureFlag>> GetAllFeatureFlagsAsync(string? category = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.SystemFeatureFlags.Where(f => f.IsActive);
        if (!string.IsNullOrWhiteSpace(category))
        {
            query = query.Where(f => f.FeatureCategory == category);
        }

        return await query.OrderBy(f => f.FeatureCategory).ThenBy(f => f.FeatureKey).ToListAsync();
    }

    public async Task<SystemFeatureFlag> SetFeatureFlagAsync(string featureKey, bool isEnabled, string? featureName = null, string category = "Integrations", string? description = null, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.SystemFeatureFlags.FirstOrDefaultAsync(f => f.FeatureKey == featureKey);

        if (existing == null)
        {
            var flag = new SystemFeatureFlag
            {
                FeatureKey = featureKey,
                FeatureName = featureName ?? featureKey,
                Description = description,
                FeatureCategory = category,
                IsEnabled = isEnabled,
                IsActive = true,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = currentUsername
            };

            db.SystemFeatureFlags.Add(flag);
            await db.SaveChangesAsync();

            _audit.LogAction(db, "SystemFeatureFlag", flag.Id, "CreateFeatureFlag", currentUsername, null, flag);
            await db.SaveChangesAsync();
            return flag;
        }
        else
        {
            var before = new { existing.IsEnabled, existing.FeatureName, existing.FeatureCategory, existing.Description };
            existing.IsEnabled = isEnabled;
            if (featureName != null) existing.FeatureName = featureName;
            if (description != null) existing.Description = description;
            existing.FeatureCategory = category;
            existing.IsActive = true;
            existing.ModifiedAt = DateTime.UtcNow;
            existing.ModifiedBy = currentUsername;

            _audit.LogAction(db, "SystemFeatureFlag", existing.Id, "UpdateFeatureFlag", currentUsername, before, existing);
            await db.SaveChangesAsync();
            return existing;
        }
    }

    public async Task SeedDefaultFeatureFlagsAsync()
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var defaults = new List<(string Key, string Name, string Cat, string Desc, bool Enabled)>
        {
            ("Integrations.DynamicsGp", "Microsoft Dynamics GP ERP Ledger", "Integrations", "Connects tranche disbursements directly to Microsoft Dynamics GP accounts payable. (Off by default)", false),
            ("Integrations.SageErp", "Sage ERP Financial Ledger Connector", "Integrations", "Integrates rebate transactions with Sage 300 / Pastel Evolution. (Off by default)", false),
            ("Integrations.LiveSarsFtp", "Automated SARS Levy Ingestion Daemon", "Integrations", "Automates hourly polling of SARS levy file drops. (Off by default)", false),
            ("Integrations.SmsOtp", "SMS / Email OTP Verification Gateway", "Integrations", "Requires One-Time-PIN confirmation for legal sign-offs. (Off by default)", false),
            ("Storage.AzureBlob", "Azure Blob Storage Provider", "Storage", "Stores document attachments in cloud Azure Blob Storage containers. (Off by default)", false),
            ("Scheduler.BackgroundWorker", "Background Cron Task & SLA Escalation Daemon", "Scheduler", "Runs automated background task escalation and recurring levy reconciliation jobs. (Off by default)", false),
            ("Pdfs.WatermarksAndQrCodes", "Official PDF Certificates & MOA Generation", "Compliance", "Generates branded PDF trade test certificates and MOA contracts with verification QR codes.", true),
            ("Compliance.StrictRsaIdLuhn", "Strict RSA ID Luhn Checksum Enforcement", "Compliance", "Enforces strict mathematical 13-digit Luhn checksum verification on all person registrations.", true)
        };

        bool anyAdded = false;
        foreach (var (key, name, cat, desc, enabled) in defaults)
        {
            var exists = await db.SystemFeatureFlags.AnyAsync(f => f.FeatureKey == key);
            if (!exists)
            {
                db.SystemFeatureFlags.Add(new SystemFeatureFlag
                {
                    FeatureKey = key,
                    FeatureName = name,
                    FeatureCategory = cat,
                    Description = desc,
                    IsEnabled = enabled,
                    IsActive = true,
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = "SystemSeeder"
                });
                anyAdded = true;
            }
        }

        if (anyAdded)
        {
            await db.SaveChangesAsync();
        }
    }
}
