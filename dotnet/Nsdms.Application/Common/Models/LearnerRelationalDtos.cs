namespace Nsdms.Application.Common.Models;

/// <summary>
/// DTO representing an agreement / enrolment under a learner's central profile.
/// </summary>
public record LearnerEnrolmentDto(
    int CompanyLearnerId,
    string? LearnerContractNumber,
    string? LearningProgrammeTypeCode,
    string? LearningProgrammeTypeName,
    string? QualificationTitle,
    int? NqfLevel,
    string? SaqaQualificationId,
    string? OFOCode,
    string? EnrolmentStatusId,
    string? EnrolmentStatusName,
    DateTime RegistrationDate,
    DateTime? CommencementDate,
    DateTime? CompletionDate,
    string? EmployerName,
    int? EmployerId,
    string? TrainingProviderName,
    int? TrainingProviderId
);

/// <summary>
/// DTO representing an employer host or sponsoring workplace linked to the learner.
/// </summary>
public record LearnerEmployerLinkDto(
    int EmployerId,
    string CompanyName,
    string SdlNumber,
    string? SiteName,
    string? PhysicalAddress,
    string? ContactPersonName,
    string? ContactEmail,
    string? ContactPhone,
    string RelationshipRole,
    DateTime? PlacementDate
);

/// <summary>
/// DTO representing a Skills Development Provider linked to the learner.
/// </summary>
public record LearnerSdpLinkDto(
    int ProviderId,
    string ProviderName,
    string? AccreditationNumber,
    string? PrimaryContactName,
    string? EmailAddress,
    string? PhoneNumber,
    string? AssessorName,
    string? AssessorRegNo,
    string? DeliveryMode
);

/// <summary>
/// DTO representing a unit standard assessment record.
/// </summary>
public record LearnerAssessmentDto(
    int AssessmentId,
    int CompanyLearnerId,
    string? UnitStandardCode,
    string? UnitStandardTitle,
    DateTime AssessmentDate,
    string? AssessmentTypeCode,
    string? ResultStatusCode,
    string? AssessorName,
    string? AssessorRegNo,
    string? StatementOfResultsNumber
);

/// <summary>
/// DTO representing Discretionary Grant / Stipend funding linkage.
/// </summary>
public record LearnerStipendDto(
    int CompanyLearnerId,
    string? FundingTypeCode,
    decimal CumulativeSpend,
    string? GrantProjectTitle,
    string? MoaNumber,
    decimal? ApprovedStipendRate,
    string Status
);
