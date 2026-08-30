using Nsdms.Domain.Entities;

namespace Nsdms.Application.Common.Interfaces;

public interface IFileStorageService
{
    Task<DocumentAttachment> SaveFileAsync(string targetEntityName, int targetEntityId, string fileName, string contentType, Stream fileStream, string? categoryCode = null, string currentUsername = "SYSTEM");
    Task<(Stream ContentStream, string ContentType, string FileName)?> GetFileAsync(int documentAttachmentId);
    Task<List<DocumentAttachment>> GetAttachmentsAsync(string targetEntityName, int targetEntityId);
    Task<bool> DeleteAttachmentAsync(int documentAttachmentId, string currentUsername = "SYSTEM");
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
    string GetActiveProviderName();
}

public interface ILearnerLifecycleService
{
    Task<CompanyLearnerTransfer> RequestTransferAsync(CompanyLearnerTransfer transfer, string currentUsername = "SYSTEM");
    Task<CompanyLearnerTransfer> ApproveTransferAsync(int transferId, string comments, string currentUsername = "SYSTEM");
    Task<CompanyLearnerTransfer> RejectTransferAsync(int transferId, string reason, string currentUsername = "SYSTEM");
    Task<List<CompanyLearnerTransfer>> GetTransfersByLearnerAsync(int companyLearnerId);

    Task<CompanyLearnerLostTime> RecordLostTimeAsync(CompanyLearnerLostTime lostTime, string currentUsername = "SYSTEM");
    Task<CompanyLearnerLostTime> ApproveLostTimeAsync(int lostTimeId, string comments, string currentUsername = "SYSTEM");
    Task<List<CompanyLearnerLostTime>> GetLostTimeByLearnerAsync(int companyLearnerId);

    Task<CompanyLearnerTermination> RequestTerminationAsync(CompanyLearnerTermination termination, string currentUsername = "SYSTEM");
    Task<CompanyLearnerTermination> ApproveTerminationAsync(int terminationId, string comments, string currentUsername = "SYSTEM");
    Task<List<CompanyLearnerTermination>> GetTerminationsByLearnerAsync(int companyLearnerId);
}
