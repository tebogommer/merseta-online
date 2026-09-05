using Nsdms.Domain.Entities;

namespace Nsdms.Application.Common.Utilities;

/// <summary>
/// Domain validation engine for Artisan Recognition of Prior Learning (ARPL - Section 28)
/// adhering to the statutory rules of 'ARPL Registration Application Use Case 27012023.NMok.signed.pdf' (Section 5).
/// </summary>
public static class ArplTradeValidator
{
    /// <summary>
    /// The 17 statutory designated trades that have toolkits in place per Section 5 (Page 18).
    /// Only these 17 trades may be admitted under ARPL Qualifying Category 7 (ARPL Toolkits Assessment).
    /// </summary>
    public static readonly HashSet<string> DesignatedToolkitTrades = new(StringComparer.OrdinalIgnoreCase)
    {
        "Diesel Mechanic",
        "Motor Mechanic",
        "Boilermaker",
        "Welder",
        "Fitter",
        "Fitter & Turner",
        "Fitter and Turner",
        "Electrician",
        "Heavy Equipment Mechanic",
        "Instrument Mechanic",
        "Instrument Mechanician",
        "Lift Mechanic",
        "Shipbuilder",
        "Panel Beater",
        "Vehicle Painter",
        "Bricklayer",
        "Plumber",
        "Carpenter",
        "Sheet fed-Lithograph",
        "Sheet fed-Lithographer",
        "Sheet-fed Lithographer"
    };

    /// <summary>
    /// Checks whether a designated trade title requires an ARPL toolkit assessment.
    /// </summary>
    public static bool RequiresToolkit(string tradeTitle)
    {
        if (string.IsNullOrWhiteSpace(tradeTitle)) return false;
        return DesignatedToolkitTrades.Contains(tradeTitle.Trim());
    }

    /// <summary>
    /// Alias for RequiresToolkit to check if a trade is a designated toolkit trade.
    /// </summary>
    public static bool IsDesignatedToolkitTrade(string tradeTitle) => RequiresToolkit(tradeTitle);

    /// <summary>
    /// Evaluates category compliance based on years of experience and certificate flag.
    /// </summary>
    public static (bool Passed, string? Message) EvaluateCategoryCompliance(
        ArplQualifyingCategory category,
        decimal yearsExperience,
        bool hasCertificate,
        string tradeTitle)
    {
        int months = (int)(yearsExperience * 12);
        var result = category switch
        {
            ArplQualifyingCategory.Category1_Min3Years_N2 => ValidateQualifyingCategory(category, tradeTitle, months, hasN2Certificate: hasCertificate),
            ArplQualifyingCategory.Category4_Min18Months_NcvLevel4 => ValidateQualifyingCategory(category, tradeTitle, months, hasNcvLevel4: hasCertificate),
            ArplQualifyingCategory.Category6_Min4Years_Grade9 => ValidateQualifyingCategory(category, tradeTitle, months, hasGrade9OrStandard7: hasCertificate),
            _ => ValidateQualifyingCategory(category, tradeTitle, months)
        };
        return (result.IsValid, result.ErrorMessage ?? "Compliant with category requirements.");
    }

    /// <summary>
    /// Evaluates credit retention for candidate task assessments.
    /// </summary>
    public static (bool Eligible, List<TradeTestTask> PassedTasks, List<TradeTestTask> FailedTasks, DateTime? ExpiryDate) EvaluateCreditRetention(
        IEnumerable<TradeTestTask> tasks,
        DateTime assessmentDate)
    {
        var taskList = tasks.ToList();
        var (qualifies, passRate, expiry) = EvaluateTaskCreditRetention(taskList, assessmentDate);
        var passed = taskList.Where(t => t.IsCompetent || (t.TotalMarksAvailable > 0 && (t.MarksObtained / t.TotalMarksAvailable) * 100m >= t.PassPercentage)).ToList();
        var failed = taskList.Except(passed).ToList();
        return (qualifies, passed, failed, qualifies ? expiry : null);
    }

    /// <summary>
    /// Validates whether a candidate's trade and experience satisfy the chosen ARPL Qualifying Category.
    /// </summary>
    public static (bool IsValid, string? ErrorMessage) ValidateQualifyingCategory(
        ArplQualifyingCategory category,
        string tradeTitle,
        int experienceMonths,
        bool hasN2Certificate = false,
        bool hasEngineeringNqf3 = false,
        bool hasTechnicalGrade12 = false,
        bool hasNcvLevel4 = false,
        bool hasN6OrNationalDiploma = false,
        bool hasGrade9OrStandard7 = false,
        bool hasCompletedToolkitAssessment = false,
        bool hasCompletedTradeLearnership = false)
    {
        bool isToolkitTrade = RequiresToolkit(tradeTitle);

        switch (category)
        {
            case ArplQualifyingCategory.Category1_Min3Years_N2:
                if (experienceMonths < 36)
                    return (false, "Category 1 requires a minimum of 3 years (36 months) relevant work experience in South Africa.");
                if (!hasN2Certificate)
                    return (false, "Category 1 requires an N2 Certificate including Relevant Trade Theory.");
                return (true, null);

            case ArplQualifyingCategory.Category2_Min3Years_NqfLevel3:
                if (experienceMonths < 36)
                    return (false, "Category 2 requires a minimum of 3 years (36 months) relevant work experience in South Africa.");
                if (!hasEngineeringNqf3)
                    return (false, "Category 2 requires a Relevant Engineering NQF Level 3 Certificate.");
                return (true, null);

            case ArplQualifyingCategory.Category3_Min3Years_TechnicalGrade12:
                if (experienceMonths < 36)
                    return (false, "Category 3 requires a minimum of 3 years (36 months) relevant work experience in South Africa.");
                if (!hasTechnicalGrade12)
                    return (false, "Category 3 requires a Technical Grade 12 with Maths, Engineering Science and Related Theory Subject.");
                return (true, null);

            case ArplQualifyingCategory.Category4_Min18Months_NcvLevel4:
                if (experienceMonths < 18)
                    return (false, "Category 4 requires a minimum of 18 months relevant work experience in South Africa.");
                if (!hasNcvLevel4)
                    return (false, "Category 4 requires a Relevant Engineering NCV Level 4 Certificate.");
                return (true, null);

            case ArplQualifyingCategory.Category5_Min18Months_N6OrNationalDiploma:
                if (experienceMonths < 18)
                    return (false, "Category 5 requires a minimum of 18 months relevant work experience in South Africa.");
                if (!hasN6OrNationalDiploma)
                    return (false, "Category 5 requires an N6 certificate or National Technical Diploma directly related to trade theory.");
                return (true, null);

            case ArplQualifyingCategory.Category6_Min4Years_Grade9:
                if (experienceMonths < 48)
                    return (false, "Category 6 requires a minimum of 4 years (48 months) relevant work experience in South Africa.");
                if (!hasGrade9OrStandard7)
                    return (false, "Category 6 requires a minimum education of Grade 9 (Standard 7) or equivalent.");
                return (true, null);

            case ArplQualifyingCategory.Category7_Min3Years_ToolkitAssessment:
                if (!isToolkitTrade)
                    return (false, $"Trade '{tradeTitle}' does not have an ARPL toolkit in place. Category 7 is restricted to the 17 statutory designated toolkit trades.");
                if (experienceMonths < 36)
                    return (false, "Category 7 requires a minimum of 3 years (36 months) relevant work experience in South Africa.");
                if (!hasCompletedToolkitAssessment)
                    return (false, "Category 7 requires successful completion of an ARPL Toolkits Assessment.");
                return (true, null);

            case ArplQualifyingCategory.Category8_Min2Years_TradeLearnershipNqf2To4:
                if (experienceMonths < 24)
                    return (false, "Category 8 requires a minimum of 2 years (24 months) relevant experience.");
                if (!hasCompletedTradeLearnership)
                    return (false, "Category 8 requires successful completion of merSETA registered NQF Level 2, 3 and 4 Trade Related Learnerships.");
                return (true, null);

            default:
                return (true, null);
        }
    }

    /// <summary>
    /// Statutory 50% Task Credit Retention Evaluator (Section 5, Page 19).
    /// Candidates passing >= 50% of tasks retain credit for max 3 attempts or 18 months.
    /// </summary>
    public static (bool QualifiesForRetention, decimal PassRate, DateTime ExpiryDate) EvaluateTaskCreditRetention(
        IEnumerable<TradeTestTask> tasks,
        DateTime assessmentDate)
    {
        var taskList = tasks.ToList();
        if (!taskList.Any()) return (false, 0m, assessmentDate);

        int totalCount = taskList.Count;
        int passedCount = taskList.Count(t => t.IsCompetent || (t.TotalMarksAvailable > 0 && (t.MarksObtained / t.TotalMarksAvailable) * 100m >= t.PassPercentage));
        decimal passRate = Math.Round(((decimal)passedCount / totalCount) * 100m, 2);

        bool qualifies = passRate >= 50m;
        DateTime expiry = assessmentDate.AddMonths(18);

        return (qualifies, passRate, expiry);
    }

    /// <summary>
    /// Validates the 5-day advance notice rule (TTC must give at least 5 business days advance notice).
    /// </summary>
    public static bool IsNoticeAdvanceCompliant(DateTime noticeDate, DateTime assessmentDate)
    {
        return assessmentDate >= noticeDate.Date.AddDays(5);
    }
}
