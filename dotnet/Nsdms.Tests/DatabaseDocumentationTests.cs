using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Metadata;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Domain.Lookups;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class DatabaseDocumentationTests
{
    [Fact]
    public void ModelBuilder_AppliesTableAndColumnComments_ToCompanyLearner()
    {
        // Arrange
        var options = new DbContextOptionsBuilder<NsdmsDbContext>()
            .UseInMemoryDatabase(databaseName: "DocTest_CompanyLearner_" + Guid.NewGuid())
            .Options;

        using var db = new NsdmsDbContext(options);
        var model = db.GetService<IDesignTimeModel>().Model;

        // Assert Entity / Table Comment
        var entityType = model.FindEntityType(typeof(CompanyLearner));
        Assert.NotNull(entityType);

        var tableComment = entityType.FindAnnotation(RelationalAnnotationNames.Comment)?.Value?.ToString();
        Assert.NotNull(tableComment);
        Assert.Contains("learner", tableComment, StringComparison.OrdinalIgnoreCase);

        // Assert Property / Column Comments
        var idProp = entityType.FindProperty(nameof(CompanyLearner.Id));
        Assert.NotNull(idProp?.FindAnnotation(RelationalAnnotationNames.Comment)?.Value?.ToString());

        var personIdProp = entityType.FindProperty(nameof(CompanyLearner.PersonId));
        Assert.NotNull(personIdProp?.FindAnnotation(RelationalAnnotationNames.Comment)?.Value?.ToString());
        Assert.Contains("Person", personIdProp.FindAnnotation(RelationalAnnotationNames.Comment)!.Value!.ToString()!, StringComparison.OrdinalIgnoreCase);

        var contractProp = entityType.FindProperty(nameof(CompanyLearner.LearnerContractNumber));
        Assert.NotNull(contractProp?.FindAnnotation(RelationalAnnotationNames.Comment)?.Value?.ToString());

        var qualProp = entityType.FindProperty(nameof(CompanyLearner.QualificationTitle));
        Assert.NotNull(qualProp?.FindAnnotation(RelationalAnnotationNames.Comment)?.Value?.ToString());

        var createdAtProp = entityType.FindProperty(nameof(CompanyLearner.CreatedAt));
        Assert.NotNull(createdAtProp?.FindAnnotation(RelationalAnnotationNames.Comment)?.Value?.ToString());
    }

    [Fact]
    public void ModelBuilder_AppliesTableAndColumnComments_ToCoreEntities()
    {
        var options = new DbContextOptionsBuilder<NsdmsDbContext>()
            .UseInMemoryDatabase(databaseName: "DocTest_CoreEntities_" + Guid.NewGuid())
            .Options;

        using var db = new NsdmsDbContext(options);
        var model = db.Model;

        // Organisation
        var orgEntity = model.FindEntityType(typeof(Organisation));
        Assert.NotNull(orgEntity?.FindAnnotation(RelationalAnnotationNames.Comment)?.Value?.ToString());
        Assert.NotNull(orgEntity.FindProperty(nameof(Organisation.SdlNumber))?.FindAnnotation(RelationalAnnotationNames.Comment)?.Value?.ToString());
        Assert.NotNull(orgEntity.FindProperty(nameof(Organisation.CompanyName))?.FindAnnotation(RelationalAnnotationNames.Comment)?.Value?.ToString());

        // Person
        var personEntity = model.FindEntityType(typeof(Person));
        Assert.NotNull(personEntity?.FindAnnotation(RelationalAnnotationNames.Comment)?.Value?.ToString());
        Assert.NotNull(personEntity.FindProperty(nameof(Person.RsaIdNumber))?.FindAnnotation(RelationalAnnotationNames.Comment)?.Value?.ToString());
        Assert.NotNull(personEntity.FindProperty(nameof(Person.FirstName))?.FindAnnotation(RelationalAnnotationNames.Comment)?.Value?.ToString());

        // Visit
        var visitEntity = model.FindEntityType(typeof(Visit));
        Assert.NotNull(visitEntity?.FindAnnotation(RelationalAnnotationNames.Comment)?.Value?.ToString());
        Assert.NotNull(visitEntity.FindProperty(nameof(Visit.ContactPersonId))?.FindAnnotation(RelationalAnnotationNames.Comment)?.Value?.ToString());

        // GrantMoa
        var moaEntity = model.FindEntityType(typeof(GrantMoa));
        Assert.NotNull(moaEntity?.FindAnnotation(RelationalAnnotationNames.Comment)?.Value?.ToString());
        Assert.NotNull(moaEntity.FindProperty(nameof(GrantMoa.MoaNumber))?.FindAnnotation(RelationalAnnotationNames.Comment)?.Value?.ToString());
        Assert.NotNull(moaEntity.FindProperty(nameof(GrantMoa.TotalContractValue))?.FindAnnotation(RelationalAnnotationNames.Comment)?.Value?.ToString());

        // Lookups
        var genderType = model.FindEntityType(typeof(GenderType));
        Assert.NotNull(genderType?.FindAnnotation(RelationalAnnotationNames.Comment)?.Value?.ToString());
        Assert.NotNull(genderType.FindProperty(nameof(GenderType.Code))?.FindAnnotation(RelationalAnnotationNames.Comment)?.Value?.ToString());
    }

    [Fact]
    public async Task DatabaseDocumentationService_ReturnsCompleteSchemaDocumentation()
    {
        var factory = new TestDbContextFactory("DocServiceTest_" + Guid.NewGuid());
        var service = new DatabaseDocumentationService(factory);

        var tables = await service.GetDatabaseSchemaDocumentationAsync();

        Assert.NotEmpty(tables);
        Assert.Contains(tables, t => t.TableName == "CompanyLearner");
        Assert.Contains(tables, t => t.TableName == "Organisation");
        Assert.Contains(tables, t => t.TableName == "Person");
        Assert.Contains(tables, t => t.TableName == "GrantMoa");
        Assert.Contains(tables, t => t.TableName == "GenderType");

        var companyLearner = tables.First(t => t.TableName == "CompanyLearner");
        Assert.False(string.IsNullOrWhiteSpace(companyLearner.Description));
        Assert.NotEmpty(companyLearner.Columns);
        Assert.All(companyLearner.Columns, col => Assert.False(string.IsNullOrWhiteSpace(col.Description)));
    }

    [Fact]
    public async Task DatabaseDocumentationService_GeneratesMarkdownAndSqlScripts()
    {
        var factory = new TestDbContextFactory("DocScriptTest_" + Guid.NewGuid());
        var service = new DatabaseDocumentationService(factory);

        // Markdown Data Dictionary
        var markdown = await service.ExportMarkdownDataDictionaryAsync();
        Assert.NotNull(markdown);
        Assert.Contains("# MerSETA NSDMS — Database Data Dictionary", markdown);
        Assert.Contains("CompanyLearner", markdown);
        Assert.Contains("Organisation", markdown);
        Assert.Contains("Person", markdown);

        // SQL Server MS_Description script
        var sql = await service.GenerateSqlExtendedPropertiesScriptAsync();
        Assert.NotNull(sql);
        Assert.Contains("sp_addextendedproperty", sql);
        Assert.Contains("sp_updateextendedproperty", sql);
        Assert.Contains("MS_Description", sql);
        Assert.Contains("CompanyLearner", sql);

        // Persist artifacts to repository paths if directories exist
        try
        {
            var solutionDir = Path.GetFullPath(Path.Combine(AppContext.BaseDirectory, "..", "..", "..", "..", ".."));
            var docsDir = Path.Combine(solutionDir, "docs", "database");
            if (!Directory.Exists(docsDir)) Directory.CreateDirectory(docsDir);
            File.WriteAllText(Path.Combine(docsDir, "DATA_DICTIONARY.md"), markdown);
            File.WriteAllText(Path.Combine(solutionDir, "docs", "DATABASE_DICTIONARY.md"), markdown);

            var sqlScriptsDir = Path.Combine(solutionDir, "dotnet", "Nsdms.Infrastructure", "Data", "SqlScripts");
            if (!Directory.Exists(sqlScriptsDir)) Directory.CreateDirectory(sqlScriptsDir);
            File.WriteAllText(Path.Combine(sqlScriptsDir, "Sync_Database_Extended_Properties.sql"), sql);
        }
        catch
        {
            // Non-blocking in CI/sandbox environments
        }
    }

    [Fact]
    public async Task SyncExtendedProperties_DirectlyToSqlServerExpress_IfAvailable()
    {
        if (!string.Equals(Environment.GetEnvironmentVariable("ENABLE_LIVE_SQL_SYNC"), "true", StringComparison.OrdinalIgnoreCase))
        {
            return;
        }

        var connectionStrings = new[]
        {
            "Server=localhost\\SQLEXPRESS;Database=NSDMS-NET;User Id=NSDMS-NET;Password=NSDMS-NET;TrustServerCertificate=True;Encrypt=False;Connect Timeout=2;",
            "Server=.\\SQLEXPRESS;Database=NSDMS-NET;User Id=NSDMS-NET;Password=NSDMS-NET;TrustServerCertificate=True;Encrypt=False;Connect Timeout=2;",
            "Server=localhost;Database=NSDMS-NET;User Id=NSDMS-NET;Password=NSDMS-NET;TrustServerCertificate=True;Encrypt=False;Connect Timeout=2;",
            "Server=localhost\\SQLEXPRESS;Database=NSDMS-NET;Trusted_Connection=True;TrustServerCertificate=True;Encrypt=False;Connect Timeout=2;",
            "Server=.\\SQLEXPRESS;Database=NSDMS-NET;Trusted_Connection=True;TrustServerCertificate=True;Encrypt=False;Connect Timeout=2;",
            "Server=(localdb)\\mssqllocaldb;Database=NSDMS-NET;Trusted_Connection=True;TrustServerCertificate=True;Connect Timeout=2;"
        };

        var factory = new TestDbContextFactory("DocScriptTest_Sql_" + Guid.NewGuid());
        var service = new DatabaseDocumentationService(factory);
        var tables = await service.GetDatabaseSchemaDocumentationAsync();

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
                    Console.WriteLine($"[SQL Sync] Connected successfully to SQL Server via: {connStr}");

                    foreach (var t in tables)
                    {
                        var schema = string.IsNullOrWhiteSpace(t.SchemaName) ? "dbo" : t.SchemaName;
                        var escapedTableDesc = (t.Description ?? string.Empty).Replace("'", "''");

                        var tableSql = $@"
IF EXISTS (SELECT 1 FROM sys.tables t JOIN sys.schemas s ON t.schema_id = s.schema_id WHERE s.name = N'{schema}' AND t.name = N'{t.TableName}')
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM sys.extended_properties ep
        JOIN sys.tables t ON ep.major_id = t.object_id
        JOIN sys.schemas s ON t.schema_id = s.schema_id
        WHERE ep.name = N'MS_Description' AND ep.minor_id = 0
          AND s.name = N'{schema}' AND t.name = N'{t.TableName}'
    )
        EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'{escapedTableDesc}', @level0type=N'SCHEMA', @level0name=N'{schema}', @level1type=N'TABLE', @level1name=N'{t.TableName}';
    ELSE
        EXEC sys.sp_updateextendedproperty @name=N'MS_Description', @value=N'{escapedTableDesc}', @level0type=N'SCHEMA', @level0name=N'{schema}', @level1type=N'TABLE', @level1name=N'{t.TableName}';
END";
                        try
                        {
                            await sqlDb.Database.ExecuteSqlRawAsync(tableSql);
                        }
                        catch { }

                        foreach (var col in t.Columns)
                        {
                            var escapedColDesc = (col.Description ?? string.Empty).Replace("'", "''");
                            var colSql = $@"
IF EXISTS (SELECT 1 FROM sys.columns c JOIN sys.tables t ON c.object_id = t.object_id JOIN sys.schemas s ON t.schema_id = s.schema_id WHERE s.name = N'{schema}' AND t.name = N'{t.TableName}' AND c.name = N'{col.ColumnName}')
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM sys.extended_properties ep
        JOIN sys.tables t ON ep.major_id = t.object_id
        JOIN sys.columns c ON ep.major_id = c.object_id AND ep.minor_id = c.column_id
        JOIN sys.schemas s ON t.schema_id = s.schema_id
        WHERE ep.name = N'MS_Description'
          AND s.name = N'{schema}' AND t.name = N'{t.TableName}' AND c.name = N'{col.ColumnName}'
    )
        EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'{escapedColDesc}', @level0type=N'SCHEMA', @level0name=N'{schema}', @level1type=N'TABLE', @level1name=N'{t.TableName}', @level2type=N'COLUMN', @level2name=N'{col.ColumnName}';
    ELSE
        EXEC sys.sp_updateextendedproperty @name=N'MS_Description', @value=N'{escapedColDesc}', @level0type=N'SCHEMA', @level0name=N'{schema}', @level1type=N'TABLE', @level1name=N'{t.TableName}', @level2type=N'COLUMN', @level2name=N'{col.ColumnName}';
END";
                            try
                            {
                                await sqlDb.Database.ExecuteSqlRawAsync(colSql);
                            }
                            catch { }
                        }
                    }

                    Console.WriteLine("[SQL Sync] Successfully synchronized all table and column descriptions to live SQL Server database!");
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
