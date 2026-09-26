using Nsdms.Application.Models;

namespace Nsdms.Application.Services;

/// <summary>
/// Service contract for enterprise PDF and CSV data exports (MOA, SARS, ITGC Audit Dossier).
/// </summary>
public interface IReportExportService
{
    Task<byte[]> GenerateGrantMoaAgreementPdfAsync(int moaId);
    Task<byte[]> GenerateSarsLevyReconCsvAsync(string finYear, string requesterUsername = "SYSTEM");
    Task<byte[]> GenerateItgcAuditDossierPdfAsync(AuditPeriodFilterRequest request, string exportedBy, CancellationToken cancellationToken = default);
}

