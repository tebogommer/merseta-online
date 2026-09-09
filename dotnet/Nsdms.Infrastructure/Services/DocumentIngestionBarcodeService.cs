using System.Security.Cryptography;
using System.Text;
using System.Text.Json;
using System.Text.Json.Serialization;
using Nsdms.Application.Common.Interfaces;
using QRCoder;
using ZXing;

namespace Nsdms.Infrastructure.Services;

internal class IngestionBarcodeCompactDto
{
    [JsonPropertyName("m")]
    public string Module { get; set; } = string.Empty;

    [JsonPropertyName("t")]
    public string DocumentType { get; set; } = string.Empty;

    [JsonPropertyName("id")]
    public int RecordId { get; set; }

    [JsonPropertyName("ref")]
    public string ReferenceNumber { get; set; } = string.Empty;

    [JsonPropertyName("p")]
    public int PageNumber { get; set; } = 1;

    [JsonPropertyName("tp")]
    public int TotalPages { get; set; } = 1;

    [JsonPropertyName("c")]
    public string Checksum { get; set; } = string.Empty;
}

/// <summary>
/// Service implementing machine-readable 2D ingestion barcode stamping and decoding
/// to support automated document sorting and indexing of scanned wet-ink forms.
/// </summary>
public class DocumentIngestionBarcodeService : IDocumentIngestionBarcodeService
{
    private static readonly JsonSerializerOptions JsonOptions = new()
    {
        DefaultIgnoreCondition = JsonIgnoreCondition.WhenWritingNull,
        WriteIndented = false
    };

    public string FormatIngestionPayload(IngestionBarcodePayload payload)
    {
        var rawChecksumData = $"{payload.Module}|{payload.DocumentType}|{payload.RecordId}|{payload.ReferenceNumber}|{payload.PageNumber}";
        var hash = SHA256.HashData(Encoding.UTF8.GetBytes(rawChecksumData));
        var checksum = Convert.ToHexStringLower(hash)[..8];

        var dto = new IngestionBarcodeCompactDto
        {
            Module = payload.Module,
            DocumentType = payload.DocumentType,
            RecordId = payload.RecordId,
            ReferenceNumber = payload.ReferenceNumber,
            PageNumber = payload.PageNumber,
            TotalPages = payload.TotalPages,
            Checksum = checksum
        };

        return JsonSerializer.Serialize(dto, JsonOptions);
    }

    public IngestionBarcodePayload? ParseIngestionPayload(string rawBarcodeText)
    {
        if (string.IsNullOrWhiteSpace(rawBarcodeText))
            return null;

        try
        {
            var dto = JsonSerializer.Deserialize<IngestionBarcodeCompactDto>(rawBarcodeText, JsonOptions);
            if (dto == null) return null;

            // Validate checksum integrity against tampering
            var rawChecksumData = $"{dto.Module}|{dto.DocumentType}|{dto.RecordId}|{dto.ReferenceNumber}|{dto.PageNumber}";
            var hash = SHA256.HashData(Encoding.UTF8.GetBytes(rawChecksumData));
            var expectedChecksum = Convert.ToHexStringLower(hash)[..8];

            if (!string.Equals(dto.Checksum, expectedChecksum, StringComparison.OrdinalIgnoreCase))
            {
                return null;
            }

            return new IngestionBarcodePayload
            {
                Module = dto.Module,
                DocumentType = dto.DocumentType,
                RecordId = dto.RecordId,
                ReferenceNumber = dto.ReferenceNumber,
                PageNumber = dto.PageNumber,
                TotalPages = dto.TotalPages,
                Checksum = dto.Checksum
            };
        }
        catch
        {
            return null;
        }
    }

    public byte[] GenerateIngestionBarcodeBytes(IngestionBarcodePayload payload, int width = 120, int height = 120)
    {
        var payloadJson = FormatIngestionPayload(payload);
        using var qrGenerator = new QRCodeGenerator();
        using var qrCodeData = qrGenerator.CreateQrCode(payloadJson, QRCodeGenerator.ECCLevel.M);
        var qrCode = new PngByteQRCode(qrCodeData);
        return qrCode.GetGraphic(4);
    }

    public IngestionBarcodePayload? DecodeBarcodeFromImageBytes(byte[] imageBytes)
    {
        if (imageBytes == null || imageBytes.Length == 0)
            return null;

        try
        {
            // If bytes are UTF-8 encoded json text directly
            var directText = Encoding.UTF8.GetString(imageBytes);
            if (directText.StartsWith("{\"m\"", StringComparison.OrdinalIgnoreCase))
            {
                return ParseIngestionPayload(directText);
            }

            var reader = new BarcodeReaderGeneric
            {
                AutoRotate = true,
                Options = new ZXing.Common.DecodingOptions
                {
                    TryHarder = true,
                    PossibleFormats = new List<BarcodeFormat>
                    {
                        BarcodeFormat.QR_CODE,
                        BarcodeFormat.DATA_MATRIX
                    }
                }
            };

            // Attempt decoding with RGBLuminanceSource if raw pixel data
            if (imageBytes.Length >= 100 * 100 * 3)
            {
                var luminanceSource = new RGBLuminanceSource(imageBytes, 100, 100);
                var result = reader.Decode(luminanceSource);
                if (result != null && !string.IsNullOrWhiteSpace(result.Text))
                {
                    return ParseIngestionPayload(result.Text);
                }
            }
        }
        catch
        {
            // Fallback gracefully on unreadable images
        }

        return null;
    }
}
