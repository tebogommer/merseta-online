using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Daily email quota tracking entity enforcing the statutory Office 365 ceiling of 10,000 emails/day.
/// </summary>
public class EmailDailyQuotaTracker : BaseEntity
{
    public DateTime QuotaDate { get; set; } = DateTime.UtcNow.Date;
    public int SentCount { get; set; } = 0;
    public int ThrottledCount { get; set; } = 0;
    public int FailedCount { get; set; } = 0;
    public int DailyLimit { get; set; } = 10000;
    public bool IsLimitReached { get; set; } = false;
    public DateTime? LastSentAt { get; set; }
}
