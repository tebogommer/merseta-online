using Nsdms.Application.Services;
using Xunit;

namespace Nsdms.Tests;

public class DatabaseDocumentationTests
{
    [Fact]
    public async Task GetDatabaseSchemaDocumentation_ShouldReturnAllTablesAndColumns()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var docService = new DatabaseDocumentationService(factory);

        // Act
        var tables = await docService.GetDatabaseSchemaDocumentationAsync();

        // Assert
        Assert.NotEmpty(tables);
        Assert.Contains(tables, t => t.TableName.Equals("Organisation", StringComparison.OrdinalIgnoreCase));
        Assert.Contains(tables, t => t.TableName.Equals("CompanyLearner", StringComparison.OrdinalIgnoreCase) || t.TableName.Equals("CompanyLearners", StringComparison.OrdinalIgnoreCase));
        Assert.Contains(tables, t => t.TableName.Equals("grant_moa", StringComparison.OrdinalIgnoreCase) || t.TableName.Equals("GrantMoa", StringComparison.OrdinalIgnoreCase));
        Assert.Contains(tables, t => t.TableName.Equals("setmis_submission_batch", StringComparison.OrdinalIgnoreCase));

        // Verify lookup tables
        Assert.Contains(tables, t => t.SchemaName == "lookup");

        var orgTable = tables.First(t => t.TableName.Equals("Organisation", StringComparison.OrdinalIgnoreCase));
        Assert.NotEmpty(orgTable.Columns);
        Assert.Contains(orgTable.Columns, c => c.ColumnName == "SdlNumber" || c.PropertyName == "SdlNumber");
    }

    [Fact]
    public async Task GetTableDocumentation_ForGrantMoa_ShouldReturnSpecificDetails()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var docService = new DatabaseDocumentationService(factory);

        // Act
        var moaTable = await docService.GetTableDocumentationAsync("grant_moa", "dbo")
                       ?? await docService.GetTableDocumentationAsync("GrantMoa", "dbo");

        // Assert
        Assert.NotNull(moaTable);
        Assert.Equal("dbo", moaTable.SchemaName);
        Assert.Contains(moaTable.Columns, c => c.ColumnName == "TotalContractValue" || c.PropertyName == "TotalContractValue");
        Assert.NotEmpty(moaTable.PrimaryKeyColumns);
    }

    [Fact]
    public async Task ExportMarkdownDataDictionary_ShouldGenerateCompleteMarkdownWithTableOverview()
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var docService = new DatabaseDocumentationService(factory);

        // Act
        var markdown = await docService.ExportMarkdownDataDictionaryAsync();

        // Assert
        Assert.NotEmpty(markdown);
        Assert.Contains("# MerSETA NSDMS — Database Data Dictionary", markdown);
        Assert.Contains("## 📋 Schema Overview Table", markdown);
        Assert.Contains("## 🏛️ Table Details & Column Specifications", markdown);
        Assert.Contains("Organisation", markdown);
    }
}
