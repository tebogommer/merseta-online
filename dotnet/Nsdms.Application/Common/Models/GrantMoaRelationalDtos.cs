namespace Nsdms.Application.Common.Models;

/// <summary>
/// DTO representing a beneficiary learner funded under a Grant MoA / Project.
/// </summary>
public record GrantMoaBeneficiaryDto(
    int CompanyLearnerId,
    string? LearnerContractNumber,
    string FullName,
    string? RsaIdNumber,
    string? QualificationTitle,
    string? ProgrammeTypeCode,
    string? ProgrammeTypeName,
    decimal StipendRate,
    string EnrolmentStatus,
    DateTime RegistrationDate
);

/// <summary>
/// DTO representing a participating employer / workplace under a Grant MoA.
/// </summary>
public record GrantMoaEmployerDto(
    int OrganisationId,
    string CompanyName,
    string SdlNumber,
    string? Chamber,
    int PlacedLearnersCount,
    string? ContactPersonName,
    string? ContactEmail,
    string? ContactPhone
);

/// <summary>
/// DTO representing a Skills Development Provider partner under a Grant MoA.
/// </summary>
public record GrantMoaSdpDto(
    int ProviderId,
    string ProviderName,
    string? AccreditationNumber,
    string? ContactPersonName,
    string? ContactEmail,
    int CohortCount
);

/// <summary>
/// DTO representing a formal contract variation / addendum against an MoA.
/// </summary>
public record GrantMoaVariationDto(
    int VariationId,
    string VariationNumber,
    string VariationType,
    DateTime VariationDate,
    decimal? RevisedContractValue,
    DateTime? RevisedEndDate,
    string Status,
    string? Justification
);
