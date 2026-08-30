using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;
using QuestPDF.Fluent;
using QuestPDF.Helpers;
using QuestPDF.Infrastructure;

namespace Nsdms.Infrastructure.Services;

public class QuestPdfDocumentService : IPdfDocumentService
{
    private readonly IFeatureFlagService _featureFlags;
    private readonly ISystemConfigurationService _config;

    public QuestPdfDocumentService(IFeatureFlagService featureFlags, ISystemConfigurationService config)
    {
        _featureFlags = featureFlags;
        _config = config;
        QuestPDF.Settings.License = LicenseType.Community;
    }

    public async Task<byte[]> GenerateTradeTestCertificateAsync(LearnerTradeTest tradeTest)
    {
        var enableWatermarks = await _featureFlags.IsFeatureEnabledAsync("Pdfs.WatermarksAndQrCodes", true);
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
                        row.RelativeItem().Column(c =>
                        {
                            c.Item().Text($"Certificate Number: {tradeTest.SerialCertificateNumber ?? "CERT-PENDING"}").Bold();
                            c.Item().Text($"Issue Date: {tradeTest.CertificateIssueDate?.ToString("yyyy-MM-dd") ?? DateTime.UtcNow.ToString("yyyy-MM-dd")}");
                            c.Item().Text($"Test Centre: {tradeTest.TestCenterName}");
                        });

                        row.RelativeItem().AlignRight().Column(c =>
                        {
                            c.Item().Text("Authorised merSETA Signatory").Bold();
                            c.Item().PaddingTop(20).LineHorizontal(1).LineColor(Colors.Grey.Darken1);
                            c.Item().AlignCenter().Text("Chief Executive Officer / ETQA Manager").FontSize(9);
                        });
                    });

                    if (enableWatermarks)
                    {
                        col.Item().PaddingTop(10).AlignCenter().Text("Official Government Credential • Issued under the Skills Development Act").FontSize(8).FontColor(Colors.Grey.Medium);
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
}
