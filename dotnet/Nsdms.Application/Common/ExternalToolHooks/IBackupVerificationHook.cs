namespace Nsdms.Application.Common.ExternalToolHooks;

/// <summary>
/// Automated backup verification hook point for ISO 27001 Annex A.12.3 and BCDR compliance.
/// Integrates with Azure Backup or Veeam to verify snapshot completion, retention, and restore readiness.
/// </summary>
public interface IBackupVerificationHook
{
    bool IsConfigured { get; }
    string ProviderName { get; }
    Task<BackupVerificationStatus> VerifyLatestBackupAsync(CancellationToken cancellationToken = default);
    Task<ExternalToolStatus> GetStatusAsync(CancellationToken cancellationToken = default);
}
