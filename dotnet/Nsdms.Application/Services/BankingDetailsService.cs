using System.Text.Json;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class BankingDetailsService : IBankingDetailsService
{
    private readonly INsdmsDbContextFactory _factory;
    private readonly AuditService _audit;
    private readonly IBankservAvsService? _avsService;

    public BankingDetailsService(INsdmsDbContextFactory factory, AuditService audit, IBankservAvsService? avsService = null)
    {
        _factory = factory;
        _audit = audit;
        _avsService = avsService;
    }

    public async Task<List<BankingDetails>> GetBankingDetailsListAsync()
    {
        using var db = await _factory.CreateDbContextAsync();
        return await db.BankingDetails
            .Include(b => b.Organisation)
            .Include(b => b.TrainingProvider)
            .OrderByDescending(b => b.CreatedAt)
            .ToListAsync();
    }

    public async Task<BankingDetails?> GetBankingDetailsByIdAsync(int id)
    {
        using var db = await _factory.CreateDbContextAsync();
        return await db.BankingDetails
            .Include(b => b.Organisation)
            .Include(b => b.TrainingProvider)
            .FirstOrDefaultAsync(b => b.Id == id);
    }

    public async Task<BankingDetails> SubmitBankingDetailsAsync(int? organisationId, int? trainingProviderId, string bankName, string branchCode, string? branchName, string accountNumber, string accountHolderName, string accountTypeCode, string? docPath, DateTime? docDate, string currentUsername)
    {
        using var db = await _factory.CreateDbContextAsync();

        // 1. Anti-Collusion Duplicate Account Check (Check if account exists across distinct entities)
        var isDuplicateAccount = await db.BankingDetails.AnyAsync(b => 
            b.AccountNumber == accountNumber && 
            b.BranchCode == branchCode && 
            b.IsActive &&
            ((organisationId.HasValue && b.OrganisationId != organisationId.Value) ||
             (trainingProviderId.HasValue && b.TrainingProviderId != trainingProviderId.Value)));

        // 2. Check if this is an updated bank account on an existing organisation (triggers 14-day cooling-off)
        var hasExistingActiveBank = organisationId.HasValue && await db.BankingDetails.AnyAsync(b => b.OrganisationId == organisationId.Value && b.IsActive && b.ApprovalStatusCode == "FullyApproved");

        // 3. Run Real-Time Bankserv AVS Verification
        AvsVerificationResult? avsResult = null;
        if (_avsService != null)
        {
            avsResult = await _avsService.VerifyAccountAsync(new AvsVerificationRequest
            {
                BankName = bankName,
                BranchCode = branchCode,
                AccountNumber = accountNumber,
                AccountHolderName = accountHolderName
            });
        }

        var entity = new BankingDetails
        {
            OrganisationId = organisationId,
            TrainingProviderId = trainingProviderId,
            BankName = bankName,
            BranchCode = branchCode,
            BranchName = branchName,
            AccountNumber = accountNumber,
            AccountHolderName = accountHolderName,
            AccountTypeCode = accountTypeCode,
            BankConfirmationDocumentPath = docPath,
            BankConfirmationDate = docDate ?? DateTime.UtcNow,
            ApprovalStatusCode = isDuplicateAccount 
                ? "FlaggedForForensicReview" 
                : (avsResult != null && !avsResult.IsValid ? "AvsFailed" : "PendingVerification"),
            RequiresForensicApproval = isDuplicateAccount,
            FraudRiskFlags = isDuplicateAccount ? "CROSS_ORGANISATION_DUPLICATE_ACCOUNT" : (avsResult != null && !avsResult.IsValid ? avsResult.ResponseCode : null),
            IsCoolingOffActive = hasExistingActiveBank,
            CoolingOffExpiresAt = hasExistingActiveBank ? DateTime.UtcNow.AddDays(14) : null,
            AvsVerificationReference = avsResult?.VerificationReference,
            AvsVerifiedAt = avsResult?.VerifiedAt,
            AvsStatusResponse = avsResult?.ResponseMessage,
            IsErpActive = false,
            CreatedBy = currentUsername,
            CreatedAt = DateTime.UtcNow
        };

        db.BankingDetails.Add(entity);
        await db.SaveChangesAsync();

        var auditLog = new BankingDetailsAudit
        {
            BankingDetailsId = entity.Id,
            ActionType = isDuplicateAccount ? "SubmitBankingDetails_FLAGGED_DUPLICATE" : "SubmitBankingDetails",
            NewStateJson = JsonSerializer.Serialize(new { entity.BankName, entity.AccountNumber, entity.ApprovalStatusCode, entity.RequiresForensicApproval, entity.IsCoolingOffActive }),
            ChangedByUserId = currentUsername,
            ChangedAt = DateTime.UtcNow
        };
        db.BankingDetailsAudits.Add(auditLog);
        await db.SaveChangesAsync();

        await _audit.LogAsync("BankingDetails", entity.Id, "SubmitBankingDetails", currentUsername, new { entity.BankName, entity.AccountNumber, entity.ApprovalStatusCode, entity.RequiresForensicApproval, entity.IsCoolingOffActive });
        return entity;
    }

    public async Task<BankingDetails> FirstSignoffAsync(int id, bool approved, string? notes, string currentUsername)
    {
        using var db = await _factory.CreateDbContextAsync();
        var entity = await db.BankingDetails.FindAsync(id) ?? throw new InvalidOperationException($"Banking details #{id} not found.");

        entity.FirstSignoffUserId = currentUsername;
        entity.FirstSignoffDate = DateTime.UtcNow;
        entity.FirstSignoffNotes = notes;
        entity.ApprovalStatusCode = approved ? "FirstSignoffApproved" : "Rejected";
        entity.ModifiedAt = DateTime.UtcNow;
        entity.ModifiedBy = currentUsername;

        await db.SaveChangesAsync();
        await _audit.LogAsync("BankingDetails", entity.Id, "FirstSignoff", currentUsername, new { entity.ApprovalStatusCode, notes });
        return entity;
    }

    public async Task<BankingDetails> SecondSignoffAndActivateErpAsync(int id, bool approved, string? notes, string currentUsername)
    {
        using var db = await _factory.CreateDbContextAsync();
        var entity = await db.BankingDetails.FindAsync(id) ?? throw new InvalidOperationException($"Banking details #{id} not found.");

        entity.SecondSignoffUserId = currentUsername;
        entity.SecondSignoffDate = DateTime.UtcNow;
        entity.SecondSignoffNotes = notes;
        entity.ApprovalStatusCode = approved ? "FullyApproved" : "Rejected";
        entity.IsErpActive = approved;
        entity.ErpVendorId = approved ? $"ERP-VND-{entity.Id:D6}" : null;
        entity.ErpSyncDate = approved ? DateTime.UtcNow : null;
        entity.ModifiedAt = DateTime.UtcNow;
        entity.ModifiedBy = currentUsername;

        await db.SaveChangesAsync();
        await _audit.LogAsync("BankingDetails", entity.Id, "SecondSignoffAndActivateErp", currentUsername, new { entity.ApprovalStatusCode, entity.IsErpActive, entity.ErpVendorId });
        return entity;
    }

    public async Task<BankingDetails> SubmitBankingDetailsAsync(
        int organisationId,
        string bankName,
        string branchName,
        string branchCode,
        string accountNumber,
        string accountHolderName,
        string accountTypeCode,
        int? bankConfirmationDocId,
        string currentUsername = "SYSTEM")
    {
        using var db = await _factory.CreateDbContextAsync();
        var entity = new BankingDetails
        {
            OrganisationId = organisationId,
            BankName = bankName,
            BranchCode = branchCode,
            BranchName = branchName,
            AccountNumber = accountNumber,
            AccountHolderName = accountHolderName,
            AccountTypeCode = accountTypeCode,
            BankConfirmationDocumentId = bankConfirmationDocId,
            ApprovalStatusCode = "PendingValidation",
            IsErpActive = false,
            CreatedBy = currentUsername,
            CreatedAt = DateTime.UtcNow
        };

        db.BankingDetails.Add(entity);
        await db.SaveChangesAsync();

        var auditLog = new BankingDetailsAudit
        {
            BankingDetailsId = entity.Id,
            ActionType = "SubmitBankingDetails",
            NewStateJson = JsonSerializer.Serialize(new { entity.BankName, entity.AccountNumber, entity.ApprovalStatusCode }),
            ChangedByUserId = currentUsername,
            ChangedAt = DateTime.UtcNow
        };
        db.BankingDetailsAudits.Add(auditLog);
        await db.SaveChangesAsync();

        await _audit.LogAsync("BankingDetails", entity.Id, "SubmitBankingDetails", currentUsername, new { entity.BankName, entity.AccountNumber });
        return entity;
    }

    public async Task<BankingDetails> PerformFirstSignoffAsync(
        int bankingDetailsId,
        bool approved,
        string comments,
        string currentUsername = "FinanceOfficer")
    {
        return await FirstSignoffAsync(bankingDetailsId, approved, comments, currentUsername);
    }

    public async Task<BankingDetails> PerformFinalSignoffAsync(
        int bankingDetailsId,
        bool approved,
        string comments,
        string currentUsername = "FinanceManager")
    {
        return await SecondSignoffAndActivateErpAsync(bankingDetailsId, approved, comments, currentUsername);
    }

    public async Task<List<BankingDetails>> GetBankingDetailsAsync(int? organisationId = null)
    {
        using var db = await _factory.CreateDbContextAsync();
        var query = db.BankingDetails
            .Include(b => b.Organisation)
            .Include(b => b.TrainingProvider)
            .AsNoTracking();

        if (organisationId.HasValue)
        {
            query = query.Where(b => b.OrganisationId == organisationId.Value);
        }

        return await query.OrderByDescending(b => b.CreatedAt).ToListAsync();
    }

    public async Task<List<BankingDetailsAudit>> GetBankingDetailsAuditsAsync(int bankingDetailsId)
    {
        using var db = await _factory.CreateDbContextAsync();
        return await db.BankingDetailsAudits
            .Where(a => a.BankingDetailsId == bankingDetailsId)
            .OrderByDescending(a => a.ChangedAt)
            .ToListAsync();
    }
}
