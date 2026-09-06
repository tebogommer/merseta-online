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
        int? qualificationId = null,
        string? specialisation = null,
        bool hasAttemptedPreviously = false,
        string? previousCenterName = null,
        DateTime? previousAttemptDate = null,
        int? previousAttemptsCount = null,
        ArplQualifyingCategory? qualifyingCategory = null,
        string employmentStatus = "Employed",
        string? unregisteredEmployerName = null,
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

    Task<LearnerTradeTestApplication> SubmitClaRecommendationAsync(
        int applicationId,
        bool recommend,
        string? rejectionReason = null,
        string? comments = null,
        string currentUsername = "SYSTEM");

    Task<LearnerTradeTestApplication> SubmitQaApprovalAsync(
        int applicationId,
        bool approve,
        int? stampedDocumentAttachmentId,
        bool isFinalRejection,
        string? rejectionReason,
        string? comments,
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

    Task<LearnerTradeTestApplication> WithdrawTradeTestApplicationAsync(
        int applicationId,
        string withdrawalReasonCode,
        string justification,
        string currentUsername = "SYSTEM");

    Task<List<ArplDocumentChecklist>> SaveDocumentChecklistAsync(
        int applicationId,
        List<(string DocTypeCode, int? AttachmentId)> documents,
        string currentUsername = "SYSTEM");

    Task<List<ArplDocumentChecklist>> SaveDocumentChecklistAsync(
        int applicationId,
        List<ArplDocumentChecklist> checklist,
        string currentUsername = "SYSTEM");

    Task<CertificateDistributionEvent> LogCertificateDistributionAsync(
        int applicationId,
        string methodCode,
        string? trackingNumber,
        string recipientName,
        string? recipientId,
        string currentUsername = "SYSTEM");

    Task<CertificateDistributionEvent> LogCertificateDistributionAsync(
        int applicationId,
        string methodCode,
        string? trackingNumber,
        DateTime dispatchedDate,
        string recipientName,
        string? recipientId,
        DateTime? receivedDate,
        string? notes,
        string currentUsername = "SYSTEM");

    Task<LearnerTradeTestApplication?> GetApplicationByIdAsync(int id);
    Task<LearnerTradeTestApplication?> GetApplicationWithFullDetailsByIdAsync(int id);
    Task<List<LearnerTradeTestApplication>> GetApplicationsAsync(string? statusCode = null, string? tradeTitle = null);
    Task<List<LearnerTradeTestApplication>> GetClaPendingApplicationsAsync();
    Task<List<LearnerTradeTestApplication>> GetQaPendingApplicationsAsync();
}

public class ModerationChecklistItemDto
{
    public int SectionNumber { get; set; } = 1;
    public string CriteriaTitle { get; set; } = string.Empty;
    public string EvidenceRequirements { get; set; } = string.Empty;
    public bool IsCompliant { get; set; } = true;
    public string? Comments { get; set; }
}

public interface ISummativeAssessmentAndModerationService
{
    Task<SummativeAssessmentReport> CreateSummativeAssessmentReportAsync(
        int companyLearnerId,
        string qualificationTitle,
        string? saqaQualId,
        int nqfLevel,
        string interventionTypeCode = "Learnership",
        string assessmentStageCode = "Completion",
        bool isFundedEmployer = true,
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

    Task<List<SummativeAssessmentReport>> GetHoldingRoomReportsAsync(int? providerId = null, string? qualificationTitle = null);

    Task<AssessmentBatch> CreateAssessmentBatchAsync(
        int providerId,
        string qualificationTitle,
        string? saqaId,
        string stageCode,
        int samplePercentage,
        List<int> reportIds,
        string? internalReportDocRef,
        string currentUsername = "SYSTEM");

    Task<List<AssessmentBatch>> GetAssessmentBatchesAsync(string? statusCode = null, int? providerId = null);
    Task<AssessmentBatch?> GetAssessmentBatchByIdAsync(int id);

    Task<AssessmentBatch> ScheduleSiteVisitAsync(
        int batchId,
        DateTime visitDate,
        bool isSiteVisitRequired,
        string? comments,
        int? contactPersonId,
        string currentUsername = "SYSTEM");

    Task<AssessmentBatch> RecordExternalModerationOutcomeAsync(
        int batchId,
        bool isUpheld,
        string? primaryRejectionReason,
        string? vacsViolation,
        string? remarks,
        string? remedialAction,
        List<ModerationChecklistItemDto>? checklistItems,
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

    Task<StatementOfResults> IssueEarlyExitStatementOfResultsAsync(
        int reportId,
        string earlyExitReason,
        string currentUsername = "SYSTEM");

    Task<CertificatePrintingBatch> CreateCertificatePrintingBatchAsync(
        List<int> batchIds,
        string currentUsername = "SYSTEM");

    Task<List<CertificatePrintingBatch>> GetCertificatePrintingBatchesAsync();
    Task<CertificatePrintingBatch?> GetCertificatePrintingBatchByIdAsync(int id);

    Task<AssessmentCertificateDistributionEvent> LogCertificateDistributionAsync(
        int certificateId,
        string method,
        string? waybill,
        string recipientName,
        string? recipientId,
        string? notes,
        string currentUsername = "SYSTEM");

    Task<ScannedCertificateAttachment> AttachScannedCertificateAsync(
        int certificateId,
        string storageKey,
        string fileName,
        long fileSize,
        string? ocrId,
        string? ocrCertNo,
        string currentUsername = "SYSTEM");

    Task<LearnerCertificate> ReissueSpoiledCertificateAsync(
        int certificateId,
        string replacementReason,
        string currentUsername = "SYSTEM");

    Task<SummativeAssessmentReport?> GetReportByIdAsync(int id);
    Task<List<SummativeAssessmentReport>> GetReportsAsync(string? statusCode = null, string? qualificationTitle = null);
    Task<StatementOfResults?> VerifyStatementOfResultsAsync(string serialNumberOrHash);

    // Modern UI Architecture Helpers & Lookup Endpoints
    Task<List<TrainingProvider>> GetTrainingProvidersAsync();
    Task<List<string>> GetQualificationsForProviderAsync(int providerId);
    Task<List<CompanyLearner>> GetCompanyLearnersForProviderAsync(int providerId, string? qualificationTitle = null);
    Task<List<EtqaAssessor>> GetAccreditedAssessorsAsync();
    Task<List<EtqaAssessor>> GetAccreditedModeratorsAsync();
    Task<List<Person>> GetContactPersonsForProviderAsync(int providerId);
    Task<AssessmentBatch> SubmitBatchToQaPoolAsync(int batchId, string currentUsername = "SYSTEM");
    Task<List<AssessmentBatch>> GetUpheldBatchesPendingPrintingAsync();
    Task<CertificatePrintingBatch?> GetPrintingBatchDetailsAsync(int printingBatchId);
    Task<List<ScannedCertificateAttachment>> GetScannedCertificatesAsync(int certificateId);
    Task<(List<AssessmentBatchLearner> Learners, Dictionary<int, LearnerCertificate> Certificates, Dictionary<int, StatementOfResults> Sors)> GetBatchCredentialsDetailsAsync(int batchId);
}
