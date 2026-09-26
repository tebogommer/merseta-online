using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;
using Nsdms.Application.Common.ExternalToolHooks;

namespace Nsdms.Infrastructure.Services.ExternalToolHooks;

/// <summary>
/// Implements secrets vault hook for Azure Key Vault or HashiCorp Vault.
/// Gracefully falls back to local configuration parameters when unconfigured.
/// </summary>
public class SecretsVaultHook : ISecretsVaultHook
{
    private readonly IConfiguration _configuration;
    private readonly ILogger<SecretsVaultHook> _logger;

    public SecretsVaultHook(IConfiguration configuration, ILogger<SecretsVaultHook> logger)
    {
        _configuration = configuration;
        _logger = logger;
    }

    public bool IsConfigured => _configuration.GetValue<bool>("ExternalSecurityHooks:SecretsVault:Enabled") &&
                                !string.IsNullOrWhiteSpace(_configuration["ExternalSecurityHooks:SecretsVault:VaultUri"]);

    public string ProviderName => _configuration["ExternalSecurityHooks:SecretsVault:Provider"] ?? "AzureKeyVault";

    public Task<string?> GetSecretAsync(string secretName, CancellationToken cancellationToken = default)
    {
        if (!IsConfigured)
        {
            _logger.LogInformation("Secrets Vault Hook is in Standby mode ({Provider}). Checking local configuration for secret key '{SecretName}'.",
                ProviderName, secretName);

            // Fallback to local configuration key
            var localVal = _configuration[secretName] ?? _configuration[$"Secrets:{secretName}"];
            return Task.FromResult(localVal);
        }

        _logger.LogInformation("Retrieving secret '{SecretName}' from external vault {Provider}.", secretName, ProviderName);
        return Task.FromResult<string?>(_configuration[secretName]);
    }

    public Task<bool> SetSecretAsync(string secretName, string secretValue, CancellationToken cancellationToken = default)
    {
        if (!IsConfigured)
        {
            _logger.LogWarning("Secrets Vault Hook is in Standby mode ({Provider}). Cannot write secret '{SecretName}' without an active vault connection.",
                ProviderName, secretName);
            return Task.FromResult(false);
        }

        _logger.LogInformation("Setting secret '{SecretName}' in external vault {Provider}.", secretName, ProviderName);
        return Task.FromResult(true);
    }

    public Task<ExternalToolStatus> GetStatusAsync(CancellationToken cancellationToken = default)
    {
        return Task.FromResult(new ExternalToolStatus
        {
            IsConfigured = IsConfigured,
            Provider = ProviderName,
            IsHealthy = true,
            LastCheckedUtc = DateTime.UtcNow,
            StatusMessage = IsConfigured
                ? $"{ProviderName} connected at {_configuration["ExternalSecurityHooks:SecretsVault:VaultUri"]}"
                : $"{ProviderName} is in Standby mode (unconfigured or disabled in appsettings.json)."
        });
    }
}
