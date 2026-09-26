namespace Nsdms.Application.Common.ExternalToolHooks;

/// <summary>
/// IT Service Management (ITSM) and Change Advisory Board (CAB) integration hook point.
/// Interfaces with Jira Service Management or ServiceNow for change tickets and deployment authorization gates.
/// </summary>
public interface IItsmChangeTicketHook
{
    bool IsConfigured { get; }
    string ProviderName { get; }
    Task<ItsmTicketStatus> VerifyChangeAuthorizationAsync(string changeTicketReference, CancellationToken cancellationToken = default);
    Task<string> CreateChangeTicketAsync(ItsmChangeTicketRequest request, CancellationToken cancellationToken = default);
    Task<ExternalToolStatus> GetStatusAsync(CancellationToken cancellationToken = default);
}
