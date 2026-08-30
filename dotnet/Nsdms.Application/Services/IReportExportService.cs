namespace Nsdms.Application.Services;

/// <summary>
/// Service contract for enterprise PDF and CSV data exports (MOA, SARS).
/// </summary>
public interface IReportExportService
{
    Task<byte[]> GenerateGrantMoaAgreementPdfAsync(int moaId);
    Task<byte[]> GenerateSarsLevyReconCsvAsync(string finYear);
}
