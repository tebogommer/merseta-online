using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class DgProjectImplementationService : IDgProjectImplementationService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly AuditService _audit;
    private readonly IErpIntegrationService _erpService;

    public DgProjectImplementationService(
        INsdmsDbContextFactory contextFactory,
        AuditService audit,
        IErpIntegrationService erpService)
    {
        _contextFactory = contextFactory;
        _audit = audit;
        _erpService = erpService;
    }

    public async Task<ProjectImplementationPlan> CreatePipAsync(
        ProjectImplementationPlan pip,
        List<PipLearnerAllocation> allocations,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        pip.PlanReferenceNumber = $"PIP-{DateTime.UtcNow:yyyyMM}-{Guid.NewGuid().ToString()[..4].ToUpper()}";
        pip.StatusCode = "Draft";
        pip.CreatedAt = DateTime.UtcNow;
        pip.CreatedBy = currentUsername;

        decimal totalAllowance = 0m;
        decimal totalTuition = 0m;
        int totalLearners = 0;

        foreach (var alloc in allocations)
        {
            alloc.CreatedAt = DateTime.UtcNow;
            alloc.CreatedBy = currentUsername;
            totalAllowance += alloc.TotalAllowanceBudget;
            totalTuition += alloc.TotalTuitionBudget;
            totalLearners += alloc.LearnerCount;
        }

        pip.TotalAwardedAmount = totalAllowance + totalTuition;
        pip.TotalLearnersAwarded = totalLearners;
        pip.Allocations = allocations;

        db.ProjectImplementationPlans.Add(pip);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "ProjectImplementationPlan", pip.Id, "CreatePip", currentUsername, null, pip);
        await db.SaveChangesAsync();

        return pip;
    }

    public async Task<ProjectImplementationPlan> SignOffContractsAsync(int pipId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var pip = await db.ProjectImplementationPlans.FindAsync(pipId);
        if (pip == null) throw new KeyNotFoundException($"ProjectImplementationPlan with ID {pipId} not found.");

        var before = new { pip.StatusCode, pip.ContractSignOffDate };
        pip.StatusCode = "ActiveContractsSigned";
        pip.ContractSignOffDate = DateTime.UtcNow;
        pip.ModifiedAt = DateTime.UtcNow;
        pip.ModifiedBy = currentUsername;

        _audit.LogAction(db, "ProjectImplementationPlan", pip.Id, "SignOffContracts", currentUsername, before, pip);
        await db.SaveChangesAsync();

        return pip;
    }

    public async Task<ProjectImplementationPlan?> GetPipByIdAsync(int pipId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.ProjectImplementationPlans
            .Include(p => p.Organisation)
            .Include(p => p.FundingWindow)
            .Include(p => p.Allocations)
            .Include(p => p.Claims.OrderBy(c => c.TrancheNumber))
            .FirstOrDefaultAsync(p => p.Id == pipId);
    }

    public async Task<List<ProjectImplementationPlan>> GetAllPipsAsync(int? organisationId = null, string? statusCode = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.ProjectImplementationPlans
            .Include(p => p.Organisation)
            .Include(p => p.Allocations)
            .Include(p => p.Claims)
            .AsQueryable();

        if (organisationId.HasValue && organisationId.Value > 0)
        {
            query = query.Where(p => p.OrganisationId == organisationId.Value);
        }

        if (!string.IsNullOrWhiteSpace(statusCode))
        {
            query = query.Where(p => p.StatusCode == statusCode);
        }

        return await query.OrderByDescending(p => p.CreatedAt).ToListAsync();
    }

    public async Task<GrantPaymentClaim> SubmitPaymentClaimAsync(GrantPaymentClaim claim, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        claim.ClaimNumber = $"CLM-DG-{DateTime.UtcNow:yyyyMMdd}-{Guid.NewGuid().ToString()[..4].ToUpper()}";
        claim.StatusCode = "PendingSubmission";
        claim.CreatedAt = DateTime.UtcNow;
        claim.CreatedBy = currentUsername;

        db.GrantPaymentClaims.Add(claim);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "GrantPaymentClaim", claim.Id, "SubmitPaymentClaim", currentUsername, null, claim);
        await db.SaveChangesAsync();

        return claim;
    }

    public async Task<GrantPaymentClaim> ApproveAndDisburseClaimAsync(int claimId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var claim = await db.GrantPaymentClaims
            .Include(c => c.ProjectImplementationPlan)
                .ThenInclude(p => p!.Organisation)
            .FirstOrDefaultAsync(c => c.Id == claimId);

        if (claim == null) throw new KeyNotFoundException($"GrantPaymentClaim with ID {claimId} not found.");

        var before = new { claim.StatusCode, claim.ApprovalDate, claim.ErpBatchNumber };
        claim.StatusCode = "ApprovedForPayment";
        claim.ApprovalDate = DateTime.UtcNow;
        claim.ApprovedByUserId = currentUsername;

        // Disburse via ERP Integration Service (safely simulates if off by default)
        var erpResult = await _erpService.DisbursePaymentAsync(
            claim.ProjectImplementationPlan?.Organisation?.SdlNumber ?? "SDL-UNKNOWN",
            claim.ClaimAmount,
            $"DG Tranche #{claim.TrancheNumber} for {claim.ClaimNumber}",
            currentUsername);

        claim.ErpBatchNumber = erpResult.BatchNumber;
        claim.StatusCode = erpResult.Success ? "Paid" : "ApprovedForPayment";
        claim.ModifiedAt = DateTime.UtcNow;
        claim.ModifiedBy = currentUsername;

        _audit.LogAction(db, "GrantPaymentClaim", claim.Id, "ApproveAndDisburseClaim", currentUsername, before, claim);
        await db.SaveChangesAsync();

        return claim;
    }
}
