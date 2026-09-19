using System.Threading.Tasks;

namespace Nsdms.Application.Common.Interfaces;

/// <summary>
/// Service contract for asynchronous event notification dispatching to external organisation webhooks.
/// </summary>
public interface IWebhookDispatcherService
{
    /// <summary>
    /// Dispatches an event payload to all active registered webhook subscriptions for the target organisation and topic.
    /// </summary>
    Task DispatchEventAsync(string eventTopic, int organisationId, object eventData);

    /// <summary>
    /// Background retry pump to re-attempt failed webhook deliveries with exponential backoff.
    /// </summary>
    Task<int> ProcessPendingRetriesAsync();
}
