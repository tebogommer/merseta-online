namespace Nsdms.Application.Common.Interfaces;

public record EmailTransportMessage(
    string RecipientEmail,
    string? RecipientName,
    string Subject,
    string BodyHtml,
    string? AttachmentStoragePath = null,
    string? AttachmentFileName = null,
    string? AttachmentContentType = null);

public record EmailSendResult(
    bool Success,
    bool Throttled,
    string? ErrorMessage = null);

/// <summary>
/// Transport layer abstraction for outbound email dispatch (Office 365 Exchange Online / SMTP).
/// </summary>
public interface IEmailTransportService
{
    Task<EmailSendResult> SendEmailAsync(EmailTransportMessage message, CancellationToken cancellationToken = default);
}
