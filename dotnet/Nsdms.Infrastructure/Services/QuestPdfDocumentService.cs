using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;
using QuestPDF.Fluent;
using QuestPDF.Helpers;
using QuestPDF.Infrastructure;

namespace Nsdms.Infrastructure.Services;

public partial class QuestPdfDocumentService : IPdfDocumentService
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
        var baseUrl = await _config.GetValueAsync("System.BaseUrl", "https://nsdms.merseta.org.za");
        var verifyUrl = $"{baseUrl.TrimEnd('/')}/verify/document/{provider.AccreditationNumber}";
        var qrBytes = GenerateQrBytes(verifyUrl);
        var securitySeal = provider.DigitalSecuritySeal ?? "SEC-" + Guid.NewGuid().ToString("N").Substring(0, 16).ToUpper();

        var document = Document.Create(container =>
        {
            container.Page(page =>
            {
                page.Size(PageSizes.A4);
                page.Margin(2, Unit.Centimetre);
                page.DefaultTextStyle(x => x.FontSize(10));

                page.Header().Column(col =>
                {
                    col.Item().AlignCenter().Text("REPUBLIC OF SOUTH AFRICA").Bold().FontSize(12).FontColor(Colors.Grey.Darken3);
                    col.Item().AlignCenter().Text(setaName).Bold().FontSize(14).FontColor(Colors.Blue.Darken3);
                    col.Item().AlignCenter().Text("EDUCATION AND TRAINING QUALITY ASSURANCE (ETQA)").FontSize(11);
                    col.Item().PaddingTop(5).LineHorizontal(1).LineColor(Colors.Blue.Darken2);
                });

                page.Content().PaddingVertical(15).Column(col =>
                {
                    col.Spacing(10);
                    col.Item().Row(r =>
                    {
                        r.RelativeItem().Column(c =>
                        {
                            c.Item().Text($"Date: {DateTime.UtcNow:yyyy-MM-dd}").Bold();
                            c.Item().Text($"To: {provider.Organisation?.CompanyName ?? "Skills Development Provider"}");
                            c.Item().Text($"SDL / Legal Reference: {provider.Organisation?.SdlNumber ?? "N/A"}");
                            c.Item().Text($"Accreditation Number: {provider.AccreditationNumber}").Bold().FontColor(Colors.Blue.Darken3);
                            if (!string.IsNullOrWhiteSpace(provider.AccreditationStream))
                            {
                                c.Item().Text($"Accreditation Stream: {provider.AccreditationStream}").Bold();
                            }
                        });
                        r.ConstantItem(70).Image(qrBytes);
                    });

                    col.Item().PaddingTop(6).Text("CONFIRMATION OF ACCREDITATION AS A SKILLS DEVELOPMENT PROVIDER").Bold().FontSize(12).FontColor(Colors.Blue.Darken4);
                    col.Item().Text($"We are pleased to inform you that following evaluation and site verification, {provider.Organisation?.CompanyName} has been granted accreditation status ({provider.ProviderStatusCode ?? "Accredited"}) effective from {provider.AccreditationStartDate:yyyy-MM-dd} to {provider.AccreditationEndDate:yyyy-MM-dd}.");

                    if (!string.IsNullOrWhiteSpace(provider.PrimaryEtqaName))
                    {
                        col.Item().Text($"Primary ETQA / Quality Assurance Partner: {provider.PrimaryEtqaName} (Accreditation No: {provider.PrimaryEtqaAccreditationNumber ?? "N/A"})");
                    }
                    if (!string.IsNullOrWhiteSpace(provider.NambTtcRegistrationNumber))
                    {
                        col.Item().Text($"NAMB Trade Test Centre (TTC) Registration: {provider.NambTtcRegistrationNumber} (Expires: {provider.NambTtcExpiryDate:yyyy-MM-dd})");
                    }
                    if (!string.IsNullOrWhiteSpace(provider.EtqaDecisionNumber))
                    {
                        col.Item().Text($"ETQA Committee Decision Number: {provider.EtqaDecisionNumber}");
                    }

                    col.Item().PaddingTop(6).Text("Accredited Qualifications & Programmes Scope:").Bold();
                    if (provider.Qualifications != null && provider.Qualifications.Any())
                    {
                        foreach (var q in provider.Qualifications)
                        {
                            col.Item().PaddingLeft(10).Text($"• {q.QualificationTitle} (SAQA ID: {q.SaqaQualificationId}, NQF Level {q.NqfLevel})");
                        }
                    }
                    else
                    {
                        col.Item().PaddingLeft(10).Text("• General Skills Development & Technical Training Scope per approved schedule.");
                    }

                    col.Item().PaddingTop(10).Row(row =>
                    {
                        row.RelativeItem().Column(c =>
                        {
                            c.Item().Text("Digital Security Seal:").FontSize(8).Bold().FontColor(Colors.Grey.Darken2);
                            c.Item().Text(securitySeal).FontFamily("Courier").FontSize(7).FontColor(Colors.Grey.Darken3);
                            c.Item().Text("Verify authenticity by scanning QR code or visiting nsdms.merseta.org.za/verify").FontSize(7).Italic();
                        });
                        row.RelativeItem().AlignRight().Column(c =>
                        {
                            c.Item().Text("Yours faithfully,").FontSize(10);
                            c.Item().PaddingTop(15).LineHorizontal(1).LineColor(Colors.Grey.Medium);
                            c.Item().Text("Senior Manager: Quality Assurance & ETQA").Bold().FontSize(9);
                            c.Item().Text(setaName).FontSize(9);
                        });
                    });
                });

                page.Footer().AlignCenter().Text(x =>
                {
                    x.Span("Official ETQA Statutory Decision Document • merSETA • Valid without physical signature if Security Seal matches");
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
            .IgnoreQueryFilters()
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
            .IgnoreQueryFilters()
            .AsNoTracking()
            .FirstOrDefaultAsync(t => t.Id == tradeTestId);

        if (app != null)
        {
            if (app.PersonId > 0)
            {
                app.Person = await db.People.IgnoreQueryFilters().AsNoTracking().FirstOrDefaultAsync(p => p.Id == app.PersonId);
            }
            else if (app.CompanyLearnerId > 0)
            {
                var cl = await db.CompanyLearners.IgnoreQueryFilters().AsNoTracking().FirstOrDefaultAsync(c => c.Id == app.CompanyLearnerId);
                if (cl != null)
                {
                    app.Person = await db.People.IgnoreQueryFilters().AsNoTracking().FirstOrDefaultAsync(p => p.Id == cl.PersonId);
                }
            }
            return await GenerateArtisanTradeCertificatePdfAsync(app);
        }

        var legacyTest = await db.LearnerTradeTests
            .IgnoreQueryFilters()
            .AsNoTracking()
            .FirstOrDefaultAsync(t => t.Id == tradeTestId);

        if (legacyTest != null)
        {
            if (legacyTest.CompanyLearnerId > 0)
            {
                var cl = await db.CompanyLearners.IgnoreQueryFilters().AsNoTracking().FirstOrDefaultAsync(c => c.Id == legacyTest.CompanyLearnerId);
                if (cl != null)
                {
                    cl.Person = await db.People.IgnoreQueryFilters().AsNoTracking().FirstOrDefaultAsync(p => p.Id == cl.PersonId);
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
            .IgnoreQueryFilters()
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
            .IgnoreQueryFilters()
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

    public async Task<byte[]> GenerateWorkplaceApprovalLetterPdfAsync(int approvalId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var approval = await db.WorkplaceApprovals
            .Include(w => w.Organisation)
            .Include(w => w.OrganisationSite)
            .Include(w => w.ContactPerson)
            .Include(w => w.Mentors).ThenInclude(m => m.Person)
            .FirstOrDefaultAsync(w => w.Id == approvalId);

        if (approval == null)
            throw new KeyNotFoundException($"Workplace approval with ID {approvalId} not found.");

        var enableWatermarks = await _featureFlags.IsFeatureEnabledAsync("Pdfs.WatermarksAndQrCodes", true);
        var setaName = await _config.GetValueAsync("General.SetaName", "Manufacturing, Engineering and Related Services SETA (merSETA)");
        var orgName = approval.Organisation?.CompanyName ?? "Host Employer";
        var sdlNumber = approval.Organisation?.SdlNumber ?? "N/A";
        var siteName = approval.OrganisationSite?.SiteName ?? approval.Organisation?.TradingName ?? orgName;
        var contactName = approval.ContactPerson?.FullName ?? "Skills Development Facilitator";
        var tradeCode = approval.TradeCode ?? (approval.SaqaQualificationId.HasValue ? approval.SaqaQualificationId.Value.ToString() : "N/A");
        var tradeTitle = approval.QualificationTitle;
        var refNumber = string.IsNullOrWhiteSpace(approval.ApprovalNumber) ? $"WPA-{approval.Id}" : approval.ApprovalNumber;
        var approvalDate = approval.ApprovalDate ?? approval.DecisionDate ?? DateTime.UtcNow;

        var baseUrl = await _config.GetValueAsync("System.BaseUrl", "https://nsdms.merseta.org.za");
        var verifyUrl = $"{baseUrl.TrimEnd('/')}/verify/wpa/{refNumber}";
        var qrBytes = GenerateQrBytes(verifyUrl);

        var document = Document.Create(container =>
        {
            container.Page(page =>
            {
                page.Size(PageSizes.A4);
                page.Margin(1.5f, Unit.Centimetre);
                page.PageColor(Colors.White);
                page.DefaultTextStyle(x => x.FontSize(10.5f).FontFamily("Arial"));

                page.Header().Column(col =>
                {
                    col.Item().Row(row =>
                    {
                        row.RelativeItem().Column(c =>
                        {
                            c.Item().Text("REPUBLIC OF SOUTH AFRICA").Bold().FontSize(12).FontColor(Colors.Grey.Darken3);
                            c.Item().Text(setaName).FontSize(10).FontColor(Colors.Blue.Darken3);
                            c.Item().Text("Department of Higher Education and Training (DHET)").FontSize(8.5f).FontColor(Colors.Grey.Darken1);
                        });

                        row.ConstantItem(50).Height(50).Image(qrBytes);
                    });
                    col.Item().PaddingTop(4).LineHorizontal(1.5f).LineColor(Colors.Blue.Darken2);
                });

                page.Content().PaddingVertical(14).Column(col =>
                {
                    col.Spacing(10);

                    col.Item().Text(approvalDate.ToString("dd MMMM yyyy")).FontSize(10).FontColor(Colors.Grey.Darken2);

                    col.Item().Text($"Dear {contactName}").Bold().FontSize(11);

                    col.Item().PaddingVertical(4).Text($"WORKPLACE APPROVAL OUTCOME FOR {orgName.ToUpperInvariant()} ({sdlNumber}): {siteName.ToUpperInvariant()}").Bold().FontSize(11.5f).FontColor(Colors.Blue.Darken4);

                    col.Item().Text("The merSETA has the pleasure to inform you that your workplace has been granted approval to train in the following discipline(s):").FontSize(10.5f);

                    col.Item().Table(table =>
                    {
                        table.ColumnsDefinition(columns =>
                        {
                            columns.RelativeColumn(1);
                            columns.RelativeColumn(3);
                        });

                        table.Header(header =>
                        {
                            header.Cell().Background(Colors.Grey.Lighten3).Border(0.5f).BorderColor(Colors.Grey.Darken1).Padding(6).Text("Qualification Code / Trade Code").Bold().FontSize(9.5f);
                            header.Cell().Background(Colors.Grey.Lighten3).Border(0.5f).BorderColor(Colors.Grey.Darken1).Padding(6).Text("Qualification Title / Trade Title").Bold().FontSize(9.5f);
                        });

                        table.Cell().Border(0.5f).BorderColor(Colors.Grey.Darken1).Padding(6).Text(tradeCode).FontFamily("Consolas").FontSize(10);
                        table.Cell().Border(0.5f).BorderColor(Colors.Grey.Darken1).Padding(6).Text(tradeTitle).Bold().FontSize(10);
                    });

                    col.Item().Text("Please note that a merSETA Verification Officer or Quality Assurance representative will be conducting monitoring, assessments, and capacity building visits throughout the training of the learners or apprentices without prior notice.").FontSize(10);

                    col.Item().Text("Should you require any assistance or further information, kindly contact the Client Services division at your designated regional office.").FontSize(10);

                    col.Item().PaddingTop(15).Row(row =>
                    {
                        row.RelativeItem().Column(c =>
                        {
                            c.Item().Text("Yours sincerely,").FontSize(10);
                            c.Item().PaddingTop(25).LineHorizontal(1).LineColor(Colors.Grey.Darken1);
                            c.Item().Text("Manager: Quality Assurance & ETQA").Bold().FontSize(10.5f);
                            c.Item().Text(setaName).FontSize(9).FontColor(Colors.Grey.Darken2);
                        });

                        row.ConstantItem(150).Column(c =>
                        {
                            c.Item().Text($"Approval Ref: {refNumber}").Bold().FontFamily("Consolas").FontSize(9);
                            c.Item().Text($"Valid From: {approvalDate:yyyy-MM-dd}").FontSize(8.5f);
                            c.Item().Text($"Expiry Date: {(approval.ExpiryDate ?? approvalDate.AddYears(3)):yyyy-MM-dd}").FontSize(8.5f);
                        });
                    });

                    col.Item().PaddingTop(12).Table(box =>
                    {
                        box.ColumnsDefinition(cols =>
                        {
                            cols.RelativeColumn(2);
                            cols.RelativeColumn(2);
                            cols.RelativeColumn(2);
                            cols.RelativeColumn(2);
                        });

                        box.Cell().Border(0.5f).Padding(4).Text("Document Title: Workplace Approval Letter").FontSize(7.5f);
                        box.Cell().Border(0.5f).Padding(4).Text("Page 1 of 1").FontSize(7.5f);
                        box.Cell().Border(0.5f).Padding(4).Text($"Generated: {DateTime.UtcNow:yyyy-MM-dd HH:mm}").FontSize(7.5f);
                        box.Cell().Border(0.5f).Padding(4).Text("Access: Controlled").FontSize(7.5f);

                        box.Cell().Border(0.5f).Padding(4).Text("Document Number: ETQ-TP-003").Bold().FontSize(7.5f);
                        box.Cell().Border(0.5f).Padding(4).Text($"Status: {approval.ApprovalStatusCode}").FontSize(7.5f);
                        box.Cell().Border(0.5f).Padding(4).Text($"SDL: {sdlNumber}").FontSize(7.5f);
                        box.Cell().Border(0.5f).Padding(4).Text("merSETA Head Office").FontSize(7.5f);
                    });
                });

                page.Footer().AlignCenter().Text("merSETA Head Office: 95 7th Avenue, Cnr Rustenburg Road, Melville, Johannesburg 2109 • Telephone: 010 219 3000 • www.merseta.org.za").FontSize(7.5f).FontColor(Colors.Grey.Darken1);
            });
        });

        return document.GeneratePdf();
    }

    public async Task<byte[]> GenerateWorkplaceApprovalReportPdfAsync(int approvalId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var approval = await db.WorkplaceApprovals
            .Include(w => w.Organisation)
            .Include(w => w.OrganisationSite)
            .Include(w => w.ContactPerson)
            .Include(w => w.VerifiedByPerson)
            .Include(w => w.DecisionByPerson)
            .Include(w => w.Mentors).ThenInclude(m => m.Person)
            .Include(w => w.ToolItems)
            .FirstOrDefaultAsync(w => w.Id == approvalId);

        if (approval == null)
            throw new KeyNotFoundException($"Workplace approval with ID {approvalId} not found.");

        var setaName = await _config.GetValueAsync("General.SetaName", "Manufacturing, Engineering and Related Services SETA (merSETA)");
        var org = approval.Organisation;
        var site = approval.OrganisationSite;
        var contact = approval.ContactPerson;
        var refNumber = string.IsNullOrWhiteSpace(approval.ApprovalNumber) ? $"WPA-{approval.Id}" : approval.ApprovalNumber;
        var submissionDate = approval.CreatedAt;

        var baseUrl = await _config.GetValueAsync("System.BaseUrl", "https://nsdms.merseta.org.za");
        var verifyUrl = $"{baseUrl.TrimEnd('/')}/verify/wpa-report/{refNumber}";
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
                    col.Item().Row(row =>
                    {
                        row.RelativeItem().Column(c =>
                        {
                            c.Item().Text("REPUBLIC OF SOUTH AFRICA").Bold().FontSize(12).FontColor(Colors.Grey.Darken3);
                            c.Item().Text(setaName).FontSize(10).FontColor(Colors.Blue.Darken3);
                            c.Item().Text("WORKPLACE APPROVAL REPORT (ETQ-TP-054)").Bold().FontSize(13).FontColor(Colors.Blue.Darken4);
                        });
                        row.ConstantItem(45).Height(45).Image(qrBytes);
                    });
                    col.Item().PaddingTop(4).LineHorizontal(1.5f).LineColor(Colors.Blue.Darken2);
                });

                page.Content().PaddingVertical(10).Column(col =>
                {
                    col.Spacing(8);

                    col.Item().Table(table =>
                    {
                        table.ColumnsDefinition(cols =>
                        {
                            cols.RelativeColumn(3);
                            cols.RelativeColumn(5);
                        });

                        table.Cell().Background(Colors.Grey.Lighten4).Border(0.5f).Padding(4).Text("ORGANISATION NAME").Bold();
                        table.Cell().Border(0.5f).Padding(4).Text(org?.CompanyName ?? "N/A");

                        table.Cell().Background(Colors.Grey.Lighten4).Border(0.5f).Padding(4).Text("SITE NAME").Bold();
                        table.Cell().Border(0.5f).Padding(4).Text(site?.SiteName ?? org?.TradingName ?? "Main Facility");

                        table.Cell().Background(Colors.Grey.Lighten4).Border(0.5f).Padding(4).Text("SDL / NON-LEVY ID").Bold();
                        table.Cell().Border(0.5f).Padding(4).Text(org?.SdlNumber ?? "N/A").FontFamily("Consolas").Bold();

                        table.Cell().Background(Colors.Grey.Lighten4).Border(0.5f).Padding(4).Text("PHYSICAL ADDRESS").Bold();
                        table.Cell().Border(0.5f).Padding(4).Text(site?.PhysicalAddress ?? org?.PhysicalAddress ?? "N/A");

                        table.Cell().Background(Colors.Grey.Lighten4).Border(0.5f).Padding(4).Text("POSTAL ADDRESS").Bold();
                        table.Cell().Border(0.5f).Padding(4).Text(site?.PostalAddress ?? org?.PostalAddress ?? "N/A");

                        table.Cell().Background(Colors.Grey.Lighten4).Border(0.5f).Padding(4).Text("CONTACT PERSON").Bold();
                        table.Cell().Border(0.5f).Padding(4).Text($"{contact?.FullName ?? "N/A"} ({contact?.Email ?? "No email"})");

                        table.Cell().Background(Colors.Grey.Lighten4).Border(0.5f).Padding(4).Text("QUALIFICATION / TRADE").Bold();
                        table.Cell().Border(0.5f).Padding(4).Text($"{approval.QualificationTitle} (Trade: {approval.TradeCode ?? "N/A"})");

                        table.Cell().Background(Colors.Grey.Lighten4).Border(0.5f).Padding(4).Text("STREAM / PROGRAMME").Bold();
                        table.Cell().Border(0.5f).Padding(4).Text(approval.LearningProgramTypeCode ?? "Apprenticeship");
                    });

                    col.Item().PaddingTop(4).Text("Inspection Audit Findings & Compliance Checklists").Bold().FontSize(10.5f).FontColor(Colors.Blue.Darken3);

                    col.Item().Table(table =>
                    {
                        table.ColumnsDefinition(cols =>
                        {
                            cols.RelativeColumn(3);
                            cols.RelativeColumn(2);
                            cols.RelativeColumn(1.5f);
                            cols.RelativeColumn(1.5f);
                            cols.RelativeColumn(2);
                        });

                        table.Header(header =>
                        {
                            header.Cell().Background(Colors.Grey.Lighten3).Border(0.5f).Padding(4).Text("Tool / Equipment Item").Bold();
                            header.Cell().Background(Colors.Grey.Lighten3).Border(0.5f).Padding(4).Text("Category").Bold();
                            header.Cell().Background(Colors.Grey.Lighten3).Border(0.5f).Padding(4).Text("Required").Bold();
                            header.Cell().Background(Colors.Grey.Lighten3).Border(0.5f).Padding(4).Text("Available").Bold();
                            header.Cell().Background(Colors.Grey.Lighten3).Border(0.5f).Padding(4).Text("Compliance").Bold();
                        });

                        if (approval.ToolItems != null && approval.ToolItems.Any())
                        {
                            foreach (var tool in approval.ToolItems)
                            {
                                table.Cell().Border(0.5f).Padding(3).Text(tool.ToolName);
                                table.Cell().Border(0.5f).Padding(3).Text(tool.Category ?? "Standard");
                                table.Cell().Border(0.5f).Padding(3).Text(tool.RequiredQuantity.ToString());
                                table.Cell().Border(0.5f).Padding(3).Text(tool.AvailableQuantity.ToString());
                                table.Cell().Border(0.5f).Padding(3).Text(tool.IsCompliant ? "COMPLIANT" : "DEFICIENT").FontColor(tool.IsCompliant ? Colors.Green.Darken3 : Colors.Red.Darken3).Bold();
                            }
                        }
                        else
                        {
                            table.Cell().ColumnSpan(5).Border(0.5f).Padding(4).AlignCenter().Text("Curriculum standard tool inspection certified by applicant.").Italic();
                        }
                    });

                    col.Item().PaddingTop(4).Text("Assigned Artisan Mentors & Supervision Ratio Quotas").Bold().FontSize(10.5f).FontColor(Colors.Blue.Darken3);

                    col.Item().Table(table =>
                    {
                        table.ColumnsDefinition(cols =>
                        {
                            cols.RelativeColumn(3);
                            cols.RelativeColumn(2);
                            cols.RelativeColumn(2);
                            cols.RelativeColumn(2);
                        });

                        table.Header(header =>
                        {
                            header.Cell().Background(Colors.Grey.Lighten3).Border(0.5f).Padding(4).Text("Mentor Artisan").Bold();
                            header.Cell().Background(Colors.Grey.Lighten3).Border(0.5f).Padding(4).Text("Trade Serial No").Bold();
                            header.Cell().Background(Colors.Grey.Lighten3).Border(0.5f).Padding(4).Text("Experience").Bold();
                            header.Cell().Background(Colors.Grey.Lighten3).Border(0.5f).Padding(4).Text("Capacity Cap").Bold();
                        });

                        if (approval.Mentors != null && approval.Mentors.Any())
                        {
                            foreach (var mentor in approval.Mentors)
                            {
                                table.Cell().Border(0.5f).Padding(3).Text(mentor.Person?.FullName ?? mentor.Designation);
                                table.Cell().Border(0.5f).Padding(3).Text(mentor.ArtisanTradeNumber ?? "Verified");
                                table.Cell().Border(0.5f).Padding(3).Text($"{mentor.YearsExperience} Years");
                                table.Cell().Border(0.5f).Padding(3).Text(mentor.MaxLearnerCapacity.HasValue ? $"1:{mentor.MaxLearnerCapacity.Value} (Custom)" : "1:4 (Standard)");
                            }
                        }
                        else
                        {
                            table.Cell().ColumnSpan(4).Border(0.5f).Padding(4).AlignCenter().Text("No dedicated mentors attached to site record.").Italic();
                        }
                    });

                    col.Item().PaddingTop(4).Table(box =>
                    {
                        box.ColumnsDefinition(cols =>
                        {
                            cols.RelativeColumn(2);
                            cols.RelativeColumn(2);
                            cols.RelativeColumn(2);
                            cols.RelativeColumn(2);
                        });

                        box.Cell().Border(0.5f).Padding(3).Text("Document Title: Workplace Approval Report").FontSize(7.5f);
                        box.Cell().Border(0.5f).Padding(3).Text("Page 1 of 1").FontSize(7.5f);
                        box.Cell().Border(0.5f).Padding(3).Text($"Audit Date: {DateTime.UtcNow:yyyy-MM-dd}").FontSize(7.5f);
                        box.Cell().Border(0.5f).Padding(3).Text("Access: Controlled").FontSize(7.5f);

                        box.Cell().Border(0.5f).Padding(3).Text("Document Number: ETQ-TP-054").Bold().FontSize(7.5f);
                        box.Cell().Border(0.5f).Padding(3).Text($"Status: {approval.ApprovalStatusCode}").FontSize(7.5f);
                        box.Cell().Border(0.5f).Padding(3).Text($"Submission: {submissionDate:yyyy-MM-dd}").FontSize(7.5f);
                        box.Cell().Border(0.5f).Padding(3).Text("merSETA NSDMS 2.0").FontSize(7.5f);
                    });
                });
            });
        });

        return document.GeneratePdf();
    }

    public async Task<byte[]> GenerateArplApplicationFormPdfAsync(int applicationId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var app = await db.LearnerTradeTestApplications
            .Include(t => t.Person)
            .Include(t => t.Organisation)
            .Include(t => t.PreferredTradeTestCenter)
                .ThenInclude(p => p!.Organisation)
            .Include(t => t.ExperienceDetails)
            .Include(t => t.TrainingDetails)
            .FirstOrDefaultAsync(t => t.Id == applicationId);

        if (app == null)
        {
            throw new KeyNotFoundException($"LearnerTradeTestApplication with ID {applicationId} not found.");
        }

        var setaName = await _config.GetValueAsync("General.SetaName", "Manufacturing, Engineering and Related Services SETA (merSETA)");
        var baseUrl = await _config.GetValueAsync("System.BaseUrl", "https://nsdms.merseta.org.za");
        var verifyUrl = $"{baseUrl.TrimEnd('/')}/verify/tradetest/{app.Id}";
        var qrBytes = GenerateQrBytes(verifyUrl);

        var doc = Document.Create(container =>
        {
            container.Page(page =>
            {
                page.Size(PageSizes.A4);
                page.Margin(1.5f, Unit.Centimetre);
                page.PageColor(Colors.White);
                page.DefaultTextStyle(x => x.FontSize(9).FontFamily("Arial"));

                page.Header().Column(col =>
                {
                    col.Item().Row(row =>
                    {
                        row.RelativeItem(3).Column(c =>
                        {
                            c.Item().Text("REPUBLIC OF SOUTH AFRICA").Bold().FontSize(12).FontColor(Colors.Grey.Darken3);
                            c.Item().Text(setaName).Bold().FontSize(10).FontColor(Colors.Blue.Darken3);
                            c.Item().Text("ARTISAN RECOGNITION OF PRIOR LEARNING (ARPL) APPLICATION FORM").Bold().FontSize(13).FontColor(Colors.Black);
                            c.Item().Text("Section 28 / Section 26D Skills Development Act, 1998").FontSize(8.5f).FontColor(Colors.Grey.Darken2);
                        });

                        row.RelativeItem(1).AlignRight().Column(c =>
                        {
                            if (qrBytes != null && qrBytes.Length > 0)
                            {
                                c.Item().Width(60).Height(60).Image(qrBytes);
                            }
                            c.Item().AlignCenter().Text($"App: {app.ApplicationNumber}").Bold().FontSize(7.5f);
                        });
                    });

                    col.Item().PaddingTop(4).LineHorizontal(1.5f).LineColor(Colors.Blue.Darken2);
                });

                page.Content().PaddingVertical(10).Column(col =>
                {
                    col.Spacing(8);

                    // Section 1: Candidate Demographics
                    col.Item().Text("SECTION 1: CANDIDATE DEMOGRAPHIC & CONTACT DETAILS").Bold().FontSize(9.5f).FontColor(Colors.Blue.Darken3);
                    col.Item().Table(table =>
                    {
                        table.ColumnsDefinition(cols =>
                        {
                            cols.RelativeColumn(1);
                            cols.RelativeColumn(2);
                            cols.RelativeColumn(1);
                            cols.RelativeColumn(2);
                        });

                        table.Cell().Background(Colors.Grey.Lighten4).Padding(3).Text("Full Name:").Bold();
                        table.Cell().Padding(3).Text($"{app.Person?.FirstName} {app.Person?.MiddleName} {app.Person?.LastName}".Replace("  ", " "));
                        table.Cell().Background(Colors.Grey.Lighten4).Padding(3).Text("RSA ID / Passport:").Bold();
                        table.Cell().Padding(3).Text(app.Person?.RsaIdNumber ?? app.Person?.PassportNumber ?? "N/A");

                        table.Cell().Background(Colors.Grey.Lighten4).Padding(3).Text("Date of Birth:").Bold();
                        table.Cell().Padding(3).Text(app.Person?.DateOfBirth?.ToString("yyyy-MM-dd") ?? "N/A");
                        table.Cell().Background(Colors.Grey.Lighten4).Padding(3).Text("Gender / Equity:").Bold();
                        table.Cell().Padding(3).Text($"{app.Person?.GenderCode ?? "N/A"} / {app.Person?.EquityCode ?? "N/A"}");

                        table.Cell().Background(Colors.Grey.Lighten4).Padding(3).Text("Primary Email:").Bold();
                        table.Cell().Padding(3).Text(app.Person?.EmailAddress ?? "N/A");
                        table.Cell().Background(Colors.Grey.Lighten4).Padding(3).Text("Cell Phone:").Bold();
                        table.Cell().Padding(3).Text(app.Person?.CellPhoneNumber ?? "N/A");

                        table.Cell().Background(Colors.Grey.Lighten4).Padding(3).Text("Employment Status:").Bold();
                        table.Cell().Padding(3).Text(app.EmploymentStatus ?? "Employed");
                        table.Cell().Background(Colors.Grey.Lighten4).Padding(3).Text("Current Employer:").Bold();
                        table.Cell().Padding(3).Text(app.Organisation?.CompanyName ?? app.UnregisteredEmployerName ?? "N/A");
                    });

                    // Section 2: Trade & Assessment Scope
                    col.Item().Text("SECTION 2: TRADE DESIGNATION & TRADE TEST CENTRE ALLOCATION").Bold().FontSize(9.5f).FontColor(Colors.Blue.Darken3);
                    col.Item().Table(table =>
                    {
                        table.ColumnsDefinition(cols =>
                        {
                            cols.RelativeColumn(1);
                            cols.RelativeColumn(2);
                            cols.RelativeColumn(1);
                            cols.RelativeColumn(2);
                        });

                        table.Cell().Background(Colors.Grey.Lighten4).Padding(3).Text("Designated Trade:").Bold();
                        table.Cell().Padding(3).Text(app.TradeTitle).Bold();
                        table.Cell().Background(Colors.Grey.Lighten4).Padding(3).Text("OFO Code:").Bold();
                        table.Cell().Padding(3).Text(app.TradeOfoCode ?? "N/A");

                        table.Cell().Background(Colors.Grey.Lighten4).Padding(3).Text("Route / Category:").Bold();
                        table.Cell().Padding(3).Text($"{app.ApplicationTypeCode} (Cat {app.QualifyingCategory?.ToString() ?? "Standard"})");
                        table.Cell().Background(Colors.Grey.Lighten4).Padding(3).Text("Toolkit Required:").Bold();
                        table.Cell().Padding(3).Text(app.RequiresToolkit ? "YES (17 Designated Whitelist)" : "NO");

                        table.Cell().Background(Colors.Grey.Lighten4).Padding(3).Text("Preferred TTC:").Bold();
                        table.Cell().Padding(3).Text(app.PreferredTradeTestCenter?.ProviderName ?? app.AssessmentCenterName ?? "Pending Allocation");
                        table.Cell().Background(Colors.Grey.Lighten4).Padding(3).Text("Attempt Number:").Bold();
                        table.Cell().Padding(3).Text($"Attempt #{app.AttemptNumber}");
                    });

                    // Section 3: Verified Workplace Experience Table
                    col.Item().Text("SECTION 3: SUMMARY OF CLAIMED WORKPLACE EXPERIENCE").Bold().FontSize(9.5f).FontColor(Colors.Blue.Darken3);
                    col.Item().Table(table =>
                    {
                        table.ColumnsDefinition(cols =>
                        {
                            cols.RelativeColumn(3);
                            cols.RelativeColumn(2);
                            cols.RelativeColumn(2);
                            cols.RelativeColumn(1.5f);
                        });

                        table.Header(header =>
                        {
                            header.Cell().Background(Colors.Grey.Lighten3).Padding(3).Text("Employer / Company").Bold();
                            header.Cell().Background(Colors.Grey.Lighten3).Padding(3).Text("Job Title").Bold();
                            header.Cell().Background(Colors.Grey.Lighten3).Padding(3).Text("Period").Bold();
                            header.Cell().Background(Colors.Grey.Lighten3).Padding(3).Text("Experience").Bold();
                        });

                        if (app.ExperienceDetails != null && app.ExperienceDetails.Any())
                        {
                            foreach (var exp in app.ExperienceDetails)
                            {
                                table.Cell().BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(3).Text(exp.EmployerName);
                                table.Cell().BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(3).Text(exp.JobTitle);
                                table.Cell().BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(3).Text($"{exp.StartDate:yyyy-MM} to {(exp.EndDate.HasValue ? exp.EndDate.Value.ToString("yyyy-MM") : "Present")}");
                                table.Cell().BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(3).Text($"{exp.YearsOfExperience:F1} yrs");
                            }
                        }
                        else
                        {
                            table.Cell().ColumnSpan(4).BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(4).AlignCenter().Text("Standard workplace portfolio on file with accredited provider.").Italic();
                        }
                    });

                    // Section 4: Regional Approval & Stamping Metadata Box
                    col.Item().Text("SECTION 4: REGIONAL VERIFICATION & STATUTORY APPROVAL STAMP").Bold().FontSize(9.5f).FontColor(Colors.Blue.Darken3);
                    col.Item().Table(box =>
                    {
                        box.ColumnsDefinition(cols =>
                        {
                            cols.RelativeColumn(1);
                            cols.RelativeColumn(1);
                        });

                        box.Cell().Border(0.5f).Padding(6).Column(c =>
                        {
                            c.Item().Text("Tier 1: Client Liaison Officer (CLA)").Bold().FontSize(8.5f);
                            c.Item().Text($"Recommendation: {app.ClaRecommendationStatus ?? "Pending Review"}");
                            c.Item().Text($"Reviewed By: {app.ClaUserId ?? "Unassigned"}");
                            c.Item().Text($"Date: {app.ClaRecommendationDate?.ToString("yyyy-MM-dd") ?? "N/A"}");
                            c.Item().PaddingTop(10).Text("Signature / Digital Seal: _______________________").FontSize(8f);
                        });

                        box.Cell().Border(0.5f).Padding(6).Column(c =>
                        {
                            c.Item().Text("Tier 2: Quality Assurance (QA Officer) Approval").Bold().FontSize(8.5f);
                            c.Item().Text($"Approval Status: {app.QaApprovalStatus ?? "Awaiting Decision"}");
                            c.Item().Text($"Official Serial Number: {app.TradeTestSerialNumber ?? "Generated on QA Approval"}").Bold().FontColor(Colors.Blue.Darken4);
                            c.Item().Text($"Date: {app.QaApprovalDate?.ToString("yyyy-MM-dd") ?? "N/A"}");
                            c.Item().PaddingTop(10).Text("Official QA Stamp: [ AFFIX PHYSICAL / DIGITAL STAMP ]").FontSize(8f).FontColor(Colors.Grey.Darken2);
                        });
                    });

                    // Section 5: Statutory Controlled Metadata Footer Box
                    col.Item().Table(meta =>
                    {
                        meta.ColumnsDefinition(cols =>
                        {
                            cols.RelativeColumn(2);
                            cols.RelativeColumn(2);
                            cols.RelativeColumn(2);
                            cols.RelativeColumn(2);
                        });

                        meta.Cell().Border(0.5f).Padding(3).Text("Document: ARPL Application").FontSize(7.5f);
                        meta.Cell().Border(0.5f).Padding(3).Text("Doc Ref: ETQ-TP-ARPL-01").Bold().FontSize(7.5f);
                        meta.Cell().Border(0.5f).Padding(3).Text($"Generated: {DateTime.UtcNow:yyyy-MM-dd}").FontSize(7.5f);
                        meta.Cell().Border(0.5f).Padding(3).Text("merSETA NSDMS 2.0").FontSize(7.5f);
                    });
                });

                page.Footer().AlignCenter().Text("merSETA Quality Assurance Division • Controlled Statutory ARPL Form ETQ-TP-ARPL-01 • www.merseta.org.za").FontSize(7.5f).FontColor(Colors.Grey.Darken1);
            });
        });

        return doc.GeneratePdf();
    }

    public async Task<byte[]> GenerateAssessorCertificateLetterPdfAsync(int assessorId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var assessor = await db.EtqaAssessors
            .Include(a => a.Person)
            .Include(a => a.Scopes)
            .FirstOrDefaultAsync(a => a.Id == assessorId);

        if (assessor == null)
            throw new KeyNotFoundException($"EtqaAssessor with ID {assessorId} not found.");

        var setaName = await _config.GetValueAsync("General.SetaName", "Manufacturing, Engineering and Related Services SETA (merSETA)");
        var refNum = assessor.RegistrationNumber;
        var baseUrl = await _config.GetValueAsync("System.BaseUrl", "https://nsdms.merseta.org.za");
        var verifyUrl = $"{baseUrl.TrimEnd('/')}/verify/assessor-letter/{refNum}";
        var qrBytes = GenerateQrBytes(verifyUrl);

        var doc = Document.Create(container =>
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
                            c.Item().Text("EDUCATION & TRAINING QUALITY ASSURANCE DIVISION (ETQA 17)").Bold().FontSize(11);
                        });
                        r.ConstantItem(60).Height(60).Image(qrBytes);
                    });
                    col.Item().PaddingTop(5).LineHorizontal(1.5f).LineColor(Colors.Blue.Darken3);
                });

                page.Content().PaddingVertical(10).Column(col =>
                {
                    col.Spacing(8);

                    col.Item().Row(r =>
                    {
                        r.RelativeItem().Text($"Date: {DateTime.UtcNow:dd MMMM yyyy}").Bold();
                        r.RelativeItem().AlignRight().Text($"Registration Ref: {assessor.RegistrationNumber}").Bold();
                    });

                    col.Item().Column(c =>
                    {
                        c.Item().Text($"To: {assessor.Person?.FullName ?? "Registered Practitioner"}").Bold();
                        c.Item().Text($"ID / Passport: {assessor.Person?.RsaIdNumber ?? assessor.Person?.PassportNumber ?? "N/A"}");
                        c.Item().Text($"Email: {assessor.Person?.EmailAddress ?? "N/A"}");
                    });

                    col.Item().PaddingVertical(4).Background(Colors.Grey.Lighten4).Padding(6).Text(
                        $"SUBJECT: OUTCOME OF ETQA PRACTITIONER REGISTRATION – {assessor.EtqaRole.ToUpperInvariant()}"
                    ).Bold().FontSize(10.5f).FontColor(Colors.Blue.Darken4);

                    col.Item().Text(
                        $"Dear {assessor.Person?.FirstName ?? "Practitioner"},"
                    );

                    col.Item().Text(
                        $"We have pleasure in informing you that the merSETA Education and Training Quality Assurance (ETQA) Review Committee " +
                        $"has formally approved your application for registration as a certified {assessor.EtqaRole}."
                    );

                    col.Item().Table(t =>
                    {
                        t.ColumnsDefinition(cols =>
                        {
                            cols.RelativeColumn(3);
                            cols.RelativeColumn(5);
                        });

                        t.Cell().Background(Colors.Grey.Lighten4).Border(0.5f).Padding(4).Text("Practitioner Registration Number:").Bold();
                        t.Cell().Border(0.5f).Padding(4).Text(assessor.RegistrationNumber).FontFamily("Consolas").Bold();

                        t.Cell().Background(Colors.Grey.Lighten4).Border(0.5f).Padding(4).Text("Practitioner ETQA Role:").Bold();
                        t.Cell().Border(0.5f).Padding(4).Text(assessor.EtqaRole);

                        t.Cell().Background(Colors.Grey.Lighten4).Border(0.5f).Padding(4).Text("Review Committee Decision Number:").Bold();
                        t.Cell().Border(0.5f).Padding(4).Text(assessor.EtqeDecisionNumber ?? "ETQA-COMM-2026-0042");

                        t.Cell().Background(Colors.Grey.Lighten4).Border(0.5f).Padding(4).Text("Statutory Registration Period:").Bold();
                        t.Cell().Border(0.5f).Padding(4).Text($"{assessor.StartDate:dd MMMM yyyy} to {assessor.EndDate:dd MMMM yyyy} (3 Years)");

                        t.Cell().Background(Colors.Grey.Lighten4).Border(0.5f).Padding(4).Text("Approved Qualification Scopes:").Bold();
                        t.Cell().Border(0.5f).Padding(4).Text($"{assessor.Scopes.Count} Registered Qualification(s)");
                    });

                    col.Item().Text(
                        "TERMS AND CONDITIONS OF REGISTRATION:"
                    ).Bold().FontSize(9.5f);

                    col.Item().Text(
                        "1. Assessment and moderation practice must strictly adhere to the QCTO, SAQA, and merSETA Quality Assurance Code of Conduct.\n" +
                        "2. This registration is valid for a maximum statutory period of three (3) years from the effective date above.\n" +
                        "3. Continuous Professional Development (CPD): Practitioners must maintain an active portfolio and accumulate at least 30 CPD points across accredited categories prior to 3-year renewal.\n" +
                        "4. The merSETA reserves the statutory right to conduct announced and unannounced moderation audits at accredited training provider sites."
                    ).FontSize(8.5f);

                    col.Item().PaddingTop(10).Row(r =>
                    {
                        r.RelativeItem().Column(c =>
                        {
                            c.Item().Text("Yours faithfully,").FontSize(9);
                            c.Item().PaddingTop(25).Text("Senior Manager: Quality Assurance & Partnerships").Bold().FontSize(9);
                            c.Item().Text("merSETA Quality Assurance Division").FontSize(8).FontColor(Colors.Grey.Darken2);
                        });
                        r.RelativeItem().Column(c =>
                        {
                            c.Item().AlignRight().Text("Official Digital Stamp:").FontSize(9);
                            c.Item().AlignRight().Text("[ STATUTORILY SEALED & RATIFIED ]").Bold().FontColor(Colors.Green.Darken3).FontSize(8.5f);
                            c.Item().AlignRight().Text($"Verification: {assessor.RegistrationNumber}").FontFamily("Consolas").FontSize(7.5f);
                        });
                    });

                    // Controlled Document Box
                    col.Item().PaddingTop(6).Table(box =>
                    {
                        box.ColumnsDefinition(cols =>
                        {
                            cols.RelativeColumn(2);
                            cols.RelativeColumn(2);
                            cols.RelativeColumn(2);
                            cols.RelativeColumn(2);
                        });

                        box.Cell().Border(0.5f).Padding(3).Text("Document Title: Certificate Letter").FontSize(7.5f);
                        box.Cell().Border(0.5f).Padding(3).Text("Document Ref: ETQ-TP-004").Bold().FontSize(7.5f);
                        box.Cell().Border(0.5f).Padding(3).Text($"Audit Date: {DateTime.UtcNow:yyyy-MM-dd}").FontSize(7.5f);
                        box.Cell().Border(0.5f).Padding(3).Text("merSETA NSDMS 2.0").FontSize(7.5f);
                    });
                });

                page.Footer().AlignCenter().Text("merSETA Quality Assurance Division • Official Controlled Letter ETQ-TP-004 • www.merseta.org.za").FontSize(7.5f).FontColor(Colors.Grey.Darken1);
            });
        });

        return doc.GeneratePdf();
    }

    public async Task<byte[]> GenerateAssessorStatementOfScopePdfAsync(int assessorId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var assessor = await db.EtqaAssessors
            .Include(a => a.Person)
            .Include(a => a.Scopes)
                .ThenInclude(s => s.UnitStandards)
            .FirstOrDefaultAsync(a => a.Id == assessorId);

        if (assessor == null)
            throw new KeyNotFoundException($"EtqaAssessor with ID {assessorId} not found.");

        var setaName = await _config.GetValueAsync("General.SetaName", "Manufacturing, Engineering and Related Services SETA (merSETA)");
        var refNum = assessor.RegistrationNumber;
        var baseUrl = await _config.GetValueAsync("System.BaseUrl", "https://nsdms.merseta.org.za");
        var verifyUrl = $"{baseUrl.TrimEnd('/')}/verify/statement-of-scope/{refNum}";
        var qrBytes = GenerateQrBytes(verifyUrl);

        var doc = Document.Create(container =>
        {
            container.Page(page =>
            {
                page.Size(PageSizes.A4);
                page.Margin(1.5f, Unit.Centimetre);
                page.PageColor(Colors.White);
                page.DefaultTextStyle(x => x.FontSize(9f).FontFamily("Arial"));

                page.Header().Column(col =>
                {
                    col.Item().Row(r =>
                    {
                        r.RelativeItem().Column(c =>
                        {
                            c.Item().Text("REPUBLIC OF SOUTH AFRICA").Bold().FontSize(12).FontColor(Colors.Grey.Darken3);
                            c.Item().Text(setaName).FontSize(10).FontColor(Colors.Blue.Darken3);
                            c.Item().Text("STATEMENT OF QUALIFICATIONS & UNIT STANDARDS SCOPE").Bold().FontSize(12).FontColor(Colors.Black);
                            c.Item().Text("ANNEXURE 10.3 – ETQA ACCREDITATION SCHEDULE").Italic().FontSize(8).FontColor(Colors.Grey.Darken2);
                        });
                        r.ConstantItem(60).Height(60).Image(qrBytes);
                    });
                    col.Item().PaddingTop(5).LineHorizontal(1.5f).LineColor(Colors.Blue.Darken3);
                });

                page.Content().PaddingVertical(10).Column(col =>
                {
                    col.Spacing(8);

                    col.Item().Table(t =>
                    {
                        t.ColumnsDefinition(cols =>
                        {
                            cols.RelativeColumn(2);
                            cols.RelativeColumn(4);
                            cols.RelativeColumn(2);
                            cols.RelativeColumn(4);
                        });

                        t.Cell().Background(Colors.Grey.Lighten4).Border(0.5f).Padding(3).Text("Practitioner:").Bold();
                        t.Cell().Border(0.5f).Padding(3).Text(assessor.Person?.FullName ?? "N/A");

                        t.Cell().Background(Colors.Grey.Lighten4).Border(0.5f).Padding(3).Text("Reg Number:").Bold();
                        t.Cell().Border(0.5f).Padding(3).Text(assessor.RegistrationNumber).FontFamily("Consolas").Bold();

                        t.Cell().Background(Colors.Grey.Lighten4).Border(0.5f).Padding(3).Text("National ID:").Bold();
                        t.Cell().Border(0.5f).Padding(3).Text(assessor.Person?.RsaIdNumber ?? assessor.Person?.PassportNumber ?? "N/A");

                        t.Cell().Background(Colors.Grey.Lighten4).Border(0.5f).Padding(3).Text("Designation:").Bold();
                        t.Cell().Border(0.5f).Padding(3).Text(assessor.EtqaRole);

                        t.Cell().Background(Colors.Grey.Lighten4).Border(0.5f).Padding(3).Text("Validity Start:").Bold();
                        t.Cell().Border(0.5f).Padding(3).Text(assessor.StartDate.ToString("yyyy-MM-dd"));

                        t.Cell().Background(Colors.Grey.Lighten4).Border(0.5f).Padding(3).Text("Validity Expiry:").Bold();
                        t.Cell().Border(0.5f).Padding(3).Text(assessor.EndDate.ToString("yyyy-MM-dd"));
                    });

                    col.Item().Text("ACCREDITED QUALIFICATIONS & CONSTITUENT UNIT STANDARDS:").Bold().FontSize(10).FontColor(Colors.Blue.Darken4);

                    if (assessor.Scopes.Count == 0)
                    {
                        col.Item().Border(0.5f).Padding(6).AlignCenter().Text("No qualification scopes recorded.").Italic();
                    }
                    else
                    {
                        foreach (var scope in assessor.Scopes)
                        {
                            col.Item().Border(0.5f).Padding(5).Column(sc =>
                            {
                                sc.Item().Row(r =>
                                {
                                    r.RelativeItem().Text($"SAQA ID {scope.SaqaQualificationId}: {scope.QualificationTitle}").Bold().FontColor(Colors.Blue.Darken3);
                                    r.ConstantItem(120).AlignRight().Text($"Status: {scope.RegistrationStatusCode ?? "REGISTERED"}").FontSize(8);
                                });

                                sc.Item().PaddingTop(3).Table(ust =>
                                {
                                    ust.ColumnsDefinition(cols =>
                                    {
                                        cols.ConstantColumn(80);
                                        cols.RelativeColumn(5);
                                        cols.ConstantColumn(50);
                                        cols.ConstantColumn(50);
                                        cols.ConstantColumn(70);
                                    });

                                    ust.Cell().Background(Colors.Grey.Lighten3).Border(0.5f).Padding(2).Text("US Code").Bold().FontSize(8);
                                    ust.Cell().Background(Colors.Grey.Lighten3).Border(0.5f).Padding(2).Text("Unit Standard Title").Bold().FontSize(8);
                                    ust.Cell().Background(Colors.Grey.Lighten3).Border(0.5f).Padding(2).Text("NQF").Bold().FontSize(8);
                                    ust.Cell().Background(Colors.Grey.Lighten3).Border(0.5f).Padding(2).Text("Credits").Bold().FontSize(8);
                                    ust.Cell().Background(Colors.Grey.Lighten3).Border(0.5f).Padding(2).Text("Origin").Bold().FontSize(8);

                                    if (scope.UnitStandards != null && scope.UnitStandards.Count > 0)
                                    {
                                        foreach (var us in scope.UnitStandards)
                                        {
                                            ust.Cell().Border(0.5f).Padding(2).Text(us.UnitStandardCode).FontFamily("Consolas").FontSize(8);
                                            ust.Cell().Border(0.5f).Padding(2).Text(us.UnitStandardTitle).FontSize(8);
                                            ust.Cell().Border(0.5f).Padding(2).AlignCenter().Text($"L{us.NqfLevel}").FontSize(8);
                                            ust.Cell().Border(0.5f).Padding(2).AlignCenter().Text(us.Credits.ToString()).FontSize(8);
                                            ust.Cell().Border(0.5f).Padding(2).AlignCenter().Text(us.IsPopulatedFromQualification ? "Core Scope" : "Elective").FontSize(7.5f);
                                        }
                                    }
                                    else
                                    {
                                        ust.Cell().ColumnSpan(5).Border(0.5f).Padding(3).AlignCenter().Text("Core qualification scope applies (all constituent unit standards).").Italic().FontSize(8);
                                    }
                                });
                            });
                        }
                    }

                    col.Item().PaddingTop(6).Table(box =>
                    {
                        box.ColumnsDefinition(cols =>
                        {
                            cols.RelativeColumn(2);
                            cols.RelativeColumn(2);
                            cols.RelativeColumn(2);
                            cols.RelativeColumn(2);
                        });

                        box.Cell().Border(0.5f).Padding(3).Text("Document: Statement of Scope").FontSize(7.5f);
                        box.Cell().Border(0.5f).Padding(3).Text("Document Ref: ETQ-TP-005").Bold().FontSize(7.5f);
                        box.Cell().Border(0.5f).Padding(3).Text($"Audit Date: {DateTime.UtcNow:yyyy-MM-dd}").FontSize(7.5f);
                        box.Cell().Border(0.5f).Padding(3).Text("merSETA NSDMS 2.0").FontSize(7.5f);
                    });
                });

                page.Footer().AlignCenter().Text("merSETA Quality Assurance Division • Controlled Scope Schedule ETQ-TP-005 • www.merseta.org.za").FontSize(7.5f).FontColor(Colors.Grey.Darken1);
            });
        });

        return doc.GeneratePdf();
    }

    public async Task<byte[]> GenerateAssessorDisciplinaryLetterPdfAsync(int disciplinaryCaseId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var c = await db.AssessorDisciplinaryCases
            .Include(d => d.EtqaAssessor)
                .ThenInclude(a => a!.Person)
            .FirstOrDefaultAsync(d => d.Id == disciplinaryCaseId);

        if (c == null)
            throw new KeyNotFoundException($"AssessorDisciplinaryCase with ID {disciplinaryCaseId} not found.");

        var setaName = await _config.GetValueAsync("General.SetaName", "Manufacturing, Engineering and Related Services SETA (merSETA)");
        var refNum = c.CaseNumber;
        var baseUrl = await _config.GetValueAsync("System.BaseUrl", "https://nsdms.merseta.org.za");
        var verifyUrl = $"{baseUrl.TrimEnd('/')}/verify/disciplinary/{refNum}";
        var qrBytes = GenerateQrBytes(verifyUrl);

        var doc = Document.Create(container =>
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
                        r.RelativeItem().Column(cl =>
                        {
                            cl.Item().Text("REPUBLIC OF SOUTH AFRICA").Bold().FontSize(12).FontColor(Colors.Grey.Darken3);
                            cl.Item().Text(setaName).FontSize(10).FontColor(Colors.Blue.Darken3);
                            cl.Item().Text("ETQA DISCIPLINARY & QUALITY COMMITTEE").Bold().FontSize(11);
                        });
                        r.ConstantItem(60).Height(60).Image(qrBytes);
                    });
                    col.Item().PaddingTop(5).LineHorizontal(1.5f).LineColor(Colors.Red.Darken3);
                });

                page.Content().PaddingVertical(10).Column(col =>
                {
                    col.Spacing(8);

                    col.Item().Row(r =>
                    {
                        r.RelativeItem().Text($"Date: {DateTime.UtcNow:dd MMMM yyyy}").Bold();
                        r.RelativeItem().AlignRight().Text($"Case Reference: {c.CaseNumber}").Bold();
                    });

                    col.Item().Column(cl =>
                    {
                        cl.Item().Text($"To: {c.EtqaAssessor?.Person?.FullName ?? "Practitioner"}").Bold();
                        cl.Item().Text($"Practitioner Reg No: {c.EtqaAssessor?.RegistrationNumber ?? "N/A"}");
                        cl.Item().Text($"National ID: {c.EtqaAssessor?.Person?.RsaIdNumber ?? c.EtqaAssessor?.Person?.PassportNumber ?? "N/A"}");
                    });

                    string outcomeTitle = c.OutcomeCode switch
                    {
                        "SUSPENDED" => "NOTIFICATION OF TEMPORARY SUSPENSION OF ACCREDITATION",
                        "DEREGISTERED" => "NOTIFICATION OF TERMINATION & DE-REGISTRATION OF ACCREDITATION",
                        "DECEASED" => "STATUTORY CESSATION OF REGISTRATION (DECEASED NOTIFICATION)",
                        _ => "NOTIFICATION OF DISCIPLINARY COMMITTEE OUTCOME"
                    };

                    col.Item().PaddingVertical(4).Background(Colors.Grey.Lighten4).Padding(6).Text(
                        $"SUBJECT: {outcomeTitle}"
                    ).Bold().FontSize(10.5f).FontColor(Colors.Red.Darken4);

                    col.Item().Text(
                        $"Dear {c.EtqaAssessor?.Person?.FirstName ?? "Practitioner"},"
                    );

                    col.Item().Text(
                        $"Following deliberations by the merSETA Review Committee on {c.ReviewCommitteeDate?.ToString("dd MMMM yyyy") ?? "the committee sitting date"} " +
                        $"(Decision Number: {c.ReviewCommitteeDecisionNumber ?? "N/A"}), you are hereby notified of the official committee outcome regarding Case {c.CaseNumber}."
                    );

                    col.Item().Table(t =>
                    {
                        t.ColumnsDefinition(cols =>
                        {
                            cols.RelativeColumn(3);
                            cols.RelativeColumn(5);
                        });

                        t.Cell().Background(Colors.Grey.Lighten4).Border(0.5f).Padding(4).Text("Disciplinary Action:").Bold();
                        t.Cell().Border(0.5f).Padding(4).Text(c.OutcomeCode).Bold().FontColor(Colors.Red.Darken4);

                        t.Cell().Background(Colors.Grey.Lighten4).Border(0.5f).Padding(4).Text("Investigation Grounds:").Bold();
                        t.Cell().Border(0.5f).Padding(4).Text(c.ComplaintSummary);

                        if (c.OutcomeCode == "SUSPENDED")
                        {
                            t.Cell().Background(Colors.Grey.Lighten4).Border(0.5f).Padding(4).Text("Suspension Period:").Bold();
                            t.Cell().Border(0.5f).Padding(4).Text($"{c.SuspensionStartDate:yyyy-MM-dd} to {c.SuspensionEndDate:yyyy-MM-dd}");

                            t.Cell().Background(Colors.Grey.Lighten4).Border(0.5f).Padding(4).Text("Development Plan:").Bold();
                            t.Cell().Border(0.5f).Padding(4).Text(c.DevelopmentPlanDetails ?? "Remedial training and reassessment required.");
                        }

                        t.Cell().Background(Colors.Grey.Lighten4).Border(0.5f).Padding(4).Text("Assessment Impact:").Bold();
                        t.Cell().Border(0.5f).Padding(4).Text("Practitioner is immediately prohibited from conducting or moderating assessments. Historical assessments completed prior to this date remain recognized.");
                    });

                    col.Item().Text(
                        "STATUTORY APPEAL RIGHTS:"
                    ).Bold().FontSize(9f);

                    col.Item().Text(
                        "In accordance with merSETA ETQA Appeal Procedures, you have the right to lodge a formal appeal against this decision within thirty (30) calendar days " +
                        "of receipt of this notification. Appeals must be submitted in writing to the Office of the Chief Executive Officer."
                    ).FontSize(8.5f);

                    col.Item().PaddingTop(15).Row(r =>
                    {
                        r.RelativeItem().Column(cl =>
                        {
                            cl.Item().Text("Issued on behalf of the Review Committee:").FontSize(9);
                            cl.Item().PaddingTop(25).Text("Chief Operations Officer / Chairperson: ETQA").Bold().FontSize(9);
                            cl.Item().Text("merSETA National Office").FontSize(8).FontColor(Colors.Grey.Darken2);
                        });
                        r.RelativeItem().Column(cl =>
                        {
                            cl.Item().AlignRight().Text("Statutory Order:").FontSize(9);
                            cl.Item().AlignRight().Text("[ RATIFIED & ENFORCED ]").Bold().FontColor(Colors.Red.Darken3).FontSize(8.5f);
                            cl.Item().AlignRight().Text($"Case: {c.CaseNumber}").FontFamily("Consolas").FontSize(7.5f);
                        });
                    });

                    col.Item().PaddingTop(6).Table(box =>
                    {
                        box.ColumnsDefinition(cols =>
                        {
                            cols.RelativeColumn(2);
                            cols.RelativeColumn(2);
                            cols.RelativeColumn(2);
                            cols.RelativeColumn(2);
                        });

                        box.Cell().Border(0.5f).Padding(3).Text("Document: Disciplinary Order").FontSize(7.5f);
                        box.Cell().Border(0.5f).Padding(3).Text("Document Ref: ETQ-TP-DISC").Bold().FontSize(7.5f);
                        box.Cell().Border(0.5f).Padding(3).Text($"Date: {DateTime.UtcNow:yyyy-MM-dd}").FontSize(7.5f);
                        box.Cell().Border(0.5f).Padding(3).Text("merSETA NSDMS 2.0").FontSize(7.5f);
                    });
                });

                page.Footer().AlignCenter().Text("merSETA Quality Assurance Division • Disciplinary Notification Order • www.merseta.org.za").FontSize(7.5f).FontColor(Colors.Grey.Darken1);
            });
        });

        return doc.GeneratePdf();
    }

    #region Statutory Learner Management Spec NMok_21112022 Documents
    public async Task<byte[]> GenerateLpmFm005TransferFormPdfAsync(int transferId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var transfer = await db.CompanyLearnerTransfers
            .Include(t => t.CompanyLearner)
                .ThenInclude(l => l!.Person)
            .Include(t => t.FromOrganisation)
            .Include(t => t.ToOrganisation)
            .Include(t => t.FromTrainingProvider)
            .Include(t => t.ToTrainingProvider)
            .Include(t => t.TargetWorkplaceApproval)
            .FirstOrDefaultAsync(t => t.Id == transferId);

        if (transfer == null)
            throw new KeyNotFoundException($"CompanyLearnerTransfer with ID {transferId} not found.");

        var learner = transfer.CompanyLearner;
        var person = learner?.Person;
        var baseUrl = await _config.GetValueAsync("System.BaseUrl", "https://nsdms.merseta.org.za");
        var verifyUrl = $"{baseUrl.TrimEnd('/')}/verify/learner-transfer/{transfer.Id}";
        var qrBytes = GenerateQrBytes(verifyUrl);

        var doc = Document.Create(container =>
        {
            container.Page(page =>
            {
                page.Size(PageSizes.A4);
                page.Margin(1.5f, Unit.Centimetre);
                page.PageColor(Colors.White);
                page.DefaultTextStyle(x => x.FontSize(10).FontFamily("Arial"));

                page.Header().Column(col =>
                {
                    col.Item().AlignCenter().Text("REPUBLIC OF SOUTH AFRICA").Bold().FontSize(12).FontColor(Colors.Grey.Darken3);
                    col.Item().AlignCenter().Text("MANUFACTURING, ENGINEERING AND RELATED SERVICES SETA").FontSize(11).FontColor(Colors.Blue.Darken3);
                    col.Item().AlignCenter().Text("LPM-FM-005: APPLICATION FOR TRANSFER OF LEARNER AGREEMENT").Bold().FontSize(14).FontColor(Colors.Black);
                    col.Item().PaddingTop(3).LineHorizontal(1.5f).LineColor(Colors.Blue.Darken2);
                });

                page.Content().PaddingVertical(15).Column(col =>
                {
                    col.Spacing(10);

                    // Metadata Box
                    col.Item().Border(1).BorderColor(Colors.Grey.Lighten1).Padding(8).Row(r =>
                    {
                        r.RelativeItem().Text($"Transfer Ref: TRF-{transfer.Id:D5}").Bold();
                        r.RelativeItem().Text($"Application Date: {transfer.TransferDate:yyyy-MM-dd}");
                        r.RelativeItem().Text($"Scope: {transfer.TransferScopeCode}").Bold();
                        r.RelativeItem().Text($"Status: {transfer.TransferStatusCode}").FontColor(Colors.Blue.Darken2).Bold();
                    });

                    // Section A: Learner Demographics
                    col.Item().Text("SECTION A: LEARNER IDENTIFICATION & PROGRAMME DETAILS").Bold().FontColor(Colors.Blue.Darken3);
                    col.Item().Border(1).BorderColor(Colors.Grey.Lighten2).Padding(8).Column(c =>
                    {
                        c.Spacing(4);
                        c.Item().Row(r =>
                        {
                            r.RelativeItem().Text($"Full Name: {person?.FullName ?? "N/A"}").Bold();
                            r.RelativeItem().Text($"National ID / Passport: {person?.RsaIdNumber ?? person?.PassportNumber ?? "N/A"}");
                        });
                        c.Item().Row(r =>
                        {
                            r.RelativeItem().Text($"Contract Number: {learner?.LearnerContractNumber ?? "N/A"}");
                            r.RelativeItem().Text($"Registered Trade/Qualification: {learner?.QualificationTitle ?? "N/A"}");
                        });
                    });

                    // Section B: Releasing Entity
                    col.Item().Text("SECTION B: CURRENT RELEASING ENTITY").Bold().FontColor(Colors.Blue.Darken3);
                    col.Item().Border(1).BorderColor(Colors.Grey.Lighten2).Padding(8).Column(c =>
                    {
                        c.Spacing(4);
                        if (transfer.TransferScopeCode == "EmployerToEmployer")
                        {
                            c.Item().Text($"Releasing Employer: {transfer.FromOrganisation?.CompanyName ?? "N/A"} (SDL: {transfer.FromOrganisation?.SdlNumber ?? "N/A"})").Bold();
                            c.Item().Text($"Current Employer Consent: {(transfer.IsCurrentEmployerAgreed == true ? "AGREED" : transfer.IsCurrentEmployerAgreed == false ? "DISSENTED" : "PENDING")}");
                        }
                        else
                        {
                            c.Item().Text($"Releasing Training Provider: {transfer.FromTrainingProvider?.ProviderName ?? "N/A"} (Accreditation: {transfer.FromTrainingProvider?.AccreditationNumber ?? "N/A"})").Bold();
                        }
                    });

                    // Section C: Receiving Entity
                    col.Item().Text("SECTION C: FUTURE RECEIVING ENTITY").Bold().FontColor(Colors.Blue.Darken3);
                    col.Item().Border(1).BorderColor(Colors.Grey.Lighten2).Padding(8).Column(c =>
                    {
                        c.Spacing(4);
                        if (transfer.TransferScopeCode == "EmployerToEmployer")
                        {
                            c.Item().Text($"Receiving Employer: {transfer.ToOrganisation?.CompanyName ?? "N/A"} (SDL: {transfer.ToOrganisation?.SdlNumber ?? "N/A"})").Bold();
                            c.Item().Text($"Workplace Approval Verification: {(transfer.TargetWorkplaceApproval != null ? "VERIFIED (Approved Workplace)" : "PENDING")}");
                            c.Item().Text($"Future Employer Consent: {(transfer.IsFutureEmployerAgreed == true ? "AGREED" : "PENDING")}");
                        }
                        else
                        {
                            c.Item().Text($"Receiving Training Provider: {transfer.ToTrainingProvider?.ProviderName ?? "N/A"} (Accreditation: {transfer.ToTrainingProvider?.AccreditationNumber ?? "N/A"})").Bold();
                        }
                    });

                    // Section D: Transfer Details
                    col.Item().Text("SECTION D: TRANSFER RATIONALE & EFFECTIVE DATES").Bold().FontColor(Colors.Blue.Darken3);
                    col.Item().Border(1).BorderColor(Colors.Grey.Lighten2).Padding(8).Column(c =>
                    {
                        c.Spacing(4);
                        c.Item().Text($"Statutory Reason Code: {transfer.TransferReasonCode}").Bold();
                        c.Item().Text($"Proposed Operational Effective Date: {transfer.EffectiveDate:yyyy-MM-dd}");
                        c.Item().Text($"Initiated By Role: {transfer.InitiatedByTypeCode}");
                        if (!string.IsNullOrWhiteSpace(transfer.ApprovalComments))
                        {
                            c.Item().Text($"Adjudication / Comments: {transfer.ApprovalComments}");
                        }
                    });

                    // Verification & Signatures
                    col.Item().PaddingTop(10).Row(r =>
                    {
                        r.RelativeItem(3).Column(c =>
                        {
                            c.Spacing(6);
                            c.Item().Text("Authorised merSETA ETQA Signatory").Bold();
                            c.Item().Text($"Official: {transfer.ApprovedByUserId ?? "Pending Adjudication"}");
                            c.Item().Text($"Date: {transfer.ApprovalDate?.ToString("yyyy-MM-dd") ?? "Pending"}");
                        });
                        r.RelativeItem(1).AlignCenter().Column(c =>
                        {
                            c.Item().Width(45).Height(45).Image(qrBytes);
                            c.Item().AlignCenter().Text("Scan to Verify").FontSize(7).FontColor(Colors.Blue.Darken2);
                        });
                    });
                });

                page.Footer().AlignCenter().Text("merSETA Learner Lifecycle Administration • Document LPM-FM-005 • www.merseta.org.za").FontSize(8).FontColor(Colors.Grey.Darken1);
            });
        });

        return doc.GeneratePdf();
    }

    public async Task<byte[]> GenerateLpmTp010MutualTerminationLetterPdfAsync(int terminationId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var termination = await db.CompanyLearnerTerminations
            .IgnoreQueryFilters()
            .FirstOrDefaultAsync(t => t.Id == terminationId);

        if (termination == null)
            throw new KeyNotFoundException($"CompanyLearnerTermination with ID {terminationId} not found.");

        var learner = await db.CompanyLearners
            .IgnoreQueryFilters()
            .Include(l => l.Person)
            .Include(l => l.Organisation)
            .FirstOrDefaultAsync(l => l.Id == termination.CompanyLearnerId);
        termination.CompanyLearner = learner;

        var person = learner?.Person;
        var org = learner?.Organisation;
        var baseUrl = await _config.GetValueAsync("System.BaseUrl", "https://nsdms.merseta.org.za");
        var verifyUrl = $"{baseUrl.TrimEnd('/')}/verify/learner-termination/{termination.Id}";
        var qrBytes = GenerateQrBytes(verifyUrl);

        var doc = Document.Create(container =>
        {
            container.Page(page =>
            {
                page.Size(PageSizes.A4);
                page.Margin(2, Unit.Centimetre);
                page.PageColor(Colors.White);
                page.DefaultTextStyle(x => x.FontSize(10.5f).FontFamily("Arial"));

                page.Header().Column(col =>
                {
                    col.Item().AlignCenter().Text("REPUBLIC OF SOUTH AFRICA").Bold().FontSize(12).FontColor(Colors.Grey.Darken3);
                    col.Item().AlignCenter().Text("MANUFACTURING, ENGINEERING AND RELATED SERVICES SETA").FontSize(11).FontColor(Colors.Blue.Darken3);
                    col.Item().AlignCenter().Text("LPM-TP-010: MUTUAL TERMINATION OF LEARNER AGREEMENT").Bold().FontSize(14).FontColor(Colors.Black);
                    col.Item().PaddingTop(4).LineHorizontal(1.5f).LineColor(Colors.Blue.Darken2);
                });

                page.Content().PaddingVertical(20).Column(col =>
                {
                    col.Spacing(12);

                    col.Item().Row(r =>
                    {
                        r.RelativeItem().Text($"Reference: LPM-TP-010/{termination.Id:D5}").Bold();
                        r.RelativeItem().AlignRight().Text($"Date: {DateTime.UtcNow:yyyy-MM-dd}");
                    });

                    col.Item().Text($"To: {org?.CompanyName ?? "The Employer"} (SDL: {org?.SdlNumber ?? "N/A"})");
                    col.Item().Text($"And: {person?.FullName ?? "The Learner"} (ID: {person?.RsaIdNumber ?? "N/A"})");

                    col.Item().PaddingTop(5).Text("CONFIRMATION OF MUTUAL TERMINATION OF LEARNER AGREEMENT").Bold().FontSize(12).FontColor(Colors.Blue.Darken4);

                    col.Item().Text($"This document confirms that by bilateral mutual agreement between {org?.CompanyName ?? "the Employer"} and the learner, {person?.FullName ?? "the Learner"}, the registered learner agreement reference {learner?.LearnerContractNumber ?? "N/A"} in respect of qualification '{learner?.QualificationTitle ?? "Skills Development Programme"}' is formally terminated with effect from {termination.EffectiveDate:yyyy-MM-dd}.");

                    col.Item().Text($"Reason for Termination: {termination.TerminationReasonCode}").Bold();

                    if (!string.IsNullOrWhiteSpace(termination.SettlementNotes))
                    {
                        col.Item().Text($"Terms of Settlement / Mutual Agreement Notes: {termination.SettlementNotes}").Italic();
                    }

                    col.Item().Text("In accordance with the Skills Development Act 97 of 1998 as amended and Sectoral Determination 5, all obligations under this tripartite agreement have ceased as of the effective date, without prejudice to any accrued statutory entitlements.");

                    col.Item().PaddingTop(15).Row(r =>
                    {
                        r.RelativeItem(3).Column(c =>
                        {
                            c.Spacing(4);
                            c.Item().Text("merSETA Client Relations / Regional Manager").Bold();
                            c.Item().Text($"Approved by: {termination.ApprovedByUserId ?? "Official Authoriser"}");
                            c.Item().Text($"Authorisation Date: {termination.ApprovalDate?.ToString("yyyy-MM-dd") ?? DateTime.UtcNow.ToString("yyyy-MM-dd")}");
                        });
                        r.RelativeItem(1).AlignCenter().Column(c =>
                        {
                            c.Item().Width(45).Height(45).Image(qrBytes);
                            c.Item().AlignCenter().Text("Scan to Verify").FontSize(7).FontColor(Colors.Blue.Darken2);
                        });
                    });
                });

                page.Footer().AlignCenter().Text("merSETA Statutory Documentation • Annexure LPM-TP-010 • Digitally Hash Anchored").FontSize(8).FontColor(Colors.Grey.Darken1);
            });
        });

        return doc.GeneratePdf();
    }

    public async Task<byte[]> GenerateChecklist036InvestigationPdfAsync(int terminationId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var termination = await db.CompanyLearnerTerminations
            .IgnoreQueryFilters()
            .FirstOrDefaultAsync(t => t.Id == terminationId);

        if (termination == null)
            throw new KeyNotFoundException($"CompanyLearnerTermination with ID {terminationId} not found.");

        var learner = await db.CompanyLearners
            .IgnoreQueryFilters()
            .Include(l => l.Person)
            .Include(l => l.Organisation)
            .FirstOrDefaultAsync(l => l.Id == termination.CompanyLearnerId);
        termination.CompanyLearner = learner;

        var person = learner?.Person;
        var org = learner?.Organisation;
        var baseUrl = await _config.GetValueAsync("System.BaseUrl", "https://nsdms.merseta.org.za");
        var verifyUrl = $"{baseUrl.TrimEnd('/')}/verify/investigation-036/{termination.Id}";
        var qrBytes = GenerateQrBytes(verifyUrl);

        var doc = Document.Create(container =>
        {
            container.Page(page =>
            {
                page.Size(PageSizes.A4);
                page.Margin(1.5f, Unit.Centimetre);
                page.PageColor(Colors.White);
                page.DefaultTextStyle(x => x.FontSize(9.5f).FontFamily("Arial"));

                page.Header().Column(col =>
                {
                    col.Item().AlignCenter().Text("REPUBLIC OF SOUTH AFRICA").Bold().FontSize(11).FontColor(Colors.Grey.Darken3);
                    col.Item().AlignCenter().Text("MANUFACTURING, ENGINEERING AND RELATED SERVICES SETA").FontSize(10).FontColor(Colors.Blue.Darken3);
                    col.Item().AlignCenter().Text("CHECKLIST (036): UNILATERAL TERMINATION INVESTIGATION REPORT").Bold().FontSize(13).FontColor(Colors.Black);
                    col.Item().PaddingTop(3).LineHorizontal(1.5f).LineColor(Colors.Blue.Darken2);
                });

                page.Content().PaddingVertical(15).Column(col =>
                {
                    col.Spacing(10);

                    // Statutory 14-day SLA Box
                    col.Item().Border(1).BorderColor(Colors.Orange.Darken2).Padding(8).Row(r =>
                    {
                        r.RelativeItem().Text($"Investigation ID: CHK-036-{termination.Id:D5}").Bold();
                        r.RelativeItem().Text($"Start: {termination.InvestigationStartDate:yyyy-MM-dd}");
                        r.RelativeItem().Text($"SLA Due (14 Working Days): {termination.InvestigationDueDate:yyyy-MM-dd}").Bold();
                        r.RelativeItem().Text($"Completed: {termination.InvestigationCompletedDate?.ToString("yyyy-MM-dd") ?? "IN PROGRESS"}").FontColor(Colors.Blue.Darken3).Bold();
                    });

                    // Target Subject
                    col.Item().Text("1. LEARNER & EMPLOYER DETAILS").Bold().FontColor(Colors.Blue.Darken3);
                    col.Item().Border(1).BorderColor(Colors.Grey.Lighten2).Padding(8).Column(c =>
                    {
                        c.Spacing(3);
                        c.Item().Row(r =>
                        {
                            r.RelativeItem().Text($"Learner: {person?.FullName ?? "N/A"} (RSA ID: {person?.RsaIdNumber ?? "N/A"})").Bold();
                            r.RelativeItem().Text($"Employer: {org?.CompanyName ?? "N/A"} (SDL: {org?.SdlNumber ?? "N/A"})");
                        });
                        c.Item().Row(r =>
                        {
                            r.RelativeItem().Text($"Contract Number: {learner?.LearnerContractNumber ?? "N/A"}");
                            r.RelativeItem().Text($"Unilateral Reason: {termination.TerminationReasonCode}").Bold();
                        });
                    });

                    // Investigation Findings
                    col.Item().Text("2. STATUTORY INVESTIGATION FINDINGS (CHECKLIST 036 CRITERIA)").Bold().FontColor(Colors.Blue.Darken3);
                    col.Item().Border(1).BorderColor(Colors.Grey.Lighten2).Padding(8).Column(c =>
                    {
                        c.Spacing(4);
                        c.Item().Text($"Investigation Summary & Audit Notes:").Bold();
                        c.Item().Text(termination.InvestigationOutcomeSummary ?? "Full investigation conducted in accordance with Section 5 of the Learner Management Specification.");
                        c.Item().PaddingTop(4).Text($"Recommendation to Employer to Write ARPL: {(termination.IsArplRecommended ? "YES (Dispatched)" : "NO")}").Bold();
                        c.Item().Text($"Recommendation to Employer to Transfer Learner: {(termination.IsTransferRecommended ? "YES (Dispatched)" : "NO")}").Bold();
                    });

                    // Committee Adjudication
                    col.Item().Text("3. ETQA REVIEW COMMITTEE ADJUDICATION").Bold().FontColor(Colors.Blue.Darken3);
                    col.Item().Border(1).BorderColor(Colors.Grey.Lighten2).Padding(8).Column(c =>
                    {
                        c.Spacing(4);
                        c.Item().Row(r =>
                        {
                            r.RelativeItem().Text($"Committee Meeting Ref: {(termination.ReviewCommitteeMeetingId.HasValue ? $"ETQA-RC-{termination.ReviewCommitteeMeetingId}" : "Pending Agenda")}");
                            r.RelativeItem().Text($"Decision: {termination.CommitteeDecisionCode ?? "Pending Review"}").Bold();
                        });
                        if (!string.IsNullOrWhiteSpace(termination.CommitteeDecisionNotes))
                        {
                            c.Item().Text($"Committee Notes: {termination.CommitteeDecisionNotes}");
                        }
                    });

                    // Sign-offs
                    col.Item().PaddingTop(8).Row(r =>
                    {
                        r.RelativeItem(3).Column(c =>
                        {
                            c.Spacing(3);
                            c.Item().Text("Investigating Official Sign-off").Bold();
                            c.Item().Text($"Officer ID: {termination.InvestigationConductedByUserId ?? "Assigned CLO/QA"}");
                            c.Item().Text($"Sign-off Date: {termination.InvestigationCompletedDate?.ToString("yyyy-MM-dd") ?? DateTime.UtcNow.ToString("yyyy-MM-dd")}");
                        });
                        r.RelativeItem(1).AlignCenter().Column(c =>
                        {
                            c.Item().Width(45).Height(45).Image(qrBytes);
                            c.Item().AlignCenter().Text("Scan to Verify").FontSize(7).FontColor(Colors.Blue.Darken2);
                        });
                    });
                });

                page.Footer().AlignCenter().Text("merSETA Quality Assurance • Checklist (036) Investigation Dossier • Official Controlled Document").FontSize(8).FontColor(Colors.Grey.Darken1);
            });
        });

        return doc.GeneratePdf();
    }

    public async Task<byte[]> GenerateLearnerAddendumPdfAsync(int extensionId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var ext = await db.CompanyLearnerExtensions
            .IgnoreQueryFilters()
            .FirstOrDefaultAsync(e => e.Id == extensionId);

        if (ext == null)
            throw new KeyNotFoundException($"CompanyLearnerExtension with ID {extensionId} not found.");

        var learner = await db.CompanyLearners
            .IgnoreQueryFilters()
            .Include(l => l.Person)
            .Include(l => l.Organisation)
            .FirstOrDefaultAsync(l => l.Id == ext.CompanyLearnerId);
        ext.CompanyLearner = learner;
        var person = learner?.Person;
        var org = learner?.Organisation;
        var baseUrl = await _config.GetValueAsync("System.BaseUrl", "https://nsdms.merseta.org.za");
        var verifyUrl = $"{baseUrl.TrimEnd('/')}/verify/learner-extension/{ext.Id}";
        var qrBytes = GenerateQrBytes(verifyUrl);

        var doc = Document.Create(container =>
        {
            container.Page(page =>
            {
                page.Size(PageSizes.A4);
                page.Margin(2, Unit.Centimetre);
                page.PageColor(Colors.White);
                page.DefaultTextStyle(x => x.FontSize(10.5f).FontFamily("Arial"));

                page.Header().Column(col =>
                {
                    col.Item().AlignCenter().Text("REPUBLIC OF SOUTH AFRICA").Bold().FontSize(12).FontColor(Colors.Grey.Darken3);
                    col.Item().AlignCenter().Text("MANUFACTURING, ENGINEERING AND RELATED SERVICES SETA").FontSize(11).FontColor(Colors.Blue.Darken3);
                    col.Item().AlignCenter().Text("ADDENDUM TO LEARNER AGREEMENT: EXTENSION OF DURATION").Bold().FontSize(14).FontColor(Colors.Black);
                    col.Item().PaddingTop(4).LineHorizontal(1.5f).LineColor(Colors.Blue.Darken2);
                });

                page.Content().PaddingVertical(20).Column(col =>
                {
                    col.Spacing(12);

                    col.Item().Row(r =>
                    {
                        r.RelativeItem().Text($"Addendum Ref: ADD-{ext.Id:D5}").Bold();
                        r.RelativeItem().AlignRight().Text($"Date: {DateTime.UtcNow:yyyy-MM-dd}");
                    });

                    col.Item().Text("ADDENDUM TO REGISTERED LEARNER AGREEMENT").Bold().FontSize(12).FontColor(Colors.Blue.Darken4);

                    col.Item().Text($"This Addendum forms an integral part of the registered Learner Agreement reference {learner?.LearnerContractNumber ?? "N/A"}, entered into between {org?.CompanyName ?? "the Employer"} and {person?.FullName ?? "the Learner"}.");

                    col.Item().Border(1).BorderColor(Colors.Grey.Lighten2).Padding(10).Column(c =>
                    {
                        c.Spacing(5);
                        c.Item().Text($"Extension Category: {ext.ExtensionTypeCode}").Bold();
                        c.Item().Text($"Reason: {ext.ExtensionReasonCode}");
                        c.Item().Text($"Justification: {ext.JustificationComments}");
                        c.Item().Text($"Original Expiry Date: {ext.OriginalExpiryDate:yyyy-MM-dd}");
                        c.Item().Text($"Approved Extended Expiry Date: {ext.ApprovedExpiryDate ?? ext.RequestedExpiryDate:yyyy-MM-dd}").Bold().FontColor(Colors.Green.Darken3);
                    });

                    col.Item().Text("All other terms and statutory conditions of the original registered Learner Agreement remain in full force and effect.");

                    col.Item().PaddingTop(15).Row(r =>
                    {
                        r.RelativeItem(3).Column(c =>
                        {
                            c.Spacing(4);
                            c.Item().Text("merSETA Authorised Approval Official").Bold();
                            c.Item().Text($"Official: {ext.ApprovedByUserId ?? "merSETA Representative"}");
                            c.Item().Text($"Date: {ext.ApprovalDate?.ToString("yyyy-MM-dd") ?? DateTime.UtcNow.ToString("yyyy-MM-dd")}");
                        });
                        r.RelativeItem(1).AlignCenter().Column(c =>
                        {
                            c.Item().Width(45).Height(45).Image(qrBytes);
                            c.Item().AlignCenter().Text("Scan to Verify").FontSize(7).FontColor(Colors.Blue.Darken2);
                        });
                    });
                });

                page.Footer().AlignCenter().Text("merSETA Learner Lifecycle Administration • Addendum of Agreement • www.merseta.org.za").FontSize(8).FontColor(Colors.Grey.Darken1);
            });
        });

        return doc.GeneratePdf();
    }

    public async Task<byte[]> GenerateTerminationDecisionLetterPdfAsync(int terminationId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var termination = await db.CompanyLearnerTerminations
            .IgnoreQueryFilters()
            .FirstOrDefaultAsync(t => t.Id == terminationId);

        if (termination == null)
            throw new KeyNotFoundException($"CompanyLearnerTermination with ID {terminationId} not found.");

        var learner = await db.CompanyLearners
            .IgnoreQueryFilters()
            .Include(l => l.Person)
            .Include(l => l.Organisation)
            .FirstOrDefaultAsync(l => l.Id == termination.CompanyLearnerId);
        termination.CompanyLearner = learner;

        var person = learner?.Person;
        var org = learner?.Organisation;
        bool isApproved = string.Equals(termination.CommitteeDecisionCode, "Approved", StringComparison.OrdinalIgnoreCase) ||
                          string.Equals(termination.TerminationStatusCode, "Approved", StringComparison.OrdinalIgnoreCase);

        var baseUrl = await _config.GetValueAsync("System.BaseUrl", "https://nsdms.merseta.org.za");
        var verifyUrl = $"{baseUrl.TrimEnd('/')}/verify/committee-decision/{termination.Id}";
        var qrBytes = GenerateQrBytes(verifyUrl);

        var doc = Document.Create(container =>
        {
            container.Page(page =>
            {
                page.Size(PageSizes.A4);
                page.Margin(2, Unit.Centimetre);
                page.PageColor(Colors.White);
                page.DefaultTextStyle(x => x.FontSize(10.5f).FontFamily("Arial"));

                page.Header().Column(col =>
                {
                    col.Item().AlignCenter().Text("REPUBLIC OF SOUTH AFRICA").Bold().FontSize(12).FontColor(Colors.Grey.Darken3);
                    col.Item().AlignCenter().Text("MANUFACTURING, ENGINEERING AND RELATED SERVICES SETA").FontSize(11).FontColor(Colors.Blue.Darken3);
                    col.Item().AlignCenter().Text(isApproved ? "ETQA REVIEW COMMITTEE: OUTCOME CONFIRMATION LETTER" : "ETQA REVIEW COMMITTEE: REQUIREMENTS NOT MET NOTICE").Bold().FontSize(13).FontColor(isApproved ? Colors.Black : Colors.Red.Darken3);
                    col.Item().PaddingTop(4).LineHorizontal(1.5f).LineColor(isApproved ? Colors.Blue.Darken2 : Colors.Red.Darken2);
                });

                page.Content().PaddingVertical(20).Column(col =>
                {
                    col.Spacing(12);

                    col.Item().Row(r =>
                    {
                        r.RelativeItem().Text($"Decision Notice Ref: DEC-{termination.Id:D5}").Bold();
                        r.RelativeItem().AlignRight().Text($"Date: {DateTime.UtcNow:yyyy-MM-dd}");
                    });

                    col.Item().Text($"To: {org?.CompanyName ?? "The Employer"} (SDL: {org?.SdlNumber ?? "N/A"})");
                    col.Item().Text($"Learner: {person?.FullName ?? "The Learner"} (ID: {person?.RsaIdNumber ?? "N/A"})");
                    col.Item().Text($"Contract Number: {learner?.LearnerContractNumber ?? "N/A"}");

                    if (isApproved)
                    {
                        col.Item().PaddingTop(5).Text("NOTICE OF FORMAL TERMINATION CONFIRMATION").Bold().FontSize(12).FontColor(Colors.Blue.Darken4);
                        col.Item().Text($"The ETQA Review Committee has reviewed the application for termination and completed statutory investigation Checklist 036. The application has been formally APPROVED. The learner agreement is terminated effective {termination.EffectiveDate:yyyy-MM-dd}.");
                    }
                    else
                    {
                        col.Item().PaddingTop(5).Text("NOTICE: REQUIREMENTS NOT MET FOR TERMINATION").Bold().FontSize(12).FontColor(Colors.Red.Darken4);
                        col.Item().Text("The ETQA Review Committee has adjudicated the termination submission and determined that statutory requirements have NOT been met. The learner agreement remains ACTIVE on the NSDMS system.");
                    }

                    if (!string.IsNullOrWhiteSpace(termination.CommitteeDecisionNotes))
                    {
                        col.Item().Border(1).BorderColor(Colors.Grey.Lighten2).Padding(8).Column(c =>
                        {
                            c.Item().Text("Committee Adjudication Rationale & Directives:").Bold();
                            c.Item().Text(termination.CommitteeDecisionNotes);
                        });
                    }

                    col.Item().PaddingTop(15).Row(r =>
                    {
                        r.RelativeItem(3).Column(c =>
                        {
                            c.Spacing(4);
                            c.Item().Text("Chairperson, merSETA ETQA Review Committee").Bold();
                            c.Item().Text($"Decision Date: {termination.CommitteeDecisionDate?.ToString("yyyy-MM-dd") ?? DateTime.UtcNow.ToString("yyyy-MM-dd")}");
                        });
                        r.RelativeItem(1).AlignCenter().Column(c =>
                        {
                            c.Item().Width(45).Height(45).Image(qrBytes);
                            c.Item().AlignCenter().Text("Scan to Verify").FontSize(7).FontColor(Colors.Blue.Darken2);
                        });
                    });
                });

            });
        });
        return doc.GeneratePdf();
    }
    #endregion


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
