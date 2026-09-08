namespace Nsdms.Domain.Common;

/// <summary>
/// Central statutory benchmark constants defined by national legislation (Skills Development Act, SAQA NLRD, DHET SETMIS).
/// Replaces magic literals across entity schemas, validators, and extract pipelines.
/// </summary>
public static class StatutoryConstants
{
    /// <summary>
    /// Statutory SETA identifier for the Manufacturing, Engineering and Related Services SETA (merSETA).
    /// </summary>
    public const string MerSetaId = "17";

    /// <summary>
    /// Statutory Supplier Code assigned to merSETA by SAQA for NLRD extracts (fixed at 599).
    /// </summary>
    public const string SaqaMerSetaSupplierCode = "599";

    /// <summary>
    /// Statutory country code for the Republic of South Africa (ISO 3166-1 alpha-2).
    /// </summary>
    public const string DefaultCountryCode = "ZA";

    /// <summary>
    /// Standard automated process audit actor identifier.
    /// </summary>
    public const string SystemActor = "SYSTEM";

    /// <summary>
    /// Default standard artisan mentor-to-apprentice ratio per qualified artisan.
    /// </summary>
    public const int DefaultStandardMentorRatio = 4;

    /// <summary>
    /// Default maximum allowable apprentice capacity per artisan under approved variance.
    /// </summary>
    public const int DefaultMaxMentorRatio = 6;

    /// <summary>
    /// Minimum post-qualification artisan experience required before supervising apprentices (years).
    /// </summary>
    public const int MinMentorExperienceYears = 3;

    /// <summary>
    /// Statutory turnaround time in business days for initial workplace audit inspection.
    /// </summary>
    public const int DefaultWorkplaceInspectionSlaBusinessDays = 20;

    /// <summary>
    /// Dispute investigation SLA in business days for unilateral learner contract terminations.
    /// </summary>
    public const int DefaultTerminationInvestigationSlaBusinessDays = 14;

    /// <summary>
    /// Maximum permitted attempts for trade test qualification assessments per NAMB regulations.
    /// </summary>
    public const int DefaultMaxTradeTestAttempts = 3;

    /// <summary>
    /// Validity window in months for retained practical task credits across subsequent attempts.
    /// </summary>
    public const int DefaultTradeTestCreditRetentionMonths = 18;

    /// <summary>
    /// Minimum evaluated practical task pass percentage to qualify for modular credit retention.
    /// </summary>
    public const decimal DefaultTradeTestCreditRetentionPassRatePercentage = 50.0m;

    /// <summary>
    /// DOFA expenditure threshold requiring dual executive CFO sign-off (ZAR).
    /// </summary>
    public const decimal DefaultCfoApprovalThreshold = 500000.00m;
}
