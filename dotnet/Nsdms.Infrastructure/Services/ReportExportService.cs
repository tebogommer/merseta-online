using System.Text;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Services;
using QuestPDF.Fluent;
using QuestPDF.Helpers;
using QuestPDF.Infrastructure;

namespace Nsdms.Infrastructure.Services;

public class ReportExportService : IReportExportService
{
    private readonly INsdmsDbContextFactory _dbFactory;

    public ReportExportService(INsdmsDbContextFactory dbFactory)
    {
        _dbFactory = dbFactory;
        QuestPDF.Settings.License = LicenseType.Community;
    }



    public async Task<byte[]> GenerateGrantMoaAgreementPdfAsync(int moaId)
    {
        await using var db = await _dbFactory.CreateDbContextAsync();
        var moa = await db.GrantMoas
            .Include(m => m.GrantApplication)
            .FirstOrDefaultAsync(m => m.Id == moaId);

        var doc = Document.Create(container =>
        {
            container.Page(page =>
            {
                page.Size(PageSizes.A4);
                page.Margin(2, Unit.Centimetre);
                page.Header().Text($"MEMORANDUM OF AGREEMENT - {moa?.MoaNumber}").Bold().FontSize(16).FontColor(Colors.Amber.Darken3);
                page.Content().PaddingVertical(1, Unit.Centimetre).Column(col =>
                {
                    col.Spacing(10);
                    col.Item().Text($"Contract Reference: {moa?.MoaNumber}").Bold();
                    col.Item().Text($"Contract Value: R {moa?.TotalContractValue:N2}").Bold();
                    col.Item().Text($"Status: {moa?.MoaStatusCode}");
                    col.Item().Text($"Project: {moa?.GrantApplication?.ProjectTitle ?? "Skills Development Project"}");
                    col.Item().Text($"Commencement Date: {moa?.ContractStartDate:yyyy-MM-dd}");
                    col.Item().Text($"Termination Date: {moa?.ContractEndDate:yyyy-MM-dd}");
                });
                page.Footer().AlignCenter().Text("Signed on behalf of merSETA and the Grantee");
            });
        });

        return doc.GeneratePdf();
    }

    public async Task<byte[]> GenerateSarsLevyReconCsvAsync(string finYear)
    {
        await using var db = await _dbFactory.CreateDbContextAsync();
        var files = await db.LevyFiles.ToListAsync();

        var sb = new StringBuilder();
        sb.AppendLine("LevyFileId,FileRef,FileName,ImportDate,TotalRecords,TotalAmount,Status");
        foreach (var f in files)
        {
            sb.AppendLine(System.Globalization.CultureInfo.InvariantCulture, $"{f.Id},{f.FileRef},{f.FileName},{f.ImportDate:yyyy-MM-dd},{f.TotalRecords},{f.TotalAmount:F2},{f.ImportStatusCode}");
        }

        return Encoding.UTF8.GetBytes(sb.ToString());
    }
}
