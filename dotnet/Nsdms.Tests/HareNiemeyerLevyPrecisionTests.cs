using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Xunit;

namespace Nsdms.Tests;

public class HareNiemeyerLevyPrecisionTests
{
    private readonly TestDbContextFactory _factory;
    private readonly AuditService _audit;
    private readonly LevyService _levyService;
    private readonly FinanceService _financeService;

    public HareNiemeyerLevyPrecisionTests()
    {
        _factory = new TestDbContextFactory($"LevyPrecisionDb_{Guid.NewGuid()}");
        _audit = new AuditService(_factory);
        _levyService = new LevyService(_factory, _audit);
        _financeService = new FinanceService(_factory);
    }

    [Theory]
    [InlineData(1000000.33)]
    [InlineData(47891.17)]
    [InlineData(12345.67)]
    [InlineData(99.99)]
    [InlineData(500000.00)]
    [InlineData(10.01)]
    public void HareNiemeyerSplit_ShouldProduceZeroCentVariance(decimal totalAmount)
    {
        // Act
        var split = _levyService.CalculateStatutorySplit(totalAmount);

        // Assert
        decimal sumComponents = split.MandatoryGrantAmount + split.DiscretionaryGrantAmount + split.AdminLevyAmount + split.QctoLevyAmount;
        
        // Total SETA portion must match sum of all 4 components exactly to the cent
        Assert.Equal(split.TotalSetaPortion, sumComponents);

        // Validation with 0.00 tolerance must return true
        bool isValid = _levyService.ValidateLevySplit(
            totalAmount,
            split.MandatoryGrantAmount,
            split.DiscretionaryGrantAmount,
            split.AdminLevyAmount,
            split.QctoLevyAmount,
            tolerance: 0.00m);

        Assert.True(isValid);
    }

    [Fact]
    public async Task GrantMoa_CreateWithBalancedTranches_ShouldEqualTotalContractValueExact()
    {
        // Arrange
        var moa = new GrantMoa
        {
            MoaNumber = "MOA-BALANCED-01",
            ContractStartDate = new DateTime(2026, 4, 1),
            ContractEndDate = new DateTime(2027, 3, 31),
            TotalContractValue = 1000000.33m,
            MoaStatusCode = "Draft"
        };

        // Act
        var created = await _financeService.CreateGrantMoaAsync(moa, "FinanceOfficer");

        // Assert
        Assert.NotNull(created.Milestones);
        Assert.Equal(4, created.Milestones.Count);

        decimal sumTranches = created.Milestones.Sum(m => m.TrancheAmount);
        Assert.Equal(1000000.33m, sumTranches);
    }

    [Fact]
    public async Task ClawbackNettingEngine_ShouldOffsetOutstandingDebtsAgainstDisbursement()
    {
        // Arrange
        int orgId;
        using (var db = await _factory.CreateDbContextAsync())
        {
            var org = new Organisation { CompanyName = "SteelTech Manufacturing", SdlNumber = "L123456789" };
            db.Organisations.Add(org);
            await db.SaveChangesAsync();
            orgId = org.Id;

            // Add 2 outstanding SARS clawback audits
            db.SarsLevyReconAudits.Add(new SarsLevyReconAudit
            {
                OrganisationId = orgId,
                FinancialYear = "2025",
                SdlNumber = "L123456789",
                ClawbackActionRequired = true,
                ClawbackAmount = 15000.00m,
                AuditStatusCode = "ClawbackIssued",
                ReconciliationDate = DateTime.UtcNow.AddMonths(-2)
            });

            db.SarsLevyReconAudits.Add(new SarsLevyReconAudit
            {
                OrganisationId = orgId,
                FinancialYear = "2025",
                SdlNumber = "L123456789",
                ClawbackActionRequired = true,
                ClawbackAmount = 10000.00m,
                AuditStatusCode = "ClawbackIssued",
                ReconciliationDate = DateTime.UtcNow.AddMonths(-1)
            });

            await db.SaveChangesAsync();
        }

        // Act - Request a R 50,000 disbursement
        var nettingResult = await _financeService.NetClawbackLiabilitiesAsync(orgId, 50000.00m, "FinanceManager");

        // Assert
        Assert.Equal(50000.00m, nettingResult.GrossClaimAmount);
        Assert.Equal(25000.00m, nettingResult.TotalOutstandingClawbacks);
        Assert.Equal(25000.00m, nettingResult.TotalNettedAmount);
        Assert.Equal(25000.00m, nettingResult.NetPayableAmount);
        Assert.Equal(0.00m, nettingResult.RemainingClawbackBalance);
        Assert.Equal(2, nettingResult.SettledAuditRecordsCount);

        // Verify that audits are marked as Resolved in the database
        using (var db = await _factory.CreateDbContextAsync())
        {
            var audits = db.SarsLevyReconAudits.Where(a => a.OrganisationId == orgId).ToList();
            Assert.All(audits, a => Assert.Equal("Resolved", a.AuditStatusCode));
            Assert.All(audits, a => Assert.NotNull(a.ClawbackSettledDate));
        }
    }
}
