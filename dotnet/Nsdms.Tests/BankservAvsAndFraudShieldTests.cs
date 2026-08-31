using Microsoft.Extensions.Configuration;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

public class BankservAvsAndFraudShieldTests
{
    private readonly TestDbContextFactory _factory;
    private readonly AuditService _audit;
    private readonly IConfiguration _configRoot;
    private readonly SystemConfigurationService _config;
    private readonly BankservAvsService _avsService;
    private readonly BankingDetailsService _bankingService;
    private readonly FinanceService _financeService;

    public BankservAvsAndFraudShieldTests()
    {
        _factory = new TestDbContextFactory($"AvsFraudDb_{Guid.NewGuid()}");
        _audit = new AuditService(_factory);
        _configRoot = new ConfigurationBuilder().Build();
        _config = new SystemConfigurationService(_factory, _configRoot, _audit);
        _avsService = new BankservAvsService(_config);
        _bankingService = new BankingDetailsService(_factory, _audit, _avsService);
        _financeService = new FinanceService(_factory);
    }

    [Fact]
    public async Task AvsService_ValidAccount_ShouldPassVerification()
    {
        // Arrange
        var req = new AvsVerificationRequest
        {
            BankName = "Standard Bank",
            BranchCode = "051001",
            AccountNumber = "0123456789",
            AccountHolderName = "MerSETA Approved Employer Pty Ltd",
            IdOrRegistrationNumber = "2015/123456/07"
        };

        // Act
        var result = await _avsService.VerifyAccountAsync(req);

        // Assert
        Assert.True(result.IsValid);
        Assert.True(result.AccountExists);
        Assert.Equal("AVS-00-SUCCESS", result.ResponseCode);
        Assert.NotNull(result.VerificationReference);
    }

    [Fact]
    public async Task AvsService_InvalidFormat_ShouldReject()
    {
        // Arrange
        var req = new AvsVerificationRequest
        {
            BankName = "FNB",
            BranchCode = "250655",
            AccountNumber = "123", // Too short
            AccountHolderName = "Test"
        };

        // Act
        var result = await _avsService.VerifyAccountAsync(req);

        // Assert
        Assert.False(result.IsValid);
        Assert.Equal("AVS-02-ACCOUNT_LENGTH", result.ResponseCode);
    }

    [Fact]
    public async Task DuplicateAccountCrossOrganisation_ShouldFlagForForensicReview()
    {
        // Arrange
        int orgAId, orgBId;
        using (var db = await _factory.CreateDbContextAsync())
        {
            var orgA = new Organisation { CompanyName = "Org Alpha", SdlNumber = "L100000001" };
            var orgB = new Organisation { CompanyName = "Org Beta", SdlNumber = "L200000002" };
            db.Organisations.AddRange(orgA, orgB);
            await db.SaveChangesAsync();
            orgAId = orgA.Id;
            orgBId = orgB.Id;
        }

        // Submit bank account for Org A
        await _bankingService.SubmitBankingDetailsAsync(
            orgAId, null, "Standard Bank", "051001", "Johannesburg", "62000012345", "Alpha Enterprise", "Current", null, DateTime.UtcNow, "AdminA");

        // Act - Submit identical bank account for Org B (potential syndicate collision)
        var entityB = await _bankingService.SubmitBankingDetailsAsync(
            orgBId, null, "Standard Bank", "051001", "Johannesburg", "62000012345", "Beta Enterprise", "Current", null, DateTime.UtcNow, "AdminB");

        // Assert
        Assert.True(entityB.RequiresForensicApproval);
        Assert.Equal("FlaggedForForensicReview", entityB.ApprovalStatusCode);
        Assert.Equal("CROSS_ORGANISATION_DUPLICATE_ACCOUNT", entityB.FraudRiskFlags);
    }

    [Fact]
    public async Task UpdatedBankAccount_ShouldTrigger14DayCoolingOffHoldAndBlockDisbursement()
    {
        // Arrange
        int orgId, disbId;
        using (var db = await _factory.CreateDbContextAsync())
        {
            var org = new Organisation { CompanyName = "Gamma Dynamics", SdlNumber = "L300000003" };
            db.Organisations.Add(org);
            await db.SaveChangesAsync();
            orgId = org.Id;

            // Existing approved bank account
            var initialBank = new BankingDetails
            {
                OrganisationId = orgId,
                BankName = "Nedbank",
                BranchCode = "198765",
                AccountNumber = "1000123456",
                AccountHolderName = "Gamma Dynamics",
                ApprovalStatusCode = "FullyApproved",
                IsActive = true
            };
            db.BankingDetails.Add(initialBank);

            var wsp = new WspSubmission { OrganisationId = orgId, FinYear = 2026, WspApprovalStatusCode = "Approved" };
            db.WspSubmissions.Add(wsp);
            await db.SaveChangesAsync();

            var disb = new MandatoryGrantDisbursement
            {
                OrganisationId = orgId,
                WspSubmissionId = wsp.Id,
                DisbursementReference = "MG-2026-COOLING",
                FinYear = 2026,
                CalculatedRebateAmount = 10000m,
                DisbursementStatusCode = "Calculated"
            };
            db.MandatoryGrantDisbursements.Add(disb);
            await db.SaveChangesAsync();
            disbId = disb.Id;
        }

        // Act - Submit a replacement bank account (new account number)
        var updatedBank = await _bankingService.SubmitBankingDetailsAsync(
            orgId, null, "ABSA", "632005", "Pretoria", "4000998877", "Gamma Dynamics New Account", "Current", null, DateTime.UtcNow, "FinanceUser");

        // Assert cooling off is active on the updated bank account
        Assert.True(updatedBank.IsCoolingOffActive);
        Assert.NotNull(updatedBank.CoolingOffExpiresAt);
        Assert.True(updatedBank.CoolingOffExpiresAt > DateTime.UtcNow.AddDays(13));

        // Assert that disbursement approval is blocked by the cooling-off security gate
        var ex = await Assert.ThrowsAsync<InvalidOperationException>(async () =>
        {
            await _financeService.ApproveMandatoryDisbursementAsync(disbId, "FinanceApprover", "BATCH-001");
        });

        Assert.Contains("cooling-off", ex.Message, StringComparison.OrdinalIgnoreCase);
    }
}
