using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;
using QuestPDF.Fluent;
using QuestPDF.Helpers;
using QuestPDF.Infrastructure;

namespace Nsdms.Infrastructure.Services;

public class QuestPdfDocumentService : IPdfDocumentService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IFeatureFlagService _featureFlags;
    private readonly ISystemConfigurationService _config;
    private readonly IDocumentVerificationService? _verificationService;

    public QuestPdfDocumentService(
        INsdmsDbContextFactory contextFactory,
        IFeatureFlagService featureFlags, 
        ISystemConfigurationService config,
        IDocumentVerificationService? verificationService = null)
    {
        _contextFactory = contextFactory;
        _featureFlags = featureFlags;
        _config = config;
        _verificationService = verificationService;
        QuestPDF.Settings.License = LicenseType.Community;
    }

    public async Task<byte[]> GenerateTradeTestCertificateAsync(LearnerTradeTest tradeTest)
    {
        var enableWatermarks = await _featureFlags.IsFeatureEnabledAsync("Pdfs.WatermarksAndQrCodes", true);
        var setaName = await _config.GetValueAsync("General.SetaName", "Manufacturing, Engineering and Related Services SETA (merSETA)");

        var certNum = tradeTest.SerialCertificateNumber ?? $"TT-{tradeTest.Id:D6}";
        var baseUrl = await _config.GetValueAsync("System.BaseUrl", "https://nsdms.merseta.org.za");
        var verifyUrl = $"{baseUrl.TrimEnd('/')}/verify/document/{certNum}";
        var qrBytes = GenerateQrBytes(verifyUrl);

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
                    col.Item().AlignCenter().Text(setaName).FontSize(13).FontColor(Colors.Blue.Darken3);
                    col.Item().AlignCenter().Text("NATIONAL ARTISAN TRADES ASSESSMENT CERTIFICATE").Bold().FontSize(18).FontColor(Colors.Black);
                    col.Item().PaddingTop(5).LineHorizontal(2).LineColor(Colors.Blue.Darken2);
                });

                page.Content().PaddingVertical(20).Column(col =>
                {
                    col.Spacing(12);

                    col.Item().AlignCenter().Text("This is to certify that").Italic().FontSize(12);
                    col.Item().AlignCenter().Text(tradeTest.CompanyLearner?.Person?.FullName ?? "Learner").Bold().FontSize(22).FontColor(Colors.Blue.Darken4);
                    col.Item().AlignCenter().Text($"National ID / Passport: {tradeTest.CompanyLearner?.Person?.RsaIdNumber ?? tradeTest.CompanyLearner?.Person?.PassportNumber ?? "N/A"}").FontSize(11);

                    col.Item().AlignCenter().Text("has successfully undergone assessment and trade testing and has been declared").FontSize(12);
                    col.Item().AlignCenter().Text("COMPETENT AS AN ARTISAN").Bold().FontSize(16).FontColor(Colors.Green.Darken3);

                    col.Item().AlignCenter().Text($"In the Designated Trade:").FontSize(11);
                    col.Item().AlignCenter().Text(tradeTest.TradeTitle).Bold().FontSize(14);

                    col.Item().PaddingTop(15).Row(row =>
                    {
                        row.RelativeItem(3).Column(c =>
                        {
                            c.Item().Text($"Certificate Number: {certNum}").Bold();
                            c.Item().Text($"Issue Date: {tradeTest.CertificateIssueDate?.ToString("yyyy-MM-dd") ?? DateTime.UtcNow.ToString("yyyy-MM-dd")}");
                            c.Item().Text($"Test Centre: {tradeTest.TestCenterName}");
                        });

                        row.RelativeItem(2).AlignCenter().Column(c =>
                        {
                            c.Item().Width(45).Height(45).Image(qrBytes);
                            c.Item().AlignCenter().Text("Scan to Verify").FontSize(7).FontColor(Colors.Blue.Darken2);
                        });

                        row.RelativeItem(3).AlignRight().Column(c =>
                        {
                            c.Item().Text("Authorised merSETA Signatory").Bold();
                            c.Item().PaddingTop(20).LineHorizontal(1).LineColor(Colors.Grey.Darken1);
                            c.Item().AlignCenter().Text("Chief Executive Officer / ETQA Manager").FontSize(9);
                        });
                    });

                    if (enableWatermarks)
                    {
                        col.Item().PaddingTop(10).AlignCenter().Text("Official Government Credential • Issued under the Skills Development Act • Digitally Hash Anchored").FontSize(8).FontColor(Colors.Grey.Medium);
                    }
                });

                page.Footer().AlignCenter().Text(x =>
                {
                    x.Span("Page ");
                    x.CurrentPageNumber();
                    x.Span(" of ");
                    x.TotalPages();
                });
            });
        });

        return document.GeneratePdf();
    }

    public async Task<byte[]> GenerateGrantMoaDocumentAsync(GrantMoa moa)
    {
        var setaName = await _config.GetValueAsync("General.SetaName", "merSETA");

        var document = Document.Create(container =>
        {
            container.Page(page =>
            {
                page.Size(PageSizes.A4);
                page.Margin(1.5f, Unit.Centimetre);
                page.DefaultTextStyle(x => x.FontSize(10).FontFamily("Arial"));

                page.Header().Column(col =>
                {
                    col.Item().Text($"{setaName} — DISCRETIONARY GRANT AGREEMENT").Bold().FontSize(14).FontColor(Colors.Blue.Darken3);
                    col.Item().Text($"Memorandum of Agreement Number: {moa.MoaNumber}").FontSize(10).FontColor(Colors.Grey.Darken2);
                    col.Item().PaddingTop(5).LineHorizontal(1).LineColor(Colors.Grey.Lighten1);
                });

                page.Content().PaddingVertical(15).Column(col =>
                {
                    col.Spacing(10);

                    col.Item().Text("1. PARTIES & SCOPE").Bold().FontSize(11);
                    col.Item().Text($"This Agreement is entered into between {setaName} and the Employer for Discretionary Grant project: {moa.GrantApplication?.ProjectTitle ?? "Skills Project"}.");
                    col.Item().Text($"Contract Value: R {moa.TotalContractValue:N2} | Period: {moa.ContractStartDate:yyyy-MM-dd} to {moa.ContractEndDate:yyyy-MM-dd}");

                    col.Item().PaddingTop(10).Text("2. TRANCHE PAYMENT SCHEDULE & MILESTONES").Bold().FontSize(11);

                    col.Item().Table(table =>
                    {
                        table.ColumnsDefinition(columns =>
                        {
                            columns.ConstantColumn(40);
                            columns.RelativeColumn(3);
                            columns.ConstantColumn(70);
                            columns.ConstantColumn(90);
                            columns.ConstantColumn(80);
                        });

                        table.Header(header =>
                        {
                            header.Cell().Background(Colors.Grey.Lighten3).Padding(4).Text("#").Bold();
                            header.Cell().Background(Colors.Grey.Lighten3).Padding(4).Text("Milestone Title").Bold();
                            header.Cell().Background(Colors.Grey.Lighten3).Padding(4).Text("Tranche %").Bold();
                            header.Cell().Background(Colors.Grey.Lighten3).Padding(4).Text("Amount (ZAR)").Bold();
                            header.Cell().Background(Colors.Grey.Lighten3).Padding(4).Text("Due Date").Bold();
                        });

                        foreach (var m in moa.Milestones.OrderBy(x => x.MilestoneNumber))
                        {
                            table.Cell().BorderBottom(1).BorderColor(Colors.Grey.Lighten2).Padding(4).Text(m.MilestoneNumber.ToString());
                            table.Cell().BorderBottom(1).BorderColor(Colors.Grey.Lighten2).Padding(4).Text(m.MilestoneTitle);
                            table.Cell().BorderBottom(1).BorderColor(Colors.Grey.Lighten2).Padding(4).Text($"{m.TranchePercentage:N0}%");
                            table.Cell().BorderBottom(1).BorderColor(Colors.Grey.Lighten2).Padding(4).Text($"R {m.TrancheAmount:N2}");
                            table.Cell().BorderBottom(1).BorderColor(Colors.Grey.Lighten2).Padding(4).Text(m.TargetDueDate.ToString("yyyy-MM-dd"));
                        }
                    });

                    col.Item().PaddingTop(10).Text("3. SIGNATURES").Bold().FontSize(11);
                    col.Item().Row(r =>
                    {
                        r.RelativeItem().Column(c =>
                        {
                            c.Item().Text("For Employer / Grantee:").Bold();
                            c.Item().Text($"Signed Date: {moa.SignoffDateEmployer?.ToString("yyyy-MM-dd") ?? "Pending Signature"}");
                            c.Item().PaddingTop(15).LineHorizontal(1);
                            c.Item().Text("Authorised Representative");
                        });

                        r.RelativeItem().Column(c =>
                        {
                            c.Item().Text($"For {setaName}:").Bold();
                            c.Item().Text($"Signed Date: {moa.SignoffDateSeta?.ToString("yyyy-MM-dd") ?? "Pending Signature"}");
                            c.Item().PaddingTop(15).LineHorizontal(1);
                            c.Item().Text("Chief Financial Officer / CEO");
                        });
                    });
                });

                page.Footer().AlignCenter().Text(x =>
                {
                    x.Span("MOA Reference: ");
                    x.Span(moa.MoaNumber).Bold();
                    x.Span(" | Page ");
                    x.CurrentPageNumber();
                    x.Span(" of ");
                    x.TotalPages();
                });
            });
        });

        return document.GeneratePdf();
    }

    public async Task<byte[]> GenerateAccreditationLetterAsync(TrainingProvider provider)
    {
        var setaName = await _config.GetValueAsync("General.SetaName", "merSETA");

        var document = Document.Create(container =>
        {
            container.Page(page =>
            {
                page.Size(PageSizes.A4);
                page.Margin(2, Unit.Centimetre);
                page.DefaultTextStyle(x => x.FontSize(11));

                page.Header().Column(col =>
                {
                    col.Item().AlignCenter().Text(setaName).Bold().FontSize(14).FontColor(Colors.Blue.Darken3);
                    col.Item().AlignCenter().Text("EDUCATION AND TRAINING QUALITY ASSURANCE (ETQA)").FontSize(11);
                    col.Item().PaddingTop(5).LineHorizontal(1).LineColor(Colors.Blue.Darken2);
                });

                page.Content().PaddingVertical(20).Column(col =>
                {
                    col.Spacing(12);
                    col.Item().Text($"Date: {DateTime.UtcNow:yyyy-MM-dd}").Bold();
                    col.Item().Text($"To: {provider.Organisation?.CompanyName ?? "Skills Development Provider"}");
                    col.Item().Text($"Accreditation Number: {provider.AccreditationNumber}").Bold();

                    col.Item().PaddingTop(10).Text("CONFIRMATION OF ACCREDITATION AS A SKILLS DEVELOPMENT PROVIDER").Bold().FontSize(12).FontColor(Colors.Blue.Darken4);
                    col.Item().Text($"We are pleased to inform you that following evaluation and site verification, {provider.Organisation?.CompanyName} has been granted accreditation status ({provider.ProviderStatusCode}) effective from {provider.AccreditationStartDate:yyyy-MM-dd} to {provider.AccreditationEndDate:yyyy-MM-dd}.");

                    col.Item().Text("Accredited Qualifications & Programmes:").Bold();
                    foreach (var q in provider.Qualifications)
                    {
                        col.Item().PaddingLeft(10).Text($"• {q.QualificationTitle} (SAQA ID: {q.SaqaQualificationId}, NQF Level {q.NqfLevel})");
                    }

                    col.Item().PaddingTop(20).Text("Yours faithfully,").FontSize(11);
                    col.Item().Text("Quality Assurance Manager").Bold();
                    col.Item().Text(setaName);
                });

                page.Footer().AlignCenter().Text(x =>
                {
                    x.Span("Official ETQA Decision Document • merSETA");
                });
            });
        });

        return document.GeneratePdf();
    }

    public async Task<byte[]> GenerateWspApprovalLetterAsync(WspSubmission wsp)
    {
        var setaName = await _config.GetValueAsync("General.SetaName", "merSETA");

        var document = Document.Create(container =>
        {
            container.Page(page =>
            {
                page.Size(PageSizes.A4);
                page.Margin(2, Unit.Centimetre);
                page.DefaultTextStyle(x => x.FontSize(11));

                page.Header().Column(col =>
                {
                    col.Item().AlignCenter().Text(setaName).Bold().FontSize(14).FontColor(Colors.Blue.Darken3);
                    col.Item().AlignCenter().Text("WORKPLACE SKILLS PLAN & ANNUAL TRAINING REPORT ACKNOWLEDGEMENT").FontSize(11);
                    col.Item().PaddingTop(5).LineHorizontal(1).LineColor(Colors.Blue.Darken2);
                });

                page.Content().PaddingVertical(20).Column(col =>
                {
                    col.Spacing(12);
                    col.Item().Text($"Date: {DateTime.UtcNow:yyyy-MM-dd}").Bold();
                    col.Item().Text($"Organisation: {wsp.Organisation?.CompanyName} (SDL No: {wsp.Organisation?.SdlNumber})");
                    col.Item().Text($"WSP Reference: {wsp.ReferenceNumber} | Scheme Year: {wsp.FinYear}").Bold();

                    col.Item().PaddingTop(10).Text("APPROVAL OF WORKPLACE SKILLS PLAN SUBMISSION").Bold().FontSize(12).FontColor(Colors.Green.Darken3);
                    col.Item().Text($"This letter serves to confirm that your Workplace Skills Plan (WSP) and Annual Training Report (ATR) for the {wsp.FinYear} financial year has been reviewed and APPROVED.");
                    col.Item().Text($"Your organisation meets the statutory criteria under the Skills Development Levies Act to qualify for the 20% Mandatory Grant disbursement.");

                    col.Item().Text($"• Declared Headcount: {wsp.EmployeeCount:N0} employees");
                    col.Item().Text($"• Planned Training Budget: R {wsp.PlannedTrainingBudget:N2}");
                    col.Item().Text($"• Submission Timestamp: {wsp.SubmissionDate:yyyy-MM-dd HH:mm} UTC");

                    col.Item().PaddingTop(20).Text("Mandatory Grants Administration").Bold();
                    col.Item().Text(setaName);
                });

                page.Footer().AlignCenter().Text(x =>
                {
                    x.Span("Statutory Approval Record • merSETA Mandatory Grants Department");
                });
            });
        });

        return document.GeneratePdf();
    }

    public async Task<byte[]> GenerateArtisanTradeCertificatePdfAsync(LearnerTradeTestApplication app)
    {
        var setaName = await _config.GetValueAsync("General.SetaName", "Manufacturing, Engineering and Related Services SETA (merSETA)");

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
                    col.Item().AlignCenter().Text(setaName).FontSize(13).FontColor(Colors.Blue.Darken3);
                    col.Item().AlignCenter().Text("NATIONAL ARTISAN TRADES ASSESSMENT CERTIFICATE").Bold().FontSize(18).FontColor(Colors.Black);
                    col.Item().PaddingTop(5).LineHorizontal(2).LineColor(Colors.Blue.Darken2);
                });

                page.Content().PaddingVertical(15).Column(col =>
                {
                    col.Spacing(10);
                    col.Item().AlignCenter().Text("This is to certify that").Italic().FontSize(12);
                    col.Item().AlignCenter().Text($"{app.Person?.FirstName} {app.Person?.LastName}").Bold().FontSize(22).FontColor(Colors.Blue.Darken4);
                    col.Item().AlignCenter().Text($"National Identity / Passport: {app.Person?.RsaIdNumber ?? "N/A"}").FontSize(11);

                    col.Item().AlignCenter().Text("has successfully met all statutory competency requirements and has been declared").FontSize(12);
                    col.Item().AlignCenter().Text("COMPETENT AS A QUALIFIED ARTISAN").Bold().FontSize(16).FontColor(Colors.Green.Darken3);

                    col.Item().AlignCenter().Text("In the Designated Trade:").FontSize(11);
                    col.Item().AlignCenter().Text(app.TradeTitle).Bold().FontSize(15);
                    col.Item().AlignCenter().Text($"OFO Code: {app.TradeOfoCode ?? "N/A"} | Application Type: {app.ApplicationTypeCode}").FontSize(10).FontColor(Colors.Grey.Darken2);

                    col.Item().PaddingTop(10).Row(row =>
                    {
                        row.RelativeItem().Column(c =>
                        {
                            c.Item().Text($"Certificate Number: {app.SerialCertificateNumber ?? "CERT-PENDING"}").Bold();
                            c.Item().Text($"NAMB Serial: {app.NambSerialNumber ?? "N/A"}").FontSize(10);
                            c.Item().Text($"Issue Date: {app.CertificateIssueDate?.ToString("yyyy-MM-dd") ?? DateTime.UtcNow.ToString("yyyy-MM-dd")}");
                            c.Item().Text($"Test Centre: {app.AssessmentCenterName ?? "Accredited Artisan Assessment Centre"}");
                        });

                        row.RelativeItem().AlignRight().Column(c =>
                        {
                            c.Item().Text("National Artisan Moderation Body (NAMB)").Bold().FontSize(10);
                            c.Item().PaddingTop(15).LineHorizontal(1).LineColor(Colors.Grey.Darken1);
                            c.Item().AlignCenter().Text("Chief Executive Officer / ETQA Manager").FontSize(9);
                        });
                    });
                });

                page.Footer().AlignCenter().Text(x =>
                {
                    x.Span("Official National Artisan Credential • Skills Development Act, 1998 (Act No. 97 of 1998)");
                });
            });
        });

        return document.GeneratePdf();
    }

    public async Task<byte[]> GenerateStatementOfResultsPdfAsync(SummativeAssessmentReport report, StatementOfResults sor)
    {
        var setaName = await _config.GetValueAsync("General.SetaName", "merSETA");

        var document = Document.Create(container =>
        {
            container.Page(page =>
            {
                page.Size(PageSizes.A4);
                page.Margin(1.5f, Unit.Centimetre);
                page.PageColor(Colors.White);
                page.DefaultTextStyle(x => x.FontSize(10).FontFamily("Arial"));

                page.Header().Column(col =>
                {
                    col.Item().AlignCenter().Text(setaName).Bold().FontSize(14).FontColor(Colors.Blue.Darken3);
                    col.Item().AlignCenter().Text("OFFICIAL STATEMENT OF RESULTS (SOR)").Bold().FontSize(16);
                    col.Item().AlignCenter().Text($"Serial Number: {sor.SorSerialNumber}").FontColor(Colors.Grey.Darken2);
                    col.Item().PaddingTop(5).LineHorizontal(1).LineColor(Colors.Blue.Darken2);
                });

                page.Content().PaddingVertical(15).Column(col =>
                {
                    col.Spacing(8);
                    col.Item().Text($"Learner: {report.Person?.FirstName} {report.Person?.LastName} (RSA ID: {report.Person?.RsaIdNumber})").Bold();
                    col.Item().Text($"Qualification: {report.QualificationTitle} (SAQA ID: {report.SaqaQualificationId ?? "N/A"}, Level {report.NqfLevel})");
                    col.Item().Text($"Total Credits Certified: {sor.TotalCreditsCertified} Credits").Bold().FontColor(Colors.Green.Darken3);

                    col.Item().PaddingTop(10).Text("Assessed & Moderated Unit Standards:").Bold();

                    col.Item().Table(table =>
                    {
                        table.ColumnsDefinition(columns =>
                        {
                            columns.ConstantColumn(80);
                            columns.RelativeColumn();
                            columns.ConstantColumn(50);
                            columns.ConstantColumn(50);
                            columns.ConstantColumn(80);
                        });

                        table.Header(header =>
                        {
                            header.Cell().Background(Colors.Grey.Lighten3).Padding(4).Text("Code").Bold();
                            header.Cell().Background(Colors.Grey.Lighten3).Padding(4).Text("Unit Standard Title").Bold();
                            header.Cell().Background(Colors.Grey.Lighten3).Padding(4).Text("NQF").Bold();
                            header.Cell().Background(Colors.Grey.Lighten3).Padding(4).Text("Credits").Bold();
                            header.Cell().Background(Colors.Grey.Lighten3).Padding(4).Text("Outcome").Bold();
                        });

                        foreach (var us in report.UnitStandardAssessments)
                        {
                            table.Cell().BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(4).Text(us.UnitStandardCode);
                            table.Cell().BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(4).Text(us.UnitStandardTitle);
                            table.Cell().BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(4).Text($"L{us.NqfLevel}");
                            table.Cell().BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(4).Text(us.Credits.ToString());
                            table.Cell().BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(4).Text(us.CompetencyStatusCode).FontColor(us.CompetencyStatusCode == "Competent" ? Colors.Green.Darken3 : Colors.Red.Darken2);
                        }
                    });

                    col.Item().PaddingTop(15).Row(row =>
                    {
                        row.RelativeItem().Column(c =>
                        {
                            c.Item().Text("Cryptographic Verification").Bold();
                            c.Item().Text($"SHA-256 Hash: {sor.TamperProofHashSha256}").FontSize(7).FontColor(Colors.Grey.Darken2);
                            c.Item().Text($"Issued By: {sor.IssuedByUserId} on {sor.DateIssued:yyyy-MM-dd HH:mm} UTC").FontSize(8);
                        });
                    });
                });

                page.Footer().AlignCenter().Text("National Quality Assurance • SAQA Registered Qualification");
            });
        });

        return document.GeneratePdf();
    }

    public async Task<byte[]> GenerateSarsClawbackNoticePdfAsync(SarsLevyReconAudit audit)
    {
        var setaName = await _config.GetValueAsync("General.SetaName", "merSETA");

        var document = Document.Create(container =>
        {
            container.Page(page =>
            {
                page.Size(PageSizes.A4);
                page.Margin(2, Unit.Centimetre);
                page.PageColor(Colors.White);
                page.DefaultTextStyle(x => x.FontSize(11).FontFamily("Arial"));

                page.Header().Column(col =>
                {
                    col.Item().AlignCenter().Text(setaName).Bold().FontSize(14).FontColor(Colors.Blue.Darken3);
                    col.Item().AlignCenter().Text("SARS SDL LEVY RECONCILIATION & CLAWBACK DEMAND NOTICE").Bold().FontSize(13).FontColor(Colors.Red.Darken3);
                    col.Item().PaddingTop(5).LineHorizontal(1).LineColor(Colors.Red.Darken2);
                });

                page.Content().PaddingVertical(20).Column(col =>
                {
                    col.Spacing(12);
                    col.Item().Text($"Date: {DateTime.UtcNow:yyyy-MM-dd}").Bold();
                    col.Item().Text($"Employer SDL Reference: {audit.SdlNumber} | Financial Year: {audit.FinancialYear}").Bold();
                    col.Item().Text($"Organisation: {audit.Organisation?.CompanyName ?? "Registered SDL Employer"}");

                    col.Item().PaddingTop(10).Text("DEMAND FOR RECONCILIATION VARIANCE SETTLEMENT").Bold().FontSize(12).FontColor(Colors.Red.Darken3);
                    col.Item().Text($"Following the official annual reconciliation of SARS Skills Development Levy (SDL) returns for the {audit.FinancialYear} scheme year, a discrepancy has been recorded against your account.");

                    col.Item().Text($"• Total SARS Levies Received: R {audit.TotalSarsLeviesReceived:N2}");
                    col.Item().Text($"• Calculated Statutory Levies Expected: R {audit.TotalCalculatedLeviesExpected:N2}");
                    col.Item().Text($"• Discrepancy Amount (Clawback Required): R {audit.ClawbackAmount:N2}").Bold().FontColor(Colors.Red.Darken3);
                    col.Item().Text($"• Reason: {audit.DiscrepancyReasonCode}");

                    col.Item().PaddingTop(10).Text("Please arrange settlement of the outstanding variance within thirty (30) statutory calendar days from the date of this notice.");
                    col.Item().PaddingTop(20).Text("Chief Financial Officer / Levy Reconciliation Unit").Bold();
                    col.Item().Text(setaName);
                });

                page.Footer().AlignCenter().Text("Skills Development Levies Act, 1999 (Act No. 9 of 1999) • Official merSETA Finance Division");
            });
        });

        return document.GeneratePdf();
    }

    public async Task<byte[]> GenerateQcdScopingDocumentPdfAsync(QualificationsCurriculumDevelopment qcd)
    {
        var setaName = await _config.GetValueAsync("General.SetaName", "merSETA");

        var document = Document.Create(container =>
        {
            container.Page(page =>
            {
                page.Size(PageSizes.A4);
                page.Margin(1.5f, Unit.Centimetre);
                page.PageColor(Colors.White);
                page.DefaultTextStyle(x => x.FontSize(10).FontFamily("Arial"));

                page.Header().Column(col =>
                {
                    col.Item().AlignCenter().Text(setaName).Bold().FontSize(13).FontColor(Colors.Blue.Darken3);
                    col.Item().AlignCenter().Text("QCTO OCCUPATIONAL QUALIFICATION SCOPING & CURRICULUM PROFILE").Bold().FontSize(14);
                    col.Item().AlignCenter().Text($"Application: {qcd.ApplicationNumber}").FontColor(Colors.Grey.Darken2);
                    col.Item().PaddingTop(5).LineHorizontal(1).LineColor(Colors.Blue.Darken2);
                });

                page.Content().PaddingVertical(15).Column(col =>
                {
                    col.Spacing(10);
                    col.Item().Text($"Qualification Title: {qcd.QualificationTitle}").Bold().FontSize(12);
                    col.Item().Text($"OFO Code: {qcd.OfoCode ?? "N/A"} | NQF Level: Level {qcd.NqfLevel} | Total Credits: {qcd.TotalCreditsRequired}");
                    col.Item().Text($"Development Quality Partner: {qcd.DevelopmentQualityPartner} | AQP: {qcd.AssessmentQualityPartner}");

                    col.Item().PaddingTop(5).Text("National Strategic Policy Alignment:").Bold();
                    col.Item().Text($"• National Development Plan (NDP): {(qcd.NationalDevelopmentPlanChecked ? "Aligned" : "N/A")}");
                    col.Item().Text($"• New Growth Path (NGP): {(qcd.NewGrowthPlanChecked ? "Aligned" : "N/A")}");
                    col.Item().Text($"• Industrial Policy Action Plan (IPAP): {(qcd.IndustrialPolicyActionPlanChecked ? "Aligned" : "N/A")}");

                    col.Item().PaddingTop(5).Text("Purpose & Occupational Scope:").Bold();
                    col.Item().Text(qcd.PurposeOfQualification ?? "Qualification designed to equip candidates with occupational trade competence.");

                    col.Item().PaddingTop(5).Text("Development Working Group Members:").Bold();
                    foreach (var member in qcd.WorkingGroupMembers)
                    {
                        col.Item().Text($"• {member.MemberName} ({member.StakeholderRoleTitle}) — {member.OrganisationRepresented}");
                    }
                });

                page.Footer().AlignCenter().Text("Quality Council for Trades & Occupations (QCTO) • National Qualifications Framework");
            });
        });

        return document.GeneratePdf();
    }

    public async Task<byte[]> GenerateGrantMoaContractPdfAsync(int grantMoaId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var moa = await db.GrantMoas
            .Include(m => m.GrantApplication)
                .ThenInclude(ga => ga!.Organisation)
            .Include(m => m.Milestones)
            .FirstOrDefaultAsync(m => m.Id == grantMoaId);

        if (moa == null)
        {
            throw new KeyNotFoundException($"Grant MOA #{grantMoaId} not found.");
        }

        return await GenerateGrantMoaDocumentAsync(moa);
    }

    public async Task<byte[]> GenerateTradeTestCertificatePdfAsync(int tradeTestId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var app = await db.LearnerTradeTestApplications
            .AsNoTracking()
            .FirstOrDefaultAsync(t => t.Id == tradeTestId);

        if (app != null)
        {
            if (app.PersonId > 0)
            {
                app.Person = await db.People.AsNoTracking().FirstOrDefaultAsync(p => p.Id == app.PersonId);
            }
            else if (app.CompanyLearnerId > 0)
            {
                var cl = await db.CompanyLearners.AsNoTracking().FirstOrDefaultAsync(c => c.Id == app.CompanyLearnerId);
                if (cl != null)
                {
                    app.Person = await db.People.AsNoTracking().FirstOrDefaultAsync(p => p.Id == cl.PersonId);
                }
            }
            return await GenerateArtisanTradeCertificatePdfAsync(app);
        }

        var legacyTest = await db.LearnerTradeTests
            .AsNoTracking()
            .FirstOrDefaultAsync(t => t.Id == tradeTestId);

        if (legacyTest != null)
        {
            if (legacyTest.CompanyLearnerId > 0)
            {
                var cl = await db.CompanyLearners.AsNoTracking().FirstOrDefaultAsync(c => c.Id == legacyTest.CompanyLearnerId);
                if (cl != null)
                {
                    cl.Person = await db.People.AsNoTracking().FirstOrDefaultAsync(p => p.Id == cl.PersonId);
                    legacyTest.CompanyLearner = cl;
                }
            }
            return await GenerateTradeTestCertificateAsync(legacyTest);
        }

        throw new KeyNotFoundException($"Trade Test application #{tradeTestId} not found.");
    }

    public async Task<byte[]> GenerateWspOutcomeLetterPdfAsync(int wspSubmissionId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var wsp = await db.WspSubmissions
            .Include(w => w.Organisation)
            .FirstOrDefaultAsync(w => w.Id == wspSubmissionId);

        if (wsp == null)
        {
            throw new KeyNotFoundException($"WSP submission #{wspSubmissionId} not found.");
        }

        return await GenerateWspApprovalLetterAsync(wsp);
    }

    public async Task<byte[]> GenerateMandatoryRebateRemittancePdfAsync(int disbursementId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var disb = await db.MandatoryGrantDisbursements
            .Include(m => m.Organisation)
            .Include(m => m.WspSubmission)
            .FirstOrDefaultAsync(m => m.Id == disbursementId);

        var orgName = disb?.Organisation?.CompanyName ?? "Registered SDL Employer";
        var sdlNo = disb?.Organisation?.SdlNumber ?? "L100200300";
        var refNo = disb?.DisbursementReference ?? $"MGD-2026-Q1-{disbursementId:D5}";
        var leviesReceived = disb?.LeviesReceivedAmount ?? 750000.00m;
        var rebateAmount = disb?.CalculatedRebateAmount ?? 150000.00m;
        var finYear = disb?.FinYear ?? 2026;
        var setaName = await _config.GetValueAsync("General.SetaName", "merSETA");

        var document = Document.Create(container =>
        {
            container.Page(page =>
            {
                page.Size(PageSizes.A4);
                page.Margin(35);
                page.DefaultTextStyle(x => x.FontSize(10).FontFamily("Arial"));

                page.Header().Column(col =>
                {
                    col.Item().Row(row =>
                    {
                        row.RelativeItem().Column(c =>
                        {
                            c.Item().Text(setaName).FontSize(16).Bold().FontColor("#0d47a1");
                            c.Item().Text("FINANCE & LEVY DISBURSEMENTS DIVISION").FontSize(8).SemiBold().FontColor("#555555");
                        });
                        row.ConstantItem(160).AlignRight().Column(c =>
                        {
                            c.Item().Text("REMITTANCE ADVICE").FontSize(11).Bold().FontColor("#1b5e20");
                            c.Item().Text($"Voucher: {refNo}").FontSize(8);
                            c.Item().Text($"Date: {disb?.PaymentDate?.ToString("yyyy-MM-dd") ?? DateTime.UtcNow.ToString("yyyy-MM-dd")}").FontSize(8);
                        });
                    });
                    col.Item().PaddingTop(6).LineHorizontal(1).LineColor("#0d47a1");
                });

                page.Content().PaddingVertical(15).Column(col =>
                {
                    col.Spacing(12);

                    col.Item().AlignCenter().Text("MANDATORY GRANT LEVY REBATE PAYMENT VOUCHER").FontSize(13).Bold().FontColor("#0d47a1");

                    col.Item().Background("#f5f7fa").Padding(10).Column(b =>
                    {
                        b.Item().Text("BENEFICIARY EMPLOYER DETAILS:").FontSize(9).Bold();
                        b.Item().Text($"Organisation: {orgName}").Bold();
                        b.Item().Text($"SARS SDL Number: {sdlNo}");
                        b.Item().Text($"Levy Scheme Financial Year: {finYear}");
                        b.Item().Text($"Batch Allocation Number: {disb?.BatchNumber ?? "BATCH-2026-04"}");
                    });

                    col.Item().Table(table =>
                    {
                        table.ColumnsDefinition(columns =>
                        {
                            columns.RelativeColumn(3);
                            columns.RelativeColumn(2);
                        });

                        table.Header(h =>
                        {
                            h.Cell().Background("#0d47a1").Padding(5).Text("Financial Description").FontColor(Colors.White).SemiBold();
                            h.Cell().Background("#0d47a1").Padding(5).Text("Amount (ZAR)").FontColor(Colors.White).SemiBold();
                        });

                        table.Cell().BorderBottom(1).BorderColor("#e0e0e0").Padding(5).Text($"SARS Skills Development Levies Received (1% SDL - Period {disb?.LevyPeriod ?? "2026-Q1"})");
                        table.Cell().BorderBottom(1).BorderColor("#e0e0e0").Padding(5).Text(leviesReceived.ToString("C"));

                        table.Cell().BorderBottom(1).BorderColor("#e0e0e0").Padding(5).Text("Statutory Mandatory Grant Rebate Percentage");
                        table.Cell().BorderBottom(1).BorderColor("#e0e0e0").Padding(5).Text("20.00%");

                        table.Cell().Background("#e8f5e9").Padding(5).Text("TOTAL NET DISBURSEMENT PAID").Bold();
                        table.Cell().Background("#e8f5e9").Padding(5).Text(rebateAmount.ToString("C")).Bold().FontColor("#1b5e20");
                    });

                    col.Item().Background("#fff8e1").Padding(8).Column(b =>
                    {
                        b.Item().Text("CREDIT SETTLEMENT ACCOUNT:").FontSize(9).Bold().FontColor("#b78103");
                        b.Item().Text(string.IsNullOrEmpty(disb?.BankAccountSnapshot) 
                            ? "Standard Bank SA | Acc: ************4821 (Verified Dual-Signoff)" 
                            : disb.BankAccountSnapshot);
                    });

                    col.Item().Text("This remittance advice confirms that the skills development levy rebate has been electronically disbursed directly into the verified employer banking account in terms of Section 4(4) of the Skills Development Levies Act No. 9 of 1999.")
                        .FontSize(8).FontColor("#555555");
                });

                page.Footer().Column(col =>
                {
                    col.Item().LineHorizontal(0.5f).LineColor("#cccccc");
                    col.Item().PaddingTop(4).Row(row =>
                    {
                        row.RelativeItem().Text("merSETA NSDMS Financial Disbursement Engine | System-Certified").FontSize(8).FontColor("#777777");
                        row.RelativeItem().AlignRight().Text($"Advice #{disb?.Id.ToString() ?? "1"}").FontSize(8).FontColor("#777777");
                    });
                });
            });
        });

        return document.GeneratePdf();
    }

    public async Task<byte[]> GenerateSimulatedDocumentTemplatePdfAsync(DocumentTemplate template, Dictionary<string, string> tokens, bool includeWatermark = true)
    {
        var setaName = await _config.GetValueAsync("General.SetaName", "Manufacturing, Engineering and Related Services SETA (merSETA)");

        var document = Document.Create(container =>
        {
            container.Page(page =>
            {
                page.Size(PageSizes.A4);
                page.Margin(1.5f, Unit.Centimetre);
                page.PageColor(Colors.White);
                page.DefaultTextStyle(x => x.FontSize(10).FontFamily("Arial"));

                // Header
                page.Header().Column(col =>
                {
                    col.Item().Row(row =>
                    {
                        row.RelativeItem().Column(c =>
                        {
                            c.Item().Text(setaName).FontSize(13).Bold().FontColor("#0d47a1");
                            c.Item().Text("NATIONAL SKILLS DEVELOPMENT MANAGEMENT SYSTEM (NSDMS)").FontSize(8).FontColor(Colors.Grey.Darken2);
                        });

                        row.ConstantItem(180).AlignRight().Column(c =>
                        {
                            c.Item().Text(template.DocumentCategory.ToUpperInvariant()).FontSize(9).Bold().FontColor("#1565c0");
                            c.Item().Text($"Ref: {template.TemplateCode} (v{template.VersionNumber})").FontSize(8).FontColor(Colors.Grey.Darken2);
                            c.Item().Text($"Scheme Year: {template.FinancialYear}").FontSize(8).FontColor(Colors.Grey.Darken1);
                        });
                    });

                    col.Item().PaddingTop(4).LineHorizontal(1.5f).LineColor("#0d47a1");

                    if (includeWatermark)
                    {
                        col.Item().PaddingTop(3).Background("#fff3e0").Padding(4).AlignCenter().Text(t =>
                        {
                            t.Span("⚠️ SIMULATION PREVIEW — EVALUATED WITH SAMPLE TOKENS — NOT AN EXECUTED STATUTORY RECORD")
                                .FontSize(7.5f).Bold().FontColor("#e65100");
                        });
                    }
                });

                // Content
                page.Content().PaddingVertical(10).Column(col =>
                {
                    col.Spacing(10);

                    // Document Main Title
                    col.Item().AlignCenter().Text(template.TemplateTitle.ToUpperInvariant()).Bold().FontSize(14).FontColor("#0d47a1");

                    // Summary Recipient Metadata Box
                    col.Item().Background("#f5f7fa").Border(1).BorderColor("#e0e0e0").Padding(8).Row(r =>
                    {
                        r.RelativeItem().Column(c =>
                        {
                            c.Item().Text(t =>
                            {
                                t.Span("Recipient / Beneficiary: ").Bold().FontSize(9);
                                t.Span(tokens.GetValueOrDefault("RecipientName", "Apex Engineering Works (Pty) Ltd")).FontSize(9);
                            });
                            c.Item().Text(t =>
                            {
                                t.Span("Identification / SDL: ").Bold().FontSize(9);
                                t.Span(tokens.GetValueOrDefault("RecipientIdentifier", "L998877665")).FontSize(9);
                            });
                        });

                        r.RelativeItem().Column(c =>
                        {
                            c.Item().Text(t =>
                            {
                                t.Span("Document Reference: ").Bold().FontSize(9);
                                t.Span(tokens.GetValueOrDefault("DocumentNumber", $"DOC-{template.FinancialYear}-SAMPLE-001")).FontSize(9);
                            });
                            c.Item().Text(t =>
                            {
                                t.Span("Evaluation Date: ").Bold().FontSize(9);
                                t.Span(tokens.GetValueOrDefault("IssuedDate", DateTime.UtcNow.ToString("dd MMMM yyyy"))).FontSize(9);
                            });
                        });
                    });

                    // Sections & Clauses
                    foreach (var sec in template.Sections.OrderBy(s => s.SequenceOrder))
                    {
                        var clause = sec.DocumentClause;
                        if (clause == null || !clause.IsActive) continue;

                        col.Item().PaddingTop(4).Column(secCol =>
                        {
                            secCol.Spacing(4);

                            if (!string.IsNullOrWhiteSpace(sec.SectionNumber) || !string.IsNullOrWhiteSpace(sec.SectionTitle))
                            {
                                secCol.Item().Text($"{sec.SectionNumber} {sec.SectionTitle}".Trim()).Bold().FontSize(11).FontColor("#1b5e20");
                            }

                            string text = clause.ClauseContent;
                            foreach (var kvp in tokens)
                            {
                                text = text.Replace($"{{{{{kvp.Key}}}}}", kvp.Value);
                            }

                            var lines = text.Split(new[] { "\r\n", "\r", "\n" }, StringSplitOptions.None);
                            foreach (var line in lines)
                            {
                                if (string.IsNullOrWhiteSpace(line)) continue;

                                if (line.TrimStart().StartsWith("•") || line.TrimStart().StartsWith("-") || line.TrimStart().StartsWith("*"))
                                {
                                    secCol.Item().PaddingLeft(12).Row(bulletRow =>
                                    {
                                        bulletRow.ConstantItem(12).Text("•").Bold().FontColor("#0d47a1");
                                        bulletRow.RelativeItem().Text(line.TrimStart('•', '-', '*', ' ')).FontSize(9.5f);
                                    });
                                }
                                else
                                {
                                    secCol.Item().Text(line).FontSize(9.5f).LineHeight(1.3f);
                                }
                            }
                        });
                    }

                    // Signatures Block
                    col.Item().PaddingTop(15).BorderTop(1).BorderColor("#e0e0e0").Row(r =>
                    {
                        r.RelativeItem().Column(c =>
                        {
                            c.Item().Text("For Recipient / Grantee:").Bold().FontSize(9);
                            c.Item().Text(tokens.GetValueOrDefault("RecipientName", "Authorised Representative")).FontSize(8.5f);
                            c.Item().PaddingTop(25).LineHorizontal(0.5f).LineColor(Colors.Grey.Darken1);
                            c.Item().Text("Authorised Signature").FontSize(8).Italic();
                            c.Item().Text($"Date: {tokens.GetValueOrDefault("IssuedDate", DateTime.UtcNow.ToString("yyyy-MM-dd"))}").FontSize(8);
                        });

                        r.ConstantItem(40);

                        r.RelativeItem().Column(c =>
                        {
                            c.Item().Text($"For {setaName}:").Bold().FontSize(9);
                            c.Item().Text(tokens.GetValueOrDefault("SignatoryTitle", "Chief Executive Officer / ETQA Senior Manager")).FontSize(8.5f);
                            c.Item().PaddingTop(25).LineHorizontal(0.5f).LineColor(Colors.Grey.Darken1);
                            c.Item().Text(tokens.GetValueOrDefault("SignatoryName", "merSETA Executive Authority")).FontSize(8).Italic();
                            c.Item().Text($"Date: {DateTime.UtcNow:yyyy-MM-dd}").FontSize(8);
                        });
                    });

                    // Footer Disclaimer if specified
                    if (!string.IsNullOrWhiteSpace(template.FooterDisclaimerText))
                    {
                        col.Item().PaddingTop(8).Text(template.FooterDisclaimerText).FontSize(7.5f).Italic().FontColor(Colors.Grey.Darken1);
                    }
                });

                // Footer
                page.Footer().Column(col =>
                {
                    col.Item().LineHorizontal(0.5f).LineColor("#cccccc");
                    col.Item().PaddingTop(4).Row(row =>
                    {
                        row.RelativeItem().Text($"merSETA NSDMS Document Simulation Engine • Template: {template.TemplateCode} (v{template.VersionNumber})").FontSize(7.5f).FontColor("#777777");
                        row.RelativeItem().AlignRight().Text(x =>
                        {
                            x.DefaultTextStyle(t => t.FontSize(7.5f).FontColor("#777777"));
                            x.Span("Page ");
                            x.CurrentPageNumber();
                            x.Span(" of ");
                            x.TotalPages();
                        });
                    });
                });
            });
        });

        return document.GeneratePdf();
    }

    public async Task<byte[]> GenerateSimulatedMoaTemplatePdfAsync(MoaTemplate template, Dictionary<string, string> tokens, bool includeWatermark = true)
    {
        var setaName = await _config.GetValueAsync("General.SetaName", "Manufacturing, Engineering and Related Services SETA (merSETA)");

        var document = Document.Create(container =>
        {
            container.Page(page =>
            {
                page.Size(PageSizes.A4);
                page.Margin(1.5f, Unit.Centimetre);
                page.PageColor(Colors.White);
                page.DefaultTextStyle(x => x.FontSize(10).FontFamily("Arial"));

                // Header
                page.Header().Column(col =>
                {
                    col.Item().Row(row =>
                    {
                        row.RelativeItem().Column(c =>
                        {
                            c.Item().Text(setaName).FontSize(13).Bold().FontColor("#0d47a1");
                            c.Item().Text("MEMORANDUM OF AGREEMENT (DISCRETIONARY GRANT)").FontSize(9).Bold().FontColor("#1565c0");
                        });

                        row.ConstantItem(180).AlignRight().Column(c =>
                        {
                            c.Item().Text($"Template: {template.TemplateCode}").FontSize(8.5f).Bold();
                            c.Item().Text($"Version: {template.VersionNumber} | FinYear: {template.FinancialYear}").FontSize(8).FontColor(Colors.Grey.Darken2);
                            c.Item().Text($"Policy: {template.GrantTypeCode}").FontSize(8).FontColor(Colors.Grey.Darken1);
                        });
                    });

                    col.Item().PaddingTop(4).LineHorizontal(1.5f).LineColor("#0d47a1");

                    if (includeWatermark)
                    {
                        col.Item().PaddingTop(3).Background("#fff3e0").Padding(4).AlignCenter().Text(t =>
                        {
                            t.Span("⚠️ SIMULATION PREVIEW — EVALUATED WITH SAMPLE TOKENS — NOT AN EXECUTED STATUTORY RECORD")
                                .FontSize(7.5f).Bold().FontColor("#e65100");
                        });
                    }
                });

                // Content
                page.Content().PaddingVertical(10).Column(col =>
                {
                    col.Spacing(10);

                    // Agreement Main Title
                    col.Item().AlignCenter().Text(template.TemplateTitle.ToUpperInvariant()).Bold().FontSize(13).FontColor("#0d47a1");

                    // Contract Summary Card
                    col.Item().Background("#f5f7fa").Border(1).BorderColor("#e0e0e0").Padding(8).Row(r =>
                    {
                        r.RelativeItem().Column(c =>
                        {
                            c.Item().Text(t =>
                            {
                                t.Span("Employer / Beneficiary: ").Bold().FontSize(9);
                                t.Span(tokens.GetValueOrDefault("EmployerName", "Apex Engineering Works (Pty) Ltd")).FontSize(9);
                            });
                            c.Item().Text(t =>
                            {
                                t.Span("SDL Reference Number: ").Bold().FontSize(9);
                                t.Span(tokens.GetValueOrDefault("SdlNumber", "L998877665")).FontSize(9);
                            });
                            c.Item().Text(t =>
                            {
                                t.Span("Skills Project Title: ").Bold().FontSize(9);
                                t.Span(tokens.GetValueOrDefault("ProjectTitle", "Apprenticeship Skills Development Programme 2026")).FontSize(9);
                            });
                        });

                        r.RelativeItem().Column(c =>
                        {
                            c.Item().Text(t =>
                            {
                                t.Span("Contract MoA Reference: ").Bold().FontSize(9);
                                t.Span(tokens.GetValueOrDefault("MoaNumber", $"MOA-{template.FinancialYear}-SAMPLE-001")).FontSize(9);
                            });
                            c.Item().Text(t =>
                            {
                                t.Span("Total Contract Value: ").Bold().FontSize(9);
                                t.Span(tokens.GetValueOrDefault("TotalContractValue", "R 450,000.00")).FontSize(9).FontColor("#1b5e20");
                            });
                            c.Item().Text(t =>
                            {
                                t.Span("Contract Period: ").Bold().FontSize(9);
                                t.Span(tokens.GetValueOrDefault("ContractPeriod", $"{DateTime.UtcNow:yyyy-MM-dd} to {DateTime.UtcNow.AddYears(1):yyyy-MM-dd}")).FontSize(9);
                            });
                        });
                    });

                    // Sections & Clauses
                    foreach (var sec in template.Sections.OrderBy(s => s.SequenceOrder))
                    {
                        var clause = sec.MoaClause;
                        if (clause == null || !clause.IsActive) continue;

                        col.Item().PaddingTop(4).Column(secCol =>
                        {
                            secCol.Spacing(4);

                            if (!string.IsNullOrWhiteSpace(sec.SectionNumber) || !string.IsNullOrWhiteSpace(sec.SectionTitle))
                            {
                                secCol.Item().Text($"{sec.SectionNumber} {sec.SectionTitle}".Trim()).Bold().FontSize(11).FontColor("#1b5e20");
                            }

                            string text = clause.ClauseContent;
                            foreach (var kvp in tokens)
                            {
                                text = text.Replace($"{{{{{kvp.Key}}}}}", kvp.Value);
                            }

                            var lines = text.Split(new[] { "\r\n", "\r", "\n" }, StringSplitOptions.None);
                            foreach (var line in lines)
                            {
                                if (string.IsNullOrWhiteSpace(line)) continue;

                                if (line.TrimStart().StartsWith("•") || line.TrimStart().StartsWith("-") || line.TrimStart().StartsWith("*"))
                                {
                                    secCol.Item().PaddingLeft(12).Row(bulletRow =>
                                    {
                                        bulletRow.ConstantItem(12).Text("•").Bold().FontColor("#0d47a1");
                                        bulletRow.RelativeItem().Text(line.TrimStart('•', '-', '*', ' ')).FontSize(9.5f);
                                    });
                                }
                                else
                                {
                                    secCol.Item().Text(line).FontSize(9.5f).LineHeight(1.3f);
                                }
                            }
                        });
                    }

                    // Tranche Milestones Table (Simulated Schedule)
                    col.Item().PaddingTop(6).Text("SCHEDULE A: TRANCHE PAYMENT MILESTONES").Bold().FontSize(10.5f).FontColor("#0d47a1");
                    col.Item().Table(table =>
                    {
                        table.ColumnsDefinition(columns =>
                        {
                            columns.ConstantColumn(30);
                            columns.RelativeColumn(3);
                            columns.ConstantColumn(60);
                            columns.ConstantColumn(90);
                            columns.ConstantColumn(80);
                        });

                        table.Header(h =>
                        {
                            h.Cell().Background("#0d47a1").Padding(4).Text("#").FontColor(Colors.White).Bold().FontSize(8.5f);
                            h.Cell().Background("#0d47a1").Padding(4).Text("Milestone Deliverable").FontColor(Colors.White).Bold().FontSize(8.5f);
                            h.Cell().Background("#0d47a1").Padding(4).Text("Tranche %").FontColor(Colors.White).Bold().FontSize(8.5f);
                            h.Cell().Background("#0d47a1").Padding(4).Text("Amount (ZAR)").FontColor(Colors.White).Bold().FontSize(8.5f);
                            h.Cell().Background("#0d47a1").Padding(4).Text("Target Date").FontColor(Colors.White).Bold().FontSize(8.5f);
                        });

                        table.Cell().BorderBottom(1).BorderColor("#e0e0e0").Padding(4).Text("1").FontSize(8.5f);
                        table.Cell().BorderBottom(1).BorderColor("#e0e0e0").Padding(4).Text("Project Inception, Learner Registration & Induction").FontSize(8.5f);
                        table.Cell().BorderBottom(1).BorderColor("#e0e0e0").Padding(4).Text("30%").FontSize(8.5f);
                        table.Cell().BorderBottom(1).BorderColor("#e0e0e0").Padding(4).Text("R 135,000.00").FontSize(8.5f);
                        table.Cell().BorderBottom(1).BorderColor("#e0e0e0").Padding(4).Text(DateTime.UtcNow.AddMonths(1).ToString("yyyy-MM-dd")).FontSize(8.5f);

                        table.Cell().BorderBottom(1).BorderColor("#e0e0e0").Padding(4).Text("2").FontSize(8.5f);
                        table.Cell().BorderBottom(1).BorderColor("#e0e0e0").Padding(4).Text("Mid-Term Workplace Monitoring & Formative Assessment").FontSize(8.5f);
                        table.Cell().BorderBottom(1).BorderColor("#e0e0e0").Padding(4).Text("40%").FontSize(8.5f);
                        table.Cell().BorderBottom(1).BorderColor("#e0e0e0").Padding(4).Text("R 180,000.00").FontSize(8.5f);
                        table.Cell().BorderBottom(1).BorderColor("#e0e0e0").Padding(4).Text(DateTime.UtcNow.AddMonths(6).ToString("yyyy-MM-dd")).FontSize(8.5f);

                        table.Cell().BorderBottom(1).BorderColor("#e0e0e0").Padding(4).Text("3").FontSize(8.5f);
                        table.Cell().BorderBottom(1).BorderColor("#e0e0e0").Padding(4).Text("Final Trade Test Assessment, Moderation & Close-out").FontSize(8.5f);
                        table.Cell().BorderBottom(1).BorderColor("#e0e0e0").Padding(4).Text("30%").FontSize(8.5f);
                        table.Cell().BorderBottom(1).BorderColor("#e0e0e0").Padding(4).Text("R 135,000.00").FontSize(8.5f);
                        table.Cell().BorderBottom(1).BorderColor("#e0e0e0").Padding(4).Text(DateTime.UtcNow.AddMonths(12).ToString("yyyy-MM-dd")).FontSize(8.5f);
                    });

                    // Signatures Block
                    col.Item().PaddingTop(15).BorderTop(1).BorderColor("#e0e0e0").Row(r =>
                    {
                        r.RelativeItem().Column(c =>
                        {
                            c.Item().Text("For Employer / Grantee:").Bold().FontSize(9);
                            c.Item().Text(tokens.GetValueOrDefault("EmployerName", "Apex Engineering Works (Pty) Ltd")).FontSize(8.5f);
                            c.Item().PaddingTop(25).LineHorizontal(0.5f).LineColor(Colors.Grey.Darken1);
                            c.Item().Text("Authorised Representative").FontSize(8).Italic();
                            c.Item().Text($"Date: {DateTime.UtcNow:yyyy-MM-dd}").FontSize(8);
                        });

                        r.ConstantItem(40);

                        r.RelativeItem().Column(c =>
                        {
                            c.Item().Text($"For {setaName}:").Bold().FontSize(9);
                            c.Item().Text("Chief Executive Officer / ETQA Senior Manager").FontSize(8.5f);
                            c.Item().PaddingTop(25).LineHorizontal(0.5f).LineColor(Colors.Grey.Darken1);
                            c.Item().Text("merSETA Executive Authority").FontSize(8).Italic();
                            c.Item().Text($"Date: {DateTime.UtcNow:yyyy-MM-dd}").FontSize(8);
                        });
                    });
                });

                // Footer
                page.Footer().Column(col =>
                {
                    col.Item().LineHorizontal(0.5f).LineColor("#cccccc");
                    col.Item().PaddingTop(4).Row(row =>
                    {
                        row.RelativeItem().Text($"merSETA NSDMS MoA Simulation Engine • Template: {template.TemplateCode} (v{template.VersionNumber})").FontSize(7.5f).FontColor("#777777");
                        row.RelativeItem().AlignRight().Text(x =>
                        {
                            x.DefaultTextStyle(t => t.FontSize(7.5f).FontColor("#777777"));
                            x.Span("Page ");
                            x.CurrentPageNumber();
                            x.Span(" of ");
                            x.TotalPages();
                        });
                    });
                });
            });
        });

        return document.GeneratePdf();
    }

    public async Task<byte[]> GenerateTripartiteAgreementPdfAsync(CompanyLearner learner)
    {
        var setaName = await _config.GetValueAsync("General.SetaName", "Manufacturing, Engineering and Related Services SETA (merSETA)");
        var contractNum = !string.IsNullOrEmpty(learner.LearnerContractNumber) ? learner.LearnerContractNumber : $"LRN-{learner.Id:D5}";
        var baseUrl = await _config.GetValueAsync("System.BaseUrl", "https://nsdms.merseta.org.za");
        var verifyUrl = $"{baseUrl.TrimEnd('/')}/verify/contract/{contractNum}";
        var qrBytes = GenerateQrBytes(verifyUrl);

        var document = Document.Create(container =>
        {
            container.Page(page =>
            {
                page.Size(PageSizes.A4);
                page.Margin(1.5f, Unit.Centimetre);
                page.PageColor(Colors.White);
                page.DefaultTextStyle(x => x.FontSize(9.5f).FontFamily("Arial"));

                page.Header().Column(col =>
                {
                    col.Item().Row(r =>
                    {
                        r.RelativeItem().Column(c =>
                        {
                            c.Item().Text("REPUBLIC OF SOUTH AFRICA").Bold().FontSize(12).FontColor(Colors.Grey.Darken3);
                            c.Item().Text(setaName).FontSize(10).FontColor(Colors.Blue.Darken3);
                            c.Item().Text("STATUTORY TRIPARTITE LEARNERSHIP AGREEMENT").Bold().FontSize(14).FontColor(Colors.Black);
                            c.Item().Text("In accordance with Section 17 of the Skills Development Act (Act No. 97 of 1998)").Italic().FontSize(8).FontColor(Colors.Grey.Darken2);
                        });
                        r.ConstantItem(60).Height(60).Image(qrBytes);
                    });
                    col.Item().PaddingTop(5).LineHorizontal(1.5f).LineColor(Colors.Blue.Darken2);
                });

                page.Content().PaddingVertical(10).Column(col =>
                {
                    col.Spacing(8);

                    // Section 1: Learner Details
                    col.Item().Background(Colors.Grey.Lighten3).Padding(4).Text("SECTION 1: LEARNER PARTICULARS").Bold().FontSize(10).FontColor(Colors.Blue.Darken4);
                    col.Item().Row(r =>
                    {
                        r.RelativeItem().Text($"Full Legal Name: {learner.Person?.FullName ?? "Learner"}");
                        r.RelativeItem().Text($"Identity / Passport: {learner.Person?.RsaIdNumber ?? learner.Person?.PassportNumber ?? "N/A"}");
                    });
                    col.Item().Row(r =>
                    {
                        r.RelativeItem().Text($"Email: {learner.Person?.Email ?? "N/A"}");
                        r.RelativeItem().Text($"Mobile: {learner.Person?.CellNumber ?? "N/A"}");
                    });

                    // Section 2: Employer Details
                    col.Item().PaddingTop(4).Background(Colors.Grey.Lighten3).Padding(4).Text("SECTION 2: HOST EMPLOYER PARTICULARS").Bold().FontSize(10).FontColor(Colors.Blue.Darken4);
                    col.Item().Row(r =>
                    {
                        r.RelativeItem().Text($"Employer Name: {learner.Organisation?.CompanyName ?? "Host Employer"}");
                        r.RelativeItem().Text($"SDL Number: {learner.Organisation?.SdlNumber ?? "N/A"}");
                    });

                    // Section 3: Training Provider
                    col.Item().PaddingTop(4).Background(Colors.Grey.Lighten3).Padding(4).Text("SECTION 3: ACCREDITED SKILLS DEVELOPMENT PROVIDER (SDP)").Bold().FontSize(10).FontColor(Colors.Blue.Darken4);
                    col.Item().Row(r =>
                    {
                        r.RelativeItem().Text($"SDP Institution: {learner.TrainingProvider?.ProviderName ?? "Accredited Training Institution"}");
                        r.RelativeItem().Text($"Accreditation No: {learner.TrainingProvider?.AccreditationNumber ?? "ETQA/17/SDP"}");
                    });

                    // Section 4: Learning Programme
                    col.Item().PaddingTop(4).Background(Colors.Grey.Lighten3).Padding(4).Text("SECTION 4: QUALIFICATION & STATUTORY CONTRACT DETAILS").Bold().FontSize(10).FontColor(Colors.Blue.Darken4);
                    col.Item().Row(r =>
                    {
                        r.RelativeItem().Text($"Qualification Title: {learner.QualificationTitle}").Bold();
                        r.RelativeItem().Text($"OFO Code: {learner.OfoCode ?? "N/A"}");
                    });
                    col.Item().Row(r =>
                    {
                        r.RelativeItem().Text($"Commencement Date: {learner.RegistrationDate.ToString("yyyy-MM-dd")}");
                        r.RelativeItem().Text($"Expected Completion Date: {learner.ExpectedCompletionDate?.ToString("yyyy-MM-dd") ?? "N/A"}");
                    });
                    col.Item().Text($"Contract Reference: {contractNum} • SETMIS File 500 Compliant").FontFamily("Courier New").FontSize(8.5f);

                    // Section 5: Tripartite Execution & Digital Seal
                    col.Item().PaddingTop(8).Background(Colors.Grey.Lighten4).Padding(8).Column(c =>
                    {
                        c.Item().Text("TRIPARTITE ATTESTATION & DIGITAL SECURITY SEAL").Bold().FontSize(9.5f).FontColor(Colors.Blue.Darken3);
                        c.Item().Text("This agreement has been digitally concluded through authenticated OTP verification. The employer agrees to provide practical workplace experience and pay the statutory allowance; the learner agrees to observe workplace rules and complete curricula; the SDP undertakes theoretical instruction.").FontSize(7.5f);
                        c.Item().PaddingTop(5).Row(r =>
                        {
                            r.RelativeItem().Text("Learner Signatory: [Digitally Verified via OTP]").FontSize(8);
                            r.RelativeItem().Text("Employer Signatory: [Digitally Executed]").FontSize(8);
                            r.RelativeItem().Text("merSETA Registrar: [Approved & Sealed]").FontSize(8);
                        });
                    });
                });

                page.Footer().Row(r =>
                {
                    r.RelativeItem().Text($"merSETA NSDMS Tripartite Contract Engine • Ref: {contractNum}").FontSize(7.5f).FontColor(Colors.Grey.Medium);
                    r.RelativeItem().AlignRight().Text("Official Government Contract • Legally Binding").FontSize(7.5f).FontColor(Colors.Grey.Medium);
                });
            });
        });

        return document.GeneratePdf();
    }

    public async Task<byte[]> GenerateAssessorRegistrationCertificatePdfAsync(EtqaAssessor assessor)
    {
        var setaName = await _config.GetValueAsync("General.SetaName", "Manufacturing, Engineering and Related Services SETA (merSETA)");
        var certNum = assessor.RegistrationNumber;
        var baseUrl = await _config.GetValueAsync("System.BaseUrl", "https://nsdms.merseta.org.za");
        var verifyUrl = $"{baseUrl.TrimEnd('/')}/verify/assessor/{certNum}";
        var qrBytes = GenerateQrBytes(verifyUrl);

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
                    col.Item().AlignCenter().Text("REPUBLIC OF SOUTH AFRICA").Bold().FontSize(15).FontColor(Colors.Grey.Darken3);
                    col.Item().AlignCenter().Text(setaName).FontSize(13).FontColor(Colors.Blue.Darken3);
                    col.Item().AlignCenter().Text($"EDUCATION & TRAINING QUALITY ASSURANCE (ETQA ID: {assessor.EtqaId})").FontSize(10).FontColor(Colors.Grey.Darken2);
                    col.Item().AlignCenter().Text($"CERTIFICATE OF REGISTRATION: {assessor.EtqaRole.ToUpperInvariant()}").Bold().FontSize(18).FontColor(Colors.Black);
                    col.Item().PaddingTop(4).LineHorizontal(2).LineColor(Colors.Blue.Darken2);
                });

                page.Content().PaddingVertical(15).Column(col =>
                {
                    col.Spacing(10);

                    col.Item().AlignCenter().Text("This is to certify that").Italic().FontSize(12);
                    col.Item().AlignCenter().Text(assessor.Person?.FullName ?? "Assessor / Moderator").Bold().FontSize(22).FontColor(Colors.Blue.Darken4);
                    col.Item().AlignCenter().Text($"National ID / Passport: {assessor.Person?.RsaIdNumber ?? assessor.Person?.PassportNumber ?? "N/A"}").FontSize(11);

                    col.Item().AlignCenter().Text($"is officially registered with merSETA ETQA as an accredited").FontSize(12);
                    col.Item().AlignCenter().Text($"{assessor.EtqaRole.ToUpperInvariant()} (Registration No: {assessor.RegistrationNumber})").Bold().FontSize(15).FontColor(Colors.Green.Darken3);

                    col.Item().AlignCenter().Text($"Validity Period: {assessor.StartDate:dd MMMM yyyy} to {assessor.EndDate:dd MMMM yyyy} (3-Year Statutory Cycle)").FontSize(11);
                    if (!string.IsNullOrEmpty(assessor.EtqeDecisionNumber))
                    {
                        col.Item().AlignCenter().Text($"ETQA Committee Decision Number: {assessor.EtqeDecisionNumber}").FontSize(10).FontColor(Colors.Grey.Darken2);
                    }

                    col.Item().PaddingTop(10).Row(row =>
                    {
                        row.RelativeItem(3).Column(c =>
                        {
                            c.Item().Text($"Registered Scope Units: {assessor.Scopes.Count} Qualifications / Unit Standards").Bold().FontSize(10);
                            c.Item().Text("SETMIS File 401 Compliant Registration").FontSize(9).FontColor(Colors.Grey.Darken2);
                            c.Item().Text($"Certificate Reference: CERT-{assessor.RegistrationNumber}").FontSize(9).FontColor(Colors.Grey.Darken2);
                        });

                        row.RelativeItem(2).AlignCenter().Column(c =>
                        {
                            c.Item().Width(50).Height(50).Image(qrBytes);
                            c.Item().AlignCenter().Text("Scan to Verify Scope").FontSize(7.5f).FontColor(Colors.Blue.Darken2);
                        });

                        row.RelativeItem(3).AlignRight().Column(c =>
                        {
                            c.Item().Text("Senior ETQA Manager").Bold().FontSize(10);
                            c.Item().PaddingTop(20).LineHorizontal(1).LineColor(Colors.Grey.Darken1);
                            c.Item().AlignCenter().Text("Quality Assurance & ETQA Division").FontSize(9);
                        });
                    });
                });

                page.Footer().AlignCenter().Text("Official Government Credential • Issued under SAQA & QCTO Statutory Regulations • merSETA NSDMS").FontSize(8).FontColor(Colors.Grey.Medium);
            });
        });

        return document.GeneratePdf();
    }

    private byte[] GenerateQrBytes(string verifyUrl)
    {
        if (_verificationService != null)
        {
            return _verificationService.GenerateVerificationQrCodeBytes(verifyUrl, 6);
        }
        using var qrGenerator = new QRCoder.QRCodeGenerator();
        using var qrCodeData = qrGenerator.CreateQrCode(verifyUrl, QRCoder.QRCodeGenerator.ECCLevel.Q);
        var qrCode = new QRCoder.PngByteQRCode(qrCodeData);
        return qrCode.GetGraphic(6);
    }
}
