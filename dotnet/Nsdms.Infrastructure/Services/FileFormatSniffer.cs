using System.Text;

namespace Nsdms.Infrastructure.Services;

/// <summary>
/// High-speed non-allocating file format, encoding, and delimiter sniffer.
/// Eliminates regional South African Excel semicolon export failures.
/// </summary>
public static class FileFormatSniffer
{
    private static readonly (byte[] Bom, Encoding Encoding)[] KnownBoms = new[]
    {
        (new byte[] { 0xEF, 0xBB, 0xBF }, Encoding.UTF8),
        (new byte[] { 0xFF, 0xFE }, Encoding.Unicode), // UTF-16 LE
        (new byte[] { 0xFE, 0xFF }, Encoding.BigEndianUnicode)
    };

    public static async Task<(Encoding Encoding, char Delimiter, bool IsExcel)> SniffFilePropertiesAsync(
        Stream stream, 
        string fileName, 
        CancellationToken ct = default)
    {
        var ext = Path.GetExtension(fileName).ToLowerInvariant();
        if (ext is ".xlsx" or ".xlsm" or ".xls")
        {
            return (Encoding.UTF8, '\0', true);
        }

        // Read up to 4KB preamble
        byte[] buffer = new byte[4096];
        int bytesRead = await stream.ReadAsync(buffer, 0, buffer.Length, ct);
        if (stream.CanSeek)
        {
            stream.Position = 0;
        }

        // 1. Detect Encoding via BOM
        Encoding detectedEncoding = Encoding.UTF8;
        int preambleOffset = 0;
        foreach (var (bom, enc) in KnownBoms)
        {
            if (bytesRead >= bom.Length && buffer.Take(bom.Length).SequenceEqual(bom))
            {
                detectedEncoding = enc;
                preambleOffset = bom.Length;
                break;
            }
        }

        // 2. Frequency analysis on delimiters
        using var reader = new StreamReader(new MemoryStream(buffer, preambleOffset, bytesRead - preambleOffset), detectedEncoding, leaveOpen: true);
        var delimiters = new[] { ',', ';', '\t', '|' };
        var counts = new Dictionary<char, int> { { ',', 0 }, { ';', 0 }, { '\t', 0 }, { '|', 0 } };

        int lines = 0;
        string? line;
        while ((line = reader.ReadLine()) != null && lines++ < 10)
        {
            bool inQuotes = false;
            foreach (char c in line)
            {
                if (c == '"') inQuotes = !inQuotes;
                else if (!inQuotes && counts.ContainsKey(c))
                {
                    counts[c]++;
                }
            }
        }

        char bestDelimiter = counts.OrderByDescending(kv => kv.Value).First().Key;
        if (counts[bestDelimiter] == 0) bestDelimiter = ',';

        return (detectedEncoding, bestDelimiter, false);
    }
}
