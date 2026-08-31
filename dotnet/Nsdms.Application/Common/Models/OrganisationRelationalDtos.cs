namespace Nsdms.Application.Common.Models;

/// <summary>
/// Lightweight DTO representing a registered/placed learner under an organisation.
/// </summary>
public record OrganisationLearnerDto(
    int CompanyLearnerId,
    int PersonId,
    string LearnerFullName,
    string? RsaIdNumber,
    string? PassportNumber,
    string? LearnerContractNumber,
    string? LearningProgrammeTypeCode,
    string? LearningProgrammeTypeName,
    string? QualificationTitle,
    int? NqfLevel,
    string? EnrolmentStatusId,
    string? EnrolmentStatusName,
    DateTime? RegistrationDate,
    DateTime? EnrolmentStartDate,
    DateTime? EnrolmentEndDate,
    string? TrainingProviderName,
    string? SiteName
);

/// <summary>
/// Summary DTO for Discretionary Grants and MOAs associated with an organisation.
/// </summary>
public record OrganisationGrantSummaryDto(
    int? GrantApplicationId,
    int? GrantMoaId,
    string ApplicationOrMoaNumber,
    string ProjectTitle,
    string? FundingWindowName,
    string? InterventionType,
    decimal RequestedAmount,
    decimal ApprovedAmount,
    decimal TotalDisbursed,
    string StatusCode,
    DateTime? StartDate,
    DateTime? EndDate,
    int MilestonesCount
);

/// <summary>
/// DTO representing Workplace Approval status and details for an employer site.
/// </summary>
public record OrganisationWpaDto(
    int WorkplaceApprovalId,
    string ApprovalNumber,
    string? SiteName,
    string QualificationTitle,
    int? SaqaQualificationId,
    string ApprovalStatusCode,
    DateTime? InspectionDate,
    DateTime? ApprovalDate,
    DateTime? ExpiryDate,
    int MentorsCount,
    int ToolItemsCount
);

/// <summary>
/// DTO representing an appointed Training Committee member for an organisation.
/// </summary>
public record OrganisationCommitteeMemberDto(
    int MemberId,
    int TrainingCommitteeId,
    int PersonId,
    string PersonFullName,
    string? RsaIdNumber,
    string? EmailAddress,
    string? CellPhoneNumber,
    string MemberRoleCode,
    string Constituency,
    bool IsActive,
    DateTime? AppointedDate
);

/// <summary>
/// DTO representing SARS Levy reconciliation and Mandatory Grant rebate status.
/// </summary>
public record OrganisationLevyReconDto(
    int Id,
    string FinYear,
    string SdlNumber,
    decimal GrossLevyReceived,
    decimal MandatoryGrantLevyCalculated,
    decimal DiscretionaryGrantLevyCalculated,
    decimal AdminLevyCalculated,
    string ReconStatus,
    DateTime? ReconciliationDate
);
