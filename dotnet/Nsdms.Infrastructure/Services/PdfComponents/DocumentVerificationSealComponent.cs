using QuestPDF.Fluent;
using QuestPDF.Helpers;
using QuestPDF.Infrastructure;

namespace Nsdms.Infrastructure.Services.PdfComponents;

/// <summary>
/// Reusable QuestPDF component rendering an official merSETA 2D Verification QR Code,
/// SHA-256 digital security seal prefix, statutory reference, and citizen/auditor guidance.
/// </summary>
public class DocumentVerificationSealComponent : IComponent
{
    private readonly byte[] _qrBytes;
    private readonly string _referenceNumber;
    private readonly string? _securitySeal;
    private readonly string? _verificationUrl;
    private readonly bool _isCompact;

    public DocumentVerificationSealComponent(
        byte[] qrBytes,
        string referenceNumber,
        string? securitySeal = null,
        string? verificationUrl = null,
        bool compact = false)
    {
        _qrBytes = qrBytes;
        _referenceNumber = referenceNumber;
        _securitySeal = securitySeal;
        _verificationUrl = verificationUrl;
        _isCompact = compact;
    }

    public void Compose(IContainer container)
    {
        var sealPrefix = !string.IsNullOrWhiteSpace(_securitySeal)
            ? (_securitySeal.Length > 24 ? $"{_securitySeal[..24]}..." : _securitySeal)
            : null;

        if (_isCompact)
        {
            // Compact horizontal layout suited for certificates & landscape credentials
            container.Border(1)
                     .BorderColor(Colors.Grey.Lighten1)
                     .Background(Colors.Grey.Lighten4)
                     .Padding(6)
                     .Row(row =>
                     {
                         row.AutoItem().Width(45).Height(45).Image(_qrBytes);

                         row.RelativeItem().PaddingLeft(8).Column(col =>
                         {
                             col.Spacing(2);
                             col.Item().Text("Digital Security Seal & Verification Reference").Bold().FontSize(8).FontColor(Colors.Blue.Darken4);
                             col.Item().Text($"Ref: {_referenceNumber}").FontSize(8).FontFamily("Consolas").Bold();
                             if (sealPrefix != null)
                             {
                                 col.Item().Text($"Seal: {sealPrefix}").FontSize(7).FontFamily("Consolas").FontColor(Colors.Grey.Darken2);
                             }
                             col.Item().Text("Scan to verify statutory authenticity on the live merSETA portal.")
                                       .FontSize(6.5f).Italic().FontColor(Colors.Grey.Darken3);
                         });
                     });
        }
        else
        {
            // Full-width structured box for outcome letters, WSP approvals & MoAs
            container.Border(1)
                     .BorderColor(Colors.Blue.Lighten3)
                     .Background(Colors.Grey.Lighten5)
                     .Padding(10)
                     .Row(row =>
                     {
                         row.AutoItem().Width(55).Height(55).Image(_qrBytes);

                         row.RelativeItem().PaddingLeft(12).Column(col =>
                         {
                             col.Spacing(2);
                             col.Item().Row(r =>
                             {
                                 r.RelativeItem().Text("Digital Security Seal & Verification Reference")
                                                 .Bold().FontSize(9).FontColor(Colors.Blue.Darken4);
                                 r.AutoItem().Text("STATUTORY VERIFIED")
                                             .Bold().FontSize(7).FontColor(Colors.Green.Darken3);
                             });

                             col.Item().Text(x =>
                             {
                                 x.Span("Document Reference: ").FontSize(8).FontColor(Colors.Grey.Darken2);
                                 x.Span(_referenceNumber).Bold().FontSize(8).FontFamily("Consolas");
                             });

                             if (sealPrefix != null)
                             {
                                 col.Item().Text(x =>
                                 {
                                     x.Span("Digital Security Seal (SHA-256): ").FontSize(7.5f).FontColor(Colors.Grey.Darken2);
                                     x.Span(sealPrefix).FontSize(7.5f).FontFamily("Consolas");
                                 });
                             }

                             col.Item().Text("Scan this 2D QR code using any smartphone camera or tablet to verify statutory authenticity, real-time approval status, and B-BBEE verification credentials on the official merSETA portal.")
                                       .FontSize(7).Italic().FontColor(Colors.Grey.Darken3);

                             if (!string.IsNullOrWhiteSpace(_verificationUrl))
                             {
                                 col.Item().Text(_verificationUrl).FontSize(6.5f).FontColor(Colors.Blue.Darken2);
                             }
                         });
                     });
        }
    }
}

public static class DocumentVerificationPdfExtensions
{
    /// <summary>
    /// Appends the official 2D Verification QR code and digital security seal box to a QuestPDF container.
    /// </summary>
    public static void DocumentVerificationSeal(
        this IContainer container,
        byte[] qrBytes,
        string referenceNumber,
        string? securitySeal = null,
        string? verificationUrl = null,
        bool compact = false)
    {
        container.Component(new DocumentVerificationSealComponent(qrBytes, referenceNumber, securitySeal, verificationUrl, compact));
    }
}
