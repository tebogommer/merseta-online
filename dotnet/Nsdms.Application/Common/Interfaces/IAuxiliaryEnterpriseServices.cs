using Nsdms.Domain.Entities;

namespace Nsdms.Application.Common.Interfaces;

public interface IBankingDetailsService
{
    Task<List<BankingDetails>> GetBankingDetailsListAsync();
    Task<BankingDetails?> GetBankingDetailsByIdAsync(int id);
    Task<BankingDetails> SubmitBankingDetailsAsync(int? organisationId, int? trainingProviderId, string bankName, string branchCode, string? branchName, string accountNumber, string accountHolderName, string accountTypeCode, string? docPath, DateTime? docDate, string currentUsername);
    Task<BankingDetails> FirstSignoffAsync(int id, bool approved, string? notes, string currentUsername);
    Task<BankingDetails> SecondSignoffAndActivateErpAsync(int id, bool approved, string? notes, string currentUsername);
}

public interface ISdfAppointmentService
{
    Task<List<SdfCompany>> GetSdfAppointmentsAsync();
    Task<SdfCompany?> GetSdfAppointmentByIdAsync(int id);
    Task<SdfCompany> SubmitSdfAppointmentAsync(int organisationId, int personId, string sdfTypeCode, DateTime startDate, string? docPath, bool allowWsp, bool allowDg, string currentUsername);
    Task<SdfCompany> ApproveSdfAppointmentAsync(int id, string comments, string currentUsername);
    Task<SdfCompany> TerminateSdfAppointmentAsync(int id, string reason, string currentUsername);
}

public interface ISdfManagementService
{
    Task<SdfCompany> AppointSdfAsync(
        int personId,
        int organisationId,
        string sdfTypeCode,
        DateTime startDate,
        DateTime? endDate,
        int? appointmentLetterId,
        string currentUsername = "SYSTEM");

    Task<SdfCompany> ApproveSdfAppointmentAsync(int sdfCompanyId, string currentUsername = "EmployerSignatory");
    Task<SdfCompany> EndorseSdfAppointmentAsync(int sdfCompanyId, string currentUsername = "SetaCoordinator");
    Task<SdfCompany> TerminateSdfAppointmentAsync(int sdfCompanyId, string reason, string currentUsername = "EmployerSignatory");
    Task<List<SdfCompany>> GetSdfAppointmentsAsync(int? organisationId = null);
    Task<SdfCompany?> GetSdfAppointmentByIdAsync(int id);
}

public interface IContractVariationService
{
    Task<List<ContractAddenda>> GetAddendasAsync();
    Task<ContractAddenda?> GetAddendaByIdAsync(int id);
    Task<ContractAddenda> CreateAddendaAsync(int grantMoaId, string variationTypeCode, decimal revisedContractValue, DateTime revisedEndDate, string motivationReason, string currentUsername);
    Task<ContractAddenda> ApproveAddendaAsync(int id, string executiveUserId);

    Task<List<ContractExtensionRequest>> GetExtensionRequestsAsync();
    Task<ContractExtensionRequest> SubmitExtensionRequestAsync(int grantMoaId, int extensionMonths, DateTime proposedEndDate, string progressStatus, string mitigationPlan, string currentUsername);
    Task<ContractExtensionRequest> ApproveExtensionRequestAsync(int id, string executiveUserId);

    Task<List<ContractTerminationRequest>> GetTerminationRequestsAsync();
    Task<ContractTerminationRequest> SubmitTerminationRequestAsync(int grantMoaId, string reasonCode, decimal fundsDisbursed, decimal deliverablesValue, decimal clawbackAmount, string motivation, string currentUsername);
    Task<ContractTerminationRequest> SettleTerminationAsync(int id, string legalUserId);

    Task<List<ContractAddenda>> GetAllAddendasAsync();
    Task<ContractAddenda> CreateContractAddendaAsync(int grantMoaId, string addendaTypeCode, string variationReason, decimal adjustmentAmount, DateTime? revisedEndDate, string currentUsername = "SYSTEM");
    Task<ContractAddenda> ApproveContractAddendaAsync(int addendaId, bool approved, string comments, string currentUsername = "FinanceDirector");
    Task<ContractExtensionRequest> RequestContractExtensionAsync(int grantMoaId, int monthsRequested, string motivation, string currentUsername = "SYSTEM");
    Task<ContractExtensionRequest> ReviewContractExtensionAsync(int extensionRequestId, bool approved, string currentUsername = "ProjectManager");
    Task<List<ContractAddenda>> GetAddendasByMoaIdAsync(int grantMoaId);
    Task<List<ContractExtensionRequest>> GetExtensionsByMoaIdAsync(int grantMoaId);
}

public interface IExtensionOfScopeService
{
    Task<List<SdpExtensionOfScope>> GetSdpScopeExtensionsAsync();
    Task<SdpExtensionOfScope?> GetSdpScopeExtensionByIdAsync(int id);
    Task<SdpExtensionOfScope> SubmitSdpScopeExtensionAsync(int trainingProviderId, string qualificationTitle, string? saqaId, int nqfLevel, int credits, string programmeTypeCode, string currentUsername);
    Task<SdpExtensionOfScope> RecordSiteInspectionAsync(int id, bool passed, string evaluatorUserId);
    Task<SdpExtensionOfScope> ApproveSdpScopeExtensionAsync(int id, string decisionRef, string approvedByUserId);

    Task<List<SdpReAccreditationApplication>> GetReAccreditationApplicationsAsync();
    Task<SdpReAccreditationApplication> SubmitReAccreditationAsync(int trainingProviderId, DateTime currentExpiry, DateTime proposedExpiry, string currentUsername);
    Task<SdpReAccreditationApplication> EndorseReAccreditationAsync(int id, string decisionNumber, string currentUsername);

    Task<List<AssessorExtensionOfScope>> GetAssessorScopeExtensionsAsync();
    Task<AssessorExtensionOfScope> SubmitAssessorScopeExtensionAsync(int assessorPersonId, string practitionerType, string qualTitle, string? saqaId, int nqfLevel, string currentUsername);
    Task<AssessorExtensionOfScope> EndorseAssessorScopeExtensionAsync(int id, string endorsedByUserId);
}

public interface ISdpScopeExtensionService
{
    Task<SdpExtensionOfScope> ApplyForExtensionOfScopeAsync(
        int trainingProviderId,
        string qualificationsJson,
        string unitStandardsJson,
        string motivation,
        string currentUsername = "SYSTEM");

    Task<SdpExtensionOfScope> ReviewExtensionOfScopeAsync(
        int extensionId,
        bool approved,
        string comments,
        string currentUsername = "EtqaManager");

    Task<SdpReAccreditation> ApplyForReAccreditationAsync(
        int trainingProviderId,
        int durationYears,
        bool selfAuditDone,
        decimal auditScore,
        string currentUsername = "SYSTEM");

    Task<SdpReAccreditation> ReviewReAccreditationAsync(
        int reAccreditationId,
        bool approved,
        string comments,
        string currentUsername = "EtqaManager");

    Task<List<SdpExtensionOfScope>> GetExtensionOfScopeApplicationsAsync(int? trainingProviderId = null);
    Task<List<SdpReAccreditation>> GetReAccreditationsAsync(int? trainingProviderId = null);
}


