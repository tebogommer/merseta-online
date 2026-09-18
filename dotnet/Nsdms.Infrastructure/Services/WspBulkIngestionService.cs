using System.Globalization;
using System.Security.Cryptography;
using System.Text;
using ClosedXML.Excel;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;

namespace Nsdms.Infrastructure.Services;

/// <summary>
/// Production implementation of the Universal Bulk Ingestion Engine for WSP / ATR submissions (Option C: Modern Hybrid).
/// Provides high-speed parsing of .xlsx and .csv, set-based pre-flight validation, inline error editing,
/// and delta correction workbook generation using ClosedXML.
/// </summary>
public class WspBulkIngestionService : IWspBulkIngestionService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;
    private readonly ILogger<WspBulkIngestionService> _logger;

    public WspBulkIngestionService(
        INsdmsDbContextFactory contextFactory,
        IAuditService audit,
        ILogger<WspBulkIngestionService> logger)
    {
        _contextFactory = contextFactory;
        _audit = audit;
        _logger = logger;
    }

    public async Task<WspBulkImportBatch> StageAndValidateBatchAsync(
        int wspSubmissionId,
        string fileName,
        Stream fileStream,
        string currentUsername,
        bool allowPartial = false)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var wsp = await db.WspSubmissions.FindAsync(wspSubmissionId);
        if (wsp == null) throw new KeyNotFoundException($"WspSubmission with ID {wspSubmissionId} not found.");

        // Copy stream into MemoryStream for rewind and hash calculation
        using var ms = new MemoryStream();
        await fileStream.CopyToAsync(ms);
        ms.Position = 0;

        var hashBytes = SHA256.HashData(ms.ToArray());
        var sha256 = Convert.ToHexString(hashBytes).ToLowerInvariant();
        ms.Position = 0;

        var (encoding, delimiter, isExcel) = await FileFormatSniffer.SniffFilePropertiesAsync(ms, fileName);
        ms.Position = 0;

        var batchRef = $"ING-{wsp.FinYear}-{DateTime.UtcNow.Ticks % 1000000:D6}";
        var batch = new WspBulkImportBatch
        {
            BatchGuid = Guid.NewGuid(),
            BatchReference = batchRef,
            WspSubmissionId = wspSubmissionId,
            OrganisationId = wsp.OrganisationId,
            SchemeYear = wsp.FinYear,
            ReportType = "WSP",
            OriginalFileName = fileName,
            FileSizeBytes = ms.Length,
            ContentHashSha256 = sha256,
            BatchStatus = "Staged",
            AllowPartialCommit = allowPartial,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        db.WspBulkImportBatches.Add(batch);
        await db.SaveChangesAsync();

        // Parse rows
        var stagedRows = new List<WspBulkImportStaging>();
        if (isExcel)
        {
            stagedRows = ParseExcelStream(ms, batch.Id, currentUsername);
        }
        else
        {
            stagedRows = ParseCsvStream(ms, encoding, delimiter, batch.Id, currentUsername);
        }

        if (stagedRows.Count == 0)
        {
            throw new InvalidOperationException("The uploaded spreadsheet contains no data rows.");
        }

        // Add staged rows in chunks
        const int chunkSize = 1000;
        for (int i = 0; i < stagedRows.Count; i += chunkSize)
        {
            var chunk = stagedRows.Skip(i).Take(chunkSize);
            db.WspBulkImportStagings.AddRange(chunk);
            await db.SaveChangesAsync();
        }

        batch.TotalRowCount = stagedRows.Count;

        // Execute Set-Based Validation
        await ValidateBatchInternalAsync(db, batch);

        _audit.LogAction(db, "WspBulkImportBatch", batch.Id, "StageAndValidate", currentUsername, null, batch);
        await db.SaveChangesAsync();

        return batch;
    }

    private List<WspBulkImportStaging> ParseExcelStream(Stream stream, int batchId, string currentUsername)
    {
        var rows = new List<WspBulkImportStaging>();
        using var workbook = new XLWorkbook(stream);
        var worksheet = workbook.Worksheets.FirstOrDefault(ws => !ws.Name.StartsWith("_")) ?? workbook.Worksheets.First();

        var firstRow = worksheet.FirstRowUsed();
        if (firstRow == null) return rows;

        // Map header column names to indexes
        var headerMap = new Dictionary<string, int>(StringComparer.OrdinalIgnoreCase);
        foreach (var cell in firstRow.CellsUsed())
        {
            var clean = cell.GetString().Trim().ToLowerInvariant().Replace(" ", "").Replace("_", "").Replace("-", "");
            headerMap[clean] = cell.Address.ColumnNumber;
        }

        var dataRows = worksheet.RowsUsed().Skip(1);
        int rowIndex = 1;
        foreach (var row in dataRows)
        {
            string GetVal(string[] aliases)
            {
                foreach (var a in aliases)
                {
                    var clean = a.ToLowerInvariant().Replace(" ", "").Replace("_", "").Replace("-", "");
                    if (headerMap.TryGetValue(clean, out var colIdx))
                    {
                        var cell = row.Cell(colIdx);
                        if (cell != null && !cell.IsEmpty()) return cell.GetString().Trim();
                    }
                }
                return string.Empty;
            }

            var ofo = GetVal(new[] { "OfoCode", "OFO", "OccupationalCode", "Ofo_Code" });
            var idNum = GetVal(new[] { "IdNumber", "NationalId", "RSAId", "IdentityNumber", "ID" });
            var qual = GetVal(new[] { "SaqaQualificationId", "QualificationCode", "SAQAId", "QualID" });
            var prog = GetVal(new[] { "ProgrammeTypeCode", "InterventionType", "LearningProgramme", "ProgrammeType" });
            var costStr = GetVal(new[] { "EstimatedCost", "Cost", "Budget", "Spend" });
            var countStr = GetVal(new[] { "BeneficiaryCount", "Learners", "Headcount", "Count" });

            if (string.IsNullOrWhiteSpace(ofo) && string.IsNullOrWhiteSpace(idNum) && string.IsNullOrWhiteSpace(qual) && string.IsNullOrWhiteSpace(costStr))
            {
                continue; // Skip blank row
            }

            rows.Add(new WspBulkImportStaging
            {
                BatchId = batchId,
                RowIndex = rowIndex++,
                RawOfoCode = string.IsNullOrWhiteSpace(ofo) ? null : ofo,
                RawIdNumber = string.IsNullOrWhiteSpace(idNum) ? null : idNum,
                RawIdType = "RSA_ID",
                RawQualificationCode = string.IsNullOrWhiteSpace(qual) ? null : qual,
                RawInterventionTypeCode = string.IsNullOrWhiteSpace(prog) ? "SkillsProgramme" : prog,
                RawEstimatedCost = string.IsNullOrWhiteSpace(costStr) ? "0" : costStr,
                RawBeneficiaryCount = string.IsNullOrWhiteSpace(countStr) ? "1" : countStr,
                IsValid = true,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = currentUsername
            });
        }

        return rows;
    }

    private List<WspBulkImportStaging> ParseCsvStream(Stream stream, Encoding encoding, char delimiter, int batchId, string currentUsername)
    {
        var rows = new List<WspBulkImportStaging>();
        using var reader = new StreamReader(stream, encoding, leaveOpen: true);

        string? headerLine = reader.ReadLine();
        if (headerLine == null) return rows;

        var headers = ParseDelimitedLine(headerLine, delimiter);
        var headerMap = new Dictionary<string, int>(StringComparer.OrdinalIgnoreCase);
        for (int i = 0; i < headers.Length; i++)
        {
            var clean = headers[i].Trim().ToLowerInvariant().Replace(" ", "").Replace("_", "").Replace("-", "");
            headerMap[clean] = i;
        }

        string? line;
        int rowIndex = 1;
        while ((line = reader.ReadLine()) != null)
        {
            if (string.IsNullOrWhiteSpace(line)) continue;
            var cols = ParseDelimitedLine(line, delimiter);

            string GetCol(string[] aliases)
            {
                foreach (var a in aliases)
                {
                    var clean = a.ToLowerInvariant().Replace(" ", "").Replace("_", "").Replace("-", "");
                    if (headerMap.TryGetValue(clean, out var idx) && idx < cols.Length)
                    {
                        return cols[idx].Trim();
                    }
                }
                return string.Empty;
            }

            var ofo = GetCol(new[] { "OfoCode", "OFO", "OccupationalCode", "Ofo_Code" });
            var idNum = GetCol(new[] { "IdNumber", "NationalId", "RSAId", "IdentityNumber", "ID" });
            var qual = GetCol(new[] { "SaqaQualificationId", "QualificationCode", "SAQAId", "QualID" });
            var prog = GetCol(new[] { "ProgrammeTypeCode", "InterventionType", "LearningProgramme", "ProgrammeType" });
            var costStr = GetCol(new[] { "EstimatedCost", "Cost", "Budget", "Spend" });
            var countStr = GetCol(new[] { "BeneficiaryCount", "Learners", "Headcount", "Count" });

            if (string.IsNullOrWhiteSpace(ofo) && string.IsNullOrWhiteSpace(idNum) && string.IsNullOrWhiteSpace(qual) && string.IsNullOrWhiteSpace(costStr))
            {
                continue;
            }

            rows.Add(new WspBulkImportStaging
            {
                BatchId = batchId,
                RowIndex = rowIndex++,
                RawOfoCode = string.IsNullOrWhiteSpace(ofo) ? null : ofo,
                RawIdNumber = string.IsNullOrWhiteSpace(idNum) ? null : idNum,
                RawIdType = "RSA_ID",
                RawQualificationCode = string.IsNullOrWhiteSpace(qual) ? null : qual,
                RawInterventionTypeCode = string.IsNullOrWhiteSpace(prog) ? "SkillsProgramme" : prog,
                RawEstimatedCost = string.IsNullOrWhiteSpace(costStr) ? "0" : costStr,
                RawBeneficiaryCount = string.IsNullOrWhiteSpace(countStr) ? "1" : countStr,
                IsValid = true,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = currentUsername
            });
        }

        return rows;
    }

    private static string[] ParseDelimitedLine(string line, char delimiter)
    {
        var tokens = new List<string>();
        var sb = new StringBuilder();
        bool inQuotes = false;

        for (int i = 0; i < line.Length; i++)
        {
            char c = line[i];
            if (c == '"')
            {
                inQuotes = !inQuotes;
            }
            else if (c == delimiter && !inQuotes)
            {
                tokens.Add(sb.ToString());
                sb.Clear();
            }
            else
            {
                sb.Append(c);
            }
        }
        tokens.Add(sb.ToString());
        return tokens.ToArray();
    }

    private async Task ValidateBatchInternalAsync(INsdmsDbContext db, WspBulkImportBatch batch)
    {
        var rows = await db.WspBulkImportStagings
            .Where(r => r.BatchId == batch.Id)
            .ToListAsync();

        // Load active OFO codes into in-memory hashset for fast check
        var validOfoCodes = await db.OfoCodeTypes
            .Where(o => o.Active)
            .Select(o => o.Code)
            .ToHashSetAsync(StringComparer.OrdinalIgnoreCase);

        int validCount = 0;
        int errorCount = 0;
        decimal totalCost = 0m;
        int totalBeneficiaries = 0;

        foreach (var r in rows)
        {
            var errors = new List<string>();

            // Parse cost
            var cleanCost = (r.RawEstimatedCost ?? "0").Replace(" ", "").Replace("R", "").Replace(",", ".");
            if (decimal.TryParse(cleanCost, NumberStyles.Any, CultureInfo.InvariantCulture, out var parsedCost) && parsedCost >= 0)
            {
                r.ParsedEstimatedCost = parsedCost;
                totalCost += parsedCost;
            }
            else
            {
                errors.Add("Estimated cost must be a non-negative number.");
            }

            // Parse count
            if (int.TryParse(r.RawBeneficiaryCount ?? "1", out var parsedCount) && parsedCount > 0)
            {
                r.ParsedBeneficiaryCount = parsedCount;
                totalBeneficiaries += parsedCount;
            }
            else
            {
                r.ParsedBeneficiaryCount = 1;
                totalBeneficiaries += 1;
            }

            // Validate OFO code
            if (string.IsNullOrWhiteSpace(r.RawOfoCode))
            {
                errors.Add("OFO code is mandatory.");
            }
            else if (validOfoCodes.Count > 0 && !validOfoCodes.Contains(r.RawOfoCode.Trim()))
            {
                errors.Add($"OFO Code [{r.RawOfoCode}] does not exist or is inactive in the taxonomy.");
            }

            // Validate RSA ID if provided
            if (!string.IsNullOrWhiteSpace(r.RawIdNumber))
            {
                var cleanId = r.RawIdNumber.Trim();
                if (cleanId.Length != 13 || !cleanId.All(char.IsDigit))
                {
                    errors.Add($"RSA ID [{cleanId}] must be exactly 13 numeric digits.");
                }
            }

            if (errors.Count > 0)
            {
                r.IsValid = false;
                r.ValidationErrorCode = "VALIDATION_FAILED";
                r.ValidationErrorDetails = string.Join(" | ", errors);
                errorCount++;
            }
            else
            {
                r.IsValid = true;
                r.ValidationErrorCode = null;
                r.ValidationErrorDetails = null;
                validCount++;
            }
        }

        batch.ValidRowCount = validCount;
        batch.ErrorRowCount = errorCount;
        batch.TotalEstimatedCostRollup = totalCost;
        batch.TotalBeneficiariesRollup = totalBeneficiaries;
        batch.BatchStatus = errorCount == 0 ? "Validated" : "ValidationFailed";
    }

    public async Task<WspBulkImportBatch> RevalidateBatchAsync(int batchId, string currentUsername)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var batch = await db.WspBulkImportBatches.FindAsync(batchId);
        if (batch == null) throw new KeyNotFoundException($"Batch {batchId} not found.");

        await ValidateBatchInternalAsync(db, batch);
        batch.ModifiedAt = DateTime.UtcNow;
        batch.ModifiedBy = currentUsername;

        _audit.LogAction(db, "WspBulkImportBatch", batch.Id, "Revalidate", currentUsername, null, batch);
        await db.SaveChangesAsync();
        return batch;
    }

    public async Task<WspBulkImportBatch> CommitBatchAsync(int batchId, string currentUsername, bool discardExceptions = false)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var batch = await db.WspBulkImportBatches
            .Include(b => b.StagedRows)
            .FirstOrDefaultAsync(b => b.Id == batchId);

        if (batch == null) throw new KeyNotFoundException($"Batch {batchId} not found.");

        if (batch.ErrorRowCount > 0 && !discardExceptions && !batch.AllowPartialCommit)
        {
            throw new InvalidOperationException($"Cannot commit batch with {batch.ErrorRowCount} unresolved exceptions without explicit discard confirmation.");
        }

        var validRows = batch.StagedRows.Where(r => r.IsValid && !r.IsCommitted).ToList();
        int committedCount = 0;

        foreach (var r in validRows)
        {
            var plan = new WspTrainingPlan
            {
                WspSubmissionId = batch.WspSubmissionId,
                ProgrammeTypeCode = r.RawInterventionTypeCode ?? "SkillsProgramme",
                NqfLevel = r.ResolvedNqfLevel ?? 4,
                BeneficiaryCount = r.ParsedBeneficiaryCount ?? 1,
                EstimatedCost = r.ParsedEstimatedCost ?? 0m,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = currentUsername
            };
            db.WspTrainingPlans.Add(plan);
            r.IsCommitted = true;
            committedCount++;
        }

        if (discardExceptions && batch.ErrorRowCount > 0)
        {
            var errorRows = batch.StagedRows.Where(r => !r.IsValid).ToList();
            db.WspBulkImportStagings.RemoveRange(errorRows);
            batch.ErrorRowCount = 0;
        }

        batch.CommittedRowCount += committedCount;
        batch.BatchStatus = batch.ErrorRowCount == 0 ? "Committed" : "PartiallyCommitted";
        batch.ModifiedAt = DateTime.UtcNow;
        batch.ModifiedBy = currentUsername;

        _audit.LogAction(db, "WspBulkImportBatch", batch.Id, "CommitBatch", currentUsername, null, new { CommittedCount = committedCount, DiscardedExceptions = discardExceptions });
        await db.SaveChangesAsync();

        return batch;
    }

    public async Task<byte[]> GenerateDeltaCorrectionExcelAsync(int batchId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var failedRows = await db.WspBulkImportStagings
            .Where(r => r.BatchId == batchId && !r.IsValid)
            .OrderBy(r => r.RowIndex)
            .ToListAsync();

        using var wb = new XLWorkbook();
        var ws = wb.Worksheets.Add("Exceptions To Fix");

        var headers = new[] { "Line #", "Diagnostic Errors", "OFO Code", "RSA ID Number", "Programme Type", "Qualification Code", "Estimated Cost", "Beneficiaries" };
        for (int i = 0; i < headers.Length; i++)
        {
            var cell = ws.Cell(1, i + 1);
            cell.Value = headers[i];
            cell.Style.Font.Bold = true;
            cell.Style.Fill.BackgroundColor = XLColor.FromArgb(0x1E, 0x3A, 0x5F); // merSETA Navy
            cell.Style.Font.FontColor = XLColor.White;
        }

        int rIdx = 2;
        foreach (var row in failedRows)
        {
            ws.Cell(rIdx, 1).Value = row.RowIndex;
            var errCell = ws.Cell(rIdx, 2);
            errCell.Value = row.ValidationErrorDetails;
            errCell.Style.Font.FontColor = XLColor.Crimson;

            var ofoCell = ws.Cell(rIdx, 3);
            ofoCell.Value = row.RawOfoCode;
            if (row.ValidationErrorDetails?.Contains("OFO") == true)
            {
                ofoCell.Style.Fill.BackgroundColor = XLColor.FromArgb(0xFF, 0xEB, 0xEE);
                ofoCell.CreateComment().AddText("Invalid OFO Code according to taxonomy.");
            }

            var idCell = ws.Cell(rIdx, 4);
            idCell.Value = row.RawIdNumber;
            if (row.ValidationErrorDetails?.Contains("RSA ID") == true)
            {
                idCell.Style.Fill.BackgroundColor = XLColor.FromArgb(0xFF, 0xEB, 0xEE);
            }

            ws.Cell(rIdx, 5).Value = row.RawInterventionTypeCode;
            ws.Cell(rIdx, 6).Value = row.RawQualificationCode;
            ws.Cell(rIdx, 7).Value = row.RawEstimatedCost;
            ws.Cell(rIdx, 8).Value = row.RawBeneficiaryCount;
            rIdx++;
        }

        ws.Columns().AdjustToContents();

        // Sheet 2: Reference Taxonomy
        var refSheet = wb.Worksheets.Add("OFO Reference Catalog");
        refSheet.Cell(1, 1).Value = "OFO Code";
        refSheet.Cell(1, 2).Value = "Occupational Title";
        refSheet.Row(1).Style.Font.Bold = true;

        var sampleOfos = await db.OfoCodeTypes.Where(o => o.Active).Take(200).ToListAsync();
        int refIdx = 2;
        foreach (var ofo in sampleOfos)
        {
            refSheet.Cell(refIdx, 1).Value = ofo.Code;
            refSheet.Cell(refIdx, 2).Value = ofo.Name;
            refIdx++;
        }
        refSheet.Columns().AdjustToContents();

        using var ms = new MemoryStream();
        wb.SaveAs(ms);
        return ms.ToArray();
    }

    public async Task<byte[]> GenerateOfficialTemplateExcelAsync(int schemeYear = 2026)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        using var wb = new XLWorkbook();
        var ws = wb.Worksheets.Add("WSP_ATR_Intake");

        var headers = new[] { "OFO Code", "RSA ID Number", "First Name", "Last Name", "Gender Code", "Equity Code", "Programme Type", "Qualification Code", "Estimated Cost (ZAR)", "Beneficiaries" };
        for (int i = 0; i < headers.Length; i++)
        {
            var cell = ws.Cell(1, i + 1);
            cell.Value = headers[i];
            cell.Style.Font.Bold = true;
            cell.Style.Fill.BackgroundColor = XLColor.FromArgb(0x1E, 0x3A, 0x5F);
            cell.Style.Font.FontColor = XLColor.White;
        }

        // Add 2 Sample Rows
        ws.Cell(2, 1).Value = "653301";
        ws.Cell(2, 2).Value = "8904125008082";
        ws.Cell(2, 3).Value = "Sipho";
        ws.Cell(2, 4).Value = "Khumalo";
        ws.Cell(2, 5).Value = "Male";
        ws.Cell(2, 6).Value = "African";
        ws.Cell(2, 7).Value = "Apprenticeship";
        ws.Cell(2, 8).Value = "90123";
        ws.Cell(2, 9).Value = 45000;
        ws.Cell(2, 10).Value = 1;

        ws.Cell(3, 1).Value = "651202";
        ws.Cell(3, 2).Value = "9206155009081";
        ws.Cell(3, 3).Value = "Jane";
        ws.Cell(3, 4).Value = "Botha";
        ws.Cell(3, 5).Value = "Female";
        ws.Cell(3, 6).Value = "Coloured";
        ws.Cell(3, 7).Value = "Learnership";
        ws.Cell(3, 8).Value = "61549";
        ws.Cell(3, 9).Value = 32000;
        ws.Cell(3, 10).Value = 1;

        ws.Columns().AdjustToContents();

        // Hidden schema metadata sheet
        var metaSheet = wb.Worksheets.Add("_merSETA_Schema");
        metaSheet.Cell(1, 1).Value = "SchemaVersion";
        metaSheet.Cell(1, 2).Value = $"{schemeYear}.1";
        metaSheet.Cell(2, 1).Value = "SchemeYear";
        metaSheet.Cell(2, 2).Value = schemeYear;
        metaSheet.Visibility = XLWorksheetVisibility.VeryHidden;

        using var ms = new MemoryStream();
        wb.SaveAs(ms);
        return ms.ToArray();
    }

    public async Task<List<WspBulkImportBatch>> GetBatchesBySubmissionAsync(int wspSubmissionId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.WspBulkImportBatches
            .Where(b => b.WspSubmissionId == wspSubmissionId)
            .OrderByDescending(b => b.CreatedAt)
            .ToListAsync();
    }

    public async Task<WspBulkImportBatch?> GetBatchByIdAsync(int batchId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.WspBulkImportBatches
            .Include(b => b.StagedRows)
            .FirstOrDefaultAsync(b => b.Id == batchId);
    }

    public async Task<List<WspBulkImportStaging>> GetBatchExceptionsAsync(int batchId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.WspBulkImportStagings
            .Where(r => r.BatchId == batchId && !r.IsValid)
            .OrderBy(r => r.RowIndex)
            .ToListAsync();
    }

    public async Task UpdateStagedRowAsync(long stagingRowId, string? ofoCode, string? rsaIdNumber, string? programmeType, decimal? cost, string currentUsername)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var row = await db.WspBulkImportStagings.FindAsync(stagingRowId);
        if (row == null) throw new KeyNotFoundException($"Staging row {stagingRowId} not found.");

        if (ofoCode != null) row.RawOfoCode = ofoCode;
        if (rsaIdNumber != null) row.RawIdNumber = rsaIdNumber;
        if (programmeType != null) row.RawInterventionTypeCode = programmeType;
        if (cost.HasValue) row.ParsedEstimatedCost = cost.Value;

        row.ModifiedAt = DateTime.UtcNow;
        row.ModifiedBy = currentUsername;

        await db.SaveChangesAsync();
    }

    public async Task<WspBulkImportBatch> MergeDeltaWorkbookAsync(int batchId, Stream deltaStream, string currentUsername)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var batch = await db.WspBulkImportBatches.FindAsync(batchId);
        if (batch == null) throw new KeyNotFoundException($"Batch {batchId} not found.");

        using var wb = new XLWorkbook(deltaStream);
        var ws = wb.Worksheets.FirstOrDefault();
        if (ws == null) throw new InvalidOperationException("Delta workbook is empty.");

        var rows = ws.RowsUsed().Skip(1);
        foreach (var r in rows)
        {
            if (!int.TryParse(r.Cell(1).GetString().Trim(), out var lineNum)) continue;

            var existingStaged = await db.WspBulkImportStagings
                .FirstOrDefaultAsync(s => s.BatchId == batchId && s.RowIndex == lineNum);

            if (existingStaged != null)
            {
                var ofo = r.Cell(3).GetString().Trim();
                var idNum = r.Cell(4).GetString().Trim();
                var prog = r.Cell(5).GetString().Trim();
                var costStr = r.Cell(7).GetString().Trim();

                if (!string.IsNullOrWhiteSpace(ofo)) existingStaged.RawOfoCode = ofo;
                if (!string.IsNullOrWhiteSpace(idNum)) existingStaged.RawIdNumber = idNum;
                if (!string.IsNullOrWhiteSpace(prog)) existingStaged.RawInterventionTypeCode = prog;
                if (!string.IsNullOrWhiteSpace(costStr)) existingStaged.RawEstimatedCost = costStr;

                existingStaged.ModifiedAt = DateTime.UtcNow;
                existingStaged.ModifiedBy = currentUsername;
            }
        }

        await db.SaveChangesAsync();

        // Revalidate batch
        await ValidateBatchInternalAsync(db, batch);
        await db.SaveChangesAsync();

        return batch;
    }
}
