namespace Nsdms.Application.Common.ExternalToolHooks;

/// <summary>
/// Secrets management vault integration hook point.
/// Interfaces with Azure Key Vault, HashiCorp Vault, or AWS Secrets Manager to safeguard encryption keys and credentials.
/// </summary>
public interface ISecretsVaultHook
{
    bool IsConfigured { get; }
    string ProviderName { get; }
    Task<string?> GetSecretAsync(string secretName, CancellationToken cancellationToken = default);
    Task<bool> SetSecretAsync(string secretName, string secretValue, CancellationToken cancellationToken = default);
    Task<ExternalToolStatus> GetStatusAsync(CancellationToken cancellationToken = default);
}
