using System;
using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Represents a registered external machine-to-machine (M2M) API client (e.g., SAP, Workday, VIP Payroll, SDP SIS).
/// </summary>
public class ApiClient : BaseEntity
{
    public int OrganisationId { get; set; }
    public Organisation? Organisation { get; set; }

    public string ClientIdentifier { get; set; } = string.Empty;
    public string ClientName { get; set; } = string.Empty;
    public string HashedClientSecret { get; set; } = string.Empty;
    public string AllowedScopes { get; set; } = "wsp:write,workforce:sync,claims:submit,learners:register,trade_tests:write,verify:read";
    
    /// <summary>
    /// Optional JWK JSON or public key thumbprint for Demonstrating Proof-of-Possession (RFC 9449 DPoP).
    /// </summary>
    public string? DpopPublicKeyJwk { get; set; }

    /// <summary>
    /// Optional SHA-256 certificate thumbprint for Mutual TLS (mTLS) client certificate pinning.
    /// </summary>
    public string? ClientCertificateThumbprint { get; set; }

    public string Tier { get; set; } = "Enterprise";
    public int RateLimitPerMinute { get; set; } = 120;
    public bool IsActive { get; set; } = true;
    public DateTime? LastUsedAt { get; set; }
}
