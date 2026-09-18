using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging.Abstractions;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Services;
using Nsdms.Web.Components.Pages.Admin;
using Nsdms.Web.Components.Pages.Finance;
using Xunit;

namespace Nsdms.Tests;

public class SecurityRemediationTests
{
    [Theory]
    [InlineData("DROP TABLE Organisation;")]
    [InlineData("DROP TABLE AttestedComputation")]
    [InlineData("ALTER TABLE Users ADD IsHacked INT")]
    [InlineData("DELETE FROM ConceptDocuments")]
    [InlineData("UPDATE Organisation SET LegalName = 'Hacked'")]
    [InlineData("INSERT INTO ApplicationRole (Name) VALUES ('Admin')")]
    [InlineData("TRUNCATE TABLE AuditLog")]
    [InlineData("EXEC xp_cmdshell 'whoami'")]
    [InlineData("EXECUTE sp_executesql N'SELECT 1'")]
    [InlineData("GRANT CONTROL SERVER TO public")]
    [InlineData("SELECT * FROM Organisation; DROP TABLE Users")]
    public async Task TsqlAttestationEngine_DestructiveQuery_ThrowsSecurityViolation(string maliciousSql)
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();

        var concept = new ConceptDocument
        {
            ConceptId = "SEC-TEST-" + Guid.NewGuid().ToString("N"),
            Title = "Security Test",
            IsActive = true
        };
        db.ConceptDocuments.Add(concept);
        await db.SaveChangesAsync();

        var comp = new AttestedComputation
        {
            ConceptId = concept.Id,
            ComputationSql = maliciousSql
        };
        db.AttestedComputations.Add(comp);
        await db.SaveChangesAsync();

        var engine = new TsqlAttestationEngine(factory, NullLogger<TsqlAttestationEngine>.Instance);

        // Act & Assert
        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() =>
            engine.ExecuteAndAttestAsync(comp.Id, new Dictionary<string, object?>(), "auditor"));

        Assert.Contains("Security Violation", ex.Message);
    }

    [Theory]
    [InlineData("SELECT TOP 10 Id, LegalName FROM Organisation")]
    [InlineData("WITH CTE AS (SELECT Id FROM Organisation) SELECT * FROM CTE")]
    public async Task TsqlAttestationEngine_ValidReadOnlyQuery_PassesSecurityValidation(string validSql)
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();

        var concept = new ConceptDocument
        {
            ConceptId = "SEC-TEST-" + Guid.NewGuid().ToString("N"),
            Title = "Security Test Valid",
            IsActive = true
        };
        db.ConceptDocuments.Add(concept);
        await db.SaveChangesAsync();

        var comp = new AttestedComputation
        {
            ConceptId = concept.Id,
            ComputationSql = validSql
        };
        db.AttestedComputations.Add(comp);
        await db.SaveChangesAsync();

        var engine = new TsqlAttestationEngine(factory, NullLogger<TsqlAttestationEngine>.Instance);

        // Act
        var result = await engine.ExecuteAndAttestAsync(comp.Id, new Dictionary<string, object?>(), "auditor");

        // Assert
        Assert.NotNull(result);
    }

    [Theory]
    [InlineData("../parent_escape")]
    [InlineData("..\\windows_escape")]
    [InlineData("org/nested/path")]
    [InlineData("org\\nested\\path")]
    [InlineData("org with spaces")]
    [InlineData("org;drop")]
    public async Task LocalFileStorage_PathTraversalTargetEntity_ThrowsArgumentException(string maliciousEntityName)
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var audit = new AuditService(factory);
        var conf = new ConfigurationBuilder().AddInMemoryCollection(new Dictionary<string, string?> { { "Storage.UploadDirectory", "test_uploads" } }).Build();
        var config = new SystemConfigurationService(factory, conf, audit);

        var storage = new LocalFileStorageService(factory, config, audit);
        using var stream = new MemoryStream(new byte[] { 0x25, 0x50, 0x44, 0x46 }); // %PDF

        // Act & Assert
        await Assert.ThrowsAsync<ArgumentException>(() =>
            storage.SaveFileAsync(maliciousEntityName, 1, "test.pdf", "application/pdf", stream));
    }

    [Theory]
    [InlineData("trojan.exe")]
    [InlineData("script.bat")]
    [InlineData("shell.sh")]
    [InlineData("backdoor.aspx")]
    [InlineData("payload.dll")]
    public async Task LocalFileStorage_DisallowedExtension_ThrowsInvalidOperationException(string maliciousFileName)
    {
        // Arrange
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var audit = new AuditService(factory);
        var conf = new ConfigurationBuilder().AddInMemoryCollection(new Dictionary<string, string?> { { "Storage.UploadDirectory", "test_uploads" } }).Build();
        var config = new SystemConfigurationService(factory, conf, audit);

        var storage = new LocalFileStorageService(factory, config, audit);
        using var stream = new MemoryStream(new byte[] { 0x4D, 0x5A });

        // Act & Assert
        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() =>
            storage.SaveFileAsync("organisation", 1, maliciousFileName, "application/octet-stream", stream));

        Assert.Contains("extension is not allowed", ex.Message, StringComparison.OrdinalIgnoreCase);
    }

    [Theory]
    [InlineData("//evil.com", false)]
    [InlineData("/\\evil.com", false)]
    [InlineData("http://evil.com", false)]
    [InlineData("https://evil.com", false)]
    [InlineData("javascript:alert(1)", false)]
    [InlineData("", false)]
    [InlineData(null, false)]
    [InlineData("/dashboard", true)]
    [InlineData("/employers/12", true)]
    [InlineData("/wsp/wsp-submissions", true)]
    public void OpenRedirect_ValidationLogic_RejectsProtocolRelativeAndExternalUrls(string? returnUrl, bool expectedValid)
    {
        bool isValid = !string.IsNullOrWhiteSpace(returnUrl) &&
                       returnUrl.StartsWith('/') &&
                       !returnUrl.StartsWith("//") &&
                       !returnUrl.StartsWith("/\\");

        Assert.Equal(expectedValid, isValid);
    }

    [Fact]
    public void AdministrativeAndFinancialPages_MustHaveExplicitRoleAuthorizationAttributes()
    {
        var requiredRolePages = new[]
        {
            typeof(RoleList),
            typeof(RoleDetail),
            typeof(SystemSettings),
            typeof(AdminHub),
            typeof(LookupManager),
            typeof(WorkflowDefinitionList),
            typeof(WorkflowDefinitionDetail),
            typeof(DocumentTemplateList),
            typeof(DocumentTemplateDetail),
            typeof(BankingDetailsList),
            typeof(BankingDetailsDetail)
        };

        foreach (var pageType in requiredRolePages)
        {
            var authAttrs = pageType.GetCustomAttributes<AuthorizeAttribute>().ToList();
            Assert.NotEmpty(authAttrs);
            Assert.True(authAttrs.Any(a => !string.IsNullOrWhiteSpace(a.Roles)), $"Page {pageType.Name} is missing explicit Roles restriction!");
        }
    }
}
