using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public interface IFinanceService
{
    // Grant MOAs
    Task<List<GrantMoa>> GetGrantMoasAsync();
    Task<GrantMoa?> GetGrantMoaByIdAsync(int id);
    Task<GrantMoa> CreateGrantMoaAsync(GrantMoa moa, string userId);
    Task<GrantMoa> UpdateGrantMoaAsync(GrantMoa moa, string userId);
    Task<bool> DeleteGrantMoaAsync(int id, string userId);

    // Milestones
    Task<GrantMoaMilestone?> GetMilestoneByIdAsync(int milestoneId);
    Task<bool> VerifyMilestoneAsync(int milestoneId, string userId, string comments);

    // Tranche Payments
    Task<List<GrantTranchePayment>> GetTranchePaymentsAsync(int? moaId = null);
    Task<GrantTranchePayment> SubmitTranchePaymentAsync(GrantTranchePayment payment, string userId);
    Task<bool> ApproveTranchePaymentAsync(int paymentId, string userId, string batchNumber, string? comments = null);
    Task<bool> ProcessTranchePayoutAsync(int paymentId, string userId, string bankReference);

    // Mandatory Grant 20% Rebates
    Task<List<MandatoryGrantDisbursement>> GetMandatoryDisbursementsAsync(int? finYear = null);
    Task<int> CalculateMandatoryGrantRebatesAsync(int finYear, string userId);
    Task<bool> ApproveMandatoryDisbursementAsync(int id, string userId, string batchNumber);
    Task<bool> ProcessMandatoryDisbursementPayoutAsync(int id, string userId);

    // Inter-SETA Transfers
    Task<List<InterSetaTransfer>> GetInterSetaTransfersAsync();
    Task<InterSetaTransfer?> GetInterSetaTransferByIdAsync(int id);
    Task<InterSetaTransfer> SaveInterSetaTransferAsync(InterSetaTransfer transfer, string userId);
    Task<bool> ApproveInterSetaTransferAsync(int id, string userId, string? dhetRef = null);
    Task<bool> DeleteInterSetaTransferAsync(int id, string userId);
}
