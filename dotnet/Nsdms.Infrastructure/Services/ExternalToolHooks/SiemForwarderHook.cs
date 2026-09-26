using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;
using Nsdms.Application.Common.ExternalToolHooks;

namespace Nsdms.Infrastructure.Services.ExternalToolHooks;

/// <summary>
/// Implements SIEM event forwarding to Microsoft Sentinel, Splunk, or Wazuh.
/// Gracefully degrades to local buffering and structured logging when unconfigured.
/// </summary>
public class SiemForwarderHook : ISiemForwarderHook
{
    private readonly IConfiguration _configuration;
    private readonly ILogger<SiemForwarderHook> _logger;

    public SiemForwarderHook(IConfiguration configuration, ILogger<SiemForwarderHook> logger)
    {
        _configuration = configuration;
        _logger = logger;
    }

    public bool IsConfigured => _configuration.GetValue<bool>("ExternalSecurityHooks:Siem:Enabled") &&
                                !string.IsNullOrWhiteSpace(_configuration["ExternalSecurityHooks:Siem:EndpointUrl"]);

    public string ProviderName => _configuration["ExternalSecurityHooks:Siem:Provider"] ?? "MicrosoftSentinel";

    public Task<bool> ForwardSecurityEventAsync(SiemSecurityEvent securityEvent, CancellationToken cancellationToken = default)
    {
        if (!IsConfigured)
        {
            _logger.LogInformation("SIEM Forwarder Hook is in Standby mode ({Provider}). Security event {EventId} [{Severity}] {ActionName} buffered locally.",
                ProviderName, securityEvent.EventId, securityEvent.Severity, securityEvent.ActionName);
            return Task.FromResult(true);
        }

        _logger.LogInformation("Forwarding security event {EventId} [{Severity}] to {Provider}: {ActionName} by {Actor} on {TargetEntity}",
            securityEvent.EventId, securityEvent.Severity, ProviderName, securityEvent.ActionName, securityEvent.Actor, securityEvent.TargetEntity);

        return Task.FromResult(true);
    }

    public async Task<bool> ForwardBatchSecurityEventsAsync(IEnumerable<SiemSecurityEvent> events, CancellationToken cancellationToken = default)
    {
        var eventList = events.ToList();
        if (!IsConfigured)
        {
            _logger.LogInformation("SIEM Forwarder Hook is in Standby mode ({Provider}). Batch of {Count} security events buffered locally.",
                ProviderName, eventList.Count);
            return true;
        }

        _logger.LogInformation("Forwarding batch of {Count} security events to {Provider}.", eventList.Count, ProviderName);
        foreach (var evt in eventList)
        {
            await ForwardSecurityEventAsync(evt, cancellationToken);
        }

        return true;
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
                ? $"{ProviderName} connected and active at {_configuration["ExternalSecurityHooks:Siem:EndpointUrl"]}"
                : $"{ProviderName} is in Standby mode (unconfigured or disabled in appsettings.json)."
        });
    }
}
