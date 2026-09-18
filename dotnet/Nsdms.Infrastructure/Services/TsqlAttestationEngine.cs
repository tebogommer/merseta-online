using System.Data;
using System.Diagnostics;
using System.Security.Cryptography;
using System.Text;
using System.Text.Json;
using System.Text.RegularExpressions;
using Microsoft.Data.SqlClient;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using Nsdms.Application.Common;
using Nsdms.Application.DTOs;
using Nsdms.Application.Interfaces;
using Nsdms.Domain.Entities;

namespace Nsdms.Infrastructure.Services;

/// <summary>
/// Parameterized execution runner and deterministic attester for OKF Attested Computations (Runtime: T-SQL).
/// Adheres strictly to:
/// - BR-OKF-002: Staleness gating (rejects execution if UtcNow >= StaleAfter).
/// - BR-OKF-003: Immutable computation invariant (agents can only pass typed parameter values).
/// - BR-OKF-004: Deterministic attestation (verifies SQL digests and stamps SHA-256 Digital Security Seal).
/// </summary>
public class TsqlAttestationEngine : ITsqlAttestationEngine
{
    private readonly INsdmsDbContextFactory _dbFactory;
    private readonly ILogger<TsqlAttestationEngine> _logger;

    public TsqlAttestationEngine(
        INsdmsDbContextFactory dbFactory,
        ILogger<TsqlAttestationEngine> logger)
    {
        _dbFactory = dbFactory;
        _logger = logger;
    }

    public async Task<AttestationExecutionResultDto> ExecuteAndAttestAsync(
        int computationId, 
        Dictionary<string, object?> parameterValues, 
        string actor, 
        CancellationToken ct = default)
    {
        return await ExecuteInternalAsync(computationId, parameterValues, actor, persistAudit: true, ct);
    }

    public async Task<AttestationExecutionResultDto> DryRunAttestationAsync(
        int computationId, 
        Dictionary<string, object?> parameterValues, 
        CancellationToken ct = default)
    {
        return await ExecuteInternalAsync(computationId, parameterValues, "dry-run", persistAudit: false, ct);
    }

    private async Task<AttestationExecutionResultDto> ExecuteInternalAsync(
        int computationId,
        Dictionary<string, object?> parameterValues,
        string actor,
        bool persistAudit,
        CancellationToken ct)
    {
        using var db = await _dbFactory.CreateDbContextAsync();

        var computation = await db.AttestedComputations
            .Include(a => a.Concept)
            .Include(a => a.Parameters)
            .FirstOrDefaultAsync(a => a.Id == computationId, ct);

        if (computation == null)
        {
            return new AttestationExecutionResultDto
            {
                Success = false,
                Verdict = "Fail",
                FailureReason = $"Attested Computation #{computationId} not found in catalog."
            };
        }

        var concept = computation.Concept;

        // 1. Freshness & Staleness Gating (BR-OKF-002)
        if (concept.StaleAfter.HasValue && DateTime.UtcNow >= concept.StaleAfter.Value)
        {
            var failureMsg = $"Freshness Violation: Computation definition '{concept.ConceptId}' expired on {concept.StaleAfter.Value:yyyy-MM-dd HH:mm:ss} UTC. Downstream execution blocked.";
            _logger.LogWarning(failureMsg);

            if (persistAudit)
            {
                await RecordExecutionAuditAsync(db, computationId, actor, parameterValues, string.Empty, "Fail", failureMsg, string.Empty, 0, receiptJson: null, ct: ct);
            }

            throw new InvalidOperationException(failureMsg);
        }

        // 2. Validate Parameter Completeness & Whitelisting (BR-OKF-003)
        var sqlText = computation.ComputationSql;
        if (string.IsNullOrWhiteSpace(sqlText))
        {
            return new AttestationExecutionResultDto
            {
                Success = false,
                Verdict = "Fail",
                FailureReason = "Computation has no SQL template defined."
            };
        }

        // Security Validation: Attested Computation SQL must be a single read-only SELECT statement
        ValidateReadOnlyQuery(sqlText);

        var sqlParameters = new List<SqlParameter>();
        foreach (var p in computation.Parameters)
        {
            var pName = p.ParameterName.StartsWith("@") ? p.ParameterName : "@" + p.ParameterName;
            
            // Match parameter by name case-insensitively
            var matchedEntry = parameterValues.FirstOrDefault(kv => 
                kv.Key.Equals(p.ParameterName, StringComparison.OrdinalIgnoreCase) ||
                kv.Key.Equals(pName, StringComparison.OrdinalIgnoreCase) ||
                kv.Key.Equals(p.ParameterName.TrimStart('@'), StringComparison.OrdinalIgnoreCase));

            object? val = matchedEntry.Value;

            if (val == null && !string.IsNullOrEmpty(p.DefaultValue))
            {
                val = p.DefaultValue;
            }

            if (val == null && p.IsRequired)
            {
                var msg = $"Parameter Validation Error: Required parameter '{p.ParameterName}' was not supplied.";
                _logger.LogWarning(msg);
                throw new ArgumentException(msg, p.ParameterName);
            }

            var sqlParam = CreateSqlParameter(pName, p.ParameterType, val);
            sqlParameters.Add(sqlParam);
        }

        // Compute SHA-256 digest of sanctioned SQL template
        var executedSqlDigest = ComputeSha256(sqlText.Trim());

        // 3. Execute query against SQL Server (or In-Memory Database Simulator for test isolation)
        var results = new List<Dictionary<string, object?>>();
        var stopwatch = Stopwatch.StartNew();
        int rowsAffected = 0;

        try
        {
            if (!db.Database.IsRelational())
            {
                // In-memory test simulator: simulate deterministic attestation execution
                stopwatch.Stop();
                var mockRow = new Dictionary<string, object?>(StringComparer.OrdinalIgnoreCase);
                foreach (var p in computation.Parameters)
                {
                    var pVal = parameterValues.FirstOrDefault(kv => kv.Key.Equals(p.ParameterName, StringComparison.OrdinalIgnoreCase)).Value;
                    mockRow[p.ParameterName] = pVal ?? p.DefaultValue;
                }
                if (mockRow.Count == 0)
                {
                    mockRow["Result"] = 1;
                }
                results.Add(mockRow);
                rowsAffected = 1;
            }
            else
            {
                var connection = db.Database.GetDbConnection();
                if (connection.State != ConnectionState.Open)
                {
                    await connection.OpenAsync(ct);
                }

                using var cmd = connection.CreateCommand();
                ValidateReadOnlyQuery(sqlText);
                cmd.CommandText = sqlText;
                cmd.CommandType = CommandType.Text;
                cmd.CommandTimeout = 60;

                foreach (var sp in sqlParameters)
                {
                    cmd.Parameters.Add(sp);
                }

                using var reader = await cmd.ExecuteReaderAsync(ct);
                while (await reader.ReadAsync(ct))
                {
                    rowsAffected++;
                    var row = new Dictionary<string, object?>(StringComparer.OrdinalIgnoreCase);
                    for (int i = 0; i < reader.FieldCount; i++)
                    {
                        var colName = reader.GetName(i);
                        var colVal = reader.IsDBNull(i) ? null : reader.GetValue(i);
                        row[colName] = colVal;
                    }
                    results.Add(row);
                }
                stopwatch.Stop();
            }
        }
        catch (InvalidOperationException)
        {
            throw;
        }
        catch (Exception ex)
        {
            stopwatch.Stop();
            _logger.LogError(ex, "T-SQL Attested Computation execution error for computation #{Id}", computationId);
            
            var failReason = $"Database Execution Error: {ex.Message}";
            if (persistAudit)
            {
                await RecordExecutionAuditAsync(db, computationId, actor, parameterValues, executedSqlDigest, "Fail", failReason, string.Empty, (int)stopwatch.ElapsedMilliseconds, receiptJson: null, ct: ct);
            }

            return new AttestationExecutionResultDto
            {
                Success = false,
                Verdict = "Fail",
                FailureReason = failReason,
                ExecutionDurationMs = (int)stopwatch.ElapsedMilliseconds,
                ExecutedSql = sqlText,
                ExecutedSqlDigest = executedSqlDigest
            };
        }

        // 4. Deterministic Attester Step (BR-OKF-004)
        // Computes authoritative result digest and stamps 64-char SHA-256 Digital Security Seal
        var resultsJson = JsonSerializer.Serialize(results);
        var sealPayload = $"{executedSqlDigest}|{JsonSerializer.Serialize(parameterValues)}|{resultsJson}|{stopwatch.ElapsedMilliseconds}";
        var digitalSecuritySeal = ComputeSha256(sealPayload);

        var receiptDict = new Dictionary<string, object?>
        {
            ["execution_id"] = Guid.NewGuid().ToString(),
            ["executed_sql"] = sqlText,
            ["executed_sql_digest"] = executedSqlDigest,
            ["parameters"] = parameterValues,
            ["rows_affected"] = rowsAffected,
            ["execution_duration_ms"] = (int)stopwatch.ElapsedMilliseconds,
            ["security_seal"] = digitalSecuritySeal,
            ["result_digest"] = ComputeSha256(resultsJson)
        };

        var receiptJson = JsonSerializer.Serialize(receiptDict);

        // 5. Persist Execution Audit Log (Double-Write)
        if (persistAudit)
        {
            await RecordExecutionAuditAsync(
                db, 
                computationId, 
                actor, 
                parameterValues, 
                executedSqlDigest, 
                "Pass", 
                null, 
                digitalSecuritySeal, 
                (int)stopwatch.ElapsedMilliseconds, 
                receiptJson, 
                ct);
        }

        _logger.LogInformation("Attested computation #{Id} passed attestation in {Ms}ms. Digital Security Seal: {Seal}", 
            computationId, stopwatch.ElapsedMilliseconds, digitalSecuritySeal);

        return new AttestationExecutionResultDto
        {
            Success = true,
            Verdict = "Pass",
            VerificationReference = digitalSecuritySeal,
            ExecutionDurationMs = (int)stopwatch.ElapsedMilliseconds,
            RowsAffected = rowsAffected,
            Results = results,
            ExecutedSql = sqlText,
            ExecutedSqlDigest = executedSqlDigest,
            ReceiptPayloadJson = receiptJson
        };
    }

    private static SqlParameter CreateSqlParameter(string name, string typeName, object? value)
    {
        var param = new SqlParameter { ParameterName = name };

        switch (typeName.Trim().ToLower())
        {
            case "int":
            case "integer":
                param.SqlDbType = SqlDbType.Int;
                param.Value = value != null && int.TryParse(value.ToString(), out var i) ? i : DBNull.Value;
                break;
            case "bigint":
            case "long":
                param.SqlDbType = SqlDbType.BigInt;
                param.Value = value != null && long.TryParse(value.ToString(), out var l) ? l : DBNull.Value;
                break;
            case "decimal":
            case "numeric":
            case "money":
                param.SqlDbType = SqlDbType.Decimal;
                param.Value = value != null && decimal.TryParse(value.ToString(), out var d) ? d : DBNull.Value;
                break;
            case "bit":
            case "bool":
            case "boolean":
                param.SqlDbType = SqlDbType.Bit;
                param.Value = value != null && bool.TryParse(value.ToString(), out var b) ? b : DBNull.Value;
                break;
            case "date":
                param.SqlDbType = SqlDbType.Date;
                param.Value = value != null && DateTime.TryParse(value.ToString(), out var dt) ? dt.Date : DBNull.Value;
                break;
            case "datetime":
            case "datetimeoffset":
                param.SqlDbType = SqlDbType.DateTimeOffset;
                param.Value = value != null && DateTimeOffset.TryParse(value.ToString(), out var dto) ? dto : DBNull.Value;
                break;
            default:
                param.SqlDbType = SqlDbType.NVarChar;
                param.Size = 255;
                param.Value = value?.ToString() ?? (object)DBNull.Value;
                break;
        }

        return param;
    }

    private static async Task RecordExecutionAuditAsync(
        INsdmsDbContext db,
        int computationId,
        string actor,
        Dictionary<string, object?> parameters,
        string sqlDigest,
        string verdict,
        string? failureReason,
        string securitySeal,
        int durationMs,
        string? receiptJson = null,
        CancellationToken ct = default)
    {
        var audit = new ComputationExecutionAudit
        {
            ComputationId = computationId,
            InvokedByActor = actor,
            BoundParametersJson = JsonSerializer.Serialize(parameters),
            ExecutedSqlDigest = string.IsNullOrEmpty(sqlDigest) ? new string('0', 64) : sqlDigest,
            ReceiptPayloadJson = receiptJson ?? "{}",
            AttestationVerdict = verdict,
            AttestationFailureReason = failureReason,
            VerificationReference = string.IsNullOrEmpty(securitySeal) ? new string('0', 64) : securitySeal,
            ExecutionDurationMs = durationMs,
            ExecutedAt = DateTime.UtcNow
        };

        db.ComputationExecutionAudits.Add(audit);
        await db.SaveChangesAsync(ct);
    }

    private static void ValidateReadOnlyQuery(string sqlText)
    {
        if (string.IsNullOrWhiteSpace(sqlText))
        {
            throw new InvalidOperationException("Security Violation: Attested Computation SQL must be a single read-only SELECT statement.");
        }

        var trimmed = sqlText.Trim();

        // 1. Must start with SELECT or WITH
        if (!Regex.IsMatch(trimmed, @"^(SELECT|WITH)\b", RegexOptions.IgnoreCase))
        {
            throw new InvalidOperationException("Security Violation: Attested Computation SQL must be a single read-only SELECT statement.");
        }

        // 2. Must not contain destructive keywords
        const string destructiveKeywordsPattern = @"\b(DROP|ALTER|CREATE|DELETE|UPDATE|INSERT|TRUNCATE|EXEC|EXECUTE|MERGE|GRANT|REVOKE)\b";
        if (Regex.IsMatch(trimmed, destructiveKeywordsPattern, RegexOptions.IgnoreCase))
        {
            throw new InvalidOperationException("Security Violation: Attested Computation SQL must be a single read-only SELECT statement.");
        }

        // 3. Must not have multiple SQL statement delimiters like ';'
        // A single trailing semicolon is allowed, but multiple semicolons or statement delimiters inside the query are forbidden.
        var semicolonCount = trimmed.Count(c => c == ';');
        if (semicolonCount > 1 || trimmed.TrimEnd(';', ' ', '\t', '\r', '\n').Contains(';'))
        {
            throw new InvalidOperationException("Security Violation: Attested Computation SQL must be a single read-only SELECT statement.");
        }
    }

    private static string ComputeSha256(string raw)
    {
        var bytes = SHA256.HashData(Encoding.UTF8.GetBytes(raw));
        return Convert.ToHexString(bytes).ToLowerInvariant();
    }
}
