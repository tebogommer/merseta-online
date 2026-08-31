namespace Nsdms.Application.Common.Models;

/// <summary>
/// DTO representing a learner placed at a workplace under an approved trade / qualification.
/// </summary>
public record WpaLearnerDto(
    int CompanyLearnerId,
    string? LearnerContractNumber,
    string FullName,
    string? RsaIdNumber,
    string? QualificationTitle,
    string? ProgrammeTypeName,
    string EnrolmentStatus,
    DateTime RegistrationDate
);

/// <summary>
/// DTO representing a Skills Development Provider partner linked to this workplace approval.
/// </summary>
public record WpaSdpDto(
    int ProviderId,
    string ProviderName,
    string? AccreditationNumber,
    string? ContactPersonName,
    string? ContactEmail,
    int CohortCount
);

/// <summary>
/// DTO representing an on-site audit or verification visit conducted against this workplace.
/// </summary>
public record WpaVisitDto(
    int VisitId,
    DateTime VisitDate,
    string VisitType,
    string VisitStatus,
    string? ContactPersonName,
    string? Location,
    string? Purpose,
    string? OutcomeNotes
);
