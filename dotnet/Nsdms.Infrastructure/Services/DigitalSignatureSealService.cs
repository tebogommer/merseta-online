using System;
using System.Security.Cryptography;
using System.Text;
using Microsoft.Extensions.Logging;
using Nsdms.Application.Common.Interfaces;

namespace Nsdms.Infrastructure.Services;

/// <summary>
/// Cryptographic digital signature sealing engine providing non-repudiable audit verification
/// for statutory grant disbursements, MoA approvals, and banking activations.
/// </summary>
public class DigitalSignatureSealService : IDigitalSignatureSealService
{
    private readonly ILogger<DigitalSignatureSealService> _logger;
    private readonly Microsoft.Extensions.Configuration.IConfiguration? _configuration;
    private const string DefaultKeyVersion = "v1";
    private static readonly byte[] DefaultHmacKey = Encoding.UTF8.GetBytes("MerSETA-NSDMS-Enterprise-Cryptographic-Approval-Key-2026");

    public DigitalSignatureSealService(
        ILogger<DigitalSignatureSealService> logger,
        Microsoft.Extensions.Configuration.IConfiguration? configuration = null)
    {
        _logger = logger;
        _configuration = configuration;
    }

    private (string Version, byte[] Key) GetSigningKey(string? requestedVersion = null)
    {
        string version = requestedVersion 
            ?? _configuration?["Cryptography:ActiveKeyVersion"] 
            ?? DefaultKeyVersion;

        string? configKey = _configuration?[$"Cryptography:Keys:{version}"]
            ?? _configuration?["Cryptography:ApprovalSecretKey"];

        byte[] key = !string.IsNullOrWhiteSpace(configKey)
            ? Encoding.UTF8.GetBytes(configKey)
            : DefaultHmacKey;

        return (version, key);
    }

    public CryptographicApprovalSeal GenerateApprovalSeal(
        string entityName,
        long recordId,
        string approverUserId,
        string approverRole,
        decimal approvedAmount,
        string payloadSummary)
    {
        var sealedAt = DateTime.UtcNow;
        var canonicalData = BuildCanonicalData(entityName, recordId, approverUserId, approverRole, approvedAmount, payloadSummary, sealedAt);
        var (version, key) = GetSigningKey();

        using var hmac = new HMACSHA256(key);
        var hashBytes = hmac.ComputeHash(Encoding.UTF8.GetBytes(canonicalData));
        var fullHex = Convert.ToHexString(hashBytes).ToLowerInvariant();
        var prefix = fullHex[..16];

        _logger.LogInformation("Generated cryptographic approval seal [{Version}] for {Entity} #{Id} by '{User}' [{Role}] - Seal: {SealPrefix}",
            version, entityName, recordId, approverUserId, approverRole, prefix);

        return new CryptographicApprovalSeal(
            entityName,
            recordId,
            approverUserId,
            approverRole,
            approvedAmount,
            sealedAt,
            prefix,
            fullHex,
            true,
            version
        );
    }

    public bool VerifyApprovalSeal(
        CryptographicApprovalSeal seal,
        string payloadSummary)
    {
        var canonicalData = BuildCanonicalData(
            seal.EntityName,
            seal.RecordId,
            seal.ApproverUserId,
            seal.ApproverRole,
            seal.ApprovedAmount,
            payloadSummary,
            seal.SealedAtUtc);

        var (_, key) = GetSigningKey(seal.KeyVersion);

        using var hmac = new HMACSHA256(key);
        var expectedHashBytes = hmac.ComputeHash(Encoding.UTF8.GetBytes(canonicalData));
        var expectedHex = Convert.ToHexString(expectedHashBytes).ToLowerInvariant();

        bool isValid = string.Equals(expectedHex, seal.DigitalSecuritySealSha256, StringComparison.OrdinalIgnoreCase);

        if (!isValid)
        {
            _logger.LogWarning("Cryptographic seal verification FAILED [{Version}] for {Entity} #{Id}. Tampering detected!",
                seal.KeyVersion, seal.EntityName, seal.RecordId);
        }

        return isValid;
    }

    private static string BuildCanonicalData(
        string entityName,
        long recordId,
        string approverUserId,
        string approverRole,
        decimal approvedAmount,
        string payloadSummary,
        DateTime sealedAtUtc)
    {
        return $"{entityName.Trim().ToUpperInvariant()}:{recordId}:{approverUserId.Trim()}:{approverRole.Trim()}:{approvedAmount:F2}:{payloadSummary.Trim()}:{sealedAtUtc:O}";
    }
}
