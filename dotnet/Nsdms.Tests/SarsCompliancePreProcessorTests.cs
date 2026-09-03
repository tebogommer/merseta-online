using System;
using System.IO;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

public class SarsCompliancePreProcessorTests
{
    private static (TestDbContextFactory Factory, IAuditService Audit, ILevyService LevyService, ISarsCompliancePreProcessor PreProcessor) CreateServices()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var audit = new AuditService(factory);
        var levyService = new LevyService(factory, audit);
        var preProcessor = new SarsCompliancePreProcessor(factory, levyService);
        return (factory, audit, levyService, preProcessor);
    }

    [Fact]
    public async Task ValidateContentAsync_ValidFile_ReturnsCompliantWithDigitalSecuritySealAndNoErrors()
    {
        // Arrange
        var (_, _, _, preProcessor) = CreateServices();

        var validContent =
            "# MerSETA Official Monthly SDL Schedule\r\n" +
            "L102938475|2026|150000.00|30000.00|74250.00|15750.00|0.00|0.00|150000.00|35100\r\n" +
            "L293847561|2026|250000.00|50000.00|123750.00|26250.00|0.00|0.00|250000.00|35200\r\n" +
            "TRAILER|2|400000.00";

        // Act
        var report = await preProcessor.ValidateContentAsync(validContent, "SARS_2026_VALID.dat");

        // Assert
        Assert.True(report.IsCompliant, $"Expected report to be compliant, but had errors: {string.Join(", ", report.Errors.Select(e => e.Description))}");
        Assert.Equal(0, report.ErrorCount);
        Assert.Equal(2, report.ValidDataRowCount);
        Assert.Equal(400000.00m, report.TotalLevyAmount);
        Assert.True(report.HasTrailer);
        Assert.True(report.IsTrailerReconciled);
        Assert.False(string.IsNullOrWhiteSpace(report.DigitalSecuritySeal));
        Assert.Equal(64, report.DigitalSecuritySeal.Length);
    }

    [Fact]
    public async Task ValidateContentAsync_InvalidSdlFormat_ReturnsNonCompliantAndIdentifiesOffendingLine()
    {
        // Arrange
        var (_, _, _, preProcessor) = CreateServices();

        // Line 2 has invalid SDL "INVALID_SDL" (not L + 9 digits)
        var invalidContent =
            "L102938475|2026|100000.00\r\n" +
            "INVALID_SDL|2026|50000.00\r\n" +
            "L384756192|2026|20000.00\r\n" +
            "TRAILER|3|170000.00";

        // Act
        var report = await preProcessor.ValidateContentAsync(invalidContent, "SARS_INVALID_SDL.dat");

        // Assert
        Assert.False(report.IsCompliant);
        Assert.NotEmpty(report.Errors);

        var sdlError = report.Errors.FirstOrDefault(e => e.IssueCode == "InvalidSdlFormat");
        Assert.NotNull(sdlError);
        Assert.Equal(2, sdlError.LineNumber);
        Assert.Equal("INVALID_SDL", sdlError.SdlNumber);
        Assert.Contains("Must start with 'L' followed by exactly 9 digits", sdlError.Description);
    }

    [Fact]
    public async Task ValidateContentAsync_InvalidSicCode_ReturnsNonCompliant()
    {
        // Arrange
        var (_, _, _, preProcessor) = CreateServices();

        // 4-column format with invalid 2-digit SIC code "99"
        var invalidContent =
            "L102938475|2026|99|150000.00\r\n" +
            "TRAILER|1|150000.00";

        // Act
        var report = await preProcessor.ValidateContentAsync(invalidContent, "SARS_INVALID_SIC.dat");

        // Assert
        Assert.False(report.IsCompliant);
        var sicError = report.Errors.FirstOrDefault(e => e.IssueCode == "InvalidSicCode");
        Assert.NotNull(sicError);
        Assert.Equal(1, sicError.LineNumber);
        Assert.Contains("Invalid 5-digit SIC code", sicError.Description);
    }

    [Fact]
    public async Task ValidateContentAsync_TrailerCountMismatch_ReturnsNonCompliantWithTruncationFlag()
    {
        // Arrange
        var (_, _, _, preProcessor) = CreateServices();

        // File has 2 rows, but trailer declared 5 rows
        var truncatedContent =
            "L102938475|2026|100000.00\r\n" +
            "L293847561|2026|200000.00\r\n" +
            "TRAILER|5|300000.00";

        // Act
        var report = await preProcessor.ValidateContentAsync(truncatedContent, "SARS_TRUNCATED.dat");

        // Assert
        Assert.False(report.IsCompliant);
        var countError = report.Errors.FirstOrDefault(e => e.IssueCode == "TrailerCountMismatch");
        Assert.NotNull(countError);
        Assert.Contains("declared 5 records, but file contains 2 parsed data lines", countError.Description);
    }

    [Fact]
    public async Task ValidateContentAsync_TrailerAmountMismatch_ReturnsNonCompliantWithVariance()
    {
        // Arrange
        var (_, _, _, preProcessor) = CreateServices();

        // Sum is 300,000, but trailer says 999,999.99
        var amountMismatchContent =
            "L102938475|2026|100000.00\r\n" +
            "L293847561|2026|200000.00\r\n" +
            "TRAILER|2|999999.99";

        // Act
        var report = await preProcessor.ValidateContentAsync(amountMismatchContent, "SARS_AMOUNT_MISMATCH.dat");

        // Assert
        Assert.False(report.IsCompliant);
        var amountError = report.Errors.FirstOrDefault(e => e.IssueCode == "TrailerAmountMismatch");
        Assert.NotNull(amountError);
        Assert.Contains("Variance: R699,999.99", amountError.Description);
    }

    [Fact]
    public async Task ValidateContentAsync_DuplicateDigitalSecuritySeal_BlocksReImportWithAuditCode()
    {
        // Arrange
        var (contextFactory, _, _, preProcessor) = CreateServices();

        var content =
            "L102938475|2026|100000.00\r\n" +
            "TRAILER|1|100000.00";

        // Calculate hash of content
        var bytes = Encoding.UTF8.GetBytes(content);
        using var sha = SHA256.Create();
        var seal = BitConverter.ToString(sha.ComputeHash(bytes)).Replace("-", "").ToLowerInvariant();

        // Seed an existing imported batch with this seal
        using (var db = await contextFactory.CreateDbContextAsync())
        {
            db.LevyFiles.Add(new LevyFile
            {
                FileName = "SARS_HISTORICAL.dat",
                FileRef = "SARS-2026-PREV",
                DigitalSecuritySeal = seal,
                ImportStatusCode = "Imported",
                TotalRecords = 1,
                TotalAmount = 100000.00m
            });
            await db.SaveChangesAsync();
        }

        // Act
        var report = await preProcessor.ValidateContentAsync(content, "SARS_DUPLICATE.dat");

        // Assert
        Assert.False(report.IsCompliant);
        var dupError = report.Errors.FirstOrDefault(e => e.IssueCode == "DuplicateBatch");
        Assert.NotNull(dupError);
        Assert.Contains("identical Digital Security Seal", dupError.Description);
    }

    [Fact]
    public async Task ValidateContentAsync_EmptyFile_ReturnsNonCompliantEmptyFileIssue()
    {
        // Arrange
        var (_, _, _, preProcessor) = CreateServices();

        // Act
        var report = await preProcessor.ValidateContentAsync("", "EMPTY.dat");

        // Assert
        Assert.False(report.IsCompliant);
        Assert.Contains(report.Errors, e => e.IssueCode == "EmptyFile");
    }

    [Fact]
    public async Task StreamingPipeline_NonCompliantFile_ThrowsSarsComplianceExceptionAndCreatesZeroStagingOrLedgerRows()
    {
        // Arrange
        var (contextFactory, audit, levyService, preProcessor) = CreateServices();
        var stagingWriter = new SarsBulkStagingWriter(contextFactory);
        var pipeline = new SarsLevyStreamingPipeline(contextFactory, stagingWriter, levyService, audit, preProcessor);

        // File has an invalid SDL number
        var corruptFileContent =
            "L102938475|2026|100000.00\r\n" +
            "BAD_SDL_CODE|2026|50000.00\r\n" +
            "TRAILER|2|150000.00";

        using var stream = new MemoryStream(Encoding.UTF8.GetBytes(corruptFileContent));

        // Act & Assert
        var ex = await Assert.ThrowsAsync<SarsComplianceException>(() =>
            pipeline.ProcessSarsStreamAsync(stream, "SARS_CORRUPT.dat", "Admin"));

        Assert.False(ex.Report.IsCompliant);
        Assert.Contains(ex.Report.Errors, e => e.IssueCode == "InvalidSdlFormat");

        // Verify that ZERO staging records and ZERO LevyFile records were written to the database!
        using var db = await contextFactory.CreateDbContextAsync();
        var stagingCount = await db.SarsLevyStagings.CountAsync();
        var fileCount = await db.LevyFiles.CountAsync();
        var lineCount = await db.LevyFileLines.CountAsync();

        Assert.Equal(0, stagingCount);
        Assert.Equal(0, fileCount);
        Assert.Equal(0, lineCount);
    }
}
