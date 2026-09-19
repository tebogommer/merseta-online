using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging.Abstractions;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

/// <summary>
/// Comprehensive verification test suite for B2B API Architecture, DPoP (RFC 9449),
/// mTLS certificate pinning, webhook HMAC-SHA256 signing, and idempotency guarantees.
/// </summary>
public class B2bApiArchitectureTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, ApiSecurityService service, Organisation org) CreateTestEnvironment()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var logger = NullLogger<ApiSecurityService>.Instance;
        var service = new ApiSecurityService(factory, audit, logger);

        var org = new Organisation
        {
            CompanyName = "Toyota South Africa Motors",
            TradingName = "Toyota SA",
            SdlNumber = "L700100200",
            SicCode = "38100",
            CreatedAt = DateTime.UtcNow
        };
        db.Organisations.Add(org);
        db.SaveChanges();

        return (factory, db, service, org);
    }

    [Fact]
    public async Task RegisterClientAsync_ProvisionsClientWithHashedSecretAndAuditTrail()
    {
        // Arrange
        var (_, db, service, org) = CreateTestEnvironment();
        var scopes = new[] { "wsp:write", "workforce:sync", "claims:submit" };

        // Act
        var (client, plaintextSecret) = await service.RegisterClientAsync(
            org.Id,
            "SAP SuccessFactors HR Connector",
            scopes,
            tier: "Enterprise",
            rateLimitPerMinute: 1200);

        // Assert
        Assert.NotNull(client);
        Assert.StartsWith("M2M_L700100200_", client.ClientIdentifier);
        Assert.StartsWith("mseta_live_", plaintextSecret);
        Assert.NotEqual(plaintextSecret, client.HashedClientSecret);
        Assert.True(client.IsActive);
        Assert.Equal("Enterprise", client.Tier);
        Assert.Equal(1200, client.RateLimitPerMinute);
        Assert.Contains("wsp:write", client.AllowedScopes);

        // Verify database persistence
        var savedClient = await db.ApiClients.FirstOrDefaultAsync(c => c.Id == client.Id);
        Assert.NotNull(savedClient);

        // Verify audit double-write
        var auditLogs = await db.AuditLogs.Where(a => a.EntityName == "ApiClient" && a.RecordId == client.Id).ToListAsync();
        Assert.NotEmpty(auditLogs);
    }

    [Fact]
    public async Task AuthenticateClientAsync_WithValidSecret_SucceedsAndUpdatesLastUsed()
    {
        // Arrange
        var (_, _, service, org) = CreateTestEnvironment();
        var scopes = new[] { "wsp:write" };
        var (client, plaintextSecret) = await service.RegisterClientAsync(org.Id, "VIP Payroll", scopes);

        // Act
        var (success, authClient, error) = await service.AuthenticateClientAsync(client.ClientIdentifier, plaintextSecret);

        // Assert
        Assert.True(success);
        Assert.Null(error);
        Assert.NotNull(authClient);
        Assert.Equal(client.ClientIdentifier, authClient.ClientIdentifier);
        Assert.NotNull(authClient.LastUsedAt);
    }

    [Fact]
    public async Task AuthenticateClientAsync_WithTamperedSecret_FailsSecurely()
    {
        // Arrange
        var (_, _, service, org) = CreateTestEnvironment();
        var (client, _) = await service.RegisterClientAsync(org.Id, "Workday Connector", new[] { "wsp:write" });

        // Act
        var (success, authClient, error) = await service.AuthenticateClientAsync(client.ClientIdentifier, "mseta_live_fraudulent_secret");

        // Assert
        Assert.False(success);
        Assert.Null(authClient);
        Assert.Equal("Invalid client credentials.", error);
    }

    [Fact]
    public async Task AuthenticateClientAsync_WhenDeactivated_RejectsAccess()
    {
        // Arrange
        var (_, db, service, org) = CreateTestEnvironment();
        var (client, plaintextSecret) = await service.RegisterClientAsync(org.Id, "Legacy Connector", new[] { "wsp:write" });

        // Deactivate client
        var dbClient = await db.ApiClients.FirstAsync(c => c.Id == client.Id);
        dbClient.IsActive = false;
        await db.SaveChangesAsync();

        // Act
        var (success, authClient, error) = await service.AuthenticateClientAsync(client.ClientIdentifier, plaintextSecret);

        // Assert
        Assert.False(success);
        Assert.Null(authClient);
        Assert.Contains("deactivated", error, StringComparison.OrdinalIgnoreCase);
    }

    [Fact]
    public async Task AuthenticateClientAsync_WithMtlsPinning_ValidatesThumbprint()
    {
        // Arrange
        var (_, _, service, org) = CreateTestEnvironment();
        const string expectedThumbprint = "E3B0C44298FC1C149AFBF4C8996FB92427AE41E4649B934CA495991B7852B855";

        var (client, plaintextSecret) = await service.RegisterClientAsync(
            org.Id, 
            "Secure Banking Connector", 
            new[] { "claims:submit" }, 
            certThumbprint: expectedThumbprint);

        // Act 1: Correct thumbprint
        var (successPass, _, _) = await service.AuthenticateClientAsync(
            client.ClientIdentifier, 
            plaintextSecret, 
            clientCertThumbprint: expectedThumbprint);

        // Act 2: Mismatched thumbprint
        var (successFail, _, errorFail) = await service.AuthenticateClientAsync(
            client.ClientIdentifier, 
            plaintextSecret, 
            clientCertThumbprint: "BAD_THUMBPRINT_00000000000000000000000000000000000000000000000000");

        // Assert
        Assert.True(successPass);
        Assert.False(successFail);
        Assert.Contains("Mutual TLS", errorFail);
    }

    [Fact]
    public async Task AuthenticateClientAsync_WithDpopProof_RequiresDpopHeader()
    {
        // Arrange
        var (_, _, service, org) = CreateTestEnvironment();
        const string jwkKey = "{\"kty\":\"EC\",\"crv\":\"P-256\",\"x\":\"f83OJ3D2xF1Bg8vub9tLe1gHMzV76e8Tus9uPHvRVEU\",\"y\":\"x_daQjjUz3WQKpHUnIoPpPpqHpQcgDTgA956_hS3bGQ\"}";

        var (client, plaintextSecret) = await service.RegisterClientAsync(
            org.Id, 
            "DPoP Client", 
            new[] { "wsp:write" }, 
            dpopKeyJwk: jwkKey);

        // Act 1: Without DPoP header
        var (successNoHeader, _, errorNoHeader) = await service.AuthenticateClientAsync(
            client.ClientIdentifier, 
            plaintextSecret, 
            dpopHeader: null);

        // Act 2: With valid DPoP header
        var (successValid, _, _) = await service.AuthenticateClientAsync(
            client.ClientIdentifier, 
            plaintextSecret, 
            dpopHeader: "eyJhbGciOiJFUzI1NiIsInR5cCI6ImRwb3Arand0In0.eyJqdGkiOiItQjEtU...");

        // Assert
        Assert.False(successNoHeader);
        Assert.Contains("RFC 9449 DPoP", errorNoHeader);
        Assert.True(successValid);
    }

    [Fact]
    public void HasScope_EnforcesGranularAndWildcardAccess()
    {
        // Arrange
        var (_, _, service, _) = CreateTestEnvironment();
        var clientScoped = new ApiClient { AllowedScopes = "wsp:write,workforce:sync" };
        var clientAdmin = new ApiClient { AllowedScopes = "*" };

        // Assert
        Assert.True(service.HasScope(clientScoped, "wsp:write"));
        Assert.True(service.HasScope(clientScoped, "workforce:sync"));
        Assert.False(service.HasScope(clientScoped, "claims:submit"));

        Assert.True(service.HasScope(clientAdmin, "claims:submit"));
        Assert.True(service.HasScope(clientAdmin, "any:scope"));
    }

    [Fact]
    public void WebhookHmacSha256_ComputesSignatureAndDetectsTampering()
    {
        // Arrange
        var (_, _, service, _) = CreateTestEnvironment();
        const string secretKey = "super_secret_webhook_key_2026";
        const string payload = "{\"EventId\":\"evt-001\",\"Topic\":\"wsp.status.changed\",\"Status\":\"Approved\"}";
        const string tamperedPayload = "{\"EventId\":\"evt-001\",\"Topic\":\"wsp.status.changed\",\"Status\":\"Rejected\"}";

        // Act
        var signature = service.ComputeWebhookHmacSha256(payload, secretKey);

        // Assert
        Assert.StartsWith("sha256=", signature);
        Assert.True(service.VerifyWebhookHmacSha256(payload, secretKey, signature));
        Assert.False(service.VerifyWebhookHmacSha256(tamperedPayload, secretKey, signature));
        Assert.False(service.VerifyWebhookHmacSha256(payload, "wrong_secret_key", signature));
    }

    [Fact]
    public async Task Idempotency_PreventsDuplicateExecutionAndReplaysCachedResponse()
    {
        // Arrange
        var (_, _, service, _) = CreateTestEnvironment();
        const string idempotencyKey = "IDEMP-UUID-9999-AAAA-BBBB";
        const string client = "M2M_TEST_CLIENT";
        const string path = "/api/v1/b2b/grants/moa/MOA-2026-001/claims";
        const string responseBody = "{\"ClaimId\":42,\"Status\":\"Submitted\"}";

        // Act 1: Initial check
        var (isInitialDup, _, _) = await service.CheckIdempotencyAsync(idempotencyKey, client, path);
        Assert.False(isInitialDup);

        // Act 2: Record response
        await service.RecordIdempotencyAsync(idempotencyKey, client, path, 201, responseBody);

        // Act 3: Subsequent duplicate check
        var (isDup, cachedJson, statusCode) = await service.CheckIdempotencyAsync(idempotencyKey, client, path);

        // Assert
        Assert.True(isDup);
        Assert.Equal(201, statusCode);
        Assert.Equal(responseBody, cachedJson);
    }
}
