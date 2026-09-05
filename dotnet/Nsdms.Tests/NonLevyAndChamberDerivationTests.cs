using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging.Abstractions;
using Nsdms.Application.Common;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Domain.Lookups;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

public class NonLevyAndChamberDerivationTests
{
    private class TestDbContextFactory : INsdmsDbContextFactory
    {
        private readonly DbContextOptions<NsdmsDbContext> _options;

        public TestDbContextFactory(DbContextOptions<NsdmsDbContext> options)
        {
            _options = options;
        }

        public Task<INsdmsDbContext> CreateDbContextAsync(CancellationToken cancellationToken = default)
        {
            return Task.FromResult<INsdmsDbContext>(new NsdmsDbContext(_options));
        }

        public INsdmsDbContext CreateDbContext()
        {
            return new NsdmsDbContext(_options);
        }
    }

    private static (TestDbContextFactory Factory, AuditService Audit, DbContextOptions<NsdmsDbContext> Options) CreateContext()
    {
        var options = new DbContextOptionsBuilder<NsdmsDbContext>()
            .UseInMemoryDatabase(Guid.NewGuid().ToString())
            .Options;

        var factory = new TestDbContextFactory(options);
        var audit = new AuditService(factory);
        return (factory, audit, options);
    }

    [Fact]
    public async Task GenerateNextNonLevyNumberAsync_AllocatesSequentialTenCharNumber()
    {
        var (factory, audit, _) = CreateContext();
        var generator = new NonLevyNumberGeneratorService(factory, audit, NullLogger<NonLevyNumberGeneratorService>.Instance);

        var num1 = await generator.GenerateNextNonLevyNumberAsync();
        var num2 = await generator.GenerateNextNonLevyNumberAsync();

        Assert.NotNull(num1);
        Assert.NotNull(num2);
        Assert.Equal(10, num1.Length);
        Assert.Equal(10, num2.Length);
        Assert.StartsWith("N", num1);
        Assert.StartsWith("N", num2);
        Assert.True(generator.IsValidNNumberFormat(num1));
        Assert.True(generator.IsValidNNumberFormat(num2));
        Assert.NotEqual(num1, num2);
    }

    [Fact]
    public async Task GenerateNextNonLevyNumberAsync_SkipsExistingOutliers()
    {
        var (factory, audit, _) = CreateContext();
        using (var db = (NsdmsDbContext)await factory.CreateDbContextAsync())
        {
            // Seed existing organisation with N000100001
            db.Organisations.Add(new Organisation
            {
                CompanyName = "Existing TVET College",
                SdlNumber = "N000100001",
                LevyCategoryCode = "NON_LEVY_PAYING",
                ChamberCode = "SETA",
                GpVendorClass = "SETA"
            });
            await db.SaveChangesAsync();
        }

        var generator = new NonLevyNumberGeneratorService(factory, audit, NullLogger<NonLevyNumberGeneratorService>.Instance);
        var generated = await generator.GenerateNextNonLevyNumberAsync();

        Assert.NotNull(generated);
        Assert.NotEqual("N000100001", generated);
        Assert.Equal(10, generated.Length);
        Assert.True(generator.IsValidNNumberFormat(generated));
    }

    [Theory]
    [InlineData("38100", "METAL", "METAL")]
    [InlineData("38400", "AUTO", "AUTO")]
    [InlineData("63100", "MOTOR", "MOTOR")]
    [InlineData("35600", "PLASTICS", "PLASTICS")]
    [InlineData("35500", "NEW_TYRE", "NEW TYRE")]
    public async Task DeriveChamberAndVendorClassAsync_FromValidSicCode_MapsToCorrectChamberAndGpClass(
        string sicCode, string expectedChamber, string expectedGpClass)
    {
        var (factory, audit, _) = CreateContext();
        using (var db = (NsdmsDbContext)await factory.CreateDbContextAsync())
        {
            db.SicCodeTypes.Add(new SicCodeType
            {
                Code = sicCode,
                Name = $"SIC {sicCode} Manufacturing",
                ChamberCode = expectedChamber,
                Active = true
            });
            await db.SaveChangesAsync();
        }

        var chamberService = new ChamberDerivationService(factory, audit, NullLogger<ChamberDerivationService>.Instance);
        var result = await chamberService.DeriveChamberAndVendorClassAsync(sicCode);

        Assert.True(result.IsSuccess);
        Assert.Equal(expectedChamber, result.ChamberCode);
        Assert.Equal(expectedGpClass, result.GpVendorClass);
    }

    [Fact]
    public async Task DeriveChamberAndVendorClassAsync_FromPublicOrganisationType_MapsToSetaChamber()
    {
        var (factory, audit, _) = CreateContext();
        var chamberService = new ChamberDerivationService(factory, audit, NullLogger<ChamberDerivationService>.Instance);

        var result = await chamberService.DeriveChamberAndVendorClassAsync(
            sicCode: null, 
            organisationTypeCode: "PUBLIC_TVET");

        Assert.True(result.IsSuccess);
        Assert.Equal("SETA", result.ChamberCode);
        Assert.Equal("SETA", result.GpVendorClass);
        Assert.Equal("ORGANISATION_TYPE", result.ResolutionSource);
    }

    [Fact]
    public async Task DeriveChamberAndVendorClassAsync_WhenSicCodeIsUnmapped_FlagsFailure()
    {
        var (factory, audit, _) = CreateContext();
        var chamberService = new ChamberDerivationService(factory, audit, NullLogger<ChamberDerivationService>.Instance);

        var result = await chamberService.DeriveChamberAndVendorClassAsync(sicCode: "99999", organisationTypeCode: "COMMERCIAL");

        Assert.False(result.IsSuccess);
        Assert.Null(result.ChamberCode);
        Assert.Null(result.GpVendorClass);
        Assert.Equal("UNMAPPED", result.ResolutionSource);
    }

    [Fact]
    public async Task CreateAsync_ForNonLevyOrganisation_AutoGeneratesNNumberAndDerivesChamber()
    {
        var (factory, audit, _) = CreateContext();
        using (var db = (NsdmsDbContext)await factory.CreateDbContextAsync())
        {
            db.SicCodeTypes.Add(new SicCodeType { Code = "38401", Name = "Automotive OEM", ChamberCode = "AUTO", Active = true });
            await db.SaveChangesAsync();
        }

        var generator = new NonLevyNumberGeneratorService(factory, audit, NullLogger<NonLevyNumberGeneratorService>.Instance);
        var chamberService = new ChamberDerivationService(factory, audit, NullLogger<ChamberDerivationService>.Instance);
        var orgService = new OrganisationService(factory, audit, generator, chamberService);

        var org = new Organisation
        {
            CompanyName = "Gauteng TVET Institute",
            SdlNumber = "", // Empty to trigger generation
            LevyCategoryCode = "NON_LEVY_PAYING",
            OrganisationTypeCode = "PUBLIC_TVET",
            SicCode = "38401"
        };

        var created = await orgService.CreateAsync(org, "TestUser");

        Assert.NotNull(created.SdlNumber);
        Assert.StartsWith("N", created.SdlNumber);
        Assert.Equal(10, created.SdlNumber.Length);
        Assert.Equal("AUTO", created.ChamberCode);
        Assert.Equal("AUTO", created.GpVendorClass);
        Assert.False(created.HasMissingChamberMapping);
    }

    [Fact]
    public async Task ValidateChamberGovernanceAsync_WhenChamberMissing_BlocksGrantAndPaymentActions()
    {
        var (factory, audit, _) = CreateContext();
        int orgId;
        using (var db = (NsdmsDbContext)await factory.CreateDbContextAsync())
        {
            var org = new Organisation
            {
                CompanyName = "Unmapped Employer",
                SdlNumber = "L111222333",
                ChamberCode = null,
                GpVendorClass = null,
                HasMissingChamberMapping = true
            };
            db.Organisations.Add(org);
            await db.SaveChangesAsync();
            orgId = org.Id;
        }

        var chamberService = new ChamberDerivationService(factory, audit, NullLogger<ChamberDerivationService>.Instance);
        var validation = await chamberService.ValidateChamberGovernanceAsync(orgId);

        Assert.False(validation.IsCompliant);
        Assert.False(validation.CanSubmitGrantApplication);
        Assert.False(validation.CanSubmitWsp);
        Assert.False(validation.CanContractMoa);
        Assert.False(validation.CanDisbursePayment);
        Assert.NotNull(validation.BlockingReason);
    }

    [Fact]
    public async Task GrantService_CreateApplicationAsync_WhenOrganisationChamberUnmapped_ThrowsException()
    {
        var (factory, audit, _) = CreateContext();
        int orgId;
        using (var db = (NsdmsDbContext)await factory.CreateDbContextAsync())
        {
            var org = new Organisation
            {
                CompanyName = "Orphaned Firm",
                SdlNumber = "L999888777",
                HasMissingChamberMapping = true
            };
            db.Organisations.Add(org);
            await db.SaveChangesAsync();
            orgId = org.Id;
        }

        var grantService = new GrantService(factory, audit);
        var app = new GrantApplication
        {
            OrganisationId = orgId,
            ProjectTitle = "Skills Programme 2026",
            GrantTypeCode = "PIVOTAL",
            RequestedAmount = 150000m
        };

        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() => grantService.CreateApplicationAsync(app));
        Assert.Contains("blocked", ex.Message, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Chamber", ex.Message, StringComparison.OrdinalIgnoreCase);
    }

    [Fact]
    public async Task ErpIntegrationService_EnqueueVendorSyncAsync_WhenOrganisationChamberUnmapped_ThrowsException()
    {
        var (factory, audit, _) = CreateContext();
        int orgId;
        using (var db = (NsdmsDbContext)await factory.CreateDbContextAsync())
        {
            var org = new Organisation
            {
                CompanyName = "Blocked Org",
                SdlNumber = "N000100099",
                HasMissingChamberMapping = true
            };
            db.Organisations.Add(org);
            await db.SaveChangesAsync();
            orgId = org.Id;
        }

        var inMemoryConfig = new Microsoft.Extensions.Configuration.ConfigurationBuilder().Build();
        var config = new SystemConfigurationService(factory, inMemoryConfig, audit);
        var flags = new FeatureFlagService(factory, inMemoryConfig, audit);
        var erpService = new ErpIntegrationService(factory, flags, config, audit);

        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() => erpService.EnqueueVendorSyncAsync(orgId));
        Assert.Contains("blocked", ex.Message, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Chamber", ex.Message, StringComparison.OrdinalIgnoreCase);
    }
}
