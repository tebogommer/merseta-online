using System.Text.RegularExpressions;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using Nsdms.Application.Common;
using Nsdms.Application.Services;

namespace Nsdms.Infrastructure.Services;

/// <summary>
/// Implementation of INonLevyNumberGeneratorService providing high-speed, collision-proof,
/// atomic N-number allocation for non-levy organisations, TVETs, NGOs, and exempt employers.
/// </summary>
public class NonLevyNumberGeneratorService : INonLevyNumberGeneratorService
{
    private readonly INsdmsDbContextFactory _factory;
    private readonly IAuditService _audit;
    private readonly ILogger<NonLevyNumberGeneratorService> _logger;

    // Static in-memory counter fallback for unit testing environments (InMemory / SQLite)
    private static long _inMemoryFallbackSequence = 100000;

    private static readonly Regex NNumberRegex = new(@"^N\d{9}$", RegexOptions.Compiled | RegexOptions.IgnoreCase);

    public NonLevyNumberGeneratorService(
        INsdmsDbContextFactory factory,
        IAuditService audit,
        ILogger<NonLevyNumberGeneratorService> logger)
    {
        _factory = factory;
        _audit = audit;
        _logger = logger;
    }

    public async Task<string> GenerateNextNonLevyNumberAsync(CancellationToken cancellationToken = default)
    {
        using var db = await _factory.CreateDbContextAsync(cancellationToken);
        int attempts = 0;
        const int maxAttempts = 50;

        while (attempts < maxAttempts && !cancellationToken.IsCancellationRequested)
        {
            attempts++;
            long sequenceVal;

            if (db.Database.IsSqlServer())
            {
                try
                {
                    sequenceVal = await db.Database
                        .SqlQueryRaw<int>("SELECT NEXT VALUE FOR [dbo].[seq_NonLevyOrganisationNumber]")
                        .SingleAsync(cancellationToken);
                }
                catch (Exception ex)
                {
                    _logger.LogWarning(ex, "Failed to draw from [seq_NonLevyOrganisationNumber]. Falling back to high-water mark probe.");
                    sequenceVal = await ResolveFallbackHighWaterMarkAsync(db, cancellationToken);
                }
            }
            else
            {
                sequenceVal = await ResolveFallbackHighWaterMarkAsync(db, cancellationToken);
            }

            // Fixed statutory format: 'N' followed by 9 digits (exactly 10 characters total)
            string candidate = $"N{sequenceVal:D9}";

            // Tier 3: Verify non-existence in database (self-healing skip-ahead guard against legacy outliers)
            bool exists = await db.Organisations.AnyAsync(o => o.SdlNumber == candidate, cancellationToken);

            if (!exists)
            {
                _logger.LogInformation("Allocated unique statutory N-Number: {Candidate} after {Attempts} attempt(s).", candidate, attempts);
                return candidate;
            }

            _logger.LogWarning("Statutory N-Number candidate {Candidate} already exists in Organisation records (legacy outlier). Drawing next sequence value.", candidate);
        }

        throw new InvalidOperationException($"Exhausted {maxAttempts} attempts without securing a unique non-levy organisation number.");
    }

    private static async Task<long> ResolveFallbackHighWaterMarkAsync(INsdmsDbContext db, CancellationToken ct)
    {
        // Safe fallback for test environments or non-SQL Server databases
        var existingNumbers = await db.Organisations
            .Where(o => o.SdlNumber.StartsWith("N") || o.SdlNumber.StartsWith("n"))
            .Select(o => o.SdlNumber)
            .ToListAsync(ct);

        long maxVal = 100000;
        foreach (var num in existingNumbers)
        {
            if (num.Length > 1 && long.TryParse(num[1..], out var parsed) && parsed > maxVal)
            {
                maxVal = parsed;
            }
        }

        var currentInMemory = Interlocked.Read(ref _inMemoryFallbackSequence);
        if (maxVal >= currentInMemory)
        {
            Interlocked.Exchange(ref _inMemoryFallbackSequence, maxVal + 1);
            return maxVal + 1;
        }

        return Interlocked.Increment(ref _inMemoryFallbackSequence);
    }

    public bool IsNonLevyNumber(string? sdlNumber)
    {
        if (string.IsNullOrWhiteSpace(sdlNumber)) return false;
        var trimmed = sdlNumber.Trim();
        return trimmed.StartsWith("N", StringComparison.OrdinalIgnoreCase)
               || trimmed.StartsWith("NON", StringComparison.OrdinalIgnoreCase);
    }

    public bool IsValidNNumberFormat(string? nNumber)
    {
        if (string.IsNullOrWhiteSpace(nNumber)) return false;
        return NNumberRegex.IsMatch(nNumber.Trim());
    }
}
