namespace Nsdms.Application.Common.ExternalToolHooks;

/// <summary>
/// Multi-Factor Authentication (MFA) provider integration hook point.
/// Supports Entra ID, Okta, and TOTP secondary challenge verification for privileged and statutory operations.
/// </summary>
public interface IMfaProviderHook
{
    bool IsConfigured { get; }
    string ProviderName { get; }
    Task<MfaChallengeResult> InitiateChallengeAsync(string userId, string? channel = null, CancellationToken cancellationToken = default);
    Task<bool> VerifyChallengeAsync(string userId, string challengeId, string token, CancellationToken cancellationToken = default);
    Task<bool> IsUserMfaEnrolledAsync(string userId, CancellationToken cancellationToken = default);
    Task<ExternalToolStatus> GetStatusAsync(CancellationToken cancellationToken = default);
}
