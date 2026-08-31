using System.ComponentModel.DataAnnotations.Schema;
using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Statutory artisan mentor-to-apprentice ratio policy per trade qualification or OFO occupational code.
/// Regulates maximum learner supervision capacity for workplace approvals under NAMB and QCTO frameworks.
/// </summary>
public class TradeMentorRatioPolicy : BaseEntity
{
    /// <summary>
    /// Unique Trade / Occupational Code (e.g. WELD, ELEC, FITT, BOIL, 651202).
    /// </summary>
    public string TradeCode { get; set; } = string.Empty;

    /// <summary>
    /// Official trade qualification or occupational title (e.g. Welder, Electrician, Fitter and Turner).
    /// </summary>
    public string TradeTitle { get; set; } = string.Empty;

    /// <summary>
    /// Organising Framework for Occupations (OFO) code.
    /// </summary>
    public string? TradeOfoCode { get; set; }

    /// <summary>
    /// SAQA Registered Qualification ID code associated with this trade.
    /// </summary>
    public int? SaqaQualificationId { get; set; }

    /// <summary>
    /// Standard statutory ratio of learners per qualified artisan mentor (e.g. 4 for 1:4).
    /// </summary>
    public int StandardRatio { get; set; } = 4;

    /// <summary>
    /// Maximum permissible ratio under special dispensation / approval (e.g. 6).
    /// </summary>
    public int MaxAllowedRatio { get; set; } = 6;

    /// <summary>
    /// Minimum post-apprenticeship / post-trade test verified years of experience required to mentor in this trade.
    /// </summary>
    public int MinExperienceYearsRequired { get; set; } = 3;

    /// <summary>
    /// Indicates whether this trade enforces strict blocking on excess learner enrollments or advisory warnings only.
    /// </summary>
    public bool EnforceStrictly { get; set; } = true;

    /// <summary>
    /// Indicates whether the trade policy is active.
    /// </summary>
    public bool IsActive { get; set; } = true;

    /// <summary>
    /// Regulatory notes, gazette references, or curriculum guidelines.
    /// </summary>
    public string? Notes { get; set; }

    /// <summary>
    /// Formatted display ratio string (e.g. "1:4").
    /// </summary>
    [NotMapped]
    public string FormattedRatio => $"1:{StandardRatio}";
}
