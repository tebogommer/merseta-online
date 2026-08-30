using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class SystemConfigurationService : ISystemConfigurationService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IConfiguration _configuration;
    private readonly AuditService _audit;

    public SystemConfigurationService(INsdmsDbContextFactory contextFactory, IConfiguration configuration, AuditService audit)
    {
        _contextFactory = contextFactory;
        _configuration = configuration;
        _audit = audit;
    }

    public async Task<string?> GetValueAsync(string key, string? defaultValue = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var config = await db.SystemConfigs.FirstOrDefaultAsync(c => c.ConfigKey == key && c.IsActive);
        if (config != null && !string.IsNullOrEmpty(config.ConfigValue))
        {
            return config.ConfigValue;
        }

        var appSettingsVal = _configuration[key];
        if (!string.IsNullOrEmpty(appSettingsVal))
        {
            return appSettingsVal;
        }

        return defaultValue;
    }

    public async Task<T> GetValueAsync<T>(string key, T defaultValue)
    {
        var stringVal = await GetValueAsync(key);
        if (stringVal == null) return defaultValue;

        try
        {
            var targetType = Nullable.GetUnderlyingType(typeof(T)) ?? typeof(T);
            return (T)Convert.ChangeType(stringVal, targetType, System.Globalization.CultureInfo.InvariantCulture);
        }
        catch
        {
            return defaultValue;
        }
    }

    public async Task<List<SystemConfig>> GetAllConfigsAsync(string? category = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.SystemConfigs.Where(c => c.IsActive);
        if (!string.IsNullOrWhiteSpace(category))
        {
            query = query.Where(c => c.ConfigCategory == category);
        }

        return await query.OrderBy(c => c.ConfigCategory).ThenBy(c => c.ConfigKey).ToListAsync();
    }

    public async Task<SystemConfig> SetConfigAsync(string key, string value, string category = "General", string? description = null, string dataType = "String", string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.SystemConfigs.FirstOrDefaultAsync(c => c.ConfigKey == key);

        if (existing == null)
        {
            var config = new SystemConfig
            {
                ConfigKey = key,
                ConfigValue = value,
                ConfigCategory = category,
                Description = description,
                DataType = dataType,
                IsActive = true,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = currentUsername
            };

            db.SystemConfigs.Add(config);
            await db.SaveChangesAsync();

            _audit.LogAction(db, "SystemConfig", config.Id, "CreateConfig", currentUsername, null, config);
            await db.SaveChangesAsync();
            return config;
        }
        else
        {
            var before = new { existing.ConfigValue, existing.ConfigCategory, existing.Description, existing.IsActive };
            existing.ConfigValue = value;
            existing.ConfigCategory = category;
            if (description != null) existing.Description = description;
            existing.DataType = dataType;
            existing.IsActive = true;
            existing.ModifiedAt = DateTime.UtcNow;
            existing.ModifiedBy = currentUsername;

            _audit.LogAction(db, "SystemConfig", existing.Id, "UpdateConfig", currentUsername, before, existing);
            await db.SaveChangesAsync();
            return existing;
        }
    }

    public async Task<bool> DeleteConfigAsync(string key, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var config = await db.SystemConfigs.FirstOrDefaultAsync(c => c.ConfigKey == key);
        if (config == null) return false;

        var before = new { config.ConfigKey, config.ConfigValue, config.IsActive };
        config.IsActive = false;
        config.ModifiedAt = DateTime.UtcNow;
        config.ModifiedBy = currentUsername;

        _audit.LogAction(db, "SystemConfig", config.Id, "DeleteConfig", currentUsername, before, config);
        await db.SaveChangesAsync();
        return true;
    }

    public async Task SeedDefaultConfigsAsync()
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var defaults = new List<(string Key, string Value, string Cat, string Desc, string Type)>
        {
            // Storage
            ("Storage:LocalRootPath", @"C:\Antigravity\nsdms_storage\documents", "Storage", "Root physical filesystem path for document attachments and evidence uploads.", "String"),
            ("Storage:MaxUploadSizeBytes", "26214400", "Storage", "Maximum allowed file size per upload in bytes (25 MB).", "Integer"),
            ("Storage:AllowedExtensions", ".pdf,.png,.jpg,.jpeg,.xlsx,.docx,.csv", "Storage", "Comma-separated list of permitted file extensions.", "String"),
            ("Storage:DefaultProvider", "Local", "Storage", "Default document storage engine (Local, AzureBlob).", "String"),

            // Integrations
            ("Integrations:DynamicsGp:EndpointUrl", "https://erp.merseta.org.za/GP/v1/Transactions", "Integrations", "Microsoft Dynamics GP Web Services transaction endpoint.", "String"),
            ("Integrations:SageErp:EndpointUrl", "https://sage.merseta.org.za/api/v2/disbursements", "Integrations", "Sage ERP 300 / Pastel Evolution disbursement webhook endpoint.", "String"),
            ("Integrations:LiveSarsFtp:Host", "ftp.sars.gov.za", "Integrations", "Official SARS monthly SDL levy extraction server.", "String"),
            ("Integrations:LiveSarsFtp:Port", "21", "Integrations", "SARS FTP communication port.", "Integer"),
            ("Integrations:SmsOtp:ProviderUrl", "https://api.smsportal.com/v1/sendsms", "Integrations", "Bulk SMS and OTP verification gateway provider URL.", "String"),

            // Finance & Grants
            ("Finance:MandatoryGrantRebatePercentage", "0.20", "Finance", "Mandatory grant rebate percentage (20% of 1% SDL).", "Decimal"),
            ("Finance:DiscretionaryGrantAdminCostCapPercentage", "0.075", "Finance", "Maximum allowable administrative project cost percentage (7.5%).", "Decimal"),
            ("Finance:Tranche1Percentage", "0.30", "Finance", "Initial mobilization tranche disbursement percentage (30%).", "Decimal"),
            ("Finance:Tranche2Percentage", "0.30", "Finance", "Mid-term learner progress tranche disbursement percentage (30%).", "Decimal"),
            ("Finance:Tranche3Percentage", "0.20", "Finance", "Assessment completion tranche disbursement percentage (20%).", "Decimal"),
            ("Finance:Tranche4Percentage", "0.20", "Finance", "Final trade test/qualification sign-off tranche percentage (20%).", "Decimal"),
            ("Finance:MandatoryApprovalDualSignOffThreshold", "500000.00", "Finance", "ZAR expenditure threshold requiring dual executive sign-off.", "Decimal"),

            // Governance & Statutory Deadlines
            ("Governance:WspAnnualSubmissionDeadline", "04-30", "Governance", "Annual statutory Workplace Skills Plan submission deadline (30 April).", "String"),
            ("Governance:WspExtensionRequestDeadline", "04-15", "Governance", "Final date for employers to request a WSP extension (15 April).", "String"),
            ("Governance:TrainingCommitteeQuorumEmployerRatio", "0.50", "Governance", "Statutory employer vs trade union member balance ratio (50%).", "Decimal"),
            ("Governance:ReviewCommitteeQuorumMinimumAttendees", "5", "Governance", "Minimum voting attendees required for ETQA / MANCO committee quorum.", "Integer"),
            ("Governance:WorkplaceMonitoringInspectionCycleDays", "180", "Governance", "Mandatory employer workplace monitoring inspection interval in days (6 months).", "Integer"),

            // General
            ("General:CallCenterPhone", "086 163 7738", "General", "Official merSETA contact center telephone line.", "String"),
            ("General:ApplicationTitle", "merSETA National Skills Development Management System", "General", "Enterprise portal application branding title.", "String")
        };

        bool anyAdded = false;
        foreach (var (key, val, cat, desc, type) in defaults)
        {
            var exists = await db.SystemConfigs.AnyAsync(c => c.ConfigKey == key);
            if (!exists)
            {
                db.SystemConfigs.Add(new SystemConfig
                {
                    ConfigKey = key,
                    ConfigValue = val,
                    ConfigCategory = cat,
                    Description = desc,
                    DataType = type,
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
