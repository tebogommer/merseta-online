using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class ContractVariationService : IContractVariationService
{
    private readonly INsdmsDbContextFactory _factory;
    private readonly AuditService _audit;

    public ContractVariationService(INsdmsDbContextFactory factory, AuditService audit)
    {
        _factory = factory;
        _audit = audit;
    }

    public async Task<List<ContractAddenda>> GetAddendasAsync()
    {
        using var db = await _factory.CreateDbContextAsync();
        return await db.ContractAddendas
            .Include(a => a.GrantMoa)
            .OrderByDescending(a => a.CreatedAt)
            .ToListAsync();
    }

    public async Task<ContractAddenda?> GetAddendaByIdAsync(int id)
    {
        using var db = await _factory.CreateDbContextAsync();
        return await db.ContractAddendas
            .Include(a => a.GrantMoa)
            .FirstOrDefaultAsync(a => a.Id == id);
    }

    public async Task<ContractAddenda> CreateAddendaAsync(int grantMoaId, string variationTypeCode, decimal revisedContractValue, DateTime revisedEndDate, string motivationReason, string currentUsername)
    {
        using var db = await _factory.CreateDbContextAsync();
        var moa = await db.GrantMoas.FindAsync(grantMoaId) ?? throw new InvalidOperationException($"Grant MOA #{grantMoaId} not found.");

        var count = await db.ContractAddendas.CountAsync() + 1;
        var entity = new ContractAddenda
        {
            GrantMoaId = grantMoaId,
            AddendaNumber = $"ADD-2026-{count:D4}",
            VariationTypeCode = variationTypeCode,
            OriginalContractValue = moa.TotalContractValue,
            RevisedContractValue = revisedContractValue > 0 ? revisedContractValue : moa.TotalContractValue,
            OriginalEndDate = moa.ContractEndDate,
            RevisedEndDate = revisedEndDate,
            MotivationReason = motivationReason,
            StatusCode = "SubmittedForReview",
            CreatedBy = currentUsername,
            CreatedAt = DateTime.UtcNow
        };

        db.ContractAddendas.Add(entity);
        await db.SaveChangesAsync();
        await _audit.LogAsync("ContractAddenda", entity.Id, "CreateAddenda", currentUsername, new { entity.AddendaNumber, entity.RevisedContractValue });
        return entity;
    }

    public async Task<ContractAddenda> ApproveAddendaAsync(int id, string executiveUserId)
    {
        using var db = await _factory.CreateDbContextAsync();
        var entity = await db.ContractAddendas.FirstOrDefaultAsync(a => a.Id == id) ?? throw new InvalidOperationException($"Addenda #{id} not found.");

        entity.StatusCode = "ExecutiveApproved";
        entity.ExecutiveApprovedByUserId = executiveUserId;
        entity.ExecutiveApprovalDate = DateTime.UtcNow;
        entity.ModifiedAt = DateTime.UtcNow;
        entity.ModifiedBy = executiveUserId;

        // Apply revision to parent MOA explicitly
        var moa = await db.GrantMoas.FindAsync(entity.GrantMoaId);
        if (moa != null)
        {
            moa.TotalContractValue = entity.RevisedContractValue;
            moa.ContractEndDate = entity.RevisedEndDate;
            moa.ModifiedAt = DateTime.UtcNow;
            moa.ModifiedBy = executiveUserId;
        }

        await db.SaveChangesAsync();
        await _audit.LogAsync("ContractAddenda", entity.Id, "ApproveAddenda", executiveUserId, new { entity.StatusCode, entity.RevisedContractValue });
        return entity;
    }

    public async Task<List<ContractExtensionRequest>> GetExtensionRequestsAsync()
    {
        using var db = await _factory.CreateDbContextAsync();
        return await db.ContractExtensionRequests
            .Include(e => e.GrantMoa)
            .OrderByDescending(e => e.CreatedAt)
            .ToListAsync();
    }

    public async Task<ContractExtensionRequest> SubmitExtensionRequestAsync(int grantMoaId, int extensionMonths, DateTime proposedEndDate, string progressStatus, string mitigationPlan, string currentUsername)
    {
        using var db = await _factory.CreateDbContextAsync();
        var moa = await db.GrantMoas.FindAsync(grantMoaId) ?? throw new InvalidOperationException($"Grant MOA #{grantMoaId} not found.");

        var count = await db.ContractExtensionRequests.CountAsync() + 1;
        var entity = new ContractExtensionRequest
        {
            GrantMoaId = grantMoaId,
            RequestNumber = $"EXT-2026-{count:D4}",
            RequestedExtensionMonths = extensionMonths,
            CurrentEndDate = moa.ContractEndDate,
            ProposedNewEndDate = proposedEndDate,
            ProjectProgressStatus = progressStatus,
            MitigationPlanSummary = mitigationPlan,
            StatusCode = "Submitted",
            CreatedBy = currentUsername,
            CreatedAt = DateTime.UtcNow
        };

        db.ContractExtensionRequests.Add(entity);
        await db.SaveChangesAsync();
        await _audit.LogAsync("ContractExtensionRequest", entity.Id, "SubmitExtensionRequest", currentUsername, new { entity.RequestNumber, extensionMonths });
        return entity;
    }

    public async Task<ContractExtensionRequest> ApproveExtensionRequestAsync(int id, string executiveUserId)
    {
        using var db = await _factory.CreateDbContextAsync();
        var entity = await db.ContractExtensionRequests.FirstOrDefaultAsync(e => e.Id == id) ?? throw new InvalidOperationException($"Extension request #{id} not found.");

        entity.StatusCode = "ApprovedByExecutive";
        entity.ReviewedByUserId = executiveUserId;
        entity.ReviewDate = DateTime.UtcNow;
        entity.ModifiedAt = DateTime.UtcNow;
        entity.ModifiedBy = executiveUserId;

        var moa = await db.GrantMoas.FindAsync(entity.GrantMoaId);
        if (moa != null)
        {
            moa.ContractEndDate = entity.ProposedNewEndDate;
            moa.ModifiedAt = DateTime.UtcNow;
            moa.ModifiedBy = executiveUserId;
        }

        await db.SaveChangesAsync();
        await _audit.LogAsync("ContractExtensionRequest", entity.Id, "ApproveExtensionRequest", executiveUserId, new { entity.StatusCode, entity.ProposedNewEndDate });
        return entity;
    }

    public async Task<List<ContractTerminationRequest>> GetTerminationRequestsAsync()
    {
        using var db = await _factory.CreateDbContextAsync();
        return await db.ContractTerminationRequests
            .Include(t => t.GrantMoa)
            .OrderByDescending(t => t.CreatedAt)
            .ToListAsync();
    }

    public async Task<ContractTerminationRequest> SubmitTerminationRequestAsync(int grantMoaId, string reasonCode, decimal fundsDisbursed, decimal deliverablesValue, decimal clawbackAmount, string motivation, string currentUsername)
    {
        using var db = await _factory.CreateDbContextAsync();
        var count = await db.ContractTerminationRequests.CountAsync() + 1;
        var entity = new ContractTerminationRequest
        {
            GrantMoaId = grantMoaId,
            TerminationNumber = $"TERM-2026-{count:D4}",
            TerminationReasonCode = reasonCode,
            TotalFundsDisbursedToDate = fundsDisbursed,
            TotalValueDeliverablesAchieved = deliverablesValue,
            ClawbackAmountRecoverable = clawbackAmount,
            DetailedMotivation = motivation,
            StatusCode = "PendingLegalReview",
            CreatedBy = currentUsername,
            CreatedAt = DateTime.UtcNow
        };

        db.ContractTerminationRequests.Add(entity);
        await db.SaveChangesAsync();
        await _audit.LogAsync("ContractTerminationRequest", entity.Id, "SubmitTerminationRequest", currentUsername, new { entity.TerminationNumber, clawbackAmount });
        return entity;
    }

    public async Task<ContractTerminationRequest> SettleTerminationAsync(int id, string legalUserId)
    {
        using var db = await _factory.CreateDbContextAsync();
        var entity = await db.ContractTerminationRequests.FirstOrDefaultAsync(t => t.Id == id) ?? throw new InvalidOperationException($"Termination request #{id} not found.");

        entity.StatusCode = "TerminatedSettled";
        entity.SettledByUserId = legalUserId;
        entity.SettlementDate = DateTime.UtcNow;
        entity.ModifiedAt = DateTime.UtcNow;
        entity.ModifiedBy = legalUserId;

        var moa = await db.GrantMoas.FindAsync(entity.GrantMoaId);
        if (moa != null)
        {
            moa.MoaStatusCode = "Terminated";
            moa.ModifiedAt = DateTime.UtcNow;
            moa.ModifiedBy = legalUserId;
        }

        await db.SaveChangesAsync();
        await _audit.LogAsync("ContractTerminationRequest", entity.Id, "SettleTermination", legalUserId, new { entity.StatusCode });
        return entity;
    }

    public Task<List<ContractAddenda>> GetAllAddendasAsync() => GetAddendasAsync();
    public Task<ContractAddenda> ApproveContractAddendaAsync(int id, bool approved, string comments, string currentUsername = "FinanceDirector") => ApproveAddendaAsync(id, currentUsername);

    public async Task<ContractAddenda> CreateContractAddendaAsync(
        int grantMoaId,
        string addendaTypeCode,
        string variationReason,
        decimal adjustmentAmount,
        DateTime? revisedEndDate,
        string currentUsername = "SYSTEM")
    {
        return await CreateAddendaAsync(grantMoaId, addendaTypeCode, adjustmentAmount, revisedEndDate ?? DateTime.UtcNow.AddMonths(6), variationReason, currentUsername);
    }

    public async Task<ContractExtensionRequest> RequestContractExtensionAsync(
        int grantMoaId,
        int monthsRequested,
        string motivation,
        string currentUsername = "SYSTEM")
    {
        return await SubmitExtensionRequestAsync(grantMoaId, monthsRequested, DateTime.UtcNow.AddMonths(monthsRequested), "OnTrack", motivation, currentUsername);
    }

    public async Task<ContractExtensionRequest> ReviewContractExtensionAsync(
        int extensionRequestId,
        bool approved,
        string currentUsername = "ProjectManager")
    {
        return await ApproveExtensionRequestAsync(extensionRequestId, currentUsername);
    }

    public async Task<List<ContractAddenda>> GetAddendasByMoaIdAsync(int grantMoaId)
    {
        using var db = await _factory.CreateDbContextAsync();
        return await db.ContractAddendas
            .Where(a => a.GrantMoaId == grantMoaId)
            .OrderByDescending(a => a.CreatedAt)
            .ToListAsync();
    }

    public async Task<List<ContractExtensionRequest>> GetExtensionsByMoaIdAsync(int grantMoaId)
    {
        using var db = await _factory.CreateDbContextAsync();
        return await db.ContractExtensionRequests
            .Where(e => e.GrantMoaId == grantMoaId)
            .OrderByDescending(e => e.CreatedAt)
            .ToListAsync();
    }
}
