using System;
using Nsdms.Domain.Entities;
using QuestPDF.Fluent;
using QuestPDF.Helpers;
using QuestPDF.Infrastructure;

namespace Nsdms.Infrastructure.Services.DocumentCompilers;

/// <summary>
/// Specialized QuestPDF compiler for Mandatory Grant (WSP / ATR) approval and outcome letters.
/// </summary>
public class WspOutcomeLetterCompiler : IDocumentCompiler<WspSubmission>
{
    public string DocumentType => "WSP_OUTCOME_LETTER";

    public byte[] Compile(WspSubmission wsp)
    {
        var orgName = wsp.Organisation?.LegalName ?? "Employer Organisation";
        var sdl = wsp.Organisation?.SdlNumber ?? "Unknown";

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
                    col.Item().Row(r =>
                    {
                        r.RelativeItem().Column(c =>
                        {
                            c.Item().Text("Manufacturing, Engineering and Related Services SETA").Bold().FontSize(12).FontColor(Colors.Blue.Darken3);
                            c.Item().Text("Skills Development Act 97 of 1998 | Mandatory Grants Directorate").FontSize(9).FontColor(Colors.Grey.Darken2);
                        });
                        r.ConstantItem(120).AlignRight().Text("OFFICIAL NOTICE").Bold().FontColor(Colors.Blue.Darken2);
                    });
                    col.Item().PaddingTop(8).LineHorizontal(1.5f).LineColor(Colors.Blue.Darken2);
                });

                page.Content().PaddingVertical(15).Column(col =>
                {
                    col.Item().Text($"Date: {DateTime.UtcNow:yyyy-MM-dd}").FontSize(10);
                    col.Item().PaddingTop(10).Text($"To: The Skills Development Facilitator / Executive Authority").Bold();
                    col.Item().Text(orgName);
                    col.Item().Text($"Skills Development Levy (SDL) Number: {sdl}");
                    col.Item().Text($"Submission Reference: {wsp.ReferenceNumber ?? $"WSP-{wsp.Id}"}");

                    col.Item().PaddingTop(15).Text($"RE: OUTCOME OF MANDATORY GRANT (WSP/ATR) SUBMISSION - SCHEME YEAR {wsp.FinYear}").Bold().FontSize(12);

                    col.Item().PaddingTop(10).Text("Dear Stakeholder,");

                    col.Item().PaddingTop(8).Text(t =>
                    {
                        t.Span("We are pleased to inform you that your Workplace Skills Plan (WSP) and Annual Training Report (ATR) submission for the statutory scheme year ");
                        t.Span($"{wsp.FinYear}").Bold();
                        t.Span(" has been evaluated in accordance with the SETA Grant Regulations and officially ");
                        t.Span("APPROVED").Bold().FontColor(Colors.Green.Darken3);
                        t.Span(".");
                    });

                    col.Item().PaddingTop(12).Table(table =>
                    {
                        table.ColumnsDefinition(c =>
                        {
                            c.RelativeColumn();
                            c.RelativeColumn();
                        });

                        table.Cell().Background(Colors.Grey.Lighten3).Padding(5).Text("Submission Attribute").Bold();
                        table.Cell().Background(Colors.Grey.Lighten3).Padding(5).Text("Declared / Approved Value").Bold();

                        table.Cell().BorderBottom(1).BorderColor(Colors.Grey.Lighten2).Padding(5).Text("Scheme Financial Year");
                        table.Cell().BorderBottom(1).BorderColor(Colors.Grey.Lighten2).Padding(5).Text(wsp.FinYear.ToString());

                        table.Cell().BorderBottom(1).BorderColor(Colors.Grey.Lighten2).Padding(5).Text("Total Workforce Declared");
                        table.Cell().BorderBottom(1).BorderColor(Colors.Grey.Lighten2).Padding(5).Text($"{wsp.EmployeeCount:N0} Employees");

                        table.Cell().BorderBottom(1).BorderColor(Colors.Grey.Lighten2).Padding(5).Text("Planned Training Budget");
                        table.Cell().BorderBottom(1).BorderColor(Colors.Grey.Lighten2).Padding(5).Text($"R {wsp.PlannedTrainingBudget:N2}");

                        table.Cell().BorderBottom(1).BorderColor(Colors.Grey.Lighten2).Padding(5).Text("Signoff Quorum Status");
                        table.Cell().BorderBottom(1).BorderColor(Colors.Grey.Lighten2).Padding(5).Text(wsp.IsSignoffQuorumMet ? "Constitutional Quorum Satisfied" : "Pending Signoff");
                    });

                    col.Item().PaddingTop(15).Text("Mandatory Grant disbursements (20% levy rebate) will be processed in accordance with reconciled SARS Skills Development Levy receipts into your verified banking profile.");

                    col.Item().PaddingTop(25).Text("Yours faithfully,");
                    col.Item().Text("Client Liaison & Mandatory Grants Division").Bold();
                    col.Item().Text("Manufacturing, Engineering and Related Services SETA (merSETA)");
                });

                page.Footer().Column(col =>
                {
                    col.Item().LineHorizontal(1).LineColor(Colors.Grey.Lighten1);
                    col.Item().PaddingTop(5).Row(r =>
                    {
                        r.RelativeItem().Text("merSETA NSDMS Statutory Document | Digitally Sealed & Audited").FontSize(8).FontColor(Colors.Grey.Darken1);
                        r.RelativeItem().AlignRight().Text($"Generated: {DateTime.UtcNow:yyyy-MM-dd HH:mm} UTC").FontSize(8).FontColor(Colors.Grey.Darken1);
                    });
                });
            });
        });

        return document.GeneratePdf();
    }
}
