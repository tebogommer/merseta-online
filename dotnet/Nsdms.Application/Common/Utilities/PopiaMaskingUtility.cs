using System.Text.RegularExpressions;

namespace Nsdms.Application.Common.Utilities;

/// <summary>
/// Enterprise POPIA (Protection of Personal Information Act) dynamic field masking and PII redaction utility.
/// Complies with POPIA Section 19 (Security Safeguards) and Section 14 (Data Minimization).
/// </summary>
public static class PopiaMaskingUtility
{
    /// <summary>
    /// Masks a 13-digit South African ID number (e.g., "9201015009087" -> "920101*****87").
    /// Preserves the first 6 digits for Date of Birth verification and last 2 digits for record alignment, masking the 5 middle digits.
    /// </summary>
    public static string MaskRsaId(string? rsaId)
    {
        if (string.IsNullOrWhiteSpace(rsaId)) return string.Empty;
        var clean = rsaId.Trim();
        if (clean.Length == 13)
        {
            return $"{clean[..6]}*****{clean[11..]}";
        }
        if (clean.Length > 4)
        {
            return $"{clean[..2]}*****{clean[^2..]}";
        }
        return "*****";
    }

    /// <summary>
    /// Masks a bank account number (e.g., "1234567890" -> "******7890").
    /// Preserves only the last 4 digits for financial reconciliation.
    /// </summary>
    public static string MaskBankAccount(string? accountNumber)
    {
        if (string.IsNullOrWhiteSpace(accountNumber)) return string.Empty;
        var clean = accountNumber.Trim();
        if (clean.Length <= 4) return "****";
        return new string('*', clean.Length - 4) + clean[^4..];
    }

    /// <summary>
    /// Masks a phone number (e.g., "+27821234567" -> "+27 82 *** 4567").
    /// </summary>
    public static string MaskPhone(string? phone)
    {
        if (string.IsNullOrWhiteSpace(phone)) return string.Empty;
        var clean = phone.Trim();
        if (clean.Length < 7) return "***-***";
        return $"{clean[..4]} *** {clean[^4..]}";
    }

    /// <summary>
    /// Masks an email address (e.g., "john.doe@merseta.org.za" -> "j***e@merseta.org.za").
    /// </summary>
    public static string MaskEmail(string? email)
    {
        if (string.IsNullOrWhiteSpace(email) || !email.Contains('@')) return "*****";
        var parts = email.Trim().Split('@');
        var user = parts[0];
        var domain = parts[1];

        if (user.Length <= 2)
        {
            return $"{user[0]}*@{domain}";
        }
        return $"{user[0]}***{user[^1]}@{domain}";
    }

    /// <summary>
    /// Checks whether a property name is classified as POPIA-sensitive PII.
    /// </summary>
    public static bool IsSensitivePropertyName(string propertyName)
    {
        if (string.IsNullOrWhiteSpace(propertyName)) return false;
        var lower = propertyName.ToLowerInvariant();
        return lower.Contains("rsaid") ||
               lower.Contains("idnumber") ||
               lower.Contains("accountnumber") ||
               lower.Contains("bankaccount") ||
               lower.Contains("password") ||
               lower.Contains("secret") ||
               lower.Contains("securitycode") ||
               lower.Contains("taxnumber") ||
               lower.Contains("phonenumber") ||
               lower.Contains("cellphone") ||
               lower.Contains("otp");
    }

    /// <summary>
    /// Masks a sensitive value dynamically based on property classification.
    /// </summary>
    public static string MaskSensitiveValue(string propertyName, string? rawValue)
    {
        if (string.IsNullOrWhiteSpace(rawValue)) return string.Empty;
        var lower = propertyName.ToLowerInvariant();

        if (lower.Contains("rsaid") || lower.Contains("idnumber"))
            return MaskRsaId(rawValue);

        if (lower.Contains("accountnumber") || lower.Contains("bankaccount"))
            return MaskBankAccount(rawValue);

        if (lower.Contains("phone") || lower.Contains("cell"))
            return MaskPhone(rawValue);

        if (lower.Contains("email"))
            return MaskEmail(rawValue);

        if (lower.Contains("password") || lower.Contains("secret") || lower.Contains("otp"))
            return "[REDACTED_SECRET]";

        return MaskBankAccount(rawValue);
    }
}
