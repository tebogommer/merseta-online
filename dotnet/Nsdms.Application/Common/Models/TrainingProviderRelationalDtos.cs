namespace Nsdms.Application.Common.Models;

/// <summary>
/// DTO representing an enrolled learner / cohort under an SDP.
/// </summary>
public record ProviderLearnerDto(
    int CompanyLearnerId,
    string? LearnerContractNumber,
    string LearnerFullName,
    string? RsaIdNumber,
    string? QualificationTitle,
    string? LearningProgrammeTypeCode,
    string? LearningProgrammeTypeName,
    string? SponsoringEmployerName,
    int? EmployerId,
    DateTime RegistrationDate,
    string Status
);

/// <summary>
/// DTO representing an Assessor or Moderator linked to the training provider.
/// </summary>
public record ProviderAssessorModeratorDto(
    int AssessorId,
    string FullName,
    string RegistrationNumber,
    string PractitionerType,
    string? ScopeQualifications,
    DateTime? ExpiryDate,
    string Status
);

/// <summary>
/// DTO representing a participating host employer partnering with the SDP.
/// </summary>
public record ProviderEmployerDto(
    int EmployerId,
    string CompanyName,
    string SdlNumber,
    string? SiteName,
    int PlacedLearnersCount,
    string? ContactPersonName,
    string? ContactEmail,
    string? ContactPhone
);

/// <summary>
/// DTO representing an ETQA accreditation or moderation site visit.
/// </summary>
public record ProviderAuditVisitDto(
    int VisitId,
    string VisitNumber,
    string AuditType,
    DateTime VisitDate,
    string AuditorName,
    string ComplianceStatus,
    string? Remarks
);
