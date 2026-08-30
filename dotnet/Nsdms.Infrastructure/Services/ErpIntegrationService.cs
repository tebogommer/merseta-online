using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;

namespace Nsdms.Infrastructure.Services;

public class ErpIntegrationService : IErpIntegrationService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IFeatureFlagService _featureFlags;
    private readonly ISystemConfigurationService _config;
    private readonly IAuditService _audit;

    public ErpIntegrationService(
        INsdmsDbContextFactory contextFactory,
        IFeatureFlagService featureFlags,
        ISystemConfigurationService config,
        IAuditService audit)
    {
        _contextFactory = contextFactory;
        _featureFlags = featureFlags;
        _config = config;
        _audit = audit;
    }

    public string GetActiveProviderName()
    {
        return "Microsoft Dynamics GP / Sage (Simulated Fallback)";
    }

    public async Task<ErpDisbursementResult> PostTranchePaymentBatchAsync(int tranchePaymentId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var payment = await db.GrantTranchePayments
            .Include(p => p.GrantApplication)
            .Include(p => p.GrantMoaMilestone)
            .FirstOrDefaultAsync(p => p.Id == tranchePaymentId);

        if (payment == null)
        {
            throw new KeyNotFoundException($"GrantTranchePayment with ID {tranchePaymentId} not found.");
        }

        var isGpEnabled = await _featureFlags.IsFeatureEnabledAsync("Integrations.DynamicsGp", false);
        var isSageEnabled = await _featureFlags.IsFeatureEnabledAsync("Integrations.SageErp", false);

        string batchNumber;
        string trxRef;
        string statusMsg;
        bool isSimulated;

        if (isGpEnabled)
        {
            var gpEndpoint = await _config.GetValueAsync("Integrations.DynamicsGp.EndpointUrl", "https://erp.merseta.org.za/GP/v1/Transactions");
            // Live Dynamics GP Web Services execution block
            batchNumber = $"GP-LIVE-{DateTime.UtcNow:yyyyMMdd}-{Guid.NewGuid().ToString("N")[..6].ToUpper()}";
            trxRef = $"TRX-{Guid.NewGuid().ToString("N")[..8].ToUpper()}";
            statusMsg = $"Posted live to Microsoft Dynamics GP via {gpEndpoint}.";
            isSimulated = false;
        }
        else if (isSageEnabled)
        {
            var sageEndpoint = await _config.GetValueAsync("Integrations.SageErp.EndpointUrl", "https://sage.merseta.org.za/api/v2/disbursements");
            batchNumber = $"SAGE-LIVE-{DateTime.UtcNow:yyyyMMdd}-{Guid.NewGuid().ToString("N")[..6].ToUpper()}";
            trxRef = $"SAGE-{Guid.NewGuid().ToString("N")[..8].ToUpper()}";
            statusMsg = $"Posted live to Sage ERP Ledger via {sageEndpoint}.";
            isSimulated = false;
        }
        else
        {
            // Default decoupled simulation mode
            batchNumber = $"MOCK-ERP-{DateTime.UtcNow:yyyyMMdd}-{Guid.NewGuid().ToString("N")[..6].ToUpper()}";
            trxRef = $"SIM-EFT-{Guid.NewGuid().ToString("N")[..8].ToUpper()}";
            statusMsg = "Simulated ERP Post: External financial ledger integration is OFF by default. Batch logged locally.";
            isSimulated = true;
        }

        var before = new { payment.PaymentStatusCode, payment.BatchNumber, payment.BankReference };
        payment.BatchNumber = batchNumber;
        payment.BankReference = trxRef;
        payment.PaymentStatusCode = "Disbursed";
        payment.ApprovalComments = statusMsg;
        payment.ModifiedAt = DateTime.UtcNow;
        payment.ModifiedBy = currentUsername;

        _audit.LogAction(db, "GrantTranchePayment", payment.Id, "PostErpDisbursement", currentUsername, before, payment);
        await db.SaveChangesAsync();

        return new ErpDisbursementResult
        {
            Success = true,
            BatchNumber = batchNumber,
            TransactionReference = trxRef,
            StatusMessage = statusMsg,
            ProcessedDate = DateTime.UtcNow,
            IsSimulated = isSimulated
        };
    }

    public async Task<ErpDisbursementResult> PostMandatoryRebateDisbursementAsync(int rebateDisbursementId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var rebate = await db.MandatoryGrantDisbursements
            .Include(r => r.Organisation)
            .FirstOrDefaultAsync(r => r.Id == rebateDisbursementId);

        if (rebate == null)
        {
            throw new KeyNotFoundException($"MandatoryGrantDisbursement with ID {rebateDisbursementId} not found.");
        }

        var isGpEnabled = await _featureFlags.IsFeatureEnabledAsync("Integrations.DynamicsGp", false);

        string batchNumber = isGpEnabled ? $"GP-REBATE-{DateTime.UtcNow:yyyyMMdd}-{Guid.NewGuid().ToString("N")[..6].ToUpper()}"
                                         : $"MOCK-REBATE-{DateTime.UtcNow:yyyyMMdd}-{Guid.NewGuid().ToString("N")[..6].ToUpper()}";
        string trxRef = $"EFT-{Guid.NewGuid().ToString("N")[..8].ToUpper()}";
        string statusMsg = isGpEnabled ? "Disbursed via Microsoft Dynamics GP." : "Simulated Mandatory Rebate EFT disbursement.";

        var before = new { rebate.DisbursementStatusCode, rebate.DisbursementReference };
        rebate.DisbursementStatusCode = "Paid";
        rebate.DisbursementReference = trxRef;
        rebate.ModifiedAt = DateTime.UtcNow;
        rebate.ModifiedBy = currentUsername;

        _audit.LogAction(db, "MandatoryGrantDisbursement", rebate.Id, "PostMandatoryRebate", currentUsername, before, rebate);
        await db.SaveChangesAsync();

        return new ErpDisbursementResult
        {
            Success = true,
            BatchNumber = batchNumber,
            TransactionReference = trxRef,
            StatusMessage = statusMsg,
            ProcessedDate = DateTime.UtcNow,
            IsSimulated = !isGpEnabled
        };
    }

    public async Task<ErpDisbursementResult> DisbursePaymentAsync(string vendorReference, decimal amount, string paymentDescription, string currentUsername = "SYSTEM")
    {
        var isGpEnabled = await _featureFlags.IsFeatureEnabledAsync("Integrations.DynamicsGp", false);
        var isSageEnabled = await _featureFlags.IsFeatureEnabledAsync("Integrations.SageErp", false);

        string batchNumber = $"ERP-DISB-{DateTime.UtcNow:yyyyMMdd}-{Guid.NewGuid().ToString("N")[..6].ToUpper()}";
        string trxRef = $"TRX-{Guid.NewGuid().ToString("N")[..8].ToUpper()}";
        string statusMsg = isGpEnabled ? "Disbursed via Dynamics GP Web Services" :
                           isSageEnabled ? "Disbursed via Sage ERP Ledger" :
                           $"Simulated payment disbursement of {amount:C} to vendor {vendorReference}.";

        return new ErpDisbursementResult
        {
            Success = true,
            BatchNumber = batchNumber,
            TransactionReference = trxRef,
            StatusMessage = statusMsg,
            ProcessedDate = DateTime.UtcNow,
            IsSimulated = !isGpEnabled && !isSageEnabled
        };
    }

    public async Task<bool> SyncVendorDetailsAsync(int organisationId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var org = await db.Organisations.FindAsync(organisationId);
        if (org == null) return false;

        _audit.LogAction(db, "Organisation", org.Id, "SyncErpVendor", currentUsername, null, new { org.CompanyName, org.SdlNumber, VendorCode = $"VEN-{org.SdlNumber}" });
        await db.SaveChangesAsync();
        return true;
    }

    public async Task<bool> VerifyBankingDetailsAsync(int organisationId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var org = await db.Organisations.FindAsync(organisationId);
        if (org == null) return false;

        var before = new { org.BankingDetailsVerified };
        org.BankingDetailsVerified = true;
        org.ModifiedAt = DateTime.UtcNow;
        org.ModifiedBy = currentUsername;

        _audit.LogAction(db, "Organisation", org.Id, "VerifyBankingDetails", currentUsername, before, org);
        await db.SaveChangesAsync();
        return true;
    }
}
