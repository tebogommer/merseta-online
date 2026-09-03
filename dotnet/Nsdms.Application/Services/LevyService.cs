using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;
using System.Globalization;

namespace Nsdms.Application.Services;

public record StatutoryLevySplit(
    decimal TotalAmount,
    decimal MandatoryGrantAmount,      // 20.0%
    decimal DiscretionaryGrantAmount,  // 49.5%
    decimal AdminLevyAmount,           // 10.5%
    decimal QctoLevyAmount,            // 0.5%
    decimal TotalSetaPortion           // 80.5%
);

public record ReconciliationResult(
    int TotalRecords,
    int ReconciledCount,
    int UnreconciledCount,
    decimal TotalAmount,
    decimal ReconciledAmount,
    string Status
);

public interface ILevyService
{
    StatutoryLevySplit CalculateStatutorySplit(decimal totalLevyAmount);
    bool ValidateLevySplit(decimal totalLevy, decimal mandatory, decimal discretionary, decimal admin, decimal qcto, decimal tolerance = 0.05m);
    List<LevyFileLine> ParseSarsFileContent(string content);

    Task<LevyFile> ParseSarsFileAsync(string fileContent, string fileName, string currentUsername = "SYSTEM");
    Task<LevyFile> ImportLevyFileAsync(string fileName, string fileContent, string currentUsername = "SYSTEM");

    Task<LevyFile?> GetByIdAsync(int id);
    Task<LevyFile?> GetLevyFileByIdAsync(int id);
    Task<List<LevyFile>> GetAllAsync(string? search = null);
    Task<List<LevyFile>> GetAllLevyFilesAsync(string? search = null);
    Task<LevyFile> CreateAsync(LevyFile file, string currentUsername = "SYSTEM");
    Task<LevyFile> UpdateAsync(LevyFile file, string currentUsername = "SYSTEM");
    Task<bool> DeleteAsync(int id, string currentUsername = "SYSTEM");

    Task<List<LevyFileLine>> GetLineItemsAsync(int levyFileId, string? sdlSearch = null);
    Task<LevyFileLine> AddLineItemAsync(LevyFileLine line, string currentUsername = "SYSTEM");
    Task<bool> RemoveLineItemAsync(long lineId, string currentUsername = "SYSTEM");

    Task<bool> ReconcileLevyLineAsync(long levyFileLineId, string currentUsername = "SYSTEM");
    Task<bool> ReconcileLineItemAsync(long lineId, string currentUsername = "SYSTEM");
    Task<ReconciliationResult> ReconcileLevyFileAsync(int levyFileId, string currentUsername = "SYSTEM");
    Task<ReconciliationResult> ReconcileAllLinesAsync(int levyFileId, string currentUsername = "SYSTEM");
    Task<int> ReconcileEmployerLeviesAsync(string sdlNumber, string schemeYear, string currentUsername = "SYSTEM");

    Task<List<SarsLevyDeviationDto>> GetLevyDeviationReportAsync(string? schemeYear = null, string? chamber = null);
    Task<List<ChamberLevyBreakdownDto>> GetChamberLevyBreakdownAsync(string? schemeYear = null);
    Task<List<SarsSchemeYearCalculation>> GetSchemeYearCalculationsAsync();
    Task<SarsSchemeYearCalculation> SaveSchemeYearCalculationAsync(SarsSchemeYearCalculation config, string currentUsername = "SYSTEM");
}

public class LevyService : ILevyService
{
    // Statutory ratios according to the Skills Development Act
    public const decimal MandatoryRate = 0.200m;     // 20.0%
    public const decimal DiscretionaryRate = 0.495m; // 49.5%
    public const decimal AdminRate = 0.105m;         // 10.5%
    public const decimal QctoRate = 0.005m;          // 0.5%

    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;

    public LevyService(INsdmsDbContextFactory contextFactory, IAuditService audit)
    {
        _contextFactory = contextFactory;
        _audit = audit;
    }

    /// <summary>
    /// Calculates the statutory 4-way levy split using the Hare-Niemeyer (Largest Remainder) Method.
    /// Guarantees zero cent-drift and exact mathematical balance across Mandatory (20%), Discretionary (49.5%), Admin (10.5%), and QCTO (0.5%).
    /// </summary>
    public StatutoryLevySplit CalculateStatutorySplit(decimal totalLevyAmount)
    {
        if (totalLevyAmount <= 0)
        {
            return new StatutoryLevySplit(0, 0, 0, 0, 0, 0);
        }

        // Total statutory SETA portion is exactly 80.5% (20% + 49.5% + 10.5% + 0.5%)
        long totalTargetCents = (long)Math.Round(totalLevyAmount * 0.805m * 100m, MidpointRounding.AwayFromZero);

        // Calculate unrounded exact cents for each statutory component
        decimal unroundedMg = totalLevyAmount * 20.0m;
        decimal unroundedDg = totalLevyAmount * 49.5m;
        decimal unroundedAdmin = totalLevyAmount * 10.5m;
        decimal unroundedQcto = totalLevyAmount * 0.5m;

        // Base integer floors
        long baseMg = (long)Math.Floor(unroundedMg);
        long baseDg = (long)Math.Floor(unroundedDg);
        long baseAdmin = (long)Math.Floor(unroundedAdmin);
        long baseQcto = (long)Math.Floor(unroundedQcto);

        // Fractional remainders
        var remainders = new List<(string Component, decimal Remainder)>
        {
            ("DG", unroundedDg - baseDg),
            ("MG", unroundedMg - baseMg),
            ("ADMIN", unroundedAdmin - baseAdmin),
            ("QCTO", unroundedQcto - baseQcto)
        };

        long allocatedCents = baseMg + baseDg + baseAdmin + baseQcto;
        long leftoverCents = totalTargetCents - allocatedCents;

        // Distribute leftover cents to components in descending order of fractional remainder
        var sortedRemainders = remainders.OrderByDescending(r => r.Remainder).ToList();
        for (int i = 0; i < leftoverCents && i < sortedRemainders.Count; i++)
        {
            var comp = sortedRemainders[i].Component;
            if (comp == "DG") baseDg++;
            else if (comp == "MG") baseMg++;
            else if (comp == "ADMIN") baseAdmin++;
            else if (comp == "QCTO") baseQcto++;
        }

        decimal mandatory = baseMg / 100m;
        decimal discretionary = baseDg / 100m;
        decimal admin = baseAdmin / 100m;
        decimal qcto = baseQcto / 100m;
        decimal totalSeta = mandatory + discretionary + admin + qcto;

        return new StatutoryLevySplit(
            TotalAmount: totalLevyAmount,
            MandatoryGrantAmount: mandatory,
            DiscretionaryGrantAmount: discretionary,
            AdminLevyAmount: admin,
            QctoLevyAmount: qcto,
            TotalSetaPortion: totalSeta
        );
    }

    public bool ValidateLevySplit(decimal totalLevy, decimal mandatory, decimal discretionary, decimal admin, decimal qcto, decimal tolerance = 0.00m)
    {
        if (totalLevy <= 0)
        {
            return false;
        }

        var expected = CalculateStatutorySplit(totalLevy);

        var isMandatoryValid = Math.Abs(mandatory - expected.MandatoryGrantAmount) <= tolerance;
        var isDiscretionaryValid = Math.Abs(discretionary - expected.DiscretionaryGrantAmount) <= tolerance;
        var isAdminValid = Math.Abs(admin - expected.AdminLevyAmount) <= tolerance;
        var isQctoValid = Math.Abs(qcto - expected.QctoLevyAmount) <= tolerance;
        var isTotalValid = Math.Abs((mandatory + discretionary + admin + qcto) - expected.TotalSetaPortion) <= tolerance;

        return isMandatoryValid && isDiscretionaryValid && isAdminValid && isQctoValid && isTotalValid;
    }

    public List<LevyFileLine> ParseSarsFileContent(string content)
    {
        var lines = new List<LevyFileLine>();
        if (string.IsNullOrWhiteSpace(content))
        {
            return lines;
        }

        var rawLines = content.Split(new[] { "\r\n", "\r", "\n" }, StringSplitOptions.RemoveEmptyEntries);

        foreach (var raw in rawLines)
        {
            var trimmed = raw.Trim();
            if (string.IsNullOrEmpty(trimmed) || trimmed.StartsWith("#") || trimmed.StartsWith("//"))
            {
                continue;
            }

            if (trimmed.Contains('|') || trimmed.Contains(',') || trimmed.Contains('\t') || trimmed.Contains(';'))
            {
                char delimiter = trimmed.Contains('|') ? '|' :
                                 trimmed.Contains('\t') ? '\t' :
                                 trimmed.Contains(';') ? ';' : ',';

                var parts = trimmed.Split(delimiter).Select(p => p.Trim().Trim('"')).ToArray();
                if (parts.Length < 2)
                {
                    continue;
                }

                if (parts[0].Equals("SDL", StringComparison.OrdinalIgnoreCase) ||
                    parts[0].Equals("SdlNumber", StringComparison.OrdinalIgnoreCase) ||
                    parts[0].Equals("SDL_NO", StringComparison.OrdinalIgnoreCase) ||
                    parts[0].Equals("REF_NO", StringComparison.OrdinalIgnoreCase) ||
                    parts[0].Equals("TRAILER", StringComparison.OrdinalIgnoreCase) ||
                    parts[0].Equals("CONTROL", StringComparison.OrdinalIgnoreCase) ||
                    parts[0].Equals("EOF", StringComparison.OrdinalIgnoreCase))
                {
                    continue;
                }

                var line = ParseDelimitedLine(parts);
                if (line != null)
                {
                    lines.Add(line);
                }
            }
            else
            {
                var line = ParseFixedWidthLine(trimmed);
                if (line != null)
                {
                    lines.Add(line);
                }
            }
        }

        return lines;
    }

    private LevyFileLine? ParseDelimitedLine(string[] parts)
    {
        if (parts.Length < 2) return null;

        var rawSdl = parts[0].Trim().ToUpperInvariant();
        if (string.IsNullOrWhiteSpace(rawSdl)) return null;

        var schemeYear = parts.Length > 1 && !string.IsNullOrWhiteSpace(parts[1])
            ? parts[1].Trim()
            : DateTime.UtcNow.Year.ToString();

        decimal totalAmount = 0m;
        decimal mandatory = 0m;
        decimal discretionary = 0m;
        decimal admin = 0m;
        decimal qcto = 0m;
        decimal interest = 0m;
        decimal penalty = 0m;
        string? extractedSic = null;

        // Case 1: Full statutory 8+ column SARS file (SDL, Year, Mand, Disc, Admin, Qcto, Int, Pen, [Total], [SIC])
        if (parts.Length >= 8)
        {
            decimal.TryParse(parts[2], NumberStyles.Any, CultureInfo.InvariantCulture, out mandatory);
            decimal.TryParse(parts[3], NumberStyles.Any, CultureInfo.InvariantCulture, out discretionary);
            decimal.TryParse(parts[4], NumberStyles.Any, CultureInfo.InvariantCulture, out admin);
            decimal.TryParse(parts[5], NumberStyles.Any, CultureInfo.InvariantCulture, out qcto);
            decimal.TryParse(parts[6], NumberStyles.Any, CultureInfo.InvariantCulture, out interest);
            decimal.TryParse(parts[7], NumberStyles.Any, CultureInfo.InvariantCulture, out penalty);

            if (parts.Length >= 9 && decimal.TryParse(parts[8], NumberStyles.Any, CultureInfo.InvariantCulture, out var parsedTotal))
            {
                totalAmount = parsedTotal;
            }
            else
            {
                totalAmount = mandatory + discretionary + admin + qcto + interest + penalty;
            }

            if (parts.Length >= 10 && parts[9].Trim().Length == 5 && parts[9].Trim().All(char.IsDigit))
            {
                extractedSic = parts[9].Trim();
            }
        }
        // Case 2: 4-column format: SDL, SchemeYear, SIC_Code, Amount
        else if (parts.Length >= 4)
        {
            if (parts[2].Trim().Length == 5 && parts[2].Trim().All(char.IsDigit))
            {
                extractedSic = parts[2].Trim();
            }
            decimal.TryParse(parts[3], NumberStyles.Any, CultureInfo.InvariantCulture, out totalAmount);
            var split = CalculateStatutorySplit(totalAmount);
            mandatory = split.MandatoryGrantAmount;
            discretionary = split.DiscretionaryGrantAmount;
            admin = split.AdminLevyAmount;
            qcto = split.QctoLevyAmount;
        }
        // Case 3: 3-column format: SDL, SchemeYear, Amount
        else if (parts.Length == 3)
        {
            decimal.TryParse(parts[2], NumberStyles.Any, CultureInfo.InvariantCulture, out totalAmount);
            var split = CalculateStatutorySplit(totalAmount);
            mandatory = split.MandatoryGrantAmount;
            discretionary = split.DiscretionaryGrantAmount;
            admin = split.AdminLevyAmount;
            qcto = split.QctoLevyAmount;
        }
        // Case 4: 2-column format: SDL, Amount
        else if (parts.Length == 2)
        {
            decimal.TryParse(parts[1], NumberStyles.Any, CultureInfo.InvariantCulture, out totalAmount);
            schemeYear = DateTime.UtcNow.Year.ToString();
            var split = CalculateStatutorySplit(totalAmount);
            mandatory = split.MandatoryGrantAmount;
            discretionary = split.DiscretionaryGrantAmount;
            admin = split.AdminLevyAmount;
            qcto = split.QctoLevyAmount;
        }

        return new LevyFileLine
        {
            SdlNumber = rawSdl,
            SchemeYear = schemeYear,
            SicCode = extractedSic,
            MandatoryLevyAmount = mandatory,
            DiscretionaryLevyAmount = discretionary,
            AdminLevyAmount = admin,
            QctoLevyAmount = qcto,
            InterestAmount = interest,
            PenaltyAmount = penalty,
            TotalLevyAmount = totalAmount > 0 ? totalAmount : (mandatory + discretionary + admin + qcto + interest + penalty),
            IsReconciled = false,
            CreatedAt = DateTime.UtcNow
        };
    }

    private LevyFileLine? ParseFixedWidthLine(string line)
    {
        if (line.Length < 14) return null;

        var sdlNumber = line.Substring(0, Math.Min(10, line.Length)).Trim();
        var schemeYear = line.Length >= 14 ? line.Substring(10, 4).Trim() : DateTime.UtcNow.Year.ToString();

        decimal mandatory = 0m, discretionary = 0m, admin = 0m, qcto = 0m, interest = 0m, penalty = 0m, total = 0m;

        if (line.Length >= 24)
            decimal.TryParse(line.Substring(14, Math.Min(10, line.Length - 14)).Trim(), NumberStyles.Any, CultureInfo.InvariantCulture, out mandatory);
        if (line.Length >= 34)
            decimal.TryParse(line.Substring(24, Math.Min(10, line.Length - 24)).Trim(), NumberStyles.Any, CultureInfo.InvariantCulture, out discretionary);
        if (line.Length >= 44)
            decimal.TryParse(line.Substring(34, Math.Min(10, line.Length - 34)).Trim(), NumberStyles.Any, CultureInfo.InvariantCulture, out admin);
        if (line.Length >= 54)
            decimal.TryParse(line.Substring(44, Math.Min(10, line.Length - 44)).Trim(), NumberStyles.Any, CultureInfo.InvariantCulture, out qcto);
        if (line.Length >= 64)
            decimal.TryParse(line.Substring(54, Math.Min(10, line.Length - 54)).Trim(), NumberStyles.Any, CultureInfo.InvariantCulture, out interest);
        if (line.Length >= 74)
            decimal.TryParse(line.Substring(64, Math.Min(10, line.Length - 64)).Trim(), NumberStyles.Any, CultureInfo.InvariantCulture, out penalty);
        if (line.Length >= 84)
            decimal.TryParse(line.Substring(74, Math.Min(10, line.Length - 74)).Trim(), NumberStyles.Any, CultureInfo.InvariantCulture, out total);
        else
            total = mandatory + discretionary + admin + qcto + interest + penalty;

        if (mandatory == 0m && discretionary == 0m && admin == 0m && total > 0)
        {
            var split = CalculateStatutorySplit(total);
            mandatory = split.MandatoryGrantAmount;
            discretionary = split.DiscretionaryGrantAmount;
            admin = split.AdminLevyAmount;
            qcto = split.QctoLevyAmount;
        }

        return new LevyFileLine
        {
            SdlNumber = sdlNumber,
            SchemeYear = schemeYear,
            MandatoryLevyAmount = mandatory,
            DiscretionaryLevyAmount = discretionary,
            AdminLevyAmount = admin,
            QctoLevyAmount = qcto,
            InterestAmount = interest,
            PenaltyAmount = penalty,
            TotalLevyAmount = total,
            IsReconciled = false,
            CreatedAt = DateTime.UtcNow
        };
    }

    public async Task<LevyFile> ParseSarsFileAsync(string fileContent, string fileName, string currentUsername = "SYSTEM")
    {
        return await ImportLevyFileAsync(fileName, fileContent, currentUsername);
    }

    public async Task<LevyFile> ImportLevyFileAsync(string fileName, string fileContent, string currentUsername = "SYSTEM")
    {
        if (string.IsNullOrWhiteSpace(fileName))
        {
            throw new ArgumentException("File name is required.");
        }

        var parsedLines = ParseSarsFileContent(fileContent);
        if (parsedLines.Count == 0)
        {
            throw new ArgumentException("No valid lines found in SARS file content.", nameof(fileContent));
        }

        foreach (var line in parsedLines)
        {
            line.CreatedBy = currentUsername;
        }

        // Compute non-repudiation Digital Security Seal (SHA-256)
        using var sha256 = System.Security.Cryptography.SHA256.Create();
        var hashBytes = sha256.ComputeHash(System.Text.Encoding.UTF8.GetBytes(fileContent));
        var digitalSecuritySeal = BitConverter.ToString(hashBytes).Replace("-", string.Empty).ToLowerInvariant();

        using var db = await _contextFactory.CreateDbContextAsync();

        if (await db.LevyFiles.AnyAsync(f => f.DigitalSecuritySeal == digitalSecuritySeal && f.ImportStatusCode == "Imported"))
        {
            throw new InvalidOperationException($"Duplicate SARS levy file rejected. A batch with Digital Security Seal '{digitalSecuritySeal}' has already been processed.");
        }

        // Automated Boundary & SIC Discrepancy Engine with chunked queries to prevent SQL 2,100 parameter limit
        var allSdlNumbers = parsedLines.Select(l => l.SdlNumber).Distinct().ToList();
        var organisations = new List<Organisation>();
        const int chunkSize = 1500;
        for (int i = 0; i < allSdlNumbers.Count; i += chunkSize)
        {
            var sdlChunk = allSdlNumbers.Skip(i).Take(chunkSize).ToList();
            var matchedOrgs = await db.Organisations.Where(o => sdlChunk.Contains(o.SdlNumber)).ToListAsync();
            organisations.AddRange(matchedOrgs);
        }

        var distinctSicCodes = parsedLines.Where(l => !string.IsNullOrWhiteSpace(l.SicCode)).Select(l => l.SicCode!).Distinct().ToList();
        var sicTypes = await db.SicCodeTypes.Where(s => distinctSicCodes.Contains(s.Code)).ToListAsync();

        foreach (var line in parsedLines)
        {
            var org = organisations.FirstOrDefault(o => o.SdlNumber == line.SdlNumber);
            var matchedSic = !string.IsNullOrWhiteSpace(line.SicCode) ? sicTypes.FirstOrDefault(s => s.Code == line.SicCode) : null;

            if (matchedSic != null)
            {
                line.ChamberCode = matchedSic.ChamberCode;
                line.SetaCode = matchedSic.SetaCode;

                // 1. Inter-SETA Out-of-Scope Boundary Detection
                if (matchedSic.SetaCode != "17")
                {
                    line.IsOutOfScopeSeta = true;
                    db.SarsLevyReconAudits.Add(new SarsLevyReconAudit
                    {
                        FinancialYear = line.SchemeYear.Length >= 4 ? line.SchemeYear[..4] : DateTime.UtcNow.Year.ToString(),
                        SchemeYear = line.SchemeYear,
                        SdlNumber = line.SdlNumber,
                        OrganisationId = org?.Id,
                        TotalSarsLeviesReceived = line.TotalLevyAmount,
                        TotalCalculatedLeviesExpected = 0m,
                        VarianceAmount = line.TotalLevyAmount,
                        DiscrepancyReasonCode = "OutOfScopeSeta",
                        CounterpartSetaCode = matchedSic.SetaCode,
                        ActualSarsSicCode = line.SicCode,
                        ActualSarsChamberCode = matchedSic.ChamberCode,
                        ExpectedSicCode = org?.SicCode,
                        ExpectedChamberCode = org?.ChamberCode,
                        AuditStatusCode = "DiscrepancyFlagged",
                        AuditNotes = $"SARS monthly levy reported non-merSETA SIC Code '{line.SicCode}' belonging to SETA '{matchedSic.SetaCode}'. Out-of-scope Inter-SETA transfer required.",
                        AuditorUserId = currentUsername,
                        ReconciliationDate = DateTime.UtcNow
                    });

                    if (org != null)
                    {
                        var hasActiveTransfer = await db.InterSetaTransfers.AnyAsync(t => t.OrganisationId == org.Id && t.TransferStatusCode == "Initiated");
                        if (!hasActiveTransfer)
                        {
                            db.InterSetaTransfers.Add(new InterSetaTransfer
                            {
                                OrganisationId = org.Id,
                                TransferType = "Outgoing",
                                OtherSetaCode = matchedSic.SetaCode,
                                OtherSetaName = matchedSic.Description ?? $"SETA {matchedSic.SetaCode}",
                                TransferReason = $"Automatic boundary detection: SARS reported non-merSETA SIC Code {line.SicCode} (SETA {matchedSic.SetaCode})",
                                EffectiveDate = DateTime.UtcNow,
                                TransferStatusCode = "Initiated",
                                TransferAmount = line.TotalLevyAmount,
                                CreatedAt = DateTime.UtcNow,
                                CreatedBy = currentUsername
                            });
                        }
                    }
                }
            }
            else if (org != null && !string.IsNullOrWhiteSpace(org.ChamberCode))
            {
                line.ChamberCode = org.ChamberCode;
            }

            // 2. SIC Code Discrepancy & Chamber Misallocation Drift Check
            if (org != null && !string.IsNullOrWhiteSpace(line.SicCode) && !string.IsNullOrWhiteSpace(org.SicCode) && line.SicCode != org.SicCode)
            {
                line.HasSicCodeMismatch = true;
                db.SarsLevyReconAudits.Add(new SarsLevyReconAudit
                {
                    FinancialYear = line.SchemeYear.Length >= 4 ? line.SchemeYear[..4] : DateTime.UtcNow.Year.ToString(),
                    SchemeYear = line.SchemeYear,
                    SdlNumber = line.SdlNumber,
                    OrganisationId = org.Id,
                    TotalSarsLeviesReceived = line.TotalLevyAmount,
                    TotalCalculatedLeviesExpected = line.TotalLevyAmount,
                    VarianceAmount = 0m,
                    DiscrepancyReasonCode = "SicCodeMismatch",
                    ExpectedSicCode = org.SicCode,
                    ActualSarsSicCode = line.SicCode,
                    ExpectedChamberCode = org.ChamberCode,
                    ActualSarsChamberCode = matchedSic?.ChamberCode,
                    AuditStatusCode = "DiscrepancyFlagged",
                    AuditNotes = $"SARS declared SIC Code '{line.SicCode}' (Chamber: {matchedSic?.ChamberCode ?? "Unknown"}) differs from verified master record '{org.SicCode}' (Chamber: {org.ChamberCode}).",
                    AuditorUserId = currentUsername,
                    ReconciliationDate = DateTime.UtcNow
                });
            }
        }

        var levyFile = new LevyFile
        {
            FileName = fileName,
            FileRef = $"SARS-{DateTime.UtcNow:yyyyMMdd}-{Guid.NewGuid().ToString("N")[..6].ToUpper()}",
            ImportDate = DateTime.UtcNow,
            TotalRecords = parsedLines.Count,
            TotalAmount = parsedLines.Sum(l => l.TotalLevyAmount > 0 ? l.TotalLevyAmount : (l.MandatoryLevyAmount + l.DiscretionaryLevyAmount + l.AdminLevyAmount + l.QctoLevyAmount + l.InterestAmount + l.PenaltyAmount)),
            ImportStatusCode = "Imported",
            DigitalSecuritySeal = digitalSecuritySeal,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername,
            LineItems = parsedLines
        };

        db.LevyFiles.Add(levyFile);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "LevyFile", levyFile.Id, "ParseSarsFile", currentUsername, null, new
        {
            levyFile.FileName,
            levyFile.FileRef,
            levyFile.DigitalSecuritySeal,
            levyFile.TotalRecords,
            levyFile.TotalAmount
        });
        await db.SaveChangesAsync();

        return levyFile;
    }

    public async Task<LevyFile?> GetByIdAsync(int id)
    {
        return await GetLevyFileByIdAsync(id);
    }

    public async Task<LevyFile?> GetLevyFileByIdAsync(int id)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.LevyFiles
            .Include(f => f.LineItems)
            .FirstOrDefaultAsync(f => f.Id == id);
    }

    public async Task<List<LevyFile>> GetAllAsync(string? search = null)
    {
        return await GetAllLevyFilesAsync(search);
    }

    public async Task<List<LevyFile>> GetAllLevyFilesAsync(string? search = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.LevyFiles
            .Include(f => f.LineItems)
            .AsQueryable();

        if (!string.IsNullOrWhiteSpace(search))
        {
            var s = search.Trim();
            query = query.Where(f =>
                f.FileName.Contains(s) ||
                f.FileRef.Contains(s) ||
                (f.ImportStatusCode != null && f.ImportStatusCode.Contains(s)));
        }

        return await query
            .OrderByDescending(f => f.ImportDate)
            .ThenByDescending(f => f.Id)
            .ToListAsync();
    }

    public async Task<LevyFile> CreateAsync(LevyFile file, string currentUsername = "SYSTEM")
    {
        if (string.IsNullOrWhiteSpace(file.FileRef))
        {
            file.FileRef = $"SARS-{DateTime.UtcNow:yyyyMMdd}-{Guid.NewGuid().ToString("N")[..6].ToUpper()}";
        }
        if (file.ImportDate == default) file.ImportDate = DateTime.UtcNow;

        using var db = await _contextFactory.CreateDbContextAsync();
        file.CreatedAt = DateTime.UtcNow;
        file.CreatedBy = currentUsername;

        db.LevyFiles.Add(file);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "LevyFile", file.Id, "Create", currentUsername, null, file);
        await db.SaveChangesAsync();
        return file;
    }

    public async Task<LevyFile> UpdateAsync(LevyFile file, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.LevyFiles.FindAsync(file.Id);
        if (existing == null) throw new KeyNotFoundException($"LevyFile with ID {file.Id} not found.");

        var beforeState = new { existing.FileName, existing.ImportStatusCode, existing.TotalAmount, existing.TotalRecords };

        existing.FileName = file.FileName;
        existing.ImportStatusCode = file.ImportStatusCode;
        existing.TotalAmount = file.TotalAmount;
        existing.TotalRecords = file.TotalRecords;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        _audit.LogAction(db, "LevyFile", existing.Id, "Update", currentUsername, beforeState, existing);
        await db.SaveChangesAsync();
        return existing;
    }

    public async Task<bool> DeleteAsync(int id, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var file = await db.LevyFiles.Include(f => f.LineItems).FirstOrDefaultAsync(f => f.Id == id);
        if (file == null) return false;

        var beforeState = new { file.Id, file.FileName, file.FileRef, file.TotalRecords };

        db.LevyFiles.Remove(file);
        _audit.LogAction(db, "LevyFile", id, "Delete", currentUsername, beforeState, null);
        await db.SaveChangesAsync();
        return true;
    }

    public async Task<List<LevyFileLine>> GetLineItemsAsync(int levyFileId, string? sdlSearch = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.LevyFileLines
            .Where(l => l.LevyFileId == levyFileId)
            .AsQueryable();

        if (!string.IsNullOrWhiteSpace(sdlSearch))
        {
            var s = sdlSearch.Trim();
            query = query.Where(l => l.SdlNumber.Contains(s) || l.SchemeYear.Contains(s));
        }

        return await query
            .OrderBy(l => l.SdlNumber)
            .ToListAsync();
    }

    public async Task<LevyFileLine> AddLineItemAsync(LevyFileLine line, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        line.CreatedAt = DateTime.UtcNow;
        line.CreatedBy = currentUsername;
        if (line.TotalLevyAmount <= 0)
        {
            line.TotalLevyAmount = line.MandatoryLevyAmount + line.DiscretionaryLevyAmount + line.AdminLevyAmount + line.QctoLevyAmount + line.InterestAmount + line.PenaltyAmount;
        }

        db.LevyFileLines.Add(line);
        await db.SaveChangesAsync();

        var file = await db.LevyFiles.FindAsync(line.LevyFileId);
        if (file != null)
        {
            var allLines = await db.LevyFileLines.Where(l => l.LevyFileId == file.Id).ToListAsync();
            file.TotalRecords = allLines.Count;
            file.TotalAmount = allLines.Sum(l => l.TotalLevyAmount);
        }

        _audit.LogAction(db, "LevyFileLine", line.Id, "AddLineItem", currentUsername, null, line);
        await db.SaveChangesAsync();
        return line;
    }

    public async Task<bool> RemoveLineItemAsync(long lineId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var line = await db.LevyFileLines.FindAsync(lineId);
        if (line == null) return false;

        var fileId = line.LevyFileId;
        db.LevyFileLines.Remove(line);
        await db.SaveChangesAsync();

        var file = await db.LevyFiles.FindAsync(fileId);
        if (file != null)
        {
            var allLines = await db.LevyFileLines.Where(l => l.LevyFileId == file.Id).ToListAsync();
            file.TotalRecords = allLines.Count;
            file.TotalAmount = allLines.Sum(l => l.TotalLevyAmount);
        }

        _audit.LogAction(db, "LevyFileLine", lineId, "RemoveLineItem", currentUsername, null, null);
        await db.SaveChangesAsync();
        return true;
    }

    public async Task<bool> ReconcileLineItemAsync(long lineId, string currentUsername = "SYSTEM")
    {
        return await ReconcileLevyLineAsync(lineId, currentUsername);
    }

    public async Task<bool> ReconcileLevyLineAsync(long levyFileLineId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var line = await db.LevyFileLines.FindAsync(levyFileLineId);
        if (line == null)
        {
            return false;
        }

        var orgExists = await db.Organisations.AnyAsync(o => o.SdlNumber == line.SdlNumber && o.IsActive);
        if (orgExists)
        {
            line.IsReconciled = true;
            line.ModifiedAt = DateTime.UtcNow;
            line.ModifiedBy = currentUsername;

            _audit.LogAction(db, "LevyFileLine", line.Id, "ReconcileLine", currentUsername, null, line);
            await db.SaveChangesAsync();
            return true;
        }

        return false;
    }

    public async Task<ReconciliationResult> ReconcileAllLinesAsync(int levyFileId, string currentUsername = "SYSTEM")
    {
        return await ReconcileLevyFileAsync(levyFileId, currentUsername);
    }

    public async Task<ReconciliationResult> ReconcileLevyFileAsync(int levyFileId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var file = await db.LevyFiles
            .Include(f => f.LineItems)
            .FirstOrDefaultAsync(f => f.Id == levyFileId);

        if (file == null)
        {
            throw new KeyNotFoundException($"LevyFile with ID {levyFileId} was not found.");
        }

        var activeSdlList = await db.Organisations
            .Where(o => o.IsActive)
            .Select(o => o.SdlNumber)
            .ToListAsync();
        var activeSdlNumbers = new HashSet<string>(activeSdlList.Select(s => s.Trim().ToUpperInvariant()), StringComparer.OrdinalIgnoreCase);

        int reconciledCount = 0;
        decimal reconciledAmount = 0m;

        foreach (var line in file.LineItems)
        {
            if (activeSdlNumbers.Contains(line.SdlNumber.Trim().ToUpperInvariant()))
            {
                line.IsReconciled = true;
                line.ModifiedAt = DateTime.UtcNow;
                line.ModifiedBy = currentUsername;
                reconciledCount++;
                reconciledAmount += line.TotalLevyAmount > 0 ? line.TotalLevyAmount : (line.MandatoryLevyAmount + line.DiscretionaryLevyAmount + line.AdminLevyAmount + line.QctoLevyAmount + line.InterestAmount + line.PenaltyAmount);
            }
        }

        var unreconciledCount = file.LineItems.Count - reconciledCount;
        var status = (reconciledCount == file.LineItems.Count && file.LineItems.Count > 0) ? "FullyReconciled" :
                     (reconciledCount > 0) ? "PartiallyReconciled" : "Unreconciled";

        var beforeState = new { file.ImportStatusCode };
        file.ImportStatusCode = status;
        file.ModifiedAt = DateTime.UtcNow;
        file.ModifiedBy = currentUsername;

        var result = new ReconciliationResult(
            TotalRecords: file.LineItems.Count,
            ReconciledCount: reconciledCount,
            UnreconciledCount: unreconciledCount,
            TotalAmount: file.TotalAmount,
            ReconciledAmount: reconciledAmount,
            Status: status
        );

        _audit.LogAction(db, "LevyFile", file.Id, "ReconcileFile", currentUsername, beforeState, result);
        await db.SaveChangesAsync();

        return result;
    }

    public async Task<int> ReconcileEmployerLeviesAsync(string sdlNumber, string schemeYear, string currentUsername = "SYSTEM")
    {
        if (string.IsNullOrWhiteSpace(sdlNumber))
        {
            throw new ArgumentException("SDL number is required for reconciliation.", nameof(sdlNumber));
        }

        var cleanSdl = sdlNumber.Trim().ToUpperInvariant();
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.LevyFileLines.Where(l => l.SdlNumber == cleanSdl && !l.IsReconciled);

        if (!string.IsNullOrWhiteSpace(schemeYear))
        {
            var sy = schemeYear.Trim();
            query = query.Where(l => l.SchemeYear == sy);
        }

        var linesToReconcile = await query.ToListAsync();
        if (linesToReconcile.Count == 0)
        {
            return 0;
        }

        foreach (var line in linesToReconcile)
        {
            line.IsReconciled = true;
            line.ModifiedAt = DateTime.UtcNow;
            line.ModifiedBy = currentUsername;
        }

        _audit.LogAction(db, "LevyFileLine", 0, "ReconcileEmployerLevies", currentUsername, null, new
        {
            SdlNumber = cleanSdl,
            SchemeYear = schemeYear,
            ReconciledCount = linesToReconcile.Count,
            TotalReconciledAmount = linesToReconcile.Sum(l => l.TotalLevyAmount)
        });
        await db.SaveChangesAsync();

        return linesToReconcile.Count;
    }

    public async Task<List<SarsLevyDeviationDto>> GetLevyDeviationReportAsync(string? schemeYear = null, string? chamber = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var query = db.LevyFileLines.AsQueryable();
        if (!string.IsNullOrWhiteSpace(schemeYear))
        {
            query = query.Where(l => l.SchemeYear == schemeYear);
        }

        var lines = await query.ToListAsync();
        var orgs = await db.Organisations.ToListAsync();
        var orgDict = orgs.Where(o => !string.IsNullOrWhiteSpace(o.SdlNumber))
                          .ToDictionary(o => o.SdlNumber.Trim().ToUpperInvariant(), o => o, StringComparer.OrdinalIgnoreCase);

        var grouped = lines.GroupBy(l => l.SdlNumber.Trim().ToUpperInvariant()).ToList();
        var report = new List<SarsLevyDeviationDto>();

        foreach (var grp in grouped)
        {
            var sdl = grp.Key;
            orgDict.TryGetValue(sdl, out var org);

            var orgName = org?.CompanyName ?? "Unregistered Contributor";
            var chamberCode = org?.ChamberCode ?? "OTHER";

            if (!string.IsNullOrWhiteSpace(chamber) && !chamberCode.Equals(chamber, StringComparison.OrdinalIgnoreCase))
            {
                continue;
            }

            var lineList = grp.OrderBy(l => l.CreatedAt).ToList();
            var amounts = lineList.Select(l => l.TotalLevyAmount > 0 ? l.TotalLevyAmount : (l.MandatoryLevyAmount + l.DiscretionaryLevyAmount + l.AdminLevyAmount + l.QctoLevyAmount + l.InterestAmount + l.PenaltyAmount)).ToList();

            if (amounts.Count == 0) continue;

            decimal total = amounts.Sum();
            decimal latest = amounts.Last();
            decimal avg = total / Math.Max(1, amounts.Count);

            // Sample standard deviation
            decimal variance = 0m;
            if (amounts.Count > 1)
            {
                decimal sumSquares = amounts.Sum(a => (a - avg) * (a - avg));
                variance = sumSquares / (amounts.Count - 1);
            }
            decimal stdDev = (decimal)Math.Sqrt((double)variance);
            decimal devPct = avg > 0 ? (stdDev / avg) * 100.0m : 0.0m;

            var dto = new SarsLevyDeviationDto
            {
                SdlNumber = sdl,
                OrganisationName = orgName,
                ChamberCode = chamberCode,
                SchemeYear = schemeYear ?? (lineList.FirstOrDefault()?.SchemeYear ?? DateTime.UtcNow.Year.ToString()),
                Month1 = amounts.ElementAtOrDefault(0),
                Month2 = amounts.ElementAtOrDefault(1),
                Month3 = amounts.ElementAtOrDefault(2),
                Month4 = amounts.ElementAtOrDefault(3),
                Month5 = amounts.ElementAtOrDefault(4),
                Month6 = amounts.ElementAtOrDefault(5),
                Month7 = amounts.ElementAtOrDefault(6),
                Month8 = amounts.ElementAtOrDefault(7),
                Month9 = amounts.ElementAtOrDefault(8),
                Month10 = amounts.ElementAtOrDefault(9),
                Month11 = amounts.ElementAtOrDefault(10),
                Month12 = amounts.ElementAtOrDefault(11),
                TotalLevy = total,
                LatestLevy = latest,
                AverageMonthlyLevy = Math.Round(avg, 2),
                StandardDeviation = Math.Round(stdDev, 2),
                DeviationPercentage = Math.Round(devPct, 2),
                LevyStatus = (avg >= 40000m && devPct >= 20.0m) ? "Inconsistent" : "Consistent"
            };

            report.Add(dto);
        }

        return report.OrderByDescending(r => r.DeviationPercentage).ToList();
    }

    public async Task<List<ChamberLevyBreakdownDto>> GetChamberLevyBreakdownAsync(string? schemeYear = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var linesQuery = db.LevyFileLines.AsQueryable();
        if (!string.IsNullOrWhiteSpace(schemeYear))
        {
            linesQuery = linesQuery.Where(l => l.SchemeYear == schemeYear);
        }

        var lines = await linesQuery.ToListAsync();
        var orgs = await db.Organisations.ToListAsync();
        var orgDict = orgs.Where(o => !string.IsNullOrWhiteSpace(o.SdlNumber))
                          .ToDictionary(o => o.SdlNumber.Trim().ToUpperInvariant(), o => o, StringComparer.OrdinalIgnoreCase);

        // Load active Chambers dynamically from the database lookup table (zero hardcoding)
        var dbChambers = await db.ChamberTypes.Where(c => c.Active).OrderBy(c => c.Name).ToListAsync();
        
        var breakdownMap = new Dictionary<string, ChamberLevyBreakdownDto>(StringComparer.OrdinalIgnoreCase);
        var seenEmployersPerChamber = new Dictionary<string, HashSet<string>>(StringComparer.OrdinalIgnoreCase);

        foreach (var ch in dbChambers)
        {
            var code = ch.Code.Trim().ToUpperInvariant();
            breakdownMap[code] = new ChamberLevyBreakdownDto
            {
                ChamberCode = code,
                ChamberName = ch.Name
            };
            seenEmployersPerChamber[code] = new HashSet<string>(StringComparer.OrdinalIgnoreCase);
        }

        // Always ensure a fallback "OTHER" chamber exists for unallocated or unclassified employers
        if (!breakdownMap.ContainsKey("OTHER"))
        {
            breakdownMap["OTHER"] = new ChamberLevyBreakdownDto
            {
                ChamberCode = "OTHER",
                ChamberName = "Other / Unassigned Sector"
            };
            seenEmployersPerChamber["OTHER"] = new HashSet<string>(StringComparer.OrdinalIgnoreCase);
        }

        foreach (var line in lines)
        {
            var sdl = line.SdlNumber.Trim().ToUpperInvariant();
            string chamber = "OTHER";
            if (orgDict.TryGetValue(sdl, out var org) && !string.IsNullOrWhiteSpace(org.ChamberCode))
            {
                var orgChamber = org.ChamberCode.Trim().ToUpperInvariant();
                if (breakdownMap.ContainsKey(orgChamber))
                {
                    chamber = orgChamber;
                }
                else
                {
                    // Match by partial prefix or canonical name if code was saved with suffix (e.g. AUTO_CHAM vs AUTO)
                    var matched = breakdownMap.Keys.FirstOrDefault(k => orgChamber.StartsWith(k) || k.StartsWith(orgChamber));
                    chamber = matched ?? "OTHER";
                }
            }

            var dto = breakdownMap[chamber];
            dto.TotalGrossLevy += line.TotalLevyAmount > 0 ? line.TotalLevyAmount : (line.MandatoryLevyAmount + line.DiscretionaryLevyAmount + line.AdminLevyAmount + line.QctoLevyAmount + line.InterestAmount + line.PenaltyAmount);
            dto.MandatoryGrantPortion += line.MandatoryLevyAmount;
            dto.DiscretionaryGrantPortion += line.DiscretionaryLevyAmount;
            dto.AdminPortion += line.AdminLevyAmount;
            dto.QctoPortion += line.QctoLevyAmount;
            dto.InterestAndPenalties += (line.InterestAmount + line.PenaltyAmount);

            seenEmployersPerChamber[chamber].Add(sdl);
        }

        foreach (var kvp in breakdownMap)
        {
            kvp.Value.EmployerCount = seenEmployersPerChamber[kvp.Key].Count;
        }

        return breakdownMap.Values.OrderByDescending(b => b.TotalGrossLevy).ToList();
    }

    public async Task<List<SarsSchemeYearCalculation>> GetSchemeYearCalculationsAsync()
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var list = await db.SarsSchemeYearCalculations.OrderByDescending(s => s.SchemeYear).ToListAsync();
        if (list.Count == 0)
        {
            // Seed current defaults
            var defaultCurr = new SarsSchemeYearCalculation
            {
                SchemeYear = DateTime.UtcNow.Year.ToString(),
                MandatoryPercentage = 20.0m,
                DiscretionaryPercentage = 49.5m,
                AdminPercentage = 10.5m,
                QctoPercentage = 0.5m,
                TotalPercentage = 80.5m,
                AllowReturnsMandatory = true,
                AllowInvoicesMandatory = true,
                AllowReturnsDiscretionary = true,
                AllowInvoicesDiscretionary = true,
                StatusCode = "Active",
                Notes = "Default Statutory Split for Skills Development Levy Scheme Year."
            };
            db.SarsSchemeYearCalculations.Add(defaultCurr);
            await db.SaveChangesAsync();
            list.Add(defaultCurr);
        }
        return list;
    }

    public async Task<SarsSchemeYearCalculation> SaveSchemeYearCalculationAsync(SarsSchemeYearCalculation config, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        if (config.Id == 0)
        {
            config.CreatedAt = DateTime.UtcNow;
            config.CreatedBy = currentUsername;
            db.SarsSchemeYearCalculations.Add(config);
            await db.SaveChangesAsync();
            _audit.LogAction(db, "SarsSchemeYearCalculation", config.Id, "Create", currentUsername, null, config);
            await db.SaveChangesAsync();
            return config;
        }
        else
        {
            var existing = await db.SarsSchemeYearCalculations.FindAsync(config.Id);
            if (existing == null) throw new KeyNotFoundException($"SarsSchemeYearCalculation ID {config.Id} not found.");

            var before = new
            {
                existing.SchemeYear,
                existing.MandatoryPercentage,
                existing.DiscretionaryPercentage,
                existing.AdminPercentage,
                existing.QctoPercentage,
                existing.StatusCode
            };

            existing.SchemeYear = config.SchemeYear;
            existing.MandatoryPercentage = config.MandatoryPercentage;
            existing.DiscretionaryPercentage = config.DiscretionaryPercentage;
            existing.AdminPercentage = config.AdminPercentage;
            existing.QctoPercentage = config.QctoPercentage;
            existing.TotalPercentage = config.MandatoryPercentage + config.DiscretionaryPercentage + config.AdminPercentage + config.QctoPercentage;
            existing.AllowReturnsMandatory = config.AllowReturnsMandatory;
            existing.AllowInvoicesMandatory = config.AllowInvoicesMandatory;
            existing.AllowReturnsDiscretionary = config.AllowReturnsDiscretionary;
            existing.AllowInvoicesDiscretionary = config.AllowInvoicesDiscretionary;
            existing.StatusCode = config.StatusCode;
            existing.Notes = config.Notes;
            existing.ModifiedAt = DateTime.UtcNow;
            existing.ModifiedBy = currentUsername;

            _audit.LogAction(db, "SarsSchemeYearCalculation", existing.Id, "Update", currentUsername, before, existing);
            await db.SaveChangesAsync();
            return existing;
        }
    }
}
