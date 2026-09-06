using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;
using QuestPDF.Fluent;
using QuestPDF.Helpers;
using QuestPDF.Infrastructure;

namespace Nsdms.Infrastructure.Services;

public partial class QuestPdfDocumentService : IPdfDocumentService
{
    #region Phase 35: ETQ-FM-005 Summative Assessment Results Form
    public async Task<byte[]> GenerateSummativeAssessmentResultsFormPdfAsync(int reportId)
    {
        await using var db = await _contextFactory.CreateDbContextAsync();
        var report = await db.SummativeAssessmentReports
            .Include(r => r.Person)
            .Include(r => r.UnitStandardAssessments)
            .Include(r => r.AssessorPerson)
            .Include(r => r.InternalModeratorPerson)
            .FirstOrDefaultAsync(r => r.Id == reportId)
            ?? throw new KeyNotFoundException($"SummativeAssessmentReport with ID {reportId} was not found.");

        var setaName = await _config.GetValueAsync("General.SetaName", "Manufacturing, Engineering and Related Services SETA (merSETA)");
        var baseUrl = await _config.GetValueAsync("System.BaseUrl", "https://nsdms.merseta.org.za");
        var verifyUrl = $"{baseUrl.TrimEnd('/')}/verify/assessment/{report.ReportNumber}";
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
                    col.Item().Row(r =>
                    {
                        r.RelativeItem(3).Column(c =>
                        {
                            c.Item().Text(setaName).Bold().FontSize(11).FontColor(Colors.Blue.Darken3);
                            c.Item().Text("EDUCATION & TRAINING QUALITY ASSURANCE (ETQA)").FontSize(9).FontColor(Colors.Grey.Darken2);
                            c.Item().Text("ETQ-FM-005: SUMMATIVE ASSESSMENT RESULTS FORM").Bold().FontSize(13).FontColor(Colors.Black);
                        });
                        r.RelativeItem(1).AlignRight().Column(c =>
                        {
                            c.Item().Width(48).Height(48).Image(qrBytes);
                            c.Item().AlignCenter().Text("Scan to Verify").FontSize(6).FontColor(Colors.Blue.Darken2);
                        });
                    });
                    col.Item().PaddingTop(4).LineHorizontal(1.5f).LineColor(Colors.Blue.Darken2);
                });

                page.Content().PaddingVertical(10).Column(col =>
                {
                    col.Spacing(8);

                    // Controlled Document Box
                    col.Item().Border(0.5f).BorderColor(Colors.Grey.Lighten1).Padding(6).Row(r =>
                    {
                        r.RelativeItem().Text($"Document Ref: ETQ-FM-005").FontSize(8).Bold();
                        r.RelativeItem().Text($"Assessment Stage: {report.AssessmentStageCode}").FontSize(8);
                        r.RelativeItem().Text($"Assessment Date: {report.AssessmentDate:yyyy-MM-dd}").FontSize(8);
                        r.RelativeItem().Text($"Status: {report.StatusCode}").FontSize(8).Bold();
                    });

                    // Candidate & Qualification Profile
                    col.Item().Background(Colors.Grey.Lighten4).Padding(6).Column(c =>
                    {
                        c.Spacing(4);
                        c.Item().Text("1. CANDIDATE & QUALIFICATION DETAILS").Bold().FontSize(9).FontColor(Colors.Blue.Darken3);
                        c.Item().Row(r =>
                        {
                            r.RelativeItem().Text($"Learner Name: {report.Person?.FirstName} {report.Person?.LastName}").Bold();
                            r.RelativeItem().Text($"National ID / Passport: {report.Person?.RsaIdNumber ?? report.Person?.PassportNumber ?? "N/A"}").Bold();
                        });
                        c.Item().Row(r =>
                        {
                            r.RelativeItem().Text($"Qualification: {report.QualificationTitle}");
                            r.RelativeItem().Text($"SAQA Qual ID: {report.SaqaQualificationId ?? "N/A"} (NQF Level {report.NqfLevel})");
                        });
                        c.Item().Row(r =>
                        {
                            r.RelativeItem().Text($"Funded Employer: {(report.IsFundedEmployer ? "Yes (50% Progress Rule Applies)" : "No (Fee-for-Service Exemption)")}");
                            r.RelativeItem().Text($"Total Credits Earned: {report.TotalCreditsEarned} / {report.TotalCreditsRequired}");
                        });
                    });

                    // Assessor and Moderator Details
                    col.Item().Border(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(6).Column(c =>
                    {
                        c.Spacing(4);
                        c.Item().Text("2. REGISTERED PRACTITIONER DETAILS").Bold().FontSize(9).FontColor(Colors.Blue.Darken3);
                        c.Item().Row(r =>
                        {
                            r.RelativeItem().Text($"Registered Assessor: {report.AssessorPerson?.FirstName} {report.AssessorPerson?.LastName} (Reg: {report.AssessorRegistrationNumber ?? "N/A"})");
                            r.RelativeItem().Text($"Internal Moderator: {report.InternalModeratorPerson?.FirstName} {report.InternalModeratorPerson?.LastName} (Reg: {report.InternalModeratorRegistrationNumber ?? "N/A"})");
                        });
                    });

                    // Unit Standards Evaluation Table
                    col.Item().Text("3. UNIT STANDARD RESULTS & OUTCOMES").Bold().FontSize(9).FontColor(Colors.Blue.Darken3);
                    col.Item().Table(table =>
                    {
                        table.ColumnsDefinition(columns =>
                        {
                            columns.ConstantColumn(65);
                            columns.RelativeColumn(3);
                            columns.ConstantColumn(35);
                            columns.ConstantColumn(40);
                            columns.ConstantColumn(50);
                            columns.ConstantColumn(65);
                            columns.ConstantColumn(65);
                        });

                        table.Header(h =>
                        {
                            h.Cell().Background(Colors.Blue.Lighten4).Padding(3).Text("Code").Bold().FontSize(8);
                            h.Cell().Background(Colors.Blue.Lighten4).Padding(3).Text("Unit Standard Title").Bold().FontSize(8);
                            h.Cell().Background(Colors.Blue.Lighten4).Padding(3).Text("NQF").Bold().FontSize(8);
                            h.Cell().Background(Colors.Blue.Lighten4).Padding(3).Text("Credits").Bold().FontSize(8);
                            h.Cell().Background(Colors.Blue.Lighten4).Padding(3).Text("Type").Bold().FontSize(8);
                            h.Cell().Background(Colors.Blue.Lighten4).Padding(3).Text("Assessor").Bold().FontSize(8);
                            h.Cell().Background(Colors.Blue.Lighten4).Padding(3).Text("Moderator").Bold().FontSize(8);
                        });

                        foreach (var us in report.UnitStandardAssessments)
                        {
                            table.Cell().BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(3).Text(us.UnitStandardCode).FontSize(8);
                            table.Cell().BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(3).Text(us.UnitStandardTitle).FontSize(8);
                            table.Cell().BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(3).Text($"L{us.NqfLevel}").FontSize(8);
                            table.Cell().BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(3).Text(us.Credits.ToString()).FontSize(8);
                            table.Cell().BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(3).Text(us.UnitStandardTypeCode).FontSize(8);
                            table.Cell().BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(3).Text(us.CompetencyStatusCode).FontSize(8)
                                .FontColor(us.CompetencyStatusCode == "Competent" ? Colors.Green.Darken3 : Colors.Orange.Darken3);
                            table.Cell().BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(3).Text(us.ModerationOutcome ?? "Pending").FontSize(8)
                                .FontColor(us.ModerationOutcome == "Upheld" ? Colors.Green.Darken3 : Colors.Grey.Darken2);
                        }
                    });

                    // Signatures & Endorsement
                    col.Item().PaddingTop(8).Text("4. DECLARATIONS & ATTESTATION").Bold().FontSize(9).FontColor(Colors.Blue.Darken3);
                    col.Item().Border(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(6).Row(r =>
                    {
                        r.RelativeItem().Column(c =>
                        {
                            c.Spacing(3);
                            c.Item().Text("Assessor Declaration:").Bold().FontSize(8);
                            c.Item().Text("I hereby attest that I evaluated the candidate in accordance with SAQA registered criteria.").FontSize(7);
                            c.Item().PaddingTop(10).LineHorizontal(0.5f).LineColor(Colors.Grey.Darken1);
                            c.Item().Text("Assessor Signature & Date").FontSize(7).AlignCenter();
                        });
                        r.ConstantItem(20);
                        r.RelativeItem().Column(c =>
                        {
                            c.Spacing(3);
                            c.Item().Text("Internal Moderator Declaration:").Bold().FontSize(8);
                            c.Item().Text("I hereby confirm internal moderation verification of the sampled assessments.").FontSize(7);
                            c.Item().PaddingTop(10).LineHorizontal(0.5f).LineColor(Colors.Grey.Darken1);
                            c.Item().Text("Moderator Signature & Date").FontSize(7).AlignCenter();
                        });
                    });
                });

                page.Footer().Column(f =>
                {
                    f.Item().LineHorizontal(0.5f).LineColor(Colors.Grey.Lighten1);
                    f.Item().PaddingTop(2).Row(r =>
                    {
                        r.RelativeItem().Text("merSETA Quality Assurance Division • Controlled Form ETQ-FM-005 • V3.2").FontSize(7).FontColor(Colors.Grey.Darken1);
                        r.RelativeItem().AlignRight().Text(x =>
                        {
                            x.Span("Page ");
                            x.CurrentPageNumber();
                            x.Span(" of ");
                            x.TotalPages();
                        });
                    });
                });
            });
        });

        return doc.GeneratePdf();
    }
    #endregion

    #region Phase 35: ETQ-TP-043 Moderation or Validation Report
    public async Task<byte[]> GenerateModerationValidationReportPdfAsync(int batchId)
    {
        await using var db = await _contextFactory.CreateDbContextAsync();
        var batch = await db.AssessmentBatches
            .Include(b => b.TrainingProvider)
            .Include(b => b.ModerationChecklists)
                .ThenInclude(c => c.ChecklistItems)
            .Include(b => b.BatchLearners)
                .ThenInclude(l => l.SummativeAssessmentReport)
                    .ThenInclude(r => r!.Person)
            .FirstOrDefaultAsync(b => b.Id == batchId)
            ?? throw new KeyNotFoundException($"AssessmentBatch with ID {batchId} was not found.");

        var setaName = await _config.GetValueAsync("General.SetaName", "Manufacturing, Engineering and Related Services SETA (merSETA)");
        var baseUrl = await _config.GetValueAsync("System.BaseUrl", "https://nsdms.merseta.org.za");
        var verifyUrl = $"{baseUrl.TrimEnd('/')}/verify/moderation-batch/{batch.BatchNumber}";
        var qrBytes = GenerateQrBytes(verifyUrl);

        var checklist = batch.ModerationChecklists.OrderByDescending(c => c.CreatedAt).FirstOrDefault();

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
                    col.Item().Row(r =>
                    {
                        r.RelativeItem(3).Column(c =>
                        {
                            c.Item().Text(setaName).Bold().FontSize(11).FontColor(Colors.Blue.Darken3);
                            c.Item().Text("EDUCATION & TRAINING QUALITY ASSURANCE (ETQA)").FontSize(9).FontColor(Colors.Grey.Darken2);
                            c.Item().Text("ETQ-TP-043: MODERATION OR VALIDATION REPORT OF SUMMATIVE ASSESSMENTS").Bold().FontSize(12).FontColor(Colors.Black);
                        });
                        r.RelativeItem(1).AlignRight().Column(c =>
                        {
                            c.Item().Width(45).Height(45).Image(qrBytes);
                            c.Item().AlignCenter().Text("Scan to Verify").FontSize(6).FontColor(Colors.Blue.Darken2);
                        });
                    });
                    col.Item().PaddingTop(4).LineHorizontal(1.5f).LineColor(Colors.Blue.Darken2);
                });

                page.Content().PaddingVertical(8).Column(col =>
                {
                    col.Spacing(7);

                    // Batch Summary Block
                    col.Item().Border(0.5f).BorderColor(Colors.Grey.Lighten1).Padding(5).Row(r =>
                    {
                        r.RelativeItem().Text($"Batch Number: {batch.BatchNumber}").Bold().FontSize(9);
                        r.RelativeItem().Text($"Stage: {batch.AssessmentStageCode}").FontSize(8);
                        r.RelativeItem().Text($"Sample: {batch.SamplePercentage}% ({batch.SampledLearnersCount}/{batch.TotalLearnersCount} learners)").FontSize(8).Bold();
                        r.RelativeItem().Text($"Status: {batch.StatusCode}").Bold().FontSize(8)
                            .FontColor(batch.StatusCode == "Upheld" ? Colors.Green.Darken3 : (batch.StatusCode.Contains("Reject") ? Colors.Red.Darken3 : Colors.Blue.Darken3));
                    });

                    // Provider & Site Visit Information
                    col.Item().Background(Colors.Grey.Lighten4).Padding(5).Column(c =>
                    {
                        c.Spacing(3);
                        c.Item().Text("1. SKILLS DEVELOPMENT PROVIDER & SITE AUDIT").Bold().FontSize(8.5f).FontColor(Colors.Blue.Darken3);
                        c.Item().Row(r =>
                        {
                            r.RelativeItem().Text($"Provider: {batch.TrainingProvider?.ProviderName ?? "Accredited SDP"}").Bold();
                            r.RelativeItem().Text($"Accreditation No: {batch.TrainingProvider?.AccreditationNumber ?? "N/A"}");
                        });
                        c.Item().Row(r =>
                        {
                            r.RelativeItem().Text($"Qualification: {batch.QualificationTitle} (SAQA ID: {batch.SaqaQualificationId ?? "N/A"})");
                            r.RelativeItem().Text($"Scheduled Visit: {batch.ScheduledSiteVisitDate?.ToString("yyyy-MM-dd") ?? "Desktop Audit"}");
                        });
                        c.Item().Row(r =>
                        {
                            r.RelativeItem().Text($"Assigned QA Officer: {batch.AssignedQaUserId ?? "Unassigned"}");
                            r.RelativeItem().Text($"Internal Moderation Date: {batch.LastInternalModerationDate?.ToString("yyyy-MM-dd") ?? "N/A"}");
                        });
                    });

                    // ETQ-TP-043 Checklist Audit
                    col.Item().Text("2. STATUTORY MODERATION CHECKLIST (ETQ-TP-043)").Bold().FontSize(8.5f).FontColor(Colors.Blue.Darken3);
                    col.Item().Table(table =>
                    {
                        table.ColumnsDefinition(columns =>
                        {
                            columns.ConstantColumn(25);
                            columns.RelativeColumn(3);
                            columns.ConstantColumn(65);
                            columns.RelativeColumn(2);
                        });

                        table.Header(h =>
                        {
                            h.Cell().Background(Colors.Blue.Lighten4).Padding(3).Text("#").Bold().FontSize(8);
                            h.Cell().Background(Colors.Blue.Lighten4).Padding(3).Text("Verification Criterion").Bold().FontSize(8);
                            h.Cell().Background(Colors.Blue.Lighten4).Padding(3).Text("Status").Bold().FontSize(8);
                            h.Cell().Background(Colors.Blue.Lighten4).Padding(3).Text("Remarks").Bold().FontSize(8);
                        });

                        if (checklist != null && checklist.ChecklistItems.Any())
                        {
                            int idx = 1;
                            foreach (var item in checklist.ChecklistItems.OrderBy(i => i.SectionNumber))
                            {
                                table.Cell().BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(3).Text(idx.ToString()).FontSize(8);
                                table.Cell().BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(3).Text(item.CriteriaTitle).FontSize(8);
                                table.Cell().BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(3).Text(item.IsCompliant ? "Compliant" : "Non-Compliant").FontSize(8)
                                    .FontColor(item.IsCompliant ? Colors.Green.Darken3 : Colors.Red.Darken3);
                                table.Cell().BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(3).Text(item.Comments ?? "-").FontSize(7);
                                idx++;
                            }
                        }
                        else
                        {
                            table.Cell().ColumnSpan(4).Padding(6).AlignCenter().Text("Checklist verification recorded electronically via QA External Moderation Workbench.").Italic().FontSize(8);
                        }
                    });

                    // Sampled Learners Section
                    col.Item().Text("3. SAMPLED LEARNERS AUDITED").Bold().FontSize(8.5f).FontColor(Colors.Blue.Darken3);
                    col.Item().Table(table =>
                    {
                        table.ColumnsDefinition(columns =>
                        {
                            columns.RelativeColumn(2);
                            columns.RelativeColumn(2);
                            columns.ConstantColumn(60);
                            columns.ConstantColumn(70);
                            columns.RelativeColumn(2);
                        });

                        table.Header(h =>
                        {
                            h.Cell().Background(Colors.Grey.Lighten3).Padding(3).Text("Learner Name").Bold().FontSize(8);
                            h.Cell().Background(Colors.Grey.Lighten3).Padding(3).Text("RSA ID / Passport").Bold().FontSize(8);
                            h.Cell().Background(Colors.Grey.Lighten3).Padding(3).Text("Credits").Bold().FontSize(8);
                            h.Cell().Background(Colors.Grey.Lighten3).Padding(3).Text("In Sample").Bold().FontSize(8);
                            h.Cell().Background(Colors.Grey.Lighten3).Padding(3).Text("Audit Outcome").Bold().FontSize(8);
                        });

                        foreach (var learner in batch.BatchLearners)
                        {
                            var person = learner.SummativeAssessmentReport?.Person;
                            table.Cell().BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(3).Text($"{person?.FirstName} {person?.LastName}").FontSize(8);
                            table.Cell().BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(3).Text(person?.RsaIdNumber ?? person?.PassportNumber ?? "N/A").FontSize(8);
                            table.Cell().BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(3).Text(learner.SummativeAssessmentReport?.TotalCreditsEarned.ToString() ?? "-").FontSize(8);
                            table.Cell().BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(3).Text(learner.IsSelectedInSample ? "Sampled" : "Not Sampled").Bold().FontSize(8)
                                .FontColor(learner.IsSelectedInSample ? Colors.Blue.Darken3 : Colors.Grey.Darken1);
                            table.Cell().BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(3).Text(learner.LearnerOutcomeStatus).FontSize(8);
                        }
                    });

                    // Outcome & VACS Decision Box
                    col.Item().Border(0.5f).BorderColor(Colors.Grey.Lighten1).Padding(5).Column(c =>
                    {
                        c.Spacing(3);
                        c.Item().Text("4. EXTERNAL MODERATION FINDING & DECISION").Bold().FontSize(8.5f).FontColor(Colors.Blue.Darken3);
                        c.Item().Row(r =>
                        {
                            r.RelativeItem().Text($"Decision: {batch.StatusCode}").Bold().FontSize(9)
                                .FontColor(batch.StatusCode == "Upheld" ? Colors.Green.Darken3 : Colors.Red.Darken3);
                            r.RelativeItem().Text($"QA Inspection Date: {checklist?.DateOfModeration.ToString("yyyy-MM-dd") ?? DateTime.UtcNow.ToString("yyyy-MM-dd")}");
                        });

                        if (checklist?.ValidationDecisionCode == "Rejected")
                        {
                            c.Item().Text($"VACS Failure Categories: {checklist.VacsPrincipleViolatedCode ?? checklist.PrimaryRejectionReasonCode ?? "Non-Compliant"}").Bold().FontSize(8).FontColor(Colors.Red.Darken3);
                            c.Item().Text($"Remedial Action Required: {checklist.RemedialActionRequired ?? checklist.RejectionRemarks ?? "Provider to submit remedial evidence."}").FontSize(8);
                        }
                    });
                });

                page.Footer().Column(f =>
                {
                    f.Item().LineHorizontal(0.5f).LineColor(Colors.Grey.Lighten1);
                    f.Item().PaddingTop(2).Row(r =>
                    {
                        r.RelativeItem().Text("merSETA Quality Assurance Division • Statutory Validation Report ETQ-TP-043").FontSize(7).FontColor(Colors.Grey.Darken1);
                        r.RelativeItem().AlignRight().Text(x =>
                        {
                            x.Span("Page ");
                            x.CurrentPageNumber();
                            x.Span(" of ");
                            x.TotalPages();
                        });
                    });
                });
            });
        });

        return doc.GeneratePdf();
    }
    #endregion

    #region Phase 35: Statutory National Qualification Certificate
    public async Task<byte[]> GenerateLearnerQualificationCertificatePdfAsync(int certificateId)
    {
        await using var db = await _contextFactory.CreateDbContextAsync();
        var cert = await db.LearnerCertificates
            .Include(c => c.Person)
            .Include(c => c.SummativeAssessmentReport)
            .FirstOrDefaultAsync(c => c.Id == certificateId)
            ?? throw new KeyNotFoundException($"LearnerCertificate with ID {certificateId} was not found.");

        var setaName = await _config.GetValueAsync("General.SetaName", "Manufacturing, Engineering and Related Services SETA (merSETA)");
        var baseUrl = await _config.GetValueAsync("System.BaseUrl", "https://nsdms.merseta.org.za");
        var verifyUrl = $"{baseUrl.TrimEnd('/')}/verify/certificate/{cert.CertificateNumber}";
        var qrBytes = GenerateQrBytes(verifyUrl);

        var doc = Document.Create(container =>
        {
            container.Page(page =>
            {
                page.Size(PageSizes.A4.Landscape());
                page.Margin(1.5f, Unit.Centimetre);
                page.PageColor(Colors.White);
                page.DefaultTextStyle(x => x.FontSize(11).FontFamily("Arial"));

                page.Content().Border(2).BorderColor(Colors.Blue.Darken4).Padding(15).Column(col =>
                {
                    col.Spacing(8);

                    // Certificate Header
                    col.Item().AlignCenter().Text("REPUBLIC OF SOUTH AFRICA").Bold().FontSize(15).FontColor(Colors.Grey.Darken3);
                    col.Item().AlignCenter().Text(setaName).Bold().FontSize(13).FontColor(Colors.Blue.Darken3);
                    col.Item().AlignCenter().Text("NATIONAL QUALIFICATIONS FRAMEWORK").FontSize(10).FontColor(Colors.Grey.Darken2);
                    col.Item().PaddingTop(2).LineHorizontal(1.5f).LineColor(Colors.Blue.Darken3);

                    col.Item().PaddingTop(12).AlignCenter().Text("THIS IS TO CERTIFY THAT").FontSize(11).Italic();

                    // Learner Name Block
                    col.Item().AlignCenter().Text($"{cert.Person?.FirstName} {cert.Person?.LastName}").Bold().FontSize(22).FontColor(Colors.Blue.Darken4);
                    col.Item().AlignCenter().Text($"National Identity / Passport Number: {cert.Person?.RsaIdNumber ?? cert.Person?.PassportNumber ?? "N/A"}").FontSize(11);

                    col.Item().PaddingTop(6).AlignCenter().Text("has been awarded the National Qualification:").FontSize(11).Italic();

                    // Qualification Block
                    col.Item().AlignCenter().Text(cert.QualificationTitle).Bold().FontSize(16).FontColor(Colors.Black);
                    col.Item().AlignCenter().Text($"SAQA Qualification ID: {cert.SaqaQualificationId ?? "N/A"} • NQF Level {cert.NqfLevel}").FontSize(10).FontColor(Colors.Grey.Darken3);

                    col.Item().PaddingTop(8).Row(r =>
                    {
                        r.RelativeItem(2).Column(c =>
                        {
                            c.Spacing(3);
                            c.Item().Text($"Certificate Number: {cert.CertificateNumber}").Bold().FontSize(11).FontColor(Colors.Blue.Darken4);
                            c.Item().Text($"Date of Issue (Moderation Date): {cert.IssueDate:yyyy-MM-dd}").FontSize(10);
                            c.Item().Text($"Digital Security Seal: {cert.TamperProofHashSha256[..16]}...").FontSize(7).FontColor(Colors.Grey.Darken2);
                        });

                        r.RelativeItem(1).AlignCenter().Column(c =>
                        {
                            c.Item().Width(60).Height(60).Image(qrBytes);
                            c.Item().AlignCenter().Text("Scan to Verify Credential").FontSize(7).FontColor(Colors.Blue.Darken2);
                        });
                    });

                    col.Item().PaddingTop(15).Row(r =>
                    {
                        r.RelativeItem().AlignCenter().Column(c =>
                        {
                            c.Item().LineHorizontal(0.75f).LineColor(Colors.Black);
                            c.Item().PaddingTop(2).Text("CHIEF EXECUTIVE OFFICER").Bold().FontSize(9);
                            c.Item().Text(setaName).FontSize(7);
                        });
                        r.ConstantItem(60);
                        r.RelativeItem().AlignCenter().Column(c =>
                        {
                            c.Item().LineHorizontal(0.75f).LineColor(Colors.Black);
                            c.Item().PaddingTop(2).Text("SENIOR MANAGER: QUALITY ASSURANCE").Bold().FontSize(9);
                            c.Item().Text("Education & Training Quality Assurance (ETQA)").FontSize(7);
                        });
                    });
                });
            });
        });

        return doc.GeneratePdf();
    }
    #endregion

    #region Phase 35: Batch Distribution Letter
    public async Task<byte[]> GenerateBatchDistributionLetterPdfAsync(int batchId)
    {
        await using var db = await _contextFactory.CreateDbContextAsync();
        var batch = await db.AssessmentBatches
            .Include(b => b.TrainingProvider)
            .Include(b => b.BatchLearners)
                .ThenInclude(l => l.SummativeAssessmentReport)
                    .ThenInclude(r => r!.Person)
            .FirstOrDefaultAsync(b => b.Id == batchId)
            ?? throw new KeyNotFoundException($"AssessmentBatch with ID {batchId} was not found.");

        var setaName = await _config.GetValueAsync("General.SetaName", "Manufacturing, Engineering and Related Services SETA (merSETA)");
        var baseUrl = await _config.GetValueAsync("System.BaseUrl", "https://nsdms.merseta.org.za");
        var verifyUrl = $"{baseUrl.TrimEnd('/')}/verify/distribution-batch/{batch.BatchNumber}";
        var qrBytes = GenerateQrBytes(verifyUrl);

        var reportIds = batch.BatchLearners.Select(l => l.SummativeAssessmentReportId).ToList();
        var certificates = await db.LearnerCertificates
            .Where(c => reportIds.Contains(c.SummativeAssessmentReportId))
            .ToDictionaryAsync(c => c.SummativeAssessmentReportId, c => c.CertificateNumber);

        var sors = await db.StatementOfResults
            .Where(s => reportIds.Contains(s.SummativeAssessmentReportId))
            .ToDictionaryAsync(s => s.SummativeAssessmentReportId, s => s.SorSerialNumber);

        var doc = Document.Create(container =>
        {
            container.Page(page =>
            {
                page.Size(PageSizes.A4);
                page.Margin(2, Unit.Centimetre);
                page.PageColor(Colors.White);
                page.DefaultTextStyle(x => x.FontSize(9.5f).FontFamily("Arial"));

                page.Header().Column(col =>
                {
                    col.Item().Row(r =>
                    {
                        r.RelativeItem(3).Column(c =>
                        {
                            c.Item().Text(setaName).Bold().FontSize(12).FontColor(Colors.Blue.Darken3);
                            c.Item().Text("QUALITY ASSURANCE & CERTIFICATION DIVISION").FontSize(9).FontColor(Colors.Grey.Darken2);
                        });
                        r.RelativeItem(1).AlignRight().Column(c =>
                        {
                            c.Item().Width(45).Height(45).Image(qrBytes);
                        });
                    });
                    col.Item().PaddingTop(4).LineHorizontal(1).LineColor(Colors.Blue.Darken2);
                });

                page.Content().PaddingVertical(10).Column(col =>
                {
                    col.Spacing(8);

                    col.Item().Row(r =>
                    {
                        r.RelativeItem().Text($"Date: {DateTime.UtcNow:yyyy-MM-dd}").Bold();
                        r.RelativeItem().AlignRight().Text($"Batch Ref: {batch.BatchNumber}").Bold();
                    });

                    col.Item().Text($"To: The Principal / SDF\n{batch.TrainingProvider?.ProviderName ?? "Accredited Skills Development Provider"}\nAccreditation Number: {batch.TrainingProvider?.AccreditationNumber ?? "N/A"}").Bold();

                    col.Item().PaddingTop(6).Text($"SUBJECT: TRANSMITTAL & DISTRIBUTION OF STATUTORY QUALIFICATION CERTIFICATES AND STATEMENTS OF RESULTS").Bold().FontSize(10.5f).FontColor(Colors.Blue.Darken3);

                    col.Item().Text("Dear Skills Development Provider,");

                    col.Item().Text($"We have the pleasure to inform you that external moderation for the summative assessment batch {batch.BatchNumber} in respect of {batch.QualificationTitle} (SAQA ID: {batch.SaqaQualificationId ?? "N/A"}) has been formally upheld and certified by merSETA ETQA.");

                    col.Item().Text("Enclosed herewith please find the official certificates and Statements of Results (SOR) issued for the learners listed below:");

                    col.Item().Table(table =>
                    {
                        table.ColumnsDefinition(columns =>
                        {
                            columns.ConstantColumn(25);
                            columns.RelativeColumn(3);
                            columns.RelativeColumn(2);
                            columns.RelativeColumn(2);
                            columns.RelativeColumn(2);
                        });

                        table.Header(h =>
                        {
                            h.Cell().Background(Colors.Grey.Lighten3).Padding(3).Text("#").Bold().FontSize(8);
                            h.Cell().Background(Colors.Grey.Lighten3).Padding(3).Text("Learner Name").Bold().FontSize(8);
                            h.Cell().Background(Colors.Grey.Lighten3).Padding(3).Text("ID Number").Bold().FontSize(8);
                            h.Cell().Background(Colors.Grey.Lighten3).Padding(3).Text("Certificate No.").Bold().FontSize(8);
                            h.Cell().Background(Colors.Grey.Lighten3).Padding(3).Text("SOR Serial No.").Bold().FontSize(8);
                        });

                        int idx = 1;
                        foreach (var learner in batch.BatchLearners)
                        {
                            var person = learner.SummativeAssessmentReport?.Person;
                            certificates.TryGetValue(learner.SummativeAssessmentReportId, out var certNo);
                            sors.TryGetValue(learner.SummativeAssessmentReportId, out var sorNo);

                            table.Cell().BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(3).Text(idx.ToString()).FontSize(8);
                            table.Cell().BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(3).Text($"{person?.FirstName} {person?.LastName}").FontSize(8);
                            table.Cell().BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(3).Text(person?.RsaIdNumber ?? person?.PassportNumber ?? "N/A").FontSize(8);
                            table.Cell().BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(3).Text(certNo ?? "N/A").Bold().FontSize(8);
                            table.Cell().BorderBottom(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(3).Text(sorNo ?? "N/A").FontSize(8);
                            idx++;
                        }
                    });

                    col.Item().PaddingTop(6).Text("STATUTORY SAFE CUSTODY INSTRUCTIONS:").Bold().FontSize(9);
                    col.Item().Text("1. In accordance with statutory regulations, original certificates must be handed over to the respective learners within 14 working days of receipt.").FontSize(8.5f);
                    col.Item().Text("2. The attached Certificate Distribution Event Log must be completed and signed by each recipient learner upon collection.").FontSize(8.5f);
                    col.Item().Text("3. Re-print of certificates is strictly restricted and requires ETQA review; distribution letters may be reproduced for audit purposes.").FontSize(8.5f);

                    col.Item().PaddingTop(15).Row(r =>
                    {
                        r.RelativeItem().Column(c =>
                        {
                            c.Item().Text("Yours faithfully,").FontSize(9);
                            c.Item().PaddingTop(15).LineHorizontal(0.5f).LineColor(Colors.Black);
                            c.Item().Text("Certification & Records Officer").Bold().FontSize(8.5f);
                            c.Item().Text("merSETA ETQA Division").FontSize(8);
                        });
                        r.ConstantItem(60);
                        r.RelativeItem().Column(c =>
                        {
                            c.Item().Text("Provider Acknowledgement of Receipt:").FontSize(9);
                            c.Item().PaddingTop(15).LineHorizontal(0.5f).LineColor(Colors.Black);
                            c.Item().Text("SDP Principal / SDF Signature & Date").Bold().FontSize(8.5f);
                            c.Item().Text("Official SDP Stamp").FontSize(8);
                        });
                    });
                });

                page.Footer().Column(f =>
                {
                    f.Item().LineHorizontal(0.5f).LineColor(Colors.Grey.Lighten1);
                    f.Item().PaddingTop(2).Row(r =>
                    {
                        r.RelativeItem().Text("merSETA Quality Assurance Division • Controlled Letter ETQ-LT-012").FontSize(7).FontColor(Colors.Grey.Darken1);
                        r.RelativeItem().AlignRight().Text(x =>
                        {
                            x.Span("Page ");
                            x.CurrentPageNumber();
                            x.Span(" of ");
                            x.TotalPages();
                        });
                    });
                });
            });
        });

        return doc.GeneratePdf();
    }
    #endregion

    #region Phase 35: Consolidated Multi-Learner Batch Certificates PDF
    public async Task<byte[]> GenerateBatchConsolidatedCertificatesPdfAsync(int batchId)
    {
        await using var db = await _contextFactory.CreateDbContextAsync();
        var batch = await db.AssessmentBatches
            .Include(b => b.BatchLearners)
            .FirstOrDefaultAsync(b => b.Id == batchId)
            ?? throw new KeyNotFoundException($"AssessmentBatch with ID {batchId} was not found.");

        var reportIds = batch.BatchLearners.Select(l => l.SummativeAssessmentReportId).ToList();

        var certs = await db.LearnerCertificates
            .Include(c => c.Person)
            .Include(c => c.SummativeAssessmentReport)
            .Where(c => reportIds.Contains(c.SummativeAssessmentReportId))
            .OrderBy(c => c.CertificateNumber)
            .ToListAsync();

        if (certs.Count == 0)
        {
            throw new InvalidOperationException($"No issued qualification certificates found for Batch {batch.BatchNumber}.");
        }

        var setaName = await _config.GetValueAsync("General.SetaName", "Manufacturing, Engineering and Related Services SETA (merSETA)");
        var baseUrl = await _config.GetValueAsync("System.BaseUrl", "https://nsdms.merseta.org.za");

        var doc = Document.Create(container =>
        {
            foreach (var cert in certs)
            {
                var verifyUrl = $"{baseUrl.TrimEnd('/')}/verify/certificate/{cert.CertificateNumber}";
                var qrBytes = GenerateQrBytes(verifyUrl);

                container.Page(page =>
                {
                    page.Size(PageSizes.A4.Landscape());
                    page.Margin(1.5f, Unit.Centimetre);
                    page.PageColor(Colors.White);
                    page.DefaultTextStyle(x => x.FontSize(11).FontFamily("Arial"));

                    page.Content().Border(2).BorderColor(Colors.Blue.Darken4).Padding(15).Column(col =>
                    {
                        col.Spacing(8);

                        // Certificate Header
                        col.Item().AlignCenter().Text("REPUBLIC OF SOUTH AFRICA").Bold().FontSize(15).FontColor(Colors.Grey.Darken3);
                        col.Item().AlignCenter().Text(setaName).Bold().FontSize(13).FontColor(Colors.Blue.Darken3);
                        col.Item().AlignCenter().Text("NATIONAL QUALIFICATIONS FRAMEWORK").FontSize(10).FontColor(Colors.Grey.Darken2);
                        col.Item().PaddingTop(2).LineHorizontal(1.5f).LineColor(Colors.Blue.Darken3);

                        col.Item().PaddingTop(12).AlignCenter().Text("THIS IS TO CERTIFY THAT").FontSize(11).Italic();

                        // Learner Name Block
                        col.Item().AlignCenter().Text($"{cert.Person?.FirstName} {cert.Person?.LastName}").Bold().FontSize(22).FontColor(Colors.Blue.Darken4);
                        col.Item().AlignCenter().Text($"National Identity / Passport Number: {cert.Person?.RsaIdNumber ?? cert.Person?.PassportNumber ?? "N/A"}").FontSize(11);

                        col.Item().PaddingTop(6).AlignCenter().Text("has been awarded the National Qualification:").FontSize(11).Italic();

                        // Qualification Block
                        col.Item().AlignCenter().Text(cert.QualificationTitle).Bold().FontSize(16).FontColor(Colors.Black);
                        col.Item().AlignCenter().Text($"SAQA Qualification ID: {cert.SaqaQualificationId ?? "N/A"} • NQF Level {cert.NqfLevel}").FontSize(10).FontColor(Colors.Grey.Darken3);

                        col.Item().PaddingTop(8).Row(r =>
                        {
                            r.RelativeItem(2).Column(c =>
                            {
                                c.Spacing(3);
                                c.Item().Text($"Certificate Number: {cert.CertificateNumber}").Bold().FontSize(11).FontColor(Colors.Blue.Darken4);
                                c.Item().Text($"Date of Issue (Moderation Date): {cert.IssueDate:yyyy-MM-dd}").FontSize(10);
                                c.Item().Text($"Digital Security Seal: {cert.TamperProofHashSha256[..16]}...").FontSize(7).FontColor(Colors.Grey.Darken2);
                            });

                            r.RelativeItem(1).AlignCenter().Column(c =>
                            {
                                c.Item().Width(60).Height(60).Image(qrBytes);
                                c.Item().AlignCenter().Text("Scan to Verify Credential").FontSize(7).FontColor(Colors.Blue.Darken2);
                            });
                        });

                        col.Item().PaddingTop(15).Row(r =>
                        {
                            r.RelativeItem().AlignCenter().Column(c =>
                            {
                                c.Item().LineHorizontal(0.75f).LineColor(Colors.Black);
                                c.Item().PaddingTop(2).Text("CHIEF EXECUTIVE OFFICER").Bold().FontSize(9);
                                c.Item().Text(setaName).FontSize(7);
                            });
                            r.ConstantItem(60);
                            r.RelativeItem().AlignCenter().Column(c =>
                            {
                                c.Item().LineHorizontal(0.75f).LineColor(Colors.Black);
                                c.Item().PaddingTop(2).Text("SENIOR MANAGER: QUALITY ASSURANCE").Bold().FontSize(9);
                                c.Item().Text("Education & Training Quality Assurance (ETQA)").FontSize(7);
                            });
                        });
                    });
                });
            }
        });

        return doc.GeneratePdf();
    }
    #endregion
}
