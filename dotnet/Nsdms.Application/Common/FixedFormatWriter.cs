using System.Text;

namespace Nsdms.Application.Common;

/// <summary>
/// High-performance stream and string builder utility for generating DHET SETMIS and SAQA NLRD
/// fixed-width positional flat-file extracts according to statutory specifications.
/// </summary>
public static class FixedFormatWriter
{
    /// <summary>
    /// Formats a string field to the exact specified width, left-aligned and space-padded on the right.
    /// Strips line breaks, trims outer whitespace, and converts to uppercase if required.
    /// </summary>
    public static string FormatString(string? value, int length, bool toUpper = true)
    {
        if (length <= 0) return string.Empty;

        if (string.IsNullOrEmpty(value))
        {
            return new string(' ', length);
        }

        // Sanitize: remove newlines, carriage returns, and tabs
        var sanitized = value
            .Replace("\r", " ")
            .Replace("\n", " ")
            .Replace("\t", " ")
            .Trim();

        if (toUpper)
        {
            sanitized = sanitized.ToUpperInvariant();
        }

        if (sanitized.Length > length)
        {
            return sanitized.Substring(0, length);
        }

        return sanitized.PadRight(length, ' ');
    }

    /// <summary>
    /// Formats an integer field to the exact specified width, right-aligned and optionally zero-padded on the left.
    /// </summary>
    public static string FormatInteger(int? value, int length, bool zeroPad = true)
    {
        if (length <= 0) return string.Empty;

        if (!value.HasValue)
        {
            return new string(' ', length);
        }

        var str = value.Value.ToString();
        if (str.Length > length)
        {
            return str.Substring(str.Length - length);
        }

        return zeroPad ? str.PadLeft(length, '0') : str.PadLeft(length, ' ');
    }

    /// <summary>
    /// Formats a long integer field to the exact specified width.
    /// </summary>
    public static string FormatLong(long? value, int length, bool zeroPad = true)
    {
        if (length <= 0) return string.Empty;

        if (!value.HasValue)
        {
            return new string(' ', length);
        }

        var str = value.Value.ToString();
        if (str.Length > length)
        {
            return str.Substring(str.Length - length);
        }

        return zeroPad ? str.PadLeft(length, '0') : str.PadLeft(length, ' ');
    }

    /// <summary>
    /// Formats a decimal monetary or numerical field.
    /// </summary>
    public static string FormatDecimal(decimal? value, int length, int decimalPlaces = 2, bool zeroPad = true)
    {
        if (length <= 0) return string.Empty;

        if (!value.HasValue)
        {
            return new string(' ', length);
        }

        var format = decimalPlaces > 0 ? $"F{decimalPlaces}" : "F0";
        var str = value.Value.ToString(format, System.Globalization.CultureInfo.InvariantCulture);

        if (str.Length > length)
        {
            return str.Substring(0, length);
        }

        return zeroPad ? str.PadLeft(length, '0') : str.PadLeft(length, ' ');
    }

    /// <summary>
    /// Formats a date field into statutory string format (default "yyyyMMdd").
    /// If null, returns space-padded string of the specified length.
    /// </summary>
    public static string FormatDate(DateTime? date, string format = "yyyyMMdd", int length = 8)
    {
        if (length <= 0) return string.Empty;

        if (!date.HasValue)
        {
            return new string(' ', length);
        }

        var str = date.Value.ToString(format, System.Globalization.CultureInfo.InvariantCulture);
        if (str.Length > length)
        {
            return str.Substring(0, length);
        }

        return str.PadRight(length, ' ');
    }

    /// <summary>
    /// Generates a SAQA NLRD standard header record.
    /// Format: "HEADER" (6) + SupplierID (3, default "599") + FileDescription (19) + RecordCount (9, zero-padded) + Filler (padded to recordLength).
    /// </summary>
    public static string FormatNlrdHeader(string fileDescription, int recordCount, int recordLength, string supplierId = "599")
    {
        var sb = new StringBuilder();
        sb.Append(FormatString("HEADER", 6, toUpper: true));
        sb.Append(FormatString(supplierId, 3, toUpper: true));
        sb.Append(FormatString(fileDescription, 19, toUpper: true));
        sb.Append(FormatInteger(recordCount, 9, zeroPad: true));

        // Padded filler to complete the exact record length
        var currentLen = sb.Length;
        if (currentLen < recordLength)
        {
            sb.Append(new string(' ', recordLength - currentLen));
        }

        return sb.ToString();
    }
}
