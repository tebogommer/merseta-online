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
    public async Task<byte[]> GenerateSdpAccreditationCertificatePdfAsync(int providerId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var provider = await db.TrainingProviders
            .Include(p => p.Organisation)
            .Include(p => p.Qualifications)
            .FirstOrDefaultAsync(p => p.Id == providerId);

        if (provider == null)
            throw new KeyNotFoundException($"TrainingProvider with ID {providerId} not found.");

        var setaName = await _config.GetValueAsync("General.SetaName", "Manufacturing, Engineering and Related Services SETA (merSETA)");
        var certSerial = $"CERT-SDP-{provider.AccreditationStartDate?.Year ?? DateTime.UtcNow.Year}-{provider.Id:D5}";
        var baseUrl = await _config.GetValueAsync("System.BaseUrl", "https://nsdms.merseta.org.za");
        var verifyUrl = $"{baseUrl.TrimEnd('/')}/verify/document/{certSerial}";
        var qrBytes = GenerateQrBytes(verifyUrl);
        var securitySeal = provider.DigitalSecuritySeal ?? "SEC-" + Guid.NewGuid().ToString("N").Substring(0, 16).ToUpper();

        var document = Document.Create(container =>
        {
            container.Page(page =>
            {
                page.Size(PageSizes.A4.Landscape());
                page.Margin(1.5f, Unit.Centimetre);
                page.PageColor(Colors.White);
                page.DefaultTextStyle(x => x.FontSize(11).FontFamily("Arial"));

                page.Content().Border(3).BorderColor(Colors.Blue.Darken4).Padding(8).Column(col =>
                {
                    col.Item().Border(1).BorderColor(Colors.Amber.Darken3).Padding(16).Column(inner =>
                    {
                        inner.Spacing(8);

                        // Header Section
                        inner.Item().AlignCenter().Text("REPUBLIC OF SOUTH AFRICA").Bold().FontSize(15).FontColor(Colors.Grey.Darken3);
                        inner.Item().AlignCenter().Text(setaName.ToUpper()).Bold().FontSize(13).FontColor(Colors.Blue.Darken3);
                        inner.Item().AlignCenter().Text("EDUCATION AND TRAINING QUALITY ASSURANCE (ETQA) DIVISION").FontSize(10).LetterSpacing(0.05f);
                        inner.Item().PaddingTop(2).LineHorizontal(1.5f).LineColor(Colors.Amber.Darken3);

                        // Title
                        inner.Item().PaddingTop(8).AlignCenter().Text("CERTIFICATE OF ACCREDITATION").Bold().FontSize(22).FontColor(Colors.Blue.Darken4);
                        inner.Item().AlignCenter().Text("This is to certify that").Italic().FontSize(12).FontColor(Colors.Grey.Darken2);

                        // Legal Entity Name
                        var legalName = provider.Organisation?.CompanyName ?? "Accredited Training Institution";
                        inner.Item().AlignCenter().Text(legalName.ToUpper()).Bold().FontSize(18).FontColor(Colors.Black);
                        
                        if (!string.IsNullOrWhiteSpace(provider.Organisation?.SdlNumber))
                        {
                            inner.Item().AlignCenter().Text($"SDL Reference: {provider.Organisation.SdlNumber} | Registration No: {provider.Organisation.RegistrationNumber ?? "N/A"}").FontSize(10).FontColor(Colors.Grey.Darken2);
                        }

                        inner.Item().AlignCenter().Text("has fulfilled all statutory evaluation and quality assurance requirements as a").FontSize(11);
                        inner.Item().AlignCenter().Text($"SKILLS DEVELOPMENT PROVIDER ({provider.AccreditationStream.ToUpper()})").Bold().FontSize(13).FontColor(Colors.Blue.Darken3);

                        // Accreditation Details Grid
                        inner.Item().PaddingTop(6).Row(row =>
                        {
                            row.RelativeItem().Column(c =>
                            {
                                c.Item().Text($"Accreditation Number: {provider.AccreditationNumber}").Bold();
                                c.Item().Text($"Provider Code: {provider.ProviderCode ?? "PRV-" + provider.Id:D5}");
                                c.Item().Text($"ETQA Committee Decision: {provider.EtqaDecisionNumber ?? provider.EtqaCommitteeDecisionNumber ?? "RATIFIED"}");
                            });
                            row.RelativeItem().Column(c =>
                            {
                                c.Item().Text($"Commencement Date: {provider.AccreditationStartDate:dd MMMM yyyy}").Bold();
                                c.Item().Text($"Expiry Date: {provider.AccreditationEndDate:dd MMMM yyyy}").Bold();
                                c.Item().Text($"Certificate Serial: {certSerial}").FontColor(Colors.Grey.Darken3);
                            });
                            row.ConstantItem(65).Image(qrBytes);
                        });

                        // Scope summary
                        if (provider.Qualifications != null && provider.Qualifications.Any())
                        {
                            var scopes = string.Join(" • ", provider.Qualifications.Take(3).Select(q => q.QualificationTitle));
                            inner.Item().PaddingTop(4).AlignCenter().Text($"Accredited Scope: {scopes}").Italic().FontSize(9);
                        }

                        // Footer & Signatures
                        inner.Item().PaddingTop(12).Row(row =>
                        {
                            row.RelativeItem().Column(c =>
                            {
                                c.Item().PaddingTop(12).LineHorizontal(1).LineColor(Colors.Grey.Medium);
                                c.Item().AlignCenter().Text("Senior Manager: ETQA").Bold().FontSize(9);
                                c.Item().AlignCenter().Text("Quality Assurance & Partnerships").FontSize(8);
                            });
                            row.ConstantItem(40);
                            row.RelativeItem().Column(c =>
                            {
                                c.Item().AlignCenter().Text("Digital Security Seal:").FontSize(7).Bold().FontColor(Colors.Grey.Darken2);
                                c.Item().AlignCenter().Text(securitySeal).FontFamily("Courier").FontSize(6).FontColor(Colors.Grey.Darken3);
                                c.Item().AlignCenter().Text($"Certified on {DateTime.UtcNow:yyyy-MM-dd HH:mm:ss} UTC").FontSize(7).Italic();
                            });
                            row.ConstantItem(40);
                            row.RelativeItem().Column(c =>
                            {
                                c.Item().PaddingTop(12).LineHorizontal(1).LineColor(Colors.Grey.Medium);
                                c.Item().AlignCenter().Text("Chief Executive Officer").Bold().FontSize(9);
                                c.Item().AlignCenter().Text(setaName).FontSize(8);
                            });
                        });
                    });
                });
            });
        });

        return document.GeneratePdf();
    }

    public async Task<byte[]> GenerateSdpDisciplinaryNoticePdfAsync(int caseId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var disciplinaryCase = await db.SdpDisciplinaryCases
            .Include(c => c.TrainingProvider)
                .ThenInclude(tp => tp!.Organisation)
            .FirstOrDefaultAsync(c => c.Id == caseId);

        if (disciplinaryCase == null)
            throw new KeyNotFoundException($"SdpDisciplinaryCase with ID {caseId} not found.");

        var setaName = await _config.GetValueAsync("General.SetaName", "merSETA");
        var provider = disciplinaryCase.TrainingProvider;
        var verifyUrl = $"https://nsdms.merseta.org.za/verify/sanction/{disciplinaryCase.CaseNumber}";
        var qrBytes = GenerateQrBytes(verifyUrl);

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
                    col.Item().AlignCenter().Text("EDUCATION AND TRAINING QUALITY ASSURANCE (ETQA) DIVISION").FontSize(10);
                    col.Item().PaddingTop(5).LineHorizontal(1).LineColor(Colors.Red.Darken2);
                });

                page.Content().PaddingVertical(15).Column(col =>
                {
                    col.Spacing(10);

                    col.Item().Row(r =>
                    {
                        r.RelativeItem().Column(c =>
                        {
                            c.Item().Text($"Notice Date: {DateTime.UtcNow:yyyy-MM-dd}").Bold();
                            c.Item().Text($"Case Reference: {disciplinaryCase.CaseNumber}").Bold().FontColor(Colors.Red.Darken3);
                            c.Item().Text($"To: {provider?.Organisation?.CompanyName ?? "Skills Development Provider"}");
                            c.Item().Text($"Accreditation Number: {provider?.AccreditationNumber ?? "N/A"}");
                            c.Item().Text($"SDL Number: {provider?.Organisation?.SdlNumber ?? "N/A"}");
                        });
                        r.ConstantItem(70).Image(qrBytes);
                    });

                    col.Item().PaddingTop(8).Text("FORM ETQ-TP-015: STATUTORY NOTICE OF COMPLIANCE SANCTION").Bold().FontSize(13).FontColor(Colors.Red.Darken4);
                    col.Item().Text($"Please be informed that following formal investigation under merSETA ETQA Disciplinary Regulations, the ETQA Review Committee has ratified the following regulatory determination on {disciplinaryCase.ReviewCommitteeDate:yyyy-MM-dd}:");

                    col.Item().Border(1).BorderColor(Colors.Grey.Medium).Padding(10).Column(box =>
                    {
                        box.Spacing(4);
                        box.Item().Text($"Sanction Imposed: {disciplinaryCase.SanctionType?.ToUpper() ?? "FORMAL DETERMINATION"}").Bold().FontColor(Colors.Red.Darken3);
                        box.Item().Text($"Operational Status: {disciplinaryCase.Status.ToUpper()}").Bold();
                        box.Item().Text($"Review Committee Resolution: {disciplinaryCase.ReviewCommitteeDecisionNumber ?? "RATIFIED"}");
                        if (disciplinaryCase.SanctionStartDate.HasValue)
                        {
                            box.Item().Text($"Effective Period: {disciplinaryCase.SanctionStartDate:yyyy-MM-dd} to {(disciplinaryCase.SanctionEndDate.HasValue ? disciplinaryCase.SanctionEndDate.Value.ToString("yyyy-MM-dd") : "Until Further Notice")}");
                        }
                    });

                    col.Item().PaddingTop(6).Text("Summary of Allegations & Findings:").Bold();
                    col.Item().Text(disciplinaryCase.AllegationSummary);

                    if (!string.IsNullOrWhiteSpace(disciplinaryCase.InvestigationFindings))
                    {
                        col.Item().PaddingTop(4).Text("Investigation Findings:").Bold();
                        col.Item().Text(disciplinaryCase.InvestigationFindings);
                    }

                    col.Item().PaddingTop(6).Text("Statutory Directives & Learner Protection:").Bold();
                    col.Item().Text("1. During any period of suspension or de-accreditation, all new learner registrations are immediately suspended by system automated gatekeepers.");
                    col.Item().Text("2. Currently enrolled learners must continue receiving training under appointed ETQA supervisory monitors or be transferred to accredited alternate facilities.");
                    col.Item().Text("3. Appeals against this determination must be lodged in writing with the Executive Chairperson within 14 calendar days of receipt of this notice.");

                    col.Item().PaddingTop(15).Row(row =>
                    {
                        row.RelativeItem().Column(c =>
                        {
                            c.Item().Text("Yours faithfully,").FontSize(10);
                            c.Item().PaddingTop(20).LineHorizontal(1).LineColor(Colors.Grey.Medium);
                            c.Item().Text("Senior Manager: Quality Assurance & ETQA").Bold().FontSize(9);
                            c.Item().Text(setaName).FontSize(9);
                        });
                        row.ConstantItem(100);
                        row.RelativeItem().Column(c =>
                        {
                            c.Item().PaddingTop(20).LineHorizontal(1).LineColor(Colors.Grey.Medium);
                            c.Item().Text("Legal, Compliance & Governance").Bold().FontSize(9);
                            c.Item().Text(setaName).FontSize(9);
                        });
                    });
                });

                page.Footer().AlignCenter().Text(x =>
                {
                    x.Span("merSETA NSDMS ETQA Compliance System • Form ETQ-TP-015 • Page ");
                    x.CurrentPageNumber();
                    x.Span(" of ");
                    x.TotalPages();
                });
            });
        });

        return document.GeneratePdf();
    }

    public async Task<byte[]> GenerateSdpSiteInspectionReportPdfAsync(int inspectionId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var inspection = await db.SdpSiteInspections
            .Include(i => i.TrainingProvider)
                .ThenInclude(tp => tp!.Organisation)
            .Include(i => i.InspectorPerson)
            .FirstOrDefaultAsync(i => i.Id == inspectionId);

        if (inspection == null)
            throw new KeyNotFoundException($"SdpSiteInspection with ID {inspectionId} not found.");

        var setaName = await _config.GetValueAsync("General.SetaName", "merSETA");
        var provider = inspection.TrainingProvider;
        var verifyUrl = $"https://nsdms.merseta.org.za/verify/site-inspection/{inspection.Id}";
        var qrBytes = GenerateQrBytes(verifyUrl);

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
                    col.Item().AlignCenter().Text("EDUCATION AND TRAINING QUALITY ASSURANCE (ETQA) DIVISION").FontSize(10);
                    col.Item().PaddingTop(5).LineHorizontal(1).LineColor(Colors.Blue.Darken2);
                });

                page.Content().PaddingVertical(15).Column(col =>
                {
                    col.Spacing(10);

                    col.Item().Row(r =>
                    {
                        r.RelativeItem().Column(c =>
                        {
                            c.Item().Text($"Inspection Date: {inspection.InspectionDate:yyyy-MM-dd}").Bold();
                            c.Item().Text($"Provider: {provider?.Organisation?.CompanyName ?? "SDP"}").Bold();
                            c.Item().Text($"Accreditation Number: {provider?.AccreditationNumber ?? "N/A"}");
                            c.Item().Text($"Inspection Mode: {inspection.InspectionType}");
                            c.Item().Text($"QA Lead Inspector: {inspection.InspectorPerson?.FullName ?? "ETQA Officer"}");
                        });
                        r.ConstantItem(70).Image(qrBytes);
                    });

                    col.Item().PaddingTop(8).Text("ANNEXURE ETQ-TP-012: PHYSICAL SITE INSPECTION AUDIT REPORT").Bold().FontSize(13).FontColor(Colors.Blue.Darken4);

                    // Facilities measurements table
                    col.Item().Table(table =>
                    {
                        table.ColumnsDefinition(columns =>
                        {
                            columns.RelativeColumn(3);
                            columns.RelativeColumn(2);
                            columns.RelativeColumn(2);
                        });

                        table.Header(header =>
                        {
                            header.Cell().Background(Colors.Blue.Lighten4).Padding(5).Text("Audit Dimension").Bold();
                            header.Cell().Background(Colors.Blue.Lighten4).Padding(5).Text("Metric / Finding").Bold();
                            header.Cell().Background(Colors.Blue.Lighten4).Padding(5).Text("Compliance Status").Bold();
                        });

                        table.Cell().Padding(4).Text("Workshop Facility Area");
                        table.Cell().Padding(4).Text($"{inspection.WorkshopSquareMeters ?? 0} m²");
                        table.Cell().Padding(4).Text("Verified");

                        table.Cell().Padding(4).Text("Theory Classroom Area");
                        table.Cell().Padding(4).Text($"{inspection.ClassroomSquareMeters ?? 0} m²");
                        table.Cell().Padding(4).Text("Verified");

                        table.Cell().Padding(4).Text("Occupational Health & Safety (OHS)");
                        table.Cell().Padding(4).Text(inspection.HealthAndSafetyCompliant ? "Fully Compliant" : "Deficiency Noted");
                        table.Cell().Padding(4).Text(inspection.HealthAndSafetyCompliant ? "PASS" : "FAIL").Bold();

                        table.Cell().Padding(4).Text("Machine Guarding & Safety Barriers");
                        table.Cell().Padding(4).Text(inspection.MachineGuardingCompliant ? "Guards Installed" : "Non-Compliant");
                        table.Cell().Padding(4).Text(inspection.MachineGuardingCompliant ? "PASS" : "FAIL").Bold();

                        table.Cell().Padding(4).Text("Fire Safety & Emergency Extinguishers");
                        table.Cell().Padding(4).Text(inspection.FireSafetyCompliant ? "Inspected & Tagged" : "Deficiency");
                        table.Cell().Padding(4).Text(inspection.FireSafetyCompliant ? "PASS" : "FAIL").Bold();

                        table.Cell().Padding(4).Text("Learner Ablution & Sanitation");
                        table.Cell().Padding(4).Text(inspection.AblutionFacilitiesCompliant ? "Adequate Capacity" : "Inadequate");
                        table.Cell().Padding(4).Text(inspection.AblutionFacilitiesCompliant ? "PASS" : "FAIL").Bold();

                        table.Cell().Padding(4).Text("Workshop Tool Ratio Compliance");
                        table.Cell().Padding(4).Text($"{inspection.ToolRatioScore ?? 100}% Score");
                        table.Cell().Padding(4).Text((inspection.ToolRatioScore ?? 100) >= 80 ? "COMPLIANT" : "RESTRICTED").Bold();
                    });

                    col.Item().PaddingTop(6).Border(1).BorderColor(Colors.Blue.Darken2).Padding(8).Column(rec =>
                    {
                        rec.Spacing(3);
                        rec.Item().Text($"Overall Inspection Recommendation: {inspection.OverallRecommendation.ToUpper()}").Bold().FontSize(11).FontColor(Colors.Blue.Darken3);
                        if (!string.IsNullOrWhiteSpace(inspection.ConditionNotes))
                        {
                            rec.Item().Text($"Conditions / Corrective Actions: {inspection.ConditionNotes}");
                        }
                    });

                    col.Item().PaddingTop(15).Row(row =>
                    {
                        row.RelativeItem().Column(c =>
                        {
                            c.Item().Text("Inspected By:").FontSize(10);
                            c.Item().PaddingTop(20).LineHorizontal(1).LineColor(Colors.Grey.Medium);
                            c.Item().Text(inspection.InspectorPerson?.FullName ?? "ETQA Regional Officer").Bold().FontSize(9);
                            c.Item().Text("Regional Quality Assurance Officer").FontSize(9);
                        });
                        row.ConstantItem(100);
                        row.RelativeItem().Column(c =>
                        {
                            c.Item().Text("Provider Representative:").FontSize(10);
                            c.Item().PaddingTop(20).LineHorizontal(1).LineColor(Colors.Grey.Medium);
                            c.Item().Text("SDP Principal / Center Manager").Bold().FontSize(9);
                            c.Item().Text(provider?.Organisation?.CompanyName ?? "Provider Center").FontSize(9);
                        });
                    });
                });

                page.Footer().AlignCenter().Text(x =>
                {
                    x.Span("merSETA NSDMS ETQA Quality Inspection System • Form ETQ-TP-012 • Page ");
                    x.CurrentPageNumber();
                    x.Span(" of ");
                    x.TotalPages();
                });
            });
        });

        return document.GeneratePdf();
    }

    public async Task<byte[]> GenerateSdpOutcomeLetterPdfAsync(int providerId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var provider = await db.TrainingProviders
            .Include(p => p.Organisation)
            .Include(p => p.Qualifications)
            .Include(p => p.UnitStandards)
            .FirstOrDefaultAsync(p => p.Id == providerId);

        if (provider == null)
            throw new KeyNotFoundException($"TrainingProvider with ID {providerId} not found.");

        var setaName = await _config.GetValueAsync("General.SetaName", "Manufacturing, Engineering and Related Services SETA (merSETA)");
        var letterRef = $"ETQ-AL-{provider.AccreditationStartDate?.Year ?? DateTime.UtcNow.Year}-{provider.Id:D5}";
        var baseUrl = await _config.GetValueAsync("System.BaseUrl", "https://nsdms.merseta.org.za");
        var verifyUrl = $"{baseUrl.TrimEnd('/')}/verify/document/{letterRef}";
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
                    col.Item().AlignCenter().Text("EDUCATION AND TRAINING QUALITY ASSURANCE (ETQA) DIVISION").FontSize(10);
                    col.Item().PaddingTop(5).LineHorizontal(1).LineColor(Colors.Blue.Darken2);
                });

                page.Content().PaddingVertical(15).Column(col =>
                {
                    col.Spacing(10);

                    col.Item().Row(r =>
                    {
                        r.RelativeItem().Column(c =>
                        {
                            c.Item().Text($"Date: {DateTime.UtcNow:dd MMMM yyyy}").Bold();
                            c.Item().Text($"Statutory Ref: {letterRef}").Bold();
                            c.Item().Text($"Accreditation Number: {provider.AccreditationNumber ?? "N/A"}").Bold();
                            c.Item().PaddingTop(6).Text($"To: The Principal / Managing Director");
                            c.Item().Text(provider.Organisation?.CompanyName ?? "Skills Development Provider").Bold();
                            if (!string.IsNullOrWhiteSpace(provider.Organisation?.SdlNumber))
                                c.Item().Text($"Levy Number (SDL): {provider.Organisation.SdlNumber}");
                            if (!string.IsNullOrWhiteSpace(provider.Organisation?.PhysicalAddress))
                                c.Item().Text($"{provider.Organisation.PhysicalAddress}");
                        });
                        r.ConstantItem(70).Image(qrBytes);
                    });

                    col.Item().PaddingTop(8).Text("FORM ETQ-TP-001: ACCREDITATION OUTCOME DETERMINATION LETTER").Bold().FontSize(13).FontColor(Colors.Blue.Darken4);

                    col.Item().Text($"Dear Provider Principal,");

                    col.Item().Text($"The Education and Training Quality Assurance (ETQA) Committee of the {setaName} has concluded its evaluation and review of your application for accreditation as a Skills Development Provider (SDP).");

                    col.Item().Text(x =>
                    {
                        x.Span("We are pleased to inform you that your application has been formally ");
                        x.Span("APPROVED").Bold().FontColor(Colors.Green.Darken3);
                        x.Span($" with operational status '{provider.ProviderStatusCode ?? "Accredited"}' in accordance with QCTO regulations and merSETA ETQA policies.");
                    });

                    // Summary details
                    col.Item().Table(table =>
                    {
                        table.ColumnsDefinition(columns =>
                        {
                            columns.RelativeColumn(2);
                            columns.RelativeColumn(3);
                        });

                        table.Cell().Background(Colors.Grey.Lighten4).Padding(5).Text("Accreditation Number:").Bold();
                        table.Cell().Background(Colors.Grey.Lighten4).Padding(5).Text(provider.AccreditationNumber ?? "Pending");

                        table.Cell().Padding(5).Text("Accreditation Period:").Bold();
                        table.Cell().Padding(5).Text($"{provider.AccreditationStartDate:yyyy-MM-dd} to {provider.AccreditationEndDate:yyyy-MM-dd}");

                        table.Cell().Background(Colors.Grey.Lighten4).Padding(5).Text("Accreditation Stream:").Bold();
                        table.Cell().Background(Colors.Grey.Lighten4).Padding(5).Text(provider.AccreditationStream ?? "Primary merSETA Scope");

                        table.Cell().Padding(5).Text("Digital Security Seal:").Bold();
                        table.Cell().Padding(5).Text(securitySeal).FontFamily("Courier");
                    });

                    // Scope of accreditation
                    if (provider.Qualifications != null && provider.Qualifications.Any())
                    {
                        col.Item().PaddingTop(6).Text("Approved Qualification Scope:").Bold();
                        col.Item().Table(table =>
                        {
                            table.ColumnsDefinition(columns =>
                            {
                                columns.ConstantColumn(70);
                                columns.RelativeColumn(4);
                                columns.ConstantColumn(60);
                                columns.ConstantColumn(80);
                            });

                            table.Header(header =>
                            {
                                header.Cell().Background(Colors.Blue.Lighten4).Padding(4).Text("SAQA ID").Bold();
                                header.Cell().Background(Colors.Blue.Lighten4).Padding(4).Text("Qualification Title").Bold();
                                header.Cell().Background(Colors.Blue.Lighten4).Padding(4).Text("NQF").Bold();
                                header.Cell().Background(Colors.Blue.Lighten4).Padding(4).Text("Expiry").Bold();
                            });

                            foreach (var q in provider.Qualifications)
                            {
                                table.Cell().BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(4).Text(q.SaqaQualificationId.ToString());
                                table.Cell().BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(4).Text(q.QualificationTitle);
                                table.Cell().BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(4).Text(q.NqfLevel?.ToString() ?? "-");
                                table.Cell().BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(4).Text(q.ExpiryDate?.ToString("yyyy-MM-dd") ?? "-");
                            }
                        });
                    }

                    col.Item().PaddingTop(6).Text("Conditions of Accreditation:").Bold();
                    col.Item().Text("1. The provider must maintain continuous compliance with merSETA QMS policies, OHS statutory standards, and registered artisan assessor/moderator quorums.");
                    col.Item().Text("2. Re-accreditation applications must be submitted within 6 months prior to expiry to guarantee unhindered learner registration transactions.");
                    col.Item().Text("3. All training deliveries must be recorded via the NSDMS platform and adhere to verified workplace and campus locations.");

                    col.Item().PaddingTop(15).Row(row =>
                    {
                        row.RelativeItem().Column(c =>
                        {
                            c.Item().Text("Yours sincerely,").FontSize(10);
                            c.Item().PaddingTop(20).LineHorizontal(1).LineColor(Colors.Grey.Medium);
                            c.Item().Text("Senior Manager: Quality Assurance & ETQA").Bold().FontSize(9);
                            c.Item().Text(setaName).FontSize(9);
                        });
                        row.ConstantItem(100);
                        row.RelativeItem().Column(c =>
                        {
                            c.Item().PaddingTop(20).LineHorizontal(1).LineColor(Colors.Grey.Medium);
                            c.Item().Text("Chief Executive Officer / Accounting Authority").Bold().FontSize(9);
                            c.Item().Text(setaName).FontSize(9);
                        });
                    });
                });

                page.Footer().AlignCenter().Text(x =>
                {
                    x.Span("merSETA NSDMS ETQA System • Form ETQ-TP-001 • Page ");
                    x.CurrentPageNumber();
                    x.Span(" of ");
                    x.TotalPages();
                });
            });
        });

        return document.GeneratePdf();
    }
}

