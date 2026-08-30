using Nsdms.Domain.Entities;

namespace Nsdms.Application.Common.Interfaces;

public interface ITradeTestAndArplService
{
    Task<LearnerTradeTestApplication> CreateTradeTestApplicationAsync(
        int companyLearnerId,
        string tradeTitle,
        string applicationTypeCode = "Section26D",
        int attemptNumber = 1,
        string? tradeOfoCode = null,
        int? preferredTrainingCenterId = null,
        string currentUsername = "SYSTEM");

    Task<ArplTradeTestInformation> SubmitArplEvidenceAndChecklistAsync(
        int applicationId,
        int yearsOfExperience,
        string currentEmployerName,
        string employerContactPhone,
        List<ArplExperienceDetail> experienceDetails,
        List<ArplTrainingDetail> trainingDetails,
        decimal portfolioScorePercentage,
        string currentUsername = "SYSTEM");

    Task<LearnerTradeTestApplication> AllocateTradeTestCenterAndScheduleAsync(
        int applicationId,
        int trainingProviderId,
        string assessmentCenterName,
        DateTime assessmentDate,
        TimeSpan scheduledStartTime,
        string? assessorName,
        string? assessorRegNumber,
        string currentUsername = "SYSTEM");

    Task<LearnerTradeTestApplication> SubmitToNambForSerialAsync(
        int applicationId,
        string currentUsername = "SYSTEM");

    Task<LearnerTradeTestApplication> RecordNambDecisionAsync(
        int applicationId,
        string decisionStatusCode,
        string nambOfficerName,
        string? nambSerialNumber = null,
        string? decisionNotes = null,
        string currentUsername = "SYSTEM");

    Task<LearnerTradeTestApplication> RecordTaskResultsAsync(
        int applicationId,
        List<TradeTestTask> taskResults,
        string? assessorComments,
        string currentUsername = "SYSTEM");

    Task<LearnerTradeTestApplication> FinalizeTradeTestAndIssueCertificateAsync(
        int applicationId,
        string? moderatorName,
        string? moderatorRegNumber,
        string currentUsername = "SYSTEM");

    Task<LearnerTradeTestApplication?> GetApplicationByIdAsync(int id);
    Task<List<LearnerTradeTestApplication>> GetApplicationsAsync(string? statusCode = null, string? tradeTitle = null);
}

public interface ISummativeAssessmentAndModerationService
{
    Task<SummativeAssessmentReport> CreateSummativeAssessmentReportAsync(
        int companyLearnerId,
        string qualificationTitle,
        string? saqaQualId,
        int nqfLevel,
        string interventionTypeCode = "Learnership",
        int totalCreditsRequired = 120,
        string currentUsername = "SYSTEM");

    Task<SummativeAssessmentReport> RecordUnitStandardCreditsAsync(
        int reportId,
        int assessorPersonId,
        string assessorRegNumber,
        List<SummativeAssessmentUnitStandard> unitStandards,
        string currentUsername = "SYSTEM");

    Task<SummativeAssessmentReport> PerformInternalModerationAsync(
        int reportId,
        int moderatorPersonId,
        string moderatorRegNumber,
        Dictionary<int, (string Outcome, string? Comments)> moderationDecisions,
        string currentUsername = "SYSTEM");

    Task<SummativeAssessmentReport> PerformEtqaExternalModerationAsync(
        int reportId,
        bool isApproved,
        string? externalModeratorComments,
        string currentUsername = "SYSTEM");

    Task<EisaAssessmentEntry> RecordEisaExamEntryAsync(
        int reportId,
        DateTime examDate,
        string centerName,
        string paperCode,
        decimal scoreAchieved,
        decimal totalScorePossible,
        string? qctoRefNumber,
        string currentUsername = "SYSTEM");

    Task<StatementOfResults> IssueStatementOfResultsAsync(
        int reportId,
        string currentUsername = "SYSTEM");

    Task<SummativeAssessmentReport?> GetReportByIdAsync(int id);
    Task<List<SummativeAssessmentReport>> GetReportsAsync(string? statusCode = null, string? qualificationTitle = null);
    Task<StatementOfResults?> VerifyStatementOfResultsAsync(string serialNumberOrHash);
}
