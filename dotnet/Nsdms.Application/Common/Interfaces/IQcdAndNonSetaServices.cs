using Nsdms.Domain.Entities;

namespace Nsdms.Application.Common.Interfaces;

public interface IQcdAndCurriculumService
{
    Task<QualificationsCurriculumDevelopment> CreateQcdApplicationAsync(
        string qualificationTitle,
        string ofoCode,
        int nqfLevel,
        int totalCreditsRequired,
        string developmentTypeCode = "NewDevelopment",
        string? purpose = null,
        string? demandJustification = null,
        int? organisationId = null,
        string currentUsername = "SYSTEM");

    Task<QualificationsCurriculumDevelopment> AddWorkingGroupMemberAsync(
        int qcdId,
        string memberName,
        string stakeholderRoleTitle,
        string organisationRepresented,
        string email,
        string phone,
        string currentUsername = "SYSTEM");

    Task<QualificationsCurriculumDevelopment> AddSkillsRegistrationAsync(
        int qcdId,
        string skillsCode,
        string skillsTitle,
        int credits,
        int nqfLevel,
        string unitStandardsJson,
        string currentUsername = "SYSTEM");

    Task<QualificationsCurriculumDevelopment> ConveneWorkingGroupAndOpenPublicCommentAsync(
        int qcdId,
        DateTime publicCommentClosingDate,
        string currentUsername = "SYSTEM");

    Task<QualificationsCurriculumDevelopment> SubmitToQctoForSaqaRegistrationAsync(
        int qcdId,
        string currentUsername = "SYSTEM");

    Task<QualificationsCurriculumDevelopment> RecordSaqaRegistrationApprovalAsync(
        int qcdId,
        string saqaRegistrationNumber,
        DateTime registrationDate,
        string currentUsername = "SYSTEM");

    Task<QualificationsCurriculumDevelopment?> GetQcdByIdAsync(int id);
    Task<List<QualificationsCurriculumDevelopment>> GetQcdApplicationsAsync(string? statusCode = null, string? title = null);
}

public interface INonSetaVerificationService
{
    Task<NonSetaCompany> RegisterNonSetaCompanyAsync(
        string companyName,
        string primarySetaCode,
        string? sdlNumber,
        string? regNumber,
        string email,
        string phone,
        string? physicalAddress,
        string currentUsername = "SYSTEM");

    Task<NonSetaQualificationsCompletion> SubmitNonSetaQualificationForVerificationAsync(
        int personId,
        string originatingSetaCode,
        string qualificationTitle,
        string? saqaQualId,
        int nqfLevel,
        int totalCredits,
        string externalCertificateNumber,
        int? nonSetaCompanyId = null,
        int? companyLearnerId = null,
        string currentUsername = "SYSTEM");

    Task<NonSetaQualificationsCompletion> EndorseNonSetaQualificationAsync(
        int verificationId,
        bool isApproved,
        string? endorsementNotes,
        string currentUsername = "SYSTEM");

    Task<NonSetaQualificationsCompletion?> GetVerificationByIdAsync(int id);
    Task<List<NonSetaQualificationsCompletion>> GetVerificationsAsync(string? statusCode = null, string? originatingSeta = null);
    Task<List<NonSetaCompany>> GetNonSetaCompaniesAsync();
}

public interface ISarsLevyReconAuditService
{
    Task<SarsLevyReconAudit> PerformSarsLevyAuditAsync(
        string financialYear,
        string sdlNumber,
        decimal totalSarsLeviesReceived,
        decimal totalCalculatedLeviesExpected,
        int? organisationId = null,
        string? auditNotes = null,
        string currentUsername = "SYSTEM");

    Task<SarsLevyReconAudit> IssueClawbackNoticeAsync(
        int auditId,
        decimal clawbackAmount,
        string currentUsername = "SYSTEM");

    Task<SarsLevyReconAudit> SettleClawbackAsync(
        int auditId,
        string currentUsername = "SYSTEM");

    Task<List<SarsLevyReconAudit>> GetReconAuditsAsync(string? financialYear = null, string? statusCode = null);
    Task<SarsLevyReconAudit?> GetAuditByIdAsync(int id);
}
