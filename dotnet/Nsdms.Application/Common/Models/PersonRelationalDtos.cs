namespace Nsdms.Application.Common.Models;

/// <summary>
/// DTO representing a learner agreement linked to a Person.
/// </summary>
public record PersonLearnerDto(
    int CompanyLearnerId,
    string? LearnerContractNumber,
    string EmployerName,
    int? EmployerId,
    string QualificationTitle,
    string? ProgrammeTypeName,
    string Status,
    DateTime RegistrationDate
);

/// <summary>
/// DTO representing an Employer Organisation link where the Person holds a designation.
/// </summary>
public record PersonEmployerLinkDto(
    int OrganisationId,
    string CompanyName,
    string SdlNumber,
    string Designation,
    string? Email,
    string? Phone
);

/// <summary>
/// DTO representing an ETQA practitioner credential linked to a Person.
/// </summary>
public record PersonEtqaDto(
    int AssessorId,
    string RegistrationNumber,
    string Role,
    string Status,
    DateTime StartDate,
    DateTime EndDate,
    int ScopesCount
);

/// <summary>
/// DTO representing an Artisan Mentorship role at an approved workshop.
/// </summary>
public record PersonMentorDto(
    int MentorId,
    int WorkplaceApprovalId,
    string EmployerName,
    string TradeTitle,
    string Designation,
    string? ArtisanTradeNumber,
    int YearsExperience,
    bool IsCertifiedArtisan
);
