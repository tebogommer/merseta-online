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
    Task<bool> RemoveLineItemAsync(int lineId, string currentUsername = "SYSTEM");

    Task<bool> ReconcileLevyLineAsync(int levyFileLineId, string currentUsername = "SYSTEM");
    Task<bool> ReconcileLineItemAsync(int lineId, string currentUsername = "SYSTEM");
    Task<ReconciliationResult> ReconcileLevyFileAsync(int levyFileId, string currentUsername = "SYSTEM");
    Task<ReconciliationResult> ReconcileAllLinesAsync(int levyFileId, string currentUsername = "SYSTEM");
    Task<int> ReconcileEmployerLeviesAsync(string sdlNumber, string schemeYear, string currentUsername = "SYSTEM");
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
                    parts[0].Equals("REF_NO", StringComparison.OrdinalIgnoreCase))
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
        }
        else if (parts.Length >= 3)
        {
            if (decimal.TryParse(parts[2], NumberStyles.Any, CultureInfo.InvariantCulture, out totalAmount))
            {
                var split = CalculateStatutorySplit(totalAmount);
                mandatory = split.MandatoryGrantAmount;
                discretionary = split.DiscretionaryGrantAmount;
                admin = split.AdminLevyAmount;
                qcto = split.QctoLevyAmount;
            }
        }
        else if (parts.Length == 2)
        {
            if (decimal.TryParse(parts[1], NumberStyles.Any, CultureInfo.InvariantCulture, out totalAmount))
            {
                schemeYear = DateTime.UtcNow.Year.ToString();
                var split = CalculateStatutorySplit(totalAmount);
                mandatory = split.MandatoryGrantAmount;
                discretionary = split.DiscretionaryGrantAmount;
                admin = split.AdminLevyAmount;
                qcto = split.QctoLevyAmount;
            }
        }

        return new LevyFileLine
        {
            SdlNumber = rawSdl,
            SchemeYear = schemeYear,
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

        var levyFile = new LevyFile
        {
            FileName = fileName,
            FileRef = $"SARS-{DateTime.UtcNow:yyyyMMdd}-{Guid.NewGuid().ToString("N")[..6].ToUpper()}",
            ImportDate = DateTime.UtcNow,
            TotalRecords = parsedLines.Count,
            TotalAmount = parsedLines.Sum(l => l.TotalLevyAmount > 0 ? l.TotalLevyAmount : (l.MandatoryLevyAmount + l.DiscretionaryLevyAmount + l.AdminLevyAmount + l.QctoLevyAmount + l.InterestAmount + l.PenaltyAmount)),
            ImportStatusCode = "Imported",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername,
            LineItems = parsedLines
        };

        using var db = await _contextFactory.CreateDbContextAsync();
        db.LevyFiles.Add(levyFile);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "LevyFile", levyFile.Id, "ParseSarsFile", currentUsername, null, new
        {
            levyFile.FileName,
            levyFile.FileRef,
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

    public async Task<bool> RemoveLineItemAsync(int lineId, string currentUsername = "SYSTEM")
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

    public async Task<bool> ReconcileLineItemAsync(int lineId, string currentUsername = "SYSTEM")
    {
        return await ReconcileLevyLineAsync(lineId, currentUsername);
    }

    public async Task<bool> ReconcileLevyLineAsync(int levyFileLineId, string currentUsername = "SYSTEM")
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
}
