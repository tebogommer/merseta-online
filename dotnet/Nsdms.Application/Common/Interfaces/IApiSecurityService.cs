using System;
using System.Threading.Tasks;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Common.Interfaces;

/// <summary>
/// Security contract for B2B Machine-to-Machine API authentication, DPoP (RFC 9449) proof-of-possession,
/// mTLS client certificate verification, webhook HMAC-SHA256 signing, and idempotency tracking.
/// </summary>
public interface IApiSecurityService
{
    /// <summary>
    /// Authenticates an incoming M2M client request validating ClientIdentifier, Secret, optional DPoP proof, and mTLS thumbprint.
    /// </summary>
    Task<(bool Success, ApiClient? Client, string? Error)> AuthenticateClientAsync(
        string clientIdentifier, 
        string clientSecret, 
        string? dpopHeader = null, 
        string? clientCertThumbprint = null);

    /// <summary>
    /// Validates whether the authenticated client possesses the required statutory scope.
    /// </summary>
    bool HasScope(ApiClient client, string requiredScope);

    /// <summary>
    /// Provisions a new M2M client credential pair returning the plaintext secret (shown once) and registering the client.
    /// </summary>
    Task<(ApiClient Client, string PlaintextSecret)> RegisterClientAsync(
        int organisationId, 
        string clientName, 
        string[] scopes, 
        string? dpopKeyJwk = null, 
        string? certThumbprint = null,
        string tier = "Enterprise",
        int rateLimitPerMinute = 120);

    /// <summary>
    /// Computes HMAC-SHA256 signature for outgoing webhook event payloads.
    /// </summary>
    string ComputeWebhookHmacSha256(string payloadJson, string secretKey);

    /// <summary>
    /// Verifies incoming webhook HMAC-SHA256 signature against expected payload.
    /// </summary>
    bool VerifyWebhookHmacSha256(string payloadJson, string secretKey, string receivedSignature);

    /// <summary>
    /// Checks whether an idempotent request has already been executed.
    /// </summary>
    Task<(bool IsDuplicate, string? CachedResponseJson, int StatusCode)> CheckIdempotencyAsync(
        string idempotencyKey, 
        string clientIdentifier, 
        string requestPath);

    /// <summary>
    /// Persists execution results of an idempotent operation.
    /// </summary>
    Task RecordIdempotencyAsync(
        string idempotencyKey, 
        string clientIdentifier, 
        string requestPath, 
        int statusCode, 
        string responseJson, 
        TimeSpan? ttl = null);
}
