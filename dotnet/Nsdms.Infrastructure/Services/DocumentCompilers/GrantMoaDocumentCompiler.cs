using System;
using Nsdms.Domain.Entities;
using QuestPDF.Fluent;
using QuestPDF.Helpers;
using QuestPDF.Infrastructure;

namespace Nsdms.Infrastructure.Services.DocumentCompilers;

/// <summary>
/// Specialized QuestPDF compiler for Discretionary Grant Memorandum of Agreement (MoA) contracts.
/// </summary>
public class GrantMoaDocumentCompiler : IDocumentCompiler<GrantMoa>
{
    public string DocumentType => "GRANT_MOA_CONTRACT";

    public byte[] Compile(GrantMoa moa)
    {
        var moaRef = moa.MoaNumber ?? $"MOA-{moa.Id:D5}";
        var orgName = moa.GrantApplication?.Organisation?.LegalName ?? "Applicant Organisation";
        var amount = moa.TotalContractValue;

        var document = Document.Create(container =>
        {
            container.Page(page =>
            {
                page.Size(PageSizes.A4);
                page.Margin(2, Unit.Centimetre);
                page.PageColor(Colors.White);
                page.DefaultTextStyle(x => x.FontSize(10).FontFamily("Arial"));

                page.Header().Column(col =>
                {
                    col.Item().AlignCenter().Text("MEMORANDUM OF AGREEMENT (MoA)").Bold().FontSize(15).FontColor(Colors.Blue.Darken3);
                    col.Item().AlignCenter().Text("DISCRETIONARY GRANT ALLOCATION & SKILLS DEVELOPMENT FUNDING").FontSize(11).FontColor(Colors.Grey.Darken3);
                    col.Item().AlignCenter().Text($"Contract Reference: {moaRef}").Bold().FontSize(10);
                    col.Item().PaddingTop(5).LineHorizontal(1.5f).LineColor(Colors.Blue.Darken2);
                });

                page.Content().PaddingVertical(15).Column(col =>
                {
                    col.Item().Text("BETWEEN:").Bold().Underline();
                    col.Item().Text("Manufacturing, Engineering and Related Services SETA (merSETA)");
                    col.Item().Text("AND:").Bold().Underline();
                    col.Item().Text(orgName);

                    col.Item().PaddingTop(15).Text("1. ALLOCATION & FUNDING CEILING").Bold();
                    col.Item().Text($"The merSETA hereby contracts Discretionary Grant funding up to a maximum ceiling of R {amount:N2} in accordance with the gazetted DG Policy and approved Project Implementation Plan.");

                    col.Item().PaddingTop(15).Text("2. CONTRACT TENURE & STATUTORY MILESTONES").Bold();
                    col.Item().Text($"Commencement Date: {moa.ContractStartDate:yyyy-MM-dd}");
                    col.Item().Text($"Projected Completion Date: {moa.ContractEndDate:yyyy-MM-dd}");
                    col.Item().Text($"Current Contract Status: {moa.MoaStatusCode ?? "Active"}");

                    col.Item().PaddingTop(25).Table(table =>
                    {
                        table.ColumnsDefinition(c =>
                        {
                            c.RelativeColumn();
                            c.ConstantColumn(40);
                            c.RelativeColumn();
                        });

                        table.Cell().Column(c =>
                        {
                            c.Item().Text("Signed on behalf of merSETA:").Bold();
                            c.Item().PaddingTop(20).LineHorizontal(1).LineColor(Colors.Black);
                            c.Item().Text("Chief Executive Officer / Delegated Authority");
                        });

                        table.Cell().Text(string.Empty);

                        table.Cell().Column(c =>
                        {
                            c.Item().Text("Signed on behalf of Employer / SDP:").Bold();
                            c.Item().PaddingTop(20).LineHorizontal(1).LineColor(Colors.Black);
                            c.Item().Text("Authorised Executive Signatory");
                        });
                    });
                });

                page.Footer().Column(col =>
                {
                    col.Item().LineHorizontal(1).LineColor(Colors.Grey.Lighten1);
                    col.Item().PaddingTop(5).Row(r =>
                    {
                        r.RelativeItem().Text("Official Legal Instrument | Enforceable under the Skills Development Act 97 of 1998").FontSize(8).FontColor(Colors.Grey.Darken1);
                        r.RelativeItem().AlignRight().Text($"Page 1 of 1").FontSize(8).FontColor(Colors.Grey.Darken1);
                    });
                });
            });
        });

        return document.GeneratePdf();
    }
}
