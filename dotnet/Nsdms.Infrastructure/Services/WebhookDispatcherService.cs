using System;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;

namespace Nsdms.Infrastructure.Services;

/// <summary>
/// Dispatches asynchronous event notifications to external webhooks with HMAC-SHA256 signatures and retry resilience.
/// </summary>
public class WebhookDispatcherService : IWebhookDispatcherService
{
    private readonly INsdmsDbContextFactory _dbFactory;
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly IApiSecurityService _securityService;
    private readonly ILogger<WebhookDispatcherService> _logger;

    public WebhookDispatcherService(
        INsdmsDbContextFactory dbFactory,
        IHttpClientFactory httpClientFactory,
        IApiSecurityService securityService,
        ILogger<WebhookDispatcherService> logger)
    {
        _dbFactory = dbFactory;
        _httpClientFactory = httpClientFactory;
        _securityService = securityService;
        _logger = logger;
    }

    public async Task DispatchEventAsync(string eventTopic, int organisationId, object eventData)
    {
        using var db = await _dbFactory.CreateDbContextAsync();
        var subscriptions = await db.ApiWebhookSubscriptions
            .Where(s => s.OrganisationId == organisationId && s.EventTopic == eventTopic && s.IsActive)
            .ToListAsync();

        if (subscriptions.Count == 0)
        {
            return;
        }

        var payloadWrapper = new
        {
            EventId = Guid.NewGuid().ToString("D"),
            Topic = eventTopic,
            OrganisationId = organisationId,
            TimestampUtc = DateTime.UtcNow,
            Data = eventData
        };

        var payloadJson = JsonSerializer.Serialize(payloadWrapper);
        var client = _httpClientFactory.CreateClient("NsdmsWebhookClient");
        client.Timeout = TimeSpan.FromSeconds(10);

        foreach (var sub in subscriptions)
        {
            var signature = _securityService.ComputeWebhookHmacSha256(payloadJson, sub.SecretKey);
            var deliveryLog = new ApiWebhookDeliveryLog
            {
                SubscriptionId = sub.Id,
                EventTopic = eventTopic,
                PayloadJson = payloadJson,
                AttemptNumber = 1,
                DeliveredAt = DateTime.UtcNow,
                CreatedBy = "WEBHOOK_DISPATCHER"
            };

            try
            {
                using var request = new HttpRequestMessage(HttpMethod.Post, sub.TargetUrl)
                {
                    Content = new StringContent(payloadJson, Encoding.UTF8, MediaTypeHeaderValue.Parse("application/json"))
                };
                request.Headers.Add("X-Hub-Signature-256", signature);
                request.Headers.Add("X-MerSETA-Event", eventTopic);
                request.Headers.Add("X-MerSETA-Delivery", deliveryLog.Id.ToString());

                var response = await client.SendAsync(request);
                deliveryLog.HttpStatusCode = (int)response.StatusCode;
                deliveryLog.IsSuccess = response.IsSuccessStatusCode;

                if (!response.IsSuccessStatusCode)
                {
                    deliveryLog.ErrorMessage = $"HTTP {(int)response.StatusCode}: {response.ReasonPhrase}";
                    sub.FailureCount++;
                }
                else
                {
                    sub.FailureCount = 0;
                    sub.LastTriggeredAt = DateTime.UtcNow;
                }
            }
            catch (Exception ex)
            {
                _logger.LogWarning(ex, "Webhook dispatch failed for subscription {Id} to {Url}", sub.Id, sub.TargetUrl);
                deliveryLog.IsSuccess = false;
                deliveryLog.ErrorMessage = ex.Message;
                sub.FailureCount++;
            }

            db.ApiWebhookDeliveryLogs.Add(deliveryLog);
        }

        await db.SaveChangesAsync();
    }

    public async Task<int> ProcessPendingRetriesAsync()
    {
        using var db = await _dbFactory.CreateDbContextAsync();
        var cutoff = DateTime.UtcNow.AddHours(-24);

        var pendingLogs = await db.ApiWebhookDeliveryLogs
            .Include(l => l.Subscription)
            .Where(l => !l.IsSuccess && l.AttemptNumber < 3 && l.DeliveredAt > cutoff && l.Subscription != null && l.Subscription.IsActive)
            .OrderBy(l => l.DeliveredAt)
            .Take(50)
            .ToListAsync();

        if (pendingLogs.Count == 0)
        {
            return 0;
        }

        var client = _httpClientFactory.CreateClient("NsdmsWebhookClient");
        client.Timeout = TimeSpan.FromSeconds(10);
        int successCount = 0;

        foreach (var log in pendingLogs)
        {
            if (log.Subscription == null) continue;

            log.AttemptNumber++;
            var signature = _securityService.ComputeWebhookHmacSha256(log.PayloadJson, log.Subscription.SecretKey);

            try
            {
                using var request = new HttpRequestMessage(HttpMethod.Post, log.Subscription.TargetUrl)
                {
                    Content = new StringContent(log.PayloadJson, Encoding.UTF8, MediaTypeHeaderValue.Parse("application/json"))
                };
                request.Headers.Add("X-Hub-Signature-256", signature);
                request.Headers.Add("X-MerSETA-Event", log.EventTopic);

                var response = await client.SendAsync(request);
                log.HttpStatusCode = (int)response.StatusCode;
                log.IsSuccess = response.IsSuccessStatusCode;
                log.DeliveredAt = DateTime.UtcNow;

                if (response.IsSuccessStatusCode)
                {
                    log.ErrorMessage = null;
                    log.Subscription.FailureCount = 0;
                    successCount++;
                }
                else
                {
                    log.ErrorMessage = $"Retry {log.AttemptNumber} failed: HTTP {(int)response.StatusCode}";
                }
            }
            catch (Exception ex)
            {
                log.ErrorMessage = $"Retry {log.AttemptNumber} error: {ex.Message}";
                log.DeliveredAt = DateTime.UtcNow;
            }
        }

        await db.SaveChangesAsync();
        return successCount;
    }
}
