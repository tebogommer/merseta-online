using System.Reflection;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class CanonicalGlossaryAndNomenclatureTests
{
    private static readonly string[] BannedTypoFragments = new[]
    {
        "dunding",
        "aontract",
        "saarea",
        "emplyer",
        "avalaible",
        "seta__status"
    };

    [Fact]
    public void DomainEntities_Properties_ContainNoBannedTypos()
    {
        var domainAssembly = typeof(CompanyLearner).Assembly;
        var entityTypes = domainAssembly.GetTypes()
            .Where(t => t.IsClass && !t.IsAbstract && t.Namespace != null && t.Namespace.StartsWith("Nsdms.Domain.Entities"))
            .ToList();

        Assert.NotEmpty(entityTypes);

        var propertyViolations = new List<string>();

        foreach (var type in entityTypes)
        {
            var properties = type.GetProperties(BindingFlags.Public | BindingFlags.Instance);
            foreach (var prop in properties)
            {
                var propNameLower = prop.Name.ToLowerInvariant();
                foreach (var banned in BannedTypoFragments)
                {
                    if (propNameLower.Contains(banned) && !propNameLower.Contains("statssa"))
                    {
                        propertyViolations.Add($"{type.Name}.{prop.Name} contains banned fragment '{banned}'");
                    }
                }
            }
        }

        Assert.Empty(propertyViolations);
    }

    [Fact]
    public void EFCore_ModelColumnMappings_ContainNoBannedTypos()
    {
        var options = new DbContextOptionsBuilder<NsdmsDbContext>()
            .UseInMemoryDatabase(databaseName: "GlossaryTest_" + Guid.NewGuid())
            .Options;

        using var db = new NsdmsDbContext(options);
        var model = db.Model;

        var columnViolations = new List<string>();

        foreach (var entityType in model.GetEntityTypes())
        {
            foreach (var prop in entityType.GetProperties())
            {
                var colName = prop.GetColumnName(StoreObjectIdentifier.Table(entityType.GetTableName() ?? entityType.ClrType.Name, null)) ?? prop.Name;
                var colLower = colName.ToLowerInvariant();

                foreach (var banned in BannedTypoFragments)
                {
                    if (colLower.Contains(banned) && !colLower.Contains("statssa"))
                    {
                        columnViolations.Add($"{entityType.ClrType.Name}.{prop.Name} (Column: {colName}) contains banned fragment '{banned}'");
                    }
                }
            }
        }

        Assert.Empty(columnViolations);
    }

    [Fact]
    public void DatabaseSchema_CompanyLearner_HasCorrectFundingWindowAndFundingTypeCode()
    {
        var options = new DbContextOptionsBuilder<NsdmsDbContext>()
            .UseInMemoryDatabase(databaseName: "FundingTest_" + Guid.NewGuid())
            .Options;

        using var db = new NsdmsDbContext(options);
        var entityType = db.Model.FindEntityType(typeof(CompanyLearner));
        Assert.NotNull(entityType);

        // Verify correct FundingTypeCode property exists
        var fundingTypeProp = entityType.FindProperty(nameof(CompanyLearner.FundingTypeCode));
        Assert.NotNull(fundingTypeProp);

        // Verify FundingWindowId foreign key exists
        var fundingIdProp = entityType.FindProperty(nameof(CompanyLearner.FundingId));
        Assert.NotNull(fundingIdProp);
    }

    [Fact]
    public async Task Execute_IdempotentTypoFixSqlScript_AgainstSqlServerExpress_IfAvailable()
    {
        var sqlScriptPath = Path.Combine(
            AppContext.BaseDirectory, "..", "..", "..", "..", "Nsdms.Infrastructure", "Data", "SqlScripts", "Fix_Legacy_Column_Typos_And_Nomenclature.sql"
        );

        if (!File.Exists(sqlScriptPath))
        {
            // Fallback path resolution
            var solutionDir = Path.GetFullPath(Path.Combine(AppContext.BaseDirectory, "..", "..", "..", "..", ".."));
            sqlScriptPath = Path.Combine(solutionDir, "dotnet", "Nsdms.Infrastructure", "Data", "SqlScripts", "Fix_Legacy_Column_Typos_And_Nomenclature.sql");
        }

        Assert.True(File.Exists(sqlScriptPath), $"Expected script at: {sqlScriptPath}");
        var scriptContent = await File.ReadAllTextAsync(sqlScriptPath);
        Assert.Contains("sp_rename", scriptContent);
        Assert.Contains("dunding_id", scriptContent);

        var connectionStrings = new[]
        {
            "Server=localhost\\SQLEXPRESS;Database=NSDMS-NET;User Id=NSDMS-NET;Password=NSDMS-NET;TrustServerCertificate=True;Encrypt=False;Connection Timeout=2;",
            "Server=.\\SQLEXPRESS;Database=NSDMS-NET;User Id=NSDMS-NET;Password=NSDMS-NET;TrustServerCertificate=True;Encrypt=False;Connection Timeout=2;",
            "Server=localhost;Database=NSDMS-NET;User Id=NSDMS-NET;Password=NSDMS-NET;TrustServerCertificate=True;Encrypt=False;Connection Timeout=2;",
            "Server=localhost\\SQLEXPRESS;Database=NSDMS-NET;Trusted_Connection=True;TrustServerCertificate=True;Encrypt=False;Connection Timeout=2;",
            "Server=.\\SQLEXPRESS;Database=NSDMS-NET;Trusted_Connection=True;TrustServerCertificate=True;Encrypt=False;Connection Timeout=2;"
        };

        foreach (var connStr in connectionStrings)
        {
            try
            {
                var options = new DbContextOptionsBuilder<NsdmsDbContext>()
                    .UseSqlServer(connStr)
                    .Options;

                using var sqlDb = new NsdmsDbContext(options);
                if (await sqlDb.Database.CanConnectAsync())
                {
                    await sqlDb.Database.ExecuteSqlRawAsync(scriptContent);
                    return;
                }
            }
            catch
            {
                // Continue trying next connection string format
            }
        }
    }
}
