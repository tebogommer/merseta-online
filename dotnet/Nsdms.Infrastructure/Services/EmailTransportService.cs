using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;
using Nsdms.Application.Common.Interfaces;
using System.Net;
using System.Net.Mail;

namespace Nsdms.Infrastructure.Services;

/// <summary>
/// SMTP and Office 365 Exchange Online email transport implementation.
/// Handles physical file attachments and detects SMTP rate-limiting/throttling error responses.
/// </summary>
public class EmailTransportService : IEmailTransportService
{
    private readonly IConfiguration _config;
    private readonly ILogger<EmailTransportService> _logger;
    private readonly bool _simulationMode;
    private readonly string _smtpHost;
    private readonly int _smtpPort;
    private readonly string _smtpUser;
    private readonly string _smtpPass;
    private readonly string _fromAddress;
    private readonly string _fromDisplayName;
    private readonly bool _enableSsl;

    public EmailTransportService(IConfiguration config, ILogger<EmailTransportService> logger)
    {
        _config = config;
        _logger = logger;

        _smtpHost = _config["Email:SmtpHost"] ?? "smtp.office365.com";
        _smtpPort = int.TryParse(_config["Email:SmtpPort"], out var port) ? port : 587;
        _smtpUser = _config["Email:Username"] ?? "noreply@merseta.org.za";
        _smtpPass = _config["Email:Password"] ?? string.Empty;
        _fromAddress = _config["Email:FromAddress"] ?? "noreply@merseta.org.za";
        _fromDisplayName = _config["Email:FromDisplayName"] ?? "merSETA Communications";
        _enableSsl = bool.TryParse(_config["Email:EnableSsl"], out var ssl) ? ssl : true;

        // Enabled if explicitly configured or running in development without credentials
        _simulationMode = bool.TryParse(_config["Email:SimulationMode"], out var sim) ? sim : string.IsNullOrWhiteSpace(_smtpPass);
    }

    public async Task<EmailSendResult> SendEmailAsync(EmailTransportMessage message, CancellationToken cancellationToken = default)
    {
        if (_simulationMode)
        {
            _logger.LogInformation("[SIMULATION] Outbound email dispatched to {Recipient}: {Subject} (HasAttachment={HasAttachment})",
                message.RecipientEmail, message.Subject, !string.IsNullOrWhiteSpace(message.AttachmentStoragePath));
            await Task.Delay(20, cancellationToken); // Simulate network latency
            return new EmailSendResult(true, false);
        }

        try
        {
            using var mail = new MailMessage();
            mail.From = new MailAddress(_fromAddress, _fromDisplayName);
            mail.To.Add(new MailAddress(message.RecipientEmail, message.RecipientName ?? message.RecipientEmail));
            mail.Subject = message.Subject;
            mail.Body = message.BodyHtml;
            mail.IsBodyHtml = true;

            // Attach physical binary document if present
            Attachment? attachment = null;
            if (!string.IsNullOrWhiteSpace(message.AttachmentStoragePath) && File.Exists(message.AttachmentStoragePath))
            {
                attachment = new Attachment(message.AttachmentStoragePath);
                if (!string.IsNullOrWhiteSpace(message.AttachmentFileName))
                {
                    attachment.Name = message.AttachmentFileName;
                }
                mail.Attachments.Add(attachment);
            }

            using var client = new SmtpClient(_smtpHost, _smtpPort)
            {
                EnableSsl = _enableSsl,
                DeliveryMethod = SmtpDeliveryMethod.Network,
                UseDefaultCredentials = false,
                Credentials = new NetworkCredential(_smtpUser, _smtpPass),
                Timeout = 15000
            };

            await client.SendMailAsync(mail, cancellationToken);

            attachment?.Dispose();
            _logger.LogInformation("Email successfully dispatched to {Recipient} via Office 365 SMTP.", message.RecipientEmail);
            return new EmailSendResult(true, false);
        }
        catch (SmtpFailedRecipientException ex)
        {
            _logger.LogWarning(ex, "SMTP failed recipient for {Recipient}: {Message}", message.RecipientEmail, ex.Message);
            return new EmailSendResult(false, false, $"Failed recipient: {ex.Message}");
        }
        catch (SmtpException ex)
        {
            var isThrottled = IsThrottlingError(ex);
            _logger.LogWarning(ex, "SMTP error sending to {Recipient} (Throttled={IsThrottled}): {Message}",
                message.RecipientEmail, isThrottled, ex.Message);
            return new EmailSendResult(false, isThrottled, ex.Message);
        }
        catch (Exception ex)
        {
            _logger.LogError(ex, "Unexpected error sending email to {Recipient}: {Message}", message.RecipientEmail, ex.Message);
            return new EmailSendResult(false, false, ex.Message);
        }
    }

    private static bool IsThrottlingError(SmtpException ex)
    {
        var msg = ex.Message.ToLowerInvariant();
        var statusCode = (int)ex.StatusCode;

        // Standard SMTP throttling/rate limiting indicators (421, 451, 4.4.2, 4.7.500)
        return statusCode == 421 || statusCode == 451 ||
               msg.Contains("throttl") ||
               msg.Contains("rate limit") ||
               msg.Contains("too many") ||
               msg.Contains("exceeded") ||
               msg.Contains("quota") ||
               msg.Contains("busy");
    }
}
