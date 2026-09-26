using System.Text;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Models;
using Nsdms.Application.Services;
using Nsdms.Infrastructure.Services.DocumentCompilers;
using QuestPDF.Fluent;
using QuestPDF.Helpers;
using QuestPDF.Infrastructure;

namespace Nsdms.Infrastructure.Services;

public class ReportExportService : IReportExportService
{
    private readonly INsdmsDbContextFactory _dbFactory;
    private readonly IAuditService? _audit;
    private readonly ISystemConfigurationService? _config;

    public ReportExportService(
        INsdmsDbContextFactory dbFactory,
        IAuditService? audit = null,
        ISystemConfigurationService? config = null)
    {
        _dbFactory = dbFactory;
        _audit = audit;
        _config = config;
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

    public async Task<byte[]> GenerateSarsLevyReconCsvAsync(string finYear, string requesterUsername = "SYSTEM")
    {
        await using var db = await _dbFactory.CreateDbContextAsync();
        var files = await db.LevyFiles.ToListAsync();

        var exportTimestamp = DateTime.UtcNow;
        var sb = new StringBuilder();
        // DLP & Output Governance Watermark (AC-17)
        sb.AppendLine($"# merSETA CONFIDENTIAL - Exported by {requesterUsername} on {exportTimestamp:O} - FinYear: {finYear}");
        sb.AppendLine("LevyFileId,FileRef,FileName,ImportDate,TotalRecords,TotalAmount,Status");
        foreach (var f in files)
        {
            sb.AppendLine(System.Globalization.CultureInfo.InvariantCulture, $"{f.Id},{f.FileRef},{f.FileName},{f.ImportDate:yyyy-MM-dd},{f.TotalRecords},{f.TotalAmount:F2},{f.ImportStatusCode}");
        }

        // Immutable Transaction Audit Trail for Dataset Exfiltration (AC-17 / AC-05)
        if (_audit != null)
        {
            await _audit.LogAsync(
                "LevyFile",
                0,
                "EXPORT_DATASET",
                requesterUsername,
                new
                {
                    Action = "CSV_EXPORT",
                    Dataset = "SarsLevyRecon",
                    FinancialYear = finYear,
                    TotalFilesExported = files.Count,
                    TimestampUtc = exportTimestamp
                });
        }

        return Encoding.UTF8.GetBytes(sb.ToString());
    }

    public async Task<byte[]> GenerateItgcAuditDossierPdfAsync(AuditPeriodFilterRequest request, string exportedBy, CancellationToken cancellationToken = default)
    {
        await using var db = await _dbFactory.CreateDbContextAsync();

        var query = db.AuditLogs.AsNoTracking().AsQueryable();

        if (request.FromDateUtc.HasValue)
        {
            query = query.Where(a => a.Timestamp >= request.FromDateUtc.Value);
        }

        if (request.ToDateUtc.HasValue)
        {
            query = query.Where(a => a.Timestamp <= request.ToDateUtc.Value);
        }

        if (!string.IsNullOrWhiteSpace(request.EntityName))
        {
            var entity = request.EntityName.Trim();
            query = query.Where(a => a.EntityName == entity);
        }

        if (!string.IsNullOrWhiteSpace(request.ActionName))
        {
            var action = request.ActionName.Trim();
            query = query.Where(a => a.ActionName == action);
        }

        if (!string.IsNullOrWhiteSpace(request.Actor))
        {
            var actor = request.Actor.Trim();
            query = query.Where(a => a.Actor == actor);
        }

        if (!string.IsNullOrWhiteSpace(request.SearchTerm))
        {
            var term = request.SearchTerm.Trim();
            query = query.Where(a =>
                a.EntityName.Contains(term) ||
                a.ActionName.Contains(term) ||
                a.Actor.Contains(term) ||
                (a.MetadataJson != null && a.MetadataJson.Contains(term)));
        }

        var totalLogs = await query.CountAsync(cancellationToken);
        var sampledLogs = await query.OrderByDescending(a => a.Timestamp).Take(250).ToListAsync(cancellationToken);

        // Compute top entities and action breakdowns for period telemetry
        var topEntities = await query
            .GroupBy(a => a.EntityName)
            .OrderByDescending(g => g.Count())
            .Take(5)
            .Select(g => new { Entity = g.Key, Count = g.Count() })
            .ToDictionaryAsync(x => x.Entity, x => x.Count, cancellationToken);

        var topActions = await query
            .GroupBy(a => a.ActionName)
            .OrderByDescending(g => g.Count())
            .Take(5)
            .Select(g => new { Action = g.Key, Count = g.Count() })
            .ToDictionaryAsync(x => x.Action, x => x.Count, cancellationToken);

        var exportTime = DateTime.UtcNow;
        var hash = AuditService.ComputeReportIntegrityHash(sampledLogs);

        var baseUrl = _config != null
            ? (await _config.GetValueAsync("System.BaseUrl", "https://nsdms.merseta.org.za") ?? "https://nsdms.merseta.org.za").TrimEnd('/')
            : "https://nsdms.merseta.org.za";

        // QR verification URL linking to statutory verification endpoint
        var qrVerificationUrl = $"{baseUrl}/verify/audit/{hash}?exp={exportTime:yyyyMMddHHmmss}&by={Uri.EscapeDataString(exportedBy)}";
        var qrBytes = ItgcAuditDossierPdfCompiler.GenerateQrCodePngBytes(qrVerificationUrl);

        var compiler = new ItgcAuditDossierPdfCompiler();
        var pdfBytes = compiler.Compile(new ItgcDossierData
        {
            Request = request,
            ExportedBy = exportedBy,
            ExportedAtUtc = exportTime,
            ReportIntegrityHash = hash,
            TotalAuditLogsExamined = totalLogs,
            SampledLogs = sampledLogs,
            TopEntities = topEntities,
            TopActions = topActions,
            Controls = ItgcAuditDossierPdfCompiler.GetStandard20Controls(),
            QrCodeBytes = qrBytes
        });

        // Double-write: Mandatory EXPORT_AUDIT_REPORT audit trail entry
        if (_audit != null)
        {
            await _audit.LogActionAsync(
                "AuditLog",
                0,
                "EXPORT_AUDIT_REPORT",
                exportedBy,
                null,
                new
                {
                    Action = "EXPORT_AUDIT_REPORT",
                    DocumentType = "PDF_ITGC_DOSSIER",
                    ExportedBy = exportedBy,
                    ExportedAtUtc = exportTime,
                    Filter = new
                    {
                        request.FromDateUtc,
                        request.ToDateUtc,
                        request.EntityName,
                        request.ActionName,
                        request.Actor,
                        request.SearchTerm
                    },
                    TotalRecordsExported = totalLogs,
                    ReportIntegrityHash = hash
                });
        }

        return pdfBytes;
    }
}


