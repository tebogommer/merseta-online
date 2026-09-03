using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;

namespace Nsdms.Application.Services;

/// <summary>
/// Pre-ingestion compliance gatekeeper implementation (Option A: Streaming In-Line Pre-Flight Gatekeeper).
/// Validates raw SARS monthly levy files prior to any database operations.
/// </summary>
public partial class SarsCompliancePreProcessor : ISarsCompliancePreProcessor
{
    private static readonly Regex SdlRegex = new(@"^[Ll]\d{9}$", RegexOptions.Compiled);
    private static readonly Regex SicRegex = new(@"^\d{5}$", RegexOptions.Compiled);
    private static readonly Regex SchemeYearRegex = new(@"^(\d{4}|\d{4}-\d{2})$", RegexOptions.Compiled);

    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly ILevyService _levyService;

    public SarsCompliancePreProcessor(INsdmsDbContextFactory contextFactory, ILevyService levyService)
    {
        _contextFactory = contextFactory;
        _levyService = levyService;
    }

    public async Task<SarsComplianceReport> ValidateContentAsync(string content, string fileName, CancellationToken cancellationToken = default)
    {
        if (string.IsNullOrWhiteSpace(content))
        {
            var emptyReport = new SarsComplianceReport
            {
                FileName = fileName,
                DigitalSecuritySeal = string.Empty
            };
            emptyReport.Errors.Add(new SarsComplianceIssue(1, null, "EmptyFile", "The file content is empty or contains only whitespace.", "Error"));
            return emptyReport;
        }

        var bytes = Encoding.UTF8.GetBytes(content);
        using var memoryStream = new MemoryStream(bytes);
        return await ValidateStreamAsync(memoryStream, fileName, cancellationToken);
    }

    public async Task<SarsComplianceReport> ValidateStreamAsync(Stream stream, string fileName, CancellationToken cancellationToken = default)
    {
        var report = new SarsComplianceReport
        {
            FileName = fileName
        };

        if (stream == null || stream.CanRead == false)
        {
            report.Errors.Add(new SarsComplianceIssue(0, null, "UnreadableStream", "The provided stream is null or cannot be read.", "Error"));
            return report;
        }

        using var sha256 = SHA256.Create();
        using var cryptoStream = new CryptoStream(stream, sha256, CryptoStreamMode.Read, leaveOpen: true);
        using var reader = new StreamReader(cryptoStream, Encoding.UTF8, detectEncodingFromByteOrderMarks: true, bufferSize: 65536, leaveOpen: true);

        int lineNumber = 0;
        int dataRowCount = 0;
        decimal totalAmount = 0m;
        string? line;
        const int maxErrors = 50;

        while ((line = await reader.ReadLineAsync(cancellationToken)) != null)
        {
            lineNumber++;
            var trimmed = line.Trim();

            // Ignore empty lines or comments
            if (string.IsNullOrEmpty(trimmed) || trimmed.StartsWith("#") || trimmed.StartsWith("//"))
            {
                continue;
            }

            // Check for Trailer / Control record
            if (IsTrailerLine(trimmed))
            {
                report.HasTrailer = true;
                ParseTrailerTotals(trimmed, lineNumber, report);
                continue;
            }

            // Skip Header lines
            if (IsHeaderLine(trimmed))
            {
                continue;
            }

            // Validate Data Line
            var issue = ValidateDataLine(trimmed, lineNumber, out decimal lineAmount, out string? parsedSdl);
            if (issue != null)
            {
                report.Errors.Add(issue);
                if (report.Errors.Count >= maxErrors)
                {
                    report.Warnings.Add(new SarsComplianceIssue(
                        lineNumber, parsedSdl, "MaxErrorsExceeded",
                        "Validation halted: Maximum error threshold (50 violations) reached. Please correct file structure and re-verify.",
                        "Warning"));
                    break;
                }
            }
            else
            {
                dataRowCount++;
                totalAmount += lineAmount;
            }
        }

        report.TotalLinesRead = lineNumber;
        report.ValidDataRowCount = dataRowCount;
        report.TotalLevyAmount = totalAmount;

        // Compute Digital Security Seal
        var hashBytes = sha256.Hash ?? Array.Empty<byte>();
        report.DigitalSecuritySeal = BitConverter.ToString(hashBytes).Replace("-", string.Empty).ToLowerInvariant();

        // 1. Data Presence Check
        if (dataRowCount == 0 && report.Errors.Count == 0)
        {
            report.Errors.Add(new SarsComplianceIssue(0, null, "EmptyFile", "The file contains no readable SDL data records.", "Error"));
            return report;
        }

        // 2. Trailer Control Total Reconciliation
        if (report.HasTrailer)
        {
            if (report.TrailerDeclaredCount.HasValue && report.TrailerDeclaredCount.Value != dataRowCount)
            {
                report.Errors.Add(new SarsComplianceIssue(
                    lineNumber, null, "TrailerCountMismatch",
                    $"SARS file trailer control count mismatch: Trailer declared {report.TrailerDeclaredCount.Value.ToString("N0", CultureInfo.InvariantCulture)} records, but file contains {dataRowCount.ToString("N0", CultureInfo.InvariantCulture)} parsed data lines. Potential file truncation detected.",
                    "Error"));
            }

            if (report.TrailerDeclaredAmount.HasValue && Math.Abs(report.TrailerDeclaredAmount.Value - totalAmount) > 0.05m)
            {
                report.Errors.Add(new SarsComplianceIssue(
                    lineNumber, null, "TrailerAmountMismatch",
                    $"SARS file trailer monetary total mismatch: Trailer declared R{report.TrailerDeclaredAmount.Value.ToString("N2", CultureInfo.InvariantCulture)}, but sum of parsed records is R{totalAmount.ToString("N2", CultureInfo.InvariantCulture)} (Variance: R{Math.Abs(report.TrailerDeclaredAmount.Value - totalAmount).ToString("N2", CultureInfo.InvariantCulture)}).",
                    "Error"));
            }

            if (report.Errors.All(e => e.IssueCode != "TrailerCountMismatch" && e.IssueCode != "TrailerAmountMismatch"))
            {
                report.IsTrailerReconciled = true;
            }
        }
        else
        {
            report.Warnings.Add(new SarsComplianceIssue(
                0, null, "MissingTrailer",
                "File does not include a TRAILER or CONTROL total record. Control record verification was bypassed.",
                "Warning"));
        }

        // 3. Cryptographic Duplicate Batch Check (Idempotency)
        if (!string.IsNullOrWhiteSpace(report.DigitalSecuritySeal))
        {
            using var db = await _contextFactory.CreateDbContextAsync();
            var duplicateExists = await db.LevyFiles.AnyAsync(f =>
                f.DigitalSecuritySeal == report.DigitalSecuritySeal &&
                f.ImportStatusCode == "Imported", cancellationToken);

            if (duplicateExists)
            {
                report.Errors.Add(new SarsComplianceIssue(
                    0, null, "DuplicateBatch",
                    $"Duplicate submission: A file with identical Digital Security Seal '{report.DigitalSecuritySeal}' has already been processed into the financial ledger. Duplicate submission blocked.",
                    "Error"));
            }
        }

        return report;
    }

    private static bool IsHeaderLine(string line)
    {
        return line.StartsWith("SDL_NO", StringComparison.OrdinalIgnoreCase) ||
               line.StartsWith("SDL", StringComparison.OrdinalIgnoreCase) ||
               line.StartsWith("REF_NO", StringComparison.OrdinalIgnoreCase);
    }

    private static bool IsTrailerLine(string line)
    {
        return line.StartsWith("TRAILER", StringComparison.OrdinalIgnoreCase) ||
               line.StartsWith("CONTROL", StringComparison.OrdinalIgnoreCase) ||
               line.StartsWith("EOF", StringComparison.OrdinalIgnoreCase);
    }

    private static void ParseTrailerTotals(string line, int lineNumber, SarsComplianceReport report)
    {
        char delimiter = line.Contains('|') ? '|' :
                         line.Contains('\t') ? '\t' :
                         line.Contains(';') ? ';' : ',';

        var parts = line.Split(delimiter).Select(p => p.Trim().Trim('"')).ToArray();
        if (parts.Length >= 3)
        {
            if (int.TryParse(parts[1], NumberStyles.Any, CultureInfo.InvariantCulture, out int count))
            {
                report.TrailerDeclaredCount = count;
            }
            if (decimal.TryParse(parts[2], NumberStyles.Any, CultureInfo.InvariantCulture, out decimal total))
            {
                report.TrailerDeclaredAmount = total;
            }
        }
        else
        {
            report.Warnings.Add(new SarsComplianceIssue(
                lineNumber, null, "MalformedTrailer",
                "Trailer record format could not be parsed into [TRAILER|COUNT|TOTAL]. Control total verification may be incomplete.",
                "Warning", line));
        }
    }

    private SarsComplianceIssue? ValidateDataLine(string line, int lineNumber, out decimal parsedAmount, out string? parsedSdl)
    {
        parsedAmount = 0m;
        parsedSdl = null;

        // Delimited Lines
        if (line.Contains('|') || line.Contains(',') || line.Contains('\t') || line.Contains(';'))
        {
            char delimiter = line.Contains('|') ? '|' :
                             line.Contains('\t') ? '\t' :
                             line.Contains(';') ? ';' : ',';

            var parts = line.Split(delimiter).Select(p => p.Trim().Trim('"')).ToArray();
            if (parts.Length < 2)
            {
                return new SarsComplianceIssue(lineNumber, null, "MalformedLine", "Line contains insufficient delimited columns (minimum 2 required).", "Error", line);
            }

            parsedSdl = parts[0].ToUpperInvariant();
            if (!SdlRegex.IsMatch(parsedSdl))
            {
                return new SarsComplianceIssue(lineNumber, parsedSdl, "InvalidSdlFormat", $"Invalid SDL Number '{parsedSdl}'. Must start with 'L' followed by exactly 9 digits (e.g. L123456789).", "Error", line);
            }

            // Validate Scheme Year if present
            if (parts.Length > 1 && !string.IsNullOrWhiteSpace(parts[1]) && !SchemeYearRegex.IsMatch(parts[1]))
            {
                return new SarsComplianceIssue(lineNumber, parsedSdl, "InvalidSchemeYear", $"Invalid Scheme Year '{parts[1]}'. Must be 4 digits (e.g. 2026).", "Error", line);
            }

            // Case 1: 8 to 10 columns format
            if (parts.Length >= 8)
            {
                if (!decimal.TryParse(parts[2], NumberStyles.Any, CultureInfo.InvariantCulture, out decimal mandatory) ||
                    !decimal.TryParse(parts[3], NumberStyles.Any, CultureInfo.InvariantCulture, out decimal discretionary) ||
                    !decimal.TryParse(parts[4], NumberStyles.Any, CultureInfo.InvariantCulture, out decimal admin) ||
                    !decimal.TryParse(parts[5], NumberStyles.Any, CultureInfo.InvariantCulture, out decimal qcto) ||
                    !decimal.TryParse(parts[6], NumberStyles.Any, CultureInfo.InvariantCulture, out decimal interest) ||
                    !decimal.TryParse(parts[7], NumberStyles.Any, CultureInfo.InvariantCulture, out decimal penalty))
                {
                    return new SarsComplianceIssue(lineNumber, parsedSdl, "InvalidAmountFormat", "One or more statutory levy amounts could not be parsed as valid numbers.", "Error", line);
                }

                if (parts.Length >= 9 && decimal.TryParse(parts[8], NumberStyles.Any, CultureInfo.InvariantCulture, out decimal totalCol))
                {
                    parsedAmount = totalCol;
                }
                else
                {
                    parsedAmount = mandatory + discretionary + admin + qcto + interest + penalty;
                }

                if (parts.Length >= 10 && !string.IsNullOrWhiteSpace(parts[9]))
                {
                    var sic = parts[9].Trim();
                    if (!SicRegex.IsMatch(sic))
                    {
                        return new SarsComplianceIssue(lineNumber, parsedSdl, "InvalidSicCode", $"Invalid 5-digit SIC code '{sic}'.", "Error", line);
                    }
                }
            }
            // Case 2: 4-column (SDL, Year, SIC, Total)
            else if (parts.Length >= 4)
            {
                if (!string.IsNullOrWhiteSpace(parts[2]) && !SicRegex.IsMatch(parts[2].Trim()))
                {
                    return new SarsComplianceIssue(lineNumber, parsedSdl, "InvalidSicCode", $"Invalid 5-digit SIC code '{parts[2]}'.", "Error", line);
                }
                if (!decimal.TryParse(parts[3], NumberStyles.Any, CultureInfo.InvariantCulture, out parsedAmount))
                {
                    return new SarsComplianceIssue(lineNumber, parsedSdl, "InvalidAmountFormat", $"Invalid monetary amount '{parts[3]}'.", "Error", line);
                }
            }
            // Case 3: 3-column (SDL, Year, Total)
            else if (parts.Length == 3)
            {
                if (!decimal.TryParse(parts[2], NumberStyles.Any, CultureInfo.InvariantCulture, out parsedAmount))
                {
                    return new SarsComplianceIssue(lineNumber, parsedSdl, "InvalidAmountFormat", $"Invalid monetary amount '{parts[2]}'.", "Error", line);
                }
            }
            // Case 4: 2-column (SDL, Total)
            else if (parts.Length == 2)
            {
                if (!decimal.TryParse(parts[1], NumberStyles.Any, CultureInfo.InvariantCulture, out parsedAmount))
                {
                    return new SarsComplianceIssue(lineNumber, parsedSdl, "InvalidAmountFormat", $"Invalid monetary amount '{parts[1]}'.", "Error", line);
                }
            }

            return null;
        }

        // Fixed-width format
        if (line.Length < 14)
        {
            return new SarsComplianceIssue(lineNumber, null, "MalformedFixedWidthLine", "Fixed-width record length is less than minimum 14 characters.", "Error", line);
        }

        parsedSdl = line.Substring(0, Math.Min(10, line.Length)).Trim().ToUpperInvariant();
        if (!SdlRegex.IsMatch(parsedSdl))
        {
            return new SarsComplianceIssue(lineNumber, parsedSdl, "InvalidSdlFormat", $"Invalid fixed-width SDL Number '{parsedSdl}'. Must start with 'L' followed by 9 digits.", "Error", line);
        }

        if (line.Length >= 84)
        {
            if (decimal.TryParse(line.Substring(74, Math.Min(10, line.Length - 74)).Trim(), NumberStyles.Any, CultureInfo.InvariantCulture, out decimal total))
            {
                parsedAmount = total;
            }
        }
        else if (line.Length >= 24)
        {
            if (decimal.TryParse(line.Substring(14, Math.Min(10, line.Length - 14)).Trim(), NumberStyles.Any, CultureInfo.InvariantCulture, out decimal amount))
            {
                parsedAmount = amount;
            }
        }

        return null;
    }
}
