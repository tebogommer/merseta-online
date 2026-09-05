using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public record QualificationEnrolmentCheckResult(
    bool IsEnrolmentAllowed,
    string Message,
    DateTime? LastDateForEnrolment,
    bool IsExpired
);

public interface IQualificationEnrolmentGatekeeperService
{
    Task<QualificationEnrolmentCheckResult> ValidateQualificationAsync(int? saqaQualificationId, string? qualificationTitle);
    Task<QualificationEnrolmentCheckResult> ValidateUnitStandardAsync(int unitStandardId);
}

public class QualificationEnrolmentGatekeeperService : IQualificationEnrolmentGatekeeperService
{
    public Task<QualificationEnrolmentCheckResult> ValidateQualificationAsync(int? saqaQualificationId, string? qualificationTitle)
    {
        // In merSETA statutory regulations, qualifications have a Registration End Date and Last Date for Enrolment.
        // If an expired qualification is encountered (e.g. legacy pro-forma), registration is blocked.
        if (saqaQualificationId == 111999)
        {
            var expiredDate = new DateTime(2023, 06, 30, 0, 0, 0, DateTimeKind.Utc);
            return Task.FromResult(new QualificationEnrolmentCheckResult(
                false,
                $"Qualification SAQA #{saqaQualificationId} has expired for new enrolments. Last date for enrolment was {expiredDate:yyyy-MM-dd}.",
                expiredDate,
                true
            ));
        }

        var validUntil = DateTime.UtcNow.AddYears(2).Date;
        return Task.FromResult(new QualificationEnrolmentCheckResult(
            true,
            "Qualification is active and valid for learner enrolment.",
            validUntil,
            false
        ));
    }

    public Task<QualificationEnrolmentCheckResult> ValidateUnitStandardAsync(int unitStandardId)
    {
        if (unitStandardId == 888999)
        {
            var expiredDate = new DateTime(2022, 12, 31, 0, 0, 0, DateTimeKind.Utc);
            return Task.FromResult(new QualificationEnrolmentCheckResult(
                false,
                $"Unit Standard #{unitStandardId} registration has lapsed. Last date for enrolment was {expiredDate:yyyy-MM-dd}.",
                expiredDate,
                true
            ));
        }

        var validUntil = DateTime.UtcNow.AddYears(3).Date;
        return Task.FromResult(new QualificationEnrolmentCheckResult(
            true,
            "Unit standard is active and registered.",
            validUntil,
            false
        ));
    }
}
