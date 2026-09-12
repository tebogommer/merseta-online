using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Globalization;
using System.IO;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

/// <summary>
/// Production implementation of Option A: Reactive Streaming Pipeline with SqlBulkCopy &amp; Staging Table.
/// Streams raw SARS levy files line-by-line, computes cryptographic SHA-256 Digital Security Seals,
/// verifies trailer control totals, writes to high-speed staging via SqlBulkCopy, detects Inter-SETA boundaries,
/// and atomically promotes into the production financial ledger.
/// </summary>
public class SarsLevyStreamingPipeline : ISarsLevyStreamingPipeline
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly ISarsBulkStagingWriter _stagingWriter;
    private readonly ILevyService _levyService;
    private readonly IAuditService _audit;
    private readonly ISarsCompliancePreProcessor _compliancePreProcessor;

    public SarsLevyStreamingPipeline(
        INsdmsDbContextFactory contextFactory,
        ISarsBulkStagingWriter stagingWriter,
        ILevyService levyService,
        IAuditService audit,
        ISarsCompliancePreProcessor compliancePreProcessor)
    {
        _contextFactory = contextFactory;
        _stagingWriter = stagingWriter;
        _levyService = levyService;
        _audit = audit;
        _compliancePreProcessor = compliancePreProcessor;
    }

    public async Task<bool> IsDuplicateFileSealAsync(string digitalSecuritySeal)
    {
        if (string.IsNullOrWhiteSpace(digitalSecuritySeal))
        {
            return false;
        }

        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.LevyFiles.AnyAsync(f =>
            f.DigitalSecuritySeal == digitalSecuritySeal &&
            f.ImportStatusCode == "Imported");
    }

    public async Task<LevyStreamingResult> ProcessSarsStreamAsync(
        Stream fileStream,
        string fileName,
        string currentUsername = "SYSTEM",
        CancellationToken cancellationToken = default)
    {
        var stopwatch = Stopwatch.StartNew();

        // Manage seekability for two-pass in-line gatekeeping
        Stream activeStream = fileStream;
        MemoryStream? bufferStream = null;

        if (!fileStream.CanSeek)
        {
            bufferStream = new MemoryStream();
            await fileStream.CopyToAsync(bufferStream, cancellationToken);
            bufferStream.Seek(0, SeekOrigin.Begin);
            activeStream = bufferStream;
        }

        try
        {
            // Pass 1: Streaming In-Line Pre-Flight Compliance Gatekeeper (Option A)
            activeStream.Seek(0, SeekOrigin.Begin);
            var complianceReport = await _compliancePreProcessor.ValidateStreamAsync(activeStream, fileName, cancellationToken);
            if (!complianceReport.IsCompliant)
            {
                throw new SarsComplianceException(complianceReport);
            }

            // Pass 2: Proceed with High-Speed Staging & Ledger Promotion
            activeStream.Seek(0, SeekOrigin.Begin);
            var batchId = $"SARS-STREAM-{DateTime.UtcNow:yyyyMMddHHmmss}-{Guid.NewGuid().ToString("N")[..6].ToUpperInvariant()}";

            using var sha256 = SHA256.Create();
            using var cryptoStream = new CryptoStream(activeStream, sha256, CryptoStreamMode.Read, leaveOpen: true);
            using var reader = new StreamReader(cryptoStream, Encoding.UTF8, detectEncodingFromByteOrderMarks: true, bufferSize: 65536, leaveOpen: true);

        var stagingBuffer = new List<SarsLevyStaging>(5000);
        int currentLineNumber = 0;
        int parsedDataLineCount = 0;
        decimal totalAccumulatedAmount = 0m;
        SarsControlTotals? fileControlTotals = null;

        string? line;
        while ((line = await reader.ReadLineAsync(cancellationToken)) != null)
        {
            currentLineNumber++;
            var trimmed = line.Trim();
            if (string.IsNullOrEmpty(trimmed) || trimmed.StartsWith("#") || trimmed.StartsWith("//"))
            {
                continue;
            }

            var parsed = ParseRawLine(trimmed, currentLineNumber);
            if (parsed == null)
            {
                continue;
            }

            if (parsed.IsTrailer)
            {
                fileControlTotals = parsed.TrailerTotals;
                continue;
            }

            parsedDataLineCount++;
            totalAccumulatedAmount += parsed.TotalLevyAmount;

            var stagingRow = new SarsLevyStaging
            {
                BatchIdentifier = batchId,
                LineNumber = parsed.LineNumber,
                RawRecord = parsed.RawRecord.Length > 1000 ? parsed.RawRecord[..1000] : parsed.RawRecord,
                SdlNumber = parsed.SdlNumber,
                SchemeYear = parsed.SchemeYear,
                SicCode = parsed.SicCode,
                MandatoryLevyAmount = parsed.MandatoryLevyAmount,
                DiscretionaryLevyAmount = parsed.DiscretionaryLevyAmount,
                AdminLevyAmount = parsed.AdminLevyAmount,
                QctoLevyAmount = parsed.QctoLevyAmount,
                InterestAmount = parsed.InterestAmount,
                PenaltyAmount = parsed.PenaltyAmount,
                TotalLevyAmount = parsed.TotalLevyAmount,
                StagingStatus = "Pending",
                CreatedAt = DateTime.UtcNow,
                CreatedBy = currentUsername
            };

            stagingBuffer.Add(stagingRow);

            // Flush micro-batch to staging table
            if (stagingBuffer.Count >= 5000)
            {
                await _stagingWriter.BulkWriteStagingAsync(stagingBuffer, cancellationToken);
                stagingBuffer.Clear();
            }
        }

        // Flush remaining buffer
        if (stagingBuffer.Count > 0)
        {
            await _stagingWriter.BulkWriteStagingAsync(stagingBuffer, cancellationToken);
            stagingBuffer.Clear();
        }

        if (parsedDataLineCount == 0)
        {
            throw new InvalidOperationException("No valid SARS levy records found in the provided stream.");
        }

        // 2. Finalize Cryptographic Digital Security Seal
        var hashBytes = sha256.Hash ?? Array.Empty<byte>();
        var digitalSecuritySeal = BitConverter.ToString(hashBytes).Replace("-", string.Empty).ToLowerInvariant();

        // 3. Idempotency Check: Prevent duplicate processing of the exact same physical file
        if (await IsDuplicateFileSealAsync(digitalSecuritySeal))
        {
            throw new InvalidOperationException(
                $"Duplicate SARS levy file rejected. A batch with identical Digital Security Seal '{digitalSecuritySeal}' has already been processed. Duplicate import blocked to protect statutory budget envelopes.");
        }

        // 4. Trailer Control Total Reconciliation
        bool isControlValidated = false;
        if (fileControlTotals != null)
        {
            if (fileControlTotals.ExpectedRecordCount != parsedDataLineCount)
            {
                throw new InvalidOperationException(
                    $"SARS file trailer control count mismatch! Trailer declared {fileControlTotals.ExpectedRecordCount} records, but stream contained {parsedDataLineCount} records. Processing aborted to prevent silent truncation.");
            }

            if (Math.Abs(fileControlTotals.ExpectedTotalAmount - totalAccumulatedAmount) > 0.05m)
            {
                throw new InvalidOperationException(
                    $"SARS file trailer control monetary total mismatch! Trailer declared R{fileControlTotals.ExpectedTotalAmount:N2}, but stream totaled R{totalAccumulatedAmount:N2} (Variance: R{Math.Abs(fileControlTotals.ExpectedTotalAmount - totalAccumulatedAmount):N2}). Processing aborted to prevent monetary discrepancy.");
            }

            isControlValidated = true;
        }

        // 5. Atomic Promotion & Automated Boundary Detection
        using var db = await _contextFactory.CreateDbContextAsync();

        // Create Master LevyFile Batch record first
        var levyFile = new LevyFile
        {
            FileName = fileName,
            FileRef = $"SARS-{DateTime.UtcNow:yyyyMMdd}-{Guid.NewGuid().ToString("N")[..6].ToUpperInvariant()}",
            ImportDate = DateTime.UtcNow,
            TotalRecords = parsedDataLineCount,
            TotalAmount = totalAccumulatedAmount,
            ImportStatusCode = "Imported",
            DigitalSecuritySeal = digitalSecuritySeal,
            ControlRecordCount = fileControlTotals?.ExpectedRecordCount,
            ControlTotalAmount = fileControlTotals?.ExpectedTotalAmount,
            IsControlValidated = isControlValidated,
            ProcessingDurationMs = 0,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        db.LevyFiles.Add(levyFile);
        await db.SaveChangesAsync(cancellationToken);

        int outOfScopeCount = 0;
        int sicMismatchCount = 0;

        bool isSqlServer = string.Equals(db.Database.ProviderName, "Microsoft.EntityFrameworkCore.SqlServer", StringComparison.OrdinalIgnoreCase)
            || (db.Database.ProviderName?.Contains("SqlServer", StringComparison.OrdinalIgnoreCase) == true);

        if (isSqlServer)
        {
            // Execute high-speed set-based stored procedure [dbo].[usp_PromoteSarsLevyBatch]
            await db.Database.ExecuteSqlRawAsync(
                "EXEC [dbo].[usp_PromoteSarsLevyBatch] @BatchIdentifier = {0}, @LevyFileId = {1}, @CurrentUsername = {2}",
                batchId, levyFile.Id, currentUsername);

            outOfScopeCount = await db.SarsLevyStagings.CountAsync(s => s.BatchIdentifier == batchId && s.IsOutOfScopeSeta, cancellationToken);
            sicMismatchCount = await db.SarsLevyStagings.CountAsync(s => s.BatchIdentifier == batchId && s.HasSicCodeMismatch, cancellationToken);
            var promotedCount = await db.SarsLevyStagings.CountAsync(s => s.BatchIdentifier == batchId && s.StagingStatus == "Promoted", cancellationToken);
            if (promotedCount > 0)
            {
                levyFile.TotalRecords = promotedCount;
            }
        }
        else
        {
            // Fallback for non-SQL Server environments (e.g. SQLite xUnit test harness)
            var stagedRows = await db.SarsLevyStagings
                .Where(s => s.BatchIdentifier == batchId)
                .OrderBy(s => s.LineNumber)
                .ToListAsync(cancellationToken);

            var distinctSdls = stagedRows.Select(s => s.SdlNumber).Distinct().ToList();
            var organisations = new List<Organisation>();
            const int chunkSize = 1500;
            for (int i = 0; i < distinctSdls.Count; i += chunkSize)
            {
                var sdlChunk = distinctSdls.Skip(i).Take(chunkSize).ToList();
                var matchedOrgs = await db.Organisations
                    .Where(o => sdlChunk.Contains(o.SdlNumber))
                    .ToListAsync(cancellationToken);
                organisations.AddRange(matchedOrgs);
            }

            var distinctSics = stagedRows
                .Where(s => !string.IsNullOrWhiteSpace(s.SicCode))
                .Select(s => s.SicCode!)
                .Distinct()
                .ToList();
            var sicTypes = await db.SicCodeTypes
                .Where(s => distinctSics.Contains(s.Code))
                .ToListAsync(cancellationToken);

            var productionLines = new List<LevyFileLine>(stagedRows.Count);

            foreach (var stage in stagedRows)
            {
                var org = organisations.FirstOrDefault(o => o.SdlNumber == stage.SdlNumber);
                var matchedSic = !string.IsNullOrWhiteSpace(stage.SicCode)
                    ? sicTypes.FirstOrDefault(s => s.Code == stage.SicCode)
                    : null;

                if (matchedSic != null)
                {
                    stage.ChamberCode = matchedSic.ChamberCode;
                    stage.SetaCode = matchedSic.SetaCode;

                    // Out-of-Scope SETA Check (SETA != 17)
                    if (matchedSic.SetaCode != "17")
                    {
                        stage.IsOutOfScopeSeta = true;
                        outOfScopeCount++;

                        db.SarsLevyReconAudits.Add(new SarsLevyReconAudit
                        {
                            FinancialYear = stage.SchemeYear.Length >= 4 ? stage.SchemeYear[..4] : DateTime.UtcNow.Year.ToString(),
                            SchemeYear = stage.SchemeYear,
                            SdlNumber = stage.SdlNumber,
                            OrganisationId = org?.Id,
                            TotalSarsLeviesReceived = stage.TotalLevyAmount,
                            TotalCalculatedLeviesExpected = 0m,
                            VarianceAmount = stage.TotalLevyAmount,
                            DiscrepancyReasonCode = "OutOfScopeSeta",
                            CounterpartSetaCode = matchedSic.SetaCode,
                            ActualSarsSicCode = stage.SicCode,
                            ActualSarsChamberCode = matchedSic.ChamberCode,
                            ExpectedSicCode = org?.SicCode,
                            ExpectedChamberCode = org?.ChamberCode,
                            AuditStatusCode = "DiscrepancyFlagged",
                            AuditNotes = $"Streaming SARS levy reported non-merSETA SIC Code '{stage.SicCode}' belonging to SETA '{matchedSic.SetaCode}'. Out-of-scope Inter-SETA transfer required.",
                            AuditorUserId = currentUsername,
                            ReconciliationDate = DateTime.UtcNow,
                            CreatedAt = DateTime.UtcNow,
                            CreatedBy = currentUsername
                        });

                        if (org != null)
                        {
                            var hasActiveTransfer = await db.InterSetaTransfers.AnyAsync(t =>
                                t.OrganisationId == org.Id && t.TransferStatusCode == "Initiated", cancellationToken);
                            if (!hasActiveTransfer)
                            {
                                db.InterSetaTransfers.Add(new InterSetaTransfer
                                {
                                    OrganisationId = org.Id,
                                    TransferType = "Outgoing",
                                    OtherSetaCode = matchedSic.SetaCode,
                                    OtherSetaName = matchedSic.Description ?? $"SETA {matchedSic.SetaCode}",
                                    TransferReason = $"Streaming boundary detection: SARS reported non-merSETA SIC Code {stage.SicCode} (SETA {matchedSic.SetaCode})",
                                    EffectiveDate = DateTime.UtcNow,
                                    TransferStatusCode = "Initiated",
                                    TransferAmount = stage.TotalLevyAmount,
                                    CreatedAt = DateTime.UtcNow,
                                    CreatedBy = currentUsername
                                });
                            }
                        }
                    }
                }
                else if (org != null && !string.IsNullOrWhiteSpace(org.ChamberCode))
                {
                    stage.ChamberCode = org.ChamberCode;
                }

                // SIC Code Mismatch Check
                if (org != null && !string.IsNullOrWhiteSpace(stage.SicCode) && !string.IsNullOrWhiteSpace(org.SicCode) && stage.SicCode != org.SicCode)
                {
                    stage.HasSicCodeMismatch = true;
                    sicMismatchCount++;

                    db.SarsLevyReconAudits.Add(new SarsLevyReconAudit
                    {
                        FinancialYear = stage.SchemeYear.Length >= 4 ? stage.SchemeYear[..4] : DateTime.UtcNow.Year.ToString(),
                        SchemeYear = stage.SchemeYear,
                        SdlNumber = stage.SdlNumber,
                        OrganisationId = org.Id,
                        TotalSarsLeviesReceived = stage.TotalLevyAmount,
                        TotalCalculatedLeviesExpected = stage.TotalLevyAmount,
                        VarianceAmount = 0m,
                        DiscrepancyReasonCode = "SicCodeMismatch",
                        ExpectedSicCode = org.SicCode,
                        ActualSarsSicCode = stage.SicCode,
                        ExpectedChamberCode = org.ChamberCode,
                        ActualSarsChamberCode = matchedSic?.ChamberCode,
                        AuditStatusCode = "DiscrepancyFlagged",
                        AuditNotes = $"Streaming SARS declared SIC Code '{stage.SicCode}' differs from verified master record '{org.SicCode}'.",
                        AuditorUserId = currentUsername,
                        ReconciliationDate = DateTime.UtcNow,
                        CreatedAt = DateTime.UtcNow,
                        CreatedBy = currentUsername
                    });
                }

                stage.StagingStatus = "Promoted";
                stage.PromotedLevyFileId = levyFile.Id;
                stage.ModifiedAt = DateTime.UtcNow;
                stage.ModifiedBy = currentUsername;

                var prodLine = new LevyFileLine
                {
                    LevyFileId = levyFile.Id,
                    SdlNumber = stage.SdlNumber,
                    SchemeYear = stage.SchemeYear,
                    SicCode = stage.SicCode,
                    ChamberCode = stage.ChamberCode,
                    SetaCode = stage.SetaCode,
                    MandatoryLevyAmount = stage.MandatoryLevyAmount,
                    DiscretionaryLevyAmount = stage.DiscretionaryLevyAmount,
                    AdminLevyAmount = stage.AdminLevyAmount,
                    QctoLevyAmount = stage.QctoLevyAmount,
                    InterestAmount = stage.InterestAmount,
                    PenaltyAmount = stage.PenaltyAmount,
                    TotalLevyAmount = stage.TotalLevyAmount,
                    IsOutOfScopeSeta = stage.IsOutOfScopeSeta,
                    HasSicCodeMismatch = stage.HasSicCodeMismatch,
                    IsReconciled = false,
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = currentUsername
                };
                productionLines.Add(prodLine);
            }

            db.LevyFileLines.AddRange(productionLines);
            levyFile.TotalRecords = productionLines.Count;
            await db.SaveChangesAsync(cancellationToken);
        }

        stopwatch.Stop();
        levyFile.ProcessingDurationMs = stopwatch.ElapsedMilliseconds;

        // Audited Change Log (Double-Write)
        _audit.LogAction(db, "LevyFile", levyFile.Id, "StreamingSarsIngestion", currentUsername, null, new
        {
            levyFile.FileName,
            levyFile.FileRef,
            levyFile.DigitalSecuritySeal,
            levyFile.TotalRecords,
            levyFile.TotalAmount,
            levyFile.IsControlValidated,
            OutOfScopeCount = outOfScopeCount,
            SicMismatchCount = sicMismatchCount,
            DurationMs = stopwatch.ElapsedMilliseconds
        });

        await db.SaveChangesAsync(cancellationToken);

        return new LevyStreamingResult(
            LevyFileId: levyFile.Id,
            BatchIdentifier: batchId,
            FileName: fileName,
            DigitalSecuritySeal: digitalSecuritySeal,
            TotalRecords: levyFile.TotalRecords,
            TotalAmount: totalAccumulatedAmount,
            IsControlValidated: isControlValidated,
            OutOfScopeCount: outOfScopeCount,
            SicMismatchCount: sicMismatchCount,
            ProcessingDurationMs: stopwatch.ElapsedMilliseconds,
            Success: true,
            StatusMessage: $"Successfully streamed and promoted {levyFile.TotalRecords:N0} SARS levy records in {stopwatch.ElapsedMilliseconds} ms."
        );
        }
        finally
        {
            bufferStream?.Dispose();
        }
    }

    private ParsedSarsStreamLine? ParseRawLine(string trimmed, int lineNumber)
    {
        // 1. Detect Trailer/Control Total Record
        if (trimmed.StartsWith("TRAILER", StringComparison.OrdinalIgnoreCase) ||
            trimmed.StartsWith("CONTROL", StringComparison.OrdinalIgnoreCase) ||
            trimmed.StartsWith("EOF", StringComparison.OrdinalIgnoreCase))
        {
            char delim = trimmed.Contains('|') ? '|' :
                         trimmed.Contains('\t') ? '\t' :
                         trimmed.Contains(';') ? ';' : ',';

            var tParts = trimmed.Split(delim).Select(p => p.Trim().Trim('"')).ToArray();
            if (tParts.Length >= 3 &&
                int.TryParse(tParts[1], NumberStyles.Any, CultureInfo.InvariantCulture, out int count) &&
                decimal.TryParse(tParts[2], NumberStyles.Any, CultureInfo.InvariantCulture, out decimal total))
            {
                return new ParsedSarsStreamLine(
                    lineNumber, trimmed, "TRAILER", "", null, 0, 0, 0, 0, 0, 0, 0,
                    IsTrailer: true,
                    TrailerTotals: new SarsControlTotals(count, total));
            }
            return null;
        }

        // 2. Skip Header lines
        if (trimmed.StartsWith("SDL", StringComparison.OrdinalIgnoreCase) ||
            trimmed.StartsWith("REF_NO", StringComparison.OrdinalIgnoreCase) ||
            trimmed.StartsWith("SDL_NO", StringComparison.OrdinalIgnoreCase))
        {
            return null;
        }

        // 3. Delimited Parsing
        if (trimmed.Contains('|') || trimmed.Contains(',') || trimmed.Contains('\t') || trimmed.Contains(';'))
        {
            char delimiter = trimmed.Contains('|') ? '|' :
                             trimmed.Contains('\t') ? '\t' :
                             trimmed.Contains(';') ? ';' : ',';

            var parts = trimmed.Split(delimiter).Select(p => p.Trim().Trim('"')).ToArray();
            if (parts.Length < 2) return null;

            var rawSdl = parts[0].Trim().ToUpperInvariant();
            if (string.IsNullOrWhiteSpace(rawSdl)) return null;

            var schemeYear = parts.Length > 1 && !string.IsNullOrWhiteSpace(parts[1])
                ? parts[1].Trim()
                : DateTime.UtcNow.Year.ToString();

            decimal totalAmount = 0m;
            decimal mandatory = 0m, discretionary = 0m, admin = 0m, qcto = 0m, interest = 0m, penalty = 0m;
            string? extractedSic = null;

            // Full 8-10 column format
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
            // 4-column: SDL, Year, SIC, Amount
            else if (parts.Length >= 4)
            {
                if (parts[2].Trim().Length == 5 && parts[2].Trim().All(char.IsDigit))
                {
                    extractedSic = parts[2].Trim();
                }
                decimal.TryParse(parts[3], NumberStyles.Any, CultureInfo.InvariantCulture, out totalAmount);
                var split = _levyService.CalculateStatutorySplit(totalAmount);
                mandatory = split.MandatoryGrantAmount;
                discretionary = split.DiscretionaryGrantAmount;
                admin = split.AdminLevyAmount;
                qcto = split.QctoLevyAmount;
            }
            // 3-column: SDL, Year, Amount
            else if (parts.Length == 3)
            {
                decimal.TryParse(parts[2], NumberStyles.Any, CultureInfo.InvariantCulture, out totalAmount);
                var split = _levyService.CalculateStatutorySplit(totalAmount);
                mandatory = split.MandatoryGrantAmount;
                discretionary = split.DiscretionaryGrantAmount;
                admin = split.AdminLevyAmount;
                qcto = split.QctoLevyAmount;
            }
            // 2-column: SDL, Amount
            else if (parts.Length == 2)
            {
                decimal.TryParse(parts[1], NumberStyles.Any, CultureInfo.InvariantCulture, out totalAmount);
                schemeYear = DateTime.UtcNow.Year.ToString();
                var split = _levyService.CalculateStatutorySplit(totalAmount);
                mandatory = split.MandatoryGrantAmount;
                discretionary = split.DiscretionaryGrantAmount;
                admin = split.AdminLevyAmount;
                qcto = split.QctoLevyAmount;
            }

            if (totalAmount <= 0)
            {
                totalAmount = mandatory + discretionary + admin + qcto + interest + penalty;
            }

            return new ParsedSarsStreamLine(
                lineNumber, trimmed, rawSdl, schemeYear, extractedSic,
                mandatory, discretionary, admin, qcto, interest, penalty, totalAmount);
        }

        // 4. Fixed-Width Parsing
        if (trimmed.Length >= 14)
        {
            var sdl = trimmed.Substring(0, Math.Min(10, trimmed.Length)).Trim();
            var year = trimmed.Length >= 14 ? trimmed.Substring(10, 4).Trim() : DateTime.UtcNow.Year.ToString();

            decimal mandatory = 0m, discretionary = 0m, admin = 0m, qcto = 0m, interest = 0m, penalty = 0m, total = 0m;
            if (trimmed.Length >= 24) decimal.TryParse(trimmed.Substring(14, Math.Min(10, trimmed.Length - 14)).Trim(), NumberStyles.Any, CultureInfo.InvariantCulture, out mandatory);
            if (trimmed.Length >= 34) decimal.TryParse(trimmed.Substring(24, Math.Min(10, trimmed.Length - 24)).Trim(), NumberStyles.Any, CultureInfo.InvariantCulture, out discretionary);
            if (trimmed.Length >= 44) decimal.TryParse(trimmed.Substring(34, Math.Min(10, trimmed.Length - 34)).Trim(), NumberStyles.Any, CultureInfo.InvariantCulture, out admin);
            if (trimmed.Length >= 54) decimal.TryParse(trimmed.Substring(44, Math.Min(10, trimmed.Length - 44)).Trim(), NumberStyles.Any, CultureInfo.InvariantCulture, out qcto);
            if (trimmed.Length >= 64) decimal.TryParse(trimmed.Substring(54, Math.Min(10, trimmed.Length - 54)).Trim(), NumberStyles.Any, CultureInfo.InvariantCulture, out interest);
            if (trimmed.Length >= 74) decimal.TryParse(trimmed.Substring(64, Math.Min(10, trimmed.Length - 64)).Trim(), NumberStyles.Any, CultureInfo.InvariantCulture, out penalty);
            if (trimmed.Length >= 84) decimal.TryParse(trimmed.Substring(74, Math.Min(10, trimmed.Length - 74)).Trim(), NumberStyles.Any, CultureInfo.InvariantCulture, out total);
            else total = mandatory + discretionary + admin + qcto + interest + penalty;

            if (mandatory == 0m && discretionary == 0m && admin == 0m && total > 0)
            {
                var split = _levyService.CalculateStatutorySplit(total);
                mandatory = split.MandatoryGrantAmount;
                discretionary = split.DiscretionaryGrantAmount;
                admin = split.AdminLevyAmount;
                qcto = split.QctoLevyAmount;
            }

            return new ParsedSarsStreamLine(
                lineNumber, trimmed, sdl, year, null,
                mandatory, discretionary, admin, qcto, interest, penalty, total);
        }

        return null;
    }
}
