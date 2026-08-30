using System.Text.RegularExpressions;

namespace Nsdms.Application.Common.Utilities;

/// <summary>
/// South African SARS Skills Development Levy (SDL) number validator.
/// Format: {Prefix}{9-digit number}
/// - Prefix: L (Levy Paying), N (Non-Levy/Exempt), W (SETA internal)
/// - 9 Digits: Core SARS tax registration sequence and check digit.
/// </summary>
public static class SdlNumberValidator
{
    private static readonly Regex SdlPattern = new(@"^[LNWlnw]\d{9}$", RegexOptions.Compiled);

    /// <summary>
    /// Validates whether the given string is a valid 10-character South African SDL Number.
    /// </summary>
    public static bool Validate(string? sdlNumber)
    {
        return Parse(sdlNumber).IsValid;
    }

    /// <summary>
    /// Parses and validates an SDL Number, returning structured diagnostic results.
    /// </summary>
    public static (bool IsValid, string? FormattedSdl, string? ErrorMessage) Parse(string? sdlNumber)
    {
        if (string.IsNullOrWhiteSpace(sdlNumber))
        {
            return (false, null, "SDL Number cannot be empty.");
        }

        string clean = sdlNumber.Trim().ToUpperInvariant();

        if (clean.Length != 10)
        {
            return (false, null, $"SDL Number must be exactly 10 characters in length (Found: {clean.Length}).");
        }

        if (!SdlPattern.IsMatch(clean))
        {
            return (false, null, "SDL Number must start with 'L', 'N', or 'W' followed by exactly 9 numeric digits.");
        }

        return (true, clean, null);
    }
}
