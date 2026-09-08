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
        
        var altKey = key.Contains(':') ? key.Replace(':', '.') : key.Replace('.', ':');
        var config = await db.SystemConfigs.FirstOrDefaultAsync(c => (c.ConfigKey == key || c.ConfigKey == altKey) && c.IsActive);
        if (config != null && !string.IsNullOrEmpty(config.ConfigValue))
        {
            return config.ConfigValue;
        }

        var appSettingsVal = _configuration[key]
            ?? _configuration[altKey]
            ?? _configuration[$"NsdmsSettings:{key.Replace('.', ':')}"]
            ?? _configuration[$"NsdmsSettings:{key}"];

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
            ("General:ApplicationTitle", "merSETA National Skills Development Management System", "General", "Enterprise portal application branding title.", "String"),
            ("System:BaseUrl", "https://nsdms.merseta.org.za", "General", "Canonical base URL for public document verification and QR code links.", "String"),

            // Statutory SLAs & Lifecycles
            ("WorkplaceApproval:InspectionSlaBusinessDays", "20", "Statutory SLAs", "Statutory turnaround time in business days for initial workplace audit inspection.", "Integer"),
            ("WorkplaceApproval:DefaultValidityYears", "3", "Statutory SLAs", "Statutory accreditation validity tenure in years.", "Integer"),
            ("LearnerLifecycle:TerminationInvestigationSlaDays", "14", "Statutory SLAs", "Dispute investigation SLA in business days for unilateral learner contract terminations.", "Integer"),
            ("TrainingProvider:SiteInspectionSlaBusinessDays", "5", "Statutory SLAs", "SDP accreditation initial inspection SLA in business days.", "Integer"),
            ("LearnerRegistration:MaxSignatureElapsedBusinessDays", "30", "Statutory SLAs", "Statutory window in business days for submitting executed tripartite learnership agreements.", "Integer"),
            ("Etqa:AssessorRegistrationCycleYears", "3", "Statutory SLAs", "Assessor and Moderator accreditation renewal validity cycle in years.", "Integer"),
            ("TradeTest:MaxAllowedAttempts", "3", "Statutory SLAs", "Maximum permitted attempts for trade test qualification assessments per NAMB regulations.", "Integer"),

            // Fiscal Calendar Defaults
            ("Fiscal:DefaultStartMonthDay", "04-01", "Fiscal Calendar", "Default statutory financial scheme year start date in MM-DD format (01 April).", "String"),
            ("Fiscal:DefaultEndMonthDay", "03-31", "Fiscal Calendar", "Default statutory financial scheme year end date in MM-DD format (31 March).", "String"),
            ("Fiscal:DefaultQ1Start", "04-01", "Fiscal Calendar", "Default statutory Quarter 1 start date in MM-DD format (01 April).", "String"),
            ("Fiscal:DefaultQ1End", "06-30", "Fiscal Calendar", "Default statutory Quarter 1 end date in MM-DD format (30 June).", "String"),
            ("Fiscal:DefaultQ2Start", "07-01", "Fiscal Calendar", "Default statutory Quarter 2 start date in MM-DD format (01 July).", "String"),
            ("Fiscal:DefaultQ2End", "09-30", "Fiscal Calendar", "Default statutory Quarter 2 end date in MM-DD format (30 September).", "String"),
            ("Fiscal:DefaultQ3Start", "10-01", "Fiscal Calendar", "Default statutory Quarter 3 start date in MM-DD format (01 October).", "String"),
            ("Fiscal:DefaultQ3End", "12-31", "Fiscal Calendar", "Default statutory Quarter 3 end date in MM-DD format (31 December).", "String"),
            ("Fiscal:DefaultQ4Start", "01-01", "Fiscal Calendar", "Default statutory Quarter 4 start date in MM-DD format (01 January).", "String"),
            ("Fiscal:DefaultQ4End", "03-31", "Fiscal Calendar", "Default statutory Quarter 4 end date in MM-DD format (31 March).", "String"),
            ("Fiscal:AllowAnyAdminReviewer", "true", "Fiscal Calendar", "Whether any user with Admin/SuperAdmin role can review and approve a financial year if they are not the submitter.", "Boolean"),
            ("Fiscal:EnforceMakerCheckerSegregation", "true", "Fiscal Calendar", "Enforce strict Maker-Checker segregation preventing the submitter from approving their own financial year.", "Boolean"),
            ("Fiscal:RequiredReviewRole", "Admin,SuperAdmin", "Fiscal Calendar", "Comma-separated list of roles authorized to review and adjudicate financial scheme years.", "String"),
            ("Fiscal:AllowMultipleActiveYears", "false", "Fiscal Calendar", "Whether multiple financial scheme years can be concurrently active without auto-deactivating predecessor years.", "Boolean"),

            // Institutional Closures & Holidays
            ("Calendar:ObserveInstitutionalClosuresInSla", "true", "Calendar & Holidays", "Whether human officer workflow SLAs universally skip registered merSETA institutional closures.", "Boolean"),
            ("Calendar:DefaultYearEndShutdownStart", "12-24", "Calendar & Holidays", "Default calendar start date for merSETA annual year-end shutdown in MM-DD format (24 December).", "String"),
            ("Calendar:DefaultYearEndShutdownEnd", "01-03", "Calendar & Holidays", "Default calendar end date for merSETA annual year-end shutdown in MM-DD format (03 January).", "String"),
            ("Calendar:RequireMakerCheckerForClosures", "false", "Calendar & Holidays", "Whether newly created ad-hoc closures require secondary officer approval before activation.", "Boolean"),
            ("TradeTest:ResultsUploadSlaDays", "5", "Statutory SLAs", "SLA in days for accredited trade test centres to upload practical assessment results.", "Integer"),
            ("Banking:CoolingOffPeriodDays", "14", "Governance", "Mandatory cooling-off period in days for updating bank disbursement accounts.", "Integer"),
            ("Banking:ConfirmationLetterMaxAgeDays", "90", "Compliance", "Maximum allowable age in days for uploaded bank confirmation letters (FICA/Treasury).", "Integer"),
            ("Workflow:SlaWarningThresholdHours", "24", "Governance", "Lead time in hours before workflow task due date to trigger proactive escalation warnings.", "Integer"),

            // Levies & Statutory Allocations
            ("Levy:MandatoryGrantRate", "0.200", "Finance", "Statutory Mandatory Grant rebate rate (20% of 1% SDL levy).", "Decimal"),
            ("Levy:DiscretionaryGrantRate", "0.495", "Finance", "Statutory Discretionary Grant strategic allocation rate (49.5% of 1% SDL levy).", "Decimal"),
            ("Levy:AdminRate", "0.105", "Finance", "Statutory SETA administration levy expenditure rate (10.5% of 1% SDL levy).", "Decimal"),
            ("Levy:QctoRate", "0.005", "Finance", "Statutory QCTO regulatory transfer levy rate (0.5% of 1% SDL levy).", "Decimal"),
            ("Levy:SarsTrailerToleranceCents", "0.05", "Finance", "Allowable gross reconciliation discrepancy tolerance in ZAR for SARS monthly levy trailers.", "Decimal"),
            ("Governance:SmallEmployerMaxEmployeeCount", "50", "Governance", "Headcount threshold exempting small employers from mandatory labour union training committee quorum.", "Integer"),

            // Apprentice, Trade & Mentor Rules
            ("WorkplaceApproval:DefaultStandardMentorRatio", "4", "Apprentice & Trade", "Statutory default mentor-to-apprentice ratio per qualified artisan.", "Integer"),
            ("WorkplaceApproval:DefaultMaxMentorRatio", "6", "Apprentice & Trade", "Maximum allowable apprentice capacity per artisan under approved variance.", "Integer"),
            ("WorkplaceApproval:MinMentorExperienceYears", "3", "Apprentice & Trade", "Minimum post-qualification artisan experience required before supervising apprentices.", "Integer"),
            ("TradeTest:CreditRetentionPassRatePercentage", "50.0", "Apprentice & Trade", "Minimum evaluated practical task pass percentage to qualify for modular credit retention.", "Decimal"),
            ("TradeTest:CreditRetentionWindowMonths", "18", "Apprentice & Trade", "Validity window in months for retained practical task credits across subsequent attempts.", "Integer"),
            ("TradeTest:DefaultPassMarkPercentage", "70.0", "Apprentice & Trade", "National benchmark practical task pass mark percentage for artisan qualifications.", "Decimal"),
            ("TradeTest:QaAuditSamplingPercentage", "10", "Apprentice & Trade", "Regional QA random audit inspection sampling rate percentage for trade test centres.", "Integer"),

            // UI & Display Defaults
            ("UiDefaults:DebounceIntervalMs", "300", "UI & Display", "Search input debouncing interval in milliseconds.", "Integer"),
            ("UiDefaults:SearchMinCharacters", "2", "UI & Display", "Minimum characters required to trigger lookup and catalog autocomplete searches.", "Integer"),
            ("UiDefaults:DefaultRowsPerPage", "20", "UI & Display", "Default pagination page size across enterprise data grids.", "Integer")
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
