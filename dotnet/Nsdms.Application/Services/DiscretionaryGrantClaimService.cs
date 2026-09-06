using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class DgMoaFinancialAggregateDto
{
    public int MoaId { get; set; }
    public string MoaNumber { get; set; } = string.Empty;
    public int OrganisationId { get; set; }
    public string OrganisationName { get; set; } = string.Empty;
    public int? FundingWindowId { get; set; }
    public decimal TotalMoaAllocation { get; set; }
    public decimal TotalClaimedAmount { get; set; }
    public decimal TotalApprovedAmount { get; set; }
    public decimal TotalDisbursedAmount { get; set; }
    public decimal RemainingEnvelopeBalance { get; set; }
    public bool IsFullyClaimed { get; set; }
    public List<GrantPaymentClaim> Claims { get; set; } = new();
    public List<GrantMoaMilestone> Milestones { get; set; } = new();
}

public class SubmitDgClaimRequest
{
    public int ProjectImplementationPlanId { get; set; }
    public int? GrantMoaMilestoneId { get; set; }
    public int TrancheNumber { get; set; } = 1;
    public decimal ClaimAmount { get; set; }
    public string DeliverableDescription { get; set; } = string.Empty;
    public string? ProofOfDeliveryDocumentUri { get; set; }
}

public class ApproveDgClaimRequest
{
    public int ClaimId { get; set; }
    public string ApprovalRole { get; set; } = "CLO"; // CLO, FinanceOfficer, CFO
    public string ApproverName { get; set; } = string.Empty;
    public string? Comments { get; set; }
}

public class ErpPaymentBatchExportDto
{
    public ErpPaymentBatchHeader BatchHeader { get; set; } = default!;
    public List<ErpPaymentBatchEntry> Entries { get; set; } = new();
    public string FormattedTsv { get; set; } = string.Empty;
}

public interface IDiscretionaryGrantClaimService
{
    Task<DgMoaFinancialAggregateDto> GetFinancialAggregateForMoaAsync(int moaId);
    Task<GrantPaymentClaim> SubmitTrancheClaimAsync(SubmitDgClaimRequest request, string currentUsername = "SYSTEM");
    Task<GrantPaymentClaim> ProcessClaimApprovalAsync(ApproveDgClaimRequest request, string currentUsername = "SYSTEM");
    Task<ErpPaymentBatchHeader> StageApprovedClaimsToErpBatchAsync(List<int> claimIds, string currentUsername = "SYSTEM");
    Task<ErpPaymentBatchExportDto> ExportBatchForErpAsync(int batchId, string currentUsername = "SYSTEM");
    Task<List<GrantPaymentClaim>> GetAllClaimsAsync(int? pipId = null, string? statusCode = null);
}

public class DiscretionaryGrantClaimService : IDiscretionaryGrantClaimService
{
    public const decimal CfoDualSignoffThreshold = 500000.00m; // Executive R500k statutory threshold

    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;
    private readonly ISystemConfigurationService? _systemConfig;

    public DiscretionaryGrantClaimService(
        INsdmsDbContextFactory contextFactory, 
        IAuditService audit,
        ISystemConfigurationService? systemConfig = null)
    {
        _contextFactory = contextFactory;
        _audit = audit;
        _systemConfig = systemConfig;
    }

    public async Task<DgMoaFinancialAggregateDto> GetFinancialAggregateForMoaAsync(int moaId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var moa = await db.GrantMoas
            .Include(m => m.GrantApplication)
                .ThenInclude(a => a!.Organisation)
            .Include(m => m.Milestones)
            .FirstOrDefaultAsync(m => m.Id == moaId);

        if (moa == null)
            throw new KeyNotFoundException($"GrantMoa with ID {moaId} not found.");

        // Find associated PIP(s)
        var pips = await db.ProjectImplementationPlans
            .Include(p => p.Claims)
            .Where(p => p.GrantMoaId == moaId || (p.GrantApplicationId.HasValue && p.GrantApplicationId == moa.GrantApplicationId))
            .ToListAsync();

        var allClaims = pips.SelectMany(p => p.Claims).ToList();

        decimal totalClaimed = allClaims.Sum(c => c.ClaimAmount);
        decimal totalApproved = allClaims
            .Where(c => c.StatusCode == "FinanceApproved" || c.StatusCode == "CfoApproved" || c.StatusCode == "Paid")
            .Sum(c => c.ClaimAmount);
        decimal totalDisbursed = allClaims
            .Where(c => c.StatusCode == "Paid")
            .Sum(c => c.ClaimAmount);

        decimal remainingBalance = moa.TotalContractValue - totalClaimed;
        if (remainingBalance < 0) remainingBalance = 0;

        return new DgMoaFinancialAggregateDto
        {
            MoaId = moa.Id,
            MoaNumber = moa.MoaNumber,
            OrganisationId = moa.GrantApplication?.OrganisationId ?? 0,
            OrganisationName = moa.GrantApplication?.Organisation?.CompanyName ?? "Beneficiary Employer",
            FundingWindowId = moa.GrantApplication?.FundingWindowId,
            TotalMoaAllocation = moa.TotalContractValue,
            TotalClaimedAmount = totalClaimed,
            TotalApprovedAmount = totalApproved,
            TotalDisbursedAmount = totalDisbursed,
            RemainingEnvelopeBalance = remainingBalance,
            IsFullyClaimed = remainingBalance <= 0,
            Claims = allClaims.OrderBy(c => c.TrancheNumber).ToList(),
            Milestones = moa.Milestones.OrderBy(m => m.MilestoneNumber).ToList()
        };
    }

    public async Task<GrantPaymentClaim> SubmitTrancheClaimAsync(SubmitDgClaimRequest request, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var pip = await db.ProjectImplementationPlans
            .Include(p => p.Claims)
            .Include(p => p.GrantMoa)
            .Include(p => p.GrantApplication)
            .FirstOrDefaultAsync(p => p.Id == request.ProjectImplementationPlanId);

        if (pip == null)
            throw new KeyNotFoundException($"ProjectImplementationPlan with ID {request.ProjectImplementationPlanId} not found.");

        decimal totalBudget = pip.GrantMoa?.TotalContractValue ?? pip.TotalAwardedAmount;
        if (totalBudget <= 0)
            throw new InvalidOperationException("Cannot submit claim: Project Implementation Plan has no valid awarded budget envelope.");

        // Validate budget headroom
        decimal alreadyClaimed = pip.Claims.Sum(c => c.ClaimAmount);
        decimal availableBudget = totalBudget - alreadyClaimed;

        if (request.ClaimAmount > availableBudget)
        {
            throw new InvalidOperationException($"Claim amount R {request.ClaimAmount:N2} exceeds available budget envelope of R {availableBudget:N2} (Total Allocation: R {totalBudget:N2}, Previously Claimed: R {alreadyClaimed:N2}).");
        }

        decimal cfoThreshold = _systemConfig != null
            ? await _systemConfig.GetValueAsync("FinancialRules.DualApprovalCfoThreshold", CfoDualSignoffThreshold)
            : CfoDualSignoffThreshold;

        bool requiresCfo = request.ClaimAmount >= cfoThreshold;
        string claimRef = $"CLM-DG-PIP{pip.Id}-T{request.TrancheNumber}-{DateTime.UtcNow:MMdd}";

        var claim = new GrantPaymentClaim
        {
            ProjectImplementationPlanId = pip.Id,
            GrantMoaMilestoneId = request.GrantMoaMilestoneId,
            ClaimNumber = claimRef,
            TrancheNumber = request.TrancheNumber,
            ClaimAmount = request.ClaimAmount,
            DeliverableDescription = request.DeliverableDescription,
            StatusCode = "PendingSubmission",
            RequiresCfoApproval = requiresCfo,
            CreatedBy = currentUsername
        };

        db.GrantPaymentClaims.Add(claim);
        await db.SaveChangesAsync();

        await _audit.LogAsync("GrantPaymentClaim", claim.Id, "SubmitTrancheClaim", currentUsername, new
        {
            ClaimNumber = claimRef,
            Tranche = request.TrancheNumber,
            Amount = request.ClaimAmount,
            RequiresCfo = requiresCfo,
            RemainingBudget = availableBudget - request.ClaimAmount
        });

        return claim;
    }

    public async Task<GrantPaymentClaim> ProcessClaimApprovalAsync(ApproveDgClaimRequest request, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var claim = await db.GrantPaymentClaims
            .Include(c => c.ProjectImplementationPlan)
            .FirstOrDefaultAsync(c => c.Id == request.ClaimId);

        if (claim == null)
            throw new KeyNotFoundException($"GrantPaymentClaim with ID {request.ClaimId} not found.");

        string previousStatus = claim.StatusCode;

        switch (request.ApprovalRole.ToUpperInvariant())
        {
            case "CLO":
                claim.CloVerifiedBy = request.ApproverName;
                claim.CloVerifiedDate = DateTime.UtcNow;
                claim.StatusCode = "CloVerified";
                break;

            case "FINANCEOFFICER":
            case "FINANCE":
                claim.FinanceOfficerApprovedBy = request.ApproverName;
                claim.FinanceOfficerApprovedDate = DateTime.UtcNow;
                
                // If claim requires CFO (high-value threshold) and CFO hasn't signed yet, keep waiting for CFO
                if (claim.RequiresCfoApproval)
                {
                    claim.StatusCode = "PendingCfoApproval";
                }
                else
                {
                    claim.StatusCode = "FinanceApproved";
                    claim.ApprovalDate = DateTime.UtcNow;
                    claim.ApprovedByUserId = currentUsername;
                    claim.PaymentVoucherNumber = $"PV-{DateTime.UtcNow.Year}-DG-{claim.Id:D5}";
                }
                break;

            case "CFO":
            case "EXECUTIVE":
                claim.CfoApprovedBy = request.ApproverName;
                claim.CfoApprovedDate = DateTime.UtcNow;
                claim.StatusCode = "CfoApproved";
                claim.ApprovalDate = DateTime.UtcNow;
                claim.ApprovedByUserId = currentUsername;
                claim.PaymentVoucherNumber = $"PV-{DateTime.UtcNow.Year}-DG-{claim.Id:D5}";
                break;

            default:
                throw new ArgumentException($"Unknown approval role '{request.ApprovalRole}'.");
        }

        await db.SaveChangesAsync();

        await _audit.LogAsync("GrantPaymentClaim", claim.Id, $"ClaimApproval_{request.ApprovalRole}", currentUsername, new
        {
            PreviousStatus = previousStatus,
            NewStatus = claim.StatusCode,
            Approver = request.ApproverName,
            Voucher = claim.PaymentVoucherNumber,
            request.Comments
        });

        return claim;
    }

    public async Task<ErpPaymentBatchHeader> StageApprovedClaimsToErpBatchAsync(List<int> claimIds, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var claims = await db.GrantPaymentClaims
            .Include(c => c.ProjectImplementationPlan)
                .ThenInclude(p => p!.Organisation)
            .Where(c => claimIds.Contains(c.Id))
            .ToListAsync();

        if (!claims.Any())
            throw new InvalidOperationException("No claims found to batch.");

        string batchNumber = $"ERP-BATCH-{DateTime.UtcNow:yyyyMMdd}-{new Random().Next(100, 999)}";
        decimal totalAmount = claims.Sum(c => c.ClaimAmount);

        var batchHeader = new ErpPaymentBatchHeader
        {
            BatchNumber = batchNumber,
            BatchTypeCode = "DG_TRANCHE",
            TotalAmount = totalAmount,
            ItemCount = claims.Count,
            BatchStatusCode = "Draft",
            CreatedBy = currentUsername
        };

        db.ErpPaymentBatchHeaders.Add(batchHeader);
        await db.SaveChangesAsync();

        foreach (var claim in claims)
        {
            var org = claim.ProjectImplementationPlan?.Organisation;
            var entry = new ErpPaymentBatchEntry
            {
                ErpPaymentBatchHeaderId = batchHeader.Id,
                GrantPaymentClaimId = claim.Id,
                OrganisationId = org?.Id ?? claim.ProjectImplementationPlan?.OrganisationId ?? 1,
                PaymentVoucherNumber = claim.PaymentVoucherNumber ?? $"PV-{DateTime.UtcNow.Year}-DG-{claim.Id:D5}",
                VendorNumber = org?.SdlNumber ?? $"VND-{org?.Id ?? 1}",
                BankAccountNumber = "62012345678",
                BankBranchCode = "250655",
                PaymentAmount = claim.ClaimAmount,
                PaymentDescription = $"DG Tranche {claim.TrancheNumber} - {claim.ClaimNumber}",
                EntryStatusCode = "IncludedInBatch",
                CreatedBy = currentUsername
            };
            db.ErpPaymentBatchEntries.Add(entry);
            claim.ErpBatchNumber = batchNumber;
        }

        await db.SaveChangesAsync();

        await _audit.LogAsync("ErpPaymentBatchHeader", batchHeader.Id, "StageClaimsToBatch", currentUsername, new
        {
            BatchNumber = batchNumber,
            ItemCount = claims.Count,
            TotalAmount = totalAmount
        });

        return batchHeader;
    }

    public async Task<ErpPaymentBatchExportDto> ExportBatchForErpAsync(int batchId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var header = await db.ErpPaymentBatchHeaders
            .Include(h => h.Entries)
                .ThenInclude(e => e.Organisation)
            .FirstOrDefaultAsync(h => h.Id == batchId);

        if (header == null)
            throw new KeyNotFoundException($"ErpPaymentBatchHeader with ID {batchId} not found.");

        header.BatchStatusCode = "ExportedToErp";
        header.ExportedDate = DateTime.UtcNow;
        header.ExportedByUserId = currentUsername;
        header.ExportFileName = $"{header.BatchNumber}.tsv";

        // Mark all claims as Paid in simulation
        var claimIds = header.Entries.Where(e => e.GrantPaymentClaimId.HasValue).Select(e => e.GrantPaymentClaimId!.Value).ToList();
        var claims = await db.GrantPaymentClaims.Where(c => claimIds.Contains(c.Id)).ToListAsync();
        foreach (var claim in claims)
        {
            claim.StatusCode = "Paid";
        }

        await db.SaveChangesAsync();

        var sb = new System.Text.StringBuilder();
        sb.AppendLine("VoucherNumber\tVendorNumber\tOrganisationName\tBankAccount\tBranchCode\tAmount\tDescription");
        foreach (var entry in header.Entries)
        {
            var orgName = entry.Organisation?.CompanyName ?? "N/A";
            string formattedAmount = entry.PaymentAmount.ToString("F2", System.Globalization.CultureInfo.InvariantCulture);
            sb.AppendLine($"{entry.PaymentVoucherNumber}\t{entry.VendorNumber}\t{orgName}\t{entry.BankAccountNumber}\t{entry.BankBranchCode}\t{formattedAmount}\t{entry.PaymentDescription}");
        }

        await _audit.LogAsync("ErpPaymentBatchHeader", header.Id, "ExportBatchToErp", currentUsername, new
        {
            header.BatchNumber,
            header.ExportFileName,
            header.TotalAmount
        });

        return new ErpPaymentBatchExportDto
        {
            BatchHeader = header,
            Entries = header.Entries.ToList(),
            FormattedTsv = sb.ToString()
        };
    }

    public async Task<List<GrantPaymentClaim>> GetAllClaimsAsync(int? pipId = null, string? statusCode = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.GrantPaymentClaims
            .Include(c => c.ProjectImplementationPlan)
                .ThenInclude(p => p!.Organisation)
            .AsQueryable();

        if (pipId.HasValue)
            query = query.Where(c => c.ProjectImplementationPlanId == pipId.Value);

        if (!string.IsNullOrWhiteSpace(statusCode))
            query = query.Where(c => c.StatusCode == statusCode);

        return await query.OrderByDescending(c => c.CreatedAt).ToListAsync();
    }
}
