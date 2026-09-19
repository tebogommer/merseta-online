using System;
using Nsdms.Domain.Entities;
using QuestPDF.Fluent;
using QuestPDF.Helpers;
using QuestPDF.Infrastructure;

namespace Nsdms.Infrastructure.Services.DocumentCompilers;

/// <summary>
/// Specialized QuestPDF compiler for Artisan Trade Test certificates.
/// </summary>
public class TradeTestCertificateCompiler : IDocumentCompiler<LearnerTradeTest>
{
    public string DocumentType => "TRADE_TEST_CERTIFICATE";

    public byte[] Compile(LearnerTradeTest tradeTest)
    {
        var certNum = tradeTest.SerialCertificateNumber ?? $"TT-{tradeTest.Id:D6}";

        var document = Document.Create(container =>
        {
            container.Page(page =>
            {
                page.Size(PageSizes.A4.Landscape());
                page.Margin(2, Unit.Centimetre);
                page.PageColor(Colors.White);
                page.DefaultTextStyle(x => x.FontSize(11).FontFamily("Arial"));

                page.Header().Column(col =>
                {
                    col.Item().AlignCenter().Text("REPUBLIC OF SOUTH AFRICA").Bold().FontSize(16).FontColor(Colors.Grey.Darken3);
                    col.Item().AlignCenter().Text("Manufacturing, Engineering and Related Services SETA (merSETA)").FontSize(13).FontColor(Colors.Blue.Darken3);
                    col.Item().AlignCenter().Text("NATIONAL ARTISAN TRADES ASSESSMENT CERTIFICATE").Bold().FontSize(18).FontColor(Colors.Black);
                    col.Item().PaddingTop(5).LineHorizontal(2).LineColor(Colors.Blue.Darken2);
                });

                page.Content().PaddingVertical(20).Column(col =>
                {
                    col.Item().AlignCenter().Text($"Certificate Reference: {certNum}").FontSize(10).FontColor(Colors.Grey.Darken2);
                    col.Item().PaddingTop(15).Text(t =>
                    {
                        t.Span("This is to certify that candidate has been evaluated in accordance with the statutory requirements of the Skills Development Act and found ");
                        t.Span("COMPETENT").Bold().FontColor(Colors.Green.Darken3);
                        t.Span(" in the designated trade:");
                    });

                    col.Item().PaddingTop(15).AlignCenter().Text(tradeTest.TradeTitle ?? "Designated Trade").Bold().FontSize(16);
                    col.Item().AlignCenter().Text($"OFO Trade Code: {tradeTest.TradeCode ?? "N/A"}").FontSize(11).FontColor(Colors.Grey.Darken2);

                    col.Item().PaddingTop(25).Row(row =>
                    {
                        row.RelativeItem().Column(c =>
                        {
                            c.Item().Text("Assessment Centre:").Bold();
                            c.Item().Text(tradeTest.TestCenterName ?? "Accredited Centre");
                        });

                        row.RelativeItem().Column(c =>
                        {
                            c.Item().Text("Assessment Date:").Bold();
                            c.Item().Text(tradeTest.TradeTestDate.ToString("yyyy-MM-dd"));
                        });

                        row.RelativeItem().Column(c =>
                        {
                            c.Item().Text("Attempt Index:").Bold();
                            c.Item().Text($"Attempt #{tradeTest.TradeTestNumber}");
                        });
                    });
                });

                page.Footer().Column(col =>
                {
                    col.Item().LineHorizontal(1).LineColor(Colors.Grey.Lighten1);
                    col.Item().PaddingTop(5).Row(r =>
                    {
                        r.RelativeItem().Text("Official merSETA Outcome Document | Verified via NSDMS Portal").FontSize(9).FontColor(Colors.Grey.Darken1);
                        r.RelativeItem().AlignRight().Text($"Issued: {DateTime.UtcNow:yyyy-MM-dd}").FontSize(9).FontColor(Colors.Grey.Darken1);
                    });
                });
            });
        });

        return document.GeneratePdf();
    }
}
