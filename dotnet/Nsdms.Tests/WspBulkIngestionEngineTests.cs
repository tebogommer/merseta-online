using System.Text;
using ClosedXML.Excel;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using Moq;
using Nsdms.Application.Common;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Domain.Lookups;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

public class WspBulkIngestionEngineTests
{
    [Fact]
    public async Task FileFormatSniffer_DetectsExcelFilesCorrectly()
    {
        using var ms = new MemoryStream();
        var (encoding, delimiter, isExcel) = await FileFormatSniffer.SniffFilePropertiesAsync(ms, "wsp_template.xlsx");
        Assert.True(isExcel);
        Assert.Equal('\0', delimiter);
    }

    [Theory]
    [InlineData("OFO,ID,Programme,Cost\n653301,8904125008082,Apprenticeship,45000", ',')]
    [InlineData("OFO;ID;Programme;Cost\n653301;8904125008082;Apprenticeship;45000", ';')]
    [InlineData("OFO\tID\tProgramme\tCost\n653301\t8904125008082\tApprenticeship\t45000", '\t')]
    [InlineData("OFO|ID|Programme|Cost\n653301|8904125008082|Apprenticeship|45000", '|')]
    public async Task FileFormatSniffer_DetectsDelimitersCorrectly(string content, char expectedDelimiter)
    {
        var bytes = Encoding.UTF8.GetBytes(content);
        using var ms = new MemoryStream(bytes);
        var (encoding, delimiter, isExcel) = await FileFormatSniffer.SniffFilePropertiesAsync(ms, "upload.csv");
        Assert.False(isExcel);
        Assert.Equal(expectedDelimiter, delimiter);
    }

    [Fact]
    public async Task GenerateOfficialTemplateExcel_CreatesValidClosedXmlWorkbook()
    {
        var dbOptions = new DbContextOptionsBuilder<NsdmsDbContext>()
            .UseInMemoryDatabase(Guid.NewGuid().ToString())
            .Options;
        var db = new NsdmsDbContext(dbOptions);

        var mockFactory = new Mock<INsdmsDbContextFactory>();
        mockFactory.Setup(f => f.CreateDbContextAsync(default)).ReturnsAsync(() => new NsdmsDbContext(dbOptions));

        var mockAudit = new Mock<IAuditService>();
        var mockLogger = new Mock<ILogger<WspBulkIngestionService>>();

        var service = new WspBulkIngestionService(mockFactory.Object, mockAudit.Object, mockLogger.Object);

        var bytes = await service.GenerateOfficialTemplateExcelAsync(2026);
        Assert.NotNull(bytes);
        Assert.True(bytes.Length > 0);

        using var ms = new MemoryStream(bytes);
        using var wb = new XLWorkbook(ms);

        Assert.True(wb.Worksheets.Contains("WSP_ATR_Intake"));
        Assert.True(wb.Worksheets.Contains("_merSETA_Schema"));

        var ws = wb.Worksheet("WSP_ATR_Intake");
        Assert.Equal("OFO Code", ws.Cell(1, 1).GetString());
        Assert.Equal("RSA ID Number", ws.Cell(1, 2).GetString());
    }

    [Fact]
    public async Task StageAndValidateBatch_DetectsInvalidOfoAndMalformedRsaId()
    {
        var dbOptions = new DbContextOptionsBuilder<NsdmsDbContext>()
            .UseInMemoryDatabase(Guid.NewGuid().ToString())
            .Options;
        var db = new NsdmsDbContext(dbOptions);

        // Seed an organisation and WSP submission
        var org = new Organisation { Id = 1, CompanyName = "Test Automotive Ltd", SdlNumber = "L123456789" };
        var wsp = new WspSubmission { Id = 10, OrganisationId = 1, FinYear = 2026, ReferenceNumber = "WSP-2026-00010" };
        db.Organisations.Add(org);
        db.WspSubmissions.Add(wsp);

        // Seed valid OFO
        db.OfoCodeTypes.Add(new OfoCodeType { Code = "653301", Name = "Diesel Mechanic", Active = true });
        await db.SaveChangesAsync();

        var mockFactory = new Mock<INsdmsDbContextFactory>();
        mockFactory.Setup(f => f.CreateDbContextAsync(default)).ReturnsAsync(() => new NsdmsDbContext(dbOptions));

        var mockAudit = new Mock<IAuditService>();
        var mockLogger = new Mock<ILogger<WspBulkIngestionService>>();

        var service = new WspBulkIngestionService(mockFactory.Object, mockAudit.Object, mockLogger.Object);

        // Row 1: Valid
        // Row 2: Invalid OFO (999999) and invalid RSA ID (short)
        var csvContent = "OfoCode,IdNumber,ProgrammeTypeCode,EstimatedCost,BeneficiaryCount\n" +
                         "653301,8904125008082,Apprenticeship,45000,1\n" +
                         "999999,12345,Learnership,25000,1\n";

        using var ms = new MemoryStream(Encoding.UTF8.GetBytes(csvContent));
        var batch = await service.StageAndValidateBatchAsync(10, "intake.csv", ms, "Admin", allowPartial: true);

        Assert.NotNull(batch);
        Assert.Equal(2, batch.TotalRowCount);
        Assert.Equal(1, batch.ValidRowCount);
        Assert.Equal(1, batch.ErrorRowCount);
        Assert.Equal("ValidationFailed", batch.BatchStatus);

        var exceptions = await service.GetBatchExceptionsAsync(batch.Id);
        Assert.Single(exceptions);
        Assert.Equal(2, exceptions[0].RowIndex);
        Assert.Contains("OFO Code [999999]", exceptions[0].ValidationErrorDetails);
        Assert.Contains("RSA ID [12345]", exceptions[0].ValidationErrorDetails);
    }

    [Fact]
    public async Task GenerateDeltaCorrectionExcel_CreatesWorkbookForExceptionsOnly()
    {
        var dbOptions = new DbContextOptionsBuilder<NsdmsDbContext>()
            .UseInMemoryDatabase(Guid.NewGuid().ToString())
            .Options;
        var db = new NsdmsDbContext(dbOptions);

        var org = new Organisation { Id = 2, CompanyName = "Steel Works Corp", SdlNumber = "L987654321" };
        var wsp = new WspSubmission { Id = 20, OrganisationId = 2, FinYear = 2026, ReferenceNumber = "WSP-2026-00020" };
        db.Organisations.Add(org);
        db.WspSubmissions.Add(wsp);
        await db.SaveChangesAsync();

        var mockFactory = new Mock<INsdmsDbContextFactory>();
        mockFactory.Setup(f => f.CreateDbContextAsync(default)).ReturnsAsync(() => new NsdmsDbContext(dbOptions));

        var mockAudit = new Mock<IAuditService>();
        var mockLogger = new Mock<ILogger<WspBulkIngestionService>>();
        var service = new WspBulkIngestionService(mockFactory.Object, mockAudit.Object, mockLogger.Object);

        var csvContent = "OfoCode,IdNumber,ProgrammeTypeCode,EstimatedCost,BeneficiaryCount\n" +
                         "INVALID_OFO,123,SkillsProgramme,10000,1\n";

        using var ms = new MemoryStream(Encoding.UTF8.GetBytes(csvContent));
        var batch = await service.StageAndValidateBatchAsync(20, "broken.csv", ms, "Admin", allowPartial: true);

        var deltaBytes = await service.GenerateDeltaCorrectionExcelAsync(batch.Id);
        Assert.NotNull(deltaBytes);

        using var deltaMs = new MemoryStream(deltaBytes);
        using var wb = new XLWorkbook(deltaMs);

        Assert.True(wb.Worksheets.Contains("Exceptions To Fix"));
        Assert.True(wb.Worksheets.Contains("OFO Reference Catalog"));

        var ws = wb.Worksheet("Exceptions To Fix");
        Assert.Equal("Line #", ws.Cell(1, 1).GetString());
        Assert.Equal(1, ws.Cell(2, 1).GetValue<int>());
        Assert.Equal("INVALID_OFO", ws.Cell(2, 3).GetString());
    }
}
