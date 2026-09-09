namespace Nsdms.Application.Common.Interfaces;

/// <summary>
/// Structured routing and indexing metadata stamped onto printable documents
/// to facilitate automatic optical classification and sorting when signed pages are re-uploaded.
/// </summary>
public class IngestionBarcodePayload
{
    /// <summary>
    /// Statutory domain module: "WSP", "DG_MOA", "LEARNER", "ETQA".
    /// </summary>
    public string Module { get; set; } = string.Empty;

    /// <summary>
    /// Document typology code: "WSP_SIGNOFF", "DG_MOA_CONTRACT", "LEARNER_AGREEMENT".
    /// </summary>
    public string DocumentType { get; set; } = string.Empty;

    /// <summary>
    /// Primary database record ID (e.g. WspSubmission.Id or GrantMoa.Id).
    /// </summary>
    public int RecordId { get; set; }

    /// <summary>
    /// Human-readable statutory reference (e.g. WSP-2026-0042, MOA-2026-001).
    /// </summary>
    public string ReferenceNumber { get; set; } = string.Empty;

    /// <summary>
    /// Page sequence number (1-based).
    /// </summary>
    public int PageNumber { get; set; } = 1;

    /// <summary>
    /// Total pages in the printable instrument.
    /// </summary>
    public int TotalPages { get; set; } = 1;

    /// <summary>
    /// Cryptographic checksum to prevent tampering or spoofed scan injection.
    /// </summary>
    public string Checksum { get; set; } = string.Empty;
}

/// <summary>
/// Engine for generating machine-readable ingestion barcodes on printable forms
/// and decoding them from uploaded scans to automatically index wet-ink signed documents.
/// </summary>
public interface IDocumentIngestionBarcodeService
{
    /// <summary>
    /// Formats structured routing metadata into a compact JSON token.
    /// </summary>
    string FormatIngestionPayload(IngestionBarcodePayload payload);

    /// <summary>
    /// Parses raw barcode text back into structured routing metadata.
    /// </summary>
    IngestionBarcodePayload? ParseIngestionPayload(string rawBarcodeText);

    /// <summary>
    /// Generates a machine-readable 2D barcode (Data Matrix / QR) as PNG bytes for stamping on document margins.
    /// </summary>
    byte[] GenerateIngestionBarcodeBytes(IngestionBarcodePayload payload, int width = 120, int height = 120);

    /// <summary>
    /// Decodes a 2D barcode from raw image bytes of a scanned document page.
    /// </summary>
    IngestionBarcodePayload? DecodeBarcodeFromImageBytes(byte[] imageBytes);
}
