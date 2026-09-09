using Nsdms.Domain.Entities;
using Nsdms.Domain.Lookups;

namespace Nsdms.Application.Common.Interfaces;

public interface IFileStorageService
{
    Task<DocumentAttachment> SaveFileAsync(string targetEntityName, int targetEntityId, string fileName, string contentType, Stream fileStream, string? categoryCode = null, string currentUsername = "SYSTEM");
    Task<(Stream ContentStream, string ContentType, string FileName)?> GetFileAsync(int documentAttachmentId);
    Task<List<DocumentAttachment>> GetAttachmentsAsync(string targetEntityName, int targetEntityId);
    Task<bool> DeleteAttachmentAsync(int documentAttachmentId, string currentUsername = "SYSTEM");

    /// <summary>
    /// Verifies or rejects an uploaded document attachment, recording reviewer details, timestamp, certification date, rejection reasons, and audit double-write.
    /// </summary>
    Task<DocumentAttachment?> VerifyAttachmentAsync(
        int documentAttachmentId,
        bool isCompliant,
        List<string>? rejectionReasonCodes = null,
        string? customNotes = null,
        DateTime? certificationDate = null,
        DateTime? expiryDate = null,
        string currentUsername = "SYSTEM");

    /// <summary>
    /// Retrieves active rejection reasons matching the specified document category code plus universal 'ALL' reasons.
    /// </summary>
    Task<List<DocumentRejectionReasonType>> GetRejectionReasonsAsync(string? categoryCode = null, bool activeOnly = true);

    /// <summary>
    /// Retrieves all rejection reasons with optional text search for administration.
    /// </summary>
    Task<List<DocumentRejectionReasonType>> GetAllRejectionReasonsAsync(string? search = null, string? categoryCode = null);

    /// <summary>
    /// Creates or updates a managed document rejection reason.
    /// </summary>
    Task<DocumentRejectionReasonType> SaveRejectionReasonAsync(DocumentRejectionReasonType reason, string currentUsername = "Admin");

    /// <summary>
    /// Deactivates / soft-deletes a document rejection reason.
    /// </summary>
    Task<bool> DeleteRejectionReasonAsync(string code, string currentUsername = "Admin");
}

public interface IPdfDocumentService
{
    Task<byte[]> GenerateTradeTestCertificateAsync(LearnerTradeTest tradeTest);
    Task<byte[]> GenerateGrantMoaDocumentAsync(GrantMoa moa);
    Task<byte[]> GenerateAccreditationLetterAsync(TrainingProvider provider);
    Task<byte[]> GenerateWspApprovalLetterAsync(WspSubmission wsp);

    Task<byte[]> GenerateArtisanTradeCertificatePdfAsync(LearnerTradeTestApplication app);
    Task<byte[]> GenerateStatementOfResultsPdfAsync(SummativeAssessmentReport report, StatementOfResults sor);
    Task<byte[]> GenerateSarsClawbackNoticePdfAsync(SarsLevyReconAudit audit);
    Task<byte[]> GenerateQcdScopingDocumentPdfAsync(QualificationsCurriculumDevelopment qcd);

    // ID-based direct PDF generation methods
    Task<byte[]> GenerateGrantMoaContractPdfAsync(int grantMoaId);
    Task<byte[]> GenerateTradeTestCertificatePdfAsync(int tradeTestId);
    Task<byte[]> GenerateWspOutcomeLetterPdfAsync(int wspSubmissionId);
    Task<byte[]> GenerateMandatoryRebateRemittancePdfAsync(int disbursementId);

    // Dynamic Live Simulation PDF Generation
    Task<byte[]> GenerateSimulatedDocumentTemplatePdfAsync(DocumentTemplate template, Dictionary<string, string> tokens, bool includeWatermark = true);
    Task<byte[]> GenerateSimulatedMoaTemplatePdfAsync(MoaTemplate template, Dictionary<string, string> tokens, bool includeWatermark = true);

    // Phase 4: Statutory Contract & Certificate Templates
    Task<byte[]> GenerateTripartiteAgreementPdfAsync(CompanyLearner learner);
    Task<byte[]> GenerateAssessorRegistrationCertificatePdfAsync(EtqaAssessor assessor);

    // Statutory Workplace Approval Spec NMok_19122022 Annexures
    Task<byte[]> GenerateWorkplaceApprovalLetterPdfAsync(int approvalId);
    Task<byte[]> GenerateWorkplaceApprovalReportPdfAsync(int approvalId);

    // Statutory ARPL Spec NMok_27012023 Form
    Task<byte[]> GenerateArplApplicationFormPdfAsync(int applicationId);

    // Statutory Assessor Spec MerSeta\NSDMS\LMS\LR\01 Annexures
    Task<byte[]> GenerateAssessorCertificateLetterPdfAsync(int assessorId);
    Task<byte[]> GenerateAssessorStatementOfScopePdfAsync(int assessorId);
    Task<byte[]> GenerateAssessorDisciplinaryLetterPdfAsync(int disciplinaryCaseId);

    // Statutory Learner Management Spec NMok_21112022 Documents
    Task<byte[]> GenerateLpmFm005TransferFormPdfAsync(int transferId);
    Task<byte[]> GenerateLpmTp010MutualTerminationLetterPdfAsync(int terminationId);
    Task<byte[]> GenerateChecklist036InvestigationPdfAsync(int terminationId);
    Task<byte[]> GenerateLearnerAddendumPdfAsync(int extensionId);
    Task<byte[]> GenerateTerminationDecisionLetterPdfAsync(int terminationId);

    // Phase 35: Assessments, Moderation & Certification Controlled Documents (Spec 18-11-2022)
    Task<byte[]> GenerateSummativeAssessmentResultsFormPdfAsync(int reportId);
    Task<byte[]> GenerateModerationValidationReportPdfAsync(int batchId);
    Task<byte[]> GenerateLearnerQualificationCertificatePdfAsync(int certificateId);
    Task<byte[]> GenerateBatchDistributionLetterPdfAsync(int batchId);
    Task<byte[]> GenerateBatchConsolidatedCertificatesPdfAsync(int batchId);

    // Phase 12: Spatial Zoning & Stakeholder Introduction
    Task<byte[]> GenerateLetterOfIntroductionPdfAsync(int organisationId, int portfolioId);

    // Phase 34/36: SDP Accreditation Certificate, Outcome Letter, Disciplinary Notice & Site Inspection Report
    Task<byte[]> GenerateSdpAccreditationCertificatePdfAsync(int providerId);
    Task<byte[]> GenerateQctoEndorsementLetterPdfAsync(int providerId);
    Task<byte[]> GenerateSdpOutcomeLetterPdfAsync(int providerId);
    Task<byte[]> GenerateSdpDisciplinaryNoticePdfAsync(int caseId);
    Task<byte[]> GenerateSdpSiteInspectionReportPdfAsync(int inspectionId);
}

public class ErpDisbursementResult
{
    public bool Success { get; set; }
    public string BatchNumber { get; set; } = string.Empty;
    public string TransactionReference { get; set; } = string.Empty;
    public string StatusMessage { get; set; } = string.Empty;
    public DateTime ProcessedDate { get; set; } = DateTime.UtcNow;
    public bool IsSimulated { get; set; } = false;
    public string ErpBatchReference => BatchNumber;
}

public interface IErpIntegrationService
{
    Task<ErpDisbursementResult> PostTranchePaymentBatchAsync(int tranchePaymentId, string currentUsername = "SYSTEM");
    Task<ErpDisbursementResult> PostMandatoryRebateDisbursementAsync(int rebateDisbursementId, string currentUsername = "SYSTEM");
    Task<ErpDisbursementResult> DisbursePaymentAsync(string vendorReference, decimal amount, string paymentDescription, string currentUsername = "SYSTEM");
    Task<bool> SyncVendorDetailsAsync(int organisationId, string currentUsername = "SYSTEM");
    Task<bool> VerifyBankingDetailsAsync(int organisationId, string currentUsername = "SYSTEM");
    Task<ErpOutboxMessage> EnqueueTranchePaymentBatchAsync(int tranchePaymentId, string currentUsername = "SYSTEM");
    Task<ErpOutboxMessage> EnqueueMandatoryRebateDisbursementAsync(int rebateDisbursementId, string currentUsername = "SYSTEM");
    Task<ErpOutboxMessage> EnqueueVendorSyncAsync(int organisationId, string currentUsername = "SYSTEM");
    Task<ErpOutboxMessage> EnqueueBankingDetailsVerificationAsync(int organisationId, string currentUsername = "SYSTEM");
    Task<ErpOutboxMessage> EnqueuePaymentDisbursementAsync(string vendorReference, decimal amount, string paymentDescription, string currentUsername = "SYSTEM");
    string GetActiveProviderName();
}
