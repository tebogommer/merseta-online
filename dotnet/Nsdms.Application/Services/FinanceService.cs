using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class FinanceService : IFinanceService
{
    private readonly INsdmsDbContextFactory _contextFactory;

    public FinanceService(INsdmsDbContextFactory contextFactory)
    {
        _contextFactory = contextFactory;
    }

    public async Task<List<GrantMoa>> GetGrantMoasAsync()
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        return await context.GrantMoas
            .Include(m => m.GrantApplication)
                .ThenInclude(g => g!.Organisation)
            .Include(m => m.Milestones)
                .ThenInclude(ms => ms.Payments)
            .OrderByDescending(m => m.CreatedAt)
            .ToListAsync();
    }

    public async Task<GrantMoa?> GetGrantMoaByIdAsync(int id)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        return await context.GrantMoas
            .Include(m => m.GrantApplication)
                .ThenInclude(g => g!.Organisation)
            .Include(m => m.Milestones)
                .ThenInclude(ms => ms.Payments)
            .FirstOrDefaultAsync(m => m.Id == id);
    }

    public async Task<GrantMoa> CreateGrantMoaAsync(GrantMoa moa, string userId)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        moa.CreatedAt = DateTime.UtcNow;
        moa.CreatedBy = userId;

        if (string.IsNullOrWhiteSpace(moa.MoaNumber))
        {
            moa.MoaNumber = $"MOA-2026-{Guid.NewGuid().ToString()[..8].ToUpperInvariant()}";
        }

        // Auto-generate standard 4 tranches if empty
        if (moa.Milestones == null || moa.Milestones.Count == 0)
        {
            var total = moa.TotalContractValue;
            moa.Milestones = new List<GrantMoaMilestone>
            {
                new() { MilestoneNumber = 1, MilestoneTitle = "Contracting & Learner Induction", DeliverableRequirement = "Signed tripartite agreements & certified learner IDs.", TranchePercentage = 30m, TrancheAmount = total * 0.30m, TargetDueDate = moa.ContractStartDate.AddMonths(2), MilestoneStatusCode = "Pending", CreatedBy = userId },
                new() { MilestoneNumber = 2, MilestoneTitle = "50% Theoretical & Practical Progress", DeliverableRequirement = "Midterm logbook assessments & accredited attendance registers.", TranchePercentage = 30m, TrancheAmount = total * 0.30m, TargetDueDate = moa.ContractStartDate.AddMonths(6), MilestoneStatusCode = "Pending", CreatedBy = userId },
                new() { MilestoneNumber = 3, MilestoneTitle = "Final Summative Assessment & Moderation", DeliverableRequirement = "Statement of results & ETQA moderation reports.", TranchePercentage = 20m, TrancheAmount = total * 0.20m, TargetDueDate = moa.ContractStartDate.AddMonths(9), MilestoneStatusCode = "Pending", CreatedBy = userId },
                new() { MilestoneNumber = 4, MilestoneTitle = "Trade Test Certification & Closeout Audit", DeliverableRequirement = "Trade test certificates & closeout financial expenditure report.", TranchePercentage = 20m, TrancheAmount = total * 0.20m, TargetDueDate = moa.ContractEndDate, MilestoneStatusCode = "Pending", CreatedBy = userId }
            };
        }

        context.GrantMoas.Add(moa);

        context.AuditLogs.Add(new AuditLog
        {
            EntityName = "GrantMoa",
            RecordId = moa.Id,
            ActionName = "CREATE_GRANT_MOA",
            Actor = userId,
            Timestamp = DateTime.UtcNow,
            MetadataJson = $"{{\"moaNumber\":\"{moa.MoaNumber}\",\"value\":{moa.TotalContractValue}}}"
        });

        await context.SaveChangesAsync();
        return moa;
    }

    public async Task<GrantMoa> UpdateGrantMoaAsync(GrantMoa moa, string userId)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var existing = await context.GrantMoas.FirstOrDefaultAsync(m => m.Id == moa.Id);
        if (existing == null) throw new InvalidOperationException($"MOA #{moa.Id} not found.");

        existing.ContractStartDate = moa.ContractStartDate;
        existing.ContractEndDate = moa.ContractEndDate;
        existing.TotalContractValue = moa.TotalContractValue;
        existing.SignoffDateEmployer = moa.SignoffDateEmployer;
        existing.SignoffDateSeta = moa.SignoffDateSeta;
        existing.SignoffDocumentUri = moa.SignoffDocumentUri;
        existing.MoaStatusCode = moa.MoaStatusCode;
        existing.SpecialConditions = moa.SpecialConditions;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = userId;

        context.AuditLogs.Add(new AuditLog
        {
            EntityName = "GrantMoa",
            RecordId = moa.Id,
            ActionName = "UPDATE_GRANT_MOA",
            Actor = userId,
            Timestamp = DateTime.UtcNow,
            MetadataJson = $"{{\"moaNumber\":\"{moa.MoaNumber}\",\"status\":\"{moa.MoaStatusCode}\"}}"
        });

        await context.SaveChangesAsync();
        return existing;
    }

    public async Task<bool> DeleteGrantMoaAsync(int id, string userId)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var moa = await context.GrantMoas.Include(m => m.Milestones).FirstOrDefaultAsync(m => m.Id == id);
        if (moa == null) return false;

        context.GrantMoas.Remove(moa);
        context.AuditLogs.Add(new AuditLog
        {
            EntityName = "GrantMoa",
            RecordId = id,
            ActionName = "DELETE_GRANT_MOA",
            Actor = userId,
            Timestamp = DateTime.UtcNow
        });

        await context.SaveChangesAsync();
        return true;
    }

    public async Task<GrantMoaMilestone?> GetMilestoneByIdAsync(int milestoneId)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        return await context.GrantMoaMilestones
            .Include(m => m.GrantMoa)
            .Include(m => m.Payments)
            .FirstOrDefaultAsync(m => m.Id == milestoneId);
    }

    public async Task<bool> VerifyMilestoneAsync(int milestoneId, string userId, string comments)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var ms = await context.GrantMoaMilestones.FirstOrDefaultAsync(m => m.Id == milestoneId);
        if (ms == null) return false;

        ms.MilestoneStatusCode = "Verified";
        ms.VerificationDate = DateTime.UtcNow;
        ms.VerifiedByUserId = userId;
        ms.VerificationComments = comments;
        ms.ModifiedAt = DateTime.UtcNow;
        ms.ModifiedBy = userId;

        context.AuditLogs.Add(new AuditLog
        {
            EntityName = "GrantMoaMilestone",
            RecordId = milestoneId,
            ActionName = "VERIFY_MILESTONE",
            Actor = userId,
            Timestamp = DateTime.UtcNow,
            MetadataJson = $"{{\"milestoneNumber\":{ms.MilestoneNumber},\"status\":\"Verified\",\"comments\":\"{comments}\"}}"
        });

        await context.SaveChangesAsync();
        return true;
    }

    public async Task<List<GrantTranchePayment>> GetTranchePaymentsAsync(int? moaId = null)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var query = context.GrantTranchePayments
            .Include(p => p.GrantMoaMilestone)
                .ThenInclude(m => m!.GrantMoa)
                    .ThenInclude(moa => moa!.GrantApplication)
                        .ThenInclude(g => g!.Organisation)
            .AsQueryable();

        if (moaId.HasValue)
        {
            query = query.Where(p => p.GrantMoaMilestone!.GrantMoaId == moaId.Value);
        }

        return await query.OrderByDescending(p => p.InvoiceDate).ToListAsync();
    }

    public async Task<GrantTranchePayment> SubmitTranchePaymentAsync(GrantTranchePayment payment, string userId)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        payment.CreatedAt = DateTime.UtcNow;
        payment.CreatedBy = userId;
        payment.PaymentStatusCode = "Submitted";

        if (string.IsNullOrWhiteSpace(payment.PaymentReferenceNumber))
        {
            payment.PaymentReferenceNumber = $"PAY-2026-{Guid.NewGuid().ToString()[..8].ToUpperInvariant()}";
        }

        context.GrantTranchePayments.Add(payment);

        context.AuditLogs.Add(new AuditLog
        {
            EntityName = "GrantTranchePayment",
            RecordId = payment.Id,
            ActionName = "SUBMIT_TRANCHE_PAYMENT",
            Actor = userId,
            Timestamp = DateTime.UtcNow,
            MetadataJson = $"{{\"invoiceNo\":\"{payment.InvoiceNumber}\",\"amount\":{payment.ClaimedAmount}}}"
        });

        await context.SaveChangesAsync();
        return payment;
    }

    public async Task<bool> ApproveTranchePaymentAsync(int paymentId, string userId, string batchNumber, string? comments = null)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var pay = await context.GrantTranchePayments.FirstOrDefaultAsync(p => p.Id == paymentId);
        if (pay == null) return false;

        pay.PaymentStatusCode = "Finance Approved";
        pay.BatchNumber = batchNumber;
        pay.FinanceApproverUserId = userId;
        pay.FinanceApprovalDate = DateTime.UtcNow;
        pay.ApprovalComments = comments;
        pay.ApprovedPaymentAmount = pay.ClaimedAmount;
        pay.ModifiedAt = DateTime.UtcNow;
        pay.ModifiedBy = userId;

        context.AuditLogs.Add(new AuditLog
        {
            EntityName = "GrantTranchePayment",
            RecordId = paymentId,
            ActionName = "APPROVE_TRANCHE_PAYMENT",
            Actor = userId,
            Timestamp = DateTime.UtcNow,
            MetadataJson = $"{{\"status\":\"Finance Approved\",\"batch\":\"{batchNumber}\"}}"
        });

        await context.SaveChangesAsync();
        return true;
    }

    public async Task<bool> ProcessTranchePayoutAsync(int paymentId, string userId, string bankReference)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var pay = await context.GrantTranchePayments.Include(p => p.GrantMoaMilestone).FirstOrDefaultAsync(p => p.Id == paymentId);
        if (pay == null) return false;

        pay.PaymentStatusCode = "Paid";
        pay.PaymentDate = DateTime.UtcNow;
        pay.BankReference = bankReference;
        pay.ModifiedAt = DateTime.UtcNow;
        pay.ModifiedBy = userId;

        if (pay.GrantMoaMilestone != null)
        {
            pay.GrantMoaMilestone.MilestoneStatusCode = "Paid";
            pay.GrantMoaMilestone.ModifiedAt = DateTime.UtcNow;
            pay.GrantMoaMilestone.ModifiedBy = userId;
        }

        context.AuditLogs.Add(new AuditLog
        {
            EntityName = "GrantTranchePayment",
            RecordId = paymentId,
            ActionName = "PAYOUT_TRANCHE",
            Actor = userId,
            Timestamp = DateTime.UtcNow,
            MetadataJson = $"{{\"bankRef\":\"{bankReference}\",\"amount\":{pay.ApprovedPaymentAmount}}}"
        });

        await context.SaveChangesAsync();
        return true;
    }

    public async Task<List<MandatoryGrantDisbursement>> GetMandatoryDisbursementsAsync(int? finYear = null)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var query = context.MandatoryGrantDisbursements
            .Include(d => d.WspSubmission)
            .Include(d => d.Organisation)
            .AsQueryable();

        if (finYear.HasValue)
        {
            query = query.Where(d => d.FinYear == finYear.Value);
        }

        return await query.OrderByDescending(d => d.CreatedAt).ToListAsync();
    }

    public async Task<int> CalculateMandatoryGrantRebatesAsync(int finYear, string userId)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var approvedWsps = await context.WspSubmissions
            .Include(w => w.Organisation)
            .Where(w => w.FinYear == finYear && (w.StatusCode == "Approved" || w.StatusCode == "Approved by CLO" || w.StatusCode == "SUBMITTED"))
            .ToListAsync();

        int createdCount = 0;
        foreach (var wsp in approvedWsps)
        {
            var exists = await context.MandatoryGrantDisbursements.AnyAsync(d => d.WspSubmissionId == wsp.Id);
            if (!exists)
            {
                var grossLevy = wsp.PlannedTrainingBudget * 5.0m;
                var rebate = wsp.PlannedTrainingBudget * 0.20m;
                var org = wsp.Organisation;

                var disb = new MandatoryGrantDisbursement
                {
                    WspSubmissionId = wsp.Id,
                    OrganisationId = wsp.OrganisationId,
                    DisbursementReference = $"MG-{finYear}-{wsp.Id:D4}",
                    FinYear = finYear,
                    LevyPeriod = $"{finYear}/04 - {finYear}/09",
                    LeviesReceivedAmount = grossLevy,
                    CalculatedRebateAmount = rebate,
                    DisbursementStatusCode = "Calculated",
                    BankAccountSnapshot = org != null ? $"{org.BankName} - Acc {org.BankAccountNumber} - Branch {org.BankBranchCode}" : "Banking verified on file",
                    Comments = "Calculated based on 20% statutory levy rebate entitlement.",
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = userId
                };

                context.MandatoryGrantDisbursements.Add(disb);
                createdCount++;
            }
        }

        if (createdCount > 0)
        {
            await context.SaveChangesAsync();
        }

        return createdCount;
    }

    public async Task<bool> ApproveMandatoryDisbursementAsync(int id, string userId, string batchNumber)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var disb = await context.MandatoryGrantDisbursements.FirstOrDefaultAsync(d => d.Id == id);
        if (disb == null) return false;

        disb.DisbursementStatusCode = "Approved";
        disb.BatchNumber = batchNumber;
        disb.ModifiedAt = DateTime.UtcNow;
        disb.ModifiedBy = userId;

        context.AuditLogs.Add(new AuditLog
        {
            EntityName = "MandatoryGrantDisbursement",
            RecordId = id,
            ActionName = "APPROVE_MG_DISBURSEMENT",
            Actor = userId,
            Timestamp = DateTime.UtcNow,
            MetadataJson = $"{{\"batch\":\"{batchNumber}\",\"amount\":{disb.CalculatedRebateAmount}}}"
        });

        await context.SaveChangesAsync();
        return true;
    }

    public async Task<bool> ProcessMandatoryDisbursementPayoutAsync(int id, string userId)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var disb = await context.MandatoryGrantDisbursements.FirstOrDefaultAsync(d => d.Id == id);
        if (disb == null) return false;

        disb.DisbursementStatusCode = "Paid";
        disb.PaymentDate = DateTime.UtcNow;
        disb.ModifiedAt = DateTime.UtcNow;
        disb.ModifiedBy = userId;

        context.AuditLogs.Add(new AuditLog
        {
            EntityName = "MandatoryGrantDisbursement",
            RecordId = id,
            ActionName = "PAYOUT_MG_DISBURSEMENT",
            Actor = userId,
            Timestamp = DateTime.UtcNow,
            MetadataJson = $"{{\"status\":\"Paid\",\"amount\":{disb.CalculatedRebateAmount}}}"
        });

        await context.SaveChangesAsync();
        return true;
    }

    public async Task<List<InterSetaTransfer>> GetInterSetaTransfersAsync()
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        return await context.InterSetaTransfers
            .Include(t => t.Organisation)
            .OrderByDescending(t => t.EffectiveDate)
            .ToListAsync();
    }

    public async Task<InterSetaTransfer?> GetInterSetaTransferByIdAsync(int id)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        return await context.InterSetaTransfers
            .Include(t => t.Organisation)
            .FirstOrDefaultAsync(t => t.Id == id);
    }

    public async Task<InterSetaTransfer> SaveInterSetaTransferAsync(InterSetaTransfer transfer, string userId)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        if (transfer.Id == 0)
        {
            transfer.CreatedAt = DateTime.UtcNow;
            transfer.CreatedBy = userId;
            context.InterSetaTransfers.Add(transfer);
        }
        else
        {
            var existing = await context.InterSetaTransfers.FirstOrDefaultAsync(t => t.Id == transfer.Id);
            if (existing == null) throw new InvalidOperationException($"Transfer #{transfer.Id} not found.");

            existing.TransferType = transfer.TransferType;
            existing.OtherSetaCode = transfer.OtherSetaCode;
            existing.OtherSetaName = transfer.OtherSetaName;
            existing.TransferReason = transfer.TransferReason;
            existing.EffectiveDate = transfer.EffectiveDate;
            existing.TransferStatusCode = transfer.TransferStatusCode;
            existing.TransferAmount = transfer.TransferAmount;
            existing.SetaApprovalReference = transfer.SetaApprovalReference;
            existing.DhetReferenceNumber = transfer.DhetReferenceNumber;
            existing.Comments = transfer.Comments;
            existing.ModifiedAt = DateTime.UtcNow;
            existing.ModifiedBy = userId;
        }

        context.AuditLogs.Add(new AuditLog
        {
            EntityName = "InterSetaTransfer",
            RecordId = transfer.Id,
            ActionName = transfer.Id == 0 ? "CREATE_INTER_SETA_TRANSFER" : "UPDATE_INTER_SETA_TRANSFER",
            Actor = userId,
            Timestamp = DateTime.UtcNow,
            MetadataJson = $"{{\"seta\":\"{transfer.OtherSetaCode}\",\"amount\":{transfer.TransferAmount}}}"
        });

        await context.SaveChangesAsync();
        return transfer;
    }

    public async Task<bool> ApproveInterSetaTransferAsync(int id, string userId, string? dhetRef = null)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var trf = await context.InterSetaTransfers.FirstOrDefaultAsync(t => t.Id == id);
        if (trf == null) return false;

        trf.TransferStatusCode = "Approved by CEO";
        trf.DhetReferenceNumber = dhetRef ?? trf.DhetReferenceNumber;
        trf.ModifiedAt = DateTime.UtcNow;
        trf.ModifiedBy = userId;

        context.AuditLogs.Add(new AuditLog
        {
            EntityName = "InterSetaTransfer",
            RecordId = id,
            ActionName = "APPROVE_INTER_SETA_TRANSFER",
            Actor = userId,
            Timestamp = DateTime.UtcNow,
            MetadataJson = $"{{\"status\":\"Approved by CEO\",\"dhetRef\":\"{dhetRef}\"}}"
        });

        await context.SaveChangesAsync();
        return true;
    }

    public async Task<bool> DeleteInterSetaTransferAsync(int id, string userId)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var trf = await context.InterSetaTransfers.FirstOrDefaultAsync(t => t.Id == id);
        if (trf == null) return false;

        context.InterSetaTransfers.Remove(trf);
        context.AuditLogs.Add(new AuditLog
        {
            EntityName = "InterSetaTransfer",
            RecordId = id,
            ActionName = "DELETE_INTER_SETA_TRANSFER",
            Actor = userId,
            Timestamp = DateTime.UtcNow
        });

        await context.SaveChangesAsync();
        return true;
    }
}
