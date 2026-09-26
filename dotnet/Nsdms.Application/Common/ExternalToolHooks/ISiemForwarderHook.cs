namespace Nsdms.Application.Common.ExternalToolHooks;

/// <summary>
/// Security Information and Event Management (SIEM) integration hook point.
/// Supports forwarding operational audit trails and security alerts to Splunk, Microsoft Sentinel, or Wazuh.
/// </summary>
public interface ISiemForwarderHook
{
    bool IsConfigured { get; }
    string ProviderName { get; }
    Task<bool> ForwardSecurityEventAsync(SiemSecurityEvent securityEvent, CancellationToken cancellationToken = default);
    Task<bool> ForwardBatchSecurityEventsAsync(IEnumerable<SiemSecurityEvent> events, CancellationToken cancellationToken = default);
    Task<ExternalToolStatus> GetStatusAsync(CancellationToken cancellationToken = default);
}
